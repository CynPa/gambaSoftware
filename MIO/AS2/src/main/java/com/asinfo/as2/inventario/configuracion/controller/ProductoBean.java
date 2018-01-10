/*    1:     */ package com.asinfo.as2.inventario.configuracion.controller;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*    4:     */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*    5:     */ import com.asinfo.as2.controller.LanguageController;
/*    6:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    7:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    8:     */ import com.asinfo.as2.entities.Atributo;
/*    9:     */ import com.asinfo.as2.entities.Bodega;
/*   10:     */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*   11:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   12:     */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*   13:     */ import com.asinfo.as2.entities.Empresa;
/*   14:     */ import com.asinfo.as2.entities.MarcaProducto;
/*   15:     */ import com.asinfo.as2.entities.Organizacion;
/*   16:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   17:     */ import com.asinfo.as2.entities.PresentacionProducto;
/*   18:     */ import com.asinfo.as2.entities.Producto;
/*   19:     */ import com.asinfo.as2.entities.ProductoAtributo;
/*   20:     */ import com.asinfo.as2.entities.ProductoBodega;
/*   21:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   22:     */ import com.asinfo.as2.entities.ProductoSustituto;
/*   23:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   24:     */ import com.asinfo.as2.entities.Sucursal;
/*   25:     */ import com.asinfo.as2.entities.Unidad;
/*   26:     */ import com.asinfo.as2.entities.UnidadConversion;
/*   27:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   28:     */ import com.asinfo.as2.entities.produccion.MezclaProducto;
/*   29:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*   30:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
/*   31:     */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   32:     */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*   33:     */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*   34:     */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*   35:     */ import com.asinfo.as2.enumeraciones.TipoMaterialEnum;
/*   36:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   37:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   38:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   39:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*   40:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*   41:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   42:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   43:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*   44:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioValorAtributo;
/*   45:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   46:     */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcaProducto;
/*   47:     */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*   48:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   49:     */ import com.asinfo.as2.util.AppUtil;
/*   50:     */ import com.asinfo.as2.util.RutaArchivo;
/*   51:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   52:     */ import java.io.BufferedInputStream;
/*   53:     */ import java.io.File;
/*   54:     */ import java.io.FileOutputStream;
/*   55:     */ import java.io.InputStream;
/*   56:     */ import java.io.PrintStream;
/*   57:     */ import java.io.Serializable;
/*   58:     */ import java.math.BigDecimal;
/*   59:     */ import java.math.RoundingMode;
/*   60:     */ import java.util.ArrayList;
/*   61:     */ import java.util.HashMap;
/*   62:     */ import java.util.List;
/*   63:     */ import java.util.Map;
/*   64:     */ import javax.annotation.PostConstruct;
/*   65:     */ import javax.ejb.EJB;
/*   66:     */ import javax.faces.application.FacesMessage;
/*   67:     */ import javax.faces.bean.ManagedBean;
/*   68:     */ import javax.faces.bean.ManagedProperty;
/*   69:     */ import javax.faces.bean.ViewScoped;
/*   70:     */ import javax.faces.component.html.HtmlInputText;
/*   71:     */ import javax.faces.context.FacesContext;
/*   72:     */ import javax.faces.event.AjaxBehaviorEvent;
/*   73:     */ import javax.faces.model.SelectItem;
/*   74:     */ import org.apache.log4j.Logger;
/*   75:     */ import org.primefaces.component.datatable.DataTable;
/*   76:     */ import org.primefaces.event.FileUploadEvent;
/*   77:     */ import org.primefaces.event.SelectEvent;
/*   78:     */ import org.primefaces.model.LazyDataModel;
/*   79:     */ import org.primefaces.model.SortOrder;
/*   80:     */ import org.primefaces.model.UploadedFile;
/*   81:     */ 
/*   82:     */ @ManagedBean
/*   83:     */ @ViewScoped
/*   84:     */ public class ProductoBean
/*   85:     */   extends PageControllerAS2
/*   86:     */   implements Serializable
/*   87:     */ {
/*   88:     */   private static final long serialVersionUID = -4585439120860530199L;
/*   89:     */   @EJB
/*   90:     */   private ServicioProducto servicioProducto;
/*   91:     */   @EJB
/*   92:     */   private ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/*   93:     */   @EJB
/*   94:     */   private ServicioConjuntoAtributo servicioConjuntoAtributo;
/*   95:     */   @EJB
/*   96:     */   private ServicioUnidad servicioUnidad;
/*   97:     */   @EJB
/*   98:     */   private ServicioBodega servicioBodega;
/*   99:     */   @EJB
/*  100:     */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  101:     */   @EJB
/*  102:     */   private ServicioEmpresa servicioEmpresa;
/*  103:     */   @EJB
/*  104:     */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  105:     */   @EJB
/*  106:     */   private ServicioMarcaProducto servicioMarcaProducto;
/*  107:     */   @EJB
/*  108:     */   private ServicioGenerico<PresentacionProducto> servicioPresentacionProducto;
/*  109:     */   @EJB
/*  110:     */   private ServicioSucursal servicioSucursal;
/*  111:     */   @EJB
/*  112:     */   private ServicioValorAtributo servicioValorAtributo;
/*  113:     */   @EJB
/*  114:     */   private ServicioOrganizacion servicioOrganizacion;
/*  115:     */   private Producto producto;
/*  116:     */   private LazyDataModel<Producto> listaProducto;
/*  117:     */   private ProductoRutaFabricacion productoRutaFabricacion;
/*  118:     */   private UploadedFile uploadedFile;
/*  119:     */   private List<SelectItem> listaTipoMaterial;
/*  120:     */   private List<SelectItem> listaTipoCosto;
/*  121:     */   private List<CategoriaImpuesto> listaCategoriaImpuesto;
/*  122:     */   private List<Unidad> listaUnidad;
/*  123:     */   private List<Bodega> listaBodega;
/*  124:     */   private List<Sucursal> listaSucursal;
/*  125:     */   private List<ConjuntoAtributo> listaConjuntoAtributo;
/*  126:     */   private List<MarcaProducto> listaMarcaProducto;
/*  127:     */   private List<PresentacionProducto> listaPresentacionProducto;
/*  128:     */   private List<MezclaProducto> listaMezclaProducto;
/*  129:     */   private DataTable dtProducto;
/*  130:     */   private DataTable dtMateriales;
/*  131:     */   private DataTable dtProductoSustituto;
/*  132:     */   private DataTable dtUnidadConversion;
/*  133:     */   private List<SelectItem> productoItems;
/*  134:     */   private SelectItem[] listaTipoProductoItem;
/*  135:     */   private List<BodegaTrabajoProductoSucursal> listaBodegaTrabajoProductoSucursal;
/*  136:     */   private Producto productoSeleccionado;
/*  137:     */   private ProductoMaterial materialSeleccionado;
/*  138:     */   private ProductoSustituto sustitutoSeleccionado;
/*  139:     */   private MezclaProducto mezclaProductoSeleccionado;
/*  140:     */   @ManagedProperty("#{listaProductoBean}")
/*  141:     */   private ListaProductoBean listaProductoBean;
/*  142:     */   
/*  143:     */   @PostConstruct
/*  144:     */   public void init()
/*  145:     */   {
/*  146: 171 */     this.listaProducto = new LazyDataModel()
/*  147:     */     {
/*  148:     */       private static final long serialVersionUID = 1L;
/*  149:     */       
/*  150:     */       public List<Producto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  151:     */       {
/*  152: 182 */         List<Producto> lista = new ArrayList();
/*  153:     */         
/*  154: 184 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  155:     */         try
/*  156:     */         {
/*  157: 187 */           lista = ProductoBean.this.servicioProducto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  158: 188 */           ProductoBean.this.listaProducto.setRowCount(ProductoBean.this.servicioProducto.contarPorCriterio(filters));
/*  159:     */         }
/*  160:     */         catch (ExcepcionAS2Inventario e)
/*  161:     */         {
/*  162: 190 */           ProductoBean.LOG.error("ERROR AL CARGAR LOS DATOS PRODUCTO", e);
/*  163:     */         }
/*  164: 192 */         return lista;
/*  165:     */       }
/*  166:     */     };
/*  167:     */   }
/*  168:     */   
/*  169:     */   public void crearProducto()
/*  170:     */   {
/*  171: 203 */     this.producto = new Producto();
/*  172: 204 */     this.producto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  173: 205 */     this.producto.setIdSucursal(AppUtil.getSucursal().getId());
/*  174: 206 */     this.producto.setListaUnidadConversion(new ArrayList());
/*  175: 207 */     this.producto.getListaProductoBodega().addAll(this.servicioProducto.getListaProductoBodegaVacios(this.producto));
/*  176: 208 */     this.listaMezclaProducto = null;
/*  177:     */   }
/*  178:     */   
/*  179:     */   public List<ValorAtributo> autocompletarValorAtributo(String consulta, Atributo atributo)
/*  180:     */   {
/*  181: 212 */     if (atributo != null) {
/*  182: 213 */       return this.servicioValorAtributo.autocompletarValorAtributo(consulta, atributo);
/*  183:     */     }
/*  184: 215 */     List<ValorAtributo> listaVacia = new ArrayList();
/*  185: 216 */     return listaVacia;
/*  186:     */   }
/*  187:     */   
/*  188:     */   public String editar()
/*  189:     */   {
/*  190: 228 */     if ((this.producto != null) && (this.producto.getIdProducto() != 0))
/*  191:     */     {
/*  192: 229 */       this.producto = this.servicioProducto.cargaDetalle(this.producto.getId());
/*  193: 230 */       actualizarAtributos();
/*  194: 231 */       cargarAtributosInstancia(this.producto);
/*  195: 232 */       actualizarAtributosIntancia();
/*  196: 233 */       this.producto.getListaProductoBodega().addAll(this.servicioProducto.getListaProductoBodegaVacios(this.producto));
/*  197: 234 */       this.producto.setListaProductoRutaFabricacionSucursal(this.servicioProducto.getListaProductoRutaFabricacionSucursal(this.producto.getIdProducto()));
/*  198: 235 */       this.servicioProducto.cargarDetalleListaMezclaProducto(this.producto);
/*  199: 236 */       this.listaMezclaProducto = null;
/*  200: 237 */       setEditado(true);
/*  201:     */     }
/*  202:     */     else
/*  203:     */     {
/*  204: 239 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  205:     */     }
/*  206: 242 */     return "";
/*  207:     */   }
/*  208:     */   
/*  209:     */   public String guardar()
/*  210:     */   {
/*  211:     */     try
/*  212:     */     {
/*  213: 253 */       this.servicioProducto.guardar(this.producto);
/*  214: 254 */       limpiar();
/*  215: 255 */       setEditado(false);
/*  216: 256 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  217:     */     }
/*  218:     */     catch (ExcepcionAS2Inventario e)
/*  219:     */     {
/*  220: 258 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  221:     */     }
/*  222:     */     catch (Exception e)
/*  223:     */     {
/*  224: 260 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  225: 261 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  226:     */     }
/*  227: 264 */     return "";
/*  228:     */   }
/*  229:     */   
/*  230:     */   public String eliminar()
/*  231:     */   {
/*  232:     */     try
/*  233:     */     {
/*  234: 275 */       this.servicioProducto.eliminar(this.producto);
/*  235: 276 */       limpiar();
/*  236: 277 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  237:     */     }
/*  238:     */     catch (ExcepcionAS2Inventario e)
/*  239:     */     {
/*  240: 279 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  241: 280 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  242:     */     }
/*  243:     */     catch (ExcepcionAS2 e)
/*  244:     */     {
/*  245: 282 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  246: 283 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  247:     */     }
/*  248:     */     catch (Exception e)
/*  249:     */     {
/*  250: 285 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  251: 286 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  252:     */     }
/*  253: 289 */     return "";
/*  254:     */   }
/*  255:     */   
/*  256:     */   public String cargarDatos()
/*  257:     */   {
/*  258: 302 */     return "";
/*  259:     */   }
/*  260:     */   
/*  261:     */   public List<Producto> autocompletarProductos(String consulta)
/*  262:     */   {
/*  263: 307 */     List<Producto> lista = new ArrayList();
/*  264: 308 */     String sortField = "nombreComercial";
/*  265: 309 */     HashMap<String, String> filters = new HashMap();
/*  266: 310 */     filters.put("codigo", consulta.trim() + "%");
/*  267:     */     try
/*  268:     */     {
/*  269: 312 */       lista = this.servicioProducto.obtenerProductos(sortField, true, filters);
/*  270:     */     }
/*  271:     */     catch (ExcepcionAS2Inventario e)
/*  272:     */     {
/*  273: 315 */       e.printStackTrace();
/*  274:     */     }
/*  275: 317 */     return lista;
/*  276:     */   }
/*  277:     */   
/*  278:     */   public String cargarItems()
/*  279:     */   {
/*  280: 324 */     if (this.productoItems == null)
/*  281:     */     {
/*  282: 325 */       this.productoItems = new ArrayList();
/*  283: 327 */       for (Producto producto : this.listaProducto)
/*  284:     */       {
/*  285: 328 */         SelectItem opcion = new SelectItem(Integer.valueOf(producto.getIdProducto()), producto.getNombre());
/*  286: 329 */         this.productoItems.add(opcion);
/*  287:     */       }
/*  288:     */     }
/*  289: 333 */     return "";
/*  290:     */   }
/*  291:     */   
/*  292:     */   public String limpiar()
/*  293:     */   {
/*  294: 343 */     crearProducto();
/*  295: 344 */     return "";
/*  296:     */   }
/*  297:     */   
/*  298:     */   public String actualizarAtributos()
/*  299:     */   {
/*  300:     */     int idConjuntoAtributo;
/*  301: 354 */     if (getProducto().getConjuntoAtributo() != null)
/*  302:     */     {
/*  303: 355 */       idConjuntoAtributo = getProducto().getConjuntoAtributo().getIdConjuntoAtributo();
/*  304: 356 */       List<Atributo> listaAtributos = this.servicioConjuntoAtributo.cargarDetalle(idConjuntoAtributo).getListaAtributo();
/*  305: 357 */       HashMap<Integer, Atributo> mapAtributos = new HashMap();
/*  306: 358 */       for (Atributo atributo : listaAtributos) {
/*  307: 359 */         mapAtributos.put(Integer.valueOf(atributo.getId()), atributo);
/*  308:     */       }
/*  309: 361 */       for (ProductoAtributo productoAtributo : getProducto().getListaProductoAtributo()) {
/*  310: 362 */         if (mapAtributos.containsKey(Integer.valueOf(productoAtributo.getAtributo().getId())))
/*  311:     */         {
/*  312: 363 */           productoAtributo.setEliminado(false);
/*  313: 364 */           mapAtributos.remove(Integer.valueOf(productoAtributo.getAtributo().getId()));
/*  314:     */         }
/*  315:     */         else
/*  316:     */         {
/*  317: 366 */           productoAtributo.setEliminado(true);
/*  318:     */         }
/*  319:     */       }
/*  320: 370 */       for (Atributo atributo : mapAtributos.values()) {
/*  321: 371 */         if (atributo.isIndicadorProducto())
/*  322:     */         {
/*  323: 372 */           ProductoAtributo pa = new ProductoAtributo(getProducto(), atributo);
/*  324: 373 */           pa.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  325: 374 */           pa.setIdSucursal(AppUtil.getSucursal().getId());
/*  326: 375 */           getProducto().getListaProductoAtributo().add(pa);
/*  327:     */         }
/*  328:     */       }
/*  329:     */     }
/*  330:     */     else
/*  331:     */     {
/*  332: 379 */       for (ProductoAtributo productoAtributo : getProducto().getListaProductoAtributo()) {
/*  333: 380 */         productoAtributo.setEliminado(true);
/*  334:     */       }
/*  335:     */     }
/*  336: 383 */     return "";
/*  337:     */   }
/*  338:     */   
/*  339:     */   public void actualizarMaterialListener(AjaxBehaviorEvent event)
/*  340:     */   {
/*  341: 393 */     ProductoMaterial pm = (ProductoMaterial)this.dtMateriales.getRowData();
/*  342:     */     
/*  343: 395 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  344:     */     try
/*  345:     */     {
/*  346: 397 */       Producto producto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  347: 398 */       pm.setMaterial(producto);
/*  348:     */     }
/*  349:     */     catch (ExcepcionAS2 e)
/*  350:     */     {
/*  351: 401 */       addInfoMessage(getLanguageController().getMensaje(e.getMessage()));
/*  352:     */     }
/*  353:     */   }
/*  354:     */   
/*  355:     */   public void cargarProducto(Producto productoCargado)
/*  356:     */   {
/*  357: 412 */     if (this.productoSeleccionado != null)
/*  358:     */     {
/*  359: 414 */       if (getProducto().getId() == 0)
/*  360:     */       {
/*  361: 415 */         productoCargado = this.servicioProducto.cargaDetalle(productoCargado.getId());
/*  362:     */         
/*  363:     */ 
/*  364: 418 */         productoCargado.setIdProducto(0);
/*  365: 419 */         productoCargado.setCodigo(productoCargado.getCodigo() + "_1");
/*  366: 420 */         productoCargado.setConjuntoAtributo(null);
/*  367: 421 */         productoCargado.getListaProductoAtributo().clear();
/*  368: 422 */         productoCargado.getListaUnidadConversion().clear();
/*  369: 423 */         productoCargado.getListaProductoSustituto().clear();
/*  370:     */         
/*  371:     */ 
/*  372: 426 */         productoCargado.getListaProductoBodega().addAll(this.servicioProducto.getListaProductoBodegaVacios(this.productoSeleccionado));
/*  373: 427 */         productoCargado.setListaProductoRutaFabricacionSucursal(this.servicioProducto.getListaProductoRutaFabricacionSucursal(this.productoSeleccionado.getIdProducto()));
/*  374: 428 */         if (productoCargado.getListaProductoBodega().size() > 0) {
/*  375: 429 */           for (ProductoBodega pb : productoCargado.getListaProductoBodega()) {
/*  376: 430 */             pb.setIdProductoBodega(0);
/*  377:     */           }
/*  378:     */         }
/*  379: 433 */         if (productoCargado.getListaProductoRutaFabricacionSucursal().size() > 0) {
/*  380: 434 */           for (ProductoRutaFabricacionSucursal prf : productoCargado.getListaProductoRutaFabricacionSucursal()) {
/*  381: 435 */             prf.setIdProductoRutaFabricacionSucursal(0);
/*  382:     */           }
/*  383:     */         }
/*  384: 439 */         for (BodegaTrabajoProductoSucursal btps : productoCargado.getListaBodegaTrabajoSucursal()) {
/*  385: 440 */           btps.setIdBodegaTrabajoProductoSucursal(0);
/*  386:     */         }
/*  387: 443 */         productoCargado.getListaProductoBodega().clear();
/*  388: 444 */         productoCargado.getListaProductoRutaFabricacionSucursal().clear();
/*  389: 445 */         productoCargado.getListaBodegaTrabajoSucursal().clear();
/*  390: 446 */         productoCargado.setPrecioUltimaVenta(BigDecimal.ZERO);
/*  391: 447 */         productoCargado.setPrecioUltimaCompra(BigDecimal.ZERO);
/*  392:     */         
/*  393: 449 */         setProducto(productoCargado);
/*  394:     */       }
/*  395:     */     }
/*  396: 452 */     else if (this.materialSeleccionado != null) {
/*  397: 453 */       this.materialSeleccionado.setMaterial(productoCargado);
/*  398: 455 */     } else if (this.sustitutoSeleccionado != null) {
/*  399: 456 */       this.sustitutoSeleccionado.setSustituto(productoCargado);
/*  400: 458 */     } else if (this.mezclaProductoSeleccionado != null) {
/*  401: 459 */       this.mezclaProductoSeleccionado.setProductoMezcla(productoCargado);
/*  402:     */     }
/*  403: 462 */     this.productoSeleccionado = null;
/*  404: 463 */     this.materialSeleccionado = null;
/*  405: 464 */     this.sustitutoSeleccionado = null;
/*  406: 465 */     this.mezclaProductoSeleccionado = null;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public void eliminarRutaFabricacion()
/*  410:     */   {
/*  411: 472 */     this.productoRutaFabricacion.setEliminado(true);
/*  412:     */   }
/*  413:     */   
/*  414:     */   public String eliminarMaterial()
/*  415:     */   {
/*  416: 480 */     ProductoMaterial productoMaterial = (ProductoMaterial)this.dtMateriales.getRowData();
/*  417: 481 */     productoMaterial.setEliminado(true);
/*  418:     */     
/*  419: 483 */     return "";
/*  420:     */   }
/*  421:     */   
/*  422:     */   public ProductoSustituto creaSustituto()
/*  423:     */   {
/*  424: 493 */     ProductoSustituto productoSustituto = new ProductoSustituto();
/*  425: 494 */     productoSustituto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  426: 495 */     productoSustituto.setSustituto(new Producto());
/*  427: 496 */     productoSustituto.setProducto(this.producto);
/*  428:     */     
/*  429: 498 */     return productoSustituto;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public void agregarSustituto()
/*  433:     */   {
/*  434: 506 */     this.producto.getListaProductoSustituto().add(creaSustituto());
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void agregarMezclaProductoListener()
/*  438:     */   {
/*  439: 510 */     MezclaProducto mp = new MezclaProducto();
/*  440: 511 */     mp.setProducto(getProducto());
/*  441: 512 */     getProducto().getListaMezclaProducto().add(mp);
/*  442: 513 */     getListaMezclaProducto().add(mp);
/*  443:     */   }
/*  444:     */   
/*  445:     */   public String eliminarSustituto()
/*  446:     */   {
/*  447: 521 */     ProductoSustituto productoSustituto = (ProductoSustituto)this.dtProductoSustituto.getRowData();
/*  448: 522 */     productoSustituto.setEliminado(true);
/*  449:     */     
/*  450: 524 */     return "";
/*  451:     */   }
/*  452:     */   
/*  453:     */   public void actualizarProductoSustitutoListener(AjaxBehaviorEvent event)
/*  454:     */   {
/*  455: 534 */     ProductoSustituto pm = (ProductoSustituto)this.dtProductoSustituto.getRowData();
/*  456:     */     
/*  457: 536 */     String codigo = ((HtmlInputText)event.getSource()).getValue().toString();
/*  458:     */     try
/*  459:     */     {
/*  460: 538 */       Producto sustituto = this.servicioProducto.buscarPorCodigo(codigo, AppUtil.getOrganizacion().getIdOrganizacion(), null);
/*  461: 539 */       pm.setSustituto(sustituto);
/*  462:     */     }
/*  463:     */     catch (ExcepcionAS2 e)
/*  464:     */     {
/*  465: 542 */       addInfoMessage(getLanguageController().getMensaje(e.getMessage()));
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   public String actualizarIndicadorImpuestos()
/*  470:     */   {
/*  471: 553 */     if (!getProducto().isIndicadorImpuestos()) {
/*  472: 554 */       getProducto().setCategoriaImpuesto(null);
/*  473:     */     }
/*  474: 557 */     return "";
/*  475:     */   }
/*  476:     */   
/*  477:     */   public void actualizarProveedor(SelectEvent event)
/*  478:     */   {
/*  479: 562 */     Empresa e = this.servicioEmpresa.buscarPorId(Integer.valueOf(((Empresa)event.getObject()).getIdEmpresa()));
/*  480: 563 */     getProducto().setEmpresa(e);
/*  481:     */   }
/*  482:     */   
/*  483:     */   public List<Unidad> autocompletarUnidades(String consulta)
/*  484:     */   {
/*  485: 575 */     List<Unidad> lista = new ArrayList();
/*  486: 576 */     HashMap<String, String> filters = new HashMap();
/*  487: 577 */     filters.put("nombre", consulta.trim());
/*  488: 578 */     lista = this.servicioUnidad.obtenerListaCombo("nombre", true, filters);
/*  489:     */     
/*  490: 580 */     return lista;
/*  491:     */   }
/*  492:     */   
/*  493:     */   public List<RutaFabricacion> autocompletarRutaFabricacion(String consulta)
/*  494:     */   {
/*  495: 591 */     List<RutaFabricacion> lista = new ArrayList();
/*  496: 592 */     HashMap<String, String> filters = new HashMap();
/*  497: 593 */     filters.put("nombre", consulta.trim());
/*  498: 594 */     lista = this.servicioRutaFabricacion.obtenerListaCombo("nombre", true, filters);
/*  499:     */     
/*  500: 596 */     return lista;
/*  501:     */   }
/*  502:     */   
/*  503:     */   public String agregarDetalleConversion()
/*  504:     */   {
/*  505: 606 */     if (getProducto().getUnidad() != null)
/*  506:     */     {
/*  507: 607 */       UnidadConversion unidadConversion = new UnidadConversion();
/*  508: 608 */       unidadConversion.setUnidadOrigen(this.producto.getUnidad());
/*  509: 609 */       unidadConversion.setProducto(getProducto());
/*  510: 610 */       getProducto().getListaUnidadConversion().add(unidadConversion);
/*  511:     */     }
/*  512:     */     else
/*  513:     */     {
/*  514: 612 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_unidad"));
/*  515:     */     }
/*  516: 615 */     return "";
/*  517:     */   }
/*  518:     */   
/*  519:     */   public String eliminarDetalleConversion()
/*  520:     */   {
/*  521: 619 */     UnidadConversion i = (UnidadConversion)this.dtUnidadConversion.getRowData();
/*  522: 620 */     i.setEliminado(true);
/*  523: 621 */     return "";
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void invertirUnidadesConversionListener()
/*  527:     */   {
/*  528: 628 */     UnidadConversion unidadConversion = (UnidadConversion)this.dtUnidadConversion.getRowData();
/*  529:     */     
/*  530: 630 */     Unidad unidadOrigen = unidadConversion.getUnidadOrigen();
/*  531: 631 */     Unidad unidadDestino = unidadConversion.getUnidadDestino();
/*  532:     */     
/*  533: 633 */     unidadConversion.setUnidadOrigen(unidadDestino);
/*  534: 634 */     unidadConversion.setUnidadDestino(unidadOrigen);
/*  535: 636 */     if (unidadConversion.getValorConversion().compareTo(BigDecimal.ZERO) != 0)
/*  536:     */     {
/*  537: 637 */       BigDecimal valorConversion = new BigDecimal(1).divide(unidadConversion.getValorConversion(), 6, RoundingMode.HALF_UP);
/*  538: 638 */       unidadConversion.setValorConversion(valorConversion);
/*  539:     */     }
/*  540:     */   }
/*  541:     */   
/*  542:     */   public List<SubcategoriaProducto> autocompletarSubcategoriaProducto(String consulta)
/*  543:     */   {
/*  544: 644 */     List<SubcategoriaProducto> lista = new ArrayList();
/*  545:     */     
/*  546: 646 */     HashMap<String, String> filters = new HashMap();
/*  547: 647 */     filters.put("nombre", "%" + consulta.trim());
/*  548:     */     
/*  549: 649 */     lista = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/*  550:     */     
/*  551: 651 */     return lista;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void actualizarAtributosIntancia()
/*  555:     */   {
/*  556: 657 */     OrganizacionConfiguracion organizacionConfiguracion = this.servicioOrganizacion.cargarDetalle(AppUtil.getOrganizacion().getId()).getOrganizacionConfiguracion();
/*  557:     */     
/*  558: 659 */     this.producto.setTraAtributo1(organizacionConfiguracion.getAtributo1());
/*  559: 660 */     this.producto.setTraAtributo2(organizacionConfiguracion.getAtributo2());
/*  560: 661 */     this.producto.setTraAtributo3(organizacionConfiguracion.getAtributo3());
/*  561: 662 */     this.producto.setTraAtributo4(organizacionConfiguracion.getAtributo4());
/*  562: 663 */     this.producto.setTraAtributo5(organizacionConfiguracion.getAtributo5());
/*  563: 664 */     this.producto.setTraAtributo6(organizacionConfiguracion.getAtributo6());
/*  564: 665 */     this.producto.setTraAtributo7(organizacionConfiguracion.getAtributo7());
/*  565: 666 */     this.producto.setTraAtributo8(organizacionConfiguracion.getAtributo8());
/*  566: 667 */     this.producto.setTraAtributo9(organizacionConfiguracion.getAtributo9());
/*  567: 668 */     this.producto.setTraAtributo10(organizacionConfiguracion.getAtributo10());
/*  568: 670 */     if (this.producto.getAtributoProduccion1() != null) {
/*  569: 671 */       this.producto.setIndicadorAtributo1(true);
/*  570:     */     }
/*  571: 673 */     if (this.producto.getAtributoProduccion2() != null) {
/*  572: 674 */       this.producto.setIndicadorAtributo2(true);
/*  573:     */     }
/*  574: 676 */     if (this.producto.getAtributoProduccion3() != null) {
/*  575: 677 */       this.producto.setIndicadorAtributo3(true);
/*  576:     */     }
/*  577: 679 */     if (this.producto.getAtributoProduccion4() != null) {
/*  578: 680 */       this.producto.setIndicadorAtributo4(true);
/*  579:     */     }
/*  580: 682 */     if (this.producto.getAtributoProduccion5() != null) {
/*  581: 683 */       this.producto.setIndicadorAtributo5(true);
/*  582:     */     }
/*  583: 685 */     if (this.producto.getAtributoProduccion6() != null) {
/*  584: 686 */       this.producto.setIndicadorAtributo6(true);
/*  585:     */     }
/*  586: 688 */     if (this.producto.getAtributoProduccion7() != null) {
/*  587: 689 */       this.producto.setIndicadorAtributo7(true);
/*  588:     */     }
/*  589: 691 */     if (this.producto.getAtributoProduccion8() != null) {
/*  590: 692 */       this.producto.setIndicadorAtributo8(true);
/*  591:     */     }
/*  592: 694 */     if (this.producto.getAtributoProduccion9() != null) {
/*  593: 695 */       this.producto.setIndicadorAtributo9(true);
/*  594:     */     }
/*  595: 697 */     if (this.producto.getAtributoProduccion10() != null) {
/*  596: 698 */       this.producto.setIndicadorAtributo10(true);
/*  597:     */     }
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void cargarAtributo()
/*  601:     */   {
/*  602: 705 */     this.producto.setAtributoProduccion1(null);
/*  603: 706 */     this.producto.setAtributoProduccion2(null);
/*  604: 707 */     this.producto.setAtributoProduccion3(null);
/*  605: 708 */     this.producto.setAtributoProduccion4(null);
/*  606: 709 */     this.producto.setAtributoProduccion5(null);
/*  607: 710 */     this.producto.setAtributoProduccion6(null);
/*  608: 711 */     this.producto.setAtributoProduccion7(null);
/*  609: 712 */     this.producto.setAtributoProduccion8(null);
/*  610: 713 */     this.producto.setAtributoProduccion9(null);
/*  611: 714 */     this.producto.setAtributoProduccion10(null);
/*  612: 717 */     if (this.producto.isIndicadorAtributo1()) {
/*  613: 718 */       this.producto.setAtributoProduccion1(this.producto.getTraAtributo1());
/*  614:     */     }
/*  615: 720 */     if (this.producto.isIndicadorAtributo2()) {
/*  616: 721 */       this.producto.setAtributoProduccion2(this.producto.getTraAtributo2());
/*  617:     */     }
/*  618: 723 */     if (this.producto.isIndicadorAtributo3()) {
/*  619: 724 */       this.producto.setAtributoProduccion3(this.producto.getTraAtributo3());
/*  620:     */     }
/*  621: 726 */     if (this.producto.isIndicadorAtributo4()) {
/*  622: 727 */       this.producto.setAtributoProduccion4(this.producto.getTraAtributo4());
/*  623:     */     }
/*  624: 729 */     if (this.producto.isIndicadorAtributo5()) {
/*  625: 730 */       this.producto.setAtributoProduccion5(this.producto.getTraAtributo5());
/*  626:     */     }
/*  627: 732 */     if (this.producto.isIndicadorAtributo6()) {
/*  628: 733 */       this.producto.setAtributoProduccion6(this.producto.getTraAtributo6());
/*  629:     */     }
/*  630: 735 */     if (this.producto.isIndicadorAtributo7()) {
/*  631: 736 */       this.producto.setAtributoProduccion7(this.producto.getTraAtributo7());
/*  632:     */     }
/*  633: 738 */     if (this.producto.isIndicadorAtributo8()) {
/*  634: 739 */       this.producto.setAtributoProduccion8(this.producto.getTraAtributo8());
/*  635:     */     }
/*  636: 741 */     if (this.producto.isIndicadorAtributo9()) {
/*  637: 742 */       this.producto.setAtributoProduccion9(this.producto.getTraAtributo9());
/*  638:     */     }
/*  639: 744 */     if (this.producto.isIndicadorAtributo10()) {
/*  640: 745 */       this.producto.setAtributoProduccion10(this.producto.getTraAtributo10());
/*  641:     */     }
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void cargarAtributosInstancia(Producto producto)
/*  645:     */   {
/*  646: 750 */     Producto productoAtributo = this.servicioProducto.cargarProductoConAtributoInstancia(producto.getId());
/*  647: 751 */     producto.setAtributoProduccion1(productoAtributo.getAtributoProduccion1());
/*  648: 752 */     producto.setAtributoProduccion2(productoAtributo.getAtributoProduccion2());
/*  649: 753 */     producto.setAtributoProduccion3(productoAtributo.getAtributoProduccion3());
/*  650: 754 */     producto.setAtributoProduccion4(productoAtributo.getAtributoProduccion4());
/*  651: 755 */     producto.setAtributoProduccion5(productoAtributo.getAtributoProduccion5());
/*  652: 756 */     producto.setAtributoProduccion6(productoAtributo.getAtributoProduccion6());
/*  653: 757 */     producto.setAtributoProduccion7(productoAtributo.getAtributoProduccion7());
/*  654: 758 */     producto.setAtributoProduccion8(productoAtributo.getAtributoProduccion8());
/*  655: 759 */     producto.setAtributoProduccion9(productoAtributo.getAtributoProduccion9());
/*  656: 760 */     producto.setAtributoProduccion10(productoAtributo.getAtributoProduccion10());
/*  657:     */   }
/*  658:     */   
/*  659:     */   public ServicioProducto getServicioProductoBean()
/*  660:     */   {
/*  661: 764 */     return this.servicioProducto;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void setServicioProductoBean(ServicioProducto servicioProducto)
/*  665:     */   {
/*  666: 768 */     this.servicioProducto = servicioProducto;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public Producto getProducto()
/*  670:     */   {
/*  671: 772 */     if (this.producto == null) {
/*  672: 773 */       crearProducto();
/*  673:     */     }
/*  674: 776 */     return this.producto;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public void setProducto(Producto producto)
/*  678:     */   {
/*  679: 780 */     this.producto = producto;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public LazyDataModel<Producto> getListaProducto()
/*  683:     */   {
/*  684: 784 */     return this.listaProducto;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public void setListaProducto(LazyDataModel<Producto> productos)
/*  688:     */   {
/*  689: 788 */     this.listaProducto = productos;
/*  690:     */   }
/*  691:     */   
/*  692:     */   public List<SelectItem> getProductoItems()
/*  693:     */   {
/*  694: 792 */     return this.productoItems;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void setProductoItems(List<SelectItem> productoItems)
/*  698:     */   {
/*  699: 796 */     this.productoItems = productoItems;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public List<SelectItem> getListaTipoMaterial()
/*  703:     */   {
/*  704: 800 */     if (this.listaTipoMaterial == null)
/*  705:     */     {
/*  706: 801 */       this.listaTipoMaterial = new ArrayList();
/*  707: 803 */       for (TipoMaterialEnum t : TipoMaterialEnum.values())
/*  708:     */       {
/*  709: 804 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  710: 805 */         this.listaTipoMaterial.add(item);
/*  711:     */       }
/*  712:     */     }
/*  713: 808 */     return this.listaTipoMaterial;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public List<SelectItem> getListaTipoCosto()
/*  717:     */   {
/*  718: 817 */     if (this.listaTipoCosto == null)
/*  719:     */     {
/*  720: 818 */       this.listaTipoCosto = new ArrayList();
/*  721: 819 */       for (TipoCosto t : TipoCosto.values())
/*  722:     */       {
/*  723: 820 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  724: 821 */         this.listaTipoCosto.add(item);
/*  725:     */       }
/*  726:     */     }
/*  727: 824 */     return this.listaTipoCosto;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void setListaTipoCosto(List<SelectItem> listaTipoCosto)
/*  731:     */   {
/*  732: 834 */     this.listaTipoCosto = listaTipoCosto;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public List<CategoriaImpuesto> getListaCategoriaImpuesto()
/*  736:     */   {
/*  737: 843 */     if (this.listaCategoriaImpuesto == null) {
/*  738: 844 */       this.listaCategoriaImpuesto = this.servicioCategoriaImpuesto.obtenerListaCombo("codigo", true, null);
/*  739:     */     }
/*  740: 846 */     return this.listaCategoriaImpuesto;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public void setListaCategoriaImpuesto(List<CategoriaImpuesto> listaCategoriaImpuesto)
/*  744:     */   {
/*  745: 856 */     this.listaCategoriaImpuesto = listaCategoriaImpuesto;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public DataTable getDtProducto()
/*  749:     */   {
/*  750: 860 */     return this.dtProducto;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public void setDtProducto(DataTable dtProducto)
/*  754:     */   {
/*  755: 864 */     this.dtProducto = dtProducto;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public List<ProductoAtributo> getListaProductoAtributoTipoValor()
/*  759:     */   {
/*  760: 874 */     List<ProductoAtributo> lista = new ArrayList();
/*  761: 875 */     for (ProductoAtributo productoAtributo : this.producto.getListaProductoAtributo()) {
/*  762: 876 */       if ((!productoAtributo.isEliminado()) && (!productoAtributo.getAtributo().isIndicadorInstancia()) && 
/*  763: 877 */         (productoAtributo.getAtributo().getTipoAtributo() != TipoAtributo.LISTA)) {
/*  764: 878 */         lista.add(productoAtributo);
/*  765:     */       }
/*  766:     */     }
/*  767: 882 */     return lista;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public List<ProductoAtributo> getListaProductoAtributoTipoLista()
/*  771:     */   {
/*  772: 886 */     List<ProductoAtributo> lista = new ArrayList();
/*  773: 887 */     for (ProductoAtributo productoAtributo : this.producto.getListaProductoAtributo()) {
/*  774: 888 */       if ((!productoAtributo.isEliminado()) && (!productoAtributo.getAtributo().isIndicadorInstancia()) && 
/*  775: 889 */         (productoAtributo.getAtributo().getTipoAtributo() == TipoAtributo.LISTA)) {
/*  776: 890 */         lista.add(productoAtributo);
/*  777:     */       }
/*  778:     */     }
/*  779: 893 */     return lista;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public UploadedFile getUploadedFile()
/*  783:     */   {
/*  784: 897 */     return this.uploadedFile;
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void setUploadedFile(UploadedFile uploadedFile)
/*  788:     */   {
/*  789: 901 */     this.uploadedFile = uploadedFile;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void cargarImagen(FileUploadEvent event)
/*  793:     */   {
/*  794: 911 */     System.out.println("entro al metodo cargar imagen");
/*  795:     */     try
/*  796:     */     {
/*  797: 916 */       String fileName = "pro_" + AppUtil.getOrganizacion().getId() + "_" + FuncionesUtiles.obtenerAlfanumericos(getProducto().getCodigo()) + "_" + FuncionesUtiles.obtenerNumeroRandomico(999999999) + recuperaExtencion(event.getFile().getFileName());
/*  798: 917 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "producto");
/*  799: 918 */       File directorio = new File(uploadDir);
/*  800:     */       
/*  801: 920 */       File file = new File(uploadDir + fileName);
/*  802: 922 */       if (!directorio.exists()) {
/*  803: 923 */         directorio.mkdirs();
/*  804:     */       }
/*  805: 926 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  806:     */       
/*  807: 928 */       this.producto.setImagen(fileName);
/*  808:     */       
/*  809: 930 */       FileOutputStream output = new FileOutputStream(file);
/*  810: 932 */       while (input.available() != 0) {
/*  811: 933 */         output.write(input.read());
/*  812:     */       }
/*  813: 936 */       input.close();
/*  814: 937 */       output.close();
/*  815:     */       
/*  816: 939 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_imagen"));
/*  817:     */     }
/*  818:     */     catch (Exception e)
/*  819:     */     {
/*  820: 942 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/*  821: 943 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  822:     */     }
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void handleFileUpload(FileUploadEvent event)
/*  826:     */   {
/*  827: 952 */     FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + "cargado.");
/*  828: 953 */     FacesContext.getCurrentInstance().addMessage(null, msg);
/*  829: 955 */     if (".pdf".equals(recuperaExtencion(event.getFile().getFileName()))) {
/*  830: 957 */       cargarPdf(event);
/*  831:     */     } else {
/*  832: 959 */       cargarImagen(event);
/*  833:     */     }
/*  834:     */   }
/*  835:     */   
/*  836:     */   public void cargarPdf(FileUploadEvent event)
/*  837:     */   {
/*  838:     */     try
/*  839:     */     {
/*  840: 970 */       String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "producto");
/*  841: 971 */       String fileName = FuncionesUtiles.uploadArchivo(event, "" + AppUtil.getOrganizacion().getId(), getProducto().getCodigo(), uploadDir);
/*  842: 972 */       this.producto.setPdf(fileName);
/*  843:     */       
/*  844: 974 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/*  845:     */     }
/*  846:     */     catch (Exception e)
/*  847:     */     {
/*  848: 977 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  849: 978 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/*  850:     */     }
/*  851:     */   }
/*  852:     */   
/*  853:     */   public void processDownload()
/*  854:     */   {
/*  855:     */     try
/*  856:     */     {
/*  857: 986 */       Producto pdfProducto = (Producto)this.dtProducto.getRowData();
/*  858: 987 */       String fileName = pdfProducto.getPdf();
/*  859: 988 */       String downloadDirectorio = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "producto");
/*  860: 990 */       if (fileName == null) {
/*  861: 991 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  862:     */       } else {
/*  863: 993 */         FuncionesUtiles.downloadArchivo(downloadDirectorio, fileName);
/*  864:     */       }
/*  865:     */     }
/*  866:     */     catch (Exception e)
/*  867:     */     {
/*  868: 997 */       e.printStackTrace();
/*  869: 998 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/*  870:     */     }
/*  871:     */   }
/*  872:     */   
/*  873:     */   public String recuperaExtencion(String nombreArchivo)
/*  874:     */   {
/*  875:1010 */     int mid = nombreArchivo.lastIndexOf(".");
/*  876:1011 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/*  877:     */   }
/*  878:     */   
/*  879:     */   public List<Unidad> getListaUnidad()
/*  880:     */   {
/*  881:1016 */     if (this.listaUnidad == null) {
/*  882:1017 */       this.listaUnidad = this.servicioUnidad.obtenerListaCombo("codigo", true, null);
/*  883:     */     }
/*  884:1020 */     return this.listaUnidad;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void setListaUnidad(List<Unidad> listaUnidad)
/*  888:     */   {
/*  889:1024 */     this.listaUnidad = listaUnidad;
/*  890:     */   }
/*  891:     */   
/*  892:     */   public DataTable getDtMateriales()
/*  893:     */   {
/*  894:1033 */     return this.dtMateriales;
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void setDtMateriales(DataTable dtMateriales)
/*  898:     */   {
/*  899:1043 */     this.dtMateriales = dtMateriales;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public List<ProductoRutaFabricacion> getListaProductoRutaFabricacion()
/*  903:     */   {
/*  904:1047 */     List<ProductoRutaFabricacion> lista = new ArrayList();
/*  905:1048 */     for (ProductoRutaFabricacion productoRutaFabricacion : this.producto.getListaProductoRutaFabricacion()) {
/*  906:1049 */       if (!productoRutaFabricacion.isEliminado()) {
/*  907:1050 */         lista.add(productoRutaFabricacion);
/*  908:     */       }
/*  909:     */     }
/*  910:1053 */     return lista;
/*  911:     */   }
/*  912:     */   
/*  913:     */   public List<ProductoSustituto> getListaProductoSustituto()
/*  914:     */   {
/*  915:1062 */     List<ProductoSustituto> lista = new ArrayList();
/*  916:1064 */     for (ProductoSustituto productoSustituto : this.producto.getListaProductoSustituto()) {
/*  917:1065 */       if (!productoSustituto.isEliminado()) {
/*  918:1066 */         lista.add(productoSustituto);
/*  919:     */       }
/*  920:     */     }
/*  921:1070 */     return lista;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public DataTable getDtProductoSustituto()
/*  925:     */   {
/*  926:1079 */     return this.dtProductoSustituto;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public void setDtProductoSustituto(DataTable dtProductoSustituto)
/*  930:     */   {
/*  931:1089 */     this.dtProductoSustituto = dtProductoSustituto;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public ProductoMaterial getMaterialSeleccionado()
/*  935:     */   {
/*  936:1096 */     return this.materialSeleccionado;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public void setMaterialSeleccionado(ProductoMaterial materialSeleccionado)
/*  940:     */   {
/*  941:1104 */     this.materialSeleccionado = materialSeleccionado;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public ProductoSustituto getSustitutoSeleccionado()
/*  945:     */   {
/*  946:1111 */     return this.sustitutoSeleccionado;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void setSustitutoSeleccionado(ProductoSustituto sustitutoSeleccionado)
/*  950:     */   {
/*  951:1119 */     this.sustitutoSeleccionado = sustitutoSeleccionado;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public Producto getProductoSeleccionado()
/*  955:     */   {
/*  956:1123 */     return this.productoSeleccionado;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public void setProductoSeleccionado(Producto productoSeleccionado)
/*  960:     */   {
/*  961:1127 */     this.productoSeleccionado = productoSeleccionado;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public SelectItem[] getListaTipoProductoItem()
/*  965:     */   {
/*  966:1137 */     if (this.listaTipoProductoItem == null)
/*  967:     */     {
/*  968:1138 */       List<SelectItem> lista = new ArrayList();
/*  969:1139 */       lista.add(new SelectItem("", ""));
/*  970:1141 */       for (TipoProducto t : TipoProducto.values())
/*  971:     */       {
/*  972:1142 */         SelectItem item = new SelectItem(t, t.getNombre());
/*  973:1143 */         lista.add(item);
/*  974:     */       }
/*  975:1145 */       this.listaTipoProductoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/*  976:     */     }
/*  977:1148 */     return this.listaTipoProductoItem;
/*  978:     */   }
/*  979:     */   
/*  980:     */   public void setListaTipoProductoItem(SelectItem[] listaTipoProductoItem)
/*  981:     */   {
/*  982:1158 */     this.listaTipoProductoItem = listaTipoProductoItem;
/*  983:     */   }
/*  984:     */   
/*  985:     */   public List<Bodega> getListaBodega()
/*  986:     */   {
/*  987:1163 */     if (this.listaBodega == null) {
/*  988:1164 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/*  989:     */     }
/*  990:1167 */     return this.listaBodega;
/*  991:     */   }
/*  992:     */   
/*  993:     */   public List<Bodega> getListaBodegaTrabajo()
/*  994:     */   {
/*  995:1171 */     List<Bodega> lista = new ArrayList();
/*  996:1172 */     for (Bodega bodega : getListaBodega()) {
/*  997:1173 */       if (bodega.getClaseBodega() == ClaseBodegaEnum.TRABAJO) {
/*  998:1174 */         lista.add(bodega);
/*  999:     */       }
/* 1000:     */     }
/* 1001:1177 */     return lista;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public List<Sucursal> getListaSucursal()
/* 1005:     */   {
/* 1006:1181 */     if (this.listaSucursal == null)
/* 1007:     */     {
/* 1008:1182 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 1009:1183 */       filtros.put("activo", "true");
/* 1010:1184 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filtros);
/* 1011:     */     }
/* 1012:1186 */     return this.listaSucursal;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public void setListaBodega(List<Bodega> listaBodega)
/* 1016:     */   {
/* 1017:1196 */     this.listaBodega = listaBodega;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public List<UnidadConversion> getListaUnidadConversion()
/* 1021:     */   {
/* 1022:1205 */     List<UnidadConversion> lista = new ArrayList();
/* 1023:1207 */     for (UnidadConversion unidadConversion : getProducto().getListaUnidadConversion()) {
/* 1024:1208 */       if (!unidadConversion.isEliminado()) {
/* 1025:1209 */         lista.add(unidadConversion);
/* 1026:     */       }
/* 1027:     */     }
/* 1028:1212 */     return lista;
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public DataTable getDtUnidadConversion()
/* 1032:     */   {
/* 1033:1222 */     return this.dtUnidadConversion;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void setDtUnidadConversion(DataTable dtUnidadConversion)
/* 1037:     */   {
/* 1038:1232 */     this.dtUnidadConversion = dtUnidadConversion;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public ProductoRutaFabricacion getProductoRutaFabricacion()
/* 1042:     */   {
/* 1043:1241 */     return this.productoRutaFabricacion;
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public void setProductoRutaFabricacion(ProductoRutaFabricacion productoRutaFabricacion)
/* 1047:     */   {
/* 1048:1251 */     this.productoRutaFabricacion = productoRutaFabricacion;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public List<ConjuntoAtributo> getListaConjuntoAtributo()
/* 1052:     */   {
/* 1053:1258 */     if (this.listaConjuntoAtributo == null)
/* 1054:     */     {
/* 1055:1259 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 1056:1260 */       filters.put("indicadorProducto", "true");
/* 1057:1261 */       this.listaConjuntoAtributo = this.servicioConjuntoAtributo.obtenerListaCombo("nombre", true, filters);
/* 1058:     */     }
/* 1059:1263 */     return this.listaConjuntoAtributo;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public void setListaConjuntoAtributo(List<ConjuntoAtributo> listaConjuntoAtributo)
/* 1063:     */   {
/* 1064:1271 */     this.listaConjuntoAtributo = listaConjuntoAtributo;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public ListaProductoBean getListaProductoBean()
/* 1068:     */   {
/* 1069:1275 */     return this.listaProductoBean;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 1073:     */   {
/* 1074:1279 */     this.listaProductoBean = listaProductoBean;
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   public List<MarcaProducto> getListaMarcaProducto()
/* 1078:     */   {
/* 1079:1283 */     if (this.listaMarcaProducto == null) {
/* 1080:1284 */       this.listaMarcaProducto = this.servicioMarcaProducto.obtenerListaCombo("nombre", true, null);
/* 1081:     */     }
/* 1082:1286 */     return this.listaMarcaProducto;
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public void setListaMarcaProducto(List<MarcaProducto> listaMarcaProducto)
/* 1086:     */   {
/* 1087:1290 */     this.listaMarcaProducto = listaMarcaProducto;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public List<PresentacionProducto> getListaPresentacionProducto()
/* 1091:     */   {
/* 1092:1294 */     if (this.listaPresentacionProducto == null) {
/* 1093:1295 */       this.listaPresentacionProducto = this.servicioPresentacionProducto.obtenerListaCombo(PresentacionProducto.class, "nombre", true, null);
/* 1094:     */     }
/* 1095:1297 */     return this.listaPresentacionProducto;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public void setListaPresentacionProducto(List<PresentacionProducto> listaPresentacionProducto)
/* 1099:     */   {
/* 1100:1301 */     this.listaPresentacionProducto = listaPresentacionProducto;
/* 1101:     */   }
/* 1102:     */   
/* 1103:     */   public void eliminarBodegaTrabajoSucursal(BodegaTrabajoProductoSucursal bodegaTrabajoSucursal)
/* 1104:     */   {
/* 1105:1305 */     bodegaTrabajoSucursal.setEliminado(true);
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public void eliminarMezclaProducto(MezclaProducto mezclaProducto)
/* 1109:     */   {
/* 1110:1309 */     mezclaProducto.setEliminado(true);
/* 1111:1310 */     this.listaMezclaProducto = null;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void agregarBodegaTrabajoSucursal()
/* 1115:     */   {
/* 1116:1314 */     BodegaTrabajoProductoSucursal bodegaTrabajoSucursal = new BodegaTrabajoProductoSucursal();
/* 1117:1315 */     bodegaTrabajoSucursal.setProducto(this.producto);
/* 1118:1316 */     bodegaTrabajoSucursal.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 1119:1317 */     this.producto.getListaBodegaTrabajoSucursal().add(bodegaTrabajoSucursal);
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public List<BodegaTrabajoProductoSucursal> getListaBodegaTrabajoProductoSucursal()
/* 1123:     */   {
/* 1124:1321 */     this.listaBodegaTrabajoProductoSucursal = new ArrayList();
/* 1125:1322 */     if (this.producto != null) {
/* 1126:1323 */       for (BodegaTrabajoProductoSucursal bodegaTrabajoSucursal : this.producto.getListaBodegaTrabajoSucursal()) {
/* 1127:1324 */         if (!bodegaTrabajoSucursal.isEliminado()) {
/* 1128:1325 */           this.listaBodegaTrabajoProductoSucursal.add(bodegaTrabajoSucursal);
/* 1129:     */         }
/* 1130:     */       }
/* 1131:     */     }
/* 1132:1329 */     return this.listaBodegaTrabajoProductoSucursal;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public List<Producto> autocompletarProducto(String consulta)
/* 1136:     */   {
/* 1137:1333 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public void agregarRutaFabricacionSucursal()
/* 1141:     */   {
/* 1142:1337 */     ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal = new ProductoRutaFabricacionSucursal();
/* 1143:1338 */     productoRutaFabricacionSucursal.setProducto(this.producto);
/* 1144:1339 */     this.producto.getListaProductoRutaFabricacionSucursal().add(productoRutaFabricacionSucursal);
/* 1145:     */   }
/* 1146:     */   
/* 1147:     */   public List<ProductoRutaFabricacionSucursal> getListaRutaFabricacionSucursal()
/* 1148:     */   {
/* 1149:1343 */     List<ProductoRutaFabricacionSucursal> lista = new ArrayList();
/* 1150:1344 */     for (ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal : this.producto.getListaProductoRutaFabricacionSucursal()) {
/* 1151:1345 */       if (!productoRutaFabricacionSucursal.isEliminado()) {
/* 1152:1346 */         lista.add(productoRutaFabricacionSucursal);
/* 1153:     */       }
/* 1154:     */     }
/* 1155:1349 */     return lista;
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public List<MezclaProducto> getListaMezclaProducto()
/* 1159:     */   {
/* 1160:1353 */     if (this.listaMezclaProducto == null)
/* 1161:     */     {
/* 1162:1354 */       this.listaMezclaProducto = new ArrayList();
/* 1163:1355 */       for (MezclaProducto mp : this.producto.getListaMezclaProducto()) {
/* 1164:1356 */         if (!mp.isEliminado()) {
/* 1165:1357 */           this.listaMezclaProducto.add(mp);
/* 1166:     */         }
/* 1167:     */       }
/* 1168:     */     }
/* 1169:1361 */     return this.listaMezclaProducto;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   public void setListaMezclaProducto(List<MezclaProducto> listaMezclaProducto)
/* 1173:     */   {
/* 1174:1365 */     this.listaMezclaProducto = listaMezclaProducto;
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public MezclaProducto getMezclaProductoSeleccionado()
/* 1178:     */   {
/* 1179:1369 */     return this.mezclaProductoSeleccionado;
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   public void setMezclaProductoSeleccionado(MezclaProducto mezclaProductoSeleccionado)
/* 1183:     */   {
/* 1184:1373 */     this.mezclaProductoSeleccionado = mezclaProductoSeleccionado;
/* 1185:     */   }
/* 1186:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.ProductoBean
 * JD-Core Version:    0.7.0.1
 */