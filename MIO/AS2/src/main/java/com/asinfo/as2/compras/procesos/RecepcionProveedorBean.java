/*    1:     */ package com.asinfo.as2.compras.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion;
/*    5:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*    6:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*    7:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    9:     */ import com.asinfo.as2.controller.LanguageController;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   11:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   12:     */ import com.asinfo.as2.entities.Atributo;
/*   13:     */ import com.asinfo.as2.entities.Bodega;
/*   14:     */ import com.asinfo.as2.entities.CuentaContable;
/*   15:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   16:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   17:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   18:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   19:     */ import com.asinfo.as2.entities.Dispositivo;
/*   20:     */ import com.asinfo.as2.entities.Documento;
/*   21:     */ import com.asinfo.as2.entities.Empresa;
/*   22:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   23:     */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*   24:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   25:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   26:     */ import com.asinfo.as2.entities.Lote;
/*   27:     */ import com.asinfo.as2.entities.Organizacion;
/*   28:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   29:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   30:     */ import com.asinfo.as2.entities.Producto;
/*   31:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   32:     */ import com.asinfo.as2.entities.Sucursal;
/*   33:     */ import com.asinfo.as2.entities.UnidadManejo;
/*   34:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   35:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   36:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   37:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   38:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   39:     */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*   40:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   41:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   42:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*   43:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   44:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   45:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   46:     */ import com.asinfo.as2.inventario.procesos.controller.MovimientoInventarioBaseBean;
/*   47:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*   48:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   49:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   50:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   51:     */ import com.asinfo.as2.util.AppUtil;
/*   52:     */ import com.asinfo.as2.util.RutaArchivo;
/*   53:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   54:     */ import com.asinfo.as2.utils.JsfUtil;
/*   55:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   56:     */ import com.asinfo.as2.web.controller.SesionControler;
/*   57:     */ import java.io.BufferedInputStream;
/*   58:     */ import java.io.IOException;
/*   59:     */ import java.io.InputStream;
/*   60:     */ import java.math.BigDecimal;
/*   61:     */ import java.math.RoundingMode;
/*   62:     */ import java.util.ArrayList;
/*   63:     */ import java.util.Collection;
/*   64:     */ import java.util.Collections;
/*   65:     */ import java.util.Comparator;
/*   66:     */ import java.util.Date;
/*   67:     */ import java.util.HashMap;
/*   68:     */ import java.util.List;
/*   69:     */ import java.util.Map;
/*   70:     */ import javax.annotation.PostConstruct;
/*   71:     */ import javax.ejb.EJB;
/*   72:     */ import javax.faces.bean.ManagedBean;
/*   73:     */ import javax.faces.bean.ManagedProperty;
/*   74:     */ import javax.faces.bean.ViewScoped;
/*   75:     */ import javax.faces.component.html.HtmlInputText;
/*   76:     */ import javax.faces.context.FacesContext;
/*   77:     */ import javax.faces.context.PartialViewContext;
/*   78:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   79:     */ import org.apache.log4j.Logger;
/*   80:     */ import org.primefaces.component.datatable.DataTable;
/*   81:     */ import org.primefaces.context.RequestContext;
/*   82:     */ import org.primefaces.event.FileUploadEvent;
/*   83:     */ import org.primefaces.event.SelectEvent;
/*   84:     */ import org.primefaces.model.LazyDataModel;
/*   85:     */ import org.primefaces.model.SortOrder;
/*   86:     */ import org.primefaces.model.UploadedFile;
/*   87:     */ 
/*   88:     */ @ManagedBean
/*   89:     */ @ViewScoped
/*   90:     */ public class RecepcionProveedorBean
/*   91:     */   extends MovimientoInventarioBaseBean
/*   92:     */ {
/*   93:     */   @EJB
/*   94:     */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*   95:     */   private static final long serialVersionUID = -1988693000468372483L;
/*   96:     */   private RecepcionProveedor recepcionProveedor;
/*   97:     */   private LazyDataModel<RecepcionProveedor> listaRecepcionProveedor;
/*   98:     */   private InventarioProducto inventarioProducto;
/*   99:     */   private boolean indicadorImportacion;
/*  100:     */   private List<Documento> listaDocumento;
/*  101:     */   private List<Bodega> listaBodega;
/*  102:     */   private List<Empresa> listaEmpresaProveedor;
/*  103:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  104: 121 */   private List<PedidoProveedor> listaPedidoProveedor = new ArrayList();
/*  105:     */   private List<Dispositivo> listaDispositivo;
/*  106:     */   private Lote loteCrear;
/*  107:     */   private DetalleRecepcionProveedor detalleRecepcionProveedorSeleccionado;
/*  108:     */   private Producto productoCargaArchivo;
/*  109:     */   private List<DetalleRecepcionProveedor> listaEdicionDetalleRecepcionProveedor;
/*  110:     */   protected List<LecturaBalanza> listaLecturaBalanza;
/*  111:     */   private DataTable dtRecepcionProveedor;
/*  112:     */   private DataTable dtDetalleRecepcionProveedor;
/*  113:     */   private DataTable dtDespachoPedidoFactura;
/*  114:     */   private DataTable dtInventarioProductoLote;
/*  115: 135 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  116:     */   private DataTable dtEdicionRecepcionProveedor;
/*  117:     */   protected DataTable dtLecturaBalanza;
/*  118:     */   private Integer idFacturaProveedor;
/*  119:     */   private Integer idRecepcionProveedor;
/*  120:     */   private boolean indicadorPendientesPorFacturar;
/*  121:     */   private String codigoCabecera;
/*  122: 145 */   private Integer opcionCarga = Integer.valueOf(0);
/*  123: 146 */   private BigDecimal cantidadPesoLote = BigDecimal.ZERO;
/*  124: 147 */   private Integer numeroLotes = Integer.valueOf(1);
/*  125: 148 */   private boolean generarLotesAutomaticamente = true;
/*  126:     */   private InputStream inputLote;
/*  127:     */   private int lineasLotesArchivo;
/*  128:     */   private BigDecimal totalCantidadLoteArchivo;
/*  129:     */   private LecturaBalanza lecturaBalanza;
/*  130:     */   private List<UnidadManejo> listaUnidadManejo;
/*  131:     */   private List<UnidadManejo> listaPallet;
/*  132:     */   private Boolean mostrarBalanza;
/*  133: 161 */   private boolean indicadorFacturado = false;
/*  134:     */   @EJB
/*  135:     */   private ServicioRecepcionProveedor servicioRecepcionProveedor;
/*  136:     */   @EJB
/*  137:     */   private ServicioEmpresa servicioEmpresa;
/*  138:     */   @EJB
/*  139:     */   private ServicioProducto servicioProducto;
/*  140:     */   @EJB
/*  141:     */   private ServicioDocumento servicioDocumento;
/*  142:     */   @EJB
/*  143:     */   private ServicioPedidoProveedor servicioPedidoProveedor;
/*  144:     */   @EJB
/*  145:     */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  146:     */   @EJB
/*  147:     */   private ServicioFacturaProveedorImportacion servicioFacturaProveedorImportacion;
/*  148:     */   @EJB
/*  149:     */   private ServicioLote servicioLote;
/*  150:     */   @EJB
/*  151:     */   private ServicioOrganizacion servicioOrganizacion;
/*  152:     */   @EJB
/*  153:     */   private ServicioInventarioProducto servicioInventarioProducto;
/*  154:     */   @EJB
/*  155:     */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  156:     */   @EJB
/*  157:     */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  158:     */   @EJB
/*  159:     */   private ServicioAtributo servicioAtributo;
/*  160:     */   private BigDecimal totalCostoAnterior;
/*  161:     */   private BigDecimal totalCostoActual;
/*  162:     */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  163:     */   private boolean activaDiferencia;
/*  164:     */   @ManagedProperty("#{listaCuentaContableBean}")
/*  165:     */   private ListaCuentaContableBean listaCuentaContableBean;
/*  166:     */   @ManagedProperty("#{sesionControler}")
/*  167:     */   private SesionControler sesionControler;
/*  168:     */   
/*  169:     */   @PostConstruct
/*  170:     */   public void init()
/*  171:     */   {
/*  172: 204 */     getListaProductoBean().setIndicadorCompra(true);
/*  173: 205 */     getListaProductoBean().setActivo(true);
/*  174: 206 */     setDocumentoBase(DocumentoBase.RECEPCION_BODEGA);
/*  175: 208 */     if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion() == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA) {
/*  176: 210 */       this.indicadorPendientesPorFacturar = true;
/*  177:     */     }
/*  178: 213 */     this.listaRecepcionProveedor = new LazyDataModel()
/*  179:     */     {
/*  180:     */       private static final long serialVersionUID = 1L;
/*  181:     */       
/*  182:     */       public List<RecepcionProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  183:     */       {
/*  184: 220 */         List<RecepcionProveedor> lista = new ArrayList();
/*  185:     */         
/*  186: 222 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  187: 224 */         if (RecepcionProveedorBean.this.idRecepcionProveedor != null)
/*  188:     */         {
/*  189: 225 */           filters.put("idRecepcionProveedor", String.valueOf(RecepcionProveedorBean.this.idRecepcionProveedor));
/*  190: 226 */           RecepcionProveedorBean.this.idRecepcionProveedor = null;
/*  191:     */         }
/*  192: 229 */         if ((RecepcionProveedorBean.this.indicadorPendientesPorFacturar) && (!filters.containsKey("estado")) && (!filters.containsKey("numero")) && 
/*  193: 230 */           (!filters.containsKey("empresa.nombreFiscal")) && (!filters.containsKey("asiento.numero")) && 
/*  194: 231 */           (!filters.containsKey("asiento.numero")) && (!filters.containsKey("pedidoProveedor.numero")) && 
/*  195: 232 */           (!filters.containsKey("pedidoProveedor.numeroFactura")) && (!filters.containsKey("idRecepcionProveedor"))) {
/*  196: 233 */           filters.put("estado", Estado.APROBADO.toString());
/*  197:     */         }
/*  198: 236 */         lista = RecepcionProveedorBean.this.servicioRecepcionProveedor.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  199:     */         
/*  200: 238 */         RecepcionProveedorBean.this.listaRecepcionProveedor.setRowCount(RecepcionProveedorBean.this.servicioRecepcionProveedor.contarPorCriterio(filters));
/*  201: 239 */         return lista;
/*  202:     */       }
/*  203:     */     };
/*  204:     */   }
/*  205:     */   
/*  206:     */   public String cancelar()
/*  207:     */   {
/*  208: 247 */     if (this.recepcionProveedor.getId() == 0) {
/*  209: 248 */       for (DetalleRecepcionProveedor detalleRecepcionProveedor : this.recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  210: 249 */         if ((detalleRecepcionProveedor.getInventarioProducto() != null) && (detalleRecepcionProveedor.getInventarioProducto().getLote() != null) && 
/*  211: 250 */           (detalleRecepcionProveedor.getInventarioProducto().getLote().getId() != 0)) {
/*  212:     */           try
/*  213:     */           {
/*  214: 252 */             this.servicioLote.eliminar(detalleRecepcionProveedor.getInventarioProducto().getLote());
/*  215:     */           }
/*  216:     */           catch (Exception e)
/*  217:     */           {
/*  218: 254 */             e.printStackTrace();
/*  219:     */           }
/*  220:     */         }
/*  221:     */       }
/*  222:     */     }
/*  223: 260 */     setEditado(false);
/*  224: 261 */     limpiar();
/*  225: 262 */     RequestContext context = RequestContext.getCurrentInstance();
/*  226: 263 */     context.execute("document.title='AS2 ERP (" + getSesionControler().getVersion() + ")'");
/*  227:     */     
/*  228: 265 */     return "";
/*  229:     */   }
/*  230:     */   
/*  231:     */   public String editar()
/*  232:     */   {
/*  233: 275 */     setEditado(false);
/*  234: 276 */     if ((this.recepcionProveedor != null) && (this.recepcionProveedor.getId() != 0)) {
/*  235:     */       try
/*  236:     */       {
/*  237: 278 */         if (this.servicioRecepcionProveedor.buscarPorId(Integer.valueOf(this.recepcionProveedor.getId())).getEstado().equals(Estado.ELABORADO))
/*  238:     */         {
/*  239: 279 */           this.recepcionProveedor = this.servicioRecepcionProveedor.cargarDetalle(this.recepcionProveedor);
/*  240: 280 */           this.servicioRecepcionProveedor.agregarInventariosProducto(this.recepcionProveedor);
/*  241: 281 */           this.listaLecturaBalanza = null;
/*  242: 282 */           setEditado(true);
/*  243:     */         }
/*  244:     */         else
/*  245:     */         {
/*  246: 284 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  247:     */         }
/*  248:     */       }
/*  249:     */       catch (ExcepcionAS2 e)
/*  250:     */       {
/*  251: 287 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  252: 288 */         LOG.info("ERROR AL EDITAR RECEPCION DE PROVEEDOR:", e);
/*  253:     */       }
/*  254:     */     } else {
/*  255: 291 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  256:     */     }
/*  257: 294 */     return "";
/*  258:     */   }
/*  259:     */   
/*  260:     */   public String guardar()
/*  261:     */   {
/*  262:     */     try
/*  263:     */     {
/*  264: 359 */       validar(this.recepcionProveedor);
/*  265: 360 */       this.recepcionProveedor.setEstado(Estado.PROCESADO);
/*  266: 361 */       this.servicioRecepcionProveedor.guardar(this.recepcionProveedor);
/*  267: 362 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  268:     */       
/*  269: 364 */       cargarDatos();
/*  270: 365 */       RequestContext context = RequestContext.getCurrentInstance();
/*  271: 366 */       context.execute("document.title='AS2 ERP 2.1'");
/*  272:     */     }
/*  273:     */     catch (ExcepcionAS2Inventario e)
/*  274:     */     {
/*  275: 368 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  276:     */     }
/*  277:     */     catch (ExcepcionAS2 e)
/*  278:     */     {
/*  279: 371 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  280:     */     }
/*  281:     */     catch (AS2Exception e)
/*  282:     */     {
/*  283: 374 */       this.exContabilizacion = e;
/*  284: 375 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  285: 376 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  286:     */     }
/*  287:     */     catch (Exception e)
/*  288:     */     {
/*  289: 378 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  290: 379 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  291:     */     }
/*  292: 382 */     return "";
/*  293:     */   }
/*  294:     */   
/*  295:     */   public String guardarBorrador()
/*  296:     */   {
/*  297:     */     try
/*  298:     */     {
/*  299: 387 */       validar(this.recepcionProveedor);
/*  300: 388 */       this.recepcionProveedor.setEstado(Estado.ELABORADO);
/*  301: 389 */       this.servicioRecepcionProveedor.guardar(this.recepcionProveedor);
/*  302: 390 */       this.recepcionProveedor = this.servicioRecepcionProveedor.cargarDetalle(this.recepcionProveedor);
/*  303: 391 */       this.servicioRecepcionProveedor.agregarInventariosProducto(this.recepcionProveedor);
/*  304:     */       
/*  305: 393 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar_borrador"));
/*  306:     */     }
/*  307:     */     catch (ExcepcionAS2Inventario e)
/*  308:     */     {
/*  309: 395 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  310:     */     }
/*  311:     */     catch (ExcepcionAS2 e)
/*  312:     */     {
/*  313: 398 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  314:     */     }
/*  315:     */     catch (AS2Exception e)
/*  316:     */     {
/*  317: 401 */       this.exContabilizacion = e;
/*  318: 402 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  319: 403 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  320:     */     }
/*  321:     */     catch (Exception e)
/*  322:     */     {
/*  323: 405 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  324: 406 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  325:     */     }
/*  326: 409 */     return "";
/*  327:     */   }
/*  328:     */   
/*  329:     */   public DetalleRecepcionProveedor crearDetalleRecepcion()
/*  330:     */   {
/*  331: 420 */     DetalleRecepcionProveedor detalleRecepcionProveedor = new DetalleRecepcionProveedor();
/*  332: 421 */     detalleRecepcionProveedor.setRecepcionProveedor(getRecepcionProveedor());
/*  333: 422 */     detalleRecepcionProveedor.setProducto(new Producto());
/*  334: 423 */     detalleRecepcionProveedor.setBodega(AppUtil.getBodega());
/*  335:     */     
/*  336: 425 */     InventarioProducto inventarioProducto = new InventarioProducto();
/*  337: 426 */     inventarioProducto.setOperacion(getRecepcionProveedor().getDocumento().getOperacion());
/*  338: 427 */     detalleRecepcionProveedor.setInventarioProducto(inventarioProducto);
/*  339: 428 */     crearLote(detalleRecepcionProveedor);
/*  340:     */     
/*  341: 430 */     return detalleRecepcionProveedor;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void agregarDetalleListener()
/*  345:     */   {
/*  346: 434 */     DetalleRecepcionProveedor detalleRecepcionProveedor = crearDetalleRecepcion();
/*  347: 435 */     getRecepcionProveedor().getListaDetalleRecepcionProveedor().add(detalleRecepcionProveedor);
/*  348:     */   }
/*  349:     */   
/*  350:     */   public String eliminar()
/*  351:     */   {
/*  352: 445 */     if (getRecepcionProveedor().getIdOrganizacion() > 0) {
/*  353:     */       try
/*  354:     */       {
/*  355: 448 */         this.servicioRecepcionProveedor.anular(this.recepcionProveedor, this.recepcionProveedor.getFecha());
/*  356: 449 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  357:     */       }
/*  358:     */       catch (ExcepcionAS2Financiero e)
/*  359:     */       {
/*  360: 452 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  361: 453 */         LOG.info("ERROR AL ELIMINAR RECEPCION PROVEEDOR:", e);
/*  362:     */       }
/*  363:     */       catch (ExcepcionAS2Inventario e)
/*  364:     */       {
/*  365: 456 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  366: 457 */         LOG.info("ERROR AL ELIMINAR RECEPCION PROVEEDOR:", e);
/*  367:     */       }
/*  368:     */       catch (ExcepcionAS2Compras e)
/*  369:     */       {
/*  370: 460 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  371: 461 */         LOG.info("ERROR AL ELIMINAR RECEPCION PROVEEDOR:", e);
/*  372:     */       }
/*  373:     */       catch (ExcepcionAS2 e)
/*  374:     */       {
/*  375: 464 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  376: 465 */         LOG.info("ERROR AL ELIMINAR RECEPCION PROVEEDOR:", e);
/*  377:     */       }
/*  378:     */       catch (AS2Exception e)
/*  379:     */       {
/*  380: 468 */         JsfUtil.addErrorMessage(e, "");
/*  381: 469 */         e.printStackTrace();
/*  382:     */       }
/*  383:     */       catch (Exception e)
/*  384:     */       {
/*  385: 471 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  386: 472 */         LOG.info("ERROR AL ELIMINAR RECEPCION PROVEEDOR:", e);
/*  387:     */       }
/*  388:     */     } else {
/*  389: 475 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  390:     */     }
/*  391: 477 */     return "";
/*  392:     */   }
/*  393:     */   
/*  394:     */   public String limpiar()
/*  395:     */   {
/*  396: 488 */     this.indicadorFacturado = false;
/*  397: 489 */     this.mostrarBalanza = null;
/*  398: 490 */     setEditado(false);
/*  399: 491 */     setIndicadorImportacion(false);
/*  400: 492 */     crearRecepcionProveedor();
/*  401:     */     
/*  402: 494 */     return "";
/*  403:     */   }
/*  404:     */   
/*  405:     */   private void crearRecepcionProveedor()
/*  406:     */   {
/*  407: 501 */     this.recepcionProveedor = new RecepcionProveedor();
/*  408: 502 */     this.recepcionProveedor.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  409: 503 */     this.recepcionProveedor.setSucursal(AppUtil.getSucursal());
/*  410: 504 */     this.recepcionProveedor.setFecha(new Date());
/*  411: 505 */     this.recepcionProveedor.setEstado(Estado.ELABORADO);
/*  412:     */     
/*  413: 507 */     Documento documento = null;
/*  414: 508 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/*  415:     */     {
/*  416: 509 */       documento = (Documento)getListaDocumento().get(0);
/*  417: 510 */       this.recepcionProveedor.setDocumento(documento);
/*  418: 511 */       actualizarDocumento();
/*  419:     */     }
/*  420:     */     else
/*  421:     */     {
/*  422: 513 */       documento = new Documento();
/*  423: 514 */       this.recepcionProveedor.setDocumento(documento);
/*  424:     */     }
/*  425: 517 */     this.listaDireccionEmpresa = null;
/*  426: 518 */     setLecturaBalanza(null);
/*  427: 519 */     this.listaLecturaBalanza = null;
/*  428:     */   }
/*  429:     */   
/*  430:     */   public String cargarDatos()
/*  431:     */   {
/*  432: 529 */     setEditado(false);
/*  433:     */     try
/*  434:     */     {
/*  435: 531 */       limpiar();
/*  436:     */     }
/*  437:     */     catch (Exception e)
/*  438:     */     {
/*  439: 534 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  440: 535 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  441:     */     }
/*  442: 537 */     return "";
/*  443:     */   }
/*  444:     */   
/*  445:     */   public void actualizarProducto(AjaxBehaviorEvent event)
/*  446:     */   {
/*  447: 547 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  448: 548 */     DetalleRecepcionProveedor detalleRecepcionProveedor = (DetalleRecepcionProveedor)this.dtDetalleRecepcionProveedor.getRowData();
/*  449:     */     try
/*  450:     */     {
/*  451: 551 */       cargarProductoDesdeCodigoEnDetalle(codigo, detalleRecepcionProveedor);
/*  452:     */     }
/*  453:     */     catch (ExcepcionAS2 e)
/*  454:     */     {
/*  455: 553 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  456: 554 */       detalleRecepcionProveedor.getProducto().setCodigo("");
/*  457: 555 */       detalleRecepcionProveedor.getProducto().setNombre("");
/*  458:     */     }
/*  459:     */     catch (Exception ex)
/*  460:     */     {
/*  461: 557 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/*  462:     */     }
/*  463:     */   }
/*  464:     */   
/*  465:     */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleRecepcionProveedor detalleRecepcionProveedor)
/*  466:     */     throws ExcepcionAS2
/*  467:     */   {
/*  468: 562 */     Producto producto = null;
/*  469: 563 */     producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.RECEPCION_BODEGA);
/*  470: 564 */     detalleRecepcionProveedor.setProducto(producto);
/*  471: 565 */     cargarBodega(detalleRecepcionProveedor);
/*  472: 566 */     InventarioProducto inventarioProducto = new InventarioProducto();
/*  473: 567 */     inventarioProducto.setOperacion(this.recepcionProveedor.getDocumento().getOperacion());
/*  474: 568 */     detalleRecepcionProveedor.setInventarioProducto(inventarioProducto);
/*  475: 569 */     inventarioProducto.setProducto(producto);
/*  476: 570 */     detalleRecepcionProveedor.setUnidadCompra(producto.getUnidadCompra());
/*  477: 572 */     if (producto.getLote() != null)
/*  478:     */     {
/*  479: 573 */       detalleRecepcionProveedor.getInventarioProducto().setLote(producto.getLote());
/*  480: 574 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), detalleRecepcionProveedor.getBodega().getIdBodega(), producto
/*  481: 575 */         .getLote().getIdLote(), this.recepcionProveedor.getFecha());
/*  482: 576 */       detalleRecepcionProveedor.setSaldo(saldo);
/*  483: 578 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  484: 579 */         detalleRecepcionProveedor.setCantidad(saldo);
/*  485:     */       }
/*  486:     */     }
/*  487:     */     else
/*  488:     */     {
/*  489: 582 */       crearLote(detalleRecepcionProveedor);
/*  490:     */     }
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void agregarDetalleDesdeCodigoCabecera()
/*  494:     */   {
/*  495: 587 */     DetalleRecepcionProveedor detalleRecepcionProveedor = null;
/*  496:     */     try
/*  497:     */     {
/*  498: 589 */       detalleRecepcionProveedor = new DetalleRecepcionProveedor();
/*  499: 590 */       detalleRecepcionProveedor.setRecepcionProveedor(getRecepcionProveedor());
/*  500: 591 */       detalleRecepcionProveedor.setProducto(new Producto());
/*  501: 592 */       detalleRecepcionProveedor.setBodega(AppUtil.getBodega());
/*  502:     */       
/*  503: 594 */       InventarioProducto inventarioProducto = new InventarioProducto();
/*  504: 595 */       inventarioProducto.setOperacion(this.recepcionProveedor.getDocumento().getOperacion());
/*  505: 596 */       detalleRecepcionProveedor.setInventarioProducto(inventarioProducto);
/*  506: 597 */       crearLote(detalleRecepcionProveedor);
/*  507:     */       
/*  508: 599 */       getRecepcionProveedor().getListaDetalleRecepcionProveedor().add(detalleRecepcionProveedor);
/*  509: 600 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, detalleRecepcionProveedor);
/*  510: 601 */       this.codigoCabecera = "";
/*  511:     */     }
/*  512:     */     catch (ExcepcionAS2 e)
/*  513:     */     {
/*  514: 603 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  515: 604 */       detalleRecepcionProveedor.getProducto().setCodigo("");
/*  516: 605 */       detalleRecepcionProveedor.getProducto().setNombre("");
/*  517: 606 */       detalleRecepcionProveedor.setEliminado(true);
/*  518: 607 */       this.codigoCabecera = "";
/*  519:     */     }
/*  520:     */   }
/*  521:     */   
/*  522:     */   public String creacionLote()
/*  523:     */     throws ExcepcionAS2
/*  524:     */   {
/*  525: 613 */     if (this.recepcionProveedor.getEmpresa() == null) {
/*  526: 614 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_proveedor"));
/*  527:     */     }
/*  528: 617 */     this.detalleRecepcionProveedorSeleccionado = ((DetalleRecepcionProveedor)this.dtDetalleRecepcionProveedor.getRowData());
/*  529:     */     
/*  530: 619 */     this.loteCrear = this.detalleRecepcionProveedorSeleccionado.getInventarioProducto().getLote();
/*  531:     */     
/*  532: 621 */     Map<String, String> filters = new HashMap();
/*  533: 622 */     filters.put("codigo", "=" + this.loteCrear.getCodigo());
/*  534: 623 */     filters.put("empresa.idEmpresa", "=" + this.recepcionProveedor.getEmpresa().getIdEmpresa());
/*  535: 624 */     filters.put("producto.idProducto", "=" + this.detalleRecepcionProveedorSeleccionado.getProducto().getIdProducto());
/*  536:     */     
/*  537: 626 */     List<Lote> loteList = this.servicioLote.obtenerListaCombo("codigo", true, filters);
/*  538: 628 */     if (!loteList.isEmpty()) {
/*  539: 629 */       this.loteCrear = ((Lote)loteList.get(0));
/*  540:     */     }
/*  541: 631 */     actualizarAtributos(this.loteCrear, this.detalleRecepcionProveedorSeleccionado.getProducto());
/*  542: 632 */     return "";
/*  543:     */   }
/*  544:     */   
/*  545:     */   public Lote crearLote(DetalleRecepcionProveedor detalleRecepcion)
/*  546:     */   {
/*  547:     */     try
/*  548:     */     {
/*  549: 639 */       if (detalleRecepcion.getProducto().isIndicadorLote())
/*  550:     */       {
/*  551: 641 */         Lote lote = this.servicioLote.crearLote(AppUtil.getOrganizacion().getIdOrganizacion(), detalleRecepcion.getProducto(), "", false, this.recepcionProveedor
/*  552: 642 */           .getEmpresa(), new Date(), new Date(), false);
/*  553:     */         
/*  554: 644 */         lote.setCodigo(null);
/*  555: 645 */         lote.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  556: 646 */         detalleRecepcion.getInventarioProducto().setLote(lote);
/*  557: 649 */         if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {}
/*  558: 655 */         return lote;
/*  559:     */       }
/*  560: 657 */       return null;
/*  561:     */     }
/*  562:     */     catch (ExcepcionAS2 e)
/*  563:     */     {
/*  564: 661 */       e.printStackTrace();
/*  565:     */     }
/*  566:     */     catch (AS2Exception e)
/*  567:     */     {
/*  568: 663 */       e.printStackTrace();
/*  569:     */     }
/*  570: 666 */     return null;
/*  571:     */   }
/*  572:     */   
/*  573:     */   public String guardarLote()
/*  574:     */   {
/*  575:     */     try
/*  576:     */     {
/*  577: 673 */       this.loteCrear.setCodigo(this.loteCrear.getProducto().getPrefijoLote() + this.loteCrear.getCodigo());
/*  578: 674 */       if (this.loteCrear.getIdLote() != 0)
/*  579:     */       {
/*  580: 675 */         this.servicioLote.guardar(this.loteCrear);
/*  581: 676 */         this.detalleRecepcionProveedorSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/*  582:     */       }
/*  583: 678 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  584: 679 */       this.loteCrear.getProducto().setPrefijoLote(null);
/*  585:     */     }
/*  586:     */     catch (AS2Exception e)
/*  587:     */     {
/*  588: 696 */       JsfUtil.addErrorMessage(e, "");
/*  589:     */     }
/*  590:     */     catch (Exception e)
/*  591:     */     {
/*  592: 698 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  593: 699 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  594:     */     }
/*  595: 701 */     return "";
/*  596:     */   }
/*  597:     */   
/*  598:     */   public void actualizarProducto(SelectEvent event)
/*  599:     */   {
/*  600: 710 */     getRecepcionProveedor().setEmpresa((Empresa)event.getObject());
/*  601:     */   }
/*  602:     */   
/*  603:     */   public List<Empresa> listarProveedores(String consulta)
/*  604:     */   {
/*  605: 720 */     List<Empresa> lista = new ArrayList();
/*  606: 722 */     for (Empresa e : getListaEmpresaProveedor()) {
/*  607: 723 */       if ((e.getCodigo().startsWith(consulta)) || (e.getIdentificacion().startsWith(consulta)) || (e.getNombreFiscal().startsWith(consulta))) {
/*  608: 724 */         lista.add(e);
/*  609:     */       }
/*  610:     */     }
/*  611: 727 */     return lista;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void actualizarProveedor(SelectEvent event)
/*  615:     */   {
/*  616: 737 */     getRecepcionProveedor().setEmpresa((Empresa)event.getObject());
/*  617: 738 */     actualizarListaPedidoProveedorARecibir();
/*  618: 740 */     if (getRecepcionProveedor().getEmpresa() != null)
/*  619:     */     {
/*  620: 741 */       RequestContext context = RequestContext.getCurrentInstance();
/*  621: 742 */       context.execute("document.title='Recepcion: " + getRecepcionProveedor().getEmpresa().getNombreFiscal() + "'");
/*  622:     */     }
/*  623:     */   }
/*  624:     */   
/*  625:     */   public String eliminarDetalle()
/*  626:     */   {
/*  627: 747 */     DetalleRecepcionProveedor detalleRecepcionProveedor = (DetalleRecepcionProveedor)this.dtDetalleRecepcionProveedor.getRowData();
/*  628: 748 */     detalleRecepcionProveedor.setEliminado(true);
/*  629: 749 */     for (LecturaBalanza lb : detalleRecepcionProveedor.getListaLecturaBalanza()) {
/*  630: 750 */       lb.setEliminado(true);
/*  631:     */     }
/*  632: 752 */     this.listaLecturaBalanza = null;
/*  633: 758 */     if (isMostrarBalanza()) {
/*  634: 759 */       guardarBorrador();
/*  635:     */     }
/*  636: 762 */     return "";
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void cargarProducto()
/*  640:     */   {
/*  641: 770 */     if (this.recepcionProveedor.getEmpresa() == null)
/*  642:     */     {
/*  643: 771 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_proveedor"));
/*  644:     */     }
/*  645:     */     else
/*  646:     */     {
/*  647: 775 */       Producto producto = getListaProductoBean().getProducto();
/*  648: 776 */       if (producto != null)
/*  649:     */       {
/*  650: 777 */         DetalleRecepcionProveedor detalleRecepcionProveedor = new DetalleRecepcionProveedor();
/*  651: 778 */         detalleRecepcionProveedor.setProducto(producto);
/*  652: 779 */         detalleRecepcionProveedor.setUnidadCompra(producto.getUnidadCompra());
/*  653: 780 */         if ((!isMostrarBalanza()) || (!producto.isIndicadorPesoBalanza())) {
/*  654: 781 */           detalleRecepcionProveedor.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/*  655:     */         }
/*  656: 784 */         cargarBodega(detalleRecepcionProveedor);
/*  657: 785 */         InventarioProducto inventarioProducto = new InventarioProducto();
/*  658: 786 */         inventarioProducto.setOperacion(this.recepcionProveedor.getDocumento().getOperacion());
/*  659: 787 */         inventarioProducto.setProducto(producto);
/*  660: 788 */         detalleRecepcionProveedor.setInventarioProducto(inventarioProducto);
/*  661: 789 */         detalleRecepcionProveedor.setRecepcionProveedor(this.recepcionProveedor);
/*  662: 790 */         crearLote(detalleRecepcionProveedor);
/*  663: 791 */         this.recepcionProveedor.getListaDetalleRecepcionProveedor().add(detalleRecepcionProveedor);
/*  664:     */       }
/*  665: 794 */       getListaProductoBean().setProducto(null);
/*  666:     */     }
/*  667:     */   }
/*  668:     */   
/*  669:     */   public String actualizarDocumento()
/*  670:     */   {
/*  671: 806 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getRecepcionProveedor().getDocumento().getIdDocumento()));
/*  672: 807 */     getRecepcionProveedor().setDocumento(documento);
/*  673:     */     
/*  674: 809 */     setSecuenciaEditable(!this.recepcionProveedor.getDocumento().isIndicadorBloqueoSecuencia());
/*  675:     */     
/*  676: 811 */     return "";
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void cargarDetallePedidoListener()
/*  680:     */   {
/*  681: 819 */     for (DetalleRecepcionProveedor ddc : getRecepcionProveedor().getListaDetalleRecepcionProveedor()) {
/*  682: 820 */       ddc.setEliminado(true);
/*  683:     */     }
/*  684:     */     try
/*  685:     */     {
/*  686: 823 */       if (getRecepcionProveedor().getPedidoProveedor() != null)
/*  687:     */       {
/*  688: 825 */         getRecepcionProveedor().setProyecto(getRecepcionProveedor().getPedidoProveedor().getProyecto());
/*  689: 826 */         getRecepcionProveedor().setPersonaResponsable(getRecepcionProveedor().getPedidoProveedor().getPersonaResponsable());
/*  690: 827 */         getRecepcionProveedor().setDescripcion(getRecepcionProveedor().getPedidoProveedor().getDescripcion());
/*  691:     */         
/*  692:     */ 
/*  693: 830 */         boolean aprobacionParcial = ParametrosSistema.getAprobacionParcialPedidoProveedor(AppUtil.getOrganizacion().getId()).booleanValue();
/*  694: 831 */         Object listaDPP = this.servicioPedidoProveedor.obtenerListaDetallePedidoPorRecibir(getRecepcionProveedor().getPedidoProveedor().getId(), aprobacionParcial);
/*  695: 834 */         for (DetallePedidoProveedor dpc : (List)listaDPP)
/*  696:     */         {
/*  697: 836 */           DetalleRecepcionProveedor drProveedor = new DetalleRecepcionProveedor();
/*  698: 837 */           drProveedor.setDetallePedidoProveedor(dpc);
/*  699: 838 */           drProveedor.setRecepcionProveedor(this.recepcionProveedor);
/*  700: 839 */           drProveedor.setProducto(dpc.getProducto());
/*  701: 840 */           drProveedor.setUnidadCompra(dpc.getUnidadCompra());
/*  702: 841 */           if ((isMostrarBalanza()) && (dpc.getProducto().isIndicadorPesoBalanza())) {
/*  703: 842 */             drProveedor.setCantidad(BigDecimal.ZERO);
/*  704:     */           } else {
/*  705: 844 */             drProveedor.setCantidad(dpc.getCantidadPorRecibir());
/*  706:     */           }
/*  707: 846 */           drProveedor.setDescripcion(dpc.getDescripcion());
/*  708: 847 */           cargarBodega(drProveedor);
/*  709: 848 */           InventarioProducto inventarioProducto = new InventarioProducto();
/*  710: 849 */           inventarioProducto.setOperacion(this.recepcionProveedor.getDocumento().getOperacion());
/*  711: 850 */           inventarioProducto.setProducto(drProveedor.getProducto());
/*  712: 851 */           drProveedor.setInventarioProducto(inventarioProducto);
/*  713: 852 */           crearLote(drProveedor);
/*  714: 853 */           this.recepcionProveedor.getListaDetalleRecepcionProveedor().add(drProveedor);
/*  715:     */         }
/*  716:     */       }
/*  717:     */     }
/*  718:     */     catch (Exception e)
/*  719:     */     {
/*  720: 857 */       e.printStackTrace();
/*  721: 858 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  722: 859 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   private void actualizarListaPedidoProveedorARecibir()
/*  727:     */   {
/*  728: 865 */     this.listaPedidoProveedor.clear();
/*  729: 867 */     if (getRecepcionProveedor().getEmpresa() != null)
/*  730:     */     {
/*  731: 868 */       this.listaPedidoProveedor = this.servicioPedidoProveedor.listaPedidosPorRecibir(getRecepcionProveedor().getEmpresa().getId());
/*  732: 870 */       if ((getRecepcionProveedor().getPedidoProveedor() != null) && 
/*  733: 871 */         (!this.listaPedidoProveedor.contains(getRecepcionProveedor().getPedidoProveedor()))) {
/*  734: 872 */         this.listaPedidoProveedor.add(getRecepcionProveedor().getPedidoProveedor());
/*  735:     */       }
/*  736:     */     }
/*  737:     */   }
/*  738:     */   
/*  739:     */   public void cargarRecepcionDesdeFactura()
/*  740:     */   {
/*  741: 882 */     if (this.idFacturaProveedor != null)
/*  742:     */     {
/*  743: 884 */       limpiar();
/*  744: 886 */       if (getListaDocumento().size() > 0) {
/*  745: 887 */         this.recepcionProveedor.setDocumento((Documento)getListaDocumento().get(0));
/*  746:     */       }
/*  747: 891 */       List<DetalleFacturaProveedor> listaDetalleFacturaProveedorPorRecepcionar = this.servicioFacturaProveedor.buscarDetallesNoDespachados(this.idFacturaProveedor);
/*  748: 892 */       if (listaDetalleFacturaProveedorPorRecepcionar.size() == 0)
/*  749:     */       {
/*  750: 893 */         addInfoMessage(getLanguageController().getMensaje("msg_info_ya_existe_recepcion_factura"));
/*  751:     */       }
/*  752:     */       else
/*  753:     */       {
/*  754: 897 */         this.recepcionProveedor = this.servicioFacturaProveedor.cargarDetalleARecibir(this.recepcionProveedor, this.idFacturaProveedor, listaDetalleFacturaProveedorPorRecepcionar);
/*  755: 900 */         for (DetalleRecepcionProveedor detalleRecepcionProveedor : this.recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  756: 901 */           cargarBodega(detalleRecepcionProveedor);
/*  757:     */         }
/*  758: 904 */         setIndicadorImportacion(this.recepcionProveedor.getFacturaProveedor().getDocumento().isIndicadorDocumentoExterior());
/*  759:     */         
/*  760: 906 */         setEditado(true);
/*  761:     */       }
/*  762: 909 */       for (DetalleRecepcionProveedor detalle : this.recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/*  763: 910 */         if (!detalle.isEliminado()) {
/*  764: 911 */           crearLote(detalle);
/*  765:     */         }
/*  766:     */       }
/*  767: 915 */       this.idFacturaProveedor = null;
/*  768: 916 */       this.indicadorFacturado = true;
/*  769:     */     }
/*  770:     */   }
/*  771:     */   
/*  772:     */   public List<Lote> autocompletarLotes(String consulta)
/*  773:     */   {
/*  774: 923 */     DetalleRecepcionProveedor detalleRecepcionProveedor = (DetalleRecepcionProveedor)this.dtDetalleRecepcionProveedor.getRowData();
/*  775: 924 */     return this.servicioLote.autocompletarLote(detalleRecepcionProveedor.getProducto(), consulta);
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void cargaArchivoLoteListener(FileUploadEvent event)
/*  779:     */   {
/*  780: 928 */     this.lineasLotesArchivo = 0;
/*  781: 929 */     this.totalCantidadLoteArchivo = BigDecimal.ZERO;
/*  782:     */     try
/*  783:     */     {
/*  784: 932 */       this.inputLote = new BufferedInputStream(event.getFile().getInputstream());
/*  785: 933 */       List<String> datosArchivo = FuncionesUtiles.leerArchivoTexto(this.inputLote);
/*  786: 935 */       for (String dato : datosArchivo) {
/*  787: 936 */         if ((dato != null) && (!dato.isEmpty()) && (dato.split(",").length >= 2))
/*  788:     */         {
/*  789: 937 */           this.lineasLotesArchivo += 1;
/*  790: 938 */           this.totalCantidadLoteArchivo = this.totalCantidadLoteArchivo.add(new BigDecimal(dato.split(",")[1]));
/*  791:     */         }
/*  792:     */       }
/*  793: 943 */       this.inputLote = new BufferedInputStream(event.getFile().getInputstream());
/*  794:     */     }
/*  795:     */     catch (IOException e)
/*  796:     */     {
/*  797: 946 */       this.inputLote = null;
/*  798: 947 */       this.lineasLotesArchivo = 0;
/*  799: 948 */       this.totalCantidadLoteArchivo = BigDecimal.ZERO;
/*  800: 949 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  801:     */     }
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void cargarDetalleArchivoLoteListener()
/*  805:     */   {
/*  806:     */     try
/*  807:     */     {
/*  808: 961 */       InputStream input = new BufferedInputStream(this.inputLote);
/*  809: 963 */       if (this.detalleRecepcionProveedorSeleccionado != null) {
/*  810: 964 */         this.detalleRecepcionProveedorSeleccionado.setEliminado(true);
/*  811:     */       }
/*  812: 966 */       this.servicioRecepcionProveedor.cargarDetalleRecepcionArchivoTexto(
/*  813: 967 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), this.recepcionProveedor, input, AppUtil.getBodega(), this.productoCargaArchivo, this.recepcionProveedor
/*  814: 968 */         .getEmpresa(), this.detalleRecepcionProveedorSeleccionado);
/*  815: 969 */       limpiarCargaLote();
/*  816: 970 */       this.detalleRecepcionProveedorSeleccionado = null;
/*  817:     */     }
/*  818:     */     catch (ExcepcionAS2 e)
/*  819:     */     {
/*  820: 973 */       if (this.detalleRecepcionProveedorSeleccionado != null) {
/*  821: 974 */         this.detalleRecepcionProveedorSeleccionado.setEliminado(false);
/*  822:     */       }
/*  823: 976 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  824: 977 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  825:     */     }
/*  826:     */     catch (Exception e)
/*  827:     */     {
/*  828: 979 */       if (this.detalleRecepcionProveedorSeleccionado != null) {
/*  829: 980 */         this.detalleRecepcionProveedorSeleccionado.setEliminado(false);
/*  830:     */       }
/*  831: 982 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  832: 983 */       e.printStackTrace();
/*  833:     */     }
/*  834:     */   }
/*  835:     */   
/*  836:     */   public List<Producto> autocompletarProductos(String consulta)
/*  837:     */   {
/*  838: 994 */     Map<String, String> filtros = new HashMap();
/*  839: 995 */     filtros.put("indicadorPesoBalanza", String.valueOf(true));
/*  840: 996 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/*  841:     */   }
/*  842:     */   
/*  843:     */   public List<Producto> autocompletarProductosTodos(String consulta)
/*  844:     */   {
/*  845:1000 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/*  846:     */   }
/*  847:     */   
/*  848:     */   public String creacionLoteCarga()
/*  849:     */   {
/*  850:1009 */     return replicarDetalles(this.detalleRecepcionProveedorSeleccionado);
/*  851:     */   }
/*  852:     */   
/*  853:     */   public String seleccionarDetalleRecepcion()
/*  854:     */   {
/*  855:1014 */     this.opcionCarga = Integer.valueOf(1);
/*  856:1015 */     this.detalleRecepcionProveedorSeleccionado = ((DetalleRecepcionProveedor)this.dtDetalleRecepcionProveedor.getRowData());
/*  857:1016 */     this.productoCargaArchivo = this.servicioProducto.cargarProductoConAtributoInstancia(this.detalleRecepcionProveedorSeleccionado.getProducto().getId());
/*  858:     */     
/*  859:1018 */     this.productoCargaArchivo.setTraAtributo1(this.productoCargaArchivo.getAtributoProduccion1());
/*  860:1019 */     this.productoCargaArchivo.setTraAtributo2(this.productoCargaArchivo.getAtributoProduccion2());
/*  861:1020 */     this.productoCargaArchivo.setTraAtributo3(this.productoCargaArchivo.getAtributoProduccion3());
/*  862:1021 */     this.productoCargaArchivo.setTraAtributo4(this.productoCargaArchivo.getAtributoProduccion4());
/*  863:1022 */     this.productoCargaArchivo.setTraAtributo5(this.productoCargaArchivo.getAtributoProduccion5());
/*  864:1023 */     this.productoCargaArchivo.setTraAtributo6(this.productoCargaArchivo.getAtributoProduccion6());
/*  865:1024 */     this.productoCargaArchivo.setTraAtributo7(this.productoCargaArchivo.getAtributoProduccion7());
/*  866:1025 */     this.productoCargaArchivo.setTraAtributo8(this.productoCargaArchivo.getAtributoProduccion8());
/*  867:1026 */     this.productoCargaArchivo.setTraAtributo9(this.productoCargaArchivo.getAtributoProduccion9());
/*  868:1027 */     this.productoCargaArchivo.setTraAtributo10(this.productoCargaArchivo.getAtributoProduccion10());
/*  869:     */     
/*  870:1029 */     return "";
/*  871:     */   }
/*  872:     */   
/*  873:     */   public String replicaLineaEnLotes(DetalleRecepcionProveedor detalleRecepcion)
/*  874:     */   {
/*  875:     */     try
/*  876:     */     {
/*  877:1034 */       validarAtributoDetalleRecepcionProveedor(detalleRecepcion);
/*  878:1035 */       int cantidad = detalleRecepcion.getCantidad().intValue();
/*  879:1036 */       if (cantidad > 1)
/*  880:     */       {
/*  881:1037 */         int indice = 0;
/*  882:1038 */         for (int k = 0; k < this.recepcionProveedor.getListaDetalleRecepcionProveedor().size(); k++) {
/*  883:1039 */           if (((DetalleRecepcionProveedor)this.recepcionProveedor.getListaDetalleRecepcionProveedor().get(k)).getRowKey() == detalleRecepcion.getRowKey())
/*  884:     */           {
/*  885:1040 */             indice = k;
/*  886:1041 */             break;
/*  887:     */           }
/*  888:     */         }
/*  889:1045 */         detalleRecepcion.setCantidad(new BigDecimal(1));
/*  890:1046 */         if (detalleRecepcion.getInventarioProducto() != null) {
/*  891:1047 */           detalleRecepcion.getInventarioProducto().setCantidad(detalleRecepcion.getCantidad());
/*  892:     */         }
/*  893:1050 */         for (int i = 1; i < cantidad; i++)
/*  894:     */         {
/*  895:1052 */           DetalleRecepcionProveedor detalle = new DetalleRecepcionProveedor();
/*  896:1053 */           detalle.setBodega(detalleRecepcion.getBodega());
/*  897:1054 */           detalle.setCantidad(new BigDecimal(1));
/*  898:1055 */           detalle.setDescripcion(detalleRecepcion.getDescripcion());
/*  899:1056 */           detalle.setDetalleFacturaProveedor(detalleRecepcion.getDetalleFacturaProveedor());
/*  900:1057 */           detalle.setDetallePedidoProveedor(detalleRecepcion.getDetallePedidoProveedor());
/*  901:1058 */           detalle.setIdOrganizacion(detalleRecepcion.getIdOrganizacion());
/*  902:1059 */           detalle.setIdSucursal(detalleRecepcion.getIdSucursal());
/*  903:1060 */           detalle.setProducto(detalleRecepcion.getProducto());
/*  904:1061 */           detalle.setRecepcionProveedor(detalleRecepcion.getRecepcionProveedor());
/*  905:1062 */           detalle.setUnidadCompra(detalleRecepcion.getUnidadCompra());
/*  906:1063 */           detalle.setSaldo(detalleRecepcion.getSaldo());
/*  907:     */           
/*  908:1065 */           this.recepcionProveedor.getListaDetalleRecepcionProveedor().add(indice, detalle);
/*  909:     */           
/*  910:1067 */           InventarioProducto ip = new InventarioProducto();
/*  911:1068 */           detalle.setInventarioProducto(ip);
/*  912:1069 */           ip.setDetalleRecepcionProveedor(detalle);
/*  913:1070 */           ip.setCantidad(detalle.getCantidad());
/*  914:1071 */           ip.setOperacion(this.recepcionProveedor.getDocumento().getOperacion());
/*  915:1072 */           crearLote(detalle);
/*  916:1073 */           actualizarValorAtributoDetalleRecepcion(detalleRecepcion, detalle);
/*  917:     */         }
/*  918:     */       }
/*  919:     */       else
/*  920:     */       {
/*  921:1076 */         addInfoMessage(getLanguageController().getMensaje("msg_info_cantidad_mayor_a_1"));
/*  922:     */       }
/*  923:     */     }
/*  924:     */     catch (AS2Exception e)
/*  925:     */     {
/*  926:1079 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/*  927:     */     }
/*  928:1081 */     return "";
/*  929:     */   }
/*  930:     */   
/*  931:     */   private String replicarDetalles(DetalleRecepcionProveedor detalleRecepcionProveedor)
/*  932:     */   {
/*  933:     */     try
/*  934:     */     {
/*  935:1086 */       this.servicioRecepcionProveedor.cargarRecepcionDesdeNumeroLotes(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), this.recepcionProveedor, 
/*  936:1087 */         AppUtil.getBodega(), this.productoCargaArchivo, this.cantidadPesoLote, this.numeroLotes, detalleRecepcionProveedor, this.generarLotesAutomaticamente);
/*  937:     */       
/*  938:     */ 
/*  939:1090 */       this.cantidadPesoLote = BigDecimal.ZERO;
/*  940:1091 */       this.numeroLotes = Integer.valueOf(1);
/*  941:1092 */       this.detalleRecepcionProveedorSeleccionado = null;
/*  942:1093 */       this.productoCargaArchivo.setPrefijoLote(null);
/*  943:     */     }
/*  944:     */     catch (ExcepcionAS2 e)
/*  945:     */     {
/*  946:1095 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  947:1096 */       limpiarCargaLote();
/*  948:1097 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  949:     */     }
/*  950:     */     catch (Exception e)
/*  951:     */     {
/*  952:1099 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  953:1100 */       limpiarCargaLote();
/*  954:1101 */       e.printStackTrace();
/*  955:     */     }
/*  956:1103 */     return "";
/*  957:     */   }
/*  958:     */   
/*  959:     */   public String limpiarCargaLote()
/*  960:     */   {
/*  961:1107 */     this.productoCargaArchivo = null;
/*  962:1108 */     this.cantidadPesoLote = BigDecimal.ZERO;
/*  963:1109 */     this.numeroLotes = Integer.valueOf(1);
/*  964:     */     
/*  965:1111 */     return "";
/*  966:     */   }
/*  967:     */   
/*  968:     */   public void capturarPesoListener()
/*  969:     */   {
/*  970:1115 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/*  971:     */       try
/*  972:     */       {
/*  973:1117 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, false);
/*  974:     */       }
/*  975:     */       catch (AS2Exception ex)
/*  976:     */       {
/*  977:1119 */         JsfUtil.addErrorMessage(ex, "");
/*  978:     */       }
/*  979:     */     }
/*  980:     */   }
/*  981:     */   
/*  982:     */   public void agregarPesoListener()
/*  983:     */   {
/*  984:1125 */     if ((this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0)) {
/*  985:     */       try
/*  986:     */       {
/*  987:1127 */         BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(this.lecturaBalanza);
/*  988:     */         
/*  989:1129 */         BigDecimal cantidad = cantidades[0];
/*  990:1130 */         BigDecimal cantidadInformativa = cantidades[1];
/*  991:1131 */         Producto producto = this.lecturaBalanza.getProducto();
/*  992:     */         
/*  993:1133 */         DetalleRecepcionProveedor detalleRecepcionProveedor = (DetalleRecepcionProveedor)this.dtDetalleRecepcionProveedor.getRowData();
/*  994:1134 */         if ((detalleRecepcionProveedor == null) || (detalleRecepcionProveedor.getProducto() == null) || 
/*  995:1135 */           (producto.getId() != detalleRecepcionProveedor.getProducto().getId())) {
/*  996:1137 */           for (DetalleRecepcionProveedor drp : getRecepcionProveedor().getListaDetalleRecepcionProveedor()) {
/*  997:1138 */             if ((!drp.isEliminado()) && (drp.getProducto() != null) && (drp.getProducto().getId() == producto.getId()))
/*  998:     */             {
/*  999:1139 */               detalleRecepcionProveedor = drp;
/* 1000:1140 */               break;
/* 1001:     */             }
/* 1002:     */           }
/* 1003:     */         }
/* 1004:1144 */         if (detalleRecepcionProveedor != null)
/* 1005:     */         {
/* 1006:1145 */           detalleRecepcionProveedor.setCantidad(detalleRecepcionProveedor.getCantidad().add(cantidad));
/* 1007:1146 */           if (producto.isIndicadorManejaUnidadInformativa())
/* 1008:     */           {
/* 1009:1147 */             if (detalleRecepcionProveedor.getCantidadUnidadInformativa() == null) {
/* 1010:1148 */               detalleRecepcionProveedor.setCantidadUnidadInformativa(BigDecimal.ZERO);
/* 1011:     */             }
/* 1012:1151 */             detalleRecepcionProveedor.setCantidadUnidadInformativa(detalleRecepcionProveedor.getCantidadUnidadInformativa().add(cantidadInformativa));
/* 1013:     */           }
/* 1014:     */         }
/* 1015:     */         else
/* 1016:     */         {
/* 1017:1154 */           detalleRecepcionProveedor = crearDetalleRecepcion();
/* 1018:1155 */           detalleRecepcionProveedor.setProducto(producto);
/* 1019:1156 */           detalleRecepcionProveedor.setCantidad(cantidad);
/* 1020:1157 */           if (producto.isIndicadorManejaUnidadInformativa()) {
/* 1021:1158 */             detalleRecepcionProveedor.setCantidadUnidadInformativa(cantidadInformativa);
/* 1022:     */           }
/* 1023:1160 */           detalleRecepcionProveedor.setUnidadCompra(producto.getUnidadCompra());
/* 1024:1161 */           getRecepcionProveedor().getListaDetalleRecepcionProveedor().add(detalleRecepcionProveedor);
/* 1025:     */         }
/* 1026:1163 */         this.servicioRecepcionProveedor.validacionCantidadRecepcion(detalleRecepcionProveedor, this.recepcionProveedor, false);
/* 1027:1164 */         this.lecturaBalanza.setDetalleRecepcionProveedor(detalleRecepcionProveedor);
/* 1028:1165 */         this.lecturaBalanza.setOperacion(1);
/* 1029:1166 */         detalleRecepcionProveedor.getListaLecturaBalanza().add(this.lecturaBalanza);
/* 1030:     */         
/* 1031:     */ 
/* 1032:1169 */         this.servicioRecepcionProveedor.validacionCantidadRecepcion(detalleRecepcionProveedor, this.recepcionProveedor, false);
/* 1033:     */         
/* 1034:1171 */         this.listaLecturaBalanza = null;
/* 1035:1172 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 1036:1173 */         this.lecturaBalanza = null;
/* 1037:1174 */         getLecturaBalanza().setDispositivo(dispositivo);
/* 1038:1175 */         guardarBorrador();
/* 1039:     */       }
/* 1040:     */       catch (AS2Exception e)
/* 1041:     */       {
/* 1042:1177 */         JsfUtil.addErrorMessage(e, "");
/* 1043:1178 */         e.printStackTrace();
/* 1044:     */       }
/* 1045:     */     }
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public void processDownload()
/* 1049:     */   {
/* 1050:     */     try
/* 1051:     */     {
/* 1052:1190 */       RecepcionProveedor fp = (RecepcionProveedor)this.dtRecepcionProveedor.getRowData();
/* 1053:1191 */       String fileName = fp.getPdf();
/* 1054:1192 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "recepcion_proveedor");
/* 1055:1194 */       if (fileName == null) {
/* 1056:1195 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1057:     */       } else {
/* 1058:1197 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/* 1059:     */       }
/* 1060:     */     }
/* 1061:     */     catch (Exception e)
/* 1062:     */     {
/* 1063:1201 */       e.printStackTrace();
/* 1064:1202 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public String eliminarArchivo()
/* 1069:     */   {
/* 1070:1207 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getRecepcionProveedor().getPdf());
/* 1071:1208 */     getRecepcionProveedor().setPdf(null);
/* 1072:1209 */     HashMap<String, Object> campos = new HashMap();
/* 1073:1210 */     campos.put("pdf", null);
/* 1074:1211 */     this.servicioRecepcionProveedor.actualizarAtributoEntidad(getRecepcionProveedor(), campos);
/* 1075:1212 */     return null;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public void processUpload(FileUploadEvent event)
/* 1079:     */   {
/* 1080:     */     try
/* 1081:     */     {
/* 1082:1225 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "recepcion_proveedor");
/* 1083:     */       
/* 1084:1227 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getRecepcionProveedor().getNumero(), uploadDir);
/* 1085:     */       
/* 1086:     */ 
/* 1087:1230 */       HashMap<String, Object> campos = new HashMap();
/* 1088:1231 */       campos.put("pdf", fileName);
/* 1089:1232 */       this.servicioRecepcionProveedor.actualizarAtributoEntidad(getRecepcionProveedor(), campos);
/* 1090:     */       
/* 1091:1234 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 1092:     */     }
/* 1093:     */     catch (Exception e)
/* 1094:     */     {
/* 1095:1237 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 1096:1238 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 1097:     */     }
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public String getDirectorioDescarga()
/* 1101:     */   {
/* 1102:1245 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "recepcion_proveedor");
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public RecepcionProveedor getRecepcionProveedor()
/* 1106:     */   {
/* 1107:1250 */     if (this.recepcionProveedor == null) {
/* 1108:1251 */       this.recepcionProveedor = new RecepcionProveedor();
/* 1109:     */     }
/* 1110:1253 */     return this.recepcionProveedor;
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public void setRecepcionProveedor(RecepcionProveedor recepcionProveedor)
/* 1114:     */   {
/* 1115:1257 */     this.recepcionProveedor = recepcionProveedor;
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   public LazyDataModel<RecepcionProveedor> getListaRecepcionProveedor()
/* 1119:     */   {
/* 1120:1261 */     return this.listaRecepcionProveedor;
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public void setListaRecepcionProveedor(LazyDataModel<RecepcionProveedor> listaRecepcionProveedor)
/* 1124:     */   {
/* 1125:1265 */     this.listaRecepcionProveedor = listaRecepcionProveedor;
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   public List<Documento> getListaDocumento()
/* 1129:     */   {
/* 1130:     */     try
/* 1131:     */     {
/* 1132:1270 */       if (this.listaDocumento == null) {
/* 1133:1271 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.RECEPCION_BODEGA);
/* 1134:     */       }
/* 1135:     */     }
/* 1136:     */     catch (ExcepcionAS2 e)
/* 1137:     */     {
/* 1138:1274 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1139:     */     }
/* 1140:1276 */     return this.listaDocumento;
/* 1141:     */   }
/* 1142:     */   
/* 1143:     */   public void setListaDocumento(List<Documento> listaDocumento)
/* 1144:     */   {
/* 1145:1280 */     this.listaDocumento = listaDocumento;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public List<Bodega> getListaBodega()
/* 1149:     */   {
/* 1150:1284 */     if (this.listaBodega == null) {
/* 1151:1285 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 1152:     */     }
/* 1153:1287 */     return this.listaBodega;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void setListaBodega(List<Bodega> listaBodega)
/* 1157:     */   {
/* 1158:1291 */     this.listaBodega = listaBodega;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public List<Empresa> getListaEmpresaProveedor()
/* 1162:     */   {
/* 1163:1295 */     if (this.listaEmpresaProveedor == null) {
/* 1164:1296 */       this.listaEmpresaProveedor = this.servicioEmpresa.obtenerProveedores();
/* 1165:     */     }
/* 1166:1298 */     return this.listaEmpresaProveedor;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public void setListaEmpresaProveedor(List<Empresa> listaEmpresaProveedor)
/* 1170:     */   {
/* 1171:1302 */     this.listaEmpresaProveedor = listaEmpresaProveedor;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1175:     */   {
/* 1176:1306 */     if (this.listaDireccionEmpresa == null) {
/* 1177:1307 */       this.listaDireccionEmpresa = new ArrayList();
/* 1178:     */     }
/* 1179:1309 */     return this.listaDireccionEmpresa;
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1183:     */   {
/* 1184:1313 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1185:     */   }
/* 1186:     */   
/* 1187:     */   public DataTable getDtRecepcionProveedor()
/* 1188:     */   {
/* 1189:1317 */     return this.dtRecepcionProveedor;
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public void setDtRecepcionProveedor(DataTable dtRecepcionProveedor)
/* 1193:     */   {
/* 1194:1321 */     this.dtRecepcionProveedor = dtRecepcionProveedor;
/* 1195:     */   }
/* 1196:     */   
/* 1197:     */   public DataTable getDtDetalleRecepcionProveedor()
/* 1198:     */   {
/* 1199:1325 */     return this.dtDetalleRecepcionProveedor;
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public void setDtDetalleRecepcionProveedor(DataTable dtDetalleRecepcionProveedor)
/* 1203:     */   {
/* 1204:1329 */     this.dtDetalleRecepcionProveedor = dtDetalleRecepcionProveedor;
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public DataTable getDtDespachoPedidoFactura()
/* 1208:     */   {
/* 1209:1333 */     return this.dtDespachoPedidoFactura;
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void setDtDespachoPedidoFactura(DataTable dtDespachoPedidoFactura)
/* 1213:     */   {
/* 1214:1337 */     this.dtDespachoPedidoFactura = dtDespachoPedidoFactura;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public List<DetalleRecepcionProveedor> getListaDetalleRecepcionProveedor()
/* 1218:     */   {
/* 1219:1346 */     List<DetalleRecepcionProveedor> detalle = new ArrayList();
/* 1220:1347 */     for (DetalleRecepcionProveedor detalleRecepcionProveedor : getRecepcionProveedor().getListaDetalleRecepcionProveedor()) {
/* 1221:1349 */       if (!detalleRecepcionProveedor.isEliminado()) {
/* 1222:1350 */         detalle.add(detalleRecepcionProveedor);
/* 1223:     */       }
/* 1224:     */     }
/* 1225:1354 */     Collections.sort(detalle, new Comparator()
/* 1226:     */     {
/* 1227:     */       public int compare(DetalleRecepcionProveedor r1, DetalleRecepcionProveedor r2)
/* 1228:     */       {
/* 1229:1358 */         int resultado = 0;
/* 1230:1359 */         if ((r1.getDetalleFacturaProveedor() != null) && (r2.getDetalleFacturaProveedor() != null)) {
/* 1231:1361 */           resultado = r1.getDetalleFacturaProveedor().getIdDetalleFacturaProveedor() - r2.getDetalleFacturaProveedor().getIdDetalleFacturaProveedor();
/* 1232:     */         }
/* 1233:1363 */         return resultado;
/* 1234:     */       }
/* 1235:1367 */     });
/* 1236:1368 */     return detalle;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public String limpiarDescripciones()
/* 1240:     */   {
/* 1241:1372 */     for (DetalleRecepcionProveedor drp : getListaDetalleRecepcionProveedor()) {
/* 1242:1373 */       drp.setDescripcion("");
/* 1243:     */     }
/* 1244:1375 */     return null;
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   public List<Empresa> autocompletarProveedores(String consulta)
/* 1248:     */   {
/* 1249:1379 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public void cargarDatosRecepcionProveedor()
/* 1253:     */     throws ExcepcionAS2Compras
/* 1254:     */   {
/* 1255:1383 */     this.listaEdicionDetalleRecepcionProveedor.clear();
/* 1256:1384 */     this.totalCostoAnterior = BigDecimal.ZERO;
/* 1257:1385 */     this.totalCostoActual = BigDecimal.ZERO;
/* 1258:1386 */     this.facturaProveedorImportacion = null;
/* 1259:1387 */     this.recepcionProveedor = ((RecepcionProveedor)this.dtRecepcionProveedor.getRowData());
/* 1260:1388 */     this.recepcionProveedor = this.servicioRecepcionProveedor.cargarDetalle(this.recepcionProveedor);
/* 1261:1390 */     if (this.recepcionProveedor.getEstado().equals(Estado.ELABORADO))
/* 1262:     */     {
/* 1263:1392 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1264:1393 */       return;
/* 1265:     */     }
/* 1266:1396 */     for (DetalleRecepcionProveedor drp : this.recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1267:1397 */       if (!drp.isEliminado())
/* 1268:     */       {
/* 1269:1398 */         this.listaEdicionDetalleRecepcionProveedor.add(drp);
/* 1270:1399 */         InventarioProducto ip = this.servicioInventarioProducto.cargarDetalle(drp.getInventarioProducto().getIdInventarioProducto());
/* 1271:1400 */         this.totalCostoAnterior = this.totalCostoAnterior.add(ip.getCosto());
/* 1272:     */       }
/* 1273:     */     }
/* 1274:1405 */     FacturaProveedor fp = this.servicioFacturaProveedor.buscarPorRecepcionProveedor(this.recepcionProveedor.getIdRecepcionProveedor());
/* 1275:1406 */     if (fp != null)
/* 1276:     */     {
/* 1277:1407 */       fp = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(fp.getIdFacturaProveedor()));
/* 1278:1409 */       if ((fp.getDocumento().isIndicadorDocumentoExterior()) && (fp.getEstado().equals(Estado.CERRADO)))
/* 1279:     */       {
/* 1280:1410 */         this.facturaProveedorImportacion = fp.getFacturaProveedorImportacion();
/* 1281:1411 */         FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
/* 1282:1412 */         RequestContext.getCurrentInstance().execute("edicionRecepcionProveedorDialogo.show()");
/* 1283:1413 */         cargarCostoActual();
/* 1284:     */       }
/* 1285:     */     }
/* 1286:1417 */     else if ((!this.recepcionProveedor.getEstado().equals(Estado.ANULADO)) || (!this.recepcionProveedor.getEstado().equals(Estado.FACTURADO)))
/* 1287:     */     {
/* 1288:1418 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form");
/* 1289:1419 */       RequestContext.getCurrentInstance().execute("edicionRecepcionProveedorDialogo.show()");
/* 1290:1420 */       cargarCostoActual();
/* 1291:1421 */       return;
/* 1292:     */     }
/* 1293:1424 */     addErrorMessage(getLanguageController().getMensaje("msg_error_recepcion_proveedor"));
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public void cargarCostoActual()
/* 1297:     */   {
/* 1298:1429 */     this.totalCostoActual = BigDecimal.ZERO;
/* 1299:1430 */     for (DetalleRecepcionProveedor drp : this.listaEdicionDetalleRecepcionProveedor) {
/* 1300:1431 */       this.totalCostoActual = this.totalCostoActual.add(drp.getInventarioProducto().getCosto());
/* 1301:     */     }
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public void actualizarCantidad(DetalleRecepcionProveedor detalle)
/* 1305:     */   {
/* 1306:1438 */     if (detalle.getInventarioProducto() != null)
/* 1307:     */     {
/* 1308:1439 */       detalle.getInventarioProducto().setCantidad(detalle.getCantidad());
/* 1309:1440 */       detalle.getInventarioProducto().setCantidadDocumento(detalle.getCantidad());
/* 1310:     */     }
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public String guardarDetalleRecepcionProveedor()
/* 1314:     */   {
/* 1315:     */     try
/* 1316:     */     {
/* 1317:1447 */       this.servicioRecepcionProveedor.guardarDetalleRecepcionProveedor(this.recepcionProveedor, this.totalCostoAnterior, this.facturaProveedorImportacion != null ? this.facturaProveedorImportacion
/* 1318:1448 */         .getCuentaContableImportacionDiferenciaEnMasOEnMenos() : null);
/* 1319:1449 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1320:     */     }
/* 1321:     */     catch (AS2Exception e)
/* 1322:     */     {
/* 1323:1451 */       JsfUtil.addErrorMessage(e, "");
/* 1324:1452 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/* 1325:1453 */       e.printStackTrace();
/* 1326:     */     }
/* 1327:     */     catch (ExcepcionAS2Financiero e)
/* 1328:     */     {
/* 1329:1455 */       e.printStackTrace();
/* 1330:1456 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 1331:     */     }
/* 1332:     */     catch (ExcepcionAS2 e)
/* 1333:     */     {
/* 1334:1458 */       e.printStackTrace();
/* 1335:1459 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 1336:     */     }
/* 1337:1461 */     return "";
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public void seleccionarCuentaContableDiferenciaEnMasOEnMenos(SelectEvent event)
/* 1341:     */   {
/* 1342:1465 */     CuentaContable cuentaContableDiferenciaEnMasOEnMenos = (CuentaContable)event.getObject();
/* 1343:1466 */     this.facturaProveedorImportacion.setCuentaContableImportacionDiferenciaEnMasOEnMenos(cuentaContableDiferenciaEnMasOEnMenos);
/* 1344:     */   }
/* 1345:     */   
/* 1346:     */   public void validarCantidadRecepcion(DetalleRecepcionProveedor detalleRecepcionProveedor)
/* 1347:     */   {
/* 1348:     */     try
/* 1349:     */     {
/* 1350:1471 */       this.servicioRecepcionProveedor.validacionCantidadRecepcion(detalleRecepcionProveedor, this.recepcionProveedor, false);
/* 1351:     */     }
/* 1352:     */     catch (AS2Exception e)
/* 1353:     */     {
/* 1354:1473 */       JsfUtil.addErrorMessage(e, "");
/* 1355:1474 */       e.printStackTrace();
/* 1356:     */     }
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   public List<PedidoProveedor> getListaPedidoProveedor()
/* 1360:     */   {
/* 1361:1482 */     return this.listaPedidoProveedor;
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public void setListaPedidoProveedor(List<PedidoProveedor> listaPedidoProveedor)
/* 1365:     */   {
/* 1366:1490 */     this.listaPedidoProveedor = listaPedidoProveedor;
/* 1367:     */   }
/* 1368:     */   
/* 1369:     */   public Integer getIdFacturaProveedor()
/* 1370:     */   {
/* 1371:1497 */     return this.idFacturaProveedor;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public void setIdFacturaProveedor(Integer idFacturaProveedor)
/* 1375:     */   {
/* 1376:1505 */     this.idFacturaProveedor = idFacturaProveedor;
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public Integer getIdRecepcionProveedor()
/* 1380:     */   {
/* 1381:1512 */     return this.idRecepcionProveedor;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public void setIdRecepcionProveedor(Integer idRecepcionProveedor)
/* 1385:     */   {
/* 1386:1520 */     this.idRecepcionProveedor = idRecepcionProveedor;
/* 1387:     */   }
/* 1388:     */   
/* 1389:     */   public DataTable getDtInventarioProductoLote()
/* 1390:     */   {
/* 1391:1529 */     return this.dtInventarioProductoLote;
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/* 1395:     */   {
/* 1396:1539 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public InventarioProducto getInventarioProducto()
/* 1400:     */   {
/* 1401:1548 */     if (this.inventarioProducto == null)
/* 1402:     */     {
/* 1403:1549 */       this.inventarioProducto = new InventarioProducto();
/* 1404:1550 */       this.inventarioProducto.setOperacion(this.recepcionProveedor.getDocumento().getOperacion());
/* 1405:     */     }
/* 1406:1552 */     return this.inventarioProducto;
/* 1407:     */   }
/* 1408:     */   
/* 1409:     */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 1410:     */   {
/* 1411:1562 */     this.inventarioProducto = inventarioProducto;
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   private void cargarBodega(DetalleRecepcionProveedor detalleRecepcionProveedor)
/* 1415:     */   {
/* 1416:1567 */     if (AppUtil.getBodega() != null) {
/* 1417:1568 */       detalleRecepcionProveedor.setBodega(AppUtil.getBodega());
/* 1418:     */     } else {
/* 1419:1571 */       detalleRecepcionProveedor.setBodega(detalleRecepcionProveedor.getProducto().getBodegaCompra() == null ? new Bodega() : detalleRecepcionProveedor
/* 1420:1572 */         .getProducto().getBodegaCompra());
/* 1421:     */     }
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   public boolean isIndicadorImportacion()
/* 1425:     */   {
/* 1426:1582 */     return this.indicadorImportacion;
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   public void setIndicadorImportacion(boolean indicadorImportacion)
/* 1430:     */   {
/* 1431:1592 */     this.indicadorImportacion = indicadorImportacion;
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   public Lote getLoteCrear()
/* 1435:     */   {
/* 1436:1601 */     if (this.loteCrear == null) {
/* 1437:1602 */       this.loteCrear = new Lote();
/* 1438:     */     }
/* 1439:1604 */     return this.loteCrear;
/* 1440:     */   }
/* 1441:     */   
/* 1442:     */   public void setLoteCrear(Lote loteCrear)
/* 1443:     */   {
/* 1444:1614 */     this.loteCrear = loteCrear;
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public DetalleRecepcionProveedor getDetalleRecepcionProveedorSeleccionado()
/* 1448:     */   {
/* 1449:1623 */     return this.detalleRecepcionProveedorSeleccionado;
/* 1450:     */   }
/* 1451:     */   
/* 1452:     */   public void setDetalleRecepcionProveedorSeleccionado(DetalleRecepcionProveedor detalleRecepcionProveedorSeleccionado)
/* 1453:     */   {
/* 1454:1633 */     this.detalleRecepcionProveedorSeleccionado = detalleRecepcionProveedorSeleccionado;
/* 1455:     */   }
/* 1456:     */   
/* 1457:     */   public String getCodigoCabecera()
/* 1458:     */   {
/* 1459:1642 */     return this.codigoCabecera;
/* 1460:     */   }
/* 1461:     */   
/* 1462:     */   public void setCodigoCabecera(String codigoCabecera)
/* 1463:     */   {
/* 1464:1652 */     this.codigoCabecera = codigoCabecera;
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   public Producto getProductoCargaArchivo()
/* 1468:     */   {
/* 1469:1661 */     if (this.productoCargaArchivo == null) {
/* 1470:1662 */       this.productoCargaArchivo = new Producto();
/* 1471:     */     }
/* 1472:1664 */     return this.productoCargaArchivo;
/* 1473:     */   }
/* 1474:     */   
/* 1475:     */   public void setProductoCargaArchivo(Producto productoCargaArchivo)
/* 1476:     */   {
/* 1477:1674 */     this.productoCargaArchivo = productoCargaArchivo;
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public Integer getOpcionCarga()
/* 1481:     */   {
/* 1482:1683 */     return this.opcionCarga;
/* 1483:     */   }
/* 1484:     */   
/* 1485:     */   public void setOpcionCarga(Integer opcionCarga)
/* 1486:     */   {
/* 1487:1693 */     this.opcionCarga = opcionCarga;
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public BigDecimal getCantidadPesoLote()
/* 1491:     */   {
/* 1492:1702 */     return this.cantidadPesoLote;
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   public void setCantidadPesoLote(BigDecimal cantidadPesoLote)
/* 1496:     */   {
/* 1497:1712 */     this.cantidadPesoLote = cantidadPesoLote;
/* 1498:     */   }
/* 1499:     */   
/* 1500:     */   public Integer getNumeroLotes()
/* 1501:     */   {
/* 1502:1721 */     return this.numeroLotes;
/* 1503:     */   }
/* 1504:     */   
/* 1505:     */   public void setNumeroLotes(Integer numeroLotes)
/* 1506:     */   {
/* 1507:1731 */     this.numeroLotes = numeroLotes;
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public boolean isGenerarLotesAutomaticamente()
/* 1511:     */   {
/* 1512:1735 */     return this.generarLotesAutomaticamente;
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   public void setGenerarLotesAutomaticamente(boolean generarLotesAutomaticamente)
/* 1516:     */   {
/* 1517:1739 */     this.generarLotesAutomaticamente = generarLotesAutomaticamente;
/* 1518:     */   }
/* 1519:     */   
/* 1520:     */   public AS2Exception getExContabilizacion()
/* 1521:     */   {
/* 1522:1748 */     return this.exContabilizacion;
/* 1523:     */   }
/* 1524:     */   
/* 1525:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1526:     */   {
/* 1527:1758 */     this.exContabilizacion = exContabilizacion;
/* 1528:     */   }
/* 1529:     */   
/* 1530:     */   public int getLineasLotesArchivo()
/* 1531:     */   {
/* 1532:1762 */     return this.lineasLotesArchivo;
/* 1533:     */   }
/* 1534:     */   
/* 1535:     */   public void setLineasLotesArchivo(int lineasLotesArchivo)
/* 1536:     */   {
/* 1537:1766 */     this.lineasLotesArchivo = lineasLotesArchivo;
/* 1538:     */   }
/* 1539:     */   
/* 1540:     */   public BigDecimal getTotalCantidadLoteArchivo()
/* 1541:     */   {
/* 1542:1770 */     return this.totalCantidadLoteArchivo;
/* 1543:     */   }
/* 1544:     */   
/* 1545:     */   public void setTotalCantidadLoteArchivo(BigDecimal totalCantidadLoteArchivo)
/* 1546:     */   {
/* 1547:1774 */     this.totalCantidadLoteArchivo = totalCantidadLoteArchivo;
/* 1548:     */   }
/* 1549:     */   
/* 1550:     */   public DataTable getDtEdicionRecepcionProveedor()
/* 1551:     */   {
/* 1552:1778 */     return this.dtEdicionRecepcionProveedor;
/* 1553:     */   }
/* 1554:     */   
/* 1555:     */   public void setDtEdicionRecepcionProveedor(DataTable dtEdicionRecepcionProveedor)
/* 1556:     */   {
/* 1557:1782 */     this.dtEdicionRecepcionProveedor = dtEdicionRecepcionProveedor;
/* 1558:     */   }
/* 1559:     */   
/* 1560:     */   public void setListaEdicionDetalleRecepcionProveedor(List<DetalleRecepcionProveedor> listaEdicionDetalleRecepcionProveedor)
/* 1561:     */   {
/* 1562:1786 */     this.listaEdicionDetalleRecepcionProveedor = listaEdicionDetalleRecepcionProveedor;
/* 1563:     */   }
/* 1564:     */   
/* 1565:     */   public List<DetalleRecepcionProveedor> getListaEdicionDetalleRecepcionProveedor()
/* 1566:     */   {
/* 1567:1790 */     if (this.listaEdicionDetalleRecepcionProveedor == null) {
/* 1568:1791 */       this.listaEdicionDetalleRecepcionProveedor = new ArrayList();
/* 1569:     */     }
/* 1570:1793 */     return this.listaEdicionDetalleRecepcionProveedor;
/* 1571:     */   }
/* 1572:     */   
/* 1573:     */   public BigDecimal getTotalCostoAnterior()
/* 1574:     */   {
/* 1575:1800 */     return this.totalCostoAnterior;
/* 1576:     */   }
/* 1577:     */   
/* 1578:     */   public void setTotalCostoAnterior(BigDecimal totalCostoAnterior)
/* 1579:     */   {
/* 1580:1808 */     this.totalCostoAnterior = totalCostoAnterior;
/* 1581:     */   }
/* 1582:     */   
/* 1583:     */   public BigDecimal getTotalCostoActual()
/* 1584:     */   {
/* 1585:1815 */     return this.totalCostoActual;
/* 1586:     */   }
/* 1587:     */   
/* 1588:     */   public void setTotalCostoActual(BigDecimal totalCostoActual)
/* 1589:     */   {
/* 1590:1823 */     this.totalCostoActual = totalCostoActual;
/* 1591:     */   }
/* 1592:     */   
/* 1593:     */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 1594:     */   {
/* 1595:1827 */     return this.facturaProveedorImportacion;
/* 1596:     */   }
/* 1597:     */   
/* 1598:     */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 1599:     */   {
/* 1600:1831 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 1601:     */   }
/* 1602:     */   
/* 1603:     */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 1604:     */   {
/* 1605:1835 */     return this.listaCuentaContableBean;
/* 1606:     */   }
/* 1607:     */   
/* 1608:     */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 1609:     */   {
/* 1610:1839 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 1611:     */   }
/* 1612:     */   
/* 1613:     */   public boolean isActivaDiferencia()
/* 1614:     */   {
/* 1615:1843 */     return this.activaDiferencia;
/* 1616:     */   }
/* 1617:     */   
/* 1618:     */   public void setActivaDiferencia(boolean activaDiferencia)
/* 1619:     */   {
/* 1620:1847 */     this.activaDiferencia = activaDiferencia;
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   public LecturaBalanza getLecturaBalanza()
/* 1624:     */   {
/* 1625:1851 */     if (this.lecturaBalanza == null)
/* 1626:     */     {
/* 1627:1852 */       this.lecturaBalanza = new LecturaBalanza();
/* 1628:1853 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1629:1854 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/* 1630:1855 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/* 1631:     */     }
/* 1632:1857 */     return this.lecturaBalanza;
/* 1633:     */   }
/* 1634:     */   
/* 1635:     */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/* 1636:     */   {
/* 1637:1861 */     this.lecturaBalanza = lecturaBalanza;
/* 1638:     */   }
/* 1639:     */   
/* 1640:     */   public List<UnidadManejo> getListaUnidadManejo()
/* 1641:     */   {
/* 1642:1868 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/* 1643:     */     {
/* 1644:1869 */       this.listaUnidadManejo = new ArrayList();
/* 1645:1870 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 1646:1871 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/* 1647:     */       }
/* 1648:     */     }
/* 1649:1874 */     return this.listaUnidadManejo;
/* 1650:     */   }
/* 1651:     */   
/* 1652:     */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 1653:     */   {
/* 1654:1882 */     this.listaUnidadManejo = listaUnidadManejo;
/* 1655:     */   }
/* 1656:     */   
/* 1657:     */   public List<UnidadManejo> getListaPallet()
/* 1658:     */   {
/* 1659:1886 */     if (this.listaPallet == null)
/* 1660:     */     {
/* 1661:1887 */       Map<String, String> filters = new HashMap();
/* 1662:1888 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 1663:1889 */       filters.put("activo", "true");
/* 1664:1890 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/* 1665:1891 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/* 1666:     */     }
/* 1667:1893 */     return this.listaPallet;
/* 1668:     */   }
/* 1669:     */   
/* 1670:     */   public void setListaPallet(List<UnidadManejo> listaPallet)
/* 1671:     */   {
/* 1672:1897 */     this.listaPallet = listaPallet;
/* 1673:     */   }
/* 1674:     */   
/* 1675:     */   public boolean isMostrarBalanza()
/* 1676:     */   {
/* 1677:1901 */     if (this.mostrarBalanza == null) {
/* 1678:1902 */       this.mostrarBalanza = Boolean.valueOf((ParametrosSistema.getRecepcionUsaBalanza(AppUtil.getOrganizacion().getId()).booleanValue()) && (!isIndicadorFacturado()));
/* 1679:     */     }
/* 1680:1904 */     return this.mostrarBalanza.booleanValue();
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   public void setMostrarBalanza(boolean mostrarBalanza)
/* 1684:     */   {
/* 1685:1908 */     this.mostrarBalanza = Boolean.valueOf(mostrarBalanza);
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   public SesionControler getSesionControler()
/* 1689:     */   {
/* 1690:1912 */     return this.sesionControler;
/* 1691:     */   }
/* 1692:     */   
/* 1693:     */   public void setSesionControler(SesionControler sesionControler)
/* 1694:     */   {
/* 1695:1916 */     this.sesionControler = sesionControler;
/* 1696:     */   }
/* 1697:     */   
/* 1698:     */   public void cargarProductoSeleccionadoPesa()
/* 1699:     */   {
/* 1700:1920 */     if ((isMostrarBalanza()) && (this.lecturaBalanza != null))
/* 1701:     */     {
/* 1702:1921 */       Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 1703:1922 */       this.lecturaBalanza = null;
/* 1704:1923 */       getLecturaBalanza().setDispositivo(dispositivo);
/* 1705:1924 */       if (this.detalleRecepcionProveedorSeleccionado.getProducto().isIndicadorPesoBalanza()) {
/* 1706:1925 */         this.lecturaBalanza.setProducto(this.servicioProducto.buscarPorId(this.detalleRecepcionProveedorSeleccionado.getProducto().getId()));
/* 1707:     */       }
/* 1708:     */     }
/* 1709:     */   }
/* 1710:     */   
/* 1711:     */   public void calcularCantidad()
/* 1712:     */   {
/* 1713:1931 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 1714:1932 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 1715:     */     }
/* 1716:     */   }
/* 1717:     */   
/* 1718:     */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 1719:     */   {
/* 1720:1937 */     if (this.listaLecturaBalanza == null)
/* 1721:     */     {
/* 1722:1938 */       this.listaLecturaBalanza = new ArrayList();
/* 1723:1939 */       for (DetalleRecepcionProveedor detalle : this.recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1724:1940 */         if (!detalle.isEliminado()) {
/* 1725:1941 */           for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/* 1726:1942 */             if (!lectura.isEliminado()) {
/* 1727:1943 */               this.listaLecturaBalanza.add(lectura);
/* 1728:     */             }
/* 1729:     */           }
/* 1730:     */         }
/* 1731:     */       }
/* 1732:     */     }
/* 1733:1949 */     return this.listaLecturaBalanza;
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public DataTable getDtLecturaBalanza()
/* 1737:     */   {
/* 1738:1953 */     return this.dtLecturaBalanza;
/* 1739:     */   }
/* 1740:     */   
/* 1741:     */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/* 1742:     */   {
/* 1743:1957 */     this.dtLecturaBalanza = dtLecturaBalanza;
/* 1744:     */   }
/* 1745:     */   
/* 1746:     */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/* 1747:     */   {
/* 1748:     */     try
/* 1749:     */     {
/* 1750:1962 */       BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lectura);
/* 1751:     */       
/* 1752:1964 */       BigDecimal cantidad = cantidades[0];
/* 1753:1965 */       BigDecimal cantidadInformativa = cantidades[1];
/* 1754:     */       
/* 1755:1967 */       cantidad = lectura.getDetalleRecepcionProveedor().getCantidad().subtract(cantidad);
/* 1756:1968 */       lectura.getDetalleRecepcionProveedor().setCantidad(cantidad);
/* 1757:1970 */       if (lectura.getProducto().isIndicadorManejaUnidadInformativa())
/* 1758:     */       {
/* 1759:1971 */         cantidadInformativa = lectura.getDetalleRecepcionProveedor().getCantidadUnidadInformativa().subtract(cantidadInformativa);
/* 1760:1972 */         lectura.getDetalleRecepcionProveedor().setCantidadUnidadInformativa(cantidadInformativa);
/* 1761:     */       }
/* 1762:1975 */       lectura.setEliminado(true);
/* 1763:1976 */       this.listaLecturaBalanza = null;
/* 1764:1977 */       guardarBorrador();
/* 1765:     */     }
/* 1766:     */     catch (AS2Exception e)
/* 1767:     */     {
/* 1768:1979 */       e.printStackTrace();
/* 1769:1980 */       JsfUtil.addErrorMessage(e, "");
/* 1770:     */     }
/* 1771:     */   }
/* 1772:     */   
/* 1773:     */   public boolean isSecuenciaEditable()
/* 1774:     */   {
/* 1775:1986 */     setSecuenciaEditable(this.recepcionProveedor.getId() == 0);
/* 1776:1987 */     return super.isSecuenciaEditable();
/* 1777:     */   }
/* 1778:     */   
/* 1779:     */   public List<Dispositivo> getListaDispositivo()
/* 1780:     */   {
/* 1781:1991 */     if (this.listaDispositivo == null)
/* 1782:     */     {
/* 1783:1992 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1784:1993 */       filtros.put("activo", "true");
/* 1785:1994 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 1786:     */     }
/* 1787:1996 */     return this.listaDispositivo;
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public boolean isIndicadorFacturado()
/* 1791:     */   {
/* 1792:2000 */     return this.indicadorFacturado;
/* 1793:     */   }
/* 1794:     */   
/* 1795:     */   public void setIndicadorFacturado(boolean indicadorFacturado)
/* 1796:     */   {
/* 1797:2004 */     this.indicadorFacturado = indicadorFacturado;
/* 1798:     */   }
/* 1799:     */   
/* 1800:     */   public void actualizarAtributos(Lote lote, Producto producto)
/* 1801:     */   {
/* 1802:2009 */     producto = this.servicioProducto.cargarProductoConAtributoInstancia(producto.getIdProducto());
/* 1803:2010 */     lote.setAtributo1(producto.getAtributoProduccion1());
/* 1804:2011 */     lote.setAtributo2(producto.getAtributoProduccion2());
/* 1805:2012 */     lote.setAtributo3(producto.getAtributoProduccion3());
/* 1806:2013 */     lote.setAtributo4(producto.getAtributoProduccion4());
/* 1807:2014 */     lote.setAtributo5(producto.getAtributoProduccion5());
/* 1808:2015 */     lote.setAtributo6(producto.getAtributoProduccion6());
/* 1809:2016 */     lote.setAtributo7(producto.getAtributoProduccion7());
/* 1810:2017 */     lote.setAtributo8(producto.getAtributoProduccion8());
/* 1811:2018 */     lote.setAtributo9(producto.getAtributoProduccion9());
/* 1812:2019 */     lote.setAtributo10(producto.getAtributoProduccion10());
/* 1813:     */   }
/* 1814:     */   
/* 1815:     */   private void actualizarValorAtributoDetalleRecepcion(DetalleRecepcionProveedor detalleRecepcionPadre, DetalleRecepcionProveedor detalleRecepcionHijo)
/* 1816:     */   {
/* 1817:2027 */     if (detalleRecepcionHijo.getProducto().isIndicadorLote())
/* 1818:     */     {
/* 1819:2028 */       Lote lote = detalleRecepcionPadre.getInventarioProducto().getLote();
/* 1820:2030 */       if (lote.getValorAtributo1() != null) {
/* 1821:2031 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo1(lote.getValorAtributo1());
/* 1822:     */       }
/* 1823:2033 */       if (lote.getValorAtributo2() != null) {
/* 1824:2034 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo2(lote.getValorAtributo2());
/* 1825:     */       }
/* 1826:2036 */       if (lote.getValorAtributo3() != null) {
/* 1827:2037 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo3(lote.getValorAtributo3());
/* 1828:     */       }
/* 1829:2039 */       if (lote.getValorAtributo4() != null) {
/* 1830:2040 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo4(lote.getValorAtributo4());
/* 1831:     */       }
/* 1832:2042 */       if (lote.getValorAtributo5() != null) {
/* 1833:2043 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo5(lote.getValorAtributo5());
/* 1834:     */       }
/* 1835:2045 */       if (lote.getValorAtributo6() != null) {
/* 1836:2046 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo6(lote.getValorAtributo6());
/* 1837:     */       }
/* 1838:2048 */       if (lote.getValorAtributo7() != null) {
/* 1839:2049 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo7(lote.getValorAtributo7());
/* 1840:     */       }
/* 1841:2051 */       if (lote.getValorAtributo8() != null) {
/* 1842:2052 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo8(lote.getValorAtributo8());
/* 1843:     */       }
/* 1844:2054 */       if (lote.getValorAtributo9() != null) {
/* 1845:2055 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo9(lote.getValorAtributo9());
/* 1846:     */       }
/* 1847:2057 */       if (lote.getValorAtributo10() != null) {
/* 1848:2058 */         detalleRecepcionHijo.getInventarioProducto().getLote().setValorAtributo10(lote.getValorAtributo10());
/* 1849:     */       }
/* 1850:     */     }
/* 1851:     */   }
/* 1852:     */   
/* 1853:     */   private void validar(RecepcionProveedor recepcionProveedor)
/* 1854:     */     throws AS2Exception
/* 1855:     */   {
/* 1856:2067 */     for (DetalleRecepcionProveedor detalleRecepcion : recepcionProveedor.getListaDetalleRecepcionProveedor()) {
/* 1857:2068 */       if (!detalleRecepcion.isEliminado()) {
/* 1858:2069 */         validarAtributoDetalleRecepcionProveedor(detalleRecepcion);
/* 1859:     */       }
/* 1860:     */     }
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   private void validarAtributoDetalleRecepcionProveedor(DetalleRecepcionProveedor detalleRecepcion)
/* 1864:     */     throws AS2Exception
/* 1865:     */   {
/* 1866:2078 */     if (detalleRecepcion.getProducto().isIndicadorLote())
/* 1867:     */     {
/* 1868:2079 */       Lote lote = detalleRecepcion.getInventarioProducto().getLote();
/* 1869:2080 */       Producto producto = this.servicioProducto.cargarProductoConAtributoInstancia(detalleRecepcion.getProducto().getId());
/* 1870:2081 */       String mensaje = "";
/* 1871:2083 */       if ((producto.getAtributoProduccion1() != null) && (lote.getValorAtributo1() == null)) {
/* 1872:2084 */         mensaje = mensaje + producto.getAtributoProduccion1().getNombre() + ";";
/* 1873:     */       }
/* 1874:2086 */       if ((producto.getAtributoProduccion2() != null) && (lote.getValorAtributo2() == null)) {
/* 1875:2087 */         mensaje = mensaje + producto.getAtributoProduccion2().getNombre() + ";";
/* 1876:     */       }
/* 1877:2089 */       if ((producto.getAtributoProduccion3() != null) && (lote.getValorAtributo3() == null)) {
/* 1878:2090 */         mensaje = mensaje + producto.getAtributoProduccion3().getNombre() + ";";
/* 1879:     */       }
/* 1880:2092 */       if ((producto.getAtributoProduccion4() != null) && (lote.getValorAtributo4() == null)) {
/* 1881:2093 */         mensaje = mensaje + producto.getAtributoProduccion4().getNombre() + ";";
/* 1882:     */       }
/* 1883:2095 */       if ((producto.getAtributoProduccion5() != null) && (lote.getValorAtributo5() == null)) {
/* 1884:2096 */         mensaje = mensaje + producto.getAtributoProduccion5().getNombre() + ";";
/* 1885:     */       }
/* 1886:2098 */       if ((producto.getAtributoProduccion6() != null) && (lote.getValorAtributo6() == null)) {
/* 1887:2099 */         mensaje = mensaje + producto.getAtributoProduccion6().getNombre() + ";";
/* 1888:     */       }
/* 1889:2101 */       if ((producto.getAtributoProduccion7() != null) && (lote.getValorAtributo7() == null)) {
/* 1890:2102 */         mensaje = mensaje + producto.getAtributoProduccion7().getNombre() + ";";
/* 1891:     */       }
/* 1892:2104 */       if ((producto.getAtributoProduccion8() != null) && (lote.getValorAtributo8() == null)) {
/* 1893:2105 */         mensaje = mensaje + producto.getAtributoProduccion8().getNombre() + ";";
/* 1894:     */       }
/* 1895:2107 */       if ((producto.getAtributoProduccion9() != null) && (lote.getValorAtributo9() == null)) {
/* 1896:2108 */         mensaje = mensaje + producto.getAtributoProduccion9().getNombre() + ";";
/* 1897:     */       }
/* 1898:2110 */       if ((producto.getAtributoProduccion10() != null) && (lote.getValorAtributo10() == null)) {
/* 1899:2111 */         mensaje = mensaje + producto.getAtributoProduccion10().getNombre() + ";";
/* 1900:     */       }
/* 1901:2114 */       if (!mensaje.isEmpty()) {
/* 1902:2115 */         throw new AS2Exception("com.asinfo.as2.compras.procesos.MENSAJE_ERROR_PRODUCTO_MANEJA_ATRIBUTOS_INSTANCIA", new String[] { producto.getNombre(), mensaje });
/* 1903:     */       }
/* 1904:     */     }
/* 1905:     */   }
/* 1906:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.RecepcionProveedorBean
 * JD-Core Version:    0.7.0.1
 */