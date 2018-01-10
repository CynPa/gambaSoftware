/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.FormaPago;
/*   9:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  11:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioEstadoFinancieroImpl;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.Stateless;
/*  22:    */ import javax.persistence.EntityManager;
/*  23:    */ import javax.persistence.Query;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.persistence.TypedQuery;
/*  26:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  27:    */ import javax.persistence.criteria.CriteriaQuery;
/*  28:    */ import javax.persistence.criteria.Expression;
/*  29:    */ import javax.persistence.criteria.Fetch;
/*  30:    */ import javax.persistence.criteria.Join;
/*  31:    */ import javax.persistence.criteria.JoinType;
/*  32:    */ import javax.persistence.criteria.Order;
/*  33:    */ import javax.persistence.criteria.Path;
/*  34:    */ import javax.persistence.criteria.Predicate;
/*  35:    */ import javax.persistence.criteria.Root;
/*  36:    */ 
/*  37:    */ @Stateless
/*  38:    */ public class AsientoDao
/*  39:    */   extends AbstractDaoAS2<Asiento>
/*  40:    */ {
/*  41:    */   public AsientoDao()
/*  42:    */   {
/*  43: 56 */     super(Asiento.class);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void guardar(Asiento asiento)
/*  47:    */   {
/*  48: 62 */     if (asiento.getIdAsiento() == 0) {
/*  49: 63 */       this.em.persist(asiento);
/*  50:    */     } else {
/*  51: 65 */       this.em.merge(asiento);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Asiento cargarDetalle(int idAsiento)
/*  56:    */   {
/*  57: 71 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  58:    */     
/*  59:    */ 
/*  60: 74 */     CriteriaQuery<Asiento> cqCabecera = criteriaBuilder.createQuery(Asiento.class);
/*  61: 75 */     Root<Asiento> fromCabecera = cqCabecera.from(Asiento.class);
/*  62:    */     
/*  63: 77 */     Fetch<Object, Object> tipoAsiento = fromCabecera.fetch("tipoAsiento", JoinType.LEFT);
/*  64: 78 */     tipoAsiento.fetch("secuencia", JoinType.LEFT);
/*  65:    */     
/*  66: 80 */     Fetch<Object, Object> periodo = fromCabecera.fetch("periodo", JoinType.LEFT);
/*  67: 81 */     periodo.fetch("ejercicio", JoinType.LEFT);
/*  68:    */     
/*  69: 83 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  70: 84 */     fromCabecera.fetch("documentoOrigen", JoinType.LEFT);
/*  71: 85 */     fromCabecera.fetch("tipoAsiento", JoinType.LEFT);
/*  72: 86 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*  73:    */     
/*  74: 88 */     Path<Integer> pathId = fromCabecera.get("idAsiento");
/*  75: 89 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idAsiento)));
/*  76: 90 */     CriteriaQuery<Asiento> selectAsiento = cqCabecera.select(fromCabecera);
/*  77:    */     
/*  78: 92 */     Asiento asiento = (Asiento)this.em.createQuery(selectAsiento).getSingleResult();
/*  79:    */     
/*  80:    */ 
/*  81: 95 */     CriteriaQuery<DetalleAsiento> cqDetalle = criteriaBuilder.createQuery(DetalleAsiento.class);
/*  82: 96 */     Root<DetalleAsiento> fromDetalle = cqDetalle.from(DetalleAsiento.class);
/*  83:    */     
/*  84: 98 */     fromDetalle.fetch("cuentaContable", JoinType.LEFT);
/*  85: 99 */     Fetch<Object, Object> movimientoBancario = fromDetalle.fetch("movimientoBancario", JoinType.LEFT);
/*  86:100 */     movimientoBancario.fetch("formaPago", JoinType.LEFT);
/*  87:101 */     movimientoBancario.fetch("conciliacionBancaria", JoinType.LEFT);
/*  88:102 */     Fetch<Object, Object> cuentaBancariaOrganizacion = movimientoBancario.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  89:103 */     cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/*  90:    */     
/*  91:105 */     Path<Integer> pathIdDetalle = fromDetalle.join("asiento").get("idAsiento");
/*  92:106 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idAsiento)));
/*  93:107 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idDetalleAsiento")) });
/*  94:    */     
/*  95:109 */     CriteriaQuery<DetalleAsiento> selectDetalleAsiento = cqDetalle.select(fromDetalle);
/*  96:    */     
/*  97:111 */     List<DetalleAsiento> listaDetalleAsiento = this.em.createQuery(selectDetalleAsiento).getResultList();
/*  98:112 */     asiento.setListaDetalleAsiento(listaDetalleAsiento);
/*  99:115 */     for (DetalleAsiento detalleAsiento : listaDetalleAsiento)
/* 100:    */     {
/* 101:117 */       if (detalleAsiento.getDimensionContable1() != null) {
/* 102:118 */         detalleAsiento.getDimensionContable1().getId();
/* 103:    */       }
/* 104:120 */       if (detalleAsiento.getDimensionContable2() != null) {
/* 105:121 */         detalleAsiento.getDimensionContable2().getId();
/* 106:    */       }
/* 107:123 */       if (detalleAsiento.getDimensionContable3() != null) {
/* 108:124 */         detalleAsiento.getDimensionContable3().getId();
/* 109:    */       }
/* 110:126 */       if (detalleAsiento.getDimensionContable4() != null) {
/* 111:127 */         detalleAsiento.getDimensionContable4().getId();
/* 112:    */       }
/* 113:129 */       if (detalleAsiento.getDimensionContable5() != null) {
/* 114:130 */         detalleAsiento.getDimensionContable5().getId();
/* 115:    */       }
/* 116:134 */       if ((detalleAsiento.getMovimientoBancario() != null) && (detalleAsiento.getMovimientoBancario().getCuentaBancariaOrganizacion() != null))
/* 117:    */       {
/* 118:136 */         detalleAsiento.getMovimientoBancario().getCuentaBancariaOrganizacion().getId();
/* 119:137 */         detalleAsiento.getMovimientoBancario().getCuentaBancariaOrganizacion().getCuentaBancaria();
/* 120:138 */         detalleAsiento.getMovimientoBancario().getCuentaBancariaOrganizacion().getListaFormaPago().size();
/* 121:    */         
/* 122:    */ 
/* 123:141 */         CriteriaQuery<MovimientoBancarioEstadoCheque> cqMovimientoBancarioEstadoCheque = criteriaBuilder.createQuery(MovimientoBancarioEstadoCheque.class);
/* 124:    */         
/* 125:143 */         Root<MovimientoBancarioEstadoCheque> fromMovimientoBancarioEstadoCheque = cqMovimientoBancarioEstadoCheque.from(MovimientoBancarioEstadoCheque.class);
/* 126:    */         
/* 127:145 */         Path<Integer> pathIdMovimientoBancario = fromMovimientoBancarioEstadoCheque.join("movimientoBancario").get("idMovimientoBancario");
/* 128:146 */         cqMovimientoBancarioEstadoCheque
/* 129:147 */           .where(criteriaBuilder.equal(pathIdMovimientoBancario, Integer.valueOf(detalleAsiento.getMovimientoBancario().getIdMovimientoBancario())));
/* 130:    */         
/* 131:    */ 
/* 132:150 */         CriteriaQuery<MovimientoBancarioEstadoCheque> selectMovimientoBancarioEstadoCheque = cqMovimientoBancarioEstadoCheque.select(fromMovimientoBancarioEstadoCheque);
/* 133:    */         
/* 134:    */ 
/* 135:153 */         List<MovimientoBancarioEstadoCheque> listaMovimientoBancarioEstadoCheque = this.em.createQuery(selectMovimientoBancarioEstadoCheque).getResultList();
/* 136:154 */         detalleAsiento.getMovimientoBancario().setListaMovimientoBancarioEstadoCheque(listaMovimientoBancarioEstadoCheque);
/* 137:156 */         for (FormaPagoCuentaBancariaOrganizacion formaPagoCuenta : detalleAsiento.getMovimientoBancario().getCuentaBancariaOrganizacion()
/* 138:157 */           .getListaFormaPago()) {
/* 139:158 */           formaPagoCuenta.getFormaPago().getId();
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:164 */     return asiento;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<Asiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 147:    */   {
/* 148:179 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 149:180 */     CriteriaQuery<Asiento> criteriaQuery = criteriaBuilder.createQuery(Asiento.class);
/* 150:181 */     Root<Asiento> from = criteriaQuery.from(Asiento.class);
/* 151:    */     
/* 152:183 */     from.fetch("tipoAsiento", JoinType.LEFT);
/* 153:184 */     from.fetch("documentoOrigen", JoinType.LEFT);
/* 154:185 */     from.fetch("proyecto", JoinType.LEFT);
/* 155:186 */     Fetch<Object, Object> sucursal = from.fetch("sucursal", JoinType.LEFT);
/* 156:187 */     sucursal.fetch("ciudad", JoinType.LEFT);
/* 157:    */     
/* 158:189 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 159:    */     
/* 160:191 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 161:192 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 162:    */     
/* 163:194 */     CriteriaQuery<Asiento> select = criteriaQuery.select(from);
/* 164:    */     
/* 165:196 */     TypedQuery<Asiento> typedQuery = this.em.createQuery(select);
/* 166:197 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 167:    */     
/* 168:199 */     return typedQuery.getResultList();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<DetalleAsiento> obtenerListaDetalleAsiento(TipoAsiento tipoAsiento, int idAsiento, Date fechaDesde, Date fechaHasta)
/* 172:    */     throws ExcepcionAS2Financiero
/* 173:    */   {
/* 174:211 */     List<DetalleAsiento> detalles = this.em.createQuery("SELECT new DetalleAsiento(d.debe, d.haber,d.descripcion, d.asiento.concepto, d.asiento.numero, d.asiento.fecha,  d.cuentaContable.nombre, d.cuentaContable.codigo)  FROM DetalleAsiento d  WHERE d.asiento.estado =:estado AND d.asiento.fecha BETWEEN :fechaDesde AND :fechaHasta ORDER BY d.asiento.fecha").setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).setParameter("estado", Estado.REVISADO).getResultList();
/* 175:212 */     if (detalles.size() == 0) {
/* 176:215 */       throw new ExcepcionAS2Financiero("", "No existen movimientos ");
/* 177:    */     }
/* 178:217 */     return detalles;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<DetalleAsiento> obtenerListaAsiento(Date fechaDesde, Date fechaHasta, int idSucursal, int idTipoAsiento, Asiento asientoFiltro)
/* 182:    */     throws ExcepcionAS2Financiero
/* 183:    */   {
/* 184:227 */     int idOrganizacion = AppUtil.getSucursal().getIdOrganizacion();
/* 185:    */     
/* 186:229 */     StringBuilder sql = new StringBuilder();
/* 187:230 */     sql.append(" SELECT d FROM DetalleAsiento d ");
/* 188:231 */     sql.append(" JOIN FETCH d.cuentaContable c ");
/* 189:232 */     sql.append(" JOIN FETCH d.asiento a ");
/* 190:233 */     sql.append(" JOIN FETCH a.tipoAsiento t ");
/* 191:234 */     sql.append(" JOIN a.sucursal s ");
/* 192:235 */     sql.append(" WHERE a.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 193:236 */     sql.append(" AND a.estado IN (:estado) ");
/* 194:237 */     sql.append(" AND (s.idSucursal = :idSucursal OR :idSucursal = 0) ");
/* 195:238 */     sql.append(" AND (t.idTipoAsiento = :idTipoAsiento OR :idTipoAsiento = 0) ");
/* 196:239 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/* 197:240 */     if (asientoFiltro != null) {
/* 198:241 */       sql.append(" AND a = :asiento ");
/* 199:    */     }
/* 200:244 */     sql.append(" ORDER BY a.fecha,t.nombre,a.numero ");
/* 201:    */     
/* 202:246 */     Query query = this.em.createQuery(sql.toString());
/* 203:247 */     query.setParameter("fechaDesde", fechaDesde);
/* 204:248 */     query.setParameter("fechaHasta", fechaHasta);
/* 205:249 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 206:250 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 207:251 */     query.setParameter("idTipoAsiento", Integer.valueOf(idTipoAsiento));
/* 208:252 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 209:253 */     if (asientoFiltro != null) {
/* 210:254 */       query.setParameter("asiento", asientoFiltro);
/* 211:    */     }
/* 212:257 */     return query.getResultList();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void mayorizarDesmayorizar(Date fechaDesde, Date fechaHasta, TipoAsiento tipoAsiento, Estado estado, int idOrganizacion)
/* 216:    */     throws ExcepcionAS2Financiero
/* 217:    */   {
/* 218:264 */     StringBuilder sql = new StringBuilder("UPDATE Asiento a set a.estado = :estado WHERE a.idOrganizacion=:idOrganizacion AND a.fecha BETWEEN :fechaDesde AND :fechaHasta AND a.estado != :estadoAnulado ");
/* 219:268 */     if ((null != tipoAsiento) && (tipoAsiento.getId() > 0)) {
/* 220:269 */       sql.append(" AND tipoAsiento =:tipoAsiento");
/* 221:    */     }
/* 222:272 */     Query query = this.em.createQuery(sql.toString());
/* 223:273 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 224:274 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 225:275 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 226:276 */     query.setParameter("estado", estado);
/* 227:277 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 228:278 */     if ((null != tipoAsiento) && (tipoAsiento.getId() > 0)) {
/* 229:279 */       query.setParameter("tipoAsiento", tipoAsiento);
/* 230:    */     }
/* 231:281 */     int result = query.executeUpdate();
/* 232:282 */     if (result < 0) {
/* 233:284 */       throw new ExcepcionAS2Financiero("No se pudo mayorizar");
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void mayorizarDesmayorizarPorAsiento(Estado estado, Asiento asiento)
/* 238:    */     throws ExcepcionAS2Financiero
/* 239:    */   {
/* 240:289 */     StringBuilder sql = new StringBuilder(" UPDATE Asiento a set a.estado = :estado WHERE a.idAsiento = :idAsiento AND a.estado != :estadoAnulado ");
/* 241:    */     
/* 242:    */ 
/* 243:292 */     Query query = this.em.createQuery(sql.toString());
/* 244:293 */     query.setParameter("estado", estado);
/* 245:294 */     query.setParameter("idAsiento", Integer.valueOf(asiento.getId()));
/* 246:295 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 247:296 */     int result = query.executeUpdate();
/* 248:297 */     if (result < 0) {
/* 249:298 */       throw new ExcepcionAS2Financiero("No se pudo mayorizar");
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Estado getEstado(Integer idAsiento)
/* 254:    */   {
/* 255:312 */     Query query = this.em.createQuery("SELECT a.estado FROM Asiento a WHERE a.idAsiento=:idAsiento");
/* 256:313 */     query.setParameter("idAsiento", idAsiento);
/* 257:314 */     Estado estado = (Estado)query.getSingleResult();
/* 258:    */     
/* 259:316 */     return estado;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void actualizarEstado(Integer idAsiento, Estado estado)
/* 263:    */     throws ExcepcionAS2Financiero
/* 264:    */   {
/* 265:331 */     if (estado.equals(Estado.ANULADO))
/* 266:    */     {
/* 267:333 */       StringBuilder sql = new StringBuilder();
/* 268:334 */       sql.append(" SELECT mb FROM MovimientoBancario mb ");
/* 269:335 */       sql.append(" LEFT  JOIN FETCH mb.conciliacionBancaria cb ");
/* 270:336 */       sql.append(" INNER JOIN mb.detalleAsiento da ");
/* 271:337 */       sql.append(" INNER JOIN da.asiento a ");
/* 272:338 */       sql.append(" WHERE a.idAsiento = :idAsiento ");
/* 273:339 */       Query query = this.em.createQuery(sql.toString());
/* 274:340 */       query.setParameter("idAsiento", idAsiento);
/* 275:341 */       List<MovimientoBancario> lista = query.getResultList();
/* 276:343 */       if (!lista.isEmpty())
/* 277:    */       {
/* 278:345 */         for (MovimientoBancario movimientoBancario : lista) {
/* 279:346 */           if (movimientoBancario.getConciliacionBancaria() != null) {
/* 280:347 */             throw new ExcepcionAS2Financiero("msg_error_anular_movimiento_conciliado");
/* 281:    */           }
/* 282:    */         }
/* 283:351 */         query = this.em.createQuery(" UPDATE MovimientoBancario mb SET mb.estado=:estado WHERE mb IN (:lista) ");
/* 284:352 */         query.setParameter("lista", lista);
/* 285:353 */         query.setParameter("estado", estado);
/* 286:354 */         query.executeUpdate();
/* 287:    */       }
/* 288:    */     }
/* 289:359 */     Query query = this.em.createQuery("UPDATE Asiento a SET a.estado=:estado WHERE a.idAsiento=:idAsiento");
/* 290:360 */     query.setParameter("idAsiento", idAsiento);
/* 291:361 */     query.setParameter("estado", estado);
/* 292:362 */     query.executeUpdate();
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List getReporteAsiento(Asiento asiento, boolean indicadorCentroCostos, Boolean resumido)
/* 296:    */   {
/* 297:378 */     StringBuilder sbSQL = new StringBuilder();
/* 298:    */     
/* 299:380 */     sbSQL = new StringBuilder();
/* 300:381 */     if (resumido.booleanValue())
/* 301:    */     {
/* 302:382 */       sbSQL.append(" SELECT a.numero, ta.nombre, a.concepto, a.fecha, cc.codigo, cc.nombre, '', '', SUM(da.debe), SUM(da.haber), '',  a.estado, '', ");
/* 303:383 */       sbSQL.append(" '','','','','','','','','','', '', '', cc.codigoAlterno, s.nombre, a.concepto2");
/* 304:384 */       sbSQL.append(" FROM DetalleAsiento da");
/* 305:385 */       sbSQL.append(" LEFT JOIN  da.dimensionContable1 d1 ");
/* 306:386 */       sbSQL.append(" LEFT JOIN  da.dimensionContable2 d2 ");
/* 307:387 */       sbSQL.append(" LEFT JOIN  da.dimensionContable3 d3 ");
/* 308:388 */       sbSQL.append(" LEFT JOIN  da.dimensionContable4 d4 ");
/* 309:389 */       sbSQL.append(" LEFT JOIN  da.dimensionContable5 d5 ");
/* 310:390 */       sbSQL.append(" INNER JOIN da.cuentaContable cc");
/* 311:391 */       sbSQL.append(" INNER JOIN da.asiento a ");
/* 312:392 */       sbSQL.append(" LEFT JOIN a.sucursal s ");
/* 313:393 */       sbSQL.append(" INNER JOIN a.tipoAsiento ta ");
/* 314:394 */       sbSQL.append(" WHERE a.idAsiento = :idAsiento");
/* 315:395 */       sbSQL.append(" GROUP BY cc.codigo, a.numero, ta.nombre, a.concepto, a.fecha, cc.nombre, cc.codigoAlterno, a.estado, s.nombre, a.concepto2");
/* 316:396 */       sbSQL.append(" ORDER BY cc.codigo, cc.nombre ");
/* 317:    */     }
/* 318:    */     else
/* 319:    */     {
/* 320:398 */       sbSQL.append(" SELECT a.numero, ta.nombre, a.concepto, a.fecha, cc.codigo, cc.nombre, '', '', da.debe, da.haber, da.descripcion, a.estado, s.nombre, ");
/* 321:399 */       sbSQL.append(" d1.codigo,d1.nombre,d2.codigo,d2.nombre,d3.codigo,d3.nombre,d4.codigo,d4.nombre,d5.codigo,d5.nombre, a.usuarioCreacion, da.descripcion2, cc.codigoAlterno, s.nombre ");
/* 322:400 */       sbSQL.append(" FROM DetalleAsiento da");
/* 323:401 */       sbSQL.append(" LEFT JOIN  da.dimensionContable1 d1 ");
/* 324:402 */       sbSQL.append(" LEFT JOIN  da.dimensionContable2 d2 ");
/* 325:403 */       sbSQL.append(" LEFT JOIN  da.dimensionContable3 d3 ");
/* 326:404 */       sbSQL.append(" LEFT JOIN  da.dimensionContable4 d4 ");
/* 327:405 */       sbSQL.append(" LEFT JOIN  da.dimensionContable5 d5 ");
/* 328:406 */       sbSQL.append(" INNER JOIN da.cuentaContable cc");
/* 329:407 */       sbSQL.append(" INNER JOIN da.asiento a ");
/* 330:408 */       sbSQL.append(" LEFT JOIN a.sucursal s ");
/* 331:409 */       sbSQL.append(" INNER JOIN a.tipoAsiento ta ");
/* 332:410 */       sbSQL.append(" WHERE a.idAsiento = :idAsiento");
/* 333:412 */       if (asiento.isIndicadorAutomatico()) {
/* 334:413 */         sbSQL.append(" ORDER BY da.debe DESC, cc.codigo ASC");
/* 335:    */       } else {
/* 336:415 */         sbSQL.append(" ORDER BY da.idDetalleAsiento ");
/* 337:    */       }
/* 338:    */     }
/* 339:418 */     Query qAsiento = this.em.createQuery(sbSQL.toString());
/* 340:419 */     qAsiento.setParameter("idAsiento", Integer.valueOf(asiento.getIdAsiento()));
/* 341:420 */     List lista = qAsiento.getResultList();
/* 342:    */     try
/* 343:    */     {
/* 344:426 */       Query qRetencion = this.em.createQuery("SELECT f.pago.asiento.idAsiento FROM FacturaProveedorSRI f WHERE f.facturaProveedor.asiento.idAsiento=:idAsiento");
/* 345:    */       
/* 346:428 */       qRetencion.setParameter("idAsiento", Integer.valueOf(asiento.getIdAsiento()));
/* 347:    */       
/* 348:430 */       int idAsientoRetencion = ((Integer)qRetencion.getSingleResult()).intValue();
/* 349:    */       
/* 350:432 */       qAsiento.setParameter("idAsiento", Integer.valueOf(idAsientoRetencion));
/* 351:433 */       lista.addAll(qAsiento.getResultList());
/* 352:    */     }
/* 353:    */     catch (Exception e)
/* 354:    */     {
/* 355:436 */       e.printStackTrace();
/* 356:    */     }
/* 357:439 */     return lista;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public List<CuentaContable> listaCuentaOrganizacionDiferente(List<CuentaContable> listaCuentasContables, int idOrganizacion)
/* 361:    */   {
/* 362:445 */     StringBuilder sbSQL = new StringBuilder();
/* 363:    */     
/* 364:447 */     sbSQL = new StringBuilder();
/* 365:448 */     sbSQL.append(" SELECT cc ");
/* 366:449 */     sbSQL.append(" FROM CuentaContable cc ");
/* 367:450 */     sbSQL.append(" WHERE cc IN (:listaCuentasContables) ");
/* 368:451 */     sbSQL.append(" AND cc.idOrganizacion !=:idOrganizacion ");
/* 369:    */     
/* 370:453 */     Query qAsiento = this.em.createQuery(sbSQL.toString());
/* 371:454 */     qAsiento.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 372:455 */     qAsiento.setParameter("listaCuentasContables", listaCuentasContables);
/* 373:    */     
/* 374:457 */     return qAsiento.getResultList();
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void actualizarIndicadorAsientoAerolineas(Integer idAsiento)
/* 378:    */   {
/* 379:463 */     Query query = this.em.createQuery("UPDATE Asiento a SET a.indicadorAutomatico = 0 WHERE a.idAsiento=:idAsiento");
/* 380:464 */     query.setParameter("idAsiento", idAsiento);
/* 381:465 */     query.executeUpdate();
/* 382:    */   }
/* 383:    */   
/* 384:    */   public Long verificarExistenciaNumero(Asiento asiento)
/* 385:    */   {
/* 386:470 */     StringBuilder sql = new StringBuilder();
/* 387:471 */     sql.append(" SELECT COUNT(1) FROM Asiento a ");
/* 388:472 */     sql.append(" WHERE a.idOrganizacion = :idOrganizacion ");
/* 389:473 */     sql.append(" AND a.tipoAsiento = :tipoAsiento ");
/* 390:474 */     sql.append(" AND a.numero = :numero ");
/* 391:475 */     sql.append(" AND a.sucursal = :sucursal ");
/* 392:476 */     if (asiento.getIdAsiento() != 0) {
/* 393:477 */       sql.append(" AND a != :asiento ");
/* 394:    */     }
/* 395:479 */     Query query = this.em.createQuery(sql.toString());
/* 396:480 */     query.setParameter("idOrganizacion", Integer.valueOf(asiento.getIdOrganizacion()));
/* 397:481 */     query.setParameter("tipoAsiento", asiento.getTipoAsiento());
/* 398:482 */     query.setParameter("numero", asiento.getNumero());
/* 399:483 */     query.setParameter("sucursal", asiento.getSucursal());
/* 400:484 */     if (asiento.getIdAsiento() != 0) {
/* 401:485 */       query.setParameter("asiento", asiento);
/* 402:    */     }
/* 403:488 */     return (Long)query.getSingleResult();
/* 404:    */   }
/* 405:    */   
/* 406:    */   public Long verificarExistenciaMovimientoBancario(int idAsiento)
/* 407:    */   {
/* 408:492 */     StringBuilder sql = new StringBuilder();
/* 409:493 */     sql.append(" SELECT COUNT(1) FROM Asiento a ");
/* 410:494 */     sql.append(" INNER JOIN a.listaDetalleAsiento lda ");
/* 411:495 */     sql.append(" INNER JOIN lda.movimientoBancario mb ");
/* 412:496 */     sql.append(" WHERE a.idAsiento = :idAsiento ");
/* 413:497 */     sql.append(" and mb.conciliacionBancaria is not null ");
/* 414:    */     
/* 415:499 */     Query query = this.em.createQuery(sql.toString());
/* 416:500 */     query.setParameter("idAsiento", Integer.valueOf(idAsiento));
/* 417:    */     
/* 418:502 */     return (Long)query.getSingleResult();
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void eliminarMovimientoBancarioAsiento(Asiento asiento)
/* 422:    */   {
/* 423:507 */     StringBuilder sql = new StringBuilder();
/* 424:508 */     sql.append(" DELETE FROM MovimientoBancario mb ");
/* 425:509 */     sql.append(" WHERE mb.idMovimientoBancario in ");
/* 426:510 */     sql.append("\t(SELECT mb2.idMovimientoBancario from MovimientoBancario mb2 ");
/* 427:511 */     sql.append("\tINNER JOIN mb2.formaPago fp ");
/* 428:512 */     sql.append("\tINNER JOIN mb2.detalleAsiento da ");
/* 429:513 */     sql.append("\tINNER JOIN da.asiento a ");
/* 430:514 */     sql.append("\tWHERE a = :asiento and fp.indicadorCheque = false ");
/* 431:515 */     sql.append("\t)");
/* 432:    */     
/* 433:517 */     Query query = this.em.createQuery(sql.toString());
/* 434:518 */     query.setParameter("asiento", asiento);
/* 435:519 */     query.executeUpdate();
/* 436:    */   }
/* 437:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.AsientoDao
 * JD-Core Version:    0.7.0.1
 */