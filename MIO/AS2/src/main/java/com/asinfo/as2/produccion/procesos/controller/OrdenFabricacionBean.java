/*    1:     */ package com.asinfo.as2.produccion.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    4:     */ import com.asinfo.as2.controller.LanguageController;
/*    5:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*    8:     */ import com.asinfo.as2.entities.Atributo;
/*    9:     */ import com.asinfo.as2.entities.Bodega;
/*   10:     */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*   11:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   12:     */ import com.asinfo.as2.entities.Documento;
/*   13:     */ import com.asinfo.as2.entities.OrdenFabricacionMaquina;
/*   14:     */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   15:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   16:     */ import com.asinfo.as2.entities.Organizacion;
/*   17:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   18:     */ import com.asinfo.as2.entities.PersonaResponsable;
/*   19:     */ import com.asinfo.as2.entities.Producto;
/*   20:     */ import com.asinfo.as2.entities.Sucursal;
/*   21:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   22:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacion;
/*   23:     */ import com.asinfo.as2.entities.produccion.Maquina;
/*   24:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   25:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
/*   26:     */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   27:     */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*   28:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   29:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   30:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   31:     */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   32:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   33:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   34:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   35:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   36:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*   37:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   38:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   39:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*   40:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*   41:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   42:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   43:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*   44:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   45:     */ import com.asinfo.as2.util.AppUtil;
/*   46:     */ import com.asinfo.as2.utils.JsfUtil;
/*   47:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   48:     */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*   49:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDetallePedidoCliente;
/*   50:     */ import java.math.BigDecimal;
/*   51:     */ import java.math.RoundingMode;
/*   52:     */ import java.util.ArrayList;
/*   53:     */ import java.util.Arrays;
/*   54:     */ import java.util.Calendar;
/*   55:     */ import java.util.Date;
/*   56:     */ import java.util.HashMap;
/*   57:     */ import java.util.HashSet;
/*   58:     */ import java.util.Iterator;
/*   59:     */ import java.util.List;
/*   60:     */ import java.util.Map;
/*   61:     */ import java.util.Set;
/*   62:     */ import javax.annotation.PostConstruct;
/*   63:     */ import javax.ejb.EJB;
/*   64:     */ import javax.faces.bean.ManagedBean;
/*   65:     */ import javax.faces.bean.ManagedProperty;
/*   66:     */ import javax.faces.bean.ViewScoped;
/*   67:     */ import javax.faces.component.html.HtmlInputText;
/*   68:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   69:     */ import javax.faces.model.SelectItem;
/*   70:     */ import org.apache.log4j.Logger;
/*   71:     */ import org.primefaces.component.datatable.DataTable;
/*   72:     */ import org.primefaces.event.SelectEvent;
/*   73:     */ import org.primefaces.event.ToggleEvent;
/*   74:     */ import org.primefaces.model.LazyDataModel;
/*   75:     */ import org.primefaces.model.SortOrder;
/*   76:     */ 
/*   77:     */ @ManagedBean
/*   78:     */ @ViewScoped
/*   79:     */ public class OrdenFabricacionBean
/*   80:     */   extends PageControllerAS2
/*   81:     */ {
/*   82:     */   private static final long serialVersionUID = 1424377436906270962L;
/*   83:     */   @EJB
/*   84:     */   private ServicioDetallePedidoCliente servicioDetallePedidoCliente;
/*   85:     */   @EJB
/*   86:     */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*   87:     */   @EJB
/*   88:     */   private ServicioGenerico<OrdenFabricacion> servicioOrdenFabricacionGenerico;
/*   89:     */   @EJB
/*   90:     */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*   91:     */   @EJB
/*   92:     */   private ServicioBodega servicioBodega;
/*   93:     */   @EJB
/*   94:     */   private ServicioDocumento servicioDocumento;
/*   95:     */   @EJB
/*   96:     */   private ServicioProducto servicioProducto;
/*   97:     */   @EJB
/*   98:     */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*   99:     */   @EJB
/*  100:     */   private ServicioMaquina servicioMaquina;
/*  101:     */   @EJB
/*  102:     */   private ServicioPersonaResponsable servicioPersonaResponsable;
/*  103:     */   @EJB
/*  104:     */   private ServicioOrganizacion servicioOrganizacion;
/*  105:     */   @EJB
/*  106:     */   private ServicioAtributo servicioAtributo;
/*  107:     */   @EJB
/*  108:     */   private ServicioValorAtributo servicioValorAtributo;
/*  109:     */   private SelectItem[] listaEstadoItem;
/*  110:     */   private SelectItem[] listaTipoCicloProduccionItem;
/*  111:     */   private DataTable dtDetalleOrden;
/*  112:     */   private DataTable dtSubordenInmediata;
/*  113:     */   private DataTable dtSubordenOtras;
/*  114:     */   private List<OrdenFabricacion> listaOrdenFabricacionSeleccionados;
/*  115:     */   private OrdenFabricacion ordenFabricacion;
/*  116:     */   private List<DetalleOrdenFabricacion> listaDetalleOrden;
/*  117:     */   private List<DetalleOrdenFabricacion> listaDetalleOrdenFilter;
/*  118:     */   private List<OrdenFabricacion> listaOrdenFabricacionFinalizar;
/*  119:     */   private List<Maquina> listaMaquina;
/*  120:     */   private Integer[] listaMaquinaSeleccionados;
/*  121:     */   private List<PersonaResponsable> listaPersonaResponsable;
/*  122:     */   private OrdenSalidaMaterial ordenSalidaMaterialAsignarSolicitud;
/*  123:     */   private LazyDataModel<OrdenFabricacion> listaOrdenFabricacion;
/*  124:     */   private List<Bodega> listaBodega;
/*  125:     */   private List<Documento> listaDocumento;
/*  126:     */   @ManagedProperty("#{listaProductoBean}")
/*  127:     */   private ListaProductoBean listaProductoBean;
/*  128: 165 */   private Date fechaLanzamiento = new Date();
/*  129:     */   private Date fechaSalidaMaterial;
/*  130: 167 */   private Date fechaCierreOrden = new Date();
/*  131:     */   private String descripcion;
/*  132:     */   private Boolean indicadorRequiereFormulacion;
/*  133:     */   private Integer idOrdenFabricacion;
/*  134:     */   private List<TipoCicloProduccionEnum> listaTipoCicloEnum;
/*  135:     */   private EnumProductoEditado enumProductoEditado;
/*  136:     */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  137:     */   
/*  138:     */   private static enum EnumProductoEditado
/*  139:     */   {
/*  140: 177 */     PRODUCTO_CABECERA,  PRODUCTO_DETALLE;
/*  141:     */     
/*  142:     */     private EnumProductoEditado() {}
/*  143:     */   }
/*  144:     */   
/*  145:     */   @PostConstruct
/*  146:     */   public void init()
/*  147:     */   {
/*  148: 194 */     getListaProductoBean().resetearIndicadores();
/*  149: 195 */     getListaProductoBean().setIndicadorProduccion(true);
/*  150:     */     
/*  151: 197 */     this.listaOrdenFabricacion = new LazyDataModel()
/*  152:     */     {
/*  153:     */       private static final long serialVersionUID = 1L;
/*  154:     */       
/*  155:     */       public List<OrdenFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  156:     */       {
/*  157: 204 */         List<OrdenFabricacion> lista = new ArrayList();
/*  158: 205 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  159: 207 */         if (filters.size() == 0) {
/*  160: 209 */           filters.put("estado", "!=" + EstadoProduccionEnum.FINALIZADA.toString());
/*  161:     */         }
/*  162: 211 */         filters = OrdenFabricacionBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  163: 212 */         filters.put("indicadorSuborden", "false");
/*  164: 214 */         if (OrdenFabricacionBean.this.idOrdenFabricacion != null) {
/*  165: 215 */           filters.put("idOrdenFabricacion", OrdenFabricacionBean.this.idOrdenFabricacion + "");
/*  166:     */         }
/*  167: 217 */         OrdenFabricacionBean.this.idOrdenFabricacion = null;
/*  168:     */         
/*  169: 219 */         lista = OrdenFabricacionBean.this.servicioOrdenFabricacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, true);
/*  170:     */         
/*  171: 221 */         OrdenFabricacionBean.this.listaOrdenFabricacion.setRowCount(OrdenFabricacionBean.this.servicioOrdenFabricacion.contarPorCriterio(filters));
/*  172:     */         
/*  173: 223 */         return lista;
/*  174:     */       }
/*  175:     */     };
/*  176:     */   }
/*  177:     */   
/*  178:     */   public void cargarValor(DetalleOrdenFabricacion dof)
/*  179:     */   {
/*  180: 238 */     if (dof.getDetallePedido() != null) {
/*  181: 240 */       dof.setCantidad(dof.getDetallePedido().getCantidad());
/*  182:     */     }
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void limpiarValor(DetalleOrdenFabricacion dof)
/*  186:     */   {
/*  187: 245 */     dof.setCantidad(BigDecimal.ZERO);
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void calcularTotales() {}
/*  191:     */   
/*  192:     */   public void cargarProducto(Producto producto)
/*  193:     */   {
/*  194: 257 */     if (producto != null)
/*  195:     */     {
/*  196: 259 */       producto = this.servicioProducto.cargarDetalleOrdenFabricacion(producto.getId());
/*  197: 260 */       Sucursal sucursal = AppUtil.getSucursal();
/*  198:     */       ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal;
/*  199: 262 */       switch (2.$SwitchMap$com$asinfo$as2$produccion$procesos$controller$OrdenFabricacionBean$EnumProductoEditado[this.enumProductoEditado.ordinal()])
/*  200:     */       {
/*  201:     */       case 1: 
/*  202:     */         try
/*  203:     */         {
/*  204: 268 */           for (BodegaTrabajoProductoSucursal bodega : producto.getListaBodegaTrabajoSucursal()) {
/*  205: 269 */             if (bodega.getSucursal().getId() == sucursal.getId())
/*  206:     */             {
/*  207: 270 */               this.ordenFabricacion.setBodega(bodega.getBodegaTrabajo());
/*  208: 271 */               break;
/*  209:     */             }
/*  210:     */           }
/*  211: 276 */           for (??? = producto.getListaProductoRutaFabricacionSucursal().iterator(); ???.hasNext();)
/*  212:     */           {
/*  213: 276 */             productoRutaFabricacionSucursal = (ProductoRutaFabricacionSucursal)???.next();
/*  214: 277 */             if (productoRutaFabricacionSucursal.getSucursal().getId() == sucursal.getId())
/*  215:     */             {
/*  216: 278 */               this.ordenFabricacion.setRutaFabricacion(productoRutaFabricacionSucursal.getRutaFabricacion());
/*  217: 279 */               break;
/*  218:     */             }
/*  219:     */           }
/*  220: 283 */           this.ordenFabricacion.setProducto(producto);
/*  221: 284 */           this.ordenFabricacion.setRutaFabricacion(producto.getRutaFabricacion());
/*  222: 286 */           if (this.ordenFabricacion.getCantidadBatch().compareTo(BigDecimal.ZERO) <= 0) {
/*  223: 287 */             this.ordenFabricacion.setCantidadBatch(BigDecimal.ONE);
/*  224:     */           }
/*  225: 290 */           this.servicioOrdenFabricacion.cargarSubordenes(getOrdenFabricacion());
/*  226: 291 */           setearDTDetalleOrden();
/*  227:     */         }
/*  228:     */         catch (AS2Exception e)
/*  229:     */         {
/*  230: 294 */           crearEntidad();
/*  231: 295 */           JsfUtil.addErrorMessage(e, "");
/*  232:     */         }
/*  233:     */       case 2: 
/*  234:     */         try
/*  235:     */         {
/*  236: 303 */           OrdenFabricacion suborden = new OrdenFabricacion();
/*  237: 304 */           suborden.setIdOrganizacion(this.ordenFabricacion.getIdOrganizacion());
/*  238: 305 */           suborden.setSucursal(this.ordenFabricacion.getSucursal());
/*  239: 306 */           suborden.setBodega(this.ordenFabricacion.getBodega());
/*  240: 307 */           suborden.setDocumento(this.ordenFabricacion.getDocumento());
/*  241: 308 */           suborden.setEstado(EstadoProduccionEnum.PLANEADA);
/*  242: 309 */           suborden.setFecha(this.ordenFabricacion.getFecha());
/*  243: 310 */           suborden.setOrdenFabricacionPadre(this.ordenFabricacion != null ? this.ordenFabricacion : null);
/*  244: 311 */           suborden.setOrdenFabricacionPrincipal(this.ordenFabricacion);
/*  245: 312 */           suborden.setPlanProduccion(this.ordenFabricacion.getPlanProduccion());
/*  246: 313 */           suborden.setProducto(producto);
/*  247: 314 */           suborden.setRutaFabricacion(producto.getRutaFabricacion());
/*  248: 315 */           suborden.setIndicadorSuborden(true);
/*  249: 316 */           suborden.setIndicadorHoja(true);
/*  250: 319 */           for (BodegaTrabajoProductoSucursal bodega : producto.getListaBodegaTrabajoSucursal()) {
/*  251: 320 */             if (bodega.getSucursal().getId() == sucursal.getId()) {
/*  252: 321 */               suborden.setBodega(bodega.getBodegaTrabajo());
/*  253:     */             }
/*  254:     */           }
/*  255: 326 */           for (ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal : producto.getListaProductoRutaFabricacionSucursal()) {
/*  256: 327 */             if (productoRutaFabricacionSucursal.getSucursal().getId() == sucursal.getId()) {
/*  257: 328 */               suborden.setRutaFabricacion(productoRutaFabricacionSucursal.getRutaFabricacion());
/*  258:     */             }
/*  259:     */           }
/*  260: 333 */           if (suborden.getRutaFabricacion() == null) {
/*  261: 335 */             throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_RUTA_FABRICACION_NULL_PRODUCTO", new String[] {suborden.getProducto().getCodigo() + "-" + suborden.getProducto().getNombre() });
/*  262:     */           }
/*  263: 339 */           if (suborden.getBodega() == null) {
/*  264: 341 */             throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] {suborden.getProducto().getCodigo() + "-" + suborden.getProducto().getNombre() });
/*  265:     */           }
/*  266: 344 */           actualizarAtributos(suborden);
/*  267: 345 */           this.ordenFabricacion.setBodega(suborden.getBodega());
/*  268: 346 */           this.ordenFabricacion.getListaSubordenes().add(suborden);
/*  269:     */         }
/*  270:     */         catch (AS2Exception e)
/*  271:     */         {
/*  272: 349 */           JsfUtil.addErrorMessage(e, "");
/*  273:     */         }
/*  274:     */       }
/*  275:     */     }
/*  276:     */   }
/*  277:     */   
/*  278:     */   private void crearEntidad()
/*  279:     */   {
/*  280: 366 */     this.ordenFabricacion = new OrdenFabricacion();
/*  281: 367 */     this.ordenFabricacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  282: 368 */     this.ordenFabricacion.setSucursal(AppUtil.getSucursal());
/*  283: 369 */     this.ordenFabricacion.setEstado(EstadoProduccionEnum.PLANEADA);
/*  284: 370 */     this.ordenFabricacion.setFecha(new Date());
/*  285: 371 */     this.ordenFabricacion.setBodega(AppUtil.getBodega());
/*  286: 372 */     this.ordenFabricacion.setIndicadorDirecto(getNumeroAtributos() == 0);
/*  287: 374 */     if (!getListaDocumento().isEmpty()) {
/*  288: 375 */       this.ordenFabricacion.setDocumento((Documento)getListaDocumento().get(0));
/*  289:     */     }
/*  290: 378 */     changeTipoOrdenFabricacion();
/*  291:     */   }
/*  292:     */   
/*  293:     */   public String editar()
/*  294:     */   {
/*  295:     */     try
/*  296:     */     {
/*  297: 389 */       this.ordenFabricacion = null;
/*  298: 390 */       if ((getOrdenFabricacion() != null) && (getOrdenFabricacion().getIdOrdenFabricacion() != 0))
/*  299:     */       {
/*  300: 391 */         OrdenFabricacion of = this.servicioOrdenFabricacion.buscarPorId(getOrdenFabricacion().getId());
/*  301: 392 */         if ((of.getEstado() == EstadoProduccionEnum.PLANEADA) && (!of.isIndicadorFormulada()) && 
/*  302: 393 */           (getOrdenFabricacion().getOrdenFabricacionPadre() == null))
/*  303:     */         {
/*  304: 394 */           this.ordenFabricacion = this.servicioOrdenFabricacion.cargarDetalle(getOrdenFabricacion().getIdOrdenFabricacion());
/*  305: 395 */           this.ordenFabricacion.setBodega(this.ordenFabricacion.getBodega() == null ? AppUtil.getBodega() : this.ordenFabricacion.getBodega());
/*  306: 396 */           this.servicioOrdenFabricacion.cargarSubordenes(getOrdenFabricacion());
/*  307: 397 */           setearDTDetalleOrden();
/*  308: 398 */           cargarListaMaquinaSeleccionados();
/*  309: 399 */           actualizarAtributoOrdenFabricacionPadre();
/*  310: 400 */           setEditado(true);
/*  311:     */         }
/*  312:     */         else
/*  313:     */         {
/*  314: 402 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  315:     */         }
/*  316:     */       }
/*  317:     */       else
/*  318:     */       {
/*  319: 405 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  320:     */       }
/*  321:     */     }
/*  322:     */     catch (AS2Exception e)
/*  323:     */     {
/*  324: 408 */       JsfUtil.addErrorMessage(e, "");
/*  325:     */     }
/*  326: 410 */     return "";
/*  327:     */   }
/*  328:     */   
/*  329:     */   public String guardar()
/*  330:     */   {
/*  331:     */     try
/*  332:     */     {
/*  333: 421 */       crearOrdenFabricacionMaquina();
/*  334: 422 */       actualizarDatosSubordenes();
/*  335: 423 */       this.servicioOrdenFabricacion.guardar(this.ordenFabricacion);
/*  336: 424 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  337: 425 */       limpiar();
/*  338: 426 */       setEditado(false);
/*  339:     */     }
/*  340:     */     catch (ExcepcionAS2Financiero e)
/*  341:     */     {
/*  342: 429 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  343: 430 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  344:     */     }
/*  345:     */     catch (ExcepcionAS2 e)
/*  346:     */     {
/*  347: 432 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  348: 433 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  349:     */     }
/*  350:     */     catch (AS2Exception e)
/*  351:     */     {
/*  352: 435 */       JsfUtil.addErrorMessage(e, "");
/*  353: 436 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  354:     */     }
/*  355:     */     catch (Exception e)
/*  356:     */     {
/*  357: 438 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  358: 439 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  359:     */     }
/*  360: 441 */     return "";
/*  361:     */   }
/*  362:     */   
/*  363:     */   public List<OrdenFabricacion> getListaOrdenFabricacionSolicitarMateriales()
/*  364:     */   {
/*  365: 445 */     List<OrdenFabricacion> lista = new ArrayList();
/*  366: 446 */     if (this.listaOrdenFabricacionSeleccionados != null) {
/*  367: 447 */       for (OrdenFabricacion orden : this.listaOrdenFabricacionSeleccionados)
/*  368:     */       {
/*  369: 450 */         HashMap<String, String> filters = new HashMap();
/*  370: 451 */         filters.put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + orden.getIdOrdenFabricacion());
/*  371: 452 */         filters.put("materialesSolicitados", "false");
/*  372: 453 */         filters.put("estado", "!=" + EstadoProduccionEnum.FINALIZADA.toString());
/*  373: 454 */         filters.put("estado", "!=" + EstadoProduccionEnum.ANULADO.toString());
/*  374: 455 */         filters.put("tipoCicloProduccionEnum", TipoCicloProduccionEnum.CICLO_CORTO.toString());
/*  375:     */         
/*  376: 457 */         List<OrdenFabricacion> subordenes = this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 1000, "numero", true, filters, true);
/*  377: 458 */         orden.setListaSubordenesFabricacion(subordenes);
/*  378: 460 */         if ((!orden.getListaSubordenesFabricacion().isEmpty()) && (!orden.isMaterialesSolicitados()) && 
/*  379: 461 */           (!orden.getEstado().equals(EstadoProduccionEnum.FINALIZADA)) && (!orden.getEstado().equals(EstadoProduccionEnum.ANULADO)) && 
/*  380: 462 */           (TipoCicloProduccionEnum.CICLO_CORTO.equals(orden.getTipoCicloProduccionEnum()))) {
/*  381: 463 */           lista.add(orden);
/*  382: 464 */         } else if ((!orden.isMaterialesSolicitados()) && (!orden.getEstado().equals(EstadoProduccionEnum.FINALIZADA)) && 
/*  383: 465 */           (!orden.getEstado().equals(EstadoProduccionEnum.ANULADO)) && 
/*  384: 466 */           (TipoCicloProduccionEnum.CICLO_CORTO.equals(orden.getTipoCicloProduccionEnum())) && (
/*  385: 467 */           (!getIndicadorRequiereFormulacion().booleanValue()) || (!orden.isIndicadorHoja()) || (orden.isIndicadorFormulada()))) {
/*  386: 468 */           lista.add(orden);
/*  387:     */         }
/*  388:     */       }
/*  389:     */     }
/*  390: 472 */     return lista;
/*  391:     */   }
/*  392:     */   
/*  393:     */   public List<OrdenFabricacion> getListaOrdenFabricacionLanzar()
/*  394:     */   {
/*  395: 476 */     List<OrdenFabricacion> lista = new ArrayList();
/*  396: 477 */     if (this.listaOrdenFabricacionSeleccionados != null) {
/*  397: 479 */       for (OrdenFabricacion ordenFabricacion : this.listaOrdenFabricacionSeleccionados)
/*  398:     */       {
/*  399: 482 */         HashMap<String, String> filters = new HashMap();
/*  400: 483 */         filters.put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + ordenFabricacion.getIdOrdenFabricacion());
/*  401: 484 */         filters.put("estado", "" + EstadoProduccionEnum.PLANEADA.toString());
/*  402: 485 */         List<OrdenFabricacion> subordenes = this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 1000, "numero", true, filters, false);
/*  403: 486 */         ordenFabricacion.setListaSubordenesFabricacion(subordenes);
/*  404: 487 */         ordenFabricacion.setIndicadorRequiereFormulacion(getIndicadorRequiereFormulacion().booleanValue());
/*  405: 490 */         if ((ordenFabricacion.getEstado().equals(EstadoProduccionEnum.PLANEADA)) && 
/*  406: 491 */           (!ordenFabricacion.getListaSubordenesFabricacion().isEmpty())) {
/*  407: 492 */           lista.add(ordenFabricacion);
/*  408: 493 */         } else if ((ordenFabricacion.getEstado().equals(EstadoProduccionEnum.PLANEADA)) && (
/*  409: 494 */           (!getIndicadorRequiereFormulacion().booleanValue()) || (!ordenFabricacion.isIndicadorHoja()) || (ordenFabricacion.isIndicadorFormulada()))) {
/*  410: 495 */           lista.add(ordenFabricacion);
/*  411:     */         }
/*  412:     */       }
/*  413:     */     }
/*  414: 499 */     return lista;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public List<OrdenFabricacion> getListaOrdenFabricacionFinalizar()
/*  418:     */   {
/*  419: 503 */     return this.listaOrdenFabricacionFinalizar;
/*  420:     */   }
/*  421:     */   
/*  422:     */   public String solicitarMaterial()
/*  423:     */   {
/*  424:     */     try
/*  425:     */     {
/*  426: 508 */       this.servicioOrdenFabricacion.solicitarMaterial(getListaOrdenFabricacionSolicitarMateriales(), this.descripcion, this.ordenSalidaMaterialAsignarSolicitud, this.fechaSalidaMaterial, 
/*  427: 509 */         isCosteoSubOrdenes());
/*  428: 510 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  429: 511 */       this.descripcion = "";
/*  430: 512 */       this.fechaSalidaMaterial = null;
/*  431: 513 */       this.ordenSalidaMaterialAsignarSolicitud = null;
/*  432:     */     }
/*  433:     */     catch (AS2Exception e)
/*  434:     */     {
/*  435: 515 */       JsfUtil.addErrorMessage(e, "");
/*  436:     */     }
/*  437:     */     catch (ExcepcionAS2Financiero e)
/*  438:     */     {
/*  439: 517 */       e.printStackTrace();
/*  440: 518 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  441:     */     }
/*  442:     */     catch (ExcepcionAS2 e)
/*  443:     */     {
/*  444: 520 */       e.printStackTrace();
/*  445: 521 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  446:     */     }
/*  447:     */     catch (Exception e)
/*  448:     */     {
/*  449: 523 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  450: 524 */       LOG.error("ERROR AL GUARDAR DATOS SOLICITAR MATERIAL (ORDEN DE SALIDA DE MERCADERIA)", e);
/*  451:     */     }
/*  452: 526 */     return "";
/*  453:     */   }
/*  454:     */   
/*  455:     */   public List<RutaFabricacion> autocompletarRutaFabricacion(String consulta)
/*  456:     */   {
/*  457: 530 */     if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getProducto() != null)) {
/*  458: 531 */       return this.servicioRutaFabricacion.autocompletarRutaFabricacion(this.ordenFabricacion.getProducto(), consulta);
/*  459:     */     }
/*  460: 533 */     return new ArrayList();
/*  461:     */   }
/*  462:     */   
/*  463:     */   public String eliminar()
/*  464:     */   {
/*  465:     */     try
/*  466:     */     {
/*  467: 546 */       this.ordenFabricacion = null;
/*  468: 547 */       if ((getOrdenFabricacion() != null) && (getOrdenFabricacion().getIdOrdenFabricacion() != 0))
/*  469:     */       {
/*  470: 548 */         this.servicioOrdenFabricacion.anular(getOrdenFabricacion());
/*  471: 549 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  472:     */       }
/*  473:     */       else
/*  474:     */       {
/*  475: 551 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  476:     */       }
/*  477:     */     }
/*  478:     */     catch (AS2Exception e)
/*  479:     */     {
/*  480: 554 */       JsfUtil.addErrorMessage(e, "");
/*  481:     */     }
/*  482:     */     catch (Exception e)
/*  483:     */     {
/*  484: 556 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  485: 557 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  486:     */     }
/*  487: 560 */     return "";
/*  488:     */   }
/*  489:     */   
/*  490:     */   public String cargarDatos()
/*  491:     */   {
/*  492: 569 */     return "";
/*  493:     */   }
/*  494:     */   
/*  495:     */   public String limpiar()
/*  496:     */   {
/*  497: 578 */     crearEntidad();
/*  498: 579 */     setearDTDetalleOrden();
/*  499: 580 */     this.ordenSalidaMaterialAsignarSolicitud = null;
/*  500: 581 */     return "";
/*  501:     */   }
/*  502:     */   
/*  503:     */   private void setearDTDetalleOrden()
/*  504:     */   {
/*  505: 585 */     if (this.dtDetalleOrden != null) {
/*  506: 586 */       this.dtDetalleOrden.reset();
/*  507:     */     }
/*  508: 589 */     this.listaDetalleOrden = null;
/*  509: 590 */     this.listaDetalleOrdenFilter = null;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void lanzarOrdenFabricacion()
/*  513:     */   {
/*  514: 594 */     for (OrdenFabricacion orden : getListaOrdenFabricacionLanzar()) {
/*  515:     */       try
/*  516:     */       {
/*  517: 596 */         orden.setFechaLanzamiento(this.fechaLanzamiento);
/*  518: 597 */         this.servicioOrdenFabricacion.lanzarOrden(orden);
/*  519: 598 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  520:     */       }
/*  521:     */       catch (AS2Exception e)
/*  522:     */       {
/*  523: 600 */         JsfUtil.addErrorMessage(e, "");
/*  524: 601 */         LOG.info("ERROR AL LANZAR LA ORDEN DE FABRICACION", e);
/*  525:     */       }
/*  526:     */     }
/*  527:     */   }
/*  528:     */   
/*  529:     */   public void finalizarOrdenFabricacion()
/*  530:     */   {
/*  531: 608 */     for (OrdenFabricacion orden : getListaOrdenFabricacionFinalizar()) {
/*  532:     */       try
/*  533:     */       {
/*  534: 610 */         this.servicioOrdenFabricacion.finalizarOrden(orden, getFechaCierreOrden(), isCosteoSubOrdenes());
/*  535: 611 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  536:     */       }
/*  537:     */       catch (ExcepcionAS2Financiero e)
/*  538:     */       {
/*  539: 613 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  540: 614 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  541:     */       }
/*  542:     */       catch (ExcepcionAS2 e)
/*  543:     */       {
/*  544: 616 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  545: 617 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  546:     */       }
/*  547:     */       catch (AS2Exception e)
/*  548:     */       {
/*  549: 619 */         JsfUtil.addErrorMessage(e, "");
/*  550: 620 */         LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/*  551:     */       }
/*  552:     */     }
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void cargarPedidosListener()
/*  556:     */   {
/*  557: 626 */     if ((null != getOrdenFabricacion()) && (null != getOrdenFabricacion().getProducto()))
/*  558:     */     {
/*  559: 629 */       List<DetallePedidoCliente> listaDetallePedidoCliente = this.servicioDetallePedidoCliente.buscarDetallePedidoClientePorProducto(
/*  560: 630 */         getOrdenFabricacion().getProducto().getIdProducto(), AppUtil.getOrganizacion().getIdOrganizacion());
/*  561: 632 */       for (DetallePedidoCliente dpc : listaDetallePedidoCliente)
/*  562:     */       {
/*  563: 633 */         DetalleOrdenFabricacion dofo = new DetalleOrdenFabricacion();
/*  564:     */         
/*  565:     */ 
/*  566:     */ 
/*  567:     */ 
/*  568:     */ 
/*  569:     */ 
/*  570: 640 */         dofo.setCantidad(dpc.getCantidad().subtract(dpc.getCantidadEnviadaAProducir()));
/*  571: 641 */         dofo.setCantidad(dpc.getCantidad());
/*  572: 642 */         dofo.setDetallePedido(dpc);
/*  573: 643 */         dofo.setOrdenFabricacion(getOrdenFabricacion());
/*  574: 644 */         dofo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  575: 645 */         dofo.setSucursal(AppUtil.getSucursal());
/*  576:     */         
/*  577: 647 */         getListaDetalleOrden().add(dofo);
/*  578:     */       }
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void changeTipoOrdenFabricacion()
/*  583:     */   {
/*  584: 655 */     boolean indicadorDirecto = this.ordenFabricacion == null ? true : this.ordenFabricacion.isIndicadorDirecto();
/*  585: 656 */     this.ordenFabricacion = new OrdenFabricacion();
/*  586: 657 */     this.ordenFabricacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  587: 658 */     this.ordenFabricacion.setSucursal(AppUtil.getSucursal());
/*  588: 659 */     this.ordenFabricacion.setEstado(EstadoProduccionEnum.PLANEADA);
/*  589: 660 */     this.ordenFabricacion.setFecha(new Date());
/*  590: 661 */     this.ordenFabricacion.setBodega(AppUtil.getBodega());
/*  591: 662 */     this.ordenFabricacion.setIndicadorDirecto(indicadorDirecto);
/*  592: 663 */     if (!getListaDocumento().isEmpty()) {
/*  593: 664 */       this.ordenFabricacion.setDocumento((Documento)getListaDocumento().get(0));
/*  594:     */     }
/*  595: 667 */     if (getOrganizacionConfiguracion().getAtributo1() != null)
/*  596:     */     {
/*  597: 668 */       Atributo atributo = this.servicioAtributo.cargarDetalle(this.organizacionConfiguracion.getAtributo1().getIdAtributo());
/*  598: 669 */       this.ordenFabricacion.setAtributoOrdenFabricacion(atributo);
/*  599: 670 */       this.ordenFabricacion.setValorAtributoOrdenFabricacion(atributo.getValorAtributoPredeterminado());
/*  600:     */     }
/*  601: 673 */     setearDTDetalleOrden();
/*  602: 674 */     this.ordenSalidaMaterialAsignarSolicitud = null;
/*  603: 676 */     if ((!this.ordenFabricacion.isIndicadorDirecto()) && 
/*  604: 677 */       (getOrganizacionConfiguracion().getAtributo1() == null))
/*  605:     */     {
/*  606: 678 */       addInfoMessage(getLanguageController().getMensaje("msg_info_orden_fabricacion_indirecto"));
/*  607: 679 */       this.ordenFabricacion.setIndicadorDirecto(true);
/*  608: 680 */       limpiar();
/*  609:     */     }
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void actualizarAtributoOrdenFabricacionPadre()
/*  613:     */   {
/*  614: 687 */     if ((!this.ordenFabricacion.isIndicadorDirecto()) && 
/*  615: 688 */       (getOrganizacionConfiguracion().getAtributo1() != null))
/*  616:     */     {
/*  617: 689 */       Atributo atributo = this.servicioAtributo.cargarDetalle(this.organizacionConfiguracion.getAtributo1().getIdAtributo());
/*  618: 690 */       this.ordenFabricacion.setAtributoOrdenFabricacion(atributo);
/*  619:     */     }
/*  620:     */   }
/*  621:     */   
/*  622:     */   public void actualizarAtributos(OrdenFabricacion ordenFabricacion)
/*  623:     */     throws AS2Exception
/*  624:     */   {
/*  625: 697 */     Producto producto = this.servicioProducto.cargarProductoConAtributoInstancia(ordenFabricacion.getProducto().getIdProducto());
/*  626:     */     
/*  627:     */ 
/*  628: 700 */     Atributo atributo = this.ordenFabricacion.getAtributoOrdenFabricacion();
/*  629: 701 */     if (this.ordenFabricacion.getValorAtributoOrdenFabricacion() == null) {
/*  630: 702 */       throw new AS2Exception("com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean.MENSAJE_ERROR_CAMPOS_REQUERIDOS", new String[] { atributo.getNombre() });
/*  631:     */     }
/*  632: 704 */     if (producto.getAtributoProduccion1() == null) {
/*  633: 705 */       throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_PRODUCTO_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { producto.getNombre(), atributo.getNombre() });
/*  634:     */     }
/*  635: 707 */     if ((producto.getAtributoProduccion1() != null) && (atributo.getIdAtributo() != producto.getAtributoProduccion1().getIdAtributo())) {
/*  636: 708 */       throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_PRODUCTO_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { producto.getNombre(), atributo.getNombre() });
/*  637:     */     }
/*  638: 712 */     atributo = producto.getAtributoProduccion1();
/*  639: 713 */     if (producto.getAtributoProduccion1() != null) {
/*  640: 714 */       atributo = this.servicioAtributo.cargarDetalle(this.ordenFabricacion.getAtributoOrdenFabricacion().getIdAtributo());
/*  641:     */     }
/*  642: 716 */     ordenFabricacion.setValorAtributoOrdenFabricacion(this.ordenFabricacion.getValorAtributoOrdenFabricacion());
/*  643: 717 */     ordenFabricacion.setAtributoOrdenFabricacion(atributo);
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/*  647:     */   {
/*  648: 726 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)this.dtSubordenInmediata.getRowData();
/*  649: 727 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  650:     */     try
/*  651:     */     {
/*  652: 730 */       Sucursal sucursal = AppUtil.getSucursal();
/*  653: 731 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  654: 732 */       producto = this.servicioProducto.cargarDetalleOrdenFabricacion(producto.getId());
/*  655: 733 */       ordenFabricacion.setRutaFabricacion(producto.getRutaFabricacion());
/*  656: 736 */       for (BodegaTrabajoProductoSucursal bodega : producto.getListaBodegaTrabajoSucursal()) {
/*  657: 737 */         if (bodega.getSucursal().getId() == sucursal.getId())
/*  658:     */         {
/*  659: 738 */           ordenFabricacion.setBodega(bodega.getBodegaTrabajo());
/*  660: 739 */           break;
/*  661:     */         }
/*  662:     */       }
/*  663: 744 */       for (ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal : producto.getListaProductoRutaFabricacionSucursal()) {
/*  664: 745 */         if (productoRutaFabricacionSucursal.getSucursal().getId() == sucursal.getId())
/*  665:     */         {
/*  666: 746 */           ordenFabricacion.setRutaFabricacion(productoRutaFabricacionSucursal.getRutaFabricacion());
/*  667: 747 */           break;
/*  668:     */         }
/*  669:     */       }
/*  670: 752 */       if (ordenFabricacion.getRutaFabricacion() == null) {
/*  671: 754 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_RUTA_FABRICACION_NULL_PRODUCTO", new String[] {ordenFabricacion.getProducto().getCodigo() + "-" + ordenFabricacion.getProducto().getNombre() });
/*  672:     */       }
/*  673: 758 */       if (ordenFabricacion.getBodega() == null) {
/*  674: 760 */         throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] {ordenFabricacion.getProducto().getCodigo() + "-" + ordenFabricacion.getProducto().getNombre() });
/*  675:     */       }
/*  676: 762 */       ordenFabricacion.setProducto(producto);
/*  677: 763 */       actualizarAtributos(ordenFabricacion);
/*  678:     */     }
/*  679:     */     catch (ExcepcionAS2 e)
/*  680:     */     {
/*  681: 765 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  682: 766 */       ordenFabricacion.getProducto().setCodigo("");
/*  683: 767 */       ordenFabricacion.getProducto().setNombre("");
/*  684:     */     }
/*  685:     */     catch (AS2Exception e)
/*  686:     */     {
/*  687: 769 */       JsfUtil.addErrorMessage(e, "");
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   public void agregarSuborden()
/*  692:     */   {
/*  693: 774 */     OrdenFabricacion suborden = new OrdenFabricacion();
/*  694: 775 */     suborden.setProducto(new Producto());
/*  695: 776 */     suborden.setIdOrganizacion(this.ordenFabricacion.getIdOrganizacion());
/*  696: 777 */     suborden.setSucursal(this.ordenFabricacion.getSucursal());
/*  697: 778 */     suborden.setBodega(this.ordenFabricacion.getBodega());
/*  698: 779 */     suborden.setDocumento(this.ordenFabricacion.getDocumento());
/*  699: 780 */     suborden.setEstado(EstadoProduccionEnum.PLANEADA);
/*  700: 781 */     suborden.setFecha(this.ordenFabricacion.getFecha());
/*  701: 782 */     suborden.setOrdenFabricacionPadre(this.ordenFabricacion != null ? this.ordenFabricacion : null);
/*  702: 783 */     suborden.setOrdenFabricacionPrincipal(this.ordenFabricacion);
/*  703: 784 */     suborden.setPlanProduccion(this.ordenFabricacion.getPlanProduccion());
/*  704: 785 */     suborden.setIndicadorSuborden(true);
/*  705: 786 */     suborden.setIndicadorHoja(true);
/*  706: 787 */     this.ordenFabricacion.getListaSubordenes().add(suborden);
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void eliminarSuborden()
/*  710:     */   {
/*  711: 791 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)this.dtSubordenInmediata.getRowData();
/*  712: 792 */     ordenFabricacion.setEliminado(true);
/*  713: 793 */     totalizarCantidad();
/*  714:     */   }
/*  715:     */   
/*  716:     */   public List<RutaFabricacion> autocompletarRutaFabricacionDetalle(String consulta)
/*  717:     */   {
/*  718: 797 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)this.dtSubordenInmediata.getRowData();
/*  719: 798 */     return this.servicioRutaFabricacion.autocompletarRutaFabricacion(ordenFabricacion.getProducto(), consulta);
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void cargarSubordenes(ToggleEvent event)
/*  723:     */   {
/*  724: 802 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)event.getData();
/*  725: 803 */     HashMap<String, String> filters = new HashMap();
/*  726: 804 */     filters.put("ordenFabricacionPrincipal.idOrdenFabricacion", "" + ordenFabricacion.getIdOrdenFabricacion());
/*  727: 805 */     List<OrdenFabricacion> lista = this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 1000, "numero", true, filters, true);
/*  728: 806 */     ordenFabricacion.setListaSubordenesFabricacion(lista);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public List<ValorAtributo> autocompletarValorAtributo(String consulta)
/*  732:     */   {
/*  733: 810 */     if (getOrdenFabricacion().getAtributoOrdenFabricacion() != null) {
/*  734: 811 */       return this.servicioValorAtributo.autocompletarValorAtributo(consulta, getOrdenFabricacion().getAtributoOrdenFabricacion());
/*  735:     */     }
/*  736: 813 */     List<ValorAtributo> listaVacia = new ArrayList();
/*  737: 814 */     return listaVacia;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void onRowSelectOrdenFabricacion(SelectEvent event)
/*  741:     */   {
/*  742: 819 */     this.ordenFabricacion = ((OrdenFabricacion)event.getObject());
/*  743:     */   }
/*  744:     */   
/*  745:     */   public OrdenFabricacion getOrdenFabricacion()
/*  746:     */   {
/*  747: 828 */     if ((this.ordenFabricacion == null) && (this.listaOrdenFabricacionSeleccionados != null) && (this.listaOrdenFabricacionSeleccionados.size() > 0)) {
/*  748: 829 */       this.ordenFabricacion = ((OrdenFabricacion)this.listaOrdenFabricacionSeleccionados.get(0));
/*  749:     */     }
/*  750: 831 */     return this.ordenFabricacion;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  754:     */   {
/*  755: 841 */     this.ordenFabricacion = ordenFabricacion;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public LazyDataModel<OrdenFabricacion> getListaOrdenFabricacion()
/*  759:     */   {
/*  760: 850 */     return this.listaOrdenFabricacion;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public void setListaOrdenFabricacion(LazyDataModel<OrdenFabricacion> listaOrdenFabricacion)
/*  764:     */   {
/*  765: 860 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public List<Bodega> getListaBodega()
/*  769:     */   {
/*  770: 869 */     if (this.listaBodega == null)
/*  771:     */     {
/*  772: 870 */       Map<String, String> filters = new HashMap();
/*  773: 871 */       agregarFiltroOrganizacion(filters);
/*  774: 872 */       if (ParametrosSistema.getFiltrarSucursalInicioSesion(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  775: 873 */         filters.put("sucursal.idSucursal", "" + AppUtil.getSucursal().getId());
/*  776:     */       }
/*  777: 875 */       filters.put("activo", "true");
/*  778: 876 */       filters.put("claseBodega", ClaseBodegaEnum.TRABAJO.toString());
/*  779: 877 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, filters);
/*  780:     */     }
/*  781: 879 */     return this.listaBodega;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public List<Documento> getListaDocumento()
/*  785:     */   {
/*  786: 889 */     if (this.listaDocumento == null) {
/*  787:     */       try
/*  788:     */       {
/*  789: 891 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.ORDEN_FABRICACION);
/*  790:     */       }
/*  791:     */       catch (ExcepcionAS2 e)
/*  792:     */       {
/*  793: 893 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  794:     */       }
/*  795:     */     }
/*  796: 896 */     return this.listaDocumento;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public ListaProductoBean getListaProductoBean()
/*  800:     */   {
/*  801: 900 */     return this.listaProductoBean;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/*  805:     */   {
/*  806: 904 */     this.listaProductoBean = listaProductoBean;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public DataTable getDtDetalleOrden()
/*  810:     */   {
/*  811: 908 */     return this.dtDetalleOrden;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void setDtDetalleOrden(DataTable dtDetalleOrden)
/*  815:     */   {
/*  816: 912 */     this.dtDetalleOrden = dtDetalleOrden;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public List<DetalleOrdenFabricacion> getListaDetalleOrden()
/*  820:     */   {
/*  821: 916 */     if (this.listaDetalleOrden == null) {
/*  822: 917 */       this.listaDetalleOrden = getOrdenFabricacion().getDetalleOrdenFabricacion();
/*  823:     */     }
/*  824: 919 */     return this.listaDetalleOrden;
/*  825:     */   }
/*  826:     */   
/*  827:     */   public void setListaDetalleOrden(List<DetalleOrdenFabricacion> listaDetalleOrden)
/*  828:     */   {
/*  829: 923 */     this.listaDetalleOrden = listaDetalleOrden;
/*  830:     */   }
/*  831:     */   
/*  832:     */   public List<DetalleOrdenFabricacion> getListaDetalleOrdenFilter()
/*  833:     */   {
/*  834: 927 */     return this.listaDetalleOrdenFilter;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public void setListaDetalleOrdenFilter(List<DetalleOrdenFabricacion> listaDetalleOrdenFilter)
/*  838:     */   {
/*  839: 931 */     this.listaDetalleOrdenFilter = listaDetalleOrdenFilter;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public Date getHoy()
/*  843:     */   {
/*  844: 935 */     return Calendar.getInstance().getTime();
/*  845:     */   }
/*  846:     */   
/*  847:     */   public SelectItem[] getListaEstadoItem()
/*  848:     */   {
/*  849: 940 */     if (this.listaEstadoItem == null)
/*  850:     */     {
/*  851: 941 */       List<SelectItem> lista = new ArrayList();
/*  852: 942 */       lista.add(new SelectItem("", ""));
/*  853: 944 */       for (EstadoProduccionEnum t : EstadoProduccionEnum.values()) {
/*  854: 945 */         lista.add(new SelectItem(t, t.getNombre()));
/*  855:     */       }
/*  856: 947 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/*  857:     */     }
/*  858: 950 */     return this.listaEstadoItem;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public void setListaEstadoItem(SelectItem[] listaEstadoItem)
/*  862:     */   {
/*  863: 954 */     this.listaEstadoItem = listaEstadoItem;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public List<OrdenFabricacion> getListaOrdenFabricacionSeleccionados()
/*  867:     */   {
/*  868: 958 */     return this.listaOrdenFabricacionSeleccionados;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public void setListaOrdenFabricacionSeleccionados(List<OrdenFabricacion> listaOrdenFabricacionSeleccionados)
/*  872:     */   {
/*  873: 962 */     this.listaOrdenFabricacionSeleccionados = listaOrdenFabricacionSeleccionados;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public String cancelar()
/*  877:     */   {
/*  878: 967 */     this.ordenFabricacion = null;
/*  879: 968 */     setEditado(false);
/*  880: 969 */     return "";
/*  881:     */   }
/*  882:     */   
/*  883:     */   public Date getFechaLanzamiento()
/*  884:     */   {
/*  885: 973 */     return this.fechaLanzamiento;
/*  886:     */   }
/*  887:     */   
/*  888:     */   public void setFechaLanzamiento(Date fechaLanzamiento)
/*  889:     */   {
/*  890: 977 */     this.fechaLanzamiento = fechaLanzamiento;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void generarOperaciones()
/*  894:     */   {
/*  895: 981 */     this.listaOrdenFabricacionFinalizar = this.servicioOrdenFabricacion.getListaOrdenFabricacionFinalizar(this.listaOrdenFabricacionSeleccionados, Boolean.valueOf(isCosteoSubOrdenes()), getIndicadorRequiereFormulacion());
/*  896: 982 */     for (OrdenFabricacion orden : this.listaOrdenFabricacionFinalizar) {
/*  897: 983 */       this.servicioOrdenFabricacion.cargarOperaciones(orden, isCosteoSubOrdenes());
/*  898:     */     }
/*  899:     */   }
/*  900:     */   
/*  901:     */   public String getDescripcion()
/*  902:     */   {
/*  903: 988 */     return this.descripcion;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void setDescripcion(String descripcion)
/*  907:     */   {
/*  908: 992 */     this.descripcion = descripcion;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(String consulta)
/*  912:     */   {
/*  913: 996 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/*  914: 997 */     filtros.put("estado", "!=" + Estado.CERRADO);
/*  915: 998 */     filtros.put("OR~numero", "%" + consulta + "%");
/*  916: 999 */     filtros.put("OR~descripcion", "%" + consulta + "%");
/*  917:1000 */     return this.servicioOrdenSalidaMaterial.obtenerListaCombo("numero", false, filtros);
/*  918:     */   }
/*  919:     */   
/*  920:     */   public void actualizarListaOrdenSalida()
/*  921:     */   {
/*  922:1004 */     this.ordenSalidaMaterialAsignarSolicitud = null;
/*  923:1005 */     this.descripcion = "";
/*  924:     */   }
/*  925:     */   
/*  926:     */   public OrdenSalidaMaterial getOrdenSalidaMaterialAsignarSolicitud()
/*  927:     */   {
/*  928:1009 */     return this.ordenSalidaMaterialAsignarSolicitud;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public void setOrdenSalidaMaterialAsignarSolicitud(OrdenSalidaMaterial ordenSalidaMaterialAsignarSolicitud)
/*  932:     */   {
/*  933:1013 */     this.ordenSalidaMaterialAsignarSolicitud = ordenSalidaMaterialAsignarSolicitud;
/*  934:1014 */     if (ordenSalidaMaterialAsignarSolicitud != null)
/*  935:     */     {
/*  936:1015 */       this.descripcion = ordenSalidaMaterialAsignarSolicitud.getDescripcion();
/*  937:1016 */       this.fechaSalidaMaterial = ordenSalidaMaterialAsignarSolicitud.getFechaSalidaMaterial();
/*  938:     */     }
/*  939:     */   }
/*  940:     */   
/*  941:     */   private Set<Estado> getEstadosOrdenSalidaMaterial(OrdenFabricacion orden)
/*  942:     */   {
/*  943:1021 */     Set<Estado> estados = new HashSet();
/*  944:1022 */     for (OrdenFabricacionOrdenSalidaMaterial ofosm : orden.getListaOrdenFabricacionOrdenSalidaMaterial()) {
/*  945:1023 */       estados.add(ofosm.getOrdenSalidaMaterial().getEstado());
/*  946:     */     }
/*  947:1025 */     return estados;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public List<OrdenFabricacion> getListaOrdenFabricacionReabrir()
/*  951:     */   {
/*  952:1029 */     List<OrdenFabricacion> lista = new ArrayList();
/*  953:1030 */     if (this.listaOrdenFabricacionSeleccionados != null) {
/*  954:1031 */       for (OrdenFabricacion orden : this.listaOrdenFabricacionSeleccionados) {
/*  955:1032 */         if ((orden.getEstado().equals(EstadoProduccionEnum.FINALIZADA)) && ((orden.getListaOrdenFabricacionOrdenSalidaMaterial().isEmpty()) || 
/*  956:1033 */           (!getEstadosOrdenSalidaMaterial(orden).contains(Estado.CERRADO)))) {
/*  957:1034 */           lista.add(orden);
/*  958:     */         }
/*  959:     */       }
/*  960:     */     }
/*  961:1038 */     return lista;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void reabrirOrdenFabricacion()
/*  965:     */   {
/*  966:1042 */     for (OrdenFabricacion orden : getListaOrdenFabricacionReabrir()) {
/*  967:     */       try
/*  968:     */       {
/*  969:1044 */         List<String> listaCampos = new ArrayList();
/*  970:1045 */         listaCampos.add("documento");
/*  971:1046 */         orden = (OrdenFabricacion)this.servicioOrdenFabricacionGenerico.cargarDetalle(OrdenFabricacion.class, orden.getId(), listaCampos);
/*  972:1047 */         this.servicioOrdenFabricacion.reabrirOrden(orden);
/*  973:     */       }
/*  974:     */       catch (ExcepcionAS2Financiero e)
/*  975:     */       {
/*  976:1049 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  977:     */       }
/*  978:     */       catch (AS2Exception e)
/*  979:     */       {
/*  980:1051 */         JsfUtil.addErrorMessage(e, "");
/*  981:     */       }
/*  982:     */     }
/*  983:     */   }
/*  984:     */   
/*  985:     */   public Boolean getIndicadorRequiereFormulacion()
/*  986:     */   {
/*  987:1057 */     if (this.indicadorRequiereFormulacion == null) {
/*  988:1058 */       this.indicadorRequiereFormulacion = ParametrosSistema.getOrdenFabricacionFormulacion(AppUtil.getOrganizacion().getId());
/*  989:     */     }
/*  990:1060 */     return this.indicadorRequiereFormulacion;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public List<OrdenFabricacion> getListaSubordenesInmediatas()
/*  994:     */   {
/*  995:1064 */     List<OrdenFabricacion> listaSubordenes = new ArrayList();
/*  996:1065 */     for (OrdenFabricacion suborden : getOrdenFabricacion().getListaSubordenes()) {
/*  997:1066 */       if (!suborden.isEliminado()) {
/*  998:1067 */         listaSubordenes.add(suborden);
/*  999:     */       }
/* 1000:     */     }
/* 1001:1070 */     return listaSubordenes;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public List<OrdenFabricacion> getListaSubordenesOtras()
/* 1005:     */   {
/* 1006:1074 */     List<OrdenFabricacion> listaSubordenes = new ArrayList();
/* 1007:1075 */     for (OrdenFabricacion subordenInmediata : getListaSubordenesInmediatas()) {
/* 1008:1076 */       for (OrdenFabricacion suborden : subordenInmediata.getListaSubordenes()) {
/* 1009:1077 */         listaSubordenes.addAll(obtenerListaSubordenesRecursivo(suborden));
/* 1010:     */       }
/* 1011:     */     }
/* 1012:1080 */     return listaSubordenes;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   private List<OrdenFabricacion> obtenerListaSubordenesRecursivo(OrdenFabricacion suborden)
/* 1016:     */   {
/* 1017:1084 */     List<OrdenFabricacion> listaSubordenes = new ArrayList();
/* 1018:1085 */     if (!suborden.isEliminado())
/* 1019:     */     {
/* 1020:1086 */       listaSubordenes.add(suborden);
/* 1021:1087 */       for (OrdenFabricacion sub : suborden.getListaSubordenes()) {
/* 1022:1088 */         listaSubordenes.addAll(obtenerListaSubordenesRecursivo(sub));
/* 1023:     */       }
/* 1024:     */     }
/* 1025:1092 */     return listaSubordenes;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public DataTable getDtSubordenInmediata()
/* 1029:     */   {
/* 1030:1096 */     return this.dtSubordenInmediata;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public void setDtSubordenInmediata(DataTable dtSubordenInmediata)
/* 1034:     */   {
/* 1035:1100 */     this.dtSubordenInmediata = dtSubordenInmediata;
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public DataTable getDtSubordenOtras()
/* 1039:     */   {
/* 1040:1104 */     return this.dtSubordenOtras;
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public void setDtSubordenOtras(DataTable dtSubordenOtras)
/* 1044:     */   {
/* 1045:1108 */     this.dtSubordenOtras = dtSubordenOtras;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public void actualizarBatchDetalle(OrdenFabricacion suborden)
/* 1049:     */   {
/* 1050:1112 */     BigDecimal cantidadDetalle = suborden.getCantidadBatch().multiply(suborden.getProducto().getCantidadProduccion()).setScale(2, RoundingMode.HALF_UP);
/* 1051:     */     
/* 1052:1114 */     suborden.setCantidad(cantidadDetalle);
/* 1053:1115 */     totalizarCantidad();
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   private void totalizarCantidad()
/* 1057:     */   {
/* 1058:1120 */     if (!this.ordenFabricacion.isIndicadorDirecto())
/* 1059:     */     {
/* 1060:1121 */       BigDecimal cantidad = BigDecimal.ZERO;
/* 1061:1122 */       for (OrdenFabricacion ordenFabricacion : getListaSubordenesInmediatas()) {
/* 1062:1123 */         cantidad = cantidad.add(ordenFabricacion.getCantidad());
/* 1063:     */       }
/* 1064:1125 */       this.ordenFabricacion.setCantidad(cantidad);
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public void actualizarDatosSubordenes()
/* 1069:     */   {
/* 1070:     */     try
/* 1071:     */     {
/* 1072:1131 */       this.servicioOrdenFabricacion.actualizarDatosSubordenes(getOrdenFabricacion());
/* 1073:     */     }
/* 1074:     */     catch (AS2Exception e)
/* 1075:     */     {
/* 1076:1133 */       JsfUtil.addErrorMessage(e, "");
/* 1077:     */     }
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public void actualizarCantidad()
/* 1081:     */   {
/* 1082:1138 */     if (this.ordenFabricacion.isIndicadorDirecto()) {
/* 1083:     */       try
/* 1084:     */       {
/* 1085:1140 */         this.servicioOrdenFabricacion.cargarSubordenes(getOrdenFabricacion());
/* 1086:1141 */         setearDTDetalleOrden();
/* 1087:     */       }
/* 1088:     */       catch (AS2Exception e)
/* 1089:     */       {
/* 1090:1143 */         JsfUtil.addErrorMessage(e, "");
/* 1091:     */       }
/* 1092:     */     }
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public Integer getIdOrdenFabricacion()
/* 1096:     */   {
/* 1097:1149 */     return this.idOrdenFabricacion;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void setIdOrdenFabricacion(Integer idOrdenFabricacion)
/* 1101:     */   {
/* 1102:1153 */     this.idOrdenFabricacion = idOrdenFabricacion;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public List<TipoCicloProduccionEnum> getListaTipoCicloEnum()
/* 1106:     */   {
/* 1107:1157 */     if (this.listaTipoCicloEnum == null)
/* 1108:     */     {
/* 1109:1158 */       this.listaTipoCicloEnum = new ArrayList();
/* 1110:1159 */       for (TipoCicloProduccionEnum tipoCiclo : TipoCicloProduccionEnum.values()) {
/* 1111:1160 */         this.listaTipoCicloEnum.add(tipoCiclo);
/* 1112:     */       }
/* 1113:     */     }
/* 1114:1163 */     return this.listaTipoCicloEnum;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public SelectItem[] getListaTipoCicloProduccionItem()
/* 1118:     */   {
/* 1119:1172 */     if (this.listaTipoCicloProduccionItem == null)
/* 1120:     */     {
/* 1121:1174 */       List<SelectItem> lista = new ArrayList();
/* 1122:1175 */       lista.add(new SelectItem("", ""));
/* 1123:1177 */       for (TipoCicloProduccionEnum t : TipoCicloProduccionEnum.values())
/* 1124:     */       {
/* 1125:1178 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 1126:1179 */         lista.add(item);
/* 1127:     */       }
/* 1128:1181 */       this.listaTipoCicloProduccionItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 1129:     */     }
/* 1130:1184 */     Arrays.sort(this.listaTipoCicloProduccionItem, new SelectItemComparator());
/* 1131:     */     
/* 1132:1186 */     return this.listaTipoCicloProduccionItem;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public Date getFechaSalidaMaterial()
/* 1136:     */   {
/* 1137:1190 */     return this.fechaSalidaMaterial;
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public void setFechaSalidaMaterial(Date fechaSalidaMaterial)
/* 1141:     */   {
/* 1142:1194 */     this.fechaSalidaMaterial = fechaSalidaMaterial;
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   public Date getFechaCierreOrden()
/* 1146:     */   {
/* 1147:1198 */     return this.fechaCierreOrden;
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   public void setFechaCierreOrden(Date fechaCierreOrden)
/* 1151:     */   {
/* 1152:1202 */     this.fechaCierreOrden = fechaCierreOrden;
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public void cargarListaMaquinaSeleccionados()
/* 1156:     */   {
/* 1157:1206 */     List<Integer> lista = new ArrayList();
/* 1158:1207 */     for (OrdenFabricacionMaquina ordenFabricacionMaquina : this.ordenFabricacion.getListaOrdenFabricacionMaquina()) {
/* 1159:1208 */       lista.add(Integer.valueOf(ordenFabricacionMaquina.getMaquina().getIdMaquina()));
/* 1160:     */     }
/* 1161:1210 */     this.listaMaquinaSeleccionados = ((Integer[])lista.toArray(new Integer[lista.size()]));
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public void crearOrdenFabricacionMaquina()
/* 1165:     */   {
/* 1166:1215 */     HashMap<Integer, OrdenFabricacionMaquina> hmOrdenFabricacionMaquina = new HashMap();
/* 1167:1216 */     for (Iterator localIterator = this.ordenFabricacion.getListaOrdenFabricacionMaquina().iterator(); localIterator.hasNext();)
/* 1168:     */     {
/* 1169:1216 */       ordenFabricacionMaquina = (OrdenFabricacionMaquina)localIterator.next();
/* 1170:1217 */       hmOrdenFabricacionMaquina.put(Integer.valueOf(ordenFabricacionMaquina.getMaquina().getId()), ordenFabricacionMaquina);
/* 1171:     */     }
/* 1172:     */     OrdenFabricacionMaquina ordenFabricacionMaquina;
/* 1173:1221 */     Object idsMaquina = new HashSet();
/* 1174:1222 */     for (Integer idMaquina : this.listaMaquinaSeleccionados)
/* 1175:     */     {
/* 1176:1223 */       ((Set)idsMaquina).add(idMaquina);
/* 1177:1224 */       OrdenFabricacionMaquina ordenFabricacionMaquina = (OrdenFabricacionMaquina)hmOrdenFabricacionMaquina.get(idMaquina);
/* 1178:1225 */       if (ordenFabricacionMaquina == null)
/* 1179:     */       {
/* 1180:1226 */         ordenFabricacionMaquina = new OrdenFabricacionMaquina();
/* 1181:1227 */         ordenFabricacionMaquina.setMaquina(this.servicioMaquina.buscarPorId(idMaquina.intValue()));
/* 1182:1228 */         ordenFabricacionMaquina.setOrdenFabricacion(this.ordenFabricacion);
/* 1183:1229 */         this.ordenFabricacion.getListaOrdenFabricacionMaquina().add(ordenFabricacionMaquina);
/* 1184:     */       }
/* 1185:     */     }
/* 1186:1234 */     for (OrdenFabricacionMaquina ordenFabricacionMaquina : this.ordenFabricacion.getListaOrdenFabricacionMaquina()) {
/* 1187:1235 */       if (!((Set)idsMaquina).contains(Integer.valueOf(ordenFabricacionMaquina.getMaquina().getIdMaquina()))) {
/* 1188:1236 */         ordenFabricacionMaquina.setEliminado(true);
/* 1189:     */       }
/* 1190:     */     }
/* 1191:     */   }
/* 1192:     */   
/* 1193:     */   public Integer[] getListaMaquinaSeleccionados()
/* 1194:     */   {
/* 1195:1243 */     return this.listaMaquinaSeleccionados;
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public void setListaMaquinaSeleccionados(Integer[] listaMaquinaSeleccionados)
/* 1199:     */   {
/* 1200:1247 */     this.listaMaquinaSeleccionados = listaMaquinaSeleccionados;
/* 1201:     */   }
/* 1202:     */   
/* 1203:     */   public List<Maquina> getListaMaquina()
/* 1204:     */   {
/* 1205:1251 */     if (this.listaMaquina == null) {
/* 1206:1252 */       this.listaMaquina = this.servicioMaquina.obtenerListaCombo("nombre", true, null);
/* 1207:     */     }
/* 1208:1254 */     return this.listaMaquina;
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   public void setListaMaquina(List<Maquina> listaMaquina)
/* 1212:     */   {
/* 1213:1258 */     this.listaMaquina = listaMaquina;
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public List<PersonaResponsable> getListaPersonaResponsable()
/* 1217:     */   {
/* 1218:1262 */     if (this.listaPersonaResponsable == null)
/* 1219:     */     {
/* 1220:1263 */       HashMap<String, String> filters = new HashMap();
/* 1221:1264 */       filters.put("indicadorOrdenFabricacion", "true");
/* 1222:1265 */       this.listaPersonaResponsable = this.servicioPersonaResponsable.obtenerListaCombo("nombres", true, filters);
/* 1223:     */     }
/* 1224:1267 */     return this.listaPersonaResponsable;
/* 1225:     */   }
/* 1226:     */   
/* 1227:     */   public void setListaPersonaResponsable(List<PersonaResponsable> listaPersonaResponsable)
/* 1228:     */   {
/* 1229:1271 */     this.listaPersonaResponsable = listaPersonaResponsable;
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public EnumProductoEditado getEnumProductoEditado()
/* 1233:     */   {
/* 1234:1275 */     return this.enumProductoEditado;
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   public void setEnumProductoEditado(EnumProductoEditado enumProductoEditado)
/* 1238:     */   {
/* 1239:1279 */     this.enumProductoEditado = enumProductoEditado;
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 1243:     */   {
/* 1244:1283 */     if (this.organizacionConfiguracion == null) {
/* 1245:1284 */       this.organizacionConfiguracion = this.servicioOrganizacion.cargarDetalle(AppUtil.getOrganizacion().getId()).getOrganizacionConfiguracion();
/* 1246:     */     }
/* 1247:1286 */     return this.organizacionConfiguracion;
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 1251:     */   {
/* 1252:1290 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public int getNumeroAtributos()
/* 1256:     */   {
/* 1257:1294 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public boolean isCosteoSubOrdenes()
/* 1261:     */   {
/* 1262:1298 */     return ParametrosSistema.isCosteoSubOrdenes(AppUtil.getOrganizacion().getId()).booleanValue();
/* 1263:     */   }
/* 1264:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.OrdenFabricacionBean
 * JD-Core Version:    0.7.0.1
 */