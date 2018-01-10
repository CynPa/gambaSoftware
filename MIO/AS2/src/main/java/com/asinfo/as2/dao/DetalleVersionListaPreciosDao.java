/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Join;
/*  16:    */ import javax.persistence.criteria.Path;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ import javax.persistence.criteria.Selection;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class DetalleVersionListaPreciosDao
/*  23:    */   extends AbstractDaoAS2<DetalleVersionListaPrecios>
/*  24:    */ {
/*  25:    */   public DetalleVersionListaPreciosDao()
/*  26:    */   {
/*  27: 39 */     super(DetalleVersionListaPrecios.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int contarPorCriterio(Map<String, String> filters)
/*  31:    */   {
/*  32: 44 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  33: 45 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  34:    */     
/*  35: 47 */     Root<DetalleVersionListaPrecios> from = criteriaQuery.from(DetalleVersionListaPrecios.class);
/*  36: 48 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  37:    */     
/*  38: 50 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  39:    */     
/*  40: 52 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  41:    */     
/*  42: 54 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<DetalleVersionListaPrecios> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 62 */     if (sortField == null) {
/*  48: 63 */       sortField = "fechaDesde";
/*  49:    */     }
/*  50: 66 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  51:    */     
/*  52: 68 */     CriteriaQuery<DetalleVersionListaPrecios> criteriaQuery = criteriaBuilder.createQuery(DetalleVersionListaPrecios.class);
/*  53:    */     
/*  54: 70 */     Root<DetalleVersionListaPrecios> from = criteriaQuery.from(DetalleVersionListaPrecios.class);
/*  55:    */     
/*  56: 72 */     Path<Integer> pathIdDetalleVersionListaPrecios = from.get("idDetalleVersionListaPrecios");
/*  57: 73 */     Path<BigDecimal> pathPrecioUnitario = from.get("precioUnitario");
/*  58: 74 */     Path<BigDecimal> pathPorcentajeDescuentoMaximo = from.get("porcentajeDescuentoMaximo");
/*  59: 75 */     Path<String> pathTraNombreProducto = from.join("producto").get("nombre");
/*  60: 76 */     Path<String> pathTraCodigoProducto = from.join("producto").get("codigo");
/*  61: 77 */     Path<String> pathTraPrecioReferencial = from.join("producto").get("precioReferencial");
/*  62:    */     
/*  63: 79 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  64:    */     
/*  65:    */ 
/*  66: 82 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  67:    */     
/*  68:    */ 
/*  69: 85 */     criteriaQuery
/*  70: 86 */       .where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  71:    */     
/*  72:    */ 
/*  73: 89 */     CriteriaQuery<DetalleVersionListaPrecios> select = criteriaQuery.multiselect(new Selection[] { pathIdDetalleVersionListaPrecios, pathPrecioUnitario, pathPorcentajeDescuentoMaximo, pathTraNombreProducto, pathTraCodigoProducto, pathTraPrecioReferencial });
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78: 94 */     TypedQuery<DetalleVersionListaPrecios> typedQuery = this.em.createQuery(select);
/*  79: 95 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  80:    */     
/*  81: 97 */     return typedQuery.getResultList();
/*  82:    */   }
/*  83:    */   
/*  84:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<DetalleVersionListaPrecios> from)
/*  85:    */   {
/*  86:104 */     List<Predicate> predicates = new ArrayList();
/*  87:106 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  88:    */     {
/*  89:107 */       String filterProperty = (String)it.next();
/*  90:109 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  91:    */       {
/*  92:110 */         String filterValue = (String)filters.get(filterProperty);
/*  93:112 */         if (filterProperty.equals("idVersionListaPrecios"))
/*  94:    */         {
/*  95:114 */           Path<Integer> path = from.join("versionListaPrecios").get(filterProperty);
/*  96:    */           
/*  97:116 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  98:    */         }
/*  99:118 */         else if (filterProperty.equals("producto.codigo"))
/* 100:    */         {
/* 101:120 */           Path<String> path = from.join("producto").get("codigo");
/* 102:121 */           predicates.add(criteriaBuilder.like(path, filterValue
/* 103:122 */             .trim() + "%"));
/* 104:    */         }
/* 105:124 */         else if (filterProperty.equals("producto.nombre"))
/* 106:    */         {
/* 107:126 */           Path<String> path = from.join("producto").get("nombre");
/* 108:127 */           predicates.add(criteriaBuilder.like(path, filterValue
/* 109:128 */             .trim() + "%"));
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:132 */     return predicates;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleVersionListaPreciosDao
 * JD-Core Version:    0.7.0.1
 */