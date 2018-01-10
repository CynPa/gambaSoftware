/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   5:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   6:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  10:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ import javax.persistence.EntityManager;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TypedQuery;
/*  19:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  20:    */ import javax.persistence.criteria.CriteriaQuery;
/*  21:    */ import javax.persistence.criteria.Expression;
/*  22:    */ import javax.persistence.criteria.Fetch;
/*  23:    */ import javax.persistence.criteria.JoinType;
/*  24:    */ import javax.persistence.criteria.Predicate;
/*  25:    */ import javax.persistence.criteria.Root;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class LiquidacionAnticipoClienteDao
/*  29:    */   extends AbstractDaoAS2<LiquidacionAnticipoCliente>
/*  30:    */ {
/*  31:    */   public LiquidacionAnticipoClienteDao()
/*  32:    */   {
/*  33: 50 */     super(LiquidacionAnticipoCliente.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public LiquidacionAnticipoCliente cargarDetalle(int idLiquidacionAnticipoCliente)
/*  37:    */   {
/*  38: 54 */     LiquidacionAnticipoCliente liquidacionAnticipoCliente = (LiquidacionAnticipoCliente)buscarPorId(Integer.valueOf(idLiquidacionAnticipoCliente));
/*  39: 55 */     AnticipoCliente anticipoCliente = liquidacionAnticipoCliente.getAnticipoCliente();
/*  40: 56 */     anticipoCliente.getDocumento().getId();
/*  41: 57 */     anticipoCliente.getEmpresa().getId();
/*  42:    */     
/*  43: 59 */     liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().size();
/*  44: 60 */     for (DetalleLiquidacionAnticipoCliente detalleLiquidacionAnticipoCliente : liquidacionAnticipoCliente
/*  45: 61 */       .getListaDetalleLiquidacionAnticipoCliente())
/*  46:    */     {
/*  47: 62 */       detalleLiquidacionAnticipoCliente.getId();
/*  48: 63 */       detalleLiquidacionAnticipoCliente.getCuentaPorCobrar().getId();
/*  49: 64 */       if (detalleLiquidacionAnticipoCliente.getCuentaPorCobrar().getFacturaCliente() != null) {
/*  50: 65 */         detalleLiquidacionAnticipoCliente.getCuentaPorCobrar().getFacturaCliente().getId();
/*  51:    */       }
/*  52:    */     }
/*  53: 68 */     return liquidacionAnticipoCliente;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<LiquidacionAnticipoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  57:    */   {
/*  58: 80 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  59: 81 */     CriteriaQuery<LiquidacionAnticipoCliente> criteriaQuery = criteriaBuilder.createQuery(LiquidacionAnticipoCliente.class);
/*  60: 82 */     Root<LiquidacionAnticipoCliente> from = criteriaQuery.from(LiquidacionAnticipoCliente.class);
/*  61:    */     
/*  62: 84 */     from.fetch("documento", JoinType.LEFT);
/*  63: 85 */     from.fetch("asiento", JoinType.LEFT);
/*  64: 86 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  65: 87 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  66:    */     
/*  67: 89 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  68:    */     
/*  69: 91 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  70: 92 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  71:    */     
/*  72: 94 */     CriteriaQuery<LiquidacionAnticipoCliente> select = criteriaQuery.select(from);
/*  73:    */     
/*  74: 96 */     TypedQuery<LiquidacionAnticipoCliente> typedQuery = this.em.createQuery(select);
/*  75: 97 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  76:    */     
/*  77: 99 */     return typedQuery.getResultList();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<DetalleInterfazContable> getDetalleLiquidacionAnticipoClienteIC(int idLiquidacionAnticipoCliente)
/*  81:    */     throws ExcepcionAS2Financiero
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:114 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #',fc.numero), '', -dl.valor)  FROM DetalleLiquidacionAnticipoCliente dl  INNER JOIN dl.cuentaPorCobrar cx  INNER JOIN cx.facturaCliente fc  INNER JOIN fc.documento do  INNER JOIN dl.liquidacionAnticipoCliente la  INNER JOIN la.anticipoCliente ac  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableCliente cc  WHERE la.idLiquidacionAnticipoCliente=:idLiquidacionAnticipoCliente");
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:125 */       query.setParameter("idLiquidacionAnticipoCliente", Integer.valueOf(idLiquidacionAnticipoCliente));
/*  97:    */       
/*  98:127 */       return query.getResultList();
/*  99:    */     }
/* 100:    */     catch (IllegalArgumentException e)
/* 101:    */     {
/* 102:130 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableCliente");
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<DetalleInterfazContable> getCuentaAnticipoCliente(int idLiquidacionAnticipoCliente)
/* 107:    */     throws ExcepcionAS2Financiero
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:146 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable((CASE WHEN nc IS NULL THEN ccac.idCuentaContable ELSE ccacnc.idCuentaContable END),em.nombreFiscal,CONCAT(do.nombre,' #', la.numero, ' - ', da.nombre,' #', ac.numero), '', la.valor)  FROM LiquidacionAnticipoCliente la  INNER JOIN la.documento do  INNER JOIN la.anticipoCliente ac  INNER JOIN ac.documento da  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ac.notaCreditoCliente nc LEFT JOIN ce.cuentaContableAnticipoCliente ccac  LEFT JOIN ce.cuentaContableAnticipoClienteNotaCredito ccacnc  WHERE la.idLiquidacionAnticipoCliente=:idLiquidacionAnticipoCliente");
/* 112:    */       
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:158 */       query.setParameter("idLiquidacionAnticipoCliente", Integer.valueOf(idLiquidacionAnticipoCliente));
/* 124:    */       
/* 125:160 */       return query.getResultList();
/* 126:    */     }
/* 127:    */     catch (IllegalArgumentException e)
/* 128:    */     {
/* 129:162 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableCliente");
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void actualizarEstado(Integer idLiquidacionAnticipoCliente, Estado estado)
/* 134:    */   {
/* 135:175 */     Query query = this.em.createQuery("UPDATE LiquidacionAnticipoCliente lac SET lac.estado=:estado WHERE lac.idLiquidacionAnticipoCliente=:idLiquidacionAnticipoCliente");
/* 136:176 */     query.setParameter("idLiquidacionAnticipoCliente", idLiquidacionAnticipoCliente);
/* 137:177 */     query.setParameter("estado", estado);
/* 138:178 */     query.executeUpdate();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<LiquidacionAnticipoCliente> getLiquidacionAnticipoClientePorAnticipoCliente(Integer idAnticipoCliente)
/* 142:    */   {
/* 143:191 */     Query query = this.em.createQuery("SELECT lac FROM LiquidacionAnticipoCliente lac WHERE lac.anticipoCliente.idAnticipoCliente=:idAnticipoCliente");
/* 144:192 */     query.setParameter("idAnticipoCliente", idAnticipoCliente);
/* 145:193 */     return query.getResultList();
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.LiquidacionAnticipoClienteDao
 * JD-Core Version:    0.7.0.1
 */