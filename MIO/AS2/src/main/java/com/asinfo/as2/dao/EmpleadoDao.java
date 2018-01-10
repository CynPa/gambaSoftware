/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CargaEmpleado;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Rubro;
/*   8:    */ import com.asinfo.as2.entities.RubroEmpleado;
/*   9:    */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*  10:    */ import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
/*  11:    */ import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Set;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ import javax.persistence.EntityManager;
/*  22:    */ import javax.persistence.NoResultException;
/*  23:    */ import javax.persistence.Query;
/*  24:    */ import javax.persistence.TypedQuery;
/*  25:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  26:    */ import javax.persistence.criteria.CriteriaQuery;
/*  27:    */ import javax.persistence.criteria.Expression;
/*  28:    */ import javax.persistence.criteria.Fetch;
/*  29:    */ import javax.persistence.criteria.Join;
/*  30:    */ import javax.persistence.criteria.JoinType;
/*  31:    */ import javax.persistence.criteria.Path;
/*  32:    */ import javax.persistence.criteria.Predicate;
/*  33:    */ import javax.persistence.criteria.Root;
/*  34:    */ 
/*  35:    */ @Stateless
/*  36:    */ public class EmpleadoDao
/*  37:    */   extends AbstractDaoAS2<Empleado>
/*  38:    */ {
/*  39:    */   private static final String EMPLEADO = "empleado";
/*  40:    */   private static final String ID_EMPRESA = "idEmpresa";
/*  41:    */   private static final String NOMBRE = "nombre";
/*  42:    */   
/*  43:    */   public EmpleadoDao()
/*  44:    */   {
/*  45: 58 */     super(Empleado.class);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<Empleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  49:    */   {
/*  50: 67 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  51: 68 */     CriteriaQuery<Empleado> criteriaQuery = criteriaBuilder.createQuery(Empleado.class);
/*  52: 69 */     Root<Empleado> from = criteriaQuery.from(Empleado.class);
/*  53: 70 */     from.fetch("cargoEmpleado", JoinType.LEFT);
/*  54: 71 */     from.fetch("departamento", JoinType.LEFT);
/*  55: 72 */     from.fetch("titulo", JoinType.LEFT);
/*  56: 73 */     from.fetch("tipoDiscapacidad", JoinType.LEFT);
/*  57: 74 */     from.fetch("tipoContrato", JoinType.LEFT);
/*  58: 75 */     from.fetch("categoriaRubro", JoinType.LEFT);
/*  59: 76 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/*  60: 77 */     empresa.fetch("tipoIdentificacion", JoinType.LEFT);
/*  61: 78 */     empresa.fetch("categoriaEmpresa", JoinType.LEFT);
/*  62:    */     
/*  63: 80 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  64: 81 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  65: 82 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  66:    */     
/*  67: 84 */     CriteriaQuery<Empleado> select = criteriaQuery.select(from);
/*  68: 85 */     TypedQuery<Empleado> typedQuery = this.em.createQuery(select);
/*  69: 86 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  70: 87 */     return typedQuery.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<Empleado> listaAgenteComercialCombo()
/*  74:    */   {
/*  75: 92 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  76: 93 */     CriteriaQuery<Empleado> criteriaQuery = criteriaBuilder.createQuery(Empleado.class);
/*  77: 94 */     Root<Empleado> from = criteriaQuery.from(Empleado.class);
/*  78: 95 */     from.fetch("empresa", JoinType.LEFT);
/*  79:    */     
/*  80: 97 */     Predicate filtro = criteriaBuilder.and(new Predicate[] { criteriaBuilder.equal(from.get("indicadorAgenteComercial"), Boolean.valueOf(true)) });
/*  81:    */     
/*  82: 99 */     criteriaQuery.where(filtro);
/*  83:    */     
/*  84:101 */     CriteriaQuery<Empleado> select = criteriaQuery.select(from);
/*  85:102 */     TypedQuery<Empleado> typedQuery = this.em.createQuery(select);
/*  86:    */     
/*  87:104 */     return typedQuery.getResultList();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<Empleado> obtenerListaCombo()
/*  91:    */   {
/*  92:110 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  93:111 */     CriteriaQuery<Empleado> criteriaQuery = criteriaBuilder.createQuery(Empleado.class);
/*  94:112 */     Root<Empleado> from = criteriaQuery.from(Empleado.class);
/*  95:113 */     from.fetch("empresa", JoinType.LEFT);
/*  96:    */     
/*  97:115 */     CriteriaQuery<Empleado> select = criteriaQuery.select(from);
/*  98:116 */     TypedQuery<Empleado> typedQuery = this.em.createQuery(select);
/*  99:    */     
/* 100:118 */     return typedQuery.getResultList();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<Empleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 104:    */   {
/* 105:124 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 106:125 */     CriteriaQuery<Empleado> criteriaQuery = criteriaBuilder.createQuery(Empleado.class);
/* 107:126 */     Root<Empleado> from = criteriaQuery.from(Empleado.class);
/* 108:127 */     from.fetch("empresa", JoinType.LEFT);
/* 109:128 */     from.fetch("grupoTrabajo", JoinType.LEFT);
/* 110:    */     
/* 111:130 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 112:131 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 113:132 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 114:    */     
/* 115:134 */     CriteriaQuery<Empleado> select = criteriaQuery.select(from);
/* 116:135 */     TypedQuery<Empleado> typedQuery = this.em.createQuery(select);
/* 117:    */     
/* 118:137 */     return typedQuery.getResultList();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<Empleado> obtenerListaComboMultiple(String sortField, boolean sortOrder, Map<String, String> filters)
/* 122:    */   {
/* 123:141 */     boolean notSetMaxResults = false;
/* 124:142 */     if (filters == null) {
/* 125:143 */       filters = new HashMap();
/* 126:    */     }
/* 127:145 */     if (filters.get("notSetMaxResults") != null)
/* 128:    */     {
/* 129:146 */       notSetMaxResults = true;
/* 130:147 */       filters.remove("notSetMaxResults");
/* 131:    */     }
/* 132:150 */     filters = agregarFiltrosOrganizacion(filters);
/* 133:    */     
/* 134:152 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 135:153 */     CriteriaQuery<Empleado> criteriaQuery = criteriaBuilder.createQuery(Empleado.class);
/* 136:154 */     Root<Empleado> from = criteriaQuery.from(Empleado.class);
/* 137:    */     
/* 138:156 */     Predicate conjunction = criteriaBuilder.conjunction();
/* 139:157 */     Predicate disjunction = criteriaBuilder.disjunction();
/* 140:159 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/* 141:    */     {
/* 142:160 */       String filterProperty = (String)it.next();
/* 143:162 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/* 144:    */       {
/* 145:163 */         String filterValue = (String)filters.get(filterProperty);
/* 146:165 */         if (filterProperty.startsWith("idOrganizacion")) {
/* 147:166 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/* 148:167 */         } else if (filterProperty.startsWith("activo")) {
/* 149:168 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/* 150:169 */         } else if (filterProperty.equals("idCliente")) {
/* 151:170 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/* 152:    */         } else {
/* 153:172 */           disjunction.getExpressions().add(criteriaBuilder
/* 154:173 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue.toLowerCase() + "%"));
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:179 */     if (disjunction.getExpressions().size() > 0) {
/* 159:181 */       conjunction.getExpressions().add(disjunction);
/* 160:    */     }
/* 161:184 */     if (conjunction.getExpressions().size() > 0) {
/* 162:185 */       criteriaQuery.where(conjunction);
/* 163:    */     }
/* 164:188 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 165:189 */     CriteriaQuery<Empleado> select = criteriaQuery.select(from);
/* 166:190 */     TypedQuery<Empleado> typedQuery = this.em.createQuery(select);
/* 167:193 */     if (!notSetMaxResults) {
/* 168:194 */       typedQuery.setMaxResults(20);
/* 169:    */     }
/* 170:197 */     return typedQuery.getResultList();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Empleado cargarDetalle(int idEmpleado)
/* 174:    */   {
/* 175:202 */     Empleado empleado = new Empleado();
/* 176:203 */     empleado = (Empleado)buscarPorId(Integer.valueOf(idEmpleado));
/* 177:204 */     empleado.getEmpresa().getId();
/* 178:205 */     empleado.getDepartamento().getId();
/* 179:207 */     if (!empleado.getListaCargaEmpleado().isEmpty()) {
/* 180:208 */       for (CargaEmpleado cargaEmpleado : empleado.getListaCargaEmpleado()) {
/* 181:209 */         if (cargaEmpleado != null)
/* 182:    */         {
/* 183:210 */           cargaEmpleado.getId();
/* 184:211 */           if (cargaEmpleado.getTipoDiscapacidad() != null) {
/* 185:212 */             cargaEmpleado.getTipoDiscapacidad().getId();
/* 186:    */           } else {
/* 187:214 */             cargaEmpleado.setTipoDiscapacidad(null);
/* 188:    */           }
/* 189:    */         }
/* 190:    */       }
/* 191:    */     }
/* 192:221 */     return empleado;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Empleado cargarEmpresa(int idEmpleado)
/* 196:    */   {
/* 197:226 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 198:    */     
/* 199:228 */     CriteriaQuery<Empleado> cqEmpleado = cb.createQuery(Empleado.class);
/* 200:229 */     Root<Empleado> from = cqEmpleado.from(Empleado.class);
/* 201:230 */     from.fetch("empresa", JoinType.INNER);
/* 202:    */     
/* 203:232 */     cqEmpleado.where(cb.equal(from.get("idEmpleado"), Integer.valueOf(idEmpleado)));
/* 204:    */     
/* 205:234 */     return (Empleado)this.em.createQuery(cqEmpleado.select(from)).getSingleResult();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public GrupoTrabajo cargarDetalleGrupoTrabajoEmpleado(int idGrupoTrabajoEmpleado)
/* 209:    */   {
/* 210:240 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 211:241 */     CriteriaQuery<GrupoTrabajo> cq = cb.createQuery(GrupoTrabajo.class);
/* 212:    */     
/* 213:243 */     Root<GrupoTrabajo> from = cq.from(GrupoTrabajo.class);
/* 214:244 */     cq.where(cb.equal(from.get("idGrupoTrabajo"), Integer.valueOf(idGrupoTrabajoEmpleado)));
/* 215:245 */     GrupoTrabajo grupoTrabajo = (GrupoTrabajo)this.em.createQuery(cq.select(from)).getSingleResult();
/* 216:246 */     this.em.detach(grupoTrabajo);
/* 217:    */     
/* 218:248 */     CriteriaQuery<Empleado> cqEmpleado = cb.createQuery(Empleado.class);
/* 219:249 */     Root<Empleado> detalle = cqEmpleado.from(Empleado.class);
/* 220:250 */     detalle.fetch("empresa", JoinType.INNER);
/* 221:    */     
/* 222:    */ 
/* 223:253 */     cqEmpleado.where(cb.equal(detalle.get("grupoTrabajo"), grupoTrabajo));
/* 224:254 */     List<Empleado> listaVersiones = this.em.createQuery(cqEmpleado).getResultList();
/* 225:255 */     grupoTrabajo.setListaEmpleados(listaVersiones);
/* 226:257 */     for (Empleado vc : listaVersiones)
/* 227:    */     {
/* 228:258 */       this.em.detach(vc);
/* 229:259 */       vc.setGrupoTrabajo(grupoTrabajo);
/* 230:    */     }
/* 231:263 */     return grupoTrabajo;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Empleado cargarRubros(int idEmpleado)
/* 235:    */   {
/* 236:269 */     Empleado empleado = (Empleado)buscarPorId(Integer.valueOf(idEmpleado));
/* 237:270 */     empleado.getEmpresa().getId();
/* 238:271 */     for (RubroEmpleado rubroEmpleado : empleado.getListaRubroEmpleado()) {
/* 239:272 */       rubroEmpleado.getRubro().getId();
/* 240:    */     }
/* 241:274 */     return empleado;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<Empleado> from)
/* 245:    */   {
/* 246:278 */     List<Predicate> predicates = new ArrayList();
/* 247:280 */     for (Iterator<String> iterador = filters.keySet().iterator(); iterador.hasNext();)
/* 248:    */     {
/* 249:281 */       String filterProperty = (String)iterador.next();
/* 250:283 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/* 251:    */       {
/* 252:285 */         String filterValue = (String)filters.get(filterProperty);
/* 253:287 */         if ("idOrganizacion".equals(filterProperty))
/* 254:    */         {
/* 255:288 */           Expression<Integer> path = from.get(filterProperty);
/* 256:289 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/* 257:    */         }
/* 258:290 */         else if ("indicadorEmpleado".equals(filterProperty))
/* 259:    */         {
/* 260:291 */           Expression<Boolean> path = from.get(filterProperty);
/* 261:292 */           predicates.add(criteriaBuilder.equal(path, Boolean.valueOf(true)));
/* 262:    */         }
/* 263:293 */         else if (filterProperty.contains("indicadorClienteProveedor"))
/* 264:    */         {
/* 265:294 */           Predicate preticateClienteProveedor = criteriaBuilder.or(criteriaBuilder.equal(from.get("indicadorCliente"), Boolean.valueOf(true)), criteriaBuilder
/* 266:295 */             .equal(from.get("indicadorProveedor"), Boolean.valueOf(true)));
/* 267:296 */           predicates.add(preticateClienteProveedor);
/* 268:    */         }
/* 269:297 */         else if ("empresa.tipoIdentificacion.nombre".equals(filterProperty))
/* 270:    */         {
/* 271:298 */           Expression<String> path = from.join("empresa").join("tipoIdentificacion").get("nombre").as(String.class);
/* 272:299 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 273:    */         }
/* 274:300 */         else if ("empresa.codigo".equals(filterProperty))
/* 275:    */         {
/* 276:301 */           Expression<String> path = from.join("empresa").get("codigo").as(String.class);
/* 277:302 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 278:    */         }
/* 279:303 */         else if ("empresa.identificacion".equals(filterProperty))
/* 280:    */         {
/* 281:304 */           Expression<String> path = from.join("empresa").get("identificacion").as(String.class);
/* 282:305 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 283:    */         }
/* 284:306 */         else if ("empresa.nombreFiscal".equals(filterProperty))
/* 285:    */         {
/* 286:307 */           Expression<String> path = from.join("empresa").get("nombreFiscal").as(String.class);
/* 287:308 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 288:    */         }
/* 289:309 */         else if ("empresa.nombreComercial".equals(filterProperty))
/* 290:    */         {
/* 291:310 */           Expression<String> path = from.join("empresa").get("nombreComercial").as(String.class);
/* 292:311 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 293:    */         }
/* 294:312 */         else if ("cargoEmpleado.nombre".equals(filterProperty))
/* 295:    */         {
/* 296:313 */           Expression<String> path = from.join("cargoEmpleado").get("nombre").as(String.class);
/* 297:314 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 298:    */         }
/* 299:315 */         else if ("departamento.nombre".equals(filterProperty))
/* 300:    */         {
/* 301:316 */           Expression<String> path = from.join("departamento").get("nombre").as(String.class);
/* 302:317 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 303:    */         }
/* 304:318 */         else if ("tipoContrato.nombre".equals(filterProperty))
/* 305:    */         {
/* 306:319 */           Expression<String> path = from.join("tipoContrato").get("nombre").as(String.class);
/* 307:320 */           predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + filterValue + "%"));
/* 308:    */         }
/* 309:    */         else
/* 310:    */         {
/* 311:322 */           Path<String> path = from.get(filterProperty);
/* 312:323 */           predicates.add(criteriaBuilder.like(path, "%" + filterValue + "%"));
/* 313:    */         }
/* 314:    */       }
/* 315:    */     }
/* 316:328 */     return predicates;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Empleado bucarEmpleadoPorIdentificacion(String identificacion, int idOrganizacion)
/* 320:    */   {
/* 321:332 */     List<Empleado> empresa = new ArrayList();
/* 322:333 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 323:334 */     CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
/* 324:335 */     Root<Empleado> from = cq.from(Empleado.class);
/* 325:336 */     Path<String> pathIdentificacion = from.join("empresa").get("identificacion");
/* 326:337 */     Path<Integer> pathIdOrganizacion = from.join("empresa").get("idOrganizacion");
/* 327:338 */     cq.where(new Predicate[] { cb.equal(pathIdentificacion, identificacion), cb.equal(pathIdOrganizacion, Integer.valueOf(idOrganizacion)) });
/* 328:339 */     CriteriaQuery<Empleado> selectEmpleado = cq.select(from);
/* 329:340 */     empresa = this.em.createQuery(selectEmpleado).getResultList();
/* 330:341 */     if (empresa.size() > 0) {
/* 331:342 */       return (Empleado)empresa.get(0);
/* 332:    */     }
/* 333:344 */     return null;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public List generarDatosArchivoEntradaIESS(int idRubro, Date desde, Date hasta)
/* 337:    */   {
/* 338:350 */     String sql = "SELECT suc.codigo,em.identificacion,he.fechaIngreso,ce.nombre,re.valor,e.codigoSectorial FROM HistoricoEmpleado he LEFT JOIN he.empleado e  LEFT JOIN e.sucursal suc LEFT JOIN e.empresa em  LEFT JOIN e.cargoEmpleado ce RIGHT JOIN e.listaRubroEmpleado re LEFT JOIN re.rubro ru WHERE (he.fechaIngreso between :desde and :hasta) AND ru.idRubro =:idRubro ORDER BY he.fechaIngreso";
/* 339:    */     
/* 340:    */ 
/* 341:    */ 
/* 342:354 */     Query query = this.em.createQuery(sql);
/* 343:355 */     query.setParameter("desde", desde);
/* 344:356 */     query.setParameter("hasta", hasta);
/* 345:357 */     query.setParameter("idRubro", Integer.valueOf(idRubro));
/* 346:    */     
/* 347:359 */     return query.getResultList();
/* 348:    */   }
/* 349:    */   
/* 350:    */   public List generarDatosArchivoSalidaIESS(Date desde, Date hasta)
/* 351:    */   {
/* 352:364 */     String sql = "SELECT suc.codigo,em.identificacion,he.fechaSalida,cs.codigo FROM HistoricoEmpleado he LEFT JOIN he.empleado e  LEFT JOIN he.causaSalidaEmpleado cs LEFT JOIN e.sucursal suc LEFT JOIN e.empresa em  WHERE (he.fechaSalida between :desde and :hasta) ORDER BY he.fechaSalida";
/* 353:    */     
/* 354:    */ 
/* 355:367 */     Query query = this.em.createQuery(sql);
/* 356:368 */     query.setParameter("desde", desde);
/* 357:369 */     query.setParameter("hasta", hasta);
/* 358:    */     
/* 359:371 */     return query.getResultList();
/* 360:    */   }
/* 361:    */   
/* 362:    */   public Empleado getEmpleadoPorCargaEmpleado(int idCargaEmpleado)
/* 363:    */   {
/* 364:376 */     StringBuilder sql = new StringBuilder();
/* 365:377 */     sql.append(" SELECT e ");
/* 366:378 */     sql.append(" FROM CargaEmpleado ce ");
/* 367:379 */     sql.append(" INNER JOIN ce.empleado e ");
/* 368:380 */     sql.append(" WHERE ce.idCargaEmpleado =:idCargaEmpleado ");
/* 369:    */     
/* 370:382 */     Query query = this.em.createQuery(sql.toString());
/* 371:383 */     query.setParameter("idCargaEmpleado", Integer.valueOf(idCargaEmpleado));
/* 372:    */     
/* 373:385 */     return (Empleado)query.getSingleResult();
/* 374:    */   }
/* 375:    */   
/* 376:    */   public List<HorarioEmpleado> getListaHorarios(Departamento departamento)
/* 377:    */   {
/* 378:391 */     StringBuilder sql = new StringBuilder();
/* 379:392 */     sql.append(" SELECT DISTINCT(he) ");
/* 380:393 */     sql.append(" FROM Empleado e ");
/* 381:394 */     sql.append(" INNER JOIN e.departamento dep ");
/* 382:395 */     sql.append(" INNER JOIN e.horarioEmpleado he ");
/* 383:396 */     sql.append(" WHERE dep =:departamento ");
/* 384:397 */     sql.append(" AND he.activo = true ");
/* 385:398 */     sql.append(" AND he.indicadorRotativo = true ");
/* 386:    */     
/* 387:400 */     Query query = this.em.createQuery(sql.toString());
/* 388:401 */     query.setParameter("departamento", departamento);
/* 389:    */     
/* 390:403 */     return query.getResultList();
/* 391:    */   }
/* 392:    */   
/* 393:    */   public List<Rubro> getListaRubros(int idEmpleado)
/* 394:    */   {
/* 395:408 */     StringBuilder SQL_QUERY = new StringBuilder();
/* 396:409 */     SQL_QUERY.append(" select r from RubroEmpleado re inner join re.rubro r ");
/* 397:410 */     SQL_QUERY.append(" where re.empleado.idEmpleado = :idEmpleado ");
/* 398:411 */     Query query = this.em.createQuery(SQL_QUERY.toString());
/* 399:412 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 400:    */     
/* 401:414 */     return query.getResultList();
/* 402:    */   }
/* 403:    */   
/* 404:    */   public List<Object[]> getCuentaBancariaEmpleado()
/* 405:    */   {
/* 406:419 */     StringBuilder sql = new StringBuilder();
/* 407:420 */     sql.append(" SELECT  e.idEmpresa, cb.numero, tcb, ban, cb.tipoServicioCuentaBancariaProveedor, cb.tipoServicioCuentaBancaria ");
/* 408:421 */     sql.append(" FROM    CuentaBancariaEmpresa cbe ");
/* 409:422 */     sql.append(" INNER   JOIN cbe.empresa e ");
/* 410:423 */     sql.append(" INNER   JOIN e.empleado em ");
/* 411:424 */     sql.append(" LEFT    JOIN cbe.cuentaBancaria cb ");
/* 412:425 */     sql.append(" INNER   JOIN cb.tipoCuentaBancaria tcb ");
/* 413:426 */     sql.append(" INNER   JOIN cb.banco ban ");
/* 414:427 */     Query query = this.em.createQuery(sql.toString());
/* 415:428 */     return query.getResultList();
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List<Object[]> getDireccionTelefonoEmpleado(int idOrganizacion)
/* 419:    */   {
/* 420:433 */     StringBuilder sql = new StringBuilder();
/* 421:434 */     sql.append(" SELECT e.idEmpresa , coalesce( de.telefono1 ,'9999999999')");
/* 422:435 */     sql.append(" FROM DireccionEmpresa de ");
/* 423:436 */     sql.append(" INNER JOIN de.empresa e ");
/* 424:437 */     sql.append(" INNER JOIN e.empleado emp ");
/* 425:438 */     sql.append(" WHERE e.idOrganizacion = :idOrganizacion ");
/* 426:439 */     sql.append(" and de.indicadorDireccionPrincipal = true ");
/* 427:    */     
/* 428:441 */     Query query = this.em.createQuery(sql.toString());
/* 429:442 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 430:    */     
/* 431:444 */     return query.getResultList();
/* 432:    */   }
/* 433:    */   
/* 434:    */   public Empleado VerificarGrupoTrabajoEmpleado(Empleado empleado)
/* 435:    */   {
/* 436:449 */     Empleado emp = null;
/* 437:    */     try
/* 438:    */     {
/* 439:451 */       System.out.println("    1   ");
/* 440:452 */       StringBuilder SQL_QUERY = new StringBuilder();
/* 441:453 */       SQL_QUERY.append(" select e from Empleado e ");
/* 442:454 */       SQL_QUERY.append(" left join fetch  e.grupoTrabajo gt ");
/* 443:455 */       SQL_QUERY.append(" where e.grupoTrabajo != null ");
/* 444:456 */       SQL_QUERY.append(" and e = :empleado ");
/* 445:457 */       Query query = this.em.createQuery(SQL_QUERY.toString());
/* 446:458 */       query.setParameter("empleado", empleado);
/* 447:459 */       return (Empleado)query.getSingleResult();
/* 448:    */     }
/* 449:    */     catch (NoResultException e)
/* 450:    */     {
/* 451:464 */       System.out.println("    2   ");
/* 452:    */     }
/* 453:465 */     return emp;
/* 454:    */   }
/* 455:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EmpleadoDao
 * JD-Core Version:    0.7.0.1
 */