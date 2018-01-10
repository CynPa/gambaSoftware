/*   1:    */ package com.asinfo.as2.caja.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.CierreCaja;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.FormaPago;
/*  12:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  18:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  20:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  21:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.JsfUtil;
/*  24:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  25:    */ import java.math.BigDecimal;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.component.datatable.DataTable;
/*  37:    */ import org.primefaces.model.LazyDataModel;
/*  38:    */ import org.primefaces.model.SortOrder;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class DepositoCierreCajaBean
/*  43:    */   extends PageControllerAS2
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 4505393807929491638L;
/*  46:    */   @EJB
/*  47:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  48:    */   @EJB
/*  49:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  50:    */   @EJB
/*  51:    */   private ServicioDocumento servicioDocumento;
/*  52:    */   @EJB
/*  53:    */   private ServicioSecuencia servicioSecuencia;
/*  54:    */   private InterfazContableProceso interfazContableProceso;
/*  55:    */   private DetalleCierreCaja detalleCierreCaja;
/*  56:    */   private boolean seleccionTodos;
/*  57:    */   private boolean filtroSeleccionados;
/*  58:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  59:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  60:    */   private List<Documento> listaDocumento;
/*  61:    */   private boolean indicadorRender;
/*  62:    */   List<DetalleCierreCaja> listaDetalleCierreCaja;
/*  63:    */   List<DetalleCierreCaja> listaDetalleCierreCajaFiltrado;
/*  64:    */   private DataTable dtDetalleCierreCaja;
/*  65:    */   
/*  66:    */   public String crear()
/*  67:    */   {
/*  68: 90 */     super.crear();
/*  69: 91 */     cargarDatosDepositar();
/*  70: 92 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:103 */       if (this.interfazContableProceso.getValor().compareTo(BigDecimal.ZERO) > 0)
/*  78:    */       {
/*  79:105 */         calcularTotales();
/*  80:    */         
/*  81:107 */         this.servicioInterfazContableProceso
/*  82:108 */           .guardar(this.interfazContableProceso);
/*  83:109 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  84:    */         
/*  85:    */ 
/*  86:112 */         limpiar();
/*  87:113 */         setEditado(false);
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:115 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (ExcepcionAS2Financiero e)
/*  95:    */     {
/*  96:120 */       addErrorMessage(getLanguageController().getMensaje(e
/*  97:121 */         .getCodigoExcepcion()));
/*  98:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  99:    */     }
/* 100:    */     catch (ExcepcionAS2 e)
/* 101:    */     {
/* 102:124 */       addErrorMessage(getLanguageController().getMensaje(e
/* 103:125 */         .getCodigoExcepcion()));
/* 104:126 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 109:    */       
/* 110:130 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 111:    */     }
/* 112:132 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void contabilizar()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:138 */       this.servicioInterfazContableProceso.contabilizarDepositoCaja(this.interfazContableProceso);
/* 120:    */     }
/* 121:    */     catch (ExcepcionAS2Financiero e)
/* 122:    */     {
/* 123:140 */       addErrorMessage(getLanguageController().getMensaje(e
/* 124:141 */         .getCodigoExcepcion()) + " " + e
/* 125:142 */         .getMessage());
/* 126:143 */       LOG.error(e);
/* 127:    */     }
/* 128:    */     catch (ExcepcionAS2 e)
/* 129:    */     {
/* 130:145 */       e.printStackTrace();
/* 131:146 */       addErrorMessage(getLanguageController().getMensaje(e
/* 132:147 */         .getCodigoExcepcion()) + " " + e
/* 133:148 */         .getMessage());
/* 134:149 */       LOG.error(e);
/* 135:    */     }
/* 136:    */     catch (AS2Exception e)
/* 137:    */     {
/* 138:151 */       JsfUtil.addErrorMessage(e, "");
/* 139:    */     }
/* 140:    */     finally
/* 141:    */     {
/* 142:153 */       setIndicadorRender(false);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String eliminar()
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:165 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 151:166 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 152:    */     }
/* 153:    */     catch (ExcepcionAS2Financiero e)
/* 154:    */     {
/* 155:169 */       addErrorMessage(getLanguageController().getMensaje(e
/* 156:170 */         .getCodigoExcepcion()));
/* 157:171 */       LOG.error("ERROR AL ELIMINAR", e);
/* 158:    */     }
/* 159:    */     catch (ExcepcionAS2 e)
/* 160:    */     {
/* 161:173 */       addErrorMessage(getLanguageController().getMensaje(e
/* 162:174 */         .getCodigoExcepcion()));
/* 163:175 */       e.printStackTrace();
/* 164:    */     }
/* 165:177 */     return "";
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String cargarDatos()
/* 169:    */   {
/* 170:187 */     return "";
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String editar()
/* 174:    */   {
/* 175:198 */     if (getInterfazContableProceso().getId() > 0) {
/* 176:    */       try
/* 177:    */       {
/* 178:201 */         if (getInterfazContableProceso().getEstado().equals(Estado.ELABORADO))
/* 179:    */         {
/* 180:204 */           this.servicioInterfazContableProceso.esEditable(this.interfazContableProceso);
/* 181:    */           
/* 182:206 */           this.interfazContableProceso = this.servicioInterfazContableProceso.cargarDetalle(getInterfazContableProceso().getId());
/* 183:    */           
/* 184:208 */           resetearTabla();
/* 185:209 */           setEditado(true);
/* 186:    */         }
/* 187:    */         else
/* 188:    */         {
/* 189:213 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 190:    */         }
/* 191:    */       }
/* 192:    */       catch (ExcepcionAS2Financiero e)
/* 193:    */       {
/* 194:219 */         addInfoMessage(getLanguageController().getMensaje(e
/* 195:220 */           .getCodigoExcepcion()));
/* 196:221 */         LOG.error("ERROR AL EDITAR DATOS", e);
/* 197:    */       }
/* 198:    */       catch (ExcepcionAS2Ventas e)
/* 199:    */       {
/* 200:223 */         addInfoMessage(getLanguageController().getMensaje(e
/* 201:224 */           .getCodigoExcepcion()));
/* 202:225 */         e.printStackTrace();
/* 203:    */       }
/* 204:    */     } else {
/* 205:228 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 206:    */     }
/* 207:232 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String limpiar()
/* 211:    */   {
/* 212:242 */     crearDepositoCaja();
/* 213:243 */     setIndicadorRender(false);
/* 214:244 */     this.filtroSeleccionados = false;
/* 215:245 */     this.listaDetalleCierreCaja = null;
/* 216:246 */     this.listaDetalleCierreCajaFiltrado = null;
/* 217:247 */     resetearTabla();
/* 218:248 */     return "";
/* 219:    */   }
/* 220:    */   
/* 221:    */   @PostConstruct
/* 222:    */   public void init()
/* 223:    */   {
/* 224:257 */     limpiar();
/* 225:258 */     this.listaInterfazContableProceso = new LazyDataModel()
/* 226:    */     {
/* 227:    */       private static final long serialVersionUID = 763093382591716471L;
/* 228:    */       
/* 229:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 230:    */       {
/* 231:267 */         List<InterfazContableProceso> lista = new ArrayList();
/* 232:    */         
/* 233:269 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 234:    */         
/* 235:271 */         filters.put("documentoBase", "" + DocumentoBase.DEPOSITO_CAJA);
/* 236:272 */         lista = DepositoCierreCajaBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 237:    */         
/* 238:274 */         DepositoCierreCajaBean.this.listaInterfazContableProceso
/* 239:275 */           .setRowCount(DepositoCierreCajaBean.this.servicioInterfazContableProceso
/* 240:276 */           .contarPorCriterio(filters));
/* 241:277 */         return lista;
/* 242:    */       }
/* 243:    */     };
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void calcularTotales()
/* 247:    */   {
/* 248:284 */     this.servicioInterfazContableProceso.calcularValorTotal(this.interfazContableProceso);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String crearDepositoCaja()
/* 252:    */   {
/* 253:289 */     this.interfazContableProceso = new InterfazContableProceso();
/* 254:290 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 255:291 */     this.interfazContableProceso.setFechaDesde(new Date());
/* 256:292 */     this.interfazContableProceso.setFechaHasta(new Date());
/* 257:293 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.DEPOSITO_CAJA);
/* 258:294 */     this.interfazContableProceso.setIndicadorAgrupadoPorCuenta(false);
/* 259:295 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 260:296 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 261:299 */     for (Documento documento : getListaDocumento()) {
/* 262:300 */       if (documento.isPredeterminado()) {
/* 263:301 */         this.interfazContableProceso.setDocumento(documento);
/* 264:    */       }
/* 265:    */     }
/* 266:305 */     if ((this.interfazContableProceso.getDocumento() == null) && 
/* 267:306 */       (!getListaDocumento().isEmpty())) {
/* 268:307 */       this.interfazContableProceso.setDocumento((Documento)this.listaDocumento.get(0));
/* 269:    */     }
/* 270:310 */     return "";
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void actualizarCuentaBancaria()
/* 274:    */   {
/* 275:314 */     if ((this.interfazContableProceso != null) && 
/* 276:315 */       (this.interfazContableProceso.getCuentaBancariaOrganizacion() != null) && 
/* 277:316 */       (this.interfazContableProceso.getEstado() != Estado.CONTABILIZADO)) {
/* 278:318 */       this.interfazContableProceso.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/* 279:319 */         .cargarDetalle(this.interfazContableProceso
/* 280:320 */         .getCuentaBancariaOrganizacion()
/* 281:321 */         .getId()));
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void actualizarFormaPago()
/* 286:    */   {
/* 287:330 */     String numero = "";
/* 288:331 */     int idFormaPago = getInterfazContableProceso().getFormaPago().getId();
/* 289:    */     
/* 290:333 */     int idCuentaBancariaOrganizacion = getInterfazContableProceso().getCuentaBancariaOrganizacion().getId();
/* 291:334 */     getInterfazContableProceso().setSecuencia(this.servicioCuentaBancariaOrganizacion
/* 292:    */     
/* 293:336 */       .obtenerSecuenciaPorFormaPago(idCuentaBancariaOrganizacion, idFormaPago));
/* 294:339 */     if (getInterfazContableProceso().getSecuencia() != null) {
/* 295:    */       try
/* 296:    */       {
/* 297:342 */         numero = this.servicioSecuencia.obtenerSecuencia(
/* 298:343 */           getInterfazContableProceso().getSecuencia(), 
/* 299:344 */           getInterfazContableProceso().getFechaContabilizacion());
/* 300:345 */         getInterfazContableProceso().setDocumentoReferencia(numero);
/* 301:    */       }
/* 302:    */       catch (ExcepcionAS2 e)
/* 303:    */       {
/* 304:347 */         addErrorMessage(getLanguageController().getMensaje(e
/* 305:348 */           .getCodigoExcepcion()));
/* 306:349 */         e.printStackTrace();
/* 307:    */       }
/* 308:    */     }
/* 309:353 */     getInterfazContableProceso().setDocumentoReferencia(numero);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void cargarDatosDepositar()
/* 313:    */   {
/* 314:358 */     this.interfazContableProceso.setListaDetalleCierreCaja(this.servicioInterfazContableProceso
/* 315:359 */       .obtenerListaDetalleCierreCaja(this.interfazContableProceso, 
/* 316:360 */       AppUtil.getSucursal().getIdSucursal()));
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void cargarDatosContabilizar()
/* 320:    */   {
/* 321:365 */     this.interfazContableProceso = this.servicioInterfazContableProceso.cargarDetalle(this.interfazContableProceso
/* 322:366 */       .getIdInterfazContableProceso());
/* 323:368 */     if (this.interfazContableProceso.getFechaContabilizacion() == null) {
/* 324:369 */       this.interfazContableProceso.setFechaContabilizacion(new Date());
/* 325:    */     }
/* 326:371 */     if (this.interfazContableProceso.getCuentaBancariaOrganizacion() == null) {
/* 327:373 */       this.interfazContableProceso.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/* 328:    */     }
/* 329:375 */     if (this.interfazContableProceso.getFormaPago() == null) {
/* 330:376 */       this.interfazContableProceso.setFormaPago(new FormaPago());
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public InterfazContableProceso getInterfazContableProceso()
/* 335:    */   {
/* 336:387 */     return this.interfazContableProceso;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 340:    */   {
/* 341:398 */     if ((interfazContableProceso != null) && 
/* 342:399 */       (interfazContableProceso.getCuentaBancariaOrganizacion() != null)) {
/* 343:400 */       actualizarCuentaBancaria();
/* 344:    */     }
/* 345:402 */     this.interfazContableProceso = interfazContableProceso;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 349:    */   {
/* 350:411 */     return this.listaInterfazContableProceso;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 354:    */   {
/* 355:415 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 356:417 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 357:    */     }
/* 358:419 */     return this.listaCuentaBancariaOrganizacion;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public List<Documento> getListaDocumento()
/* 362:    */   {
/* 363:423 */     if (this.listaDocumento == null)
/* 364:    */     {
/* 365:424 */       Map<String, String> filters = new HashMap();
/* 366:425 */       filters.put("documentoBase", 
/* 367:426 */         String.valueOf(DocumentoBase.DEPOSITO_CAJA));
/* 368:427 */       this.listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/* 369:    */     }
/* 370:430 */     return this.listaDocumento;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void seleccionarTodos()
/* 374:    */   {
/* 375:438 */     for (DetalleCierreCaja detalleCierreCaja : this.interfazContableProceso
/* 376:439 */       .getListaDetalleCierreCaja()) {
/* 377:440 */       detalleCierreCaja.setSeleccionado(this.seleccionTodos);
/* 378:    */     }
/* 379:443 */     this.listaDetalleCierreCaja = null;
/* 380:444 */     getListaDetalleCierreCaja();
/* 381:    */     
/* 382:446 */     calcularTotales();
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void seleccionarTodoElCierreCaja()
/* 386:    */   {
/* 387:455 */     for (DetalleCierreCaja detalleCierreCaja : this.interfazContableProceso
/* 388:456 */       .getListaDetalleCierreCaja()) {
/* 389:457 */       if (detalleCierreCaja.getCierreCaja().equals(this.detalleCierreCaja
/* 390:458 */         .getCierreCaja())) {
/* 391:459 */         detalleCierreCaja.setSeleccionado(true);
/* 392:    */       }
/* 393:    */     }
/* 394:463 */     this.listaDetalleCierreCaja = null;
/* 395:464 */     getListaDetalleCierreCaja();
/* 396:465 */     calcularTotales();
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List<DetalleCierreCaja> getListaDetalleCierreCaja()
/* 400:    */   {
/* 401:485 */     if (this.listaDetalleCierreCaja == null)
/* 402:    */     {
/* 403:487 */       this.listaDetalleCierreCaja = new ArrayList();
/* 404:488 */       resetearTabla();
/* 405:490 */       if (this.filtroSeleccionados) {
/* 406:492 */         for (DetalleCierreCaja detalleCierreCaja : this.interfazContableProceso
/* 407:493 */           .getListaDetalleCierreCaja()) {
/* 408:494 */           if (detalleCierreCaja.isSeleccionado()) {
/* 409:495 */             this.listaDetalleCierreCaja.add(detalleCierreCaja);
/* 410:    */           }
/* 411:    */         }
/* 412:    */       } else {
/* 413:500 */         this.listaDetalleCierreCaja.addAll(this.interfazContableProceso
/* 414:501 */           .getListaDetalleCierreCaja());
/* 415:    */       }
/* 416:    */     }
/* 417:505 */     return this.listaDetalleCierreCaja;
/* 418:    */   }
/* 419:    */   
/* 420:    */   private void resetearTabla()
/* 421:    */   {
/* 422:509 */     this.listaDetalleCierreCajaFiltrado = null;
/* 423:510 */     if (this.dtDetalleCierreCaja != null) {
/* 424:511 */       this.dtDetalleCierreCaja.reset();
/* 425:    */     }
/* 426:    */   }
/* 427:    */   
/* 428:    */   public DetalleCierreCaja getDetalleCierreCaja()
/* 429:    */   {
/* 430:521 */     return this.detalleCierreCaja;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setDetalleCierreCaja(DetalleCierreCaja detalleCierreCaja)
/* 434:    */   {
/* 435:531 */     this.detalleCierreCaja = detalleCierreCaja;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public boolean isSeleccionTodos()
/* 439:    */   {
/* 440:540 */     return this.seleccionTodos;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setSeleccionTodos(boolean seleccionTodos)
/* 444:    */   {
/* 445:550 */     this.seleccionTodos = seleccionTodos;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean isFiltroSeleccionados()
/* 449:    */   {
/* 450:554 */     return this.filtroSeleccionados;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setFiltroSeleccionados(boolean filtroSeleccionados)
/* 454:    */   {
/* 455:559 */     if (this.filtroSeleccionados != filtroSeleccionados)
/* 456:    */     {
/* 457:560 */       this.listaDetalleCierreCaja = null;
/* 458:561 */       this.filtroSeleccionados = filtroSeleccionados;
/* 459:562 */       getListaDetalleCierreCaja();
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public boolean isIndicadorRender()
/* 464:    */   {
/* 465:568 */     return this.indicadorRender;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void setIndicadorRender(boolean indicadorRender)
/* 469:    */   {
/* 470:572 */     this.indicadorRender = indicadorRender;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public List<DetalleCierreCaja> getListaDetalleCierreCajaFiltrado()
/* 474:    */   {
/* 475:576 */     return this.listaDetalleCierreCajaFiltrado;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public void setListaDetalleCierreCajaFiltrado(List<DetalleCierreCaja> listaDetalleCierreCajaFiltrado)
/* 479:    */   {
/* 480:581 */     this.listaDetalleCierreCajaFiltrado = listaDetalleCierreCajaFiltrado;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public DataTable getDtDetalleCierreCaja()
/* 484:    */   {
/* 485:585 */     return this.dtDetalleCierreCaja;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public void setDtDetalleCierreCaja(DataTable dtDetalleCierreCaja)
/* 489:    */   {
/* 490:589 */     this.dtDetalleCierreCaja = dtDetalleCierreCaja;
/* 491:    */   }
/* 492:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.procesos.DepositoCierreCajaBean
 * JD-Core Version:    0.7.0.1
 */