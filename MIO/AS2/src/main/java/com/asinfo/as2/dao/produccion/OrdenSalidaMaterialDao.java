/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   5:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*   6:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*   7:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   9:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.ProductoMaterial;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*  14:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  17:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  18:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  19:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*  21:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  22:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.Iterator;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import javax.ejb.Stateless;
/*  30:    */ import javax.persistence.EntityManager;
/*  31:    */ import javax.persistence.NoResultException;
/*  32:    */ import javax.persistence.Query;
/*  33:    */ import javax.persistence.TemporalType;
/*  34:    */ import javax.persistence.TypedQuery;
/*  35:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  36:    */ import javax.persistence.criteria.CriteriaQuery;
/*  37:    */ import javax.persistence.criteria.Expression;
/*  38:    */ import javax.persistence.criteria.Fetch;
/*  39:    */ import javax.persistence.criteria.JoinType;
/*  40:    */ import javax.persistence.criteria.Order;
/*  41:    */ import javax.persistence.criteria.Predicate;
/*  42:    */ import javax.persistence.criteria.Root;
/*  43:    */ 
/*  44:    */ @Stateless
/*  45:    */ public class OrdenSalidaMaterialDao
/*  46:    */   extends AbstractDaoAS2<OrdenSalidaMaterial>
/*  47:    */ {
/*  48:    */   public OrdenSalidaMaterialDao()
/*  49:    */   {
/*  50: 65 */     super(OrdenSalidaMaterial.class);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public OrdenSalidaMaterial cargarDetalle(int idOrdenSalidaMaterial)
/*  54:    */   {
/*  55: 72 */     return cargarDetalle(idOrdenSalidaMaterial, null);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public OrdenSalidaMaterial cargarDetalle(int idOrdenSalidaMaterial, Producto productoDetalle)
/*  59:    */   {
/*  60: 78 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  61: 79 */     CriteriaQuery<OrdenSalidaMaterial> cqCabecera = criteriaBuilder.createQuery(OrdenSalidaMaterial.class);
/*  62: 80 */     Root<OrdenSalidaMaterial> fromCabecera = cqCabecera.from(OrdenSalidaMaterial.class);
/*  63: 81 */     fromCabecera.fetch("documento", JoinType.LEFT);
/*  64: 82 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  65: 83 */     fromCabecera.fetch("transportista", JoinType.LEFT);
/*  66: 84 */     fromCabecera.fetch("chofer", JoinType.LEFT);
/*  67: 85 */     fromCabecera.fetch("ruta", JoinType.LEFT);
/*  68: 86 */     fromCabecera.fetch("bodegaOrigen", JoinType.LEFT);
/*  69: 87 */     Fetch<Object, Object> vehiculo = fromCabecera.fetch("vehiculo", JoinType.LEFT);
/*  70: 88 */     vehiculo.fetch("tipoVehiculo", JoinType.LEFT);
/*  71: 89 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idOrdenSalidaMaterial"), Integer.valueOf(idOrdenSalidaMaterial)));
/*  72: 90 */     CriteriaQuery<OrdenSalidaMaterial> select = cqCabecera.select(fromCabecera);
/*  73:    */     
/*  74: 92 */     OrdenSalidaMaterial ordenSalidaMaterial = (OrdenSalidaMaterial)this.em.createQuery(select).getSingleResult();
/*  75: 93 */     this.em.detach(ordenSalidaMaterial);
/*  76:    */     
/*  77:    */ 
/*  78: 96 */     CriteriaQuery<DetalleOrdenSalidaMaterial> cqDetalle = criteriaBuilder.createQuery(DetalleOrdenSalidaMaterial.class);
/*  79: 97 */     Root<DetalleOrdenSalidaMaterial> fromDetalle = cqDetalle.from(DetalleOrdenSalidaMaterial.class);
/*  80: 98 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  81: 99 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  82:100 */     producto.fetch("unidad", JoinType.LEFT);
/*  83:101 */     producto.fetch("unidadVenta", JoinType.LEFT);
/*  84:102 */     producto.fetch("unidadCompra", JoinType.LEFT);
/*  85:103 */     producto.fetch("unidadAlmacenamiento", JoinType.LEFT);
/*  86:104 */     producto.fetch("unidadInformativa", JoinType.LEFT);
/*  87:105 */     fromDetalle.fetch("unidad", JoinType.LEFT);
/*  88:106 */     fromDetalle.fetch("ordenFabricacion", JoinType.LEFT).fetch("ordenFabricacionPadre", JoinType.LEFT);
/*  89:107 */     fromDetalle.fetch("bodega", JoinType.LEFT);
/*  90:108 */     fromDetalle.fetch("destinoCosto", JoinType.LEFT);
/*  91:109 */     fromDetalle.fetch("lote", JoinType.LEFT);
/*  92:    */     
/*  93:111 */     cqDetalle.where(criteriaBuilder.equal(fromDetalle.get("ordenSalidaMaterial"), ordenSalidaMaterial));
/*  94:112 */     if (productoDetalle != null) {
/*  95:113 */       cqDetalle.where(criteriaBuilder.and(criteriaBuilder.equal(fromDetalle.get("ordenSalidaMaterial"), ordenSalidaMaterial), criteriaBuilder
/*  96:114 */         .equal(fromDetalle.get("producto"), productoDetalle)));
/*  97:    */     }
/*  98:116 */     CriteriaQuery<DetalleOrdenSalidaMaterial> selectDetalle = cqDetalle.select(fromDetalle);
/*  99:117 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idDetalleOrdenSalidaMaterial")) });
/* 100:    */     
/* 101:119 */     List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterial = this.em.createQuery(selectDetalle).getResultList();
/* 102:120 */     ordenSalidaMaterial.setListaDetalleOrdenSalidaMaterial(listaDetalleOrdenSalidaMaterial);
/* 103:122 */     for (Iterator localIterator1 = listaDetalleOrdenSalidaMaterial.iterator(); localIterator1.hasNext();)
/* 104:    */     {
/* 105:122 */       detalle = (DetalleOrdenSalidaMaterial)localIterator1.next();
/* 106:123 */       this.em.detach(detalle);
/* 107:124 */       detalle.setOrdenSalidaMaterial(ordenSalidaMaterial);
/* 108:    */       
/* 109:    */ 
/* 110:127 */       CriteriaQuery<LecturaBalanza> cqLecturaBalanza = criteriaBuilder.createQuery(LecturaBalanza.class);
/* 111:128 */       Root<LecturaBalanza> fromLecturaBalanza = cqLecturaBalanza.from(LecturaBalanza.class);
/* 112:129 */       fromLecturaBalanza.fetch("producto", JoinType.INNER);
/* 113:130 */       fromLecturaBalanza.fetch("unidadManejo", JoinType.LEFT);
/* 114:131 */       fromLecturaBalanza.fetch("pallet", JoinType.LEFT);
/* 115:    */       
/* 116:133 */       cqLecturaBalanza.where(criteriaBuilder.equal(fromLecturaBalanza.join("detalleOrdenSalidaMaterial"), detalle));
/* 117:134 */       CriteriaQuery<LecturaBalanza> selectLecturaBalanza = cqLecturaBalanza.select(fromLecturaBalanza);
/* 118:    */       
/* 119:136 */       List<LecturaBalanza> listaLecturaBalanza = this.em.createQuery(selectLecturaBalanza).getResultList();
/* 120:137 */       detalle.setListaLecturaBalanza(listaLecturaBalanza);
/* 121:139 */       for (LecturaBalanza lb : detalle.getListaLecturaBalanza())
/* 122:    */       {
/* 123:140 */         this.em.detach(lb);
/* 124:141 */         lb.setDetalleOrdenSalidaMaterial(detalle);
/* 125:    */       }
/* 126:146 */       Object cqDSMOF = criteriaBuilder.createQuery(DetalleOrdenSalidaMaterialOrdenFabricacion.class);
/* 127:147 */       Root<DetalleOrdenSalidaMaterialOrdenFabricacion> fromDSMOF = ((CriteriaQuery)cqDSMOF).from(DetalleOrdenSalidaMaterialOrdenFabricacion.class);
/* 128:148 */       fromDSMOF.fetch("ordenFabricacion", JoinType.INNER).fetch("producto", JoinType.LEFT);
/* 129:    */       
/* 130:150 */       ((CriteriaQuery)cqDSMOF).where(criteriaBuilder.equal(fromDSMOF.join("detalleOrdenSalidaMaterial"), detalle));
/* 131:151 */       CriteriaQuery<DetalleOrdenSalidaMaterialOrdenFabricacion> selectDSMOF = ((CriteriaQuery)cqDSMOF).select(fromDSMOF);
/* 132:    */       
/* 133:153 */       List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDSMOF = this.em.createQuery(selectDSMOF).getResultList();
/* 134:154 */       detalle.setListaDetalleOrdenSalidaMaterialOrdenFabricacion(listaDSMOF);
/* 135:156 */       for (DetalleOrdenSalidaMaterialOrdenFabricacion dosmof : detalle.getListaDetalleOrdenSalidaMaterialOrdenFabricacion())
/* 136:    */       {
/* 137:157 */         this.em.detach(dosmof);
/* 138:158 */         dosmof.setDetalleOrdenSalidaMaterial(detalle);
/* 139:    */       }
/* 140:    */     }
/* 141:    */     DetalleOrdenSalidaMaterial detalle;
/* 142:163 */     Object cqOFOSM = criteriaBuilder.createQuery(OrdenFabricacionOrdenSalidaMaterial.class);
/* 143:164 */     Root<OrdenFabricacionOrdenSalidaMaterial> fromOFOSM = ((CriteriaQuery)cqOFOSM).from(OrdenFabricacionOrdenSalidaMaterial.class);
/* 144:    */     
/* 145:166 */     fromOFOSM.fetch("ordenSalidaMaterial", JoinType.INNER);
/* 146:167 */     Fetch<Object, Object> ordenFabricacion = fromOFOSM.fetch("ordenFabricacion", JoinType.INNER);
/* 147:168 */     ordenFabricacion.fetch("producto", JoinType.INNER).fetch("unidad", JoinType.INNER);
/* 148:169 */     ordenFabricacion.fetch("ordenFabricacionPadre", JoinType.LEFT);
/* 149:170 */     ((CriteriaQuery)cqOFOSM).where(criteriaBuilder.equal(fromOFOSM.get("ordenSalidaMaterial"), ordenSalidaMaterial));
/* 150:171 */     CriteriaQuery<OrdenFabricacionOrdenSalidaMaterial> selectOFOSM = ((CriteriaQuery)cqOFOSM).select(fromOFOSM);
/* 151:    */     
/* 152:173 */     List<OrdenFabricacionOrdenSalidaMaterial> listaOFOSM = this.em.createQuery(selectOFOSM).getResultList();
/* 153:174 */     ordenSalidaMaterial.setListaOrdenFabricacionOrdenSalidaMaterial(listaOFOSM);
/* 154:    */     
/* 155:176 */     return ordenSalidaMaterial;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<OrdenSalidaMaterial> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 159:    */   {
/* 160:187 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 161:188 */     CriteriaQuery<OrdenSalidaMaterial> criteriaQuery = criteriaBuilder.createQuery(OrdenSalidaMaterial.class);
/* 162:    */     
/* 163:190 */     Root<OrdenSalidaMaterial> from = criteriaQuery.from(OrdenSalidaMaterial.class);
/* 164:191 */     from.fetch("documento", JoinType.LEFT);
/* 165:192 */     from.fetch("sucursal", JoinType.LEFT);
/* 166:    */     
/* 167:194 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 168:    */     
/* 169:196 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 170:197 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 171:    */     
/* 172:199 */     CriteriaQuery<OrdenSalidaMaterial> select = criteriaQuery.select(from);
/* 173:    */     
/* 174:201 */     TypedQuery<OrdenSalidaMaterial> typedQuery = this.em.createQuery(select);
/* 175:202 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 176:    */     
/* 177:204 */     return typedQuery.getResultList();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<ProductoMaterial> getMateriales(Producto producto, Date fecha)
/* 181:    */   {
/* 182:210 */     StringBuilder sql = new StringBuilder();
/* 183:211 */     sql.append(" SELECT pm FROM ProductoMaterial pm");
/* 184:212 */     sql.append(" JOIN FETCH pm.producto pr");
/* 185:213 */     sql.append(" JOIN FETCH pm.material ma");
/* 186:214 */     sql.append(" JOIN FETCH ma.unidad un");
/* 187:215 */     sql.append(" WHERE pr = :producto");
/* 188:216 */     sql.append(" AND pm.fechaDesde <= :fecha)");
/* 189:217 */     sql.append(" AND (pm.fechaHasta IS NULL OR pm.fechaHasta <= :fecha)");
/* 190:218 */     sql.append(" ORDER BY pm.orden, pr.codigo ");
/* 191:    */     
/* 192:220 */     Query query = this.em.createQuery(sql.toString());
/* 193:221 */     query.setParameter("producto", producto);
/* 194:222 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 195:    */     
/* 196:224 */     return query.getResultList();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public List<OrdenSalidaMaterial> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 200:    */   {
/* 201:229 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 202:230 */     CriteriaQuery<OrdenSalidaMaterial> criteriaQuery = criteriaBuilder.createQuery(OrdenSalidaMaterial.class);
/* 203:231 */     Root<OrdenSalidaMaterial> from = criteriaQuery.from(OrdenSalidaMaterial.class);
/* 204:232 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 205:    */     
/* 206:234 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 207:235 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 208:    */     
/* 209:237 */     CriteriaQuery<OrdenSalidaMaterial> select = criteriaQuery.select(from);
/* 210:238 */     TypedQuery<OrdenSalidaMaterial> typedQuery = this.em.createQuery(select);
/* 211:    */     
/* 212:240 */     return typedQuery.getResultList();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void actualizarCantidadConsumida(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial, BigDecimal cantidad)
/* 216:    */   {
/* 217:250 */     StringBuilder sql = new StringBuilder();
/* 218:251 */     sql.append(" UPDATE DetalleOrdenSalidaMaterial dosm");
/* 219:252 */     sql.append(" SET dosm.cantidadUtilizada = dosm.cantidadUtilizada + :cantidad");
/* 220:253 */     sql.append(" WHERE dosm = :detalleOrdenSalidaMaterial");
/* 221:    */     
/* 222:255 */     Query query = this.em.createQuery(sql.toString());
/* 223:256 */     query.setParameter("detalleOrdenSalidaMaterial", detalleOrdenSalidaMaterial);
/* 224:257 */     query.setParameter("cantidad", cantidad.multiply(new BigDecimal(-1)));
/* 225:    */     
/* 226:259 */     query.executeUpdate();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(int idOrganizacion, String numero, Boolean indicadorTransferencia, Boolean indicadorAprobado)
/* 230:    */   {
/* 231:266 */     numero = numero == null ? "" : numero.trim();
/* 232:    */     
/* 233:268 */     StringBuilder sql = new StringBuilder();
/* 234:269 */     sql.append(" SELECT osm FROM OrdenSalidaMaterial osm");
/* 235:270 */     sql.append(" WHERE osm.idOrganizacion=:idOrganizacion");
/* 236:271 */     sql.append(" AND osm.estado = :estado");
/* 237:273 */     if (numero.length() > 0) {
/* 238:274 */       sql.append(" AND osm.numero LIKE :numero");
/* 239:    */     }
/* 240:276 */     if (indicadorTransferencia != null) {
/* 241:277 */       sql.append(" AND osm.indicadorTransferencia = :indicadorTransferencia");
/* 242:    */     }
/* 243:280 */     if (indicadorAprobado != null) {
/* 244:281 */       sql.append(" AND osm.aprobado = :indicadorAprobado");
/* 245:    */     }
/* 246:283 */     sql.append(" ORDER BY osm.numero DESC");
/* 247:    */     
/* 248:285 */     Query query = this.em.createQuery(sql.toString());
/* 249:286 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 250:287 */     query.setParameter("estado", Estado.ELABORADO);
/* 251:288 */     if (indicadorTransferencia != null) {
/* 252:289 */       query.setParameter("indicadorTransferencia", indicadorTransferencia);
/* 253:    */     }
/* 254:292 */     if (numero.length() > 0) {
/* 255:293 */       query.setParameter("numero", "%" + numero + "%");
/* 256:    */     }
/* 257:296 */     if (indicadorAprobado != null) {
/* 258:297 */       query.setParameter("indicadorAprobado", indicadorAprobado);
/* 259:    */     }
/* 260:299 */     return query.getResultList();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void abrirCerrarOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 264:    */   {
/* 265:304 */     StringBuilder sqll1 = new StringBuilder();
/* 266:305 */     sqll1.append(" SELECT COUNT(*) FROM DetalleOrdenSalidaMaterial dosm");
/* 267:306 */     sqll1.append(" JOIN dosm.ordenSalidaMaterial osm");
/* 268:307 */     sqll1.append(" WHERE osm = :ordenSalidaMaterial");
/* 269:308 */     sqll1.append(" AND (dosm.cantidad - dosm.cantidadUtilizada) > 0");
/* 270:    */     
/* 271:310 */     Query query1 = this.em.createQuery(sqll1.toString());
/* 272:311 */     query1.setParameter("ordenSalidaMaterial", ordenSalidaMaterial);
/* 273:    */     
/* 274:313 */     StringBuilder sqll2 = new StringBuilder();
/* 275:314 */     sqll2.append(" UPDATE OrdenSalidaMaterial osm SET osm.estado = :estado WHERE osm = :ordenSalidaMaterial");
/* 276:    */     
/* 277:316 */     Query query2 = this.em.createQuery(sqll2.toString());
/* 278:317 */     query2.setParameter("ordenSalidaMaterial", ordenSalidaMaterial);
/* 279:318 */     sqll2.append(" SET osm.estado = :estado");
/* 280:320 */     if (((Long)query1.getSingleResult()).longValue() == 0L) {
/* 281:321 */       query2.setParameter("estado", Estado.PROCESADO);
/* 282:    */     } else {
/* 283:323 */       query2.setParameter("estado", Estado.ELABORADO);
/* 284:    */     }
/* 285:326 */     query2.executeUpdate();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<LecturaBalanza> getLecturaBalanzaPorRecibir(int idOrganizacion, String usuarioCreacion, List<Integer> idSucursales)
/* 289:    */   {
/* 290:332 */     StringBuilder sql = new StringBuilder();
/* 291:333 */     sql.append(" SELECT lb FROM LecturaBalanza lb ");
/* 292:334 */     sql.append(" INNER JOIN FETCH lb.producto pr");
/* 293:335 */     sql.append(" LEFT JOIN FETCH lb.detalleOrdenSalidaMaterial dosm ");
/* 294:336 */     sql.append(" LEFT JOIN FETCH dosm.ordenSalidaMaterial osm ");
/* 295:337 */     sql.append(" LEFT JOIN FETCH osm.sucursal s ");
/* 296:338 */     sql.append(" LEFT JOIN FETCH lb.unidadManejo um");
/* 297:339 */     sql.append(" LEFT JOIN FETCH lb.pallet p ");
/* 298:340 */     sql.append(" WHERE lb.indicadorRecibido = false ");
/* 299:341 */     sql.append(" AND lb.operacion = 1 ");
/* 300:342 */     sql.append(" AND lb.idOrganizacion = :idOrganizacion ");
/* 301:343 */     sql.append(" AND lb.detalleOrdenSalidaMaterial IS NOT NULL ");
/* 302:344 */     if (usuarioCreacion != null) {
/* 303:345 */       sql.append(" AND lb.usuarioCreacion = :usuarioCreacion");
/* 304:    */     }
/* 305:347 */     if ((idSucursales != null) && (idSucursales.size() > 0)) {
/* 306:348 */       sql.append(" AND lb.idSucursal in (:idSucursales)");
/* 307:    */     }
/* 308:351 */     sql.append(" ORDER BY lb.fechaCreacion  ");
/* 309:    */     
/* 310:353 */     Query query = this.em.createQuery(sql.toString());
/* 311:354 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 312:356 */     if (usuarioCreacion != null) {
/* 313:357 */       query.setParameter("usuarioCreacion", usuarioCreacion);
/* 314:    */     }
/* 315:359 */     if ((idSucursales != null) && (idSucursales.size() > 0)) {
/* 316:360 */       query.setParameter("idSucursales", idSucursales);
/* 317:    */     }
/* 318:363 */     return query.getResultList();
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean existeLecturaBalanzaSinRecibir(OrdenSalidaMaterial ordenSalidaMaterial)
/* 322:    */   {
/* 323:367 */     StringBuilder sql = new StringBuilder();
/* 324:368 */     sql.append(" SELECT COUNT(lb) FROM LecturaBalanza lb ");
/* 325:369 */     sql.append(" LEFT JOIN lb.detalleOrdenSalidaMaterial dosm ");
/* 326:370 */     sql.append(" LEFT JOIN dosm.ordenSalidaMaterial osm ");
/* 327:371 */     sql.append(" WHERE lb.indicadorRecibido = false ");
/* 328:372 */     sql.append(" AND lb.operacion = 1 ");
/* 329:373 */     sql.append(" AND osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial ");
/* 330:    */     
/* 331:375 */     Query query = this.em.createQuery(sql.toString());
/* 332:376 */     query.setParameter("idOrdenSalidaMaterial", Integer.valueOf(ordenSalidaMaterial.getId()));
/* 333:    */     
/* 334:378 */     Long cantidad = (Long)query.getSingleResult();
/* 335:379 */     return cantidad.longValue() > 0L;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void actualizarARecibidaLecturaBalanza(LecturaBalanza lecturaBalanza)
/* 339:    */   {
/* 340:384 */     StringBuilder sql = new StringBuilder();
/* 341:385 */     sql.append(" UPDATE LecturaBalanza lb ");
/* 342:386 */     sql.append(" SET lb.indicadorRecibido = true ");
/* 343:387 */     sql.append(" WHERE lb.idLecturaBalanza = :idLecturaBalanza ");
/* 344:    */     
/* 345:389 */     Query query = this.em.createQuery(sql.toString());
/* 346:390 */     query.setParameter("idLecturaBalanza", Integer.valueOf(lecturaBalanza.getId()));
/* 347:    */     
/* 348:392 */     query.executeUpdate();
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<OrdenFabricacion> getOrdenesNoFinalizadas(OrdenSalidaMaterial ordenSalida)
/* 352:    */   {
/* 353:398 */     StringBuilder sql = new StringBuilder();
/* 354:399 */     sql.append(" SELECT DISTINCT of1 FROM DetalleOrdenSalidaMaterialOrdenFabricacion dosmof");
/* 355:400 */     sql.append(" JOIN dosmof.ordenFabricacion of1");
/* 356:401 */     sql.append(" JOIN dosmof.detalleOrdenSalidaMaterial dosm");
/* 357:402 */     sql.append(" JOIN dosm.ordenSalidaMaterial os");
/* 358:403 */     sql.append(" WHERE of1.estado NOT IN(:listaEstados)");
/* 359:404 */     sql.append(" AND os = :ordenSalida");
/* 360:    */     
/* 361:406 */     List<EstadoProduccionEnum> listaEstados = new ArrayList();
/* 362:407 */     listaEstados.add(EstadoProduccionEnum.FINALIZADA);
/* 363:    */     
/* 364:409 */     Query query = this.em.createQuery(sql.toString());
/* 365:410 */     query.setParameter("ordenSalida", ordenSalida);
/* 366:411 */     query.setParameter("listaEstados", listaEstados);
/* 367:    */     
/* 368:413 */     return query.getResultList();
/* 369:    */   }
/* 370:    */   
/* 371:    */   public int reabrirOrden(OrdenSalidaMaterial ordenSalidaMaterial)
/* 372:    */   {
/* 373:417 */     StringBuilder sql = new StringBuilder();
/* 374:418 */     sql.append(" UPDATE OrdenSalidaMaterial osm ");
/* 375:419 */     sql.append(" SET osm.estado = :estadoElaborado ");
/* 376:420 */     sql.append(" WHERE osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial ");
/* 377:421 */     sql.append(" AND (osm.estado = :estadoCerrado OR osm.estado = :estadoProcesado )  ");
/* 378:    */     
/* 379:423 */     Query query = this.em.createQuery(sql.toString());
/* 380:424 */     query.setParameter("idOrdenSalidaMaterial", Integer.valueOf(ordenSalidaMaterial.getId()));
/* 381:425 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 382:426 */     query.setParameter("estadoCerrado", Estado.CERRADO);
/* 383:427 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 384:    */     
/* 385:429 */     return query.executeUpdate();
/* 386:    */   }
/* 387:    */   
/* 388:    */   public MovimientoInventario obtenerSalidaMaterialPorOrdenSalida(OrdenSalidaMaterial ordenSalidaMaterial)
/* 389:    */   {
/* 390:433 */     StringBuilder sql = new StringBuilder();
/* 391:434 */     sql.append(" SELECT DISTINCT sm FROM DetalleMovimientoInventario dsm ");
/* 392:435 */     sql.append(" INNER JOIN dsm.movimientoInventario sm ");
/* 393:436 */     sql.append(" INNER JOIN dsm.detalleOrdenSalidaMaterial dosm ");
/* 394:437 */     sql.append(" INNER JOIN dosm.ordenSalidaMaterial osm ");
/* 395:438 */     sql.append(" WHERE osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial ");
/* 396:    */     
/* 397:440 */     Query query = this.em.createQuery(sql.toString());
/* 398:441 */     query.setParameter("idOrdenSalidaMaterial", Integer.valueOf(ordenSalidaMaterial.getId()));
/* 399:442 */     query.setMaxResults(1);
/* 400:    */     try
/* 401:    */     {
/* 402:444 */       return (MovimientoInventario)query.getSingleResult();
/* 403:    */     }
/* 404:    */     catch (NoResultException e) {}
/* 405:447 */     return null;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void eliminarAsignacionOrdenFabricacion(OrdenFabricacion ordenFabricacion, OrdenSalidaMaterial ordenSalidaMaterial)
/* 409:    */   {
/* 410:452 */     StringBuilder sql = new StringBuilder();
/* 411:453 */     sql.append(" DELETE FROM OrdenFabricacionOrdenSalidaMaterial ofosm ");
/* 412:454 */     sql.append(" WHERE ofosm.ordenFabricacion = :ordenFabricacion ");
/* 413:455 */     if (ordenSalidaMaterial != null) {
/* 414:456 */       sql.append(" AND ofosm.ordenSalidaMaterial = :ordenSalidaMaterial ");
/* 415:    */     }
/* 416:459 */     Query query = this.em.createQuery(sql.toString());
/* 417:460 */     query.setParameter("ordenFabricacion", ordenFabricacion);
/* 418:461 */     if (ordenSalidaMaterial != null) {
/* 419:462 */       query.setParameter("ordenSalidaMaterial", ordenFabricacion);
/* 420:    */     }
/* 421:464 */     query.executeUpdate();
/* 422:    */   }
/* 423:    */   
/* 424:    */   public List<OrdenSalidaMaterial> getConsultaOrdenSalidaMaterial(Date fechaHasta, TipoCicloProduccionEnum tipoCiclo, Estado estado, boolean transferencia)
/* 425:    */   {
/* 426:471 */     StringBuilder sql = new StringBuilder();
/* 427:472 */     sql.append(" SELECT osm FROM OrdenSalidaMaterial osm ");
/* 428:473 */     sql.append(" WHERE osm.fecha <= :fechaHasta");
/* 429:474 */     sql.append(" AND   osm.indicadorTransferencia = :transferencia");
/* 430:475 */     if (estado != null) {
/* 431:476 */       sql.append(" AND  osm.estado = :estado ");
/* 432:    */     }
/* 433:478 */     if (tipoCiclo != null) {
/* 434:479 */       sql.append(" AND   osm.tipoCicloProduccionEnum = :tipoCiclo ");
/* 435:    */     }
/* 436:482 */     Query query = this.em.createQuery(sql.toString());
/* 437:483 */     query.setParameter("fechaHasta", fechaHasta);
/* 438:484 */     query.setParameter("transferencia", Boolean.valueOf(transferencia));
/* 439:485 */     if (estado != null) {
/* 440:486 */       query.setParameter("estado", estado);
/* 441:    */     }
/* 442:488 */     if (tipoCiclo != null) {
/* 443:489 */       query.setParameter("tipoCiclo", tipoCiclo);
/* 444:    */     }
/* 445:492 */     return query.getResultList();
/* 446:    */   }
/* 447:    */   
/* 448:    */   public List<OrdenSalidaMaterial> getOrdenSalidaMaterialPorOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 449:    */   {
/* 450:499 */     StringBuilder sql = new StringBuilder();
/* 451:500 */     sql.append(" SELECT osm FROM OrdenSalidaMaterial osm ");
/* 452:501 */     sql.append(" INNER JOIN osm.listaOrdenFabricacionOrdenSalidaMaterial ofosm ");
/* 453:502 */     sql.append(" INNER JOIN ofosm.ordenFabricacion ofa ");
/* 454:503 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion");
/* 455:    */     
/* 456:505 */     Query query = this.em.createQuery(sql.toString());
/* 457:506 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/* 458:    */     
/* 459:508 */     return query.getResultList();
/* 460:    */   }
/* 461:    */   
/* 462:    */   public List<OrdenFabricacionOrdenSalidaMaterial> getOrdenFabricacionOrdenSalidaMaterialPorOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 463:    */   {
/* 464:514 */     StringBuilder sql = new StringBuilder();
/* 465:515 */     sql.append(" SELECT ofosm FROM OrdenFabricacionOrdenSalidaMaterial ofosm ");
/* 466:516 */     sql.append(" JOIN FETCH ofosm.ordenFabricacion ofa ");
/* 467:517 */     sql.append(" JOIN FETCH ofa.producto p ");
/* 468:518 */     sql.append(" JOIN FETCH ofa.rutaFabricacion rf ");
/* 469:519 */     sql.append(" INNER JOIN ofosm.ordenSalidaMaterial osm ");
/* 470:520 */     sql.append(" WHERE osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial");
/* 471:    */     
/* 472:522 */     Query query = this.em.createQuery(sql.toString());
/* 473:523 */     query.setParameter("idOrdenSalidaMaterial", Integer.valueOf(ordenSalidaMaterial.getId()));
/* 474:    */     
/* 475:525 */     return query.getResultList();
/* 476:    */   }
/* 477:    */   
/* 478:    */   public List<OrdenSalidaMaterial> getOrdenSalidaMaterialPorAprobar(int idOrganizacion, Usuario usuarioEnSesion, Integer idOrdenSalidaMaterial, List<EntidadUsuario> listaSuperiores, List<EntidadUsuario> listaSubordinados, String numero, TipoCicloProduccionEnum tipoCicloProduccionEnum, String sucursal, Estado estado, String descripcion)
/* 479:    */     throws AS2Exception
/* 480:    */   {
/* 481:532 */     StringBuilder sql = new StringBuilder();
/* 482:533 */     sql.append("SELECT osm FROM OrdenSalidaMaterial osm");
/* 483:534 */     sql.append(" JOIN FETCH osm.sucursal s");
/* 484:535 */     sql.append(" WHERE osm.idOrganizacion= :idOrganizacion");
/* 485:536 */     sql.append(" AND osm.indicadorTransferencia = false");
/* 486:538 */     if ((numero == null) && (tipoCicloProduccionEnum == null) && (sucursal == null) && (estado == null) && (descripcion == null))
/* 487:    */     {
/* 488:539 */       sql.append(" AND osm.estado != :estadoAnulado");
/* 489:540 */       sql.append(" AND osm.estado != :estadoProcesado");
/* 490:541 */       sql.append(" AND osm.estado != :estadoCerrado");
/* 491:542 */       sql.append(" AND osm.aprobado = false");
/* 492:    */     }
/* 493:544 */     if ((numero != null) && (!numero.trim().isEmpty())) {
/* 494:545 */       sql.append(" AND osm.numero LIKE :numero");
/* 495:    */     }
/* 496:547 */     if (tipoCicloProduccionEnum != null) {
/* 497:548 */       sql.append(" AND osm.tipoCicloProduccionEnum = :tipoCicloProduccionEnum");
/* 498:    */     }
/* 499:550 */     if ((sucursal != null) && (!sucursal.trim().isEmpty())) {
/* 500:551 */       sql.append(" AND s.nombre LIKE :sucursal");
/* 501:    */     }
/* 502:553 */     if (estado != null) {
/* 503:554 */       sql.append(" AND osm.estado = :estado");
/* 504:    */     }
/* 505:556 */     if ((descripcion != null) && (!descripcion.trim().isEmpty())) {
/* 506:557 */       sql.append(" AND osm.descripcion LIKE :descripcion");
/* 507:    */     }
/* 508:560 */     if (idOrdenSalidaMaterial != null) {
/* 509:561 */       sql.append(" AND osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial");
/* 510:    */     }
/* 511:564 */     if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioEnSesion.getTipoVisualizacion())) {
/* 512:565 */       sql.append(" AND s.idSucursal IN (:listaIdSucursal)");
/* 513:    */     }
/* 514:567 */     if (usuarioEnSesion.isIndicadorAprobador())
/* 515:    */     {
/* 516:568 */       if ((listaSuperiores.isEmpty()) && (listaSubordinados.isEmpty())) {
/* 517:569 */         throw new AS2Exception("ERROR_NO_DEFINIDA_JERARQUIA_USUARIO_APROBADOR", new String[] { usuarioEnSesion.getNombreUsuario() });
/* 518:    */       }
/* 519:571 */       if (!listaSubordinados.isEmpty()) {
/* 520:573 */         sql.append(" AND osm.usuarioCreacion IN (:listaUsuarioSubordinados)");
/* 521:    */       } else {
/* 522:576 */         sql.append(" AND 1=2");
/* 523:    */       }
/* 524:    */     }
/* 525:580 */     Query query = this.em.createQuery(sql.toString());
/* 526:581 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 527:582 */     if ((numero == null) && (tipoCicloProduccionEnum == null) && (sucursal == null) && (estado == null) && (descripcion == null))
/* 528:    */     {
/* 529:583 */       query.setParameter("estadoAnulado", Estado.ANULADO);
/* 530:584 */       query.setParameter("estadoProcesado", Estado.CERRADO);
/* 531:585 */       query.setParameter("estadoCerrado", Estado.PROCESADO);
/* 532:    */     }
/* 533:587 */     if (idOrdenSalidaMaterial != null) {
/* 534:588 */       query.setParameter("idOrdenSalidaMaterial", idOrdenSalidaMaterial);
/* 535:    */     }
/* 536:590 */     if ((numero != null) && (!numero.trim().isEmpty())) {
/* 537:591 */       query.setParameter("numero", "%" + numero.trim() + "%");
/* 538:    */     }
/* 539:593 */     if (tipoCicloProduccionEnum != null) {
/* 540:594 */       query.setParameter("tipoCicloProduccionEnum", tipoCicloProduccionEnum);
/* 541:    */     }
/* 542:596 */     if ((sucursal != null) && (!sucursal.trim().isEmpty())) {
/* 543:597 */       query.setParameter("sucursal", "%" + sucursal.trim() + "%");
/* 544:    */     }
/* 545:599 */     if (estado != null) {
/* 546:600 */       query.setParameter("estado", estado);
/* 547:    */     }
/* 548:602 */     if ((descripcion != null) && (!descripcion.trim().isEmpty())) {
/* 549:603 */       query.setParameter("descripcion", "%" + descripcion.trim() + "%");
/* 550:    */     }
/* 551:606 */     if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioEnSesion.getTipoVisualizacion()))
/* 552:    */     {
/* 553:607 */       List<Integer> listaIdSucursal = new ArrayList();
/* 554:608 */       for (UsuarioSucursal usuarioSucursal : usuarioEnSesion.getListaUsuarioSucursal()) {
/* 555:609 */         listaIdSucursal.add(Integer.valueOf(usuarioSucursal.getSucursal().getId()));
/* 556:    */       }
/* 557:611 */       query.setParameter("listaIdSucursal", listaIdSucursal);
/* 558:    */     }
/* 559:613 */     if (usuarioEnSesion.isIndicadorAprobador()) {
/* 560:615 */       if (!listaSubordinados.isEmpty())
/* 561:    */       {
/* 562:616 */         List<String> listaUsuarioSubordinados = new ArrayList();
/* 563:617 */         for (EntidadUsuario entidadUsuario : listaSubordinados) {
/* 564:618 */           listaUsuarioSubordinados.add(entidadUsuario.getNombreUsuario());
/* 565:    */         }
/* 566:620 */         query.setParameter("listaUsuarioSubordinados", listaUsuarioSubordinados);
/* 567:    */       }
/* 568:    */     }
/* 569:624 */     return query.getResultList();
/* 570:    */   }
/* 571:    */   
/* 572:    */   public List<String> ordenSalidaMaterialEnConsumoBodega(OrdenSalidaMaterial ordenSalidaMaterial)
/* 573:    */   {
/* 574:629 */     StringBuilder sql = new StringBuilder();
/* 575:630 */     sql.append(" SELECT mi.numero ");
/* 576:631 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/* 577:632 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 578:633 */     sql.append(" INNER JOIN mi.documento d ");
/* 579:634 */     sql.append(" INNER JOIN dmi.detalleOrdenSalidaMaterial dosm ");
/* 580:635 */     sql.append(" WHERE dosm.ordenSalidaMaterial= :ordenSalidaMaterial ");
/* 581:636 */     sql.append(" AND d.documentoBase = :documentoBase ");
/* 582:637 */     sql.append(" GROUP BY mi.numero ");
/* 583:638 */     Query query = this.em.createQuery(sql.toString());
/* 584:639 */     query.setParameter("ordenSalidaMaterial", ordenSalidaMaterial);
/* 585:640 */     query.setParameter("documentoBase", DocumentoBase.CONSUMO_BODEGA);
/* 586:641 */     return query.getResultList();
/* 587:    */   }
/* 588:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao
 * JD-Core Version:    0.7.0.1
 */