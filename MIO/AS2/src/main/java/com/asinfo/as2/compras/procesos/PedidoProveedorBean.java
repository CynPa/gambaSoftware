/*    1:     */ package com.asinfo.as2.compras.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    6:     */ import com.asinfo.as2.controller.LanguageController;
/*    7:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   12:     */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   13:     */ import com.asinfo.as2.entities.Bodega;
/*   14:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   15:     */ import com.asinfo.as2.entities.CondicionPago;
/*   16:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   17:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   18:     */ import com.asinfo.as2.entities.DimensionContable;
/*   19:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   20:     */ import com.asinfo.as2.entities.Documento;
/*   21:     */ import com.asinfo.as2.entities.Empleado;
/*   22:     */ import com.asinfo.as2.entities.Empresa;
/*   23:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   24:     */ import com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor;
/*   25:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   26:     */ import com.asinfo.as2.entities.Organizacion;
/*   27:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   28:     */ import com.asinfo.as2.entities.PersonaResponsable;
/*   29:     */ import com.asinfo.as2.entities.Producto;
/*   30:     */ import com.asinfo.as2.entities.Proveedor;
/*   31:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   32:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   33:     */ import com.asinfo.as2.entities.Secuencia;
/*   34:     */ import com.asinfo.as2.entities.Sucursal;
/*   35:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   36:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   37:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   38:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   39:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   40:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*   41:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   42:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   43:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   44:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   45:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   46:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   47:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   48:     */ import com.asinfo.as2.util.AppUtil;
/*   49:     */ import com.asinfo.as2.util.RutaArchivo;
/*   50:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   51:     */ import com.asinfo.as2.utils.JsfUtil;
/*   52:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   53:     */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   54:     */ import java.io.BufferedInputStream;
/*   55:     */ import java.io.InputStream;
/*   56:     */ import java.math.BigDecimal;
/*   57:     */ import java.math.RoundingMode;
/*   58:     */ import java.util.ArrayList;
/*   59:     */ import java.util.Date;
/*   60:     */ import java.util.HashMap;
/*   61:     */ import java.util.List;
/*   62:     */ import java.util.Map;
/*   63:     */ import javax.annotation.PostConstruct;
/*   64:     */ import javax.ejb.EJB;
/*   65:     */ import javax.faces.bean.ManagedBean;
/*   66:     */ import javax.faces.bean.ManagedProperty;
/*   67:     */ import javax.faces.bean.ViewScoped;
/*   68:     */ import javax.faces.component.html.HtmlInputText;
/*   69:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   70:     */ import org.apache.log4j.Logger;
/*   71:     */ import org.primefaces.component.datatable.DataTable;
/*   72:     */ import org.primefaces.event.FileUploadEvent;
/*   73:     */ import org.primefaces.event.SelectEvent;
/*   74:     */ import org.primefaces.model.LazyDataModel;
/*   75:     */ import org.primefaces.model.SortOrder;
/*   76:     */ import org.primefaces.model.UploadedFile;
/*   77:     */ 
/*   78:     */ @ManagedBean
/*   79:     */ @ViewScoped
/*   80:     */ public class PedidoProveedorBean
/*   81:     */   extends PageControllerAS2
/*   82:     */ {
/*   83:     */   private static final long serialVersionUID = 725178972436303995L;
/*   84:     */   @EJB
/*   85:     */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*   86:     */   @EJB
/*   87:     */   private ServicioProducto servicioProducto;
/*   88:     */   @EJB
/*   89:     */   private ServicioDocumento servicioDocumento;
/*   90:     */   @EJB
/*   91:     */   private ServicioEmpresa servicioEmpresa;
/*   92:     */   @EJB
/*   93:     */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*   94:     */   @EJB
/*   95:     */   private ServicioCondicionPago servicioCondicionPago;
/*   96:     */   @EJB
/*   97:     */   private ServicioSucursal servicioSucursal;
/*   98:     */   @EJB
/*   99:     */   protected transient ServicioListaPrecios servicioListaPrecios;
/*  100:     */   @EJB
/*  101:     */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  102:     */   @EJB
/*  103:     */   private ServicioUsuario servicioUsuario;
/*  104:     */   private PedidoProveedor pedidoProveedor;
/*  105:     */   private LazyDataModel<PedidoProveedor> listaPedidoProveedor;
/*  106:     */   private List<Documento> listaDocumentoProveedor;
/*  107:     */   private List<Empresa> listaEmpresaProveedor;
/*  108:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  109:     */   private List<Sucursal> listaSucursal;
/*  110:     */   private List<CondicionPago> listaCondicionPago;
/*  111:     */   private List<PersonaResponsable> listaPersonaResponsable;
/*  112:     */   private boolean indicadorIngresoPrecioTotal;
/*  113:     */   private DataTable dtPedidoProveedor;
/*  114:     */   private DataTable dtDetallePedidoProveedor;
/*  115:     */   private DataTable dtImpuestoDetallePedidoProveedor;
/*  116:     */   private DetallePedidoProveedor detallePedidoProveedorSeleccionado;
/*  117:     */   private List<Bodega> listaBodega;
/*  118:     */   private Integer idPedidoProveedor;
/*  119:     */   @ManagedProperty("#{listaProductoBean}")
/*  120:     */   private ListaProductoBean listaProductoBean;
/*  121:     */   @ManagedProperty("#{listaDimensionContableBean}")
/*  122:     */   private ListaDimensionContableBean listaDimensionContableBean;
/*  123: 144 */   private Boolean indicadorParametroEditarPrecioPedido = null;
/*  124: 145 */   private boolean indicadorCambiarPrecio = false;
/*  125:     */   @Emails
/*  126:     */   private String mails;
/*  127:     */   
/*  128:     */   @PostConstruct
/*  129:     */   public void init()
/*  130:     */   {
/*  131: 153 */     this.listaProductoBean.setIndicadorCompra(true);
/*  132: 154 */     this.listaProductoBean.setActivo(true);
/*  133: 155 */     setDocumentoBase(DocumentoBase.PEDIDO_PROVEEDOR);
/*  134:     */     
/*  135: 157 */     this.listaPedidoProveedor = new LazyDataModel()
/*  136:     */     {
/*  137:     */       private static final long serialVersionUID = 1L;
/*  138:     */       
/*  139:     */       public List<PedidoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  140:     */       {
/*  141: 164 */         filters = PedidoProveedorBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  142:     */         
/*  143: 166 */         List<PedidoProveedor> lista = new ArrayList();
/*  144: 168 */         if (PedidoProveedorBean.this.idPedidoProveedor != null)
/*  145:     */         {
/*  146: 169 */           filters.put("idPedidoProveedor", PedidoProveedorBean.this.idPedidoProveedor.toString());
/*  147: 170 */           PedidoProveedorBean.this.idPedidoProveedor = null;
/*  148:     */         }
/*  149: 173 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  150:     */         try
/*  151:     */         {
/*  152: 176 */           lista = PedidoProveedorBean.this.servicioPedidoProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  153: 177 */           PedidoProveedorBean.this.listaPedidoProveedor.setRowCount(PedidoProveedorBean.this.servicioPedidoProveedor.contarPorCriterio(filters));
/*  154:     */         }
/*  155:     */         catch (ExcepcionAS2 e)
/*  156:     */         {
/*  157: 180 */           PedidoProveedorBean.this.addInfoMessage(PedidoProveedorBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/*  158: 181 */           PedidoProveedorBean.LOG.info("ERROR AL CARGAR DATOS PEDIDO PROVEEDOR", e);
/*  159:     */         }
/*  160: 184 */         return lista;
/*  161:     */       }
/*  162:     */     };
/*  163:     */   }
/*  164:     */   
/*  165:     */   public String editar()
/*  166:     */   {
/*  167:     */     try
/*  168:     */     {
/*  169: 198 */       if (getPedidoProveedor().getId() != 0)
/*  170:     */       {
/*  171: 200 */         this.pedidoProveedor = this.servicioPedidoProveedor.cargarDetalle(Integer.valueOf(this.pedidoProveedor.getId()));
/*  172: 202 */         if (getPedidoProveedor().getEstado().equals(Estado.ELABORADO))
/*  173:     */         {
/*  174: 204 */           this.servicioPedidoProveedor.esEditable(this.pedidoProveedor);
/*  175:     */           
/*  176: 206 */           cargarDirecciones();
/*  177: 207 */           totalizar();
/*  178: 208 */           setEditado(true);
/*  179:     */         }
/*  180:     */         else
/*  181:     */         {
/*  182: 210 */           addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/*  183:     */         }
/*  184:     */       }
/*  185:     */       else
/*  186:     */       {
/*  187: 214 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  188:     */       }
/*  189:     */     }
/*  190:     */     catch (ExcepcionAS2Financiero e)
/*  191:     */     {
/*  192: 218 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  193: 219 */       LOG.info("ERROR AL EDITAR PEDIDO DE PROVEEDOR:", e);
/*  194:     */     }
/*  195:     */     catch (ExcepcionAS2Compras e)
/*  196:     */     {
/*  197: 222 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  198: 223 */       LOG.info("ERROR AL EDITAR PEDIDO DE PROVEEDOR: " + e.getErrorMessage(e));
/*  199:     */     }
/*  200:     */     catch (Exception e)
/*  201:     */     {
/*  202: 226 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  203: 227 */       LOG.error("ERROR AL EDITAR PEDIDO DE PROVEEDOR:", e);
/*  204:     */     }
/*  205: 229 */     return "";
/*  206:     */   }
/*  207:     */   
/*  208:     */   public String guardar()
/*  209:     */   {
/*  210:     */     try
/*  211:     */     {
/*  212: 241 */       this.servicioPedidoProveedor.guardar(this.pedidoProveedor);
/*  213:     */       
/*  214: 243 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  215:     */       
/*  216: 245 */       cargarDatos();
/*  217:     */     }
/*  218:     */     catch (ExcepcionAS2Financiero e)
/*  219:     */     {
/*  220: 248 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  221: 249 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO PROVEEDOR", e);
/*  222:     */     }
/*  223:     */     catch (AS2Exception e)
/*  224:     */     {
/*  225: 252 */       JsfUtil.addErrorMessage(e, "");
/*  226:     */     }
/*  227:     */     catch (ExcepcionAS2Compras e)
/*  228:     */     {
/*  229: 254 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  230: 255 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO PROVEEDOR", e);
/*  231:     */     }
/*  232:     */     catch (ExcepcionAS2 e)
/*  233:     */     {
/*  234: 258 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  235: 259 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO PROVEEDOR", e);
/*  236:     */     }
/*  237:     */     catch (Exception e)
/*  238:     */     {
/*  239: 262 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  240: 263 */       LOG.error("ERROR AL GUARDAR DATOS PEDIDO PROVEEDOR", e);
/*  241:     */     }
/*  242: 265 */     return "";
/*  243:     */   }
/*  244:     */   
/*  245:     */   public String eliminar()
/*  246:     */   {
/*  247:     */     try
/*  248:     */     {
/*  249: 277 */       this.servicioPedidoProveedor.anular(this.pedidoProveedor);
/*  250: 278 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  251:     */     }
/*  252:     */     catch (ExcepcionAS2Compras e)
/*  253:     */     {
/*  254: 281 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  255: 282 */       LOG.info("ERROR AL ANULAR DATOS PEDIDO PROVEEDOR", e);
/*  256:     */     }
/*  257:     */     catch (ExcepcionAS2Financiero e)
/*  258:     */     {
/*  259: 284 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  260: 285 */       LOG.info("ERROR AL ANULAR DATOS PEDIDO PROVEEDOR", e);
/*  261:     */     }
/*  262:     */     catch (Exception e)
/*  263:     */     {
/*  264: 287 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  265: 288 */       LOG.info("ERROR AL ANULAR DATOS PEDIDO PROVEEDOR", e);
/*  266:     */     }
/*  267: 290 */     return "";
/*  268:     */   }
/*  269:     */   
/*  270:     */   public void seleccionarDimensionContableListener(SelectEvent event)
/*  271:     */   {
/*  272:     */     try
/*  273:     */     {
/*  274: 295 */       DimensionContable dimension = (DimensionContable)event.getObject();
/*  275:     */       
/*  276: 297 */       String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/*  277: 298 */       if (numeroDimension.equals("1")) {
/*  278: 299 */         this.detallePedidoProveedorSeleccionado.setDimensionContable1(dimension);
/*  279: 300 */       } else if (numeroDimension.equals("2")) {
/*  280: 301 */         this.detallePedidoProveedorSeleccionado.setDimensionContable2(dimension);
/*  281: 302 */       } else if (numeroDimension.equals("3")) {
/*  282: 303 */         this.detallePedidoProveedorSeleccionado.setDimensionContable3(dimension);
/*  283: 304 */       } else if (numeroDimension.equals("4")) {
/*  284: 305 */         this.detallePedidoProveedorSeleccionado.setDimensionContable4(dimension);
/*  285: 306 */       } else if (numeroDimension.equals("5")) {
/*  286: 307 */         this.detallePedidoProveedorSeleccionado.setDimensionContable5(dimension);
/*  287:     */       }
/*  288:     */     }
/*  289:     */     catch (Exception e)
/*  290:     */     {
/*  291: 311 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  292: 312 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/*  293:     */     }
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void seleccionarDetalle(DetallePedidoProveedor detallePedidoProveedor)
/*  297:     */   {
/*  298: 317 */     this.detallePedidoProveedorSeleccionado = detallePedidoProveedor;
/*  299:     */   }
/*  300:     */   
/*  301:     */   public String limpiar()
/*  302:     */   {
/*  303: 327 */     setEditado(false);
/*  304:     */     
/*  305: 329 */     crearPedidoProveedor();
/*  306:     */     
/*  307: 331 */     this.indicadorCambiarPrecio = false;
/*  308:     */     
/*  309: 333 */     return "";
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void crearPedidoProveedor()
/*  313:     */   {
/*  314: 340 */     this.pedidoProveedor = new PedidoProveedor();
/*  315: 341 */     this.pedidoProveedor.setSucursal(AppUtil.getSucursal());
/*  316: 342 */     this.pedidoProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  317: 343 */     this.pedidoProveedor.setNumero("");
/*  318: 344 */     this.pedidoProveedor.setFecha(new Date());
/*  319: 345 */     this.pedidoProveedor.setEstado(Estado.ELABORADO);
/*  320: 346 */     this.pedidoProveedor.setBodega(AppUtil.getBodega());
/*  321:     */     
/*  322: 348 */     Documento documento = null;
/*  323: 349 */     if ((getListaDocumentoProveedor() != null) && (!getListaDocumentoProveedor().isEmpty()))
/*  324:     */     {
/*  325: 351 */       for (Documento d : getListaDocumentoProveedor())
/*  326:     */       {
/*  327: 352 */         if (d.isPredeterminado())
/*  328:     */         {
/*  329: 353 */           documento = d;
/*  330: 354 */           break;
/*  331:     */         }
/*  332: 356 */         documento = new Documento();
/*  333:     */       }
/*  334: 359 */       this.pedidoProveedor.setDocumento(documento);
/*  335: 360 */       if (documento.getId() != 0) {
/*  336: 361 */         actualizarDocumento();
/*  337:     */       }
/*  338:     */     }
/*  339:     */     else
/*  340:     */     {
/*  341: 364 */       documento = new Documento();
/*  342: 365 */       documento.setSecuencia(new Secuencia());
/*  343: 366 */       this.pedidoProveedor.setDocumento(documento);
/*  344:     */     }
/*  345: 369 */     this.listaDireccionEmpresa = new ArrayList();
/*  346:     */     
/*  347: 371 */     cargarPersonaResponsable();
/*  348:     */   }
/*  349:     */   
/*  350:     */   public void actualizarPorcentajeDescuento()
/*  351:     */   {
/*  352: 375 */     totalizar();
/*  353:     */   }
/*  354:     */   
/*  355:     */   public String cargarDatos()
/*  356:     */   {
/*  357:     */     try
/*  358:     */     {
/*  359: 386 */       setEditado(false);
/*  360: 387 */       limpiar();
/*  361:     */     }
/*  362:     */     catch (Exception e)
/*  363:     */     {
/*  364: 389 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  365: 390 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  366:     */     }
/*  367: 392 */     return "";
/*  368:     */   }
/*  369:     */   
/*  370:     */   public String agregarDetalle()
/*  371:     */   {
/*  372: 401 */     DetallePedidoProveedor d = new DetallePedidoProveedor();
/*  373: 402 */     d.setPedidoProveedor(getPedidoProveedor());
/*  374: 403 */     d.setCantidad(BigDecimal.ZERO);
/*  375: 404 */     d.setPrecio(BigDecimal.ZERO);
/*  376: 405 */     d.setProducto(new Producto());
/*  377: 406 */     getPedidoProveedor().getListaDetallePedidoProveedor().add(d);
/*  378: 407 */     return "";
/*  379:     */   }
/*  380:     */   
/*  381:     */   public String eliminarDetalle()
/*  382:     */   {
/*  383: 416 */     DetallePedidoProveedor d = (DetallePedidoProveedor)this.dtDetallePedidoProveedor.getRowData();
/*  384: 418 */     for (ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor : d.getListaImpuestoProductoPedidoProveedor())
/*  385:     */     {
/*  386: 419 */       impuestoProductoPedidoProveedor.setEliminado(true);
/*  387: 420 */       impuestoProductoPedidoProveedor.setTraAuxiliarEliminaImpuesto(true);
/*  388:     */     }
/*  389: 422 */     d.setEliminado(true);
/*  390: 423 */     d.setCantidad(BigDecimal.ZERO);
/*  391: 424 */     totalizar();
/*  392: 425 */     return "";
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void actualizarProducto(AjaxBehaviorEvent event)
/*  396:     */   {
/*  397: 435 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  398: 436 */     DetallePedidoProveedor dpp = (DetallePedidoProveedor)this.dtDetallePedidoProveedor.getRowData();
/*  399:     */     try
/*  400:     */     {
/*  401: 439 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.PEDIDO_PROVEEDOR);
/*  402: 440 */       actualizarProducto(dpp, producto);
/*  403:     */     }
/*  404:     */     catch (ExcepcionAS2 e)
/*  405:     */     {
/*  406: 442 */       e.printStackTrace();
/*  407: 443 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411:     */   public void actualizarProducto(DetallePedidoProveedor dpp, Producto producto)
/*  412:     */   {
/*  413: 454 */     if (this.pedidoProveedor.getEmpresa() != null)
/*  414:     */     {
/*  415: 456 */       dpp.setPedidoProveedor(this.pedidoProveedor);
/*  416: 458 */       for (ImpuestoProductoPedidoProveedor ippc : dpp.getListaImpuestoProductoPedidoProveedor()) {
/*  417: 459 */         ippc.setEliminado(true);
/*  418:     */       }
/*  419: 462 */       dpp.setProducto(producto);
/*  420: 463 */       dpp.setDescripcion(producto.getNombre());
/*  421: 464 */       dpp.setUnidadCompra(producto.getUnidadCompra());
/*  422: 465 */       dpp.setPedidoProveedor(this.pedidoProveedor);
/*  423: 466 */       dpp.setIndicadorImpuestos(producto.isIndicadorImpuestos());
/*  424: 468 */       if (dpp.isIndicadorImpuestos()) {
/*  425: 469 */         obtenerImpuestosProductos(dpp.getProducto(), dpp);
/*  426:     */       }
/*  427:     */       try
/*  428:     */       {
/*  429: 474 */         Integer idZona = null;
/*  430: 476 */         if (this.pedidoProveedor.getEmpresa().getProveedor().getListaPrecios() != null)
/*  431:     */         {
/*  432: 477 */           DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.pedidoProveedor
/*  433: 478 */             .getEmpresa().getProveedor().getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), this.pedidoProveedor
/*  434: 479 */             .getFecha(), idZona, "");
/*  435: 480 */           dpp.setPrecio(dvlp.getPrecioUnitario());
/*  436:     */         }
/*  437: 482 */         else if (dpp.getPrecio().compareTo(BigDecimal.ZERO) == 0)
/*  438:     */         {
/*  439: 484 */           dpp.setPrecio(dpp.getProducto().getPrecioUltimaCompra());
/*  440: 485 */           dpp.setPrecioLinea(dpp.getPrecio().multiply(dpp.getCantidad()));
/*  441:     */         }
/*  442:     */       }
/*  443:     */       catch (ExcepcionAS2 e)
/*  444:     */       {
/*  445: 490 */         e.printStackTrace();
/*  446: 491 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  447: 492 */         LOG.error("ERROR RECUPERAR PRODUCTO", e);
/*  448:     */       }
/*  449:     */       catch (Exception e)
/*  450:     */       {
/*  451: 494 */         e.printStackTrace();
/*  452: 495 */         addInfoMessage("msg_error_cargar_datos");
/*  453: 496 */         LOG.error("ERROR RECUPERAR PRODUCTO", e);
/*  454:     */       }
/*  455: 498 */       totalizar();
/*  456:     */     }
/*  457:     */     else
/*  458:     */     {
/*  459: 501 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  460: 502 */       dpp.getProducto().setCodigo("");
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public List<DetallePedidoProveedor> getDetallePedidoProveedor()
/*  465:     */   {
/*  466: 512 */     List<DetallePedidoProveedor> detalle = new ArrayList();
/*  467: 513 */     for (DetallePedidoProveedor dpp : getPedidoProveedor().getListaDetallePedidoProveedor()) {
/*  468: 514 */       if (!dpp.isEliminado()) {
/*  469: 515 */         detalle.add(dpp);
/*  470:     */       }
/*  471:     */     }
/*  472: 518 */     return detalle;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public void totalizar()
/*  476:     */   {
/*  477:     */     try
/*  478:     */     {
/*  479: 528 */       this.servicioPedidoProveedor.totalizar(this.pedidoProveedor);
/*  480:     */     }
/*  481:     */     catch (ExcepcionAS2Compras e)
/*  482:     */     {
/*  483: 530 */       LOG.info(e.getErrorMessage(e));
/*  484:     */     }
/*  485:     */     catch (Exception e)
/*  486:     */     {
/*  487: 532 */       LOG.info(e);
/*  488:     */     }
/*  489:     */   }
/*  490:     */   
/*  491:     */   public String actualizarDocumento()
/*  492:     */   {
/*  493: 537 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.pedidoProveedor.getDocumento().getId()));
/*  494: 538 */     this.pedidoProveedor.setDocumento(documento);
/*  495:     */     
/*  496: 540 */     setSecuenciaEditable(!this.pedidoProveedor.getDocumento().isIndicadorBloqueoSecuencia());
/*  497:     */     
/*  498: 542 */     return "";
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void actualizarProveedor(SelectEvent event)
/*  502:     */   {
/*  503: 552 */     Empresa empresa = (Empresa)event.getObject();
/*  504: 553 */     getPedidoProveedor().setEmpresa(empresa);
/*  505:     */     
/*  506: 555 */     getPedidoProveedor().setCondicionPago(empresa.getProveedor().getCondicionPago());
/*  507: 556 */     getPedidoProveedor().setNumeroCuotas(empresa.getProveedor().getNumeroCuotas());
/*  508: 557 */     this.servicioPedidoProveedor.cargaDatosProveedor(getPedidoProveedor());
/*  509: 558 */     getPedidoProveedor().setDireccionEmpresa(null);
/*  510: 559 */     cargarDirecciones();
/*  511:     */   }
/*  512:     */   
/*  513:     */   public void cargarDirecciones()
/*  514:     */   {
/*  515: 568 */     this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getPedidoProveedor().getEmpresa().getId());
/*  516: 570 */     if (getPedidoProveedor().getDireccionEmpresa() == null) {
/*  517: 573 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/*  518: 574 */         if (de.isIndicadorDireccionPrincipal())
/*  519:     */         {
/*  520: 575 */           getPedidoProveedor().setDireccionEmpresa(de);
/*  521: 576 */           break;
/*  522:     */         }
/*  523:     */       }
/*  524:     */     }
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/*  528:     */   {
/*  529: 584 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/*  530:     */     {
/*  531: 585 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/*  532: 586 */       cargarProducto();
/*  533:     */     }
/*  534:     */   }
/*  535:     */   
/*  536:     */   public void cargarProducto(Producto producto)
/*  537:     */   {
/*  538: 591 */     getListaProductoBean().setProducto(producto);
/*  539: 592 */     getListaProductoBean().setSaldoProductoLote(null);
/*  540: 593 */     cargarProducto();
/*  541:     */   }
/*  542:     */   
/*  543:     */   public void cargarProducto()
/*  544:     */   {
/*  545: 601 */     Producto producto = getListaProductoBean().getProducto();
/*  546: 602 */     if (producto != null)
/*  547:     */     {
/*  548: 603 */       DetallePedidoProveedor detallePedidoProveedor = new DetallePedidoProveedor();
/*  549: 604 */       detallePedidoProveedor.setProducto(producto);
/*  550: 605 */       detallePedidoProveedor.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/*  551: 606 */       this.pedidoProveedor.getListaDetallePedidoProveedor().add(detallePedidoProveedor);
/*  552: 607 */       detallePedidoProveedor.setCantidad(producto.getTraCantidad());
/*  553: 608 */       actualizarProducto(detallePedidoProveedor, producto);
/*  554:     */     }
/*  555: 610 */     getListaProductoBean().setProducto(null);
/*  556:     */   }
/*  557:     */   
/*  558:     */   public void obtenerImpuestosProductos(Producto producto, DetallePedidoProveedor d)
/*  559:     */   {
/*  560:     */     try
/*  561:     */     {
/*  562: 623 */       producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/*  563:     */       
/*  564: 625 */       List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, d.getPedidoProveedor().getFecha());
/*  565: 627 */       for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/*  566:     */       {
/*  567: 628 */         ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor = new ImpuestoProductoPedidoProveedor();
/*  568: 629 */         impuestoProductoPedidoProveedor.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/*  569: 630 */         impuestoProductoPedidoProveedor.setImpuesto(rangoImpuesto.getImpuesto());
/*  570: 631 */         impuestoProductoPedidoProveedor.setDetallePedidoProveedor(d);
/*  571: 632 */         d.getListaImpuestoProductoPedidoProveedor().add(impuestoProductoPedidoProveedor);
/*  572:     */       }
/*  573:     */     }
/*  574:     */     catch (ExcepcionAS2 ex)
/*  575:     */     {
/*  576: 635 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/*  577: 636 */       LOG.info("Error es: " + ex.getErrorMessage(ex));
/*  578:     */     }
/*  579:     */     catch (Exception e)
/*  580:     */     {
/*  581: 638 */       LOG.info(e);
/*  582:     */     }
/*  583:     */   }
/*  584:     */   
/*  585:     */   public void actualizarImpuesto()
/*  586:     */     throws ExcepcionAS2Inventario
/*  587:     */   {
/*  588: 650 */     DetallePedidoProveedor d = (DetallePedidoProveedor)this.dtDetallePedidoProveedor.getRowData();
/*  589: 652 */     for (ImpuestoProductoPedidoProveedor ipfp : d.getListaImpuestoProductoPedidoProveedor()) {
/*  590: 653 */       ipfp.setEliminado(true);
/*  591:     */     }
/*  592: 656 */     if (d.isIndicadorImpuestos()) {
/*  593: 657 */       this.servicioPedidoProveedor.obtenerImpuestosProductos(d.getProducto(), d);
/*  594:     */     }
/*  595: 660 */     totalizar();
/*  596:     */   }
/*  597:     */   
/*  598:     */   public String cargarDetallePedidoProveedorExcel(FileUploadEvent event)
/*  599:     */   {
/*  600:     */     try
/*  601:     */     {
/*  602: 666 */       String fileName = "pedido_proveedor" + event.getFile().getFileName();
/*  603: 667 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  604: 668 */       this.servicioPedidoProveedor.cargarDetallePedidoProveedorExcel(this.pedidoProveedor, fileName, input, 5);
/*  605: 669 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  606:     */     }
/*  607:     */     catch (ExcepcionAS2 e)
/*  608:     */     {
/*  609: 672 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  610: 673 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  611:     */     }
/*  612:     */     catch (Exception e)
/*  613:     */     {
/*  614: 676 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  615: 677 */       e.printStackTrace();
/*  616:     */     }
/*  617: 679 */     return null;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public String copiar()
/*  621:     */   {
/*  622:     */     try
/*  623:     */     {
/*  624: 686 */       PedidoProveedor auxPedidoProveedorCopia = this.servicioPedidoProveedor.cargarDetalle(Integer.valueOf(this.pedidoProveedor.getId()));
/*  625: 687 */       this.pedidoProveedor = this.servicioPedidoProveedor.copiarPedidoProveedor(auxPedidoProveedorCopia);
/*  626: 688 */       this.pedidoProveedor.setCondicionPago(auxPedidoProveedorCopia.getCondicionPago());
/*  627: 689 */       cargarDirecciones();
/*  628:     */     }
/*  629:     */     catch (ExcepcionAS2Compras e)
/*  630:     */     {
/*  631: 691 */       e.printStackTrace();
/*  632: 692 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  633:     */     }
/*  634:     */     catch (ExcepcionAS2Financiero e)
/*  635:     */     {
/*  636: 694 */       e.printStackTrace();
/*  637: 695 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  638:     */     }
/*  639: 697 */     actualizarImpuestosListener();
/*  640: 698 */     setEditado(true);
/*  641:     */     
/*  642: 700 */     return "";
/*  643:     */   }
/*  644:     */   
/*  645:     */   public void actualizarImpuestosListener()
/*  646:     */   {
/*  647: 704 */     for (DetallePedidoProveedor dpp : this.pedidoProveedor.getListaDetallePedidoProveedor()) {
/*  648: 705 */       actualizarProducto(dpp, dpp.getProducto());
/*  649:     */     }
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void enviarMail()
/*  653:     */   {
/*  654: 710 */     if ((getPedidoProveedor() != null) && (getPedidoProveedor().getId() != 0))
/*  655:     */     {
/*  656: 711 */       if (getPedidoProveedor().getEstado().equals(Estado.ANULADO)) {
/*  657: 712 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  658:     */       } else {
/*  659: 714 */         this.servicioPedidoProveedor.enviarEmail(getPedidoProveedor(), this.mails);
/*  660:     */       }
/*  661:     */     }
/*  662:     */     else {
/*  663: 717 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void processDownload()
/*  668:     */   {
/*  669:     */     try
/*  670:     */     {
/*  671: 728 */       PedidoProveedor fp = (PedidoProveedor)this.dtPedidoProveedor.getRowData();
/*  672: 729 */       String fileName = fp.getPdf();
/*  673: 730 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pedido_proveedor");
/*  674: 732 */       if (fileName == null) {
/*  675: 733 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  676:     */       } else {
/*  677: 735 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  678:     */       }
/*  679:     */     }
/*  680:     */     catch (Exception e)
/*  681:     */     {
/*  682: 739 */       e.printStackTrace();
/*  683: 740 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  684:     */     }
/*  685:     */   }
/*  686:     */   
/*  687:     */   public String eliminarArchivo()
/*  688:     */   {
/*  689: 745 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getPedidoProveedor().getPdf());
/*  690: 746 */     getPedidoProveedor().setPdf(null);
/*  691: 747 */     HashMap<String, Object> campos = new HashMap();
/*  692: 748 */     campos.put("pdf", null);
/*  693: 749 */     this.servicioPedidoProveedor.actualizarAtributoEntidad(getPedidoProveedor(), campos);
/*  694: 750 */     return null;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void processUpload(FileUploadEvent event)
/*  698:     */   {
/*  699:     */     try
/*  700:     */     {
/*  701: 763 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pedido_proveedor");
/*  702:     */       
/*  703: 765 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getPedidoProveedor().getNumero(), uploadDir);
/*  704:     */       
/*  705:     */ 
/*  706: 768 */       HashMap<String, Object> campos = new HashMap();
/*  707: 769 */       campos.put("pdf", fileName);
/*  708: 770 */       this.servicioPedidoProveedor.actualizarAtributoEntidad(getPedidoProveedor(), campos);
/*  709:     */       
/*  710: 772 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  711:     */     }
/*  712:     */     catch (Exception e)
/*  713:     */     {
/*  714: 775 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  715: 776 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  716:     */     }
/*  717:     */   }
/*  718:     */   
/*  719:     */   public String getDirectorioDescarga()
/*  720:     */   {
/*  721: 783 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pedido_proveedor");
/*  722:     */   }
/*  723:     */   
/*  724:     */   public boolean isIndicadorIngresoPrecioTotal()
/*  725:     */   {
/*  726: 792 */     this.indicadorIngresoPrecioTotal = ParametrosSistema.getIndicadorIngresoComprasPrecioTotal(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue();
/*  727: 793 */     return this.indicadorIngresoPrecioTotal;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void setDetallePedidoProveedor(List<DetallePedidoProveedor> detallePedidoProveedor)
/*  731:     */   {
/*  732: 797 */     getPedidoProveedor().setListaDetallePedidoProveedor(detallePedidoProveedor);
/*  733:     */   }
/*  734:     */   
/*  735:     */   public PedidoProveedor getPedidoProveedor()
/*  736:     */   {
/*  737: 801 */     return this.pedidoProveedor;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/*  741:     */   {
/*  742: 805 */     this.pedidoProveedor = pedidoProveedor;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public LazyDataModel<PedidoProveedor> getListaPedidoProveedor()
/*  746:     */   {
/*  747: 809 */     if (this.listaPedidoProveedor == null) {
/*  748: 810 */       cargarDatos();
/*  749:     */     }
/*  750: 812 */     return this.listaPedidoProveedor;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public void setListaPedidoProveedor(LazyDataModel<PedidoProveedor> listaPedidoProveedor)
/*  754:     */   {
/*  755: 816 */     this.listaPedidoProveedor = listaPedidoProveedor;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public DataTable getDtPedidoProveedor()
/*  759:     */   {
/*  760: 821 */     return this.dtPedidoProveedor;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public void setDtPedidoProveedor(DataTable dtPedidoProveedor)
/*  764:     */   {
/*  765: 825 */     this.dtPedidoProveedor = dtPedidoProveedor;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public DataTable getDtDetallePedidoProveedor()
/*  769:     */   {
/*  770: 829 */     return this.dtDetallePedidoProveedor;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setDtDetallePedidoProveedor(DataTable dtDetallePedidoProveedor)
/*  774:     */   {
/*  775: 833 */     this.dtDetallePedidoProveedor = dtDetallePedidoProveedor;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public List<Documento> getListaDocumentoProveedor()
/*  779:     */   {
/*  780:     */     try
/*  781:     */     {
/*  782: 838 */       if (this.listaDocumentoProveedor == null) {
/*  783: 839 */         this.listaDocumentoProveedor = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PEDIDO_PROVEEDOR);
/*  784:     */       }
/*  785:     */     }
/*  786:     */     catch (ExcepcionAS2 e)
/*  787:     */     {
/*  788: 842 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  789:     */     }
/*  790: 844 */     return this.listaDocumentoProveedor;
/*  791:     */   }
/*  792:     */   
/*  793:     */   public void setListaDocumentoProveedor(List<Documento> listaDocumentoProveedor)
/*  794:     */   {
/*  795: 848 */     this.listaDocumentoProveedor = listaDocumentoProveedor;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public List<Empresa> getListaEmpresaProveedor()
/*  799:     */   {
/*  800: 852 */     if (this.listaEmpresaProveedor == null) {
/*  801: 853 */       this.listaEmpresaProveedor = this.servicioEmpresa.obtenerProveedores();
/*  802:     */     }
/*  803: 855 */     return this.listaEmpresaProveedor;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void setListaEmpresaProveedor(List<Empresa> listaEmpresaProveedor)
/*  807:     */   {
/*  808: 859 */     this.listaEmpresaProveedor = listaEmpresaProveedor;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/*  812:     */   {
/*  813: 863 */     if (this.listaDireccionEmpresa == null) {
/*  814: 864 */       this.listaDireccionEmpresa = new ArrayList();
/*  815:     */     }
/*  816: 866 */     return this.listaDireccionEmpresa;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/*  820:     */   {
/*  821: 870 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public DataTable getDtImpuestoDetallePedidoProveedor()
/*  825:     */   {
/*  826: 879 */     return this.dtImpuestoDetallePedidoProveedor;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public void setDtImpuestoDetallePedidoProveedor(DataTable dtImpuestoDetallePedidoProveedor)
/*  830:     */   {
/*  831: 889 */     this.dtImpuestoDetallePedidoProveedor = dtImpuestoDetallePedidoProveedor;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public List<ImpuestoProductoPedidoProveedor> getListaImpuestoProductoPedidoProveedor()
/*  835:     */   {
/*  836: 899 */     List<ImpuestoProductoPedidoProveedor> listaImpuestoProductoPedidoProveedores = new ArrayList();
/*  837: 901 */     for (DetallePedidoProveedor detallePedidoProveedor : getPedidoProveedor().getListaDetallePedidoProveedor()) {
/*  838: 903 */       for (ImpuestoProductoPedidoProveedor impuestoProductoPedidoProveedor : detallePedidoProveedor.getListaImpuestoProductoPedidoProveedor()) {
/*  839: 905 */         if (!impuestoProductoPedidoProveedor.isEliminado()) {
/*  840: 906 */           listaImpuestoProductoPedidoProveedores.add(impuestoProductoPedidoProveedor);
/*  841:     */         }
/*  842:     */       }
/*  843:     */     }
/*  844: 912 */     return listaImpuestoProductoPedidoProveedores;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  848:     */   {
/*  849: 917 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/*  850:     */   }
/*  851:     */   
/*  852:     */   public List<CondicionPago> getListaCondicionPago()
/*  853:     */   {
/*  854: 924 */     if (this.listaCondicionPago == null) {
/*  855: 925 */       this.listaCondicionPago = this.servicioCondicionPago.obtenerListaCombo("", false, null);
/*  856:     */     }
/*  857: 927 */     return this.listaCondicionPago;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public void setListaCondicionPago(List<CondicionPago> listaCondicionPago)
/*  861:     */   {
/*  862: 935 */     this.listaCondicionPago = listaCondicionPago;
/*  863:     */   }
/*  864:     */   
/*  865:     */   public void cerrarPedidoProveedor()
/*  866:     */   {
/*  867: 942 */     this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(this.pedidoProveedor.getId()), Estado.CERRADO);
/*  868:     */   }
/*  869:     */   
/*  870:     */   public void procesarPedidoProveedor()
/*  871:     */   {
/*  872: 946 */     this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(this.pedidoProveedor.getId()), Estado.APROBADO);
/*  873:     */   }
/*  874:     */   
/*  875:     */   public Integer getIdPedidoProveedor()
/*  876:     */   {
/*  877: 953 */     return this.idPedidoProveedor;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public void setIdPedidoProveedor(Integer idPedidoProveedor)
/*  881:     */   {
/*  882: 961 */     this.idPedidoProveedor = idPedidoProveedor;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public List<Sucursal> getListaSucursal()
/*  886:     */   {
/*  887: 968 */     if (this.listaSucursal == null) {
/*  888: 969 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/*  889:     */     }
/*  890: 971 */     return this.listaSucursal;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void setListaSucursal(List<Sucursal> listaSucursal)
/*  894:     */   {
/*  895: 979 */     this.listaSucursal = listaSucursal;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public ListaProductoBean getListaProductoBean()
/*  899:     */   {
/*  900: 986 */     return this.listaProductoBean;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/*  904:     */   {
/*  905: 994 */     this.listaProductoBean = listaProductoBean;
/*  906:     */   }
/*  907:     */   
/*  908:     */   public List<Bodega> getListaBodega()
/*  909:     */   {
/*  910:1001 */     if (this.listaBodega == null) {
/*  911:1002 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/*  912:     */     }
/*  913:1004 */     return this.listaBodega;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void setListaBodega(List<Bodega> listaBodega)
/*  917:     */   {
/*  918:1012 */     this.listaBodega = listaBodega;
/*  919:     */   }
/*  920:     */   
/*  921:     */   public ListaDimensionContableBean getListaDimensionContableBean()
/*  922:     */   {
/*  923:1016 */     return this.listaDimensionContableBean;
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/*  927:     */   {
/*  928:1020 */     this.listaDimensionContableBean = listaDimensionContableBean;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public DetallePedidoProveedor getDetallePedidoProveedorSeleccionado()
/*  932:     */   {
/*  933:1024 */     return this.detallePedidoProveedorSeleccionado;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void setDetallePedidoProveedorSeleccionado(DetallePedidoProveedor detallePedidoProveedorSeleccionado)
/*  937:     */   {
/*  938:1028 */     this.detallePedidoProveedorSeleccionado = detallePedidoProveedorSeleccionado;
/*  939:     */   }
/*  940:     */   
/*  941:     */   public Boolean getIndicadorParametroEditarPrecioPedido()
/*  942:     */   {
/*  943:1032 */     if (this.indicadorParametroEditarPrecioPedido == null) {
/*  944:1033 */       this.indicadorParametroEditarPrecioPedido = ParametrosSistema.getEditarPrecioFacturaPedidoProveedor(AppUtil.getOrganizacion().getId());
/*  945:     */     }
/*  946:1035 */     return this.indicadorParametroEditarPrecioPedido;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void solicitarCambioPrecio()
/*  950:     */   {
/*  951:1039 */     if ((getPedidoProveedor() != null) && (getPedidoProveedor().getId() != 0)) {
/*  952:     */       try
/*  953:     */       {
/*  954:1041 */         this.pedidoProveedor = this.servicioPedidoProveedor.cargarDetalle(Integer.valueOf(this.pedidoProveedor.getId()));
/*  955:1042 */         List<FacturaProveedor> listaFacturaProveedor = this.servicioPedidoProveedor.pedidoEnFacturaProveedor(this.pedidoProveedor.getId());
/*  956:1043 */         if ((listaFacturaProveedor.isEmpty()) && ((getPedidoProveedor().getEstado().equals(Estado.APROBADO)) || 
/*  957:1044 */           (getPedidoProveedor().getEstado().equals(Estado.APROBADO_PARCIAL)) || 
/*  958:1045 */           (!getPedidoProveedor().getEstado().equals(Estado.CERRADO))))
/*  959:     */         {
/*  960:1047 */           if (!this.pedidoProveedor.getIndicadorSolicitudCambioPrecio().booleanValue())
/*  961:     */           {
/*  962:1048 */             this.pedidoProveedor.setIndicadorSolicitudCambioPrecio(Boolean.valueOf(true));
/*  963:1049 */             this.pedidoProveedor.setTotalPedidoAnterior(this.pedidoProveedor.getTotalPedido());
/*  964:1050 */             this.pedidoProveedor.setSubtotalAnterior(this.pedidoProveedor.getTotal().subtract(this.pedidoProveedor.getDescuento()));
/*  965:1051 */             for (DetallePedidoProveedor dpp : this.pedidoProveedor.getListaDetallePedidoProveedor())
/*  966:     */             {
/*  967:1052 */               dpp.setPrecioAnterior(dpp.getPrecio());
/*  968:1053 */               dpp.setPrecioLineaAnterior(dpp.getPrecioLinea());
/*  969:     */             }
/*  970:1055 */             String descripcionAnterior = this.pedidoProveedor.getDescripcion();
/*  971:1056 */             this.pedidoProveedor
/*  972:1057 */               .setDescripcion("Cambio Precio(" + AppUtil.getUsuarioEnSesion().getNombreUsuario() + ") " + descripcionAnterior);
/*  973:     */           }
/*  974:1059 */           cargarDirecciones();
/*  975:1060 */           totalizar();
/*  976:1061 */           setEditado(true);
/*  977:1062 */           this.indicadorCambiarPrecio = true;
/*  978:     */         }
/*  979:     */         else
/*  980:     */         {
/*  981:1064 */           addInfoMessage(getLanguageController().getMensaje("msg_error_editar"));
/*  982:     */         }
/*  983:     */       }
/*  984:     */       catch (ExcepcionAS2Compras e)
/*  985:     */       {
/*  986:1067 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  987:     */       }
/*  988:     */     } else {
/*  989:1073 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  990:     */     }
/*  991:     */   }
/*  992:     */   
/*  993:     */   public boolean isIndicadorCambiarPrecio()
/*  994:     */   {
/*  995:1078 */     return this.indicadorCambiarPrecio;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public void setIndicadorCambiarPrecio(boolean indicadorCambiarPrecio)
/*  999:     */   {
/* 1000:1082 */     this.indicadorCambiarPrecio = indicadorCambiarPrecio;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public String getMails()
/* 1004:     */   {
/* 1005:1086 */     if ((this.pedidoProveedor != null) && (this.pedidoProveedor.getDocumento() != null) && (this.pedidoProveedor.getDocumento().getDocumentoBase() != null))
/* 1006:     */     {
/* 1007:1088 */       String[] correos = this.servicioEmpresa.cargarMails(this.pedidoProveedor.getEmpresa(), getPedidoProveedor().getDocumento().getDocumentoBase()).split("~");
/* 1008:1089 */       if (correos.length > 0) {
/* 1009:1090 */         this.mails = correos[0];
/* 1010:     */       }
/* 1011:     */     }
/* 1012:1092 */     return this.mails;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public void setMails(String mails)
/* 1016:     */   {
/* 1017:1096 */     this.mails = mails;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public String getRutaPlantilla()
/* 1021:     */   {
/* 1022:1101 */     return "/resources/plantillas/compras/AS2 Pedido Proveedor.xls";
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public String getNombrePlantilla()
/* 1026:     */   {
/* 1027:1106 */     return "AS2 Pedido Proveedor.xls";
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 1031:     */   {
/* 1032:1110 */     if (this.listaPersonaResponsable == null)
/* 1033:     */     {
/* 1034:1111 */       HashMap<String, String> filters = new HashMap();
/* 1035:1112 */       filters.put("indicadorCompra", "true");
/* 1036:1113 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 1037:     */     }
/* 1038:1115 */     return this.listaPersonaResponsable;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public void setListaPersonaResponsable(List<PersonaResponsable> listaPersonaResponsable)
/* 1042:     */   {
/* 1043:1119 */     this.listaPersonaResponsable = listaPersonaResponsable;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   private void cargarPersonaResponsable()
/* 1047:     */   {
/* 1048:     */     List<EntidadUsuario> listaUsuario;
/* 1049:1124 */     if (isIndicadorOrdenCompraConPersonaResponsable())
/* 1050:     */     {
/* 1051:1126 */       HashMap<String, String> filters = new HashMap();
/* 1052:1127 */       filters.put("idUsuario", "" + AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 1053:     */       
/* 1054:1129 */       listaUsuario = this.servicioUsuario.obtenerListaCombo("", true, filters);
/* 1055:1132 */       if ((listaUsuario != null) && (!listaUsuario.isEmpty()) && (((EntidadUsuario)listaUsuario.get(0)).getEmpleado() != null)) {
/* 1056:1133 */         for (PersonaResponsable personaResponsable : getListaPersonaResponsable()) {
/* 1057:1134 */           if ((personaResponsable.getEmpleado() != null) && 
/* 1058:1135 */             (personaResponsable.getEmpleado().getIdEmpleado() == ((EntidadUsuario)listaUsuario.get(0)).getEmpleado().getIdEmpleado()))
/* 1059:     */           {
/* 1060:1136 */             this.pedidoProveedor.setPersonaResponsable(personaResponsable);
/* 1061:1137 */             break;
/* 1062:     */           }
/* 1063:     */         }
/* 1064:     */       }
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.PedidoProveedorBean
 * JD-Core Version:    0.7.0.1
 */