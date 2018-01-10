/*    1:     */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*    9:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   10:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   11:     */ import com.asinfo.as2.entities.DetalleFormaPago;
/*   12:     */ import com.asinfo.as2.entities.DetallePago;
/*   13:     */ import com.asinfo.as2.entities.Documento;
/*   14:     */ import com.asinfo.as2.entities.Empresa;
/*   15:     */ import com.asinfo.as2.entities.FormaPago;
/*   16:     */ import com.asinfo.as2.entities.Organizacion;
/*   17:     */ import com.asinfo.as2.entities.Pago;
/*   18:     */ import com.asinfo.as2.entities.Proveedor;
/*   19:     */ import com.asinfo.as2.entities.Secuencia;
/*   20:     */ import com.asinfo.as2.entities.Sucursal;
/*   21:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   22:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   23:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   24:     */ import com.asinfo.as2.finaciero.contabilidad.procesos.controller.CuentaBancariaOrganizacionBean;
/*   25:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   26:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   27:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/*   28:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   29:     */ import com.asinfo.as2.util.AppUtil;
/*   30:     */ import com.asinfo.as2.util.RutaArchivo;
/*   31:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   32:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   33:     */ import java.math.BigDecimal;
/*   34:     */ import java.util.ArrayList;
/*   35:     */ import java.util.Date;
/*   36:     */ import java.util.HashMap;
/*   37:     */ import java.util.List;
/*   38:     */ import java.util.Map;
/*   39:     */ import javax.annotation.PostConstruct;
/*   40:     */ import javax.ejb.EJB;
/*   41:     */ import javax.faces.bean.ManagedBean;
/*   42:     */ import javax.faces.bean.ManagedProperty;
/*   43:     */ import javax.faces.bean.ViewScoped;
/*   44:     */ import javax.faces.event.ActionEvent;
/*   45:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   46:     */ import org.apache.log4j.Logger;
/*   47:     */ import org.primefaces.component.datatable.DataTable;
/*   48:     */ import org.primefaces.event.FileUploadEvent;
/*   49:     */ import org.primefaces.event.SelectEvent;
/*   50:     */ import org.primefaces.model.LazyDataModel;
/*   51:     */ import org.primefaces.model.SortOrder;
/*   52:     */ 
/*   53:     */ @ManagedBean
/*   54:     */ @ViewScoped
/*   55:     */ public class PagoBean
/*   56:     */   extends PageControllerAS2
/*   57:     */ {
/*   58:     */   private static final long serialVersionUID = -7899753400417860319L;
/*   59:     */   @EJB
/*   60:     */   private transient ServicioPago servicioPago;
/*   61:     */   @EJB
/*   62:     */   private transient ServicioEmpresa servicioEmpresa;
/*   63:     */   @EJB
/*   64:     */   private transient ServicioDocumento servicioDocumento;
/*   65:     */   @EJB
/*   66:     */   private transient ServicioFormaPago servicioFormaPago;
/*   67:     */   @EJB
/*   68:     */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   69:     */   @EJB
/*   70:     */   private transient ServicioSecuencia servicioSecuencia;
/*   71:     */   private Pago pago;
/*   72:     */   private LazyDataModel<Pago> listaPago;
/*   73:     */   private List<Empresa> listaEmpresa;
/*   74:     */   private List<Documento> listaDocumento;
/*   75:     */   private List<FormaPago> listaFormaPago;
/*   76:     */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*   77:     */   private BigDecimal totalLiquidado;
/*   78:     */   private BigDecimal totalLiquidarFormaPago;
/*   79:     */   private boolean mostrarDialogContabilizar;
/*   80:     */   private boolean mostrarDialogEntregarCheque;
/*   81:     */   private DataTable dtPago;
/*   82:     */   private DataTable dtFormaPago;
/*   83:     */   private DataTable dtFormaPagoDialog;
/*   84:     */   private DataTable dtDetallePago;
/*   85:     */   private Integer idPago;
/*   86:     */   private Integer idPagoCash;
/*   87:     */   private Integer idOrdenPago;
/*   88:     */   private Boolean usaOrdenPago;
/*   89:     */   private List<DetallePago> listaDetallePago;
/*   90:     */   private List<DetallePago> listaDetallePagoFiltrado;
/*   91:     */   @ManagedProperty("#{cuentaBancariaOrganizacionBean}")
/*   92:     */   private CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean;
/*   93:     */   
/*   94:     */   @PostConstruct
/*   95:     */   public void init()
/*   96:     */   {
/*   97: 122 */     this.listaPago = new LazyDataModel()
/*   98:     */     {
/*   99:     */       private static final long serialVersionUID = 1L;
/*  100:     */       
/*  101:     */       public List<Pago> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  102:     */       {
/*  103: 134 */         List<Pago> lista = new ArrayList();
/*  104:     */         
/*  105: 136 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  106: 138 */         if (PagoBean.this.idPago != null)
/*  107:     */         {
/*  108: 139 */           filters.put("idPago", PagoBean.this.idPago.toString());
/*  109: 140 */           PagoBean.this.idPago = null;
/*  110:     */         }
/*  111: 143 */         if (PagoBean.this.idPagoCash != null)
/*  112:     */         {
/*  113: 144 */           filters.put("pagoCash.idPagoCash", PagoBean.this.idPagoCash.toString());
/*  114: 145 */           PagoBean.this.idPagoCash = null;
/*  115:     */         }
/*  116: 147 */         if (PagoBean.this.idOrdenPago != null)
/*  117:     */         {
/*  118: 148 */           filters.put("ordenPagoProveedor.idOrdenPagoProveedor", PagoBean.this.idOrdenPago.toString());
/*  119: 149 */           PagoBean.this.idOrdenPago = null;
/*  120:     */         }
/*  121: 152 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_PROVEEDOR.toString());
/*  122: 153 */         lista = PagoBean.this.servicioPago.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  123: 154 */         PagoBean.this.listaPago.setRowCount(PagoBean.this.servicioPago.contarPorCriterio(filters));
/*  124:     */         
/*  125: 156 */         return lista;
/*  126:     */       }
/*  127:     */     };
/*  128:     */   }
/*  129:     */   
/*  130:     */   public String editar()
/*  131:     */   {
/*  132: 168 */     if (getPago().getId() > 0) {
/*  133:     */       try
/*  134:     */       {
/*  135: 172 */         this.servicioPago.esEditable(this.pago);
/*  136: 173 */         this.pago = this.servicioPago.cargarDetalle(this.pago.getId());
/*  137: 174 */         this.pago.setIndicadorEdicionBloqueo(true);
/*  138: 178 */         if (this.pago.getAsiento() != null) {
/*  139: 179 */           for (DetallePago detallePago : this.pago.getListaDetallePago())
/*  140:     */           {
/*  141: 180 */             BigDecimal saldo = detallePago.getCuentaPorPagar().getSaldo();
/*  142: 181 */             detallePago.getCuentaPorPagar().setSaldo(saldo.add(detallePago.getValor()));
/*  143:     */           }
/*  144:     */         }
/*  145: 185 */         setEditado(true);
/*  146:     */       }
/*  147:     */       catch (ExcepcionAS2Financiero e)
/*  148:     */       {
/*  149: 188 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  150: 189 */         LOG.error("ERROR AL EDITAR PAGO PROVEEDOR", e);
/*  151:     */       }
/*  152:     */     } else {
/*  153: 193 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  154:     */     }
/*  155: 196 */     return "";
/*  156:     */   }
/*  157:     */   
/*  158:     */   public String guardar()
/*  159:     */   {
/*  160:     */     try
/*  161:     */     {
/*  162: 208 */       this.servicioPago.guardar(this.pago, AppUtil.getUsuarioEnSesion().getNombreUsuario());
/*  163: 209 */       this.idPago = Integer.valueOf(this.pago.getIdPago());
/*  164: 210 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  165:     */       
/*  166: 212 */       cargarDatos();
/*  167:     */       
/*  168: 214 */       return "pago.xhtml?faces-redirect=true&idPago=" + this.idPago;
/*  169:     */     }
/*  170:     */     catch (ExcepcionAS2Financiero e)
/*  171:     */     {
/*  172: 216 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  173:     */     }
/*  174:     */     catch (ExcepcionAS2 e)
/*  175:     */     {
/*  176: 218 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  177:     */     }
/*  178:     */     catch (Exception e)
/*  179:     */     {
/*  180: 220 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  181: 221 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  182:     */     }
/*  183: 223 */     return "";
/*  184:     */   }
/*  185:     */   
/*  186:     */   public String eliminar()
/*  187:     */   {
/*  188: 233 */     anularPago();
/*  189: 234 */     return "";
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String limpiar()
/*  193:     */   {
/*  194: 244 */     this.pago = null;
/*  195: 245 */     crearPago();
/*  196: 246 */     return "";
/*  197:     */   }
/*  198:     */   
/*  199:     */   public String cargarDatos()
/*  200:     */   {
/*  201: 256 */     setEditado(false);
/*  202:     */     try
/*  203:     */     {
/*  204: 259 */       limpiar();
/*  205:     */     }
/*  206:     */     catch (Exception e)
/*  207:     */     {
/*  208: 262 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  209: 263 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  210:     */     }
/*  211: 265 */     return "";
/*  212:     */   }
/*  213:     */   
/*  214:     */   public String crearPago()
/*  215:     */   {
/*  216: 269 */     this.pago = new Pago();
/*  217: 270 */     this.pago.setValor(BigDecimal.ZERO);
/*  218: 271 */     this.pago.setFecha(new Date());
/*  219: 272 */     this.pago.setEstado(Estado.ELABORADO);
/*  220: 273 */     this.pago.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  221: 274 */     this.pago.setSucursal(AppUtil.getSucursal());
/*  222: 275 */     this.pago.setNumero("");
/*  223: 276 */     Documento documento = null;
/*  224: 277 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/*  225:     */     {
/*  226: 278 */       documento = (Documento)getListaDocumento().get(0);
/*  227: 279 */       this.pago.setDocumento(documento);
/*  228: 280 */       actualizarDocumento();
/*  229:     */     }
/*  230:     */     else
/*  231:     */     {
/*  232: 282 */       documento = new Documento();
/*  233: 283 */       documento.setSecuencia(new Secuencia());
/*  234: 284 */       this.pago.setDocumento(documento);
/*  235:     */     }
/*  236: 287 */     if (getUsaOrdenPago().booleanValue()) {
/*  237: 288 */       this.pago.setEstado(Estado.CONTABILIZADO);
/*  238:     */     }
/*  239: 294 */     return "";
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void cargarFacturasPendientes()
/*  243:     */   {
/*  244: 303 */     if (this.pago.getEmpresa() == null)
/*  245:     */     {
/*  246: 304 */       addErrorMessage(getLanguageController().getMensaje("msg_info_pago_0003"));
/*  247:     */     }
/*  248:     */     else
/*  249:     */     {
/*  250: 306 */       this.listaDetallePago = null;
/*  251: 307 */       this.listaDetallePagoFiltrado = null;
/*  252:     */       
/*  253: 309 */       boolean filtrarPorSucursal = ParametrosSistema.getFiltrarSucursalInicioSesion(AppUtil.getOrganizacion().getId()).booleanValue();
/*  254: 310 */       this.servicioPago.cargarFacturasPendientes(this.pago, filtrarPorSucursal, getUsaOrdenPago().booleanValue());
/*  255:     */     }
/*  256: 312 */     calcularValorAPagar();
/*  257:     */   }
/*  258:     */   
/*  259:     */   public String crearFormaPago()
/*  260:     */   {
/*  261: 322 */     DetalleFormaPago detalleFormaPago = new DetalleFormaPago();
/*  262: 323 */     detalleFormaPago.setPago(getPago());
/*  263: 324 */     detalleFormaPago.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/*  264: 325 */     detalleFormaPago.setFormaPago(new FormaPago());
/*  265: 326 */     detalleFormaPago.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  266: 327 */     detalleFormaPago.setIdSucursal(AppUtil.getSucursal().getId());
/*  267: 328 */     detalleFormaPago.setValor(getPago().getValor());
/*  268:     */     
/*  269: 330 */     getPago().getListaDetalleFormaPago().add(detalleFormaPago);
/*  270: 331 */     return "";
/*  271:     */   }
/*  272:     */   
/*  273:     */   public String eliminarFormaPago()
/*  274:     */   {
/*  275: 341 */     DetalleFormaPago detalleFormaPago = (DetalleFormaPago)this.dtFormaPago.getRowData();
/*  276: 342 */     detalleFormaPago.setEliminado(true);
/*  277:     */     
/*  278: 344 */     return "";
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void actualizarProveedor(SelectEvent event)
/*  282:     */   {
/*  283: 354 */     getPago().getListaDetallePago().clear();
/*  284: 355 */     Empresa empresa = (Empresa)event.getObject();
/*  285:     */     
/*  286: 357 */     String beneficiario = empresa.getProveedor().getBeneficiario() != null ? empresa.getProveedor().getBeneficiario() : "";
/*  287:     */     
/*  288: 359 */     getPago().setBeneficiario(beneficiario.equals("") ? empresa.getNombreFiscal() : beneficiario);
/*  289: 362 */     if (getPago().getBeneficiario().length() > 50) {
/*  290: 363 */       getPago().setBeneficiario(getPago().getBeneficiario().substring(0, 50));
/*  291:     */     }
/*  292: 366 */     for (DetallePago detallePago : this.pago.getListaDetallePago()) {
/*  293: 367 */       detallePago.setEliminado(true);
/*  294:     */     }
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/*  298:     */   {
/*  299: 384 */     DetalleFormaPago detalleFormaPago = (DetalleFormaPago)this.dtFormaPago.getRowData();
/*  300: 385 */     detalleFormaPago.setCuentaContableFormaPago(detalleFormaPago.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/*  301: 386 */     detalleFormaPago.getCuentaBancariaOrganizacion().setListaFormaPago(this.servicioCuentaBancariaOrganizacion
/*  302: 387 */       .cargarDetalle(detalleFormaPago.getCuentaBancariaOrganizacion().getId()).getListaFormaPago());
/*  303: 388 */     detalleFormaPago.getCuentaBancariaOrganizacion().setListaCuentaContableCruceCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/*  304: 389 */       .cargarDetalle(detalleFormaPago.getCuentaBancariaOrganizacion().getId())
/*  305: 390 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion());
/*  306: 391 */     if (((detalleFormaPago.getFormaPago() != null) && (detalleFormaPago.getDocumentoReferencia() != null)) || 
/*  307: 392 */       (detalleFormaPago.getSecuencia() != null))
/*  308:     */     {
/*  309: 393 */       detalleFormaPago.setFormaPago(null);
/*  310: 394 */       detalleFormaPago.setDocumentoReferencia(null);
/*  311: 395 */       detalleFormaPago.setSecuencia(null);
/*  312:     */     }
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void anularPago()
/*  316:     */   {
/*  317: 404 */     if (this.pago.getId() > 0) {
/*  318:     */       try
/*  319:     */       {
/*  320: 408 */         this.servicioPago.anularPago(this.pago);
/*  321: 409 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  322:     */       }
/*  323:     */       catch (ExcepcionAS2Financiero e)
/*  324:     */       {
/*  325: 412 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  326: 413 */         LOG.error("ERROR AL ANULAR PAGO " + e);
/*  327:     */       }
/*  328:     */       catch (ExcepcionAS2 e)
/*  329:     */       {
/*  330: 416 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  331: 417 */         LOG.error("ERROR AL ANULAR PAGO " + e);
/*  332:     */       }
/*  333:     */       catch (Exception e)
/*  334:     */       {
/*  335: 419 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  336: 420 */         LOG.error("ERROR AL ANULAR PAGO " + e);
/*  337:     */       }
/*  338:     */     } else {
/*  339: 424 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  340:     */     }
/*  341:     */   }
/*  342:     */   
/*  343:     */   public String seleccionarPagoAContabilizar()
/*  344:     */   {
/*  345: 434 */     setMostrarDialogContabilizar(true);
/*  346: 435 */     Pago pagoSeleccionado = (Pago)this.dtPago.getRowData();
/*  347: 436 */     Pago pagoConDetalle = this.servicioPago.cargarDetalle(pagoSeleccionado.getIdPago());
/*  348: 437 */     obtenerDetalleFormaFago(pagoConDetalle);
/*  349: 438 */     setPago(pagoConDetalle);
/*  350: 439 */     return "";
/*  351:     */   }
/*  352:     */   
/*  353:     */   private void obtenerDetalleFormaFago(Pago pago)
/*  354:     */   {
/*  355: 446 */     for (DetalleFormaPago detalleFormaPago : pago.getListaDetalleFormaPago())
/*  356:     */     {
/*  357: 447 */       detalleFormaPago.setSecuencia(this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(detalleFormaPago
/*  358: 448 */         .getCuentaBancariaOrganizacion().getIdCuentaBancariaOrganizacion(), detalleFormaPago.getFormaPago().getIdFormaPago()));
/*  359: 449 */       cargarSecuencia(detalleFormaPago);
/*  360:     */     }
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void contabilizarPago(ActionEvent aev)
/*  364:     */   {
/*  365:     */     try
/*  366:     */     {
/*  367: 460 */       Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.pago.getDocumento().getId()));
/*  368: 461 */       this.pago.getDocumento().setTipoAsiento(documento.getTipoAsiento());
/*  369: 462 */       this.pago.setEstado(Estado.CONTABILIZADO);
/*  370: 463 */       this.servicioPago.guardar(this.pago);
/*  371: 464 */       setMostrarDialogContabilizar(false);
/*  372: 465 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  373:     */     }
/*  374:     */     catch (ExcepcionAS2Financiero e)
/*  375:     */     {
/*  376: 467 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  377: 468 */       LOG.error("ERROR AL CONTABILIZAR PAGO", e);
/*  378:     */     }
/*  379:     */     catch (ExcepcionAS2 e)
/*  380:     */     {
/*  381: 470 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  382: 471 */       LOG.error("ERROR AL CONTABILIZAR PAGO", e);
/*  383:     */     }
/*  384:     */     catch (Exception e)
/*  385:     */     {
/*  386: 473 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  387: 474 */       LOG.error("ERROR AL CONTABILIZAR PAGO", e);
/*  388:     */     }
/*  389:     */   }
/*  390:     */   
/*  391:     */   public void calcularValorAPagar()
/*  392:     */   {
/*  393: 484 */     BigDecimal valorTmp = BigDecimal.ZERO;
/*  394: 485 */     int numeroLineas = 0;
/*  395: 486 */     for (DetallePago detallePago : getPago().getListaDetallePago()) {
/*  396: 487 */       if ((!detallePago.isEliminado()) && (detallePago.getValor().compareTo(BigDecimal.ZERO) > 0))
/*  397:     */       {
/*  398: 488 */         numeroLineas++;
/*  399: 489 */         if ((ParametrosSistema.getNumeroMaximoLineasPago(AppUtil.getOrganizacion().getIdOrganizacion()).intValue() > 0) && 
/*  400: 490 */           (numeroLineas > ParametrosSistema.getNumeroMaximoLineasPago(AppUtil.getOrganizacion().getIdOrganizacion()).intValue()))
/*  401:     */         {
/*  402: 491 */           addInfoMessage(getLanguageController().getMensaje("msg_info_numero_maximo_lineas_pago_excedido"));
/*  403: 492 */           detallePago.setValor(BigDecimal.ZERO);
/*  404:     */         }
/*  405:     */         else
/*  406:     */         {
/*  407: 494 */           valorTmp = valorTmp.add(detallePago.getValor());
/*  408:     */         }
/*  409:     */       }
/*  410:     */     }
/*  411: 499 */     this.pago.setValor(valorTmp);
/*  412:     */   }
/*  413:     */   
/*  414:     */   public String cargarValorCuota()
/*  415:     */   {
/*  416: 503 */     DetallePago d = (DetallePago)this.dtDetallePago.getRowData();
/*  417: 504 */     d.setValor(d.getCuentaPorPagar().getSaldo());
/*  418: 505 */     calcularValorAPagar();
/*  419: 506 */     return "";
/*  420:     */   }
/*  421:     */   
/*  422:     */   public String limpiarValorCuota()
/*  423:     */   {
/*  424: 510 */     DetallePago d = (DetallePago)this.dtDetallePago.getRowData();
/*  425: 511 */     d.setValor(BigDecimal.ZERO);
/*  426: 512 */     calcularValorAPagar();
/*  427:     */     
/*  428: 514 */     return "";
/*  429:     */   }
/*  430:     */   
/*  431:     */   public void actualizarFormaPago()
/*  432:     */   {
/*  433: 518 */     DetalleFormaPago d = (DetalleFormaPago)this.dtFormaPago.getRowData();
/*  434: 519 */     if ((d.getDocumentoReferencia() != null) && (d.getSecuencia() != null))
/*  435:     */     {
/*  436: 520 */       d.setDocumentoReferencia(null);
/*  437: 521 */       d.setSecuencia(null);
/*  438:     */     }
/*  439: 523 */     cargarSecuencia(d);
/*  440:     */   }
/*  441:     */   
/*  442:     */   private void cargarSecuencia(DetalleFormaPago d)
/*  443:     */   {
/*  444: 527 */     d.setSecuencia(this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(d.getCuentaBancariaOrganizacion()
/*  445: 528 */       .getIdCuentaBancariaOrganizacion(), d.getFormaPago().getIdFormaPago()));
/*  446: 529 */     String numero = "";
/*  447: 530 */     if (d.getSecuencia() != null) {
/*  448:     */       try
/*  449:     */       {
/*  450: 533 */         numero = this.servicioSecuencia.obtenerSecuencia(d.getSecuencia(), this.pago.getFecha());
/*  451: 534 */         d.setDocumentoReferencia(numero);
/*  452:     */       }
/*  453:     */       catch (ExcepcionAS2 e)
/*  454:     */       {
/*  455: 536 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  456: 537 */         e.printStackTrace();
/*  457:     */       }
/*  458:     */     }
/*  459: 540 */     d.setDocumentoReferencia(numero);
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void processDownload()
/*  463:     */   {
/*  464:     */     try
/*  465:     */     {
/*  466: 550 */       Pago fp = (Pago)this.dtPago.getRowData();
/*  467: 551 */       String fileName = fp.getPdf();
/*  468: 552 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago");
/*  469: 554 */       if (fileName == null) {
/*  470: 555 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  471:     */       } else {
/*  472: 557 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  473:     */       }
/*  474:     */     }
/*  475:     */     catch (Exception e)
/*  476:     */     {
/*  477: 561 */       e.printStackTrace();
/*  478: 562 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  479:     */     }
/*  480:     */   }
/*  481:     */   
/*  482:     */   public String eliminarArchivo()
/*  483:     */   {
/*  484: 567 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getPago().getPdf());
/*  485: 568 */     getPago().setPdf(null);
/*  486: 569 */     HashMap<String, Object> campos = new HashMap();
/*  487: 570 */     campos.put("pdf", null);
/*  488: 571 */     this.servicioPago.actualizarAtributoEntidad(getPago(), campos);
/*  489: 572 */     return null;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void processUpload(FileUploadEvent event)
/*  493:     */   {
/*  494:     */     try
/*  495:     */     {
/*  496: 585 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago");
/*  497:     */       
/*  498: 587 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getPago().getNumero(), uploadDir);
/*  499:     */       
/*  500:     */ 
/*  501: 590 */       HashMap<String, Object> campos = new HashMap();
/*  502: 591 */       campos.put("pdf", fileName);
/*  503: 592 */       this.servicioPago.actualizarAtributoEntidad(getPago(), campos);
/*  504:     */       
/*  505: 594 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  506:     */     }
/*  507:     */     catch (Exception e)
/*  508:     */     {
/*  509: 597 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  510: 598 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  511:     */     }
/*  512:     */   }
/*  513:     */   
/*  514:     */   public String getDirectorioDescarga()
/*  515:     */   {
/*  516: 605 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago");
/*  517:     */   }
/*  518:     */   
/*  519:     */   public Pago getPago()
/*  520:     */   {
/*  521: 616 */     if (this.pago == null) {
/*  522: 617 */       crearPago();
/*  523:     */     }
/*  524: 619 */     return this.pago;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void setPago(Pago pago)
/*  528:     */   {
/*  529: 629 */     this.pago = pago;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public List<Empresa> getListaEmpresa()
/*  533:     */   {
/*  534: 638 */     if (this.listaEmpresa == null) {
/*  535: 639 */       this.listaEmpresa = this.servicioEmpresa.obtenerProveedores();
/*  536:     */     }
/*  537: 641 */     return this.listaEmpresa;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/*  541:     */   {
/*  542: 651 */     this.listaEmpresa = listaEmpresa;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public List<Documento> getListaDocumento()
/*  546:     */   {
/*  547: 661 */     if (this.listaDocumento == null) {
/*  548:     */       try
/*  549:     */       {
/*  550: 663 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_PROVEEDOR);
/*  551:     */       }
/*  552:     */       catch (ExcepcionAS2 e)
/*  553:     */       {
/*  554: 665 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  555:     */       }
/*  556:     */     }
/*  557: 668 */     return this.listaDocumento;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void setListaDocumento(List<Documento> listaDocumento)
/*  561:     */   {
/*  562: 678 */     this.listaDocumento = listaDocumento;
/*  563:     */   }
/*  564:     */   
/*  565:     */   public BigDecimal getTotalLiquidado()
/*  566:     */   {
/*  567: 687 */     this.totalLiquidado = BigDecimal.ZERO;
/*  568: 689 */     for (DetallePago detallePago : getPago().getListaDetallePago()) {
/*  569: 690 */       if (!detallePago.isEliminado()) {
/*  570: 691 */         this.totalLiquidado = this.totalLiquidado.add(detallePago.getValor());
/*  571:     */       }
/*  572:     */     }
/*  573: 694 */     return this.totalLiquidado;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/*  577:     */   {
/*  578: 704 */     this.totalLiquidado = totalLiquidado;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public CuentaBancariaOrganizacionBean getCuentaBancariaOrganizacionBean()
/*  582:     */   {
/*  583: 713 */     return this.cuentaBancariaOrganizacionBean;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setCuentaBancariaOrganizacionBean(CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean)
/*  587:     */   {
/*  588: 723 */     this.cuentaBancariaOrganizacionBean = cuentaBancariaOrganizacionBean;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public List<DetallePago> getListaDetallePago()
/*  592:     */   {
/*  593: 734 */     if (this.listaDetallePago == null)
/*  594:     */     {
/*  595: 735 */       this.listaDetallePago = new ArrayList();
/*  596: 736 */       for (DetallePago detallePago : getPago().getListaDetallePago()) {
/*  597: 737 */         if (!detallePago.isEliminado()) {
/*  598: 738 */           this.listaDetallePago.add(detallePago);
/*  599:     */         }
/*  600:     */       }
/*  601:     */     }
/*  602: 742 */     return this.listaDetallePago;
/*  603:     */   }
/*  604:     */   
/*  605:     */   public List<DetalleFormaPago> getListaDetalleFormaPago()
/*  606:     */   {
/*  607: 752 */     List<DetalleFormaPago> lista = new ArrayList();
/*  608: 754 */     for (DetalleFormaPago detalleFormaPago : getPago().getListaDetalleFormaPago()) {
/*  609: 755 */       if (!detalleFormaPago.isEliminado()) {
/*  610: 756 */         lista.add(detalleFormaPago);
/*  611:     */       }
/*  612:     */     }
/*  613: 760 */     return lista;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public DataTable getDtPago()
/*  617:     */   {
/*  618: 764 */     return this.dtPago;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public void setDtPago(DataTable dtPago)
/*  622:     */   {
/*  623: 768 */     this.dtPago = dtPago;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public DataTable getDtFormaPago()
/*  627:     */   {
/*  628: 777 */     return this.dtFormaPago;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public void setDtFormaPago(DataTable dtFormaPago)
/*  632:     */   {
/*  633: 787 */     this.dtFormaPago = dtFormaPago;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public LazyDataModel<Pago> getListaPago()
/*  637:     */   {
/*  638: 796 */     return this.listaPago;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public void setListaPago(LazyDataModel<Pago> listaPago)
/*  642:     */   {
/*  643: 806 */     this.listaPago = listaPago;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public List<FormaPago> getListaFormaPago()
/*  647:     */   {
/*  648: 811 */     if (this.listaFormaPago == null)
/*  649:     */     {
/*  650: 812 */       HashMap<String, String> filters = new HashMap();
/*  651: 813 */       filters.put("indicadorPago", "true");
/*  652: 814 */       this.listaFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", true, filters);
/*  653:     */     }
/*  654: 816 */     return this.listaFormaPago;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public void setListaFormaPago(List<FormaPago> listaFormaPago)
/*  658:     */   {
/*  659: 820 */     this.listaFormaPago = listaFormaPago;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/*  663:     */   {
/*  664: 824 */     if (this.listaCuentaBancariaOrganizacion == null) {
/*  665: 825 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/*  666:     */     }
/*  667: 827 */     return this.listaCuentaBancariaOrganizacion;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/*  671:     */   {
/*  672: 831 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  676:     */   {
/*  677: 835 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/*  678:     */   }
/*  679:     */   
/*  680:     */   public boolean isMostrarDialogContabilizar()
/*  681:     */   {
/*  682: 844 */     return this.mostrarDialogContabilizar;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void setMostrarDialogContabilizar(boolean mostrarDialogContabilizar)
/*  686:     */   {
/*  687: 854 */     this.mostrarDialogContabilizar = mostrarDialogContabilizar;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public DataTable getDtFormaPagoDialog()
/*  691:     */   {
/*  692: 863 */     return this.dtFormaPagoDialog;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setDtFormaPagoDialog(DataTable dtFormaPagoDialog)
/*  696:     */   {
/*  697: 873 */     this.dtFormaPagoDialog = dtFormaPagoDialog;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public DataTable getDtDetallePago()
/*  701:     */   {
/*  702: 882 */     return this.dtDetallePago;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public void setDtDetallePago(DataTable dtDetallePago)
/*  706:     */   {
/*  707: 892 */     this.dtDetallePago = dtDetallePago;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public boolean isMostrarDialogEntregarCheque()
/*  711:     */   {
/*  712: 901 */     return this.mostrarDialogEntregarCheque;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void setMostrarDialogEntregarCheque(boolean mostrarDialogEntregarCheque)
/*  716:     */   {
/*  717: 911 */     this.mostrarDialogEntregarCheque = mostrarDialogEntregarCheque;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public BigDecimal getTotalLiquidarFormaPago()
/*  721:     */   {
/*  722: 920 */     this.totalLiquidarFormaPago = BigDecimal.ZERO;
/*  723: 921 */     for (DetalleFormaPago detalleFormaPago : getPago().getListaDetalleFormaPago()) {
/*  724: 922 */       if (!detalleFormaPago.isEliminado()) {
/*  725: 923 */         this.totalLiquidarFormaPago = this.totalLiquidarFormaPago.add(detalleFormaPago.getValor());
/*  726:     */       }
/*  727:     */     }
/*  728: 926 */     return this.totalLiquidarFormaPago;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void setTotalLiquidarFormaPago(BigDecimal totalLiquidarFormaPago)
/*  732:     */   {
/*  733: 936 */     this.totalLiquidarFormaPago = totalLiquidarFormaPago;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public Integer getIdPago()
/*  737:     */   {
/*  738: 945 */     return this.idPago;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public void setIdPago(Integer idPago)
/*  742:     */   {
/*  743: 955 */     this.idPago = idPago;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public Integer getIdPagoCash()
/*  747:     */   {
/*  748: 962 */     return this.idPagoCash;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public void setIdPagoCash(Integer idPagoCash)
/*  752:     */   {
/*  753: 970 */     this.idPagoCash = idPagoCash;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public List<DetallePago> getListaDetallePagoFiltrado()
/*  757:     */   {
/*  758: 974 */     return this.listaDetallePagoFiltrado;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public void setListaDetallePagoFiltrado(List<DetallePago> listaDetallePagoFiltrado)
/*  762:     */   {
/*  763: 978 */     this.listaDetallePagoFiltrado = listaDetallePagoFiltrado;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public String actualizarDocumento()
/*  767:     */   {
/*  768: 983 */     setSecuenciaEditable(!this.pago.getDocumento().isIndicadorBloqueoSecuencia());
/*  769:     */     
/*  770: 985 */     return "";
/*  771:     */   }
/*  772:     */   
/*  773:     */   public Integer getIdOrdenPago()
/*  774:     */   {
/*  775: 989 */     return this.idOrdenPago;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void setIdOrdenPago(Integer idOrdenPago)
/*  779:     */   {
/*  780: 993 */     this.idOrdenPago = idOrdenPago;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public Boolean getUsaOrdenPago()
/*  784:     */   {
/*  785: 997 */     if (this.usaOrdenPago == null) {
/*  786: 998 */       this.usaOrdenPago = ParametrosSistema.getIndicadorOrdenesPago(AppUtil.getOrganizacion().getId());
/*  787:     */     }
/*  788:1000 */     return this.usaOrdenPago;
/*  789:     */   }
/*  790:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.PagoBean
 * JD-Core Version:    0.7.0.1
 */