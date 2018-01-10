/*    1:     */ package com.asinfo.as2.inventario.procesos.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    6:     */ import com.asinfo.as2.controller.LanguageController;
/*    7:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   10:     */ import com.asinfo.as2.entities.Bodega;
/*   11:     */ import com.asinfo.as2.entities.CargaPreviaTransportista;
/*   12:     */ import com.asinfo.as2.entities.Chofer;
/*   13:     */ import com.asinfo.as2.entities.Ciudad;
/*   14:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   15:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   16:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   17:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   18:     */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   19:     */ import com.asinfo.as2.entities.DetalleRegistroPeso;
/*   20:     */ import com.asinfo.as2.entities.DetalleRegistroPesoLote;
/*   21:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   22:     */ import com.asinfo.as2.entities.Dispositivo;
/*   23:     */ import com.asinfo.as2.entities.Documento;
/*   24:     */ import com.asinfo.as2.entities.Empresa;
/*   25:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   26:     */ import com.asinfo.as2.entities.GuiaRemision;
/*   27:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   28:     */ import com.asinfo.as2.entities.Lote;
/*   29:     */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   30:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   31:     */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   32:     */ import com.asinfo.as2.entities.Organizacion;
/*   33:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   34:     */ import com.asinfo.as2.entities.PedidoProveedor;
/*   35:     */ import com.asinfo.as2.entities.Producto;
/*   36:     */ import com.asinfo.as2.entities.RegistroPeso;
/*   37:     */ import com.asinfo.as2.entities.Ruta;
/*   38:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   39:     */ import com.asinfo.as2.entities.Sucursal;
/*   40:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   41:     */ import com.asinfo.as2.entities.TipoVehiculo;
/*   42:     */ import com.asinfo.as2.entities.Transportista;
/*   43:     */ import com.asinfo.as2.entities.Unidad;
/*   44:     */ import com.asinfo.as2.entities.Vehiculo;
/*   45:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   46:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   47:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   48:     */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*   49:     */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*   50:     */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*   51:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   52:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   53:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   54:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioChofer;
/*   55:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   56:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*   57:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   58:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoVehiculo;
/*   59:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*   60:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo;
/*   61:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   62:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*   63:     */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*   64:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*   65:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*   66:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   67:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   68:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   69:     */ import com.asinfo.as2.util.AppUtil;
/*   70:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   71:     */ import com.asinfo.as2.utils.JsfUtil;
/*   72:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   73:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   74:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*   75:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   76:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   77:     */ import com.asinfo.validaciones.identificacion.ValidarIdentificacion;
/*   78:     */ import java.io.PrintStream;
/*   79:     */ import java.math.BigDecimal;
/*   80:     */ import java.math.RoundingMode;
/*   81:     */ import java.text.SimpleDateFormat;
/*   82:     */ import java.util.ArrayList;
/*   83:     */ import java.util.Date;
/*   84:     */ import java.util.HashMap;
/*   85:     */ import java.util.Iterator;
/*   86:     */ import java.util.List;
/*   87:     */ import java.util.Map;
/*   88:     */ import javax.annotation.PostConstruct;
/*   89:     */ import javax.ejb.EJB;
/*   90:     */ import javax.faces.bean.ManagedBean;
/*   91:     */ import javax.faces.bean.ViewScoped;
/*   92:     */ import javax.faces.model.SelectItem;
/*   93:     */ import org.apache.log4j.Logger;
/*   94:     */ import org.primefaces.component.datatable.DataTable;
/*   95:     */ import org.primefaces.context.RequestContext;
/*   96:     */ import org.primefaces.event.SelectEvent;
/*   97:     */ import org.primefaces.model.LazyDataModel;
/*   98:     */ import org.primefaces.model.SortOrder;
/*   99:     */ 
/*  100:     */ @ManagedBean
/*  101:     */ @ViewScoped
/*  102:     */ public class RegistroPesoBean
/*  103:     */   extends PageControllerAS2
/*  104:     */ {
/*  105:     */   private static final long serialVersionUID = -8204372454439685260L;
/*  106:     */   @EJB
/*  107:     */   private transient ServicioGenerico<CargaPreviaTransportista> servicioCargaPreviaTransportista;
/*  108:     */   @EJB
/*  109:     */   private transient ServicioRegistroPeso servicioRegistroPeso;
/*  110:     */   @EJB
/*  111:     */   private transient ServicioDocumento servicioDocumento;
/*  112:     */   @EJB
/*  113:     */   private transient ServicioTransportista servicioTransportista;
/*  114:     */   @EJB
/*  115:     */   private transient ServicioChofer servicioChofer;
/*  116:     */   @EJB
/*  117:     */   private transient ServicioVehiculo servicioVehiculo;
/*  118:     */   @EJB
/*  119:     */   private transient ServicioEmpresa servicioEmpresa;
/*  120:     */   @EJB
/*  121:     */   private transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  122:     */   @EJB
/*  123:     */   private transient ServicioPedidoCliente servicioPedidoCliente;
/*  124:     */   @EJB
/*  125:     */   private transient ServicioBodega servicioBodega;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioGenerico<Dispositivo> servicioDispositivo;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioMovimientoInventario servicioMovimientoInventario;
/*  132:     */   @EJB
/*  133:     */   private transient ServicioLote servicioLote;
/*  134:     */   @EJB
/*  135:     */   private transient ServicioProducto servicioProducto;
/*  136:     */   @EJB
/*  137:     */   private transient ServicioGenerico<Ruta> servicioRuta;
/*  138:     */   @EJB
/*  139:     */   private transient ServicioSucursal servicioSucursal;
/*  140:     */   @EJB
/*  141:     */   private transient ServicioTipoVehiculo servicioTipoVehiculo;
/*  142:     */   @EJB
/*  143:     */   private transient ServicioUsuario servicioUsuario;
/*  144:     */   @EJB
/*  145:     */   private transient ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  146:     */   @EJB
/*  147:     */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  148:     */   @EJB
/*  149:     */   private transient ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  150:     */   @EJB
/*  151:     */   private transient ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  152:     */   @EJB
/*  153:     */   private transient ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  154:     */   private Date fechaDesde;
/*  155:     */   private Date fechaHasta;
/*  156:     */   private Sucursal sucursal;
/*  157:     */   private Chofer chofer;
/*  158:     */   private Vehiculo vehiculo;
/*  159:     */   private Transportista transportista;
/*  160:     */   private CargaPreviaTransportista cargaPrevia;
/*  161:     */   private RegistroPeso registroPeso;
/*  162:     */   private PedidoProveedor pedidoProveedor;
/*  163:     */   private PedidoCliente pedidoCliente;
/*  164:     */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  165:     */   private FacturaCliente facturaCliente;
/*  166:     */   private MovimientoInventario transferenciaBodega;
/*  167:     */   private MovimientoInventario ajusteInventarioIngreso;
/*  168:     */   private MovimientoInventario ajusteInventarioEgreso;
/*  169:     */   private DataTable dtRegistroPeso;
/*  170:     */   private DataTable dtDetallePedidoProveedor;
/*  171:     */   private DataTable dtDetallePedidoCliente;
/*  172:     */   private DataTable dtDetalleTransferenciaBodega;
/*  173:     */   private DataTable dtDetalleRegistroPeso;
/*  174:     */   private DataTable dtDetalleOrdenSalidaMaterial;
/*  175:     */   private DataTable dtDetalleFacturaCliente;
/*  176:     */   private LazyDataModel<RegistroPeso> listaRegistroPeso;
/*  177:     */   private List<Documento> listaDocumento;
/*  178:     */   private List<TipoRegistroPeso> listaTipoRegistroPeso;
/*  179:     */   private List<Chofer> listaChoferCombo;
/*  180:     */   private List<Vehiculo> listaVehiculoCombo;
/*  181:     */   private List<TipoVehiculo> listaTipoVehiculoCombo;
/*  182:     */   private List<Documento> listaDocumentosAjusteIngreso;
/*  183:     */   private List<Documento> listaDocumentosAjusteEgreso;
/*  184:     */   private List<Dispositivo> listaDispositivo;
/*  185:     */   private List<Bodega> listaBodga;
/*  186:     */   private List<Ruta> listaRuta;
/*  187:     */   private SelectItem[] listaTipoRegistroPesoItem;
/*  188:     */   private SelectItem[] listaEstadoRegistroPesoItem;
/*  189:     */   private List<Transportista> listaTransportistaCombo;
/*  190: 211 */   private List<PedidoProveedor> listaPedidoProveedor = new ArrayList();
/*  191: 212 */   private List<PedidoCliente> listaPedidoCliente = new ArrayList();
/*  192: 213 */   private List<MovimientoInventario> listaTransferenciaBodega = new ArrayList();
/*  193:     */   private List<DetallePedidoProveedor> listaDetallePedidoProveedor;
/*  194:     */   private List<DetallePedidoCliente> listaDetallePedidoCliente;
/*  195:     */   private List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterial;
/*  196:     */   private List<DetalleFacturaCliente> listaDetalleFacturaCliente;
/*  197:     */   private List<DetalleMovimientoInventario> listaDetalleTransferenciaBodega;
/*  198:     */   private List<DetallePedidoCliente> listaDetallePedidoClienteSeleccionados;
/*  199:     */   private List<DetalleFacturaCliente> listaDetalleFacturaClienteSeleccionados;
/*  200:     */   private List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterialSeleccionados;
/*  201:     */   private DireccionEmpresa direccionEmpresaSeleccionada;
/*  202: 227 */   private Map<String, BigDecimal> mapaSaldoComprometidoAnterior = new HashMap();
/*  203: 228 */   private Map<Integer, String> mapaUnidades = new HashMap();
/*  204:     */   private List<String> listaCargaPrevia;
/*  205:     */   private String mensajeConfirmacion;
/*  206: 233 */   private BigDecimal pesoActual = BigDecimal.ZERO;
/*  207: 234 */   private Integer intervaloRefrescarPesoBalanza = null;
/*  208:     */   private boolean mostradoDialogoAjusteInventario;
/*  209:     */   private boolean indicadorEditarParcial;
/*  210:     */   private Integer idRegistroPeso;
/*  211:     */   
/*  212:     */   @PostConstruct
/*  213:     */   public void init()
/*  214:     */   {
/*  215: 243 */     this.listaRegistroPeso = new LazyDataModel()
/*  216:     */     {
/*  217:     */       private static final long serialVersionUID = 1L;
/*  218:     */       
/*  219:     */       public List<RegistroPeso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  220:     */       {
/*  221: 250 */         filters = RegistroPesoBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), false);
/*  222:     */         
/*  223: 252 */         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  224: 253 */         List<RegistroPeso> lista = new ArrayList();
/*  225: 254 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  226: 256 */         if (RegistroPesoBean.this.idRegistroPeso != null) {
/*  227: 257 */           filters.put("idRegistroPeso", RegistroPesoBean.this.idRegistroPeso.toString());
/*  228:     */         } else {
/*  229: 259 */           filters.put("fecha", OperacionEnum.BETWEEN.name() + sdf.format(RegistroPesoBean.this.fechaDesde) + "~" + sdf.format(RegistroPesoBean.this.fechaHasta));
/*  230:     */         }
/*  231: 262 */         RegistroPesoBean.this.agregarFiltroOrganizacion(filters);
/*  232:     */         
/*  233: 264 */         lista = RegistroPesoBean.this.servicioRegistroPeso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  234: 265 */         RegistroPesoBean.this.listaRegistroPeso.setRowCount(RegistroPesoBean.this.servicioRegistroPeso.contarPorCriterio(filters));
/*  235:     */         
/*  236: 267 */         return lista;
/*  237:     */       }
/*  238: 270 */     };
/*  239: 271 */     this.fechaHasta = new Date();
/*  240: 272 */     this.fechaDesde = new Date();
/*  241: 273 */     this.sucursal = this.servicioSucursal.cargarDetalle(AppUtil.getSucursal().getId());
/*  242:     */   }
/*  243:     */   
/*  244:     */   public String editar()
/*  245:     */   {
/*  246: 278 */     if ((this.registroPeso != null) && (this.registroPeso.getId() != 0))
/*  247:     */     {
/*  248: 281 */       this.registroPeso = this.servicioRegistroPeso.cargarDetalle(this.registroPeso.getId());
/*  249: 284 */       if (this.registroPeso.getEstado().equals(EstadoRegistroPeso.SEGUNDO_PESO))
/*  250:     */       {
/*  251: 285 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.registroPeso.getEstado());
/*  252: 286 */         return "";
/*  253:     */       }
/*  254:     */       DetalleRegistroPeso rpdpc;
/*  255: 289 */       if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) && 
/*  256: 290 */         (this.registroPeso.getDetallePedidoProveedor() != null))
/*  257:     */       {
/*  258: 293 */         this.pedidoProveedor = this.registroPeso.getDetallePedidoProveedor().getPedidoProveedor();
/*  259: 294 */         actualizarEmpresaListener();
/*  260: 295 */         cargarDetallePedidoListener(false);
/*  261:     */       }
/*  262: 297 */       else if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) && 
/*  263: 298 */         (this.registroPeso.getDetalleTransferenciaBodega() != null))
/*  264:     */       {
/*  265: 301 */         actualizarListaTransferenciaBodega();
/*  266: 302 */         this.transferenciaBodega = this.registroPeso.getDetalleTransferenciaBodega().getMovimientoInventario();
/*  267: 303 */         cargarDetalleTransferenciaBodega(false);
/*  268:     */       }
/*  269:     */       else
/*  270:     */       {
/*  271:     */         Iterator localIterator;
/*  272: 305 */         if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) && (this.registroPeso.getEmpresa() != null))
/*  273:     */         {
/*  274: 308 */           actualizarEmpresaListener();
/*  275: 309 */           localIterator = this.registroPeso.getListaDetalleRegistroPeso().iterator();
/*  276: 309 */           if (localIterator.hasNext())
/*  277:     */           {
/*  278: 309 */             DetalleRegistroPeso rpdpc = (DetalleRegistroPeso)localIterator.next();
/*  279: 310 */             this.pedidoCliente = rpdpc.getDetallePedidoCliente().getPedidoCliente();
/*  280:     */           }
/*  281: 313 */           cargarDetallePedidoListener(false);
/*  282: 314 */           actualizarTodosSaldos(false);
/*  283: 315 */           totalizarDetalles();
/*  284:     */         }
/*  285: 317 */         else if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso()))
/*  286:     */         {
/*  287: 320 */           localIterator = this.registroPeso.getListaDetalleRegistroPeso().iterator();
/*  288: 320 */           if (localIterator.hasNext())
/*  289:     */           {
/*  290: 320 */             DetalleRegistroPeso rpdpc = (DetalleRegistroPeso)localIterator.next();
/*  291: 321 */             this.ordenSalidaMaterial = rpdpc.getDetalleOrdenSalidaMaterial().getOrdenSalidaMaterial();
/*  292:     */           }
/*  293: 324 */           actualizarOrdenSalidaMaterial(false);
/*  294: 325 */           cargarDetalleTransferenciaBodega(false);
/*  295: 326 */           actualizarTodosSaldos(false);
/*  296: 327 */           totalizarDetalles();
/*  297:     */         }
/*  298: 329 */         else if (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso()))
/*  299:     */         {
/*  300: 332 */           localIterator = this.registroPeso.getListaDetalleRegistroPeso().iterator();
/*  301: 332 */           if (localIterator.hasNext())
/*  302:     */           {
/*  303: 332 */             rpdpc = (DetalleRegistroPeso)localIterator.next();
/*  304: 333 */             setFacturaCliente(rpdpc.getDetalleFacturaCliente().getFacturaCliente());
/*  305:     */           }
/*  306: 336 */           actualizarDetallesFacturaCliente(false);
/*  307: 337 */           actualizarTodosSaldos(false);
/*  308: 338 */           totalizarDetalles();
/*  309:     */         }
/*  310:     */       }
/*  311: 343 */       if ((this.registroPeso.getCargasAnteriores() != null) && (!this.registroPeso.getCargasAnteriores().isEmpty()))
/*  312:     */       {
/*  313: 344 */         String[] cargas = this.registroPeso.getCargasAnteriores().split(", ");
/*  314: 345 */         this.listaCargaPrevia = new ArrayList();
/*  315: 346 */         for (String string : cargas) {
/*  316: 347 */           this.listaCargaPrevia.add(string);
/*  317:     */         }
/*  318:     */       }
/*  319:     */       else
/*  320:     */       {
/*  321: 350 */         this.listaCargaPrevia = new ArrayList();
/*  322:     */       }
/*  323: 353 */       setEditado(true);
/*  324: 356 */       if ((this.registroPeso.getId() != 0) && 
/*  325: 357 */         (this.registroPeso.getDispositivo() == null) && (getListaDispositivo().size() > 0)) {
/*  326: 358 */         this.registroPeso.setDispositivo((Dispositivo)getListaDispositivo().get(0));
/*  327:     */       }
/*  328: 362 */       actualizarTransportista(false);
/*  329: 363 */       actualizarVehiculo(false);
/*  330:     */     }
/*  331:     */     else
/*  332:     */     {
/*  333: 365 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  334:     */     }
/*  335: 367 */     return "";
/*  336:     */   }
/*  337:     */   
/*  338:     */   public String guardar()
/*  339:     */   {
/*  340: 373 */     this.mensajeConfirmacion = null;
/*  341: 374 */     boolean error = false;
/*  342: 375 */     boolean existeDiferencia = false;
/*  343: 376 */     boolean segundoPeso = (EstadoRegistroPeso.PRIMER_PESO.equals(this.registroPeso.getEstado())) && (this.registroPeso.getFechaSalida() != null);
/*  344: 378 */     if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) && (segundoPeso))
/*  345:     */     {
/*  346: 380 */       BigDecimal pesoNeto = this.registroPeso.getPesoNeto();
/*  347: 381 */       BigDecimal pesoNetoAux = this.registroPeso.getPesoNeto();
/*  348: 382 */       for (int i = 0; i < getTransferenciaBodega().getDetalleMovimientosInventario().size(); i++)
/*  349:     */       {
/*  350: 384 */         pesoNeto = pesoNeto.subtract(((DetalleMovimientoInventario)getTransferenciaBodega().getDetalleMovimientosInventario().get(i)).getCantidad());
/*  351: 385 */         if (pesoNeto.compareTo(BigDecimal.ZERO) >= 0) {
/*  352: 387 */           ((DetalleMovimientoInventario)getTransferenciaBodega().getDetalleMovimientosInventario().get(i)).setCantidadPesada(((DetalleMovimientoInventario)getTransferenciaBodega().getDetalleMovimientosInventario().get(i)).getCantidad());
/*  353:     */         } else {
/*  354: 389 */           ((DetalleMovimientoInventario)getTransferenciaBodega().getDetalleMovimientosInventario().get(i)).setCantidadPesada(pesoNetoAux.abs());
/*  355:     */         }
/*  356: 391 */         if (i == getTransferenciaBodega().getDetalleMovimientosInventario().size() - 1) {
/*  357: 392 */           ((DetalleMovimientoInventario)getTransferenciaBodega().getDetalleMovimientosInventario().get(i)).setCantidadPesada(pesoNetoAux.abs());
/*  358:     */         }
/*  359: 394 */         pesoNetoAux = pesoNeto;
/*  360:     */       }
/*  361: 396 */       EntidadUsuario usuario = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  362:     */       try
/*  363:     */       {
/*  364: 398 */         existeDiferencia = this.servicioMovimientoInventario.existenDiferenciasRecepcionTransferencia(this.transferenciaBodega, usuario
/*  365: 399 */           .isIndicadorAdministrador());
/*  366:     */       }
/*  367:     */       catch (AS2Exception e)
/*  368:     */       {
/*  369: 401 */         error = true;
/*  370: 402 */         JsfUtil.addErrorMessage(e, "");
/*  371:     */       }
/*  372: 404 */       if (existeDiferencia)
/*  373:     */       {
/*  374: 406 */         Map<Integer, MovimientoInventario> mapa = this.servicioMovimientoInventario.crearAjustesInventarioDiferenciasRecepcionTransferenciaRegistroPeso(this.transferenciaBodega);
/*  375: 407 */         this.ajusteInventarioEgreso = ((MovimientoInventario)mapa.get(Integer.valueOf(-1)));
/*  376: 408 */         this.ajusteInventarioIngreso = ((MovimientoInventario)mapa.get(Integer.valueOf(1)));
/*  377: 409 */         this.mostradoDialogoAjusteInventario = true;
/*  378: 410 */         RequestContext.getCurrentInstance().update(":form:panelDialogoAjusteInventario");
/*  379: 411 */         RequestContext.getCurrentInstance().execute("dialogAjusteInventario.show()");
/*  380:     */       }
/*  381:     */     }
/*  382: 415 */     if ((!error) && (!existeDiferencia))
/*  383:     */     {
/*  384:     */       try
/*  385:     */       {
/*  386: 417 */         this.servicioRegistroPeso.validar(this.registroPeso);
/*  387:     */       }
/*  388:     */       catch (AS2Exception e1)
/*  389:     */       {
/*  390: 419 */         if (!e1.getMensaje().contains("negativo")) {
/*  391: 420 */           this.mensajeConfirmacion = e1.getMensaje();
/*  392:     */         }
/*  393:     */       }
/*  394: 423 */       if (this.mensajeConfirmacion != null)
/*  395:     */       {
/*  396: 424 */         RequestContext context = RequestContext.getCurrentInstance();
/*  397: 425 */         context.execute("dialogConfirmar.show();");
/*  398:     */       }
/*  399:     */       else
/*  400:     */       {
/*  401:     */         try
/*  402:     */         {
/*  403: 429 */           if ((segundoPeso) && (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso()))) {
/*  404: 430 */             this.registroPeso.setPuntoDeVenta(AppUtil.getPuntoDeVenta());
/*  405:     */           }
/*  406: 432 */           this.servicioRegistroPeso.guardar(this.registroPeso);
/*  407: 433 */           limpiar();
/*  408: 434 */           setEditado(false);
/*  409: 435 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  410:     */         }
/*  411:     */         catch (ExcepcionAS2Inventario e)
/*  412:     */         {
/*  413: 438 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  414: 439 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/*  415:     */         }
/*  416:     */         catch (ExcepcionAS2Identification e)
/*  417:     */         {
/*  418: 441 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  419: 442 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/*  420:     */         }
/*  421:     */         catch (AS2Exception e)
/*  422:     */         {
/*  423: 444 */           JsfUtil.addErrorMessage(e, "");
/*  424:     */         }
/*  425:     */         catch (ExcepcionAS2 e)
/*  426:     */         {
/*  427: 446 */           addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/*  428:     */         }
/*  429:     */         catch (Exception e)
/*  430:     */         {
/*  431: 448 */           addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  432: 449 */           LOG.error("ERROR AL GUARDAR DATOS", e);
/*  433:     */         }
/*  434:     */       }
/*  435:     */     }
/*  436: 453 */     return "";
/*  437:     */   }
/*  438:     */   
/*  439:     */   public String eliminar()
/*  440:     */   {
/*  441: 458 */     if ((this.registroPeso != null) && (this.registroPeso.getId() != 0))
/*  442:     */     {
/*  443: 460 */       if (this.registroPeso.getEstado().equals(EstadoRegistroPeso.SEGUNDO_PESO))
/*  444:     */       {
/*  445: 461 */         addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " - " + this.registroPeso.getEstado());
/*  446: 462 */         return "";
/*  447:     */       }
/*  448:     */       try
/*  449:     */       {
/*  450: 465 */         this.servicioRegistroPeso.eliminar(this.registroPeso);
/*  451: 466 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  452: 467 */         limpiar();
/*  453:     */       }
/*  454:     */       catch (Exception e)
/*  455:     */       {
/*  456: 469 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  457: 470 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  458:     */       }
/*  459:     */     }
/*  460:     */     else
/*  461:     */     {
/*  462: 473 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  463:     */     }
/*  464: 475 */     return "";
/*  465:     */   }
/*  466:     */   
/*  467:     */   public String limpiar()
/*  468:     */   {
/*  469: 480 */     this.pedidoProveedor = null;
/*  470: 481 */     this.pedidoCliente = null;
/*  471: 482 */     this.listaDetallePedidoProveedor = null;
/*  472: 483 */     this.listaDetallePedidoCliente = null;
/*  473: 484 */     this.chofer = null;
/*  474: 485 */     this.pesoActual = BigDecimal.ZERO;
/*  475: 486 */     this.listaBodga = null;
/*  476: 487 */     this.listaCargaPrevia = new ArrayList();
/*  477: 488 */     crearRegistroPeso();
/*  478: 489 */     if (this.dtDetallePedidoProveedor != null) {
/*  479: 490 */       this.dtDetallePedidoProveedor.reset();
/*  480:     */     }
/*  481: 492 */     if (this.dtDetalleTransferenciaBodega != null) {
/*  482: 493 */       this.dtDetalleTransferenciaBodega.reset();
/*  483:     */     }
/*  484: 495 */     if (this.dtDetallePedidoCliente != null) {
/*  485: 496 */       this.dtDetallePedidoCliente.reset();
/*  486:     */     }
/*  487: 498 */     actualizarListaTransferenciaBodega();
/*  488: 499 */     return "";
/*  489:     */   }
/*  490:     */   
/*  491:     */   public String cargarDatos()
/*  492:     */   {
/*  493: 504 */     return "";
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void crearRegistroPeso()
/*  497:     */   {
/*  498: 511 */     this.registroPeso = new RegistroPeso();
/*  499: 512 */     this.registroPeso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  500: 513 */     this.registroPeso.setIdSucursal(AppUtil.getSucursal().getId());
/*  501: 514 */     this.registroPeso.setEstado(EstadoRegistroPeso.EN_ESPERA);
/*  502: 515 */     this.registroPeso.setNumero("");
/*  503: 516 */     this.registroPeso.setFecha(FuncionesUtiles.setAtributoFecha(new Date()));
/*  504: 517 */     this.registroPeso.setTipoRegistroPeso(TipoRegistroPeso.INGRESO_MATERIA_PRIMA);
/*  505: 518 */     if (getListaDocumento().size() > 0) {
/*  506: 519 */       this.registroPeso.setDocumento((Documento)getListaDocumento().get(0));
/*  507:     */     }
/*  508:     */   }
/*  509:     */   
/*  510:     */   public void actualizarTipo()
/*  511:     */   {
/*  512: 527 */     if (this.registroPeso != null)
/*  513:     */     {
/*  514: 528 */       this.registroPeso.setEmpresa(null);
/*  515: 529 */       this.registroPeso.setDetallePedidoProveedor(null);
/*  516: 530 */       this.registroPeso.setDetalleTransferenciaBodega(null);
/*  517: 531 */       this.pedidoProveedor = null;
/*  518: 532 */       this.transferenciaBodega = null;
/*  519: 533 */       actualizarTransportista(true);
/*  520:     */     }
/*  521: 535 */     this.listaBodga = null;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void actualizarTransportista(boolean setearVehiculo)
/*  525:     */   {
/*  526: 542 */     this.listaChoferCombo = null;
/*  527: 543 */     this.listaVehiculoCombo = null;
/*  528: 544 */     this.listaRuta = null;
/*  529: 545 */     if (setearVehiculo)
/*  530:     */     {
/*  531: 546 */       this.registroPeso.setVehiculo(null);
/*  532: 547 */       this.registroPeso.setRuta(null);
/*  533: 548 */       this.registroPeso.setChofer(null);
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void actualizarEmpresaListener()
/*  538:     */   {
/*  539: 556 */     Empresa empresa = this.servicioEmpresa.cargarDetalle(this.registroPeso.getEmpresa());
/*  540: 557 */     this.registroPeso.setEmpresa(empresa);
/*  541: 558 */     String direccion = "";
/*  542: 560 */     for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones())
/*  543:     */     {
/*  544: 561 */       direccion = direccionEmpresa.getDireccionCompleta();
/*  545: 562 */       this.direccionEmpresaSeleccionada = direccionEmpresa;
/*  546: 563 */       if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) && 
/*  547: 564 */         (direccionEmpresa.isIndicadorDireccionPrincipal())) {
/*  548:     */         break;
/*  549:     */       }
/*  550: 567 */       if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) && (direccionEmpresa.isIndicadorDireccionEnvio())) {
/*  551:     */         break;
/*  552:     */       }
/*  553:     */     }
/*  554: 571 */     this.registroPeso.setDireccion(direccion);
/*  555: 572 */     if (this.registroPeso.getTipoRegistroPeso() != null) {
/*  556: 573 */       if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) {
/*  557: 574 */         actualizarListaPedidoProveedor();
/*  558: 575 */       } else if (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) {
/*  559: 576 */         actualizarListaPedidoCliente();
/*  560: 577 */       } else if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/*  561: 578 */         this.listaDetalleOrdenSalidaMaterial = null;
/*  562:     */       }
/*  563:     */     }
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void actualizarListaPedidoCliente()
/*  567:     */   {
/*  568: 587 */     this.listaPedidoCliente.clear();
/*  569: 588 */     this.pedidoCliente = null;
/*  570: 589 */     this.listaDetallePedidoCliente = null;
/*  571: 590 */     if (this.registroPeso.getEmpresa() != null) {
/*  572: 591 */       this.listaPedidoCliente = this.servicioPedidoCliente.getPedidosClienteNoExistentesRegistroPeso(Estado.REALIZADO_LOGISTICA, this.registroPeso.getEmpresa(), this.registroPeso);
/*  573:     */     }
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void actualizarListaPedidoProveedor()
/*  577:     */   {
/*  578: 599 */     this.listaPedidoProveedor.clear();
/*  579: 600 */     if (this.registroPeso.getEmpresa() != null) {
/*  580: 601 */       this.listaPedidoProveedor = this.servicioPedidoProveedor.listaPedidosPorRecibir(this.registroPeso.getEmpresa().getId());
/*  581:     */     }
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void actualizarListaTransferenciaBodega()
/*  585:     */   {
/*  586: 609 */     this.listaTransferenciaBodega.clear();
/*  587: 610 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/*  588: 611 */     filtros.put("documento.documentoBase", DocumentoBase.TRANSFERENCIA_BODEGA.toString());
/*  589: 612 */     filtros.put("estado", String.valueOf(Estado.ELABORADO));
/*  590: 613 */     this.listaTransferenciaBodega = this.servicioMovimientoInventario.obtenerListaCombo("fecha", true, filtros);
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void cargarDetallePedidoListener(boolean autoseleccionar)
/*  594:     */   {
/*  595: 619 */     if ((this.registroPeso.getTipoRegistroPeso() != null) && (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)))
/*  596:     */     {
/*  597: 620 */       if (this.pedidoProveedor != null)
/*  598:     */       {
/*  599: 621 */         this.listaDetallePedidoProveedor = this.servicioPedidoProveedor.obtenerListaDetallePedidoPorRecibir(this.pedidoProveedor.getId());
/*  600: 622 */         if ((autoseleccionar) && (this.listaDetallePedidoProveedor.size() > 0))
/*  601:     */         {
/*  602: 623 */           this.registroPeso.setDetallePedidoProveedor((DetallePedidoProveedor)this.listaDetallePedidoProveedor.get(0));
/*  603: 624 */           this.registroPeso.setProducto(this.registroPeso.getDetallePedidoProveedor().getProducto());
/*  604: 625 */           this.registroPeso.setPesoDestareUnidad(this.registroPeso.getDetallePedidoProveedor().getProducto().getPesoDestareUnidad());
/*  605:     */         }
/*  606:     */       }
/*  607: 628 */       this.listaBodga = null;
/*  608:     */     }
/*  609: 631 */     else if (this.pedidoCliente != null)
/*  610:     */     {
/*  611: 632 */       this.pedidoCliente = this.servicioPedidoCliente.cargarDetalle(this.pedidoCliente.getId());
/*  612: 633 */       this.listaDetallePedidoCliente = this.pedidoCliente.getListaDetallePedidoCliente();
/*  613: 634 */       if (autoseleccionar)
/*  614:     */       {
/*  615: 635 */         this.registroPeso.setTransportista(this.pedidoCliente.getTransportista());
/*  616: 636 */         actualizarTransportista(false);
/*  617: 637 */         this.registroPeso.setChofer(this.pedidoCliente.getChofer());
/*  618: 638 */         this.registroPeso.setVehiculo(this.pedidoCliente.getVehiculo());
/*  619: 639 */         this.registroPeso.setRuta(this.pedidoCliente.getRuta());
/*  620: 640 */         actualizarVehiculo();
/*  621: 641 */         this.registroPeso.setBodega(this.pedidoCliente.getBodega());
/*  622: 642 */         actualizarTodosSaldos(true);
/*  623:     */       }
/*  624:     */       else
/*  625:     */       {
/*  626: 645 */         this.listaDetallePedidoClienteSeleccionados = new ArrayList();
/*  627: 646 */         for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  628: 647 */           if (!rpdpc.isEliminado()) {
/*  629: 648 */             this.listaDetallePedidoClienteSeleccionados.add(rpdpc.getDetallePedidoCliente());
/*  630:     */           }
/*  631:     */         }
/*  632: 652 */         for (DetalleRegistroPesoLote drp : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/*  633: 653 */           if (!drp.isEliminado())
/*  634:     */           {
/*  635: 654 */             String key = drp.getProducto().getId() + "";
/*  636: 655 */             if (drp.getProducto().isIndicadorLote()) {
/*  637: 656 */               key = key + "~" + drp.getLote().getId();
/*  638:     */             }
/*  639: 658 */             this.mapaSaldoComprometidoAnterior.put(key, drp.getCantidad());
/*  640:     */           }
/*  641:     */         }
/*  642:     */       }
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void cargarDetalleTransferenciaBodega(boolean autoseleccionar)
/*  647:     */   {
/*  648: 667 */     if (this.transferenciaBodega != null)
/*  649:     */     {
/*  650: 668 */       this.transferenciaBodega = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(this.transferenciaBodega.getId()));
/*  651: 669 */       this.listaDetalleTransferenciaBodega = this.transferenciaBodega.getDetalleMovimientosInventario();
/*  652: 670 */       if ((autoseleccionar) && (this.listaDetalleTransferenciaBodega.size() > 0))
/*  653:     */       {
/*  654: 671 */         this.registroPeso.setDetalleTransferenciaBodega((DetalleMovimientoInventario)this.listaDetalleTransferenciaBodega.get(0));
/*  655: 672 */         actualizarDetalleTransferenciaBodega();
/*  656:     */       }
/*  657: 674 */       BigDecimal aux = BigDecimal.ZERO;
/*  658: 675 */       for (DetalleMovimientoInventario dmi : this.transferenciaBodega.getDetalleMovimientosInventario())
/*  659:     */       {
/*  660: 676 */         BigDecimal pesoLinea = dmi.getCantidad();
/*  661: 677 */         if ((dmi.getProducto().getPeso() != null) && (dmi.getProducto().getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/*  662: 678 */           pesoLinea = pesoLinea.multiply(dmi.getProducto().getPeso());
/*  663:     */         }
/*  664: 680 */         aux = aux.add(pesoLinea);
/*  665:     */       }
/*  666: 682 */       this.registroPeso.setPesoReferencia(FuncionesUtiles.redondearBigDecimal(aux));
/*  667:     */     }
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void actualizarDetallePedidoProveedor()
/*  671:     */   {
/*  672: 687 */     this.listaBodga = null;
/*  673: 688 */     if (this.registroPeso.getDetallePedidoProveedor() != null) {
/*  674: 689 */       this.registroPeso.setProducto(this.registroPeso.getDetallePedidoProveedor().getProducto());
/*  675:     */     } else {
/*  676: 691 */       this.registroPeso.setProducto(null);
/*  677:     */     }
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void actualizarDetalleRegistroPeso()
/*  681:     */   {
/*  682: 697 */     for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  683: 698 */       rpdpc.setEliminado(true);
/*  684:     */     }
/*  685: 700 */     if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/*  686: 701 */       for (DetallePedidoCliente dpc : getListaDetallePedidoClienteSeleccionados()) {
/*  687: 702 */         verificarDetalleRegistroPeso(dpc, null, null, dpc.getUnidadVenta());
/*  688:     */       }
/*  689: 704 */     } else if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso())) {
/*  690: 705 */       for (DetalleOrdenSalidaMaterial dosm : getListaDetalleOrdenSalidaMaterialSeleccionados()) {
/*  691: 706 */         verificarDetalleRegistroPeso(null, dosm, null, dosm.getUnidad());
/*  692:     */       }
/*  693: 708 */     } else if (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/*  694: 709 */       for (DetalleFacturaCliente dfc : getListaDetalleFacturaClienteSeleccionados()) {
/*  695: 710 */         verificarDetalleRegistroPeso(null, null, dfc, dfc.getUnidadVenta());
/*  696:     */       }
/*  697:     */     }
/*  698: 713 */     actualizarTodosSaldos(false);
/*  699: 714 */     totalizarDetalles();
/*  700:     */   }
/*  701:     */   
/*  702:     */   private void verificarDetalleRegistroPeso(DetallePedidoCliente detallePedidoCliente, DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial, DetalleFacturaCliente detalleFacturaCliente, Unidad unidad)
/*  703:     */   {
/*  704: 719 */     boolean encontre = false;
/*  705: 720 */     if (this.registroPeso.getListaDetalleRegistroPeso().size() == 0) {
/*  706: 721 */       if (detallePedidoCliente != null) {
/*  707: 722 */         this.mapaUnidades.put(Integer.valueOf(detallePedidoCliente.getProducto().getId()), unidad.getNombre());
/*  708: 723 */       } else if (detalleOrdenSalidaMaterial != null) {
/*  709: 724 */         this.mapaUnidades.put(Integer.valueOf(detalleOrdenSalidaMaterial.getProducto().getId()), unidad.getNombre());
/*  710:     */       } else {
/*  711: 726 */         this.mapaUnidades.put(Integer.valueOf(detalleFacturaCliente.getProducto().getId()), unidad.getNombre());
/*  712:     */       }
/*  713:     */     }
/*  714: 730 */     for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso())
/*  715:     */     {
/*  716:     */       int idDetalleRegistrado;
/*  717:     */       int idDetalle;
/*  718: 733 */       if (detallePedidoCliente != null)
/*  719:     */       {
/*  720: 734 */         int idDetalleRegistrado = rpdpc.getDetallePedidoCliente().getId();
/*  721: 735 */         int idDetalle = detallePedidoCliente.getId();
/*  722: 736 */         this.mapaUnidades.put(Integer.valueOf(detallePedidoCliente.getProducto().getId()), unidad.getNombre());
/*  723:     */       }
/*  724: 737 */       else if (detalleOrdenSalidaMaterial != null)
/*  725:     */       {
/*  726: 738 */         int idDetalleRegistrado = rpdpc.getDetalleOrdenSalidaMaterial().getId();
/*  727: 739 */         int idDetalle = detalleOrdenSalidaMaterial.getId();
/*  728: 740 */         this.mapaUnidades.put(Integer.valueOf(detalleOrdenSalidaMaterial.getProducto().getId()), unidad.getNombre());
/*  729:     */       }
/*  730:     */       else
/*  731:     */       {
/*  732: 742 */         idDetalleRegistrado = rpdpc.getDetalleFacturaCliente().getId();
/*  733: 743 */         idDetalle = detalleFacturaCliente.getId();
/*  734: 744 */         this.mapaUnidades.put(Integer.valueOf(detalleFacturaCliente.getProducto().getId()), unidad.getNombre());
/*  735:     */       }
/*  736: 746 */       if (idDetalleRegistrado == idDetalle)
/*  737:     */       {
/*  738: 747 */         rpdpc.setEliminado(false);
/*  739: 748 */         encontre = true;
/*  740: 749 */         break;
/*  741:     */       }
/*  742:     */     }
/*  743: 753 */     if (!encontre)
/*  744:     */     {
/*  745: 754 */       DetalleRegistroPeso rpdpc = new DetalleRegistroPeso();
/*  746: 755 */       rpdpc.setIdOrganizacion(this.registroPeso.getIdOrganizacion());
/*  747: 756 */       rpdpc.setIdSucursal(this.registroPeso.getIdSucursal());
/*  748: 757 */       rpdpc.setRegistroPeso(this.registroPeso);
/*  749: 758 */       rpdpc.setDetallePedidoCliente(detallePedidoCliente);
/*  750: 759 */       rpdpc.setDetalleOrdenSalidaMaterial(detalleOrdenSalidaMaterial);
/*  751: 760 */       rpdpc.setDetalleFacturaCliente(detalleFacturaCliente);
/*  752: 761 */       this.registroPeso.getListaDetalleRegistroPeso().add(rpdpc);
/*  753:     */     }
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void actualizarDetalleOrdenSalidaMaterial()
/*  757:     */   {
/*  758: 767 */     for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  759: 768 */       rpdpc.setEliminado(true);
/*  760:     */     }
/*  761: 770 */     for (DetalleOrdenSalidaMaterial dpc : getListaDetalleOrdenSalidaMaterialSeleccionados())
/*  762:     */     {
/*  763: 771 */       boolean encontre = false;
/*  764: 773 */       for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  765: 774 */         if (rpdpc.getDetalleOrdenSalidaMaterial().getId() == dpc.getId())
/*  766:     */         {
/*  767: 775 */           rpdpc.setEliminado(false);
/*  768: 776 */           encontre = true;
/*  769: 777 */           break;
/*  770:     */         }
/*  771:     */       }
/*  772: 781 */       if (!encontre)
/*  773:     */       {
/*  774: 782 */         DetalleRegistroPeso rpdpc = new DetalleRegistroPeso();
/*  775: 783 */         rpdpc.setIdOrganizacion(this.registroPeso.getIdOrganizacion());
/*  776: 784 */         rpdpc.setIdSucursal(this.registroPeso.getIdSucursal());
/*  777: 785 */         rpdpc.setRegistroPeso(this.registroPeso);
/*  778: 786 */         rpdpc.setDetalleOrdenSalidaMaterial(dpc);
/*  779: 787 */         this.registroPeso.getListaDetalleRegistroPeso().add(rpdpc);
/*  780:     */       }
/*  781:     */     }
/*  782: 790 */     actualizarTodosSaldos(true);
/*  783: 791 */     totalizarDetalles();
/*  784:     */   }
/*  785:     */   
/*  786:     */   public void actualizarDetalleTransferenciaBodega()
/*  787:     */   {
/*  788: 795 */     if (this.registroPeso.getDetalleTransferenciaBodega() != null)
/*  789:     */     {
/*  790: 796 */       if (this.registroPeso.getDetalleTransferenciaBodega().getInventarioProducto() != null) {
/*  791: 797 */         this.registroPeso.setLote(this.registroPeso.getDetalleTransferenciaBodega().getInventarioProducto().getLote());
/*  792:     */       }
/*  793: 799 */       this.registroPeso.setProducto(this.registroPeso.getDetalleTransferenciaBodega().getProducto());
/*  794: 800 */       this.registroPeso.setBodega(this.registroPeso.getDetalleTransferenciaBodega().getBodegaDestino());
/*  795: 802 */       if (this.registroPeso.getDetalleTransferenciaBodega().getMovimientoInventario().getGuiaRemision() != null) {
/*  796: 804 */         this.registroPeso.setNumeroGuiaRemision(this.registroPeso.getDetalleTransferenciaBodega().getMovimientoInventario().getGuiaRemision().getNumero());
/*  797:     */       }
/*  798:     */     }
/*  799:     */     else
/*  800:     */     {
/*  801: 807 */       this.registroPeso.setProducto(null);
/*  802: 808 */       this.registroPeso.setBodega(null);
/*  803:     */       
/*  804: 810 */       this.registroPeso.setNumeroGuiaRemision(null);
/*  805:     */     }
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void agregarCargasAnteriores()
/*  809:     */   {
/*  810: 815 */     String cargasPrevias = "";
/*  811: 817 */     if (this.listaCargaPrevia == null) {
/*  812: 818 */       this.listaCargaPrevia = new ArrayList();
/*  813:     */     }
/*  814: 820 */     for (String cargaPrevia : this.listaCargaPrevia)
/*  815:     */     {
/*  816: 821 */       String nueva = cargaPrevia + ", ";
/*  817: 822 */       cargasPrevias = cargasPrevias + nueva;
/*  818:     */     }
/*  819: 824 */     this.registroPeso.setCargasAnteriores(cargasPrevias);
/*  820:     */   }
/*  821:     */   
/*  822:     */   public void actualizarOrdenSalidaMaterial(boolean autoSeleccionar)
/*  823:     */   {
/*  824: 828 */     if (getOrdenSalidaMaterial() != null)
/*  825:     */     {
/*  826: 829 */       this.ordenSalidaMaterial = this.servicioOrdenSalidaMaterial.cargarDetalle(getOrdenSalidaMaterial().getId());
/*  827: 830 */       this.registroPeso.setOrdenSalidaMaterial(this.ordenSalidaMaterial);
/*  828: 831 */       this.listaDetalleOrdenSalidaMaterial = this.ordenSalidaMaterial.getListaDetalleOrdenSalidaMaterial();
/*  829: 832 */       if (autoSeleccionar)
/*  830:     */       {
/*  831: 833 */         this.registroPeso.setTransportista(this.ordenSalidaMaterial.getTransportista());
/*  832: 834 */         actualizarTransportista(false);
/*  833: 835 */         this.registroPeso.setChofer(this.ordenSalidaMaterial.getChofer());
/*  834: 836 */         this.registroPeso.setVehiculo(this.ordenSalidaMaterial.getVehiculo());
/*  835: 837 */         this.registroPeso.setRuta(this.ordenSalidaMaterial.getRuta());
/*  836: 838 */         actualizarVehiculo();
/*  837: 839 */         this.registroPeso.setBodega(this.ordenSalidaMaterial.getBodegaOrigen());
/*  838: 840 */         if (!this.listaDetalleOrdenSalidaMaterial.isEmpty())
/*  839:     */         {
/*  840: 841 */           this.registroPeso.setProducto(((DetalleOrdenSalidaMaterial)this.listaDetalleOrdenSalidaMaterial.get(0)).getProducto());
/*  841: 842 */           this.registroPeso.setLote(((DetalleOrdenSalidaMaterial)this.listaDetalleOrdenSalidaMaterial.get(0)).getLote());
/*  842:     */         }
/*  843: 844 */         actualizarTodosSaldos(true);
/*  844:     */       }
/*  845:     */       else
/*  846:     */       {
/*  847: 847 */         this.listaDetalleOrdenSalidaMaterialSeleccionados = new ArrayList();
/*  848: 848 */         for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  849: 849 */           if (!rpdpc.isEliminado()) {
/*  850: 850 */             this.listaDetalleOrdenSalidaMaterialSeleccionados.add(rpdpc.getDetalleOrdenSalidaMaterial());
/*  851:     */           }
/*  852:     */         }
/*  853: 854 */         for (DetalleRegistroPesoLote drp : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/*  854: 855 */           if (!drp.isEliminado())
/*  855:     */           {
/*  856: 856 */             String key = drp.getProducto().getId() + "";
/*  857: 857 */             if (drp.getProducto().isIndicadorLote()) {
/*  858: 858 */               key = key + "~" + drp.getLote().getId();
/*  859:     */             }
/*  860: 860 */             this.mapaSaldoComprometidoAnterior.put(key, drp.getCantidad());
/*  861:     */           }
/*  862:     */         }
/*  863:     */       }
/*  864:     */     }
/*  865:     */   }
/*  866:     */   
/*  867:     */   public void actualizarFactura(SelectEvent event)
/*  868:     */   {
/*  869: 869 */     FacturaCliente facturaCliente = (FacturaCliente)event.getObject();
/*  870: 870 */     setFacturaCliente(facturaCliente);
/*  871: 871 */     actualizarDetallesFacturaCliente(true);
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void seleccionarUltimaFactura()
/*  875:     */   {
/*  876: 879 */     FacturaCliente facturaCliente = this.servicioFacturaCliente.obtenerUltimaFacturaAutorizadaPorCliente(AppUtil.getOrganizacion().getId(), this.registroPeso
/*  877: 880 */       .getEmpresa(), true, null);
/*  878: 881 */     setFacturaCliente(facturaCliente);
/*  879: 882 */     actualizarDetallesFacturaCliente(true);
/*  880:     */   }
/*  881:     */   
/*  882:     */   public void actualizarDetallesFacturaCliente(boolean autoSeleccionar)
/*  883:     */   {
/*  884: 886 */     if (getFacturaCliente() != null)
/*  885:     */     {
/*  886: 887 */       this.listaDetalleFacturaCliente = this.servicioNotaCreditoCliente.obtenerDetalleFacturaClientePorDevolver(getFacturaCliente().getId());
/*  887: 888 */       if (autoSeleccionar)
/*  888:     */       {
/*  889: 889 */         this.registroPeso.setBodega(AppUtil.getBodega());
/*  890:     */       }
/*  891:     */       else
/*  892:     */       {
/*  893: 892 */         this.listaDetalleFacturaClienteSeleccionados = new ArrayList();
/*  894: 893 */         for (DetalleRegistroPeso rpdpc : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  895: 894 */           if (!rpdpc.isEliminado()) {
/*  896: 895 */             this.listaDetalleFacturaClienteSeleccionados.add(rpdpc.getDetalleFacturaCliente());
/*  897:     */           }
/*  898:     */         }
/*  899: 899 */         for (DetalleRegistroPesoLote drp : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/*  900: 900 */           if (!drp.isEliminado())
/*  901:     */           {
/*  902: 901 */             String key = drp.getProducto().getId() + "";
/*  903: 902 */             if (drp.getProducto().isIndicadorLote()) {
/*  904: 903 */               key = key + "~" + drp.getLote().getId();
/*  905:     */             }
/*  906: 905 */             this.mapaSaldoComprometidoAnterior.put(key, drp.getCantidad());
/*  907:     */           }
/*  908:     */         }
/*  909:     */       }
/*  910:     */     }
/*  911:     */   }
/*  912:     */   
/*  913:     */   public void actualizarCantidades()
/*  914:     */   {
/*  915: 922 */     for (Iterator localIterator1 = this.listaDetalleFacturaCliente.iterator(); localIterator1.hasNext();)
/*  916:     */     {
/*  917: 922 */       dfc = (DetalleFacturaCliente)localIterator1.next();
/*  918: 923 */       for (DetalleRegistroPeso drp : this.registroPeso.getListaDetalleRegistroPeso()) {
/*  919: 924 */         if (dfc.getId() == drp.getDetalleFacturaCliente().getId()) {
/*  920: 925 */           drp.setCantidad(dfc.getCantidadADevolver());
/*  921:     */         }
/*  922:     */       }
/*  923:     */     }
/*  924:     */     DetalleFacturaCliente dfc;
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void crearChofer()
/*  928:     */   {
/*  929: 932 */     this.chofer = new Chofer();
/*  930: 933 */     this.chofer.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  931: 934 */     this.chofer.setIdSucursal(AppUtil.getSucursal().getId());
/*  932: 935 */     this.chofer.setActivo(true);
/*  933: 936 */     this.chofer.setTransportista(this.registroPeso.getTransportista());
/*  934: 937 */     this.chofer.setDescripcion("");
/*  935: 938 */     this.chofer.setRendered(true);
/*  936:     */   }
/*  937:     */   
/*  938:     */   public void guardarChofer()
/*  939:     */   {
/*  940:     */     try
/*  941:     */     {
/*  942: 943 */       this.servicioChofer.guardar(getChofer());
/*  943: 944 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  944:     */       
/*  945: 946 */       this.registroPeso.setTransportista(this.chofer.getTransportista());
/*  946: 947 */       actualizarTransportista(false);
/*  947: 948 */       this.registroPeso.setChofer(this.chofer);
/*  948:     */     }
/*  949:     */     catch (ExcepcionAS2Identification e)
/*  950:     */     {
/*  951: 950 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  952: 951 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  953:     */     }
/*  954:     */     catch (AS2Exception e)
/*  955:     */     {
/*  956: 953 */       JsfUtil.addErrorMessage(e, "");
/*  957:     */     }
/*  958:     */     catch (Exception e)
/*  959:     */     {
/*  960: 955 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  961: 956 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  962:     */     }
/*  963:     */   }
/*  964:     */   
/*  965:     */   public void actualizarPesada()
/*  966:     */   {
/*  967: 961 */     if ((this.registroPeso.getDispositivo() != null) && (!this.registroPeso.getDispositivo().isFueraLinea())) {
/*  968: 962 */       this.pesoActual = this.servicioMarcacionDispositivo.getMarcacion(AppUtil.getOrganizacion().getId(), this.registroPeso.getDispositivo().getIp());
/*  969:     */     }
/*  970:     */   }
/*  971:     */   
/*  972:     */   public void capturarPeso()
/*  973:     */   {
/*  974: 971 */     if (this.registroPeso.getEstado().equals(EstadoRegistroPeso.EN_ESPERA))
/*  975:     */     {
/*  976: 972 */       this.registroPeso.setPesoEntrada(this.pesoActual);
/*  977: 973 */       this.registroPeso.setFechaEntrada(new Date());
/*  978:     */     }
/*  979:     */     else
/*  980:     */     {
/*  981: 975 */       this.registroPeso.setPesoSalida(this.pesoActual);
/*  982: 976 */       this.registroPeso.setFechaSalida(new Date());
/*  983:     */     }
/*  984: 978 */     this.servicioRegistroPeso.totalizarPesoNeto(this.registroPeso);
/*  985:     */   }
/*  986:     */   
/*  987:     */   public void calcularDestareTotal()
/*  988:     */   {
/*  989: 985 */     if ((this.registroPeso.getCantidad() != null) && (this.registroPeso.getDetallePedidoProveedor() != null))
/*  990:     */     {
/*  991: 987 */       BigDecimal destareTotal = this.registroPeso.getCantidad().multiply(this.registroPeso.getPesoDestareUnidad());
/*  992: 988 */       this.registroPeso.setPesoDestareTotal(destareTotal);
/*  993:     */     }
/*  994: 990 */     else if ((this.registroPeso.getTipoRegistroPeso() != null) && ((TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) || 
/*  995: 991 */       (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso())) || 
/*  996: 992 */       (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso()))))
/*  997:     */     {
/*  998: 994 */       BigDecimal destareTotal = BigDecimal.ZERO;
/*  999: 995 */       BigDecimal cantidadDetalleTotal = BigDecimal.ZERO;
/* 1000: 996 */       BigDecimal pesoDetalleTotal = BigDecimal.ZERO;
/* 1001: 997 */       this.registroPeso.setCantidad(BigDecimal.ZERO);
/* 1002: 998 */       this.registroPeso.setTotalPesoDetalles(BigDecimal.ZERO);
/* 1003:1000 */       for (DetalleRegistroPeso registroDetallePedido : this.registroPeso.getListaDetalleRegistroPeso())
/* 1004:     */       {
/* 1005:     */         Producto producto;
/* 1006:     */         Producto producto;
/* 1007:1002 */         if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso()))
/* 1008:     */         {
/* 1009:1003 */           producto = registroDetallePedido.getDetallePedidoCliente().getProducto();
/* 1010:     */         }
/* 1011:     */         else
/* 1012:     */         {
/* 1013:     */           Producto producto;
/* 1014:1004 */           if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1015:1005 */             producto = registroDetallePedido.getDetalleOrdenSalidaMaterial().getProducto();
/* 1016:     */           } else {
/* 1017:1007 */             producto = registroDetallePedido.getDetalleFacturaCliente().getProducto();
/* 1018:     */           }
/* 1019:     */         }
/* 1020:1009 */         if ((!registroDetallePedido.isEliminado()) && (!producto.isIndicadorLote()))
/* 1021:     */         {
/* 1022:     */           BigDecimal cantidadDetalle;
/* 1023:     */           BigDecimal cantidadDetalle;
/* 1024:1011 */           if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso()))
/* 1025:     */           {
/* 1026:1012 */             cantidadDetalle = registroDetallePedido.getDetallePedidoCliente().getCantidadPorDespachar();
/* 1027:     */           }
/* 1028:     */           else
/* 1029:     */           {
/* 1030:     */             BigDecimal cantidadDetalle;
/* 1031:1013 */             if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1032:1014 */               cantidadDetalle = registroDetallePedido.getDetalleOrdenSalidaMaterial().getCantidad();
/* 1033:     */             } else {
/* 1034:1016 */               cantidadDetalle = registroDetallePedido.getDetalleFacturaCliente().getCantidadADevolver();
/* 1035:     */             }
/* 1036:     */           }
/* 1037:1018 */           BigDecimal destareDetalle = cantidadDetalle.multiply(producto.getPesoDestareUnidad());
/* 1038:1019 */           BigDecimal pesoDetalle = cantidadDetalle.multiply(producto.getPeso());
/* 1039:     */           
/* 1040:1021 */           destareTotal = destareTotal.add(destareDetalle);
/* 1041:1022 */           cantidadDetalleTotal = cantidadDetalleTotal.add(cantidadDetalle);
/* 1042:1023 */           pesoDetalleTotal = pesoDetalleTotal.add(pesoDetalle);
/* 1043:     */         }
/* 1044:     */       }
/* 1045:1027 */       for (DetalleRegistroPesoLote detalle : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/* 1046:1028 */         if (!detalle.isEliminado())
/* 1047:     */         {
/* 1048:1029 */           BigDecimal cantidadDetalle = detalle.getCantidad();
/* 1049:1030 */           BigDecimal destareDetalle = cantidadDetalle.multiply(detalle.getProducto().getPesoDestareUnidad());
/* 1050:1031 */           BigDecimal pesoDetalle = cantidadDetalle.multiply(detalle.getProducto().getPeso());
/* 1051:     */           
/* 1052:1033 */           destareTotal = destareTotal.add(destareDetalle);
/* 1053:1034 */           cantidadDetalleTotal = cantidadDetalleTotal.add(cantidadDetalle);
/* 1054:1035 */           pesoDetalleTotal = pesoDetalleTotal.add(pesoDetalle);
/* 1055:     */         }
/* 1056:     */       }
/* 1057:1039 */       this.registroPeso.setPesoDestareTotal(destareTotal.setScale(2, RoundingMode.HALF_UP));
/* 1058:1040 */       this.registroPeso.setCantidad(cantidadDetalleTotal.setScale(2, RoundingMode.HALF_UP));
/* 1059:1041 */       this.registroPeso.setTotalPesoDetalles(pesoDetalleTotal.setScale(2, RoundingMode.HALF_UP));
/* 1060:     */     }
/* 1061:1043 */     this.servicioRegistroPeso.totalizarPesoNeto(this.registroPeso);
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void totalizarPesoNeto()
/* 1065:     */   {
/* 1066:1047 */     this.servicioRegistroPeso.totalizarPesoNeto(this.registroPeso);
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public void cargarDatosEditarParcial()
/* 1070:     */   {
/* 1071:1051 */     this.registroPeso = this.servicioRegistroPeso.cargarDetalle(((RegistroPeso)this.dtRegistroPeso.getRowData()).getId());
/* 1072:1052 */     this.indicadorEditarParcial = true;
/* 1073:1054 */     if (this.registroPeso.getDetallePedidoProveedor() != null)
/* 1074:     */     {
/* 1075:1055 */       this.pedidoProveedor = this.registroPeso.getDetallePedidoProveedor().getPedidoProveedor();
/* 1076:1056 */       actualizarEmpresaListener();
/* 1077:     */       
/* 1078:1058 */       boolean existe = false;
/* 1079:1059 */       for (PedidoProveedor pedidoProveedorBase : this.listaPedidoProveedor) {
/* 1080:1060 */         if (this.pedidoProveedor.getId() == pedidoProveedorBase.getId())
/* 1081:     */         {
/* 1082:1061 */           existe = true;
/* 1083:1062 */           break;
/* 1084:     */         }
/* 1085:     */       }
/* 1086:1065 */       if (!existe) {
/* 1087:1066 */         this.listaPedidoProveedor.add(this.pedidoProveedor);
/* 1088:     */       }
/* 1089:1068 */       this.listaDetallePedidoProveedor = new ArrayList();
/* 1090:1069 */       this.listaDetallePedidoProveedor.add(this.registroPeso.getDetallePedidoProveedor());
/* 1091:     */     }
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void guardarParcial()
/* 1095:     */   {
/* 1096:     */     try
/* 1097:     */     {
/* 1098:1075 */       this.servicioRegistroPeso.guardarParcial(this.registroPeso);
/* 1099:     */     }
/* 1100:     */     catch (AS2Exception e)
/* 1101:     */     {
/* 1102:1077 */       JsfUtil.addErrorMessage(e, "");
/* 1103:     */     }
/* 1104:     */     catch (ExcepcionAS2 e)
/* 1105:     */     {
/* 1106:1079 */       addErrorMessage(getLanguageController().getMensaje(e.getMessage()));
/* 1107:     */     }
/* 1108:     */     catch (Exception e)
/* 1109:     */     {
/* 1110:1081 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 1111:1082 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1112:     */     }
/* 1113:1085 */     this.dtRegistroPeso.reset();
/* 1114:1086 */     this.indicadorEditarParcial = false;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public void agregarDetalle()
/* 1118:     */   {
/* 1119:1090 */     DetalleRegistroPesoLote detalle = new DetalleRegistroPesoLote();
/* 1120:1091 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1121:1092 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 1122:1093 */     detalle.setRegistroPeso(this.registroPeso);
/* 1123:1094 */     detalle.setProducto(this.registroPeso.getProducto());
/* 1124:1095 */     this.registroPeso.getListaDetalleRegistroPesoLote().add(detalle);
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public void eliminarDetalle()
/* 1128:     */   {
/* 1129:1099 */     DetalleRegistroPesoLote detalle = (DetalleRegistroPesoLote)this.dtDetalleRegistroPeso.getRowData();
/* 1130:1100 */     detalle.setEliminado(true);
/* 1131:1101 */     detalle.setEliminadoManual(true);
/* 1132:1102 */     totalizarDetalles();
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public List<DetalleRegistroPesoLote> getListaDetalleRegistroPeso()
/* 1136:     */   {
/* 1137:1106 */     List<DetalleRegistroPesoLote> listaDetalleregistroPeso = new ArrayList();
/* 1138:1107 */     for (DetalleRegistroPesoLote detalle : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/* 1139:1108 */       if (!detalle.isEliminado()) {
/* 1140:1109 */         listaDetalleregistroPeso.add(detalle);
/* 1141:     */       }
/* 1142:     */     }
/* 1143:1112 */     return listaDetalleregistroPeso;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public void actualizarTodosSaldos(boolean prorratear)
/* 1147:     */   {
/* 1148:1122 */     for (DetalleRegistroPesoLote drp : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/* 1149:1123 */       drp.setEliminado(true);
/* 1150:     */     }
/* 1151:1125 */     if (this.registroPeso.getBodega() != null) {
/* 1152:1126 */       if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1153:1127 */         for (DetallePedidoCliente dpc : getListaDetallePedidoClienteSeleccionados()) {
/* 1154:1128 */           actualizarDetalleRegistroPesoLoteSaldo(dpc.getProducto(), dpc.getCantidadADespachar(), prorratear);
/* 1155:     */         }
/* 1156:1130 */       } else if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1157:1131 */         for (DetalleOrdenSalidaMaterial dosm : getListaDetalleOrdenSalidaMaterialSeleccionados()) {
/* 1158:1132 */           actualizarDetalleRegistroPesoLoteSaldo(dosm.getProducto(), dosm.getCantidad(), prorratear);
/* 1159:     */         }
/* 1160:1134 */       } else if (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1161:1135 */         for (DetalleFacturaCliente dfc : getListaDetalleFacturaClienteSeleccionados()) {
/* 1162:1136 */           actualizarDetalleRegistroPesoLoteSaldo(dfc.getProducto(), dfc.getCantidadADevolver(), prorratear);
/* 1163:     */         }
/* 1164:     */       }
/* 1165:     */     }
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   private void actualizarDetalleRegistroPesoLoteSaldo(Producto producto, BigDecimal cantidad, boolean prorratear)
/* 1169:     */   {
/* 1170:     */     BigDecimal cantidadPorRepartir;
/* 1171:1151 */     if (producto.isIndicadorLote())
/* 1172:     */     {
/* 1173:1154 */       cantidadPorRepartir = cantidad;
/* 1174:     */       
/* 1175:     */ 
/* 1176:1157 */       List<SaldoProductoLote> listaSPL = this.servicioProducto.obtenerProductosConSaldoBodegaLote(this.registroPeso.getBodega(), this.registroPeso.getFecha(), producto, false);
/* 1177:1161 */       for (SaldoProductoLote saldoProductoLote : listaSPL)
/* 1178:     */       {
/* 1179:1164 */         String key = saldoProductoLote.getProducto().getId() + "~" + saldoProductoLote.getLote().getId();
/* 1180:     */         
/* 1181:     */ 
/* 1182:1167 */         BigDecimal comprometidoAnterior = (BigDecimal)this.mapaSaldoComprometidoAnterior.get(key);
/* 1183:1168 */         if (comprometidoAnterior == null) {
/* 1184:1169 */           comprometidoAnterior = BigDecimal.ZERO;
/* 1185:     */         }
/* 1186:1174 */         BigDecimal saldoCalculado = saldoProductoLote.getSaldo().subtract(saldoProductoLote.getInventarioComprometido()).add(comprometidoAnterior);
/* 1187:1176 */         if (saldoCalculado.compareTo(BigDecimal.ZERO) > 0)
/* 1188:     */         {
/* 1189:1179 */           boolean encontre = false;
/* 1190:1180 */           for (Iterator localIterator2 = this.registroPeso.getListaDetalleRegistroPesoLote().iterator(); localIterator2.hasNext(); goto 516)
/* 1191:     */           {
/* 1192:1180 */             DetalleRegistroPesoLote drp = (DetalleRegistroPesoLote)localIterator2.next();
/* 1193:1181 */             if ((drp.getProducto().getId() == saldoProductoLote.getProducto().getId()) && 
/* 1194:1182 */               (drp.getLote().getId() == saldoProductoLote.getLote().getId()))
/* 1195:     */             {
/* 1196:1184 */               System.out.println("1_Producto:---------------->" + saldoProductoLote.getProducto().getNombre());
/* 1197:1185 */               System.out.println("1_Lote:-------------------->" + saldoProductoLote.getLote().getCodigo());
/* 1198:1186 */               System.out.println("1_idSaldoProductoLote:----->" + saldoProductoLote.getId());
/* 1199:1187 */               System.out.println("1_Inventario:-------------->" + saldoProductoLote.getSaldo());
/* 1200:1188 */               System.out.println("1_Comprometido:------------>" + saldoProductoLote.getInventarioComprometido());
/* 1201:1189 */               System.out.println("1_ComprometidoAnterior:---->" + comprometidoAnterior);
/* 1202:     */               
/* 1203:1191 */               drp.setEliminado(false);
/* 1204:1192 */               drp.setSaldoInventario(saldoProductoLote.getSaldo());
/* 1205:1193 */               drp.setSaldoComprometido(saldoProductoLote.getInventarioComprometido().subtract(comprometidoAnterior));
/* 1206:1194 */               drp.setSaldo(saldoCalculado);
/* 1207:1195 */               encontre = true;
/* 1208:1196 */               if (!prorratear) {
/* 1209:     */                 break;
/* 1210:     */               }
/* 1211:1198 */               if (cantidadPorRepartir.compareTo(saldoCalculado) >= 0)
/* 1212:     */               {
/* 1213:1199 */                 drp.setCantidad(saldoCalculado);
/* 1214:1200 */                 cantidadPorRepartir = cantidadPorRepartir.subtract(saldoCalculado);
/* 1215:     */               }
/* 1216:     */               else
/* 1217:     */               {
/* 1218:1202 */                 drp.setCantidad(cantidadPorRepartir);
/* 1219:1203 */                 cantidadPorRepartir = BigDecimal.ZERO;
/* 1220:     */               }
/* 1221:     */             }
/* 1222:     */           }
/* 1223:1211 */           if (!encontre)
/* 1224:     */           {
/* 1225:1213 */             System.out.println("2_Producto:---------------->" + saldoProductoLote.getProducto().getNombre());
/* 1226:1214 */             System.out.println("2_Lote:-------------------->" + saldoProductoLote.getLote().getCodigo());
/* 1227:1215 */             System.out.println("2_idSaldoProductoLote:----->" + saldoProductoLote.getId());
/* 1228:1216 */             System.out.println("2_Inventario:-------------->" + saldoProductoLote.getSaldo());
/* 1229:1217 */             System.out.println("2_Comprometido:------------>" + saldoProductoLote.getInventarioComprometido());
/* 1230:1218 */             System.out.println("2_ComprometidoAnterior:---->" + comprometidoAnterior);
/* 1231:     */             
/* 1232:1220 */             DetalleRegistroPesoLote drp = new DetalleRegistroPesoLote();
/* 1233:1221 */             drp.setIdOrganizacion(this.registroPeso.getIdOrganizacion());
/* 1234:1222 */             drp.setIdSucursal(this.registroPeso.getIdSucursal());
/* 1235:1223 */             drp.setRegistroPeso(this.registroPeso);
/* 1236:1224 */             drp.setProducto(saldoProductoLote.getProducto());
/* 1237:1225 */             drp.setLote(saldoProductoLote.getLote());
/* 1238:1226 */             drp.setSaldoInventario(saldoProductoLote.getSaldo());
/* 1239:1227 */             drp.setSaldoComprometido(saldoProductoLote.getInventarioComprometido().subtract(comprometidoAnterior));
/* 1240:1228 */             drp.setSaldo(saldoCalculado);
/* 1241:1229 */             this.registroPeso.getListaDetalleRegistroPesoLote().add(drp);
/* 1242:1230 */             if (prorratear) {
/* 1243:1232 */               if (cantidadPorRepartir.compareTo(saldoCalculado) >= 0)
/* 1244:     */               {
/* 1245:1233 */                 drp.setCantidad(saldoCalculado);
/* 1246:1234 */                 cantidadPorRepartir = cantidadPorRepartir.subtract(saldoCalculado);
/* 1247:     */               }
/* 1248:     */               else
/* 1249:     */               {
/* 1250:1236 */                 drp.setCantidad(cantidadPorRepartir);
/* 1251:1237 */                 cantidadPorRepartir = BigDecimal.ZERO;
/* 1252:     */               }
/* 1253:     */             }
/* 1254:     */           }
/* 1255:     */         }
/* 1256:     */       }
/* 1257:     */     }
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public void actualizarSaldo(DetalleRegistroPesoLote detalle)
/* 1261:     */   {
/* 1262:1247 */     if ((detalle.getLote() != null) && (this.registroPeso.getBodega() != null))
/* 1263:     */     {
/* 1264:1248 */       BigDecimal saldo = this.servicioProducto.getSaldoLote(detalle.getProducto().getIdProducto(), this.registroPeso.getBodega().getIdBodega(), detalle
/* 1265:1249 */         .getLote().getIdLote(), this.registroPeso.getFecha());
/* 1266:1250 */       detalle.setSaldo(saldo);
/* 1267:     */     }
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public void totalizarDetalles()
/* 1271:     */   {
/* 1272:1258 */     this.registroPeso.setMapaTotalDetalles(new HashMap());
/* 1273:1259 */     BigDecimal total = BigDecimal.ZERO;
/* 1274:1260 */     for (DetalleRegistroPesoLote detalleRegistroPesoLote : getListaDetalleRegistroPeso())
/* 1275:     */     {
/* 1276:1261 */       String key = "" + detalleRegistroPesoLote.getProducto().getId();
/* 1277:1262 */       if (detalleRegistroPesoLote.getLote() != null) {
/* 1278:1263 */         key = key + "~" + detalleRegistroPesoLote.getLote().getId();
/* 1279:     */       }
/* 1280:1265 */       BigDecimal valorMapa = (BigDecimal)this.registroPeso.getMapaTotalDetalles().get(key);
/* 1281:1266 */       if (valorMapa == null) {
/* 1282:1267 */         valorMapa = BigDecimal.ZERO;
/* 1283:     */       }
/* 1284:1269 */       valorMapa = valorMapa.add(detalleRegistroPesoLote.getCantidad());
/* 1285:1270 */       this.registroPeso.getMapaTotalDetalles().put(key, valorMapa);
/* 1286:     */       
/* 1287:1272 */       total = total.add(detalleRegistroPesoLote.getCantidad());
/* 1288:     */     }
/* 1289:1274 */     this.registroPeso.setTotalDetalles(total);
/* 1290:1275 */     calcularDestareTotal();
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public void crearVehiculo()
/* 1294:     */   {
/* 1295:1279 */     this.vehiculo = new Vehiculo();
/* 1296:1280 */     this.vehiculo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1297:1281 */     this.vehiculo.setIdSucursal(AppUtil.getSucursal().getId());
/* 1298:1282 */     this.vehiculo.setActivo(true);
/* 1299:1283 */     this.vehiculo.setTransportista(this.registroPeso.getTransportista());
/* 1300:1284 */     this.vehiculo.setDescripcion("");
/* 1301:1285 */     this.vehiculo.setRendered(true);
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public void guardarVehiculo()
/* 1305:     */   {
/* 1306:     */     try
/* 1307:     */     {
/* 1308:1290 */       this.servicioVehiculo.guardar(getVehiculo());
/* 1309:1291 */       this.registroPeso.setTransportista(getVehiculo().getTransportista());
/* 1310:1292 */       this.registroPeso.setVehiculo(getVehiculo());
/* 1311:1293 */       this.listaChoferCombo = null;
/* 1312:1294 */       actualizarVehiculo(true);
/* 1313:1295 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1314:     */     }
/* 1315:     */     catch (Exception e)
/* 1316:     */     {
/* 1317:1297 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 1318:1298 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1319:     */     }
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   public void actualizarVehiculo(boolean setearVehiculo)
/* 1323:     */   {
/* 1324:1304 */     this.listaRuta = null;
/* 1325:1305 */     this.listaVehiculoCombo = null;
/* 1326:1306 */     if (setearVehiculo) {
/* 1327:1307 */       this.registroPeso.setRuta(null);
/* 1328:     */     }
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public String actualizarValores()
/* 1332:     */   {
/* 1333:1313 */     if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) && 
/* 1334:1314 */       (this.registroPeso.getPesoNeto().compareTo(this.registroPeso.getDetalleTransferenciaBodega().getCantidad()) != 0))
/* 1335:     */     {
/* 1336:1316 */       BigDecimal diferencia = this.registroPeso.getPesoNeto().subtract(this.registroPeso.getPesoReferencia());
/* 1337:1317 */       this.registroPeso.setPesoDestareTotal(this.registroPeso.getPesoDestareTotal().add(diferencia));
/* 1338:1318 */       this.servicioRegistroPeso.totalizarPesoNeto(this.registroPeso);
/* 1339:     */     }
/* 1340:1320 */     else if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS)) || 
/* 1341:1321 */       (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) || 
/* 1342:1322 */       (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())))
/* 1343:     */     {
/* 1344:1324 */       BigDecimal[] rango = this.servicioRegistroPeso.calcularRangosPermitidos(this.registroPeso);
/* 1345:1325 */       BigDecimal pesoDespacho = BigDecimal.ZERO;
/* 1346:1326 */       for (DetalleRegistroPesoLote drpl : this.registroPeso.getListaDetalleRegistroPesoLote()) {
/* 1347:1327 */         if ((drpl.getProducto().getPeso() != null) && (drpl.getProducto().getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/* 1348:1328 */           pesoDespacho = pesoDespacho.add(drpl.getCantidad().multiply(drpl.getProducto().getPeso()));
/* 1349:     */         }
/* 1350:     */       }
/* 1351:1332 */       pesoDespacho = FuncionesUtiles.redondearBigDecimal(pesoDespacho);
/* 1352:1333 */       if ((this.registroPeso.getPesoNeto().compareTo(pesoDespacho) != 0) && (rango == null))
/* 1353:     */       {
/* 1354:1334 */         BigDecimal diferencia = this.registroPeso.getPesoNeto().subtract(pesoDespacho);
/* 1355:1335 */         this.registroPeso.setPesoDestareTotal(this.registroPeso.getPesoDestareTotal().add(diferencia));
/* 1356:1336 */         this.servicioRegistroPeso.totalizarPesoNeto(this.registroPeso);
/* 1357:     */       }
/* 1358:1337 */       else if (rango != null)
/* 1359:     */       {
/* 1360:1338 */         BigDecimal cantidadCompareMin = rango[0];
/* 1361:1339 */         BigDecimal cantidadCompareMax = rango[1];
/* 1362:1340 */         BigDecimal diferencia = BigDecimal.ZERO;
/* 1363:1341 */         if ((this.registroPeso.getPesoNeto().compareTo(cantidadCompareMin) < 0) || 
/* 1364:1342 */           (this.registroPeso.getPesoNeto().compareTo(cantidadCompareMax) > 0)) {
/* 1365:1343 */           if (this.registroPeso.getPesoNeto().compareTo(cantidadCompareMin) < 0) {
/* 1366:1344 */             diferencia = this.registroPeso.getPesoNeto().subtract(cantidadCompareMin);
/* 1367:1345 */           } else if (this.registroPeso.getPesoNeto().compareTo(cantidadCompareMax) > 0) {
/* 1368:1346 */             diferencia = this.registroPeso.getPesoNeto().subtract(cantidadCompareMax);
/* 1369:     */           }
/* 1370:     */         }
/* 1371:1349 */         this.registroPeso.setPesoDestareTotal(this.registroPeso.getPesoDestareTotal().add(diferencia));
/* 1372:1350 */         this.servicioRegistroPeso.totalizarPesoNeto(this.registroPeso);
/* 1373:     */       }
/* 1374:     */     }
/* 1375:1353 */     guardar();
/* 1376:1354 */     return null;
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public String generarAjusteInventario()
/* 1380:     */   {
/* 1381:1358 */     List<MovimientoInventario> listaAjustesInventario = new ArrayList();
/* 1382:1359 */     if (this.ajusteInventarioEgreso != null)
/* 1383:     */     {
/* 1384:1360 */       listaAjustesInventario.add(this.ajusteInventarioEgreso);
/* 1385:1361 */       if ((this.ajusteInventarioEgreso.getDocumento() == null) || (this.ajusteInventarioEgreso.getMotivoAjusteInventario() == null)) {
/* 1386:1362 */         return null;
/* 1387:     */       }
/* 1388:     */     }
/* 1389:1365 */     if (this.ajusteInventarioIngreso != null)
/* 1390:     */     {
/* 1391:1366 */       listaAjustesInventario.add(this.ajusteInventarioIngreso);
/* 1392:1367 */       if ((this.ajusteInventarioIngreso.getDocumento() == null) || (this.ajusteInventarioIngreso.getMotivoAjusteInventario() == null)) {
/* 1393:1368 */         return null;
/* 1394:     */       }
/* 1395:     */     }
/* 1396:     */     try
/* 1397:     */     {
/* 1398:1372 */       BigDecimal diferencia = this.registroPeso.getPesoNeto().subtract(this.registroPeso.getPesoReferencia());
/* 1399:1373 */       this.registroPeso.setPesoDestareTotal(diferencia);
/* 1400:1374 */       totalizarPesoNeto();
/* 1401:1375 */       this.servicioMovimientoInventario.guardarRecepcionTransferenciaConAjusteInventario(listaAjustesInventario, this.transferenciaBodega, this.registroPeso);
/* 1402:1376 */       RequestContext.getCurrentInstance().execute("dialogAjusteInventario.hide()");
/* 1403:1377 */       RequestContext.getCurrentInstance().update(":form:panelDialogoAjusteInventario");
/* 1404:     */       
/* 1405:1379 */       limpiar();
/* 1406:1380 */       setEditado(false);
/* 1407:1381 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1408:1382 */       cerrarDialogoAjusteInventario();
/* 1409:     */     }
/* 1410:     */     catch (ExcepcionAS2Identification e1)
/* 1411:     */     {
/* 1412:1384 */       addErrorMessage(getLanguageController().getMensaje(e1.getCodigoExcepcion()));
/* 1413:1385 */       e1.printStackTrace();
/* 1414:     */     }
/* 1415:     */     catch (ExcepcionAS2Inventario e)
/* 1416:     */     {
/* 1417:1387 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1418:1388 */       e.printStackTrace();
/* 1419:     */     }
/* 1420:     */     catch (ExcepcionAS2 e)
/* 1421:     */     {
/* 1422:1390 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1423:1391 */       e.printStackTrace();
/* 1424:     */     }
/* 1425:     */     catch (AS2Exception e)
/* 1426:     */     {
/* 1427:1393 */       JsfUtil.addErrorMessage(e, "");
/* 1428:1394 */       e.printStackTrace();
/* 1429:     */     }
/* 1430:1396 */     return null;
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   public String cerrarDialogoAjusteInventario()
/* 1434:     */   {
/* 1435:1400 */     this.mostradoDialogoAjusteInventario = false;
/* 1436:1401 */     this.ajusteInventarioEgreso = null;
/* 1437:1402 */     this.ajusteInventarioIngreso = null;
/* 1438:1403 */     return null;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public void crearTransportista()
/* 1442:     */   {
/* 1443:1407 */     this.transportista = new Transportista();
/* 1444:1408 */     this.transportista.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1445:1409 */     this.transportista.setIdSucursal(AppUtil.getSucursal().getId());
/* 1446:1410 */     this.transportista.setActivo(true);
/* 1447:1411 */     this.transportista.setDescripcion("");
/* 1448:1412 */     this.transportista.setDireccion("");
/* 1449:1413 */     this.transportista.setRendered(true);
/* 1450:     */   }
/* 1451:     */   
/* 1452:     */   public String guardarTrasportista()
/* 1453:     */   {
/* 1454:     */     try
/* 1455:     */     {
/* 1456:1418 */       ValidarIdentificacion.validarIdentificacion(getTransportista().getTipoIdentificacion().isIndicadorValidarIdentificacion(), 
/* 1457:1419 */         getTransportista().getIdentificacion());
/* 1458:1420 */       this.servicioTransportista.guardar(getTransportista());
/* 1459:1421 */       this.listaTransportistaCombo = null;
/* 1460:1422 */       getRegistroPeso().setTransportista(getTransportista());
/* 1461:1423 */       actualizarTransportista(true);
/* 1462:1424 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 1463:     */     }
/* 1464:     */     catch (ExcepcionAS2Identification e)
/* 1465:     */     {
/* 1466:1426 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1467:1427 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1468:     */     }
/* 1469:     */     catch (Exception e)
/* 1470:     */     {
/* 1471:1429 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 1472:1430 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 1473:     */     }
/* 1474:1432 */     return "";
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public List<TipoVehiculo> getListaTipoVehiculoCombo()
/* 1478:     */   {
/* 1479:1436 */     if (this.listaTipoVehiculoCombo == null) {
/* 1480:1437 */       this.listaTipoVehiculoCombo = this.servicioTipoVehiculo.obtenerListaCombo("codigo", true, null);
/* 1481:     */     }
/* 1482:1439 */     return this.listaTipoVehiculoCombo;
/* 1483:     */   }
/* 1484:     */   
/* 1485:     */   public List<Dispositivo> getListaDispositivo()
/* 1486:     */   {
/* 1487:1443 */     if (this.listaDispositivo == null)
/* 1488:     */     {
/* 1489:1444 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1490:1445 */       filtros.put("activo", "true");
/* 1491:1446 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 1492:     */     }
/* 1493:1448 */     return this.listaDispositivo;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public List<Chofer> getListaChoferCombo()
/* 1497:     */   {
/* 1498:1452 */     if ((this.listaChoferCombo == null) && (this.registroPeso != null) && (this.registroPeso.getTransportista() != null))
/* 1499:     */     {
/* 1500:1453 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1501:1454 */       filtros.put("transportista.idTransportista", this.registroPeso.getTransportista().getId() + "");
/* 1502:1455 */       this.listaChoferCombo = this.servicioChofer.obtenerListaCombo("nombre", true, filtros);
/* 1503:     */     }
/* 1504:1457 */     return this.listaChoferCombo;
/* 1505:     */   }
/* 1506:     */   
/* 1507:     */   public List<Vehiculo> getListaVehiculoCombo()
/* 1508:     */   {
/* 1509:1461 */     if ((this.listaVehiculoCombo == null) && (this.registroPeso != null) && (this.registroPeso.getTransportista() != null))
/* 1510:     */     {
/* 1511:1462 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1512:1463 */       filtros.put("transportista.idTransportista", this.registroPeso.getTransportista().getId() + "");
/* 1513:1464 */       this.listaVehiculoCombo = this.servicioVehiculo.obtenerListaCombo("placa", true, filtros);
/* 1514:     */     }
/* 1515:1466 */     return this.listaVehiculoCombo;
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public List<Documento> getListaDocumento()
/* 1519:     */   {
/* 1520:1470 */     if (this.listaDocumento == null) {
/* 1521:     */       try
/* 1522:     */       {
/* 1523:1472 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.REGISTRO_PESO, AppUtil.getOrganizacion().getId());
/* 1524:     */       }
/* 1525:     */       catch (ExcepcionAS2 e)
/* 1526:     */       {
/* 1527:1474 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1528:     */       }
/* 1529:     */     }
/* 1530:1477 */     return this.listaDocumento;
/* 1531:     */   }
/* 1532:     */   
/* 1533:     */   public List<TipoRegistroPeso> getListaTipoRegistroPeso()
/* 1534:     */   {
/* 1535:1481 */     if (this.listaTipoRegistroPeso == null)
/* 1536:     */     {
/* 1537:1482 */       this.listaTipoRegistroPeso = new ArrayList();
/* 1538:1483 */       for (TipoRegistroPeso tipoRegistroPeso : TipoRegistroPeso.values()) {
/* 1539:1484 */         this.listaTipoRegistroPeso.add(tipoRegistroPeso);
/* 1540:     */       }
/* 1541:     */     }
/* 1542:1487 */     return this.listaTipoRegistroPeso;
/* 1543:     */   }
/* 1544:     */   
/* 1545:     */   public List<Transportista> getListaTransportistaCombo()
/* 1546:     */   {
/* 1547:1491 */     if (this.listaTransportistaCombo == null) {
/* 1548:1492 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 1549:     */     }
/* 1550:1494 */     return this.listaTransportistaCombo;
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   public List<Bodega> getListaBodega()
/* 1554:     */   {
/* 1555:1498 */     if (this.listaBodga == null) {
/* 1556:1499 */       if ((this.registroPeso.getTipoRegistroPeso() != null) && (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) && 
/* 1557:1500 */         (this.registroPeso.getDetallePedidoProveedor() != null))
/* 1558:     */       {
/* 1559:1502 */         this.listaBodga = new ArrayList();
/* 1560:1503 */         for (Bodega bodega : AppUtil.getUsuarioEnSesion().getListaBodega()) {
/* 1561:1504 */           if (bodega.isIndicadorRecepcionBodega()) {
/* 1562:1505 */             this.listaBodga.add(bodega);
/* 1563:     */           }
/* 1564:     */         }
/* 1565:     */       }
/* 1566:1522 */       else if (((this.registroPeso.getTipoRegistroPeso() != null) && (
/* 1567:1523 */         (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.RECEPCION_TRANSFERENCIA)) || 
/* 1568:1524 */         (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)))) || 
/* 1569:1525 */         (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())))
/* 1570:     */       {
/* 1571:1526 */         this.listaBodga = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 1572:     */       }
/* 1573:     */     }
/* 1574:1530 */     return this.listaBodga;
/* 1575:     */   }
/* 1576:     */   
/* 1577:     */   public List<Documento> getListaDocumentosAjusteIngreso()
/* 1578:     */   {
/* 1579:1534 */     if (this.listaDocumentosAjusteIngreso == null) {
/* 1580:     */       try
/* 1581:     */       {
/* 1582:1536 */         this.listaDocumentosAjusteIngreso = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.AJUSTE_INVENTARIO, 
/* 1583:1537 */           AppUtil.getOrganizacion().getId(), Integer.valueOf(1));
/* 1584:     */       }
/* 1585:     */       catch (ExcepcionAS2 e)
/* 1586:     */       {
/* 1587:1539 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1588:     */       }
/* 1589:     */     }
/* 1590:1542 */     return this.listaDocumentosAjusteIngreso;
/* 1591:     */   }
/* 1592:     */   
/* 1593:     */   public List<Documento> getListaDocumentosAjusteEgreso()
/* 1594:     */   {
/* 1595:1546 */     if (this.listaDocumentosAjusteEgreso == null) {
/* 1596:     */       try
/* 1597:     */       {
/* 1598:1548 */         this.listaDocumentosAjusteEgreso = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.AJUSTE_INVENTARIO, 
/* 1599:1549 */           AppUtil.getOrganizacion().getId(), Integer.valueOf(-1));
/* 1600:     */       }
/* 1601:     */       catch (ExcepcionAS2 e)
/* 1602:     */       {
/* 1603:1551 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1604:     */       }
/* 1605:     */     }
/* 1606:1554 */     return this.listaDocumentosAjusteEgreso;
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   public List<Ruta> getListaRuta()
/* 1610:     */   {
/* 1611:1558 */     if ((this.listaRuta == null) && (this.registroPeso.getVehiculo() != null))
/* 1612:     */     {
/* 1613:1559 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 1614:1560 */       filters.put("tipoVehiculo.idTipoVehiculo", this.registroPeso.getVehiculo().getTipoVehiculo().getId() + "");
/* 1615:1561 */       List<String> listaCampos = new ArrayList();
/* 1616:1562 */       listaCampos.add("ciudadOrigen");
/* 1617:1563 */       listaCampos.add("ciudadDestino");
/* 1618:1564 */       this.listaRuta = this.servicioRuta.obtenerListaPorPagina(Ruta.class, 0, 10000, "ciudadOrigen", true, filters, listaCampos);
/* 1619:1567 */       if (this.registroPeso.getTipoRegistroPeso() != null) {
/* 1620:1569 */         for (Ruta ruta : this.listaRuta) {
/* 1621:1576 */           if ((this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) && (this.sucursal.getCiudad() != null) && 
/* 1622:1577 */             (this.registroPeso.getEmpresa() != null) && (this.direccionEmpresaSeleccionada != null) && 
/* 1623:1578 */             (ruta.getCiudadDestino().getId() == this.sucursal.getCiudad().getId()) && 
/* 1624:1579 */             (ruta.getCiudadOrigen().getId() == this.direccionEmpresaSeleccionada.getCiudad().getId()))
/* 1625:     */           {
/* 1626:1580 */             this.registroPeso.setRuta(ruta);
/* 1627:1581 */             break;
/* 1628:     */           }
/* 1629:     */         }
/* 1630:     */       }
/* 1631:     */     }
/* 1632:1587 */     return this.listaRuta;
/* 1633:     */   }
/* 1634:     */   
/* 1635:     */   public SelectItem[] getListaEstadoRegistroPesoItem()
/* 1636:     */   {
/* 1637:1591 */     if (this.listaEstadoRegistroPesoItem == null)
/* 1638:     */     {
/* 1639:1592 */       List<SelectItem> lista = new ArrayList();
/* 1640:1593 */       lista.add(new SelectItem("", ""));
/* 1641:1594 */       for (EstadoRegistroPeso estadoRegistroPeso : EstadoRegistroPeso.values()) {
/* 1642:1595 */         lista.add(new SelectItem(estadoRegistroPeso, estadoRegistroPeso.getNombre()));
/* 1643:     */       }
/* 1644:1597 */       this.listaEstadoRegistroPesoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 1645:     */     }
/* 1646:1599 */     return this.listaEstadoRegistroPesoItem;
/* 1647:     */   }
/* 1648:     */   
/* 1649:     */   public SelectItem[] getListaTipoRegistroPesoItem()
/* 1650:     */   {
/* 1651:1603 */     if (this.listaTipoRegistroPesoItem == null)
/* 1652:     */     {
/* 1653:1604 */       List<SelectItem> lista = new ArrayList();
/* 1654:1605 */       lista.add(new SelectItem("", ""));
/* 1655:1606 */       for (TipoRegistroPeso tipoRegistroPeso : TipoRegistroPeso.values()) {
/* 1656:1607 */         lista.add(new SelectItem(tipoRegistroPeso, tipoRegistroPeso.getNombre()));
/* 1657:     */       }
/* 1658:1609 */       this.listaTipoRegistroPesoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 1659:     */     }
/* 1660:1611 */     return this.listaTipoRegistroPesoItem;
/* 1661:     */   }
/* 1662:     */   
/* 1663:     */   public List<Empresa> autocompletarEmpresa(String consulta)
/* 1664:     */   {
/* 1665:1615 */     if ((this.registroPeso != null) && (this.registroPeso.getTipoRegistroPeso() != null) && (
/* 1666:1616 */       (this.registroPeso.getTipoRegistroPeso().equals(TipoRegistroPeso.DESPACHO_CLIENTE)) || 
/* 1667:1617 */       (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())))) {
/* 1668:1618 */       return this.servicioEmpresa.autocompletarClientes(consulta, true);
/* 1669:     */     }
/* 1670:1620 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true);
/* 1671:     */   }
/* 1672:     */   
/* 1673:     */   public List<MotivoAjusteInventario> autocompletarMotivoAjusteInventario(String consulta)
/* 1674:     */   {
/* 1675:1624 */     consulta = consulta.toUpperCase();
/* 1676:1625 */     return this.servicioMotivoAjusteInventario.autoCompletarMotivoAjusteInventario(consulta);
/* 1677:     */   }
/* 1678:     */   
/* 1679:     */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(String consulta)
/* 1680:     */   {
/* 1681:1629 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 1682:1630 */     List<OrdenSalidaMaterial> lista = new ArrayList();
/* 1683:1631 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 1684:1632 */       filters.put("numero", "%" + consulta);
/* 1685:     */     }
/* 1686:1634 */     filters.put("estado", Estado.REALIZADO_LOGISTICA.name());
/* 1687:1635 */     lista = this.servicioOrdenSalidaMaterial.obtenerListaCombo("numero", true, filters);
/* 1688:1636 */     return lista;
/* 1689:     */   }
/* 1690:     */   
/* 1691:     */   public List<String> autocompletarCargaPrevia(String consulta)
/* 1692:     */   {
/* 1693:1640 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1694:1641 */     filtros.put("nombre", "%" + consulta + "%");
/* 1695:1642 */     List<CargaPreviaTransportista> lista = this.servicioCargaPreviaTransportista.obtenerListaCombo(CargaPreviaTransportista.class, "nombre", true, filtros);
/* 1696:     */     
/* 1697:1644 */     List<String> listaNombre = new ArrayList();
/* 1698:1645 */     for (CargaPreviaTransportista cargaPrevia : lista) {
/* 1699:1646 */       listaNombre.add(cargaPrevia.getNombre());
/* 1700:     */     }
/* 1701:1648 */     return listaNombre;
/* 1702:     */   }
/* 1703:     */   
/* 1704:     */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/* 1705:     */   {
/* 1706:1652 */     Map<String, String> filters = new HashMap();
/* 1707:1653 */     List<FacturaCliente> lista = new ArrayList();
/* 1708:1654 */     if (getRegistroPeso().getEmpresa() != null)
/* 1709:     */     {
/* 1710:1655 */       filters.put("empresa.idEmpresa", "" + getRegistroPeso().getEmpresa().getId());
/* 1711:1656 */       if ((consulta != null) && (!consulta.isEmpty())) {
/* 1712:1657 */         filters.put("numero", "%" + consulta);
/* 1713:     */       }
/* 1714:1659 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.name());
/* 1715:1660 */       filters.put("despachoCliente", "!=" + null);
/* 1716:1661 */       filters.put("despachoCliente.idDespachoCliente", ">0");
/* 1717:1663 */       if (!ParametrosSistema.getRealizaNotasCreditoAFacturaNoAutorizada(AppUtil.getOrganizacion().getId()).booleanValue()) {
/* 1718:1664 */         filters.put("facturaClienteSRI.autorizacion", "!=0000000000");
/* 1719:     */       }
/* 1720:1666 */       lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/* 1721:     */     }
/* 1722:     */     else
/* 1723:     */     {
/* 1724:1668 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 1725:     */     }
/* 1726:1670 */     return lista;
/* 1727:     */   }
/* 1728:     */   
/* 1729:     */   public List<Lote> autocompletarLotes(String consulta)
/* 1730:     */   {
/* 1731:1674 */     DetalleRegistroPesoLote detalle = (DetalleRegistroPesoLote)this.dtDetalleRegistroPeso.getRowData();
/* 1732:1675 */     return this.servicioLote.autocompletarLote(detalle.getProducto(), consulta);
/* 1733:     */   }
/* 1734:     */   
/* 1735:     */   public RegistroPeso getRegistroPeso()
/* 1736:     */   {
/* 1737:1679 */     return this.registroPeso;
/* 1738:     */   }
/* 1739:     */   
/* 1740:     */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 1741:     */   {
/* 1742:1683 */     this.registroPeso = registroPeso;
/* 1743:     */   }
/* 1744:     */   
/* 1745:     */   public LazyDataModel<RegistroPeso> getListaRegistroPeso()
/* 1746:     */   {
/* 1747:1687 */     return this.listaRegistroPeso;
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   public void setListaRegistroPeso(LazyDataModel<RegistroPeso> listaRegistroPeso)
/* 1751:     */   {
/* 1752:1691 */     this.listaRegistroPeso = listaRegistroPeso;
/* 1753:     */   }
/* 1754:     */   
/* 1755:     */   public DataTable getDtRegistroPeso()
/* 1756:     */   {
/* 1757:1695 */     return this.dtRegistroPeso;
/* 1758:     */   }
/* 1759:     */   
/* 1760:     */   public void setDtRegistroPeso(DataTable dtRegistroPeso)
/* 1761:     */   {
/* 1762:1699 */     this.dtRegistroPeso = dtRegistroPeso;
/* 1763:     */   }
/* 1764:     */   
/* 1765:     */   public Date getFechaDesde()
/* 1766:     */   {
/* 1767:1703 */     return this.fechaDesde;
/* 1768:     */   }
/* 1769:     */   
/* 1770:     */   public void setFechaDesde(Date fechaDesde)
/* 1771:     */   {
/* 1772:1707 */     this.fechaDesde = fechaDesde;
/* 1773:     */   }
/* 1774:     */   
/* 1775:     */   public Date getFechaHasta()
/* 1776:     */   {
/* 1777:1711 */     return this.fechaHasta;
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public void setFechaHasta(Date fechaHasta)
/* 1781:     */   {
/* 1782:1715 */     this.fechaHasta = fechaHasta;
/* 1783:     */   }
/* 1784:     */   
/* 1785:     */   public List<PedidoProveedor> getListaPedidoProveedor()
/* 1786:     */   {
/* 1787:1719 */     return this.listaPedidoProveedor;
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public void setListaPedidoProveedor(List<PedidoProveedor> listaPedidoProveedor)
/* 1791:     */   {
/* 1792:1723 */     this.listaPedidoProveedor = listaPedidoProveedor;
/* 1793:     */   }
/* 1794:     */   
/* 1795:     */   public List<DetallePedidoProveedor> getListaDetallePedidoProveedor()
/* 1796:     */   {
/* 1797:1727 */     return this.listaDetallePedidoProveedor;
/* 1798:     */   }
/* 1799:     */   
/* 1800:     */   public void setListaDetallePedidoProveedor(List<DetallePedidoProveedor> listaDetallePedidoProveedor)
/* 1801:     */   {
/* 1802:1731 */     this.listaDetallePedidoProveedor = listaDetallePedidoProveedor;
/* 1803:     */   }
/* 1804:     */   
/* 1805:     */   public DataTable getDtDetallePedidoProveedor()
/* 1806:     */   {
/* 1807:1735 */     return this.dtDetallePedidoProveedor;
/* 1808:     */   }
/* 1809:     */   
/* 1810:     */   public void setDtDetallePedidoProveedor(DataTable dtDetallePedidoProveedor)
/* 1811:     */   {
/* 1812:1739 */     this.dtDetallePedidoProveedor = dtDetallePedidoProveedor;
/* 1813:     */   }
/* 1814:     */   
/* 1815:     */   public CargaPreviaTransportista getCargaPrevia()
/* 1816:     */   {
/* 1817:1743 */     return this.cargaPrevia;
/* 1818:     */   }
/* 1819:     */   
/* 1820:     */   public void setCargaPrevia(CargaPreviaTransportista cargaPrevia)
/* 1821:     */   {
/* 1822:1747 */     this.cargaPrevia = cargaPrevia;
/* 1823:     */   }
/* 1824:     */   
/* 1825:     */   public PedidoProveedor getPedidoProveedor()
/* 1826:     */   {
/* 1827:1751 */     return this.pedidoProveedor;
/* 1828:     */   }
/* 1829:     */   
/* 1830:     */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 1831:     */   {
/* 1832:1755 */     this.pedidoProveedor = pedidoProveedor;
/* 1833:     */   }
/* 1834:     */   
/* 1835:     */   public BigDecimal getPesoActual()
/* 1836:     */   {
/* 1837:1759 */     return this.pesoActual;
/* 1838:     */   }
/* 1839:     */   
/* 1840:     */   public void setPesoActual(BigDecimal pesoActual)
/* 1841:     */   {
/* 1842:1763 */     this.pesoActual = pesoActual;
/* 1843:     */   }
/* 1844:     */   
/* 1845:     */   public Integer getIntervaloRefrescarPesoBalanza()
/* 1846:     */   {
/* 1847:1767 */     if (this.intervaloRefrescarPesoBalanza == null) {
/* 1848:1768 */       this.intervaloRefrescarPesoBalanza = ParametrosSistema.getIntervaloRefrescarPesoBalanza(AppUtil.getOrganizacion().getId());
/* 1849:     */     }
/* 1850:1770 */     return this.intervaloRefrescarPesoBalanza;
/* 1851:     */   }
/* 1852:     */   
/* 1853:     */   public List<String> getListaCargaPrevia()
/* 1854:     */   {
/* 1855:1774 */     return this.listaCargaPrevia;
/* 1856:     */   }
/* 1857:     */   
/* 1858:     */   public void setListaCargaPrevia(List<String> listaCargaPrevia)
/* 1859:     */   {
/* 1860:1778 */     this.listaCargaPrevia = listaCargaPrevia;
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   public boolean isIndicadorEditarParcial()
/* 1864:     */   {
/* 1865:1782 */     return this.indicadorEditarParcial;
/* 1866:     */   }
/* 1867:     */   
/* 1868:     */   public void setIndicadorEditarParcial(boolean indicadorEditarParcial)
/* 1869:     */   {
/* 1870:1786 */     this.indicadorEditarParcial = indicadorEditarParcial;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   public List<MovimientoInventario> getListaTransferenciaBodega()
/* 1874:     */   {
/* 1875:1790 */     return this.listaTransferenciaBodega;
/* 1876:     */   }
/* 1877:     */   
/* 1878:     */   public void setListaTransferenciaBodega(List<MovimientoInventario> listaTransferenciaBodega)
/* 1879:     */   {
/* 1880:1794 */     this.listaTransferenciaBodega = listaTransferenciaBodega;
/* 1881:     */   }
/* 1882:     */   
/* 1883:     */   public MovimientoInventario getTransferenciaBodega()
/* 1884:     */   {
/* 1885:1798 */     return this.transferenciaBodega;
/* 1886:     */   }
/* 1887:     */   
/* 1888:     */   public void setTransferenciaBodega(MovimientoInventario transferenciaBodega)
/* 1889:     */   {
/* 1890:1802 */     this.transferenciaBodega = transferenciaBodega;
/* 1891:     */   }
/* 1892:     */   
/* 1893:     */   public List<DetalleMovimientoInventario> getListaDetalleTransferenciaBodega()
/* 1894:     */   {
/* 1895:1806 */     return this.listaDetalleTransferenciaBodega;
/* 1896:     */   }
/* 1897:     */   
/* 1898:     */   public void setListaDetalleTransferenciaBodega(List<DetalleMovimientoInventario> listaDetalleTransferenciaBodega)
/* 1899:     */   {
/* 1900:1810 */     this.listaDetalleTransferenciaBodega = listaDetalleTransferenciaBodega;
/* 1901:     */   }
/* 1902:     */   
/* 1903:     */   public DataTable getDtDetalleTransferenciaBodega()
/* 1904:     */   {
/* 1905:1814 */     return this.dtDetalleTransferenciaBodega;
/* 1906:     */   }
/* 1907:     */   
/* 1908:     */   public void setDtDetalleTransferenciaBodega(DataTable dtDetalleTransferenciaBodega)
/* 1909:     */   {
/* 1910:1818 */     this.dtDetalleTransferenciaBodega = dtDetalleTransferenciaBodega;
/* 1911:     */   }
/* 1912:     */   
/* 1913:     */   public DataTable getDtDetallePedidoCliente()
/* 1914:     */   {
/* 1915:1822 */     return this.dtDetallePedidoCliente;
/* 1916:     */   }
/* 1917:     */   
/* 1918:     */   public void setDtDetallePedidoCliente(DataTable dtDetallePedidoCliente)
/* 1919:     */   {
/* 1920:1826 */     this.dtDetallePedidoCliente = dtDetallePedidoCliente;
/* 1921:     */   }
/* 1922:     */   
/* 1923:     */   public List<PedidoCliente> getListaPedidoCliente()
/* 1924:     */   {
/* 1925:1830 */     return this.listaPedidoCliente;
/* 1926:     */   }
/* 1927:     */   
/* 1928:     */   public void setListaPedidoCliente(List<PedidoCliente> listaPedidoCliente)
/* 1929:     */   {
/* 1930:1834 */     this.listaPedidoCliente = listaPedidoCliente;
/* 1931:     */   }
/* 1932:     */   
/* 1933:     */   public List<DetallePedidoCliente> getListaDetallePedidoCliente()
/* 1934:     */   {
/* 1935:1838 */     return this.listaDetallePedidoCliente;
/* 1936:     */   }
/* 1937:     */   
/* 1938:     */   public void setListaDetallePedidoCliente(List<DetallePedidoCliente> listaDetallePedidoCliente)
/* 1939:     */   {
/* 1940:1842 */     this.listaDetallePedidoCliente = listaDetallePedidoCliente;
/* 1941:     */   }
/* 1942:     */   
/* 1943:     */   public PedidoCliente getPedidoCliente()
/* 1944:     */   {
/* 1945:1846 */     return this.pedidoCliente;
/* 1946:     */   }
/* 1947:     */   
/* 1948:     */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 1949:     */   {
/* 1950:1850 */     this.pedidoCliente = pedidoCliente;
/* 1951:     */   }
/* 1952:     */   
/* 1953:     */   public DataTable getDtDetalleRegistroPeso()
/* 1954:     */   {
/* 1955:1854 */     return this.dtDetalleRegistroPeso;
/* 1956:     */   }
/* 1957:     */   
/* 1958:     */   public void setDtDetalleRegistroPeso(DataTable dtDetalleRegistroPeso)
/* 1959:     */   {
/* 1960:1858 */     this.dtDetalleRegistroPeso = dtDetalleRegistroPeso;
/* 1961:     */   }
/* 1962:     */   
/* 1963:     */   public void actualizarVehiculo()
/* 1964:     */   {
/* 1965:1862 */     this.listaRuta = null;
/* 1966:     */   }
/* 1967:     */   
/* 1968:     */   public List<DetallePedidoCliente> getListaDetallePedidoClienteSeleccionados()
/* 1969:     */   {
/* 1970:1866 */     if (this.listaDetallePedidoClienteSeleccionados == null) {
/* 1971:1867 */       this.listaDetallePedidoClienteSeleccionados = new ArrayList();
/* 1972:     */     }
/* 1973:1869 */     return this.listaDetallePedidoClienteSeleccionados;
/* 1974:     */   }
/* 1975:     */   
/* 1976:     */   public void setListaDetallePedidoClienteSeleccionados(List<DetallePedidoCliente> listaDetallePedidoClienteSeleccionados)
/* 1977:     */   {
/* 1978:1873 */     this.listaDetallePedidoClienteSeleccionados = listaDetallePedidoClienteSeleccionados;
/* 1979:     */   }
/* 1980:     */   
/* 1981:     */   public boolean isindicadorLote()
/* 1982:     */   {
/* 1983:1877 */     if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1984:1878 */       for (DetallePedidoCliente dpc : getListaDetallePedidoClienteSeleccionados()) {
/* 1985:1879 */         if (dpc.getProducto().isIndicadorLote()) {
/* 1986:1880 */           return true;
/* 1987:     */         }
/* 1988:     */       }
/* 1989:1883 */     } else if (TipoRegistroPeso.DESPACHO_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1990:1884 */       for (DetalleOrdenSalidaMaterial dpc : getListaDetalleOrdenSalidaMaterialSeleccionados()) {
/* 1991:1885 */         if (dpc.getProducto().isIndicadorLote()) {
/* 1992:1886 */           return true;
/* 1993:     */         }
/* 1994:     */       }
/* 1995:1889 */     } else if (TipoRegistroPeso.DEVOLUCION_CLIENTE.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 1996:1890 */       for (DetalleFacturaCliente dfc : getListaDetalleFacturaClienteSeleccionados()) {
/* 1997:1891 */         if (dfc.getProducto().isIndicadorLote()) {
/* 1998:1892 */           return true;
/* 1999:     */         }
/* 2000:     */       }
/* 2001:1895 */     } else if (TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS.equals(this.registroPeso.getTipoRegistroPeso())) {
/* 2002:1896 */       for (DetalleOrdenSalidaMaterial dosm : getListaDetalleOrdenSalidaMaterialSeleccionados()) {
/* 2003:1897 */         if (dosm.getProducto().isIndicadorLote()) {
/* 2004:1898 */           return true;
/* 2005:     */         }
/* 2006:     */       }
/* 2007:     */     }
/* 2008:1902 */     return false;
/* 2009:     */   }
/* 2010:     */   
/* 2011:     */   public Vehiculo getVehiculo()
/* 2012:     */   {
/* 2013:1906 */     if (this.vehiculo == null) {
/* 2014:1907 */       this.vehiculo = new Vehiculo();
/* 2015:     */     }
/* 2016:1908 */     return this.vehiculo;
/* 2017:     */   }
/* 2018:     */   
/* 2019:     */   public void setVehiculo(Vehiculo vehiculo)
/* 2020:     */   {
/* 2021:1912 */     this.vehiculo = vehiculo;
/* 2022:     */   }
/* 2023:     */   
/* 2024:     */   public Integer getIdRegistroPeso()
/* 2025:     */   {
/* 2026:1916 */     return this.idRegistroPeso;
/* 2027:     */   }
/* 2028:     */   
/* 2029:     */   public void setIdRegistroPeso(Integer idRegistroPeso)
/* 2030:     */   {
/* 2031:1920 */     this.idRegistroPeso = idRegistroPeso;
/* 2032:     */   }
/* 2033:     */   
/* 2034:     */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 2035:     */   {
/* 2036:1924 */     return this.ordenSalidaMaterial;
/* 2037:     */   }
/* 2038:     */   
/* 2039:     */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 2040:     */   {
/* 2041:1928 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 2042:     */   }
/* 2043:     */   
/* 2044:     */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrdenSalidaMaterial()
/* 2045:     */   {
/* 2046:1932 */     return this.listaDetalleOrdenSalidaMaterial;
/* 2047:     */   }
/* 2048:     */   
/* 2049:     */   public void setListaDetalleOrdenSalidaMaterial(List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterial)
/* 2050:     */   {
/* 2051:1936 */     this.listaDetalleOrdenSalidaMaterial = listaDetalleOrdenSalidaMaterial;
/* 2052:     */   }
/* 2053:     */   
/* 2054:     */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrdenSalidaMaterialSeleccionados()
/* 2055:     */   {
/* 2056:1940 */     if (this.listaDetalleOrdenSalidaMaterialSeleccionados == null) {
/* 2057:1941 */       this.listaDetalleOrdenSalidaMaterialSeleccionados = new ArrayList();
/* 2058:     */     }
/* 2059:1943 */     return this.listaDetalleOrdenSalidaMaterialSeleccionados;
/* 2060:     */   }
/* 2061:     */   
/* 2062:     */   public void setListaDetalleOrdenSalidaMaterialSeleccionados(List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterialSeleccionados)
/* 2063:     */   {
/* 2064:1947 */     this.listaDetalleOrdenSalidaMaterialSeleccionados = listaDetalleOrdenSalidaMaterialSeleccionados;
/* 2065:     */   }
/* 2066:     */   
/* 2067:     */   public DataTable getDtDetalleOrdenSalidaMaterial()
/* 2068:     */   {
/* 2069:1951 */     return this.dtDetalleOrdenSalidaMaterial;
/* 2070:     */   }
/* 2071:     */   
/* 2072:     */   public void setDtDetalleOrdenSalidaMaterial(DataTable dtDetalleOrdenSalidaMaterial)
/* 2073:     */   {
/* 2074:1955 */     this.dtDetalleOrdenSalidaMaterial = dtDetalleOrdenSalidaMaterial;
/* 2075:     */   }
/* 2076:     */   
/* 2077:     */   public FacturaCliente getFacturaCliente()
/* 2078:     */   {
/* 2079:1959 */     return this.facturaCliente;
/* 2080:     */   }
/* 2081:     */   
/* 2082:     */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 2083:     */   {
/* 2084:1963 */     this.facturaCliente = facturaCliente;
/* 2085:     */   }
/* 2086:     */   
/* 2087:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaClienteSeleccionados()
/* 2088:     */   {
/* 2089:1967 */     if (this.listaDetalleFacturaClienteSeleccionados == null) {
/* 2090:1968 */       this.listaDetalleFacturaClienteSeleccionados = new ArrayList();
/* 2091:     */     }
/* 2092:1970 */     return this.listaDetalleFacturaClienteSeleccionados;
/* 2093:     */   }
/* 2094:     */   
/* 2095:     */   public void setListaDetalleFacturaClienteSeleccionados(List<DetalleFacturaCliente> listaDetalleFacturaClienteSeleccionados)
/* 2096:     */   {
/* 2097:1974 */     this.listaDetalleFacturaClienteSeleccionados = listaDetalleFacturaClienteSeleccionados;
/* 2098:     */   }
/* 2099:     */   
/* 2100:     */   public DataTable getDtDetalleFacturaCliente()
/* 2101:     */   {
/* 2102:1978 */     return this.dtDetalleFacturaCliente;
/* 2103:     */   }
/* 2104:     */   
/* 2105:     */   public void setDtDetalleFacturaCliente(DataTable dtDetalleFacturaCliente)
/* 2106:     */   {
/* 2107:1982 */     this.dtDetalleFacturaCliente = dtDetalleFacturaCliente;
/* 2108:     */   }
/* 2109:     */   
/* 2110:     */   public List<DetalleFacturaCliente> getListaDetalleFacturaCliente()
/* 2111:     */   {
/* 2112:1986 */     return this.listaDetalleFacturaCliente;
/* 2113:     */   }
/* 2114:     */   
/* 2115:     */   public void setListaDetalleFacturaCliente(List<DetalleFacturaCliente> listaDetalleFacturaCliente)
/* 2116:     */   {
/* 2117:1990 */     this.listaDetalleFacturaCliente = listaDetalleFacturaCliente;
/* 2118:     */   }
/* 2119:     */   
/* 2120:     */   public String getUnidadProducto(int idProducto)
/* 2121:     */   {
/* 2122:1994 */     return (String)this.mapaUnidades.get(Integer.valueOf(idProducto));
/* 2123:     */   }
/* 2124:     */   
/* 2125:     */   public BigDecimal getPesoProducto(Producto producto, BigDecimal cantidad)
/* 2126:     */   {
/* 2127:1998 */     BigDecimal pesoCalculado = cantidad;
/* 2128:1999 */     if ((producto.getPeso() != null) && (producto.getPeso().compareTo(BigDecimal.ZERO) != 0)) {
/* 2129:2000 */       pesoCalculado = producto.getPeso().multiply(cantidad);
/* 2130:     */     }
/* 2131:2002 */     return FuncionesUtiles.redondearBigDecimal(pesoCalculado);
/* 2132:     */   }
/* 2133:     */   
/* 2134:     */   public Transportista getTransportista()
/* 2135:     */   {
/* 2136:2006 */     if (this.transportista == null) {
/* 2137:2007 */       this.transportista = new Transportista();
/* 2138:     */     }
/* 2139:2009 */     return this.transportista;
/* 2140:     */   }
/* 2141:     */   
/* 2142:     */   public void setTransportista(Transportista transportista)
/* 2143:     */   {
/* 2144:2013 */     this.transportista = transportista;
/* 2145:     */   }
/* 2146:     */   
/* 2147:     */   public MovimientoInventario getAjusteInventarioIngreso()
/* 2148:     */   {
/* 2149:2017 */     return this.ajusteInventarioIngreso;
/* 2150:     */   }
/* 2151:     */   
/* 2152:     */   public void setAjusteInventarioIngreso(MovimientoInventario ajusteInventarioIngreso)
/* 2153:     */   {
/* 2154:2021 */     this.ajusteInventarioIngreso = ajusteInventarioIngreso;
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   public MovimientoInventario getAjusteInventarioEgreso()
/* 2158:     */   {
/* 2159:2025 */     return this.ajusteInventarioEgreso;
/* 2160:     */   }
/* 2161:     */   
/* 2162:     */   public void setAjusteInventarioEgreso(MovimientoInventario ajusteInventarioEgreso)
/* 2163:     */   {
/* 2164:2029 */     this.ajusteInventarioEgreso = ajusteInventarioEgreso;
/* 2165:     */   }
/* 2166:     */   
/* 2167:     */   public boolean isMostradoDialogoAjusteInventario()
/* 2168:     */   {
/* 2169:2033 */     return this.mostradoDialogoAjusteInventario;
/* 2170:     */   }
/* 2171:     */   
/* 2172:     */   public void setMostradoDialogoAjusteInventario(boolean mostradoDialogoAjusteInventario)
/* 2173:     */   {
/* 2174:2037 */     this.mostradoDialogoAjusteInventario = mostradoDialogoAjusteInventario;
/* 2175:     */   }
/* 2176:     */   
/* 2177:     */   public Chofer getChofer()
/* 2178:     */   {
/* 2179:2041 */     if (this.chofer == null) {
/* 2180:2042 */       this.chofer = new Chofer();
/* 2181:     */     }
/* 2182:2044 */     return this.chofer;
/* 2183:     */   }
/* 2184:     */   
/* 2185:     */   public void setChofer(Chofer chofer)
/* 2186:     */   {
/* 2187:2048 */     this.chofer = chofer;
/* 2188:     */   }
/* 2189:     */   
/* 2190:     */   public String getMensajeConfirmacion()
/* 2191:     */   {
/* 2192:2052 */     return this.mensajeConfirmacion;
/* 2193:     */   }
/* 2194:     */   
/* 2195:     */   public BigDecimal getTotalCantidadPedidoProveedor()
/* 2196:     */   {
/* 2197:2057 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2198:2058 */     if (getListaDetallePedidoProveedor() != null) {
/* 2199:2059 */       for (DetallePedidoProveedor detallePedidoProveedor : getListaDetallePedidoProveedor()) {
/* 2200:2060 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2201:2061 */           cantidad = cantidad.add(detallePedidoProveedor.getCantidad());
/* 2202:     */         }
/* 2203:     */       }
/* 2204:     */     }
/* 2205:2065 */     return cantidad;
/* 2206:     */   }
/* 2207:     */   
/* 2208:     */   public BigDecimal getTotalPesoPedidoProveedor()
/* 2209:     */   {
/* 2210:2069 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2211:2070 */     if (getListaDetallePedidoProveedor() != null) {
/* 2212:2071 */       for (DetallePedidoProveedor detallePedidoProveedor : getListaDetallePedidoProveedor()) {
/* 2213:2072 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2214:2073 */           cantidad = cantidad.add(getPesoProducto(detallePedidoProveedor.getProducto(), detallePedidoProveedor.getCantidad()));
/* 2215:     */         }
/* 2216:     */       }
/* 2217:     */     }
/* 2218:2077 */     return cantidad;
/* 2219:     */   }
/* 2220:     */   
/* 2221:     */   public BigDecimal getTotalCantidadPorRecibirPedidoProveedor()
/* 2222:     */   {
/* 2223:2081 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2224:2083 */     if (getListaDetallePedidoProveedor() != null) {
/* 2225:2084 */       for (DetallePedidoProveedor detallePedidoProveedor : getListaDetallePedidoProveedor()) {
/* 2226:2085 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2227:2086 */           cantidad = cantidad.add(detallePedidoProveedor.getCantidadPorRecibir());
/* 2228:     */         }
/* 2229:     */       }
/* 2230:     */     }
/* 2231:2090 */     return cantidad;
/* 2232:     */   }
/* 2233:     */   
/* 2234:     */   public BigDecimal getTotalPesoPorRecibirPedidoProveedor()
/* 2235:     */   {
/* 2236:2094 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2237:2095 */     if (getListaDetallePedidoProveedor() != null) {
/* 2238:2096 */       for (DetallePedidoProveedor detallePedidoProveedor : getListaDetallePedidoProveedor()) {
/* 2239:2097 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2240:2098 */           cantidad = cantidad.add(getPesoProducto(detallePedidoProveedor.getProducto(), detallePedidoProveedor.getCantidadPorRecibir()));
/* 2241:     */         }
/* 2242:     */       }
/* 2243:     */     }
/* 2244:2102 */     return cantidad;
/* 2245:     */   }
/* 2246:     */   
/* 2247:     */   public BigDecimal getTotalCantidadTransferencia()
/* 2248:     */   {
/* 2249:2107 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2250:2108 */     if (getListaDetalleTransferenciaBodega() != null) {
/* 2251:2109 */       for (DetalleMovimientoInventario detalleTransferencia : getListaDetalleTransferenciaBodega()) {
/* 2252:2110 */         if (!detalleTransferencia.isEliminado()) {
/* 2253:2111 */           cantidad = cantidad.add(detalleTransferencia.getCantidad());
/* 2254:     */         }
/* 2255:     */       }
/* 2256:     */     }
/* 2257:2115 */     return cantidad;
/* 2258:     */   }
/* 2259:     */   
/* 2260:     */   public BigDecimal getTotalPesoTransferencia()
/* 2261:     */   {
/* 2262:2119 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2263:2120 */     if (getListaDetalleTransferenciaBodega() != null) {
/* 2264:2121 */       for (DetalleMovimientoInventario detalleTransferencia : getListaDetalleTransferenciaBodega()) {
/* 2265:2122 */         if (!detalleTransferencia.isEliminado()) {
/* 2266:2123 */           cantidad = cantidad.add(detalleTransferencia.getPesoProducto());
/* 2267:     */         }
/* 2268:     */       }
/* 2269:     */     }
/* 2270:2127 */     return cantidad;
/* 2271:     */   }
/* 2272:     */   
/* 2273:     */   public BigDecimal getTotalCantidadPedidoCliente()
/* 2274:     */   {
/* 2275:2132 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2276:2133 */     if (getListaDetallePedidoCliente() != null) {
/* 2277:2134 */       for (DetallePedidoCliente detallePedidoProveedor : getListaDetallePedidoCliente()) {
/* 2278:2135 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2279:2136 */           cantidad = cantidad.add(detallePedidoProveedor.getCantidad());
/* 2280:     */         }
/* 2281:     */       }
/* 2282:     */     }
/* 2283:2140 */     return cantidad;
/* 2284:     */   }
/* 2285:     */   
/* 2286:     */   public BigDecimal getTotalPesoPedidoCliente()
/* 2287:     */   {
/* 2288:2144 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2289:2145 */     if (getListaDetallePedidoCliente() != null) {
/* 2290:2146 */       for (DetallePedidoCliente detallePedidoProveedor : getListaDetallePedidoCliente()) {
/* 2291:2147 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2292:2148 */           cantidad = cantidad.add(getPesoProducto(detallePedidoProveedor.getProducto(), detallePedidoProveedor.getCantidad()));
/* 2293:     */         }
/* 2294:     */       }
/* 2295:     */     }
/* 2296:2152 */     return cantidad;
/* 2297:     */   }
/* 2298:     */   
/* 2299:     */   public BigDecimal getTotalCantidadPorRecibirPedidoCliente()
/* 2300:     */   {
/* 2301:2156 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2302:2158 */     if (getListaDetallePedidoCliente() != null) {
/* 2303:2159 */       for (DetallePedidoCliente detallePedidoProveedor : getListaDetallePedidoCliente()) {
/* 2304:2160 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2305:2161 */           cantidad = cantidad.add(detallePedidoProveedor.getCantidadPorDespachar());
/* 2306:     */         }
/* 2307:     */       }
/* 2308:     */     }
/* 2309:2165 */     return cantidad;
/* 2310:     */   }
/* 2311:     */   
/* 2312:     */   public BigDecimal getTotalPesoPorRecibirPedidoCliente()
/* 2313:     */   {
/* 2314:2169 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2315:2170 */     if (getListaDetallePedidoCliente() != null) {
/* 2316:2171 */       for (DetallePedidoCliente detallePedidoProveedor : getListaDetallePedidoCliente()) {
/* 2317:2172 */         if (!detallePedidoProveedor.isEliminado()) {
/* 2318:2173 */           cantidad = cantidad.add(getPesoProducto(detallePedidoProveedor.getProducto(), detallePedidoProveedor.getCantidadPorDespachar()));
/* 2319:     */         }
/* 2320:     */       }
/* 2321:     */     }
/* 2322:2177 */     return cantidad;
/* 2323:     */   }
/* 2324:     */   
/* 2325:     */   public BigDecimal getTotalCantidadOrdenSalidaMaterial()
/* 2326:     */   {
/* 2327:2182 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2328:2183 */     if (getListaDetalleOrdenSalidaMaterial() != null) {
/* 2329:2184 */       for (DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial : getListaDetalleOrdenSalidaMaterial()) {
/* 2330:2185 */         if (!detalleOrdenSalidaMaterial.isEliminado()) {
/* 2331:2186 */           cantidad = cantidad.add(detalleOrdenSalidaMaterial.getCantidad());
/* 2332:     */         }
/* 2333:     */       }
/* 2334:     */     }
/* 2335:2190 */     return cantidad;
/* 2336:     */   }
/* 2337:     */   
/* 2338:     */   public BigDecimal getTotalPesoOrdenSalidaMaterial()
/* 2339:     */   {
/* 2340:2194 */     BigDecimal cantidad = BigDecimal.ZERO;
/* 2341:2195 */     if (getListaDetalleOrdenSalidaMaterial() != null) {
/* 2342:2196 */       for (DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial : getListaDetalleOrdenSalidaMaterial()) {
/* 2343:2197 */         if (!detalleOrdenSalidaMaterial.isEliminado()) {
/* 2344:2198 */           cantidad = cantidad.add(getPesoProducto(detalleOrdenSalidaMaterial.getProducto(), detalleOrdenSalidaMaterial.getCantidad()));
/* 2345:     */         }
/* 2346:     */       }
/* 2347:     */     }
/* 2348:2202 */     return cantidad;
/* 2349:     */   }
/* 2350:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.RegistroPesoBean
 * JD-Core Version:    0.7.0.1
 */