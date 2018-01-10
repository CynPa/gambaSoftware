/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*    4:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    5:     */ import com.asinfo.as2.entities.AnticipoCliente;
/*    6:     */ import com.asinfo.as2.entities.AnticipoProveedor;
/*    7:     */ import com.asinfo.as2.entities.Asiento;
/*    8:     */ import com.asinfo.as2.entities.Bodega;
/*    9:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*   10:     */ import com.asinfo.as2.entities.CuentaContable;
/*   11:     */ import com.asinfo.as2.entities.DetalleAsiento;
/*   12:     */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   13:     */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   14:     */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   15:     */ import com.asinfo.as2.entities.Documento;
/*   16:     */ import com.asinfo.as2.entities.GuiaRemision;
/*   17:     */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   18:     */ import com.asinfo.as2.entities.InventarioProducto;
/*   19:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*   20:     */ import com.asinfo.as2.entities.Lote;
/*   21:     */ import com.asinfo.as2.entities.MovimientoInventario;
/*   22:     */ import com.asinfo.as2.entities.Producto;
/*   23:     */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*   24:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   25:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   26:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   27:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   28:     */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   29:     */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   30:     */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*   31:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   32:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   33:     */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   34:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   35:     */ import java.math.BigDecimal;
/*   36:     */ import java.math.RoundingMode;
/*   37:     */ import java.util.ArrayList;
/*   38:     */ import java.util.Date;
/*   39:     */ import java.util.HashMap;
/*   40:     */ import java.util.List;
/*   41:     */ import java.util.Map;
/*   42:     */ import javax.ejb.EJB;
/*   43:     */ import javax.ejb.Stateless;
/*   44:     */ import javax.persistence.EntityManager;
/*   45:     */ import javax.persistence.Query;
/*   46:     */ import javax.persistence.TemporalType;
/*   47:     */ import javax.persistence.TypedQuery;
/*   48:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   49:     */ import javax.persistence.criteria.CriteriaQuery;
/*   50:     */ import javax.persistence.criteria.Expression;
/*   51:     */ import javax.persistence.criteria.Fetch;
/*   52:     */ import javax.persistence.criteria.Join;
/*   53:     */ import javax.persistence.criteria.JoinType;
/*   54:     */ import javax.persistence.criteria.Order;
/*   55:     */ import javax.persistence.criteria.Path;
/*   56:     */ import javax.persistence.criteria.Predicate;
/*   57:     */ import javax.persistence.criteria.Root;
/*   58:     */ 
/*   59:     */ @Stateless
/*   60:     */ public class MovimientoInventarioDao
/*   61:     */   extends AbstractDaoAS2<MovimientoInventario>
/*   62:     */ {
/*   63:     */   @EJB
/*   64:     */   private SerieInventarioProductoDao serieInventarioProductoDao;
/*   65:     */   @EJB
/*   66:     */   private ServicioGenerico<InventarioProducto> servicioInventarioProducto;
/*   67:     */   @EJB
/*   68:     */   private ServicioGenerico<DetalleMovimientoInventario> servicioDetalleMovimientoInventario;
/*   69:     */   @EJB
/*   70:     */   private ServicioGenerico<DetalleAsiento> servicioDetalleAsiento;
/*   71:     */   @EJB
/*   72:     */   private ServicioGenerico<Asiento> servicioAsiento;
/*   73:     */   
/*   74:     */   public MovimientoInventarioDao()
/*   75:     */   {
/*   76:  87 */     super(MovimientoInventario.class);
/*   77:     */   }
/*   78:     */   
/*   79:     */   public MovimientoInventario cargarDetalle(int idMovimientoInventario)
/*   80:     */   {
/*   81:  97 */     return cargarDetalle(idMovimientoInventario, null);
/*   82:     */   }
/*   83:     */   
/*   84:     */   public MovimientoInventario cargarDetalle(int idMovimientoInventario, Producto productoDetalle)
/*   85:     */   {
/*   86: 103 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   87: 104 */     CriteriaQuery<MovimientoInventario> cqCabecera = criteriaBuilder.createQuery(MovimientoInventario.class);
/*   88: 105 */     Root<MovimientoInventario> fromCabecera = cqCabecera.from(MovimientoInventario.class);
/*   89: 106 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*   90: 107 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*   91: 108 */     documento.fetch("secuencia", JoinType.LEFT);
/*   92:     */     
/*   93: 110 */     fromCabecera.fetch("motivoAjusteInventario", JoinType.LEFT);
/*   94: 111 */     fromCabecera.fetch("movimientoInventarioPadre", JoinType.LEFT);
/*   95: 112 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*   96: 113 */     fromCabecera.fetch("responsableSalidaMercaderia", JoinType.LEFT);
/*   97: 114 */     fromCabecera.fetch("asiento", JoinType.LEFT);
/*   98: 115 */     fromCabecera.fetch("ordenFabricacion", JoinType.LEFT);
/*   99: 116 */     fromCabecera.fetch("bodegaOrigen", JoinType.LEFT);
/*  100: 117 */     fromCabecera.fetch("bodegaDestino", JoinType.LEFT);
/*  101: 118 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  102:     */     
/*  103: 120 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idMovimientoInventario"), Integer.valueOf(idMovimientoInventario)));
/*  104: 121 */     CriteriaQuery<MovimientoInventario> select = cqCabecera.select(fromCabecera);
/*  105:     */     
/*  106: 123 */     MovimientoInventario movimientoInventario = (MovimientoInventario)this.em.createQuery(select).getSingleResult();
/*  107: 124 */     this.em.detach(movimientoInventario);
/*  108:     */     
/*  109:     */ 
/*  110: 127 */     CriteriaQuery<DetalleMovimientoInventario> cqDetalle = criteriaBuilder.createQuery(DetalleMovimientoInventario.class);
/*  111: 128 */     Root<DetalleMovimientoInventario> fromDetalle = cqDetalle.from(DetalleMovimientoInventario.class);
/*  112: 129 */     fromDetalle.fetch("bodegaOrigen", JoinType.LEFT);
/*  113: 130 */     fromDetalle.fetch("materialOrdenTrabajoMantenimiento", JoinType.LEFT);
/*  114: 131 */     fromDetalle.fetch("transformacionAutomatica", JoinType.LEFT);
/*  115:     */     
/*  116: 133 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  117: 134 */     producto.fetch("unidad", JoinType.LEFT);
/*  118: 135 */     producto.fetch("unidadCompra", JoinType.LEFT);
/*  119: 136 */     producto.fetch("unidadVenta", JoinType.LEFT);
/*  120: 137 */     producto.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  121: 138 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  122:     */     
/*  123: 140 */     Fetch<Object, Object> inventarioProducto = fromDetalle.fetch("inventarioProducto", JoinType.LEFT);
/*  124: 141 */     inventarioProducto.fetch("lote", JoinType.LEFT);
/*  125:     */     
/*  126: 143 */     Fetch<Object, Object> detalleOSM = fromDetalle.fetch("detalleOrdenSalidaMaterial", JoinType.LEFT);
/*  127: 144 */     detalleOSM.fetch("ordenSalidaMaterial", JoinType.LEFT);
/*  128: 145 */     fromDetalle.fetch("unidadConversion", JoinType.LEFT);
/*  129: 146 */     fromDetalle.fetch("lote", JoinType.LEFT);
/*  130: 148 */     if (movimientoInventario.getDocumento().getDocumentoBase() == DocumentoBase.CONSUMO_BODEGA)
/*  131:     */     {
/*  132: 149 */       fromDetalle.fetch("destinoCosto", JoinType.LEFT);
/*  133:     */     }
/*  134: 150 */     else if (movimientoInventario.getDocumento().getDocumentoBase() == DocumentoBase.TRANSFERENCIA_BODEGA)
/*  135:     */     {
/*  136: 151 */       fromDetalle.fetch("bodegaDestino", JoinType.LEFT);
/*  137: 152 */       inventarioProducto.fetch("inventarioProductoTransferencia", JoinType.LEFT);
/*  138:     */     }
/*  139: 155 */     cqDetalle.where(criteriaBuilder.equal(fromDetalle.get("movimientoInventario"), movimientoInventario));
/*  140: 156 */     if (productoDetalle != null) {
/*  141: 157 */       cqDetalle.where(criteriaBuilder.and(criteriaBuilder.equal(fromDetalle.get("movimientoInventario"), movimientoInventario), criteriaBuilder
/*  142: 158 */         .equal(fromDetalle.get("producto"), productoDetalle)));
/*  143:     */     }
/*  144: 160 */     CriteriaQuery<DetalleMovimientoInventario> selectDetalle = cqDetalle.select(fromDetalle);
/*  145: 161 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idDetalleMovimientoInventario")) });
/*  146:     */     
/*  147: 163 */     List<DetalleMovimientoInventario> listaDetalleMovimientosInventario = this.em.createQuery(selectDetalle).getResultList();
/*  148: 164 */     movimientoInventario.setDetalleMovimientosInventario(listaDetalleMovimientosInventario);
/*  149: 166 */     for (DetalleMovimientoInventario detalleMovimientoInventario : listaDetalleMovimientosInventario)
/*  150:     */     {
/*  151: 167 */       this.em.detach(detalleMovimientoInventario);
/*  152:     */       
/*  153: 169 */       detalleMovimientoInventario.setMovimientoInventario(movimientoInventario);
/*  154: 171 */       if (detalleMovimientoInventario.getInventarioProducto() != null)
/*  155:     */       {
/*  156: 173 */         detalleMovimientoInventario.setTraCantidadCoversion(detalleMovimientoInventario.getInventarioProducto().getCantidad());
/*  157: 175 */         if (detalleMovimientoInventario.getProducto().getIndicadorSerie().booleanValue())
/*  158:     */         {
/*  159: 177 */           this.em.detach(detalleMovimientoInventario.getInventarioProducto());
/*  160: 178 */           this.serieInventarioProductoDao.cargarDetalle(detalleMovimientoInventario.getInventarioProducto());
/*  161:     */         }
/*  162:     */         else
/*  163:     */         {
/*  164: 180 */           detalleMovimientoInventario.getInventarioProducto().setListaSerieProducto(new ArrayList());
/*  165:     */         }
/*  166:     */       }
/*  167: 185 */       CriteriaQuery<LecturaBalanza> cqLecturaBalanza = criteriaBuilder.createQuery(LecturaBalanza.class);
/*  168: 186 */       Root<LecturaBalanza> fromLecturaBalanza = cqLecturaBalanza.from(LecturaBalanza.class);
/*  169: 187 */       fromLecturaBalanza.fetch("producto", JoinType.INNER);
/*  170: 188 */       fromLecturaBalanza.fetch("unidadManejo", JoinType.LEFT);
/*  171: 189 */       fromLecturaBalanza.fetch("pallet", JoinType.LEFT);
/*  172: 190 */       Fetch<Object, Object> dmi = fromLecturaBalanza.fetch("detalleMovimientoInventario", JoinType.INNER);
/*  173: 191 */       dmi.fetch("producto", JoinType.LEFT);
/*  174: 192 */       dmi.fetch("bodegaOrigen", JoinType.LEFT);
/*  175: 193 */       dmi.fetch("bodegaDestino", JoinType.LEFT);
/*  176:     */       
/*  177: 195 */       cqLecturaBalanza.where(criteriaBuilder.equal(fromLecturaBalanza.join("detalleMovimientoInventario"), detalleMovimientoInventario));
/*  178: 196 */       CriteriaQuery<LecturaBalanza> selectLecturaBalanza = cqLecturaBalanza.select(fromLecturaBalanza);
/*  179:     */       
/*  180: 198 */       List<LecturaBalanza> listaLecturaBalanza = this.em.createQuery(selectLecturaBalanza).getResultList();
/*  181: 199 */       detalleMovimientoInventario.setListaLecturaBalanza(listaLecturaBalanza);
/*  182:     */     }
/*  183: 288 */     if (movimientoInventario.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE)
/*  184:     */     {
/*  185: 290 */       String sql = "from AnticipoCliente as a left join fetch a.asiento where a.devolucionCliente.idMovimientoInventario = :idMovimientoInventario";
/*  186:     */       
/*  187:     */ 
/*  188: 293 */       Query query = this.em.createQuery(sql).setParameter("idMovimientoInventario", Integer.valueOf(movimientoInventario.getIdMovimientoInventario()));
/*  189: 294 */       movimientoInventario.setTraAnticipoCliente((AnticipoCliente)query.getSingleResult());
/*  190:     */     }
/*  191: 296 */     else if (movimientoInventario.getDocumento().getDocumentoBase() == DocumentoBase.DEVOLUCION_PROVEEDOR)
/*  192:     */     {
/*  193: 298 */       String sql = "from AnticipoProveedor as a left join fetch a.asiento where a.devolucionProveedor.idMovimientoInventario = :idMovimientoInventario";
/*  194:     */       
/*  195:     */ 
/*  196: 301 */       Query query = this.em.createQuery(sql).setParameter("idMovimientoInventario", Integer.valueOf(movimientoInventario.getIdMovimientoInventario()));
/*  197: 302 */       movimientoInventario.setTraAnticipoProveedor((AnticipoProveedor)query.getSingleResult());
/*  198:     */     }
/*  199: 305 */     return movimientoInventario;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public List<MovimientoInventario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  203:     */   {
/*  204: 317 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  205: 318 */     CriteriaQuery<MovimientoInventario> criteriaQuery = criteriaBuilder.createQuery(MovimientoInventario.class);
/*  206: 319 */     Root<MovimientoInventario> from = criteriaQuery.from(MovimientoInventario.class);
/*  207:     */     
/*  208: 321 */     from.fetch("motivoAjusteInventario", JoinType.LEFT);
/*  209: 322 */     from.fetch("movimientoInventarioPadre", JoinType.LEFT);
/*  210: 323 */     from.fetch("documento", JoinType.LEFT);
/*  211: 324 */     from.fetch("documentoDestino", JoinType.LEFT);
/*  212: 325 */     from.fetch("bodegaOrigen", JoinType.LEFT);
/*  213: 326 */     from.fetch("bodegaDestino", JoinType.LEFT);
/*  214: 327 */     from.fetch("guiaRemision", JoinType.LEFT);
/*  215: 328 */     from.fetch("proyecto", JoinType.LEFT);
/*  216: 329 */     from.fetch("ordenFabricacion", JoinType.LEFT).fetch("producto", JoinType.LEFT);
/*  217: 330 */     from.fetch("productoTerminadoTransformacion", JoinType.LEFT);
/*  218: 331 */     from.fetch("ordenTrabajoMantenimiento", JoinType.LEFT);
/*  219: 332 */     from.fetch("responsableSalidaMercaderia", JoinType.LEFT);
/*  220: 333 */     from.fetch("sucursal", JoinType.LEFT);
/*  221:     */     
/*  222: 335 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  223: 336 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  224:     */     
/*  225: 338 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  226: 339 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  227:     */     
/*  228: 341 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  229:     */     
/*  230: 343 */     CriteriaQuery<MovimientoInventario> select = criteriaQuery.select(from);
/*  231:     */     
/*  232: 345 */     TypedQuery<MovimientoInventario> typedQuery = this.em.createQuery(select);
/*  233: 346 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  234:     */     
/*  235: 348 */     List<MovimientoInventario> listaMovimientoInventario = typedQuery.getResultList();
/*  236: 349 */     for (MovimientoInventario movimientoInventario : listaMovimientoInventario)
/*  237:     */     {
/*  238: 351 */       CriteriaQuery<MovimientoInventario> cqAjustes = criteriaBuilder.createQuery(MovimientoInventario.class);
/*  239: 352 */       Root<MovimientoInventario> fromAjustes = cqAjustes.from(MovimientoInventario.class);
/*  240:     */       
/*  241: 354 */       List<Expression<?>> expresionesAjustes = new ArrayList();
/*  242: 355 */       expresionesAjustes.add(criteriaBuilder.equal(fromAjustes.join("transferenciaAjuste"), movimientoInventario));
/*  243: 356 */       cqAjustes.where((Predicate[])expresionesAjustes.toArray(new Predicate[expresionesAjustes.size()]));
/*  244: 357 */       CriteriaQuery<MovimientoInventario> selectAjustes = cqAjustes.select(fromAjustes);
/*  245:     */       
/*  246: 359 */       List<MovimientoInventario> listaAjustes = this.em.createQuery(selectAjustes).getResultList();
/*  247:     */       
/*  248: 361 */       movimientoInventario.setListaAjustesTransferencia(listaAjustes);
/*  249:     */     }
/*  250: 364 */     return listaMovimientoInventario;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public List<DetalleFacturaCliente> getDetalleFacturaDevolucionCliente(int idFacturaCliente, int idDevolucionCliente)
/*  254:     */   {
/*  255: 376 */     String sql = "SELECT new DetalleFacturaCliente (dfc.idDetalleFacturaCliente,dfc.producto,dfc.cantidad,dfc.precio,  dfc.cantidad - SUM(CASE WHEN dmi.movimientoInventario.idMovimientoInventario NOT IN (:idDevolucionCliente) THEN dmi.cantidad ELSE 0 END),  dfc.descuento, dmi.cantidad)  FROM  DetalleMovimientoInventario dmi RIGHT OUTER JOIN dmi.detalleFacturaCliente dfc INNER JOIN dfc.facturaCliente fc  WHERE fc.idFacturaCliente = :idFacturaCliente  GROUP BY dfc.idDetalleFacturaCliente,dfc.producto,dfc.cantidad,dfc.precio,dfc.descuento,dmi.cantidad ";
/*  256:     */     
/*  257:     */ 
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261:     */ 
/*  262: 383 */     Query query = this.em.createQuery(sql).setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
/*  263:     */     
/*  264: 385 */     query.setParameter("idDevolucionCliente", Integer.valueOf(idDevolucionCliente));
/*  265:     */     
/*  266: 387 */     List<DetalleFacturaCliente> detalleFacturaClientes = query.getResultList();
/*  267: 388 */     for (DetalleFacturaCliente detalleFacturaCliente : detalleFacturaClientes) {
/*  268: 389 */       detalleFacturaCliente.getProducto().getId();
/*  269:     */     }
/*  270: 391 */     return detalleFacturaClientes;
/*  271:     */   }
/*  272:     */   
/*  273:     */   public List<DetalleFacturaProveedor> getDetalleFacturaDevolucionProveedor(int idFacturaProveedor, int idDevolucionProveedor)
/*  274:     */   {
/*  275: 402 */     String sql = "SELECT new DetalleFacturaProveedor (dfp.idDetalleFacturaProveedor, dfp.producto, dfp.cantidad, dfp.precio,  dfp.cantidad - SUM(CASE WHEN dmi.movimientoInventario.idMovimientoInventario NOT IN (:idDevolucionProveedor) THEN dmi.cantidad ELSE 0 END),  dfp.descuento)  FROM DetalleMovimientoInventario dmi RIGHT OUTER JOIN dmi.detalleFacturaProveedor dfp INNER JOIN dfp.facturaProveedor fp  WHERE fp.idFacturaProveedor = :idFacturaProveedor ";
/*  276:     */     
/*  277:     */ 
/*  278:     */ 
/*  279:     */ 
/*  280: 407 */     sql = sql + " GROUP BY dfp.idDetalleFacturaProveedor, dfp.producto, dfp.cantidad, dfp.precio, dfp.descuento";
/*  281:     */     
/*  282: 409 */     Query query = this.em.createQuery(sql).setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/*  283:     */     
/*  284: 411 */     query.setParameter("idDevolucionProveedor", Integer.valueOf(idDevolucionProveedor));
/*  285:     */     
/*  286: 413 */     List<DetalleFacturaProveedor> detalleFacturaProveedores = query.getResultList();
/*  287: 414 */     for (DetalleFacturaProveedor detalleFacturaProveedor : detalleFacturaProveedores) {
/*  288: 415 */       detalleFacturaProveedor.getProducto().getId();
/*  289:     */     }
/*  290: 417 */     return query.getResultList();
/*  291:     */   }
/*  292:     */   
/*  293:     */   public boolean verificaAjusteTomaFisica(int idMovimientoInventario)
/*  294:     */   {
/*  295: 429 */     String sql = "SELECT count(mi.tomaFisica.idTomaFisica) FROM MovimientoInventario mi WHERE mi.idMovimientoInventario = :idMovimientoInventario";
/*  296:     */     
/*  297:     */ 
/*  298: 432 */     Query query = this.em.createQuery(sql).setParameter("idMovimientoInventario", Integer.valueOf(idMovimientoInventario));
/*  299:     */     
/*  300: 434 */     Long cuenta = (Long)query.getSingleResult();
/*  301:     */     
/*  302: 436 */     return cuenta.longValue() > 0L;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public List<DetalleInterfazContable> getAjusteInventarioCCIIC(List<Integer> listaIdMovimientoInventario)
/*  306:     */     throws ExcepcionAS2Financiero
/*  307:     */   {
/*  308:     */     try
/*  309:     */     {
/*  310: 450 */       StringBuilder sql = new StringBuilder();
/*  311: 451 */       sql.append(" SELECT new DetalleInterfazContable(cc.idCuentaContable, '',CONCAT(do.nombre, ' # ', mi.numero),'',ROUND(SUM(do.operacion*ip.costo),2),mi.idMovimientoInventario ) ");
/*  312:     */       
/*  313: 453 */       sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  314: 454 */       sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  315: 455 */       sql.append(" INNER JOIN mi.documento do ");
/*  316: 456 */       sql.append(" INNER JOIN dmi.producto pr ");
/*  317: 457 */       sql.append(" INNER JOIN dmi.inventarioProducto ip ");
/*  318: 458 */       sql.append(" INNER JOIN pr.subcategoriaProducto sc ");
/*  319: 459 */       sql.append(" LEFT JOIN sc.cuentaContableInventario cc");
/*  320: 460 */       sql.append(" WHERE pr.tipoProducto=:tipoProducto ");
/*  321: 461 */       sql.append(" AND mi.idMovimientoInventario in (:listaIdMovimientoInventario) ");
/*  322: 462 */       sql.append(" GROUP BY cc.idCuentaContable, ");
/*  323: 463 */       sql.append(" do.nombre, mi.numero,mi.idMovimientoInventario ");
/*  324:     */       
/*  325: 465 */       Query query = this.em.createQuery(sql.toString());
/*  326: 466 */       query.setParameter("listaIdMovimientoInventario", listaIdMovimientoInventario);
/*  327: 467 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  328:     */       
/*  329: 469 */       return query.getResultList();
/*  330:     */     }
/*  331:     */     catch (IllegalArgumentException e)
/*  332:     */     {
/*  333: 471 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableInventario");
/*  334:     */     }
/*  335:     */   }
/*  336:     */   
/*  337:     */   @Deprecated
/*  338:     */   public List<DetalleInterfazContable> getAjusteIngresoCCAIIC(Integer idMovimientoInventario)
/*  339:     */   {
/*  340: 483 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(sc.cuentaContableAjusteIngreso.idCuentaContable,\t'',CONCAT(do.nombre, ' # ', mi.numero),'',ROUND(-SUM(ip.costo),2) )\tFROM DetalleMovimientoInventario dmi\tINNER JOIN dmi.movimientoInventario mi\tINNER JOIN mi.documento do\tINNER JOIN dmi.producto pr \tINNER JOIN dmi.inventarioProducto ip\tINNER JOIN pr.subcategoriaProducto sc WHERE pr.tipoProducto=:tipoProducto AND mi.idMovimientoInventario=:idMovimientoInventario\tGROUP BY sc.cuentaContableAjusteIngreso.idCuentaContable, do.nombre, mi.numero");
/*  341:     */     
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345:     */ 
/*  346:     */ 
/*  347: 490 */     query.setParameter("idMovimientoInventario", idMovimientoInventario);
/*  348: 491 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  349: 492 */     return query.getResultList();
/*  350:     */   }
/*  351:     */   
/*  352:     */   @Deprecated
/*  353:     */   public List<DetalleInterfazContable> getAjusteEgresoCCAEIC(Integer idMovimientoInventario)
/*  354:     */   {
/*  355: 503 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(sc.cuentaContableAjusteEgreso.idCuentaContable,\t'',CONCAT(do.nombre, ' # ', mi.numero),'',ROUND(SUM(ip.costo),2) )\tFROM DetalleMovimientoInventario dmi\tINNER JOIN dmi.movimientoInventario mi\tINNER JOIN mi.documento do\tINNER JOIN dmi.producto pr \tINNER JOIN dmi.inventarioProducto ip\tINNER JOIN pr.subcategoriaProducto sc WHERE pr.tipoProducto=:tipoProducto AND mi.idMovimientoInventario=:idMovimientoInventario\tGROUP BY sc.cuentaContableAjusteEgreso.idCuentaContable, do.nombre, mi.numero");
/*  356:     */     
/*  357:     */ 
/*  358:     */ 
/*  359:     */ 
/*  360:     */ 
/*  361:     */ 
/*  362: 510 */     query.setParameter("idMovimientoInventario", idMovimientoInventario);
/*  363: 511 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  364: 512 */     return query.getResultList();
/*  365:     */   }
/*  366:     */   
/*  367:     */   public List<DetalleInterfazContable> getAjusteCCA(List<Integer> listaIdMovimientoInventario)
/*  368:     */     throws ExcepcionAS2Inventario
/*  369:     */   {
/*  370:     */     try
/*  371:     */     {
/*  372: 525 */       StringBuilder sql = new StringBuilder();
/*  373: 526 */       sql.append("SELECT new DetalleInterfazContable(cc.idCuentaContable,'',CONCAT(do.nombre, ' # ', mi.numero),'',ROUND(SUM(do.operacion*ip.costo*(-1)),2), mi.idMovimientoInventario  ) ");
/*  374:     */       
/*  375: 528 */       sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  376: 529 */       sql.append(" JOIN dmi.movimientoInventario mi ");
/*  377: 530 */       sql.append(" JOIN mi.motivoAjusteInventario mai ");
/*  378: 531 */       sql.append(" JOIN mi.documento do ");
/*  379: 532 */       sql.append(" JOIN dmi.producto pr ");
/*  380: 533 */       sql.append(" JOIN dmi.inventarioProducto ip ");
/*  381: 534 */       sql.append(" LEFT JOIN mai.cuentaContable cc ");
/*  382: 535 */       sql.append(" WHERE pr.tipoProducto=:tipoProducto ");
/*  383: 536 */       sql.append(" AND mi.idMovimientoInventario in (:listaIdMovimientoInventario) ");
/*  384: 537 */       sql.append(" GROUP BY cc.idCuentaContable, do.nombre, mi.numero, mi.idMovimientoInventario ");
/*  385:     */       
/*  386: 539 */       Query query = this.em.createQuery(sql.toString());
/*  387: 540 */       query.setParameter("listaIdMovimientoInventario", listaIdMovimientoInventario);
/*  388: 541 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/*  389: 542 */       return query.getResultList();
/*  390:     */     }
/*  391:     */     catch (IllegalArgumentException e)
/*  392:     */     {
/*  393: 545 */       throw new ExcepcionAS2Inventario("msg_error_parametrizacion_contable", " motivoAjusteInventario");
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   public void actualizarEstado(Integer idMovimientoInventario, Estado estado)
/*  398:     */   {
/*  399: 550 */     Query query = this.em.createQuery("UPDATE MovimientoInventario mi SET mi.estado=:estado WHERE mi.idMovimientoInventario=:idMovimientoInventario");
/*  400: 551 */     query.setParameter("idMovimientoInventario", idMovimientoInventario);
/*  401: 552 */     query.setParameter("estado", estado);
/*  402: 553 */     query.executeUpdate();
/*  403:     */   }
/*  404:     */   
/*  405:     */   public List<MovimientoInventario> getListaConsumoBodega(Date fechaDesde, Date fechaHasta, DocumentoBase documentoBase, int idOrganizacion)
/*  406:     */     throws ExcepcionAS2Financiero
/*  407:     */   {
/*  408: 569 */     Query query = this.em.createQuery("SELECT mi FROM MovimientoInventario mi  INNER JOIN mi.documento do  WHERE mi.fecha BETWEEN :fechaDesde AND :fechaHasta  AND do.documentoBase =:documentoBase \tAND mi.estado<>:estadoAnulado  AND mi.estado<>:estadoContabilizado AND mi.idOrganizacion = :idOrganizacion ");
/*  409:     */     
/*  410:     */ 
/*  411:     */ 
/*  412: 573 */     query.setParameter("fechaDesde", fechaDesde);
/*  413: 574 */     query.setParameter("fechaHasta", fechaHasta);
/*  414: 575 */     query.setParameter("documentoBase", documentoBase);
/*  415: 576 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  416: 577 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/*  417: 578 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  418:     */     
/*  419: 580 */     return query.getResultList();
/*  420:     */   }
/*  421:     */   
/*  422:     */   public void actualizarEstadoConsumosInterfazContable(InterfazContableProceso interfazContableProceso)
/*  423:     */   {
/*  424: 592 */     String sql = "UPDATE MovimientoInventario mi  SET mi.estado=:estado, mi.interfazContableProceso = NULL\tWHERE mi.interfazContableProceso = :interfazContableProceso";
/*  425:     */     
/*  426:     */ 
/*  427: 595 */     Query query = this.em.createQuery(sql);
/*  428: 596 */     query.setParameter("estado", Estado.PROCESADO);
/*  429: 597 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/*  430: 598 */     query.executeUpdate();
/*  431:     */   }
/*  432:     */   
/*  433:     */   public List<MovimientoInventario> getListaAjusteInvetario(int idOrganizacion, Date fechaDesde, Date fechaHasta, TipoOrganizacion tipoOrganizacion, List<DocumentoBase> listaDocumento)
/*  434:     */   {
/*  435: 604 */     StringBuilder sql = new StringBuilder();
/*  436: 605 */     sql.append(" SELECT mi FROM MovimientoInventario mi ");
/*  437: 606 */     sql.append(" JOIN FETCH mi.documento d ");
/*  438: 607 */     sql.append(" LEFT JOIN FETCH mi.movimientoInventarioPadre mp ");
/*  439: 608 */     sql.append(" LEFT JOIN FETCH d.tipoAsiento ta ");
/*  440: 609 */     sql.append(" LEFT JOIN FETCH mi.asiento a ");
/*  441: 610 */     sql.append(" WHERE mi.fecha BETWEEN  :fechaDesde AND :fechaHasta ");
/*  442: 611 */     sql.append(" AND d.documentoBase IN (:listaDocumento)");
/*  443: 612 */     sql.append(" AND mi.idOrganizacion=:idOrganizacion ");
/*  444: 613 */     sql.append(" AND mi.estado!=:estadoAnulado ");
/*  445:     */     
/*  446: 615 */     sql.append(" AND (d.documentoBase != :documentoBaseTransformacion OR mp IS NOT NULL) ");
/*  447: 616 */     sql.append(" AND mi.indicadorSaldoInicial=false ");
/*  448: 617 */     if (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_GENERAL)) {
/*  449: 618 */       sql.append(" AND (d.documentoBase = :documentoIngresoProduccion OR d.documentoBase = :documentoBaseTransformacion OR d.indicadorGeneraCosto=false OR d.indicadorCostoEstandar=true) ");
/*  450:     */     }
/*  451: 622 */     if ((listaDocumento == null) || (listaDocumento.isEmpty()))
/*  452:     */     {
/*  453: 623 */       listaDocumento = new ArrayList();
/*  454: 624 */       listaDocumento.add(DocumentoBase.AJUSTE_INVENTARIO);
/*  455:     */     }
/*  456: 627 */     Query query = this.em.createQuery(sql.toString());
/*  457: 628 */     query.setParameter("fechaDesde", fechaDesde);
/*  458: 629 */     query.setParameter("fechaHasta", fechaHasta);
/*  459: 630 */     query.setParameter("listaDocumento", listaDocumento);
/*  460: 631 */     query.setParameter("documentoBaseTransformacion", DocumentoBase.TRANSFORMACION_PRODUCTO);
/*  461: 632 */     if (tipoOrganizacion.equals(TipoOrganizacion.TIPO_ORGANIZACION_GENERAL)) {
/*  462: 633 */       query.setParameter("documentoIngresoProduccion", DocumentoBase.INGRESO_PRODUCCION);
/*  463:     */     }
/*  464: 635 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  465: 636 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  466:     */     
/*  467: 638 */     List<MovimientoInventario> lista = new ArrayList();
/*  468: 639 */     lista = query.getResultList();
/*  469: 641 */     for (MovimientoInventario movimientoInventario : lista) {
/*  470: 642 */       if (movimientoInventario.getAsiento() != null)
/*  471:     */       {
/*  472: 643 */         movimientoInventario.getAsiento().getListaDetalleAsiento().size();
/*  473: 644 */         for (DetalleAsiento da : movimientoInventario.getAsiento().getListaDetalleAsiento()) {
/*  474: 645 */           da.getCuentaContable().getId();
/*  475:     */         }
/*  476:     */       }
/*  477:     */     }
/*  478: 650 */     return lista;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public void actualizarEstadoContabilizadoMovimientoInventarioInterfazContable(InterfazContableProceso interfazContableProceso, List<Integer> listaMovimientoIventarioInterfazContable)
/*  482:     */   {
/*  483: 664 */     String sql = "UPDATE MovimientoInventario mi  SET mi.estado=:estado, mi.interfazContableProceso = :interfazContableProceso, fechaContabilizacion = :fechaContabilizacion\tWHERE mi.idMovimientoInventario in (:listaMovimientoIventarioInterfazContable)";
/*  484:     */     
/*  485:     */ 
/*  486:     */ 
/*  487: 668 */     Query query = this.em.createQuery(sql);
/*  488: 669 */     query.setParameter("estado", Estado.CONTABILIZADO);
/*  489: 670 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/*  490: 671 */     query.setParameter("fechaContabilizacion", interfazContableProceso.getFechaHasta());
/*  491: 672 */     query.setParameter("listaMovimientoIventarioInterfazContable", listaMovimientoIventarioInterfazContable);
/*  492: 673 */     query.executeUpdate();
/*  493:     */   }
/*  494:     */   
/*  495:     */   public List<DetalleInterfazContableProceso> getInterfazMovimientoInventarioDimensiones(List<Integer> listaMovimientoInventario, DocumentoBase documentoBase, boolean indicadorRecepcionTransferencia)
/*  496:     */   {
/*  497: 692 */     StringBuilder sql = new StringBuilder();
/*  498:     */     
/*  499: 694 */     String descripcion = "";
/*  500: 695 */     String grupoDescripcion = "";
/*  501: 696 */     if (listaMovimientoInventario.size() == 1)
/*  502:     */     {
/*  503: 697 */       descripcion = "CONCAT(d.nombre,' #',mi.numero,' ',mi.descripcion)";
/*  504: 698 */       grupoDescripcion = "," + descripcion;
/*  505:     */     }
/*  506:     */     else
/*  507:     */     {
/*  508: 700 */       descripcion = "''";
/*  509:     */     }
/*  510: 703 */     if (documentoBase.equals(DocumentoBase.CONSUMO_BODEGA)) {
/*  511: 704 */       sql.append(" SELECT new DetalleInterfazContableProcesoConsumoBodega(");
/*  512:     */     } else {
/*  513: 706 */       sql.append(" SELECT new DetalleInterfazContableProcesoMovimientoInventario(");
/*  514:     */     }
/*  515: 709 */     sql.append(" d.idDocumento, d.nombre, s.idSucursal, s.nombre, cp.idCategoriaProducto, cp.nombre, ");
/*  516: 710 */     sql.append(" sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, ");
/*  517: 712 */     if (indicadorRecepcionTransferencia) {
/*  518: 713 */       sql.append(" COALESCE(bt.idBodega,b.idBodega), COALESCE(bt.nombre, b.nombre), ");
/*  519:     */     } else {
/*  520: 715 */       sql.append(" b.idBodega, b.nombre, ");
/*  521:     */     }
/*  522: 718 */     if (documentoBase.equals(DocumentoBase.CONSUMO_BODEGA)) {
/*  523: 719 */       sql.append(" dcs.idDestinoCosto, dcs.nombre, proy.idDimensionContable, proy.nombre, ");
/*  524:     */     } else {
/*  525: 721 */       sql.append(" mai.idMotivoAjusteInventario, mai.nombre, ");
/*  526:     */     }
/*  527: 723 */     sql.append(" " + descripcion + ", CASE WHEN d.documentoBase = :documentoBaseDevolucionConsumoBodega THEN (-1 * SUM(ip.costo)) ELSE SUM(ip.costo) END )");
/*  528:     */     
/*  529: 725 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  530: 726 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  531: 727 */     sql.append(" INNER JOIN mi.documento d ");
/*  532: 728 */     sql.append(" INNER JOIN dmi.inventarioProducto ip ");
/*  533: 729 */     sql.append(" INNER JOIN ip.bodega b ");
/*  534: 730 */     sql.append(" LEFT OUTER JOIN ip.inventarioProductoTransferencia ipt ");
/*  535: 731 */     sql.append(" LEFT OUTER JOIN ipt.bodega bt ");
/*  536: 732 */     sql.append(" INNER JOIN dmi.producto p ");
/*  537: 733 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/*  538: 734 */     sql.append(" INNER JOIN sp.categoriaProducto cp ");
/*  539: 735 */     sql.append(" LEFT JOIN mi.motivoAjusteInventario mai ");
/*  540: 736 */     sql.append(" LEFT JOIN mi.proyecto proy ");
/*  541: 737 */     sql.append(" LEFT JOIN dmi.destinoCosto dcs ");
/*  542: 738 */     sql.append(" LEFT JOIN mi.sucursal s ");
/*  543: 739 */     sql.append(" WHERE mi.idMovimientoInventario in (:listaMovimientoInventario)");
/*  544: 740 */     sql.append(" GROUP BY d.idDocumento, ");
/*  545: 741 */     if (documentoBase.equals(DocumentoBase.CONSUMO_BODEGA)) {
/*  546: 742 */       sql.append(" dcs.idDestinoCosto, dcs.nombre, proy.idDimensionContable, proy.nombre, ");
/*  547:     */     } else {
/*  548: 744 */       sql.append(" mai.idMotivoAjusteInventario, mai.nombre, ");
/*  549:     */     }
/*  550: 746 */     sql.append(" d.idDocumento, d.nombre, s.idSucursal, s.nombre, cp.idCategoriaProducto, cp.nombre, ");
/*  551: 747 */     sql.append(" sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, ");
/*  552: 748 */     if (indicadorRecepcionTransferencia) {
/*  553: 749 */       sql.append(" COALESCE(bt.idBodega,b.idBodega), COALESCE(bt.nombre, b.nombre) ");
/*  554:     */     } else {
/*  555: 751 */       sql.append(" b.idBodega, b.nombre ");
/*  556:     */     }
/*  557: 753 */     sql.append(" ,d.documentoBase ");
/*  558: 754 */     sql.append(grupoDescripcion);
/*  559: 755 */     sql.append(" HAVING SUM(ip.costo) <> 0");
/*  560:     */     
/*  561: 757 */     Query query = this.em.createQuery(sql.toString());
/*  562: 758 */     query.setParameter("listaMovimientoInventario", listaMovimientoInventario);
/*  563: 759 */     query.setParameter("documentoBaseDevolucionConsumoBodega", DocumentoBase.DEVOLUCION_CONSUMO_BODEGA);
/*  564: 760 */     return query.getResultList();
/*  565:     */   }
/*  566:     */   
/*  567:     */   public List getReporteAprobarAjusteInventario(int idMovimientoInventario)
/*  568:     */   {
/*  569: 771 */     StringBuilder sql = new StringBuilder();
/*  570: 772 */     sql.append(" SELECT d.nombre,mi.numero,mi.fecha,mai.nombre,mi.descripcion,mi.indicadorSaldoInicial");
/*  571: 773 */     sql.append(",p.codigo,p.nombre,dmi.unidadConversion.nombre,dmi.bodegaOrigen.nombre,dmi.descripcion,dmi.cantidad,dmi.costo");
/*  572: 774 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  573: 775 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  574: 776 */     sql.append(" INNER JOIN mi.documento d ");
/*  575: 777 */     sql.append(" INNER JOIN dmi.producto p ");
/*  576: 778 */     sql.append(" LEFT JOIN mi.motivoAjusteInventario mai ");
/*  577: 779 */     sql.append(" WHERE mi.idMovimientoInventario = :idMovimientoInventario");
/*  578:     */     
/*  579: 781 */     Query query = this.em.createQuery(sql.toString());
/*  580: 782 */     query.setParameter("idMovimientoInventario", Integer.valueOf(idMovimientoInventario));
/*  581: 783 */     return query.getResultList();
/*  582:     */   }
/*  583:     */   
/*  584:     */   public List<DetalleAsiento> getInterfazTextilPadilla(MovimientoInventario movimientoInventario)
/*  585:     */   {
/*  586: 790 */     List<DetalleAsiento> listaInv = new ArrayList();
/*  587: 791 */     List<DetalleAsiento> listaCon = new ArrayList();
/*  588:     */     
/*  589: 793 */     StringBuilder sqlCa = new StringBuilder();
/*  590: 794 */     sqlCa.append(" SELECT NEW DetalleAsiento(cuco.idCuentaContable, cuco.codigo, cuco.nombre, cuco.indicadorMovimiento, ");
/*  591: 795 */     sqlCa.append(" ROUND(SUM(ip.costo),2), ");
/*  592: 796 */     sqlCa.append(" ROUND(SUM(ip.costo*0),2), ");
/*  593: 797 */     sqlCa.append(" CONCAT(d.nombre,' #',mi.numero,' ',mi.descripcion)  )");
/*  594: 798 */     sqlCa.append(" FROM DetalleMovimientoInventario dmi ");
/*  595: 799 */     sqlCa.append(" JOIN dmi.movimientoInventario mi ");
/*  596: 800 */     sqlCa.append(" JOIN mi.documento d ");
/*  597: 801 */     sqlCa.append(" JOIN dmi.inventarioProducto ip, CuentaContable cuco ");
/*  598: 802 */     sqlCa.append(" WHERE mi = :movimientoInventario");
/*  599:     */     
/*  600:     */ 
/*  601: 805 */     StringBuilder sqlInv = new StringBuilder(sqlCa.toString());
/*  602: 806 */     sqlInv.append(" AND ip.cuenta1 = cuco.codigo");
/*  603: 807 */     sqlInv.append(" GROUP BY cuco.idCuentaContable, cuco.codigo, cuco.nombre, cuco.indicadorMovimiento, CONCAT(d.nombre,' #',mi.numero,' ',mi.descripcion)");
/*  604:     */     
/*  605:     */ 
/*  606: 810 */     Query queryInv = this.em.createQuery(sqlInv.toString());
/*  607: 811 */     queryInv.setParameter("movimientoInventario", movimientoInventario);
/*  608: 812 */     listaInv.addAll(queryInv.getResultList());
/*  609:     */     
/*  610:     */ 
/*  611: 815 */     StringBuilder sqlCon = new StringBuilder(sqlCa.toString());
/*  612: 816 */     sqlCon.append(" AND ip.cuenta2 = cuco.codigo");
/*  613: 817 */     sqlCon.append(" GROUP BY cuco.idCuentaContable, cuco.codigo, cuco.nombre, cuco.indicadorMovimiento, CONCAT(d.nombre,' #',mi.numero,' ',mi.descripcion)");
/*  614:     */     
/*  615:     */ 
/*  616: 820 */     Query queryCon = this.em.createQuery(sqlCon.toString());
/*  617: 821 */     queryCon.setParameter("movimientoInventario", movimientoInventario);
/*  618: 822 */     listaCon.addAll(queryCon.getResultList());
/*  619: 824 */     for (DetalleAsiento detalle : listaCon)
/*  620:     */     {
/*  621: 826 */       BigDecimal debe = detalle.getDebe();
/*  622: 827 */       BigDecimal haber = detalle.getHaber();
/*  623: 828 */       detalle.setDebe(haber);
/*  624: 829 */       detalle.setHaber(debe);
/*  625:     */     }
/*  626: 833 */     listaInv.addAll(listaCon);
/*  627: 836 */     for (DetalleAsiento detale : listaInv)
/*  628:     */     {
/*  629: 837 */       detale.setDebe(detale.getDebe().setScale(2, RoundingMode.HALF_UP));
/*  630: 838 */       detale.setHaber(detale.getHaber().setScale(2, RoundingMode.HALF_UP));
/*  631:     */     }
/*  632: 841 */     return listaInv;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public List<Object[]> getReporteTransformacionProducto(MovimientoInventario transformacionProducto)
/*  636:     */   {
/*  637: 847 */     StringBuilder sql = new StringBuilder();
/*  638: 848 */     sql.append(" SELECT p.codigo,p.nombre,dmi.unidadConversion.nombre,bo.nombre,bd.nombre,dmi.cantidad, do.operacion ");
/*  639: 849 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  640: 850 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  641: 851 */     sql.append(" LEFT JOIN mi.movimientoInventarioPadre mip ");
/*  642: 852 */     sql.append(" LEFT JOIN dmi.bodegaOrigen bo ");
/*  643: 853 */     sql.append(" LEFT JOIN dmi.bodegaDestino bd ");
/*  644: 854 */     sql.append(" INNER JOIN mi.documento do ");
/*  645: 855 */     sql.append(" INNER JOIN dmi.producto p ");
/*  646: 856 */     sql.append(" WHERE mi.idMovimientoInventario =:idMovimientoInventario ");
/*  647: 858 */     if (transformacionProducto.getMovimientoInventarioPadre() != null) {
/*  648: 859 */       sql.append(" OR mi.idMovimientoInventario =:idMovimientoInventarioPadre ");
/*  649:     */     }
/*  650: 861 */     sql.append(" ORDER BY do.operacion ASC ");
/*  651:     */     
/*  652: 863 */     Query query = this.em.createQuery(sql.toString());
/*  653: 864 */     query.setParameter("idMovimientoInventario", Integer.valueOf(transformacionProducto.getId()));
/*  654: 865 */     if (transformacionProducto.getMovimientoInventarioPadre() != null) {
/*  655: 866 */       query.setParameter("idMovimientoInventarioPadre", Integer.valueOf(transformacionProducto.getMovimientoInventarioPadre().getId()));
/*  656:     */     }
/*  657: 869 */     return query.getResultList();
/*  658:     */   }
/*  659:     */   
/*  660:     */   public MovimientoInventario obtenerIngresoFabricacionPorFecha(int idOrganizacion, Date fecha, OrdenFabricacion ordenFabricacion)
/*  661:     */   {
/*  662: 874 */     StringBuilder sql = new StringBuilder();
/*  663: 875 */     sql.append(" SELECT DISTINCT(mi) ");
/*  664: 876 */     sql.append(" FROM MovimientoInventario mi ");
/*  665: 877 */     sql.append(" LEFT JOIN FETCH mi.asiento a ");
/*  666: 878 */     sql.append(" INNER JOIN FETCH mi.detalleMovimientosInventario dmi ");
/*  667: 879 */     sql.append(" LEFT JOIN FETCH dmi.bodegaOrigen bo ");
/*  668: 880 */     sql.append(" LEFT JOIN FETCH dmi.bodegaDestino bd ");
/*  669: 881 */     sql.append(" INNER JOIN FETCH mi.documento do ");
/*  670: 882 */     sql.append(" INNER JOIN FETCH dmi.producto p ");
/*  671: 883 */     sql.append(" LEFT JOIN FETCH dmi.inventarioProducto ip ");
/*  672: 884 */     sql.append(" INNER JOIN FETCH p.unidad u ");
/*  673: 885 */     sql.append(" INNER JOIN FETCH dmi.unidadConversion uc ");
/*  674: 886 */     sql.append(" INNER JOIN FETCH mi.ordenFabricacion ofa ");
/*  675: 887 */     sql.append(" WHERE mi.idOrganizacion =:idOrganizacion ");
/*  676: 888 */     sql.append(" AND mi.fecha =:fecha ");
/*  677: 889 */     sql.append(" AND do.documentoBase =:documentoBaseIngresoFabricacion ");
/*  678: 890 */     sql.append(" AND ofa.idOrdenFabricacion =:idOrdenFabricacion ");
/*  679:     */     
/*  680: 892 */     Query query = this.em.createQuery(sql.toString());
/*  681: 893 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  682: 894 */     query.setParameter("fecha", fecha);
/*  683: 895 */     query.setParameter("documentoBaseIngresoFabricacion", DocumentoBase.INGRESO_PRODUCCION);
/*  684: 896 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  685:     */     
/*  686: 898 */     List<MovimientoInventario> listaMovimientoInventario = query.getResultList();
/*  687: 899 */     if (listaMovimientoInventario.size() > 0)
/*  688:     */     {
/*  689: 900 */       MovimientoInventario mi = (MovimientoInventario)listaMovimientoInventario.get(0);
/*  690: 901 */       for (DetalleMovimientoInventario detalle : mi.getDetalleMovimientosInventario()) {
/*  691: 902 */         if (detalle.getInventarioProducto() != null)
/*  692:     */         {
/*  693: 903 */           StringBuilder sql2 = new StringBuilder();
/*  694: 904 */           sql2.append(" SELECT sip ");
/*  695: 905 */           sql2.append(" FROM SerieInventarioProducto sip ");
/*  696: 906 */           sql2.append(" INNER JOIN FETCH sip.inventarioProducto ip ");
/*  697: 907 */           sql2.append(" INNER JOIN FETCH sip.serieProducto sp ");
/*  698: 908 */           sql2.append(" WHERE ip.idInventarioProducto =:idInventarioProducto ");
/*  699:     */           
/*  700: 910 */           Query query2 = this.em.createQuery(sql2.toString());
/*  701: 911 */           query2.setParameter("idInventarioProducto", Integer.valueOf(detalle.getInventarioProducto().getId()));
/*  702: 912 */           List<SerieInventarioProducto> listaSerieInventarioProducto = query2.getResultList();
/*  703: 913 */           detalle.getInventarioProducto().setListaSerieProducto(listaSerieInventarioProducto);
/*  704:     */         }
/*  705:     */       }
/*  706: 916 */       return mi;
/*  707:     */     }
/*  708: 918 */     return null;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public List<DetalleMovimientoInventario> obtenerListaDetalleRecepcionFabricacion(int idOrganizacion, Bodega bodega)
/*  712:     */   {
/*  713: 922 */     return obtenerListaDetalleRecepcionFabricacion(idOrganizacion, bodega, null, null);
/*  714:     */   }
/*  715:     */   
/*  716:     */   public List<DetalleMovimientoInventario> obtenerListaDetalleRecepcionFabricacion(int idOrganizacion, Bodega bodega, String usuarioCreacion, List<Integer> idsSucursalesAsignadasUsuarioEnSesion)
/*  717:     */   {
/*  718: 928 */     StringBuilder sql = new StringBuilder();
/*  719: 929 */     sql.append(" SELECT dmi ");
/*  720: 930 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  721: 931 */     sql.append(" INNER JOIN FETCH dmi.movimientoInventario mi ");
/*  722: 932 */     sql.append(" LEFT JOIN FETCH dmi.bodegaOrigen bo ");
/*  723: 933 */     sql.append(" LEFT JOIN FETCH dmi.bodegaDestino bd ");
/*  724: 934 */     sql.append(" LEFT JOIN FETCH dmi.destinoCosto dc ");
/*  725: 935 */     sql.append(" LEFT JOIN FETCH dmi.lote lo ");
/*  726: 936 */     sql.append(" INNER JOIN FETCH mi.documento do ");
/*  727: 937 */     sql.append(" INNER JOIN FETCH dmi.producto p ");
/*  728: 938 */     sql.append(" LEFT JOIN FETCH dmi.inventarioProducto ip ");
/*  729: 939 */     sql.append(" INNER JOIN FETCH p.unidad u ");
/*  730: 940 */     sql.append(" LEFT JOIN FETCH p.unidadVenta uv ");
/*  731: 941 */     sql.append(" LEFT JOIN FETCH p.unidadAlmacenamiento ua ");
/*  732: 942 */     sql.append(" INNER JOIN FETCH p.subcategoriaProducto scp ");
/*  733: 943 */     sql.append(" INNER JOIN FETCH dmi.unidadConversion uc ");
/*  734: 944 */     sql.append(" INNER JOIN FETCH mi.ordenFabricacion ofa ");
/*  735: 945 */     sql.append(" WHERE mi.idOrganizacion =:idOrganizacion ");
/*  736: 946 */     sql.append(" AND do.documentoBase =:documentoBaseIngresoFabricacion ");
/*  737: 947 */     sql.append(" AND ((p.indicadorControlCalidad = true AND dmi.indicadorCumpleCalidad = true AND dmi.indicadorRecibido = false) OR (p.indicadorControlCalidad = false AND dmi.indicadorRecibido = false)) ");
/*  738: 950 */     if (bodega != null) {
/*  739: 951 */       sql.append(" AND bd.idBodega = :idBodega ");
/*  740:     */     }
/*  741: 953 */     if (usuarioCreacion != null) {
/*  742: 954 */       sql.append(" AND dmi.usuarioCreacion = :usuarioCreacion");
/*  743:     */     }
/*  744: 956 */     if ((idsSucursalesAsignadasUsuarioEnSesion != null) && (idsSucursalesAsignadasUsuarioEnSesion.size() > 0)) {
/*  745: 957 */       sql.append(" AND dmi.idSucursal in (:idSucursales)");
/*  746:     */     }
/*  747: 959 */     sql.append(" ORDER BY dmi.fechaCreacion ASC ");
/*  748:     */     
/*  749: 961 */     Query query = this.em.createQuery(sql.toString());
/*  750: 962 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  751: 963 */     if (bodega != null) {
/*  752: 964 */       query.setParameter("idBodega", Integer.valueOf(bodega.getId()));
/*  753:     */     }
/*  754: 966 */     if (usuarioCreacion != null) {
/*  755: 967 */       query.setParameter("usuarioCreacion", usuarioCreacion);
/*  756:     */     }
/*  757: 969 */     if ((idsSucursalesAsignadasUsuarioEnSesion != null) && (idsSucursalesAsignadasUsuarioEnSesion.size() > 0)) {
/*  758: 970 */       query.setParameter("idSucursales", idsSucursalesAsignadasUsuarioEnSesion);
/*  759:     */     }
/*  760: 972 */     query.setParameter("documentoBaseIngresoFabricacion", DocumentoBase.INGRESO_PRODUCCION);
/*  761:     */     
/*  762: 974 */     List<DetalleMovimientoInventario> listaDetalleMovimientoInventario = query.getResultList();
/*  763: 975 */     return listaDetalleMovimientoInventario;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public MovimientoInventario cargarDetallesDiariosIngresoFabricacion(MovimientoInventario ingresoFabricacion, CategoriaProducto categoriaProducto, int numeroAtributos)
/*  767:     */   {
/*  768: 981 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  769: 982 */     CriteriaQuery<MovimientoInventario> cqCabecera = criteriaBuilder.createQuery(MovimientoInventario.class);
/*  770: 983 */     Root<MovimientoInventario> fromCabecera = cqCabecera.from(MovimientoInventario.class);
/*  771: 984 */     fromCabecera.fetch("responsableSalidaMercaderia", JoinType.LEFT);
/*  772:     */     
/*  773: 986 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  774: 987 */     documento.fetch("secuencia", JoinType.LEFT);
/*  775:     */     
/*  776: 989 */     fromCabecera.fetch("asiento", JoinType.LEFT);
/*  777: 990 */     fromCabecera.fetch("bodegaOrigen", JoinType.LEFT);
/*  778:     */     
/*  779: 992 */     Fetch<Object, Object> ordenFabricacion = fromCabecera.fetch("ordenFabricacion", JoinType.LEFT);
/*  780: 993 */     ordenFabricacion.fetch("ordenFabricacionPadre", JoinType.LEFT);
/*  781: 994 */     ordenFabricacion.fetch("ordenFabricacionPrincipal", JoinType.LEFT);
/*  782: 995 */     ordenFabricacion.fetch("valorAtributoOrdenFabricacion", JoinType.LEFT);
/*  783: 996 */     Fetch<Object, Object> productoOF = ordenFabricacion.fetch("producto", JoinType.LEFT);
/*  784: 997 */     productoOF.fetch("unidad", JoinType.LEFT);
/*  785: 998 */     productoOF.fetch("unidadCompra", JoinType.LEFT);
/*  786: 999 */     productoOF.fetch("unidadVenta", JoinType.LEFT);
/*  787:1000 */     productoOF.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  788:1001 */     productoOF.fetch("unidadInformativa", JoinType.LEFT);
/*  789:1002 */     productoOF.fetch("subcategoriaProducto", JoinType.LEFT);
/*  790:     */     
/*  791:1004 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idMovimientoInventario"), Integer.valueOf(ingresoFabricacion.getId())));
/*  792:1005 */     CriteriaQuery<MovimientoInventario> select = cqCabecera.select(fromCabecera);
/*  793:     */     
/*  794:1007 */     MovimientoInventario movimientoInventario = (MovimientoInventario)this.em.createQuery(select).getSingleResult();
/*  795:1008 */     this.em.detach(movimientoInventario);
/*  796:     */     
/*  797:     */ 
/*  798:1011 */     CriteriaQuery<DetalleMovimientoInventario> cqDetalle = criteriaBuilder.createQuery(DetalleMovimientoInventario.class);
/*  799:1012 */     Root<DetalleMovimientoInventario> fromDetalle = cqDetalle.from(DetalleMovimientoInventario.class);
/*  800:1013 */     fromDetalle.fetch("bodegaOrigen", JoinType.LEFT);
/*  801:1014 */     fromDetalle.fetch("transformacionAutomatica", JoinType.LEFT);
/*  802:     */     
/*  803:1016 */     Fetch<Object, Object> lote = fromDetalle.fetch("lote", JoinType.LEFT);
/*  804:1017 */     for (int i = 1; i <= numeroAtributos; i++)
/*  805:     */     {
/*  806:1018 */       lote.fetch("atributo" + i, JoinType.LEFT);
/*  807:1019 */       lote.fetch("valorAtributo" + i, JoinType.LEFT);
/*  808:     */     }
/*  809:1022 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  810:1023 */     Fetch<Object, Object> mi = fromDetalle.fetch("movimientoInventario", JoinType.INNER);
/*  811:1024 */     mi.fetch("ordenFabricacion", JoinType.INNER);
/*  812:1025 */     mi.fetch("documento", JoinType.INNER);
/*  813:1026 */     producto.fetch("unidad", JoinType.LEFT);
/*  814:1027 */     producto.fetch("unidadCompra", JoinType.LEFT);
/*  815:1028 */     producto.fetch("unidadVenta", JoinType.LEFT);
/*  816:1029 */     producto.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  817:1030 */     producto.fetch("unidadInformativa", JoinType.LEFT);
/*  818:1031 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  819:     */     
/*  820:1033 */     Fetch<Object, Object> inventarioProducto = fromDetalle.fetch("inventarioProducto", JoinType.LEFT);
/*  821:1034 */     inventarioProducto.fetch("lote", JoinType.LEFT);
/*  822:     */     
/*  823:1036 */     fromDetalle.fetch("unidadConversion", JoinType.LEFT);
/*  824:1038 */     if (categoriaProducto != null) {
/*  825:1039 */       cqDetalle.where(new Predicate[] {criteriaBuilder
/*  826:1040 */         .equal(fromDetalle.join("movimientoInventario").get("idOrganizacion"), Integer.valueOf(movimientoInventario.getIdOrganizacion())), criteriaBuilder
/*  827:1041 */         .equal(fromDetalle.join("movimientoInventario").get("fecha"), movimientoInventario.getFecha()), criteriaBuilder
/*  828:1042 */         .equal(fromDetalle
/*  829:1043 */         .join("producto").join("subcategoriaProducto").join("categoriaProducto").get("idCategoriaProducto"), 
/*  830:1044 */         Integer.valueOf(categoriaProducto.getId())) });
/*  831:     */     } else {
/*  832:1046 */       cqDetalle.where(new Predicate[] {criteriaBuilder
/*  833:1047 */         .equal(fromDetalle.join("movimientoInventario").get("idOrganizacion"), Integer.valueOf(movimientoInventario.getIdOrganizacion())), criteriaBuilder
/*  834:1048 */         .equal(fromDetalle.join("movimientoInventario").get("fecha"), movimientoInventario.getFecha()) });
/*  835:     */     }
/*  836:1050 */     CriteriaQuery<DetalleMovimientoInventario> selectDetalle = cqDetalle.select(fromDetalle);
/*  837:1051 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idDetalleMovimientoInventario")) });
/*  838:     */     
/*  839:1053 */     List<DetalleMovimientoInventario> listaDetalleMovimientosInventario = this.em.createQuery(selectDetalle).getResultList();
/*  840:1054 */     movimientoInventario.setDetalleMovimientosInventario(listaDetalleMovimientosInventario);
/*  841:1055 */     for (DetalleMovimientoInventario detalleMovimientoInventario : listaDetalleMovimientosInventario)
/*  842:     */     {
/*  843:1057 */       CriteriaQuery<LecturaBalanza> cqLecturaBalanza = criteriaBuilder.createQuery(LecturaBalanza.class);
/*  844:1058 */       Root<LecturaBalanza> fromLecturaBalanza = cqLecturaBalanza.from(LecturaBalanza.class);
/*  845:1059 */       fromLecturaBalanza.fetch("producto", JoinType.INNER);
/*  846:1060 */       fromLecturaBalanza.fetch("unidadManejo", JoinType.INNER);
/*  847:1061 */       fromLecturaBalanza.fetch("pallet", JoinType.LEFT);
/*  848:1062 */       fromLecturaBalanza.fetch("detalleMovimientoInventario", JoinType.INNER);
/*  849:     */       
/*  850:1064 */       Path<Integer> pathIdDetalle = fromLecturaBalanza.join("detalleMovimientoInventario").get("idDetalleMovimientoInventario");
/*  851:1065 */       cqLecturaBalanza.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(detalleMovimientoInventario.getId())));
/*  852:1066 */       CriteriaQuery<LecturaBalanza> selectLecturaBalanza = cqLecturaBalanza.select(fromLecturaBalanza);
/*  853:     */       
/*  854:1068 */       List<LecturaBalanza> listaLecturaBalanza = this.em.createQuery(selectLecturaBalanza).getResultList();
/*  855:1069 */       detalleMovimientoInventario.setListaLecturaBalanza(listaLecturaBalanza);
/*  856:     */     }
/*  857:1072 */     return movimientoInventario;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public boolean existeDetalleIngresoFabricacionSinRecibir(OrdenFabricacion ordenFabricacion)
/*  861:     */   {
/*  862:1076 */     StringBuilder sql = new StringBuilder();
/*  863:1077 */     sql.append(" SELECT COUNT(dmi) ");
/*  864:1078 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  865:1079 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  866:1080 */     sql.append(" INNER JOIN mi.documento do ");
/*  867:1081 */     sql.append(" INNER JOIN mi.ordenFabricacion ofa ");
/*  868:1082 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  869:1083 */     sql.append(" AND do.documentoBase =:documentoBaseIngresoFabricacion ");
/*  870:1084 */     sql.append(" AND dmi.indicadorRecibido != true ");
/*  871:     */     
/*  872:1086 */     Query query = this.em.createQuery(sql.toString());
/*  873:1087 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  874:1088 */     query.setParameter("documentoBaseIngresoFabricacion", DocumentoBase.INGRESO_PRODUCCION);
/*  875:     */     
/*  876:1090 */     Long cantidad = (Long)query.getSingleResult();
/*  877:1091 */     return cantidad.longValue() > 0L;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public List<MovimientoInventario> buscarIngresoFabricacionPorOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  881:     */   {
/*  882:1096 */     StringBuilder sql = new StringBuilder();
/*  883:1097 */     sql.append(" SELECT mi ");
/*  884:1098 */     sql.append(" FROM MovimientoInventario mi ");
/*  885:1099 */     sql.append(" INNER JOIN FETCH mi.ordenFabricacion ofa ");
/*  886:1100 */     sql.append(" INNER JOIN FETCH mi.documento do ");
/*  887:1101 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  888:1102 */     sql.append(" AND do.documentoBase =:documentoBaseIngresoFabricacion ");
/*  889:     */     
/*  890:1104 */     Query query = this.em.createQuery(sql.toString());
/*  891:1105 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  892:1106 */     query.setParameter("documentoBaseIngresoFabricacion", DocumentoBase.INGRESO_PRODUCCION);
/*  893:     */     
/*  894:1108 */     return query.getResultList();
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void eliminar(MovimientoInventario entidad)
/*  898:     */   {
/*  899:1113 */     entidad = cargarDetalle(entidad.getId());
/*  900:1114 */     for (DetalleMovimientoInventario dmi : entidad.getDetalleMovimientosInventario())
/*  901:     */     {
/*  902:1115 */       if (dmi.getInventarioProducto() != null)
/*  903:     */       {
/*  904:1116 */         if (dmi.getInventarioProducto().getInventarioProductoTransferencia() != null) {
/*  905:1117 */           this.servicioInventarioProducto.eliminar(dmi.getInventarioProducto().getInventarioProductoTransferencia());
/*  906:     */         }
/*  907:1119 */         this.servicioInventarioProducto.eliminar(dmi.getInventarioProducto());
/*  908:     */       }
/*  909:1121 */       this.servicioDetalleMovimientoInventario.eliminar(dmi);
/*  910:     */     }
/*  911:1123 */     if (entidad.getAsiento() != null)
/*  912:     */     {
/*  913:1124 */       for (DetalleAsiento da : entidad.getAsiento().getListaDetalleAsiento()) {
/*  914:1125 */         this.servicioDetalleAsiento.eliminar(da);
/*  915:     */       }
/*  916:1127 */       this.servicioAsiento.eliminar(entidad.getAsiento());
/*  917:     */     }
/*  918:1129 */     if (entidad.getGuiaRemision() != null) {
/*  919:1130 */       entidad.getGuiaRemision().setTransferenciaBodega(null);
/*  920:     */     }
/*  921:1132 */     super.eliminar(entidad);
/*  922:     */   }
/*  923:     */   
/*  924:     */   public MovimientoInventario obtenerUltimaTransformacion(Producto producto, Lote lote)
/*  925:     */   {
/*  926:1136 */     MovimientoInventario transformacion = null;
/*  927:     */     
/*  928:1138 */     StringBuilder sql = new StringBuilder();
/*  929:1139 */     sql.append(" SELECT mi ");
/*  930:1140 */     sql.append(" FROM MovimientoInventario mi ");
/*  931:1141 */     sql.append(" INNER JOIN mi.productoTerminadoTransformacion ptt ");
/*  932:1142 */     sql.append(" LEFT JOIN mi.loteTransformacion lt ");
/*  933:1143 */     sql.append(" INNER JOIN mi.documento do ");
/*  934:1144 */     sql.append(" WHERE ptt.idProducto =:idProducto ");
/*  935:1145 */     sql.append(" AND do.documentoBase =:documentoBaseTransformacion ");
/*  936:1146 */     sql.append(" AND do.operacion = -1 ");
/*  937:1147 */     if (lote == null) {
/*  938:1148 */       sql.append(" AND lt IS NULL ");
/*  939:     */     } else {
/*  940:1150 */       sql.append(" AND lt.idLote = :idLote ");
/*  941:     */     }
/*  942:1152 */     sql.append(" ORDER BY mi.fecha DESC ");
/*  943:     */     
/*  944:1154 */     Query query = this.em.createQuery(sql.toString());
/*  945:1155 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  946:1156 */     query.setParameter("documentoBaseTransformacion", DocumentoBase.TRANSFORMACION_PRODUCTO);
/*  947:1157 */     if (lote != null) {
/*  948:1158 */       query.setParameter("idLote", Integer.valueOf(lote.getId()));
/*  949:     */     }
/*  950:1160 */     List<MovimientoInventario> lista = query.getResultList();
/*  951:1161 */     if (lista.size() > 0) {
/*  952:1162 */       transformacion = (MovimientoInventario)lista.get(0);
/*  953:     */     }
/*  954:1165 */     return transformacion;
/*  955:     */   }
/*  956:     */   
/*  957:     */   public void eliminarDetalleOrdenSalidaMaterial(MovimientoInventario movimientoInventario)
/*  958:     */   {
/*  959:1171 */     StringBuilder sql = new StringBuilder();
/*  960:1172 */     sql.append(" UPDATE DetalleMovimientoInventario dmi SET dmi.detalleOrdenSalidaMaterial = null WHERE dmi.movimientoInventario = :mi");
/*  961:     */     
/*  962:1174 */     Query query = this.em.createQuery(sql.toString());
/*  963:1175 */     query.setParameter("mi", movimientoInventario);
/*  964:     */     
/*  965:1177 */     query.executeUpdate();
/*  966:     */   }
/*  967:     */   
/*  968:     */   public void actualizarCantidadDevueltaDetalle(DetalleMovimientoInventario dmi, BigDecimal cantidad)
/*  969:     */     throws AS2ExceptionMantenimiento
/*  970:     */   {
/*  971:1184 */     BigDecimal cantidadPorDevolver = dmi.getCantidadPorDevolver();
/*  972:1185 */     if (cantidadPorDevolver.subtract(cantidad).compareTo(BigDecimal.ZERO) < 0) {
/*  973:1186 */       throw new AS2ExceptionMantenimiento("msg_error_cantidad_devuelta_supera", new String[] { cantidad + " > " + cantidadPorDevolver });
/*  974:     */     }
/*  975:1189 */     StringBuilder sql2 = new StringBuilder();
/*  976:1190 */     sql2.append(" UPDATE DetalleMovimientoInventario dmi SET dmi.cantidadDevuelta = dmi.cantidadDevuelta + :cantidad ");
/*  977:1191 */     sql2.append(" WHERE dmi.idDetalleMovimientoInventario = :idDetalleMovimientoInventario ");
/*  978:     */     
/*  979:1193 */     Query query2 = this.em.createQuery(sql2.toString());
/*  980:1194 */     query2.setParameter("cantidad", cantidad);
/*  981:1195 */     query2.setParameter("idDetalleMovimientoInventario", Integer.valueOf(dmi.getId()));
/*  982:     */     
/*  983:1197 */     query2.executeUpdate();
/*  984:     */   }
/*  985:     */   
/*  986:     */   public List<MovimientoInventario> obtenerIngresoFabricacionDelMes(Date fecha, OrdenFabricacion ordenFabricacion)
/*  987:     */   {
/*  988:1203 */     Date fechaDesde = FuncionesUtiles.getFechaInicioMes(fecha);
/*  989:1204 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(fecha);
/*  990:     */     
/*  991:1206 */     StringBuilder sql = new StringBuilder();
/*  992:1207 */     sql.append(" SELECT mi FROM MovimientoInventario mi ");
/*  993:1208 */     sql.append(" INNER JOIN FETCH mi.ordenFabricacion ofa ");
/*  994:1209 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  995:1210 */     sql.append(" AND mi.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  996:     */     
/*  997:1212 */     Query query = this.em.createQuery(sql.toString());
/*  998:1213 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  999:1214 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1000:1215 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1001:     */     
/* 1002:1217 */     return query.getResultList();
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public DetalleMovimientoInventario obtenerDetalleRecepcionFabricacion(int idDetalleMovimientoInventario)
/* 1006:     */   {
/* 1007:1222 */     StringBuilder sql = new StringBuilder();
/* 1008:1223 */     sql.append(" SELECT dmi ");
/* 1009:1224 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/* 1010:1225 */     sql.append(" INNER JOIN FETCH dmi.movimientoInventario mi ");
/* 1011:1226 */     sql.append(" LEFT JOIN FETCH dmi.bodegaDestino bd ");
/* 1012:1227 */     sql.append(" LEFT JOIN FETCH dmi.destinoCosto dc ");
/* 1013:1228 */     sql.append(" LEFT JOIN FETCH dmi.lote lo ");
/* 1014:1229 */     sql.append(" INNER JOIN FETCH mi.documento do ");
/* 1015:1230 */     sql.append(" INNER JOIN FETCH dmi.producto p ");
/* 1016:1231 */     sql.append(" LEFT JOIN FETCH dmi.inventarioProducto ip ");
/* 1017:1232 */     sql.append(" INNER JOIN FETCH p.unidad u ");
/* 1018:1233 */     sql.append(" LEFT JOIN FETCH p.unidadVenta uv ");
/* 1019:1234 */     sql.append(" LEFT JOIN FETCH p.unidadAlmacenamiento ua ");
/* 1020:1235 */     sql.append(" INNER JOIN FETCH dmi.unidadConversion uc ");
/* 1021:1236 */     sql.append(" WHERE dmi.idDetalleMovimientoInventario =:idDetalleMovimientoInventario");
/* 1022:     */     
/* 1023:1238 */     Query query = this.em.createQuery(sql.toString());
/* 1024:1239 */     query.setParameter("idDetalleMovimientoInventario", Integer.valueOf(idDetalleMovimientoInventario));
/* 1025:     */     
/* 1026:1241 */     return (DetalleMovimientoInventario)query.getSingleResult();
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public HashMap<Integer, BigDecimal> obtenerTotalTransferenciasPorProductoPorFecha(Producto producto, Date fechaInicio, Date fechaFin, List<Bodega> listaBodegaTrabajo)
/* 1030:     */   {
/* 1031:1246 */     HashMap<Integer, BigDecimal> mpTransferencias = new HashMap();
/* 1032:     */     
/* 1033:1248 */     StringBuilder sql = new StringBuilder();
/* 1034:1249 */     sql.append(" SELECT p.idProducto, COALESCE(SUM(ip.cantidad),0) ");
/* 1035:1250 */     sql.append(" FROM InventarioProducto ip ");
/* 1036:1251 */     sql.append(" INNER JOIN ip.producto p ");
/* 1037:1252 */     sql.append(" INNER JOIN ip.bodega b ");
/* 1038:1253 */     sql.append(" INNER JOIN ip.detalleMovimientoInventario dmi ");
/* 1039:1254 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 1040:1255 */     sql.append(" INNER JOIN mi.documento doc ");
/* 1041:1256 */     sql.append(" WHERE (mi.estado = :estadoElaborado OR mi.estado = :estadoProcesado OR mi.estado = :estadoContabilizado)");
/* 1042:1257 */     sql.append(" AND mi.fecha >= :fechaInicio ");
/* 1043:1258 */     sql.append(" AND mi.fecha <= :fechaFin ");
/* 1044:1259 */     sql.append(" AND doc.documentoBase = :documentoBase ");
/* 1045:1260 */     if (producto != null) {
/* 1046:1261 */       sql.append(" AND p.idProducto = :idProducto ");
/* 1047:     */     }
/* 1048:1263 */     if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0)) {
/* 1049:1264 */       sql.append(" AND b IN (:listaBodegaTrabajo)");
/* 1050:     */     }
/* 1051:1266 */     sql.append(" GROUP BY p.idProducto");
/* 1052:     */     
/* 1053:1268 */     Query query = this.em.createQuery(sql.toString());
/* 1054:1270 */     if (producto != null) {
/* 1055:1271 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 1056:     */     }
/* 1057:1273 */     query.setParameter("fechaInicio", fechaInicio);
/* 1058:1274 */     query.setParameter("fechaFin", fechaFin);
/* 1059:1275 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 1060:1276 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 1061:1277 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 1062:1278 */     query.setParameter("documentoBase", DocumentoBase.TRANSFERENCIA_BODEGA);
/* 1063:1279 */     if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0)) {
/* 1064:1280 */       query.setParameter("listaBodegaTrabajo", listaBodegaTrabajo);
/* 1065:     */     }
/* 1066:1283 */     List<Object[]> listaTransferencias = query.getResultList();
/* 1067:1284 */     for (Object[] objects : listaTransferencias) {
/* 1068:1285 */       mpTransferencias.put((Integer)objects[0], (BigDecimal)objects[1]);
/* 1069:     */     }
/* 1070:1287 */     return mpTransferencias;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public List<DetalleMovimientoInventario> autocompletarIngresosFabricacion(int idOrganizacion, String cadena, CategoriaProducto categoriaProducto)
/* 1074:     */   {
/* 1075:1302 */     cadena = cadena == null ? "" : cadena.trim();
/* 1076:     */     
/* 1077:1304 */     StringBuilder sql = new StringBuilder();
/* 1078:1305 */     sql.append(" SELECT dmi FROM DetalleMovimientoInventario dmi");
/* 1079:1306 */     sql.append(" INNER JOIN FETCH dmi.movimientoInventario mi");
/* 1080:1307 */     sql.append(" LEFT JOIN FETCH dmi.unidadConversion uc ");
/* 1081:1308 */     sql.append(" LEFT JOIN FETCH dmi.bodegaOrigen bo ");
/* 1082:1309 */     sql.append(" LEFT  JOIN FETCH dmi.lote l");
/* 1083:1310 */     sql.append(" INNER JOIN FETCH mi.documento doc");
/* 1084:1311 */     sql.append(" INNER JOIN FETCH mi.ordenFabricacion ofa");
/* 1085:1312 */     sql.append(" INNER JOIN FETCH ofa.producto p ");
/* 1086:1313 */     sql.append(" LEFT JOIN FETCH ofa.sucursal s");
/* 1087:1314 */     sql.append(" LEFT JOIN FETCH p.subcategoriaProducto scp ");
/* 1088:1315 */     sql.append(" LEFT JOIN FETCH scp.categoriaProducto cp ");
/* 1089:1316 */     sql.append(" LEFT JOIN FETCH p.unidad u ");
/* 1090:1317 */     sql.append(" LEFT JOIN FETCH p.unidadCompra uc ");
/* 1091:1318 */     sql.append(" LEFT JOIN FETCH p.unidadVenta uv ");
/* 1092:1319 */     sql.append(" LEFT JOIN FETCH p.unidadAlmacenamiento ua ");
/* 1093:1320 */     sql.append(" LEFT JOIN FETCH p.unidadInformativa ui ");
/* 1094:1321 */     sql.append(" WHERE ofa.idOrganizacion=:idOrganizacion");
/* 1095:1322 */     sql.append(" AND ofa.estado != :estadoAnulado");
/* 1096:1323 */     sql.append(" AND ofa.fechaLanzamiento IS NOT NULL");
/* 1097:1324 */     sql.append(" AND ofa.fechaCierre IS NULL");
/* 1098:1325 */     sql.append(" AND p.indicadorControlCalidad = true ");
/* 1099:1326 */     sql.append(" AND dmi.indicadorCumpleCalidad = false ");
/* 1100:1327 */     sql.append(" AND NOT EXISTS (SELECT hc FROM HistoricoCalidadOrdenFabricacion hc WHERE hc.detalleIngresoFabricacion = dmi) ");
/* 1101:1329 */     if (cadena.length() > 0)
/* 1102:     */     {
/* 1103:1330 */       sql.append(" AND ( ofa.numero LIKE :cadena ");
/* 1104:1331 */       sql.append(" OR p.nombre LIKE :cadena");
/* 1105:1332 */       sql.append(" OR p.codigo LIKE :cadena");
/* 1106:1333 */       sql.append(" OR ofa.descripcion LIKE :cadena)");
/* 1107:     */     }
/* 1108:1335 */     if ((categoriaProducto != null) && (categoriaProducto.getId() != 0)) {
/* 1109:1336 */       sql.append(" AND cp.idCategoriaProducto =:idCategoriaProducto");
/* 1110:     */     }
/* 1111:1339 */     sql.append(" ORDER BY ofa.numero");
/* 1112:     */     
/* 1113:1341 */     Query query = this.em.createQuery(sql.toString());
/* 1114:1342 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1115:1343 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/* 1116:1344 */     if ((categoriaProducto != null) && (categoriaProducto.getId() != 0)) {
/* 1117:1345 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/* 1118:     */     }
/* 1119:1348 */     if (cadena.length() > 0) {
/* 1120:1349 */       query.setParameter("cadena", "%" + cadena + "%");
/* 1121:     */     }
/* 1122:1352 */     return query.getResultList();
/* 1123:     */   }
/* 1124:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MovimientoInventarioDao
 * JD-Core Version:    0.7.0.1
 */