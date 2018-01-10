/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.MotivoAjusteUnidadManejo;
/*   7:    */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*   8:    */ import com.asinfo.as2.entities.SaldoUnidadManejo;
/*   9:    */ import com.asinfo.as2.entities.Subempresa;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Transportista;
/*  12:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ import javax.persistence.EntityManager;
/*  21:    */ import javax.persistence.Query;
/*  22:    */ import javax.persistence.TypedQuery;
/*  23:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  24:    */ import javax.persistence.criteria.CriteriaQuery;
/*  25:    */ import javax.persistence.criteria.Expression;
/*  26:    */ import javax.persistence.criteria.JoinType;
/*  27:    */ import javax.persistence.criteria.Predicate;
/*  28:    */ import javax.persistence.criteria.Root;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class MovimientoUnidadManejoDao
/*  32:    */   extends AbstractDaoAS2<MovimientoUnidadManejo>
/*  33:    */ {
/*  34:    */   @EJB
/*  35:    */   private GenericoDao<SaldoUnidadManejo> saldoUnidadManejoDao;
/*  36:    */   
/*  37:    */   public MovimientoUnidadManejoDao()
/*  38:    */   {
/*  39: 48 */     super(MovimientoUnidadManejo.class);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<MovimientoUnidadManejo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  43:    */   {
/*  44: 63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  45: 64 */     CriteriaQuery<MovimientoUnidadManejo> criteriaQuery = criteriaBuilder.createQuery(MovimientoUnidadManejo.class);
/*  46: 65 */     Root<MovimientoUnidadManejo> from = criteriaQuery.from(MovimientoUnidadManejo.class);
/*  47:    */     
/*  48: 67 */     from.fetch("motivoAjusteUnidadManejo", JoinType.LEFT);
/*  49: 68 */     from.fetch("documento", JoinType.LEFT);
/*  50: 69 */     from.fetch("sucursal", JoinType.LEFT);
/*  51: 70 */     from.fetch("transportista", JoinType.LEFT);
/*  52: 71 */     from.fetch("empresa", JoinType.LEFT);
/*  53:    */     
/*  54: 73 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  55: 74 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  56:    */     
/*  57: 76 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  58:    */     
/*  59: 78 */     CriteriaQuery<MovimientoUnidadManejo> select = criteriaQuery.select(from);
/*  60:    */     
/*  61: 80 */     TypedQuery<MovimientoUnidadManejo> typedQuery = this.em.createQuery(select);
/*  62: 81 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  63:    */     
/*  64: 83 */     List<MovimientoUnidadManejo> listaMovimientoUnidadManejo = typedQuery.getResultList();
/*  65:    */     
/*  66: 85 */     return listaMovimientoUnidadManejo;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int idOrganizacion, Sucursal sucursal, Empresa empresa, Subempresa subempresa, Transportista transportista, UnidadManejo unidadManejo)
/*  70:    */   {
/*  71: 92 */     StringBuilder sql = new StringBuilder();
/*  72: 93 */     sql.append(" SELECT sumj ");
/*  73: 94 */     sql.append(" FROM SaldoUnidadManejo sumj ");
/*  74: 95 */     sql.append(" INNER JOIN FETCH sumj.unidadManejo um ");
/*  75: 96 */     sql.append(" LEFT JOIN FETCH um.categoriaUnidadManejo cum ");
/*  76: 97 */     sql.append(" LEFT JOIN FETCH sumj.sucursal s ");
/*  77: 98 */     sql.append(" LEFT JOIN FETCH sumj.empresa e ");
/*  78: 99 */     sql.append(" LEFT JOIN FETCH sumj.subempresa se ");
/*  79:100 */     sql.append(" LEFT JOIN FETCH sumj.transportista t ");
/*  80:101 */     sql.append(" WHERE sumj.idOrganizacion =:idOrganizacion ");
/*  81:103 */     if (sucursal != null) {
/*  82:104 */       sql.append(" AND s = :sucursal ");
/*  83:    */     }
/*  84:106 */     if (empresa != null) {
/*  85:107 */       sql.append(" AND e = :empresa ");
/*  86:    */     }
/*  87:109 */     if (subempresa != null) {
/*  88:110 */       sql.append(" AND se = :subempresa ");
/*  89:111 */     } else if ((empresa != null) && (subempresa == null)) {
/*  90:112 */       sql.append(" AND se IS NULL ");
/*  91:    */     }
/*  92:114 */     if (transportista != null) {
/*  93:115 */       sql.append(" AND t = :transportista ");
/*  94:    */     }
/*  95:117 */     if (unidadManejo != null) {
/*  96:118 */       sql.append(" AND um = :unidadManejo");
/*  97:    */     }
/*  98:120 */     sql.append(" ORDER BY um.nombre, sumj.fechaCreacion ASC ");
/*  99:    */     
/* 100:122 */     Query query = this.em.createQuery(sql.toString());
/* 101:123 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 102:125 */     if (sucursal != null) {
/* 103:126 */       query.setParameter("sucursal", sucursal);
/* 104:    */     }
/* 105:128 */     if (empresa != null) {
/* 106:129 */       query.setParameter("empresa", empresa);
/* 107:    */     }
/* 108:131 */     if (subempresa != null) {
/* 109:132 */       query.setParameter("subempresa", subempresa);
/* 110:    */     }
/* 111:134 */     if (transportista != null) {
/* 112:135 */       query.setParameter("transportista", transportista);
/* 113:    */     }
/* 114:137 */     if (unidadManejo != null) {
/* 115:138 */       query.setParameter("unidadManejo", unidadManejo);
/* 116:    */     }
/* 117:141 */     return query.getResultList();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void actualizarSaldo(int idOrganizacion, Sucursal sucursal, UnidadManejo unidadManejo, int cantidad)
/* 121:    */     throws ExcepcionAS2Inventario
/* 122:    */   {
/* 123:146 */     actualizarSaldo(idOrganizacion, sucursal, null, null, null, unidadManejo, cantidad);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void actualizarSaldo(int idOrganizacion, Transportista transportista, UnidadManejo unidadManejo, int cantidad)
/* 127:    */     throws ExcepcionAS2Inventario
/* 128:    */   {
/* 129:151 */     actualizarSaldo(idOrganizacion, null, null, null, transportista, unidadManejo, cantidad);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void actualizarSaldo(int idOrganizacion, Empresa empresa, Subempresa subempresa, UnidadManejo unidadManejo, int cantidad)
/* 133:    */     throws ExcepcionAS2Inventario
/* 134:    */   {
/* 135:156 */     actualizarSaldo(idOrganizacion, null, empresa, subempresa, null, unidadManejo, cantidad);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void actualizarSaldo(int idOrganizacion, Sucursal sucursal, Empresa empresa, Subempresa subempresa, Transportista transportista, UnidadManejo unidadManejo, int cantidad)
/* 139:    */     throws ExcepcionAS2Inventario
/* 140:    */   {
/* 141:162 */     List<SaldoUnidadManejo> lista = obtenerSaldoUnidadManejo(idOrganizacion, sucursal, empresa, subempresa, transportista, unidadManejo);
/* 142:    */     
/* 143:164 */     SaldoUnidadManejo saldoUnidadManejo = null;
/* 144:165 */     if (lista.size() == 1) {
/* 145:166 */       saldoUnidadManejo = (SaldoUnidadManejo)lista.get(0);
/* 146:    */     }
/* 147:169 */     if (saldoUnidadManejo == null)
/* 148:    */     {
/* 149:170 */       saldoUnidadManejo = new SaldoUnidadManejo();
/* 150:171 */       saldoUnidadManejo.setIdOrganizacion(idOrganizacion);
/* 151:172 */       saldoUnidadManejo.setSucursal(sucursal);
/* 152:173 */       saldoUnidadManejo.setTransportista(transportista);
/* 153:174 */       saldoUnidadManejo.setEmpresa(empresa);
/* 154:175 */       saldoUnidadManejo.setSubempresa(subempresa);
/* 155:176 */       saldoUnidadManejo.setUnidadManejo(unidadManejo);
/* 156:177 */       saldoUnidadManejo.setCantidad(cantidad);
/* 157:    */     }
/* 158:    */     else
/* 159:    */     {
/* 160:180 */       saldoUnidadManejo.setCantidad(saldoUnidadManejo.getCantidad() + cantidad);
/* 161:    */     }
/* 162:183 */     this.saldoUnidadManejoDao.guardar(saldoUnidadManejo);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getSaldo(int idOrganizacion, Sucursal sucursal, Empresa empresa, Subempresa subempresa, Transportista transportista, UnidadManejo unidadManejo)
/* 166:    */   {
/* 167:191 */     List<SaldoUnidadManejo> lista = obtenerSaldoUnidadManejo(idOrganizacion, sucursal, empresa, subempresa, transportista, unidadManejo);
/* 168:    */     int saldoum;
/* 169:    */     int saldoum;
/* 170:193 */     if (lista.size() == 1) {
/* 171:194 */       saldoum = ((SaldoUnidadManejo)lista.get(0)).getCantidad();
/* 172:    */     } else {
/* 173:196 */       saldoum = 0;
/* 174:    */     }
/* 175:199 */     return saldoum;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<SaldoUnidadManejo> getReporteSaldoUnidadManejo(int idOrganizacion, Sucursal sucursal, Empresa empresa, Transportista transportista)
/* 179:    */   {
/* 180:206 */     StringBuilder sql = new StringBuilder();
/* 181:207 */     sql.append(" SELECT s.nombre, um.nombre, sumj.cantidad ");
/* 182:208 */     sql.append(" FROM SaldoUnidadManejo sumj ");
/* 183:209 */     sql.append(" INNER JOIN sumj.unidadManejo um ");
/* 184:210 */     sql.append(" LEFT JOIN um.categoriaUnidadManejo cum ");
/* 185:211 */     sql.append(" LEFT JOIN sumj.sucursal s ");
/* 186:212 */     sql.append(" LEFT JOIN sumj.empresa e ");
/* 187:213 */     sql.append(" LEFT JOIN sumj.transportista t ");
/* 188:214 */     sql.append(" WHERE sumj.idOrganizacion =:idOrganizacion ");
/* 189:216 */     if (sucursal != null) {
/* 190:217 */       sql.append(" AND s = :sucursal ");
/* 191:    */     }
/* 192:220 */     if (empresa != null) {
/* 193:221 */       sql.append(" AND e = :empresa ");
/* 194:    */     }
/* 195:223 */     if (transportista != null) {
/* 196:224 */       sql.append(" AND t = :transportista ");
/* 197:    */     }
/* 198:227 */     sql.append(" ORDER BY sumj.fechaCreacion ASC ");
/* 199:    */     
/* 200:229 */     Query query = this.em.createQuery(sql.toString());
/* 201:    */     
/* 202:231 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 203:233 */     if (sucursal != null) {
/* 204:234 */       query.setParameter("sucursal", sucursal);
/* 205:    */     }
/* 206:236 */     if (empresa != null) {
/* 207:237 */       query.setParameter("empresa", empresa);
/* 208:    */     }
/* 209:239 */     if (transportista != null) {
/* 210:240 */       query.setParameter("transportista", transportista);
/* 211:    */     }
/* 212:243 */     return query.getResultList();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public MovimientoUnidadManejo cargarDetalle(int idMovimientoUnidadManejo)
/* 216:    */   {
/* 217:248 */     MovimientoUnidadManejo movimientoUnidadManejo = (MovimientoUnidadManejo)buscarPorId(Integer.valueOf(idMovimientoUnidadManejo));
/* 218:249 */     if (movimientoUnidadManejo.getDocumento() != null) {
/* 219:250 */       movimientoUnidadManejo.getDocumento().getId();
/* 220:    */     }
/* 221:252 */     if (movimientoUnidadManejo.getEmpresa() != null) {
/* 222:253 */       movimientoUnidadManejo.getEmpresa().getId();
/* 223:    */     }
/* 224:255 */     if (movimientoUnidadManejo.getMotivoAjusteUnidadManejo() != null) {
/* 225:256 */       movimientoUnidadManejo.getMotivoAjusteUnidadManejo().getId();
/* 226:    */     }
/* 227:258 */     if (movimientoUnidadManejo.getSubempresa() != null) {
/* 228:259 */       movimientoUnidadManejo.getSubempresa().getId();
/* 229:    */     }
/* 230:261 */     if (movimientoUnidadManejo.getSucursal() != null) {
/* 231:262 */       movimientoUnidadManejo.getSucursal().getId();
/* 232:    */     }
/* 233:264 */     if (movimientoUnidadManejo.getTransportista() != null) {
/* 234:265 */       movimientoUnidadManejo.getTransportista().getId();
/* 235:    */     }
/* 236:267 */     for (DetalleMovimientoUnidadManejo dmu : movimientoUnidadManejo.getDetalleMovimientoUnidadManejo())
/* 237:    */     {
/* 238:268 */       dmu.getId();
/* 239:269 */       if (dmu.getUnidadManejo() != null) {
/* 240:270 */         dmu.getUnidadManejo().getId();
/* 241:    */       }
/* 242:    */     }
/* 243:273 */     return movimientoUnidadManejo;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<DetalleMovimientoUnidadManejo> getReporteKardexUnidadManejo(int idOrganizacion, Sucursal sucursal, Transportista transportista, Empresa empresa, Subempresa subempresa, UnidadManejo unidadManejo, Date fechaDesde, Date fechaHasta)
/* 247:    */   {
/* 248:280 */     StringBuilder sql = new StringBuilder();
/* 249:    */     
/* 250:282 */     sql.append("SELECT dmum");
/* 251:283 */     sql.append(" FROM DetalleMovimientoUnidadManejo dmum");
/* 252:284 */     sql.append(" LEFT JOIN FETCH dmum.unidadManejo um");
/* 253:285 */     sql.append(" LEFT JOIN FETCH dmum.sucursal s");
/* 254:286 */     sql.append(" LEFT JOIN FETCH dmum.transportista t");
/* 255:287 */     sql.append(" LEFT JOIN FETCH dmum.empresa e");
/* 256:288 */     sql.append(" LEFT JOIN FETCH dmum.subempresa se");
/* 257:289 */     sql.append(" LEFT JOIN FETCH dmum.movimientoUnidadManejo mum");
/* 258:290 */     sql.append(" LEFT JOIN FETCH mum.documento d");
/* 259:291 */     sql.append(" LEFT JOIN FETCH mum.sucursal sm");
/* 260:292 */     sql.append(" LEFT JOIN FETCH mum.transportista tm");
/* 261:293 */     sql.append(" LEFT JOIN FETCH mum.empresa em");
/* 262:294 */     sql.append(" LEFT JOIN FETCH mum.subempresa sem");
/* 263:295 */     sql.append(" WHERE mum.idOrganizacion =:idOrganizacion");
/* 264:296 */     sql.append(" AND mum.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 265:297 */     sql.append(" AND mum.estado !=:estado ");
/* 266:298 */     if (sucursal != null) {
/* 267:299 */       sql.append(" AND s=:sucursal");
/* 268:    */     }
/* 269:301 */     if (transportista != null) {
/* 270:302 */       sql.append(" AND t=:transportista");
/* 271:    */     }
/* 272:304 */     if (empresa != null) {
/* 273:305 */       sql.append(" AND e=:empresa");
/* 274:    */     }
/* 275:307 */     if (subempresa != null) {
/* 276:308 */       sql.append(" AND se=:subempresa");
/* 277:    */     }
/* 278:310 */     if (unidadManejo != null) {
/* 279:311 */       sql.append(" AND um=:unidadManejo");
/* 280:    */     }
/* 281:313 */     sql.append(" ORDER BY um.idUnidadManejo, mum.fecha ASC, dmum.operacion DESC");
/* 282:    */     
/* 283:315 */     Query query = this.em.createQuery(sql.toString());
/* 284:    */     
/* 285:317 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 286:318 */     if (sucursal != null) {
/* 287:319 */       query.setParameter("sucursal", sucursal);
/* 288:    */     }
/* 289:320 */     if (transportista != null) {
/* 290:321 */       query.setParameter("transportista", transportista);
/* 291:    */     }
/* 292:322 */     if (empresa != null) {
/* 293:323 */       query.setParameter("empresa", empresa);
/* 294:    */     }
/* 295:324 */     if (subempresa != null) {
/* 296:325 */       query.setParameter("subempresa", subempresa);
/* 297:    */     }
/* 298:326 */     if (unidadManejo != null) {
/* 299:327 */       query.setParameter("unidadManejo", unidadManejo);
/* 300:    */     }
/* 301:328 */     query.setParameter("fechaDesde", fechaDesde);
/* 302:329 */     query.setParameter("fechaHasta", fechaHasta);
/* 303:330 */     query.setParameter("estado", Estado.ANULADO);
/* 304:    */     
/* 305:332 */     List<DetalleMovimientoUnidadManejo> list = query.getResultList();
/* 306:333 */     return list;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<Object[]> getSalgoInicialUnidadManejo(int idOrganizacion, Sucursal sucursal, Transportista transportista, Empresa empresa, Subempresa subempresa, UnidadManejo unidadManejo, Date fechaDesde, Date fechaHasta)
/* 310:    */   {
/* 311:340 */     StringBuilder sql = new StringBuilder();
/* 312:    */     
/* 313:342 */     sql.append("SELECT SUM(dmum.cantidad*dmum.operacion), 'Saldo Inicial', um.idUnidadManejo");
/* 314:343 */     sql.append(" FROM DetalleMovimientoUnidadManejo dmum");
/* 315:344 */     sql.append(" LEFT JOIN  dmum.movimientoUnidadManejo mum");
/* 316:345 */     sql.append(" LEFT JOIN  mum.documento d");
/* 317:346 */     sql.append(" LEFT JOIN  dmum.unidadManejo um");
/* 318:347 */     if (sucursal != null) {
/* 319:348 */       sql.append(" LEFT JOIN  dmum.sucursal s");
/* 320:    */     }
/* 321:349 */     if (transportista != null) {
/* 322:350 */       sql.append(" LEFT JOIN  dmum.transportista t");
/* 323:    */     }
/* 324:351 */     if (empresa != null) {
/* 325:352 */       sql.append(" LEFT JOIN  dmum.empresa e");
/* 326:    */     }
/* 327:353 */     if (subempresa != null) {
/* 328:354 */       sql.append(" LEFT JOIN  dmum.subempresa se");
/* 329:    */     }
/* 330:355 */     sql.append(" WHERE mum.idOrganizacion =:idOrganizacion");
/* 331:356 */     sql.append(" AND mum.fecha<:fechaDesde");
/* 332:357 */     sql.append(" AND mum.estado !=:estado");
/* 333:358 */     if (sucursal != null) {
/* 334:359 */       sql.append(" AND s=:sucursal");
/* 335:    */     }
/* 336:360 */     if (transportista != null) {
/* 337:361 */       sql.append(" AND t=:transportista");
/* 338:    */     }
/* 339:362 */     if (empresa != null) {
/* 340:363 */       sql.append(" AND e=:empresa");
/* 341:    */     }
/* 342:364 */     if (subempresa != null) {
/* 343:365 */       sql.append(" AND se=:subempresa");
/* 344:    */     }
/* 345:366 */     if (unidadManejo != null) {
/* 346:367 */       sql.append(" AND um=:unidadManejo");
/* 347:    */     }
/* 348:368 */     sql.append(" GROUP BY um.idUnidadManejo");
/* 349:    */     
/* 350:370 */     Query query = this.em.createQuery(sql.toString());
/* 351:    */     
/* 352:372 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 353:373 */     if (sucursal != null) {
/* 354:374 */       query.setParameter("sucursal", sucursal);
/* 355:    */     }
/* 356:375 */     if (transportista != null) {
/* 357:376 */       query.setParameter("transportista", transportista);
/* 358:    */     }
/* 359:377 */     if (empresa != null) {
/* 360:378 */       query.setParameter("empresa", empresa);
/* 361:    */     }
/* 362:379 */     if (subempresa != null) {
/* 363:380 */       query.setParameter("subempresa", subempresa);
/* 364:    */     }
/* 365:381 */     if (unidadManejo != null) {
/* 366:382 */       query.setParameter("unidadManejo", unidadManejo);
/* 367:    */     }
/* 368:383 */     query.setParameter("fechaDesde", fechaDesde);
/* 369:384 */     query.setParameter("estado", Estado.ANULADO);
/* 370:385 */     return query.getResultList();
/* 371:    */   }
/* 372:    */   
/* 373:    */   public List<UnidadManejo> obtenerUnidadManejoPorUsuario(int idOrganizacion, String consulta, Transportista transportista, Sucursal sucursal, Empresa empresa, Subempresa subempresa)
/* 374:    */   {
/* 375:391 */     StringBuilder sql = new StringBuilder();
/* 376:    */     
/* 377:393 */     sql.append(" SELECT um");
/* 378:394 */     sql.append(" FROM SaldoUnidadManejo sumj ");
/* 379:395 */     sql.append(" INNER JOIN  sumj.unidadManejo um ");
/* 380:396 */     if (transportista != null) {
/* 381:397 */       sql.append(" LEFT JOIN  sumj.transportista t ");
/* 382:    */     }
/* 383:399 */     if (sucursal != null) {
/* 384:400 */       sql.append(" LEFT JOIN  sumj.sucursal s ");
/* 385:    */     }
/* 386:402 */     if (empresa != null) {
/* 387:403 */       sql.append(" LEFT JOIN  sumj.empresa e ");
/* 388:    */     }
/* 389:405 */     if (subempresa != null) {
/* 390:406 */       sql.append(" LEFT JOIN  sumj.subempresa se ");
/* 391:    */     }
/* 392:408 */     sql.append(" WHERE sumj.idOrganizacion =:idOrganizacion ");
/* 393:409 */     sql.append(" AND ( um.nombre LIKE :consulta");
/* 394:410 */     sql.append(" OR um.codigo LIKE :consulta )");
/* 395:411 */     sql.append(" AND sumj.cantidad>0");
/* 396:412 */     if (transportista != null) {
/* 397:413 */       sql.append(" AND t =:transportista");
/* 398:    */     }
/* 399:415 */     if (sucursal != null) {
/* 400:416 */       sql.append(" AND s =:sucursal");
/* 401:    */     }
/* 402:418 */     if (empresa != null) {
/* 403:419 */       sql.append(" AND e =:empresa");
/* 404:    */     }
/* 405:421 */     if (subempresa != null) {
/* 406:422 */       sql.append(" AND se =:subempresa");
/* 407:    */     }
/* 408:424 */     Query query = this.em.createQuery(sql.toString());
/* 409:    */     
/* 410:426 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 411:427 */     query.setParameter("consulta", "%" + consulta + "%");
/* 412:428 */     if (transportista != null) {
/* 413:429 */       query.setParameter("transportista", transportista);
/* 414:    */     }
/* 415:431 */     if (sucursal != null) {
/* 416:432 */       query.setParameter("sucursal", sucursal);
/* 417:    */     }
/* 418:434 */     if (empresa != null) {
/* 419:435 */       query.setParameter("empresa", empresa);
/* 420:    */     }
/* 421:437 */     if (subempresa != null) {
/* 422:438 */       query.setParameter("subempresa", subempresa);
/* 423:    */     }
/* 424:440 */     return query.getResultList();
/* 425:    */   }
/* 426:    */   
/* 427:    */   public List<Object[]> getReporteTransferencia(MovimientoUnidadManejo movimientoUnidadManejo)
/* 428:    */   {
/* 429:446 */     StringBuilder sql = new StringBuilder();
/* 430:    */     
/* 431:448 */     sql.append("SELECT d.nombre, mum.numero, ");
/* 432:449 */     sql.append(" s.nombre, t.nombre, e.nombreFiscal, se.codigo, ");
/* 433:450 */     sql.append(" coalesce(sd.nombre,''), coalesce(td.nombre,''), coalesce(ed.nombreFiscal,''), coalesce(sed.codigo,''), ");
/* 434:451 */     sql.append(" mum.fecha, mum.descripcion, um.codigo, um.nombre, dmum.cantidad, dmum.operacion ");
/* 435:452 */     sql.append(" FROM DetalleMovimientoUnidadManejo dmum");
/* 436:453 */     sql.append(" LEFT JOIN  dmum.sucursal sd");
/* 437:454 */     sql.append(" LEFT JOIN  dmum.transportista td");
/* 438:455 */     sql.append(" LEFT JOIN  dmum.empresa ed");
/* 439:456 */     sql.append(" LEFT JOIN  dmum.subempresa sed");
/* 440:457 */     sql.append(" LEFT JOIN  dmum.unidadManejo um");
/* 441:458 */     sql.append(" LEFT JOIN  dmum.movimientoUnidadManejo mum");
/* 442:459 */     sql.append(" LEFT JOIN  mum.documento d");
/* 443:460 */     sql.append(" LEFT JOIN  mum.sucursal s");
/* 444:461 */     sql.append(" LEFT JOIN  mum.transportista t");
/* 445:462 */     sql.append(" LEFT JOIN  mum.empresa e");
/* 446:463 */     sql.append(" LEFT JOIN  mum.subempresa se");
/* 447:464 */     sql.append(" WHERE mum =:movimiento");
/* 448:    */     
/* 449:466 */     Query query = this.em.createQuery(sql.toString());
/* 450:    */     
/* 451:468 */     query.setParameter("movimiento", movimientoUnidadManejo);
/* 452:469 */     return query.getResultList();
/* 453:    */   }
/* 454:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MovimientoUnidadManejoDao
 * JD-Core Version:    0.7.0.1
 */