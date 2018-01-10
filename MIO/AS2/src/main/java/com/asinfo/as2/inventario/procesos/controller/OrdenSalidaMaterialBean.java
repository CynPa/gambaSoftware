/*    1:     */ package com.asinfo.as2.inventario.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    5:     */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    7:     */ import com.asinfo.as2.entities.Bodega;
/*    8:     */ import com.asinfo.as2.entities.DestinoCosto;
/*    9:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   10:     */ import com.asinfo.as2.entities.Dispositivo;
/*   11:     */ import com.asinfo.as2.entities.Documento;
/*   12:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   13:     */ import com.asinfo.as2.entities.Lote;
/*   14:     */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   15:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   16:     */ import com.asinfo.as2.entities.Organizacion;
/*   17:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   18:     */ import com.asinfo.as2.entities.Producto;
/*   19:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   20:     */ import com.asinfo.as2.entities.Sucursal;
/*   21:     */ import com.asinfo.as2.entities.UnidadManejo;
/*   22:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   23:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   24:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   25:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   26:     */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*   27:     */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   28:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   29:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   30:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   31:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   32:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   33:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   34:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   35:     */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*   36:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   37:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*   38:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   39:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   40:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   41:     */ import com.asinfo.as2.util.AppUtil;
/*   42:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   43:     */ import com.asinfo.as2.utils.JsfUtil;
/*   44:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   45:     */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*   46:     */ import java.io.BufferedInputStream;
/*   47:     */ import java.io.InputStream;
/*   48:     */ import java.math.BigDecimal;
/*   49:     */ import java.text.SimpleDateFormat;
/*   50:     */ import java.util.ArrayList;
/*   51:     */ import java.util.Arrays;
/*   52:     */ import java.util.Collection;
/*   53:     */ import java.util.Date;
/*   54:     */ import java.util.HashMap;
/*   55:     */ import java.util.HashSet;
/*   56:     */ import java.util.List;
/*   57:     */ import java.util.Map;
/*   58:     */ import java.util.Set;
/*   59:     */ import javax.annotation.PostConstruct;
/*   60:     */ import javax.ejb.EJB;
/*   61:     */ import javax.faces.bean.ManagedBean;
/*   62:     */ import javax.faces.bean.ViewScoped;
/*   63:     */ import javax.faces.component.html.HtmlInputText;
/*   64:     */ import javax.faces.context.FacesContext;
/*   65:     */ import javax.faces.context.PartialViewContext;
/*   66:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   67:     */ import javax.faces.model.SelectItem;
/*   68:     */ import org.apache.log4j.Logger;
/*   69:     */ import org.primefaces.component.datatable.DataTable;
/*   70:     */ import org.primefaces.context.RequestContext;
/*   71:     */ import org.primefaces.event.CloseEvent;
/*   72:     */ import org.primefaces.event.FileUploadEvent;
/*   73:     */ import org.primefaces.event.RowEditEvent;
/*   74:     */ import org.primefaces.event.SelectEvent;
/*   75:     */ import org.primefaces.model.LazyDataModel;
/*   76:     */ import org.primefaces.model.SortOrder;
/*   77:     */ import org.primefaces.model.UploadedFile;
/*   78:     */ 
/*   79:     */ @ManagedBean
/*   80:     */ @ViewScoped
/*   81:     */ public class OrdenSalidaMaterialBean
/*   82:     */   extends PageControllerAS2
/*   83:     */ {
/*   84:     */   private static final long serialVersionUID = 1424377436906270962L;
/*   85:     */   @EJB
/*   86:     */   protected ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*   87:     */   @EJB
/*   88:     */   private ServicioDocumento servicioDocumento;
/*   89:     */   @EJB
/*   90:     */   private ServicioProducto servicioProducto;
/*   91:     */   @EJB
/*   92:     */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*   93:     */   @EJB
/*   94:     */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*   95:     */   @EJB
/*   96:     */   private ServicioGenerico<LecturaBalanza> servicioLecturaBalanza;
/*   97:     */   @EJB
/*   98:     */   private ServicioGenerico<DetalleOrdenSalidaMaterial> servicioDetalleOrdenSalidaMaterial;
/*   99:     */   @EJB
/*  100:     */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  101:     */   @EJB
/*  102:     */   private ServicioDestinoCosto servicioDestinoCosto;
/*  103:     */   @EJB
/*  104:     */   private ServicioLote servicioLote;
/*  105:     */   @EJB
/*  106:     */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  107:     */   protected List<Bodega> listaBodega;
/*  108:     */   protected List<LecturaBalanza> listaLecturaBalanza;
/*  109:     */   private List<Dispositivo> listaDispositivo;
/*  110:     */   protected LecturaBalanza lecturaBalanza;
/*  111:     */   protected List<UnidadManejo> listaUnidadManejo;
/*  112:     */   protected List<UnidadManejo> listaPallet;
/*  113:     */   protected Boolean mostrarBalanza;
/*  114:     */   protected DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterialSeleccionado;
/*  115:     */   protected boolean ingresoDevolucion;
/*  116:     */   protected Integer idOrdenSalidaMaterial;
/*  117:     */   protected List<OrdenFabricacion> listaOrdenFabricacionCicloLargoNoAsignadas;
/*  118:     */   protected List<OrdenFabricacion> listaOrdenFabricacionCicloLargoNoAsignadasFilters;
/*  119:     */   protected SelectItem[] listaEstadoItem;
/*  120:     */   protected SelectItem[] listaTipoCicloProduccionItem;
/*  121:     */   protected DataTable dtLecturaBalanza;
/*  122:     */   protected DataTable dtOrdenFabricacion;
/*  123:     */   protected DataTable dtOrdenFabricacionNoAsignadas;
/*  124:     */   protected DataTable dtOrdenFabricacionCicloCortoNoAsignadas;
/*  125:     */   protected OrdenSalidaMaterial ordenSalidaMaterial;
/*  126:     */   protected List<DetalleOrdenSalidaMaterial> listaDetalleOrden;
/*  127:     */   private List<DetalleOrdenSalidaMaterial> listaDetalleOrdenFilteredValue;
/*  128:     */   private List<OrdenFabricacion> listaOrdenFabricacionCicloCortoNoAsignadas;
/*  129:     */   protected List<OrdenFabricacion> listaOrdenFabricacionCicloCortoNoAsignadasFilters;
/*  130:     */   protected LazyDataModel<OrdenSalidaMaterial> listaOrdenSalidaMaterial;
/*  131:     */   protected List<Documento> listaDocumento;
/*  132:     */   @EJB
/*  133:     */   private ServicioMigracion servicioMigracion;
/*  134:     */   private Boolean indicadorManejaCiclosLargos;
/*  135:     */   private Boolean aprobarOrdenSalidaMaterial;
/*  136:     */   private List<TipoCicloProduccionEnum> listaTipoCicloEnum;
/*  137:     */   private DetalleOrdenSalidaMaterial detalleOSMActualizar;
/*  138:     */   private List<OrdenFabricacion> listaOFSeleccionados;
/*  139:     */   private String movimiento;
/*  140:     */   private String codigoCabecera;
/*  141:     */   private DataTable dtDetalleOrden;
/*  142:     */   private List<String> listaMensajes;
/*  143:     */   private int numeroOSMCerrar;
/*  144:     */   
/*  145:     */   @PostConstruct
/*  146:     */   public void init()
/*  147:     */   {
/*  148: 188 */     this.listaOrdenSalidaMaterial = new LazyDataModel()
/*  149:     */     {
/*  150:     */       private static final long serialVersionUID = 1L;
/*  151:     */       
/*  152:     */       public List<OrdenSalidaMaterial> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  153:     */       {
/*  154: 194 */         if (OrdenSalidaMaterialBean.this.idOrdenSalidaMaterial != null)
/*  155:     */         {
/*  156: 195 */           filters.put("idOrdenSalidaMaterial", OrdenSalidaMaterialBean.this.idOrdenSalidaMaterial.toString());
/*  157: 196 */           OrdenSalidaMaterialBean.this.idOrdenSalidaMaterial = null;
/*  158:     */         }
/*  159: 199 */         if (filters.size() == 0) {
/*  160: 202 */           filters.put("estado", "!=" + Estado.CERRADO.toString());
/*  161:     */         }
/*  162: 204 */         if (OrdenSalidaMaterialBean.this.ingresoDevolucion) {
/*  163: 205 */           filters.put("indicadorTransferencia", "false");
/*  164:     */         }
/*  165: 207 */         filters = OrdenSalidaMaterialBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  166:     */         
/*  167: 209 */         List<OrdenSalidaMaterial> lista = new ArrayList();
/*  168: 210 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  169:     */         
/*  170: 212 */         lista = OrdenSalidaMaterialBean.this.servicioOrdenSalidaMaterial.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  171:     */         
/*  172: 214 */         OrdenSalidaMaterialBean.this.listaOrdenSalidaMaterial.setRowCount(OrdenSalidaMaterialBean.this.servicioOrdenSalidaMaterial.contarPorCriterio(filters));
/*  173:     */         
/*  174: 216 */         return lista;
/*  175:     */       }
/*  176:     */     };
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void actualizarProducto(AjaxBehaviorEvent event)
/*  180:     */   {
/*  181: 232 */     DetalleOrdenSalidaMaterial detalleOrden = null;
/*  182: 233 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  183:     */     try
/*  184:     */     {
/*  185: 235 */       detalleOrden = (DetalleOrdenSalidaMaterial)this.dtDetalleOrden.getRowData();
/*  186: 236 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  187: 237 */       detalleOrden.setProducto(producto);
/*  188: 238 */       detalleOrden.setUnidad(producto.getUnidad());
/*  189: 239 */       actulizarBodega(detalleOrden);
/*  190:     */     }
/*  191:     */     catch (ExcepcionAS2 e)
/*  192:     */     {
/*  193: 241 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  194: 242 */       detalleOrden.getProducto().setCodigo("");
/*  195: 243 */       detalleOrden.getProducto().setNombre("");
/*  196:     */     }
/*  197:     */     catch (Exception ex)
/*  198:     */     {
/*  199: 245 */       LOG.error("Error al cargar el producto por codigo: " + ex);
/*  200: 246 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/*  201: 247 */       detalleOrden.getProducto().setCodigo("");
/*  202: 248 */       detalleOrden.getProducto().setNombre("");
/*  203:     */     }
/*  204:     */   }
/*  205:     */   
/*  206:     */   public String editar(String movimiento)
/*  207:     */   {
/*  208: 259 */     if ((getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().getIdOrdenSalidaMaterial() != 0))
/*  209:     */     {
/*  210: 261 */       OrdenSalidaMaterial osm = this.servicioOrdenSalidaMaterial.buscarPorId(getOrdenSalidaMaterial().getId());
/*  211: 262 */       if ((osm.getEstado().equals(Estado.ANULADO)) || (osm.getEstado().equals(Estado.PROCESADO)) || (osm.getEstado().equals(Estado.CERRADO)) || (
/*  212: 263 */         (getAprobarOrdenSalidaMaterial().booleanValue()) && (osm.isAprobado())))
/*  213:     */       {
/*  214: 264 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  215:     */       }
/*  216:     */       else
/*  217:     */       {
/*  218: 266 */         this.ordenSalidaMaterial = this.servicioOrdenSalidaMaterial.cargarDetalle(this.ordenSalidaMaterial.getIdOrdenSalidaMaterial());
/*  219: 267 */         setMovimiento(movimiento);
/*  220: 268 */         setEditado(true);
/*  221:     */       }
/*  222: 271 */       this.dtDetalleOrden.reset();
/*  223: 272 */       this.listaDetalleOrden = null;
/*  224: 273 */       this.listaDetalleOrdenFilteredValue = null;
/*  225:     */     }
/*  226:     */     else
/*  227:     */     {
/*  228: 275 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  229:     */     }
/*  230: 278 */     return "";
/*  231:     */   }
/*  232:     */   
/*  233:     */   public String editar()
/*  234:     */   {
/*  235: 283 */     return editar("DESPACHO");
/*  236:     */   }
/*  237:     */   
/*  238:     */   public String guardar()
/*  239:     */   {
/*  240: 288 */     return guardar(true, false);
/*  241:     */   }
/*  242:     */   
/*  243:     */   public String guardar(boolean guardarYSalir, boolean validarDetalleOSM)
/*  244:     */   {
/*  245:     */     try
/*  246:     */     {
/*  247: 299 */       eliminarDetalleOrdenConCero();
/*  248: 300 */       validarCantidaddesechoInformativa(this.ordenSalidaMaterial);
/*  249: 302 */       if ((guardarYSalir) && (getIndicadorManejaCiclosLargos().booleanValue()) && 
/*  250: 303 */         (this.ordenSalidaMaterial.getTipoCicloProduccionEnum().equals(TipoCicloProduccionEnum.CICLO_CORTO)) && (!validarDetalleOSM) && 
/*  251: 304 */         (this.movimiento.equals("DESPACHO"))) {
/*  252: 305 */         validarDetallesOSMSinOF(this.ordenSalidaMaterial);
/*  253:     */       }
/*  254: 307 */       this.servicioOrdenSalidaMaterial.guardar(this.ordenSalidaMaterial);
/*  255: 309 */       if (guardarYSalir)
/*  256:     */       {
/*  257: 310 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  258:     */         
/*  259: 312 */         limpiar();
/*  260: 313 */         setEditado(false);
/*  261:     */       }
/*  262:     */     }
/*  263:     */     catch (AS2Exception e)
/*  264:     */     {
/*  265: 316 */       JsfUtil.addErrorMessage(e, "");
/*  266: 317 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  267:     */     }
/*  268:     */     catch (ExcepcionAS2Financiero e)
/*  269:     */     {
/*  270: 319 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  271: 320 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  272:     */     }
/*  273:     */     catch (ExcepcionAS2 e)
/*  274:     */     {
/*  275: 322 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  276: 323 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  277:     */     }
/*  278:     */     catch (Exception e)
/*  279:     */     {
/*  280: 325 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  281: 326 */       if (!guardarYSalir)
/*  282:     */       {
/*  283: 327 */         resetearTablas();
/*  284: 328 */         editar(this.movimiento);
/*  285:     */       }
/*  286: 330 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  287:     */     }
/*  288: 332 */     return "";
/*  289:     */   }
/*  290:     */   
/*  291:     */   public String eliminar()
/*  292:     */   {
/*  293:     */     try
/*  294:     */     {
/*  295: 342 */       this.servicioOrdenSalidaMaterial.eliminar(this.ordenSalidaMaterial);
/*  296: 343 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  297:     */     }
/*  298:     */     catch (Exception e)
/*  299:     */     {
/*  300: 345 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  301: 346 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  302:     */     }
/*  303: 348 */     return "";
/*  304:     */   }
/*  305:     */   
/*  306:     */   public String cargarDatos()
/*  307:     */   {
/*  308: 357 */     return "";
/*  309:     */   }
/*  310:     */   
/*  311:     */   public String limpiar()
/*  312:     */   {
/*  313: 366 */     crearEntidad();
/*  314: 367 */     return "";
/*  315:     */   }
/*  316:     */   
/*  317:     */   private void crearEntidad()
/*  318:     */   {
/*  319: 374 */     this.ordenSalidaMaterial = new OrdenSalidaMaterial();
/*  320: 375 */     this.ordenSalidaMaterial.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  321: 376 */     this.ordenSalidaMaterial.setSucursal(AppUtil.getSucursal());
/*  322: 377 */     this.ordenSalidaMaterial.setEstado(Estado.ELABORADO);
/*  323: 378 */     this.ordenSalidaMaterial.setFecha(new Date());
/*  324: 380 */     if (!getListaDocumento().isEmpty()) {
/*  325: 381 */       this.ordenSalidaMaterial.setDocumento((Documento)getListaDocumento().get(0));
/*  326:     */     }
/*  327: 384 */     resetearTablas();
/*  328:     */     
/*  329: 386 */     this.movimiento = "DESPACHO";
/*  330: 387 */     this.codigoCabecera = "";
/*  331:     */   }
/*  332:     */   
/*  333:     */   private void resetearTablas()
/*  334:     */   {
/*  335: 391 */     this.listaLecturaBalanza = null;
/*  336: 392 */     this.listaDetalleOrden = null;
/*  337: 393 */     this.listaUnidadManejo = null;
/*  338:     */     
/*  339: 395 */     Dispositivo dispositivo = null;
/*  340: 396 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getDispositivo() != null)) {
/*  341: 397 */       dispositivo = this.lecturaBalanza.getDispositivo();
/*  342:     */     }
/*  343: 399 */     this.lecturaBalanza = null;
/*  344: 400 */     getLecturaBalanza().setDispositivo(dispositivo);
/*  345: 401 */     this.detalleOrdenSalidaMaterialSeleccionado = null;
/*  346: 402 */     if (this.dtDetalleOrden != null) {
/*  347: 403 */       this.dtDetalleOrden.reset();
/*  348:     */     }
/*  349: 405 */     if (this.dtLecturaBalanza != null) {
/*  350: 406 */       this.dtLecturaBalanza.reset();
/*  351:     */     }
/*  352: 408 */     RequestContext.getCurrentInstance().update("panelLecturaPeso");
/*  353: 409 */     RequestContext.getCurrentInstance().update("tvDetalles:panelDetalleProductos");
/*  354: 410 */     RequestContext.getCurrentInstance().update("tvDetalles:panelDetallePesadas");
/*  355:     */   }
/*  356:     */   
/*  357:     */   public void eliminarDetalle(DetalleOrdenSalidaMaterial detalleOrden)
/*  358:     */   {
/*  359: 421 */     DetalleOrdenSalidaMaterial dosm = (DetalleOrdenSalidaMaterial)this.servicioDetalleOrdenSalidaMaterial.buscarPorId(DetalleOrdenSalidaMaterial.class, Integer.valueOf(detalleOrden.getId()));
/*  360: 423 */     if ((dosm == null) || (
/*  361: 424 */       (dosm.getCantidadDespachada().compareTo(BigDecimal.ZERO) == 0) && (dosm.getCantidadDevuelta().compareTo(BigDecimal.ZERO) == 0)))
/*  362:     */     {
/*  363: 425 */       this.listaDetalleOrden = null;
/*  364: 426 */       if (this.dtDetalleOrden != null) {
/*  365: 427 */         this.dtDetalleOrden.reset();
/*  366:     */       }
/*  367: 429 */       if ((isMostrarBalanza()) && (dosm != null)) {
/*  368:     */         try
/*  369:     */         {
/*  370: 431 */           this.servicioOrdenSalidaMaterial.crearMovimientoInventario(this.ordenSalidaMaterial, dosm.getProducto());
/*  371:     */         }
/*  372:     */         catch (AS2Exception e)
/*  373:     */         {
/*  374: 433 */           JsfUtil.addErrorMessage(e, "");
/*  375: 434 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/*  376:     */         }
/*  377:     */         catch (ExcepcionAS2 e)
/*  378:     */         {
/*  379: 436 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  380: 437 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/*  381:     */         }
/*  382:     */       }
/*  383: 440 */       detalleOrden.setEliminado(true);
/*  384:     */     }
/*  385: 441 */     else if ((detalleOrden.getCantidadDespachada().compareTo(BigDecimal.ZERO) > 0) && 
/*  386: 442 */       (detalleOrden.getCantidadDevuelta().compareTo(BigDecimal.ZERO) == 0) && (detalleOrden.getListaLecturaBalanzaView().isEmpty()))
/*  387:     */     {
/*  388: 443 */       this.servicioOrdenSalidaMaterial.eliminarDetalleOrdenSalidaMaterial(detalleOrden);
/*  389: 444 */       this.ordenSalidaMaterial.setListaDetalleOrdenSalidaMaterial(this.ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterialNoEliminados());
/*  390: 445 */       resetearTablas();
/*  391:     */     }
/*  392:     */     else
/*  393:     */     {
/*  394: 447 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  395:     */     }
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void crearDetalleOrdenSalida()
/*  399:     */   {
/*  400: 452 */     crearDetalleOrdenSalida(true, null);
/*  401:     */   }
/*  402:     */   
/*  403:     */   public DetalleOrdenSalidaMaterial crearDetalleOrdenSalida(boolean agregarDetalle, DetalleOrdenSalidaMaterial detalle)
/*  404:     */   {
/*  405: 472 */     return this.servicioOrdenSalidaMaterial.crearDetalleOrdenSalida(this.ordenSalidaMaterial, agregarDetalle, getListaDetalleOrden(), detalle);
/*  406:     */   }
/*  407:     */   
/*  408:     */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/*  409:     */   {
/*  410: 476 */     cargarProducto(saldoLote.getProducto(), saldoLote.getLote());
/*  411:     */   }
/*  412:     */   
/*  413:     */   public void cargarProducto(Producto producto)
/*  414:     */   {
/*  415: 480 */     cargarProducto(producto, null);
/*  416:     */   }
/*  417:     */   
/*  418:     */   public void cargarProducto(Producto producto, Lote lote)
/*  419:     */   {
/*  420: 484 */     DetalleOrdenSalidaMaterial detalleOrden = crearDetalleOrdenSalida(true, null);
/*  421: 485 */     detalleOrden.setProducto(producto);
/*  422: 486 */     detalleOrden.setUnidad(detalleOrden.getProducto().getUnidad());
/*  423: 487 */     detalleOrden.setCantidad(detalleOrden.getProducto().getTraCantidad());
/*  424: 488 */     detalleOrden.setLote(lote);
/*  425: 489 */     actulizarBodega(detalleOrden);
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void actulizarBodega(DetalleOrdenSalidaMaterial detalleOrden)
/*  429:     */   {
/*  430: 493 */     this.servicioOrdenSalidaMaterial.actulizarBodega(detalleOrden, AppUtil.getBodega(), this.ordenSalidaMaterial.getSucursal().getId());
/*  431:     */   }
/*  432:     */   
/*  433:     */   public List<Lote> autocompletarLotes(String consulta)
/*  434:     */   {
/*  435: 505 */     DetalleOrdenSalidaMaterial detalle = (DetalleOrdenSalidaMaterial)this.dtDetalleOrden.getRowData();
/*  436: 506 */     return this.servicioLote.autocompletarLote(detalle.getProducto(), consulta + "~LIKE" + "~MAX_RESULT");
/*  437:     */   }
/*  438:     */   
/*  439:     */   public void capturarPesoListener()
/*  440:     */   {
/*  441: 510 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/*  442:     */       try
/*  443:     */       {
/*  444: 512 */         String ip = getIP();
/*  445: 513 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, true);
/*  446:     */       }
/*  447:     */       catch (AS2Exception ex)
/*  448:     */       {
/*  449: 515 */         JsfUtil.addErrorMessage(ex, "");
/*  450:     */       }
/*  451:     */     }
/*  452:     */   }
/*  453:     */   
/*  454:     */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/*  455:     */   {
/*  456:     */     try
/*  457:     */     {
/*  458: 522 */       LecturaBalanza lbBDD = (LecturaBalanza)this.servicioLecturaBalanza.buscarPorId(lectura.getClass(), Integer.valueOf(lectura.getId()));
/*  459: 523 */       if ((lbBDD == null) || (!lbBDD.isIndicadorRecibido()))
/*  460:     */       {
/*  461: 524 */         lectura.setEliminado(true);
/*  462: 525 */         totalizarCantidadPesada(lectura.getDetalleOrdenSalidaMaterial());
/*  463: 527 */         if (lbBDD != null) {
/*  464: 528 */           guardar(false, false);
/*  465:     */         }
/*  466: 532 */         lectura.getDetalleOrdenSalidaMaterial().getListaLecturaBalanza().remove(lectura);
/*  467: 533 */         this.listaLecturaBalanza = null;
/*  468: 534 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/*  469: 535 */         this.lecturaBalanza = null;
/*  470: 536 */         getLecturaBalanza().setDispositivo(dispositivo);
/*  471: 537 */         if (this.dtLecturaBalanza != null) {
/*  472: 538 */           this.dtLecturaBalanza.reset();
/*  473:     */         }
/*  474: 540 */         if (isMostrarBalanza()) {
/*  475:     */           try
/*  476:     */           {
/*  477: 542 */             this.servicioOrdenSalidaMaterial.crearMovimientoInventario(this.ordenSalidaMaterial, lectura.getProducto());
/*  478:     */           }
/*  479:     */           catch (AS2Exception e)
/*  480:     */           {
/*  481: 544 */             JsfUtil.addErrorMessage(e, "");
/*  482: 545 */             LOG.error("ERROR AL GUARDAR DATOS", e);
/*  483:     */           }
/*  484:     */           catch (ExcepcionAS2 e)
/*  485:     */           {
/*  486: 547 */             addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  487: 548 */             LOG.error("ERROR AL GUARDAR DATOS", e);
/*  488:     */           }
/*  489:     */         }
/*  490:     */       }
/*  491:     */       else
/*  492:     */       {
/*  493: 552 */         lectura.setIndicadorRecibido(lbBDD.isIndicadorRecibido());
/*  494: 553 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  495:     */       }
/*  496:     */     }
/*  497:     */     catch (AS2Exception e)
/*  498:     */     {
/*  499: 556 */       e.printStackTrace();
/*  500: 557 */       JsfUtil.addErrorMessage(e, "");
/*  501:     */     }
/*  502:     */     catch (Exception e)
/*  503:     */     {
/*  504: 559 */       e.printStackTrace();
/*  505: 560 */       addErrorMessage(e.getMessage());
/*  506:     */     }
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void totalizarCantidadPesada(DetalleOrdenSalidaMaterial detalle)
/*  510:     */     throws AS2Exception
/*  511:     */   {
/*  512: 566 */     this.servicioOrdenSalidaMaterial.totalizarCantidadPesada(detalle, this.movimiento);
/*  513:     */   }
/*  514:     */   
/*  515:     */   public List<Producto> autocompletarProductos(String consulta)
/*  516:     */   {
/*  517: 586 */     Map<String, String> filtros = new HashMap();
/*  518: 587 */     filtros.put("indicadorPesoBalanza", String.valueOf(true));
/*  519: 588 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void cerrarTodo()
/*  523:     */   {
/*  524:     */     List<OrdenSalidaMaterial> lista;
/*  525: 592 */     if (this.ordenSalidaMaterial == null)
/*  526:     */     {
/*  527: 593 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  528:     */     }
/*  529: 595 */     else if ((this.ordenSalidaMaterial != null) && (this.ordenSalidaMaterial.getId() != 0))
/*  530:     */     {
/*  531: 596 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  532:     */       
/*  533: 598 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/*  534: 599 */       filters.put("fecha", OperacionEnum.BETWEEN.name() + sdf.format(FuncionesUtiles.getFechaInicioMes(this.ordenSalidaMaterial.getFecha())) + "~" + sdf
/*  535: 600 */         .format(FuncionesUtiles.getFechaFinMes(this.ordenSalidaMaterial.getFecha())));
/*  536: 601 */       lista = this.servicioOrdenSalidaMaterial.obtenerListaCombo("numero", true, filters);
/*  537: 603 */       for (OrdenSalidaMaterial ordenSalidaMaterialTmp : lista)
/*  538:     */       {
/*  539: 604 */         this.ordenSalidaMaterial = ordenSalidaMaterialTmp;
/*  540: 605 */         cerrarOrdenSalidaMaterial();
/*  541: 606 */         this.numeroOSMCerrar = lista.size();
/*  542: 607 */         if (this.listaMensajes.size() > 0)
/*  543:     */         {
/*  544: 608 */           FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  545: 609 */           RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  546:     */         }
/*  547:     */       }
/*  548:     */     }
/*  549:     */   }
/*  550:     */   
/*  551:     */   public void cerrarOrdenSalidaMaterial()
/*  552:     */   {
/*  553: 621 */     if ((this.ordenSalidaMaterial != null) && (this.ordenSalidaMaterial.getId() != 0))
/*  554:     */     {
/*  555: 622 */       if ((this.ordenSalidaMaterial.isIndicadorTransferencia()) || (this.ordenSalidaMaterial.getEstado().equals(Estado.PROCESADO))) {
/*  556:     */         try
/*  557:     */         {
/*  558: 625 */           this.servicioOrdenSalidaMaterial.cerrarOrdenSalidaMaterial(this.ordenSalidaMaterial);
/*  559:     */           
/*  560: 627 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  561:     */         }
/*  562:     */         catch (ExcepcionAS2Financiero e)
/*  563:     */         {
/*  564: 630 */           if (e.getMessage() != null) {
/*  565: 631 */             this.listaMensajes.add(this.ordenSalidaMaterial.getNumero() + "--->" + e.getMessage());
/*  566:     */           }
/*  567: 633 */           LOG.info("ERROR AL GENERAR EL CONSUMO DE BODEGA: ORDEN SALIDA MATERIAL:", e);
/*  568:     */         }
/*  569:     */         catch (ExcepcionAS2Inventario e)
/*  570:     */         {
/*  571: 635 */           if (e.getMessage() != null) {
/*  572: 636 */             this.listaMensajes.add(this.ordenSalidaMaterial.getNumero() + "--->" + e.getMessage());
/*  573:     */           }
/*  574: 638 */           LOG.info("ERROR AL GENERAR EL CONSUMO DE BODEGA: ORDEN SALIDA MATERIAL:", e);
/*  575:     */         }
/*  576:     */         catch (ExcepcionAS2 e)
/*  577:     */         {
/*  578: 641 */           if (e.getMessage() != null) {
/*  579: 642 */             this.listaMensajes.add(this.ordenSalidaMaterial.getNumero() + "--->" + e.getMessage());
/*  580:     */           }
/*  581: 644 */           LOG.info("ERROR AL GENERAR EL CONSUMO DE BODEGA: ORDEN SALIDA MATERIAL", e);
/*  582:     */         }
/*  583:     */         catch (AS2Exception e)
/*  584:     */         {
/*  585: 647 */           for (String a : e.getMensajes()) {
/*  586: 648 */             this.listaMensajes.add(this.ordenSalidaMaterial.getNumero() + "--->" + a);
/*  587:     */           }
/*  588: 650 */           LOG.info("ERROR AL GENERAR EL CONSUMO DE BODEGA: ORDEN SALIDA MATERIAL", e);
/*  589:     */         }
/*  590:     */         catch (Exception e)
/*  591:     */         {
/*  592: 652 */           e.printStackTrace();
/*  593: 653 */           if (e.getMessage() != null) {
/*  594: 654 */             this.listaMensajes.add(this.ordenSalidaMaterial.getNumero() + "--->" + e.getMessage());
/*  595:     */           }
/*  596: 656 */           LOG.error("ERROR AL GENERAR EL CONSUMO DE BODEGA: ORDEN SALIDA MATERIAL", e);
/*  597:     */         }
/*  598:     */         finally
/*  599:     */         {
/*  600: 658 */           if ((this.numeroOSMCerrar == 0) && (this.listaMensajes.size() > 0))
/*  601:     */           {
/*  602: 659 */             FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  603: 660 */             RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  604:     */           }
/*  605:     */         }
/*  606:     */       } else {
/*  607: 664 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + "-" + this.ordenSalidaMaterial.getEstado());
/*  608:     */       }
/*  609:     */     }
/*  610:     */     else {
/*  611: 667 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  612:     */     }
/*  613:     */   }
/*  614:     */   
/*  615:     */   public String cargarOrenSalidaMaterial(FileUploadEvent event)
/*  616:     */   {
/*  617:     */     try
/*  618:     */     {
/*  619: 675 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  620: 676 */       this.servicioMigracion.cargarOrdenSalidaMaterial(AppUtil.getOrganizacion().getId(), input, 4, getListaDetalleOrden(), 
/*  621: 677 */         getOrdenSalidaMaterial());
/*  622: 678 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  623:     */     }
/*  624:     */     catch (AS2Exception e)
/*  625:     */     {
/*  626: 680 */       JsfUtil.addErrorMessage(e, "");
/*  627: 681 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/*  628: 682 */       e.printStackTrace();
/*  629:     */     }
/*  630:     */     catch (ExcepcionAS2 e)
/*  631:     */     {
/*  632: 684 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  633: 685 */       LOG.error("ERROR AL MIGRAR ", e);
/*  634: 686 */       e.printStackTrace();
/*  635:     */     }
/*  636:     */     catch (Exception e)
/*  637:     */     {
/*  638: 688 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  639: 689 */       e.printStackTrace();
/*  640:     */     }
/*  641: 691 */     return null;
/*  642:     */   }
/*  643:     */   
/*  644:     */   private void validarCantidaddesechoInformativa(OrdenSalidaMaterial ordenSalidaMaterial)
/*  645:     */     throws ExcepcionAS2, AS2Exception
/*  646:     */   {
/*  647: 695 */     for (DetalleOrdenSalidaMaterial dosm : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  648: 696 */       if (!dosm.isEliminado())
/*  649:     */       {
/*  650: 697 */         if ((this.movimiento.equals("DESECHO")) && (dosm.getProducto().isIndicadorManejaUnidadInformativa()) && 
/*  651: 698 */           ((!this.mostrarBalanza.booleanValue()) || (!dosm.getProducto().isIndicadorPesoBalanza())) && (dosm.getCantidadUnidadInformativaDesecho() == null) && 
/*  652: 699 */           (dosm.getCantidadDespachada().compareTo(BigDecimal.ZERO) > 0)) {
/*  653: 700 */           throw new ExcepcionAS2("msg_error_cantidad_desecho_informativa_requerida");
/*  654:     */         }
/*  655: 702 */         this.servicioOrdenSalidaMaterial.validarCantidadDesecho(dosm, dosm.getCantidadDesecho(), this.movimiento, ordenSalidaMaterial
/*  656: 703 */           .getTipoCicloProduccionEnum());
/*  657:     */       }
/*  658:     */     }
/*  659:     */   }
/*  660:     */   
/*  661:     */   public void agregarDetalleDesdeCodigoCabecera()
/*  662:     */   {
/*  663:     */     try
/*  664:     */     {
/*  665: 710 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(this.codigoCabecera, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  666: 715 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/*  667:     */       {
/*  668: 717 */         HashMap<Integer, DetalleOrdenSalidaMaterial> hmDetalleOrden = new HashMap();
/*  669: 718 */         for (DetalleOrdenSalidaMaterial detalleOrden : getListaDetalleOrden()) {
/*  670: 719 */           if (detalleOrden.getCantidadDespachada().compareTo(BigDecimal.ZERO) <= 0) {
/*  671: 720 */             hmDetalleOrden.put(Integer.valueOf(detalleOrden.getProducto().getId()), detalleOrden);
/*  672:     */           }
/*  673:     */         }
/*  674: 724 */         DetalleOrdenSalidaMaterial detalleOrden = (DetalleOrdenSalidaMaterial)hmDetalleOrden.get(Integer.valueOf(producto.getId()));
/*  675: 726 */         if (detalleOrden == null)
/*  676:     */         {
/*  677: 727 */           detalleOrden = new DetalleOrdenSalidaMaterial();
/*  678: 728 */           detalleOrden.setIdOrganizacion(this.ordenSalidaMaterial.getIdOrganizacion());
/*  679: 729 */           detalleOrden.setIdSucursal(this.ordenSalidaMaterial.getSucursal().getIdSucursal());
/*  680: 730 */           detalleOrden.setCantidadDespachada(BigDecimal.ZERO);
/*  681: 731 */           detalleOrden.setCantidadUnidadInformativaDespacho(BigDecimal.ZERO);
/*  682: 732 */           detalleOrden.setOrdenSalidaMaterial(this.ordenSalidaMaterial);
/*  683: 733 */           this.ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial().add(detalleOrden);
/*  684:     */         }
/*  685: 735 */         detalleOrden.setProducto(producto);
/*  686: 736 */         detalleOrden.setIndicadorConsumoDirecto(producto.isIndicadorConsumoDirecto());
/*  687: 737 */         detalleOrden.setUnidad(producto.getUnidad());
/*  688: 738 */         detalleOrden.setCantidad(producto.getTraCantidad());
/*  689: 739 */         detalleOrden.setLote(producto.getLote() != null ? producto.getLote() : null);
/*  690: 740 */         actulizarBodega(detalleOrden);
/*  691:     */         
/*  692: 742 */         this.servicioOrdenSalidaMaterial.agregarDetalleDesdeCodigoCabecera(this.ordenSalidaMaterial, detalleOrden);
/*  693:     */       }
/*  694: 745 */       else if (producto.getLote() != null)
/*  695:     */       {
/*  696: 746 */         cargarProducto(producto, producto.getLote());
/*  697:     */       }
/*  698:     */       else
/*  699:     */       {
/*  700: 748 */         cargarProducto(producto);
/*  701:     */       }
/*  702: 751 */       this.codigoCabecera = "";
/*  703: 752 */       resetearTablas();
/*  704:     */     }
/*  705:     */     catch (ExcepcionAS2 e)
/*  706:     */     {
/*  707: 754 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  708: 755 */       e.printStackTrace();
/*  709:     */     }
/*  710:     */     catch (AS2Exception e)
/*  711:     */     {
/*  712: 757 */       JsfUtil.addErrorMessage(e, "");
/*  713: 758 */       e.printStackTrace();
/*  714:     */     }
/*  715:     */   }
/*  716:     */   
/*  717:     */   public void eliminarDetalleOrdenConCero()
/*  718:     */   {
/*  719: 765 */     if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  720: 766 */       for (DetalleOrdenSalidaMaterial detalleOrden : getListaDetalleOrden()) {
/*  721: 767 */         if (detalleOrden.getCantidadDespachada().compareTo(BigDecimal.ZERO) == 0) {
/*  722: 768 */           detalleOrden.setEliminado(true);
/*  723:     */         }
/*  724:     */       }
/*  725:     */     }
/*  726:     */   }
/*  727:     */   
/*  728:     */   public void copiar()
/*  729:     */   {
/*  730: 776 */     if ((getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().getIdOrdenSalidaMaterial() != 0))
/*  731:     */     {
/*  732: 777 */       setOrdenSalidaMaterial(this.servicioOrdenSalidaMaterial.copiarOrdenSalidaMaterial(getOrdenSalidaMaterial().getId(), AppUtil.getSucursal()));
/*  733: 778 */       this.movimiento = "DESPACHO";
/*  734: 779 */       setEditado(true);
/*  735: 780 */       this.dtDetalleOrden.reset();
/*  736: 781 */       this.listaDetalleOrden = null;
/*  737: 782 */       this.listaDetalleOrdenFilteredValue = null;
/*  738:     */     }
/*  739:     */     else
/*  740:     */     {
/*  741: 784 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  742:     */     }
/*  743:     */   }
/*  744:     */   
/*  745:     */   public List<Bodega> getListaBodega()
/*  746:     */   {
/*  747: 789 */     if (this.listaBodega == null) {
/*  748: 790 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/*  749:     */     }
/*  750: 792 */     return this.listaBodega;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public LazyDataModel<OrdenSalidaMaterial> getListaOrdenSalidaMaterial()
/*  754:     */   {
/*  755: 801 */     return this.listaOrdenSalidaMaterial;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setListaOrdenSalidaMaterial(LazyDataModel<OrdenSalidaMaterial> listaOrdenSalidaMaterial)
/*  759:     */   {
/*  760: 811 */     this.listaOrdenSalidaMaterial = listaOrdenSalidaMaterial;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/*  764:     */   {
/*  765: 815 */     return this.ordenSalidaMaterial;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/*  769:     */   {
/*  770: 819 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public List<Documento> getListaDocumento()
/*  774:     */   {
/*  775: 823 */     if (this.listaDocumento == null) {
/*  776:     */       try
/*  777:     */       {
/*  778: 825 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.ORDEN_SALIDA_MATERIAL);
/*  779:     */       }
/*  780:     */       catch (ExcepcionAS2 e)
/*  781:     */       {
/*  782: 827 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  783:     */       }
/*  784:     */     }
/*  785: 830 */     return this.listaDocumento;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setListaDocumento(List<Documento> listaDocumento)
/*  789:     */   {
/*  790: 834 */     this.listaDocumento = listaDocumento;
/*  791:     */   }
/*  792:     */   
/*  793:     */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrden()
/*  794:     */   {
/*  795: 839 */     if (this.listaDetalleOrden == null)
/*  796:     */     {
/*  797: 840 */       this.listaDetalleOrden = new ArrayList();
/*  798: 842 */       for (DetalleOrdenSalidaMaterial detalleOrden : this.ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  799: 843 */         if (!detalleOrden.isEliminado()) {
/*  800: 846 */           if (this.movimiento.equals("DESECHO"))
/*  801:     */           {
/*  802: 847 */             if ((detalleOrden.isIndicadorConsumoDirecto()) || ((!detalleOrden.isIndicadorConsumoDirecto()) && 
/*  803: 848 */               (detalleOrden.getCantidadDespachada().compareTo(BigDecimal.ZERO) > 0))) {
/*  804: 849 */               this.listaDetalleOrden.add(detalleOrden);
/*  805:     */             }
/*  806:     */           }
/*  807: 853 */           else if ((this.ingresoDevolucion) || (!isMostrarBalanza()) || (!detalleOrden.isIndicadorConsumoDirecto())) {
/*  808: 856 */             this.listaDetalleOrden.add(detalleOrden);
/*  809:     */           }
/*  810:     */         }
/*  811:     */       }
/*  812:     */     }
/*  813: 862 */     return this.listaDetalleOrden;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void setListaDetalleOrden(List<DetalleOrdenSalidaMaterial> listaDetalleOrden)
/*  817:     */   {
/*  818: 866 */     this.listaDetalleOrden = listaDetalleOrden;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public DataTable getDtDetalleOrden()
/*  822:     */   {
/*  823: 875 */     return this.dtDetalleOrden;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public void setDtDetalleOrden(DataTable dtDetalleOrden)
/*  827:     */   {
/*  828: 885 */     this.dtDetalleOrden = dtDetalleOrden;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public List<LecturaBalanza> getListaLecturaBalanza()
/*  832:     */   {
/*  833: 889 */     if (this.listaLecturaBalanza == null)
/*  834:     */     {
/*  835: 890 */       this.listaLecturaBalanza = new ArrayList();
/*  836: 891 */       for (DetalleOrdenSalidaMaterial detalle : this.ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/*  837: 892 */         for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/*  838: 893 */           if (!lectura.isEliminado()) {
/*  839: 894 */             if (lectura.getOperacion() == (this.movimiento.equals("DEVOLVER") ? -1 : this.movimiento.equals("DESPACHO") ? 1 : 0)) {
/*  840: 895 */               this.listaLecturaBalanza.add(lectura);
/*  841:     */             }
/*  842:     */           }
/*  843:     */         }
/*  844:     */       }
/*  845:     */     }
/*  846: 900 */     return this.listaLecturaBalanza;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public DataTable getDtLecturaBalanza()
/*  850:     */   {
/*  851: 904 */     return this.dtLecturaBalanza;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/*  855:     */   {
/*  856: 908 */     this.dtLecturaBalanza = dtLecturaBalanza;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public LecturaBalanza getLecturaBalanza()
/*  860:     */   {
/*  861: 912 */     if (this.lecturaBalanza == null)
/*  862:     */     {
/*  863: 913 */       this.lecturaBalanza = new LecturaBalanza();
/*  864: 914 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  865: 915 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/*  866: 916 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/*  867:     */     }
/*  868: 918 */     return this.lecturaBalanza;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/*  872:     */   {
/*  873: 922 */     this.lecturaBalanza = lecturaBalanza;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public List<UnidadManejo> getListaUnidadManejo()
/*  877:     */   {
/*  878: 926 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/*  879:     */     {
/*  880: 927 */       this.listaUnidadManejo = new ArrayList();
/*  881: 928 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/*  882: 929 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/*  883:     */       }
/*  884:     */     }
/*  885: 932 */     return this.listaUnidadManejo;
/*  886:     */   }
/*  887:     */   
/*  888:     */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/*  889:     */   {
/*  890: 936 */     this.listaUnidadManejo = listaUnidadManejo;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public List<UnidadManejo> getListaPallet()
/*  894:     */   {
/*  895: 940 */     if (this.listaPallet == null)
/*  896:     */     {
/*  897: 941 */       Map<String, String> filters = new HashMap();
/*  898: 942 */       agregarFiltroOrganizacion(filters);
/*  899: 943 */       filters.put("activo", "true");
/*  900: 944 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/*  901: 945 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/*  902:     */     }
/*  903: 947 */     return this.listaPallet;
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void setListaPallet(List<UnidadManejo> listaPallet)
/*  907:     */   {
/*  908: 951 */     this.listaPallet = listaPallet;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public void agregarPesoListener()
/*  912:     */   {
/*  913: 956 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0)) {
/*  914:     */       try
/*  915:     */       {
/*  916: 958 */         if ((this.movimiento.equals("DESECHO")) || (this.movimiento.equals("DEVOLVER"))) {
/*  917: 959 */           this.servicioOrdenSalidaMaterial.validarCantidadDesecho(this.detalleOrdenSalidaMaterialSeleccionado, this.lecturaBalanza.getPesoNeto(), this.movimiento, null);
/*  918:     */         }
/*  919: 962 */         this.servicioOrdenSalidaMaterial.agregarPeso(this.ordenSalidaMaterial, this.lecturaBalanza, this.detalleOrdenSalidaMaterialSeleccionado, 
/*  920: 963 */           isMostrarBalanza(), getListaDetalleOrden(), this.movimiento, getListaLecturaBalanza(), AppUtil.getBodega());
/*  921: 964 */         this.ordenSalidaMaterial = this.servicioOrdenSalidaMaterial.cargarDetalle(this.ordenSalidaMaterial.getId());
/*  922: 965 */         resetearTablas();
/*  923:     */       }
/*  924:     */       catch (ExcepcionAS2 e)
/*  925:     */       {
/*  926: 968 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  927: 969 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/*  928:     */       }
/*  929:     */       catch (AS2Exception e)
/*  930:     */       {
/*  931: 971 */         JsfUtil.addErrorMessage(e, "");
/*  932:     */       }
/*  933:     */     }
/*  934:     */   }
/*  935:     */   
/*  936:     */   public boolean isMostrarBalanza()
/*  937:     */   {
/*  938: 977 */     if (this.mostrarBalanza == null) {
/*  939: 978 */       this.mostrarBalanza = ParametrosSistema.getDespachoUsaBalanza(AppUtil.getOrganizacion().getId());
/*  940:     */     }
/*  941: 980 */     return this.mostrarBalanza.booleanValue();
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void cargarProductoSeleccionadoPesa(SelectEvent event)
/*  945:     */   {
/*  946: 984 */     DetalleOrdenSalidaMaterial dosm = (DetalleOrdenSalidaMaterial)event.getObject();
/*  947: 985 */     if ((isMostrarBalanza()) && (this.lecturaBalanza != null) && (dosm.getProducto().isIndicadorPesoBalanza()))
/*  948:     */     {
/*  949: 986 */       this.lecturaBalanza.setProducto(this.servicioProducto.buscarPorId(dosm.getProducto().getId()));
/*  950:     */     }
/*  951: 988 */     else if (this.lecturaBalanza != null)
/*  952:     */     {
/*  953: 989 */       Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/*  954: 990 */       this.lecturaBalanza = null;
/*  955: 991 */       getLecturaBalanza().setDispositivo(dispositivo);
/*  956:     */     }
/*  957:     */   }
/*  958:     */   
/*  959:     */   public DetalleOrdenSalidaMaterial getDetalleOrdenSalidaMaterialSeleccionado()
/*  960:     */   {
/*  961: 997 */     return this.detalleOrdenSalidaMaterialSeleccionado;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void setDetalleOrdenSalidaMaterialSeleccionado(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterialSeleccionado)
/*  965:     */   {
/*  966:1001 */     this.detalleOrdenSalidaMaterialSeleccionado = detalleOrdenSalidaMaterialSeleccionado;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public SelectItem[] getListaEstadoItem()
/*  970:     */   {
/*  971:1005 */     if (this.listaEstadoItem == null)
/*  972:     */     {
/*  973:1006 */       this.listaEstadoItem = new SelectItem[5];
/*  974:1007 */       this.listaEstadoItem[0] = new SelectItem("", "");
/*  975:1008 */       this.listaEstadoItem[1] = new SelectItem(Estado.ELABORADO, Estado.ELABORADO.getNombre());
/*  976:1009 */       this.listaEstadoItem[2] = new SelectItem(Estado.ANULADO, Estado.ANULADO.getNombre());
/*  977:1010 */       this.listaEstadoItem[3] = new SelectItem(Estado.PROCESADO, Estado.PROCESADO.getNombre());
/*  978:1011 */       this.listaEstadoItem[4] = new SelectItem(Estado.CERRADO, Estado.CERRADO.getNombre());
/*  979:     */     }
/*  980:1013 */     return this.listaEstadoItem;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public SelectItem[] getListaTipoCicloProduccionItem()
/*  984:     */   {
/*  985:1022 */     if (this.listaTipoCicloProduccionItem == null)
/*  986:     */     {
/*  987:1024 */       List<SelectItem> lista = new ArrayList();
/*  988:1025 */       lista.add(new SelectItem("", ""));
/*  989:1027 */       for (TipoCicloProduccionEnum t : TipoCicloProduccionEnum.values())
/*  990:     */       {
/*  991:1028 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  992:1029 */         lista.add(item);
/*  993:     */       }
/*  994:1031 */       this.listaTipoCicloProduccionItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/*  995:     */     }
/*  996:1034 */     Arrays.sort(this.listaTipoCicloProduccionItem, new SelectItemComparator());
/*  997:     */     
/*  998:1036 */     return this.listaTipoCicloProduccionItem;
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public void setListaEstadoItem(SelectItem[] listaEstadoItem)
/* 1002:     */   {
/* 1003:1040 */     this.listaEstadoItem = listaEstadoItem;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public boolean isIngresoDevolucion()
/* 1007:     */   {
/* 1008:1044 */     return this.ingresoDevolucion;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public void setIngresoDevolucion(boolean ingresoDevolucion)
/* 1012:     */   {
/* 1013:1048 */     this.ingresoDevolucion = ingresoDevolucion;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public void calcularCantidad()
/* 1017:     */   {
/* 1018:1052 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 1019:1053 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 1020:     */     }
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public List<Dispositivo> getListaDispositivo()
/* 1024:     */   {
/* 1025:1058 */     if (this.listaDispositivo == null)
/* 1026:     */     {
/* 1027:1059 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1028:1060 */       filtros.put("activo", "true");
/* 1029:1061 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 1030:     */     }
/* 1031:1063 */     return this.listaDispositivo;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public Integer getIdOrdenSalidaMaterial()
/* 1035:     */   {
/* 1036:1067 */     return this.idOrdenSalidaMaterial;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public void setIdOrdenSalidaMaterial(Integer idOrdenSalidaMaterial)
/* 1040:     */   {
/* 1041:1071 */     this.idOrdenSalidaMaterial = idOrdenSalidaMaterial;
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public List<DestinoCosto> autocompletarDestinoCosto(String consulta)
/* 1045:     */   {
/* 1046:1082 */     consulta = consulta.toUpperCase();
/* 1047:1083 */     List<DestinoCosto> lista = this.servicioDestinoCosto.autocompletarDestinoCosto(consulta);
/* 1048:1084 */     return lista;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public void reabrirOrden()
/* 1052:     */   {
/* 1053:1089 */     if ((getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().getIdOrdenSalidaMaterial() != 0)) {
/* 1054:     */       try
/* 1055:     */       {
/* 1056:1091 */         this.servicioOrdenSalidaMaterial.reabrirOrden(this.ordenSalidaMaterial);
/* 1057:     */       }
/* 1058:     */       catch (ExcepcionAS2Financiero e)
/* 1059:     */       {
/* 1060:1093 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1061:1094 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1062:     */       }
/* 1063:     */       catch (AS2Exception e)
/* 1064:     */       {
/* 1065:1096 */         JsfUtil.addErrorMessage(e, "");
/* 1066:     */       }
/* 1067:     */     } else {
/* 1068:1099 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1069:     */     }
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public void copiarDetalle()
/* 1073:     */   {
/* 1074:1104 */     DetalleOrdenSalidaMaterial detalleOriginal = (DetalleOrdenSalidaMaterial)this.dtDetalleOrden.getRowData();
/* 1075:1105 */     DetalleOrdenSalidaMaterial detalleNuevo = crearDetalleOrdenSalida(true, detalleOriginal);
/* 1076:1106 */     detalleNuevo.setProducto(detalleOriginal.getProducto());
/* 1077:1107 */     detalleNuevo.setUnidad(detalleOriginal.getProducto().getUnidad());
/* 1078:1108 */     if (detalleOriginal.getProducto().isIndicadorLote()) {
/* 1079:1109 */       detalleNuevo.setLote(detalleOriginal.getLote());
/* 1080:     */     }
/* 1081:1111 */     detalleNuevo.setTraEditarCopia(false);
/* 1082:1112 */     actulizarBodega(detalleNuevo);
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public Boolean getIndicadorManejaCiclosLargos()
/* 1086:     */   {
/* 1087:1116 */     if (this.indicadorManejaCiclosLargos == null) {
/* 1088:1117 */       this.indicadorManejaCiclosLargos = ParametrosSistema.getManejaCiclosLargosProduccion(AppUtil.getOrganizacion().getId());
/* 1089:     */     }
/* 1090:1119 */     return this.indicadorManejaCiclosLargos;
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public Boolean getAprobarOrdenSalidaMaterial()
/* 1094:     */   {
/* 1095:1123 */     if (this.aprobarOrdenSalidaMaterial == null) {
/* 1096:1124 */       this.aprobarOrdenSalidaMaterial = ParametrosSistema.getAprobarOrdenSalidaMaterial(AppUtil.getOrganizacion().getId());
/* 1097:     */     }
/* 1098:1126 */     return this.aprobarOrdenSalidaMaterial;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public List<TipoCicloProduccionEnum> getListaTipoCicloEnum()
/* 1102:     */   {
/* 1103:1130 */     if (this.listaTipoCicloEnum == null)
/* 1104:     */     {
/* 1105:1131 */       this.listaTipoCicloEnum = new ArrayList();
/* 1106:1132 */       for (TipoCicloProduccionEnum tipoCiclo : TipoCicloProduccionEnum.values()) {
/* 1107:1133 */         this.listaTipoCicloEnum.add(tipoCiclo);
/* 1108:     */       }
/* 1109:     */     }
/* 1110:1136 */     return this.listaTipoCicloEnum;
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public List<OrdenFabricacionOrdenSalidaMaterial> getListaOrdenFabricacion()
/* 1114:     */   {
/* 1115:1140 */     List<OrdenFabricacionOrdenSalidaMaterial> listaOrdenFabricacion = new ArrayList();
/* 1116:1141 */     if (this.ordenSalidaMaterial != null) {
/* 1117:1142 */       for (OrdenFabricacionOrdenSalidaMaterial detalle : this.ordenSalidaMaterial.getListaOrdenFabricacionOrdenSalidaMaterial()) {
/* 1118:1143 */         if (!detalle.isEliminado()) {
/* 1119:1144 */           listaOrdenFabricacion.add(detalle);
/* 1120:     */         }
/* 1121:     */       }
/* 1122:     */     }
/* 1123:1148 */     return listaOrdenFabricacion;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public DataTable getDtOrdenFabricacion()
/* 1127:     */   {
/* 1128:1152 */     return this.dtOrdenFabricacion;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 1132:     */   {
/* 1133:1156 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public void eliminarOrdenFabricacion(OrdenFabricacionOrdenSalidaMaterial orden)
/* 1137:     */   {
/* 1138:1160 */     orden.setEliminado(true);
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public DataTable getDtOrdenFabricacionNoAsignadas()
/* 1142:     */   {
/* 1143:1164 */     return this.dtOrdenFabricacionNoAsignadas;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public void setDtOrdenFabricacionNoAsignadas(DataTable dtOrdenFabricacionNoAsignadas)
/* 1147:     */   {
/* 1148:1168 */     this.dtOrdenFabricacionNoAsignadas = dtOrdenFabricacionNoAsignadas;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public List<OrdenFabricacion> getListaOrdenFabricacionCicloLargoNoAsignadas()
/* 1152:     */   {
/* 1153:     */     Set<Integer> ordenesAsignadas;
/* 1154:1172 */     if (this.listaOrdenFabricacionCicloLargoNoAsignadas == null)
/* 1155:     */     {
/* 1156:1173 */       this.listaOrdenFabricacionCicloLargoNoAsignadas = new ArrayList();
/* 1157:1174 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1158:1175 */       filtros.put("estado", EstadoProduccionEnum.LANZADA.toString());
/* 1159:1176 */       filtros.put("tipoCicloProduccionEnum", TipoCicloProduccionEnum.CICLO_LARGO.toString());
/* 1160:1177 */       List<OrdenFabricacion> lista = this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 10000, "fechaLanzamiento", true, filtros, false);
/* 1161:     */       
/* 1162:     */ 
/* 1163:1180 */       ordenesAsignadas = new HashSet();
/* 1164:1181 */       for (OrdenFabricacionOrdenSalidaMaterial ordenAsignada : getListaOrdenFabricacion()) {
/* 1165:1182 */         ordenesAsignadas.add(Integer.valueOf(ordenAsignada.getOrdenFabricacion().getId()));
/* 1166:     */       }
/* 1167:1186 */       for (OrdenFabricacion orden : lista) {
/* 1168:1187 */         if (!ordenesAsignadas.contains(Integer.valueOf(orden.getId()))) {
/* 1169:1188 */           this.listaOrdenFabricacionCicloLargoNoAsignadas.add(orden);
/* 1170:     */         }
/* 1171:     */       }
/* 1172:     */     }
/* 1173:1192 */     return this.listaOrdenFabricacionCicloLargoNoAsignadas;
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public List<OrdenFabricacion> getListaOrdenFabricacionCicloLargoNoAsignadasFilters()
/* 1177:     */   {
/* 1178:1196 */     if (this.listaOrdenFabricacionCicloLargoNoAsignadasFilters == null) {
/* 1179:1197 */       this.listaOrdenFabricacionCicloLargoNoAsignadasFilters = getListaOrdenFabricacionCicloLargoNoAsignadas();
/* 1180:     */     }
/* 1181:1199 */     return this.listaOrdenFabricacionCicloLargoNoAsignadasFilters;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public void setListaOrdenFabricacionCicloLargoNoAsignadasFilters(List<OrdenFabricacion> listaOrdenFabricacionCicloLargoNoAsignadasFilters)
/* 1185:     */   {
/* 1186:1203 */     this.listaOrdenFabricacionCicloLargoNoAsignadasFilters = listaOrdenFabricacionCicloLargoNoAsignadasFilters;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public void agregarOrdenFabricacion(OrdenFabricacion orden)
/* 1190:     */   {
/* 1191:1207 */     OrdenFabricacionOrdenSalidaMaterial ofosm = new OrdenFabricacionOrdenSalidaMaterial();
/* 1192:1208 */     ofosm.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1193:1209 */     ofosm.setIdSucursal(AppUtil.getSucursal().getId());
/* 1194:1210 */     ofosm.setOrdenFabricacion(orden);
/* 1195:1211 */     ofosm.setOrdenSalidaMaterial(this.ordenSalidaMaterial);
/* 1196:1212 */     this.ordenSalidaMaterial.getListaOrdenFabricacionOrdenSalidaMaterial().add(ofosm);
/* 1197:1213 */     reiniciarOFNoAsignadas();
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public void reiniciarOFNoAsignadas()
/* 1201:     */   {
/* 1202:1217 */     this.listaOrdenFabricacionCicloLargoNoAsignadas = null;
/* 1203:1218 */     this.listaOrdenFabricacionCicloLargoNoAsignadasFilters = null;
/* 1204:1219 */     if (this.dtOrdenFabricacionNoAsignadas != null) {
/* 1205:1220 */       this.dtOrdenFabricacionNoAsignadas.reset();
/* 1206:     */     }
/* 1207:1222 */     this.listaOrdenFabricacionCicloCortoNoAsignadas = null;
/* 1208:1223 */     this.listaOrdenFabricacionCicloCortoNoAsignadasFilters = null;
/* 1209:1224 */     if (this.dtOrdenFabricacionCicloCortoNoAsignadas != null) {
/* 1210:1225 */       this.dtOrdenFabricacionCicloCortoNoAsignadas.reset();
/* 1211:     */     }
/* 1212:1227 */     RequestContext.getCurrentInstance().update("tvDetalles:panelDetalleProductos");
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrdenFilteredValue()
/* 1216:     */   {
/* 1217:1234 */     return this.listaDetalleOrdenFilteredValue;
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   public void setListaDetalleOrdenFilteredValue(List<DetalleOrdenSalidaMaterial> listaDetalleOrdenFilteredValue)
/* 1221:     */   {
/* 1222:1242 */     this.listaDetalleOrdenFilteredValue = listaDetalleOrdenFilteredValue;
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public void onRowEdit(RowEditEvent event)
/* 1226:     */   {
/* 1227:1246 */     RequestContext.getCurrentInstance().update("txtNumero");
/* 1228:1247 */     setDetalleOrdenSalidaMaterialSeleccionado((DetalleOrdenSalidaMaterial)event.getObject());
/* 1229:     */     try
/* 1230:     */     {
/* 1231:1249 */       if (this.detalleOrdenSalidaMaterialSeleccionado != null)
/* 1232:     */       {
/* 1233:1250 */         if (this.ordenSalidaMaterial.isIndicadorTransferencia()) {
/* 1234:1251 */           this.detalleOrdenSalidaMaterialSeleccionado.setCantidad(this.detalleOrdenSalidaMaterialSeleccionado.getTraCantidadDespachada());
/* 1235:     */         }
/* 1236:1253 */         this.servicioOrdenSalidaMaterial.editarDetalle(this.detalleOrdenSalidaMaterialSeleccionado, this.movimiento);
/* 1237:     */       }
/* 1238:     */       else
/* 1239:     */       {
/* 1240:1255 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1241:     */       }
/* 1242:     */     }
/* 1243:     */     catch (ExcepcionAS2 e)
/* 1244:     */     {
/* 1245:1258 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1246:1259 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1247:     */     }
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public void onRowCancel(RowEditEvent event)
/* 1251:     */   {
/* 1252:1264 */     setDetalleOrdenSalidaMaterialSeleccionado((DetalleOrdenSalidaMaterial)event.getObject());
/* 1253:1265 */     if (this.detalleOrdenSalidaMaterialSeleccionado != null) {
/* 1254:1266 */       this.detalleOrdenSalidaMaterialSeleccionado.setTraCantidadDespachada(this.detalleOrdenSalidaMaterialSeleccionado.getCantidadDespachada());
/* 1255:     */     }
/* 1256:     */   }
/* 1257:     */   
/* 1258:     */   public String getMovimiento()
/* 1259:     */   {
/* 1260:1270 */     return this.movimiento;
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   public void setMovimiento(String movimiento)
/* 1264:     */   {
/* 1265:1274 */     this.movimiento = movimiento;
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   public List<OrdenFabricacion> getListaOrdenFabricacionCicloCortoNoAsignadas()
/* 1269:     */   {
/* 1270:1278 */     if ((this.ordenSalidaMaterial.getId() != 0) && (getListaOrdenFabricacion().size() > 0))
/* 1271:     */     {
/* 1272:1279 */       this.listaOrdenFabricacionCicloCortoNoAsignadas = new ArrayList();
/* 1273:1280 */       for (OrdenFabricacionOrdenSalidaMaterial ordenOrdenFabricacion : getListaOrdenFabricacion()) {
/* 1274:1281 */         if (ordenOrdenFabricacion.getOrdenFabricacion().getEstado().equals(EstadoProduccionEnum.LANZADA)) {
/* 1275:1282 */           this.listaOrdenFabricacionCicloCortoNoAsignadas.add(ordenOrdenFabricacion.getOrdenFabricacion());
/* 1276:     */         }
/* 1277:     */       }
/* 1278:1285 */       return this.listaOrdenFabricacionCicloCortoNoAsignadas;
/* 1279:     */     }
/* 1280:1287 */     if (this.listaOrdenFabricacionCicloCortoNoAsignadas == null)
/* 1281:     */     {
/* 1282:1288 */       this.listaOrdenFabricacionCicloCortoNoAsignadas = new ArrayList();
/* 1283:1289 */       Object filtros = agregarFiltroOrganizacion(null);
/* 1284:1290 */       ((Map)filtros).put("estado", EstadoProduccionEnum.LANZADA.toString());
/* 1285:1291 */       ((Map)filtros).put("tipoCicloProduccionEnum", TipoCicloProduccionEnum.CICLO_CORTO.toString());
/* 1286:1292 */       List<OrdenFabricacion> lista = this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 10000, "fechaLanzamiento", true, (Map)filtros, false);
/* 1287:1293 */       this.listaOrdenFabricacionCicloCortoNoAsignadas = lista;
/* 1288:     */     }
/* 1289:1295 */     return this.listaOrdenFabricacionCicloCortoNoAsignadas;
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public List<OrdenFabricacion> getListaOrdenFabricacionCicloCortoNoAsignadasFilters()
/* 1293:     */   {
/* 1294:1300 */     if (this.listaOrdenFabricacionCicloCortoNoAsignadasFilters == null) {
/* 1295:1301 */       this.listaOrdenFabricacionCicloCortoNoAsignadasFilters = getListaOrdenFabricacionCicloCortoNoAsignadas();
/* 1296:     */     }
/* 1297:1303 */     return this.listaOrdenFabricacionCicloCortoNoAsignadasFilters;
/* 1298:     */   }
/* 1299:     */   
/* 1300:     */   public DataTable getDtOrdenFabricacionCicloCortoNoAsignadas()
/* 1301:     */   {
/* 1302:1307 */     return this.dtOrdenFabricacionCicloCortoNoAsignadas;
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   public void setDtOrdenFabricacionCicloCortoNoAsignadas(DataTable dtOrdenFabricacionCicloCortoNoAsignadas)
/* 1306:     */   {
/* 1307:1311 */     this.dtOrdenFabricacionCicloCortoNoAsignadas = dtOrdenFabricacionCicloCortoNoAsignadas;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public void detalleSeleccionado(DetalleOrdenSalidaMaterial detalle)
/* 1311:     */   {
/* 1312:1315 */     if (detalle != null) {
/* 1313:1316 */       this.detalleOSMActualizar = ((DetalleOrdenSalidaMaterial)this.dtDetalleOrden.getRowData());
/* 1314:     */     }
/* 1315:     */   }
/* 1316:     */   
/* 1317:     */   public DetalleOrdenSalidaMaterial getDetalleOSMActualizar()
/* 1318:     */   {
/* 1319:1321 */     return this.detalleOSMActualizar;
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public void setDetalleOSMActualizar(DetalleOrdenSalidaMaterial detalleOSMActualizar)
/* 1323:     */   {
/* 1324:1325 */     this.detalleOSMActualizar = detalleOSMActualizar;
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public void enlazarOrdenesDeFabricacion()
/* 1328:     */   {
/* 1329:1330 */     if ((this.detalleOSMActualizar != null) && (this.listaOFSeleccionados != null)) {
/* 1330:1331 */       this.servicioOrdenSalidaMaterial.enlazarOrdenesDeFabricacion(this.detalleOSMActualizar, this.listaOFSeleccionados);
/* 1331:     */     }
/* 1332:1333 */     resetearTablas();
/* 1333:     */   }
/* 1334:     */   
/* 1335:     */   private void validarDetallesOSMSinOF(OrdenSalidaMaterial ordenSalidaMaterial)
/* 1336:     */     throws AS2Exception
/* 1337:     */   {
/* 1338:1338 */     for (DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial : ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial()) {
/* 1339:1339 */       if ((!detalleOrdenSalidaMaterial.isIndicadorConsumoDirecto()) && (!detalleOrdenSalidaMaterial.isEliminado()))
/* 1340:     */       {
/* 1341:1340 */         String mensaje = "";
/* 1342:1341 */         mensaje = mensaje + "," + detalleOrdenSalidaMaterial.getProducto().getCodigo();
/* 1343:1342 */         mensaje = mensaje.substring(1);
/* 1344:1343 */         if (detalleOrdenSalidaMaterial.getListaDetalleOrdenSalidaMaterialOrdenFabricacion().isEmpty()) {
/* 1345:1345 */           throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenSalidaMaterial.ERROR_DETALLE_ORDEN_SALIDA_MATERIAL_SIN_ORDEN_FABRICACION", new String[] {detalleOrdenSalidaMaterial.getProducto().getCodigo(), "" });
/* 1346:     */         }
/* 1347:     */       }
/* 1348:     */     }
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   public List<OrdenFabricacion> getListaOFSeleccionados()
/* 1352:     */   {
/* 1353:1352 */     return this.listaOFSeleccionados;
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   public void setListaOFSeleccionados(List<OrdenFabricacion> listaOFSeleccionados)
/* 1357:     */   {
/* 1358:1356 */     this.listaOFSeleccionados = listaOFSeleccionados;
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   public String getCodigoCabecera()
/* 1362:     */   {
/* 1363:1360 */     return this.codigoCabecera;
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public void setCodigoCabecera(String codigoCabecera)
/* 1367:     */   {
/* 1368:1364 */     this.codigoCabecera = codigoCabecera;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   public List<String> getListaMensajes()
/* 1372:     */   {
/* 1373:1368 */     if (this.listaMensajes == null) {
/* 1374:1369 */       this.listaMensajes = new ArrayList();
/* 1375:     */     }
/* 1376:1371 */     return this.listaMensajes;
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public void setListaMensajes(List<String> listaMensajes)
/* 1380:     */   {
/* 1381:1375 */     this.listaMensajes = listaMensajes;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public void limpiarListaErrores(CloseEvent event)
/* 1385:     */   {
/* 1386:1378 */     this.listaMensajes.clear();
/* 1387:     */   }
/* 1388:     */   
/* 1389:     */   public String getRutaPlantilla()
/* 1390:     */   {
/* 1391:1382 */     return "/resources/plantillas/inventario/AS2 Orden Salida Material.xls";
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public String getNombrePlantilla()
/* 1395:     */   {
/* 1396:1387 */     return "AS2 Orden Salida Material.xls";
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public int getNumeroOSMCerrar()
/* 1400:     */   {
/* 1401:1391 */     return this.numeroOSMCerrar;
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   public void setNumeroOSMCerrar(int numeroOSMCerrar)
/* 1405:     */   {
/* 1406:1395 */     this.numeroOSMCerrar = numeroOSMCerrar;
/* 1407:     */   }
/* 1408:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.OrdenSalidaMaterialBean
 * JD-Core Version:    0.7.0.1
 */