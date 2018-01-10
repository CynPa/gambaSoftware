/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    6:     */ import com.asinfo.as2.entities.Bodega;
/*    7:     */ import com.asinfo.as2.entities.DespachoCliente;
/*    8:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*    9:     */ import com.asinfo.as2.entities.DetalleOrdenDespachoCliente;
/*   10:     */ import com.asinfo.as2.entities.DetalleOrdenDespachoClientePedidoCliente;
/*   11:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   12:     */ import com.asinfo.as2.entities.Dispositivo;
/*   13:     */ import com.asinfo.as2.entities.Documento;
/*   14:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   15:     */ import com.asinfo.as2.entities.Lote;
/*   16:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   17:     */ import com.asinfo.as2.entities.OrdenDespachoCliente;
/*   18:     */ import com.asinfo.as2.entities.Organizacion;
/*   19:     */ import com.asinfo.as2.entities.Producto;
/*   20:     */ import com.asinfo.as2.entities.Sucursal;
/*   21:     */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*   22:     */ import com.asinfo.as2.entities.TipoPresentacionProducto;
/*   23:     */ import com.asinfo.as2.entities.Unidad;
/*   24:     */ import com.asinfo.as2.entities.UnidadManejo;
/*   25:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   26:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   27:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   28:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   29:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   30:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   31:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   32:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   33:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   34:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   35:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   36:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   37:     */ import com.asinfo.as2.inventario.procesos.controller.MovimientoInventarioBaseBean;
/*   38:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*   39:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   40:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   41:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   42:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   43:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   44:     */ import com.asinfo.as2.util.AppUtil;
/*   45:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   46:     */ import com.asinfo.as2.utils.JsfUtil;
/*   47:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   48:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   49:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   50:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioOrdenDespachoCliente;
/*   51:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   52:     */ import java.math.BigDecimal;
/*   53:     */ import java.math.RoundingMode;
/*   54:     */ import java.util.ArrayList;
/*   55:     */ import java.util.Collection;
/*   56:     */ import java.util.Date;
/*   57:     */ import java.util.HashMap;
/*   58:     */ import java.util.Iterator;
/*   59:     */ import java.util.List;
/*   60:     */ import java.util.Map;
/*   61:     */ import javax.annotation.PostConstruct;
/*   62:     */ import javax.ejb.EJB;
/*   63:     */ import javax.faces.bean.ManagedBean;
/*   64:     */ import javax.faces.bean.ViewScoped;
/*   65:     */ import javax.faces.context.FacesContext;
/*   66:     */ import javax.faces.context.PartialViewContext;
/*   67:     */ import org.apache.log4j.Logger;
/*   68:     */ import org.primefaces.component.datatable.DataTable;
/*   69:     */ import org.primefaces.context.RequestContext;
/*   70:     */ import org.primefaces.model.LazyDataModel;
/*   71:     */ import org.primefaces.model.SortOrder;
/*   72:     */ 
/*   73:     */ @ManagedBean
/*   74:     */ @ViewScoped
/*   75:     */ public class OrdenDespachoClienteBean
/*   76:     */   extends MovimientoInventarioBaseBean
/*   77:     */ {
/*   78:     */   private static final long serialVersionUID = -7577609751620904609L;
/*   79:     */   @EJB
/*   80:     */   protected ServicioDespachoCliente servicioDespachoCliente;
/*   81:     */   @EJB
/*   82:     */   private ServicioProducto servicioProducto;
/*   83:     */   @EJB
/*   84:     */   private ServicioDocumento servicioDocumento;
/*   85:     */   @EJB
/*   86:     */   private ServicioEmpresa servicioEmpresa;
/*   87:     */   @EJB
/*   88:     */   private ServicioPedidoCliente servicioPedidoCliente;
/*   89:     */   @EJB
/*   90:     */   private ServicioLote servicioLote;
/*   91:     */   @EJB
/*   92:     */   private ServicioBodega servicioBodega;
/*   93:     */   @EJB
/*   94:     */   private ServicioOrdenDespachoCliente servicioOrdenDespachoCliente;
/*   95:     */   @EJB
/*   96:     */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*   97:     */   @EJB
/*   98:     */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*   99:     */   @EJB
/*  100:     */   private ServicioGenerico<TipoOrdenDespacho> servicioTipoOrdenDespacho;
/*  101:     */   @EJB
/*  102:     */   private ServicioGenerico<LecturaBalanza> servicioLecturaBalanza;
/*  103:     */   @EJB
/*  104:     */   private ServicioGenerico<TipoPresentacionProducto> servicioTipoPresentacionProducto;
/*  105:     */   @EJB
/*  106:     */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  107:     */   @EJB
/*  108:     */   private ServicioUsuario servicioUsuario;
/*  109:     */   @EJB
/*  110:     */   private ServicioCosteo servicioCosteo;
/*  111:     */   @EJB
/*  112:     */   private ServicioGenerico<DetalleOrdenDespachoCliente> servicioDetalleOrdenDespachoCliente;
/*  113:     */   private OrdenDespachoCliente ordenDespachoCliente;
/*  114:     */   private LazyDataModel<OrdenDespachoCliente> listaOrdenDespachoCliente;
/*  115:     */   private LecturaBalanza lecturaBalanza;
/*  116:     */   private List<Documento> listaDocumentoOrdenDespacho;
/*  117:     */   private List<UnidadManejo> listaUnidadManejo;
/*  118:     */   private List<UnidadManejo> listaPallet;
/*  119:     */   private List<TipoOrdenDespacho> listaTipoOrdenDespacho;
/*  120:     */   private List<TipoPresentacionProducto> listaTipoPresentacionProducto;
/*  121:     */   private List<Dispositivo> listaDispositivo;
/*  122:     */   private DataTable dtOrdenDespachoCliente;
/*  123:     */   protected DataTable dtDetalleOrdenDespachoCliente;
/*  124:     */   private DataTable dtDetallePedidoCliente;
/*  125:     */   private DataTable dtLecturaBalanza;
/*  126:     */   private DataTable dtDespachoCliente;
/*  127:     */   private DataTable dtDetalleDespachoCliente;
/*  128: 146 */   private boolean procesado = false;
/*  129:     */   private TipoPresentacionProducto tipoPresentacionProducto;
/*  130:     */   private DetalleOrdenDespachoCliente detalleOrdenDespachoCliente;
/*  131:     */   private List<DespachoCliente> listaDespachoCliente;
/*  132: 154 */   private Boolean usuarioAdministrador = null;
/*  133:     */   
/*  134:     */   @PostConstruct
/*  135:     */   public void init()
/*  136:     */   {
/*  137: 159 */     getListaProductoBean().setIndicadorVenta(true);
/*  138: 160 */     getListaProductoBean().setTipoProducto(TipoProducto.ARTICULO);
/*  139: 161 */     getListaProductoBean().setActivo(true);
/*  140:     */     
/*  141: 163 */     this.listaOrdenDespachoCliente = new LazyDataModel()
/*  142:     */     {
/*  143:     */       private static final long serialVersionUID = 1L;
/*  144:     */       
/*  145:     */       public List<OrdenDespachoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  146:     */       {
/*  147: 170 */         List<OrdenDespachoCliente> lista = new ArrayList();
/*  148:     */         
/*  149: 172 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  150:     */         
/*  151: 174 */         lista = OrdenDespachoClienteBean.this.servicioOrdenDespachoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  152: 175 */         OrdenDespachoClienteBean.this.listaOrdenDespachoCliente.setRowCount(OrdenDespachoClienteBean.this.servicioOrdenDespachoCliente.contarPorCriterio(filters));
/*  153:     */         
/*  154: 177 */         return lista;
/*  155:     */       }
/*  156:     */     };
/*  157:     */   }
/*  158:     */   
/*  159:     */   private void setearTablas()
/*  160:     */   {
/*  161: 183 */     if (this.dtDetalleOrdenDespachoCliente != null) {
/*  162: 184 */       this.dtDetalleOrdenDespachoCliente.reset();
/*  163:     */     }
/*  164: 186 */     if (this.dtLecturaBalanza != null) {
/*  165: 187 */       this.dtLecturaBalanza.reset();
/*  166:     */     }
/*  167:     */   }
/*  168:     */   
/*  169:     */   public String editar()
/*  170:     */   {
/*  171: 198 */     if ((this.ordenDespachoCliente != null) && (this.ordenDespachoCliente.getId() != 0))
/*  172:     */     {
/*  173: 199 */       if (this.ordenDespachoCliente.getEstado().equals(Estado.ELABORADO))
/*  174:     */       {
/*  175: 200 */         this.procesado = true;
/*  176: 201 */         this.ordenDespachoCliente = this.servicioOrdenDespachoCliente.cargarDetalle(Integer.valueOf(this.ordenDespachoCliente.getId()));
/*  177: 202 */         actualizarReparticionPedidos();
/*  178: 203 */         setearTablas();
/*  179: 204 */         setEditado(true);
/*  180:     */       }
/*  181:     */       else
/*  182:     */       {
/*  183: 206 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.ordenDespachoCliente.getEstado());
/*  184:     */       }
/*  185:     */     }
/*  186:     */     else {
/*  187: 209 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  188:     */     }
/*  189: 212 */     return "";
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String finalizar()
/*  193:     */   {
/*  194: 217 */     OrdenDespachoCliente ordenDespachoClienteAux = this.servicioOrdenDespachoCliente.buscarPorId(Integer.valueOf(this.ordenDespachoCliente.getId()));
/*  195:     */     try
/*  196:     */     {
/*  197: 219 */       if (ordenDespachoClienteAux.getEstado().equals(Estado.PROCESADO))
/*  198:     */       {
/*  199: 220 */         addInfoMessage(getLanguageController().getMensaje("msg_error_orden_despacho_cliente"));
/*  200:     */       }
/*  201: 222 */       else if ((this.ordenDespachoCliente != null) && (this.ordenDespachoCliente.getId() != 0))
/*  202:     */       {
/*  203: 224 */         this.servicioOrdenDespachoCliente.validarProducotListaMaterial(this.ordenDespachoCliente);
/*  204: 225 */         actualizarReparticionPedidos();
/*  205: 226 */         if (this.ordenDespachoCliente.getEstado().equals(Estado.ELABORADO)) {
/*  206:     */           try
/*  207:     */           {
/*  208: 229 */             this.listaDespachoCliente = this.servicioOrdenDespachoCliente.generarDespachos(this.ordenDespachoCliente);
/*  209: 230 */             FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelCrearDespachoCliente");
/*  210: 231 */             RequestContext.getCurrentInstance().execute("dglDespachoCliente.show()");
/*  211:     */           }
/*  212:     */           catch (AS2Exception e)
/*  213:     */           {
/*  214: 233 */             JsfUtil.addErrorMessage(e, "");
/*  215:     */           }
/*  216:     */           catch (ExcepcionAS2Inventario e)
/*  217:     */           {
/*  218: 235 */             addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  219: 236 */             LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/*  220:     */           }
/*  221:     */           catch (ExcepcionAS2 e)
/*  222:     */           {
/*  223: 238 */             addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  224: 239 */             LOG.info("ERROR AL GUARDAR DATOS TRANSFORMACION PRODUCTO", e);
/*  225:     */           }
/*  226:     */         } else {
/*  227: 242 */           addInfoMessage(
/*  228: 243 */             getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.ordenDespachoCliente.getEstado());
/*  229:     */         }
/*  230:     */       }
/*  231:     */       else
/*  232:     */       {
/*  233: 246 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  234:     */       }
/*  235:     */     }
/*  236:     */     catch (AS2Exception e)
/*  237:     */     {
/*  238: 250 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  239: 251 */       LOG.info("ERROR AL GUARDAR DATOS", e);
/*  240:     */     }
/*  241: 253 */     return "";
/*  242:     */   }
/*  243:     */   
/*  244:     */   public void guardarDespachosCliente()
/*  245:     */   {
/*  246: 257 */     boolean guardo = false;
/*  247: 258 */     MovimientoInventario egresoTransformacion = null;
/*  248:     */     try
/*  249:     */     {
/*  250: 261 */       egresoTransformacion = this.servicioOrdenDespachoCliente.guardarDespachosCliente(this.listaDespachoCliente, this.ordenDespachoCliente);
/*  251: 262 */       guardo = true;
/*  252: 263 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  253:     */     }
/*  254:     */     catch (AS2Exception e)
/*  255:     */     {
/*  256: 265 */       e.printStackTrace();
/*  257: 266 */       JsfUtil.addErrorMessage(e, "");
/*  258:     */     }
/*  259:     */     catch (ExcepcionAS2Inventario e)
/*  260:     */     {
/*  261: 268 */       e.printStackTrace();
/*  262: 269 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  263:     */     }
/*  264:     */     catch (ExcepcionAS2 e)
/*  265:     */     {
/*  266: 271 */       e.printStackTrace();
/*  267: 272 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  268:     */     }
/*  269: 274 */     if (guardo) {
/*  270:     */       try
/*  271:     */       {
/*  272: 277 */         if ((egresoTransformacion != null) && (egresoTransformacion.getMovimientoInventarioPadre() != null))
/*  273:     */         {
/*  274: 278 */           Integer versionCosteo = null;
/*  275: 280 */           for (Object localObject1 = egresoTransformacion.getMovimientoInventarioPadre()
/*  276: 281 */                 .getDetalleMovimientosInventario().iterator(); ((Iterator)localObject1).hasNext();)
/*  277:     */           {
/*  278: 280 */             detalle = (DetalleMovimientoInventario)((Iterator)localObject1).next();
/*  279: 282 */             if ((versionCosteo == null) || (versionCosteo.compareTo(Integer.valueOf(detalle.getProducto().getVersionCosteo())) < 0)) {
/*  280: 283 */               versionCosteo = Integer.valueOf(detalle.getProducto().getVersionCosteo());
/*  281:     */             }
/*  282:     */           }
/*  283:     */           DetalleMovimientoInventario detalle;
/*  284: 285 */           if (versionCosteo != null)
/*  285:     */           {
/*  286: 286 */             localObject1 = versionCosteo;detalle = versionCosteo = Integer.valueOf(versionCosteo.intValue() + 1);
/*  287:     */           }
/*  288: 288 */           this.servicioCosteo.generarCostos(egresoTransformacion.getMovimientoInventarioPadre(), 
/*  289: 289 */             ParametrosSistema.isCosteoPorBodega(egresoTransformacion.getMovimientoInventarioPadre().getIdOrganizacion()).booleanValue(), versionCosteo);
/*  290:     */           
/*  291:     */ 
/*  292: 292 */           this.servicioMovimientoInventario.contabilizar(egresoTransformacion);
/*  293:     */         }
/*  294:     */       }
/*  295:     */       catch (ExcepcionAS2Financiero e)
/*  296:     */       {
/*  297: 295 */         e.printStackTrace();
/*  298: 296 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  299:     */       }
/*  300:     */       catch (ExcepcionAS2Ventas e)
/*  301:     */       {
/*  302: 298 */         e.printStackTrace();
/*  303: 299 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  304:     */       }
/*  305:     */       catch (AS2Exception e)
/*  306:     */       {
/*  307: 301 */         e.printStackTrace();
/*  308: 302 */         JsfUtil.addErrorMessage(e, "");
/*  309:     */       }
/*  310:     */       catch (ExcepcionAS2 e)
/*  311:     */       {
/*  312: 304 */         e.printStackTrace();
/*  313: 305 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  314:     */       }
/*  315:     */       catch (Exception e)
/*  316:     */       {
/*  317: 307 */         e.printStackTrace();
/*  318: 308 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar") + e.getMessage());
/*  319:     */       }
/*  320:     */       finally
/*  321:     */       {
/*  322: 312 */         setEditado(false);
/*  323:     */       }
/*  324:     */     }
/*  325:     */   }
/*  326:     */   
/*  327:     */   public String eliminar()
/*  328:     */   {
/*  329: 319 */     if ((this.ordenDespachoCliente != null) && (this.ordenDespachoCliente.getId() != 0)) {
/*  330:     */       try
/*  331:     */       {
/*  332: 321 */         this.servicioOrdenDespachoCliente.eliminar(this.ordenDespachoCliente);
/*  333:     */       }
/*  334:     */       catch (AS2Exception e)
/*  335:     */       {
/*  336: 323 */         JsfUtil.addErrorMessage(e, "");
/*  337:     */       }
/*  338:     */     } else {
/*  339: 326 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  340:     */     }
/*  341: 329 */     return "";
/*  342:     */   }
/*  343:     */   
/*  344:     */   public String guardar()
/*  345:     */   {
/*  346: 339 */     if (guardar(this.ordenDespachoCliente))
/*  347:     */     {
/*  348: 340 */       setEditado(false);
/*  349: 341 */       limpiar();
/*  350:     */     }
/*  351: 343 */     return "";
/*  352:     */   }
/*  353:     */   
/*  354:     */   public boolean guardar(OrdenDespachoCliente ordenDespachoCliente)
/*  355:     */   {
/*  356:     */     try
/*  357:     */     {
/*  358: 348 */       this.servicioOrdenDespachoCliente.guardar(ordenDespachoCliente);
/*  359: 349 */       this.ordenDespachoCliente = this.servicioOrdenDespachoCliente.cargarDetalle(Integer.valueOf(ordenDespachoCliente.getId()));
/*  360: 350 */       if (isEditado()) {
/*  361: 351 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar_borrador"));
/*  362:     */       }
/*  363: 353 */       return true;
/*  364:     */     }
/*  365:     */     catch (AS2Exception e)
/*  366:     */     {
/*  367: 355 */       JsfUtil.addErrorMessage(e, "");
/*  368: 356 */       return false;
/*  369:     */     }
/*  370:     */     catch (Exception e)
/*  371:     */     {
/*  372: 358 */       e.printStackTrace();
/*  373: 359 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar") + e.getMessage());
/*  374: 360 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  375:     */     }
/*  376: 361 */     return false;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public List<Documento> getListaDocumentoOrdenDespacho()
/*  380:     */   {
/*  381:     */     try
/*  382:     */     {
/*  383: 367 */       if (this.listaDocumentoOrdenDespacho == null) {
/*  384: 368 */         this.listaDocumentoOrdenDespacho = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.ORDEN_DESPACHO_CLIENTE, 
/*  385: 369 */           AppUtil.getOrganizacion().getId());
/*  386:     */       }
/*  387:     */     }
/*  388:     */     catch (ExcepcionAS2 e)
/*  389:     */     {
/*  390: 372 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  391:     */     }
/*  392: 375 */     return this.listaDocumentoOrdenDespacho;
/*  393:     */   }
/*  394:     */   
/*  395:     */   public String agregarDetalle()
/*  396:     */   {
/*  397: 385 */     DetalleOrdenDespachoCliente detalleOrdenDespachoCliente = new DetalleOrdenDespachoCliente();
/*  398: 386 */     detalleOrdenDespachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  399: 387 */     detalleOrdenDespachoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  400: 388 */     detalleOrdenDespachoCliente.setProducto(new Producto());
/*  401: 389 */     detalleOrdenDespachoCliente.setOrdenDespachoCliente(this.ordenDespachoCliente);
/*  402: 390 */     if (asignarBodega(detalleOrdenDespachoCliente, new Producto())) {
/*  403: 391 */       this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().add(0, detalleOrdenDespachoCliente);
/*  404:     */     }
/*  405: 393 */     return "";
/*  406:     */   }
/*  407:     */   
/*  408:     */   public String eliminarDetalle(DetalleOrdenDespachoCliente detalleOrdenDespachoCliente)
/*  409:     */   {
/*  410: 397 */     detalleOrdenDespachoCliente.setEliminado(true);
/*  411: 398 */     for (LecturaBalanza lb : detalleOrdenDespachoCliente.getListaLecturaBalanza()) {
/*  412: 399 */       if (lb.getId() != 0) {
/*  413: 400 */         this.servicioLecturaBalanza.eliminar(lb);
/*  414:     */       }
/*  415:     */     }
/*  416: 403 */     detalleOrdenDespachoCliente.setListaLecturaBalanza(new ArrayList());
/*  417: 404 */     guardar(this.ordenDespachoCliente);
/*  418: 405 */     return "";
/*  419:     */   }
/*  420:     */   
/*  421:     */   public String limpiar()
/*  422:     */   {
/*  423: 416 */     setEditado(false);
/*  424:     */     
/*  425: 418 */     crearOrdenDespachoCliente();
/*  426:     */     
/*  427: 420 */     return "";
/*  428:     */   }
/*  429:     */   
/*  430:     */   private void crearOrdenDespachoCliente()
/*  431:     */   {
/*  432: 427 */     this.ordenDespachoCliente = new OrdenDespachoCliente();
/*  433: 428 */     this.ordenDespachoCliente.setFecha(FuncionesUtiles.getHoraCero(new Date()));
/*  434: 429 */     this.ordenDespachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  435: 430 */     this.ordenDespachoCliente.setSucursal(AppUtil.getSucursal());
/*  436: 431 */     this.ordenDespachoCliente.setEstado(Estado.ELABORADO);
/*  437: 433 */     if (!getListaDocumentoOrdenDespacho().isEmpty())
/*  438:     */     {
/*  439: 434 */       Documento documento = (Documento)getListaDocumentoOrdenDespacho().get(0);
/*  440: 435 */       this.ordenDespachoCliente.setDocumento(documento);
/*  441:     */     }
/*  442: 437 */     if (this.lecturaBalanza != null)
/*  443:     */     {
/*  444: 438 */       Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/*  445: 439 */       this.lecturaBalanza = null;
/*  446: 440 */       getLecturaBalanza().setDispositivo(dispositivo);
/*  447:     */     }
/*  448: 442 */     setProcesado(false);
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String cargarDatos()
/*  452:     */   {
/*  453: 452 */     return "";
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void procesarCabeceraListener()
/*  457:     */   {
/*  458: 456 */     actualizarDetallesPedidoCliente();
/*  459: 457 */     this.procesado = true;
/*  460: 458 */     guardar(this.ordenDespachoCliente);
/*  461:     */   }
/*  462:     */   
/*  463:     */   public void reiniciarCabeceraListener()
/*  464:     */   {
/*  465: 462 */     for (DetalleOrdenDespachoCliente detalle : this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente())
/*  466:     */     {
/*  467: 463 */       detalle.setEliminado(true);
/*  468: 464 */       for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/*  469: 465 */         lectura.setEliminado(true);
/*  470:     */       }
/*  471:     */     }
/*  472: 468 */     this.procesado = false;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public void actualizarDetallesPedidoCliente()
/*  476:     */   {
/*  477: 472 */     Map<String, DetalleOrdenDespachoCliente> mapa = new HashMap();
/*  478: 473 */     List<DetallePedidoCliente> listaDetalle = this.servicioOrdenDespachoCliente.actualizarDetallesPedidoCliente(this.ordenDespachoCliente);
/*  479: 474 */     for (DetallePedidoCliente detallePedidoCliente : listaDetalle)
/*  480:     */     {
/*  481:     */       DetalleOrdenDespachoCliente detalleOrden;
/*  482: 477 */       if (mapa.containsKey(detallePedidoCliente.getProducto().getId() + "~" + detallePedidoCliente.getCantidadEmbalajeDespacho()))
/*  483:     */       {
/*  484: 478 */         DetalleOrdenDespachoCliente detalleOrden = (DetalleOrdenDespachoCliente)mapa.get(detallePedidoCliente.getProducto().getId() + "~" + detallePedidoCliente.getCantidadEmbalajeDespacho());
/*  485: 479 */         BigDecimal cantidadPedido = detalleOrden.getCantidadPedido().add(detallePedidoCliente.getCantidadPorDespachar());
/*  486: 480 */         detalleOrden.setCantidadPedido(cantidadPedido);
/*  487:     */       }
/*  488:     */       else
/*  489:     */       {
/*  490: 482 */         detalleOrden = new DetalleOrdenDespachoCliente();
/*  491: 483 */         detalleOrden.setProducto(detallePedidoCliente.getProducto());
/*  492: 484 */         detalleOrden.setIndicadorManejoPeso(detallePedidoCliente.getProducto().isIndicadorManejoPeso());
/*  493: 485 */         detalleOrden.setIdOrganizacion(this.ordenDespachoCliente.getIdOrganizacion());
/*  494: 486 */         detalleOrden.setIdSucursal(this.ordenDespachoCliente.getSucursal().getId());
/*  495: 487 */         BigDecimal cantidadPedido = detallePedidoCliente.getCantidadPorDespachar();
/*  496: 488 */         detalleOrden.setCantidadPedido(cantidadPedido);
/*  497: 489 */         detalleOrden.setCantidadEmbalajeDespacho(detallePedidoCliente.getCantidadEmbalajeDespacho());
/*  498: 490 */         detalleOrden.setOrdenDespachoCliente(this.ordenDespachoCliente);
/*  499: 491 */         asignarBodega(detalleOrden, detallePedidoCliente.getProducto());
/*  500:     */       }
/*  501: 493 */       detalleOrden.setEditado(true);
/*  502: 494 */       DetalleOrdenDespachoClientePedidoCliente detalleOrdenDespachoClientePedidoCliente = new DetalleOrdenDespachoClientePedidoCliente();
/*  503: 495 */       detalleOrdenDespachoClientePedidoCliente.setIdOrganizacion(detalleOrden.getIdOrganizacion());
/*  504: 496 */       detalleOrdenDespachoClientePedidoCliente.setIdSucursal(detalleOrden.getIdSucursal());
/*  505: 497 */       detalleOrdenDespachoClientePedidoCliente.setDetalleOrdenDespachoCliente(detalleOrden);
/*  506: 498 */       detalleOrdenDespachoClientePedidoCliente.setDetallePedidoCliente(detallePedidoCliente);
/*  507: 499 */       detalleOrden.getListaDetallePedidoCliente().add(detalleOrdenDespachoClientePedidoCliente);
/*  508:     */       
/*  509: 501 */       mapa.put(detalleOrden.getProducto().getId() + "~" + detallePedidoCliente.getCantidadEmbalajeDespacho(), detalleOrden);
/*  510:     */     }
/*  511: 503 */     for (DetalleOrdenDespachoCliente detalleOrden : mapa.values()) {
/*  512: 504 */       if (detalleOrden.getBodega() != null)
/*  513:     */       {
/*  514: 505 */         calcularCantidad(detalleOrden);
/*  515: 506 */         if (this.ordenDespachoCliente.getTipoOrdenDespacho().getIndicadorAplicaPorcientoAdicionalPedidos().booleanValue()) {
/*  516: 507 */           aumentarPorcientoPedido(detalleOrden);
/*  517:     */         }
/*  518: 509 */         this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().add(detalleOrden);
/*  519:     */       }
/*  520:     */     }
/*  521:     */   }
/*  522:     */   
/*  523:     */   private void aumentarPorcientoPedido(DetalleOrdenDespachoCliente detalleOrden)
/*  524:     */   {
/*  525: 515 */     BigDecimal porcientoPermitido = new BigDecimal(detalleOrden.getProducto().getPorCientoDespachoSuperaPedido());
/*  526: 516 */     BigDecimal cantidadPedido = detalleOrden.getCantidadPedido();
/*  527: 517 */     BigDecimal cantidadAdicional = porcientoPermitido.multiply(cantidadPedido).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
/*  528: 518 */     detalleOrden.setCantidadPedido(cantidadPedido.add(cantidadAdicional)
/*  529: 519 */       .setScale(detalleOrden.getProducto().getUnidadVenta().getNumeroDecimales().intValue(), RoundingMode.HALF_UP));
/*  530:     */   }
/*  531:     */   
/*  532:     */   private boolean asignarBodega(DetalleOrdenDespachoCliente detalle, Producto producto)
/*  533:     */   {
/*  534: 523 */     Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(producto, Integer.valueOf(AppUtil.getSucursal().getId()));
/*  535: 524 */     detalle.setBodega(bodegaTrabajo);
/*  536: 525 */     if (detalle.getBodega() == null)
/*  537:     */     {
/*  538: 526 */       addErrorMessage(getLanguageController().getMensaje("msg_info_parametrizar_bodega_trabajo") + " ( " + (detalle
/*  539: 527 */         .getProducto() != null ? detalle.getProducto().getNombre() : "") + " - " + AppUtil.getSucursal().getNombre() + " ).");
/*  540: 528 */       return false;
/*  541:     */     }
/*  542: 530 */     return true;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void cargarProducto()
/*  546:     */   {
/*  547:     */     try
/*  548:     */     {
/*  549: 539 */       Producto producto = getListaProductoBean().getProducto();
/*  550: 540 */       if (producto != null)
/*  551:     */       {
/*  552: 541 */         crearDetalle(producto, producto.getTraCantidad(), null, false, null);
/*  553: 542 */         getListaProductoBean().setProducto(null);
/*  554: 543 */         getListaProductoBean().setSaldoProductoLote(null);
/*  555:     */       }
/*  556:     */     }
/*  557:     */     catch (Exception e)
/*  558:     */     {
/*  559: 547 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  560: 548 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  561:     */     }
/*  562:     */     finally
/*  563:     */     {
/*  564: 550 */       getListaProductoBean().setProducto(null);
/*  565: 551 */       getListaProductoBean().setSaldoProductoLote(null);
/*  566:     */     }
/*  567:     */   }
/*  568:     */   
/*  569:     */   public List<Producto> autocompletarProductos(String consulta)
/*  570:     */   {
/*  571: 562 */     Map<String, String> filtros = new HashMap();
/*  572: 563 */     filtros.put("indicadorPesoBalanza", String.valueOf(true));
/*  573: 564 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public List<Lote> autocompletarLotes(String consulta)
/*  577:     */   {
/*  578: 568 */     DetalleOrdenDespachoCliente detalleOrdenDespachoCliente = (DetalleOrdenDespachoCliente)this.dtDetalleOrdenDespachoCliente.getRowData();
/*  579: 569 */     return this.servicioLote.autocompletarLote(detalleOrdenDespachoCliente.getProducto(), consulta);
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void capturarPesoListener()
/*  583:     */   {
/*  584: 573 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/*  585:     */       try
/*  586:     */       {
/*  587: 575 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, true);
/*  588:     */       }
/*  589:     */       catch (AS2Exception ex)
/*  590:     */       {
/*  591: 577 */         JsfUtil.addErrorMessage(ex, "");
/*  592:     */       }
/*  593:     */     }
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void agregarPesoListener()
/*  597:     */   {
/*  598: 583 */     if ((this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0))
/*  599:     */     {
/*  600:     */       try
/*  601:     */       {
/*  602: 585 */         this.servicioMarcacionDispositivo.validarCantidad(this.lecturaBalanza);
/*  603:     */       }
/*  604:     */       catch (AS2Exception e)
/*  605:     */       {
/*  606: 587 */         JsfUtil.addErrorMessage(e, "");
/*  607: 588 */         return;
/*  608:     */       }
/*  609: 590 */       boolean existeProducto = false;
/*  610:     */       
/*  611: 592 */       BigDecimal cantidadProducto = this.lecturaBalanza.getCantidad() == null ? this.lecturaBalanza.getPesoNeto() : new BigDecimal(this.lecturaBalanza.getCantidad().intValue());
/*  612:     */       
/*  613: 594 */       DetalleOrdenDespachoCliente detalleOrden = getDetalleOrdenDespachoCliente();
/*  614:     */       
/*  615:     */ 
/*  616:     */ 
/*  617:     */ 
/*  618:     */ 
/*  619:     */ 
/*  620:     */ 
/*  621: 602 */       DetalleOrdenDespachoCliente detalleOrdenBDD = this.servicioOrdenDespachoCliente.buscarPorId(detalleOrden);
/*  622: 603 */       if (detalleOrdenBDD != null) {
/*  623: 604 */         setearCantidadesBDD(detalleOrden, detalleOrdenBDD);
/*  624:     */       }
/*  625: 607 */       BigDecimal cantidadAnterior = detalleOrden.getCantidad();
/*  626: 608 */       detalleOrden.setCantidad(detalleOrden.getCantidad().add(cantidadProducto));
/*  627:     */       
/*  628: 610 */       BigDecimal sumatoriaPesoMateriaprima = detalleOrden.getPesoMateriaPrima().add(this.lecturaBalanza.getPesoNeto());
/*  629: 611 */       detalleOrden.setPesoMateriaPrima(sumatoriaPesoMateriaprima);
/*  630: 612 */       existeProducto = true;
/*  631:     */       try
/*  632:     */       {
/*  633: 614 */         this.servicioOrdenDespachoCliente.validarAgregarPeso(detalleOrden);
/*  634: 615 */         this.lecturaBalanza.setDetalleOrdenDespachoCliente(detalleOrden);
/*  635: 616 */         detalleOrden.getListaLecturaBalanza().add(this.lecturaBalanza);
/*  636: 617 */         detalleOrden.setIndicadorAutomatico(true);
/*  637:     */       }
/*  638:     */       catch (AS2Exception e)
/*  639:     */       {
/*  640: 619 */         detalleOrden.setCantidad(detalleOrdenBDD == null ? cantidadAnterior : detalleOrdenBDD.getCantidad());
/*  641: 620 */         detalleOrden.setEditado(false);
/*  642: 621 */         JsfUtil.addErrorMessage(e, "");
/*  643:     */       }
/*  644:     */       catch (Exception e)
/*  645:     */       {
/*  646: 623 */         detalleOrden.setCantidad(detalleOrdenBDD == null ? cantidadAnterior : detalleOrdenBDD.getCantidad());
/*  647: 624 */         detalleOrden.setEditado(false);
/*  648:     */       }
/*  649: 626 */       calcularCantidadUnidadDespacho(detalleOrden);
/*  650: 631 */       if (!existeProducto) {
/*  651: 632 */         crearDetalle(this.lecturaBalanza.getProducto(), cantidadProducto, this.lecturaBalanza, true, this.lecturaBalanza.getPesoNeto());
/*  652:     */       }
/*  653: 635 */       guardar(this.ordenDespachoCliente);
/*  654: 636 */       Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/*  655: 637 */       this.lecturaBalanza = null;
/*  656: 638 */       getLecturaBalanza().setDispositivo(dispositivo);
/*  657:     */     }
/*  658:     */   }
/*  659:     */   
/*  660:     */   private void setearCantidadesBDD(DetalleOrdenDespachoCliente detalleOrden, DetalleOrdenDespachoCliente detalleOrdenBDD)
/*  661:     */   {
/*  662: 643 */     detalleOrden.setCantidad(detalleOrdenBDD.getCantidad());
/*  663: 644 */     detalleOrden.setCantidadEmbalajeDespacho(detalleOrdenBDD.getCantidadEmbalajeDespacho());
/*  664: 645 */     detalleOrden.setCantidadPedido(detalleOrdenBDD.getCantidadPedido());
/*  665: 646 */     detalleOrden.setCantidadUnidadDespacho(detalleOrdenBDD.getCantidadUnidadDespacho());
/*  666: 647 */     detalleOrden.setPesoMateriaPrima(detalleOrdenBDD.getPesoMateriaPrima());
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void crearDetalle(Producto producto, BigDecimal cantidad, LecturaBalanza lectura, boolean automatico, BigDecimal pesoMateriaPrima)
/*  670:     */   {
/*  671: 651 */     producto = this.servicioProducto.cargaDetalle(producto.getId());
/*  672:     */     
/*  673: 653 */     DetalleOrdenDespachoCliente detalleOrdenDespachoCliente = new DetalleOrdenDespachoCliente();
/*  674: 654 */     detalleOrdenDespachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  675: 655 */     detalleOrdenDespachoCliente.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  676: 656 */     detalleOrdenDespachoCliente.setProducto(producto);
/*  677: 657 */     detalleOrdenDespachoCliente.setIndicadorAutomatico(automatico);
/*  678: 658 */     detalleOrdenDespachoCliente.setPesoMateriaPrima(pesoMateriaPrima);
/*  679:     */     
/*  680: 660 */     detalleOrdenDespachoCliente.setOrdenDespachoCliente(this.ordenDespachoCliente);
/*  681: 661 */     detalleOrdenDespachoCliente.setCantidad(cantidad.setScale(2, RoundingMode.HALF_UP));
/*  682: 662 */     detalleOrdenDespachoCliente.setIndicadorManejoPeso(producto.isIndicadorManejoPeso());
/*  683: 663 */     if (asignarBodega(detalleOrdenDespachoCliente, producto))
/*  684:     */     {
/*  685: 664 */       this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().add(0, detalleOrdenDespachoCliente);
/*  686: 665 */       if (lectura != null)
/*  687:     */       {
/*  688: 666 */         lectura.setDetalleOrdenDespachoCliente(detalleOrdenDespachoCliente);
/*  689: 667 */         detalleOrdenDespachoCliente.getListaLecturaBalanza().add(lectura);
/*  690:     */       }
/*  691: 669 */       calcularCantidadUnidadDespacho(detalleOrdenDespachoCliente);
/*  692:     */     }
/*  693:     */   }
/*  694:     */   
/*  695:     */   public List<DetalleOrdenDespachoCliente> getListaDetalleOrdenDespachoCliente()
/*  696:     */   {
/*  697: 679 */     List<DetalleOrdenDespachoCliente> lista = new ArrayList();
/*  698: 680 */     for (int i = 0; i < this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().size(); i++) {
/*  699: 682 */       if (!((DetalleOrdenDespachoCliente)this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().get(i)).isEliminado()) {
/*  700: 683 */         lista.add(this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente().get(i));
/*  701:     */       }
/*  702:     */     }
/*  703: 687 */     return lista;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public OrdenDespachoCliente getOrdenDespachoCliente()
/*  707:     */   {
/*  708: 691 */     return this.ordenDespachoCliente;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void setOrdenDespachoCliente(OrdenDespachoCliente ordenDespachoCliente)
/*  712:     */   {
/*  713: 695 */     this.ordenDespachoCliente = ordenDespachoCliente;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public LazyDataModel<OrdenDespachoCliente> getListaOrdenDespachoCliente()
/*  717:     */   {
/*  718: 699 */     return this.listaOrdenDespachoCliente;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void setListaOrdenDespachoCliente(LazyDataModel<OrdenDespachoCliente> listaOrdenDespachoCliente)
/*  722:     */   {
/*  723: 703 */     this.listaOrdenDespachoCliente = listaOrdenDespachoCliente;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public DataTable getDtOrdenDespachoCliente()
/*  727:     */   {
/*  728: 707 */     return this.dtOrdenDespachoCliente;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void setDtOrdenDespachoCliente(DataTable dtOrdenDespachoCliente)
/*  732:     */   {
/*  733: 711 */     this.dtOrdenDespachoCliente = dtOrdenDespachoCliente;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public DataTable getDtDetalleOrdenDespachoCliente()
/*  737:     */   {
/*  738: 715 */     return this.dtDetalleOrdenDespachoCliente;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public void setDtDetalleOrdenDespachoCliente(DataTable dtDetalleOrdenDespachoCliente)
/*  742:     */   {
/*  743: 719 */     this.dtDetalleOrdenDespachoCliente = dtDetalleOrdenDespachoCliente;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public DetalleOrdenDespachoCliente getDetalleOrdenDespachoCliente()
/*  747:     */   {
/*  748: 723 */     return this.detalleOrdenDespachoCliente;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public void setDetalleOrdenDespachoCliente(DetalleOrdenDespachoCliente detalleOrdenDespachoCliente)
/*  752:     */   {
/*  753: 727 */     this.detalleOrdenDespachoCliente = detalleOrdenDespachoCliente;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setListaDocumentoOrdenDespacho(List<Documento> listaDocumentoOrdenDespacho)
/*  757:     */   {
/*  758: 731 */     this.listaDocumentoOrdenDespacho = listaDocumentoOrdenDespacho;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public LecturaBalanza getLecturaBalanza()
/*  762:     */   {
/*  763: 735 */     if (this.lecturaBalanza == null)
/*  764:     */     {
/*  765: 736 */       this.lecturaBalanza = new LecturaBalanza();
/*  766: 737 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  767: 738 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/*  768: 739 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/*  769:     */     }
/*  770: 741 */     return this.lecturaBalanza;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/*  774:     */   {
/*  775: 745 */     this.lecturaBalanza = lecturaBalanza;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public List<UnidadManejo> getListaUnidadManejo()
/*  779:     */   {
/*  780: 752 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/*  781:     */     {
/*  782: 753 */       this.listaUnidadManejo = new ArrayList();
/*  783: 754 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/*  784: 755 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/*  785:     */       }
/*  786:     */     }
/*  787: 758 */     return this.listaUnidadManejo;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/*  791:     */   {
/*  792: 766 */     this.listaUnidadManejo = listaUnidadManejo;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public List<UnidadManejo> getListaPallet()
/*  796:     */   {
/*  797: 770 */     if (this.listaPallet == null)
/*  798:     */     {
/*  799: 771 */       Map<String, String> filters = new HashMap();
/*  800: 772 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  801: 773 */       filters.put("activo", "true");
/*  802: 774 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/*  803: 775 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/*  804:     */     }
/*  805: 777 */     return this.listaPallet;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void setListaPallet(List<UnidadManejo> listaPallet)
/*  809:     */   {
/*  810: 781 */     this.listaPallet = listaPallet;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public DataTable getDtDetallePedidoCliente()
/*  814:     */   {
/*  815: 785 */     return this.dtDetallePedidoCliente;
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void setDtDetallePedidoCliente(DataTable dtDetallePedidoCliente)
/*  819:     */   {
/*  820: 789 */     this.dtDetallePedidoCliente = dtDetallePedidoCliente;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public boolean isProcesado()
/*  824:     */   {
/*  825: 793 */     return this.procesado;
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void setProcesado(boolean procesado)
/*  829:     */   {
/*  830: 797 */     this.procesado = procesado;
/*  831:     */   }
/*  832:     */   
/*  833:     */   public List<TipoOrdenDespacho> getListaTipoOrdenDespacho()
/*  834:     */   {
/*  835: 801 */     if (this.listaTipoOrdenDespacho == null)
/*  836:     */     {
/*  837: 802 */       Map<String, String> filtros = new HashMap();
/*  838: 803 */       filtros.put("idOrganizacion", AppUtil.getOrganizacion().getId() + "");
/*  839: 804 */       filtros.put("activo", "true");
/*  840: 805 */       this.listaTipoOrdenDespacho = this.servicioTipoOrdenDespacho.obtenerListaCombo(TipoOrdenDespacho.class, "nombre", true, filtros);
/*  841:     */     }
/*  842: 807 */     return this.listaTipoOrdenDespacho;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public void setListaTipoOrdenDespacho(List<TipoOrdenDespacho> listaTipoOrdenDespacho)
/*  846:     */   {
/*  847: 811 */     this.listaTipoOrdenDespacho = listaTipoOrdenDespacho;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/*  851:     */   {
/*  852: 816 */     DetalleOrdenDespachoCliente detalleOrdenBDD = this.servicioOrdenDespachoCliente.buscarPorId(lectura.getDetalleOrdenDespachoCliente());
/*  853: 817 */     if (detalleOrdenBDD != null) {
/*  854: 818 */       setearCantidadesBDD(lectura.getDetalleOrdenDespachoCliente(), detalleOrdenBDD);
/*  855:     */     }
/*  856: 822 */     BigDecimal cantidad = lectura.getCantidad() == null ? lectura.getDetalleOrdenDespachoCliente().getCantidad().subtract(lectura.getPesoNeto()) : lectura.getDetalleOrdenDespachoCliente().getCantidad().subtract(new BigDecimal(lectura.getCantidad().intValue()));
/*  857: 823 */     lectura.setEliminado(true);
/*  858: 824 */     lectura.getDetalleOrdenDespachoCliente().setCantidad(cantidad);
/*  859: 825 */     calcularCantidadUnidadDespacho(lectura.getDetalleOrdenDespachoCliente());
/*  860: 827 */     if ((lectura.getDetalleOrdenDespachoCliente().getPesoMateriaPrima() != null) && 
/*  861: 828 */       (lectura.getDetalleOrdenDespachoCliente().getPesoMateriaPrima().compareTo(BigDecimal.ZERO) > 0))
/*  862:     */     {
/*  863: 830 */       BigDecimal nuevoPesoMP = lectura.getDetalleOrdenDespachoCliente().getPesoMateriaPrima().subtract(lectura.getPesoNeto());
/*  864: 831 */       lectura.getDetalleOrdenDespachoCliente().setPesoMateriaPrima(nuevoPesoMP);
/*  865:     */     }
/*  866: 834 */     boolean encontre = false;
/*  867: 835 */     for (LecturaBalanza lb : lectura.getDetalleOrdenDespachoCliente().getListaLecturaBalanza()) {
/*  868: 836 */       if (!lb.isEliminado())
/*  869:     */       {
/*  870: 837 */         encontre = true;
/*  871: 838 */         break;
/*  872:     */       }
/*  873:     */     }
/*  874: 842 */     if (!encontre) {
/*  875: 843 */       lectura.getDetalleOrdenDespachoCliente().setIndicadorAutomatico(false);
/*  876:     */     }
/*  877: 845 */     guardar(lectura.getDetalleOrdenDespachoCliente().getOrdenDespachoCliente());
/*  878:     */   }
/*  879:     */   
/*  880:     */   public List<LecturaBalanza> getListaLecturaBalanza()
/*  881:     */   {
/*  882: 849 */     List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/*  883: 850 */     for (DetalleOrdenDespachoCliente detalle : this.ordenDespachoCliente.getListaDetalleOrdenDespachoCliente()) {
/*  884: 851 */       for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/*  885: 852 */         if (!lectura.isEliminado()) {
/*  886: 853 */           listaLecturaBalanza.add(lectura);
/*  887:     */         }
/*  888:     */       }
/*  889:     */     }
/*  890: 858 */     return listaLecturaBalanza;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public DataTable getDtLecturaBalanza()
/*  894:     */   {
/*  895: 862 */     return this.dtLecturaBalanza;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/*  899:     */   {
/*  900: 866 */     this.dtLecturaBalanza = dtLecturaBalanza;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public void calcularCantidadUnidadDespacho(DetalleOrdenDespachoCliente detalle)
/*  904:     */   {
/*  905: 870 */     if ((detalle.getCantidadEmbalajeDespacho() != null) && (detalle.getCantidadEmbalajeDespacho().compareTo(BigDecimal.ZERO) != 0))
/*  906:     */     {
/*  907: 871 */       BigDecimal cantidadUnidadDespacho = detalle.getCantidad().divide(detalle.getCantidadEmbalajeDespacho(), 2, RoundingMode.HALF_UP);
/*  908: 872 */       detalle.setCantidadUnidadDespacho(cantidadUnidadDespacho);
/*  909:     */     }
/*  910:     */     else
/*  911:     */     {
/*  912: 874 */       detalle.setCantidadUnidadDespacho(detalle.getCantidad());
/*  913:     */     }
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void calcularCantidad(DetalleOrdenDespachoCliente detalleOrden)
/*  917:     */   {
/*  918: 879 */     DetalleOrdenDespachoCliente detalleOrdenBDD = null;
/*  919: 880 */     if (detalleOrden.getId() != 0) {
/*  920: 881 */       detalleOrdenBDD = this.servicioOrdenDespachoCliente.buscarPorId(detalleOrden);
/*  921:     */     }
/*  922: 884 */     if (detalleOrden.getCantidadEmbalajeDespacho() != null)
/*  923:     */     {
/*  924: 885 */       BigDecimal cantidad = detalleOrden.getCantidadUnidadDespacho().multiply(detalleOrden.getCantidadEmbalajeDespacho()).setScale(2, RoundingMode.HALF_UP);
/*  925:     */       
/*  926: 887 */       detalleOrden.setCantidad(cantidad);
/*  927:     */     }
/*  928:     */     else
/*  929:     */     {
/*  930: 889 */       detalleOrden.setCantidad(detalleOrden.getCantidadUnidadDespacho());
/*  931:     */     }
/*  932:     */     try
/*  933:     */     {
/*  934: 893 */       this.servicioOrdenDespachoCliente.validarAgregarPeso(detalleOrden);
/*  935: 896 */       if (detalleOrden.getId() != 0) {
/*  936: 897 */         this.servicioDetalleOrdenDespachoCliente.guardar(detalleOrden);
/*  937:     */       }
/*  938:     */     }
/*  939:     */     catch (AS2Exception e)
/*  940:     */     {
/*  941: 900 */       if (detalleOrdenBDD != null) {
/*  942: 901 */         setearCantidadesBDD(detalleOrden, detalleOrdenBDD);
/*  943:     */       }
/*  944: 903 */       JsfUtil.addErrorMessage(e, "");
/*  945: 904 */       return;
/*  946:     */     }
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void actualizarReparticionPedidos()
/*  950:     */   {
/*  951:     */     try
/*  952:     */     {
/*  953: 913 */       this.servicioOrdenDespachoCliente.actualizarPedidosClientePorDetalle(this.ordenDespachoCliente);
/*  954: 914 */       setearTablas();
/*  955:     */     }
/*  956:     */     catch (AS2Exception e)
/*  957:     */     {
/*  958: 916 */       JsfUtil.addErrorMessage(e, "");
/*  959:     */     }
/*  960:     */     catch (Exception e)
/*  961:     */     {
/*  962: 918 */       e.printStackTrace();
/*  963: 919 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar") + " " + e.getMessage());
/*  964: 920 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  965:     */     }
/*  966:     */   }
/*  967:     */   
/*  968:     */   public List<DespachoCliente> getListaDespachoCliente()
/*  969:     */   {
/*  970: 925 */     return this.listaDespachoCliente;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public void setListaDespachoCliente(List<DespachoCliente> listaDespachoCliente)
/*  974:     */   {
/*  975: 929 */     this.listaDespachoCliente = listaDespachoCliente;
/*  976:     */   }
/*  977:     */   
/*  978:     */   public DataTable getDtDespachoCliente()
/*  979:     */   {
/*  980: 933 */     return this.dtDespachoCliente;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void setDtDespachoCliente(DataTable dtDespachoCliente)
/*  984:     */   {
/*  985: 937 */     this.dtDespachoCliente = dtDespachoCliente;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public DataTable getDtDetalleDespachoCliente()
/*  989:     */   {
/*  990: 941 */     return this.dtDetalleDespachoCliente;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setDtDetalleDespachoCliente(DataTable dtDetalleDespachoCliente)
/*  994:     */   {
/*  995: 945 */     this.dtDetalleDespachoCliente = dtDetalleDespachoCliente;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public void cerrar()
/*  999:     */   {
/* 1000:     */     try
/* 1001:     */     {
/* 1002: 951 */       this.ordenDespachoCliente = this.servicioOrdenDespachoCliente.cargarDetalle(Integer.valueOf(this.ordenDespachoCliente.getId()));
/* 1003: 952 */       this.servicioOrdenDespachoCliente.cerrar(this.ordenDespachoCliente);
/* 1004:     */     }
/* 1005:     */     catch (ExcepcionAS2Inventario e)
/* 1006:     */     {
/* 1007: 954 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1008: 955 */       LOG.error("ERROR AL CERRAR ORDEN DESPACHO", e);
/* 1009:     */     }
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public void cargarProductoSeleccionadoPesa()
/* 1013:     */   {
/* 1014: 960 */     if (this.lecturaBalanza != null) {
/* 1015: 961 */       if (this.detalleOrdenDespachoCliente.getProducto().isIndicadorPesoBalanza())
/* 1016:     */       {
/* 1017: 963 */         this.lecturaBalanza.setProducto(this.servicioProducto.buscarPorId(this.detalleOrdenDespachoCliente.getProducto().getId()));
/* 1018:     */       }
/* 1019:     */       else
/* 1020:     */       {
/* 1021: 965 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 1022: 966 */         this.lecturaBalanza = null;
/* 1023: 967 */         getLecturaBalanza().setDispositivo(dispositivo);
/* 1024:     */       }
/* 1025:     */     }
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public void calcularCantidad()
/* 1029:     */   {
/* 1030: 973 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 1031: 974 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 1032:     */     }
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public List<TipoPresentacionProducto> getListaTipoPresentacionProducto()
/* 1036:     */   {
/* 1037: 979 */     if (this.listaTipoPresentacionProducto == null)
/* 1038:     */     {
/* 1039: 980 */       Map<String, String> filtros = new HashMap();
/* 1040: 981 */       agregarFiltroOrganizacion(filtros);
/* 1041: 982 */       filtros.put("activo", "true");
/* 1042: 983 */       this.listaTipoPresentacionProducto = this.servicioTipoPresentacionProducto.obtenerListaCombo(TipoPresentacionProducto.class, "nombre", true, filtros);
/* 1043:     */     }
/* 1044: 986 */     return this.listaTipoPresentacionProducto;
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public void setListaTipoPresentacionProducto(List<TipoPresentacionProducto> listaTipoPresentacionProducto)
/* 1048:     */   {
/* 1049: 990 */     this.listaTipoPresentacionProducto = listaTipoPresentacionProducto;
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public TipoPresentacionProducto getTipoPresentacionProducto()
/* 1053:     */   {
/* 1054: 994 */     return this.tipoPresentacionProducto;
/* 1055:     */   }
/* 1056:     */   
/* 1057:     */   public void setTipoPresentacionProducto(TipoPresentacionProducto tipoPresentacionProducto)
/* 1058:     */   {
/* 1059: 998 */     this.tipoPresentacionProducto = tipoPresentacionProducto;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public List<Dispositivo> getListaDispositivo()
/* 1063:     */   {
/* 1064:1002 */     if (this.listaDispositivo == null)
/* 1065:     */     {
/* 1066:1003 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1067:1004 */       filtros.put("activo", "true");
/* 1068:1005 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 1069:     */     }
/* 1070:1007 */     return this.listaDispositivo;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public boolean isUsuarioAdministrador()
/* 1074:     */   {
/* 1075:1011 */     if (this.usuarioAdministrador == null)
/* 1076:     */     {
/* 1077:1012 */       Integer idUsuario = Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 1078:1013 */       EntidadUsuario usuario = this.servicioUsuario.buscarPorId(idUsuario);
/* 1079:1014 */       this.usuarioAdministrador = Boolean.valueOf(usuario.isIndicadorAdministrador());
/* 1080:     */     }
/* 1081:1016 */     return this.usuarioAdministrador.booleanValue();
/* 1082:     */   }
/* 1083:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.OrdenDespachoClienteBean
 * JD-Core Version:    0.7.0.1
 */