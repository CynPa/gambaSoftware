/*    1:     */ package com.asinfo.as2.inventario.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    5:     */ import com.asinfo.as2.controller.LanguageController;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    8:     */ import com.asinfo.as2.entities.Bodega;
/*    9:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   10:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   11:     */ import com.asinfo.as2.entities.Dispositivo;
/*   12:     */ import com.asinfo.as2.entities.Documento;
/*   13:     */ import com.asinfo.as2.entities.Empresa;
/*   14:     */ import com.asinfo.as2.entities.GuiaRemision;
/*   15:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   16:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   17:     */ import com.asinfo.as2.entities.Lote;
/*   18:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   19:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   20:     */ import com.asinfo.as2.entities.Organizacion;
/*   21:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   22:     */ import com.asinfo.as2.entities.Producto;
/*   23:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   24:     */ import com.asinfo.as2.entities.Sucursal;
/*   25:     */ import com.asinfo.as2.entities.UnidadManejo;
/*   26:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   27:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   28:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   29:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   30:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   31:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   32:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   33:     */ import com.asinfo.as2.inventario.configuracion.controller.ProductoBean;
/*   34:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   35:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   36:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   37:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   38:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   39:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   40:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   41:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   42:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   43:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   44:     */ import com.asinfo.as2.util.AppUtil;
/*   45:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   46:     */ import com.asinfo.as2.utils.JsfUtil;
/*   47:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   48:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioGuiaRemision;
/*   49:     */ import java.io.BufferedInputStream;
/*   50:     */ import java.io.File;
/*   51:     */ import java.io.FileNotFoundException;
/*   52:     */ import java.io.InputStream;
/*   53:     */ import java.math.BigDecimal;
/*   54:     */ import java.math.RoundingMode;
/*   55:     */ import java.util.ArrayList;
/*   56:     */ import java.util.Collection;
/*   57:     */ import java.util.Date;
/*   58:     */ import java.util.HashMap;
/*   59:     */ import java.util.List;
/*   60:     */ import java.util.Map;
/*   61:     */ import javax.annotation.PostConstruct;
/*   62:     */ import javax.ejb.EJB;
/*   63:     */ import javax.faces.bean.ManagedBean;
/*   64:     */ import javax.faces.bean.ManagedProperty;
/*   65:     */ import javax.faces.bean.ViewScoped;
/*   66:     */ import javax.faces.context.ExternalContext;
/*   67:     */ import javax.faces.context.FacesContext;
/*   68:     */ import javax.faces.context.PartialViewContext;
/*   69:     */ import javax.servlet.http.HttpSession;
/*   70:     */ import org.apache.log4j.Logger;
/*   71:     */ import org.primefaces.component.datatable.DataTable;
/*   72:     */ import org.primefaces.context.RequestContext;
/*   73:     */ import org.primefaces.event.FileUploadEvent;
/*   74:     */ import org.primefaces.event.SelectEvent;
/*   75:     */ import org.primefaces.model.LazyDataModel;
/*   76:     */ import org.primefaces.model.SortOrder;
/*   77:     */ import org.primefaces.model.StreamedContent;
/*   78:     */ import org.primefaces.model.UploadedFile;
/*   79:     */ 
/*   80:     */ @ManagedBean
/*   81:     */ @ViewScoped
/*   82:     */ public class TransferenciaBodegaBean
/*   83:     */   extends MovimientoInventarioBaseBean
/*   84:     */ {
/*   85:     */   private static final long serialVersionUID = -8487161393637972593L;
/*   86:     */   @EJB
/*   87:     */   protected ServicioMovimientoInventario servicioMovimientoInventario;
/*   88:     */   @EJB
/*   89:     */   private ServicioProducto servicioProducto;
/*   90:     */   @EJB
/*   91:     */   private ServicioUnidadConversion servicioUnidadConversion;
/*   92:     */   @EJB
/*   93:     */   protected ServicioDocumento servicioDocumento;
/*   94:     */   @EJB
/*   95:     */   private ServicioLote servicioLote;
/*   96:     */   @EJB
/*   97:     */   private ServicioBodega servicioBodega;
/*   98:     */   @EJB
/*   99:     */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  100:     */   @EJB
/*  101:     */   private ServicioGuiaRemision servicioGuiaRemision;
/*  102:     */   @EJB
/*  103:     */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  104:     */   @EJB
/*  105:     */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  106:     */   @EJB
/*  107:     */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  108:     */   @EJB
/*  109:     */   private ServicioEmpresa servicioEmpresa;
/*  110:     */   protected Integer idTransferenciaBodega;
/*  111:     */   private MovimientoInventario transferencia;
/*  112:     */   private LazyDataModel<MovimientoInventario> listaTransferencia;
/*  113:     */   private List<Documento> listaDocumentosTransferencia;
/*  114:     */   private List<Bodega> listaBodegaOrigen;
/*  115:     */   private List<Bodega> listaBodegaDestino;
/*  116:     */   private InventarioProducto inventarioProducto;
/*  117:     */   private DetalleMovimientoInventario detalleTransferenciaSeleccionada;
/*  118:     */   private DataTable dtMovimientoInventario;
/*  119:     */   private DataTable dtDetalleMovimientoInventario;
/*  120:     */   private DataTable dtInventarioProductoLote;
/*  121:     */   private DataTable dtLecturaBalanza;
/*  122:     */   @ManagedProperty("#{productoBean}")
/*  123:     */   private ProductoBean productoBean;
/*  124:     */   private String codigoCabecera;
/*  125:     */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  126:     */   private boolean indicadorMostrarOrdenSalidaMaterial;
/*  127:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  128:     */   private Lote loteCrear;
/*  129:     */   private DetalleMovimientoInventario detalleMovimientoInventarioSeleccionado;
/*  130:     */   private List<Dispositivo> listaDispositivo;
/*  131:     */   private LecturaBalanza lecturaBalanza;
/*  132:     */   private List<UnidadManejo> listaUnidadManejo;
/*  133:     */   private List<UnidadManejo> listaPallet;
/*  134:     */   private Boolean mostrarBalanza;
/*  135:     */   protected String mails;
/*  136: 163 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  137:     */   
/*  138:     */   @PostConstruct
/*  139:     */   public void init()
/*  140:     */   {
/*  141: 167 */     crearMovimiento();
/*  142: 168 */     getListaProductoBean().setActivo(true);
/*  143: 169 */     this.listaTransferencia = new LazyDataModel()
/*  144:     */     {
/*  145:     */       private static final long serialVersionUID = 1L;
/*  146:     */       
/*  147:     */       public List<MovimientoInventario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  148:     */       {
/*  149: 176 */         List<MovimientoInventario> lista = new ArrayList();
/*  150:     */         
/*  151: 178 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  152: 180 */         if (filters == null) {
/*  153: 181 */           filters = new HashMap();
/*  154:     */         }
/*  155: 183 */         TransferenciaBodegaBean.this.obtenerFiltros(filters);
/*  156: 184 */         filters = TransferenciaBodegaBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  157:     */         
/*  158: 186 */         lista = TransferenciaBodegaBean.this.servicioMovimientoInventario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  159:     */         
/*  160: 188 */         TransferenciaBodegaBean.this.listaTransferencia.setRowCount(TransferenciaBodegaBean.this.servicioMovimientoInventario.contarPorCirterio(filters));
/*  161:     */         
/*  162: 190 */         return lista;
/*  163:     */       }
/*  164:     */     };
/*  165:     */   }
/*  166:     */   
/*  167:     */   public String emitirGuiaRemision()
/*  168:     */   {
/*  169: 198 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/*  170: 199 */     if ((this.transferencia != null) && 
/*  171: 200 */       (this.transferencia.getId() > 0)) {
/*  172: 201 */       if ((this.transferencia.getGuiaRemision() != null) && (this.transferencia.getGuiaRemision().getEstado() != null))
/*  173:     */       {
/*  174: 202 */         if (!this.transferencia.getGuiaRemision().getEstado().equals(Estado.EN_ESPERA)) {
/*  175: 204 */           if (this.transferencia.getGuiaRemision().getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA)) {}
/*  176:     */         }
/*  177:     */       }
/*  178:     */       else
/*  179:     */       {
/*  180: 206 */         HttpSession session = (HttpSession)context.getSession(true);
/*  181: 207 */         session.setAttribute("transferenciaBodega", this.transferencia);
/*  182: 208 */         return "/paginas/ventas/procesos/guiaRemision.xhtml?faces-redirect=true";
/*  183:     */       }
/*  184:     */     }
/*  185: 210 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  186: 211 */     return "";
/*  187:     */   }
/*  188:     */   
/*  189:     */   protected void obtenerFiltros(Map<String, String> filters)
/*  190:     */   {
/*  191: 223 */     if (this.idTransferenciaBodega != null)
/*  192:     */     {
/*  193: 224 */       filters.put("idMovimientoInventario", this.idTransferenciaBodega.toString());
/*  194: 225 */       this.idTransferenciaBodega = null;
/*  195:     */     }
/*  196: 228 */     filters.put("documento.documentoBase", DocumentoBase.TRANSFERENCIA_BODEGA.toString());
/*  197:     */   }
/*  198:     */   
/*  199:     */   public void actualizarProducto()
/*  200:     */   {
/*  201: 232 */     DetalleMovimientoInventario detalleMovimientoInventario = null;
/*  202:     */     try
/*  203:     */     {
/*  204: 234 */       detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/*  205: 235 */       String codigo = detalleMovimientoInventario.getProducto().getCodigo();
/*  206: 236 */       cargarProductoDesdeCodigoEnDetalle(codigo, detalleMovimientoInventario);
/*  207:     */     }
/*  208:     */     catch (ExcepcionAS2 e)
/*  209:     */     {
/*  210: 239 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  211: 240 */       detalleMovimientoInventario.getProducto().setCodigo("");
/*  212: 241 */       detalleMovimientoInventario.getProducto().setNombre("");
/*  213:     */     }
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void agregarDetalleDesdeCodigoCabecera()
/*  217:     */   {
/*  218: 247 */     DetalleMovimientoInventario detalleMovimientoInventario = null;
/*  219:     */     try
/*  220:     */     {
/*  221: 249 */       detalleMovimientoInventario = new DetalleMovimientoInventario();
/*  222: 250 */       detalleMovimientoInventario.setMovimientoInventario(getTransferencia());
/*  223: 251 */       detalleMovimientoInventario.setBodegaOrigen(getTransferencia().getBodegaOrigen());
/*  224: 252 */       detalleMovimientoInventario.setBodegaDestino(getTransferencia().getBodegaDestino());
/*  225: 253 */       detalleMovimientoInventario.setProducto(new Producto());
/*  226: 254 */       getTransferencia().getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/*  227:     */       
/*  228: 256 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, detalleMovimientoInventario);
/*  229: 257 */       this.codigoCabecera = "";
/*  230:     */     }
/*  231:     */     catch (ExcepcionAS2 e)
/*  232:     */     {
/*  233: 259 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  234: 260 */       detalleMovimientoInventario.getProducto().setCodigo("");
/*  235: 261 */       detalleMovimientoInventario.getProducto().setNombre("");
/*  236: 262 */       detalleMovimientoInventario.setEliminado(true);
/*  237: 263 */       this.codigoCabecera = "";
/*  238:     */     }
/*  239:     */   }
/*  240:     */   
/*  241:     */   private void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleMovimientoInventario detalleMovimientoInventario)
/*  242:     */     throws ExcepcionAS2
/*  243:     */   {
/*  244: 268 */     Producto producto = null;
/*  245: 269 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  246: 270 */     this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/*  247: 271 */     detalleMovimientoInventario.setProducto(producto);
/*  248: 272 */     detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/*  249: 273 */     InventarioProducto inventarioProducto = new InventarioProducto();
/*  250: 274 */     inventarioProducto.setOperacion(this.transferencia.getDocumento().getOperacion());
/*  251: 275 */     detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/*  252: 276 */     inventarioProducto.setProducto(producto);
/*  253: 277 */     BigDecimal saldo = BigDecimal.ZERO;
/*  254: 278 */     if (producto.getLote() != null)
/*  255:     */     {
/*  256: 279 */       detalleMovimientoInventario.getInventarioProducto().setLote(producto.getLote());
/*  257: 280 */       saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), producto
/*  258: 281 */         .getLote().getIdLote(), this.transferencia.getFecha());
/*  259: 282 */       detalleMovimientoInventario.setSaldo(saldo);
/*  260: 284 */       if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  261: 285 */         detalleMovimientoInventario.setCantidad(saldo);
/*  262:     */       }
/*  263:     */     }
/*  264:     */     else
/*  265:     */     {
/*  266: 288 */       saldo = this.servicioProducto.getSaldo(producto.getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), this.transferencia
/*  267: 289 */         .getFecha());
/*  268: 290 */       detalleMovimientoInventario.setSaldo(saldo);
/*  269:     */     }
/*  270:     */   }
/*  271:     */   
/*  272:     */   public void actualizarSaldo(SelectEvent event)
/*  273:     */   {
/*  274: 300 */     DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/*  275: 301 */     Lote lote = (Lote)event.getObject();
/*  276: 302 */     detalleMovimientoInventario.getInventarioProducto().setLote(lote);
/*  277:     */     
/*  278:     */ 
/*  279: 305 */     BigDecimal saldo = this.servicioProducto.getSaldoLote(detalleMovimientoInventario.getProducto().getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), detalleMovimientoInventario
/*  280: 306 */       .getInventarioProducto().getLote().getIdLote(), this.transferencia.getFecha());
/*  281: 307 */     detalleMovimientoInventario.setSaldo(saldo);
/*  282:     */   }
/*  283:     */   
/*  284:     */   public String editar()
/*  285:     */   {
/*  286: 317 */     if ((getTransferencia() != null) && (getTransferencia().getIdMovimientoInventario() > 0)) {
/*  287:     */       try
/*  288:     */       {
/*  289: 320 */         this.servicioMovimientoInventario.esEditable(getTransferencia());
/*  290:     */         
/*  291: 322 */         this.transferencia = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(getTransferencia().getId()));
/*  292: 323 */         if (!this.transferencia.getEstado().equals(Estado.REGISTRADO_DATOS))
/*  293:     */         {
/*  294: 324 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.transferencia.getEstado());
/*  295: 325 */           return "";
/*  296:     */         }
/*  297: 327 */         this.servicioMovimientoInventario.agregarInventariosProducto(this.transferencia);
/*  298: 328 */         for (DetalleMovimientoInventario detalle : this.transferencia.getDetalleMovimientosInventario()) {
/*  299:     */           try
/*  300:     */           {
/*  301: 330 */             this.servicioUnidadConversion.cargarListaUnidadConversion(detalle.getProducto());
/*  302:     */           }
/*  303:     */           catch (ExcepcionAS2 e)
/*  304:     */           {
/*  305: 332 */             addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  306:     */           }
/*  307:     */         }
/*  308: 335 */         setEditado(true);
/*  309:     */       }
/*  310:     */       catch (ExcepcionAS2Inventario e)
/*  311:     */       {
/*  312: 338 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  313:     */       }
/*  314:     */       catch (ExcepcionAS2Financiero e)
/*  315:     */       {
/*  316: 341 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  317:     */       }
/*  318:     */       catch (Exception e)
/*  319:     */       {
/*  320: 344 */         addErrorMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  321:     */       }
/*  322:     */     } else {
/*  323: 347 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  324:     */     }
/*  325: 350 */     return "";
/*  326:     */   }
/*  327:     */   
/*  328:     */   public String limpiar()
/*  329:     */   {
/*  330: 360 */     setEditado(false);
/*  331: 361 */     crearMovimiento();
/*  332: 362 */     return "";
/*  333:     */   }
/*  334:     */   
/*  335:     */   private void crearMovimiento()
/*  336:     */   {
/*  337: 366 */     this.transferencia = new MovimientoInventario();
/*  338: 367 */     this.transferencia.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  339: 368 */     this.transferencia.setSucursal(AppUtil.getSucursal());
/*  340: 369 */     this.transferencia.setEstado(Estado.ELABORADO);
/*  341: 370 */     this.transferencia.setBodegaOrigen(AppUtil.getBodega());
/*  342: 371 */     this.transferencia.setNumero("");
/*  343: 372 */     Documento documento = null;
/*  344: 373 */     if ((getListaDocumentosTransferencia() != null) && (!getListaDocumentosTransferencia().isEmpty()))
/*  345:     */     {
/*  346: 374 */       documento = (Documento)getListaDocumentosTransferencia().get(0);
/*  347: 375 */       this.transferencia.setDocumento(documento);
/*  348: 376 */       actualizarDocumento();
/*  349:     */     }
/*  350: 379 */     this.transferencia.setFecha(new Date());
/*  351:     */   }
/*  352:     */   
/*  353:     */   public String guardar()
/*  354:     */   {
/*  355:     */     try
/*  356:     */     {
/*  357: 391 */       if (getTransferencia().getTotal().compareTo(new BigDecimal(0)) == 0) {
/*  358: 392 */         throw new ExcepcionAS2Financiero("msg_info_seleccionar_producto");
/*  359:     */       }
/*  360: 395 */       this.transferencia.setEstado(Estado.ELABORADO);
/*  361: 396 */       for (DetalleMovimientoInventario detalle : getTransferencia().getDetalleMovimientosInventario()) {
/*  362: 397 */         if (detalle.getBodegaDestino() == null) {
/*  363: 398 */           detalle.setBodegaDestino(getTransferencia().getBodegaDestino());
/*  364:     */         }
/*  365:     */       }
/*  366: 400 */       this.servicioMovimientoInventario.guardar(getTransferencia());
/*  367: 401 */       this.idTransferenciaBodega = Integer.valueOf(getTransferencia().getIdMovimientoInventario());
/*  368: 402 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  369:     */       
/*  370: 404 */       limpiar();
/*  371: 405 */       return "transferenciaBodegas.xhtml?faces-redirect=true&idTransferenciaBodega=" + this.idTransferenciaBodega;
/*  372:     */     }
/*  373:     */     catch (ExcepcionAS2Inventario e)
/*  374:     */     {
/*  375: 408 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  376: 409 */       LOG.error("ERROR AL GUARDAR DATOS TRANSFERENCIA BODEGA", e);
/*  377:     */     }
/*  378:     */     catch (ExcepcionAS2 e)
/*  379:     */     {
/*  380: 411 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  381: 412 */       LOG.error("ERROR AL GUARDAR DATOS TRANSFERENCIA BODEGA", e);
/*  382:     */     }
/*  383:     */     catch (AS2Exception e)
/*  384:     */     {
/*  385: 414 */       e.printStackTrace();
/*  386: 415 */       this.exContabilizacion = e;
/*  387: 416 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  388: 417 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  389:     */     }
/*  390:     */     catch (Exception e)
/*  391:     */     {
/*  392: 419 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  393: 420 */       LOG.error("ERROR AL GUARDAR DATOS TRANSFERENCIA BODEGA", e);
/*  394:     */     }
/*  395: 422 */     return "";
/*  396:     */   }
/*  397:     */   
/*  398:     */   public String guardarBorrador()
/*  399:     */   {
/*  400:     */     try
/*  401:     */     {
/*  402: 427 */       getTransferencia().setEstado(Estado.REGISTRADO_DATOS);
/*  403: 428 */       this.servicioMovimientoInventario.guardar(getTransferencia(), false, false, true);
/*  404: 429 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar_borrador"));
/*  405:     */       
/*  406: 431 */       this.transferencia = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(getTransferencia().getId()));
/*  407: 432 */       this.servicioMovimientoInventario.agregarInventariosProducto(this.transferencia);
/*  408: 433 */       for (DetalleMovimientoInventario detalle : this.transferencia.getDetalleMovimientosInventario()) {
/*  409:     */         try
/*  410:     */         {
/*  411: 435 */           this.servicioUnidadConversion.cargarListaUnidadConversion(detalle.getProducto());
/*  412:     */         }
/*  413:     */         catch (ExcepcionAS2 e)
/*  414:     */         {
/*  415: 437 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  416:     */         }
/*  417:     */       }
/*  418: 440 */       this.detalleMovimientoInventarioSeleccionado = null;
/*  419: 441 */       if (this.dtDetalleMovimientoInventario != null) {
/*  420: 442 */         this.dtDetalleMovimientoInventario.reset();
/*  421:     */       }
/*  422: 444 */       if (this.dtLecturaBalanza != null) {
/*  423: 445 */         this.dtLecturaBalanza.reset();
/*  424:     */       }
/*  425: 447 */       if (this.lecturaBalanza != null)
/*  426:     */       {
/*  427: 448 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/*  428: 449 */         this.lecturaBalanza = null;
/*  429: 450 */         getLecturaBalanza().setDispositivo(dispositivo);
/*  430:     */       }
/*  431:     */     }
/*  432:     */     catch (ExcepcionAS2Inventario e)
/*  433:     */     {
/*  434: 452 */       e = 
/*  435:     */       
/*  436:     */ 
/*  437:     */ 
/*  438:     */ 
/*  439:     */ 
/*  440:     */ 
/*  441:     */ 
/*  442:     */ 
/*  443:     */ 
/*  444:     */ 
/*  445:     */ 
/*  446:     */ 
/*  447: 465 */         e;addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());LOG.error("ERROR AL GUARDAR DATOS TRANSFERENCIA BODEGA", e);
/*  448:     */     }
/*  449:     */     catch (ExcepcionAS2 e)
/*  450:     */     {
/*  451: 455 */       e = 
/*  452:     */       
/*  453:     */ 
/*  454:     */ 
/*  455:     */ 
/*  456:     */ 
/*  457:     */ 
/*  458:     */ 
/*  459:     */ 
/*  460:     */ 
/*  461: 465 */         e;addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());LOG.error("ERROR AL GUARDAR DATOS TRANSFERENCIA BODEGA", e);
/*  462:     */     }
/*  463:     */     catch (AS2Exception e)
/*  464:     */     {
/*  465: 458 */       e = 
/*  466:     */       
/*  467:     */ 
/*  468:     */ 
/*  469:     */ 
/*  470:     */ 
/*  471:     */ 
/*  472: 465 */         e;addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/*  473:     */     }
/*  474:     */     catch (Exception e)
/*  475:     */     {
/*  476: 460 */       e = 
/*  477:     */       
/*  478:     */ 
/*  479:     */ 
/*  480:     */ 
/*  481: 465 */         e;addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));LOG.error("ERROR AL GUARDAR DATOS TRANSFERENCIA BODEGA", e);
/*  482:     */     }
/*  483:     */     finally {}
/*  484: 466 */     return "";
/*  485:     */   }
/*  486:     */   
/*  487:     */   public String creacionLote()
/*  488:     */   {
/*  489: 470 */     this.detalleMovimientoInventarioSeleccionado = ((DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData());
/*  490: 471 */     this.loteCrear = new Lote();
/*  491: 472 */     this.loteCrear.setActivo(true);
/*  492: 473 */     this.loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  493: 474 */     this.loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  494: 475 */     this.loteCrear.setProducto(this.detalleMovimientoInventarioSeleccionado.getProducto());
/*  495: 476 */     return "";
/*  496:     */   }
/*  497:     */   
/*  498:     */   public String guardarLote()
/*  499:     */     throws AS2Exception
/*  500:     */   {
/*  501:     */     try
/*  502:     */     {
/*  503: 481 */       this.servicioLote.guardar(this.loteCrear);
/*  504: 482 */       this.detalleMovimientoInventarioSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/*  505: 483 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  506:     */     }
/*  507:     */     catch (Exception e)
/*  508:     */     {
/*  509: 485 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  510: 486 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  511:     */     }
/*  512: 488 */     return "";
/*  513:     */   }
/*  514:     */   
/*  515:     */   public String agregarDetalle()
/*  516:     */   {
/*  517: 498 */     DetalleMovimientoInventario d = new DetalleMovimientoInventario();
/*  518: 499 */     d.setMovimientoInventario(getTransferencia());
/*  519: 500 */     d.setBodegaOrigen(getTransferencia().getBodegaOrigen());
/*  520: 501 */     d.setBodegaDestino(getTransferencia().getBodegaDestino());
/*  521: 502 */     d.setProducto(new Producto());
/*  522: 503 */     getTransferencia().getDetalleMovimientosInventario().add(d);
/*  523:     */     
/*  524: 505 */     return "";
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void agregarDetalleListener()
/*  528:     */   {
/*  529: 509 */     DetalleMovimientoInventario d = new DetalleMovimientoInventario();
/*  530: 510 */     d.setMovimientoInventario(getTransferencia());
/*  531: 511 */     d.setBodegaOrigen(getTransferencia().getBodegaOrigen());
/*  532: 512 */     d.setBodegaDestino(getTransferencia().getBodegaDestino());
/*  533: 513 */     getTransferencia().getDetalleMovimientosInventario().add(d);
/*  534:     */   }
/*  535:     */   
/*  536:     */   public String eliminarDetalle()
/*  537:     */   {
/*  538: 517 */     DetalleMovimientoInventario d = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/*  539: 518 */     d.setEliminado(true);
/*  540: 520 */     if (d.getInventarioProducto() != null)
/*  541:     */     {
/*  542: 521 */       d.getInventarioProducto().setEliminado(true);
/*  543: 523 */       if (d.getInventarioProducto().getInventarioProductoTransferencia() != null) {
/*  544: 524 */         d.getInventarioProducto().getInventarioProductoTransferencia().setEliminado(true);
/*  545:     */       }
/*  546:     */     }
/*  547: 527 */     boolean indicadorBalanza = false;
/*  548: 528 */     for (LecturaBalanza lb : d.getListaLecturaBalanza())
/*  549:     */     {
/*  550: 529 */       lb.setEliminado(true);
/*  551: 530 */       indicadorBalanza = true;
/*  552:     */     }
/*  553: 532 */     actualizarIndicadorMostrarOrdenSalidaMaterial();
/*  554: 534 */     if (this.dtLecturaBalanza != null) {
/*  555: 535 */       this.dtLecturaBalanza.reset();
/*  556:     */     }
/*  557: 537 */     if (indicadorBalanza) {
/*  558: 538 */       guardarBorrador();
/*  559:     */     }
/*  560: 541 */     return "";
/*  561:     */   }
/*  562:     */   
/*  563:     */   public String actualizarDocumento()
/*  564:     */   {
/*  565: 551 */     getTransferencia().setDocumento(this.servicioDocumento.buscarPorId(Integer.valueOf(getTransferencia().getDocumento().getId())));
/*  566:     */     
/*  567: 553 */     setSecuenciaEditable(!this.transferencia.getDocumento().isIndicadorBloqueoSecuencia());
/*  568:     */     
/*  569: 555 */     return "";
/*  570:     */   }
/*  571:     */   
/*  572:     */   public String eliminar()
/*  573:     */   {
/*  574: 565 */     if (getTransferencia().getId() > 0) {
/*  575:     */       try
/*  576:     */       {
/*  577: 569 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  578:     */       }
/*  579:     */       catch (Exception e)
/*  580:     */       {
/*  581: 577 */         addInfoMessage(getLanguageController().getMensaje("msg_error_anular"));
/*  582: 578 */         LOG.error("ERROR AL ELIMINAR DESPACHO CLIENTE:", e);
/*  583:     */       }
/*  584:     */     } else {
/*  585: 581 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  586:     */     }
/*  587: 583 */     return "";
/*  588:     */   }
/*  589:     */   
/*  590:     */   public void cargarProducto()
/*  591:     */   {
/*  592:     */     try
/*  593:     */     {
/*  594: 590 */       Producto producto = getListaProductoBean().getProducto();
/*  595: 591 */       if (producto != null)
/*  596:     */       {
/*  597:     */         try
/*  598:     */         {
/*  599: 594 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/*  600:     */         }
/*  601:     */         catch (ExcepcionAS2 e)
/*  602:     */         {
/*  603: 596 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  604:     */         }
/*  605: 599 */         DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/*  606: 600 */         detalleMovimientoInventario.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  607: 601 */         detalleMovimientoInventario.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  608: 602 */         detalleMovimientoInventario.setProducto(producto);
/*  609: 603 */         if ((!isMostrarBalanza()) || (!producto.isIndicadorPesoBalanza())) {
/*  610: 604 */           detalleMovimientoInventario.setCantidad(producto.getTraCantidad().setScale(4, RoundingMode.HALF_UP));
/*  611:     */         }
/*  612: 606 */         detalleMovimientoInventario.setBodegaOrigen(this.transferencia.getBodegaOrigen());
/*  613: 607 */         detalleMovimientoInventario.setBodegaDestino(this.transferencia.getBodegaDestino());
/*  614: 608 */         detalleMovimientoInventario.setMovimientoInventario(this.transferencia);
/*  615:     */         
/*  616: 610 */         detalleMovimientoInventario.setUnidadConversion(producto.getUnidad());
/*  617: 611 */         InventarioProducto inventarioProducto = new InventarioProducto();
/*  618: 612 */         inventarioProducto.setOperacion(this.transferencia.getDocumento().getOperacion());
/*  619: 613 */         inventarioProducto.setDetalleMovimientoInventario(detalleMovimientoInventario);
/*  620: 614 */         inventarioProducto.setProducto(producto);
/*  621: 615 */         detalleMovimientoInventario.setInventarioProducto(inventarioProducto);
/*  622: 617 */         if (getListaProductoBean().getSaldoProductoLote() != null)
/*  623:     */         {
/*  624: 618 */           detalleMovimientoInventario.setBodegaOrigen(getListaProductoBean().getSaldoProductoLote().getBodega());
/*  625: 619 */           detalleMovimientoInventario.setCantidad(getListaProductoBean().getSaldoProductoLote().getSaldo()
/*  626: 620 */             .setScale(2, RoundingMode.HALF_UP));
/*  627: 621 */           detalleMovimientoInventario.setSaldo(detalleMovimientoInventario.getCantidad());
/*  628: 622 */           inventarioProducto.setBodega(detalleMovimientoInventario.getBodegaOrigen());
/*  629: 623 */           inventarioProducto.setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/*  630: 624 */           inventarioProducto.setCantidad(detalleMovimientoInventario.getCantidad());
/*  631:     */         }
/*  632:     */         else
/*  633:     */         {
/*  634: 626 */           BigDecimal saldo = this.servicioProducto.getSaldo(producto.getIdProducto(), detalleMovimientoInventario.getBodegaOrigen().getIdBodega(), this.transferencia
/*  635: 627 */             .getFecha());
/*  636: 628 */           detalleMovimientoInventario.setSaldo(saldo);
/*  637:     */         }
/*  638: 631 */         this.transferencia.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/*  639:     */       }
/*  640:     */     }
/*  641:     */     catch (Exception e)
/*  642:     */     {
/*  643: 635 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  644: 636 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  645:     */     }
/*  646:     */     finally
/*  647:     */     {
/*  648: 638 */       getListaProductoBean().setProducto(null);
/*  649: 639 */       getListaProductoBean().setSaldoProductoLote(null);
/*  650:     */     }
/*  651:     */   }
/*  652:     */   
/*  653:     */   public String cargarDatos()
/*  654:     */   {
/*  655: 650 */     return "";
/*  656:     */   }
/*  657:     */   
/*  658:     */   public List<Lote> autocompletarLotes(String consulta)
/*  659:     */   {
/*  660: 654 */     DetalleMovimientoInventario detalleMovimientoInventario = (DetalleMovimientoInventario)this.dtDetalleMovimientoInventario.getRowData();
/*  661: 655 */     return this.servicioLote.autocompletarLote(detalleMovimientoInventario.getProducto(), consulta);
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void eventBodegaOrigen()
/*  665:     */   {
/*  666: 659 */     getListaProductoBean().setBodega(getTransferencia().getBodegaOrigen());
/*  667: 660 */     for (DetalleMovimientoInventario dmi : this.transferencia.getDetalleMovimientosInventario()) {
/*  668: 661 */       dmi.setBodegaOrigen(getTransferencia().getBodegaOrigen());
/*  669:     */     }
/*  670:     */   }
/*  671:     */   
/*  672:     */   public void eventBodegaDestino()
/*  673:     */   {
/*  674: 667 */     for (DetalleMovimientoInventario dmi : this.transferencia.getDetalleMovimientosInventario()) {
/*  675: 668 */       dmi.setBodegaDestino(getTransferencia().getBodegaDestino());
/*  676:     */     }
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void cargarDetalleMovimientoClienteTxt(FileUploadEvent event)
/*  680:     */   {
/*  681:     */     try
/*  682:     */     {
/*  683: 679 */       String fileName = "transferencia_bodega" + event.getFile().getFileName();
/*  684: 680 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  685: 681 */       this.servicioMovimientoInventario.cargarDetalleTransferenciaArchivoTexto(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/*  686: 682 */         .getTipoOrganizacion(), this.transferencia, fileName, input, getTransferencia().getBodegaOrigen(), getTransferencia()
/*  687: 683 */         .getBodegaDestino());
/*  688: 684 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  689:     */     }
/*  690:     */     catch (ExcepcionAS2 e)
/*  691:     */     {
/*  692: 687 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  693: 688 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  694:     */     }
/*  695:     */     catch (Exception e)
/*  696:     */     {
/*  697: 690 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  698: 691 */       e.printStackTrace();
/*  699:     */     }
/*  700:     */   }
/*  701:     */   
/*  702:     */   public String copiar()
/*  703:     */   {
/*  704:     */     try
/*  705:     */     {
/*  706: 697 */       MovimientoInventario auxMovimientoInventarioCopias = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.transferencia.getId()));
/*  707: 698 */       this.transferencia = this.servicioMovimientoInventario.copiarMovimientoInventario(auxMovimientoInventarioCopias);
/*  708: 699 */       for (DetalleMovimientoInventario de : this.transferencia.getDetalleMovimientosInventario()) {
/*  709:     */         try
/*  710:     */         {
/*  711: 701 */           cargarProductoDesdeCodigoEnDetalle(de.getProducto().getCodigo(), de);
/*  712:     */         }
/*  713:     */         catch (ExcepcionAS2 e)
/*  714:     */         {
/*  715: 703 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  716: 704 */           e.printStackTrace();
/*  717:     */         }
/*  718:     */       }
/*  719: 708 */       setEditado(true);
/*  720:     */     }
/*  721:     */     catch (ExcepcionAS2Financiero e)
/*  722:     */     {
/*  723: 711 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  724:     */     }
/*  725: 714 */     return "";
/*  726:     */   }
/*  727:     */   
/*  728:     */   public List<Documento> getListaDocumentosTransferencia()
/*  729:     */   {
/*  730: 722 */     if (this.listaDocumentosTransferencia == null) {
/*  731:     */       try
/*  732:     */       {
/*  733: 724 */         this.listaDocumentosTransferencia = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.TRANSFERENCIA_BODEGA, 
/*  734: 725 */           AppUtil.getOrganizacion().getIdOrganizacion());
/*  735:     */       }
/*  736:     */       catch (ExcepcionAS2 e)
/*  737:     */       {
/*  738: 727 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  739:     */       }
/*  740:     */     }
/*  741: 731 */     return this.listaDocumentosTransferencia;
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void setListaDocumentosTransferencia(List<Documento> listaDocumentosTransferencia)
/*  745:     */   {
/*  746: 739 */     this.listaDocumentosTransferencia = listaDocumentosTransferencia;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public List<DetalleMovimientoInventario> getDetalleMovimientoInventario()
/*  750:     */   {
/*  751: 749 */     List<DetalleMovimientoInventario> detalle = new ArrayList();
/*  752: 750 */     for (DetalleMovimientoInventario dm : getTransferencia().getDetalleMovimientosInventario()) {
/*  753: 752 */       if (!dm.isEliminado()) {
/*  754: 753 */         detalle.add(dm);
/*  755:     */       }
/*  756:     */     }
/*  757: 757 */     return detalle;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public void setDetalleMovimientoInventario(List<DetalleMovimientoInventario> detalleMovimientoInventario)
/*  761:     */   {
/*  762: 767 */     getTransferencia().setDetalleMovimientosInventario(detalleMovimientoInventario);
/*  763:     */   }
/*  764:     */   
/*  765:     */   public DataTable getDtMovimientoInventario()
/*  766:     */   {
/*  767: 776 */     return this.dtMovimientoInventario;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void setDtMovimientoInventario(DataTable dtMovimientoInventario)
/*  771:     */   {
/*  772: 786 */     this.dtMovimientoInventario = dtMovimientoInventario;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public DataTable getDtDetalleMovimientoInventario()
/*  776:     */   {
/*  777: 795 */     return this.dtDetalleMovimientoInventario;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setDtDetalleMovimientoInventario(DataTable dtDetalleMovimientoInventario)
/*  781:     */   {
/*  782: 805 */     this.dtDetalleMovimientoInventario = dtDetalleMovimientoInventario;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public MovimientoInventario getTransferencia()
/*  786:     */   {
/*  787: 814 */     return this.transferencia;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setTransferencia(MovimientoInventario transferencia)
/*  791:     */   {
/*  792: 824 */     this.transferencia = transferencia;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public LazyDataModel<MovimientoInventario> getListaTransferencia()
/*  796:     */   {
/*  797: 833 */     return this.listaTransferencia;
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void setListaTransferencia(LazyDataModel<MovimientoInventario> listaTransferencia)
/*  801:     */   {
/*  802: 843 */     this.listaTransferencia = listaTransferencia;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public List<Bodega> getListaBodegaOrigen()
/*  806:     */   {
/*  807: 852 */     if (this.listaBodegaOrigen == null) {
/*  808: 853 */       this.listaBodegaOrigen = AppUtil.getUsuarioEnSesion().getListaBodega();
/*  809:     */     }
/*  810: 855 */     return this.listaBodegaOrigen;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public List<Bodega> getListaBodegaDestino()
/*  814:     */   {
/*  815: 864 */     if (this.listaBodegaDestino == null)
/*  816:     */     {
/*  817: 865 */       Map<String, String> filters = new HashMap();
/*  818: 866 */       filters.put("activo", "true");
/*  819: 867 */       filters = agregarFiltroOrganizacion(filters);
/*  820: 868 */       this.listaBodegaDestino = this.servicioBodega.obtenerBodegaCombo("nombre", true, filters);
/*  821:     */     }
/*  822: 870 */     return this.listaBodegaDestino;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public InventarioProducto getInventarioProducto()
/*  826:     */   {
/*  827: 879 */     if (this.inventarioProducto == null)
/*  828:     */     {
/*  829: 880 */       this.inventarioProducto = new InventarioProducto();
/*  830: 881 */       this.inventarioProducto.setOperacion(this.transferencia.getDocumento().getOperacion());
/*  831:     */     }
/*  832: 883 */     return this.inventarioProducto;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/*  836:     */   {
/*  837: 893 */     this.inventarioProducto = inventarioProducto;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public DataTable getDtInventarioProductoLote()
/*  841:     */   {
/*  842: 902 */     return this.dtInventarioProductoLote;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public void setDtInventarioProductoLote(DataTable dtInventarioProductoLote)
/*  846:     */   {
/*  847: 912 */     this.dtInventarioProductoLote = dtInventarioProductoLote;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public String getCodigoCabecera()
/*  851:     */   {
/*  852: 921 */     return this.codigoCabecera;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public void setCodigoCabecera(String codigoCabecera)
/*  856:     */   {
/*  857: 931 */     this.codigoCabecera = codigoCabecera;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public Lote getLoteCrear()
/*  861:     */   {
/*  862: 940 */     if (this.loteCrear == null) {
/*  863: 941 */       this.loteCrear = new Lote();
/*  864:     */     }
/*  865: 943 */     return this.loteCrear;
/*  866:     */   }
/*  867:     */   
/*  868:     */   public void setLoteCrear(Lote loteCrear)
/*  869:     */   {
/*  870: 953 */     this.loteCrear = loteCrear;
/*  871:     */   }
/*  872:     */   
/*  873:     */   public DetalleMovimientoInventario getDetalleMovimientoInventarioSeleccionado()
/*  874:     */   {
/*  875: 962 */     return this.detalleMovimientoInventarioSeleccionado;
/*  876:     */   }
/*  877:     */   
/*  878:     */   public void setDetalleMovimientoInventarioSeleccionado(DetalleMovimientoInventario detalleMovimientoInventarioSeleccionado)
/*  879:     */   {
/*  880: 972 */     this.detalleMovimientoInventarioSeleccionado = detalleMovimientoInventarioSeleccionado;
/*  881:     */   }
/*  882:     */   
/*  883:     */   public void setListaBodegaDestino(List<Bodega> listaBodegaDestino)
/*  884:     */   {
/*  885: 976 */     this.listaBodegaDestino = listaBodegaDestino;
/*  886:     */   }
/*  887:     */   
/*  888:     */   public Integer getIdTransferenciaBodega()
/*  889:     */   {
/*  890: 980 */     return this.idTransferenciaBodega;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void setIdTransferenciaBodega(Integer idTransferenciaBodega)
/*  894:     */   {
/*  895: 984 */     this.idTransferenciaBodega = idTransferenciaBodega;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  899:     */   {
/*  900: 988 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  901:     */   }
/*  902:     */   
/*  903:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/*  904:     */   {
/*  905: 992 */     if (this.listaDireccionEmpresa == null) {
/*  906: 993 */       this.listaDireccionEmpresa = new ArrayList();
/*  907:     */     }
/*  908: 995 */     return this.listaDireccionEmpresa;
/*  909:     */   }
/*  910:     */   
/*  911:     */   public void cargarDirecciones(Empresa empresa)
/*  912:     */   {
/*  913:1000 */     this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(this.transferencia.getEmpresa().getId());
/*  914:1002 */     for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/*  915:1003 */       if (de.isIndicadorDireccionPrincipal())
/*  916:     */       {
/*  917:1004 */         this.transferencia.setDireccionEmpresa(de);
/*  918:1005 */         break;
/*  919:     */       }
/*  920:     */     }
/*  921:     */   }
/*  922:     */   
/*  923:     */   public boolean isMostrarCliente()
/*  924:     */   {
/*  925:1012 */     return ParametrosSistema.getTransferenciaBodegaMostrarCliente(AppUtil.getOrganizacion().getId()).booleanValue();
/*  926:     */   }
/*  927:     */   
/*  928:     */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/*  929:     */   {
/*  930:1016 */     return this.ordenSalidaMaterial;
/*  931:     */   }
/*  932:     */   
/*  933:     */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/*  934:     */   {
/*  935:1020 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/*  936:     */   }
/*  937:     */   
/*  938:     */   public boolean isIndicadorMostrarOrdenSalidaMaterial()
/*  939:     */   {
/*  940:1024 */     return this.indicadorMostrarOrdenSalidaMaterial;
/*  941:     */   }
/*  942:     */   
/*  943:     */   public void setIndicadorMostrarOrdenSalidaMaterial(boolean indicadorMostrarOrdenSalidaMaterial)
/*  944:     */   {
/*  945:1028 */     this.indicadorMostrarOrdenSalidaMaterial = indicadorMostrarOrdenSalidaMaterial;
/*  946:     */   }
/*  947:     */   
/*  948:     */   public void cargarDetalleDesdeOrdenSalidaMaterial(SelectEvent event)
/*  949:     */   {
/*  950:1033 */     OrdenSalidaMaterial ordenSalidaMaterial = (OrdenSalidaMaterial)event.getObject();
/*  951:1035 */     if ((getTransferencia() != null) && (ordenSalidaMaterial != null)) {
/*  952:     */       try
/*  953:     */       {
/*  954:1039 */         this.servicioOrdenSalidaMaterial.generarTransferenciaBodega(ordenSalidaMaterial, this.transferencia);
/*  955:     */       }
/*  956:     */       catch (ExcepcionAS2 e)
/*  957:     */       {
/*  958:1042 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  959:1043 */         LOG.info("ERROR AL CARGAR ORDEN-SALIDA-MERCADERIA TRANSFERENCIA BODEGA:", e);
/*  960:     */       }
/*  961:     */     }
/*  962:1047 */     actualizarIndicadorMostrarOrdenSalidaMaterial();
/*  963:     */   }
/*  964:     */   
/*  965:     */   public void actualizarIndicadorMostrarOrdenSalidaMaterial()
/*  966:     */   {
/*  967:1051 */     setIndicadorMostrarOrdenSalidaMaterial(false);
/*  968:1052 */     if (getTransferencia() != null) {
/*  969:1053 */       for (DetalleMovimientoInventario dm : getTransferencia().getDetalleMovimientosInventario()) {
/*  970:1054 */         if ((!dm.isEliminado()) && (dm.getDetalleOrdenSalidaMaterial() != null))
/*  971:     */         {
/*  972:1055 */           setIndicadorMostrarOrdenSalidaMaterial(true);
/*  973:1056 */           break;
/*  974:     */         }
/*  975:     */       }
/*  976:     */     }
/*  977:     */   }
/*  978:     */   
/*  979:     */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(String numero)
/*  980:     */   {
/*  981:1063 */     List<OrdenSalidaMaterial> lista = this.servicioOrdenSalidaMaterial.autocompletarOrdenSalidaMaterial(AppUtil.getOrganizacion().getId(), numero, 
/*  982:1064 */       Boolean.valueOf(true));
/*  983:1065 */     return lista;
/*  984:     */   }
/*  985:     */   
/*  986:     */   public void cargarSaldosBodegaOrigenTransferencia()
/*  987:     */   {
/*  988:     */     try
/*  989:     */     {
/*  990:1070 */       if (this.transferencia != null)
/*  991:     */       {
/*  992:1071 */         for (DetalleMovimientoInventario dmi : this.transferencia.getDetalleMovimientosInventario()) {
/*  993:1072 */           dmi.setEliminado(true);
/*  994:     */         }
/*  995:1074 */         this.servicioMovimientoInventario.cargarSaldosBodegaOrigenTransferencia(this.transferencia);
/*  996:     */       }
/*  997:     */     }
/*  998:     */     catch (AS2Exception e)
/*  999:     */     {
/* 1000:1077 */       JsfUtil.addErrorMessage(e, "");
/* 1001:     */     }
/* 1002:     */     catch (ExcepcionAS2 e)
/* 1003:     */     {
/* 1004:1079 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1005:     */     }
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public void enviarMail()
/* 1009:     */   {
/* 1010:     */     try
/* 1011:     */     {
/* 1012:1085 */       if (this.transferencia.getGuiaRemision() != null)
/* 1013:     */       {
/* 1014:1086 */         GuiaRemision guiaRem = this.servicioGuiaRemision.cargarDetalle(this.transferencia.getGuiaRemision().getId());
/* 1015:1087 */         if (((guiaRem.getDocumento() != null) && (!guiaRem.getDocumento().isIndicadorDocumentoElectronico())) || 
/* 1016:1088 */           (guiaRem.getEstado().equals(Estado.ANULADO)) || (guiaRem.getEstado().equals(Estado.EN_ESPERA)) || 
/* 1017:1089 */           (guiaRem.getEstado().equals(Estado.EN_ESPERA_CONTINGENCIA))) {
/* 1018:1090 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1019:     */         } else {
/* 1020:1092 */           this.servicioGuiaRemision.enviarMail(guiaRem, this.mails);
/* 1021:     */         }
/* 1022:     */       }
/* 1023:     */       else
/* 1024:     */       {
/* 1025:1095 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 1026:     */       }
/* 1027:     */     }
/* 1028:     */     catch (ExcepcionAS2 e)
/* 1029:     */     {
/* 1030:1098 */       addErrorMessage(this.transferencia.getNumero() + " -> " + this.mails + e.getMessage() + e.getCodigoExcepcion());
/* 1031:     */     }
/* 1032:     */     catch (Exception e)
/* 1033:     */     {
/* 1034:1100 */       addErrorMessage(this.transferencia.getNumero() + " -> " + this.mails + e.getMessage());
/* 1035:     */     }
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public String getMails()
/* 1039:     */   {
/* 1040:1106 */     return this.mails;
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public void setMails(String mails)
/* 1044:     */   {
/* 1045:1110 */     this.mails = mails;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public StreamedContent getXMLSRI()
/* 1049:     */   {
/* 1050:1114 */     if ((this.transferencia != null) && (this.transferencia.getId() != 0) && (this.transferencia.getGuiaRemision() != null))
/* 1051:     */     {
/* 1052:1115 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 1053:     */       
/* 1054:     */ 
/* 1055:1118 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.GUIA_REMISION.toString();
/* 1056:1119 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 1057:1120 */       String nombreArchivo = this.transferencia.getGuiaRemision().getNumero() + "-" + this.transferencia.getGuiaRemision().getClaveAcceso() + ".xml";
/* 1058:1121 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 1059:     */       try
/* 1060:     */       {
/* 1061:1123 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 1062:     */       }
/* 1063:     */       catch (FileNotFoundException e)
/* 1064:     */       {
/* 1065:1125 */         e.printStackTrace();
/* 1066:1126 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 1067:1127 */         return null;
/* 1068:     */       }
/* 1069:     */     }
/* 1070:1130 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1071:1131 */     return null;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public LecturaBalanza getLecturaBalanza()
/* 1075:     */   {
/* 1076:1136 */     if (this.lecturaBalanza == null)
/* 1077:     */     {
/* 1078:1137 */       this.lecturaBalanza = new LecturaBalanza();
/* 1079:1138 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1080:1139 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/* 1081:1140 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/* 1082:1141 */       this.lecturaBalanza.setOperacion(-1);
/* 1083:     */     }
/* 1084:1143 */     return this.lecturaBalanza;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/* 1088:     */   {
/* 1089:1147 */     this.lecturaBalanza = lecturaBalanza;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public List<UnidadManejo> getListaUnidadManejo()
/* 1093:     */   {
/* 1094:1154 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/* 1095:     */     {
/* 1096:1155 */       this.listaUnidadManejo = new ArrayList();
/* 1097:1156 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 1098:1157 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/* 1099:     */       }
/* 1100:     */     }
/* 1101:1160 */     return this.listaUnidadManejo;
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 1105:     */   {
/* 1106:1168 */     this.listaUnidadManejo = listaUnidadManejo;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public List<UnidadManejo> getListaPallet()
/* 1110:     */   {
/* 1111:1172 */     if (this.listaPallet == null)
/* 1112:     */     {
/* 1113:1173 */       Map<String, String> filters = new HashMap();
/* 1114:1174 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 1115:1175 */       filters.put("activo", "true");
/* 1116:1176 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/* 1117:1177 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/* 1118:     */     }
/* 1119:1179 */     return this.listaPallet;
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public void setListaPallet(List<UnidadManejo> listaPallet)
/* 1123:     */   {
/* 1124:1183 */     this.listaPallet = listaPallet;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public boolean isMostrarBalanza()
/* 1128:     */   {
/* 1129:1187 */     if (this.mostrarBalanza == null) {
/* 1130:1188 */       this.mostrarBalanza = ParametrosSistema.getTransferenciaUsaBalanza(AppUtil.getOrganizacion().getId());
/* 1131:     */     }
/* 1132:1190 */     return this.mostrarBalanza.booleanValue();
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public void setMostrarBalanza(boolean mostrarBalanza)
/* 1136:     */   {
/* 1137:1194 */     this.mostrarBalanza = Boolean.valueOf(mostrarBalanza);
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 1141:     */   {
/* 1142:1198 */     List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/* 1143:1199 */     for (DetalleMovimientoInventario detalle : this.transferencia.getDetalleMovimientosInventario()) {
/* 1144:1200 */       for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/* 1145:1201 */         if ((!lectura.isEliminado()) && (lectura.getOperacion() == -1)) {
/* 1146:1202 */           listaLecturaBalanza.add(lectura);
/* 1147:     */         }
/* 1148:     */       }
/* 1149:     */     }
/* 1150:1207 */     return listaLecturaBalanza;
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   public DataTable getDtLecturaBalanza()
/* 1154:     */   {
/* 1155:1211 */     return this.dtLecturaBalanza;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/* 1159:     */   {
/* 1160:1215 */     this.dtLecturaBalanza = dtLecturaBalanza;
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public List<Dispositivo> getListaDispositivo()
/* 1164:     */   {
/* 1165:1219 */     if (this.listaDispositivo == null)
/* 1166:     */     {
/* 1167:1220 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1168:1221 */       filtros.put("activo", "true");
/* 1169:1222 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 1170:     */     }
/* 1171:1224 */     return this.listaDispositivo;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/* 1175:     */   {
/* 1176:     */     try
/* 1177:     */     {
/* 1178:1229 */       BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lectura);
/* 1179:1230 */       BigDecimal cantidadProducto = cantidades[0];
/* 1180:1231 */       BigDecimal cantidadInformativa = cantidades[1];
/* 1181:1234 */       for (DetalleMovimientoInventario detalle : this.transferencia.getDetalleMovimientosInventario()) {
/* 1182:1235 */         if (detalle.getId() == lectura.getDetalleMovimientoInventario().getId())
/* 1183:     */         {
/* 1184:1236 */           detalle.setCantidad(detalle.getCantidad().subtract(cantidadProducto));
/* 1185:1237 */           if (cantidadInformativa != null) {
/* 1186:1238 */             detalle.setCantidadUnidadInformativa(detalle.getCantidadUnidadInformativa().subtract(cantidadInformativa));
/* 1187:     */           }
/* 1188:     */         }
/* 1189:     */       }
/* 1190:1243 */       lectura.getDetalleMovimientoInventario().setCantidad(lectura.getDetalleMovimientoInventario().getCantidad().subtract(cantidadProducto));
/* 1191:1244 */       if (cantidadInformativa != null) {
/* 1192:1245 */         lectura.getDetalleMovimientoInventario().setCantidadUnidadInformativa(lectura
/* 1193:1246 */           .getDetalleMovimientoInventario().getCantidadUnidadInformativa().subtract(cantidadInformativa));
/* 1194:     */       }
/* 1195:1249 */       lectura.setEliminado(true);
/* 1196:     */       
/* 1197:1251 */       guardarBorrador();
/* 1198:     */     }
/* 1199:     */     catch (AS2Exception e)
/* 1200:     */     {
/* 1201:1253 */       e.printStackTrace();
/* 1202:1254 */       JsfUtil.addErrorMessage(e, "");
/* 1203:     */     }
/* 1204:     */     catch (Exception e)
/* 1205:     */     {
/* 1206:1256 */       e.printStackTrace();
/* 1207:1257 */       JsfUtil.addErrorMessage(e.getMessage());
/* 1208:     */     }
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   public void calcularCantidad()
/* 1212:     */   {
/* 1213:1262 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 1214:1263 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 1215:     */     }
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public void capturarPesoListener()
/* 1219:     */   {
/* 1220:1268 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 1221:     */       try
/* 1222:     */       {
/* 1223:1271 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, true);
/* 1224:     */       }
/* 1225:     */       catch (AS2Exception ex)
/* 1226:     */       {
/* 1227:1273 */         JsfUtil.addErrorMessage(ex, "");
/* 1228:     */       }
/* 1229:     */     }
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public void agregarPesoListener()
/* 1233:     */   {
/* 1234:1279 */     if ((this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0))
/* 1235:     */     {
/* 1236:     */       try
/* 1237:     */       {
/* 1238:1281 */         this.servicioMarcacionDispositivo.validarCantidad(this.lecturaBalanza);
/* 1239:     */       }
/* 1240:     */       catch (AS2Exception e)
/* 1241:     */       {
/* 1242:1283 */         JsfUtil.addErrorMessage(e, "");
/* 1243:1284 */         return;
/* 1244:     */       }
/* 1245:1286 */       if (this.detalleTransferenciaSeleccionada == null)
/* 1246:     */       {
/* 1247:1287 */         addErrorMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/* 1248:1288 */         return;
/* 1249:     */       }
/* 1250:     */       try
/* 1251:     */       {
/* 1252:1291 */         BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(this.lecturaBalanza);
/* 1253:1292 */         BigDecimal cantidadProducto = cantidades[0];
/* 1254:1293 */         BigDecimal cantidadInformativa = cantidades[1];
/* 1255:     */         
/* 1256:1295 */         this.detalleTransferenciaSeleccionada.setCantidad(this.detalleTransferenciaSeleccionada.getCantidad().add(cantidadProducto));
/* 1257:1297 */         if (cantidadInformativa != null)
/* 1258:     */         {
/* 1259:1298 */           if (this.detalleTransferenciaSeleccionada.getCantidadUnidadInformativa() == null) {
/* 1260:1299 */             this.detalleTransferenciaSeleccionada.setCantidadUnidadInformativa(BigDecimal.ZERO);
/* 1261:     */           }
/* 1262:1301 */           this.detalleTransferenciaSeleccionada.setCantidadUnidadInformativa(this.detalleTransferenciaSeleccionada.getCantidadUnidadInformativa()
/* 1263:1302 */             .add(cantidadInformativa));
/* 1264:     */         }
/* 1265:1305 */         this.lecturaBalanza.setDetalleMovimientoInventario(this.detalleTransferenciaSeleccionada);
/* 1266:1306 */         this.detalleTransferenciaSeleccionada.getListaLecturaBalanza().add(this.lecturaBalanza);
/* 1267:     */         
/* 1268:1308 */         guardarBorrador();
/* 1269:     */       }
/* 1270:     */       catch (AS2Exception e)
/* 1271:     */       {
/* 1272:1310 */         e.printStackTrace();
/* 1273:1311 */         JsfUtil.addErrorMessage(e, "");
/* 1274:     */       }
/* 1275:     */       catch (Exception e)
/* 1276:     */       {
/* 1277:1313 */         e.printStackTrace();
/* 1278:1314 */         JsfUtil.addErrorMessage(e.getMessage());
/* 1279:     */       }
/* 1280:     */     }
/* 1281:     */   }
/* 1282:     */   
/* 1283:     */   public void cargarProductoSeleccionadoPesa()
/* 1284:     */   {
/* 1285:1320 */     if ((isMostrarBalanza()) && (this.lecturaBalanza != null)) {
/* 1286:1321 */       if (this.detalleTransferenciaSeleccionada.getProducto().isIndicadorPesoBalanza())
/* 1287:     */       {
/* 1288:1322 */         this.lecturaBalanza.setProducto(this.servicioProducto.buscarPorId(this.detalleTransferenciaSeleccionada.getProducto().getId()));
/* 1289:     */       }
/* 1290:     */       else
/* 1291:     */       {
/* 1292:1324 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 1293:1325 */         this.lecturaBalanza = null;
/* 1294:1326 */         getLecturaBalanza().setDispositivo(dispositivo);
/* 1295:     */       }
/* 1296:     */     }
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public DetalleMovimientoInventario getDetalleTransferenciaSeleccionada()
/* 1300:     */   {
/* 1301:1332 */     return this.detalleTransferenciaSeleccionada;
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public void setDetalleTransferenciaSeleccionada(DetalleMovimientoInventario detalleTransferenciaSeleccionada)
/* 1305:     */   {
/* 1306:1336 */     this.detalleTransferenciaSeleccionada = detalleTransferenciaSeleccionada;
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public ProductoBean getProductoBean()
/* 1310:     */   {
/* 1311:1340 */     return this.productoBean;
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public void setProductoBean(ProductoBean productoBean)
/* 1315:     */   {
/* 1316:1344 */     this.productoBean = productoBean;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   public AS2Exception getExContabilizacion()
/* 1320:     */   {
/* 1321:1348 */     return this.exContabilizacion;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 1325:     */   {
/* 1326:1352 */     this.exContabilizacion = exContabilizacion;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public String getRutaPlantilla()
/* 1330:     */   {
/* 1331:1357 */     return "/resources/plantillas/inventario/AS2 Transferencia Bodega.xls";
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public String getNombrePlantilla()
/* 1335:     */   {
/* 1336:1362 */     return "AS2 Transferencia Bodega.xls";
/* 1337:     */   }
/* 1338:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.TransferenciaBodegaBean
 * JD-Core Version:    0.7.0.1
 */