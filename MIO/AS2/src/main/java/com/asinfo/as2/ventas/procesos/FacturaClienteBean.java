/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*    4:     */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    9:     */ import com.asinfo.as2.controller.LanguageController;
/*   10:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   11:     */ import com.asinfo.as2.entities.AjustePrefacturaCliente;
/*   12:     */ import com.asinfo.as2.entities.Ciudad;
/*   13:     */ import com.asinfo.as2.entities.Cliente;
/*   14:     */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   15:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleAjustePrefacturaCliente;
/*   17:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   18:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   19:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   20:     */ import com.asinfo.as2.entities.Empresa;
/*   21:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   22:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   23:     */ import com.asinfo.as2.entities.Organizacion;
/*   24:     */ import com.asinfo.as2.entities.Pais;
/*   25:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   26:     */ import com.asinfo.as2.entities.PrefacturaCliente;
/*   27:     */ import com.asinfo.as2.entities.Producto;
/*   28:     */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   29:     */ import com.asinfo.as2.entities.Subempresa;
/*   30:     */ import com.asinfo.as2.entities.Sucursal;
/*   31:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   32:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   33:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   34:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   35:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   36:     */ import com.asinfo.as2.enumeraciones.RefrendoEnum;
/*   37:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   38:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   39:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   40:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   41:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   42:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   43:     */ import com.asinfo.as2.util.AppUtil;
/*   44:     */ import com.asinfo.as2.util.RutaArchivo;
/*   45:     */ import com.asinfo.as2.utils.DatosSRI;
/*   46:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   47:     */ import com.asinfo.as2.utils.JsfUtil;
/*   48:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   49:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   50:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   51:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   52:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   53:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
/*   54:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   55:     */ import java.io.BufferedInputStream;
/*   56:     */ import java.io.File;
/*   57:     */ import java.io.FileNotFoundException;
/*   58:     */ import java.io.InputStream;
/*   59:     */ import java.io.PrintStream;
/*   60:     */ import java.math.BigDecimal;
/*   61:     */ import java.util.ArrayList;
/*   62:     */ import java.util.HashMap;
/*   63:     */ import java.util.HashSet;
/*   64:     */ import java.util.Iterator;
/*   65:     */ import java.util.List;
/*   66:     */ import java.util.Map;
/*   67:     */ import java.util.Set;
/*   68:     */ import javax.annotation.PostConstruct;
/*   69:     */ import javax.ejb.EJB;
/*   70:     */ import javax.faces.bean.ManagedBean;
/*   71:     */ import javax.faces.bean.ViewScoped;
/*   72:     */ import javax.validation.constraints.NotNull;
/*   73:     */ import javax.validation.constraints.Size;
/*   74:     */ import org.apache.log4j.Logger;
/*   75:     */ import org.primefaces.component.datatable.DataTable;
/*   76:     */ import org.primefaces.event.FileUploadEvent;
/*   77:     */ import org.primefaces.event.SelectEvent;
/*   78:     */ import org.primefaces.model.StreamedContent;
/*   79:     */ import org.primefaces.model.TreeNode;
/*   80:     */ import org.primefaces.model.UploadedFile;
/*   81:     */ 
/*   82:     */ @ManagedBean
/*   83:     */ @ViewScoped
/*   84:     */ public class FacturaClienteBean
/*   85:     */   extends FacturaClienteBaseBean
/*   86:     */ {
/*   87:     */   private static final long serialVersionUID = -7836267446292666793L;
/*   88:     */   @EJB
/*   89:     */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*   90:     */   @EJB
/*   91:     */   private transient ServicioPais servicioPais;
/*   92:     */   @EJB
/*   93:     */   private transient ServicioCiudad servicioCiudad;
/*   94:     */   private boolean mostrarCheckExportar;
/*   95:     */   private Integer idDespachoCliente;
/*   96: 104 */   private List<ErrorCarga> errores = new ArrayList();
/*   97: 106 */   private List<DespachoCliente> listaDespachoClientePendientes = new ArrayList();
/*   98: 108 */   private List<DespachoCliente> listaDespachoClienteSeleccionados = new ArrayList();
/*   99: 110 */   private List<DespachoCliente> listaDespachoClienteAsignados = new ArrayList();
/*  100: 112 */   private List<CuentaPorCobrar> listaCuentaPorCobrarVerificada = new ArrayList();
/*  101: 113 */   private List<CuentaPorCobrar> listaCuentaPorCobrarDefinitiva = new ArrayList();
/*  102:     */   private List<Pais> listaPais;
/*  103:     */   private List<Pais> listaPaisOrigen;
/*  104:     */   private List<Pais> listaPaisDestino;
/*  105:     */   private List<Ciudad> listaCiudadOrigen;
/*  106:     */   private List<Ciudad> listaCiudadDestino;
/*  107:     */   private PrefacturaCliente prefacturaCliente;
/*  108:     */   private String identificacionCliente;
/*  109:     */   @NotNull
/*  110:     */   private TipoIdentificacion tipoIdentificacionCliente;
/*  111:     */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  112:     */   private List<EntidadUsuario> listaAgenteComercial;
/*  113:     */   @Size(min=10, max=100)
/*  114:     */   private String nombreCliente;
/*  115:     */   @Size(min=2, max=50)
/*  116:     */   private String direccionCliente;
/*  117:     */   @Size(max=13)
/*  118:     */   private String telefonoCliente;
/*  119:     */   @NotNull
/*  120:     */   private Ciudad ciudad;
/*  121:     */   private EntidadUsuario agenteComercial;
/*  122:     */   private String email;
/*  123:     */   private boolean renderClienteNuevo;
/*  124:     */   @EJB
/*  125:     */   protected transient ServicioPrefacturaCliente servicioPrefacturaCliente;
/*  126:     */   @EJB
/*  127:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  128:     */   private BigDecimal totalFactura;
/*  129:     */   private BigDecimal totalCuota;
/*  130:     */   protected DataTable dtDetalleCuotas;
/*  131:     */   private TreeNode selectedNode;
/*  132:     */   
/*  133:     */   @PostConstruct
/*  134:     */   public void init()
/*  135:     */   {
/*  136: 159 */     super.init();
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void obtenerFiltros(Map<String, String> filters)
/*  140:     */   {
/*  141: 169 */     if (this.idFacturaCliente != null)
/*  142:     */     {
/*  143: 170 */       filters.put("idFacturaCliente", this.idFacturaCliente.toString());
/*  144: 171 */       this.idFacturaCliente = null;
/*  145:     */     }
/*  146: 173 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  147: 174 */     filters.put("documento.tipoComprobanteSRI.codigo", "!=11");
/*  148:     */   }
/*  149:     */   
/*  150:     */   public String limpiar()
/*  151:     */   {
/*  152: 186 */     super.limpiar();
/*  153: 187 */     this.prefacturaCliente = null;
/*  154: 188 */     this.listaDespachoClienteAsignados = new ArrayList();
/*  155: 189 */     this.listaDespachoClientePendientes = new ArrayList();
/*  156: 190 */     return "";
/*  157:     */   }
/*  158:     */   
/*  159:     */   public String crear()
/*  160:     */   {
/*  161: 201 */     if (AppUtil.getPuntoDeVenta().isIndicadorPos())
/*  162:     */     {
/*  163: 202 */       addInfoMessage(getLanguageController().getMensaje("msg_error_facturar_pos"));
/*  164: 203 */       setEditado(false);
/*  165: 204 */       return "";
/*  166:     */     }
/*  167: 206 */     limpiar();
/*  168: 207 */     setEditado(true);
/*  169: 208 */     return "";
/*  170:     */   }
/*  171:     */   
/*  172:     */   public boolean actualizarCamposEnvioXML()
/*  173:     */   {
/*  174: 212 */     System.out.println("-*-*-*-*  " + getFacturaCliente().getEmpresa().getCliente().getIndicadorEnvio());
/*  175:     */     
/*  176: 214 */     return getFacturaCliente().getEmpresa().getCliente().getIndicadorEnvio().booleanValue();
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void cargarCuotas()
/*  180:     */   {
/*  181: 219 */     this.totalCuota = BigDecimal.ZERO;
/*  182: 220 */     this.totalFactura = BigDecimal.ZERO;
/*  183: 221 */     this.listaCuentaPorCobrarVerificada = new ArrayList();
/*  184:     */     
/*  185: 223 */     this.facturaCliente = ((FacturaCliente)getDtFacturaCliente().getRowData());
/*  186: 224 */     this.totalFactura = this.facturaCliente.getTotalFactura();
/*  187:     */     try
/*  188:     */     {
/*  189: 226 */       this.facturaCliente = this.servicioFacturaCliente.cargarDetalle(this.facturaCliente.getIdFacturaCliente());
/*  190: 228 */       for (CuentaPorCobrar cpc : this.facturaCliente.getListaCuentaPorCobrar())
/*  191:     */       {
/*  192: 230 */         if (cpc.getSaldo().compareTo(cpc.getValor()) != 0) {
/*  193: 231 */           cpc.setActivoEdicionCuota(true);
/*  194:     */         }
/*  195: 234 */         this.listaCuentaPorCobrarVerificada.add(cpc);
/*  196:     */       }
/*  197:     */     }
/*  198:     */     catch (ExcepcionAS2Ventas e)
/*  199:     */     {
/*  200: 238 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  201: 239 */       e.printStackTrace();
/*  202:     */     }
/*  203: 242 */     cargarTotalCuota();
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void guardarCuotas()
/*  207:     */   {
/*  208:     */     try
/*  209:     */     {
/*  210: 249 */       this.servicioFacturaCliente.guardarCuotas(this.facturaCliente.getListaCuentaPorCobrar(), this.totalFactura);
/*  211: 250 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  212:     */     }
/*  213:     */     catch (AS2Exception e)
/*  214:     */     {
/*  215: 253 */       JsfUtil.addErrorMessage(e, "");
/*  216: 254 */       LOG.info("ERROR AL GUARDAR DATOS ", e);
/*  217: 255 */       e.printStackTrace();
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void agregarDetalleCuota()
/*  222:     */   {
/*  223: 260 */     CuentaPorCobrar cpcAux = (CuentaPorCobrar)this.listaCuentaPorCobrarVerificada.get(0);
/*  224:     */     
/*  225: 262 */     CuentaPorCobrar cpc = new CuentaPorCobrar();
/*  226: 263 */     cpc.setFechaVencimiento(cpcAux.getFechaVencimiento());
/*  227: 264 */     cpc.setFechaCancelacion(cpcAux.getFechaCancelacion());
/*  228: 265 */     cpc.setIdOrganizacion(cpcAux.getIdOrganizacion());
/*  229: 266 */     cpc.setIdSucursal(cpcAux.getIdSucursal());
/*  230: 267 */     cpc.setFacturaCliente(cpcAux.getFacturaCliente());
/*  231: 268 */     this.listaCuentaPorCobrarVerificada.add(cpc);
/*  232: 269 */     this.facturaCliente.getListaCuentaPorCobrar().add(cpc);
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void eliminarDetalleCuota()
/*  236:     */   {
/*  237: 275 */     this.listaCuentaPorCobrarDefinitiva = new ArrayList();
/*  238: 276 */     CuentaPorCobrar cpcEliminar = (CuentaPorCobrar)this.dtDetalleCuotas.getRowData();
/*  239: 277 */     cpcEliminar.setEliminado(true);
/*  240: 278 */     for (CuentaPorCobrar cpc : this.listaCuentaPorCobrarVerificada) {
/*  241: 279 */       if (!cpc.isEliminado()) {
/*  242: 280 */         this.listaCuentaPorCobrarDefinitiva.add(cpc);
/*  243:     */       }
/*  244:     */     }
/*  245: 284 */     this.listaCuentaPorCobrarVerificada.clear();
/*  246: 285 */     for (CuentaPorCobrar cpcDefinitiva : this.listaCuentaPorCobrarDefinitiva) {
/*  247: 286 */       this.listaCuentaPorCobrarVerificada.add(cpcDefinitiva);
/*  248:     */     }
/*  249: 289 */     cargarTotalCuota();
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void cargarTotalCuota()
/*  253:     */   {
/*  254: 294 */     this.totalCuota = BigDecimal.ZERO;
/*  255: 295 */     for (CuentaPorCobrar cpc : this.listaCuentaPorCobrarVerificada) {
/*  256: 296 */       this.totalCuota = this.totalCuota.add(cpc.getValor());
/*  257:     */     }
/*  258:     */   }
/*  259:     */   
/*  260:     */   public List<PrefacturaCliente> autocompletarPrefacturas(String consulta)
/*  261:     */   {
/*  262: 303 */     List<PrefacturaCliente> listaPrefactura = new ArrayList();
/*  263: 305 */     if (getFacturaCliente().getEmpresa() != null)
/*  264:     */     {
/*  265: 306 */       HashMap<String, String> filters = new HashMap();
/*  266: 307 */       filters.put("empresa.idEmpresa", String.valueOf(getFacturaCliente().getEmpresa().getId()));
/*  267: 308 */       filters.put("estado", "=" + Estado.ELABORADO.toString());
/*  268: 309 */       filters.put("numero", "%" + consulta.trim() + "%");
/*  269: 310 */       listaPrefactura = this.servicioPrefacturaCliente.obtenerListaCombo("numero", true, filters);
/*  270:     */     }
/*  271: 314 */     listaPrefactura.removeAll(getFacturaCliente().getListaPrefacturaCliente());
/*  272:     */     
/*  273: 316 */     return listaPrefactura;
/*  274:     */   }
/*  275:     */   
/*  276:     */   public void cargarDocumento()
/*  277:     */   {
/*  278: 324 */     if (this.idDespachoCliente != null)
/*  279:     */     {
/*  280: 325 */       limpiar();
/*  281: 326 */       boolean indicadorError = false;
/*  282:     */       try
/*  283:     */       {
/*  284: 328 */         this.servicioFacturaCliente.crearFacturaDesdeDespacho(getFacturaCliente(), this.idDespachoCliente.intValue(), AppUtil.getPuntoDeVenta(), AppUtil.getSucursal(), false);
/*  285:     */       }
/*  286:     */       catch (AS2Exception e)
/*  287:     */       {
/*  288: 331 */         indicadorError = true;
/*  289: 332 */         e.printStackTrace();
/*  290: 333 */         JsfUtil.addErrorMessage(e, "");
/*  291:     */       }
/*  292:     */       catch (ExcepcionAS2 e)
/*  293:     */       {
/*  294: 335 */         if (("msg_error_producto_no_lista_precios".equals(e.getCodigoExcepcion())) || 
/*  295: 336 */           ("msg_secuencia_no_encontrada".equals(e.getCodigoExcepcion())))
/*  296:     */         {
/*  297: 337 */           indicadorError = false;
/*  298:     */         }
/*  299:     */         else
/*  300:     */         {
/*  301: 339 */           indicadorError = true;
/*  302: 340 */           e.printStackTrace();
/*  303:     */         }
/*  304: 342 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  305:     */       }
/*  306: 344 */       if (!indicadorError)
/*  307:     */       {
/*  308: 345 */         this.listaDespachoClienteAsignados.add(getFacturaCliente().getDespachoCliente());
/*  309: 348 */         if (getFacturaCliente().getPedidoCliente() != null)
/*  310:     */         {
/*  311: 349 */           this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getFacturaCliente().getEmpresa().getIdEmpresa());
/*  312:     */         }
/*  313: 350 */         else if (getFacturaCliente().getEmpresa().getCliente().getAgenteComercial() != null)
/*  314:     */         {
/*  315: 351 */           Set<EntidadUsuario> listaAux = new HashSet();
/*  316: 352 */           Set<EntidadUsuario> listaAux2 = new HashSet();
/*  317: 354 */           for (EntidadUsuario entidadUsuario : getListaAgenteComercialCombo()) {
/*  318: 355 */             if (!entidadUsuario.equals(getFacturaCliente().getEmpresa().getCliente().getAgenteComercial())) {
/*  319: 356 */               listaAux.add(getFacturaCliente().getEmpresa().getCliente().getAgenteComercial());
/*  320:     */             }
/*  321:     */           }
/*  322: 359 */           if (listaAux.size() > 0) {
/*  323: 360 */             getListaAgenteComercialCombo().addAll(listaAux);
/*  324:     */           }
/*  325: 363 */           listaAux2.addAll(getListaAgenteComercialCombo());
/*  326: 364 */           getListaAgenteComercialCombo().clear();
/*  327: 365 */           getListaAgenteComercialCombo().addAll(listaAux2);
/*  328:     */         }
/*  329: 368 */         cargarDirecciones(getFacturaCliente().getEmpresa());
/*  330: 369 */         cargarSubempresas();
/*  331:     */         
/*  332: 371 */         actualizarDocumento();
/*  333:     */         
/*  334: 373 */         setEditado(true);
/*  335:     */       }
/*  336: 375 */       this.idDespachoCliente = null;
/*  337:     */       
/*  338:     */ 
/*  339: 378 */       this.listaFormaPagoSRI = null;
/*  340:     */     }
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void cargarDetallePedidoListener()
/*  344:     */   {
/*  345: 388 */     for (Iterator localIterator1 = getFacturaCliente().getListaDetalleFacturaCliente().iterator(); localIterator1.hasNext();)
/*  346:     */     {
/*  347: 388 */       ddc = (DetalleFacturaCliente)localIterator1.next();
/*  348: 389 */       ddc.setEliminado(true);
/*  349: 390 */       for (ImpuestoProductoFacturaCliente impuesto : ddc.getListaImpuestoProductoFacturaCliente()) {
/*  350: 391 */         impuesto.setEliminado(true);
/*  351:     */       }
/*  352:     */     }
/*  353:     */     DetalleFacturaCliente ddc;
/*  354: 395 */     if (this.facturaCliente.getPedidoCliente() != null)
/*  355:     */     {
/*  356: 396 */       getFacturaCliente().setZona(this.facturaCliente.getPedidoCliente().getZona());
/*  357: 397 */       getFacturaCliente().setCondicionPago(this.facturaCliente.getPedidoCliente().getCondicionPago());
/*  358: 398 */       getFacturaCliente().setAgenteComercial(this.facturaCliente.getPedidoCliente().getAgenteComercial());
/*  359: 399 */       getFacturaCliente().setNumeroCuotas(this.facturaCliente.getPedidoCliente().getNumeroCuotas());
/*  360: 400 */       getFacturaCliente().setCanal(this.facturaCliente.getPedidoCliente().getCanal());
/*  361: 401 */       getFacturaCliente().setDescripcion(this.facturaCliente.getPedidoCliente().getDescripcion());
/*  362: 402 */       getFacturaCliente().setSubempresa(this.facturaCliente.getPedidoCliente().getSubempresa());
/*  363: 403 */       getFacturaCliente().setProyecto(this.facturaCliente.getPedidoCliente().getProyecto());
/*  364: 404 */       cargarDirecciones(this.facturaCliente.getEmpresa());
/*  365: 405 */       getFacturaCliente().setDireccionEmpresa(this.facturaCliente.getPedidoCliente().getDireccionEmpresa());
/*  366:     */       
/*  367:     */ 
/*  368:     */ 
/*  369: 409 */       Object listaDPC = this.servicioPedidoCliente.obtenerListaDetallePedidoPorFacturar(this.facturaCliente.getPedidoCliente().getId());
/*  370: 411 */       for (DetallePedidoCliente dpc : (List)listaDPC)
/*  371:     */       {
/*  372: 413 */         DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/*  373: 414 */         dfc.setDetallePedidoCliente(dpc);
/*  374: 415 */         dfc.setFacturaCliente(this.facturaCliente);
/*  375: 416 */         dfc.setUnidadVenta(dpc.getUnidadVenta());
/*  376: 417 */         dfc.setCantidad(dpc.getCantidadPorFacturar());
/*  377: 418 */         dfc.setIndicadorPorcentajeIce(dpc.isIndicadorPorcentajeIce());
/*  378: 419 */         dfc.setCodigoIce(dpc.getCodigoIce());
/*  379: 420 */         dfc.setIceLinea(dpc.getIceLinea());
/*  380: 421 */         dfc.setIce(dpc.getIce());
/*  381: 422 */         dfc.setPrecio(dpc.getPrecio());
/*  382: 423 */         dfc.setDescuento(dpc.getDescuento());
/*  383: 424 */         dfc.setPorcentajeDescuento(dpc.getPorcentajeDescuento());
/*  384: 425 */         dfc.setDescripcion(dpc.getDescripcion());
/*  385: 426 */         getFacturaCliente().getListaDetalleFacturaCliente().add(dfc);
/*  386: 427 */         super.actualizarProducto(dfc, dpc.getProducto(), false);
/*  387:     */       }
/*  388: 430 */       Map<String, String> filtros = new HashMap();
/*  389: 431 */       filtros.put("pedidoCliente.idPedidoCliente", this.facturaCliente.getPedidoCliente().getId() + "");
/*  390: 432 */       Object listaDespachoPedido = new ArrayList();
/*  391: 433 */       listaDespachoPedido = this.servicioDespachoCliente.obtenerListaPorPagina(0, 1, null, true, filtros);
/*  392: 434 */       if (!((List)listaDespachoPedido).isEmpty()) {
/*  393: 435 */         this.listaDespachoClienteAsignados.add(((List)listaDespachoPedido).get(0));
/*  394:     */       }
/*  395:     */     }
/*  396: 439 */     actualizarImpuestosTodos();
/*  397:     */     
/*  398: 441 */     cargarSubempresas();
/*  399:     */   }
/*  400:     */   
/*  401:     */   public void cargarDetallePrefacturaListener(SelectEvent event)
/*  402:     */   {
/*  403: 452 */     PrefacturaCliente prefacturaCliente = (PrefacturaCliente)event.getObject();
/*  404: 454 */     if (prefacturaCliente != null)
/*  405:     */     {
/*  406: 456 */       if (!getFacturaCliente().getListaPrefacturaCliente().contains(prefacturaCliente)) {
/*  407: 457 */         getFacturaCliente().getListaPrefacturaCliente().add(prefacturaCliente);
/*  408:     */       }
/*  409: 460 */       if (prefacturaCliente.getEstado().equals(Estado.FACTURADO))
/*  410:     */       {
/*  411: 461 */         addInfoMessage(getLanguageController().getMensaje("msg_info_ya_existe_factura_prefactura"));
/*  412:     */       }
/*  413:     */       else
/*  414:     */       {
/*  415: 464 */         AjustePrefacturaCliente ajustePrefacturaCliente = this.servicioPrefacturaCliente.obtenerAjustePrefacturaActivo(prefacturaCliente.getId());
/*  416:     */         
/*  417: 466 */         getFacturaCliente().setEmpresa(ajustePrefacturaCliente.getPrefacturaCliente().getEmpresa());
/*  418: 467 */         if (!getFacturaCliente().getListaPrefacturaCliente().contains(ajustePrefacturaCliente.getPrefacturaCliente())) {
/*  419: 468 */           getFacturaCliente().getListaPrefacturaCliente().add(ajustePrefacturaCliente.getPrefacturaCliente());
/*  420:     */         }
/*  421: 471 */         getFacturaCliente().setCondicionPago(ajustePrefacturaCliente.getPrefacturaCliente().getCondicionPago());
/*  422: 472 */         getFacturaCliente().setNumeroCuotas(ajustePrefacturaCliente.getPrefacturaCliente().getNumeroCuotas());
/*  423:     */         
/*  424: 474 */         String descripcion = getFacturaCliente().getDescripcion();
/*  425: 475 */         descripcion = (descripcion == null ? "" : new StringBuilder().append(descripcion).append(" ").toString()) + ajustePrefacturaCliente.getPrefacturaCliente().getDescripcion();
/*  426: 476 */         getFacturaCliente().setDescripcion(descripcion);
/*  427:     */         
/*  428: 478 */         getFacturaCliente().setDireccionEmpresa(ajustePrefacturaCliente.getPrefacturaCliente().getDireccionEmpresa());
/*  429: 479 */         getFacturaCliente().setAgenteComercial(ajustePrefacturaCliente.getPrefacturaCliente().getAgenteComercial());
/*  430: 481 */         for (DetalleAjustePrefacturaCliente detalle : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente())
/*  431:     */         {
/*  432: 482 */           DetalleFacturaCliente detalleFactura = new DetalleFacturaCliente();
/*  433: 483 */           detalleFactura.setIdOrganizacion(detalle.getIdOrganizacion());
/*  434: 484 */           detalleFactura.setIdSucursal(detalle.getIdSucursal());
/*  435: 485 */           detalleFactura.setProducto(detalle.getProducto());
/*  436: 486 */           detalleFactura.setDescripcion(detalle.getDescripcion());
/*  437: 487 */           detalleFactura.setCantidad(detalle.getCantidad());
/*  438: 488 */           detalleFactura.setDescuento(detalle.getDescuento());
/*  439: 489 */           detalleFactura.setPorcentajeDescuento(detalle.getPorcentajeDescuento());
/*  440: 490 */           detalleFactura.setPrecio(detalle.getPrecio());
/*  441: 491 */           detalleFactura.setUnidadVenta(detalle.getUnidadVenta());
/*  442: 492 */           detalleFactura.setFacturaCliente(getFacturaCliente());
/*  443: 493 */           detalleFactura.setIndicadorImpuesto(detalle.getProducto().isIndicadorImpuestos());
/*  444:     */           try
/*  445:     */           {
/*  446: 496 */             this.servicioUnidadConversion.cargarListaUnidadConversion(detalleFactura.getProducto());
/*  447:     */           }
/*  448:     */           catch (ExcepcionAS2 e)
/*  449:     */           {
/*  450: 498 */             addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  451:     */           }
/*  452: 501 */           if (detalleFactura.isIndicadorImpuesto()) {
/*  453:     */             try
/*  454:     */             {
/*  455: 504 */               this.servicioFacturaCliente.obtenerImpuestosProductos(detalleFactura.getProducto(), detalleFactura);
/*  456:     */             }
/*  457:     */             catch (ExcepcionAS2Inventario e)
/*  458:     */             {
/*  459: 507 */               addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  460:     */             }
/*  461:     */           }
/*  462: 519 */           getFacturaCliente().getListaDetalleFacturaCliente().add(detalleFactura);
/*  463:     */         }
/*  464: 523 */         totalizar();
/*  465:     */       }
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   public String autorizarVenta()
/*  470:     */   {
/*  471:     */     try
/*  472:     */     {
/*  473: 536 */       EntidadUsuario usuarioAutoriza = this.servicioUsuario.obtieneUsuarioAutorizaVentas(getUsuarioAutorizaVenta(), getClaveAutorizaVenta());
/*  474: 537 */       getFacturaCliente().setUsuarioAutorizaVentas(usuarioAutoriza.getNombreUsuario());
/*  475: 538 */       getFacturaCliente().setIndicadorAutorizaVenta(true);
/*  476: 539 */       addInfoMessage(getLanguageController().getMensaje("msg_info_autorizar_venta"));
/*  477:     */     }
/*  478:     */     catch (ExcepcionAS2 e)
/*  479:     */     {
/*  480: 541 */       getFacturaCliente().setUsuarioAutorizaVentas(null);
/*  481: 542 */       getFacturaCliente().setIndicadorAutorizaVenta(false);
/*  482: 543 */       addErrorMessage(getLanguageController().getMensaje("msg_error_autorizar_venta"));
/*  483:     */     }
/*  484: 545 */     return "";
/*  485:     */   }
/*  486:     */   
/*  487:     */   public String seleccionarFacturaClienteExportacion()
/*  488:     */   {
/*  489: 554 */     FacturaCliente facturaClienteSeleccionada = (FacturaCliente)this.dtFacturaCliente.getRowData();
/*  490:     */     try
/*  491:     */     {
/*  492: 556 */       setRenderDialogDatosExportaciones(true);
/*  493: 557 */       this.listaDistritoAduanero = DatosSRI.getListaDistritoAduanero();
/*  494: 558 */       this.listaRegimen = DatosSRI.getListaRegimen();
/*  495: 559 */       FacturaCliente facturaClienteConDetalle = this.servicioFacturaCliente.cargarDetalle(facturaClienteSeleccionada.getIdFacturaCliente(), false);
/*  496:     */       
/*  497:     */ 
/*  498: 562 */       setFacturaCliente(facturaClienteConDetalle);
/*  499:     */     }
/*  500:     */     catch (Exception e)
/*  501:     */     {
/*  502: 564 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  503:     */     }
/*  504: 566 */     return "";
/*  505:     */   }
/*  506:     */   
/*  507:     */   public String crearCliente()
/*  508:     */   {
/*  509:     */     try
/*  510:     */     {
/*  511: 577 */       Empresa empresa = this.servicioEmpresa.crearClienteConDatosBasicos(this.identificacionCliente, this.tipoIdentificacionCliente, this.nombreCliente, this.direccionCliente, this.telefonoCliente, 
/*  512: 578 */         AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), this.ciudad, this.agenteComercial, this.email);
/*  513:     */       
/*  514:     */ 
/*  515: 581 */       this.identificacionCliente = null;
/*  516:     */       
/*  517: 583 */       this.nombreCliente = null;
/*  518: 584 */       this.direccionCliente = null;
/*  519: 585 */       this.telefonoCliente = null;
/*  520: 586 */       this.agenteComercial = null;
/*  521: 587 */       this.email = null;
/*  522:     */       
/*  523: 589 */       actualizarCliente(empresa);
/*  524: 590 */       setRenderClienteNuevo(false);
/*  525: 591 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  526:     */     }
/*  527:     */     catch (ExcepcionAS2 e)
/*  528:     */     {
/*  529: 593 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  530: 594 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  531:     */     }
/*  532:     */     catch (Exception ex)
/*  533:     */     {
/*  534: 596 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  535: 597 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  536:     */     }
/*  537: 600 */     return "";
/*  538:     */   }
/*  539:     */   
/*  540:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  541:     */   {
/*  542: 604 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void asignarFechaRefrendo()
/*  546:     */   {
/*  547: 608 */     if (this.facturaCliente.getFacturaClienteSRI() != null)
/*  548:     */     {
/*  549: 609 */       this.facturaCliente.getFacturaClienteSRI().setFechaTransaccion(this.facturaCliente.getFecha());
/*  550: 610 */       if (!RefrendoEnum.EXPORTACION_SERVICIOS.equals(this.facturaCliente.getFacturaClienteSRI().getRefrendo()))
/*  551:     */       {
/*  552: 611 */         this.facturaCliente.getFacturaClienteSRI().setIngresoExteriorGraboImpuestos(false);
/*  553: 612 */         this.facturaCliente.getFacturaClienteSRI().setValorImpuestoExportacion(BigDecimal.ZERO);
/*  554:     */       }
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   public void processDownload()
/*  559:     */   {
/*  560:     */     try
/*  561:     */     {
/*  562: 624 */       FacturaCliente fp = (FacturaCliente)this.dtFacturaCliente.getRowData();
/*  563: 625 */       String fileName = fp.getPdf();
/*  564: 626 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_cliente");
/*  565: 628 */       if (fileName == null) {
/*  566: 629 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  567:     */       } else {
/*  568: 631 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  569:     */       }
/*  570:     */     }
/*  571:     */     catch (Exception e)
/*  572:     */     {
/*  573: 635 */       e.printStackTrace();
/*  574: 636 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  575:     */     }
/*  576:     */   }
/*  577:     */   
/*  578:     */   public String eliminarArchivo()
/*  579:     */   {
/*  580: 641 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getFacturaCliente().getPdf());
/*  581: 642 */     getFacturaCliente().setPdf(null);
/*  582: 643 */     HashMap<String, Object> campos = new HashMap();
/*  583: 644 */     campos.put("pdf", null);
/*  584: 645 */     this.servicioFacturaCliente.actualizarAtributoEntidad(getFacturaCliente(), campos);
/*  585: 646 */     return null;
/*  586:     */   }
/*  587:     */   
/*  588:     */   public void processUpload(FileUploadEvent event)
/*  589:     */   {
/*  590:     */     try
/*  591:     */     {
/*  592: 659 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_cliente");
/*  593:     */       
/*  594: 661 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getFacturaCliente().getNumero(), uploadDir);
/*  595:     */       
/*  596:     */ 
/*  597: 664 */       HashMap<String, Object> campos = new HashMap();
/*  598: 665 */       campos.put("pdf", fileName);
/*  599: 666 */       this.servicioFacturaCliente.actualizarAtributoEntidad(getFacturaCliente(), campos);
/*  600:     */       
/*  601: 668 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  602:     */     }
/*  603:     */     catch (Exception e)
/*  604:     */     {
/*  605: 671 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  606: 672 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  607:     */     }
/*  608:     */   }
/*  609:     */   
/*  610:     */   public String getDirectorioDescarga()
/*  611:     */   {
/*  612: 679 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "factura_cliente");
/*  613:     */   }
/*  614:     */   
/*  615:     */   public Integer getIdFacturaCliente()
/*  616:     */   {
/*  617: 686 */     return this.idFacturaCliente;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public void setIdFacturaCliente(Integer idFacturaCliente)
/*  621:     */   {
/*  622: 694 */     this.idFacturaCliente = idFacturaCliente;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public Integer getIdDespachoCliente()
/*  626:     */   {
/*  627: 701 */     return this.idDespachoCliente;
/*  628:     */   }
/*  629:     */   
/*  630:     */   public void setIdDespachoCliente(Integer idDespachoCliente)
/*  631:     */   {
/*  632: 709 */     this.idDespachoCliente = idDespachoCliente;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public boolean isMostrarCheckExportar()
/*  636:     */   {
/*  637: 718 */     return this.mostrarCheckExportar;
/*  638:     */   }
/*  639:     */   
/*  640:     */   public void setMostrarCheckExportar(boolean mostrarCheckExportar)
/*  641:     */   {
/*  642: 728 */     this.mostrarCheckExportar = mostrarCheckExportar;
/*  643:     */   }
/*  644:     */   
/*  645:     */   public PrefacturaCliente getPrefacturaCliente()
/*  646:     */   {
/*  647: 737 */     return this.prefacturaCliente;
/*  648:     */   }
/*  649:     */   
/*  650:     */   public void setPrefacturaCliente(PrefacturaCliente prefacturaCliente)
/*  651:     */   {
/*  652: 747 */     this.prefacturaCliente = prefacturaCliente;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public List<ErrorCarga> getErrores()
/*  656:     */   {
/*  657: 751 */     return this.errores;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void setErrores(List<ErrorCarga> errores)
/*  661:     */   {
/*  662: 755 */     this.errores = errores;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public String cargarFacturaClienteElectronica(FileUploadEvent event)
/*  666:     */   {
/*  667: 764 */     List<FacturaCliente> lisFacturaCliente = new ArrayList();
/*  668:     */     try
/*  669:     */     {
/*  670: 767 */       String fileName = "migracion_factura_cliente" + event.getFile().getFileName();
/*  671: 768 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  672: 769 */       lisFacturaCliente = this.servicioFacturaCliente.migracionFacturaCliente(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/*  673: 770 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  674:     */       
/*  675: 772 */       this.servicioFacturaCliente.guardarFacturaClienteRevisadas(lisFacturaCliente);
/*  676:     */     }
/*  677:     */     catch (AS2Exception e)
/*  678:     */     {
/*  679: 775 */       e.printStackTrace();
/*  680: 776 */       List<String> listaMensajes = e.getCodigoMensajes();
/*  681: 777 */       int i = 0;
/*  682: 778 */       for (String a : listaMensajes)
/*  683:     */       {
/*  684: 779 */         i = a.indexOf("*");
/*  685: 780 */         a.substring(0, i + 1);
/*  686: 781 */         ErrorCarga ec = new ErrorCarga();
/*  687: 782 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/*  688: 783 */         this.errores.add(ec);
/*  689:     */       }
/*  690: 785 */       for (String a : e.getMensajes())
/*  691:     */       {
/*  692: 786 */         ErrorCarga ec = new ErrorCarga();
/*  693: 787 */         ec.setError(a);
/*  694: 788 */         this.errores.add(ec);
/*  695:     */       }
/*  696:     */     }
/*  697:     */     catch (ExcepcionAS2Financiero e)
/*  698:     */     {
/*  699: 792 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  700: 793 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  701:     */     }
/*  702:     */     catch (ExcepcionAS2Compras e)
/*  703:     */     {
/*  704: 796 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  705: 797 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  706:     */     }
/*  707:     */     catch (ExcepcionAS2 e)
/*  708:     */     {
/*  709: 800 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  710: 801 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  711:     */     }
/*  712:     */     catch (Exception e)
/*  713:     */     {
/*  714: 804 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  715: 805 */       e.printStackTrace();
/*  716:     */     }
/*  717: 807 */     return null;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public String getIdentificacionCliente()
/*  721:     */   {
/*  722: 811 */     return this.identificacionCliente;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setIdentificacionCliente(String identificacionCliente)
/*  726:     */   {
/*  727: 815 */     this.identificacionCliente = identificacionCliente;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public TipoIdentificacion getTipoIdentificacionCliente()
/*  731:     */   {
/*  732: 819 */     return this.tipoIdentificacionCliente;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void setTipoIdentificacionCliente(TipoIdentificacion tipoIdentificacionCliente)
/*  736:     */   {
/*  737: 823 */     this.tipoIdentificacionCliente = tipoIdentificacionCliente;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/*  741:     */   {
/*  742: 827 */     if (this.listaTipoIdentificacion == null) {
/*  743: 828 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/*  744:     */     }
/*  745: 830 */     return this.listaTipoIdentificacion;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/*  749:     */   {
/*  750: 834 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public List<EntidadUsuario> getListaAgenteComercial()
/*  754:     */   {
/*  755: 838 */     return this.listaAgenteComercial;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setListaAgenteComercial(List<EntidadUsuario> listaAgenteComercial)
/*  759:     */   {
/*  760: 842 */     this.listaAgenteComercial = listaAgenteComercial;
/*  761:     */   }
/*  762:     */   
/*  763:     */   public String getNombreCliente()
/*  764:     */   {
/*  765: 846 */     return this.nombreCliente;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void setNombreCliente(String nombreCliente)
/*  769:     */   {
/*  770: 850 */     this.nombreCliente = nombreCliente;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public String getDireccionCliente()
/*  774:     */   {
/*  775: 854 */     return this.direccionCliente;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void setDireccionCliente(String direccionCliente)
/*  779:     */   {
/*  780: 858 */     this.direccionCliente = direccionCliente;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public String getTelefonoCliente()
/*  784:     */   {
/*  785: 862 */     return this.telefonoCliente;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setTelefonoCliente(String telefonoCliente)
/*  789:     */   {
/*  790: 866 */     this.telefonoCliente = telefonoCliente;
/*  791:     */   }
/*  792:     */   
/*  793:     */   public Ciudad getCiudad()
/*  794:     */   {
/*  795: 870 */     return this.ciudad;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void setCiudad(Ciudad ciudad)
/*  799:     */   {
/*  800: 874 */     this.ciudad = ciudad;
/*  801:     */   }
/*  802:     */   
/*  803:     */   public String getEmail()
/*  804:     */   {
/*  805: 878 */     return this.email;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void setEmail(String email)
/*  809:     */   {
/*  810: 882 */     this.email = email;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public boolean isRenderClienteNuevo()
/*  814:     */   {
/*  815: 886 */     return this.renderClienteNuevo;
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void setRenderClienteNuevo(boolean renderClienteNuevo)
/*  819:     */   {
/*  820: 890 */     this.renderClienteNuevo = renderClienteNuevo;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public EntidadUsuario getAgenteComercial()
/*  824:     */   {
/*  825: 894 */     return this.agenteComercial;
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/*  829:     */   {
/*  830: 898 */     this.agenteComercial = agenteComercial;
/*  831:     */   }
/*  832:     */   
/*  833:     */   public List<DespachoCliente> getListaDespachoClientePendientes()
/*  834:     */   {
/*  835: 902 */     return this.listaDespachoClientePendientes;
/*  836:     */   }
/*  837:     */   
/*  838:     */   public void setListaDespachoClientePendientes(List<DespachoCliente> listaDespachoClientePendientes)
/*  839:     */   {
/*  840: 906 */     this.listaDespachoClientePendientes = listaDespachoClientePendientes;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public List<DespachoCliente> getListaDespachoClienteAsignados()
/*  844:     */   {
/*  845: 910 */     return this.listaDespachoClienteAsignados;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public void setListaDespachoClienteAsignados(List<DespachoCliente> listaDespachoClienteAsignados)
/*  849:     */   {
/*  850: 914 */     this.listaDespachoClienteAsignados = listaDespachoClienteAsignados;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public List<DespachoCliente> getListaDespachoClienteSeleccionados()
/*  854:     */   {
/*  855: 918 */     return this.listaDespachoClienteSeleccionados;
/*  856:     */   }
/*  857:     */   
/*  858:     */   public void setListaDespachoClienteSeleccionados(List<DespachoCliente> listaDespachoClienteSeleccionados)
/*  859:     */   {
/*  860: 922 */     this.listaDespachoClienteSeleccionados = listaDespachoClienteSeleccionados;
/*  861:     */   }
/*  862:     */   
/*  863:     */   public String cargarDespachosPendientes()
/*  864:     */   {
/*  865: 926 */     if (this.facturaCliente.getEmpresa() == null)
/*  866:     */     {
/*  867: 927 */       this.listaDespachoClientePendientes = new ArrayList();
/*  868: 928 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_cliente"));
/*  869:     */     }
/*  870:     */     else
/*  871:     */     {
/*  872: 930 */       this.listaDespachoClientePendientes = this.servicioDespachoCliente.buscarDespachosNoFacturadosPorCliente(Integer.valueOf(this.facturaCliente.getEmpresa().getId()));
/*  873: 931 */       for (DespachoCliente despacho : this.listaDespachoClienteAsignados) {
/*  874: 932 */         this.listaDespachoClientePendientes.remove(despacho);
/*  875:     */       }
/*  876:     */     }
/*  877: 935 */     return "";
/*  878:     */   }
/*  879:     */   
/*  880:     */   public String adicionarDespachos()
/*  881:     */   {
/*  882: 939 */     for (DespachoCliente despacho : this.listaDespachoClienteSeleccionados)
/*  883:     */     {
/*  884: 940 */       DespachoCliente despachoCliente = this.servicioDespachoCliente.cargarDetalleAFacturar(Integer.valueOf(despacho.getIdDespachoCliente()));
/*  885: 941 */       if (this.facturaCliente.getSubempresa() != null)
/*  886:     */       {
/*  887: 942 */         Empresa subempresa = this.facturaCliente.getSubempresa().getEmpresa();
/*  888: 943 */         this.facturaCliente.setEmail(this.servicioEmpresa.cargarMails(subempresa, DocumentoBase.FACTURA_CLIENTE));
/*  889:     */       }
/*  890:     */       else
/*  891:     */       {
/*  892: 945 */         this.facturaCliente.setEmail(this.servicioEmpresa.cargarMails(this.facturaCliente.getEmpresa(), DocumentoBase.FACTURA_CLIENTE));
/*  893:     */       }
/*  894: 948 */       if ((!agregarDetalleDespachoAFactura(despachoCliente)) || 
/*  895: 949 */         (!ParametrosSistema.isBloqueoProductoListaPrecios(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue())) {
/*  896: 950 */         this.listaDespachoClienteAsignados.add(despachoCliente);
/*  897:     */       }
/*  898:     */     }
/*  899: 953 */     return "";
/*  900:     */   }
/*  901:     */   
/*  902:     */   public boolean agregarDetalleDespachoAFactura(DespachoCliente despachoCliente)
/*  903:     */   {
/*  904: 957 */     boolean bandera = false;
/*  905:     */     try
/*  906:     */     {
/*  907: 959 */       ExcepcionAS2 excepcionPrecio = this.servicioFacturaCliente.agregarDetalleDespachoAFactura(despachoCliente, this.facturaCliente, false);
/*  908: 960 */       if (excepcionPrecio != null)
/*  909:     */       {
/*  910: 961 */         bandera = true;
/*  911: 962 */         addErrorMessage(getLanguageController().getMensaje(excepcionPrecio.getCodigoExcepcion()) + excepcionPrecio.getMessage());
/*  912:     */       }
/*  913:     */     }
/*  914:     */     catch (ExcepcionAS2 e)
/*  915:     */     {
/*  916: 965 */       e.printStackTrace();
/*  917: 966 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  918:     */     }
/*  919: 968 */     return bandera;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public String eliminarDetalle()
/*  923:     */   {
/*  924: 973 */     super.eliminarDetalle();
/*  925:     */     
/*  926: 975 */     this.listaDespachoClienteAsignados = new ArrayList();
/*  927: 976 */     this.facturaCliente.setDespachoCliente(null);
/*  928: 977 */     Map<Integer, DespachoCliente> mapaDespachoCliente = new HashMap();
/*  929: 979 */     for (DetalleFacturaCliente detalle : this.facturaCliente.getListaDetalleFacturaCliente()) {
/*  930: 980 */       if ((!detalle.isEliminado()) && (detalle.getDetalleDespachoCliente() != null))
/*  931:     */       {
/*  932: 981 */         mapaDespachoCliente.put(Integer.valueOf(detalle.getDetalleDespachoCliente().getDespachoCliente().getId()), detalle.getDetalleDespachoCliente()
/*  933: 982 */           .getDespachoCliente());
/*  934: 983 */         this.facturaCliente.setDespachoCliente(detalle.getDetalleDespachoCliente().getDespachoCliente());
/*  935:     */       }
/*  936:     */     }
/*  937: 987 */     this.listaDespachoClienteAsignados.addAll(mapaDespachoCliente.values());
/*  938:     */     
/*  939: 989 */     return "";
/*  940:     */   }
/*  941:     */   
/*  942:     */   public StreamedContent getXMLSRI()
/*  943:     */   {
/*  944: 993 */     if ((this.facturaCliente != null) && (this.facturaCliente.getId() != 0) && (this.facturaCliente.getFacturaClienteSRI() != null))
/*  945:     */     {
/*  946: 994 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/*  947:     */       
/*  948:     */ 
/*  949: 997 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.FACTURA.toString();
/*  950: 998 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/*  951: 999 */       String nombreArchivo = this.facturaCliente.getNumero() + "-" + this.facturaCliente.getFacturaClienteSRI().getClaveAcceso() + ".xml";
/*  952:1000 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/*  953:     */       try
/*  954:     */       {
/*  955:1002 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/*  956:     */       }
/*  957:     */       catch (FileNotFoundException e)
/*  958:     */       {
/*  959:1004 */         e.printStackTrace();
/*  960:1005 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  961:1006 */         return null;
/*  962:     */       }
/*  963:     */     }
/*  964:1009 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  965:1010 */     return null;
/*  966:     */   }
/*  967:     */   
/*  968:     */   public List<CuentaPorCobrar> getListaCuentaPorCobrarVerificada()
/*  969:     */   {
/*  970:1017 */     return this.listaCuentaPorCobrarVerificada;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public void setListaCuentaPorCobrarVerificada(List<CuentaPorCobrar> listaCuentaPorCobrarVerificada)
/*  974:     */   {
/*  975:1021 */     this.listaCuentaPorCobrarVerificada = listaCuentaPorCobrarVerificada;
/*  976:     */   }
/*  977:     */   
/*  978:     */   public BigDecimal getTotalFactura()
/*  979:     */   {
/*  980:1025 */     return this.totalFactura;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void setTotalFactura(BigDecimal totalFactura)
/*  984:     */   {
/*  985:1029 */     this.totalFactura = totalFactura;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public DataTable getDtDetalleCuotas()
/*  989:     */   {
/*  990:1033 */     return this.dtDetalleCuotas;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setDtDetalleCuotas(DataTable dtDetalleCuotas)
/*  994:     */   {
/*  995:1037 */     this.dtDetalleCuotas = dtDetalleCuotas;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public List<CuentaPorCobrar> getListaCuentaPorCobrarDefinitiva()
/*  999:     */   {
/* 1000:1041 */     return this.listaCuentaPorCobrarDefinitiva;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void setListaCuentaPorCobrarDefinitiva(List<CuentaPorCobrar> listaCuentaPorCobrarDefinitiva)
/* 1004:     */   {
/* 1005:1045 */     this.listaCuentaPorCobrarDefinitiva = listaCuentaPorCobrarDefinitiva;
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public BigDecimal getTotalCuota()
/* 1009:     */   {
/* 1010:1049 */     return this.totalCuota;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void setTotalCuota(BigDecimal totalCuota)
/* 1014:     */   {
/* 1015:1053 */     this.totalCuota = totalCuota;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public TreeNode getSelectedNode()
/* 1019:     */   {
/* 1020:1057 */     return this.selectedNode;
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public void setSelectedNode(TreeNode selectedNode)
/* 1024:     */   {
/* 1025:1061 */     this.selectedNode = selectedNode;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public void cargarCiudad()
/* 1029:     */   {
/* 1030:     */     try
/* 1031:     */     {
/* 1032:1066 */       Ciudad ciudad = (Ciudad)this.selectedNode.getData();
/* 1033:1067 */       this.facturaCliente.setLugarIncoterm(ciudad);
/* 1034:     */     }
/* 1035:     */     catch (Exception e)
/* 1036:     */     {
/* 1037:1069 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1038:     */     }
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public List<Pais> getListaPaisOrigen()
/* 1042:     */   {
/* 1043:1075 */     if (this.listaPaisOrigen == null) {
/* 1044:1076 */       this.listaPaisOrigen = this.servicioPais.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 1045:     */     }
/* 1046:1078 */     return this.listaPaisOrigen;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public void setListaPaisOrigen(List<Pais> listaPaisOrigen)
/* 1050:     */   {
/* 1051:1082 */     this.listaPaisOrigen = listaPaisOrigen;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public List<Pais> getListaPaisDestino()
/* 1055:     */   {
/* 1056:1086 */     if (this.listaPaisDestino == null) {
/* 1057:1087 */       this.listaPaisDestino = this.servicioPais.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 1058:     */     }
/* 1059:1089 */     return this.listaPaisDestino;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public void setListaPaisDestino(List<Pais> listaPaisDestino)
/* 1063:     */   {
/* 1064:1093 */     this.listaPaisDestino = listaPaisDestino;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public List<Ciudad> getListaCiudadOrigen()
/* 1068:     */   {
/* 1069:1097 */     if (this.facturaCliente.getPaisOrigen() != null)
/* 1070:     */     {
/* 1071:1098 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 1072:1099 */       filters.put("provincia.pais.idPais", String.valueOf(this.facturaCliente.getPaisOrigen().getId()));
/* 1073:1100 */       this.listaCiudadOrigen = this.servicioCiudad.obtenerListaCombo("nombre", true, filters);
/* 1074:     */     }
/* 1075:     */     else
/* 1076:     */     {
/* 1077:1102 */       this.listaCiudadOrigen = new ArrayList();
/* 1078:     */     }
/* 1079:1104 */     return this.listaCiudadOrigen;
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   public void setListaCiudadOrigen(List<Ciudad> listaCiudadOrigen)
/* 1083:     */   {
/* 1084:1108 */     this.listaCiudadOrigen = listaCiudadOrigen;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public List<Ciudad> getListaCiudadDestino()
/* 1088:     */   {
/* 1089:1112 */     if (this.facturaCliente.getPaisDestino() != null)
/* 1090:     */     {
/* 1091:1113 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 1092:1114 */       filters.put("provincia.pais.idPais", String.valueOf(this.facturaCliente.getPaisDestino().getId()));
/* 1093:1115 */       this.listaCiudadDestino = this.servicioCiudad.obtenerListaCombo("nombre", true, filters);
/* 1094:     */     }
/* 1095:     */     else
/* 1096:     */     {
/* 1097:1117 */       this.listaCiudadDestino = new ArrayList();
/* 1098:     */     }
/* 1099:1119 */     return this.listaCiudadDestino;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public void setListaCiudadDestino(List<Ciudad> listaCiudadDestino)
/* 1103:     */   {
/* 1104:1123 */     this.listaCiudadDestino = listaCiudadDestino;
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   public List<Pais> getListaPais()
/* 1108:     */   {
/* 1109:1127 */     if (this.listaPais == null) {
/* 1110:1128 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 1111:     */     }
/* 1112:1130 */     return this.listaPais;
/* 1113:     */   }
/* 1114:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.FacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */