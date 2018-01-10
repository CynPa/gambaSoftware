/*    1:     */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.controller.LanguageController;
/*    5:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*    8:     */ import com.asinfo.as2.entities.ConciliacionBancaria;
/*    9:     */ import com.asinfo.as2.entities.ConfiguracionConciliacionBancaria;
/*   10:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   11:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   12:     */ import com.asinfo.as2.entities.CuentaContable;
/*   13:     */ import com.asinfo.as2.entities.Documento;
/*   14:     */ import com.asinfo.as2.entities.FormaPago;
/*   15:     */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   16:     */ import com.asinfo.as2.entities.MovimientoBancario;
/*   17:     */ import com.asinfo.as2.entities.Organizacion;
/*   18:     */ import com.asinfo.as2.entities.Sucursal;
/*   19:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   20:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   21:     */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*   22:     */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*   23:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   24:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   25:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioConciliacionBancaria;
/*   26:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   27:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   28:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   29:     */ import com.asinfo.as2.util.AppUtil;
/*   30:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   31:     */ import java.io.BufferedInputStream;
/*   32:     */ import java.io.IOException;
/*   33:     */ import java.io.InputStream;
/*   34:     */ import java.io.PrintStream;
/*   35:     */ import java.math.BigDecimal;
/*   36:     */ import java.util.ArrayList;
/*   37:     */ import java.util.Date;
/*   38:     */ import java.util.HashMap;
/*   39:     */ import java.util.Iterator;
/*   40:     */ import java.util.List;
/*   41:     */ import java.util.Map;
/*   42:     */ import javax.annotation.PostConstruct;
/*   43:     */ import javax.ejb.EJB;
/*   44:     */ import javax.faces.bean.ManagedBean;
/*   45:     */ import javax.faces.bean.ViewScoped;
/*   46:     */ import org.apache.log4j.Logger;
/*   47:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   48:     */ import org.primefaces.component.datatable.DataTable;
/*   49:     */ import org.primefaces.context.RequestContext;
/*   50:     */ import org.primefaces.event.FileUploadEvent;
/*   51:     */ import org.primefaces.event.SelectEvent;
/*   52:     */ import org.primefaces.model.LazyDataModel;
/*   53:     */ import org.primefaces.model.SortOrder;
/*   54:     */ import org.primefaces.model.UploadedFile;
/*   55:     */ 
/*   56:     */ @ManagedBean
/*   57:     */ @ViewScoped
/*   58:     */ public class ConciliacionBancariaBean
/*   59:     */   extends PageControllerAS2
/*   60:     */ {
/*   61:     */   private static final long serialVersionUID = -8080160392358095583L;
/*   62:     */   @EJB
/*   63:     */   private ServicioConciliacionBancaria servicioConciliacionBancaria;
/*   64:     */   @EJB
/*   65:     */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   66:     */   @EJB
/*   67:     */   private ServicioCuentaContable servicioCuentaContable;
/*   68:     */   @EJB
/*   69:     */   private ServicioDocumento servicioDocumento;
/*   70:     */   @EJB
/*   71:     */   private ServicioFormaPago servicioFormaPago;
/*   72:     */   @EJB
/*   73:     */   private ServicioGenerico<ConfiguracionConciliacionBancaria> servicioConfiguracionConciliacionBancaria;
/*   74:     */   private ConciliacionBancaria conciliacionBancaria;
/*   75:     */   private MovimientoBancario movimientoBancarioNoRegistrado;
/*   76:     */   private LazyDataModel<ConciliacionBancaria> listaConciliacionBancaria;
/*   77:     */   private List<MovimientoBancario> listaDetalleConciliacionBancaria;
/*   78:     */   private List<MovimientoBancario> listaDocumentosConciliados;
/*   79:     */   private List<MovimientoBancario> listaDocumentosNoConciliados;
/*   80:     */   private List<MovimientoBancario> listaMovimientoBancarioNoRegistrado;
/*   81:     */   private List<MovimientoBancario> listaDetalleConciliacionBancariaFilteredValue;
/*   82:     */   private List<MovimientoBancario> listaDocumentosConciliadosFilteredValue;
/*   83:     */   private List<MovimientoBancario> listaDocumentosNoConciliadosFilteredValue;
/*   84:     */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*   85:     */   private List<Documento> listaDocumento;
/*   86:     */   private DataTable dtConciliacionBancaria;
/*   87:     */   private DataTable dtDetalleConciliacionBancaria;
/*   88:     */   private DataTable dtDetalleDocumentosConciliados;
/*   89:     */   private DataTable dtDetalleDocumentosNoConciliados;
/*   90:     */   private DataTable dtDetalleDocumentosNoRegistradosEnLibros;
/*   91: 115 */   private BigDecimal totalConciliado = BigDecimal.ZERO;
/*   92: 116 */   private BigDecimal totalDebitosNoConciliados = BigDecimal.ZERO;
/*   93: 117 */   private BigDecimal totalDebitosConciliados = BigDecimal.ZERO;
/*   94: 118 */   private BigDecimal totalCreditosNoConciliados = BigDecimal.ZERO;
/*   95: 119 */   private BigDecimal totalCreditosConciliados = BigDecimal.ZERO;
/*   96: 120 */   private BigDecimal saldoEstadoCuenta = BigDecimal.ZERO;
/*   97: 121 */   private BigDecimal diferencia = BigDecimal.ZERO;
/*   98: 122 */   private BigDecimal saldoContable = BigDecimal.ZERO;
/*   99: 123 */   private BigDecimal totalDebe = BigDecimal.ZERO;
/*  100: 124 */   private BigDecimal totalHaber = BigDecimal.ZERO;
/*  101: 125 */   private BigDecimal totalCreditosChequesNoConciliados = BigDecimal.ZERO;
/*  102:     */   private Integer idCuentaBancariaOrganizacion;
/*  103:     */   private CuentaContable cuentaBanco;
/*  104:     */   private boolean indicadorRender;
/*  105:     */   
/*  106:     */   @PostConstruct
/*  107:     */   public void init()
/*  108:     */   {
/*  109: 139 */     this.listaConciliacionBancaria = new LazyDataModel()
/*  110:     */     {
/*  111:     */       private static final long serialVersionUID = 1L;
/*  112:     */       
/*  113:     */       public List<ConciliacionBancaria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  114:     */       {
/*  115: 146 */         List<ConciliacionBancaria> lista = new ArrayList();
/*  116: 147 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  117: 149 */         if ((ConciliacionBancariaBean.this.idCuentaBancariaOrganizacion != null) && 
/*  118: 150 */           (ConciliacionBancariaBean.this.idCuentaBancariaOrganizacion != null))
/*  119:     */         {
/*  120: 151 */           filters.put("cuentaBancariaOrganizacion.idCuentaBancariaOrganizacion", String.valueOf(ConciliacionBancariaBean.this.idCuentaBancariaOrganizacion));
/*  121: 152 */           ConciliacionBancariaBean.this.idCuentaBancariaOrganizacion = null;
/*  122:     */         }
/*  123: 156 */         lista = ConciliacionBancariaBean.this.servicioConciliacionBancaria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  124: 157 */         ConciliacionBancariaBean.this.listaConciliacionBancaria.setRowCount(ConciliacionBancariaBean.this.servicioConciliacionBancaria.contarPorCriterio(filters));
/*  125:     */         
/*  126: 159 */         return lista;
/*  127:     */       }
/*  128:     */     };
/*  129:     */   }
/*  130:     */   
/*  131:     */   public String editar()
/*  132:     */   {
/*  133: 172 */     if ((this.conciliacionBancaria != null) && (this.conciliacionBancaria.getId() > 0)) {
/*  134:     */       try
/*  135:     */       {
/*  136: 174 */         this.servicioConciliacionBancaria.esEditable(this.conciliacionBancaria);
/*  137:     */         
/*  138: 176 */         this.conciliacionBancaria = this.servicioConciliacionBancaria.cargarDetalle(this.conciliacionBancaria.getId());
/*  139: 177 */         actualizaCuentaBanco(this.conciliacionBancaria);
/*  140:     */         
/*  141: 179 */         cargarDatosAConciliar();
/*  142:     */         
/*  143: 181 */         setEditado(true);
/*  144:     */       }
/*  145:     */       catch (ExcepcionAS2Financiero e)
/*  146:     */       {
/*  147: 184 */         e.printStackTrace();
/*  148: 185 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  149: 186 */         LOG.info("ERROR AL EDITAR CONCILIACION BANCARIA");
/*  150:     */       }
/*  151:     */     } else {
/*  152: 190 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  153:     */     }
/*  154: 193 */     return "";
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void calcular()
/*  158:     */   {
/*  159: 202 */     if (this.cuentaBanco != null)
/*  160:     */     {
/*  161: 204 */       this.totalDebitosNoConciliados = BigDecimal.ZERO;
/*  162: 205 */       this.totalCreditosNoConciliados = BigDecimal.ZERO;
/*  163: 206 */       this.totalDebitosConciliados = BigDecimal.ZERO;
/*  164: 207 */       this.totalCreditosConciliados = BigDecimal.ZERO;
/*  165: 208 */       this.listaDocumentosConciliados = new ArrayList();
/*  166: 209 */       this.listaDocumentosNoConciliados = new ArrayList();
/*  167:     */       
/*  168: 211 */       this.totalCreditosChequesNoConciliados = BigDecimal.ZERO;
/*  169: 213 */       for (MovimientoBancario d : this.conciliacionBancaria.getListaMovimientoBancario()) {
/*  170: 215 */         if (!d.isConciliado())
/*  171:     */         {
/*  172: 217 */           this.listaDocumentosNoConciliados.add(d);
/*  173: 219 */           if (d.getValor().compareTo(BigDecimal.ZERO) < 0)
/*  174:     */           {
/*  175: 220 */             this.totalCreditosNoConciliados = this.totalCreditosNoConciliados.add(d.getValor().abs());
/*  176: 221 */             if (d.getFormaPago().isIndicadorCheque()) {
/*  177: 222 */               this.totalCreditosChequesNoConciliados = this.totalCreditosChequesNoConciliados.add(d.getValor().abs());
/*  178:     */             }
/*  179:     */           }
/*  180:     */           else
/*  181:     */           {
/*  182: 225 */             this.totalDebitosNoConciliados = this.totalDebitosNoConciliados.add(d.getValor());
/*  183:     */           }
/*  184:     */         }
/*  185:     */         else
/*  186:     */         {
/*  187: 228 */           this.listaDocumentosConciliados.add(d);
/*  188: 229 */           if (d.getValor().compareTo(BigDecimal.ZERO) < 0) {
/*  189: 230 */             this.totalCreditosConciliados = this.totalCreditosConciliados.add(d.getValor().abs());
/*  190:     */           } else {
/*  191: 232 */             this.totalDebitosConciliados = this.totalDebitosConciliados.add(d.getValor());
/*  192:     */           }
/*  193:     */         }
/*  194:     */       }
/*  195: 237 */       this.saldoEstadoCuenta = this.conciliacionBancaria.getSaldoBancos().add(this.totalDebitosNoConciliados).subtract(this.totalCreditosNoConciliados);
/*  196: 238 */       this.saldoContable = this.servicioCuentaContable.obtenerSaldo(FuncionesUtiles.obtenerFechaInicial(), this.conciliacionBancaria.getFecha(), this.cuentaBanco
/*  197: 239 */         .getId(), ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_FINAL, false, -1);
/*  198:     */       
/*  199: 241 */       this.diferencia = this.saldoContable.subtract(this.saldoEstadoCuenta);
/*  200:     */     }
/*  201:     */     else
/*  202:     */     {
/*  203: 244 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_cuenta_bancaria"));
/*  204:     */     }
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void desconciliarTodo()
/*  208:     */   {
/*  209: 250 */     if (this.listaDocumentosConciliados != null)
/*  210:     */     {
/*  211: 251 */       for (MovimientoBancario movimiento : this.listaDocumentosConciliados) {
/*  212: 252 */         movimiento.setConciliado(false);
/*  213:     */       }
/*  214: 255 */       calcular();
/*  215:     */     }
/*  216:     */   }
/*  217:     */   
/*  218:     */   public void conciliarTodo()
/*  219:     */   {
/*  220: 260 */     if (this.listaDocumentosNoConciliados != null)
/*  221:     */     {
/*  222: 261 */       for (MovimientoBancario movimiento : this.listaDocumentosNoConciliados) {
/*  223: 262 */         movimiento.setConciliado(true);
/*  224:     */       }
/*  225: 265 */       calcular();
/*  226:     */     }
/*  227:     */   }
/*  228:     */   
/*  229:     */   public void calcular(MovimientoBancario movimientoBancario)
/*  230:     */   {
/*  231: 270 */     if (this.cuentaBanco != null)
/*  232:     */     {
/*  233: 271 */       if (movimientoBancario.isConciliado())
/*  234:     */       {
/*  235: 272 */         getListaDocumentosNoConciliados().remove(movimientoBancario);
/*  236: 273 */         getListaDocumentosConciliados().add(movimientoBancario);
/*  237:     */       }
/*  238:     */       else
/*  239:     */       {
/*  240: 275 */         getListaDocumentosConciliados().remove(movimientoBancario);
/*  241: 276 */         getListaDocumentosNoConciliados().add(movimientoBancario);
/*  242:     */       }
/*  243: 279 */       this.listaDocumentosNoConciliadosFilteredValue = null;
/*  244: 280 */       this.listaDocumentosConciliadosFilteredValue = null;
/*  245: 281 */       this.dtDetalleDocumentosConciliados.reset();
/*  246: 282 */       this.dtDetalleDocumentosNoConciliados.reset();
/*  247: 284 */       if (movimientoBancario.isConciliado())
/*  248:     */       {
/*  249: 285 */         if (movimientoBancario.getValor().compareTo(BigDecimal.ZERO) < 0)
/*  250:     */         {
/*  251: 286 */           this.totalCreditosNoConciliados = this.totalCreditosNoConciliados.subtract(movimientoBancario.getValor().abs());
/*  252: 287 */           this.totalCreditosConciliados = this.totalCreditosConciliados.add(movimientoBancario.getValor().abs());
/*  253:     */         }
/*  254:     */         else
/*  255:     */         {
/*  256: 289 */           this.totalDebitosNoConciliados = this.totalDebitosNoConciliados.subtract(movimientoBancario.getValor());
/*  257: 290 */           this.totalDebitosConciliados = this.totalDebitosConciliados.add(movimientoBancario.getValor());
/*  258:     */         }
/*  259:     */       }
/*  260: 293 */       else if (movimientoBancario.getValor().compareTo(BigDecimal.ZERO) < 0)
/*  261:     */       {
/*  262: 294 */         this.totalCreditosNoConciliados = this.totalCreditosNoConciliados.add(movimientoBancario.getValor().abs());
/*  263: 295 */         this.totalCreditosConciliados = this.totalCreditosConciliados.subtract(movimientoBancario.getValor().abs());
/*  264:     */       }
/*  265:     */       else
/*  266:     */       {
/*  267: 297 */         this.totalDebitosNoConciliados = this.totalDebitosNoConciliados.add(movimientoBancario.getValor());
/*  268: 298 */         this.totalDebitosConciliados = this.totalDebitosConciliados.subtract(movimientoBancario.getValor());
/*  269:     */       }
/*  270: 302 */       this.saldoEstadoCuenta = this.conciliacionBancaria.getSaldoBancos().add(this.totalDebitosNoConciliados).subtract(this.totalCreditosNoConciliados);
/*  271: 303 */       this.saldoContable = this.servicioCuentaContable.obtenerSaldo(FuncionesUtiles.obtenerFechaInicial(), this.conciliacionBancaria.getFecha(), this.cuentaBanco
/*  272: 304 */         .getId(), ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_FINAL, false, -1);
/*  273:     */       
/*  274: 306 */       this.diferencia = this.saldoContable.subtract(this.saldoEstadoCuenta);
/*  275:     */     }
/*  276:     */     else
/*  277:     */     {
/*  278: 309 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_cuenta_bancaria"));
/*  279:     */     }
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String guardar()
/*  283:     */   {
/*  284:     */     try
/*  285:     */     {
/*  286: 323 */       this.servicioConciliacionBancaria.guardar(this.conciliacionBancaria);
/*  287: 324 */       this.listaMovimientoBancarioNoRegistrado = null;
/*  288: 325 */       this.listaDetalleConciliacionBancaria = null;
/*  289:     */       
/*  290: 327 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  291: 328 */       setEditado(false);
/*  292:     */     }
/*  293:     */     catch (Exception e)
/*  294:     */     {
/*  295: 330 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  296: 331 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  297:     */     }
/*  298: 333 */     return "";
/*  299:     */   }
/*  300:     */   
/*  301:     */   public String limpiar()
/*  302:     */   {
/*  303: 343 */     crearConciliacionBancaria();
/*  304: 344 */     this.listaDetalleConciliacionBancaria = null;
/*  305: 345 */     this.listaMovimientoBancarioNoRegistrado = null;
/*  306: 346 */     return "";
/*  307:     */   }
/*  308:     */   
/*  309:     */   private void crearConciliacionBancaria()
/*  310:     */   {
/*  311: 350 */     this.conciliacionBancaria = new ConciliacionBancaria();
/*  312: 351 */     this.conciliacionBancaria.setEstado(Estado.ELABORADO);
/*  313: 352 */     this.conciliacionBancaria.setSucursal(AppUtil.getSucursal());
/*  314: 353 */     this.conciliacionBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  315: 354 */     this.conciliacionBancaria.setFecha(new Date());
/*  316:     */   }
/*  317:     */   
/*  318:     */   public String eliminar()
/*  319:     */   {
/*  320: 364 */     return "";
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void cargarDatosAConciliarPorFechaListener()
/*  324:     */   {
/*  325: 380 */     cargarDatosAConciliar();
/*  326:     */   }
/*  327:     */   
/*  328:     */   public List<CuentaBancariaOrganizacion> autocompletarCuentaBancariaOrganizacion(String consula)
/*  329:     */   {
/*  330: 391 */     consula = consula.toUpperCase();
/*  331:     */     
/*  332: 393 */     List<CuentaBancariaOrganizacion> lista = new ArrayList();
/*  333: 395 */     for (CuentaBancariaOrganizacion cuentaBancariaOrganizacion : getListaCuentaBancariaOrganizacion()) {
/*  334: 396 */       if (cuentaBancariaOrganizacion.getNombreCompleto().toUpperCase().contains(consula)) {
/*  335: 397 */         lista.add(cuentaBancariaOrganizacion);
/*  336:     */       }
/*  337:     */     }
/*  338: 401 */     return lista;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public void cargarDatosAConciliar()
/*  342:     */   {
/*  343: 410 */     this.listaDetalleConciliacionBancaria = null;
/*  344: 411 */     this.listaMovimientoBancarioNoRegistrado = null;
/*  345: 413 */     if ((this.conciliacionBancaria != null) && (this.conciliacionBancaria.getFecha() != null) && (this.conciliacionBancaria.getCuentaBancariaOrganizacion() != null))
/*  346:     */     {
/*  347: 415 */       List<MovimientoBancario> listaMovimientoBancario = this.servicioConciliacionBancaria.cargarDatosConciliar(this.conciliacionBancaria);
/*  348: 416 */       for (MovimientoBancario movimientoBancario : listaMovimientoBancario) {
/*  349: 417 */         if (movimientoBancario.getConciliacionBancaria() != null) {
/*  350: 418 */           movimientoBancario.setConciliado(true);
/*  351:     */         }
/*  352:     */       }
/*  353: 421 */       this.listaDetalleConciliacionBancaria = null;
/*  354: 422 */       this.listaMovimientoBancarioNoRegistrado = null;
/*  355: 423 */       this.conciliacionBancaria.setListaMovimientoBancario(listaMovimientoBancario);
/*  356:     */     }
/*  357: 426 */     calcular();
/*  358:     */   }
/*  359:     */   
/*  360:     */   public void actualizarCuentaBancariaOrganizacionListener(SelectEvent event)
/*  361:     */   {
/*  362: 431 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = (CuentaBancariaOrganizacion)event.getObject();
/*  363: 432 */     this.conciliacionBancaria.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/*  364:     */     
/*  365: 434 */     actualizaCuentaBanco(this.conciliacionBancaria);
/*  366:     */     
/*  367:     */ 
/*  368:     */ 
/*  369:     */ 
/*  370: 439 */     cargarDatosAConciliar();
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void actualizaCuentaBanco(ConciliacionBancaria conciliacion)
/*  374:     */   {
/*  375: 444 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(conciliacion
/*  376: 445 */       .getCuentaBancariaOrganizacion().getId());
/*  377: 446 */     this.cuentaBanco = cuentaBancariaOrganizacion.getCuentaContableBanco();
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void contabilizarGastosYRetenciones(ConciliacionBancaria conciliacion, HSSFCell[][] datos)
/*  381:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  382:     */   {
/*  383: 461 */     List<DetalleInterfazContable> listaDetalleIC = new ArrayList();
/*  384: 462 */     Map<String, CuentaContable> hmCuentaContable = new HashMap();
/*  385: 463 */     Map<Integer, DetalleInterfazContable> hmDetalleInterfazContableGastoRetencion = new HashMap();
/*  386: 464 */     CuentaContable cuentaGastoRetencion = null;
/*  387: 465 */     FormaPago formaPago = null;
/*  388:     */     
/*  389: 467 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(conciliacion
/*  390: 468 */       .getCuentaBancariaOrganizacion().getId());
/*  391: 469 */     this.cuentaBanco = cuentaBancariaOrganizacion.getCuentaContableBanco();
/*  392: 472 */     for (Object localObject = cuentaBancariaOrganizacion.getListaFormaPago().iterator(); ((Iterator)localObject).hasNext();)
/*  393:     */     {
/*  394: 472 */       formaPagoCBO = (FormaPagoCuentaBancariaOrganizacion)((Iterator)localObject).next();
/*  395: 473 */       if (formaPagoCBO.isIndicadorDebitoBancario()) {
/*  396: 474 */         formaPago = formaPagoCBO.getFormaPago();
/*  397:     */       }
/*  398:     */     }
/*  399: 478 */     if (formaPago == null) {
/*  400: 479 */       throw new ExcepcionAS2Financiero("msg_info_forma_pago_debito_bancario");
/*  401:     */     }
/*  402: 482 */     localObject = datos;FormaPagoCuentaBancariaOrganizacion formaPagoCBO = localObject.length;
/*  403: 482 */     for (FormaPagoCuentaBancariaOrganizacion localFormaPagoCuentaBancariaOrganizacion1 = 0; localFormaPagoCuentaBancariaOrganizacion1 < formaPagoCBO; localFormaPagoCuentaBancariaOrganizacion1++)
/*  404:     */     {
/*  405: 482 */       HSSFCell[] fila = localObject[localFormaPagoCuentaBancariaOrganizacion1];
/*  406:     */       
/*  407: 484 */       String numeroDocumento = fila[3].getStringCellValue();
/*  408: 485 */       BigDecimal valor = BigDecimal.valueOf(fila[4].getNumericCellValue());
/*  409: 486 */       boolean indicadorGastoRetencion = fila[5].getStringCellValue().equalsIgnoreCase("SI");
/*  410: 488 */       if (indicadorGastoRetencion)
/*  411:     */       {
/*  412: 490 */         String codigoCuentaContable = fila[6].getStringCellValue();
/*  413: 491 */         String descripcion = fila[7].getStringCellValue();
/*  414:     */         try
/*  415:     */         {
/*  416: 495 */           cuentaGastoRetencion = (CuentaContable)hmCuentaContable.get(codigoCuentaContable);
/*  417: 497 */           if (cuentaGastoRetencion == null)
/*  418:     */           {
/*  419: 498 */             cuentaGastoRetencion = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion()
/*  420: 499 */               .getIdOrganizacion());
/*  421: 500 */             hmCuentaContable.put(codigoCuentaContable, cuentaGastoRetencion);
/*  422:     */           }
/*  423: 504 */           DetalleInterfazContable detalleInterfazContableGR = (DetalleInterfazContable)hmDetalleInterfazContableGastoRetencion.get(Integer.valueOf(cuentaGastoRetencion.getId()));
/*  424: 505 */           if (detalleInterfazContableGR == null)
/*  425:     */           {
/*  426: 507 */             detalleInterfazContableGR = new DetalleInterfazContable();
/*  427: 508 */             detalleInterfazContableGR.setIdCuentaContable(cuentaGastoRetencion.getId());
/*  428: 509 */             detalleInterfazContableGR.setReferencia1("");
/*  429: 510 */             detalleInterfazContableGR.setReferencia2(descripcion);
/*  430: 511 */             detalleInterfazContableGR.setReferencia3("");
/*  431: 512 */             detalleInterfazContableGR.setReferencia4("");
/*  432: 513 */             detalleInterfazContableGR.setValor(valor);
/*  433: 514 */             hmDetalleInterfazContableGastoRetencion.put(Integer.valueOf(cuentaGastoRetencion.getId()), detalleInterfazContableGR);
/*  434:     */           }
/*  435:     */           else
/*  436:     */           {
/*  437: 517 */             detalleInterfazContableGR.setValor(detalleInterfazContableGR.getValor().add(valor));
/*  438: 518 */             detalleInterfazContableGR.setReferencia1(detalleInterfazContableGR.getReferencia1() + descripcion);
/*  439:     */           }
/*  440: 521 */           DetalleInterfazContable detalleInterfazContable = new DetalleInterfazContable();
/*  441: 522 */           detalleInterfazContable.setIdCuentaContable(this.cuentaBanco.getId());
/*  442:     */           
/*  443: 524 */           detalleInterfazContable.setReferencia1("");
/*  444: 525 */           detalleInterfazContable.setReferencia2(descripcion);
/*  445: 526 */           detalleInterfazContable.setReferencia3(numeroDocumento);
/*  446: 527 */           detalleInterfazContable.setReferencia4("");
/*  447: 528 */           detalleInterfazContable.setIdFormaPago(Integer.valueOf(formaPago.getId()));
/*  448: 529 */           detalleInterfazContable.setValor(valor.negate());
/*  449:     */           
/*  450: 531 */           listaDetalleIC.add(detalleInterfazContable);
/*  451:     */         }
/*  452:     */         catch (IllegalArgumentException e)
/*  453:     */         {
/*  454: 534 */           addErrorMessage("msg_error_formato_incorrecto");
/*  455:     */         }
/*  456:     */       }
/*  457:     */     }
/*  458: 539 */     listaDetalleIC.addAll(hmDetalleInterfazContableGastoRetencion.values());
/*  459: 540 */     if (!listaDetalleIC.isEmpty()) {
/*  460: 541 */       this.servicioConciliacionBancaria.contabilizarGastosYRetenciones(conciliacion, listaDetalleIC);
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public String leerExcel(FileUploadEvent event)
/*  465:     */   {
/*  466: 546 */     Map<String, String> filters = new HashMap();
/*  467: 547 */     filters.put("cuentaBancariaOrganizacion.idCuentaBancariaOrganizacion", 
/*  468: 548 */       String.valueOf(this.conciliacionBancaria.getCuentaBancariaOrganizacion().getId()));
/*  469: 549 */     filters.put("activo", "true");
/*  470: 550 */     List<ConfiguracionConciliacionBancaria> listaCCB = this.servicioConfiguracionConciliacionBancaria.obtenerListaCombo(ConfiguracionConciliacionBancaria.class, "idOrganizacion", true, filters);
/*  471: 552 */     if ((listaCCB != null) && (!listaCCB.isEmpty())) {
/*  472: 553 */       return leerExelPorBanco(event, (ConfiguracionConciliacionBancaria)listaCCB.get(0));
/*  473:     */     }
/*  474: 555 */     return leerExelAS2(event);
/*  475:     */   }
/*  476:     */   
/*  477:     */   private String leerExelPorBanco(FileUploadEvent event, ConfiguracionConciliacionBancaria ccb)
/*  478:     */   {
/*  479:     */     try
/*  480:     */     {
/*  481: 561 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  482: 562 */       int filaInicial = ccb.getFilaInicial().intValue();
/*  483: 563 */       int columnaMonto = ccb.getColumnaMonto().intValue() - 1;
/*  484: 564 */       int columnaDocumento = ccb.getColumnaDocumento().intValue() - 1;
/*  485:     */       
/*  486: 566 */       cargarDatosAConciliar();
/*  487:     */       
/*  488:     */ 
/*  489: 569 */       Map<String, MovimientoBancario> hmMovimientoBancario = new HashMap();
/*  490: 570 */       for (Iterator localIterator = this.conciliacionBancaria.getListaMovimientoBancario().iterator(); localIterator.hasNext();)
/*  491:     */       {
/*  492: 570 */         movimientoBancario = (MovimientoBancario)localIterator.next();
/*  493: 571 */         hmMovimientoBancario.put(movimientoBancario.getDocumentoReferencia().replaceFirst("^0*", ""), movimientoBancario);
/*  494:     */       }
/*  495:     */       MovimientoBancario movimientoBancario;
/*  496: 573 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelSinEncabezado(input, filaInicial, 0);
/*  497: 574 */       for (HSSFCell[] fila : datos) {
/*  498: 575 */         if ((fila[columnaMonto] != null) && (fila[columnaDocumento] != null))
/*  499:     */         {
/*  500: 578 */           BigDecimal valor = new BigDecimal(-1);
/*  501: 579 */           if (fila[columnaMonto].getCellType() == 0)
/*  502:     */           {
/*  503: 580 */             valor = BigDecimal.valueOf(fila[columnaMonto].getNumericCellValue()).abs();
/*  504:     */           }
/*  505: 581 */           else if (fila[columnaMonto].getCellType() == 1)
/*  506:     */           {
/*  507: 582 */             String strvalor = fila[columnaMonto].getStringCellValue().trim();
/*  508: 583 */             strvalor = strvalor.replace("$", "").replaceAll(",", "").replaceAll("-", "");
/*  509: 584 */             valor = new BigDecimal(strvalor).setScale(2, 4);
/*  510:     */           }
/*  511: 586 */           fila[columnaDocumento].setCellType(1);
/*  512: 587 */           String documentoReferencia = fila[columnaDocumento].getStringCellValue().replaceFirst("^0*", "").replaceAll("'", "");
/*  513: 588 */           MovimientoBancario movimientoBancarioAConciliar = (MovimientoBancario)hmMovimientoBancario.get(documentoReferencia);
/*  514: 589 */           if ((movimientoBancarioAConciliar != null) && (valor.compareTo(movimientoBancarioAConciliar.getValor().abs()) == 0)) {
/*  515: 590 */             movimientoBancarioAConciliar.setConciliado(true);
/*  516:     */           }
/*  517:     */         }
/*  518:     */       }
/*  519: 593 */       calcular();
/*  520:     */     }
/*  521:     */     catch (IOException e)
/*  522:     */     {
/*  523: 595 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  524: 596 */       LOG.error("ERROR AL CARGAR EXCEL CONCILIACION BANCARIA POR BANCO", e);
/*  525: 597 */       e.printStackTrace();
/*  526:     */     }
/*  527: 600 */     return "";
/*  528:     */   }
/*  529:     */   
/*  530:     */   private String leerExelAS2(FileUploadEvent event)
/*  531:     */   {
/*  532:     */     try
/*  533:     */     {
/*  534: 605 */       String numeroCuentaEmpresa = this.conciliacionBancaria.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero();
/*  535:     */       
/*  536: 607 */       String fileName = numeroCuentaEmpresa + event.getFile().getFileName();
/*  537: 608 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  538: 609 */       int numeroFila = 5;
/*  539:     */       
/*  540: 611 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, input, numeroFila, 0);
/*  541:     */       
/*  542: 613 */       String numeroCuenta = datos[0][0].getStringCellValue();
/*  543: 617 */       if (!numeroCuenta.equals(numeroCuentaEmpresa))
/*  544:     */       {
/*  545: 618 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cuenta_bancaria_erronea"));
/*  546: 619 */         return "";
/*  547:     */       }
/*  548: 623 */       contabilizarGastosYRetenciones(this.conciliacionBancaria, datos);
/*  549:     */       
/*  550: 625 */       cargarDatosAConciliar();
/*  551:     */       
/*  552:     */ 
/*  553: 628 */       Map<String, MovimientoBancario> hmMovimientoBancario = new HashMap();
/*  554: 629 */       for (Object localObject = this.conciliacionBancaria.getListaMovimientoBancario().iterator(); ((Iterator)localObject).hasNext();)
/*  555:     */       {
/*  556: 629 */         movimientoBancario = (MovimientoBancario)((Iterator)localObject).next();
/*  557: 630 */         hmMovimientoBancario.put(movimientoBancario.getDocumentoReferencia().replaceFirst("^0*", ""), movimientoBancario);
/*  558:     */       }
/*  559: 633 */       localObject = datos;MovimientoBancario movimientoBancario = localObject.length;
/*  560: 633 */       for (MovimientoBancario localMovimientoBancario1 = 0; localMovimientoBancario1 < movimientoBancario; localMovimientoBancario1++)
/*  561:     */       {
/*  562: 633 */         HSSFCell[] fila = localObject[localMovimientoBancario1];
/*  563: 634 */         BigDecimal valor = BigDecimal.valueOf(fila[4].getNumericCellValue()).abs();
/*  564: 635 */         String documentoReferencia = fila[3].getStringCellValue().replaceFirst("^0*", "");
/*  565:     */         
/*  566: 637 */         MovimientoBancario movimientoBancarioAConciliar = (MovimientoBancario)hmMovimientoBancario.get(documentoReferencia);
/*  567: 638 */         System.out.println("antes de la comparacion     - - - - -- - - - " + movimientoBancarioAConciliar);
/*  568: 639 */         if ((movimientoBancarioAConciliar != null) && (valor.compareTo(movimientoBancarioAConciliar.getValor().abs()) == 0))
/*  569:     */         {
/*  570: 640 */           System.out.println("entro en la comparacion exitosa");
/*  571: 641 */           movimientoBancarioAConciliar.setConciliado(true);
/*  572:     */         }
/*  573:     */       }
/*  574: 644 */       calcular();
/*  575:     */     }
/*  576:     */     catch (ExcepcionAS2Financiero e)
/*  577:     */     {
/*  578: 646 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  579:     */     }
/*  580:     */     catch (ExcepcionAS2 e)
/*  581:     */     {
/*  582: 649 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  583:     */     }
/*  584:     */     catch (Exception e)
/*  585:     */     {
/*  586: 652 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  587: 653 */       e.printStackTrace();
/*  588: 654 */       LOG.error("ERROR AL HACER LA CONCILIACION AUTOMATICA", e);
/*  589:     */     }
/*  590: 657 */     return "";
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void cargarMovimientoBancarioNoRegistrado()
/*  594:     */   {
/*  595: 661 */     getConciliacionBancaria().getListaMovimientoBancario().add(this.movimientoBancarioNoRegistrado);
/*  596: 662 */     setIndicadorRender(false);
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void agregarMovimientoBancarioNoRegistrado()
/*  600:     */   {
/*  601: 666 */     this.movimientoBancarioNoRegistrado = new MovimientoBancario();
/*  602: 667 */     this.movimientoBancarioNoRegistrado.setEstado(Estado.PROCESADO);
/*  603: 668 */     this.movimientoBancarioNoRegistrado.setCuentaBancariaOrganizacion(this.conciliacionBancaria.getCuentaBancariaOrganizacion());
/*  604: 669 */     this.movimientoBancarioNoRegistrado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  605: 670 */     this.movimientoBancarioNoRegistrado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  606: 671 */     this.movimientoBancarioNoRegistrado.setValor(BigDecimal.ZERO);
/*  607: 672 */     this.movimientoBancarioNoRegistrado.setValorPagado(BigDecimal.ZERO);
/*  608: 673 */     this.movimientoBancarioNoRegistrado.setValorCobrado(BigDecimal.ZERO);
/*  609: 674 */     this.movimientoBancarioNoRegistrado.setFecha(new Date());
/*  610: 675 */     this.movimientoBancarioNoRegistrado.setDocumento(new Documento());
/*  611: 676 */     this.movimientoBancarioNoRegistrado.setFormaPago((FormaPago)this.servicioFormaPago.obtenerListaCombo("codigo", true, null).get(0));
/*  612: 677 */     if (!getListaDocumento().isEmpty()) {
/*  613: 678 */       this.movimientoBancarioNoRegistrado.setDocumento((Documento)getListaDocumento().get(0));
/*  614:     */     }
/*  615: 680 */     setIndicadorRender(true);
/*  616:     */     
/*  617: 682 */     RequestContext rctx = RequestContext.getCurrentInstance();
/*  618: 683 */     rctx.update("tvConciliacionBancaria:panelMovimientoBancario");
/*  619: 684 */     rctx.execute("movimientoBancarioDialog.show();");
/*  620:     */   }
/*  621:     */   
/*  622:     */   public void eliminarMovimientoBancarioNoRegistrado()
/*  623:     */   {
/*  624: 689 */     MovimientoBancario movimientoBancario = (MovimientoBancario)this.dtDetalleDocumentosNoRegistradosEnLibros.getRowData();
/*  625: 690 */     movimientoBancario.setEliminado(true);
/*  626:     */   }
/*  627:     */   
/*  628:     */   public ConciliacionBancaria getConciliacionBancaria()
/*  629:     */   {
/*  630: 695 */     return this.conciliacionBancaria;
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void setConciliacionBancaria(ConciliacionBancaria conciliacionBancaria)
/*  634:     */   {
/*  635: 699 */     this.conciliacionBancaria = conciliacionBancaria;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public LazyDataModel<ConciliacionBancaria> getListaConciliacionBancaria()
/*  639:     */   {
/*  640: 703 */     return this.listaConciliacionBancaria;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public void setListaConciliacionBancaria(LazyDataModel<ConciliacionBancaria> listaConciliacionBancaria)
/*  644:     */   {
/*  645: 707 */     this.listaConciliacionBancaria = listaConciliacionBancaria;
/*  646:     */   }
/*  647:     */   
/*  648:     */   public DataTable getDtConciliacionBancaria()
/*  649:     */   {
/*  650: 711 */     return this.dtConciliacionBancaria;
/*  651:     */   }
/*  652:     */   
/*  653:     */   public void setDtConciliacionBancaria(DataTable dtConciliacionBancaria)
/*  654:     */   {
/*  655: 715 */     this.dtConciliacionBancaria = dtConciliacionBancaria;
/*  656:     */   }
/*  657:     */   
/*  658:     */   public ServicioConciliacionBancaria getServicioConciliacionBancaria()
/*  659:     */   {
/*  660: 719 */     return this.servicioConciliacionBancaria;
/*  661:     */   }
/*  662:     */   
/*  663:     */   public List<MovimientoBancario> getListaDetalleConciliacionBancaria()
/*  664:     */   {
/*  665: 728 */     if (this.listaDetalleConciliacionBancaria == null)
/*  666:     */     {
/*  667: 729 */       this.listaDetalleConciliacionBancaria = new ArrayList();
/*  668: 730 */       for (MovimientoBancario d : getConciliacionBancaria().getListaMovimientoBancario()) {
/*  669: 731 */         if ((d.getDocumento() != null) || (d.getDocumento().getDocumentoBase() == DocumentoBase.MOVIMIENTO_BANCARIO_NO_REGISTRADO_EN_LIBROS)) {
/*  670: 732 */           this.listaDetalleConciliacionBancaria.add(d);
/*  671:     */         }
/*  672:     */       }
/*  673:     */     }
/*  674: 736 */     return this.listaDetalleConciliacionBancaria;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public List<MovimientoBancario> getListaMovimientoBancarioNoRegistrado()
/*  678:     */   {
/*  679: 746 */     this.totalDebe = BigDecimal.ZERO;
/*  680: 747 */     this.totalHaber = BigDecimal.ZERO;
/*  681:     */     
/*  682: 749 */     this.listaMovimientoBancarioNoRegistrado = new ArrayList();
/*  683: 750 */     for (MovimientoBancario d : getConciliacionBancaria().getListaMovimientoBancario()) {
/*  684: 751 */       if ((d.getDocumento() != null) && (d.getDocumento().getDocumentoBase() == DocumentoBase.MOVIMIENTO_BANCARIO_NO_REGISTRADO_EN_LIBROS) && 
/*  685: 752 */         (!d.isEliminado()))
/*  686:     */       {
/*  687: 753 */         if (d.getValor().compareTo(BigDecimal.ZERO) == 1) {
/*  688: 754 */           this.totalDebe = this.totalDebe.add(d.getValor());
/*  689:     */         } else {
/*  690: 756 */           this.totalHaber = this.totalHaber.add(d.getValor());
/*  691:     */         }
/*  692: 758 */         this.listaMovimientoBancarioNoRegistrado.add(d);
/*  693:     */       }
/*  694:     */     }
/*  695: 762 */     return this.listaMovimientoBancarioNoRegistrado;
/*  696:     */   }
/*  697:     */   
/*  698:     */   public DataTable getDtDetalleConciliacionBancaria()
/*  699:     */   {
/*  700: 766 */     return this.dtDetalleConciliacionBancaria;
/*  701:     */   }
/*  702:     */   
/*  703:     */   public void setDtDetalleConciliacionBancaria(DataTable dtDetalleConciliacionBancaria)
/*  704:     */   {
/*  705: 770 */     this.dtDetalleConciliacionBancaria = dtDetalleConciliacionBancaria;
/*  706:     */   }
/*  707:     */   
/*  708:     */   public String cargarDatos()
/*  709:     */   {
/*  710: 781 */     return null;
/*  711:     */   }
/*  712:     */   
/*  713:     */   public BigDecimal getTotalConciliado()
/*  714:     */   {
/*  715: 785 */     return this.totalConciliado;
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void setTotalConciliado(BigDecimal totalConciliado)
/*  719:     */   {
/*  720: 789 */     this.totalConciliado = totalConciliado;
/*  721:     */   }
/*  722:     */   
/*  723:     */   public BigDecimal getTotalDebitosNoConciliados()
/*  724:     */   {
/*  725: 793 */     return this.totalDebitosNoConciliados;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public void setTotalDebitosNoConciliados(BigDecimal totalDebitosNoConciliados)
/*  729:     */   {
/*  730: 797 */     this.totalDebitosNoConciliados = totalDebitosNoConciliados;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public BigDecimal getTotalCreditosNoConciliados()
/*  734:     */   {
/*  735: 801 */     return this.totalCreditosNoConciliados;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public void setTotalCreditosNoConciliados(BigDecimal totalCreditosNoConciliados)
/*  739:     */   {
/*  740: 805 */     this.totalCreditosNoConciliados = totalCreditosNoConciliados;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public BigDecimal getSaldoEstadoCuenta()
/*  744:     */   {
/*  745: 809 */     return this.saldoEstadoCuenta;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public BigDecimal getDiferencia()
/*  749:     */   {
/*  750: 813 */     return this.diferencia;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public BigDecimal getSaldoContable()
/*  754:     */   {
/*  755: 817 */     return this.saldoContable;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setSaldoContable(BigDecimal saldoContable)
/*  759:     */   {
/*  760: 821 */     this.saldoContable = saldoContable;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public Integer getIdCuentaBancariaOrganizacion()
/*  764:     */   {
/*  765: 825 */     return this.idCuentaBancariaOrganizacion;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public BigDecimal getTotalDebe()
/*  769:     */   {
/*  770: 834 */     return this.totalDebe;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setTotalDebe(BigDecimal totalDebe)
/*  774:     */   {
/*  775: 844 */     this.totalDebe = totalDebe;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public BigDecimal getTotalHaber()
/*  779:     */   {
/*  780: 853 */     return this.totalHaber;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public void setTotalHaber(BigDecimal totalHaber)
/*  784:     */   {
/*  785: 863 */     this.totalHaber = totalHaber;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setIdCuentaBancariaOrganizacion(Integer idCuentaBancariaOrganizacion)
/*  789:     */   {
/*  790: 867 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/*  791:     */   }
/*  792:     */   
/*  793:     */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/*  794:     */   {
/*  795: 871 */     if (this.listaCuentaBancariaOrganizacion == null) {
/*  796: 872 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, false, null);
/*  797:     */     }
/*  798: 874 */     return this.listaCuentaBancariaOrganizacion;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/*  802:     */   {
/*  803: 878 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public MovimientoBancario getMovimientoBancarioNoRegistrado()
/*  807:     */   {
/*  808: 887 */     if (this.movimientoBancarioNoRegistrado == null) {
/*  809: 888 */       this.movimientoBancarioNoRegistrado = new MovimientoBancario();
/*  810:     */     }
/*  811: 890 */     return this.movimientoBancarioNoRegistrado;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void setMovimientoBancarioNoRegistrado(MovimientoBancario movimientoBancarioNoRegistrado)
/*  815:     */   {
/*  816: 900 */     this.movimientoBancarioNoRegistrado = movimientoBancarioNoRegistrado;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public List<Documento> getListaDocumento()
/*  820:     */   {
/*  821: 909 */     if (this.listaDocumento == null)
/*  822:     */     {
/*  823: 910 */       Map<String, String> filters = new HashMap();
/*  824: 911 */       filters.put("documentoBase", String.valueOf(DocumentoBase.MOVIMIENTO_BANCARIO_NO_REGISTRADO_EN_LIBROS));
/*  825:     */       
/*  826: 913 */       this.listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filters);
/*  827:     */     }
/*  828: 916 */     return this.listaDocumento;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public BigDecimal getTotalDebitosConciliados()
/*  832:     */   {
/*  833: 920 */     return this.totalDebitosConciliados;
/*  834:     */   }
/*  835:     */   
/*  836:     */   public void setTotalDebitosConciliados(BigDecimal totalDebitosConciliados)
/*  837:     */   {
/*  838: 924 */     this.totalDebitosConciliados = totalDebitosConciliados;
/*  839:     */   }
/*  840:     */   
/*  841:     */   public BigDecimal getTotalCreditosConciliados()
/*  842:     */   {
/*  843: 928 */     return this.totalCreditosConciliados;
/*  844:     */   }
/*  845:     */   
/*  846:     */   public void setTotalCreditosConciliados(BigDecimal totalCreditosConciliados)
/*  847:     */   {
/*  848: 932 */     this.totalCreditosConciliados = totalCreditosConciliados;
/*  849:     */   }
/*  850:     */   
/*  851:     */   public List<MovimientoBancario> getListaDocumentosConciliados()
/*  852:     */   {
/*  853: 936 */     return this.listaDocumentosConciliados;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public List<MovimientoBancario> getListaDocumentosNoConciliados()
/*  857:     */   {
/*  858: 940 */     return this.listaDocumentosNoConciliados;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public DataTable getDtDetalleDocumentosConciliados()
/*  862:     */   {
/*  863: 944 */     return this.dtDetalleDocumentosConciliados;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public void setDtDetalleDocumentosConciliados(DataTable dtDetalleDocumentosConciliados)
/*  867:     */   {
/*  868: 948 */     this.dtDetalleDocumentosConciliados = dtDetalleDocumentosConciliados;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public DataTable getDtDetalleDocumentosNoConciliados()
/*  872:     */   {
/*  873: 952 */     return this.dtDetalleDocumentosNoConciliados;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void setDtDetalleDocumentosNoConciliados(DataTable dtDetalleDocumentosNoConciliados)
/*  877:     */   {
/*  878: 956 */     this.dtDetalleDocumentosNoConciliados = dtDetalleDocumentosNoConciliados;
/*  879:     */   }
/*  880:     */   
/*  881:     */   public boolean isIndicadorRender()
/*  882:     */   {
/*  883: 960 */     return this.indicadorRender;
/*  884:     */   }
/*  885:     */   
/*  886:     */   public void setIndicadorRender(boolean indicadorRender)
/*  887:     */   {
/*  888: 964 */     this.indicadorRender = indicadorRender;
/*  889:     */   }
/*  890:     */   
/*  891:     */   public BigDecimal getTotalCreditosChequesNoConciliados()
/*  892:     */   {
/*  893: 973 */     return this.totalCreditosChequesNoConciliados;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public void setTotalCreditosChequesNoConciliados(BigDecimal totalCreditosChequesNoConciliados)
/*  897:     */   {
/*  898: 983 */     this.totalCreditosChequesNoConciliados = totalCreditosChequesNoConciliados;
/*  899:     */   }
/*  900:     */   
/*  901:     */   public DataTable getDtDetalleDocumentosNoRegistradosEnLibros()
/*  902:     */   {
/*  903: 992 */     return this.dtDetalleDocumentosNoRegistradosEnLibros;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void setDtDetalleDocumentosNoRegistradosEnLibros(DataTable dtDetalleDocumentosNoRegistradosEnLibros)
/*  907:     */   {
/*  908:1002 */     this.dtDetalleDocumentosNoRegistradosEnLibros = dtDetalleDocumentosNoRegistradosEnLibros;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public List<MovimientoBancario> getListaDetalleConciliacionBancariaFilteredValue()
/*  912:     */   {
/*  913:1011 */     return this.listaDetalleConciliacionBancariaFilteredValue;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void setListaDetalleConciliacionBancariaFilteredValue(List<MovimientoBancario> listaDetalleConciliacionBancariaFilteredValue)
/*  917:     */   {
/*  918:1021 */     this.listaDetalleConciliacionBancariaFilteredValue = listaDetalleConciliacionBancariaFilteredValue;
/*  919:     */   }
/*  920:     */   
/*  921:     */   public List<MovimientoBancario> getListaDocumentosConciliadosFilteredValue()
/*  922:     */   {
/*  923:1030 */     return this.listaDocumentosConciliadosFilteredValue;
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void setListaDocumentosConciliadosFilteredValue(List<MovimientoBancario> listaDocumentosConciliadosFilteredValue)
/*  927:     */   {
/*  928:1040 */     this.listaDocumentosConciliadosFilteredValue = listaDocumentosConciliadosFilteredValue;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public List<MovimientoBancario> getListaDocumentosNoConciliadosFilteredValue()
/*  932:     */   {
/*  933:1049 */     return this.listaDocumentosNoConciliadosFilteredValue;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void setListaDocumentosNoConciliadosFilteredValue(List<MovimientoBancario> listaDocumentosNoConciliadosFilteredValue)
/*  937:     */   {
/*  938:1059 */     this.listaDocumentosNoConciliadosFilteredValue = listaDocumentosNoConciliadosFilteredValue;
/*  939:     */   }
/*  940:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.ConciliacionBancariaBean
 * JD-Core Version:    0.7.0.1
 */