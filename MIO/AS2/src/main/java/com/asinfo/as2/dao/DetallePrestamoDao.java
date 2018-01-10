/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetallePrestamo;
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
/*  15:    */ import javax.persistence.criteria.Path;
/*  16:    */ import javax.persistence.criteria.Predicate;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ import javax.persistence.criteria.Selection;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class DetallePrestamoDao
/*  22:    */   extends AbstractDaoAS2<DetallePrestamo>
/*  23:    */ {
/*  24:    */   public DetallePrestamoDao()
/*  25:    */   {
/*  26: 42 */     super(DetallePrestamo.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<DetallePrestamo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 52 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 53 */     CriteriaQuery<DetallePrestamo> criteriaQuery = criteriaBuilder.createQuery(DetallePrestamo.class);
/*  33: 54 */     Root<DetallePrestamo> from = criteriaQuery.from(DetallePrestamo.class);
/*  34:    */     
/*  35: 56 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  36: 57 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  37: 58 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  38:    */     
/*  39: 60 */     CriteriaQuery<DetallePrestamo> select = criteriaQuery.select(from);
/*  40: 61 */     TypedQuery<DetallePrestamo> typedQuery = this.em.createQuery(select);
/*  41: 62 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  42:    */     
/*  43: 64 */     return typedQuery.getResultList();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<DetallePrestamo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  47:    */   {
/*  48: 75 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  49: 76 */     CriteriaQuery<DetallePrestamo> criteriaQuery = criteriaBuilder.createQuery(DetallePrestamo.class);
/*  50: 77 */     Root<DetallePrestamo> from = criteriaQuery.from(DetallePrestamo.class);
/*  51:    */     
/*  52: 79 */     Path<Integer> pathIdDetallePrestamo = from.get("idDetallePrestamo");
/*  53: 80 */     Path<String> pathCodigo = from.get("codigo");
/*  54: 81 */     Path<String> pathNombre = from.get("nombre");
/*  55:    */     
/*  56: 83 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  57: 84 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  58: 85 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  59:    */     
/*  60: 87 */     CriteriaQuery<DetallePrestamo> select = criteriaQuery.multiselect(new Selection[] { pathIdDetallePrestamo, pathCodigo, pathNombre });
/*  61: 88 */     TypedQuery<DetallePrestamo> typedQuery = this.em.createQuery(select);
/*  62:    */     
/*  63: 90 */     return typedQuery.getResultList();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int contarPorCriterio(Map<String, String> filters)
/*  67:    */   {
/*  68:102 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  69:103 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  70:    */     
/*  71:105 */     Root<DetallePrestamo> from = criteriaQuery.from(DetallePrestamo.class);
/*  72:106 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  73:    */     
/*  74:108 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  75:109 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  76:    */     
/*  77:111 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  78:    */   }
/*  79:    */   
/*  80:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<DetallePrestamo> from)
/*  81:    */   {
/*  82:127 */     filters = agregarFiltrosOrganizacion(filters);
/*  83:128 */     List<Predicate> predicates = new ArrayList();
/*  84:129 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  85:    */     {
/*  86:130 */       String filterProperty = (String)it.next();
/*  87:132 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  88:    */       {
/*  89:133 */         String filterValue = (String)filters.get(filterProperty);
/*  90:135 */         if (filterProperty.equals("idOrganizacion"))
/*  91:    */         {
/*  92:136 */           Expression<Integer> path = from.get(filterProperty);
/*  93:137 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  94:    */         }
/*  95:    */         else
/*  96:    */         {
/*  97:139 */           Expression<String> path = from.get(filterProperty);
/*  98:140 */           predicates.add(criteriaBuilder.like(path.as(String.class), "%" + filterValue + "%"));
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:144 */     return predicates;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public DetallePrestamo cargarDetalle(int idDetallePrestamo)
/* 106:    */   {
/* 107:154 */     return null;
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetallePrestamoDao
 * JD-Core Version:    0.7.0.1
 */