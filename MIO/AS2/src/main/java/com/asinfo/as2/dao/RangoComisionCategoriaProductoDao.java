/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Canal;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.RangoComision;
/*   6:    */ import com.asinfo.as2.entities.RangoComisionCategoriaProducto;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.JoinType;
/*  20:    */ import javax.persistence.criteria.Predicate;
/*  21:    */ import javax.persistence.criteria.Root;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class RangoComisionCategoriaProductoDao
/*  25:    */   extends AbstractDaoAS2<RangoComisionCategoriaProducto>
/*  26:    */ {
/*  27:    */   public RangoComisionCategoriaProductoDao()
/*  28:    */   {
/*  29: 44 */     super(RangoComisionCategoriaProducto.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<RangoComisionCategoriaProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 56 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  35: 57 */     CriteriaQuery<RangoComisionCategoriaProducto> criteriaQuery = criteriaBuilder.createQuery(RangoComisionCategoriaProducto.class);
/*  36: 58 */     Root<RangoComisionCategoriaProducto> from = criteriaQuery.from(RangoComisionCategoriaProducto.class);
/*  37:    */     
/*  38: 60 */     from.fetch("rangoComision", JoinType.LEFT);
/*  39: 61 */     from.fetch("categoriaProducto", JoinType.LEFT);
/*  40: 62 */     from.fetch("canal", JoinType.LEFT);
/*  41:    */     
/*  42: 64 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  43: 65 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  44: 66 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  45: 67 */     CriteriaQuery<RangoComisionCategoriaProducto> select = criteriaQuery.select(from);
/*  46: 68 */     TypedQuery<RangoComisionCategoriaProducto> typedQuery = this.em.createQuery(select);
/*  47: 69 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  48:    */     
/*  49: 71 */     return typedQuery.getResultList();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int contarPorCriterio(Map<String, String> filters)
/*  53:    */   {
/*  54: 83 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  55: 84 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  56:    */     
/*  57: 86 */     Root<RangoComisionCategoriaProducto> from = criteriaQuery.from(RangoComisionCategoriaProducto.class);
/*  58: 87 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  59:    */     
/*  60: 89 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  61: 90 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  62:    */     
/*  63: 92 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  64:    */   }
/*  65:    */   
/*  66:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<RangoComisionCategoriaProducto> from)
/*  67:    */   {
/*  68:107 */     filters = agregarFiltrosOrganizacion(filters);
/*  69:108 */     List<Predicate> predicates = new ArrayList();
/*  70:109 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  71:    */     {
/*  72:110 */       String filterProperty = (String)it.next();
/*  73:112 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  74:    */       {
/*  75:113 */         String filterValue = (String)filters.get(filterProperty);
/*  76:115 */         if (filterProperty.equals("idOrganizacion"))
/*  77:    */         {
/*  78:116 */           Expression<Integer> path = from.get(filterProperty);
/*  79:117 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  80:    */         }
/*  81:    */         else
/*  82:    */         {
/*  83:119 */           Expression<String> path = from.get(filterProperty);
/*  84:120 */           predicates.add(criteriaBuilder.like(path.as(String.class), "%" + filterValue + "%"));
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:124 */     return predicates;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public RangoComisionCategoriaProducto cargarDetalle(int idRangoComisionCategoriaProducto)
/*  92:    */   {
/*  93:134 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<RangoComisionCategoriaProducto> getListaRangoComisionCategoriaProducto()
/*  97:    */   {
/*  98:144 */     List<RangoComisionCategoriaProducto> lrccp = new ArrayList();
/*  99:145 */     String sql = "SELECT t from RangoComisionCategoriaProducto t ";
/* 100:146 */     Query query = this.em.createQuery(sql);
/* 101:147 */     lrccp = query.getResultList();
/* 102:148 */     for (RangoComisionCategoriaProducto rccp : lrccp)
/* 103:    */     {
/* 104:149 */       rccp.getRangoComision().getId();
/* 105:150 */       rccp.getCanal().getId();
/* 106:151 */       rccp.getCategoriaProducto().getId();
/* 107:    */     }
/* 108:154 */     return lrccp;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RangoComisionCategoriaProductoDao
 * JD-Core Version:    0.7.0.1
 */