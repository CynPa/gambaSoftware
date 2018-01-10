/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   7:    */ import com.asinfo.as2.entities.Bodega;
/*   8:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.Dispositivo;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*  12:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*  13:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  16:    */ import com.asinfo.as2.entities.Producto;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  19:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*  20:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  21:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  22:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  27:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  28:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  29:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*  30:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  31:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  32:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  33:    */ import com.asinfo.as2.util.AppUtil;
/*  34:    */ import com.asinfo.as2.utils.JsfUtil;
/*  35:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  36:    */ import java.math.BigDecimal;
/*  37:    */ import java.util.ArrayList;
/*  38:    */ import java.util.Collection;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.context.FacesContext;
/*  47:    */ import javax.faces.context.PartialViewContext;
/*  48:    */ import org.apache.log4j.Logger;
/*  49:    */ import org.primefaces.component.datatable.DataTable;
/*  50:    */ import org.primefaces.context.RequestContext;
/*  51:    */ import org.primefaces.model.LazyDataModel;
/*  52:    */ import org.primefaces.model.SortOrder;
/*  53:    */ 
/*  54:    */ @ManagedBean
/*  55:    */ @ViewScoped
/*  56:    */ public class RecepcionTransferenciaBodegaBean
/*  57:    */   extends PageControllerAS2
/*  58:    */ {
/*  59:    */   private static final long serialVersionUID = 6613908497091366177L;
/*  60:    */   @EJB
/*  61:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  62:    */   @EJB
/*  63:    */   private ServicioGenerico<LecturaBalanza> servicioLecturaBalanza;
/*  64:    */   @EJB
/*  65:    */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  66:    */   @EJB
/*  67:    */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  68:    */   @EJB
/*  69:    */   private ServicioProducto servicioProducto;
/*  70:    */   @EJB
/*  71:    */   private ServicioUsuario servicioUsuario;
/*  72:    */   @EJB
/*  73:    */   private ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  74:    */   @EJB
/*  75:    */   private ServicioDocumento servicioDocumento;
/*  76:    */   @EJB
/*  77:    */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  78:    */   @EJB
/*  79:    */   private ServicioPersonaResponsable servicioResponsableSalidaMercaderia;
/*  80:    */   private MovimientoInventario transferencia;
/*  81:    */   private LazyDataModel<MovimientoInventario> listaTransferencia;
/*  82:    */   private DetalleMovimientoInventario detalleTransferenciaSeleccionada;
/*  83:    */   private MovimientoInventario ajusteInventarioIngreso;
/*  84:    */   private MovimientoInventario ajusteInventarioEgreso;
/*  85:    */   private List<Documento> listaDocumentosAjusteIngreso;
/*  86:    */   private List<Documento> listaDocumentosAjusteEgreso;
/*  87:    */   private List<Dispositivo> listaDispositivo;
/*  88:    */   private LecturaBalanza lecturaBalanza;
/*  89:    */   private List<UnidadManejo> listaUnidadManejo;
/*  90:    */   private List<UnidadManejo> listaPallet;
/*  91:    */   private Boolean mostrarBalanza;
/*  92:    */   private boolean mostradoDialogoAjusteInventario;
/*  93:    */   private List<PersonaResponsable> listaResponsableSalidaMercaderia;
/*  94:    */   private AS2Exception exContabilizacion;
/*  95:    */   private DataTable dtMovimientoInventario;
/*  96:    */   private DataTable dtLecturaBalanza;
/*  97:    */   private DataTable dtDetalles;
/*  98:    */   
/*  99:    */   public RecepcionTransferenciaBodegaBean()
/* 100:    */   {
/* 101:119 */     this.exContabilizacion = new AS2Exception("");
/* 102:    */   }
/* 103:    */   
/* 104:    */   @PostConstruct
/* 105:    */   public void init()
/* 106:    */   {
/* 107:137 */     this.listaTransferencia = new LazyDataModel()
/* 108:    */     {
/* 109:    */       private static final long serialVersionUID = 1L;
/* 110:    */       
/* 111:    */       public List<MovimientoInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 112:    */       {
/* 113:144 */         List<MovimientoInventario> lista = new ArrayList();
/* 114:    */         
/* 115:146 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 116:148 */         if (filters == null) {
/* 117:149 */           filters = new HashMap();
/* 118:    */         }
/* 119:151 */         filters.put("documento.documentoBase", DocumentoBase.TRANSFERENCIA_BODEGA.toString());
/* 120:152 */         filters.put("estado", String.valueOf(Estado.ELABORADO));
/* 121:153 */         filters = RecepcionTransferenciaBodegaBean.this.agregarFiltroPorBodega(filters, AppUtil.getUsuarioEnSesion());
/* 122:    */         
/* 123:155 */         lista = RecepcionTransferenciaBodegaBean.this.servicioMovimientoInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 124:    */         
/* 125:157 */         RecepcionTransferenciaBodegaBean.this.listaTransferencia.setRowCount(RecepcionTransferenciaBodegaBean.this.servicioMovimientoInventario.contarPorCirterio(filters));
/* 126:    */         
/* 127:159 */         return lista;
/* 128:    */       }
/* 129:    */     };
/* 130:    */   }
/* 131:    */   
/* 132:    */   private void crearEntidad() {}
/* 133:    */   
/* 134:    */   public String editar()
/* 135:    */   {
/* 136:181 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 137:182 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String crear()
/* 141:    */   {
/* 142:192 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 143:193 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String recibir()
/* 147:    */   {
/* 148:197 */     this.transferencia = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.transferencia.getIdMovimientoInventario()));
/* 149:198 */     if (isMostrarBalanza()) {
/* 150:199 */       for (DetalleMovimientoInventario dmi : this.transferencia.getDetalleMovimientosInventario()) {
/* 151:200 */         if (!dmi.getProducto().isIndicadorPesoBalanza()) {
/* 152:201 */           dmi.setCantidadPesada(dmi.getCantidad());
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:205 */     setEditado(true);
/* 157:206 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String guardar()
/* 161:    */   {
/* 162:    */     try
/* 163:    */     {
/* 164:216 */       boolean error = false;
/* 165:217 */       boolean existeDiferencia = false;
/* 166:218 */       if (isMostrarBalanza())
/* 167:    */       {
/* 168:219 */         EntidadUsuario usuario = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/* 169:    */         try
/* 170:    */         {
/* 171:221 */           existeDiferencia = this.servicioMovimientoInventario.existenDiferenciasRecepcionTransferencia(this.transferencia, usuario
/* 172:222 */             .isIndicadorAdministrador());
/* 173:    */         }
/* 174:    */         catch (AS2Exception e)
/* 175:    */         {
/* 176:224 */           error = true;
/* 177:225 */           this.exContabilizacion = e;
/* 178:226 */           FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 179:227 */           RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 180:    */         }
/* 181:229 */         if (existeDiferencia)
/* 182:    */         {
/* 183:231 */           Map<Integer, MovimientoInventario> mapa = this.servicioMovimientoInventario.crearAjustesInventarioDiferenciasRecepcionTransferencia(this.transferencia);
/* 184:232 */           this.ajusteInventarioEgreso = ((MovimientoInventario)mapa.get(Integer.valueOf(-1)));
/* 185:233 */           this.ajusteInventarioIngreso = ((MovimientoInventario)mapa.get(Integer.valueOf(1)));
/* 186:234 */           this.mostradoDialogoAjusteInventario = true;
/* 187:235 */           RequestContext.getCurrentInstance().update(":form:panelDialogoAjusteInventario");
/* 188:236 */           RequestContext.getCurrentInstance().execute("dialogAjusteInventario.show()");
/* 189:    */         }
/* 190:    */       }
/* 191:239 */       if ((!error) && (!existeDiferencia))
/* 192:    */       {
/* 193:240 */         this.servicioMovimientoInventario.guardaTransferenciaBodegaIngreso(this.transferencia, false);
/* 194:    */         
/* 195:242 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 196:243 */         limpiar();
/* 197:    */       }
/* 198:    */     }
/* 199:    */     catch (ExcepcionAS2Inventario e)
/* 200:    */     {
/* 201:246 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 202:247 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 203:    */     }
/* 204:    */     catch (ExcepcionAS2 e)
/* 205:    */     {
/* 206:249 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 207:250 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 208:    */     }
/* 209:    */     catch (AS2Exception e)
/* 210:    */     {
/* 211:252 */       e.printStackTrace();
/* 212:253 */       this.exContabilizacion = e;
/* 213:254 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/* 214:255 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/* 215:    */     }
/* 216:    */     catch (Exception e)
/* 217:    */     {
/* 218:257 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 219:258 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 220:    */     }
/* 221:260 */     return "";
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String guardarBorrador()
/* 225:    */   {
/* 226:    */     try
/* 227:    */     {
/* 228:266 */       this.servicioMovimientoInventario.guardaTransferenciaBodegaIngreso(this.transferencia, true);
/* 229:    */       
/* 230:268 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar_borrador"));
/* 231:269 */       recibir();
/* 232:    */     }
/* 233:    */     catch (ExcepcionAS2Inventario e)
/* 234:    */     {
/* 235:272 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 236:273 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 237:    */     }
/* 238:    */     catch (ExcepcionAS2 e)
/* 239:    */     {
/* 240:275 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 241:276 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 242:    */     }
/* 243:    */     catch (Exception e)
/* 244:    */     {
/* 245:278 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 246:279 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 247:    */     }
/* 248:281 */     return "";
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String eliminar()
/* 252:    */   {
/* 253:    */     try
/* 254:    */     {
/* 255:292 */       this.transferencia = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.transferencia.getId()));
/* 256:293 */       this.servicioMovimientoInventario.guardaTransferenciaBodegaIngreso(this.transferencia, true, false);
/* 257:294 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 258:    */     }
/* 259:    */     catch (ExcepcionAS2Inventario e)
/* 260:    */     {
/* 261:296 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 262:297 */       LOG.error("ERROR AL GUARDAR DATOS RECEPCION TRANSFERENCIA BODEGA", e);
/* 263:    */     }
/* 264:    */     catch (Exception e)
/* 265:    */     {
/* 266:299 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 267:300 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 268:    */     }
/* 269:302 */     return "";
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String cargarDatos()
/* 273:    */   {
/* 274:311 */     return "";
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String limpiar()
/* 278:    */   {
/* 279:320 */     setEditado(false);
/* 280:321 */     crearEntidad();
/* 281:322 */     return "";
/* 282:    */   }
/* 283:    */   
/* 284:    */   public ServicioMovimientoInventario getServicioMovimientoInventario()
/* 285:    */   {
/* 286:342 */     return this.servicioMovimientoInventario;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setServicioMovimientoInventario(ServicioMovimientoInventario servicioMovimientoInventario)
/* 290:    */   {
/* 291:352 */     this.servicioMovimientoInventario = servicioMovimientoInventario;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public MovimientoInventario getTransferencia()
/* 295:    */   {
/* 296:361 */     return this.transferencia;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setTransferencia(MovimientoInventario transferencia)
/* 300:    */   {
/* 301:371 */     this.transferencia = transferencia;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public LazyDataModel<MovimientoInventario> getListaTransferencia()
/* 305:    */   {
/* 306:380 */     return this.listaTransferencia;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setListaTransferencia(LazyDataModel<MovimientoInventario> listaTransferencia)
/* 310:    */   {
/* 311:390 */     this.listaTransferencia = listaTransferencia;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public DataTable getDtMovimientoInventario()
/* 315:    */   {
/* 316:399 */     return this.dtMovimientoInventario;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setDtMovimientoInventario(DataTable dtMovimientoInventario)
/* 320:    */   {
/* 321:409 */     this.dtMovimientoInventario = dtMovimientoInventario;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void capturarPesoListener()
/* 325:    */   {
/* 326:413 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 327:    */       try
/* 328:    */       {
/* 329:416 */         String ip = getIP();
/* 330:417 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, true);
/* 331:    */       }
/* 332:    */       catch (AS2Exception ex)
/* 333:    */       {
/* 334:419 */         JsfUtil.addErrorMessage(ex, "");
/* 335:    */       }
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void agregarPesoListener()
/* 340:    */   {
/* 341:425 */     if ((this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0))
/* 342:    */     {
/* 343:    */       try
/* 344:    */       {
/* 345:427 */         this.servicioMarcacionDispositivo.validarCantidad(this.lecturaBalanza);
/* 346:    */       }
/* 347:    */       catch (AS2Exception e)
/* 348:    */       {
/* 349:429 */         JsfUtil.addErrorMessage(e, "");
/* 350:430 */         return;
/* 351:    */       }
/* 352:    */       try
/* 353:    */       {
/* 354:433 */         BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(this.lecturaBalanza);
/* 355:434 */         BigDecimal cantidadProducto = cantidades[0];
/* 356:435 */         BigDecimal cantidadInformativa = cantidades[1];
/* 357:    */         
/* 358:437 */         this.detalleTransferenciaSeleccionada.setCantidadPesada(this.detalleTransferenciaSeleccionada.getCantidadPesada().add(cantidadProducto));
/* 359:438 */         if (cantidadInformativa != null)
/* 360:    */         {
/* 361:439 */           if (this.detalleTransferenciaSeleccionada.getCantidadUnidadInformativaRecibida() == null) {
/* 362:440 */             this.detalleTransferenciaSeleccionada.setCantidadUnidadInformativaRecibida(BigDecimal.ZERO);
/* 363:    */           }
/* 364:442 */           this.detalleTransferenciaSeleccionada.setCantidadUnidadInformativaRecibida(this.detalleTransferenciaSeleccionada
/* 365:443 */             .getCantidadUnidadInformativaRecibida().add(cantidadInformativa));
/* 366:    */         }
/* 367:447 */         this.lecturaBalanza.setDetalleMovimientoInventario(this.detalleTransferenciaSeleccionada);
/* 368:448 */         this.detalleTransferenciaSeleccionada.getListaLecturaBalanza().add(this.lecturaBalanza);
/* 369:450 */         if (this.detalleTransferenciaSeleccionada == null) {
/* 370:451 */           addErrorMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 371:    */         }
/* 372:454 */         guardarBorrador();
/* 373:455 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 374:456 */         this.lecturaBalanza = null;
/* 375:457 */         getLecturaBalanza().setDispositivo(dispositivo);
/* 376:458 */         if (this.dtLecturaBalanza != null) {
/* 377:459 */           this.dtLecturaBalanza.reset();
/* 378:    */         }
/* 379:    */       }
/* 380:    */       catch (AS2Exception e)
/* 381:    */       {
/* 382:462 */         e.printStackTrace();
/* 383:463 */         JsfUtil.addErrorMessage(e, "");
/* 384:    */       }
/* 385:    */       catch (Exception e)
/* 386:    */       {
/* 387:465 */         e.printStackTrace();
/* 388:466 */         JsfUtil.addErrorMessage(e.getMessage());
/* 389:    */       }
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void cargarProductoSeleccionadoPesa()
/* 394:    */   {
/* 395:472 */     if ((isMostrarBalanza()) && (this.lecturaBalanza != null)) {
/* 396:473 */       if (this.detalleTransferenciaSeleccionada.getProducto().isIndicadorPesoBalanza())
/* 397:    */       {
/* 398:474 */         this.lecturaBalanza.setProducto(this.servicioProducto.buscarPorId(this.detalleTransferenciaSeleccionada.getProducto().getId()));
/* 399:475 */         this.lecturaBalanza.setDetalleMovimientoInventario(this.detalleTransferenciaSeleccionada);
/* 400:    */       }
/* 401:    */       else
/* 402:    */       {
/* 403:477 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 404:478 */         this.lecturaBalanza = null;
/* 405:479 */         getLecturaBalanza().setDispositivo(dispositivo);
/* 406:    */       }
/* 407:    */     }
/* 408:    */   }
/* 409:    */   
/* 410:    */   public LecturaBalanza getLecturaBalanza()
/* 411:    */   {
/* 412:485 */     if (this.lecturaBalanza == null)
/* 413:    */     {
/* 414:486 */       this.lecturaBalanza = new LecturaBalanza();
/* 415:487 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 416:488 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/* 417:489 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/* 418:490 */       this.lecturaBalanza.setOperacion(1);
/* 419:    */     }
/* 420:492 */     return this.lecturaBalanza;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/* 424:    */   {
/* 425:496 */     this.lecturaBalanza = lecturaBalanza;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public List<UnidadManejo> getListaUnidadManejo()
/* 429:    */   {
/* 430:503 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/* 431:    */     {
/* 432:504 */       this.listaUnidadManejo = new ArrayList();
/* 433:505 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 434:506 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/* 435:    */       }
/* 436:    */     }
/* 437:509 */     return this.listaUnidadManejo;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 441:    */   {
/* 442:517 */     this.listaUnidadManejo = listaUnidadManejo;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public List<UnidadManejo> getListaPallet()
/* 446:    */   {
/* 447:521 */     if (this.listaPallet == null)
/* 448:    */     {
/* 449:522 */       Map<String, String> filters = new HashMap();
/* 450:523 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 451:524 */       filters.put("activo", "true");
/* 452:525 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/* 453:526 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/* 454:    */     }
/* 455:528 */     return this.listaPallet;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setListaPallet(List<UnidadManejo> listaPallet)
/* 459:    */   {
/* 460:532 */     this.listaPallet = listaPallet;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public boolean isMostrarBalanza()
/* 464:    */   {
/* 465:536 */     if (this.mostrarBalanza == null) {
/* 466:537 */       this.mostrarBalanza = ParametrosSistema.getRecepcionTransferenciaUsaBalanza(AppUtil.getOrganizacion().getId());
/* 467:    */     }
/* 468:539 */     return this.mostrarBalanza.booleanValue();
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setMostrarBalanza(boolean mostrarBalanza)
/* 472:    */   {
/* 473:543 */     this.mostrarBalanza = Boolean.valueOf(mostrarBalanza);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 477:    */   {
/* 478:547 */     List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/* 479:548 */     for (DetalleMovimientoInventario detalle : this.transferencia.getDetalleMovimientosInventario()) {
/* 480:549 */       for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/* 481:550 */         if ((!lectura.isEliminado()) && (lectura.getOperacion() == 1)) {
/* 482:551 */           listaLecturaBalanza.add(lectura);
/* 483:    */         }
/* 484:    */       }
/* 485:    */     }
/* 486:556 */     return listaLecturaBalanza;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public DataTable getDtLecturaBalanza()
/* 490:    */   {
/* 491:560 */     return this.dtLecturaBalanza;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/* 495:    */   {
/* 496:564 */     this.dtLecturaBalanza = dtLecturaBalanza;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public DetalleMovimientoInventario getDetalleTransferenciaSeleccionada()
/* 500:    */   {
/* 501:568 */     return this.detalleTransferenciaSeleccionada;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void setDetalleTransferenciaSeleccionada(DetalleMovimientoInventario detalleTransferenciaSeleccionada)
/* 505:    */   {
/* 506:572 */     this.detalleTransferenciaSeleccionada = detalleTransferenciaSeleccionada;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public DataTable getDtDetalles()
/* 510:    */   {
/* 511:576 */     return this.dtDetalles;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setDtDetalles(DataTable dtDetalles)
/* 515:    */   {
/* 516:580 */     this.dtDetalles = dtDetalles;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/* 520:    */   {
/* 521:    */     try
/* 522:    */     {
/* 523:585 */       BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lectura);
/* 524:586 */       BigDecimal cantidadProducto = cantidades[0];
/* 525:587 */       BigDecimal cantidadInformativa = cantidades[1];
/* 526:    */       
/* 527:589 */       lectura.setEliminado(true);
/* 528:590 */       for (DetalleMovimientoInventario dmi : this.transferencia.getDetalleMovimientosInventario()) {
/* 529:591 */         if (dmi.getId() == lectura.getDetalleMovimientoInventario().getId())
/* 530:    */         {
/* 531:593 */           lectura.getDetalleMovimientoInventario().setCantidadPesada(lectura.getDetalleMovimientoInventario().getCantidadPesada().subtract(cantidadProducto));
/* 532:594 */           dmi.setCantidadPesada(dmi.getCantidadPesada().subtract(cantidadProducto));
/* 533:596 */           if (cantidadInformativa != null)
/* 534:    */           {
/* 535:597 */             lectura.getDetalleMovimientoInventario().setCantidadUnidadInformativaRecibida(lectura
/* 536:598 */               .getDetalleMovimientoInventario().getCantidadUnidadInformativaRecibida().subtract(cantidadInformativa));
/* 537:599 */             dmi.setCantidadUnidadInformativaRecibida(dmi.getCantidadUnidadInformativaRecibida().subtract(cantidadInformativa));
/* 538:    */           }
/* 539:    */         }
/* 540:    */       }
/* 541:604 */       guardarBorrador();
/* 542:    */     }
/* 543:    */     catch (AS2Exception e)
/* 544:    */     {
/* 545:606 */       e.printStackTrace();
/* 546:607 */       JsfUtil.addErrorMessage(e, "");
/* 547:    */     }
/* 548:    */     catch (Exception e)
/* 549:    */     {
/* 550:609 */       e.printStackTrace();
/* 551:610 */       JsfUtil.addErrorMessage(e.getMessage());
/* 552:    */     }
/* 553:    */   }
/* 554:    */   
/* 555:    */   public void calcularCantidad()
/* 556:    */   {
/* 557:615 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 558:616 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   public MovimientoInventario getAjusteInventarioIngreso()
/* 563:    */   {
/* 564:621 */     return this.ajusteInventarioIngreso;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setAjusteInventarioIngreso(MovimientoInventario ajusteInventarioIngreso)
/* 568:    */   {
/* 569:625 */     this.ajusteInventarioIngreso = ajusteInventarioIngreso;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public MovimientoInventario getAjusteInventarioEgreso()
/* 573:    */   {
/* 574:629 */     return this.ajusteInventarioEgreso;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setAjusteInventarioEgreso(MovimientoInventario ajusteInventarioEgreso)
/* 578:    */   {
/* 579:633 */     this.ajusteInventarioEgreso = ajusteInventarioEgreso;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public List<MotivoAjusteInventario> autocompletarMotivoAjusteInventario(String consulta)
/* 583:    */   {
/* 584:637 */     consulta = consulta.toUpperCase();
/* 585:638 */     return this.servicioMotivoAjusteInventario.autoCompletarMotivoAjusteInventario(consulta);
/* 586:    */   }
/* 587:    */   
/* 588:    */   public List<Documento> getListaDocumentosAjusteIngreso()
/* 589:    */   {
/* 590:642 */     if (this.listaDocumentosAjusteIngreso == null) {
/* 591:    */       try
/* 592:    */       {
/* 593:644 */         this.listaDocumentosAjusteIngreso = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.AJUSTE_INVENTARIO, 
/* 594:645 */           AppUtil.getOrganizacion().getId(), Integer.valueOf(1));
/* 595:    */       }
/* 596:    */       catch (ExcepcionAS2 e)
/* 597:    */       {
/* 598:647 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 599:    */       }
/* 600:    */     }
/* 601:650 */     return this.listaDocumentosAjusteIngreso;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public List<Documento> getListaDocumentosAjusteEgreso()
/* 605:    */   {
/* 606:654 */     if (this.listaDocumentosAjusteEgreso == null) {
/* 607:    */       try
/* 608:    */       {
/* 609:656 */         this.listaDocumentosAjusteEgreso = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.AJUSTE_INVENTARIO, 
/* 610:657 */           AppUtil.getOrganizacion().getId(), Integer.valueOf(-1));
/* 611:    */       }
/* 612:    */       catch (ExcepcionAS2 e)
/* 613:    */       {
/* 614:659 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 615:    */       }
/* 616:    */     }
/* 617:662 */     return this.listaDocumentosAjusteEgreso;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public boolean isMostradoDialogoAjusteInventario()
/* 621:    */   {
/* 622:666 */     return this.mostradoDialogoAjusteInventario;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public void setMostradoDialogoAjusteInventario(boolean mostradoDialogoAjusteInventario)
/* 626:    */   {
/* 627:670 */     this.mostradoDialogoAjusteInventario = mostradoDialogoAjusteInventario;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void cerrarDialogoAjusteInventario()
/* 631:    */   {
/* 632:674 */     this.mostradoDialogoAjusteInventario = false;
/* 633:675 */     this.ajusteInventarioEgreso = null;
/* 634:676 */     this.ajusteInventarioIngreso = null;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public void generarAjusteInventario()
/* 638:    */   {
/* 639:680 */     List<MovimientoInventario> listaAjustesInventario = new ArrayList();
/* 640:681 */     if (this.ajusteInventarioEgreso != null)
/* 641:    */     {
/* 642:682 */       listaAjustesInventario.add(this.ajusteInventarioEgreso);
/* 643:683 */       if ((this.ajusteInventarioEgreso.getDocumento() == null) || (this.ajusteInventarioEgreso.getMotivoAjusteInventario() == null)) {
/* 644:684 */         return;
/* 645:    */       }
/* 646:    */     }
/* 647:687 */     if (this.ajusteInventarioIngreso != null)
/* 648:    */     {
/* 649:688 */       listaAjustesInventario.add(this.ajusteInventarioIngreso);
/* 650:689 */       if ((this.ajusteInventarioIngreso.getDocumento() == null) || (this.ajusteInventarioIngreso.getMotivoAjusteInventario() == null)) {
/* 651:690 */         return;
/* 652:    */       }
/* 653:    */     }
/* 654:    */     try
/* 655:    */     {
/* 656:695 */       this.servicioMovimientoInventario.guardarRecepcionTransferenciaConAjusteInventario(listaAjustesInventario, this.transferencia);
/* 657:696 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 658:    */       
/* 659:698 */       RequestContext.getCurrentInstance().execute("dialogAjusteInventario.hide()");
/* 660:699 */       RequestContext.getCurrentInstance().update(":form:panelDialogoAjusteInventario");
/* 661:700 */       cerrarDialogoAjusteInventario();
/* 662:701 */       setEditado(false);
/* 663:    */     }
/* 664:    */     catch (ExcepcionAS2Inventario e)
/* 665:    */     {
/* 666:703 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 667:    */     }
/* 668:    */     catch (ExcepcionAS2 e)
/* 669:    */     {
/* 670:705 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 671:    */     }
/* 672:    */     catch (AS2Exception e)
/* 673:    */     {
/* 674:707 */       JsfUtil.addErrorMessage(e, "");
/* 675:    */     }
/* 676:    */   }
/* 677:    */   
/* 678:    */   public List<Dispositivo> getListaDispositivo()
/* 679:    */   {
/* 680:712 */     if (this.listaDispositivo == null)
/* 681:    */     {
/* 682:713 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 683:714 */       filtros.put("activo", "true");
/* 684:715 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 685:    */     }
/* 686:717 */     return this.listaDispositivo;
/* 687:    */   }
/* 688:    */   
/* 689:    */   public AS2Exception getExContabilizacion()
/* 690:    */   {
/* 691:721 */     return this.exContabilizacion;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 695:    */   {
/* 696:725 */     this.exContabilizacion = exContabilizacion;
/* 697:    */   }
/* 698:    */   
/* 699:    */   public List<PersonaResponsable> autocompletarResponsable(String consulta)
/* 700:    */   {
/* 701:729 */     consulta = consulta.toUpperCase();
/* 702:    */     
/* 703:731 */     List<PersonaResponsable> lista = new ArrayList();
/* 704:733 */     for (PersonaResponsable rsm : getListaResponsableSalidaMercaderia()) {
/* 705:734 */       if ((rsm.getNombres().toUpperCase().startsWith(consulta)) || (rsm.getApellidos().toUpperCase().startsWith(consulta)) || 
/* 706:735 */         (rsm.getIdentificacion().startsWith(consulta))) {
/* 707:736 */         lista.add(rsm);
/* 708:    */       }
/* 709:    */     }
/* 710:740 */     return lista;
/* 711:    */   }
/* 712:    */   
/* 713:    */   public List<PersonaResponsable> getListaResponsableSalidaMercaderia()
/* 714:    */   {
/* 715:744 */     if (this.listaResponsableSalidaMercaderia == null)
/* 716:    */     {
/* 717:745 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 718:746 */       filtros.put("indicadorSalidaMercaderia", "true");
/* 719:747 */       this.listaResponsableSalidaMercaderia = this.servicioResponsableSalidaMercaderia.obtenerListaCombo("nombres", true, filtros);
/* 720:    */     }
/* 721:750 */     return this.listaResponsableSalidaMercaderia;
/* 722:    */   }
/* 723:    */   
/* 724:    */   public Map<String, String> agregarFiltroPorBodega(Map<String, String> filters, Usuario usuarioEnSesion)
/* 725:    */   {
/* 726:758 */     filters = agregarFiltroOrganizacion(filters);
/* 727:    */     
/* 728:760 */     int secuencial = 0;
/* 729:762 */     for (UsuarioBodega usuarioBodega : usuarioEnSesion.getListaUsuarioBodega())
/* 730:    */     {
/* 731:763 */       secuencial++;
/* 732:    */       
/* 733:765 */       filters.put("OR~BO01~" + secuencial + "~" + "bodegaDestino.idBodega", "" + usuarioBodega.getBodega().getId());
/* 734:    */     }
/* 735:767 */     return filters;
/* 736:    */   }
/* 737:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.RecepcionTransferenciaBodegaBean
 * JD-Core Version:    0.7.0.1
 */