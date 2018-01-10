/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Canal;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.Zona;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  12:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoVentaEnum;
/*  14:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  20:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  21:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.RequestScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @RequestScoped
/*  37:    */ public class ReporteFacturacionResumidoClienteComercializadoraBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  41:    */   @EJB
/*  42:    */   private ServicioReporteVenta servicioReporteVenta;
/*  43:    */   @EJB
/*  44:    */   private ServicioEmpresa servicioEmpresa;
/*  45:    */   @EJB
/*  46:    */   private ServicioUsuario servicioUsuario;
/*  47:    */   @EJB
/*  48:    */   private ServicioCanal servicioCanal;
/*  49:    */   @EJB
/*  50:    */   private ServicioZona servicioZona;
/*  51:    */   @EJB
/*  52:    */   private ServicioSucursal servicioSucursal;
/*  53:    */   private Empresa empresa;
/*  54:    */   private int idVendedor;
/*  55:    */   private int idCanal;
/*  56:    */   private int idZona;
/*  57:    */   private Sucursal sucursal;
/*  58:    */   private boolean anuladas;
/*  59:    */   private Date fechaDesde;
/*  60:    */   private Date fechaHasta;
/*  61:    */   private String numeroFacturaDesde;
/*  62:    */   private String numeroFacturaHasta;
/*  63:    */   private List<Empresa> listaClienteCombo;
/*  64:    */   private List<Zona> listaZonaCombo;
/*  65:    */   private List<Canal> listaCanalCombo;
/*  66:    */   private List<EntidadUsuario> listaVendedorCombo;
/*  67:    */   private List<Sucursal> listaSucursalCombo;
/*  68:    */   private List<SelectItem> listaTipoVenta;
/*  69: 91 */   private boolean indicadorResumen = true;
/*  70:    */   private boolean saldoInicial;
/*  71: 94 */   private TipoVentaEnum tipoVenta = TipoVentaEnum.LOCAL;
/*  72:    */   private boolean indicadorTipoReporte;
/*  73:    */   
/*  74:    */   private static enum enumTipoReporte
/*  75:    */   {
/*  76: 98 */     PRODUCTO,  FACTURA;
/*  77:    */     
/*  78:    */     private enumTipoReporte() {}
/*  79:    */   }
/*  80:    */   
/*  81:102 */   private enumTipoReporte tipoReporte = enumTipoReporte.FACTURA;
/*  82:    */   private List<SelectItem> listaTipoReporte;
/*  83:    */   
/*  84:    */   protected JRDataSource getJRDataSource()
/*  85:    */   {
/*  86:114 */     List listaDatosReporte = new ArrayList();
/*  87:115 */     JRDataSource ds = null;
/*  88:    */     
/*  89:117 */     listaDatosReporte = this.servicioReporteVenta.getListaReporteFacturacionResumido(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, this.empresa
/*  90:118 */       .getId(), this.idVendedor, this.anuladas, this.idCanal, this.idZona, this.sucursal, null, this.tipoVenta, this.saldoInicial, isIndicadorTipoReporte(), 
/*  91:119 */       AppUtil.getOrganizacion().getId(), DocumentoBase.FACTURA_CLIENTE, 0, null);
/*  92:    */     
/*  93:121 */     String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroFactura", "f_fechaFactura", "f_nombreCliente", "f_identificacionCliente", "f_totalFactura", "f_descuentoFactura", "f_impuestoFactura", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_tipoEstructuraPrecio", "f_proyecto", "f_descripcion_factura" };
/*  94:    */     
/*  95:    */ 
/*  96:    */ 
/*  97:125 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  98:    */     
/*  99:127 */     return ds;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected String getCompileFileName()
/* 103:    */   {
/* 104:137 */     return "reporteFacturacionComercializadoraResumido";
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected Map<String, Object> getReportParameters()
/* 108:    */   {
/* 109:147 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 110:148 */     reportParameters.put("ReportTitle", "Facturacion resumido");
/* 111:149 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 112:150 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 113:151 */     reportParameters.put("NumeroDesde", this.numeroFacturaDesde);
/* 114:152 */     reportParameters.put("NumeroHasta", this.numeroFacturaHasta);
/* 115:153 */     reportParameters.put("Total", "Total");
/* 116:154 */     return reportParameters;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String execute()
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:164 */       validaDatos();
/* 124:165 */       super.prepareReport();
/* 125:    */     }
/* 126:    */     catch (JRException e)
/* 127:    */     {
/* 128:167 */       LOG.info("Error JRException");
/* 129:168 */       e.printStackTrace();
/* 130:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 131:    */     }
/* 132:    */     catch (IOException e)
/* 133:    */     {
/* 134:171 */       LOG.info("Error IOException");
/* 135:172 */       e.printStackTrace();
/* 136:173 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 137:    */     }
/* 138:176 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void validaDatos()
/* 142:    */   {
/* 143:181 */     if (this.empresa == null)
/* 144:    */     {
/* 145:182 */       this.empresa = new Empresa();
/* 146:183 */       this.empresa.setId(-1);
/* 147:    */     }
/* 148:185 */     if (this.fechaDesde == null) {
/* 149:186 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 150:    */     }
/* 151:188 */     if (this.fechaHasta == null) {
/* 152:189 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Date getFechaDesde()
/* 157:    */   {
/* 158:199 */     return this.fechaDesde;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setFechaDesde(Date fechaDesde)
/* 162:    */   {
/* 163:209 */     this.fechaDesde = fechaDesde;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Date getFechaHasta()
/* 167:    */   {
/* 168:218 */     return this.fechaHasta;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setFechaHasta(Date fechaHasta)
/* 172:    */   {
/* 173:228 */     this.fechaHasta = fechaHasta;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getNumeroFacturaDesde()
/* 177:    */   {
/* 178:237 */     return this.numeroFacturaDesde;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setNumeroFacturaDesde(String numeroFacturaDesde)
/* 182:    */   {
/* 183:247 */     this.numeroFacturaDesde = numeroFacturaDesde;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getNumeroFacturaHasta()
/* 187:    */   {
/* 188:256 */     return this.numeroFacturaHasta;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setNumeroFacturaHasta(String numeroFacturaHasta)
/* 192:    */   {
/* 193:266 */     this.numeroFacturaHasta = numeroFacturaHasta;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Empresa getEmpresa()
/* 197:    */   {
/* 198:275 */     if (this.empresa == null)
/* 199:    */     {
/* 200:276 */       this.empresa = new Empresa();
/* 201:277 */       this.empresa.setId(0);
/* 202:    */     }
/* 203:279 */     return this.empresa;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setEmpresa(Empresa empresa)
/* 207:    */   {
/* 208:289 */     this.empresa = empresa;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public int getIdVendedor()
/* 212:    */   {
/* 213:298 */     return this.idVendedor;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setIdVendedor(int idVendedor)
/* 217:    */   {
/* 218:308 */     this.idVendedor = idVendedor;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public int getIdCanal()
/* 222:    */   {
/* 223:317 */     return this.idCanal;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIdCanal(int idCanal)
/* 227:    */   {
/* 228:327 */     this.idCanal = idCanal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getIdZona()
/* 232:    */   {
/* 233:336 */     return this.idZona;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIdZona(int idZona)
/* 237:    */   {
/* 238:346 */     this.idZona = idZona;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Sucursal getSucursal()
/* 242:    */   {
/* 243:355 */     return this.sucursal;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setSucursal(Sucursal Sucursal)
/* 247:    */   {
/* 248:365 */     this.sucursal = Sucursal;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean isIndicadorResumen()
/* 252:    */   {
/* 253:374 */     return this.indicadorResumen;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 257:    */   {
/* 258:384 */     this.indicadorResumen = indicadorResumen;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 262:    */   {
/* 263:388 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<Empresa> getListaClienteCombo()
/* 267:    */   {
/* 268:397 */     if (this.listaClienteCombo == null) {
/* 269:398 */       this.listaClienteCombo = this.servicioEmpresa.obtenerClientes();
/* 270:    */     }
/* 271:400 */     return this.listaClienteCombo;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaClienteCombo(List<Empresa> listaClienteCombo)
/* 275:    */   {
/* 276:410 */     this.listaClienteCombo = listaClienteCombo;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<Zona> getListaZonaCombo()
/* 280:    */   {
/* 281:419 */     if (this.listaZonaCombo == null) {
/* 282:420 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 283:    */     }
/* 284:422 */     return this.listaZonaCombo;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 288:    */   {
/* 289:432 */     this.listaZonaCombo = listaZonaCombo;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<Canal> getListaCanalCombo()
/* 293:    */   {
/* 294:441 */     if (this.listaCanalCombo == null) {
/* 295:442 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 296:    */     }
/* 297:444 */     return this.listaCanalCombo;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 301:    */   {
/* 302:454 */     this.listaCanalCombo = listaCanalCombo;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public List<EntidadUsuario> getListaVendedorCombo()
/* 306:    */   {
/* 307:463 */     if (this.listaVendedorCombo == null) {
/* 308:464 */       this.listaVendedorCombo = this.servicioUsuario.obtenerListaCombo("nombre1", true, null);
/* 309:    */     }
/* 310:466 */     return this.listaVendedorCombo;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setListaVendedorCombo(List<EntidadUsuario> listaVendedorCombo)
/* 314:    */   {
/* 315:476 */     this.listaVendedorCombo = listaVendedorCombo;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public List<Sucursal> getListaSucursalCombo()
/* 319:    */   {
/* 320:485 */     if (this.listaSucursalCombo == null) {
/* 321:486 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 322:    */     }
/* 323:488 */     return this.listaSucursalCombo;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 327:    */   {
/* 328:498 */     if (listaSucursalCombo == null) {
/* 329:499 */       listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 330:    */     }
/* 331:501 */     this.listaSucursalCombo = listaSucursalCombo;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean isAnuladas()
/* 335:    */   {
/* 336:505 */     return this.anuladas;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setAnuladas(boolean anuladas)
/* 340:    */   {
/* 341:509 */     this.anuladas = anuladas;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public List<SelectItem> getListaTipoVenta()
/* 345:    */   {
/* 346:518 */     if (this.listaTipoVenta == null)
/* 347:    */     {
/* 348:519 */       this.listaTipoVenta = new ArrayList();
/* 349:520 */       for (TipoVentaEnum tipoVenta : TipoVentaEnum.values())
/* 350:    */       {
/* 351:521 */         SelectItem item = new SelectItem(tipoVenta, tipoVenta.name());
/* 352:522 */         this.listaTipoVenta.add(item);
/* 353:    */       }
/* 354:    */     }
/* 355:525 */     return this.listaTipoVenta;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setListaTipoVenta(List<SelectItem> listaTipoVenta)
/* 359:    */   {
/* 360:535 */     this.listaTipoVenta = listaTipoVenta;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public TipoVentaEnum getTipoVenta()
/* 364:    */   {
/* 365:544 */     return this.tipoVenta;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setTipoVenta(TipoVentaEnum tipoVenta)
/* 369:    */   {
/* 370:554 */     this.tipoVenta = tipoVenta;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public boolean isSaldoInicial()
/* 374:    */   {
/* 375:563 */     return this.saldoInicial;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void setSaldoInicial(boolean saldoInicial)
/* 379:    */   {
/* 380:573 */     this.saldoInicial = saldoInicial;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public enumTipoReporte getTipoReporte()
/* 384:    */   {
/* 385:582 */     return this.tipoReporte;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public boolean isIndicadorTipoReporte()
/* 389:    */   {
/* 390:591 */     this.indicadorTipoReporte = false;
/* 391:592 */     if (this.tipoReporte.equals(enumTipoReporte.PRODUCTO)) {
/* 392:593 */       this.indicadorTipoReporte = true;
/* 393:    */     }
/* 394:595 */     return this.indicadorTipoReporte;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setIndicadorTipoReporte(boolean indicadorTipoReporte)
/* 398:    */   {
/* 399:605 */     this.indicadorTipoReporte = indicadorTipoReporte;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setTipoReporte(enumTipoReporte tipoReporte)
/* 403:    */   {
/* 404:615 */     this.tipoReporte = tipoReporte;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public List<SelectItem> getListaTipoReporte()
/* 408:    */   {
/* 409:624 */     if (this.listaTipoReporte == null)
/* 410:    */     {
/* 411:625 */       this.listaTipoReporte = new ArrayList();
/* 412:626 */       for (enumTipoReporte tr : enumTipoReporte.values()) {
/* 413:627 */         this.listaTipoReporte.add(new SelectItem(tr, tr.name()));
/* 414:    */       }
/* 415:    */     }
/* 416:630 */     return this.listaTipoReporte;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void setListaTipoReporte(List<SelectItem> listaTipoReporte)
/* 420:    */   {
/* 421:640 */     this.listaTipoReporte = listaTipoReporte;
/* 422:    */   }
/* 423:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteFacturacionResumidoClienteComercializadoraBean
 * JD-Core Version:    0.7.0.1
 */