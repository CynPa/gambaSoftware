/*    1:     */ package com.asinfo.as2.datosbase.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.controller.TipoCuentaBancariaBean;
/*    4:     */ import com.asinfo.as2.configuracionbase.controller.TipoIdentificacionBean;
/*    5:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrigenIngresos;
/*    6:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioPais;
/*    7:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioParroquia;
/*    8:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*    9:     */ import com.asinfo.as2.controller.LanguageController;
/*   10:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*   11:     */ import com.asinfo.as2.dao.SectorDao;
/*   12:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   13:     */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   14:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   15:     */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   16:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   17:     */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   18:     */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*   19:     */ import com.asinfo.as2.entities.Atributo;
/*   20:     */ import com.asinfo.as2.entities.Banco;
/*   21:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   22:     */ import com.asinfo.as2.entities.CategoriaRetencion;
/*   23:     */ import com.asinfo.as2.entities.Ciudad;
/*   24:     */ import com.asinfo.as2.entities.Cliente;
/*   25:     */ import com.asinfo.as2.entities.CondicionPago;
/*   26:     */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*   27:     */ import com.asinfo.as2.entities.Contacto;
/*   28:     */ import com.asinfo.as2.entities.CuentaBancaria;
/*   29:     */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*   30:     */ import com.asinfo.as2.entities.CuentaContable;
/*   31:     */ import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
/*   32:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   33:     */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   34:     */ import com.asinfo.as2.entities.Empleado;
/*   35:     */ import com.asinfo.as2.entities.Empresa;
/*   36:     */ import com.asinfo.as2.entities.EmpresaAtributo;
/*   37:     */ import com.asinfo.as2.entities.EstadoCivil;
/*   38:     */ import com.asinfo.as2.entities.FormaPago;
/*   39:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   40:     */ import com.asinfo.as2.entities.ListaDescuentos;
/*   41:     */ import com.asinfo.as2.entities.ListaPrecios;
/*   42:     */ import com.asinfo.as2.entities.Organizacion;
/*   43:     */ import com.asinfo.as2.entities.OrigenIngresos;
/*   44:     */ import com.asinfo.as2.entities.Pais;
/*   45:     */ import com.asinfo.as2.entities.Parroquia;
/*   46:     */ import com.asinfo.as2.entities.Proveedor;
/*   47:     */ import com.asinfo.as2.entities.Provincia;
/*   48:     */ import com.asinfo.as2.entities.RecargoListaPreciosCliente;
/*   49:     */ import com.asinfo.as2.entities.Recaudador;
/*   50:     */ import com.asinfo.as2.entities.Sector;
/*   51:     */ import com.asinfo.as2.entities.Subempresa;
/*   52:     */ import com.asinfo.as2.entities.Sucursal;
/*   53:     */ import com.asinfo.as2.entities.TipoContacto;
/*   54:     */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   55:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   56:     */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*   57:     */ import com.asinfo.as2.entities.Transportista;
/*   58:     */ import com.asinfo.as2.entities.Ubicacion;
/*   59:     */ import com.asinfo.as2.entities.Zona;
/*   60:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   61:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   62:     */ import com.asinfo.as2.enumeraciones.Genero;
/*   63:     */ import com.asinfo.as2.enumeraciones.MetodoFacturacionEnum;
/*   64:     */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*   65:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   66:     */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   67:     */ import com.asinfo.as2.enumeraciones.TipoListaPreciosEnum;
/*   68:     */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   69:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   70:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   71:     */ import com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion;
/*   72:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*   73:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*   74:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*   75:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEstadoCivil;
/*   76:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   77:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   78:     */ import com.asinfo.as2.util.AppUtil;
/*   79:     */ import com.asinfo.as2.util.RutaArchivo;
/*   80:     */ import com.asinfo.as2.utils.DatosSRI;
/*   81:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   82:     */ import com.asinfo.as2.utils.JsfUtil;
/*   83:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   84:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*   85:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*   86:     */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*   87:     */ import java.io.PrintStream;
/*   88:     */ import java.math.BigDecimal;
/*   89:     */ import java.util.ArrayList;
/*   90:     */ import java.util.Date;
/*   91:     */ import java.util.HashMap;
/*   92:     */ import java.util.List;
/*   93:     */ import java.util.Map;
/*   94:     */ import javax.annotation.PostConstruct;
/*   95:     */ import javax.ejb.EJB;
/*   96:     */ import javax.faces.bean.ManagedBean;
/*   97:     */ import javax.faces.bean.ManagedProperty;
/*   98:     */ import javax.faces.bean.ViewScoped;
/*   99:     */ import javax.faces.component.html.HtmlSelectBooleanCheckbox;
/*  100:     */ import javax.faces.event.AjaxBehaviorEvent;
/*  101:     */ import javax.faces.model.SelectItem;
/*  102:     */ import org.apache.log4j.Logger;
/*  103:     */ import org.primefaces.component.datatable.DataTable;
/*  104:     */ import org.primefaces.context.RequestContext;
/*  105:     */ import org.primefaces.event.FileUploadEvent;
/*  106:     */ import org.primefaces.event.SelectEvent;
/*  107:     */ import org.primefaces.event.map.PointSelectEvent;
/*  108:     */ import org.primefaces.model.LazyDataModel;
/*  109:     */ import org.primefaces.model.SortOrder;
/*  110:     */ import org.primefaces.model.TreeNode;
/*  111:     */ import org.primefaces.model.map.DefaultMapModel;
/*  112:     */ import org.primefaces.model.map.LatLng;
/*  113:     */ import org.primefaces.model.map.MapModel;
/*  114:     */ import org.primefaces.model.map.Marker;
/*  115:     */ 
/*  116:     */ @ManagedBean
/*  117:     */ @ViewScoped
/*  118:     */ public class EmpresaBean
/*  119:     */   extends PageControllerAS2
/*  120:     */ {
/*  121:     */   private static final long serialVersionUID = -5538312557965585206L;
/*  122:     */   @EJB
/*  123:     */   private transient ServicioEmpresa servicioEmpresa;
/*  124:     */   @EJB
/*  125:     */   private transient ServicioCondicionPago servicioCondicionPago;
/*  126:     */   @EJB
/*  127:     */   private transient ServicioFormaPago servicioFormaPago;
/*  128:     */   @EJB
/*  129:     */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  130:     */   @EJB
/*  131:     */   private transient ServicioListaPrecios servicioListaPrecios;
/*  132:     */   @EJB
/*  133:     */   private transient ServicioListaDescuentos servicioListaDescuentos;
/*  134:     */   @EJB
/*  135:     */   private transient ServicioZona servicioZona;
/*  136:     */   @EJB
/*  137:     */   private transient ServicioUsuario servicioUsuario;
/*  138:     */   @EJB
/*  139:     */   private transient ServicioPais servicioPais;
/*  140:     */   @EJB
/*  141:     */   private transient ServicioGenerico<Banco> servicioBanco;
/*  142:     */   @EJB
/*  143:     */   private transient ServicioRecaudador servicioRecaudador;
/*  144:     */   @EJB
/*  145:     */   private transient ServicioCategoriaRetencion servicioCategoriaRetencion;
/*  146:     */   @EJB
/*  147:     */   private transient ServicioTipoCuentaBancaria servicioTipoCuentaBancaria;
/*  148:     */   @EJB
/*  149:     */   private transient ServicioTipoContacto servicioTipoContacto;
/*  150:     */   @EJB
/*  151:     */   private transient ServicioTransportista servicioTransportista;
/*  152:     */   @EJB
/*  153:     */   private transient ServicioOrigenIngresos servicioOrigenIngresos;
/*  154:     */   @EJB
/*  155:     */   private transient ServicioParroquia servicioParroquia;
/*  156:     */   @EJB
/*  157:     */   private transient ServicioEstadoCivil servicioEstadoCivil;
/*  158:     */   @EJB
/*  159:     */   private transient ServicioGenerico<TipoOrdenDespacho> servicioTipoOrdenDespacho;
/*  160:     */   @EJB
/*  161:     */   private ServicioDetalleDocumentoDigitalizado servicioDetalleDocumentoDigitalizado;
/*  162:     */   @EJB
/*  163:     */   private ServicioConjuntoAtributo servicioConjuntoAtributo;
/*  164:     */   private Empresa empresa;
/*  165:     */   private String agenteComercialSeleccionado;
/*  166:     */   private String recaudadorSeleccionado;
/*  167:     */   private RecargoListaPreciosCliente recargoListaPreciosClienteSeleccionado;
/*  168:     */   private CuentaContable cuentaContable;
/*  169:     */   private List<SelectItem> listaTipoEmpresa;
/*  170:     */   private List<OrigenIngresos> listaOrigenIngresos;
/*  171:     */   private List<SelectItem> listaMetodoFacturacion;
/*  172:     */   private DireccionEmpresa direccionEmpresa;
/*  173:     */   private boolean empresaExiste;
/*  174:     */   private static final String NOMBRE = "nombre";
/*  175:     */   private DetalleDocumentoDigitalizado detalleDocumentoDigitalizado;
/*  176:     */   private List<ConjuntoAtributo> listaConjuntoAtributoCliente;
/*  177:     */   private List<ConjuntoAtributo> listaConjuntoAtributoProveedor;
/*  178:     */   private String centerGeoMap;
/*  179: 200 */   private int zoomMap = 13;
/*  180: 201 */   private String lugarABuscar = "";
/*  181:     */   private List<SelectItem> listaGenero;
/*  182:     */   private List<EstadoCivil> listaEstadoCivil;
/*  183:     */   private LazyDataModel<Empresa> listaEmpresa;
/*  184:     */   private List<ListaPrecios> listaPrecios;
/*  185:     */   private List<ListaDescuentos> listaListaDescuentos;
/*  186:     */   private List<ListaPrecios> listaPreciosRecargos;
/*  187:     */   private List<FormaPago> listaFormaPagoCliente;
/*  188:     */   private List<FormaPago> listaFormaPagoProveedor;
/*  189:     */   private List<CondicionPago> listaCondicionPagoCliente;
/*  190:     */   private List<CondicionPago> listaCondicionPagoProveedor;
/*  191:     */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  192:     */   private List<CategoriaRetencion> listaCategoriaRetencion;
/*  193:     */   private List<Zona> listaZona;
/*  194:     */   private List<Recaudador> listaRecaudador;
/*  195:     */   private List<EntidadUsuario> listaAgentesComerciales;
/*  196:     */   private List<Pais> listaPais;
/*  197:     */   private List<Banco> listaBanco;
/*  198:     */   private List<TipoCuentaBancaria> listaTipoCuentaBancaria;
/*  199:     */   private List<TipoEmpresaEnum> listaTipoCliente;
/*  200:     */   private SelectItem[] listaTipoClienteItem;
/*  201:     */   private List<Transportista> listaTransportistaCombo;
/*  202:     */   private List<TipoContacto> listaTipoContacto;
/*  203:     */   private List<TipoOrdenDespacho> listaTipoOrdenDespacho;
/*  204:     */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizado;
/*  205:     */   private TreeNode selectedNode;
/*  206:     */   private DataTable dtEmpresa;
/*  207:     */   private DataTable dtDirecciones;
/*  208:     */   private DataTable dtAutorizacionProveedorSRI;
/*  209:     */   private DataTable dtCuentaBancarioEmpresa;
/*  210:     */   private DataTable dtCuentaContable;
/*  211:     */   private DataTable dtSubempresa;
/*  212:     */   private DataTable dtFormaPagoSRI;
/*  213:     */   private DataTable dtDetalleDocumentoDigitalizadoEmpresa;
/*  214:     */   private MapModel geoModel;
/*  215:     */   @ManagedProperty("#{tipoIdentificacionBean}")
/*  216:     */   private TipoIdentificacionBean tipoIdentificacionBean;
/*  217:     */   @ManagedProperty("#{tipoCuentaBancariaBean}")
/*  218:     */   private TipoCuentaBancariaBean tipoCuentaBancariaBean;
/*  219:     */   @EJB
/*  220:     */   private SectorDao sectorDao;
/*  221:     */   
/*  222:     */   @PostConstruct
/*  223:     */   public void init()
/*  224:     */   {
/*  225: 260 */     this.listaEmpresa = new LazyDataModel()
/*  226:     */     {
/*  227:     */       private static final long serialVersionUID = 1L;
/*  228:     */       
/*  229:     */       public List<Empresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  230:     */       {
/*  231: 268 */         List<Empresa> lista = new ArrayList();
/*  232:     */         
/*  233: 270 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  234: 271 */         filters.put("indicadorClienteProveedor", "true");
/*  235: 272 */         lista = EmpresaBean.this.servicioEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  236: 273 */         EmpresaBean.this.listaEmpresa.setRowCount(EmpresaBean.this.servicioEmpresa.contarPorCriterio(filters));
/*  237: 274 */         return lista;
/*  238:     */       }
/*  239:     */     };
/*  240:     */   }
/*  241:     */   
/*  242:     */   public String editar(boolean soloLectura)
/*  243:     */   {
/*  244: 286 */     if ((getEmpresa() != null) && (getEmpresa().getId() != 0))
/*  245:     */     {
/*  246: 287 */       this.empresa = this.servicioEmpresa.cargarDetalleTodo(getEmpresa());
/*  247: 288 */       getActivarSubempresa();
/*  248: 289 */       limpiarCamposTipoEmpresa();
/*  249: 290 */       getEmpresa().setSoloLectura(soloLectura);
/*  250:     */       
/*  251: 292 */       this.empresa = this.servicioEmpresa.cargarDetalleAtributos(this.empresa);
/*  252: 293 */       if (this.empresa.isIndicadorCliente()) {
/*  253: 294 */         actualizarAtributosCliente();
/*  254:     */       }
/*  255: 296 */       if (this.empresa.isIndicadorProveedor()) {
/*  256: 297 */         actualizarAtributosProveedor();
/*  257:     */       }
/*  258: 299 */       actualizarMapa(this.empresa);
/*  259: 300 */       setEditado(true);
/*  260:     */     }
/*  261:     */     else
/*  262:     */     {
/*  263: 302 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  264:     */     }
/*  265: 304 */     return "";
/*  266:     */   }
/*  267:     */   
/*  268:     */   public String editar()
/*  269:     */   {
/*  270: 309 */     return editar(false);
/*  271:     */   }
/*  272:     */   
/*  273:     */   public List<Sector> getListaSectores()
/*  274:     */   {
/*  275: 313 */     List<Sector> listaSectors = new ArrayList();
/*  276:     */     
/*  277: 315 */     Zona zona = getEmpresa().getCliente().getZona();
/*  278: 316 */     if (zona != null)
/*  279:     */     {
/*  280: 317 */       HashMap<String, String> filtros = new HashMap();
/*  281: 318 */       filtros.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  282: 319 */       filtros.put("zona.idZona", Integer.toString(zona.getIdZona()));
/*  283:     */       
/*  284: 321 */       listaSectors = this.sectorDao.obtenerListaCombo("nombre", true, filtros);
/*  285:     */     }
/*  286: 325 */     return listaSectors;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public String guardar()
/*  290:     */   {
/*  291:     */     try
/*  292:     */     {
/*  293: 336 */       cargarCodigo();
/*  294: 337 */       boolean banderaFechaCorrecta = true;
/*  295: 338 */       for (DetalleDocumentoDigitalizado detalleDocumento : this.listaDetalleDocumentoDigitalizado) {
/*  296: 339 */         if ((detalleDocumento.getFichero() != null) && 
/*  297: 340 */           (detalleDocumento.getFechaDesde() != null) && (detalleDocumento.getFechaHasta() != null) && 
/*  298: 341 */           (detalleDocumento.getFechaDesde().compareTo(detalleDocumento.getFechaHasta()) == 1) && 
/*  299: 342 */           (detalleDocumento.getDocumentoDigitalizado().isCaduca()))
/*  300:     */         {
/*  301: 343 */           banderaFechaCorrecta = false;
/*  302: 344 */           addErrorMessage(getLanguageController().getMensaje("msg_error_fecha_hasta"));
/*  303:     */         }
/*  304:     */       }
/*  305: 348 */       if (banderaFechaCorrecta) {
/*  306: 350 */         if (!this.empresa.isSoloLectura())
/*  307:     */         {
/*  308: 351 */           if ((this.empresa.isIndicadorProveedor()) || (this.empresa.isIndicadorCliente()))
/*  309:     */           {
/*  310: 352 */             for (AutorizacionProveedorSRI apSRI : getEmpresa().getListaAutorizacionProveedorSRI()) {
/*  311: 353 */               if (apSRI.isIndicadorFacturaElectronica())
/*  312:     */               {
/*  313: 354 */                 apSRI.setAutorizacion("0000000000000000000000000000000000000");
/*  314: 355 */                 apSRI.setFechaDesde(FuncionesUtiles.getFecha(1, 1, 1999));
/*  315: 356 */                 apSRI.setFechaHasta(FuncionesUtiles.getFecha(31, 12, 2999));
/*  316:     */               }
/*  317:     */             }
/*  318: 359 */             this.servicioEmpresa.guardar(getEmpresa());
/*  319: 360 */             for (DetalleDocumentoDigitalizado detalleDocumento : this.listaDetalleDocumentoDigitalizado) {
/*  320: 362 */               if (detalleDocumento.getFichero() != null)
/*  321:     */               {
/*  322: 363 */                 detalleDocumento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  323: 364 */                 detalleDocumento.setIdSucursal(AppUtil.getSucursal().getId());
/*  324: 365 */                 if (detalleDocumento.isEliminado())
/*  325:     */                 {
/*  326: 366 */                   FuncionesUtiles.eliminarArchivo(getDirectorioDescarga(), detalleDocumento.getFichero());
/*  327: 367 */                   detalleDocumento.setFichero(null);
/*  328:     */                 }
/*  329: 370 */                 detalleDocumento.setEmpresa(getEmpresa());
/*  330:     */                 
/*  331: 372 */                 this.servicioDetalleDocumentoDigitalizado.guardar(detalleDocumento);
/*  332:     */               }
/*  333:     */             }
/*  334: 375 */             addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  335: 376 */             cargarDatos();
/*  336: 377 */             setEmpresaExiste(false);
/*  337:     */           }
/*  338:     */           else
/*  339:     */           {
/*  340: 379 */             addErrorMessage(getLanguageController().getMensaje("msg_error_seleccionar_cliente_proveedor"));
/*  341:     */           }
/*  342:     */         }
/*  343:     */         else {
/*  344: 382 */           addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  345:     */         }
/*  346:     */       }
/*  347:     */     }
/*  348:     */     catch (AS2Exception e)
/*  349:     */     {
/*  350: 386 */       JsfUtil.addErrorMessage(e, "");
/*  351: 387 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  352:     */     }
/*  353:     */     catch (ExcepcionAS2Identification e)
/*  354:     */     {
/*  355: 389 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  356: 390 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  357:     */     }
/*  358:     */     catch (ExcepcionAS2 e)
/*  359:     */     {
/*  360: 392 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  361: 393 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  362:     */     }
/*  363:     */     catch (Exception e)
/*  364:     */     {
/*  365: 395 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  366: 396 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  367:     */     }
/*  368: 399 */     return "";
/*  369:     */   }
/*  370:     */   
/*  371:     */   public String eliminar()
/*  372:     */   {
/*  373:     */     try
/*  374:     */     {
/*  375: 411 */       this.servicioEmpresa.eliminar(getEmpresa());
/*  376: 412 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  377: 413 */       cargarDatos();
/*  378:     */     }
/*  379:     */     catch (ExcepcionAS2 e)
/*  380:     */     {
/*  381: 417 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  382:     */     }
/*  383:     */     catch (Exception e)
/*  384:     */     {
/*  385: 421 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  386: 422 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  387:     */     }
/*  388: 425 */     return "";
/*  389:     */   }
/*  390:     */   
/*  391:     */   public String limpiar()
/*  392:     */   {
/*  393: 435 */     crearEmpresa();
/*  394: 436 */     this.listaDetalleDocumentoDigitalizado = null;
/*  395:     */     
/*  396: 438 */     return "";
/*  397:     */   }
/*  398:     */   
/*  399:     */   public void cargarTipoServicio()
/*  400:     */   {
/*  401: 442 */     CuentaBancariaEmpresa cbe = (CuentaBancariaEmpresa)this.dtCuentaBancarioEmpresa.getRowData();
/*  402: 443 */     if (!cbe.getCuentaBancaria().getBanco().getCodigo().equals("30")) {
/*  403: 444 */       cbe.getCuentaBancaria().setTipoServicioCuentaBancariaProveedor(TipoServicioCuentaBancariaEnum.RU);
/*  404:     */     } else {
/*  405: 446 */       cbe.getCuentaBancaria().setTipoServicioCuentaBancariaProveedor(TipoServicioCuentaBancariaEnum.PR);
/*  406:     */     }
/*  407:     */   }
/*  408:     */   
/*  409:     */   public String cargarDatos()
/*  410:     */   {
/*  411:     */     try
/*  412:     */     {
/*  413: 459 */       setEditado(false);
/*  414:     */       
/*  415: 461 */       limpiar();
/*  416:     */     }
/*  417:     */     catch (Exception e)
/*  418:     */     {
/*  419: 464 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  420: 465 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  421:     */     }
/*  422: 467 */     return "";
/*  423:     */   }
/*  424:     */   
/*  425:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  426:     */   {
/*  427: 471 */     List<Empresa> lista = this.servicioEmpresa.autocompletarClientes(consulta);
/*  428:     */     
/*  429: 473 */     return lista;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  433:     */   {
/*  434: 477 */     List<Empresa> lista = this.servicioEmpresa.autocompletarProveedores(consulta);
/*  435:     */     
/*  436: 479 */     return lista;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public List<Empresa> autocompletarSubempresa(String consulta)
/*  440:     */   {
/*  441: 486 */     HashMap<String, String> filters = new HashMap();
/*  442: 487 */     if (getEmpresa().isIndicadorCliente())
/*  443:     */     {
/*  444: 489 */       filters.put("indicadorCliente", "true");
/*  445: 490 */       filters.put("tipoCliente", "!=" + TipoEmpresaEnum.PRINCIPAL);
/*  446:     */     }
/*  447: 492 */     else if (getEmpresa().isIndicadorProveedor())
/*  448:     */     {
/*  449: 494 */       filters.put("indicadorProveedor", "true");
/*  450: 495 */       filters.put("tipoProveedor", "!=" + TipoEmpresaEnum.PRINCIPAL);
/*  451:     */     }
/*  452: 499 */     filters.put("nombreFiscal", consulta.trim());
/*  453: 500 */     filters.put("nombreComercial", consulta.trim());
/*  454: 501 */     filters.put("identificacion", consulta.trim());
/*  455:     */     
/*  456: 503 */     List<Empresa> lista = this.servicioEmpresa.obtenerListaCombo("", false, filters);
/*  457:     */     
/*  458: 505 */     return lista;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void crearCliente(AjaxBehaviorEvent event)
/*  462:     */   {
/*  463: 516 */     boolean indicadorCliente = Boolean.parseBoolean(((HtmlSelectBooleanCheckbox)event.getSource()).getValue().toString());
/*  464: 517 */     this.listaConjuntoAtributoCliente = null;
/*  465: 518 */     this.listaConjuntoAtributoProveedor = null;
/*  466: 519 */     if (indicadorCliente)
/*  467:     */     {
/*  468: 520 */       System.out.println("indicador");
/*  469: 522 */       if (getEmpresa().getCliente() == null)
/*  470:     */       {
/*  471: 523 */         Cliente cliente = new Cliente();
/*  472: 524 */         cliente.setFormaPago(new FormaPago());
/*  473: 525 */         cliente.setCondicionPago(new CondicionPago());
/*  474: 526 */         cliente.setEmpresa(getEmpresa());
/*  475: 527 */         cliente.setListaPrecios(new ListaPrecios());
/*  476: 528 */         cliente.setAgenteComercial(new EntidadUsuario());
/*  477: 529 */         cliente.setZona(new Zona());
/*  478: 530 */         cliente.setNumeroCuotas(1);
/*  479: 531 */         cliente.setTipoCliente(TipoEmpresaEnum.PRINCIPAL);
/*  480: 532 */         cliente.setIndicadorExterior(Boolean.valueOf(false));
/*  481: 533 */         cliente.setIndicadorEmiteRetencion(true);
/*  482: 534 */         this.servicioEmpresa.actualizarCreditoMaximo(getEmpresa());
/*  483: 535 */         getEmpresa().setCliente(cliente);
/*  484: 536 */         actualizarListaDetalleDocumentoDigitalizado();
/*  485:     */       }
/*  486:     */     }
/*  487:     */     else
/*  488:     */     {
/*  489: 539 */       System.out.println("false");
/*  490: 540 */       this.empresa.setConjuntoAtributoCliente(null);
/*  491: 541 */       if (getEmpresa().getCliente() == null) {}
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   public void crearProveedor(AjaxBehaviorEvent event)
/*  496:     */   {
/*  497: 554 */     boolean indicadorProveedor = Boolean.parseBoolean(((HtmlSelectBooleanCheckbox)event.getSource()).getValue().toString());
/*  498: 555 */     this.listaConjuntoAtributoCliente = null;
/*  499: 556 */     this.listaConjuntoAtributoProveedor = null;
/*  500: 557 */     if (indicadorProveedor)
/*  501:     */     {
/*  502: 558 */       if (getEmpresa().getProveedor() == null)
/*  503:     */       {
/*  504: 559 */         Proveedor proveedor = new Proveedor();
/*  505: 560 */         proveedor.setFormaPago(new FormaPago());
/*  506: 561 */         proveedor.setCondicionPago(new CondicionPago());
/*  507: 562 */         proveedor.setCategoriaRetencion(new CategoriaRetencion());
/*  508: 563 */         proveedor.setEmpresa(getEmpresa());
/*  509: 564 */         getEmpresa().setProveedor(proveedor);
/*  510: 565 */         actualizarListaDetalleDocumentoDigitalizado();
/*  511:     */       }
/*  512:     */     }
/*  513:     */     else
/*  514:     */     {
/*  515: 568 */       this.empresa.setConjuntoAtributoProveedor(null);
/*  516: 569 */       if (getEmpresa().getProveedor() == null) {}
/*  517:     */     }
/*  518:     */   }
/*  519:     */   
/*  520:     */   public String agregarDireccion()
/*  521:     */   {
/*  522: 582 */     DireccionEmpresa direccion = new DireccionEmpresa();
/*  523: 583 */     direccion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  524:     */     
/*  525: 585 */     Ubicacion ubicacion = new Ubicacion();
/*  526: 586 */     ubicacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  527: 587 */     ubicacion.setDireccion5(" ");
/*  528: 588 */     direccion.setUbicacion(ubicacion);
/*  529: 589 */     direccion.setEmpresa(getEmpresa());
/*  530: 590 */     getEmpresa().getDirecciones().add(direccion);
/*  531:     */     
/*  532: 592 */     return "";
/*  533:     */   }
/*  534:     */   
/*  535:     */   public String eliminarDireccion()
/*  536:     */   {
/*  537: 602 */     DireccionEmpresa direccionEmpresa = (DireccionEmpresa)this.dtDirecciones.getRowData();
/*  538: 603 */     direccionEmpresa.setEliminado(true);
/*  539: 604 */     direccionEmpresa.getUbicacion().setEliminado(true);
/*  540:     */     
/*  541: 606 */     return "";
/*  542:     */   }
/*  543:     */   
/*  544:     */   public String eliminarCuentaBancariaEmpresa()
/*  545:     */   {
/*  546: 610 */     CuentaBancariaEmpresa cuentaBancariaEmpresa = (CuentaBancariaEmpresa)this.dtCuentaBancarioEmpresa.getRowData();
/*  547: 611 */     cuentaBancariaEmpresa.setEliminado(true);
/*  548: 612 */     cuentaBancariaEmpresa.getCuentaBancaria().setEliminado(true);
/*  549:     */     
/*  550: 614 */     return "";
/*  551:     */   }
/*  552:     */   
/*  553:     */   public String agregarAutorizacionSRI()
/*  554:     */   {
/*  555: 624 */     AutorizacionProveedorSRI autorizacionProveedorSRI = new AutorizacionProveedorSRI();
/*  556: 625 */     autorizacionProveedorSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  557: 626 */     autorizacionProveedorSRI.setEmpresa(getEmpresa());
/*  558: 627 */     autorizacionProveedorSRI.setActivo(true);
/*  559:     */     
/*  560: 629 */     getEmpresa().getListaAutorizacionProveedorSRI().add(autorizacionProveedorSRI);
/*  561:     */     
/*  562: 631 */     return "";
/*  563:     */   }
/*  564:     */   
/*  565:     */   public void agregarRecargoListener()
/*  566:     */   {
/*  567: 638 */     RecargoListaPreciosCliente recargo = new RecargoListaPreciosCliente(this.empresa);
/*  568: 639 */     this.empresa.getListaRecargoListaPreciosCliente().add(recargo);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void cargarCuentaContable(SelectEvent event)
/*  572:     */   {
/*  573: 646 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/*  574: 647 */     this.recargoListaPreciosClienteSeleccionado.setCuentaContable(cuentaContable);
/*  575:     */   }
/*  576:     */   
/*  577:     */   public String eliminarAutorizacionSRI()
/*  578:     */   {
/*  579: 657 */     AutorizacionProveedorSRI autorizacionProveedorSRI = (AutorizacionProveedorSRI)this.dtAutorizacionProveedorSRI.getRowData();
/*  580: 658 */     autorizacionProveedorSRI.setEliminado(true);
/*  581:     */     
/*  582: 660 */     return "";
/*  583:     */   }
/*  584:     */   
/*  585:     */   public void agregarContactoListener()
/*  586:     */   {
/*  587: 664 */     Contacto contacto = new Contacto();
/*  588: 665 */     contacto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  589: 666 */     contacto.setEmpresa(getEmpresa());
/*  590: 667 */     getEmpresa().getListaContacto().add(contacto);
/*  591:     */   }
/*  592:     */   
/*  593:     */   public String eliminarContacto(Contacto contacto)
/*  594:     */   {
/*  595: 677 */     contacto.setEliminado(true);
/*  596: 678 */     return "";
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void processUpload(FileUploadEvent event)
/*  600:     */   {
/*  601:     */     try
/*  602:     */     {
/*  603: 690 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "cliente_proveedor");
/*  604: 691 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), this.detalleDocumentoDigitalizado
/*  605: 692 */         .getDocumentoDigitalizado().getNombre() + "-" + this.empresa.getNombreFiscal(), uploadDir);
/*  606:     */       
/*  607: 694 */       this.detalleDocumentoDigitalizado.setFichero(fileName);
/*  608: 695 */       this.detalleDocumentoDigitalizado.setEliminado(false);
/*  609:     */       
/*  610: 697 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  611:     */     }
/*  612:     */     catch (Exception e)
/*  613:     */     {
/*  614: 700 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/*  615: 701 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  616:     */     }
/*  617:     */   }
/*  618:     */   
/*  619:     */   public String eliminarArchivo()
/*  620:     */   {
/*  621: 707 */     this.detalleDocumentoDigitalizado.setEliminado(true);
/*  622: 708 */     return null;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public String getDirectorioDescarga()
/*  626:     */   {
/*  627: 713 */     return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "cliente_proveedor");
/*  628:     */   }
/*  629:     */   
/*  630:     */   public String getNombreArchivo()
/*  631:     */   {
/*  632: 718 */     return this.detalleDocumentoDigitalizado.getFichero();
/*  633:     */   }
/*  634:     */   
/*  635:     */   public String recuperaExtencion(String nombreArchivo)
/*  636:     */   {
/*  637: 729 */     int mid = nombreArchivo.lastIndexOf('.');
/*  638: 730 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/*  639:     */   }
/*  640:     */   
/*  641:     */   public ServicioEmpresa getServicioEmpresa()
/*  642:     */   {
/*  643: 739 */     return this.servicioEmpresa;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setServicioEmpresa(ServicioEmpresa servicioEmpresa)
/*  647:     */   {
/*  648: 749 */     this.servicioEmpresa = servicioEmpresa;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public Empresa getEmpresa()
/*  652:     */   {
/*  653: 758 */     return this.empresa;
/*  654:     */   }
/*  655:     */   
/*  656:     */   private void crearEmpresa()
/*  657:     */   {
/*  658: 765 */     this.empresa = new Empresa();
/*  659: 766 */     this.empresa.setCategoriaEmpresa(new CategoriaEmpresa());
/*  660: 767 */     this.empresa.setTipoIdentificacion(new TipoIdentificacion());
/*  661: 768 */     this.empresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  662: 769 */     this.empresa.setIdSucursal(AppUtil.getSucursal().getId());
/*  663:     */   }
/*  664:     */   
/*  665:     */   public void agregarCuentaBancariaEmpresa()
/*  666:     */   {
/*  667: 775 */     CuentaBancaria cuentaBancaria = new CuentaBancaria();
/*  668: 776 */     cuentaBancaria.setPais(new Pais());
/*  669: 777 */     cuentaBancaria.setBanco(new Banco());
/*  670: 778 */     cuentaBancaria.setContacto("N/A");
/*  671: 779 */     cuentaBancaria.setTelefonoContacto("999999");
/*  672: 780 */     cuentaBancaria.setDescripcion("N/A");
/*  673: 781 */     cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria());
/*  674: 782 */     cuentaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  675: 783 */     cuentaBancaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  676: 784 */     cuentaBancaria.setPredeterminado(true);
/*  677:     */     
/*  678: 786 */     CuentaBancariaEmpresa cuentaBancariaEmpresa = new CuentaBancariaEmpresa();
/*  679: 787 */     cuentaBancariaEmpresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  680: 788 */     cuentaBancariaEmpresa.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getId()));
/*  681: 789 */     cuentaBancariaEmpresa.setDescripcion("N/A");
/*  682:     */     
/*  683: 791 */     cuentaBancariaEmpresa.setCuentaBancaria(cuentaBancaria);
/*  684: 792 */     cuentaBancariaEmpresa.setEmpresa(this.empresa);
/*  685:     */     
/*  686: 794 */     getEmpresa().getListaCuentaBancariaEmpresa().add(cuentaBancariaEmpresa);
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void actualizarCiudad()
/*  690:     */   {
/*  691: 799 */     this.direccionEmpresa = ((DireccionEmpresa)this.dtDirecciones.getRowData());
/*  692:     */   }
/*  693:     */   
/*  694:     */   public void cargarCiudad()
/*  695:     */   {
/*  696:     */     try
/*  697:     */     {
/*  698: 804 */       Ciudad ciudad = (Ciudad)this.selectedNode.getData();
/*  699: 805 */       this.direccionEmpresa.setCiudad(ciudad);
/*  700:     */       
/*  701:     */ 
/*  702: 808 */       this.direccionEmpresa.getCiudad().setListaParroquia(listaParroquiasPorCiudad(this.direccionEmpresa.getCiudad()));
/*  703:     */     }
/*  704:     */     catch (Exception e)
/*  705:     */     {
/*  706: 811 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  707:     */     }
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void buscarEmpresaEvent()
/*  711:     */   {
/*  712: 818 */     if (!ParametrosSistema.getRecargosEnFactura(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  713:     */       try
/*  714:     */       {
/*  715: 820 */         Empresa empresa = this.servicioEmpresa.buscarEmpresaPorIdentificacion(getEmpresa().getIdentificacion());
/*  716: 821 */         empresa = this.servicioEmpresa.cargarDetalleTodo(empresa);
/*  717: 822 */         empresa = this.servicioEmpresa.cargarDetalleAtributos(empresa);
/*  718: 824 */         if (empresa.getEmpleado() != null)
/*  719:     */         {
/*  720: 825 */           empresa.setEstadoCivil(empresa.getEmpleado().getEstadoCivil());
/*  721: 826 */           empresa.setGenero(empresa.getEmpleado().getGenero());
/*  722:     */         }
/*  723: 828 */         setEmpresaExiste(true);
/*  724: 829 */         setEmpresa(empresa);
/*  725: 830 */         RequestContext.getCurrentInstance().update("panelNuevo");
/*  726:     */       }
/*  727:     */       catch (ExcepcionAS2 e)
/*  728:     */       {
/*  729: 832 */         setEmpresaExiste(false);
/*  730: 833 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  731:     */       }
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void buscarEmpresaCodigoEvent()
/*  736:     */   {
/*  737:     */     try
/*  738:     */     {
/*  739: 840 */       if (this.servicioEmpresa.verificarCodigoEmpresa(getEmpresa())) {
/*  740: 841 */         throw new ExcepcionAS2("msg_info_existe_codigo_empresa", ": " + getEmpresa().getCodigo());
/*  741:     */       }
/*  742:     */     }
/*  743:     */     catch (ExcepcionAS2 e)
/*  744:     */     {
/*  745: 844 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  746:     */     }
/*  747:     */   }
/*  748:     */   
/*  749:     */   public void listarCuentaContable()
/*  750:     */   {
/*  751: 850 */     Map<String, Object> properties = new HashMap();
/*  752: 851 */     properties.put("modal", Boolean.valueOf(true));
/*  753: 852 */     properties.put("resizable", Boolean.valueOf(true));
/*  754: 853 */     properties.put("draggable", Boolean.valueOf(true));
/*  755: 854 */     properties.put("width", Integer.valueOf(850));
/*  756: 855 */     properties.put("height", Integer.valueOf(400));
/*  757: 856 */     RequestContext.getCurrentInstance().openDialog("/resources/componentes/seleccionarCuentaContable");
/*  758:     */   }
/*  759:     */   
/*  760:     */   public String cancelar()
/*  761:     */   {
/*  762: 861 */     setEditado(false);
/*  763: 862 */     setEmpresaExiste(false);
/*  764: 863 */     limpiar();
/*  765: 864 */     return "";
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void setEmpresa(Empresa empresa)
/*  769:     */   {
/*  770: 874 */     this.empresa = empresa;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public LazyDataModel<Empresa> getListaEmpresa()
/*  774:     */   {
/*  775: 883 */     return this.listaEmpresa;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void setListaEmpresa(LazyDataModel<Empresa> listaEmpresa)
/*  779:     */   {
/*  780: 893 */     this.listaEmpresa = listaEmpresa;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public DataTable getDtEmpresa()
/*  784:     */   {
/*  785: 902 */     return this.dtEmpresa;
/*  786:     */   }
/*  787:     */   
/*  788:     */   public void setDtEmpresa(DataTable dtEmpresa)
/*  789:     */   {
/*  790: 912 */     this.dtEmpresa = dtEmpresa;
/*  791:     */   }
/*  792:     */   
/*  793:     */   public TipoIdentificacionBean getTipoIdentificacionBean()
/*  794:     */   {
/*  795: 921 */     return this.tipoIdentificacionBean;
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void setTipoIdentificacionBean(TipoIdentificacionBean tipoIdentificacionBean)
/*  799:     */   {
/*  800: 931 */     this.tipoIdentificacionBean = tipoIdentificacionBean;
/*  801:     */   }
/*  802:     */   
/*  803:     */   public List<SelectItem> getListaTipoEmpresa()
/*  804:     */   {
/*  805: 941 */     if (this.listaTipoEmpresa == null)
/*  806:     */     {
/*  807: 942 */       this.listaTipoEmpresa = new ArrayList();
/*  808: 943 */       for (TipoEmpresa t : TipoEmpresa.values())
/*  809:     */       {
/*  810: 944 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  811: 945 */         this.listaTipoEmpresa.add(item);
/*  812:     */       }
/*  813:     */     }
/*  814: 949 */     return this.listaTipoEmpresa;
/*  815:     */   }
/*  816:     */   
/*  817:     */   public DataTable getDtDirecciones()
/*  818:     */   {
/*  819: 958 */     return this.dtDirecciones;
/*  820:     */   }
/*  821:     */   
/*  822:     */   public void setDtDirecciones(DataTable dtDirecciones)
/*  823:     */   {
/*  824: 968 */     this.dtDirecciones = dtDirecciones;
/*  825:     */   }
/*  826:     */   
/*  827:     */   public List<DireccionEmpresa> getDirecciones()
/*  828:     */   {
/*  829: 978 */     List<DireccionEmpresa> direcciones = new ArrayList();
/*  830: 979 */     for (DireccionEmpresa de : getEmpresa().getDirecciones()) {
/*  831: 981 */       if (!de.isEliminado()) {
/*  832: 982 */         direcciones.add(de);
/*  833:     */       }
/*  834:     */     }
/*  835: 986 */     return direcciones;
/*  836:     */   }
/*  837:     */   
/*  838:     */   public List<CuentaBancariaEmpresa> getCuentaBancariaEmpresa()
/*  839:     */   {
/*  840: 991 */     List<CuentaBancariaEmpresa> cuentaBancariaEmpresa = new ArrayList();
/*  841: 992 */     for (CuentaBancariaEmpresa cbe : this.empresa.getListaCuentaBancariaEmpresa()) {
/*  842: 993 */       if (!cbe.isEliminado()) {
/*  843: 994 */         cuentaBancariaEmpresa.add(cbe);
/*  844:     */       }
/*  845:     */     }
/*  846: 997 */     return cuentaBancariaEmpresa;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public List<Subempresa> getListaSubempresa()
/*  850:     */   {
/*  851:1001 */     List<Subempresa> lista = new ArrayList();
/*  852:1002 */     for (Subempresa subempresa : getEmpresa().getListaSubempresa()) {
/*  853:1003 */       if (!subempresa.isEliminado()) {
/*  854:1004 */         lista.add(subempresa);
/*  855:     */       }
/*  856:     */     }
/*  857:1007 */     return lista;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public List<FormaPagoSRI> getListaFormaPagoSRIProveedor()
/*  861:     */   {
/*  862:1011 */     List<FormaPagoSRI> lista = new ArrayList();
/*  863:1012 */     for (FormaPagoSRI formaPagoSRI : this.empresa.getListaFormaPagoSRI()) {
/*  864:1013 */       if (!formaPagoSRI.isEliminado()) {
/*  865:1014 */         lista.add(formaPagoSRI);
/*  866:     */       }
/*  867:     */     }
/*  868:1017 */     return lista;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public String agregarSubempresa()
/*  872:     */   {
/*  873:1021 */     Subempresa subempresa = new Subempresa();
/*  874:1022 */     subempresa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  875:1023 */     subempresa.setIdSucursal(AppUtil.getSucursal().getId());
/*  876:1024 */     subempresa.setEmpresaPadre(getEmpresa());
/*  877:1025 */     getEmpresa().getListaSubempresa().add(subempresa);
/*  878:1026 */     return "";
/*  879:     */   }
/*  880:     */   
/*  881:     */   public String agregarFormaPagoSRI()
/*  882:     */   {
/*  883:1030 */     FormaPagoSRI formaPagoSRI = new FormaPagoSRI();
/*  884:1031 */     formaPagoSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  885:1032 */     formaPagoSRI.setIdSucursal(AppUtil.getSucursal().getId());
/*  886:1033 */     formaPagoSRI.setEmpresa(this.empresa);
/*  887:1034 */     getEmpresa().getListaFormaPagoSRI().add(formaPagoSRI);
/*  888:1035 */     return "";
/*  889:     */   }
/*  890:     */   
/*  891:     */   public String eliminarSubempresa()
/*  892:     */   {
/*  893:1039 */     Subempresa subempresa = (Subempresa)this.dtSubempresa.getRowData();
/*  894:1040 */     subempresa.setEliminado(true);
/*  895:1041 */     return "";
/*  896:     */   }
/*  897:     */   
/*  898:     */   public String eliminarFormaPagoSRI()
/*  899:     */   {
/*  900:1045 */     FormaPagoSRI formaPagoSRI = (FormaPagoSRI)this.dtFormaPagoSRI.getRowData();
/*  901:1046 */     formaPagoSRI.setEliminado(true);
/*  902:1047 */     return "";
/*  903:     */   }
/*  904:     */   
/*  905:     */   public boolean getActivarSubempresa()
/*  906:     */   {
/*  907:1051 */     boolean activador = false;
/*  908:1054 */     if ((getEmpresa().getCliente() != null) && (getEmpresa().getCliente().getTipoCliente() != null) && 
/*  909:1055 */       (getEmpresa().isIndicadorCliente()) && (!getEmpresa().getCliente().getTipoCliente().equals(TipoEmpresaEnum.SUBCLIENTE))) {
/*  910:1056 */       activador = true;
/*  911:     */     }
/*  912:1060 */     if ((getEmpresa().getProveedor() != null) && (getEmpresa().getProveedor().getTipoProveedor() != null) && 
/*  913:1061 */       (getEmpresa().isIndicadorProveedor()) && (!getEmpresa().getProveedor().getTipoProveedor().equals(TipoEmpresaEnum.SUBCLIENTE))) {
/*  914:1062 */       activador = true;
/*  915:     */     }
/*  916:1067 */     return activador;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public void agregarMarker(PointSelectEvent event)
/*  920:     */   {
/*  921:1071 */     LatLng latlng = event.getLatLng();
/*  922:1072 */     Marker marker = new Marker(latlng, "");
/*  923:1073 */     getGeoModel().addOverlay(marker);
/*  924:1074 */     actualizarGoeModel(latlng);
/*  925:1075 */     this.empresa.setLatitud(FuncionesUtiles.redondearBigDecimal(new BigDecimal(latlng.getLat()), 6));
/*  926:1076 */     this.empresa.setLongitud(FuncionesUtiles.redondearBigDecimal(new BigDecimal(latlng.getLng()), 6));
/*  927:1077 */     setZoomMap(18);
/*  928:     */   }
/*  929:     */   
/*  930:     */   private void actualizarGoeModel(LatLng latLng)
/*  931:     */   {
/*  932:1081 */     for (Marker m : getGeoModel().getMarkers()) {
/*  933:1082 */       if ((m.getLatlng().getLat() != latLng.getLat()) && (m.getLatlng().getLng() != latLng.getLng())) {
/*  934:1083 */         m.setVisible(false);
/*  935:     */       }
/*  936:     */     }
/*  937:     */   }
/*  938:     */   
/*  939:     */   public void actualizarMapa(Empresa empresa)
/*  940:     */   {
/*  941:1088 */     this.empresa = empresa;
/*  942:1089 */     if ((empresa != null) && (empresa.getLongitud() != null))
/*  943:     */     {
/*  944:1090 */       LatLng latlng = new LatLng(empresa.getLatitud().doubleValue(), empresa.getLongitud().doubleValue());
/*  945:1091 */       Marker marker = new Marker(latlng, "");
/*  946:1092 */       getGeoModel().addOverlay(marker);
/*  947:1093 */       actualizarGoeModel(latlng);
/*  948:1094 */       setZoomMap(16);
/*  949:     */     }
/*  950:     */     else
/*  951:     */     {
/*  952:1096 */       setZoomMap(13);
/*  953:     */     }
/*  954:     */   }
/*  955:     */   
/*  956:     */   public void setDirecciones(List<DireccionEmpresa> direcciones)
/*  957:     */   {
/*  958:1107 */     getEmpresa().setDirecciones(direcciones);
/*  959:     */   }
/*  960:     */   
/*  961:     */   public List<AutorizacionProveedorSRI> getListaAutorizacionProveedorSRI()
/*  962:     */   {
/*  963:1117 */     List<AutorizacionProveedorSRI> lista = new ArrayList();
/*  964:1118 */     for (AutorizacionProveedorSRI a : getEmpresa().getListaAutorizacionProveedorSRI()) {
/*  965:1120 */       if (!a.isEliminado()) {
/*  966:1121 */         lista.add(a);
/*  967:     */       }
/*  968:     */     }
/*  969:1125 */     return lista;
/*  970:     */   }
/*  971:     */   
/*  972:     */   public List<RecargoListaPreciosCliente> getListaRecargoListaPreciosCliente()
/*  973:     */   {
/*  974:1130 */     List<RecargoListaPreciosCliente> lista = new ArrayList();
/*  975:1132 */     for (RecargoListaPreciosCliente recargo : this.empresa.getListaRecargoListaPreciosCliente()) {
/*  976:1133 */       if ((!recargo.isEliminado()) || (!recargo.getListaPrecios().isIndicadorCompra())) {
/*  977:1134 */         lista.add(recargo);
/*  978:     */       }
/*  979:     */     }
/*  980:1138 */     return lista;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public DataTable getDtAutorizacionProveedorSRI()
/*  984:     */   {
/*  985:1147 */     return this.dtAutorizacionProveedorSRI;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public void setDtAutorizacionProveedorSRI(DataTable dtAutorizacionProveedorSRI)
/*  989:     */   {
/*  990:1157 */     this.dtAutorizacionProveedorSRI = dtAutorizacionProveedorSRI;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public List<ListaPrecios> getListaPrecios()
/*  994:     */   {
/*  995:1161 */     if (this.listaPrecios == null)
/*  996:     */     {
/*  997:1162 */       Map<String, String> filters = new HashMap();
/*  998:1163 */       filters.put("tipoListaPrecios", TipoListaPreciosEnum.LISTA_PRECIOS.toString());
/*  999:1164 */       filters.put("indicadorVenta", "true");
/* 1000:1165 */       filters.put("activo", "true");
/* 1001:1166 */       this.listaPrecios = this.servicioListaPrecios.obtenerListaCombo("nombre", true, filters);
/* 1002:     */     }
/* 1003:1169 */     return this.listaPrecios;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public List<ListaPrecios> getListaPreciosProveedor()
/* 1007:     */   {
/* 1008:1173 */     List<ListaPrecios> lista = new ArrayList();
/* 1009:1174 */     Map<String, String> filters = new HashMap();
/* 1010:1175 */     filters.put("tipoListaPrecios", TipoListaPreciosEnum.LISTA_PRECIOS.toString());
/* 1011:1176 */     filters.put("indicadorCompra", "true");
/* 1012:1177 */     filters.put("activo", "true");
/* 1013:1178 */     lista = this.servicioListaPrecios.obtenerListaCombo("nombre", true, filters);
/* 1014:     */     
/* 1015:1180 */     return lista;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public void setListaPrecios(List<ListaPrecios> listaPrecios)
/* 1019:     */   {
/* 1020:1184 */     this.listaPrecios = listaPrecios;
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public String actualizarGenero()
/* 1024:     */   {
/* 1025:1188 */     return "";
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 1029:     */   {
/* 1030:1197 */     if (this.listaCategoriaEmpresa == null) {
/* 1031:1198 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/* 1032:     */     }
/* 1033:1200 */     return this.listaCategoriaEmpresa;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 1037:     */   {
/* 1038:1210 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public List<FormaPago> getListaFormaPagoCliente()
/* 1042:     */   {
/* 1043:1219 */     if (this.listaFormaPagoCliente == null) {
/* 1044:1220 */       this.listaFormaPagoCliente = this.servicioFormaPago.obtenerListaCombo("codigo", true, null);
/* 1045:     */     }
/* 1046:1222 */     return this.listaFormaPagoCliente;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public void setListaFormaPagoCliente(List<FormaPago> listaFormaPagoCliente)
/* 1050:     */   {
/* 1051:1232 */     this.listaFormaPagoCliente = listaFormaPagoCliente;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public List<FormaPago> getListaFormaPagoProveedor()
/* 1055:     */   {
/* 1056:1241 */     if (this.listaFormaPagoProveedor == null) {
/* 1057:1242 */       this.listaFormaPagoProveedor = this.servicioFormaPago.obtenerListaCombo("codigo", true, null);
/* 1058:     */     }
/* 1059:1244 */     return this.listaFormaPagoProveedor;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public void setListaFormaPagoProveedor(List<FormaPago> listaFormaPagoProveedor)
/* 1063:     */   {
/* 1064:1254 */     this.listaFormaPagoProveedor = listaFormaPagoProveedor;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public List<CondicionPago> getListaCondicionPagoCliente()
/* 1068:     */   {
/* 1069:1263 */     if (this.listaCondicionPagoCliente == null) {
/* 1070:1264 */       this.listaCondicionPagoCliente = this.servicioCondicionPago.obtenerListaCombo("nombre", true, null);
/* 1071:     */     }
/* 1072:1266 */     return this.listaCondicionPagoCliente;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public void setListaCondicionPagoCliente(List<CondicionPago> listaCondicionPagoCliente)
/* 1076:     */   {
/* 1077:1276 */     this.listaCondicionPagoCliente = listaCondicionPagoCliente;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public List<CondicionPago> getListaCondicionPagoProveedor()
/* 1081:     */   {
/* 1082:1285 */     if (this.listaCondicionPagoProveedor == null) {
/* 1083:1286 */       this.listaCondicionPagoProveedor = this.servicioCondicionPago.obtenerListaCombo("nombre", true, null);
/* 1084:     */     }
/* 1085:1288 */     return this.listaCondicionPagoProveedor;
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public void setListaCondicionPagoProveedor(List<CondicionPago> listaCondicionPagoProveedor)
/* 1089:     */   {
/* 1090:1298 */     this.listaCondicionPagoProveedor = listaCondicionPagoProveedor;
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public List<Zona> getListaZona()
/* 1094:     */   {
/* 1095:1307 */     if (this.listaZona == null) {
/* 1096:1308 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 1097:     */     }
/* 1098:1310 */     return this.listaZona;
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public void setListaZona(List<Zona> listaZona)
/* 1102:     */   {
/* 1103:1320 */     this.listaZona = listaZona;
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public List<EntidadUsuario> getListaAgentesComerciales()
/* 1107:     */   {
/* 1108:1324 */     if (this.listaAgentesComerciales == null) {
/* 1109:1325 */       this.listaAgentesComerciales = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId(), Boolean.valueOf(true));
/* 1110:     */     }
/* 1111:1327 */     return this.listaAgentesComerciales;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public String getAgenteComercialSeleccionado()
/* 1115:     */   {
/* 1116:1331 */     this.agenteComercialSeleccionado = "";
/* 1117:1332 */     if (this.empresa.getCliente().getAgenteComercial() != null) {
/* 1118:1333 */       this.agenteComercialSeleccionado = Integer.toString(this.empresa.getCliente().getAgenteComercial().getId());
/* 1119:     */     }
/* 1120:1335 */     return this.agenteComercialSeleccionado;
/* 1121:     */   }
/* 1122:     */   
/* 1123:     */   public void setAgenteComercialSeleccionado(String AgenteComercialSeleccionado)
/* 1124:     */   {
/* 1125:1339 */     if (this.agenteComercialSeleccionado != AgenteComercialSeleccionado)
/* 1126:     */     {
/* 1127:1341 */       this.agenteComercialSeleccionado = AgenteComercialSeleccionado;
/* 1128:     */       
/* 1129:1343 */       EntidadUsuario agenteComercial = null;
/* 1130:1344 */       if (!this.agenteComercialSeleccionado.isEmpty())
/* 1131:     */       {
/* 1132:1345 */         int idAgenteComercial = Integer.parseInt(this.agenteComercialSeleccionado);
/* 1133:1346 */         agenteComercial = this.servicioUsuario.buscarPorId(Integer.valueOf(idAgenteComercial));
/* 1134:     */       }
/* 1135:1348 */       getEmpresa().getCliente().setAgenteComercial(agenteComercial);
/* 1136:     */     }
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public TipoCuentaBancariaBean getTipoCuentaBancariaBean()
/* 1140:     */   {
/* 1141:1359 */     return this.tipoCuentaBancariaBean;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public void setTipoCuentaBancariaBean(TipoCuentaBancariaBean tipoCuentaBancariaBean)
/* 1145:     */   {
/* 1146:1369 */     this.tipoCuentaBancariaBean = tipoCuentaBancariaBean;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public List<Pais> getListaPais()
/* 1150:     */   {
/* 1151:1373 */     if (this.listaPais == null) {
/* 1152:1374 */       this.listaPais = this.servicioPais.obtenerListaCombo("nombre", false, null);
/* 1153:     */     }
/* 1154:1376 */     return this.listaPais;
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   public List<Banco> getListaBanco()
/* 1158:     */   {
/* 1159:1380 */     if (this.listaBanco == null) {
/* 1160:1381 */       this.listaBanco = this.servicioBanco.obtenerListaCombo(Banco.class, "nombre", true, null);
/* 1161:     */     }
/* 1162:1383 */     return this.listaBanco;
/* 1163:     */   }
/* 1164:     */   
/* 1165:     */   public DataTable getDtCuentaBancarioEmpresa()
/* 1166:     */   {
/* 1167:1392 */     return this.dtCuentaBancarioEmpresa;
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public void setDtCuentaBancarioEmpresa(DataTable dtCuentaBancarioEmpresa)
/* 1171:     */   {
/* 1172:1402 */     this.dtCuentaBancarioEmpresa = dtCuentaBancarioEmpresa;
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public TreeNode getSelectedNode()
/* 1176:     */   {
/* 1177:1411 */     return this.selectedNode;
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public void setSelectedNode(TreeNode selectedNode)
/* 1181:     */   {
/* 1182:1421 */     this.selectedNode = selectedNode;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public String getRecaudadorSeleccionado()
/* 1186:     */   {
/* 1187:1430 */     this.recaudadorSeleccionado = "";
/* 1188:1431 */     if (this.empresa.getCliente().getRecaudador() != null) {
/* 1189:1432 */       this.recaudadorSeleccionado = Integer.toString(this.empresa.getCliente().getRecaudador().getId());
/* 1190:     */     }
/* 1191:1434 */     return this.recaudadorSeleccionado;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public void setRecaudadorSeleccionado(String tipoRecaudoSeleccionado)
/* 1195:     */   {
/* 1196:1444 */     if (this.recaudadorSeleccionado != tipoRecaudoSeleccionado)
/* 1197:     */     {
/* 1198:1445 */       this.recaudadorSeleccionado = tipoRecaudoSeleccionado;
/* 1199:     */       
/* 1200:1447 */       Recaudador recaudador = null;
/* 1201:1448 */       if (!this.recaudadorSeleccionado.equals(""))
/* 1202:     */       {
/* 1203:1449 */         int idRecaudador = Integer.parseInt(this.recaudadorSeleccionado);
/* 1204:1450 */         recaudador = this.servicioRecaudador.buscarPorId(idRecaudador);
/* 1205:     */       }
/* 1206:1452 */       getEmpresa().getCliente().setRecaudador(recaudador);
/* 1207:     */     }
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public List<Recaudador> getListaRecaudador()
/* 1211:     */   {
/* 1212:1462 */     if (this.listaRecaudador == null) {
/* 1213:1463 */       this.listaRecaudador = this.servicioRecaudador.obtenerListaCombo("nombre", true, null);
/* 1214:     */     }
/* 1215:1465 */     return this.listaRecaudador;
/* 1216:     */   }
/* 1217:     */   
/* 1218:     */   public void setListaRecaudador(List<Recaudador> listaRecaudador)
/* 1219:     */   {
/* 1220:1475 */     this.listaRecaudador = listaRecaudador;
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   public List<CategoriaRetencion> getListaCategoriaRetencion()
/* 1224:     */   {
/* 1225:1484 */     this.listaCategoriaRetencion = this.servicioCategoriaRetencion.obtenerListaCombo("nombre", true, null);
/* 1226:1485 */     return this.listaCategoriaRetencion;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   public void setListaCategoriaRetencion(List<CategoriaRetencion> listaCategoriaRetencion)
/* 1230:     */   {
/* 1231:1495 */     this.listaCategoriaRetencion = listaCategoriaRetencion;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public DireccionEmpresa getDireccionEmpresa()
/* 1235:     */   {
/* 1236:1504 */     return this.direccionEmpresa;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 1240:     */   {
/* 1241:1514 */     this.direccionEmpresa = direccionEmpresa;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public List<TipoCuentaBancaria> getListaTipoCuentaBancaria()
/* 1245:     */   {
/* 1246:1518 */     if (this.listaTipoCuentaBancaria == null) {
/* 1247:1519 */       this.listaTipoCuentaBancaria = this.servicioTipoCuentaBancaria.obtenerListaCombo(null, false, null);
/* 1248:     */     }
/* 1249:1521 */     return this.listaTipoCuentaBancaria;
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public boolean isEmpresaExiste()
/* 1253:     */   {
/* 1254:1530 */     return this.empresaExiste;
/* 1255:     */   }
/* 1256:     */   
/* 1257:     */   public void setEmpresaExiste(boolean empresaExiste)
/* 1258:     */   {
/* 1259:1540 */     this.empresaExiste = empresaExiste;
/* 1260:     */   }
/* 1261:     */   
/* 1262:     */   public List<SelectItem> getListaMetodoFacturacion()
/* 1263:     */   {
/* 1264:1548 */     if (this.listaMetodoFacturacion == null)
/* 1265:     */     {
/* 1266:1549 */       this.listaMetodoFacturacion = new ArrayList();
/* 1267:1550 */       for (MetodoFacturacionEnum t : MetodoFacturacionEnum.values())
/* 1268:     */       {
/* 1269:1551 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 1270:1552 */         this.listaMetodoFacturacion.add(item);
/* 1271:     */       }
/* 1272:     */     }
/* 1273:1556 */     return this.listaMetodoFacturacion;
/* 1274:     */   }
/* 1275:     */   
/* 1276:     */   public void setListaMetodoFacturacion(List<SelectItem> listaMetodoFacturacion)
/* 1277:     */   {
/* 1278:1564 */     this.listaMetodoFacturacion = listaMetodoFacturacion;
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   public List<ListaPrecios> getListaPreciosRecargos()
/* 1282:     */   {
/* 1283:1572 */     if (this.listaPreciosRecargos == null)
/* 1284:     */     {
/* 1285:1573 */       Map<String, String> filters = new HashMap();
/* 1286:1574 */       filters.put("tipoListaPrecios", TipoListaPreciosEnum.RECARGOS.toString());
/* 1287:     */       
/* 1288:1576 */       this.listaPreciosRecargos = this.servicioListaPrecios.obtenerListaCombo("nombre", true, filters);
/* 1289:     */     }
/* 1290:1579 */     return this.listaPreciosRecargos;
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public void setListaPreciosRecargos(List<ListaPrecios> listaPreciosRecargos)
/* 1294:     */   {
/* 1295:1587 */     this.listaPreciosRecargos = listaPreciosRecargos;
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   public RecargoListaPreciosCliente getRecargoListaPreciosClienteSeleccionado()
/* 1299:     */   {
/* 1300:1594 */     return this.recargoListaPreciosClienteSeleccionado;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public void setRecargoListaPreciosClienteSeleccionado(RecargoListaPreciosCliente recargoListaPreciosClienteSeleccionado)
/* 1304:     */   {
/* 1305:1602 */     this.recargoListaPreciosClienteSeleccionado = recargoListaPreciosClienteSeleccionado;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public List<TipoEmpresaEnum> getListaTipoCliente()
/* 1309:     */   {
/* 1310:1611 */     if (this.listaTipoCliente == null)
/* 1311:     */     {
/* 1312:1612 */       this.listaTipoCliente = new ArrayList();
/* 1313:1613 */       for (TipoEmpresaEnum tipoCliente : TipoEmpresaEnum.values()) {
/* 1314:1614 */         this.listaTipoCliente.add(tipoCliente);
/* 1315:     */       }
/* 1316:     */     }
/* 1317:1617 */     return this.listaTipoCliente;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public void setListaTipoAgrupacion(List<TipoEmpresaEnum> listaTipoCliente)
/* 1321:     */   {
/* 1322:1627 */     this.listaTipoCliente = listaTipoCliente;
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public DataTable getDtCuentaContable()
/* 1326:     */   {
/* 1327:1634 */     return this.dtCuentaContable;
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 1331:     */   {
/* 1332:1642 */     this.dtCuentaContable = dtCuentaContable;
/* 1333:     */   }
/* 1334:     */   
/* 1335:     */   public DataTable getDtSubempresa()
/* 1336:     */   {
/* 1337:1651 */     return this.dtSubempresa;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public void setDtSubempresa(DataTable dtSubempresa)
/* 1341:     */   {
/* 1342:1661 */     this.dtSubempresa = dtSubempresa;
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   public CuentaContable getCuentaContable()
/* 1346:     */   {
/* 1347:1668 */     return this.cuentaContable;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public void setCuentaContable(CuentaContable cuentaContable)
/* 1351:     */   {
/* 1352:1676 */     this.cuentaContable = cuentaContable;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public SelectItem[] getListaTipoClienteItem()
/* 1356:     */   {
/* 1357:1685 */     if (this.listaTipoClienteItem == null)
/* 1358:     */     {
/* 1359:1687 */       List<SelectItem> lista = new ArrayList();
/* 1360:1688 */       lista.add(new SelectItem("", ""));
/* 1361:1690 */       for (TipoEmpresaEnum t : TipoEmpresaEnum.values())
/* 1362:     */       {
/* 1363:1691 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 1364:1692 */         lista.add(item);
/* 1365:     */       }
/* 1366:1694 */       this.listaTipoClienteItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 1367:     */     }
/* 1368:1697 */     return this.listaTipoClienteItem;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   public DataTable getDtFormaPagoSRI()
/* 1372:     */   {
/* 1373:1701 */     return this.dtFormaPagoSRI;
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public void setDtFormaPagoSRI(DataTable dtFormaPagoSRI)
/* 1377:     */   {
/* 1378:1705 */     this.dtFormaPagoSRI = dtFormaPagoSRI;
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public List<SelectItem> getListaFormaPagoSRI()
/* 1382:     */   {
/* 1383:1709 */     return DatosSRI.getListaFormaPago(AppUtil.getOrganizacion().getId());
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public List<TipoContacto> getListaTipoContacto()
/* 1387:     */   {
/* 1388:1713 */     if (this.listaTipoContacto == null) {
/* 1389:1714 */       this.listaTipoContacto = this.servicioTipoContacto.obtenerListaCombo("nombre", true, null);
/* 1390:     */     }
/* 1391:1716 */     return this.listaTipoContacto;
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public void setListaTipoContacto(List<TipoContacto> listaTipoContacto)
/* 1395:     */   {
/* 1396:1720 */     this.listaTipoContacto = listaTipoContacto;
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public List<Contacto> getListaContacto()
/* 1400:     */   {
/* 1401:1724 */     List<Contacto> lista = new ArrayList();
/* 1402:1725 */     for (Contacto contacto : getEmpresa().getListaContacto()) {
/* 1403:1726 */       if (!contacto.isEliminado()) {
/* 1404:1727 */         lista.add(contacto);
/* 1405:     */       }
/* 1406:     */     }
/* 1407:1731 */     return lista;
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   public List<Transportista> getListaTransportistaCombo()
/* 1411:     */   {
/* 1412:1735 */     if (this.listaTransportistaCombo == null) {
/* 1413:1736 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("codigo", true, null);
/* 1414:     */     }
/* 1415:1738 */     return this.listaTransportistaCombo;
/* 1416:     */   }
/* 1417:     */   
/* 1418:     */   public List<ListaDescuentos> getListaListaDescuentos()
/* 1419:     */   {
/* 1420:1742 */     HashMap<String, String> filtros = new HashMap();
/* 1421:1743 */     filtros.put("activo", "true");
/* 1422:1744 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 1423:1745 */     if (this.listaListaDescuentos == null) {
/* 1424:1746 */       this.listaListaDescuentos = this.servicioListaDescuentos.obtenerListaCombo("codigo", true, filtros);
/* 1425:     */     }
/* 1426:1748 */     return this.listaListaDescuentos;
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   public List<OrigenIngresos> getListaOrigenIngresos()
/* 1430:     */   {
/* 1431:1752 */     this.listaOrigenIngresos = this.servicioOrigenIngresos.obtenerListaCombo("nombre", true, new HashMap());
/* 1432:1753 */     return this.listaOrigenIngresos;
/* 1433:     */   }
/* 1434:     */   
/* 1435:     */   public void setListaOrigenIngresos(List<OrigenIngresos> listaOrigenIngresos)
/* 1436:     */   {
/* 1437:1757 */     this.listaOrigenIngresos = listaOrigenIngresos;
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   public List<SelectItem> getListaGenero()
/* 1441:     */   {
/* 1442:1761 */     if (this.listaGenero == null)
/* 1443:     */     {
/* 1444:1762 */       this.listaGenero = new ArrayList();
/* 1445:1763 */       for (Genero genero : Genero.values())
/* 1446:     */       {
/* 1447:1764 */         SelectItem item = new SelectItem(genero, genero.getNombre());
/* 1448:1765 */         this.listaGenero.add(item);
/* 1449:     */       }
/* 1450:     */     }
/* 1451:1768 */     return this.listaGenero;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   public List<EstadoCivil> getListaEstadoCivil()
/* 1455:     */   {
/* 1456:1777 */     if (this.listaEstadoCivil == null) {
/* 1457:1778 */       this.listaEstadoCivil = this.servicioEstadoCivil.obtenerListaCombo("nombre", true, null);
/* 1458:     */     }
/* 1459:1780 */     return this.listaEstadoCivil;
/* 1460:     */   }
/* 1461:     */   
/* 1462:     */   public List<Parroquia> listaParroquiasPorCiudad(Ciudad ciudad)
/* 1463:     */   {
/* 1464:1784 */     List<Parroquia> listaParroquiasPorCiudad = this.servicioParroquia.obtenerListaCombo("", true, null, ciudad.getIdCiudad());
/* 1465:1785 */     return listaParroquiasPorCiudad;
/* 1466:     */   }
/* 1467:     */   
/* 1468:     */   public void limpiarCamposTipoEmpresa()
/* 1469:     */   {
/* 1470:1789 */     if (this.empresa.getTipoEmpresa().equals(TipoEmpresa.JURIDICA))
/* 1471:     */     {
/* 1472:1790 */       this.empresa.setGenero(null);
/* 1473:1791 */       this.empresa.setEstadoCivil(null);
/* 1474:1792 */       this.empresa.setOrigenIngresos(null);
/* 1475:     */     }
/* 1476:     */     else
/* 1477:     */     {
/* 1478:1794 */       if (this.empresa.getGenero() == null) {
/* 1479:1795 */         this.empresa.setGenero(Genero.MASCULINO);
/* 1480:     */       }
/* 1481:1797 */       if (this.empresa.getEstadoCivil() == null) {
/* 1482:1798 */         for (EstadoCivil ec : getListaEstadoCivil()) {
/* 1483:1799 */           if (ec.isPredeterminado()) {
/* 1484:1800 */             this.empresa.setEstadoCivil(ec);
/* 1485:     */           }
/* 1486:     */         }
/* 1487:     */       }
/* 1488:     */     }
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public List<TipoOrdenDespacho> getListaTipoOrdenDespacho()
/* 1492:     */   {
/* 1493:1808 */     if (this.listaTipoOrdenDespacho == null)
/* 1494:     */     {
/* 1495:1809 */       Map<String, String> filtros = new HashMap();
/* 1496:1810 */       filtros.put("idOrganizacion", AppUtil.getOrganizacion().getId() + "");
/* 1497:1811 */       filtros.put("activo", "true");
/* 1498:1812 */       this.listaTipoOrdenDespacho = this.servicioTipoOrdenDespacho.obtenerListaCombo(TipoOrdenDespacho.class, "nombre", true, filtros);
/* 1499:     */     }
/* 1500:1814 */     return this.listaTipoOrdenDespacho;
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   public void setListaTipoOrdenDespacho(List<TipoOrdenDespacho> listaTipoOrdenDespacho)
/* 1504:     */   {
/* 1505:1818 */     this.listaTipoOrdenDespacho = listaTipoOrdenDespacho;
/* 1506:     */   }
/* 1507:     */   
/* 1508:     */   public void actualizarCreditoMaxCliente()
/* 1509:     */   {
/* 1510:1844 */     this.servicioEmpresa.actualizarCreditoMaximo(getEmpresa());
/* 1511:1845 */     actualizarListaDetalleDocumentoDigitalizado();
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public boolean isActivaTabSubEmpresa()
/* 1515:     */   {
/* 1516:1849 */     boolean activa = false;
/* 1517:1851 */     if (((getEmpresa().getCliente() != null) && (getEmpresa().getCliente().getTipoCliente() != null) && 
/* 1518:1852 */       (!getEmpresa().getCliente().getTipoCliente().equals(TipoEmpresaEnum.SUBCLIENTE))) || (
/* 1519:1853 */       (getEmpresa().getProveedor() != null) && (getEmpresa().getProveedor().getTipoProveedor() != null) && 
/* 1520:1854 */       (!getEmpresa().getProveedor().getTipoProveedor().equals(TipoEmpresaEnum.SUBCLIENTE)))) {
/* 1521:1855 */       activa = true;
/* 1522:     */     }
/* 1523:1858 */     return activa;
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public List<DetalleDocumentoDigitalizado> getListaDetalleDocumentoDigitalizado()
/* 1527:     */   {
/* 1528:1862 */     if (this.empresa == null)
/* 1529:     */     {
/* 1530:1863 */       this.listaDetalleDocumentoDigitalizado = new ArrayList();
/* 1531:1864 */       return this.listaDetalleDocumentoDigitalizado;
/* 1532:     */     }
/* 1533:1866 */     if ((this.listaDetalleDocumentoDigitalizado == null) || (this.listaDetalleDocumentoDigitalizado.size() == 0))
/* 1534:     */     {
/* 1535:1867 */       int idOrganizacion = AppUtil.getOrganizacion().getIdOrganizacion();
/* 1536:1868 */       int idEmpresa = this.empresa.getId();
/* 1537:1869 */       int idCategoriaEmpresa = this.empresa.getCategoriaEmpresa() == null ? 0 : this.empresa.getCategoriaEmpresa().getId();
/* 1538:1870 */       this.listaDetalleDocumentoDigitalizado = this.servicioDetalleDocumentoDigitalizado.cargarListaDetalleDocumentoDigitalizadoEmpresa(idOrganizacion, idEmpresa, idCategoriaEmpresa, this.empresa
/* 1539:1871 */         .isIndicadorProveedor(), this.empresa.isIndicadorCliente(), 0);
/* 1540:     */     }
/* 1541:1874 */     return this.listaDetalleDocumentoDigitalizado;
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   public List<DetalleDocumentoDigitalizado> actualizarListaDetalleDocumentoDigitalizado()
/* 1545:     */   {
/* 1546:1878 */     if (this.empresa == null)
/* 1547:     */     {
/* 1548:1879 */       this.listaDetalleDocumentoDigitalizado = new ArrayList();
/* 1549:1880 */       return this.listaDetalleDocumentoDigitalizado;
/* 1550:     */     }
/* 1551:1882 */     int idOrganizacion = AppUtil.getOrganizacion().getIdOrganizacion();
/* 1552:1883 */     int idEmpresa = this.empresa.getId();
/* 1553:1884 */     int idCategoriaEmpresa = this.empresa.getCategoriaEmpresa() == null ? 0 : this.empresa.getCategoriaEmpresa().getId();
/* 1554:1885 */     this.listaDetalleDocumentoDigitalizado = this.servicioDetalleDocumentoDigitalizado.cargarListaDetalleDocumentoDigitalizadoEmpresa(idOrganizacion, idEmpresa, idCategoriaEmpresa, this.empresa
/* 1555:1886 */       .isIndicadorProveedor(), this.empresa.isIndicadorCliente(), 0);
/* 1556:     */     
/* 1557:     */ 
/* 1558:1889 */     return this.listaDetalleDocumentoDigitalizado;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public void setListaDetalleDocumentoDigitalizado(List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizado)
/* 1562:     */   {
/* 1563:1893 */     this.listaDetalleDocumentoDigitalizado = listaDetalleDocumentoDigitalizado;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public DetalleDocumentoDigitalizado getDetalleDocumentoDigitalizado()
/* 1567:     */   {
/* 1568:1897 */     return this.detalleDocumentoDigitalizado;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public void setDetalleDocumentoDigitalizado(DetalleDocumentoDigitalizado detalleDocumentoDigitalizado)
/* 1572:     */   {
/* 1573:1901 */     this.detalleDocumentoDigitalizado = detalleDocumentoDigitalizado;
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public DataTable getDtDetalleDocumentoDigitalizadoEmpresa()
/* 1577:     */   {
/* 1578:1905 */     return this.dtDetalleDocumentoDigitalizadoEmpresa;
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   public void setDtDetalleDocumentoDigitalizadoEmpresa(DataTable dtDetalleDocumentoDigitalizadoEmpresa)
/* 1582:     */   {
/* 1583:1909 */     this.dtDetalleDocumentoDigitalizadoEmpresa = dtDetalleDocumentoDigitalizadoEmpresa;
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public List<EmpresaAtributo> getListaEmpresaAtributoTipoValorCliente()
/* 1587:     */   {
/* 1588:1913 */     List<EmpresaAtributo> lista = new ArrayList();
/* 1589:1914 */     if (this.empresa.getConjuntoAtributoCliente() != null) {
/* 1590:1915 */       for (EmpresaAtributo ea : this.empresa.getListaEmpresaAtributo()) {
/* 1591:1916 */         if ((ea.isIndicadorCliente()) && (!ea.isEliminado()) && (!ea.getAtributo().isIndicadorInstancia()) && 
/* 1592:1917 */           (!ea.getAtributo().getTipoAtributo().equals(TipoAtributo.LISTA))) {
/* 1593:1918 */           lista.add(ea);
/* 1594:     */         }
/* 1595:     */       }
/* 1596:     */     }
/* 1597:1923 */     return lista;
/* 1598:     */   }
/* 1599:     */   
/* 1600:     */   public List<EmpresaAtributo> getListaEmpresaAtributoTipoValorProveedor()
/* 1601:     */   {
/* 1602:1927 */     List<EmpresaAtributo> lista = new ArrayList();
/* 1603:1928 */     if (this.empresa.getConjuntoAtributoProveedor() != null) {
/* 1604:1929 */       for (EmpresaAtributo ea : this.empresa.getListaEmpresaAtributo()) {
/* 1605:1930 */         if ((ea.isIndicadorProveedor()) && (!ea.isEliminado()) && (!ea.getAtributo().isIndicadorInstancia()) && 
/* 1606:1931 */           (!ea.getAtributo().getTipoAtributo().equals(TipoAtributo.LISTA))) {
/* 1607:1932 */           lista.add(ea);
/* 1608:     */         }
/* 1609:     */       }
/* 1610:     */     }
/* 1611:1937 */     return lista;
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public List<EmpresaAtributo> getListaEmpresaAtributoTipoListaCliente()
/* 1615:     */   {
/* 1616:1941 */     List<EmpresaAtributo> lista = new ArrayList();
/* 1617:1942 */     if (this.empresa.getConjuntoAtributoCliente() != null) {
/* 1618:1943 */       for (EmpresaAtributo ea : this.empresa.getListaEmpresaAtributo()) {
/* 1619:1944 */         if ((ea.isIndicadorCliente()) && (!ea.isEliminado()) && (!ea.getAtributo().isIndicadorInstancia()) && 
/* 1620:1945 */           (ea.getAtributo().getTipoAtributo().equals(TipoAtributo.LISTA))) {
/* 1621:1946 */           lista.add(ea);
/* 1622:     */         }
/* 1623:     */       }
/* 1624:     */     }
/* 1625:1951 */     return lista;
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   public List<EmpresaAtributo> getListaEmpresaAtributoTipoListaProveedor()
/* 1629:     */   {
/* 1630:1955 */     List<EmpresaAtributo> lista = new ArrayList();
/* 1631:1956 */     if (this.empresa.getConjuntoAtributoProveedor() != null) {
/* 1632:1957 */       for (EmpresaAtributo ea : this.empresa.getListaEmpresaAtributo()) {
/* 1633:1958 */         if ((ea.isIndicadorProveedor()) && (!ea.isEliminado()) && (!ea.getAtributo().isIndicadorInstancia()) && 
/* 1634:1959 */           (ea.getAtributo().getTipoAtributo().equals(TipoAtributo.LISTA))) {
/* 1635:1960 */           lista.add(ea);
/* 1636:     */         }
/* 1637:     */       }
/* 1638:     */     }
/* 1639:1965 */     return lista;
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public String actualizarAtributosCliente()
/* 1643:     */   {
/* 1644:     */     int idConjuntoAtributo;
/* 1645:1969 */     if (this.empresa.getConjuntoAtributoCliente() != null)
/* 1646:     */     {
/* 1647:1970 */       idConjuntoAtributo = this.empresa.getConjuntoAtributoCliente().getIdConjuntoAtributo();
/* 1648:1971 */       List<Atributo> listaAtributos = this.servicioConjuntoAtributo.cargarDetalle(idConjuntoAtributo).getListaAtributo();
/* 1649:1972 */       HashMap<Integer, Atributo> mapAtributos = new HashMap();
/* 1650:1973 */       for (Atributo atributo : listaAtributos) {
/* 1651:1974 */         mapAtributos.put(Integer.valueOf(atributo.getId()), atributo);
/* 1652:     */       }
/* 1653:1976 */       for (EmpresaAtributo empresaAtributo : this.empresa.getListaEmpresaAtributo()) {
/* 1654:1977 */         if (empresaAtributo.isIndicadorCliente()) {
/* 1655:1978 */           if (mapAtributos.containsKey(Integer.valueOf(empresaAtributo.getAtributo().getId())))
/* 1656:     */           {
/* 1657:1979 */             empresaAtributo.setEliminado(false);
/* 1658:1980 */             mapAtributos.remove(Integer.valueOf(empresaAtributo.getAtributo().getId()));
/* 1659:     */           }
/* 1660:     */           else
/* 1661:     */           {
/* 1662:1982 */             empresaAtributo.setEliminado(true);
/* 1663:     */           }
/* 1664:     */         }
/* 1665:     */       }
/* 1666:1987 */       for (Atributo atributo : mapAtributos.values()) {
/* 1667:1988 */         if ((atributo.isIndicadorCliente()) && (this.empresa.isIndicadorCliente()))
/* 1668:     */         {
/* 1669:1989 */           EmpresaAtributo ea = new EmpresaAtributo(this.empresa, atributo);
/* 1670:1990 */           ea.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1671:1991 */           ea.setIdSucursal(AppUtil.getSucursal().getId());
/* 1672:1992 */           ea.setConjuntoAtributo(this.empresa.getConjuntoAtributoCliente());
/* 1673:1993 */           ea.setIndicadorCliente(true);
/* 1674:1994 */           this.empresa.getListaEmpresaAtributo().add(ea);
/* 1675:     */         }
/* 1676:     */       }
/* 1677:     */     }
/* 1678:     */     else
/* 1679:     */     {
/* 1680:1998 */       for (EmpresaAtributo empresaAtributo : this.empresa.getListaEmpresaAtributo()) {
/* 1681:1999 */         if (empresaAtributo.isIndicadorCliente()) {
/* 1682:2000 */           empresaAtributo.setEliminado(true);
/* 1683:     */         }
/* 1684:     */       }
/* 1685:     */     }
/* 1686:2005 */     return "";
/* 1687:     */   }
/* 1688:     */   
/* 1689:     */   public String actualizarAtributosProveedor()
/* 1690:     */   {
/* 1691:     */     int idConjuntoAtributo;
/* 1692:2009 */     if (this.empresa.getConjuntoAtributoProveedor() != null)
/* 1693:     */     {
/* 1694:2010 */       idConjuntoAtributo = this.empresa.getConjuntoAtributoProveedor().getIdConjuntoAtributo();
/* 1695:2011 */       List<Atributo> listaAtributos = this.servicioConjuntoAtributo.cargarDetalle(idConjuntoAtributo).getListaAtributo();
/* 1696:2012 */       HashMap<Integer, Atributo> mapAtributos = new HashMap();
/* 1697:2013 */       for (Atributo atributo : listaAtributos) {
/* 1698:2014 */         mapAtributos.put(Integer.valueOf(atributo.getId()), atributo);
/* 1699:     */       }
/* 1700:2016 */       for (EmpresaAtributo empresaAtributo : this.empresa.getListaEmpresaAtributo()) {
/* 1701:2017 */         if (empresaAtributo.isIndicadorProveedor()) {
/* 1702:2018 */           if (mapAtributos.containsKey(Integer.valueOf(empresaAtributo.getAtributo().getId())))
/* 1703:     */           {
/* 1704:2019 */             empresaAtributo.setEliminado(false);
/* 1705:2020 */             mapAtributos.remove(Integer.valueOf(empresaAtributo.getAtributo().getId()));
/* 1706:     */           }
/* 1707:     */           else
/* 1708:     */           {
/* 1709:2022 */             empresaAtributo.setEliminado(true);
/* 1710:     */           }
/* 1711:     */         }
/* 1712:     */       }
/* 1713:2027 */       for (Atributo atributo : mapAtributos.values()) {
/* 1714:2028 */         if ((atributo.isIndicadorProveedor()) && (this.empresa.isIndicadorProveedor()))
/* 1715:     */         {
/* 1716:2029 */           EmpresaAtributo ea = new EmpresaAtributo(this.empresa, atributo);
/* 1717:2030 */           ea.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1718:2031 */           ea.setIdSucursal(AppUtil.getSucursal().getId());
/* 1719:2032 */           ea.setConjuntoAtributo(this.empresa.getConjuntoAtributoProveedor());
/* 1720:2033 */           ea.setIndicadorProveedor(true);
/* 1721:2034 */           this.empresa.getListaEmpresaAtributo().add(ea);
/* 1722:     */         }
/* 1723:     */       }
/* 1724:     */     }
/* 1725:     */     else
/* 1726:     */     {
/* 1727:2038 */       for (EmpresaAtributo empresaAtributo : this.empresa.getListaEmpresaAtributo()) {
/* 1728:2039 */         if (empresaAtributo.isIndicadorProveedor()) {
/* 1729:2040 */           empresaAtributo.setEliminado(true);
/* 1730:     */         }
/* 1731:     */       }
/* 1732:     */     }
/* 1733:2045 */     return "";
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public List<ConjuntoAtributo> getListaConjuntoAtributoCliente()
/* 1737:     */   {
/* 1738:2052 */     if (this.listaConjuntoAtributoCliente == null) {
/* 1739:2053 */       this.listaConjuntoAtributoCliente = this.servicioConjuntoAtributo.obtenerListaComboPorIndicador(AppUtil.getOrganizacion().getId(), false, this.empresa
/* 1740:2054 */         .isIndicadorCliente(), false);
/* 1741:     */     }
/* 1742:2057 */     return this.listaConjuntoAtributoCliente;
/* 1743:     */   }
/* 1744:     */   
/* 1745:     */   public void setListaConjuntoAtributoCliente(List<ConjuntoAtributo> listaConjuntoAtributoCliente)
/* 1746:     */   {
/* 1747:2065 */     this.listaConjuntoAtributoCliente = listaConjuntoAtributoCliente;
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   public List<ConjuntoAtributo> getListaConjuntoAtributoProveedor()
/* 1751:     */   {
/* 1752:2072 */     if (this.listaConjuntoAtributoProveedor == null) {
/* 1753:2073 */       this.listaConjuntoAtributoProveedor = this.servicioConjuntoAtributo.obtenerListaComboPorIndicador(AppUtil.getOrganizacion().getId(), false, false, this.empresa
/* 1754:2074 */         .isIndicadorProveedor());
/* 1755:     */     }
/* 1756:2077 */     return this.listaConjuntoAtributoProveedor;
/* 1757:     */   }
/* 1758:     */   
/* 1759:     */   public void setListaConjuntoAtributoProveedor(List<ConjuntoAtributo> listaConjuntoAtributoProveedor)
/* 1760:     */   {
/* 1761:2085 */     this.listaConjuntoAtributoProveedor = listaConjuntoAtributoProveedor;
/* 1762:     */   }
/* 1763:     */   
/* 1764:     */   public int getCantidadConjuntoAtributo()
/* 1765:     */   {
/* 1766:2089 */     int cantidad = 0;
/* 1767:2090 */     if ((this.empresa != null) && (this.empresa.isIndicadorCliente())) {
/* 1768:2091 */       cantidad++;
/* 1769:     */     }
/* 1770:2093 */     if ((this.empresa != null) && (this.empresa.isIndicadorProveedor())) {
/* 1771:2094 */       cantidad++;
/* 1772:     */     }
/* 1773:2097 */     return cantidad;
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   public String getCenterGeoMap()
/* 1777:     */   {
/* 1778:2101 */     this.centerGeoMap = "-0.180300, -78.492223";
/* 1779:2102 */     if ((this.empresa != null) && (this.empresa.getLatitud() != null) && (this.empresa.getLongitud() != null))
/* 1780:     */     {
/* 1781:2103 */       this.centerGeoMap = (this.empresa.getLatitud().toString() + ", " + this.empresa.getLongitud().toString());
/* 1782:2104 */       setZoomMap(16);
/* 1783:     */     }
/* 1784:2105 */     else if ((this.empresa != null) && (isEditado()))
/* 1785:     */     {
/* 1786:2106 */       for (DireccionEmpresa dirEmp : this.empresa.getDirecciones()) {
/* 1787:2107 */         if ((dirEmp.isIndicadorDireccionPrincipal()) && (dirEmp.getCiudad().getProvincia().getLatitud() != null) && 
/* 1788:2108 */           (dirEmp.getCiudad().getProvincia().getLongitud() != null))
/* 1789:     */         {
/* 1790:2109 */           this.centerGeoMap = (dirEmp.getCiudad().getProvincia().getLatitud().toString() + ", " + dirEmp.getCiudad().getProvincia().getLongitud().toString());
/* 1791:2110 */           break;
/* 1792:     */         }
/* 1793:     */       }
/* 1794:     */     }
/* 1795:2115 */     return this.centerGeoMap;
/* 1796:     */   }
/* 1797:     */   
/* 1798:     */   public void setCenterGeoMap(String centerGeoMap)
/* 1799:     */   {
/* 1800:2119 */     this.centerGeoMap = centerGeoMap;
/* 1801:     */   }
/* 1802:     */   
/* 1803:     */   public MapModel getGeoModel()
/* 1804:     */   {
/* 1805:2123 */     if (this.geoModel == null) {
/* 1806:2124 */       this.geoModel = new DefaultMapModel();
/* 1807:     */     }
/* 1808:2126 */     return this.geoModel;
/* 1809:     */   }
/* 1810:     */   
/* 1811:     */   public void setGeoModel(MapModel geoModel)
/* 1812:     */   {
/* 1813:2130 */     this.geoModel = geoModel;
/* 1814:     */   }
/* 1815:     */   
/* 1816:     */   public int getZoomMap()
/* 1817:     */   {
/* 1818:2134 */     return this.zoomMap;
/* 1819:     */   }
/* 1820:     */   
/* 1821:     */   public void setZoomMap(int zoomMap)
/* 1822:     */   {
/* 1823:2138 */     this.zoomMap = zoomMap;
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   public String getLugarABuscar()
/* 1827:     */   {
/* 1828:2142 */     return this.lugarABuscar;
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   public void setLugarABuscar(String lugarABuscar)
/* 1832:     */   {
/* 1833:2146 */     this.lugarABuscar = lugarABuscar;
/* 1834:     */   }
/* 1835:     */   
/* 1836:     */   public void cargarCodigo()
/* 1837:     */   {
/* 1838:2150 */     if (!isManejaCodigoEmpresa()) {
/* 1839:2151 */       this.empresa.setCodigo(this.empresa.getIdentificacion());
/* 1840:     */     }
/* 1841:     */   }
/* 1842:     */   
/* 1843:     */   public boolean isManejaCodigoEmpresa()
/* 1844:     */   {
/* 1845:2156 */     return ParametrosSistema.isManejaCodigoEmpresa(AppUtil.getOrganizacion().getIdOrganizacion()).booleanValue();
/* 1846:     */   }
/* 1847:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.EmpresaBean
 * JD-Core Version:    0.7.0.1
 */