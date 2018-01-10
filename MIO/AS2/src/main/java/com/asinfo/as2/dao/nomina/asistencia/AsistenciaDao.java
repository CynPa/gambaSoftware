/*   1:    */ package com.asinfo.as2.dao.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.nomina.asistencia.Asistencia;
/*   7:    */ import com.asinfo.as2.entities.nomina.asistencia.DetallePlanPersonalizadoHorarioEmpleado;
/*   8:    */ import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
/*   9:    */ import com.asinfo.as2.entities.nomina.asistencia.MarcacionReloj;
/*  10:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Calendar;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.Stateless;
/*  17:    */ import javax.persistence.EntityManager;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.TypedQuery;
/*  21:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  22:    */ import javax.persistence.criteria.CriteriaQuery;
/*  23:    */ import javax.persistence.criteria.Expression;
/*  24:    */ import javax.persistence.criteria.Fetch;
/*  25:    */ import javax.persistence.criteria.Join;
/*  26:    */ import javax.persistence.criteria.JoinType;
/*  27:    */ import javax.persistence.criteria.Order;
/*  28:    */ import javax.persistence.criteria.Path;
/*  29:    */ import javax.persistence.criteria.Predicate;
/*  30:    */ import javax.persistence.criteria.Root;
/*  31:    */ 
/*  32:    */ @Stateless
/*  33:    */ public class AsistenciaDao
/*  34:    */   extends AbstractDaoAS2<Asistencia>
/*  35:    */ {
/*  36:    */   public AsistenciaDao()
/*  37:    */   {
/*  38: 52 */     super(Asistencia.class);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Asistencia cargarDetalle(int idAsistencia)
/*  42:    */   {
/*  43: 58 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  44: 59 */     CriteriaQuery<Asistencia> criteriaQuery = cb.createQuery(Asistencia.class);
/*  45: 60 */     Root<Asistencia> from = criteriaQuery.from(Asistencia.class);
/*  46: 61 */     from.fetch("tipoFalta", JoinType.LEFT);
/*  47: 62 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*  48: 63 */     empleado.fetch("empresa", JoinType.LEFT);
/*  49: 64 */     empleado.fetch("departamento", JoinType.LEFT);
/*  50: 65 */     Fetch<Object, Object> subsidioEmpleado = from.fetch("subsidioEmpleado", JoinType.LEFT);
/*  51: 66 */     subsidioEmpleado.fetch("tipoSubsidio", JoinType.LEFT);
/*  52:    */     
/*  53: 68 */     criteriaQuery.where(cb.equal(from.get("idAsistencia"), Integer.valueOf(idAsistencia)));
/*  54: 69 */     CriteriaQuery<Asistencia> select = criteriaQuery.select(from);
/*  55:    */     
/*  56: 71 */     Asistencia asistencia = (Asistencia)this.em.createQuery(select).getSingleResult();
/*  57: 72 */     this.em.detach(asistencia);
/*  58:    */     
/*  59: 74 */     return asistencia;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Empleado> obtenerHorariosPorDiaSemana(int idOrganizacion, Date fecha, int diaMes, int diaSemana, Departamento departamento)
/*  63:    */   {
/*  64: 80 */     StringBuilder sql = new StringBuilder();
/*  65: 81 */     sql.append("SELECT e FROM Empleado e");
/*  66: 82 */     sql.append(" JOIN FETCH e.horarioEmpleado he");
/*  67: 83 */     sql.append(" LEFT JOIN FETCH e.cargoEmpleado ce");
/*  68: 84 */     sql.append(" JOIN FETCH he.turno" + diaSemana);
/*  69: 85 */     sql.append(" WHERE e.idOrganizacion=:idOrganizacion");
/*  70: 86 */     sql.append(" AND ce.indicadorRegistraAsistencia IS TRUE");
/*  71: 87 */     if (departamento != null) {
/*  72: 88 */       sql.append(" AND e.departamento = :departamento");
/*  73:    */     }
/*  74: 90 */     sql.append(" AND NOT EXISTS (");
/*  75: 91 */     sql.append(" SELECT 1 FROM Asistencia asi");
/*  76: 92 */     sql.append(" JOIN asi.empleado e2");
/*  77: 93 */     sql.append(" WHERE asi.idOrganizacion = :idOrganizacion");
/*  78: 94 */     sql.append(" AND e.idEmpleado = e2.idEmpleado");
/*  79:    */     
/*  80: 96 */     sql.append(" AND asi.diaMes = :diaMes ");
/*  81: 97 */     sql.append(" AND asi.fecha = :fecha");
/*  82: 98 */     sql.append(" AND asi.indicadorCreadaManualmente = true");
/*  83: 99 */     sql.append(" )");
/*  84:    */     
/*  85:101 */     sql.append(" AND EXISTS (");
/*  86:102 */     sql.append(" \t\tSELECT 1 FROM HistoricoEmpleado he");
/*  87:103 */     sql.append(" \t\tJOIN  he.empleado emp");
/*  88:104 */     sql.append(" \t\tWHERE emp=e");
/*  89:105 */     sql.append(" \t\tAND CASE WHEN he.fechaSalida IS NOT NULL THEN he.fechaSalida ELSE :fecha END >=:fecha");
/*  90:106 */     sql.append(" )");
/*  91:    */     
/*  92:    */ 
/*  93:109 */     sql.append(" AND (he.indicadorDiaOpcional" + diaSemana + " = false or (EXISTS(");
/*  94:110 */     sql.append("\t\tSELECT 1 FROM MarcacionReloj mr");
/*  95:111 */     sql.append("\t\tJOIN mr.empleado empl");
/*  96:112 */     sql.append("\t\tWHERE mr.idOrganizacion = :idOrganizacion");
/*  97:113 */     sql.append("\t\tAND mr.fecha = :fecha");
/*  98:114 */     sql.append("\t\tAND empl = e");
/*  99:115 */     sql.append(" )))");
/* 100:    */     
/* 101:117 */     Query query = this.em.createQuery(sql.toString());
/* 102:118 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 103:119 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 104:120 */     query.setParameter("diaMes", Integer.valueOf(diaMes));
/* 105:121 */     if (departamento != null) {
/* 106:122 */       query.setParameter("departamento", departamento);
/* 107:    */     }
/* 108:125 */     return query.getResultList();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<DetallePlanPersonalizadoHorarioEmpleado> obtenerHorariosMensual(int idOrganizacion, Calendar calFecha, int diaMes, Departamento departamento)
/* 112:    */   {
/* 113:132 */     int anio = calFecha.get(1);
/* 114:    */     
/* 115:134 */     Mes mes = Mes.values()[calFecha.get(2)];
/* 116:    */     
/* 117:136 */     StringBuilder sql = new StringBuilder();
/* 118:137 */     sql.append(" SELECT dpl FROM DetallePlanPersonalizadoHorarioEmpleado dpl");
/* 119:138 */     sql.append(" JOIN FETCH dpl.turno" + diaMes);
/* 120:139 */     sql.append(" JOIN FETCH dpl.empleado e");
/* 121:140 */     sql.append(" JOIN e.cargoEmpleado ce");
/* 122:141 */     sql.append(" JOIN dpl.planPersonalizadoHorarioEmpleado pl");
/* 123:142 */     sql.append(" WHERE e.idOrganizacion=:idOrganizacion");
/* 124:143 */     sql.append(" AND ce.indicadorRegistraAsistencia IS TRUE");
/* 125:144 */     sql.append(" AND pl.anno=:anio");
/* 126:145 */     sql.append(" AND pl.mes=:mes");
/* 127:146 */     if (departamento != null) {
/* 128:147 */       sql.append(" AND e.departamento = :departamento");
/* 129:    */     }
/* 130:149 */     sql.append(" AND NOT EXISTS (");
/* 131:150 */     sql.append(" SELECT 1 FROM Asistencia asi ");
/* 132:151 */     sql.append(" WHERE asi.idOrganizacion = :idOrganizacion");
/* 133:    */     
/* 134:153 */     sql.append(" AND asi.diaMes = :diaMes ");
/* 135:154 */     sql.append(" AND asi.empleado = e ");
/* 136:155 */     sql.append(" AND asi.fecha = :fecha");
/* 137:156 */     sql.append(" AND asi.indicadorCreadaManualmente = true");
/* 138:157 */     sql.append(" )");
/* 139:    */     
/* 140:159 */     sql.append(" AND EXISTS (");
/* 141:160 */     sql.append(" \t\tSELECT 1 FROM HistoricoEmpleado he");
/* 142:161 */     sql.append(" \t\tJOIN  he.empleado emp");
/* 143:162 */     sql.append(" \t\tWHERE emp=e");
/* 144:163 */     sql.append(" \t\tAND CASE WHEN he.fechaSalida IS NOT NULL THEN he.fechaSalida ELSE :fecha END >=:fecha");
/* 145:164 */     sql.append(" )");
/* 146:165 */     Query query = this.em.createQuery(sql.toString());
/* 147:    */     
/* 148:167 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 149:168 */     query.setParameter("anio", Integer.valueOf(anio));
/* 150:169 */     query.setParameter("mes", mes);
/* 151:170 */     query.setParameter("diaMes", Integer.valueOf(diaMes));
/* 152:171 */     query.setParameter("fecha", calFecha.getTime(), TemporalType.DATE);
/* 153:172 */     if (departamento != null) {
/* 154:173 */       query.setParameter("departamento", departamento);
/* 155:    */     }
/* 156:176 */     return query.getResultList();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<Asistencia> obtenerListaPorPagina(int startIndex, int pageSize, Map<String, String> filters, Date fechaDesde, Date fechaHasta)
/* 160:    */   {
/* 161:180 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 162:181 */     CriteriaQuery<Asistencia> criteriaQuery = criteriaBuilder.createQuery(Asistencia.class);
/* 163:182 */     Root<Asistencia> from = criteriaQuery.from(Asistencia.class);
/* 164:183 */     from.fetch("tipoFalta", JoinType.LEFT);
/* 165:184 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/* 166:185 */     empleado.fetch("empresa", JoinType.LEFT);
/* 167:186 */     empleado.fetch("departamento", JoinType.LEFT);
/* 168:187 */     Fetch<Object, Object> subsidioEmpleado = from.fetch("subsidioEmpleado", JoinType.LEFT);
/* 169:188 */     subsidioEmpleado.fetch("tipoSubsidio", JoinType.LEFT);
/* 170:    */     
/* 171:    */ 
/* 172:191 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 173:    */     
/* 174:193 */     Path<Date> pathFechaFiltro = from.get("fechaFiltro");
/* 175:194 */     Path<Date> pathFecha = from.get("fecha");
/* 176:    */     
/* 177:196 */     Expression<Boolean> whereFechaDesde = criteriaBuilder.greaterThanOrEqualTo(pathFechaFiltro, fechaDesde);
/* 178:    */     
/* 179:198 */     Expression<Boolean> whereFechaHasta = criteriaBuilder.lessThanOrEqualTo(pathFechaFiltro, fechaHasta);
/* 180:    */     
/* 181:200 */     expresiones.add(whereFechaHasta);
/* 182:201 */     expresiones.add(whereFechaDesde);
/* 183:    */     
/* 184:203 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 185:    */     
/* 186:205 */     Path<String> pathEmpleado = from.join("empleado", JoinType.LEFT).get("apellidos");
/* 187:    */     
/* 188:207 */     Order ordenEmpleado = criteriaBuilder.asc(pathEmpleado);
/* 189:208 */     Order ordenFecha = criteriaBuilder.asc(pathFecha);
/* 190:209 */     Order ordenEntrada = criteriaBuilder.asc(from.get("entrada"));
/* 191:210 */     List<Order> listaOrden = new ArrayList();
/* 192:211 */     listaOrden.add(ordenEmpleado);
/* 193:212 */     listaOrden.add(ordenFecha);
/* 194:213 */     listaOrden.add(ordenEntrada);
/* 195:    */     
/* 196:215 */     CriteriaQuery<Asistencia> select = criteriaQuery.select(from).orderBy(listaOrden);
/* 197:    */     
/* 198:217 */     TypedQuery<Asistencia> typedQuery = this.em.createQuery(select);
/* 199:218 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 200:219 */     return typedQuery.getResultList();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public int contarPorCriterio(Map<String, String> filtros, Date fechaDesde, Date fechaHasta)
/* 204:    */   {
/* 205:224 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 206:225 */     CriteriaQuery<Long> cq = cb.createQuery(Long.class);
/* 207:226 */     Root<Asistencia> from = cq.from(Asistencia.class);
/* 208:227 */     cq.select(cb.count(from));
/* 209:    */     
/* 210:    */ 
/* 211:230 */     List<Expression<?>> expresiones = obtenerExpresiones(filtros, cb, from);
/* 212:    */     
/* 213:232 */     Path<Date> pathFecha = from.get("fecha");
/* 214:    */     
/* 215:234 */     Expression<Boolean> whereFechaDesde = cb.greaterThanOrEqualTo(pathFecha, fechaDesde);
/* 216:    */     
/* 217:236 */     Expression<Boolean> whereFechaHasta = cb.lessThanOrEqualTo(pathFecha, fechaHasta);
/* 218:    */     
/* 219:238 */     expresiones.add(whereFechaHasta);
/* 220:239 */     expresiones.add(whereFechaDesde);
/* 221:241 */     if (!expresiones.isEmpty()) {
/* 222:242 */       cq.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 223:    */     }
/* 224:245 */     return ((Long)this.em.createQuery(cq).getSingleResult()).intValue();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<MarcacionReloj> getMarcacionesRelojPorFecha(int idOrganizacion, Date fecha, Departamento departamento)
/* 228:    */   {
/* 229:265 */     StringBuilder sql = new StringBuilder();
/* 230:266 */     sql.append(" SELECT mr FROM MarcacionReloj mr ");
/* 231:267 */     sql.append(" JOIN mr.empleado e ");
/* 232:268 */     sql.append(" WHERE mr.idOrganizacion = :idOrganizacion");
/* 233:269 */     sql.append(" AND mr.fecha = :fecha");
/* 234:270 */     if (departamento != null) {
/* 235:271 */       sql.append(" AND e.departamento = :departamento");
/* 236:    */     }
/* 237:273 */     sql.append(" ORDER BY e.idEmpleado, mr.marcacion");
/* 238:    */     
/* 239:275 */     Query query = this.em.createQuery(sql.toString());
/* 240:276 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 241:277 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 242:278 */     if (departamento != null) {
/* 243:279 */       query.setParameter("departamento", departamento);
/* 244:    */     }
/* 245:282 */     return query.getResultList();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<Asistencia> getAsistenciaPorFecha(int idOrganizacion, Date fecha, Departamento departamento, boolean sinAsistenciaPadre)
/* 249:    */   {
/* 250:287 */     StringBuilder sql = new StringBuilder();
/* 251:288 */     sql.append(" SELECT asi FROM Asistencia asi ");
/* 252:289 */     sql.append(" JOIN asi.empleado e ");
/* 253:290 */     sql.append(" LEFT JOIN FETCH asi.asistenciaPadre asipa ");
/* 254:291 */     sql.append(" WHERE asi.idOrganizacion = :idOrganizacion");
/* 255:292 */     sql.append(" AND asi.fecha = :fecha");
/* 256:293 */     if (departamento != null) {
/* 257:294 */       sql.append(" AND e.departamento = :departamento");
/* 258:    */     }
/* 259:296 */     if (sinAsistenciaPadre) {
/* 260:297 */       sql.append(" AND asipa IS NULL");
/* 261:    */     }
/* 262:299 */     sql.append(" ORDER BY e.idEmpleado, asi.entrada");
/* 263:    */     
/* 264:301 */     Query query = this.em.createQuery(sql.toString());
/* 265:302 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 266:303 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 267:304 */     if (departamento != null) {
/* 268:305 */       query.setParameter("departamento", departamento);
/* 269:    */     }
/* 270:307 */     return query.getResultList();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<EmpleadoAsistencia> obtenerListaHoraExtraEmpleado(Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 274:    */   {
/* 275:312 */     List<EmpleadoAsistencia> lista = new ArrayList();
/* 276:    */     
/* 277:314 */     StringBuilder sql = new StringBuilder();
/* 278:315 */     sql.append(" SELECT new EmpleadoAsistencia(em.idEmpleado, em.empresa.identificacion, em.nombres, em.apellidos, dep.nombre,");
/* 279:316 */     sql.append(" SUM(a.horasExtras25Aprobadas), SUM(a.horasExtras50Aprobadas), SUM(a.horasExtras100FeriadoAprobadas + a.horasExtras100Aprobadas))");
/* 280:317 */     sql.append(" FROM Asistencia a ");
/* 281:318 */     sql.append(" INNER JOIN a.empleado em ");
/* 282:319 */     sql.append(" INNER JOIN em.cargoEmpleado ce ");
/* 283:320 */     sql.append(" INNER JOIN em.departamento dep ");
/* 284:    */     
/* 285:322 */     sql.append(" WHERE ce.indicadorGanaHorasExtras = true ");
/* 286:323 */     if ((departamento != null) && (departamento.getId() != 0)) {
/* 287:324 */       sql.append(" AND dep.idDepartamento =:idDepartamento ");
/* 288:    */     }
/* 289:326 */     sql.append(" AND a.fechaFiltro BETWEEN :fechaDesde AND :fechaHasta");
/* 290:327 */     sql.append(" AND em.idOrganizacion =:idOrganizacion ");
/* 291:    */     
/* 292:329 */     sql.append(" GROUP BY em.idEmpleado, em.empresa.identificacion, em.nombres, em.apellidos, dep.nombre ");
/* 293:330 */     sql.append(" HAVING SUM(a.horasExtras25Aprobadas) > 0 ");
/* 294:331 */     sql.append(" OR SUM(a.horasExtras50Aprobadas) > 0 ");
/* 295:332 */     sql.append(" OR SUM(a.horasExtras100Feriado + a.horasExtras100Aprobadas) > 0 ");
/* 296:333 */     sql.append(" ORDER BY dep.nombre, em.apellidos ");
/* 297:    */     
/* 298:335 */     Query query = this.em.createQuery(sql.toString());
/* 299:336 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 300:337 */     if ((departamento != null) && (departamento.getId() != 0)) {
/* 301:338 */       query.setParameter("idDepartamento", Integer.valueOf(departamento.getId()));
/* 302:    */     }
/* 303:340 */     query.setParameter("fechaDesde", fechaDesde);
/* 304:341 */     query.setParameter("fechaHasta", fechaHasta);
/* 305:    */     
/* 306:343 */     lista = query.getResultList();
/* 307:344 */     return lista;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public List<EmpleadoAsistencia> obtenerListaFaltasEmpleado(Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 311:    */   {
/* 312:349 */     StringBuilder sql = new StringBuilder();
/* 313:350 */     sql.append("SELECT new EmpleadoAsistencia(em.idEmpleado, em.empresa.identificacion,");
/* 314:351 */     sql.append(" em.nombres, em.apellidos, dep.nombre, a.diaMes, COALESCE(tf.idTipoFalta, 0))");
/* 315:352 */     sql.append(" FROM Asistencia a");
/* 316:353 */     sql.append(" LEFT JOIN a.tipoFalta tf");
/* 317:354 */     sql.append(" LEFT JOIN a.subsidioEmpleado sub");
/* 318:355 */     sql.append(" INNER JOIN a.empleado em");
/* 319:356 */     sql.append(" INNER JOIN em.cargoEmpleado ce");
/* 320:357 */     sql.append(" INNER JOIN em.departamento dep");
/* 321:    */     
/* 322:359 */     sql.append(" WHERE em.activo = true");
/* 323:360 */     if ((departamento != null) && (departamento.getId() != 0)) {
/* 324:361 */       sql.append(" AND dep.idDepartamento =:idDepartamento");
/* 325:    */     }
/* 326:363 */     sql.append(" AND a.marcacionEntrada IS NULL");
/* 327:364 */     sql.append(" AND a.marcacionSalida IS NULL");
/* 328:365 */     sql.append(" AND sub IS NULL");
/* 329:366 */     sql.append(" AND a.indicadorVacacion = false");
/* 330:367 */     sql.append(" AND a.indicadorDiaFestivo = false");
/* 331:368 */     sql.append(" AND a.fecha <= :fechaHasta");
/* 332:369 */     sql.append(" AND a.fecha >= :fechaDesde");
/* 333:370 */     sql.append(" AND a.asistenciaPadre IS NULL");
/* 334:371 */     sql.append(" AND em.idOrganizacion =:idOrganizacion");
/* 335:372 */     sql.append(" AND NOT EXISTS (");
/* 336:373 */     sql.append("\t\tSELECT 1 FROM DetallePermisoEmpleado dpe");
/* 337:374 */     sql.append("\t\tINNER JOIN dpe.permisoEmpleado pe");
/* 338:375 */     sql.append("\t\tINNER JOIN pe.historicoEmpleado he");
/* 339:376 */     sql.append("\t\tINNER JOIN he.empleado empl");
/* 340:377 */     sql.append("\t\twhere dpe.fechaPermiso=a.fecha");
/* 341:378 */     sql.append("\t\tAND dpe.indicadorDiaCompleto=true");
/* 342:379 */     sql.append("\t\tAND empl=em");
/* 343:380 */     sql.append(")");
/* 344:381 */     sql.append(" ORDER BY dep.nombre, em.apellidos");
/* 345:    */     
/* 346:383 */     Query query = this.em.createQuery(sql.toString());
/* 347:384 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 348:385 */     if ((departamento != null) && (departamento.getId() != 0)) {
/* 349:386 */       query.setParameter("idDepartamento", Integer.valueOf(departamento.getId()));
/* 350:    */     }
/* 351:388 */     query.setParameter("fechaDesde", fechaDesde);
/* 352:389 */     query.setParameter("fechaHasta", fechaHasta);
/* 353:390 */     return query.getResultList();
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void eliminarAsistencia(Asistencia asistencia)
/* 357:    */   {
/* 358:394 */     StringBuilder sql = new StringBuilder();
/* 359:395 */     sql.append(" DELETE FROM Asistencia a");
/* 360:396 */     sql.append(" WHERE a.asistenciaPadre = :asistenciaPadre");
/* 361:    */     
/* 362:398 */     Query query = this.em.createQuery(sql.toString());
/* 363:399 */     query.setParameter("asistenciaPadre", asistencia);
/* 364:400 */     query.executeUpdate();
/* 365:    */     
/* 366:402 */     StringBuilder sql2 = new StringBuilder();
/* 367:403 */     sql2.append("DELETE FROM Asistencia a");
/* 368:404 */     sql2.append(" WHERE a = :asistencia");
/* 369:    */     
/* 370:406 */     Query query2 = this.em.createQuery(sql2.toString());
/* 371:407 */     query2.setParameter("asistencia", asistencia);
/* 372:408 */     query2.executeUpdate();
/* 373:    */   }
/* 374:    */   
/* 375:    */   public Asistencia getAsistenciaHija(Asistencia asistencia)
/* 376:    */   {
/* 377:412 */     StringBuilder sql = new StringBuilder();
/* 378:413 */     sql.append(" SELECT asi FROM Asistencia asi ");
/* 379:414 */     sql.append(" JOIN asi.empleado e ");
/* 380:415 */     sql.append(" LEFT JOIN FETCH asi.asistenciaPadre asipa ");
/* 381:416 */     sql.append(" WHERE asipa = :asis");
/* 382:    */     
/* 383:418 */     Query query = this.em.createQuery(sql.toString());
/* 384:419 */     query.setParameter("asis", asistencia);
/* 385:420 */     query.setMaxResults(1);
/* 386:421 */     return (Asistencia)query.getSingleResult();
/* 387:    */   }
/* 388:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.nomina.asistencia.AsistenciaDao
 * JD-Core Version:    0.7.0.1
 */