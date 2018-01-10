/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Unidad;
/*   7:    */ import com.asinfo.as2.entities.UnidadConversion;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.NoResultException;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.JoinType;
/*  20:    */ import javax.persistence.criteria.Predicate;
/*  21:    */ import javax.persistence.criteria.Root;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class SubcategoriaProductoDao
/*  25:    */   extends AbstractDaoAS2<SubcategoriaProducto>
/*  26:    */ {
/*  27:    */   public SubcategoriaProductoDao()
/*  28:    */   {
/*  29: 41 */     super(SubcategoriaProducto.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<SubcategoriaProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 51 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  35: 52 */     CriteriaQuery<SubcategoriaProducto> criteriaQuery = criteriaBuilder.createQuery(SubcategoriaProducto.class);
/*  36: 53 */     Root<SubcategoriaProducto> from = criteriaQuery.from(SubcategoriaProducto.class);
/*  37:    */     
/*  38: 55 */     from.fetch("categoriaProducto", JoinType.LEFT);
/*  39:    */     
/*  40: 57 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  41: 58 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  42:    */     
/*  43: 60 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  44:    */     
/*  45: 62 */     CriteriaQuery<SubcategoriaProducto> select = criteriaQuery.select(from);
/*  46:    */     
/*  47: 64 */     TypedQuery<SubcategoriaProducto> typedQuery = this.em.createQuery(select);
/*  48: 65 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  49:    */     
/*  50: 67 */     return typedQuery.getResultList();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public SubcategoriaProducto cargarDetalle(int idSubcategoriaProducto)
/*  54:    */   {
/*  55: 79 */     SubcategoriaProducto subcategoriaProducto = (SubcategoriaProducto)buscarPorId(Integer.valueOf(idSubcategoriaProducto));
/*  56:    */     
/*  57: 81 */     subcategoriaProducto.getCategoriaProducto().getId();
/*  58: 83 */     if (subcategoriaProducto.getListaUnidadConversion().size() > 0) {
/*  59: 84 */       for (UnidadConversion unidadConversion : subcategoriaProducto.getListaUnidadConversion())
/*  60:    */       {
/*  61: 85 */         unidadConversion.getUnidadDestino().getIdUnidad();
/*  62: 86 */         unidadConversion.getUnidadOrigen().getIdUnidad();
/*  63:    */       }
/*  64:    */     }
/*  65: 90 */     if (subcategoriaProducto.getCuentaContableCosto() != null) {
/*  66: 91 */       subcategoriaProducto.getCuentaContableCosto().getId();
/*  67:    */     }
/*  68: 94 */     if (subcategoriaProducto.getCuentaContableGasto() != null) {
/*  69: 95 */       subcategoriaProducto.getCuentaContableGasto().getId();
/*  70:    */     }
/*  71: 98 */     if (subcategoriaProducto.getCuentaContableIngreso() != null) {
/*  72: 99 */       subcategoriaProducto.getCuentaContableIngreso().getId();
/*  73:    */     }
/*  74:102 */     if (subcategoriaProducto.getCuentaContableInventario() != null) {
/*  75:103 */       subcategoriaProducto.getCuentaContableInventario().getId();
/*  76:    */     }
/*  77:106 */     if (subcategoriaProducto.getCuentaContableMercaderiaPorDespachar() != null) {
/*  78:107 */       subcategoriaProducto.getCuentaContableMercaderiaPorDespachar().getId();
/*  79:    */     }
/*  80:110 */     if (subcategoriaProducto.getCuentaContableMercaderiaPorRecibir() != null) {
/*  81:111 */       subcategoriaProducto.getCuentaContableMercaderiaPorRecibir().getId();
/*  82:    */     }
/*  83:114 */     if (subcategoriaProducto.getCuentaContableDescuentoVenta() != null) {
/*  84:115 */       subcategoriaProducto.getCuentaContableDescuentoVenta().getId();
/*  85:    */     }
/*  86:118 */     if (subcategoriaProducto.getCuentaContableDevolucionVenta() != null) {
/*  87:119 */       subcategoriaProducto.getCuentaContableDevolucionVenta().getId();
/*  88:    */     }
/*  89:122 */     if (subcategoriaProducto.getCuentaContableDescuentoCompra() != null) {
/*  90:123 */       subcategoriaProducto.getCuentaContableDescuentoCompra().getId();
/*  91:    */     }
/*  92:126 */     if (subcategoriaProducto.getCuentaContableDevolucionCompra() != null) {
/*  93:127 */       subcategoriaProducto.getCuentaContableDevolucionCompra().getId();
/*  94:    */     }
/*  95:130 */     return subcategoriaProducto;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public SubcategoriaProducto buscarPorCodigo(String codigo)
/*  99:    */     throws ExcepcionAS2
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:136 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 104:137 */       CriteriaQuery<SubcategoriaProducto> criteriaQuery = criteriaBuilder.createQuery(SubcategoriaProducto.class);
/* 105:138 */       Root<SubcategoriaProducto> from = criteriaQuery.from(SubcategoriaProducto.class);
/* 106:    */       
/* 107:140 */       Map<String, String> filters = new HashMap();
/* 108:141 */       filters.put("codigo", "=" + codigo);
/* 109:142 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 110:143 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 111:    */       
/* 112:145 */       CriteriaQuery<SubcategoriaProducto> select = criteriaQuery.select(from);
/* 113:146 */       TypedQuery<SubcategoriaProducto> typedQuery = this.em.createQuery(select);
/* 114:    */       
/* 115:148 */       return (SubcategoriaProducto)typedQuery.getSingleResult();
/* 116:    */     }
/* 117:    */     catch (NoResultException e)
/* 118:    */     {
/* 119:151 */       throw new ExcepcionAS2(e);
/* 120:    */     }
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SubcategoriaProductoDao
 * JD-Core Version:    0.7.0.1
 */