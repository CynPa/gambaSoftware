/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.contabilidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Empresa;
/*   5:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ReporteGarantiaClienteDao
/*  19:    */   extends AbstractDaoAS2<GarantiaCliente>
/*  20:    */ {
/*  21:    */   public ReporteGarantiaClienteDao()
/*  22:    */   {
/*  23: 22 */     super(GarantiaCliente.class);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List getReporteGarantiaCliente(int idGarantiaCliente)
/*  27:    */   {
/*  28: 28 */     String sql = "SELECT em.nombreFiscal, em.identificacion,  b.nombre, ch.numero, ch.numeroCuenta, ch.fechaIngreso,  ch.fechaCobro, ch.girador, ch.recibidoPor, ch.estadoGarantiaCliente,  ch.valor, ch.observacion, cb.numero, ch.valorProtestado, ch.diasCreditoOtorgado  FROM GarantiaCliente ch  INNER JOIN ch.empresa em  LEFT JOIN ch.banco b  LEFT JOIN ch.cuentaBancariaOrganizacion cbo  LEFT JOIN cbo.cuentaBancaria cb  WHERE ch.idGarantiaCliente = :idGarantiaCliente";
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36: 36 */     Query query = this.em.createQuery(sql.toString()).setParameter("idGarantiaCliente", Integer.valueOf(idGarantiaCliente));
/*  37: 37 */     return query.getResultList();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List getReporteGarantiaClienteLista(Empresa empresa, EstadoGarantiaCliente estadoGarantiaCliente, Date fechaIngresoDesde, Date fechaIngresoHasta, TipoGarantiaCliente tipoGarantiaCliente, int idOrganizacion, boolean indicadorFechaRegistro, Sucursal sucursal, TipoOrganizacion tipoOrganizacion)
/*  41:    */   {
/*  42: 45 */     StringBuilder sql = new StringBuilder();
/*  43: 47 */     if (tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)
/*  44:    */     {
/*  45: 48 */       sql.append("SELECT em.nombreFiscal, em.identificacion, b.nombre, ch.numero, ch.numeroCuenta, ch.fechaIngreso, ");
/*  46: 49 */       sql.append(" ch.fechaCobro, ch.girador, ch.recibidoPor, ch.estadoGarantiaCliente, ");
/*  47: 50 */       sql.append(" ch.valor, ch.observacion, cb.numero, ch.valorProtestado, ch.diasCreditoOtorgado,fc.numero, dc.valor, c.numero,ch.observacion ");
/*  48: 51 */       sql.append(" FROM GarantiaCliente ch ");
/*  49: 52 */       sql.append(" INNER JOIN ch.empresa em ");
/*  50: 53 */       sql.append(" INNER JOIN ch.banco b ");
/*  51: 54 */       sql.append(" INNER JOIN ch.cuentaBancariaOrganizacion cbo ");
/*  52: 55 */       sql.append(" INNER JOIN cbo.cuentaBancaria cb ");
/*  53: 56 */       sql.append(" LEFT JOIN ch.detalleFormaCobro dfc ");
/*  54: 57 */       sql.append(" LEFT JOIN dfc.cobro c , DetalleCobro dc ");
/*  55: 58 */       sql.append(" LEFT JOIN dc.cuentaPorCobrar  cc ");
/*  56: 59 */       sql.append(" LEFT JOIN cc.facturaCliente   fc ");
/*  57: 60 */       sql.append(" WHERE (:empresa IS NULL OR em=:empresa) ");
/*  58: 61 */       sql.append(" AND c = dc.cobro ");
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 64 */       sql.append("SELECT em.nombreFiscal, em.identificacion, b.nombre, ch.numero, ch.numeroCuenta, ch.fechaIngreso, ");
/*  63: 65 */       sql.append(" ch.fechaCobro, ch.girador, ch.recibidoPor, ch.estadoGarantiaCliente, ");
/*  64: 66 */       sql.append(" ch.valor, ch.observacion, cb.numero, ch.valorProtestado, ch.diasCreditoOtorgado ");
/*  65: 67 */       sql.append(" FROM GarantiaCliente ch ");
/*  66: 68 */       sql.append(" INNER JOIN ch.empresa em ");
/*  67: 69 */       sql.append(" INNER JOIN ch.banco b ");
/*  68: 70 */       sql.append(" INNER JOIN ch.cuentaBancariaOrganizacion cbo ");
/*  69: 71 */       sql.append(" INNER JOIN cbo.cuentaBancaria cb ");
/*  70: 72 */       sql.append(" WHERE (:empresa IS NULL OR em=:empresa) ");
/*  71:    */     }
/*  72: 75 */     if (indicadorFechaRegistro) {
/*  73: 76 */       sql.append(" AND ch.fechaIngreso BETWEEN :fechaIngresoDesde AND :fechaIngresoHasta ");
/*  74:    */     } else {
/*  75: 78 */       sql.append(" AND ch.fechaCobro BETWEEN :fechaIngresoDesde AND :fechaIngresoHasta ");
/*  76:    */     }
/*  77: 81 */     if (sucursal != null) {
/*  78: 82 */       sql.append(" AND (ch.idSucursal = :idSucursal) ");
/*  79:    */     }
/*  80: 84 */     if (estadoGarantiaCliente != null) {
/*  81: 85 */       sql.append(" AND ch.estadoGarantiaCliente=:estadoGarantiaCliente ");
/*  82:    */     }
/*  83: 87 */     if (tipoGarantiaCliente != null) {
/*  84: 88 */       sql.append(" AND ch.tipoGarantiaCliente =:tipoGarantiaCliente ");
/*  85:    */     }
/*  86: 90 */     sql.append(" AND ch.idOrganizacion = :idOrganizacion ");
/*  87: 92 */     if (tipoOrganizacion == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA) {
/*  88: 93 */       sql.append(" ORDER BY em.nombreFiscal, ch.fechaCobro, c.fecha, ch.numero ");
/*  89:    */     } else {
/*  90: 95 */       sql.append(" ORDER BY em.nombreFiscal asc");
/*  91:    */     }
/*  92: 98 */     Query query = this.em.createQuery(sql.toString());
/*  93: 99 */     if (fechaIngresoDesde == null) {
/*  94:100 */       query.setParameter("fechaIngresoDesde", FuncionesUtiles.obtenerFechaInicial());
/*  95:    */     } else {
/*  96:102 */       query.setParameter("fechaIngresoDesde", fechaIngresoDesde);
/*  97:    */     }
/*  98:104 */     if (fechaIngresoHasta == null) {
/*  99:105 */       query.setParameter("fechaIngresoHasta", FuncionesUtiles.obtenerFechaFinal());
/* 100:    */     } else {
/* 101:107 */       query.setParameter("fechaIngresoHasta", fechaIngresoHasta);
/* 102:    */     }
/* 103:109 */     if (sucursal != null) {
/* 104:110 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 105:    */     }
/* 106:112 */     query.setParameter("empresa", empresa);
/* 107:113 */     if (estadoGarantiaCliente != null) {
/* 108:114 */       query.setParameter("estadoGarantiaCliente", estadoGarantiaCliente);
/* 109:    */     }
/* 110:116 */     if (tipoGarantiaCliente != null) {
/* 111:117 */       query.setParameter("tipoGarantiaCliente", tipoGarantiaCliente);
/* 112:    */     }
/* 113:119 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 114:    */     
/* 115:121 */     return query.getResultList();
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.contabilidad.ReporteGarantiaClienteDao
 * JD-Core Version:    0.7.0.1
 */