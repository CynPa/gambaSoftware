/*    1:     */ package com.asinfo.as2.compras.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.ReporteEstadoCuentaFactura;
/*    4:     */ import com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion;
/*    5:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    6:     */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGastoImportacion;
/*    7:     */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*    8:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    9:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   10:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*   11:     */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   12:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   13:     */ import com.asinfo.as2.controller.LanguageController;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   15:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   19:     */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   20:     */ import com.asinfo.as2.entities.CentroCosto;
/*   21:     */ import com.asinfo.as2.entities.Ciudad;
/*   22:     */ import com.asinfo.as2.entities.CondicionPago;
/*   23:     */ import com.asinfo.as2.entities.CuentaContable;
/*   24:     */ import com.asinfo.as2.entities.CuentaPorPagar;
/*   25:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   26:     */ import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
/*   27:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   28:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   29:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   30:     */ import com.asinfo.as2.entities.DimensionContable;
/*   31:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   32:     */ import com.asinfo.as2.entities.Documento;
/*   33:     */ import com.asinfo.as2.entities.Empresa;
/*   34:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   35:     */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   36:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   37:     */ import com.asinfo.as2.entities.GastoImportacion;
/*   38:     */ import com.asinfo.as2.entities.GastoProductoFacturaProveedor;
/*   39:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor;
/*   40:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   41:     */ import com.asinfo.as2.entities.Organizacion;
/*   42:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   43:     */ import com.asinfo.as2.entities.Producto;
/*   44:     */ import com.asinfo.as2.entities.Proveedor;
/*   45:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   46:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   47:     */ import com.asinfo.as2.entities.RegistroPeso;
/*   48:     */ import com.asinfo.as2.entities.Ruta;
/*   49:     */ import com.asinfo.as2.entities.Secuencia;
/*   50:     */ import com.asinfo.as2.entities.Sucursal;
/*   51:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   52:     */ import com.asinfo.as2.entities.TipoOperacion;
/*   53:     */ import com.asinfo.as2.entities.Transportista;
/*   54:     */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*   55:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   56:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   57:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   58:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   59:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   60:     */ import com.asinfo.as2.enumeraciones.ExportOption;
/*   61:     */ import com.asinfo.as2.enumeraciones.FormaPagoFleteEnum;
/*   62:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   63:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   64:     */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*   65:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   66:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   67:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*   68:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*   69:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*   70:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/*   71:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*   72:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   73:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   74:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   75:     */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*   76:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   77:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   78:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   79:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*   80:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   81:     */ import com.asinfo.as2.util.AppUtil;
/*   82:     */ import com.asinfo.as2.util.RutaArchivo;
/*   83:     */ import com.asinfo.as2.utils.DatosSRI;
/*   84:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   85:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   86:     */ import java.io.PrintStream;
/*   87:     */ import java.math.BigDecimal;
/*   88:     */ import java.util.ArrayList;
/*   89:     */ import java.util.Collection;
/*   90:     */ import java.util.Date;
/*   91:     */ import java.util.HashMap;
/*   92:     */ import java.util.Iterator;
/*   93:     */ import java.util.List;
/*   94:     */ import java.util.Map;
/*   95:     */ import javax.ejb.EJB;
/*   96:     */ import javax.faces.bean.ManagedProperty;
/*   97:     */ import javax.faces.component.html.HtmlInputText;
/*   98:     */ import javax.faces.context.ExternalContext;
/*   99:     */ import javax.faces.context.FacesContext;
/*  100:     */ import javax.faces.context.PartialViewContext;
/*  101:     */ import javax.faces.event.AjaxBehaviorEvent;
/*  102:     */ import javax.faces.model.SelectItem;
/*  103:     */ import javax.servlet.http.HttpSession;
/*  104:     */ import org.apache.log4j.Logger;
/*  105:     */ import org.primefaces.component.datatable.DataTable;
/*  106:     */ import org.primefaces.context.RequestContext;
/*  107:     */ import org.primefaces.event.FileUploadEvent;
/*  108:     */ import org.primefaces.event.SelectEvent;
/*  109:     */ import org.primefaces.event.ToggleEvent;
/*  110:     */ import org.primefaces.model.LazyDataModel;
/*  111:     */ import org.primefaces.model.SortOrder;
/*  112:     */ 
/*  113:     */ public class FacturaProveedorBaseBean
/*  114:     */   extends DocumentoBaseProveedorBean
/*  115:     */ {
/*  116:     */   private static final long serialVersionUID = -5086395407201884353L;
/*  117:     */   @EJB
/*  118:     */   protected transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  119:     */   @EJB
/*  120:     */   protected transient ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  121:     */   @EJB
/*  122:     */   protected transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  123:     */   @EJB
/*  124:     */   protected transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  125:     */   @EJB
/*  126:     */   protected transient ServicioCentroCosto servicioCentroCosto;
/*  127:     */   @EJB
/*  128:     */   protected transient ServicioSRI servicioSRI;
/*  129:     */   @EJB
/*  130:     */   protected transient ServicioSecuencia servicioSecuencia;
/*  131:     */   @EJB
/*  132:     */   protected transient ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  133:     */   @EJB
/*  134:     */   protected transient ServicioCuentaContable servicioCuentaContable;
/*  135:     */   @EJB
/*  136:     */   protected transient ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  137:     */   @EJB
/*  138:     */   protected transient ServicioTipoOperacion servicioTipoOperacion;
/*  139:     */   @EJB
/*  140:     */   protected transient ServicioGastoImportacion servicioGastoImportacion;
/*  141:     */   @EJB
/*  142:     */   protected transient ServicioDocumento servicioDocumento;
/*  143:     */   @EJB
/*  144:     */   protected transient ServicioRegistroPeso servicioRegistroPeso;
/*  145:     */   @EJB
/*  146:     */   protected transient ServicioPeriodo servicioPeriodo;
/*  147:     */   @EJB
/*  148:     */   private ServicioReporteCompra servicioReporteCompra;
/*  149:     */   @EJB
/*  150:     */   protected ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  151:     */   @EJB
/*  152:     */   protected transient ServicioCiudad servicioCiudad;
/*  153:     */   @EJB
/*  154:     */   protected transient ServicioGenerico<AutorizacionProveedorSRI> servicioAutorizacionProveedorSRI;
/*  155:     */   protected FacturaProveedor facturaProveedor;
/*  156:     */   protected LazyDataModel<FacturaProveedor> listaFacturaProveedor;
/*  157:     */   protected List<Documento> listaDocumentoFactura;
/*  158:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  159: 165 */   protected List<PedidoProveedor> listaPedidoProveedorPorFacturar = new ArrayList();
/*  160:     */   private List<SelectItem> listaExportOpcion;
/*  161:     */   private List<SelectItem> listaTipoProrroteo;
/*  162:     */   private FacturaProveedorImportacion facturaProveedorSeleccionado;
/*  163:     */   private List<GastoImportacion> listaGastoImportacion;
/*  164:     */   private List<CondicionPago> listaCondicionPago;
/*  165:     */   private List<TipoComprobanteSRI> listaTipoComprobanteSRI;
/*  166:     */   private List<TipoOperacion> listaTipoOperacion;
/*  167:     */   private String proveedorSeleccionado;
/*  168: 174 */   protected List<String> listaRegistroPesoPorLiquidar = new ArrayList();
/*  169: 175 */   protected Map<String, RegistroPeso> mapaRegistroPesoPorLiquidar = new HashMap();
/*  170: 177 */   protected List<RecepcionProveedor> listaRecepcionProveedorPendientes = new ArrayList();
/*  171: 179 */   protected List<RecepcionProveedor> listaRecepcionProveedorSeleccionados = new ArrayList();
/*  172: 181 */   protected List<RecepcionProveedor> listaRecepcionProveedorAsignados = new ArrayList();
/*  173:     */   private PedidoProveedor pedidoProveedor;
/*  174:     */   protected List<String> listaRegistroPesoSeleccionados;
/*  175:     */   private List<ReporteEstadoCuentaFactura> listaEstadoCuenta;
/*  176: 187 */   private BigDecimal totalDebito = BigDecimal.ZERO;
/*  177:     */   private DataTable dtListaReporte;
/*  178: 189 */   private BigDecimal totalCredito = BigDecimal.ZERO;
/*  179:     */   private DataTable dtFacturaProveedor;
/*  180:     */   private DataTable dtDetalleFacturaProveedor;
/*  181:     */   private DataTable dtImpuestoDetalleFacturaProveedor;
/*  182:     */   private DataTable dtCuentasPorPagar;
/*  183:     */   private DataTable dtGastoImportacion;
/*  184:     */   private DataTable dtDetalleFacturaProveedorServicios;
/*  185:     */   private DataTable dtGastoProductoFacturaProveedor;
/*  186:     */   private DataTable dtDetallePedidoPendienteFacturaProveedor;
/*  187:     */   private DataTable dtPedidoPendienteFacturaProveedor;
/*  188:     */   private DataTable dtCuentaContable;
/*  189: 203 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  190:     */   private boolean indicadorIngresoPrecioTotal;
/*  191:     */   private String mails;
/*  192:     */   protected Integer idFacturaProveedor;
/*  193: 213 */   private GastoProductoFacturaProveedor gastoProductoFacturaProveedorSeleccionado = new GastoProductoFacturaProveedor();
/*  194:     */   private CentroCosto centroCosto;
/*  195:     */   @ManagedProperty("#{listaProductoBean}")
/*  196:     */   private ListaProductoBean listaProductoBean;
/*  197:     */   @ManagedProperty("#{listaCuentaContableBean}")
/*  198:     */   private ListaCuentaContableBean listaCuentaContableBean;
/*  199:     */   @ManagedProperty("#{listaDimensionContableBean}")
/*  200:     */   private ListaDimensionContableBean listaDimensionContableBean;
/*  201:     */   private Integer idRecepcionProveedor;
/*  202: 231 */   protected List<SelectItem> listaFormaPagoSRI = new ArrayList();
/*  203:     */   
/*  204:     */   public void init()
/*  205:     */   {
/*  206:     */     try
/*  207:     */     {
/*  208: 236 */       this.listaProductoBean.setIndicadorCompra(true);
/*  209: 237 */       this.listaProductoBean.setActivo(true);
/*  210: 238 */       this.listaFacturaProveedor = new LazyDataModel()
/*  211:     */       {
/*  212:     */         private static final long serialVersionUID = 1L;
/*  213:     */         
/*  214:     */         public List<FacturaProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  215:     */         {
/*  216: 250 */           List<FacturaProveedor> lista = new ArrayList();
/*  217:     */           
/*  218: 252 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  219:     */           
/*  220: 254 */           FacturaProveedorBaseBean.this.obtenerFiltros(filters);
/*  221: 255 */           lista = FacturaProveedorBaseBean.this.servicioFacturaProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  222:     */           
/*  223: 257 */           FacturaProveedorBaseBean.this.listaFacturaProveedor.setRowCount(FacturaProveedorBaseBean.this.servicioFacturaProveedor.contarPorCriterio(filters));
/*  224: 258 */           return lista;
/*  225:     */         }
/*  226:     */       };
/*  227:     */     }
/*  228:     */     catch (Exception e)
/*  229:     */     {
/*  230: 264 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  231: 265 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  232:     */     }
/*  233:     */   }
/*  234:     */   
/*  235:     */   protected void obtenerFiltros(Map<String, String> filters)
/*  236:     */   {
/*  237: 275 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/*  238: 276 */     filters.put("documento.indicadorDocumentoExterior", "false");
/*  239:     */   }
/*  240:     */   
/*  241:     */   protected void actualizarListaPedidoClienteADespachar()
/*  242:     */   {
/*  243: 284 */     this.listaPedidoProveedorPorFacturar.clear();
/*  244: 286 */     if (getFacturaProveedor().getEmpresa() != null)
/*  245:     */     {
/*  246: 287 */       HashMap<String, String> filters = new HashMap();
/*  247: 288 */       filters.put("empresa.idEmpresa", String.valueOf(getFacturaProveedor().getEmpresa().getId()));
/*  248: 289 */       filters.put("estado", "=" + Estado.APROBADO.toString());
/*  249: 290 */       filters.put("indicadorSolicitudCambioPrecio", "= false");
/*  250: 291 */       this.listaPedidoProveedorPorFacturar = this.servicioPedidoProveedor.obtenerListaCombo("numero", true, filters);
/*  251: 293 */       if ((getPedidoProveedor() != null) && (!this.listaPedidoProveedorPorFacturar.contains(getPedidoProveedor()))) {
/*  252: 294 */         this.listaPedidoProveedorPorFacturar.add(getPedidoProveedor());
/*  253:     */       }
/*  254:     */     }
/*  255:     */   }
/*  256:     */   
/*  257:     */   protected void actualizarListaRegistroPesoPorLiquidar()
/*  258:     */   {
/*  259: 300 */     this.listaRegistroPesoPorLiquidar.clear();
/*  260: 301 */     this.mapaRegistroPesoPorLiquidar.clear();
/*  261: 302 */     this.listaRegistroPesoSeleccionados = null;
/*  262: 304 */     if (getFacturaProveedor().getEmpresa() != null)
/*  263:     */     {
/*  264: 305 */       List<RegistroPeso> lista = this.servicioRegistroPeso.obtenerListaRegistroPesoPendientesPorLiquidar(getFacturaProveedor().getEmpresa());
/*  265: 306 */       for (RegistroPeso registroPeso : lista)
/*  266:     */       {
/*  267: 307 */         this.mapaRegistroPesoPorLiquidar.put(registroPeso.getNumero() + "-" + registroPeso.getNumeroGuiaRemision(), registroPeso);
/*  268: 308 */         this.listaRegistroPesoPorLiquidar.add(registroPeso.getNumero() + "-" + registroPeso.getNumeroGuiaRemision());
/*  269:     */       }
/*  270: 311 */       for (DetalleFacturaProveedor detalleFacturaProveedor : this.facturaProveedor.getListaDetalleFacturaProveedor()) {
/*  271: 312 */         if ((!detalleFacturaProveedor.isEliminado()) && (!detalleFacturaProveedor.getListaRegistroPesoLiquidados().isEmpty())) {
/*  272: 314 */           if (((RegistroPeso)detalleFacturaProveedor.getListaRegistroPesoLiquidados().get(0)).getTransportista().getEmpresa().getId() != getFacturaProveedor().getEmpresa().getId()) {
/*  273: 315 */             detalleFacturaProveedor.setEliminado(true);
/*  274:     */           }
/*  275:     */         }
/*  276:     */       }
/*  277:     */     }
/*  278:     */   }
/*  279:     */   
/*  280:     */   public String editarPadre()
/*  281:     */     throws ExcepcionAS2Compras, ExcepcionAS2Financiero
/*  282:     */   {
/*  283: 327 */     if ((getFacturaProveedor() != null) && (getFacturaProveedor().getId() > 0))
/*  284:     */     {
/*  285: 328 */       this.facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.facturaProveedor.getId()));
/*  286: 329 */       this.servicioFacturaProveedor.esEditable(this.facturaProveedor);
/*  287:     */       
/*  288: 331 */       cargarDirecciones();
/*  289:     */       
/*  290: 333 */       cargarListaTipoComprobanteSRI();
/*  291:     */       
/*  292: 335 */       actualizarListaRegistroPesoPorLiquidar();
/*  293:     */       
/*  294: 337 */       this.listaFormaPagoSRI = null;
/*  295: 338 */       getListaFormaPagoSRI();
/*  296:     */       
/*  297: 340 */       setEditado(true);
/*  298:     */     }
/*  299:     */     else
/*  300:     */     {
/*  301: 343 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  302:     */     }
/*  303: 346 */     return "";
/*  304:     */   }
/*  305:     */   
/*  306:     */   public String guardar()
/*  307:     */   {
/*  308:     */     try
/*  309:     */     {
/*  310: 358 */       if ((this.facturaProveedor.getFacturaProveedorSRI() != null) && (!this.facturaProveedor.getFacturaProveedorSRI().getNumero().matches("\\d+")))
/*  311:     */       {
/*  312: 359 */         addErrorMessage(getLanguageController().getMensaje("msg_mensaje_error_numero_factura_numero"));
/*  313:     */       }
/*  314:     */       else
/*  315:     */       {
/*  316: 362 */         this.servicioFacturaProveedorSRI.actualizarFacturaProveedorSRI(this.facturaProveedor);
/*  317: 363 */         this.servicioFacturaProveedor.guardar(this.facturaProveedor);
/*  318:     */         
/*  319: 365 */         this.idFacturaProveedor = Integer.valueOf(this.facturaProveedor.getIdFacturaProveedor());
/*  320:     */         
/*  321: 367 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  322:     */         
/*  323: 369 */         limpiar();
/*  324:     */         
/*  325: 371 */         return "facturaProveedor.xhtml?faces-redirect=true&idFacturaProveedor=" + this.idFacturaProveedor;
/*  326:     */       }
/*  327:     */     }
/*  328:     */     catch (ExcepcionAS2Financiero e)
/*  329:     */     {
/*  330: 374 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  331: 375 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  332:     */     }
/*  333:     */     catch (ExcepcionAS2Compras e)
/*  334:     */     {
/*  335: 379 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  336: 380 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  337: 381 */       e.printStackTrace();
/*  338:     */     }
/*  339:     */     catch (ExcepcionAS2 e)
/*  340:     */     {
/*  341: 384 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  342: 385 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  343:     */     }
/*  344:     */     catch (AS2Exception e)
/*  345:     */     {
/*  346: 388 */       this.exContabilizacion = e;
/*  347: 389 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  348: 390 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  349:     */     }
/*  350:     */     catch (Exception e)
/*  351:     */     {
/*  352: 392 */       addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  353: 393 */       e.printStackTrace();
/*  354:     */     }
/*  355: 396 */     return "";
/*  356:     */   }
/*  357:     */   
/*  358:     */   public String eliminar()
/*  359:     */   {
/*  360: 406 */     if (this.facturaProveedor.getId() > 0) {
/*  361:     */       try
/*  362:     */       {
/*  363: 409 */         this.servicioFacturaProveedor.anular(this.facturaProveedor);
/*  364: 410 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  365:     */       }
/*  366:     */       catch (ExcepcionAS2Financiero e)
/*  367:     */       {
/*  368: 413 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  369: 414 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  370:     */       }
/*  371:     */       catch (ExcepcionAS2Compras e)
/*  372:     */       {
/*  373: 417 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  374: 418 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  375:     */       }
/*  376:     */       catch (ExcepcionAS2 e)
/*  377:     */       {
/*  378: 421 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  379: 422 */         LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  380:     */       }
/*  381:     */       catch (Exception e)
/*  382:     */       {
/*  383: 425 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  384: 426 */         LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/*  385:     */       }
/*  386:     */     } else {
/*  387: 429 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  388:     */     }
/*  389: 431 */     return "";
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void cargarDetallePedidoListener()
/*  393:     */   {
/*  394: 438 */     for (DetalleFacturaProveedor ddc : getFacturaProveedor().getListaDetalleFacturaProveedor())
/*  395:     */     {
/*  396: 439 */       ddc.setEliminado(true);
/*  397: 440 */       for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : ddc.getListaGastoProductoFacturaProveedor()) {
/*  398: 441 */         gastoProductoFacturaProveedor.setEliminado(true);
/*  399:     */       }
/*  400: 443 */       for (ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor : ddc.getListaImpuestoProductoFacturaProveedor()) {
/*  401: 444 */         impuestoProductoFacturaProveedor.setEliminado(true);
/*  402:     */       }
/*  403:     */     }
/*  404: 448 */     if (getPedidoProveedor() != null)
/*  405:     */     {
/*  406: 449 */       this.facturaProveedor.setDescripcion(getPedidoProveedor().getDescripcion());
/*  407:     */       
/*  408:     */ 
/*  409: 452 */       DireccionEmpresa direccionEmpresa = null;
/*  410: 453 */       Object listaDPC = this.servicioPedidoProveedor.obtenerListaDetallePedidoPorFacturar(getPedidoProveedor().getId());
/*  411: 455 */       for (DetallePedidoProveedor dpp : (List)listaDPC)
/*  412:     */       {
/*  413: 457 */         DetalleFacturaProveedor dfp = new DetalleFacturaProveedor();
/*  414: 458 */         dfp.setDetallePedidoProveedor(dpp);
/*  415: 459 */         dfp.setFacturaProveedor(this.facturaProveedor);
/*  416:     */         
/*  417: 461 */         dfp.setCantidad(dpp.getCantidadPorFacturar());
/*  418: 462 */         dfp.setUnidadCompra(dpp.getUnidadCompra());
/*  419: 463 */         dfp.setPrecio(dpp.getPrecio());
/*  420: 465 */         if (this.facturaProveedor.getFacturaProveedorImportacion() == null) {
/*  421: 466 */           dfp.setPorcentajeDescuento(dpp.getPorcentajeDescuento());
/*  422:     */         }
/*  423: 468 */         dfp.setDescuento(dpp.getDescuento());
/*  424: 469 */         dfp.setDescripcion(dpp.getDescripcion());
/*  425: 470 */         getFacturaProveedor().getListaDetalleFacturaProveedor().add(dfp);
/*  426: 471 */         actualizarProducto(dfp, dpp.getProducto());
/*  427: 472 */         direccionEmpresa = dpp.getPedidoProveedor().getDireccionEmpresa();
/*  428:     */       }
/*  429: 474 */       this.facturaProveedor.setDireccionEmpresa(direccionEmpresa);
/*  430: 475 */       this.facturaProveedor.setProyecto(this.pedidoProveedor != null ? this.pedidoProveedor.getProyecto() : null);
/*  431: 476 */       this.facturaProveedor.setPersonaResponsable(this.pedidoProveedor != null ? this.pedidoProveedor.getPersonaResponsable() : null);
/*  432:     */       
/*  433: 478 */       Object filtros = new HashMap();
/*  434: 479 */       ((Map)filtros).put("pedidoProveedor.idPedidoProveedor", getPedidoProveedor().getId() + "");
/*  435: 480 */       List<RecepcionProveedor> listaRecepcionPedido = new ArrayList();
/*  436: 481 */       listaRecepcionPedido = this.servicioRecepcionProveedor.obtenerListaPorPagina(0, 1, null, true, (Map)filtros);
/*  437: 482 */       if (!listaRecepcionPedido.isEmpty()) {
/*  438: 483 */         this.listaRecepcionProveedorAsignados.add(listaRecepcionPedido.get(0));
/*  439:     */       }
/*  440:     */     }
/*  441:     */   }
/*  442:     */   
/*  443:     */   public void cargarRegistroPesoListener()
/*  444:     */   {
/*  445: 489 */     for (DetalleFacturaProveedor ddc : getFacturaProveedor().getListaDetalleFacturaProveedor())
/*  446:     */     {
/*  447: 490 */       ddc.setEliminado(true);
/*  448: 491 */       for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : ddc.getListaGastoProductoFacturaProveedor()) {
/*  449: 492 */         gastoProductoFacturaProveedor.setEliminado(true);
/*  450:     */       }
/*  451: 494 */       for (??? = ddc.getListaImpuestoProductoFacturaProveedor().iterator(); ???.hasNext();)
/*  452:     */       {
/*  453: 494 */         impuestoProductoFacturaProveedor = (ImpuestoProductoFacturaProveedor)???.next();
/*  454: 495 */         impuestoProductoFacturaProveedor.setEliminado(true);
/*  455:     */       }
/*  456:     */     }
/*  457:     */     ImpuestoProductoFacturaProveedor impuestoProductoFacturaProveedor;
/*  458: 499 */     Integer idProductoFlete = ParametrosSistema.getIdProductoFlete(AppUtil.getOrganizacion().getId());
/*  459: 500 */     Producto producto = this.servicioProducto.cargaDetalle(idProductoFlete.intValue());
/*  460:     */     Object mapaDetalleFacturaProveedor;
/*  461: 502 */     if ((getListaRegistroPesoSeleccionados() != null) && (!getListaRegistroPesoSeleccionados().isEmpty()))
/*  462:     */     {
/*  463: 503 */       mapaDetalleFacturaProveedor = new HashMap();
/*  464: 504 */       for (String numero : getListaRegistroPesoSeleccionados())
/*  465:     */       {
/*  466: 505 */         RegistroPeso registroPeso = (RegistroPeso)this.mapaRegistroPesoPorLiquidar.get(numero);
/*  467: 506 */         BigDecimal tarifa = BigDecimal.ZERO;
/*  468: 507 */         BigDecimal valor = BigDecimal.ZERO;
/*  469: 508 */         String key = "";
/*  470: 509 */         if (registroPeso.getRuta().getFormaPagoFlete().equals(FormaPagoFleteEnum.PAGA_X_NUMERO_FLETES))
/*  471:     */         {
/*  472: 510 */           key = key + "1~" + registroPeso.getRuta().getTarifa() + "~";
/*  473: 511 */           tarifa = registroPeso.getRuta().getTarifa();
/*  474: 512 */           valor = BigDecimal.ONE;
/*  475:     */         }
/*  476: 513 */         else if (registroPeso.getRuta().getFormaPagoFlete().equals(FormaPagoFleteEnum.PAGA_X_PESO))
/*  477:     */         {
/*  478: 514 */           key = key + "2~" + registroPeso.getRuta().getTarifa() + "~";
/*  479: 515 */           tarifa = registroPeso.getRuta().getTarifa();
/*  480: 516 */           valor = registroPeso.getPesoNeto();
/*  481:     */         }
/*  482: 518 */         DetalleFacturaProveedor detalle = (DetalleFacturaProveedor)((Map)mapaDetalleFacturaProveedor).get(key);
/*  483: 519 */         if (detalle == null)
/*  484:     */         {
/*  485: 520 */           detalle = new DetalleFacturaProveedor();
/*  486: 521 */           detalle.setFacturaProveedor(this.facturaProveedor);
/*  487:     */           
/*  488: 523 */           detalle.setCantidad(BigDecimal.ZERO);
/*  489: 524 */           detalle.setProducto(producto);
/*  490: 525 */           detalle.setUnidadCompra(producto.getUnidadCompra());
/*  491: 526 */           detalle.setPrecio(tarifa);
/*  492: 527 */           getFacturaProveedor().getListaDetalleFacturaProveedor().add(detalle);
/*  493:     */           
/*  494: 529 */           detalle.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/*  495: 531 */           if (detalle.isIndicadorImpuestos()) {
/*  496:     */             try
/*  497:     */             {
/*  498: 533 */               this.servicioFacturaProveedor.obtenerImpuestosProductos(detalle.getProducto(), detalle);
/*  499:     */             }
/*  500:     */             catch (ExcepcionAS2Inventario e)
/*  501:     */             {
/*  502: 535 */               addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  503:     */             }
/*  504:     */           }
/*  505: 538 */           ((Map)mapaDetalleFacturaProveedor).put(key, detalle);
/*  506:     */         }
/*  507: 540 */         registroPeso.setDetalleFacturaProveedor(detalle);
/*  508: 541 */         detalle.getListaRegistroPesoLiquidados().add(registroPeso);
/*  509: 542 */         detalle.setCantidad(detalle.getCantidad().add(valor));
/*  510:     */       }
/*  511:     */     }
/*  512: 545 */     totalizar();
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void cargarFacturaDesdeRecepcion()
/*  516:     */   {
/*  517: 553 */     if (this.idRecepcionProveedor != null)
/*  518:     */     {
/*  519: 555 */       limpiar();
/*  520:     */       
/*  521:     */ 
/*  522:     */ 
/*  523:     */ 
/*  524:     */ 
/*  525: 561 */       FacturaProveedor facturaProveedorExistente = this.servicioFacturaProveedor.buscarPorRecepcionProveedor(this.idRecepcionProveedor.intValue());
/*  526: 562 */       if (facturaProveedorExistente != null)
/*  527:     */       {
/*  528: 563 */         addInfoMessage(getLanguageController().getMensaje("msg_info_ya_existe_factura_recepcion") + " " + facturaProveedorExistente
/*  529: 564 */           .getNumero());
/*  530:     */       }
/*  531:     */       else
/*  532:     */       {
/*  533: 568 */         RecepcionProveedor recepcionProveedor = this.servicioRecepcionProveedor.cargarDetalleAFacturar(this.idRecepcionProveedor.intValue());
/*  534: 569 */         this.listaRecepcionProveedorAsignados.add(recepcionProveedor);
/*  535:     */         
/*  536: 571 */         this.facturaProveedor.setEmpresa(recepcionProveedor.getEmpresa());
/*  537: 572 */         this.facturaProveedor.setProyecto(recepcionProveedor.getProyecto());
/*  538: 573 */         this.facturaProveedor.setDescripcion(recepcionProveedor.getDescripcion());
/*  539: 574 */         this.facturaProveedor.setIndicadorCreditoTributario(!ParametrosSistema.getFacturaProveedorImpuestoProductoServicioGasto(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue());
/*  540: 576 */         if (recepcionProveedor.getPedidoProveedor() != null)
/*  541:     */         {
/*  542: 577 */           this.facturaProveedor.setCondicionPago(recepcionProveedor.getPedidoProveedor().getCondicionPago());
/*  543: 578 */           this.facturaProveedor.setNumeroCuotas(recepcionProveedor.getPedidoProveedor().getNumeroCuotas());
/*  544: 579 */           this.facturaProveedor.setDireccionEmpresa(recepcionProveedor.getPedidoProveedor().getDireccionEmpresa());
/*  545: 580 */           this.facturaProveedor.setPersonaResponsable(recepcionProveedor.getPedidoProveedor().getPersonaResponsable());
/*  546:     */         }
/*  547:     */         else
/*  548:     */         {
/*  549: 582 */           this.facturaProveedor.setCondicionPago(recepcionProveedor.getEmpresa().getProveedor().getCondicionPago());
/*  550: 583 */           this.facturaProveedor.setNumeroCuotas(recepcionProveedor.getEmpresa().getProveedor().getNumeroCuotas());
/*  551:     */         }
/*  552: 586 */         this.facturaProveedor.setRecepcionProveedor(recepcionProveedor);
/*  553:     */         
/*  554: 588 */         agregarDetalleDespachoAFactura(recepcionProveedor);
/*  555:     */         
/*  556: 590 */         cargarDirecciones();
/*  557:     */         
/*  558: 592 */         cargarListaTipoComprobanteSRI();
/*  559:     */         
/*  560: 594 */         setEditado(true);
/*  561:     */       }
/*  562: 597 */       this.idRecepcionProveedor = null;
/*  563:     */     }
/*  564:     */   }
/*  565:     */   
/*  566:     */   public String limpiar()
/*  567:     */   {
/*  568: 609 */     setEditado(false);
/*  569:     */     
/*  570: 611 */     crearFacturaProveedor();
/*  571: 612 */     this.listaRegistroPesoPorLiquidar.clear();
/*  572: 613 */     this.mapaRegistroPesoPorLiquidar.clear();
/*  573: 614 */     this.listaRegistroPesoSeleccionados = null;
/*  574:     */     
/*  575: 616 */     this.listaFormaPagoSRI = null;
/*  576: 617 */     this.pedidoProveedor = null;
/*  577: 618 */     this.listaPedidoProveedorPorFacturar = new ArrayList();
/*  578:     */     
/*  579: 620 */     return "";
/*  580:     */   }
/*  581:     */   
/*  582:     */   private void crearFacturaProveedor()
/*  583:     */   {
/*  584: 627 */     this.centroCosto = null;
/*  585:     */     
/*  586: 629 */     this.facturaProveedor = new FacturaProveedor();
/*  587:     */     
/*  588: 631 */     this.facturaProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  589: 632 */     this.facturaProveedor.setSucursal(AppUtil.getSucursal());
/*  590: 633 */     this.facturaProveedor.setNumero("");
/*  591: 634 */     this.facturaProveedor.setFecha(new Date());
/*  592: 635 */     if (getTipoOrganizacion() == TipoOrganizacion.TIPO_ORGANIZACION_ADRIALPETRO) {
/*  593: 636 */       this.facturaProveedor.setFechaRecepcion(this.facturaProveedor.getFecha());
/*  594:     */     }
/*  595: 638 */     this.facturaProveedor.setEstado(Estado.APROBADO);
/*  596: 639 */     this.facturaProveedor.setNumeroCuotas(1);
/*  597:     */     
/*  598: 641 */     crearFacturaProveedorSRI();
/*  599:     */     
/*  600: 643 */     Documento documento = null;
/*  601: 644 */     if ((getListaDocumentoFactura() != null) && (!getListaDocumentoFactura().isEmpty()))
/*  602:     */     {
/*  603: 645 */       documento = (Documento)getListaDocumentoFactura().get(0);
/*  604: 646 */       this.facturaProveedor.setDocumento(documento);
/*  605: 647 */       actualizarDocumento();
/*  606:     */     }
/*  607:     */     else
/*  608:     */     {
/*  609: 649 */       documento = new Documento();
/*  610: 650 */       documento.setSecuencia(new Secuencia());
/*  611: 651 */       this.facturaProveedor.setDocumento(documento);
/*  612:     */     }
/*  613: 654 */     this.listaDireccionEmpresa = new ArrayList();
/*  614: 655 */     this.listaTipoComprobanteSRI = null;
/*  615: 656 */     getListaProductoBean().setProducto(null);
/*  616:     */   }
/*  617:     */   
/*  618:     */   private void crearFacturaProveedorSRI()
/*  619:     */   {
/*  620: 663 */     FacturaProveedorSRI facturaProveedorSRI = new FacturaProveedorSRI();
/*  621: 664 */     facturaProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  622: 665 */     facturaProveedorSRI.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  623: 666 */     facturaProveedorSRI.setNumero("0");
/*  624: 667 */     facturaProveedorSRI.setEstablecimientoRetencion("000");
/*  625: 668 */     facturaProveedorSRI.setPuntoEmisionRetencion("000");
/*  626: 669 */     facturaProveedorSRI.setNumeroRetencion("0");
/*  627: 670 */     facturaProveedorSRI.setAutorizacionRetencion("0000000000");
/*  628: 671 */     facturaProveedorSRI.setFechaEmision(new Date());
/*  629: 672 */     facturaProveedorSRI.setTipoComprobanteSRI(new TipoComprobanteSRI());
/*  630:     */     
/*  631: 674 */     facturaProveedorSRI.setFacturaProveedor(this.facturaProveedor);
/*  632: 675 */     this.facturaProveedor.setFacturaProveedorSRI(facturaProveedorSRI);
/*  633:     */   }
/*  634:     */   
/*  635:     */   public void actualizarProveedorListener(SelectEvent event)
/*  636:     */   {
/*  637: 684 */     Empresa empresa = this.servicioEmpresa.obtenerDatosProveedor(((Empresa)event.getObject()).getIdEmpresa());
/*  638: 685 */     actualizarProveedor(empresa);
/*  639:     */   }
/*  640:     */   
/*  641:     */   public void actualizarProveedor(Empresa empresa)
/*  642:     */   {
/*  643: 690 */     getFacturaProveedor().setEmpresa(empresa);
/*  644: 691 */     if (getFacturaProveedor().getDocumento().isIndicadorDocumentoTributario())
/*  645:     */     {
/*  646: 692 */       getFacturaProveedor().getFacturaProveedorSRI().setTipoIdentificacion(empresa.getTipoIdentificacion());
/*  647: 693 */       getFacturaProveedor().getFacturaProveedorSRI().setPuntoEmision(null);
/*  648: 694 */       getFacturaProveedor().getFacturaProveedorSRI().setEstablecimiento(null);
/*  649: 695 */       getFacturaProveedor().getFacturaProveedorSRI().setAutorizacion(null);
/*  650:     */     }
/*  651: 699 */     if (getFacturaProveedor().getCondicionPago() != null) {
/*  652: 700 */       getFacturaProveedor().setCondicionPago(empresa.getProveedor().getCondicionPago());
/*  653:     */     }
/*  654: 704 */     if (getFacturaProveedor().getNumeroCuotas() == 0) {
/*  655: 705 */       getFacturaProveedor().setNumeroCuotas(empresa.getProveedor().getNumeroCuotas());
/*  656:     */     }
/*  657: 708 */     cargarDirecciones();
/*  658:     */     
/*  659: 710 */     cargarListaTipoComprobanteSRI();
/*  660:     */   }
/*  661:     */   
/*  662:     */   public String actualizarDocumento()
/*  663:     */   {
/*  664: 719 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.facturaProveedor.getDocumento().getId()));
/*  665: 720 */     this.facturaProveedor.setDocumento(documento);
/*  666: 722 */     if (this.facturaProveedor.getDocumento().isIndicadorDocumentoTributario())
/*  667:     */     {
/*  668: 723 */       if (this.facturaProveedor.getFacturaProveedorSRI() == null) {
/*  669: 724 */         crearFacturaProveedorSRI();
/*  670:     */       }
/*  671: 727 */       this.facturaProveedor.getFacturaProveedorSRI().setTipoComprobanteSRI(this.facturaProveedor.getDocumento().getTipoComprobanteSRI());
/*  672: 728 */       if ((documento.getTipoComprobanteSRI() != null) && (
/*  673: 729 */         (documento.getTipoComprobanteSRI().getCodigo() == "41") || (documento.getTipoComprobanteSRI().getCodigo() == "03")))
/*  674:     */       {
/*  675: 730 */         this.facturaProveedor.getFacturaProveedorSRI().setEstablecimiento(AppUtil.getSucursal().getCodigo());
/*  676: 731 */         this.facturaProveedor.getFacturaProveedorSRI().setPuntoEmision(AppUtil.getPuntoDeVenta().getCodigo());
/*  677:     */       }
/*  678:     */     }
/*  679:     */     else
/*  680:     */     {
/*  681: 735 */       this.facturaProveedor.setFacturaProveedorSRI(null);
/*  682:     */     }
/*  683:     */     try
/*  684:     */     {
/*  685: 739 */       this.servicioFacturaProveedor.cargarSecuencia(this.facturaProveedor, null);
/*  686:     */     }
/*  687:     */     catch (ExcepcionAS2 e)
/*  688:     */     {
/*  689: 742 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  690:     */     }
/*  691: 745 */     setSecuenciaEditable(!this.facturaProveedor.getDocumento().isIndicadorBloqueoSecuencia());
/*  692:     */     
/*  693: 747 */     return "";
/*  694:     */   }
/*  695:     */   
/*  696:     */   public String cargarDatos()
/*  697:     */   {
/*  698: 757 */     return "";
/*  699:     */   }
/*  700:     */   
/*  701:     */   public String seleccionar()
/*  702:     */   {
/*  703: 761 */     this.facturaProveedor = ((FacturaProveedor)this.dtFacturaProveedor.getRowData());
/*  704: 762 */     return "";
/*  705:     */   }
/*  706:     */   
/*  707:     */   public void seleccionarDetalle(GastoProductoFacturaProveedor gastoProductoFacturaProveedor)
/*  708:     */   {
/*  709: 766 */     this.gastoProductoFacturaProveedorSeleccionado = gastoProductoFacturaProveedor;
/*  710:     */   }
/*  711:     */   
/*  712:     */   public void seleccionarDimensionContableListener(SelectEvent event)
/*  713:     */   {
/*  714: 771 */     DimensionContable dimension = (DimensionContable)event.getObject();
/*  715:     */     
/*  716: 773 */     String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/*  717: 774 */     if (numeroDimension.equals("1")) {
/*  718: 775 */       this.gastoProductoFacturaProveedorSeleccionado.setDimensionContable1(dimension);
/*  719: 776 */     } else if (numeroDimension.equals("2")) {
/*  720: 777 */       this.gastoProductoFacturaProveedorSeleccionado.setDimensionContable2(dimension);
/*  721: 778 */     } else if (numeroDimension.equals("3")) {
/*  722: 779 */       this.gastoProductoFacturaProveedorSeleccionado.setDimensionContable3(dimension);
/*  723: 780 */     } else if (numeroDimension.equals("4")) {
/*  724: 781 */       this.gastoProductoFacturaProveedorSeleccionado.setDimensionContable4(dimension);
/*  725: 782 */     } else if (numeroDimension.equals("5")) {
/*  726: 783 */       this.gastoProductoFacturaProveedorSeleccionado.setDimensionContable5(dimension);
/*  727:     */     }
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void seleccionarCuentaContable(SelectEvent event)
/*  731:     */   {
/*  732: 790 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/*  733:     */     
/*  734: 792 */     setearCuentaContable(cuentaContable);
/*  735:     */   }
/*  736:     */   
/*  737:     */   protected void setearCuentaContable(CuentaContable cuentaContable)
/*  738:     */   {
/*  739: 796 */     getGastoProductoFacturaProveedorSeleccionado().setCuentaContableGasto(cuentaContable);
/*  740: 797 */     if (!cuentaContable.isIndicadorValidarDimension1()) {
/*  741: 798 */       getGastoProductoFacturaProveedorSeleccionado().setDimensionContable1(null);
/*  742:     */     }
/*  743: 800 */     if (!cuentaContable.isIndicadorValidarDimension2()) {
/*  744: 801 */       getGastoProductoFacturaProveedorSeleccionado().setDimensionContable2(null);
/*  745:     */     }
/*  746: 803 */     if (!cuentaContable.isIndicadorValidarDimension3()) {
/*  747: 804 */       getGastoProductoFacturaProveedorSeleccionado().setDimensionContable3(null);
/*  748:     */     }
/*  749: 806 */     if (!cuentaContable.isIndicadorValidarDimension4()) {
/*  750: 807 */       getGastoProductoFacturaProveedorSeleccionado().setDimensionContable4(null);
/*  751:     */     }
/*  752: 809 */     if (!cuentaContable.isIndicadorValidarDimension5()) {
/*  753: 810 */       getGastoProductoFacturaProveedorSeleccionado().setDimensionContable5(null);
/*  754:     */     }
/*  755:     */   }
/*  756:     */   
/*  757:     */   public String eliminarDetalle()
/*  758:     */   {
/*  759: 821 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)this.dtDetalleFacturaProveedor.getRowData();
/*  760: 822 */     List<DetalleFacturaProveedor> listaDetalleFacturaProveedorEliminar = new ArrayList();
/*  761:     */     int idRecepcionProveedor;
/*  762:     */     Iterator localIterator;
/*  763:     */     DetalleFacturaProveedor detalle;
/*  764: 824 */     if ((dfp.getCantidad().compareTo(BigDecimal.ZERO) > 0) && (!dfp.getListaDetalleRecepcionProveedor().isEmpty()))
/*  765:     */     {
/*  766: 825 */       idRecepcionProveedor = ((DetalleRecepcionProveedor)dfp.getListaDetalleRecepcionProveedor().get(0)).getRecepcionProveedor().getId();
/*  767: 826 */       for (localIterator = getFacturaProveedor().getListaDetalleFacturaProveedor().iterator(); localIterator.hasNext();)
/*  768:     */       {
/*  769: 826 */         detalle = (DetalleFacturaProveedor)localIterator.next();
/*  770: 827 */         if (((DetalleRecepcionProveedor)detalle.getListaDetalleRecepcionProveedor().get(0)).getRecepcionProveedor().getId() == idRecepcionProveedor) {
/*  771: 828 */           listaDetalleFacturaProveedorEliminar.add(detalle);
/*  772:     */         }
/*  773:     */       }
/*  774:     */     }
/*  775:     */     else
/*  776:     */     {
/*  777: 832 */       listaDetalleFacturaProveedorEliminar.add(dfp);
/*  778:     */     }
/*  779: 835 */     for (DetalleFacturaProveedor df : listaDetalleFacturaProveedorEliminar)
/*  780:     */     {
/*  781: 837 */       for (ImpuestoProductoFacturaProveedor gasto : df.getListaImpuestoProductoFacturaProveedor()) {
/*  782: 838 */         gasto.setEliminado(true);
/*  783:     */       }
/*  784: 842 */       for (CuentaPorPagar cxp : df.getFacturaProveedor().getListaCuentaPorPagar()) {
/*  785: 843 */         cxp.setEliminado(true);
/*  786:     */       }
/*  787: 847 */       for (GastoProductoFacturaProveedor gasto : df.getListaGastoProductoFacturaProveedor()) {
/*  788: 848 */         gasto.setEliminado(true);
/*  789:     */       }
/*  790: 851 */       df.setEliminado(true);
/*  791:     */     }
/*  792: 854 */     totalizar();
/*  793:     */     
/*  794: 856 */     return "";
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/*  798:     */   {
/*  799: 861 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)this.dtDetalleFacturaProveedor.getRowData();
/*  800: 862 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  801:     */     try
/*  802:     */     {
/*  803: 865 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.FACTURA_PROVEEDOR);
/*  804: 866 */       if (!producto.isIndicadorCompra()) {
/*  805: 867 */         throw new ExcepcionAS2("msg_producto_no_encontrado", " " + codigo);
/*  806:     */       }
/*  807: 869 */       actualizarProducto(dfp, producto);
/*  808:     */     }
/*  809:     */     catch (ExcepcionAS2 e)
/*  810:     */     {
/*  811: 872 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  812:     */     }
/*  813:     */   }
/*  814:     */   
/*  815:     */   public void actualizarProducto(DetalleFacturaProveedor dfp, Producto producto)
/*  816:     */   {
/*  817: 883 */     if (this.facturaProveedor.getEmpresa() != null)
/*  818:     */     {
/*  819:     */       try
/*  820:     */       {
/*  821: 886 */         for (ImpuestoProductoFacturaProveedor ipfp : dfp.getListaImpuestoProductoFacturaProveedor()) {
/*  822: 887 */           ipfp.setEliminado(true);
/*  823:     */         }
/*  824: 890 */         if (producto.getTipoProducto() == TipoProducto.ARTICULO) {
/*  825: 891 */           for (GastoProductoFacturaProveedor gpfp : dfp.getListaGastoProductoFactura()) {
/*  826: 892 */             gpfp.setEliminado(true);
/*  827:     */           }
/*  828:     */         }
/*  829: 896 */         dfp.setProducto(producto);
/*  830: 897 */         dfp.setUnidadCompra(producto.getUnidadCompra());
/*  831: 898 */         dfp.setFacturaProveedor(this.facturaProveedor);
/*  832: 899 */         dfp.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/*  833: 901 */         if (dfp.isIndicadorImpuestos()) {
/*  834: 902 */           this.servicioFacturaProveedor.obtenerImpuestosProductos(dfp.getProducto(), dfp);
/*  835:     */         }
/*  836:     */         try
/*  837:     */         {
/*  838: 907 */           Integer idZona = null;
/*  839: 908 */           if (this.facturaProveedor.getEmpresa().getProveedor().getListaPrecios() != null)
/*  840:     */           {
/*  841: 909 */             DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.facturaProveedor
/*  842: 910 */               .getEmpresa().getProveedor().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), this.facturaProveedor
/*  843: 911 */               .getFecha(), idZona, this.facturaProveedor.getNumero());
/*  844: 912 */             if (dfp.getPrecio().compareTo(BigDecimal.ZERO) == 0) {
/*  845: 913 */               dfp.setPrecio(dvlp.getPrecioUnitario());
/*  846:     */             }
/*  847:     */           }
/*  848:     */         }
/*  849:     */         catch (ExcepcionAS2 e)
/*  850:     */         {
/*  851: 920 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  852:     */         }
/*  853: 923 */         totalizar();
/*  854:     */       }
/*  855:     */       catch (ExcepcionAS2 e)
/*  856:     */       {
/*  857: 926 */         addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/*  858:     */       }
/*  859:     */     }
/*  860:     */     else
/*  861:     */     {
/*  862: 930 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  863: 931 */       dfp.getProducto().setCodigo("");
/*  864:     */     }
/*  865:     */   }
/*  866:     */   
/*  867:     */   public String confirmarListener()
/*  868:     */   {
/*  869: 937 */     return anularRetencion();
/*  870:     */   }
/*  871:     */   
/*  872:     */   public String anularRetencion()
/*  873:     */   {
/*  874: 941 */     if ((getFacturaProveedor() != null) && (getFacturaProveedor().getId() != 0))
/*  875:     */     {
/*  876: 942 */       if (getFacturaProveedor().getEstado().equals(Estado.ANULADO)) {
/*  877: 943 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  878:     */       } else {
/*  879:     */         try
/*  880:     */         {
/*  881: 946 */           this.facturaProveedor = this.servicioFacturaProveedor.cargarInformacionSRI(Integer.valueOf(this.facturaProveedor.getIdFacturaProveedor()));
/*  882: 947 */           FacturaProveedorSRI fpSRI = this.facturaProveedor.getFacturaProveedorSRI();
/*  883: 948 */           if ((fpSRI != null) && (
/*  884: 949 */             ((fpSRI.getMensajeSRI() != null) && (fpSRI.getMensajeSRI().toLowerCase().contains("error secuencial registrado"))) || 
/*  885: 950 */             (fpSRI.isIndicadorRetencionEmitida())))
/*  886:     */           {
/*  887: 952 */             if (fpSRI.getEstado().equals(Estado.ANULADO))
/*  888:     */             {
/*  889: 953 */               addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  890: 954 */               return "";
/*  891:     */             }
/*  892: 957 */             if (fpSRI.isIndicadorReembolso())
/*  893:     */             {
/*  894: 958 */               addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  895: 959 */               return "";
/*  896:     */             }
/*  897: 962 */             this.servicioPeriodo.buscarPorFecha(fpSRI
/*  898: 963 */               .getFechaEmisionRetencion() == null ? fpSRI.getFechaEmision() : fpSRI.getFechaEmisionRetencion(), this.facturaProveedor
/*  899: 964 */               .getIdOrganizacion(), fpSRI.getDocumento().getDocumentoBase());
/*  900: 966 */             if (!fpSRI.getEstado().equals(Estado.EN_ESPERA))
/*  901:     */             {
/*  902: 967 */               this.servicioFacturaProveedor.anularRetencion(fpSRI);
/*  903:     */             }
/*  904:     */             else
/*  905:     */             {
/*  906: 969 */               addErrorMessage(getLanguageController().getMensaje("msg_retencion_en_espera"));
/*  907: 970 */               return "";
/*  908:     */             }
/*  909:     */           }
/*  910:     */           else
/*  911:     */           {
/*  912: 973 */             addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  913:     */           }
/*  914:     */         }
/*  915:     */         catch (ExcepcionAS2Compras e)
/*  916:     */         {
/*  917: 976 */           LOG.error(e);
/*  918:     */         }
/*  919:     */         catch (ExcepcionAS2Financiero e)
/*  920:     */         {
/*  921: 978 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  922:     */         }
/*  923:     */         catch (ExcepcionAS2 e)
/*  924:     */         {
/*  925: 980 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  926:     */         }
/*  927:     */       }
/*  928:     */     }
/*  929:     */     else {
/*  930: 984 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  931:     */     }
/*  932: 987 */     return "";
/*  933:     */   }
/*  934:     */   
/*  935:     */   private void completarDocumento()
/*  936:     */   {
/*  937: 994 */     if (this.facturaProveedor.getFacturaProveedorSRI().getDocumento() == null)
/*  938:     */     {
/*  939:     */       List<Documento> listaDocumento;
/*  940:     */       try
/*  941:     */       {
/*  942: 997 */         listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.RETENCION_PROVEEDOR, 
/*  943: 998 */           AppUtil.getOrganizacion().getIdOrganizacion());
/*  944:     */       }
/*  945:     */       catch (ExcepcionAS2 e)
/*  946:     */       {
/*  947:     */         List<Documento> listaDocumento;
/*  948:1000 */         listaDocumento = new ArrayList();
/*  949:     */       }
/*  950:1002 */       if (listaDocumento.size() > 0) {
/*  951:1003 */         this.facturaProveedor.getFacturaProveedorSRI().setDocumento((Documento)listaDocumento.get(0));
/*  952:     */       }
/*  953:     */     }
/*  954:     */   }
/*  955:     */   
/*  956:     */   public String emitirRetencion()
/*  957:     */   {
/*  958:1009 */     FacturaProveedorSRI fpSRI = this.facturaProveedor.getFacturaProveedorSRI();
/*  959:1010 */     if ((!fpSRI.isIndicadorDocumentoElectronico()) && (Integer.parseInt(fpSRI.getNumeroRetencion()) == 0))
/*  960:     */     {
/*  961:1011 */       this.facturaProveedor.getFacturaProveedorSRI().setTraCorregirDatos(false);
/*  962:1012 */       ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  963:1013 */       HttpSession session = (HttpSession)context.getSession(true);
/*  964:1014 */       session.setAttribute("facturaProveedor", this.facturaProveedor);
/*  965:1015 */       return "/paginas/financiero/SRI/configuracion/facturaProveedorSRI.xhtml?faces-redirect=true&indicadorFactura=true";
/*  966:     */     }
/*  967:1017 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  968:     */     
/*  969:     */ 
/*  970:1020 */     return "";
/*  971:     */   }
/*  972:     */   
/*  973:     */   public String corregirRetencion()
/*  974:     */   {
/*  975:1024 */     FacturaProveedorSRI fpSRI = this.facturaProveedor.getFacturaProveedorSRI();
/*  976:1025 */     if (fpSRI.isIndicadorRetencionEmitida())
/*  977:     */     {
/*  978:1027 */       if ((!fpSRI.isIndicadorDocumentoElectronico()) || ((fpSRI.getMensajeSRI() != null) && (
/*  979:1028 */         (fpSRI.getMensajeSRI().toLowerCase().contains("guardado")) || (fpSRI.getMensajeSRI().toLowerCase().contains("autorizado")))))
/*  980:     */       {
/*  981:1029 */         this.facturaProveedor.getFacturaProveedorSRI().setTraCorregirDatos(true);
/*  982:1030 */         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  983:1031 */         HttpSession session = (HttpSession)context.getSession(true);
/*  984:1032 */         session.setAttribute("facturaProveedor", this.facturaProveedor);
/*  985:1033 */         return "/paginas/financiero/SRI/configuracion/facturaProveedorSRI.xhtml?faces-redirect=true&indicadorFactura=true";
/*  986:     */       }
/*  987:1035 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  988:     */     }
/*  989:     */     else
/*  990:     */     {
/*  991:1038 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  992:     */     }
/*  993:1041 */     return "";
/*  994:     */   }
/*  995:     */   
/*  996:     */   public String cargarRetencion(boolean corrigeDatos)
/*  997:     */   {
/*  998:1045 */     if ((getFacturaProveedor() != null) && (getFacturaProveedor().getId() != 0))
/*  999:     */     {
/* 1000:1046 */       if ((getFacturaProveedor().getEstado() == Estado.ANULADO) || (!getFacturaProveedor().getDocumento().isIndicadorDocumentoTributario()))
/* 1001:     */       {
/* 1002:1047 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1003:1048 */         return "";
/* 1004:     */       }
/* 1005:     */       try
/* 1006:     */       {
/* 1007:1051 */         this.facturaProveedor = this.servicioFacturaProveedor.cargarInformacionSRI(Integer.valueOf(this.facturaProveedor.getIdFacturaProveedor()));
/* 1008:1052 */         completarDocumento();
/* 1009:1053 */         FacturaProveedorSRI fpSRI = this.facturaProveedor.getFacturaProveedorSRI();
/* 1010:1054 */         if (fpSRI != null)
/* 1011:     */         {
/* 1012:1055 */           if (fpSRI.isIndicadorReembolso())
/* 1013:     */           {
/* 1014:1056 */             addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1015:1057 */             return "";
/* 1016:     */           }
/* 1017:1060 */           if ((fpSRI.getMensajeSRI() != null) && (fpSRI.getMensajeSRI().toLowerCase().contains("error secuencial registrado")))
/* 1018:     */           {
/* 1019:1061 */             addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1020:1062 */             return "";
/* 1021:     */           }
/* 1022:1064 */           if (corrigeDatos) {
/* 1023:1065 */             return corregirRetencion();
/* 1024:     */           }
/* 1025:1067 */           return emitirRetencion();
/* 1026:     */         }
/* 1027:     */       }
/* 1028:     */       catch (ExcepcionAS2Compras e)
/* 1029:     */       {
/* 1030:1071 */         return "";
/* 1031:     */       }
/* 1032:     */     }
/* 1033:     */     else
/* 1034:     */     {
/* 1035:1074 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1036:     */     }
/* 1037:1077 */     return "";
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public void totalizar()
/* 1041:     */   {
/* 1042:     */     try
/* 1043:     */     {
/* 1044:1089 */       for (DetalleFacturaProveedor dfp : getListaDetalleFacturaProveedorServicios()) {
/* 1045:1090 */         if ((dfp.getListaGastoProductoFactura().isEmpty()) && 
/* 1046:1091 */           (dfp.getBaseImponible().add(dfp.getValorImpuestosLinea()).compareTo(BigDecimal.ZERO) > 0)) {
/* 1047:1092 */           agregarGastoProductoFacturaProveedor(dfp);
/* 1048:     */         }
/* 1049:     */       }
/* 1050:1096 */       this.servicioFacturaProveedor.totalizar(getFacturaProveedor());
/* 1051:     */       
/* 1052:     */ 
/* 1053:1099 */       cargarCuentaPorPagar();
/* 1054:     */     }
/* 1055:     */     catch (ExcepcionAS2Compras e)
/* 1056:     */     {
/* 1057:1102 */       LOG.info(e.getErrorMessage(e));
/* 1058:     */     }
/* 1059:     */     catch (Exception e)
/* 1060:     */     {
/* 1061:1104 */       LOG.info(e);
/* 1062:     */     }
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public void cargarCuentaPorPagar()
/* 1066:     */   {
/* 1067:1114 */     this.servicioFacturaProveedor.generarCuentaPorPagar(this.facturaProveedor);
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public void agregarGastoProductoFacturaProveedorListener()
/* 1071:     */   {
/* 1072:1122 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)this.dtDetalleFacturaProveedorServicios.getRowData();
/* 1073:1123 */     agregarGastoProductoFacturaProveedor(dfp);
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public String agregarGastoProductoFacturaProveedor(DetalleFacturaProveedor dfp)
/* 1077:     */   {
/* 1078:1133 */     boolean agregarGPFP = true;
/* 1079:1134 */     if ((dfp.getListaGastoProductoFactura().isEmpty()) && (!dfp.isIndicadorGastoImportacion())) {
/* 1080:     */       try
/* 1081:     */       {
/* 1082:1140 */         this.servicioFacturaProveedor.agregarGastoProductoFacturaProveedor(dfp, getCentroCosto());
/* 1083:     */         
/* 1084:1142 */         agregarGPFP = false;
/* 1085:     */       }
/* 1086:     */       catch (ExcepcionAS2 e)
/* 1087:     */       {
/* 1088:1144 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1089:     */       }
/* 1090:     */     }
/* 1091:1149 */     if (agregarGPFP)
/* 1092:     */     {
/* 1093:1153 */       GastoProductoFacturaProveedor gastoProductoFacturaProveedor = new GastoProductoFacturaProveedor();
/* 1094:1154 */       gastoProductoFacturaProveedor.setDetalleFacturaProveedor(dfp);
/* 1095:1155 */       gastoProductoFacturaProveedor.setCuentaContableGasto(new CuentaContable());
/* 1096:1156 */       dfp.getListaGastoProductoFacturaProveedor().add(gastoProductoFacturaProveedor);
/* 1097:     */     }
/* 1098:1159 */     return "";
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public void actualizarImpuesto()
/* 1102:     */     throws ExcepcionAS2Inventario
/* 1103:     */   {
/* 1104:1170 */     DetalleFacturaProveedor d = (DetalleFacturaProveedor)this.dtDetalleFacturaProveedor.getRowData();
/* 1105:1172 */     for (ImpuestoProductoFacturaProveedor ipfp : d.getListaImpuestoProductoFacturaProveedor()) {
/* 1106:1173 */       ipfp.setEliminado(true);
/* 1107:     */     }
/* 1108:1176 */     if (d.isIndicadorImpuestos()) {
/* 1109:1177 */       this.servicioFacturaProveedor.obtenerImpuestosProductos(d.getProducto(), d);
/* 1110:     */     }
/* 1111:1180 */     totalizar();
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void processUpload(FileUploadEvent event)
/* 1115:     */   {
/* 1116:     */     try
/* 1117:     */     {
/* 1118:1193 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_proveedor");
/* 1119:     */       
/* 1120:1195 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getFacturaProveedor().getNumero(), uploadDir);
/* 1121:     */       
/* 1122:     */ 
/* 1123:1198 */       HashMap<String, Object> campos = new HashMap();
/* 1124:1199 */       campos.put("pdf", fileName);
/* 1125:1200 */       this.servicioFacturaProveedor.actualizarAtributoEntidad(this.facturaProveedor, campos);
/* 1126:     */       
/* 1127:1202 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 1128:     */     }
/* 1129:     */     catch (Exception e)
/* 1130:     */     {
/* 1131:1205 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 1132:1206 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 1133:     */     }
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void processDownload()
/* 1137:     */   {
/* 1138:     */     try
/* 1139:     */     {
/* 1140:1218 */       FacturaProveedor fp = (FacturaProveedor)this.dtFacturaProveedor.getRowData();
/* 1141:1219 */       String fileName = fp.getPdf();
/* 1142:1220 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_proveedor");
/* 1143:1222 */       if (fileName == null) {
/* 1144:1223 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1145:     */       } else {
/* 1146:1225 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 1147:     */       }
/* 1148:     */     }
/* 1149:     */     catch (Exception e)
/* 1150:     */     {
/* 1151:1229 */       e.printStackTrace();
/* 1152:1230 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1153:     */     }
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public List<CentroCosto> autocompletarCentroCosto(String consulta)
/* 1157:     */   {
/* 1158:1242 */     consulta = consulta.toUpperCase();
/* 1159:     */     
/* 1160:1244 */     String sortField = "codigo";
/* 1161:1245 */     HashMap<String, String> filters = new HashMap();
/* 1162:1246 */     filters.put("activo", "true");
/* 1163:     */     
/* 1164:1248 */     List<CentroCosto> lista = new ArrayList();
/* 1165:1250 */     for (CentroCosto centroCosto : this.servicioCentroCosto.obtenerListaCombo(sortField, true, filters)) {
/* 1166:1252 */       if ((centroCosto.getCodigo().toUpperCase().contains(consulta)) || (centroCosto.getNombre().toUpperCase().contains(consulta))) {
/* 1167:1253 */         lista.add(centroCosto);
/* 1168:     */       }
/* 1169:     */     }
/* 1170:1257 */     return lista;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   public List<Ciudad> autocompletarCiudad(String consulta)
/* 1174:     */   {
/* 1175:1261 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public void cargarProducto(Producto producto)
/* 1179:     */   {
/* 1180:1265 */     getListaProductoBean().setProducto(producto);
/* 1181:1266 */     getListaProductoBean().setSaldoProductoLote(null);
/* 1182:1267 */     cargarProducto();
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public void cargarProducto()
/* 1186:     */   {
/* 1187:1271 */     Producto producto = getListaProductoBean().getProducto();
/* 1188:1272 */     if (producto != null)
/* 1189:     */     {
/* 1190:1273 */       DetalleFacturaProveedor detalleFacturaProveedor = new DetalleFacturaProveedor();
/* 1191:1274 */       detalleFacturaProveedor.setCantidad(producto.getTraCantidad());
/* 1192:1275 */       this.facturaProveedor.getListaDetalleFacturaProveedor().add(detalleFacturaProveedor);
/* 1193:1276 */       actualizarProducto(detalleFacturaProveedor, producto);
/* 1194:     */     }
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public List<AutorizacionProveedorSRI> autocompletarAutorizacionProveedorSRI(String consulta)
/* 1198:     */   {
/* 1199:1282 */     List<AutorizacionProveedorSRI> lista = new ArrayList();
/* 1200:1284 */     if (this.facturaProveedor.getEmpresa() != null)
/* 1201:     */     {
/* 1202:1286 */       TipoComprobanteSRI tipoComprobanteSRI = this.facturaProveedor.getFacturaProveedorSRI().getTipoComprobanteSRI();
/* 1203:     */       try
/* 1204:     */       {
/* 1205:1289 */         FacturaProveedorSRI facturaProveedorSRI = this.facturaProveedor.getFacturaProveedorSRI();
/* 1206:1290 */         if (((facturaProveedorSRI.getTipoIdentificacion() == null) || 
/* 1207:1291 */           (!facturaProveedorSRI.getTipoIdentificacion().isIndicadorValidarIdentificacion()) || 
/* 1208:1292 */           (facturaProveedorSRI.getTipoIdentificacion().getLongitudMaxima() != 13)) && (tipoComprobanteSRI != null) && 
/* 1209:1293 */           (this.facturaProveedor.getFacturaProveedorSRI().isIndicadorLiquidacionCompra()))
/* 1210:     */         {
/* 1211:1297 */           Documento documento = (Documento)this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.LIQUIDACION_COMPRA, this.facturaProveedor.getIdOrganizacion()).get(0);
/* 1212:     */           
/* 1213:1299 */           AutorizacionDocumentoSRI autorizacionDocumentoSRI = this.servicioDocumento.cargarDocumentoConAutorizacion(documento, 
/* 1214:1300 */             AppUtil.getPuntoDeVenta(), facturaProveedorSRI.getFechaEmision());
/* 1215:     */           
/* 1216:1302 */           AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/* 1217:1303 */           autorizacionProveedorSRI.setAutorizacion(autorizacionDocumentoSRI.getAutorizacion());
/* 1218:1304 */           autorizacionProveedorSRI.setEstablecimiento(AppUtil.getSucursal().getCodigo());
/* 1219:1305 */           autorizacionProveedorSRI.setPuntoEmision(AppUtil.getPuntoDeVenta().getCodigo());
/* 1220:1306 */           autorizacionProveedorSRI.setFechaHasta(autorizacionDocumentoSRI.getSecuencia().getFechaHasta());
/* 1221:     */           
/* 1222:     */ 
/* 1223:     */ 
/* 1224:1310 */           lista.add(autorizacionProveedorSRI);
/* 1225:     */         }
/* 1226:     */         else
/* 1227:     */         {
/* 1228:1313 */           int idEmpresa = this.facturaProveedor.getEmpresa().getId();
/* 1229:1314 */           lista = this.servicioEmpresa.obtenerListaComboAutorizacionSRI(idEmpresa, consulta, this.facturaProveedor.getFecha());
/* 1230:1315 */           if (lista.size() == 0) {
/* 1231:1316 */             addErrorMessage(getLanguageController().getMensaje("msg_info_no_existe_autorizacion_sri"));
/* 1232:     */           }
/* 1233:     */         }
/* 1234:     */       }
/* 1235:     */       catch (ExcepcionAS2 e)
/* 1236:     */       {
/* 1237:1321 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1238:1322 */         LOG.error("ERROR AL CARGAR LAS AUTORIZACIONES DEL PROVEEDOR/LIQUIDACION DE COMPRA", e);
/* 1239:     */       }
/* 1240:     */     }
/* 1241:     */     else
/* 1242:     */     {
/* 1243:1326 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 1244:     */     }
/* 1245:1329 */     return lista;
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public void actualizarAutorizacionProveedorSRI(SelectEvent event)
/* 1249:     */   {
/* 1250:1334 */     AutorizacionProveedorSRI autorizacionProveedorSRI = (AutorizacionProveedorSRI)event.getObject();
/* 1251:1336 */     if (autorizacionProveedorSRI != null)
/* 1252:     */     {
/* 1253:1337 */       this.facturaProveedor.getFacturaProveedorSRI().setEstablecimiento(autorizacionProveedorSRI.getEstablecimiento());
/* 1254:1338 */       this.facturaProveedor.getFacturaProveedorSRI().setPuntoEmision(autorizacionProveedorSRI.getPuntoEmision());
/* 1255:1339 */       if (!autorizacionProveedorSRI.isIndicadorFacturaElectronica()) {
/* 1256:1340 */         this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion(autorizacionProveedorSRI.getAutorizacion());
/* 1257:     */       } else {
/* 1258:1342 */         this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion("");
/* 1259:     */       }
/* 1260:1344 */       this.facturaProveedor.getFacturaProveedorSRI().setIndicadorFacturaElectronica(autorizacionProveedorSRI.isIndicadorFacturaElectronica());
/* 1261:1345 */       this.facturaProveedor.getFacturaProveedorSRI().setPatronAutorizacion(autorizacionProveedorSRI.getPatronAutorizacion());
/* 1262:     */     }
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public boolean actualizarAutorizacionProveedorSRIInputText()
/* 1266:     */   {
/* 1267:1351 */     boolean autorizacion = true;
/* 1268:1352 */     AutorizacionProveedorSRI autorizacionProveedorSRI = null;
/* 1269:1353 */     if (this.facturaProveedor.getFacturaProveedorSRI() != null) {
/* 1270:1354 */       autorizacionProveedorSRI = this.facturaProveedor.getFacturaProveedorSRI().getAutorizacionProveedorSRI();
/* 1271:     */     }
/* 1272:1356 */     if (autorizacionProveedorSRI != null)
/* 1273:     */     {
/* 1274:1357 */       this.facturaProveedor.getFacturaProveedorSRI().setEstablecimiento(autorizacionProveedorSRI.getEstablecimiento());
/* 1275:1358 */       this.facturaProveedor.getFacturaProveedorSRI().setPuntoEmision(autorizacionProveedorSRI.getPuntoEmision());
/* 1276:1359 */       if (!autorizacionProveedorSRI.isIndicadorFacturaElectronica())
/* 1277:     */       {
/* 1278:1360 */         List<AutorizacionProveedorSRI> lista = autocompletarAutorizacionProveedorSRI(autorizacionProveedorSRI.getEstablecimiento());
/* 1279:1361 */         if (lista.size() == 0)
/* 1280:     */         {
/* 1281:1362 */           addErrorMessage(getLanguageController().getMensaje("msg_info_no_existe_autorizacion_sri"));
/* 1282:1363 */           autorizacion = false;
/* 1283:     */         }
/* 1284:1365 */         for (AutorizacionProveedorSRI aps : lista)
/* 1285:     */         {
/* 1286:1366 */           if ((aps.getEstablecimiento().equals(this.facturaProveedor.getFacturaProveedorSRI().getEstablecimiento())) && 
/* 1287:1367 */             (aps.getPuntoEmision().equals(this.facturaProveedor.getFacturaProveedorSRI().getPuntoEmision())))
/* 1288:     */           {
/* 1289:1368 */             this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion(aps.getAutorizacion());
/* 1290:1369 */             break;
/* 1291:     */           }
/* 1292:1371 */           this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion("");
/* 1293:     */         }
/* 1294:1374 */         if (this.facturaProveedor.getFacturaProveedorSRI().getAutorizacion().length() < 1)
/* 1295:     */         {
/* 1296:1375 */           this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion("");
/* 1297:1376 */           addErrorMessage(getLanguageController().getMensaje("msg_info_no_existe_autorizacion_sri"));
/* 1298:1377 */           autorizacion = false;
/* 1299:     */         }
/* 1300:     */       }
/* 1301:     */       else
/* 1302:     */       {
/* 1303:1380 */         this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion("");
/* 1304:     */       }
/* 1305:1382 */       this.facturaProveedor.getFacturaProveedorSRI().setIndicadorFacturaElectronica(autorizacionProveedorSRI.isIndicadorFacturaElectronica());
/* 1306:1383 */       this.facturaProveedor.getFacturaProveedorSRI().setPatronAutorizacion(autorizacionProveedorSRI.getPatronAutorizacion());
/* 1307:     */     }
/* 1308:1385 */     return autorizacion;
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   public void actualizarTipoComprobanteSRI()
/* 1312:     */   {
/* 1313:     */     try
/* 1314:     */     {
/* 1315:1394 */       this.servicioFacturaProveedorSRI.actualizarTipoComprobanteSRI(this.facturaProveedor.getFacturaProveedorSRI(), this.facturaProveedor
/* 1316:1395 */         .getFacturaProveedorSRI().getTipoComprobanteSRI(), this.facturaProveedor.getFecha());
/* 1317:     */     }
/* 1318:     */     catch (ExcepcionAS2 e)
/* 1319:     */     {
/* 1320:1397 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1321:1398 */       LOG.error("ERROR AL CARGAR LA SECUENCIA DE LA LIQUIDACION DE COMPRA", e);
/* 1322:     */     }
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public void actualizarPorcentajeDescuento()
/* 1326:     */   {
/* 1327:1407 */     totalizar();
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public void cargarDirecciones()
/* 1331:     */   {
/* 1332:1411 */     this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getFacturaProveedor().getEmpresa().getId());
/* 1333:1413 */     if (getFacturaProveedor().getDireccionEmpresa() == null) {
/* 1334:1416 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/* 1335:1417 */         if (de.isIndicadorDireccionPrincipal())
/* 1336:     */         {
/* 1337:1418 */           getFacturaProveedor().setDireccionEmpresa(de);
/* 1338:1419 */           break;
/* 1339:     */         }
/* 1340:     */       }
/* 1341:     */     }
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public String eliminarGastoProductoFacturaProveedor()
/* 1345:     */   {
/* 1346:1432 */     GastoProductoFacturaProveedor gastoProductoFacturaProveedor = (GastoProductoFacturaProveedor)this.dtGastoProductoFacturaProveedor.getRowData();
/* 1347:     */     
/* 1348:1434 */     gastoProductoFacturaProveedor.setEliminado(true);
/* 1349:     */     
/* 1350:1436 */     return "";
/* 1351:     */   }
/* 1352:     */   
/* 1353:     */   public void dateSelectFecha()
/* 1354:     */   {
/* 1355:1440 */     if (this.facturaProveedor.getNumeroCuotas() > 0) {
/* 1356:1441 */       cargarCuentaPorPagar();
/* 1357:     */     }
/* 1358:1445 */     if (this.facturaProveedor.getEmpresa() != null)
/* 1359:     */     {
/* 1360:1446 */       this.facturaProveedor.getFacturaProveedorSRI().setPuntoEmision(null);
/* 1361:1447 */       this.facturaProveedor.getFacturaProveedorSRI().setEstablecimiento(null);
/* 1362:1448 */       this.facturaProveedor.getFacturaProveedorSRI().setAutorizacion(null);
/* 1363:1449 */       this.facturaProveedor.getFacturaProveedorSRI().setAutorizacionProveedorSRI(null);
/* 1364:     */     }
/* 1365:1452 */     actualizarImpuestosListener();
/* 1366:     */   }
/* 1367:     */   
/* 1368:     */   public String agregarDetalle()
/* 1369:     */   {
/* 1370:1462 */     DetalleFacturaProveedor d = new DetalleFacturaProveedor();
/* 1371:1463 */     d.setFacturaProveedor(getFacturaProveedor());
/* 1372:1464 */     d.setProducto(new Producto());
/* 1373:1465 */     getFacturaProveedor().getListaDetalleFacturaProveedor().add(d);
/* 1374:1466 */     return "";
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public List<CuentaPorPagar> getListaCuentaPorPagar()
/* 1378:     */   {
/* 1379:1477 */     List<CuentaPorPagar> lista = new ArrayList();
/* 1380:1479 */     for (CuentaPorPagar cuentaPorPagar : this.facturaProveedor.getListaCuentaPorPagar()) {
/* 1381:1480 */       if (!cuentaPorPagar.isEliminado()) {
/* 1382:1481 */         lista.add(cuentaPorPagar);
/* 1383:     */       }
/* 1384:     */     }
/* 1385:1484 */     return lista;
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public List<DetalleFacturaProveedor> getListaDetalleFacturaProveedor()
/* 1389:     */   {
/* 1390:1493 */     List<DetalleFacturaProveedor> detalle = new ArrayList();
/* 1391:1494 */     for (DetalleFacturaProveedor detalleFacturaProveedor : getFacturaProveedor().getListaDetalleFacturaProveedor()) {
/* 1392:1496 */       if (!detalleFacturaProveedor.isEliminado())
/* 1393:     */       {
/* 1394:1497 */         detalleFacturaProveedor.getValorImpuestosLinea();
/* 1395:1498 */         detalle.add(detalleFacturaProveedor);
/* 1396:     */       }
/* 1397:     */     }
/* 1398:1501 */     return detalle;
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public List<DetalleFacturaProveedor> getListaDetalleFacturaProveedorGastoImportacion()
/* 1402:     */   {
/* 1403:1505 */     List<DetalleFacturaProveedor> detalle = new ArrayList();
/* 1404:1506 */     for (DetalleFacturaProveedor detalleFacturaProveedor : getListaDetalleFacturaProveedor()) {
/* 1405:1507 */       if (detalleFacturaProveedor.isIndicadorGastoImportacion()) {
/* 1406:1508 */         detalle.add(detalleFacturaProveedor);
/* 1407:     */       }
/* 1408:     */     }
/* 1409:1511 */     return detalle;
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   public void actualizarGastoImportacion()
/* 1413:     */   {
/* 1414:1518 */     for (DetalleFacturaProveedor detalleFacturaProveedor : getFacturaProveedor().getListaDetalleFacturaProveedor()) {
/* 1415:1519 */       if (getFacturaProveedor().isIndicadorGastoImportacion() != detalleFacturaProveedor.isIndicadorGastoImportacion())
/* 1416:     */       {
/* 1417:1520 */         detalleFacturaProveedor.setIndicadorGastoImportacion(getFacturaProveedor().isIndicadorGastoImportacion());
/* 1418:1521 */         actualizaDetalleFacturaProveedorGastoImportacion(detalleFacturaProveedor);
/* 1419:     */       }
/* 1420:     */     }
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   public String actualizaDetalleFacturaProveedorGastoImportacion(DetalleFacturaProveedor detalleFacturaProveedor)
/* 1424:     */   {
/* 1425:1528 */     if (detalleFacturaProveedor.isIndicadorGastoImportacion())
/* 1426:     */     {
/* 1427:1529 */       for (GastoProductoFacturaProveedor gastoProductoFacturaProveedor : detalleFacturaProveedor.getListaGastoProductoFacturaProveedor()) {
/* 1428:1530 */         gastoProductoFacturaProveedor.setEliminado(true);
/* 1429:     */       }
/* 1430:     */     }
/* 1431:     */     else
/* 1432:     */     {
/* 1433:1533 */       for (DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacion : detalleFacturaProveedor
/* 1434:1534 */         .getListaDetalleFacturaProveedorImportacion()) {
/* 1435:1535 */         detalleFacturaProveedorImportacion.setEliminado(true);
/* 1436:     */       }
/* 1437:1537 */       detalleFacturaProveedor.setGastoImportacion(null);
/* 1438:1538 */       if (detalleFacturaProveedor.getProducto() != null) {
/* 1439:1539 */         actualizarProducto(detalleFacturaProveedor, detalleFacturaProveedor.getProducto());
/* 1440:     */       }
/* 1441:     */     }
/* 1442:1542 */     return "";
/* 1443:     */   }
/* 1444:     */   
/* 1445:     */   public void establecerMail()
/* 1446:     */   {
/* 1447:1547 */     if ((getFacturaProveedor() != null) && (getFacturaProveedor().getId() > 0)) {
/* 1448:     */       try
/* 1449:     */       {
/* 1450:1550 */         this.facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(this.facturaProveedor.getId()));
/* 1451:1551 */         String recogeEmail = this.facturaProveedor.getFacturaProveedorSRI().getEmail();
/* 1452:1552 */         if ((recogeEmail == null) || (recogeEmail.isEmpty()))
/* 1453:     */         {
/* 1454:1553 */           this.mails = "";
/* 1455:     */         }
/* 1456:     */         else
/* 1457:     */         {
/* 1458:1556 */           String[] divideEmail = recogeEmail.split(";");
/* 1459:1557 */           if (divideEmail.length > 0) {
/* 1460:1558 */             this.mails = divideEmail[0].trim();
/* 1461:     */           } else {
/* 1462:1560 */             this.mails = recogeEmail.trim();
/* 1463:     */           }
/* 1464:     */         }
/* 1465:     */       }
/* 1466:     */       catch (ExcepcionAS2Compras e)
/* 1467:     */       {
/* 1468:1565 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1469:1566 */         LOG.info("NO SE ESTABLECIO EL CORREO:", e);
/* 1470:     */       }
/* 1471:     */     }
/* 1472:     */   }
/* 1473:     */   
/* 1474:     */   public void actualizarImpuestosListener()
/* 1475:     */   {
/* 1476:1573 */     for (DetalleFacturaProveedor dfp : this.facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 1477:1574 */       actualizarProducto(dfp, dfp.getProducto());
/* 1478:     */     }
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   public FacturaProveedor getFacturaProveedor()
/* 1482:     */   {
/* 1483:1584 */     return this.facturaProveedor;
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 1487:     */   {
/* 1488:1594 */     this.facturaProveedor = facturaProveedor;
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public LazyDataModel<FacturaProveedor> getListaFacturaProveedor()
/* 1492:     */   {
/* 1493:1603 */     return this.listaFacturaProveedor;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public void setListaFacturaProveedor(LazyDataModel<FacturaProveedor> listaFacturaProveedor)
/* 1497:     */   {
/* 1498:1613 */     this.listaFacturaProveedor = listaFacturaProveedor;
/* 1499:     */   }
/* 1500:     */   
/* 1501:     */   public List<Documento> getListaDocumentoFactura()
/* 1502:     */   {
/* 1503:     */     try
/* 1504:     */     {
/* 1505:1623 */       if (this.listaDocumentoFactura == null)
/* 1506:     */       {
/* 1507:1624 */         this.listaDocumentoFactura = new ArrayList();
/* 1508:1626 */         for (Documento documento : this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.FACTURA_PROVEEDOR, 
/* 1509:1627 */           AppUtil.getOrganizacion().getId())) {
/* 1510:1628 */           if (!documento.isIndicadorDocumentoExterior()) {
/* 1511:1629 */             this.listaDocumentoFactura.add(documento);
/* 1512:     */           }
/* 1513:     */         }
/* 1514:     */       }
/* 1515:     */     }
/* 1516:     */     catch (ExcepcionAS2 e)
/* 1517:     */     {
/* 1518:1634 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1519:     */     }
/* 1520:1637 */     return this.listaDocumentoFactura;
/* 1521:     */   }
/* 1522:     */   
/* 1523:     */   public void setListaDocumentoFactura(List<Documento> listaDocumentoFactura)
/* 1524:     */   {
/* 1525:1647 */     this.listaDocumentoFactura = listaDocumentoFactura;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1529:     */   {
/* 1530:1656 */     if (this.listaDireccionEmpresa == null) {
/* 1531:1657 */       this.listaDireccionEmpresa = new ArrayList();
/* 1532:     */     }
/* 1533:1659 */     return this.listaDireccionEmpresa;
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1537:     */   {
/* 1538:1669 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public List<PedidoProveedor> getListaPedidoProveedorPorFacturar()
/* 1542:     */   {
/* 1543:1678 */     return this.listaPedidoProveedorPorFacturar;
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public void setListaPedidoProveedorPorFacturar(List<PedidoProveedor> listaPedidoProveedorPorFacturar)
/* 1547:     */   {
/* 1548:1688 */     this.listaPedidoProveedorPorFacturar = listaPedidoProveedorPorFacturar;
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   public DataTable getDtFacturaProveedor()
/* 1552:     */   {
/* 1553:1697 */     return this.dtFacturaProveedor;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public void setDtFacturaProveedor(DataTable dtFacturaProveedor)
/* 1557:     */   {
/* 1558:1707 */     this.dtFacturaProveedor = dtFacturaProveedor;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public DataTable getDtDetalleFacturaProveedor()
/* 1562:     */   {
/* 1563:1716 */     return this.dtDetalleFacturaProveedor;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public void setDtDetalleFacturaProveedor(DataTable dtDetalleFacturaProveedor)
/* 1567:     */   {
/* 1568:1726 */     this.dtDetalleFacturaProveedor = dtDetalleFacturaProveedor;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public DataTable getDtImpuestoDetalleFacturaProveedor()
/* 1572:     */   {
/* 1573:1735 */     return this.dtImpuestoDetalleFacturaProveedor;
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public void setDtImpuestoDetalleFacturaProveedor(DataTable dtImpuestoDetalleFacturaProveedor)
/* 1577:     */   {
/* 1578:1745 */     this.dtImpuestoDetalleFacturaProveedor = dtImpuestoDetalleFacturaProveedor;
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   public DataTable getDtCuentasPorPagar()
/* 1582:     */   {
/* 1583:1754 */     return this.dtCuentasPorPagar;
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public void setDtCuentasPorPagar(DataTable dtCuentasPorPagar)
/* 1587:     */   {
/* 1588:1764 */     this.dtCuentasPorPagar = dtCuentasPorPagar;
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   public List<ImpuestoProductoFacturaProveedor> getListaImpuestoProductoFacturaProveedor()
/* 1592:     */   {
/* 1593:1774 */     List<ImpuestoProductoFacturaProveedor> listaImpuestoProductoFacturaProveedor = new ArrayList();
/* 1594:1776 */     for (DetalleFacturaProveedor dfp : this.facturaProveedor.getListaDetalleFacturaProveedor()) {
/* 1595:1778 */       if (!dfp.isEliminado()) {
/* 1596:1780 */         for (ImpuestoProductoFacturaProveedor ipfp : dfp.getListaImpuestoProductoFacturaProveedor()) {
/* 1597:1782 */           if (!ipfp.isEliminado()) {
/* 1598:1783 */             listaImpuestoProductoFacturaProveedor.add(ipfp);
/* 1599:     */           }
/* 1600:     */         }
/* 1601:     */       }
/* 1602:     */     }
/* 1603:1789 */     return listaImpuestoProductoFacturaProveedor;
/* 1604:     */   }
/* 1605:     */   
/* 1606:     */   public List<SelectItem> getListaExportOpcion()
/* 1607:     */   {
/* 1608:1799 */     if (this.listaExportOpcion == null)
/* 1609:     */     {
/* 1610:1800 */       this.listaExportOpcion = new ArrayList();
/* 1611:1801 */       for (ExportOption exportOption : ExportOption.values())
/* 1612:     */       {
/* 1613:1802 */         SelectItem item = new SelectItem(exportOption, exportOption.getNombre());
/* 1614:1803 */         this.listaExportOpcion.add(item);
/* 1615:     */       }
/* 1616:     */     }
/* 1617:1806 */     return this.listaExportOpcion;
/* 1618:     */   }
/* 1619:     */   
/* 1620:     */   public void setListaExportOpcion(List<SelectItem> listaExportOpcion)
/* 1621:     */   {
/* 1622:1816 */     this.listaExportOpcion = listaExportOpcion;
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   public List<DetalleFacturaProveedor> getListaDetalleFacturaProveedorServicios()
/* 1626:     */   {
/* 1627:1826 */     List<DetalleFacturaProveedor> detalle = new ArrayList();
/* 1628:1827 */     for (DetalleFacturaProveedor detalleFacturaProveedor : getFacturaProveedor().getListaDetalleFacturaProveedor()) {
/* 1629:1831 */       if ((!detalleFacturaProveedor.isEliminado()) && (detalleFacturaProveedor.getProducto().isTraIndicadorServicio()) && 
/* 1630:1832 */         (!detalleFacturaProveedor.isIndicadorGastoImportacion()) && 
/* 1631:1833 */         (detalleFacturaProveedor.getListaDetalleRecepcionProveedor().isEmpty())) {
/* 1632:1834 */         detalle.add(detalleFacturaProveedor);
/* 1633:     */       }
/* 1634:     */     }
/* 1635:1838 */     return detalle;
/* 1636:     */   }
/* 1637:     */   
/* 1638:     */   public GastoProductoFacturaProveedor getGastoProductoFacturaProveedorSeleccionado()
/* 1639:     */   {
/* 1640:1847 */     return this.gastoProductoFacturaProveedorSeleccionado;
/* 1641:     */   }
/* 1642:     */   
/* 1643:     */   public void setGastoProductoFacturaProveedorSeleccionado(GastoProductoFacturaProveedor gpfp)
/* 1644:     */   {
/* 1645:1857 */     this.gastoProductoFacturaProveedorSeleccionado = gpfp;
/* 1646:1858 */     getListaCuentaContableBean().setPrefijoBusquedaCuenta(null);
/* 1647:1860 */     if ((gpfp.getDetalleFacturaProveedor() != null) && (gpfp.getDetalleFacturaProveedor().getProducto() != null))
/* 1648:     */     {
/* 1649:1862 */       int idProducto = gpfp.getDetalleFacturaProveedor().getProducto().getIdProducto();
/* 1650:     */       
/* 1651:1864 */       CuentaContable cuentaContableGasto = this.servicioProducto.obtenerCuentaContableGastoPorProducto(idProducto);
/* 1652:1868 */       if (cuentaContableGasto != null) {
/* 1653:1871 */         getListaCuentaContableBean().setPrefijoBusquedaCuenta(cuentaContableGasto.getCodigo());
/* 1654:     */       }
/* 1655:     */     }
/* 1656:     */   }
/* 1657:     */   
/* 1658:     */   public CentroCosto getCentroCosto()
/* 1659:     */   {
/* 1660:1882 */     return this.centroCosto;
/* 1661:     */   }
/* 1662:     */   
/* 1663:     */   public void setCentroCosto(CentroCosto centroCosto)
/* 1664:     */   {
/* 1665:1892 */     if ((centroCosto != null) && 
/* 1666:1893 */       (!centroCosto.isIndicadorMovimiento()))
/* 1667:     */     {
/* 1668:1894 */       centroCosto = null;
/* 1669:1895 */       addInfoMessage(getLanguageController().getMensaje("msg_info_centro_costo_0001"));
/* 1670:     */     }
/* 1671:1898 */     this.centroCosto = centroCosto;
/* 1672:     */   }
/* 1673:     */   
/* 1674:     */   public ListaProductoBean getListaProductoBean()
/* 1675:     */   {
/* 1676:1907 */     return this.listaProductoBean;
/* 1677:     */   }
/* 1678:     */   
/* 1679:     */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 1680:     */   {
/* 1681:1917 */     this.listaProductoBean = listaProductoBean;
/* 1682:     */   }
/* 1683:     */   
/* 1684:     */   public List<SelectItem> getListaTipoProrroteo()
/* 1685:     */   {
/* 1686:1927 */     if (this.listaTipoProrroteo == null)
/* 1687:     */     {
/* 1688:1928 */       this.listaTipoProrroteo = new ArrayList();
/* 1689:1929 */       for (TipoProrrateoEnum tipoProrrateoEnum : TipoProrrateoEnum.values()) {
/* 1690:1930 */         this.listaTipoProrroteo.add(new SelectItem(tipoProrrateoEnum, tipoProrrateoEnum.getNombre()));
/* 1691:     */       }
/* 1692:     */     }
/* 1693:1933 */     return this.listaTipoProrroteo;
/* 1694:     */   }
/* 1695:     */   
/* 1696:     */   public void setListaTipoProrroteo(List<SelectItem> listaTipoProrroteo)
/* 1697:     */   {
/* 1698:1943 */     this.listaTipoProrroteo = listaTipoProrroteo;
/* 1699:     */   }
/* 1700:     */   
/* 1701:     */   public FacturaProveedorImportacion getFacturaProveedorSeleccionado()
/* 1702:     */   {
/* 1703:1955 */     return this.facturaProveedorSeleccionado;
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   public void setFacturaProveedorSeleccionado(FacturaProveedorImportacion facturaProveedorSeleccionado)
/* 1707:     */   {
/* 1708:1965 */     this.facturaProveedorSeleccionado = facturaProveedorSeleccionado;
/* 1709:     */   }
/* 1710:     */   
/* 1711:     */   public List<GastoImportacion> getListaGastoImportacion()
/* 1712:     */   {
/* 1713:1974 */     if ((this.facturaProveedor.isIndicadorGastoImportacion()) && 
/* 1714:1975 */       (this.listaGastoImportacion == null)) {
/* 1715:1976 */       this.listaGastoImportacion = this.servicioGastoImportacion.obtenerListaCombo("nombre", true, null);
/* 1716:     */     }
/* 1717:1980 */     return this.listaGastoImportacion;
/* 1718:     */   }
/* 1719:     */   
/* 1720:     */   public void setListaGastoImportacion(List<GastoImportacion> listaGastoImportacion)
/* 1721:     */   {
/* 1722:1990 */     this.listaGastoImportacion = listaGastoImportacion;
/* 1723:     */   }
/* 1724:     */   
/* 1725:     */   public void buscarTipoProrroteo()
/* 1726:     */   {
/* 1727:1994 */     DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)this.dtGastoImportacion.getRowData();
/* 1728:1995 */     if (dfp.getGastoImportacion() != null) {
/* 1729:1996 */       dfp.setTipoProrrateoEnum(dfp.getGastoImportacion().getTipoProrrateo());
/* 1730:     */     }
/* 1731:     */   }
/* 1732:     */   
/* 1733:     */   public DataTable getDtGastoImportacion()
/* 1734:     */   {
/* 1735:2006 */     return this.dtGastoImportacion;
/* 1736:     */   }
/* 1737:     */   
/* 1738:     */   public void setDtGastoImportacion(DataTable dtGastoImportacion)
/* 1739:     */   {
/* 1740:2016 */     this.dtGastoImportacion = dtGastoImportacion;
/* 1741:     */   }
/* 1742:     */   
/* 1743:     */   public List<CondicionPago> getListaCondicionPago()
/* 1744:     */   {
/* 1745:2025 */     if (this.listaCondicionPago == null) {
/* 1746:2026 */       this.listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("", false, null);
/* 1747:     */     }
/* 1748:2028 */     return this.listaCondicionPago;
/* 1749:     */   }
/* 1750:     */   
/* 1751:     */   public void setListaCondicionPago(List<CondicionPago> listaCondicionPago)
/* 1752:     */   {
/* 1753:2038 */     this.listaCondicionPago = listaCondicionPago;
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   public List<TipoComprobanteSRI> getListaTipoComprobanteSRI()
/* 1757:     */   {
/* 1758:2047 */     return this.listaTipoComprobanteSRI;
/* 1759:     */   }
/* 1760:     */   
/* 1761:     */   public void setListaTipoComprobanteSRI(List<TipoComprobanteSRI> listaTipoComprobanteSRI)
/* 1762:     */   {
/* 1763:2057 */     this.listaTipoComprobanteSRI = listaTipoComprobanteSRI;
/* 1764:     */   }
/* 1765:     */   
/* 1766:     */   public List<TipoOperacion> getListaTipoOperacion()
/* 1767:     */   {
/* 1768:2066 */     if (this.listaTipoOperacion == null) {
/* 1769:2067 */       this.listaTipoOperacion = this.servicioTipoOperacion.obtenerListaCombo("nombre", true, null);
/* 1770:     */     }
/* 1771:2069 */     return this.listaTipoOperacion;
/* 1772:     */   }
/* 1773:     */   
/* 1774:     */   public void setListaTipoOperacion(List<TipoOperacion> listaTipoOperacion)
/* 1775:     */   {
/* 1776:2079 */     this.listaTipoOperacion = listaTipoOperacion;
/* 1777:     */   }
/* 1778:     */   
/* 1779:     */   public DataTable getDtDetalleFacturaProveedorServicios()
/* 1780:     */   {
/* 1781:2088 */     return this.dtDetalleFacturaProveedorServicios;
/* 1782:     */   }
/* 1783:     */   
/* 1784:     */   public void setDtDetalleFacturaProveedorServicios(DataTable dtDetalleFacturaProveedorServicios)
/* 1785:     */   {
/* 1786:2098 */     this.dtDetalleFacturaProveedorServicios = dtDetalleFacturaProveedorServicios;
/* 1787:     */   }
/* 1788:     */   
/* 1789:     */   public DataTable getDtGastoProductoFacturaProveedor()
/* 1790:     */   {
/* 1791:2107 */     return this.dtGastoProductoFacturaProveedor;
/* 1792:     */   }
/* 1793:     */   
/* 1794:     */   public void setDtGastoProductoFacturaProveedor(DataTable dtGastoProductoFacturaProveedor)
/* 1795:     */   {
/* 1796:2117 */     this.dtGastoProductoFacturaProveedor = dtGastoProductoFacturaProveedor;
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   public DataTable getDtDetallePedidoPendienteFacturaProveedor()
/* 1800:     */   {
/* 1801:2126 */     return this.dtDetallePedidoPendienteFacturaProveedor;
/* 1802:     */   }
/* 1803:     */   
/* 1804:     */   public void setDtDetallePedidoPendienteFacturaProveedor(DataTable dtDetallePedidoPendienteFacturaProveedor)
/* 1805:     */   {
/* 1806:2136 */     this.dtDetallePedidoPendienteFacturaProveedor = dtDetallePedidoPendienteFacturaProveedor;
/* 1807:     */   }
/* 1808:     */   
/* 1809:     */   public DataTable getDtPedidoPendienteFacturaProveedor()
/* 1810:     */   {
/* 1811:2145 */     return this.dtPedidoPendienteFacturaProveedor;
/* 1812:     */   }
/* 1813:     */   
/* 1814:     */   public void setDtPedidoPendienteFacturaProveedor(DataTable dtPedidoPendienteFacturaProveedor)
/* 1815:     */   {
/* 1816:2155 */     this.dtPedidoPendienteFacturaProveedor = dtPedidoPendienteFacturaProveedor;
/* 1817:     */   }
/* 1818:     */   
/* 1819:     */   public DataTable getDtCuentaContable()
/* 1820:     */   {
/* 1821:2164 */     return this.dtCuentaContable;
/* 1822:     */   }
/* 1823:     */   
/* 1824:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 1825:     */   {
/* 1826:2174 */     this.dtCuentaContable = dtCuentaContable;
/* 1827:     */   }
/* 1828:     */   
/* 1829:     */   public String getProveedorSeleccionado()
/* 1830:     */   {
/* 1831:2183 */     return this.proveedorSeleccionado;
/* 1832:     */   }
/* 1833:     */   
/* 1834:     */   public void setProveedorSeleccionado(String proveedorSeleccionado)
/* 1835:     */   {
/* 1836:2193 */     this.proveedorSeleccionado = proveedorSeleccionado;
/* 1837:     */   }
/* 1838:     */   
/* 1839:     */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 1840:     */   {
/* 1841:2202 */     return this.listaCuentaContableBean;
/* 1842:     */   }
/* 1843:     */   
/* 1844:     */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 1845:     */   {
/* 1846:2212 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 1847:     */   }
/* 1848:     */   
/* 1849:     */   public Integer getIdRecepcionProveedor()
/* 1850:     */   {
/* 1851:2221 */     return this.idRecepcionProveedor;
/* 1852:     */   }
/* 1853:     */   
/* 1854:     */   public void setIdRecepcionProveedor(Integer idRecepcionProveedor)
/* 1855:     */   {
/* 1856:2231 */     this.idRecepcionProveedor = idRecepcionProveedor;
/* 1857:     */   }
/* 1858:     */   
/* 1859:     */   public PedidoProveedor getPedidoProveedor()
/* 1860:     */   {
/* 1861:2240 */     return this.pedidoProveedor;
/* 1862:     */   }
/* 1863:     */   
/* 1864:     */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 1865:     */   {
/* 1866:2250 */     this.pedidoProveedor = pedidoProveedor;
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   public boolean isIndicadorIngresoPrecioTotal()
/* 1870:     */   {
/* 1871:2259 */     this.indicadorIngresoPrecioTotal = ParametrosSistema.getIndicadorIngresoComprasPrecioTotal(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue();
/* 1872:2260 */     return this.indicadorIngresoPrecioTotal;
/* 1873:     */   }
/* 1874:     */   
/* 1875:     */   public void setIndicadorIngresoPrecioTotal(boolean indicadorIngresoPrecioTotal)
/* 1876:     */   {
/* 1877:2270 */     this.indicadorIngresoPrecioTotal = indicadorIngresoPrecioTotal;
/* 1878:     */   }
/* 1879:     */   
/* 1880:     */   public AS2Exception getExContabilizacion()
/* 1881:     */   {
/* 1882:2279 */     return this.exContabilizacion;
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1886:     */   {
/* 1887:2289 */     this.exContabilizacion = exContabilizacion;
/* 1888:     */   }
/* 1889:     */   
/* 1890:     */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 1891:     */   {
/* 1892:2293 */     return this.listaDimensionContableBean;
/* 1893:     */   }
/* 1894:     */   
/* 1895:     */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 1896:     */   {
/* 1897:2297 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 1898:     */   }
/* 1899:     */   
/* 1900:     */   public String getMails()
/* 1901:     */   {
/* 1902:2301 */     return this.mails;
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   public void setMails(String mails)
/* 1906:     */   {
/* 1907:2305 */     this.mails = mails;
/* 1908:     */   }
/* 1909:     */   
/* 1910:     */   public void enviarMail()
/* 1911:     */   {
/* 1912:     */     try
/* 1913:     */     {
/* 1914:2310 */       if (this.facturaProveedor.getFacturaProveedorSRI() != null)
/* 1915:     */       {
/* 1916:2311 */         this.facturaProveedor.setFacturaProveedorSRI(this.servicioFacturaProveedorSRI.cargarDetalle(this.facturaProveedor.getFacturaProveedorSRI().getId()));
/* 1917:2312 */         if (((this.facturaProveedor.getFacturaProveedorSRI().getDocumento() != null) && 
/* 1918:2313 */           (!this.facturaProveedor.getFacturaProveedorSRI().getDocumento().isIndicadorDocumentoElectronico())) || 
/* 1919:2314 */           (this.facturaProveedor.getFacturaProveedorSRI().getEstado().equals(Estado.ANULADO)) || 
/* 1920:2315 */           (this.facturaProveedor.getFacturaProveedorSRI().getEstado().equals(Estado.EN_ESPERA)) || 
/* 1921:2316 */           (this.facturaProveedor.getFacturaProveedorSRI().getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 1922:2317 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1923:     */         } else {
/* 1924:2319 */           this.servicioFacturaProveedorSRI.enviarMail(this.facturaProveedor.getFacturaProveedorSRI(), this.mails);
/* 1925:     */         }
/* 1926:     */       }
/* 1927:     */       else
/* 1928:     */       {
/* 1929:2322 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1930:     */       }
/* 1931:     */     }
/* 1932:     */     catch (ExcepcionAS2 e)
/* 1933:     */     {
/* 1934:2325 */       addErrorMessage(this.facturaProveedor.getFacturaProveedorSRI().getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 1935:     */     }
/* 1936:     */   }
/* 1937:     */   
/* 1938:     */   public void agregarDetalleDespachoAFactura(RecepcionProveedor recepcionProveedor)
/* 1939:     */   {
/* 1940:2330 */     getFacturaProveedor().setRecepcionProveedor(recepcionProveedor);
/* 1941:2331 */     getFacturaProveedor().setDescripcion(recepcionProveedor.getDescripcion());
/* 1942:2332 */     HashMap<String, DetalleFacturaProveedor> hmDetalleFacturaProveedor = new HashMap();
/* 1943:2333 */     for (DetalleRecepcionProveedor drp : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1944:2334 */       if ((drp.getDetalleFacturaProveedor() == null) || 
/* 1945:2335 */         (drp.getDetalleFacturaProveedor().getFacturaProveedor().getEstado().equals(Estado.ANULADO)))
/* 1946:     */       {
/* 1947:2336 */         if (!hmDetalleFacturaProveedor.containsKey(drp.getProducto().getCodigo() + (drp
/* 1948:2337 */           .getDetallePedidoProveedor() != null ? "~" + drp.getDetallePedidoProveedor().getPrecio() : "")))
/* 1949:     */         {
/* 1950:2338 */           DetalleFacturaProveedor dfp = new DetalleFacturaProveedor();
/* 1951:2339 */           hmDetalleFacturaProveedor.put(drp.getProducto().getCodigo() + (drp
/* 1952:2340 */             .getDetallePedidoProveedor() != null ? "~" + drp.getDetallePedidoProveedor().getPrecio() : ""), dfp);
/* 1953:2341 */           this.facturaProveedor.getListaDetalleFacturaProveedor().add(dfp);
/* 1954:     */         }
/* 1955:2343 */         DetalleFacturaProveedor dfp = (DetalleFacturaProveedor)hmDetalleFacturaProveedor.get(drp.getProducto().getCodigo() + (drp
/* 1956:2344 */           .getDetallePedidoProveedor() != null ? "~" + drp.getDetallePedidoProveedor().getPrecio() : ""));
/* 1957:2345 */         drp.setDetalleFacturaProveedor(dfp);
/* 1958:     */         
/* 1959:2347 */         dfp.getListaDetalleRecepcionProveedor().add(drp);
/* 1960:     */         
/* 1961:2349 */         dfp.setCantidad(dfp.getCantidad().add(drp.getCantidadPorDevolver()));
/* 1962:2350 */         dfp.setUnidadCompra(drp.getUnidadCompra());
/* 1963:2352 */         if (drp.getDetallePedidoProveedor() != null)
/* 1964:     */         {
/* 1965:2354 */           dfp.setPrecio(drp.getDetallePedidoProveedor().getPrecio());
/* 1966:2355 */           dfp.setDescuento(drp.getDetallePedidoProveedor().getDescuento());
/* 1967:2356 */           dfp.setPorcentajeDescuento(drp.getDetallePedidoProveedor().getPorcentajeDescuento());
/* 1968:     */         }
/* 1969:2360 */         actualizarProducto(dfp, drp.getProducto());
/* 1970:     */       }
/* 1971:     */     }
/* 1972:     */   }
/* 1973:     */   
/* 1974:     */   public List<RecepcionProveedor> getListaRecepcionProveedorPendientes()
/* 1975:     */   {
/* 1976:2366 */     return this.listaRecepcionProveedorPendientes;
/* 1977:     */   }
/* 1978:     */   
/* 1979:     */   public void setListaRecepcionProveedorPendientes(List<RecepcionProveedor> listaRecepcionProveedorPendientes)
/* 1980:     */   {
/* 1981:2370 */     this.listaRecepcionProveedorPendientes = listaRecepcionProveedorPendientes;
/* 1982:     */   }
/* 1983:     */   
/* 1984:     */   public List<RecepcionProveedor> getListaRecepcionProveedorAsignados()
/* 1985:     */   {
/* 1986:2374 */     return this.listaRecepcionProveedorAsignados;
/* 1987:     */   }
/* 1988:     */   
/* 1989:     */   public void setListaRecepcionProveedorAsignados(List<RecepcionProveedor> listaRecepcionProveedorAsignados)
/* 1990:     */   {
/* 1991:2378 */     this.listaRecepcionProveedorAsignados = listaRecepcionProveedorAsignados;
/* 1992:     */   }
/* 1993:     */   
/* 1994:     */   public List<RecepcionProveedor> getListaRecepcionProveedorSeleccionados()
/* 1995:     */   {
/* 1996:2382 */     return this.listaRecepcionProveedorSeleccionados;
/* 1997:     */   }
/* 1998:     */   
/* 1999:     */   public void setListaRecepcionProveedorSeleccionados(List<RecepcionProveedor> listaRecepcionProveedorSeleccionados)
/* 2000:     */   {
/* 2001:2386 */     this.listaRecepcionProveedorSeleccionados = listaRecepcionProveedorSeleccionados;
/* 2002:     */   }
/* 2003:     */   
/* 2004:     */   public List<String> getListaRegistroPesoPorLiquidar()
/* 2005:     */   {
/* 2006:2390 */     return this.listaRegistroPesoPorLiquidar;
/* 2007:     */   }
/* 2008:     */   
/* 2009:     */   public void setListaRegistroPesoPorLiquidar(List<String> listaRegistroPesoPorLiquidar)
/* 2010:     */   {
/* 2011:2394 */     this.listaRegistroPesoPorLiquidar = listaRegistroPesoPorLiquidar;
/* 2012:     */   }
/* 2013:     */   
/* 2014:     */   public List<String> getListaRegistroPesoSeleccionados()
/* 2015:     */   {
/* 2016:2398 */     return this.listaRegistroPesoSeleccionados;
/* 2017:     */   }
/* 2018:     */   
/* 2019:     */   public void setListaRegistroPesoSeleccionados(List<String> listaRegistroPesoSeleccionados)
/* 2020:     */   {
/* 2021:2402 */     this.listaRegistroPesoSeleccionados = listaRegistroPesoSeleccionados;
/* 2022:     */   }
/* 2023:     */   
/* 2024:     */   public void cargarListaTipoComprobanteSRI()
/* 2025:     */   {
/* 2026:2406 */     this.listaTipoComprobanteSRI = this.servicioSRI.buscarPorTipoIdentificacion(this.facturaProveedor.getEmpresa().getTipoIdentificacion());
/* 2027:     */   }
/* 2028:     */   
/* 2029:     */   public void cargarDatosFactura(ToggleEvent event)
/* 2030:     */   {
/* 2031:2410 */     this.totalDebito = BigDecimal.ZERO;
/* 2032:2411 */     this.totalCredito = BigDecimal.ZERO;
/* 2033:2412 */     getListaEstadoCuenta().clear();
/* 2034:2413 */     FacturaProveedor facturaProveedorAux = (FacturaProveedor)event.getData();
/* 2035:2414 */     setFacturaProveedor(facturaProveedorAux);
/* 2036:2415 */     Empresa empresa = facturaProveedorAux.getEmpresa();
/* 2037:     */     try
/* 2038:     */     {
/* 2039:2417 */       if (getFacturaProveedor() != null)
/* 2040:     */       {
/* 2041:2420 */         List<Object[]> reporteAux = this.servicioReporteCompra.getListaReporteEstadoCuenta(empresa.getId(), getFacturaProveedor());
/* 2042:2421 */         reporteEstadoCuentaFactura = null;
/* 2043:2422 */         saldo = BigDecimal.ZERO;
/* 2044:2424 */         for (Object[] object : reporteAux)
/* 2045:     */         {
/* 2046:2425 */           reporteEstadoCuentaFactura = new ReporteEstadoCuentaFactura();
/* 2047:2426 */           reporteEstadoCuentaFactura.setFechaDocumento((Date)object[0]);
/* 2048:2427 */           reporteEstadoCuentaFactura.setNumeroDocumento((String)object[1]);
/* 2049:2428 */           reporteEstadoCuentaFactura.setFechaVencimiento((Date)object[2]);
/* 2050:2429 */           reporteEstadoCuentaFactura.setNumeroFactura((String)object[3]);
/* 2051:2430 */           BigDecimal debito = (BigDecimal)object[4];
/* 2052:2431 */           BigDecimal credito = (BigDecimal)object[5];
/* 2053:2432 */           reporteEstadoCuentaFactura.setDebito(debito);
/* 2054:2433 */           reporteEstadoCuentaFactura.setCredito(credito);
/* 2055:2434 */           reporteEstadoCuentaFactura.setNombreDocumento((String)object[9]);
/* 2056:2435 */           reporteEstadoCuentaFactura.setCodigoDocumentoProceso((String)object[7]);
/* 2057:2436 */           reporteEstadoCuentaFactura.setIdFacturaProveedor(object[10] == null ? 0 : ((Integer)object[10]).intValue());
/* 2058:2437 */           System.out.println("DB: " + object[11]);
/* 2059:2438 */           reporteEstadoCuentaFactura.setDocumentoBase((DocumentoBase)(object[11] == null ? Integer.valueOf(0) : object[11]));
/* 2060:2439 */           reporteEstadoCuentaFactura.setIdCobro(object[12] == null ? 0 : ((Integer)object[12]).intValue());
/* 2061:     */           
/* 2062:2441 */           this.totalDebito = this.totalDebito.add(debito);
/* 2063:2442 */           this.totalCredito = this.totalCredito.add(credito);
/* 2064:     */           
/* 2065:2444 */           saldo = this.totalDebito.subtract(this.totalCredito);
/* 2066:     */           
/* 2067:2446 */           reporteEstadoCuentaFactura.setSaldo(saldo);
/* 2068:     */           
/* 2069:2448 */           getListaEstadoCuenta().add(reporteEstadoCuentaFactura);
/* 2070:     */         }
/* 2071:     */       }
/* 2072:     */     }
/* 2073:     */     catch (Exception e)
/* 2074:     */     {
/* 2075:     */       ReporteEstadoCuentaFactura reporteEstadoCuentaFactura;
/* 2076:     */       BigDecimal saldo;
/* 2077:2453 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 2078:     */     }
/* 2079:     */   }
/* 2080:     */   
/* 2081:     */   public List<ReporteEstadoCuentaFactura> getListaEstadoCuenta()
/* 2082:     */   {
/* 2083:2458 */     if (this.listaEstadoCuenta == null) {
/* 2084:2459 */       this.listaEstadoCuenta = new ArrayList();
/* 2085:     */     }
/* 2086:2461 */     return this.listaEstadoCuenta;
/* 2087:     */   }
/* 2088:     */   
/* 2089:     */   public void setListaEstadoCuenta(List<ReporteEstadoCuentaFactura> listaEstadoCuenta)
/* 2090:     */   {
/* 2091:2465 */     this.listaEstadoCuenta = listaEstadoCuenta;
/* 2092:     */   }
/* 2093:     */   
/* 2094:     */   public BigDecimal getTotalDebito()
/* 2095:     */   {
/* 2096:2469 */     return this.totalDebito;
/* 2097:     */   }
/* 2098:     */   
/* 2099:     */   public void setTotalDebito(BigDecimal totalDebito)
/* 2100:     */   {
/* 2101:2473 */     this.totalDebito = totalDebito;
/* 2102:     */   }
/* 2103:     */   
/* 2104:     */   public BigDecimal getTotalCredito()
/* 2105:     */   {
/* 2106:2477 */     return this.totalCredito;
/* 2107:     */   }
/* 2108:     */   
/* 2109:     */   public void setTotalCredito(BigDecimal totalCredito)
/* 2110:     */   {
/* 2111:2481 */     this.totalCredito = totalCredito;
/* 2112:     */   }
/* 2113:     */   
/* 2114:     */   public DataTable getDtListaReporte()
/* 2115:     */   {
/* 2116:2485 */     return this.dtListaReporte;
/* 2117:     */   }
/* 2118:     */   
/* 2119:     */   public void setDtListaReporte(DataTable dtListaReporte)
/* 2120:     */   {
/* 2121:2489 */     this.dtListaReporte = dtListaReporte;
/* 2122:     */   }
/* 2123:     */   
/* 2124:     */   public Integer getIdFacturaProveedor()
/* 2125:     */   {
/* 2126:2493 */     return this.idFacturaProveedor;
/* 2127:     */   }
/* 2128:     */   
/* 2129:     */   public void setIdFacturaProveedor(Integer idFacturaProveedor)
/* 2130:     */   {
/* 2131:2497 */     this.idFacturaProveedor = idFacturaProveedor;
/* 2132:     */   }
/* 2133:     */   
/* 2134:     */   public List<SelectItem> getListaFormaPagoSRI()
/* 2135:     */   {
/* 2136:2502 */     if ((this.listaFormaPagoSRI == null) && (getFacturaProveedor().getEmpresa() != null))
/* 2137:     */     {
/* 2138:2503 */       this.listaFormaPagoSRI = new ArrayList();
/* 2139:2505 */       for (Iterator localIterator1 = this.servicioFormaPagoSRI.getListaFormaPagoSRI(getFacturaProveedor().getEmpresa()).iterator(); localIterator1.hasNext();)
/* 2140:     */       {
/* 2141:2505 */         formaPagoSRI = (FormaPagoSRI)localIterator1.next();
/* 2142:2506 */         for (SelectItem seleItem : DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId())) {
/* 2143:2507 */           if (formaPagoSRI.getCodigo().equals(seleItem.getValue().toString())) {
/* 2144:2508 */             this.listaFormaPagoSRI.add(seleItem);
/* 2145:     */           }
/* 2146:     */         }
/* 2147:     */       }
/* 2148:     */       FormaPagoSRI formaPagoSRI;
/* 2149:2513 */       if (this.listaFormaPagoSRI.size() == 0) {
/* 2150:2514 */         this.listaFormaPagoSRI.addAll(DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId()));
/* 2151:     */       }
/* 2152:2517 */       if ((getFacturaProveedor().getFacturaProveedorSRI() != null) && (!this.listaFormaPagoSRI.isEmpty())) {
/* 2153:2518 */         getFacturaProveedor().getFacturaProveedorSRI().setCodigoFormaPagoSRI(((SelectItem)this.listaFormaPagoSRI.get(0)).getValue().toString());
/* 2154:     */       }
/* 2155:     */     }
/* 2156:2522 */     return this.listaFormaPagoSRI;
/* 2157:     */   }
/* 2158:     */   
/* 2159:     */   public String editar()
/* 2160:     */   {
/* 2161:     */     try
/* 2162:     */     {
/* 2163:2528 */       return editarPadre();
/* 2164:     */     }
/* 2165:     */     catch (ExcepcionAS2Financiero e)
/* 2166:     */     {
/* 2167:2530 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 2168:2531 */       LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 2169:     */     }
/* 2170:     */     catch (ExcepcionAS2Compras e)
/* 2171:     */     {
/* 2172:2534 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 2173:2535 */       LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 2174:     */     }
/* 2175:     */     catch (Exception e)
/* 2176:     */     {
/* 2177:2538 */       addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 2178:2539 */       LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 2179:     */     }
/* 2180:2541 */     return null;
/* 2181:     */   }
/* 2182:     */   
/* 2183:     */   public Boolean getVencimientoPorFechaRecepcion()
/* 2184:     */   {
/* 2185:2545 */     return ParametrosSistema.getVencimientoPorFechaRecepcion(AppUtil.getOrganizacion().getId());
/* 2186:     */   }
/* 2187:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.FacturaProveedorBaseBean
 * JD-Core Version:    0.7.0.1
 */