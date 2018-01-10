/*   1:    */ package com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CuentaBancariaDao;
/*   4:    */ import com.asinfo.as2.dao.CuentaBancariaOrganizacionDao;
/*   5:    */ import com.asinfo.as2.dao.CuentaContableCruceCuentaBancariaOrganizacionDao;
/*   6:    */ import com.asinfo.as2.dao.FormaPagoCuentaBancariaOrganizacionDao;
/*   7:    */ import com.asinfo.as2.entities.BancoAcreedor;
/*   8:    */ import com.asinfo.as2.entities.ConfiguracionConciliacionBancaria;
/*   9:    */ import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
/*  10:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  11:    */ import com.asinfo.as2.entities.CuentaContable;
/*  12:    */ import com.asinfo.as2.entities.CuentaContableCruceCuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.entities.FormaPago;
/*  14:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  15:    */ import com.asinfo.as2.entities.Secuencia;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  17:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  18:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  19:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  21:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.ejb.Stateless;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class ServicioCuentaBancariaOrganizacionImpl
/*  29:    */   implements ServicioCuentaBancariaOrganizacion
/*  30:    */ {
/*  31:    */   @EJB
/*  32:    */   private CuentaBancariaOrganizacionDao cuentaBancariaOrganizacionDao;
/*  33:    */   @EJB
/*  34:    */   private FormaPagoCuentaBancariaOrganizacionDao formaPagoCuentaBancariaOrganizacionDao;
/*  35:    */   @EJB
/*  36:    */   private CuentaBancariaDao cuentaBancariaDao;
/*  37:    */   @EJB
/*  38:    */   private transient CuentaContableCruceCuentaBancariaOrganizacionDao cuentaContableCruceCuentaBancariaOrganizacionDao;
/*  39:    */   @EJB
/*  40:    */   private transient ServicioGenerico<ConfiguracionConciliacionBancaria> servicioConfiguracionConciliacionBancaria;
/*  41:    */   @EJB
/*  42:    */   private ServicioGenerico<BancoAcreedor> servicioBancoAcreedor;
/*  43:    */   
/*  44:    */   public void guardar(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/*  45:    */     throws ExcepcionAS2, AS2Exception
/*  46:    */   {
/*  47: 71 */     validar(cuentaBancariaOrganizacion);
/*  48:    */     
/*  49: 73 */     this.cuentaBancariaOrganizacionDao.guardar(cuentaBancariaOrganizacion);
/*  50: 75 */     for (FormaPagoCuentaBancariaOrganizacion formaPago : cuentaBancariaOrganizacion.getListaFormaPago()) {
/*  51: 76 */       this.formaPagoCuentaBancariaOrganizacionDao.guardar(formaPago);
/*  52:    */     }
/*  53: 79 */     for (BancoAcreedor ba : cuentaBancariaOrganizacion.getListaBancoAcreedor()) {
/*  54: 80 */       this.servicioBancoAcreedor.guardar(ba);
/*  55:    */     }
/*  56: 83 */     for (CuentaContableCruceCuentaBancariaOrganizacion cuentaContableCruceCuentaBancariaOrganizacion : cuentaBancariaOrganizacion
/*  57: 84 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion()) {
/*  58: 85 */       this.cuentaContableCruceCuentaBancariaOrganizacionDao.guardar(cuentaContableCruceCuentaBancariaOrganizacion);
/*  59:    */     }
/*  60: 88 */     this.cuentaBancariaDao.guardar(cuentaBancariaOrganizacion.getCuentaBancaria());
/*  61: 90 */     if ((cuentaBancariaOrganizacion.getConfiguracionConciliacionBancaria() != null) && 
/*  62: 91 */       (cuentaBancariaOrganizacion.getConfiguracionConciliacionBancaria().getColumnaMonto() != null))
/*  63:    */     {
/*  64: 92 */       cuentaBancariaOrganizacion.getConfiguracionConciliacionBancaria().setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/*  65: 93 */       this.servicioConfiguracionConciliacionBancaria.guardar(cuentaBancariaOrganizacion.getConfiguracionConciliacionBancaria());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void eliminar(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/*  70:    */   {
/*  71:105 */     this.cuentaBancariaOrganizacionDao.eliminar(cuentaBancariaOrganizacion);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public CuentaBancariaOrganizacion buscarPorId(Integer id)
/*  75:    */   {
/*  76:115 */     return (CuentaBancariaOrganizacion)this.cuentaBancariaOrganizacionDao.buscarPorId(id);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<CuentaBancariaOrganizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  80:    */   {
/*  81:121 */     return this.cuentaBancariaOrganizacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public CuentaBancariaOrganizacion buscarPorCuentaContable(int idCuentaContable)
/*  85:    */   {
/*  86:131 */     return this.cuentaBancariaOrganizacionDao.buscarPorCuentaContable(idCuentaContable);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<CuentaBancariaOrganizacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  90:    */   {
/*  91:142 */     return this.cuentaBancariaOrganizacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public CuentaBancariaOrganizacion cargarDetalle(int idCuentaBancariaOrganizacion)
/*  95:    */   {
/*  96:152 */     return this.cuentaBancariaOrganizacionDao.cargarDetalle(idCuentaBancariaOrganizacion);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int contarPorCriterio(Map<String, String> filters)
/* 100:    */   {
/* 101:162 */     return this.cuentaBancariaOrganizacionDao.contarPorCriterio(filters);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getReporteCuentaBancaria(int idAsiento)
/* 105:    */   {
/* 106:172 */     return this.cuentaBancariaOrganizacionDao.getReporteCuentaBancaria(idAsiento);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public CuentaBancariaOrganizacion buscarPorNumeroCuentaBancaria(int idOrganizacion, String numeroCuentaBancaria)
/* 110:    */   {
/* 111:177 */     return this.cuentaBancariaOrganizacionDao.buscarPorNumeroCuentaBancaria(idOrganizacion, numeroCuentaBancaria);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public List<FormaPagoCuentaBancariaOrganizacion> getListaCuentaBancariaPorFormaPago(FormaPago formaPago)
/* 115:    */   {
/* 116:182 */     return this.cuentaBancariaOrganizacionDao.getListaCuentaBancariaPorFormaPago(formaPago);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Secuencia obtenerSecuenciaPorFormaPago(int idCuentaBancariaOrganizacion, int idFormaPago)
/* 120:    */   {
/* 121:192 */     return this.cuentaBancariaOrganizacionDao.obtenerSecuenciaPorFormaPago(idCuentaBancariaOrganizacion, idFormaPago);
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void validar(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 125:    */     throws ExcepcionAS2
/* 126:    */   {
/* 127:197 */     if (!cuentaBancariaOrganizacion.isIndicadorCruce())
/* 128:    */     {
/* 129:199 */       if (cuentaBancariaOrganizacion.getCuentaContableBanco() == null) {
/* 130:200 */         throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " CUENTA CONTABLE BANCOS");
/* 131:    */       }
/* 132:201 */       if ((cuentaBancariaOrganizacion.getTipoCuentaBancariaOrganizacion() == TipoCuentaBancariaOrganizacion.BANCO) && 
/* 133:202 */         (!cuentaBancariaOrganizacion.getCuentaContableBanco().getTipoCuentaContable().equals(TipoCuentaContable.BANCO))) {
/* 134:203 */         throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + TipoCuentaContable.BANCO);
/* 135:    */       }
/* 136:206 */       if (cuentaBancariaOrganizacion.getCuentaContableGastosBancarios() == null) {
/* 137:207 */         throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " CUENTA CONTABLE GASTOS BANCARIOS");
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public ConfiguracionExtractoBancario getConfiguracionExtractoBancario(CuentaBancariaOrganizacion cuentaBancaria)
/* 143:    */   {
/* 144:215 */     return this.cuentaBancariaOrganizacionDao.getConfiguracionExtractoBancario(cuentaBancaria);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List<CuentaBancariaOrganizacion> cuentaBancariaOrganizacionPorFormaPago(boolean indicadorRetencionFuente, boolean indicadorRetencionIva, int idOrganizacion)
/* 148:    */   {
/* 149:220 */     return this.cuentaBancariaOrganizacionDao.cuentaBancariaOrganizacionPorFormaPago(indicadorRetencionFuente, indicadorRetencionIva, idOrganizacion);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaConCheques(int idOrganizacion)
/* 153:    */   {
/* 154:225 */     return this.cuentaBancariaOrganizacionDao.getListaCuentaBancariaConCheques(idOrganizacion);
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.impl.ServicioCuentaBancariaOrganizacionImpl
 * JD-Core Version:    0.7.0.1
 */