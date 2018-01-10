/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Propina;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Expression;
/* 12:   */ import javax.persistence.criteria.JoinType;
/* 13:   */ import javax.persistence.criteria.Path;
/* 14:   */ import javax.persistence.criteria.Predicate;
/* 15:   */ import javax.persistence.criteria.Root;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class PropinaDao
/* 19:   */   extends AbstractDaoAS2<Propina>
/* 20:   */ {
/* 21:   */   public PropinaDao()
/* 22:   */   {
/* 23:38 */     super(Propina.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<Propina> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 27:   */   {
/* 28:45 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 29:46 */     CriteriaQuery<Propina> criteriaQuery = criteriaBuilder.createQuery(Propina.class);
/* 30:47 */     Root<Propina> from = criteriaQuery.from(Propina.class);
/* 31:   */     
/* 32:49 */     from.fetch("pagoRol", JoinType.INNER);
/* 33:50 */     from.fetch("pagoRolDiasTrabajados", JoinType.INNER);
/* 34:   */     
/* 35:   */ 
/* 36:53 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 37:54 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 38:55 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 39:   */     
/* 40:57 */     CriteriaQuery<Propina> select = criteriaQuery.select(from);
/* 41:58 */     TypedQuery<Propina> typedQuery = this.em.createQuery(select);
/* 42:59 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 43:   */     
/* 44:61 */     return typedQuery.getResultList();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Propina cargarDetalle(int idPropina)
/* 48:   */   {
/* 49:66 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 50:   */     
/* 51:68 */     CriteriaQuery<Propina> cqCabecera = criteriaBuilder.createQuery(Propina.class);
/* 52:69 */     Root<Propina> fromCabecera = cqCabecera.from(Propina.class);
/* 53:   */     
/* 54:71 */     fromCabecera.fetch("pagoRol", JoinType.INNER);
/* 55:72 */     fromCabecera.fetch("pagoRolDiasTrabajados", JoinType.INNER);
/* 56:   */     
/* 57:   */ 
/* 58:75 */     Path<Integer> pathId = fromCabecera.get("idPropina");
/* 59:76 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPropina)));
/* 60:77 */     CriteriaQuery<Propina> selectPropina = cqCabecera.select(fromCabecera);
/* 61:   */     
/* 62:79 */     Propina propina = (Propina)this.em.createQuery(selectPropina).getSingleResult();
/* 63:   */     
/* 64:81 */     return propina;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PropinaDao
 * JD-Core Version:    0.7.0.1
 */