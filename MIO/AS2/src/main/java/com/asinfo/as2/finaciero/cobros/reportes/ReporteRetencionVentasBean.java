/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
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
/*  33:    */ public class ReporteRetencionVentasBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioReporteVenta servicioReporteVenta;
/*  41:    */   @EJB
/*  42:    */   private ServicioSucursal servicioSucursal;
/*  43:    */   @EJB
/*  44:    */   protected ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  45:    */   private Empresa empresa;
/*  46:    */   private Date fechaDesde;
/*  47:    */   private Date fechaHasta;
/*  48:    */   private Sucursal sucursal;
/*  49:    */   private PuntoDeVenta puntoVenta;
/*  50: 74 */   private boolean facturaSinRetencion = false;
/*  51:    */   private List<Sucursal> listaSucursal;
/*  52:    */   private List<PuntoDeVenta> listaPuntoVenta;
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 86 */     return "reporteRetencionVentas";
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Map<String, Object> getReportParameters()
/*  60:    */   {
/*  61: 96 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  62: 97 */     reportParameters.put("ReportTitle", "Rentenciones en Ventas");
/*  63: 98 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  64: 99 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  65:100 */     reportParameters.put("p_sucursal", getSucursal() != null ? getSucursal().getNombre() : "Todos");
/*  66:101 */     reportParameters.put("p_puntoVenta", getPuntoVenta() != null ? getPuntoVenta().getNombre() : "Todos");
/*  67:    */     
/*  68:103 */     return reportParameters;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected JRDataSource getJRDataSource()
/*  72:    */   {
/*  73:114 */     List listaDatosReporte = new ArrayList();
/*  74:115 */     JRDataSource jrDataSource = null;
/*  75:116 */     String[] fields = null;
/*  76:    */     try
/*  77:    */     {
/*  78:118 */       listaDatosReporte = this.servicioReporteVenta.getReporteListaRetencionVentas(this.fechaDesde, this.fechaHasta, getEmpresa().getId(), 
/*  79:119 */         AppUtil.getOrganizacion().getId(), getSucursal(), isFacturaSinRetencion(), getPuntoVenta());
/*  80:120 */       if (isFacturaSinRetencion()) {
/*  81:121 */         fields = new String[] { "f_identificacion", "f_nombreComercial", "f_nombreFiscal", "f_fechaFactura", "f_numeroFactura", "f_subtotalFactura", "f_impuestoFactura", "f_descuentoFactura", "f_totalFactura" };
/*  82:    */       } else {
/*  83:124 */         fields = new String[] { "f_identificacion", "f_nombreComercial", "f_nombreFiscal", "f_fechaFactura", "f_numeroFactura", "f_subtotalFactura", "f_impuestoFactura", "f_descuentoFactura", "f_totalFactura", "f_numeroRetencion", "f_fechaRetencion", "f_descripcionRetencion", "f_valorRetencion", "f_valorIva", "f_valorFuente", "fecha_cobro", "asiento_cobro", "f_porcentajeRetencionIva", "f_porcentajeRetencionFuente", "f_secuenciaComprobanteRetencion", "f_autorizacionComprobanteRetencion" };
/*  84:    */       }
/*  85:129 */       jrDataSource = new QueryResultDataSource(listaDatosReporte, fields);
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:131 */       LOG.error("Error ", e);
/*  90:132 */       e.printStackTrace();
/*  91:133 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  92:    */     }
/*  93:136 */     return jrDataSource;
/*  94:    */   }
/*  95:    */   
/*  96:    */   @PostConstruct
/*  97:    */   public void init()
/*  98:    */   {
/*  99:141 */     Calendar calfechaDesde = Calendar.getInstance();
/* 100:142 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 101:143 */     this.fechaDesde = calfechaDesde.getTime();
/* 102:144 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String execute()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:155 */       if (this.fechaDesde == null) {
/* 110:156 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 111:    */       }
/* 112:158 */       if (this.fechaHasta == null) {
/* 113:159 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 114:    */       }
/* 115:161 */       super.prepareReport();
/* 116:    */     }
/* 117:    */     catch (JRException e)
/* 118:    */     {
/* 119:163 */       LOG.info("Error JRException");
/* 120:164 */       e.printStackTrace();
/* 121:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 122:    */     }
/* 123:    */     catch (IOException e)
/* 124:    */     {
/* 125:167 */       LOG.info("Error IOException");
/* 126:168 */       e.printStackTrace();
/* 127:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 128:    */     }
/* 129:172 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Empresa getEmpresa()
/* 133:    */   {
/* 134:183 */     if (this.empresa == null) {
/* 135:184 */       this.empresa = new Empresa();
/* 136:    */     }
/* 137:186 */     return this.empresa;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setEmpresa(Empresa empresa)
/* 141:    */   {
/* 142:196 */     this.empresa = empresa;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Date getFechaDesde()
/* 146:    */   {
/* 147:205 */     return this.fechaDesde;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFechaDesde(Date fechaDesde)
/* 151:    */   {
/* 152:215 */     this.fechaDesde = fechaDesde;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Date getFechaHasta()
/* 156:    */   {
/* 157:224 */     return this.fechaHasta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setFechaHasta(Date fechaHasta)
/* 161:    */   {
/* 162:234 */     this.fechaHasta = fechaHasta;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 166:    */   {
/* 167:238 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Sucursal getSucursal()
/* 171:    */   {
/* 172:242 */     return this.sucursal;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setSucursal(Sucursal sucursal)
/* 176:    */   {
/* 177:246 */     this.sucursal = sucursal;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<Sucursal> getListaSucursal()
/* 181:    */   {
/* 182:250 */     if (this.listaSucursal == null)
/* 183:    */     {
/* 184:251 */       Map<String, String> filters = new HashMap();
/* 185:252 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 186:253 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 187:    */     }
/* 188:255 */     return this.listaSucursal;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 192:    */   {
/* 193:259 */     this.listaSucursal = listaSucursal;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isFacturaSinRetencion()
/* 197:    */   {
/* 198:264 */     return this.facturaSinRetencion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setFacturaSinRetencion(boolean facturaSinRetencion)
/* 202:    */   {
/* 203:269 */     this.facturaSinRetencion = facturaSinRetencion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public PuntoDeVenta getPuntoVenta()
/* 207:    */   {
/* 208:276 */     return this.puntoVenta;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setPuntoVenta(PuntoDeVenta puntoVenta)
/* 212:    */   {
/* 213:284 */     this.puntoVenta = puntoVenta;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 217:    */   {
/* 218:291 */     return this.listaPuntoVenta;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 222:    */   {
/* 223:299 */     this.listaPuntoVenta = listaPuntoVenta;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void cargarPuntoVenta()
/* 227:    */   {
/* 228:303 */     if (this.sucursal != null)
/* 229:    */     {
/* 230:304 */       Map<String, String> filters = new HashMap();
/* 231:305 */       filters.put("sucursal.idSucursal", String.valueOf(this.sucursal.getId()));
/* 232:306 */       this.listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("nombre", true, filters);
/* 233:    */     }
/* 234:    */     else
/* 235:    */     {
/* 236:308 */       setListaPuntoVenta(new ArrayList());
/* 237:    */     }
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteRetencionVentasBean
 * JD-Core Version:    0.7.0.1
 */