/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Canal;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.MotivoPedidoCliente;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Transportista;
/*  12:    */ import com.asinfo.as2.entities.Zona;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  15:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  19:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  20:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  21:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioMotivoPedidoCliente;
/*  22:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  23:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidoDespachoFactura;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Calendar;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  35:    */ import net.sf.jasperreports.engine.JRException;
/*  36:    */ import org.apache.log4j.Logger;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class ReportePedidoClienteResumidoBean
/*  41:    */   extends AbstractBaseReportBean
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  44:    */   @EJB
/*  45:    */   private ServicioReportePedidoDespachoFactura servicioReportePedidoDespachoFactura;
/*  46:    */   @EJB
/*  47:    */   private ServicioEmpresa servicioEmpresa;
/*  48:    */   @EJB
/*  49:    */   private ServicioUsuario servicioUsuario;
/*  50:    */   @EJB
/*  51:    */   private ServicioCanal servicioCanal;
/*  52:    */   @EJB
/*  53:    */   private ServicioZona servicioZona;
/*  54:    */   @EJB
/*  55:    */   private ServicioSucursal servicioSucursal;
/*  56:    */   @EJB
/*  57:    */   private ServicioMotivoPedidoCliente servicioMotivoPedidoCliente;
/*  58:    */   @EJB
/*  59:    */   private ServicioTransportista servicioTransportista;
/*  60:    */   private Empresa empresa;
/*  61:    */   private int idVendedor;
/*  62:    */   private int idCanal;
/*  63:    */   private int idZona;
/*  64:    */   private int idSucursal;
/*  65:    */   private boolean anuladas;
/*  66:    */   private MotivoPedidoCliente motivoPedidoCliente;
/*  67:    */   private Transportista transportista;
/*  68:    */   private Date fechaDesde;
/*  69:    */   private Date fechaHasta;
/*  70:    */   private String numeroFacturaDesde;
/*  71:    */   private String numeroFacturaHasta;
/*  72:    */   private List<Empresa> listaClienteCombo;
/*  73:    */   private List<Zona> listaZonaCombo;
/*  74:    */   private List<Canal> listaCanalCombo;
/*  75:    */   private List<EntidadUsuario> listaVendedorCombo;
/*  76:    */   private List<Sucursal> listaSucursalCombo;
/*  77:    */   private List<MotivoPedidoCliente> listaMotivoPedidoCliente;
/*  78: 99 */   private boolean indicadorResumen = true;
/*  79:    */   private List<Transportista> listaTransportistaCombo;
/*  80:102 */   private boolean indicadorTomaFecha = true;
/*  81:    */   
/*  82:    */   protected JRDataSource getJRDataSource()
/*  83:    */   {
/*  84:114 */     List listaDatosReporte = new ArrayList();
/*  85:115 */     JRDataSource ds = null;
/*  86:116 */     listaDatosReporte = this.servicioReportePedidoDespachoFactura.getReportePedidoResumido(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/*  87:117 */       .getId(), this.idVendedor, this.anuladas, this.idCanal, this.idZona, this.idSucursal, this.motivoPedidoCliente, this.transportista, this.indicadorTomaFecha);
/*  88:    */     
/*  89:119 */     String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroPedido", "f_fechaPedido", "f_fechaDespacho", "f_nombreCliente", "f_identificacionCliente", "f_totalPedido", "f_descuentoPedido", "f_transportista", "f_impuestoPedido", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_empresaFinal" };
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:125 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  96:    */     
/*  97:127 */     return ds;
/*  98:    */   }
/*  99:    */   
/* 100:    */   @PostConstruct
/* 101:    */   public void init()
/* 102:    */   {
/* 103:132 */     Calendar calfechaDesde = Calendar.getInstance();
/* 104:133 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 105:134 */     this.fechaDesde = calfechaDesde.getTime();
/* 106:135 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected String getCompileFileName()
/* 110:    */   {
/* 111:146 */     if (this.indicadorResumen) {
/* 112:147 */       return "reportePedidoClienteResumido";
/* 113:    */     }
/* 114:149 */     return "reportePedidoClienteDetallado";
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected Map<String, Object> getReportParameters()
/* 118:    */   {
/* 119:162 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 120:163 */     reportParameters.put("ReportTitle", "Pedido Cliente Resumido");
/* 121:164 */     if (!this.indicadorResumen) {
/* 122:165 */       reportParameters.put("ReportTitle", "Pedido Cliente Detallado");
/* 123:    */     }
/* 124:167 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 125:168 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 126:169 */     reportParameters.put("NumeroDesde", this.numeroFacturaDesde);
/* 127:170 */     reportParameters.put("NumeroHasta", this.numeroFacturaHasta);
/* 128:171 */     reportParameters.put("Total", "Total");
/* 129:172 */     return reportParameters;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String execute()
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:182 */       validaDatos();
/* 137:183 */       super.prepareReport();
/* 138:    */     }
/* 139:    */     catch (JRException e)
/* 140:    */     {
/* 141:185 */       LOG.info("Error JRException");
/* 142:186 */       e.printStackTrace();
/* 143:187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 144:    */     }
/* 145:    */     catch (IOException e)
/* 146:    */     {
/* 147:189 */       LOG.info("Error IOException");
/* 148:190 */       e.printStackTrace();
/* 149:191 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 150:    */     }
/* 151:194 */     return null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void validaDatos()
/* 155:    */   {
/* 156:199 */     if (this.empresa == null)
/* 157:    */     {
/* 158:200 */       this.empresa = new Empresa();
/* 159:201 */       this.empresa.setId(-1);
/* 160:    */     }
/* 161:203 */     if (this.fechaDesde == null) {
/* 162:204 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 163:    */     }
/* 164:206 */     if (this.fechaHasta == null) {
/* 165:207 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Date getFechaDesde()
/* 170:    */   {
/* 171:217 */     return this.fechaDesde;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setFechaDesde(Date fechaDesde)
/* 175:    */   {
/* 176:227 */     this.fechaDesde = fechaDesde;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Date getFechaHasta()
/* 180:    */   {
/* 181:236 */     return this.fechaHasta;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setFechaHasta(Date fechaHasta)
/* 185:    */   {
/* 186:246 */     this.fechaHasta = fechaHasta;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getNumeroFacturaDesde()
/* 190:    */   {
/* 191:255 */     return this.numeroFacturaDesde;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setNumeroFacturaDesde(String numeroFacturaDesde)
/* 195:    */   {
/* 196:265 */     this.numeroFacturaDesde = numeroFacturaDesde;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getNumeroFacturaHasta()
/* 200:    */   {
/* 201:274 */     return this.numeroFacturaHasta;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setNumeroFacturaHasta(String numeroFacturaHasta)
/* 205:    */   {
/* 206:284 */     this.numeroFacturaHasta = numeroFacturaHasta;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Empresa getEmpresa()
/* 210:    */   {
/* 211:293 */     if (this.empresa == null)
/* 212:    */     {
/* 213:294 */       this.empresa = new Empresa();
/* 214:295 */       this.empresa.setId(0);
/* 215:    */     }
/* 216:297 */     return this.empresa;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setEmpresa(Empresa empresa)
/* 220:    */   {
/* 221:307 */     this.empresa = empresa;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public int getIdVendedor()
/* 225:    */   {
/* 226:316 */     return this.idVendedor;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setIdVendedor(int idVendedor)
/* 230:    */   {
/* 231:326 */     this.idVendedor = idVendedor;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getIdCanal()
/* 235:    */   {
/* 236:335 */     return this.idCanal;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setIdCanal(int idCanal)
/* 240:    */   {
/* 241:345 */     this.idCanal = idCanal;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public int getIdZona()
/* 245:    */   {
/* 246:354 */     return this.idZona;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setIdZona(int idZona)
/* 250:    */   {
/* 251:364 */     this.idZona = idZona;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public int getIdSucursal()
/* 255:    */   {
/* 256:373 */     return this.idSucursal;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setIdSucursal(int idSucursal)
/* 260:    */   {
/* 261:383 */     this.idSucursal = idSucursal;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean isIndicadorResumen()
/* 265:    */   {
/* 266:392 */     return this.indicadorResumen;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 270:    */   {
/* 271:402 */     this.indicadorResumen = indicadorResumen;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 275:    */   {
/* 276:406 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<Empresa> getListaClienteCombo()
/* 280:    */   {
/* 281:415 */     if (this.listaClienteCombo == null) {
/* 282:416 */       this.listaClienteCombo = this.servicioEmpresa.obtenerClientes();
/* 283:    */     }
/* 284:418 */     return this.listaClienteCombo;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setListaClienteCombo(List<Empresa> listaClienteCombo)
/* 288:    */   {
/* 289:428 */     this.listaClienteCombo = listaClienteCombo;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<Zona> getListaZonaCombo()
/* 293:    */   {
/* 294:437 */     if (this.listaZonaCombo == null) {
/* 295:438 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 296:    */     }
/* 297:440 */     return this.listaZonaCombo;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<MotivoPedidoCliente> getListaMotivoPedidoCliente()
/* 301:    */   {
/* 302:449 */     if (this.listaMotivoPedidoCliente == null) {
/* 303:450 */       this.listaMotivoPedidoCliente = this.servicioMotivoPedidoCliente.obtenerListaCombo("nombre", true, null);
/* 304:    */     }
/* 305:452 */     return this.listaMotivoPedidoCliente;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 309:    */   {
/* 310:462 */     this.listaZonaCombo = listaZonaCombo;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public List<Canal> getListaCanalCombo()
/* 314:    */   {
/* 315:471 */     if (this.listaCanalCombo == null) {
/* 316:472 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 317:    */     }
/* 318:474 */     return this.listaCanalCombo;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 322:    */   {
/* 323:484 */     this.listaCanalCombo = listaCanalCombo;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public List<EntidadUsuario> getListaVendedorCombo()
/* 327:    */   {
/* 328:493 */     if (this.listaVendedorCombo == null) {
/* 329:494 */       this.listaVendedorCombo = this.servicioUsuario.getEntidadUsuario(AppUtil.getOrganizacion().getId(), true, AppUtil.getSucursal());
/* 330:    */     }
/* 331:496 */     return this.listaVendedorCombo;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setListaVendedorCombo(List<EntidadUsuario> listaVendedorCombo)
/* 335:    */   {
/* 336:506 */     this.listaVendedorCombo = listaVendedorCombo;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public List<Sucursal> getListaSucursalCombo()
/* 340:    */   {
/* 341:515 */     if (this.listaSucursalCombo == null) {
/* 342:516 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 343:    */     }
/* 344:518 */     return this.listaSucursalCombo;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 348:    */   {
/* 349:528 */     if (listaSucursalCombo == null) {
/* 350:529 */       listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 351:    */     }
/* 352:531 */     this.listaSucursalCombo = listaSucursalCombo;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public boolean isAnuladas()
/* 356:    */   {
/* 357:535 */     return this.anuladas;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setAnuladas(boolean anuladas)
/* 361:    */   {
/* 362:539 */     this.anuladas = anuladas;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public MotivoPedidoCliente getMotivoPedidoCliente()
/* 366:    */   {
/* 367:543 */     return this.motivoPedidoCliente;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setMotivoPedidoCliente(MotivoPedidoCliente motivoPedidoCliente)
/* 371:    */   {
/* 372:547 */     this.motivoPedidoCliente = motivoPedidoCliente;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public List<Transportista> getListaTransportistaCombo()
/* 376:    */   {
/* 377:551 */     if (this.listaTransportistaCombo == null)
/* 378:    */     {
/* 379:552 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 380:553 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("nombre", true, filtros);
/* 381:    */     }
/* 382:555 */     return this.listaTransportistaCombo;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Transportista getTransportista()
/* 386:    */   {
/* 387:559 */     return this.transportista;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setTransportista(Transportista transportista)
/* 391:    */   {
/* 392:563 */     this.transportista = transportista;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public boolean isIndicadorTomaFecha()
/* 396:    */   {
/* 397:567 */     return this.indicadorTomaFecha;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setIndicadorTomaFecha(boolean indicadorFecha)
/* 401:    */   {
/* 402:571 */     this.indicadorTomaFecha = indicadorFecha;
/* 403:    */   }
/* 404:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidoClienteResumidoBean
 * JD-Core Version:    0.7.0.1
 */