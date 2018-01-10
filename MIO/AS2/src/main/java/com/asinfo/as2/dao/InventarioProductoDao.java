/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*    4:     */ import com.asinfo.as2.entities.Bodega;
/*    5:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*    6:     */ import com.asinfo.as2.entities.CostoProducto;
/*    7:     */ import com.asinfo.as2.entities.DespachoCliente;
/*    8:     */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*    9:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   10:     */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   11:     */ import com.asinfo.as2.entities.DimensionContable;
/*   12:     */ import com.asinfo.as2.entities.Documento;
/*   13:     */ import com.asinfo.as2.entities.FacturaCliente;
/*   14:     */ import com.asinfo.as2.entities.FacturaProveedor;
/*   15:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   16:     */ import com.asinfo.as2.entities.Lote;
/*   17:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   18:     */ import com.asinfo.as2.entities.Producto;
/*   19:     */ import com.asinfo.as2.entities.RecepcionProveedor;
/*   20:     */ import com.asinfo.as2.entities.SaldoProducto;
/*   21:     */ import com.asinfo.as2.entities.SaldoProductoLote;
/*   22:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   23:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   24:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   25:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   26:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   27:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   28:     */ import java.math.BigDecimal;
/*   29:     */ import java.util.ArrayList;
/*   30:     */ import java.util.Calendar;
/*   31:     */ import java.util.Date;
/*   32:     */ import java.util.HashMap;
/*   33:     */ import java.util.List;
/*   34:     */ import javax.ejb.EJB;
/*   35:     */ import javax.ejb.Stateless;
/*   36:     */ import javax.persistence.EntityManager;
/*   37:     */ import javax.persistence.Query;
/*   38:     */ import javax.persistence.TemporalType;
/*   39:     */ import javax.persistence.TypedQuery;
/*   40:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   41:     */ import javax.persistence.criteria.CriteriaQuery;
/*   42:     */ import javax.persistence.criteria.Expression;
/*   43:     */ import javax.persistence.criteria.Fetch;
/*   44:     */ import javax.persistence.criteria.Join;
/*   45:     */ import javax.persistence.criteria.JoinType;
/*   46:     */ import javax.persistence.criteria.Order;
/*   47:     */ import javax.persistence.criteria.Path;
/*   48:     */ import javax.persistence.criteria.Predicate;
/*   49:     */ import javax.persistence.criteria.Root;
/*   50:     */ 
/*   51:     */ @Stateless
/*   52:     */ public class InventarioProductoDao
/*   53:     */   extends AbstractDaoAS2<InventarioProducto>
/*   54:     */ {
/*   55:     */   @EJB
/*   56:     */   private CostoProductoDao costoProductoDao;
/*   57:     */   @EJB
/*   58:     */   private transient DespachoClienteDao despachoClienteDao;
/*   59:     */   @EJB
/*   60:     */   private transient RecepcionProveedorDao recepcionProveedorDao;
/*   61:     */   @EJB
/*   62:     */   private transient ServicioGenerico<SaldoProducto> saldoProductoDao;
/*   63:     */   @EJB
/*   64:     */   private transient ProductoDao productoDao;
/*   65:     */   @EJB
/*   66:     */   private transient BodegaDao bodegaDao;
/*   67:     */   
/*   68:     */   public InventarioProductoDao()
/*   69:     */   {
/*   70:  79 */     super(InventarioProducto.class);
/*   71:     */   }
/*   72:     */   
/*   73:     */   public List<InventarioProducto> obtenerMovimientos(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta)
/*   74:     */   {
/*   75: 112 */     return obtenerMovimientos(idOrganizacion, producto, bodega, fechaDesde, fechaHasta, null);
/*   76:     */   }
/*   77:     */   
/*   78:     */   public List<InventarioProducto> obtenerMovimientos(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta, Boolean indicadorCostoEstandar)
/*   79:     */   {
/*   80: 126 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   81: 127 */     CriteriaQuery<InventarioProducto> criteriaQuery = criteriaBuilder.createQuery(InventarioProducto.class);
/*   82: 128 */     Root<InventarioProducto> from = criteriaQuery.from(InventarioProducto.class);
/*   83:     */     
/*   84: 130 */     boolean indicadorCosteoHora = ParametrosSistema.isCosteoInventarioFechaHora(idOrganizacion).booleanValue();
/*   85:     */     
/*   86: 132 */     from.fetch("documento", JoinType.LEFT);
/*   87: 133 */     from.fetch("producto", JoinType.LEFT);
/*   88: 134 */     from.fetch("bodega", JoinType.LEFT);
/*   89: 135 */     from.fetch("lote", JoinType.LEFT);
/*   90: 136 */     from.fetch("inventarioProductoTransferencia", JoinType.LEFT);
/*   91: 137 */     from.fetch("detalleMovimientoInventario", JoinType.LEFT).fetch("movimientoInventario", JoinType.LEFT)
/*   92: 138 */       .fetch("ordenFabricacion", JoinType.LEFT);
/*   93:     */     
/*   94:     */ 
/*   95: 141 */     List<Expression<?>> predicates = new ArrayList();
/*   96: 142 */     predicates.add(criteriaBuilder.equal(from.get("idOrganizacion"), Integer.valueOf(idOrganizacion)));
/*   97: 143 */     if (indicadorCostoEstandar != null) {
/*   98: 144 */       predicates.add(criteriaBuilder.equal(from.join("documento").get("indicadorCostoEstandar"), indicadorCostoEstandar));
/*   99:     */     }
/*  100: 147 */     if (bodega != null) {
/*  101: 148 */       predicates.add(criteriaBuilder.equal(from.join("bodega").get("idBodega"), Integer.valueOf(bodega.getId())));
/*  102:     */     }
/*  103: 151 */     if (producto != null) {
/*  104: 152 */       predicates.add(criteriaBuilder.equal(from.join("producto").get("idProducto"), Integer.valueOf(producto.getId())));
/*  105:     */     }
/*  106: 155 */     if (fechaDesde != null) {
/*  107: 156 */       predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("fecha").as(Date.class), fechaDesde));
/*  108:     */     }
/*  109: 159 */     if (fechaHasta != null) {
/*  110: 160 */       predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("fecha").as(Date.class), fechaHasta));
/*  111:     */     }
/*  112: 163 */     CriteriaQuery<InventarioProducto> select = criteriaQuery.select(from);
/*  113: 164 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  114:     */     
/*  115:     */ 
/*  116:     */ 
/*  117: 168 */     Order fecha = criteriaBuilder.asc(from.get("fecha"));
/*  118: 169 */     Order orden = criteriaBuilder.asc(from.get("orden"));
/*  119: 170 */     Order fechaCreacion = criteriaBuilder.asc(from.get("fechaCreacion"));
/*  120: 171 */     if (indicadorCosteoHora)
/*  121:     */     {
/*  122: 172 */       Order horaCreacion = criteriaBuilder.asc(from.get("horaCreacion"));
/*  123: 173 */       criteriaQuery.orderBy(new Order[] { fecha, horaCreacion, orden, fechaCreacion });
/*  124:     */     }
/*  125:     */     else
/*  126:     */     {
/*  127: 175 */       criteriaQuery.orderBy(new Order[] { fecha, orden, fechaCreacion });
/*  128:     */     }
/*  129: 178 */     return this.em.createQuery(select).getResultList();
/*  130:     */   }
/*  131:     */   
/*  132:     */   public List<ReporteInventarioProducto> obtenerMovimientosInventarioProducto(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta, Lote lote, int numeroAtributos)
/*  133:     */   {
/*  134: 183 */     return obtenerMovimientosInventarioProducto(idOrganizacion, producto, bodega, fechaDesde, fechaHasta, lote, null, numeroAtributos);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public List<Producto> obtenerProductos(int idOrganizacion, CategoriaProducto categoriaProducto, SubcategoriaProducto subCategoriaProducto)
/*  138:     */   {
/*  139: 188 */     StringBuilder sql = new StringBuilder();
/*  140: 189 */     sql.append(" SELECT p");
/*  141: 190 */     sql.append(" FROM Producto p ");
/*  142: 191 */     if (subCategoriaProducto != null) {
/*  143: 192 */       sql.append(" LEFT JOIN p.subcategoriaProducto sp ");
/*  144:     */     }
/*  145: 194 */     if ((categoriaProducto != null) && (subCategoriaProducto == null))
/*  146:     */     {
/*  147: 195 */       sql.append(" LEFT JOIN p.subcategoriaProducto sp ");
/*  148: 196 */       sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/*  149:     */     }
/*  150: 198 */     if ((categoriaProducto != null) && (subCategoriaProducto != null)) {
/*  151: 199 */       sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/*  152:     */     }
/*  153: 201 */     sql.append(" WHERE idOrganizacion=:idOrganizacion ");
/*  154: 202 */     if (subCategoriaProducto != null) {
/*  155: 203 */       sql.append(" AND sp =:subcategoriaProducto ");
/*  156:     */     }
/*  157: 205 */     if (categoriaProducto != null) {
/*  158: 206 */       sql.append(" AND cp =:categoriaProducto ");
/*  159:     */     }
/*  160: 208 */     Query query = this.em.createQuery(sql.toString());
/*  161: 209 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  162: 210 */     if (subCategoriaProducto != null) {
/*  163: 211 */       query.setParameter("subcategoriaProducto", subCategoriaProducto);
/*  164:     */     }
/*  165: 213 */     if (categoriaProducto != null) {
/*  166: 214 */       query.setParameter("categoriaProducto", categoriaProducto);
/*  167:     */     }
/*  168: 216 */     return query.getResultList();
/*  169:     */   }
/*  170:     */   
/*  171:     */   public HashMap<String, List<Object[]>> obtenerInventarioProducto(Producto producto, boolean indicadorLote, Date fechaCorte)
/*  172:     */   {
/*  173: 233 */     StringBuilder sql = new StringBuilder();
/*  174: 234 */     sql.append(" SELECT b.idBodega ,p.idProducto, ip.fecha, SUM(ip.cantidad*ip.operacion)");
/*  175: 235 */     if (indicadorLote) {
/*  176: 236 */       sql.append(", l.idLote");
/*  177:     */     }
/*  178: 237 */     sql.append(" FROM InventarioProducto ip ");
/*  179: 238 */     sql.append(" INNER JOIN ip.bodega b ");
/*  180: 239 */     sql.append(" INNER JOIN ip.producto p ");
/*  181: 240 */     if (indicadorLote) {
/*  182: 241 */       sql.append(" INNER JOIN ip.lote l ");
/*  183:     */     }
/*  184: 242 */     sql.append(" WHERE 1=1 ");
/*  185: 243 */     if (producto != null) {
/*  186: 244 */       sql.append(" AND p=:producto ");
/*  187:     */     }
/*  188: 246 */     if (fechaCorte != null) {
/*  189: 247 */       sql.append(" AND ip.fecha >= :fechaCorte ");
/*  190:     */     }
/*  191: 249 */     sql.append(" GROUP BY b.idBodega ,p.idProducto, ip.fecha ");
/*  192: 250 */     if (indicadorLote) {
/*  193: 251 */       sql.append(", l.idLote");
/*  194:     */     }
/*  195: 252 */     sql.append(" ORDER BY b.idBodega ,p.idProducto, ip.fecha ");
/*  196: 253 */     if (indicadorLote) {
/*  197: 254 */       sql.append(", l.idLote");
/*  198:     */     }
/*  199: 255 */     Query query = this.em.createQuery(sql.toString());
/*  200: 256 */     if (producto != null) {
/*  201: 257 */       query.setParameter("producto", producto);
/*  202:     */     }
/*  203: 259 */     if (fechaCorte != null) {
/*  204: 260 */       query.setParameter("fechaCorte", fechaCorte, TemporalType.DATE);
/*  205:     */     }
/*  206: 262 */     HashMap<String, List<Object[]>> result = new HashMap();
/*  207: 263 */     List<Object[]> lis = query.getResultList();
/*  208: 264 */     for (Object[] bd : lis)
/*  209:     */     {
/*  210: 265 */       List<Object[]> toHs = new ArrayList();
/*  211: 266 */       if (indicadorLote) {
/*  212: 267 */         result.put(Integer.valueOf(bd[0].toString()) + "~" + Integer.valueOf(bd[4].toString()), toHs);
/*  213:     */       } else {
/*  214: 269 */         result.put(String.valueOf(Integer.valueOf(bd[0].toString())), toHs);
/*  215:     */       }
/*  216:     */     }
/*  217: 272 */     for (Object[] bd2 : lis) {
/*  218: 273 */       if (indicadorLote)
/*  219:     */       {
/*  220: 274 */         if (result.get(Integer.valueOf(bd2[0].toString()) + "~" + Integer.valueOf(bd2[4].toString())) != null) {
/*  221: 275 */           ((List)result.get(Integer.valueOf(bd2[0].toString()) + "~" + Integer.valueOf(bd2[4].toString()))).add(bd2);
/*  222:     */         }
/*  223:     */       }
/*  224: 278 */       else if (result.get(String.valueOf(Integer.valueOf(bd2[0].toString()))) != null) {
/*  225: 279 */         ((List)result.get(String.valueOf(Integer.valueOf(bd2[0].toString())))).add(bd2);
/*  226:     */       }
/*  227:     */     }
/*  228: 283 */     return result;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public List<SaldoProducto> consultarSaldoProducto(Producto producto, Date fechaCorte)
/*  232:     */   {
/*  233: 288 */     StringBuilder sql = new StringBuilder();
/*  234: 289 */     sql.append(" SELECT sp FROM SaldoProducto sp ");
/*  235: 290 */     sql.append(" WHERE sp.producto =:producto ");
/*  236: 291 */     if (fechaCorte != null) {
/*  237: 292 */       sql.append(" AND sp.fecha >= :fechaCorte ");
/*  238:     */     }
/*  239: 294 */     Query query = this.em.createQuery(sql.toString());
/*  240: 295 */     query.setParameter("producto", producto);
/*  241: 296 */     if (fechaCorte != null) {
/*  242: 297 */       query.setParameter("fechaCorte", fechaCorte, TemporalType.DATE);
/*  243:     */     }
/*  244: 299 */     return query.getResultList();
/*  245:     */   }
/*  246:     */   
/*  247:     */   public List<SaldoProductoLote> consultarSaldoProductoLote(Producto producto, Date fechaCorte)
/*  248:     */   {
/*  249: 304 */     StringBuilder sql = new StringBuilder();
/*  250: 305 */     sql.append(" SELECT spl FROM SaldoProductoLote spl ");
/*  251: 306 */     sql.append(" WHERE spl.producto =:producto ");
/*  252: 307 */     if (fechaCorte != null) {
/*  253: 308 */       sql.append(" AND spl.fecha >= :fechaCorte ");
/*  254:     */     }
/*  255: 310 */     Query query = this.em.createQuery(sql.toString());
/*  256: 311 */     query.setParameter("producto", producto);
/*  257: 312 */     if (fechaCorte != null) {
/*  258: 313 */       query.setParameter("fechaCorte", fechaCorte, TemporalType.DATE);
/*  259:     */     }
/*  260: 315 */     return query.getResultList();
/*  261:     */   }
/*  262:     */   
/*  263:     */   public List<ReporteInventarioProducto> obtenerMovimientosInventarioProducto(int idOrganizacion, Producto producto, Bodega bodega, Date fechaDesde, Date fechaHasta, Lote lote, DimensionContable proyecto, int numeroAtributos)
/*  264:     */   {
/*  265: 322 */     boolean indicadorCosteoHora = ParametrosSistema.isCosteoInventarioFechaHora(idOrganizacion).booleanValue();
/*  266:     */     
/*  267: 324 */     StringBuilder sql = new StringBuilder();
/*  268: 325 */     sql.append(" SELECT new ReporteInventarioProducto(ip.fecha, d.nombre , ip.numeroDocumento, ip.unidadDocumento ,b.nombre, p.idProducto, coalesce(fc.numero, CONCAT(fps.establecimiento,'-',fps.puntoEmision,'-',fps.numero)), ip.operacion, ip.orden, ");
/*  269: 326 */     sql.append(" ip.cantidadDocumento, ip.cantidad,ip.costo,fc.idFacturaCliente,l.codigo,coalesce(mi.descripcion,coalesce(dc.descripcion,coalesce(rp.descripcion,coalesce(ip.descripcion,'')))), p.codigo, p.nombre, pro.nombre, p.nombreComercial, p.codigoAlterno, ");
/*  270: 327 */     sql.append(" coalesce(emp.identificacion, ''), coalesce(emp.nombreComercial, ''), coalesce(ip.nombreFiscalEmpresa, ''),  uni.numeroDecimales)");
/*  271: 328 */     if (numeroAtributos > 0)
/*  272:     */     {
/*  273: 329 */       sql = new StringBuilder(sql.toString().substring(0, sql.toString().length() - 1));
/*  274: 330 */       for (int i = 1; i <= numeroAtributos; i++) {
/*  275: 331 */         sql.append(" ,vat" + i + ".nombre");
/*  276:     */       }
/*  277: 333 */       sql.append(")");
/*  278:     */     }
/*  279: 336 */     sql.append(" FROM InventarioProducto ip ");
/*  280: 337 */     sql.append(" LEFT JOIN ip.documento d ");
/*  281: 338 */     sql.append(" LEFT JOIN ip.producto p ");
/*  282: 339 */     sql.append(" LEFT JOIN p.unidad uni ");
/*  283: 340 */     sql.append(" LEFT JOIN ip.bodega b ");
/*  284: 341 */     sql.append(" LEFT JOIN ip.lote l ");
/*  285: 342 */     if (numeroAtributos > 0) {
/*  286: 343 */       for (int i = 1; i <= numeroAtributos; i++) {
/*  287: 344 */         sql.append(" LEFT JOIN l.valorAtributo" + i + " vat" + i);
/*  288:     */       }
/*  289:     */     }
/*  290: 348 */     sql.append(" LEFT JOIN ip.detalleMovimientoInventario dmi ");
/*  291: 349 */     sql.append(" LEFT JOIN dmi.movimientoInventario mi ");
/*  292: 350 */     sql.append(" LEFT JOIN ip.detalleDespachoCliente ddc ");
/*  293: 351 */     sql.append(" LEFT JOIN ddc.despachoCliente dc ");
/*  294: 352 */     sql.append(" LEFT JOIN dc.empresa emp ");
/*  295: 353 */     sql.append(" LEFT JOIN ddc.detalleFacturaCliente dfc ");
/*  296: 354 */     sql.append(" LEFT JOIN dfc.facturaCliente fc ");
/*  297: 355 */     sql.append(" LEFT JOIN ip.detalleRecepcionProveedor drp ");
/*  298: 356 */     sql.append(" LEFT JOIN drp.recepcionProveedor rp ");
/*  299: 357 */     sql.append(" LEFT JOIN drp.detalleFacturaProveedor dfp ");
/*  300: 358 */     sql.append(" LEFT JOIN dfp.facturaProveedor fp ");
/*  301: 359 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/*  302:     */     
/*  303:     */ 
/*  304:     */ 
/*  305: 363 */     sql.append(" LEFT JOIN ip.proyecto pro ");
/*  306: 364 */     sql.append(" WHERE ip.idOrganizacion=:idOrganizacion ");
/*  307: 365 */     sql.append(" AND ip.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  308: 366 */     if (bodega != null) {
/*  309: 367 */       sql.append(" AND b=:bodega ");
/*  310:     */     }
/*  311: 369 */     if (lote != null) {
/*  312: 370 */       sql.append(" AND l=:lote ");
/*  313:     */     }
/*  314: 372 */     if (producto != null) {
/*  315: 373 */       sql.append(" AND p=:producto ");
/*  316:     */     }
/*  317: 375 */     if (proyecto != null) {
/*  318: 376 */       sql.append(" AND pro=:proyecto");
/*  319:     */     }
/*  320: 380 */     sql.append(" ORDER BY p.idProducto, ip.fecha , " + (indicadorCosteoHora ? "ip.horaCreacion," : " ") + " ip.orden, ip.fechaCreacion");
/*  321: 381 */     Query query = this.em.createQuery(sql.toString());
/*  322: 382 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  323: 383 */     query.setParameter("fechaDesde", fechaDesde);
/*  324: 384 */     query.setParameter("fechaHasta", fechaHasta);
/*  325: 385 */     if (bodega != null) {
/*  326: 386 */       query.setParameter("bodega", bodega);
/*  327:     */     }
/*  328: 388 */     if (lote != null) {
/*  329: 389 */       query.setParameter("lote", lote);
/*  330:     */     }
/*  331: 391 */     if (producto != null) {
/*  332: 392 */       query.setParameter("producto", producto);
/*  333:     */     }
/*  334: 394 */     if (proyecto != null) {
/*  335: 395 */       query.setParameter("proyecto", proyecto);
/*  336:     */     }
/*  337: 399 */     return query.getResultList();
/*  338:     */   }
/*  339:     */   
/*  340:     */   public void eliminaInventarioProductoPorIdDespachoCliente(Integer idDespachoCliente)
/*  341:     */   {
/*  342: 403 */     StringBuilder sql = new StringBuilder();
/*  343: 404 */     sql.append(" DELETE FROM InventarioProducto ip ");
/*  344: 405 */     sql.append(" WHERE ip.idInventarioProducto in ");
/*  345: 406 */     sql.append("\t(SELECT sip.idInventarioProducto FROM InventarioProducto sip ");
/*  346: 407 */     sql.append("\tINNER JOIN sip.detalleDespachoCliente ddc ");
/*  347: 408 */     sql.append("\tINNER JOIN ddc.despachoCliente dc ");
/*  348: 409 */     sql.append("\tWHERE dc.idDespachoCliente=:idDespachoCliente ");
/*  349: 410 */     sql.append("\t)");
/*  350:     */     
/*  351: 412 */     Query query = this.em.createQuery(sql.toString());
/*  352: 413 */     query.setParameter("idDespachoCliente", idDespachoCliente);
/*  353: 414 */     query.executeUpdate();
/*  354:     */   }
/*  355:     */   
/*  356:     */   public void generarMovimientoInventarioInverso(DespachoCliente despachoCliente, Date fechaAnulacion)
/*  357:     */   {
/*  358: 425 */     StringBuilder sql = new StringBuilder();
/*  359: 426 */     sql.append(" SELECT sip ");
/*  360: 427 */     sql.append(" FROM InventarioProducto sip ");
/*  361: 428 */     sql.append(" INNER JOIN sip.detalleDespachoCliente ddc ");
/*  362: 429 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/*  363: 430 */     sql.append(" WHERE dc = :despachoCliente ");
/*  364:     */     
/*  365: 432 */     Query query = this.em.createQuery(sql.toString());
/*  366: 433 */     query.setParameter("despachoCliente", despachoCliente);
/*  367:     */     
/*  368: 435 */     List<InventarioProducto> listaInventarioProducto = new ArrayList();
/*  369: 436 */     listaInventarioProducto = query.getResultList();
/*  370: 437 */     generarInventarioProductoInversoDespachoCliente(fechaAnulacion, listaInventarioProducto);
/*  371:     */     
/*  372: 439 */     this.despachoClienteDao.actualizarFechaAnulacionDespachoCliente(despachoCliente, fechaAnulacion);
/*  373:     */   }
/*  374:     */   
/*  375:     */   private void generarInventarioProductoInversoDespachoCliente(Date fechaAnulacion, List<InventarioProducto> listaInventarioProducto)
/*  376:     */   {
/*  377: 443 */     for (InventarioProducto inventarioProducto : listaInventarioProducto)
/*  378:     */     {
/*  379: 444 */       detach(inventarioProducto);
/*  380: 445 */       inventarioProducto.setIdInventarioProducto(0);
/*  381: 446 */       inventarioProducto.setDetalleDespachoClienteAjuste(inventarioProducto.getDetalleDespachoCliente());
/*  382: 447 */       inventarioProducto.setDetalleDespachoCliente(null);
/*  383: 448 */       inventarioProducto.setFecha(fechaAnulacion);
/*  384: 449 */       inventarioProducto.setIndicadorAnulacion(true);
/*  385: 450 */       String numeroDocumento = inventarioProducto.getNumeroDocumento();
/*  386: 451 */       inventarioProducto.setNumeroDocumento("Anulación " + numeroDocumento);
/*  387: 452 */       int operacion = inventarioProducto.getOperacion() * -1;
/*  388: 453 */       inventarioProducto.setOperacion(operacion);
/*  389: 454 */       Calendar calFechaCreacion = Calendar.getInstance();
/*  390: 455 */       calFechaCreacion.setTime(inventarioProducto.getFechaCreacion());
/*  391: 456 */       calFechaCreacion.add(14, 10);
/*  392: 457 */       inventarioProducto.setFechaCreacion(calFechaCreacion.getTime());
/*  393: 458 */       Calendar calHoraCreacion = Calendar.getInstance();
/*  394: 459 */       calHoraCreacion.setTime(inventarioProducto.getHoraCreacion());
/*  395: 460 */       calHoraCreacion.add(14, 10);
/*  396: 461 */       inventarioProducto.setHoraCreacion(calHoraCreacion.getTime());
/*  397: 462 */       inventarioProducto.setListaSerieProducto(new ArrayList());
/*  398: 463 */       guardar(inventarioProducto);
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void generarMovimientoInventarioInverso(RecepcionProveedor recepcionProveedor, Date fechaAnulacion)
/*  403:     */   {
/*  404: 475 */     StringBuilder sql = new StringBuilder();
/*  405: 476 */     sql.append(" SELECT sip ");
/*  406: 477 */     sql.append(" FROM InventarioProducto sip ");
/*  407: 478 */     sql.append(" INNER JOIN sip.detalleRecepcionProveedor drp ");
/*  408: 479 */     sql.append(" INNER JOIN drp.recepcionProveedor rp ");
/*  409: 480 */     sql.append(" WHERE rp = :recepcionProveedor ");
/*  410:     */     
/*  411: 482 */     Query query = this.em.createQuery(sql.toString());
/*  412: 483 */     query.setParameter("recepcionProveedor", recepcionProveedor);
/*  413:     */     
/*  414: 485 */     List<InventarioProducto> listaInventarioProducto = new ArrayList();
/*  415: 486 */     listaInventarioProducto = query.getResultList();
/*  416: 487 */     generarInventarioProductoInversoRecepcionProveedor(fechaAnulacion, listaInventarioProducto);
/*  417:     */     
/*  418: 489 */     this.recepcionProveedorDao.actualizarFechaAnulacionRecepcionProveedor(recepcionProveedor, fechaAnulacion);
/*  419:     */   }
/*  420:     */   
/*  421:     */   private void generarInventarioProductoInversoRecepcionProveedor(Date fechaAnulacion, List<InventarioProducto> listaInventarioProducto)
/*  422:     */   {
/*  423: 493 */     for (InventarioProducto inventarioProducto : listaInventarioProducto)
/*  424:     */     {
/*  425: 494 */       detach(inventarioProducto);
/*  426: 495 */       inventarioProducto.setIdInventarioProducto(0);
/*  427: 496 */       inventarioProducto.setDetalleRecepcionProveedorAjuste(inventarioProducto.getDetalleRecepcionProveedor());
/*  428: 497 */       inventarioProducto.setDetalleRecepcionProveedor(null);
/*  429: 498 */       inventarioProducto.setFecha(fechaAnulacion);
/*  430: 499 */       inventarioProducto.setIndicadorAnulacion(true);
/*  431: 500 */       String numeroDocumento = inventarioProducto.getNumeroDocumento();
/*  432: 501 */       inventarioProducto.setNumeroDocumento("Anulación " + numeroDocumento);
/*  433: 502 */       int operacion = inventarioProducto.getOperacion() * -1;
/*  434: 503 */       inventarioProducto.setOperacion(operacion);
/*  435: 504 */       Calendar calFechaCreacion = Calendar.getInstance();
/*  436: 505 */       calFechaCreacion.setTime(inventarioProducto.getFechaCreacion());
/*  437: 506 */       calFechaCreacion.add(14, 10);
/*  438: 507 */       inventarioProducto.setFechaCreacion(calFechaCreacion.getTime());
/*  439: 508 */       inventarioProducto.setListaSerieProducto(new ArrayList());
/*  440: 509 */       guardar(inventarioProducto);
/*  441:     */     }
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void eliminarInventarioProductoDetalleMovimientoInventario(DetalleMovimientoInventario dmi)
/*  445:     */   {
/*  446: 514 */     if (dmi != null)
/*  447:     */     {
/*  448: 515 */       StringBuilder sql = new StringBuilder();
/*  449: 516 */       sql.append(" DELETE FROM InventarioProducto ip ");
/*  450: 517 */       sql.append(" WHERE ip.detalleMovimientoInventario = :dmi ");
/*  451:     */       
/*  452: 519 */       Query query = this.em.createQuery(sql.toString());
/*  453: 520 */       query.setParameter("dmi", dmi);
/*  454:     */       
/*  455: 522 */       query.executeUpdate();
/*  456:     */     }
/*  457:     */   }
/*  458:     */   
/*  459:     */   public void generarMovimientoInventarioInverso(MovimientoInventario movimientoInventario, DetalleMovimientoInventario dmi, Date fechaAnulacion)
/*  460:     */   {
/*  461: 534 */     StringBuilder sql = new StringBuilder();
/*  462: 535 */     sql.append("SELECT ip ");
/*  463: 536 */     sql.append(" FROM InventarioProducto ip ");
/*  464: 537 */     sql.append(" INNER JOIN ip.detalleMovimientoInventario dmi ");
/*  465: 538 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  466: 539 */     sql.append(" WHERE mi = :movimientoInventario ");
/*  467: 540 */     if (dmi != null) {
/*  468: 541 */       sql.append(" AND dmi = :dmi ");
/*  469:     */     }
/*  470: 544 */     Query query = this.em.createQuery(sql.toString());
/*  471: 545 */     query.setParameter("movimientoInventario", movimientoInventario);
/*  472: 546 */     if (dmi != null) {
/*  473: 547 */       query.setParameter("dmi", dmi);
/*  474:     */     }
/*  475: 550 */     List<InventarioProducto> listaInventarioProducto = new ArrayList();
/*  476: 551 */     listaInventarioProducto = query.getResultList();
/*  477: 552 */     generarInventarioProductoInversoMovimientoInventario(fechaAnulacion, listaInventarioProducto, movimientoInventario, dmi == null);
/*  478:     */   }
/*  479:     */   
/*  480:     */   private void generarInventarioProductoInversoMovimientoInventario(Date fechaAnulacion, List<InventarioProducto> listaInventarioProducto, MovimientoInventario movimientoInventario, boolean ajuste)
/*  481:     */   {
/*  482: 563 */     for (InventarioProducto inventarioProducto : listaInventarioProducto)
/*  483:     */     {
/*  484: 564 */       detach(inventarioProducto);
/*  485: 565 */       inventarioProducto.setIdInventarioProducto(0);
/*  486: 566 */       inventarioProducto.setDetalleMovimientoInventarioAjuste(ajuste ? inventarioProducto.getDetalleMovimientoInventario() : null);
/*  487: 567 */       inventarioProducto.setDetalleMovimientoInventario(null);
/*  488: 568 */       inventarioProducto.setFecha(fechaAnulacion);
/*  489: 569 */       inventarioProducto.setIndicadorAnulacion(true);
/*  490: 570 */       String numeroDocumento = inventarioProducto.getNumeroDocumento();
/*  491: 571 */       inventarioProducto.setNumeroDocumento("Anulación " + numeroDocumento);
/*  492: 572 */       int operacion = inventarioProducto.getOperacion() * -1;
/*  493: 573 */       inventarioProducto.setOperacion(operacion);
/*  494: 574 */       Calendar calFechaCreacion = Calendar.getInstance();
/*  495: 575 */       calFechaCreacion.setTime(inventarioProducto.getFechaCreacion());
/*  496: 576 */       calFechaCreacion.add(14, 10);
/*  497: 577 */       inventarioProducto.setFechaCreacion(calFechaCreacion.getTime());
/*  498: 578 */       inventarioProducto.setListaSerieProducto(new ArrayList());
/*  499: 579 */       guardar(inventarioProducto);
/*  500:     */     }
/*  501:     */   }
/*  502:     */   
/*  503:     */   public void generarMovimientoInventarioInverso(FacturaCliente devolucionCliente, Date fechaAnulacion)
/*  504:     */   {
/*  505: 591 */     StringBuilder sql = new StringBuilder();
/*  506: 592 */     sql.append(" SELECT sip FROM InventarioProducto sip ");
/*  507: 593 */     sql.append(" INNER JOIN sip.detalleDevolucionCliente ddc ");
/*  508: 594 */     sql.append(" INNER JOIN ddc.facturaCliente dc ");
/*  509: 595 */     sql.append(" WHERE dc = :devolucionCliente ");
/*  510:     */     
/*  511: 597 */     Query query = this.em.createQuery(sql.toString());
/*  512: 598 */     query.setParameter("devolucionCliente", devolucionCliente);
/*  513:     */     
/*  514: 600 */     List<InventarioProducto> listaInventarioProducto = new ArrayList();
/*  515: 601 */     listaInventarioProducto = query.getResultList();
/*  516: 602 */     generarInventarioProductoInversoDevolucionCliente(fechaAnulacion, listaInventarioProducto);
/*  517:     */   }
/*  518:     */   
/*  519:     */   private void generarInventarioProductoInversoDevolucionCliente(Date fechaAnulacion, List<InventarioProducto> listaInventarioProducto)
/*  520:     */   {
/*  521: 607 */     for (InventarioProducto inventarioProducto : listaInventarioProducto)
/*  522:     */     {
/*  523: 608 */       detach(inventarioProducto);
/*  524: 609 */       inventarioProducto.setInventarioProductoOrigenAnulacion(new InventarioProducto(inventarioProducto.getId()));
/*  525: 610 */       inventarioProducto.setIdInventarioProducto(0);
/*  526: 611 */       inventarioProducto.setDetalleDevolucionCliente(null);
/*  527: 612 */       inventarioProducto.setFecha(fechaAnulacion);
/*  528: 613 */       inventarioProducto.setIndicadorAnulacion(true);
/*  529: 614 */       String numeroDocumento = inventarioProducto.getNumeroDocumento();
/*  530: 615 */       inventarioProducto.setNumeroDocumento("Anulación " + numeroDocumento);
/*  531: 616 */       int operacion = inventarioProducto.getOperacion() * -1;
/*  532: 617 */       inventarioProducto.setOperacion(operacion);
/*  533: 618 */       Calendar calFechaCreacion = Calendar.getInstance();
/*  534: 619 */       calFechaCreacion.setTime(inventarioProducto.getFechaCreacion());
/*  535: 620 */       calFechaCreacion.add(14, 10);
/*  536: 621 */       inventarioProducto.setFechaCreacion(calFechaCreacion.getTime());
/*  537: 622 */       inventarioProducto.setListaSerieProducto(new ArrayList());
/*  538: 623 */       guardar(inventarioProducto);
/*  539:     */     }
/*  540:     */   }
/*  541:     */   
/*  542:     */   public void generarMovimientoInventarioInverso(FacturaProveedor devolucionProveedor, Date fechaAnulacion)
/*  543:     */   {
/*  544: 636 */     StringBuilder sql = new StringBuilder();
/*  545: 637 */     sql.append(" SELECT sip FROM InventarioProducto sip ");
/*  546: 638 */     sql.append(" INNER JOIN sip.detalleDevolucionProveedor ddp ");
/*  547: 639 */     sql.append(" INNER JOIN ddp.facturaProveedor dp ");
/*  548: 640 */     sql.append(" WHERE dp = :devolucionProveedor ");
/*  549:     */     
/*  550: 642 */     Query query = this.em.createQuery(sql.toString());
/*  551: 643 */     query.setParameter("devolucionProveedor", devolucionProveedor);
/*  552:     */     
/*  553: 645 */     List<InventarioProducto> listaInventarioProducto = new ArrayList();
/*  554: 646 */     listaInventarioProducto = query.getResultList();
/*  555: 647 */     generarInventarioProductoInversoDevolucionProveedor(fechaAnulacion, listaInventarioProducto);
/*  556:     */   }
/*  557:     */   
/*  558:     */   private void generarInventarioProductoInversoDevolucionProveedor(Date fechaAnulacion, List<InventarioProducto> listaInventarioProducto)
/*  559:     */   {
/*  560: 652 */     for (InventarioProducto inventarioProducto : listaInventarioProducto)
/*  561:     */     {
/*  562: 653 */       detach(inventarioProducto);
/*  563: 654 */       inventarioProducto.setInventarioProductoOrigenAnulacion(new InventarioProducto(inventarioProducto.getId()));
/*  564: 655 */       inventarioProducto.setIdInventarioProducto(0);
/*  565: 656 */       inventarioProducto.setDetalleDevolucionProveedor(null);
/*  566: 657 */       inventarioProducto.setFecha(fechaAnulacion);
/*  567: 658 */       inventarioProducto.setIndicadorAnulacion(true);
/*  568: 659 */       String numeroDocumento = inventarioProducto.getNumeroDocumento();
/*  569: 660 */       inventarioProducto.setNumeroDocumento("Anulación " + numeroDocumento);
/*  570: 661 */       int operacion = inventarioProducto.getOperacion() * -1;
/*  571: 662 */       inventarioProducto.setOperacion(operacion);
/*  572: 663 */       Calendar calFechaCreacion = Calendar.getInstance();
/*  573: 664 */       calFechaCreacion.setTime(inventarioProducto.getFechaCreacion());
/*  574: 665 */       calFechaCreacion.add(14, 10);
/*  575: 666 */       inventarioProducto.setFechaCreacion(calFechaCreacion.getTime());
/*  576: 667 */       inventarioProducto.setListaSerieProducto(new ArrayList());
/*  577: 668 */       guardar(inventarioProducto);
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   public List<InventarioProducto> getInventarioProductoTransformacion(int idDevolucionProveedor)
/*  582:     */   {
/*  583: 684 */     StringBuilder sql = new StringBuilder();
/*  584: 685 */     sql.append(" SELECT ip FROM InventarioProducto ip ");
/*  585: 686 */     sql.append(" WHERE ip.idInventarioProducto in");
/*  586: 687 */     sql.append(" ( SELECT it.idInventarioProducto FROM InventarioProducto sip ");
/*  587: 688 */     sql.append("   INNER JOIN sip.detalleMovimientoInventario dmi");
/*  588: 689 */     sql.append("   INNER JOIN sip.inventarioProductoTransformacion it");
/*  589: 690 */     sql.append("   INNER JOIN dmi.movimientoInventario mi");
/*  590: 691 */     sql.append("   WHERE mi.idMovimientoInventario in ");
/*  591: 692 */     sql.append("  ( SELECT ta.idMovimientoInventario FROM DetalleFacturaProveedor dfp ");
/*  592: 693 */     sql.append("   INNER JOIN dfp.transformacionAutomatica ta ");
/*  593: 694 */     sql.append("   INNER JOIN dfp.facturaProveedor fp ");
/*  594: 695 */     sql.append("   WHERE fp.idFacturaProveedor = :idDevolucionProveedor )  ");
/*  595: 696 */     sql.append(" )");
/*  596: 697 */     Query query = this.em.createQuery(sql.toString());
/*  597: 698 */     query.setParameter("idDevolucionProveedor", Integer.valueOf(idDevolucionProveedor));
/*  598:     */     
/*  599: 700 */     List<InventarioProducto> listaInventarioProducto = query.getResultList();
/*  600:     */     
/*  601:     */ 
/*  602: 703 */     sql = new StringBuilder();
/*  603: 704 */     sql.append(" SELECT ip FROM InventarioProducto ip ");
/*  604: 705 */     sql.append(" WHERE ip.idInventarioProducto in");
/*  605: 706 */     sql.append(" ( SELECT sip.idInventarioProducto FROM InventarioProducto sip ");
/*  606: 707 */     sql.append("   INNER JOIN sip.detalleMovimientoInventario dmi");
/*  607: 708 */     sql.append("   INNER JOIN dmi.movimientoInventario mi");
/*  608: 709 */     sql.append("   WHERE mi.idMovimientoInventario in ");
/*  609: 710 */     sql.append("  ( SELECT ta.idMovimientoInventario FROM DetalleFacturaProveedor dfp ");
/*  610: 711 */     sql.append("   INNER JOIN dfp.transformacionAutomatica ta ");
/*  611: 712 */     sql.append("   INNER JOIN dfp.facturaProveedor fp ");
/*  612: 713 */     sql.append("   WHERE fp.idFacturaProveedor = :idDevolucionProveedor )  ");
/*  613: 714 */     sql.append(" )");
/*  614: 715 */     query = this.em.createQuery(sql.toString());
/*  615: 716 */     query.setParameter("idDevolucionProveedor", Integer.valueOf(idDevolucionProveedor));
/*  616:     */     
/*  617: 718 */     listaInventarioProducto.addAll(query.getResultList());
/*  618:     */     
/*  619: 720 */     return listaInventarioProducto;
/*  620:     */   }
/*  621:     */   
/*  622:     */   public void generarMovimientoInventarioTransformacionInverso(FacturaProveedor devolucionProveedor, Date fechaAnulacion)
/*  623:     */   {
/*  624: 731 */     generarInventarioProductoInversoTransformacionDevolucionProveedor(fechaAnulacion, getInventarioProductoTransformacion(devolucionProveedor.getIdFacturaProveedor()));
/*  625:     */   }
/*  626:     */   
/*  627:     */   private void generarInventarioProductoInversoTransformacionDevolucionProveedor(Date fechaAnulacion, List<InventarioProducto> listaInventarioProducto)
/*  628:     */   {
/*  629: 735 */     for (InventarioProducto inventarioProducto : listaInventarioProducto)
/*  630:     */     {
/*  631: 736 */       detach(inventarioProducto);
/*  632: 737 */       inventarioProducto.setInventarioProductoOrigenAnulacion(new InventarioProducto(inventarioProducto.getId()));
/*  633: 738 */       inventarioProducto.setIdInventarioProducto(0);
/*  634: 739 */       inventarioProducto.setDetalleDevolucionProveedor(null);
/*  635: 740 */       inventarioProducto.setFecha(fechaAnulacion);
/*  636: 741 */       inventarioProducto.setIndicadorAnulacion(true);
/*  637: 742 */       String numeroDocumento = inventarioProducto.getNumeroDocumento();
/*  638: 743 */       inventarioProducto.setNumeroDocumento("Anulación " + numeroDocumento);
/*  639: 744 */       int operacion = inventarioProducto.getOperacion() * -1;
/*  640: 745 */       inventarioProducto.setOperacion(operacion);
/*  641: 746 */       Calendar calFechaCreacion = Calendar.getInstance();
/*  642: 747 */       calFechaCreacion.setTime(inventarioProducto.getFechaCreacion());
/*  643: 748 */       calFechaCreacion.add(14, 10);
/*  644: 749 */       inventarioProducto.setFechaCreacion(calFechaCreacion.getTime());
/*  645: 750 */       inventarioProducto.setListaSerieProducto(new ArrayList());
/*  646: 751 */       guardar(inventarioProducto);
/*  647:     */     }
/*  648:     */   }
/*  649:     */   
/*  650:     */   public void eliminaInventarioProductoPorIdRecepcionProveedor(Integer idRecepcionProveedor)
/*  651:     */   {
/*  652: 756 */     Query query = this.em.createQuery(" DELETE FROM InventarioProducto ip  WHERE ip.idInventarioProducto in  (SELECT sip.idInventarioProducto FROM InventarioProducto sip  INNER JOIN sip.detalleRecepcionProveedor drp  INNER JOIN drp.recepcionProveedor rp  WHERE rp.idRecepcionProveedor=:idRecepcionProveedor) ");
/*  653:     */     
/*  654:     */ 
/*  655: 759 */     query.setParameter("idRecepcionProveedor", idRecepcionProveedor);
/*  656: 760 */     query.executeUpdate();
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void eliminaInventarioProductoPorIdMovimientoInventario(Integer idMovimientoInventario)
/*  660:     */   {
/*  661: 766 */     Query query1 = this.em.createQuery(" SELECT ip.inventarioProductoTransferencia.idInventarioProducto FROM InventarioProducto ip  INNER JOIN ip.detalleMovimientoInventario dmi  INNER JOIN dmi.movimientoInventario mi  WHERE mi.idMovimientoInventario=:idMovimientoInventario  AND ip.inventarioProductoTransferencia.idInventarioProducto IS NOT NULL ");
/*  662:     */     
/*  663:     */ 
/*  664:     */ 
/*  665: 770 */     query1.setParameter("idMovimientoInventario", idMovimientoInventario);
/*  666: 771 */     List<Integer> idsInventarioProducto = query1.getResultList();
/*  667:     */     
/*  668: 773 */     Query query = this.em.createQuery(" DELETE FROM InventarioProducto ip  WHERE ip.idInventarioProducto in  (SELECT sip.idInventarioProducto FROM InventarioProducto sip  INNER JOIN sip.detalleMovimientoInventario dmi  INNER JOIN dmi.movimientoInventario mi  WHERE mi.idMovimientoInventario=:idMovimientoInventario) ");
/*  669:     */     
/*  670:     */ 
/*  671: 776 */     query.setParameter("idMovimientoInventario", idMovimientoInventario);
/*  672: 777 */     query.executeUpdate();
/*  673: 779 */     if (idsInventarioProducto.size() != 0)
/*  674:     */     {
/*  675: 780 */       Query query2 = this.em.createQuery(" DELETE FROM InventarioProducto ip  WHERE ip.idInventarioProducto in (:idsInventarioProducto) ");
/*  676: 781 */       query2.setParameter("idsInventarioProducto", idsInventarioProducto);
/*  677: 782 */       query2.executeUpdate();
/*  678:     */     }
/*  679:     */   }
/*  680:     */   
/*  681:     */   public void eliminaInventarioProductoConIdAjusteTransferencia(Integer idMovimientoInventario)
/*  682:     */   {
/*  683: 788 */     Query query = this.em.createQuery(" DELETE FROM InventarioProducto ip  WHERE ip.InventarioProductoTransferencia idInventarioProducto in  (SELECT sip.idInventarioProducto FROM InventarioProducto sip  INNER JOIN sip.detalleMovimientoInventario dmi  INNER JOIN dmi.movimientoInventario mi  WHERE mi.idMovimientoInventario=:idMovimientoInventario AND  sip.idInventarioProducto IS NOT NULL) ");
/*  684:     */     
/*  685:     */ 
/*  686:     */ 
/*  687: 792 */     query.setParameter("idMovimientoInventario", idMovimientoInventario);
/*  688: 793 */     query.executeUpdate();
/*  689:     */   }
/*  690:     */   
/*  691:     */   public List<Producto> obtenerProductosConMovimiento(int idOrganizacion, Date fechaDesde, Date fechaHasta, Bodega bodega)
/*  692:     */   {
/*  693: 806 */     StringBuilder sql = new StringBuilder();
/*  694: 807 */     sql.append(" SELECT NEW Producto(d.producto.idProducto, d.producto.codigo, d.producto.versionCosteo) ");
/*  695: 808 */     sql.append(" FROM InventarioProducto d ");
/*  696: 809 */     sql.append(" WHERE idOrganizacion=:idOrganizacion ");
/*  697: 810 */     sql.append(" AND d.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  698: 811 */     if (bodega != null) {
/*  699: 812 */       sql.append(" AND d.bodega = :bodega ");
/*  700:     */     }
/*  701: 814 */     sql.append(" GROUP BY d.producto.idProducto, d.producto.codigo, d.producto.versionCosteo ");
/*  702: 815 */     sql.append(" ORDER BY d.producto.idProducto");
/*  703: 816 */     Query query = this.em.createQuery(sql.toString());
/*  704:     */     
/*  705: 818 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  706: 819 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  707: 820 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  708: 821 */     if (bodega != null) {
/*  709: 822 */       query.setParameter("bodega", bodega);
/*  710:     */     }
/*  711: 824 */     return query.getResultList();
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void actualizarCostoInicial(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  715:     */   {
/*  716: 834 */     Query query = this.em.createQuery("SELECT COUNT(*) FROM CostoProducto WHERE idOrganizacion=:idOrganizacion AND indicadorSaldoInicial=true");
/*  717: 835 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  718: 837 */     if (((Long)query.getSingleResult()).longValue() == 0L)
/*  719:     */     {
/*  720: 840 */       Query qSaldos = this.em.createQuery("SELECT NEW CostoProducto (ip.idOrganizacion,ip.producto,ip.fecha,SUM(ip.cantidad),SUM(ip.costo)) FROM InventarioProducto ip INNER JOIN ip.detalleMovimientoInventario dmi INNER JOIN dmi.movimientoInventario mi WHERE ip.idOrganizacion=:idOrganizacion AND mi.indicadorSaldoInicial=true AND mi.estado!=:estadoAnulado GROUP BY ip.idOrganizacion,ip.producto,ip.fecha");
/*  721:     */       
/*  722:     */ 
/*  723:     */ 
/*  724: 844 */       qSaldos.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  725: 845 */       qSaldos.setParameter("estadoAnulado", Estado.ANULADO);
/*  726:     */       
/*  727:     */ 
/*  728: 848 */       List<CostoProducto> listaSaldos = qSaldos.getResultList();
/*  729: 850 */       for (CostoProducto costoProducto : listaSaldos)
/*  730:     */       {
/*  731: 851 */         costoProducto.setIndicadorSaldoInicial(true);
/*  732: 852 */         this.costoProductoDao.guardar(costoProducto);
/*  733:     */       }
/*  734:     */     }
/*  735:     */   }
/*  736:     */   
/*  737:     */   public InventarioProducto cargarDetalle(int idInventarioProducto)
/*  738:     */   {
/*  739: 864 */     InventarioProducto inventarioProducto = (InventarioProducto)buscarPorId(Integer.valueOf(idInventarioProducto));
/*  740: 865 */     inventarioProducto.getId();
/*  741: 866 */     if (inventarioProducto.getBodega() != null) {
/*  742: 867 */       inventarioProducto.getBodega().getId();
/*  743:     */     }
/*  744: 869 */     inventarioProducto.getProducto().getId();
/*  745: 871 */     if (inventarioProducto.getDetalleMovimientoInventario() != null) {
/*  746: 872 */       inventarioProducto.getDetalleMovimientoInventario().getId();
/*  747:     */     }
/*  748: 875 */     if (inventarioProducto.getDetalleRecepcionProveedor() != null) {
/*  749: 876 */       inventarioProducto.getDetalleRecepcionProveedor().getId();
/*  750:     */     }
/*  751: 879 */     if (inventarioProducto.getDetalleDespachoCliente() != null) {
/*  752: 880 */       inventarioProducto.getDetalleDespachoCliente().getId();
/*  753:     */     }
/*  754: 883 */     return inventarioProducto;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public void eliminaInventarioProductoPorIdDevolucionCliente(Integer idDevolucionCliente)
/*  758:     */   {
/*  759: 887 */     StringBuilder sql = new StringBuilder();
/*  760: 888 */     sql.append(" DELETE FROM InventarioProducto ip ");
/*  761: 889 */     sql.append(" WHERE ip.idInventarioProducto in");
/*  762: 890 */     sql.append(" ( SELECT sip.idInventarioProducto FROM InventarioProducto sip ");
/*  763: 891 */     sql.append("   INNER JOIN sip.detalleDevolucionCliente ddc");
/*  764: 892 */     sql.append("   INNER JOIN ddc.facturaCliente dc");
/*  765: 893 */     sql.append("   WHERE dc.idFacturaCliente=:idDevolucionCliente ");
/*  766: 894 */     sql.append(" )");
/*  767: 895 */     Query query = this.em.createQuery(sql.toString());
/*  768: 896 */     query.setParameter("idDevolucionCliente", idDevolucionCliente);
/*  769: 897 */     query.executeUpdate();
/*  770:     */   }
/*  771:     */   
/*  772:     */   public void eliminaInventarioProductoPorIdDevolucionProveedor(Integer idDevolucionProveedor)
/*  773:     */   {
/*  774: 901 */     StringBuilder sql = new StringBuilder();
/*  775: 902 */     sql.append(" DELETE FROM InventarioProducto ip ");
/*  776: 903 */     sql.append(" WHERE ip.idInventarioProducto in");
/*  777: 904 */     sql.append(" ( SELECT sip.idInventarioProducto FROM InventarioProducto sip ");
/*  778: 905 */     sql.append("   INNER JOIN sip.detalleDevolucionProveedor ddp");
/*  779: 906 */     sql.append("   INNER JOIN ddp.facturaProveedor dp");
/*  780: 907 */     sql.append("   WHERE dp.idFacturaProveedor=:idDevolucionCliente ");
/*  781: 908 */     sql.append(" )");
/*  782: 909 */     Query query = this.em.createQuery(sql.toString());
/*  783: 910 */     query.setParameter("idDevolucionCliente", idDevolucionProveedor);
/*  784: 911 */     query.executeUpdate();
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void eliminaInventarioProductoTransformacionPorIdDevolucionProveedor(Integer idDevolucionProveedor)
/*  788:     */   {
/*  789: 916 */     List<InventarioProducto> lista = getInventarioProductoTransformacion(idDevolucionProveedor.intValue());
/*  790: 918 */     if ((lista != null) && (!lista.isEmpty()))
/*  791:     */     {
/*  792: 921 */       StringBuilder sql = new StringBuilder();
/*  793: 922 */       sql.append(" DELETE FROM InventarioProducto ip ");
/*  794: 923 */       sql.append(" WHERE ip in ( :lista ) ");
/*  795:     */       
/*  796: 925 */       Query query = this.em.createQuery(sql.toString());
/*  797: 926 */       query.setParameter("lista", lista);
/*  798: 927 */       query.executeUpdate();
/*  799:     */     }
/*  800:     */   }
/*  801:     */   
/*  802:     */   public void guardar(InventarioProducto inventarioProducto)
/*  803:     */   {
/*  804: 934 */     int orden = 20;
/*  805: 936 */     if (inventarioProducto.getOperacion() == 1) {
/*  806: 937 */       if (inventarioProducto.isIndicadorGeneraCosto()) {
/*  807: 938 */         orden = 0;
/*  808:     */       } else {
/*  809: 940 */         orden = 10;
/*  810:     */       }
/*  811:     */     }
/*  812: 945 */     if ((inventarioProducto.isIndicadorTransferencia()) && (ParametrosSistema.isCosteoPorBodega(inventarioProducto.getIdOrganizacion()).booleanValue())) {
/*  813: 946 */       orden = inventarioProducto.getOperacion() == 1 ? 0 : 30;
/*  814: 947 */     } else if ((inventarioProducto.isIndicadorAnulacion()) && (inventarioProducto.getDocumento() != null) && 
/*  815: 948 */       (inventarioProducto.getDocumento().getDocumentoBase().equals(DocumentoBase.DEVOLUCION_PROVEEDOR))) {
/*  816: 949 */       orden = 21;
/*  817: 950 */     } else if (inventarioProducto.isIndicadorIngresoDespuesEgreso()) {
/*  818: 952 */       orden = 31;
/*  819:     */     }
/*  820: 955 */     inventarioProducto.setOrden(orden);
/*  821: 956 */     if (inventarioProducto.getDetalleRecepcionProveedorAjuste() != null)
/*  822:     */     {
/*  823: 957 */       inventarioProducto.setHoraCreacion(inventarioProducto.getDetalleRecepcionProveedorAjuste().getInventarioProducto().getHoraCreacion());
/*  824:     */     }
/*  825: 958 */     else if (inventarioProducto.isIndicadorAnulacion())
/*  826:     */     {
/*  827: 960 */       Calendar cal = Calendar.getInstance();
/*  828: 961 */       cal.setTime(inventarioProducto.getHoraCreacion());
/*  829: 962 */       cal.add(13, 1);
/*  830: 963 */       inventarioProducto.setHoraCreacion(cal.getTime());
/*  831:     */     }
/*  832: 964 */     else if ((inventarioProducto.getId() == 0) && (!inventarioProducto.isIndicadorAnulacion()))
/*  833:     */     {
/*  834: 965 */       inventarioProducto.setHoraCreacion(Calendar.getInstance().getTime());
/*  835:     */     }
/*  836: 967 */     super.guardar(inventarioProducto);
/*  837:     */   }
/*  838:     */   
/*  839:     */   public BigDecimal obtenerCostoTransformacion(InventarioProducto inventarioProducto)
/*  840:     */   {
/*  841: 977 */     this.em.flush();
/*  842: 978 */     StringBuilder sql = new StringBuilder();
/*  843: 979 */     sql.append(" SELECT SUM(ipt.costo) ");
/*  844: 980 */     sql.append(" FROM InventarioProducto ipt ");
/*  845: 981 */     sql.append(" WHERE ipt.inventarioProductoTransformacion = :inventarioProducto ");
/*  846:     */     
/*  847: 983 */     Query query = this.em.createQuery(sql.toString());
/*  848: 984 */     query.setParameter("inventarioProducto", inventarioProducto);
/*  849:     */     
/*  850: 986 */     BigDecimal costo = (BigDecimal)query.getSingleResult();
/*  851: 987 */     if (costo == null) {
/*  852: 988 */       costo = BigDecimal.ZERO;
/*  853:     */     }
/*  854: 990 */     return costo;
/*  855:     */   }
/*  856:     */   
/*  857:     */   public List<Producto> obtenerMaterialesTransformacion(InventarioProducto inventarioProducto)
/*  858:     */   {
/*  859: 996 */     this.em.flush();
/*  860: 997 */     StringBuilder sql = new StringBuilder();
/*  861: 998 */     sql.append(" SELECT DISTINCT (ipt.producto) ");
/*  862: 999 */     sql.append(" FROM InventarioProducto ipt ");
/*  863:1000 */     sql.append(" WHERE ipt.inventarioProductoTransformacion = :inventarioProducto ");
/*  864:     */     
/*  865:1002 */     Query query = this.em.createQuery(sql.toString());
/*  866:1003 */     query.setParameter("inventarioProducto", inventarioProducto);
/*  867:     */     
/*  868:1005 */     return query.getResultList();
/*  869:     */   }
/*  870:     */   
/*  871:     */   public List obtenerSaldosPorBodegaProyecto(Bodega bodega, DimensionContable proyecto, Date fecha)
/*  872:     */   {
/*  873:1010 */     StringBuilder sql = new StringBuilder();
/*  874:1011 */     sql.append(" SELECT bo.idBodega, proy.idDimensionContable, SUM(ipt.operacion * ipt.cantidad), prod.idProducto, lot.idLote ");
/*  875:1012 */     sql.append(" FROM InventarioProducto ipt ");
/*  876:1013 */     sql.append(" LEFT JOIN ipt.producto prod ");
/*  877:1014 */     sql.append(" LEFT JOIN ipt.lote lot ");
/*  878:1015 */     sql.append(" LEFT JOIN ipt.bodega bo ");
/*  879:1016 */     sql.append(" LEFT JOIN ipt.proyecto proy ");
/*  880:1017 */     sql.append(" WHERE fecha <= :fecha ");
/*  881:1019 */     if (bodega != null) {
/*  882:1020 */       sql.append(" AND bo = :bodega ");
/*  883:     */     }
/*  884:1022 */     if (proyecto != null) {
/*  885:1023 */       sql.append(" AND proy = :proyecto ");
/*  886:     */     }
/*  887:1025 */     sql.append(" GROUP BY bo.idBodega, proy.idDimensionContable, prod.idProducto, lot.idLote ");
/*  888:1026 */     sql.append(" HAVING SUM(ipt.operacion * ipt.cantidad) > 0 ");
/*  889:     */     
/*  890:1028 */     Query query = this.em.createQuery(sql.toString());
/*  891:1029 */     if (bodega != null) {
/*  892:1030 */       query.setParameter("bodega", bodega);
/*  893:     */     }
/*  894:1032 */     if (proyecto != null) {
/*  895:1033 */       query.setParameter("proyecto", proyecto);
/*  896:     */     }
/*  897:1036 */     query.setParameter("fecha", fecha);
/*  898:1037 */     return query.getResultList();
/*  899:     */   }
/*  900:     */   
/*  901:     */   public List<InventarioProducto> buscarInventarioProductoByOrdenFabricacion(OrdenFabricacion ordenFabricacion, Date fechaDesde, Date fechaHasta)
/*  902:     */   {
/*  903:1042 */     this.em.flush();
/*  904:1043 */     StringBuilder sql = new StringBuilder();
/*  905:1044 */     sql.append(" SELECT ip ");
/*  906:1045 */     sql.append(" FROM InventarioProducto ip ");
/*  907:1046 */     sql.append(" INNER JOIN ip.detalleMovimientoInventario dmi ");
/*  908:1047 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  909:1048 */     sql.append(" INNER JOIN mi.ordenFabricacion ofa ");
/*  910:1049 */     sql.append(" INNER JOIN ip.documento doc ");
/*  911:1050 */     sql.append(" WHERE ofa.idOrdenFabricacion =:idOrdenFabricacion ");
/*  912:1051 */     sql.append(" AND doc.documentoBase = :documentoIngresoProduccion ");
/*  913:1052 */     sql.append(" AND doc.documentoBase = :documentoIngresoProduccion ");
/*  914:1053 */     if (fechaDesde != null) {
/*  915:1054 */       sql.append(" AND mi.fecha >= :fechaDesde ");
/*  916:     */     }
/*  917:1056 */     if (fechaHasta != null) {
/*  918:1057 */       sql.append(" AND mi.fecha <= :fechaHasta ");
/*  919:     */     }
/*  920:1059 */     sql.append(" AND dmi.indicadorRecibido = true ");
/*  921:1060 */     sql.append(" AND mi.estado != :estadoAnulado ");
/*  922:     */     
/*  923:1062 */     Query query = this.em.createQuery(sql.toString());
/*  924:1063 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  925:1064 */     query.setParameter("documentoIngresoProduccion", DocumentoBase.INGRESO_PRODUCCION);
/*  926:1065 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  927:1066 */     if (fechaDesde != null) {
/*  928:1067 */       query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  929:     */     }
/*  930:1069 */     if (fechaHasta != null) {
/*  931:1070 */       query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  932:     */     }
/*  933:1073 */     return query.getResultList();
/*  934:     */   }
/*  935:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.InventarioProductoDao
 * JD-Core Version:    0.7.0.1
 */