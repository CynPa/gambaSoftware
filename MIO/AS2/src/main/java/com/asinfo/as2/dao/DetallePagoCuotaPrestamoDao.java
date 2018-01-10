/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetallePagoCuotaPrestamo;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Predicate;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class DetallePagoCuotaPrestamoDao
/*  21:    */   extends AbstractDaoAS2<DetallePagoCuotaPrestamo>
/*  22:    */ {
/*  23:    */   public DetallePagoCuotaPrestamoDao()
/*  24:    */   {
/*  25: 43 */     super(DetallePagoCuotaPrestamo.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<DetallePagoCuotaPrestamo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  29:    */   {
/*  30: 54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  31: 55 */     CriteriaQuery<DetallePagoCuotaPrestamo> criteriaQuery = criteriaBuilder.createQuery(DetallePagoCuotaPrestamo.class);
/*  32: 56 */     Root<DetallePagoCuotaPrestamo> from = criteriaQuery.from(DetallePagoCuotaPrestamo.class);
/*  33:    */     
/*  34: 58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  35: 59 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  36: 60 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  37:    */     
/*  38: 62 */     CriteriaQuery<DetallePagoCuotaPrestamo> select = criteriaQuery.select(from);
/*  39: 63 */     TypedQuery<DetallePagoCuotaPrestamo> typedQuery = this.em.createQuery(select);
/*  40: 64 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  41:    */     
/*  42: 66 */     return typedQuery.getResultList();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<DetallePagoCuotaPrestamo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 77 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52: 88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  53: 89 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  54:    */     
/*  55: 91 */     Root<DetallePagoCuotaPrestamo> from = criteriaQuery.from(DetallePagoCuotaPrestamo.class);
/*  56: 92 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  57:    */     
/*  58: 94 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  59: 95 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  60:    */     
/*  61: 97 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  62:    */   }
/*  63:    */   
/*  64:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<DetallePagoCuotaPrestamo> from)
/*  65:    */   {
/*  66:112 */     filters = agregarFiltrosOrganizacion(filters);
/*  67:113 */     List<Predicate> predicates = new ArrayList();
/*  68:114 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  69:    */     {
/*  70:115 */       String filterProperty = (String)it.next();
/*  71:117 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  72:    */       {
/*  73:118 */         String filterValue = (String)filters.get(filterProperty);
/*  74:120 */         if (filterProperty.equals("idOrganizacion"))
/*  75:    */         {
/*  76:121 */           Expression<Integer> path = from.get(filterProperty);
/*  77:122 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  78:    */         }
/*  79:    */         else
/*  80:    */         {
/*  81:124 */           Expression<String> path = from.get(filterProperty);
/*  82:125 */           predicates.add(criteriaBuilder.like(path.as(String.class), "%" + filterValue + "%"));
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:129 */     return predicates;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public DetallePagoCuotaPrestamo cargarDetalle(int idDetallePagoCuotaPrestamo)
/*  90:    */   {
/*  91:139 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isExistePagoCuota(int idDetallePrestamo, int idPagoRol)
/*  95:    */   {
/*  96:144 */     String sql = "SELECT dpcp FROM DetallePagoCuotaPrestamo dpcp WHERE dpcp.detallePrestamo.idDetallePrestamo=:idDetallePrestamo AND dpcp.pagoRolEmpleado.pagoRol.idPagoRol=:idPagoRol";
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:148 */     Query query = this.em.createQuery(sql);
/* 101:149 */     query.setParameter("idDetallePrestamo", Integer.valueOf(idDetallePrestamo));
/* 102:150 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/* 103:151 */     return !query.getResultList().isEmpty();
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetallePagoCuotaPrestamoDao
 * JD-Core Version:    0.7.0.1
 */