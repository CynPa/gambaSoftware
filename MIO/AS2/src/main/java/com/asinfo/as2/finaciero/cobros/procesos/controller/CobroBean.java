/*    1:     */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*    7:     */ import com.asinfo.as2.entities.Banco;
/*    8:     */ import com.asinfo.as2.entities.Cliente;
/*    9:     */ import com.asinfo.as2.entities.Cobro;
/*   10:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   11:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   12:     */ import com.asinfo.as2.entities.DetalleCobro;
/*   13:     */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   14:     */ import com.asinfo.as2.entities.Documento;
/*   15:     */ import com.asinfo.as2.entities.Empresa;
/*   16:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   17:     */ import com.asinfo.as2.entities.FormaPago;
/*   18:     */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   19:     */ import com.asinfo.as2.entities.GarantiaCliente;
/*   20:     */ import com.asinfo.as2.entities.Organizacion;
/*   21:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   22:     */ import com.asinfo.as2.entities.Recaudador;
/*   23:     */ import com.asinfo.as2.entities.Secuencia;
/*   24:     */ import com.asinfo.as2.entities.Sucursal;
/*   25:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   26:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   27:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   28:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   29:     */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*   30:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   31:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   32:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   33:     */ import com.asinfo.as2.finaciero.contabilidad.procesos.controller.CuentaBancariaOrganizacionBean;
/*   34:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI;
/*   35:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*   36:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*   37:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   38:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente;
/*   39:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   40:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   41:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   42:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   43:     */ import com.asinfo.as2.util.AppUtil;
/*   44:     */ import com.asinfo.as2.util.RutaArchivo;
/*   45:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   46:     */ import com.asinfo.as2.utils.JsfUtil;
/*   47:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*   48:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   49:     */ import java.io.BufferedInputStream;
/*   50:     */ import java.io.InputStream;
/*   51:     */ import java.io.StringReader;
/*   52:     */ import java.math.BigDecimal;
/*   53:     */ import java.math.RoundingMode;
/*   54:     */ import java.util.ArrayList;
/*   55:     */ import java.util.Date;
/*   56:     */ import java.util.HashMap;
/*   57:     */ import java.util.List;
/*   58:     */ import java.util.Map;
/*   59:     */ import javax.annotation.PostConstruct;
/*   60:     */ import javax.ejb.EJB;
/*   61:     */ import javax.faces.bean.ManagedBean;
/*   62:     */ import javax.faces.bean.ManagedProperty;
/*   63:     */ import javax.faces.bean.ViewScoped;
/*   64:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   65:     */ import org.apache.log4j.Logger;
/*   66:     */ import org.jdom2.Document;
/*   67:     */ import org.jdom2.Element;
/*   68:     */ import org.jdom2.input.SAXBuilder;
/*   69:     */ import org.primefaces.component.datatable.DataTable;
/*   70:     */ import org.primefaces.event.FileUploadEvent;
/*   71:     */ import org.primefaces.event.SelectEvent;
/*   72:     */ import org.primefaces.model.LazyDataModel;
/*   73:     */ import org.primefaces.model.SortOrder;
/*   74:     */ import org.primefaces.model.UploadedFile;
/*   75:     */ import org.xml.sax.InputSource;
/*   76:     */ 
/*   77:     */ @ManagedBean
/*   78:     */ @ViewScoped
/*   79:     */ public class CobroBean
/*   80:     */   extends VoucherBean
/*   81:     */ {
/*   82:     */   private static final long serialVersionUID = 1425588770056975545L;
/*   83:     */   @EJB
/*   84:     */   private transient ServicioCobro servicioCobro;
/*   85:     */   @EJB
/*   86:     */   private transient ServicioEmpresa servicioEmpresa;
/*   87:     */   @EJB
/*   88:     */   private transient ServicioDocumento servicioDocumento;
/*   89:     */   @EJB
/*   90:     */   private transient ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   91:     */   @EJB
/*   92:     */   private transient ServicioGarantiaCliente servicioGarantiaCliente;
/*   93:     */   @EJB
/*   94:     */   private transient ServicioGenerico<Banco> servicioBanco;
/*   95:     */   @EJB
/*   96:     */   private transient ServicioRecaudador servicioRecaudador;
/*   97:     */   @EJB
/*   98:     */   private transient ServicioAnticipoCliente servicioAnticipoCliente;
/*   99:     */   @EJB
/*  100:     */   private transient ServicioConceptoRetencionSRI servicioConceptoRetencionSRI;
/*  101:     */   @EJB
/*  102:     */   private transient ServicioFormaPago servicioFormaPago;
/*  103:     */   @EJB
/*  104:     */   private transient ServicioUsuario servicioUsuario;
/*  105:     */   private Cobro cobro;
/*  106:     */   private DetalleFormaCobro detalleFormaCobroSeleccionado;
/*  107:     */   private LazyDataModel<Cobro> listaCobro;
/*  108: 123 */   private String numeroFactura = "";
/*  109:     */   private List<DetalleFormaCobro> listaDetalleFormaCobroProtesto;
/*  110:     */   private List<Empresa> listaEmpresa;
/*  111:     */   private List<Documento> listaDocumento;
/*  112:     */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  113:     */   private List<GarantiaCliente> listaChequesPosfechados;
/*  114:     */   private List<Banco> listaBanco;
/*  115:     */   private List<Recaudador> listaRecaudador;
/*  116: 133 */   private BigDecimal totalLiquidado = BigDecimal.ZERO;
/*  117:     */   private BigDecimal totalLiquidarFormaCobro;
/*  118:     */   private Date fechaProtesto;
/*  119:     */   private boolean indicadorRenderPanelEdicionFormaCobro;
/*  120:     */   private TipoOrganizacion tipoOrganizacion;
/*  121:     */   private DataTable dtCobro;
/*  122:     */   private DataTable dtFormaCobro;
/*  123:     */   private DataTable dtEdicionFormaCobro;
/*  124:     */   private DataTable dtChequesPosfechados;
/*  125:     */   private DataTable dtGarantiaCliente;
/*  126:     */   private DataTable dtDetalleFormaCobroProtesto;
/*  127:     */   private DataTable dtDetalleCobro;
/*  128:     */   private Integer idCobro;
/*  129:     */   private String numero;
/*  130:     */   @ManagedProperty("#{cuentaBancariaOrganizacionBean}")
/*  131:     */   private CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean;
/*  132:     */   
/*  133:     */   @PostConstruct
/*  134:     */   public void init()
/*  135:     */   {
/*  136: 162 */     limpiar();
/*  137:     */     
/*  138: 164 */     this.listaCobro = new LazyDataModel()
/*  139:     */     {
/*  140:     */       private static final long serialVersionUID = 1L;
/*  141:     */       
/*  142:     */       public List<Cobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  143:     */       {
/*  144: 171 */         List<Cobro> lista = new ArrayList();
/*  145:     */         
/*  146: 173 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  147: 175 */         if (CobroBean.this.idCobro != null)
/*  148:     */         {
/*  149: 176 */           filters.put("idCobro", CobroBean.this.idCobro.toString());
/*  150: 177 */           CobroBean.this.idCobro = null;
/*  151:     */         }
/*  152: 180 */         if (CobroBean.this.numero != null) {
/*  153: 181 */           filters.put("numero", CobroBean.this.numero);
/*  154:     */         }
/*  155: 185 */         filters.put("documento.documentoBase", DocumentoBase.COBRO_CLIENTE.toString());
/*  156: 186 */         lista = CobroBean.this.servicioCobro.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  157:     */         
/*  158: 188 */         CobroBean.this.listaCobro.setRowCount(CobroBean.this.servicioCobro.contarPorCriterio(filters));
/*  159:     */         
/*  160: 190 */         return lista;
/*  161:     */       }
/*  162:     */     };
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void cargarDatosCobro()
/*  166:     */   {
/*  167: 196 */     this.cobro = ((Cobro)this.dtCobro.getRowData());
/*  168: 197 */     this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getIdCobro());
/*  169: 198 */     for (DetalleFormaCobro detalleFormaCobro : this.cobro.getListaDetalleFormaCobro())
/*  170:     */     {
/*  171: 199 */       setDetalleFormaCobroSeleccionado(detalleFormaCobro);
/*  172: 200 */       cargarPlanTarjetaListener(detalleFormaCobro);
/*  173:     */     }
/*  174: 202 */     setIndicadorRenderPanelEdicionFormaCobro(true);
/*  175:     */   }
/*  176:     */   
/*  177:     */   public String guardarFormaCobro()
/*  178:     */   {
/*  179:     */     try
/*  180:     */     {
/*  181: 207 */       this.cobro.setIndicadorTieneCheques(true);
/*  182: 208 */       this.servicioCobro.guardarFormaCobro(this.cobro);
/*  183: 209 */       this.idCobro = Integer.valueOf(this.cobro.getIdCobro());
/*  184: 210 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  185: 211 */       limpiar();
/*  186:     */       
/*  187: 213 */       return "cobro.xhtml?faces-redirect=true&idCobro=" + this.idCobro;
/*  188:     */     }
/*  189:     */     catch (ExcepcionAS2Financiero e)
/*  190:     */     {
/*  191: 216 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  192:     */     }
/*  193:     */     catch (ExcepcionAS2 e)
/*  194:     */     {
/*  195: 218 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  196:     */     }
/*  197:     */     catch (AS2Exception e)
/*  198:     */     {
/*  199: 220 */       JsfUtil.addErrorMessage(e, "");
/*  200:     */     }
/*  201:     */     catch (Exception e)
/*  202:     */     {
/*  203: 222 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  204: 223 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  205:     */     }
/*  206: 225 */     return "";
/*  207:     */   }
/*  208:     */   
/*  209:     */   public String editar()
/*  210:     */   {
/*  211: 236 */     if ((getCobro() != null) && (getCobro().getId() > 0)) {
/*  212:     */       try
/*  213:     */       {
/*  214: 240 */         this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getId());
/*  215: 241 */         this.servicioCobro.esEditable(this.cobro);
/*  216: 244 */         for (DetalleCobro detalleCobro : this.cobro.getListaDetalleCobro())
/*  217:     */         {
/*  218: 245 */           BigDecimal valorCobro = detalleCobro.getValor();
/*  219: 246 */           detalleCobro.getCuentaPorCobrar().setSaldo(valorCobro.add(detalleCobro.getCuentaPorCobrar().getSaldo()));
/*  220:     */         }
/*  221: 249 */         for (DetalleFormaCobro detalleFormaCobro : this.cobro.getListaDetalleFormaCobro()) {
/*  222: 250 */           cargarPlanTarjetaListener(detalleFormaCobro);
/*  223:     */         }
/*  224: 253 */         setEditado(true);
/*  225:     */       }
/*  226:     */       catch (ExcepcionAS2Financiero e)
/*  227:     */       {
/*  228: 256 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  229: 257 */         LOG.info("ERROR AL EDITAR COBRO CLIENTE");
/*  230: 258 */         e.printStackTrace();
/*  231:     */       }
/*  232:     */     } else {
/*  233: 262 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  234:     */     }
/*  235: 265 */     return "";
/*  236:     */   }
/*  237:     */   
/*  238:     */   public String guardar()
/*  239:     */   {
/*  240:     */     try
/*  241:     */     {
/*  242: 280 */       if (getCobro().isIndicadorExcede()) {
/*  243: 281 */         throw new ExcepcionAS2Financiero("msg_info_valor_posfechado_excede_saldo");
/*  244:     */       }
/*  245: 283 */       this.servicioCobro.guardar(this.cobro);
/*  246: 284 */       this.idCobro = Integer.valueOf(this.cobro.getIdCobro());
/*  247:     */       
/*  248: 286 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  249: 287 */       limpiar();
/*  250:     */       
/*  251: 289 */       return "cobro.xhtml?faces-redirect=true&idCobro=" + this.idCobro;
/*  252:     */     }
/*  253:     */     catch (ExcepcionAS2Financiero e)
/*  254:     */     {
/*  255: 293 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  256: 294 */       e.printStackTrace();
/*  257:     */     }
/*  258:     */     catch (ExcepcionAS2 e)
/*  259:     */     {
/*  260: 296 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  261: 297 */       e.printStackTrace();
/*  262:     */     }
/*  263:     */     catch (AS2Exception e)
/*  264:     */     {
/*  265: 299 */       JsfUtil.addErrorMessage(e, "");
/*  266:     */     }
/*  267:     */     catch (Exception e)
/*  268:     */     {
/*  269: 301 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  270: 302 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  271: 303 */       e.printStackTrace();
/*  272:     */     }
/*  273: 305 */     return "";
/*  274:     */   }
/*  275:     */   
/*  276:     */   public String eliminar()
/*  277:     */   {
/*  278: 315 */     anularCobro();
/*  279: 316 */     return "";
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String limpiar()
/*  283:     */   {
/*  284: 326 */     setEditado(false);
/*  285: 327 */     setIndicadorRenderPanelEdicionFormaCobro(false);
/*  286: 328 */     setNumeroFactura("");
/*  287: 329 */     crearCobro();
/*  288: 330 */     return "";
/*  289:     */   }
/*  290:     */   
/*  291:     */   public String cargarDatos()
/*  292:     */   {
/*  293: 340 */     return "";
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void crearCobro()
/*  297:     */   {
/*  298: 349 */     this.cobro = new Cobro();
/*  299: 350 */     this.cobro.setValor(BigDecimal.ZERO);
/*  300: 351 */     this.cobro.setFecha(new Date());
/*  301: 352 */     this.cobro.setEmpresa(new Empresa());
/*  302: 353 */     this.cobro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  303: 354 */     this.cobro.setSucursal(AppUtil.getSucursal());
/*  304:     */     
/*  305: 356 */     Documento documento = null;
/*  306: 357 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/*  307:     */     {
/*  308: 358 */       documento = (Documento)getListaDocumento().get(0);
/*  309: 359 */       this.cobro.setDocumento(documento);
/*  310: 360 */       actualizarDocumento();
/*  311:     */     }
/*  312:     */     else
/*  313:     */     {
/*  314: 362 */       documento = new Documento();
/*  315: 363 */       documento.setSecuencia(new Secuencia());
/*  316: 364 */       this.cobro.setDocumento(documento);
/*  317:     */     }
/*  318: 367 */     this.cobro.setEstado(Estado.ELABORADO);
/*  319: 368 */     crearFormaCobro();
/*  320:     */     
/*  321: 370 */     setDetalleFormaCobroSeleccionado(null);
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void cargarFacturasPendientes()
/*  325:     */   {
/*  326: 380 */     if (this.cobro.getEmpresa().getId() == 0)
/*  327:     */     {
/*  328: 381 */       addErrorMessage(getLanguageController().getMensaje("msg_info_cobro_0003"));
/*  329:     */     }
/*  330:     */     else
/*  331:     */     {
/*  332: 383 */       for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/*  333: 384 */         if (!detalleFormaCobro.isEliminado()) {
/*  334: 385 */           detalleFormaCobro.setValor(this.cobro.getValor());
/*  335:     */         }
/*  336:     */       }
/*  337: 388 */       this.servicioCobro.cargarFacturasPendientes(this.cobro, this.numeroFactura);
/*  338:     */     }
/*  339:     */   }
/*  340:     */   
/*  341:     */   public String actualizarDocumento()
/*  342:     */   {
/*  343: 399 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getCobro().getDocumento().getIdDocumento()));
/*  344: 400 */     getCobro().setDocumento(documento);
/*  345:     */     
/*  346: 402 */     setSecuenciaEditable(!this.cobro.getDocumento().isIndicadorBloqueoSecuencia());
/*  347:     */     
/*  348: 404 */     return "";
/*  349:     */   }
/*  350:     */   
/*  351:     */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/*  352:     */   {
/*  353:     */     DetalleFormaCobro detalleFormaCobro;
/*  354:     */     DetalleFormaCobro detalleFormaCobro;
/*  355: 420 */     if (this.indicadorRenderPanelEdicionFormaCobro) {
/*  356: 421 */       detalleFormaCobro = (DetalleFormaCobro)this.dtEdicionFormaCobro.getRowData();
/*  357:     */     } else {
/*  358: 423 */       detalleFormaCobro = (DetalleFormaCobro)this.dtFormaCobro.getRowData();
/*  359:     */     }
/*  360: 426 */     detalleFormaCobro.setCuentaContableFormaCobro(detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/*  361: 427 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaFormaPago(this.servicioCuentaBancariaOrganizacion
/*  362: 428 */       .cargarDetalle(detalleFormaCobro.getCuentaBancariaOrganizacion().getId()).getListaFormaPago());
/*  363: 429 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaCuentaContableCruceCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/*  364: 430 */       .cargarDetalle(detalleFormaCobro.getCuentaBancariaOrganizacion().getId())
/*  365: 431 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion());
/*  366: 432 */     List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago = new ArrayList();
/*  367: 433 */     for (FormaPagoCuentaBancariaOrganizacion f : detalleFormaCobro.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/*  368: 434 */       if (f.isIndicadorCliente()) {
/*  369: 435 */         listaFormaPago.add(f);
/*  370:     */       }
/*  371:     */     }
/*  372: 438 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaFormaPago(listaFormaPago);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void actualizarFormaPago()
/*  376:     */   {
/*  377: 444 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtFormaCobro.getRowData();
/*  378: 445 */     if ((detalleFormaCobro.getFormaPago().isIndicadorRetencionFuente()) || (detalleFormaCobro.getFormaPago().isIndicadorRetencionIva()))
/*  379:     */     {
/*  380: 446 */       BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/*  381: 447 */       BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  382: 448 */       BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  383: 449 */       BigDecimal montoIva = BigDecimal.ZERO;
/*  384: 451 */       for (DetalleCobro detalleCobro : getListaDetalleCobro()) {
/*  385: 452 */         if (detalleCobro.getValor().compareTo(BigDecimal.ZERO) != 0)
/*  386:     */         {
/*  387: 453 */           FacturaCliente facturaCliente = detalleCobro.getCuentaPorCobrar().getFacturaCliente();
/*  388: 454 */           if (facturaCliente.getFacturaClienteSRI() != null)
/*  389:     */           {
/*  390: 455 */             baseImponibleTarifaCero = baseImponibleTarifaCero.add(facturaCliente.getFacturaClienteSRI().getBaseImponibleTarifaCero());
/*  391: 456 */             baseImponibleDiferenteCero = baseImponibleDiferenteCero.add(facturaCliente.getFacturaClienteSRI()
/*  392: 457 */               .getBaseImponibleDiferenteCero());
/*  393: 458 */             baseImponibleNoObjetoIva = baseImponibleNoObjetoIva.add(facturaCliente.getFacturaClienteSRI().getBaseImponibleNoObjetoIva());
/*  394: 459 */             montoIva = montoIva.add(facturaCliente.getFacturaClienteSRI().getMontoIva());
/*  395:     */           }
/*  396:     */         }
/*  397:     */       }
/*  398: 464 */       if (detalleFormaCobro.getFormaPago().isIndicadorRetencionFuente()) {
/*  399: 466 */         detalleFormaCobro.setValor(baseImponibleDiferenteCero.add(baseImponibleTarifaCero).add(baseImponibleNoObjetoIva)
/*  400: 467 */           .multiply(detalleFormaCobro.getFormaPago().getPorcentajeRetencion())
/*  401: 468 */           .divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP));
/*  402:     */       } else {
/*  403: 470 */         detalleFormaCobro.setValor(montoIva.multiply(detalleFormaCobro.getFormaPago().getPorcentajeRetencion()).divide(
/*  404: 471 */           BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP));
/*  405:     */       }
/*  406:     */     }
/*  407:     */   }
/*  408:     */   
/*  409:     */   public void actualizarCuentaBancariaProtesto(AjaxBehaviorEvent event)
/*  410:     */   {
/*  411: 477 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtDetalleFormaCobroProtesto.getRowData();
/*  412: 478 */     CuentaBancariaOrganizacion cuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.cargarDetalle(detalleFormaCobro
/*  413: 479 */       .getCuentaBancariaOrganizacionProtesto().getId());
/*  414: 480 */     detalleFormaCobro.getCuentaBancariaOrganizacionProtesto().setListaFormaPago(new ArrayList());
/*  415: 482 */     for (FormaPagoCuentaBancariaOrganizacion formaPagoCuentaBancariaOrganizacion : cuentaBancariaOrganizacion.getListaFormaPago()) {
/*  416: 483 */       if (formaPagoCuentaBancariaOrganizacion.isIndicadorDebitoBancario()) {
/*  417: 484 */         detalleFormaCobro.getCuentaBancariaOrganizacionProtesto().getListaFormaPago().add(formaPagoCuentaBancariaOrganizacion);
/*  418:     */       }
/*  419:     */     }
/*  420:     */   }
/*  421:     */   
/*  422:     */   public void crearFormaCobro()
/*  423:     */   {
/*  424: 497 */     DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/*  425: 498 */     detalleFormaCobro.setCaja(AppUtil.getCaja());
/*  426: 499 */     detalleFormaCobro.setCobro(getCobro());
/*  427: 500 */     detalleFormaCobro.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/*  428: 501 */     detalleFormaCobro.setFormaPago(new FormaPago());
/*  429: 502 */     detalleFormaCobro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  430: 503 */     detalleFormaCobro.setIdSucursal(AppUtil.getSucursal().getId());
/*  431: 504 */     detalleFormaCobro.setValor(BigDecimal.ZERO);
/*  432:     */     
/*  433: 506 */     getCobro().getListaDetalleFormaCobro().add(detalleFormaCobro);
/*  434:     */   }
/*  435:     */   
/*  436:     */   public String eliminarFormaCobro()
/*  437:     */   {
/*  438: 517 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtFormaCobro.getRowData();
/*  439: 518 */     detalleFormaCobro.setEliminado(true);
/*  440: 519 */     return "";
/*  441:     */   }
/*  442:     */   
/*  443:     */   public String actualizarFechaCobro()
/*  444:     */   {
/*  445: 528 */     Date fechaIngreso = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getFechaIngreso();
/*  446: 529 */     int dias = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getDiasCreditoOtorgado();
/*  447: 530 */     fechaIngreso = FuncionesUtiles.sumarFechaDiasMeses(fechaIngreso, dias);
/*  448: 531 */     this.detalleFormaCobroSeleccionado.getGarantiaCliente().setFechaCobro(fechaIngreso);
/*  449:     */     
/*  450: 533 */     return "";
/*  451:     */   }
/*  452:     */   
/*  453:     */   public void actualizarDiasCreditoListener()
/*  454:     */   {
/*  455: 537 */     actualizarDiasCredito(this.detalleFormaCobroSeleccionado.getGarantiaCliente().getFechaCobro());
/*  456:     */   }
/*  457:     */   
/*  458:     */   public void actualizarDiasCredito(Date fechaCobro)
/*  459:     */   {
/*  460: 541 */     this.detalleFormaCobroSeleccionado.getGarantiaCliente().setFechaCobro(fechaCobro);
/*  461: 542 */     Date fechaIngreso = this.detalleFormaCobroSeleccionado.getGarantiaCliente().getFechaIngreso();
/*  462: 543 */     int diasCredito = FuncionesUtiles.diferenciasDeFechas(fechaIngreso, fechaCobro);
/*  463: 544 */     this.detalleFormaCobroSeleccionado.getGarantiaCliente().setDiasCreditoOtorgado(diasCredito);
/*  464:     */   }
/*  465:     */   
/*  466:     */   public String cargarListaGarantiaCliente()
/*  467:     */   {
/*  468: 554 */     this.cobro = ((Cobro)this.dtCobro.getRowData());
/*  469: 555 */     this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getId());
/*  470: 556 */     this.listaDetalleFormaCobroProtesto = new ArrayList();
/*  471: 557 */     for (DetalleFormaCobro detalleFormaCobro : this.cobro.getListaDetalleFormaCobro()) {
/*  472: 558 */       if ((detalleFormaCobro.getFormaPago().isIndicadorCheque()) && (!detalleFormaCobro.isIndicadorChequeProtestado()))
/*  473:     */       {
/*  474: 559 */         detalleFormaCobro.setValorProtestado(detalleFormaCobro.getCuentaBancariaOrganizacion().getValorProtesto());
/*  475: 560 */         this.listaDetalleFormaCobroProtesto.add(detalleFormaCobro);
/*  476:     */       }
/*  477:     */     }
/*  478: 564 */     this.fechaProtesto = new Date();
/*  479: 565 */     return "";
/*  480:     */   }
/*  481:     */   
/*  482:     */   public String cargarListaGarantiaClienteAnularProtesto()
/*  483:     */   {
/*  484: 574 */     this.cobro = ((Cobro)this.dtCobro.getRowData());
/*  485: 575 */     this.cobro = this.servicioCobro.cargarDetalle(this.cobro.getId());
/*  486: 576 */     this.listaDetalleFormaCobroProtesto = new ArrayList();
/*  487: 577 */     for (DetalleFormaCobro detalleFormaCobro : this.cobro.getListaDetalleFormaCobro()) {
/*  488: 578 */       if ((detalleFormaCobro.getFormaPago().isIndicadorCheque()) && (detalleFormaCobro.isIndicadorChequeProtestado())) {
/*  489: 579 */         this.listaDetalleFormaCobroProtesto.add(detalleFormaCobro);
/*  490:     */       }
/*  491:     */     }
/*  492: 583 */     this.fechaProtesto = new Date();
/*  493: 584 */     return "";
/*  494:     */   }
/*  495:     */   
/*  496:     */   public String procesarProtesto()
/*  497:     */   {
/*  498:     */     try
/*  499:     */     {
/*  500: 594 */       this.servicioCobro.procesarProtesto(this.cobro, this.listaDetalleFormaCobroProtesto, this.fechaProtesto);
/*  501: 595 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  502:     */     }
/*  503:     */     catch (ExcepcionAS2Financiero e)
/*  504:     */     {
/*  505: 597 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  506: 598 */       LOG.info("Error al procesar un cheque protestado", e);
/*  507:     */     }
/*  508:     */     catch (ExcepcionAS2 e)
/*  509:     */     {
/*  510: 600 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  511: 601 */       LOG.info("Error al procesar un cheque protestado", e);
/*  512:     */     }
/*  513:     */     catch (AS2Exception e)
/*  514:     */     {
/*  515: 603 */       JsfUtil.addErrorMessage(e, "");
/*  516:     */     }
/*  517:     */     catch (Exception e)
/*  518:     */     {
/*  519: 605 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  520: 606 */       e.printStackTrace();
/*  521: 607 */       LOG.error("Error al procesar un cheque protestado", e);
/*  522:     */     }
/*  523: 609 */     return "";
/*  524:     */   }
/*  525:     */   
/*  526:     */   public String anularProtesto()
/*  527:     */   {
/*  528:     */     try
/*  529:     */     {
/*  530: 619 */       this.servicioCobro.anularProtesto(this.cobro);
/*  531: 620 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  532:     */     }
/*  533:     */     catch (ExcepcionAS2Financiero e)
/*  534:     */     {
/*  535: 622 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  536: 623 */       LOG.info("Error al anular un cheque protestado", e);
/*  537:     */     }
/*  538:     */     catch (Exception e)
/*  539:     */     {
/*  540: 625 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  541: 626 */       e.printStackTrace();
/*  542: 627 */       LOG.error("Error al anular un cheque protestado", e);
/*  543:     */     }
/*  544: 629 */     return "";
/*  545:     */   }
/*  546:     */   
/*  547:     */   public String actualizarGarantiaCliente()
/*  548:     */   {
/*  549: 637 */     setDetalleFormaCobroSeleccionado((DetalleFormaCobro)this.dtFormaCobro.getRowData());
/*  550:     */     
/*  551: 639 */     this.servicioCobro.actualizarGarantiaCliente(getDetalleFormaCobroSeleccionado());
/*  552:     */     
/*  553: 641 */     GarantiaCliente garantiaCliente = getDetalleFormaCobroSeleccionado().getGarantiaCliente();
/*  554: 643 */     if (garantiaCliente != null)
/*  555:     */     {
/*  556: 645 */       actualizarFechaCobro();
/*  557: 647 */       if ((garantiaCliente.getRecibidoPor() == null) || (garantiaCliente.getRecibidoPor().isEmpty())) {
/*  558: 648 */         garantiaCliente.setRecibidoPor(AppUtil.getUsuarioEnSesion().getNombre1() + " " + AppUtil.getUsuarioEnSesion().getNombre2());
/*  559:     */       }
/*  560: 651 */       if ((garantiaCliente.getGirador() == null) || (garantiaCliente.getGirador().isEmpty())) {
/*  561: 652 */         garantiaCliente.setGirador(this.cobro.getEmpresa().getNombreFiscal());
/*  562:     */       }
/*  563:     */     }
/*  564: 656 */     return "";
/*  565:     */   }
/*  566:     */   
/*  567:     */   public String actualizarVoucher()
/*  568:     */   {
/*  569: 660 */     setDetalleFormaCobroSeleccionado((DetalleFormaCobro)this.dtFormaCobro.getRowData());
/*  570: 661 */     this.servicioCobro.actualizarVoucher(getCobro(), getDetalleFormaCobroSeleccionado(), AppUtil.getPuntoDeVenta(), null);
/*  571: 662 */     return null;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public String refrescar()
/*  575:     */   {
/*  576: 666 */     return "";
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void actualizarCliente(SelectEvent event)
/*  580:     */   {
/*  581: 671 */     Empresa empresa = (Empresa)event.getObject();
/*  582: 672 */     getCobro().setRecaudador(empresa.getCliente().getRecaudador());
/*  583: 673 */     getCobro().getListaDetalleCobro().clear();
/*  584: 674 */     for (DetalleCobro detalleCobro : this.cobro.getListaDetalleCobro()) {
/*  585: 675 */       detalleCobro.setEliminado(true);
/*  586:     */     }
/*  587: 678 */     EntidadUsuario entidadUsuario = this.servicioUsuario.cargarDetalle(AppUtil.getUsuarioEnSesion().getIdUsuario(), AppUtil.getOrganizacion().getId());
/*  588: 679 */     if (entidadUsuario.getRecaudador() != null)
/*  589:     */     {
/*  590: 680 */       getListaRecaudador().clear();
/*  591: 681 */       getListaRecaudador().add(entidadUsuario.getRecaudador());
/*  592: 682 */       getCobro().setRecaudador(entidadUsuario.getRecaudador());
/*  593:     */     }
/*  594:     */   }
/*  595:     */   
/*  596:     */   public String getDirectorioDescarga()
/*  597:     */   {
/*  598: 688 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "cobro");
/*  599:     */   }
/*  600:     */   
/*  601:     */   public String getNombreArchivo()
/*  602:     */   {
/*  603: 693 */     return this.cobro.getArchivo();
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void processUpload(FileUploadEvent event)
/*  607:     */   {
/*  608:     */     try
/*  609:     */     {
/*  610: 706 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "cobro");
/*  611:     */       
/*  612: 708 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getCobro().getNumero(), uploadDir);
/*  613:     */       
/*  614: 710 */       HashMap<String, Object> campos = new HashMap();
/*  615: 711 */       campos.put("archivo", fileName);
/*  616: 712 */       this.servicioCobro.actualizarAtributoEntidad(this.cobro, campos);
/*  617:     */       
/*  618: 714 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  619:     */     }
/*  620:     */     catch (Exception e)
/*  621:     */     {
/*  622: 717 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  623: 718 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  624:     */     }
/*  625:     */   }
/*  626:     */   
/*  627:     */   public String eliminarArchivo()
/*  628:     */   {
/*  629: 724 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.cobro.getArchivo());
/*  630: 725 */     this.cobro.setArchivo(null);
/*  631: 726 */     HashMap<String, Object> campos = new HashMap();
/*  632: 727 */     campos.put("archivo", null);
/*  633: 728 */     this.servicioCobro.actualizarAtributoEntidad(this.cobro, campos);
/*  634: 729 */     return null;
/*  635:     */   }
/*  636:     */   
/*  637:     */   public void onRowSelect(SelectEvent event)
/*  638:     */   {
/*  639: 737 */     this.cobro = ((Cobro)event.getObject());
/*  640:     */   }
/*  641:     */   
/*  642:     */   public void anularCobro()
/*  643:     */   {
/*  644: 746 */     if (this.cobro.getId() > 0) {
/*  645:     */       try
/*  646:     */       {
/*  647: 749 */         this.servicioCobro.anularCobro(this.cobro);
/*  648:     */         
/*  649: 751 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  650:     */       }
/*  651:     */       catch (ExcepcionAS2Financiero e)
/*  652:     */       {
/*  653: 754 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  654: 755 */         LOG.info("ERROR AL ANULAR COBRO", e);
/*  655:     */       }
/*  656:     */       catch (ExcepcionAS2Ventas e)
/*  657:     */       {
/*  658: 758 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  659: 759 */         LOG.info("ERROR AL ANULAR COBRO", e);
/*  660:     */       }
/*  661:     */       catch (ExcepcionAS2 e)
/*  662:     */       {
/*  663: 761 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  664: 762 */         LOG.info("ERROR AL ANULAR COBRO", e);
/*  665:     */       }
/*  666:     */       catch (Exception e)
/*  667:     */       {
/*  668: 764 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  669: 765 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  670:     */       }
/*  671:     */     } else {
/*  672: 769 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  673:     */     }
/*  674:     */   }
/*  675:     */   
/*  676:     */   public String cargarValorCuota()
/*  677:     */   {
/*  678: 774 */     DetalleCobro d = (DetalleCobro)this.dtDetalleCobro.getRowData();
/*  679: 775 */     d.setValor(d.getCuentaPorCobrar().getSaldo().subtract(d.getCuentaPorCobrar().getValorBloqueado()));
/*  680: 776 */     calcularValorAPagar();
/*  681: 777 */     return "";
/*  682:     */   }
/*  683:     */   
/*  684:     */   public String limpiarValorCuota()
/*  685:     */   {
/*  686: 781 */     DetalleCobro d = (DetalleCobro)this.dtDetalleCobro.getRowData();
/*  687: 782 */     d.setValor(BigDecimal.ZERO);
/*  688: 783 */     calcularValorAPagar();
/*  689:     */     
/*  690: 785 */     return "";
/*  691:     */   }
/*  692:     */   
/*  693:     */   public void calcularValorAPagar()
/*  694:     */   {
/*  695: 793 */     BigDecimal valorTmp = BigDecimal.ZERO;
/*  696: 794 */     for (DetalleCobro detalleCobro : getCobro().getListaDetalleCobro()) {
/*  697: 795 */       if ((!detalleCobro.isEliminado()) && (detalleCobro.getValor().compareTo(BigDecimal.ZERO) > 0))
/*  698:     */       {
/*  699: 796 */         getCobro().setIndicadorExcede(false);
/*  700: 797 */         if (detalleCobro.getValor().compareTo(detalleCobro.getCuentaPorCobrar().getSaldo()) == 1)
/*  701:     */         {
/*  702: 798 */           addInfoMessage(getLanguageController().getMensaje("msg_error_excedio_valor_a_liquidar"));
/*  703:     */         }
/*  704: 801 */         else if (detalleCobro.getValor().add(detalleCobro.getCuentaPorCobrar().getValorBloqueado()).compareTo(detalleCobro.getCuentaPorCobrar().getSaldo()) == 1)
/*  705:     */         {
/*  706: 802 */           getCobro().setIndicadorExcede(true);
/*  707: 803 */           addInfoMessage(getLanguageController().getMensaje("msg_info_valor_posfechado_excede_saldo"));
/*  708:     */         }
/*  709: 805 */         valorTmp = valorTmp.add(detalleCobro.getValor());
/*  710:     */       }
/*  711:     */     }
/*  712: 808 */     if (getCobro().getListaDetalleFormaCobro().size() == 1) {
/*  713: 809 */       for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/*  714: 810 */         if (!detalleFormaCobro.isEliminado()) {
/*  715: 811 */           detalleFormaCobro.setValor(valorTmp);
/*  716:     */         }
/*  717:     */       }
/*  718:     */     }
/*  719: 816 */     this.cobro.setValor(valorTmp);
/*  720:     */   }
/*  721:     */   
/*  722:     */   public String cargarXML(FileUploadEvent event)
/*  723:     */   {
/*  724:     */     try
/*  725:     */     {
/*  726: 822 */       List<Cobro> listaCobro = new ArrayList();
/*  727:     */       
/*  728: 824 */       getCobro().setCaja(AppUtil.getCaja());
/*  729:     */       
/*  730: 826 */       SAXBuilder builder = new SAXBuilder();
/*  731:     */       
/*  732: 828 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  733:     */       
/*  734:     */ 
/*  735: 831 */       Document document = builder.build(input);
/*  736: 832 */       Element rootNode = document.getRootElement();
/*  737: 833 */       Element nodoComprobante = rootNode.getChild("comprobante");
/*  738:     */       
/*  739: 835 */       String comprobante = nodoComprobante.getText();
/*  740: 836 */       Document documentString = builder.build(new InputSource(new StringReader(comprobante)));
/*  741: 837 */       Element rootNodes = documentString.getRootElement();
/*  742: 838 */       Element nodoInfoTributaria = rootNodes.getChild("infoTributaria");
/*  743:     */       
/*  744: 840 */       List<Element> infoImpuestos = rootNodes.getChildren("impuestos");
/*  745:     */       
/*  746: 842 */       listaCobro = this.servicioCobro.cargarXML(new ArrayList(), infoImpuestos, nodoInfoTributaria, AppUtil.getOrganizacion()
/*  747: 843 */         .getIdOrganizacion(), this.cobro.getDocumento(), AppUtil.getSucursal(), AppUtil.getCaja(), event.getFile().getFileName(), true);
/*  748: 844 */       getCobro().setFecha(new Date());
/*  749: 845 */       setCobro((Cobro)listaCobro.get(0));
/*  750:     */     }
/*  751:     */     catch (AS2Exception e)
/*  752:     */     {
/*  753: 849 */       JsfUtil.addErrorMessage(e, "");
/*  754: 850 */       e.printStackTrace();
/*  755:     */     }
/*  756:     */     catch (Exception e)
/*  757:     */     {
/*  758: 852 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  759: 853 */       e.printStackTrace();
/*  760:     */     }
/*  761: 856 */     return null;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public Cobro getCobro()
/*  765:     */   {
/*  766: 868 */     return this.cobro;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void setCobro(Cobro cobro)
/*  770:     */   {
/*  771: 878 */     this.cobro = cobro;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public DataTable getDtCobro()
/*  775:     */   {
/*  776: 887 */     return this.dtCobro;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public void setDtCobro(DataTable dtCobro)
/*  780:     */   {
/*  781: 897 */     this.dtCobro = dtCobro;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public CuentaBancariaOrganizacionBean getCuentaBancariaOrganizacionBean()
/*  785:     */   {
/*  786: 906 */     return this.cuentaBancariaOrganizacionBean;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public void setCuentaBancariaOrganizacionBean(CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean)
/*  790:     */   {
/*  791: 916 */     this.cuentaBancariaOrganizacionBean = cuentaBancariaOrganizacionBean;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public List<Empresa> getListaEmpresa()
/*  795:     */   {
/*  796: 925 */     if (this.listaEmpresa == null) {
/*  797: 926 */       this.listaEmpresa = this.servicioEmpresa.obtenerClientes();
/*  798:     */     }
/*  799: 928 */     return this.listaEmpresa;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/*  803:     */   {
/*  804: 938 */     this.listaEmpresa = listaEmpresa;
/*  805:     */   }
/*  806:     */   
/*  807:     */   public List<Documento> getListaDocumento()
/*  808:     */   {
/*  809: 948 */     if (this.listaDocumento == null) {
/*  810:     */       try
/*  811:     */       {
/*  812: 950 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.COBRO_CLIENTE);
/*  813:     */       }
/*  814:     */       catch (ExcepcionAS2 e)
/*  815:     */       {
/*  816: 952 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  817:     */       }
/*  818:     */     }
/*  819: 955 */     return this.listaDocumento;
/*  820:     */   }
/*  821:     */   
/*  822:     */   public void setListaDocumento(List<Documento> listaDocumento)
/*  823:     */   {
/*  824: 965 */     this.listaDocumento = listaDocumento;
/*  825:     */   }
/*  826:     */   
/*  827:     */   public List<DetalleCobro> getListaDetalleCobro()
/*  828:     */   {
/*  829: 976 */     List<DetalleCobro> lista = new ArrayList();
/*  830: 978 */     for (DetalleCobro detalleCobro : getCobro().getListaDetalleCobro()) {
/*  831: 979 */       if (!detalleCobro.isEliminado())
/*  832:     */       {
/*  833: 981 */         if ((detalleCobro.getCuentaPorCobrar().getFacturaCliente().getFacturaClienteSRI() != null) && 
/*  834: 982 */           (detalleCobro.getCuentaPorCobrar().getFacturaCliente().getFacturaClienteSRI().getMensajeSRI() != null)) {
/*  835: 984 */           if (!detalleCobro.getCuentaPorCobrar().getFacturaCliente().getFacturaClienteSRI().getMensajeSRI().toUpperCase().contains("AUTORIZADO")) {
/*  836: 985 */             detalleCobro.getCuentaPorCobrar().getFacturaCliente().setIndicadorNoAutorizadoSRI(true);
/*  837:     */           }
/*  838:     */         }
/*  839: 988 */         lista.add(detalleCobro);
/*  840:     */       }
/*  841:     */     }
/*  842: 992 */     return lista;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public List<DetalleFormaCobro> getListaDetalleFormaCobro()
/*  846:     */   {
/*  847:1002 */     List<DetalleFormaCobro> lista = new ArrayList();
/*  848:1004 */     for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/*  849:1005 */       if (!detalleFormaCobro.isEliminado()) {
/*  850:1006 */         lista.add(detalleFormaCobro);
/*  851:     */       }
/*  852:     */     }
/*  853:1009 */     return lista;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public BigDecimal getTotalLiquidado()
/*  857:     */   {
/*  858:1013 */     this.totalLiquidado = BigDecimal.ZERO;
/*  859:1015 */     for (DetalleCobro detalleCobro : getCobro().getListaDetalleCobro()) {
/*  860:1016 */       if (!detalleCobro.isEliminado()) {
/*  861:1017 */         this.totalLiquidado = this.totalLiquidado.add(detalleCobro.getValor());
/*  862:     */       }
/*  863:     */     }
/*  864:1021 */     return this.totalLiquidado;
/*  865:     */   }
/*  866:     */   
/*  867:     */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/*  868:     */   {
/*  869:1025 */     this.totalLiquidado = totalLiquidado;
/*  870:     */   }
/*  871:     */   
/*  872:     */   public DataTable getDtFormaCobro()
/*  873:     */   {
/*  874:1034 */     return this.dtFormaCobro;
/*  875:     */   }
/*  876:     */   
/*  877:     */   public void setDtFormaCobro(DataTable dtFormaCobro)
/*  878:     */   {
/*  879:1044 */     this.dtFormaCobro = dtFormaCobro;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public LazyDataModel<Cobro> getListaCobro()
/*  883:     */   {
/*  884:1053 */     return this.listaCobro;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void setListaCobro(LazyDataModel<Cobro> listaCobro)
/*  888:     */   {
/*  889:1063 */     this.listaCobro = listaCobro;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/*  893:     */   {
/*  894:1067 */     if (this.listaCuentaBancariaOrganizacion == null) {
/*  895:1068 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", false, null);
/*  896:     */     }
/*  897:1070 */     return this.listaCuentaBancariaOrganizacion;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/*  901:     */   {
/*  902:1074 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public List<GarantiaCliente> getListaChequesPosfechados()
/*  906:     */   {
/*  907:1078 */     if (this.listaChequesPosfechados == null) {
/*  908:1079 */       this.listaChequesPosfechados = this.servicioGarantiaCliente.buscaListaChequesPosfechados(this.cobro.getEmpresa().getId());
/*  909:     */     }
/*  910:1081 */     return this.listaChequesPosfechados;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public void setListaChequesPosfechados(List<GarantiaCliente> listaChequesPosfechados)
/*  914:     */   {
/*  915:1085 */     this.listaChequesPosfechados = listaChequesPosfechados;
/*  916:     */   }
/*  917:     */   
/*  918:     */   public DataTable getDtChequesPosfechados()
/*  919:     */   {
/*  920:1089 */     return this.dtChequesPosfechados;
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void setDtChequesPosfechados(DataTable dtChequesPosfechados)
/*  924:     */   {
/*  925:1093 */     this.dtChequesPosfechados = dtChequesPosfechados;
/*  926:     */   }
/*  927:     */   
/*  928:     */   public DetalleFormaCobro getDetalleFormaCobroSeleccionado()
/*  929:     */   {
/*  930:1097 */     return this.detalleFormaCobroSeleccionado;
/*  931:     */   }
/*  932:     */   
/*  933:     */   public void setDetalleFormaCobroSeleccionado(DetalleFormaCobro detalleFormaCobroSeleccionado)
/*  934:     */   {
/*  935:1101 */     this.detalleFormaCobroSeleccionado = detalleFormaCobroSeleccionado;
/*  936:     */   }
/*  937:     */   
/*  938:     */   public DataTable getDtDetalleCobro()
/*  939:     */   {
/*  940:1110 */     return this.dtDetalleCobro;
/*  941:     */   }
/*  942:     */   
/*  943:     */   public void setDtDetalleCobro(DataTable dtDetalleCobro)
/*  944:     */   {
/*  945:1120 */     this.dtDetalleCobro = dtDetalleCobro;
/*  946:     */   }
/*  947:     */   
/*  948:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  949:     */   {
/*  950:1124 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/*  951:     */   }
/*  952:     */   
/*  953:     */   public List<Banco> getListaBanco()
/*  954:     */   {
/*  955:1133 */     if (this.listaBanco == null) {
/*  956:1134 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/*  957:     */     }
/*  958:1136 */     return this.listaBanco;
/*  959:     */   }
/*  960:     */   
/*  961:     */   public void setListaBanco(List<Banco> listaBanco)
/*  962:     */   {
/*  963:1146 */     this.listaBanco = listaBanco;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public List<DetalleFormaCobro> getListaDetalleFormaCobroProtesto()
/*  967:     */   {
/*  968:1155 */     return this.listaDetalleFormaCobroProtesto;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public DataTable getDtGarantiaCliente()
/*  972:     */   {
/*  973:1164 */     return this.dtGarantiaCliente;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public void setDtGarantiaCliente(DataTable dtGarantiaCliente)
/*  977:     */   {
/*  978:1174 */     this.dtGarantiaCliente = dtGarantiaCliente;
/*  979:     */   }
/*  980:     */   
/*  981:     */   public Date getFechaProtesto()
/*  982:     */   {
/*  983:1183 */     return this.fechaProtesto;
/*  984:     */   }
/*  985:     */   
/*  986:     */   public void setFechaProtesto(Date fechaProtesto)
/*  987:     */   {
/*  988:1193 */     this.fechaProtesto = fechaProtesto;
/*  989:     */   }
/*  990:     */   
/*  991:     */   public BigDecimal getTotalLiquidarFormaCobro()
/*  992:     */   {
/*  993:1202 */     this.totalLiquidarFormaCobro = BigDecimal.ZERO;
/*  994:1203 */     for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/*  995:1204 */       if (!detalleFormaCobro.isEliminado()) {
/*  996:1205 */         this.totalLiquidarFormaCobro = this.totalLiquidarFormaCobro.add(detalleFormaCobro.getValor());
/*  997:     */       }
/*  998:     */     }
/*  999:1209 */     return this.totalLiquidarFormaCobro;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public void setTotalLiquidarFormaCobro(BigDecimal totalLiquidarFormaCobro)
/* 1003:     */   {
/* 1004:1219 */     this.totalLiquidarFormaCobro = totalLiquidarFormaCobro;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public String getNumero()
/* 1008:     */   {
/* 1009:1228 */     return this.numero;
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public void setNumero(String numero)
/* 1013:     */   {
/* 1014:1238 */     this.numero = numero;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public DataTable getDtDetalleFormaCobroProtesto()
/* 1018:     */   {
/* 1019:1247 */     return this.dtDetalleFormaCobroProtesto;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public void setDtDetalleFormaCobroProtesto(DataTable dtDetalleFormaCobroProtesto)
/* 1023:     */   {
/* 1024:1257 */     this.dtDetalleFormaCobroProtesto = dtDetalleFormaCobroProtesto;
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public List<Recaudador> getListaRecaudador()
/* 1028:     */   {
/* 1029:1266 */     if (this.listaRecaudador == null) {
/* 1030:1267 */       this.listaRecaudador = this.servicioRecaudador.obtenerListaCombo("nombre", true, null);
/* 1031:     */     }
/* 1032:1269 */     return this.listaRecaudador;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public Integer getIdCobro()
/* 1036:     */   {
/* 1037:1278 */     return this.idCobro;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public void setIdCobro(Integer idCobro)
/* 1041:     */   {
/* 1042:1288 */     this.idCobro = idCobro;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public TipoOrganizacion getTipoOrganizacion()
/* 1046:     */   {
/* 1047:1297 */     if (this.tipoOrganizacion == null) {
/* 1048:1298 */       this.tipoOrganizacion = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/* 1049:     */     }
/* 1050:1300 */     return this.tipoOrganizacion;
/* 1051:     */   }
/* 1052:     */   
/* 1053:     */   public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion)
/* 1054:     */   {
/* 1055:1310 */     this.tipoOrganizacion = tipoOrganizacion;
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public boolean isIndicadorRenderPanelEdicionFormaCobro()
/* 1059:     */   {
/* 1060:1314 */     return this.indicadorRenderPanelEdicionFormaCobro;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   public void setIndicadorRenderPanelEdicionFormaCobro(boolean indicadorRenderPanelEdicionFormaCobro)
/* 1064:     */   {
/* 1065:1318 */     this.indicadorRenderPanelEdicionFormaCobro = indicadorRenderPanelEdicionFormaCobro;
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public DataTable getDtEdicionFormaCobro()
/* 1069:     */   {
/* 1070:1322 */     return this.dtEdicionFormaCobro;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public void setDtEdicionFormaCobro(DataTable dtEdicionFormaCobro)
/* 1074:     */   {
/* 1075:1326 */     this.dtEdicionFormaCobro = dtEdicionFormaCobro;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public String getNumeroFactura()
/* 1079:     */   {
/* 1080:1330 */     return this.numeroFactura;
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public void setNumeroFactura(String numeroFactura)
/* 1084:     */   {
/* 1085:1334 */     this.numeroFactura = numeroFactura;
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public DocumentoBase getDocumento()
/* 1089:     */   {
/* 1090:1339 */     return null;
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public TipoCuentaBancariaOrganizacion getTipoCuentaBancariaOrganizacion()
/* 1094:     */   {
/* 1095:1344 */     return null;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public void cargarPlanTarjetaListener(DetalleFormaCobro voucher)
/* 1099:     */   {
/* 1100:1348 */     cargarPlanTarjeta(voucher.getTarjetaCredito());
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public void actualizarDetalleSeleccioando(DetalleFormaCobro detalle)
/* 1104:     */   {
/* 1105:1352 */     this.detalleFormaCobroSeleccionado = ((DetalleFormaCobro)this.dtEdicionFormaCobro.getRowData());
/* 1106:     */   }
/* 1107:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.CobroBean
 * JD-Core Version:    0.7.0.1
 */