/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    6:     */ import com.asinfo.as2.controller.LanguageController;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    9:     */ import com.asinfo.as2.entities.Bodega;
/*   10:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   11:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   12:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   13:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   14:     */ import com.asinfo.as2.entities.DimensionContable;
/*   15:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   16:     */ import com.asinfo.as2.entities.Documento;
/*   17:     */ import com.asinfo.as2.entities.Empresa;
/*   18:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   19:     */ import com.asinfo.as2.entities.GuiaRemision;
/*   20:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   21:     */ import com.asinfo.as2.entities.Lote;
/*   22:     */ import com.asinfo.as2.entities.Organizacion;
/*   23:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   24:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   25:     */ import com.asinfo.as2.entities.Producto;
/*   26:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   27:     */ import com.asinfo.as2.entities.Subempresa;
/*   28:     */ import com.asinfo.as2.entities.Sucursal;
/*   29:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   30:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   31:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   32:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   33:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   34:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   35:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   36:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   37:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   38:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   39:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   40:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   41:     */ import com.asinfo.as2.inventario.procesos.controller.MovimientoInventarioBaseBean;
/*   42:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*   43:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   44:     */ import com.asinfo.as2.util.AppUtil;
/*   45:     */ import com.asinfo.as2.util.RutaArchivo;
/*   46:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   47:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   48:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   49:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   50:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*   51:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   52:     */ import java.io.BufferedInputStream;
/*   53:     */ import java.io.File;
/*   54:     */ import java.io.FileNotFoundException;
/*   55:     */ import java.io.InputStream;
/*   56:     */ import java.math.BigDecimal;
/*   57:     */ import java.math.RoundingMode;
/*   58:     */ import java.util.ArrayList;
/*   59:     */ import java.util.Collection;
/*   60:     */ import java.util.Date;
/*   61:     */ import java.util.HashMap;
/*   62:     */ import java.util.Iterator;
/*   63:     */ import java.util.List;
/*   64:     */ import java.util.Map;
/*   65:     */ import javax.annotation.PostConstruct;
/*   66:     */ import javax.ejb.EJB;
/*   67:     */ import javax.faces.bean.ManagedBean;
/*   68:     */ import javax.faces.bean.ViewScoped;
/*   69:     */ import javax.faces.component.html.HtmlInputText;
/*   70:     */ import javax.faces.component.html.HtmlSelectOneMenu;
/*   71:     */ import javax.faces.context.ExternalContext;
/*   72:     */ import javax.faces.context.FacesContext;
/*   73:     */ import javax.faces.context.PartialViewContext;
/*   74:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   75:     */ import javax.servlet.http.HttpSession;
/*   76:     */ import org.apache.log4j.Logger;
/*   77:     */ import org.primefaces.component.datatable.DataTable;
/*   78:     */ import org.primefaces.context.RequestContext;
/*   79:     */ import org.primefaces.event.FileUploadEvent;
/*   80:     */ import org.primefaces.event.SelectEvent;
/*   81:     */ import org.primefaces.model.LazyDataModel;
/*   82:     */ import org.primefaces.model.SortOrder;
/*   83:     */ import org.primefaces.model.StreamedContent;
/*   84:     */ import org.primefaces.model.UploadedFile;
/*   85:     */ 
/*   86:     */ @ManagedBean
/*   87:     */ @ViewScoped
/*   88:     */ public class DespachoClienteBean
/*   89:     */   extends MovimientoInventarioBaseBean
/*   90:     */ {
/*   91:     */   private static final long serialVersionUID = -7577609751620904609L;
/*   92:     */   @EJB
/*   93:     */   protected ServicioDespachoCliente servicioDespachoCliente;
/*   94:     */   @EJB
/*   95:     */   private ServicioProducto servicioProducto;
/*   96:     */   @EJB
/*   97:     */   private ServicioDocumento servicioDocumento;
/*   98:     */   @EJB
/*   99:     */   private ServicioEmpresa servicioEmpresa;
/*  100:     */   @EJB
/*  101:     */   private ServicioOrganizacion servicioOrganizacion;
/*  102:     */   @EJB
/*  103:     */   private ServicioPedidoCliente servicioPedidoCliente;
/*  104:     */   @EJB
/*  105:     */   private ServicioFacturaCliente servicioFacturaCliente;
/*  106:     */   @EJB
/*  107:     */   private ServicioLote servicioLote;
/*  108:     */   @EJB
/*  109:     */   private ServicioInventarioProducto servicioInventarioProducto;
/*  110:     */   @EJB
/*  111:     */   private ServicioBodega servicioBodega;
/*  112:     */   @EJB
/*  113:     */   private ServicioGuiaRemision servicioGuiaRemision;
/*  114:     */   private DespachoCliente despachoCliente;
/*  115:     */   private LazyDataModel<DespachoCliente> listaDespachoCliente;
/*  116:     */   private InventarioProducto inventarioProducto;
/*  117:     */   private DimensionContable proyecto;
/*  118:     */   private List<Documento> listaDocumentoDespacho;
/*  119:     */   private List<Bodega> listaBodega;
/*  120:     */   private List<Empresa> listaEmpresaCliente;
/*  121:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  122: 137 */   private List<PedidoCliente> listaPedidoCliente = new ArrayList();
/*  123:     */   private List<Subempresa> listaSubempresa;
/*  124:     */   private DataTable dtDespachoCliente;
/*  125:     */   protected DataTable dtDetalleDespachoCliente;
/*  126:     */   private DataTable dtInventarioProductoLote;
/*  127:     */   private Lote loteCrear;
/*  128:     */   private DetalleDespachoCliente detalleDespachoClienteSeleccionado;
/*  129:     */   private Integer idDespachoCliente;
/*  130:     */   private Integer idFacturaCliente;
/*  131:     */   private TipoOrganizacion tipoOrganizacion;
/*  132:     */   private String codigoCabecera;
/*  133:     */   private List<DespachoCliente> listaDespachoClienteReasignar;
/*  134:     */   private DataTable dtDespachoClienteReasignar;
/*  135:     */   private DespachoCliente despachoClienteReasignar;
/*  136:     */   private Empresa empresaReasignar;
/*  137:     */   private Date fechaReasignar;
/*  138:     */   private DespachoCliente despachoOrigen;
/*  139:     */   private DespachoCliente despachoDestino;
/*  140:     */   private List<DetalleDespachoCliente> detalleReasignarProductoOrigen;
/*  141:     */   private List<DetalleDespachoCliente> detalleReasignarProductoDestino;
/*  142:     */   private List<DetalleDespachoCliente> listaDetalleReasignarProductoOrigen;
/*  143:     */   private List<DetalleDespachoCliente> listaDetalleReasignarProductoDestino;
/*  144:     */   private List<DetalleDespachoCliente> listaDetalleProductoConsulta;
/*  145:     */   private DataTable dtDetalleReasignarProductoOrigen;
/*  146:     */   private DataTable dtDetalleReasignarProductoDestino;
/*  147:     */   private List<DetalleDespachoCliente> listaDetalleReasignarProductoOrigenFiltros;
/*  148:     */   private List<DetalleDespachoCliente> listaDetalleReasignarProductoOrigenFiltrado;
/*  149:     */   private List<DetalleDespachoCliente> listaDetalleReasignarProductoDestinoFiltrado;
/*  150: 178 */   private boolean cargaOrigen = false;
/*  151: 179 */   private boolean cargaDestino = false;
/*  152:     */   protected String mails;
/*  153:     */   private List<FacturaCliente> listaFacturaDespacho;
/*  154:     */   private FacturaCliente facturaDespacho;
/*  155:     */   private DataTable dtFacturaDespacho;
/*  156:     */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  157: 190 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  158:     */   
/*  159:     */   @PostConstruct
/*  160:     */   public void init()
/*  161:     */   {
/*  162: 194 */     getListaProductoBean().setIndicadorVenta(true);
/*  163:     */     
/*  164: 196 */     getListaProductoBean().setActivo(true);
/*  165:     */     
/*  166: 198 */     this.listaDespachoCliente = new LazyDataModel()
/*  167:     */     {
/*  168:     */       private static final long serialVersionUID = 1L;
/*  169:     */       
/*  170:     */       public List<DespachoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  171:     */       {
/*  172: 205 */         if (DespachoClienteBean.this.idDespachoCliente != null)
/*  173:     */         {
/*  174: 206 */           filters.put("idDespachoCliente", DespachoClienteBean.this.idDespachoCliente.toString());
/*  175: 207 */           DespachoClienteBean.this.idDespachoCliente = null;
/*  176:     */         }
/*  177: 211 */         if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  178: 212 */           filters.put("sucursal.idSucursal", String.valueOf(AppUtil.getSucursal().getIdSucursal()));
/*  179:     */         }
/*  180: 215 */         List<DespachoCliente> lista = new ArrayList();
/*  181:     */         
/*  182: 217 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  183:     */         
/*  184: 219 */         lista = DespachoClienteBean.this.servicioDespachoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  185: 220 */         DespachoClienteBean.this.listaDespachoCliente.setRowCount(DespachoClienteBean.this.servicioDespachoCliente.contarPorCriterio(filters));
/*  186:     */         
/*  187: 222 */         return lista;
/*  188:     */       }
/*  189:     */     };
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String editar()
/*  193:     */   {
/*  194: 235 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  195:     */     
/*  196:     */ 
/*  197:     */ 
/*  198:     */ 
/*  199:     */ 
/*  200:     */ 
/*  201:     */ 
/*  202:     */ 
/*  203:     */ 
/*  204:     */ 
/*  205:     */ 
/*  206:     */ 
/*  207:     */ 
/*  208:     */ 
/*  209:     */ 
/*  210:     */ 
/*  211:     */ 
/*  212:     */ 
/*  213:     */ 
/*  214:     */ 
/*  215:     */ 
/*  216:     */ 
/*  217: 258 */     return "";
/*  218:     */   }
/*  219:     */   
/*  220:     */   public String guardar()
/*  221:     */   {
/*  222:     */     try
/*  223:     */     {
/*  224: 270 */       this.servicioDespachoCliente.guardar(this.despachoCliente);
/*  225: 271 */       this.idDespachoCliente = Integer.valueOf(this.despachoCliente.getIdDespachoCliente());
/*  226:     */       
/*  227: 273 */       setEditado(false);
/*  228: 274 */       limpiar();
/*  229: 275 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  230: 276 */       return "despachoCliente.xhtml?faces-redirect=true&idDespachoCliente=" + this.idDespachoCliente;
/*  231:     */     }
/*  232:     */     catch (ExcepcionAS2Inventario e)
/*  233:     */     {
/*  234: 279 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  235:     */     }
/*  236:     */     catch (ExcepcionAS2Financiero e)
/*  237:     */     {
/*  238: 281 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  239:     */     }
/*  240:     */     catch (ExcepcionAS2 e)
/*  241:     */     {
/*  242: 283 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  243: 284 */       if (("msg_error_producto_no_lista_precios".equals(e.getCodigoExcepcion())) || 
/*  244: 285 */         ("msg_secuencia_no_encontrada".equals(e.getCodigoExcepcion())))
/*  245:     */       {
/*  246: 287 */         this.idDespachoCliente = Integer.valueOf(this.despachoCliente.getIdDespachoCliente());
/*  247: 288 */         setEditado(false);
/*  248: 289 */         limpiar();
/*  249: 290 */         return "facturaCliente.xhtml?faces-redirect=true&idDespachoCliente=" + this.idDespachoCliente;
/*  250:     */       }
/*  251:     */     }
/*  252:     */     catch (AS2Exception e)
/*  253:     */     {
/*  254: 293 */       this.exContabilizacion = e;
/*  255: 294 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  256: 295 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  257: 296 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/*  258:     */     }
/*  259:     */     catch (Exception e)
/*  260:     */     {
/*  261: 298 */       e.printStackTrace();
/*  262: 299 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  263: 300 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  264:     */     }
/*  265: 303 */     return "";
/*  266:     */   }
/*  267:     */   
/*  268:     */   public String eliminar()
/*  269:     */   {
/*  270: 313 */     if ((getDespachoCliente() != null) && (getDespachoCliente().getId() > 0)) {
/*  271:     */       try
/*  272:     */       {
/*  273: 315 */         this.servicioDespachoCliente.anular(this.despachoCliente, this.despachoCliente.getFecha());
/*  274: 316 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  275:     */       }
/*  276:     */       catch (ExcepcionAS2Financiero e)
/*  277:     */       {
/*  278: 319 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  279: 320 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/*  280:     */       }
/*  281:     */       catch (ExcepcionAS2Ventas e)
/*  282:     */       {
/*  283: 322 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  284: 323 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/*  285:     */       }
/*  286:     */       catch (ExcepcionAS2Inventario e)
/*  287:     */       {
/*  288: 325 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  289: 326 */         e.printStackTrace();
/*  290: 327 */         LOG.info("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/*  291:     */       }
/*  292:     */       catch (Exception e)
/*  293:     */       {
/*  294: 329 */         addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  295: 330 */         LOG.error("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/*  296:     */       }
/*  297:     */     } else {
/*  298: 333 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  299:     */     }
/*  300: 335 */     return "";
/*  301:     */   }
/*  302:     */   
/*  303:     */   public String agregarDetalle()
/*  304:     */   {
/*  305: 345 */     DetalleDespachoCliente detalleDespachoCliente = new DetalleDespachoCliente();
/*  306: 346 */     detalleDespachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  307: 347 */     detalleDespachoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  308: 348 */     detalleDespachoCliente.setProducto(new Producto());
/*  309: 349 */     detalleDespachoCliente.setBodega(AppUtil.getBodega());
/*  310: 350 */     detalleDespachoCliente.setDespachoCliente(getDespachoCliente());
/*  311:     */     
/*  312: 352 */     InventarioProducto inventarioProducto = new InventarioProducto();
/*  313: 353 */     inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/*  314: 354 */     detalleDespachoCliente.setInventarioProducto(inventarioProducto);
/*  315:     */     
/*  316: 356 */     getDespachoCliente().getListaDetalleDespachoCliente().add(detalleDespachoCliente);
/*  317:     */     
/*  318: 358 */     return "";
/*  319:     */   }
/*  320:     */   
/*  321:     */   public String eliminarDetalle()
/*  322:     */   {
/*  323: 362 */     DetalleDespachoCliente detalleDespachoCliente = (DetalleDespachoCliente)this.dtDetalleDespachoCliente.getRowData();
/*  324: 363 */     detalleDespachoCliente.setEliminado(true);
/*  325: 364 */     return "";
/*  326:     */   }
/*  327:     */   
/*  328:     */   public String limpiar()
/*  329:     */   {
/*  330: 375 */     setEditado(false);
/*  331:     */     
/*  332: 377 */     crearDespachoCliente();
/*  333:     */     
/*  334: 379 */     return "";
/*  335:     */   }
/*  336:     */   
/*  337:     */   private void crearDespachoCliente()
/*  338:     */   {
/*  339: 386 */     this.despachoCliente = new DespachoCliente();
/*  340: 387 */     this.despachoCliente.setFecha(new Date());
/*  341: 388 */     this.despachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  342: 389 */     this.despachoCliente.setSucursal(AppUtil.getSucursal());
/*  343: 390 */     this.despachoCliente.setEstado(Estado.PROCESADO);
/*  344: 391 */     this.despachoCliente.setNumero("");
/*  345: 392 */     if (!getListaDocumentoDespacho().isEmpty())
/*  346:     */     {
/*  347: 393 */       Documento documento = (Documento)getListaDocumentoDespacho().get(0);
/*  348: 394 */       this.despachoCliente.setDocumento(documento);
/*  349: 395 */       actualizarDocumento();
/*  350:     */     }
/*  351:     */   }
/*  352:     */   
/*  353:     */   public String cargarDatos()
/*  354:     */   {
/*  355: 407 */     return "";
/*  356:     */   }
/*  357:     */   
/*  358:     */   public void actualizarProducto(AjaxBehaviorEvent event)
/*  359:     */   {
/*  360: 416 */     DetalleDespachoCliente detalleDespachoCliente = null;
/*  361:     */     try
/*  362:     */     {
/*  363: 418 */       String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  364: 419 */       detalleDespachoCliente = (DetalleDespachoCliente)this.dtDetalleDespachoCliente.getRowData();
/*  365: 420 */       cargarProductoDesdeCodigoEnDetalle(codigo, detalleDespachoCliente);
/*  366:     */     }
/*  367:     */     catch (ExcepcionAS2 e)
/*  368:     */     {
/*  369: 422 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  370: 423 */       detalleDespachoCliente.getProducto().setCodigo("");
/*  371: 424 */       detalleDespachoCliente.getProducto().setNombre("");
/*  372:     */     }
/*  373:     */   }
/*  374:     */   
/*  375:     */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleDespachoCliente detalleDespachoCliente)
/*  376:     */     throws ExcepcionAS2
/*  377:     */   {
/*  378: 429 */     Producto producto = null;
/*  379:     */     
/*  380: 431 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.DESPACHO_BODEGA);
/*  381:     */     
/*  382: 433 */     detalleDespachoCliente.setProducto(producto);
/*  383: 434 */     detalleDespachoCliente.getInventarioProducto().setProducto(producto);
/*  384: 435 */     detalleDespachoCliente.setUnidadVenta(producto.getUnidadVenta());
/*  385: 436 */     detalleDespachoCliente.setIndicadorManejoPeso(producto.isIndicadorManejoPeso());
/*  386: 437 */     if (producto.getLote() != null)
/*  387:     */     {
/*  388: 438 */       detalleDespachoCliente.getInventarioProducto().setLote(this.servicioLote.getAtributosLote(producto.getLote().getIdLote(), 
/*  389: 439 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos()));
/*  390: 440 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), detalleDespachoCliente.getBodega().getIdBodega(), producto
/*  391: 441 */         .getLote().getIdLote(), this.despachoCliente.getFecha());
/*  392: 442 */       detalleDespachoCliente.setSaldo(saldo);
/*  393: 444 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  394: 445 */         detalleDespachoCliente.setCantidad(saldo.setScale(4, RoundingMode.HALF_UP));
/*  395:     */       }
/*  396:     */     }
/*  397: 448 */     cargarBodega(detalleDespachoCliente);
/*  398:     */   }
/*  399:     */   
/*  400:     */   public void agregarDetalleDesdeCodigoCabecera()
/*  401:     */   {
/*  402: 453 */     DetalleDespachoCliente detalleDespachoCliente = null;
/*  403:     */     try
/*  404:     */     {
/*  405: 455 */       detalleDespachoCliente = new DetalleDespachoCliente();
/*  406: 456 */       detalleDespachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  407: 457 */       detalleDespachoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  408: 458 */       detalleDespachoCliente.setProducto(new Producto());
/*  409: 459 */       detalleDespachoCliente.setBodega(AppUtil.getBodega());
/*  410: 460 */       detalleDespachoCliente.setDespachoCliente(getDespachoCliente());
/*  411:     */       
/*  412: 462 */       InventarioProducto inventarioProducto = new InventarioProducto();
/*  413: 463 */       inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/*  414: 464 */       detalleDespachoCliente.setInventarioProducto(inventarioProducto);
/*  415:     */       
/*  416: 466 */       getDespachoCliente().getListaDetalleDespachoCliente().add(detalleDespachoCliente);
/*  417: 467 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, detalleDespachoCliente);
/*  418: 468 */       this.codigoCabecera = "";
/*  419:     */     }
/*  420:     */     catch (ExcepcionAS2 e)
/*  421:     */     {
/*  422: 470 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  423: 471 */       detalleDespachoCliente.getProducto().setCodigo("");
/*  424: 472 */       detalleDespachoCliente.getProducto().setNombre("");
/*  425: 473 */       detalleDespachoCliente.setEliminado(true);
/*  426: 474 */       this.codigoCabecera = "";
/*  427:     */     }
/*  428:     */   }
/*  429:     */   
/*  430:     */   public void actualizarSaldo(SelectEvent event)
/*  431:     */   {
/*  432: 484 */     DetalleDespachoCliente detalleDespachoCliente = (DetalleDespachoCliente)this.dtDetalleDespachoCliente.getRowData();
/*  433: 485 */     Lote lote = (Lote)event.getObject();
/*  434: 486 */     actualizarSaldo(detalleDespachoCliente, lote);
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void actualizarSaldo(DetalleDespachoCliente detalleDespachoCliente, Lote lote)
/*  438:     */   {
/*  439: 490 */     lote = this.servicioLote.getAtributosLote(lote.getId(), AppUtil.getOrganizacion().getOrganizacionConfiguracion().getId());
/*  440: 491 */     detalleDespachoCliente.getInventarioProducto().setLote(lote);
/*  441:     */     
/*  442: 493 */     BigDecimal saldo = this.servicioProducto.getSaldoLote(detalleDespachoCliente.getProducto().getIdProducto(), detalleDespachoCliente
/*  443: 494 */       .getBodega().getIdBodega(), detalleDespachoCliente.getInventarioProducto().getLote().getIdLote(), this.despachoCliente
/*  444: 495 */       .getFecha());
/*  445: 496 */     detalleDespachoCliente.setSaldo(saldo);
/*  446:     */   }
/*  447:     */   
/*  448:     */   private void cargarBodega(DetalleDespachoCliente detalleDespacho)
/*  449:     */   {
/*  450: 501 */     if (AppUtil.getBodega() != null) {
/*  451: 502 */       detalleDespacho.setBodega(AppUtil.getBodega());
/*  452:     */     } else {
/*  453: 504 */       detalleDespacho.setBodega(detalleDespacho
/*  454: 505 */         .getProducto().getBodegaVenta() == null ? new Bodega() : detalleDespacho.getProducto().getBodegaVenta());
/*  455:     */     }
/*  456:     */   }
/*  457:     */   
/*  458:     */   public void actualizarCliente(SelectEvent event)
/*  459:     */   {
/*  460: 510 */     getDespachoCliente().setEmpresa((Empresa)event.getObject());
/*  461: 511 */     getDespachoCliente().setPedidoCliente(null);
/*  462: 512 */     this.listaDireccionEmpresa = null;
/*  463: 513 */     getDespachoCliente().setDireccionEmpresa(null);
/*  464: 514 */     cargarDirecciones();
/*  465: 515 */     actualizarListaPedidoClienteADespachar();
/*  466: 516 */     cargarSubempresas();
/*  467:     */   }
/*  468:     */   
/*  469:     */   public void cargarSubempresas()
/*  470:     */   {
/*  471: 520 */     if (this.despachoCliente.getEmpresa() != null) {
/*  472: 521 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.despachoCliente.getEmpresa().getId());
/*  473:     */     }
/*  474:     */   }
/*  475:     */   
/*  476:     */   private void actualizarListaPedidoClienteADespachar()
/*  477:     */   {
/*  478: 527 */     this.listaPedidoCliente.clear();
/*  479: 529 */     if (getDespachoCliente().getEmpresa() != null)
/*  480:     */     {
/*  481: 530 */       HashMap<String, String> filters = new HashMap();
/*  482: 531 */       filters.put("empresa.idEmpresa", String.valueOf(getDespachoCliente().getEmpresa().getId()));
/*  483: 533 */       if (getDespachoCliente().getSubempresa() != null) {
/*  484: 534 */         filters.put("subempresa.idSubempresa", "" + getDespachoCliente().getSubempresa().getId());
/*  485:     */       }
/*  486: 536 */       filters.put("estado", "=" + Estado.PROCESADO.toString());
/*  487: 537 */       this.listaPedidoCliente = this.servicioPedidoCliente.obtenerListaCombo("numero", true, filters);
/*  488: 539 */       if ((getDespachoCliente().getPedidoCliente() != null) && (!this.listaPedidoCliente.contains(getDespachoCliente().getPedidoCliente()))) {
/*  489: 540 */         this.listaPedidoCliente.add(getDespachoCliente().getPedidoCliente());
/*  490:     */       }
/*  491:     */     }
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void cargarDirecciones()
/*  495:     */   {
/*  496: 552 */     if (getDespachoCliente().getSubempresa() != null) {
/*  497: 553 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getDespachoCliente().getSubempresa().getEmpresa().getId());
/*  498:     */     }
/*  499: 556 */     if ((this.listaDireccionEmpresa == null) || (this.listaDireccionEmpresa.isEmpty())) {
/*  500: 557 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getDespachoCliente().getEmpresa().getId());
/*  501:     */     }
/*  502: 560 */     if (getDespachoCliente().getDireccionEmpresa() == null) {
/*  503: 563 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/*  504: 564 */         if (de.isIndicadorDireccionEnvio()) {
/*  505: 565 */           getDespachoCliente().setDireccionEmpresa(de);
/*  506:     */         }
/*  507:     */       }
/*  508:     */     }
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void cargarProducto()
/*  512:     */   {
/*  513:     */     try
/*  514:     */     {
/*  515: 579 */       Producto producto = getListaProductoBean().getProducto();
/*  516: 580 */       if (producto != null)
/*  517:     */       {
/*  518: 581 */         DetalleDespachoCliente detalleDespachoCliente = new DetalleDespachoCliente();
/*  519: 582 */         detalleDespachoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  520: 583 */         detalleDespachoCliente.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  521: 584 */         detalleDespachoCliente.setProducto(producto);
/*  522: 585 */         detalleDespachoCliente.setUnidadVenta(producto.getUnidadVenta());
/*  523:     */         
/*  524: 587 */         cargarBodega(detalleDespachoCliente);
/*  525:     */         
/*  526: 589 */         InventarioProducto inventarioProducto = new InventarioProducto();
/*  527: 590 */         inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/*  528: 591 */         inventarioProducto.setProducto(producto);
/*  529: 592 */         detalleDespachoCliente.setInventarioProducto(inventarioProducto);
/*  530: 593 */         detalleDespachoCliente.setDespachoCliente(this.despachoCliente);
/*  531: 594 */         detalleDespachoCliente.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/*  532: 595 */         detalleDespachoCliente.setIndicadorManejoPeso(producto.isIndicadorManejoPeso());
/*  533: 596 */         if (getListaProductoBean().getSaldoProductoLote() != null)
/*  534:     */         {
/*  535: 597 */           detalleDespachoCliente.setBodega(getListaProductoBean().getSaldoProductoLote().getBodega());
/*  536: 598 */           detalleDespachoCliente.setCantidad(getListaProductoBean().getSaldoProductoLote().getSaldo().setScale(2, RoundingMode.HALF_UP));
/*  537: 599 */           detalleDespachoCliente.setSaldo(detalleDespachoCliente.getCantidad());
/*  538: 600 */           inventarioProducto.setBodega(detalleDespachoCliente.getBodega());
/*  539: 601 */           inventarioProducto.setLote(this.servicioLote.getAtributosLote(getListaProductoBean().getSaldoProductoLote().getLote().getId(), 
/*  540: 602 */             AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos()));
/*  541: 603 */           detalleDespachoCliente.setBodega(getListaProductoBean().getSaldoProductoLote().getBodega());
/*  542: 604 */           inventarioProducto.setCantidad(detalleDespachoCliente.getCantidad());
/*  543:     */         }
/*  544:     */         else
/*  545:     */         {
/*  546: 606 */           BigDecimal saldo = this.servicioProducto.getSaldo(producto.getIdProducto(), detalleDespachoCliente.getBodega().getIdBodega(), this.despachoCliente
/*  547: 607 */             .getFecha());
/*  548: 608 */           detalleDespachoCliente.setSaldo(saldo);
/*  549:     */         }
/*  550: 611 */         this.despachoCliente.getListaDetalleDespachoCliente().add(detalleDespachoCliente);
/*  551: 612 */         getListaProductoBean().setProducto(null);
/*  552: 613 */         getListaProductoBean().setSaldoProductoLote(null);
/*  553:     */       }
/*  554:     */     }
/*  555:     */     catch (Exception e)
/*  556:     */     {
/*  557: 617 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  558: 618 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  559:     */     }
/*  560:     */     finally
/*  561:     */     {
/*  562: 620 */       getListaProductoBean().setProducto(null);
/*  563: 621 */       getListaProductoBean().setSaldoProductoLote(null);
/*  564:     */     }
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void cargarDetallePedidoListener()
/*  568:     */   {
/*  569: 630 */     for (Iterator localIterator = getDespachoCliente().getListaDetalleDespachoCliente().iterator(); localIterator.hasNext();)
/*  570:     */     {
/*  571: 630 */       ddc = (DetalleDespachoCliente)localIterator.next();
/*  572: 631 */       if (!ddc.isIndicadorProyecto()) {
/*  573: 632 */         ddc.setEliminado(true);
/*  574:     */       }
/*  575:     */     }
/*  576:     */     DetalleDespachoCliente ddc;
/*  577: 636 */     if (getDespachoCliente().getPedidoCliente() != null)
/*  578:     */     {
/*  579: 637 */       getDespachoCliente().setDireccionEmpresa(getDespachoCliente().getPedidoCliente().getDireccionEmpresa());
/*  580: 638 */       getDespachoCliente().setDescripcion(getDespachoCliente().getPedidoCliente().getDescripcion());
/*  581: 639 */       getDespachoCliente().setSubempresa(getDespachoCliente().getPedidoCliente().getSubempresa());
/*  582: 640 */       getDespachoCliente().setProyecto(getDespachoCliente().getPedidoCliente().getProyecto());
/*  583: 641 */       getDespachoCliente().setTransportista(getDespachoCliente().getPedidoCliente().getTransportista());
/*  584:     */       
/*  585:     */ 
/*  586: 644 */       Object listaDPC = this.servicioPedidoCliente.obtenerListaDetallePedidoPorDespachar(getDespachoCliente().getPedidoCliente().getId());
/*  587: 646 */       for (DetallePedidoCliente dpc : (List)listaDPC)
/*  588:     */       {
/*  589: 648 */         DetalleDespachoCliente dpCliente = obtenerDetallePorProducto(dpc.getProducto(), null);
/*  590: 649 */         if (dpCliente == null)
/*  591:     */         {
/*  592: 650 */           dpCliente = new DetalleDespachoCliente();
/*  593: 651 */           this.despachoCliente.getListaDetalleDespachoCliente().add(dpCliente);
/*  594:     */           
/*  595: 653 */           dpCliente.setDespachoCliente(this.despachoCliente);
/*  596: 654 */           dpCliente.setProducto(dpc.getProducto());
/*  597: 655 */           dpCliente.setUnidadVenta(dpc.getProducto().getUnidadVenta());
/*  598: 656 */           dpCliente.setCantidad(dpc.getCantidadPorDespachar().setScale(4, RoundingMode.HALF_UP));
/*  599: 657 */           dpCliente.setDetallePedidoCliente(dpc);
/*  600: 658 */           dpCliente.setDescripcion(dpc.getDescripcion());
/*  601: 659 */           InventarioProducto inventarioProducto = new InventarioProducto();
/*  602: 660 */           inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/*  603: 661 */           inventarioProducto.setProducto(dpc.getProducto());
/*  604: 662 */           dpCliente.setInventarioProducto(inventarioProducto);
/*  605: 663 */           cargarBodega(dpCliente);
/*  606:     */         }
/*  607: 665 */         dpCliente.setDetallePedidoCliente(dpc);
/*  608:     */       }
/*  609:     */     }
/*  610: 669 */     cargarDirecciones();
/*  611:     */   }
/*  612:     */   
/*  613:     */   public String emitirGuiaRemision()
/*  614:     */   {
/*  615: 673 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  616: 674 */     if ((getDespachoCliente() != null) && (getDespachoCliente().getId() > 0) && (getDespachoCliente().getEstado() != Estado.ANULADO) && (
/*  617: 675 */       (getDespachoCliente().getGuiaRemision() == null) || (getDespachoCliente().getGuiaRemision().getEstado() == null) || (
/*  618: 676 */       (!getDespachoCliente().getGuiaRemision().getEstado().equals(Estado.EN_ESPERA)) && 
/*  619: 677 */       (!getDespachoCliente().getGuiaRemision().getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA)))))
/*  620:     */     {
/*  621: 679 */       HttpSession session = (HttpSession)context.getSession(true);
/*  622: 680 */       session.setAttribute("despachoCliente", this.despachoCliente);
/*  623: 681 */       return "/paginas/ventas/procesos/guiaRemision.xhtml?faces-redirect=true";
/*  624:     */     }
/*  625: 683 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  626: 684 */     return "";
/*  627:     */   }
/*  628:     */   
/*  629:     */   public String guardarLote()
/*  630:     */   {
/*  631:     */     try
/*  632:     */     {
/*  633: 691 */       this.servicioLote.guardar(this.loteCrear);
/*  634: 692 */       this.detalleDespachoClienteSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/*  635: 693 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  636:     */     }
/*  637:     */     catch (Exception e)
/*  638:     */     {
/*  639: 695 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  640: 696 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  641:     */     }
/*  642: 698 */     return "";
/*  643:     */   }
/*  644:     */   
/*  645:     */   public String creacionLote()
/*  646:     */   {
/*  647: 702 */     this.detalleDespachoClienteSeleccionado = ((DetalleDespachoCliente)this.dtDetalleDespachoCliente.getRowData());
/*  648: 703 */     this.loteCrear = new Lote();
/*  649: 704 */     this.loteCrear.setActivo(true);
/*  650: 705 */     this.loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  651: 706 */     this.loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  652: 707 */     this.loteCrear.setProducto(this.detalleDespachoClienteSeleccionado.getProducto());
/*  653: 709 */     if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA))
/*  654:     */     {
/*  655: 710 */       Integer numeroSerie = this.servicioOrganizacion.actualizarSecuenciaInicioSerie(AppUtil.getOrganizacion());
/*  656: 711 */       this.loteCrear.setCodigo(String.valueOf(numeroSerie));
/*  657: 712 */       this.loteCrear.setIndicadorMovimientoInterno(true);
/*  658: 713 */       this.loteCrear.setFechaCaducidad(new Date());
/*  659: 714 */       this.loteCrear.setFechaFabricacion(new Date());
/*  660:     */     }
/*  661: 716 */     return "";
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void cargarDespachoDesdeFactura()
/*  665:     */   {
/*  666: 724 */     if (this.idFacturaCliente != null)
/*  667:     */     {
/*  668: 726 */       limpiar();
/*  669: 728 */       if (getListaDocumentoDespacho().size() > 0) {
/*  670: 729 */         this.despachoCliente.setDocumento((Documento)getListaDocumentoDespacho().get(0));
/*  671:     */       }
/*  672: 732 */       List<DetalleFacturaCliente> listaDetalleFacturaClientePorDespachar = this.servicioFacturaCliente.buscarDetallesNoDespachados(this.idFacturaCliente);
/*  673: 733 */       if (listaDetalleFacturaClientePorDespachar.size() == 0)
/*  674:     */       {
/*  675: 734 */         addInfoMessage(getLanguageController().getMensaje("msg_info_ya_existe_despacho_factura"));
/*  676:     */       }
/*  677:     */       else
/*  678:     */       {
/*  679: 737 */         FacturaCliente facturaCliente = this.servicioFacturaCliente.cargarCabecera(this.idFacturaCliente);
/*  680:     */         
/*  681: 739 */         this.despachoCliente.setFacturaCliente(facturaCliente);
/*  682: 740 */         this.despachoCliente.setIndicadorGeneradoFactura(true);
/*  683: 741 */         this.despachoCliente.setEmpresa(facturaCliente.getEmpresa());
/*  684: 742 */         this.despachoCliente.setSubempresa(facturaCliente.getSubempresa());
/*  685: 743 */         this.despachoCliente.setDescripcion(facturaCliente.getDescripcion());
/*  686: 744 */         this.despachoCliente.setPedidoCliente(facturaCliente.getPedidoCliente());
/*  687: 745 */         this.despachoCliente.setDireccionEmpresa(facturaCliente.getDireccionEmpresa());
/*  688: 746 */         this.despachoCliente.setProyecto(facturaCliente.getProyecto());
/*  689: 747 */         if (facturaCliente.getPedidoCliente() != null) {
/*  690: 748 */           this.despachoCliente.setTransportista(facturaCliente.getPedidoCliente().getTransportista());
/*  691:     */         }
/*  692: 751 */         for (DetalleFacturaCliente dfc : listaDetalleFacturaClientePorDespachar)
/*  693:     */         {
/*  694: 752 */           DetalleDespachoCliente ddc = new DetalleDespachoCliente();
/*  695: 753 */           ddc.setDetalleFacturaCliente(dfc);
/*  696: 754 */           ddc.setProducto(dfc.getProducto());
/*  697: 755 */           ddc.setUnidadVenta(dfc.getProducto().getUnidadVenta());
/*  698: 756 */           ddc.setCantidad(dfc.getCantidad().setScale(4, RoundingMode.HALF_UP));
/*  699: 757 */           ddc.setDetalleFacturaCliente(dfc);
/*  700: 758 */           ddc.setDespachoCliente(this.despachoCliente);
/*  701: 759 */           ddc.setPeso(dfc.getPeso());
/*  702: 760 */           if (dfc.getProducto().getTipoProducto() == TipoProducto.ARTICULO)
/*  703:     */           {
/*  704: 761 */             InventarioProducto inventarioProducto = new InventarioProducto();
/*  705: 762 */             inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/*  706: 763 */             inventarioProducto.setProducto(ddc.getProducto());
/*  707: 764 */             inventarioProducto.setPeso(dfc.getPeso());
/*  708: 765 */             ddc.setInventarioProducto(inventarioProducto);
/*  709:     */           }
/*  710:     */           else
/*  711:     */           {
/*  712: 767 */             ddc.setInventarioProducto(null);
/*  713:     */           }
/*  714: 770 */           this.despachoCliente.getListaDetalleDespachoCliente().add(ddc);
/*  715: 771 */           cargarBodega(ddc);
/*  716:     */         }
/*  717: 773 */         cargarDirecciones();
/*  718: 774 */         cargarSubempresas();
/*  719: 775 */         setEditado(true);
/*  720:     */       }
/*  721: 778 */       this.idFacturaCliente = null;
/*  722:     */     }
/*  723:     */   }
/*  724:     */   
/*  725:     */   public List<Lote> autocompletarLotes(String consulta)
/*  726:     */   {
/*  727: 784 */     DetalleDespachoCliente detalleDespachoCliente = (DetalleDespachoCliente)this.dtDetalleDespachoCliente.getRowData();
/*  728: 785 */     return this.servicioLote.autocompletarLote(detalleDespachoCliente.getProducto(), consulta + "~LIKE" + "~MAX_RESULT");
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/*  732:     */   {
/*  733: 790 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/*  734: 792 */     if (subempresa != null)
/*  735:     */     {
/*  736: 793 */       this.despachoCliente.setSubempresa(subempresa);
/*  737:     */       
/*  738: 795 */       cargarDirecciones();
/*  739: 796 */       actualizarListaPedidoClienteADespachar();
/*  740:     */     }
/*  741:     */   }
/*  742:     */   
/*  743:     */   public void cargarDetalleDespachoClienteTxt(FileUploadEvent event)
/*  744:     */   {
/*  745:     */     try
/*  746:     */     {
/*  747: 808 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  748: 809 */       this.servicioDespachoCliente.cargarDetalleDespachoArchivoTexto(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion(), this.despachoCliente, input, 
/*  749: 810 */         AppUtil.getBodega());
/*  750:     */     }
/*  751:     */     catch (ExcepcionAS2 e)
/*  752:     */     {
/*  753: 812 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  754: 813 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  755:     */     }
/*  756:     */     catch (Exception e)
/*  757:     */     {
/*  758: 815 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  759: 816 */       e.printStackTrace();
/*  760:     */     }
/*  761:     */   }
/*  762:     */   
/*  763:     */   public String seleccionar()
/*  764:     */   {
/*  765: 821 */     this.despachoCliente = ((DespachoCliente)this.dtDespachoCliente.getRowData());
/*  766: 822 */     return "";
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void processDownload()
/*  770:     */   {
/*  771:     */     try
/*  772:     */     {
/*  773: 828 */       DespachoCliente dc = (DespachoCliente)this.dtDespachoCliente.getRowData();
/*  774: 829 */       String fileName = dc.getPdf();
/*  775: 830 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "despacho_cliente");
/*  776: 832 */       if (fileName == null) {
/*  777: 833 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  778:     */       } else {
/*  779: 835 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  780:     */       }
/*  781:     */     }
/*  782:     */     catch (Exception e)
/*  783:     */     {
/*  784: 839 */       e.printStackTrace();
/*  785: 840 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  786:     */     }
/*  787:     */   }
/*  788:     */   
/*  789:     */   public void processUpload(FileUploadEvent event)
/*  790:     */   {
/*  791:     */     try
/*  792:     */     {
/*  793: 854 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "despacho_cliente");
/*  794:     */       
/*  795: 856 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getDespachoCliente().getNumero(), uploadDir);
/*  796:     */       
/*  797:     */ 
/*  798: 859 */       HashMap<String, Object> campos = new HashMap();
/*  799: 860 */       campos.put("pdf", fileName);
/*  800: 861 */       this.servicioDespachoCliente.actualizarAtributoEntidad(this.despachoCliente, campos);
/*  801:     */       
/*  802: 863 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  803:     */     }
/*  804:     */     catch (Exception e)
/*  805:     */     {
/*  806: 866 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  807: 867 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  808:     */     }
/*  809:     */   }
/*  810:     */   
/*  811:     */   public String eliminarArchivo()
/*  812:     */   {
/*  813: 872 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getDespachoCliente().getPdf());
/*  814: 873 */     getDespachoCliente().setPdf(null);
/*  815: 874 */     HashMap<String, Object> campos = new HashMap();
/*  816: 875 */     campos.put("pdf", null);
/*  817: 876 */     this.servicioDespachoCliente.actualizarAtributoEntidad(getDespachoCliente(), campos);
/*  818: 877 */     return null;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public String getDirectorioDescarga()
/*  822:     */   {
/*  823: 882 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "despacho_cliente");
/*  824:     */   }
/*  825:     */   
/*  826:     */   public void asignarNuevoDespachoCliente() {}
/*  827:     */   
/*  828:     */   public void reiniciarReasignar()
/*  829:     */   {
/*  830: 892 */     this.empresaReasignar = null;
/*  831: 893 */     this.fechaReasignar = null;
/*  832: 894 */     this.listaDespachoClienteReasignar = null;
/*  833: 895 */     this.listaDetalleReasignarProductoDestino = null;
/*  834: 896 */     this.listaDetalleReasignarProductoOrigen = null;
/*  835: 897 */     setDespachoOrigen(new DespachoCliente());
/*  836: 898 */     setDespachoDestino(new DespachoCliente());
/*  837:     */   }
/*  838:     */   
/*  839:     */   public String limpiarOrigen()
/*  840:     */   {
/*  841: 903 */     this.cargaOrigen = false;
/*  842: 904 */     this.listaDetalleReasignarProductoOrigen = null;
/*  843: 905 */     setDespachoOrigen(new DespachoCliente());
/*  844: 906 */     return null;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public String limpiarDestino()
/*  848:     */   {
/*  849: 910 */     this.cargaDestino = false;
/*  850: 911 */     this.listaDetalleReasignarProductoDestino = null;
/*  851: 912 */     setDespachoDestino(new DespachoCliente());
/*  852: 913 */     return null;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public void cargarDespachosClienteReasignar()
/*  856:     */   {
/*  857: 918 */     setListaDespachoClienteReasignar(this.servicioDespachoCliente.obtenerDespachosPorFecha(getFechaReasignar(), 
/*  858: 919 */       AppUtil.getOrganizacion().getIdOrganizacion(), getEmpresaReasignar()));
/*  859:     */   }
/*  860:     */   
/*  861:     */   public void recuperarDetallesDespachoClienteLista(DespachoCliente despachoCliente)
/*  862:     */   {
/*  863: 926 */     List<DetalleDespachoCliente> listaDetalleDespachoClienteOrigen = getListaDetalleReasignarProductoOrigen();
/*  864: 927 */     List<DetalleDespachoCliente> listaDetalleDespachoClienteDestino = getListaDetalleReasignarProductoDestino();
/*  865: 929 */     if (listaDetalleDespachoClienteOrigen.size() == 0)
/*  866:     */     {
/*  867: 930 */       setListaDetalleReasignarProductoOrigen(this.servicioDespachoCliente.cargarDetalleDespachoClientePorDespacho(despachoCliente));
/*  868: 931 */       setDespachoOrigen(despachoCliente);
/*  869:     */     }
/*  870: 932 */     else if (listaDetalleDespachoClienteDestino.size() == 0)
/*  871:     */     {
/*  872: 933 */       setListaDetalleReasignarProductoDestino(this.servicioDespachoCliente.cargarDetalleDespachoClientePorDespacho(despachoCliente));
/*  873: 934 */       setDespachoDestino(despachoCliente);
/*  874:     */     }
/*  875:     */   }
/*  876:     */   
/*  877:     */   public void cargarOrigen(DespachoCliente despachoCliente)
/*  878:     */   {
/*  879: 940 */     if (getDespachoDestino().getId() != despachoCliente.getId())
/*  880:     */     {
/*  881: 941 */       this.cargaOrigen = true;
/*  882: 942 */       this.listaDetalleReasignarProductoOrigen = this.servicioDespachoCliente.cargarDetalleDespachoClientePorDespacho(despachoCliente);
/*  883: 943 */       setDespachoOrigen(despachoCliente);
/*  884:     */     }
/*  885:     */     else
/*  886:     */     {
/*  887: 945 */       addInfoMessage(getLanguageController().getMensaje("msg_mensaje_error_despacho_ya_cargado"));
/*  888:     */     }
/*  889:     */   }
/*  890:     */   
/*  891:     */   public void cargarDestino(DespachoCliente despachoCliente)
/*  892:     */   {
/*  893: 951 */     if (getDespachoOrigen().getId() != despachoCliente.getId())
/*  894:     */     {
/*  895: 952 */       this.cargaDestino = true;
/*  896: 953 */       this.listaDetalleReasignarProductoDestino = this.servicioDespachoCliente.cargarDetalleDespachoClientePorDespacho(despachoCliente);
/*  897: 954 */       setDespachoDestino(despachoCliente);
/*  898:     */     }
/*  899:     */     else
/*  900:     */     {
/*  901: 956 */       addInfoMessage(getLanguageController().getMensaje("msg_mensaje_error_despacho_ya_cargado"));
/*  902:     */     }
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void consultaDetallesDespachoClienteLista(DespachoCliente despachoCliente)
/*  906:     */   {
/*  907: 961 */     this.listaDetalleProductoConsulta = this.servicioDespachoCliente.cargarDetalleDespachoClientePorDespacho(despachoCliente);
/*  908:     */   }
/*  909:     */   
/*  910:     */   public void eliminarDetalleReasignarProductoOrigen()
/*  911:     */   {
/*  912: 965 */     if (this.detalleReasignarProductoOrigen.size() > 0)
/*  913:     */     {
/*  914: 966 */       for (DetalleDespachoCliente ddc : this.detalleReasignarProductoOrigen)
/*  915:     */       {
/*  916: 967 */         this.listaDetalleReasignarProductoDestino.add(ddc);
/*  917: 968 */         this.listaDetalleReasignarProductoOrigen.remove(ddc);
/*  918:     */       }
/*  919: 970 */       this.servicioDespachoCliente.actualizarDespachoCliente(this.detalleReasignarProductoOrigen, getDespachoDestino());
/*  920:     */       
/*  921: 972 */       this.dtDetalleReasignarProductoOrigen.reset();
/*  922:     */     }
/*  923:     */     else
/*  924:     */     {
/*  925: 974 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_producto"));
/*  926:     */     }
/*  927:     */   }
/*  928:     */   
/*  929:     */   public void asignaDetalleDespachoDestino()
/*  930:     */   {
/*  931: 980 */     if (this.detalleReasignarProductoOrigen.size() > 0)
/*  932:     */     {
/*  933: 981 */       for (DetalleDespachoCliente ddc : this.detalleReasignarProductoDestino)
/*  934:     */       {
/*  935: 982 */         this.listaDetalleReasignarProductoDestino.remove(ddc);
/*  936: 983 */         this.listaDetalleReasignarProductoOrigen.add(ddc);
/*  937:     */       }
/*  938: 985 */       this.servicioDespachoCliente.actualizarDespachoCliente(this.detalleReasignarProductoDestino, getDespachoOrigen());
/*  939: 986 */       this.dtDetalleReasignarProductoDestino.reset();
/*  940:     */     }
/*  941:     */     else
/*  942:     */     {
/*  943: 988 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_producto"));
/*  944:     */     }
/*  945:     */   }
/*  946:     */   
/*  947:     */   public void resetear()
/*  948:     */   {
/*  949: 994 */     if (getDtDetalleReasignarProductoDestino() != null) {
/*  950: 995 */       getDtDetalleReasignarProductoDestino().reset();
/*  951:     */     }
/*  952: 997 */     if (getDtDetalleReasignarProductoOrigen() != null) {
/*  953: 998 */       getDtDetalleReasignarProductoOrigen().reset();
/*  954:     */     }
/*  955:     */   }
/*  956:     */   
/*  957:     */   public List<DetalleDespachoCliente> getListaDetalleReasignarProductoOrigenFiltrado()
/*  958:     */   {
/*  959:1005 */     return this.listaDetalleReasignarProductoOrigenFiltrado;
/*  960:     */   }
/*  961:     */   
/*  962:     */   public List<DetalleDespachoCliente> getListaDetalleReasignarProductoDestinoFiltrado()
/*  963:     */   {
/*  964:1009 */     return this.listaDetalleReasignarProductoDestinoFiltrado;
/*  965:     */   }
/*  966:     */   
/*  967:     */   public void cargarListaFacturaDespacho()
/*  968:     */   {
/*  969:1013 */     this.listaFacturaDespacho = new ArrayList();
/*  970:1014 */     DespachoCliente despachoCliente = (DespachoCliente)getDtDespachoCliente().getRowData();
/*  971:1015 */     HashMap<String, String> filtros = new HashMap();
/*  972:1016 */     filtros.put("idOrganizacion", Integer.toString(despachoCliente.getIdOrganizacion()));
/*  973:1017 */     filtros.put("despachoCliente.idDespachoCliente", Integer.toString(despachoCliente.getIdDespachoCliente()));
/*  974:1018 */     this.listaFacturaDespacho = this.servicioFacturaCliente.obtenerListaCombo("numero", true, filtros);
/*  975:     */   }
/*  976:     */   
/*  977:     */   public void facturaDespachoImprimir()
/*  978:     */   {
/*  979:1022 */     FacturaCliente facturaCliente = (FacturaCliente)getDtFacturaDespacho().getRowData();
/*  980:1023 */     setFacturaDespacho(facturaCliente);
/*  981:     */   }
/*  982:     */   
/*  983:     */   public DespachoCliente getDespachoCliente()
/*  984:     */   {
/*  985:1034 */     return this.despachoCliente;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public void setDespachoCliente(DespachoCliente despachoCliente)
/*  989:     */   {
/*  990:1044 */     this.despachoCliente = despachoCliente;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public LazyDataModel<DespachoCliente> getListaDespachoCliente()
/*  994:     */   {
/*  995:1053 */     return this.listaDespachoCliente;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public void setListaDespachoCliente(LazyDataModel<DespachoCliente> listaDespachoCliente)
/*  999:     */   {
/* 1000:1063 */     this.listaDespachoCliente = listaDespachoCliente;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public List<DetalleDespachoCliente> getListaDetalleDespachoCliente()
/* 1004:     */   {
/* 1005:1072 */     List<DetalleDespachoCliente> detalle = new ArrayList();
/* 1006:1073 */     for (DetalleDespachoCliente detalleDespachoCliente : getDespachoCliente().getListaDetalleDespachoCliente()) {
/* 1007:1075 */       if (!detalleDespachoCliente.isEliminado()) {
/* 1008:1076 */         detalle.add(detalleDespachoCliente);
/* 1009:     */       }
/* 1010:     */     }
/* 1011:1080 */     return detalle;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public boolean isModoEdicion()
/* 1015:     */   {
/* 1016:1084 */     return getDespachoCliente().getId() > 0;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public DataTable getDtDespachoCliente()
/* 1020:     */   {
/* 1021:1093 */     return this.dtDespachoCliente;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void setDtDespachoCliente(DataTable dtDespachoCliente)
/* 1025:     */   {
/* 1026:1103 */     this.dtDespachoCliente = dtDespachoCliente;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public DataTable getDtDetalleDespachoCliente()
/* 1030:     */   {
/* 1031:1112 */     return this.dtDetalleDespachoCliente;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void setDtDetalleDespachoCliente(DataTable dtDetalleDespachoCliente)
/* 1035:     */   {
/* 1036:1122 */     this.dtDetalleDespachoCliente = dtDetalleDespachoCliente;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public List<Documento> getListaDocumentoDespacho()
/* 1040:     */   {
/* 1041:     */     try
/* 1042:     */     {
/* 1043:1127 */       if (this.listaDocumentoDespacho == null) {
/* 1044:1128 */         this.listaDocumentoDespacho = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.DESPACHO_BODEGA, 
/* 1045:1129 */           AppUtil.getOrganizacion().getIdOrganizacion());
/* 1046:     */       }
/* 1047:     */     }
/* 1048:     */     catch (ExcepcionAS2 e)
/* 1049:     */     {
/* 1050:1132 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1051:     */     }
/* 1052:1135 */     return this.listaDocumentoDespacho;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public void setListaDocumentoDespacho(List<Documento> listaDocumentoDespacho)
/* 1056:     */   {
/* 1057:1139 */     this.listaDocumentoDespacho = listaDocumentoDespacho;
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public List<Empresa> getListaEmpresaCliente()
/* 1061:     */   {
/* 1062:1143 */     if (this.listaEmpresaCliente == null) {
/* 1063:1144 */       this.listaEmpresaCliente = this.servicioEmpresa.obtenerClientes();
/* 1064:     */     }
/* 1065:1146 */     return this.listaEmpresaCliente;
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   public void setListaEmpresaCliente(List<Empresa> listaEmpresaCliente)
/* 1069:     */   {
/* 1070:1150 */     this.listaEmpresaCliente = listaEmpresaCliente;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1074:     */   {
/* 1075:1154 */     if (this.listaDireccionEmpresa == null) {
/* 1076:1155 */       this.listaDireccionEmpresa = new ArrayList();
/* 1077:     */     }
/* 1078:1157 */     return this.listaDireccionEmpresa;
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1082:     */   {
/* 1083:1161 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public List<Bodega> getListaBodega()
/* 1087:     */   {
/* 1088:1165 */     if (this.listaBodega == null) {
/* 1089:1166 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 1090:     */     }
/* 1091:1168 */     return this.listaBodega;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void setListaBodega(List<Bodega> listaBodega)
/* 1095:     */   {
/* 1096:1172 */     this.listaBodega = listaBodega;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public List<Empresa> autocompletarClientes(String consulta)
/* 1100:     */   {
/* 1101:1176 */     return this.servicioEmpresa.autocompletarClientes(consulta, true, null, true);
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public List<PedidoCliente> getListaPedidoCliente()
/* 1105:     */   {
/* 1106:1183 */     return this.listaPedidoCliente;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public void setListaPedidoCliente(List<PedidoCliente> listaPedidoCliente)
/* 1110:     */   {
/* 1111:1191 */     this.listaPedidoCliente = listaPedidoCliente;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public Integer getIdDespachoCliente()
/* 1115:     */   {
/* 1116:1198 */     return this.idDespachoCliente;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public void setIdDespachoCliente(Integer idDespachoCliente)
/* 1120:     */   {
/* 1121:1206 */     this.idDespachoCliente = idDespachoCliente;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   public Integer getIdFacturaCliente()
/* 1125:     */   {
/* 1126:1213 */     return this.idFacturaCliente;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 1130:     */   {
/* 1131:1221 */     this.idFacturaCliente = idFacturaCliente;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public DataTable getDtInventarioProductoLote()
/* 1135:     */   {
/* 1136:1230 */     return this.dtInventarioProductoLote;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/* 1140:     */   {
/* 1141:1240 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public InventarioProducto getInventarioProducto()
/* 1145:     */   {
/* 1146:1249 */     if (this.inventarioProducto == null)
/* 1147:     */     {
/* 1148:1250 */       this.inventarioProducto = new InventarioProducto();
/* 1149:1251 */       this.inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/* 1150:     */     }
/* 1151:1253 */     return this.inventarioProducto;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 1155:     */   {
/* 1156:1263 */     this.inventarioProducto = inventarioProducto;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public List<Subempresa> getListaSubempresa()
/* 1160:     */   {
/* 1161:1272 */     if (this.listaSubempresa == null) {
/* 1162:1273 */       this.listaSubempresa = new ArrayList();
/* 1163:     */     }
/* 1164:1275 */     return this.listaSubempresa;
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 1168:     */   {
/* 1169:1285 */     this.listaSubempresa = listaSubempresa;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public String getCodigoCabecera()
/* 1173:     */   {
/* 1174:1294 */     return this.codigoCabecera;
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public void setCodigoCabecera(String codigoCabecera)
/* 1178:     */   {
/* 1179:1304 */     this.codigoCabecera = codigoCabecera;
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public Lote getLoteCrear()
/* 1183:     */   {
/* 1184:1313 */     if (this.loteCrear == null) {
/* 1185:1314 */       this.loteCrear = new Lote();
/* 1186:     */     }
/* 1187:1316 */     return this.loteCrear;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public void setLoteCrear(Lote loteCrear)
/* 1191:     */   {
/* 1192:1326 */     this.loteCrear = loteCrear;
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   public DetalleDespachoCliente getDetalleDespachoClienteSeleccionado()
/* 1196:     */   {
/* 1197:1335 */     return this.detalleDespachoClienteSeleccionado;
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public void setDetalleDespachoClienteSeleccionado(DetalleDespachoCliente detalleDespachoClienteSeleccionado)
/* 1201:     */   {
/* 1202:1345 */     this.detalleDespachoClienteSeleccionado = detalleDespachoClienteSeleccionado;
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   public TipoOrganizacion getTipoOrganizacion()
/* 1206:     */   {
/* 1207:1349 */     if (this.tipoOrganizacion == null) {
/* 1208:1350 */       this.tipoOrganizacion = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/* 1209:     */     }
/* 1210:1352 */     return this.tipoOrganizacion;
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion)
/* 1214:     */   {
/* 1215:1356 */     this.tipoOrganizacion = tipoOrganizacion;
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public DimensionContable getProyecto()
/* 1219:     */   {
/* 1220:1360 */     return this.proyecto;
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   public void cargarSaldosProyecto()
/* 1224:     */   {
/* 1225:1365 */     this.proyecto = this.despachoCliente.getProyecto();
/* 1226:1366 */     if (this.proyecto != null)
/* 1227:     */     {
/* 1228:1367 */       List<Object[]> listaSaldos = this.servicioInventarioProducto.obtenerSaldosPorBodegaProyecto(AppUtil.getBodega(), this.proyecto, this.despachoCliente
/* 1229:1368 */         .getFecha());
/* 1230:1369 */       for (DetalleDespachoCliente detalle : this.despachoCliente.getListaDetalleDespachoCliente()) {
/* 1231:1370 */         if (detalle.getDetallePedidoCliente() == null) {
/* 1232:1371 */           detalle.setEliminado(true);
/* 1233:     */         }
/* 1234:     */       }
/* 1235:1374 */       for (Object[] objects : listaSaldos)
/* 1236:     */       {
/* 1237:1375 */         Integer idBodegaDetalle = (Integer)objects[0];
/* 1238:1376 */         BigDecimal cantidadDetalle = (BigDecimal)objects[2];
/* 1239:1377 */         Integer idProductoDetalle = (Integer)objects[3];
/* 1240:1378 */         Integer idLoteDetalle = (Integer)objects[4];
/* 1241:     */         
/* 1242:1380 */         Bodega bodegaDetalle = this.servicioBodega.buscarPorId(idBodegaDetalle);
/* 1243:1381 */         Producto productoDetalle = this.servicioProducto.cargaDetalle(idProductoDetalle.intValue());
/* 1244:1382 */         Lote loteDetalle = null;
/* 1245:1383 */         if (idLoteDetalle != null) {
/* 1246:1384 */           loteDetalle = this.servicioLote.buscarPorId(idLoteDetalle.intValue());
/* 1247:     */         }
/* 1248:1387 */         DetalleDespachoCliente detalle = obtenerDetallePorProducto(productoDetalle, loteDetalle);
/* 1249:1388 */         if (detalle == null)
/* 1250:     */         {
/* 1251:1389 */           detalle = new DetalleDespachoCliente();
/* 1252:1390 */           this.despachoCliente.getListaDetalleDespachoCliente().add(detalle);
/* 1253:     */         }
/* 1254:1392 */         detalle.setIndicadorProyecto(true);
/* 1255:1393 */         detalle.setBodega(bodegaDetalle);
/* 1256:1394 */         detalle.setCantidad(cantidadDetalle.setScale(4, RoundingMode.DOWN));
/* 1257:1395 */         detalle.setDespachoCliente(this.despachoCliente);
/* 1258:1396 */         detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1259:1397 */         detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 1260:1398 */         detalle.setIndicadorManejoPeso(productoDetalle.isIndicadorManejoPeso());
/* 1261:1399 */         detalle.setProducto(productoDetalle);
/* 1262:1400 */         if (detalle.getInventarioProducto() == null)
/* 1263:     */         {
/* 1264:1401 */           InventarioProducto inventarioProducto = new InventarioProducto();
/* 1265:1402 */           inventarioProducto.setOperacion(this.despachoCliente.getDocumento().getOperacion());
/* 1266:1403 */           inventarioProducto.setProducto(productoDetalle);
/* 1267:1404 */           detalle.setInventarioProducto(inventarioProducto);
/* 1268:     */         }
/* 1269:1406 */         detalle.setUnidadVenta(productoDetalle.getUnidadVenta());
/* 1270:1407 */         if (loteDetalle != null) {
/* 1271:1408 */           actualizarSaldo(detalle, loteDetalle);
/* 1272:     */         }
/* 1273:     */       }
/* 1274:     */     }
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public DetalleDespachoCliente obtenerDetallePorProducto(Producto producto, Lote lote)
/* 1278:     */   {
/* 1279:1415 */     for (DetalleDespachoCliente detalle : getListaDetalleDespachoCliente())
/* 1280:     */     {
/* 1281:1416 */       boolean lotesIguales = false;
/* 1282:1420 */       if ((lote != null) && (detalle.getInventarioProducto().getLote() != null) && 
/* 1283:1421 */         (lote.getId() == detalle.getInventarioProducto().getLote().getId())) {
/* 1284:1422 */         lotesIguales = true;
/* 1285:     */       }
/* 1286:1424 */       if ((detalle.getProducto() != null) && (detalle.getProducto().getId() == producto.getId()) && (lotesIguales)) {
/* 1287:1425 */         return detalle;
/* 1288:     */       }
/* 1289:     */     }
/* 1290:1428 */     return null;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public String actualizarDocumento()
/* 1294:     */   {
/* 1295:1433 */     setSecuenciaEditable(!this.despachoCliente.getDocumento().isIndicadorBloqueoSecuencia());
/* 1296:     */     
/* 1297:1435 */     return "";
/* 1298:     */   }
/* 1299:     */   
/* 1300:     */   public void enviarMail()
/* 1301:     */   {
/* 1302:     */     try
/* 1303:     */     {
/* 1304:1440 */       if (this.despachoCliente.getGuiaRemision() != null)
/* 1305:     */       {
/* 1306:1441 */         GuiaRemision guiaRem = this.servicioGuiaRemision.cargarDetalle(this.despachoCliente.getGuiaRemision().getId());
/* 1307:1442 */         if (((guiaRem.getDocumento() != null) && (!guiaRem.getDocumento().isIndicadorDocumentoElectronico())) || 
/* 1308:1443 */           (guiaRem.getEstado().equals(Estado.ANULADO)) || (guiaRem.getEstado().equals(Estado.EN_ESPERA)) || 
/* 1309:1444 */           (guiaRem.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 1310:1445 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1311:     */         } else {
/* 1312:1447 */           this.servicioGuiaRemision.enviarMail(guiaRem, this.mails);
/* 1313:     */         }
/* 1314:     */       }
/* 1315:     */       else
/* 1316:     */       {
/* 1317:1450 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1318:     */       }
/* 1319:     */     }
/* 1320:     */     catch (ExcepcionAS2 e)
/* 1321:     */     {
/* 1322:1453 */       addErrorMessage(this.despachoCliente.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 1323:     */     }
/* 1324:     */     catch (Exception e)
/* 1325:     */     {
/* 1326:1455 */       addErrorMessage(this.despachoCliente.getNumero() + " -> " + this.mails + e.getMessage());
/* 1327:     */     }
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public List<DespachoCliente> getListaDespachoClienteReasignar()
/* 1331:     */   {
/* 1332:1461 */     return this.listaDespachoClienteReasignar == null ? (this.listaDespachoClienteReasignar = new ArrayList()) : this.listaDespachoClienteReasignar;
/* 1333:     */   }
/* 1334:     */   
/* 1335:     */   public void setListaDespachoClienteReasignar(List<DespachoCliente> listaDespachoClienteReasignar)
/* 1336:     */   {
/* 1337:1466 */     this.listaDespachoClienteReasignar = listaDespachoClienteReasignar;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public DataTable getDtDespachoClienteReasignar()
/* 1341:     */   {
/* 1342:1470 */     return this.dtDespachoClienteReasignar;
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   public void setDtDespachoClienteReasignar(DataTable dtDespachoClienteReasignar)
/* 1346:     */   {
/* 1347:1474 */     this.dtDespachoClienteReasignar = dtDespachoClienteReasignar;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public DespachoCliente getDespachoClienteReasignar()
/* 1351:     */   {
/* 1352:1478 */     return this.despachoClienteReasignar;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public void setDespachoClienteReasignar(DespachoCliente despachoClienteReasignar)
/* 1356:     */   {
/* 1357:1482 */     this.despachoClienteReasignar = despachoClienteReasignar;
/* 1358:     */   }
/* 1359:     */   
/* 1360:     */   public Empresa getEmpresaReasignar()
/* 1361:     */   {
/* 1362:1486 */     return this.empresaReasignar;
/* 1363:     */   }
/* 1364:     */   
/* 1365:     */   public void setEmpresaReasignar(Empresa empresaReasignar)
/* 1366:     */   {
/* 1367:1490 */     this.empresaReasignar = empresaReasignar;
/* 1368:     */   }
/* 1369:     */   
/* 1370:     */   public Date getFechaReasignar()
/* 1371:     */   {
/* 1372:1494 */     return this.fechaReasignar;
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public void setFechaReasignar(Date fechaReasignar)
/* 1376:     */   {
/* 1377:1498 */     this.fechaReasignar = fechaReasignar;
/* 1378:     */   }
/* 1379:     */   
/* 1380:     */   public DespachoCliente getDespachoOrigen()
/* 1381:     */   {
/* 1382:1502 */     return this.despachoOrigen == null ? (this.despachoOrigen = new DespachoCliente()) : this.despachoOrigen;
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public void setDespachoOrigen(DespachoCliente despachoOrigen)
/* 1386:     */   {
/* 1387:1506 */     this.despachoOrigen = despachoOrigen;
/* 1388:     */   }
/* 1389:     */   
/* 1390:     */   public DespachoCliente getDespachoDestino()
/* 1391:     */   {
/* 1392:1510 */     return this.despachoDestino == null ? (this.despachoDestino = new DespachoCliente()) : this.despachoDestino;
/* 1393:     */   }
/* 1394:     */   
/* 1395:     */   public void setDespachoDestino(DespachoCliente despachoDestino)
/* 1396:     */   {
/* 1397:1514 */     this.despachoDestino = despachoDestino;
/* 1398:     */   }
/* 1399:     */   
/* 1400:     */   public List<DetalleDespachoCliente> getListaDetalleReasignarProductoOrigen()
/* 1401:     */   {
/* 1402:1518 */     return this.listaDetalleReasignarProductoOrigen == null ? (this.listaDetalleReasignarProductoOrigen = new ArrayList()) : this.listaDetalleReasignarProductoOrigen;
/* 1403:     */   }
/* 1404:     */   
/* 1405:     */   public void setListaDetalleReasignarProductoOrigen(List<DetalleDespachoCliente> listaDetalleReasignarProductoOrigen)
/* 1406:     */   {
/* 1407:1523 */     this.listaDetalleReasignarProductoOrigen = listaDetalleReasignarProductoOrigen;
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   public DataTable getDtDetalleReasignarProductoOrigen()
/* 1411:     */   {
/* 1412:1527 */     return this.dtDetalleReasignarProductoOrigen;
/* 1413:     */   }
/* 1414:     */   
/* 1415:     */   public void setDtDetalleReasignarProductoOrigen(DataTable dtDetalleReasignarProductoOrigen)
/* 1416:     */   {
/* 1417:1531 */     this.dtDetalleReasignarProductoOrigen = dtDetalleReasignarProductoOrigen;
/* 1418:     */   }
/* 1419:     */   
/* 1420:     */   public List<DetalleDespachoCliente> getDetalleReasignarProductoOrigen()
/* 1421:     */   {
/* 1422:1535 */     return this.detalleReasignarProductoOrigen;
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   public void setDetalleReasignarProductoOrigen(List<DetalleDespachoCliente> detalleReasignarProductoOrigen)
/* 1426:     */   {
/* 1427:1539 */     this.detalleReasignarProductoOrigen = detalleReasignarProductoOrigen;
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   public List<DetalleDespachoCliente> getDetalleReasignarProductoDestino()
/* 1431:     */   {
/* 1432:1543 */     return this.detalleReasignarProductoDestino;
/* 1433:     */   }
/* 1434:     */   
/* 1435:     */   public void setDetalleReasignarProductoDestino(List<DetalleDespachoCliente> detalleReasignarProductoDestino)
/* 1436:     */   {
/* 1437:1547 */     this.detalleReasignarProductoDestino = detalleReasignarProductoDestino;
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   public List<DetalleDespachoCliente> getListaDetalleReasignarProductoDestino()
/* 1441:     */   {
/* 1442:1551 */     return this.listaDetalleReasignarProductoDestino == null ? (this.listaDetalleReasignarProductoDestino = new ArrayList()) : this.listaDetalleReasignarProductoDestino;
/* 1443:     */   }
/* 1444:     */   
/* 1445:     */   public void setListaDetalleReasignarProductoDestino(List<DetalleDespachoCliente> listaDetalleReasignarProductoDestino)
/* 1446:     */   {
/* 1447:1556 */     this.listaDetalleReasignarProductoDestino = listaDetalleReasignarProductoDestino;
/* 1448:     */   }
/* 1449:     */   
/* 1450:     */   public DataTable getDtDetalleReasignarProductoDestino()
/* 1451:     */   {
/* 1452:1560 */     return this.dtDetalleReasignarProductoDestino;
/* 1453:     */   }
/* 1454:     */   
/* 1455:     */   public void setDtDetalleReasignarProductoDestino(DataTable dtDetalleReasignarProductoDestino)
/* 1456:     */   {
/* 1457:1564 */     this.dtDetalleReasignarProductoDestino = dtDetalleReasignarProductoDestino;
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   public List<DetalleDespachoCliente> getListaDetalleReasignarProductoOrigenFiltros()
/* 1461:     */   {
/* 1462:1568 */     return this.listaDetalleReasignarProductoOrigenFiltros;
/* 1463:     */   }
/* 1464:     */   
/* 1465:     */   public void setListaDetalleReasignarProductoOrigenFiltros(List<DetalleDespachoCliente> listaDetalleReasignarProductoOrigenFiltros)
/* 1466:     */   {
/* 1467:1572 */     this.listaDetalleReasignarProductoOrigenFiltros = listaDetalleReasignarProductoOrigenFiltros;
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   public List<DetalleDespachoCliente> getListaDetalleProductoConsulta()
/* 1471:     */   {
/* 1472:1576 */     return this.listaDetalleProductoConsulta;
/* 1473:     */   }
/* 1474:     */   
/* 1475:     */   public void setListaDetalleProductoConsulta(List<DetalleDespachoCliente> listaDetalleProductoConsulta)
/* 1476:     */   {
/* 1477:1580 */     this.listaDetalleProductoConsulta = listaDetalleProductoConsulta;
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public boolean isCargaOrigen()
/* 1481:     */   {
/* 1482:1584 */     return this.cargaOrigen;
/* 1483:     */   }
/* 1484:     */   
/* 1485:     */   public void setCargaOrigen(boolean cargaOrigen)
/* 1486:     */   {
/* 1487:1588 */     this.cargaOrigen = cargaOrigen;
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public boolean isCargaDestino()
/* 1491:     */   {
/* 1492:1592 */     return this.cargaDestino;
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   public void setCargaDestino(boolean cargaDestino)
/* 1496:     */   {
/* 1497:1596 */     this.cargaDestino = cargaDestino;
/* 1498:     */   }
/* 1499:     */   
/* 1500:     */   public String getMails()
/* 1501:     */   {
/* 1502:1600 */     return this.mails;
/* 1503:     */   }
/* 1504:     */   
/* 1505:     */   public void setMails(String mails)
/* 1506:     */   {
/* 1507:1604 */     this.mails = mails;
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public StreamedContent getXMLSRI()
/* 1511:     */   {
/* 1512:1608 */     if ((this.despachoCliente != null) && (this.despachoCliente.getId() != 0) && (this.despachoCliente.getGuiaRemision() != null))
/* 1513:     */     {
/* 1514:1609 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 1515:     */       
/* 1516:     */ 
/* 1517:1612 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.GUIA_REMISION.toString();
/* 1518:1613 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 1519:1614 */       String nombreArchivo = this.despachoCliente.getGuiaRemision().getNumero() + "-" + this.despachoCliente.getGuiaRemision().getClaveAcceso() + ".xml";
/* 1520:1615 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 1521:     */       try
/* 1522:     */       {
/* 1523:1617 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 1524:     */       }
/* 1525:     */       catch (FileNotFoundException e)
/* 1526:     */       {
/* 1527:1619 */         e.printStackTrace();
/* 1528:1620 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1529:1621 */         return null;
/* 1530:     */       }
/* 1531:     */     }
/* 1532:1624 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1533:1625 */     return null;
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public List<FacturaCliente> getListaFacturaDespacho()
/* 1537:     */   {
/* 1538:1630 */     return this.listaFacturaDespacho;
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public void setListaFacturaDespacho(List<FacturaCliente> listaFacturaDespacho)
/* 1542:     */   {
/* 1543:1634 */     this.listaFacturaDespacho = listaFacturaDespacho;
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public FacturaCliente getFacturaDespacho()
/* 1547:     */   {
/* 1548:1638 */     return this.facturaDespacho;
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   public void setFacturaDespacho(FacturaCliente facturaDespacho)
/* 1552:     */   {
/* 1553:1642 */     this.facturaDespacho = facturaDespacho;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public DataTable getDtFacturaDespacho()
/* 1557:     */   {
/* 1558:1646 */     return this.dtFacturaDespacho;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public void setDtFacturaDespacho(DataTable dtFacturaDespacho)
/* 1562:     */   {
/* 1563:1650 */     this.dtFacturaDespacho = dtFacturaDespacho;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public AS2Exception getExContabilizacion()
/* 1567:     */   {
/* 1568:1657 */     return this.exContabilizacion;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1572:     */   {
/* 1573:1665 */     this.exContabilizacion = exContabilizacion;
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 1577:     */   {
/* 1578:1669 */     if (this.organizacionConfiguracion == null) {
/* 1579:1670 */       this.organizacionConfiguracion = this.servicioOrganizacion.cargarDetalle(AppUtil.getOrganizacion().getId()).getOrganizacionConfiguracion();
/* 1580:     */     }
/* 1581:1672 */     return this.organizacionConfiguracion;
/* 1582:     */   }
/* 1583:     */   
/* 1584:     */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 1585:     */   {
/* 1586:1676 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 1587:     */   }
/* 1588:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.DespachoClienteBean
 * JD-Core Version:    0.7.0.1
 */