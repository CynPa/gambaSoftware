/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.Atributo;
/*    4:     */ import com.asinfo.as2.entities.Bodega;
/*    5:     */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*    6:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*    7:     */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*    8:     */ import com.asinfo.as2.entities.CuentaContable;
/*    9:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   10:     */ import com.asinfo.as2.entities.DetalleVisualizacion;
/*   11:     */ import com.asinfo.as2.entities.FiltroProducto;
/*   12:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   13:     */ import com.asinfo.as2.entities.Lote;
/*   14:     */ import com.asinfo.as2.entities.MarcaProducto;
/*   15:     */ import com.asinfo.as2.entities.Producto;
/*   16:     */ import com.asinfo.as2.entities.ProductoAtributo;
/*   17:     */ import com.asinfo.as2.entities.ProductoBodega;
/*   18:     */ import com.asinfo.as2.entities.ProductoMaterial;
/*   19:     */ import com.asinfo.as2.entities.ProductoSustituto;
/*   20:     */ import com.asinfo.as2.entities.SaldoProducto;
/*   21:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   22:     */ import com.asinfo.as2.entities.SubProducto;
/*   23:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   24:     */ import com.asinfo.as2.entities.UnidadConversion;
/*   25:     */ import com.asinfo.as2.entities.UnidadManejo;
/*   26:     */ import com.asinfo.as2.entities.UnidadManejoProducto;
/*   27:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   28:     */ import com.asinfo.as2.entities.Visualizacion;
/*   29:     */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*   30:     */ import com.asinfo.as2.entities.produccion.MezclaProducto;
/*   31:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*   32:     */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
/*   33:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   34:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   35:     */ import com.asinfo.as2.enumeraciones.TipoCosto;
/*   36:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   37:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   38:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   39:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   40:     */ import java.io.PrintStream;
/*   41:     */ import java.math.BigDecimal;
/*   42:     */ import java.util.ArrayList;
/*   43:     */ import java.util.Date;
/*   44:     */ import java.util.HashMap;
/*   45:     */ import java.util.Iterator;
/*   46:     */ import java.util.LinkedHashMap;
/*   47:     */ import java.util.List;
/*   48:     */ import java.util.Map;
/*   49:     */ import java.util.Set;
/*   50:     */ import javax.ejb.EJB;
/*   51:     */ import javax.ejb.Stateless;
/*   52:     */ import javax.persistence.EntityManager;
/*   53:     */ import javax.persistence.NoResultException;
/*   54:     */ import javax.persistence.Query;
/*   55:     */ import javax.persistence.TypedQuery;
/*   56:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   57:     */ import javax.persistence.criteria.CriteriaQuery;
/*   58:     */ import javax.persistence.criteria.Expression;
/*   59:     */ import javax.persistence.criteria.Fetch;
/*   60:     */ import javax.persistence.criteria.Join;
/*   61:     */ import javax.persistence.criteria.JoinType;
/*   62:     */ import javax.persistence.criteria.Order;
/*   63:     */ import javax.persistence.criteria.Path;
/*   64:     */ import javax.persistence.criteria.Predicate;
/*   65:     */ import javax.persistence.criteria.Root;
/*   66:     */ 
/*   67:     */ @Stateless
/*   68:     */ public class ProductoDao
/*   69:     */   extends AbstractDaoAS2<Producto>
/*   70:     */ {
/*   71:     */   @EJB
/*   72:     */   private SaldoProductoDao saldoProductoDao;
/*   73:     */   @EJB
/*   74:     */   private SaldoProductoLoteDao saldoProductoLoteDao;
/*   75:     */   @EJB
/*   76:     */   private ProductoBodegaDao productoBodegaDao;
/*   77:     */   @EJB
/*   78:     */   private AtributoDao atributoDao;
/*   79:     */   @EJB
/*   80:     */   private VisualizacionDao visualizacionUsuarioDao;
/*   81:     */   
/*   82:     */   public ProductoDao()
/*   83:     */   {
/*   84:  96 */     super(Producto.class);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public Producto buscarPorCodigo(String codigo, int idOrganizacion, DocumentoBase documentoBase)
/*   88:     */     throws ExcepcionAS2
/*   89:     */   {
/*   90:     */     try
/*   91:     */     {
/*   92: 102 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   93: 103 */       CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*   94: 104 */       Root<Producto> from = criteriaQuery.from(Producto.class);
/*   95:     */       
/*   96: 106 */       from.fetch("subcategoriaProducto", JoinType.LEFT).fetch("categoriaProducto", JoinType.LEFT);
/*   97: 107 */       from.fetch("categoriaImpuesto", JoinType.LEFT);
/*   98: 108 */       from.fetch("partidaArancelaria", JoinType.LEFT);
/*   99: 109 */       from.fetch("unidad", JoinType.LEFT);
/*  100: 110 */       from.fetch("unidadCompra", JoinType.LEFT);
/*  101: 111 */       from.fetch("unidadVenta", JoinType.LEFT);
/*  102: 112 */       from.fetch("bodegaVenta", JoinType.LEFT);
/*  103: 113 */       from.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  104: 114 */       from.fetch("bodegaCompra", JoinType.LEFT);
/*  105:     */       
/*  106: 116 */       from.fetch("presentacionProducto", JoinType.LEFT);
/*  107: 117 */       from.fetch("marcaProducto", JoinType.LEFT);
/*  108:     */       
/*  109: 119 */       Map<String, String> filters = new HashMap();
/*  110: 120 */       filters.put("codigo", "=" + codigo);
/*  111: 121 */       filters.put("idOrganizacion", "=" + idOrganizacion);
/*  112: 122 */       if (documentoBase != null) {
/*  113: 123 */         getPeriodo(documentoBase, filters);
/*  114:     */       }
/*  115: 125 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  116: 126 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  117:     */       
/*  118: 128 */       CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  119: 129 */       TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  120: 130 */       return (Producto)typedQuery.getSingleResult();
/*  121:     */     }
/*  122:     */     catch (NoResultException e)
/*  123:     */     {
/*  124: 133 */       throw new ExcepcionAS2("msg_producto_no_encontrado", " " + codigo);
/*  125:     */     }
/*  126:     */   }
/*  127:     */   
/*  128:     */   private void getPeriodo(DocumentoBase documentoBase, Map<String, String> filters)
/*  129:     */   {
/*  130: 139 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$DocumentoBase[documentoBase.ordinal()])
/*  131:     */     {
/*  132:     */     case 1: 
/*  133:     */     case 2: 
/*  134:     */     case 3: 
/*  135:     */     case 4: 
/*  136:     */     case 5: 
/*  137:     */     case 6: 
/*  138: 146 */       filters.put("indicadorVenta", "true");
/*  139: 147 */       break;
/*  140:     */     case 7: 
/*  141:     */     case 8: 
/*  142:     */     case 9: 
/*  143:     */     case 10: 
/*  144:     */     case 11: 
/*  145: 154 */       filters.put("indicadorCompra", "true");
/*  146: 155 */       break;
/*  147:     */     case 12: 
/*  148: 157 */       filters.put("indicadorConsumo", "true");
/*  149:     */     }
/*  150:     */   }
/*  151:     */   
/*  152:     */   public Producto buscarPorCualquierCodigo(String codigo, int idOrganizacion)
/*  153:     */     throws ExcepcionAS2
/*  154:     */   {
/*  155: 164 */     StringBuilder jpql = new StringBuilder();
/*  156: 165 */     jpql = new StringBuilder();
/*  157: 166 */     jpql.append(" SELECT p ");
/*  158: 167 */     jpql.append(" FROM Producto p ");
/*  159: 168 */     jpql.append(" LEFT JOIN FETCH p.subcategoriaProducto ");
/*  160: 169 */     jpql.append(" LEFT JOIN FETCH p.categoriaImpuesto ");
/*  161: 170 */     jpql.append(" LEFT JOIN FETCH p.partidaArancelaria ");
/*  162: 171 */     jpql.append(" LEFT JOIN FETCH p.unidadCompra ");
/*  163: 172 */     jpql.append(" LEFT JOIN FETCH p.unidadVenta ");
/*  164: 173 */     jpql.append(" LEFT JOIN FETCH p.bodegaVenta ");
/*  165: 174 */     jpql.append(" LEFT JOIN FETCH p.unidadAlmacenamiento ");
/*  166: 175 */     jpql.append(" LEFT JOIN FETCH p.bodegaCompra ");
/*  167:     */     
/*  168: 177 */     jpql.append(" LEFT JOIN FETCH p.presentacionProducto ");
/*  169: 178 */     jpql.append(" LEFT JOIN FETCH p.marcaProducto ");
/*  170: 179 */     jpql.append(" WHERE p.idOrganizacion = :idOrganizacion ");
/*  171: 180 */     jpql.append(" AND ( p.codigoBarras =:codigo ");
/*  172: 181 */     jpql.append(" OR p.codigo =:codigo ");
/*  173: 182 */     jpql.append(" OR p.codigoAlterno =:codigo ");
/*  174: 183 */     jpql.append(" OR p.codigoComercial =:codigo )");
/*  175:     */     
/*  176: 185 */     Query query = this.em.createQuery(jpql.toString());
/*  177: 186 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  178: 187 */     query.setParameter("codigo", codigo);
/*  179:     */     
/*  180: 189 */     List<Producto> lista = query.getResultList();
/*  181: 190 */     if (lista.size() > 0) {
/*  182: 191 */       return (Producto)lista.get(0);
/*  183:     */     }
/*  184: 193 */     throw new ExcepcionAS2("msg_producto_no_encontrado", " " + codigo);
/*  185:     */   }
/*  186:     */   
/*  187:     */   public Producto buscarPorAtributo(Map<String, String> filters)
/*  188:     */     throws ExcepcionAS2
/*  189:     */   {
/*  190:     */     try
/*  191:     */     {
/*  192: 200 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  193: 201 */       CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  194: 202 */       Root<Producto> from = criteriaQuery.from(Producto.class);
/*  195:     */       
/*  196: 204 */       from.fetch("subcategoriaProducto", JoinType.LEFT);
/*  197: 205 */       from.fetch("categoriaImpuesto", JoinType.LEFT);
/*  198: 206 */       from.fetch("partidaArancelaria", JoinType.LEFT);
/*  199: 207 */       from.fetch("unidadCompra", JoinType.LEFT);
/*  200: 208 */       from.fetch("unidadVenta", JoinType.LEFT);
/*  201: 209 */       from.fetch("bodegaVenta", JoinType.LEFT);
/*  202: 210 */       from.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  203: 211 */       from.fetch("bodegaCompra", JoinType.LEFT);
/*  204: 212 */       from.fetch("presentacionProducto", JoinType.LEFT);
/*  205: 213 */       from.fetch("marcaProducto", JoinType.LEFT);
/*  206:     */       
/*  207: 215 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  208: 216 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  209:     */       
/*  210: 218 */       CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  211: 219 */       TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  212: 220 */       return (Producto)typedQuery.getSingleResult();
/*  213:     */     }
/*  214:     */     catch (NoResultException e)
/*  215:     */     {
/*  216: 223 */       throw new ExcepcionAS2("msg_producto_no_encontrado");
/*  217:     */     }
/*  218:     */   }
/*  219:     */   
/*  220:     */   public Producto buscarPorCodigoYCargarCuentasContables(String codigo)
/*  221:     */     throws ExcepcionAS2
/*  222:     */   {
/*  223:     */     try
/*  224:     */     {
/*  225: 237 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  226: 238 */       CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  227: 239 */       Root<Producto> from = criteriaQuery.from(Producto.class);
/*  228:     */       
/*  229: 241 */       Fetch<Object, Object> subcategoriaProducto = from.fetch("subcategoriaProducto", JoinType.LEFT);
/*  230:     */       
/*  231: 243 */       subcategoriaProducto.fetch("cuentaContableGasto", JoinType.LEFT);
/*  232:     */       
/*  233:     */ 
/*  234: 246 */       Map<String, String> filters = new HashMap();
/*  235: 247 */       filters.put("codigo", "=" + codigo);
/*  236:     */       
/*  237: 249 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  238: 250 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  239:     */       
/*  240: 252 */       CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  241: 253 */       TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  242:     */       
/*  243: 255 */       return (Producto)typedQuery.getSingleResult();
/*  244:     */     }
/*  245:     */     catch (NoResultException e)
/*  246:     */     {
/*  247: 258 */       throw new ExcepcionAS2("msg_producto_no_encontrado", " " + codigo);
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   public List<Producto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  252:     */   {
/*  253: 270 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  254: 271 */     CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  255: 272 */     Root<Producto> from = criteriaQuery.from(Producto.class);
/*  256:     */     
/*  257: 274 */     Integer numeroAtributos = Integer.valueOf(0);
/*  258: 275 */     if ((filters != null) && (filters.containsKey("numeroAtributos")))
/*  259:     */     {
/*  260: 276 */       numeroAtributos = Integer.valueOf((String)filters.get("numeroAtributos"));
/*  261: 277 */       filters.remove("numeroAtributos");
/*  262:     */     }
/*  263: 280 */     from.fetch("unidad", JoinType.LEFT);
/*  264: 281 */     from.fetch("unidadCompra", JoinType.LEFT);
/*  265: 282 */     from.fetch("unidadVenta", JoinType.LEFT);
/*  266: 283 */     from.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  267: 284 */     from.fetch("subcategoriaProducto", JoinType.LEFT).fetch("categoriaProducto", JoinType.LEFT);
/*  268: 285 */     from.fetch("conjuntoAtributo", JoinType.LEFT);
/*  269: 287 */     for (int i = 1; i <= numeroAtributos.intValue(); i++) {
/*  270: 288 */       from.fetch("atributoProduccion" + i, JoinType.LEFT);
/*  271:     */     }
/*  272: 291 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  273:     */     
/*  274: 293 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  275: 294 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  276:     */     
/*  277: 296 */     CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  278:     */     
/*  279: 298 */     TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  280:     */     
/*  281: 300 */     return typedQuery.getResultList();
/*  282:     */   }
/*  283:     */   
/*  284:     */   public List<Producto> obtenerListaComboMultiple(String sortField, boolean sortOrder, Map<String, String> filters)
/*  285:     */   {
/*  286: 305 */     boolean notSetMaxResults = false;
/*  287: 306 */     if (filters == null) {
/*  288: 307 */       filters = new HashMap();
/*  289:     */     }
/*  290: 309 */     if (filters.get("notSetMaxResults") != null)
/*  291:     */     {
/*  292: 310 */       notSetMaxResults = true;
/*  293: 311 */       filters.remove("notSetMaxResults");
/*  294:     */     }
/*  295: 314 */     filters = agregarFiltrosOrganizacion(filters);
/*  296:     */     
/*  297: 316 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  298: 317 */     CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  299: 318 */     Root<Producto> from = criteriaQuery.from(Producto.class);
/*  300:     */     
/*  301: 320 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  302: 321 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  303: 323 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  304:     */     {
/*  305: 324 */       String filterProperty = (String)it.next();
/*  306: 326 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  307:     */       {
/*  308: 327 */         String filterValue = (String)filters.get(filterProperty);
/*  309: 329 */         if (filterProperty.startsWith("idOrganizacion")) {
/*  310: 330 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  311: 331 */         } else if (filterProperty.startsWith("activo")) {
/*  312: 332 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  313: 333 */         } else if (filterProperty.startsWith("indicadorCompra")) {
/*  314: 334 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  315:     */         } else {
/*  316: 336 */           disjunction.getExpressions().add(criteriaBuilder
/*  317: 337 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue.toLowerCase() + "%"));
/*  318:     */         }
/*  319:     */       }
/*  320:     */     }
/*  321: 343 */     if (disjunction.getExpressions().size() > 0) {
/*  322: 344 */       conjunction.getExpressions().add(disjunction);
/*  323:     */     }
/*  324: 347 */     if (conjunction.getExpressions().size() > 0) {
/*  325: 348 */       criteriaQuery.where(conjunction);
/*  326:     */     }
/*  327: 351 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  328: 352 */     CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  329: 353 */     TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  330: 356 */     if (!notSetMaxResults) {
/*  331: 357 */       typedQuery.setMaxResults(20);
/*  332:     */     }
/*  333: 360 */     return typedQuery.getResultList();
/*  334:     */   }
/*  335:     */   
/*  336:     */   public List<Producto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  337:     */   {
/*  338: 365 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  339: 366 */     CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  340: 367 */     Root<Producto> from = criteriaQuery.from(Producto.class);
/*  341:     */     
/*  342: 369 */     from.fetch("unidad", JoinType.LEFT);
/*  343: 370 */     from.fetch("unidadVenta", JoinType.LEFT);
/*  344: 371 */     from.fetch("bodegaVenta", JoinType.LEFT);
/*  345: 372 */     from.fetch("unidadCompra", JoinType.LEFT);
/*  346: 373 */     from.fetch("bodegaCompra", JoinType.LEFT);
/*  347: 374 */     from.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  348: 375 */     from.fetch("categoriaImpuesto", JoinType.LEFT);
/*  349: 376 */     from.fetch("partidaArancelaria", JoinType.LEFT);
/*  350: 377 */     from.fetch("presentacionProducto", JoinType.LEFT);
/*  351: 378 */     Fetch<Object, Object> subcategoriaProducto = from.fetch("subcategoriaProducto", JoinType.LEFT);
/*  352: 379 */     subcategoriaProducto.fetch("categoriaProducto", JoinType.LEFT);
/*  353:     */     
/*  354: 381 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  355:     */     
/*  356: 383 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  357: 384 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  358:     */     
/*  359: 386 */     CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  360: 387 */     TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  361: 388 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  362:     */     
/*  363: 390 */     return typedQuery.getResultList();
/*  364:     */   }
/*  365:     */   
/*  366:     */   public Producto cargarDetalle(int id)
/*  367:     */   {
/*  368: 394 */     return cargarDetalle(id, true);
/*  369:     */   }
/*  370:     */   
/*  371:     */   public Producto cargarDetalle(int id, boolean cargarDetalle)
/*  372:     */   {
/*  373: 405 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  374:     */     
/*  375:     */ 
/*  376: 408 */     CriteriaQuery<Producto> cqCabecera = criteriaBuilder.createQuery(Producto.class);
/*  377: 409 */     Root<Producto> fromCabecera = cqCabecera.from(Producto.class);
/*  378:     */     
/*  379: 411 */     fromCabecera.fetch("conjuntoAtributo", JoinType.LEFT);
/*  380: 412 */     fromCabecera.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  381: 413 */     fromCabecera.fetch("unidadCompra", JoinType.LEFT);
/*  382: 414 */     fromCabecera.fetch("unidadVenta", JoinType.LEFT);
/*  383: 415 */     fromCabecera.fetch("unidadInformativa", JoinType.LEFT);
/*  384: 416 */     fromCabecera.fetch("categoriaImpuesto", JoinType.LEFT);
/*  385: 417 */     fromCabecera.fetch("bodegaCompra", JoinType.LEFT);
/*  386: 418 */     fromCabecera.fetch("bodegaVenta", JoinType.LEFT);
/*  387: 419 */     fromCabecera.fetch("rutaFabricacion", JoinType.LEFT);
/*  388: 420 */     fromCabecera.fetch("empresa", JoinType.LEFT);
/*  389: 421 */     fromCabecera.fetch("creditoTributarioSRI", JoinType.LEFT);
/*  390:     */     
/*  391: 423 */     fromCabecera.fetch("marcaProducto", JoinType.LEFT);
/*  392:     */     
/*  393: 425 */     Fetch<Object, Object> presentacionProducto = fromCabecera.fetch("presentacionProducto", JoinType.LEFT);
/*  394: 426 */     presentacionProducto.fetch("tipoPresentacionProducto", JoinType.LEFT);
/*  395:     */     
/*  396: 428 */     Fetch<Object, Object> subcategoria = fromCabecera.fetch("subcategoriaProducto", JoinType.LEFT);
/*  397: 429 */     subcategoria.fetch("categoriaProducto", JoinType.LEFT);
/*  398:     */     
/*  399: 431 */     Path<Integer> pathId = fromCabecera.get("idProducto");
/*  400: 432 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(id)));
/*  401: 433 */     CriteriaQuery<Producto> cqProducto = cqCabecera.select(fromCabecera);
/*  402:     */     
/*  403: 435 */     Producto producto = (Producto)this.em.createQuery(cqProducto).getSingleResult();
/*  404: 436 */     if (cargarDetalle)
/*  405:     */     {
/*  406: 438 */       CriteriaQuery<ProductoAtributo> cqProductoAtributo = criteriaBuilder.createQuery(ProductoAtributo.class);
/*  407: 439 */       Root<ProductoAtributo> fromProductoAtributo = cqProductoAtributo.from(ProductoAtributo.class);
/*  408:     */       
/*  409: 441 */       fromProductoAtributo.fetch("atributo", JoinType.LEFT);
/*  410:     */       
/*  411: 443 */       Path<Integer> pathIdProductoProductoAtributo = fromProductoAtributo.join("producto").get("idProducto");
/*  412: 444 */       cqProductoAtributo.where(criteriaBuilder.equal(pathIdProductoProductoAtributo, Integer.valueOf(id)));
/*  413: 445 */       CriteriaQuery<ProductoAtributo> selectProductoAtributo = cqProductoAtributo.select(fromProductoAtributo);
/*  414:     */       
/*  415: 447 */       List<ProductoAtributo> listaProductoAtributo = this.em.createQuery(selectProductoAtributo).getResultList();
/*  416: 448 */       producto.setListaProductoAtributo(listaProductoAtributo);
/*  417: 450 */       for (ProductoAtributo productoAtributo : listaProductoAtributo)
/*  418:     */       {
/*  419: 452 */         CriteriaQuery<ValorAtributo> cqValorAtributo = criteriaBuilder.createQuery(ValorAtributo.class);
/*  420: 453 */         Root<ValorAtributo> fromValorAtributo = cqValorAtributo.from(ValorAtributo.class);
/*  421:     */         
/*  422: 455 */         Path<Integer> pathIdAtributoValorAtributo = fromValorAtributo.join("atributo").get("idAtributo");
/*  423: 456 */         cqValorAtributo.where(criteriaBuilder.equal(pathIdAtributoValorAtributo, Integer.valueOf(productoAtributo.getAtributo().getId())));
/*  424: 457 */         CriteriaQuery<ValorAtributo> selectValorAtributo = cqValorAtributo.select(fromValorAtributo);
/*  425:     */         
/*  426: 459 */         List<ValorAtributo> listaValorAtributo = this.em.createQuery(selectValorAtributo).getResultList();
/*  427: 460 */         productoAtributo.getAtributo().setListaValorAtributo(listaValorAtributo);
/*  428:     */       }
/*  429: 464 */       Object cqUnidadConversion = criteriaBuilder.createQuery(UnidadConversion.class);
/*  430: 465 */       Root<UnidadConversion> fromUnidadConversion = ((CriteriaQuery)cqUnidadConversion).from(UnidadConversion.class);
/*  431:     */       
/*  432: 467 */       fromUnidadConversion.fetch("unidadDestino", JoinType.LEFT);
/*  433: 468 */       fromUnidadConversion.fetch("unidadOrigen", JoinType.LEFT);
/*  434:     */       
/*  435: 470 */       Path<Integer> pathIdProductoUnidadConversion = fromUnidadConversion.join("producto").get("idProducto");
/*  436: 471 */       ((CriteriaQuery)cqUnidadConversion).where(criteriaBuilder.equal(pathIdProductoUnidadConversion, Integer.valueOf(id)));
/*  437: 472 */       CriteriaQuery<UnidadConversion> selectUnidadConversion = ((CriteriaQuery)cqUnidadConversion).select(fromUnidadConversion);
/*  438:     */       
/*  439: 474 */       List<UnidadConversion> listaUnidadConversion = this.em.createQuery(selectUnidadConversion).getResultList();
/*  440: 475 */       producto.setListaUnidadConversion(listaUnidadConversion);
/*  441:     */       
/*  442:     */ 
/*  443: 478 */       CriteriaQuery<ProductoSustituto> cqProductoSustituto = criteriaBuilder.createQuery(ProductoSustituto.class);
/*  444: 479 */       Root<ProductoSustituto> fromProductoSustituto = cqProductoSustituto.from(ProductoSustituto.class);
/*  445:     */       
/*  446: 481 */       fromProductoSustituto.fetch("sustituto", JoinType.LEFT);
/*  447:     */       
/*  448: 483 */       Path<Integer> pathIdProductoProductoSustituto = fromProductoSustituto.join("producto").get("idProducto");
/*  449: 484 */       cqProductoSustituto.where(criteriaBuilder.equal(pathIdProductoProductoSustituto, Integer.valueOf(id)));
/*  450: 485 */       CriteriaQuery<ProductoSustituto> selectProductoSustituto = cqProductoSustituto.select(fromProductoSustituto);
/*  451:     */       
/*  452: 487 */       List<ProductoSustituto> listaProductoSustituto = this.em.createQuery(selectProductoSustituto).getResultList();
/*  453: 488 */       producto.setListaProductoSustituto(listaProductoSustituto);
/*  454:     */       
/*  455:     */ 
/*  456: 491 */       CriteriaQuery<ProductoBodega> cqProductoBodega = criteriaBuilder.createQuery(ProductoBodega.class);
/*  457: 492 */       Root<ProductoBodega> fromProductoBodega = cqProductoBodega.from(ProductoBodega.class);
/*  458:     */       
/*  459: 494 */       fromProductoBodega.fetch("bodega", JoinType.LEFT);
/*  460: 495 */       fromProductoBodega.fetch("producto", JoinType.LEFT);
/*  461:     */       
/*  462: 497 */       Path<Integer> pathIdProductoProductoBodega = fromProductoBodega.join("producto").get("idProducto");
/*  463: 498 */       cqProductoBodega.where(criteriaBuilder.equal(pathIdProductoProductoBodega, Integer.valueOf(id)));
/*  464: 499 */       CriteriaQuery<ProductoBodega> selectProductoBodega = cqProductoBodega.select(fromProductoBodega);
/*  465:     */       
/*  466: 501 */       List<ProductoBodega> listaProductoBodega = this.em.createQuery(selectProductoBodega).getResultList();
/*  467: 502 */       producto.setListaProductoBodega(listaProductoBodega);
/*  468:     */       
/*  469:     */ 
/*  470:     */ 
/*  471: 506 */       CriteriaQuery<BodegaTrabajoProductoSucursal> cqBodegaTrabajoProductoSucursal = criteriaBuilder.createQuery(BodegaTrabajoProductoSucursal.class);
/*  472:     */       
/*  473: 508 */       Root<BodegaTrabajoProductoSucursal> fromBodegaTrabajoProductoSucursal = cqBodegaTrabajoProductoSucursal.from(BodegaTrabajoProductoSucursal.class);
/*  474:     */       
/*  475: 510 */       fromBodegaTrabajoProductoSucursal.fetch("bodegaTrabajo", JoinType.INNER);
/*  476: 511 */       fromBodegaTrabajoProductoSucursal.fetch("sucursal", JoinType.INNER);
/*  477: 512 */       fromBodegaTrabajoProductoSucursal.fetch("producto", JoinType.INNER);
/*  478:     */       
/*  479: 514 */       Path<Integer> pathIdProductobodegaTrabajoSucursal = fromBodegaTrabajoProductoSucursal.join("producto").get("idProducto");
/*  480: 515 */       cqBodegaTrabajoProductoSucursal.where(criteriaBuilder.equal(pathIdProductobodegaTrabajoSucursal, Integer.valueOf(id)));
/*  481:     */       
/*  482: 517 */       CriteriaQuery<BodegaTrabajoProductoSucursal> selectBodegaTrabajoProductoSucursal = cqBodegaTrabajoProductoSucursal.select(fromBodegaTrabajoProductoSucursal);
/*  483:     */       
/*  484:     */ 
/*  485: 520 */       List<BodegaTrabajoProductoSucursal> listaBodegaTrabajoProductoSucursal = this.em.createQuery(selectBodegaTrabajoProductoSucursal).getResultList();
/*  486: 521 */       producto.setListaBodegaTrabajoSucursal(listaBodegaTrabajoProductoSucursal);
/*  487:     */     }
/*  488: 524 */     producto.setListaMezclaProducto(new ArrayList());
/*  489: 525 */     return producto;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public BigDecimal getSaldo(int idProducto, Date fecha)
/*  493:     */   {
/*  494: 537 */     BigDecimal saldo = BigDecimal.ZERO;
/*  495:     */     
/*  496:     */ 
/*  497: 540 */     Query query = this.em.createQuery("SELECT b.idBodega, MAX(sp.fecha) FROM SaldoProducto sp INNER JOIN sp.bodega b WHERE sp.producto.idProducto=:idProducto AND sp.fecha<=:fecha GROUP BY b.idBodega");
/*  498: 541 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  499: 542 */     query.setParameter("fecha", fecha);
/*  500: 545 */     for (Object[] datos : query.getResultList())
/*  501:     */     {
/*  502: 546 */       Integer idBodega = Integer.valueOf(datos[0].toString());
/*  503: 547 */       Date fechaSaldo = (Date)datos[1];
/*  504:     */       
/*  505: 549 */       saldo = saldo.add(getSaldo(idProducto, idBodega.intValue(), fechaSaldo));
/*  506:     */     }
/*  507: 552 */     return saldo;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public BigDecimal getSaldo(int idProducto, int idBodega, Date fecha)
/*  511:     */   {
/*  512: 568 */     if (idBodega <= 0) {
/*  513: 569 */       return getSaldo(idProducto, fecha);
/*  514:     */     }
/*  515: 575 */     StringBuilder sbSQL = new StringBuilder("SELECT sp1.saldo FROM SaldoProducto sp1");
/*  516: 576 */     sbSQL.append(" WHERE sp1.producto.idProducto=:idProducto AND sp1.bodega.idBodega=:idBodega");
/*  517: 577 */     sbSQL.append(" AND sp1.fecha=(SELECT MAX(sp2.fecha) FROM SaldoProducto sp2  ");
/*  518: 578 */     sbSQL.append("WHERE sp2.producto.idProducto=:idProducto AND sp2.bodega.idBodega=:idBodega AND sp2.fecha<=:fecha)");
/*  519:     */     
/*  520: 580 */     Query query = this.em.createQuery(sbSQL.toString());
/*  521: 581 */     query.setParameter("idBodega", Integer.valueOf(idBodega));
/*  522: 582 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  523: 583 */     query.setParameter("fecha", fecha);
/*  524:     */     BigDecimal saldo;
/*  525:     */     try
/*  526:     */     {
/*  527: 586 */       saldo = (BigDecimal)query.getSingleResult();
/*  528:     */     }
/*  529:     */     catch (NoResultException e)
/*  530:     */     {
/*  531:     */       BigDecimal saldo;
/*  532: 589 */       saldo = BigDecimal.ZERO;
/*  533:     */     }
/*  534:     */     catch (Exception e)
/*  535:     */     {
/*  536:     */       BigDecimal saldo;
/*  537: 591 */       saldo = BigDecimal.ZERO;
/*  538: 592 */       e.printStackTrace();
/*  539:     */     }
/*  540: 595 */     return saldo;
/*  541:     */   }
/*  542:     */   
/*  543:     */   public BigDecimal getSaldoLote(int idProducto, int idBodega, int idLote, Date fecha)
/*  544:     */   {
/*  545:     */     BigDecimal saldo;
/*  546:     */     try
/*  547:     */     {
/*  548: 612 */       Query query = this.em.createQuery("SELECT p.saldo FROM SaldoProductoLote p WHERE p.producto.idProducto=:idProducto AND p.bodega.idBodega=:idBodega  AND p.lote.idLote=:idLote AND fecha<=:fecha ORDER BY fecha DESC");
/*  549:     */       
/*  550:     */ 
/*  551:     */ 
/*  552: 616 */       query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  553: 617 */       query.setParameter("idBodega", Integer.valueOf(idBodega));
/*  554: 618 */       query.setParameter("idLote", Integer.valueOf(idLote));
/*  555: 619 */       query.setParameter("fecha", fecha);
/*  556:     */       
/*  557: 621 */       query.setMaxResults(1);
/*  558: 622 */       saldo = (BigDecimal)query.getSingleResult();
/*  559:     */     }
/*  560:     */     catch (NoResultException e)
/*  561:     */     {
/*  562:     */       BigDecimal saldo;
/*  563: 625 */       saldo = BigDecimal.ZERO;
/*  564:     */     }
/*  565: 628 */     return saldo;
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void actualizarSaldo(Producto producto, Bodega bodega, Date fecha, BigDecimal cantidad, BigDecimal saldo, boolean indicadorPedidoCliente)
/*  569:     */   {
/*  570: 643 */     cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 6);
/*  571:     */     
/*  572: 645 */     Query queryExiste = this.em.createQuery("SELECT COUNT(sp.saldo) FROM SaldoProducto sp WHERE sp.producto.idProducto=:idProducto AND sp.bodega.idBodega=:idBodega AND sp.fecha=:fecha");
/*  573:     */     
/*  574:     */ 
/*  575: 648 */     queryExiste.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/*  576: 649 */     queryExiste.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/*  577: 650 */     queryExiste.setParameter("fecha", fecha);
/*  578: 652 */     if (((Long)queryExiste.getSingleResult()).intValue() == 0)
/*  579:     */     {
/*  580: 653 */       SaldoProducto saldoProducto = new SaldoProducto();
/*  581: 654 */       saldoProducto.setFecha(fecha);
/*  582: 655 */       saldoProducto.setProducto(producto);
/*  583: 656 */       saldoProducto.setBodega(bodega);
/*  584: 657 */       saldoProducto.setSaldo(saldo);
/*  585:     */       
/*  586: 659 */       BigDecimal inventarioComprometido = getInventarioComprometido(producto, null, bodega, fecha, false);
/*  587: 660 */       BigDecimal inventarioComprometidoProduccion = getInventarioComprometido(producto, null, bodega, fecha, true);
/*  588: 661 */       saldoProducto.setInventarioComprometido(inventarioComprometido);
/*  589: 662 */       saldoProducto.setInventarioComprometidoProduccion(inventarioComprometidoProduccion);
/*  590:     */       
/*  591: 664 */       this.saldoProductoDao.guardar(saldoProducto);
/*  592:     */     }
/*  593:     */     Query query;
/*  594:     */     Query query;
/*  595: 668 */     if (indicadorPedidoCliente) {
/*  596: 669 */       query = this.em.createQuery("UPDATE SaldoProducto p SET p.saldo=p.saldo+:cantidad WHERE p.producto.idProducto=:idProducto AND p.bodega.idBodega=:idBodega AND fecha>=:fecha");
/*  597:     */     } else {
/*  598: 672 */       query = this.em.createQuery("UPDATE SaldoProducto p SET p.saldo=p.saldo+:cantidad WHERE p.producto.idProducto=:idProducto AND p.bodega.idBodega=:idBodega AND fecha>=:fecha");
/*  599:     */     }
/*  600: 676 */     query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/*  601: 677 */     query.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/*  602: 678 */     query.setParameter("fecha", fecha);
/*  603: 679 */     query.setParameter("cantidad", cantidad);
/*  604:     */     
/*  605: 681 */     query.executeUpdate();
/*  606:     */   }
/*  607:     */   
/*  608:     */   public void actualizarSaldoLote(int idProducto, int idBodega, int idLote, Date fecha, BigDecimal cantidad, BigDecimal saldo, boolean activaLote)
/*  609:     */   {
/*  610: 696 */     cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 6);
/*  611:     */     
/*  612: 698 */     Query queryExiste = this.em.createQuery("SELECT COUNT(sp.saldo) FROM SaldoProductoLote sp WHERE sp.producto.idProducto=:idProducto AND sp.bodega.idBodega=:idBodega AND sp.lote.idLote=:idLote AND sp.fecha=:fecha");
/*  613:     */     
/*  614:     */ 
/*  615: 701 */     queryExiste.setParameter("idProducto", Integer.valueOf(idProducto));
/*  616: 702 */     queryExiste.setParameter("idBodega", Integer.valueOf(idBodega));
/*  617: 703 */     queryExiste.setParameter("idLote", Integer.valueOf(idLote));
/*  618: 704 */     queryExiste.setParameter("fecha", fecha);
/*  619: 706 */     if (((Long)queryExiste.getSingleResult()).intValue() == 0)
/*  620:     */     {
/*  621: 707 */       SaldoProductoLote saldoProductoLote = new SaldoProductoLote(new Producto(idProducto), new Bodega(idBodega), new Lote(idLote), fecha, saldo);
/*  622:     */       
/*  623:     */ 
/*  624: 710 */       Producto producto = new Producto(idProducto);
/*  625: 711 */       Bodega bodega = new Bodega(idBodega);
/*  626: 712 */       Lote lote = new Lote(idLote);
/*  627: 713 */       BigDecimal inventarioComprometido = getInventarioComprometido(producto, lote, bodega, fecha, false);
/*  628: 714 */       BigDecimal inventarioComprometidoProduccion = getInventarioComprometido(producto, lote, bodega, fecha, true);
/*  629: 715 */       saldoProductoLote.setInventarioComprometido(inventarioComprometido);
/*  630: 716 */       saldoProductoLote.setInventarioComprometidoProduccion(inventarioComprometidoProduccion);
/*  631: 717 */       this.saldoProductoLoteDao.guardar(saldoProductoLote);
/*  632:     */     }
/*  633: 721 */     Query query = this.em.createQuery("UPDATE SaldoProductoLote p SET p.saldo=p.saldo+:cantidad WHERE p.producto.idProducto=:idProducto AND p.bodega.idBodega=:idBodega AND p.lote.idLote=:idLote AND fecha>=:fecha");
/*  634:     */     
/*  635:     */ 
/*  636: 724 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  637: 725 */     query.setParameter("idBodega", Integer.valueOf(idBodega));
/*  638: 726 */     query.setParameter("idLote", Integer.valueOf(idLote));
/*  639: 727 */     query.setParameter("fecha", fecha);
/*  640: 728 */     query.setParameter("cantidad", cantidad);
/*  641:     */     
/*  642: 730 */     query.executeUpdate();
/*  643: 732 */     if (activaLote)
/*  644:     */     {
/*  645: 733 */       query = this.em.createQuery("UPDATE Lote l SET l.activo=true WHERE l.idLote=:idLote AND l.activo = false");
/*  646: 734 */       query.setParameter("idLote", Integer.valueOf(idLote));
/*  647: 735 */       query.executeUpdate();
/*  648:     */     }
/*  649:     */   }
/*  650:     */   
/*  651:     */   public List<SaldoProducto> obtenerSaldosPorBodega(int idProducto, Bodega bodega, Date fecha)
/*  652:     */   {
/*  653: 749 */     StringBuilder sql = new StringBuilder();
/*  654: 750 */     sql.append(" SELECT p FROM SaldoProducto p");
/*  655: 751 */     sql.append(" join fetch p.bodega b ");
/*  656: 752 */     sql.append(" join fetch p.producto pro");
/*  657: 753 */     sql.append(" WHERE pro.idProducto=:idProducto AND b = :bodega AND fecha<=:fecha");
/*  658: 754 */     sql.append(" ORDER BY fecha DESC");
/*  659:     */     
/*  660: 756 */     Query query = this.em.createQuery(sql.toString());
/*  661: 757 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  662: 758 */     query.setParameter("fecha", fecha);
/*  663: 759 */     query.setParameter("bodega", bodega);
/*  664:     */     
/*  665: 761 */     query.setMaxResults(1);
/*  666:     */     
/*  667: 763 */     return query.getResultList();
/*  668:     */   }
/*  669:     */   
/*  670:     */   public List<ProductoSustituto> obtenerProductosSustitutos(int idProducto)
/*  671:     */   {
/*  672: 774 */     Query query = this.em.createQuery("SELECT p FROM ProductoSustituto p join fetch p.sustituto WHERE p.producto.idProducto=:idProducto ORDER BY p.producto.nombre");
/*  673:     */     
/*  674:     */ 
/*  675: 777 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  676: 778 */     return query.getResultList();
/*  677:     */   }
/*  678:     */   
/*  679:     */   public Producto buscarProducto(Map<String, String> filters)
/*  680:     */     throws ExcepcionAS2
/*  681:     */   {
/*  682:     */     try
/*  683:     */     {
/*  684: 785 */       CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  685: 786 */       CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
/*  686: 787 */       Root<Producto> from = cq.from(Producto.class);
/*  687:     */       
/*  688: 789 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
/*  689: 790 */       cq.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  690:     */       
/*  691: 792 */       CriteriaQuery<Producto> select = cq.select(from);
/*  692: 793 */       return cargarDetalle(((Producto)this.em.createQuery(select).getSingleResult()).getId());
/*  693:     */     }
/*  694:     */     catch (NoResultException e)
/*  695:     */     {
/*  696: 796 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + (String)filters.get("nombre"));
/*  697:     */     }
/*  698:     */   }
/*  699:     */   
/*  700:     */   public List<Producto> autocompletarProductos(String sortField, boolean sortOrder, Map<String, String> filters)
/*  701:     */   {
/*  702: 802 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  703: 803 */     CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  704: 804 */     Root<Producto> from = criteriaQuery.from(Producto.class);
/*  705:     */     
/*  706: 806 */     from.fetch("unidad", JoinType.LEFT);
/*  707: 807 */     from.fetch("unidadCompra", JoinType.LEFT);
/*  708: 808 */     from.fetch("unidadVenta", JoinType.LEFT);
/*  709: 809 */     from.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  710: 810 */     from.fetch("subcategoriaProducto", JoinType.LEFT);
/*  711:     */     
/*  712:     */ 
/*  713: 813 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  714: 814 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  715: 816 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  716:     */     {
/*  717: 817 */       String filterProperty = (String)it.next();
/*  718: 818 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  719:     */       {
/*  720: 819 */         String filterValue = (String)filters.get(filterProperty);
/*  721: 820 */         if ((filterProperty.equals("codigo")) || (filterProperty.equals("codigoAlterno")) || (filterProperty.equals("codigoComercial")) || 
/*  722: 821 */           (filterProperty.equals("codigoBarras")) || (filterProperty.equals("nombre")) || (filterProperty.equals("nombreComercial"))) {
/*  723: 822 */           disjunction.getExpressions().add(criteriaBuilder
/*  724: 823 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/*  725: 824 */         } else if (filterProperty.equals("idOrganizacion")) {
/*  726: 825 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  727: 826 */         } else if ((filterProperty.startsWith("indicador")) || (filterProperty.equals("activo"))) {
/*  728: 827 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  729:     */         }
/*  730:     */       }
/*  731:     */     }
/*  732: 833 */     if (disjunction.getExpressions().size() > 0) {
/*  733: 835 */       conjunction.getExpressions().add(disjunction);
/*  734:     */     }
/*  735: 838 */     if (conjunction.getExpressions().size() > 0) {
/*  736: 839 */       criteriaQuery.where(conjunction);
/*  737:     */     }
/*  738: 842 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  739:     */     
/*  740: 844 */     CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  741:     */     
/*  742: 846 */     TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  743:     */     
/*  744: 848 */     return typedQuery.getResultList();
/*  745:     */   }
/*  746:     */   
/*  747:     */   public CuentaContable obtenerCuentaContableGastoPorProducto(int idProducto)
/*  748:     */   {
/*  749:     */     try
/*  750:     */     {
/*  751: 859 */       String sql = " SELECT p.subcategoriaProducto.cuentaContableGasto FROM Producto p WHERE p.idProducto = :idProducto";
/*  752: 860 */       Query query = this.em.createQuery(sql);
/*  753: 861 */       query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  754:     */       
/*  755: 863 */       return (CuentaContable)query.getSingleResult();
/*  756:     */     }
/*  757:     */     catch (NoResultException e) {}
/*  758: 865 */     return null;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public BigDecimal getCostoTotal(int idProducto, Date fecha, Bodega bodega, boolean indicadorSaldoMayorCero)
/*  762:     */   {
/*  763: 878 */     BigDecimal costoTotal = BigDecimal.ZERO;
/*  764: 879 */     StringBuilder sql = new StringBuilder();
/*  765: 880 */     sql.append(" SELECT d.costo ");
/*  766: 881 */     sql.append(" FROM CostoProducto d ");
/*  767: 882 */     sql.append(" WHERE d.fecha<=:fecha ");
/*  768: 883 */     sql.append(" AND d.producto.idProducto=:idProducto ");
/*  769: 884 */     if (bodega != null) {
/*  770: 885 */       sql.append(" AND d.bodega.idBodega=:idBodega");
/*  771:     */     }
/*  772: 887 */     if (indicadorSaldoMayorCero) {
/*  773: 888 */       sql.append(" AND d.costo > 0");
/*  774:     */     }
/*  775: 890 */     sql.append(" ORDER BY fecha DESC");
/*  776:     */     
/*  777: 892 */     Query query = this.em.createQuery(sql.toString());
/*  778: 893 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  779: 894 */     query.setParameter("fecha", fecha);
/*  780: 895 */     if (bodega != null) {
/*  781: 896 */       query.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/*  782:     */     }
/*  783: 898 */     query.setMaxResults(1);
/*  784:     */     try
/*  785:     */     {
/*  786: 901 */       costoTotal = (BigDecimal)query.getSingleResult();
/*  787:     */     }
/*  788:     */     catch (NoResultException localNoResultException) {}
/*  789: 906 */     return costoTotal;
/*  790:     */   }
/*  791:     */   
/*  792:     */   public BigDecimal getCostoUnitario(int idProducto, Date fecha, Bodega bodega, boolean indicadorSaldoMayorCero)
/*  793:     */   {
/*  794: 917 */     BigDecimal costoTotal = BigDecimal.ZERO;
/*  795: 918 */     StringBuilder sql = new StringBuilder();
/*  796: 919 */     sql.append(" SELECT case when d.saldo = 0 then (0*d.costo) else (d.costo/d.saldo) end ");
/*  797: 920 */     sql.append(" FROM CostoProducto d ");
/*  798: 921 */     sql.append(" WHERE d.fecha<=:fecha ");
/*  799: 922 */     sql.append(" AND d.producto.idProducto=:idProducto ");
/*  800: 923 */     if (bodega != null) {
/*  801: 924 */       sql.append(" AND d.bodega.idBodega=:idBodega");
/*  802:     */     }
/*  803: 926 */     if (indicadorSaldoMayorCero) {
/*  804: 927 */       sql.append(" AND d.saldo > 0");
/*  805:     */     }
/*  806: 929 */     sql.append(" ORDER BY fecha DESC");
/*  807:     */     
/*  808: 931 */     Query query = this.em.createQuery(sql.toString());
/*  809: 932 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  810: 933 */     query.setParameter("fecha", fecha);
/*  811: 934 */     if (bodega != null) {
/*  812: 935 */       query.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/*  813:     */     }
/*  814: 937 */     query.setMaxResults(1);
/*  815:     */     try
/*  816:     */     {
/*  817: 940 */       costoTotal = (BigDecimal)query.getSingleResult();
/*  818:     */     }
/*  819:     */     catch (NoResultException localNoResultException) {}
/*  820: 945 */     return costoTotal;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public List obtenerProductoCategoriaAtributo(CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Atributo atributo, ConjuntoAtributo conjuntoAtributo, int idOrganizacion)
/*  824:     */     throws ExcepcionAS2Inventario
/*  825:     */   {
/*  826:     */     try
/*  827:     */     {
/*  828: 960 */       StringBuilder sql = new StringBuilder();
/*  829: 961 */       sql.append(" SELECT a.nombre, pa.valor, p.codigo, p.nombre, cp.nombre,sp.nombre,p.nombreComercial,p.tipoProducto,u.nombre,p.tipoCosto,p.costoEstandar,p.indicadorProduccion,p.indicadorVenta,p.indicadorCompra,p.indicadorLote,p.indicadorImpuestos,ci.nombre,uc.nombre,bc.nombre,uv.nombre,bv.nombre");
/*  830:     */       
/*  831:     */ 
/*  832: 964 */       sql.append(" FROM ProductoAtributo pa ");
/*  833: 965 */       sql.append(" RIGHT OUTER JOIN pa.producto p ");
/*  834: 966 */       sql.append(" LEFT OUTER JOIN p.conjuntoAtributo ca ");
/*  835: 967 */       sql.append(" LEFT OUTER JOIN pa.atributo a ");
/*  836: 968 */       sql.append(" LEFT OUTER JOIN p.subcategoriaProducto sp ");
/*  837: 969 */       sql.append(" LEFT OUTER JOIN p.unidad u ");
/*  838: 970 */       sql.append(" LEFT OUTER JOIN p.unidadCompra uc ");
/*  839: 971 */       sql.append(" LEFT OUTER JOIN p.unidadVenta uv ");
/*  840: 972 */       sql.append(" LEFT OUTER JOIN p.bodegaCompra bc ");
/*  841: 973 */       sql.append(" LEFT OUTER JOIN p.bodegaVenta bv ");
/*  842: 974 */       sql.append(" LEFT OUTER JOIN p.categoriaImpuesto ci ");
/*  843: 975 */       sql.append(" LEFT OUTER JOIN sp.categoriaProducto cp ");
/*  844: 976 */       sql.append(" WHERE p.idOrganizacion = :idOrganizacion ");
/*  845: 977 */       if (subcategoriaProducto != null) {
/*  846: 978 */         sql.append(" AND sp = :subcategoriaProducto ");
/*  847:     */       }
/*  848: 980 */       if (atributo != null) {
/*  849: 981 */         sql.append(" AND a = :atributo");
/*  850:     */       }
/*  851: 983 */       if (conjuntoAtributo != null) {
/*  852: 984 */         sql.append(" AND ca = :conjuntoAtributo");
/*  853:     */       }
/*  854: 986 */       if (categoriaProducto != null) {
/*  855: 987 */         sql.append(" AND cp = :categoriaProducto ");
/*  856:     */       }
/*  857: 990 */       Query query = this.em.createQuery(sql.toString());
/*  858: 991 */       if (subcategoriaProducto != null) {
/*  859: 992 */         query.setParameter("subcategoriaProducto", subcategoriaProducto);
/*  860:     */       }
/*  861: 994 */       if (atributo != null) {
/*  862: 995 */         query.setParameter("atributo", atributo);
/*  863:     */       }
/*  864: 997 */       if (conjuntoAtributo != null) {
/*  865: 998 */         query.setParameter("conjuntoAtributo", conjuntoAtributo);
/*  866:     */       }
/*  867:1000 */       if (categoriaProducto != null) {
/*  868:1001 */         query.setParameter("categoriaProducto", categoriaProducto);
/*  869:     */       }
/*  870:1004 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  871:     */       
/*  872:1006 */       List<Object[]> lista = query.getResultList();
/*  873:1007 */       List<Object[]> result = new ArrayList();
/*  874:1008 */       for (Object[] objects : lista)
/*  875:     */       {
/*  876:1009 */         Object[] objeto = new Object[7];
/*  877:1010 */         objeto[0] = objects[0];
/*  878:1011 */         objeto[1] = objects[1];
/*  879:1012 */         objeto[2] = objects[2];
/*  880:1013 */         objeto[3] = objects[3];
/*  881:1014 */         objeto[4] = objects[4];
/*  882:1015 */         objeto[5] = objects[5];
/*  883:1016 */         objeto[6] = objects[6];
/*  884:1017 */         result.add(objeto);
/*  885:     */         
/*  886:1019 */         Object[] objetoTipoProducto = obtenerObjetoColumnaCross(objects, "06. Tipo Producto", ((TipoProducto)objects[7]).getNombre());
/*  887:1020 */         result.add(objetoTipoProducto);
/*  888:1021 */         Object[] objetoUnidad = obtenerObjetoColumnaCross(objects, "07. Unidad Stock", (String)objects[8]);
/*  889:1022 */         result.add(objetoUnidad);
/*  890:1023 */         Object[] objetoTipoCosto = obtenerObjetoColumnaCross(objects, "08. Tipo Costeo", ((TipoCosto)objects[9]).getNombre());
/*  891:1024 */         result.add(objetoTipoCosto);
/*  892:1025 */         Object[] objetoCostoEstandar = obtenerObjetoColumnaCross(objects, "09. Costo Estandar", ((BigDecimal)objects[10]).toString());
/*  893:1026 */         result.add(objetoCostoEstandar);
/*  894:1027 */         Object[] objetoProduccion = obtenerObjetoColumnaCross(objects, "10. Producción", ((Boolean)objects[11]).booleanValue() ? "Si" : "No");
/*  895:1028 */         result.add(objetoProduccion);
/*  896:1029 */         Object[] objetoVenta = obtenerObjetoColumnaCross(objects, "11. Venta", ((Boolean)objects[12]).booleanValue() ? "Si" : "No");
/*  897:1030 */         result.add(objetoVenta);
/*  898:1031 */         Object[] objetoCompra = obtenerObjetoColumnaCross(objects, "12. Compra", ((Boolean)objects[13]).booleanValue() ? "Si" : "No");
/*  899:1032 */         result.add(objetoCompra);
/*  900:1033 */         Object[] objetoLote = obtenerObjetoColumnaCross(objects, "13. Lote", ((Boolean)objects[14]).booleanValue() ? "Si" : "No");
/*  901:1034 */         result.add(objetoLote);
/*  902:1035 */         Object[] objetoImpuestos = obtenerObjetoColumnaCross(objects, "14. Impuestos", ((Boolean)objects[15]).booleanValue() ? "Si" : "No");
/*  903:1036 */         result.add(objetoImpuestos);
/*  904:1037 */         Object[] objetoCategoriaImpuesto = obtenerObjetoColumnaCross(objects, "15. Categoría Impuesto", (String)objects[16]);
/*  905:1038 */         result.add(objetoCategoriaImpuesto);
/*  906:1039 */         Object[] objetoUnidadCompra = obtenerObjetoColumnaCross(objects, "16. Unidad Compra", (String)objects[17]);
/*  907:1040 */         result.add(objetoUnidadCompra);
/*  908:1041 */         Object[] objetoBodegaCompra = obtenerObjetoColumnaCross(objects, "17. Bodega Compra", (String)objects[18]);
/*  909:1042 */         result.add(objetoBodegaCompra);
/*  910:1043 */         Object[] objetoUnidadVenta = obtenerObjetoColumnaCross(objects, "18. Unidad Venta", (String)objects[19]);
/*  911:1044 */         result.add(objetoUnidadVenta);
/*  912:1045 */         Object[] objetoBodegaVenta = obtenerObjetoColumnaCross(objects, "19. Bodega Venta", (String)objects[20]);
/*  913:1046 */         result.add(objetoBodegaVenta);
/*  914:     */       }
/*  915:1050 */       return result;
/*  916:     */     }
/*  917:     */     catch (NoResultException e)
/*  918:     */     {
/*  919:1052 */       throw new ExcepcionAS2Inventario("msg_no_hay_datos");
/*  920:     */     }
/*  921:     */   }
/*  922:     */   
/*  923:     */   private Object[] obtenerObjetoColumnaCross(Object[] objects, String titulo, String valor)
/*  924:     */   {
/*  925:1057 */     Object[] objeto = new Object[7];
/*  926:1058 */     objeto[0] = titulo;
/*  927:1059 */     objeto[1] = valor;
/*  928:1060 */     objeto[2] = objects[2];
/*  929:1061 */     objeto[3] = objects[3];
/*  930:1062 */     objeto[4] = objects[4];
/*  931:1063 */     objeto[5] = objects[5];
/*  932:1064 */     objeto[6] = objects[6];
/*  933:1065 */     return objeto;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void actualizarPrecioFechaUltimaVenta(Date fechaUltimaVenta, BigDecimal precioUltimaVenta, int idProducto)
/*  937:     */   {
/*  938:1076 */     StringBuffer sql = new StringBuffer();
/*  939:1077 */     sql.append("UPDATE Producto p SET p.fechaUltimaVenta = :fechaUltimaVenta, p.precioUltimaVenta = :precioUltimaVenta ");
/*  940:1078 */     sql.append(" WHERE (p.fechaUltimaVenta IS NULL or p.fechaUltimaVenta <= :fechaUltimaVenta) ");
/*  941:1079 */     sql.append(" AND p.idProducto = :idProducto");
/*  942:     */     
/*  943:     */ 
/*  944:1082 */     Query query = this.em.createQuery(sql.toString()).setParameter("fechaUltimaVenta", fechaUltimaVenta).setParameter("idProducto", Integer.valueOf(idProducto)).setParameter("precioUltimaVenta", precioUltimaVenta);
/*  945:1083 */     query.executeUpdate();
/*  946:     */   }
/*  947:     */   
/*  948:     */   public void actualizarPrecioFechaUltimaCompra(Date fechaUltimaCompra, BigDecimal precioUltimaCompra, int idProducto)
/*  949:     */   {
/*  950:1094 */     StringBuffer sql = new StringBuffer();
/*  951:1095 */     sql.append("UPDATE Producto p SET p.fechaUltimaCompra = :fechaUltimaCompra, p.precioUltimaCompra = :precioUltimaCompra ");
/*  952:1096 */     sql.append(" WHERE (p.fechaUltimaCompra IS NULL or p.fechaUltimaCompra <= :fechaUltimaCompra) ");
/*  953:1097 */     sql.append(" AND p.idProducto = :idProducto");
/*  954:     */     
/*  955:     */ 
/*  956:1100 */     Query query = this.em.createQuery(sql.toString()).setParameter("fechaUltimaCompra", fechaUltimaCompra).setParameter("precioUltimaCompra", precioUltimaCompra).setParameter("idProducto", Integer.valueOf(idProducto));
/*  957:1101 */     query.executeUpdate();
/*  958:     */   }
/*  959:     */   
/*  960:     */   public Producto getProductoCategoriaImpuesto(Producto producto)
/*  961:     */     throws ExcepcionAS2
/*  962:     */   {
/*  963:     */     try
/*  964:     */     {
/*  965:1106 */       CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  966:1107 */       CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  967:1108 */       Root<Producto> from = criteriaQuery.from(Producto.class);
/*  968:1109 */       from.fetch("categoriaImpuesto", JoinType.LEFT);
/*  969:     */       
/*  970:1111 */       Map<String, String> filters = new HashMap();
/*  971:1112 */       filters.put("idProducto", "=" + producto.getIdProducto());
/*  972:1113 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  973:1114 */       criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  974:     */       
/*  975:1116 */       CriteriaQuery<Producto> select = criteriaQuery.select(from);
/*  976:1117 */       TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/*  977:1118 */       return (Producto)typedQuery.getSingleResult();
/*  978:     */     }
/*  979:     */     catch (NoResultException e)
/*  980:     */     {
/*  981:1120 */       throw new ExcepcionAS2("msg_producto_no_encontrado", " " + producto.getCodigo());
/*  982:     */     }
/*  983:     */   }
/*  984:     */   
/*  985:     */   public void actualizarIndicadorTieneMovimiento(int idProducto, boolean indicadorTieneMovimientos)
/*  986:     */   {
/*  987:1131 */     StringBuffer sql = new StringBuffer();
/*  988:1132 */     sql.append("UPDATE Producto p SET p.indicadorTieneMovimientos = :indicadorTieneMovimientos WHERE p.idProducto = :idProducto ");
/*  989:1133 */     sql.append(" AND p.indicadorTieneMovimientos = false ");
/*  990:1134 */     Query query = this.em.createQuery(sql.toString()).setParameter("indicadorTieneMovimientos", Boolean.valueOf(indicadorTieneMovimientos)).setParameter("idProducto", 
/*  991:1135 */       Integer.valueOf(idProducto));
/*  992:1136 */     query.executeUpdate();
/*  993:     */   }
/*  994:     */   
/*  995:     */   public List<Producto> obtenerListaPos(String sortField, boolean sortOrder, Map<String, String> filters)
/*  996:     */   {
/*  997:1141 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  998:1142 */     CriteriaQuery<Producto> criteriaQuery = criteriaBuilder.createQuery(Producto.class);
/*  999:1143 */     Root<Producto> from = criteriaQuery.from(Producto.class);
/* 1000:1144 */     from.fetch("unidadVenta", JoinType.LEFT);
/* 1001:1145 */     from.fetch("subcategoriaProducto", JoinType.LEFT).fetch("categoriaProducto", JoinType.LEFT);
/* 1002:     */     
/* 1003:1147 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 1004:     */     
/* 1005:1149 */     filters = new HashMap();
/* 1006:1150 */     filters.put("indicadorVenta", "true");
/* 1007:1151 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 1008:1152 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 1009:     */     
/* 1010:1154 */     CriteriaQuery<Producto> select = criteriaQuery.select(from);
/* 1011:     */     
/* 1012:1156 */     TypedQuery<Producto> typedQuery = this.em.createQuery(select);
/* 1013:     */     
/* 1014:1158 */     return typedQuery.getResultList();
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public void actualizarInventarioComprometido(Producto producto, Lote lote, Bodega bodega, Date fecha, BigDecimal cantidad, BigDecimal inventarioComprometido, boolean indicadorProduccion)
/* 1018:     */   {
/* 1019:1174 */     cantidad = FuncionesUtiles.redondearBigDecimal(cantidad, 6);
/* 1020:     */     
/* 1021:1176 */     String nombreTabla = "SaldoProducto";
/* 1022:1177 */     String consultaLote = "";
/* 1023:1178 */     if (lote != null)
/* 1024:     */     {
/* 1025:1179 */       nombreTabla = "SaldoProductoLote";
/* 1026:1180 */       consultaLote = " AND sp.lote.idLote=:idLote ";
/* 1027:     */     }
/* 1028:1183 */     Query queryExiste = this.em.createQuery("SELECT COUNT(sp.inventarioComprometido) FROM " + nombreTabla + " sp WHERE sp.producto.idProducto=:idProducto" + " AND sp.bodega.idBodega=:idBodega AND sp.fecha=:fecha " + consultaLote);
/* 1029:     */     
/* 1030:     */ 
/* 1031:1186 */     queryExiste.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 1032:1187 */     queryExiste.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/* 1033:1188 */     queryExiste.setParameter("fecha", fecha);
/* 1034:1189 */     if (lote != null) {
/* 1035:1190 */       queryExiste.setParameter("idLote", Integer.valueOf(lote.getId()));
/* 1036:     */     }
/* 1037:1193 */     if (((Long)queryExiste.getSingleResult()).intValue() == 0) {
/* 1038:1194 */       if (lote != null)
/* 1039:     */       {
/* 1040:1195 */         SaldoProductoLote saldoProducto = new SaldoProductoLote();
/* 1041:1196 */         saldoProducto.setFecha(fecha);
/* 1042:1197 */         saldoProducto.setProducto(producto);
/* 1043:1198 */         saldoProducto.setBodega(bodega);
/* 1044:1199 */         saldoProducto.setLote(lote);
/* 1045:     */         
/* 1046:1201 */         BigDecimal saldo = getSaldoLote(producto.getIdProducto(), bodega.getIdBodega(), lote.getId(), fecha);
/* 1047:1202 */         saldoProducto.setSaldo(saldo);
/* 1048:1203 */         if (!indicadorProduccion) {
/* 1049:1204 */           saldoProducto.setInventarioComprometido(inventarioComprometido);
/* 1050:     */         } else {
/* 1051:1206 */           saldoProducto.setInventarioComprometidoProduccion(inventarioComprometido);
/* 1052:     */         }
/* 1053:1208 */         this.saldoProductoLoteDao.guardar(saldoProducto);
/* 1054:     */       }
/* 1055:     */       else
/* 1056:     */       {
/* 1057:1210 */         SaldoProducto saldoProducto = new SaldoProducto();
/* 1058:1211 */         saldoProducto.setFecha(fecha);
/* 1059:1212 */         saldoProducto.setProducto(producto);
/* 1060:1213 */         saldoProducto.setBodega(bodega);
/* 1061:     */         
/* 1062:1215 */         BigDecimal saldo = getSaldo(producto.getIdProducto(), bodega.getIdBodega(), fecha);
/* 1063:1216 */         saldoProducto.setSaldo(saldo);
/* 1064:1217 */         if (!indicadorProduccion) {
/* 1065:1218 */           saldoProducto.setInventarioComprometido(inventarioComprometido);
/* 1066:     */         } else {
/* 1067:1220 */           saldoProducto.setInventarioComprometidoProduccion(inventarioComprometido);
/* 1068:     */         }
/* 1069:1222 */         this.saldoProductoDao.guardar(saldoProducto);
/* 1070:     */       }
/* 1071:     */     }
/* 1072:1226 */     String campo = "inventarioComprometido";
/* 1073:1227 */     if (indicadorProduccion) {
/* 1074:1228 */       campo = "inventarioComprometidoProduccion";
/* 1075:     */     }
/* 1076:1231 */     Query query = this.em.createQuery("UPDATE " + nombreTabla + " sp SET sp." + campo + "=CASE WHEN  (sp." + campo + "+:cantidad < 0) THEN 0 ELSE (sp." + campo + "+:cantidad) END WHERE sp.producto.idProducto=:idProducto" + " AND sp.bodega.idBodega=:idBodega AND fecha>=:fecha " + consultaLote);
/* 1077:     */     
/* 1078:     */ 
/* 1079:     */ 
/* 1080:1235 */     query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 1081:1236 */     query.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/* 1082:1237 */     query.setParameter("fecha", fecha);
/* 1083:1238 */     query.setParameter("cantidad", cantidad);
/* 1084:1239 */     if (lote != null) {
/* 1085:1240 */       query.setParameter("idLote", Integer.valueOf(lote.getId()));
/* 1086:     */     }
/* 1087:1243 */     query.executeUpdate();
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public BigDecimal getInventarioComprometido(Producto producto, Lote lote, Bodega bodega, Date fecha, boolean indicadorProduccion)
/* 1091:     */   {
/* 1092:1258 */     String nombreTabla = "SaldoProducto";
/* 1093:1259 */     String nombreCampo = "inventarioComprometido";
/* 1094:1260 */     if (lote != null) {
/* 1095:1261 */       nombreTabla = "SaldoProductoLote";
/* 1096:     */     }
/* 1097:1263 */     if (indicadorProduccion) {
/* 1098:1264 */       nombreCampo = "inventarioComprometidoProduccion";
/* 1099:     */     }
/* 1100:1269 */     StringBuilder sbSQL = new StringBuilder("SELECT sp1." + nombreCampo + " FROM " + nombreTabla + " sp1");
/* 1101:1270 */     sbSQL.append(" WHERE sp1.producto.idProducto=:idProducto AND sp1.bodega.idBodega=:idBodega");
/* 1102:1271 */     if (lote != null) {
/* 1103:1272 */       sbSQL.append(" AND sp1.lote.idLote=:idLote");
/* 1104:     */     }
/* 1105:1274 */     sbSQL.append(" AND sp1.fecha=(SELECT MAX(sp2.fecha) FROM " + nombreTabla + " sp2 WHERE sp2.producto.idProducto=:idProducto AND sp2.bodega.idBodega=:idBodega AND sp2.fecha<=:fecha)");
/* 1106:     */     
/* 1107:     */ 
/* 1108:1277 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1109:1278 */     query.setParameter("idBodega", Integer.valueOf(bodega.getIdBodega()));
/* 1110:1279 */     query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 1111:1280 */     query.setParameter("fecha", fecha);
/* 1112:1281 */     if (lote != null) {
/* 1113:1282 */       query.setParameter("idLote", Integer.valueOf(lote.getId()));
/* 1114:     */     }
/* 1115:     */     BigDecimal inventarioComprometido;
/* 1116:     */     try
/* 1117:     */     {
/* 1118:1286 */       inventarioComprometido = (BigDecimal)query.getSingleResult();
/* 1119:     */     }
/* 1120:     */     catch (NoResultException e)
/* 1121:     */     {
/* 1122:     */       BigDecimal inventarioComprometido;
/* 1123:1289 */       inventarioComprometido = BigDecimal.ZERO;
/* 1124:     */     }
/* 1125:     */     catch (Exception e)
/* 1126:     */     {
/* 1127:     */       BigDecimal inventarioComprometido;
/* 1128:1291 */       inventarioComprometido = BigDecimal.ZERO;
/* 1129:1292 */       e.printStackTrace();
/* 1130:     */     }
/* 1131:1295 */     return inventarioComprometido;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public List<Object[]> obtenerListaProductoEstadoImportacion(int idProducto, Date fecha)
/* 1135:     */   {
/* 1136:1301 */     StringBuilder sql = new StringBuilder();
/* 1137:1302 */     sql.append(" SELECT fpi.estadoProcesoImportacion,fp.estado,dfp.cantidad,fpi.medioTransporteEnum,fpi.fechaLlegada");
/* 1138:1303 */     sql.append(" FROM DetalleFacturaProveedor dfp");
/* 1139:1304 */     sql.append(" JOIN dfp.producto p");
/* 1140:1305 */     sql.append(" JOIN dfp.facturaProveedor fp");
/* 1141:1306 */     sql.append(" JOIN fp.facturaProveedorImportacion fpi");
/* 1142:1307 */     sql.append(" WHERE p.idProducto=:idProducto");
/* 1143:1308 */     sql.append(" AND fp.estado<>:estado");
/* 1144:1309 */     sql.append(" GROUP BY fp.idFacturaProveedor,");
/* 1145:1310 */     sql.append(" fpi.estadoProcesoImportacion,fp.estado,dfp.cantidad,fpi.medioTransporteEnum,fpi.fechaLlegada");
/* 1146:1311 */     Query query = this.em.createQuery(sql.toString());
/* 1147:1312 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 1148:1313 */     query.setParameter("estado", Estado.CERRADO);
/* 1149:     */     
/* 1150:     */ 
/* 1151:1316 */     return query.getResultList();
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public List<ProductoAtributo> obtenerListaProductoAtributo(int idProducto)
/* 1155:     */   {
/* 1156:1322 */     StringBuilder sql = new StringBuilder();
/* 1157:1323 */     sql.append(" SELECT pa FROM ProductoAtributo pa ");
/* 1158:1324 */     sql.append(" JOIN FETCH pa.atributo a ");
/* 1159:1325 */     sql.append(" INNER JOIN pa.producto p ");
/* 1160:1326 */     sql.append(" WHERE p.idProducto =:idProducto  ");
/* 1161:     */     
/* 1162:1328 */     Query query = this.em.createQuery(sql.toString());
/* 1163:1329 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 1164:     */     
/* 1165:1331 */     return query.getResultList();
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   public Long contarProductoPorCodigoBarras(int idProducto, String codigoBarras, int idOrganizacion)
/* 1169:     */   {
/* 1170:1344 */     StringBuilder sql = new StringBuilder();
/* 1171:1345 */     sql.append("SELECT COUNT(p) FROM Producto p ");
/* 1172:1346 */     sql.append(" WHERE p.codigoBarras = :codigoBarras ");
/* 1173:1347 */     sql.append(" AND p.idOrganizacion = :idOrganizacion ");
/* 1174:1348 */     sql.append(" AND p.idProducto != :idProducto ");
/* 1175:1349 */     sql.append(" AND p.codigoBarras IS NOT NULL ");
/* 1176:1350 */     sql.append(" AND p.codigoBarras <> '' ");
/* 1177:1351 */     Query query = this.em.createQuery(sql.toString());
/* 1178:1352 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/* 1179:1353 */     query.setParameter("codigoBarras", codigoBarras);
/* 1180:1354 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1181:     */     
/* 1182:1356 */     return (Long)query.getSingleResult();
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public List<SubProducto> getListaSubproducto(Producto producto)
/* 1186:     */   {
/* 1187:1368 */     StringBuilder sql = new StringBuilder();
/* 1188:1369 */     sql.append(" SELECT sp FROM SubProducto sp");
/* 1189:1370 */     sql.append(" JOIN FETCH sp.producto p");
/* 1190:1371 */     sql.append(" JOIN sp.productoPadre ppa");
/* 1191:1372 */     sql.append(" WHERE ppa = :productoPadre");
/* 1192:     */     
/* 1193:1374 */     Query query = this.em.createQuery(sql.toString());
/* 1194:1375 */     query.setParameter("productoPadre", producto);
/* 1195:     */     
/* 1196:1377 */     return query.getResultList();
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public List<Producto> obtenerListaPorPagina(FiltroProducto filtroProducto, int startIndex, int pageSize, Map<String, String> filter, Visualizacion visualizacion)
/* 1200:     */   {
/* 1201:1383 */     Map<String, String> hmParametros = new HashMap();
/* 1202:1384 */     String filterValue = null;
/* 1203:1385 */     StringBuilder sql = new StringBuilder();
/* 1204:1386 */     boolean filtroPorAtributo = false;
/* 1205:1387 */     Map<String, String> filtroAtributo = new LinkedHashMap();
/* 1206:1389 */     if ((filter.containsKey("atributo1")) || (filter.containsKey("atributo2")) || (filter.containsKey("atributo3")) || (filter.containsKey("atributo4")) || 
/* 1207:1390 */       (filter.containsKey("atributo5")) || (filter.containsKey("atributo6")) || (filter.containsKey("atributo7")) || 
/* 1208:1391 */       (filter.containsKey("atributo8")) || (filter.containsKey("atributo9")) || (filter.containsKey("atributo10"))) {
/* 1209:1392 */       filtroPorAtributo = true;
/* 1210:     */     }
/* 1211:1395 */     sql = new StringBuilder();
/* 1212:1396 */     sql.append(" SELECT new Producto(p.idProducto, p.codigo, p.codigoAlterno, p.nombre, p.nombreComercial, p.descripcion, p.tipoProducto,p.tipoCosto, ");
/* 1213:1397 */     sql.append(" p.indicadorProduccion, p.indicadorVenta, p.indicadorCompra, p.indicadorImpuestos, p.indicadorLote, ");
/* 1214:1398 */     sql.append(" cp.idCategoriaProducto, cp.codigo, cp.nombre, sp.idSubcategoriaProducto, sp.codigo, sp.nombre, ci.idCategoriaImpuesto, ci.codigo, ci.nombre, ");
/* 1215:1399 */     sql.append(" u.idUnidad, u.codigo, u.nombre, ua.idUnidad, ua.codigo, ua.nombre, uv.idUnidad, uv.codigo, uv.nombre, uc.idUnidad, uc.codigo, uc.nombre,");
/* 1216:1400 */     sql.append(" bv.idBodega, bv.codigo, bv.nombre, bc.idBodega, bc.codigo, bc.nombre, par.idPartidaArancelaria, par.codigo, par.nombre, p.precioUltimaCompra,");
/* 1217:1401 */     sql.append(" p.precioUltimaVenta, p.indicadorManejoPeso, p.indicadorSerie, p.cantidadProduccion, ");
/* 1218:1402 */     sql.append(" pre.idPresentacionProducto, pre.nombre, pre.cantidadUnidades, uc.numeroDecimales, p.indicadorConsumoDirecto, p.indicadorPesoBalanza, p.subsidio, ");
/* 1219:1403 */     sql.append(" COALESCE(p.indicadorPorcentajeIce, false), p.ice, p.codigoIce, p.indicadorManejaUnidadInformativa, ui.idUnidad, ui.codigo, ui.nombre, ui.tipoUnidadMedida, ");
/* 1220:1404 */     sql.append(" p.imagen, p.codigoBarras, p.prefijosLote ");
/* 1221:1407 */     if ((filtroProducto.isIndicadorAtributo1()) || (filtroProducto.isIndicadorAtributo2()) || (filtroProducto.isIndicadorAtributo3()) || 
/* 1222:1408 */       (filtroProducto.isIndicadorAtributo4()) || (filtroProducto.isIndicadorAtributo5()) || (filtroProducto.isIndicadorAtributo6()) || 
/* 1223:1409 */       (filtroProducto.isIndicadorAtributo7()) || (filtroProducto.isIndicadorAtributo8()) || (filtroProducto.isIndicadorAtributo9()) || 
/* 1224:1410 */       (filtroProducto.isIndicadorAtributo10()))
/* 1225:     */     {
/* 1226:1412 */       sql.append(", (CASE WHEN at = :atributo1  THEN pa0.valor ELSE '' END)");
/* 1227:1413 */       sql.append(", (CASE WHEN at = :atributo2  THEN pa0.valor ELSE '' END)");
/* 1228:1414 */       sql.append(", (CASE WHEN at = :atributo3  THEN pa0.valor ELSE '' END)");
/* 1229:1415 */       sql.append(", (CASE WHEN at = :atributo4  THEN pa0.valor ELSE '' END)");
/* 1230:1416 */       sql.append(", (CASE WHEN at = :atributo5  THEN pa0.valor ELSE '' END)");
/* 1231:1417 */       sql.append(", (CASE WHEN at = :atributo6  THEN pa0.valor ELSE '' END)");
/* 1232:1418 */       sql.append(", (CASE WHEN at = :atributo7  THEN pa0.valor ELSE '' END)");
/* 1233:1419 */       sql.append(", (CASE WHEN at = :atributo8  THEN pa0.valor ELSE '' END)");
/* 1234:1420 */       sql.append(", (CASE WHEN at = :atributo9  THEN pa0.valor ELSE '' END)");
/* 1235:1421 */       sql.append(", (CASE WHEN at = :atributo10 THEN pa0.valor ELSE '' END)");
/* 1236:     */     }
/* 1237:1424 */     sql.append(" ) ");
/* 1238:1425 */     sql.append(" FROM Producto p ");
/* 1239:1426 */     if ((filtroProducto.isIndicadorAtributo1()) || (filtroProducto.isIndicadorAtributo2()) || (filtroProducto.isIndicadorAtributo3()) || 
/* 1240:1427 */       (filtroProducto.isIndicadorAtributo4()) || (filtroProducto.isIndicadorAtributo5()) || (filtroProducto.isIndicadorAtributo6()) || 
/* 1241:1428 */       (filtroProducto.isIndicadorAtributo7()) || (filtroProducto.isIndicadorAtributo8()) || (filtroProducto.isIndicadorAtributo9()) || 
/* 1242:1429 */       (filtroProducto.isIndicadorAtributo10())) {
/* 1243:1430 */       sql.append(" LEFT JOIN p.listaProductoAtributo pa0 LEFT JOIN pa0.atributo at ");
/* 1244:     */     }
/* 1245:1432 */     sql.append(" INNER JOIN p.subcategoriaProducto    sp ");
/* 1246:1433 */     sql.append(" INNER JOIN sp.categoriaProducto   \t  cp ");
/* 1247:1434 */     sql.append(" LEFT  JOIN p.categoriaImpuesto       ci ");
/* 1248:1435 */     sql.append(" LEFT  JOIN p.bodegaVenta             bv ");
/* 1249:1436 */     sql.append(" LEFT  JOIN p.bodegaCompra            bc ");
/* 1250:1437 */     sql.append(" LEFT  JOIN p.partidaArancelaria      par ");
/* 1251:1438 */     sql.append(" INNER JOIN p.unidad               \t  u  ");
/* 1252:1439 */     sql.append(" LEFT  JOIN p.unidadAlmacenamiento    ua ");
/* 1253:1440 */     sql.append(" LEFT  JOIN p.unidadVenta   \t\t  uv ");
/* 1254:1441 */     sql.append(" LEFT  JOIN p.unidadCompra   \t\t  uc ");
/* 1255:1442 */     sql.append(" LEFT  JOIN p.presentacionProducto\t  pre ");
/* 1256:1443 */     sql.append(" LEFT  JOIN p.unidadInformativa   \t\t  ui ");
/* 1257:     */     
/* 1258:1445 */     sql.append(" WHERE 0=0 ");
/* 1259:1447 */     if (visualizacion != null)
/* 1260:     */     {
/* 1261:1448 */       List<DetalleVisualizacion> listaDetalleVisualizacionUsuario = new ArrayList();
/* 1262:     */       
/* 1263:1450 */       listaDetalleVisualizacionUsuario = this.visualizacionUsuarioDao.getListaDetalleVisualizacion(visualizacion.getIdVisualizacion());
/* 1264:1451 */       String filtroVisualizacion = " AND ( ";
/* 1265:1452 */       boolean primerRegistro = true;
/* 1266:1453 */       for (DetalleVisualizacion detalleVisualizacionUsuario : listaDetalleVisualizacionUsuario) {
/* 1267:1454 */         if (detalleVisualizacionUsuario.getSubcategoriaProducto() == null)
/* 1268:     */         {
/* 1269:1455 */           if (!primerRegistro) {
/* 1270:1456 */             filtroVisualizacion = filtroVisualizacion + " or ";
/* 1271:     */           } else {
/* 1272:1458 */             primerRegistro = false;
/* 1273:     */           }
/* 1274:1460 */           filtroVisualizacion = filtroVisualizacion + " cp.idCategoriaProducto = " + detalleVisualizacionUsuario.getCategoriaProducto().getIdCategoriaProducto();
/* 1275:     */         }
/* 1276:     */         else
/* 1277:     */         {
/* 1278:1462 */           if (!primerRegistro) {
/* 1279:1463 */             filtroVisualizacion = filtroVisualizacion + " or ";
/* 1280:     */           } else {
/* 1281:1465 */             primerRegistro = false;
/* 1282:     */           }
/* 1283:1468 */           filtroVisualizacion = filtroVisualizacion + " sp.idSubcategoriaProducto = " + detalleVisualizacionUsuario.getSubcategoriaProducto().getIdSubcategoriaProducto();
/* 1284:     */         }
/* 1285:     */       }
/* 1286:1471 */       filtroVisualizacion = filtroVisualizacion + " ) ";
/* 1287:1472 */       sql.append(filtroVisualizacion);
/* 1288:     */     }
/* 1289:1474 */     TipoProducto tipoProducto = null;
/* 1290:1475 */     String stringBoolean = null;
/* 1291:1476 */     Integer idOrganizacion = null;
/* 1292:     */     String parametro;
/* 1293:1478 */     if (filter != null)
/* 1294:     */     {
/* 1295:1480 */       int i = 1;
/* 1296:1481 */       parametro = "parametro";
/* 1297:1482 */       for (Iterator localIterator2 = filter.keySet().iterator(); localIterator2.hasNext();)
/* 1298:     */       {
/* 1299:1482 */         filterProperty = (String)localIterator2.next();
/* 1300:1483 */         if (filterProperty.equals("codigo"))
/* 1301:     */         {
/* 1302:1484 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1303:1485 */           sql.append(" AND LOWER(p.codigo) LIKE :" + parametro + i);
/* 1304:1486 */           i++;
/* 1305:     */         }
/* 1306:1487 */         else if (filterProperty.equals("codigoAlterno"))
/* 1307:     */         {
/* 1308:1488 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1309:1489 */           sql.append(" AND LOWER(p.codigoAlterno) LIKE :" + parametro + i);
/* 1310:1490 */           i++;
/* 1311:     */         }
/* 1312:1491 */         else if (filterProperty.equals("codigoBarras"))
/* 1313:     */         {
/* 1314:1492 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1315:1493 */           sql.append(" AND LOWER(p.codigoBarras) LIKE :" + parametro + i);
/* 1316:1494 */           i++;
/* 1317:     */         }
/* 1318:1495 */         else if (filterProperty.equals("nombre"))
/* 1319:     */         {
/* 1320:1496 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1321:1497 */           sql.append(" AND LOWER(p.nombre) LIKE :" + parametro + i);
/* 1322:1498 */           i++;
/* 1323:     */         }
/* 1324:1499 */         else if (filterProperty.equals("nombreComercial"))
/* 1325:     */         {
/* 1326:1500 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1327:1501 */           sql.append(" AND LOWER(p.nombreComercial) LIKE :" + parametro + i);
/* 1328:1502 */           i++;
/* 1329:     */         }
/* 1330:1503 */         else if (filterProperty.equals("nombreSubcategoriaProducto"))
/* 1331:     */         {
/* 1332:1504 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1333:1505 */           sql.append(" AND LOWER(sp.nombre) LIKE :" + parametro + i);
/* 1334:1506 */           i++;
/* 1335:     */         }
/* 1336:1507 */         else if (filterProperty.equals("nombreCategoriaProducto"))
/* 1337:     */         {
/* 1338:1508 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1339:1509 */           sql.append(" AND LOWER(cp.nombre) LIKE :" + parametro + i);
/* 1340:1510 */           i++;
/* 1341:     */         }
/* 1342:1511 */         else if (filterProperty.equals("codigoUnidad"))
/* 1343:     */         {
/* 1344:1512 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1345:1513 */           sql.append(" AND LOWER(u.codigo) LIKE :" + parametro + i);
/* 1346:1514 */           i++;
/* 1347:     */         }
/* 1348:1515 */         else if (filterProperty.equals("codigoUnidadAlmacenamiento"))
/* 1349:     */         {
/* 1350:1516 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1351:1517 */           sql.append(" AND LOWER(ua.codigo) LIKE :" + parametro + i);
/* 1352:1518 */           i++;
/* 1353:     */         }
/* 1354:1519 */         else if (filterProperty.equals("codigoUnidadVenta"))
/* 1355:     */         {
/* 1356:1520 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1357:1521 */           sql.append(" AND LOWER(uv.codigo) LIKE :" + parametro + i);
/* 1358:1522 */           i++;
/* 1359:     */         }
/* 1360:1523 */         else if (filterProperty.equals("codigoUnidadCompra"))
/* 1361:     */         {
/* 1362:1524 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1363:1525 */           sql.append(" AND LOWER(uc.codigo) LIKE :" + parametro + i);
/* 1364:1526 */           i++;
/* 1365:     */         }
/* 1366:1527 */         else if (filterProperty.equals("activo"))
/* 1367:     */         {
/* 1368:1528 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1369:1529 */           sql.append(" AND p.activo = " + stringBoolean);
/* 1370:     */         }
/* 1371:1530 */         else if (filterProperty.equals("indicadorVenta"))
/* 1372:     */         {
/* 1373:1531 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1374:1532 */           sql.append(" AND p.indicadorVenta = " + stringBoolean);
/* 1375:     */         }
/* 1376:1533 */         else if (filterProperty.equals("indicadorCompra"))
/* 1377:     */         {
/* 1378:1534 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1379:1535 */           sql.append(" AND p.indicadorCompra = " + stringBoolean);
/* 1380:     */         }
/* 1381:1536 */         else if (filterProperty.equals("indicadorProduccion"))
/* 1382:     */         {
/* 1383:1537 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1384:1538 */           sql.append(" AND p.indicadorProduccion = " + stringBoolean);
/* 1385:     */         }
/* 1386:1539 */         else if (filterProperty.equals("indicadorMantenimiento"))
/* 1387:     */         {
/* 1388:1540 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1389:1541 */           sql.append(" AND p.indicadorMantenimiento=" + stringBoolean);
/* 1390:     */         }
/* 1391:1542 */         else if (filterProperty.equals("tipoProducto"))
/* 1392:     */         {
/* 1393:1543 */           tipoProducto = TipoProducto.valueOf((String)filter.get("tipoProducto"));
/* 1394:1544 */           sql.append(" AND p.tipoProducto =:tipoProducto");
/* 1395:     */         }
/* 1396:1545 */         else if (filterProperty.equals("idOrganizacion"))
/* 1397:     */         {
/* 1398:1546 */           idOrganizacion = Integer.valueOf((String)filter.get("idOrganizacion"));
/* 1399:1547 */           sql.append(" AND p.idOrganizacion =:idOrganizacion");
/* 1400:     */         }
/* 1401:1548 */         else if (filterProperty.equals("indicadorPesoBalanza"))
/* 1402:     */         {
/* 1403:1549 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1404:1550 */           sql.append(" AND p.indicadorPesoBalanza = " + stringBoolean);
/* 1405:     */         }
/* 1406:1551 */         else if (filterProperty.equals("indicadorConsumo"))
/* 1407:     */         {
/* 1408:1552 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1409:1553 */           sql.append(" AND p.indicadorConsumo = " + stringBoolean);
/* 1410:     */         }
/* 1411:1556 */         if (filtroPorAtributo) {
/* 1412:1558 */           if ((filterProperty.equals("atributo1")) && (filtroProducto.isIndicadorAtributo1())) {
/* 1413:1559 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1414:1560 */           } else if ((filterProperty.equals("atributo2")) && (filtroProducto.isIndicadorAtributo2())) {
/* 1415:1561 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1416:1562 */           } else if ((filterProperty.equals("atributo3")) && (filtroProducto.isIndicadorAtributo3())) {
/* 1417:1563 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1418:1564 */           } else if ((filterProperty.equals("atributo4")) && (filtroProducto.isIndicadorAtributo4())) {
/* 1419:1565 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1420:1566 */           } else if ((filterProperty.equals("atributo5")) && (filtroProducto.isIndicadorAtributo5())) {
/* 1421:1567 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1422:1568 */           } else if ((filterProperty.equals("atributo6")) && (filtroProducto.isIndicadorAtributo6())) {
/* 1423:1569 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1424:1570 */           } else if ((filterProperty.equals("atributo7")) && (filtroProducto.isIndicadorAtributo7())) {
/* 1425:1571 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1426:1572 */           } else if ((filterProperty.equals("atributo8")) && (filtroProducto.isIndicadorAtributo8())) {
/* 1427:1573 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1428:1574 */           } else if ((filterProperty.equals("atributo9")) && (filtroProducto.isIndicadorAtributo9())) {
/* 1429:1575 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1430:1576 */           } else if ((filterProperty.equals("atributo10")) && (filtroProducto.isIndicadorAtributo10())) {
/* 1431:1577 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1432:     */           }
/* 1433:     */         }
/* 1434:     */       }
/* 1435:     */       String filterProperty;
/* 1436:1582 */       int k = 0;
/* 1437:1583 */       for (String filterProperty : filtroAtributo.keySet())
/* 1438:     */       {
/* 1439:1584 */         Atributo atributo = null;
/* 1440:1585 */         if (filterProperty.equals("atributo1")) {
/* 1441:1586 */           atributo = filtroProducto.getAtributo1();
/* 1442:     */         }
/* 1443:1588 */         if (filterProperty.equals("atributo2")) {
/* 1444:1589 */           atributo = filtroProducto.getAtributo2();
/* 1445:     */         }
/* 1446:1591 */         if (filterProperty.equals("atributo3")) {
/* 1447:1592 */           atributo = filtroProducto.getAtributo3();
/* 1448:     */         }
/* 1449:1594 */         if (filterProperty.equals("atributo4")) {
/* 1450:1595 */           atributo = filtroProducto.getAtributo4();
/* 1451:     */         }
/* 1452:1597 */         if (filterProperty.equals("atributo5")) {
/* 1453:1598 */           atributo = filtroProducto.getAtributo5();
/* 1454:     */         }
/* 1455:1600 */         if (filterProperty.equals("atributo6")) {
/* 1456:1601 */           atributo = filtroProducto.getAtributo6();
/* 1457:     */         }
/* 1458:1603 */         if (filterProperty.equals("atributo7")) {
/* 1459:1604 */           atributo = filtroProducto.getAtributo7();
/* 1460:     */         }
/* 1461:1606 */         if (filterProperty.equals("atributo8")) {
/* 1462:1607 */           atributo = filtroProducto.getAtributo8();
/* 1463:     */         }
/* 1464:1609 */         if (filterProperty.equals("atributo9")) {
/* 1465:1610 */           atributo = filtroProducto.getAtributo9();
/* 1466:     */         }
/* 1467:1612 */         if (filterProperty.equals("atributo10")) {
/* 1468:1613 */           atributo = filtroProducto.getAtributo10();
/* 1469:     */         }
/* 1470:1616 */         k++;
/* 1471:1617 */         sql.append(" AND EXISTS( SELECT 1 FROM ProductoAtributo pa" + k + " WHERE pa" + k + ".producto = pa" + (k - 1) + ".producto AND pa" + k + ".valor LIKE '" + 
/* 1472:1618 */           (String)filtroAtributo.get(filterProperty.toLowerCase()) + "%'");
/* 1473:1619 */         sql.append(" AND  pa" + k + ".atributo.idAtributo = " + atributo.getId());
/* 1474:     */       }
/* 1475:1621 */       for (int j = 1; j <= k; j++) {
/* 1476:1622 */         sql.append(")");
/* 1477:     */       }
/* 1478:     */     }
/* 1479:1627 */     Query query = this.em.createQuery(sql.toString());
/* 1480:1628 */     query.setParameter("idOrganizacion", idOrganizacion);
/* 1481:1630 */     for (String parametro : hmParametros.keySet())
/* 1482:     */     {
/* 1483:1631 */       filterValue = ((String)hmParametros.get(parametro)).toLowerCase();
/* 1484:1632 */       query.setParameter(parametro, "%" + filterValue + "%");
/* 1485:     */     }
/* 1486:1634 */     if (tipoProducto != null) {
/* 1487:1635 */       query.setParameter("tipoProducto", tipoProducto);
/* 1488:     */     }
/* 1489:1638 */     if ((filtroProducto.isIndicadorAtributo1()) || (filtroProducto.isIndicadorAtributo2()) || (filtroProducto.isIndicadorAtributo3()) || 
/* 1490:1639 */       (filtroProducto.isIndicadorAtributo4()) || (filtroProducto.isIndicadorAtributo5()) || (filtroProducto.isIndicadorAtributo6()) || 
/* 1491:1640 */       (filtroProducto.isIndicadorAtributo7()) || (filtroProducto.isIndicadorAtributo8()) || (filtroProducto.isIndicadorAtributo9()) || 
/* 1492:1641 */       (filtroProducto.isIndicadorAtributo10()))
/* 1493:     */     {
/* 1494:1642 */       query.setParameter("atributo1", filtroProducto.getAtributo1());
/* 1495:1643 */       query.setParameter("atributo2", filtroProducto.getAtributo2());
/* 1496:1644 */       query.setParameter("atributo3", filtroProducto.getAtributo3());
/* 1497:1645 */       query.setParameter("atributo4", filtroProducto.getAtributo4());
/* 1498:1646 */       query.setParameter("atributo5", filtroProducto.getAtributo5());
/* 1499:1647 */       query.setParameter("atributo6", filtroProducto.getAtributo6());
/* 1500:1648 */       query.setParameter("atributo7", filtroProducto.getAtributo7());
/* 1501:1649 */       query.setParameter("atributo8", filtroProducto.getAtributo8());
/* 1502:1650 */       query.setParameter("atributo9", filtroProducto.getAtributo9());
/* 1503:1651 */       query.setParameter("atributo10", filtroProducto.getAtributo10());
/* 1504:     */     }
/* 1505:1655 */     query.setMaxResults(filtroProducto.getNumeroRegistros());
/* 1506:     */     
/* 1507:1657 */     return query.getResultList();
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public int contarPorCriterio(FiltroProducto filtroProducto, Map<String, String> filter)
/* 1511:     */   {
/* 1512:1663 */     Map<String, String> hmParametros = new HashMap();
/* 1513:     */     
/* 1514:1665 */     String filterValue = null;
/* 1515:1666 */     StringBuilder sql = new StringBuilder();
/* 1516:1667 */     boolean filtroPorAtributo = false;
/* 1517:1668 */     Map<String, String> filtroAtributo = new HashMap();
/* 1518:1670 */     if ((filter.containsKey("atributo1")) || (filter.containsKey("atributo2")) || (filter.containsKey("atributo3")) || (filter.containsKey("atributo4")) || 
/* 1519:1671 */       (filter.containsKey("atributo5")) || (filter.containsKey("atributo6")) || (filter.containsKey("atributo7")) || 
/* 1520:1672 */       (filter.containsKey("atributo8")) || (filter.containsKey("atributo9")) || (filter.containsKey("atributo10"))) {
/* 1521:1673 */       filtroPorAtributo = true;
/* 1522:     */     }
/* 1523:1676 */     if ((filtroProducto.isIndicadorAtributo1()) || (filtroProducto.isIndicadorAtributo2()) || (filtroProducto.isIndicadorAtributo3()) || 
/* 1524:1677 */       (filtroProducto.isIndicadorAtributo4()) || (filtroProducto.isIndicadorAtributo5()) || (filtroProducto.isIndicadorAtributo6()) || 
/* 1525:1678 */       (filtroProducto.isIndicadorAtributo7()) || (filtroProducto.isIndicadorAtributo8()) || (filtroProducto.isIndicadorAtributo9()) || 
/* 1526:1679 */       (filtroProducto.isIndicadorAtributo10()))
/* 1527:     */     {
/* 1528:1681 */       sql = new StringBuilder();
/* 1529:1682 */       sql.append(" SELECT COUNT(p.idProducto) ");
/* 1530:1683 */       sql.append(" FROM ProductoAtributo pa0 ");
/* 1531:1684 */       if (filtroPorAtributo) {
/* 1532:1685 */         sql.append(" INNER JOIN pa0.producto p ");
/* 1533:     */       } else {
/* 1534:1687 */         sql.append(" RIGHT JOIN pa0.producto p ");
/* 1535:     */       }
/* 1536:1689 */       sql.append(" LEFT  JOIN pa0.atributo at ");
/* 1537:1690 */       sql.append(" INNER JOIN pa0.valorAtributo   \t\t  va ");
/* 1538:1691 */       sql.append(" INNER JOIN p.subcategoriaProducto    sp ");
/* 1539:1692 */       sql.append(" INNER JOIN sp.categoriaProducto   \t  cp ");
/* 1540:1693 */       sql.append(" INNER JOIN p.unidad               \t  u  ");
/* 1541:1694 */       sql.append(" LEFT  JOIN p.unidadAlmacenamiento    ua ");
/* 1542:1695 */       sql.append(" LEFT  JOIN p.unidadVenta   \t\t  uv ");
/* 1543:1696 */       sql.append(" LEFT  JOIN p.unidadCompra   \t\t  uc ");
/* 1544:1697 */       sql.append(" WHERE 0=0  ");
/* 1545:     */     }
/* 1546:     */     else
/* 1547:     */     {
/* 1548:1700 */       sql = new StringBuilder();
/* 1549:1701 */       sql.append(" SELECT DISTINCT p.idProducto ");
/* 1550:1702 */       sql.append(" FROM Producto p ");
/* 1551:1703 */       sql.append(" INNER JOIN p.subcategoriaProducto    sp ");
/* 1552:1704 */       sql.append(" INNER JOIN sp.categoriaProducto   \t  cp ");
/* 1553:1705 */       sql.append(" INNER JOIN p.unidad               \t  u  ");
/* 1554:1706 */       sql.append(" LEFT  JOIN p.unidadAlmacenamiento    ua ");
/* 1555:1707 */       sql.append(" LEFT  JOIN p.unidadVenta   \t\t  uv ");
/* 1556:1708 */       sql.append(" LEFT  JOIN p.unidadCompra   \t\t  uc ");
/* 1557:1709 */       sql.append(" WHERE 0=0 ");
/* 1558:     */     }
/* 1559:1712 */     TipoProducto tipoProducto = null;
/* 1560:1713 */     String stringBoolean = null;
/* 1561:1714 */     Integer idOrganizacion = null;
/* 1562:1716 */     if (filter != null)
/* 1563:     */     {
/* 1564:1718 */       int i = 1;
/* 1565:1719 */       parametro = "parametro";
/* 1566:1720 */       for (Iterator localIterator = filter.keySet().iterator(); localIterator.hasNext();)
/* 1567:     */       {
/* 1568:1720 */         filterProperty = (String)localIterator.next();
/* 1569:1721 */         if (filterProperty.equals("codigo"))
/* 1570:     */         {
/* 1571:1722 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1572:1723 */           sql.append(" AND p.codigo LIKE :" + parametro + i);
/* 1573:1724 */           i++;
/* 1574:     */         }
/* 1575:1725 */         else if (filterProperty.equals("codigoAlterno"))
/* 1576:     */         {
/* 1577:1726 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1578:1727 */           sql.append(" AND p.codigoAlterno LIKE :" + parametro + i);
/* 1579:1728 */           i++;
/* 1580:     */         }
/* 1581:1729 */         else if (filterProperty.equals("nombre"))
/* 1582:     */         {
/* 1583:1730 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1584:1731 */           sql.append(" AND p.nombre LIKE :" + parametro + i);
/* 1585:1732 */           i++;
/* 1586:     */         }
/* 1587:1733 */         else if (filterProperty.equals("nombreSubcategoriaProducto"))
/* 1588:     */         {
/* 1589:1734 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1590:1735 */           sql.append(" AND sp.nombre LIKE :" + parametro + i);
/* 1591:1736 */           i++;
/* 1592:     */         }
/* 1593:1737 */         else if (filterProperty.equals("nombreCategoriaProducto"))
/* 1594:     */         {
/* 1595:1738 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1596:1739 */           sql.append(" AND cp.nombre LIKE :" + parametro + i);
/* 1597:1740 */           i++;
/* 1598:     */         }
/* 1599:1741 */         else if (filterProperty.equals("codigoUnidad"))
/* 1600:     */         {
/* 1601:1742 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1602:1743 */           sql.append(" AND u.codigo LIKE :" + parametro + i);
/* 1603:1744 */           i++;
/* 1604:     */         }
/* 1605:1745 */         else if (filterProperty.equals("codigoUnidadAlmacenamiento"))
/* 1606:     */         {
/* 1607:1746 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1608:1747 */           sql.append(" AND ua.codigo LIKE :" + parametro + i);
/* 1609:1748 */           i++;
/* 1610:     */         }
/* 1611:1749 */         else if (filterProperty.equals("codigoUnidadVenta"))
/* 1612:     */         {
/* 1613:1750 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1614:1751 */           sql.append(" AND uv.codigo LIKE :" + parametro + i);
/* 1615:1752 */           i++;
/* 1616:     */         }
/* 1617:1753 */         else if (filterProperty.equals("codigoUnidadCompra"))
/* 1618:     */         {
/* 1619:1754 */           hmParametros.put(parametro + i, filter.get(filterProperty));
/* 1620:1755 */           sql.append(" AND uc.codigo LIKE :" + parametro + i);
/* 1621:1756 */           i++;
/* 1622:     */         }
/* 1623:1757 */         else if (filterProperty.equals("activo"))
/* 1624:     */         {
/* 1625:1758 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1626:1759 */           sql.append(" AND p.activo = " + stringBoolean);
/* 1627:     */         }
/* 1628:1760 */         else if (filterProperty.equals("indicadorVenta"))
/* 1629:     */         {
/* 1630:1761 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1631:1762 */           sql.append(" AND p.indicadorVenta = " + stringBoolean);
/* 1632:     */         }
/* 1633:1763 */         else if (filterProperty.equals("indicadorCompra"))
/* 1634:     */         {
/* 1635:1764 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1636:1765 */           sql.append(" AND p.indicadorCompra = " + stringBoolean);
/* 1637:     */         }
/* 1638:1766 */         else if (filterProperty.equals("indicadorProduccion"))
/* 1639:     */         {
/* 1640:1767 */           stringBoolean = ((String)filter.get(filterProperty)).equals("true") ? "true" : "false";
/* 1641:1768 */           sql.append(" AND p.indicadorProduccion = " + stringBoolean);
/* 1642:     */         }
/* 1643:1769 */         else if (filterProperty.equals("tipoProducto"))
/* 1644:     */         {
/* 1645:1770 */           tipoProducto = TipoProducto.valueOf((String)filter.get("tipoProducto"));
/* 1646:1771 */           sql.append(" AND p.tipoProducto =:tipoProducto");
/* 1647:     */         }
/* 1648:1772 */         else if (filterProperty.equals("idOrganizacion"))
/* 1649:     */         {
/* 1650:1773 */           idOrganizacion = Integer.valueOf((String)filter.get("idOrganizacion"));
/* 1651:1774 */           sql.append(" AND p.idOrganizacion =:idOrganizacion");
/* 1652:     */         }
/* 1653:1777 */         if (filtroPorAtributo) {
/* 1654:1779 */           if (filterProperty.equals("atributo1")) {
/* 1655:1780 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1656:1781 */           } else if (filterProperty.equals("atributo2")) {
/* 1657:1782 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1658:1783 */           } else if (filterProperty.equals("atributo3")) {
/* 1659:1784 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1660:1785 */           } else if (filterProperty.equals("atributo4")) {
/* 1661:1786 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1662:1787 */           } else if (filterProperty.equals("atributo5")) {
/* 1663:1788 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1664:1789 */           } else if (filterProperty.equals("atributo6")) {
/* 1665:1790 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1666:1791 */           } else if (filterProperty.equals("atributo7")) {
/* 1667:1792 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1668:1793 */           } else if (filterProperty.equals("atributo8")) {
/* 1669:1794 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1670:1795 */           } else if (filterProperty.equals("atributo9")) {
/* 1671:1796 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1672:1797 */           } else if (filterProperty.equals("atributo10")) {
/* 1673:1798 */             filtroAtributo.put(filterProperty, filter.get(filterProperty));
/* 1674:     */           }
/* 1675:     */         }
/* 1676:     */       }
/* 1677:     */       String filterProperty;
/* 1678:1803 */       int k = 0;
/* 1679:1804 */       for (String filterProperty : filtroAtributo.keySet())
/* 1680:     */       {
/* 1681:1805 */         k++;
/* 1682:1806 */         sql.append(" AND EXISTS( SELECT 1 FROM ProductoAtributo pa" + k + " WHERE pa" + k + ".producto = pa" + (k - 1) + ".producto AND pa" + k + ".valor LIKE :valorAtributo" + k);
/* 1683:     */       }
/* 1684:1809 */       for (int j = 1; j <= k; j++) {
/* 1685:1810 */         sql.append(")");
/* 1686:     */       }
/* 1687:     */     }
/* 1688:1815 */     Query query = this.em.createQuery(sql.toString());
/* 1689:1816 */     query.setParameter("idOrganizacion", idOrganizacion);
/* 1690:1818 */     for (String parametro = hmParametros.keySet().iterator(); parametro.hasNext();)
/* 1691:     */     {
/* 1692:1818 */       parametro = (String)parametro.next();
/* 1693:1819 */       filterValue = (String)hmParametros.get(parametro);
/* 1694:1820 */       query.setParameter((String)parametro, "%" + filterValue + "%");
/* 1695:     */     }
/* 1696:1823 */     if (tipoProducto != null) {
/* 1697:1824 */       query.setParameter("tipoProducto", tipoProducto);
/* 1698:     */     }
/* 1699:1827 */     int k = 0;
/* 1700:1828 */     for (Object parametro = filtroAtributo.keySet().iterator(); ((Iterator)parametro).hasNext();)
/* 1701:     */     {
/* 1702:1828 */       String filterProperty = (String)((Iterator)parametro).next();
/* 1703:1829 */       k++;
/* 1704:1830 */       query.setParameter("valorAtributo" + k, (String)filtroAtributo.get(filterProperty) + "%");
/* 1705:     */     }
/* 1706:1833 */     return ((Long)query.getSingleResult()).intValue();
/* 1707:     */   }
/* 1708:     */   
/* 1709:     */   public Producto cargarDetalleListaMaterial(Producto producto)
/* 1710:     */   {
/* 1711:1838 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 1712:     */     
/* 1713:     */ 
/* 1714:1841 */     CriteriaQuery<Producto> cqCabecera = criteriaBuilder.createQuery(Producto.class);
/* 1715:1842 */     Root<Producto> fromCabecera = cqCabecera.from(Producto.class);
/* 1716:1843 */     fromCabecera.fetch("unidad", JoinType.LEFT);
/* 1717:1844 */     fromCabecera.fetch("unidadInformativa", JoinType.LEFT);
/* 1718:1845 */     Path<Integer> pathId = fromCabecera.get("idProducto");
/* 1719:1846 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(producto.getId())));
/* 1720:1847 */     CriteriaQuery<Producto> cqProducto = cqCabecera.select(fromCabecera);
/* 1721:1848 */     producto = (Producto)this.em.createQuery(cqProducto).getSingleResult();
/* 1722:     */     
/* 1723:1850 */     this.em.detach(producto);
/* 1724:     */     
/* 1725:     */ 
/* 1726:1853 */     CriteriaQuery<ProductoMaterial> cqProductoMaterial = criteriaBuilder.createQuery(ProductoMaterial.class);
/* 1727:1854 */     Root<ProductoMaterial> fromProductoMaterial = cqProductoMaterial.from(ProductoMaterial.class);
/* 1728:1855 */     Fetch<Object, Object> sustituto = fromProductoMaterial.fetch("sustituto", JoinType.LEFT);
/* 1729:1856 */     sustituto.fetch("unidad", JoinType.LEFT);
/* 1730:     */     
/* 1731:1858 */     Fetch<Object, Object> material = fromProductoMaterial.fetch("material", JoinType.LEFT);
/* 1732:     */     
/* 1733:1860 */     material.fetch("unidad", JoinType.LEFT);
/* 1734:     */     
/* 1735:1862 */     cqProductoMaterial.where(criteriaBuilder.equal(fromProductoMaterial.join("producto"), producto));
/* 1736:1863 */     cqProductoMaterial.orderBy(new Order[] { criteriaBuilder.asc(fromProductoMaterial.get("orden")) });
/* 1737:1864 */     CriteriaQuery<ProductoMaterial> selectProductoMaterial = cqProductoMaterial.select(fromProductoMaterial);
/* 1738:     */     
/* 1739:1866 */     List<ProductoMaterial> listaProductoMaterial = this.em.createQuery(selectProductoMaterial).getResultList();
/* 1740:1867 */     producto.setListaProductoMaterial(listaProductoMaterial);
/* 1741:     */     
/* 1742:     */ 
/* 1743:1870 */     CriteriaQuery<ProductoRutaFabricacion> cqRuta = criteriaBuilder.createQuery(ProductoRutaFabricacion.class);
/* 1744:1871 */     Root<ProductoRutaFabricacion> fromRuta = cqRuta.from(ProductoRutaFabricacion.class);
/* 1745:     */     
/* 1746:1873 */     fromRuta.fetch("rutaFabricacion", JoinType.LEFT);
/* 1747:     */     
/* 1748:1875 */     cqRuta.where(criteriaBuilder.equal(fromRuta.join("producto"), producto));
/* 1749:1876 */     CriteriaQuery<ProductoRutaFabricacion> selectRuta = cqRuta.select(fromRuta);
/* 1750:     */     
/* 1751:1878 */     List<ProductoRutaFabricacion> listaProductoRutaFabricacion = this.em.createQuery(selectRuta).getResultList();
/* 1752:1879 */     producto.setListaProductoRutaFabricacion(listaProductoRutaFabricacion);
/* 1753:     */     
/* 1754:     */ 
/* 1755:1882 */     CriteriaQuery<SubProducto> cqSubProducto = criteriaBuilder.createQuery(SubProducto.class);
/* 1756:1883 */     Root<SubProducto> fromSubProducto = cqSubProducto.from(SubProducto.class);
/* 1757:     */     
/* 1758:1885 */     fromSubProducto.fetch("producto", JoinType.LEFT);
/* 1759:     */     
/* 1760:1887 */     cqSubProducto.where(criteriaBuilder.equal(fromSubProducto.join("productoPadre").get("idProducto"), Integer.valueOf(producto.getId())));
/* 1761:1888 */     CriteriaQuery<SubProducto> selectSubProducto = cqSubProducto.select(fromSubProducto);
/* 1762:     */     
/* 1763:1890 */     List<SubProducto> listaSubProducto = this.em.createQuery(selectSubProducto).getResultList();
/* 1764:1891 */     producto.setListaSubProducto(listaSubProducto);
/* 1765:1892 */     return producto;
/* 1766:     */   }
/* 1767:     */   
/* 1768:     */   public Producto cargarDetalleListaVariableCalidadProducto(Producto producto)
/* 1769:     */   {
/* 1770:1896 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 1771:     */     
/* 1772:     */ 
/* 1773:1899 */     CriteriaQuery<Producto> cqCabecera = criteriaBuilder.createQuery(Producto.class);
/* 1774:1900 */     Root<Producto> fromCabecera = cqCabecera.from(Producto.class);
/* 1775:1901 */     fromCabecera.fetch("unidad", JoinType.LEFT);
/* 1776:1902 */     fromCabecera.fetch("unidadInformativa", JoinType.LEFT);
/* 1777:1903 */     Path<Integer> pathId = fromCabecera.get("idProducto");
/* 1778:1904 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(producto.getId())));
/* 1779:1905 */     CriteriaQuery<Producto> cqProducto = cqCabecera.select(fromCabecera);
/* 1780:1906 */     producto = (Producto)this.em.createQuery(cqProducto).getSingleResult();
/* 1781:     */     
/* 1782:1908 */     this.em.detach(producto);
/* 1783:     */     
/* 1784:     */ 
/* 1785:1911 */     CriteriaQuery<VariableCalidadProducto> cqVCP = criteriaBuilder.createQuery(VariableCalidadProducto.class);
/* 1786:1912 */     Root<VariableCalidadProducto> fromVCP = cqVCP.from(VariableCalidadProducto.class);
/* 1787:1913 */     fromVCP.fetch("variableCalidad", JoinType.LEFT);
/* 1788:1914 */     cqVCP.where(criteriaBuilder.equal(fromVCP.join("producto"), producto));
/* 1789:1915 */     CriteriaQuery<VariableCalidadProducto> selectVCP = cqVCP.select(fromVCP);
/* 1790:1916 */     List<VariableCalidadProducto> listaVCP = this.em.createQuery(selectVCP).getResultList();
/* 1791:1917 */     producto.setListaVariableCalidadProducto(listaVCP);
/* 1792:     */     
/* 1793:1919 */     return producto;
/* 1794:     */   }
/* 1795:     */   
/* 1796:     */   public UnidadManejoProducto getUnidadManejoProducto(Producto producto, UnidadManejo unidadManejo)
/* 1797:     */   {
/* 1798:1923 */     StringBuilder sqll = new StringBuilder();
/* 1799:     */     
/* 1800:1925 */     sqll.append(" SELECT gp FROM UnidadManejoProducto gp");
/* 1801:1926 */     sqll.append(" JOIN FETCH gp.unidadManejo ga");
/* 1802:1927 */     sqll.append(" WHERE gp.producto=:producto");
/* 1803:1928 */     sqll.append(" AND gp.unidadManejo=:unidadManejo");
/* 1804:     */     
/* 1805:1930 */     Query query = this.em.createQuery(sqll.toString());
/* 1806:1931 */     query.setParameter("producto", producto);
/* 1807:1932 */     query.setParameter("unidadManejo", unidadManejo);
/* 1808:     */     try
/* 1809:     */     {
/* 1810:1935 */       return (UnidadManejoProducto)query.getSingleResult();
/* 1811:     */     }
/* 1812:     */     catch (NoResultException e) {}
/* 1813:1937 */     return null;
/* 1814:     */   }
/* 1815:     */   
/* 1816:     */   public List<ProductoMaterial> getProductosCambioAngilProporcionProduccion(int idOrganizacion, SubcategoriaProducto subcategoriaProducto, CategoriaProducto categoriaProducto, MarcaProducto marcaProducto, Producto material)
/* 1817:     */   {
/* 1818:1945 */     StringBuilder sbSQL = new StringBuilder();
/* 1819:1946 */     sbSQL.append(" SELECT pm ");
/* 1820:1947 */     sbSQL.append(" FROM ProductoMaterial pm ");
/* 1821:1948 */     sbSQL.append(" INNER JOIN FETCH pm.material mat ");
/* 1822:1949 */     sbSQL.append(" LEFT JOIN FETCH mat.unidad un ");
/* 1823:1950 */     sbSQL.append(" INNER JOIN FETCH pm.producto pro ");
/* 1824:1951 */     sbSQL.append(" LEFT JOIN FETCH pro.subcategoriaProducto scpro ");
/* 1825:1952 */     sbSQL.append(" LEFT JOIN FETCH scpro.categoriaProducto cpro ");
/* 1826:1953 */     sbSQL.append(" LEFT JOIN FETCH pro.marcaProducto mpro ");
/* 1827:1954 */     sbSQL.append(" WHERE pm.idOrganizacion =:idOrganizacion ");
/* 1828:1955 */     sbSQL.append(" AND pro.indicadorProduccion = true ");
/* 1829:1956 */     sbSQL.append(" AND pm.activo = true ");
/* 1830:1957 */     if (material != null) {
/* 1831:1958 */       sbSQL.append(" AND mat.idProducto =:idMaterial ");
/* 1832:     */     }
/* 1833:1960 */     if (subcategoriaProducto != null) {
/* 1834:1961 */       sbSQL.append(" AND scpro.idSubcategoriaProducto =:idSubcategoriaProducto ");
/* 1835:     */     }
/* 1836:1963 */     if (categoriaProducto != null) {
/* 1837:1964 */       sbSQL.append(" AND cpro.idCategoriaProducto =:idCategoriaProducto ");
/* 1838:     */     }
/* 1839:1966 */     if (marcaProducto != null) {
/* 1840:1967 */       sbSQL.append(" AND mpro.idMarcaProducto =:idMarcaProducto ");
/* 1841:     */     }
/* 1842:1970 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1843:1971 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1844:1972 */     if (material != null) {
/* 1845:1973 */       query.setParameter("idMaterial", Integer.valueOf(material.getId()));
/* 1846:     */     }
/* 1847:1975 */     if (subcategoriaProducto != null) {
/* 1848:1976 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 1849:     */     }
/* 1850:1978 */     if (categoriaProducto != null) {
/* 1851:1979 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 1852:     */     }
/* 1853:1981 */     if (marcaProducto != null) {
/* 1854:1982 */       query.setParameter("idMarcaProducto", Integer.valueOf(marcaProducto.getId()));
/* 1855:     */     }
/* 1856:1985 */     return query.getResultList();
/* 1857:     */   }
/* 1858:     */   
/* 1859:     */   public List<UnidadManejo> obtenerListaUnidadManejoPorProducto(Producto producto)
/* 1860:     */   {
/* 1861:1990 */     StringBuilder sbSQL = new StringBuilder();
/* 1862:1991 */     sbSQL.append(" SELECT gv ");
/* 1863:1992 */     sbSQL.append(" FROM UnidadManejoProducto gp ");
/* 1864:1993 */     sbSQL.append(" INNER JOIN gp.producto p ");
/* 1865:1994 */     sbSQL.append(" INNER JOIN gp.unidadManejo gv ");
/* 1866:1995 */     sbSQL.append(" INNER JOIN gv.categoriaUnidadManejo cg ");
/* 1867:1996 */     sbSQL.append(" WHERE p.idProducto = :idProducto ");
/* 1868:1997 */     sbSQL.append(" AND gv.activo = true ");
/* 1869:1998 */     sbSQL.append(" AND cg.indicadorPallet != true ");
/* 1870:     */     
/* 1871:2000 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1872:2001 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 1873:2002 */     return query.getResultList();
/* 1874:     */   }
/* 1875:     */   
/* 1876:     */   public List<SaldoProducto> obtenerProductosConSaldoBodega(Bodega bodega, Date fecha, boolean indicadorTomaFisica, boolean todoProducto)
/* 1877:     */   {
/* 1878:2007 */     StringBuilder sbSQL = new StringBuilder();
/* 1879:2008 */     sbSQL.append(" SELECT sp ");
/* 1880:2009 */     sbSQL.append(" FROM SaldoProducto sp ");
/* 1881:2010 */     sbSQL.append(" INNER JOIN FETCH sp.bodega b ");
/* 1882:2011 */     sbSQL.append(" INNER JOIN FETCH sp.producto p ");
/* 1883:2012 */     sbSQL.append(" INNER JOIN FETCH p.subcategoriaProducto scp");
/* 1884:2013 */     sbSQL.append(" INNER JOIN FETCH scp.categoriaProducto cp");
/* 1885:2014 */     sbSQL.append(" LEFT JOIN FETCH p.unidad u ");
/* 1886:2015 */     sbSQL.append(" WHERE b.idBodega=:idBodega ");
/* 1887:2016 */     if (!todoProducto) {
/* 1888:2017 */       sbSQL.append(" AND p.indicadorLote = false ");
/* 1889:     */     }
/* 1890:2019 */     sbSQL.append(" AND sp.saldo <> 0 ");
/* 1891:2020 */     sbSQL.append(" AND sp.fecha=(SELECT MAX(sp2.fecha) FROM SaldoProducto sp2 ");
/* 1892:2021 */     sbSQL.append(" \t\tINNER JOIN sp2.producto p2 ");
/* 1893:2022 */     sbSQL.append(" \t\tINNER JOIN sp2.bodega b2 ");
/* 1894:2023 */     sbSQL.append(" \t\tWHERE b2.idBodega = :idBodega ");
/* 1895:2024 */     sbSQL.append(" \t\tAND p.idProducto = p2.idProducto ");
/* 1896:2025 */     sbSQL.append(" \t\tAND sp2.fecha<=:fecha ) ");
/* 1897:2026 */     if (indicadorTomaFisica)
/* 1898:     */     {
/* 1899:2027 */       sbSQL.append(" AND NOT EXISTS (SELECT 1 FROM DetalleTomaFisica dtf ");
/* 1900:2028 */       sbSQL.append(" JOIN dtf.tomaFisica tf JOIN tf.bodega b3 JOIN dtf.producto p3 ");
/* 1901:2029 */       sbSQL.append(" WHERE b3.idBodega = b.idBodega AND p3.idProducto = p.idProducto AND tf.fecha = :fecha ");
/* 1902:2030 */       sbSQL.append(" AND tf.estado =:estado)");
/* 1903:     */     }
/* 1904:2032 */     sbSQL.append(" ORDER BY p.nombre ");
/* 1905:     */     
/* 1906:2034 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1907:2035 */     query.setParameter("idBodega", Integer.valueOf(bodega.getId()));
/* 1908:2036 */     query.setParameter("fecha", fecha);
/* 1909:2037 */     if (indicadorTomaFisica) {
/* 1910:2038 */       query.setParameter("estado", Estado.ELABORADO);
/* 1911:     */     }
/* 1912:2040 */     return query.getResultList();
/* 1913:     */   }
/* 1914:     */   
/* 1915:     */   public List<SaldoProductoLote> obtenerProductosConSaldoBodegaLote(Bodega bodega, Date fecha, Producto producto, boolean indicadorTomaFisica)
/* 1916:     */   {
/* 1917:2046 */     StringBuilder sbSQL = new StringBuilder();
/* 1918:2047 */     sbSQL.append(" SELECT sp ");
/* 1919:2048 */     sbSQL.append(" FROM SaldoProductoLote sp ");
/* 1920:2049 */     sbSQL.append(" INNER JOIN FETCH sp.producto p ");
/* 1921:2050 */     sbSQL.append(" INNER JOIN FETCH p.subcategoriaProducto scp");
/* 1922:2051 */     sbSQL.append(" INNER JOIN FETCH scp.categoriaProducto cp");
/* 1923:2052 */     sbSQL.append(" INNER JOIN FETCH sp.bodega b ");
/* 1924:2053 */     sbSQL.append(" INNER JOIN FETCH sp.lote lt ");
/* 1925:2054 */     sbSQL.append(" LEFT JOIN FETCH p.unidad u ");
/* 1926:2055 */     sbSQL.append(" WHERE p.indicadorLote = true ");
/* 1927:2056 */     if (bodega != null) {
/* 1928:2057 */       sbSQL.append(" AND b.idBodega=:idBodega ");
/* 1929:     */     }
/* 1930:2059 */     if (producto != null) {
/* 1931:2060 */       sbSQL.append(" AND p.idProducto=:idProducto ");
/* 1932:     */     }
/* 1933:2062 */     sbSQL.append(" AND sp.saldo <> 0 ");
/* 1934:2063 */     sbSQL.append(" AND sp.fecha=(SELECT MAX(sp2.fecha) FROM SaldoProductoLote sp2 ");
/* 1935:2064 */     sbSQL.append(" \t\tINNER JOIN sp2.producto p2 ");
/* 1936:2065 */     sbSQL.append(" \t\tINNER JOIN sp2.bodega b2 ");
/* 1937:2066 */     sbSQL.append(" \t\tINNER JOIN sp2.lote lt2 ");
/* 1938:2067 */     sbSQL.append(" \t\tWHERE p.idProducto = p2.idProducto ");
/* 1939:2068 */     if (bodega != null) {
/* 1940:2069 */       sbSQL.append(" \t\tAND b2.idBodega = :idBodega ");
/* 1941:     */     }
/* 1942:2071 */     sbSQL.append(" \t\tAND lt.idLote = lt2.idLote ");
/* 1943:2072 */     sbSQL.append(" \t\tAND sp2.fecha<=:fecha ) ");
/* 1944:2073 */     if (indicadorTomaFisica)
/* 1945:     */     {
/* 1946:2074 */       sbSQL.append(" AND NOT EXISTS (SELECT 1 FROM DetalleTomaFisica dtf ");
/* 1947:2075 */       sbSQL.append(" JOIN dtf.tomaFisica tf JOIN tf.bodega b3 JOIN dtf.producto p3 JOIN dtf.lote lt3 ");
/* 1948:2076 */       sbSQL.append(" WHERE b3.idBodega = b.idBodega AND p3.idProducto = p.idProducto AND lt3.idLote = lt.idLote AND tf.fecha = :fecha ");
/* 1949:2077 */       sbSQL.append(" AND tf.estado = :estado )");
/* 1950:     */     }
/* 1951:2079 */     sbSQL.append(" ORDER BY lt.fechaCaducidad, p.nombre, lt.codigo ");
/* 1952:     */     
/* 1953:2081 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1954:2082 */     if (bodega != null) {
/* 1955:2083 */       query.setParameter("idBodega", Integer.valueOf(bodega.getId()));
/* 1956:     */     }
/* 1957:2085 */     if (producto != null) {
/* 1958:2086 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 1959:     */     }
/* 1960:2088 */     query.setParameter("fecha", fecha);
/* 1961:2089 */     if (indicadorTomaFisica) {
/* 1962:2090 */       query.setParameter("estado", Estado.ELABORADO);
/* 1963:     */     }
/* 1964:2092 */     return query.getResultList();
/* 1965:     */   }
/* 1966:     */   
/* 1967:     */   public List<Producto> getListaProductos(int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/* 1968:     */   {
/* 1969:2098 */     StringBuilder sbSQL = new StringBuilder();
/* 1970:2099 */     sbSQL.append(" SELECT mat ");
/* 1971:2100 */     sbSQL.append(" FROM Producto mat ");
/* 1972:2101 */     sbSQL.append(" JOIN FETCH mat.unidad uni ");
/* 1973:2102 */     sbSQL.append(" JOIN FETCH mat.subcategoriaProducto subcp ");
/* 1974:2103 */     sbSQL.append(" JOIN FETCH subcp.categoriaProducto cp ");
/* 1975:2104 */     sbSQL.append(" WHERE mat.idOrganizacion = :idOrganizacion ");
/* 1976:2105 */     sbSQL.append(" AND cp = :categoriaProducto ");
/* 1977:2106 */     if (subcategoriaProducto != null) {
/* 1978:2107 */       sbSQL.append(" AND subcp = :subcategoriaProducto ");
/* 1979:     */     }
/* 1980:2109 */     sbSQL.append(" AND mat.activo = true ");
/* 1981:2110 */     sbSQL.append(" AND mat.indicadorProduccion = true ");
/* 1982:     */     
/* 1983:2112 */     Query query = this.em.createQuery(sbSQL.toString());
/* 1984:2113 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1985:2114 */     query.setParameter("categoriaProducto", categoriaProducto);
/* 1986:2115 */     if (subcategoriaProducto != null) {
/* 1987:2116 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 1988:     */     }
/* 1989:2118 */     return query.getResultList();
/* 1990:     */   }
/* 1991:     */   
/* 1992:     */   public List<ProductoMaterial> getListaProductoMaterial(Producto producto)
/* 1993:     */   {
/* 1994:2125 */     StringBuilder sbSQL = new StringBuilder();
/* 1995:2126 */     sbSQL.append(" SELECT proma ");
/* 1996:2127 */     sbSQL.append(" FROM ProductoMaterial proma ");
/* 1997:2128 */     sbSQL.append(" JOIN FETCH proma.producto pro ");
/* 1998:2129 */     sbSQL.append(" JOIN FETCH proma.material mat ");
/* 1999:2130 */     sbSQL.append(" JOIN FETCH proma.sustituto sus ");
/* 2000:2131 */     sbSQL.append(" JOIN FETCH pro.subcategoriaProducto subcp ");
/* 2001:2132 */     sbSQL.append(" JOIN FETCH subcp.categoriaProducto cp ");
/* 2002:2133 */     sbSQL.append(" WHERE pro = :producto ");
/* 2003:     */     
/* 2004:2135 */     Query query = this.em.createQuery(sbSQL.toString());
/* 2005:2136 */     query.setParameter("producto", producto);
/* 2006:2137 */     return query.getResultList();
/* 2007:     */   }
/* 2008:     */   
/* 2009:     */   public BigDecimal getCostoDespachoDevolucion(InventarioProducto ip)
/* 2010:     */   {
/* 2011:2142 */     DetalleFacturaCliente dfc = ip.getDetalleDevolucionCliente().getDetalleFacturaClientePadre();
/* 2012:2143 */     if (dfc != null)
/* 2013:     */     {
/* 2014:2144 */       StringBuilder sql = new StringBuilder();
/* 2015:2145 */       sql.append(" SELECT ip.costo/ip.cantidad FROM InventarioProducto ip ");
/* 2016:2146 */       sql.append(" JOIN ip.detalleDespachoCliente ddc ");
/* 2017:2147 */       sql.append(" JOIN ddc.detalleFacturaCliente dfc ");
/* 2018:2148 */       sql.append(" WHERE dfc.idDetalleFacturaCliente = :idDetalleFacturaCliente ");
/* 2019:     */       
/* 2020:2150 */       Query query = this.em.createQuery(sql.toString());
/* 2021:2151 */       query.setParameter("idDetalleFacturaCliente", Integer.valueOf(dfc.getIdDetalleFacturaCliente()));
/* 2022:     */       try
/* 2023:     */       {
/* 2024:2153 */         return (BigDecimal)query.getSingleResult();
/* 2025:     */       }
/* 2026:     */       catch (NoResultException e)
/* 2027:     */       {
/* 2028:2155 */         System.out.println("----------- No existe despacho para la devolucion idInventarioProducto=" + ip.getId());
/* 2029:2156 */         return BigDecimal.ZERO;
/* 2030:     */       }
/* 2031:     */     }
/* 2032:2159 */     return BigDecimal.ZERO;
/* 2033:     */   }
/* 2034:     */   
/* 2035:     */   public Producto getProductoTransformacionAutomatica(Producto producto)
/* 2036:     */   {
/* 2037:2164 */     StringBuilder sql = new StringBuilder();
/* 2038:2165 */     sql.append(" SELECT pta ");
/* 2039:2166 */     sql.append(" FROM Producto p ");
/* 2040:2167 */     sql.append(" INNER JOIN p.productoTransformacionAutomatica pta ");
/* 2041:2168 */     sql.append(" INNER JOIN FETCH pta.unidad u ");
/* 2042:2169 */     sql.append(" LEFT JOIN FETCH pta.unidadAlmacenamiento ua ");
/* 2043:2170 */     sql.append(" LEFT JOIN FETCH pta.unidadInformativa ui ");
/* 2044:2171 */     sql.append(" WHERE p.idProducto = :idProducto ");
/* 2045:     */     
/* 2046:2173 */     Query query = this.em.createQuery(sql.toString());
/* 2047:2174 */     query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 2048:     */     try
/* 2049:     */     {
/* 2050:2177 */       return (Producto)query.getSingleResult();
/* 2051:     */     }
/* 2052:     */     catch (NoResultException e) {}
/* 2053:2179 */     return null;
/* 2054:     */   }
/* 2055:     */   
/* 2056:     */   public List<ProductoRutaFabricacionSucursal> getListaProductoRutaFabricacionSucursal(int id)
/* 2057:     */   {
/* 2058:2186 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 2059:     */     
/* 2060:     */ 
/* 2061:2189 */     CriteriaQuery<ProductoRutaFabricacionSucursal> cqProductoRutaFabricacionSucursal = criteriaBuilder.createQuery(ProductoRutaFabricacionSucursal.class);
/* 2062:2190 */     Root<ProductoRutaFabricacionSucursal> fromProductoRutaFabricacionSucursal = cqProductoRutaFabricacionSucursal.from(ProductoRutaFabricacionSucursal.class);
/* 2063:2191 */     fromProductoRutaFabricacionSucursal.fetch("sucursal", JoinType.LEFT);
/* 2064:2192 */     fromProductoRutaFabricacionSucursal.fetch("rutaFabricacion", JoinType.LEFT);
/* 2065:     */     
/* 2066:2194 */     Path<Integer> pathIdProductoRutaFabricacionSucursal = fromProductoRutaFabricacionSucursal.join("producto").get("idProducto");
/* 2067:2195 */     cqProductoRutaFabricacionSucursal.where(criteriaBuilder.equal(pathIdProductoRutaFabricacionSucursal, Integer.valueOf(id)));
/* 2068:2196 */     CriteriaQuery<ProductoRutaFabricacionSucursal> selectProductoRutaFabricacionSucursal = cqProductoRutaFabricacionSucursal.select(fromProductoRutaFabricacionSucursal);
/* 2069:     */     
/* 2070:2198 */     List<ProductoRutaFabricacionSucursal> listaProductoRutaFabricacionSucursal = this.em.createQuery(selectProductoRutaFabricacionSucursal).getResultList();
/* 2071:     */     
/* 2072:2200 */     return listaProductoRutaFabricacionSucursal;
/* 2073:     */   }
/* 2074:     */   
/* 2075:     */   public Producto cargarDetalleOrdenFabricacion(int id)
/* 2076:     */   {
/* 2077:2204 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 2078:     */     
/* 2079:     */ 
/* 2080:2207 */     CriteriaQuery<Producto> cqCabecera = criteriaBuilder.createQuery(Producto.class);
/* 2081:2208 */     Root<Producto> fromCabecera = cqCabecera.from(Producto.class);
/* 2082:     */     
/* 2083:2210 */     fromCabecera.fetch("unidad", JoinType.LEFT);
/* 2084:2211 */     fromCabecera.fetch("rutaFabricacion", JoinType.LEFT);
/* 2085:     */     
/* 2086:2213 */     Path<Integer> pathId = fromCabecera.get("idProducto");
/* 2087:2214 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(id)));
/* 2088:2215 */     CriteriaQuery<Producto> cqProducto = cqCabecera.select(fromCabecera);
/* 2089:     */     
/* 2090:2217 */     Producto producto = (Producto)this.em.createQuery(cqProducto).getSingleResult();
/* 2091:     */     
/* 2092:     */ 
/* 2093:     */ 
/* 2094:2221 */     CriteriaQuery<BodegaTrabajoProductoSucursal> cqBodegaTrabajoProductoSucursal = criteriaBuilder.createQuery(BodegaTrabajoProductoSucursal.class);
/* 2095:     */     
/* 2096:2223 */     Root<BodegaTrabajoProductoSucursal> fromBodegaTrabajoProductoSucursal = cqBodegaTrabajoProductoSucursal.from(BodegaTrabajoProductoSucursal.class);
/* 2097:     */     
/* 2098:2225 */     fromBodegaTrabajoProductoSucursal.fetch("bodegaTrabajo", JoinType.LEFT);
/* 2099:2226 */     fromBodegaTrabajoProductoSucursal.fetch("sucursal", JoinType.LEFT);
/* 2100:     */     
/* 2101:2228 */     Path<Integer> pathIdProductobodegaTrabajoSucursal = fromBodegaTrabajoProductoSucursal.join("producto").get("idProducto");
/* 2102:2229 */     cqBodegaTrabajoProductoSucursal.where(criteriaBuilder.equal(pathIdProductobodegaTrabajoSucursal, Integer.valueOf(id)));
/* 2103:     */     
/* 2104:2231 */     CriteriaQuery<BodegaTrabajoProductoSucursal> selectBodegaTrabajoProductoSucursal = cqBodegaTrabajoProductoSucursal.select(fromBodegaTrabajoProductoSucursal);
/* 2105:     */     
/* 2106:2233 */     List<BodegaTrabajoProductoSucursal> listaBodegaTrabajoProductoSucursal = this.em.createQuery(selectBodegaTrabajoProductoSucursal).getResultList();
/* 2107:2234 */     producto.setListaBodegaTrabajoSucursal(listaBodegaTrabajoProductoSucursal);
/* 2108:     */     
/* 2109:     */ 
/* 2110:     */ 
/* 2111:2238 */     CriteriaQuery<ProductoRutaFabricacionSucursal> cqProductoRutaFabricacionSucursal = criteriaBuilder.createQuery(ProductoRutaFabricacionSucursal.class);
/* 2112:     */     
/* 2113:2240 */     Root<ProductoRutaFabricacionSucursal> fromProductoRutaFabricacionSucursal = cqProductoRutaFabricacionSucursal.from(ProductoRutaFabricacionSucursal.class);
/* 2114:2241 */     fromProductoRutaFabricacionSucursal.fetch("sucursal", JoinType.LEFT);
/* 2115:2242 */     fromProductoRutaFabricacionSucursal.fetch("rutaFabricacion", JoinType.LEFT);
/* 2116:     */     
/* 2117:2244 */     Path<Integer> pathIdProductoRutaFabricacionSucursal = fromProductoRutaFabricacionSucursal.join("producto").get("idProducto");
/* 2118:2245 */     cqProductoRutaFabricacionSucursal.where(criteriaBuilder.equal(pathIdProductoRutaFabricacionSucursal, Integer.valueOf(id)));
/* 2119:     */     
/* 2120:2247 */     CriteriaQuery<ProductoRutaFabricacionSucursal> selectProductoRutaFabricacionSucursal = cqProductoRutaFabricacionSucursal.select(fromProductoRutaFabricacionSucursal);
/* 2121:     */     
/* 2122:     */ 
/* 2123:2250 */     List<ProductoRutaFabricacionSucursal> listaProductoRutaFabricacionSucursal = this.em.createQuery(selectProductoRutaFabricacionSucursal).getResultList();
/* 2124:2251 */     producto.setListaProductoRutaFabricacionSucursal(listaProductoRutaFabricacionSucursal);
/* 2125:     */     
/* 2126:2253 */     return producto;
/* 2127:     */   }
/* 2128:     */   
/* 2129:     */   public Producto cargarProductoConAtributoInstancia(int id)
/* 2130:     */   {
/* 2131:2265 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 2132:     */     
/* 2133:     */ 
/* 2134:2268 */     CriteriaQuery<Producto> cqCabecera = criteriaBuilder.createQuery(Producto.class);
/* 2135:2269 */     Root<Producto> fromCabecera = cqCabecera.from(Producto.class);
/* 2136:2270 */     for (int i = 1; i <= 10; i++) {
/* 2137:2271 */       fromCabecera.fetch("atributoProduccion" + i, JoinType.LEFT);
/* 2138:     */     }
/* 2139:2273 */     Path<Integer> pathId = fromCabecera.get("idProducto");
/* 2140:2274 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(id)));
/* 2141:2275 */     CriteriaQuery<Producto> cqProducto = cqCabecera.select(fromCabecera);
/* 2142:     */     
/* 2143:2277 */     Producto producto = (Producto)this.em.createQuery(cqProducto).getSingleResult();
/* 2144:     */     
/* 2145:2279 */     Atributo atributo = producto.getAtributoProduccion1();
/* 2146:2280 */     if (atributo != null)
/* 2147:     */     {
/* 2148:2281 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2149:2282 */       producto.setAtributoProduccion1(atributo);
/* 2150:     */     }
/* 2151:2285 */     atributo = producto.getAtributoProduccion2();
/* 2152:2286 */     if (atributo != null)
/* 2153:     */     {
/* 2154:2287 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2155:2288 */       producto.setAtributoProduccion2(atributo);
/* 2156:     */     }
/* 2157:2291 */     atributo = producto.getAtributoProduccion3();
/* 2158:2292 */     if (atributo != null)
/* 2159:     */     {
/* 2160:2293 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2161:2294 */       producto.setAtributoProduccion3(atributo);
/* 2162:     */     }
/* 2163:2297 */     atributo = producto.getAtributoProduccion4();
/* 2164:2298 */     if (atributo != null)
/* 2165:     */     {
/* 2166:2299 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2167:2300 */       producto.setAtributoProduccion4(atributo);
/* 2168:     */     }
/* 2169:2303 */     atributo = producto.getAtributoProduccion5();
/* 2170:2304 */     if (atributo != null)
/* 2171:     */     {
/* 2172:2305 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2173:2306 */       producto.setAtributoProduccion5(atributo);
/* 2174:     */     }
/* 2175:2309 */     atributo = producto.getAtributoProduccion6();
/* 2176:2310 */     if (atributo != null)
/* 2177:     */     {
/* 2178:2311 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2179:2312 */       producto.setAtributoProduccion6(atributo);
/* 2180:     */     }
/* 2181:2315 */     atributo = producto.getAtributoProduccion7();
/* 2182:2316 */     if (atributo != null)
/* 2183:     */     {
/* 2184:2317 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2185:2318 */       producto.setAtributoProduccion7(atributo);
/* 2186:     */     }
/* 2187:2321 */     atributo = producto.getAtributoProduccion8();
/* 2188:2322 */     if (atributo != null)
/* 2189:     */     {
/* 2190:2323 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2191:2324 */       producto.setAtributoProduccion8(atributo);
/* 2192:     */     }
/* 2193:2327 */     atributo = producto.getAtributoProduccion9();
/* 2194:2328 */     if (atributo != null)
/* 2195:     */     {
/* 2196:2329 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2197:2330 */       producto.setAtributoProduccion9(atributo);
/* 2198:     */     }
/* 2199:2333 */     atributo = producto.getAtributoProduccion10();
/* 2200:2334 */     if (atributo != null)
/* 2201:     */     {
/* 2202:2335 */       atributo = this.atributoDao.cargarDetalle(atributo.getIdAtributo());
/* 2203:2336 */       producto.setAtributoProduccion10(atributo);
/* 2204:     */     }
/* 2205:2339 */     return producto;
/* 2206:     */   }
/* 2207:     */   
/* 2208:     */   public Producto cargarDetalleListaMezclaProducto(Producto producto)
/* 2209:     */   {
/* 2210:2345 */     this.em.detach(producto);
/* 2211:2346 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 2212:     */     
/* 2213:     */ 
/* 2214:2349 */     CriteriaQuery<MezclaProducto> cq = criteriaBuilder.createQuery(MezclaProducto.class);
/* 2215:2350 */     Root<MezclaProducto> from = cq.from(MezclaProducto.class);
/* 2216:2351 */     from.fetch("productoMezcla", JoinType.LEFT);
/* 2217:2352 */     cq.where(criteriaBuilder.equal(from.join("producto"), producto));
/* 2218:2353 */     CriteriaQuery<MezclaProducto> select = cq.select(from);
/* 2219:2354 */     List<MezclaProducto> lista = this.em.createQuery(select).getResultList();
/* 2220:2355 */     producto.setListaMezclaProducto(lista);
/* 2221:     */     
/* 2222:2357 */     return producto;
/* 2223:     */   }
/* 2224:     */   
/* 2225:     */   public List<Producto> getListaProductos(int idOrganizacion, String consulta, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/* 2226:     */   {
/* 2227:2363 */     StringBuilder sbSQL = new StringBuilder();
/* 2228:2364 */     sbSQL.append(" SELECT mat ");
/* 2229:2365 */     sbSQL.append(" FROM Producto mat ");
/* 2230:2366 */     sbSQL.append(" JOIN FETCH mat.subcategoriaProducto subcp ");
/* 2231:2367 */     sbSQL.append(" JOIN FETCH subcp.categoriaProducto cp ");
/* 2232:2368 */     sbSQL.append(" WHERE mat.idOrganizacion = :idOrganizacion ");
/* 2233:2369 */     if (categoriaProducto != null) {
/* 2234:2370 */       sbSQL.append(" AND cp = :categoriaProducto ");
/* 2235:     */     }
/* 2236:2372 */     if (subcategoriaProducto != null) {
/* 2237:2373 */       sbSQL.append(" AND subcp = :subcategoriaProducto ");
/* 2238:     */     }
/* 2239:2375 */     if (consulta != null) {
/* 2240:2376 */       sbSQL.append(" AND ( mat.nombre like :consulta OR mat.nombreComercial like :consulta OR mat.codigo like :consulta )");
/* 2241:     */     }
/* 2242:2378 */     sbSQL.append(" AND mat.activo = true ");
/* 2243:2379 */     Query query = this.em.createQuery(sbSQL.toString());
/* 2244:2380 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 2245:2381 */     if (categoriaProducto != null) {
/* 2246:2382 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 2247:     */     }
/* 2248:2384 */     if (subcategoriaProducto != null) {
/* 2249:2385 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 2250:     */     }
/* 2251:2387 */     if (consulta != null) {
/* 2252:2388 */       query.setParameter("consulta", "%" + consulta.trim() + "%");
/* 2253:     */     }
/* 2254:2390 */     query.setMaxResults(20);
/* 2255:2391 */     return query.getResultList();
/* 2256:     */   }
/* 2257:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProductoDao
 * JD-Core Version:    0.7.0.1
 */