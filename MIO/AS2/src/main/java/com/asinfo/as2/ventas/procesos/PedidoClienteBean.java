/*    1:     */ package com.asinfo.as2.ventas.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*    5:     */ import com.asinfo.as2.controller.LanguageController;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   10:     */ import com.asinfo.as2.entities.Bodega;
/*   11:     */ import com.asinfo.as2.entities.Ciudad;
/*   12:     */ import com.asinfo.as2.entities.Cliente;
/*   13:     */ import com.asinfo.as2.entities.Contacto;
/*   14:     */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   15:     */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   16:     */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   17:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   18:     */ import com.asinfo.as2.entities.Documento;
/*   19:     */ import com.asinfo.as2.entities.Empresa;
/*   20:     */ import com.asinfo.as2.entities.ImpuestoProductoPedidoCliente;
/*   21:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   22:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   23:     */ import com.asinfo.as2.entities.MotivoPedidoCliente;
/*   24:     */ import com.asinfo.as2.entities.Organizacion;
/*   25:     */ import com.asinfo.as2.entities.PedidoCliente;
/*   26:     */ import com.asinfo.as2.entities.Producto;
/*   27:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   28:     */ import com.asinfo.as2.entities.Secuencia;
/*   29:     */ import com.asinfo.as2.entities.Subempresa;
/*   30:     */ import com.asinfo.as2.entities.Sucursal;
/*   31:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   32:     */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*   33:     */ import com.asinfo.as2.entities.Transportista;
/*   34:     */ import com.asinfo.as2.entities.Zona;
/*   35:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   36:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   37:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   38:     */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*   39:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   40:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   41:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   42:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   43:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   44:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*   45:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   46:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   47:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   48:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   49:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   50:     */ import com.asinfo.as2.util.AppUtil;
/*   51:     */ import com.asinfo.as2.util.RutaArchivo;
/*   52:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   53:     */ import com.asinfo.as2.utils.JsfUtil;
/*   54:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   55:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioMotivoPedidoCliente;
/*   56:     */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*   57:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*   58:     */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*   59:     */ import java.io.BufferedInputStream;
/*   60:     */ import java.io.InputStream;
/*   61:     */ import java.io.PrintStream;
/*   62:     */ import java.math.BigDecimal;
/*   63:     */ import java.math.RoundingMode;
/*   64:     */ import java.text.ParseException;
/*   65:     */ import java.text.SimpleDateFormat;
/*   66:     */ import java.util.ArrayList;
/*   67:     */ import java.util.Date;
/*   68:     */ import java.util.HashMap;
/*   69:     */ import java.util.List;
/*   70:     */ import java.util.Map;
/*   71:     */ import javax.annotation.PostConstruct;
/*   72:     */ import javax.ejb.EJB;
/*   73:     */ import javax.faces.bean.ManagedBean;
/*   74:     */ import javax.faces.bean.ViewScoped;
/*   75:     */ import javax.faces.component.html.HtmlSelectOneMenu;
/*   76:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   77:     */ import javax.validation.constraints.NotNull;
/*   78:     */ import javax.validation.constraints.Size;
/*   79:     */ import org.apache.log4j.Logger;
/*   80:     */ import org.primefaces.component.datatable.DataTable;
/*   81:     */ import org.primefaces.event.FileUploadEvent;
/*   82:     */ import org.primefaces.event.RowEditEvent;
/*   83:     */ import org.primefaces.event.SelectEvent;
/*   84:     */ import org.primefaces.model.LazyDataModel;
/*   85:     */ import org.primefaces.model.SortOrder;
/*   86:     */ import org.primefaces.model.UploadedFile;
/*   87:     */ 
/*   88:     */ @ManagedBean
/*   89:     */ @ViewScoped
/*   90:     */ public class PedidoClienteBean
/*   91:     */   extends DocumentoBaseClienteBean
/*   92:     */ {
/*   93:     */   private static final long serialVersionUID = 1L;
/*   94:     */   @EJB
/*   95:     */   protected ServicioPedidoCliente servicioPedidoCliente;
/*   96:     */   @EJB
/*   97:     */   private ServicioSucursal servicioSucursal;
/*   98:     */   @EJB
/*   99:     */   private ServicioMotivoPedidoCliente servicioMotivoPedidoCliente;
/*  100:     */   @EJB
/*  101:     */   private ServicioTransportista servicioTransportista;
/*  102:     */   @EJB
/*  103:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  104:     */   @EJB
/*  105:     */   private ServicioGenerico<PedidoCliente> servicioGenericoPedido;
/*  106:     */   @EJB
/*  107:     */   private ServicioUnidadConversion servicioUnidadConversion;
/*  108:     */   @EJB
/*  109:     */   private ServicioVerificadorVentas servicioVerificadorVentas;
/*  110:     */   protected PedidoCliente pedidoCliente;
/*  111:     */   protected LazyDataModel<PedidoCliente> listaPedidoCliente;
/*  112:     */   private List<Documento> listaDocumentoCliente;
/*  113:     */   private List<DireccionEmpresa> listaDireccionEmpresa;
/*  114:     */   private List<Sucursal> listaSucursal;
/*  115:     */   private List<MotivoPedidoCliente> listaMotivoPedidoCliente;
/*  116:     */   private List<Bodega> listaBodega;
/*  117:     */   private List<Subempresa> listaSubempresa;
/*  118:     */   private boolean indicadorRender;
/*  119:     */   private boolean indicadorRenderCerrar;
/*  120:     */   protected List<Transportista> listaTransportistaCombo;
/*  121:     */   protected DataTable dtPedidoCliente;
/*  122:     */   protected DataTable dtDetallePedidoCliente;
/*  123:     */   protected DataTable dtImpuestoDetallePedidoCliente;
/*  124:     */   protected DataTable dtImpuestoResumen;
/*  125:     */   private Integer idPedidoCliente;
/*  126:     */   private String identificacionCliente;
/*  127:     */   @NotNull
/*  128:     */   private TipoIdentificacion tipoIdentificacionCliente;
/*  129:     */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  130:     */   @Size(min=10, max=100)
/*  131:     */   private String nombreCliente;
/*  132:     */   @Size(min=2, max=50)
/*  133:     */   private String direccionCliente;
/*  134:     */   @Size(max=13)
/*  135:     */   private String telefonoCliente;
/*  136:     */   @NotNull
/*  137:     */   private Ciudad ciudad;
/*  138:     */   @NotNull
/*  139:     */   private EntidadUsuario agenteComercial;
/*  140:     */   private String email;
/*  141:     */   private boolean renderClienteNuevo;
/*  142: 152 */   private Boolean manejaMotivoPedido = null;
/*  143:     */   private List<Contacto> listaContacto;
/*  144: 156 */   protected boolean indicadorAprobar = false;
/*  145: 157 */   protected boolean indicadorMostrarTodoAprobacion = false;
/*  146: 158 */   protected boolean indicadorAprobarCompleto = true;
/*  147:     */   
/*  148:     */   @PostConstruct
/*  149:     */   public void init()
/*  150:     */   {
/*  151: 164 */     getListaProductoBean().setIndicadorVenta(true);
/*  152: 165 */     getListaProductoBean().setActivo(true);
/*  153:     */     
/*  154: 167 */     this.listaPedidoCliente = new LazyDataModel()
/*  155:     */     {
/*  156:     */       private static final long serialVersionUID = 1L;
/*  157:     */       
/*  158:     */       public List<PedidoCliente> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  159:     */       {
/*  160: 174 */         if (!PedidoClienteBean.this.indicadorAprobar) {
/*  161: 175 */           filters = PedidoClienteBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  162:     */         } else {
/*  163: 177 */           filters = PedidoClienteBean.this.agregarFiltroOrganizacion(filters);
/*  164:     */         }
/*  165: 179 */         List<PedidoCliente> lista = new ArrayList();
/*  166: 181 */         if (PedidoClienteBean.this.idPedidoCliente != null)
/*  167:     */         {
/*  168: 182 */           filters.put("idPedidoCliente", PedidoClienteBean.this.idPedidoCliente.toString());
/*  169: 183 */           PedidoClienteBean.this.idPedidoCliente = null;
/*  170:     */         }
/*  171: 186 */         String filtroFechaDespacho = (String)filters.get("fechaDespacho");
/*  172: 187 */         if (filtroFechaDespacho != null) {
/*  173: 189 */           if (filtroFechaDespacho.length() != 10) {
/*  174: 190 */             filters.remove("fechaDespacho");
/*  175:     */           } else {
/*  176:     */             try
/*  177:     */             {
/*  178: 193 */               SimpleDateFormat sdf = new SimpleDateFormat(PedidoClienteBean.this.getFormatoFecha());
/*  179: 194 */               Date fechaPago = sdf.parse(filtroFechaDespacho);
/*  180: 195 */               SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
/*  181: 196 */               filters.put("fechaDespacho", "=" + sdf2.format(fechaPago));
/*  182:     */             }
/*  183:     */             catch (ParseException e)
/*  184:     */             {
/*  185: 199 */               e.printStackTrace();
/*  186:     */             }
/*  187:     */           }
/*  188:     */         }
/*  189: 203 */         if (PedidoClienteBean.this.indicadorAprobar)
/*  190:     */         {
/*  191: 204 */           filters.put("estado", Estado.ELABORADO.toString());
/*  192:     */           
/*  193: 206 */           Usuario usuarioSesion = AppUtil.getUsuarioEnSesion();
/*  194: 207 */           if (usuarioSesion.isIndicadorAprobador())
/*  195:     */           {
/*  196: 209 */             PedidoClienteBean.this.indicadorAprobarCompleto = false;
/*  197:     */             
/*  198: 211 */             List<EntidadUsuario> listaSuperiores = PedidoClienteBean.this.servicioUsuario.buscarJerarquiaInmediata(usuarioSesion.getIdUsuario(), true, DocumentoBase.PEDIDO_CLIENTE);
/*  199:     */             
/*  200: 213 */             List<EntidadUsuario> listaSubordinados = PedidoClienteBean.this.servicioUsuario.buscarJerarquiaInmediata(usuarioSesion.getIdUsuario(), false, DocumentoBase.PEDIDO_CLIENTE);
/*  201: 215 */             if ((listaSuperiores.isEmpty()) && (listaSubordinados.isEmpty()))
/*  202:     */             {
/*  203: 217 */               JsfUtil.addErrorMessage(new AS2Exception("ERROR_NO_DEFINIDA_JERARQUIA_USUARIO_APROBADOR", new String[] {usuarioSesion
/*  204: 218 */                 .getNombreUsuario() }), "");
/*  205: 219 */               PedidoClienteBean.this.listaPedidoCliente.setRowCount(0);
/*  206: 220 */               return new ArrayList();
/*  207:     */             }
/*  208: 223 */             if ((!listaSuperiores.isEmpty()) && (listaSubordinados.isEmpty()))
/*  209:     */             {
/*  210: 225 */               if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioSesion.getTipoVisualizacion())) {
/*  211: 226 */                 filters = PedidoClienteBean.this.agregarFiltroSucursalUsuario(filters, usuarioSesion, true);
/*  212:     */               }
/*  213:     */             }
/*  214:     */             else
/*  215:     */             {
/*  216: 229 */               if (listaSuperiores.isEmpty()) {
/*  217: 230 */                 PedidoClienteBean.this.indicadorAprobarCompleto = true;
/*  218:     */               }
/*  219: 233 */               if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioSesion.getTipoVisualizacion())) {
/*  220: 234 */                 filters = PedidoClienteBean.this.agregarFiltroSucursalUsuario(filters, usuarioSesion, true);
/*  221:     */               }
/*  222: 237 */               filters.put("usuarioAutoriza", "!=" + usuarioSesion.getNombreUsuario());
/*  223: 240 */               if (!PedidoClienteBean.this.indicadorMostrarTodoAprobacion)
/*  224:     */               {
/*  225: 241 */                 filters = PedidoClienteBean.this.agregarFiltroUsuarioAutorizacion(filters, "usuarioAutoriza", listaSubordinados);
/*  226: 242 */                 filters.put("estado", Estado.APROBADO_PARCIAL.toString());
/*  227:     */               }
/*  228:     */               else
/*  229:     */               {
/*  230: 245 */                 filters.remove("estado");
/*  231: 246 */                 filters.put("OR~EST01~01~estado", Estado.ELABORADO.toString());
/*  232: 247 */                 filters.put("OR~EST01~02~estado", Estado.APROBADO_PARCIAL.toString());
/*  233:     */               }
/*  234:     */             }
/*  235:     */           }
/*  236:     */         }
/*  237: 253 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  238:     */         try
/*  239:     */         {
/*  240: 256 */           lista = PedidoClienteBean.this.servicioPedidoCliente.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  241: 257 */           PedidoClienteBean.this.listaPedidoCliente.setRowCount(PedidoClienteBean.this.servicioPedidoCliente.contarPorCriterio(filters));
/*  242:     */         }
/*  243:     */         catch (Exception e)
/*  244:     */         {
/*  245: 260 */           PedidoClienteBean.this.addInfoMessage(PedidoClienteBean.this.getLanguageController().getMensaje("msg_info_carga_datos"));
/*  246: 261 */           PedidoClienteBean.LOG.info("ERROR AL CARGAR DATOS PEDIDO CLIENTE", e);
/*  247:     */         }
/*  248: 264 */         return lista;
/*  249:     */       }
/*  250:     */     };
/*  251:     */   }
/*  252:     */   
/*  253:     */   public String autorizarVenta()
/*  254:     */   {
/*  255:     */     try
/*  256:     */     {
/*  257: 276 */       EntidadUsuario usuarioAutoriza = this.servicioUsuario.obtieneUsuarioAutorizaVentas(getUsuarioAutorizaVenta(), getClaveAutorizaVenta());
/*  258: 277 */       getPedidoCliente().setUsuarioAutoriza(usuarioAutoriza.getNombreUsuario());
/*  259: 278 */       getPedidoCliente().setIndicadorAutoriza(true);
/*  260: 279 */       addInfoMessage(getLanguageController().getMensaje("msg_info_autorizar_venta"));
/*  261:     */     }
/*  262:     */     catch (ExcepcionAS2 e)
/*  263:     */     {
/*  264: 281 */       getPedidoCliente().setUsuarioAutoriza(null);
/*  265: 282 */       getPedidoCliente().setIndicadorAutoriza(false);
/*  266: 283 */       addErrorMessage(getLanguageController().getMensaje("msg_error_autorizar_venta"));
/*  267:     */     }
/*  268: 285 */     return "";
/*  269:     */   }
/*  270:     */   
/*  271:     */   public String editar()
/*  272:     */   {
/*  273: 296 */     if (getPedidoCliente().getId() > 0) {
/*  274:     */       try
/*  275:     */       {
/*  276: 300 */         this.servicioPedidoCliente.esEditable(this.pedidoCliente);
/*  277:     */         
/*  278: 302 */         this.pedidoCliente = this.servicioPedidoCliente.cargarDetalle(this.pedidoCliente.getId());
/*  279: 303 */         cargarDirecciones();
/*  280: 304 */         totalizar();
/*  281:     */         
/*  282: 306 */         setEditado(true);
/*  283:     */       }
/*  284:     */       catch (ExcepcionAS2Ventas e)
/*  285:     */       {
/*  286: 309 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  287: 310 */         LOG.error("ERROR AL EDITAR PEDIDO DE CLIENTE: " + e.getErrorMessage(e));
/*  288:     */       }
/*  289:     */       catch (ExcepcionAS2Financiero e)
/*  290:     */       {
/*  291: 313 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  292: 314 */         LOG.info("ERROR AL EDITAR PEDIDO DE CLIENTE:", e);
/*  293:     */       }
/*  294:     */       catch (Exception e)
/*  295:     */       {
/*  296: 317 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  297: 318 */         LOG.error("ERROR AL EDITAR PEDIDO DE CLIENTE:", e);
/*  298:     */       }
/*  299:     */     } else {
/*  300: 321 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  301:     */     }
/*  302: 323 */     return "";
/*  303:     */   }
/*  304:     */   
/*  305:     */   public String guardar()
/*  306:     */   {
/*  307:     */     try
/*  308:     */     {
/*  309: 334 */       if (this.pedidoCliente.getListaDetallePedidoCliente().size() > 0)
/*  310:     */       {
/*  311: 335 */         this.pedidoCliente.setIndicadorAutorizacionPedidoPorCriterio(getAutorizacionPedidoPorCriterio());
/*  312: 336 */         this.servicioPedidoCliente.guardar(this.pedidoCliente);
/*  313: 337 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  314: 338 */         cargarDatos();
/*  315:     */       }
/*  316:     */       else
/*  317:     */       {
/*  318: 340 */         addErrorMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/*  319:     */       }
/*  320:     */     }
/*  321:     */     catch (ExcepcionAS2Ventas e)
/*  322:     */     {
/*  323: 343 */       e.printStackTrace();
/*  324: 344 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  325: 345 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  326:     */     }
/*  327:     */     catch (ExcepcionAS2Financiero e)
/*  328:     */     {
/*  329: 347 */       e.printStackTrace();
/*  330: 348 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  331: 349 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  332:     */     }
/*  333:     */     catch (ExcepcionAS2 e)
/*  334:     */     {
/*  335: 351 */       e.printStackTrace();
/*  336: 352 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  337: 353 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  338:     */     }
/*  339:     */     catch (Exception e)
/*  340:     */     {
/*  341: 355 */       e.printStackTrace();
/*  342: 356 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  343: 357 */       LOG.error("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  344:     */     }
/*  345: 360 */     return "";
/*  346:     */   }
/*  347:     */   
/*  348:     */   public String eliminar()
/*  349:     */   {
/*  350:     */     try
/*  351:     */     {
/*  352: 371 */       this.servicioPedidoCliente.anular(this.pedidoCliente);
/*  353: 372 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  354: 373 */       cargarDatos();
/*  355:     */     }
/*  356:     */     catch (ExcepcionAS2 e)
/*  357:     */     {
/*  358: 375 */       e.printStackTrace();
/*  359: 376 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  360: 377 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  361:     */     }
/*  362:     */     catch (Exception e)
/*  363:     */     {
/*  364: 379 */       e.printStackTrace();
/*  365: 380 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  366: 381 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  367:     */     }
/*  368: 383 */     return null;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public String limpiar()
/*  372:     */   {
/*  373: 393 */     setEditado(false);
/*  374: 394 */     setIndicadorRender(false);
/*  375: 395 */     crearPedidoCliente();
/*  376: 396 */     setMostrarAutorizarVenta(false);
/*  377: 397 */     return "";
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void actualizarPorcentajeDescuentoGeneral()
/*  381:     */   {
/*  382: 404 */     for (DetallePedidoCliente dpc : getDetallePedidoCliente()) {
/*  383: 405 */       validarDescuento(dpc, getDescuentoGeneral());
/*  384:     */     }
/*  385: 407 */     totalizar();
/*  386:     */   }
/*  387:     */   
/*  388:     */   private void crearPedidoCliente()
/*  389:     */   {
/*  390: 414 */     this.pedidoCliente = new PedidoCliente();
/*  391: 415 */     this.pedidoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  392: 416 */     this.pedidoCliente.setSucursal(AppUtil.getSucursal());
/*  393: 417 */     this.pedidoCliente.setBodega(AppUtil.getBodega());
/*  394: 418 */     this.pedidoCliente.setNumero("");
/*  395: 419 */     this.pedidoCliente.setFecha(new Date());
/*  396: 420 */     this.pedidoCliente.setEstado(Estado.ELABORADO);
/*  397:     */     
/*  398: 422 */     Documento documento = null;
/*  399: 423 */     if ((getListaDocumentoCliente() != null) && (!getListaDocumentoCliente().isEmpty()))
/*  400:     */     {
/*  401: 424 */       documento = (Documento)getListaDocumentoCliente().get(0);
/*  402: 425 */       this.pedidoCliente.setDocumento(documento);
/*  403: 426 */       actualizarDocumento();
/*  404:     */     }
/*  405:     */     else
/*  406:     */     {
/*  407: 428 */       documento = new Documento();
/*  408: 429 */       documento.setSecuencia(new Secuencia());
/*  409: 430 */       this.pedidoCliente.setDocumento(documento);
/*  410:     */     }
/*  411: 433 */     this.listaDireccionEmpresa = new ArrayList();
/*  412:     */   }
/*  413:     */   
/*  414:     */   public String cargarDatos()
/*  415:     */   {
/*  416: 443 */     setEditado(false);
/*  417:     */     try
/*  418:     */     {
/*  419: 446 */       limpiar();
/*  420:     */     }
/*  421:     */     catch (Exception e)
/*  422:     */     {
/*  423: 448 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  424: 449 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  425:     */     }
/*  426: 451 */     return "";
/*  427:     */   }
/*  428:     */   
/*  429:     */   public String agregarDetalle()
/*  430:     */   {
/*  431: 461 */     DetallePedidoCliente d = new DetallePedidoCliente();
/*  432: 462 */     d.setPedidoCliente(getPedidoCliente());
/*  433: 463 */     d.setCantidad(BigDecimal.ZERO);
/*  434: 464 */     d.setPrecio(BigDecimal.ZERO);
/*  435: 465 */     d.setProducto(new Producto());
/*  436: 466 */     getPedidoCliente().getListaDetallePedidoCliente().add(d);
/*  437: 467 */     return "";
/*  438:     */   }
/*  439:     */   
/*  440:     */   public void actualizarProductoListener(SelectEvent event)
/*  441:     */   {
/*  442: 472 */     DetallePedidoCliente dpc = (DetallePedidoCliente)this.dtDetallePedidoCliente.getRowData();
/*  443: 473 */     Producto producto = (Producto)event.getObject();
/*  444: 475 */     if (producto != null) {
/*  445:     */       try
/*  446:     */       {
/*  447: 477 */         producto = this.servicioProducto.buscarProductoPorCodigoProductoLote(producto.getCodigo(), AppUtil.getOrganizacion().getIdOrganizacion(), DocumentoBase.PEDIDO_CLIENTE);
/*  448: 478 */         actualizarProducto(dpc, producto);
/*  449:     */       }
/*  450:     */       catch (ExcepcionAS2 e)
/*  451:     */       {
/*  452: 481 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  453: 482 */         dpc.getProducto().setCodigo("");
/*  454: 483 */         dpc.getProducto().setNombre("");
/*  455:     */       }
/*  456:     */     }
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void actualizarProducto(DetallePedidoCliente dpc, Producto producto)
/*  460:     */   {
/*  461: 496 */     if (this.pedidoCliente.getEmpresa() != null)
/*  462:     */     {
/*  463: 498 */       dpc.setPedidoCliente(this.pedidoCliente);
/*  464: 500 */       for (ImpuestoProductoPedidoCliente ippc : dpc.getListaImpuestoProductoPedidoCliente()) {
/*  465: 501 */         ippc.setEliminado(true);
/*  466:     */       }
/*  467: 504 */       dpc.setProducto(producto);
/*  468: 505 */       dpc.setUnidadVenta(producto.getUnidadVenta());
/*  469: 506 */       dpc.setIndicadorPorcentajeIce(producto.isIndicadorPorcentajeIce());
/*  470: 507 */       dpc.setCodigoIce(producto.getCodigoIce());
/*  471: 508 */       dpc.setIce(producto.getIce());
/*  472: 509 */       dpc.setPedidoCliente(this.pedidoCliente);
/*  473: 511 */       if (this.pedidoCliente.getEmpresa().getCliente().isExcentoImpuestos()) {
/*  474: 512 */         dpc.setIndicadorImpuesto(false);
/*  475:     */       } else {
/*  476: 514 */         dpc.setIndicadorImpuesto(producto.isIndicadorImpuestos());
/*  477:     */       }
/*  478:     */       try
/*  479:     */       {
/*  480: 518 */         if (dpc.isIndicadorImpuesto()) {
/*  481: 519 */           this.servicioPedidoCliente.obtenerImpuestosProductos(dpc.getProducto(), dpc);
/*  482:     */         }
/*  483: 522 */         Integer idZona = null;
/*  484: 523 */         if ((isIndicadorListaPrecioPorZona()) && 
/*  485: 524 */           (getPedidoCliente().getZona() != null)) {
/*  486: 525 */           idZona = Integer.valueOf(getPedidoCliente().getZona().getId());
/*  487:     */         }
/*  488: 529 */         if (this.pedidoCliente.getEmpresa().getCliente().getListaPrecios() != null)
/*  489:     */         {
/*  490: 530 */           DetalleVersionListaPrecios dvlp = this.servicioListaPrecios.getDatosVersionListaPrecios(this.pedidoCliente.getEmpresa().getCliente()
/*  491: 531 */             .getListaPrecios().getIdListaPrecios(), producto.getIdProducto(), this.pedidoCliente.getFecha(), idZona, "");
/*  492: 532 */           dpc.setPrecio(dvlp.getPrecioUnitario());
/*  493:     */         }
/*  494:     */         else
/*  495:     */         {
/*  496: 535 */           if (ParametrosSistema.isBloqueoProductoListaPrecios(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue()) {
/*  497: 536 */             dpc.setEliminado(true);
/*  498:     */           }
/*  499: 539 */           addInfoMessage(getLanguageController().getMensaje("msg_error_empresa_lista_precios"));
/*  500:     */         }
/*  501:     */       }
/*  502:     */       catch (ExcepcionAS2 e)
/*  503:     */       {
/*  504: 546 */         if (ParametrosSistema.isBloqueoProductoListaPrecios(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue()) {
/*  505: 547 */           dpc.setEliminado(true);
/*  506:     */         }
/*  507: 550 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  508: 551 */         LOG.error("ERROR RECUPERAR PRODUCTO", e);
/*  509:     */       }
/*  510:     */       catch (Exception e)
/*  511:     */       {
/*  512: 553 */         e.printStackTrace();
/*  513: 554 */         addInfoMessage("msg_error_cargar_datos");
/*  514: 555 */         LOG.error("ERROR RECUPERAR PRODUCTO", e);
/*  515:     */       }
/*  516: 558 */       totalizar();
/*  517:     */     }
/*  518:     */     else
/*  519:     */     {
/*  520: 561 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  521: 562 */       dpc.getProducto().setCodigo("");
/*  522:     */     }
/*  523:     */   }
/*  524:     */   
/*  525:     */   public String eliminarDetalle()
/*  526:     */   {
/*  527: 572 */     DetallePedidoCliente detallePedidoCliente = (DetallePedidoCliente)this.dtDetallePedidoCliente.getRowData();
/*  528: 574 */     for (ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente : detallePedidoCliente.getListaImpuestoProductoPedidoCliente()) {
/*  529: 575 */       impuestoProductoPedidoCliente.setEliminado(true);
/*  530:     */     }
/*  531: 578 */     detallePedidoCliente.setEliminado(true);
/*  532: 579 */     detallePedidoCliente.setCantidad(BigDecimal.ZERO);
/*  533: 580 */     totalizar();
/*  534:     */     
/*  535: 582 */     return "";
/*  536:     */   }
/*  537:     */   
/*  538:     */   public void totalizar()
/*  539:     */   {
/*  540:     */     try
/*  541:     */     {
/*  542: 592 */       this.servicioPedidoCliente.totalizar(this.pedidoCliente);
/*  543: 594 */       for (DetallePedidoCliente detallePedidoCliente : this.pedidoCliente.getListaDetallePedidoCliente()) {
/*  544: 596 */         if ((!detallePedidoCliente.isEliminado()) && (detallePedidoCliente.getProducto() != null))
/*  545:     */         {
/*  546: 597 */           BigDecimal saldo = this.servicioProducto.getSaldo(detallePedidoCliente.getProducto().getId(), this.pedidoCliente.getBodega() == null ? 0 : this.pedidoCliente
/*  547: 598 */             .getBodega().getId(), this.pedidoCliente.getFecha());
/*  548:     */           
/*  549: 600 */           saldo = this.servicioProducto.convierteUnidad(detallePedidoCliente.getProducto().getUnidad(), detallePedidoCliente.getUnidadVenta(), detallePedidoCliente
/*  550: 601 */             .getProducto().getIdProducto(), saldo);
/*  551:     */           
/*  552: 603 */           BigDecimal saldoComprometido = this.servicioProducto.getInventarioComprometido(detallePedidoCliente.getProducto(), null, this.pedidoCliente
/*  553: 604 */             .getBodega(), this.pedidoCliente.getFecha(), false);
/*  554: 605 */           detallePedidoCliente.setSaldoVenta(saldo.subtract(saldoComprometido));
/*  555:     */         }
/*  556:     */       }
/*  557: 610 */       if (this.pedidoCliente.getEmpresa().getCliente().getCreditoMaximo().subtract(this.pedidoCliente.getEmpresa().getCliente().getCreditoUtilizado()).compareTo(this.pedidoCliente.getTotalPedido()) < 0) {
/*  558: 611 */         setMostrarAutorizarVenta(true);
/*  559:     */       } else {
/*  560: 613 */         setMostrarAutorizarVenta(false);
/*  561:     */       }
/*  562:     */     }
/*  563:     */     catch (ExcepcionAS2Inventario e)
/*  564:     */     {
/*  565: 616 */       e.printStackTrace();
/*  566: 617 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  567:     */     }
/*  568:     */     catch (Exception e)
/*  569:     */     {
/*  570: 619 */       LOG.error(e);
/*  571: 620 */       e.printStackTrace();
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public void actualizarPorcentajeDescuento()
/*  576:     */   {
/*  577: 626 */     DetallePedidoCliente dfc = (DetallePedidoCliente)this.dtDetallePedidoCliente.getRowData();
/*  578: 627 */     validarDescuento(dfc, null);
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void actuailzarPorcentajeDescuentoDesdePrecioUnitario()
/*  582:     */   {
/*  583: 632 */     DetallePedidoCliente dfc = (DetallePedidoCliente)this.dtDetallePedidoCliente.getRowData();
/*  584:     */     
/*  585: 634 */     BigDecimal c = dfc.getCantidad().multiply(dfc.getDescuento()).multiply(new BigDecimal(100)).divide(dfc.getPrecioLinea(), 4, RoundingMode.HALF_UP);
/*  586: 635 */     dfc.setPorcentajeDescuento(c);
/*  587: 636 */     System.out.println("" + c);
/*  588: 637 */     validarDescuento(dfc, null);
/*  589: 638 */     totalizar();
/*  590:     */   }
/*  591:     */   
/*  592:     */   private void validarDescuento(DetallePedidoCliente dfc, BigDecimal porcentajeDescuentoGeneral)
/*  593:     */   {
/*  594: 643 */     if (this.pedidoCliente.getEmpresa().getCliente().getListaDescuentos() != null)
/*  595:     */     {
/*  596: 644 */       if (!this.pedidoCliente.getEmpresa().getCliente().getListaDescuentos().isIndicadorDescuentoPorProducto())
/*  597:     */       {
/*  598: 645 */         dfc.getProducto().setTraDescuentoPorcentajeMaximo(this.servicioListaDescuentos
/*  599: 646 */           .getPorcentajeDescuentoMaximoVigente(this.pedidoCliente.getEmpresa().getCliente().getListaDescuentos(), this.pedidoCliente.getFecha()));
/*  600:     */       }
/*  601:     */       else
/*  602:     */       {
/*  603: 649 */         DetalleListaDescuentos detalleListaDescuentos = this.servicioListaDescuentos.getDatosListaDescuentosPorProducto(this.pedidoCliente.getEmpresa()
/*  604: 650 */           .getCliente().getListaDescuentos(), dfc.getProducto());
/*  605: 651 */         if (dfc.getProducto() != null) {
/*  606: 652 */           if (detalleListaDescuentos != null) {
/*  607: 653 */             dfc.getProducto().setTraDescuentoPorcentajeMaximo(detalleListaDescuentos.getPorcentajeDescuentoMaximo());
/*  608:     */           } else {
/*  609: 655 */             dfc.getProducto().setTraDescuentoPorcentajeMaximo(BigDecimal.ZERO);
/*  610:     */           }
/*  611:     */         }
/*  612:     */       }
/*  613:     */     }
/*  614:     */     else {
/*  615: 660 */       dfc.getProducto().setTraDescuentoPorcentajeMaximo(BigDecimal.ZERO);
/*  616:     */     }
/*  617: 662 */     if (porcentajeDescuentoGeneral != null) {
/*  618: 663 */       dfc.setPorcentajeDescuento(porcentajeDescuentoGeneral);
/*  619:     */     }
/*  620: 665 */     if (dfc.getPorcentajeDescuento().compareTo(dfc.getProducto().getTraDescuentoPorcentajeMaximo()) > 0)
/*  621:     */     {
/*  622: 667 */       dfc.setPorcentajeDescuento(dfc.getProducto().getTraDescuentoPorcentajeMaximo());
/*  623: 668 */       addErrorMessage(getLanguageController().getMensaje("msg_error_porcentaje_descuento_maximo"));
/*  624:     */     }
/*  625: 671 */     totalizar();
/*  626:     */   }
/*  627:     */   
/*  628:     */   public String actualizarDocumento()
/*  629:     */   {
/*  630: 676 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(this.pedidoCliente.getDocumento().getId()));
/*  631: 677 */     this.pedidoCliente.setDocumento(documento);
/*  632:     */     
/*  633: 679 */     setSecuenciaEditable(!this.pedidoCliente.getDocumento().isIndicadorBloqueoSecuencia());
/*  634:     */     
/*  635: 681 */     return "";
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void actualizarCliente(SelectEvent event)
/*  639:     */   {
/*  640: 685 */     Empresa empresa = (Empresa)event.getObject();
/*  641: 686 */     actualizarCliente(empresa);
/*  642: 687 */     cargarPedidoSugerido();
/*  643:     */   }
/*  644:     */   
/*  645:     */   public void actualizarCliente(Empresa empresa)
/*  646:     */   {
/*  647: 692 */     getPedidoCliente().setEmpresa(empresa);
/*  648:     */     
/*  649: 694 */     getPedidoCliente().setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  650: 697 */     if (getPedidoCliente().getZona() == null) {
/*  651: 698 */       getPedidoCliente().setZona(empresa.getCliente().getZona());
/*  652:     */     }
/*  653: 702 */     getPedidoCliente().setCondicionPago(empresa.getCliente().getCondicionPago());
/*  654:     */     
/*  655:     */ 
/*  656:     */ 
/*  657: 706 */     getPedidoCliente().setTransportista(empresa.getCliente().getTransportista());
/*  658:     */     
/*  659:     */ 
/*  660:     */ 
/*  661: 710 */     getPedidoCliente().setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/*  662:     */     
/*  663:     */ 
/*  664:     */ 
/*  665: 714 */     getPedidoCliente().setDescripcion(empresa.getEmail1());
/*  666:     */     
/*  667: 716 */     getPedidoCliente().setDireccionEmpresa(null);
/*  668: 717 */     this.listaDireccionEmpresa = null;
/*  669: 718 */     cargarDirecciones();
/*  670: 719 */     cargarSubempresas();
/*  671: 720 */     cargarContactos(empresa);
/*  672:     */     
/*  673: 722 */     actualizarFecha();
/*  674: 723 */     if (this.pedidoCliente.getEmpresa() != null) {
/*  675: 725 */       if (!this.pedidoCliente.getEmpresa().getCliente().getIndicadorPedidoPreautorizado().booleanValue()) {
/*  676:     */         try
/*  677:     */         {
/*  678: 727 */           this.servicioVerificadorVentas.verificarBloqueoCliente(this.pedidoCliente.getEmpresa().getCliente(), this.pedidoCliente.getFecha());
/*  679:     */         }
/*  680:     */         catch (ExcepcionAS2Ventas e)
/*  681:     */         {
/*  682: 729 */           addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  683:     */         }
/*  684:     */       }
/*  685:     */     }
/*  686:     */   }
/*  687:     */   
/*  688:     */   public void cargarSubempresas()
/*  689:     */   {
/*  690: 736 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(this.pedidoCliente.getEmpresa().getId());
/*  691:     */   }
/*  692:     */   
/*  693:     */   public void cargarDirecciones()
/*  694:     */   {
/*  695: 745 */     if (getPedidoCliente().getSubempresa() != null) {
/*  696: 746 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getPedidoCliente().getSubempresa().getEmpresa().getId());
/*  697:     */     }
/*  698: 749 */     if ((this.listaDireccionEmpresa == null) || (this.listaDireccionEmpresa.isEmpty())) {
/*  699: 750 */       this.listaDireccionEmpresa = this.servicioEmpresa.obtenerListaComboDirecciones(getPedidoCliente().getEmpresa().getId());
/*  700:     */     }
/*  701: 753 */     if (getPedidoCliente().getDireccionEmpresa() == null) {
/*  702: 756 */       for (DireccionEmpresa de : this.listaDireccionEmpresa) {
/*  703: 757 */         if (de.isIndicadorDireccionPrincipal())
/*  704:     */         {
/*  705: 758 */           getPedidoCliente().setDireccionEmpresa(de);
/*  706: 759 */           break;
/*  707:     */         }
/*  708:     */       }
/*  709:     */     }
/*  710:     */   }
/*  711:     */   
/*  712:     */   public void cargarContactos(Empresa empresa)
/*  713:     */   {
/*  714: 766 */     this.listaContacto = this.servicioEmpresa.obtenerListaComboContactos(empresa);
/*  715:     */   }
/*  716:     */   
/*  717:     */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/*  718:     */   {
/*  719: 770 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/*  720:     */     {
/*  721: 771 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/*  722: 772 */       cargarProducto();
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   public void cargarProducto(Producto producto)
/*  727:     */   {
/*  728: 777 */     getListaProductoBean().setProducto(producto);
/*  729: 778 */     getListaProductoBean().setSaldoProductoLote(null);
/*  730: 779 */     cargarProducto();
/*  731:     */   }
/*  732:     */   
/*  733:     */   public void cargarProducto()
/*  734:     */   {
/*  735: 787 */     Producto producto = getListaProductoBean().getProducto();
/*  736: 789 */     if (producto != null)
/*  737:     */     {
/*  738: 790 */       DetallePedidoCliente detallePedidoCliente = new DetallePedidoCliente();
/*  739: 791 */       detallePedidoCliente.setProducto(producto);
/*  740: 792 */       this.pedidoCliente.getListaDetallePedidoCliente().add(detallePedidoCliente);
/*  741: 793 */       detallePedidoCliente.setCantidad(producto.getTraCantidad());
/*  742: 794 */       actualizarProducto(detallePedidoCliente, producto);
/*  743:     */     }
/*  744: 796 */     getListaProductoBean().setProducto(null);
/*  745:     */   }
/*  746:     */   
/*  747:     */   public void procesarPedidoCliente()
/*  748:     */   {
/*  749:     */     try
/*  750:     */     {
/*  751: 805 */       this.servicioPedidoCliente.procesarPedidoCliente(this.pedidoCliente, Boolean.valueOf(true), this.indicadorAprobarCompleto, AppUtil.getUsuarioEnSesion()
/*  752: 806 */         .getNombreUsuario(), new Date());
/*  753: 807 */       setIndicadorRender(false);
/*  754:     */     }
/*  755:     */     catch (ExcepcionAS2Inventario e)
/*  756:     */     {
/*  757: 810 */       e.printStackTrace();
/*  758: 811 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  759: 812 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  760:     */     }
/*  761:     */     catch (Exception e)
/*  762:     */     {
/*  763: 814 */       e.printStackTrace();
/*  764: 815 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  765: 816 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  766:     */     }
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void cerrarPedidoCliente()
/*  770:     */   {
/*  771:     */     try
/*  772:     */     {
/*  773: 826 */       this.servicioPedidoCliente.cerrarPedidoCliente(this.pedidoCliente);
/*  774: 827 */       this.indicadorRenderCerrar = false;
/*  775:     */     }
/*  776:     */     catch (ExcepcionAS2 e)
/*  777:     */     {
/*  778: 829 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  779: 830 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  780:     */     }
/*  781:     */   }
/*  782:     */   
/*  783:     */   public String cargarDetallePedidoClienteExcel(FileUploadEvent event)
/*  784:     */   {
/*  785:     */     try
/*  786:     */     {
/*  787: 837 */       String fileName = "pedido_cliente" + event.getFile().getFileName();
/*  788: 838 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  789: 839 */       this.servicioPedidoCliente.cargarDetallePedidoClienteExcel(this.pedidoCliente, fileName, input, 5);
/*  790: 840 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  791:     */     }
/*  792:     */     catch (ExcepcionAS2 e)
/*  793:     */     {
/*  794: 843 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  795: 844 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  796:     */     }
/*  797:     */     catch (Exception e)
/*  798:     */     {
/*  799: 847 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  800: 848 */       e.printStackTrace();
/*  801:     */     }
/*  802: 851 */     return null;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public void actualizarSubclienteListener(AjaxBehaviorEvent event)
/*  806:     */   {
/*  807: 856 */     Subempresa subempresa = (Subempresa)((HtmlSelectOneMenu)event.getSource()).getValue();
/*  808: 858 */     if (subempresa != null)
/*  809:     */     {
/*  810: 860 */       Empresa empresa = subempresa.getEmpresa();
/*  811:     */       
/*  812: 862 */       this.pedidoCliente.setSubempresa(subempresa);
/*  813: 863 */       this.pedidoCliente.setDireccionEmpresa(null);
/*  814:     */       
/*  815:     */ 
/*  816: 866 */       this.pedidoCliente.setAgenteComercial(this.servicioUsuario.buscaAgenteComercialPorIdEmpresa(empresa.getId()));
/*  817: 867 */       this.pedidoCliente.setZona(empresa.getCliente().getZona());
/*  818: 868 */       this.pedidoCliente.setCondicionPago(empresa.getCliente().getCondicionPago());
/*  819: 869 */       this.pedidoCliente.setNumeroCuotas(empresa.getCliente().getNumeroCuotas());
/*  820: 870 */       if (subempresa.getEmpresa().getCliente().getTransportista() != null) {
/*  821: 871 */         this.pedidoCliente.setTransportista(empresa.getCliente().getTransportista());
/*  822:     */       }
/*  823: 873 */       cargarDirecciones();
/*  824:     */     }
/*  825: 875 */     actualizarFecha();
/*  826: 876 */     cargarPedidoSugerido();
/*  827:     */   }
/*  828:     */   
/*  829:     */   public String crearCliente()
/*  830:     */   {
/*  831:     */     try
/*  832:     */     {
/*  833: 887 */       Empresa empresa = this.servicioEmpresa.crearClienteConDatosBasicos(this.identificacionCliente, this.tipoIdentificacionCliente, this.nombreCliente, this.direccionCliente, this.telefonoCliente, 
/*  834: 888 */         AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), this.ciudad, this.agenteComercial, this.email);
/*  835:     */       
/*  836:     */ 
/*  837: 891 */       this.identificacionCliente = null;
/*  838:     */       
/*  839: 893 */       this.nombreCliente = null;
/*  840: 894 */       this.direccionCliente = null;
/*  841: 895 */       this.telefonoCliente = null;
/*  842: 896 */       this.agenteComercial = null;
/*  843: 897 */       this.email = null;
/*  844:     */       
/*  845: 899 */       actualizarCliente(empresa);
/*  846: 900 */       setRenderClienteNuevo(false);
/*  847: 901 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  848:     */     }
/*  849:     */     catch (ExcepcionAS2 e)
/*  850:     */     {
/*  851: 903 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  852: 904 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  853:     */     }
/*  854:     */     catch (Exception ex)
/*  855:     */     {
/*  856: 906 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  857: 907 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  858:     */     }
/*  859: 910 */     return "";
/*  860:     */   }
/*  861:     */   
/*  862:     */   public String copiar()
/*  863:     */     throws ExcepcionAS2Financiero
/*  864:     */   {
/*  865: 915 */     if ((getPedidoCliente() != null) && (getPedidoCliente().getId() != 0))
/*  866:     */     {
/*  867: 917 */       PedidoCliente auxPedidoCliente = this.servicioPedidoCliente.cargarDetalle(this.pedidoCliente.getId());
/*  868: 918 */       this.pedidoCliente = this.servicioPedidoCliente.copiarPedidoCliente(auxPedidoCliente);
/*  869: 919 */       this.pedidoCliente.setSucursal(AppUtil.getSucursal());
/*  870: 920 */       this.pedidoCliente.setBodega(AppUtil.getBodega());
/*  871: 921 */       this.pedidoCliente.setUsuarioAutoriza("");
/*  872: 922 */       cargarSubempresas();
/*  873: 923 */       cargarDirecciones();
/*  874: 924 */       cargarContactos(this.pedidoCliente.getEmpresa());
/*  875: 925 */       totalizar();
/*  876: 926 */       setEditado(true);
/*  877:     */     }
/*  878:     */     else
/*  879:     */     {
/*  880: 929 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  881:     */     }
/*  882: 933 */     return "";
/*  883:     */   }
/*  884:     */   
/*  885:     */   public void actualizarDatosPedido(RowEditEvent event)
/*  886:     */   {
/*  887:     */     try
/*  888:     */     {
/*  889: 939 */       this.servicioGenericoPedido.guardar(getPedidoCliente());
/*  890: 940 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  891:     */     }
/*  892:     */     catch (AS2Exception e)
/*  893:     */     {
/*  894: 942 */       JsfUtil.addErrorMessage(e, "");
/*  895: 943 */       LOG.info("ERROR AL GUARDAR DATOS PEDIDO CLIENTE", e);
/*  896:     */     }
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void actualizarImpuestosListener()
/*  900:     */   {
/*  901: 948 */     for (DetallePedidoCliente dpp : this.pedidoCliente.getListaDetallePedidoCliente()) {
/*  902: 949 */       actualizarProducto(dpp, dpp.getProducto());
/*  903:     */     }
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void processDownload()
/*  907:     */   {
/*  908:     */     try
/*  909:     */     {
/*  910: 960 */       PedidoCliente fp = (PedidoCliente)this.dtPedidoCliente.getRowData();
/*  911: 961 */       String fileName = fp.getPdf();
/*  912: 962 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pedido_cliente");
/*  913: 964 */       if (fileName == null) {
/*  914: 965 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  915:     */       } else {
/*  916: 967 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  917:     */       }
/*  918:     */     }
/*  919:     */     catch (Exception e)
/*  920:     */     {
/*  921: 971 */       e.printStackTrace();
/*  922: 972 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public String eliminarArchivo()
/*  927:     */   {
/*  928: 977 */     FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), getPedidoCliente().getPdf());
/*  929: 978 */     getPedidoCliente().setPdf(null);
/*  930: 979 */     HashMap<String, Object> campos = new HashMap();
/*  931: 980 */     campos.put("pdf", null);
/*  932: 981 */     this.servicioPedidoCliente.actualizarAtributoEntidad(getPedidoCliente(), campos);
/*  933: 982 */     return null;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void processUpload(FileUploadEvent event)
/*  937:     */   {
/*  938:     */     try
/*  939:     */     {
/*  940: 995 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pedido_cliente");
/*  941:     */       
/*  942: 997 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getPedidoCliente().getNumero(), uploadDir);
/*  943:     */       
/*  944:     */ 
/*  945:1000 */       HashMap<String, Object> campos = new HashMap();
/*  946:1001 */       campos.put("pdf", fileName);
/*  947:1002 */       this.servicioPedidoCliente.actualizarAtributoEntidad(getPedidoCliente(), campos);
/*  948:     */       
/*  949:1004 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  950:     */     }
/*  951:     */     catch (Exception e)
/*  952:     */     {
/*  953:1007 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  954:1008 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  955:     */     }
/*  956:     */   }
/*  957:     */   
/*  958:     */   public String getDirectorioDescarga()
/*  959:     */   {
/*  960:1015 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "pedido_cliente");
/*  961:     */   }
/*  962:     */   
/*  963:     */   public PedidoCliente getPedidoCliente()
/*  964:     */   {
/*  965:1021 */     return this.pedidoCliente;
/*  966:     */   }
/*  967:     */   
/*  968:     */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/*  969:     */   {
/*  970:1025 */     this.pedidoCliente = pedidoCliente;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public LazyDataModel<PedidoCliente> getListaPedidoCliente()
/*  974:     */   {
/*  975:1029 */     if (this.listaPedidoCliente == null) {
/*  976:1030 */       cargarDatos();
/*  977:     */     }
/*  978:1032 */     return this.listaPedidoCliente;
/*  979:     */   }
/*  980:     */   
/*  981:     */   public void setListaPedidoCliente(LazyDataModel<PedidoCliente> listaPedidoCliente)
/*  982:     */   {
/*  983:1036 */     this.listaPedidoCliente = listaPedidoCliente;
/*  984:     */   }
/*  985:     */   
/*  986:     */   public List<DetallePedidoCliente> getDetallePedidoCliente()
/*  987:     */   {
/*  988:1040 */     List<DetallePedidoCliente> detalle = new ArrayList();
/*  989:1041 */     for (DetallePedidoCliente dmc : getPedidoCliente().getListaDetallePedidoCliente()) {
/*  990:1042 */       if (!dmc.isEliminado()) {
/*  991:1043 */         detalle.add(dmc);
/*  992:     */       }
/*  993:     */     }
/*  994:1046 */     return detalle;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public void setDetallePedidoCliente(List<DetallePedidoCliente> detallePedidoCliente)
/*  998:     */   {
/*  999:1050 */     getPedidoCliente().setListaDetallePedidoCliente(detallePedidoCliente);
/* 1000:     */   }
/* 1001:     */   
/* 1002:     */   public List<Sucursal> getListaSucursal()
/* 1003:     */   {
/* 1004:1059 */     if (this.listaSucursal == null) {
/* 1005:1060 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 1006:     */     }
/* 1007:1062 */     return this.listaSucursal;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 1011:     */   {
/* 1012:1072 */     this.listaSucursal = listaSucursal;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public DataTable getDtPedidoCliente()
/* 1016:     */   {
/* 1017:1076 */     return this.dtPedidoCliente;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void setDtPedidoCliente(DataTable dtPedidoCliente)
/* 1021:     */   {
/* 1022:1080 */     this.dtPedidoCliente = dtPedidoCliente;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public DataTable getDtDetallePedidoCliente()
/* 1026:     */   {
/* 1027:1084 */     return this.dtDetallePedidoCliente;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public void setDtDetallePedidoCliente(DataTable dtDetallePedidoCliente)
/* 1031:     */   {
/* 1032:1088 */     this.dtDetallePedidoCliente = dtDetallePedidoCliente;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public List<Documento> getListaDocumentoCliente()
/* 1036:     */   {
/* 1037:     */     try
/* 1038:     */     {
/* 1039:1099 */       if (this.listaDocumentoCliente == null) {
/* 1040:1100 */         this.listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.PEDIDO_CLIENTE, AppUtil.getOrganizacion()
/* 1041:1101 */           .getIdOrganizacion());
/* 1042:     */       }
/* 1043:     */     }
/* 1044:     */     catch (ExcepcionAS2 e)
/* 1045:     */     {
/* 1046:1104 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 1047:     */     }
/* 1048:1106 */     return this.listaDocumentoCliente;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public void setListaDocumentoCliente(List<Documento> listaDocumentoCliente)
/* 1052:     */   {
/* 1053:1116 */     this.listaDocumentoCliente = listaDocumentoCliente;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public List<MotivoPedidoCliente> getListaMotivoPedidoCliente()
/* 1057:     */   {
/* 1058:1125 */     if (this.listaMotivoPedidoCliente == null) {
/* 1059:1126 */       this.listaMotivoPedidoCliente = this.servicioMotivoPedidoCliente.obtenerListaCombo("nombre", true, null);
/* 1060:     */     }
/* 1061:1128 */     return this.listaMotivoPedidoCliente;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void setListaMotivoPedidoCliente(List<MotivoPedidoCliente> listaMotivoPedidoCliente)
/* 1065:     */   {
/* 1066:1138 */     this.listaMotivoPedidoCliente = listaMotivoPedidoCliente;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public List<DireccionEmpresa> getListaDireccionEmpresa()
/* 1070:     */   {
/* 1071:1147 */     if (this.listaDireccionEmpresa == null) {
/* 1072:1148 */       this.listaDireccionEmpresa = new ArrayList();
/* 1073:     */     }
/* 1074:1150 */     return this.listaDireccionEmpresa;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   public void setListaDireccionEmpresa(List<DireccionEmpresa> listaDireccionEmpresa)
/* 1078:     */   {
/* 1079:1160 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   public DataTable getDtImpuestoDetallePedidoCliente()
/* 1083:     */   {
/* 1084:1164 */     return this.dtImpuestoDetallePedidoCliente;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public void setDtImpuestoDetallePedidoCliente(DataTable dtImpuestoDetallePedidoCliente)
/* 1088:     */   {
/* 1089:1168 */     this.dtImpuestoDetallePedidoCliente = dtImpuestoDetallePedidoCliente;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public List<ImpuestoProductoPedidoCliente> getListaImpuestoProductoPedidoCliente()
/* 1093:     */   {
/* 1094:1173 */     List<ImpuestoProductoPedidoCliente> listaImpuestoProductoPedidoCliente = new ArrayList();
/* 1095:1175 */     for (DetallePedidoCliente detallePedidoCliente : getPedidoCliente().getListaDetallePedidoCliente()) {
/* 1096:1176 */       if (!detallePedidoCliente.isEliminado()) {
/* 1097:1177 */         for (ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente : detallePedidoCliente.getListaImpuestoProductoPedidoCliente()) {
/* 1098:1178 */           if (!impuestoProductoPedidoCliente.isEliminado()) {
/* 1099:1179 */             listaImpuestoProductoPedidoCliente.add(impuestoProductoPedidoCliente);
/* 1100:     */           }
/* 1101:     */         }
/* 1102:     */       }
/* 1103:     */     }
/* 1104:1184 */     return listaImpuestoProductoPedidoCliente;
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   public DataTable getDtImpuestoResumen()
/* 1108:     */   {
/* 1109:1188 */     return this.dtImpuestoResumen;
/* 1110:     */   }
/* 1111:     */   
/* 1112:     */   public void setDtImpuestoResumen(DataTable dtImpuestoResumen)
/* 1113:     */   {
/* 1114:1193 */     this.dtImpuestoResumen = dtImpuestoResumen;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public List<Empresa> autocompletarClientes(String consulta)
/* 1118:     */   {
/* 1119:1197 */     return this.servicioEmpresa.autocompletarClientes(consulta, true, null, true);
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public Integer getIdPedidoCliente()
/* 1123:     */   {
/* 1124:1204 */     return this.idPedidoCliente;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public void setIdPedidoCliente(Integer idPedidoCliente)
/* 1128:     */   {
/* 1129:1212 */     this.idPedidoCliente = idPedidoCliente;
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public List<Bodega> getListaBodega()
/* 1133:     */   {
/* 1134:1219 */     if (this.listaBodega == null) {
/* 1135:1220 */       this.listaBodega = AppUtil.getUsuarioEnSesion().getListaBodega();
/* 1136:     */     }
/* 1137:1222 */     return this.listaBodega;
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public List<Subempresa> getListaSubempresa()
/* 1141:     */   {
/* 1142:1231 */     if (this.listaSubempresa == null) {
/* 1143:1232 */       this.listaSubempresa = new ArrayList();
/* 1144:     */     }
/* 1145:1234 */     return this.listaSubempresa;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 1149:     */   {
/* 1150:1244 */     this.listaSubempresa = listaSubempresa;
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   public boolean isIndicadorRender()
/* 1154:     */   {
/* 1155:1248 */     return this.indicadorRender;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public void setIndicadorRender(boolean indicadorRender)
/* 1159:     */   {
/* 1160:1252 */     this.indicadorRender = indicadorRender;
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public List<Transportista> getListaTransportistaCombo()
/* 1164:     */   {
/* 1165:1256 */     if (this.listaTransportistaCombo == null) {
/* 1166:1257 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("nombre", true, null);
/* 1167:     */     }
/* 1168:1259 */     return this.listaTransportistaCombo;
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   public String getIdentificacionCliente()
/* 1172:     */   {
/* 1173:1263 */     return this.identificacionCliente;
/* 1174:     */   }
/* 1175:     */   
/* 1176:     */   public void setIdentificacionCliente(String identificacionCliente)
/* 1177:     */   {
/* 1178:1267 */     this.identificacionCliente = identificacionCliente;
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   public TipoIdentificacion getTipoIdentificacionCliente()
/* 1182:     */   {
/* 1183:1271 */     return this.tipoIdentificacionCliente;
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public void setTipoIdentificacionCliente(TipoIdentificacion tipoIdentificacionCliente)
/* 1187:     */   {
/* 1188:1275 */     this.tipoIdentificacionCliente = tipoIdentificacionCliente;
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 1192:     */   {
/* 1193:1279 */     if (this.listaTipoIdentificacion == null) {
/* 1194:1280 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 1195:     */     }
/* 1196:1282 */     return this.listaTipoIdentificacion;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 1200:     */   {
/* 1201:1286 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public String getNombreCliente()
/* 1205:     */   {
/* 1206:1290 */     return this.nombreCliente;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public void setNombreCliente(String nombreCliente)
/* 1210:     */   {
/* 1211:1294 */     this.nombreCliente = nombreCliente;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public String getDireccionCliente()
/* 1215:     */   {
/* 1216:1298 */     return this.direccionCliente;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public void setDireccionCliente(String direccionCliente)
/* 1220:     */   {
/* 1221:1302 */     this.direccionCliente = direccionCliente;
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public String getTelefonoCliente()
/* 1225:     */   {
/* 1226:1306 */     return this.telefonoCliente;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   public void setTelefonoCliente(String telefonoCliente)
/* 1230:     */   {
/* 1231:1310 */     this.telefonoCliente = telefonoCliente;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public Ciudad getCiudad()
/* 1235:     */   {
/* 1236:1314 */     return this.ciudad;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public void setCiudad(Ciudad ciudad)
/* 1240:     */   {
/* 1241:1318 */     this.ciudad = ciudad;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public EntidadUsuario getAgenteComercial()
/* 1245:     */   {
/* 1246:1322 */     return this.agenteComercial;
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 1250:     */   {
/* 1251:1326 */     this.agenteComercial = agenteComercial;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public String getEmail()
/* 1255:     */   {
/* 1256:1330 */     return this.email;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public void setEmail(String email)
/* 1260:     */   {
/* 1261:1334 */     this.email = email;
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public boolean isRenderClienteNuevo()
/* 1265:     */   {
/* 1266:1338 */     return this.renderClienteNuevo;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public void setRenderClienteNuevo(boolean renderClienteNuevo)
/* 1270:     */   {
/* 1271:1342 */     this.renderClienteNuevo = renderClienteNuevo;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public List<Contacto> getListaContacto()
/* 1275:     */   {
/* 1276:1349 */     return this.listaContacto == null ? (this.listaContacto = new ArrayList()) : this.listaContacto;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public void setListaContacto(List<Contacto> listaContacto)
/* 1280:     */   {
/* 1281:1357 */     this.listaContacto = listaContacto;
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public void actualizarFecha()
/* 1285:     */   {
/* 1286:1361 */     if ((this.pedidoCliente.getEmpresaFinal() == null) || (this.pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho() == null))
/* 1287:     */     {
/* 1288:1362 */       this.pedidoCliente.setFechaDespacho(this.pedidoCliente.getFecha());
/* 1289:1363 */       actualizarImpuestosListener();
/* 1290:1364 */       return;
/* 1291:     */     }
/* 1292:1367 */     Date fecha = this.pedidoCliente.getFecha();
/* 1293:1368 */     int diasDespacho = 0;
/* 1294:1369 */     if ((this.pedidoCliente.getEmpresaFinal() != null) && (this.pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho() != null) && 
/* 1295:1370 */       (this.pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getDiasDespacho() > 0))
/* 1296:     */     {
/* 1297:1371 */       fecha = new Date();
/* 1298:1372 */       diasDespacho = this.pedidoCliente.getEmpresaFinal().getCliente().getTipoOrdenDespacho().getDiasDespacho();
/* 1299:     */     }
/* 1300:1374 */     this.pedidoCliente.setFecha(fecha);
/* 1301:1375 */     fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, diasDespacho);
/* 1302:1376 */     this.pedidoCliente.setFechaDespacho(fecha);
/* 1303:     */     
/* 1304:1378 */     actualizarImpuestosListener();
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   public Boolean getManejaMotivoPedido()
/* 1308:     */   {
/* 1309:1382 */     if (this.manejaMotivoPedido == null) {
/* 1310:1383 */       this.manejaMotivoPedido = Boolean.valueOf(!getListaMotivoPedidoCliente().isEmpty());
/* 1311:     */     }
/* 1312:1385 */     return this.manejaMotivoPedido;
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public void setManejaMotivoPedido(Boolean manejaMotivoPedido)
/* 1316:     */   {
/* 1317:1389 */     this.manejaMotivoPedido = manejaMotivoPedido;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public boolean isIndicadorRenderCerrar()
/* 1321:     */   {
/* 1322:1393 */     return this.indicadorRenderCerrar;
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public void setIndicadorRenderCerrar(boolean indicadorRenderCerrar)
/* 1326:     */   {
/* 1327:1397 */     this.indicadorRenderCerrar = indicadorRenderCerrar;
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public boolean isIndicadorMostrarTodoAprobacion()
/* 1331:     */   {
/* 1332:1401 */     return this.indicadorMostrarTodoAprobacion;
/* 1333:     */   }
/* 1334:     */   
/* 1335:     */   public void setIndicadorMostrarTodoAprobacion(boolean indicadorMostrarTodoAprobacion)
/* 1336:     */   {
/* 1337:1405 */     this.indicadorMostrarTodoAprobacion = indicadorMostrarTodoAprobacion;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public void cargarPedidoSugerido()
/* 1341:     */   {
/* 1342:1410 */     boolean existianDetallesSugeridos = false;
/* 1343:1411 */     Map<Integer, DetallePedidoCliente> mapaDetalleProducto = new HashMap();
/* 1344:1412 */     for (DetallePedidoCliente detalle : this.pedidoCliente.getListaDetallePedidoCliente()) {
/* 1345:1413 */       if (!detalle.isEliminado())
/* 1346:     */       {
/* 1347:1414 */         if ((detalle.getProducto() != null) && (detalle.getProducto().getId() != 0)) {
/* 1348:1415 */           mapaDetalleProducto.put(Integer.valueOf(detalle.getProducto().getIdProducto()), detalle);
/* 1349:     */         }
/* 1350:1417 */         if (detalle.isIndicadorSugerido())
/* 1351:     */         {
/* 1352:1418 */           existianDetallesSugeridos = true;
/* 1353:1419 */           detalle.setEliminado(true);
/* 1354:1420 */           hayImpuestosGuardados = false;
/* 1355:1421 */           for (ImpuestoProductoPedidoCliente impuestoProductoPedidoCliente : detalle.getListaImpuestoProductoPedidoCliente())
/* 1356:     */           {
/* 1357:1422 */             impuestoProductoPedidoCliente.setEliminado(true);
/* 1358:1424 */             if (impuestoProductoPedidoCliente.getId() != 0) {
/* 1359:1425 */               hayImpuestosGuardados = true;
/* 1360:     */             }
/* 1361:     */           }
/* 1362:1429 */           if (!hayImpuestosGuardados) {
/* 1363:1430 */             detalle.setListaImpuestoProductoPedidoCliente(new ArrayList());
/* 1364:     */           }
/* 1365:     */         }
/* 1366:     */       }
/* 1367:     */     }
/* 1368:     */     boolean hayImpuestosGuardados;
/* 1369:1436 */     boolean agregadosSugeridosNuevos = false;
/* 1370:1437 */     if ((this.pedidoCliente.getEmpresa() != null) && (this.pedidoCliente.getEmpresa().getCliente() != null) && 
/* 1371:1438 */       (this.pedidoCliente.getEmpresa().getCliente().getIndicadorCargarPedidoSugerido() != null) && 
/* 1372:1439 */       (this.pedidoCliente.getEmpresa().getCliente().getIndicadorCargarPedidoSugerido().booleanValue())) {
/* 1373:     */       try
/* 1374:     */       {
/* 1375:1441 */         List<Producto> listaProductosSugeridos = this.servicioPedidoCliente.cargarPedidoSugerido(this.pedidoCliente.getIdOrganizacion(), this.pedidoCliente
/* 1376:1442 */           .getEmpresa(), this.pedidoCliente.getSubempresa());
/* 1377:1443 */         for (Producto producto : listaProductosSugeridos)
/* 1378:     */         {
/* 1379:1444 */           DetallePedidoCliente detalle = (DetallePedidoCliente)mapaDetalleProducto.get(Integer.valueOf(producto.getId()));
/* 1380:1445 */           if (detalle == null)
/* 1381:     */           {
/* 1382:1446 */             detalle = new DetallePedidoCliente();
/* 1383:1447 */             detalle.setIndicadorSugerido(true);
/* 1384:1448 */             detalle.setProducto(producto);
/* 1385:1449 */             detalle.setPedidoCliente(this.pedidoCliente);
/* 1386:1450 */             detalle.setIdOrganizacion(this.pedidoCliente.getIdOrganizacion());
/* 1387:1451 */             detalle.setIdSucursal(this.pedidoCliente.getSucursal().getIdSucursal());
/* 1388:1452 */             this.pedidoCliente.getListaDetallePedidoCliente().add(detalle);
/* 1389:     */           }
/* 1390:1454 */           if ((detalle.isEliminado()) || (detalle.getCantidad() == null) || (detalle.getCantidad().compareTo(producto.getTraCantidad()) <= 0))
/* 1391:     */           {
/* 1392:1455 */             detalle.setEliminado(false);
/* 1393:1456 */             detalle.setCantidad(producto.getTraCantidad());
/* 1394:     */           }
/* 1395:1458 */           actualizarProducto(detalle, producto);
/* 1396:1459 */           agregadosSugeridosNuevos = true;
/* 1397:     */         }
/* 1398:     */       }
/* 1399:     */       catch (AS2Exception e)
/* 1400:     */       {
/* 1401:1462 */         e.printStackTrace();
/* 1402:1463 */         JsfUtil.addErrorMessage(e, "");
/* 1403:     */       }
/* 1404:     */     }
/* 1405:1467 */     if ((existianDetallesSugeridos) && (!agregadosSugeridosNuevos)) {
/* 1406:1468 */       totalizar();
/* 1407:     */     }
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   public String getRutaPlantilla()
/* 1411:     */   {
/* 1412:1474 */     return "/resources/plantillas/ventas/AS2 Pedido Cliente.xls";
/* 1413:     */   }
/* 1414:     */   
/* 1415:     */   public String getNombrePlantilla()
/* 1416:     */   {
/* 1417:1479 */     return "AS2 Pedido Cliente.xls";
/* 1418:     */   }
/* 1419:     */   
/* 1420:     */   private boolean getAutorizacionPedidoPorCriterio()
/* 1421:     */   {
/* 1422:1483 */     return ParametrosSistema.getAutorizacionPedidoPorCriterio(AppUtil.getOrganizacion().getId()).booleanValue();
/* 1423:     */   }
/* 1424:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.PedidoClienteBean
 * JD-Core Version:    0.7.0.1
 */