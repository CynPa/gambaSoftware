/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  4:   */ import com.asinfo.as2.entities.Impuesto;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ import javax.persistence.EntityManager;
/* 11:   */ import javax.persistence.NoResultException;
/* 12:   */ import javax.persistence.TypedQuery;
/* 13:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 14:   */ import javax.persistence.criteria.CriteriaQuery;
/* 15:   */ import javax.persistence.criteria.Expression;
/* 16:   */ import javax.persistence.criteria.Predicate;
/* 17:   */ import javax.persistence.criteria.Root;
/* 18:   */ 
/* 19:   */ @Stateless
/* 20:   */ public class CategoriaImpuestoDao
/* 21:   */   extends AbstractDaoAS2<CategoriaImpuesto>
/* 22:   */ {
/* 23:   */   public CategoriaImpuestoDao()
/* 24:   */   {
/* 25:45 */     super(CategoriaImpuesto.class);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public CategoriaImpuesto cargarDetalle(int idCategoriaImpuesto)
/* 29:   */   {
/* 30:49 */     CategoriaImpuesto categoriaImpuesto = (CategoriaImpuesto)buscarPorId(Integer.valueOf(idCategoriaImpuesto));
/* 31:50 */     categoriaImpuesto.getListaImpuesto().size();
/* 32:52 */     for (Impuesto impuesto : categoriaImpuesto.getListaImpuesto()) {
/* 33:53 */       impuesto.getListaRangoImpuesto().size();
/* 34:   */     }
/* 35:56 */     return categoriaImpuesto;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public CategoriaImpuesto buscarCodigo(int idOrganizacion, String codigo)
/* 39:   */     throws ExcepcionAS2
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:62 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 44:63 */       CriteriaQuery<CategoriaImpuesto> criteriaQuery = criteriaBuilder.createQuery(CategoriaImpuesto.class);
/* 45:64 */       Root<CategoriaImpuesto> from = criteriaQuery.from(CategoriaImpuesto.class);
/* 46:   */       
/* 47:66 */       Map<String, String> filters = new HashMap();
/* 48:67 */       filters.put("codigo", "=" + codigo);
/* 49:68 */       filters.put("idOrganizacion", Integer.toString(idOrganizacion));
/* 50:69 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 51:70 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 52:   */       
/* 53:72 */       CriteriaQuery<CategoriaImpuesto> select = criteriaQuery.select(from);
/* 54:73 */       TypedQuery<CategoriaImpuesto> typedQuery = this.em.createQuery(select);
/* 55:   */       
/* 56:75 */       return (CategoriaImpuesto)typedQuery.getSingleResult();
/* 57:   */     }
/* 58:   */     catch (NoResultException e)
/* 59:   */     {
/* 60:78 */       throw new ExcepcionAS2(e);
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CategoriaImpuestoDao
 * JD-Core Version:    0.7.0.1
 */