/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ValorAtributo;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Set;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ import javax.persistence.EntityManager;
/* 10:   */ import javax.persistence.TypedQuery;
/* 11:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 12:   */ import javax.persistence.criteria.CriteriaQuery;
/* 13:   */ import javax.persistence.criteria.Path;
/* 14:   */ import javax.persistence.criteria.Predicate;
/* 15:   */ import javax.persistence.criteria.Root;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class ValorAtributoDao
/* 19:   */   extends AbstractDaoAS2<ValorAtributo>
/* 20:   */ {
/* 21:   */   public ValorAtributoDao()
/* 22:   */   {
/* 23:44 */     super(ValorAtributo.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<ValorAtributo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 27:   */   {
/* 28:55 */     filters = agregarFiltrosOrganizacion(filters);
/* 29:   */     
/* 30:57 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 31:58 */     CriteriaQuery<ValorAtributo> criteriaQuery = criteriaBuilder.createQuery(ValorAtributo.class);
/* 32:59 */     Root<ValorAtributo> from = criteriaQuery.from(ValorAtributo.class);
/* 33:   */     
/* 34:61 */     Predicate conjunction = criteriaBuilder.conjunction();
/* 35:62 */     Predicate disjunction = criteriaBuilder.disjunction();
/* 36:64 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/* 37:   */     {
/* 38:65 */       String filterProperty = (String)it.next();
/* 39:67 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/* 40:   */       {
/* 41:68 */         String filterValue = (String)filters.get(filterProperty);
/* 42:69 */         if (filterProperty.startsWith("idOrganizacion")) {
/* 43:70 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/* 44:71 */         } else if (filterProperty.contains("atributo.idAtributo")) {
/* 45:72 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get("atributo").get("idAtributo"), Integer.valueOf(Integer.parseInt(filterValue))));
/* 46:73 */         } else if ((filterProperty.equals("codigo")) || (filterProperty.equals("nombre"))) {
/* 47:74 */           disjunction.getExpressions().add(criteriaBuilder
/* 48:75 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/* 49:   */         }
/* 50:   */       }
/* 51:   */     }
/* 52:80 */     if (disjunction.getExpressions().size() > 0) {
/* 53:81 */       conjunction.getExpressions().add(disjunction);
/* 54:   */     }
/* 55:84 */     if (conjunction.getExpressions().size() > 0) {
/* 56:85 */       criteriaQuery.where(conjunction);
/* 57:   */     }
/* 58:88 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 59:   */     
/* 60:90 */     CriteriaQuery<ValorAtributo> select = criteriaQuery.select(from);
/* 61:91 */     TypedQuery<ValorAtributo> typedQuery = this.em.createQuery(select);
/* 62:   */     
/* 63:93 */     return typedQuery.getResultList();
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ValorAtributoDao
 * JD-Core Version:    0.7.0.1
 */