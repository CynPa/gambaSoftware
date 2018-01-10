/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.FormaPago;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteDepositosCobroBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  37:    */   @EJB
/*  38:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   private Empresa empresa;
/*  42:    */   private Date fechaDesde;
/*  43:    */   private Date fechaHasta;
/*  44:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  45:    */   private FormaPago formaPago;
/*  46:    */   private boolean indicadorPendienteDeposito;
/*  47:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  48:    */   
/*  49:    */   protected JRDataSource getJRDataSource()
/*  50:    */   {
/*  51: 86 */     List listaDatosReporte = new ArrayList();
/*  52: 87 */     JRDataSource ds = null;
/*  53: 88 */     listaDatosReporte = this.servicioReporteCobroCliente.getReporteDepositosCobro(getEmpresa().getId(), this.fechaDesde, this.fechaHasta, 
/*  54: 89 */       getCuentaBancariaOrganizacion().getId(), this.indicadorPendienteDeposito, AppUtil.getOrganizacion().getId(), getFormaPago().getId());
/*  55:    */     
/*  56: 91 */     String[] fields = { "f_fechaCobro", "f_numeroCobro", "f_identificacion", "f_nombreFiscal", "f_nombreComercial", "f_cuentaBancariaCobro", "f_formaPagoCobro", "f_documentoReferencia", "f_valorCobro", "f_asientoCobro", "f_numeroDeposito", "f_fechaDeposito", "f_cuentaBancariaDeposito", "f_formaPagoDeposito", "f_valorDeposito", "f_asientoDeposito", "f_bancoCobro", "f_bancoDeposito" };
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61: 96 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  62:    */     
/*  63: 98 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   @PostConstruct
/*  67:    */   public void init()
/*  68:    */   {
/*  69:104 */     Calendar calfechaDesde = Calendar.getInstance();
/*  70:105 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  71:106 */     this.fechaDesde = calfechaDesde.getTime();
/*  72:107 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected String getCompileFileName()
/*  76:    */   {
/*  77:117 */     return "reporteDepositosCobro";
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected Map<String, Object> getReportParameters()
/*  81:    */   {
/*  82:129 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  83:130 */     reportParameters.put("ReportTitle", "Depositos de Cobros");
/*  84:131 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  85:132 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  86:133 */     return reportParameters;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String execute()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:144 */       validaDatos();
/*  94:145 */       super.prepareReport();
/*  95:    */     }
/*  96:    */     catch (JRException e)
/*  97:    */     {
/*  98:147 */       LOG.info("Error JRException");
/*  99:148 */       e.printStackTrace();
/* 100:149 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:    */     }
/* 102:    */     catch (IOException e)
/* 103:    */     {
/* 104:151 */       LOG.info("Error IOException");
/* 105:152 */       e.printStackTrace();
/* 106:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:156 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void validaDatos()
/* 112:    */   {
/* 113:160 */     if (this.fechaDesde == null) {
/* 114:161 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 115:    */     }
/* 116:163 */     if (this.fechaHasta == null) {
/* 117:164 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 122:    */   {
/* 123:175 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void actualizarFormaPago()
/* 127:    */   {
/* 128:179 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.cuentaBancariaOrganizacion;
/* 129:180 */     setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(cuentaBancariaOrganizacion.getId()));
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Empresa getEmpresa()
/* 133:    */   {
/* 134:189 */     if (this.empresa == null) {
/* 135:190 */       this.empresa = new Empresa();
/* 136:    */     }
/* 137:192 */     return this.empresa;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setEmpresa(Empresa empresa)
/* 141:    */   {
/* 142:202 */     this.empresa = empresa;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Date getFechaDesde()
/* 146:    */   {
/* 147:211 */     return this.fechaDesde;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFechaDesde(Date fechaDesde)
/* 151:    */   {
/* 152:221 */     this.fechaDesde = fechaDesde;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Date getFechaHasta()
/* 156:    */   {
/* 157:230 */     return this.fechaHasta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setFechaHasta(Date fechaHasta)
/* 161:    */   {
/* 162:240 */     this.fechaHasta = fechaHasta;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 166:    */   {
/* 167:249 */     if (this.cuentaBancariaOrganizacion == null) {
/* 168:250 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 169:    */     }
/* 170:252 */     return this.cuentaBancariaOrganizacion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 174:    */   {
/* 175:262 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public FormaPago getFormaPago()
/* 179:    */   {
/* 180:271 */     if (this.formaPago == null) {
/* 181:272 */       this.formaPago = new FormaPago();
/* 182:    */     }
/* 183:274 */     return this.formaPago;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setFormaPago(FormaPago formaPago)
/* 187:    */   {
/* 188:284 */     this.formaPago = formaPago;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean isIndicadorPendienteDeposito()
/* 192:    */   {
/* 193:293 */     return this.indicadorPendienteDeposito;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setIndicadorPendienteDeposito(boolean indicadorPendienteDeposito)
/* 197:    */   {
/* 198:303 */     this.indicadorPendienteDeposito = indicadorPendienteDeposito;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 202:    */   {
/* 203:312 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 204:313 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", false, null);
/* 205:    */     }
/* 206:315 */     return this.listaCuentaBancariaOrganizacion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 210:    */   {
/* 211:324 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 212:    */   }
/* 213:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteDepositosCobroBean
 * JD-Core Version:    0.7.0.1
 */