/*    1:     */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    5:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    9:     */ import com.asinfo.as2.controller.LanguageController;
/*   10:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   12:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   13:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   14:     */ import com.asinfo.as2.entities.CajaChica;
/*   15:     */ import com.asinfo.as2.entities.Ciudad;
/*   16:     */ import com.asinfo.as2.entities.CompraCajaChica;
/*   17:     */ import com.asinfo.as2.entities.CuentaContable;
/*   18:     */ import com.asinfo.as2.entities.DetalleCompraCajaChica;
/*   19:     */ import com.asinfo.as2.entities.DimensionContable;
/*   20:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   21:     */ import com.asinfo.as2.entities.Documento;
/*   22:     */ import com.asinfo.as2.entities.Empresa;
/*   23:     */ import com.asinfo.as2.entities.Impuesto;
/*   24:     */ import com.asinfo.as2.entities.Organizacion;
/*   25:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   26:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   27:     */ import com.asinfo.as2.entities.Secuencia;
/*   28:     */ import com.asinfo.as2.entities.Sucursal;
/*   29:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   30:     */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*   31:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   32:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   33:     */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*   34:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   35:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   36:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   37:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   38:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   39:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*   40:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*   41:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*   42:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*   43:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   44:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   45:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCajaChica;
/*   46:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica;
/*   47:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   48:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   49:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   50:     */ import com.asinfo.as2.util.AppUtil;
/*   51:     */ import com.asinfo.as2.util.RutaArchivo;
/*   52:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   53:     */ import com.asinfo.as2.utils.JsfUtil;
/*   54:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   55:     */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*   56:     */ import java.io.BufferedInputStream;
/*   57:     */ import java.io.File;
/*   58:     */ import java.io.FileNotFoundException;
/*   59:     */ import java.io.InputStream;
/*   60:     */ import java.io.StringReader;
/*   61:     */ import java.math.BigDecimal;
/*   62:     */ import java.math.RoundingMode;
/*   63:     */ import java.text.SimpleDateFormat;
/*   64:     */ import java.util.ArrayList;
/*   65:     */ import java.util.Date;
/*   66:     */ import java.util.HashMap;
/*   67:     */ import java.util.List;
/*   68:     */ import java.util.Map;
/*   69:     */ import javax.annotation.PostConstruct;
/*   70:     */ import javax.ejb.EJB;
/*   71:     */ import javax.faces.bean.ManagedBean;
/*   72:     */ import javax.faces.bean.ManagedProperty;
/*   73:     */ import javax.faces.bean.ViewScoped;
/*   74:     */ import javax.faces.context.ExternalContext;
/*   75:     */ import javax.faces.context.FacesContext;
/*   76:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   77:     */ import javax.servlet.http.HttpSession;
/*   78:     */ import javax.validation.constraints.NotNull;
/*   79:     */ import javax.validation.constraints.Size;
/*   80:     */ import org.apache.log4j.Logger;
/*   81:     */ import org.jdom2.Document;
/*   82:     */ import org.jdom2.Element;
/*   83:     */ import org.jdom2.input.SAXBuilder;
/*   84:     */ import org.primefaces.component.datatable.DataTable;
/*   85:     */ import org.primefaces.component.selectonemenu.SelectOneMenu;
/*   86:     */ import org.primefaces.event.FileUploadEvent;
/*   87:     */ import org.primefaces.event.SelectEvent;
/*   88:     */ import org.primefaces.model.LazyDataModel;
/*   89:     */ import org.primefaces.model.SortOrder;
/*   90:     */ import org.primefaces.model.StreamedContent;
/*   91:     */ import org.primefaces.model.UploadedFile;
/*   92:     */ import org.xml.sax.InputSource;
/*   93:     */ 
/*   94:     */ @ManagedBean
/*   95:     */ @ViewScoped
/*   96:     */ public class CompraCajaChicaBean
/*   97:     */   extends PageControllerAS2
/*   98:     */ {
/*   99:     */   private static final long serialVersionUID = -1965885031797009135L;
/*  100:     */   @EJB
/*  101:     */   private ServicioCompraCajaChica servicioCompraCajaChica;
/*  102:     */   @EJB
/*  103:     */   private ServicioCuentaContable servicioCuentaContable;
/*  104:     */   @EJB
/*  105:     */   private ServicioCajaChica servicioCajaChica;
/*  106:     */   @EJB
/*  107:     */   private ServicioDocumento servicioDocumento;
/*  108:     */   @EJB
/*  109:     */   private ServicioEmpresa servicioEmpresa;
/*  110:     */   @EJB
/*  111:     */   private ServicioImpuesto servicioImpuesto;
/*  112:     */   @EJB
/*  113:     */   private ServicioSRI servicioSRI;
/*  114:     */   @EJB
/*  115:     */   private ServicioSecuencia servicioSecuencia;
/*  116:     */   @EJB
/*  117:     */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  118:     */   @EJB
/*  119:     */   protected transient ServicioPeriodo servicioPeriodo;
/*  120:     */   @EJB
/*  121:     */   protected transient ServicioGenerico<AutorizacionProveedorSRI> servicioAutorizacionProveedorSRI;
/*  122:     */   @EJB
/*  123:     */   protected transient ServicioCiudad servicioCiudad;
/*  124:     */   @ManagedProperty("#{listaDimensionContableBean}")
/*  125:     */   private ListaDimensionContableBean listaDimensionContableBean;
/*  126:     */   private CompraCajaChica compraCajaChica;
/*  127:     */   private LazyDataModel<CompraCajaChica> listaCompraCajaChica;
/*  128:     */   private int idAutorizacionProveedorSRISeleccionado;
/*  129:     */   private List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI;
/*  130:     */   private List<Documento> listaDocumento;
/*  131:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  132:     */   private List<CajaChica> listaCajaChica;
/*  133:     */   private List<TipoComprobanteSRI> listaTipoComprobanteSRI;
/*  134:     */   private DataTable dtCompraCajaChica;
/*  135:     */   private DataTable dtDetalleCompraCajaChica;
/*  136:     */   private DataTable dtCuentaContable;
/*  137: 160 */   private DetalleCompraCajaChica lineaDetalleCompraCajaChica = new DetalleCompraCajaChica();
/*  138:     */   private CuentaContable cuentaContable;
/*  139:     */   private BigDecimal diferencia;
/*  140:     */   private BigDecimal total;
/*  141:     */   private RangoImpuesto rangoImpuesto;
/*  142:     */   @ManagedProperty("#{listaCuentaContableBean}")
/*  143:     */   private ListaCuentaContableBean listaCuentaContableBean;
/*  144:     */   @EJB
/*  145:     */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  146:     */   @EJB
/*  147:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  148:     */   private DataTable dtReembolsoGastos;
/*  149: 176 */   private BigDecimal totalBaseImponibleDiferenteCero = BigDecimal.ZERO;
/*  150: 177 */   private BigDecimal totalBaseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  151: 178 */   private BigDecimal totalBaseImponibleTarifaCero = BigDecimal.ZERO;
/*  152: 179 */   private BigDecimal totalBaseExentaIVA = BigDecimal.ZERO;
/*  153: 180 */   private BigDecimal totalMontoIva = BigDecimal.ZERO;
/*  154: 181 */   private BigDecimal totalMontoIce = BigDecimal.ZERO;
/*  155: 182 */   private BigDecimal totalDescuentoImpuesto = BigDecimal.ZERO;
/*  156:     */   private List<TipoIdentificacion> listaTipoIdentificacionCombo;
/*  157:     */   private boolean renderProveedorNuevo;
/*  158:     */   private boolean renderAutorizacionNuevo;
/*  159:     */   private String identificacionProveedor;
/*  160:     */   @NotNull
/*  161:     */   private TipoIdentificacion tipoIdentificacionProveedor;
/*  162:     */   @Size(min=10, max=100)
/*  163:     */   private String nombreProveedor;
/*  164:     */   @Size(min=2, max=50)
/*  165:     */   private String direccionProveedor;
/*  166:     */   @Size(max=13)
/*  167:     */   private String telefonoProveedor;
/*  168:     */   @NotNull
/*  169:     */   private Ciudad ciudad;
/*  170:     */   private String email;
/*  171:     */   private List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRIProveedor;
/*  172:     */   private String mails;
/*  173:     */   
/*  174:     */   @PostConstruct
/*  175:     */   public void init()
/*  176:     */   {
/*  177:     */     try
/*  178:     */     {
/*  179: 214 */       this.listaCompraCajaChica = new LazyDataModel()
/*  180:     */       {
/*  181:     */         private static final long serialVersionUID = 1L;
/*  182:     */         
/*  183:     */         public List<CompraCajaChica> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  184:     */         {
/*  185: 220 */           List<CompraCajaChica> lista = new ArrayList();
/*  186:     */           
/*  187: 222 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  188: 223 */           lista = CompraCajaChicaBean.this.servicioCompraCajaChica.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  189: 224 */           CompraCajaChicaBean.this.listaCompraCajaChica.setRowCount(CompraCajaChicaBean.this.servicioCompraCajaChica.contarPorCriterio(filters));
/*  190: 225 */           return lista;
/*  191:     */         }
/*  192:     */       };
/*  193:     */     }
/*  194:     */     catch (Exception e)
/*  195:     */     {
/*  196: 230 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  197: 231 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  198:     */     }
/*  199:     */   }
/*  200:     */   
/*  201:     */   public String editar()
/*  202:     */   {
/*  203: 243 */     if (getCompraCajaChica().getId() > 0) {
/*  204:     */       try
/*  205:     */       {
/*  206: 246 */         this.compraCajaChica = this.servicioCompraCajaChica.cargarDetalle(this.compraCajaChica.getIdCompraCajaChica());
/*  207: 247 */         this.servicioCompraCajaChica.esEditable(this.compraCajaChica);
/*  208: 248 */         cargarDirecciones();
/*  209: 250 */         if (this.compraCajaChica.isIndicadorFactura())
/*  210:     */         {
/*  211: 251 */           cargarAutorizaciones();
/*  212: 252 */           if ((this.compraCajaChica.getFacturaProveedorSRI() != null) && (this.compraCajaChica.getFacturaProveedorSRI().isIndicadorReembolso()))
/*  213:     */           {
/*  214: 253 */             totalizarReembolsoGastos();
/*  215: 255 */             for (ReembolsoGastos rg : this.compraCajaChica.getFacturaProveedorSRI().getListaReembolsoGastos())
/*  216:     */             {
/*  217: 256 */               List<TipoComprobanteSRI> listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(rg.getTipoIdentificacion());
/*  218: 257 */               rg.setListaTipoComprobanteSRI(listaTipoComprobanteSRI);
/*  219:     */             }
/*  220:     */           }
/*  221: 260 */           cargarListaTipoComprobanteSRI();
/*  222:     */         }
/*  223: 263 */         setEditado(true);
/*  224:     */       }
/*  225:     */       catch (ExcepcionAS2Financiero e)
/*  226:     */       {
/*  227: 266 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  228: 267 */         LOG.info("ERROR AL EDITAR COMPRA CAJA CHICA:", e);
/*  229:     */       }
/*  230:     */       catch (ExcepcionAS2Compras e)
/*  231:     */       {
/*  232: 270 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion() + e.getMessage()));
/*  233: 271 */         LOG.info("ERROR AL EDITAR COMPRA CAJA CHICA:", e);
/*  234:     */       }
/*  235:     */       catch (Exception e)
/*  236:     */       {
/*  237: 274 */         addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/*  238: 275 */         LOG.error("ERROR AL EDITAR COMPRA CAJA CHICA:", e);
/*  239:     */       }
/*  240:     */     } else {
/*  241: 278 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  242:     */     }
/*  243: 281 */     return "";
/*  244:     */   }
/*  245:     */   
/*  246:     */   public void seleccionarDetalle(DetalleCompraCajaChica detalleCompraCajaChica)
/*  247:     */   {
/*  248: 285 */     this.lineaDetalleCompraCajaChica = detalleCompraCajaChica;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public void cargarDimensionContableListener(SelectEvent event)
/*  252:     */   {
/*  253: 290 */     DimensionContable dimension = (DimensionContable)event.getObject();
/*  254:     */     try
/*  255:     */     {
/*  256: 292 */       String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/*  257: 293 */       if (numeroDimension.equals("1")) {
/*  258: 294 */         this.lineaDetalleCompraCajaChica.setDimensionContable1(dimension);
/*  259: 295 */       } else if (numeroDimension.equals("2")) {
/*  260: 296 */         this.lineaDetalleCompraCajaChica.setDimensionContable2(dimension);
/*  261: 297 */       } else if (numeroDimension.equals("3")) {
/*  262: 298 */         this.lineaDetalleCompraCajaChica.setDimensionContable3(dimension);
/*  263: 299 */       } else if (numeroDimension.equals("4")) {
/*  264: 300 */         this.lineaDetalleCompraCajaChica.setDimensionContable4(dimension);
/*  265: 301 */       } else if (numeroDimension.equals("5")) {
/*  266: 302 */         this.lineaDetalleCompraCajaChica.setDimensionContable5(dimension);
/*  267:     */       }
/*  268:     */     }
/*  269:     */     catch (Exception e)
/*  270:     */     {
/*  271: 306 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  272: 307 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/*  273:     */     }
/*  274:     */   }
/*  275:     */   
/*  276:     */   public String crear()
/*  277:     */   {
/*  278: 318 */     limpiar();
/*  279: 319 */     setEditado(true);
/*  280: 320 */     return "";
/*  281:     */   }
/*  282:     */   
/*  283:     */   public String guardar()
/*  284:     */   {
/*  285:     */     try
/*  286:     */     {
/*  287: 331 */       if (getDiferencia().compareTo(BigDecimal.ZERO) == 0)
/*  288:     */       {
/*  289: 333 */         if (this.compraCajaChica.isIndicadorFactura())
/*  290:     */         {
/*  291: 335 */           obtenerResumenImpuestos();
/*  292: 336 */           if ((this.compraCajaChica.getFacturaProveedorSRI() != null) && (this.compraCajaChica.getFacturaProveedorSRI().isIndicadorReembolso()))
/*  293:     */           {
/*  294: 338 */             this.servicioFacturaProveedor.agregarDetalleFacturaSRI332(this.compraCajaChica.getFacturaProveedorSRI());
/*  295:     */             
/*  296: 340 */             this.servicioFacturaProveedor.validaReembolsoGastos(this.compraCajaChica.getFacturaProveedorSRI());
/*  297:     */           }
/*  298:     */         }
/*  299: 345 */         if (this.compraCajaChica.isIndicadorFactura()) {
/*  300: 346 */           this.compraCajaChica.getFacturaProveedorSRI().setTipoEmpresa(this.compraCajaChica.getEmpresa().getTipoEmpresa());
/*  301:     */         }
/*  302: 349 */         this.servicioCompraCajaChica.guardar(this.compraCajaChica);
/*  303:     */         
/*  304: 351 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  305: 352 */         limpiar();
/*  306:     */       }
/*  307:     */       else
/*  308:     */       {
/*  309: 355 */         addErrorMessage(getLanguageController().getMensaje("msg_error_valor_factura"));
/*  310:     */       }
/*  311:     */     }
/*  312:     */     catch (ExcepcionAS2Financiero e)
/*  313:     */     {
/*  314: 358 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  315: 359 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  316: 360 */       e.printStackTrace();
/*  317:     */     }
/*  318:     */     catch (AS2Exception e)
/*  319:     */     {
/*  320: 362 */       JsfUtil.addErrorMessage(e, "");
/*  321:     */     }
/*  322:     */     catch (ExcepcionAS2 e)
/*  323:     */     {
/*  324: 364 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  325: 365 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  326: 366 */       e.printStackTrace();
/*  327:     */     }
/*  328:     */     catch (Exception e)
/*  329:     */     {
/*  330: 368 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  331: 369 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  332: 370 */       e.printStackTrace();
/*  333:     */     }
/*  334: 372 */     return "";
/*  335:     */   }
/*  336:     */   
/*  337:     */   public String eliminar()
/*  338:     */   {
/*  339: 382 */     if (this.compraCajaChica.getId() > 0) {
/*  340:     */       try
/*  341:     */       {
/*  342: 384 */         this.servicioCompraCajaChica.eliminar(this.compraCajaChica);
/*  343: 385 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  344: 386 */         cargarDatos();
/*  345:     */       }
/*  346:     */       catch (ExcepcionAS2Financiero e)
/*  347:     */       {
/*  348: 388 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  349: 389 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  350:     */       }
/*  351:     */       catch (ExcepcionAS2Compras e)
/*  352:     */       {
/*  353: 392 */         addErrorMessage(getLanguageController().getMensaje("msgs_error_existe_retencion_factura"));
/*  354: 393 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  355:     */       }
/*  356:     */       catch (ExcepcionAS2 e)
/*  357:     */       {
/*  358: 396 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  359: 397 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  360:     */       }
/*  361:     */       catch (Exception e)
/*  362:     */       {
/*  363: 400 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  364: 401 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  365:     */       }
/*  366:     */     } else {
/*  367: 404 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  368:     */     }
/*  369: 406 */     return "";
/*  370:     */   }
/*  371:     */   
/*  372:     */   public String limpiar()
/*  373:     */   {
/*  374: 416 */     setEditado(false);
/*  375:     */     
/*  376: 418 */     this.compraCajaChica = new CompraCajaChica();
/*  377:     */     
/*  378: 420 */     this.compraCajaChica.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  379: 421 */     this.compraCajaChica.setSucursal(AppUtil.getSucursal());
/*  380: 422 */     this.compraCajaChica.setDocumentoReferencia("");
/*  381: 423 */     this.compraCajaChica.setFecha(new Date());
/*  382: 424 */     this.compraCajaChica.setEstado(Estado.APROBADO);
/*  383:     */     
/*  384: 426 */     this.compraCajaChica.setCajaChica(new CajaChica());
/*  385: 427 */     this.compraCajaChica.setValor(BigDecimal.ZERO);
/*  386:     */     
/*  387: 429 */     FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/*  388: 430 */     facturaProveedorSRI.setEstablecimientoRetencion("000");
/*  389: 431 */     facturaProveedorSRI.setPuntoEmisionRetencion("000");
/*  390: 432 */     facturaProveedorSRI.setNumeroRetencion("0");
/*  391: 433 */     facturaProveedorSRI.setEstado(Estado.ELABORADO);
/*  392: 434 */     facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/*  393: 435 */     facturaProveedorSRI.setTipoComprobanteSRI(new TipoComprobanteSRI());
/*  394: 436 */     facturaProveedorSRI.setCompraCajaChica(this.compraCajaChica);
/*  395: 437 */     facturaProveedorSRI.setFechaEmision(new Date());
/*  396: 438 */     facturaProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  397: 439 */     facturaProveedorSRI.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  398: 440 */     this.compraCajaChica.setFacturaProveedorSRI(facturaProveedorSRI);
/*  399: 441 */     this.listaDireccionEmpresa = new ArrayList();
/*  400: 442 */     this.listaTipoComprobanteSRI = null;
/*  401:     */     
/*  402: 444 */     return "";
/*  403:     */   }
/*  404:     */   
/*  405:     */   public String cargarDatos()
/*  406:     */   {
/*  407: 454 */     return "";
/*  408:     */   }
/*  409:     */   
/*  410:     */   public void cargarAutorizaciones()
/*  411:     */   {
/*  412: 458 */     this.listaAutorizacionProveedorSRI = this.servicioEmpresa.listaAutorizacionProveedorSRI(this.compraCajaChica.getEmpresa().getId(), this.compraCajaChica
/*  413: 459 */       .getFecha());
/*  414:     */   }
/*  415:     */   
/*  416:     */   public String emitirRetencion()
/*  417:     */   {
/*  418: 463 */     if ((getCompraCajaChica() != null) && (getCompraCajaChica().getId() != 0))
/*  419:     */     {
/*  420: 464 */       if ((getCompraCajaChica().getEstado() == Estado.ANULADO) || (getCompraCajaChica().getCajaChica().getEstado() == Estado.CONTABILIZADO) || 
/*  421: 465 */         (!getCompraCajaChica().isIndicadorFactura()))
/*  422:     */       {
/*  423: 466 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  424: 467 */         return "";
/*  425:     */       }
/*  426: 469 */       this.compraCajaChica = this.servicioCompraCajaChica.cargarDetalle(this.compraCajaChica.getId());
/*  427: 470 */       completarDocumento();
/*  428: 471 */       FacturaProveedorSRI fpSRI = this.compraCajaChica.getFacturaProveedorSRI();
/*  429: 472 */       if (fpSRI != null)
/*  430:     */       {
/*  431: 473 */         if (fpSRI.isIndicadorReembolso())
/*  432:     */         {
/*  433: 474 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  434: 475 */           return "";
/*  435:     */         }
/*  436: 477 */         if ((!fpSRI.isIndicadorDocumentoElectronico()) && (Integer.parseInt(fpSRI.getNumeroRetencion()) == 0))
/*  437:     */         {
/*  438: 478 */           ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  439: 479 */           HttpSession session = (HttpSession)context.getSession(true);
/*  440: 480 */           session.setAttribute("compraCajaChica", this.compraCajaChica);
/*  441: 481 */           return "/paginas/financiero/SRI/configuracion/facturaProveedorSRI.xhtml?faces-redirect=true&indicadorFactura=false";
/*  442:     */         }
/*  443: 483 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  444:     */       }
/*  445:     */       else
/*  446:     */       {
/*  447: 486 */         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  448: 487 */         HttpSession session = (HttpSession)context.getSession(true);
/*  449: 488 */         session.setAttribute("compraCajaChica", this.compraCajaChica);
/*  450: 489 */         this.compraCajaChica.setFacturaProveedorSRI(new FacturaProveedorSRI());
/*  451: 490 */         return "/paginas/financiero/SRI/configuracion/facturaProveedorSRI.xhtml?faces-redirect=true&indicadorFactura=false";
/*  452:     */       }
/*  453:     */     }
/*  454:     */     else
/*  455:     */     {
/*  456: 493 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  457:     */     }
/*  458: 496 */     return "";
/*  459:     */   }
/*  460:     */   
/*  461:     */   public List<DetalleCompraCajaChica> getDetalleCompraCajaChica()
/*  462:     */   {
/*  463: 506 */     List<DetalleCompraCajaChica> detalle = new ArrayList();
/*  464: 507 */     for (DetalleCompraCajaChica d : getCompraCajaChica().getListaDetalleCompraCajaChica()) {
/*  465: 509 */       if (!d.isEliminado()) {
/*  466: 510 */         detalle.add(d);
/*  467:     */       }
/*  468:     */     }
/*  469: 514 */     return detalle;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void agregarDetalleCompraCajaChicaListener()
/*  473:     */   {
/*  474: 525 */     agregarDetalleCompraCajaChica(getCompraCajaChica(), new CuentaContable(), null);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public String agregarDetalleCompraCajaChica(CompraCajaChica compraCajaChica, CuentaContable cuentaContable, BigDecimal valor)
/*  478:     */   {
/*  479: 537 */     DetalleCompraCajaChica detalleCompraCajaChica = new DetalleCompraCajaChica();
/*  480: 538 */     detalleCompraCajaChica.setCompraCajaChica(compraCajaChica);
/*  481: 539 */     detalleCompraCajaChica.setCuentaContable(cuentaContable);
/*  482: 540 */     detalleCompraCajaChica.setDescripcion(compraCajaChica.getDescripcion());
/*  483: 541 */     if (valor != null) {
/*  484: 542 */       detalleCompraCajaChica.setValor(valor);
/*  485:     */     }
/*  486: 544 */     compraCajaChica.getListaDetalleCompraCajaChica().add(detalleCompraCajaChica);
/*  487:     */     
/*  488: 546 */     return "";
/*  489:     */   }
/*  490:     */   
/*  491:     */   public void buscarCuentaContableListener()
/*  492:     */   {
/*  493:     */     try
/*  494:     */     {
/*  495: 552 */       DetalleCompraCajaChica d = (DetalleCompraCajaChica)this.dtDetalleCompraCajaChica.getRowData();
/*  496: 553 */       String codigoCuentaContable = d.getCuentaContable().getCodigoCuentaTransient();
/*  497:     */       
/*  498: 555 */       CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, 
/*  499: 556 */         AppUtil.getOrganizacion().getIdOrganizacion());
/*  500: 557 */       d.setCuentaContable(cuentaContable);
/*  501: 558 */       d.getCuentaContable().setCodigoCuentaTransient(d.getCuentaContable().getCodigo());
/*  502: 559 */       this.lineaDetalleCompraCajaChica = d;
/*  503:     */     }
/*  504:     */     catch (ExcepcionAS2Financiero e)
/*  505:     */     {
/*  506: 562 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  507:     */     }
/*  508:     */     catch (Exception e)
/*  509:     */     {
/*  510: 565 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/*  511:     */     }
/*  512:     */   }
/*  513:     */   
/*  514:     */   public String eliminarDetalle()
/*  515:     */   {
/*  516: 571 */     DetalleCompraCajaChica dccc = (DetalleCompraCajaChica)this.dtDetalleCompraCajaChica.getRowData();
/*  517: 572 */     dccc.setEliminado(true);
/*  518:     */     
/*  519: 574 */     calcular();
/*  520: 575 */     return "";
/*  521:     */   }
/*  522:     */   
/*  523:     */   private void calcular()
/*  524:     */   {
/*  525: 579 */     this.total = BigDecimal.ZERO;
/*  526: 580 */     this.diferencia = BigDecimal.ZERO;
/*  527: 582 */     for (DetalleCompraCajaChica d : getDetalleCompraCajaChica()) {
/*  528: 583 */       this.total = this.total.add(d.getValor());
/*  529:     */     }
/*  530: 585 */     this.diferencia = this.diferencia.add(this.total);
/*  531: 586 */     this.diferencia = this.diferencia.subtract(this.compraCajaChica.getValor().subtract(this.compraCajaChica.getDescuentoImpuesto()));
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void totalizarListener()
/*  535:     */   {
/*  536: 592 */     FacturaProveedorSRI facturaProveedorSRI = getCompraCajaChica().getFacturaProveedorSRI();
/*  537:     */     
/*  538: 594 */     obtenerResumenImpuestos();
/*  539:     */     
/*  540: 596 */     CuentaContable cuentaContableIVACompras = getRangoImpuesto().getImpuesto().getCuentaContableCompra();
/*  541: 597 */     if (cuentaContableIVACompras != null)
/*  542:     */     {
/*  543: 598 */       cuentaContableIVACompras.setCodigoCuentaTransient(cuentaContableIVACompras.getCodigo());
/*  544: 599 */       if ((getDetalleCompraCajaChica().isEmpty()) && (facturaProveedorSRI.getMontoIva().compareTo(BigDecimal.ZERO) > 0)) {
/*  545: 602 */         agregarDetalleCompraCajaChica(getCompraCajaChica(), cuentaContableIVACompras, null);
/*  546:     */       }
/*  547: 605 */       for (DetalleCompraCajaChica dccc : getDetalleCompraCajaChica()) {
/*  548: 606 */         if ((dccc.getCuentaContable() != null) && (dccc.getCuentaContable().getId() == cuentaContableIVACompras.getId())) {
/*  549: 607 */           dccc.setValor(facturaProveedorSRI.getMontoIva());
/*  550:     */         }
/*  551:     */       }
/*  552:     */     }
/*  553: 612 */     DetalleCompraCajaChica detalleImpuesto = detalleCompensacionSolidaria(getCompraCajaChica().getListaDetalleCompraCajaChica());
/*  554: 613 */     if (getCompraCajaChica().getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0)
/*  555:     */     {
/*  556: 614 */       if (detalleImpuesto != null) {
/*  557: 615 */         detalleImpuesto.setValor(getCompraCajaChica().getDescuentoImpuesto().negate());
/*  558:     */       } else {
/*  559: 617 */         agregarDetalleCompraCajaChica(getCompraCajaChica(), null, getCompraCajaChica().getDescuentoImpuesto().negate());
/*  560:     */       }
/*  561:     */     }
/*  562: 621 */     else if (detalleImpuesto != null) {
/*  563: 622 */       detalleImpuesto.setEliminado(true);
/*  564:     */     }
/*  565: 626 */     BigDecimal valorTotal = BigDecimal.ZERO;
/*  566: 627 */     valorTotal = valorTotal.add(facturaProveedorSRI.getBaseImponibleDiferenteCero());
/*  567: 628 */     valorTotal = valorTotal.add(facturaProveedorSRI.getBaseImponibleTarifaCero());
/*  568: 629 */     valorTotal = valorTotal.add(facturaProveedorSRI.getBaseImponibleNoObjetoIva());
/*  569: 630 */     valorTotal = valorTotal.add(facturaProveedorSRI.getMontoIva());
/*  570: 631 */     valorTotal = valorTotal.add(facturaProveedorSRI.getMontoIce());
/*  571:     */     
/*  572:     */ 
/*  573:     */ 
/*  574: 635 */     this.compraCajaChica.setValor(valorTotal);
/*  575:     */     
/*  576: 637 */     calcular();
/*  577:     */   }
/*  578:     */   
/*  579:     */   public DetalleCompraCajaChica detalleCompensacionSolidaria(List<DetalleCompraCajaChica> listaDetalleCompra)
/*  580:     */   {
/*  581: 641 */     if (listaDetalleCompra.size() > 0) {
/*  582: 642 */       for (DetalleCompraCajaChica dccc : listaDetalleCompra) {
/*  583: 643 */         if ((dccc.getValor().compareTo(BigDecimal.ZERO) < 0) && (!dccc.isEliminado())) {
/*  584: 644 */           return dccc;
/*  585:     */         }
/*  586:     */       }
/*  587:     */     }
/*  588: 648 */     return null;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public void actualizarProveedor(SelectEvent event)
/*  592:     */   {
/*  593: 653 */     Empresa e = this.servicioEmpresa.buscarPorId(Integer.valueOf(((Empresa)event.getObject()).getIdEmpresa()));
/*  594:     */     
/*  595: 655 */     getCompraCajaChica().setEmpresa(e);
/*  596: 656 */     getCompraCajaChica().getFacturaProveedorSRI().setTipoIdentificacion(e.getTipoIdentificacion());
/*  597: 657 */     cargarDirecciones();
/*  598: 658 */     cargarAutorizaciones();
/*  599: 659 */     cargarListaTipoComprobanteSRI();
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void actualizaAutorizacion(AjaxBehaviorEvent event)
/*  603:     */   {
/*  604: 663 */     int idAutorizacionProveedorSRI = ((Integer)((SelectOneMenu)event.getSource()).getValue()).intValue();
/*  605: 664 */     AutorizacionProveedorSRI autorizacionProveedorSRI = this.servicioEmpresa.buscarAutorizacionProveedorSRIPorId(idAutorizacionProveedorSRI);
/*  606: 665 */     this.compraCajaChica.getFacturaProveedorSRI().setEstablecimiento(autorizacionProveedorSRI.getEstablecimiento());
/*  607: 666 */     this.compraCajaChica.getFacturaProveedorSRI().setPuntoEmision(autorizacionProveedorSRI.getPuntoEmision());
/*  608: 667 */     this.compraCajaChica.getFacturaProveedorSRI().setAutorizacion(autorizacionProveedorSRI.getAutorizacion());
/*  609: 668 */     this.compraCajaChica.getFacturaProveedorSRI().setIndicadorFacturaElectronica(autorizacionProveedorSRI.isIndicadorFacturaElectronica());
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void cargarDirecciones()
/*  613:     */   {
/*  614: 672 */     if (getCompraCajaChica().getEmpresa() != null)
/*  615:     */     {
/*  616: 673 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getCompraCajaChica().getEmpresa().getId());
/*  617: 674 */       if (this.listaDireccionEmpresa.size() > 0)
/*  618:     */       {
/*  619: 676 */         boolean direccionPrincipal = false;
/*  620: 677 */         for (DireccionEmpresa direccionEmpresa : this.listaDireccionEmpresa) {
/*  621: 678 */           if (direccionEmpresa.isIndicadorDireccionPrincipal())
/*  622:     */           {
/*  623: 679 */             getCompraCajaChica().setDireccionEmpresa(direccionEmpresa);
/*  624: 680 */             direccionPrincipal = true;
/*  625: 681 */             break;
/*  626:     */           }
/*  627:     */         }
/*  628: 684 */         if (!direccionPrincipal) {
/*  629: 685 */           getCompraCajaChica().setDireccionEmpresa((DireccionEmpresa)this.listaDireccionEmpresa.get(0));
/*  630:     */         }
/*  631:     */       }
/*  632:     */     }
/*  633:     */     else
/*  634:     */     {
/*  635: 689 */       this.listaDireccionEmpresa = new ArrayList();
/*  636:     */     }
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void seleccionar()
/*  640:     */   {
/*  641: 694 */     this.lineaDetalleCompraCajaChica = ((DetalleCompraCajaChica)this.dtDetalleCompraCajaChica.getRowData());
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void cargarCuentaContable(SelectEvent event)
/*  645:     */   {
/*  646: 702 */     this.cuentaContable = ((CuentaContable)event.getObject());
/*  647: 703 */     this.lineaDetalleCompraCajaChica.setCuentaContable(this.cuentaContable);
/*  648: 704 */     this.lineaDetalleCompraCajaChica.getCuentaContable().setCodigoCuentaTransient(this.lineaDetalleCompraCajaChica.getCuentaContable().getCodigo());
/*  649:     */   }
/*  650:     */   
/*  651:     */   private void obtenerResumenImpuestos()
/*  652:     */   {
/*  653: 709 */     FacturaProveedorSRI facturaProveedorSRI = this.compraCajaChica.getFacturaProveedorSRI();
/*  654:     */     
/*  655: 711 */     BigDecimal montoIva = FuncionesUtiles.porcentaje(facturaProveedorSRI.getBaseImponibleDiferenteCero(), 
/*  656: 712 */       getRangoImpuesto().getPorcentajeImpuesto(), 2);
/*  657:     */     
/*  658: 714 */     montoIva = FuncionesUtiles.redondearBigDecimal(montoIva, 2);
/*  659:     */     
/*  660: 716 */     facturaProveedorSRI.setIdentificacionProveedor(this.compraCajaChica.getEmpresa().getIdentificacion());
/*  661: 717 */     facturaProveedorSRI.setNombreProveedor(this.compraCajaChica.getEmpresa().getNombreFiscal());
/*  662: 723 */     if (facturaProveedorSRI.getFechaRegistro() == null) {
/*  663: 724 */       facturaProveedorSRI.setFechaRegistro(FuncionesUtiles.obtenerFechaFinal());
/*  664:     */     }
/*  665: 727 */     facturaProveedorSRI.setFechaEmision(this.compraCajaChica.getFecha());
/*  666: 728 */     facturaProveedorSRI.setMontoIva(montoIva);
/*  667:     */   }
/*  668:     */   
/*  669:     */   public List<AutorizacionProveedorSRI> autocompletarAutorizacionProveedorSRI(String consulta)
/*  670:     */   {
/*  671: 734 */     List<AutorizacionProveedorSRI> lista = new ArrayList();
/*  672: 736 */     if (this.compraCajaChica.getEmpresa() != null)
/*  673:     */     {
/*  674: 738 */       TipoComprobanteSRI tipoComprobanteSRI = this.compraCajaChica.getFacturaProveedorSRI().getTipoComprobanteSRI();
/*  675:     */       try
/*  676:     */       {
/*  677: 741 */         FacturaProveedorSRI facturaProveedorSRI = this.compraCajaChica.getFacturaProveedorSRI();
/*  678: 742 */         if (((!facturaProveedorSRI.getTipoIdentificacion().isIndicadorValidarIdentificacion()) || 
/*  679: 743 */           (facturaProveedorSRI.getTipoIdentificacion().getLongitudMaxima() != 13)) && (tipoComprobanteSRI != null) && 
/*  680: 744 */           (this.compraCajaChica.getFacturaProveedorSRI().isIndicadorLiquidacionCompra()))
/*  681:     */         {
/*  682: 749 */           Documento documento = (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.LIQUIDACION_COMPRA, this.compraCajaChica.getIdOrganizacion()).get(0);
/*  683: 750 */           AutorizacionDocumentoSRI autorizacionDocumentoSRI = this.servicioDocumento.cargarDocumentoConAutorizacion(documento, 
/*  684: 751 */             AppUtil.getPuntoDeVenta(), facturaProveedorSRI.getFechaEmision());
/*  685:     */           
/*  686: 753 */           AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  687: 754 */           autorizacionProveedorSRI.setAutorizacion(autorizacionDocumentoSRI.getAutorizacion());
/*  688: 755 */           autorizacionProveedorSRI.setEstablecimiento(AppUtil.getSucursal().getCodigo());
/*  689: 756 */           autorizacionProveedorSRI.setPuntoEmision(AppUtil.getPuntoDeVenta().getCodigo());
/*  690: 757 */           autorizacionProveedorSRI.setFechaHasta(autorizacionDocumentoSRI.getSecuencia().getFechaHasta());
/*  691:     */           
/*  692:     */ 
/*  693:     */ 
/*  694: 761 */           lista.add(autorizacionProveedorSRI);
/*  695:     */         }
/*  696:     */         else
/*  697:     */         {
/*  698: 764 */           int idEmpresa = this.compraCajaChica.getEmpresa().getId();
/*  699: 765 */           lista = this.servicioEmpresa.obtenerListaComboAutorizacionSRI(idEmpresa, consulta, this.compraCajaChica.getFecha());
/*  700: 766 */           if (lista.size() == 0) {
/*  701: 767 */             addErrorMessage(getLanguageController().getMensaje("msg_info_no_existe_autorizacion_sri"));
/*  702:     */           }
/*  703:     */         }
/*  704:     */       }
/*  705:     */       catch (ExcepcionAS2 e)
/*  706:     */       {
/*  707: 772 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  708: 773 */         LOG.error("ERROR AL CARGAR LAS AUTORIZACIONES DEL PROVEEDOR/LIQUIDACION DE COMPRA", e);
/*  709:     */       }
/*  710:     */     }
/*  711:     */     else
/*  712:     */     {
/*  713: 777 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  714:     */     }
/*  715: 780 */     return lista;
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void actualizarAutorizacionProveedorSRI(SelectEvent event)
/*  719:     */   {
/*  720: 785 */     AutorizacionProveedorSRI autorizacionProveedorSRI = (AutorizacionProveedorSRI)event.getObject();
/*  721: 787 */     if (autorizacionProveedorSRI != null)
/*  722:     */     {
/*  723: 788 */       this.compraCajaChica.getFacturaProveedorSRI().setEstablecimiento(autorizacionProveedorSRI.getEstablecimiento());
/*  724: 789 */       this.compraCajaChica.getFacturaProveedorSRI().setPuntoEmision(autorizacionProveedorSRI.getPuntoEmision());
/*  725: 790 */       this.compraCajaChica.getFacturaProveedorSRI().setAutorizacion(autorizacionProveedorSRI.getAutorizacion());
/*  726: 791 */       this.compraCajaChica.getFacturaProveedorSRI().setIndicadorFacturaElectronica(autorizacionProveedorSRI.isIndicadorFacturaElectronica());
/*  727:     */     }
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void actualizarTipoComprobanteSRI()
/*  731:     */   {
/*  732:     */     try
/*  733:     */     {
/*  734: 798 */       this.servicioFacturaProveedorSRI.actualizarTipoComprobanteSRI(getCompraCajaChica().getFacturaProveedorSRI(), 
/*  735: 799 */         getCompraCajaChica().getFacturaProveedorSRI().getTipoComprobanteSRI(), getCompraCajaChica().getFecha());
/*  736:     */     }
/*  737:     */     catch (ExcepcionAS2 e)
/*  738:     */     {
/*  739: 801 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  740: 802 */       LOG.error("ERROR AL CARGAR LA SECUENCIA DE LA LIQUIDACION DE COMPRA", e);
/*  741:     */     }
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void agregarDetalleReembolsoGastos()
/*  745:     */   {
/*  746: 808 */     ReembolsoGastos rg = new ReembolsoGastos();
/*  747: 809 */     rg.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  748: 810 */     rg.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  749: 811 */     rg.setFacturaProveedorSRI(getCompraCajaChica().getFacturaProveedorSRI());
/*  750: 812 */     getCompraCajaChica().getFacturaProveedorSRI().getListaReembolsoGastos().add(rg);
/*  751:     */   }
/*  752:     */   
/*  753:     */   public List<ReembolsoGastos> getListaReembolsoGastos()
/*  754:     */   {
/*  755: 818 */     List<ReembolsoGastos> listaFiltrado = new ArrayList();
/*  756: 819 */     for (ReembolsoGastos rg : getCompraCajaChica().getFacturaProveedorSRI().getListaReembolsoGastos()) {
/*  757: 820 */       if (!rg.isEliminado()) {
/*  758: 821 */         listaFiltrado.add(rg);
/*  759:     */       }
/*  760:     */     }
/*  761: 825 */     return listaFiltrado;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void buscarEmpresaEvent()
/*  765:     */   {
/*  766:     */     try
/*  767:     */     {
/*  768: 830 */       ReembolsoGastos rg = (ReembolsoGastos)this.dtReembolsoGastos.getRowData();
/*  769: 831 */       if (rg.getTipoIdentificacion().isIndicadorValidarIdentificacion()) {
/*  770: 832 */         ValidarIdentificacion.validarIdentificacion(true, rg.getIdentificacionProveedor());
/*  771:     */       }
/*  772:     */     }
/*  773:     */     catch (ExcepcionAS2Identification e)
/*  774:     */     {
/*  775: 836 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  776: 837 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  777: 838 */       e.printStackTrace();
/*  778:     */     }
/*  779:     */   }
/*  780:     */   
/*  781:     */   public void totalizarReembolsoGastos()
/*  782:     */   {
/*  783: 844 */     setTotalBaseImponibleDiferenteCero(BigDecimal.ZERO);
/*  784: 845 */     setTotalBaseImponibleNoObjetoIva(BigDecimal.ZERO);
/*  785: 846 */     setTotalBaseImponibleTarifaCero(BigDecimal.ZERO);
/*  786: 847 */     setTotalBaseExentaIVA(BigDecimal.ZERO);
/*  787: 848 */     setTotalMontoIva(BigDecimal.ZERO);
/*  788: 849 */     setTotalMontoIce(BigDecimal.ZERO);
/*  789: 850 */     setTotalDescuentoImpuesto(BigDecimal.ZERO);
/*  790: 852 */     for (ReembolsoGastos rg : getListaReembolsoGastos()) {
/*  791: 853 */       if (!rg.isEliminado())
/*  792:     */       {
/*  793: 854 */         BigDecimal porcentajeImpuestoParaReembolso = this.servicioImpuesto.getPorcentajeIVA(getCompraCajaChica().getIdOrganizacion(), rg
/*  794: 855 */           .getFechaEmision() != null ? rg.getFechaEmision() : getCompraCajaChica().getFecha());
/*  795: 856 */         rg.setMontoIva(rg.getBaseImponibleDiferenteCero()
/*  796: 857 */           .multiply(porcentajeImpuestoParaReembolso.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)));
/*  797: 858 */         rg.setMontoIva(FuncionesUtiles.redondearBigDecimal(rg.getMontoIva(), 2));
/*  798:     */         
/*  799: 860 */         this.totalBaseImponibleTarifaCero = this.totalBaseImponibleTarifaCero.add(rg.getBaseImponibleTarifaCero());
/*  800: 861 */         this.totalBaseImponibleDiferenteCero = this.totalBaseImponibleDiferenteCero.add(rg.getBaseImponibleDiferenteCero());
/*  801: 862 */         this.totalBaseImponibleNoObjetoIva = this.totalBaseImponibleNoObjetoIva.add(rg.getBaseImponibleNoObjetoIva());
/*  802: 863 */         this.totalBaseExentaIVA = this.totalBaseExentaIVA.add(rg.getBaseExentaIva());
/*  803: 864 */         this.totalMontoIva = this.totalMontoIva.add(rg.getMontoIva());
/*  804: 865 */         this.totalMontoIce = this.totalMontoIce.add(rg.getMontoIce());
/*  805: 866 */         this.totalDescuentoImpuesto = this.totalDescuentoImpuesto.add(rg.getDescuentoImpuesto());
/*  806:     */       }
/*  807:     */     }
/*  808:     */   }
/*  809:     */   
/*  810:     */   public String eliminarDetalleReembolsoGastos()
/*  811:     */   {
/*  812: 874 */     ReembolsoGastos rg = (ReembolsoGastos)this.dtReembolsoGastos.getRowData();
/*  813: 875 */     rg.setEliminado(true);
/*  814: 876 */     return "";
/*  815:     */   }
/*  816:     */   
/*  817:     */   public String getDirectorioDescarga()
/*  818:     */   {
/*  819: 882 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "compra_caja_chica");
/*  820:     */   }
/*  821:     */   
/*  822:     */   public String getNombreArchivo()
/*  823:     */   {
/*  824: 887 */     return this.compraCajaChica.getArchivo();
/*  825:     */   }
/*  826:     */   
/*  827:     */   public void processUpload(FileUploadEvent event)
/*  828:     */   {
/*  829:     */     try
/*  830:     */     {
/*  831: 900 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "compra_caja_chica");
/*  832:     */       
/*  833: 902 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), this.compraCajaChica.getDocumentoReferencia(), uploadDir);
/*  834:     */       
/*  835:     */ 
/*  836: 905 */       HashMap<String, Object> campos = new HashMap();
/*  837: 906 */       campos.put("archivo", fileName);
/*  838: 907 */       this.servicioCompraCajaChica.actualizarAtributoEntidad(this.compraCajaChica, campos);
/*  839:     */       
/*  840: 909 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  841:     */     }
/*  842:     */     catch (Exception e)
/*  843:     */     {
/*  844: 912 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  845: 913 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  846:     */     }
/*  847:     */   }
/*  848:     */   
/*  849:     */   public String eliminarArchivo()
/*  850:     */   {
/*  851: 919 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), this.compraCajaChica.getArchivo());
/*  852: 920 */     this.compraCajaChica.setArchivo(null);
/*  853: 921 */     HashMap<String, Object> campos = new HashMap();
/*  854: 922 */     campos.put("archivo", null);
/*  855: 923 */     this.servicioCompraCajaChica.actualizarAtributoEntidad(this.compraCajaChica, campos);
/*  856: 924 */     return null;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public void actualizarFechaListener()
/*  860:     */   {
/*  861: 928 */     setRangoImpuesto(null);
/*  862: 929 */     totalizarListener();
/*  863:     */   }
/*  864:     */   
/*  865:     */   public String crearProveedor()
/*  866:     */   {
/*  867:     */     try
/*  868:     */     {
/*  869: 940 */       Empresa empresa = this.servicioEmpresa.crearClienteProveedorConDatosBasicos(this.identificacionProveedor, this.tipoIdentificacionProveedor, this.nombreProveedor, this.direccionProveedor, this.telefonoProveedor, 
/*  870: 941 */         AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), this.ciudad, null, this.email, false);
/*  871:     */       
/*  872:     */ 
/*  873: 944 */       this.identificacionProveedor = null;
/*  874: 945 */       this.tipoIdentificacionProveedor = null;
/*  875: 946 */       this.nombreProveedor = null;
/*  876: 947 */       this.direccionProveedor = null;
/*  877: 948 */       this.telefonoProveedor = null;
/*  878: 949 */       this.email = null;
/*  879: 950 */       getCompraCajaChica().setEmpresa(empresa);
/*  880: 951 */       getCompraCajaChica().getFacturaProveedorSRI().setTipoIdentificacion(empresa.getTipoIdentificacion());
/*  881: 952 */       cargarDirecciones();
/*  882: 953 */       cargarAutorizaciones();
/*  883: 954 */       cargarListaTipoComprobanteSRI();
/*  884: 955 */       setRenderProveedorNuevo(false);
/*  885: 956 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  886:     */     }
/*  887:     */     catch (ExcepcionAS2Identification e)
/*  888:     */     {
/*  889: 958 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  890: 959 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  891:     */     }
/*  892:     */     catch (ExcepcionAS2 e)
/*  893:     */     {
/*  894: 961 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  895: 962 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  896:     */     }
/*  897:     */     catch (Exception ex)
/*  898:     */     {
/*  899: 964 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  900: 965 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  901:     */     }
/*  902: 968 */     return "";
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void actualizarListaAutorizacion()
/*  906:     */   {
/*  907: 972 */     this.renderAutorizacionNuevo = true;
/*  908: 973 */     this.listaAutorizacionProveedorSRIProveedor = new ArrayList();
/*  909: 974 */     if (getCompraCajaChica().getEmpresa() != null)
/*  910:     */     {
/*  911: 975 */       AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  912: 976 */       autorizacionProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  913: 977 */       autorizacionProveedorSRI.setEmpresa(getCompraCajaChica().getEmpresa());
/*  914: 978 */       autorizacionProveedorSRI.setActivo(true);
/*  915: 979 */       this.listaAutorizacionProveedorSRIProveedor.add(autorizacionProveedorSRI);
/*  916:     */     }
/*  917:     */     else
/*  918:     */     {
/*  919: 981 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  920:     */     }
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void crearAutorizacion()
/*  924:     */   {
/*  925:     */     try
/*  926:     */     {
/*  927: 988 */       AutorizacionProveedorSRI autorizacionProveedorSRI = (AutorizacionProveedorSRI)getListaAutorizacionProveedorSRIProveedor().get(0);
/*  928: 990 */       if (autorizacionProveedorSRI.isIndicadorFacturaElectronica())
/*  929:     */       {
/*  930: 992 */         HashMap<String, String> filters = new HashMap();
/*  931: 993 */         filters.put("puntoEmision", "=" + autorizacionProveedorSRI.getPuntoEmision());
/*  932: 994 */         filters.put("establecimiento", "=" + autorizacionProveedorSRI.getEstablecimiento());
/*  933: 995 */         filters.put("indicadorFacturaElectronica", "=true");
/*  934: 996 */         filters.put("empresa.idEmpresa", "=" + this.compraCajaChica.getEmpresa().getIdEmpresa());
/*  935: 997 */         filters.put("idOrganizacion", "!=-1");
/*  936:     */         
/*  937: 999 */         List<AutorizacionProveedorSRI> lista = this.servicioAutorizacionProveedorSRI.obtenerListaCombo(AutorizacionProveedorSRI.class, "establecimiento", true, filters);
/*  938:1002 */         if (lista.isEmpty())
/*  939:     */         {
/*  940:1003 */           autorizacionProveedorSRI.setAutorizacion("0000000000000000000000000000000000000");
/*  941:1004 */           autorizacionProveedorSRI.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1999));
/*  942:1005 */           autorizacionProveedorSRI.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 2999));
/*  943:1006 */           autorizacionProveedorSRI.setActivo(true);
/*  944:1007 */           this.servicioAutorizacionProveedorSRI.guardar(autorizacionProveedorSRI);
/*  945:     */         }
/*  946:     */         else
/*  947:     */         {
/*  948:1009 */           autorizacionProveedorSRI = (AutorizacionProveedorSRI)lista.get(0);
/*  949:     */         }
/*  950:     */       }
/*  951:     */       else
/*  952:     */       {
/*  953:1013 */         this.servicioAutorizacionProveedorSRI.guardar(autorizacionProveedorSRI);
/*  954:     */       }
/*  955:1016 */       if ((autorizacionProveedorSRI.isActivo()) && (autorizacionProveedorSRI.getFechaHasta().compareTo(getCompraCajaChica().getFecha()) != -1))
/*  956:     */       {
/*  957:1017 */         getCompraCajaChica().getFacturaProveedorSRI().setAutorizacionProveedorSRI(autorizacionProveedorSRI);
/*  958:1018 */         getCompraCajaChica().getFacturaProveedorSRI().setEstablecimiento(autorizacionProveedorSRI.getEstablecimiento());
/*  959:1019 */         getCompraCajaChica().getFacturaProveedorSRI().setPuntoEmision(autorizacionProveedorSRI.getPuntoEmision());
/*  960:1020 */         if (!autorizacionProveedorSRI.isIndicadorFacturaElectronica()) {
/*  961:1021 */           getCompraCajaChica().getFacturaProveedorSRI().setAutorizacion(autorizacionProveedorSRI.getAutorizacion());
/*  962:     */         } else {
/*  963:1023 */           getCompraCajaChica().getFacturaProveedorSRI().setAutorizacion("");
/*  964:     */         }
/*  965:1025 */         getCompraCajaChica().getFacturaProveedorSRI().setIndicadorFacturaElectronica(autorizacionProveedorSRI.isIndicadorFacturaElectronica());
/*  966:1026 */         getCompraCajaChica().getFacturaProveedorSRI().setPatronAutorizacion(autorizacionProveedorSRI.getPatronAutorizacion());
/*  967:     */       }
/*  968:1028 */       setListaAutorizacionProveedorSRIProveedor(null);
/*  969:1029 */       setRenderAutorizacionNuevo(false);
/*  970:     */     }
/*  971:     */     catch (AS2Exception e)
/*  972:     */     {
/*  973:1031 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  974:1032 */       e.printStackTrace();
/*  975:     */     }
/*  976:     */   }
/*  977:     */   
/*  978:     */   public List<Ciudad> autocompletarCiudad(String consulta)
/*  979:     */   {
/*  980:1037 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/*  981:     */   }
/*  982:     */   
/*  983:     */   public String cargarCabeceraFacturaProveedorXML(FileUploadEvent event)
/*  984:     */   {
/*  985:     */     try
/*  986:     */     {
/*  987:1042 */       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
/*  988:1043 */       Date fecha = null;
/*  989:1044 */       SAXBuilder builder = new SAXBuilder();
/*  990:1045 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  991:     */       
/*  992:1047 */       Document document = builder.build(input);
/*  993:1048 */       Element rootNode = document.getRootElement();
/*  994:1049 */       List<Element> list = rootNode.getChildren("comprobante");
/*  995:1050 */       List<Element> listNumeroAutorizacion = rootNode.getChildren("numeroAutorizacion");
/*  996:1053 */       if ((list == null) || (list.isEmpty()))
/*  997:     */       {
/*  998:1054 */         addErrorMessage(getLanguageController().getMensaje("msg_error_carga_xml_proveedor"));
/*  999:1055 */         return "";
/* 1000:     */       }
/* 1001:1058 */       for (int i = 0; i < list.size(); i++)
/* 1002:     */       {
/* 1003:1060 */         Element node = (Element)list.get(i);
/* 1004:1061 */         String comprobante = node.getText();
/* 1005:1062 */         Document documentString = builder.build(new InputSource(new StringReader(comprobante)));
/* 1006:1063 */         Element rootNodes = documentString.getRootElement();
/* 1007:1064 */         List<Element> infoFactura = rootNodes.getChildren("infoFactura");
/* 1008:1065 */         List<Element> infoTributaria = rootNodes.getChildren("infoTributaria");
/* 1009:1067 */         for (int j = 0; j < infoFactura.size(); j++)
/* 1010:     */         {
/* 1011:1068 */           Element nodeInfoFactura = (Element)infoFactura.get(j);
/* 1012:1069 */           String fechaEmision = nodeInfoFactura.getChildText("fechaEmision");
/* 1013:1070 */           fecha = dateFormat.parse(fechaEmision);
/* 1014:1071 */           getCompraCajaChica().getFacturaProveedorSRI().setFechaEmision(fecha);
/* 1015:     */         }
/* 1016:1074 */         for (int k = 0; k < infoTributaria.size(); k++)
/* 1017:     */         {
/* 1018:1076 */           Element nodeInfoTributaria = (Element)infoTributaria.get(k);
/* 1019:1077 */           String identificacion = nodeInfoTributaria.getChildText("ruc");
/* 1020:1078 */           Empresa empresaPorId = this.servicioEmpresa.buscarEmpresaPorIdentificacion(AppUtil.getOrganizacion().getId(), identificacion);
/* 1021:1079 */           Empresa obtenerDatosProveedor = this.servicioEmpresa.obtenerDatosProveedor(empresaPorId.getId());
/* 1022:1080 */           actualizarProveedor(obtenerDatosProveedor);
/* 1023:1081 */           String secuencial = nodeInfoTributaria.getChildText("secuencial");
/* 1024:1082 */           getCompraCajaChica().getFacturaProveedorSRI().setNumero(secuencial);
/* 1025:1083 */           String estab = nodeInfoTributaria.getChildText("estab");
/* 1026:1084 */           String ptoEmi = nodeInfoTributaria.getChildText("ptoEmi");
/* 1027:1087 */           for (int m = 0; m < listNumeroAutorizacion.size(); m++)
/* 1028:     */           {
/* 1029:1088 */             Element nodeListNumeroAutorizacion = (Element)listNumeroAutorizacion.get(i);
/* 1030:1089 */             String autorizacion = nodeListNumeroAutorizacion.getText();
/* 1031:1090 */             getCompraCajaChica().getFacturaProveedorSRI().setAutorizacion(autorizacion);
/* 1032:     */           }
/* 1033:1094 */           HashMap<String, String> filters = new HashMap();
/* 1034:1095 */           filters.put("puntoEmision", "=" + ptoEmi);
/* 1035:1096 */           filters.put("establecimiento", "=" + estab);
/* 1036:1097 */           filters.put("indicadorFacturaElectronica", "=true");
/* 1037:1098 */           filters.put("empresa.idEmpresa", "=" + getCompraCajaChica().getEmpresa().getIdEmpresa());
/* 1038:1099 */           filters.put("idOrganizacion", "=" + AppUtil.getOrganizacion().getId());
/* 1039:1100 */           List<AutorizacionProveedorSRI> lista = this.servicioAutorizacionProveedorSRI.obtenerListaCombo(AutorizacionProveedorSRI.class, "establecimiento", true, filters);
/* 1040:1101 */           AutorizacionProveedorSRI autorizacionProveedorSRI = null;
/* 1041:1102 */           if (lista.isEmpty())
/* 1042:     */           {
/* 1043:1104 */             autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/* 1044:1105 */             autorizacionProveedorSRI.setEstablecimiento(estab);
/* 1045:1106 */             autorizacionProveedorSRI.setPuntoEmision(ptoEmi);
/* 1046:1107 */             autorizacionProveedorSRI.setIndicadorFacturaElectronica(true);
/* 1047:1108 */             autorizacionProveedorSRI.setActivo(true);
/* 1048:1109 */             autorizacionProveedorSRI.setEmpresa(obtenerDatosProveedor);
/* 1049:1110 */             autorizacionProveedorSRI.setAutorizacion("0000000000000000000000000000000000000");
/* 1050:1111 */             autorizacionProveedorSRI.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1999));
/* 1051:1112 */             autorizacionProveedorSRI.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 2999));
/* 1052:1113 */             autorizacionProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1053:1114 */             this.servicioAutorizacionProveedorSRI.guardar(autorizacionProveedorSRI);
/* 1054:     */           }
/* 1055:     */           else
/* 1056:     */           {
/* 1057:1117 */             autorizacionProveedorSRI = (AutorizacionProveedorSRI)lista.get(0);
/* 1058:     */           }
/* 1059:1120 */           getCompraCajaChica().getFacturaProveedorSRI().setEstablecimiento(estab);
/* 1060:1121 */           getCompraCajaChica().getFacturaProveedorSRI().setPuntoEmision(ptoEmi);
/* 1061:1122 */           getCompraCajaChica().getFacturaProveedorSRI().setIndicadorFacturaElectronica(true);
/* 1062:1123 */           getCompraCajaChica().getFacturaProveedorSRI().setAutorizacionProveedorSRI(autorizacionProveedorSRI);
/* 1063:     */         }
/* 1064:     */       }
/* 1065:     */     }
/* 1066:     */     catch (ExcepcionAS2 e)
/* 1067:     */     {
/* 1068:1128 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 1069:     */     }
/* 1070:     */     catch (Exception e)
/* 1071:     */     {
/* 1072:1131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 1073:1132 */       e.printStackTrace();
/* 1074:     */     }
/* 1075:1134 */     return null;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public void actualizarProveedor(Empresa empresa)
/* 1079:     */   {
/* 1080:1144 */     getCompraCajaChica().setEmpresa(empresa);
/* 1081:1145 */     getCompraCajaChica().getFacturaProveedorSRI().setTipoIdentificacion(empresa.getTipoIdentificacion());
/* 1082:1146 */     getCompraCajaChica().getFacturaProveedorSRI().setPuntoEmision(null);
/* 1083:1147 */     getCompraCajaChica().getFacturaProveedorSRI().setEstablecimiento(null);
/* 1084:1148 */     getCompraCajaChica().getFacturaProveedorSRI().setAutorizacion(null);
/* 1085:1149 */     getCompraCajaChica().getFacturaProveedorSRI().setAutorizacionProveedorSRI(null);
/* 1086:1150 */     cargarDirecciones();
/* 1087:1151 */     cargarListaTipoComprobanteSRI();
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public List<Documento> getListaDocumento()
/* 1091:     */   {
/* 1092:1156 */     if (this.listaDocumento == null) {
/* 1093:     */       try
/* 1094:     */       {
/* 1095:1158 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_PROVEEDOR, 
/* 1096:1159 */           AppUtil.getOrganizacion().getId());
/* 1097:     */       }
/* 1098:     */       catch (ExcepcionAS2 e)
/* 1099:     */       {
/* 1100:1161 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1101:     */       }
/* 1102:     */     }
/* 1103:1164 */     return this.listaDocumento;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public void setListaDocumento(List<Documento> listaDocumento)
/* 1107:     */   {
/* 1108:1168 */     this.listaDocumento = listaDocumento;
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public BigDecimal getDiferencia()
/* 1112:     */   {
/* 1113:1172 */     calcular();
/* 1114:1173 */     return this.diferencia;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public void setDiferencia(BigDecimal diferencia)
/* 1118:     */   {
/* 1119:1177 */     this.diferencia = diferencia;
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public BigDecimal getTotal()
/* 1123:     */   {
/* 1124:1181 */     calcular();
/* 1125:1182 */     return this.total;
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   public void setTotal(BigDecimal total)
/* 1129:     */   {
/* 1130:1186 */     this.total = total;
/* 1131:     */   }
/* 1132:     */   
/* 1133:     */   public CompraCajaChica getCompraCajaChica()
/* 1134:     */   {
/* 1135:1190 */     return this.compraCajaChica;
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public void setCompraCajaChica(CompraCajaChica compraCajaChica)
/* 1139:     */   {
/* 1140:1194 */     this.compraCajaChica = compraCajaChica;
/* 1141:     */   }
/* 1142:     */   
/* 1143:     */   public LazyDataModel<CompraCajaChica> getListaCompraCajaChica()
/* 1144:     */   {
/* 1145:1198 */     return this.listaCompraCajaChica;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public void setListaCompraCajaChica(LazyDataModel<CompraCajaChica> listaCompraCajaChica)
/* 1149:     */   {
/* 1150:1202 */     this.listaCompraCajaChica = listaCompraCajaChica;
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   public DataTable getDtCompraCajaChica()
/* 1154:     */   {
/* 1155:1206 */     return this.dtCompraCajaChica;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public void setDtCompraCajaChica(DataTable dtCompraCajaChica)
/* 1159:     */   {
/* 1160:1210 */     this.dtCompraCajaChica = dtCompraCajaChica;
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public DataTable getDtDetalleCompraCajaChica()
/* 1164:     */   {
/* 1165:1214 */     return this.dtDetalleCompraCajaChica;
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   public void setDtDetalleCompraCajaChica(DataTable dtDetalleCompraCajaChica)
/* 1169:     */   {
/* 1170:1218 */     this.dtDetalleCompraCajaChica = dtDetalleCompraCajaChica;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1174:     */   {
/* 1175:1222 */     return this.listaDireccionEmpresa;
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1179:     */   {
/* 1180:1226 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1181:     */   }
/* 1182:     */   
/* 1183:     */   public List<CajaChica> getListaCajaChica()
/* 1184:     */   {
/* 1185:1230 */     if (this.listaCajaChica == null)
/* 1186:     */     {
/* 1187:1231 */       HashMap<String, String> filtros = new HashMap();
/* 1188:1232 */       filtros.put("estado", Estado.ELABORADO.toString());
/* 1189:1233 */       this.listaCajaChica = this.servicioCajaChica.obtenerListaCombo(null, false, filtros);
/* 1190:     */     }
/* 1191:1235 */     return this.listaCajaChica;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public DetalleCompraCajaChica getLineaDetalleCompraCajaChica()
/* 1195:     */   {
/* 1196:1239 */     return this.lineaDetalleCompraCajaChica;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public void setLineaDetalleCompraCajaChica(DetalleCompraCajaChica lineaDetalleCompraCajaChica)
/* 1200:     */   {
/* 1201:1243 */     this.lineaDetalleCompraCajaChica = lineaDetalleCompraCajaChica;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public CuentaContable getCuentaContable()
/* 1205:     */   {
/* 1206:1247 */     return this.cuentaContable;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public void setCuentaContable(CuentaContable cuentaContable)
/* 1210:     */   {
/* 1211:1251 */     this.cuentaContable = cuentaContable;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public DataTable getDtCuentaContable()
/* 1215:     */   {
/* 1216:1255 */     return this.dtCuentaContable;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 1220:     */   {
/* 1221:1259 */     this.dtCuentaContable = dtCuentaContable;
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public List<AutorizacionProveedorSRI> getListaAutorizacionProveedorSRI()
/* 1225:     */   {
/* 1226:1263 */     if (this.listaAutorizacionProveedorSRI == null) {
/* 1227:1264 */       this.listaAutorizacionProveedorSRI = new ArrayList();
/* 1228:     */     }
/* 1229:1266 */     return this.listaAutorizacionProveedorSRI;
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public void setListaAutorizacionProveedorSRI(List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI)
/* 1233:     */   {
/* 1234:1270 */     this.listaAutorizacionProveedorSRI = listaAutorizacionProveedorSRI;
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public int getIdAutorizacionProveedorSRISeleccionado()
/* 1238:     */   {
/* 1239:1274 */     return this.idAutorizacionProveedorSRISeleccionado;
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   public void setIdAutorizacionProveedorSRISeleccionado(int idAutorizacionProveedorSRISeleccionado)
/* 1243:     */   {
/* 1244:1278 */     this.idAutorizacionProveedorSRISeleccionado = idAutorizacionProveedorSRISeleccionado;
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   public RangoImpuesto getRangoImpuesto()
/* 1248:     */   {
/* 1249:1282 */     if (this.rangoImpuesto == null) {
/* 1250:1283 */       this.rangoImpuesto = this.servicioImpuesto.getRangoRangoImpuestoTributario(this.compraCajaChica.getFecha(), this.compraCajaChica.getIdOrganizacion());
/* 1251:     */     }
/* 1252:1285 */     return this.rangoImpuesto;
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public void setRangoImpuesto(RangoImpuesto rangoImpuesto)
/* 1256:     */   {
/* 1257:1289 */     this.rangoImpuesto = rangoImpuesto;
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public List<TipoComprobanteSRI> getListaTipoComprobanteSRI()
/* 1261:     */   {
/* 1262:1293 */     return this.listaTipoComprobanteSRI;
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public void setListaTipoComprobanteSRI(List<TipoComprobanteSRI> listaTipoComprobanteSRI)
/* 1266:     */   {
/* 1267:1297 */     this.listaTipoComprobanteSRI = listaTipoComprobanteSRI;
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 1271:     */   {
/* 1272:1301 */     return this.listaDimensionContableBean;
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 1276:     */   {
/* 1277:1305 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 1281:     */   {
/* 1282:1309 */     return this.listaCuentaContableBean;
/* 1283:     */   }
/* 1284:     */   
/* 1285:     */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 1286:     */   {
/* 1287:1313 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   public DataTable getDtReembolsoGastos()
/* 1291:     */   {
/* 1292:1317 */     return this.dtReembolsoGastos;
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public void setDtReembolsoGastos(DataTable dtReembolsoGastos)
/* 1296:     */   {
/* 1297:1321 */     this.dtReembolsoGastos = dtReembolsoGastos;
/* 1298:     */   }
/* 1299:     */   
/* 1300:     */   public BigDecimal getTotalBaseImponibleDiferenteCero()
/* 1301:     */   {
/* 1302:1325 */     return this.totalBaseImponibleDiferenteCero;
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   public void setTotalBaseImponibleDiferenteCero(BigDecimal totalBaseImponibleDiferenteCero)
/* 1306:     */   {
/* 1307:1329 */     this.totalBaseImponibleDiferenteCero = totalBaseImponibleDiferenteCero;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public BigDecimal getTotalBaseImponibleNoObjetoIva()
/* 1311:     */   {
/* 1312:1333 */     return this.totalBaseImponibleNoObjetoIva;
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public void setTotalBaseImponibleNoObjetoIva(BigDecimal totalBaseImponibleNoObjetoIva)
/* 1316:     */   {
/* 1317:1337 */     this.totalBaseImponibleNoObjetoIva = totalBaseImponibleNoObjetoIva;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public BigDecimal getTotalBaseImponibleTarifaCero()
/* 1321:     */   {
/* 1322:1341 */     return this.totalBaseImponibleTarifaCero;
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public void setTotalBaseImponibleTarifaCero(BigDecimal totalBaseImponibleTarifaCero)
/* 1326:     */   {
/* 1327:1345 */     this.totalBaseImponibleTarifaCero = totalBaseImponibleTarifaCero;
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public List<TipoIdentificacion> getListaTipoIdentificacionCombo()
/* 1331:     */   {
/* 1332:1349 */     HashMap<String, String> filters = new HashMap();
/* 1333:1350 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 1334:1351 */     if (this.listaTipoIdentificacionCombo == null) {
/* 1335:1352 */       this.listaTipoIdentificacionCombo = this.servicioTipoIdentificacion.obtenerListaCombo("codigo", true, filters);
/* 1336:     */     }
/* 1337:1354 */     return this.listaTipoIdentificacionCombo;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public void setListaTipoIdentificacionCombo(List<TipoIdentificacion> listaTipoIdentificacionCombo)
/* 1341:     */   {
/* 1342:1358 */     this.listaTipoIdentificacionCombo = listaTipoIdentificacionCombo;
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   public BigDecimal getTotalBaseExentaIVA()
/* 1346:     */   {
/* 1347:1362 */     return this.totalBaseExentaIVA;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public void setTotalBaseExentaIVA(BigDecimal totalBaseExentaIVA)
/* 1351:     */   {
/* 1352:1366 */     this.totalBaseExentaIVA = totalBaseExentaIVA;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public BigDecimal getTotalMontoIva()
/* 1356:     */   {
/* 1357:1370 */     return this.totalMontoIva;
/* 1358:     */   }
/* 1359:     */   
/* 1360:     */   public void setTotalMontoIva(BigDecimal totalMontoIva)
/* 1361:     */   {
/* 1362:1374 */     this.totalMontoIva = totalMontoIva;
/* 1363:     */   }
/* 1364:     */   
/* 1365:     */   public BigDecimal getTotalMontoIce()
/* 1366:     */   {
/* 1367:1378 */     return this.totalMontoIce;
/* 1368:     */   }
/* 1369:     */   
/* 1370:     */   public void setTotalMontoIce(BigDecimal totalMontoIce)
/* 1371:     */   {
/* 1372:1382 */     this.totalMontoIce = totalMontoIce;
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public void cargarListaTipoComprobanteSRI()
/* 1376:     */   {
/* 1377:1386 */     this.listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(this.compraCajaChica.getEmpresa().getTipoIdentificacion());
/* 1378:     */   }
/* 1379:     */   
/* 1380:     */   public String confirmarListener()
/* 1381:     */   {
/* 1382:1390 */     return anularRetencion();
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public String anularRetencion()
/* 1386:     */   {
/* 1387:1394 */     if ((getCompraCajaChica() != null) && (getCompraCajaChica().getId() != 0))
/* 1388:     */     {
/* 1389:1395 */       if ((getCompraCajaChica().getEstado().equals(Estado.ANULADO)) || (getCompraCajaChica().getCajaChica().getAsiento() != null)) {
/* 1390:1396 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1391:     */       } else {
/* 1392:     */         try
/* 1393:     */         {
/* 1394:1399 */           this.compraCajaChica = this.servicioCompraCajaChica.cargarDetalle(this.compraCajaChica.getId());
/* 1395:1400 */           FacturaProveedorSRI fpSRI = this.compraCajaChica.getFacturaProveedorSRI();
/* 1396:1401 */           if ((fpSRI != null) && (fpSRI.isIndicadorRetencionEmitida()))
/* 1397:     */           {
/* 1398:1403 */             if (fpSRI.getEstado().equals(Estado.ANULADO))
/* 1399:     */             {
/* 1400:1404 */               addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1401:1405 */               return "";
/* 1402:     */             }
/* 1403:1408 */             if (fpSRI.isIndicadorReembolso())
/* 1404:     */             {
/* 1405:1409 */               addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1406:1410 */               return "";
/* 1407:     */             }
/* 1408:1413 */             this.servicioPeriodo.buscarPorFecha(fpSRI.getFechaEmisionRetencion(), this.compraCajaChica.getIdOrganizacion(), DocumentoBase.CAJA_CHICA);
/* 1409:1416 */             if (!fpSRI.getEstado().equals(Estado.EN_ESPERA))
/* 1410:     */             {
/* 1411:1417 */               this.servicioFacturaProveedor.anularRetencion(fpSRI);
/* 1412:     */             }
/* 1413:     */             else
/* 1414:     */             {
/* 1415:1419 */               addErrorMessage(getLanguageController().getMensaje("msg_retencion_en_espera"));
/* 1416:1420 */               return "";
/* 1417:     */             }
/* 1418:     */           }
/* 1419:     */           else
/* 1420:     */           {
/* 1421:1423 */             addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1422:     */           }
/* 1423:     */         }
/* 1424:     */         catch (ExcepcionAS2Compras e)
/* 1425:     */         {
/* 1426:1426 */           e.printStackTrace();
/* 1427:1427 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1428:1428 */           LOG.error(e);
/* 1429:     */         }
/* 1430:     */         catch (ExcepcionAS2Financiero e)
/* 1431:     */         {
/* 1432:1430 */           e.printStackTrace();
/* 1433:1431 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1434:     */         }
/* 1435:     */         catch (ExcepcionAS2 e)
/* 1436:     */         {
/* 1437:1433 */           e.printStackTrace();
/* 1438:1434 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1439:     */         }
/* 1440:     */       }
/* 1441:     */     }
/* 1442:     */     else {
/* 1443:1438 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1444:     */     }
/* 1445:1441 */     return "";
/* 1446:     */   }
/* 1447:     */   
/* 1448:     */   private void completarDocumento()
/* 1449:     */   {
/* 1450:1445 */     if (this.compraCajaChica.getFacturaProveedorSRI().getDocumento() == null)
/* 1451:     */     {
/* 1452:     */       List<Documento> listaDocumento;
/* 1453:     */       try
/* 1454:     */       {
/* 1455:1448 */         listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RETENCION_PROVEEDOR, 
/* 1456:1449 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 1457:     */       }
/* 1458:     */       catch (ExcepcionAS2 e)
/* 1459:     */       {
/* 1460:     */         List<Documento> listaDocumento;
/* 1461:1451 */         listaDocumento = new ArrayList();
/* 1462:     */       }
/* 1463:1453 */       if (listaDocumento.size() > 0) {
/* 1464:1454 */         this.compraCajaChica.getFacturaProveedorSRI().setDocumento((Documento)listaDocumento.get(0));
/* 1465:     */       }
/* 1466:     */     }
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   public void cargarListaTipoComprobanteSRIReembolso()
/* 1470:     */   {
/* 1471:1460 */     ReembolsoGastos rg = (ReembolsoGastos)this.dtReembolsoGastos.getRowData();
/* 1472:1461 */     if ((rg != null) && (rg.getTipoIdentificacion() != null))
/* 1473:     */     {
/* 1474:1462 */       List<TipoComprobanteSRI> listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(rg.getTipoIdentificacion());
/* 1475:1463 */       rg.setListaTipoComprobanteSRI(listaTipoComprobanteSRI);
/* 1476:     */     }
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   public void enviarMail()
/* 1480:     */   {
/* 1481:     */     try
/* 1482:     */     {
/* 1483:1469 */       if (this.compraCajaChica.getFacturaProveedorSRI() != null)
/* 1484:     */       {
/* 1485:1470 */         this.compraCajaChica.setFacturaProveedorSRI(this.servicioFacturaProveedorSRI.cargarDetalle(this.compraCajaChica.getFacturaProveedorSRI().getId()));
/* 1486:1471 */         if (((this.compraCajaChica.getFacturaProveedorSRI().getDocumento() != null) && 
/* 1487:1472 */           (!this.compraCajaChica.getFacturaProveedorSRI().getDocumento().isIndicadorDocumentoElectronico())) || 
/* 1488:1473 */           (this.compraCajaChica.getFacturaProveedorSRI().getEstado().equals(Estado.ANULADO)) || 
/* 1489:1474 */           (this.compraCajaChica.getFacturaProveedorSRI().getEstado().equals(Estado.EN_ESPERA)) || 
/* 1490:1475 */           (this.compraCajaChica.getFacturaProveedorSRI().getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 1491:1476 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1492:     */         } else {
/* 1493:1478 */           this.servicioFacturaProveedorSRI.enviarMail(this.compraCajaChica.getFacturaProveedorSRI(), this.mails);
/* 1494:     */         }
/* 1495:     */       }
/* 1496:     */       else
/* 1497:     */       {
/* 1498:1481 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1499:     */       }
/* 1500:     */     }
/* 1501:     */     catch (ExcepcionAS2 e)
/* 1502:     */     {
/* 1503:1484 */       addErrorMessage(this.compraCajaChica.getFacturaProveedorSRI().getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 1504:     */     }
/* 1505:     */   }
/* 1506:     */   
/* 1507:     */   public void establecerMail()
/* 1508:     */   {
/* 1509:1490 */     if ((getCompraCajaChica() != null) && (getCompraCajaChica().getId() > 0)) {
/* 1510:     */       try
/* 1511:     */       {
/* 1512:1493 */         this.compraCajaChica = this.servicioCompraCajaChica.cargarDetalle(this.compraCajaChica.getId());
/* 1513:1494 */         String recogeEmail = this.compraCajaChica.getFacturaProveedorSRI().getEmail();
/* 1514:1495 */         if ((recogeEmail == null) || (recogeEmail.isEmpty()))
/* 1515:     */         {
/* 1516:1496 */           this.mails = "";
/* 1517:     */         }
/* 1518:     */         else
/* 1519:     */         {
/* 1520:1499 */           String[] divideEmail = recogeEmail.split(";");
/* 1521:1500 */           if (divideEmail.length > 0) {
/* 1522:1501 */             this.mails = divideEmail[0].trim();
/* 1523:     */           } else {
/* 1524:1503 */             this.mails = recogeEmail.trim();
/* 1525:     */           }
/* 1526:     */         }
/* 1527:     */       }
/* 1528:     */       catch (Exception e)
/* 1529:     */       {
/* 1530:1508 */         e.printStackTrace();
/* 1531:1509 */         LOG.info("NO SE ESTABLECIO EL CORREO:", e);
/* 1532:     */       }
/* 1533:     */     }
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public String getMails()
/* 1537:     */   {
/* 1538:1516 */     return this.mails;
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public void setMails(String mails)
/* 1542:     */   {
/* 1543:1520 */     this.mails = mails;
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public String cargarRetencion(boolean corrigeDatos)
/* 1547:     */   {
/* 1548:1525 */     if ((getCompraCajaChica() != null) && (getCompraCajaChica().getId() != 0))
/* 1549:     */     {
/* 1550:1526 */       if (getCompraCajaChica().getEstado() == Estado.ANULADO)
/* 1551:     */       {
/* 1552:1527 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1553:1528 */         return null;
/* 1554:     */       }
/* 1555:     */       try
/* 1556:     */       {
/* 1557:1531 */         this.compraCajaChica = this.servicioCompraCajaChica.cargarInformacionSRI(Integer.valueOf(this.compraCajaChica.getId()));
/* 1558:1532 */         completarDocumento();
/* 1559:1533 */         FacturaProveedorSRI fpSRI = this.compraCajaChica.getFacturaProveedorSRI();
/* 1560:1534 */         if (fpSRI != null)
/* 1561:     */         {
/* 1562:1535 */           if (fpSRI.isIndicadorReembolso())
/* 1563:     */           {
/* 1564:1536 */             addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1565:1537 */             return null;
/* 1566:     */           }
/* 1567:1545 */           if (corrigeDatos) {
/* 1568:1546 */             return corregirRetencion();
/* 1569:     */           }
/* 1570:1548 */           return emitirRetencion();
/* 1571:     */         }
/* 1572:     */       }
/* 1573:     */       catch (ExcepcionAS2Compras e)
/* 1574:     */       {
/* 1575:1552 */         return null;
/* 1576:     */       }
/* 1577:     */     }
/* 1578:     */     else
/* 1579:     */     {
/* 1580:1555 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1581:     */     }
/* 1582:1558 */     return null;
/* 1583:     */   }
/* 1584:     */   
/* 1585:     */   public String corregirRetencion()
/* 1586:     */   {
/* 1587:1562 */     FacturaProveedorSRI fpSRI = this.compraCajaChica.getFacturaProveedorSRI();
/* 1588:1563 */     if (fpSRI.isIndicadorRetencionEmitida())
/* 1589:     */     {
/* 1590:1565 */       if ((!fpSRI.isIndicadorDocumentoElectronico()) || ((fpSRI.getMensajeSRI() != null) && (
/* 1591:1566 */         (fpSRI.getMensajeSRI().toLowerCase().contains("guardado")) || (fpSRI.getMensajeSRI().toLowerCase().contains("autorizado")))))
/* 1592:     */       {
/* 1593:1567 */         corregirDatos(true);
/* 1594:1568 */         return "/paginas/financiero/SRI/configuracion/facturaProveedorSRI.xhtml?faces-redirect=true&indicadorFactura=false";
/* 1595:     */       }
/* 1596:1570 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1597:     */     }
/* 1598:     */     else
/* 1599:     */     {
/* 1600:1573 */       if (fpSRI.getMensajeSRI().toLowerCase().contains("error secuencial registrado"))
/* 1601:     */       {
/* 1602:1575 */         corregirDatos(false);
/* 1603:1576 */         return "/paginas/financiero/SRI/configuracion/facturaProveedorSRI.xhtml?faces-redirect=true";
/* 1604:     */       }
/* 1605:1578 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1606:     */     }
/* 1607:1582 */     return null;
/* 1608:     */   }
/* 1609:     */   
/* 1610:     */   private void corregirDatos(boolean traCorregirDatos)
/* 1611:     */   {
/* 1612:1587 */     this.compraCajaChica.getFacturaProveedorSRI().setTraCorregirDatos(traCorregirDatos);
/* 1613:1588 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/* 1614:1589 */     HttpSession session = (HttpSession)context.getSession(true);
/* 1615:1590 */     session.setAttribute("compraCajaChica", this.compraCajaChica);
/* 1616:1591 */     session.setAttribute("compraCajaChica", this.compraCajaChica);
/* 1617:     */   }
/* 1618:     */   
/* 1619:     */   public StreamedContent getXMLSRI()
/* 1620:     */   {
/* 1621:1595 */     if ((this.compraCajaChica != null) && (this.compraCajaChica.getId() != 0) && (this.compraCajaChica.getFacturaProveedorSRI() != null))
/* 1622:     */     {
/* 1623:1596 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 1624:     */       
/* 1625:     */ 
/* 1626:1599 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.RETENCION.toString();
/* 1627:1600 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 1628:     */       
/* 1629:     */ 
/* 1630:1603 */       String nombreArchivo = this.compraCajaChica.getFacturaProveedorSRI().getEstablecimientoRetencion() + "-" + this.compraCajaChica.getFacturaProveedorSRI().getPuntoEmisionRetencion() + "-" + this.compraCajaChica.getFacturaProveedorSRI().getNumeroRetencion() + "-" + this.compraCajaChica.getFacturaProveedorSRI().getClaveAcceso() + ".xml";
/* 1631:     */       
/* 1632:1605 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 1633:     */       try
/* 1634:     */       {
/* 1635:1607 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 1636:     */       }
/* 1637:     */       catch (FileNotFoundException e)
/* 1638:     */       {
/* 1639:1609 */         e.printStackTrace();
/* 1640:1610 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1641:1611 */         return null;
/* 1642:     */       }
/* 1643:     */     }
/* 1644:1614 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1645:1615 */     return null;
/* 1646:     */   }
/* 1647:     */   
/* 1648:     */   public BigDecimal getTotalDescuentoImpuesto()
/* 1649:     */   {
/* 1650:1620 */     return this.totalDescuentoImpuesto;
/* 1651:     */   }
/* 1652:     */   
/* 1653:     */   public void setTotalDescuentoImpuesto(BigDecimal totalDescuentoImpuesto)
/* 1654:     */   {
/* 1655:1624 */     this.totalDescuentoImpuesto = totalDescuentoImpuesto;
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   public boolean isRenderProveedorNuevo()
/* 1659:     */   {
/* 1660:1628 */     return this.renderProveedorNuevo;
/* 1661:     */   }
/* 1662:     */   
/* 1663:     */   public void setRenderProveedorNuevo(boolean renderProveedorNuevo)
/* 1664:     */   {
/* 1665:1632 */     this.renderProveedorNuevo = renderProveedorNuevo;
/* 1666:     */   }
/* 1667:     */   
/* 1668:     */   public boolean isRenderAutorizacionNuevo()
/* 1669:     */   {
/* 1670:1636 */     return this.renderAutorizacionNuevo;
/* 1671:     */   }
/* 1672:     */   
/* 1673:     */   public void setRenderAutorizacionNuevo(boolean renderAutorizacionNuevo)
/* 1674:     */   {
/* 1675:1640 */     this.renderAutorizacionNuevo = renderAutorizacionNuevo;
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   public String getIdentificacionProveedor()
/* 1679:     */   {
/* 1680:1644 */     return this.identificacionProveedor;
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   public void setIdentificacionProveedor(String identificacionProveedor)
/* 1684:     */   {
/* 1685:1648 */     this.identificacionProveedor = identificacionProveedor;
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   public TipoIdentificacion getTipoIdentificacionProveedor()
/* 1689:     */   {
/* 1690:1652 */     return this.tipoIdentificacionProveedor;
/* 1691:     */   }
/* 1692:     */   
/* 1693:     */   public void setTipoIdentificacionProveedor(TipoIdentificacion tipoIdentificacionProveedor)
/* 1694:     */   {
/* 1695:1656 */     this.tipoIdentificacionProveedor = tipoIdentificacionProveedor;
/* 1696:     */   }
/* 1697:     */   
/* 1698:     */   public String getNombreProveedor()
/* 1699:     */   {
/* 1700:1660 */     return this.nombreProveedor;
/* 1701:     */   }
/* 1702:     */   
/* 1703:     */   public void setNombreProveedor(String nombreProveedor)
/* 1704:     */   {
/* 1705:1664 */     this.nombreProveedor = nombreProveedor;
/* 1706:     */   }
/* 1707:     */   
/* 1708:     */   public String getDireccionProveedor()
/* 1709:     */   {
/* 1710:1668 */     return this.direccionProveedor;
/* 1711:     */   }
/* 1712:     */   
/* 1713:     */   public void setDireccionProveedor(String direccionProveedor)
/* 1714:     */   {
/* 1715:1672 */     this.direccionProveedor = direccionProveedor;
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   public String getTelefonoProveedor()
/* 1719:     */   {
/* 1720:1676 */     return this.telefonoProveedor;
/* 1721:     */   }
/* 1722:     */   
/* 1723:     */   public void setTelefonoProveedor(String telefonoProveedor)
/* 1724:     */   {
/* 1725:1680 */     this.telefonoProveedor = telefonoProveedor;
/* 1726:     */   }
/* 1727:     */   
/* 1728:     */   public Ciudad getCiudad()
/* 1729:     */   {
/* 1730:1684 */     return this.ciudad;
/* 1731:     */   }
/* 1732:     */   
/* 1733:     */   public void setCiudad(Ciudad ciudad)
/* 1734:     */   {
/* 1735:1688 */     this.ciudad = ciudad;
/* 1736:     */   }
/* 1737:     */   
/* 1738:     */   public String getEmail()
/* 1739:     */   {
/* 1740:1692 */     return this.email;
/* 1741:     */   }
/* 1742:     */   
/* 1743:     */   public void setEmail(String email)
/* 1744:     */   {
/* 1745:1696 */     this.email = email;
/* 1746:     */   }
/* 1747:     */   
/* 1748:     */   public List<AutorizacionProveedorSRI> getListaAutorizacionProveedorSRIProveedor()
/* 1749:     */   {
/* 1750:1700 */     return this.listaAutorizacionProveedorSRIProveedor;
/* 1751:     */   }
/* 1752:     */   
/* 1753:     */   public void setListaAutorizacionProveedorSRIProveedor(List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRIProveedor)
/* 1754:     */   {
/* 1755:1704 */     this.listaAutorizacionProveedorSRIProveedor = listaAutorizacionProveedorSRIProveedor;
/* 1756:     */   }
/* 1757:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.CompraCajaChicaBean
 * JD-Core Version:    0.7.0.1
 */