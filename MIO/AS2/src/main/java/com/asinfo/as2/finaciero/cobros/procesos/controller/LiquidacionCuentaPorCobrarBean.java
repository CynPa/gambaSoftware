/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Banco;
/*   8:    */ import com.asinfo.as2.entities.Cobro;
/*   9:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  11:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  12:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  13:    */ import com.asinfo.as2.entities.Documento;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.FormaPago;
/*  16:    */ import com.asinfo.as2.entities.GarantiaCliente;
/*  17:    */ import com.asinfo.as2.entities.Organizacion;
/*  18:    */ import com.asinfo.as2.entities.Secuencia;
/*  19:    */ import com.asinfo.as2.entities.Sucursal;
/*  20:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  21:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.finaciero.contabilidad.procesos.controller.CuentaBancariaOrganizacionBean;
/*  24:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  25:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  26:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente;
/*  27:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  28:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  29:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  30:    */ import com.asinfo.as2.util.AppUtil;
/*  31:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  32:    */ import java.math.BigDecimal;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.annotation.PostConstruct;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.faces.bean.ManagedBean;
/*  40:    */ import javax.faces.bean.ManagedProperty;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  43:    */ import org.apache.log4j.Logger;
/*  44:    */ import org.primefaces.component.datatable.DataTable;
/*  45:    */ import org.primefaces.event.SelectEvent;
/*  46:    */ import org.primefaces.model.LazyDataModel;
/*  47:    */ import org.primefaces.model.SortOrder;
/*  48:    */ 
/*  49:    */ @ManagedBean
/*  50:    */ @ViewScoped
/*  51:    */ public class LiquidacionCuentaPorCobrarBean
/*  52:    */   extends PageControllerAS2
/*  53:    */ {
/*  54:    */   private static final long serialVersionUID = 1425588770056975545L;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioCobro servicioCobro;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioEmpresa servicioEmpresa;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioDocumento servicioDocumento;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioGarantiaCliente servicioGarantiaCliente;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioGenerico<Banco> servicioBanco;
/*  67:    */   private Cobro cobro;
/*  68:    */   private DetalleFormaCobro detalleFormaCobroSeleccionado;
/*  69:    */   private LazyDataModel<Cobro> listaCobro;
/*  70:    */   private List<Empresa> listaEmpresa;
/*  71:    */   private List<Documento> listaDocumento;
/*  72:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  73:    */   private List<GarantiaCliente> listaChequesPosfechados;
/*  74:    */   private List<Banco> listaBanco;
/*  75: 92 */   private BigDecimal totalLiquidado = BigDecimal.ZERO;
/*  76:    */   private BigDecimal totalLiquidarFormaCobro;
/*  77:    */   private Date fechaProtesto;
/*  78:    */   private DataTable dtCobro;
/*  79:    */   private DataTable dtFormaCobro;
/*  80:    */   private DataTable dtChequesPosfechados;
/*  81:    */   private DataTable dtGarantiaCliente;
/*  82:    */   private DataTable dtDetalleFormaCobroProtesto;
/*  83:    */   private String numero;
/*  84:    */   @ManagedProperty("#{cuentaBancariaOrganizacionBean}")
/*  85:    */   private CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean;
/*  86:    */   
/*  87:    */   @PostConstruct
/*  88:    */   public void init()
/*  89:    */   {
/*  90:112 */     limpiar();
/*  91:    */     
/*  92:114 */     this.listaCobro = new LazyDataModel()
/*  93:    */     {
/*  94:    */       private static final long serialVersionUID = 1L;
/*  95:    */       
/*  96:    */       public List<Cobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  97:    */       {
/*  98:121 */         List<Cobro> lista = new ArrayList();
/*  99:    */         
/* 100:123 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 101:125 */         if (LiquidacionCuentaPorCobrarBean.this.numero != null) {
/* 102:126 */           filters.put("numero", LiquidacionCuentaPorCobrarBean.this.numero);
/* 103:    */         }
/* 104:129 */         lista = LiquidacionCuentaPorCobrarBean.this.servicioCobro.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 105:    */         
/* 106:131 */         LiquidacionCuentaPorCobrarBean.this.listaCobro.setRowCount(LiquidacionCuentaPorCobrarBean.this.servicioCobro.contarPorCriterio(filters));
/* 107:    */         
/* 108:133 */         return lista;
/* 109:    */       }
/* 110:    */     };
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String editar()
/* 114:    */   {
/* 115:146 */     if ((getCobro() != null) && (getCobro().getId() > 0)) {
/* 116:    */       try
/* 117:    */       {
/* 118:150 */         this.servicioCobro.esEditable(this.cobro);
/* 119:151 */         this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getId());
/* 120:154 */         for (DetalleCobro detalleCobro : this.cobro.getListaDetalleCobro())
/* 121:    */         {
/* 122:155 */           BigDecimal saldo = detalleCobro.getCuentaPorCobrar().getSaldo();
/* 123:156 */           detalleCobro.getCuentaPorCobrar().setSaldo(saldo.add(detalleCobro.getValor()));
/* 124:    */         }
/* 125:159 */         setEditado(true);
/* 126:    */       }
/* 127:    */       catch (ExcepcionAS2Financiero e)
/* 128:    */       {
/* 129:162 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 130:163 */         LOG.info("ERROR AL EDITAR COBRO CLIENTE");
/* 131:164 */         e.printStackTrace();
/* 132:    */       }
/* 133:    */     } else {
/* 134:168 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 135:    */     }
/* 136:171 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String guardar()
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:183 */       this.servicioCobro.guardar(this.cobro);
/* 144:184 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 145:185 */       limpiar();
/* 146:    */     }
/* 147:    */     catch (ExcepcionAS2Financiero e)
/* 148:    */     {
/* 149:188 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 150:    */     }
/* 151:    */     catch (ExcepcionAS2 e)
/* 152:    */     {
/* 153:190 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 154:    */     }
/* 155:    */     catch (Exception e)
/* 156:    */     {
/* 157:192 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 158:193 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 159:    */     }
/* 160:195 */     return "";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String eliminar()
/* 164:    */   {
/* 165:205 */     anularCobro();
/* 166:206 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String limpiar()
/* 170:    */   {
/* 171:216 */     setEditado(false);
/* 172:217 */     crearCobro();
/* 173:218 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String cargarDatos()
/* 177:    */   {
/* 178:228 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String crearCobro()
/* 182:    */   {
/* 183:237 */     this.cobro = new Cobro();
/* 184:238 */     this.cobro.setIndicadorCobroLiquidacion(true);
/* 185:239 */     this.cobro.setValor(BigDecimal.ZERO);
/* 186:240 */     this.cobro.setFecha(new Date());
/* 187:241 */     this.cobro.setEmpresa(new Empresa());
/* 188:242 */     this.cobro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 189:243 */     this.cobro.setSucursal(AppUtil.getSucursal());
/* 190:244 */     this.cobro.setFecha(new Date());
/* 191:    */     
/* 192:246 */     Documento documento = null;
/* 193:247 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 194:    */     {
/* 195:248 */       documento = (Documento)getListaDocumento().get(0);
/* 196:249 */       this.cobro.setDocumento(documento);
/* 197:250 */       actualizarDocumento();
/* 198:    */     }
/* 199:    */     else
/* 200:    */     {
/* 201:252 */       documento = new Documento();
/* 202:253 */       documento.setSecuencia(new Secuencia());
/* 203:254 */       this.cobro.setDocumento(documento);
/* 204:    */     }
/* 205:257 */     this.cobro.setEstado(Estado.ELABORADO);
/* 206:    */     
/* 207:259 */     setDetalleFormaCobroSeleccionado(null);
/* 208:    */     
/* 209:261 */     return "";
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void cargarFacturasPendientes()
/* 213:    */   {
/* 214:270 */     if (this.cobro.getEmpresa().getId() == 0)
/* 215:    */     {
/* 216:271 */       addErrorMessage(getLanguageController().getMensaje("msg_info_cobro_0003"));
/* 217:    */     }
/* 218:    */     else
/* 219:    */     {
/* 220:273 */       this.cobro.setValor(BigDecimal.ZERO);
/* 221:274 */       this.servicioCobro.cargarFacturasPendientes(this.cobro);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void cargarFacturasPendientesSelectEvent()
/* 226:    */   {
/* 227:282 */     cargarFacturasPendientes();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String actualizarDocumento()
/* 231:    */   {
/* 232:292 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getCobro().getDocumento().getIdDocumento()));
/* 233:293 */     getCobro().setDocumento(documento);
/* 234:    */     
/* 235:295 */     return "";
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 239:    */   {
/* 240:309 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtFormaCobro.getRowData();
/* 241:310 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaFormaPago(this.servicioCuentaBancariaOrganizacion
/* 242:311 */       .cargarDetalle(detalleFormaCobro.getCuentaBancariaOrganizacion().getId()).getListaFormaPago());
/* 243:312 */     detalleFormaCobro.setCuentaContableFormaCobro(detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/* 244:313 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaCuentaContableCruceCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/* 245:314 */       .cargarDetalle(detalleFormaCobro.getCuentaBancariaOrganizacion().getId())
/* 246:315 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion());
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String crearFormaCobro()
/* 250:    */   {
/* 251:325 */     DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/* 252:326 */     detalleFormaCobro.setCaja(AppUtil.getCaja());
/* 253:327 */     detalleFormaCobro.setCobro(getCobro());
/* 254:328 */     detalleFormaCobro.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/* 255:329 */     detalleFormaCobro.setFormaPago(new FormaPago());
/* 256:330 */     detalleFormaCobro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 257:331 */     detalleFormaCobro.setIdSucursal(AppUtil.getSucursal().getId());
/* 258:332 */     detalleFormaCobro.setValor(getCobro().getValor());
/* 259:    */     
/* 260:334 */     getCobro().getListaDetalleFormaCobro().add(detalleFormaCobro);
/* 261:    */     
/* 262:336 */     return "";
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String eliminarFormaCobro()
/* 266:    */   {
/* 267:346 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtFormaCobro.getRowData();
/* 268:347 */     detalleFormaCobro.setEliminado(true);
/* 269:348 */     return "";
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String actualizarFechaCobro()
/* 273:    */   {
/* 274:357 */     Date fechaIngreso = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getFechaIngreso();
/* 275:358 */     int dias = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getDiasCreditoOtorgado();
/* 276:359 */     fechaIngreso = FuncionesUtiles.sumarFechaDiasMeses(fechaIngreso, dias);
/* 277:360 */     this.detalleFormaCobroSeleccionado.getGarantiaCliente().setFechaCobro(fechaIngreso);
/* 278:    */     
/* 279:362 */     return "";
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String actualizarDiasCreditoOtorgado()
/* 283:    */   {
/* 284:371 */     Date fechaCobro = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getFechaCobro();
/* 285:372 */     Date fechaIngreso = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getFechaIngreso();
/* 286:373 */     int diasCredito = FuncionesUtiles.diferenciasDeFechas(fechaIngreso, fechaCobro) + 1;
/* 287:374 */     this.detalleFormaCobroSeleccionado.getGarantiaCliente().setDiasCreditoOtorgado(diasCredito);
/* 288:    */     
/* 289:376 */     return "";
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String cargarListaGarantiaCliente()
/* 293:    */   {
/* 294:385 */     this.cobro = ((Cobro)this.dtCobro.getRowData());
/* 295:386 */     this.fechaProtesto = new Date();
/* 296:387 */     return "";
/* 297:    */   }
/* 298:    */   
/* 299:    */   public String actualizarGarantiaCliente()
/* 300:    */   {
/* 301:395 */     setDetalleFormaCobroSeleccionado((DetalleFormaCobro)this.dtFormaCobro.getRowData());
/* 302:    */     
/* 303:397 */     this.servicioCobro.actualizarGarantiaCliente(getDetalleFormaCobroSeleccionado());
/* 304:    */     
/* 305:399 */     GarantiaCliente garantiaCliente = getDetalleFormaCobroSeleccionado().getGarantiaCliente();
/* 306:401 */     if (garantiaCliente != null)
/* 307:    */     {
/* 308:403 */       actualizarFechaCobro();
/* 309:405 */       if ((garantiaCliente.getRecibidoPor() == null) || (garantiaCliente.getRecibidoPor().isEmpty())) {
/* 310:406 */         garantiaCliente.setRecibidoPor(AppUtil.getUsuarioEnSesion().getNombre1() + " " + AppUtil.getUsuarioEnSesion().getNombre2());
/* 311:    */       }
/* 312:409 */       if ((garantiaCliente.getGirador() == null) || (garantiaCliente.getGirador().isEmpty())) {
/* 313:410 */         garantiaCliente.setGirador(this.cobro.getEmpresa().getNombreFiscal());
/* 314:    */       }
/* 315:    */     }
/* 316:414 */     return "";
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String refrescar()
/* 320:    */   {
/* 321:418 */     return "";
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void actualizarCliente(SelectEvent event)
/* 325:    */   {
/* 326:422 */     getCobro().getListaDetalleCobro().clear();
/* 327:424 */     for (DetalleCobro detalleCobro : this.cobro.getListaDetalleCobro()) {
/* 328:425 */       detalleCobro.setEliminado(true);
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void onRowSelect(SelectEvent event)
/* 333:    */   {
/* 334:435 */     this.cobro = ((Cobro)event.getObject());
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void anularCobro()
/* 338:    */   {
/* 339:444 */     if (this.cobro.getId() > 0) {
/* 340:    */       try
/* 341:    */       {
/* 342:447 */         this.servicioCobro.anularCobro(this.cobro);
/* 343:448 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 344:    */       }
/* 345:    */       catch (ExcepcionAS2Financiero e)
/* 346:    */       {
/* 347:451 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 348:452 */         LOG.info("ERROR AL ANULAR PAGO", e);
/* 349:    */       }
/* 350:    */       catch (ExcepcionAS2 e)
/* 351:    */       {
/* 352:455 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 353:456 */         LOG.info("ERROR AL ANULAR PAGO", e);
/* 354:    */       }
/* 355:    */     } else {
/* 356:460 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   public Cobro getCobro()
/* 361:    */   {
/* 362:470 */     return this.cobro;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setCobro(Cobro cobro)
/* 366:    */   {
/* 367:480 */     this.cobro = cobro;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public DataTable getDtCobro()
/* 371:    */   {
/* 372:489 */     return this.dtCobro;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setDtCobro(DataTable dtCobro)
/* 376:    */   {
/* 377:499 */     this.dtCobro = dtCobro;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public CuentaBancariaOrganizacionBean getCuentaBancariaOrganizacionBean()
/* 381:    */   {
/* 382:508 */     return this.cuentaBancariaOrganizacionBean;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setCuentaBancariaOrganizacionBean(CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean)
/* 386:    */   {
/* 387:518 */     this.cuentaBancariaOrganizacionBean = cuentaBancariaOrganizacionBean;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<Empresa> getListaEmpresa()
/* 391:    */   {
/* 392:527 */     if (this.listaEmpresa == null) {
/* 393:528 */       this.listaEmpresa = this.servicioEmpresa.obtenerClientes();
/* 394:    */     }
/* 395:530 */     return this.listaEmpresa;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/* 399:    */   {
/* 400:540 */     this.listaEmpresa = listaEmpresa;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public List<Documento> getListaDocumento()
/* 404:    */   {
/* 405:550 */     if (this.listaDocumento == null) {
/* 406:    */       try
/* 407:    */       {
/* 408:552 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.COBRO_CLIENTE);
/* 409:    */       }
/* 410:    */       catch (ExcepcionAS2 e)
/* 411:    */       {
/* 412:554 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 413:    */       }
/* 414:    */     }
/* 415:557 */     return this.listaDocumento;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 419:    */   {
/* 420:567 */     this.listaDocumento = listaDocumento;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public List<DetalleCobro> getListaDetalleCobro()
/* 424:    */   {
/* 425:578 */     List<DetalleCobro> lista = new ArrayList();
/* 426:580 */     for (DetalleCobro detalleCobro : getCobro().getListaDetalleCobro()) {
/* 427:581 */       if (!detalleCobro.isEliminado()) {
/* 428:582 */         lista.add(detalleCobro);
/* 429:    */       }
/* 430:    */     }
/* 431:586 */     return lista;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public List<DetalleFormaCobro> getListaDetalleFormaCobro()
/* 435:    */   {
/* 436:596 */     List<DetalleFormaCobro> lista = new ArrayList();
/* 437:598 */     for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/* 438:599 */       if (!detalleFormaCobro.isEliminado()) {
/* 439:600 */         lista.add(detalleFormaCobro);
/* 440:    */       }
/* 441:    */     }
/* 442:603 */     return lista;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public BigDecimal getTotalLiquidado()
/* 446:    */   {
/* 447:607 */     this.totalLiquidado = BigDecimal.ZERO;
/* 448:609 */     for (DetalleCobro detalleCobro : getCobro().getListaDetalleCobro()) {
/* 449:610 */       if (!detalleCobro.isEliminado()) {
/* 450:611 */         this.totalLiquidado = this.totalLiquidado.add(detalleCobro.getValor());
/* 451:    */       }
/* 452:    */     }
/* 453:614 */     return this.totalLiquidado;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/* 457:    */   {
/* 458:618 */     this.totalLiquidado = totalLiquidado;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public DataTable getDtFormaCobro()
/* 462:    */   {
/* 463:627 */     return this.dtFormaCobro;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setDtFormaCobro(DataTable dtFormaCobro)
/* 467:    */   {
/* 468:637 */     this.dtFormaCobro = dtFormaCobro;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public LazyDataModel<Cobro> getListaCobro()
/* 472:    */   {
/* 473:646 */     return this.listaCobro;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setListaCobro(LazyDataModel<Cobro> listaCobro)
/* 477:    */   {
/* 478:656 */     this.listaCobro = listaCobro;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 482:    */   {
/* 483:660 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 484:661 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", false, null);
/* 485:    */     }
/* 486:663 */     return this.listaCuentaBancariaOrganizacion;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 490:    */   {
/* 491:667 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public List<GarantiaCliente> getListaChequesPosfechados()
/* 495:    */   {
/* 496:671 */     if (this.listaChequesPosfechados == null) {
/* 497:672 */       this.listaChequesPosfechados = this.servicioGarantiaCliente.buscaListaChequesPosfechados(this.cobro.getEmpresa().getId());
/* 498:    */     }
/* 499:674 */     return this.listaChequesPosfechados;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setListaChequesPosfechados(List<GarantiaCliente> listaChequesPosfechados)
/* 503:    */   {
/* 504:678 */     this.listaChequesPosfechados = listaChequesPosfechados;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public DataTable getDtChequesPosfechados()
/* 508:    */   {
/* 509:682 */     return this.dtChequesPosfechados;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setDtChequesPosfechados(DataTable dtChequesPosfechados)
/* 513:    */   {
/* 514:686 */     this.dtChequesPosfechados = dtChequesPosfechados;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public DetalleFormaCobro getDetalleFormaCobroSeleccionado()
/* 518:    */   {
/* 519:690 */     return this.detalleFormaCobroSeleccionado;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setDetalleFormaCobroSeleccionado(DetalleFormaCobro detalleFormaCobroSeleccionado)
/* 523:    */   {
/* 524:694 */     this.detalleFormaCobroSeleccionado = detalleFormaCobroSeleccionado;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 528:    */   {
/* 529:698 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 530:    */   }
/* 531:    */   
/* 532:    */   public List<Banco> getListaBanco()
/* 533:    */   {
/* 534:707 */     if (this.listaBanco == null) {
/* 535:708 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/* 536:    */     }
/* 537:710 */     return this.listaBanco;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setListaBanco(List<Banco> listaBanco)
/* 541:    */   {
/* 542:720 */     this.listaBanco = listaBanco;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public List<GarantiaCliente> getListaGarantiaCliente()
/* 546:    */   {
/* 547:729 */     List<GarantiaCliente> lista = new ArrayList();
/* 548:730 */     if ((this.cobro != null) && (!isEditado()) && (this.cobro.getId() != 0))
/* 549:    */     {
/* 550:731 */       this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getIdCobro());
/* 551:732 */       for (DetalleFormaCobro dfc : this.cobro.getListaDetalleFormaCobro()) {
/* 552:733 */         if (dfc.getGarantiaCliente() != null)
/* 553:    */         {
/* 554:734 */           dfc.getGarantiaCliente().setValorProtestado(dfc
/* 555:735 */             .getGarantiaCliente().getDetalleFormaCobro().getCuentaBancariaOrganizacion().getValorProtesto());
/* 556:736 */           lista.add(dfc.getGarantiaCliente());
/* 557:    */         }
/* 558:    */       }
/* 559:    */     }
/* 560:740 */     return lista;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public List<DetalleFormaCobro> getListaDetalleFormaCobroProtesto()
/* 564:    */   {
/* 565:749 */     List<DetalleFormaCobro> lista = new ArrayList();
/* 566:750 */     if ((this.cobro != null) && (!isEditado()) && (this.cobro.getId() != 0))
/* 567:    */     {
/* 568:751 */       this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getIdCobro());
/* 569:752 */       for (DetalleFormaCobro detalleFormaCobro : this.cobro.getListaDetalleFormaCobro()) {
/* 570:753 */         detalleFormaCobro.setValorProtestado(detalleFormaCobro.getCuentaBancariaOrganizacion().getValorProtesto());
/* 571:    */       }
/* 572:755 */       lista = this.cobro.getListaDetalleFormaCobro();
/* 573:    */     }
/* 574:757 */     return lista;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public DataTable getDtGarantiaCliente()
/* 578:    */   {
/* 579:766 */     return this.dtGarantiaCliente;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void setDtGarantiaCliente(DataTable dtGarantiaCliente)
/* 583:    */   {
/* 584:776 */     this.dtGarantiaCliente = dtGarantiaCliente;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public Date getFechaProtesto()
/* 588:    */   {
/* 589:785 */     return this.fechaProtesto;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void setFechaProtesto(Date fechaProtesto)
/* 593:    */   {
/* 594:795 */     this.fechaProtesto = fechaProtesto;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public BigDecimal getTotalLiquidarFormaCobro()
/* 598:    */   {
/* 599:804 */     this.totalLiquidarFormaCobro = BigDecimal.ZERO;
/* 600:805 */     for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/* 601:806 */       if (!detalleFormaCobro.isEliminado()) {
/* 602:807 */         this.totalLiquidarFormaCobro = this.totalLiquidarFormaCobro.add(detalleFormaCobro.getValor());
/* 603:    */       }
/* 604:    */     }
/* 605:811 */     return this.totalLiquidarFormaCobro;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public void setTotalLiquidarFormaCobro(BigDecimal totalLiquidarFormaCobro)
/* 609:    */   {
/* 610:821 */     this.totalLiquidarFormaCobro = totalLiquidarFormaCobro;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public String getNumero()
/* 614:    */   {
/* 615:830 */     return this.numero;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public void setNumero(String numero)
/* 619:    */   {
/* 620:840 */     this.numero = numero;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public DataTable getDtDetalleFormaCobroProtesto()
/* 624:    */   {
/* 625:849 */     return this.dtDetalleFormaCobroProtesto;
/* 626:    */   }
/* 627:    */   
/* 628:    */   public void setDtDetalleFormaCobroProtesto(DataTable dtDetalleFormaCobroProtesto)
/* 629:    */   {
/* 630:859 */     this.dtDetalleFormaCobroProtesto = dtDetalleFormaCobroProtesto;
/* 631:    */   }
/* 632:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.LiquidacionCuentaPorCobrarBean
 * JD-Core Version:    0.7.0.1
 */