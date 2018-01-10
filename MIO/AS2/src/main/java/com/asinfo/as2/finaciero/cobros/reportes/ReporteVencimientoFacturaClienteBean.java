/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteAnticipoCliente;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  10:    */ import com.asinfo.as2.entities.Recaudador;
/*  11:    */ import com.asinfo.as2.entities.Subempresa;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.Zona;
/*  14:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  15:    */ import com.asinfo.as2.enumeraciones.ReporteEnvioMailsEnum;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  21:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  22:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  33:    */ import net.sf.jasperreports.engine.JRException;
/*  34:    */ import net.sf.jasperreports.engine.JasperExportManager;
/*  35:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  36:    */ import org.apache.log4j.Logger;
/*  37:    */ import org.primefaces.component.datatable.DataTable;
/*  38:    */ import org.primefaces.context.RequestContext;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class ReporteVencimientoFacturaClienteBean
/*  43:    */   extends AbstractClientReportBean
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 3813079431949948097L;
/*  46:    */   @EJB
/*  47:    */   private ServicioReporteVenta servicioReporteVenta;
/*  48:    */   @EJB
/*  49:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/*  50:    */   private List<Empresa> listaEmpresaEnvioEmail;
/*  51:    */   private List<Empresa> listaEmpresaEnvioEmailFiltrado;
/*  52:    */   private DataTable dtEmpresaEnvioEmail;
/*  53: 73 */   private TipoReporte tipoReporte = TipoReporte.REPORTE_VENCIMIENTO_FACTURA_DETALLADO;
/*  54:    */   private List<SelectItem> listaTipoReporte;
/*  55:    */   
/*  56:    */   static enum TipoReporte
/*  57:    */   {
/*  58: 77 */     REPORTE_VENCIMIENTO_FACTURA_DETALLADO("Reporte Vencimiento Factura Detallado"),  REPORTE_VENCIMIENTO_FACTURA_DETALLADO_POSFECHADOS("Reporte Vencimiento Factura Detallado con Posfechados"),  REPORTE_VENCIMIENTO_FACTURA_RESUMIDO("Reporte Vencimiento Factura Resumido");
/*  59:    */     
/*  60:    */     private String nombre;
/*  61:    */     
/*  62:    */     private TipoReporte(String nombre)
/*  63:    */     {
/*  64: 83 */       this.nombre = nombre;
/*  65:    */     }
/*  66:    */     
/*  67:    */     public String getNombre()
/*  68:    */     {
/*  69: 87 */       return this.nombre;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public void setNombre(String nombre)
/*  73:    */     {
/*  74: 91 */       this.nombre = nombre;
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected String getCompileFileName()
/*  79:    */   {
/*  80:102 */     if (TipoReporte.REPORTE_VENCIMIENTO_FACTURA_RESUMIDO.equals(this.tipoReporte)) {
/*  81:103 */       return "reporteVencimientoFacturaResumen";
/*  82:    */     }
/*  83:104 */     if (TipoReporte.REPORTE_VENCIMIENTO_FACTURA_DETALLADO.equals(this.tipoReporte)) {
/*  84:105 */       return "reporteVencimientoFactura";
/*  85:    */     }
/*  86:107 */     return "reporteVencimientoFacturaConPosfechados";
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Map<String, Object> getReportParameters()
/*  90:    */   {
/*  91:118 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  92:119 */     if (TipoReporte.REPORTE_VENCIMIENTO_FACTURA_RESUMIDO.equals(this.tipoReporte)) {
/*  93:120 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_titulo_reporte_vencimientos_resumido"));
/*  94:    */     } else {
/*  95:122 */       reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_titulo_reporte_vencimientos"));
/*  96:    */     }
/*  97:124 */     if (this.fechaHasta == null) {
/*  98:125 */       this.fechaHasta = new Date();
/*  99:    */     }
/* 100:127 */     reportParameters.put("FechaHasta", this.fechaHasta);
/* 101:128 */     reportParameters.put("total", "Total");
/* 102:129 */     reportParameters.put("reporteCliente", Boolean.TRUE);
/* 103:130 */     reportParameters.put("p_agente_comercial", (this.agenteComercial != null) && (this.agenteComercial.getId() != 0) ? this.agenteComercial.getNombre1() + " " + this.agenteComercial
/* 104:131 */       .getNombre2() : "Todos");
/* 105:132 */     reportParameters.put("p_recaudador", (this.recaudador != null) && (this.recaudador.getId() != 0) ? this.recaudador.getNombre() : "Todos");
/* 106:133 */     reportParameters.put("p_zona", this.zona != null ? this.zona.getNombre() : "Todos");
/* 107:134 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 108:135 */     reportParameters.put("p_punto_venta", this.puntoVenta != null ? this.puntoVenta.getNombre() : "Todos");
/* 109:136 */     reportParameters.put("p_subempresa", (this.subempresa != null) && (this.subempresa.getId() != 0) ? this.subempresa.getEmpresaFinal() : "Todos");
/* 110:137 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? this.categoriaEmpresa.getNombre() : "Todos");
/* 111:138 */     List<ReporteAnticipoCliente> listaAnticipos = this.servicioAnticipoCliente.obtenerAnticiposClientes(this.categoriaEmpresa, 
/* 112:139 */       getEmpresa() != null ? Integer.valueOf(getEmpresa().getIdEmpresa()) : null, this.fechaHasta, Integer.valueOf(AppUtil.getOrganizacion().getId()));
/* 113:140 */     if (listaAnticipos != null) {
/* 114:141 */       reportParameters.put("listaAnticipos", listaAnticipos);
/* 115:    */     }
/* 116:143 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 117:    */     
/* 118:    */ 
/* 119:146 */     return reportParameters;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected JRDataSource getJRDataSource()
/* 123:    */   {
/* 124:157 */     List listaDatosReporte = new ArrayList();
/* 125:158 */     JRDataSource ds = null;
/* 126:    */     try
/* 127:    */     {
/* 128:161 */       validarFiltrosNulos();
/* 129:162 */       boolean indicadorMostrarPosfechados = TipoReporte.REPORTE_VENCIMIENTO_FACTURA_DETALLADO_POSFECHADOS.equals(this.tipoReporte);
/* 130:163 */       listaDatosReporte = this.servicioReporteVenta.getVencimientos(getEmpresa().getIdEmpresa(), this.fechaHasta, getRecaudador().getId(), 
/* 131:164 */         AppUtil.getOrganizacion().getId(), getSubempresa().getId(), getAgenteComercial().getIdUsuario(), this.sucursal, this.puntoVenta, this.zona, this.categoriaEmpresa, indicadorMostrarPosfechados);
/* 132:    */       
/* 133:166 */       String[] fields = { "f_codigo", "f_nombreFiscal", "f_identificacion", "f_numeroFactura", "f_fechaFactura", "f_fechaVencimiento", "f_saldoVencido", "f_saldoPorVencer", "f_saldo", "f_codigoDocumento", "f_telefono", "f_direccion", "f_contacto", "f_condicionPagoCliente", "f_formaPagoCliente", "f_creditoMaximoCliente", "f_creditoUtilizadoCliente", "f_descripcionCliente", "numero_packing", "f_agenteComercial", "f_subempresa", "proyecto_codigo", "proyecto_nombre", "f_nombreBancoPosfechado", "f_numeroChequePosfechado", "f_fechaCobroPosfechado", "f_fechaACobrarPosfechado", "f_valorPosfechado" };
/* 134:    */       
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:171 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 139:    */     }
/* 140:    */     catch (ExcepcionAS2 e)
/* 141:    */     {
/* 142:173 */       LOG.info("Error " + e);
/* 143:174 */       e.printStackTrace();
/* 144:    */     }
/* 145:176 */     return ds;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String execute()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:186 */       if (this.fechaHasta == null) {
/* 153:187 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 154:    */       }
/* 155:189 */       super.prepareReport();
/* 156:    */     }
/* 157:    */     catch (JRException e)
/* 158:    */     {
/* 159:191 */       LOG.info("Error JRException");
/* 160:192 */       e.printStackTrace();
/* 161:193 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 162:    */     }
/* 163:    */     catch (IOException e)
/* 164:    */     {
/* 165:195 */       LOG.info("Error IOException");
/* 166:196 */       e.printStackTrace();
/* 167:197 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 168:    */     }
/* 169:200 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   private void validarFiltrosNulos()
/* 173:    */   {
/* 174:204 */     if (null == this.empresa) {
/* 175:205 */       this.empresa = new Empresa();
/* 176:    */     }
/* 177:207 */     if (null == this.recaudador) {
/* 178:208 */       this.recaudador = new Recaudador();
/* 179:    */     }
/* 180:210 */     if (null == this.subempresa) {
/* 181:211 */       this.subempresa = new Subempresa();
/* 182:    */     }
/* 183:213 */     if (null == this.agenteComercial) {
/* 184:214 */       this.agenteComercial = new EntidadUsuario();
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void cargarEmpresasEnvioEmail()
/* 189:    */   {
/* 190:219 */     validarFiltrosNulos();
/* 191:220 */     this.listaEmpresaEnvioEmail = this.servicioReporteVenta.getEmpresasEnvioEmailVencimiento(getEmpresa().getIdEmpresa(), this.fechaHasta, getRecaudador()
/* 192:221 */       .getId(), AppUtil.getOrganizacion().getId(), getSubempresa().getId(), getAgenteComercial().getIdUsuario(), this.sucursal, this.puntoVenta, this.zona, this.categoriaEmpresa);
/* 193:224 */     for (Empresa empresaEnvio : this.listaEmpresaEnvioEmail)
/* 194:    */     {
/* 195:225 */       empresaEnvio.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 196:226 */       String envioEmails = this.servicioEmpresa.cargarMails(empresaEnvio, null, ReporteEnvioMailsEnum.INDICADOR_VENCIMIENTO_FACTURA_CLIENTE);
/* 197:227 */       empresaEnvio.setEnvioEmails(envioEmails);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<Empresa> getListaEmpresaEnvioEmail()
/* 202:    */   {
/* 203:232 */     List<Empresa> listaEmpresa = new ArrayList();
/* 204:233 */     if (this.listaEmpresaEnvioEmail != null) {
/* 205:234 */       for (Empresa empresaEnvio : this.listaEmpresaEnvioEmail) {
/* 206:235 */         if (!empresaEnvio.isEliminado()) {
/* 207:236 */           listaEmpresa.add(empresaEnvio);
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:240 */     return listaEmpresa;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void eliminarEmpresaEnvioEmail()
/* 215:    */   {
/* 216:244 */     Empresa empresaEnvio = (Empresa)this.dtEmpresaEnvioEmail.getRowData();
/* 217:245 */     empresaEnvio.setEliminado(true);
/* 218:246 */     this.dtEmpresaEnvioEmail.reset();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void enviarEmails()
/* 222:    */   {
/* 223:250 */     Empresa empresaOriginal = getEmpresa();
/* 224:251 */     List<String> listaMensajeError = new ArrayList();
/* 225:252 */     List<Empresa> listaEnviar = getListaEmpresaEnvioEmailFiltrado();
/* 226:253 */     if (listaEnviar == null) {
/* 227:254 */       listaEnviar = getListaEmpresaEnvioEmail();
/* 228:    */     }
/* 229:256 */     for (Empresa empresaEnviar : listaEnviar) {
/* 230:    */       try
/* 231:    */       {
/* 232:258 */         this.empresa = empresaEnviar;
/* 233:259 */         JasperPrint jasperPrint = prepareReport(getJRDataSource());
/* 234:260 */         byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
/* 235:261 */         this.servicioReporteVenta.enviarEmail(this.fechaHasta, empresaEnviar.getEnvioEmails(), empresaEnviar, pdf);
/* 236:262 */         empresaEnviar.setEliminado(true);
/* 237:    */       }
/* 238:    */       catch (Exception e)
/* 239:    */       {
/* 240:264 */         listaMensajeError.add(e.getMessage());
/* 241:    */       }
/* 242:    */     }
/* 243:268 */     if (!listaMensajeError.isEmpty())
/* 244:    */     {
/* 245:269 */       for (String mensaje : listaMensajeError) {
/* 246:270 */         addErrorMessage(mensaje);
/* 247:    */       }
/* 248:    */     }
/* 249:    */     else
/* 250:    */     {
/* 251:273 */       RequestContext context = RequestContext.getCurrentInstance();
/* 252:274 */       context.execute("PF('enviarEmailsDialog').hide();");
/* 253:    */     }
/* 254:276 */     this.dtEmpresaEnvioEmail.reset();
/* 255:277 */     this.empresa = empresaOriginal;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public DataTable getDtEmpresaEnvioEmail()
/* 259:    */   {
/* 260:281 */     return this.dtEmpresaEnvioEmail;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDtEmpresaEnvioEmail(DataTable dtEmpresaEnvioEmail)
/* 264:    */   {
/* 265:285 */     this.dtEmpresaEnvioEmail = dtEmpresaEnvioEmail;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List<Empresa> getListaEmpresaEnvioEmailFiltrado()
/* 269:    */   {
/* 270:289 */     return this.listaEmpresaEnvioEmailFiltrado;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setListaEmpresaEnvioEmailFiltrado(List<Empresa> listaEmpresaEnvioEmailFiltrado)
/* 274:    */   {
/* 275:293 */     this.listaEmpresaEnvioEmailFiltrado = listaEmpresaEnvioEmailFiltrado;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public TipoReporte getTipoReporte()
/* 279:    */   {
/* 280:297 */     return this.tipoReporte;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 284:    */   {
/* 285:301 */     this.tipoReporte = tipoReporte;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<SelectItem> getListaTipoReporte()
/* 289:    */   {
/* 290:305 */     if (this.listaTipoReporte == null)
/* 291:    */     {
/* 292:306 */       this.listaTipoReporte = new ArrayList();
/* 293:307 */       for (TipoReporte tipoReporte : TipoReporte.values())
/* 294:    */       {
/* 295:308 */         SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 296:309 */         this.listaTipoReporte.add(item);
/* 297:    */       }
/* 298:    */     }
/* 299:313 */     return this.listaTipoReporte;
/* 300:    */   }
/* 301:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteVencimientoFacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */