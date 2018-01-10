/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGastoImportacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*  11:    */ import com.asinfo.as2.entities.GastoImportacion;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  14:    */ import com.asinfo.as2.entities.Secuencia;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  17:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  18:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  19:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoDocumentoBase;
/*  21:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  25:    */ import com.asinfo.as2.util.AppUtil;
/*  26:    */ import com.asinfo.as2.utils.JsfUtil;
/*  27:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Collections;
/*  30:    */ import java.util.HashMap;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import javax.annotation.PostConstruct;
/*  34:    */ import javax.ejb.EJB;
/*  35:    */ import javax.faces.bean.ManagedBean;
/*  36:    */ import javax.faces.bean.ViewScoped;
/*  37:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  38:    */ import javax.faces.model.SelectItem;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.component.datatable.DataTable;
/*  41:    */ import org.primefaces.event.SelectEvent;
/*  42:    */ import org.primefaces.model.LazyDataModel;
/*  43:    */ import org.primefaces.model.SortOrder;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class DocumentoBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = -3277467698519504587L;
/*  51:    */   @EJB
/*  52:    */   private ServicioDocumento servicioDocumento;
/*  53:    */   @EJB
/*  54:    */   private ServicioSecuencia servicioSecuencia;
/*  55:    */   @EJB
/*  56:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  57:    */   @EJB
/*  58:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  59:    */   @EJB
/*  60:    */   private ServicioSRI servicioSRI;
/*  61:    */   @EJB
/*  62:    */   private ServicioGastoImportacion servicioGastoImportacion;
/*  63:    */   private Documento documento;
/*  64:    */   private LazyDataModel<Documento> listaDocumento;
/*  65:    */   private List<Secuencia> listaSecuencia;
/*  66:    */   private List<TipoAsiento> listaTipoAsiento;
/*  67:    */   private List<PuntoDeVenta> listaPuntoDeVenta;
/*  68:    */   private List<TipoComprobanteSRI> listaTipoComprobanteSRI;
/*  69:    */   private List<GastoImportacion> listaGastoImportacionNoAsignado;
/*  70:    */   private List<GastoImportacion> listaGastoImportacion;
/*  71:    */   private List<SelectItem> listaDocumentoBase;
/*  72: 91 */   private String secuenciaSeleccionada = "";
/*  73:    */   private String tipoComprobanteSRISeleccionado;
/*  74:    */   private GastoImportacion[] listaGastoImportacionSelecciando;
/*  75:    */   private DataTable dtDocumento;
/*  76:    */   private DataTable dtTablaAutorizacionDocumentoSRI;
/*  77:    */   private DataTable dtDocumentoGastoImportacion;
/*  78:    */   
/*  79:    */   @PostConstruct
/*  80:    */   public void init()
/*  81:    */   {
/*  82:103 */     this.listaDocumento = new LazyDataModel()
/*  83:    */     {
/*  84:    */       private static final long serialVersionUID = 1L;
/*  85:    */       
/*  86:    */       public List<Documento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  87:    */       {
/*  88:109 */         List<Documento> lista = new ArrayList();
/*  89:110 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  90:    */         try
/*  91:    */         {
/*  92:112 */           lista = DocumentoBean.this.servicioDocumento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  93:    */         }
/*  94:    */         catch (Exception e)
/*  95:    */         {
/*  96:115 */           e.printStackTrace();
/*  97:    */         }
/*  98:117 */         DocumentoBean.this.listaDocumento.setRowCount(DocumentoBean.this.servicioDocumento.contarPorCriterio(filters));
/*  99:118 */         return lista;
/* 100:    */       }
/* 101:    */     };
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String editar()
/* 105:    */   {
/* 106:130 */     if (getDocumento().getId() != 0)
/* 107:    */     {
/* 108:131 */       this.documento = this.servicioDocumento.cargarDetalle(this.documento.getId());
/* 109:132 */       setEditado(true);
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:134 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 114:    */     }
/* 115:137 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String guardar()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:148 */       this.servicioDocumento.guardar(getDocumento());
/* 123:149 */       limpiar();
/* 124:150 */       setEditado(false);
/* 125:151 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 126:    */     }
/* 127:    */     catch (AS2Exception e)
/* 128:    */     {
/* 129:153 */       JsfUtil.addErrorMessage(e, "");
/* 130:154 */       e.printStackTrace();
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:156 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 135:157 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 136:    */     }
/* 137:160 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String eliminar()
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144:171 */       this.servicioDocumento.eliminar(getDocumento());
/* 145:172 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:174 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 150:175 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 151:    */     }
/* 152:178 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String limpiar()
/* 156:    */   {
/* 157:188 */     crearDocumento();
/* 158:189 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String cargarDatos()
/* 162:    */   {
/* 163:199 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void onRowSelect(SelectEvent event)
/* 167:    */   {
/* 168:208 */     this.documento = ((Documento)event.getObject());
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String agregarAutorizacion()
/* 172:    */   {
/* 173:212 */     AutorizacionDocumentoSRI adSRI = new AutorizacionDocumentoSRI();
/* 174:    */     
/* 175:214 */     adSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 176:215 */     adSRI.setIdSucursal(AppUtil.getSucursal().getId());
/* 177:216 */     adSRI.setPuntoDeVenta(new PuntoDeVenta());
/* 178:217 */     adSRI.setSecuencia(new Secuencia());
/* 179:218 */     adSRI.setDocumento(this.documento);
/* 180:219 */     adSRI.setActivo(true);
/* 181:    */     
/* 182:221 */     this.documento.getListaAutorizacionDocumentoSRI().add(adSRI);
/* 183:    */     
/* 184:223 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String agregarGastoImportacion()
/* 188:    */   {
/* 189:232 */     if (this.listaGastoImportacionSelecciando != null) {
/* 190:233 */       for (GastoImportacion gastoImportacion : this.listaGastoImportacionSelecciando)
/* 191:    */       {
/* 192:235 */         DocumentoGastoImportacion documentoGastoImportacion = new DocumentoGastoImportacion();
/* 193:236 */         documentoGastoImportacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 194:237 */         documentoGastoImportacion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 195:238 */         documentoGastoImportacion.setGastoImportacion(gastoImportacion);
/* 196:239 */         documentoGastoImportacion.setDocumento(this.documento);
/* 197:240 */         this.documento.getListaDocumentoGastoImportacion().add(documentoGastoImportacion);
/* 198:    */       }
/* 199:    */     }
/* 200:245 */     return "";
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void eliminarAutorizacion()
/* 204:    */   {
/* 205:255 */     AutorizacionDocumentoSRI autorizacionDocumentoSRI = (AutorizacionDocumentoSRI)this.dtTablaAutorizacionDocumentoSRI.getRowData();
/* 206:    */     
/* 207:257 */     autorizacionDocumentoSRI.setEliminado(true);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void actualizarIndicadorDocumentoTributario(AjaxBehaviorEvent event)
/* 211:    */   {
/* 212:267 */     if ((getDocumento().isIndicadorDocumentoTributario()) && 
/* 213:268 */       (getDocumento().getDocumentoBase().getTipoDocumentoBase() != TipoDocumentoBase.PROVEEDOR))
/* 214:    */     {
/* 215:269 */       getDocumento().setSecuencia(null);
/* 216:270 */       getDocumento().setTipoComprobanteSRI(null);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void actualizarDocumentoBase(AjaxBehaviorEvent event)
/* 221:    */   {
/* 222:275 */     if ((getDocumento().getDocumentoBase().getTipoDocumentoBase() != TipoDocumentoBase.CLIENTE) && 
/* 223:276 */       (getDocumento().getDocumentoBase().getTipoDocumentoBase() != TipoDocumentoBase.PROVEEDOR)) {
/* 224:277 */       getDocumento().setIndicadorDocumentoTributario(false);
/* 225:    */     }
/* 226:279 */     if (getDocumento().getDocumentoBase().getTipoDocumentoBase() == TipoDocumentoBase.PROVEEDOR) {
/* 227:280 */       getDocumento().setIndicadorDocumentoExterior(false);
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void actualizarSecuencia(AjaxBehaviorEvent event)
/* 232:    */   {
/* 233:291 */     AutorizacionDocumentoSRI autorizacionDocumentoSRI = (AutorizacionDocumentoSRI)this.dtTablaAutorizacionDocumentoSRI.getRowData();
/* 234:292 */     Secuencia secuencia = this.servicioSecuencia.buscarPorId(Integer.valueOf(autorizacionDocumentoSRI.getSecuencia().getId()));
/* 235:293 */     autorizacionDocumentoSRI.setSecuencia(secuencia);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String cargaGastoImportacionNoAsignado()
/* 239:    */   {
/* 240:302 */     this.listaGastoImportacionSelecciando = null;
/* 241:    */     
/* 242:304 */     Map<Integer, GastoImportacion> documentoGastoImportacion = new HashMap();
/* 243:306 */     for (GastoImportacion gastoImportacion : getListaGastoImportacion()) {
/* 244:307 */       documentoGastoImportacion.put(Integer.valueOf(gastoImportacion.getIdGastoImportacion()), gastoImportacion);
/* 245:    */     }
/* 246:309 */     for (DocumentoGastoImportacion documentoGastoImportacion_ : getListaDocumentoGastoImportacion()) {
/* 247:310 */       documentoGastoImportacion.remove(Integer.valueOf(documentoGastoImportacion_.getGastoImportacion().getIdGastoImportacion()));
/* 248:    */     }
/* 249:313 */     this.listaGastoImportacionNoAsignado = new ArrayList(documentoGastoImportacion.values());
/* 250:314 */     return "";
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String eliminarDocumentoGastoImportacion()
/* 254:    */   {
/* 255:323 */     DocumentoGastoImportacion documentoGastoImportacion = (DocumentoGastoImportacion)this.dtDocumentoGastoImportacion.getRowData();
/* 256:324 */     documentoGastoImportacion.setEliminado(true);
/* 257:    */     
/* 258:326 */     return "";
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String eliminaTodoDocumentoGastoImportacion()
/* 262:    */   {
/* 263:330 */     for (DocumentoGastoImportacion documentoGastoImportacion : this.documento.getListaDocumentoGastoImportacion()) {
/* 264:331 */       documentoGastoImportacion.setEliminado(true);
/* 265:    */     }
/* 266:333 */     return "";
/* 267:    */   }
/* 268:    */   
/* 269:    */   public Documento getDocumento()
/* 270:    */   {
/* 271:342 */     if (this.documento == null) {
/* 272:343 */       crearDocumento();
/* 273:    */     }
/* 274:345 */     return this.documento;
/* 275:    */   }
/* 276:    */   
/* 277:    */   private void crearDocumento()
/* 278:    */   {
/* 279:352 */     this.documento = new Documento();
/* 280:353 */     this.documento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 281:354 */     this.documento.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 282:355 */     this.documento.setTipoAsiento(new TipoAsiento());
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setDocumento(Documento documento)
/* 286:    */   {
/* 287:365 */     this.documento = documento;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public LazyDataModel<Documento> getListaDocumento()
/* 291:    */   {
/* 292:374 */     return this.listaDocumento;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setListaDocumento(LazyDataModel<Documento> listaDocumento)
/* 296:    */   {
/* 297:384 */     this.listaDocumento = listaDocumento;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public DataTable getDtDocumento()
/* 301:    */   {
/* 302:393 */     return this.dtDocumento;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setDtDocumento(DataTable dtDocumento)
/* 306:    */   {
/* 307:403 */     this.dtDocumento = dtDocumento;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public List<SelectItem> getListaDocumentoBase()
/* 311:    */   {
/* 312:412 */     if (this.listaDocumentoBase == null)
/* 313:    */     {
/* 314:413 */       this.listaDocumentoBase = new ArrayList();
/* 315:414 */       SelectItem item = null;
/* 316:415 */       for (DocumentoBase t : DocumentoBase.values()) {
/* 317:416 */         if (!t.getTipoDocumentoBase().equals(TipoDocumentoBase.FLORICOLA))
/* 318:    */         {
/* 319:417 */           item = new SelectItem(t, t.getNombre());
/* 320:418 */           this.listaDocumentoBase.add(item);
/* 321:    */         }
/* 322:    */       }
/* 323:    */     }
/* 324:422 */     Collections.sort(this.listaDocumentoBase, new SelectItemComparator());
/* 325:    */     
/* 326:424 */     return this.listaDocumentoBase;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setListaDocumentoBase(List<SelectItem> listaDocumentoBase)
/* 330:    */   {
/* 331:434 */     this.listaDocumentoBase = listaDocumentoBase;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public List<Secuencia> getListaSecuencia()
/* 335:    */   {
/* 336:438 */     HashMap<String, String> filters = new HashMap();
/* 337:439 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 338:    */     
/* 339:441 */     this.listaSecuencia = this.servicioSecuencia.obtenerListaCombo("nombre", true, filters);
/* 340:    */     
/* 341:443 */     return this.listaSecuencia;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setListaSecuencia(List<Secuencia> listaSecuencia)
/* 345:    */   {
/* 346:447 */     this.listaSecuencia = listaSecuencia;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public List<TipoAsiento> getListaTipoAsiento()
/* 350:    */   {
/* 351:451 */     if (this.listaTipoAsiento == null) {
/* 352:452 */       this.listaTipoAsiento = this.servicioTipoAsiento.obtenerListaCombo("nombre", true, null);
/* 353:    */     }
/* 354:454 */     return this.listaTipoAsiento;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setListaTipoAsiento(List<TipoAsiento> listaTipoAsiento)
/* 358:    */   {
/* 359:458 */     this.listaTipoAsiento = listaTipoAsiento;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public String getSecuenciaSeleccionada()
/* 363:    */   {
/* 364:467 */     this.secuenciaSeleccionada = "";
/* 365:469 */     if (getDocumento().getSecuencia() != null) {
/* 366:470 */       this.secuenciaSeleccionada = ("" + getDocumento().getSecuencia().getId());
/* 367:    */     }
/* 368:472 */     return this.secuenciaSeleccionada;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setSecuenciaSeleccionada(String secuenciaSeleccionada)
/* 372:    */   {
/* 373:482 */     if (!this.secuenciaSeleccionada.equals(secuenciaSeleccionada))
/* 374:    */     {
/* 375:483 */       this.secuenciaSeleccionada = secuenciaSeleccionada;
/* 376:    */       
/* 377:485 */       Secuencia secuencia = null;
/* 378:486 */       if (!secuenciaSeleccionada.isEmpty()) {
/* 379:487 */         secuencia = this.servicioSecuencia.buscarPorId(Integer.valueOf(Integer.parseInt(this.secuenciaSeleccionada)));
/* 380:    */       }
/* 381:489 */       getDocumento().setSecuencia(secuencia);
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<AutorizacionDocumentoSRI> getListaAutorizacionDocumentoSRI()
/* 386:    */   {
/* 387:501 */     List<AutorizacionDocumentoSRI> lista = new ArrayList();
/* 388:503 */     for (AutorizacionDocumentoSRI autorizacionDocumentoSRI : getDocumento().getListaAutorizacionDocumentoSRI()) {
/* 389:504 */       if (!autorizacionDocumentoSRI.isEliminado()) {
/* 390:505 */         lista.add(autorizacionDocumentoSRI);
/* 391:    */       }
/* 392:    */     }
/* 393:509 */     return lista;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public DataTable getDtTablaAutorizacionDocumentoSRI()
/* 397:    */   {
/* 398:518 */     return this.dtTablaAutorizacionDocumentoSRI;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setDtTablaAutorizacionDocumentoSRI(DataTable dtTablaAutorizacionDocumentoSRI)
/* 402:    */   {
/* 403:528 */     this.dtTablaAutorizacionDocumentoSRI = dtTablaAutorizacionDocumentoSRI;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public List<PuntoDeVenta> getListaPuntoDeVenta()
/* 407:    */   {
/* 408:537 */     if (this.listaPuntoDeVenta == null) {
/* 409:538 */       this.listaPuntoDeVenta = this.servicioPuntoDeVenta.obtenerListaCombo("sucursal.codigo", true, null);
/* 410:    */     }
/* 411:540 */     return this.listaPuntoDeVenta;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setListaPuntoDeVenta(List<PuntoDeVenta> listaPuntoDeVenta)
/* 415:    */   {
/* 416:550 */     this.listaPuntoDeVenta = listaPuntoDeVenta;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String getTipoComprobanteSRISeleccionado()
/* 420:    */   {
/* 421:560 */     this.tipoComprobanteSRISeleccionado = "";
/* 422:562 */     if (getDocumento().getTipoComprobanteSRI() != null) {
/* 423:563 */       this.tipoComprobanteSRISeleccionado = ("" + getDocumento().getTipoComprobanteSRI().getId());
/* 424:    */     }
/* 425:566 */     return this.tipoComprobanteSRISeleccionado;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void setTipoComprobanteSRISeleccionado(String tipoComprobanteSRISeleccionado)
/* 429:    */     throws NumberFormatException, ExcepcionAS2
/* 430:    */   {
/* 431:579 */     if (!this.tipoComprobanteSRISeleccionado.equals(tipoComprobanteSRISeleccionado))
/* 432:    */     {
/* 433:580 */       this.tipoComprobanteSRISeleccionado = tipoComprobanteSRISeleccionado;
/* 434:    */       
/* 435:582 */       TipoComprobanteSRI tipoComprobanteSRI = null;
/* 436:583 */       if (!tipoComprobanteSRISeleccionado.isEmpty()) {
/* 437:584 */         tipoComprobanteSRI = this.servicioSRI.buscarTipoComprobanteSRIPorId(Integer.parseInt(this.tipoComprobanteSRISeleccionado));
/* 438:    */       }
/* 439:586 */       getDocumento().setTipoComprobanteSRI(tipoComprobanteSRI);
/* 440:    */     }
/* 441:589 */     this.tipoComprobanteSRISeleccionado = tipoComprobanteSRISeleccionado;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public List<TipoComprobanteSRI> getListaTipoComprobanteSRI()
/* 445:    */   {
/* 446:598 */     if (this.listaTipoComprobanteSRI == null) {
/* 447:599 */       this.listaTipoComprobanteSRI = this.servicioSRI.obtenerTipoComprobanteSRI();
/* 448:    */     }
/* 449:601 */     return this.listaTipoComprobanteSRI;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setListaTipoComprobanteSRI(List<TipoComprobanteSRI> listaTipoComprobanteSRI)
/* 453:    */   {
/* 454:611 */     this.listaTipoComprobanteSRI = listaTipoComprobanteSRI;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public List<GastoImportacion> getListaGastoImportacionNoAsignado()
/* 458:    */   {
/* 459:620 */     return this.listaGastoImportacionNoAsignado;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setListaGastoImportacionNoAsignado(List<GastoImportacion> listaGastoImportacionNoAsignado)
/* 463:    */   {
/* 464:630 */     this.listaGastoImportacionNoAsignado = listaGastoImportacionNoAsignado;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public GastoImportacion[] getListaGastoImportacionSelecciando()
/* 468:    */   {
/* 469:639 */     return this.listaGastoImportacionSelecciando;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setListaGastoImportacionSelecciando(GastoImportacion[] listaGastoImportacionSelecciando)
/* 473:    */   {
/* 474:649 */     this.listaGastoImportacionSelecciando = listaGastoImportacionSelecciando;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public List<GastoImportacion> getListaGastoImportacion()
/* 478:    */   {
/* 479:658 */     if (this.listaGastoImportacion == null) {
/* 480:659 */       this.listaGastoImportacion = this.servicioGastoImportacion.obtenerListaCombo(null, false, null);
/* 481:    */     }
/* 482:661 */     return this.listaGastoImportacion;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void setListaGastoImportacion(List<GastoImportacion> listaGastoImportacion)
/* 486:    */   {
/* 487:671 */     this.listaGastoImportacion = listaGastoImportacion;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public DataTable getDtDocumentoGastoImportacion()
/* 491:    */   {
/* 492:680 */     return this.dtDocumentoGastoImportacion;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public void setDtDocumentoGastoImportacion(DataTable dtDocumentoGastoImportacion)
/* 496:    */   {
/* 497:690 */     this.dtDocumentoGastoImportacion = dtDocumentoGastoImportacion;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public List<DocumentoGastoImportacion> getListaDocumentoGastoImportacion()
/* 501:    */   {
/* 502:694 */     List<DocumentoGastoImportacion> lista = new ArrayList();
/* 503:695 */     for (DocumentoGastoImportacion documentoGastoImportacion : this.documento.getListaDocumentoGastoImportacion()) {
/* 504:696 */       if (!documentoGastoImportacion.isEliminado()) {
/* 505:697 */         lista.add(documentoGastoImportacion);
/* 506:    */       }
/* 507:    */     }
/* 508:700 */     return lista;
/* 509:    */   }
/* 510:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.DocumentoBean
 * JD-Core Version:    0.7.0.1
 */