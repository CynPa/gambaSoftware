/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    5:     */ import com.asinfo.as2.controller.LanguageController;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    8:     */ import com.asinfo.as2.entities.Bodega;
/*    9:     */ import com.asinfo.as2.entities.Ciudad;
/*   10:     */ import com.asinfo.as2.entities.Cliente;
/*   11:     */ import com.asinfo.as2.entities.Cobro;
/*   12:     */ import com.asinfo.as2.entities.Comision;
/*   13:     */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   14:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   15:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   17:     */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   18:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   19:     */ import com.asinfo.as2.entities.Documento;
/*   20:     */ import com.asinfo.as2.entities.Empresa;
/*   21:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   22:     */ import com.asinfo.as2.entities.FormaPago;
/*   23:     */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   24:     */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   25:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   26:     */ import com.asinfo.as2.entities.Lote;
/*   27:     */ import com.asinfo.as2.entities.Organizacion;
/*   28:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   29:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   30:     */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*   31:     */ import com.asinfo.as2.entities.Producto;
/*   32:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   33:     */ import com.asinfo.as2.entities.Sucursal;
/*   34:     */ import com.asinfo.as2.entities.TarjetaCredito;
/*   35:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   36:     */ import com.asinfo.as2.entities.VersionComision;
/*   37:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   38:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   39:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   40:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   41:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   42:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   43:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   44:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*   45:     */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioTarjetaCredito;
/*   46:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*   47:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   48:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   49:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   50:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   51:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   52:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   53:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   54:     */ import com.asinfo.as2.util.AppUtil;
/*   55:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   56:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*   57:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   58:     */ import java.math.BigDecimal;
/*   59:     */ import java.math.RoundingMode;
/*   60:     */ import java.util.ArrayList;
/*   61:     */ import java.util.HashMap;
/*   62:     */ import java.util.Iterator;
/*   63:     */ import java.util.List;
/*   64:     */ import java.util.Map;
/*   65:     */ import javax.annotation.PostConstruct;
/*   66:     */ import javax.ejb.EJB;
/*   67:     */ import javax.faces.bean.ManagedBean;
/*   68:     */ import javax.faces.bean.ViewScoped;
/*   69:     */ import javax.faces.component.html.HtmlInputText;
/*   70:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   71:     */ import javax.validation.constraints.NotNull;
/*   72:     */ import javax.validation.constraints.Size;
/*   73:     */ import org.apache.log4j.Logger;
/*   74:     */ import org.primefaces.component.datatable.DataTable;
/*   75:     */ import org.primefaces.event.SelectEvent;
/*   76:     */ 
/*   77:     */ @ManagedBean
/*   78:     */ @ViewScoped
/*   79:     */ public class FacturaClienteAgilBean
/*   80:     */   extends FacturaClienteBaseBean
/*   81:     */ {
/*   82:     */   private static final long serialVersionUID = 4631720976613845483L;
/*   83:     */   @EJB
/*   84:     */   private ServicioLote servicioLote;
/*   85:     */   @EJB
/*   86:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*   87:     */   @EJB
/*   88:     */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*   89:     */   @EJB
/*   90:     */   private ServicioCobro servicioCobro;
/*   91:     */   @EJB
/*   92:     */   protected transient ServicioTarjetaCredito servicioTarjetaCredito;
/*   93:     */   private String identificacionCliente;
/*   94:     */   @NotNull
/*   95:     */   private TipoIdentificacion tipoIdentificacionCliente;
/*   96:     */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*   97:     */   private List<EntidadUsuario> listaAgenteComercial;
/*   98:     */   @Size(min=5, max=100)
/*   99:     */   private String nombreCliente;
/*  100:     */   @Size(min=2, max=50)
/*  101:     */   private String direccionCliente;
/*  102:     */   @Size(max=13)
/*  103:     */   private String telefonoCliente;
/*  104:     */   @NotNull
/*  105:     */   private Ciudad ciudad;
/*  106:     */   private EntidadUsuario agenteComercial;
/*  107:     */   private String email;
/*  108:     */   private List<Documento> listaDocumentoDespacho;
/*  109:     */   private List<TarjetaCredito> listaTarjetaCredito;
/*  110:     */   private Cobro cobro;
/*  111:     */   private Documento documento;
/*  112:     */   private String codigoCabecera;
/*  113:     */   private DetalleFormaCobro detalleFormaCobroSeleccionado;
/*  114: 123 */   private Map<Integer, TarjetaCredito> hmTarjeta = new HashMap();
/*  115:     */   private DataTable dtDetalleFormaCobro;
/*  116:     */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion;
/*  117:     */   private BigDecimal totalLiquidarFormaCobro;
/*  118:     */   private List<Ciudad> listaCiudad;
/*  119:     */   private List<Documento> listaDocumento;
/*  120:     */   private Lote loteCrear;
/*  121:     */   private DetalleFacturaCliente detalleFacturaClienteSeleccionado;
/*  122:     */   private boolean renderClienteNuevo;
/*  123:     */   
/*  124:     */   @PostConstruct
/*  125:     */   public void init()
/*  126:     */   {
/*  127: 144 */     super.init();
/*  128:     */   }
/*  129:     */   
/*  130:     */   public String limpiar()
/*  131:     */   {
/*  132: 154 */     super.limpiar();
/*  133:     */     try
/*  134:     */     {
/*  135: 158 */       Integer idEmpresa = ParametrosSistema.getClienteGenerico(AppUtil.getOrganizacion().getIdOrganizacion());
/*  136: 159 */       if (idEmpresa == null)
/*  137:     */       {
/*  138: 160 */         addInfoMessage(getLanguageController().getMensaje("msg_info_cliente_generico"));
/*  139:     */       }
/*  140:     */       else
/*  141:     */       {
/*  142: 162 */         Map<String, String> filters = new HashMap();
/*  143: 163 */         filters.put("idEmpresa", idEmpresa.toString());
/*  144: 164 */         List<Empresa> lista = this.servicioEmpresa.obtenerListaCombo("codigo", false, filters);
/*  145: 165 */         if (!lista.isEmpty())
/*  146:     */         {
/*  147: 166 */           actualizarCliente((Empresa)lista.get(0));
/*  148: 168 */           if (TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA.equals(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/*  149: 169 */             .getTipoOrganizacion()))
/*  150:     */           {
/*  151: 171 */             EntidadUsuario agenteComercial = this.servicioUsuario.buscarPorId(Integer.valueOf(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  152: 172 */             if (agenteComercial.isIndicadorAgenteComercial()) {
/*  153: 173 */               getFacturaCliente().setAgenteComercial(agenteComercial);
/*  154:     */             } else {
/*  155: 175 */               getFacturaCliente().setAgenteComercial(null);
/*  156:     */             }
/*  157:     */           }
/*  158:     */         }
/*  159:     */       }
/*  160:     */     }
/*  161:     */     catch (Exception e)
/*  162:     */     {
/*  163: 182 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo") + e.getMessage());
/*  164: 183 */       LOG.info("ERROR AL LIMPIAR DATOS FACTURA CLIENTE AGIL", e);
/*  165:     */     }
/*  166: 187 */     DespachoCliente despachoCliente = new DespachoCliente();
/*  167: 188 */     despachoCliente.setNumero("");
/*  168: 189 */     despachoCliente.setEstado(Estado.PROCESADO);
/*  169: 190 */     getFacturaCliente().setDespachoCliente(despachoCliente);
/*  170:     */     
/*  171:     */ 
/*  172: 193 */     this.cobro = new Cobro();
/*  173: 194 */     this.cobro.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  174: 195 */     this.cobro.setSucursal(AppUtil.getSucursal());
/*  175: 197 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/*  176:     */     {
/*  177: 198 */       this.documento = ((Documento)getListaDocumento().get(0));
/*  178: 199 */       this.cobro.setDocumento(this.documento);
/*  179: 200 */       actualizarDocumento();
/*  180:     */     }
/*  181:     */     else
/*  182:     */     {
/*  183: 202 */       this.documento = new Documento();
/*  184:     */     }
/*  185: 205 */     return "";
/*  186:     */   }
/*  187:     */   
/*  188:     */   public void obtenerFiltros(Map<String, String> filters)
/*  189:     */   {
/*  190: 215 */     if (this.idFacturaCliente != null)
/*  191:     */     {
/*  192: 216 */       filters.put("idFacturaCliente", this.idFacturaCliente.toString());
/*  193: 217 */       this.idFacturaCliente = null;
/*  194:     */     }
/*  195: 219 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  196:     */   }
/*  197:     */   
/*  198:     */   public String guardar()
/*  199:     */   {
/*  200: 232 */     if (this.totalLiquidarFormaCobro.compareTo(getFacturaCliente().getTotalFactura()) > 0) {
/*  201: 233 */       addErrorMessage(getLanguageController().getMensaje("msg_error_valor_cobro_total_factura"));
/*  202:     */     } else {
/*  203:     */       try
/*  204:     */       {
/*  205: 236 */         getFacturaCliente().setNumeroCuotas(1);
/*  206: 237 */         getFacturaCliente().setCondicionPago(getFacturaCliente().getEmpresa().getCliente().getCondicionPago());
/*  207:     */         
/*  208: 239 */         getFacturaCliente().getDespachoCliente().setFecha(getFacturaCliente().getFecha());
/*  209: 240 */         getFacturaCliente().getDespachoCliente().setIdOrganizacion(getFacturaCliente().getIdOrganizacion());
/*  210: 241 */         getFacturaCliente().getDespachoCliente().setSucursal(getFacturaCliente().getSucursal());
/*  211: 242 */         getFacturaCliente().getDespachoCliente().setEmpresa(getFacturaCliente().getEmpresa());
/*  212: 243 */         getFacturaCliente().getDespachoCliente().setDireccionEmpresa(getFacturaCliente().getDireccionEmpresa());
/*  213: 244 */         getFacturaCliente().getDespachoCliente().setDescripcion(getFacturaCliente().getDescripcion());
/*  214: 245 */         getFacturaCliente().getDespachoCliente().setPedidoCliente(getFacturaCliente().getPedidoCliente());
/*  215: 247 */         if (!getListaDocumentoDespacho().isEmpty()) {
/*  216: 248 */           getFacturaCliente().getDespachoCliente().setDocumento((Documento)getListaDocumentoDespacho().get(0));
/*  217:     */         }
/*  218: 251 */         for (DetalleFacturaCliente dfc : getFacturaCliente().getListaDetalleFacturaCliente()) {
/*  219: 252 */           if ((!dfc.isEliminado()) && (dfc.getDetalleDespachoCliente() != null))
/*  220:     */           {
/*  221: 253 */             dfc.getDetalleDespachoCliente().setProducto(dfc.getProducto());
/*  222: 254 */             dfc.getDetalleDespachoCliente().setCantidad(dfc.getCantidad().setScale(4, RoundingMode.HALF_UP));
/*  223: 255 */             dfc.getDetalleDespachoCliente().setPeso(dfc.getPeso().setScale(2, RoundingMode.HALF_UP));
/*  224: 256 */             dfc.getDetalleDespachoCliente().setUnidadVenta(dfc.getUnidadVenta());
/*  225: 257 */             dfc.getDetalleDespachoCliente().setDespachoCliente(getFacturaCliente().getDespachoCliente());
/*  226:     */           }
/*  227:     */         }
/*  228: 261 */         this.servicioFacturaCliente.validarBodega(getFacturaCliente());
/*  229: 264 */         if (this.cobro.getListaDetalleFormaCobro().size() > 0)
/*  230:     */         {
/*  231: 265 */           this.cobro.setEmpresa(getFacturaCliente().getEmpresa());
/*  232: 266 */           this.cobro.setFecha(getFacturaCliente().getFecha());
/*  233: 267 */           this.cobro.setValor(this.totalLiquidarFormaCobro);
/*  234: 268 */           this.cobro.setDescripcion(getFacturaCliente().getDescripcion());
/*  235: 269 */           this.cobro.setListaDetalleFormaCobro(getListaDetalleFormaCobro());
/*  236: 270 */           getFacturaCliente().setCobro(this.cobro);
/*  237:     */         }
/*  238:     */         else
/*  239:     */         {
/*  240: 272 */           getFacturaCliente().setCobro(null);
/*  241:     */         }
/*  242: 275 */         super.guardar();
/*  243:     */       }
/*  244:     */       catch (ExcepcionAS2Financiero e)
/*  245:     */       {
/*  246: 278 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  247: 279 */         LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE AGIL", e);
/*  248: 280 */         e.printStackTrace();
/*  249:     */       }
/*  250:     */       catch (ExcepcionAS2Inventario e)
/*  251:     */       {
/*  252: 282 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  253: 283 */         LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE AGIL", e);
/*  254: 284 */         e.printStackTrace();
/*  255:     */       }
/*  256:     */       catch (ExcepcionAS2 e)
/*  257:     */       {
/*  258: 286 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  259: 287 */         LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE AGIL", e);
/*  260: 288 */         e.printStackTrace();
/*  261:     */       }
/*  262:     */     }
/*  263: 293 */     return "";
/*  264:     */   }
/*  265:     */   
/*  266:     */   public String eliminar()
/*  267:     */   {
/*  268: 303 */     if ((this.facturaCliente != null) && (this.facturaCliente.getId() > 0))
/*  269:     */     {
/*  270: 304 */       this.facturaCliente.setFacturaClienteAgil(true);
/*  271: 305 */       super.eliminar();
/*  272:     */     }
/*  273:     */     else
/*  274:     */     {
/*  275: 307 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  276:     */     }
/*  277: 310 */     return "";
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/*  281:     */   {
/*  282: 321 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)getDtDetalleFacturaCliente().getRowData();
/*  283: 322 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  284:     */     try
/*  285:     */     {
/*  286: 325 */       cargarProductoDesdeCodigoEnDetalle(codigo, dfc);
/*  287:     */     }
/*  288:     */     catch (ExcepcionAS2 e)
/*  289:     */     {
/*  290: 327 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  291: 328 */       dfc.getProducto().setCodigo("");
/*  292: 329 */       dfc.getProducto().setNombre("");
/*  293:     */     }
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void cargarProductoDesdeCodigoEnDetalle(String codigo, DetalleFacturaCliente dfc)
/*  297:     */     throws ExcepcionAS2
/*  298:     */   {
/*  299: 335 */     Producto producto = null;
/*  300: 336 */     producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  301: 337 */     if (dfc.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
/*  302: 338 */       dfc.setCantidad(ParametrosSistema.getCantidadFacturaClienteAgil(this.facturaCliente.getIdOrganizacion()));
/*  303:     */     }
/*  304: 340 */     dfc.setProducto(producto);
/*  305: 341 */     actualizarInventarioProducto(dfc, null);
/*  306: 343 */     if (dfc.getProducto().getTipoProducto() == TipoProducto.ARTICULO)
/*  307:     */     {
/*  308: 344 */       if (AppUtil.getBodega() != null) {
/*  309: 345 */         dfc.getDetalleDespachoCliente().setBodega(AppUtil.getBodega());
/*  310:     */       } else {
/*  311: 347 */         dfc.getDetalleDespachoCliente().setBodega(dfc.getProducto().getBodegaVenta());
/*  312:     */       }
/*  313: 349 */       if (producto.getLote() != null)
/*  314:     */       {
/*  315: 350 */         dfc.getDetalleDespachoCliente().getInventarioProducto().setLote(producto.getLote());
/*  316: 351 */         BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), dfc.getDetalleDespachoCliente().getBodega().getIdBodega(), producto
/*  317: 352 */           .getLote().getIdLote(), this.facturaCliente.getFecha());
/*  318: 353 */         dfc.setSaldo(saldo);
/*  319: 355 */         if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  320: 356 */           dfc.setCantidad(saldo.setScale(4, RoundingMode.HALF_UP));
/*  321:     */         }
/*  322:     */       }
/*  323:     */     }
/*  324: 361 */     super.actualizarProducto(dfc, producto);
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void agregarDetalleDesdeCodigoCabecera()
/*  328:     */   {
/*  329: 366 */     DetalleFacturaCliente dfc = super.agregarDetalleFactura();
/*  330:     */     try
/*  331:     */     {
/*  332: 369 */       actualizarInventarioProducto(dfc, null);
/*  333: 370 */       cargarProductoDesdeCodigoEnDetalle(this.codigoCabecera, dfc);
/*  334: 371 */       this.codigoCabecera = "";
/*  335:     */     }
/*  336:     */     catch (ExcepcionAS2 e)
/*  337:     */     {
/*  338: 374 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  339: 375 */       dfc.getProducto().setCodigo("");
/*  340: 376 */       dfc.getProducto().setNombre("");
/*  341: 377 */       this.codigoCabecera = "";
/*  342: 378 */       dfc.setEliminado(true);
/*  343:     */     }
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void actualizarSaldo(SelectEvent event)
/*  347:     */   {
/*  348: 383 */     DetalleFacturaCliente detalleFacturaCliente = (DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData();
/*  349: 384 */     Lote lote = (Lote)event.getObject();
/*  350: 385 */     detalleFacturaCliente.getDetalleDespachoCliente().getInventarioProducto().setLote(lote);
/*  351:     */     
/*  352: 387 */     BigDecimal saldo = this.servicioProducto.getSaldoLote(detalleFacturaCliente.getProducto().getIdProducto(), detalleFacturaCliente
/*  353: 388 */       .getDetalleDespachoCliente().getBodega().getIdBodega(), detalleFacturaCliente.getDetalleDespachoCliente().getInventarioProducto()
/*  354: 389 */       .getLote().getIdLote(), this.facturaCliente.getFecha());
/*  355: 390 */     detalleFacturaCliente.setSaldo(saldo);
/*  356:     */   }
/*  357:     */   
/*  358:     */   public void actualizarFormaPago(AjaxBehaviorEvent event)
/*  359:     */   {
/*  360: 402 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtDetalleFormaCobro.getRowData();
/*  361: 403 */     if ((detalleFormaCobro.getFormaPago().isIndicadorRetencionFuente()) || (detalleFormaCobro.getFormaPago().isIndicadorRetencionIva()))
/*  362:     */     {
/*  363: 404 */       if (this.facturaCliente != null) {
/*  364: 405 */         if (detalleFormaCobro.getFormaPago().isIndicadorRetencionFuente())
/*  365:     */         {
/*  366: 406 */           detalleFormaCobro.setValor(this.facturaCliente
/*  367: 407 */             .getTotal()
/*  368: 408 */             .subtract(this.facturaCliente.getDescuento())
/*  369: 409 */             .multiply(detalleFormaCobro
/*  370: 410 */             .getFormaPago().getPorcentajeRetencion()
/*  371: 411 */             .divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
/*  372:     */         }
/*  373:     */         else
/*  374:     */         {
/*  375: 413 */           BigDecimal montoIva = BigDecimal.ZERO;
/*  376: 414 */           montoIva = montoIva.add(this.facturaCliente.getImpuesto());
/*  377: 415 */           detalleFormaCobro.setValor(montoIva.multiply(detalleFormaCobro.getFormaPago().getPorcentajeRetencion())
/*  378: 416 */             .divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
/*  379:     */         }
/*  380:     */       }
/*  381:     */     }
/*  382:     */     else
/*  383:     */     {
/*  384: 421 */       BigDecimal valorTotal = BigDecimal.ZERO;
/*  385: 422 */       for (DetalleFormaCobro detaFormaCobro : getListaDetalleFormaCobro()) {
/*  386: 423 */         valorTotal = valorTotal.add(detaFormaCobro.getValor());
/*  387:     */       }
/*  388: 425 */       detalleFormaCobro.setValor(this.facturaCliente.getTotalFactura().subtract(valorTotal).setScale(2, RoundingMode.HALF_UP));
/*  389:     */     }
/*  390:     */   }
/*  391:     */   
/*  392:     */   private void actualizarInventarioProducto(DetalleFacturaCliente dfc, Lote lote)
/*  393:     */   {
/*  394: 437 */     if ((dfc.getProducto() != null) && (dfc.getProducto().getTipoProducto() != null) && 
/*  395: 438 */       (dfc.getProducto().getTipoProducto().equals(TipoProducto.ARTICULO)))
/*  396:     */     {
/*  397: 440 */       if (dfc.getDetalleDespachoCliente() == null)
/*  398:     */       {
/*  399: 441 */         DetalleDespachoCliente ddc = new DetalleDespachoCliente();
/*  400: 442 */         dfc.setDetalleDespachoCliente(ddc);
/*  401: 443 */         dfc.getDetalleDespachoCliente().setDespachoCliente(getFacturaCliente().getDespachoCliente());
/*  402: 444 */         getFacturaCliente().getDespachoCliente().getListaDetalleDespachoCliente().add(ddc);
/*  403:     */       }
/*  404:     */       InventarioProducto ip;
/*  405: 448 */       if (dfc.getDetalleDespachoCliente().getInventarioProducto() == null)
/*  406:     */       {
/*  407: 449 */         InventarioProducto ip = new InventarioProducto();
/*  408: 450 */         ip.setDetalleDespachoCliente(dfc.getDetalleDespachoCliente());
/*  409: 451 */         dfc.getDetalleDespachoCliente().setInventarioProducto(ip);
/*  410:     */       }
/*  411:     */       else
/*  412:     */       {
/*  413: 454 */         ip = dfc.getDetalleDespachoCliente().getInventarioProducto();
/*  414:     */       }
/*  415: 456 */       ip.setLote(lote);
/*  416:     */     }
/*  417:     */   }
/*  418:     */   
/*  419:     */   public DetalleFacturaCliente agregarDetalleFactura()
/*  420:     */   {
/*  421: 468 */     DetalleFacturaCliente dfc = super.agregarDetalleFactura();
/*  422: 469 */     dfc.setCantidad(ParametrosSistema.getCantidadFacturaClienteAgil(this.facturaCliente.getIdOrganizacion()));
/*  423: 470 */     actualizarInventarioProducto(dfc, null);
/*  424: 471 */     return dfc;
/*  425:     */   }
/*  426:     */   
/*  427:     */   public String crearCliente()
/*  428:     */   {
/*  429:     */     try
/*  430:     */     {
/*  431: 482 */       Empresa empresa = this.servicioEmpresa.crearClienteConDatosBasicos(this.identificacionCliente, this.tipoIdentificacionCliente, this.nombreCliente, this.direccionCliente, this.telefonoCliente, 
/*  432: 483 */         AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), this.ciudad, this.agenteComercial, this.email);
/*  433:     */       
/*  434:     */ 
/*  435: 486 */       this.identificacionCliente = null;
/*  436:     */       
/*  437: 488 */       this.nombreCliente = null;
/*  438: 489 */       this.direccionCliente = null;
/*  439: 490 */       this.telefonoCliente = null;
/*  440: 491 */       this.agenteComercial = null;
/*  441: 492 */       this.email = null;
/*  442:     */       
/*  443: 494 */       actualizarCliente(empresa);
/*  444: 495 */       setRenderClienteNuevo(false);
/*  445: 496 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  446:     */     }
/*  447:     */     catch (ExcepcionAS2 e)
/*  448:     */     {
/*  449: 498 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  450: 499 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  451:     */     }
/*  452:     */     catch (Exception ex)
/*  453:     */     {
/*  454: 501 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  455: 502 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  456:     */     }
/*  457: 505 */     return "";
/*  458:     */   }
/*  459:     */   
/*  460:     */   public String actualizarDocumentoCobro()
/*  461:     */   {
/*  462: 515 */     if ((this.documento != null) && (this.documento.getIdDocumento() != 0))
/*  463:     */     {
/*  464: 516 */       Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.documento.getIdDocumento()));
/*  465: 517 */       this.cobro.setDocumento(documento);
/*  466: 518 */       this.cobro.setEstado(Estado.ELABORADO);
/*  467: 519 */       this.cobro.setNumero("");
/*  468:     */     }
/*  469: 522 */     return "";
/*  470:     */   }
/*  471:     */   
/*  472:     */   public DetalleFacturaCliente cargarProducto()
/*  473:     */   {
/*  474: 533 */     DetalleFacturaCliente dfc = super.cargarProducto();
/*  475: 537 */     if (dfc.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
/*  476: 538 */       dfc.setCantidad(ParametrosSistema.getCantidadFacturaClienteAgil(this.facturaCliente.getIdOrganizacion()));
/*  477:     */     }
/*  478: 540 */     actualizarInventarioProducto(dfc, null);
/*  479: 542 */     if (dfc.getProducto().getTipoProducto() == TipoProducto.ARTICULO) {
/*  480: 544 */       if (getListaProductoBean().getSaldoProductoLote() == null)
/*  481:     */       {
/*  482: 545 */         if (AppUtil.getBodega() != null) {
/*  483: 546 */           dfc.getDetalleDespachoCliente().setBodega(AppUtil.getBodega());
/*  484:     */         } else {
/*  485: 548 */           dfc.getDetalleDespachoCliente().setBodega(dfc.getProducto().getBodegaVenta());
/*  486:     */         }
/*  487:     */       }
/*  488:     */       else
/*  489:     */       {
/*  490: 553 */         DetalleDespachoCliente detalleDespachoCliente = dfc.getDetalleDespachoCliente();
/*  491: 555 */         if (detalleDespachoCliente != null)
/*  492:     */         {
/*  493: 556 */           dfc.setCantidad(getListaProductoBean().getSaldoProductoLote().getSaldo().setScale(4, RoundingMode.HALF_UP));
/*  494: 557 */           dfc.setSaldo(dfc.getCantidad());
/*  495: 558 */           detalleDespachoCliente.setBodega(getListaProductoBean().getSaldoProductoLote().getBodega());
/*  496: 559 */           detalleDespachoCliente.setCantidad(dfc.getCantidad());
/*  497: 560 */           detalleDespachoCliente.setSaldo(detalleDespachoCliente.getCantidad());
/*  498:     */           
/*  499: 562 */           detalleDespachoCliente.getInventarioProducto().setBodega(detalleDespachoCliente.getBodega());
/*  500: 563 */           detalleDespachoCliente.getInventarioProducto().setLote(getListaProductoBean().getSaldoProductoLote().getLote());
/*  501: 564 */           detalleDespachoCliente.getInventarioProducto().setCantidad(detalleDespachoCliente.getCantidad());
/*  502:     */         }
/*  503:     */       }
/*  504:     */     }
/*  505: 570 */     totalizar();
/*  506:     */     
/*  507: 572 */     getListaProductoBean().setProducto(null);
/*  508: 573 */     getListaProductoBean().setSaldoProductoLote(null);
/*  509:     */     
/*  510: 575 */     return dfc;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public String crearDetalleFormaCobro()
/*  514:     */   {
/*  515: 585 */     DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/*  516: 586 */     detalleFormaCobro.setCaja(AppUtil.getCaja());
/*  517: 587 */     detalleFormaCobro.setCobro(getCobro());
/*  518: 588 */     detalleFormaCobro.setCuentaBancariaOrganizacion(new CuentaBancariaOrganizacion());
/*  519: 589 */     detalleFormaCobro.setFormaPago(new FormaPago());
/*  520: 590 */     detalleFormaCobro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  521: 591 */     detalleFormaCobro.setIdSucursal(AppUtil.getSucursal().getId());
/*  522: 592 */     detalleFormaCobro.setValor(BigDecimal.ZERO);
/*  523:     */     
/*  524: 594 */     getCobro().getListaDetalleFormaCobro().add(detalleFormaCobro);
/*  525:     */     
/*  526: 596 */     return "";
/*  527:     */   }
/*  528:     */   
/*  529:     */   public String creacionLote()
/*  530:     */   {
/*  531: 600 */     this.detalleFacturaClienteSeleccionado = ((DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData());
/*  532: 601 */     this.loteCrear = new Lote();
/*  533: 602 */     this.loteCrear.setActivo(true);
/*  534: 603 */     this.loteCrear.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  535: 604 */     this.loteCrear.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  536: 605 */     this.loteCrear.setProducto(this.detalleFacturaClienteSeleccionado.getProducto());
/*  537: 606 */     return "";
/*  538:     */   }
/*  539:     */   
/*  540:     */   public String guardarLote()
/*  541:     */   {
/*  542:     */     try
/*  543:     */     {
/*  544: 611 */       this.servicioLote.guardar(this.loteCrear);
/*  545: 612 */       this.detalleFacturaClienteSeleccionado.getInventarioProducto().setLote(this.loteCrear);
/*  546: 613 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  547:     */     }
/*  548:     */     catch (Exception e)
/*  549:     */     {
/*  550: 615 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  551: 616 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  552:     */     }
/*  553: 618 */     return "";
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void cargarDetallePedidoListener()
/*  557:     */     throws ExcepcionAS2
/*  558:     */   {
/*  559: 628 */     for (Iterator localIterator1 = getFacturaCliente().getListaDetalleFacturaCliente().iterator(); localIterator1.hasNext();)
/*  560:     */     {
/*  561: 628 */       ddc = (DetalleFacturaCliente)localIterator1.next();
/*  562: 629 */       ddc.setEliminado(true);
/*  563: 630 */       for (ImpuestoProductoFacturaCliente impuesto : ddc.getListaImpuestoProductoFacturaCliente()) {
/*  564: 631 */         impuesto.setEliminado(true);
/*  565:     */       }
/*  566:     */     }
/*  567:     */     DetalleFacturaCliente ddc;
/*  568: 635 */     if (this.facturaCliente.getPedidoCliente() != null)
/*  569:     */     {
/*  570: 636 */       getFacturaCliente().setZona(this.facturaCliente.getPedidoCliente().getZona());
/*  571: 637 */       getFacturaCliente().setCondicionPago(this.facturaCliente.getPedidoCliente().getCondicionPago());
/*  572: 638 */       getFacturaCliente().setAgenteComercial(this.facturaCliente.getPedidoCliente().getAgenteComercial());
/*  573: 639 */       getFacturaCliente().setNumeroCuotas(this.facturaCliente.getPedidoCliente().getNumeroCuotas());
/*  574: 640 */       getFacturaCliente().setCanal(this.facturaCliente.getPedidoCliente().getCanal());
/*  575: 641 */       getFacturaCliente().setDescripcion(this.facturaCliente.getPedidoCliente().getDescripcion());
/*  576: 642 */       getFacturaCliente().setSubempresa(this.facturaCliente.getPedidoCliente().getSubempresa());
/*  577: 643 */       getFacturaCliente().setProyecto(this.facturaCliente.getPedidoCliente().getProyecto());
/*  578: 644 */       cargarDirecciones(this.facturaCliente.getEmpresa());
/*  579: 645 */       getFacturaCliente().setDireccionEmpresa(this.facturaCliente.getPedidoCliente().getDireccionEmpresa());
/*  580:     */       
/*  581:     */ 
/*  582: 648 */       Object listaDPC = this.servicioPedidoCliente.obtenerListaDetallePedidoPorFacturar(this.facturaCliente.getPedidoCliente().getId());
/*  583: 650 */       for (DetallePedidoCliente dpc : (List)listaDPC)
/*  584:     */       {
/*  585: 652 */         DetalleFacturaCliente dfc = new DetalleFacturaCliente();
/*  586: 653 */         dfc.setDetallePedidoCliente(dpc);
/*  587: 654 */         dfc.setFacturaCliente(this.facturaCliente);
/*  588: 655 */         dfc.setUnidadVenta(dpc.getUnidadVenta());
/*  589: 656 */         dfc.setCantidad(dpc.getCantidadPorFacturar());
/*  590: 657 */         dfc.setIndicadorPorcentajeIce(dpc.isIndicadorPorcentajeIce());
/*  591: 658 */         dfc.setCodigoIce(dpc.getCodigoIce());
/*  592: 659 */         dfc.setIceLinea(dpc.getIceLinea());
/*  593: 660 */         dfc.setIce(dpc.getIce());
/*  594: 661 */         dfc.setPrecio(dpc.getPrecio());
/*  595: 662 */         dfc.setDescuento(dpc.getDescuento());
/*  596: 663 */         dfc.setPorcentajeDescuento(dpc.getPorcentajeDescuento());
/*  597: 664 */         dfc.setDescripcion(dpc.getDescripcion());
/*  598: 665 */         getFacturaCliente().getListaDetalleFacturaCliente().add(dfc);
/*  599: 666 */         if (dfc.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
/*  600: 667 */           dfc.setCantidad(ParametrosSistema.getCantidadFacturaClienteAgil(this.facturaCliente.getIdOrganizacion()));
/*  601:     */         }
/*  602: 669 */         super.actualizarProducto(dfc, dpc.getProducto(), false);
/*  603:     */         
/*  604: 671 */         Producto producto = null;
/*  605: 672 */         producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(dpc.getProducto().getCodigo(), 
/*  606: 673 */           AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  607: 674 */         super.actualizarProducto(dfc, producto, false);
/*  608: 675 */         actualizarInventarioProducto(dfc, null);
/*  609: 676 */         if (dfc.getProducto().getTipoProducto() == TipoProducto.ARTICULO)
/*  610:     */         {
/*  611: 677 */           if (AppUtil.getBodega() != null) {
/*  612: 678 */             dfc.getDetalleDespachoCliente().setBodega(AppUtil.getBodega());
/*  613:     */           } else {
/*  614: 680 */             dfc.getDetalleDespachoCliente().setBodega(dfc.getProducto().getBodegaVenta());
/*  615:     */           }
/*  616: 682 */           if (producto.getLote() != null)
/*  617:     */           {
/*  618: 683 */             dfc.getDetalleDespachoCliente().getInventarioProducto().setLote(producto.getLote());
/*  619: 684 */             BigDecimal saldo = this.servicioProducto.getSaldoLote(producto.getIdProducto(), dfc
/*  620: 685 */               .getDetalleDespachoCliente().getBodega().getIdBodega(), producto.getLote().getIdLote(), this.facturaCliente.getFecha());
/*  621: 686 */             dfc.setSaldo(saldo);
/*  622: 688 */             if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion().equals(TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA)) {
/*  623: 689 */               dfc.setCantidad(saldo.setScale(2, RoundingMode.HALF_UP));
/*  624:     */             }
/*  625:     */           }
/*  626:     */         }
/*  627:     */       }
/*  628:     */     }
/*  629: 699 */     actualizarImpuestosTodos();
/*  630:     */     
/*  631: 701 */     cargarSubempresas();
/*  632:     */   }
/*  633:     */   
/*  634:     */   public String eliminarFormaCobro()
/*  635:     */   {
/*  636: 711 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtDetalleFormaCobro.getRowData();
/*  637: 712 */     detalleFormaCobro.setEliminado(true);
/*  638: 713 */     return "";
/*  639:     */   }
/*  640:     */   
/*  641:     */   public List<Lote> autocompletarLotes(String consulta)
/*  642:     */   {
/*  643: 718 */     DetalleFacturaCliente detalleFacturaCliente = (DetalleFacturaCliente)getDtDetalleFacturaCliente().getRowData();
/*  644: 719 */     return this.servicioLote.autocompletarLote(detalleFacturaCliente.getProducto(), consulta);
/*  645:     */   }
/*  646:     */   
/*  647:     */   public List<Documento> getListaDocumentoDespacho()
/*  648:     */   {
/*  649: 726 */     if (this.listaDocumentoDespacho == null) {
/*  650:     */       try
/*  651:     */       {
/*  652: 728 */         this.listaDocumentoDespacho = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DESPACHO_BODEGA);
/*  653:     */       }
/*  654:     */       catch (ExcepcionAS2 e)
/*  655:     */       {
/*  656: 730 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  657:     */       }
/*  658:     */     }
/*  659: 733 */     return this.listaDocumentoDespacho;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public void setListaDocumentoDespacho(List<Documento> listaDocumentoDespacho)
/*  663:     */   {
/*  664: 741 */     this.listaDocumentoDespacho = listaDocumentoDespacho;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public String getIdentificacionCliente()
/*  668:     */   {
/*  669: 748 */     return this.identificacionCliente;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public void setIdentificacionCliente(String identificacionCliente)
/*  673:     */   {
/*  674: 756 */     this.identificacionCliente = identificacionCliente;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public TipoIdentificacion getTipoIdentificacionCliente()
/*  678:     */   {
/*  679: 763 */     return this.tipoIdentificacionCliente;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public void setTipoIdentificacionCliente(TipoIdentificacion tipoIdentificacionCliente)
/*  683:     */   {
/*  684: 771 */     this.tipoIdentificacionCliente = tipoIdentificacionCliente;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/*  688:     */   {
/*  689: 778 */     if (this.listaTipoIdentificacion == null) {
/*  690: 779 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/*  691:     */     }
/*  692: 781 */     return this.listaTipoIdentificacion;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/*  696:     */   {
/*  697: 789 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public String getNombreCliente()
/*  701:     */   {
/*  702: 796 */     return this.nombreCliente;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public void setNombreCliente(String nombreCliente)
/*  706:     */   {
/*  707: 804 */     this.nombreCliente = nombreCliente;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public String getDireccionCliente()
/*  711:     */   {
/*  712: 811 */     return this.direccionCliente;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void setDireccionCliente(String direccionCliente)
/*  716:     */   {
/*  717: 819 */     this.direccionCliente = direccionCliente;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public String getTelefonoCliente()
/*  721:     */   {
/*  722: 826 */     return this.telefonoCliente;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setTelefonoCliente(String telefonoCliente)
/*  726:     */   {
/*  727: 834 */     this.telefonoCliente = telefonoCliente;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public Cobro getCobro()
/*  731:     */   {
/*  732: 843 */     return this.cobro;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void setCobro(Cobro cobro)
/*  736:     */   {
/*  737: 853 */     this.cobro = cobro;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public List<DetalleFormaCobro> getListaDetalleFormaCobro()
/*  741:     */   {
/*  742: 862 */     List<DetalleFormaCobro> lista = new ArrayList();
/*  743: 864 */     for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/*  744: 865 */       if (!detalleFormaCobro.isEliminado()) {
/*  745: 866 */         lista.add(detalleFormaCobro);
/*  746:     */       }
/*  747:     */     }
/*  748: 869 */     return lista;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public DataTable getDtDetalleFormaCobro()
/*  752:     */   {
/*  753: 878 */     return this.dtDetalleFormaCobro;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setDtDetalleFormaCobro(DataTable dtDetalleFormaCobro)
/*  757:     */   {
/*  758: 888 */     this.dtDetalleFormaCobro = dtDetalleFormaCobro;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacion()
/*  762:     */   {
/*  763: 897 */     if (this.listaCuentaBancariaOrganizacion == null) {
/*  764: 898 */       this.listaCuentaBancariaOrganizacion = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo("idCuentaBancariaOrganizacion", false, null);
/*  765:     */     }
/*  766: 900 */     return this.listaCuentaBancariaOrganizacion;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void setListaCuentaBancariaOrganizacion(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacion)
/*  770:     */   {
/*  771: 910 */     this.listaCuentaBancariaOrganizacion = listaCuentaBancariaOrganizacion;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public void actualizarCuentaBancaria(AjaxBehaviorEvent event)
/*  775:     */   {
/*  776: 921 */     DetalleFormaCobro detalleFormaCobro = (DetalleFormaCobro)this.dtDetalleFormaCobro.getRowData();
/*  777: 922 */     detalleFormaCobro.setCuentaContableFormaCobro(detalleFormaCobro.getCuentaBancariaOrganizacion().getCuentaContableBanco());
/*  778: 923 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaFormaPago(this.servicioCuentaBancariaOrganizacion
/*  779: 924 */       .cargarDetalle(detalleFormaCobro.getCuentaBancariaOrganizacion().getId()).getListaFormaPago());
/*  780: 925 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaCuentaContableCruceCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion
/*  781: 926 */       .cargarDetalle(detalleFormaCobro.getCuentaBancariaOrganizacion().getId())
/*  782: 927 */       .getListaCuentaContableCruceCuentaBancariaOrganizacion());
/*  783:     */     
/*  784: 929 */     List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago = new ArrayList();
/*  785: 930 */     for (FormaPagoCuentaBancariaOrganizacion f : detalleFormaCobro.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/*  786: 931 */       if (f.isIndicadorCliente()) {
/*  787: 932 */         listaFormaPago.add(f);
/*  788:     */       }
/*  789:     */     }
/*  790: 935 */     detalleFormaCobro.getCuentaBancariaOrganizacion().setListaFormaPago(listaFormaPago);
/*  791:     */   }
/*  792:     */   
/*  793:     */   public BigDecimal getTotalLiquidarFormaCobro()
/*  794:     */   {
/*  795: 945 */     this.totalLiquidarFormaCobro = BigDecimal.ZERO;
/*  796: 946 */     for (DetalleFormaCobro detalleFormaCobro : getCobro().getListaDetalleFormaCobro()) {
/*  797: 947 */       if (!detalleFormaCobro.isEliminado()) {
/*  798: 948 */         this.totalLiquidarFormaCobro = this.totalLiquidarFormaCobro.add(detalleFormaCobro.getValor());
/*  799:     */       }
/*  800:     */     }
/*  801: 952 */     return this.totalLiquidarFormaCobro;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setTotalLiquidarFormaCobro(BigDecimal totalLiquidarFormaCobro)
/*  805:     */   {
/*  806: 962 */     this.totalLiquidarFormaCobro = totalLiquidarFormaCobro;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public Ciudad getCiudad()
/*  810:     */   {
/*  811: 971 */     return this.ciudad;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public void setCiudad(Ciudad ciudad)
/*  815:     */   {
/*  816: 981 */     this.ciudad = ciudad;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public List<Ciudad> getListaCiudad()
/*  820:     */   {
/*  821: 990 */     if (this.listaCiudad == null) {
/*  822: 991 */       this.listaCiudad = this.servicioCiudad.obtenerListaCombo("nombre", true, null);
/*  823:     */     }
/*  824: 993 */     return this.listaCiudad;
/*  825:     */   }
/*  826:     */   
/*  827:     */   public void setListaCiudad(List<Ciudad> listaCiudad)
/*  828:     */   {
/*  829:1003 */     this.listaCiudad = listaCiudad;
/*  830:     */   }
/*  831:     */   
/*  832:     */   public Documento getDocumento()
/*  833:     */   {
/*  834:1012 */     return this.documento;
/*  835:     */   }
/*  836:     */   
/*  837:     */   public void setDocumento(Documento documento)
/*  838:     */   {
/*  839:1022 */     this.documento = documento;
/*  840:     */   }
/*  841:     */   
/*  842:     */   public List<Documento> getListaDocumento()
/*  843:     */   {
/*  844:1031 */     if (this.listaDocumento == null) {
/*  845:     */       try
/*  846:     */       {
/*  847:1033 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.COBRO_CLIENTE);
/*  848:     */       }
/*  849:     */       catch (ExcepcionAS2 e)
/*  850:     */       {
/*  851:1035 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  852:     */       }
/*  853:     */     }
/*  854:1038 */     return this.listaDocumento;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public void setListaDocumento(List<Documento> listaDocumento)
/*  858:     */   {
/*  859:1048 */     this.listaDocumento = listaDocumento;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public String getCodigoCabecera()
/*  863:     */   {
/*  864:1057 */     return this.codigoCabecera;
/*  865:     */   }
/*  866:     */   
/*  867:     */   public void setCodigoCabecera(String codigoCabecera)
/*  868:     */   {
/*  869:1067 */     this.codigoCabecera = codigoCabecera;
/*  870:     */   }
/*  871:     */   
/*  872:     */   public Lote getLoteCrear()
/*  873:     */   {
/*  874:1076 */     if (this.loteCrear == null) {
/*  875:1077 */       this.loteCrear = new Lote();
/*  876:     */     }
/*  877:1079 */     return this.loteCrear;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public void setLoteCrear(Lote loteCrear)
/*  881:     */   {
/*  882:1089 */     this.loteCrear = loteCrear;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public DetalleFacturaCliente getDetalleFacturaClienteSeleccionado()
/*  886:     */   {
/*  887:1098 */     return this.detalleFacturaClienteSeleccionado;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public void setDetalleFacturaClienteSeleccionado(DetalleFacturaCliente detalleFacturaClienteSeleccionado)
/*  891:     */   {
/*  892:1108 */     this.detalleFacturaClienteSeleccionado = detalleFacturaClienteSeleccionado;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public EntidadUsuario getAgenteComercial()
/*  896:     */   {
/*  897:1112 */     return this.agenteComercial;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/*  901:     */   {
/*  902:1116 */     this.agenteComercial = agenteComercial;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public boolean isRenderClienteNuevo()
/*  906:     */   {
/*  907:1120 */     return this.renderClienteNuevo;
/*  908:     */   }
/*  909:     */   
/*  910:     */   public void setRenderClienteNuevo(boolean renderClienteNuevo)
/*  911:     */   {
/*  912:1124 */     this.renderClienteNuevo = renderClienteNuevo;
/*  913:     */   }
/*  914:     */   
/*  915:     */   public String getEmail()
/*  916:     */   {
/*  917:1128 */     return this.email;
/*  918:     */   }
/*  919:     */   
/*  920:     */   public void setEmail(String email)
/*  921:     */   {
/*  922:1132 */     this.email = email;
/*  923:     */   }
/*  924:     */   
/*  925:     */   public String eliminarDetalle()
/*  926:     */   {
/*  927:1136 */     DetalleFacturaCliente dfc = (DetalleFacturaCliente)this.dtDetalleFacturaCliente.getRowData();
/*  928:     */     
/*  929:1138 */     dfc.setEliminado(true);
/*  930:1139 */     dfc.setCantidad(BigDecimal.ZERO);
/*  931:1141 */     if (dfc.getDetalleDespachoCliente() != null) {
/*  932:1142 */       dfc.getDetalleDespachoCliente().setEliminado(true);
/*  933:     */     }
/*  934:1145 */     for (ImpuestoProductoFacturaCliente ipfc : dfc.getListaImpuestoProductoFacturaCliente()) {
/*  935:1146 */       ipfc.setEliminado(true);
/*  936:     */     }
/*  937:1149 */     totalizar();
/*  938:     */     
/*  939:1151 */     return "";
/*  940:     */   }
/*  941:     */   
/*  942:     */   public DetalleFormaCobro getDetalleFormaCobroSeleccionado()
/*  943:     */   {
/*  944:1155 */     return this.detalleFormaCobroSeleccionado;
/*  945:     */   }
/*  946:     */   
/*  947:     */   public void setDetalleFormaCobroSeleccionado(DetalleFormaCobro detalleFormaCobroSeleccionado)
/*  948:     */   {
/*  949:1159 */     this.detalleFormaCobroSeleccionado = detalleFormaCobroSeleccionado;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public String actualizarVoucher()
/*  953:     */   {
/*  954:     */     try
/*  955:     */     {
/*  956:1164 */       this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(this.facturaCliente);
/*  957:     */     }
/*  958:     */     catch (ExcepcionAS2 e)
/*  959:     */     {
/*  960:1167 */       e.printStackTrace();
/*  961:     */     }
/*  962:1169 */     setDetalleFormaCobroSeleccionado((DetalleFormaCobro)this.dtDetalleFormaCobro.getRowData());
/*  963:1170 */     this.detalleFormaCobroSeleccionado.setPuntoVenta(AppUtil.getPuntoDeVenta());
/*  964:1171 */     this.detalleFormaCobroSeleccionado.setFechaVoucher(this.cobro.getFecha());
/*  965:1172 */     this.detalleFormaCobroSeleccionado.setFacturaCliente(getFacturaCliente());
/*  966:1173 */     this.servicioCobro.actualizarVoucher(getCobro(), getDetalleFormaCobroSeleccionado(), AppUtil.getPuntoDeVenta(), getFacturaCliente());
/*  967:1174 */     return null;
/*  968:     */   }
/*  969:     */   
/*  970:     */   public List<TarjetaCredito> getListaTarjetaCredito()
/*  971:     */   {
/*  972:1178 */     if (this.listaTarjetaCredito == null)
/*  973:     */     {
/*  974:1179 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/*  975:1180 */       this.listaTarjetaCredito = this.servicioTarjetaCredito.obtenerListaCombo("nombre", false, filtros);
/*  976:1182 */       for (TarjetaCredito tc : this.listaTarjetaCredito) {
/*  977:1183 */         cargarPlanTarjeta(tc);
/*  978:     */       }
/*  979:     */     }
/*  980:1187 */     return this.listaTarjetaCredito;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void cargarPlanTarjetaListener(DetalleFormaCobro voucher)
/*  984:     */   {
/*  985:1190 */     cargarPlanTarjeta(voucher.getTarjetaCredito());
/*  986:     */   }
/*  987:     */   
/*  988:     */   public void cargarPlanTarjeta(TarjetaCredito tc)
/*  989:     */   {
/*  990:1195 */     if (tc != null)
/*  991:     */     {
/*  992:1197 */       TarjetaCredito tcMapa = (TarjetaCredito)this.hmTarjeta.get(Integer.valueOf(tc.getId()));
/*  993:1199 */       if (tcMapa == null)
/*  994:     */       {
/*  995:1201 */         tcMapa = this.servicioTarjetaCredito.cargarDetalle(tc.getId());
/*  996:     */         
/*  997:1203 */         Map<Integer, PlanTarjetaCredito> hmPlan = new HashMap();
/*  998:1205 */         for (VersionComision version : tcMapa.getListaVersionComision()) {
/*  999:1206 */           for (Comision com : version.getListaComision()) {
/* 1000:1207 */             hmPlan.put(Integer.valueOf(com.getPlanTarjetaCredito().getId()), com.getPlanTarjetaCredito());
/* 1001:     */           }
/* 1002:     */         }
/* 1003:1211 */         tcMapa.getListaPlanTarjetaCredito().addAll(hmPlan.values());
/* 1004:     */         
/* 1005:1213 */         this.hmTarjeta.put(Integer.valueOf(tcMapa.getId()), tcMapa);
/* 1006:     */       }
/* 1007:1216 */       if (tc.getListaPlanTarjetaCredito().isEmpty()) {
/* 1008:1217 */         tc.getListaPlanTarjetaCredito().addAll(tcMapa.getListaPlanTarjetaCredito());
/* 1009:     */       }
/* 1010:     */     }
/* 1011:     */   }
/* 1012:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.FacturaClienteAgilBean
 * JD-Core Version:    0.7.0.1
 */