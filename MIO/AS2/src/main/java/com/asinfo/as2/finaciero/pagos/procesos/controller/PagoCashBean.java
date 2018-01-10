/*    1:     */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.SecuenciaAS2Service;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   11:     */ import com.asinfo.as2.entities.Banco;
/*   12:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   13:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   14:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   15:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   16:     */ import com.asinfo.as2.entities.DetallePagoCash;
/*   17:     */ import com.asinfo.as2.entities.Documento;
/*   18:     */ import com.asinfo.as2.entities.Empleado;
/*   19:     */ import com.asinfo.as2.entities.Empresa;
/*   20:     */ import com.asinfo.as2.entities.FormaPago;
/*   21:     */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   22:     */ import com.asinfo.as2.entities.Organizacion;
/*   23:     */ import com.asinfo.as2.entities.PagoCash;
/*   24:     */ import com.asinfo.as2.entities.PersonaResponsable;
/*   25:     */ import com.asinfo.as2.entities.Sucursal;
/*   26:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   27:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   28:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   29:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   30:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   31:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   32:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   33:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash;
/*   34:     */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor;
/*   35:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   36:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   37:     */ import com.asinfo.as2.util.AppUtil;
/*   38:     */ import com.asinfo.as2.util.RutaArchivo;
/*   39:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   40:     */ import com.asinfo.as2.utils.JsfUtil;
/*   41:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   42:     */ import java.io.File;
/*   43:     */ import java.math.BigDecimal;
/*   44:     */ import java.util.ArrayList;
/*   45:     */ import java.util.Calendar;
/*   46:     */ import java.util.Date;
/*   47:     */ import java.util.HashMap;
/*   48:     */ import java.util.List;
/*   49:     */ import java.util.Map;
/*   50:     */ import javax.annotation.PostConstruct;
/*   51:     */ import javax.ejb.EJB;
/*   52:     */ import javax.faces.bean.ManagedBean;
/*   53:     */ import javax.faces.bean.ViewScoped;
/*   54:     */ import javax.faces.event.ActionEvent;
/*   55:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   56:     */ import org.apache.log4j.Logger;
/*   57:     */ import org.primefaces.component.datatable.DataTable;
/*   58:     */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*   59:     */ import org.primefaces.event.FileUploadEvent;
/*   60:     */ import org.primefaces.model.LazyDataModel;
/*   61:     */ import org.primefaces.model.SortOrder;
/*   62:     */ import org.primefaces.model.StreamedContent;
/*   63:     */ 
/*   64:     */ @ManagedBean
/*   65:     */ @ViewScoped
/*   66:     */ public class PagoCashBean
/*   67:     */   extends PageControllerAS2
/*   68:     */ {
/*   69:     */   private static final long serialVersionUID = 7085091448710210515L;
/*   70:     */   @EJB
/*   71:     */   private transient ServicioPagoCash servicioPagoCash;
/*   72:     */   @EJB
/*   73:     */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   74:     */   @EJB
/*   75:     */   private transient ServicioDocumento servicioDocumento;
/*   76:     */   @EJB
/*   77:     */   private transient ServicioFormaPago servicioFormaPago;
/*   78:     */   @EJB
/*   79:     */   private transient ServicioReportePagoProveedor servicioReportePagoProveedor;
/*   80:     */   @EJB
/*   81:     */   private transient ServicioEmpresa servicioEmpresa;
/*   82:     */   @EJB
/*   83:     */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*   84:     */   @EJB
/*   85:     */   private ServicioUsuario servicioUsuario;
/*   86:     */   @EJB
/*   87:     */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*   88:     */   @EJB
/*   89:     */   private SecuenciaAS2Service secuenciaAS2Service;
/*   90:     */   private PagoCash pagoCash;
/*   91:     */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*   92:     */   private List<Documento> listaDocumento;
/*   93:     */   private BigDecimal totalLiquidado;
/*   94:     */   private int idCuentaBancariaOrganizacion;
/*   95:     */   private List<FormaPago> listaFormaPago;
/*   96:     */   private int idFormaPago;
/*   97:     */   private Empresa empresa;
/*   98:     */   private BigDecimal valorAnticipo;
/*   99:     */   private String documentoReferencia;
/*  100:     */   private String descripcionAnticipo;
/*  101:     */   private static final String TIPO_CONTENIDO = "application/txt";
/*  102:     */   private StreamedContent file;
/*  103:     */   private boolean mostrarDialogContabilizar;
/*  104:     */   private Date fechaContabilizacion;
/*  105:     */   private List<Documento> listaDocumentoPago;
/*  106:     */   private Documento documentoFiltro;
/*  107:     */   private List<Documento> listaDocumentoFiltro;
/*  108:     */   private List<Documento> listaDocumentoAnticipo;
/*  109: 128 */   private BigDecimal totalValorAPagar = BigDecimal.ZERO.setScale(2);
/*  110:     */   private PersonaResponsable personaResponsable;
/*  111:     */   private boolean indicadorRenderedAnticipo;
/*  112:     */   private CategoriaEmpresa categoriaEmpresa;
/*  113:     */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  114: 135 */   private HashMap<String, DetallePagoCash> hmDetallePagoCash = new HashMap();
/*  115:     */   private LazyDataModel<PagoCash> listaPagoCash;
/*  116:     */   private DataTable dtPagoCash;
/*  117:     */   private DataTable dtDetallePagoCash;
/*  118:     */   private DataTable dtDetallePagoCashAContabilizar;
/*  119:     */   private List<DetallePagoCash> listaDetallePagoCash;
/*  120:     */   private List<DetallePagoCash> listaDetallePagoCashFilters;
/*  121:     */   private List<PersonaResponsable> listaPersonaResponsable;
/*  122:     */   private BigDecimal totalValorALiquidar;
/*  123:     */   private BigDecimal totalValorPendiente;
/*  124:     */   private BigDecimal diferencia;
/*  125: 158 */   private boolean activadorTipoServicio = true;
/*  126:     */   private List<FormaPago> listaFormaPagoOrganizacion;
/*  127:     */   private boolean seleccionarTodos;
/*  128:     */   private Boolean usaOrdenPago;
/*  129:     */   
/*  130:     */   @PostConstruct
/*  131:     */   public void init()
/*  132:     */   {
/*  133: 169 */     this.listaPagoCash = new LazyDataModel()
/*  134:     */     {
/*  135:     */       private static final long serialVersionUID = -6092104942165704404L;
/*  136:     */       
/*  137:     */       public List<PagoCash> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  138:     */       {
/*  139: 181 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  140:     */         
/*  141: 183 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  142: 184 */         filters.put("documento.documentoBase", DocumentoBase.PAGO_CASH_PROVEEDOR.toString());
/*  143: 185 */         List<PagoCash> lista = PagoCashBean.this.servicioPagoCash.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  144:     */         
/*  145: 187 */         PagoCashBean.this.listaPagoCash.setRowCount(PagoCashBean.this.servicioPagoCash.contarPorCriterio(filters));
/*  146: 188 */         return lista;
/*  147:     */       }
/*  148:     */     };
/*  149:     */   }
/*  150:     */   
/*  151:     */   public void activarTipoServicio()
/*  152:     */   {
/*  153: 199 */     if ((getPagoCash().getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("30")) || 
/*  154: 200 */       (getPagoCash().getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("17"))) {
/*  155: 201 */       this.activadorTipoServicio = false;
/*  156:     */     } else {
/*  157: 203 */       this.activadorTipoServicio = true;
/*  158:     */     }
/*  159:     */   }
/*  160:     */   
/*  161:     */   private void crearPagoCash()
/*  162:     */   {
/*  163: 212 */     this.pagoCash = new PagoCash();
/*  164: 213 */     this.pagoCash.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  165: 214 */     this.pagoCash.setIdSucursal(AppUtil.getSucursal().getId());
/*  166: 215 */     this.pagoCash.setEstado(Estado.ELABORADO);
/*  167: 216 */     this.pagoCash.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/*  168: 217 */     this.pagoCash.setFormaPago(new FormaPago());
/*  169: 218 */     this.pagoCash.setFechaPago(new Date());
/*  170: 219 */     this.pagoCash.setFechaVencimiento(new Date());
/*  171:     */     
/*  172: 221 */     Documento documento = null;
/*  173: 222 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/*  174:     */     {
/*  175: 223 */       documento = (Documento)getListaDocumento().get(0);
/*  176: 224 */       this.pagoCash.setDocumento(documento);
/*  177: 225 */       actualizarDocumento();
/*  178:     */     }
/*  179:     */     else
/*  180:     */     {
/*  181: 227 */       documento = new Documento();
/*  182: 228 */       this.pagoCash.setDocumento(documento);
/*  183:     */     }
/*  184: 231 */     if ((getListaDocumentoPago() != null) && (!getListaDocumentoPago().isEmpty())) {
/*  185: 232 */       this.pagoCash.setDocumentoPago((Documento)getListaDocumentoPago().get(0));
/*  186:     */     }
/*  187: 235 */     if ((getListaDocumentoAnticipo() != null) && (!getListaDocumentoAnticipo().isEmpty())) {
/*  188: 236 */       this.pagoCash.setDocumentoAnticipo((Documento)getListaDocumentoAnticipo().get(0));
/*  189:     */     }
/*  190: 239 */     if (getUsaOrdenPago().booleanValue()) {
/*  191: 240 */       this.pagoCash.setEstado(Estado.APROBADO);
/*  192:     */     }
/*  193: 243 */     this.pagoCash.setNumero("");
/*  194: 244 */     this.pagoCash.setValorPago(null);
/*  195:     */   }
/*  196:     */   
/*  197:     */   public String editar()
/*  198:     */   {
/*  199: 255 */     if (getPagoCash().getIdPagoCash() > 0) {
/*  200:     */       try
/*  201:     */       {
/*  202: 257 */         this.servicioPagoCash.esEditable(this.pagoCash);
/*  203: 258 */         setPagoCash(this.servicioPagoCash.cargarDetalle(getPagoCash().getIdPagoCash()));
/*  204: 259 */         setListaDetallePagoCash(getPagoCash().getListaDetallePagoCash());
/*  205: 260 */         calcularValorPagoCash();
/*  206: 261 */         setEditado(true);
/*  207:     */       }
/*  208:     */       catch (ExcepcionAS2Financiero e)
/*  209:     */       {
/*  210: 263 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  211: 264 */         LOG.info("ERROR AL EDITAR PAGO CASH DE PROVVEDOR" + e);
/*  212:     */       }
/*  213:     */     } else {
/*  214: 268 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  215:     */     }
/*  216: 270 */     return "";
/*  217:     */   }
/*  218:     */   
/*  219:     */   public String guardar()
/*  220:     */   {
/*  221:     */     try
/*  222:     */     {
/*  223: 281 */       validarValorLiquidar();
/*  224:     */       
/*  225: 283 */       this.servicioPagoCash.guardar(this.pagoCash);
/*  226:     */       
/*  227: 285 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  228: 286 */       setEditado(false);
/*  229: 287 */       limpiar();
/*  230:     */     }
/*  231:     */     catch (AS2Exception e)
/*  232:     */     {
/*  233: 289 */       JsfUtil.addErrorMessage(e, "");
/*  234:     */     }
/*  235:     */     catch (ExcepcionAS2Financiero e)
/*  236:     */     {
/*  237: 291 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  238: 292 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  239:     */     }
/*  240:     */     catch (Exception e)
/*  241:     */     {
/*  242: 294 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  243: 295 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  244:     */     }
/*  245: 297 */     return "";
/*  246:     */   }
/*  247:     */   
/*  248:     */   public String eliminar()
/*  249:     */   {
/*  250:     */     try
/*  251:     */     {
/*  252: 307 */       anularPago();
/*  253:     */     }
/*  254:     */     catch (Exception e)
/*  255:     */     {
/*  256: 309 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  257: 310 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  258:     */     }
/*  259: 312 */     return "";
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void anularPago()
/*  263:     */   {
/*  264: 319 */     if (this.pagoCash.getId() > 0) {
/*  265:     */       try
/*  266:     */       {
/*  267: 321 */         this.servicioPagoCash.anularPagoCash(this.pagoCash);
/*  268: 322 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  269:     */       }
/*  270:     */       catch (ExcepcionAS2Financiero e)
/*  271:     */       {
/*  272: 324 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  273: 325 */         LOG.error("ERROR AL ANULAR PAGO " + e);
/*  274: 326 */         e.printStackTrace();
/*  275:     */       }
/*  276:     */       catch (ExcepcionAS2 e)
/*  277:     */       {
/*  278: 329 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  279: 330 */         LOG.error("ERROR AL ANULAR PAGO " + e);
/*  280: 331 */         e.printStackTrace();
/*  281:     */       }
/*  282:     */       catch (Exception e)
/*  283:     */       {
/*  284: 333 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  285: 334 */         LOG.error("ERROR AL ANULAR PAGO " + e);
/*  286: 335 */         e.printStackTrace();
/*  287:     */       }
/*  288:     */     } else {
/*  289: 338 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void validarValorLiquidar()
/*  294:     */     throws ExcepcionAS2Financiero
/*  295:     */   {
/*  296: 346 */     for (DetallePagoCash detallePagoCash : this.pagoCash.getListaDetallePagoCash()) {
/*  297: 347 */       if ((detallePagoCash.getCuentaPorPagar() != null) && 
/*  298: 348 */         (detallePagoCash.getValor().compareTo(detallePagoCash.getCuentaPorPagar().getSaldo()) == 1)) {
/*  299: 349 */         throw new ExcepcionAS2Financiero("msg_info_pago_0002");
/*  300:     */       }
/*  301:     */     }
/*  302:     */   }
/*  303:     */   
/*  304:     */   public String cargarDatos()
/*  305:     */   {
/*  306: 360 */     return "";
/*  307:     */   }
/*  308:     */   
/*  309:     */   public String limpiar()
/*  310:     */   {
/*  311: 369 */     this.dtDetallePagoCash.reset();
/*  312:     */     
/*  313: 371 */     crearPagoCash();
/*  314:     */     
/*  315: 373 */     this.listaDetallePagoCashFilters = null;
/*  316: 374 */     this.activadorTipoServicio = true;
/*  317: 375 */     return "";
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/*  321:     */   {
/*  322: 384 */     SelectOneMenu selectOneMenu = (SelectOneMenu)event.getComponent();
/*  323: 385 */     CuentaBancariaOrganizacion cbo = (CuentaBancariaOrganizacion)selectOneMenu.getValue();
/*  324:     */     
/*  325: 387 */     this.listaFormaPagoOrganizacion = new ArrayList();
/*  326:     */     
/*  327: 389 */     cbo = this.servicioCuentaBancariaOrganizacion.cargarDetalle(cbo.getIdCuentaBancariaOrganizacion());
/*  328: 390 */     for (FormaPagoCuentaBancariaOrganizacion fp : cbo.getListaFormaPago()) {
/*  329: 391 */       this.listaFormaPagoOrganizacion.add(fp.getFormaPago());
/*  330:     */     }
/*  331: 394 */     activarTipoServicio();
/*  332:     */   }
/*  333:     */   
/*  334:     */   public String actualizarDocumento()
/*  335:     */   {
/*  336: 404 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getPagoCash().getDocumento().getIdDocumento()));
/*  337: 405 */     getPagoCash().setDocumento(documento);
/*  338: 406 */     return "";
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void cargarFacturasPendientes(ActionEvent aev)
/*  342:     */   {
/*  343: 415 */     this.dtDetallePagoCash.reset();
/*  344: 416 */     getPagoCash().setCategoriaEmpresa(getCategoriaEmpresa());
/*  345: 417 */     this.pagoCash.setListaDetallePagoCash(new ArrayList());
/*  346: 418 */     this.servicioPagoCash.cargarFacturasPendientes(getPagoCash(), this.documentoFiltro, getUsaOrdenPago().booleanValue());
/*  347: 419 */     this.pagoCash.getListaDetallePagoCash().addAll(0, getListaDetallePagoCash());
/*  348:     */     
/*  349: 421 */     calculoTotales(getPagoCash());
/*  350: 422 */     setListaDetallePagoCashFilters(this.pagoCash.getListaDetallePagoCash());
/*  351:     */   }
/*  352:     */   
/*  353:     */   public StreamedContent generarArchivo(PagoCash pagoCashSeleccionado)
/*  354:     */   {
/*  355: 430 */     StreamedContent file = null;
/*  356:     */     try
/*  357:     */     {
/*  358: 432 */       PagoCash pagoCashConDetalle = this.servicioPagoCash.cargarDetalle(pagoCashSeleccionado.getIdPagoCash());
/*  359:     */       
/*  360: 434 */       List<Object[]> listaDatos = this.servicioReportePagoProveedor.getCashManagementProveedor(pagoCashConDetalle);
/*  361:     */       
/*  362: 436 */       String rutaArchivoTxt = ParametrosSistema.getAS2_HOME(this.pagoCash.getIdOrganizacion()) + File.separator + "documentos" + File.separator + "pagoCashProveedor" + File.separator;
/*  363:     */       
/*  364: 438 */       String nombreArchivoTxt = "pagoCashProveedor-" + pagoCashSeleccionado.getFechaPago() + "-" + pagoCashSeleccionado.getIdPagoCash() + ".dat";
/*  365:     */       
/*  366:     */ 
/*  367: 441 */       int a = 0;
/*  368:     */       
/*  369: 443 */       boolean bancoPichincha = pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre().toLowerCase().contains("pichincha");
/*  370: 445 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("10")) || (bancoPichincha == true)) {
/*  371: 447 */         if (ParametrosSistema.isPagoCashProveedorShort(this.pagoCash.getIdOrganizacion()).booleanValue()) {
/*  372: 448 */           a = 12;
/*  373:     */         } else {
/*  374: 450 */           a = 20;
/*  375:     */         }
/*  376:     */       }
/*  377: 455 */       boolean bancoPacifico = pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre().toLowerCase().contains("pacifico");
/*  378: 456 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("30")) || (bancoPacifico))
/*  379:     */       {
/*  380: 458 */         nombreArchivoTxt = "Banco Pacifico" + pagoCashSeleccionado.getFechaPago() + "-" + pagoCashSeleccionado.getIdPagoCash() + " (" + (pagoCashSeleccionado.getTipoServicioCuentaBancaria() != null ? pagoCashSeleccionado.getTipoServicioCuentaBancaria() : "") + ")" + ".dat";
/*  381:     */         
/*  382: 460 */         a = 1;
/*  383: 461 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivoTxt, listaDatos, a, "");
/*  384:     */       }
/*  385: 464 */       boolean bancoProdubanco = pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre().toLowerCase().contains("produbanco");
/*  386: 465 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("36")) || (bancoProdubanco))
/*  387:     */       {
/*  388: 466 */         nombreArchivoTxt = "Banco Produbanco.dat";
/*  389: 467 */         a = 1;
/*  390: 468 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivoTxt, listaDatos, a, "");
/*  391:     */       }
/*  392: 471 */       boolean bancoInternacional = pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre().toLowerCase().contains("internacional");
/*  393: 472 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("32")) || (bancoInternacional))
/*  394:     */       {
/*  395: 473 */         nombreArchivoTxt = "Banco Internacional.dat";
/*  396: 474 */         a = 1;
/*  397: 475 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivoTxt, listaDatos, a, "");
/*  398:     */       }
/*  399: 479 */       boolean bancoBolivariano = pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre().toLowerCase().contains("bolivariano");
/*  400: 480 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("37")) || 
/*  401: 481 */         (pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("34")) || (bancoBolivariano))
/*  402:     */       {
/*  403: 484 */         String mes = "" + FuncionesUtiles.getMes(this.pagoCash.getFechaPago());
/*  404: 485 */         mes = mes.length() == 1 ? "0" + mes : mes;
/*  405: 486 */         String dia = "" + FuncionesUtiles.getDiaFecha(this.pagoCash.getFechaPago());
/*  406: 487 */         dia = dia.length() == 1 ? "0" + dia : dia;
/*  407:     */         
/*  408:     */ 
/*  409: 490 */         nombreArchivoTxt = AppUtil.getOrganizacion().getRazonSocial() + FuncionesUtiles.getAnio(this.pagoCash.getFechaPago()) + mes + dia + this.pagoCash.getIdPagoCash() + ".BIZ";
/*  410: 491 */         a = 1;
/*  411: 492 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivoTxt, listaDatos, a, "");
/*  412:     */       }
/*  413: 496 */       boolean bancoGuayaquil = pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre().toLowerCase().contains("guayaquil");
/*  414: 497 */       if ((pagoCashSeleccionado.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getCodigo().equals("17")) || (bancoGuayaquil))
/*  415:     */       {
/*  416: 499 */         Date hoy = Calendar.getInstance().getTime();
/*  417: 500 */         String mes = (FuncionesUtiles.getMes(hoy) < 9 ? "0" : "") + FuncionesUtiles.getMes(hoy);
/*  418: 501 */         String dia = (FuncionesUtiles.getDiaFecha(hoy) < 9 ? "0" : "") + FuncionesUtiles.getDiaFecha(hoy);
/*  419:     */         
/*  420: 503 */         nombreArchivoTxt = "PAGOS_MULTICASH_" + FuncionesUtiles.getAnio(this.pagoCash.getFechaPago()) + mes + dia;
/*  421: 504 */         int secuencia = Integer.parseInt(this.secuenciaAS2Service.getSecuencia(AppUtil.getOrganizacion().getId(), nombreArchivoTxt));
/*  422:     */         
/*  423: 506 */         nombreArchivoTxt = nombreArchivoTxt + "_" + (secuencia < 10 ? "0" : "") + secuencia + ".txt";
/*  424:     */         
/*  425: 508 */         a = 1;
/*  426: 509 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivoTxt, listaDatos, a, "");
/*  427:     */       }
/*  428:     */       else
/*  429:     */       {
/*  430: 511 */         FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivoTxt, listaDatos, a, "\t");
/*  431:     */       }
/*  432: 515 */       file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivoTxt, "application/txt", nombreArchivoTxt);
/*  433:     */       
/*  434: 517 */       LOG.info("Archivo pago cash proveedor generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivoTxt);
/*  435: 518 */       addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado") + ": " + rutaArchivoTxt + nombreArchivoTxt);
/*  436:     */     }
/*  437:     */     catch (AS2Exception e)
/*  438:     */     {
/*  439: 521 */       JsfUtil.addErrorMessage(e, "");
/*  440: 522 */       LOG.error("ERROR AL GENERAR EL ARCHIVO ", e);
/*  441:     */     }
/*  442:     */     catch (Exception e)
/*  443:     */     {
/*  444: 524 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/*  445: 525 */       LOG.error("ERROR AL GENERAR EL ARCHIVO ", e);
/*  446:     */     }
/*  447: 527 */     return file;
/*  448:     */   }
/*  449:     */   
/*  450:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  451:     */   {
/*  452: 537 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/*  453:     */   }
/*  454:     */   
/*  455:     */   public void seleccionarPagoCashAContabilizar(ActionEvent aev)
/*  456:     */   {
/*  457: 546 */     setMostrarDialogContabilizar(true);
/*  458: 547 */     PagoCash pagoCashSeleccionado = (PagoCash)this.dtPagoCash.getRowData();
/*  459: 548 */     PagoCash pagoCashConDetalle = this.servicioPagoCash.cargarDetalle(pagoCashSeleccionado.getIdPagoCash());
/*  460: 549 */     setPagoCash(pagoCashConDetalle);
/*  461:     */   }
/*  462:     */   
/*  463:     */   public String agregarAnticipo()
/*  464:     */   {
/*  465: 559 */     if (this.servicioEmpresa.obtenerListaCuentaBancariaEmpresa(AppUtil.getOrganizacion().getId(), getEmpresa().getId()).size() > 0)
/*  466:     */     {
/*  467: 560 */       this.servicioPagoCash.agregarAnticipo(this.pagoCash, null, this.empresa, this.valorAnticipo, this.descripcionAnticipo, this.personaResponsable);
/*  468:     */       
/*  469:     */ 
/*  470: 563 */       calculoTotales(this.pagoCash);
/*  471: 564 */       this.empresa = null;
/*  472: 565 */       this.valorAnticipo = null;
/*  473: 566 */       this.descripcionAnticipo = "";
/*  474:     */     }
/*  475:     */     else
/*  476:     */     {
/*  477: 568 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cuenta_bancaria_proveedor"));
/*  478: 569 */       this.empresa = null;
/*  479: 570 */       this.valorAnticipo = null;
/*  480: 571 */       this.descripcionAnticipo = "";
/*  481:     */     }
/*  482: 573 */     setIndicadorRenderedAnticipo(false);
/*  483: 574 */     return null;
/*  484:     */   }
/*  485:     */   
/*  486:     */   public String calcularValorPagoCash()
/*  487:     */   {
/*  488: 583 */     calculoTotales(this.pagoCash);
/*  489: 584 */     return "";
/*  490:     */   }
/*  491:     */   
/*  492:     */   public String eliminarDetalle()
/*  493:     */   {
/*  494: 593 */     DetallePagoCash detallePagoCash = (DetallePagoCash)this.dtDetallePagoCash.getRowData();
/*  495: 594 */     detallePagoCash.setEliminado(true);
/*  496: 595 */     calcularValorPagoCash();
/*  497:     */     
/*  498: 597 */     return "";
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void contabilizarPagoCash(ActionEvent aev)
/*  502:     */   {
/*  503:     */     try
/*  504:     */     {
/*  505: 607 */       if (this.totalValorAPagar.compareTo(BigDecimal.ZERO) == 0)
/*  506:     */       {
/*  507: 608 */         addErrorMessage(getLanguageController().getMensaje("msg_error_contabilizar_pago_cash_asiento_0"));
/*  508:     */       }
/*  509:     */       else
/*  510:     */       {
/*  511: 611 */         this.servicioPagoCash.contabilizarPagoCash(this.pagoCash, getDocumentoReferencia(), AppUtil.getUsuarioEnSesion().getNombreUsuario());
/*  512:     */         
/*  513: 613 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  514:     */       }
/*  515:     */     }
/*  516:     */     catch (ExcepcionAS2Financiero e)
/*  517:     */     {
/*  518: 616 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  519:     */     }
/*  520:     */     catch (ExcepcionAS2 e)
/*  521:     */     {
/*  522: 618 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  523:     */     }
/*  524:     */     catch (Exception e)
/*  525:     */     {
/*  526: 620 */       addErrorMessage(getLanguageController().getMensaje("msg_error_contabilizar_pago_cash"));
/*  527: 621 */       LOG.error("Error al contabilizar el pago cash " + e + ". Causa: " + e.getCause());
/*  528:     */     }
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void cargarValorCuota()
/*  532:     */   {
/*  533: 627 */     DetallePagoCash dpc = (DetallePagoCash)this.dtDetallePagoCash.getRowData();
/*  534: 628 */     datosBanco(dpc);
/*  535: 629 */     dpc.setValor(dpc.getCuentaPorPagar().getSaldo());
/*  536:     */     
/*  537: 631 */     calculoTotales(this.pagoCash);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void cargarValorCuotaGlobal()
/*  541:     */   {
/*  542: 635 */     for (DetallePagoCash dpc : this.pagoCash.getListaDetallePagoCash())
/*  543:     */     {
/*  544: 636 */       datosBanco(dpc);
/*  545: 637 */       if (dpc.getCuentaPorPagar() != null) {
/*  546: 638 */         dpc.setValor(dpc.getCuentaPorPagar().getSaldo());
/*  547:     */       }
/*  548:     */     }
/*  549: 640 */     calculoTotales(this.pagoCash);
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void limpiarValorCuota()
/*  553:     */   {
/*  554: 644 */     DetallePagoCash dpc = (DetallePagoCash)this.dtDetallePagoCash.getRowData();
/*  555: 645 */     dpc.setValor(BigDecimal.ZERO);
/*  556: 646 */     calculoTotales(this.pagoCash);
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void limpiarValorCuotaGlobal()
/*  560:     */   {
/*  561: 650 */     for (DetallePagoCash dpc : this.pagoCash.getListaDetallePagoCash()) {
/*  562: 651 */       if (dpc.getCuentaPorPagar() != null) {
/*  563: 652 */         dpc.setValor(BigDecimal.ZERO);
/*  564:     */       }
/*  565:     */     }
/*  566: 655 */     calculoTotales(this.pagoCash);
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void procesarTodos()
/*  570:     */   {
/*  571: 659 */     if (getPagoCash() != null) {
/*  572: 661 */       for (DetallePagoCash detallePagoCash : getPagoCash().getListaDetallePagoCash()) {
/*  573: 662 */         if ((!detallePagoCash.isEliminado()) && (detallePagoCash.isIndicadorAprobado())) {
/*  574: 663 */           detallePagoCash.setIndicadorProcesado(this.seleccionarTodos);
/*  575:     */         }
/*  576:     */       }
/*  577:     */     }
/*  578: 668 */     calcularTotalValorAPagar();
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void datosBanco(DetallePagoCash dpc)
/*  582:     */   {
/*  583: 673 */     this.servicioPagoCash.datosBanco(dpc);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void calculoTotales(PagoCash pagoCash)
/*  587:     */   {
/*  588: 677 */     this.totalValorALiquidar = BigDecimal.ZERO;
/*  589: 678 */     this.totalValorPendiente = BigDecimal.ZERO;
/*  590: 679 */     this.diferencia = BigDecimal.ZERO;
/*  591: 681 */     for (DetallePagoCash dpc : pagoCash.getListaDetallePagoCash())
/*  592:     */     {
/*  593: 682 */       this.totalValorALiquidar = this.totalValorALiquidar.add(dpc.getValor());
/*  594: 683 */       this.totalValorPendiente = this.totalValorPendiente.add(dpc.getCuentaPorPagar() != null ? dpc.getCuentaPorPagar().getSaldo() : BigDecimal.ZERO);
/*  595: 684 */       datosBanco(dpc);
/*  596:     */     }
/*  597: 687 */     this.diferencia = this.totalValorPendiente.subtract(this.totalValorALiquidar);
/*  598: 688 */     pagoCash.setValorPago(this.totalValorALiquidar);
/*  599:     */   }
/*  600:     */   
/*  601:     */   public void processDownload()
/*  602:     */   {
/*  603:     */     try
/*  604:     */     {
/*  605: 699 */       PagoCash fp = (PagoCash)this.dtPagoCash.getRowData();
/*  606: 700 */       String fileName = fp.getPdf();
/*  607: 701 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_cash");
/*  608: 703 */       if (fileName == null) {
/*  609: 704 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  610:     */       } else {
/*  611: 706 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  612:     */       }
/*  613:     */     }
/*  614:     */     catch (Exception e)
/*  615:     */     {
/*  616: 710 */       e.printStackTrace();
/*  617: 711 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   public String eliminarArchivo()
/*  622:     */   {
/*  623: 716 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getPagoCash().getPdf());
/*  624: 717 */     getPagoCash().setPdf(null);
/*  625: 718 */     HashMap<String, Object> campos = new HashMap();
/*  626: 719 */     campos.put("pdf", null);
/*  627: 720 */     this.servicioPagoCash.actualizarAtributoEntidad(getPagoCash(), campos);
/*  628: 721 */     return null;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public void processUpload(FileUploadEvent event)
/*  632:     */   {
/*  633:     */     try
/*  634:     */     {
/*  635: 734 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_cash");
/*  636:     */       
/*  637: 736 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getPagoCash().getNumero(), uploadDir);
/*  638:     */       
/*  639: 738 */       HashMap<String, Object> campos = new HashMap();
/*  640: 739 */       campos.put("pdf", fileName);
/*  641: 740 */       this.servicioPagoCash.actualizarAtributoEntidad(getPagoCash(), campos);
/*  642:     */       
/*  643: 742 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  644:     */     }
/*  645:     */     catch (Exception e)
/*  646:     */     {
/*  647: 745 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  648: 746 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  649:     */     }
/*  650:     */   }
/*  651:     */   
/*  652:     */   public String getDirectorioDescarga()
/*  653:     */   {
/*  654: 753 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pago_cash");
/*  655:     */   }
/*  656:     */   
/*  657:     */   public PagoCash getPagoCash()
/*  658:     */   {
/*  659: 765 */     if (this.pagoCash == null) {
/*  660: 766 */       crearPagoCash();
/*  661:     */     }
/*  662: 768 */     return this.pagoCash;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public void setPagoCash(PagoCash pagoCash)
/*  666:     */   {
/*  667: 778 */     this.pagoCash = pagoCash;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public LazyDataModel<PagoCash> getListaPagoCash()
/*  671:     */   {
/*  672: 787 */     return this.listaPagoCash;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void setListaPagoCash(LazyDataModel<PagoCash> listaPagoCash)
/*  676:     */   {
/*  677: 797 */     this.listaPagoCash = listaPagoCash;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public DataTable getDtPagoCash()
/*  681:     */   {
/*  682: 806 */     return this.dtPagoCash;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public void setDtPagoCash(DataTable dtPagoCash)
/*  686:     */   {
/*  687: 816 */     this.dtPagoCash = dtPagoCash;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/*  691:     */   {
/*  692: 825 */     if (this.listaCuentaBancariaOrganizacion == null) {
/*  693: 826 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/*  694:     */     }
/*  695: 828 */     return this.listaCuentaBancariaOrganizacion;
/*  696:     */   }
/*  697:     */   
/*  698:     */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/*  699:     */   {
/*  700: 838 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/*  701:     */   }
/*  702:     */   
/*  703:     */   public DataTable getDtDetallePagoCash()
/*  704:     */   {
/*  705: 847 */     return this.dtDetallePagoCash;
/*  706:     */   }
/*  707:     */   
/*  708:     */   public void setDtDetallePagoCash(DataTable dtDetallePagoCash)
/*  709:     */   {
/*  710: 857 */     this.dtDetallePagoCash = dtDetallePagoCash;
/*  711:     */   }
/*  712:     */   
/*  713:     */   public List<Documento> getListaDocumento()
/*  714:     */   {
/*  715: 867 */     if (this.listaDocumento == null) {
/*  716:     */       try
/*  717:     */       {
/*  718: 869 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_CASH_PROVEEDOR);
/*  719:     */       }
/*  720:     */       catch (ExcepcionAS2 e)
/*  721:     */       {
/*  722: 871 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  723:     */       }
/*  724:     */     }
/*  725: 874 */     return this.listaDocumento;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public void setListaDocumento(List<Documento> listaDocumento)
/*  729:     */   {
/*  730: 884 */     this.listaDocumento = listaDocumento;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public BigDecimal getTotalLiquidado()
/*  734:     */   {
/*  735: 899 */     this.totalLiquidado = BigDecimal.ZERO;
/*  736: 901 */     for (DetallePagoCash detallePagoCash : getPagoCash().getListaDetallePagoCash()) {
/*  737: 902 */       if (!detallePagoCash.isEliminado()) {
/*  738: 903 */         this.totalLiquidado = this.totalLiquidado.add(detallePagoCash.getValor());
/*  739:     */       }
/*  740:     */     }
/*  741: 907 */     return this.totalLiquidado;
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/*  745:     */   {
/*  746: 917 */     this.totalLiquidado = totalLiquidado;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public int getIdCuentaBancariaOrganizacion()
/*  750:     */   {
/*  751: 926 */     return this.idCuentaBancariaOrganizacion;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void setIdCuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/*  755:     */   {
/*  756: 936 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public List<FormaPago> getListaFormaPago()
/*  760:     */   {
/*  761: 945 */     if (this.listaFormaPago == null) {
/*  762: 946 */       this.listaFormaPago = this.servicioFormaPago.obtenerListaCombo(null, false, null);
/*  763:     */     }
/*  764: 948 */     return this.listaFormaPago;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public void setListaFormaPago(List<FormaPago> listaFormaPago)
/*  768:     */   {
/*  769: 958 */     this.listaFormaPago = listaFormaPago;
/*  770:     */   }
/*  771:     */   
/*  772:     */   public int getIdFormaPago()
/*  773:     */   {
/*  774: 967 */     return this.idFormaPago;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public void setIdFormaPago(int idFormaPago)
/*  778:     */   {
/*  779: 977 */     this.idFormaPago = idFormaPago;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public Empresa getEmpresa()
/*  783:     */   {
/*  784: 986 */     return this.empresa;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setEmpresa(Empresa empresa)
/*  788:     */   {
/*  789: 996 */     this.empresa = empresa;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public BigDecimal getValorAnticipo()
/*  793:     */   {
/*  794:1005 */     return this.valorAnticipo;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void setValorAnticipo(BigDecimal valorAnticipo)
/*  798:     */   {
/*  799:1015 */     this.valorAnticipo = valorAnticipo;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public List<DetallePagoCash> getListaDetallePagoCashContabilizar()
/*  803:     */   {
/*  804:1024 */     List<DetallePagoCash> lista = new ArrayList();
/*  805:1025 */     if (getPagoCash() != null) {
/*  806:1026 */       for (DetallePagoCash detallePagoCash : getPagoCash().getListaDetallePagoCash()) {
/*  807:1027 */         if ((!detallePagoCash.isEliminado()) && (detallePagoCash.isIndicadorAprobado())) {
/*  808:1028 */           lista.add(detallePagoCash);
/*  809:     */         }
/*  810:     */       }
/*  811:     */     }
/*  812:1033 */     return lista;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public DataTable getDtDetallePagoCashAContabilizar()
/*  816:     */   {
/*  817:1042 */     return this.dtDetallePagoCashAContabilizar;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public void setDtDetallePagoCashAContabilizar(DataTable dtDetallePagoCashAContabilizar)
/*  821:     */   {
/*  822:1052 */     this.dtDetallePagoCashAContabilizar = dtDetallePagoCashAContabilizar;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public String getDocumentoReferencia()
/*  826:     */   {
/*  827:1061 */     return this.documentoReferencia;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void setDocumentoReferencia(String documentoReferencia)
/*  831:     */   {
/*  832:1071 */     this.documentoReferencia = documentoReferencia;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public String getDescripcionAnticipo()
/*  836:     */   {
/*  837:1080 */     return this.descripcionAnticipo;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public void setDescripcionAnticipo(String descripcionAnticipo)
/*  841:     */   {
/*  842:1090 */     this.descripcionAnticipo = descripcionAnticipo;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public StreamedContent getFile()
/*  846:     */   {
/*  847:1099 */     PagoCash pagoCashSeleccionado = (PagoCash)this.dtPagoCash.getRowData();
/*  848:1100 */     this.file = generarArchivo(pagoCashSeleccionado);
/*  849:1101 */     return this.file;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public void setFile(StreamedContent file)
/*  853:     */   {
/*  854:1111 */     this.file = file;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public boolean isMostrarDialogContabilizar()
/*  858:     */   {
/*  859:1120 */     return this.mostrarDialogContabilizar;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public void setMostrarDialogContabilizar(boolean mostrarDialogContabilizar)
/*  863:     */   {
/*  864:1130 */     this.mostrarDialogContabilizar = mostrarDialogContabilizar;
/*  865:     */   }
/*  866:     */   
/*  867:     */   public void setListaDetallePagoCash(List<DetallePagoCash> listaDetallePagoCash)
/*  868:     */   {
/*  869:1138 */     this.listaDetallePagoCash = listaDetallePagoCash;
/*  870:     */   }
/*  871:     */   
/*  872:     */   public List<DetallePagoCash> getListaDetallePagoCash()
/*  873:     */   {
/*  874:1145 */     if (this.listaDetallePagoCash == null) {
/*  875:1146 */       this.listaDetallePagoCash = new ArrayList();
/*  876:     */     }
/*  877:1148 */     return this.listaDetallePagoCash;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public List<DetallePagoCash> getListaDetallePagoCashFilters()
/*  881:     */   {
/*  882:1155 */     return this.listaDetallePagoCashFilters;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void setListaDetallePagoCashFilters(List<DetallePagoCash> listaDetallePagoCashFilters)
/*  886:     */   {
/*  887:1163 */     this.listaDetallePagoCashFilters = listaDetallePagoCashFilters;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public BigDecimal getTotalValorALiquidar()
/*  891:     */   {
/*  892:1170 */     return this.totalValorALiquidar;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void setTotalValorALiquidar(BigDecimal totalValorALiquidar)
/*  896:     */   {
/*  897:1178 */     this.totalValorALiquidar = totalValorALiquidar;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public BigDecimal getTotalValorPendiente()
/*  901:     */   {
/*  902:1185 */     return this.totalValorPendiente;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void setTotalValorPendiente(BigDecimal totalValorPendiente)
/*  906:     */   {
/*  907:1193 */     this.totalValorPendiente = totalValorPendiente;
/*  908:     */   }
/*  909:     */   
/*  910:     */   public BigDecimal getDiferencia()
/*  911:     */   {
/*  912:1200 */     return this.diferencia;
/*  913:     */   }
/*  914:     */   
/*  915:     */   public void setDiferencia(BigDecimal diferencia)
/*  916:     */   {
/*  917:1208 */     this.diferencia = diferencia;
/*  918:     */   }
/*  919:     */   
/*  920:     */   public Date getFechaContabilizacion()
/*  921:     */   {
/*  922:1215 */     return this.fechaContabilizacion;
/*  923:     */   }
/*  924:     */   
/*  925:     */   public void setFechaContabilizacion(Date fechaContabilizacion)
/*  926:     */   {
/*  927:1223 */     this.fechaContabilizacion = fechaContabilizacion;
/*  928:     */   }
/*  929:     */   
/*  930:     */   public List<FormaPago> getListaFormaPagoOrganizacion()
/*  931:     */   {
/*  932:1230 */     return this.listaFormaPagoOrganizacion;
/*  933:     */   }
/*  934:     */   
/*  935:     */   public void setListaFormaPagoOrganizacion(List<FormaPago> listaFormaPagoOrganizacion)
/*  936:     */   {
/*  937:1238 */     this.listaFormaPagoOrganizacion = listaFormaPagoOrganizacion;
/*  938:     */   }
/*  939:     */   
/*  940:     */   public CategoriaEmpresa getCategoriaEmpresa()
/*  941:     */   {
/*  942:1242 */     return this.categoriaEmpresa;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/*  946:     */   {
/*  947:1246 */     this.categoriaEmpresa = categoriaEmpresa;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/*  951:     */   {
/*  952:1255 */     HashMap<String, String> filtros = new HashMap();
/*  953:1256 */     filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  954:1258 */     if (this.listaCategoriaEmpresa == null) {
/*  955:1259 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros);
/*  956:     */     }
/*  957:1261 */     return this.listaCategoriaEmpresa;
/*  958:     */   }
/*  959:     */   
/*  960:     */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/*  961:     */   {
/*  962:1271 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public HashMap<String, DetallePagoCash> getHmDetallePagoCash()
/*  966:     */   {
/*  967:1275 */     return this.hmDetallePagoCash;
/*  968:     */   }
/*  969:     */   
/*  970:     */   public void setHmDetallePagoCash(HashMap<String, DetallePagoCash> hmDetallePagoCash)
/*  971:     */   {
/*  972:1279 */     this.hmDetallePagoCash = hmDetallePagoCash;
/*  973:     */   }
/*  974:     */   
/*  975:     */   public boolean isActivadorTipoServicio()
/*  976:     */   {
/*  977:1283 */     return this.activadorTipoServicio;
/*  978:     */   }
/*  979:     */   
/*  980:     */   public void setActivadorTipoServicio(boolean activadorTipoServicio)
/*  981:     */   {
/*  982:1287 */     this.activadorTipoServicio = activadorTipoServicio;
/*  983:     */   }
/*  984:     */   
/*  985:     */   public List<Documento> getListaDocumentoPago()
/*  986:     */   {
/*  987:1295 */     if (this.listaDocumentoPago == null) {
/*  988:     */       try
/*  989:     */       {
/*  990:1297 */         this.listaDocumentoPago = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PAGO_PROVEEDOR);
/*  991:     */       }
/*  992:     */       catch (ExcepcionAS2 e)
/*  993:     */       {
/*  994:1299 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  995:     */       }
/*  996:     */     }
/*  997:1303 */     return this.listaDocumentoPago;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public void setListaDocumentoPago(List<Documento> listaDocumentoPago)
/* 1001:     */   {
/* 1002:1311 */     this.listaDocumentoPago = listaDocumentoPago;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public List<Documento> getListaDocumentoAnticipo()
/* 1006:     */   {
/* 1007:1319 */     if (this.listaDocumentoAnticipo == null) {
/* 1008:     */       try
/* 1009:     */       {
/* 1010:1321 */         this.listaDocumentoAnticipo = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.ANTICIPO_PROVEEDOR);
/* 1011:     */       }
/* 1012:     */       catch (ExcepcionAS2 e)
/* 1013:     */       {
/* 1014:1323 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1015:     */       }
/* 1016:     */     }
/* 1017:1327 */     return this.listaDocumentoAnticipo;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void setListaDocumentoAnticipo(List<Documento> listaDocumentoAnticipo)
/* 1021:     */   {
/* 1022:1335 */     this.listaDocumentoAnticipo = listaDocumentoAnticipo;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public BigDecimal getTotalValorAPagar()
/* 1026:     */   {
/* 1027:1339 */     return this.totalValorAPagar;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public void setTotalValorAPagar(BigDecimal totalValorAPagar)
/* 1031:     */   {
/* 1032:1343 */     this.totalValorAPagar = totalValorAPagar;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public void calcularTotalValorAPagar()
/* 1036:     */   {
/* 1037:1347 */     this.totalValorAPagar = BigDecimal.ZERO;
/* 1038:1348 */     for (DetallePagoCash dpc : this.pagoCash.getListaDetallePagoCash()) {
/* 1039:1349 */       if (dpc.isIndicadorProcesado()) {
/* 1040:1350 */         this.totalValorAPagar = this.totalValorAPagar.add(dpc.getValor());
/* 1041:     */       }
/* 1042:     */     }
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public boolean isSeleccionarTodos()
/* 1046:     */   {
/* 1047:1356 */     return this.seleccionarTodos;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void setSeleccionarTodos(boolean seleccionarTodos)
/* 1051:     */   {
/* 1052:1360 */     this.seleccionarTodos = seleccionarTodos;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public Documento getDocumentoFiltro()
/* 1056:     */   {
/* 1057:1364 */     return this.documentoFiltro;
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public void setDocumentoFiltro(Documento documentoFiltro)
/* 1061:     */   {
/* 1062:1368 */     this.documentoFiltro = documentoFiltro;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public List<Documento> getListaDocumentoFiltro()
/* 1066:     */   {
/* 1067:1372 */     if (this.listaDocumentoFiltro == null) {
/* 1068:     */       try
/* 1069:     */       {
/* 1070:1374 */         this.listaDocumentoFiltro = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_PROVEEDOR, 
/* 1071:1375 */           AppUtil.getOrganizacion().getId());
/* 1072:     */       }
/* 1073:     */       catch (ExcepcionAS2 e)
/* 1074:     */       {
/* 1075:1377 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1076:     */       }
/* 1077:     */     }
/* 1078:1381 */     return this.listaDocumentoFiltro;
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public Boolean getUsaOrdenPago()
/* 1082:     */   {
/* 1083:1385 */     if (this.usaOrdenPago == null) {
/* 1084:1386 */       this.usaOrdenPago = ParametrosSistema.getIndicadorOrdenesPago(AppUtil.getOrganizacion().getId());
/* 1085:     */     }
/* 1086:1388 */     return this.usaOrdenPago;
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 1090:     */   {
/* 1091:1392 */     if (this.listaPersonaResponsable == null)
/* 1092:     */     {
/* 1093:1393 */       HashMap<String, String> filters = new HashMap();
/* 1094:1394 */       filters.put("indicadorCompra", "true");
/* 1095:1395 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 1096:     */     }
/* 1097:1397 */     return this.listaPersonaResponsable;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void setListaPersonaResponsable(List<PersonaResponsable> listaPersonaResponsable)
/* 1101:     */   {
/* 1102:1401 */     this.listaPersonaResponsable = listaPersonaResponsable;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public boolean isIndicadorOrdenCompraConPersonaResponsable()
/* 1106:     */   {
/* 1107:1405 */     return ParametrosSistema.isOrdenCompraConPersonaResponsable(AppUtil.getOrganizacion().getId()).booleanValue();
/* 1108:     */   }
/* 1109:     */   
/* 1110:     */   public PersonaResponsable getPersonaResponsable()
/* 1111:     */   {
/* 1112:     */     List<EntidadUsuario> listaUsuario;
/* 1113:1409 */     if ((isIndicadorOrdenCompraConPersonaResponsable()) && (this.personaResponsable == null))
/* 1114:     */     {
/* 1115:1412 */       HashMap<String, String> filters = new HashMap();
/* 1116:1413 */       filters.put("idUsuario", "" + AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 1117:1414 */       listaUsuario = this.servicioUsuario.obtenerListaCombo("", true, filters);
/* 1118:1417 */       if ((listaUsuario != null) && (!listaUsuario.isEmpty()) && (((EntidadUsuario)listaUsuario.get(0)).getEmpleado() != null)) {
/* 1119:1418 */         for (PersonaResponsable personaResponsable : getListaPersonaResponsable()) {
/* 1120:1419 */           if ((personaResponsable.getEmpleado() != null) && 
/* 1121:1420 */             (personaResponsable.getEmpleado().getIdEmpleado() == ((EntidadUsuario)listaUsuario.get(0)).getEmpleado().getIdEmpleado()))
/* 1122:     */           {
/* 1123:1421 */             setPersonaResponsable(personaResponsable);
/* 1124:1422 */             break;
/* 1125:     */           }
/* 1126:     */         }
/* 1127:     */       }
/* 1128:     */     }
/* 1129:1427 */     return this.personaResponsable;
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 1133:     */   {
/* 1134:1431 */     this.personaResponsable = personaResponsable;
/* 1135:     */   }
/* 1136:     */   
/* 1137:     */   public boolean isIndicadorRenderedAnticipo()
/* 1138:     */   {
/* 1139:1435 */     return this.indicadorRenderedAnticipo;
/* 1140:     */   }
/* 1141:     */   
/* 1142:     */   public void setIndicadorRenderedAnticipo(boolean indicadorRenderedAnticipo)
/* 1143:     */   {
/* 1144:1439 */     this.indicadorRenderedAnticipo = indicadorRenderedAnticipo;
/* 1145:     */   }
/* 1146:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.PagoCashBean
 * JD-Core Version:    0.7.0.1
 */