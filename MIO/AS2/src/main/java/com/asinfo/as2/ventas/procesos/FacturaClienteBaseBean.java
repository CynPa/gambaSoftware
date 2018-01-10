/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.ReporteEstadoCuentaFactura;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    7:     */ import com.asinfo.as2.controller.LanguageController;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   12:     */ import com.asinfo.as2.entities.Ciudad;
/*   13:     */ import com.asinfo.as2.entities.Cliente;
/*   14:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   15:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   17:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   18:     */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   19:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   20:     */ import com.asinfo.as2.entities.Documento;
/*   21:     */ import com.asinfo.as2.entities.Empresa;
/*   22:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   23:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   24:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   25:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   26:     */ import com.asinfo.as2.entities.Organizacion;
/*   27:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   28:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   29:     */ import com.asinfo.as2.entities.Producto;
/*   30:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   31:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   32:     */ import com.asinfo.as2.entities.Secuencia;
/*   33:     */ import com.asinfo.as2.entities.Subempresa;
/*   34:     */ import com.asinfo.as2.entities.Sucursal;
/*   35:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   36:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   37:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   38:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   39:     */ import com.asinfo.as2.enumeraciones.ExportOption;
/*   40:     */ import com.asinfo.as2.enumeraciones.RefrendoEnum;
/*   41:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   42:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   43:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   44:     */ import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
/*   45:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*   46:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   47:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   48:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   49:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   50:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   51:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   52:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   53:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   54:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   55:     */ import com.asinfo.as2.util.AppUtil;
/*   56:     */ import com.asinfo.as2.utils.DatosSRI;
/*   57:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   58:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   59:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   60:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   61:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   62:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   63:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   64:     */ import com.asinfo.as2.ventas.reportes.ReporteFacturaClienteBean;
/*   65:     */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*   66:     */ import java.math.BigDecimal;
/*   67:     */ import java.math.RoundingMode;
/*   68:     */ import java.util.ArrayList;
/*   69:     */ import java.util.Calendar;
/*   70:     */ import java.util.Collection;
/*   71:     */ import java.util.Date;
/*   72:     */ import java.util.HashMap;
/*   73:     */ import java.util.Iterator;
/*   74:     */ import java.util.List;
/*   75:     */ import java.util.Map;
/*   76:     */ import javax.ejb.EJB;
/*   77:     */ import javax.faces.bean.ManagedProperty;
/*   78:     */ import javax.faces.component.html.HtmlInputText;
/*   79:     */ import javax.faces.component.html.HtmlSelectOneMenu;
/*   80:     */ import javax.faces.context.FacesContext;
/*   81:     */ import javax.faces.context.PartialViewContext;
/*   82:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   83:     */ import javax.faces.model.SelectItem;
/*   84:     */ import org.apache.log4j.Logger;
/*   85:     */ import org.primefaces.component.datatable.DataTable;
/*   86:     */ import org.primefaces.context.RequestContext;
/*   87:     */ import org.primefaces.event.SelectEvent;
/*   88:     */ import org.primefaces.event.ToggleEvent;
/*   89:     */ import org.primefaces.model.LazyDataModel;
/*   90:     */ import org.primefaces.model.SortOrder;
/*   91:     */ 
/*   92:     */ public class FacturaClienteBaseBean
/*   93:     */   extends DocumentoBaseClienteBean
/*   94:     */ {
/*   95:     */   private static final long serialVersionUID = 1L;
/*   96:     */   @EJB
/*   97:     */   protected transient ServicioFacturaCliente servicioFacturaCliente;
/*   98:     */   @EJB
/*   99:     */   protected transient ServicioDespachoCliente servicioDespachoCliente;
/*  100:     */   @EJB
/*  101:     */   protected transient ServicioPedidoCliente servicioPedidoCliente;
/*  102:     */   @EJB
/*  103:     */   protected transient ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  104:     */   @EJB
/*  105:     */   protected transient ServicioUnidadConversion servicioUnidadConversion;
/*  106:     */   @EJB
/*  107:     */   protected transient ServicioUsuario servicioUsuario;
/*  108:     */   @EJB
/*  109:     */   protected transient ServicioCiudad servicioCiudad;
/*  110:     */   @EJB
/*  111:     */   protected transient ServicioLote servicioLote;
/*  112:     */   @EJB
/*  113:     */   protected transient ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  114:     */   @EJB
/*  115:     */   protected transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  116:     */   @EJB
/*  117:     */   protected transient ServicioSucursal servicioSucursal;
/*  118:     */   @EJB
/*  119:     */   private ServicioReporteVenta servicioReporteVenta;
/*  120:     */   @EJB
/*  121:     */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  122:     */   protected FacturaCliente facturaCliente;
/*  123:     */   protected LazyDataModel<FacturaCliente> listaFacturaCliente;
/*  124:     */   protected List<Documento> listaDocumentoCliente;
/*  125:     */   protected List<DireccionEmpresa> listaDireccionEmpresa;
/*  126: 134 */   private List<PedidoCliente> listaPedidoClientePorFacturar = new ArrayList();
/*  127:     */   private List<Subempresa> listaSubempresa;
/*  128:     */   private int idDocumentoAFacturar;
/*  129:     */   private List<SelectItem> listaTipoDocumentoAFacturar;
/*  130:     */   protected List<SelectItem> listaDistritoAduanero;
/*  131:     */   protected List<SelectItem> listaRegimen;
/*  132:     */   protected List<SelectItem> listaRefrendo;
/*  133:     */   protected DataTable dtFacturaCliente;
/*  134:     */   protected DataTable dtDetalleFacturaCliente;
/*  135:     */   private DataTable dtImpuestoDetalleFacturaCliente;
/*  136:     */   private DataTable dtCuentasPorCobrar;
/*  137:     */   private boolean mostrarSaldoInicial;
/*  138:     */   private List<ReporteEstadoCuentaFactura> listaEstadoCuenta;
/*  139:     */   private DataTable dtListaReporte;
/*  140: 155 */   private BigDecimal totalDebito = BigDecimal.ZERO;
/*  141: 156 */   private BigDecimal totalCredito = BigDecimal.ZERO;
/*  142:     */   protected boolean renderDialogDatosExportaciones;
/*  143:     */   protected Integer idFacturaCliente;
/*  144:     */   protected String mails;
/*  145:     */   @ManagedProperty("#{reporteFacturaClienteBean}")
/*  146:     */   private ReporteFacturaClienteBean reporteFacturaClienteBean;
/*  147: 168 */   protected List<SelectItem> listaFormaPagoSRI = new ArrayList();
/*  148:     */   protected List<SelectItem> listaTipoIngresoExterior;
/*  149:     */   @EJB
/*  150:     */   protected ServicioFormaPagoSRI servicioFormaPagoSRI;
/*  151: 175 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  152:     */   
/*  153:     */   public void init()
/*  154:     */   {
/*  155: 179 */     getListaProductoBean().setIndicadorVenta(true);
/*  156: 180 */     getListaProductoBean().setActivo(true);
/*  157: 182 */     if (isEditado()) {
/*  158: 183 */       limpiar();
/*  159:     */     }
/*  160:     */     try
/*  161:     */     {
/*  162: 188 */       this.listaFacturaCliente = new LazyDataModel()
/*  163:     */       {
/*  164:     */         private static final long serialVersionUID = 1L;
/*  165:     */         
/*  166:     */         public List<FacturaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  167:     */         {
/*  168: 195 */           List<FacturaCliente> lista = new ArrayList();
/*  169:     */           
/*  170: 197 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  171: 198 */           FacturaClienteBaseBean.this.obtenerFiltros(filters);
/*  172: 199 */           filters = FacturaClienteBaseBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  173:     */           
/*  174: 201 */           lista = FacturaClienteBaseBean.this.servicioFacturaCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  175:     */           
/*  176: 203 */           FacturaClienteBaseBean.this.listaFacturaCliente.setRowCount(FacturaClienteBaseBean.this.servicioFacturaCliente.contarPorCriterio(filters));
/*  177: 204 */           return lista;
/*  178:     */         }
/*  179:     */       };
/*  180:     */     }
/*  181:     */     catch (Exception e)
/*  182:     */     {
/*  183: 210 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  184: 211 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  185:     */     }
/*  186:     */   }
/*  187:     */   
/*  188:     */   protected void obtenerFiltros(Map<String, String> filters)
/*  189:     */   {
/*  190: 222 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  191:     */   }
/*  192:     */   
/*  193:     */   public void enviarMail()
/*  194:     */   {
/*  195:     */     try
/*  196:     */     {
/*  197: 229 */       if (((this.facturaCliente.getDocumento() != null) && (!this.facturaCliente.getDocumento().isIndicadorDocumentoElectronico())) || 
/*  198: 230 */         (this.facturaCliente.getEstado().equals(Estado.ANULADO)) || (this.facturaCliente.getEstado().equals(Estado.EN_ESPERA)) || 
/*  199: 231 */         (this.facturaCliente.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/*  200: 232 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  201:     */       } else {
/*  202: 234 */         this.servicioFacturaCliente.enviarMail(this.facturaCliente, this.mails);
/*  203:     */       }
/*  204:     */     }
/*  205:     */     catch (ExcepcionAS2 e)
/*  206:     */     {
/*  207: 237 */       addErrorMessage(this.facturaCliente.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/*  208:     */     }
/*  209:     */   }
/*  210:     */   
/*  211:     */   public String editar()
/*  212:     */   {
/*  213: 248 */     if (!this.facturaCliente.getEstado().equals(Estado.RECHAZADO_SRI)) {
/*  214: 249 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  215:     */     } else {
/*  216:     */       try
/*  217:     */       {
/*  218: 252 */         this.facturaCliente = this.servicioFacturaCliente.cargarDetalle(this.facturaCliente.getId());
/*  219: 253 */         actualizarDocumento();
/*  220: 254 */         cargarDirecciones(this.facturaCliente.getEmpresa());
/*  221: 255 */         for (DetalleFacturaCliente detalle : this.facturaCliente.getListaDetalleFacturaCliente()) {
/*  222: 256 */           actualizarProducto(detalle, detalle.getProducto(), true);
/*  223:     */         }
/*  224: 259 */         this.listaFormaPagoSRI = null;
/*  225: 260 */         getListaFormaPagoSRI();
/*  226:     */         
/*  227: 262 */         setEditado(true);
/*  228:     */       }
/*  229:     */       catch (Exception e)
/*  230:     */       {
/*  231: 264 */         addErrorMessage(e.getMessage());
/*  232:     */       }
/*  233:     */     }
/*  234: 267 */     return "";
/*  235:     */   }
/*  236:     */   
/*  237:     */   public String guardar()
/*  238:     */   {
/*  239:     */     try
/*  240:     */     {
/*  241: 278 */       if (this.facturaCliente.getAgenteComercial() == null)
/*  242:     */       {
/*  243: 279 */         EntidadUsuario entidadUsuario = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  244: 280 */         if (entidadUsuario.isIndicadorAgenteComercial()) {
/*  245: 281 */           this.facturaCliente.setAgenteComercial(new EntidadUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  246:     */         }
/*  247:     */       }
/*  248: 284 */       if (this.facturaCliente.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() != null) {
/*  249: 285 */         this.servicioFacturaCliente.cargarAutorizacionAutoImpresor(this.facturaCliente);
/*  250:     */       }
/*  251: 287 */       this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(this.facturaCliente);
/*  252:     */       
/*  253: 289 */       PuntoDeVenta puntoDeVenta = this.servicioFacturaCliente.cargarPuntoVenta(this.facturaCliente);
/*  254:     */       
/*  255: 291 */       this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(this.facturaCliente, puntoDeVenta);
/*  256: 292 */       this.servicioFacturaCliente.guardar(this.facturaCliente);
/*  257:     */       
/*  258: 294 */       this.idFacturaCliente = Integer.valueOf(this.facturaCliente.getIdFacturaCliente());
/*  259: 295 */       FacturaCliente fcAux = this.facturaCliente;
/*  260: 296 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  261:     */       
/*  262: 298 */       limpiar();
/*  263: 300 */       if (fcAux.getAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI() != null)
/*  264:     */       {
/*  265: 301 */         this.reporteFacturaClienteBean.setIndicadorImpreso(false);
/*  266: 302 */         this.reporteFacturaClienteBean.setFacturaCliente(fcAux);
/*  267: 303 */         this.reporteFacturaClienteBean.setExportOption(ExportOption.PRINTER);
/*  268: 304 */         this.reporteFacturaClienteBean.setNumeroImpresiones(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/*  269: 305 */           .getNumeroCopiasDocumentoTributario());
/*  270: 306 */         this.reporteFacturaClienteBean.execute();
/*  271:     */       }
/*  272: 309 */       return "";
/*  273:     */     }
/*  274:     */     catch (ExcepcionAS2DocumentoElectronico e)
/*  275:     */     {
/*  276: 312 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  277: 313 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  278:     */       
/*  279: 315 */       limpiar();
/*  280:     */     }
/*  281:     */     catch (ExcepcionAS2Financiero e)
/*  282:     */     {
/*  283: 318 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  284: 319 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  285: 320 */       e.printStackTrace();
/*  286:     */     }
/*  287:     */     catch (ExcepcionAS2Ventas e)
/*  288:     */     {
/*  289: 322 */       actualizarDocumento();
/*  290: 323 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  291: 324 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  292: 325 */       e.printStackTrace();
/*  293:     */     }
/*  294:     */     catch (ExcepcionAS2Inventario e)
/*  295:     */     {
/*  296: 327 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  297: 328 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  298: 329 */       e.printStackTrace();
/*  299:     */     }
/*  300:     */     catch (ExcepcionAS2 e)
/*  301:     */     {
/*  302: 331 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  303: 332 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  304: 333 */       e.printStackTrace();
/*  305:     */     }
/*  306:     */     catch (AS2Exception e)
/*  307:     */     {
/*  308: 335 */       this.exContabilizacion = e;
/*  309: 336 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  310: 337 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  311:     */     }
/*  312:     */     catch (Exception e)
/*  313:     */     {
/*  314: 339 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  315: 340 */       LOG.error("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  316: 341 */       e.printStackTrace();
/*  317:     */     }
/*  318: 343 */     return "";
/*  319:     */   }
/*  320:     */   
/*  321:     */   public String eliminar()
/*  322:     */   {
/*  323: 353 */     if (this.facturaCliente.getId() > 0) {
/*  324:     */       try
/*  325:     */       {
/*  326: 357 */         FacturaCliente fcAux = this.servicioFacturaCliente.buscarPorId(Integer.valueOf(this.facturaCliente.getId()));
/*  327: 358 */         if (fcAux.isIndicadorAutomatico())
/*  328:     */         {
/*  329: 359 */           addInfoMessage(getLanguageController().getMensaje("msg_info_error_generado_automatico"));
/*  330:     */         }
/*  331:     */         else
/*  332:     */         {
/*  333: 361 */           if (getTipoOrganizacion() == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA) {
/*  334: 364 */             this.servicioFacturaCliente.anulaFacturaCliente(this.facturaCliente, AppUtil.getSucursal());
/*  335:     */           } else {
/*  336: 366 */             this.servicioFacturaCliente.anulaFacturaCliente(this.facturaCliente);
/*  337:     */           }
/*  338: 368 */           addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  339:     */         }
/*  340:     */       }
/*  341:     */       catch (ExcepcionAS2Ventas e)
/*  342:     */       {
/*  343: 371 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  344: 372 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE ExcepcionAS2Ventas", e);
/*  345:     */       }
/*  346:     */       catch (ExcepcionAS2Financiero e)
/*  347:     */       {
/*  348: 374 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  349: 375 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE ExcepcionAS2Financiero", e);
/*  350:     */       }
/*  351:     */       catch (Exception e)
/*  352:     */       {
/*  353: 377 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  354: 378 */         LOG.info("ERROR AL ANULAR UNA FACTURA DE CLIENTE Exception", e);
/*  355:     */       }
/*  356:     */     } else {
/*  357: 381 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  358:     */     }
/*  359: 384 */     return "";
/*  360:     */   }
/*  361:     */   
/*  362:     */   public String limpiar()
/*  363:     */   {
/*  364: 394 */     setEditado(false);
/*  365:     */     
/*  366: 396 */     this.listaPedidoClientePorFacturar.clear();
/*  367:     */     
/*  368: 398 */     this.facturaCliente = new FacturaCliente();
/*  369: 399 */     this.facturaCliente.setNumero("");
/*  370: 400 */     this.facturaCliente.setFecha(new Date());
/*  371: 401 */     this.facturaCliente.setEstado(Estado.PROCESADO);
/*  372:     */     
/*  373: 403 */     this.facturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  374: 404 */     this.facturaCliente.setSucursal(AppUtil.getSucursal());
/*  375:     */     
/*  376: 406 */     this.facturaCliente.setFacturaClienteSRI(new FacturaClienteSRI());
/*  377: 407 */     this.facturaCliente.getFacturaClienteSRI().setEstado(this.facturaCliente.getEstado());
/*  378: 408 */     this.facturaCliente.getFacturaClienteSRI().setIdOrganizacion(this.facturaCliente.getIdOrganizacion());
/*  379: 409 */     this.facturaCliente.getFacturaClienteSRI().setIdSucursal(this.facturaCliente.getSucursal().getId());
/*  380:     */     
/*  381: 411 */     this.facturaCliente.getFacturaClienteSRI().setFacturaCliente(this.facturaCliente);
/*  382: 412 */     this.facturaCliente.setFacturaClienteSRI(this.facturaCliente.getFacturaClienteSRI());
/*  383:     */     
/*  384: 414 */     this.listaDireccionEmpresa = new ArrayList();
/*  385:     */     
/*  386: 416 */     Documento documento = null;
/*  387: 417 */     if ((getListaDocumentoCliente() != null) && (!getListaDocumentoCliente().isEmpty()))
/*  388:     */     {
/*  389: 418 */       documento = (Documento)getListaDocumentoCliente().get(0);
/*  390: 419 */       this.facturaCliente.setDocumento(documento);
/*  391:     */       
/*  392: 421 */       actualizarDocumento();
/*  393:     */     }
/*  394:     */     else
/*  395:     */     {
/*  396: 423 */       documento = new Documento();
/*  397: 424 */       documento.setSecuencia(new Secuencia());
/*  398: 425 */       this.facturaCliente.setDocumento(documento);
/*  399:     */     }
/*  400: 428 */     setMostrarAutorizarVenta(false);
/*  401: 429 */     this.facturaCliente.setIndicadorAutorizaVenta(false);
/*  402:     */     
/*  403: 431 */     return "";
/*  404:     */   }
/*  405:     */   
/*  406:     */   public String agregarDetalleFacturaListener()
/*  407:     */   {
/*  408: 441 */     agregarDetalleFactura();
/*  409:     */     
/*  410: 443 */     return "";
/*  411:     */   }
/*  412:     */   
/*  413:     */   public DetalleFacturaCliente agregarDetalleFactura()
/*  414:     */   {
/*  415: 448 */     DetalleFacturaCliente d = new DetalleFacturaCliente();
/*  416: 449 */     d.setFacturaCliente(getFacturaCliente());
/*  417: 450 */     d.setProducto(new Producto());
/*  418: 451 */     d.setCantidad(BigDecimal.ZERO);
/*  419: 452 */     d.setPrecio(BigDecimal.ZERO);
/*  420: 453 */     d.setDescuento(BigDecimal.ZERO);
/*  421: 454 */     getFacturaCliente().getListaDetalleFacturaCliente().add(d);
/*  422:     */     
/*  423: 456 */     return d;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/*  427:     */   {
/*  428: 465 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData();
/*  429:     */     
/*  430: 467 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  431: 468 */     Producto producto = null;
/*  432:     */     try
/*  433:     */     {
/*  434: 470 */       producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/*  435: 471 */       actualizarProducto(dfc, producto);
/*  436:     */     }
/*  437:     */     catch (ExcepcionAS2 e)
/*  438:     */     {
/*  439: 474 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  440: 475 */       dfc.getProducto().setCodigo("");
/*  441: 476 */       dfc.getProducto().setNombre("");
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected void actualizarProducto(DetalleFacturaCliente dfc, Producto producto)
/*  446:     */   {
/*  447: 490 */     actualizarProducto(dfc, producto, false, true);
/*  448:     */   }
/*  449:     */   
/*  450:     */   protected void actualizarProducto(DetalleFacturaCliente dfc, Producto producto, boolean validarListaPrecios)
/*  451:     */   {
/*  452: 494 */     actualizarProducto(dfc, producto, false, validarListaPrecios);
/*  453:     */   }
/*  454:     */   
/*  455:     */   protected void actualizarProducto(DetalleFacturaCliente dfc, Producto producto, boolean editar, boolean validarListaPrecios)
/*  456:     */   {
/*  457: 499 */     if (this.facturaCliente.getEmpresa() != null)
/*  458:     */     {
/*  459:     */       try
/*  460:     */       {
/*  461: 501 */         this.servicioFacturaCliente.actualizarProducto(getFacturaCliente(), dfc, producto, editar, AppUtil.getOrganizacion()
/*  462: 502 */           .getOrganizacionConfiguracion().getTipoOrganizacion(), validarListaPrecios);
/*  463:     */       }
/*  464:     */       catch (ExcepcionAS2Inventario e)
/*  465:     */       {
/*  466: 504 */         e.printStackTrace();
/*  467: 505 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  468:     */       }
/*  469:     */       catch (ExcepcionAS2 e)
/*  470:     */       {
/*  471: 508 */         if (ParametrosSistema.isBloqueoProductoListaPrecios(this.facturaCliente.getIdOrganizacion()).booleanValue())
/*  472:     */         {
/*  473: 509 */           dfc.setEliminado(true);
/*  474: 510 */           totalizar();
/*  475:     */         }
/*  476: 513 */         e.printStackTrace();
/*  477: 514 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  478:     */       }
/*  479:     */     }
/*  480:     */     else
/*  481:     */     {
/*  482: 517 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  483: 518 */       dfc.getProducto().setCodigo("");
/*  484:     */     }
/*  485:     */   }
/*  486:     */   
/*  487:     */   public void actualizarPorcentajeDescuento()
/*  488:     */   {
/*  489: 528 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData();
/*  490: 529 */     validarDescuento(dfc, null);
/*  491:     */   }
/*  492:     */   
/*  493:     */   private void validarDescuento(DetalleFacturaCliente dfc, BigDecimal porcentajeDescuentoGeneral)
/*  494:     */   {
/*  495: 533 */     if (this.facturaCliente.getEmpresa().getCliente().getListaDescuentos() != null)
/*  496:     */     {
/*  497: 534 */       if (!this.facturaCliente.getEmpresa().getCliente().getListaDescuentos().isIndicadorDescuentoPorProducto())
/*  498:     */       {
/*  499: 535 */         dfc.getProducto().setTraDescuentoPorcentajeMaximo(this.servicioListaDescuentos.getPorcentajeDescuentoMaximoVigente(this.facturaCliente
/*  500: 536 */           .getEmpresa().getCliente().getListaDescuentos(), this.facturaCliente.getFecha()));
/*  501:     */       }
/*  502:     */       else
/*  503:     */       {
/*  504: 538 */         DetalleListaDescuentos detalleListaDescuentos = this.servicioListaDescuentos.getDatosListaDescuentosPorProducto(this.facturaCliente
/*  505: 539 */           .getEmpresa().getCliente().getListaDescuentos(), dfc.getProducto());
/*  506: 540 */         if (dfc.getProducto() != null) {
/*  507: 541 */           if (detalleListaDescuentos != null) {
/*  508: 542 */             dfc.getProducto().setTraDescuentoPorcentajeMaximo(detalleListaDescuentos.getPorcentajeDescuentoMaximo());
/*  509:     */           } else {
/*  510: 544 */             dfc.getProducto().setTraDescuentoPorcentajeMaximo(BigDecimal.ZERO);
/*  511:     */           }
/*  512:     */         }
/*  513:     */       }
/*  514:     */     }
/*  515:     */     else {
/*  516: 550 */       dfc.getProducto().setTraDescuentoPorcentajeMaximo(BigDecimal.ZERO);
/*  517:     */     }
/*  518: 552 */     if (porcentajeDescuentoGeneral != null) {
/*  519: 553 */       dfc.setPorcentajeDescuento(porcentajeDescuentoGeneral);
/*  520:     */     }
/*  521: 555 */     if (dfc.getPorcentajeDescuento().compareTo(dfc.getProducto().getTraDescuentoPorcentajeMaximo()) > 0)
/*  522:     */     {
/*  523: 557 */       dfc.setPorcentajeDescuento(dfc.getProducto().getTraDescuentoPorcentajeMaximo());
/*  524: 558 */       addErrorMessage(getLanguageController().getMensaje("msg_error_porcentaje_descuento_maximo"));
/*  525:     */     }
/*  526: 561 */     totalizar();
/*  527:     */   }
/*  528:     */   
/*  529:     */   public void actualizarPorcentajeDescuentoGeneral()
/*  530:     */   {
/*  531: 569 */     if (!getDescuentoGeneral().equals(BigDecimal.ZERO)) {
/*  532: 570 */       for (DetalleFacturaCliente dfc : getDetalleFacturaCliente()) {
/*  533: 571 */         validarDescuento(dfc, getDescuentoGeneral());
/*  534:     */       }
/*  535:     */     }
/*  536: 574 */     totalizar();
/*  537:     */   }
/*  538:     */   
/*  539:     */   public void actualizarImpuesto()
/*  540:     */   {
/*  541: 581 */     DetalleFacturaCliente d = (DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData();
/*  542: 582 */     actualizarImpuestoDetalle(d, true);
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void actualizarImpuestosTodos()
/*  546:     */   {
/*  547: 586 */     for (DetalleFacturaCliente d : this.facturaCliente.getListaDetalleFacturaCliente()) {
/*  548: 587 */       if (!d.isEliminado()) {
/*  549: 588 */         actualizarImpuestoDetalle(d, false);
/*  550:     */       }
/*  551:     */     }
/*  552: 591 */     totalizar();
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void actualizarImpuestoDetalle(DetalleFacturaCliente d, boolean totalizar)
/*  556:     */   {
/*  557: 595 */     for (ImpuestoProductoFacturaCliente ipfc : d.getListaImpuestoProductoFacturaCliente()) {
/*  558: 596 */       ipfc.setEliminado(true);
/*  559:     */     }
/*  560: 599 */     if (d.isIndicadorImpuesto()) {
/*  561:     */       try
/*  562:     */       {
/*  563: 601 */         this.servicioFacturaCliente.obtenerImpuestosProductos(d.getProducto(), d);
/*  564:     */       }
/*  565:     */       catch (ExcepcionAS2Inventario e)
/*  566:     */       {
/*  567: 603 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  568:     */       }
/*  569:     */     }
/*  570: 606 */     if (totalizar) {
/*  571: 607 */       totalizar();
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public String eliminarDetalle()
/*  576:     */   {
/*  577: 618 */     DetalleFacturaCliente detfc = (DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData();
/*  578:     */     
/*  579: 620 */     List<DetalleFacturaCliente> listaDetalleFacturaClienteEliminar = new ArrayList();
/*  580:     */     int idDespachoCliente;
/*  581:     */     Iterator localIterator;
/*  582:     */     DetalleFacturaCliente detalle;
/*  583: 621 */     if (detfc.getDetalleDespachoCliente() != null)
/*  584:     */     {
/*  585: 622 */       idDespachoCliente = detfc.getDetalleDespachoCliente().getDespachoCliente().getId();
/*  586: 623 */       for (localIterator = getFacturaCliente().getListaDetalleFacturaCliente().iterator(); localIterator.hasNext();)
/*  587:     */       {
/*  588: 623 */         detalle = (DetalleFacturaCliente)localIterator.next();
/*  589: 624 */         if (detalle.getDetalleDespachoCliente().getDespachoCliente().getId() == idDespachoCliente) {
/*  590: 625 */           listaDetalleFacturaClienteEliminar.add(detalle);
/*  591:     */         }
/*  592:     */       }
/*  593:     */     }
/*  594:     */     else
/*  595:     */     {
/*  596: 629 */       listaDetalleFacturaClienteEliminar.add(detfc);
/*  597:     */     }
/*  598: 632 */     for (DetalleFacturaCliente dfc : listaDetalleFacturaClienteEliminar)
/*  599:     */     {
/*  600: 633 */       if ((dfc.getDetalleDespachoCliente() != null) && (dfc.getDetalleDespachoCliente().getId() == 0)) {
/*  601: 634 */         dfc.getDetalleDespachoCliente().setEliminado(true);
/*  602:     */       }
/*  603: 637 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/*  604: 638 */         ipfc.setEliminado(true);
/*  605:     */       }
/*  606: 641 */       dfc.setEliminado(true);
/*  607: 642 */       dfc.setCantidad(BigDecimal.ZERO);
/*  608:     */     }
/*  609: 645 */     totalizar();
/*  610:     */     
/*  611: 647 */     return "";
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void totalizar()
/*  615:     */   {
/*  616:     */     try
/*  617:     */     {
/*  618: 657 */       this.servicioFacturaCliente.totalizar(this.facturaCliente);
/*  619: 659 */       if (this.facturaCliente.getEmpresa().getCliente().getCreditoMaximo().subtract(this.facturaCliente.getEmpresa().getCliente().getCreditoUtilizado()).compareTo(this.facturaCliente.getTotalFactura()) < 0) {
/*  620: 660 */         setMostrarAutorizarVenta(true);
/*  621:     */       } else {
/*  622: 662 */         setMostrarAutorizarVenta(false);
/*  623:     */       }
/*  624: 664 */       cargarCuentaPorCobrar();
/*  625:     */     }
/*  626:     */     catch (ExcepcionAS2Ventas e)
/*  627:     */     {
/*  628: 666 */       LOG.error(e.getErrorMessage(e));
/*  629:     */     }
/*  630:     */     catch (Exception e)
/*  631:     */     {
/*  632: 668 */       LOG.error(e);
/*  633:     */     }
/*  634:     */   }
/*  635:     */   
/*  636:     */   public String actualizarDocumento()
/*  637:     */   {
/*  638:     */     try
/*  639:     */     {
/*  640: 674 */       this.servicioFacturaCliente.actualizarDocumento(getFacturaCliente(), isIndicadorAutoimpresor(), AppUtil.getPuntoDeVenta());
/*  641: 675 */       if (this.facturaCliente.getDocumento().isIndicadorDocumentoExterior())
/*  642:     */       {
/*  643: 676 */         this.listaDistritoAduanero = DatosSRI.getListaDistritoAduanero();
/*  644: 677 */         this.listaRegimen = DatosSRI.getListaRegimen();
/*  645:     */       }
/*  646: 680 */       setSecuenciaEditable(!this.facturaCliente.getDocumento().isIndicadorBloqueoSecuencia());
/*  647:     */     }
/*  648:     */     catch (ExcepcionAS2 e)
/*  649:     */     {
/*  650: 682 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  651:     */     }
/*  652: 685 */     return "";
/*  653:     */   }
/*  654:     */   
/*  655:     */   public void cargarCuentaPorCobrar()
/*  656:     */   {
/*  657:     */     try
/*  658:     */     {
/*  659: 695 */       this.servicioFacturaCliente.generarCuentaPorCobrar(this.facturaCliente);
/*  660:     */     }
/*  661:     */     catch (ExcepcionAS2 e)
/*  662:     */     {
/*  663: 697 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   public DetalleFacturaCliente cargarProducto()
/*  668:     */   {
/*  669: 706 */     DetalleFacturaCliente detalleFacturaCliente = null;
/*  670: 707 */     if (this.facturaCliente.getEmpresa() != null)
/*  671:     */     {
/*  672: 709 */       Producto producto = getListaProductoBean().getProducto();
/*  673:     */       
/*  674: 711 */       BigDecimal traCantidad = producto.getTraCantidad();
/*  675: 712 */       producto = this.servicioProducto.cargaDetalle(producto.getId());
/*  676: 713 */       producto.setTraCantidad(traCantidad);
/*  677: 715 */       if (producto != null)
/*  678:     */       {
/*  679:     */         try
/*  680:     */         {
/*  681: 717 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/*  682:     */         }
/*  683:     */         catch (ExcepcionAS2 e)
/*  684:     */         {
/*  685: 719 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  686:     */         }
/*  687: 721 */         detalleFacturaCliente = new DetalleFacturaCliente();
/*  688: 722 */         detalleFacturaCliente.setCantidad(traCantidad.setScale(4, RoundingMode.HALF_UP));
/*  689: 723 */         this.facturaCliente.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/*  690:     */         
/*  691: 725 */         actualizarProducto(detalleFacturaCliente, producto);
/*  692: 726 */         detalleFacturaCliente.setCantidad(traCantidad);
/*  693:     */       }
/*  694:     */     }
/*  695:     */     else
/*  696:     */     {
/*  697: 730 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  698: 731 */       getListaProductoBean().setProducto(null);
/*  699:     */     }
/*  700: 734 */     getListaProductoBean().setProducto(null);
/*  701: 735 */     return detalleFacturaCliente;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/*  705:     */   {
/*  706: 739 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/*  707:     */     {
/*  708: 740 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/*  709: 741 */       cargarProducto();
/*  710:     */     }
/*  711:     */   }
/*  712:     */   
/*  713:     */   public void cargarProducto(Producto producto)
/*  714:     */   {
/*  715: 746 */     getListaProductoBean().setProducto(producto);
/*  716: 747 */     getListaProductoBean().setSaldoProductoLote(null);
/*  717: 748 */     cargarProducto();
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void actualizarCliente(Empresa empresa)
/*  721:     */   {
/*  722: 759 */     if (empresa != null) {
/*  723: 761 */       if (TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA.equals(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/*  724: 762 */         .getTipoOrganizacion()))
/*  725:     */       {
/*  726: 764 */         if (empresa.getId() == ParametrosSistema.getClienteGenerico(AppUtil.getOrganizacion().getId()).intValue())
/*  727:     */         {
/*  728: 765 */           EntidadUsuario agenteComercial = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  729: 766 */           if (agenteComercial.isIndicadorAgenteComercial()) {
/*  730: 767 */             getFacturaCliente().setAgenteComercial(agenteComercial);
/*  731:     */           } else {
/*  732: 769 */             getFacturaCliente().setAgenteComercial(null);
/*  733:     */           }
/*  734:     */         }
/*  735:     */         else
/*  736:     */         {
/*  737: 772 */           getFacturaCliente().setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  738:     */         }
/*  739:     */       }
/*  740:     */       else {
/*  741: 776 */         getFacturaCliente().setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  742:     */       }
/*  743:     */     }
/*  744: 781 */     getFacturaCliente().setEmpresa(empresa);
/*  745: 782 */     getFacturaCliente().setDireccionEmpresa(null);
/*  746: 783 */     this.listaDireccionEmpresa = null;
/*  747: 784 */     getFacturaCliente().setEmail(this.servicioEmpresa.cargarMails(empresa, getFacturaCliente().getDocumento().getDocumentoBase()));
/*  748: 787 */     if (getFacturaCliente().getZona() == null) {
/*  749: 788 */       getFacturaCliente().setZona(empresa.getCliente().getZona());
/*  750:     */     }
/*  751: 791 */     getFacturaCliente().setCondicionPago(empresa.getCliente().getCondicionPago());
/*  752: 793 */     if (getFacturaCliente().getNumeroCuotas() == 0) {
/*  753: 794 */       getFacturaCliente().setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/*  754:     */     }
/*  755: 797 */     if ((getFacturaCliente().getEmail() != null) && (getFacturaCliente().getEmail().isEmpty())) {
/*  756: 798 */       getFacturaCliente().setEmail(empresa.getEmail1());
/*  757:     */     }
/*  758: 801 */     cargarDirecciones(empresa);
/*  759:     */     
/*  760: 803 */     cargarSubempresas();
/*  761:     */     
/*  762: 805 */     this.listaFormaPagoSRI = null;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public void actualizarClienteListener(SelectEvent event)
/*  766:     */   {
/*  767: 816 */     Empresa empresa = (Empresa)event.getObject();
/*  768: 817 */     actualizarCliente(empresa);
/*  769: 819 */     if (getTipoOrganizacion() != TipoOrganizacion.TIPO_ORGANIZACION_ADRIALPETRO) {
/*  770: 820 */       actualizarListaPedidoClienteADespachar();
/*  771:     */     }
/*  772:     */   }
/*  773:     */   
/*  774:     */   private void actualizarListaPedidoClienteADespachar()
/*  775:     */   {
/*  776: 828 */     getListaPedidoClientePorFacturar().clear();
/*  777: 830 */     if (getFacturaCliente().getEmpresa() != null)
/*  778:     */     {
/*  779: 831 */       HashMap<String, String> filters = new HashMap();
/*  780: 832 */       filters.put("empresa.idEmpresa", String.valueOf(getFacturaCliente().getEmpresa().getId()));
/*  781: 833 */       filters.put("estado", "=" + Estado.PROCESADO.toString());
/*  782:     */       
/*  783: 835 */       setListaPedidoClientePorFacturar(this.servicioPedidoCliente.obtenerListaCombo("numero", true, filters));
/*  784: 837 */       if ((this.facturaCliente.getPedidoCliente() != null) && (!getListaPedidoClientePorFacturar().contains(this.facturaCliente.getPedidoCliente()))) {
/*  785: 838 */         getListaPedidoClientePorFacturar().add(this.facturaCliente.getPedidoCliente());
/*  786:     */       }
/*  787:     */     }
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void seleccionarDocumentoModificadoListener(SelectEvent event)
/*  791:     */   {
/*  792: 844 */     FacturaCliente docModificado = (FacturaCliente)event.getObject();
/*  793: 845 */     getFacturaCliente().setProyecto(docModificado.getProyecto());
/*  794:     */   }
/*  795:     */   
/*  796:     */   public void cargarDirecciones(Empresa empresa)
/*  797:     */   {
/*  798: 853 */     if (getFacturaCliente().getSubempresa() != null) {
/*  799: 854 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getFacturaCliente().getSubempresa().getEmpresa().getId());
/*  800:     */     }
/*  801: 857 */     if ((this.listaDireccionEmpresa == null) || (this.listaDireccionEmpresa.isEmpty())) {
/*  802: 858 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(empresa.getId());
/*  803:     */     }
/*  804: 861 */     if (!empresa.isSoloLectura()) {
/*  805: 862 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/*  806: 863 */         if (de.isIndicadorDireccionPrincipal())
/*  807:     */         {
/*  808: 864 */           getFacturaCliente().setDireccionEmpresa(de);
/*  809: 865 */           break;
/*  810:     */         }
/*  811:     */       }
/*  812:     */     }
/*  813:     */   }
/*  814:     */   
/*  815:     */   public boolean isGuardarAjax()
/*  816:     */   {
/*  817: 874 */     return !isIndicadorAutoimpresor();
/*  818:     */   }
/*  819:     */   
/*  820:     */   public void cargarSubempresas()
/*  821:     */   {
/*  822: 878 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(getFacturaCliente().getEmpresa().getId());
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/*  826:     */   {
/*  827: 883 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/*  828: 885 */     if (subempresa != null)
/*  829:     */     {
/*  830: 887 */       Empresa empresa = subempresa.getEmpresa();
/*  831:     */       
/*  832: 889 */       getFacturaCliente().setSubempresa(subempresa);
/*  833: 890 */       getFacturaCliente().setDireccionEmpresa(null);
/*  834:     */       
/*  835:     */ 
/*  836: 893 */       getFacturaCliente().setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  837: 894 */       getFacturaCliente().setZona(empresa.getCliente().getZona());
/*  838: 895 */       getFacturaCliente().setCondicionPago(empresa.getCliente().getCondicionPago());
/*  839: 896 */       getFacturaCliente().setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/*  840: 897 */       cargarDirecciones(empresa);
/*  841: 898 */       getFacturaCliente().setEmail(this.servicioEmpresa.cargarMails(empresa, getFacturaCliente().getDocumento().getDocumentoBase()));
/*  842:     */     }
/*  843:     */   }
/*  844:     */   
/*  845:     */   public void dateSelect()
/*  846:     */   {
/*  847: 905 */     actualizarImpuestosTodos();
/*  848: 906 */     cargarCuentaPorCobrar();
/*  849:     */   }
/*  850:     */   
/*  851:     */   public String actualizarDatosExportaciones()
/*  852:     */   {
/*  853:     */     try
/*  854:     */     {
/*  855: 916 */       this.servicioFacturaClienteSRI.actualizarDatosExportacion(getFacturaCliente());
/*  856: 917 */       setRenderDialogDatosExportaciones(false);
/*  857: 918 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  858:     */     }
/*  859:     */     catch (ExcepcionAS2Ventas e)
/*  860:     */     {
/*  861: 920 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  862: 921 */       LOG.error("ERROR AL ACTUALIZAR DATOS EXPORTACIONES FACTURA CLIENTE", e);
/*  863:     */     }
/*  864:     */     catch (Exception e)
/*  865:     */     {
/*  866: 923 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  867: 924 */       LOG.error("ERROR AL ACTUALIZAR DATOS EXPORTACIONES FACTURA CLIENTE", e);
/*  868:     */     }
/*  869: 926 */     return "";
/*  870:     */   }
/*  871:     */   
/*  872:     */   public void cargarDatosFactura(ToggleEvent event)
/*  873:     */   {
/*  874: 930 */     this.totalDebito = BigDecimal.ZERO;
/*  875: 931 */     this.totalCredito = BigDecimal.ZERO;
/*  876: 932 */     getListaEstadoCuenta().clear();
/*  877: 933 */     FacturaCliente facturaClienteAux = (FacturaCliente)event.getData();
/*  878: 934 */     setFacturaCliente(facturaClienteAux);
/*  879: 935 */     Empresa empresa = facturaClienteAux.getEmpresa();
/*  880:     */     try
/*  881:     */     {
/*  882: 937 */       if (getFacturaCliente() != null)
/*  883:     */       {
/*  884: 938 */         String factura = getFacturaCliente().getNumero();
/*  885: 939 */         List<Object[]> reporteAux = this.servicioReporteVenta.getListaReporteEstadoCuenta(empresa.getId(), factura);
/*  886: 940 */         reporteEstadoCuentaFactura = null;
/*  887: 941 */         saldo = BigDecimal.ZERO;
/*  888: 943 */         for (Object[] object : reporteAux)
/*  889:     */         {
/*  890: 944 */           reporteEstadoCuentaFactura = new ReporteEstadoCuentaFactura();
/*  891: 945 */           reporteEstadoCuentaFactura.setFechaDocumento((Date)object[0]);
/*  892: 946 */           reporteEstadoCuentaFactura.setNumeroDocumento((String)object[1]);
/*  893: 947 */           reporteEstadoCuentaFactura.setFechaVencimiento((Date)object[2]);
/*  894: 948 */           reporteEstadoCuentaFactura.setNumeroFactura((String)object[3]);
/*  895: 949 */           BigDecimal debito = (BigDecimal)object[4];
/*  896: 950 */           BigDecimal credito = (BigDecimal)object[5];
/*  897: 951 */           reporteEstadoCuentaFactura.setDebito(debito);
/*  898: 952 */           reporteEstadoCuentaFactura.setCredito(credito);
/*  899: 953 */           reporteEstadoCuentaFactura.setCodigoDocumento((String)object[6]);
/*  900: 954 */           reporteEstadoCuentaFactura.setNombreDocumento((String)object[7]);
/*  901: 955 */           reporteEstadoCuentaFactura.setCodigoDocumentoProceso((String)object[8]);
/*  902: 956 */           reporteEstadoCuentaFactura.setIdCobro(object[9] == null ? 0 : ((Integer)object[9]).intValue());
/*  903: 957 */           reporteEstadoCuentaFactura.setIdFacturaCliente(object[10] == null ? 0 : ((Integer)object[10]).intValue());
/*  904: 958 */           reporteEstadoCuentaFactura.setDocumentoBase((DocumentoBase)(object[11] == null ? Integer.valueOf(0) : object[11]));
/*  905: 959 */           reporteEstadoCuentaFactura.setNumeroPacking((String)object[12]);
/*  906:     */           
/*  907: 961 */           this.totalDebito = this.totalDebito.add(debito);
/*  908: 962 */           this.totalCredito = this.totalCredito.add(credito);
/*  909:     */           
/*  910: 964 */           saldo = this.totalDebito.subtract(this.totalCredito);
/*  911:     */           
/*  912: 966 */           reporteEstadoCuentaFactura.setSaldo(saldo);
/*  913:     */           
/*  914: 968 */           getListaEstadoCuenta().add(reporteEstadoCuentaFactura);
/*  915:     */         }
/*  916:     */       }
/*  917:     */     }
/*  918:     */     catch (Exception e)
/*  919:     */     {
/*  920:     */       ReporteEstadoCuentaFactura reporteEstadoCuentaFactura;
/*  921:     */       BigDecimal saldo;
/*  922: 973 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public FacturaCliente getFacturaCliente()
/*  927:     */   {
/*  928: 990 */     return this.facturaCliente;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public void setFacturaCliente(FacturaCliente facturaCliente)
/*  932:     */   {
/*  933:1000 */     this.facturaCliente = facturaCliente;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public LazyDataModel<FacturaCliente> getListaFacturaCliente()
/*  937:     */   {
/*  938:1009 */     return this.listaFacturaCliente;
/*  939:     */   }
/*  940:     */   
/*  941:     */   public void setListaFacturaCliente(LazyDataModel<FacturaCliente> listaFacturaCliente)
/*  942:     */   {
/*  943:1019 */     this.listaFacturaCliente = listaFacturaCliente;
/*  944:     */   }
/*  945:     */   
/*  946:     */   public List<DetalleFacturaCliente> getDetalleFacturaCliente()
/*  947:     */   {
/*  948:1028 */     List<DetalleFacturaCliente> detalle = new ArrayList();
/*  949:1029 */     for (DetalleFacturaCliente dfc : getFacturaCliente().getListaDetalleFacturaCliente()) {
/*  950:1030 */       if (!dfc.isEliminado()) {
/*  951:1031 */         detalle.add(dfc);
/*  952:     */       }
/*  953:     */     }
/*  954:1034 */     return detalle;
/*  955:     */   }
/*  956:     */   
/*  957:     */   public void setDetalleFacturaCliente(List<DetalleFacturaCliente> detalleFacturaClientes)
/*  958:     */   {
/*  959:1044 */     getFacturaCliente().setListaDetalleFacturaCliente(detalleFacturaClientes);
/*  960:     */   }
/*  961:     */   
/*  962:     */   public DataTable getDtFacturaCliente()
/*  963:     */   {
/*  964:1053 */     return this.dtFacturaCliente;
/*  965:     */   }
/*  966:     */   
/*  967:     */   public void setDtFacturaCliente(DataTable dtFacturaCliente)
/*  968:     */   {
/*  969:1063 */     this.dtFacturaCliente = dtFacturaCliente;
/*  970:     */   }
/*  971:     */   
/*  972:     */   public DataTable getDtDetalleFacturaCliente()
/*  973:     */   {
/*  974:1072 */     return this.dtDetalleFacturaCliente;
/*  975:     */   }
/*  976:     */   
/*  977:     */   public void setDtDetalleFacturaCliente(DataTable dtDetalleFacturaCliente)
/*  978:     */   {
/*  979:1082 */     this.dtDetalleFacturaCliente = dtDetalleFacturaCliente;
/*  980:     */   }
/*  981:     */   
/*  982:     */   public List<Documento> getListaDocumentoCliente()
/*  983:     */   {
/*  984:1092 */     if (this.listaDocumentoCliente == null) {
/*  985:     */       try
/*  986:     */       {
/*  987:1094 */         this.listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.FACTURA_CLIENTE);
/*  988:     */       }
/*  989:     */       catch (ExcepcionAS2 e)
/*  990:     */       {
/*  991:1096 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  992:     */       }
/*  993:     */     }
/*  994:1099 */     return this.listaDocumentoCliente;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public void setListaDocumentoCliente(List<Documento> listaDocumentoCliente)
/*  998:     */   {
/*  999:1109 */     this.listaDocumentoCliente = listaDocumentoCliente;
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1003:     */   {
/* 1004:1118 */     if (this.listaDireccionEmpresa == null) {
/* 1005:1119 */       this.listaDireccionEmpresa = new ArrayList();
/* 1006:     */     }
/* 1007:1121 */     return this.listaDireccionEmpresa;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1011:     */   {
/* 1012:1131 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public List<ImpuestoProductoFacturaCliente> getListaImpuestoProductoFacturaCliente()
/* 1016:     */   {
/* 1017:1141 */     List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaClientes = new ArrayList();
/* 1018:1143 */     for (DetalleFacturaCliente dfc : getFacturaCliente().getListaDetalleFacturaCliente()) {
/* 1019:1145 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/* 1020:1146 */         if (!ipfc.isEliminado()) {
/* 1021:1147 */           listaImpuestoProductoFacturaClientes.add(ipfc);
/* 1022:     */         }
/* 1023:     */       }
/* 1024:     */     }
/* 1025:1153 */     return listaImpuestoProductoFacturaClientes;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public DataTable getDtImpuestoDetalleFacturaCliente()
/* 1029:     */   {
/* 1030:1163 */     return this.dtImpuestoDetalleFacturaCliente;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public void setDtImpuestoDetalleFacturaCliente(DataTable dtImpuestoDetalleFacturaCliente)
/* 1034:     */   {
/* 1035:1173 */     this.dtImpuestoDetalleFacturaCliente = dtImpuestoDetalleFacturaCliente;
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public List<CuentaPorCobrar> getListaCuentaPorCobrar()
/* 1039:     */   {
/* 1040:1182 */     List<CuentaPorCobrar> lista = new ArrayList();
/* 1041:1183 */     for (CuentaPorCobrar cuentaPorCobrar : this.facturaCliente.getListaCuentaPorCobrar()) {
/* 1042:1184 */       if (!cuentaPorCobrar.isEliminado()) {
/* 1043:1185 */         lista.add(cuentaPorCobrar);
/* 1044:     */       }
/* 1045:     */     }
/* 1046:1188 */     return lista;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public DataTable getDtCuentasPorCobrar()
/* 1050:     */   {
/* 1051:1197 */     return this.dtCuentasPorCobrar;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public void setDtCuentasPorCobrar(DataTable dtCuentasPorCobrar)
/* 1055:     */   {
/* 1056:1207 */     this.dtCuentasPorCobrar = dtCuentasPorCobrar;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public int getIdDocumentoAFacturar()
/* 1060:     */   {
/* 1061:1211 */     return this.idDocumentoAFacturar;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void setIdDocumentoAFacturar(int idDocumentoAFacturar)
/* 1065:     */   {
/* 1066:1215 */     this.idDocumentoAFacturar = idDocumentoAFacturar;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public List<SelectItem> getListaTipoDocumentoAFacturar()
/* 1070:     */   {
/* 1071:1219 */     return this.listaTipoDocumentoAFacturar;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void setListaTipoDocumentoAFacturar(List<SelectItem> listaTipoDocumentoAFacturar)
/* 1075:     */   {
/* 1076:1223 */     this.listaTipoDocumentoAFacturar = listaTipoDocumentoAFacturar;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaCliente()
/* 1080:     */   {
/* 1081:1232 */     List<DetalleFacturaCliente> detalle = new ArrayList();
/* 1082:1233 */     for (DetalleFacturaCliente detalleFacturaCliente : getFacturaCliente().getListaDetalleFacturaCliente()) {
/* 1083:1235 */       if (!detalleFacturaCliente.isEliminado()) {
/* 1084:1236 */         detalle.add(detalleFacturaCliente);
/* 1085:     */       }
/* 1086:     */     }
/* 1087:1240 */     return detalle;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public List<Empresa> autocompletarClientes(String consulta)
/* 1091:     */   {
/* 1092:1244 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public List<Ciudad> autocompletarCiudad(String consulta)
/* 1096:     */   {
/* 1097:1248 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public List<PedidoCliente> getListaPedidoClientePorFacturar()
/* 1101:     */   {
/* 1102:1255 */     return this.listaPedidoClientePorFacturar;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public void setListaPedidoClientePorFacturar(List<PedidoCliente> listaPedidoClientePorFacturar)
/* 1106:     */   {
/* 1107:1263 */     this.listaPedidoClientePorFacturar = listaPedidoClientePorFacturar;
/* 1108:     */   }
/* 1109:     */   
/* 1110:     */   public String cargarDatos()
/* 1111:     */   {
/* 1112:1273 */     return "";
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public List<SelectItem> getListaDistritoAduanero()
/* 1116:     */   {
/* 1117:1282 */     return this.listaDistritoAduanero;
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void setListaDistritoAduanero(List<SelectItem> listaDistritoAduanero)
/* 1121:     */   {
/* 1122:1292 */     this.listaDistritoAduanero = listaDistritoAduanero;
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   public List<SelectItem> getListaRegimen()
/* 1126:     */   {
/* 1127:1301 */     return this.listaRegimen;
/* 1128:     */   }
/* 1129:     */   
/* 1130:     */   public void setListaRegimen(List<SelectItem> listaRegimen)
/* 1131:     */   {
/* 1132:1311 */     this.listaRegimen = listaRegimen;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public List<SelectItem> getListaRefrendo()
/* 1136:     */   {
/* 1137:1320 */     if (this.listaRefrendo == null)
/* 1138:     */     {
/* 1139:1321 */       this.listaRefrendo = new ArrayList();
/* 1140:1322 */       for (RefrendoEnum refrendoEnum : RefrendoEnum.values()) {
/* 1141:1323 */         if (!refrendoEnum.equals(RefrendoEnum.NO_ESCOGER))
/* 1142:     */         {
/* 1143:1324 */           SelectItem item = new SelectItem(refrendoEnum, refrendoEnum.getNombre());
/* 1144:1325 */           this.listaRefrendo.add(item);
/* 1145:     */         }
/* 1146:     */       }
/* 1147:     */     }
/* 1148:1329 */     return this.listaRefrendo;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public void setListaRefrendo(List<SelectItem> listaRefrendo)
/* 1152:     */   {
/* 1153:1339 */     this.listaRefrendo = listaRefrendo;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public List<Subempresa> getListaSubempresa()
/* 1157:     */   {
/* 1158:1348 */     if (this.listaSubempresa == null) {
/* 1159:1349 */       this.listaSubempresa = new ArrayList();
/* 1160:     */     }
/* 1161:1351 */     return this.listaSubempresa;
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 1165:     */   {
/* 1166:1361 */     this.listaSubempresa = listaSubempresa;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public boolean isMostrarSaldoInicial()
/* 1170:     */   {
/* 1171:1370 */     Calendar calendario = Calendar.getInstance();
/* 1172:1371 */     calendario.setTime(ParametrosSistema.getFechaMaximaSaldosIniciales(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 1173:1372 */     int day = calendario.get(5);
/* 1174:1373 */     int month = calendario.get(2) + 1;
/* 1175:1374 */     int year = calendario.get(1);
/* 1176:     */     
/* 1177:1376 */     Date fecha = FuncionesUtiles.getFecha(day, month, year);
/* 1178:     */     
/* 1179:1378 */     this.mostrarSaldoInicial = FuncionesUtiles.compararFechaAnteriorOIgual(new Date(), fecha);
/* 1180:     */     
/* 1181:1380 */     return this.mostrarSaldoInicial;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public void setMostrarSaldoInicial(boolean mostrarSaldoInicial)
/* 1185:     */   {
/* 1186:1390 */     this.mostrarSaldoInicial = mostrarSaldoInicial;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public ReporteFacturaClienteBean getReporteFacturaClienteBean()
/* 1190:     */   {
/* 1191:1397 */     return this.reporteFacturaClienteBean;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public void setReporteFacturaClienteBean(ReporteFacturaClienteBean reporteFacturaClienteBean)
/* 1195:     */   {
/* 1196:1405 */     this.reporteFacturaClienteBean = reporteFacturaClienteBean;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public String getMails()
/* 1200:     */   {
/* 1201:1409 */     return this.mails;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public void setMails(String mails)
/* 1205:     */   {
/* 1206:1413 */     this.mails = mails;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public List<ReporteEstadoCuentaFactura> getListaEstadoCuenta()
/* 1210:     */   {
/* 1211:1417 */     if (this.listaEstadoCuenta == null) {
/* 1212:1418 */       this.listaEstadoCuenta = new ArrayList();
/* 1213:     */     }
/* 1214:1420 */     return this.listaEstadoCuenta;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public void setListaEstadoCuenta(List<ReporteEstadoCuentaFactura> listaEstadoCuenta)
/* 1218:     */   {
/* 1219:1424 */     this.listaEstadoCuenta = listaEstadoCuenta;
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public DataTable getDtListaReporte()
/* 1223:     */   {
/* 1224:1428 */     return this.dtListaReporte;
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public void setDtListaReporte(DataTable dtListaReporte)
/* 1228:     */   {
/* 1229:1432 */     this.dtListaReporte = dtListaReporte;
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public BigDecimal getTotalDebito()
/* 1233:     */   {
/* 1234:1436 */     return this.totalDebito;
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public void setTotalDebito(BigDecimal totalDebito)
/* 1238:     */   {
/* 1239:1440 */     this.totalDebito = totalDebito;
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   public BigDecimal getTotalCredito()
/* 1243:     */   {
/* 1244:1444 */     return this.totalCredito;
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   public void setTotalCredito(BigDecimal totalCredito)
/* 1248:     */   {
/* 1249:1448 */     this.totalCredito = totalCredito;
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public Integer getIdFacturaCliente()
/* 1253:     */   {
/* 1254:1452 */     return this.idFacturaCliente;
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 1258:     */   {
/* 1259:1456 */     this.idFacturaCliente = idFacturaCliente;
/* 1260:     */   }
/* 1261:     */   
/* 1262:     */   public List<SelectItem> getListaFormaPagoSRI()
/* 1263:     */   {
/* 1264:1460 */     if ((this.listaFormaPagoSRI == null) && (getFacturaCliente().getEmpresa() != null))
/* 1265:     */     {
/* 1266:1461 */       this.listaFormaPagoSRI = new ArrayList();
/* 1267:1463 */       for (Iterator localIterator1 = this.servicioFormaPagoSRI.getListaFormaPagoSRI(getFacturaCliente().getEmpresa()).iterator(); localIterator1.hasNext();)
/* 1268:     */       {
/* 1269:1463 */         formaPagoSRI = (FormaPagoSRI)localIterator1.next();
/* 1270:1464 */         for (SelectItem seleItem : DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId())) {
/* 1271:1465 */           if (formaPagoSRI.getCodigo().equals(seleItem.getValue().toString())) {
/* 1272:1466 */             this.listaFormaPagoSRI.add(seleItem);
/* 1273:     */           }
/* 1274:     */         }
/* 1275:     */       }
/* 1276:     */       FormaPagoSRI formaPagoSRI;
/* 1277:1470 */       if (this.listaFormaPagoSRI.size() == 0)
/* 1278:     */       {
/* 1279:1471 */         this.listaFormaPagoSRI.addAll(DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId()));
/* 1280:1472 */         if ((this.listaFormaPagoSRI.size() > 0) && (String.valueOf(true).equals(((SelectItem)this.listaFormaPagoSRI.get(0)).getDescription()))) {
/* 1281:1473 */           this.facturaCliente.getFacturaClienteSRI().setCodigoFormaPagoSRI(((SelectItem)this.listaFormaPagoSRI.get(0)).getValue().toString());
/* 1282:     */         }
/* 1283:     */       }
/* 1284:1477 */       else if ((getFacturaCliente().getFacturaClienteSRI() != null) && 
/* 1285:1478 */         (getFacturaCliente().getFacturaClienteSRI().getCodigoFormaPagoSRI() == null))
/* 1286:     */       {
/* 1287:1479 */         getFacturaCliente().getFacturaClienteSRI().setCodigoFormaPagoSRI(((SelectItem)this.listaFormaPagoSRI.get(0)).getValue().toString());
/* 1288:     */       }
/* 1289:     */     }
/* 1290:1483 */     return this.listaFormaPagoSRI;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public List<SelectItem> getListaTipoIngresoExterior()
/* 1294:     */   {
/* 1295:1487 */     if (this.listaTipoIngresoExterior == null)
/* 1296:     */     {
/* 1297:1488 */       this.listaTipoIngresoExterior = new ArrayList();
/* 1298:1489 */       this.listaTipoIngresoExterior.addAll(DatosSRI.getListaTipoIngresoExterior());
/* 1299:     */     }
/* 1300:1491 */     return this.listaTipoIngresoExterior;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public boolean isRenderDialogDatosExportaciones()
/* 1304:     */   {
/* 1305:1495 */     return this.renderDialogDatosExportaciones;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public void setRenderDialogDatosExportaciones(boolean renderDialogDatosExportaciones)
/* 1309:     */   {
/* 1310:1499 */     this.renderDialogDatosExportaciones = renderDialogDatosExportaciones;
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public AS2Exception getExContabilizacion()
/* 1314:     */   {
/* 1315:1506 */     return this.exContabilizacion;
/* 1316:     */   }
/* 1317:     */   
/* 1318:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1319:     */   {
/* 1320:1514 */     this.exContabilizacion = exContabilizacion;
/* 1321:     */   }
/* 1322:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.FacturaClienteBaseBean
 * JD-Core Version:    0.7.0.1
 */