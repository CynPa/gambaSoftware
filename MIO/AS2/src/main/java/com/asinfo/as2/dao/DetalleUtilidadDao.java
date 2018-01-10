/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleUtilidad;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Predicate;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class DetalleUtilidadDao
/*  21:    */   extends AbstractDaoAS2<DetalleUtilidad>
/*  22:    */ {
/*  23:    */   public DetalleUtilidadDao()
/*  24:    */   {
/*  25: 43 */     super(DetalleUtilidad.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<DetalleUtilidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  29:    */   {
/*  30: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  31: 54 */     CriteriaQuery<DetalleUtilidad> criteriaQuery = criteriaBuilder.createQuery(DetalleUtilidad.class);
/*  32: 55 */     Root<DetalleUtilidad> from = criteriaQuery.from(DetalleUtilidad.class);
/*  33:    */     
/*  34: 57 */     from.fetch("pagoRol", JoinType.LEFT);
/*  35: 58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  36:    */     
/*  37: 60 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  38: 61 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  39:    */     
/*  40: 63 */     CriteriaQuery<DetalleUtilidad> select = criteriaQuery.select(from);
/*  41: 64 */     TypedQuery<DetalleUtilidad> typedQuery = this.em.createQuery(select);
/*  42: 65 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  43:    */     
/*  44: 67 */     return typedQuery.getResultList();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int contarPorCriterio(Map<String, String> filters)
/*  48:    */   {
/*  49: 78 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  50: 79 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  51:    */     
/*  52: 81 */     Root<DetalleUtilidad> from = criteriaQuery.from(DetalleUtilidad.class);
/*  53: 82 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  54:    */     
/*  55: 84 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  56: 85 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  57:    */     
/*  58: 87 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  59:    */   }
/*  60:    */   
/*  61:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<DetalleUtilidad> from)
/*  62:    */   {
/*  63:103 */     List<Predicate> predicates = new ArrayList();
/*  64:104 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  65:    */     {
/*  66:105 */       String filterProperty = (String)it.next();
/*  67:107 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  68:    */       {
/*  69:108 */         String filterValue = (String)filters.get(filterProperty);
/*  70:110 */         if (filterProperty.equals("idOrganizacion"))
/*  71:    */         {
/*  72:111 */           Expression<Integer> path = from.get(filterProperty);
/*  73:112 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:116 */     return predicates;
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleUtilidadDao
 * JD-Core Version:    0.7.0.1
 */