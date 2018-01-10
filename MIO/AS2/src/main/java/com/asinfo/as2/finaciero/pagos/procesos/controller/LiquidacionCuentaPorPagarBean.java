/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  11:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*  12:    */ import com.asinfo.as2.entities.DetallePago;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FormaPago;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.Pago;
/*  18:    */ import com.asinfo.as2.entities.Secuencia;
/*  19:    */ import com.asinfo.as2.entities.Sucursal;
/*  20:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.finaciero.contabilidad.procesos.controller.CuentaBancariaOrganizacionBean;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*  27:    */ import com.asinfo.as2.util.AppUtil;
/*  28:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.HashMap;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.annotation.PostConstruct;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.faces.bean.ManagedBean;
/*  38:    */ import javax.faces.bean.ManagedProperty;
/*  39:    */ import javax.faces.bean.ViewScoped;
/*  40:    */ import javax.faces.event.ActionEvent;
/*  41:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  42:    */ import org.apache.log4j.Logger;
/*  43:    */ import org.primefaces.component.datatable.DataTable;
/*  44:    */ import org.primefaces.event.SelectEvent;
/*  45:    */ import org.primefaces.model.LazyDataModel;
/*  46:    */ import org.primefaces.model.SortOrder;
/*  47:    */ 
/*  48:    */ @ManagedBean
/*  49:    */ @ViewScoped
/*  50:    */ public class LiquidacionCuentaPorPagarBean
/*  51:    */   extends PageControllerAS2
/*  52:    */ {
/*  53:    */   private static final long serialVersionUID = -7899753400417860319L;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioPago servicioPago;
/*  56:    */   @EJB
/*  57:    */   private transient ServicioEmpresa servicioEmpresa;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioDocumento servicioDocumento;
/*  60:    */   @EJB
/*  61:    */   private transient ServicioFormaPago servicioFormaPago;
/*  62:    */   @EJB
/*  63:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  64:    */   @EJB
/*  65:    */   private transient ServicioSecuencia servicioSecuencia;
/*  66:    */   private Pago pago;
/*  67:    */   private LazyDataModel<Pago> listaPago;
/*  68:    */   private List<Empresa> listaEmpresa;
/*  69:    */   private List<Documento> listaDocumento;
/*  70:    */   private List<FormaPago> listaFormaPago;
/*  71:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  72:    */   private BigDecimal totalLiquidado;
/*  73:    */   private BigDecimal totalLiquidarFormaPago;
/*  74:    */   private boolean mostrarDialogContabilizar;
/*  75:    */   private boolean mostrarDialogEntregarCheque;
/*  76:    */   private DataTable dtPago;
/*  77:    */   private DataTable dtFormaPago;
/*  78:    */   private DataTable dtFormaPagoDialog;
/*  79:    */   private DataTable dtDetallePago;
/*  80:    */   @ManagedProperty("#{cuentaBancariaOrganizacionBean}")
/*  81:    */   private CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean;
/*  82:    */   
/*  83:    */   @PostConstruct
/*  84:    */   public void init()
/*  85:    */   {
/*  86:107 */     this.listaPago = new LazyDataModel()
/*  87:    */     {
/*  88:    */       private static final long serialVersionUID = 1L;
/*  89:    */       
/*  90:    */       public List<Pago> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  91:    */       {
/*  92:119 */         List<Pago> lista = new ArrayList();
/*  93:    */         
/*  94:121 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  95:    */         
/*  96:123 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_PROVEEDOR.toString());
/*  97:    */         
/*  98:125 */         lista = LiquidacionCuentaPorPagarBean.this.servicioPago.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  99:126 */         LiquidacionCuentaPorPagarBean.this.listaPago.setRowCount(LiquidacionCuentaPorPagarBean.this.servicioPago.contarPorCriterio(filters));
/* 100:    */         
/* 101:128 */         return lista;
/* 102:    */       }
/* 103:    */     };
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String editar()
/* 107:    */   {
/* 108:140 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 109:141 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String guardar()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:153 */       this.servicioPago.guardar(this.pago);
/* 117:154 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 118:155 */       cargarDatos();
/* 119:    */     }
/* 120:    */     catch (ExcepcionAS2Financiero e)
/* 121:    */     {
/* 122:157 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 123:    */     }
/* 124:    */     catch (ExcepcionAS2 e)
/* 125:    */     {
/* 126:159 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 131:162 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 132:    */     }
/* 133:164 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String eliminar()
/* 137:    */   {
/* 138:174 */     return "";
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String limpiar()
/* 142:    */   {
/* 143:184 */     this.pago = null;
/* 144:185 */     crearPago();
/* 145:186 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String cargarDatos()
/* 149:    */   {
/* 150:196 */     setEditado(false);
/* 151:    */     try
/* 152:    */     {
/* 153:198 */       limpiar();
/* 154:    */     }
/* 155:    */     catch (Exception e)
/* 156:    */     {
/* 157:200 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 158:201 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 159:    */     }
/* 160:203 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String crearPago()
/* 164:    */   {
/* 165:207 */     this.pago = new Pago();
/* 166:    */     
/* 167:209 */     this.pago.setIndicadorPagoLiquidacion(true);
/* 168:210 */     this.pago.setFecha(new Date());
/* 169:211 */     this.pago.setValor(BigDecimal.ZERO);
/* 170:212 */     this.pago.setEmpresa(new Empresa());
/* 171:213 */     this.pago.setEstado(Estado.ELABORADO);
/* 172:214 */     this.pago.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 173:215 */     this.pago.setSucursal(AppUtil.getSucursal());
/* 174:    */     
/* 175:217 */     Documento documento = null;
/* 176:218 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 177:    */     {
/* 178:219 */       documento = (Documento)getListaDocumento().get(0);
/* 179:220 */       this.pago.setDocumento(documento);
/* 180:221 */       actualizarDocumento();
/* 181:    */     }
/* 182:    */     else
/* 183:    */     {
/* 184:223 */       documento = new Documento();
/* 185:224 */       documento.setSecuencia(new Secuencia());
/* 186:225 */       this.pago.setDocumento(documento);
/* 187:    */     }
/* 188:228 */     return "";
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void cargarFacturasPendientes()
/* 192:    */   {
/* 193:236 */     if (this.pago.getEmpresa().getId() == 0)
/* 194:    */     {
/* 195:237 */       addErrorMessage(getLanguageController().getMensaje("msg_info_pago_0003"));
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:239 */       this.pago.setValor(BigDecimal.ZERO);
/* 200:240 */       this.servicioPago.cargarFacturasPendientes(this.pago, false, false);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void cargarFacturasPendientesDateSelect()
/* 205:    */   {
/* 206:248 */     cargarFacturasPendientes();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String actualizarDocumento()
/* 210:    */   {
/* 211:258 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getPago().getDocumento().getIdDocumento()));
/* 212:259 */     getPago().setDocumento(documento);
/* 213:    */     
/* 214:261 */     return "";
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String crearFormaPago()
/* 218:    */   {
/* 219:271 */     DetalleFormaPago detalleFormaPago = new DetalleFormaPago();
/* 220:272 */     detalleFormaPago.setPago(getPago());
/* 221:273 */     detalleFormaPago.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/* 222:274 */     detalleFormaPago.setFormaPago(new FormaPago());
/* 223:275 */     detalleFormaPago.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 224:276 */     detalleFormaPago.setIdSucursal(AppUtil.getSucursal().getId());
/* 225:277 */     detalleFormaPago.setValor(getPago().getValor());
/* 226:    */     
/* 227:279 */     getPago().getListaDetalleFormaPago().add(detalleFormaPago);
/* 228:280 */     return "";
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String eliminarFormaPago()
/* 232:    */   {
/* 233:290 */     DetalleFormaPago detalleFormaPago = (DetalleFormaPago)this.dtFormaPago.getRowData();
/* 234:291 */     detalleFormaPago.setEliminado(true);
/* 235:    */     
/* 236:293 */     return "";
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void actualizarProveedor(SelectEvent event)
/* 240:    */   {
/* 241:303 */     getPago().getListaDetallePago().clear();
/* 242:304 */     getPago().setBeneficiario(((Empresa)event.getObject()).getNombreFiscal().trim());
/* 243:307 */     if (getPago().getBeneficiario().length() > 50) {
/* 244:308 */       getPago().setBeneficiario(getPago().getBeneficiario().substring(0, 50));
/* 245:    */     }
/* 246:311 */     for (DetallePago detallePago : this.pago.getListaDetallePago()) {
/* 247:312 */       detallePago.setEliminado(true);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 252:    */   {
/* 253:324 */     DetalleFormaPago detalleFormaPago = (DetalleFormaPago)this.dtFormaPago.getRowData();
/* 254:325 */     detalleFormaPago.setCuentaContableFormaPago(detalleFormaPago.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/* 255:326 */     detalleFormaPago.getCuentaBancariaOrganizacion().setListaFormaPago(this.servicioCuentaBancariaOrganizacion
/* 256:327 */       .cargarDetalle(detalleFormaPago.getCuentaBancariaOrganizacion().getId()).getListaFormaPago());
/* 257:328 */     detalleFormaPago.getCuentaBancariaOrganizacion().setListaCuentaContableCruceCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/* 258:329 */       .cargarDetalle(detalleFormaPago.getCuentaBancariaOrganizacion().getId())
/* 259:330 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion());
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String seleccionarPagoAContabilizar()
/* 263:    */   {
/* 264:339 */     setMostrarDialogContabilizar(true);
/* 265:340 */     Pago pagoSeleccionado = (Pago)this.dtPago.getRowData();
/* 266:341 */     Pago pagoConDetalle = this.servicioPago.cargarDetalle(pagoSeleccionado.getIdPago());
/* 267:342 */     obtenerDetalleFormaFago(pagoConDetalle);
/* 268:343 */     setPago(pagoConDetalle);
/* 269:344 */     return "";
/* 270:    */   }
/* 271:    */   
/* 272:    */   private void obtenerDetalleFormaFago(Pago pago)
/* 273:    */   {
/* 274:351 */     for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago())
/* 275:    */     {
/* 276:352 */       detalleFormaPago.setSecuencia(this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(detalleFormaPago
/* 277:353 */         .getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion(), detalleFormaPago.getFormaPago().getIdFormaPago()));
/* 278:354 */       cargarSecuencia(detalleFormaPago);
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void contabilizarPago(ActionEvent aev)
/* 283:    */   {
/* 284:    */     try
/* 285:    */     {
/* 286:365 */       Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.pago.getDocumento().getId()));
/* 287:366 */       this.pago.getDocumento().setTipoAsiento(documento.getTipoAsiento());
/* 288:367 */       this.pago.setEstado(Estado.CONTABILIZADO);
/* 289:368 */       this.servicioPago.guardar(this.pago);
/* 290:369 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 291:    */     }
/* 292:    */     catch (ExcepcionAS2Financiero e)
/* 293:    */     {
/* 294:371 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 295:372 */       LOG.error("ERROR AL CONTABILIZAR PAGO", e);
/* 296:    */     }
/* 297:    */     catch (ExcepcionAS2 e)
/* 298:    */     {
/* 299:374 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 300:375 */       LOG.error("ERROR AL CONTABILIZAR PAGO", e);
/* 301:    */     }
/* 302:    */     catch (Exception e)
/* 303:    */     {
/* 304:377 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 305:378 */       LOG.error("ERROR AL CONTABILIZAR PAGO", e);
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void calcularValorAPagar()
/* 310:    */   {
/* 311:388 */     BigDecimal valorTmp = BigDecimal.ZERO;
/* 312:389 */     int numeroLineas = 0;
/* 313:390 */     for (DetallePago detallePago : getPago().getListaDetallePago()) {
/* 314:391 */       if ((!detallePago.isEliminado()) && (detallePago.getValor().compareTo(BigDecimal.ZERO) > 0))
/* 315:    */       {
/* 316:392 */         numeroLineas++;
/* 317:393 */         if ((ParametrosSistema.getNumeroMaximoLineasPago(AppUtil.getOrganizacion().getIdOrganizacion()).intValue() > 0) && 
/* 318:394 */           (numeroLineas > ParametrosSistema.getNumeroMaximoLineasPago(AppUtil.getOrganizacion().getIdOrganizacion()).intValue()))
/* 319:    */         {
/* 320:395 */           addInfoMessage(getLanguageController().getMensaje("msg_info_numero_maximo_lineas_pago_excedido"));
/* 321:396 */           detallePago.setValor(BigDecimal.ZERO);
/* 322:    */         }
/* 323:    */         else
/* 324:    */         {
/* 325:398 */           valorTmp = valorTmp.add(detallePago.getValor());
/* 326:    */         }
/* 327:    */       }
/* 328:    */     }
/* 329:403 */     this.pago.setValor(valorTmp);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String cargarValorCuota()
/* 333:    */   {
/* 334:407 */     DetallePago d = (DetallePago)this.dtDetallePago.getRowData();
/* 335:408 */     d.setValor(d.getCuentaPorPagar().getSaldo());
/* 336:409 */     calcularValorAPagar();
/* 337:410 */     return "";
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String limpiarValorCuota()
/* 341:    */   {
/* 342:414 */     DetallePago d = (DetallePago)this.dtDetallePago.getRowData();
/* 343:415 */     d.setValor(BigDecimal.ZERO);
/* 344:416 */     calcularValorAPagar();
/* 345:    */     
/* 346:418 */     return "";
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void actualizarFormaPago()
/* 350:    */   {
/* 351:422 */     DetalleFormaPago d = (DetalleFormaPago)this.dtFormaPago.getRowData();
/* 352:423 */     cargarSecuencia(d);
/* 353:    */   }
/* 354:    */   
/* 355:    */   private void cargarSecuencia(DetalleFormaPago d)
/* 356:    */   {
/* 357:427 */     d.setSecuencia(this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(d.getCuentaBancariaOrganizacion()
/* 358:428 */       .getIdCuentaBancariaOrganizacion(), d.getFormaPago().getIdFormaPago()));
/* 359:429 */     String numero = "";
/* 360:430 */     if (d.getSecuencia() != null) {
/* 361:    */       try
/* 362:    */       {
/* 363:433 */         numero = this.servicioSecuencia.obtenerSecuencia(d.getSecuencia(), this.pago.getFecha());
/* 364:434 */         d.setDocumentoReferencia(numero);
/* 365:    */       }
/* 366:    */       catch (ExcepcionAS2 e)
/* 367:    */       {
/* 368:436 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 369:437 */         e.printStackTrace();
/* 370:    */       }
/* 371:    */     }
/* 372:440 */     d.setDocumentoReferencia(numero);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public Pago getPago()
/* 376:    */   {
/* 377:451 */     if (this.pago == null) {
/* 378:452 */       crearPago();
/* 379:    */     }
/* 380:454 */     return this.pago;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setPago(Pago pago)
/* 384:    */   {
/* 385:464 */     this.pago = pago;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public List<Empresa> getListaEmpresa()
/* 389:    */   {
/* 390:473 */     if (this.listaEmpresa == null) {
/* 391:474 */       this.listaEmpresa = this.servicioEmpresa.obtenerProveedores();
/* 392:    */     }
/* 393:476 */     return this.listaEmpresa;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/* 397:    */   {
/* 398:486 */     this.listaEmpresa = listaEmpresa;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public List<Documento> getListaDocumento()
/* 402:    */   {
/* 403:496 */     if (this.listaDocumento == null) {
/* 404:    */       try
/* 405:    */       {
/* 406:498 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_PROVEEDOR);
/* 407:    */       }
/* 408:    */       catch (ExcepcionAS2 e)
/* 409:    */       {
/* 410:500 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 411:    */       }
/* 412:    */     }
/* 413:503 */     return this.listaDocumento;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 417:    */   {
/* 418:513 */     this.listaDocumento = listaDocumento;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public BigDecimal getTotalLiquidado()
/* 422:    */   {
/* 423:522 */     this.totalLiquidado = BigDecimal.ZERO;
/* 424:524 */     for (DetallePago detallePago : getPago().getListaDetallePago()) {
/* 425:525 */       if (!detallePago.isEliminado()) {
/* 426:526 */         this.totalLiquidado = this.totalLiquidado.add(detallePago.getValor());
/* 427:    */       }
/* 428:    */     }
/* 429:529 */     return this.totalLiquidado;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/* 433:    */   {
/* 434:539 */     this.totalLiquidado = totalLiquidado;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public CuentaBancariaOrganizacionBean getCuentaBancariaOrganizacionBean()
/* 438:    */   {
/* 439:548 */     return this.cuentaBancariaOrganizacionBean;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setCuentaBancariaOrganizacionBean(CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean)
/* 443:    */   {
/* 444:558 */     this.cuentaBancariaOrganizacionBean = cuentaBancariaOrganizacionBean;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public List<DetallePago> getListaDetallePago()
/* 448:    */   {
/* 449:569 */     List<DetallePago> lista = new ArrayList();
/* 450:571 */     for (DetallePago detallePago : getPago().getListaDetallePago()) {
/* 451:572 */       if (!detallePago.isEliminado()) {
/* 452:573 */         lista.add(detallePago);
/* 453:    */       }
/* 454:    */     }
/* 455:577 */     return lista;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public List<DetalleFormaPago> getListaDetalleFormaPago()
/* 459:    */   {
/* 460:587 */     List<DetalleFormaPago> lista = new ArrayList();
/* 461:589 */     for (DetalleFormaPago detalleFormaPago : getPago().getListaDetalleFormaPago()) {
/* 462:590 */       if (!detalleFormaPago.isEliminado()) {
/* 463:591 */         lista.add(detalleFormaPago);
/* 464:    */       }
/* 465:    */     }
/* 466:595 */     return lista;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public DataTable getDtPago()
/* 470:    */   {
/* 471:599 */     return this.dtPago;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setDtPago(DataTable dtPago)
/* 475:    */   {
/* 476:603 */     this.dtPago = dtPago;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public DataTable getDtFormaPago()
/* 480:    */   {
/* 481:612 */     return this.dtFormaPago;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setDtFormaPago(DataTable dtFormaPago)
/* 485:    */   {
/* 486:622 */     this.dtFormaPago = dtFormaPago;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public LazyDataModel<Pago> getListaPago()
/* 490:    */   {
/* 491:631 */     return this.listaPago;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setListaPago(LazyDataModel<Pago> listaPago)
/* 495:    */   {
/* 496:641 */     this.listaPago = listaPago;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public List<FormaPago> getListaFormaPago()
/* 500:    */   {
/* 501:646 */     if (this.listaFormaPago == null)
/* 502:    */     {
/* 503:647 */       HashMap<String, String> filters = new HashMap();
/* 504:648 */       filters.put("indicadorPago", "true");
/* 505:649 */       this.listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", true, filters);
/* 506:    */     }
/* 507:651 */     return this.listaFormaPago;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setListaFormaPago(List<FormaPago> listaFormaPago)
/* 511:    */   {
/* 512:655 */     this.listaFormaPago = listaFormaPago;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 516:    */   {
/* 517:659 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 518:660 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/* 519:    */     }
/* 520:662 */     return this.listaCuentaBancariaOrganizacion;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 524:    */   {
/* 525:666 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 529:    */   {
/* 530:670 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 531:    */   }
/* 532:    */   
/* 533:    */   public boolean isMostrarDialogContabilizar()
/* 534:    */   {
/* 535:679 */     return this.mostrarDialogContabilizar;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public void setMostrarDialogContabilizar(boolean mostrarDialogContabilizar)
/* 539:    */   {
/* 540:689 */     this.mostrarDialogContabilizar = mostrarDialogContabilizar;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public DataTable getDtFormaPagoDialog()
/* 544:    */   {
/* 545:698 */     return this.dtFormaPagoDialog;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public void setDtFormaPagoDialog(DataTable dtFormaPagoDialog)
/* 549:    */   {
/* 550:708 */     this.dtFormaPagoDialog = dtFormaPagoDialog;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public DataTable getDtDetallePago()
/* 554:    */   {
/* 555:717 */     return this.dtDetallePago;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void setDtDetallePago(DataTable dtDetallePago)
/* 559:    */   {
/* 560:727 */     this.dtDetallePago = dtDetallePago;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public boolean isMostrarDialogEntregarCheque()
/* 564:    */   {
/* 565:736 */     return this.mostrarDialogEntregarCheque;
/* 566:    */   }
/* 567:    */   
/* 568:    */   public void setMostrarDialogEntregarCheque(boolean mostrarDialogEntregarCheque)
/* 569:    */   {
/* 570:746 */     this.mostrarDialogEntregarCheque = mostrarDialogEntregarCheque;
/* 571:    */   }
/* 572:    */   
/* 573:    */   public BigDecimal getTotalLiquidarFormaPago()
/* 574:    */   {
/* 575:755 */     this.totalLiquidarFormaPago = BigDecimal.ZERO;
/* 576:756 */     for (DetalleFormaPago detalleFormaPago : getPago().getListaDetalleFormaPago()) {
/* 577:757 */       if (!detalleFormaPago.isEliminado()) {
/* 578:758 */         this.totalLiquidarFormaPago = this.totalLiquidarFormaPago.add(detalleFormaPago.getValor());
/* 579:    */       }
/* 580:    */     }
/* 581:761 */     return this.totalLiquidarFormaPago;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public void setTotalLiquidarFormaPago(BigDecimal totalLiquidarFormaPago)
/* 585:    */   {
/* 586:771 */     this.totalLiquidarFormaPago = totalLiquidarFormaPago;
/* 587:    */   }
/* 588:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.LiquidacionCuentaPorPagarBean
 * JD-Core Version:    0.7.0.1
 */