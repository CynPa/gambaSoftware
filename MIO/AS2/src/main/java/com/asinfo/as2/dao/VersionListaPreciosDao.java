/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*  4:   */ import com.asinfo.as2.entities.ListaPrecios;
/*  5:   */ import com.asinfo.as2.entities.Producto;
/*  6:   */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.Query;
/* 12:   */ import javax.persistence.TypedQuery;
/* 13:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 14:   */ import javax.persistence.criteria.CriteriaQuery;
/* 15:   */ import javax.persistence.criteria.Expression;
/* 16:   */ import javax.persistence.criteria.Predicate;
/* 17:   */ import javax.persistence.criteria.Root;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class VersionListaPreciosDao
/* 21:   */   extends AbstractDaoAS2<VersionListaPrecios>
/* 22:   */ {
/* 23:   */   public VersionListaPreciosDao()
/* 24:   */   {
/* 25:38 */     super(VersionListaPrecios.class);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public VersionListaPrecios cargarVersionListaPrecios(int idVersionListaPrecios)
/* 29:   */   {
/* 30:43 */     VersionListaPrecios versionListaPrecios = (VersionListaPrecios)buscarPorId(Integer.valueOf(idVersionListaPrecios));
/* 31:   */     
/* 32:45 */     versionListaPrecios.getDetalleVersionesListaPrecios().size();
/* 33:47 */     for (DetalleVersionListaPrecios dvlp : versionListaPrecios.getDetalleVersionesListaPrecios()) {
/* 34:48 */       dvlp.getProducto().getId();
/* 35:   */     }
/* 36:51 */     return versionListaPrecios;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public List<VersionListaPrecios> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 40:   */   {
/* 41:63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 42:   */     
/* 43:65 */     CriteriaQuery<VersionListaPrecios> criteriaQuery = criteriaBuilder.createQuery(VersionListaPrecios.class);
/* 44:   */     
/* 45:67 */     Root<VersionListaPrecios> from = criteriaQuery.from(VersionListaPrecios.class);
/* 46:   */     
/* 47:69 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 48:70 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 49:   */     
/* 50:72 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 51:   */     
/* 52:74 */     CriteriaQuery<VersionListaPrecios> select = criteriaQuery.select(from);
/* 53:   */     
/* 54:76 */     TypedQuery<VersionListaPrecios> typedQuery = this.em.createQuery(select);
/* 55:77 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 56:   */     
/* 57:79 */     return typedQuery.getResultList();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public List<VersionListaPrecios> autocompletarListaVersionListaPrecios(String consulta, ListaPrecios listaPrecios)
/* 61:   */   {
/* 62:85 */     StringBuilder sql = new StringBuilder();
/* 63:   */     
/* 64:87 */     sql.append(" SELECT v FROM VersionListaPrecios v ");
/* 65:88 */     sql.append(" LEFT JOIN FETCH v.zona z ");
/* 66:89 */     sql.append(" WHERE v.listaPrecios = :listaPrecios ");
/* 67:90 */     sql.append(" AND v.activo = true ");
/* 68:   */     
/* 69:92 */     Query query = this.em.createQuery(sql.toString());
/* 70:93 */     query.setParameter("listaPrecios", listaPrecios);
/* 71:94 */     return query.getResultList();
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.VersionListaPreciosDao
 * JD-Core Version:    0.7.0.1
 */