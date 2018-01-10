/*    1:     */ package com.asinfo.as2.produccion.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    6:     */ import com.asinfo.as2.entities.Bodega;
/*    7:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*    8:     */ import com.asinfo.as2.entities.Documento;
/*    9:     */ import com.asinfo.as2.entities.Organizacion;
/*   10:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   11:     */ import com.asinfo.as2.entities.Producto;
/*   12:     */ import com.asinfo.as2.entities.Sucursal;
/*   13:     */ import com.asinfo.as2.entities.Unidad;
/*   14:     */ import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
/*   15:     */ import com.asinfo.as2.entities.produccion.PlanMaestroProduccion;
/*   16:     */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*   17:     */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   18:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   19:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   20:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   21:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   22:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   23:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   24:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   25:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   26:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*   27:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanMaestroProduccion;
/*   28:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion;
/*   29:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*   30:     */ import com.asinfo.as2.util.AppUtil;
/*   31:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   32:     */ import com.asinfo.as2.utils.JsfUtil;
/*   33:     */ import com.asinfo.as2.utils.NodoArbol;
/*   34:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   35:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   36:     */ import java.math.BigDecimal;
/*   37:     */ import java.util.ArrayList;
/*   38:     */ import java.util.Collection;
/*   39:     */ import java.util.Date;
/*   40:     */ import java.util.HashMap;
/*   41:     */ import java.util.List;
/*   42:     */ import java.util.Map;
/*   43:     */ import java.util.TreeMap;
/*   44:     */ import javax.annotation.PostConstruct;
/*   45:     */ import javax.ejb.EJB;
/*   46:     */ import javax.faces.bean.ManagedBean;
/*   47:     */ import javax.faces.bean.ManagedProperty;
/*   48:     */ import javax.faces.bean.ViewScoped;
/*   49:     */ import javax.faces.component.html.HtmlInputText;
/*   50:     */ import javax.faces.context.FacesContext;
/*   51:     */ import javax.faces.context.PartialViewContext;
/*   52:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   53:     */ import org.apache.log4j.Logger;
/*   54:     */ import org.primefaces.component.datatable.DataTable;
/*   55:     */ import org.primefaces.context.RequestContext;
/*   56:     */ import org.primefaces.model.DefaultTreeNode;
/*   57:     */ import org.primefaces.model.LazyDataModel;
/*   58:     */ import org.primefaces.model.SortOrder;
/*   59:     */ import org.primefaces.model.TreeNode;
/*   60:     */ 
/*   61:     */ @ManagedBean
/*   62:     */ @ViewScoped
/*   63:     */ public class PlanProduccionBean
/*   64:     */   extends PageControllerAS2
/*   65:     */ {
/*   66:     */   private static final long serialVersionUID = 1424377436906270962L;
/*   67:     */   @EJB
/*   68:     */   private ServicioPlanProduccion servicioPlanProduccion;
/*   69:     */   @EJB
/*   70:     */   private ServicioProducto servicioProducto;
/*   71:     */   @EJB
/*   72:     */   private ServicioUnidad servicioUnidad;
/*   73:     */   @EJB
/*   74:     */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*   75:     */   @EJB
/*   76:     */   private ServicioDocumento servicioDocumento;
/*   77:     */   @EJB
/*   78:     */   private ServicioBodega servicioBodega;
/*   79:     */   @EJB
/*   80:     */   private ServicioPedidoCliente servicioPedidoCliente;
/*   81:     */   @EJB
/*   82:     */   private ServicioPlanMaestroProduccion servicioPlanMaestroProduccion;
/*   83:     */   private DataTable dtDetallePlanProduccion;
/*   84:     */   private DataTable dtPlanProduccion;
/*   85:     */   private DataTable dtMateriales;
/*   86:     */   private DataTable dtComponentes;
/*   87:     */   private DataTable dtDetalleConsumibles;
/*   88:     */   private PlanProduccion planProduccion;
/*   89:     */   private LazyDataModel<PlanProduccion> listaPlanProduccion;
/*   90:     */   private DetallePlanProduccion detallePlanProduccionSeleccionado;
/*   91:     */   @ManagedProperty("#{listaProductoBean}")
/*   92:     */   private ListaProductoBean listaProductoBean;
/*   93:     */   private List<PedidoCliente> listaPedidoClientePendiente;
/*   94:     */   private List<PedidoCliente> listaPedidoClienteSeleccionados;
/*   95:     */   private List<PlanMaestroProduccion> listaPlanMaestroProduccion;
/*   96:     */   private PlanMaestroProduccion planMaestroProduccion;
/*   97:     */   private List<DetallePlanProduccion> listaMateriales;
/*   98:     */   private List<DetallePlanProduccion> listaDetallePlanProduccionFiltrados;
/*   99:     */   private List<DetalleOrdenSalidaMaterial> listaDetalleConsumibles;
/*  100:     */   private List<DetalleOrdenSalidaMaterial> listaDetalleConsumiblesFiltrados;
/*  101:     */   private TreeNode root;
/*  102:     */   private boolean abiertaVentanaFecha;
/*  103: 134 */   private BigDecimal factorCambio = BigDecimal.ONE;
/*  104: 136 */   private boolean mostrarFiltros = true;
/*  105: 137 */   private boolean mostrarConfiguracion = true;
/*  106:     */   private boolean mostrarVentasPrevias;
/*  107: 139 */   private boolean mostrarProyeccionVentas = true;
/*  108: 140 */   private boolean mostrarPlanificacion = true;
/*  109:     */   private boolean mostrarSaldos;
/*  110:     */   private boolean mostrarExtras;
/*  111:     */   private int totalBatchLunes;
/*  112:     */   private int totalBatchMartes;
/*  113:     */   private int totalBatchMiercoles;
/*  114:     */   private int totalBatchJueves;
/*  115:     */   private int totalBatchViernes;
/*  116:     */   private int totalBatchSabado;
/*  117:     */   private int totalBatchDomingo;
/*  118:     */   private String descripcion;
/*  119:     */   Date fechaOrdenProduccion;
/*  120:     */   private List<Bodega> listaBodegaSeleccionada;
/*  121:     */   
/*  122:     */   @PostConstruct
/*  123:     */   public void init()
/*  124:     */   {
/*  125: 164 */     getListaProductoBean().setIndicadorProduccion(true);
/*  126:     */     
/*  127: 166 */     this.listaPlanProduccion = new LazyDataModel()
/*  128:     */     {
/*  129:     */       private static final long serialVersionUID = 1L;
/*  130:     */       
/*  131:     */       public List<PlanProduccion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  132:     */       {
/*  133: 173 */         List<PlanProduccion> lista = new ArrayList();
/*  134: 174 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  135: 175 */         filters = PlanProduccionBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), false);
/*  136: 176 */         lista = PlanProduccionBean.this.servicioPlanProduccion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  137:     */         
/*  138: 178 */         PlanProduccionBean.this.listaPlanProduccion.setRowCount(PlanProduccionBean.this.servicioPlanProduccion.contarPorCriterio(filters));
/*  139:     */         
/*  140: 180 */         return lista;
/*  141:     */       }
/*  142:     */     };
/*  143:     */   }
/*  144:     */   
/*  145:     */   public String editar()
/*  146:     */   {
/*  147: 187 */     if (!this.planProduccion.getEstado().equals(Estado.ELABORADO))
/*  148:     */     {
/*  149: 188 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " | " + this.planProduccion.getEstado());
/*  150: 189 */       return "";
/*  151:     */     }
/*  152: 191 */     if ((this.planProduccion != null) && (this.planProduccion.getId() != 0))
/*  153:     */     {
/*  154: 192 */       this.planProduccion = this.servicioPlanProduccion.cargarDetalle(this.planProduccion.getId());
/*  155:     */       String[] cargas;
/*  156: 193 */       if ((this.planProduccion.getBodegasTrabajo() != null) && (!this.planProduccion.getBodegasTrabajo().isEmpty()))
/*  157:     */       {
/*  158: 194 */         cargas = this.planProduccion.getBodegasTrabajo().split(", ");
/*  159: 195 */         this.listaBodegaSeleccionada = new ArrayList();
/*  160: 196 */         HashMap<String, String> filters = new HashMap();
/*  161: 197 */         for (String string : cargas)
/*  162:     */         {
/*  163: 198 */           filters.put("nombre", string.trim());
/*  164: 199 */           List<Bodega> listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, filters);
/*  165: 200 */           for (Bodega bodega : listaBodega) {
/*  166: 201 */             this.listaBodegaSeleccionada.add(bodega);
/*  167:     */           }
/*  168:     */         }
/*  169:     */       }
/*  170:     */       else
/*  171:     */       {
/*  172: 206 */         this.listaBodegaSeleccionada = new ArrayList();
/*  173:     */       }
/*  174: 208 */       for (DetallePlanProduccion detallePlanProduccion : this.planProduccion.getListaDetallePlanProduccion()) {
/*  175: 209 */         actualizarArbol(detallePlanProduccion);
/*  176:     */       }
/*  177: 212 */       setEditado(true);
/*  178: 213 */       this.listaDetallePlanProduccionFiltrados = null;
/*  179: 214 */       this.dtDetallePlanProduccion.reset();
/*  180:     */     }
/*  181:     */     else
/*  182:     */     {
/*  183: 219 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  184:     */     }
/*  185: 221 */     return "";
/*  186:     */   }
/*  187:     */   
/*  188:     */   public String guardar()
/*  189:     */   {
/*  190:     */     try
/*  191:     */     {
/*  192: 227 */       this.servicioPlanProduccion.guardar(this.planProduccion);
/*  193: 228 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  194: 229 */       limpiar();
/*  195: 230 */       setEditado(false);
/*  196:     */     }
/*  197:     */     catch (AS2Exception e)
/*  198:     */     {
/*  199: 232 */       JsfUtil.addErrorMessage(e, "");
/*  200:     */     }
/*  201: 234 */     return "";
/*  202:     */   }
/*  203:     */   
/*  204:     */   public String eliminar()
/*  205:     */   {
/*  206:     */     try
/*  207:     */     {
/*  208: 240 */       this.servicioPlanProduccion.eliminar(this.planProduccion);
/*  209: 241 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  210:     */     }
/*  211:     */     catch (Exception e)
/*  212:     */     {
/*  213: 243 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  214: 244 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  215:     */     }
/*  216: 246 */     return "";
/*  217:     */   }
/*  218:     */   
/*  219:     */   public String cargarDatos()
/*  220:     */   {
/*  221: 251 */     return "";
/*  222:     */   }
/*  223:     */   
/*  224:     */   public String limpiar()
/*  225:     */   {
/*  226: 256 */     this.planProduccion = new PlanProduccion();
/*  227: 257 */     this.planProduccion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  228: 258 */     this.planProduccion.setIdSucursal(AppUtil.getSucursal().getId());
/*  229: 259 */     this.listaPedidoClientePendiente = null;
/*  230: 260 */     this.listaPedidoClienteSeleccionados = null;
/*  231: 261 */     Date fechaDesde = new Date();
/*  232: 262 */     Date fechaHasta = new Date();
/*  233: 263 */     fechaDesde = FuncionesUtiles.getFechaProximoDiaSemana(fechaDesde, 2);
/*  234: 264 */     fechaHasta = FuncionesUtiles.getFechaProximoDiaSemana(fechaDesde, 1);
/*  235: 265 */     this.planProduccion.setFechaInicio(fechaDesde);
/*  236: 266 */     this.planProduccion.setFechaFin(fechaHasta);
/*  237: 268 */     if (getListaDocumento().size() > 0) {
/*  238: 269 */       this.planProduccion.setDocumento((Documento)getListaDocumento().get(0));
/*  239:     */     }
/*  240: 271 */     if (getListaPlanMaestroProduccion().size() > 0) {
/*  241: 272 */       this.planProduccion.setPlanMaestroProduccion((PlanMaestroProduccion)getListaPlanMaestroProduccion().get(0));
/*  242:     */     }
/*  243: 274 */     this.listaBodegaSeleccionada = new ArrayList();
/*  244: 275 */     return "";
/*  245:     */   }
/*  246:     */   
/*  247:     */   public DataTable getDtDetallePlanProduccion()
/*  248:     */   {
/*  249: 279 */     return this.dtDetallePlanProduccion;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void setDtDetallePlanProduccion(DataTable dtDetallePlanProduccion)
/*  253:     */   {
/*  254: 283 */     this.dtDetallePlanProduccion = dtDetallePlanProduccion;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public DataTable getDtPlanProduccion()
/*  258:     */   {
/*  259: 287 */     return this.dtPlanProduccion;
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void setDtPlanProduccion(DataTable dtPlanProduccion)
/*  263:     */   {
/*  264: 291 */     this.dtPlanProduccion = dtPlanProduccion;
/*  265:     */   }
/*  266:     */   
/*  267:     */   public PlanProduccion getPlanProduccion()
/*  268:     */   {
/*  269: 295 */     return this.planProduccion;
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void setPlanProduccion(PlanProduccion planProduccion)
/*  273:     */   {
/*  274: 299 */     this.planProduccion = planProduccion;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public LazyDataModel<PlanProduccion> getListaPlanProduccion()
/*  278:     */   {
/*  279: 303 */     return this.listaPlanProduccion;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public void setListaPlanProduccion(LazyDataModel<PlanProduccion> listaPlanProduccion)
/*  283:     */   {
/*  284: 307 */     this.listaPlanProduccion = listaPlanProduccion;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public ListaProductoBean getListaProductoBean()
/*  288:     */   {
/*  289: 311 */     return this.listaProductoBean;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/*  293:     */   {
/*  294: 315 */     this.listaProductoBean = listaProductoBean;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public DetallePlanProduccion getDetallePlanProduccionSeleccionado()
/*  298:     */   {
/*  299: 319 */     return this.detallePlanProduccionSeleccionado;
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void setDetallePlanProduccionSeleccionado(DetallePlanProduccion detallePlanProduccionSeleccionado)
/*  303:     */   {
/*  304: 323 */     this.detallePlanProduccionSeleccionado = detallePlanProduccionSeleccionado;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void agregarDetalle()
/*  308:     */   {
/*  309: 328 */     DetallePlanProduccion detallePlanProduccion = new DetallePlanProduccion();
/*  310: 329 */     detallePlanProduccion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  311: 330 */     detallePlanProduccion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  312: 331 */     detallePlanProduccion.setProducto(new Producto());
/*  313: 332 */     detallePlanProduccion.setPlanProduccion(this.planProduccion);
/*  314:     */     
/*  315: 334 */     this.planProduccion.getListaDetallePlanProduccion().add(detallePlanProduccion);
/*  316: 335 */     getListaProductoBean().setProducto(null);
/*  317: 336 */     getListaProductoBean().setSaldoProductoLote(null);
/*  318:     */     
/*  319: 338 */     this.listaDetallePlanProduccionFiltrados = null;
/*  320: 339 */     this.dtDetallePlanProduccion.reset();
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void actualizarProducto(AjaxBehaviorEvent event)
/*  324:     */   {
/*  325: 343 */     DetallePlanProduccion detallePlanProduccion = null;
/*  326:     */     try
/*  327:     */     {
/*  328: 345 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  329: 346 */       detallePlanProduccion = (DetallePlanProduccion)this.dtDetallePlanProduccion.getRowData();
/*  330: 347 */       cargarProductoDesdeCodigoEnDetalle(codigo, detallePlanProduccion);
/*  331:     */     }
/*  332:     */     catch (ExcepcionAS2 e)
/*  333:     */     {
/*  334: 349 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  335: 350 */       detallePlanProduccion.getProducto().setCodigo("");
/*  336: 351 */       detallePlanProduccion.getProducto().setNombre("");
/*  337:     */     }
/*  338:     */   }
/*  339:     */   
/*  340:     */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetallePlanProduccion detallePlanProduccion)
/*  341:     */     throws ExcepcionAS2
/*  342:     */   {
/*  343: 356 */     Producto producto = null;
/*  344: 357 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  345: 358 */     producto = this.servicioProducto.cargaDetalle(producto.getId());
/*  346: 359 */     producto.setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(producto));
/*  347: 360 */     detallePlanProduccion.setProducto(producto);
/*  348: 361 */     detallePlanProduccion.setRutaFabricacion(producto.getRutaFabricacion());
/*  349: 362 */     detallePlanProduccion.setUnidadStock(producto.getUnidadAlmacenamiento());
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void cargarProducto(Producto producto)
/*  353:     */   {
/*  354: 367 */     DetallePlanProduccion detallePlanProduccion = new DetallePlanProduccion();
/*  355: 368 */     detallePlanProduccion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  356: 369 */     detallePlanProduccion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  357: 370 */     detallePlanProduccion.setPlanProduccion(this.planProduccion);
/*  358:     */     
/*  359: 372 */     producto = this.servicioProducto.cargaDetalle(producto.getId());
/*  360: 373 */     producto.setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(producto));
/*  361: 374 */     detallePlanProduccion.setProducto(producto);
/*  362: 375 */     detallePlanProduccion.setRutaFabricacion(producto.getRutaFabricacion());
/*  363: 376 */     detallePlanProduccion.setUnidadStock(producto.getUnidadAlmacenamiento());
/*  364:     */     
/*  365: 378 */     this.planProduccion.getListaDetallePlanProduccion().add(detallePlanProduccion);
/*  366: 379 */     getListaProductoBean().setProducto(null);
/*  367: 380 */     getListaProductoBean().setSaldoProductoLote(null);
/*  368:     */     
/*  369: 382 */     this.listaDetallePlanProduccionFiltrados = null;
/*  370: 383 */     this.dtDetallePlanProduccion.reset();
/*  371:     */   }
/*  372:     */   
/*  373:     */   public String eliminarDetalle()
/*  374:     */   {
/*  375: 387 */     this.detallePlanProduccionSeleccionado = ((DetallePlanProduccion)this.dtDetallePlanProduccion.getRowData());
/*  376: 388 */     this.detallePlanProduccionSeleccionado.setEliminado(true);
/*  377: 389 */     this.listaDetallePlanProduccionFiltrados = null;
/*  378: 390 */     this.dtDetallePlanProduccion.reset();
/*  379: 391 */     return "";
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void proyectarVentas()
/*  383:     */   {
/*  384: 395 */     this.detallePlanProduccionSeleccionado = ((DetallePlanProduccion)this.dtDetallePlanProduccion.getRowData());
/*  385: 396 */     proyectarVentas(this.detallePlanProduccionSeleccionado);
/*  386:     */   }
/*  387:     */   
/*  388:     */   public void proyectarVentas(DetallePlanProduccion detallePlanProduccion)
/*  389:     */   {
/*  390:     */     try
/*  391:     */     {
/*  392: 401 */       this.servicioPlanProduccion.proyectarVentas(detallePlanProduccion);
/*  393: 402 */       this.servicioPlanProduccion.actualizarPlanMaestroProduccion(this.planProduccion);
/*  394: 403 */       calcularSaldos(detallePlanProduccion);
/*  395:     */     }
/*  396:     */     catch (AS2Exception e)
/*  397:     */     {
/*  398: 405 */       JsfUtil.addErrorMessage(e, "");
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public List<Unidad> getListaUnidad()
/*  403:     */   {
/*  404: 410 */     Map<String, String> filtros = new HashMap();
/*  405: 411 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  406: 412 */     filtros.put("activo", "true");
/*  407: 413 */     return this.servicioUnidad.obtenerListaCombo("nombre", true, filtros);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public List<RutaFabricacion> getListaRutaFabricacion()
/*  411:     */   {
/*  412: 417 */     Map<String, String> filtros = new HashMap();
/*  413: 418 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  414: 419 */     filtros.put("activo", "true");
/*  415: 420 */     return this.servicioRutaFabricacion.obtenerListaCombo("nombre", true, filtros);
/*  416:     */   }
/*  417:     */   
/*  418:     */   public List<Bodega> getListaBodega()
/*  419:     */   {
/*  420: 424 */     Map<String, String> filtros = new HashMap();
/*  421: 425 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/*  422: 426 */     if (ParametrosSistema.getFiltrarSucursalInicioSesion(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  423: 427 */       filtros.put("sucursal.idSucursal", "" + AppUtil.getSucursal().getId());
/*  424:     */     }
/*  425: 429 */     filtros.put("activo", "true");
/*  426: 430 */     return this.servicioBodega.obtenerListaCombo("nombre", true, filtros);
/*  427:     */   }
/*  428:     */   
/*  429:     */   public List<Documento> getListaDocumento()
/*  430:     */   {
/*  431: 434 */     Map<String, String> filtros = new HashMap();
/*  432: 435 */     agregarFiltroOrganizacion(filtros);
/*  433: 436 */     filtros.put("activo", "true");
/*  434: 437 */     filtros.put("documentoBase", DocumentoBase.ORDEN_FABRICACION.toString());
/*  435: 438 */     return this.servicioDocumento.obtenerListaCombo("predeterminado", false, filtros);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public List<DetallePlanProduccion> getListaDetallePlanProduccion()
/*  439:     */   {
/*  440: 442 */     List<DetallePlanProduccion> lista = new ArrayList();
/*  441: 443 */     for (DetallePlanProduccion detallePlanProduccion : this.planProduccion.getListaDetallePlanProduccion()) {
/*  442: 444 */       if (!detallePlanProduccion.isEliminado()) {
/*  443: 445 */         lista.add(detallePlanProduccion);
/*  444:     */       }
/*  445:     */     }
/*  446: 449 */     return lista;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public List<PedidoCliente> getListaPedidoClientePendiente()
/*  450:     */   {
/*  451: 453 */     if (this.listaPedidoClientePendiente == null) {
/*  452: 454 */       this.listaPedidoClientePendiente = this.servicioPedidoCliente.listaPedidosPorDespachar(AppUtil.getOrganizacion());
/*  453:     */     }
/*  454: 456 */     return this.listaPedidoClientePendiente;
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void setListaPedidoClientePendiente(List<PedidoCliente> listaPedidoClientePendiente)
/*  458:     */   {
/*  459: 460 */     this.listaPedidoClientePendiente = listaPedidoClientePendiente;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public List<PedidoCliente> getListaPedidoClienteSeleccionados()
/*  463:     */   {
/*  464: 464 */     if (this.listaPedidoClienteSeleccionados == null) {
/*  465: 465 */       this.listaPedidoClienteSeleccionados = new ArrayList();
/*  466:     */     }
/*  467: 467 */     return this.listaPedidoClienteSeleccionados;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public void setListaPedidoClienteSeleccionados(List<PedidoCliente> listaPedidoClienteSeleccionados)
/*  471:     */   {
/*  472: 471 */     this.listaPedidoClienteSeleccionados = listaPedidoClienteSeleccionados;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public List<PlanMaestroProduccion> cargarListaPlanMaestro()
/*  476:     */   {
/*  477: 475 */     this.listaPlanMaestroProduccion = new ArrayList();
/*  478:     */     try
/*  479:     */     {
/*  480: 477 */       this.listaPlanMaestroProduccion = this.servicioPlanMaestroProduccion.buscarPlanesMaestrosProduccionPorRangoFecha(AppUtil.getOrganizacion().getId(), this.planProduccion
/*  481: 478 */         .getFechaInicio(), this.planProduccion.getFechaFin());
/*  482:     */     }
/*  483:     */     catch (ExcepcionAS2 localExcepcionAS2) {}
/*  484: 482 */     return this.listaPlanMaestroProduccion;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public PlanMaestroProduccion getPlanMaestroProduccion()
/*  488:     */   {
/*  489: 486 */     return this.planMaestroProduccion;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public void setPlanMaestroProduccion(PlanMaestroProduccion planMaestroProduccion)
/*  493:     */   {
/*  494: 490 */     this.planMaestroProduccion = planMaestroProduccion;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public void asignarPlanMaestro()
/*  498:     */   {
/*  499:     */     try
/*  500:     */     {
/*  501: 495 */       this.servicioPlanProduccion.actualizarPlanMaestroProduccion(this.planProduccion);
/*  502:     */     }
/*  503:     */     catch (AS2Exception e)
/*  504:     */     {
/*  505: 497 */       JsfUtil.addErrorMessage(e, "");
/*  506:     */     }
/*  507:     */   }
/*  508:     */   
/*  509:     */   public List<PlanMaestroProduccion> getListaPlanMaestroProduccion()
/*  510:     */   {
/*  511: 502 */     cargarListaPlanMaestro();
/*  512: 503 */     return this.listaPlanMaestroProduccion;
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void setListaPlanMaestroProduccion(List<PlanMaestroProduccion> listaPlanMaestroProduccion)
/*  516:     */   {
/*  517: 507 */     this.listaPlanMaestroProduccion = listaPlanMaestroProduccion;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public void procesarPlanProduccion(PlanProduccion planProduccionSeleccionado)
/*  521:     */   {
/*  522: 511 */     this.planProduccion = this.servicioPlanProduccion.cargarDetalle(planProduccionSeleccionado.getId());
/*  523: 512 */     this.planProduccion.setEstado(Estado.PROCESADO);
/*  524:     */     try
/*  525:     */     {
/*  526: 515 */       this.servicioPlanProduccion.guardar(this.planProduccion);
/*  527: 516 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  528:     */     }
/*  529:     */     catch (AS2Exception e)
/*  530:     */     {
/*  531: 518 */       JsfUtil.addErrorMessage(e, "");
/*  532:     */     }
/*  533:     */   }
/*  534:     */   
/*  535:     */   public TreeNode getRoot()
/*  536:     */   {
/*  537: 523 */     return this.root;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void setRoot(TreeNode root)
/*  541:     */   {
/*  542: 527 */     this.root = root;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void crearArbol(DetallePlanProduccion detallePlanProduccion)
/*  546:     */   {
/*  547: 531 */     NodoArbol<Producto> arbol = detallePlanProduccion.getProducto().getArbolComponentes();
/*  548: 532 */     Producto producto = (Producto)arbol.getValor();
/*  549: 533 */     producto.setCantidadProducir(detallePlanProduccion.getTotalProduccionSemana());
/*  550: 534 */     ((Producto)arbol.getValor()).setCantidadProducir(detallePlanProduccion.getTotalProduccionSemana());
/*  551: 535 */     this.servicioProducto.actualizarCantidadesProducir(arbol, null);
/*  552: 536 */     this.root = new DefaultTreeNode(arbol.getValor(), null);
/*  553: 537 */     this.root.setExpanded(true);
/*  554: 538 */     for (NodoArbol<Producto> nodo : arbol.getHijos()) {
/*  555: 539 */       crearArbolRecusivo(nodo, this.root);
/*  556:     */     }
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void crearArbolRecusivo(NodoArbol<Producto> nodo, TreeNode padre)
/*  560:     */   {
/*  561: 544 */     TreeNode root1 = new DefaultTreeNode(nodo.getValor(), this.root);
/*  562: 545 */     root1.setExpanded(true);
/*  563: 546 */     padre.getChildren().add(root1);
/*  564: 547 */     for (NodoArbol<Producto> nodo1 : nodo.getHijos()) {
/*  565: 548 */       crearArbolRecusivo(nodo1, root1);
/*  566:     */     }
/*  567:     */   }
/*  568:     */   
/*  569:     */   public List<DetallePlanProduccion> getListaMateriales()
/*  570:     */   {
/*  571: 553 */     actualizarListaMateriales();
/*  572: 554 */     return this.listaMateriales;
/*  573:     */   }
/*  574:     */   
/*  575:     */   public void setListaMateriales(List<DetallePlanProduccion> listaMateriales)
/*  576:     */   {
/*  577: 558 */     this.listaMateriales = listaMateriales;
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void actualizarListaMateriales()
/*  581:     */   {
/*  582: 562 */     Map<String, DetallePlanProduccion> mapaMateriales = new TreeMap();
/*  583: 564 */     for (DetallePlanProduccion detallePlanProduccion : this.planProduccion.getListaDetallePlanProduccion()) {
/*  584: 565 */       if (!detallePlanProduccion.isEliminado()) {
/*  585: 566 */         for (NodoArbol<Producto> nodo : detallePlanProduccion.getProducto().getArbolComponentes().getHojas())
/*  586:     */         {
/*  587: 567 */           String key = "" + ((Producto)nodo.getValor()).getId();
/*  588: 568 */           DetallePlanProduccion detalleNew = null;
/*  589: 569 */           if (mapaMateriales.containsKey(key))
/*  590:     */           {
/*  591: 570 */             detalleNew = (DetallePlanProduccion)mapaMateriales.get(key);
/*  592:     */           }
/*  593:     */           else
/*  594:     */           {
/*  595: 573 */             detalleNew = new DetallePlanProduccion();
/*  596: 574 */             detalleNew.setProducto((Producto)nodo.getValor());
/*  597:     */           }
/*  598: 579 */           mapaMateriales.put(key, detalleNew);
/*  599:     */         }
/*  600:     */       }
/*  601:     */     }
/*  602: 583 */     this.listaMateriales = new ArrayList();
/*  603: 584 */     this.listaMateriales.addAll(mapaMateriales.values());
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void actualizarListaComponentes() {}
/*  607:     */   
/*  608:     */   public void actualizarArbol(DetallePlanProduccion detallePlanProduccion)
/*  609:     */   {
/*  610: 614 */     detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion.getTotalProduccionSemana());
/*  611: 615 */     detallePlanProduccion.getProducto().setArbolComponentes(this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto()));
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void actualizarCantidadesProducir(Producto producto)
/*  615:     */   {
/*  616: 619 */     this.servicioProducto.actualizarCantidadesProducir(producto.getArbolComponentes(), null);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public DataTable getDtMateriales()
/*  620:     */   {
/*  621: 623 */     return this.dtMateriales;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void setDtMateriales(DataTable dtMateriales)
/*  625:     */   {
/*  626: 627 */     this.dtMateriales = dtMateriales;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public DataTable getDtComponentes()
/*  630:     */   {
/*  631: 631 */     return this.dtComponentes;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void setDtComponentes(DataTable dtComponentes)
/*  635:     */   {
/*  636: 635 */     this.dtComponentes = dtComponentes;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public List<DetallePlanProduccion> getListaComponentes()
/*  640:     */   {
/*  641: 639 */     List<DetallePlanProduccion> lista = new ArrayList();
/*  642: 640 */     for (DetallePlanProduccion detallePlanProduccion : this.planProduccion.getListaDetallePlanProduccion()) {
/*  643: 641 */       if (!detallePlanProduccion.isEliminado()) {
/*  644: 642 */         lista.add(detallePlanProduccion);
/*  645:     */       }
/*  646:     */     }
/*  647: 645 */     return lista;
/*  648:     */   }
/*  649:     */   
/*  650:     */   public List<RutaFabricacion> autocompletarRutaFabricacion(String consulta)
/*  651:     */   {
/*  652: 649 */     this.detallePlanProduccionSeleccionado = ((DetallePlanProduccion)getDtDetallePlanProduccion().getRowData());
/*  653: 650 */     return this.servicioRutaFabricacion.autocompletarRutaFabricacion(this.detallePlanProduccionSeleccionado.getProducto(), consulta);
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void sugerir()
/*  657:     */   {
/*  658:     */     try
/*  659:     */     {
/*  660: 655 */       this.planProduccion.setListaDetallePlanProduccion(new ArrayList());
/*  661: 656 */       this.servicioPlanProduccion.cargarSugerenciasVentas(this.planProduccion, AppUtil.getSucursal().getIdSucursal(), this.listaBodegaSeleccionada);
/*  662: 657 */       this.listaDetallePlanProduccionFiltrados = null;
/*  663: 658 */       this.dtDetallePlanProduccion.reset();
/*  664:     */     }
/*  665:     */     catch (AS2Exception e)
/*  666:     */     {
/*  667: 660 */       JsfUtil.addErrorMessage(e, "");
/*  668:     */     }
/*  669:     */   }
/*  670:     */   
/*  671:     */   public void agregarBodegasTrabajo()
/*  672:     */   {
/*  673: 665 */     String bodegasTrabajo = "";
/*  674: 667 */     if (this.listaBodegaSeleccionada == null)
/*  675:     */     {
/*  676: 668 */       this.listaBodegaSeleccionada = new ArrayList();
/*  677: 669 */       bodegasTrabajo = "TODAS";
/*  678:     */     }
/*  679: 671 */     for (Bodega bodegaTrabajo : this.listaBodegaSeleccionada)
/*  680:     */     {
/*  681: 672 */       String nueva = bodegaTrabajo.getNombre() + ", ";
/*  682: 673 */       bodegasTrabajo = bodegasTrabajo + nueva;
/*  683:     */     }
/*  684: 675 */     this.planProduccion.setBodegasTrabajo(bodegasTrabajo);
/*  685:     */   }
/*  686:     */   
/*  687:     */   public void calcularSaldos()
/*  688:     */   {
/*  689: 679 */     this.detallePlanProduccionSeleccionado = ((DetallePlanProduccion)this.dtDetallePlanProduccion.getRowData());
/*  690: 680 */     calcularSaldos(this.detallePlanProduccionSeleccionado);
/*  691:     */   }
/*  692:     */   
/*  693:     */   public void calcularSaldos(DetallePlanProduccion detallePlanProduccion)
/*  694:     */   {
/*  695:     */     try
/*  696:     */     {
/*  697: 685 */       this.servicioPlanProduccion.calcularSaldosSemana(detallePlanProduccion);
/*  698:     */     }
/*  699:     */     catch (AS2Exception e)
/*  700:     */     {
/*  701: 687 */       JsfUtil.addErrorMessage(e, "");
/*  702:     */     }
/*  703:     */   }
/*  704:     */   
/*  705:     */   public boolean isMostrarConfiguracion()
/*  706:     */   {
/*  707: 692 */     return this.mostrarConfiguracion;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void setMostrarConfiguracion(boolean mostrarConfiguracion)
/*  711:     */   {
/*  712: 696 */     this.mostrarConfiguracion = mostrarConfiguracion;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public boolean isMostrarVentasPrevias()
/*  716:     */   {
/*  717: 700 */     return this.mostrarVentasPrevias;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void setMostrarVentasPrevias(boolean mostrarVentasPrevias)
/*  721:     */   {
/*  722: 704 */     this.mostrarVentasPrevias = mostrarVentasPrevias;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public boolean isMostrarProyeccionVentas()
/*  726:     */   {
/*  727: 708 */     return this.mostrarProyeccionVentas;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void setMostrarProyeccionVentas(boolean mostrarProyeccionVentas)
/*  731:     */   {
/*  732: 712 */     this.mostrarProyeccionVentas = mostrarProyeccionVentas;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public boolean isMostrarPlanificacion()
/*  736:     */   {
/*  737: 716 */     return this.mostrarPlanificacion;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void setMostrarPlanificacion(boolean mostrarPlanificacion)
/*  741:     */   {
/*  742: 720 */     this.mostrarPlanificacion = mostrarPlanificacion;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public boolean isMostrarSaldos()
/*  746:     */   {
/*  747: 724 */     return this.mostrarSaldos;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public void setMostrarSaldos(boolean mostrarSaldos)
/*  751:     */   {
/*  752: 728 */     this.mostrarSaldos = mostrarSaldos;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public Date getFechaOrdenProduccion()
/*  756:     */   {
/*  757: 732 */     return this.fechaOrdenProduccion;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public void setFechaOrdenProduccion(Date fechaOrdenProduccion)
/*  761:     */   {
/*  762: 736 */     this.fechaOrdenProduccion = fechaOrdenProduccion;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public boolean isMostrarExtras()
/*  766:     */   {
/*  767: 740 */     return this.mostrarExtras;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void setMostrarExtras(boolean mostrarExtras)
/*  771:     */   {
/*  772: 744 */     this.mostrarExtras = mostrarExtras;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public boolean isMostrarFiltros()
/*  776:     */   {
/*  777: 748 */     return this.mostrarFiltros;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setMostrarFiltros(boolean mostrarFiltros)
/*  781:     */   {
/*  782: 752 */     this.mostrarFiltros = mostrarFiltros;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public boolean isEditable(int diaSemana)
/*  786:     */   {
/*  787: 756 */     Date fechaUltimaGeneracionOrdenes = this.planProduccion.getUltimaFechaGeneradaOrden();
/*  788: 757 */     if (fechaUltimaGeneracionOrdenes == null) {
/*  789: 758 */       return true;
/*  790:     */     }
/*  791: 760 */     fechaUltimaGeneracionOrdenes = FuncionesUtiles.getHoraCero(fechaUltimaGeneracionOrdenes);
/*  792: 761 */     Date fecha = FuncionesUtiles.sumarFechaDiasMeses(this.planProduccion.getFechaInicio(), diaSemana);
/*  793: 762 */     return fecha.after(fechaUltimaGeneracionOrdenes);
/*  794:     */   }
/*  795:     */   
/*  796:     */   public void prepararOrdenProduccion(PlanProduccion planProduccion)
/*  797:     */   {
/*  798: 766 */     this.planProduccion = planProduccion;
/*  799: 767 */     if (planProduccion.getUltimaFechaGeneradaOrden() != null) {
/*  800: 768 */       this.fechaOrdenProduccion = FuncionesUtiles.sumarFechaDiasMeses(planProduccion.getUltimaFechaGeneradaOrden(), 1);
/*  801:     */     } else {
/*  802: 770 */       this.fechaOrdenProduccion = planProduccion.getFechaInicio();
/*  803:     */     }
/*  804: 772 */     this.abiertaVentanaFecha = true;
/*  805: 773 */     FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelFechaOrdenProduccion");
/*  806: 774 */     RequestContext.getCurrentInstance().execute("dglModalGenerarOrdenProduccion.show()");
/*  807:     */   }
/*  808:     */   
/*  809:     */   public void generarOrdenProduccion()
/*  810:     */   {
/*  811:     */     try
/*  812:     */     {
/*  813: 779 */       this.servicioPlanProduccion.generarOrdenProduccion(this.planProduccion, this.fechaOrdenProduccion);
/*  814: 780 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  815: 781 */       this.abiertaVentanaFecha = false;
/*  816:     */     }
/*  817:     */     catch (AS2Exception e)
/*  818:     */     {
/*  819: 783 */       JsfUtil.addErrorMessage(e, "");
/*  820:     */     }
/*  821:     */   }
/*  822:     */   
/*  823:     */   public List<DetallePlanProduccion> getListaDetallePlanProduccionFiltrados()
/*  824:     */   {
/*  825: 788 */     return this.listaDetallePlanProduccionFiltrados;
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void setListaDetallePlanProduccionFiltrados(List<DetallePlanProduccion> listaDetallePlanProduccionFiltrados)
/*  829:     */   {
/*  830: 792 */     this.listaDetallePlanProduccionFiltrados = listaDetallePlanProduccionFiltrados;
/*  831:     */   }
/*  832:     */   
/*  833:     */   public int getTotalBatchLunes()
/*  834:     */   {
/*  835: 796 */     this.totalBatchLunes = 0;
/*  836: 797 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  837: 799 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  838: 800 */       if (!detallePlanProduccion.isEliminado()) {
/*  839: 801 */         this.totalBatchLunes += detallePlanProduccion.getBatchLunes();
/*  840:     */       }
/*  841:     */     }
/*  842: 804 */     return this.totalBatchLunes;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public int getTotalBatchMartes()
/*  846:     */   {
/*  847: 808 */     this.totalBatchMartes = 0;
/*  848: 809 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  849: 811 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  850: 812 */       if (!detallePlanProduccion.isEliminado()) {
/*  851: 813 */         this.totalBatchMartes += detallePlanProduccion.getBatchMartes();
/*  852:     */       }
/*  853:     */     }
/*  854: 816 */     return this.totalBatchMartes;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public int getTotalBatchMiercoles()
/*  858:     */   {
/*  859: 820 */     this.totalBatchMiercoles = 0;
/*  860: 821 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  861: 823 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  862: 824 */       if (!detallePlanProduccion.isEliminado()) {
/*  863: 825 */         this.totalBatchMiercoles += detallePlanProduccion.getBatchMiercoles();
/*  864:     */       }
/*  865:     */     }
/*  866: 828 */     return this.totalBatchMiercoles;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public int getTotalBatchJueves()
/*  870:     */   {
/*  871: 832 */     this.totalBatchJueves = 0;
/*  872: 833 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  873: 835 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  874: 836 */       if (!detallePlanProduccion.isEliminado()) {
/*  875: 837 */         this.totalBatchJueves += detallePlanProduccion.getBatchJueves();
/*  876:     */       }
/*  877:     */     }
/*  878: 840 */     return this.totalBatchJueves;
/*  879:     */   }
/*  880:     */   
/*  881:     */   public int getTotalBatchViernes()
/*  882:     */   {
/*  883: 844 */     this.totalBatchViernes = 0;
/*  884: 845 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  885: 847 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  886: 848 */       if (!detallePlanProduccion.isEliminado()) {
/*  887: 849 */         this.totalBatchViernes += detallePlanProduccion.getBatchViernes();
/*  888:     */       }
/*  889:     */     }
/*  890: 852 */     return this.totalBatchViernes;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public int getTotalBatchSabado()
/*  894:     */   {
/*  895: 856 */     this.totalBatchSabado = 0;
/*  896: 857 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  897: 859 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  898: 860 */       if (!detallePlanProduccion.isEliminado()) {
/*  899: 861 */         this.totalBatchSabado += detallePlanProduccion.getBatchSabado();
/*  900:     */       }
/*  901:     */     }
/*  902: 864 */     return this.totalBatchSabado;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public int getTotalBatchDomingo()
/*  906:     */   {
/*  907: 868 */     this.totalBatchDomingo = 0;
/*  908: 869 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  909: 871 */     for (DetallePlanProduccion detallePlanProduccion : lista) {
/*  910: 872 */       if (!detallePlanProduccion.isEliminado()) {
/*  911: 873 */         this.totalBatchDomingo += detallePlanProduccion.getBatchDomingo();
/*  912:     */       }
/*  913:     */     }
/*  914: 876 */     return this.totalBatchDomingo;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public int getColumnaInicioBatch()
/*  918:     */   {
/*  919: 880 */     int columnas = 3;
/*  920: 881 */     if (this.mostrarFiltros) {
/*  921: 882 */       columnas += 2;
/*  922:     */     }
/*  923: 884 */     if (this.mostrarConfiguracion) {
/*  924: 885 */       columnas += 2;
/*  925:     */     }
/*  926: 887 */     if (this.mostrarVentasPrevias) {
/*  927: 888 */       columnas += 8;
/*  928:     */     }
/*  929: 890 */     if (this.mostrarProyeccionVentas) {
/*  930: 891 */       columnas += 8;
/*  931:     */     }
/*  932: 894 */     return columnas;
/*  933:     */   }
/*  934:     */   
/*  935:     */   public int getColumnasPosterioresBatch()
/*  936:     */   {
/*  937: 898 */     int columnas = 2;
/*  938: 899 */     if (this.mostrarSaldos) {
/*  939: 900 */       columnas += 8;
/*  940:     */     }
/*  941: 902 */     if (this.mostrarExtras) {
/*  942: 903 */       columnas += 4;
/*  943:     */     }
/*  944: 906 */     return columnas;
/*  945:     */   }
/*  946:     */   
/*  947:     */   public boolean isAbiertaVentanaFecha()
/*  948:     */   {
/*  949: 910 */     return this.abiertaVentanaFecha;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public void setAbiertaVentanaFecha(boolean abiertaVentanaFecha)
/*  953:     */   {
/*  954: 914 */     this.abiertaVentanaFecha = abiertaVentanaFecha;
/*  955:     */   }
/*  956:     */   
/*  957:     */   public void totalizar() {}
/*  958:     */   
/*  959:     */   public BigDecimal getFactorCambio()
/*  960:     */   {
/*  961: 922 */     return this.factorCambio;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void setFactorCambio(BigDecimal factorCambio)
/*  965:     */   {
/*  966: 926 */     this.factorCambio = factorCambio;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void asignarFactorCambio()
/*  970:     */   {
/*  971: 930 */     List<DetallePlanProduccion> lista = this.listaDetallePlanProduccionFiltrados == null ? this.planProduccion.getListaDetallePlanProduccion() : this.listaDetallePlanProduccionFiltrados;
/*  972: 932 */     for (DetallePlanProduccion detallePlanProduccion : lista)
/*  973:     */     {
/*  974: 933 */       detallePlanProduccion.setFactorCambio(this.factorCambio);
/*  975: 934 */       proyectarVentas(detallePlanProduccion);
/*  976:     */     }
/*  977:     */   }
/*  978:     */   
/*  979:     */   public String getColorCelda(int diaSemana, DetallePlanProduccion dpp, boolean indicadorBatch)
/*  980:     */   {
/*  981: 939 */     BigDecimal saldoDiaSemana = BigDecimal.ZERO;
/*  982: 940 */     boolean indicadorPlanMaestro = false;
/*  983: 941 */     switch (diaSemana)
/*  984:     */     {
/*  985:     */     case 1: 
/*  986: 943 */       if (indicadorBatch) {
/*  987: 944 */         saldoDiaSemana = dpp.getSaldoMartes();
/*  988:     */       } else {
/*  989: 946 */         saldoDiaSemana = dpp.getSaldoLunes();
/*  990:     */       }
/*  991: 948 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroLunes();
/*  992: 949 */       break;
/*  993:     */     case 2: 
/*  994: 951 */       if (indicadorBatch) {
/*  995: 952 */         saldoDiaSemana = dpp.getSaldoMiercoles();
/*  996:     */       } else {
/*  997: 954 */         saldoDiaSemana = dpp.getSaldoMartes();
/*  998:     */       }
/*  999: 956 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroMartes();
/* 1000: 957 */       break;
/* 1001:     */     case 3: 
/* 1002: 959 */       if (indicadorBatch) {
/* 1003: 960 */         saldoDiaSemana = dpp.getSaldoJueves();
/* 1004:     */       } else {
/* 1005: 962 */         saldoDiaSemana = dpp.getSaldoMiercoles();
/* 1006:     */       }
/* 1007: 964 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroMiercoles();
/* 1008: 965 */       break;
/* 1009:     */     case 4: 
/* 1010: 967 */       if (indicadorBatch) {
/* 1011: 968 */         saldoDiaSemana = dpp.getSaldoViernes();
/* 1012:     */       } else {
/* 1013: 970 */         saldoDiaSemana = dpp.getSaldoJueves();
/* 1014:     */       }
/* 1015: 972 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroJueves();
/* 1016: 973 */       break;
/* 1017:     */     case 5: 
/* 1018: 975 */       if (indicadorBatch) {
/* 1019: 976 */         saldoDiaSemana = dpp.getSaldoSabado();
/* 1020:     */       } else {
/* 1021: 978 */         saldoDiaSemana = dpp.getSaldoViernes();
/* 1022:     */       }
/* 1023: 980 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroViernes();
/* 1024: 981 */       break;
/* 1025:     */     case 6: 
/* 1026: 983 */       if (indicadorBatch) {
/* 1027: 984 */         saldoDiaSemana = dpp.getSaldoDomingo();
/* 1028:     */       } else {
/* 1029: 986 */         saldoDiaSemana = dpp.getSaldoSabado();
/* 1030:     */       }
/* 1031: 988 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroSabado();
/* 1032: 989 */       break;
/* 1033:     */     case 7: 
/* 1034: 991 */       saldoDiaSemana = dpp.getSaldoDomingo();
/* 1035: 992 */       indicadorPlanMaestro = dpp.isIndicadorPlanMaestroDomingo();
/* 1036: 993 */       break;
/* 1037:     */     }
/* 1038: 998 */     String colorFondo = saldoDiaSemana.compareTo(dpp.getStockMinimo()) < 0 ? "color_amarillo" : saldoDiaSemana.compareTo(BigDecimal.ZERO) < 0 ? "color_rojo" : "color_verde";
/* 1039: 999 */     String colorTexto = indicadorPlanMaestro == true ? "text_color_azul" : "";
/* 1040:1001 */     if (indicadorBatch) {
/* 1041:1002 */       return colorFondo + " " + colorTexto;
/* 1042:     */     }
/* 1043:1004 */     return colorFondo;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public String getFormatoNumero2Decimales()
/* 1047:     */   {
/* 1048:1009 */     return ",###,##0.00";
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public String getFormatoNumero0Decimales()
/* 1052:     */   {
/* 1053:1013 */     return ",###,##0";
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void iniciarSolicitudConsumibles()
/* 1057:     */   {
/* 1058:1017 */     if ((this.planProduccion == null) || (this.planProduccion.getId() == 0))
/* 1059:     */     {
/* 1060:1018 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1061:     */     }
/* 1062:     */     else
/* 1063:     */     {
/* 1064:1020 */       Date fecha = this.planProduccion.getFechaInicio();
/* 1065:     */       
/* 1066:1022 */       this.planProduccion.setFecha1((Date)fecha.clone());
/* 1067:1023 */       this.planProduccion.setFecha2(FuncionesUtiles.sumarFechaDiasMeses(fecha, 1));
/* 1068:1024 */       this.planProduccion.setFecha3(FuncionesUtiles.sumarFechaDiasMeses(fecha, 2));
/* 1069:1025 */       this.planProduccion.setFecha4(FuncionesUtiles.sumarFechaDiasMeses(fecha, 3));
/* 1070:1026 */       this.planProduccion.setFecha5(FuncionesUtiles.sumarFechaDiasMeses(fecha, 4));
/* 1071:1027 */       this.planProduccion.setFecha6(FuncionesUtiles.sumarFechaDiasMeses(fecha, 5));
/* 1072:1028 */       this.planProduccion.setFecha7(FuncionesUtiles.sumarFechaDiasMeses(fecha, 6));
/* 1073:     */       
/* 1074:1030 */       Date hoy = new Date();
/* 1075:1031 */       hoy = FuncionesUtiles.getHoraCero(hoy);
/* 1076:     */       
/* 1077:1033 */       this.planProduccion.setIndicadorFecha1(!this.planProduccion.getFecha1().before(hoy));
/* 1078:1034 */       this.planProduccion.setIndicadorFecha2(!this.planProduccion.getFecha2().before(hoy));
/* 1079:1035 */       this.planProduccion.setIndicadorFecha3(!this.planProduccion.getFecha3().before(hoy));
/* 1080:1036 */       this.planProduccion.setIndicadorFecha4(!this.planProduccion.getFecha4().before(hoy));
/* 1081:1037 */       this.planProduccion.setIndicadorFecha5(!this.planProduccion.getFecha5().before(hoy));
/* 1082:1038 */       this.planProduccion.setIndicadorFecha6(!this.planProduccion.getFecha6().before(hoy));
/* 1083:1039 */       this.planProduccion.setIndicadorFecha7(!this.planProduccion.getFecha7().before(hoy));
/* 1084:     */       
/* 1085:1041 */       actualizarDetallesSolicitudConsumibles();
/* 1086:     */       
/* 1087:1043 */       RequestContext.getCurrentInstance().execute("dialogSolicitarConsumibles.show()");
/* 1088:     */     }
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public void actualizarDetallesSolicitudConsumibles()
/* 1092:     */   {
/* 1093:     */     try
/* 1094:     */     {
/* 1095:1049 */       this.listaDetalleConsumibles = this.servicioPlanProduccion.generarDetallesOrdenSalidaMaterial(this.planProduccion);
/* 1096:1050 */       this.listaDetalleConsumiblesFiltrados = null;
/* 1097:1051 */       this.dtDetalleConsumibles.reset();
/* 1098:     */     }
/* 1099:     */     catch (AS2Exception e)
/* 1100:     */     {
/* 1101:1053 */       JsfUtil.addErrorMessage(e, "");
/* 1102:     */     }
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public DataTable getDtDetalleConsumibles()
/* 1106:     */   {
/* 1107:1058 */     return this.dtDetalleConsumibles;
/* 1108:     */   }
/* 1109:     */   
/* 1110:     */   public void setDtDetalleConsumibles(DataTable dtDetalleConsumibles)
/* 1111:     */   {
/* 1112:1062 */     this.dtDetalleConsumibles = dtDetalleConsumibles;
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public List<DetalleOrdenSalidaMaterial> getListaDetalleConsumibles()
/* 1116:     */   {
/* 1117:1066 */     return this.listaDetalleConsumibles;
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void setListaDetalleConsumibles(List<DetalleOrdenSalidaMaterial> listaDetalleConsumibles)
/* 1121:     */   {
/* 1122:1070 */     this.listaDetalleConsumibles = listaDetalleConsumibles;
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   public List<DetalleOrdenSalidaMaterial> getListaDetalleConsumiblesFiltrados()
/* 1126:     */   {
/* 1127:1074 */     return this.listaDetalleConsumiblesFiltrados;
/* 1128:     */   }
/* 1129:     */   
/* 1130:     */   public void setListaDetalleConsumiblesFiltrados(List<DetalleOrdenSalidaMaterial> listaDetalleConsumiblesFiltrados)
/* 1131:     */   {
/* 1132:1078 */     this.listaDetalleConsumiblesFiltrados = listaDetalleConsumiblesFiltrados;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public void generarOrdenSolicitudMaterialConsumibles()
/* 1136:     */   {
/* 1137:1082 */     List<DetalleOrdenSalidaMaterial> lista = new ArrayList();
/* 1138:1083 */     if (this.listaDetalleConsumiblesFiltrados != null) {
/* 1139:1084 */       lista = this.listaDetalleConsumiblesFiltrados;
/* 1140:     */     } else {
/* 1141:1086 */       lista = this.listaDetalleConsumibles;
/* 1142:     */     }
/* 1143:     */     try
/* 1144:     */     {
/* 1145:1089 */       if (this.servicioPlanProduccion.generarOrdenSolicitudMaterialConsumibles(lista, this.descripcion))
/* 1146:     */       {
/* 1147:1090 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1148:1091 */         RequestContext.getCurrentInstance().execute("dialogSolicitarConsumibles.hide()");
/* 1149:1092 */         this.descripcion = "";
/* 1150:     */       }
/* 1151:     */       else
/* 1152:     */       {
/* 1153:1094 */         addErrorMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/* 1154:     */       }
/* 1155:1096 */       this.listaDetalleConsumiblesFiltrados = null;
/* 1156:1097 */       this.dtDetalleConsumibles.reset();
/* 1157:     */     }
/* 1158:     */     catch (AS2Exception e)
/* 1159:     */     {
/* 1160:1099 */       JsfUtil.addErrorMessage(e, "");
/* 1161:     */     }
/* 1162:     */     catch (ExcepcionAS2Financiero e)
/* 1163:     */     {
/* 1164:1101 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1165:1102 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1166:     */     }
/* 1167:     */     catch (ExcepcionAS2 e)
/* 1168:     */     {
/* 1169:1104 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1170:1105 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1171:     */     }
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public String getDescripcion()
/* 1175:     */   {
/* 1176:1110 */     return this.descripcion;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public void setDescripcion(String descripcion)
/* 1180:     */   {
/* 1181:1114 */     this.descripcion = descripcion;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public List<Bodega> getListaBodegaSeleccionada()
/* 1185:     */   {
/* 1186:1118 */     return this.listaBodegaSeleccionada;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public void setListaBodegaSeleccionada(List<Bodega> listaBodegaSeleccionada)
/* 1190:     */   {
/* 1191:1122 */     this.listaBodegaSeleccionada = listaBodegaSeleccionada;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public List<Bodega> autocompletarBodegasActivas(String consulta)
/* 1195:     */   {
/* 1196:1127 */     return autocompletarBodega(Boolean.valueOf(true), consulta);
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public List<Bodega> autocompletarBodega(Boolean activo, String consulta)
/* 1200:     */   {
/* 1201:1131 */     Map<String, String> filters = new HashMap();
/* 1202:1132 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 1203:1133 */     if (activo != null) {
/* 1204:1134 */       filters.put("activo", String.valueOf(activo));
/* 1205:     */     }
/* 1206:1136 */     return this.servicioBodega.obtenerListaCombo("nombre", true, filters);
/* 1207:     */   }
/* 1208:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.PlanProduccionBean
 * JD-Core Version:    0.7.0.1
 */