/*    1:     */ package com.asinfo.as2.datosbase.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.controller.TipoCuentaBancariaBean;
/*    4:     */ import com.asinfo.as2.configuracionbase.controller.TipoIdentificacionBean;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrigenIngresos;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*    9:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   10:     */ import com.asinfo.as2.controller.LanguageController;
/*   11:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*   12:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   13:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   15:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*   19:     */ import com.asinfo.as2.entities.Banco;
/*   20:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   21:     */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   22:     */ import com.asinfo.as2.entities.Ciudad;
/*   23:     */ import com.asinfo.as2.entities.Cliente;
/*   24:     */ import com.asinfo.as2.entities.CondicionPago;
/*   25:     */ import com.asinfo.as2.entities.Contacto;
/*   26:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   27:     */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*   28:     */ import com.asinfo.as2.entities.CuentaContable;
/*   29:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   30:     */ import com.asinfo.as2.entities.Empleado;
/*   31:     */ import com.asinfo.as2.entities.Empresa;
/*   32:     */ import com.asinfo.as2.entities.FormaPago;
/*   33:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   34:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   35:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   36:     */ import com.asinfo.as2.entities.Organizacion;
/*   37:     */ import com.asinfo.as2.entities.OrigenIngresos;
/*   38:     */ import com.asinfo.as2.entities.Pais;
/*   39:     */ import com.asinfo.as2.entities.Proveedor;
/*   40:     */ import com.asinfo.as2.entities.RecargoListaPreciosCliente;
/*   41:     */ import com.asinfo.as2.entities.Recaudador;
/*   42:     */ import com.asinfo.as2.entities.Subempresa;
/*   43:     */ import com.asinfo.as2.entities.Sucursal;
/*   44:     */ import com.asinfo.as2.entities.TipoContacto;
/*   45:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   46:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   47:     */ import com.asinfo.as2.entities.Transportista;
/*   48:     */ import com.asinfo.as2.entities.Ubicacion;
/*   49:     */ import com.asinfo.as2.entities.Zona;
/*   50:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   51:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   52:     */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*   53:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   54:     */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   55:     */ import com.asinfo.as2.enumeraciones.TipoListaPreciosEnum;
/*   56:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   57:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*   58:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*   59:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   60:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   61:     */ import com.asinfo.as2.util.AppUtil;
/*   62:     */ import com.asinfo.as2.util.RutaArchivo;
/*   63:     */ import com.asinfo.as2.utils.DatosSRI;
/*   64:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   65:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*   66:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*   67:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   68:     */ import java.io.BufferedInputStream;
/*   69:     */ import java.io.File;
/*   70:     */ import java.io.FileOutputStream;
/*   71:     */ import java.io.InputStream;
/*   72:     */ import java.util.ArrayList;
/*   73:     */ import java.util.HashMap;
/*   74:     */ import java.util.List;
/*   75:     */ import java.util.Map;
/*   76:     */ import javax.annotation.PostConstruct;
/*   77:     */ import javax.ejb.EJB;
/*   78:     */ import javax.faces.bean.ManagedBean;
/*   79:     */ import javax.faces.bean.ManagedProperty;
/*   80:     */ import javax.faces.bean.ViewScoped;
/*   81:     */ import javax.faces.component.html.HtmlSelectBooleanCheckbox;
/*   82:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   83:     */ import javax.faces.model.SelectItem;
/*   84:     */ import javax.validation.constraints.Size;
/*   85:     */ import org.apache.log4j.Logger;
/*   86:     */ import org.primefaces.component.datatable.DataTable;
/*   87:     */ import org.primefaces.context.RequestContext;
/*   88:     */ import org.primefaces.event.FileUploadEvent;
/*   89:     */ import org.primefaces.event.SelectEvent;
/*   90:     */ import org.primefaces.model.LazyDataModel;
/*   91:     */ import org.primefaces.model.SortOrder;
/*   92:     */ import org.primefaces.model.TreeNode;
/*   93:     */ import org.primefaces.model.UploadedFile;
/*   94:     */ 
/*   95:     */ @ManagedBean
/*   96:     */ @ViewScoped
/*   97:     */ public class EmpresaAgilBean
/*   98:     */   extends PageControllerAS2
/*   99:     */ {
/*  100:     */   private static final long serialVersionUID = -5538312557965585206L;
/*  101:     */   @EJB
/*  102:     */   private transient ServicioEmpresa servicioEmpresa;
/*  103:     */   @EJB
/*  104:     */   private transient ServicioCondicionPago servicioCondicionPago;
/*  105:     */   @EJB
/*  106:     */   private transient ServicioFormaPago servicioFormaPago;
/*  107:     */   @EJB
/*  108:     */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  109:     */   @EJB
/*  110:     */   private transient ServicioListaPrecios servicioListaPrecios;
/*  111:     */   @EJB
/*  112:     */   private transient ServicioListaDescuentos servicioListaDescuentos;
/*  113:     */   @EJB
/*  114:     */   private transient ServicioZona servicioZona;
/*  115:     */   @EJB
/*  116:     */   private transient ServicioUsuario servicioUsuario;
/*  117:     */   @EJB
/*  118:     */   private transient ServicioPais servicioPais;
/*  119:     */   @EJB
/*  120:     */   private transient ServicioGenerico<Banco> servicioBanco;
/*  121:     */   @EJB
/*  122:     */   private transient ServicioRecaudador servicioRecaudador;
/*  123:     */   @EJB
/*  124:     */   private transient ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  125:     */   @EJB
/*  126:     */   private transient ServicioTipoCuentaBancaria servicioTipoCuentaBancaria;
/*  127:     */   @EJB
/*  128:     */   private transient ServicioTipoContacto servicioTipoContacto;
/*  129:     */   @EJB
/*  130:     */   private transient ServicioTransportista servicioTransportista;
/*  131:     */   @EJB
/*  132:     */   private transient ServicioOrigenIngresos servicioOrigenIngresos;
/*  133:     */   @EJB
/*  134:     */   protected transient ServicioCiudad servicioCiudad;
/*  135:     */   @EJB
/*  136:     */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  137:     */   private Empresa empresa;
/*  138:     */   private String agenteComercialSeleccionado;
/*  139:     */   private String recaudadorSeleccionado;
/*  140:     */   private RecargoListaPreciosCliente recargoListaPreciosClienteSeleccionado;
/*  141:     */   private CuentaContable cuentaContable;
/*  142:     */   private List<SelectItem> listaTipoEmpresa;
/*  143:     */   private List<OrigenIngresos> listaOrigenIngresos;
/*  144:     */   private List<SelectItem> listaMetodoFacturacion;
/*  145:     */   private DireccionEmpresa direccionEmpresa;
/*  146:     */   private boolean empresaExiste;
/*  147:     */   private static final String NOMBRE = "nombre";
/*  148:     */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  149:     */   private Ciudad ciudad;
/*  150:     */   private LazyDataModel<Empresa> listaEmpresa;
/*  151:     */   private List<ListaPrecios> listaPrecios;
/*  152:     */   private List<ListaDescuentos> listaListaDescuentos;
/*  153:     */   private List<ListaPrecios> listaPreciosRecargos;
/*  154:     */   private List<FormaPago> listaFormaPagoCliente;
/*  155:     */   private List<FormaPago> listaFormaPagoProveedor;
/*  156:     */   private List<CondicionPago> listaCondicionPagoCliente;
/*  157:     */   private List<CondicionPago> listaCondicionPagoProveedor;
/*  158:     */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  159:     */   private List<CategoriaRetencion> listaCategoriaRetencion;
/*  160:     */   private List<Zona> listaZona;
/*  161:     */   private List<Recaudador> listaRecaudador;
/*  162:     */   private List<EntidadUsuario> listaAgentesComerciales;
/*  163:     */   private List<Pais> listaPais;
/*  164:     */   private List<Banco> listaBanco;
/*  165:     */   private List<TipoCuentaBancaria> listaTipoCuentaBancaria;
/*  166:     */   private List<TipoEmpresaEnum> listaTipoCliente;
/*  167:     */   private SelectItem[] listaTipoClienteItem;
/*  168:     */   private List<Transportista> listaTransportistaCombo;
/*  169:     */   private List<TipoContacto> listaTipoContacto;
/*  170:     */   @Size(min=2, max=50)
/*  171:     */   private String direccionCliente;
/*  172:     */   @Size(max=13)
/*  173:     */   private String telefonoCliente;
/*  174:     */   private TreeNode selectedNode;
/*  175:     */   private DataTable dtEmpresa;
/*  176:     */   private DataTable dtDirecciones;
/*  177:     */   private DataTable dtAutorizacionProveedorSRI;
/*  178:     */   private DataTable dtCuentaBancarioEmpresa;
/*  179:     */   private DataTable dtCuentaContable;
/*  180:     */   private DataTable dtSubempresa;
/*  181:     */   private DataTable dtFormaPagoSRI;
/*  182:     */   @ManagedProperty("#{tipoIdentificacionBean}")
/*  183:     */   private TipoIdentificacionBean tipoIdentificacionBean;
/*  184:     */   @ManagedProperty("#{tipoCuentaBancariaBean}")
/*  185:     */   private TipoCuentaBancariaBean tipoCuentaBancariaBean;
/*  186:     */   
/*  187:     */   @PostConstruct
/*  188:     */   public void init()
/*  189:     */   {
/*  190: 222 */     this.listaEmpresa = new LazyDataModel()
/*  191:     */     {
/*  192:     */       private static final long serialVersionUID = 1L;
/*  193:     */       
/*  194:     */       public List<Empresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  195:     */       {
/*  196: 230 */         List<Empresa> lista = new ArrayList();
/*  197:     */         
/*  198: 232 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  199: 233 */         filters.put("indicadorClienteProveedor", "true");
/*  200: 234 */         lista = EmpresaAgilBean.this.servicioEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  201: 235 */         EmpresaAgilBean.this.listaEmpresa.setRowCount(EmpresaAgilBean.this.servicioEmpresa.contarPorCriterio(filters));
/*  202: 236 */         return lista;
/*  203:     */       }
/*  204:     */     };
/*  205:     */   }
/*  206:     */   
/*  207:     */   public String editar()
/*  208:     */   {
/*  209: 248 */     if ((getEmpresa() != null) && (getEmpresa().getId() != 0))
/*  210:     */     {
/*  211: 249 */       this.empresa = this.servicioEmpresa.cargarDetalle(getEmpresa());
/*  212: 250 */       if (this.empresa.getDirecciones().size() != 0) {
/*  213: 251 */         this.direccionCliente = ((DireccionEmpresa)this.empresa.getDirecciones().get(0)).getDireccionCompleta();
/*  214:     */       }
/*  215: 253 */       this.telefonoCliente = ((DireccionEmpresa)this.empresa.getDirecciones().get(0)).getTelefono1();
/*  216: 254 */       this.ciudad = ((DireccionEmpresa)this.empresa.getDirecciones().get(0)).getCiudad();
/*  217: 255 */       this.empresa = this.servicioEmpresa.cargarDetalleAtributos(this.empresa);
/*  218:     */       
/*  219: 257 */       setEditado(true);
/*  220:     */     }
/*  221:     */     else
/*  222:     */     {
/*  223: 259 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  224:     */     }
/*  225: 261 */     return "";
/*  226:     */   }
/*  227:     */   
/*  228:     */   public String guardar()
/*  229:     */   {
/*  230:     */     try
/*  231:     */     {
/*  232: 272 */       cargarCodigo();
/*  233: 273 */       if ((this.empresa.isIndicadorProveedor()) || (this.empresa.isIndicadorCliente()))
/*  234:     */       {
/*  235: 274 */         Empresa empresaNew = this.servicioEmpresa.crearEmpresaConDatosBasicos(this.empresa.getId(), this.empresa.getCodigo(), this.empresa.getIdentificacion(), this.empresa
/*  236: 275 */           .getTipoIdentificacion(), this.empresa.getNombreFiscal(), this.direccionCliente, this.telefonoCliente, 
/*  237: 276 */           AppUtil.getOrganizacion().getIdOrganizacion(), AppUtil.getSucursal().getIdSucursal(), this.ciudad, null, this.empresa.getEmail1(), this.empresa
/*  238: 277 */           .isIndicadorCliente(), this.empresa.isIndicadorProveedor(), this.empresa.getTipoEmpresa(), this.empresa.getListaEmpresaAtributo());
/*  239:     */         
/*  240: 279 */         limpiar();
/*  241: 280 */         setEditado(false);
/*  242: 281 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  243:     */       }
/*  244:     */       else
/*  245:     */       {
/*  246: 283 */         addErrorMessage(getLanguageController().getMensaje("msg_error_seleccionar_cliente_proveedor"));
/*  247:     */       }
/*  248:     */     }
/*  249:     */     catch (ExcepcionAS2Identification e)
/*  250:     */     {
/*  251: 286 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  252: 287 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  253:     */     }
/*  254:     */     catch (ExcepcionAS2 e)
/*  255:     */     {
/*  256: 289 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  257: 290 */       LOG.info("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", e);
/*  258:     */     }
/*  259:     */     catch (Exception ex)
/*  260:     */     {
/*  261: 292 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  262: 293 */       LOG.error("ERROR AL GUARDAR DATOS CLIENTE CON DATOS BASICOS", ex);
/*  263:     */     }
/*  264: 296 */     return "";
/*  265:     */   }
/*  266:     */   
/*  267:     */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/*  268:     */   {
/*  269: 303 */     if (this.listaTipoIdentificacion == null) {
/*  270: 304 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/*  271:     */     }
/*  272: 306 */     return this.listaTipoIdentificacion;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/*  276:     */   {
/*  277: 314 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public String eliminar()
/*  281:     */   {
/*  282:     */     try
/*  283:     */     {
/*  284: 326 */       this.servicioEmpresa.eliminar(getEmpresa());
/*  285: 327 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  286: 328 */       cargarDatos();
/*  287:     */     }
/*  288:     */     catch (ExcepcionAS2 e)
/*  289:     */     {
/*  290: 332 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  291:     */     }
/*  292:     */     catch (Exception e)
/*  293:     */     {
/*  294: 336 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  295: 337 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  296:     */     }
/*  297: 340 */     return "";
/*  298:     */   }
/*  299:     */   
/*  300:     */   public String limpiar()
/*  301:     */   {
/*  302: 350 */     crearEmpresa();
/*  303: 351 */     this.direccionCliente = null;
/*  304: 352 */     this.telefonoCliente = null;
/*  305: 353 */     this.ciudad = null;
/*  306: 354 */     return "";
/*  307:     */   }
/*  308:     */   
/*  309:     */   public String cargarDatos()
/*  310:     */   {
/*  311:     */     try
/*  312:     */     {
/*  313: 365 */       setEditado(false);
/*  314:     */       
/*  315: 367 */       limpiar();
/*  316:     */     }
/*  317:     */     catch (Exception e)
/*  318:     */     {
/*  319: 370 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  320: 371 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  321:     */     }
/*  322: 373 */     return "";
/*  323:     */   }
/*  324:     */   
/*  325:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  326:     */   {
/*  327: 377 */     List<Empresa> lista = this.servicioEmpresa.autocompletarClientes(consulta);
/*  328:     */     
/*  329: 379 */     return lista;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  333:     */   {
/*  334: 383 */     List<Empresa> lista = this.servicioEmpresa.autocompletarProveedores(consulta);
/*  335:     */     
/*  336: 385 */     return lista;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public List<Empresa> autocompletarSubempresa(String consulta)
/*  340:     */   {
/*  341: 390 */     HashMap<String, String> filters = new HashMap();
/*  342: 391 */     filters.put("indicadorCliente", "true");
/*  343: 392 */     filters.put("tipoCliente", "!=" + TipoEmpresaEnum.PRINCIPAL);
/*  344: 393 */     filters.put("nombreFiscal", consulta.trim());
/*  345: 394 */     filters.put("nombreComercial", consulta.trim());
/*  346: 395 */     filters.put("identificacion", consulta.trim());
/*  347:     */     
/*  348: 397 */     List<Empresa> lista = this.servicioEmpresa.obtenerListaCombo("", false, filters);
/*  349:     */     
/*  350: 399 */     return lista;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void crearCliente(AjaxBehaviorEvent event)
/*  354:     */   {
/*  355: 410 */     boolean indicadorCliente = Boolean.parseBoolean(((HtmlSelectBooleanCheckbox)event.getSource()).getValue().toString());
/*  356: 412 */     if (indicadorCliente)
/*  357:     */     {
/*  358: 414 */       if (getEmpresa().getCliente() == null)
/*  359:     */       {
/*  360: 415 */         Cliente cliente = new Cliente();
/*  361: 416 */         cliente.setFormaPago(new FormaPago());
/*  362: 417 */         cliente.setCondicionPago(new CondicionPago());
/*  363: 418 */         cliente.setEmpresa(getEmpresa());
/*  364: 419 */         cliente.setListaPrecios(new ListaPrecios());
/*  365: 420 */         cliente.setAgenteComercial(new EntidadUsuario());
/*  366: 421 */         cliente.setZona(new Zona());
/*  367: 422 */         cliente.setNumeroCuotas(1);
/*  368: 423 */         cliente.setTipoCliente(TipoEmpresaEnum.PRINCIPAL);
/*  369: 424 */         getEmpresa().setCliente(cliente);
/*  370:     */       }
/*  371:     */     }
/*  372: 428 */     else if (getEmpresa().getCliente() == null) {}
/*  373:     */   }
/*  374:     */   
/*  375:     */   public void crearProveedor(AjaxBehaviorEvent event)
/*  376:     */   {
/*  377: 441 */     boolean indicadorProveedor = Boolean.parseBoolean(((HtmlSelectBooleanCheckbox)event.getSource()).getValue().toString());
/*  378: 443 */     if (indicadorProveedor)
/*  379:     */     {
/*  380: 444 */       if (getEmpresa().getProveedor() == null)
/*  381:     */       {
/*  382: 445 */         Proveedor proveedor = new Proveedor();
/*  383: 446 */         proveedor.setFormaPago(new FormaPago());
/*  384: 447 */         proveedor.setCondicionPago(new CondicionPago());
/*  385: 448 */         proveedor.setCategoriaRetencion(new CategoriaRetencion());
/*  386: 449 */         proveedor.setEmpresa(getEmpresa());
/*  387: 450 */         getEmpresa().setProveedor(proveedor);
/*  388:     */       }
/*  389:     */     }
/*  390: 453 */     else if (getEmpresa().getProveedor() == null) {}
/*  391:     */   }
/*  392:     */   
/*  393:     */   public String agregarDireccion()
/*  394:     */   {
/*  395: 466 */     DireccionEmpresa direccion = new DireccionEmpresa();
/*  396: 467 */     direccion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  397:     */     
/*  398: 469 */     Ubicacion ubicacion = new Ubicacion();
/*  399: 470 */     ubicacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  400: 471 */     ubicacion.setDireccion5(" ");
/*  401: 472 */     direccion.setUbicacion(ubicacion);
/*  402: 473 */     direccion.setEmpresa(getEmpresa());
/*  403: 474 */     getEmpresa().getDirecciones().add(direccion);
/*  404:     */     
/*  405: 476 */     return "";
/*  406:     */   }
/*  407:     */   
/*  408:     */   public String eliminarDireccion()
/*  409:     */   {
/*  410: 486 */     DireccionEmpresa direccionEmpresa = (DireccionEmpresa)this.dtDirecciones.getRowData();
/*  411: 487 */     direccionEmpresa.setEliminado(true);
/*  412: 488 */     direccionEmpresa.getUbicacion().setEliminado(true);
/*  413:     */     
/*  414: 490 */     return "";
/*  415:     */   }
/*  416:     */   
/*  417:     */   public String eliminarCuentaBancariaEmpresa()
/*  418:     */   {
/*  419: 494 */     CuentaBancariaEmpresa cuentaBancariaEmpresa = (CuentaBancariaEmpresa)this.dtCuentaBancarioEmpresa.getRowData();
/*  420: 495 */     cuentaBancariaEmpresa.setEliminado(true);
/*  421: 496 */     cuentaBancariaEmpresa.getCuentaBancaria().setEliminado(true);
/*  422:     */     
/*  423: 498 */     return "";
/*  424:     */   }
/*  425:     */   
/*  426:     */   public String agregarAutorizacionSRI()
/*  427:     */   {
/*  428: 508 */     AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  429: 509 */     autorizacionProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  430: 510 */     autorizacionProveedorSRI.setEmpresa(getEmpresa());
/*  431: 511 */     autorizacionProveedorSRI.setActivo(true);
/*  432:     */     
/*  433: 513 */     getEmpresa().getListaAutorizacionProveedorSRI().add(autorizacionProveedorSRI);
/*  434:     */     
/*  435: 515 */     return "";
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void agregarRecargoListener()
/*  439:     */   {
/*  440: 522 */     RecargoListaPreciosCliente recargo = new RecargoListaPreciosCliente(this.empresa);
/*  441: 523 */     this.empresa.getListaRecargoListaPreciosCliente().add(recargo);
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void cargarCuentaContable(SelectEvent event)
/*  445:     */   {
/*  446: 530 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/*  447: 531 */     this.recargoListaPreciosClienteSeleccionado.setCuentaContable(cuentaContable);
/*  448:     */   }
/*  449:     */   
/*  450:     */   public String eliminarAutorizacionSRI()
/*  451:     */   {
/*  452: 541 */     AutorizacionProveedorSRI autorizacionProveedorSRI = (AutorizacionProveedorSRI)this.dtAutorizacionProveedorSRI.getRowData();
/*  453: 542 */     autorizacionProveedorSRI.setEliminado(true);
/*  454:     */     
/*  455: 544 */     return "";
/*  456:     */   }
/*  457:     */   
/*  458:     */   public void agregarContactoListener()
/*  459:     */   {
/*  460: 548 */     Contacto contacto = new Contacto();
/*  461: 549 */     contacto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  462: 550 */     contacto.setEmpresa(getEmpresa());
/*  463: 551 */     getEmpresa().getListaContacto().add(contacto);
/*  464:     */   }
/*  465:     */   
/*  466:     */   public String eliminarContacto(Contacto contacto)
/*  467:     */   {
/*  468: 561 */     contacto.setEliminado(true);
/*  469: 562 */     return "";
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void processUpload(FileUploadEvent eventImagen)
/*  473:     */   {
/*  474:     */     try
/*  475:     */     {
/*  476: 576 */       String fileName = "emp_" + AppUtil.getOrganizacion().getId() + "_" + getEmpresa().getCodigo() + recuperaExtencion(eventImagen.getFile().getFileName());
/*  477:     */       
/*  478: 578 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "empleado");
/*  479:     */       
/*  480: 580 */       File directorio = new File(uploadDir);
/*  481:     */       
/*  482: 582 */       File file = new File(uploadDir + fileName);
/*  483: 584 */       if (!directorio.exists()) {
/*  484: 585 */         directorio.mkdirs();
/*  485:     */       }
/*  486: 588 */       InputStream input = new BufferedInputStream(eventImagen.getFile().getInputstream());
/*  487:     */       
/*  488: 590 */       this.empresa.getEmpleado().setImagen(fileName);
/*  489:     */       
/*  490: 592 */       FileOutputStream output = new FileOutputStream(file);
/*  491: 594 */       while (input.available() != 0) {
/*  492: 595 */         output.write(input.read());
/*  493:     */       }
/*  494: 598 */       input.close();
/*  495: 599 */       output.close();
/*  496:     */       
/*  497: 601 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_imagen"));
/*  498:     */     }
/*  499:     */     catch (Exception e)
/*  500:     */     {
/*  501: 604 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/*  502: 605 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  503:     */     }
/*  504:     */   }
/*  505:     */   
/*  506:     */   public String recuperaExtencion(String nombreArchivo)
/*  507:     */   {
/*  508: 618 */     int mid = nombreArchivo.lastIndexOf('.');
/*  509: 619 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/*  510:     */   }
/*  511:     */   
/*  512:     */   public ServicioEmpresa getServicioEmpresa()
/*  513:     */   {
/*  514: 628 */     return this.servicioEmpresa;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public void setServicioEmpresa(ServicioEmpresa servicioEmpresa)
/*  518:     */   {
/*  519: 638 */     this.servicioEmpresa = servicioEmpresa;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public Empresa getEmpresa()
/*  523:     */   {
/*  524: 647 */     if (this.empresa == null) {
/*  525: 648 */       crearEmpresa();
/*  526:     */     }
/*  527: 650 */     return this.empresa;
/*  528:     */   }
/*  529:     */   
/*  530:     */   private void crearEmpresa()
/*  531:     */   {
/*  532: 657 */     this.empresa = new Empresa();
/*  533: 658 */     this.empresa.setCategoriaEmpresa(new CategoriaEmpresa());
/*  534: 659 */     this.empresa.setTipoIdentificacion(new TipoIdentificacion());
/*  535: 660 */     this.empresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  536: 661 */     this.empresa.setIdSucursal(AppUtil.getSucursal().getId());
/*  537:     */   }
/*  538:     */   
/*  539:     */   public void agregarCuentaBancariaEmpresa()
/*  540:     */   {
/*  541: 666 */     CuentaBancaria cuentaBancaria = new CuentaBancaria();
/*  542: 667 */     cuentaBancaria.setPais(new Pais());
/*  543: 668 */     cuentaBancaria.setBanco(new Banco());
/*  544: 669 */     cuentaBancaria.setContacto("N/A");
/*  545: 670 */     cuentaBancaria.setTelefonoContacto("999999");
/*  546: 671 */     cuentaBancaria.setDescripcion("N/A");
/*  547: 672 */     cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria());
/*  548: 673 */     cuentaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  549: 674 */     cuentaBancaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  550:     */     
/*  551: 676 */     CuentaBancariaEmpresa cuentaBancariaEmpresa = new CuentaBancariaEmpresa();
/*  552: 677 */     cuentaBancariaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  553: 678 */     cuentaBancariaEmpresa.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getId()));
/*  554: 679 */     cuentaBancariaEmpresa.setDescripcion("N/A");
/*  555:     */     
/*  556: 681 */     cuentaBancariaEmpresa.setCuentaBancaria(cuentaBancaria);
/*  557: 682 */     cuentaBancariaEmpresa.setEmpresa(this.empresa);
/*  558:     */     
/*  559: 684 */     getEmpresa().getListaCuentaBancariaEmpresa().add(cuentaBancariaEmpresa);
/*  560:     */   }
/*  561:     */   
/*  562:     */   public void actualizarCiudad()
/*  563:     */   {
/*  564: 689 */     this.direccionEmpresa = ((DireccionEmpresa)this.dtDirecciones.getRowData());
/*  565:     */   }
/*  566:     */   
/*  567:     */   public void cargarCiudad()
/*  568:     */   {
/*  569:     */     try
/*  570:     */     {
/*  571: 694 */       Ciudad ciudad = (Ciudad)this.selectedNode.getData();
/*  572: 695 */       this.direccionEmpresa.setCiudad(ciudad);
/*  573:     */     }
/*  574:     */     catch (Exception e)
/*  575:     */     {
/*  576: 697 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  577:     */     }
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void buscarEmpresaEvent()
/*  581:     */   {
/*  582: 704 */     if (!ParametrosSistema.getRecargosEnFactura(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  583:     */       try
/*  584:     */       {
/*  585: 706 */         Empresa empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(getEmpresa().getIdentificacion());
/*  586: 707 */         empresa = this.servicioEmpresa.cargarDetalle(empresa);
/*  587: 708 */         setEmpresaExiste(true);
/*  588: 709 */         setEmpresa(empresa);
/*  589: 710 */         RequestContext.getCurrentInstance().update("panelNuevo");
/*  590:     */       }
/*  591:     */       catch (ExcepcionAS2 e)
/*  592:     */       {
/*  593: 712 */         setEmpresaExiste(false);
/*  594: 713 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  595:     */       }
/*  596:     */     }
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void listarCuentaContable()
/*  600:     */   {
/*  601: 720 */     Map<String, Object> properties = new HashMap();
/*  602: 721 */     properties.put("modal", Boolean.valueOf(true));
/*  603: 722 */     properties.put("resizable", Boolean.valueOf(true));
/*  604: 723 */     properties.put("draggable", Boolean.valueOf(true));
/*  605: 724 */     properties.put("width", Integer.valueOf(850));
/*  606: 725 */     properties.put("height", Integer.valueOf(400));
/*  607: 726 */     RequestContext.getCurrentInstance().openDialog("/resources/componentes/seleccionarCuentaContable");
/*  608:     */   }
/*  609:     */   
/*  610:     */   public String cancelar()
/*  611:     */   {
/*  612: 731 */     setEditado(false);
/*  613: 732 */     setEmpresaExiste(false);
/*  614: 733 */     limpiar();
/*  615: 734 */     return "";
/*  616:     */   }
/*  617:     */   
/*  618:     */   public void setEmpresa(Empresa empresa)
/*  619:     */   {
/*  620: 744 */     this.empresa = empresa;
/*  621:     */   }
/*  622:     */   
/*  623:     */   public LazyDataModel<Empresa> getListaEmpresa()
/*  624:     */   {
/*  625: 753 */     return this.listaEmpresa;
/*  626:     */   }
/*  627:     */   
/*  628:     */   public void setListaEmpresa(LazyDataModel<Empresa> listaEmpresa)
/*  629:     */   {
/*  630: 763 */     this.listaEmpresa = listaEmpresa;
/*  631:     */   }
/*  632:     */   
/*  633:     */   public DataTable getDtEmpresa()
/*  634:     */   {
/*  635: 772 */     return this.dtEmpresa;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public void setDtEmpresa(DataTable dtEmpresa)
/*  639:     */   {
/*  640: 782 */     this.dtEmpresa = dtEmpresa;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public TipoIdentificacionBean getTipoIdentificacionBean()
/*  644:     */   {
/*  645: 791 */     return this.tipoIdentificacionBean;
/*  646:     */   }
/*  647:     */   
/*  648:     */   public void setTipoIdentificacionBean(TipoIdentificacionBean tipoIdentificacionBean)
/*  649:     */   {
/*  650: 801 */     this.tipoIdentificacionBean = tipoIdentificacionBean;
/*  651:     */   }
/*  652:     */   
/*  653:     */   public List<SelectItem> getListaTipoEmpresa()
/*  654:     */   {
/*  655: 811 */     if (this.listaTipoEmpresa == null)
/*  656:     */     {
/*  657: 812 */       this.listaTipoEmpresa = new ArrayList();
/*  658: 813 */       for (TipoEmpresa t : TipoEmpresa.values())
/*  659:     */       {
/*  660: 814 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  661: 815 */         this.listaTipoEmpresa.add(item);
/*  662:     */       }
/*  663:     */     }
/*  664: 819 */     return this.listaTipoEmpresa;
/*  665:     */   }
/*  666:     */   
/*  667:     */   public DataTable getDtDirecciones()
/*  668:     */   {
/*  669: 828 */     return this.dtDirecciones;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public void setDtDirecciones(DataTable dtDirecciones)
/*  673:     */   {
/*  674: 838 */     this.dtDirecciones = dtDirecciones;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public List<DireccionEmpresa> getDirecciones()
/*  678:     */   {
/*  679: 848 */     List<DireccionEmpresa> direcciones = new ArrayList();
/*  680: 849 */     for (DireccionEmpresa de : getEmpresa().getDirecciones()) {
/*  681: 851 */       if (!de.isEliminado()) {
/*  682: 852 */         direcciones.add(de);
/*  683:     */       }
/*  684:     */     }
/*  685: 856 */     return direcciones;
/*  686:     */   }
/*  687:     */   
/*  688:     */   public List<CuentaBancariaEmpresa> getCuentaBancariaEmpresa()
/*  689:     */   {
/*  690: 861 */     List<CuentaBancariaEmpresa> cuentaBancariaEmpresa = new ArrayList();
/*  691: 862 */     for (CuentaBancariaEmpresa cbe : this.empresa.getListaCuentaBancariaEmpresa()) {
/*  692: 863 */       if (!cbe.isEliminado()) {
/*  693: 864 */         cuentaBancariaEmpresa.add(cbe);
/*  694:     */       }
/*  695:     */     }
/*  696: 867 */     return cuentaBancariaEmpresa;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public List<Subempresa> getListaSubempresa()
/*  700:     */   {
/*  701: 871 */     List<Subempresa> lista = new ArrayList();
/*  702: 872 */     for (Subempresa subempresa : getEmpresa().getListaSubempresa()) {
/*  703: 873 */       if (!subempresa.isEliminado()) {
/*  704: 874 */         lista.add(subempresa);
/*  705:     */       }
/*  706:     */     }
/*  707: 877 */     return lista;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public List<FormaPagoSRI> getListaFormaPagoSRIProveedor()
/*  711:     */   {
/*  712: 881 */     List<FormaPagoSRI> lista = new ArrayList();
/*  713: 882 */     for (FormaPagoSRI formaPagoSRI : this.empresa.getListaFormaPagoSRI()) {
/*  714: 883 */       if (!formaPagoSRI.isEliminado()) {
/*  715: 884 */         lista.add(formaPagoSRI);
/*  716:     */       }
/*  717:     */     }
/*  718: 887 */     return lista;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public String agregarSubempresa()
/*  722:     */   {
/*  723: 891 */     Subempresa subempresa = new Subempresa();
/*  724: 892 */     subempresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  725: 893 */     subempresa.setIdSucursal(AppUtil.getSucursal().getId());
/*  726: 894 */     subempresa.setEmpresaPadre(getEmpresa());
/*  727: 895 */     getEmpresa().getListaSubempresa().add(subempresa);
/*  728: 896 */     return "";
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String agregarFormaPagoSRI()
/*  732:     */   {
/*  733: 900 */     FormaPagoSRI formaPagoSRI = new FormaPagoSRI();
/*  734: 901 */     formaPagoSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  735: 902 */     formaPagoSRI.setIdSucursal(AppUtil.getSucursal().getId());
/*  736: 903 */     formaPagoSRI.setEmpresa(this.empresa);
/*  737: 904 */     getEmpresa().getListaFormaPagoSRI().add(formaPagoSRI);
/*  738: 905 */     return "";
/*  739:     */   }
/*  740:     */   
/*  741:     */   public String eliminarSubempresa()
/*  742:     */   {
/*  743: 909 */     Subempresa subempresa = (Subempresa)this.dtSubempresa.getRowData();
/*  744: 910 */     subempresa.setEliminado(true);
/*  745: 911 */     return "";
/*  746:     */   }
/*  747:     */   
/*  748:     */   public String eliminarFormaPagoSRI()
/*  749:     */   {
/*  750: 915 */     FormaPagoSRI formaPagoSRI = (FormaPagoSRI)this.dtFormaPagoSRI.getRowData();
/*  751: 916 */     formaPagoSRI.setEliminado(true);
/*  752: 917 */     return "";
/*  753:     */   }
/*  754:     */   
/*  755:     */   public void setDirecciones(List<DireccionEmpresa> direcciones)
/*  756:     */   {
/*  757: 927 */     getEmpresa().setDirecciones(direcciones);
/*  758:     */   }
/*  759:     */   
/*  760:     */   public List<AutorizacionProveedorSRI> getListaAutorizacionProveedorSRI()
/*  761:     */   {
/*  762: 937 */     List<AutorizacionProveedorSRI> lista = new ArrayList();
/*  763: 938 */     for (AutorizacionProveedorSRI a : getEmpresa().getListaAutorizacionProveedorSRI()) {
/*  764: 940 */       if (!a.isEliminado()) {
/*  765: 941 */         lista.add(a);
/*  766:     */       }
/*  767:     */     }
/*  768: 945 */     return lista;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public List<RecargoListaPreciosCliente> getListaRecargoListaPreciosCliente()
/*  772:     */   {
/*  773: 950 */     List<RecargoListaPreciosCliente> lista = new ArrayList();
/*  774: 952 */     for (RecargoListaPreciosCliente recargo : this.empresa.getListaRecargoListaPreciosCliente()) {
/*  775: 953 */       if ((!recargo.isEliminado()) || (!recargo.getListaPrecios().isIndicadorCompra())) {
/*  776: 954 */         lista.add(recargo);
/*  777:     */       }
/*  778:     */     }
/*  779: 958 */     return lista;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public DataTable getDtAutorizacionProveedorSRI()
/*  783:     */   {
/*  784: 967 */     return this.dtAutorizacionProveedorSRI;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setDtAutorizacionProveedorSRI(DataTable dtAutorizacionProveedorSRI)
/*  788:     */   {
/*  789: 977 */     this.dtAutorizacionProveedorSRI = dtAutorizacionProveedorSRI;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public List<ListaPrecios> getListaPrecios()
/*  793:     */   {
/*  794: 981 */     if (this.listaPrecios == null)
/*  795:     */     {
/*  796: 982 */       Map<String, String> filters = new HashMap();
/*  797: 983 */       filters.put("tipoListaPrecios", TipoListaPreciosEnum.LISTA_PRECIOS.toString());
/*  798: 984 */       filters.put("indicadorVenta", "true");
/*  799: 985 */       filters.put("activo", "true");
/*  800: 986 */       this.listaPrecios = this.servicioListaPrecios.obtenerListaCombo("codigo", true, filters);
/*  801:     */     }
/*  802: 989 */     return this.listaPrecios;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public List<ListaPrecios> getListaPreciosProveedor()
/*  806:     */   {
/*  807: 993 */     List<ListaPrecios> lista = new ArrayList();
/*  808: 994 */     Map<String, String> filters = new HashMap();
/*  809: 995 */     filters.put("tipoListaPrecios", TipoListaPreciosEnum.LISTA_PRECIOS.toString());
/*  810: 996 */     filters.put("indicadorCompra", "true");
/*  811: 997 */     filters.put("activo", "true");
/*  812: 998 */     lista = this.servicioListaPrecios.obtenerListaCombo("codigo", true, filters);
/*  813:     */     
/*  814:1000 */     return lista;
/*  815:     */   }
/*  816:     */   
/*  817:     */   public void setListaPrecios(List<ListaPrecios> listaPrecios)
/*  818:     */   {
/*  819:1004 */     this.listaPrecios = listaPrecios;
/*  820:     */   }
/*  821:     */   
/*  822:     */   public String actualizarGenero()
/*  823:     */   {
/*  824:1008 */     return "";
/*  825:     */   }
/*  826:     */   
/*  827:     */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/*  828:     */   {
/*  829:1017 */     if (this.listaCategoriaEmpresa == null) {
/*  830:1018 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/*  831:     */     }
/*  832:1020 */     return this.listaCategoriaEmpresa;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/*  836:     */   {
/*  837:1030 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public List<FormaPago> getListaFormaPagoCliente()
/*  841:     */   {
/*  842:1039 */     if (this.listaFormaPagoCliente == null) {
/*  843:1040 */       this.listaFormaPagoCliente = this.servicioFormaPago.obtenerListaCombo("codigo", true, null);
/*  844:     */     }
/*  845:1042 */     return this.listaFormaPagoCliente;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public void setListaFormaPagoCliente(List<FormaPago> listaFormaPagoCliente)
/*  849:     */   {
/*  850:1052 */     this.listaFormaPagoCliente = listaFormaPagoCliente;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public List<FormaPago> getListaFormaPagoProveedor()
/*  854:     */   {
/*  855:1061 */     if (this.listaFormaPagoProveedor == null) {
/*  856:1062 */       this.listaFormaPagoProveedor = this.servicioFormaPago.obtenerListaCombo("codigo", true, null);
/*  857:     */     }
/*  858:1064 */     return this.listaFormaPagoProveedor;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public void setListaFormaPagoProveedor(List<FormaPago> listaFormaPagoProveedor)
/*  862:     */   {
/*  863:1074 */     this.listaFormaPagoProveedor = listaFormaPagoProveedor;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public List<CondicionPago> getListaCondicionPagoCliente()
/*  867:     */   {
/*  868:1083 */     if (this.listaCondicionPagoCliente == null) {
/*  869:1084 */       this.listaCondicionPagoCliente = this.servicioCondicionPago.obtenerListaCombo("nombre", true, null);
/*  870:     */     }
/*  871:1086 */     return this.listaCondicionPagoCliente;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setListaCondicionPagoCliente(List<CondicionPago> listaCondicionPagoCliente)
/*  875:     */   {
/*  876:1096 */     this.listaCondicionPagoCliente = listaCondicionPagoCliente;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public List<CondicionPago> getListaCondicionPagoProveedor()
/*  880:     */   {
/*  881:1105 */     if (this.listaCondicionPagoProveedor == null) {
/*  882:1106 */       this.listaCondicionPagoProveedor = this.servicioCondicionPago.obtenerListaCombo("nombre", true, null);
/*  883:     */     }
/*  884:1108 */     return this.listaCondicionPagoProveedor;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void setListaCondicionPagoProveedor(List<CondicionPago> listaCondicionPagoProveedor)
/*  888:     */   {
/*  889:1118 */     this.listaCondicionPagoProveedor = listaCondicionPagoProveedor;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public List<Zona> getListaZona()
/*  893:     */   {
/*  894:1127 */     if (this.listaZona == null) {
/*  895:1128 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/*  896:     */     }
/*  897:1130 */     return this.listaZona;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void setListaZona(List<Zona> listaZona)
/*  901:     */   {
/*  902:1140 */     this.listaZona = listaZona;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public List<EntidadUsuario> getListaAgentesComerciales()
/*  906:     */   {
/*  907:1144 */     if (this.listaAgentesComerciales == null) {
/*  908:1145 */       this.listaAgentesComerciales = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId(), Boolean.valueOf(true));
/*  909:     */     }
/*  910:1147 */     return this.listaAgentesComerciales;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public String getAgenteComercialSeleccionado()
/*  914:     */   {
/*  915:1151 */     this.agenteComercialSeleccionado = "";
/*  916:1152 */     if (this.empresa.getCliente().getAgenteComercial() != null) {
/*  917:1153 */       this.agenteComercialSeleccionado = Integer.toString(this.empresa.getCliente().getAgenteComercial().getId());
/*  918:     */     }
/*  919:1155 */     return this.agenteComercialSeleccionado;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public void setAgenteComercialSeleccionado(String AgenteComercialSeleccionado)
/*  923:     */   {
/*  924:1159 */     if (this.agenteComercialSeleccionado != AgenteComercialSeleccionado)
/*  925:     */     {
/*  926:1161 */       this.agenteComercialSeleccionado = AgenteComercialSeleccionado;
/*  927:     */       
/*  928:1163 */       EntidadUsuario agenteComercial = null;
/*  929:1164 */       if (!this.agenteComercialSeleccionado.isEmpty())
/*  930:     */       {
/*  931:1165 */         int idAgenteComercial = Integer.parseInt(this.agenteComercialSeleccionado);
/*  932:1166 */         agenteComercial = this.servicioUsuario.buscarPorId(Integer.valueOf(idAgenteComercial));
/*  933:     */       }
/*  934:1168 */       getEmpresa().getCliente().setAgenteComercial(agenteComercial);
/*  935:     */     }
/*  936:     */   }
/*  937:     */   
/*  938:     */   public TipoCuentaBancariaBean getTipoCuentaBancariaBean()
/*  939:     */   {
/*  940:1179 */     return this.tipoCuentaBancariaBean;
/*  941:     */   }
/*  942:     */   
/*  943:     */   public void setTipoCuentaBancariaBean(TipoCuentaBancariaBean tipoCuentaBancariaBean)
/*  944:     */   {
/*  945:1189 */     this.tipoCuentaBancariaBean = tipoCuentaBancariaBean;
/*  946:     */   }
/*  947:     */   
/*  948:     */   public List<Pais> getListaPais()
/*  949:     */   {
/*  950:1193 */     if (this.listaPais == null) {
/*  951:1194 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", false, null);
/*  952:     */     }
/*  953:1196 */     return this.listaPais;
/*  954:     */   }
/*  955:     */   
/*  956:     */   public List<Banco> getListaBanco()
/*  957:     */   {
/*  958:1200 */     if (this.listaBanco == null) {
/*  959:1201 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/*  960:     */     }
/*  961:1203 */     return this.listaBanco;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public DataTable getDtCuentaBancarioEmpresa()
/*  965:     */   {
/*  966:1212 */     return this.dtCuentaBancarioEmpresa;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void setDtCuentaBancarioEmpresa(DataTable dtCuentaBancarioEmpresa)
/*  970:     */   {
/*  971:1222 */     this.dtCuentaBancarioEmpresa = dtCuentaBancarioEmpresa;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public TreeNode getSelectedNode()
/*  975:     */   {
/*  976:1231 */     return this.selectedNode;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public void setSelectedNode(TreeNode selectedNode)
/*  980:     */   {
/*  981:1241 */     this.selectedNode = selectedNode;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public String getRecaudadorSeleccionado()
/*  985:     */   {
/*  986:1250 */     this.recaudadorSeleccionado = "";
/*  987:1251 */     if (this.empresa.getCliente().getRecaudador() != null) {
/*  988:1252 */       this.recaudadorSeleccionado = Integer.toString(this.empresa.getCliente().getRecaudador().getId());
/*  989:     */     }
/*  990:1254 */     return this.recaudadorSeleccionado;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setRecaudadorSeleccionado(String tipoRecaudoSeleccionado)
/*  994:     */   {
/*  995:1264 */     if (this.recaudadorSeleccionado != tipoRecaudoSeleccionado)
/*  996:     */     {
/*  997:1265 */       this.recaudadorSeleccionado = tipoRecaudoSeleccionado;
/*  998:     */       
/*  999:1267 */       Recaudador recaudador = null;
/* 1000:1268 */       if (!this.recaudadorSeleccionado.equals(""))
/* 1001:     */       {
/* 1002:1269 */         int idRecaudador = Integer.parseInt(this.recaudadorSeleccionado);
/* 1003:1270 */         recaudador = this.servicioRecaudador.buscarPorId(idRecaudador);
/* 1004:     */       }
/* 1005:1272 */       getEmpresa().getCliente().setRecaudador(recaudador);
/* 1006:     */     }
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public List<Recaudador> getListaRecaudador()
/* 1010:     */   {
/* 1011:1282 */     if (this.listaRecaudador == null) {
/* 1012:1283 */       this.listaRecaudador = this.servicioRecaudador.obtenerListaCombo("nombre", true, null);
/* 1013:     */     }
/* 1014:1285 */     return this.listaRecaudador;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public void setListaRecaudador(List<Recaudador> listaRecaudador)
/* 1018:     */   {
/* 1019:1295 */     this.listaRecaudador = listaRecaudador;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public List<CategoriaRetencion> getListaCategoriaRetencion()
/* 1023:     */   {
/* 1024:1304 */     this.listaCategoriaRetencion = this.servicioCategoriaRetencion.obtenerListaCombo("nombre", true, null);
/* 1025:1305 */     return this.listaCategoriaRetencion;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public void setListaCategoriaRetencion(List<CategoriaRetencion> listaCategoriaRetencion)
/* 1029:     */   {
/* 1030:1315 */     this.listaCategoriaRetencion = listaCategoriaRetencion;
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public DireccionEmpresa getDireccionEmpresa()
/* 1034:     */   {
/* 1035:1324 */     return this.direccionEmpresa;
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 1039:     */   {
/* 1040:1334 */     this.direccionEmpresa = direccionEmpresa;
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public List<TipoCuentaBancaria> getListaTipoCuentaBancaria()
/* 1044:     */   {
/* 1045:1338 */     if (this.listaTipoCuentaBancaria == null) {
/* 1046:1339 */       this.listaTipoCuentaBancaria = this.servicioTipoCuentaBancaria.obtenerListaCombo(null, false, null);
/* 1047:     */     }
/* 1048:1341 */     return this.listaTipoCuentaBancaria;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public boolean isEmpresaExiste()
/* 1052:     */   {
/* 1053:1350 */     return this.empresaExiste;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void setEmpresaExiste(boolean empresaExiste)
/* 1057:     */   {
/* 1058:1360 */     this.empresaExiste = empresaExiste;
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public List<SelectItem> getListaMetodoFacturacion()
/* 1062:     */   {
/* 1063:1368 */     if (this.listaMetodoFacturacion == null)
/* 1064:     */     {
/* 1065:1369 */       this.listaMetodoFacturacion = new ArrayList();
/* 1066:1370 */       for (MetodoFacturacionEnum t : MetodoFacturacionEnum.values())
/* 1067:     */       {
/* 1068:1371 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 1069:1372 */         this.listaMetodoFacturacion.add(item);
/* 1070:     */       }
/* 1071:     */     }
/* 1072:1376 */     return this.listaMetodoFacturacion;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public void setListaMetodoFacturacion(List<SelectItem> listaMetodoFacturacion)
/* 1076:     */   {
/* 1077:1384 */     this.listaMetodoFacturacion = listaMetodoFacturacion;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public List<ListaPrecios> getListaPreciosRecargos()
/* 1081:     */   {
/* 1082:1392 */     if (this.listaPreciosRecargos == null)
/* 1083:     */     {
/* 1084:1393 */       Map<String, String> filters = new HashMap();
/* 1085:1394 */       filters.put("tipoListaPrecios", TipoListaPreciosEnum.RECARGOS.toString());
/* 1086:     */       
/* 1087:1396 */       this.listaPreciosRecargos = this.servicioListaPrecios.obtenerListaCombo("codigo", true, filters);
/* 1088:     */     }
/* 1089:1399 */     return this.listaPreciosRecargos;
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public void setListaPreciosRecargos(List<ListaPrecios> listaPreciosRecargos)
/* 1093:     */   {
/* 1094:1407 */     this.listaPreciosRecargos = listaPreciosRecargos;
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   public RecargoListaPreciosCliente getRecargoListaPreciosClienteSeleccionado()
/* 1098:     */   {
/* 1099:1414 */     return this.recargoListaPreciosClienteSeleccionado;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public void setRecargoListaPreciosClienteSeleccionado(RecargoListaPreciosCliente recargoListaPreciosClienteSeleccionado)
/* 1103:     */   {
/* 1104:1422 */     this.recargoListaPreciosClienteSeleccionado = recargoListaPreciosClienteSeleccionado;
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   public List<TipoEmpresaEnum> getListaTipoCliente()
/* 1108:     */   {
/* 1109:1431 */     if (this.listaTipoCliente == null)
/* 1110:     */     {
/* 1111:1432 */       this.listaTipoCliente = new ArrayList();
/* 1112:1433 */       for (TipoEmpresaEnum tipoCliente : TipoEmpresaEnum.values()) {
/* 1113:1434 */         this.listaTipoCliente.add(tipoCliente);
/* 1114:     */       }
/* 1115:     */     }
/* 1116:1437 */     return this.listaTipoCliente;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public void setListaTipoAgrupacion(List<TipoEmpresaEnum> listaTipoCliente)
/* 1120:     */   {
/* 1121:1447 */     this.listaTipoCliente = listaTipoCliente;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   public DataTable getDtCuentaContable()
/* 1125:     */   {
/* 1126:1454 */     return this.dtCuentaContable;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 1130:     */   {
/* 1131:1462 */     this.dtCuentaContable = dtCuentaContable;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public DataTable getDtSubempresa()
/* 1135:     */   {
/* 1136:1471 */     return this.dtSubempresa;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public void setDtSubempresa(DataTable dtSubempresa)
/* 1140:     */   {
/* 1141:1481 */     this.dtSubempresa = dtSubempresa;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public CuentaContable getCuentaContable()
/* 1145:     */   {
/* 1146:1488 */     return this.cuentaContable;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public void setCuentaContable(CuentaContable cuentaContable)
/* 1150:     */   {
/* 1151:1496 */     this.cuentaContable = cuentaContable;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public SelectItem[] getListaTipoClienteItem()
/* 1155:     */   {
/* 1156:1505 */     if (this.listaTipoClienteItem == null)
/* 1157:     */     {
/* 1158:1507 */       List<SelectItem> lista = new ArrayList();
/* 1159:1508 */       lista.add(new SelectItem("", ""));
/* 1160:1510 */       for (TipoEmpresaEnum t : TipoEmpresaEnum.values())
/* 1161:     */       {
/* 1162:1511 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 1163:1512 */         lista.add(item);
/* 1164:     */       }
/* 1165:1514 */       this.listaTipoClienteItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 1166:     */     }
/* 1167:1517 */     return this.listaTipoClienteItem;
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public DataTable getDtFormaPagoSRI()
/* 1171:     */   {
/* 1172:1521 */     return this.dtFormaPagoSRI;
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public void setDtFormaPagoSRI(DataTable dtFormaPagoSRI)
/* 1176:     */   {
/* 1177:1525 */     this.dtFormaPagoSRI = dtFormaPagoSRI;
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public List<SelectItem> getListaFormaPagoSRI()
/* 1181:     */   {
/* 1182:1529 */     return DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId());
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public List<TipoContacto> getListaTipoContacto()
/* 1186:     */   {
/* 1187:1533 */     if (this.listaTipoContacto == null) {
/* 1188:1534 */       this.listaTipoContacto = this.servicioTipoContacto.obtenerListaCombo("nombre", true, null);
/* 1189:     */     }
/* 1190:1536 */     return this.listaTipoContacto;
/* 1191:     */   }
/* 1192:     */   
/* 1193:     */   public void setListaTipoContacto(List<TipoContacto> listaTipoContacto)
/* 1194:     */   {
/* 1195:1540 */     this.listaTipoContacto = listaTipoContacto;
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public List<Contacto> getListaContacto()
/* 1199:     */   {
/* 1200:1544 */     List<Contacto> lista = new ArrayList();
/* 1201:1545 */     for (Contacto contacto : getEmpresa().getListaContacto()) {
/* 1202:1546 */       if (!contacto.isEliminado()) {
/* 1203:1547 */         lista.add(contacto);
/* 1204:     */       }
/* 1205:     */     }
/* 1206:1551 */     return lista;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public List<Transportista> getListaTransportistaCombo()
/* 1210:     */   {
/* 1211:1555 */     if (this.listaTransportistaCombo == null) {
/* 1212:1556 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("codigo", true, null);
/* 1213:     */     }
/* 1214:1558 */     return this.listaTransportistaCombo;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public List<ListaDescuentos> getListaListaDescuentos()
/* 1218:     */   {
/* 1219:1562 */     HashMap<String, String> filtros = new HashMap();
/* 1220:1563 */     filtros.put("activo", "true");
/* 1221:1564 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 1222:1565 */     if (this.listaListaDescuentos == null) {
/* 1223:1566 */       this.listaListaDescuentos = this.servicioListaDescuentos.obtenerListaCombo("codigo", true, filtros);
/* 1224:     */     }
/* 1225:1568 */     return this.listaListaDescuentos;
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   public List<OrigenIngresos> getListaOrigenIngresos()
/* 1229:     */   {
/* 1230:1572 */     this.listaOrigenIngresos = this.servicioOrigenIngresos.obtenerListaCombo("nombre", true, new HashMap());
/* 1231:1573 */     return this.listaOrigenIngresos;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public void setListaOrigenIngresos(List<OrigenIngresos> listaOrigenIngresos)
/* 1235:     */   {
/* 1236:1577 */     this.listaOrigenIngresos = listaOrigenIngresos;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public String getDireccionCliente()
/* 1240:     */   {
/* 1241:1584 */     return this.direccionCliente;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public void setDireccionCliente(String direccionCliente)
/* 1245:     */   {
/* 1246:1592 */     this.direccionCliente = direccionCliente;
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   public Ciudad getCiudad()
/* 1250:     */   {
/* 1251:1601 */     return this.ciudad;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public void setCiudad(Ciudad ciudad)
/* 1255:     */   {
/* 1256:1611 */     this.ciudad = ciudad;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public List<Ciudad> autocompletarCiudad(String consulta)
/* 1260:     */   {
/* 1261:1615 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public String getTelefonoCliente()
/* 1265:     */   {
/* 1266:1622 */     return this.telefonoCliente;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public void setTelefonoCliente(String telefonoCliente)
/* 1270:     */   {
/* 1271:1630 */     this.telefonoCliente = telefonoCliente;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public void cargarCodigo()
/* 1275:     */   {
/* 1276:1634 */     if ((!isManejaCodigoEmpresa()) || (this.empresa.getCodigo().isEmpty())) {
/* 1277:1635 */       this.empresa.setCodigo(this.empresa.getIdentificacion());
/* 1278:     */     }
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   public boolean isManejaCodigoEmpresa()
/* 1282:     */   {
/* 1283:1640 */     return ParametrosSistema.isManejaCodigoEmpresa(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue();
/* 1284:     */   }
/* 1285:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.EmpresaAgilBean
 * JD-Core Version:    0.7.0.1
 */