/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*  11:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteNotaCreditoCliente;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.JRException;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteNotaCreditoClienteBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 6853959903254538972L;
/*  37:    */   @EJB
/*  38:    */   private ServicioReporteNotaCreditoCliente servicioReporteNotaCreditoCliente;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   @EJB
/*  42:    */   private ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  43:    */   @EJB
/*  44:    */   private ServicioSucursal servicioSucursal;
/*  45:    */   private Empresa empresa;
/*  46:    */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente;
/*  47:    */   private Date fechaDesde;
/*  48:    */   private Date fechaHasta;
/*  49:    */   private boolean indicadorResumen;
/*  50:    */   private Sucursal sucursal;
/*  51:    */   private List<Sucursal> listaSucursal;
/*  52:    */   
/*  53:    */   protected JRDataSource getJRDataSource()
/*  54:    */   {
/*  55: 62 */     List listaDatosReporte = new ArrayList();
/*  56: 63 */     JRDataSource ds = null;
/*  57:    */     try
/*  58:    */     {
/*  59:    */       String[] fields;
/*  60:    */       String[] fields;
/*  61: 68 */       if (this.indicadorResumen)
/*  62:    */       {
/*  63: 70 */         listaDatosReporte = this.servicioReporteNotaCreditoCliente.getListaNotaCreditoCliente(this.fechaDesde, this.fechaHasta, this.empresa, this.motivoNotaCreditoCliente, 
/*  64: 71 */           AppUtil.getOrganizacion().getId(), this.sucursal);
/*  65: 72 */         fields = new String[] { "identificacion", "nombreComercial", "nombreFiscal", "motivoNotaCredito", "documento", "total", "ventas" };
/*  66:    */       }
/*  67:    */       else
/*  68:    */       {
/*  69: 76 */         listaDatosReporte = this.servicioReporteNotaCreditoCliente.getReporteNotasCredito(this.fechaDesde, this.fechaHasta, this.empresa, this.motivoNotaCreditoCliente, 
/*  70: 77 */           AppUtil.getOrganizacion().getId(), this.sucursal);
/*  71: 78 */         fields = new String[] { "fechaNotaCredito", "numeroNotaCredito", "fechaFactura", "numeroFactura", "identificacion", "nombreComercial", "nombreFiscal", "motivoNotaCredito", "documento", "totalNotaCredito", "totalFactura", "f_codigoDF", "f_subtotalNotaCredito", "f_impuestoNotaCredito", "f_subtotalFacturaPadre", "f_impuestoFacturaPadre", "f_referencia2", "f_consignatario", "fp_autorizacion", "f_autorizacion", "doc_transp", "dae_info" };
/*  72:    */       }
/*  73: 83 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77: 85 */       e.printStackTrace();
/*  78:    */     }
/*  79: 87 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85: 93 */     Calendar calfechaDesde = Calendar.getInstance();
/*  86: 94 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  87: 95 */     this.fechaDesde = calfechaDesde.getTime();
/*  88: 96 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected String getCompileFileName()
/*  92:    */   {
/*  93:101 */     if (this.indicadorResumen) {
/*  94:102 */       return "reporteNotaCreditoClientePorMotivo";
/*  95:    */     }
/*  96:104 */     return "reporteNotaCreditoClientePorMotivoDetallado";
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected Map<String, Object> getReportParameters()
/* 100:    */   {
/* 101:111 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 102:112 */     reportParameters.put("ReportTitle", "Nota Credito Cliente");
/* 103:113 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 104:114 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 105:115 */     return reportParameters;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String execute()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:123 */       validaDatos();
/* 113:124 */       super.prepareReport();
/* 114:    */     }
/* 115:    */     catch (JRException e)
/* 116:    */     {
/* 117:127 */       LOG.info("Error JRException");
/* 118:128 */       e.printStackTrace();
/* 119:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:    */     catch (IOException e)
/* 122:    */     {
/* 123:131 */       LOG.info("Error IOException");
/* 124:132 */       e.printStackTrace();
/* 125:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 126:    */     }
/* 127:136 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 131:    */   {
/* 132:140 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void validaDatos()
/* 136:    */   {
/* 137:144 */     if (this.fechaDesde == null) {
/* 138:145 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 139:    */     }
/* 140:147 */     if (this.fechaHasta == null) {
/* 141:148 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Empresa getEmpresa()
/* 146:    */   {
/* 147:158 */     return this.empresa;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setEmpresa(Empresa empresa)
/* 151:    */   {
/* 152:168 */     this.empresa = empresa;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/* 156:    */   {
/* 157:177 */     return this.motivoNotaCreditoCliente;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 161:    */   {
/* 162:187 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Date getFechaDesde()
/* 166:    */   {
/* 167:196 */     return this.fechaDesde;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFechaDesde(Date fechaDesde)
/* 171:    */   {
/* 172:206 */     this.fechaDesde = fechaDesde;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Date getFechaHasta()
/* 176:    */   {
/* 177:215 */     return this.fechaHasta;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setFechaHasta(Date fechaHasta)
/* 181:    */   {
/* 182:225 */     this.fechaHasta = fechaHasta;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isIndicadorResumen()
/* 186:    */   {
/* 187:234 */     return this.indicadorResumen;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 191:    */   {
/* 192:244 */     this.indicadorResumen = indicadorResumen;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<MotivoNotaCreditoCliente> getListaMotivoNotaCreditoCliente()
/* 196:    */   {
/* 197:253 */     List<MotivoNotaCreditoCliente> lista = new ArrayList();
/* 198:254 */     lista = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("nombre", true, null);
/* 199:255 */     return lista;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Sucursal getSucursal()
/* 203:    */   {
/* 204:259 */     return this.sucursal;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setSucursal(Sucursal sucursal)
/* 208:    */   {
/* 209:263 */     this.sucursal = sucursal;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Sucursal> getListaSucursal()
/* 213:    */   {
/* 214:267 */     if (this.listaSucursal == null)
/* 215:    */     {
/* 216:268 */       Map<String, String> filters = new HashMap();
/* 217:269 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 218:270 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 219:    */     }
/* 220:272 */     return this.listaSucursal;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 224:    */   {
/* 225:276 */     this.listaSucursal = listaSucursal;
/* 226:    */   }
/* 227:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteNotaCreditoClienteBean
 * JD-Core Version:    0.7.0.1
 */