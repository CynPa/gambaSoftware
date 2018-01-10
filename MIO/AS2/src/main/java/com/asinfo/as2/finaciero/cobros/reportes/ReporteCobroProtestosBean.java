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
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteCobroProtestosBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioReporteCobroCliente servicioReporteCobroCliente;
/*  35:    */   @EJB
/*  36:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   private Empresa empresa;
/*  40:    */   private Date fechaDesde;
/*  41:    */   private Date fechaHasta;
/*  42:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  43:    */   private FormaPago formaPago;
/*  44:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  45:    */   
/*  46:    */   protected JRDataSource getJRDataSource()
/*  47:    */   {
/*  48: 83 */     List listaDatosReporte = new ArrayList();
/*  49: 84 */     JRDataSource ds = null;
/*  50: 85 */     listaDatosReporte = this.servicioReporteCobroCliente.getReporteCobroProtestos(getEmpresa().getId(), this.fechaDesde, this.fechaHasta, 
/*  51: 86 */       getCuentaBancariaOrganizacion().getId(), getFormaPago().getId(), AppUtil.getOrganizacion().getId());
/*  52:    */     
/*  53: 88 */     String[] fields = { "f_fechaCobro", "f_numeroCobro", "f_identificacion", "f_nombreFiscal", "f_nombreComercial", "f_cuentaBancariaCobro", "f_formaPagoCobro", "f_documentoReferencia", "f_valorCobro", "f_asientoCobro", "f_fechaProtesto", "f_cuentaBancariaProtesto", "f_formaPagoProtesto", "f_valorProtesto", "f_asientoProtesto", "f_bancoCobro", "f_bancoProtesto", "f_descripcionCobro" };
/*  54:    */     
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58: 93 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  59:    */     
/*  60: 95 */     return ds;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected String getCompileFileName()
/*  64:    */   {
/*  65:105 */     return "reporteCobroProtestos";
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected Map<String, Object> getReportParameters()
/*  69:    */   {
/*  70:116 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  71:117 */     reportParameters.put("ReportTitle", "Cobro Protestos");
/*  72:118 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  73:119 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  74:120 */     return reportParameters;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String execute()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:131 */       validaDatos();
/*  82:132 */       super.prepareReport();
/*  83:    */     }
/*  84:    */     catch (JRException e)
/*  85:    */     {
/*  86:134 */       LOG.info("Error JRException");
/*  87:135 */       e.printStackTrace();
/*  88:136 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  89:    */     }
/*  90:    */     catch (IOException e)
/*  91:    */     {
/*  92:138 */       LOG.info("Error IOException");
/*  93:139 */       e.printStackTrace();
/*  94:140 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  95:    */     }
/*  96:143 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void validaDatos()
/* 100:    */   {
/* 101:147 */     if (this.fechaDesde == null) {
/* 102:148 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 103:    */     }
/* 104:150 */     if (this.fechaHasta == null) {
/* 105:151 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 110:    */   {
/* 111:162 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void actualizarFormaPago()
/* 115:    */   {
/* 116:166 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.cuentaBancariaOrganizacion;
/* 117:167 */     setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(cuentaBancariaOrganizacion.getId()));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Empresa getEmpresa()
/* 121:    */   {
/* 122:176 */     if (this.empresa == null) {
/* 123:177 */       this.empresa = new Empresa();
/* 124:    */     }
/* 125:179 */     return this.empresa;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setEmpresa(Empresa empresa)
/* 129:    */   {
/* 130:189 */     this.empresa = empresa;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Date getFechaDesde()
/* 134:    */   {
/* 135:198 */     return this.fechaDesde;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFechaDesde(Date fechaDesde)
/* 139:    */   {
/* 140:208 */     this.fechaDesde = fechaDesde;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Date getFechaHasta()
/* 144:    */   {
/* 145:217 */     return this.fechaHasta;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setFechaHasta(Date fechaHasta)
/* 149:    */   {
/* 150:227 */     this.fechaHasta = fechaHasta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 154:    */   {
/* 155:236 */     if (this.cuentaBancariaOrganizacion == null) {
/* 156:237 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 157:    */     }
/* 158:239 */     return this.cuentaBancariaOrganizacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 162:    */   {
/* 163:249 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public FormaPago getFormaPago()
/* 167:    */   {
/* 168:258 */     if (this.formaPago == null) {
/* 169:259 */       this.formaPago = new FormaPago();
/* 170:    */     }
/* 171:261 */     return this.formaPago;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setFormaPago(FormaPago formaPago)
/* 175:    */   {
/* 176:271 */     this.formaPago = formaPago;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 180:    */   {
/* 181:280 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 182:281 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", false, null);
/* 183:    */     }
/* 184:283 */     return this.listaCuentaBancariaOrganizacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 188:    */   {
/* 189:292 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteCobroProtestosBean
 * JD-Core Version:    0.7.0.1
 */