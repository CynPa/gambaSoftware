/*    1:     */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.FactorConversion;
/*    4:     */ import com.asinfo.as2.clases.ProductoEstadoImportacion;
/*    5:     */ import com.asinfo.as2.dao.BodegaDao;
/*    6:     */ import com.asinfo.as2.dao.BodegaTrabajoProductoSucursalDao;
/*    7:     */ import com.asinfo.as2.dao.CategoriaImpuestoDao;
/*    8:     */ import com.asinfo.as2.dao.GenericoDao;
/*    9:     */ import com.asinfo.as2.dao.ProductoAtributoDao;
/*   10:     */ import com.asinfo.as2.dao.ProductoBodegaDao;
/*   11:     */ import com.asinfo.as2.dao.ProductoDao;
/*   12:     */ import com.asinfo.as2.dao.ProductoSustitutoDao;
/*   13:     */ import com.asinfo.as2.dao.UnidadConversionDao;
/*   14:     */ import com.asinfo.as2.dao.mantenimiento.old.ProductoRutaFabricacionDao;
/*   15:     */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*   16:     */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*   17:     */ import com.asinfo.as2.entities.Bodega;
/*   18:     */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*   19:     */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   20:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   21:     */ import com.asinfo.as2.entities.CuentaContable;
/*   22:     */ import com.asinfo.as2.entities.FiltroProducto;
/*   23:     */ import com.asinfo.as2.entities.Impuesto;
/*   24:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   25:     */ import com.asinfo.as2.entities.Lote;
/*   26:     */ import com.asinfo.as2.entities.MarcaProducto;
/*   27:     */ import com.asinfo.as2.entities.Organizacion;
/*   28:     */ import com.asinfo.as2.entities.Producto;
/*   29:     */ import com.asinfo.as2.entities.ProductoAtributo;
/*   30:     */ import com.asinfo.as2.entities.ProductoBodega;
/*   31:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   32:     */ import com.asinfo.as2.entities.ProductoSustituto;
/*   33:     */ import com.asinfo.as2.entities.RangoImpuesto;
/*   34:     */ import com.asinfo.as2.entities.SaldoProducto;
/*   35:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   36:     */ import com.asinfo.as2.entities.SubProducto;
/*   37:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   38:     */ import com.asinfo.as2.entities.Sucursal;
/*   39:     */ import com.asinfo.as2.entities.Unidad;
/*   40:     */ import com.asinfo.as2.entities.UnidadConversion;
/*   41:     */ import com.asinfo.as2.entities.UnidadManejo;
/*   42:     */ import com.asinfo.as2.entities.UnidadManejoProducto;
/*   43:     */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*   44:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*   45:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterialMezcla;
/*   46:     */ import com.asinfo.as2.entities.produccion.MezclaProducto;
/*   47:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   48:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*   49:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
/*   50:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   51:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   52:     */ import com.asinfo.as2.enumeraciones.MedioTransporteEnum;
/*   53:     */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*   54:     */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*   55:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   56:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   57:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   58:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*   59:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   60:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoRemoto;
/*   61:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   62:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*   63:     */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*   64:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   65:     */ import com.asinfo.as2.util.AppUtil;
/*   66:     */ import com.asinfo.as2.utils.EjbUtil;
/*   67:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   68:     */ import com.asinfo.as2.utils.NodoArbol;
/*   69:     */ import java.io.InputStream;
/*   70:     */ import java.math.BigDecimal;
/*   71:     */ import java.math.RoundingMode;
/*   72:     */ import java.util.ArrayList;
/*   73:     */ import java.util.Date;
/*   74:     */ import java.util.HashMap;
/*   75:     */ import java.util.HashSet;
/*   76:     */ import java.util.Iterator;
/*   77:     */ import java.util.List;
/*   78:     */ import java.util.Map;
/*   79:     */ import java.util.Set;
/*   80:     */ import javax.annotation.Resource;
/*   81:     */ import javax.ejb.EJB;
/*   82:     */ import javax.ejb.SessionContext;
/*   83:     */ import javax.ejb.Stateless;
/*   84:     */ import javax.ejb.TransactionAttribute;
/*   85:     */ import javax.ejb.TransactionAttributeType;
/*   86:     */ import javax.ejb.TransactionManagement;
/*   87:     */ import javax.ejb.TransactionManagementType;
/*   88:     */ import javax.persistence.NoResultException;
/*   89:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   90:     */ 
/*   91:     */ @Stateless
/*   92:     */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*   93:     */ public class ServicioProductoImpl
/*   94:     */   implements ServicioProducto, ServicioProductoRemoto
/*   95:     */ {
/*   96:     */   @EJB
/*   97:     */   private ProductoDao productoDao;
/*   98:     */   @EJB
/*   99:     */   private CategoriaImpuestoDao categoriaImpuestoDao;
/*  100:     */   @EJB
/*  101:     */   private ProductoAtributoDao productoAtributoDao;
/*  102:     */   @EJB
/*  103:     */   private GenericoDao<ProductoMaterial> productoMaterialDao;
/*  104:     */   @EJB
/*  105:     */   private GenericoDao<VariableCalidadProducto> variableCalidadProductoDao;
/*  106:     */   @EJB
/*  107:     */   private GenericoDao<ProductoRutaFabricacionSucursal> productoRutaFabricacionSucursalDao;
/*  108:     */   @EJB
/*  109:     */   private ProductoSustitutoDao productoSustitutoDao;
/*  110:     */   @EJB
/*  111:     */   private BodegaDao bodegaDao;
/*  112:     */   @EJB
/*  113:     */   private UnidadConversionDao unidadConversionDao;
/*  114:     */   @EJB
/*  115:     */   private ProductoRutaFabricacionDao productoRutaFabricacionDao;
/*  116:     */   @EJB
/*  117:     */   private GenericoDao<SubProducto> subProductoDao;
/*  118:     */   @EJB
/*  119:     */   private ProductoBodegaDao productoBodegaDao;
/*  120:     */   @EJB
/*  121:     */   private ServicioLote servicioLote;
/*  122:     */   @EJB
/*  123:     */   private ServicioUnidad servicioUnidad;
/*  124:     */   @EJB
/*  125:     */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  126:     */   @EJB
/*  127:     */   private BodegaTrabajoProductoSucursalDao bodegaTrabajoProductoSucursalDao;
/*  128:     */   @EJB
/*  129:     */   private OrdenFabricacionDao ordenFabricacionDao;
/*  130:     */   @EJB
/*  131:     */   private ServicioUnidadConversion servicioUnidadConversion;
/*  132:     */   @EJB
/*  133:     */   private GenericoDao<MezclaProducto> mezclaProductoDao;
/*  134:     */   @EJB
/*  135:     */   private UsuarioDao usuarioDao;
/*  136:     */   @Resource
/*  137:     */   protected SessionContext context;
/*  138:     */   
/*  139:     */   public void guardar(Producto producto)
/*  140:     */     throws ExcepcionAS2Inventario
/*  141:     */   {
/*  142: 166 */     validaProducto(producto);
/*  143: 169 */     for (ProductoAtributo productoAtributo : producto.getListaProductoAtributo())
/*  144:     */     {
/*  145: 171 */       if ((productoAtributo.getValor().isEmpty()) && (productoAtributo.getValorAtributo() == null)) {
/*  146: 172 */         productoAtributo.setEliminado(true);
/*  147:     */       }
/*  148: 175 */       this.productoAtributoDao.guardar(productoAtributo);
/*  149:     */     }
/*  150: 179 */     for (ProductoSustituto productoSustituto : producto.getListaProductoSustituto()) {
/*  151: 180 */       this.productoSustitutoDao.guardar(productoSustituto);
/*  152:     */     }
/*  153: 184 */     for (UnidadConversion unidadConversion : producto.getListaUnidadConversion()) {
/*  154: 185 */       this.unidadConversionDao.guardar(unidadConversion);
/*  155:     */     }
/*  156: 189 */     for (MezclaProducto mezclaProducto : producto.getListaMezclaProducto())
/*  157:     */     {
/*  158: 190 */       if (mezclaProducto.getPorcentaje().compareTo(BigDecimal.ZERO) == 0) {
/*  159: 191 */         mezclaProducto.setEliminado(true);
/*  160:     */       }
/*  161: 193 */       this.mezclaProductoDao.guardar(mezclaProducto);
/*  162:     */     }
/*  163: 196 */     this.productoDao.guardar(producto);
/*  164: 198 */     for (ProductoBodega productoBodega : producto.getListaProductoBodega()) {
/*  165: 199 */       if ((productoBodega.getId() != 0) || (!productoBodega.getSaldoMaximo().equals(new BigDecimal(0.0D))) || 
/*  166: 200 */         (!productoBodega.getSaldoMinimo().equals(new BigDecimal(0.0D))))
/*  167:     */       {
/*  168: 201 */         if (productoBodega.getSaldoMaximo().compareTo(productoBodega.getSaldoMinimo()) == -1) {
/*  169: 202 */           throw new ExcepcionAS2Inventario("msg_error_saldo_minimo_maximo");
/*  170:     */         }
/*  171: 204 */         productoBodega.setIdOrganizacion(producto.getIdOrganizacion());
/*  172: 205 */         Sucursal suc = new Sucursal();
/*  173: 206 */         suc.setIdSucursal(producto.getIdSucursal());
/*  174: 207 */         productoBodega.setSucursal(suc);
/*  175: 208 */         this.productoBodegaDao.guardar(productoBodega);
/*  176:     */       }
/*  177:     */     }
/*  178: 214 */     for (BodegaTrabajoProductoSucursal bodegaTrabajoSucursal : producto.getListaBodegaTrabajoSucursal()) {
/*  179: 215 */       this.bodegaTrabajoProductoSucursalDao.guardar(bodegaTrabajoSucursal);
/*  180:     */     }
/*  181: 219 */     for (ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal : producto.getListaProductoRutaFabricacionSucursal()) {
/*  182: 220 */       this.productoRutaFabricacionSucursalDao.guardar(productoRutaFabricacionSucursal);
/*  183:     */     }
/*  184: 223 */     this.servicioUnidadConversion.actualizaUnidadConversionProducto(producto);
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void guardarProductoAgil(Producto producto)
/*  188:     */     throws ExcepcionAS2Inventario
/*  189:     */   {
/*  190: 229 */     producto.setActivo(true);
/*  191: 230 */     producto.setNombreComercial(producto.getNombre());
/*  192: 231 */     if (producto.getTipoCosto() == null) {
/*  193: 232 */       producto.setTipoCosto(TipoCosto.ESTANDAR);
/*  194:     */     }
/*  195: 235 */     Map<String, String> filters = new HashMap();
/*  196: 236 */     filters.put("predeterminado", "true");
/*  197: 237 */     filters.put("idOrganizacion", "" + producto.getIdOrganizacion());
/*  198:     */     
/*  199: 239 */     List<Unidad> listaUnidad = this.servicioUnidad.obtenerListaCombo("nombre", false, filters);
/*  200: 240 */     if (listaUnidad.isEmpty()) {
/*  201: 241 */       throw new ExcepcionAS2Inventario("msg_error_creacion_producto_agil_unidad");
/*  202:     */     }
/*  203: 243 */     if (producto.getUnidad() == null) {
/*  204: 244 */       producto.setUnidad((Unidad)listaUnidad.get(0));
/*  205:     */     }
/*  206: 246 */     if (producto.getUnidadAlmacenamiento() == null) {
/*  207: 247 */       producto.setUnidadAlmacenamiento((Unidad)listaUnidad.get(0));
/*  208:     */     }
/*  209: 249 */     if ((producto.isIndicadorCompra()) && (producto.getUnidadCompra() == null)) {
/*  210: 250 */       producto.setUnidadCompra((Unidad)listaUnidad.get(0));
/*  211:     */     }
/*  212: 252 */     if ((producto.isIndicadorVenta()) && (producto.getUnidadVenta() == null)) {
/*  213: 253 */       producto.setUnidadVenta((Unidad)listaUnidad.get(0));
/*  214:     */     }
/*  215: 257 */     if (producto.getSubcategoriaProducto() == null)
/*  216:     */     {
/*  217: 258 */       List<SubcategoriaProducto> listaSubcategoria = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/*  218: 259 */       if (listaSubcategoria.isEmpty()) {
/*  219: 260 */         throw new ExcepcionAS2Inventario("msg_error_creacion_producto_agil_subcategoria");
/*  220:     */       }
/*  221: 262 */       producto.setSubcategoriaProducto((SubcategoriaProducto)listaSubcategoria.get(0));
/*  222:     */     }
/*  223: 266 */     validaProducto(producto);
/*  224:     */     
/*  225: 268 */     this.productoDao.guardar(producto);
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void guardarListaMaterial(Producto producto)
/*  229:     */     throws AS2Exception
/*  230:     */   {
/*  231: 278 */     validarListaMaterial(producto);
/*  232: 281 */     if (!EjbUtil.getEntidadesNoEliminadas(producto.getListaProductoMaterial()).isEmpty()) {
/*  233: 282 */       producto.setIndicadorListaMaterial(true);
/*  234:     */     } else {
/*  235: 284 */       producto.setIndicadorListaMaterial(false);
/*  236:     */     }
/*  237: 288 */     for (ProductoMaterial productoMaterial : producto.getListaProductoMaterial()) {
/*  238: 289 */       this.productoMaterialDao.guardar(productoMaterial);
/*  239:     */     }
/*  240: 293 */     for (SubProducto subProducto : producto.getListaSubProducto()) {
/*  241: 294 */       this.subProductoDao.guardar(subProducto);
/*  242:     */     }
/*  243: 298 */     for (ProductoRutaFabricacion productoRuta : producto.getListaProductoRutaFabricacion()) {
/*  244: 299 */       this.productoRutaFabricacionDao.guardar(productoRuta);
/*  245:     */     }
/*  246: 302 */     this.productoDao.guardar(producto);
/*  247:     */   }
/*  248:     */   
/*  249:     */   public void actualizarCantidadProductoMaterial(ProductoMaterial productoMaterial)
/*  250:     */   {
/*  251: 307 */     this.productoMaterialDao.guardar(productoMaterial);
/*  252:     */   }
/*  253:     */   
/*  254:     */   public void guardarListaVariableCalidadProducto(Producto producto)
/*  255:     */     throws AS2Exception
/*  256:     */   {
/*  257: 312 */     for (VariableCalidadProducto productoMaterial : producto.getListaVariableCalidadProducto()) {
/*  258: 313 */       this.variableCalidadProductoDao.guardar(productoMaterial);
/*  259:     */     }
/*  260:     */   }
/*  261:     */   
/*  262:     */   public void eliminar(Producto producto)
/*  263:     */     throws ExcepcionAS2Inventario, ExcepcionAS2
/*  264:     */   {
/*  265:     */     try
/*  266:     */     {
/*  267: 325 */       Producto productoDetalle = this.productoDao.cargarDetalle(producto.getId());
/*  268: 326 */       productoDetalle.setEliminado(true);
/*  269:     */       
/*  270: 328 */       guardar(productoDetalle);
/*  271:     */     }
/*  272:     */     catch (NoResultException e)
/*  273:     */     {
/*  274: 331 */       throw new ExcepcionAS2("msg_error_eliminar");
/*  275:     */     }
/*  276: 333 */     this.productoDao.eliminar(producto);
/*  277:     */   }
/*  278:     */   
/*  279:     */   public Producto buscarPorId(int id)
/*  280:     */   {
/*  281: 342 */     return (Producto)this.productoDao.buscarPorId(Integer.valueOf(id));
/*  282:     */   }
/*  283:     */   
/*  284:     */   public Producto buscarPorCodigo(String codigo, int idOrganizacion, DocumentoBase documentoBase)
/*  285:     */     throws ExcepcionAS2
/*  286:     */   {
/*  287: 352 */     return this.productoDao.buscarPorCodigo(codigo, idOrganizacion, documentoBase);
/*  288:     */   }
/*  289:     */   
/*  290:     */   public Producto buscarPorCualquierCodigo(String codigo, int idOrganizacion)
/*  291:     */     throws ExcepcionAS2
/*  292:     */   {
/*  293: 358 */     return this.productoDao.buscarPorCualquierCodigo(codigo, idOrganizacion);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public List<RangoImpuesto> impuestoProducto(Producto producto, Date fecha)
/*  297:     */     throws ExcepcionAS2Inventario
/*  298:     */   {
/*  299: 369 */     fecha = FuncionesUtiles.setAtributoFecha(fecha);
/*  300: 370 */     producto.setCategoriaImpuesto(this.categoriaImpuestoDao.cargarDetalle(producto.getCategoriaImpuesto().getId()));
/*  301:     */     
/*  302: 372 */     List<RangoImpuesto> listaRangoImpuesto = new ArrayList();
/*  303: 374 */     for (Impuesto impuesto : producto.getCategoriaImpuesto().getListaImpuesto()) {
/*  304: 376 */       for (RangoImpuesto rangoImpuesto : impuesto.getListaRangoImpuesto()) {
/*  305: 378 */         if ((FuncionesUtiles.compararFechaAnteriorOIgual(rangoImpuesto.getFechaDesde(), fecha)) && ((rangoImpuesto.getFechaHasta() == null) || 
/*  306: 379 */           (FuncionesUtiles.compararFechaAnteriorOIgual(fecha, rangoImpuesto.getFechaHasta())))) {
/*  307: 380 */           listaRangoImpuesto.add(rangoImpuesto);
/*  308:     */         }
/*  309:     */       }
/*  310:     */     }
/*  311: 385 */     return listaRangoImpuesto;
/*  312:     */   }
/*  313:     */   
/*  314:     */   public List<Producto> obtenerProductos(String sortField, boolean sortOrder, Map<String, String> filters)
/*  315:     */     throws ExcepcionAS2Inventario
/*  316:     */   {
/*  317: 390 */     Map<String, String> filtro = filters;
/*  318: 391 */     if (filtro == null) {
/*  319: 392 */       filtro = new HashMap();
/*  320:     */     }
/*  321: 394 */     if (filtro.isEmpty()) {
/*  322: 395 */       filtro.put("indicadorProducto", "true");
/*  323:     */     }
/*  324: 397 */     return this.productoDao.obtenerListaCombo(sortField, sortOrder, filtro);
/*  325:     */   }
/*  326:     */   
/*  327:     */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/*  328:     */   public List<Producto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  329:     */   {
/*  330: 408 */     return this.productoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  331:     */   }
/*  332:     */   
/*  333:     */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/*  334:     */   public List<Producto> obtenerListaComboMultiple(String sortField, boolean sortOrder, Map<String, String> filters)
/*  335:     */   {
/*  336: 414 */     return this.productoDao.obtenerListaComboMultiple(sortField, sortOrder, filters);
/*  337:     */   }
/*  338:     */   
/*  339:     */   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
/*  340:     */   public List<Producto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  341:     */     throws ExcepcionAS2Inventario
/*  342:     */   {
/*  343: 426 */     return this.productoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  344:     */   }
/*  345:     */   
/*  346:     */   public Producto cargaDetalle(int id)
/*  347:     */   {
/*  348: 443 */     return this.productoDao.cargarDetalle(id);
/*  349:     */   }
/*  350:     */   
/*  351:     */   public BigDecimal getSaldo(int idProducto, int idBodega, Date fecha)
/*  352:     */   {
/*  353: 453 */     return this.productoDao.getSaldo(idProducto, idBodega, fecha);
/*  354:     */   }
/*  355:     */   
/*  356:     */   public BigDecimal getSaldo(int idProducto, Date fecha)
/*  357:     */   {
/*  358: 463 */     return this.productoDao.getSaldo(idProducto, fecha);
/*  359:     */   }
/*  360:     */   
/*  361:     */   public List<SaldoProducto> obtenerSaldos(int idProducto, Date fecha, Bodega bodega)
/*  362:     */   {
/*  363: 474 */     List<SaldoProducto> lista = new ArrayList();
/*  364: 475 */     List<Bodega> listaBodega = new ArrayList();
/*  365: 477 */     if (bodega != null) {
/*  366: 478 */       listaBodega.add(bodega);
/*  367:     */     } else {
/*  368: 480 */       listaBodega = this.bodegaDao.obtenerListaCombo("codigo", false, null);
/*  369:     */     }
/*  370: 483 */     for (Bodega b : listaBodega) {
/*  371: 484 */       lista.addAll(this.productoDao.obtenerSaldosPorBodega(idProducto, b, fecha));
/*  372:     */     }
/*  373: 487 */     return lista;
/*  374:     */   }
/*  375:     */   
/*  376:     */   public List<ProductoSustituto> obtenerProductosSustitutos(int idProducto)
/*  377:     */   {
/*  378: 497 */     return this.productoDao.obtenerProductosSustitutos(idProducto);
/*  379:     */   }
/*  380:     */   
/*  381:     */   public int contarPorCriterio(Map<String, String> filters)
/*  382:     */   {
/*  383: 507 */     return this.productoDao.contarPorCriterio(filters);
/*  384:     */   }
/*  385:     */   
/*  386:     */   public Producto buscarPorCodigoYCargarCuentasContables(String codigo)
/*  387:     */     throws ExcepcionAS2
/*  388:     */   {
/*  389: 517 */     return this.productoDao.buscarPorCodigoYCargarCuentasContables(codigo);
/*  390:     */   }
/*  391:     */   
/*  392:     */   private void validaProducto(Producto producto)
/*  393:     */     throws ExcepcionAS2Inventario
/*  394:     */   {
/*  395: 530 */     if (producto.getId() == 0)
/*  396:     */     {
/*  397: 531 */       Producto existeProducto = null;
/*  398:     */       try
/*  399:     */       {
/*  400: 533 */         existeProducto = buscarPorCodigo(producto.getCodigo(), producto.getIdOrganizacion(), null);
/*  401:     */       }
/*  402:     */       catch (ExcepcionAS2 localExcepcionAS2) {}
/*  403: 537 */       if (existeProducto != null) {
/*  404: 538 */         throw new ExcepcionAS2Inventario("msg_error_codigo_repetido");
/*  405:     */       }
/*  406:     */     }
/*  407: 541 */     if ((producto.isIndicadorImpuestos()) && (producto.getCategoriaImpuesto() == null)) {
/*  408: 542 */       throw new ExcepcionAS2Inventario("msg_info_seleccionar_categoria_impuesto");
/*  409:     */     }
/*  410: 545 */     Long cuentaCodigoBarras = this.productoDao.contarProductoPorCodigoBarras(producto.getIdProducto(), producto.getCodigoBarras(), producto
/*  411: 546 */       .getIdOrganizacion());
/*  412: 547 */     if (cuentaCodigoBarras.longValue() > 0L) {
/*  413: 549 */       throw new ExcepcionAS2Inventario("msg_error_codigo_barras_existente", producto.getCodigoBarras() == null ? "" : producto.getCodigoBarras());
/*  414:     */     }
/*  415: 551 */     if ((producto.getPesoMinimo().compareTo(producto.getPeso()) > 0) || (producto.getPesoMaximo().compareTo(producto.getPeso()) < 0)) {
/*  416: 552 */       throw new ExcepcionAS2Inventario("msg_error_rango_pesos");
/*  417:     */     }
/*  418: 555 */     Set<String> setBodegaTrabajoSucursal = new HashSet();
/*  419: 556 */     for (Iterator localIterator = producto.getListaBodegaTrabajoSucursal().iterator(); localIterator.hasNext();)
/*  420:     */     {
/*  421: 556 */       bodegaTrabajoSucursal = (BodegaTrabajoProductoSucursal)localIterator.next();
/*  422: 557 */       String key = bodegaTrabajoSucursal.getSucursal().getId() + " - " + bodegaTrabajoSucursal.getBodegaTrabajo().getId();
/*  423: 558 */       if ((!bodegaTrabajoSucursal.isEliminado()) && (setBodegaTrabajoSucursal.contains(key))) {
/*  424: 559 */         throw new ExcepcionAS2Inventario("msg_error_bodega_trabajo_sucursal_repetidos");
/*  425:     */       }
/*  426: 561 */       setBodegaTrabajoSucursal.add(key);
/*  427:     */     }
/*  428: 565 */     if (producto.isIndicadorPesoBalanza())
/*  429:     */     {
/*  430: 566 */       boolean unidadMasa = false;
/*  431: 567 */       if ((producto.getUnidad().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)) || ((producto.isIndicadorManejaUnidadInformativa()) && 
/*  432: 568 */         (producto.getUnidadInformativa().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)))) {
/*  433: 569 */         unidadMasa = true;
/*  434:     */       }
/*  435: 571 */       if (!unidadMasa) {
/*  436: 572 */         throw new ExcepcionAS2Inventario("msg_error_producto_con_balanza_sin_unidad_masa");
/*  437:     */       }
/*  438:     */     }
/*  439: 576 */     if ((producto.isIndicadorManejaUnidadInformativa()) && 
/*  440: 577 */       (producto.getUnidad().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA)) && 
/*  441: 578 */       (producto.getUnidadInformativa().getTipoUnidadMedida().equals(TipoUnidadMedida.MASA))) {
/*  442: 579 */       throw new ExcepcionAS2Inventario("msg_error_producto_con_unidad_y_unidad_informativa_tipo_masa");
/*  443:     */     }
/*  444: 584 */     Object idsSucursal = new HashSet();
/*  445: 585 */     for (BodegaTrabajoProductoSucursal bodegaTrabajoSucursal = producto.getListaProductoRutaFabricacionSucursal().iterator(); bodegaTrabajoSucursal.hasNext();)
/*  446:     */     {
/*  447: 585 */       productoRutaFabricacionSucursal = (ProductoRutaFabricacionSucursal)bodegaTrabajoSucursal.next();
/*  448: 586 */       if ((!productoRutaFabricacionSucursal.isEliminado()) && (((Set)idsSucursal).contains(Integer.valueOf(productoRutaFabricacionSucursal.getSucursal().getId())))) {
/*  449: 587 */         throw new ExcepcionAS2Inventario("msg_error_ruta_fabricacion_sucursal_repetidos");
/*  450:     */       }
/*  451: 589 */       ((Set)idsSucursal).add(Integer.valueOf(productoRutaFabricacionSucursal.getSucursal().getId()));
/*  452:     */     }
/*  453:     */     ProductoRutaFabricacionSucursal productoRutaFabricacionSucursal;
/*  454:     */     BigDecimal totalporcentaje;
/*  455: 592 */     if (producto.isIndicadorMezcla())
/*  456:     */     {
/*  457: 593 */       totalporcentaje = BigDecimal.ZERO;
/*  458: 594 */       for (MezclaProducto mezclaProducto : producto.getListaMezclaProducto()) {
/*  459: 595 */         if (!mezclaProducto.isEliminado()) {
/*  460: 596 */           totalporcentaje = totalporcentaje.add(mezclaProducto.getPorcentaje());
/*  461:     */         }
/*  462:     */       }
/*  463: 599 */       if (totalporcentaje.compareTo(new BigDecimal(100)) != 0) {
/*  464: 600 */         throw new ExcepcionAS2Inventario("msg_error_porcentaje_igual_cien");
/*  465:     */       }
/*  466:     */     }
/*  467:     */     else
/*  468:     */     {
/*  469: 603 */       for (MezclaProducto mezclaProducto : producto.getListaMezclaProducto()) {
/*  470: 604 */         mezclaProducto.setPorcentaje(BigDecimal.ZERO);
/*  471:     */       }
/*  472:     */     }
/*  473:     */   }
/*  474:     */   
/*  475:     */   private void validarListaMaterial(Producto producto)
/*  476:     */     throws AS2Exception
/*  477:     */   {
/*  478: 611 */     validarMaterialSubproducto(producto, null);
/*  479:     */     
/*  480: 613 */     BigDecimal totalPorcentajeCosto = BigDecimal.ZERO;
/*  481: 615 */     for (SubProducto subProducto : producto.getListaSubProducto()) {
/*  482: 616 */       if (!subProducto.isEliminado()) {
/*  483: 617 */         totalPorcentajeCosto = totalPorcentajeCosto.add(subProducto.getPorcentajeCosto());
/*  484:     */       }
/*  485:     */     }
/*  486: 621 */     if (totalPorcentajeCosto.compareTo(new BigDecimal(100)) > 0) {
/*  487: 622 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PORCENTAJE_COSTO_SUBPRODUCTO", new String[] { totalPorcentajeCosto.toString() });
/*  488:     */     }
/*  489: 625 */     if ((producto.getListaProductoMaterial().size() == 1) && (!((ProductoMaterial)producto.getListaProductoMaterial().get(0)).isEliminado()) && 
/*  490: 626 */       (((ProductoMaterial)producto.getListaProductoMaterial().get(0)).isActivo())) {
/*  491: 628 */       ((ProductoMaterial)producto.getListaProductoMaterial().get(0)).setIndicadorPrincipal(Boolean.valueOf(true));
/*  492:     */     }
/*  493: 631 */     int cantidadPrincipal = 0;
/*  494: 632 */     int cantidad = 0;
/*  495: 634 */     for (ProductoMaterial pm : producto.getListaProductoMaterial())
/*  496:     */     {
/*  497: 635 */       if (!pm.isEliminado()) {
/*  498: 636 */         cantidad++;
/*  499:     */       }
/*  500: 638 */       if ((!pm.isEliminado()) && (pm.getIndicadorPrincipal().booleanValue() == true) && (pm.isActivo())) {
/*  501: 639 */         cantidadPrincipal++;
/*  502:     */       }
/*  503: 642 */       if ((!pm.isEliminado()) && 
/*  504: 643 */         (pm.getProporcion().compareTo(new BigDecimal(100)) < 0) && (pm.getSustituto() == null)) {
/*  505: 644 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PROPORCION_MATERIAL_SUSTITUTO", new String[] { producto.getNombre() });
/*  506:     */       }
/*  507:     */     }
/*  508: 648 */     if ((cantidadPrincipal == 0) && (cantidad > 0)) {
/*  509: 649 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PRODUCTO_PRINCIPAL_OBLIGATORIO", new String[] { producto.getNombre() + "-" + producto.getCodigo() });
/*  510:     */     }
/*  511:     */   }
/*  512:     */   
/*  513:     */   public void validarMaterialSubproducto(Producto producto, Producto materialSubProducto)
/*  514:     */     throws AS2Exception
/*  515:     */   {
/*  516: 662 */     if ((materialSubProducto != null) && (materialSubProducto.equals(producto))) {
/*  517: 663 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_MATERIAL_ERRONEO", new String[] { producto.getCodigo(), producto.getNombre() });
/*  518:     */     }
/*  519: 668 */     for (ProductoMaterial productoMaterial : producto.getListaProductoMaterial()) {
/*  520: 669 */       if (productoMaterial.getMaterial().equals(producto)) {
/*  521: 670 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_MATERIAL_ERRONEO", new String[] { producto.getCodigo(), producto.getNombre() });
/*  522:     */       }
/*  523:     */     }
/*  524: 676 */     for (SubProducto subProducto : producto.getListaSubProducto()) {
/*  525: 677 */       if (subProducto.getProducto().equals(producto)) {
/*  526: 678 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_MATERIAL_ERRONEO", new String[] { producto.getCodigo(), producto.getNombre() });
/*  527:     */       }
/*  528:     */     }
/*  529:     */   }
/*  530:     */   
/*  531:     */   public void guardarPost(Producto producto)
/*  532:     */     throws ExcepcionAS2Inventario
/*  533:     */   {
/*  534: 691 */     validaProducto(producto);
/*  535: 694 */     for (ProductoAtributo productoAtributo : producto.getListaProductoAtributo())
/*  536:     */     {
/*  537: 696 */       if ((productoAtributo.getValor() == null) || (productoAtributo.getValor().isEmpty())) {
/*  538: 697 */         productoAtributo.setEliminado(true);
/*  539:     */       }
/*  540: 700 */       this.productoAtributoDao.guardar(productoAtributo);
/*  541:     */     }
/*  542: 704 */     for (ProductoMaterial productoMaterial : producto.getListaProductoMaterial()) {
/*  543: 705 */       this.productoMaterialDao.guardar(productoMaterial);
/*  544:     */     }
/*  545: 709 */     for (ProductoSustituto productoSustituto : producto.getListaProductoSustituto()) {
/*  546: 710 */       this.productoSustitutoDao.guardar(productoSustituto);
/*  547:     */     }
/*  548: 712 */     for (UnidadConversion unidadConversion : producto.getListaUnidadConversion()) {
/*  549: 713 */       this.unidadConversionDao.guardar(unidadConversion);
/*  550:     */     }
/*  551: 715 */     this.productoDao.guardar(producto);
/*  552:     */   }
/*  553:     */   
/*  554:     */   public BigDecimal convierteUnidad(Unidad unidadOrigen, Unidad unidadDestino, int idProducto, BigDecimal cantidad)
/*  555:     */     throws ExcepcionAS2Inventario
/*  556:     */   {
/*  557: 722 */     if (cantidad.compareTo(BigDecimal.ZERO) != 0)
/*  558:     */     {
/*  559: 724 */       if ((unidadOrigen == null) || (unidadDestino == null))
/*  560:     */       {
/*  561: 725 */         String mensaje = "";
/*  562: 726 */         if (unidadOrigen == null) {
/*  563: 727 */           mensaje = "unidadOrigen es null";
/*  564:     */         } else {
/*  565: 729 */           mensaje = "unidadDestino es null";
/*  566:     */         }
/*  567: 731 */         throw new ExcepcionAS2Inventario("msg_error_unidad_conversion", mensaje);
/*  568:     */       }
/*  569: 734 */       int idUnidadOrigen = unidadOrigen.getIdUnidad();
/*  570: 735 */       int idUnidadDestino = unidadDestino.getIdUnidad();
/*  571: 737 */       if (idUnidadOrigen == idUnidadDestino) {
/*  572: 738 */         return cantidad;
/*  573:     */       }
/*  574: 741 */       Producto p = cargaDetalle(idProducto);
/*  575: 742 */       FactorConversion factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(p.getIdProducto(), p
/*  576: 743 */         .getSubcategoriaProducto().getIdSubcategoriaProducto(), idUnidadOrigen, idUnidadDestino);
/*  577: 745 */       if (factorConversion != null)
/*  578:     */       {
/*  579: 746 */         if (factorConversion.isIndicadorInverso()) {
/*  580: 747 */           return cantidad.divide(factorConversion.getFactor(), 4, RoundingMode.HALF_UP);
/*  581:     */         }
/*  582: 749 */         return cantidad.multiply(factorConversion.getFactor()).setScale(4, RoundingMode.HALF_UP);
/*  583:     */       }
/*  584: 754 */       String strMensaje = p.getCodigo() + "-" + p.getNombre() + " (" + unidadOrigen.getNombre() + "-" + unidadDestino.getNombre() + ")";
/*  585: 755 */       throw new ExcepcionAS2Inventario("msg_error_unidad_conversion", strMensaje);
/*  586:     */     }
/*  587: 761 */     return cantidad;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public Producto buscarProducto(Map<String, String> filters)
/*  591:     */     throws ExcepcionAS2
/*  592:     */   {
/*  593: 774 */     return this.productoDao.buscarProducto(filters);
/*  594:     */   }
/*  595:     */   
/*  596:     */   public List<Producto> autocompletarProductos(Integer idOrganizacion, String consulta)
/*  597:     */   {
/*  598: 779 */     return autocompletarProductos(idOrganizacion, consulta, null);
/*  599:     */   }
/*  600:     */   
/*  601:     */   public List<Producto> autocompletarProductos(Integer idOrganizacion, String consulta, Map<String, String> filtros)
/*  602:     */   {
/*  603: 784 */     List<Producto> lista = new ArrayList();
/*  604:     */     
/*  605: 786 */     String sortField = "codigo";
/*  606: 787 */     HashMap<String, String> filters = new HashMap();
/*  607: 788 */     filters.put("codigo", consulta.trim());
/*  608: 789 */     filters.put("codigoAlterno", consulta.trim());
/*  609: 790 */     filters.put("codigoComercial", consulta.trim());
/*  610: 791 */     filters.put("codigoBarras", consulta.trim());
/*  611: 792 */     filters.put("nombre", consulta.trim());
/*  612: 793 */     filters.put("nombreComercial", consulta.trim());
/*  613: 794 */     filters.put("activo", "true");
/*  614: 796 */     if (idOrganizacion != null) {
/*  615: 797 */       filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/*  616:     */     }
/*  617: 800 */     if (filtros != null) {
/*  618: 801 */       for (String propiedadFiltro : filtros.keySet()) {
/*  619: 802 */         filters.put(propiedadFiltro, filtros.get(propiedadFiltro));
/*  620:     */       }
/*  621:     */     }
/*  622: 812 */     lista = this.productoDao.autocompletarProductos(sortField, true, filters);
/*  623:     */     
/*  624: 814 */     return lista;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public CuentaContable obtenerCuentaContableGastoPorProducto(int idProducto)
/*  628:     */   {
/*  629: 824 */     return this.productoDao.obtenerCuentaContableGastoPorProducto(idProducto);
/*  630:     */   }
/*  631:     */   
/*  632:     */   public BigDecimal getCostoInicialMayorCero(InventarioProducto inventarioProducto, Bodega bodega)
/*  633:     */   {
/*  634: 834 */     Date fecha = inventarioProducto.getFecha();
/*  635: 835 */     fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, -1);
/*  636: 836 */     BigDecimal costo = getCostoMayorCero(inventarioProducto.getProducto().getId(), fecha, bodega);
/*  637: 837 */     if (costo.compareTo(BigDecimal.ZERO) <= 0) {
/*  638: 838 */       costo = this.productoDao.getCostoDespachoDevolucion(inventarioProducto);
/*  639:     */     }
/*  640: 840 */     return costo;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public BigDecimal getCostoInicial(int idProducto, Date fecha, Bodega bodega)
/*  644:     */   {
/*  645: 850 */     fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, -1);
/*  646: 851 */     return getCosto(idProducto, fecha, bodega);
/*  647:     */   }
/*  648:     */   
/*  649:     */   public BigDecimal getCostoInicial(int idProducto, Date fecha)
/*  650:     */   {
/*  651: 856 */     fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, -1);
/*  652: 857 */     return getCosto(idProducto, fecha, null);
/*  653:     */   }
/*  654:     */   
/*  655:     */   public BigDecimal getCosto(int idProducto, Date fecha, Bodega bodega)
/*  656:     */   {
/*  657: 867 */     return this.productoDao.getCostoTotal(idProducto, fecha, bodega, false);
/*  658:     */   }
/*  659:     */   
/*  660:     */   private BigDecimal getCostoMayorCero(int idProducto, Date fecha, Bodega bodega)
/*  661:     */   {
/*  662: 871 */     return this.productoDao.getCostoUnitario(idProducto, fecha, bodega, true);
/*  663:     */   }
/*  664:     */   
/*  665:     */   public BigDecimal getCostoUnitario(int idProducto, Date fecha, Bodega bodega)
/*  666:     */   {
/*  667: 876 */     return this.productoDao.getCostoUnitario(idProducto, fecha, bodega, false);
/*  668:     */   }
/*  669:     */   
/*  670:     */   public BigDecimal getSaldoInicial(int idProducto, Bodega bodega, Date fecha)
/*  671:     */   {
/*  672: 886 */     fecha = FuncionesUtiles.sumarFechaDiasMeses(fecha, -1);
/*  673:     */     
/*  674: 888 */     return this.productoDao.getSaldo(idProducto, bodega == null ? 0 : bodega.getIdBodega(), fecha);
/*  675:     */   }
/*  676:     */   
/*  677:     */   public BigDecimal getSaldoInicial(int idProducto, Date fecha)
/*  678:     */   {
/*  679: 898 */     return getSaldoInicial(idProducto, null, fecha);
/*  680:     */   }
/*  681:     */   
/*  682:     */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  683:     */   public void cargaCostoEstandar(String fileName, InputStream imInputStream, int filaInicial)
/*  684:     */     throws ExcepcionAS2Financiero, ExcepcionAS2
/*  685:     */   {
/*  686: 905 */     int filaActual = filaInicial;
/*  687: 906 */     HSSFCell[] filaErronea = new HSSFCell[0];
/*  688: 907 */     int columnaErronea = -1;
/*  689:     */     try
/*  690:     */     {
/*  691: 910 */       HSSFCell[][] datos = FuncionesUtiles.leerExcel2(AppUtil.getOrganizacion().getIdOrganizacion(), fileName, imInputStream, filaInicial, 0);
/*  692:     */       
/*  693:     */ 
/*  694: 913 */       String codigoProducto = "";
/*  695: 914 */       String nombreProducto = "";
/*  696:     */       
/*  697:     */ 
/*  698: 917 */       HashMap<String, Producto> hashMapProducto = new HashMap();
/*  699: 918 */       List<Producto> listaProducto = obtenerListaCombo("", false, null);
/*  700: 919 */       for (Object localObject = listaProducto.iterator(); ((Iterator)localObject).hasNext();)
/*  701:     */       {
/*  702: 919 */         prod = (Producto)((Iterator)localObject).next();
/*  703: 920 */         hashMapProducto.put(prod.getCodigo(), prod);
/*  704:     */       }
/*  705: 924 */       localObject = datos;Producto prod = localObject.length;
/*  706: 924 */       for (Producto localProducto1 = 0; localProducto1 < prod; localProducto1++)
/*  707:     */       {
/*  708: 924 */         HSSFCell[] fila = localObject[localProducto1];
/*  709: 925 */         codigoProducto = fila[0].getStringCellValue();
/*  710: 926 */         nombreProducto = fila[1].getStringCellValue();
/*  711: 927 */         BigDecimal costoEstandar = BigDecimal.valueOf(fila[2].getNumericCellValue());
/*  712: 929 */         if (!hashMapProducto.containsKey(codigoProducto)) {
/*  713: 930 */           throw new ExcepcionAS2Inventario("msg_producto_no_encontrado", nombreProducto);
/*  714:     */         }
/*  715: 932 */         Producto producto = (Producto)hashMapProducto.get(codigoProducto);
/*  716: 933 */         producto.setCostoEstandar(costoEstandar);
/*  717: 934 */         this.productoDao.guardar(producto);
/*  718:     */         
/*  719: 936 */         filaActual++;
/*  720:     */       }
/*  721:     */     }
/*  722:     */     catch (IllegalArgumentException e)
/*  723:     */     {
/*  724: 940 */       this.context.setRollbackOnly();
/*  725:     */       
/*  726: 942 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/*  727:     */     }
/*  728:     */     catch (ExcepcionAS2Inventario e)
/*  729:     */     {
/*  730: 945 */       this.context.setRollbackOnly();
/*  731: 946 */       throw e;
/*  732:     */     }
/*  733:     */     catch (IllegalStateException e)
/*  734:     */     {
/*  735: 949 */       this.context.setRollbackOnly();
/*  736:     */       
/*  737: 951 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/*  738:     */     }
/*  739:     */     catch (Exception e)
/*  740:     */     {
/*  741: 953 */       this.context.setRollbackOnly();
/*  742: 954 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/*  743:     */     }
/*  744:     */   }
/*  745:     */   
/*  746:     */   public Producto getProductoCategoriaImpuesto(Producto producto)
/*  747:     */     throws ExcepcionAS2
/*  748:     */   {
/*  749: 966 */     return this.productoDao.getProductoCategoriaImpuesto(producto);
/*  750:     */   }
/*  751:     */   
/*  752:     */   public Producto buscarPorAtributo(Map<String, String> filters)
/*  753:     */     throws ExcepcionAS2
/*  754:     */   {
/*  755: 976 */     return this.productoDao.buscarPorAtributo(filters);
/*  756:     */   }
/*  757:     */   
/*  758:     */   public List<Producto> obtenerListaPos(String sortField, boolean sortOrder, Map<String, String> filters)
/*  759:     */   {
/*  760: 981 */     return this.productoDao.obtenerListaPos(sortField, sortOrder, filters);
/*  761:     */   }
/*  762:     */   
/*  763:     */   public List<ProductoEstadoImportacion> obtenerListaProductoEstadoImportacion(int idProducto, Date fecha)
/*  764:     */   {
/*  765: 991 */     List<Object[]> lista = this.productoDao.obtenerListaProductoEstadoImportacion(idProducto, fecha);
/*  766: 992 */     List<ProductoEstadoImportacion> listaProductoEstadoImportacion = new ArrayList();
/*  767: 993 */     for (Object[] dato : lista)
/*  768:     */     {
/*  769: 994 */       ProductoEstadoImportacion productoEstadoImportacion = new ProductoEstadoImportacion();
/*  770: 995 */       productoEstadoImportacion.setProcesoImportacion((String)dato[0]);
/*  771: 996 */       productoEstadoImportacion.setEstadoImportacion(((Estado)dato[1]).getNombre());
/*  772: 997 */       productoEstadoImportacion.setCantidad((BigDecimal)dato[2]);
/*  773: 998 */       productoEstadoImportacion.setTransporte(((MedioTransporteEnum)dato[3]).getNombre());
/*  774: 999 */       productoEstadoImportacion.setFecha((Date)dato[4]);
/*  775:1000 */       listaProductoEstadoImportacion.add(productoEstadoImportacion);
/*  776:     */     }
/*  777:1002 */     return listaProductoEstadoImportacion;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public List<ProductoAtributo> obtenerListaProductoAtributo(int idProducto)
/*  781:     */   {
/*  782:1012 */     return this.productoDao.obtenerListaProductoAtributo(idProducto);
/*  783:     */   }
/*  784:     */   
/*  785:     */   public Producto buscarProductoPorCodigoProductoLote(String codigo, int idOrganizacion, DocumentoBase documentoBase)
/*  786:     */     throws ExcepcionAS2
/*  787:     */   {
/*  788:1022 */     Producto producto = null;
/*  789:     */     try
/*  790:     */     {
/*  791:1025 */       producto = buscarPorCodigo(codigo, idOrganizacion, documentoBase);
/*  792:     */     }
/*  793:     */     catch (ExcepcionAS2 e)
/*  794:     */     {
/*  795:     */       try
/*  796:     */       {
/*  797:1029 */         Lote lote = this.servicioLote.buscarPorCodigo(codigo);
/*  798:1030 */         producto = buscarPorCodigo(lote.getProducto().getCodigo(), idOrganizacion, documentoBase);
/*  799:1031 */         producto.setLote(lote);
/*  800:     */       }
/*  801:     */       catch (Exception e2)
/*  802:     */       {
/*  803:     */         try
/*  804:     */         {
/*  805:1035 */           Map<String, String> filters = new HashMap();
/*  806:1036 */           filters.put("codigoBarras", "=" + codigo);
/*  807:1037 */           producto = buscarPorAtributo(filters);
/*  808:     */         }
/*  809:     */         catch (ExcepcionAS2 e3)
/*  810:     */         {
/*  811:1039 */           throw e3;
/*  812:     */         }
/*  813:1042 */         if (producto == null) {
/*  814:1043 */           throw e;
/*  815:     */         }
/*  816:     */       }
/*  817:     */     }
/*  818:1049 */     return producto;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public BigDecimal getSaldoLote(int idProducto, int idBodega, int idLote, Date fecha)
/*  822:     */   {
/*  823:1059 */     return this.productoDao.getSaldoLote(idProducto, idBodega, idLote, fecha);
/*  824:     */   }
/*  825:     */   
/*  826:     */   public List<SubProducto> getListaSubproducto(Producto producto)
/*  827:     */   {
/*  828:1069 */     return this.productoDao.getListaSubproducto(producto);
/*  829:     */   }
/*  830:     */   
/*  831:     */   public List<Producto> obtenerListaPorPagina(FiltroProducto filtroProducto, int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filter, String nombreUsuario)
/*  832:     */   {
/*  833:1084 */     List<Producto> lista = this.productoDao.obtenerListaPorPagina(filtroProducto, startIndex, pageSize, filter, this.usuarioDao
/*  834:1085 */       .buscarVisualizacionPorNombreUsuario(nombreUsuario));
/*  835:1086 */     List<Producto> listaProducto = new ArrayList();
/*  836:1089 */     if ((filtroProducto.isIndicadorAtributo1()) || (filtroProducto.isIndicadorAtributo2()) || (filtroProducto.isIndicadorAtributo3()) || 
/*  837:1090 */       (filtroProducto.isIndicadorAtributo4()) || (filtroProducto.isIndicadorAtributo5()) || (filtroProducto.isIndicadorAtributo6()) || 
/*  838:1091 */       (filtroProducto.isIndicadorAtributo7()) || (filtroProducto.isIndicadorAtributo8()) || (filtroProducto.isIndicadorAtributo9()) || 
/*  839:1092 */       (filtroProducto.isIndicadorAtributo10()))
/*  840:     */     {
/*  841:1094 */       HashMap<Integer, Producto> hmProducto = new HashMap();
/*  842:1095 */       for (Producto p : lista)
/*  843:     */       {
/*  844:1096 */         Producto producto = (Producto)hmProducto.get(Integer.valueOf(p.getIdProducto()));
/*  845:1097 */         if (producto == null)
/*  846:     */         {
/*  847:1098 */           hmProducto.put(Integer.valueOf(p.getIdProducto()), p);
/*  848:     */         }
/*  849:     */         else
/*  850:     */         {
/*  851:1101 */           if (!p.getAtributo1().isEmpty()) {
/*  852:1102 */             producto.setAtributo1(p.getAtributo1());
/*  853:     */           }
/*  854:1104 */           if (!p.getAtributo2().isEmpty()) {
/*  855:1105 */             producto.setAtributo2(p.getAtributo2());
/*  856:     */           }
/*  857:1107 */           if (!p.getAtributo3().isEmpty()) {
/*  858:1108 */             producto.setAtributo3(p.getAtributo3());
/*  859:     */           }
/*  860:1110 */           if (!p.getAtributo4().isEmpty()) {
/*  861:1111 */             producto.setAtributo4(p.getAtributo4());
/*  862:     */           }
/*  863:1113 */           if (!p.getAtributo5().isEmpty()) {
/*  864:1114 */             producto.setAtributo5(p.getAtributo5());
/*  865:     */           }
/*  866:1116 */           if (!p.getAtributo6().isEmpty()) {
/*  867:1117 */             producto.setAtributo6(p.getAtributo6());
/*  868:     */           }
/*  869:1119 */           if (!p.getAtributo7().isEmpty()) {
/*  870:1120 */             producto.setAtributo7(p.getAtributo7());
/*  871:     */           }
/*  872:1122 */           if (!p.getAtributo8().isEmpty()) {
/*  873:1123 */             producto.setAtributo8(p.getAtributo8());
/*  874:     */           }
/*  875:1125 */           if (!p.getAtributo9().isEmpty()) {
/*  876:1126 */             producto.setAtributo9(p.getAtributo9());
/*  877:     */           }
/*  878:1128 */           if (!p.getAtributo10().isEmpty()) {
/*  879:1129 */             producto.setAtributo10(p.getAtributo10());
/*  880:     */           }
/*  881:     */         }
/*  882:     */       }
/*  883:1133 */       listaProducto.addAll(hmProducto.values());
/*  884:     */     }
/*  885:     */     else
/*  886:     */     {
/*  887:1135 */       listaProducto = lista;
/*  888:     */     }
/*  889:1139 */     if (sortField != null) {
/*  890:1140 */       FuncionesUtiles.ordenaLista(listaProducto, sortField, sortOrder);
/*  891:     */     }
/*  892:1143 */     return listaProducto;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public int contarPorCriterio(FiltroProducto filtroProducto, Map<String, String> filter)
/*  896:     */   {
/*  897:1154 */     return this.productoDao.contarPorCriterio(filtroProducto, filter);
/*  898:     */   }
/*  899:     */   
/*  900:     */   public BigDecimal getInventarioComprometido(Producto producto, Lote lote, Bodega bodega, Date fecha, boolean indicadorProduccion)
/*  901:     */   {
/*  902:1164 */     return this.productoDao.getInventarioComprometido(producto, lote, bodega, fecha, indicadorProduccion);
/*  903:     */   }
/*  904:     */   
/*  905:     */   public List getReporteSaldosMinimos(int idOrganizacion, int idCategoria, int idSubcategoria, Bodega bodega, List<Bodega> listaBodega, boolean indicadorBajoSaldoMinimo, String valorAtributo)
/*  906:     */   {
/*  907:1170 */     return this.productoBodegaDao.getReporteSaldosMinimos(idOrganizacion, idCategoria, idSubcategoria, bodega, listaBodega, indicadorBajoSaldoMinimo, valorAtributo);
/*  908:     */   }
/*  909:     */   
/*  910:     */   public List<ProductoBodega> getListaProductoBodegaVacios(Producto producto)
/*  911:     */   {
/*  912:1176 */     return this.productoBodegaDao.getListaProductoBodegaVacios(producto);
/*  913:     */   }
/*  914:     */   
/*  915:     */   public Producto cargarDetalleListaMaterial(Producto producto)
/*  916:     */   {
/*  917:1181 */     return this.productoDao.cargarDetalleListaMaterial(producto);
/*  918:     */   }
/*  919:     */   
/*  920:     */   public Producto cargarDetalleListaVariableCalidadProducto(Producto producto)
/*  921:     */   {
/*  922:1186 */     return this.productoDao.cargarDetalleListaVariableCalidadProducto(producto);
/*  923:     */   }
/*  924:     */   
/*  925:     */   public NodoArbol<Producto> obtenerArbolComponentes(Producto producto)
/*  926:     */   {
/*  927:1191 */     return obtenerArbolComponentes(producto, false);
/*  928:     */   }
/*  929:     */   
/*  930:     */   public NodoArbol<Producto> obtenerArbolComponentes(Producto producto, boolean indicadorGeneraSuborden)
/*  931:     */   {
/*  932:1196 */     BigDecimal cantidadProducir = producto.getCantidadProducir();
/*  933:1197 */     if (producto == null) {
/*  934:1198 */       return null;
/*  935:     */     }
/*  936:1200 */     producto = this.productoDao.cargarDetalleListaMaterial(producto);
/*  937:1201 */     producto.setCantidadProducir(cantidadProducir);
/*  938:     */     
/*  939:1203 */     NodoArbol<Producto> nodo = new NodoArbol(producto, null);
/*  940:1205 */     for (ProductoMaterial productoMaterial : producto.getListaProductoMaterial()) {
/*  941:1206 */       if ((productoMaterial.isActivo()) && (((!indicadorGeneraSuborden) && (!productoMaterial.isIndicadorGeneraSuborden())) || ((indicadorGeneraSuborden) && 
/*  942:1207 */         (productoMaterial.isIndicadorGeneraSuborden()))))
/*  943:     */       {
/*  944:1208 */         List<NodoArbol<Producto>> hijos = obtenerArbolComponentesRecursivo(productoMaterial, nodo, null, indicadorGeneraSuborden);
/*  945:1209 */         for (NodoArbol<Producto> hijo : hijos) {
/*  946:1210 */           nodo.addHijo(hijo);
/*  947:     */         }
/*  948:     */       }
/*  949:1212 */       else if (productoMaterial.isActivo())
/*  950:     */       {
/*  951:1213 */         List<NodoArbol<Producto>> hijos = obtenerArbolComponentesRecursivo(productoMaterial, nodo, null, false);
/*  952:1214 */         for (NodoArbol<Producto> hijo : hijos) {
/*  953:1215 */           nodo.addHijo(hijo);
/*  954:     */         }
/*  955:     */       }
/*  956:     */     }
/*  957:1219 */     actualizarCantidadesProducir(nodo, null);
/*  958:1220 */     return nodo;
/*  959:     */   }
/*  960:     */   
/*  961:     */   private List<NodoArbol<Producto>> obtenerArbolComponentesRecursivo(ProductoMaterial productoMaterial, NodoArbol<Producto> padre, BigDecimal proporcionHeredada, boolean indicadorGeneraSuborden)
/*  962:     */   {
/*  963:1226 */     BigDecimal proporcionReceta = (proporcionHeredada != null) && (productoMaterial.getIndicadorPrincipal().booleanValue()) ? proporcionHeredada : productoMaterial.getProporcion();
/*  964:1227 */     List<NodoArbol<Producto>> listaNodo = new ArrayList();
/*  965:1228 */     Producto materialPrincipal = productoMaterial.getMaterial();
/*  966:1229 */     materialPrincipal.getId();
/*  967:     */     
/*  968:1231 */     NodoArbol<Producto> nodo = new NodoArbol(materialPrincipal, padre);
/*  969:1232 */     nodo.getPropiedades().put("ProductoMaterial", productoMaterial);
/*  970:1233 */     nodo.getPropiedades().put("Sustituto", Boolean.valueOf(false));
/*  971:1235 */     for (ProductoMaterial productoMaterial2 : materialPrincipal.getListaProductoMaterial()) {
/*  972:1236 */       if ((productoMaterial2.isActivo()) && (
/*  973:1237 */         (productoMaterial.isIndicadorExplota()) || ((indicadorGeneraSuborden) && (productoMaterial.isIndicadorGeneraSuborden()))))
/*  974:     */       {
/*  975:1238 */         hijos = obtenerArbolComponentesRecursivo(productoMaterial2, nodo, productoMaterial
/*  976:1239 */           .getProporcionMaterialPrincipalHijo(), indicadorGeneraSuborden);
/*  977:1240 */         for (NodoArbol<Producto> hijo : hijos) {
/*  978:1241 */           nodo.addHijo(hijo);
/*  979:     */         }
/*  980:     */       }
/*  981:     */     }
/*  982:     */     List<NodoArbol<Producto>> hijos;
/*  983:1245 */     listaNodo.add(nodo);
/*  984:1246 */     if ((productoMaterial.getSustituto() == null) || (
/*  985:     */     
/*  986:     */ 
/*  987:1249 */       (proporcionReceta.compareTo(new BigDecimal(100)) < 0) && (productoMaterial.getSustituto() != null)))
/*  988:     */     {
/*  989:1250 */       Producto materialSustituto = productoMaterial.getSustituto();
/*  990:1251 */       materialSustituto.getId();
/*  991:     */       
/*  992:1253 */       NodoArbol<Producto> nodoSustituto = new NodoArbol(materialSustituto, padre);
/*  993:1254 */       nodoSustituto.getPropiedades().put("ProductoMaterial", productoMaterial);
/*  994:1255 */       nodoSustituto.getPropiedades().put("Sustituto", Boolean.valueOf(true));
/*  995:1257 */       for (ProductoMaterial productoMaterial2 : materialSustituto.getListaProductoMaterial()) {
/*  996:1258 */         if ((productoMaterial2.isActivo()) && (
/*  997:1259 */           (productoMaterial.isIndicadorExplota()) || ((indicadorGeneraSuborden) && (productoMaterial.isIndicadorGeneraSuborden()))))
/*  998:     */         {
/*  999:1260 */           List<NodoArbol<Producto>> hijos = obtenerArbolComponentesRecursivo(productoMaterial2, nodoSustituto, productoMaterial
/* 1000:1261 */             .getProporcionMaterialPrincipalHijo(), indicadorGeneraSuborden);
/* 1001:1262 */           for (NodoArbol<Producto> hijo : hijos) {
/* 1002:1263 */             nodoSustituto.addHijo(hijo);
/* 1003:     */           }
/* 1004:     */         }
/* 1005:     */       }
/* 1006:1267 */       listaNodo.add(nodoSustituto);
/* 1007:     */     }
/* 1008:1270 */     return listaNodo;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public void actualizarCantidadesProducir(NodoArbol<Producto> arbol, BigDecimal proporcionHeredada)
/* 1012:     */   {
/* 1013:1275 */     BigDecimal cantidadProducir = ((Producto)arbol.getValor()).getCantidadProducir();
/* 1014:1276 */     BigDecimal cantidadBoom = ((Producto)arbol.getValor()).getCantidadProduccion();
/* 1015:1277 */     BigDecimal proporcionBoom = BigDecimal.ZERO;
/* 1016:1278 */     if (cantidadBoom.compareTo(BigDecimal.ZERO) != 0) {
/* 1017:1279 */       proporcionBoom = cantidadProducir.divide(cantidadBoom, 2, RoundingMode.HALF_UP);
/* 1018:     */     }
/* 1019:1281 */     for (NodoArbol<Producto> nodo : arbol.getHijos())
/* 1020:     */     {
/* 1021:1282 */       ProductoMaterial productoMaterial = (ProductoMaterial)nodo.getPropiedades().get("ProductoMaterial");
/* 1022:     */       
/* 1023:1284 */       BigDecimal proporcionReceta = (proporcionHeredada != null) && (productoMaterial.getIndicadorPrincipal().booleanValue()) ? proporcionHeredada : productoMaterial.getProporcion();
/* 1024:1285 */       boolean sustituto = ((Boolean)nodo.getPropiedades().get("Sustituto")).booleanValue();
/* 1025:1286 */       if (productoMaterial != null)
/* 1026:     */       {
/* 1027:1287 */         BigDecimal cantidad = BigDecimal.ZERO;
/* 1028:1288 */         if (!sustituto)
/* 1029:     */         {
/* 1030:1289 */           cantidad = productoMaterial.getCantidad();
/* 1031:     */         }
/* 1032:     */         else
/* 1033:     */         {
/* 1034:1291 */           cantidad = productoMaterial.getCantidadSustituto();
/* 1035:1292 */           proporcionReceta = new BigDecimal(100).subtract(proporcionReceta);
/* 1036:     */         }
/* 1037:1294 */         BigDecimal cantidadReceta = proporcionReceta.multiply(cantidad).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
/* 1038:     */         
/* 1039:1296 */         ((Producto)nodo.getValor()).setCantidadProducir(cantidadReceta.multiply(proporcionBoom));
/* 1040:1297 */         ((Producto)nodo.getValor()).setValorReceta(productoMaterial.getCantidad());
/* 1041:     */       }
/* 1042:1299 */       actualizarCantidadesProducir(nodo, productoMaterial.getProporcionMaterialPrincipalHijo());
/* 1043:     */     }
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public UnidadManejoProducto getUnidadManejoProducto(Producto producto, UnidadManejo unidadManejo)
/* 1047:     */   {
/* 1048:1305 */     return this.productoDao.getUnidadManejoProducto(producto, unidadManejo);
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public List<ProductoMaterial> getProductosCambioAngilProporcionProduccion(int idOrganizacion, SubcategoriaProducto subcategoriaProducto, CategoriaProducto categoriaProducto, MarcaProducto marcaProducto, Producto material)
/* 1052:     */   {
/* 1053:1311 */     return this.productoDao.getProductosCambioAngilProporcionProduccion(idOrganizacion, subcategoriaProducto, categoriaProducto, marcaProducto, material);
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public List<UnidadManejo> obtenerListaUnidadManejoPorProducto(Producto producto)
/* 1057:     */   {
/* 1058:1317 */     return this.productoDao.obtenerListaUnidadManejoPorProducto(producto);
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public List<SaldoProducto> obtenerProductosConSaldoBodega(Bodega bodega, Date fecha, boolean indicadorTomaFisica)
/* 1062:     */   {
/* 1063:1322 */     return this.productoDao.obtenerProductosConSaldoBodega(bodega, fecha, indicadorTomaFisica, false);
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public List<SaldoProductoLote> obtenerProductosConSaldoBodegaLote(Bodega bodega, Date fecha, Producto producto, boolean indicadorTomaFisica)
/* 1067:     */   {
/* 1068:1327 */     return this.productoDao.obtenerProductosConSaldoBodegaLote(bodega, fecha, producto, indicadorTomaFisica);
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public Bodega obtenerBodegaTrabajoProducto(Producto producto, Integer idSucursal)
/* 1072:     */   {
/* 1073:1332 */     List<Bodega> listaBodega = this.bodegaTrabajoProductoSucursalDao.obtenerBodegaTrabajoProducto(producto, idSucursal);
/* 1074:1333 */     if (listaBodega.size() > 0) {
/* 1075:1334 */       return (Bodega)listaBodega.get(0);
/* 1076:     */     }
/* 1077:1336 */     return null;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public List<Bodega> obtenerListaBodegasTrabajoProducto(Producto producto, Integer idSucursal)
/* 1081:     */   {
/* 1082:1342 */     return this.bodegaTrabajoProductoSucursalDao.obtenerBodegaTrabajoProducto(producto, idSucursal);
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public List<Producto> getListaProductos(int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, List<Producto> listaMaterial)
/* 1086:     */   {
/* 1087:1348 */     List<Producto> listaProductos = new ArrayList();
/* 1088:1349 */     HashMap<String, Producto> hmProducto = new HashMap();
/* 1089:1350 */     int z = listaMaterial.size();
/* 1090:1351 */     for (Producto producto : this.productoDao.getListaProductos(idOrganizacion, categoriaProducto, subcategoriaProducto))
/* 1091:     */     {
/* 1092:1352 */       List<ProductoMaterial> listaProductoMaterial = getListaProductoMaterial(producto);
/* 1093:1353 */       i = 0;
/* 1094:1354 */       for (ProductoMaterial pm : listaProductoMaterial)
/* 1095:     */       {
/* 1096:1355 */         for (Producto material : listaMaterial) {
/* 1097:1356 */           if (pm.getMaterial().getCodigo().equals(material.getCodigo())) {
/* 1098:1357 */             i++;
/* 1099:     */           }
/* 1100:     */         }
/* 1101:1361 */         if (z == i)
/* 1102:     */         {
/* 1103:1362 */           Producto productoAux = (Producto)hmProducto.get(pm.getProducto().getCodigo());
/* 1104:1363 */           if (productoAux == null) {
/* 1105:1364 */             hmProducto.put(pm.getProducto().getCodigo(), pm.getProducto());
/* 1106:     */           }
/* 1107:     */         }
/* 1108:     */       }
/* 1109:     */     }
/* 1110:     */     int i;
/* 1111:1371 */     listaProductos.addAll(hmProducto.values());
/* 1112:     */     
/* 1113:1373 */     return listaProductos;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public List<ProductoMaterial> getListaProductoMaterial(Producto producto)
/* 1117:     */   {
/* 1118:1378 */     return this.productoDao.getListaProductoMaterial(producto);
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public List<Producto> getListaMaterialesComunes(List<Producto> listaMateriales)
/* 1122:     */   {
/* 1123:1383 */     List<ProductoMaterial> listaProductoMaterialesComunes = new ArrayList();
/* 1124:1384 */     int numeroRepetirse = listaMateriales.size() - 1;
/* 1125:1385 */     HashMap<Producto, ProductoMaterial> hmMaterilesComunes = new HashMap();
/* 1126:1386 */     for (Iterator localIterator1 = listaMateriales.iterator(); localIterator1.hasNext();)
/* 1127:     */     {
/* 1128:1386 */       producto = (Producto)localIterator1.next();
/* 1129:1387 */       List<ProductoMaterial> listaProductoMaterial = getListaProductoMaterial(producto);
/* 1130:1388 */       for (ProductoMaterial pm : listaProductoMaterial)
/* 1131:     */       {
/* 1132:1389 */         ProductoMaterial pmAux = (ProductoMaterial)hmMaterilesComunes.get(pm.getMaterial());
/* 1133:1390 */         if (pmAux != null)
/* 1134:     */         {
/* 1135:1391 */           pmAux.setNumeroRepetriciones(pmAux.getNumeroRepetriciones() + 1);
/* 1136:1392 */           hmMaterilesComunes.put(pm.getMaterial(), pmAux);
/* 1137:     */         }
/* 1138:     */         else
/* 1139:     */         {
/* 1140:1394 */           hmMaterilesComunes.put(pm.getMaterial(), pm);
/* 1141:     */         }
/* 1142:     */       }
/* 1143:     */     }
/* 1144:     */     Producto producto;
/* 1145:1399 */     Object listaMaterialesComunes = new ArrayList();
/* 1146:1400 */     listaProductoMaterialesComunes.addAll(hmMaterilesComunes.values());
/* 1147:1401 */     listaMaterialesComunes = new ArrayList();
/* 1148:1402 */     for (ProductoMaterial pm : listaProductoMaterialesComunes) {
/* 1149:1403 */       if ((numeroRepetirse > 0) && (numeroRepetirse == pm.getNumeroRepetriciones()) && (!pm.getMaterial().isIndicadorConsumoDirecto())) {
/* 1150:1404 */         ((List)listaMaterialesComunes).add(pm.getMaterial());
/* 1151:     */       }
/* 1152:     */     }
/* 1153:1408 */     return listaMaterialesComunes;
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void procesarNuevosPorcentajes(List<Producto> listaMateriales, List<Producto> listaMaterialesComunes)
/* 1157:     */     throws AS2Exception
/* 1158:     */   {
/* 1159:1414 */     validarPorcentajeMateriales(listaMaterialesComunes);
/* 1160:1416 */     for (Producto producto : listaMateriales)
/* 1161:     */     {
/* 1162:1417 */       List<ProductoMaterial> listaProductoMaterial = getListaProductoMaterial(producto);
/* 1163:1418 */       sumaTotalEntreProductosMaterial = sumaTotalEntreProductosMaterial(listaProductoMaterial, listaMaterialesComunes);
/* 1164:1419 */       for (localIterator2 = listaProductoMaterial.iterator(); localIterator2.hasNext();)
/* 1165:     */       {
/* 1166:1419 */         pm = (ProductoMaterial)localIterator2.next();
/* 1167:1420 */         for (Producto material : listaMaterialesComunes) {
/* 1168:1421 */           if (material.getCodigo().equals(pm.getMaterial().getCodigo()))
/* 1169:     */           {
/* 1170:1422 */             BigDecimal nuevaCantidad = BigDecimal.ZERO;
/* 1171:     */             
/* 1172:1424 */             nuevaCantidad = sumaTotalEntreProductosMaterial.multiply(material.getPorcentajeReceta()).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP);
/* 1173:     */             
/* 1174:1426 */             pm.setCantidad(nuevaCantidad);
/* 1175:1427 */             actualizarCantidadProductoMaterial(pm);
/* 1176:     */           }
/* 1177:     */         }
/* 1178:     */       }
/* 1179:     */     }
/* 1180:     */     BigDecimal sumaTotalEntreProductosMaterial;
/* 1181:     */     Iterator localIterator2;
/* 1182:     */     ProductoMaterial pm;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public void agregarNuevosMateriales(List<Producto> listaMateriales, List<Producto> listaMaterialesComunes)
/* 1186:     */     throws AS2Exception
/* 1187:     */   {
/* 1188:1438 */     for (Iterator localIterator1 = listaMateriales.iterator(); localIterator1.hasNext();)
/* 1189:     */     {
/* 1190:1438 */       producto = (Producto)localIterator1.next();
/* 1191:1439 */       List<ProductoMaterial> listaProductoMaterial = getListaProductoMaterial(producto);
/* 1192:1440 */       hmMaterialesPorProducto = new HashMap();
/* 1193:1441 */       for (ProductoMaterial pm : listaProductoMaterial) {
/* 1194:1442 */         hmMaterialesPorProducto.put(pm.getMaterial().getCodigo(), pm.getMaterial());
/* 1195:     */       }
/* 1196:1444 */       for (Producto material : listaMaterialesComunes)
/* 1197:     */       {
/* 1198:1445 */         Producto materialAux = (Producto)hmMaterialesPorProducto.get(material.getCodigo());
/* 1199:1446 */         if (materialAux == null)
/* 1200:     */         {
/* 1201:1447 */           Date fecha = new Date();
/* 1202:1448 */           fecha = FuncionesUtiles.getFechaInicioMes(fecha);
/* 1203:1449 */           ProductoMaterial productoMaterial = new ProductoMaterial();
/* 1204:1450 */           productoMaterial.setIdOrganizacion(producto.getIdOrganizacion());
/* 1205:1451 */           productoMaterial.setMaterial(material);
/* 1206:1452 */           productoMaterial.setFechaDesde(fecha);
/* 1207:1453 */           productoMaterial.setProducto(producto);
/* 1208:     */           
/* 1209:1455 */           this.productoMaterialDao.guardar(productoMaterial);
/* 1210:     */         }
/* 1211:     */       }
/* 1212:     */     }
/* 1213:     */     Producto producto;
/* 1214:     */     HashMap<String, Producto> hmMaterialesPorProducto;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public BigDecimal sumaTotalEntreProductosMaterial(List<ProductoMaterial> listaProductoMaterial, List<Producto> listaMaterialesComunes)
/* 1218:     */   {
/* 1219:1462 */     BigDecimal sumaTotalEntreProductosMaterial = BigDecimal.ZERO;
/* 1220:1463 */     for (Iterator localIterator1 = listaProductoMaterial.iterator(); localIterator1.hasNext();)
/* 1221:     */     {
/* 1222:1463 */       pm = (ProductoMaterial)localIterator1.next();
/* 1223:1464 */       for (Producto material : listaMaterialesComunes) {
/* 1224:1465 */         if (material.getCodigo().equals(pm.getMaterial().getCodigo())) {
/* 1225:1466 */           sumaTotalEntreProductosMaterial = sumaTotalEntreProductosMaterial.add(pm.getCantidad());
/* 1226:     */         }
/* 1227:     */       }
/* 1228:     */     }
/* 1229:     */     ProductoMaterial pm;
/* 1230:1470 */     return sumaTotalEntreProductosMaterial;
/* 1231:     */   }
/* 1232:     */   
/* 1233:     */   public void validarPorcentajeMateriales(List<Producto> listaMaterialesComunes)
/* 1234:     */     throws AS2Exception
/* 1235:     */   {
/* 1236:1475 */     BigDecimal total = BigDecimal.ZERO;
/* 1237:1476 */     for (Producto producto : listaMaterialesComunes) {
/* 1238:1477 */       total = total.add(producto.getPorcentajeReceta());
/* 1239:     */     }
/* 1240:1479 */     if (total.compareTo(new BigDecimal(100)) != 0) {
/* 1241:1480 */       throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_TOTAL_PORCENTAJE_IGUAL_CIEN", new String[] { "" });
/* 1242:     */     }
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public List<DetalleOrdenFabricacionMaterial> obtenerMaterialesDetalleOrdenFabricacionMaterial(OrdenFabricacion ordenFabricacion, Boolean indicadorHoja)
/* 1246:     */   {
/* 1247:1488 */     return this.ordenFabricacionDao.obtenerMaterialesDetalleOrdenFabricacionMaterial(ordenFabricacion, indicadorHoja);
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public List<OrdenFabricacion> buscarOrdenesFabricacionFormuladasPorProducto(Producto producto)
/* 1251:     */   {
/* 1252:1493 */     return this.ordenFabricacionDao.buscarOrdenesFabricacionFormuladasPorProducto(producto);
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public List<DetalleOrdenFabricacionMaterial> copiarFormula(OrdenFabricacion origen, OrdenFabricacion destino, BigDecimal cantidadActual)
/* 1256:     */     throws AS2Exception
/* 1257:     */   {
/* 1258:1499 */     BigDecimal proporcion = BigDecimal.ONE;
/* 1259:     */     
/* 1260:     */ 
/* 1261:1502 */     List<DetalleOrdenFabricacionMaterial> listaDetallesDestino = this.ordenFabricacionDao.obtenerMaterialesDetalleOrdenFabricacionMaterial(destino, null);
/* 1262:     */     
/* 1263:1504 */     DetalleOrdenFabricacionMaterial detallePrincipal = null;
/* 1264:1506 */     for (Iterator localIterator = listaDetallesDestino.iterator(); localIterator.hasNext();)
/* 1265:     */     {
/* 1266:1506 */       detalleDestino = (DetalleOrdenFabricacionMaterial)localIterator.next();
/* 1267:1507 */       if (detalleDestino.getDetalleOrdenFabricacionMaterialPadre() != null) {
/* 1268:1508 */         detalleDestino.setEliminado(true);
/* 1269:     */       } else {
/* 1270:1510 */         detallePrincipal = detalleDestino;
/* 1271:     */       }
/* 1272:     */     }
/* 1273:     */     DetalleOrdenFabricacionMaterial detalleDestino;
/* 1274:1513 */     if (detallePrincipal == null) {
/* 1275:1514 */       detallePrincipal = crearDetalle(destino, listaDetallesDestino, null, destino.getProducto(), cantidadActual, null);
/* 1276:     */     }
/* 1277:1516 */     Object listaDetallesOrigen = this.ordenFabricacionDao.obtenerMaterialesDetalleOrdenFabricacionMaterial(origen, null);
/* 1278:1520 */     for (DetalleOrdenFabricacionMaterial detalleOrigen : (List)listaDetallesOrigen) {
/* 1279:1521 */       if ((!detalleOrigen.isIndicadorHoja()) && (detalleOrigen.getDetalleOrdenFabricacionMaterialPadre() == null))
/* 1280:     */       {
/* 1281:1523 */         proporcion = cantidadActual.divide(detalleOrigen.getCantidad(), 6, RoundingMode.HALF_UP);
/* 1282:1524 */         break;
/* 1283:     */       }
/* 1284:     */     }
/* 1285:1528 */     for (DetalleOrdenFabricacionMaterial detalleOrigen : (List)listaDetallesOrigen) {
/* 1286:1529 */       if (detalleOrigen.isIndicadorHoja())
/* 1287:     */       {
/* 1288:1530 */         BigDecimal cantidadProporcional = detalleOrigen.getCantidad().multiply(proporcion).setScale(2, RoundingMode.HALF_UP);
/* 1289:1531 */         crearDetalle(destino, listaDetallesDestino, detallePrincipal, detalleOrigen.getMaterial(), cantidadProporcional, null);
/* 1290:     */       }
/* 1291:     */     }
/* 1292:1534 */     return listaDetallesDestino;
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public DetalleOrdenFabricacionMaterial crearDetalle(OrdenFabricacion ordenFabricacion, List<DetalleOrdenFabricacionMaterial> listaDetalleOrdenFabricacionMaterial, DetalleOrdenFabricacionMaterial detallePrincipal, Producto producto, BigDecimal cantidad, SaldoProductoLote saldoProductoLote)
/* 1296:     */     throws AS2Exception
/* 1297:     */   {
/* 1298:1542 */     listaDetalleOrdenFabricacionMaterial = listaDetalleOrdenFabricacionMaterial == null ? new ArrayList() : listaDetalleOrdenFabricacionMaterial;
/* 1299:     */     
/* 1300:     */ 
/* 1301:     */ 
/* 1302:1546 */     DetalleOrdenFabricacionMaterial detalle = new DetalleOrdenFabricacionMaterial();
/* 1303:1547 */     detalle.setIdOrganizacion(ordenFabricacion.getIdOrganizacion());
/* 1304:1548 */     detalle.setSucursal(ordenFabricacion.getSucursal());
/* 1305:1549 */     detalle.setDetalleOrdenFabricacionMaterialPadre(detallePrincipal);
/* 1306:1550 */     detalle.setIndicadorHoja(detallePrincipal != null);
/* 1307:1551 */     detalle.setActivo(true);
/* 1308:1552 */     detalle.setMaterial(producto);
/* 1309:1553 */     detalle.setIndicadorConsumoDirecto(producto.isIndicadorConsumoDirecto());
/* 1310:1554 */     detalle.setOrdenFabricacion(ordenFabricacion);
/* 1311:1555 */     detalle.setUnidad(producto.getUnidadAlmacenamiento());
/* 1312:1556 */     detalle.setNivel(detallePrincipal == null ? 0 : 1);
/* 1313:1557 */     detalle.setCantidad(cantidad);
/* 1314:1558 */     detalle.setCantidadPorCadaBatch(cantidad);
/* 1315:1561 */     if (detalle.isIndicadorHoja())
/* 1316:     */     {
/* 1317:1563 */       detalle.setBodegaTrabajoMaterial(obtenerBodegaTrabajoProducto(producto, Integer.valueOf(ordenFabricacion.getSucursal().getId())));
/* 1318:1564 */       if (detalle.getBodegaTrabajoMaterial() == null) {
/* 1319:1565 */         throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.MENSAJE_INFO_PARAMETRIZAR_BODEGA_TRABAJO", new String[] { producto.getNombre() });
/* 1320:     */       }
/* 1321:1569 */       detalle.setCantidadDisponible(getSaldo(producto.getId(), detalle.getBodegaTrabajoMaterial().getId(), new Date()));
/* 1322:1572 */       if (producto.isIndicadorMezcla())
/* 1323:     */       {
/* 1324:1573 */         Producto p = cargarDetalleListaMezclaProducto(producto);
/* 1325:1574 */         for (MezclaProducto mezclaProducto : p.getListaMezclaProducto())
/* 1326:     */         {
/* 1327:1575 */           DetalleOrdenFabricacionMaterialMezcla detalleMezcla = new DetalleOrdenFabricacionMaterialMezcla();
/* 1328:1576 */           detalleMezcla.setDetalleOrdenFabricacionMaterial(detalle);
/* 1329:1577 */           detalleMezcla.setProducto(mezclaProducto.getProductoMezcla());
/* 1330:1578 */           detalleMezcla.setPorcentaje(mezclaProducto.getPorcentaje());
/* 1331:1579 */           detalleMezcla
/* 1332:1580 */             .setCantidad(cantidad.multiply(mezclaProducto.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 1333:1581 */           BigDecimal cantidadPorCadaBatch = detalle.getCantidadPorCadaBatch();
/* 1334:1582 */           detalleMezcla.setCantidadPorCadaBatch(cantidadPorCadaBatch
/* 1335:1583 */             .multiply(mezclaProducto.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 1336:1584 */           detalle.getListaDetalleOrdenFabricacionMaterialMezcla().add(detalleMezcla);
/* 1337:     */         }
/* 1338:     */       }
/* 1339:     */     }
/* 1340:1588 */     listaDetalleOrdenFabricacionMaterial.add(detalle);
/* 1341:1589 */     return detalle;
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public Producto cargaDetalle(int id, boolean cargarDetalle)
/* 1345:     */   {
/* 1346:1594 */     return this.productoDao.cargarDetalle(id, cargarDetalle);
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public Producto cargarDetalleOrdenFabricacion(int id)
/* 1350:     */   {
/* 1351:1599 */     return this.productoDao.cargarDetalleOrdenFabricacion(id);
/* 1352:     */   }
/* 1353:     */   
/* 1354:     */   public List<ProductoRutaFabricacionSucursal> getListaProductoRutaFabricacionSucursal(int id)
/* 1355:     */   {
/* 1356:1604 */     return this.productoDao.getListaProductoRutaFabricacionSucursal(id);
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   public List<SaldoProducto> obtenerProductosConSaldoBodega(Bodega bodega, Date fecha, boolean indicadorTomaFisica, boolean todoProducto)
/* 1360:     */   {
/* 1361:1609 */     return this.productoDao.obtenerProductosConSaldoBodega(bodega, fecha, indicadorTomaFisica, todoProducto);
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public Producto cargarProductoConAtributoInstancia(int id)
/* 1365:     */   {
/* 1366:1614 */     return this.productoDao.cargarProductoConAtributoInstancia(id);
/* 1367:     */   }
/* 1368:     */   
/* 1369:     */   public Producto cargarDetalleListaMezclaProducto(Producto producto)
/* 1370:     */   {
/* 1371:1619 */     return this.productoDao.cargarDetalleListaMezclaProducto(producto);
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public List<Producto> obtenerListaCombo(int idOrganizacion, String consulta, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/* 1375:     */   {
/* 1376:1625 */     return this.productoDao.getListaProductos(idOrganizacion, consulta, categoriaProducto, subcategoriaProducto);
/* 1377:     */   }
/* 1378:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl
 * JD-Core Version:    0.7.0.1
 */