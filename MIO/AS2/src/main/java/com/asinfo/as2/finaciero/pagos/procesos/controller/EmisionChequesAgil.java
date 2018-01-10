/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.CuentaPorPagar;
/*  10:    */ import com.asinfo.as2.entities.DetalleFormaPago;
/*  11:    */ import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.FormaPago;
/*  14:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  15:    */ import com.asinfo.as2.entities.OrdenPagoProveedor;
/*  16:    */ import com.asinfo.as2.entities.Organizacion;
/*  17:    */ import com.asinfo.as2.entities.Pago;
/*  18:    */ import com.asinfo.as2.entities.Proveedor;
/*  19:    */ import com.asinfo.as2.entities.Secuencia;
/*  20:    */ import com.asinfo.as2.entities.Sucursal;
/*  21:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  22:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  23:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  24:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor;
/*  27:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.Date;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedBean;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import javax.faces.event.ActionEvent;
/*  39:    */ import javax.faces.event.AjaxBehaviorEvent;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.component.datatable.DataTable;
/*  42:    */ 
/*  43:    */ @ManagedBean
/*  44:    */ @ViewScoped
/*  45:    */ public class EmisionChequesAgil
/*  46:    */   extends OrdenPagoProveedorBean
/*  47:    */ {
/*  48:    */   private static final long serialVersionUID = 7085091448710210515L;
/*  49:    */   @EJB
/*  50:    */   private transient ServicioFormaPago servicioFormaPago;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioSecuencia servicioSecuencia;
/*  55:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  56:    */   private List<FormaPagoCuentaBancariaOrganizacion> listaFormaPagoCuentaBancariaOrganizacion;
/*  57:    */   private List<Proveedor> listaProveedorFiltrado;
/*  58:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  59:    */   private FormaPago formaPago;
/*  60:    */   private Documento documento;
/*  61:    */   private Date fechaPago;
/*  62:    */   private String descripcion;
/*  63:    */   private Secuencia secuencia;
/*  64: 74 */   private BigDecimal totalAprobado = BigDecimal.ZERO;
/*  65: 75 */   private BigDecimal totalPagado = BigDecimal.ZERO;
/*  66:    */   
/*  67:    */   @PostConstruct
/*  68:    */   public void init()
/*  69:    */   {
/*  70: 82 */     this.fechaPago = new Date();
/*  71: 83 */     if (getListaDocumento().size() > 0) {
/*  72: 84 */       this.documento = ((Documento)getListaDocumento().get(0));
/*  73:    */     }
/*  74: 87 */     actualizarLista();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void actualizarLista()
/*  78:    */   {
/*  79: 91 */     if (this.dtDetalleProveedor != null) {
/*  80: 92 */       this.dtDetalleProveedor.reset();
/*  81:    */     }
/*  82: 94 */     this.ordenPagoProveedor = new OrdenPagoProveedor();
/*  83: 95 */     this.listaDetalleOrdenPagoProveedor = this.servicioOrdenPagoProveedor.buscarDetallesPendientesPago(AppUtil.getOrganizacion().getId(), Boolean.valueOf(false), null);
/*  84: 96 */     this.ordenPagoProveedor.setListaDetalleOrdenPagoProveedor(this.listaDetalleOrdenPagoProveedor);
/*  85: 97 */     this.listaDetalleOrdenPagoProveedorFilters = this.listaDetalleOrdenPagoProveedor;
/*  86: 98 */     agruparMapas();
/*  87: 99 */     calculoTotales();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String crear()
/*  91:    */   {
/*  92:104 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  93:105 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eliminar()
/*  97:    */   {
/*  98:110 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  99:111 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String editar()
/* 103:    */   {
/* 104:116 */     if ((this.ordenPagoProveedor != null) && (this.ordenPagoProveedor.getId() != 0))
/* 105:    */     {
/* 106:117 */       this.ordenPagoProveedor = this.servicioOrdenPagoProveedor.cargarDetalle(this.ordenPagoProveedor.getId());
/* 107:118 */       if (!this.ordenPagoProveedor.getEstado().equals(Estado.APROBADO))
/* 108:    */       {
/* 109:119 */         setEditado(true);
/* 110:120 */         agruparMapas();
/* 111:121 */         calculoTotales();
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:123 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.ordenPagoProveedor.getEstado());
/* 116:    */       }
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 121:    */     }
/* 122:128 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void cargarFacturasPendientesNoVencidas(ActionEvent event)
/* 126:    */   {
/* 127:132 */     List<CuentaPorPagar> lista = this.servicioFacturaProveedor.obtenerFacturasPendientes(0, 0, null, null, null, 0, null, null, 
/* 128:133 */       Integer.valueOf(this.ordenPagoProveedor.getIdOrganizacion()), null);
/* 129:134 */     this.listaFacturasPendientesNoVencidas = new ArrayList();
/* 130:136 */     for (CuentaPorPagar cuentaPorPagar : lista)
/* 131:    */     {
/* 132:137 */       boolean encontre = false;
/* 133:138 */       for (DetalleOrdenPagoProveedor detalle : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 134:139 */         if ((!detalle.isEliminado()) && (detalle.getCuentaPorPagar() != null) && (detalle.getCuentaPorPagar().getId() == cuentaPorPagar.getId()))
/* 135:    */         {
/* 136:140 */           encontre = true;
/* 137:141 */           break;
/* 138:    */         }
/* 139:    */       }
/* 140:144 */       if (!encontre) {
/* 141:145 */         this.listaFacturasPendientesNoVencidas.add(cuentaPorPagar);
/* 142:    */       }
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public DetalleOrdenPagoProveedor agregarFactura(CuentaPorPagar cxp)
/* 147:    */   {
/* 148:151 */     DetalleOrdenPagoProveedor detalleOrdenPago = super.agregarFactura(cxp);
/* 149:152 */     detalleOrdenPago.setIndicadorAprobacionManual(true);
/* 150:    */     
/* 151:154 */     return detalleOrdenPago;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public DetalleOrdenPagoProveedor agregarAnticipo()
/* 155:    */   {
/* 156:163 */     DetalleOrdenPagoProveedor detalleOrdenpago = super.agregarAnticipo();
/* 157:164 */     detalleOrdenpago.setValorAprobado(detalleOrdenpago.getValor());
/* 158:165 */     detalleOrdenpago.setValor(BigDecimal.ZERO);
/* 159:166 */     detalleOrdenpago.setIndicadorAprobacionManual(true);
/* 160:167 */     calculoTotales();
/* 161:168 */     return detalleOrdenpago;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedor()
/* 165:    */   {
/* 166:172 */     this.listaDetalleOrdenPagoProveedor = new ArrayList();
/* 167:173 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.ordenPagoProveedor.getListaDetalleOrdenPagoProveedor()) {
/* 168:174 */       if ((!detalleOrdenPago.isEliminado()) && (
/* 169:175 */         (detalleOrdenPago.getValor().compareTo(BigDecimal.ZERO) > 0) || (detalleOrdenPago.isIndicadorAprobacionManual()))) {
/* 170:176 */         this.listaDetalleOrdenPagoProveedor.add(detalleOrdenPago);
/* 171:    */       }
/* 172:    */     }
/* 173:179 */     return this.listaDetalleOrdenPagoProveedor;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private void pagarDetalle(DetalleOrdenPagoProveedor detalleOrdenPago)
/* 177:    */   {
/* 178:183 */     detalleOrdenPago.setValorPagado(detalleOrdenPago.getValorAprobado());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void cargarValorCuota()
/* 182:    */   {
/* 183:193 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 184:194 */     pagarDetalle(detalleOrdenPago);
/* 185:195 */     calculoTotales();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void limpiarValorCuota()
/* 189:    */   {
/* 190:199 */     DetalleOrdenPagoProveedor detalleOrdenPago = (DetalleOrdenPagoProveedor)this.dtDetalleOrdenPagoProveedor.getRowData();
/* 191:200 */     detalleOrdenPago.setValorPagado(BigDecimal.ZERO);
/* 192:201 */     calculoTotales();
/* 193:202 */     if (detalleOrdenPago.getProveedor().getValorPagado().compareTo(BigDecimal.ZERO) == 0) {
/* 194:203 */       detalleOrdenPago.getProveedor().setNumeroChequePagoAgil("");
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void cargarValorCuotaGlobalProveedor()
/* 199:    */   {
/* 200:208 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.listaDetalleOrdenPagoProveedor) {
/* 201:209 */       pagarDetalle(detalleOrdenPago);
/* 202:    */     }
/* 203:211 */     calculoTotales();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void cargarValorCuotaProveedor()
/* 207:    */   {
/* 208:215 */     Proveedor proveedor = (Proveedor)this.dtDetalleProveedor.getRowData();
/* 209:216 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 210:217 */       pagarDetalle(detalleOrdenPago);
/* 211:    */     }
/* 212:219 */     calculoTotales();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void limpiarValorCuotaGlobalProveedor()
/* 216:    */   {
/* 217:223 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : this.listaDetalleOrdenPagoProveedor)
/* 218:    */     {
/* 219:224 */       detalleOrdenPago.setValorPagado(BigDecimal.ZERO);
/* 220:225 */       detalleOrdenPago.getProveedor().setNumeroChequePagoAgil("");
/* 221:    */     }
/* 222:228 */     calculoTotales();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void limpiarValorCuotaProveedor()
/* 226:    */   {
/* 227:232 */     Proveedor proveedor = (Proveedor)this.dtDetalleProveedor.getRowData();
/* 228:233 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : proveedor.getListaDetalleOrdenPagoProveedor()) {
/* 229:234 */       detalleOrdenPago.setValorPagado(BigDecimal.ZERO);
/* 230:    */     }
/* 231:236 */     proveedor.setNumeroChequePagoAgil("");
/* 232:237 */     calculoTotales();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void calculoTotales()
/* 236:    */   {
/* 237:241 */     reiniciarTotalesMapas();
/* 238:242 */     this.totalAprobado = BigDecimal.ZERO;
/* 239:243 */     this.totalPagado = BigDecimal.ZERO;
/* 240:244 */     for (DetalleOrdenPagoProveedor detalleOrdenPago : getListaDetalleOrdenPagoProveedor()) {
/* 241:245 */       if (!detalleOrdenPago.isEliminado())
/* 242:    */       {
/* 243:246 */         this.totalAprobado = this.totalAprobado.add(detalleOrdenPago.getValorAprobado());
/* 244:247 */         this.totalPagado = this.totalPagado.add(detalleOrdenPago.getValorPagado());
/* 245:    */         
/* 246:249 */         Proveedor proveedor = (Proveedor)this.mapaProveedor.get(Integer.valueOf(detalleOrdenPago.getProveedor().getId()));
/* 247:250 */         proveedor.setValorAprobadoPago(proveedor.getValorAprobadoPago().add(detalleOrdenPago.getValorAprobado()));
/* 248:251 */         proveedor.setValorPagado(proveedor.getValorPagado().add(detalleOrdenPago.getValorPagado()));
/* 249:    */       }
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<Documento> getListaDocumento()
/* 254:    */   {
/* 255:259 */     if (this.listaDocumento == null) {
/* 256:    */       try
/* 257:    */       {
/* 258:262 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PAGO_PROVEEDOR, AppUtil.getOrganizacion().getId());
/* 259:    */       }
/* 260:    */       catch (ExcepcionAS2 e)
/* 261:    */       {
/* 262:264 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 263:    */       }
/* 264:    */     }
/* 265:267 */     return this.listaDocumento;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/* 269:    */   {
/* 270:271 */     if (this.listaCuentaBancariaOrganizacion == null) {
/* 271:272 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.getListaCuentaBancariaConCheques(AppUtil.getOrganizacion().getId());
/* 272:    */     }
/* 273:274 */     return this.listaCuentaBancariaOrganizacion;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/* 277:    */   {
/* 278:278 */     this.formaPago = null;
/* 279:279 */     if (this.cuentaBancariaOrganizacion != null)
/* 280:    */     {
/* 281:280 */       this.cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(this.cuentaBancariaOrganizacion.getId());
/* 282:281 */       this.listaFormaPagoCuentaBancariaOrganizacion = new ArrayList();
/* 283:282 */       for (FormaPagoCuentaBancariaOrganizacion fp : this.cuentaBancariaOrganizacion.getListaFormaPago()) {
/* 284:283 */         if ((fp.getFormaPago() != null) && (fp.isIndicadorProveedor()) && (fp.getFormaPago().isIndicadorCheque())) {
/* 285:284 */           this.listaFormaPagoCuentaBancariaOrganizacion.add(fp);
/* 286:    */         }
/* 287:    */       }
/* 288:    */     }
/* 289:288 */     if (this.listaFormaPagoCuentaBancariaOrganizacion.size() > 0)
/* 290:    */     {
/* 291:289 */       this.formaPago = ((FormaPagoCuentaBancariaOrganizacion)this.listaFormaPagoCuentaBancariaOrganizacion.get(0)).getFormaPago();
/* 292:290 */       actualizarFormaPago();
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 297:    */   {
/* 298:295 */     return this.cuentaBancariaOrganizacion;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 302:    */   {
/* 303:299 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Documento getDocumento()
/* 307:    */   {
/* 308:303 */     return this.documento;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setDocumento(Documento documento)
/* 312:    */   {
/* 313:307 */     this.documento = documento;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Date getFechaPago()
/* 317:    */   {
/* 318:311 */     return this.fechaPago;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setFechaPago(Date fechaPago)
/* 322:    */   {
/* 323:315 */     this.fechaPago = fechaPago;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public String getDescripcion()
/* 327:    */   {
/* 328:319 */     return this.descripcion;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setDescripcion(String descripcion)
/* 332:    */   {
/* 333:323 */     this.descripcion = descripcion;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/* 337:    */   {
/* 338:327 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public FormaPago getFormaPago()
/* 342:    */   {
/* 343:331 */     return this.formaPago;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setFormaPago(FormaPago formaPago)
/* 347:    */   {
/* 348:335 */     this.formaPago = formaPago;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<Proveedor> getListaProveedor()
/* 352:    */   {
/* 353:339 */     List<Proveedor> listaProveedor = new ArrayList();
/* 354:340 */     if (this.mapaProveedor != null) {
/* 355:341 */       listaProveedor.addAll(this.mapaProveedor.values());
/* 356:    */     }
/* 357:343 */     return listaProveedor;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public List<Proveedor> getListaProveedorFiltrado()
/* 361:    */   {
/* 362:347 */     return this.listaProveedorFiltrado;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setListaProveedorFiltrado(List<Proveedor> listaProveedorFiltrado)
/* 366:    */   {
/* 367:351 */     this.listaProveedorFiltrado = listaProveedorFiltrado;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<FormaPagoCuentaBancariaOrganizacion> getListaFormaPagoCuentaBancariaOrganizacion()
/* 371:    */   {
/* 372:355 */     return this.listaFormaPagoCuentaBancariaOrganizacion;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setListaFormaPagoCuentaBancariaOrganizacion(List<FormaPagoCuentaBancariaOrganizacion> listaFormaPagoCuentaBancariaOrganizacion)
/* 376:    */   {
/* 377:359 */     this.listaFormaPagoCuentaBancariaOrganizacion = listaFormaPagoCuentaBancariaOrganizacion;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void generarPagos()
/* 381:    */   {
/* 382:363 */     if (this.totalPagado.compareTo(BigDecimal.ZERO) > 0)
/* 383:    */     {
/* 384:364 */       Pago pago = new Pago();
/* 385:365 */       pago.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 386:366 */       pago.setSucursal(AppUtil.getSucursal());
/* 387:367 */       pago.setDocumento(this.documento);
/* 388:368 */       pago.setFecha(this.fechaPago);
/* 389:369 */       pago.setEstado(Estado.CONTABILIZADO);
/* 390:370 */       pago.setNumero("");
/* 391:    */       
/* 392:372 */       DetalleFormaPago detalleFormaPago = new DetalleFormaPago();
/* 393:373 */       detalleFormaPago.setIdOrganizacion(pago.getIdOrganizacion());
/* 394:374 */       detalleFormaPago.setIdSucursal(pago.getSucursal().getId());
/* 395:375 */       detalleFormaPago.setCuentaBancariaOrganizacion(this.cuentaBancariaOrganizacion);
/* 396:376 */       detalleFormaPago.setCuentaContableFormaPago(this.cuentaBancariaOrganizacion.getCuentaContableBanco());
/* 397:377 */       detalleFormaPago.setFormaPago(this.formaPago);
/* 398:378 */       detalleFormaPago.setSecuencia(this.secuencia);
/* 399:379 */       detalleFormaPago.setPago(pago);
/* 400:    */       try
/* 401:    */       {
/* 402:382 */         this.servicioOrdenPagoProveedor.generarPagos(pago, detalleFormaPago, getListaProveedor(), AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 403:383 */         actualizarLista();
/* 404:384 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 405:    */       }
/* 406:    */       catch (ExcepcionAS2Financiero e)
/* 407:    */       {
/* 408:386 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 409:    */       }
/* 410:    */       catch (ExcepcionAS2 e)
/* 411:    */       {
/* 412:388 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 413:    */       }
/* 414:    */       catch (Exception e)
/* 415:    */       {
/* 416:390 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 417:391 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 418:    */       }
/* 419:    */     }
/* 420:    */     else
/* 421:    */     {
/* 422:394 */       addInfoMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/* 423:    */     }
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void actualizarFormaPago()
/* 427:    */   {
/* 428:399 */     this.secuencia = this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(this.cuentaBancariaOrganizacion.getIdCuentaBancariaOrganizacion(), this.formaPago
/* 429:400 */       .getIdFormaPago());
/* 430:    */   }
/* 431:    */   
/* 432:    */   public BigDecimal getTotalAprobado()
/* 433:    */   {
/* 434:404 */     return this.totalAprobado;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void setTotalAprobado(BigDecimal totalAprobado)
/* 438:    */   {
/* 439:408 */     this.totalAprobado = totalAprobado;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public BigDecimal getTotalPagado()
/* 443:    */   {
/* 444:412 */     return this.totalPagado;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void setTotalPagado(BigDecimal totalPagado)
/* 448:    */   {
/* 449:416 */     this.totalPagado = totalPagado;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public BigDecimal getDiferencia()
/* 453:    */   {
/* 454:420 */     return this.totalAprobado.subtract(this.totalPagado);
/* 455:    */   }
/* 456:    */   
/* 457:    */   public Secuencia getSecuencia()
/* 458:    */   {
/* 459:424 */     return this.secuencia;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setSecuencia(Secuencia secuencia)
/* 463:    */   {
/* 464:428 */     this.secuencia = secuencia;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void actualizarDescripcion()
/* 468:    */   {
/* 469:432 */     for (Proveedor proveedor : getListaProveedor()) {
/* 470:433 */       proveedor.setDescripcionPagoAgil(this.descripcion);
/* 471:    */     }
/* 472:    */   }
/* 473:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.EmisionChequesAgil
 * JD-Core Version:    0.7.0.1
 */