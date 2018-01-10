/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    4:     */ import com.asinfo.as2.controller.LanguageController;
/*    5:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*    9:     */ import com.asinfo.as2.entities.AjustePrefacturaCliente;
/*   10:     */ import com.asinfo.as2.entities.Bodega;
/*   11:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   12:     */ import com.asinfo.as2.entities.Cliente;
/*   13:     */ import com.asinfo.as2.entities.DespachoCliente;
/*   14:     */ import com.asinfo.as2.entities.DetalleAjustePrefacturaCliente;
/*   15:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   17:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   18:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   19:     */ import com.asinfo.as2.entities.Documento;
/*   20:     */ import com.asinfo.as2.entities.Empresa;
/*   21:     */ import com.asinfo.as2.entities.ImpuestoProductoPrefacturaCliente;
/*   22:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   23:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   24:     */ import com.asinfo.as2.entities.Organizacion;
/*   25:     */ import com.asinfo.as2.entities.PrefacturaCliente;
/*   26:     */ import com.asinfo.as2.entities.Producto;
/*   27:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   28:     */ import com.asinfo.as2.entities.Secuencia;
/*   29:     */ import com.asinfo.as2.entities.Subempresa;
/*   30:     */ import com.asinfo.as2.entities.Sucursal;
/*   31:     */ import com.asinfo.as2.entities.Zona;
/*   32:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   33:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   34:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   35:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   36:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   37:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   38:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   39:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   40:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   41:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   42:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   43:     */ import com.asinfo.as2.util.AppUtil;
/*   44:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   45:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente;
/*   46:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
/*   47:     */ import java.math.BigDecimal;
/*   48:     */ import java.util.ArrayList;
/*   49:     */ import java.util.Date;
/*   50:     */ import java.util.HashMap;
/*   51:     */ import java.util.Iterator;
/*   52:     */ import java.util.List;
/*   53:     */ import java.util.Map;
/*   54:     */ import javax.annotation.PostConstruct;
/*   55:     */ import javax.ejb.EJB;
/*   56:     */ import javax.faces.bean.ManagedBean;
/*   57:     */ import javax.faces.bean.ViewScoped;
/*   58:     */ import javax.faces.component.html.HtmlInputText;
/*   59:     */ import javax.faces.component.html.HtmlSelectOneMenu;
/*   60:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   61:     */ import org.apache.log4j.Logger;
/*   62:     */ import org.primefaces.component.datatable.DataTable;
/*   63:     */ import org.primefaces.event.SelectEvent;
/*   64:     */ import org.primefaces.model.LazyDataModel;
/*   65:     */ import org.primefaces.model.SortOrder;
/*   66:     */ 
/*   67:     */ @ManagedBean
/*   68:     */ @ViewScoped
/*   69:     */ public class PrefacturaClienteBean
/*   70:     */   extends DocumentoBaseClienteBean
/*   71:     */ {
/*   72:     */   private static final long serialVersionUID = 6180042509279363488L;
/*   73:     */   @EJB
/*   74:     */   private transient ServicioPrefacturaCliente servicioPrefacturaCliente;
/*   75:     */   @EJB
/*   76:     */   private transient ServicioSucursal servicioSucursal;
/*   77:     */   @EJB
/*   78:     */   private transient ServicioDespachoCliente servicioDespachoCliente;
/*   79:     */   private PrefacturaCliente prefacturaCliente;
/*   80:     */   private AjustePrefacturaCliente ajustePrefacturaCliente;
/*   81:     */   private LazyDataModel<PrefacturaCliente> listaPrefacturaCliente;
/*   82:     */   private List<Documento> listaDocumentoCliente;
/*   83:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*   84:     */   private List<Sucursal> listaSucursal;
/*   85:     */   private List<Bodega> listaBodega;
/*   86:     */   private List<Subempresa> listaSubempresa;
/*   87:     */   private List<AjustePrefacturaCliente> listaAjustePrefacturaCliente;
/*   88:  91 */   private List<DespachoCliente> listaDespachoCliente = new ArrayList();
/*   89:  92 */   private List<DespachoCliente> listaDespachoClienteNoAsignados = new ArrayList();
/*   90:     */   private DespachoCliente[] despachoClienteSeleccionados;
/*   91:     */   private DataTable dtPrefacturaCliente;
/*   92:     */   private DataTable dtDetalleAjustePrefacturaCliente;
/*   93:     */   private DataTable dtImpuestoDetalleAjustePrefactura;
/*   94:     */   private DataTable dtImpuestoResumen;
/*   95:     */   private DataTable dtDespachoCliente;
/*   96:     */   private DataTable dtAjustePrefactura;
/*   97:     */   private boolean indicadorAjuste;
/*   98:     */   
/*   99:     */   @PostConstruct
/*  100:     */   public void init()
/*  101:     */   {
/*  102: 108 */     getListaProductoBean().setIndicadorVenta(true);
/*  103: 109 */     getListaProductoBean().setActivo(true);
/*  104:     */     
/*  105: 111 */     getListaProductoBean().setTipoProducto(TipoProducto.SERVICIO);
/*  106:     */     
/*  107: 113 */     this.listaPrefacturaCliente = new LazyDataModel()
/*  108:     */     {
/*  109:     */       private static final long serialVersionUID = 7015524812052576806L;
/*  110:     */       
/*  111:     */       public List<PrefacturaCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  112:     */       {
/*  113: 119 */         List<PrefacturaCliente> lista = new ArrayList();
/*  114: 120 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  115:     */         try
/*  116:     */         {
/*  117: 123 */           lista = PrefacturaClienteBean.this.servicioPrefacturaCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  118: 124 */           PrefacturaClienteBean.this.listaPrefacturaCliente.setRowCount(PrefacturaClienteBean.this.servicioPrefacturaCliente.contarPorCriterio(filters));
/*  119:     */         }
/*  120:     */         catch (Exception e)
/*  121:     */         {
/*  122: 126 */           PrefacturaClienteBean.this.addInfoMessage(PrefacturaClienteBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/*  123: 127 */           PrefacturaClienteBean.LOG.info("ERROR AL CARGAR DATOS PEDIDO CLIENTE", e);
/*  124:     */         }
/*  125: 129 */         return lista;
/*  126:     */       }
/*  127:     */     };
/*  128:     */   }
/*  129:     */   
/*  130:     */   public String crear()
/*  131:     */   {
/*  132: 142 */     super.crear();
/*  133: 143 */     setIndicadorAjuste(false);
/*  134: 144 */     return "";
/*  135:     */   }
/*  136:     */   
/*  137:     */   public String editar()
/*  138:     */   {
/*  139: 154 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  140: 155 */     return null;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public String guardar()
/*  144:     */   {
/*  145:     */     try
/*  146:     */     {
/*  147: 166 */       if (!this.indicadorAjuste) {
/*  148: 167 */         this.prefacturaCliente.setFecha(this.ajustePrefacturaCliente.getFecha());
/*  149:     */       }
/*  150: 169 */       this.servicioPrefacturaCliente.guardar(this.prefacturaCliente);
/*  151: 170 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  152: 171 */       cargarDatos();
/*  153:     */     }
/*  154:     */     catch (ExcepcionAS2Ventas e)
/*  155:     */     {
/*  156: 174 */       e.printStackTrace();
/*  157: 175 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  158: 176 */       LOG.info("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  159:     */     }
/*  160:     */     catch (ExcepcionAS2Financiero e)
/*  161:     */     {
/*  162: 178 */       e.printStackTrace();
/*  163: 179 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  164: 180 */       LOG.info("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  165:     */     }
/*  166:     */     catch (ExcepcionAS2 e)
/*  167:     */     {
/*  168: 182 */       e.printStackTrace();
/*  169: 183 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  170: 184 */       LOG.info("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  171:     */     }
/*  172:     */     catch (Exception e)
/*  173:     */     {
/*  174: 186 */       e.printStackTrace();
/*  175: 187 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  176: 188 */       LOG.error("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  177:     */     }
/*  178: 190 */     return null;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public String eliminar()
/*  182:     */   {
/*  183:     */     try
/*  184:     */     {
/*  185: 202 */       this.servicioPrefacturaCliente.anular(this.prefacturaCliente);
/*  186: 203 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  187: 204 */       cargarDatos();
/*  188:     */     }
/*  189:     */     catch (ExcepcionAS2Ventas e)
/*  190:     */     {
/*  191: 207 */       e.printStackTrace();
/*  192: 208 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  193: 209 */       LOG.info("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  194:     */     }
/*  195:     */     catch (ExcepcionAS2Financiero e)
/*  196:     */     {
/*  197: 211 */       e.printStackTrace();
/*  198: 212 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  199: 213 */       LOG.info("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  200:     */     }
/*  201:     */     catch (Exception e)
/*  202:     */     {
/*  203: 215 */       e.printStackTrace();
/*  204: 216 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  205: 217 */       LOG.error("ERROR AL GUARDAR DATOS PREFACTURA CLIENTE", e);
/*  206:     */     }
/*  207: 219 */     return null;
/*  208:     */   }
/*  209:     */   
/*  210:     */   public String limpiar()
/*  211:     */   {
/*  212: 229 */     setEditado(false);
/*  213: 230 */     this.prefacturaCliente = new PrefacturaCliente();
/*  214: 231 */     this.prefacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  215: 232 */     this.prefacturaCliente.setSucursal(AppUtil.getSucursal());
/*  216: 233 */     this.prefacturaCliente.setBodega(AppUtil.getBodega());
/*  217: 234 */     this.prefacturaCliente.setNumero("");
/*  218: 235 */     this.prefacturaCliente.setFecha(new Date());
/*  219: 236 */     this.prefacturaCliente.setEstado(Estado.ELABORADO);
/*  220:     */     
/*  221: 238 */     Documento documento = null;
/*  222: 239 */     if ((getListaDocumentoCliente() != null) && (!getListaDocumentoCliente().isEmpty()))
/*  223:     */     {
/*  224: 240 */       documento = (Documento)getListaDocumentoCliente().get(0);
/*  225: 241 */       this.prefacturaCliente.setDocumento(documento);
/*  226: 242 */       actualizarDocumento();
/*  227:     */     }
/*  228:     */     else
/*  229:     */     {
/*  230: 244 */       documento = new Documento();
/*  231: 245 */       documento.setSecuencia(new Secuencia());
/*  232: 246 */       this.prefacturaCliente.setDocumento(documento);
/*  233:     */     }
/*  234: 249 */     this.ajustePrefacturaCliente = new AjustePrefacturaCliente();
/*  235: 250 */     this.ajustePrefacturaCliente.setActivo(true);
/*  236: 251 */     this.ajustePrefacturaCliente.setAsiento(null);
/*  237: 252 */     this.ajustePrefacturaCliente.setFecha(new Date());
/*  238: 253 */     this.ajustePrefacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  239: 254 */     this.ajustePrefacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  240: 255 */     this.ajustePrefacturaCliente.setPrefacturaCliente(this.prefacturaCliente);
/*  241:     */     
/*  242: 257 */     this.prefacturaCliente.getListaAjustePrefacturaCliente().add(this.ajustePrefacturaCliente);
/*  243:     */     
/*  244: 259 */     return null;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public String actualizarDocumento()
/*  248:     */   {
/*  249: 263 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.prefacturaCliente.getDocumento().getId()));
/*  250: 264 */     this.prefacturaCliente.setDocumento(documento);
/*  251: 265 */     return "";
/*  252:     */   }
/*  253:     */   
/*  254:     */   public String cargarDatos()
/*  255:     */   {
/*  256: 275 */     setEditado(false);
/*  257:     */     try
/*  258:     */     {
/*  259: 277 */       limpiar();
/*  260:     */     }
/*  261:     */     catch (Exception e)
/*  262:     */     {
/*  263: 279 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  264: 280 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  265:     */     }
/*  266: 282 */     return null;
/*  267:     */   }
/*  268:     */   
/*  269:     */   public String agregarDetalle()
/*  270:     */   {
/*  271: 292 */     DetalleAjustePrefacturaCliente detalle = new DetalleAjustePrefacturaCliente();
/*  272: 293 */     detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  273: 294 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/*  274: 295 */     detalle.setAjustePrefacturaCliente(this.ajustePrefacturaCliente);
/*  275: 296 */     detalle.setCantidad(BigDecimal.ZERO);
/*  276: 297 */     detalle.setPrecio(BigDecimal.ZERO);
/*  277: 298 */     detalle.setProducto(new Producto());
/*  278: 299 */     for (AjustePrefacturaCliente apc : this.prefacturaCliente.getListaAjustePrefacturaCliente()) {
/*  279: 300 */       apc.getListaDetalleAjustePrefacturaCliente().add(detalle);
/*  280:     */     }
/*  281: 302 */     return "";
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void actualizarProductoListener(AjaxBehaviorEvent event)
/*  285:     */   {
/*  286: 307 */     DetalleAjustePrefacturaCliente dpc = (DetalleAjustePrefacturaCliente)this.dtDetalleAjustePrefacturaCliente.getRowData();
/*  287:     */     
/*  288: 309 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  289:     */     try
/*  290:     */     {
/*  291: 312 */       Producto producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.PREFACTURA_CLIENTE);
/*  292:     */       
/*  293: 314 */       actualizarProducto(dpc, producto);
/*  294:     */     }
/*  295:     */     catch (ExcepcionAS2 e)
/*  296:     */     {
/*  297: 317 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  298: 318 */       dpc.getProducto().setCodigo("");
/*  299: 319 */       dpc.getProducto().setNombre("");
/*  300:     */     }
/*  301:     */   }
/*  302:     */   
/*  303:     */   public void actualizarProducto(DetalleAjustePrefacturaCliente dapc, Producto producto)
/*  304:     */   {
/*  305: 330 */     if (this.prefacturaCliente.getEmpresa() != null)
/*  306:     */     {
/*  307: 331 */       for (ImpuestoProductoPrefacturaCliente ippc : dapc.getListaImpuestoProductoPrefacturaCliente()) {
/*  308: 332 */         ippc.setEliminado(true);
/*  309:     */       }
/*  310: 335 */       dapc.setProducto(producto);
/*  311: 336 */       dapc.setUnidadVenta(producto.getUnidadVenta());
/*  312: 337 */       dapc.setAjustePrefacturaCliente(this.ajustePrefacturaCliente);
/*  313: 339 */       if (this.prefacturaCliente.getEmpresa().getCliente().isExcentoImpuestos()) {
/*  314: 340 */         dapc.setIndicadorImpuesto(false);
/*  315:     */       } else {
/*  316: 342 */         dapc.setIndicadorImpuesto(producto.isIndicadorImpuestos());
/*  317:     */       }
/*  318: 345 */       if (dapc.isIndicadorImpuesto()) {
/*  319: 346 */         obtenerImpuestosProductos(dapc.getProducto(), dapc, this.prefacturaCliente.getFecha());
/*  320:     */       }
/*  321:     */       try
/*  322:     */       {
/*  323: 350 */         Integer idZona = null;
/*  324: 351 */         if (isIndicadorListaPrecioPorZona()) {
/*  325: 353 */           if (getPrefacturaCliente().getZona() != null) {
/*  326: 354 */             idZona = Integer.valueOf(getPrefacturaCliente().getZona().getId());
/*  327:     */           }
/*  328:     */         }
/*  329: 357 */         if (this.prefacturaCliente.getEmpresa().getCliente().getListaPrecios() != null)
/*  330:     */         {
/*  331: 358 */           DetalleVersionListaPrecios dvlp = null;
/*  332:     */           try
/*  333:     */           {
/*  334: 360 */             dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.prefacturaCliente.getEmpresa().getCliente().getListaPrecios()
/*  335: 361 */               .getIdListaPrecios(), producto.getIdProducto(), this.prefacturaCliente.getFecha(), idZona, "");
/*  336: 362 */             dapc.setPrecio(dvlp.getPrecioUnitario());
/*  337:     */           }
/*  338:     */           catch (ExcepcionAS2 e)
/*  339:     */           {
/*  340: 364 */             dapc.setPrecio(BigDecimal.ZERO);
/*  341: 365 */             dapc.getProducto().setTraDescuentoPorcentajeMaximo(BigDecimal.ZERO);
/*  342:     */           }
/*  343:     */         }
/*  344:     */         else
/*  345:     */         {
/*  346: 369 */           addInfoMessage(getLanguageController().getMensaje("msg_error_empresa_lista_precios"));
/*  347:     */         }
/*  348:     */       }
/*  349:     */       catch (Exception e)
/*  350:     */       {
/*  351: 374 */         e.printStackTrace();
/*  352: 375 */         addInfoMessage("msg_error_cargar_datos");
/*  353: 376 */         LOG.error("ERROR RECUPERAR PRODUCTO", e);
/*  354:     */       }
/*  355: 378 */       totalizar();
/*  356:     */     }
/*  357:     */     else
/*  358:     */     {
/*  359: 381 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  360: 382 */       dapc.getProducto().setCodigo("");
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void obtenerImpuestosProductos(Producto producto, DetalleAjustePrefacturaCliente d, Date fecha)
/*  365:     */   {
/*  366:     */     try
/*  367:     */     {
/*  368: 396 */       producto.setCategoriaImpuesto(this.servicioCategoriaImpuesto.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/*  369:     */       
/*  370: 398 */       List<RangoImpuesto> listaRangoImpuesto = this.servicioProducto.impuestoProducto(producto, fecha);
/*  371: 400 */       for (RangoImpuesto rangoImpuesto : listaRangoImpuesto)
/*  372:     */       {
/*  373: 402 */         ImpuestoProductoPrefacturaCliente impuestoProductoPrefacturaCliente = new ImpuestoProductoPrefacturaCliente();
/*  374: 403 */         impuestoProductoPrefacturaCliente.setPorcentajeImpuesto(rangoImpuesto.getPorcentajeImpuesto());
/*  375: 404 */         impuestoProductoPrefacturaCliente.setImpuesto(rangoImpuesto.getImpuesto());
/*  376: 405 */         impuestoProductoPrefacturaCliente.setDetalleAjustePrefacturaCliente(d);
/*  377: 406 */         d.getListaImpuestoProductoPrefacturaCliente().add(impuestoProductoPrefacturaCliente);
/*  378:     */       }
/*  379:     */     }
/*  380:     */     catch (ExcepcionAS2 ex)
/*  381:     */     {
/*  382: 409 */       addInfoMessage(getLanguageController().getMensaje("msg_producto_no_encontrado"));
/*  383: 410 */       LOG.info("Error es: " + ex.getErrorMessage(ex));
/*  384:     */     }
/*  385:     */     catch (Exception e)
/*  386:     */     {
/*  387: 412 */       LOG.info(e);
/*  388:     */     }
/*  389:     */   }
/*  390:     */   
/*  391:     */   public void totalizar()
/*  392:     */   {
/*  393:     */     try
/*  394:     */     {
/*  395: 424 */       this.servicioPrefacturaCliente.totalizar(this.ajustePrefacturaCliente);
/*  396:     */     }
/*  397:     */     catch (Exception e)
/*  398:     */     {
/*  399: 426 */       LOG.error(e);
/*  400: 427 */       e.printStackTrace();
/*  401:     */     }
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void actualizarPorcentajeDescuento()
/*  405:     */   {
/*  406: 433 */     DetalleAjustePrefacturaCliente dapc = (DetalleAjustePrefacturaCliente)this.dtDetalleAjustePrefacturaCliente.getRowData();
/*  407:     */     
/*  408: 435 */     validarDescuento(dapc);
/*  409: 436 */     if (dapc.getPorcentajeDescuento().compareTo(dapc.getProducto().getTraDescuentoPorcentajeMaximo()) > 0)
/*  410:     */     {
/*  411: 437 */       dapc.setPorcentajeDescuento(dapc.getProducto().getTraDescuentoPorcentajeMaximo());
/*  412: 438 */       addErrorMessage(getLanguageController().getMensaje("msg_error_porcentaje_descuento_maximo"));
/*  413:     */     }
/*  414: 441 */     totalizar();
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void actualizarCliente(SelectEvent event)
/*  418:     */   {
/*  419: 445 */     Empresa empresa = (Empresa)event.getObject();
/*  420: 446 */     getPrefacturaCliente().setEmpresa(empresa);
/*  421:     */     
/*  422: 448 */     getPrefacturaCliente().setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  423: 451 */     if (getPrefacturaCliente().getZona() == null) {
/*  424: 452 */       getPrefacturaCliente().setZona(empresa.getCliente().getZona());
/*  425:     */     }
/*  426: 455 */     if (getPrefacturaCliente().getCondicionPago() == null) {
/*  427: 456 */       getPrefacturaCliente().setCondicionPago(empresa.getCliente().getCondicionPago());
/*  428:     */     }
/*  429: 459 */     if (getPrefacturaCliente().getNumeroCuotas() == 0) {
/*  430: 460 */       getPrefacturaCliente().setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/*  431:     */     }
/*  432: 463 */     cargarDirecciones();
/*  433: 464 */     cargarSubempresas();
/*  434: 465 */     cargarListaDespachoPorClientes();
/*  435:     */   }
/*  436:     */   
/*  437:     */   private void validarDescuento(DetalleAjustePrefacturaCliente dpc)
/*  438:     */   {
/*  439: 469 */     if (this.prefacturaCliente.getEmpresa().getCliente().getListaDescuentos() != null)
/*  440:     */     {
/*  441: 470 */       if (!this.prefacturaCliente.getEmpresa().getCliente().getListaDescuentos().isIndicadorDescuentoPorProducto())
/*  442:     */       {
/*  443: 471 */         dpc.getProducto().setTraDescuentoPorcentajeMaximo(this.servicioListaDescuentos.getPorcentajeDescuentoMaximoVigente(this.prefacturaCliente
/*  444: 472 */           .getEmpresa().getCliente().getListaDescuentos(), this.prefacturaCliente.getFecha()));
/*  445:     */       }
/*  446:     */       else
/*  447:     */       {
/*  448: 474 */         DetalleListaDescuentos detalleListaDescuentos = this.servicioListaDescuentos.getDatosListaDescuentosPorProducto(this.prefacturaCliente
/*  449: 475 */           .getEmpresa().getCliente().getListaDescuentos(), dpc.getProducto());
/*  450: 477 */         if (detalleListaDescuentos != null) {
/*  451: 478 */           dpc.getProducto().setTraDescuentoPorcentajeMaximo(detalleListaDescuentos.getPorcentajeDescuentoMaximo());
/*  452:     */         }
/*  453:     */       }
/*  454:     */     }
/*  455:     */     else {
/*  456: 482 */       dpc.getProducto().setTraDescuentoPorcentajeMaximo(BigDecimal.ZERO);
/*  457:     */     }
/*  458: 484 */     if (dpc.getPorcentajeDescuento().compareTo(dpc.getProducto().getTraDescuentoPorcentajeMaximo()) > 0)
/*  459:     */     {
/*  460: 486 */       dpc.setPorcentajeDescuento(dpc.getProducto().getTraDescuentoPorcentajeMaximo());
/*  461: 487 */       addErrorMessage(getLanguageController().getMensaje("msg_error_porcentaje_descuento_maximo"));
/*  462:     */     }
/*  463: 490 */     totalizar();
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void cargarPrefacturaConDetalle()
/*  467:     */   {
/*  468:     */     try
/*  469:     */     {
/*  470: 501 */       PrefacturaCliente prefacturaCliente = (PrefacturaCliente)this.dtPrefacturaCliente.getRowData();
/*  471: 502 */       prefacturaCliente = this.servicioPrefacturaCliente.buscarPorId(Integer.valueOf(prefacturaCliente.getId()));
/*  472: 504 */       if (prefacturaCliente.getEstado() == Estado.ELABORADO)
/*  473:     */       {
/*  474: 506 */         prefacturaCliente = this.servicioPrefacturaCliente.cargarDetalle(prefacturaCliente.getIdPrefacturaCliente());
/*  475: 508 */         for (AjustePrefacturaCliente ajustePrefacturaCliente : prefacturaCliente.getListaAjustePrefacturaCliente())
/*  476:     */         {
/*  477: 509 */           this.ajustePrefacturaCliente = ajustePrefacturaCliente;
/*  478: 510 */           for (DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente : ajustePrefacturaCliente
/*  479: 511 */             .getListaDetalleAjustePrefacturaCliente())
/*  480:     */           {
/*  481: 512 */             detalleAjustePrefacturaCliente.setIdDetalleAjustePrefacturaCliente(0);
/*  482: 513 */             for (ImpuestoProductoPrefacturaCliente impuestoProductoPrefacturaCliente : detalleAjustePrefacturaCliente
/*  483: 514 */               .getListaImpuestoProductoPrefacturaCliente()) {
/*  484: 515 */               impuestoProductoPrefacturaCliente.setIdImpuestoProductoPrefacturaCliente(0);
/*  485:     */             }
/*  486:     */           }
/*  487:     */         }
/*  488: 519 */         this.ajustePrefacturaCliente.setIdAjustePrefacturaCliente(0);
/*  489: 520 */         this.ajustePrefacturaCliente.setFecha(new Date());
/*  490: 521 */         this.ajustePrefacturaCliente.setAsiento(null);
/*  491: 522 */         setPrefacturaCliente(prefacturaCliente);
/*  492:     */         
/*  493: 524 */         cargarDirecciones();
/*  494: 525 */         cargarSubempresas();
/*  495: 526 */         cargarListaDespachoPorClientes();
/*  496: 527 */         setEditado(true);
/*  497: 528 */         setIndicadorAjuste(true);
/*  498:     */       }
/*  499:     */       else
/*  500:     */       {
/*  501: 531 */         addErrorMessage(getLanguageController().getMensaje("msg_error_ajuste_prefactura") + " " + prefacturaCliente
/*  502: 532 */           .getEstado().getNombre());
/*  503:     */       }
/*  504:     */     }
/*  505:     */     catch (Exception e)
/*  506:     */     {
/*  507: 536 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_prefactura") + " " + e.getCause());
/*  508:     */     }
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void cargarSubempresas()
/*  512:     */   {
/*  513: 541 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.prefacturaCliente.getEmpresa().getId());
/*  514:     */   }
/*  515:     */   
/*  516:     */   public void cargarDirecciones()
/*  517:     */   {
/*  518: 550 */     this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getPrefacturaCliente().getEmpresa().getId());
/*  519: 552 */     if (getPrefacturaCliente().getDireccionEmpresa() == null) {
/*  520: 555 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/*  521: 556 */         if (de.isIndicadorDireccionPrincipal())
/*  522:     */         {
/*  523: 557 */           getPrefacturaCliente().setDireccionEmpresa(de);
/*  524: 558 */           break;
/*  525:     */         }
/*  526:     */       }
/*  527: 562 */     } else if (getPrefacturaCliente().getDireccionEmpresa().isIndicadorDireccionPrincipal()) {
/*  528: 563 */       getPrefacturaCliente().setDireccionEmpresa(getPrefacturaCliente().getDireccionEmpresa());
/*  529:     */     }
/*  530:     */   }
/*  531:     */   
/*  532:     */   public void cargarProducto(Producto producto)
/*  533:     */   {
/*  534: 573 */     if (producto != null)
/*  535:     */     {
/*  536: 575 */       DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente = new DetalleAjustePrefacturaCliente();
/*  537: 576 */       detalleAjustePrefacturaCliente.setProducto(producto);
/*  538: 577 */       this.ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente().add(detalleAjustePrefacturaCliente);
/*  539: 578 */       detalleAjustePrefacturaCliente.setCantidad(producto.getTraCantidad());
/*  540: 579 */       detalleAjustePrefacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  541: 580 */       detalleAjustePrefacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  542: 581 */       actualizarProducto(detalleAjustePrefacturaCliente, producto);
/*  543:     */     }
/*  544: 584 */     getListaProductoBean().setProducto(null);
/*  545:     */   }
/*  546:     */   
/*  547:     */   public void cargarProductosDesdeDespachos()
/*  548:     */   {
/*  549: 591 */     if (getDespachoClienteSeleccionados() != null) {
/*  550: 592 */       for (DespachoCliente despachoCliente : getDespachoClienteSeleccionados())
/*  551:     */       {
/*  552: 593 */         DespachoCliente despachoConDetalle = this.servicioDespachoCliente.cargarDetalle(Integer.valueOf(despachoCliente.getIdDespachoCliente()));
/*  553: 594 */         for (DetalleDespachoCliente detalleDespachoCliente : despachoConDetalle.getListaDetalleDespachoCliente())
/*  554:     */         {
/*  555: 595 */           DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente = new DetalleAjustePrefacturaCliente();
/*  556: 596 */           detalleAjustePrefacturaCliente.setProducto(detalleDespachoCliente.getProducto());
/*  557: 597 */           this.ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente().add(detalleAjustePrefacturaCliente);
/*  558: 598 */           detalleAjustePrefacturaCliente.setCantidad(detalleDespachoCliente.getProducto().getTraCantidad());
/*  559: 599 */           detalleAjustePrefacturaCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  560: 600 */           detalleAjustePrefacturaCliente.setIdSucursal(AppUtil.getSucursal().getId());
/*  561: 601 */           detalleAjustePrefacturaCliente.setDetalleDespachoCliente(detalleDespachoCliente);
/*  562: 602 */           detalleAjustePrefacturaCliente.setCantidad(detalleDespachoCliente.getCantidad());
/*  563: 603 */           detalleAjustePrefacturaCliente.setDescripcion(detalleDespachoCliente.getDescripcion());
/*  564: 604 */           actualizarProducto(detalleAjustePrefacturaCliente, detalleDespachoCliente.getProducto());
/*  565:     */         }
/*  566:     */       }
/*  567:     */     }
/*  568: 609 */     setDespachoClienteSeleccionados(null);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/*  572:     */   {
/*  573: 614 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/*  574: 616 */     if (subempresa != null)
/*  575:     */     {
/*  576: 618 */       Empresa empresa = subempresa.getEmpresa();
/*  577:     */       
/*  578: 620 */       this.prefacturaCliente.setSubempresa(subempresa);
/*  579: 621 */       this.prefacturaCliente.setDireccionEmpresa(null);
/*  580:     */       
/*  581:     */ 
/*  582: 624 */       this.prefacturaCliente.setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  583: 625 */       this.prefacturaCliente.setZona(empresa.getCliente().getZona());
/*  584: 626 */       this.prefacturaCliente.setCondicionPago(empresa.getCliente().getCondicionPago());
/*  585: 627 */       this.prefacturaCliente.setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/*  586: 628 */       cargarDirecciones();
/*  587:     */     }
/*  588:     */   }
/*  589:     */   
/*  590:     */   public String eliminarDetalle()
/*  591:     */   {
/*  592: 641 */     DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente = (DetalleAjustePrefacturaCliente)this.dtDetalleAjustePrefacturaCliente.getRowData();
/*  593:     */     Iterator localIterator1;
/*  594:     */     ImpuestoProductoPrefacturaCliente impuestoProductoPrefacturaCliente;
/*  595:     */     int idDespachoCliente;
/*  596: 643 */     if (detalleAjustePrefacturaCliente.getDetalleDespachoCliente() == null)
/*  597:     */     {
/*  598: 644 */       detalleAjustePrefacturaCliente.setEliminado(true);
/*  599: 645 */       for (localIterator1 = detalleAjustePrefacturaCliente
/*  600: 646 */             .getListaImpuestoProductoPrefacturaCliente().iterator(); localIterator1.hasNext();)
/*  601:     */       {
/*  602: 645 */         impuestoProductoPrefacturaCliente = (ImpuestoProductoPrefacturaCliente)localIterator1.next();
/*  603:     */         
/*  604: 647 */         impuestoProductoPrefacturaCliente.setEliminado(true);
/*  605:     */       }
/*  606:     */     }
/*  607:     */     else
/*  608:     */     {
/*  609: 650 */       idDespachoCliente = detalleAjustePrefacturaCliente.getDetalleDespachoCliente().getDespachoCliente().getIdDespachoCliente();
/*  610: 652 */       for (DetalleAjustePrefacturaCliente detalleAjuste : getListaDetalleAjustePrefacturaCliente()) {
/*  611: 653 */         if ((detalleAjuste.getDetalleDespachoCliente() != null) && 
/*  612: 654 */           (detalleAjuste.getDetalleDespachoCliente().getDespachoCliente().getIdDespachoCliente() == idDespachoCliente))
/*  613:     */         {
/*  614: 655 */           for (ImpuestoProductoPrefacturaCliente impuestoProductoPrefacturaCliente : detalleAjuste
/*  615: 656 */             .getListaImpuestoProductoPrefacturaCliente()) {
/*  616: 657 */             impuestoProductoPrefacturaCliente.setEliminado(true);
/*  617:     */           }
/*  618: 659 */           detalleAjuste.setEliminado(true);
/*  619: 660 */           detalleAjuste.setCantidad(BigDecimal.ZERO);
/*  620:     */         }
/*  621:     */       }
/*  622:     */     }
/*  623: 665 */     totalizar();
/*  624:     */     
/*  625: 667 */     return "";
/*  626:     */   }
/*  627:     */   
/*  628:     */   public List<PrefacturaCliente> autocompletarPrefacturas(String consulta)
/*  629:     */   {
/*  630: 677 */     return this.servicioPrefacturaCliente.autocompletarPrefactura(consulta);
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void cargarListaDespachoPorClientes()
/*  634:     */   {
/*  635: 686 */     Map<String, String> filters = new HashMap();
/*  636: 687 */     filters.put("empresa.idEmpresa", String.valueOf(this.prefacturaCliente.getEmpresa().getIdEmpresa()));
/*  637: 688 */     filters.put("indicadorGeneradoPrefactura", "false");
/*  638: 689 */     filters.put("estado", "!=" + Estado.ANULADO);
/*  639:     */     
/*  640: 691 */     this.listaDespachoCliente = this.servicioDespachoCliente.obtenerListaCombo("numero", true, filters);
/*  641:     */   }
/*  642:     */   
/*  643:     */   public String cargarDespachosNoAsignados()
/*  644:     */   {
/*  645: 701 */     this.despachoClienteSeleccionados = null;
/*  646: 702 */     Map<Integer, DespachoCliente> despachos = new HashMap();
/*  647: 704 */     for (DespachoCliente despachoCliente : this.listaDespachoCliente) {
/*  648: 705 */       despachos.put(Integer.valueOf(despachoCliente.getId()), despachoCliente);
/*  649:     */     }
/*  650: 708 */     for (DetalleAjustePrefacturaCliente detalleAjuste : getListaDetalleAjustePrefacturaCliente()) {
/*  651: 709 */       if ((!detalleAjuste.isEliminado()) && (detalleAjuste.getDetalleDespachoCliente() != null)) {
/*  652: 710 */         despachos.remove(Integer.valueOf(detalleAjuste.getDetalleDespachoCliente().getDespachoCliente().getIdDespachoCliente()));
/*  653:     */       }
/*  654:     */     }
/*  655: 714 */     this.listaDespachoClienteNoAsignados = new ArrayList(despachos.values());
/*  656: 715 */     return "";
/*  657:     */   }
/*  658:     */   
/*  659:     */   public String cargarAjustesPorPrefactura()
/*  660:     */   {
/*  661: 724 */     PrefacturaCliente prefacturaCliente = (PrefacturaCliente)this.dtPrefacturaCliente.getRowData();
/*  662: 725 */     List<AjustePrefacturaCliente> listaAjustePrefacturaCliente = this.servicioPrefacturaCliente.getListaAjustePrefacturaCliente(prefacturaCliente
/*  663: 726 */       .getIdPrefacturaCliente());
/*  664: 727 */     setListaAjustePrefacturaCliente(listaAjustePrefacturaCliente);
/*  665: 728 */     return "";
/*  666:     */   }
/*  667:     */   
/*  668:     */   public void contabilizar(PrefacturaCliente prefacturaCliente)
/*  669:     */   {
/*  670: 732 */     List<AjustePrefacturaCliente> listaAjustePrefacturaCliente = this.servicioPrefacturaCliente.getListaAjustePrefacturaCliente(prefacturaCliente
/*  671: 733 */       .getIdPrefacturaCliente());
/*  672:     */     try
/*  673:     */     {
/*  674: 737 */       for (AjustePrefacturaCliente ajuste : listaAjustePrefacturaCliente) {
/*  675: 738 */         if (ajuste.getAsiento() == null) {
/*  676: 739 */           this.servicioPrefacturaCliente.contabilizar(ajuste);
/*  677:     */         }
/*  678:     */       }
/*  679: 743 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  680:     */     }
/*  681:     */     catch (ExcepcionAS2 e)
/*  682:     */     {
/*  683: 746 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  684:     */     }
/*  685:     */     catch (AS2Exception e)
/*  686:     */     {
/*  687: 748 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   public PrefacturaCliente getPrefacturaCliente()
/*  692:     */   {
/*  693: 760 */     return this.prefacturaCliente;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void setPrefacturaCliente(PrefacturaCliente prefacturaCliente)
/*  697:     */   {
/*  698: 770 */     this.prefacturaCliente = prefacturaCliente;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public LazyDataModel<PrefacturaCliente> getListaPrefacturaCliente()
/*  702:     */   {
/*  703: 779 */     if (this.listaPrefacturaCliente == null) {
/*  704: 780 */       cargarDatos();
/*  705:     */     }
/*  706: 782 */     return this.listaPrefacturaCliente;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setListaPrefacturaCliente(LazyDataModel<PrefacturaCliente> listaPrefacturaCliente)
/*  710:     */   {
/*  711: 792 */     this.listaPrefacturaCliente = listaPrefacturaCliente;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public List<Documento> getListaDocumentoCliente()
/*  715:     */   {
/*  716:     */     try
/*  717:     */     {
/*  718: 802 */       if (this.listaDocumentoCliente == null) {
/*  719: 803 */         this.listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.PREFACTURA_CLIENTE);
/*  720:     */       }
/*  721:     */     }
/*  722:     */     catch (ExcepcionAS2 e)
/*  723:     */     {
/*  724: 806 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  725:     */     }
/*  726: 808 */     return this.listaDocumentoCliente;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public void setListaDocumentoCliente(List<Documento> listaDocumentoCliente)
/*  730:     */   {
/*  731: 818 */     this.listaDocumentoCliente = listaDocumentoCliente;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/*  735:     */   {
/*  736: 827 */     if (this.listaDireccionEmpresa == null) {
/*  737: 828 */       this.listaDireccionEmpresa = new ArrayList();
/*  738:     */     }
/*  739: 830 */     return this.listaDireccionEmpresa;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/*  743:     */   {
/*  744: 840 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public List<Sucursal> getListaSucursal()
/*  748:     */   {
/*  749: 849 */     if (this.listaSucursal == null) {
/*  750: 850 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/*  751:     */     }
/*  752: 852 */     return this.listaSucursal;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public void setListaSucursal(List<Sucursal> listaSucursal)
/*  756:     */   {
/*  757: 862 */     this.listaSucursal = listaSucursal;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public List<Bodega> getListaBodega()
/*  761:     */   {
/*  762: 871 */     if (this.listaBodega == null) {
/*  763: 872 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/*  764:     */     }
/*  765: 874 */     return this.listaBodega;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void setListaBodega(List<Bodega> listaBodega)
/*  769:     */   {
/*  770: 884 */     this.listaBodega = listaBodega;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public List<Subempresa> getListaSubempresa()
/*  774:     */   {
/*  775: 893 */     if (this.listaSubempresa == null) {
/*  776: 894 */       this.listaSubempresa = new ArrayList();
/*  777:     */     }
/*  778: 896 */     return this.listaSubempresa;
/*  779:     */   }
/*  780:     */   
/*  781:     */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/*  782:     */   {
/*  783: 906 */     this.listaSubempresa = listaSubempresa;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public DataTable getDtPrefacturaCliente()
/*  787:     */   {
/*  788: 915 */     return this.dtPrefacturaCliente;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public void setDtPrefacturaCliente(DataTable dtPrefacturaCliente)
/*  792:     */   {
/*  793: 925 */     this.dtPrefacturaCliente = dtPrefacturaCliente;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public DataTable getDtImpuestoDetalleAjustePrefactura()
/*  797:     */   {
/*  798: 934 */     return this.dtImpuestoDetalleAjustePrefactura;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public void setDtImpuestoDetalleAjustePrefactura(DataTable dtImpuestoDetalleAjustePrefactura)
/*  802:     */   {
/*  803: 944 */     this.dtImpuestoDetalleAjustePrefactura = dtImpuestoDetalleAjustePrefactura;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public DataTable getDtImpuestoResumen()
/*  807:     */   {
/*  808: 953 */     return this.dtImpuestoResumen;
/*  809:     */   }
/*  810:     */   
/*  811:     */   public void setDtImpuestoResumen(DataTable dtImpuestoResumen)
/*  812:     */   {
/*  813: 963 */     this.dtImpuestoResumen = dtImpuestoResumen;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public AjustePrefacturaCliente getAjustePrefacturaCliente()
/*  817:     */   {
/*  818: 972 */     return this.ajustePrefacturaCliente;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public void setAjustePrefacturaCliente(AjustePrefacturaCliente ajustePrefacturaCliente)
/*  822:     */   {
/*  823: 982 */     this.ajustePrefacturaCliente = ajustePrefacturaCliente;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public DataTable getDtDetalleAjustePrefacturaCliente()
/*  827:     */   {
/*  828: 991 */     return this.dtDetalleAjustePrefacturaCliente;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public void setDtDetalleAjustePrefacturaCliente(DataTable dtDetalleAjustePrefacturaCliente)
/*  832:     */   {
/*  833:1001 */     this.dtDetalleAjustePrefacturaCliente = dtDetalleAjustePrefacturaCliente;
/*  834:     */   }
/*  835:     */   
/*  836:     */   public List<DetalleAjustePrefacturaCliente> getListaDetalleAjustePrefacturaCliente()
/*  837:     */   {
/*  838:1010 */     List<DetalleAjustePrefacturaCliente> lista = new ArrayList();
/*  839:1011 */     for (DetalleAjustePrefacturaCliente dapc : this.ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente()) {
/*  840:1012 */       if (!dapc.isEliminado()) {
/*  841:1013 */         lista.add(dapc);
/*  842:     */       }
/*  843:     */     }
/*  844:1017 */     return lista;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public List<ImpuestoProductoPrefacturaCliente> getListaImpuestoProductoPrefacturaCliente()
/*  848:     */   {
/*  849:1026 */     List<ImpuestoProductoPrefacturaCliente> lista = new ArrayList();
/*  850:1027 */     for (DetalleAjustePrefacturaCliente detalle : this.ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente()) {
/*  851:1028 */       for (ImpuestoProductoPrefacturaCliente impuesto : detalle.getListaImpuestoProductoPrefacturaCliente()) {
/*  852:1029 */         if (!impuesto.isEliminado()) {
/*  853:1030 */           lista.add(impuesto);
/*  854:     */         }
/*  855:     */       }
/*  856:     */     }
/*  857:1038 */     return lista;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public List<DespachoCliente> getListaDespachoCliente()
/*  861:     */   {
/*  862:1047 */     return this.listaDespachoCliente;
/*  863:     */   }
/*  864:     */   
/*  865:     */   public void setListaDespachoCliente(List<DespachoCliente> listaDespachoCliente)
/*  866:     */   {
/*  867:1057 */     this.listaDespachoCliente = listaDespachoCliente;
/*  868:     */   }
/*  869:     */   
/*  870:     */   public DataTable getDtDespachoCliente()
/*  871:     */   {
/*  872:1066 */     return this.dtDespachoCliente;
/*  873:     */   }
/*  874:     */   
/*  875:     */   public void setDtDespachoCliente(DataTable dtDespachoCliente)
/*  876:     */   {
/*  877:1076 */     this.dtDespachoCliente = dtDespachoCliente;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public DespachoCliente[] getDespachoClienteSeleccionados()
/*  881:     */   {
/*  882:1085 */     return this.despachoClienteSeleccionados;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void setDespachoClienteSeleccionados(DespachoCliente[] despachoClienteSeleccionados)
/*  886:     */   {
/*  887:1095 */     this.despachoClienteSeleccionados = despachoClienteSeleccionados;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public List<DespachoCliente> getListaDespachoClienteNoAsignados()
/*  891:     */   {
/*  892:1104 */     return this.listaDespachoClienteNoAsignados;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public void setListaDespachoClienteNoAsignados(List<DespachoCliente> listaDespachoClienteNoAsignados)
/*  896:     */   {
/*  897:1114 */     this.listaDespachoClienteNoAsignados = listaDespachoClienteNoAsignados;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public boolean isIndicadorAjuste()
/*  901:     */   {
/*  902:1123 */     return this.indicadorAjuste;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void setIndicadorAjuste(boolean indicadorAjuste)
/*  906:     */   {
/*  907:1133 */     this.indicadorAjuste = indicadorAjuste;
/*  908:     */   }
/*  909:     */   
/*  910:     */   public List<AjustePrefacturaCliente> getListaAjustePrefacturaCliente()
/*  911:     */   {
/*  912:1142 */     if (this.listaAjustePrefacturaCliente == null) {
/*  913:1143 */       this.listaAjustePrefacturaCliente = new ArrayList();
/*  914:     */     }
/*  915:1145 */     return this.listaAjustePrefacturaCliente;
/*  916:     */   }
/*  917:     */   
/*  918:     */   public void setListaAjustePrefacturaCliente(List<AjustePrefacturaCliente> listaAjustePrefacturaCliente)
/*  919:     */   {
/*  920:1155 */     this.listaAjustePrefacturaCliente = listaAjustePrefacturaCliente;
/*  921:     */   }
/*  922:     */   
/*  923:     */   public DataTable getDtAjustePrefactura()
/*  924:     */   {
/*  925:1164 */     return this.dtAjustePrefactura;
/*  926:     */   }
/*  927:     */   
/*  928:     */   public void setDtAjustePrefactura(DataTable dtAjustePrefactura)
/*  929:     */   {
/*  930:1174 */     this.dtAjustePrefactura = dtAjustePrefactura;
/*  931:     */   }
/*  932:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.PrefacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */