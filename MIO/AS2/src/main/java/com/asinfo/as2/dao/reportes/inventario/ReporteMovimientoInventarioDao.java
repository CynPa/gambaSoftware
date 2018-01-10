/*   1:    */ package com.asinfo.as2.dao.reportes.inventario;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Lote;
/*  10:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.TomaFisica;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TemporalType;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class ReporteMovimientoInventarioDao
/*  30:    */   extends AbstractDaoAS2<MovimientoInventario>
/*  31:    */ {
/*  32:    */   public ReporteMovimientoInventarioDao()
/*  33:    */   {
/*  34: 44 */     super(MovimientoInventario.class);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List getReporteTransferenciaBodega(int idTransferenciaBodega)
/*  38:    */   {
/*  39: 55 */     StringBuilder sql = new StringBuilder();
/*  40: 56 */     sql.append("SELECT mi.numero, mi.fecha, mi.descripcion, ");
/*  41: 57 */     sql.append(" p.codigo, p.nombre, u.codigo, dmi.cantidad , bo.nombre, bd.nombre, dmi.descripcion, l.codigo, pr.nombre, mi.estado, ip.costo, coalesce(ipt.cantidad, dmi.cantidadPesada), a.numero, mi.usuarioCreacion ");
/*  42:    */     
/*  43: 59 */     sql.append(" , ui.nombre, dmi.cantidadUnidadInformativa, dmi.cantidadUnidadInformativaRecibida, p.codigoAlterno ");
/*  44: 60 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  45: 61 */     sql.append(" INNER JOIN dmi.inventarioProducto ip ");
/*  46: 62 */     sql.append(" LEFT JOIN ip.inventarioProductoTransferencia ipt ");
/*  47: 63 */     sql.append(" LEFT OUTER JOIN ip.lote l ");
/*  48: 64 */     sql.append(" LEFT OUTER JOIN dmi.movimientoInventario mi ");
/*  49: 65 */     sql.append(" LEFT OUTER JOIN mi.asiento a ");
/*  50: 66 */     sql.append(" LEFT OUTER JOIN dmi.producto p ");
/*  51: 67 */     sql.append(" LEFT OUTER JOIN p.unidad u ");
/*  52: 68 */     sql.append(" LEFT OUTER JOIN p.unidadInformativa ui ");
/*  53: 69 */     sql.append(" LEFT OUTER JOIN dmi.bodegaOrigen bo ");
/*  54: 70 */     sql.append(" LEFT OUTER JOIN dmi.bodegaDestino bd ");
/*  55: 71 */     sql.append(" LEFT OUTER JOIN mi.proyecto pr ");
/*  56: 72 */     sql.append(" WHERE mi.idMovimientoInventario = :idTransferenciaBodega ");
/*  57: 73 */     sql.append(" AND mi.estado <> :estadoAnulado ");
/*  58: 74 */     sql.append(" ORDER BY p.codigo asc ");
/*  59:    */     
/*  60: 76 */     Query query = this.em.createQuery(sql.toString()).setParameter("idTransferenciaBodega", Integer.valueOf(idTransferenciaBodega)).setParameter("estadoAnulado", Estado.ANULADO);
/*  61:    */     
/*  62: 78 */     return query.getResultList();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List getReporteConsumoBodega(int idConsumoBodega)
/*  66:    */   {
/*  67: 90 */     StringBuilder sql = new StringBuilder();
/*  68: 91 */     sql.append(" SELECT dmi.idDetalleMovimientoInventario, mi.numero, mi.fecha, mi.descripcion, ");
/*  69: 92 */     sql.append(" bo.nombre, p.codigo, p.nombre, lo.codigo, u.codigo, ip.costo, dmi.cantidad, ud.codigo, dmi.cantidadOrigen , dmi.descripcion, ");
/*  70: 93 */     sql.append(" dc.codigo, dc.nombre, dmi.cantidad, proy.nombre, osm.numero, rsm.nombres, rsm.apellidos ");
/*  71: 94 */     sql.append(" FROM  DetalleMovimientoInventario dmi ");
/*  72: 95 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  73: 96 */     sql.append(" LEFT JOIN dmi.destinoCosto dc ");
/*  74: 97 */     sql.append(" LEFT JOIN dmi.inventarioProducto ip ");
/*  75: 98 */     sql.append(" LEFT JOIN ip.lote lo ");
/*  76: 99 */     sql.append(" LEFT JOIN dmi.bodegaOrigen bo ");
/*  77:100 */     sql.append(" LEFT JOIN dmi.producto p ");
/*  78:101 */     sql.append(" LEFT JOIN dmi.unidadConversion ud ");
/*  79:102 */     sql.append(" LEFT JOIN p.unidad u ");
/*  80:103 */     sql.append(" LEFT JOIN mi.proyecto proy ");
/*  81:104 */     sql.append(" LEFT JOIN dmi.detalleOrdenSalidaMaterial dosm");
/*  82:105 */     sql.append(" LEFT JOIN dosm.ordenSalidaMaterial osm");
/*  83:106 */     sql.append(" LEFT JOIN mi.responsableSalidaMercaderia rsm ");
/*  84:107 */     sql.append(" WHERE mi.idMovimientoInventario = :idConsumoBodega ");
/*  85:    */     
/*  86:109 */     Query query = this.em.createQuery(sql.toString()).setParameter("idConsumoBodega", Integer.valueOf(idConsumoBodega));
/*  87:110 */     return query.getResultList();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List getReporteAjusteInventario(int idAjusteInventario)
/*  91:    */   {
/*  92:122 */     String sql = " SELECT dmi.idDetalleMovimientoInventario, mi.numero, mi.fecha, mi.descripcion,  bo.nombre, p.codigo, p.nombre, u.codigo, dmi.cantidad, ud.codigo, dmi.cantidadOrigen , dmi.descripcion, l.codigo,  ip.cantidad, ip.costo, mai.nombre, do.nombre, mi.usuarioCreacion  FROM  DetalleMovimientoInventario dmi  INNER JOIN dmi.movimientoInventario mi  INNER JOIN mi.documento do  INNER JOIN dmi.bodegaOrigen bo  INNER JOIN dmi.producto p  INNER JOIN dmi.unidadConversion ud  INNER JOIN p.unidad u  LEFT  JOIN dmi.inventarioProducto ip  LEFT  JOIN ip.lote l   LEFT JOIN mi.motivoAjusteInventario mai  WHERE mi.idMovimientoInventario = :idAjusteInventario  ORDER BY p.nombre ";
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:130 */     Query query = this.em.createQuery(sql).setParameter("idAjusteInventario", Integer.valueOf(idAjusteInventario));
/* 101:131 */     return query.getResultList();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List getReporteConsumoBodegaDestinoCostoResumido(Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, DimensionContable proyecto, Sucursal sucursal)
/* 105:    */   {
/* 106:145 */     StringBuffer sql = new StringBuffer();
/* 107:146 */     sql.append(" SELECT dc.codigo, dc.nombre, p.codigo, p.nombre, dmi.cantidad, ip.costo, cp.nombre, sp.nombre ");
/* 108:147 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/* 109:148 */     sql.append(" LEFT OUTER JOIN dmi.inventarioProducto ip ");
/* 110:149 */     sql.append(" LEFT JOIN dmi.destinoCosto dc ");
/* 111:150 */     sql.append(" INNER JOIN ip.producto p ");
/* 112:151 */     sql.append(" LEFT OUTER JOIN dmi.movimientoInventario mi ");
/* 113:152 */     sql.append(" INNER JOIN mi.documento d ");
/* 114:153 */     sql.append(" LEFT  JOIN mi.sucursal s ");
/* 115:154 */     if (proyecto != null) {
/* 116:155 */       sql.append(" LEFT JOIN mi.proyecto proy ");
/* 117:    */     }
/* 118:157 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 119:158 */     sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/* 120:159 */     sql.append(" WHERE mi.fecha between :fechaDesde AND :fechaHasta ");
/* 121:160 */     sql.append(" AND mi.idOrganizacion = :idOrganizacion ");
/* 122:161 */     if (sucursal != null) {
/* 123:162 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 124:    */     }
/* 125:164 */     sql.append(" AND d.documentoBase = :documentoBase  ");
/* 126:165 */     sql.append(" AND mi.estado <> :estado ");
/* 127:166 */     if (categoriaProducto.size() > 0) {
/* 128:167 */       sql.append(" AND cp IN :categoriaProducto ");
/* 129:    */     }
/* 130:169 */     if (subcategoriaProducto != null) {
/* 131:170 */       sql.append(" AND sp = :subcategoriaProducto ");
/* 132:    */     }
/* 133:172 */     if (producto != null) {
/* 134:173 */       sql.append(" AND p = :producto ");
/* 135:    */     }
/* 136:175 */     if (destinoCosto != null) {
/* 137:176 */       sql.append(" AND dc = :destinoCosto ");
/* 138:    */     }
/* 139:178 */     if (proyecto != null) {
/* 140:179 */       sql.append(" AND proy = :proyecto ");
/* 141:    */     }
/* 142:182 */     sql.append(" ORDER BY dc.codigo, p.codigo, cp.nombre, sp.nombre ");
/* 143:    */     
/* 144:184 */     Query query = this.em.createQuery(sql.toString());
/* 145:185 */     query.setParameter("fechaDesde", fechaDesde);
/* 146:186 */     query.setParameter("fechaHasta", fechaHasta);
/* 147:187 */     query.setParameter("estado", Estado.ANULADO);
/* 148:188 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 149:189 */     if (sucursal != null) {
/* 150:190 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/* 151:    */     }
/* 152:192 */     query.setParameter("documentoBase", DocumentoBase.CONSUMO_BODEGA);
/* 153:193 */     if (categoriaProducto.size() > 0) {
/* 154:194 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 155:    */     }
/* 156:196 */     if (subcategoriaProducto != null) {
/* 157:197 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 158:    */     }
/* 159:200 */     if (producto != null) {
/* 160:201 */       query.setParameter("producto", producto);
/* 161:    */     }
/* 162:203 */     if (destinoCosto != null) {
/* 163:204 */       query.setParameter("destinoCosto", destinoCosto);
/* 164:    */     }
/* 165:206 */     if (proyecto != null) {
/* 166:207 */       query.setParameter("proyecto", proyecto);
/* 167:    */     }
/* 168:210 */     return query.getResultList();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List getReporteConsumoBodegaDestinoCostoMensual(Date fechaDesde, Date fechaHasta, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, SubcategoriaProducto subcategoriaProducto, DimensionContable proyecto, Sucursal sucursal, Documento documento)
/* 172:    */   {
/* 173:217 */     StringBuffer sql = new StringBuffer();
/* 174:218 */     sql.append(" SELECT dc.codigo, dc.nombre, p.codigo, p.nombre, YEAR(mi.fecha), MONTH(mi.fecha), SUM(dmi.cantidad), SUM(ip.costo), proy.codigo");
/* 175:    */     
/* 176:220 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/* 177:221 */     sql.append(" INNER JOIN dmi.inventarioProducto ip ");
/* 178:222 */     sql.append(" INNER JOIN ip.producto p ");
/* 179:223 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 180:224 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 181:225 */     sql.append(" INNER JOIN mi.documento do ");
/* 182:226 */     sql.append(" LEFT JOIN mi.proyecto proy");
/* 183:227 */     sql.append(" LEFT JOIN dmi.destinoCosto dc ");
/* 184:228 */     sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/* 185:229 */     sql.append(" LEFT  JOIN mi.sucursal s ");
/* 186:230 */     sql.append(" WHERE mi.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 187:231 */     sql.append(" AND mi.idOrganizacion = :idOrganizacion ");
/* 188:232 */     if (sucursal != null) {
/* 189:233 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 190:    */     }
/* 191:235 */     sql.append(" AND do = :documento ");
/* 192:236 */     sql.append(" AND mi.estado <> :estado ");
/* 193:237 */     if (proyecto != null) {
/* 194:238 */       sql.append(" AND proy = :proyecto ");
/* 195:    */     }
/* 196:241 */     if (categoriaProducto.size() > 0) {
/* 197:242 */       sql.append(" AND cp IN :categoriaProducto ");
/* 198:    */     }
/* 199:244 */     if (subcategoriaProducto != null) {
/* 200:245 */       sql.append(" AND sp = :subcategoriaProducto ");
/* 201:    */     }
/* 202:247 */     if (producto != null) {
/* 203:248 */       sql.append(" AND p = :producto ");
/* 204:    */     }
/* 205:250 */     if (destinoCosto != null) {
/* 206:251 */       sql.append(" AND dc = :destinoCosto ");
/* 207:    */     }
/* 208:253 */     sql.append(" GROUP BY dc.codigo, dc.nombre, p.codigo, p.nombre, YEAR(mi.fecha), MONTH(mi.fecha), proy.codigo");
/* 209:254 */     sql.append(" ORDER BY dc.codigo, p.codigo, YEAR(mi.fecha), MONTH(mi.fecha) ");
/* 210:    */     
/* 211:256 */     Query query = this.em.createQuery(sql.toString());
/* 212:257 */     query.setParameter("fechaDesde", fechaDesde);
/* 213:258 */     query.setParameter("fechaHasta", fechaHasta);
/* 214:259 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 215:260 */     if (sucursal != null) {
/* 216:261 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/* 217:    */     }
/* 218:263 */     query.setParameter("estado", Estado.ANULADO);
/* 219:264 */     query.setParameter("documento", documento);
/* 220:265 */     if (categoriaProducto.size() > 0) {
/* 221:266 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 222:    */     }
/* 223:268 */     if (subcategoriaProducto != null) {
/* 224:269 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 225:    */     }
/* 226:271 */     if (producto != null) {
/* 227:272 */       query.setParameter("producto", producto);
/* 228:    */     }
/* 229:274 */     if (destinoCosto != null) {
/* 230:275 */       query.setParameter("destinoCosto", destinoCosto);
/* 231:    */     }
/* 232:277 */     if (proyecto != null) {
/* 233:278 */       query.setParameter("proyecto", proyecto);
/* 234:    */     }
/* 235:281 */     return query.getResultList();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List getReporteConsumoBodegaDestinoCostoDetallado(Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, DimensionContable proyecto, Sucursal sucursal, boolean resumido, Documento documento)
/* 239:    */   {
/* 240:296 */     StringBuffer sql = new StringBuffer();
/* 241:297 */     sql.append("SELECT mi.numero, mi.fecha,dc.codigo, dc.nombre, p.codigo, p.nombre, dmi.cantidad, ip.costo,");
/* 242:298 */     sql.append(" dmi.descripcion, proy.codigo, proy.descripcion, cp.nombre, sp.nombre, mi.descripcion, uni.codigo, uni.nombre ");
/* 243:299 */     sql.append(" FROM DetalleMovimientoInventario dmi");
/* 244:300 */     sql.append(" LEFT JOIN dmi.destinoCosto dc ");
/* 245:301 */     sql.append(" LEFT OUTER JOIN dmi.inventarioProducto ip ");
/* 246:302 */     sql.append(" INNER JOIN ip.producto p ");
/* 247:303 */     sql.append(" INNER JOIN dmi.unidadConversion uni ");
/* 248:304 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 249:305 */     sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/* 250:306 */     sql.append(" LEFT OUTER JOIN dmi.movimientoInventario mi ");
/* 251:307 */     sql.append(" INNER JOIN mi.documento d ");
/* 252:308 */     sql.append(" LEFT JOIN mi.proyecto proy ");
/* 253:309 */     sql.append(" LEFT  JOIN mi.sucursal s ");
/* 254:310 */     sql.append(" WHERE mi.fecha between :fechaDesde AND :fechaHasta ");
/* 255:311 */     sql.append(" AND mi.idOrganizacion = :idOrganizacion ");
/* 256:312 */     if (sucursal != null) {
/* 257:313 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 258:    */     }
/* 259:315 */     sql.append(" AND d = :documento  ");
/* 260:316 */     sql.append(" AND mi.estado <> :estado ");
/* 261:318 */     if (categoriaProducto.size() > 0) {
/* 262:319 */       sql.append(" AND cp IN :categoriaProducto ");
/* 263:    */     }
/* 264:321 */     if (subcategoriaProducto != null) {
/* 265:322 */       sql.append(" AND sp = :subcategoriaProducto ");
/* 266:    */     }
/* 267:324 */     if (producto != null) {
/* 268:325 */       sql.append(" AND p = :producto ");
/* 269:    */     }
/* 270:327 */     if (destinoCosto != null) {
/* 271:328 */       sql.append(" AND dc = :destinoCosto ");
/* 272:    */     }
/* 273:330 */     if (proyecto != null) {
/* 274:331 */       sql.append(" AND proy  = :proyecto ");
/* 275:    */     }
/* 276:334 */     if (resumido) {
/* 277:335 */       sql.append(" ORDER BY cp.nombre, sp.nombre, p.nombre, ip.fecha");
/* 278:    */     } else {
/* 279:337 */       sql.append(" ORDER BY proy.codigo, dc.codigo, cp.nombre, sp.nombre, ip.fecha, p.codigo");
/* 280:    */     }
/* 281:339 */     Query query = this.em.createQuery(sql.toString());
/* 282:340 */     query.setParameter("fechaDesde", fechaDesde);
/* 283:341 */     query.setParameter("fechaHasta", fechaHasta);
/* 284:342 */     query.setParameter("estado", Estado.ANULADO);
/* 285:343 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 286:344 */     if (sucursal != null) {
/* 287:345 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/* 288:    */     }
/* 289:347 */     query.setParameter("documento", documento);
/* 290:348 */     if (categoriaProducto.size() > 0) {
/* 291:349 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 292:    */     }
/* 293:351 */     if (subcategoriaProducto != null) {
/* 294:352 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 295:    */     }
/* 296:354 */     if (producto != null) {
/* 297:355 */       query.setParameter("producto", producto);
/* 298:    */     }
/* 299:357 */     if (destinoCosto != null) {
/* 300:358 */       query.setParameter("destinoCosto", destinoCosto);
/* 301:    */     }
/* 302:360 */     if (proyecto != null) {
/* 303:361 */       query.setParameter("proyecto", proyecto);
/* 304:    */     }
/* 305:364 */     return query.getResultList();
/* 306:    */   }
/* 307:    */   
/* 308:    */   public List getReporteConsumoBodegaDestinoCostoCategoria(Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, DimensionContable proyecto, Sucursal sucursal, Documento documento)
/* 309:    */   {
/* 310:372 */     StringBuffer sql = new StringBuffer();
/* 311:373 */     sql.append(" SELECT cp.nombre, sp.nombre , sum( ip.costo ), dc.codigo, dc.nombre  ");
/* 312:374 */     sql.append(" FROM DetalleMovimientoInventario dmi");
/* 313:375 */     sql.append(" LEFT JOIN dmi.destinoCosto dc ");
/* 314:376 */     sql.append(" LEFT OUTER JOIN dmi.inventarioProducto ip ");
/* 315:377 */     sql.append(" INNER JOIN ip.producto p ");
/* 316:378 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 317:379 */     sql.append(" LEFT JOIN sp.categoriaProducto cp ");
/* 318:380 */     sql.append(" LEFT OUTER JOIN dmi.movimientoInventario mi ");
/* 319:381 */     sql.append(" INNER JOIN mi.documento d ");
/* 320:382 */     sql.append(" LEFT JOIN mi.proyecto proy ");
/* 321:383 */     sql.append(" LEFT  JOIN mi.sucursal s ");
/* 322:384 */     sql.append(" WHERE mi.fecha between :fechaDesde AND :fechaHasta ");
/* 323:385 */     sql.append(" AND mi.idOrganizacion = :idOrganizacion ");
/* 324:386 */     if (sucursal != null) {
/* 325:387 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 326:    */     }
/* 327:389 */     sql.append(" AND d = :documento  ");
/* 328:390 */     sql.append(" AND mi.estado <> :estado ");
/* 329:392 */     if (categoriaProducto.size() > 0) {
/* 330:393 */       sql.append(" AND cp IN :categoriaProducto ");
/* 331:    */     }
/* 332:395 */     if (subcategoriaProducto != null) {
/* 333:396 */       sql.append(" AND sp = :subcategoriaProducto ");
/* 334:    */     }
/* 335:398 */     if (producto != null) {
/* 336:399 */       sql.append(" AND p = :producto ");
/* 337:    */     }
/* 338:401 */     if (destinoCosto != null) {
/* 339:402 */       sql.append(" AND dc = :destinoCosto ");
/* 340:    */     }
/* 341:404 */     if (proyecto != null) {
/* 342:405 */       sql.append(" AND proy  = :proyecto ");
/* 343:    */     }
/* 344:408 */     sql.append(" GROUP BY dc.codigo, dc.nombre, cp.nombre, sp.nombre  ");
/* 345:    */     
/* 346:410 */     Query query = this.em.createQuery(sql.toString());
/* 347:411 */     query.setParameter("fechaDesde", fechaDesde);
/* 348:412 */     query.setParameter("fechaHasta", fechaHasta);
/* 349:413 */     query.setParameter("estado", Estado.ANULADO);
/* 350:414 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 351:415 */     if (sucursal != null) {
/* 352:416 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/* 353:    */     }
/* 354:418 */     query.setParameter("documento", documento);
/* 355:419 */     if (categoriaProducto.size() > 0) {
/* 356:420 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 357:    */     }
/* 358:422 */     if (subcategoriaProducto != null) {
/* 359:423 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 360:    */     }
/* 361:425 */     if (producto != null) {
/* 362:426 */       query.setParameter("producto", producto);
/* 363:    */     }
/* 364:428 */     if (destinoCosto != null) {
/* 365:429 */       query.setParameter("destinoCosto", destinoCosto);
/* 366:    */     }
/* 367:431 */     if (proyecto != null) {
/* 368:432 */       query.setParameter("proyecto", proyecto);
/* 369:    */     }
/* 370:435 */     return query.getResultList();
/* 371:    */   }
/* 372:    */   
/* 373:    */   public List getReporteAjusteInventario(Date fechaDesde, Date fechaHasta, int idDocumento, int idMotivoAjusteInventario, int idOrganizacion, int idSubcategoriaProducto, int idBodega)
/* 374:    */   {
/* 375:449 */     StringBuilder sql = new StringBuilder();
/* 376:450 */     sql.append(" SELECT mi.numero, mi.fecha, mi.descripcion, p.codigo, p.nombreComercial, u.codigo, (dmi.cantidad * ip.operacion) , (ip.costo * ip.operacion), b.nombre, mai.nombre, d.nombre, p.nombre, p.codigoAlterno ");
/* 377:    */     
/* 378:452 */     sql.append(" FROM  DetalleMovimientoInventario dmi ");
/* 379:453 */     sql.append(" INNER JOIN dmi.producto p ");
/* 380:454 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 381:455 */     sql.append(" INNER JOIN dmi.bodegaOrigen   b ");
/* 382:456 */     sql.append(" LEFT  JOIN dmi.inventarioProducto   ip ");
/* 383:457 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 384:458 */     sql.append(" INNER JOIN dmi.unidadConversion     uc ");
/* 385:459 */     sql.append(" INNER JOIN p.unidad                 u  ");
/* 386:460 */     sql.append(" INNER JOIN mi.documento d ");
/* 387:461 */     sql.append(" INNER JOIN mi.motivoAjusteInventario mai ");
/* 388:462 */     sql.append(" WHERE mi.fecha BETWEEN :fechaDesde  AND :fechaHasta ");
/* 389:463 */     sql.append(" AND  (d.idDocumento=:idDocumento OR :idDocumento=0) ");
/* 390:464 */     sql.append(" AND  (b.idBodega=:idBodega OR :idBodega=0) ");
/* 391:465 */     sql.append(" AND  (mai.idMotivoAjusteInventario=:idMotivoAjusteInventario OR :idMotivoAjusteInventario=0) ");
/* 392:466 */     sql.append(" AND  (sp.idSubcategoriaProducto=:idSubcategoriaProducto OR :idSubcategoriaProducto=0) ");
/* 393:467 */     sql.append(" AND   mi.idOrganizacion=:idOrganizacion ");
/* 394:468 */     sql.append(" AND   mi.estado != :estadoAnulado ");
/* 395:469 */     sql.append(" ORDER BY p.nombreComercial, mi.numero ,mi.fecha ");
/* 396:    */     
/* 397:471 */     Query query = this.em.createQuery(sql.toString());
/* 398:472 */     query.setParameter("fechaDesde", fechaDesde);
/* 399:473 */     query.setParameter("fechaHasta", fechaHasta);
/* 400:474 */     query.setParameter("idDocumento", Integer.valueOf(idDocumento));
/* 401:475 */     query.setParameter("idBodega", Integer.valueOf(idBodega));
/* 402:476 */     query.setParameter("idMotivoAjusteInventario", Integer.valueOf(idMotivoAjusteInventario));
/* 403:477 */     query.setParameter("idSubcategoriaProducto", Integer.valueOf(idSubcategoriaProducto));
/* 404:478 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 405:479 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 406:    */     
/* 407:481 */     return query.getResultList();
/* 408:    */   }
/* 409:    */   
/* 410:    */   public List getReporteIngresoFabricacion(MovimientoInventario ingresoFabricacion, boolean imprimirOF)
/* 411:    */   {
/* 412:493 */     if (ingresoFabricacion == null) {
/* 413:494 */       return new ArrayList();
/* 414:    */     }
/* 415:496 */     StringBuilder sql = new StringBuilder();
/* 416:497 */     sql.append(" SELECT ofa.numero, pro.codigo, pro.nombre, dmi.fechaCreacion, dmi.cantidadOrigen, uc.nombre, dmi.cantidad, ");
/* 417:498 */     sql.append(" dmi.descripcion, lt.codigo, ui.nombre, dmi.cantidadUnidadInformativa, mi.numero ");
/* 418:499 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/* 419:500 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 420:501 */     sql.append(" INNER JOIN mi.ordenFabricacion ofa ");
/* 421:502 */     sql.append(" INNER JOIN ofa.producto pro ");
/* 422:503 */     sql.append(" INNER JOIN dmi.unidadConversion uc ");
/* 423:504 */     sql.append(" LEFT JOIN dmi.lote lt ");
/* 424:505 */     sql.append(" LEFT JOIN pro.unidadInformativa ui ");
/* 425:506 */     sql.append(" WHERE mi.idOrganizacion =:idOrganizacion ");
/* 426:507 */     if (imprimirOF) {
/* 427:508 */       sql.append(" AND mi.idMovimientoInventario =:idMovimientoInventario ");
/* 428:    */     }
/* 429:510 */     sql.append(" AND mi.fecha =:fecha ");
/* 430:511 */     sql.append(" ORDER BY ofa.numero, dmi.fechaCreacion ");
/* 431:    */     
/* 432:513 */     Query query = this.em.createQuery(sql.toString());
/* 433:514 */     query.setParameter("idOrganizacion", Integer.valueOf(ingresoFabricacion.getIdOrganizacion()));
/* 434:515 */     query.setParameter("fecha", ingresoFabricacion.getFecha());
/* 435:516 */     if (imprimirOF) {
/* 436:517 */       query.setParameter("idMovimientoInventario", Integer.valueOf(ingresoFabricacion.getIdMovimientoInventario()));
/* 437:    */     }
/* 438:520 */     return query.getResultList();
/* 439:    */   }
/* 440:    */   
/* 441:    */   public List getReporteOrdenSalidaMaterial(int idOrdenSalidaMaterial, boolean devolucion, Date fechaDesde, Date fechaHasta, Producto producto, Lote lote, boolean indicadorSoloCerradas, boolean dosmConsumoDirecto)
/* 442:    */   {
/* 443:532 */     StringBuilder sql = new StringBuilder();
/* 444:533 */     sql.append(" SELECT MAX(dosm.idDetalleOrdenSalidaMaterial), MAX(osm.numero), MAX(osm.fecha), MAX(osm.descripcion), p.codigo, p.nombre, u.nombre, ");
/* 445:    */     
/* 446:535 */     sql.append(" SUM(dosm.cantidad), SUM(dosm.cantidadDespachada), SUM(dosm.cantidadADevolver), SUM(dosm.cantidadDevuelta), SUM(dosm.cantidadUtilizada), MAX(dosm.descripcion), MAX(ofa.numero), ");
/* 447:    */     
/* 448:537 */     sql.append(" lot.codigo, ui.nombre, SUM(dosm.cantidadUnidadInformativaDespacho), SUM(dosm.cantidadUnidadInformativaDevolucion), SUM(dosm.cantidadDesecho), SUM(dosm.cantidadAdicional), dc.nombre  ");
/* 449:    */     
/* 450:539 */     sql.append(" FROM  DetalleOrdenSalidaMaterial dosm ");
/* 451:540 */     sql.append(" INNER JOIN dosm.ordenSalidaMaterial osm ");
/* 452:541 */     sql.append(" LEFT JOIN dosm.ordenFabricacion ofa ");
/* 453:542 */     sql.append(" LEFT JOIN dosm.producto p ");
/* 454:543 */     sql.append(" LEFT JOIN dosm.destinoCosto dc ");
/* 455:544 */     sql.append(" LEFT JOIN p.unidad u ");
/* 456:545 */     sql.append(" LEFT JOIN p.unidadInformativa ui ");
/* 457:546 */     sql.append(" LEFT JOIN dosm.lote lot ");
/* 458:549 */     if (idOrdenSalidaMaterial != 0)
/* 459:    */     {
/* 460:550 */       sql.append(" WHERE osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial ");
/* 461:551 */       if (devolucion) {
/* 462:552 */         sql.append(" AND dosm.indicadorConsumoDirecto = false ");
/* 463:553 */       } else if (dosmConsumoDirecto) {
/* 464:554 */         sql.append(" AND dosm.indicadorConsumoDirecto = true ");
/* 465:    */       }
/* 466:    */     }
/* 467:    */     else
/* 468:    */     {
/* 469:558 */       String condicion = " WHERE ";
/* 470:559 */       if (fechaDesde != null)
/* 471:    */       {
/* 472:560 */         sql.append(condicion + " osm.fecha >= :fechaDesde ");
/* 473:561 */         condicion = " AND ";
/* 474:    */       }
/* 475:563 */       if (fechaHasta != null)
/* 476:    */       {
/* 477:564 */         sql.append(condicion + " osm.fecha <= :fechaHasta ");
/* 478:565 */         condicion = " AND ";
/* 479:    */       }
/* 480:567 */       if (producto != null)
/* 481:    */       {
/* 482:568 */         sql.append(condicion + " p.idProducto = :idProducto ");
/* 483:569 */         condicion = " AND ";
/* 484:    */       }
/* 485:571 */       if (lote != null)
/* 486:    */       {
/* 487:572 */         sql.append(condicion + " lot.idLote = :idLote ");
/* 488:573 */         condicion = " AND ";
/* 489:    */       }
/* 490:575 */       if (indicadorSoloCerradas)
/* 491:    */       {
/* 492:576 */         sql.append(condicion + " osm.estado = :estadoCerrada ");
/* 493:577 */         condicion = " AND ";
/* 494:    */       }
/* 495:581 */       sql.append(condicion + " osm.indicadorTransferencia = false ");
/* 496:    */     }
/* 497:583 */     sql.append(" GROUP BY p.codigo, p.nombre, u.nombre, lot.codigo, ui.nombre, dc.nombre  ");
/* 498:    */     
/* 499:585 */     Query query = this.em.createQuery(sql.toString());
/* 500:586 */     if (idOrdenSalidaMaterial != 0) {
/* 501:587 */       query.setParameter("idOrdenSalidaMaterial", Integer.valueOf(idOrdenSalidaMaterial));
/* 502:    */     }
/* 503:589 */     if (fechaDesde != null) {
/* 504:590 */       query.setParameter("fechaDesde", fechaDesde);
/* 505:    */     }
/* 506:592 */     if (fechaHasta != null) {
/* 507:593 */       query.setParameter("fechaHasta", fechaHasta);
/* 508:    */     }
/* 509:595 */     if (producto != null) {
/* 510:596 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 511:    */     }
/* 512:598 */     if (lote != null) {
/* 513:599 */       query.setParameter("idLote", Integer.valueOf(lote.getId()));
/* 514:    */     }
/* 515:601 */     if (indicadorSoloCerradas) {
/* 516:602 */       query.setParameter("estadoCerrada", Estado.CERRADO);
/* 517:    */     }
/* 518:604 */     return query.getResultList();
/* 519:    */   }
/* 520:    */   
/* 521:    */   public List getReporteOrdenSalidaMaterialByOrdenFabricacion(int idOrdenFabricacion)
/* 522:    */   {
/* 523:615 */     String sql = "SELECT dosm.idDetalleOrdenSalidaMaterial, osm.numero, osm.fecha, osm.descripcion,  p.codigo, p.nombre, u.codigo, dosm.cantidad, dosm.descripcion, ofa.numero  FROM  DetalleOrdenSalidaMaterial dosm INNER JOIN dosm.ordenSalidaMaterial osm LEFT JOIN osm.ordenFabricacion ofa  INNER JOIN dosm.producto p INNER JOIN p.unidad u  WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ORDER BY p.nombre ";
/* 524:    */     
/* 525:    */ 
/* 526:    */ 
/* 527:    */ 
/* 528:    */ 
/* 529:621 */     Query query = this.em.createQuery(sql).setParameter("idOrdenFabricacion", Integer.valueOf(idOrdenFabricacion));
/* 530:622 */     return query.getResultList();
/* 531:    */   }
/* 532:    */   
/* 533:    */   public List<Object[]> getReporteTransferenciaInventario(int idOrganizacion, Documento documento, Date fechaDesde, Date fechaHasta, Bodega bodegaOrigen, Bodega bodegaDestino, SubcategoriaProducto subcategoriaProducto, Estado estado, DimensionContable proyecto, CategoriaProducto categoriaProducto)
/* 534:    */   {
/* 535:641 */     StringBuilder sql = new StringBuilder();
/* 536:642 */     sql.append(" SELECT mi.numero, mi.fecha, mi.descripcion, mi.estado, p.codigo, p.nombre,");
/* 537:643 */     sql.append(" p.nombreComercial, u.codigo, dmi.cantidad, ip.costo,");
/* 538:644 */     sql.append(" d.nombre, lo.codigo, bo.nombre, bd.nombre, mi.estado, pr.nombre, sp.codigo, sp.nombre, cp.codigo, cp.nombre ");
/* 539:645 */     sql.append(" FROM  DetalleMovimientoInventario dmi ");
/* 540:646 */     sql.append(" INNER JOIN dmi.producto p ");
/* 541:647 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 542:648 */     sql.append(" INNER JOIN sp.categoriaProducto cp");
/* 543:649 */     sql.append(" INNER JOIN dmi.bodegaOrigen   bo ");
/* 544:650 */     sql.append(" INNER JOIN dmi.bodegaDestino   bd ");
/* 545:651 */     sql.append(" INNER JOIN dmi.inventarioProducto   ip ");
/* 546:652 */     sql.append(" LEFT JOIN ip.lote lo ");
		/* 547:653 */ sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 548:654 */     sql.append(" INNER JOIN dmi.unidadConversion     uc ");
/* 549:655 */     sql.append(" INNER JOIN p.unidad                 u  ");
/* 550:656 */     sql.append(" INNER JOIN mi.documento d ");
/* 551:657 */     sql.append(" LEFT JOIN mi.proyecto pr ");
/* 552:658 */     sql.append(" WHERE mi.idOrganizacion=:idOrganizacion ");
/* 553:659 */     sql.append(" AND mi.fecha BETWEEN :fechaDesde  AND :fechaHasta ");
/* 554:660 */     sql.append(" AND mi.estado != :estadoAnulado ");
/* 555:662 */     if (documento != null) {
/* 556:663 */       sql.append(" AND d=:documento ");
/* 557:    */     }
/* 558:665 */     if (bodegaOrigen != null) {
/* 559:666 */       sql.append(" AND bo=:bodegaOrigen ");
/* 560:    */     }
/* 561:668 */     if (bodegaDestino != null) {
/* 562:669 */       sql.append(" AND bd=:bodegaDestino ");
/* 563:    */     }
/* 564:671 */     if (subcategoriaProducto != null) {
/* 565:672 */       sql.append(" AND sp=:subcategoriaProducto ");
/* 566:    */     }
/* 567:674 */     if (categoriaProducto != null) {
/* 568:675 */       sql.append(" AND cp=:categoriaProducto ");
/* 569:    */     }
/* 570:677 */     if (estado != null) {
/* 571:678 */       sql.append(" AND mi.estado=:estado ");
/* 572:    */     }
/* 573:680 */     if (proyecto != null) {
/* 574:681 */       sql.append(" AND mi.proyecto = :proyecto ");
/* 575:    */     }
/* 576:683 */     sql.append(" ORDER BY mi.numero ,mi.fecha, p.nombre ");
/* 577:    */     
/* 578:685 */     Query query = this.em.createQuery(sql.toString());
/* 579:686 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 580:687 */     query.setParameter("fechaDesde", fechaDesde);
/* 581:688 */     query.setParameter("fechaHasta", fechaHasta);
/* 582:689 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 583:691 */     if (documento != null) {
/* 584:692 */       query.setParameter("documento", documento);
/* 585:    */     }
/* 586:694 */     if (bodegaOrigen != null) {
/* 587:695 */       query.setParameter("bodegaOrigen", bodegaOrigen);
/* 588:    */     }
/* 589:697 */     if (bodegaDestino != null) {
/* 590:698 */       query.setParameter("bodegaDestino", bodegaDestino);
/* 591:    */     }
/* 592:700 */     if (subcategoriaProducto != null) {
/* 593:701 */       query.setParameter("subcategoriaProducto", subcategoriaProducto);
/* 594:    */     }
/* 595:703 */     if (categoriaProducto != null) {
/* 596:704 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 597:    */     }
/* 598:706 */     if (estado != null) {
/* 599:707 */       query.setParameter("estado", estado);
/* 600:    */     }
/* 601:709 */     if (proyecto != null) {
/* 602:710 */       query.setParameter("proyecto", proyecto);
/* 603:    */     }
/* 604:713 */     return query.getResultList();
/* 605:    */   }
/* 606:    */   
/* 607:    */   public List reporteTomaFisica(TomaFisica tomaFisica)
/* 608:    */   {
/* 609:718 */     StringBuilder sql = new StringBuilder();
/* 610:719 */     sql.append(" SELECT p.codigo, p.nombre, b.nombre, dtf.cantidadSistema, dtf.cantidadTomaFisica ");
/* 611:720 */     sql.append(" FROM  DetalleTomaFisica dtf ");
/* 612:721 */     sql.append(" INNER JOIN dtf.producto p ");
/* 613:722 */     sql.append(" INNER JOIN dtf.tomaFisica tf ");
/* 614:723 */     sql.append(" INNER JOIN tf.bodega b ");
/* 615:724 */     sql.append(" WHERE tf = :tomaFisica ");
/* 616:    */     
/* 617:726 */     Query query = this.em.createQuery(sql.toString());
/* 618:727 */     query.setParameter("tomaFisica", tomaFisica);
/* 619:728 */     return query.getResultList();
/* 620:    */   }
/* 621:    */   
/* 622:    */   public List<Object[]> getReporteCierreCirculo(int idOrganizacion, Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto)
/* 623:    */   {
/* 624:734 */     StringBuilder sql = new StringBuilder();
/* 625:735 */     sql.append(" SELECT p.idProducto, SUM (ip.cantidad) ");
/* 626:736 */     sql.append(" FROM  InventarioProducto ip ");
/* 627:737 */     sql.append(" INNER JOIN ip.producto p ");
/* 628:738 */     sql.append(" LEFT JOIN p.subcategoriaProducto sp ");
/* 629:739 */     sql.append(" LEFT JOIN ip.lote lot ");
/* 630:740 */     sql.append(" INNER JOIN ip.detalleRecepcionProveedor drp ");
/* 631:741 */     sql.append(" INNER JOIN drp.recepcionProveedor rp ");
/* 632:742 */     sql.append(" WHERE rp.fecha >= :fechaDesde ");
/* 633:743 */     sql.append(" AND rp.fecha <= :fechaHasta ");
/* 634:744 */     sql.append(" AND rp.estado != 0 ");
/* 635:745 */     sql.append(" AND rp.idOrganizacion =:idOrganizacion ");
/* 636:746 */     if (subcategoriaProducto != null) {
/* 637:747 */       sql.append(" AND sp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/* 638:    */     }
/* 639:749 */     sql.append(" GROUP BY p.idProducto ");
/* 640:    */     
/* 641:751 */     Query query = this.em.createQuery(sql.toString());
/* 642:752 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 643:753 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 644:754 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 645:755 */     if (subcategoriaProducto != null) {
/* 646:756 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 647:    */     }
/* 648:759 */     List<Object[]> listaRecibidos = query.getResultList();
/* 649:    */     
/* 650:761 */     Map<Integer, BigDecimal> mapaRecibidos = new HashMap();
/* 651:762 */     for (Object[] objects : listaRecibidos)
/* 652:    */     {
/* 653:763 */       Integer idProducto = (Integer)objects[0];
/* 654:764 */       BigDecimal cantidad = (BigDecimal)objects[1];
/* 655:765 */       mapaRecibidos.put(idProducto, cantidad);
/* 656:    */     }
/* 657:768 */     StringBuilder sql2 = new StringBuilder();
/* 658:769 */     sql2.append(" SELECT mp.codigo, mp.nombre, lotmp.codigo, prov.nombreFiscal, SUM(dmi.cantidad), SUM(dmi.cantidadDesechoTransformacion), ptt.codigo, ptt.nombre, lt.codigo, SUM(mi.cantidadTransformacion), mp.idProducto ");
/* 659:    */     
/* 660:771 */     sql2.append(" FROM DetalleMovimientoInventario dmi ");
/* 661:772 */     sql2.append(" INNER JOIN dmi.producto mp ");
/* 662:773 */     sql2.append(" LEFT JOIN mp.subcategoriaProducto sp ");
/* 663:774 */     sql2.append(" INNER JOIN dmi.inventarioProducto ipmp ");
/* 664:775 */     sql2.append(" INNER JOIN ipmp.lote lotmp ");
/* 665:776 */     sql2.append(" INNER JOIN lotmp.empresa prov ");
/* 666:777 */     sql2.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 667:778 */     sql2.append(" INNER JOIN mi.productoTerminadoTransformacion ptt ");
/* 668:779 */     sql2.append(" LEFT JOIN mi.loteTransformacion lt ");
/* 669:780 */     sql2.append(" INNER JOIN mi.documento do ");
/* 670:781 */     sql2.append(" WHERE mi.idOrganizacion =:idOrganizacion ");
/* 671:782 */     sql2.append(" AND do.documentoBase =:documentoBaseTransformacion ");
/* 672:783 */     sql2.append(" AND do.operacion = -1 ");
/* 673:784 */     sql2.append(" AND mi.estado != 0 ");
/* 674:785 */     sql2.append(" AND mi.fecha >= :fechaDesde ");
/* 675:786 */     sql2.append(" AND mi.fecha <= :fechaHasta ");
/* 676:787 */     if (subcategoriaProducto != null) {
/* 677:788 */       sql2.append(" AND sp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/* 678:    */     }
/* 679:790 */     sql2.append(" GROUP BY mp.codigo, mp.nombre, lotmp.codigo, prov.nombreFiscal, ptt.codigo, ptt.nombre, lt.codigo, mp.idProducto ");
/* 680:791 */     sql2.append(" ORDER BY mp.nombre, lotmp.codigo  ");
/* 681:    */     
/* 682:793 */     Query query2 = this.em.createQuery(sql2.toString());
/* 683:794 */     query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 684:795 */     query2.setParameter("documentoBaseTransformacion", DocumentoBase.TRANSFORMACION_PRODUCTO);
/* 685:796 */     query2.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 686:797 */     query2.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 687:798 */     if (subcategoriaProducto != null) {
/* 688:799 */       query2.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/* 689:    */     }
/* 690:802 */     List<Object[]> listaProduccion = query2.getResultList();
/* 691:803 */     List<Object[]> resultadoFinal = new ArrayList();
/* 692:804 */     for (Object[] objects : listaProduccion)
/* 693:    */     {
/* 694:805 */       Integer idMaterial = (Integer)objects[10];
/* 695:806 */       BigDecimal cantidadRecibida = (BigDecimal)mapaRecibidos.get(idMaterial);
/* 696:807 */       if (cantidadRecibida == null) {
/* 697:808 */         cantidadRecibida = BigDecimal.ZERO;
/* 698:    */       }
/* 699:811 */       Object[] result = new Object[13];
/* 700:812 */       result[0] = objects[0];
/* 701:813 */       result[1] = objects[1];
/* 702:814 */       result[2] = objects[2];
/* 703:815 */       result[3] = objects[3];
/* 704:816 */       result[4] = cantidadRecibida;
/* 705:817 */       result[5] = objects[4];
/* 706:818 */       result[6] = objects[5];
/* 707:819 */       result[7] = objects[6];
/* 708:820 */       result[8] = objects[7];
/* 709:821 */       result[9] = objects[8];
/* 710:822 */       result[10] = objects[9];
/* 711:823 */       result[11] = BigDecimal.ZERO;
/* 712:824 */       result[12] = BigDecimal.ZERO;
/* 713:825 */       resultadoFinal.add(result);
/* 714:    */     }
/* 715:827 */     return resultadoFinal;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public List<Object[]> getDatosImpresionEtiquetaLote(int idOrganizacion, int idDocumento, String numero, int idLote, int numeroAtributos)
/* 719:    */   {
/* 720:833 */     StringBuilder sql = new StringBuilder();
/* 721:834 */     sql.append(" SELECT r.numero, r.fecha, lo.codigo, p.nombreComercial, d.cantidad,");
/* 722:835 */     sql.append(" u.nombre, p.descripcion, '', p.nombre, u.codigo, r.fechaCreacion, m.nombre, CONCAT(pr.apellidos,' ',pr.nombres),");
/* 723:836 */     for (int i = 1; i <= numeroAtributos; i++)
/* 724:    */     {
/* 725:837 */       sql.append(" COALESCE(at" + i + ".codigo,''), ");
/* 726:838 */       sql.append(" COALESCE(at" + i + ".nombre,''), ");
/* 727:839 */       sql.append(" COALESCE(vat" + i + ".codigo,''), ");
/* 728:840 */       sql.append(" COALESCE(vat" + i + ".nombre,''),");
/* 729:    */     }
/* 730:842 */     sql = new StringBuilder(sql.toString().substring(0, sql.toString().length() - 1));
/* 731:843 */     sql.append(" FROM DetalleMovimientoInventario d ");
/* 732:844 */     sql.append(" JOIN d.movimientoInventario r ");
/* 733:845 */     sql.append(" LEFT JOIN r.ordenFabricacion ofa ");
/* 734:846 */     sql.append(" LEFT JOIN r.responsableSalidaMercaderia pr ");
/* 735:847 */     sql.append(" JOIN d.producto p ");
/* 736:848 */     sql.append(" LEFT JOIN d.maquina m ");
/* 737:849 */     sql.append(" JOIN p.unidad u");
/* 738:850 */     sql.append(" LEFT JOIN d.inventarioProducto i ");
/* 739:851 */     sql.append(" LEFT JOIN i.lote lo ");
/* 740:852 */     for (int i = 1; i <= numeroAtributos; i++)
/* 741:    */     {
/* 742:853 */       sql.append(" LEFT JOIN lo.atributo" + i + " at" + i);
/* 743:854 */       sql.append(" LEFT JOIN lo.valorAtributo" + i + " vat" + i);
/* 744:    */     }
/* 745:857 */     sql.append(" WHERE r.idOrganizacion = :idOrganizacion ");
/* 746:858 */     sql.append(" AND r.documento.idDocumento = :idDocumento ");
/* 747:859 */     sql.append(" AND r.numero = :numero ");
/* 748:860 */     if (idLote != 0) {
/* 749:861 */       sql.append(" AND lo.idLote = :idLote ");
/* 750:    */     }
/* 751:863 */     sql.append(" ORDER BY lo.codigo");
/* 752:    */     
/* 753:865 */     Query query = this.em.createQuery(sql.toString());
/* 754:866 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 755:867 */     query.setParameter("idDocumento", Integer.valueOf(idDocumento));
/* 756:868 */     query.setParameter("numero", numero);
/* 757:869 */     if (idLote != 0) {
/* 758:870 */       query.setParameter("idLote", Integer.valueOf(idLote));
/* 759:    */     }
/* 760:873 */     return query.getResultList();
/* 761:    */   }
/* 762:    */   
/* 763:    */   public List getReporteOrdenSalidaMaterialConOrdenFabricacion(int idOrdenSalidaMaterial, boolean devolucion, Date fechaDesde, Date fechaHasta, Producto producto, Lote lote, boolean indicadorSoloCerradas, boolean dosmConsumoDirecto)
/* 764:    */   {
/* 765:885 */     StringBuilder sql = new StringBuilder();
/* 766:886 */     sql.append(" SELECT osm.numero, osm.fecha, osm.descripcion, p.codigo, p.nombre, u.nombre, ");
/* 767:887 */     sql.append(" dosm.cantidad, dosm.cantidadDespachada, dosm.cantidadADevolver, dosm.cantidadDevuelta, dosm.cantidadUtilizada, ");
/* 768:888 */     sql.append(" dosm.descripcion, ofa.numero, lot.codigo, ui.nombre, ");
/* 769:889 */     sql.append(" dosm.cantidadUnidadInformativaDespacho, dosm.cantidadUnidadInformativaDevolucion, dosm.cantidadDesecho, ");
/* 770:890 */     sql.append(" dosm.cantidadAdicional, dc.nombre, dosmof.cantidad, dosmof.cantidadUtilizada, dosmof.cantidadDesecho   ");
/* 771:891 */     sql.append(" FROM  DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/* 772:892 */     sql.append(" INNER JOIN dosmof.ordenFabricacion ofa ");
/* 773:893 */     sql.append(" INNER JOIN dosmof.detalleOrdenSalidaMaterial dosm ");
/* 774:894 */     sql.append(" INNER JOIN dosm.ordenSalidaMaterial osm ");
/* 775:895 */     sql.append(" LEFT JOIN dosm.producto p ");
/* 776:896 */     sql.append(" LEFT JOIN dosm.destinoCosto dc ");
/* 777:897 */     sql.append(" LEFT JOIN p.unidad u ");
/* 778:898 */     sql.append(" LEFT JOIN p.unidadInformativa ui ");
/* 779:899 */     sql.append(" LEFT JOIN dosm.lote lot ");
/* 780:902 */     if (idOrdenSalidaMaterial != 0)
/* 781:    */     {
/* 782:903 */       sql.append(" WHERE osm.idOrdenSalidaMaterial = :idOrdenSalidaMaterial ");
/* 783:904 */       if (devolucion) {
/* 784:905 */         sql.append(" AND dosm.indicadorConsumoDirecto = false ");
/* 785:906 */       } else if (dosmConsumoDirecto) {
/* 786:907 */         sql.append(" AND dosm.indicadorConsumoDirecto = true ");
/* 787:    */       }
/* 788:    */     }
/* 789:    */     else
/* 790:    */     {
/* 791:911 */       String condicion = " WHERE ";
/* 792:912 */       if (fechaDesde != null)
/* 793:    */       {
/* 794:913 */         sql.append(condicion + " osm.fecha >= :fechaDesde ");
/* 795:914 */         condicion = " AND ";
/* 796:    */       }
/* 797:916 */       if (fechaHasta != null)
/* 798:    */       {
/* 799:917 */         sql.append(condicion + " osm.fecha <= :fechaHasta ");
/* 800:918 */         condicion = " AND ";
/* 801:    */       }
/* 802:920 */       if (producto != null)
/* 803:    */       {
/* 804:921 */         sql.append(condicion + " p.idProducto = :idProducto ");
/* 805:922 */         condicion = " AND ";
/* 806:    */       }
/* 807:924 */       if (lote != null)
/* 808:    */       {
/* 809:925 */         sql.append(condicion + " lot.idLote = :idLote ");
/* 810:926 */         condicion = " AND ";
/* 811:    */       }
/* 812:928 */       if (indicadorSoloCerradas)
/* 813:    */       {
/* 814:929 */         sql.append(condicion + " osm.estado = :estadoCerrada ");
/* 815:930 */         condicion = " AND ";
/* 816:    */       }
/* 817:934 */       sql.append(condicion + " osm.indicadorTransferencia = false ");
/* 818:    */     }
/* 819:936 */     sql.append(" ORDER BY ofa.numero, p.codigo ");
/* 820:    */     
/* 821:938 */     Query query = this.em.createQuery(sql.toString());
/* 822:939 */     if (idOrdenSalidaMaterial != 0) {
/* 823:940 */       query.setParameter("idOrdenSalidaMaterial", Integer.valueOf(idOrdenSalidaMaterial));
/* 824:    */     }
/* 825:942 */     if (fechaDesde != null) {
/* 826:943 */       query.setParameter("fechaDesde", fechaDesde);
/* 827:    */     }
/* 828:945 */     if (fechaHasta != null) {
/* 829:946 */       query.setParameter("fechaHasta", fechaHasta);
/* 830:    */     }
/* 831:948 */     if (producto != null) {
/* 832:949 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 833:    */     }
/* 834:951 */     if (lote != null) {
/* 835:952 */       query.setParameter("idLote", Integer.valueOf(lote.getId()));
/* 836:    */     }
/* 837:954 */     if (indicadorSoloCerradas) {
/* 838:955 */       query.setParameter("estadoCerrada", Estado.CERRADO);
/* 839:    */     }
/* 840:957 */     return query.getResultList();
/* 841:    */   }
/* 842:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.inventario.ReporteMovimientoInventarioDao
 * JD-Core Version:    0.7.0.1
 */