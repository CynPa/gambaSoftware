/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaProducto;
/*  4:   */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.NoResultException;
/* 12:   */ import javax.persistence.Query;
/* 13:   */ import javax.persistence.TypedQuery;
/* 14:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 15:   */ import javax.persistence.criteria.CriteriaQuery;
/* 16:   */ import javax.persistence.criteria.Expression;
/* 17:   */ import javax.persistence.criteria.Predicate;
/* 18:   */ import javax.persistence.criteria.Root;
/* 19:   */ 
/* 20:   */ @Stateless
/* 21:   */ public class CategoriaProductoDao
/* 22:   */   extends AbstractDaoAS2<CategoriaProducto>
/* 23:   */ {
/* 24:   */   public CategoriaProductoDao()
/* 25:   */   {
/* 26:40 */     super(CategoriaProducto.class);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public CategoriaProducto buscarPorCodigo(String codigo)
/* 30:   */     throws ExcepcionAS2
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:47 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 35:48 */       CriteriaQuery<CategoriaProducto> criteriaQuery = criteriaBuilder.createQuery(CategoriaProducto.class);
/* 36:49 */       Root<CategoriaProducto> from = criteriaQuery.from(CategoriaProducto.class);
/* 37:   */       
/* 38:51 */       Map<String, String> filters = new HashMap();
/* 39:52 */       filters.put("codigo", "=" + codigo);
/* 40:53 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 41:54 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 42:   */       
/* 43:56 */       CriteriaQuery<CategoriaProducto> select = criteriaQuery.select(from);
/* 44:57 */       TypedQuery<CategoriaProducto> typedQuery = this.em.createQuery(select);
/* 45:   */       
/* 46:59 */       return (CategoriaProducto)typedQuery.getSingleResult();
/* 47:   */     }
/* 48:   */     catch (NoResultException e)
/* 49:   */     {
/* 50:62 */       throw new ExcepcionAS2(e);
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public List<SubcategoriaProducto> obtenerListaSubcategoriaProducto(int idOrganizacion, CategoriaProducto categoriaProducto, String consulta)
/* 55:   */   {
/* 56:68 */     StringBuilder sbSQL = new StringBuilder();
/* 57:69 */     sbSQL.append(" SELECT scp FROM SubcategoriaProducto scp");
/* 58:70 */     sbSQL.append(" JOIN FETCH scp.categoriaProducto cp");
/* 59:71 */     sbSQL.append(" WHERE scp.idOrganizacion = :idOrganizacion ");
/* 60:72 */     sbSQL.append(" AND scp.activo = :activo ");
/* 61:73 */     if (categoriaProducto != null) {
/* 62:74 */       sbSQL.append(" AND cp.idCategoriaProducto = :idCategoriaProducto ");
/* 63:   */     }
/* 64:76 */     if (consulta != null) {
/* 65:77 */       sbSQL.append(" AND scp.nombre like :consulta");
/* 66:   */     }
/* 67:79 */     Query query = this.em.createQuery(sbSQL.toString());
/* 68:80 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 69:81 */     query.setParameter("activo", Boolean.valueOf(true));
/* 70:82 */     if (categoriaProducto != null) {
/* 71:83 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getIdCategoriaProducto()));
/* 72:   */     }
/* 73:85 */     if (consulta != null)
/* 74:   */     {
/* 75:86 */       query.setParameter("consulta", "%" + consulta.trim() + "%");
/* 76:87 */       sbSQL.append(" AND scp.nombre like :consulta");
/* 77:   */     }
/* 78:89 */     query.setMaxResults(20);
/* 79:90 */     return query.getResultList();
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CategoriaProductoDao
 * JD-Core Version:    0.7.0.1
 */