/*    1:     */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    4:     */ import com.asinfo.as2.controller.LanguageController;
/*    5:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    6:     */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*    8:     */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*    9:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   10:     */ import com.asinfo.as2.entities.ActivoFijo;
/*   11:     */ import com.asinfo.as2.entities.Bodega;
/*   12:     */ import com.asinfo.as2.entities.Canal;
/*   13:     */ import com.asinfo.as2.entities.CategoriaActivo;
/*   14:     */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   15:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   16:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   17:     */ import com.asinfo.as2.entities.ConceptoContable;
/*   18:     */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*   19:     */ import com.asinfo.as2.entities.CriterioDistribucion;
/*   20:     */ import com.asinfo.as2.entities.CuentaContable;
/*   21:     */ import com.asinfo.as2.entities.Departamento;
/*   22:     */ import com.asinfo.as2.entities.DestinoCosto;
/*   23:     */ import com.asinfo.as2.entities.DimensionContable;
/*   24:     */ import com.asinfo.as2.entities.Documento;
/*   25:     */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*   26:     */ import com.asinfo.as2.entities.DocumentoVariableProceso;
/*   27:     */ import com.asinfo.as2.entities.Empleado;
/*   28:     */ import com.asinfo.as2.entities.Empresa;
/*   29:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   30:     */ import com.asinfo.as2.entities.Impuesto;
/*   31:     */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   32:     */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*   33:     */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*   34:     */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*   35:     */ import com.asinfo.as2.entities.Organizacion;
/*   36:     */ import com.asinfo.as2.entities.Producto;
/*   37:     */ import com.asinfo.as2.entities.Rubro;
/*   38:     */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*   39:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   40:     */ import com.asinfo.as2.entities.Sucursal;
/*   41:     */ import com.asinfo.as2.entities.Zona;
/*   42:     */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*   43:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   44:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   45:     */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*   46:     */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*   47:     */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo;
/*   48:     */ import com.asinfo.as2.financiero.cobros.configuracion.servicio.ServicioMotivoNotaCreditoCliente;
/*   49:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   50:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioConceptoContable;
/*   51:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*   52:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   53:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*   54:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoVariableProceso;
/*   55:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*   56:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   57:     */ import com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioMotivoNotaCreditoProveedor;
/*   58:     */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*   59:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   60:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*   61:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*   62:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   63:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   64:     */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*   65:     */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*   66:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado;
/*   67:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   68:     */ import com.asinfo.as2.util.AppUtil;
/*   69:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*   70:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*   71:     */ import java.util.ArrayList;
/*   72:     */ import java.util.HashMap;
/*   73:     */ import java.util.List;
/*   74:     */ import java.util.Map;
/*   75:     */ import javax.annotation.PostConstruct;
/*   76:     */ import javax.ejb.EJB;
/*   77:     */ import javax.faces.bean.ManagedBean;
/*   78:     */ import javax.faces.bean.ManagedProperty;
/*   79:     */ import javax.faces.bean.ViewScoped;
/*   80:     */ import org.apache.log4j.Logger;
/*   81:     */ import org.primefaces.component.datatable.DataTable;
/*   82:     */ import org.primefaces.event.SelectEvent;
/*   83:     */ 
/*   84:     */ @ManagedBean
/*   85:     */ @ViewScoped
/*   86:     */ public class DocumentoContabilizacionBean
/*   87:     */   extends PageControllerAS2
/*   88:     */ {
/*   89:     */   private static final long serialVersionUID = -1832175620477451430L;
/*   90:     */   @EJB
/*   91:     */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*   92:     */   @EJB
/*   93:     */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*   94:     */   @EJB
/*   95:     */   private ServicioDocumentoVariableProceso servicioDocumentoVariableProceso;
/*   96:     */   @EJB
/*   97:     */   private ServicioCuentaContable servicioCuentaContable;
/*   98:     */   @EJB
/*   99:     */   private ServicioDocumento servicioDocumento;
/*  100:     */   @EJB
/*  101:     */   private ServicioConceptoContable servicioConceptoContable;
/*  102:     */   @EJB
/*  103:     */   private ServicioSucursal servicioSucursal;
/*  104:     */   @EJB
/*  105:     */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  106:     */   @EJB
/*  107:     */   private ServicioEmpresa servicioEmpresa;
/*  108:     */   @EJB
/*  109:     */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  110:     */   @EJB
/*  111:     */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  112:     */   @EJB
/*  113:     */   private ServicioProducto servicioProducto;
/*  114:     */   @EJB
/*  115:     */   private ServicioBodega servicioBodega;
/*  116:     */   @EJB
/*  117:     */   private ServicioDestinoCosto servicioDestinoCosto;
/*  118:     */   @EJB
/*  119:     */   private ServicioCanal servicioCanal;
/*  120:     */   @EJB
/*  121:     */   private ServicioZona servicioZona;
/*  122:     */   @EJB
/*  123:     */   private ServicioImpuesto servicioImpuesto;
/*  124:     */   @EJB
/*  125:     */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*  126:     */   @EJB
/*  127:     */   private ServicioMotivoNotaCreditoCliente servicioMotivoNotaCreditoCliente;
/*  128:     */   @EJB
/*  129:     */   private ServicioMotivoNotaCreditoProveedor servicioMotivoNotaCreditoProveedor;
/*  130:     */   @EJB
/*  131:     */   private ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  132:     */   @EJB
/*  133:     */   private ServicioMotivoBajaActivo servicioMotivoBajaActivo;
/*  134:     */   @EJB
/*  135:     */   private ServicioCategoriaActivo servicioCategoriaActivo;
/*  136:     */   @EJB
/*  137:     */   private ServicioActivoFijo servicioActivoFijo;
/*  138:     */   @EJB
/*  139:     */   private ServicioRubro servicioRubro;
/*  140:     */   @EJB
/*  141:     */   private ServicioDepartamento servicioDepartamento;
/*  142:     */   @EJB
/*  143:     */   private ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  144:     */   @EJB
/*  145:     */   private ServicioGenerico<TipoAmortizacion> servicioTipoAmortizacion;
/*  146:     */   private DocumentoBase documentoBase;
/*  147:     */   private DocumentoContabilizacion documentoContabilizacion;
/*  148:     */   private CriterioContabilizacion criterioContabilizacion;
/*  149:     */   private CriterioDistribucion criterioDistribucion;
/*  150:     */   private List<DocumentoContabilizacion> listaDocumentoContabilizacion;
/*  151:     */   private List<CriterioDistribucion> listaCriterioDistribucion;
/*  152:     */   private List<DocumentoVariableProceso> listaDocumentoVariableProceso;
/*  153:     */   private List<DocumentoBase> listaDocumentoBase;
/*  154:     */   private Producto[] productosSeleccionados;
/*  155:     */   @ManagedProperty("#{listaProductoBean}")
/*  156:     */   private ListaProductoBean listaProductoBean;
/*  157:     */   @ManagedProperty("#{listaDimensionContableBean}")
/*  158:     */   private ListaDimensionContableBean listaDimensionContableBean;
/*  159:     */   @ManagedProperty("#{listaCuentaContableBean}")
/*  160:     */   private ListaCuentaContableBean listaCuentaContableBean;
/*  161:     */   public List<Documento> listaDocumento;
/*  162:     */   public List<ConceptoContable> listaConceptoContable;
/*  163:     */   public List<Sucursal> listaSucursal;
/*  164:     */   public List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  165:     */   public List<Empresa> listaCliente;
/*  166:     */   public List<Empresa> listaProveedor;
/*  167:     */   public List<CategoriaProducto> listaCategoriaProducto;
/*  168:     */   public List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  169:     */   public List<Producto> listaProducto;
/*  170:     */   public List<Bodega> listaBodega;
/*  171:     */   public List<DestinoCosto> listaDestinoCosto;
/*  172:     */   public List<Canal> listaCanal;
/*  173:     */   public List<Zona> listaZona;
/*  174:     */   public List<CategoriaImpuesto> listaCategoriaImpuesto;
/*  175:     */   public List<Impuesto> listaImpuesto;
/*  176:     */   public List<MotivoNotaCreditoCliente> listaMotivoNotaCreditoCliente;
/*  177:     */   public List<MotivoNotaCreditoProveedor> listaMotivoNotaCreditoProveedor;
/*  178:     */   public List<MotivoAjusteInventario> listaMotivoAjusteInventario;
/*  179:     */   public List<MotivoBajaActivo> listaMotivoBajaActivo;
/*  180:     */   public List<CategoriaActivo> listaCategoriaActivo;
/*  181:     */   public List<SubcategoriaActivo> listaSubcategoriaActivo;
/*  182:     */   public List<ActivoFijo> listaActivoFijo;
/*  183:     */   public List<TipoAmortizacion> listaTipoAmortizacion;
/*  184:     */   private CuentaContable cuentaContable;
/*  185:     */   private DataTable dtDocumentoBase;
/*  186:     */   private DataTable dtDocumentoContabilizacion;
/*  187:     */   private DataTable dtCriterioContabilizacion;
/*  188:     */   private DataTable dtCriterioDistribucion;
/*  189:     */   private DataTable dtCuentaContable;
/*  190:     */   private boolean mostrarDocumento;
/*  191:     */   private boolean mostrarSucursal;
/*  192:     */   private boolean mostrarCategoriaEmpresa;
/*  193:     */   private boolean mostrarCliente;
/*  194:     */   private boolean mostrarProveedor;
/*  195:     */   private boolean mostrarCategoriaProducto;
/*  196:     */   private boolean mostrarSubcategoriaProducto;
/*  197:     */   private boolean mostrarBodega;
/*  198:     */   private boolean mostrarDestinoCosto;
/*  199:     */   private boolean mostrarZona;
/*  200:     */   private boolean mostrarCategoriaImpuesto;
/*  201:     */   private boolean mostrarImpuesto;
/*  202:     */   private boolean mostrarCanal;
/*  203:     */   private boolean mostrarMotivoNotaCreditoCliente;
/*  204:     */   private boolean mostrarMotivoNotaCreditoProveedor;
/*  205:     */   private boolean mostrarMotivoAjusteInventario;
/*  206:     */   private boolean mostrarMotivoBajaActivo;
/*  207:     */   private boolean mostrarCategoriaActivo;
/*  208:     */   private boolean mostrarActivoFijo;
/*  209:     */   private boolean mostrarEmpleado;
/*  210:     */   private boolean mostrarRubro;
/*  211:     */   private boolean mostrarDepartamento;
/*  212:     */   private boolean mostrarProducto;
/*  213:     */   private boolean mostrarSubcategoriaActivo;
/*  214:     */   private boolean mostrarSubcliente;
/*  215:     */   private boolean mostrarSubProveedor;
/*  216:     */   private boolean mostrarConceptoContable;
/*  217:     */   private boolean mostrarTipoAmortizacion;
/*  218:     */   private boolean habilitarBotonCrear;
/*  219:     */   private String codigoCuentaContable;
/*  220:     */   
/*  221:     */   @PostConstruct
/*  222:     */   public void init()
/*  223:     */   {
/*  224: 270 */     this.listaDocumentoBase = new ArrayList();
/*  225: 271 */     for (DocumentoBase documentoBase : DocumentoBase.values()) {
/*  226: 272 */       if (documentoBase.isDocumentoContabilidad()) {
/*  227: 273 */         this.listaDocumentoBase.add(documentoBase);
/*  228:     */       }
/*  229:     */     }
/*  230: 276 */     limpiar();
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void cargarProducto(Producto producto)
/*  234:     */   {
/*  235: 281 */     if (producto != null)
/*  236:     */     {
/*  237: 282 */       if (this.criterioContabilizacion != null) {
/*  238: 283 */         this.criterioContabilizacion.setProducto(producto);
/*  239:     */       }
/*  240: 285 */       if (this.criterioDistribucion != null) {
/*  241: 286 */         this.criterioDistribucion.setProducto(producto);
/*  242:     */       }
/*  243:     */     }
/*  244:     */   }
/*  245:     */   
/*  246:     */   public List<Empresa> autocompletarProveedores(String consulta)
/*  247:     */   {
/*  248: 292 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/*  249:     */   }
/*  250:     */   
/*  251:     */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/*  252:     */   {
/*  253: 296 */     List<SubcategoriaProducto> lista = new ArrayList();
/*  254:     */     
/*  255: 298 */     HashMap<String, String> filters = new HashMap();
/*  256: 299 */     filters.put("nombre", "%" + consulta.trim());
/*  257:     */     
/*  258: 301 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/*  259:     */     
/*  260: 303 */     return lista;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public String editar()
/*  264:     */   {
/*  265: 308 */     cargarDatosContabilizar();
/*  266: 309 */     mostrarColummnas();
/*  267: 310 */     setEditado(true);
/*  268: 311 */     return "";
/*  269:     */   }
/*  270:     */   
/*  271:     */   public void seleccionarCuentaContable(SelectEvent event)
/*  272:     */   {
/*  273: 318 */     this.cuentaContable = ((CuentaContable)event.getObject());
/*  274:     */     
/*  275:     */ 
/*  276:     */ 
/*  277: 322 */     this.criterioContabilizacion.setCuentaContable(this.cuentaContable);
/*  278: 323 */     this.criterioContabilizacion.getCuentaContable().setCodigoCuentaTransient(this.criterioContabilizacion.getCuentaContable().getCodigo());
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void buscarCuentaContable()
/*  282:     */   {
/*  283:     */     try
/*  284:     */     {
/*  285: 329 */       this.criterioContabilizacion = ((CriterioContabilizacion)this.dtCriterioContabilizacion.getRowData());
/*  286: 330 */       String codigoCuentaContable = this.criterioContabilizacion.getCuentaContable().getCodigoCuentaTransient();
/*  287: 331 */       this.cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion().getIdOrganizacion());
/*  288: 332 */       this.criterioContabilizacion.setCuentaContable(this.cuentaContable);
/*  289:     */     }
/*  290:     */     catch (ExcepcionAS2Financiero e)
/*  291:     */     {
/*  292: 334 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + ": " + this.criterioContabilizacion.getCuentaContable().getCodigoCuentaTransient();
/*  293: 335 */       this.criterioContabilizacion.setCuentaContable(new CuentaContable());
/*  294: 336 */       addInfoMessage(strMensaje);
/*  295:     */     }
/*  296:     */     catch (Exception e)
/*  297:     */     {
/*  298: 339 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void seleccionarDocumentoContabilizacion(DocumentoContabilizacion documentoContabilizacion)
/*  303:     */   {
/*  304: 344 */     this.documentoContabilizacion = documentoContabilizacion;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void seleccionarCriterioContabilizacion(CriterioContabilizacion criterioContabilizacion)
/*  308:     */   {
/*  309: 348 */     this.criterioContabilizacion = criterioContabilizacion;
/*  310: 349 */     this.criterioDistribucion = null;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void seleccionarCriterioDistribucion(CriterioDistribucion criterioDistribucion)
/*  314:     */   {
/*  315: 353 */     this.criterioDistribucion = criterioDistribucion;
/*  316: 354 */     this.criterioContabilizacion = null;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void agregarCriterioContabilizacion()
/*  320:     */   {
/*  321: 361 */     this.criterioContabilizacion = new CriterioContabilizacion();
/*  322: 362 */     this.criterioContabilizacion.setIdOrganizacion(this.documentoContabilizacion.getIdOrganizacion());
/*  323: 363 */     this.criterioContabilizacion.setDocumentoContabilizacion(this.documentoContabilizacion);
/*  324: 364 */     this.criterioContabilizacion.setCuentaContable(new CuentaContable());
/*  325: 365 */     this.documentoContabilizacion.getListaCriterioContabilizacion().add(this.criterioContabilizacion);
/*  326:     */   }
/*  327:     */   
/*  328:     */   public void eliminarCriterioContabilizacion()
/*  329:     */   {
/*  330: 370 */     this.criterioContabilizacion = ((CriterioContabilizacion)this.dtCriterioContabilizacion.getRowData());
/*  331: 371 */     this.criterioContabilizacion.setEliminado(true);
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void agregarCriterioDistribucion()
/*  335:     */   {
/*  336: 379 */     CriterioDistribucion cd = new CriterioDistribucion();
/*  337: 380 */     cd.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  338: 381 */     cd.setDocumentoBase(this.documentoBase);
/*  339: 382 */     this.listaCriterioDistribucion.add(cd);
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void eliminarCriterioDistribucion()
/*  343:     */   {
/*  344: 386 */     this.criterioDistribucion = ((CriterioDistribucion)this.dtCriterioDistribucion.getRowData());
/*  345: 387 */     this.criterioDistribucion.setEliminado(true);
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void cargarDatosContabilizar()
/*  349:     */   {
/*  350: 393 */     this.listaDocumento = null;
/*  351: 394 */     this.listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(AppUtil.getOrganizacion().getIdOrganizacion(), this.documentoBase);
/*  352:     */     
/*  353: 396 */     this.listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(AppUtil.getOrganizacion().getIdOrganizacion(), this.documentoBase);
/*  354:     */   }
/*  355:     */   
/*  356:     */   public List<DocumentoContabilizacion> getListaDocumentoContabilizacion()
/*  357:     */   {
/*  358: 401 */     return this.listaDocumentoContabilizacion;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public String guardar()
/*  362:     */   {
/*  363:     */     try
/*  364:     */     {
/*  365: 410 */       this.servicioDocumentoContabilizacion.guardarListaDocumentoContabilizacion(this.listaDocumentoContabilizacion);
/*  366: 411 */       this.servicioCriterioDistribucion.guardarListaCriterioDistribucion(this.listaCriterioDistribucion, getNombreDimension1(), getNombreDimension2(), 
/*  367: 412 */         getNombreDimension3(), getNombreDimension4(), getNombreDimension5());
/*  368: 413 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  369: 414 */       setEditado(false);
/*  370: 415 */       cargarDatos();
/*  371:     */     }
/*  372:     */     catch (ExcepcionAS2Financiero e)
/*  373:     */     {
/*  374: 417 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  375: 418 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  376:     */     }
/*  377:     */     catch (Exception e)
/*  378:     */     {
/*  379: 422 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  380: 423 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  381:     */     }
/*  382: 425 */     return "";
/*  383:     */   }
/*  384:     */   
/*  385:     */   public String eliminar()
/*  386:     */   {
/*  387: 433 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  388: 434 */     return "";
/*  389:     */   }
/*  390:     */   
/*  391:     */   public String cargarDatos()
/*  392:     */   {
/*  393: 441 */     limpiar();
/*  394: 442 */     return "";
/*  395:     */   }
/*  396:     */   
/*  397:     */   public void seleccionarDimensionContableListener(SelectEvent event)
/*  398:     */   {
/*  399: 446 */     DimensionContable dimension = (DimensionContable)event.getObject();
/*  400:     */     try
/*  401:     */     {
/*  402: 448 */       String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/*  403: 449 */       if (numeroDimension.equals("1")) {
/*  404: 450 */         this.criterioDistribucion.setDimensionContable1(dimension);
/*  405: 451 */       } else if (numeroDimension.equals("2")) {
/*  406: 452 */         this.criterioDistribucion.setDimensionContable2(dimension);
/*  407: 453 */       } else if (numeroDimension.equals("3")) {
/*  408: 454 */         this.criterioDistribucion.setDimensionContable3(dimension);
/*  409: 455 */       } else if (numeroDimension.equals("4")) {
/*  410: 456 */         this.criterioDistribucion.setDimensionContable4(dimension);
/*  411: 457 */       } else if (numeroDimension.equals("5")) {
/*  412: 458 */         this.criterioDistribucion.setDimensionContable5(dimension);
/*  413:     */       }
/*  414:     */     }
/*  415:     */     catch (Exception e)
/*  416:     */     {
/*  417: 462 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  418: 463 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/*  419:     */     }
/*  420:     */   }
/*  421:     */   
/*  422:     */   public String crear()
/*  423:     */   {
/*  424: 474 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  425: 475 */     return "";
/*  426:     */   }
/*  427:     */   
/*  428:     */   public String limpiar()
/*  429:     */   {
/*  430: 482 */     this.documentoContabilizacion = new DocumentoContabilizacion();
/*  431:     */     
/*  432: 484 */     return "";
/*  433:     */   }
/*  434:     */   
/*  435:     */   private void ocultarColumnas()
/*  436:     */   {
/*  437: 488 */     this.mostrarDocumento = false;
/*  438: 489 */     this.mostrarSucursal = false;
/*  439: 490 */     this.mostrarCategoriaEmpresa = false;
/*  440: 491 */     this.mostrarCliente = false;
/*  441: 492 */     this.mostrarProveedor = false;
/*  442: 493 */     this.mostrarCategoriaProducto = false;
/*  443: 494 */     this.mostrarSubcategoriaProducto = false;
/*  444: 495 */     this.mostrarBodega = false;
/*  445: 496 */     this.mostrarDestinoCosto = false;
/*  446: 497 */     this.mostrarZona = false;
/*  447: 498 */     this.mostrarCategoriaImpuesto = false;
/*  448: 499 */     this.mostrarImpuesto = false;
/*  449: 500 */     this.mostrarCanal = false;
/*  450: 501 */     this.mostrarMotivoNotaCreditoCliente = false;
/*  451: 502 */     this.mostrarMotivoNotaCreditoProveedor = false;
/*  452: 503 */     this.mostrarMotivoAjusteInventario = false;
/*  453: 504 */     this.mostrarMotivoBajaActivo = false;
/*  454: 505 */     this.mostrarCategoriaActivo = false;
/*  455: 506 */     this.mostrarActivoFijo = false;
/*  456: 507 */     this.mostrarEmpleado = false;
/*  457: 508 */     this.mostrarRubro = false;
/*  458: 509 */     this.mostrarProducto = false;
/*  459: 510 */     this.mostrarSubcategoriaActivo = false;
/*  460: 511 */     this.mostrarSubcliente = false;
/*  461: 512 */     this.mostrarSubProveedor = false;
/*  462: 513 */     this.mostrarDepartamento = false;
/*  463:     */   }
/*  464:     */   
/*  465:     */   private void mostrarColummnas()
/*  466:     */   {
/*  467: 518 */     ocultarColumnas();
/*  468: 519 */     Map<String, String> filtros = new HashMap();
/*  469: 520 */     filtros.put("documentoBase", getDocumentoBase().toString());
/*  470: 521 */     for (DocumentoVariableProceso documentoVariableProceso : this.servicioDocumentoVariableProceso.obtenerListaCombo("variableProceso", true, filtros)) {
/*  471: 523 */       switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$VariableProcesoEnum[documentoVariableProceso.getVariableProceso().ordinal()])
/*  472:     */       {
/*  473:     */       case 1: 
/*  474: 525 */         this.mostrarDocumento = true;
/*  475: 526 */         break;
/*  476:     */       case 2: 
/*  477: 528 */         this.mostrarSucursal = true;
/*  478: 529 */         break;
/*  479:     */       case 3: 
/*  480: 531 */         this.mostrarCategoriaEmpresa = true;
/*  481: 532 */         break;
/*  482:     */       case 4: 
/*  483: 534 */         this.mostrarCliente = true;
/*  484: 535 */         break;
/*  485:     */       case 5: 
/*  486: 537 */         this.mostrarProveedor = true;
/*  487: 538 */         break;
/*  488:     */       case 6: 
/*  489: 540 */         this.mostrarCategoriaProducto = true;
/*  490: 541 */         break;
/*  491:     */       case 7: 
/*  492: 543 */         this.mostrarSubcategoriaProducto = true;
/*  493: 544 */         break;
/*  494:     */       case 8: 
/*  495: 546 */         this.mostrarBodega = true;
/*  496: 547 */         break;
/*  497:     */       case 9: 
/*  498: 549 */         this.mostrarDestinoCosto = true;
/*  499: 550 */         break;
/*  500:     */       case 10: 
/*  501: 552 */         this.mostrarZona = true;
/*  502: 553 */         break;
/*  503:     */       case 11: 
/*  504: 555 */         this.mostrarCategoriaImpuesto = true;
/*  505: 556 */         break;
/*  506:     */       case 12: 
/*  507: 558 */         this.mostrarImpuesto = true;
/*  508: 559 */         break;
/*  509:     */       case 13: 
/*  510: 561 */         this.mostrarCanal = true;
/*  511: 562 */         break;
/*  512:     */       case 14: 
/*  513: 564 */         this.mostrarMotivoNotaCreditoCliente = true;
/*  514: 565 */         break;
/*  515:     */       case 15: 
/*  516: 567 */         this.mostrarMotivoNotaCreditoProveedor = true;
/*  517: 568 */         break;
/*  518:     */       case 16: 
/*  519: 570 */         this.mostrarMotivoAjusteInventario = true;
/*  520: 571 */         break;
/*  521:     */       case 17: 
/*  522: 573 */         this.mostrarMotivoBajaActivo = true;
/*  523: 574 */         break;
/*  524:     */       case 18: 
/*  525: 576 */         this.mostrarCategoriaActivo = true;
/*  526: 577 */         break;
/*  527:     */       case 19: 
/*  528: 579 */         this.mostrarActivoFijo = true;
/*  529: 580 */         break;
/*  530:     */       case 20: 
/*  531: 582 */         this.mostrarEmpleado = true;
/*  532: 583 */         break;
/*  533:     */       case 21: 
/*  534: 585 */         this.mostrarRubro = true;
/*  535: 586 */         break;
/*  536:     */       case 22: 
/*  537: 588 */         this.mostrarProducto = true;
/*  538: 589 */         break;
/*  539:     */       case 23: 
/*  540: 591 */         this.mostrarSubcategoriaActivo = true;
/*  541: 592 */         break;
/*  542:     */       case 24: 
/*  543: 594 */         this.mostrarSubcliente = true;
/*  544: 595 */         break;
/*  545:     */       case 25: 
/*  546: 597 */         this.mostrarSubProveedor = true;
/*  547: 598 */         break;
/*  548:     */       case 26: 
/*  549: 600 */         this.mostrarConceptoContable = true;
/*  550: 601 */         break;
/*  551:     */       case 27: 
/*  552: 603 */         this.mostrarDepartamento = true;
/*  553: 604 */         break;
/*  554:     */       case 28: 
/*  555: 606 */         this.mostrarTipoAmortizacion = true;
/*  556:     */       }
/*  557:     */     }
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void crearDocumentoContabilizacionDescuentoImpuestoVentas()
/*  561:     */   {
/*  562:     */     try
/*  563:     */     {
/*  564: 616 */       if (isHabilitarBotonCrear())
/*  565:     */       {
/*  566: 617 */         DocumentoContabilizacion documentoContabilizacion = new DocumentoContabilizacion();
/*  567: 618 */         documentoContabilizacion.setActivo(true);
/*  568: 619 */         documentoContabilizacion.setDebe(false);
/*  569: 620 */         documentoContabilizacion.setHaber(true);
/*  570: 621 */         documentoContabilizacion.setDocumentoBase(getDocumentoBase());
/*  571: 622 */         documentoContabilizacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  572: 623 */         documentoContabilizacion.setIdSucursal(AppUtil.getSucursal().getId());
/*  573: 624 */         documentoContabilizacion.setProcesoContabilizacion(ProcesoContabilizacionEnum.COSTO_FLETE);
/*  574:     */         
/*  575: 626 */         this.servicioDocumentoContabilizacion.guardar(documentoContabilizacion);
/*  576: 627 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  577: 628 */         this.habilitarBotonCrear = false;
/*  578: 629 */         cargarDatosContabilizar();
/*  579:     */       }
/*  580:     */     }
/*  581:     */     catch (ExcepcionAS2Financiero e)
/*  582:     */     {
/*  583: 632 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  584: 633 */       LOG.error("ERROR AL GUARDAR DOCUMENTO DE CONTABILIZACION", e);
/*  585:     */     }
/*  586:     */   }
/*  587:     */   
/*  588:     */   public boolean isHabilitarBotonCrear()
/*  589:     */   {
/*  590: 638 */     this.habilitarBotonCrear = DocumentoBase.FACTURA_CLIENTE.equals(getDocumentoBase());
/*  591: 639 */     for (DocumentoContabilizacion dc : this.listaDocumentoContabilizacion) {
/*  592: 640 */       if (ProcesoContabilizacionEnum.COSTO_FLETE.equals(dc.getProcesoContabilizacion()))
/*  593:     */       {
/*  594: 641 */         this.habilitarBotonCrear = false;
/*  595: 642 */         break;
/*  596:     */       }
/*  597:     */     }
/*  598: 645 */     return this.habilitarBotonCrear;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public DocumentoBase getDocumentoBase()
/*  602:     */   {
/*  603: 654 */     return this.documentoBase;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  607:     */   {
/*  608: 664 */     this.documentoBase = documentoBase;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public DocumentoContabilizacion getDocumentoContabilizacion()
/*  612:     */   {
/*  613: 673 */     return this.documentoContabilizacion;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void setDocumentoContabilizacion(DocumentoContabilizacion documentoContabilizacion)
/*  617:     */   {
/*  618: 683 */     this.documentoContabilizacion = documentoContabilizacion;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public List<DocumentoVariableProceso> getListaDocumentoVariableProceso()
/*  622:     */   {
/*  623: 692 */     return this.listaDocumentoVariableProceso;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public void setListaDocumentoVariableProceso(List<DocumentoVariableProceso> listaDocumentoVariableProceso)
/*  627:     */   {
/*  628: 702 */     this.listaDocumentoVariableProceso = listaDocumentoVariableProceso;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public List<DocumentoBase> getListaDocumentoBase()
/*  632:     */   {
/*  633: 711 */     return this.listaDocumentoBase;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public void setListaDocumentoBase(List<DocumentoBase> listaDocumentoBase)
/*  637:     */   {
/*  638: 721 */     this.listaDocumentoBase = listaDocumentoBase;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public DataTable getDtDocumentoBase()
/*  642:     */   {
/*  643: 730 */     return this.dtDocumentoBase;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setDtDocumentoBase(DataTable dtDocumentoBase)
/*  647:     */   {
/*  648: 740 */     this.dtDocumentoBase = dtDocumentoBase;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public DataTable getDtDocumentoContabilizacion()
/*  652:     */   {
/*  653: 749 */     return this.dtDocumentoContabilizacion;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void setDtDocumentoContabilizacion(DataTable dtDocumentoContabilizacion)
/*  657:     */   {
/*  658: 759 */     this.dtDocumentoContabilizacion = dtDocumentoContabilizacion;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public DataTable getDtCriterioContabilizacion()
/*  662:     */   {
/*  663: 768 */     return this.dtCriterioContabilizacion;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setDtCriterioContabilizacion(DataTable dtCriterioContabilizacion)
/*  667:     */   {
/*  668: 778 */     this.dtCriterioContabilizacion = dtCriterioContabilizacion;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public DataTable getDtCriterioDistribucion()
/*  672:     */   {
/*  673: 787 */     return this.dtCriterioDistribucion;
/*  674:     */   }
/*  675:     */   
/*  676:     */   public void setDtCriterioDistribucion(DataTable dtCriterioDistribucion)
/*  677:     */   {
/*  678: 797 */     this.dtCriterioDistribucion = dtCriterioDistribucion;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public void setListaDocumentoContabilizacion(List<DocumentoContabilizacion> listaDocumentoContabilizacion)
/*  682:     */   {
/*  683: 807 */     this.listaDocumentoContabilizacion = listaDocumentoContabilizacion;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public CriterioContabilizacion getCriterioContabilizacion()
/*  687:     */   {
/*  688: 816 */     return this.criterioContabilizacion;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public void setCriterioContabilizacion(CriterioContabilizacion criterioContabilizacion)
/*  692:     */   {
/*  693: 826 */     this.criterioContabilizacion = criterioContabilizacion;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public CriterioDistribucion getCriterioDistribucion()
/*  697:     */   {
/*  698: 835 */     return this.criterioDistribucion;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public void setCriterioDistribucion(CriterioDistribucion criterioDistribucion)
/*  702:     */   {
/*  703: 845 */     this.criterioDistribucion = criterioDistribucion;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public CuentaContable getCuentaContable()
/*  707:     */   {
/*  708: 854 */     return this.cuentaContable;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void setCuentaContable(CuentaContable cuentaContable)
/*  712:     */   {
/*  713: 864 */     this.cuentaContable = cuentaContable;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public DataTable getDtCuentaContable()
/*  717:     */   {
/*  718: 873 */     return this.dtCuentaContable;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void setDtCuentaContable(DataTable dtCuentaContable)
/*  722:     */   {
/*  723: 883 */     this.dtCuentaContable = dtCuentaContable;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/*  727:     */   {
/*  728: 892 */     if (this.listaCategoriaEmpresa == null) {
/*  729: 893 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/*  730:     */     }
/*  731: 895 */     return this.listaCategoriaEmpresa;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public List<CategoriaProducto> getListaCategoriaProducto()
/*  735:     */   {
/*  736: 904 */     if (this.listaCategoriaProducto == null) {
/*  737: 905 */       this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, null);
/*  738:     */     }
/*  739: 907 */     return this.listaCategoriaProducto;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/*  743:     */   {
/*  744: 916 */     if (this.listaSubcategoriaProducto == null) {
/*  745: 917 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/*  746:     */     }
/*  747: 919 */     return this.listaSubcategoriaProducto;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public List<Bodega> getListaBodega()
/*  751:     */   {
/*  752: 928 */     if (this.listaBodega == null) {
/*  753: 929 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/*  754:     */     }
/*  755: 931 */     return this.listaBodega;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public List<DestinoCosto> getListaDestinoCosto()
/*  759:     */   {
/*  760: 940 */     if (this.listaDestinoCosto == null) {
/*  761: 941 */       this.listaDestinoCosto = this.servicioDestinoCosto.obtenerListaCombo("nombre", true, null);
/*  762:     */     }
/*  763: 943 */     return this.listaDestinoCosto;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public List<Zona> getListaZona()
/*  767:     */   {
/*  768: 952 */     if (this.listaZona == null) {
/*  769: 953 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/*  770:     */     }
/*  771: 955 */     return this.listaZona;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public List<CategoriaImpuesto> getListaCategoriaImpuesto()
/*  775:     */   {
/*  776: 964 */     if (this.listaCategoriaImpuesto == null) {
/*  777: 965 */       this.listaCategoriaImpuesto = this.servicioCategoriaImpuesto.obtenerListaCombo("nombre", true, null);
/*  778:     */     }
/*  779: 967 */     return this.listaCategoriaImpuesto;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public List<Impuesto> getListaImpuesto()
/*  783:     */   {
/*  784: 977 */     if (this.listaImpuesto == null) {
/*  785: 978 */       this.listaImpuesto = this.servicioImpuesto.obtenerListaCombo("nombre", true, null);
/*  786:     */     }
/*  787: 980 */     return this.listaImpuesto;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public List<Documento> getListaDocumento()
/*  791:     */   {
/*  792: 989 */     if (this.listaDocumento == null)
/*  793:     */     {
/*  794: 990 */       Map<String, String> filtros = new HashMap();
/*  795: 991 */       filtros.put("documentoBase", getDocumentoBase().toString());
/*  796: 992 */       agregarFiltroOrganizacion(filtros);
/*  797: 993 */       this.listaDocumento = this.servicioDocumento.obtenerListaCombo("nombre", true, filtros);
/*  798:     */     }
/*  799: 995 */     return this.listaDocumento;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public List<Sucursal> getListaSucursal()
/*  803:     */   {
/*  804:1004 */     if (this.listaSucursal == null) {
/*  805:1005 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/*  806:     */     }
/*  807:1007 */     return this.listaSucursal;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public List<Empresa> getListaCliente()
/*  811:     */   {
/*  812:1016 */     if (this.listaCliente == null) {
/*  813:1017 */       this.listaCliente = this.servicioEmpresa.obtenerClientes("nombreFiscal", true, null);
/*  814:     */     }
/*  815:1019 */     return this.listaCliente;
/*  816:     */   }
/*  817:     */   
/*  818:     */   public List<Empresa> autocompletarClientes(String consulta)
/*  819:     */   {
/*  820:1023 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  821:     */   }
/*  822:     */   
/*  823:     */   public List<Empresa> getListaProveedor()
/*  824:     */   {
/*  825:1032 */     if (this.listaProveedor == null) {
/*  826:1033 */       this.listaProveedor = this.servicioEmpresa.obtenerProveedores("nombreFiscal", true, null);
/*  827:     */     }
/*  828:1035 */     return this.listaProveedor;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public List<MotivoNotaCreditoCliente> getListaMotivoNotaCreditoCliente()
/*  832:     */   {
/*  833:1044 */     if (this.listaMotivoNotaCreditoCliente == null) {
/*  834:1045 */       this.listaMotivoNotaCreditoCliente = this.servicioMotivoNotaCreditoCliente.obtenerListaCombo("nombre", true, null);
/*  835:     */     }
/*  836:1047 */     return this.listaMotivoNotaCreditoCliente;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public List<MotivoNotaCreditoProveedor> getListaMotivoNotaCreditoProveedor()
/*  840:     */   {
/*  841:1056 */     if (this.listaMotivoNotaCreditoProveedor == null) {
/*  842:1057 */       this.listaMotivoNotaCreditoProveedor = this.servicioMotivoNotaCreditoProveedor.obtenerListaCombo("nombre", true, null);
/*  843:     */     }
/*  844:1060 */     return this.listaMotivoNotaCreditoProveedor;
/*  845:     */   }
/*  846:     */   
/*  847:     */   public List<MotivoAjusteInventario> getListaMotivoAjusteInventario()
/*  848:     */   {
/*  849:1069 */     if (this.listaMotivoAjusteInventario == null) {
/*  850:1070 */       this.listaMotivoAjusteInventario = this.servicioMotivoAjusteInventario.obtenerListaCombo("nombre", true, null);
/*  851:     */     }
/*  852:1072 */     return this.listaMotivoAjusteInventario;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public List<MotivoBajaActivo> getListaMotivoBajaActivo()
/*  856:     */   {
/*  857:1081 */     if (this.listaMotivoBajaActivo == null) {
/*  858:1082 */       this.listaMotivoBajaActivo = this.servicioMotivoBajaActivo.obtenerListaCombo("nombre", true, null);
/*  859:     */     }
/*  860:1084 */     return this.listaMotivoBajaActivo;
/*  861:     */   }
/*  862:     */   
/*  863:     */   public List<CategoriaActivo> getListaCategoriaActivo()
/*  864:     */   {
/*  865:1093 */     if (this.listaCategoriaActivo == null) {
/*  866:1094 */       this.listaCategoriaActivo = this.servicioCategoriaActivo.obtenerListaCombo("nombre", true, null);
/*  867:     */     }
/*  868:1096 */     return this.listaCategoriaActivo;
/*  869:     */   }
/*  870:     */   
/*  871:     */   public List<SubcategoriaActivo> getListaSubcategoriaActivo()
/*  872:     */   {
/*  873:1105 */     return this.listaSubcategoriaActivo;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public List<ActivoFijo> getListaActivoFijo()
/*  877:     */   {
/*  878:1114 */     if (this.listaActivoFijo == null) {
/*  879:1115 */       this.listaActivoFijo = this.servicioActivoFijo.obtenerListaCombo("nombre", true, null);
/*  880:     */     }
/*  881:1117 */     return this.listaActivoFijo;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public List<TipoAmortizacion> getListaTipoAmortizacion()
/*  885:     */   {
/*  886:1124 */     if (this.listaTipoAmortizacion == null) {
/*  887:1125 */       this.listaTipoAmortizacion = this.servicioTipoAmortizacion.obtenerListaCombo(TipoAmortizacion.class, "nombre", true, null);
/*  888:     */     }
/*  889:1127 */     return this.listaTipoAmortizacion;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public List<Empleado> autocompletarEmpleado(String consulta)
/*  893:     */   {
/*  894:1136 */     Map<String, String> filters = new HashMap();
/*  895:1137 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  896:1138 */     List<Empleado> lista = new ArrayList();
/*  897:1139 */     for (HistoricoEmpleado historicoEmpleado : this.servicioHistoricoEmpleado.autocompletarHistoricoEmpleado(consulta, filters)) {
/*  898:1140 */       lista.add(historicoEmpleado.getEmpleado());
/*  899:     */     }
/*  900:1142 */     return lista;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public List<Rubro> autocompletarRubro(String consulta)
/*  904:     */   {
/*  905:1146 */     List<Rubro> lista = new ArrayList();
/*  906:1147 */     HashMap<String, String> filters = new HashMap();
/*  907:1148 */     filters.put("nombre", consulta.trim());
/*  908:1149 */     lista = this.servicioRubro.obtenerListaCombo("nombre", true, filters);
/*  909:     */     
/*  910:1151 */     return lista;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public List<Departamento> autocompletarDepartamento(String consulta)
/*  914:     */   {
/*  915:1155 */     List<Departamento> lista = new ArrayList();
/*  916:1156 */     HashMap<String, String> filters = new HashMap();
/*  917:1157 */     filters.put("nombre", consulta.trim());
/*  918:1158 */     lista = this.servicioDepartamento.obtenerListaCombo("nombre", true, filters);
/*  919:     */     
/*  920:1160 */     return lista;
/*  921:     */   }
/*  922:     */   
/*  923:     */   public List<Canal> getListaCanal()
/*  924:     */   {
/*  925:1169 */     if (this.listaCanal == null) {
/*  926:1170 */       this.listaCanal = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/*  927:     */     }
/*  928:1172 */     return this.listaCanal;
/*  929:     */   }
/*  930:     */   
/*  931:     */   public Producto[] getProductosSeleccionados()
/*  932:     */   {
/*  933:1181 */     return this.productosSeleccionados;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void setProductosSeleccionados(Producto[] productosSeleccionados)
/*  937:     */   {
/*  938:1191 */     this.productosSeleccionados = productosSeleccionados;
/*  939:     */   }
/*  940:     */   
/*  941:     */   public ListaProductoBean getListaProductoBean()
/*  942:     */   {
/*  943:1200 */     return this.listaProductoBean;
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/*  947:     */   {
/*  948:1210 */     this.listaProductoBean = listaProductoBean;
/*  949:     */   }
/*  950:     */   
/*  951:     */   public ListaDimensionContableBean getListaDimensionContableBean()
/*  952:     */   {
/*  953:1219 */     return this.listaDimensionContableBean;
/*  954:     */   }
/*  955:     */   
/*  956:     */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/*  957:     */   {
/*  958:1229 */     this.listaDimensionContableBean = listaDimensionContableBean;
/*  959:     */   }
/*  960:     */   
/*  961:     */   public static long getSerialversionuid()
/*  962:     */   {
/*  963:1233 */     return -1832175620477451430L;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public List<Producto> getListaProducto()
/*  967:     */   {
/*  968:1237 */     return this.listaProducto;
/*  969:     */   }
/*  970:     */   
/*  971:     */   public boolean isMostrarDocumento()
/*  972:     */   {
/*  973:1241 */     return this.mostrarDocumento;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public boolean isMostrarSucursal()
/*  977:     */   {
/*  978:1245 */     return this.mostrarSucursal;
/*  979:     */   }
/*  980:     */   
/*  981:     */   public boolean isMostrarCategoriaEmpresa()
/*  982:     */   {
/*  983:1249 */     return this.mostrarCategoriaEmpresa;
/*  984:     */   }
/*  985:     */   
/*  986:     */   public boolean isMostrarCliente()
/*  987:     */   {
/*  988:1253 */     return this.mostrarCliente;
/*  989:     */   }
/*  990:     */   
/*  991:     */   public boolean isMostrarProveedor()
/*  992:     */   {
/*  993:1257 */     return this.mostrarProveedor;
/*  994:     */   }
/*  995:     */   
/*  996:     */   public boolean isMostrarCategoriaProducto()
/*  997:     */   {
/*  998:1261 */     return this.mostrarCategoriaProducto;
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public boolean isMostrarSubcategoriaProducto()
/* 1002:     */   {
/* 1003:1265 */     return this.mostrarSubcategoriaProducto;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public boolean isMostrarBodega()
/* 1007:     */   {
/* 1008:1269 */     return this.mostrarBodega;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public boolean isMostrarZona()
/* 1012:     */   {
/* 1013:1273 */     return this.mostrarZona;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public boolean isMostrarCategoriaImpuesto()
/* 1017:     */   {
/* 1018:1277 */     return this.mostrarCategoriaImpuesto;
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public boolean isMostrarImpuesto()
/* 1022:     */   {
/* 1023:1281 */     return this.mostrarImpuesto;
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public boolean isMostrarCanal()
/* 1027:     */   {
/* 1028:1285 */     return this.mostrarCanal;
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public boolean isMostrarMotivoNotaCreditoCliente()
/* 1032:     */   {
/* 1033:1289 */     return this.mostrarMotivoNotaCreditoCliente;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public boolean isMostrarMotivoNotaCreditoProveedor()
/* 1037:     */   {
/* 1038:1293 */     return this.mostrarMotivoNotaCreditoProveedor;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public boolean isMostrarMotivoAjusteInventario()
/* 1042:     */   {
/* 1043:1297 */     return this.mostrarMotivoAjusteInventario;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public boolean isMostrarMotivoBajaActivo()
/* 1047:     */   {
/* 1048:1301 */     return this.mostrarMotivoBajaActivo;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public boolean isMostrarCategoriaActivo()
/* 1052:     */   {
/* 1053:1305 */     return this.mostrarCategoriaActivo;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public boolean isMostrarActivoFijo()
/* 1057:     */   {
/* 1058:1309 */     return this.mostrarActivoFijo;
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public boolean isMostrarEmpleado()
/* 1062:     */   {
/* 1063:1313 */     return this.mostrarEmpleado;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public boolean isMostrarRubro()
/* 1067:     */   {
/* 1068:1317 */     return this.mostrarRubro;
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public boolean isMostrarProducto()
/* 1072:     */   {
/* 1073:1321 */     return this.mostrarProducto;
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public boolean isMostrarSubcategoriaActivo()
/* 1077:     */   {
/* 1078:1325 */     return this.mostrarSubcategoriaActivo;
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public boolean isMostrarSubcliente()
/* 1082:     */   {
/* 1083:1329 */     return this.mostrarSubcliente;
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public boolean isMostrarSubProveedor()
/* 1087:     */   {
/* 1088:1333 */     return this.mostrarSubProveedor;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public List<CriterioDistribucion> getListaCriterioDistribucion()
/* 1092:     */   {
/* 1093:1337 */     List<CriterioDistribucion> lista = new ArrayList();
/* 1094:1338 */     for (CriterioDistribucion criterioDistribucion : this.listaCriterioDistribucion) {
/* 1095:1339 */       if (!criterioDistribucion.isEliminado()) {
/* 1096:1340 */         lista.add(criterioDistribucion);
/* 1097:     */       }
/* 1098:     */     }
/* 1099:1343 */     return lista;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public List<CriterioContabilizacion> getListaCriterioContabilizacion()
/* 1103:     */   {
/* 1104:1347 */     List<CriterioContabilizacion> lista = new ArrayList();
/* 1105:1348 */     for (CriterioContabilizacion criterioContabilizacion : this.documentoContabilizacion.getListaCriterioContabilizacion()) {
/* 1106:1349 */       if (!criterioContabilizacion.isEliminado()) {
/* 1107:1350 */         lista.add(criterioContabilizacion);
/* 1108:     */       }
/* 1109:     */     }
/* 1110:1353 */     return lista;
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public List<ConceptoContable> getListaConceptoContable()
/* 1114:     */   {
/* 1115:1357 */     if (this.listaConceptoContable == null) {
/* 1116:1358 */       this.listaConceptoContable = this.servicioConceptoContable.obtenerListaCombo("nombre", true, null);
/* 1117:     */     }
/* 1118:1360 */     return this.listaConceptoContable;
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public boolean isMostrarConceptoContable()
/* 1122:     */   {
/* 1123:1364 */     return this.mostrarConceptoContable;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public boolean isMostrarDepartamento()
/* 1127:     */   {
/* 1128:1368 */     return this.mostrarDepartamento;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public boolean isMostrarDestinoCosto()
/* 1132:     */   {
/* 1133:1372 */     return this.mostrarDestinoCosto;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public String getCodigoCuentaContable()
/* 1137:     */   {
/* 1138:1376 */     return this.codigoCuentaContable;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public void setCodigoCuentaContable(String codigoCuentaContable)
/* 1142:     */   {
/* 1143:1380 */     this.codigoCuentaContable = codigoCuentaContable;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 1147:     */   {
/* 1148:1384 */     return this.listaCuentaContableBean;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 1152:     */   {
/* 1153:1388 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public boolean isMostrarTipoAmortizacion()
/* 1157:     */   {
/* 1158:1395 */     return this.mostrarTipoAmortizacion;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   public void setMostrarTipoAmortizacion(boolean mostrarTipoAmortizacion)
/* 1162:     */   {
/* 1163:1403 */     this.mostrarTipoAmortizacion = mostrarTipoAmortizacion;
/* 1164:     */   }
/* 1165:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.DocumentoContabilizacionBean
 * JD-Core Version:    0.7.0.1
 */