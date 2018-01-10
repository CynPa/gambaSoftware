/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   5:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   7:    */ import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.Fetch;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Predicate;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class LiquidacionAnticipoProveedorDao
/*  26:    */   extends AbstractDaoAS2<LiquidacionAnticipoProveedor>
/*  27:    */ {
/*  28:    */   public LiquidacionAnticipoProveedorDao()
/*  29:    */   {
/*  30: 48 */     super(LiquidacionAnticipoProveedor.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<LiquidacionAnticipoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 60 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  36: 61 */     CriteriaQuery<LiquidacionAnticipoProveedor> criteriaQuery = criteriaBuilder.createQuery(LiquidacionAnticipoProveedor.class);
/*  37: 62 */     Root<LiquidacionAnticipoProveedor> from = criteriaQuery.from(LiquidacionAnticipoProveedor.class);
/*  38:    */     
/*  39: 64 */     from.fetch("documento", JoinType.LEFT);
/*  40: 65 */     from.fetch("asiento", JoinType.LEFT);
/*  41: 66 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  42: 67 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  43:    */     
/*  44: 69 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  45:    */     
/*  46: 71 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  47: 72 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  48:    */     
/*  49: 74 */     CriteriaQuery<LiquidacionAnticipoProveedor> select = criteriaQuery.select(from);
/*  50:    */     
/*  51: 76 */     TypedQuery<LiquidacionAnticipoProveedor> typedQuery = this.em.createQuery(select);
/*  52: 77 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  53:    */     
/*  54: 79 */     return typedQuery.getResultList();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public LiquidacionAnticipoProveedor cargarDetalle(int idLiquidacionAnticipoProveedor)
/*  58:    */   {
/*  59: 83 */     LiquidacionAnticipoProveedor liquidacionAnticipoProveedor = (LiquidacionAnticipoProveedor)buscarPorId(Integer.valueOf(idLiquidacionAnticipoProveedor));
/*  60: 84 */     liquidacionAnticipoProveedor.getListaDetalleLiquidacionAnticipoProveedor().size();
/*  61: 85 */     for (DetalleLiquidacionAnticipoProveedor detalleLiquidacionAnticipoCliente : liquidacionAnticipoProveedor
/*  62: 86 */       .getListaDetalleLiquidacionAnticipoProveedor())
/*  63:    */     {
/*  64: 87 */       detalleLiquidacionAnticipoCliente.getId();
/*  65: 88 */       detalleLiquidacionAnticipoCliente.getCuentaPorPagar().getId();
/*  66: 89 */       if (detalleLiquidacionAnticipoCliente.getCuentaPorPagar().getFacturaProveedor() != null) {
/*  67: 90 */         detalleLiquidacionAnticipoCliente.getCuentaPorPagar().getFacturaProveedor().getId();
/*  68:    */       }
/*  69:    */     }
/*  70: 93 */     return liquidacionAnticipoProveedor;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<DetalleInterfazContable> getDetalleLiquidacionAnticipoProveedorIC(int idLiquidacionAnticipoProveedor)
/*  74:    */     throws ExcepcionAS2Financiero
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:110 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fc.numero), '', dl.valor)  FROM DetalleLiquidacionAnticipoProveedor dl  INNER JOIN dl.cuentaPorPagar cx  INNER JOIN cx.facturaProveedor fc  INNER JOIN fc.documento do  INNER JOIN dl.liquidacionAnticipoProveedor la  INNER JOIN la.anticipoProveedor ac  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  INNER JOIN ce.cuentaContableProveedor cc  WHERE la.idLiquidacionAnticipoProveedor=:idLiquidacionAnticipoProveedor");
/*  79:    */       
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:122 */       query.setParameter("idLiquidacionAnticipoProveedor", Integer.valueOf(idLiquidacionAnticipoProveedor));
/*  91:    */       
/*  92:124 */       return query.getResultList();
/*  93:    */     }
/*  94:    */     catch (IllegalArgumentException e)
/*  95:    */     {
/*  96:127 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableProveedor");
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<DetalleInterfazContable> getCuentaAnticipoProveedor(int idLiquidacionAnticipoProveedor)
/* 101:    */     throws ExcepcionAS2Financiero
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:145 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable((CASE WHEN nc IS NULL THEN ccap.idCuentaContable ELSE ccapnc.idCuentaContable END ),em.nombreFiscal,CONCAT(do.nombre,' #', la.numero, ' - ', da.nombre,' #', ac.numero), '', -la.valor)  FROM LiquidacionAnticipoProveedor la  INNER JOIN la.documento do  INNER JOIN la.anticipoProveedor ac  INNER JOIN ac.documento da  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ac.notaCreditoProveedor nc LEFT JOIN ce.cuentaContableAnticipoProveedor ccap  LEFT JOIN ce.cuentaContableAnticipoProveedorNotaCredito ccapnc  WHERE la.idLiquidacionAnticipoProveedor=:idLiquidacionAnticipoProveedor");
/* 106:    */       
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:157 */       query.setParameter("idLiquidacionAnticipoProveedor", Integer.valueOf(idLiquidacionAnticipoProveedor));
/* 118:    */       
/* 119:159 */       return query.getResultList();
/* 120:    */     }
/* 121:    */     catch (IllegalArgumentException e)
/* 122:    */     {
/* 123:162 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableAnticipoProveedor || cuentaContableAnticipoProveedorNotaCredito");
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void actualizarEstado(Integer idLiquidacionAnticipoProveedor, Estado estado)
/* 128:    */   {
/* 129:176 */     Query query = this.em.createQuery("UPDATE LiquidacionAnticipoProveedor lap SET lap.estado=:estado WHERE lap.idLiquidacionAnticipoProveedor=:idLiquidacionAnticipoProveedor");
/* 130:177 */     query.setParameter("idLiquidacionAnticipoProveedor", idLiquidacionAnticipoProveedor);
/* 131:178 */     query.setParameter("estado", estado);
/* 132:179 */     query.executeUpdate();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List<LiquidacionAnticipoProveedor> getLiquidacionAnticipoProveedorPorAnticipoProveedor(Integer idAnticipoProveedor)
/* 136:    */   {
/* 137:193 */     Query query = this.em.createQuery("SELECT lap FROM LiquidacionAnticipoProveedor lap WHERE lap.anticipoProveedor.idAnticipoProveedor=:idAnticipoProveedor");
/* 138:194 */     query.setParameter("idAnticipoProveedor", idAnticipoProveedor);
/* 139:195 */     return query.getResultList();
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.LiquidacionAnticipoProveedorDao
 * JD-Core Version:    0.7.0.1
 */