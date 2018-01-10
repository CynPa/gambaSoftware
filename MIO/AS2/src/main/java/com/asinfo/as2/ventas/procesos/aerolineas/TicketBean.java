/*    1:     */ package com.asinfo.as2.ventas.procesos.aerolineas;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    5:     */ import com.asinfo.as2.controller.LanguageController;
/*    6:     */ import com.asinfo.as2.dao.ProductoDao;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    9:     */ import com.asinfo.as2.entities.AjustePrefacturaCliente;
/*   10:     */ import com.asinfo.as2.entities.Ciudad;
/*   11:     */ import com.asinfo.as2.entities.Cliente;
/*   12:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   13:     */ import com.asinfo.as2.entities.DetalleAjustePrefacturaCliente;
/*   14:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   15:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   16:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   17:     */ import com.asinfo.as2.entities.Documento;
/*   18:     */ import com.asinfo.as2.entities.Empresa;
/*   19:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   20:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   21:     */ import com.asinfo.as2.entities.Organizacion;
/*   22:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   23:     */ import com.asinfo.as2.entities.PrefacturaCliente;
/*   24:     */ import com.asinfo.as2.entities.Producto;
/*   25:     */ import com.asinfo.as2.entities.Sucursal;
/*   26:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   27:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   28:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   29:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   30:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   31:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   32:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   33:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   34:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   35:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   36:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   37:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   38:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   39:     */ import com.asinfo.as2.util.AppUtil;
/*   40:     */ import com.asinfo.as2.utils.DatosSRI;
/*   41:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   42:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   43:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   44:     */ import com.asinfo.as2.ventas.procesos.ErrorCarga;
/*   45:     */ import com.asinfo.as2.ventas.procesos.FacturaClienteBaseBean;
/*   46:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   47:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   48:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   49:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
/*   50:     */ import java.io.BufferedInputStream;
/*   51:     */ import java.io.InputStream;
/*   52:     */ import java.math.BigDecimal;
/*   53:     */ import java.util.ArrayList;
/*   54:     */ import java.util.HashMap;
/*   55:     */ import java.util.HashSet;
/*   56:     */ import java.util.Iterator;
/*   57:     */ import java.util.List;
/*   58:     */ import java.util.Map;
/*   59:     */ import java.util.Set;
/*   60:     */ import javax.annotation.PostConstruct;
/*   61:     */ import javax.ejb.EJB;
/*   62:     */ import javax.faces.bean.ManagedBean;
/*   63:     */ import javax.faces.bean.ViewScoped;
/*   64:     */ import javax.faces.component.html.HtmlInputText;
/*   65:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   66:     */ import javax.validation.constraints.NotNull;
/*   67:     */ import javax.validation.constraints.Size;
/*   68:     */ import org.apache.log4j.Logger;
/*   69:     */ import org.primefaces.component.datatable.DataTable;
/*   70:     */ import org.primefaces.event.FileUploadEvent;
/*   71:     */ import org.primefaces.event.SelectEvent;
/*   72:     */ import org.primefaces.model.UploadedFile;
/*   73:     */ 
/*   74:     */ @ManagedBean
/*   75:     */ @ViewScoped
/*   76:     */ public class TicketBean
/*   77:     */   extends FacturaClienteBaseBean
/*   78:     */ {
/*   79:     */   private static final long serialVersionUID = -7836267446292666793L;
/*   80:     */   private boolean mostrarCheckExportar;
/*   81:     */   private Integer idDespachoCliente;
/*   82:  90 */   private List<ErrorCarga> errores = new ArrayList();
/*   83:  92 */   private List<DespachoCliente> listaDespachoClientePendientes = new ArrayList();
/*   84:  94 */   private List<DespachoCliente> listaDespachoClienteSeleccionados = new ArrayList();
/*   85:  96 */   private List<DespachoCliente> listaDespachoClienteAsignados = new ArrayList();
/*   86:     */   private PrefacturaCliente prefacturaCliente;
/*   87:     */   private String identificacionCliente;
/*   88:     */   @NotNull
/*   89:     */   private TipoIdentificacion tipoIdentificacionCliente;
/*   90:     */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*   91:     */   private List<EntidadUsuario> listaAgenteComercial;
/*   92:     */   @Size(min=10, max=100)
/*   93:     */   private String nombreCliente;
/*   94:     */   @Size(min=2, max=50)
/*   95:     */   private String direccionCliente;
/*   96:     */   @Size(max=13)
/*   97:     */   private String telefonoCliente;
/*   98:     */   @NotNull
/*   99:     */   private Ciudad ciudad;
/*  100:     */   @NotNull
/*  101:     */   private EntidadUsuario agenteComercial;
/*  102:     */   private String email;
/*  103:     */   private boolean renderClienteNuevo;
/*  104:     */   @EJB
/*  105:     */   protected transient ServicioPrefacturaCliente servicioPrefacturaCliente;
/*  106:     */   @EJB
/*  107:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  108:     */   @EJB
/*  109:     */   private ProductoDao productoDao;
/*  110:     */   protected DataTable dtDetalleFacturaClienteImpuestoExtranjero;
/*  111:     */   protected DataTable dtDetalleFacturaClienteImpuestoNacional;
/*  112:     */   private List<DetalleFacturaCliente> listaDetalleFacturaImpuestoExtranjero;
/*  113:     */   private List<DetalleFacturaCliente> listaDetalleFacturaImpuestoNacional;
/*  114:     */   private List<DetalleFacturaCliente> listaDetalleFacturaTicket;
/*  115:     */   private boolean agregarListaDetalleImpuestoExtranjero;
/*  116:     */   private boolean nacional;
/*  117:     */   
/*  118:     */   @PostConstruct
/*  119:     */   public void init()
/*  120:     */   {
/*  121: 142 */     super.init();
/*  122: 143 */     limpiarFiltrosListaProducto();
/*  123:     */   }
/*  124:     */   
/*  125:     */   public void obtenerFiltros(Map<String, String> filters)
/*  126:     */   {
/*  127: 155 */     if (this.idFacturaCliente != null)
/*  128:     */     {
/*  129: 156 */       filters.put("idFacturaCliente", this.idFacturaCliente.toString());
/*  130: 157 */       this.idFacturaCliente = null;
/*  131:     */     }
/*  132: 159 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  133: 160 */     filters.put("documento.tipoComprobanteSRI.codigo", "=11");
/*  134:     */   }
/*  135:     */   
/*  136:     */   public String limpiar()
/*  137:     */   {
/*  138: 174 */     super.limpiar();
/*  139: 175 */     this.prefacturaCliente = null;
/*  140: 176 */     this.listaDespachoClienteAsignados = new ArrayList();
/*  141: 177 */     this.listaDespachoClientePendientes = new ArrayList();
/*  142:     */     
/*  143: 179 */     HashMap<String, String> hmFiltros = new HashMap();
/*  144: 180 */     hmFiltros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  145: 181 */     hmFiltros.put("indicadorImpuestos", "true");
/*  146: 182 */     hmFiltros.put("indicadorVenta", "true");
/*  147: 183 */     hmFiltros.put("predeterminado", "true");
/*  148:     */     try
/*  149:     */     {
/*  150: 186 */       DetalleFacturaCliente d = new DetalleFacturaCliente();
/*  151: 187 */       d.setFacturaCliente(getFacturaCliente());
/*  152: 188 */       d.setProducto(this.productoDao.buscarProducto(hmFiltros));
/*  153: 189 */       d.setPrecio(BigDecimal.ZERO);
/*  154: 190 */       d.setDescuento(BigDecimal.ZERO);
/*  155: 191 */       d.setCantidad(BigDecimal.ONE);
/*  156: 192 */       d.setIndicadorImpuesto(true);
/*  157:     */       
/*  158: 194 */       d.setUnidadVenta(this.productoDao.buscarProducto(hmFiltros).getUnidadVenta());
/*  159:     */       
/*  160: 196 */       this.servicioFacturaCliente.obtenerImpuestosProductos(d.getProducto(), d);
/*  161:     */       
/*  162: 198 */       getFacturaCliente().getListaDetalleFacturaCliente().add(d);
/*  163:     */       
/*  164:     */ 
/*  165:     */ 
/*  166: 202 */       Integer idEmpresa = ParametrosSistema.getClienteGenerico(AppUtil.getOrganizacion().getIdOrganizacion());
/*  167: 203 */       if (idEmpresa == null)
/*  168:     */       {
/*  169: 204 */         addInfoMessage(getLanguageController().getMensaje("msg_info_cliente_generico"));
/*  170:     */       }
/*  171:     */       else
/*  172:     */       {
/*  173: 206 */         Map<String, String> filters = new HashMap();
/*  174: 207 */         filters.put("idEmpresa", idEmpresa.toString());
/*  175: 208 */         List<Empresa> lista = this.servicioEmpresa.obtenerListaCombo("codigo", false, filters);
/*  176: 209 */         if (!lista.isEmpty())
/*  177:     */         {
/*  178: 210 */           actualizarCliente((Empresa)lista.get(0));
/*  179:     */           
/*  180: 212 */           EntidadUsuario agenteComercial = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  181: 213 */           getFacturaCliente().setDescripcion2("Pasajero Final");
/*  182: 214 */           if (agenteComercial.isIndicadorAgenteComercial()) {
/*  183: 215 */             getFacturaCliente().setAgenteComercial(agenteComercial);
/*  184:     */           } else {
/*  185: 217 */             getFacturaCliente().setAgenteComercial(null);
/*  186:     */           }
/*  187:     */         }
/*  188:     */       }
/*  189: 223 */       separarDetalleFactura();
/*  190:     */     }
/*  191:     */     catch (ExcepcionAS2 e)
/*  192:     */     {
/*  193: 226 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  194: 227 */       e.printStackTrace();
/*  195:     */     }
/*  196: 230 */     return "";
/*  197:     */   }
/*  198:     */   
/*  199:     */   public void totalizar()
/*  200:     */   {
/*  201:     */     try
/*  202:     */     {
/*  203: 235 */       this.servicioFacturaCliente.totalizar(this.facturaCliente);
/*  204: 236 */       cargarCuentaPorCobrar();
/*  205: 237 */       separarImpuestos(this.facturaCliente);
/*  206:     */     }
/*  207:     */     catch (ExcepcionAS2Ventas e)
/*  208:     */     {
/*  209: 241 */       e.printStackTrace();
/*  210:     */     }
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void separarImpuestos(FacturaCliente facturaCliente)
/*  214:     */   {
/*  215: 246 */     BigDecimal totalImpuestosExtranjeros = BigDecimal.ZERO;
/*  216: 247 */     BigDecimal totalImpuestosNacionales = BigDecimal.ZERO;
/*  217: 248 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/*  218: 249 */       if (!dfc.isEliminado()) {
/*  219: 250 */         if ((dfc.getProducto().getImpuestoAviacion().booleanValue()) && (dfc.getProducto().getIndicadorNacional().booleanValue())) {
/*  220: 251 */           totalImpuestosNacionales = totalImpuestosNacionales.add(dfc.getPrecioLinea());
/*  221: 252 */         } else if ((dfc.getProducto().getImpuestoAviacion().booleanValue()) && (!dfc.getProducto().getIndicadorNacional().booleanValue())) {
/*  222: 253 */           totalImpuestosExtranjeros = totalImpuestosExtranjeros.add(dfc.getPrecioLinea());
/*  223:     */         }
/*  224:     */       }
/*  225:     */     }
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void actualizarProductoListenerExtranjero(AjaxBehaviorEvent event)
/*  229:     */   {
/*  230: 264 */     actualizarProductoListenerNacionalExtranjero(event, this.dtDetalleFacturaClienteImpuestoExtranjero);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void actualizarProductoListenerNacional(AjaxBehaviorEvent event)
/*  234:     */   {
/*  235: 268 */     actualizarProductoListenerNacionalExtranjero(event, this.dtDetalleFacturaClienteImpuestoNacional);
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void actualizarProductoListenerTicket(AjaxBehaviorEvent event)
/*  239:     */   {
/*  240: 272 */     actualizarProductoListenerNacionalExtranjero(event, this.dtDetalleFacturaCliente);
/*  241:     */   }
/*  242:     */   
/*  243:     */   public void actualizarProductoListenerNacionalExtranjero(AjaxBehaviorEvent event, DataTable dtDetalleFacturaClienteImpuestoNacionalExtranjero)
/*  244:     */   {
/*  245: 277 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)dtDetalleFacturaClienteImpuestoNacionalExtranjero.getRowData();
/*  246: 278 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  247: 279 */     Producto producto = null;
/*  248:     */     try
/*  249:     */     {
/*  250: 281 */       producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  251: 282 */       actualizarProducto(dfc, producto);
/*  252: 283 */       dfc.setPrecio(producto.getPrecioReferencialVenta());
/*  253: 284 */       dfc.setCantidad(BigDecimal.ONE);
/*  254: 285 */       totalizar();
/*  255:     */     }
/*  256:     */     catch (ExcepcionAS2 e)
/*  257:     */     {
/*  258: 287 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  259: 288 */       dfc.getProducto().setCodigo("");
/*  260: 289 */       dfc.getProducto().setNombre("");
/*  261:     */     }
/*  262:     */   }
/*  263:     */   
/*  264:     */   public List<PrefacturaCliente> autocompletarPrefacturas(String consulta)
/*  265:     */   {
/*  266: 295 */     List<PrefacturaCliente> listaPrefactura = new ArrayList();
/*  267: 297 */     if (getFacturaCliente().getEmpresa() != null)
/*  268:     */     {
/*  269: 298 */       HashMap<String, String> filters = new HashMap();
/*  270: 299 */       filters.put("empresa.idEmpresa", String.valueOf(getFacturaCliente().getEmpresa().getId()));
/*  271: 300 */       filters.put("estado", "=" + Estado.ELABORADO.toString());
/*  272: 301 */       filters.put("numero", "%" + consulta.trim() + "%");
/*  273: 302 */       listaPrefactura = this.servicioPrefacturaCliente.obtenerListaCombo("numero", true, filters);
/*  274:     */     }
/*  275: 306 */     listaPrefactura.removeAll(getFacturaCliente().getListaPrefacturaCliente());
/*  276:     */     
/*  277: 308 */     return listaPrefactura;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void cargarDocumento()
/*  281:     */   {
/*  282: 316 */     if (this.idDespachoCliente != null)
/*  283:     */     {
/*  284: 317 */       limpiar();
/*  285: 318 */       if (getListaDocumentoCliente().size() > 0)
/*  286:     */       {
/*  287: 319 */         for (Documento documento : getListaDocumentoCliente()) {
/*  288: 320 */           if ((documento.isActivo()) && (
/*  289: 321 */             (documento.isPredeterminado()) || (getFacturaCliente().getDocumento() == null))) {
/*  290: 322 */             getFacturaCliente().setDocumento(documento);
/*  291:     */           }
/*  292:     */         }
/*  293: 326 */         actualizarDocumento();
/*  294:     */       }
/*  295: 328 */       FacturaCliente facturaClienteExistente = this.servicioFacturaCliente.buscarPorDespachoCliente(this.idDespachoCliente);
/*  296: 329 */       if (facturaClienteExistente != null)
/*  297:     */       {
/*  298: 330 */         addInfoMessage(getLanguageController().getMensaje("msg_info_ya_existe_factura_despacho") + " " + facturaClienteExistente
/*  299: 331 */           .getNumero());
/*  300:     */       }
/*  301:     */       else
/*  302:     */       {
/*  303: 335 */         DespachoCliente despachoCliente = this.servicioDespachoCliente.cargarDetalleAFacturar(this.idDespachoCliente);
/*  304:     */         
/*  305: 337 */         getFacturaCliente().setEmpresa(despachoCliente.getEmpresa());
/*  306:     */         
/*  307: 339 */         this.listaDespachoClienteAsignados.add(despachoCliente);
/*  308:     */         
/*  309: 341 */         agregarDetalleDespachoAFactura(despachoCliente);
/*  310: 342 */         cargarDirecciones(getFacturaCliente().getEmpresa());
/*  311: 343 */         setEditado(true);
/*  312:     */       }
/*  313: 347 */       this.idDespachoCliente = null;
/*  314:     */     }
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void cargarDetallePedidoListener()
/*  318:     */   {
/*  319: 356 */     for (Iterator localIterator = getFacturaCliente().getListaDetalleFacturaCliente().iterator(); localIterator.hasNext();)
/*  320:     */     {
/*  321: 356 */       ddc = (DetalleFacturaCliente)localIterator.next();
/*  322: 357 */       ddc.setEliminado(true);
/*  323:     */     }
/*  324:     */     DetalleFacturaCliente ddc;
/*  325: 360 */     if (this.facturaCliente.getPedidoCliente() != null)
/*  326:     */     {
/*  327: 362 */       getFacturaCliente().setZona(this.facturaCliente.getPedidoCliente().getZona());
/*  328: 363 */       getFacturaCliente().setCondicionPago(this.facturaCliente.getPedidoCliente().getCondicionPago());
/*  329: 364 */       getFacturaCliente().setAgenteComercial(this.facturaCliente.getPedidoCliente().getAgenteComercial());
/*  330: 365 */       getFacturaCliente().setNumeroCuotas(this.facturaCliente.getPedidoCliente().getNumeroCuotas());
/*  331: 366 */       getFacturaCliente().setCanal(this.facturaCliente.getPedidoCliente().getCanal());
/*  332: 367 */       getFacturaCliente().setDireccionEmpresa(this.facturaCliente.getPedidoCliente().getDireccionEmpresa());
/*  333: 368 */       getFacturaCliente().setDescripcion(this.facturaCliente.getPedidoCliente().getDescripcion());
/*  334: 369 */       getFacturaCliente().setSubempresa(this.facturaCliente.getPedidoCliente().getSubempresa());
/*  335: 370 */       getFacturaCliente().setProyecto(this.facturaCliente.getPedidoCliente().getProyecto());
/*  336:     */       
/*  337:     */ 
/*  338: 373 */       Object listaDPC = this.servicioPedidoCliente.obtenerListaDetallePedidoPorFacturar(this.facturaCliente.getPedidoCliente().getId());
/*  339: 375 */       for (DetallePedidoCliente dpc : (List)listaDPC)
/*  340:     */       {
/*  341: 377 */         DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/*  342: 378 */         dfc.setDetallePedidoCliente(dpc);
/*  343: 379 */         dfc.setFacturaCliente(this.facturaCliente);
/*  344: 380 */         dfc.setUnidadVenta(dpc.getUnidadVenta());
/*  345: 381 */         dfc.setCantidad(dpc.getCantidadPorFacturar());
/*  346: 382 */         dfc.setPrecio(dpc.getPrecio());
/*  347: 383 */         dfc.setDescuento(dpc.getDescuento());
/*  348: 384 */         dfc.setPorcentajeDescuento(dpc.getPorcentajeDescuento());
/*  349: 385 */         dfc.setDescripcion(dpc.getDescripcion());
/*  350: 386 */         getFacturaCliente().getListaDetalleFacturaCliente().add(dfc);
/*  351: 387 */         super.actualizarProducto(dfc, dpc.getProducto());
/*  352:     */       }
/*  353: 390 */       Map<String, String> filtros = new HashMap();
/*  354: 391 */       filtros.put("pedidoCliente.idPedidoCliente", this.facturaCliente.getPedidoCliente().getId() + "");
/*  355: 392 */       List<DespachoCliente> listaDespachoPedido = new ArrayList();
/*  356: 393 */       listaDespachoPedido = this.servicioDespachoCliente.obtenerListaPorPagina(0, 1, null, true, filtros);
/*  357: 394 */       if (!listaDespachoPedido.isEmpty()) {
/*  358: 395 */         this.listaDespachoClienteAsignados.add(listaDespachoPedido.get(0));
/*  359:     */       }
/*  360:     */     }
/*  361: 399 */     cargarSubempresas();
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void cargarDetallePrefacturaListener(SelectEvent event)
/*  365:     */   {
/*  366: 410 */     PrefacturaCliente prefacturaCliente = (PrefacturaCliente)event.getObject();
/*  367: 412 */     if (prefacturaCliente != null)
/*  368:     */     {
/*  369: 414 */       if (!getFacturaCliente().getListaPrefacturaCliente().contains(prefacturaCliente)) {
/*  370: 415 */         getFacturaCliente().getListaPrefacturaCliente().add(prefacturaCliente);
/*  371:     */       }
/*  372: 418 */       if (prefacturaCliente.getEstado().equals(Estado.FACTURADO))
/*  373:     */       {
/*  374: 419 */         addInfoMessage(getLanguageController().getMensaje("msg_info_ya_existe_factura_prefactura"));
/*  375:     */       }
/*  376:     */       else
/*  377:     */       {
/*  378: 422 */         AjustePrefacturaCliente ajustePrefacturaCliente = this.servicioPrefacturaCliente.obtenerAjustePrefacturaActivo(prefacturaCliente.getId());
/*  379:     */         
/*  380: 424 */         getFacturaCliente().setEmpresa(ajustePrefacturaCliente.getPrefacturaCliente().getEmpresa());
/*  381: 425 */         if (!getFacturaCliente().getListaPrefacturaCliente().contains(ajustePrefacturaCliente.getPrefacturaCliente())) {
/*  382: 426 */           getFacturaCliente().getListaPrefacturaCliente().add(ajustePrefacturaCliente.getPrefacturaCliente());
/*  383:     */         }
/*  384: 429 */         getFacturaCliente().setCondicionPago(ajustePrefacturaCliente.getPrefacturaCliente().getCondicionPago());
/*  385: 430 */         getFacturaCliente().setNumeroCuotas(ajustePrefacturaCliente.getPrefacturaCliente().getNumeroCuotas());
/*  386:     */         
/*  387: 432 */         String descripcion = getFacturaCliente().getDescripcion();
/*  388: 433 */         descripcion = (descripcion == null ? "" : new StringBuilder().append(descripcion).append(" ").toString()) + ajustePrefacturaCliente.getPrefacturaCliente().getDescripcion();
/*  389: 434 */         getFacturaCliente().setDescripcion(descripcion);
/*  390:     */         
/*  391: 436 */         getFacturaCliente().setDireccionEmpresa(ajustePrefacturaCliente.getPrefacturaCliente().getDireccionEmpresa());
/*  392: 437 */         getFacturaCliente().setAgenteComercial(ajustePrefacturaCliente.getPrefacturaCliente().getAgenteComercial());
/*  393: 439 */         for (DetalleAjustePrefacturaCliente detalle : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente())
/*  394:     */         {
/*  395: 440 */           DetalleFacturaCliente detalleFactura = new DetalleFacturaCliente();
/*  396: 441 */           detalleFactura.setIdOrganizacion(detalle.getIdOrganizacion());
/*  397: 442 */           detalleFactura.setIdSucursal(detalle.getIdSucursal());
/*  398: 443 */           detalleFactura.setProducto(detalle.getProducto());
/*  399: 444 */           detalleFactura.setDescripcion(detalle.getDescripcion());
/*  400: 445 */           detalleFactura.setCantidad(detalle.getCantidad());
/*  401: 446 */           detalleFactura.setDescuento(detalle.getDescuento());
/*  402: 447 */           detalleFactura.setPorcentajeDescuento(detalle.getPorcentajeDescuento());
/*  403: 448 */           detalleFactura.setPrecio(detalle.getPrecio());
/*  404: 449 */           detalleFactura.setUnidadVenta(detalle.getUnidadVenta());
/*  405: 450 */           detalleFactura.setFacturaCliente(getFacturaCliente());
/*  406: 451 */           detalleFactura.setIndicadorImpuesto(detalle.getProducto().isIndicadorImpuestos());
/*  407:     */           try
/*  408:     */           {
/*  409: 454 */             this.servicioUnidadConversion.cargarListaUnidadConversion(detalleFactura.getProducto());
/*  410:     */           }
/*  411:     */           catch (ExcepcionAS2 e)
/*  412:     */           {
/*  413: 456 */             addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  414:     */           }
/*  415: 459 */           if (detalleFactura.isIndicadorImpuesto()) {
/*  416:     */             try
/*  417:     */             {
/*  418: 462 */               this.servicioFacturaCliente.obtenerImpuestosProductos(detalleFactura.getProducto(), detalleFactura);
/*  419:     */             }
/*  420:     */             catch (ExcepcionAS2Inventario e)
/*  421:     */             {
/*  422: 465 */               addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  423:     */             }
/*  424:     */           }
/*  425: 477 */           getFacturaCliente().getListaDetalleFacturaCliente().add(detalleFactura);
/*  426:     */         }
/*  427: 481 */         totalizar();
/*  428:     */       }
/*  429:     */     }
/*  430:     */   }
/*  431:     */   
/*  432:     */   public String autorizarVenta()
/*  433:     */   {
/*  434:     */     try
/*  435:     */     {
/*  436: 494 */       EntidadUsuario usuarioAutoriza = this.servicioUsuario.obtieneUsuarioAutorizaVentas(getUsuarioAutorizaVenta(), getClaveAutorizaVenta());
/*  437: 495 */       getFacturaCliente().setUsuarioAutorizaVentas(usuarioAutoriza.getNombreUsuario());
/*  438: 496 */       getFacturaCliente().setIndicadorAutorizaVenta(true);
/*  439: 497 */       addInfoMessage(getLanguageController().getMensaje("msg_info_autorizar_venta"));
/*  440:     */     }
/*  441:     */     catch (ExcepcionAS2 e)
/*  442:     */     {
/*  443: 499 */       getFacturaCliente().setUsuarioAutorizaVentas(null);
/*  444: 500 */       getFacturaCliente().setIndicadorAutorizaVenta(false);
/*  445: 501 */       addErrorMessage(getLanguageController().getMensaje("msg_error_autorizar_venta"));
/*  446:     */     }
/*  447: 503 */     return "";
/*  448:     */   }
/*  449:     */   
/*  450:     */   public String seleccionarFacturaClienteExportacion()
/*  451:     */   {
/*  452: 512 */     FacturaCliente facturaClienteSeleccionada = (FacturaCliente)this.dtFacturaCliente.getRowData();
/*  453:     */     try
/*  454:     */     {
/*  455: 514 */       this.listaDistritoAduanero = DatosSRI.getListaDistritoAduanero();
/*  456: 515 */       this.listaRegimen = DatosSRI.getListaRegimen();
/*  457:     */       
/*  458:     */ 
/*  459: 518 */       setFacturaCliente(facturaClienteSeleccionada);
/*  460:     */     }
/*  461:     */     catch (Exception e)
/*  462:     */     {
/*  463: 520 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  464:     */     }
/*  465: 522 */     return "";
/*  466:     */   }
/*  467:     */   
/*  468:     */   public String crearCliente()
/*  469:     */   {
/*  470:     */     try
/*  471:     */     {
/*  472: 533 */       Empresa empresa = this.servicioEmpresa.crearClienteConDatosBasicos(this.identificacionCliente, this.tipoIdentificacionCliente, this.nombreCliente, this.direccionCliente, this.telefonoCliente, 
/*  473: 534 */         AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), this.ciudad, this.agenteComercial, this.email);
/*  474:     */       
/*  475:     */ 
/*  476: 537 */       this.identificacionCliente = null;
/*  477:     */       
/*  478: 539 */       this.nombreCliente = null;
/*  479: 540 */       this.direccionCliente = null;
/*  480: 541 */       this.telefonoCliente = null;
/*  481: 542 */       this.agenteComercial = null;
/*  482: 543 */       this.email = null;
/*  483:     */       
/*  484: 545 */       actualizarCliente(empresa);
/*  485: 546 */       setRenderClienteNuevo(false);
/*  486: 547 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  487:     */     }
/*  488:     */     catch (ExcepcionAS2 e)
/*  489:     */     {
/*  490: 549 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  491: 550 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  492:     */     }
/*  493:     */     catch (Exception ex)
/*  494:     */     {
/*  495: 552 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  496: 553 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  497:     */     }
/*  498: 556 */     return "";
/*  499:     */   }
/*  500:     */   
/*  501:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  502:     */   {
/*  503: 560 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  504:     */   }
/*  505:     */   
/*  506:     */   public Integer getIdFacturaCliente()
/*  507:     */   {
/*  508: 567 */     return this.idFacturaCliente;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void setIdFacturaCliente(Integer idFacturaCliente)
/*  512:     */   {
/*  513: 575 */     this.idFacturaCliente = idFacturaCliente;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public Integer getIdDespachoCliente()
/*  517:     */   {
/*  518: 582 */     return this.idDespachoCliente;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public void setIdDespachoCliente(Integer idDespachoCliente)
/*  522:     */   {
/*  523: 590 */     this.idDespachoCliente = idDespachoCliente;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public boolean isMostrarCheckExportar()
/*  527:     */   {
/*  528: 599 */     return this.mostrarCheckExportar;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void setMostrarCheckExportar(boolean mostrarCheckExportar)
/*  532:     */   {
/*  533: 609 */     this.mostrarCheckExportar = mostrarCheckExportar;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public PrefacturaCliente getPrefacturaCliente()
/*  537:     */   {
/*  538: 618 */     return this.prefacturaCliente;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public void setPrefacturaCliente(PrefacturaCliente prefacturaCliente)
/*  542:     */   {
/*  543: 628 */     this.prefacturaCliente = prefacturaCliente;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public List<ErrorCarga> getErrores()
/*  547:     */   {
/*  548: 632 */     return this.errores;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public void setErrores(List<ErrorCarga> errores)
/*  552:     */   {
/*  553: 636 */     this.errores = errores;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public String cargarFacturaClienteElectronica(FileUploadEvent event)
/*  557:     */   {
/*  558: 645 */     List<FacturaCliente> lisFacturaCliente = new ArrayList();
/*  559:     */     try
/*  560:     */     {
/*  561: 648 */       String fileName = "migracion_factura_cliente" + event.getFile().getFileName();
/*  562: 649 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  563: 650 */       lisFacturaCliente = this.servicioFacturaCliente.migracionFacturaCliente(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/*  564: 651 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  565:     */       
/*  566: 653 */       this.servicioFacturaCliente.guardarFacturaClienteRevisadas(lisFacturaCliente);
/*  567:     */     }
/*  568:     */     catch (AS2Exception e)
/*  569:     */     {
/*  570: 656 */       e.printStackTrace();
/*  571: 657 */       List<String> listaMensajes = e.getCodigoMensajes();
/*  572: 658 */       int i = 0;
/*  573: 659 */       for (String a : listaMensajes)
/*  574:     */       {
/*  575: 660 */         i = a.indexOf("*");
/*  576: 661 */         a.substring(0, i + 1);
/*  577: 662 */         ErrorCarga ec = new ErrorCarga();
/*  578: 663 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/*  579: 664 */         this.errores.add(ec);
/*  580:     */       }
/*  581: 666 */       for (String a : e.getMensajes())
/*  582:     */       {
/*  583: 667 */         ErrorCarga ec = new ErrorCarga();
/*  584: 668 */         ec.setError(a);
/*  585: 669 */         this.errores.add(ec);
/*  586:     */       }
/*  587:     */     }
/*  588:     */     catch (ExcepcionAS2Financiero e)
/*  589:     */     {
/*  590: 673 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  591: 674 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  592:     */     }
/*  593:     */     catch (ExcepcionAS2Compras e)
/*  594:     */     {
/*  595: 677 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  596: 678 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  597:     */     }
/*  598:     */     catch (ExcepcionAS2 e)
/*  599:     */     {
/*  600: 681 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  601: 682 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  602:     */     }
/*  603:     */     catch (Exception e)
/*  604:     */     {
/*  605: 685 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  606: 686 */       e.printStackTrace();
/*  607:     */     }
/*  608: 688 */     return null;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public String getIdentificacionCliente()
/*  612:     */   {
/*  613: 692 */     return this.identificacionCliente;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void setIdentificacionCliente(String identificacionCliente)
/*  617:     */   {
/*  618: 696 */     this.identificacionCliente = identificacionCliente;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public TipoIdentificacion getTipoIdentificacionCliente()
/*  622:     */   {
/*  623: 700 */     return this.tipoIdentificacionCliente;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public void setTipoIdentificacionCliente(TipoIdentificacion tipoIdentificacionCliente)
/*  627:     */   {
/*  628: 704 */     this.tipoIdentificacionCliente = tipoIdentificacionCliente;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/*  632:     */   {
/*  633: 708 */     if (this.listaTipoIdentificacion == null) {
/*  634: 709 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/*  635:     */     }
/*  636: 711 */     return this.listaTipoIdentificacion;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/*  640:     */   {
/*  641: 715 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public List<EntidadUsuario> getListaAgenteComercial()
/*  645:     */   {
/*  646: 719 */     return this.listaAgenteComercial;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void setListaAgenteComercial(List<EntidadUsuario> listaAgenteComercial)
/*  650:     */   {
/*  651: 723 */     this.listaAgenteComercial = listaAgenteComercial;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public String getNombreCliente()
/*  655:     */   {
/*  656: 727 */     return this.nombreCliente;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setNombreCliente(String nombreCliente)
/*  660:     */   {
/*  661: 731 */     this.nombreCliente = nombreCliente;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public String getDireccionCliente()
/*  665:     */   {
/*  666: 735 */     return this.direccionCliente;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void setDireccionCliente(String direccionCliente)
/*  670:     */   {
/*  671: 739 */     this.direccionCliente = direccionCliente;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public String getTelefonoCliente()
/*  675:     */   {
/*  676: 743 */     return this.telefonoCliente;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public void setTelefonoCliente(String telefonoCliente)
/*  680:     */   {
/*  681: 747 */     this.telefonoCliente = telefonoCliente;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public Ciudad getCiudad()
/*  685:     */   {
/*  686: 751 */     return this.ciudad;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void setCiudad(Ciudad ciudad)
/*  690:     */   {
/*  691: 755 */     this.ciudad = ciudad;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public String getEmail()
/*  695:     */   {
/*  696: 759 */     return this.email;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void setEmail(String email)
/*  700:     */   {
/*  701: 763 */     this.email = email;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public boolean isRenderClienteNuevo()
/*  705:     */   {
/*  706: 767 */     return this.renderClienteNuevo;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setRenderClienteNuevo(boolean renderClienteNuevo)
/*  710:     */   {
/*  711: 771 */     this.renderClienteNuevo = renderClienteNuevo;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public EntidadUsuario getAgenteComercial()
/*  715:     */   {
/*  716: 775 */     return this.agenteComercial;
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/*  720:     */   {
/*  721: 779 */     this.agenteComercial = agenteComercial;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public List<DespachoCliente> getListaDespachoClientePendientes()
/*  725:     */   {
/*  726: 783 */     return this.listaDespachoClientePendientes;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public void setListaDespachoClientePendientes(List<DespachoCliente> listaDespachoClientePendientes)
/*  730:     */   {
/*  731: 787 */     this.listaDespachoClientePendientes = listaDespachoClientePendientes;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public List<DespachoCliente> getListaDespachoClienteAsignados()
/*  735:     */   {
/*  736: 791 */     return this.listaDespachoClienteAsignados;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public void setListaDespachoClienteAsignados(List<DespachoCliente> listaDespachoClienteAsignados)
/*  740:     */   {
/*  741: 795 */     this.listaDespachoClienteAsignados = listaDespachoClienteAsignados;
/*  742:     */   }
/*  743:     */   
/*  744:     */   public List<DespachoCliente> getListaDespachoClienteSeleccionados()
/*  745:     */   {
/*  746: 799 */     return this.listaDespachoClienteSeleccionados;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public void setListaDespachoClienteSeleccionados(List<DespachoCliente> listaDespachoClienteSeleccionados)
/*  750:     */   {
/*  751: 803 */     this.listaDespachoClienteSeleccionados = listaDespachoClienteSeleccionados;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public String cargarDespachosPendientes()
/*  755:     */   {
/*  756: 807 */     if (this.facturaCliente.getEmpresa() == null)
/*  757:     */     {
/*  758: 808 */       this.listaDespachoClientePendientes = new ArrayList();
/*  759: 809 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_cliente"));
/*  760:     */     }
/*  761:     */     else
/*  762:     */     {
/*  763: 811 */       this.listaDespachoClientePendientes = this.servicioDespachoCliente.buscarDespachosNoFacturadosPorCliente(Integer.valueOf(this.facturaCliente.getEmpresa().getId()));
/*  764: 812 */       for (DespachoCliente despacho : this.listaDespachoClienteAsignados) {
/*  765: 813 */         this.listaDespachoClientePendientes.remove(despacho);
/*  766:     */       }
/*  767:     */     }
/*  768: 816 */     return "";
/*  769:     */   }
/*  770:     */   
/*  771:     */   public String adicionarDespachos()
/*  772:     */   {
/*  773: 820 */     for (DespachoCliente despacho : this.listaDespachoClienteSeleccionados)
/*  774:     */     {
/*  775: 821 */       DespachoCliente despachoCliente = this.servicioDespachoCliente.cargarDetalleAFacturar(Integer.valueOf(despacho.getIdDespachoCliente()));
/*  776: 822 */       agregarDetalleDespachoAFactura(despachoCliente);
/*  777: 823 */       this.listaDespachoClienteAsignados.add(despachoCliente);
/*  778:     */     }
/*  779: 825 */     return "";
/*  780:     */   }
/*  781:     */   
/*  782:     */   public void agregarDetalleDespachoAFactura(DespachoCliente despachoCliente)
/*  783:     */   {
/*  784: 829 */     getFacturaCliente().setDespachoCliente(despachoCliente);
/*  785: 830 */     getFacturaCliente().setProyecto(despachoCliente.getProyecto());
/*  786:     */     Set<EntidadUsuario> listaAux;
/*  787: 832 */     if (despachoCliente.getPedidoCliente() != null)
/*  788:     */     {
/*  789: 833 */       getFacturaCliente().setPedidoCliente(despachoCliente.getPedidoCliente());
/*  790: 834 */       getFacturaCliente().setZona(despachoCliente.getPedidoCliente().getZona());
/*  791: 835 */       getFacturaCliente().setCanal(despachoCliente.getPedidoCliente().getCanal());
/*  792: 836 */       getFacturaCliente().setCondicionPago(despachoCliente.getPedidoCliente().getCondicionPago());
/*  793: 837 */       getFacturaCliente().setNumeroCuotas(despachoCliente.getPedidoCliente().getNumeroCuotas());
/*  794: 838 */       getFacturaCliente().setDescripcion(despachoCliente.getPedidoCliente().getDescripcion());
/*  795: 839 */       getFacturaCliente().setDireccionEmpresa(despachoCliente.getPedidoCliente().getDireccionEmpresa());
/*  796: 840 */       getFacturaCliente().setAgenteComercial(despachoCliente.getPedidoCliente().getAgenteComercial());
/*  797:     */     }
/*  798:     */     else
/*  799:     */     {
/*  800: 842 */       getFacturaCliente().setCondicionPago(despachoCliente.getEmpresa().getCliente().getCondicionPago());
/*  801: 843 */       getFacturaCliente().setNumeroCuotas(despachoCliente.getEmpresa().getCliente().getNumeroCuotas());
/*  802: 844 */       if (despachoCliente.getEmpresa().getCliente().getAgenteComercial() != null)
/*  803:     */       {
/*  804: 845 */         listaAux = new HashSet();
/*  805: 846 */         Set<EntidadUsuario> listaAux2 = new HashSet();
/*  806: 847 */         getFacturaCliente().setAgenteComercial(despachoCliente.getEmpresa().getCliente().getAgenteComercial());
/*  807: 849 */         for (EntidadUsuario entidadUsuario : getListaAgenteComercialCombo()) {
/*  808: 850 */           if (!entidadUsuario.equals(despachoCliente.getEmpresa().getCliente().getAgenteComercial())) {
/*  809: 851 */             listaAux.add(despachoCliente.getEmpresa().getCliente().getAgenteComercial());
/*  810:     */           }
/*  811:     */         }
/*  812: 854 */         if (listaAux.size() > 0) {
/*  813: 855 */           getListaAgenteComercialCombo().addAll(listaAux);
/*  814:     */         }
/*  815: 858 */         listaAux2.addAll(getListaAgenteComercialCombo());
/*  816: 859 */         getListaAgenteComercialCombo().clear();
/*  817: 860 */         getListaAgenteComercialCombo().addAll(listaAux2);
/*  818:     */       }
/*  819:     */     }
/*  820: 864 */     for (DetalleDespachoCliente ddc : despachoCliente.getListaDetalleDespachoCliente()) {
/*  821: 865 */       if ((ddc.getDetalleFacturaCliente() == null) || (ddc.getDetalleFacturaCliente().getFacturaCliente().getEstado().equals(Estado.ANULADO)))
/*  822:     */       {
/*  823: 866 */         DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/*  824: 867 */         dfc.setDetalleDespachoCliente(ddc);
/*  825: 868 */         getFacturaCliente().getListaDetalleFacturaCliente().add(dfc);
/*  826:     */         
/*  827: 870 */         dfc.setCantidad(ddc.getCantidad());
/*  828: 871 */         dfc.setUnidadVenta(ddc.getUnidadVenta());
/*  829: 872 */         dfc.setPeso(ddc.getPeso());
/*  830: 873 */         dfc.setDescripcion(ddc.getDescripcion());
/*  831: 874 */         dfc.setIndicadorManejoPeso(ddc.isIndicadorManejoPeso());
/*  832: 876 */         if (ddc.getDetallePedidoCliente() != null)
/*  833:     */         {
/*  834: 877 */           dfc.setPrecio(ddc.getDetallePedidoCliente().getPrecio());
/*  835: 878 */           dfc.setDescuento(ddc.getDetallePedidoCliente().getDescuento());
/*  836:     */         }
/*  837: 881 */         actualizarProducto(dfc, ddc.getProducto());
/*  838:     */       }
/*  839:     */     }
/*  840:     */   }
/*  841:     */   
/*  842:     */   public String eliminarDetalleExtranjero()
/*  843:     */   {
/*  844: 887 */     return eliminarDetalleNacionalExtranjero(this.dtDetalleFacturaClienteImpuestoExtranjero);
/*  845:     */   }
/*  846:     */   
/*  847:     */   public String eliminarDetalleNacional()
/*  848:     */   {
/*  849: 892 */     return eliminarDetalleNacionalExtranjero(this.dtDetalleFacturaClienteImpuestoNacional);
/*  850:     */   }
/*  851:     */   
/*  852:     */   public String eliminarDetalleTicket()
/*  853:     */   {
/*  854: 896 */     return eliminarDetalleNacionalExtranjero(this.dtDetalleFacturaCliente);
/*  855:     */   }
/*  856:     */   
/*  857:     */   public String eliminarDetalleNacionalExtranjero(DataTable dtDetalleFacturaClienteImpuestoNacionalExtranjero)
/*  858:     */   {
/*  859: 901 */     DetalleFacturaCliente detfc = (DetalleFacturaCliente)dtDetalleFacturaClienteImpuestoNacionalExtranjero.getRowData();
/*  860:     */     
/*  861: 903 */     List<DetalleFacturaCliente> listaDetalleFacturaClienteEliminar = new ArrayList();
/*  862: 904 */     listaDetalleFacturaClienteEliminar.add(detfc);
/*  863: 906 */     for (DetalleFacturaCliente dfc : listaDetalleFacturaClienteEliminar)
/*  864:     */     {
/*  865: 907 */       if ((dfc.getDetalleDespachoCliente() != null) && (dfc.getDetalleDespachoCliente().getId() == 0)) {
/*  866: 908 */         dfc.getDetalleDespachoCliente().setEliminado(true);
/*  867:     */       }
/*  868: 911 */       for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/*  869: 912 */         ipfc.setEliminado(true);
/*  870:     */       }
/*  871: 915 */       dfc.setEliminado(true);
/*  872: 916 */       dfc.setCantidad(BigDecimal.ZERO);
/*  873:     */     }
/*  874: 918 */     separarDetalleFactura();
/*  875:     */     
/*  876: 920 */     totalizar();
/*  877:     */     
/*  878: 922 */     return "";
/*  879:     */   }
/*  880:     */   
/*  881:     */   public List<Documento> getListaDocumentoCliente()
/*  882:     */   {
/*  883: 927 */     if (this.listaDocumentoCliente == null) {
/*  884:     */       try
/*  885:     */       {
/*  886: 929 */         this.listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBaseOrganizacionAerolinea(DocumentoBase.FACTURA_CLIENTE, 
/*  887: 930 */           AppUtil.getOrganizacion().getIdOrganizacion());
/*  888:     */         
/*  889: 932 */         FuncionesUtiles.ordenaLista(this.listaDocumentoCliente, "fechaCreacion");
/*  890:     */       }
/*  891:     */       catch (ExcepcionAS2 e)
/*  892:     */       {
/*  893: 934 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  894:     */       }
/*  895:     */     }
/*  896: 937 */     return this.listaDocumentoCliente;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public String agregarDetalleFacturaImpuestoExtranjero()
/*  900:     */   {
/*  901: 944 */     return "";
/*  902:     */   }
/*  903:     */   
/*  904:     */   public String agregarDetalleFacturaImpuestoNacional()
/*  905:     */   {
/*  906: 951 */     return "";
/*  907:     */   }
/*  908:     */   
/*  909:     */   public String agregarDetalleFacturaTicket()
/*  910:     */   {
/*  911: 955 */     super.agregarDetalleFactura();
/*  912: 956 */     separarDetalleFactura();
/*  913: 957 */     return "";
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void cambiarImpuestoTicket() {}
/*  917:     */   
/*  918:     */   public void cambiarImpuestoExtranjero() {}
/*  919:     */   
/*  920:     */   public void cambiarImpuestoNacional() {}
/*  921:     */   
/*  922:     */   public void limpiarFiltrosListaProducto() {}
/*  923:     */   
/*  924:     */   public void cargarProducto(Producto producto)
/*  925:     */   {
/*  926: 989 */     getListaProductoBean().setProducto(producto);
/*  927: 990 */     getListaProductoBean().setSaldoProductoLote(null);
/*  928: 991 */     cargarProducto();
/*  929:     */   }
/*  930:     */   
/*  931:     */   public DetalleFacturaCliente cargarProducto()
/*  932:     */   {
/*  933: 996 */     DetalleFacturaCliente detalleFacturaCliente = null;
/*  934: 997 */     if (this.facturaCliente.getEmpresa() != null)
/*  935:     */     {
/*  936: 999 */       Producto producto = getListaProductoBean().getProducto();
/*  937:1001 */       if (producto != null)
/*  938:     */       {
/*  939:     */         try
/*  940:     */         {
/*  941:1003 */           this.servicioUnidadConversion.cargarListaUnidadConversion(producto);
/*  942:     */         }
/*  943:     */         catch (ExcepcionAS2 e)
/*  944:     */         {
/*  945:1005 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  946:     */         }
/*  947:1007 */         detalleFacturaCliente = new DetalleFacturaCliente();
/*  948:1008 */         if ((!producto.getImpuestoAviacion().booleanValue()) || (producto.getIndicadorNacional().booleanValue())) {
/*  949:1010 */           if ((!producto.getImpuestoAviacion().booleanValue()) || (!producto.getIndicadorNacional().booleanValue())) {}
/*  950:     */         }
/*  951:1013 */         this.facturaCliente.getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/*  952:     */         
/*  953:1015 */         actualizarProducto(detalleFacturaCliente, producto);
/*  954:     */         
/*  955:1017 */         detalleFacturaCliente.setCantidad(BigDecimal.ONE);
/*  956:     */         
/*  957:1019 */         detalleFacturaCliente.setPrecio(producto.getPrecioReferencialVenta());
/*  958:     */         
/*  959:1021 */         totalizar();
/*  960:1022 */         separarDetalleFactura();
/*  961:     */       }
/*  962:     */     }
/*  963:     */     else
/*  964:     */     {
/*  965:1026 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  966:1027 */       getListaProductoBean().setProducto(null);
/*  967:     */     }
/*  968:1030 */     getListaProductoBean().setProducto(null);
/*  969:1031 */     return detalleFacturaCliente;
/*  970:     */   }
/*  971:     */   
/*  972:     */   public void separarDetalleFactura()
/*  973:     */   {
/*  974:1037 */     this.listaDetalleFacturaImpuestoExtranjero = new ArrayList();
/*  975:1038 */     this.listaDetalleFacturaTicket = new ArrayList();
/*  976:1039 */     this.listaDetalleFacturaImpuestoNacional = new ArrayList();
/*  977:1041 */     for (DetalleFacturaCliente detalleFacturaCliente : this.facturaCliente.getListaDetalleFacturaCliente()) {
/*  978:1042 */       if (!detalleFacturaCliente.isEliminado()) {
/*  979:1050 */         getListaDetalleFacturaCliente().add(detalleFacturaCliente);
/*  980:     */       }
/*  981:     */     }
/*  982:     */   }
/*  983:     */   
/*  984:     */   public DataTable getDtDetalleFacturaClienteImpuestoExtranjero()
/*  985:     */   {
/*  986:1058 */     return this.dtDetalleFacturaClienteImpuestoExtranjero;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public void setDtDetalleFacturaClienteImpuestoExtranjero(DataTable dtDetalleFacturaClienteImpuestoExtranjero)
/*  990:     */   {
/*  991:1062 */     this.dtDetalleFacturaClienteImpuestoExtranjero = dtDetalleFacturaClienteImpuestoExtranjero;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public DataTable getDtDetalleFacturaClienteImpuestoNacional()
/*  995:     */   {
/*  996:1066 */     return this.dtDetalleFacturaClienteImpuestoNacional;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public void setDtDetalleFacturaClienteImpuestoNacional(DataTable dtDetalleFacturaClienteImpuestoNacional)
/* 1000:     */   {
/* 1001:1070 */     this.dtDetalleFacturaClienteImpuestoNacional = dtDetalleFacturaClienteImpuestoNacional;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaImpuestoExtranjero()
/* 1005:     */   {
/* 1006:1074 */     return this.listaDetalleFacturaImpuestoExtranjero == null ? (this.listaDetalleFacturaImpuestoExtranjero = new ArrayList()) : this.listaDetalleFacturaImpuestoExtranjero;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public void setListaDetalleFacturaImpuestoExtranjero(List<DetalleFacturaCliente> listaDetalleFacturaImpuestoExtranjero)
/* 1010:     */   {
/* 1011:1079 */     this.listaDetalleFacturaImpuestoExtranjero = listaDetalleFacturaImpuestoExtranjero;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaImpuestoNacional()
/* 1015:     */   {
/* 1016:1083 */     return this.listaDetalleFacturaImpuestoNacional == null ? (this.listaDetalleFacturaImpuestoNacional = new ArrayList()) : this.listaDetalleFacturaImpuestoNacional;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public void setListaDetalleFacturaImpuestoNacional(List<DetalleFacturaCliente> listaDetalleFacturaImpuestoNacional)
/* 1020:     */   {
/* 1021:1088 */     this.listaDetalleFacturaImpuestoNacional = listaDetalleFacturaImpuestoNacional;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public boolean isAgregarListaDetalleImpuestoExtranjero()
/* 1025:     */   {
/* 1026:1092 */     return this.agregarListaDetalleImpuestoExtranjero;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public void setAgregarListaDetalleImpuestoExtranjero(boolean agregarListaDetalleImpuestoExtranjero)
/* 1030:     */   {
/* 1031:1096 */     this.agregarListaDetalleImpuestoExtranjero = agregarListaDetalleImpuestoExtranjero;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaTicket()
/* 1035:     */   {
/* 1036:1100 */     return this.listaDetalleFacturaTicket;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public void setListaDetalleFacturaTicket(List<DetalleFacturaCliente> listaDetalleFacturaTicket)
/* 1040:     */   {
/* 1041:1104 */     this.listaDetalleFacturaTicket = listaDetalleFacturaTicket;
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public boolean isNacional()
/* 1045:     */   {
/* 1046:1108 */     return this.nacional;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public void setNacional(boolean nacional)
/* 1050:     */   {
/* 1051:1112 */     this.nacional = nacional;
/* 1052:     */   }
/* 1053:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.TicketBean
 * JD-Core Version:    0.7.0.1
 */