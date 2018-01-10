/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.Asiento;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.CuentaContable;
/*   9:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  10:    */ import com.asinfo.as2.entities.DimensionContable;
/*  11:    */ import com.asinfo.as2.entities.FormaPago;
/*  12:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  13:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.PlantillaAsiento;
/*  16:    */ import com.asinfo.as2.entities.Secuencia;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  21:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  24:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioPlantillaAsiento;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  28:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  29:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  30:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  31:    */ import com.asinfo.as2.util.AppUtil;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.text.SimpleDateFormat;
/*  34:    */ import java.util.ArrayList;
/*  35:    */ import java.util.Date;
/*  36:    */ import java.util.HashMap;
/*  37:    */ import java.util.List;
/*  38:    */ import java.util.Map;
/*  39:    */ import javax.annotation.PostConstruct;
/*  40:    */ import javax.ejb.EJB;
/*  41:    */ import javax.faces.bean.ManagedBean;
/*  42:    */ import javax.faces.bean.ManagedProperty;
/*  43:    */ import javax.faces.bean.ViewScoped;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ import org.primefaces.component.datatable.DataTable;
/*  46:    */ import org.primefaces.event.SelectEvent;
/*  47:    */ import org.primefaces.model.LazyDataModel;
/*  48:    */ import org.primefaces.model.SortOrder;
/*  49:    */ 
/*  50:    */ @ManagedBean
/*  51:    */ @ViewScoped
/*  52:    */ public class AsientoBean
/*  53:    */   extends PageControllerAS2
/*  54:    */ {
/*  55:    */   private static final long serialVersionUID = -5788627579433275126L;
/*  56:    */   @EJB
/*  57:    */   private ServicioAsiento servicioAsiento;
/*  58:    */   @EJB
/*  59:    */   private ServicioCuentaContable servicioCuentaContable;
/*  60:    */   @EJB
/*  61:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  62:    */   @EJB
/*  63:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  64:    */   @EJB
/*  65:    */   private ServicioPlantillaAsiento servicioPlantillaAsiento;
/*  66:    */   @EJB
/*  67:    */   private ServicioSecuencia servicioSecuencia;
/*  68:    */   private Asiento asiento;
/*  69:    */   private List<TipoAsiento> listaTipoAsiento;
/*  70:    */   private LazyDataModel<Asiento> listaAsiento;
/*  71:    */   private DataTable dtAsiento;
/*  72:    */   private DataTable dtDetalleAsiento;
/*  73:    */   private DataTable dtDetalleAsientoCentroCosto;
/*  74:    */   private DataTable dtCuentaContable;
/*  75:    */   private DataTable dtMovimientoBancario;
/*  76:    */   private CuentaContable cuentaContable;
/*  77:    */   private DetalleAsiento lineaDetalleAsiento;
/*  78:    */   private String tipoAsiento;
/*  79:    */   private String numeroAsiento;
/*  80:101 */   private TipoAccesoContable tipoAccesoCuentaContable = TipoAccesoContable.BLOQUEADA;
/*  81:102 */   private Integer idAsiento = null;
/*  82:    */   private Date fechaDesde;
/*  83:    */   private Date fechaHasta;
/*  84:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  85:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  86:    */   private List<PlantillaAsiento> listaPlantillaAsiento;
/*  87:111 */   private PlantillaAsiento plantillaAsiento = new PlantillaAsiento();
/*  88:113 */   private BigDecimal valorAsiento = BigDecimal.ZERO;
/*  89:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  90:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  91:    */   
/*  92:    */   @PostConstruct
/*  93:    */   public void init()
/*  94:    */   {
/*  95:125 */     this.listaAsiento = new LazyDataModel()
/*  96:    */     {
/*  97:    */       private static final long serialVersionUID = 1L;
/*  98:    */       
/*  99:    */       public List<Asiento> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 100:    */       {
/* 101:132 */         List<Asiento> lista = new ArrayList();
/* 102:    */         
/* 103:134 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 104:136 */         if ((AsientoBean.this.tipoAsiento != null) && (AsientoBean.this.numeroAsiento != null))
/* 105:    */         {
/* 106:137 */           filters.put("tipoAsiento.nombre", AsientoBean.this.tipoAsiento);
/* 107:138 */           filters.put("numero", AsientoBean.this.numeroAsiento);
/* 108:    */         }
/* 109:141 */         if (AsientoBean.this.idAsiento != null)
/* 110:    */         {
/* 111:142 */           filters.put("idAsiento", String.valueOf(AsientoBean.this.idAsiento));
/* 112:143 */           AsientoBean.this.idAsiento = null;
/* 113:    */         }
/* 114:145 */         filters = AsientoBean.this.getFiltrosFechas(filters);
/* 115:146 */         lista = AsientoBean.this.servicioAsiento.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 116:147 */         AsientoBean.this.listaAsiento.setRowCount(AsientoBean.this.servicioAsiento.contarPorCriterio(filters));
/* 117:    */         
/* 118:149 */         AsientoBean.this.tipoAsiento = null;
/* 119:150 */         AsientoBean.this.numeroAsiento = null;
/* 120:    */         
/* 121:152 */         return lista;
/* 122:    */       }
/* 123:    */     };
/* 124:    */   }
/* 125:    */   
/* 126:    */   private Map<String, String> getFiltrosFechas(Map<String, String> filters)
/* 127:    */   {
/* 128:159 */     if ((this.fechaDesde != null) && (this.fechaHasta != null))
/* 129:    */     {
/* 130:160 */       SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
/* 131:161 */       filters.put("fecha", OperacionEnum.BETWEEN.name() + dateFormat.format(getFechaDesde()) + "~" + dateFormat.format(getFechaHasta()));
/* 132:    */     }
/* 133:163 */     return filters;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private void crearAsiento()
/* 137:    */   {
/* 138:170 */     this.asiento = new Asiento();
/* 139:171 */     this.asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 140:172 */     this.asiento.setSucursal(AppUtil.getSucursal());
/* 141:173 */     this.asiento.setTipoAsiento(new TipoAsiento());
/* 142:174 */     this.asiento.getTipoAsiento().setSecuencia(new Secuencia());
/* 143:175 */     this.asiento.getTipoAsiento().getSecuencia().setSufijo("");
/* 144:176 */     this.asiento.getTipoAsiento().getSecuencia().setPrefijo("");
/* 145:177 */     this.asiento.getTipoAsiento().getSecuencia().setLongitud(2);
/* 146:178 */     this.asiento.setEstado(Estado.ELABORADO);
/* 147:179 */     this.asiento.setFecha(new Date());
/* 148:180 */     this.asiento.setNumero("");
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String guardar()
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:190 */       if ((isEditado()) && (this.asiento.isIndicadorAutomatico()))
/* 156:    */       {
/* 157:192 */         this.asiento.setIndicadorAutomaticoEditado(isEditado());
/* 158:    */         
/* 159:194 */         BigDecimal nuevoValor = this.asiento.getTotalDebe();
/* 160:195 */         if ((this.asiento.getValorOriginal().compareTo(nuevoValor) < 0) || (this.asiento.getValorOriginal().compareTo(nuevoValor) > 0)) {
/* 161:196 */           throw new ExcepcionAS2Financiero("msg_error_guardar_asiento_automatico_editado");
/* 162:    */         }
/* 163:    */       }
/* 164:200 */       this.servicioAsiento.guardar(this.asiento);
/* 165:201 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 166:202 */       setEditado(false);
/* 167:    */       
/* 168:    */ 
/* 169:    */ 
/* 170:206 */       this.idAsiento = Integer.valueOf(this.asiento.getIdAsiento());
/* 171:    */       
/* 172:208 */       limpiar();
/* 173:    */       
/* 174:    */ 
/* 175:211 */       return "";
/* 176:    */     }
/* 177:    */     catch (ExcepcionAS2Financiero e)
/* 178:    */     {
/* 179:214 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 180:215 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 181:    */     }
/* 182:    */     catch (ExcepcionAS2 e)
/* 183:    */     {
/* 184:217 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 185:218 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 186:    */     }
/* 187:    */     catch (Exception e)
/* 188:    */     {
/* 189:220 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 190:221 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 191:    */     }
/* 192:223 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String eliminar()
/* 196:    */   {
/* 197:    */     try
/* 198:    */     {
/* 199:232 */       this.servicioAsiento.anular(this.asiento);
/* 200:233 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 201:234 */       limpiar();
/* 202:    */     }
/* 203:    */     catch (ExcepcionAS2Financiero e)
/* 204:    */     {
/* 205:236 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 206:237 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 207:    */     }
/* 208:    */     catch (Exception e)
/* 209:    */     {
/* 210:239 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 211:240 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 212:    */     }
/* 213:242 */     return "";
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String eliminarCheque()
/* 217:    */   {
/* 218:    */     try
/* 219:    */     {
/* 220:247 */       this.servicioAsiento.eliminarCheque(this.asiento);
/* 221:248 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 222:249 */       limpiar();
/* 223:    */     }
/* 224:    */     catch (ExcepcionAS2Financiero e)
/* 225:    */     {
/* 226:251 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 227:252 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 228:    */     }
/* 229:    */     catch (Exception e)
/* 230:    */     {
/* 231:254 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 232:255 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 233:    */     }
/* 234:257 */     return "";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String cargarDatos()
/* 238:    */   {
/* 239:264 */     return "";
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String editar()
/* 243:    */   {
/* 244:274 */     if (this.asiento.getIdAsiento() > 0) {
/* 245:    */       try
/* 246:    */       {
/* 247:276 */         this.asiento = this.servicioAsiento.cargarDetalle(this.asiento.getIdAsiento());
/* 248:277 */         this.servicioAsiento.esEditable(this.asiento);
/* 249:278 */         this.servicioAsiento.calcularTotales(this.asiento);
/* 250:    */         
/* 251:    */ 
/* 252:281 */         this.asiento.setValorOriginal(this.asiento.getValor());
/* 253:    */         
/* 254:283 */         setEditado(true);
/* 255:    */       }
/* 256:    */       catch (ExcepcionAS2Financiero e)
/* 257:    */       {
/* 258:285 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 259:286 */         LOG.error("ERROR AL EDITAR DATOS", e);
/* 260:    */       }
/* 261:    */       catch (Exception e)
/* 262:    */       {
/* 263:289 */         addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 264:290 */         LOG.error("ERROR AL CARGAR DETALLE");
/* 265:    */       }
/* 266:    */     } else {
/* 267:294 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 268:    */     }
/* 269:297 */     return "";
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String limpiar()
/* 273:    */   {
/* 274:306 */     crearAsiento();
/* 275:307 */     return "";
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List<DetalleAsiento> getDetalleAsiento()
/* 279:    */   {
/* 280:316 */     List<DetalleAsiento> lista = new ArrayList();
/* 281:317 */     for (DetalleAsiento da : getAsiento().getListaDetalleAsiento()) {
/* 282:318 */       if (!da.isEliminado()) {
/* 283:319 */         lista.add(da);
/* 284:    */       }
/* 285:    */     }
/* 286:322 */     return lista;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public List<MovimientoBancario> getListaMovimientoBancario()
/* 290:    */   {
/* 291:331 */     List<MovimientoBancario> lista = new ArrayList();
/* 292:333 */     for (DetalleAsiento da : this.asiento.getListaDetalleAsiento()) {
/* 293:334 */       if ((da.getMovimientoBancario() != null) && (!da.getMovimientoBancario().isEliminado())) {
/* 294:335 */         lista.add(da.getMovimientoBancario());
/* 295:    */       }
/* 296:    */     }
/* 297:338 */     return lista;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public String agregarDetalle()
/* 301:    */   {
/* 302:348 */     DetalleAsiento detalleAsiento = new DetalleAsiento();
/* 303:349 */     detalleAsiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 304:350 */     detalleAsiento.setIdOrganizacion(AppUtil.getSucursal().getId());
/* 305:351 */     detalleAsiento.setCuentaContable(new CuentaContable());
/* 306:352 */     detalleAsiento.setDebe(BigDecimal.ZERO);
/* 307:353 */     detalleAsiento.setHaber(BigDecimal.ZERO);
/* 308:354 */     detalleAsiento.setAsiento(getAsiento());
/* 309:355 */     getAsiento().getListaDetalleAsiento().add(detalleAsiento);
/* 310:356 */     return "";
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void buscarCuentaContable()
/* 314:    */   {
/* 315:    */     try
/* 316:    */     {
/* 317:364 */       this.lineaDetalleAsiento = ((DetalleAsiento)this.dtDetalleAsiento.getRowData());
/* 318:365 */       String codigoCuentaContable = this.lineaDetalleAsiento.getCuentaContable().getCodigo();
/* 319:366 */       if (codigoCuentaContable != "")
/* 320:    */       {
/* 321:367 */         this.cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion().getIdOrganizacion());
/* 322:368 */         this.lineaDetalleAsiento.setCuentaContable(this.cuentaContable);
/* 323:369 */         this.servicioAsiento.actualizarMovimientoBancario(this.lineaDetalleAsiento);
/* 324:    */       }
/* 325:    */     }
/* 326:    */     catch (ExcepcionAS2Financiero e)
/* 327:    */     {
/* 328:372 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + this.lineaDetalleAsiento.getCuentaContable().getCodigo();
/* 329:373 */       addInfoMessage(strMensaje);
/* 330:    */     }
/* 331:    */     catch (Exception e)
/* 332:    */     {
/* 333:376 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void cargarCuentaContable(SelectEvent event)
/* 338:    */   {
/* 339:    */     try
/* 340:    */     {
/* 341:386 */       this.cuentaContable = ((CuentaContable)event.getObject());
/* 342:387 */       if (this.cuentaContable.getTipoAccesoCuentaContable().equals(TipoAccesoContable.BLOQUEADA))
/* 343:    */       {
/* 344:388 */         this.cuentaContable = null;
/* 345:389 */         this.lineaDetalleAsiento.setCuentaContable(this.cuentaContable);
/* 346:390 */         addInfoMessage(getLanguageController().getMensaje("msg_error_asiento_bloequeado"));
/* 347:    */       }
/* 348:    */       else
/* 349:    */       {
/* 350:392 */         this.lineaDetalleAsiento.setCuentaContable(this.cuentaContable);
/* 351:393 */         this.servicioAsiento.actualizarMovimientoBancario(this.lineaDetalleAsiento);
/* 352:    */       }
/* 353:    */     }
/* 354:    */     catch (ExcepcionAS2Financiero e)
/* 355:    */     {
/* 356:396 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 357:397 */       LOG.error("ERROR AL CARGAR CUENTA CONTABLE", e);
/* 358:    */     }
/* 359:    */     catch (ExcepcionAS2 e)
/* 360:    */     {
/* 361:399 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 362:400 */       LOG.error("ERROR AL CARGAR CUENTA CONTABLE", e);
/* 363:    */     }
/* 364:    */     catch (Exception e)
/* 365:    */     {
/* 366:402 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 367:403 */       LOG.error("ERROR AL CARGAR CUENTA CONTABLE", e);
/* 368:    */     }
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void actualizarTipoAsiento()
/* 372:    */   {
/* 373:411 */     TipoAsiento tipoAsiento = this.servicioTipoAsiento.buscarPorId(Integer.valueOf(this.asiento.getTipoAsiento().getId()));
/* 374:412 */     this.asiento.setTipoAsiento(tipoAsiento);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void seleccionarDetalle(DetalleAsiento detalle)
/* 378:    */   {
/* 379:416 */     this.lineaDetalleAsiento = detalle;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public String eliminarDetalle()
/* 383:    */   {
/* 384:425 */     DetalleAsiento d = (DetalleAsiento)this.dtDetalleAsiento.getRowData();
/* 385:426 */     d.setEliminado(true);
/* 386:427 */     if (d.getMovimientoBancario() != null)
/* 387:    */     {
/* 388:428 */       d.getMovimientoBancario().setEliminado(true);
/* 389:429 */       for (MovimientoBancarioEstadoCheque mbec : d.getMovimientoBancario().getListaMovimientoBancarioEstadoCheque()) {
/* 390:430 */         mbec.setEliminado(true);
/* 391:    */       }
/* 392:    */     }
/* 393:433 */     this.servicioAsiento.calcularTotales(this.asiento);
/* 394:    */     
/* 395:435 */     return "";
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void cargarDimensionContableListener(SelectEvent event)
/* 399:    */   {
/* 400:440 */     DimensionContable dimension = (DimensionContable)event.getObject();
/* 401:    */     try
/* 402:    */     {
/* 403:442 */       String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/* 404:443 */       if (numeroDimension.equals("1")) {
/* 405:444 */         this.lineaDetalleAsiento.setDimensionContable1(dimension);
/* 406:445 */       } else if (numeroDimension.equals("2")) {
/* 407:446 */         this.lineaDetalleAsiento.setDimensionContable2(dimension);
/* 408:447 */       } else if (numeroDimension.equals("3")) {
/* 409:448 */         this.lineaDetalleAsiento.setDimensionContable3(dimension);
/* 410:449 */       } else if (numeroDimension.equals("4")) {
/* 411:450 */         this.lineaDetalleAsiento.setDimensionContable4(dimension);
/* 412:451 */       } else if (numeroDimension.equals("5")) {
/* 413:452 */         this.lineaDetalleAsiento.setDimensionContable5(dimension);
/* 414:    */       }
/* 415:    */     }
/* 416:    */     catch (Exception e)
/* 417:    */     {
/* 418:456 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 419:457 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void cargarPlantillaAsientoListener()
/* 424:    */   {
/* 425:465 */     this.servicioAsiento.cargarPlantillaAsiento(this.asiento, this.plantillaAsiento, this.valorAsiento);
/* 426:466 */     this.servicioAsiento.calcularTotales(this.asiento);
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void calcularTotalesListener()
/* 430:    */   {
/* 431:473 */     this.servicioAsiento.calcularTotales(this.asiento);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public DetalleAsiento getLineaDetalleAsiento()
/* 435:    */   {
/* 436:488 */     return this.lineaDetalleAsiento;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void actualizarValor()
/* 440:    */   {
/* 441:492 */     DetalleAsiento detalleAsiento = (DetalleAsiento)this.dtDetalleAsiento.getRowData();
/* 442:493 */     if (detalleAsiento.getMovimientoBancario() != null)
/* 443:    */     {
/* 444:495 */       if (detalleAsiento.getDebe().compareTo(BigDecimal.ZERO) == 1) {
/* 445:496 */         detalleAsiento.getMovimientoBancario().setValor(detalleAsiento.getDebe());
/* 446:    */       }
/* 447:498 */       if (detalleAsiento.getHaber().compareTo(BigDecimal.ZERO) == 1) {
/* 448:499 */         detalleAsiento.getMovimientoBancario().setValor(detalleAsiento.getHaber().negate());
/* 449:    */       }
/* 450:    */     }
/* 451:503 */     this.servicioAsiento.calcularTotales(this.asiento);
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void actualizarFecha()
/* 455:    */   {
/* 456:507 */     for (DetalleAsiento detalleAsiento : this.asiento.getListaDetalleAsiento()) {
/* 457:508 */       if ((detalleAsiento.getMovimientoBancario() != null) && 
/* 458:509 */         (!detalleAsiento.getMovimientoBancario().isEliminado())) {
/* 459:510 */         detalleAsiento.getMovimientoBancario().setFecha(this.asiento.getFecha());
/* 460:    */       }
/* 461:    */     }
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void actualizarFecha(Date fecha)
/* 465:    */   {
/* 466:517 */     for (DetalleAsiento detalleAsiento : this.asiento.getListaDetalleAsiento()) {
/* 467:518 */       if ((detalleAsiento.getMovimientoBancario() != null) && 
/* 468:519 */         (!detalleAsiento.getMovimientoBancario().isEliminado())) {
/* 469:520 */         detalleAsiento.getMovimientoBancario().setFecha(fecha);
/* 470:    */       }
/* 471:    */     }
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void actualizarFechaListener()
/* 475:    */   {
/* 476:532 */     actualizarFecha(getAsiento().getFecha());
/* 477:    */   }
/* 478:    */   
/* 479:    */   public String copiar()
/* 480:    */   {
/* 481:    */     try
/* 482:    */     {
/* 483:537 */       Asiento auxAsiento = this.servicioAsiento.cargarDetalle(this.asiento.getId());
/* 484:538 */       this.asiento = this.servicioAsiento.copiarAsiento(auxAsiento);
/* 485:539 */       setEditado(true);
/* 486:    */     }
/* 487:    */     catch (ExcepcionAS2Financiero e)
/* 488:    */     {
/* 489:542 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 490:    */     }
/* 491:    */     catch (ExcepcionAS2 e)
/* 492:    */     {
/* 493:544 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 494:    */     }
/* 495:547 */     return "";
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void actualizarFormaPago(MovimientoBancario movimientoBancario)
/* 499:    */   {
/* 500:552 */     Secuencia secuencia = this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(movimientoBancario.getCuentaBancariaOrganizacion()
/* 501:553 */       .getIdCuentaBancariaOrganizacion(), movimientoBancario.getFormaPago().getIdFormaPago());
/* 502:    */     
/* 503:555 */     movimientoBancario.setSecuencia(secuencia);
/* 504:556 */     String numero = "";
/* 505:557 */     if (secuencia != null) {
/* 506:    */       try
/* 507:    */       {
/* 508:560 */         numero = this.servicioSecuencia.obtenerSecuencia(secuencia, this.asiento.getFecha());
/* 509:561 */         movimientoBancario.setDocumentoReferencia(numero);
/* 510:    */       }
/* 511:    */       catch (ExcepcionAS2 e)
/* 512:    */       {
/* 513:563 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 514:564 */         e.printStackTrace();
/* 515:    */       }
/* 516:    */     }
/* 517:567 */     movimientoBancario.setDocumentoReferencia(numero);
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void listarCuentaContable() {}
/* 521:    */   
/* 522:    */   public Asiento getAsiento()
/* 523:    */   {
/* 524:575 */     if (this.asiento == null) {
/* 525:576 */       crearAsiento();
/* 526:    */     }
/* 527:578 */     return this.asiento;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setAsiento(Asiento asiento)
/* 531:    */   {
/* 532:582 */     this.asiento = asiento;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public LazyDataModel<Asiento> getListaAsiento()
/* 536:    */   {
/* 537:586 */     return this.listaAsiento;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setListaAsiento(LazyDataModel<Asiento> listaAsiento)
/* 541:    */   {
/* 542:590 */     this.listaAsiento = listaAsiento;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public DataTable getDtAsiento()
/* 546:    */   {
/* 547:594 */     return this.dtAsiento;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setDtAsiento(DataTable dtAsiento)
/* 551:    */   {
/* 552:598 */     this.dtAsiento = dtAsiento;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public DataTable getDtDetalleAsiento()
/* 556:    */   {
/* 557:602 */     return this.dtDetalleAsiento;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 561:    */   {
/* 562:606 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public void setLineaDetalleAsiento(DetalleAsiento lineaDetalleAsiento)
/* 566:    */   {
/* 567:610 */     this.lineaDetalleAsiento = lineaDetalleAsiento;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public CuentaContable getCuentaContable()
/* 571:    */   {
/* 572:614 */     return this.cuentaContable;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 576:    */   {
/* 577:618 */     this.cuentaContable = cuentaContable;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public DataTable getDtDetalleAsientoCentroCosto()
/* 581:    */   {
/* 582:622 */     return this.dtDetalleAsientoCentroCosto;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void setDtDetalleAsientoCentroCosto(DataTable dtDetalleAsientoCentroCosto)
/* 586:    */   {
/* 587:626 */     this.dtDetalleAsientoCentroCosto = dtDetalleAsientoCentroCosto;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public String getTipoAsiento()
/* 591:    */   {
/* 592:635 */     return this.tipoAsiento;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void setTipoAsiento(String tipoAsiento)
/* 596:    */   {
/* 597:645 */     this.tipoAsiento = tipoAsiento;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public String getNumeroAsiento()
/* 601:    */   {
/* 602:654 */     return this.numeroAsiento;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public void setNumeroAsiento(String numeroAsiento)
/* 606:    */   {
/* 607:664 */     this.numeroAsiento = numeroAsiento;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public DataTable getDtCuentaContable()
/* 611:    */   {
/* 612:668 */     return this.dtCuentaContable;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 616:    */   {
/* 617:672 */     this.dtCuentaContable = dtCuentaContable;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public List<TipoAsiento> getListaTipoAsiento()
/* 621:    */   {
/* 622:681 */     if (this.listaTipoAsiento == null) {
/* 623:682 */       this.listaTipoAsiento = this.servicioTipoAsiento.obtenerListaCombo("nombre", true, null);
/* 624:    */     }
/* 625:684 */     return this.listaTipoAsiento;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public void setListaTipoAsiento(List<TipoAsiento> listaTipoAsiento)
/* 629:    */   {
/* 630:694 */     this.listaTipoAsiento = listaTipoAsiento;
/* 631:    */   }
/* 632:    */   
/* 633:    */   public DataTable getDtMovimientoBancario()
/* 634:    */   {
/* 635:703 */     return this.dtMovimientoBancario;
/* 636:    */   }
/* 637:    */   
/* 638:    */   public void setDtMovimientoBancario(DataTable dtMovimientoBancario)
/* 639:    */   {
/* 640:713 */     this.dtMovimientoBancario = dtMovimientoBancario;
/* 641:    */   }
/* 642:    */   
/* 643:    */   public TipoAccesoContable getTipoAccesoCuentaContable()
/* 644:    */   {
/* 645:722 */     return this.tipoAccesoCuentaContable;
/* 646:    */   }
/* 647:    */   
/* 648:    */   public void setTipoAccesoCuentaContable(TipoAccesoContable tipoAccesoCuentaContable)
/* 649:    */   {
/* 650:732 */     this.tipoAccesoCuentaContable = tipoAccesoCuentaContable;
/* 651:    */   }
/* 652:    */   
/* 653:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 654:    */   {
/* 655:739 */     return this.listaDimensionContableBean;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 659:    */   {
/* 660:747 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public BigDecimal getValorAsiento()
/* 664:    */   {
/* 665:754 */     return this.valorAsiento;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public void setValorAsiento(BigDecimal valorAsiento)
/* 669:    */   {
/* 670:762 */     this.valorAsiento = valorAsiento;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public List<PlantillaAsiento> getListaPlantillaAsiento()
/* 674:    */   {
/* 675:769 */     if (this.listaPlantillaAsiento == null)
/* 676:    */     {
/* 677:770 */       Map<String, String> filtros = new HashMap();
/* 678:771 */       filtros.put("activo", "true");
/* 679:772 */       this.listaPlantillaAsiento = this.servicioPlantillaAsiento.obtenerListaCombo("codigo", true, filtros);
/* 680:    */     }
/* 681:774 */     return this.listaPlantillaAsiento;
/* 682:    */   }
/* 683:    */   
/* 684:    */   public void setListaPlantillaAsiento(List<PlantillaAsiento> listaPlantillaAsiento)
/* 685:    */   {
/* 686:782 */     this.listaPlantillaAsiento = listaPlantillaAsiento;
/* 687:    */   }
/* 688:    */   
/* 689:    */   public PlantillaAsiento getPlantillaAsiento()
/* 690:    */   {
/* 691:789 */     return this.plantillaAsiento;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public void setPlantillaAsiento(PlantillaAsiento plantillaAsiento)
/* 695:    */   {
/* 696:797 */     this.plantillaAsiento = plantillaAsiento;
/* 697:    */   }
/* 698:    */   
/* 699:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 700:    */   {
/* 701:801 */     return this.listaCuentaContableBean;
/* 702:    */   }
/* 703:    */   
/* 704:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 705:    */   {
/* 706:805 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 707:    */   }
/* 708:    */   
/* 709:    */   public Integer getIdAsiento()
/* 710:    */   {
/* 711:809 */     return this.idAsiento;
/* 712:    */   }
/* 713:    */   
/* 714:    */   public void setIdAsiento(Integer idAsiento)
/* 715:    */   {
/* 716:813 */     this.idAsiento = idAsiento;
/* 717:    */   }
/* 718:    */   
/* 719:    */   public Date getFechaDesde()
/* 720:    */   {
/* 721:817 */     return this.fechaDesde;
/* 722:    */   }
/* 723:    */   
/* 724:    */   public void setFechaDesde(Date fechaDesde)
/* 725:    */   {
/* 726:821 */     this.fechaDesde = fechaDesde;
/* 727:    */   }
/* 728:    */   
/* 729:    */   public Date getFechaHasta()
/* 730:    */   {
/* 731:825 */     return this.fechaHasta;
/* 732:    */   }
/* 733:    */   
/* 734:    */   public void setFechaHasta(Date fechaHasta)
/* 735:    */   {
/* 736:829 */     this.fechaHasta = fechaHasta;
/* 737:    */   }
/* 738:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.AsientoBean
 * JD-Core Version:    0.7.0.1
 */