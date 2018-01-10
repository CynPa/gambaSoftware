/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.TypedQuery;
/*  10:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  11:    */ import javax.persistence.criteria.CriteriaQuery;
/*  12:    */ import javax.persistence.criteria.Expression;
/*  13:    */ import javax.persistence.criteria.Path;
/*  14:    */ import javax.persistence.criteria.Predicate;
/*  15:    */ import javax.persistence.criteria.Root;
/*  16:    */ import javax.persistence.criteria.Selection;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class CategoriaActivoDao
/*  20:    */   extends AbstractDaoAS2<CategoriaActivo>
/*  21:    */ {
/*  22:    */   public CategoriaActivoDao()
/*  23:    */   {
/*  24: 40 */     super(CategoriaActivo.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List<CategoriaActivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  28:    */   {
/*  29: 51 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  30: 52 */     CriteriaQuery<CategoriaActivo> criteriaQuery = criteriaBuilder.createQuery(CategoriaActivo.class);
/*  31: 53 */     Root<CategoriaActivo> from = criteriaQuery.from(CategoriaActivo.class);
/*  32:    */     
/*  33: 55 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  34: 56 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  35: 57 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  36:    */     
/*  37:    */ 
/*  38: 60 */     CriteriaQuery<CategoriaActivo> select = criteriaQuery.select(from);
/*  39: 61 */     TypedQuery<CategoriaActivo> typedQuery = this.em.createQuery(select);
/*  40: 62 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  41:    */     
/*  42: 64 */     return typedQuery.getResultList();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<CategoriaActivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  48: 77 */     CriteriaQuery<CategoriaActivo> criteriaQuery = criteriaBuilder.createQuery(CategoriaActivo.class);
/*  49: 78 */     Root<CategoriaActivo> from = criteriaQuery.from(CategoriaActivo.class);
/*  50:    */     
/*  51: 80 */     Path<Integer> pathIdCategoriaActivo = from.get("idCategoriaActivo");
/*  52: 81 */     Path<String> pathCodigo = from.get("codigo");
/*  53: 82 */     Path<String> pathNombre = from.get("nombre");
/*  54:    */     
/*  55: 84 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  56:    */     
/*  57: 86 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  58: 87 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  59:    */     
/*  60: 89 */     CriteriaQuery<CategoriaActivo> select = criteriaQuery.multiselect(new Selection[] { pathIdCategoriaActivo, pathCodigo, pathNombre });
/*  61: 90 */     TypedQuery<CategoriaActivo> typedQuery = this.em.createQuery(select);
/*  62:    */     
/*  63: 92 */     return typedQuery.getResultList();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int contarPorCriterio(Map<String, String> filters)
/*  67:    */   {
/*  68:105 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  69:106 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  70:    */     
/*  71:108 */     Root<CategoriaActivo> from = criteriaQuery.from(CategoriaActivo.class);
/*  72:109 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  73:    */     
/*  74:111 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  75:112 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  76:    */     
/*  77:    */ 
/*  78:115 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public CategoriaActivo cargarDetalle(int idCategoriaActivo)
/*  82:    */   {
/*  83:125 */     CategoriaActivo categoriaActivo = (CategoriaActivo)buscarPorId(Integer.valueOf(idCategoriaActivo));
/*  84:    */     
/*  85:127 */     categoriaActivo.getListaSubcategoriaActivo().size();
/*  86:129 */     if (categoriaActivo.getCuentaContableActivoFijo() != null) {
/*  87:130 */       categoriaActivo.getCuentaContableActivoFijo().getId();
/*  88:    */     }
/*  89:132 */     if (categoriaActivo.getCuentaContableDepreciacion() != null) {
/*  90:133 */       categoriaActivo.getCuentaContableDepreciacion().getId();
/*  91:    */     }
/*  92:135 */     if (categoriaActivo.getCuentaContableDepreciacionAcumulada() != null) {
/*  93:136 */       categoriaActivo.getCuentaContableDepreciacionAcumulada().getId();
/*  94:    */     }
/*  95:138 */     if (categoriaActivo.getCuentaContableSuperavitPorRevalorizacion() != null) {
/*  96:139 */       categoriaActivo.getCuentaContableSuperavitPorRevalorizacion().getId();
/*  97:    */     }
/*  98:141 */     if (categoriaActivo.getCuentaContableDeDeficitPorRevalorizacion() != null) {
/*  99:142 */       categoriaActivo.getCuentaContableDeDeficitPorRevalorizacion().getId();
/* 100:    */     }
/* 101:144 */     return categoriaActivo;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CategoriaActivoDao
 * JD-Core Version:    0.7.0.1
 */