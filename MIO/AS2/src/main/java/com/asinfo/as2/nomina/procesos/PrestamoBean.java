/*   1:    */ package com.asinfo.as2.nomina.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.DetallePrestamo;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empleado;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Prestamo;
/*  15:    */ import com.asinfo.as2.entities.Secuencia;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoPrestamo;
/*  25:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.utils.JsfUtil;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedBean;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  39:    */ import javax.faces.model.SelectItem;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.component.datatable.DataTable;
/*  42:    */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*  43:    */ import org.primefaces.event.SelectEvent;
/*  44:    */ import org.primefaces.model.LazyDataModel;
/*  45:    */ import org.primefaces.model.SortOrder;
/*  46:    */ 
/*  47:    */ @ManagedBean
/*  48:    */ @ViewScoped
/*  49:    */ public class PrestamoBean
/*  50:    */   extends PageControllerAS2
/*  51:    */ {
/*  52:    */   private static final long serialVersionUID = 1L;
/*  53:    */   @EJB
/*  54:    */   protected ServicioPrestamo servicioPrestamo;
/*  55:    */   @EJB
/*  56:    */   private ServicioTipoPrestamo servicioTipoPrestamo;
/*  57:    */   @EJB
/*  58:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  59:    */   @EJB
/*  60:    */   private ServicioFormaPago servicioFormaPago;
/*  61:    */   @EJB
/*  62:    */   private ServicioSecuencia servicioSecuencia;
/*  63:    */   @EJB
/*  64:    */   private ServicioDocumento servicioDocumento;
/*  65:    */   private Prestamo prestamo;
/*  66:    */   private Empleado empleado;
/*  67:    */   private String tipoPrestamoSelecionado;
/*  68:    */   private String cuentaBancariaOrganizacionSelecionado;
/*  69:    */   private boolean indicadorAprobar;
/*  70:    */   private boolean indicadorContabilizar;
/*  71: 95 */   private BigDecimal montoPrestamo = BigDecimal.ZERO;
/*  72: 96 */   private BigDecimal saldoPrestamo = BigDecimal.ZERO;
/*  73:    */   private String mascara;
/*  74:    */   private LazyDataModel<Prestamo> listaPrestamo;
/*  75:    */   private List<TipoPrestamo> listaTipoPrestamo;
/*  76:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  77:    */   private List<Prestamo> listaPrestamoAprobados;
/*  78:    */   private List<Documento> listaDocumento;
/*  79:    */   private SelectItem[] listaEstadoItem;
/*  80:    */   private DataTable dtPrestamo;
/*  81:    */   private DataTable dtDetallePrestamo;
/*  82:    */   
/*  83:    */   @PostConstruct
/*  84:    */   public void init()
/*  85:    */   {
/*  86:124 */     this.listaPrestamo = new LazyDataModel()
/*  87:    */     {
/*  88:    */       private static final long serialVersionUID = 1L;
/*  89:    */       
/*  90:    */       public List<Prestamo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  91:    */       {
/*  92:131 */         List<Prestamo> lista = new ArrayList();
/*  93:132 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  94:    */         
/*  95:134 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  96:135 */         lista = PrestamoBean.this.servicioPrestamo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  97:136 */         PrestamoBean.this.listaPrestamo.setRowCount(PrestamoBean.this.servicioPrestamo.contarPorCriterio(filters));
/*  98:    */         
/*  99:138 */         return lista;
/* 100:    */       }
/* 101:    */     };
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void crearEntidad()
/* 105:    */   {
/* 106:151 */     this.prestamo = new Prestamo();
/* 107:152 */     this.prestamo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 108:153 */     this.prestamo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 109:154 */     this.prestamo.setEmpleado(new Empleado());
/* 110:155 */     this.prestamo.setTipoPrestamo(new TipoPrestamo());
/* 111:156 */     this.prestamo.setMonto(BigDecimal.ZERO);
/* 112:157 */     this.prestamo.setInteres(BigDecimal.ZERO);
/* 113:158 */     this.prestamo.setTotalCapitalCuota(BigDecimal.ZERO);
/* 114:159 */     this.prestamo.setTotalCuota(BigDecimal.ZERO);
/* 115:160 */     this.prestamo.setTotalInteresCuota(BigDecimal.ZERO);
/* 116:161 */     this.prestamo.setEstado(Estado.ELABORADO);
/* 117:162 */     this.prestamo.setFechaSolicitud(new Date());
/* 118:163 */     this.prestamo.setAsiento(null);
/* 119:164 */     this.prestamo.setFormaPago(null);
/* 120:165 */     this.prestamo.setCuentaBancariaOrganizacion(null);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String editar()
/* 124:    */   {
/* 125:175 */     if (getPrestamo().getIdPrestamo() != 0) {
/* 126:    */       try
/* 127:    */       {
/* 128:178 */         this.prestamo = this.servicioPrestamo.cargarDetalle(this.prestamo.getId());
/* 129:179 */         if (this.prestamo.getEstado() != Estado.ELABORADO)
/* 130:    */         {
/* 131:180 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 132:181 */           return "";
/* 133:    */         }
/* 134:183 */         this.servicioPrestamo.validar(this.prestamo);
/* 135:184 */         setEmpleado(this.prestamo.getEmpleado());
/* 136:    */         
/* 137:186 */         this.montoPrestamo = getPrestamo().getMonto();
/* 138:187 */         setEditado(true);
/* 139:    */       }
/* 140:    */       catch (AS2Exception e)
/* 141:    */       {
/* 142:189 */         JsfUtil.addErrorMessage(e, "");
/* 143:190 */         e.printStackTrace();
/* 144:    */       }
/* 145:    */       catch (ExcepcionAS2Financiero e)
/* 146:    */       {
/* 147:192 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 148:    */       }
/* 149:    */     } else {
/* 150:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 151:    */     }
/* 152:198 */     return "";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String guardar()
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:208 */       this.servicioPrestamo.guardar(getPrestamo());
/* 160:209 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 161:210 */       setEditado(false);
/* 162:211 */       limpiar();
/* 163:    */     }
/* 164:    */     catch (AS2Exception e)
/* 165:    */     {
/* 166:213 */       JsfUtil.addErrorMessage(e, "");
/* 167:214 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 168:215 */       e.printStackTrace();
/* 169:    */     }
/* 170:    */     catch (Exception e)
/* 171:    */     {
/* 172:217 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 173:218 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 174:    */     }
/* 175:220 */     return "";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String eliminar()
/* 179:    */   {
/* 180:    */     try
/* 181:    */     {
/* 182:230 */       if (this.prestamo.getEstado() == Estado.ELABORADO)
/* 183:    */       {
/* 184:231 */         this.servicioPrestamo.eliminarDetallesPrestamo(this.prestamo);
/* 185:232 */         this.servicioPrestamo.eliminar(this.prestamo);
/* 186:233 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 187:    */       }
/* 188:    */       else
/* 189:    */       {
/* 190:235 */         addInfoMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 191:    */       }
/* 192:    */     }
/* 193:    */     catch (Exception e)
/* 194:    */     {
/* 195:239 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 196:240 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 197:    */     }
/* 198:242 */     return "";
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String cargarDatos()
/* 202:    */   {
/* 203:251 */     setPrestamo(this.servicioPrestamo.cargarDetalle(this.prestamo.getId()));
/* 204:252 */     return "";
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String limpiar()
/* 208:    */   {
/* 209:261 */     crearEntidad();
/* 210:262 */     this.empleado = null;
/* 211:263 */     this.montoPrestamo = BigDecimal.ZERO;
/* 212:264 */     return "";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String cargarEmpleado()
/* 216:    */   {
/* 217:271 */     this.prestamo.setEmpleado(this.empleado);
/* 218:272 */     this.listaPrestamoAprobados = new ArrayList();
/* 219:273 */     HashMap<String, String> filters = new HashMap();
/* 220:274 */     filters.put("empleado.idEmpleado", "" + this.empleado.getId());
/* 221:275 */     for (Prestamo prestamo : this.servicioPrestamo.obtenerListaCombo("", false, filters)) {
/* 222:276 */       if ((prestamo.getEstado() == Estado.APROBADO) || (prestamo.getEstado() == Estado.CONTABILIZADO)) {
/* 223:277 */         this.listaPrestamoAprobados.add(prestamo);
/* 224:    */       }
/* 225:    */     }
/* 226:280 */     return "";
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void actualizarPrestamo()
/* 230:    */   {
/* 231:284 */     actualizarSaldoPrestamo();
/* 232:285 */     tablaAmortizacion();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void actualizarTipoPrestamo()
/* 236:    */   {
/* 237:289 */     if (getPrestamo().getTipoPrestamo() != null)
/* 238:    */     {
/* 239:290 */       getPrestamo().setTipoPrestamo(this.servicioTipoPrestamo.cargarDetalle(getPrestamo().getTipoPrestamo().getId()));
/* 240:291 */       setMascara(getPrestamo().getTipoPrestamo().getDocumento().getSecuencia().getPatron());
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void actualizarSaldoPrestamo()
/* 245:    */   {
/* 246:296 */     this.saldoPrestamo = BigDecimal.ZERO;
/* 247:297 */     if (this.prestamo.getPrestamoPadre() != null)
/* 248:    */     {
/* 249:298 */       Prestamo prestamo = this.servicioPrestamo.cargarDetalle(getPrestamo().getPrestamoPadre().getId());
/* 250:299 */       for (DetallePrestamo d : prestamo.getListaDetallePrestamo()) {
/* 251:300 */         this.saldoPrestamo = this.saldoPrestamo.add(d.getSaldoCapitalCuota()).add(d.getSaldoInteresCuota());
/* 252:    */       }
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<DetallePrestamo> getListaDetallePrestamo()
/* 257:    */   {
/* 258:311 */     List<DetallePrestamo> detalle = new ArrayList();
/* 259:312 */     for (DetallePrestamo dmc : getPrestamo().getListaDetallePrestamo()) {
/* 260:313 */       if (!dmc.isEliminado()) {
/* 261:314 */         detalle.add(dmc);
/* 262:    */       }
/* 263:    */     }
/* 264:318 */     return detalle;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void tablaAmortizacion()
/* 268:    */   {
/* 269:327 */     BigDecimal monto = this.montoPrestamo;
/* 270:328 */     if ((monto.compareTo(BigDecimal.ZERO) >= 0) && (this.prestamo.getPlazo() > 0))
/* 271:    */     {
/* 272:329 */       this.prestamo.setMonto(monto);
/* 273:330 */       this.servicioPrestamo.tablaAmortizacion(this.prestamo);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void dialogoContabilizar()
/* 278:    */   {
/* 279:338 */     setIndicadorContabilizar(true);
/* 280:339 */     this.prestamo = ((Prestamo)this.dtPrestamo.getRowData());
/* 281:340 */     setPrestamo(this.servicioPrestamo.cargarDetalle(this.prestamo.getId()));
/* 282:341 */     getPrestamo().setBeneficiario(
/* 283:342 */       getPrestamo().getEmpleado().getEmpresa().getNombreFiscal() + " " + getPrestamo().getEmpleado().getEmpresa().getNombreComercial());
/* 284:343 */     getPrestamo().setFechaContabilizacion(new Date());
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void contabilizarPrestamo()
/* 288:    */   {
/* 289:    */     try
/* 290:    */     {
/* 291:352 */       if ((getPrestamo().getEstado() == Estado.APROBADO) && (getPrestamo().getTipoPrestamo().isIndicadorContabilizar()))
/* 292:    */       {
/* 293:353 */         this.servicioPrestamo.guardar(getPrestamo(), false, false);
/* 294:    */         
/* 295:355 */         this.servicioPrestamo.contabilizar(getPrestamo());
/* 296:356 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 297:    */       }
/* 298:    */     }
/* 299:    */     catch (ExcepcionAS2Financiero e)
/* 300:    */     {
/* 301:359 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 302:360 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 303:    */     }
/* 304:    */     catch (ExcepcionAS2 e)
/* 305:    */     {
/* 306:362 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 307:363 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 308:    */     }
/* 309:    */     catch (Exception e)
/* 310:    */     {
/* 311:365 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 312:366 */       e.printStackTrace();
/* 313:    */     }
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void dialogoAprobacion()
/* 317:    */   {
/* 318:375 */     setIndicadorAprobar(true);
/* 319:376 */     this.prestamo = ((Prestamo)this.dtPrestamo.getRowData());
/* 320:377 */     setPrestamo(this.servicioPrestamo.cargarDetalle(this.prestamo.getId()));
/* 321:378 */     getPrestamo().setFechaAprobacion(new Date());
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void aprobarPrestamo()
/* 325:    */   {
/* 326:    */     try
/* 327:    */     {
/* 328:389 */       if ((getPrestamo().getFechaInicioDescuento() != null) && (getPrestamo().getFechaAprobacion() != null))
/* 329:    */       {
/* 330:390 */         getPrestamo().setEstado(Estado.APROBADO);
/* 331:391 */         this.servicioPrestamo.guardar(getPrestamo());
/* 332:392 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 333:    */       }
/* 334:    */       else
/* 335:    */       {
/* 336:394 */         addErrorMessage(getLanguageController().getMensaje("msg_error_fecha"));
/* 337:    */       }
/* 338:    */     }
/* 339:    */     catch (ExcepcionAS2Financiero e)
/* 340:    */     {
/* 341:398 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 342:399 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 343:    */     }
/* 344:    */     catch (ExcepcionAS2 e)
/* 345:    */     {
/* 346:401 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 347:402 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 348:    */     }
/* 349:    */     catch (Exception e)
/* 350:    */     {
/* 351:404 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 352:405 */       e.printStackTrace();
/* 353:    */     }
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void dialogoAnular()
/* 357:    */   {
/* 358:414 */     this.prestamo = ((Prestamo)this.dtPrestamo.getRowData());
/* 359:415 */     setPrestamo(this.servicioPrestamo.cargarDetalle(this.prestamo.getId()));
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void negarPrestamo()
/* 363:    */   {
/* 364:    */     try
/* 365:    */     {
/* 366:423 */       this.servicioPrestamo.anularPrestamo(this.prestamo);
/* 367:424 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 368:    */     }
/* 369:    */     catch (AS2Exception e)
/* 370:    */     {
/* 371:426 */       JsfUtil.addErrorMessage(e, "");
/* 372:    */     }
/* 373:    */     catch (Exception e)
/* 374:    */     {
/* 375:428 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 376:429 */       e.printStackTrace();
/* 377:    */     }
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 381:    */   {
/* 382:441 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/* 383:442 */     Integer idCuentaBancaria = Integer.valueOf(Integer.parseInt(selectOneMenu.getValue().toString()));
/* 384:443 */     this.prestamo.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(idCuentaBancaria.intValue()));
/* 385:444 */     this.prestamo.setCuentaContable(this.prestamo.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void dateSelectFechaInicioDescuento(SelectEvent event)
/* 389:    */   {
/* 390:448 */     this.prestamo.setFechaInicioDescuento((Date)event.getObject());
/* 391:449 */     this.servicioPrestamo.tablaAmortizacion(this.prestamo);
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void dateSelect(SelectEvent event)
/* 395:    */   {
/* 396:453 */     this.prestamo.setFechaSolicitud((Date)event.getObject());
/* 397:454 */     this.servicioPrestamo.tablaAmortizacion(this.prestamo);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public String cargarSecuencia(AjaxBehaviorEvent event)
/* 401:    */   {
/* 402:463 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/* 403:    */     try
/* 404:    */     {
/* 405:465 */       String numero = "";
/* 406:466 */       if ((!getCuentaBancariaOrganizacionSelecionado().equals("")) && (this.prestamo.getFormaPago() != null))
/* 407:    */       {
/* 408:467 */         Secuencia secuencia = this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(
/* 409:468 */           Integer.parseInt(getCuentaBancariaOrganizacionSelecionado()), Integer.parseInt(selectOneMenu.getValue().toString()));
/* 410:469 */         if (secuencia != null)
/* 411:    */         {
/* 412:470 */           this.prestamo.setSecuencia(secuencia);
/* 413:471 */           numero = this.servicioSecuencia.obtenerSecuencia(secuencia, this.prestamo.getFechaContabilizacion());
/* 414:472 */           this.prestamo.setDocumentoReferencia(numero);
/* 415:    */         }
/* 416:474 */         this.prestamo.setDocumentoReferencia(numero);
/* 417:    */       }
/* 418:    */     }
/* 419:    */     catch (ExcepcionAS2 e)
/* 420:    */     {
/* 421:477 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 422:478 */       e.printStackTrace();
/* 423:    */     }
/* 424:481 */     return "";
/* 425:    */   }
/* 426:    */   
/* 427:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 428:    */   {
/* 429:491 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 430:492 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 431:    */     }
/* 432:495 */     return this.listaCuentaBancariaOrganizacion;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 436:    */   {
/* 437:499 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public Prestamo getPrestamo()
/* 441:    */   {
/* 442:508 */     if (this.prestamo == null) {
/* 443:509 */       this.prestamo = new Prestamo();
/* 444:    */     }
/* 445:511 */     return this.prestamo;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setPrestamo(Prestamo prestamo)
/* 449:    */   {
/* 450:521 */     this.prestamo = prestamo;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public LazyDataModel<Prestamo> getListaPrestamo()
/* 454:    */   {
/* 455:530 */     return this.listaPrestamo;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setListaPrestamo(LazyDataModel<Prestamo> listaPrestamo)
/* 459:    */   {
/* 460:540 */     this.listaPrestamo = listaPrestamo;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public DataTable getDtPrestamo()
/* 464:    */   {
/* 465:549 */     return this.dtPrestamo;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public void setDtPrestamo(DataTable dtPrestamo)
/* 469:    */   {
/* 470:559 */     this.dtPrestamo = dtPrestamo;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public Empleado getEmpleado()
/* 474:    */   {
/* 475:568 */     if (this.empleado == null) {
/* 476:569 */       this.empleado = new Empleado();
/* 477:    */     }
/* 478:571 */     return this.empleado;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void setEmpleado(Empleado empleado)
/* 482:    */   {
/* 483:581 */     this.empleado = empleado;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public List<TipoPrestamo> getListaTipoPrestamo()
/* 487:    */   {
/* 488:590 */     if (this.listaTipoPrestamo == null) {
/* 489:591 */       this.listaTipoPrestamo = this.servicioTipoPrestamo.obtenerListaCombo("nombre", true, null);
/* 490:    */     }
/* 491:593 */     return this.listaTipoPrestamo;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public String getTipoPrestamoSelecionado()
/* 495:    */   {
/* 496:602 */     this.tipoPrestamoSelecionado = "";
/* 497:603 */     if (this.prestamo.getTipoPrestamo() != null) {
/* 498:604 */       this.tipoPrestamoSelecionado = Integer.toString(this.prestamo.getTipoPrestamo().getId());
/* 499:    */     }
/* 500:606 */     return this.tipoPrestamoSelecionado;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setTipoPrestamoSelecionado(String tipoPrestamoSelecionado)
/* 504:    */   {
/* 505:617 */     if (this.tipoPrestamoSelecionado != tipoPrestamoSelecionado)
/* 506:    */     {
/* 507:618 */       this.tipoPrestamoSelecionado = tipoPrestamoSelecionado;
/* 508:619 */       TipoPrestamo auxTipoPrestamo = null;
/* 509:620 */       int idTipoPrestamo = Integer.parseInt(this.tipoPrestamoSelecionado);
/* 510:621 */       auxTipoPrestamo = this.servicioTipoPrestamo.buscarPorId(idTipoPrestamo);
/* 511:622 */       this.prestamo.setTipoPrestamo(auxTipoPrestamo);
/* 512:    */     }
/* 513:    */   }
/* 514:    */   
/* 515:    */   public DataTable getDtDetallePrestamo()
/* 516:    */   {
/* 517:633 */     return this.dtDetallePrestamo;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setDtDetallePrestamo(DataTable dtDetallePrestamo)
/* 521:    */   {
/* 522:643 */     this.dtDetallePrestamo = dtDetallePrestamo;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public SelectItem[] getListaEstadoItem()
/* 526:    */   {
/* 527:647 */     if (this.listaEstadoItem == null)
/* 528:    */     {
/* 529:649 */       List<SelectItem> lista = new ArrayList();
/* 530:650 */       lista.add(new SelectItem("", ""));
/* 531:652 */       for (Estado t : Estado.values())
/* 532:    */       {
/* 533:653 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 534:654 */         lista.add(item);
/* 535:    */       }
/* 536:656 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 537:    */     }
/* 538:659 */     return this.listaEstadoItem;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public String getCuentaBancariaOrganizacionSelecionado()
/* 542:    */   {
/* 543:668 */     this.cuentaBancariaOrganizacionSelecionado = "";
/* 544:669 */     if (this.prestamo.getCuentaBancariaOrganizacion() != null) {
/* 545:670 */       this.cuentaBancariaOrganizacionSelecionado = Integer.toString(getPrestamo().getCuentaBancariaOrganizacion().getId());
/* 546:    */     }
/* 547:672 */     return this.cuentaBancariaOrganizacionSelecionado;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setCuentaBancariaOrganizacionSelecionado(String cuentaBancariaOrganizacionSelecionado)
/* 551:    */   {
/* 552:683 */     if (this.cuentaBancariaOrganizacionSelecionado != cuentaBancariaOrganizacionSelecionado)
/* 553:    */     {
/* 554:684 */       this.cuentaBancariaOrganizacionSelecionado = cuentaBancariaOrganizacionSelecionado;
/* 555:685 */       CuentaBancariaOrganizacion auxCuentaBancariaOrganizacion = null;
/* 556:686 */       int idCuentaBancariaOrganizaciono = Integer.parseInt(this.cuentaBancariaOrganizacionSelecionado);
/* 557:687 */       auxCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.buscarPorId(Integer.valueOf(idCuentaBancariaOrganizaciono));
/* 558:688 */       getPrestamo().setCuentaBancariaOrganizacion(auxCuentaBancariaOrganizacion);
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   public boolean isIndicadorAprobar()
/* 563:    */   {
/* 564:705 */     return this.indicadorAprobar;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setIndicadorAprobar(boolean indicadorAprobar)
/* 568:    */   {
/* 569:715 */     this.indicadorAprobar = indicadorAprobar;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public boolean isIndicadorContabilizar()
/* 573:    */   {
/* 574:724 */     return this.indicadorContabilizar;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setIndicadorContabilizar(boolean indicadorContabilizar)
/* 578:    */   {
/* 579:734 */     this.indicadorContabilizar = indicadorContabilizar;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public BigDecimal getSaldoPrestamo()
/* 583:    */   {
/* 584:743 */     return this.saldoPrestamo;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public void setSaldoPrestamo(BigDecimal saldoPrestamo)
/* 588:    */   {
/* 589:753 */     this.saldoPrestamo = saldoPrestamo;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public BigDecimal getMontoPrestamo()
/* 593:    */   {
/* 594:762 */     return this.montoPrestamo;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public void setMontoPrestamo(BigDecimal montoPrestamo)
/* 598:    */   {
/* 599:772 */     this.montoPrestamo = montoPrestamo;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public String getMascara()
/* 603:    */   {
/* 604:781 */     return this.mascara;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public void setMascara(String mascara)
/* 608:    */   {
/* 609:791 */     this.mascara = mascara;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public List<Prestamo> getListaPrestamoAprobados()
/* 613:    */   {
/* 614:800 */     return this.listaPrestamoAprobados;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public void setListaPrestamoAprobados(List<Prestamo> listaPrestamoAprobados)
/* 618:    */   {
/* 619:810 */     this.listaPrestamoAprobados = listaPrestamoAprobados;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public List<Documento> getListaDocumento()
/* 623:    */   {
/* 624:820 */     if (this.listaDocumento == null) {
/* 625:    */       try
/* 626:    */       {
/* 627:822 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PRESTAMO_NOMINA);
/* 628:    */       }
/* 629:    */       catch (ExcepcionAS2 e)
/* 630:    */       {
/* 631:824 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 632:    */       }
/* 633:    */     }
/* 634:827 */     return this.listaDocumento;
/* 635:    */   }
/* 636:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.PrestamoBean
 * JD-Core Version:    0.7.0.1
 */