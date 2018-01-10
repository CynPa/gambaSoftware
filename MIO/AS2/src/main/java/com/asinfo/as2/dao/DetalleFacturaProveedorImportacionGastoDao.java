/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
/*   5:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   7:    */ import com.asinfo.as2.entities.GastoImportacion;
/*   8:    */ import com.asinfo.as2.entities.Moneda;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.NoResultException;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class DetalleFacturaProveedorImportacionGastoDao
/*  24:    */   extends AbstractDaoAS2<DetalleFacturaProveedorImportacionGasto>
/*  25:    */ {
/*  26:    */   public DetalleFacturaProveedorImportacionGastoDao()
/*  27:    */   {
/*  28: 43 */     super(DetalleFacturaProveedorImportacionGasto.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<DetalleFacturaProveedorImportacionGasto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34:    */     
/*  35: 55 */     CriteriaQuery<DetalleFacturaProveedorImportacionGasto> criteriaQuery = criteriaBuilder.createQuery(DetalleFacturaProveedorImportacionGasto.class);
/*  36: 56 */     Root<DetalleFacturaProveedorImportacionGasto> from = criteriaQuery.from(DetalleFacturaProveedorImportacionGasto.class);
/*  37:    */     
/*  38: 58 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  39: 59 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  40:    */     
/*  41: 61 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  42:    */     
/*  43: 63 */     CriteriaQuery<DetalleFacturaProveedorImportacionGasto> select = criteriaQuery.select(from);
/*  44: 64 */     TypedQuery<DetalleFacturaProveedorImportacionGasto> typedQuery = this.em.createQuery(select);
/*  45: 65 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  46:    */     
/*  47: 67 */     return typedQuery.getResultList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<DetalleFacturaProveedorImportacionGasto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  51:    */   {
/*  52: 76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  53:    */     
/*  54: 78 */     CriteriaQuery<DetalleFacturaProveedorImportacionGasto> criteriaQuery = criteriaBuilder.createQuery(DetalleFacturaProveedorImportacionGasto.class);
/*  55: 79 */     Root<DetalleFacturaProveedorImportacionGasto> from = criteriaQuery.from(DetalleFacturaProveedorImportacionGasto.class);
/*  56:    */     
/*  57: 81 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  58: 82 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  59:    */     
/*  60: 84 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  61:    */     
/*  62: 86 */     CriteriaQuery<DetalleFacturaProveedorImportacionGasto> select = criteriaQuery.select(from);
/*  63: 87 */     TypedQuery<DetalleFacturaProveedorImportacionGasto> typedQuery = this.em.createQuery(select);
/*  64:    */     
/*  65: 89 */     return typedQuery.getResultList();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public DetalleFacturaProveedorImportacionGasto cargarDetalle(int idDetalleFacturaProveedorImportacionGasto)
/*  69:    */   {
/*  70: 99 */     DetalleFacturaProveedorImportacionGasto dfpig = (DetalleFacturaProveedorImportacionGasto)buscarPorId(Integer.valueOf(idDetalleFacturaProveedorImportacionGasto));
/*  71:101 */     if (dfpig.getGastoImportacion() != null) {
/*  72:102 */       dfpig.getGastoImportacion().getId();
/*  73:    */     }
/*  74:104 */     if (dfpig.getFacturaProveedorImportacion() != null) {
/*  75:105 */       dfpig.getFacturaProveedorImportacion().getId();
/*  76:    */     }
/*  77:107 */     if (dfpig.getMoneda() != null) {
/*  78:108 */       dfpig.getMoneda().getId();
/*  79:    */     }
/*  80:111 */     return dfpig;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<DetalleFacturaProveedorImportacionGasto> obtenerListaPresupuestoImportacion(FacturaProveedor facturaProveedor)
/*  84:    */     throws ExcepcionAS2Compras
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:124 */       StringBuilder sql = new StringBuilder();
/*  89:125 */       sql.append("SELECT dfpig");
/*  90:126 */       sql.append(" FROM DetalleFacturaProveedorImportacionGasto dfpig");
/*  91:127 */       sql.append(" INNER JOIN FETCH dfpig.gastoImportacion gi");
/*  92:128 */       sql.append(" INNER JOIN dfpig.facturaProveedorImportacion fpi");
/*  93:129 */       sql.append(" INNER JOIN fpi.facturaProveedor fp");
/*  94:130 */       sql.append(" WHERE fp = :facturaProveedor");
/*  95:131 */       Query query = this.em.createQuery(sql.toString()).setParameter("facturaProveedor", facturaProveedor);
/*  96:132 */       return query.getResultList();
/*  97:    */     }
/*  98:    */     catch (NoResultException e)
/*  99:    */     {
/* 100:134 */       throw new ExcepcionAS2Compras("msg_error_factura_proveedor_importacion");
/* 101:    */     }
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleFacturaProveedorImportacionGastoDao
 * JD-Core Version:    0.7.0.1
 */