/*  1:   */ package com.asinfo.as2.dao.sri;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.sri.AnuladoSRI;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.Query;
/* 10:   */ import javax.persistence.TypedQuery;
/* 11:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 12:   */ import javax.persistence.criteria.CriteriaQuery;
/* 13:   */ import javax.persistence.criteria.Expression;
/* 14:   */ import javax.persistence.criteria.JoinType;
/* 15:   */ import javax.persistence.criteria.Predicate;
/* 16:   */ import javax.persistence.criteria.Root;
/* 17:   */ 
/* 18:   */ @Stateless
/* 19:   */ public class AnuladoSRIDao
/* 20:   */   extends AbstractDaoAS2<AnuladoSRI>
/* 21:   */ {
/* 22:   */   public AnuladoSRIDao()
/* 23:   */   {
/* 24:35 */     super(AnuladoSRI.class);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<AnuladoSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 28:   */   {
/* 29:40 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 30:41 */     CriteriaQuery<AnuladoSRI> criteriaQuery = criteriaBuilder.createQuery(AnuladoSRI.class);
/* 31:42 */     Root<AnuladoSRI> from = criteriaQuery.from(AnuladoSRI.class);
/* 32:43 */     from.fetch("tipoComprobanteSRI", JoinType.LEFT);
/* 33:   */     
/* 34:45 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 35:46 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 36:47 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 37:48 */     CriteriaQuery<AnuladoSRI> select = criteriaQuery.select(from);
/* 38:   */     
/* 39:50 */     TypedQuery<AnuladoSRI> typedQuery = this.em.createQuery(select);
/* 40:51 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 41:   */     
/* 42:53 */     return typedQuery.getResultList();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<AnuladoSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 46:   */   {
/* 47:63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 48:64 */     CriteriaQuery<AnuladoSRI> criteriaQuery = criteriaBuilder.createQuery(AnuladoSRI.class);
/* 49:65 */     Root<AnuladoSRI> from = criteriaQuery.from(AnuladoSRI.class);
/* 50:66 */     from.fetch("tipoComprobanteSRI", JoinType.LEFT);
/* 51:67 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 52:   */     
/* 53:69 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 54:70 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 55:   */     
/* 56:72 */     CriteriaQuery<AnuladoSRI> select = criteriaQuery.select(from);
/* 57:73 */     TypedQuery<AnuladoSRI> typedQuery = this.em.createQuery(select);
/* 58:   */     
/* 59:75 */     return typedQuery.getResultList();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public List<AnuladoSRI> obtenerAnuladosMes(int anio, int mes, int idOrganizacion)
/* 63:   */   {
/* 64:81 */     Query query = this.em.createQuery("SELECT a  FROM AnuladoSRI a LEFT JOIN FETCH a.tipoComprobanteSRI tc  WHERE mes=:mes AND anio=:anio AND a.idOrganizacion=:idOrganizacion ");
/* 65:   */     
/* 66:   */ 
/* 67:84 */     query.setParameter("mes", Integer.valueOf(mes));
/* 68:85 */     query.setParameter("anio", Integer.valueOf(anio));
/* 69:86 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 70:87 */     return query.getResultList();
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.AnuladoSRIDao
 * JD-Core Version:    0.7.0.1
 */