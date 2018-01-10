/*    1:     */ package com.asinfo.as2.seguridad.configuracion.cotroller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    5:     */ import com.asinfo.as2.controller.LanguageController;
/*    6:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    8:     */ import com.asinfo.as2.entities.Bodega;
/*    9:     */ import com.asinfo.as2.entities.DimensionContable;
/*   10:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   11:     */ import com.asinfo.as2.entities.Dispositivo;
/*   12:     */ import com.asinfo.as2.entities.Empleado;
/*   13:     */ import com.asinfo.as2.entities.Empresa;
/*   14:     */ import com.asinfo.as2.entities.Organizacion;
/*   15:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   16:     */ import com.asinfo.as2.entities.PlanComision;
/*   17:     */ import com.asinfo.as2.entities.Recaudador;
/*   18:     */ import com.asinfo.as2.entities.Sucursal;
/*   19:     */ import com.asinfo.as2.entities.Tema;
/*   20:     */ import com.asinfo.as2.entities.TipoVendedor;
/*   21:     */ import com.asinfo.as2.entities.UsuarioBodega;
/*   22:     */ import com.asinfo.as2.entities.UsuarioDimensionContable;
/*   23:     */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*   24:     */ import com.asinfo.as2.entities.UsuarioSucursal;
/*   25:     */ import com.asinfo.as2.entities.Visualizacion;
/*   26:     */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*   27:     */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   28:     */ import com.asinfo.as2.entities.seguridad.UsuarioSuperior;
/*   29:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   30:     */ import com.asinfo.as2.enumeraciones.Genero;
/*   31:     */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*   32:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   33:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*   34:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   35:     */ import com.asinfo.as2.seguridad.ServicioRol;
/*   36:     */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   37:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   38:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   39:     */ import com.asinfo.as2.servicio.ServicioTema;
/*   40:     */ import com.asinfo.as2.util.AppUtil;
/*   41:     */ import com.asinfo.as2.utils.NodoArbol;
/*   42:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision;
/*   43:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*   44:     */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioTipoVendedor;
/*   45:     */ import java.util.ArrayList;
/*   46:     */ import java.util.Collection;
/*   47:     */ import java.util.Collections;
/*   48:     */ import java.util.Comparator;
/*   49:     */ import java.util.HashMap;
/*   50:     */ import java.util.HashSet;
/*   51:     */ import java.util.Iterator;
/*   52:     */ import java.util.List;
/*   53:     */ import java.util.Map;
/*   54:     */ import java.util.Set;
/*   55:     */ import javax.annotation.PostConstruct;
/*   56:     */ import javax.ejb.EJB;
/*   57:     */ import javax.faces.bean.ManagedBean;
/*   58:     */ import javax.faces.bean.ManagedProperty;
/*   59:     */ import javax.faces.bean.ViewScoped;
/*   60:     */ import javax.faces.model.SelectItem;
/*   61:     */ import org.apache.log4j.Logger;
/*   62:     */ import org.primefaces.component.datatable.DataTable;
/*   63:     */ import org.primefaces.context.RequestContext;
/*   64:     */ import org.primefaces.model.DefaultTreeNode;
/*   65:     */ import org.primefaces.model.LazyDataModel;
/*   66:     */ import org.primefaces.model.SortOrder;
/*   67:     */ import org.primefaces.model.TreeNode;
/*   68:     */ 
/*   69:     */ @ManagedBean
/*   70:     */ @ViewScoped
/*   71:     */ public class UsuarioBean
/*   72:     */   extends PageControllerAS2
/*   73:     */ {
/*   74:     */   private static final long serialVersionUID = -8101778477610247284L;
/*   75:     */   @EJB
/*   76:     */   private transient ServicioUsuario servicioUsuario;
/*   77:     */   @EJB
/*   78:     */   private transient ServicioRol servicioRol;
/*   79:     */   @EJB
/*   80:     */   private transient ServicioTipoVendedor servicioTipoVendedor;
/*   81:     */   @EJB
/*   82:     */   private transient ServicioEmpresa servicioEmpresa;
/*   83:     */   @EJB
/*   84:     */   private transient ServicioTema servicioTema;
/*   85:     */   @EJB
/*   86:     */   private transient ServicioBodega servicioBodega;
/*   87:     */   @EJB
/*   88:     */   private transient ServicioSucursal servicioSucursal;
/*   89:     */   @EJB
/*   90:     */   private transient ServicioOrganizacion servicioOrganizacion;
/*   91:     */   @EJB
/*   92:     */   private transient ServicioRecaudador servicioRecaudador;
/*   93:     */   @EJB
/*   94:     */   private transient ServicioDimensionContable servicioDimensionContable;
/*   95:     */   @EJB
/*   96:     */   private transient ServicioPlanComision servicioPlanComision;
/*   97:     */   @EJB
/*   98:     */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*   99:     */   @EJB
/*  100:     */   private ServicioGenerico<Visualizacion> servicioVisualizacion;
/*  101:     */   private EntidadUsuario entidadUsuario;
/*  102:     */   private Empleado empleado;
/*  103:     */   private String tipoVendedorSeleccionado;
/*  104:     */   private boolean indicadorAgentecomercial;
/*  105:     */   private boolean indicadorAscendenteJerarquia;
/*  106:     */   private LazyDataModel<EntidadUsuario> listaEntidadUsuarios;
/*  107:     */   private List<SelectItem> listaGenero;
/*  108:     */   private List<SelectItem> entidadUsuarioItems;
/*  109: 138 */   private List<EntidadRol> listaRolesNoAsignados = new ArrayList();
/*  110: 139 */   private List<Bodega> listaBodegaNoAsignados = new ArrayList();
/*  111: 140 */   private List<Sucursal> listaSucursalesNoAsignadas = new ArrayList();
/*  112: 141 */   private List<Organizacion> listaOrganizacionesNoAsignadas = new ArrayList();
/*  113:     */   private EntidadRol[] rolesSeleccionados;
/*  114:     */   private Bodega[] bodegasSeleccionadas;
/*  115:     */   private Sucursal[] sucursalesSeleccionadas;
/*  116:     */   private Organizacion[] organizacionesSeleccionadas;
/*  117:     */   private List<TipoVendedor> listaTipoVendedorCombo;
/*  118:     */   private List<TipoVisualizacionEnum> listaTipoVisualizacionCombo;
/*  119:     */   private List<Tema> listaTema;
/*  120:     */   private List<Organizacion> listaOrganizacion;
/*  121:     */   private List<Recaudador> listaRecaudador;
/*  122:     */   private List<DocumentoBase> listaDocumentoBaseCombo;
/*  123:     */   private List<PlanComision> listaPlanComision;
/*  124:     */   private List<Dispositivo> listaDispositivo;
/*  125:     */   private List<Visualizacion> listaVisualizacionUsuario;
/*  126:     */   private boolean indicadorPresupuesto;
/*  127:     */   private List<SelectItem> listaDimensionContableItems;
/*  128:     */   private String numeroDimension;
/*  129:     */   private DimensionContable[] dimensionesSeleccionadas;
/*  130:     */   private List<DimensionContable> listaDimensionContable;
/*  131:     */   private List<DimensionContable> listaDimensionContablePresupuesto;
/*  132:     */   @ManagedProperty("#{rolBean}")
/*  133:     */   private RolBean rolBean;
/*  134:     */   private DataTable dtEntidadUsuario;
/*  135:     */   private DataTable dtEntidadRol;
/*  136:     */   private DataTable dtRolesAsignados;
/*  137:     */   private DataTable dtBodegasAsignadas;
/*  138:     */   private DataTable dtSucursalesAsignadas;
/*  139:     */   private DataTable dtOrganizacionesAsignadas;
/*  140:     */   private DataTable dtDimensionPresupuesto;
/*  141:     */   private DataTable dtDimensionNoPresupuesto;
/*  142:     */   private DataTable dtSuperioresAsignados;
/*  143:     */   private TreeNode root;
/*  144:     */   private boolean mostrarRoles;
/*  145:     */   
/*  146:     */   @PostConstruct
/*  147:     */   public void init()
/*  148:     */   {
/*  149: 193 */     this.listaEntidadUsuarios = new LazyDataModel()
/*  150:     */     {
/*  151:     */       private static final long serialVersionUID = 1L;
/*  152:     */       
/*  153:     */       public List<EntidadUsuario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  154:     */       {
/*  155: 200 */         List<EntidadUsuario> lista = new ArrayList();
/*  156: 201 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  157: 202 */         List<Integer> listaIdOrganizacion = new ArrayList();
/*  158:     */         Iterator localIterator;
/*  159:     */         UsuarioOrganizacion usuarioOrganizacion;
/*  160: 204 */         if (!AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador())
/*  161:     */         {
/*  162: 205 */           for (localIterator = AppUtil.getUsuarioEnSesion().getListaUsuarioOrganizacion().iterator(); localIterator.hasNext();)
/*  163:     */           {
/*  164: 205 */             usuarioOrganizacion = (UsuarioOrganizacion)localIterator.next();
/*  165: 206 */             listaIdOrganizacion.add(Integer.valueOf(usuarioOrganizacion.getOrganizacion().getId()));
/*  166:     */           }
/*  167:     */         }
/*  168:     */         else
/*  169:     */         {
/*  170: 209 */           Object listaOrganizacion = UsuarioBean.this.servicioOrganizacion.obtenerListaCombo(null, true, null);
/*  171: 210 */           for (Organizacion organizacion : (List)listaOrganizacion) {
/*  172: 211 */             listaIdOrganizacion.add(Integer.valueOf(organizacion.getId()));
/*  173:     */           }
/*  174:     */         }
/*  175: 215 */         UsuarioBean.this.obtenerFiltros(filters);
/*  176:     */         
/*  177: 217 */         lista = UsuarioBean.this.servicioUsuario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, listaIdOrganizacion);
/*  178: 218 */         UsuarioBean.this.listaEntidadUsuarios.setRowCount(UsuarioBean.this.servicioUsuario.contarPorCriterio(filters, listaIdOrganizacion));
/*  179:     */         
/*  180: 220 */         return lista;
/*  181:     */       }
/*  182:     */     };
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void obtenerFiltros(Map<String, String> filters) {}
/*  186:     */   
/*  187:     */   public void crearUsuario()
/*  188:     */   {
/*  189: 235 */     this.entidadUsuario = new EntidadUsuario();
/*  190: 236 */     this.entidadUsuario.setActivo(true);
/*  191: 237 */     UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion();
/*  192: 238 */     usuarioOrganizacion.setEntidadUsuario(this.entidadUsuario);
/*  193: 239 */     usuarioOrganizacion.setOrganizacion(AppUtil.getOrganizacion());
/*  194: 240 */     usuarioOrganizacion.setPredeterminado(true);
/*  195: 241 */     this.entidadUsuario.getListaUsuarioOrganizacion().add(usuarioOrganizacion);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public String editar()
/*  199:     */   {
/*  200: 251 */     if (getEntidadUsuario().getId() != 0)
/*  201:     */     {
/*  202: 252 */       setEditado(true);
/*  203: 253 */       this.entidadUsuario = this.servicioUsuario.cargarDetalle(this.entidadUsuario.getId(), AppUtil.getOrganizacion().getId());
/*  204:     */     }
/*  205:     */     else
/*  206:     */     {
/*  207: 256 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  208:     */     }
/*  209: 258 */     return "";
/*  210:     */   }
/*  211:     */   
/*  212:     */   public String guardar()
/*  213:     */   {
/*  214:     */     try
/*  215:     */     {
/*  216: 269 */       this.entidadUsuario.setListaRutaVendedor(new ArrayList());
/*  217:     */       
/*  218: 271 */       AppUtil.getUsuarioEnSesion().setTema(this.entidadUsuario.getTema());
/*  219:     */       
/*  220:     */ 
/*  221: 274 */       this.servicioUsuario.guardar(this.entidadUsuario);
/*  222:     */       
/*  223: 276 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  224: 277 */       limpiar();
/*  225:     */       
/*  226: 279 */       setEditado(false);
/*  227:     */     }
/*  228:     */     catch (ExcepcionAS2 e)
/*  229:     */     {
/*  230: 282 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  231: 283 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  232:     */     }
/*  233:     */     catch (Exception e)
/*  234:     */     {
/*  235: 285 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"), e.getMessage());
/*  236: 286 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  237:     */     }
/*  238: 288 */     return "";
/*  239:     */   }
/*  240:     */   
/*  241:     */   public String eliminar()
/*  242:     */   {
/*  243:     */     try
/*  244:     */     {
/*  245: 299 */       this.servicioUsuario.eliminar(this.entidadUsuario);
/*  246: 300 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  247:     */     }
/*  248:     */     catch (Exception e)
/*  249:     */     {
/*  250: 302 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  251: 303 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/*  252:     */     }
/*  253: 305 */     return "";
/*  254:     */   }
/*  255:     */   
/*  256:     */   public String limpiar()
/*  257:     */   {
/*  258: 315 */     crearUsuario();
/*  259: 316 */     return "";
/*  260:     */   }
/*  261:     */   
/*  262:     */   public String cargarDatos()
/*  263:     */   {
/*  264: 326 */     return "";
/*  265:     */   }
/*  266:     */   
/*  267:     */   public String agregarRol()
/*  268:     */   {
/*  269: 330 */     EntidadRol r = new EntidadRol();
/*  270: 331 */     getEntidadUsuario().getListaRol().add(r);
/*  271: 332 */     return "";
/*  272:     */   }
/*  273:     */   
/*  274:     */   public String eliminarDetalle()
/*  275:     */   {
/*  276: 343 */     EntidadRol e = (EntidadRol)this.dtRolesAsignados.getRowData();
/*  277: 344 */     e.setEliminado(true);
/*  278: 345 */     this.entidadUsuario.getListaRol().remove(e);
/*  279: 346 */     return "";
/*  280:     */   }
/*  281:     */   
/*  282:     */   public String cargarRolesNoAsignados()
/*  283:     */   {
/*  284: 355 */     this.rolesSeleccionados = null;
/*  285: 356 */     HashMap<Integer, EntidadRol> roles = new HashMap();
/*  286: 357 */     Map<String, String> filters = new HashMap();
/*  287: 358 */     filters.put("idOrganizacion", "!=-1");
/*  288: 359 */     filters.put("activo", "true");
/*  289: 360 */     for (EntidadRol entidadRol : this.servicioRol.obtenerListaCombo("", false, filters)) {
/*  290: 361 */       roles.put(Integer.valueOf(entidadRol.getId()), entidadRol);
/*  291:     */     }
/*  292: 364 */     for (EntidadRol entidadRol : getEntidadUsuario().getListaRol()) {
/*  293: 365 */       roles.remove(Integer.valueOf(entidadRol.getId()));
/*  294:     */     }
/*  295: 368 */     this.listaRolesNoAsignados = new ArrayList(roles.values());
/*  296:     */     
/*  297: 370 */     return "";
/*  298:     */   }
/*  299:     */   
/*  300:     */   public String asignarRoles()
/*  301:     */   {
/*  302: 380 */     if (this.rolesSeleccionados != null) {
/*  303: 381 */       for (EntidadRol entidadRol : this.rolesSeleccionados) {
/*  304: 382 */         getEntidadUsuario().getListaRol().add(entidadRol);
/*  305:     */       }
/*  306:     */     }
/*  307: 385 */     return "";
/*  308:     */   }
/*  309:     */   
/*  310:     */   public String eliminarBodega()
/*  311:     */   {
/*  312: 394 */     UsuarioBodega ub = (UsuarioBodega)this.dtBodegasAsignadas.getRowData();
/*  313: 395 */     ub.setEliminado(true);
/*  314: 396 */     return "";
/*  315:     */   }
/*  316:     */   
/*  317:     */   public String eliminarSucursal()
/*  318:     */   {
/*  319: 400 */     UsuarioSucursal usuarioSucursal = (UsuarioSucursal)this.dtSucursalesAsignadas.getRowData();
/*  320: 401 */     usuarioSucursal.setEliminado(true);
/*  321: 402 */     return "";
/*  322:     */   }
/*  323:     */   
/*  324:     */   public String eliminarOrganizacion()
/*  325:     */   {
/*  326: 406 */     UsuarioOrganizacion usuarioOrganizacion = (UsuarioOrganizacion)this.dtOrganizacionesAsignadas.getRowData();
/*  327: 407 */     usuarioOrganizacion.setEliminado(true);
/*  328: 408 */     for (UsuarioSucursal usuarioSucursal : getEntidadUsuario().getListaUsuarioSucursal()) {
/*  329: 409 */       if (usuarioSucursal.getSucursal().getIdOrganizacion() == usuarioOrganizacion.getOrganizacion().getId()) {
/*  330: 410 */         usuarioSucursal.setEliminado(true);
/*  331:     */       }
/*  332:     */     }
/*  333: 413 */     for (UsuarioBodega usuarioBodega : getEntidadUsuario().getListaUsuarioBodega()) {
/*  334: 414 */       if (usuarioBodega.getBodega().getIdOrganizacion() == usuarioOrganizacion.getOrganizacion().getId()) {
/*  335: 415 */         usuarioBodega.setEliminado(true);
/*  336:     */       }
/*  337:     */     }
/*  338: 418 */     return "";
/*  339:     */   }
/*  340:     */   
/*  341:     */   public String cargarBodegasNoAsignadas()
/*  342:     */   {
/*  343: 427 */     this.bodegasSeleccionadas = null;
/*  344: 428 */     HashMap<Integer, Bodega> bodegas = new HashMap();
/*  345: 429 */     for (Iterator localIterator1 = getEntidadUsuario().getListaUsuarioOrganizacion().iterator(); localIterator1.hasNext();)
/*  346:     */     {
/*  347: 429 */       usuarioOrganizacion = (UsuarioOrganizacion)localIterator1.next();
/*  348: 430 */       if (!usuarioOrganizacion.isEliminado())
/*  349:     */       {
/*  350: 431 */         HashMap<String, String> filtros = new HashMap();
/*  351: 432 */         filtros.put("activo", "true");
/*  352: 433 */         filtros.put("idOrganizacion", "" + usuarioOrganizacion.getOrganizacion().getId());
/*  353: 434 */         for (Bodega bodega : this.servicioBodega.obtenerListaCombo("nombre", true, filtros))
/*  354:     */         {
/*  355: 435 */           bodega.setOrganizacion(usuarioOrganizacion.getOrganizacion());
/*  356: 436 */           bodegas.put(Integer.valueOf(bodega.getId()), bodega);
/*  357:     */         }
/*  358:     */       }
/*  359:     */     }
/*  360:     */     UsuarioOrganizacion usuarioOrganizacion;
/*  361: 441 */     for (UsuarioBodega usuarioBodega : getEntidadUsuario().getListaUsuarioBodega()) {
/*  362: 442 */       if (!usuarioBodega.isEliminado()) {
/*  363: 443 */         bodegas.remove(Integer.valueOf(usuarioBodega.getBodega().getId()));
/*  364:     */       }
/*  365:     */     }
/*  366: 446 */     this.listaBodegaNoAsignados = new ArrayList(bodegas.values());
/*  367:     */     
/*  368: 448 */     return "";
/*  369:     */   }
/*  370:     */   
/*  371:     */   public String getDimensionPresupuesto()
/*  372:     */   {
/*  373: 452 */     OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/*  374: 453 */     String numeroDimension = "99";
/*  375: 454 */     if ((!aux.getNombreDimension1().equals("")) && (aux.isIndicadorUsoPresupuestoDimension1())) {
/*  376: 455 */       numeroDimension = "1";
/*  377: 456 */     } else if ((!aux.getNombreDimension2().equals("")) && (aux.isIndicadorUsoPresupuestoDimension2())) {
/*  378: 457 */       numeroDimension = "2";
/*  379: 458 */     } else if ((!aux.getNombreDimension3().equals("")) && (aux.isIndicadorUsoPresupuestoDimension3())) {
/*  380: 459 */       numeroDimension = "3";
/*  381: 460 */     } else if ((!aux.getNombreDimension4().equals("")) && (aux.isIndicadorUsoPresupuestoDimension4())) {
/*  382: 461 */       numeroDimension = "4";
/*  383: 462 */     } else if ((!aux.getNombreDimension5().equals("")) && (aux.isIndicadorUsoPresupuestoDimension5())) {
/*  384: 463 */       numeroDimension = "5";
/*  385:     */     }
/*  386: 465 */     return numeroDimension;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public String cargarSucursalesNoAsignadas()
/*  390:     */   {
/*  391: 474 */     this.sucursalesSeleccionadas = null;
/*  392: 475 */     HashMap<Integer, Sucursal> mapaSucursal = new HashMap();
/*  393: 476 */     for (UsuarioOrganizacion usuarioOrganizacion : getEntidadUsuario().getListaUsuarioOrganizacion()) {
/*  394: 477 */       if (!usuarioOrganizacion.isEliminado())
/*  395:     */       {
/*  396: 478 */         HashMap<String, String> filtros = new HashMap();
/*  397: 479 */         filtros.put("activo", "true");
/*  398: 480 */         filtros.put("idOrganizacion", "" + usuarioOrganizacion.getOrganizacion().getId());
/*  399: 482 */         for (Sucursal sucursal : this.servicioSucursal.obtenerListaCombo("idOrganizacion", true, filtros)) {
/*  400: 483 */           mapaSucursal.put(Integer.valueOf(sucursal.getId()), sucursal);
/*  401:     */         }
/*  402:     */       }
/*  403:     */     }
/*  404: 488 */     for (UsuarioSucursal usuarioSucursal : getEntidadUsuario().getListaUsuarioSucursal()) {
/*  405: 489 */       if (!usuarioSucursal.isEliminado()) {
/*  406: 490 */         mapaSucursal.remove(Integer.valueOf(usuarioSucursal.getSucursal().getId()));
/*  407:     */       }
/*  408:     */     }
/*  409: 494 */     this.listaSucursalesNoAsignadas = new ArrayList(mapaSucursal.values());
/*  410:     */     
/*  411: 496 */     return "";
/*  412:     */   }
/*  413:     */   
/*  414:     */   public String cargarOrganizacionesNoAsignadas()
/*  415:     */   {
/*  416: 500 */     this.organizacionesSeleccionadas = null;
/*  417:     */     
/*  418: 502 */     HashMap<Integer, Organizacion> mapaOrganizacion = new HashMap();
/*  419: 503 */     if (AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador()) {
/*  420: 504 */       for (Organizacion organizacion : this.servicioOrganizacion.obtenerListaCombo("razonSocial", true, null)) {
/*  421: 505 */         mapaOrganizacion.put(Integer.valueOf(organizacion.getId()), organizacion);
/*  422:     */       }
/*  423:     */     } else {
/*  424: 508 */       for (UsuarioOrganizacion usuarioOrganizacion : AppUtil.getUsuarioEnSesion().getListaUsuarioOrganizacion()) {
/*  425: 509 */         mapaOrganizacion.put(Integer.valueOf(usuarioOrganizacion.getOrganizacion().getId()), usuarioOrganizacion.getOrganizacion());
/*  426:     */       }
/*  427:     */     }
/*  428: 513 */     for (UsuarioOrganizacion usuarioOrganizacion : getEntidadUsuario().getListaUsuarioOrganizacion()) {
/*  429: 514 */       if (!usuarioOrganizacion.isEliminado()) {
/*  430: 515 */         mapaOrganizacion.remove(Integer.valueOf(usuarioOrganizacion.getOrganizacion().getId()));
/*  431:     */       }
/*  432:     */     }
/*  433: 519 */     this.listaOrganizacionesNoAsignadas = new ArrayList(mapaOrganizacion.values());
/*  434:     */     
/*  435: 521 */     return "";
/*  436:     */   }
/*  437:     */   
/*  438:     */   public String asignarBodegas()
/*  439:     */   {
/*  440: 530 */     if (this.bodegasSeleccionadas != null) {
/*  441: 531 */       for (Bodega bodega : this.bodegasSeleccionadas)
/*  442:     */       {
/*  443: 532 */         UsuarioBodega usuarioBodega = new UsuarioBodega();
/*  444: 533 */         usuarioBodega.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  445: 534 */         usuarioBodega.setIdSucursal(AppUtil.getSucursal().getId());
/*  446: 535 */         usuarioBodega.setBodega(bodega);
/*  447: 536 */         usuarioBodega.setEntidadUsuario(getEntidadUsuario());
/*  448: 537 */         getEntidadUsuario().getListaUsuarioBodega().add(usuarioBodega);
/*  449:     */       }
/*  450:     */     }
/*  451: 540 */     return "";
/*  452:     */   }
/*  453:     */   
/*  454:     */   public String asignarDimensiones()
/*  455:     */   {
/*  456: 545 */     if (this.dimensionesSeleccionadas != null)
/*  457:     */     {
/*  458: 547 */       Set<Integer> keysDimensionContable = new HashSet();
/*  459: 548 */       if (this.indicadorPresupuesto) {
/*  460: 549 */         for (localObject = getEntidadUsuario().getListaDimensionContablesPresupuesto().iterator(); ((Iterator)localObject).hasNext();)
/*  461:     */         {
/*  462: 549 */           UsuarioDimensionContable usuarioDimensionContable = (UsuarioDimensionContable)((Iterator)localObject).next();
/*  463: 550 */           keysDimensionContable.add(Integer.valueOf(usuarioDimensionContable.getDimensionContable().getId()));
/*  464:     */         }
/*  465:     */       } else {
/*  466: 554 */         for (localObject = getEntidadUsuario().getListaDimensionContablesNoPresupuesto().iterator(); ((Iterator)localObject).hasNext();)
/*  467:     */         {
/*  468: 554 */           usuarioDimensionContable = (UsuarioDimensionContable)((Iterator)localObject).next();
/*  469: 555 */           keysDimensionContable.add(Integer.valueOf(usuarioDimensionContable.getDimensionContable().getId()));
/*  470:     */         }
/*  471:     */       }
/*  472: 559 */       Object localObject = this.dimensionesSeleccionadas;UsuarioDimensionContable usuarioDimensionContable = localObject.length;
/*  473:     */       DimensionContable dimensionContable;
/*  474:     */       UsuarioDimensionContable usuarioDimensionContable;
/*  475: 559 */       for (UsuarioDimensionContable localUsuarioDimensionContable1 = 0; localUsuarioDimensionContable1 < usuarioDimensionContable; localUsuarioDimensionContable1++)
/*  476:     */       {
/*  477: 559 */         dimensionContable = localObject[localUsuarioDimensionContable1];
/*  478:     */         
/*  479: 561 */         usuarioDimensionContable = new UsuarioDimensionContable();
/*  480: 562 */         usuarioDimensionContable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  481: 563 */         usuarioDimensionContable.setIdSucursal(AppUtil.getSucursal().getId());
/*  482: 564 */         usuarioDimensionContable.setDimensionContable(dimensionContable);
/*  483: 565 */         usuarioDimensionContable.setEntidadUsuario(getEntidadUsuario());
/*  484: 566 */         usuarioDimensionContable.setIndicadorPresupuesto(this.indicadorPresupuesto);
/*  485: 567 */         getEntidadUsuario().getListaUsuarioDimensionContable().add(usuarioDimensionContable);
/*  486: 568 */         keysDimensionContable.add(Integer.valueOf(dimensionContable.getId()));
/*  487:     */         
/*  488: 570 */         List<DimensionContable> listDimensionesPadres = this.servicioDimensionContable.obtenerListaDimensionPadreRecursivo(dimensionContable);
/*  489: 572 */         for (DimensionContable dc : listDimensionesPadres) {
/*  490: 573 */           if (!keysDimensionContable.contains(Integer.valueOf(dc.getId())))
/*  491:     */           {
/*  492: 574 */             usuarioDimensionContable = new UsuarioDimensionContable();
/*  493: 575 */             usuarioDimensionContable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  494: 576 */             usuarioDimensionContable.setIdSucursal(AppUtil.getSucursal().getId());
/*  495: 577 */             usuarioDimensionContable.setDimensionContable(dc);
/*  496: 578 */             usuarioDimensionContable.setEntidadUsuario(getEntidadUsuario());
/*  497: 579 */             usuarioDimensionContable.setIndicadorPresupuesto(this.indicadorPresupuesto);
/*  498: 580 */             getEntidadUsuario().getListaUsuarioDimensionContable().add(usuarioDimensionContable);
/*  499: 581 */             keysDimensionContable.add(Integer.valueOf(dimensionContable.getId()));
/*  500:     */           }
/*  501:     */         }
/*  502:     */       }
/*  503:     */     }
/*  504: 586 */     return "";
/*  505:     */   }
/*  506:     */   
/*  507:     */   public String asignarSucursales()
/*  508:     */   {
/*  509: 590 */     if (this.sucursalesSeleccionadas != null) {
/*  510: 591 */       for (Sucursal sucursal : this.sucursalesSeleccionadas)
/*  511:     */       {
/*  512: 592 */         UsuarioSucursal usuarioSucursal = getUsuarioSucursalEliminado(sucursal.getId());
/*  513: 593 */         if (usuarioSucursal == null)
/*  514:     */         {
/*  515: 594 */           usuarioSucursal = new UsuarioSucursal();
/*  516: 595 */           usuarioSucursal.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  517: 596 */           usuarioSucursal.setSucursal(sucursal);
/*  518: 597 */           usuarioSucursal.setEntidadUsuario(getEntidadUsuario());
/*  519: 598 */           getEntidadUsuario().getListaUsuarioSucursal().add(usuarioSucursal);
/*  520:     */         }
/*  521:     */         else
/*  522:     */         {
/*  523: 600 */           usuarioSucursal.setEliminado(false);
/*  524:     */         }
/*  525:     */       }
/*  526:     */     }
/*  527: 604 */     return "";
/*  528:     */   }
/*  529:     */   
/*  530:     */   private UsuarioSucursal getUsuarioSucursalEliminado(int idSucursal)
/*  531:     */   {
/*  532: 608 */     for (UsuarioSucursal usuarioSucursal : getEntidadUsuario().getListaUsuarioSucursal()) {
/*  533: 609 */       if ((usuarioSucursal.isEliminado()) && (usuarioSucursal.getSucursal().getId() == idSucursal)) {
/*  534: 610 */         return usuarioSucursal;
/*  535:     */       }
/*  536:     */     }
/*  537: 613 */     return null;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public String asignarOrganizaciones()
/*  541:     */   {
/*  542: 618 */     if (this.organizacionesSeleccionadas != null) {
/*  543: 619 */       for (Organizacion organizacion : this.organizacionesSeleccionadas)
/*  544:     */       {
/*  545: 620 */         UsuarioOrganizacion usuarioOrganizacion = new UsuarioOrganizacion();
/*  546:     */         
/*  547: 622 */         usuarioOrganizacion.setOrganizacion(organizacion);
/*  548: 623 */         usuarioOrganizacion.setEntidadUsuario(getEntidadUsuario());
/*  549: 624 */         getEntidadUsuario().getListaUsuarioOrganizacion().add(usuarioOrganizacion);
/*  550:     */       }
/*  551:     */     }
/*  552: 627 */     return "";
/*  553:     */   }
/*  554:     */   
/*  555:     */   public String cargarEmpleado()
/*  556:     */   {
/*  557: 636 */     this.entidadUsuario.setEmpleado(this.empleado);
/*  558: 637 */     Empresa empresa = this.servicioEmpresa.cargarDetalle(this.empleado.getEmpresa());
/*  559: 638 */     this.entidadUsuario.setIdentificacion(empresa.getIdentificacion());
/*  560: 639 */     this.entidadUsuario.setNombre1(empresa.getEmpleado().getNombres());
/*  561: 640 */     this.entidadUsuario.setNombre2(empresa.getEmpleado().getApellidos());
/*  562: 641 */     this.entidadUsuario.setEmail(empresa.getEmail1());
/*  563: 642 */     this.entidadUsuario.setGenero(this.empleado.getGenero());
/*  564: 643 */     this.entidadUsuario.setCodigo(empresa.getCodigo());
/*  565: 644 */     if (empresa.getDirecciones().size() > 0) {
/*  566: 645 */       this.entidadUsuario.setDireccion(((DireccionEmpresa)empresa.getDirecciones().get(0)).getDireccionCompleta());
/*  567:     */     } else {
/*  568: 647 */       this.entidadUsuario.setDireccion("");
/*  569:     */     }
/*  570: 649 */     return "";
/*  571:     */   }
/*  572:     */   
/*  573:     */   public void changeTipoDimension()
/*  574:     */   {
/*  575: 653 */     if (this.indicadorPresupuesto) {
/*  576: 654 */       this.numeroDimension = getDimensionPresupuesto();
/*  577:     */     } else {
/*  578: 656 */       this.numeroDimension = null;
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   public List<Visualizacion> autocompletarVisualizacion(String consulta)
/*  583:     */   {
/*  584: 661 */     List<Visualizacion> lista = new ArrayList();
/*  585: 662 */     HashMap<String, String> filters = new HashMap();
/*  586: 663 */     filters.put("nombre", consulta.trim());
/*  587: 664 */     filters.put("idOrganizacion", "" + ((UsuarioOrganizacion)this.dtOrganizacionesAsignadas.getRowData()).getOrganizacion().getIdOrganizacion());
/*  588: 665 */     lista = this.servicioVisualizacion.obtenerListaCombo(Visualizacion.class, "nombre", true, filters);
/*  589: 666 */     return lista;
/*  590:     */   }
/*  591:     */   
/*  592:     */   public EntidadUsuario getEntidadUsuario()
/*  593:     */   {
/*  594: 684 */     if (this.entidadUsuario == null) {
/*  595: 685 */       crearUsuario();
/*  596:     */     }
/*  597: 687 */     return this.entidadUsuario;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  601:     */   {
/*  602: 691 */     this.entidadUsuario = entidadUsuario;
/*  603:     */   }
/*  604:     */   
/*  605:     */   public LazyDataModel<EntidadUsuario> getListaEntidadUsuarios()
/*  606:     */   {
/*  607: 695 */     if (this.listaEntidadUsuarios == null) {
/*  608: 696 */       cargarDatos();
/*  609:     */     }
/*  610: 698 */     return this.listaEntidadUsuarios;
/*  611:     */   }
/*  612:     */   
/*  613:     */   public void setListaEntidadUsuarios(LazyDataModel<EntidadUsuario> listaEntidadUsuarios)
/*  614:     */   {
/*  615: 702 */     this.listaEntidadUsuarios = listaEntidadUsuarios;
/*  616:     */   }
/*  617:     */   
/*  618:     */   public List<SelectItem> getEntidadUsuarioItems()
/*  619:     */   {
/*  620: 706 */     return this.entidadUsuarioItems;
/*  621:     */   }
/*  622:     */   
/*  623:     */   public void setEntidadUsuarioItems(List<SelectItem> entidadUsuarioItems)
/*  624:     */   {
/*  625: 710 */     this.entidadUsuarioItems = entidadUsuarioItems;
/*  626:     */   }
/*  627:     */   
/*  628:     */   public DataTable getDtEntidadUsuario()
/*  629:     */   {
/*  630: 714 */     return this.dtEntidadUsuario;
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void setDtEntidadUsuario(DataTable dtEntidadUsuario)
/*  634:     */   {
/*  635: 718 */     this.dtEntidadUsuario = dtEntidadUsuario;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public List<SelectItem> getListaGenero()
/*  639:     */   {
/*  640: 722 */     if (this.listaGenero == null)
/*  641:     */     {
/*  642: 723 */       this.listaGenero = new ArrayList();
/*  643: 724 */       for (Genero g : Genero.values())
/*  644:     */       {
/*  645: 725 */         SelectItem item = new SelectItem(g, g.getNombre());
/*  646: 726 */         this.listaGenero.add(item);
/*  647:     */       }
/*  648:     */     }
/*  649: 729 */     return this.listaGenero;
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void setListaGenero(List<SelectItem> listaGenero)
/*  653:     */   {
/*  654: 733 */     this.listaGenero = listaGenero;
/*  655:     */   }
/*  656:     */   
/*  657:     */   public List<EntidadRol> getEntidadRol()
/*  658:     */   {
/*  659: 742 */     List<EntidadRol> roles = new ArrayList();
/*  660: 743 */     for (EntidadRol entidadRol : getEntidadUsuario().getListaRol()) {
/*  661: 744 */       roles.add(entidadRol);
/*  662:     */     }
/*  663: 746 */     return roles;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public List<UsuarioBodega> getListaUsuarioBodega()
/*  667:     */   {
/*  668: 755 */     List<UsuarioBodega> lista = new ArrayList();
/*  669: 756 */     for (UsuarioBodega usuarioBodega : getEntidadUsuario().getListaUsuarioBodega()) {
/*  670: 757 */       if (!usuarioBodega.isEliminado()) {
/*  671: 758 */         lista.add(usuarioBodega);
/*  672:     */       }
/*  673:     */     }
/*  674: 761 */     return lista;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public List<UsuarioSucursal> getListaUsuarioSucursal()
/*  678:     */   {
/*  679: 770 */     List<UsuarioSucursal> lista = new ArrayList();
/*  680: 771 */     for (UsuarioSucursal usuarioSucursal : getEntidadUsuario().getListaUsuarioSucursal()) {
/*  681: 772 */       if (!usuarioSucursal.isEliminado()) {
/*  682: 773 */         lista.add(usuarioSucursal);
/*  683:     */       }
/*  684:     */     }
/*  685: 776 */     return lista;
/*  686:     */   }
/*  687:     */   
/*  688:     */   public List<UsuarioDimensionContable> getListaUsuarioDimensionContable()
/*  689:     */   {
/*  690: 785 */     List<UsuarioDimensionContable> lista = new ArrayList();
/*  691: 786 */     for (UsuarioDimensionContable usuarioDimensionContable : getEntidadUsuario().getListaUsuarioDimensionContable()) {
/*  692: 787 */       if (!usuarioDimensionContable.isEliminado()) {
/*  693: 788 */         lista.add(usuarioDimensionContable);
/*  694:     */       }
/*  695:     */     }
/*  696: 791 */     return lista;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public List<UsuarioOrganizacion> getListaUsuarioOrganizacion()
/*  700:     */   {
/*  701: 795 */     List<UsuarioOrganizacion> lista = new ArrayList();
/*  702: 796 */     for (UsuarioOrganizacion usuarioOrganizacion : getEntidadUsuario().getListaUsuarioOrganizacion()) {
/*  703: 797 */       if (!usuarioOrganizacion.isEliminado()) {
/*  704: 798 */         lista.add(usuarioOrganizacion);
/*  705:     */       }
/*  706:     */     }
/*  707: 801 */     return lista;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public List<Organizacion> getListaOrganizacionesNoAsignadas()
/*  711:     */   {
/*  712: 805 */     return this.listaOrganizacionesNoAsignadas;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void setListaOrganizacionesNoAsignadas(List<Organizacion> listaOrganizacionesNoAsignadas)
/*  716:     */   {
/*  717: 809 */     this.listaOrganizacionesNoAsignadas = listaOrganizacionesNoAsignadas;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public Organizacion[] getOrganizacionesSeleccionadas()
/*  721:     */   {
/*  722: 813 */     return this.organizacionesSeleccionadas;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void setOrganizacionesSeleccionadas(Organizacion[] organizacionesSeleccionadas)
/*  726:     */   {
/*  727: 817 */     this.organizacionesSeleccionadas = organizacionesSeleccionadas;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public DataTable getDtOrganizacionesAsignadas()
/*  731:     */   {
/*  732: 821 */     return this.dtOrganizacionesAsignadas;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public void setDtOrganizacionesAsignadas(DataTable dtOrganizacionesAsignadas)
/*  736:     */   {
/*  737: 825 */     this.dtOrganizacionesAsignadas = dtOrganizacionesAsignadas;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public Empleado getEmpleado()
/*  741:     */   {
/*  742: 834 */     return this.empleado;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public void setEmpleado(Empleado empleado)
/*  746:     */   {
/*  747: 844 */     this.empleado = empleado;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public List<TipoVendedor> getListaTipoVendedorCombo()
/*  751:     */   {
/*  752: 853 */     if (this.listaTipoVendedorCombo == null) {
/*  753: 854 */       this.listaTipoVendedorCombo = this.servicioTipoVendedor.obtenerListaCombo("nombre", true, null);
/*  754:     */     }
/*  755: 856 */     return this.listaTipoVendedorCombo;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public String getTipoVendedorSeleccionado()
/*  759:     */   {
/*  760: 866 */     this.tipoVendedorSeleccionado = "";
/*  761: 867 */     if (getEntidadUsuario().getTipoVendedor() != null) {
/*  762: 868 */       this.tipoVendedorSeleccionado = ("" + getEntidadUsuario().getTipoVendedor().getId());
/*  763:     */     }
/*  764: 871 */     return this.tipoVendedorSeleccionado;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public void setTipoVendedorSeleccionado(String tipoVendedorSeleccionado)
/*  768:     */   {
/*  769: 882 */     if (this.tipoVendedorSeleccionado != tipoVendedorSeleccionado)
/*  770:     */     {
/*  771: 883 */       this.tipoVendedorSeleccionado = tipoVendedorSeleccionado;
/*  772:     */       
/*  773: 885 */       TipoVendedor tipoVendedor = null;
/*  774: 887 */       if (this.tipoVendedorSeleccionado != "")
/*  775:     */       {
/*  776: 888 */         int idTipoVendedor = Integer.parseInt(this.tipoVendedorSeleccionado);
/*  777: 889 */         tipoVendedor = this.servicioTipoVendedor.buscarPorId(idTipoVendedor);
/*  778:     */       }
/*  779: 892 */       getEntidadUsuario().setTipoVendedor(tipoVendedor);
/*  780:     */     }
/*  781: 895 */     this.tipoVendedorSeleccionado = tipoVendedorSeleccionado;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public void setListaTipoVendedorCombo(List<TipoVendedor> listaTipoVendedorCombo)
/*  785:     */   {
/*  786: 905 */     this.listaTipoVendedorCombo = listaTipoVendedorCombo;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public boolean isIndicadorAgentecomercial()
/*  790:     */   {
/*  791: 914 */     return this.indicadorAgentecomercial;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public void setIndicadorAgentecomercial(boolean indicadorAgentecomercial)
/*  795:     */   {
/*  796: 924 */     this.indicadorAgentecomercial = indicadorAgentecomercial;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public void setEntidadRol(List<EntidadRol> entidadRol)
/*  800:     */   {
/*  801: 928 */     getEntidadUsuario().setListaRol(entidadRol);
/*  802:     */   }
/*  803:     */   
/*  804:     */   public DataTable getDtEntidadRol()
/*  805:     */   {
/*  806: 932 */     return this.dtEntidadRol;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public void setDtEntidadRol(DataTable dtEntidadRol)
/*  810:     */   {
/*  811: 936 */     this.dtEntidadRol = dtEntidadRol;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public RolBean getRolBean()
/*  815:     */   {
/*  816: 940 */     return this.rolBean;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public void setRolBean(RolBean rolBean)
/*  820:     */   {
/*  821: 944 */     this.rolBean = rolBean;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public EntidadRol[] getRolesSeleccionados()
/*  825:     */   {
/*  826: 953 */     return this.rolesSeleccionados;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public void setRolesSeleccionados(EntidadRol[] rolesSeleccionados)
/*  830:     */   {
/*  831: 963 */     this.rolesSeleccionados = rolesSeleccionados;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public boolean isMostrarRoles()
/*  835:     */   {
/*  836: 972 */     this.mostrarRoles = false;
/*  837: 973 */     if (this.entidadUsuario.getId() > 0) {
/*  838: 974 */       this.mostrarRoles = true;
/*  839:     */     }
/*  840: 976 */     return this.mostrarRoles;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void setMostrarRoles(boolean mostrarRoles)
/*  844:     */   {
/*  845: 986 */     this.mostrarRoles = mostrarRoles;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public DataTable getDtRolesAsignados()
/*  849:     */   {
/*  850: 995 */     return this.dtRolesAsignados;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public void setDtRolesAsignados(DataTable dtRolesAsignados)
/*  854:     */   {
/*  855:1005 */     this.dtRolesAsignados = dtRolesAsignados;
/*  856:     */   }
/*  857:     */   
/*  858:     */   public List<EntidadRol> getListaRol()
/*  859:     */   {
/*  860:1009 */     List<EntidadRol> lista = new ArrayList();
/*  861:1011 */     for (EntidadRol entidadRol : getEntidadUsuario().getListaRol()) {
/*  862:1012 */       if (!entidadRol.isEliminado()) {
/*  863:1013 */         lista.add(entidadRol);
/*  864:     */       }
/*  865:     */     }
/*  866:1016 */     return lista;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public List<EntidadRol> getListaRolesNoAsignados()
/*  870:     */   {
/*  871:1025 */     return this.listaRolesNoAsignados;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setListaRolesNoAsignados(List<EntidadRol> listaRolesNoAsignados)
/*  875:     */   {
/*  876:1035 */     this.listaRolesNoAsignados = listaRolesNoAsignados;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public List<Tema> getListaTema()
/*  880:     */   {
/*  881:1044 */     if (this.listaTema == null) {
/*  882:1045 */       this.listaTema = this.servicioTema.obtenerListaCombo("nombre", true, null);
/*  883:     */     }
/*  884:1047 */     return this.listaTema;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void setListaTema(List<Tema> listaTema)
/*  888:     */   {
/*  889:1057 */     this.listaTema = listaTema;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public List<Bodega> getListaBodegaNoAsignados()
/*  893:     */   {
/*  894:1066 */     return this.listaBodegaNoAsignados;
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void setListaBodegaNoAsignados(List<Bodega> listaBodegaNoAsignados)
/*  898:     */   {
/*  899:1076 */     this.listaBodegaNoAsignados = listaBodegaNoAsignados;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public Bodega[] getBodegasSeleccionadas()
/*  903:     */   {
/*  904:1085 */     return this.bodegasSeleccionadas;
/*  905:     */   }
/*  906:     */   
/*  907:     */   public void setBodegasSeleccionadas(Bodega[] bodegasSeleccionadas)
/*  908:     */   {
/*  909:1095 */     this.bodegasSeleccionadas = bodegasSeleccionadas;
/*  910:     */   }
/*  911:     */   
/*  912:     */   public DataTable getDtBodegasAsignadas()
/*  913:     */   {
/*  914:1104 */     return this.dtBodegasAsignadas;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public void setDtBodegasAsignadas(DataTable dtBodegasAsignadas)
/*  918:     */   {
/*  919:1114 */     this.dtBodegasAsignadas = dtBodegasAsignadas;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public List<Sucursal> getListaSucursalesNoAsignadas()
/*  923:     */   {
/*  924:1123 */     return this.listaSucursalesNoAsignadas;
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void setListaSucursalesNoAsignadas(List<Sucursal> listaSucursalesNoAsignadas)
/*  928:     */   {
/*  929:1133 */     this.listaSucursalesNoAsignadas = listaSucursalesNoAsignadas;
/*  930:     */   }
/*  931:     */   
/*  932:     */   public Sucursal[] getSucursalesSeleccionadas()
/*  933:     */   {
/*  934:1142 */     return this.sucursalesSeleccionadas;
/*  935:     */   }
/*  936:     */   
/*  937:     */   public void setSucursalesSeleccionadas(Sucursal[] sucursalesSeleccionadas)
/*  938:     */   {
/*  939:1152 */     this.sucursalesSeleccionadas = sucursalesSeleccionadas;
/*  940:     */   }
/*  941:     */   
/*  942:     */   public DataTable getDtSucursalesAsignadas()
/*  943:     */   {
/*  944:1161 */     return this.dtSucursalesAsignadas;
/*  945:     */   }
/*  946:     */   
/*  947:     */   public void setDtSucursalesAsignadas(DataTable dtSucursalesAsignadas)
/*  948:     */   {
/*  949:1171 */     this.dtSucursalesAsignadas = dtSucursalesAsignadas;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public List<Organizacion> getListaOrganizacion()
/*  953:     */   {
/*  954:1178 */     if (this.listaOrganizacion == null) {
/*  955:1179 */       this.listaOrganizacion = this.servicioOrganizacion.obtenerListaCombo("razonSocial", true, null);
/*  956:     */     }
/*  957:1181 */     return this.listaOrganizacion;
/*  958:     */   }
/*  959:     */   
/*  960:     */   public void setListaOrganizacion(List<Organizacion> listaOrganizacion)
/*  961:     */   {
/*  962:1189 */     this.listaOrganizacion = listaOrganizacion;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public List<Recaudador> getListaRecaudador()
/*  966:     */   {
/*  967:1193 */     if (this.listaRecaudador == null) {
/*  968:1194 */       this.listaRecaudador = this.servicioRecaudador.obtenerListaCombo("nombre", true, null);
/*  969:     */     }
/*  970:1196 */     return this.listaRecaudador;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public void setListaRecaudador(List<Recaudador> listaRecaudador)
/*  974:     */   {
/*  975:1200 */     this.listaRecaudador = listaRecaudador;
/*  976:     */   }
/*  977:     */   
/*  978:     */   public DimensionContable[] getDimensionesSeleccionadas()
/*  979:     */   {
/*  980:1204 */     return this.dimensionesSeleccionadas;
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void setDimensionesSeleccionadas(DimensionContable[] dimensionesSeleccionadas)
/*  984:     */   {
/*  985:1208 */     this.dimensionesSeleccionadas = dimensionesSeleccionadas;
/*  986:     */   }
/*  987:     */   
/*  988:     */   public DataTable getDtDimensionPresupuesto()
/*  989:     */   {
/*  990:1212 */     return this.dtDimensionPresupuesto;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setDtDimensionPresupuesto(DataTable dtDimensionPresupuesto)
/*  994:     */   {
/*  995:1216 */     this.dtDimensionPresupuesto = dtDimensionPresupuesto;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public DataTable getDtDimensionNoPresupuesto()
/*  999:     */   {
/* 1000:1220 */     return this.dtDimensionNoPresupuesto;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void setDtDimensionNoPresupuesto(DataTable dtDimensionNoPresupuesto)
/* 1004:     */   {
/* 1005:1224 */     this.dtDimensionNoPresupuesto = dtDimensionNoPresupuesto;
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public DataTable getDtSuperioresAsignados()
/* 1009:     */   {
/* 1010:1228 */     return this.dtSuperioresAsignados;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void setDtSuperioresAsignados(DataTable dtSuperioresAsignados)
/* 1014:     */   {
/* 1015:1232 */     this.dtSuperioresAsignados = dtSuperioresAsignados;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public void agregarUsuarioSuperior()
/* 1019:     */   {
/* 1020:1236 */     UsuarioSuperior usuarioSuperior = new UsuarioSuperior();
/* 1021:1237 */     usuarioSuperior.setEntidadUsuario(this.entidadUsuario);
/* 1022:1238 */     this.entidadUsuario.getListaUsuarioSuperior().add(usuarioSuperior);
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public List<UsuarioSuperior> getListaUsuarioSuperior()
/* 1026:     */   {
/* 1027:1242 */     List<UsuarioSuperior> lista = new ArrayList();
/* 1028:1243 */     for (UsuarioSuperior usuarioSuperior : this.entidadUsuario.getListaUsuarioSuperior()) {
/* 1029:1244 */       if (!usuarioSuperior.isEliminado()) {
/* 1030:1245 */         lista.add(usuarioSuperior);
/* 1031:     */       }
/* 1032:     */     }
/* 1033:1248 */     return lista;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void eliminarSuperior()
/* 1037:     */   {
/* 1038:1252 */     UsuarioSuperior usuarioSuperior = (UsuarioSuperior)this.dtSuperioresAsignados.getRowData();
/* 1039:1253 */     usuarioSuperior.setEliminado(true);
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public List<DocumentoBase> getListaDocumentoBaseCombo()
/* 1043:     */   {
/* 1044:1257 */     if (this.listaDocumentoBaseCombo == null) {
/* 1045:1259 */       this.listaDocumentoBaseCombo = this.servicioUsuario.getListaProcesosJerarquia();
/* 1046:     */     }
/* 1047:1261 */     return this.listaDocumentoBaseCombo;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void setListaDocumentoBaseCombo(List<DocumentoBase> listaDocumentoBaseCombo)
/* 1051:     */   {
/* 1052:1265 */     this.listaDocumentoBaseCombo = listaDocumentoBaseCombo;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public List<EntidadUsuario> autocompletarSuperior(String consulta)
/* 1056:     */   {
/* 1057:1269 */     return this.servicioUsuario.autocompletarSuperiores(consulta, this.entidadUsuario);
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public boolean isIndicadorAscendenteJerarquia()
/* 1061:     */   {
/* 1062:1273 */     return this.indicadorAscendenteJerarquia;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public void setIndicadorAscendenteJerarquia(boolean indicadorAscendenteJerarquia)
/* 1066:     */   {
/* 1067:1277 */     this.indicadorAscendenteJerarquia = indicadorAscendenteJerarquia;
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public void cargarJerarquia()
/* 1071:     */   {
/* 1072:1281 */     if ((getEntidadUsuario() != null) && (getEntidadUsuario().getId() != 0))
/* 1073:     */     {
/* 1074:1282 */       actualizarArbol(true);
/* 1075:     */       
/* 1076:1284 */       RequestContext context = RequestContext.getCurrentInstance();
/* 1077:1285 */       context.execute("jerarquiaDialog.show()");
/* 1078:     */     }
/* 1079:     */     else
/* 1080:     */     {
/* 1081:1287 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   private String getTextoArbol(EntidadUsuario usuario)
/* 1086:     */   {
/* 1087:1292 */     String textoArbol = "(" + usuario.getNombreUsuario() + ") ";
/* 1088:1293 */     textoArbol = textoArbol + usuario.getNombre1() + " " + usuario.getNombre2();
/* 1089:1294 */     if (usuario.getProceso() != null) {
/* 1090:1295 */       textoArbol = textoArbol + " - " + usuario.getProceso().getNombre();
/* 1091:     */     }
/* 1092:1297 */     return textoArbol;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public void actualizarArbol(boolean indicadorAscendente)
/* 1096:     */   {
/* 1097:1301 */     this.indicadorAscendenteJerarquia = indicadorAscendente;
/* 1098:     */     
/* 1099:1303 */     NodoArbol<EntidadUsuario> nodoArbol = null;
/* 1100:     */     try
/* 1101:     */     {
/* 1102:1305 */       nodoArbol = this.servicioUsuario.obtenerJerarquiaUsuario(this.entidadUsuario, null, this.entidadUsuario, indicadorAscendente);
/* 1103:     */     }
/* 1104:     */     catch (ExcepcionAS2 e)
/* 1105:     */     {
/* 1106:1307 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 1107:1308 */       return;
/* 1108:     */     }
/* 1109:1310 */     this.root = new DefaultTreeNode(getTextoArbol((EntidadUsuario)nodoArbol.getValor()), null);
/* 1110:1311 */     this.root.setExpanded(true);
/* 1111:1312 */     for (NodoArbol<EntidadUsuario> nodo : nodoArbol.getHijos()) {
/* 1112:1313 */       crearArbolRecusivo(nodo, this.root);
/* 1113:     */     }
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public void crearArbolRecusivo(NodoArbol<EntidadUsuario> nodo, TreeNode padre)
/* 1117:     */   {
/* 1118:1318 */     TreeNode root1 = new DefaultTreeNode(getTextoArbol((EntidadUsuario)nodo.getValor()), padre);
/* 1119:1319 */     root1.setExpanded(true);
/* 1120:1320 */     padre.getChildren().add(root1);
/* 1121:1321 */     for (NodoArbol<EntidadUsuario> nodoHijo : nodo.getHijos()) {
/* 1122:1322 */       crearArbolRecusivo(nodoHijo, root1);
/* 1123:     */     }
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public TreeNode getRoot()
/* 1127:     */   {
/* 1128:1327 */     return this.root;
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public void setRoot(TreeNode root)
/* 1132:     */   {
/* 1133:1331 */     this.root = root;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public List<TipoVisualizacionEnum> getListaTipoVisualizacionCombo()
/* 1137:     */   {
/* 1138:1335 */     if (this.listaTipoVisualizacionCombo == null)
/* 1139:     */     {
/* 1140:1336 */       this.listaTipoVisualizacionCombo = new ArrayList();
/* 1141:1337 */       for (TipoVisualizacionEnum tipo : TipoVisualizacionEnum.values()) {
/* 1142:1338 */         this.listaTipoVisualizacionCombo.add(tipo);
/* 1143:     */       }
/* 1144:     */     }
/* 1145:1341 */     return this.listaTipoVisualizacionCombo;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public List<PlanComision> getListaPlanComision()
/* 1149:     */   {
/* 1150:1345 */     if (this.listaPlanComision == null)
/* 1151:     */     {
/* 1152:1346 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1153:1347 */       filtros.put("activo", "true");
/* 1154:1348 */       this.listaPlanComision = this.servicioPlanComision.obtenerListaCombo("nombre", true, filtros);
/* 1155:     */     }
/* 1156:1350 */     return this.listaPlanComision;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public List<Dispositivo> getListaDispositivo()
/* 1160:     */   {
/* 1161:1354 */     if (this.listaDispositivo == null)
/* 1162:     */     {
/* 1163:1355 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1164:1356 */       filtros.put("activo", "true");
/* 1165:1357 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 1166:     */     }
/* 1167:1359 */     return this.listaDispositivo;
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public List<Visualizacion> getListaVisualizacionUsuario()
/* 1171:     */   {
/* 1172:1363 */     if (this.listaVisualizacionUsuario == null)
/* 1173:     */     {
/* 1174:1364 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1175:1365 */       filtros.put("activo", "true");
/* 1176:1366 */       this.listaVisualizacionUsuario = this.servicioVisualizacion.obtenerListaCombo(Visualizacion.class, "nombre", true, filtros);
/* 1177:     */     }
/* 1178:1368 */     return this.listaVisualizacionUsuario;
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   public List<DimensionContable> getListaDimensionContablesSeleccion()
/* 1182:     */   {
/* 1183:1373 */     OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 1184:1374 */     List<DimensionContable> lista = new ArrayList();
/* 1185:1375 */     Set<Integer> keysDimensionContable = new HashSet();
/* 1186:1377 */     if (this.indicadorPresupuesto)
/* 1187:     */     {
/* 1188:1379 */       for (UsuarioDimensionContable usuarioDimensionContable : getEntidadUsuario().getListaDimensionContablesPresupuesto()) {
/* 1189:1380 */         keysDimensionContable.add(Integer.valueOf(usuarioDimensionContable.getDimensionContable().getId()));
/* 1190:     */       }
/* 1191:1382 */       for (DimensionContable dimensionContable : getListaDimensionContablePresupuesto()) {
/* 1192:1383 */         if ((!keysDimensionContable.contains(Integer.valueOf(dimensionContable.getId()))) && 
/* 1193:1384 */           (dimensionContable.getNumero().equals(getNumeroDimension()))) {
/* 1194:1385 */           lista.add(dimensionContable);
/* 1195:     */         }
/* 1196:     */       }
/* 1197:     */     }
/* 1198:     */     else
/* 1199:     */     {
/* 1200:1391 */       for (UsuarioDimensionContable usuarioDimensionContable : getEntidadUsuario().getListaDimensionContablesNoPresupuesto()) {
/* 1201:1392 */         keysDimensionContable.add(Integer.valueOf(usuarioDimensionContable.getDimensionContable().getId()));
/* 1202:     */       }
/* 1203:1394 */       for (DimensionContable dimensionContable : getListaDimensionContable()) {
/* 1204:1395 */         if ((!keysDimensionContable.contains(Integer.valueOf(dimensionContable.getId()))) && 
/* 1205:1396 */           (dimensionContable.getNumero().equals(getNumeroDimension()))) {
/* 1206:1397 */           lista.add(dimensionContable);
/* 1207:     */         }
/* 1208:     */       }
/* 1209:     */     }
/* 1210:1402 */     return lista;
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public List<DimensionContable> getListaDimensionContable()
/* 1214:     */   {
/* 1215:1406 */     if (this.listaDimensionContable == null)
/* 1216:     */     {
/* 1217:1407 */       HashMap<String, String> filtros = new HashMap();
/* 1218:1408 */       filtros.put("activo", "true");
/* 1219:1409 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 1220:1410 */       this.listaDimensionContable = this.servicioDimensionContable.obtenerListaCombo("codigo", true, filtros);
/* 1221:     */     }
/* 1222:1412 */     Collections.sort(this.listaDimensionContable, new Comparator()
/* 1223:     */     {
/* 1224:     */       public int compare(DimensionContable o1, DimensionContable o2)
/* 1225:     */       {
/* 1226:1415 */         return (o1.getNumero() + "_" + o1.getCodigo()).compareTo(o2.getNumero() + "_" + o2.getCodigo());
/* 1227:     */       }
/* 1228:1417 */     });
/* 1229:1418 */     return this.listaDimensionContable;
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public List<DimensionContable> getListaDimensionContablePresupuesto()
/* 1233:     */   {
/* 1234:1422 */     if (this.listaDimensionContablePresupuesto == null)
/* 1235:     */     {
/* 1236:1423 */       HashMap<String, String> filtros = new HashMap();
/* 1237:1424 */       filtros.put("activo", "true");
/* 1238:1425 */       filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 1239:1426 */       filtros.put("numero", getDimensionPresupuesto());
/* 1240:1427 */       this.listaDimensionContablePresupuesto = this.servicioDimensionContable.obtenerListaCombo("codigo", true, filtros);
/* 1241:     */     }
/* 1242:1429 */     Collections.sort(this.listaDimensionContablePresupuesto, new Comparator()
/* 1243:     */     {
/* 1244:     */       public int compare(DimensionContable o1, DimensionContable o2)
/* 1245:     */       {
/* 1246:1432 */         return (o1.getNumero() + "_" + o1.getCodigo()).compareTo(o2.getNumero() + "_" + o2.getCodigo());
/* 1247:     */       }
/* 1248:1434 */     });
/* 1249:1435 */     return this.listaDimensionContablePresupuesto;
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public List<SelectItem> getListaDimensionContableItems()
/* 1253:     */   {
/* 1254:1439 */     if (this.listaDimensionContableItems == null)
/* 1255:     */     {
/* 1256:1440 */       this.listaDimensionContableItems = new ArrayList();
/* 1257:1441 */       OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 1258:1442 */       if (!aux.getNombreDimension1().equals("")) {
/* 1259:1443 */         this.listaDimensionContableItems.add(new SelectItem("1", aux.getNombreDimension1()));
/* 1260:     */       }
/* 1261:1445 */       if (!aux.getNombreDimension2().equals("")) {
/* 1262:1446 */         this.listaDimensionContableItems.add(new SelectItem("2", aux.getNombreDimension2()));
/* 1263:     */       }
/* 1264:1448 */       if (!aux.getNombreDimension3().equals("")) {
/* 1265:1449 */         this.listaDimensionContableItems.add(new SelectItem("3", aux.getNombreDimension3()));
/* 1266:     */       }
/* 1267:1451 */       if (!aux.getNombreDimension4().equals("")) {
/* 1268:1452 */         this.listaDimensionContableItems.add(new SelectItem("4", aux.getNombreDimension4()));
/* 1269:     */       }
/* 1270:1454 */       if (!aux.getNombreDimension5().equals("")) {
/* 1271:1455 */         this.listaDimensionContableItems.add(new SelectItem("5", aux.getNombreDimension5()));
/* 1272:     */       }
/* 1273:     */     }
/* 1274:1458 */     return this.listaDimensionContableItems;
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public void setListaDimensionContableItems(List<SelectItem> listaDimensionContableItems)
/* 1278:     */   {
/* 1279:1462 */     this.listaDimensionContableItems = listaDimensionContableItems;
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   public boolean isIndicadorPresupuesto()
/* 1283:     */   {
/* 1284:1466 */     return this.indicadorPresupuesto;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public void setIndicadorPresupuesto(boolean indicadorPresupuesto)
/* 1288:     */   {
/* 1289:1470 */     this.indicadorPresupuesto = indicadorPresupuesto;
/* 1290:     */   }
/* 1291:     */   
/* 1292:     */   public String getNumeroDimension()
/* 1293:     */   {
/* 1294:1474 */     if (this.numeroDimension == null)
/* 1295:     */     {
/* 1296:1475 */       OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 1297:1476 */       if (!aux.getNombreDimension1().equals("")) {
/* 1298:1477 */         this.numeroDimension = "1";
/* 1299:1478 */       } else if (!aux.getNombreDimension2().equals("")) {
/* 1300:1479 */         this.numeroDimension = "2";
/* 1301:1480 */       } else if (!aux.getNombreDimension3().equals("")) {
/* 1302:1481 */         this.numeroDimension = "3";
/* 1303:1482 */       } else if (!aux.getNombreDimension4().equals("")) {
/* 1304:1483 */         this.numeroDimension = "4";
/* 1305:1484 */       } else if (!aux.getNombreDimension5().equals("")) {
/* 1306:1485 */         this.numeroDimension = "5";
/* 1307:     */       }
/* 1308:     */     }
/* 1309:1488 */     return this.numeroDimension;
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public void setNumeroDimension(String numeroDimension)
/* 1313:     */   {
/* 1314:1492 */     this.numeroDimension = numeroDimension;
/* 1315:     */   }
/* 1316:     */   
/* 1317:     */   public List<UsuarioDimensionContable> getListaDimensionContablesNoPresupuesto()
/* 1318:     */   {
/* 1319:1496 */     List<UsuarioDimensionContable> lista = new ArrayList();
/* 1320:1497 */     for (UsuarioDimensionContable usuarioDimensionContable : getEntidadUsuario().getListaDimensionContablesNoPresupuesto()) {
/* 1321:1498 */       if (usuarioDimensionContable.getDimensionContable().getNumero().equals(getNumeroDimension())) {
/* 1322:1499 */         lista.add(usuarioDimensionContable);
/* 1323:     */       }
/* 1324:     */     }
/* 1325:1502 */     return lista;
/* 1326:     */   }
/* 1327:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.configuracion.cotroller.UsuarioBean
 * JD-Core Version:    0.7.0.1
 */