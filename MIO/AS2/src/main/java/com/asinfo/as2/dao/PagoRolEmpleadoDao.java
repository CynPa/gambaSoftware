/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.AprobacionPagoRol;
/*   4:    */ import com.asinfo.as2.clases.CalculoImpuestoRenta;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*   7:    */ import com.asinfo.as2.entities.PagoEmpleado;
/*   8:    */ import com.asinfo.as2.entities.PagoRol;
/*   9:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  10:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  11:    */ import com.asinfo.as2.entities.Quincena;
/*  12:    */ import com.asinfo.as2.entities.Rubro;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*  14:    */ import java.math.BigDecimal;
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
/*  26:    */ import javax.persistence.criteria.Fetch;
/*  27:    */ import javax.persistence.criteria.Join;
/*  28:    */ import javax.persistence.criteria.JoinType;
/*  29:    */ import javax.persistence.criteria.Order;
/*  30:    */ import javax.persistence.criteria.Path;
/*  31:    */ import javax.persistence.criteria.Predicate;
/*  32:    */ import javax.persistence.criteria.Root;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class PagoRolEmpleadoDao
/*  36:    */   extends AbstractDaoAS2<PagoRolEmpleado>
/*  37:    */ {
/*  38:    */   @EJB
/*  39:    */   PagoRolEmpleadoRubroDao pagoRolEmpleadoRubroDao;
/*  40:    */   
/*  41:    */   public PagoRolEmpleadoDao()
/*  42:    */   {
/*  43: 49 */     super(PagoRolEmpleado.class);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public PagoRolEmpleado cargarDetalle(int idPagoRolEmpleado)
/*  47:    */   {
/*  48: 54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  49:    */     
/*  50: 56 */     CriteriaQuery<PagoRolEmpleado> cqCabecera = criteriaBuilder.createQuery(PagoRolEmpleado.class);
/*  51: 57 */     Root<PagoRolEmpleado> fromCabecera = cqCabecera.from(PagoRolEmpleado.class);
/*  52:    */     
/*  53: 59 */     Fetch<Object, Object> empleado = fromCabecera.fetch("empleado", JoinType.LEFT);
/*  54: 60 */     empleado.fetch("empresa", JoinType.LEFT);
/*  55:    */     
/*  56: 62 */     fromCabecera.fetch("pagoRol", JoinType.LEFT);
/*  57: 63 */     Path<Integer> pathId = fromCabecera.get("idPagoRolEmpleado");
/*  58: 64 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPagoRolEmpleado)));
/*  59: 65 */     CriteriaQuery<PagoRolEmpleado> selectPagoRolEmpleado = cqCabecera.select(fromCabecera);
/*  60: 66 */     PagoRolEmpleado pagoRolEmpleado = (PagoRolEmpleado)this.em.createQuery(selectPagoRolEmpleado).getSingleResult();
/*  61:    */     
/*  62: 68 */     CriteriaQuery<PagoRolEmpleadoRubro> cqDetalle = criteriaBuilder.createQuery(PagoRolEmpleadoRubro.class);
/*  63: 69 */     Root<PagoRolEmpleadoRubro> fromDetalle = cqDetalle.from(PagoRolEmpleadoRubro.class);
/*  64: 70 */     Path<Integer> pathIdPagoRolEmpleado = fromDetalle.get("pagoRolEmpleado").get("idPagoRolEmpleado");
/*  65: 71 */     cqDetalle.where(criteriaBuilder.equal(pathIdPagoRolEmpleado, Integer.valueOf(idPagoRolEmpleado)));
/*  66: 72 */     Fetch<Object, Object> rubro = fromDetalle.fetch("rubro", JoinType.LEFT);
/*  67: 73 */     rubro.fetch("rubroPadre", JoinType.LEFT);
/*  68: 74 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("rubro").get("ordenCalculo")) });
/*  69: 75 */     CriteriaQuery<PagoRolEmpleadoRubro> selectPagoRolEmpleadoRubro = cqDetalle.select(fromDetalle);
/*  70: 76 */     List<PagoRolEmpleadoRubro> lista = this.em.createQuery(selectPagoRolEmpleadoRubro).getResultList();
/*  71: 77 */     for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : lista)
/*  72:    */     {
/*  73: 78 */       this.pagoRolEmpleadoRubroDao.detach(pagoRolEmpleadoRubro);
/*  74: 79 */       pagoRolEmpleadoRubro.setPagoRolEmpleado(pagoRolEmpleado);
/*  75:    */     }
/*  76: 81 */     pagoRolEmpleado.setListaPagoRolEmpleadoRubro(lista);
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94: 99 */     return pagoRolEmpleado;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<PagoRolEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  98:    */   {
/*  99:109 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 100:110 */     CriteriaQuery<PagoRolEmpleado> criteriaQuery = criteriaBuilder.createQuery(PagoRolEmpleado.class);
/* 101:111 */     Root<PagoRolEmpleado> from = criteriaQuery.from(PagoRolEmpleado.class);
/* 102:    */     
/* 103:113 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/* 104:114 */     from.fetch("pagoRol", JoinType.LEFT);
/* 105:115 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 106:    */     
/* 107:117 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 108:118 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 109:    */     
/* 110:120 */     CriteriaQuery<PagoRolEmpleado> select = criteriaQuery.select(from);
/* 111:121 */     TypedQuery<PagoRolEmpleado> typedQuery = this.em.createQuery(select);
/* 112:122 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 113:    */     
/* 114:124 */     return typedQuery.getResultList();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<PagoRolEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 118:    */   {
/* 119:134 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 120:135 */     CriteriaQuery<PagoRolEmpleado> criteriaQuery = criteriaBuilder.createQuery(PagoRolEmpleado.class);
/* 121:136 */     Root<PagoRolEmpleado> from = criteriaQuery.from(PagoRolEmpleado.class);
/* 122:    */     
/* 123:138 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/* 124:139 */     from.fetch("pagoRol", JoinType.LEFT);
/* 125:140 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 126:    */     
/* 127:142 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 128:143 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 129:    */     
/* 130:145 */     CriteriaQuery<PagoRolEmpleado> select = criteriaQuery.select(from);
/* 131:146 */     TypedQuery<PagoRolEmpleado> typedQuery = this.em.createQuery(select);
/* 132:    */     
/* 133:148 */     return typedQuery.getResultList();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int contarPorCriterio(Map<String, String> filters)
/* 137:    */   {
/* 138:160 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 139:161 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/* 140:    */     
/* 141:163 */     Root<PagoRolEmpleado> from = criteriaQuery.from(PagoRolEmpleado.class);
/* 142:164 */     criteriaQuery.select(criteriaBuilder.count(from));
/* 143:    */     
/* 144:166 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 145:167 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 146:    */     
/* 147:169 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<PagoRolEmpleadoRubro> cargarRubrosPorEmpleado(PagoRol pagoRol)
/* 151:    */   {
/* 152:176 */     Query query = this.em.createQuery(" SELECT new PagoRolEmpleadoRubro ( \tCOALESCE(prer.idPagoRolEmpleadoRubro,0), \tCOALESCE(prer.tiempo,r.valor*0),  \tCASE WHEN r.tipoRubro = :tipoRubroFijo THEN re.valor ELSE COALESCE(prer.valor,r.valor*0) END,  \tr.indicadorTiempo, r.indicadorProvision , \tr.indicadorImpresionSobre, r.indicadorCalculoIESS, \t\tr.indicadorCalculoImpuestoRenta, r, COALESCE(prer.indicadorAutomatico, false), pre.idPagoRolEmpleado, \t\tCOALESCE(prer.indicadorNoProcesado, false), prerPadre  ) FROM PagoRolEmpleadoRubro prer RIGHT JOIN prer.pagoRolEmpleado pre  JOIN pre.empleado e JOIN pre.pagoRol pr, RubroEmpleado re JOIN re.rubro r LEFT JOIN r.quincena q LEFT JOIN prer.pagoRolEmpleadoRubroPadre prerPadre WHERE (pr.indicadorFiniquito = true OR q.idQuincena = :idQuincena)  AND pr.idPagoRol = :idPagoRol AND (prer.rubro IS NULL OR prer.rubro = r) AND e.idEmpleado = re.empleado.idEmpleado ORDER BY r.ordenCalculo");
/* 153:    */     
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:191 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/* 168:192 */     query.setParameter("tipoRubroFijo", TipoRubro.FIJO);
/* 169:193 */     query.setParameter("idQuincena", Integer.valueOf(pagoRol.getQuincena().getIdQuincena()));
/* 170:    */     
/* 171:195 */     return query.getResultList();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public BigDecimal obtenerIngresosAportables(Date fechaDesde, Date fechaHasta, int idHistoricoEmpleado)
/* 175:    */   {
/* 176:200 */     StringBuilder sql = new StringBuilder();
/* 177:201 */     sql.append(" SELECT COALESCE(SUM(prer.valor),0)");
/* 178:202 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/* 179:203 */     sql.append(" JOIN prer.pagoRolEmpleado pre");
/* 180:204 */     sql.append(" JOIN pre.historicoEmpleado he ");
/* 181:205 */     sql.append(" JOIN pre.pagoRol pr");
/* 182:206 */     sql.append(" JOIN prer.rubro r");
/* 183:207 */     sql.append(" WHERE r.operacion = 1 AND prer.indicadorCalculoIESS = true AND he.idHistoricoEmpleado = :idHistoricoEmpleado ");
/* 184:208 */     sql.append(" AND pr.fecha BETWEEN :fechaDesde AND :fechaHasta AND pr.indicadorFiniquito = false ");
/* 185:    */     
/* 186:210 */     Query query = this.em.createQuery(sql.toString());
/* 187:211 */     query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/* 188:212 */     query.setParameter("fechaDesde", fechaDesde);
/* 189:213 */     query.setParameter("fechaHasta", fechaHasta);
/* 190:214 */     BigDecimal valor = (BigDecimal)query.getSingleResult();
/* 191:215 */     return valor;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public CalculoImpuestoRenta obtenerValoresCalculoImpuestoRenta(int idEmpleado, int anio, int mes, int idRubroIess, int idRubroImpuestoRenta, Date fecha)
/* 195:    */   {
/* 196:222 */     Query query = this.em.createQuery(" SELECT new CalculoImpuestoRenta ( \tCOALESCE(SUM(CASE WHEN prer.indicadorCalculoImpuestoRenta = true AND r.operacion = 1 THEN prer.valor ELSE 0 END),0),  \tCOALESCE(SUM(CASE WHEN r.idRubro = :idRubroIess THEN prer.valor ELSE 0 END),0),  \tCOALESCE(SUM(CASE WHEN r.idRubro = :idRubroImpuestoRenta THEN prer.valor ELSE 0 END),0) ) FROM PagoRolEmpleadoRubro prer JOIN prer.pagoRolEmpleado pre JOIN pre.empleado e JOIN pre.pagoRol pr JOIN prer.rubro r WHERE pr.fecha < :fecha AND pr.anio = :anio AND e.idEmpleado = :idEmpleado");
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:228 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 203:229 */     query.setParameter("anio", Integer.valueOf(anio));
/* 204:230 */     query.setParameter("idRubroIess", Integer.valueOf(idRubroIess));
/* 205:231 */     query.setParameter("idRubroImpuestoRenta", Integer.valueOf(idRubroImpuestoRenta));
/* 206:232 */     query.setParameter("fecha", fecha);
/* 207:233 */     return (CalculoImpuestoRenta)query.getResultList().get(0);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public PagoRolEmpleado buscarPoIdRolIDEmpleado(int idPagoRol, int idEmpleado)
/* 211:    */   {
/* 212:239 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 213:240 */     CriteriaQuery<PagoRolEmpleado> cq = cb.createQuery(PagoRolEmpleado.class);
/* 214:241 */     Root<PagoRolEmpleado> from = cq.from(PagoRolEmpleado.class);
/* 215:    */     
/* 216:243 */     cq.select(from).where(new Predicate[] { cb.equal(from.join("pagoRol").get("idPagoRol"), Integer.valueOf(idPagoRol)), cb
/* 217:244 */       .equal(from.join("empleado").get("idEmpleado"), Integer.valueOf(idEmpleado)) });
/* 218:245 */     TypedQuery<PagoRolEmpleado> tq = this.em.createQuery(cq);
/* 219:246 */     return (PagoRolEmpleado)tq.getSingleResult();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public BigDecimal obtenerValorRubroPadre(int anio, int mes, int idHistoricoEmpleado, int idRubro)
/* 223:    */   {
/* 224:251 */     Query query = this.em.createQuery(" SELECT COALESCE(SUM(prer.valor),0)  FROM PagoRolEmpleadoRubro prer JOIN prer.pagoRolEmpleado pre JOIN pre.historicoEmpleado he JOIN pre.pagoRol pr WHERE prer.rubro.idRubro = :idRubro AND he.idHistoricoEmpleado = :idHistoricoEmpleado  AND year(pr.fecha) = :anio AND month(pr.fecha) =:mes");
/* 225:    */     
/* 226:    */ 
/* 227:    */ 
/* 228:255 */     query.setParameter("anio", Integer.valueOf(anio));
/* 229:256 */     query.setParameter("mes", Integer.valueOf(mes));
/* 230:257 */     query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/* 231:258 */     query.setParameter("idRubro", Integer.valueOf(idRubro));
/* 232:259 */     BigDecimal valor = (BigDecimal)query.getSingleResult();
/* 233:260 */     return valor;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void actualizarFaltas(int idPagoRolEmpleado, int diasFalta)
/* 237:    */   {
/* 238:264 */     String sql = " UPDATE PagoRolEmpleado pre SET pre.diasFalta = :diasFalta  WHERE pre.idPagoRolEmpleado = :idPagoRolEmpleado";
/* 239:265 */     Query query = this.em.createQuery(sql);
/* 240:266 */     query.setParameter("diasFalta", Integer.valueOf(diasFalta));
/* 241:267 */     query.setParameter("idPagoRolEmpleado", Integer.valueOf(idPagoRolEmpleado));
/* 242:268 */     query.executeUpdate();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void actualizarDiasTrabajados(int idPagoRolEmpleado, int diasTrabajados)
/* 246:    */   {
/* 247:272 */     String sql = " UPDATE PagoRolEmpleado pre SET pre.diasTrabajados = :diasTrabajados  WHERE pre.idPagoRolEmpleado = :idPagoRolEmpleado";
/* 248:273 */     Query query = this.em.createQuery(sql);
/* 249:274 */     query.setParameter("diasTrabajados", Integer.valueOf(diasTrabajados));
/* 250:275 */     query.setParameter("idPagoRolEmpleado", Integer.valueOf(idPagoRolEmpleado));
/* 251:276 */     query.executeUpdate();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void actualizarPagoEmpleado(PagoRolEmpleado pagoRolEmpleado, PagoEmpleado pagoEmpleado, boolean indicadorCobrado)
/* 255:    */   {
/* 256:280 */     String sql = " UPDATE PagoRolEmpleado pre SET pre.pagoEmpleado = :pagoEmpleado, indicadorCobrado = :indicadorCobrado WHERE pre = :pagoRolEmpleado";
/* 257:281 */     Query query = this.em.createQuery(sql);
/* 258:282 */     query.setParameter("pagoEmpleado", pagoEmpleado);
/* 259:283 */     query.setParameter("pagoRolEmpleado", pagoRolEmpleado);
/* 260:284 */     query.setParameter("indicadorCobrado", Boolean.valueOf(indicadorCobrado));
/* 261:285 */     query.executeUpdate();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void actualizarReferenciaPagoRolEmpleado(PagoRolEmpleado pagoRolEmpleado)
/* 265:    */   {
/* 266:289 */     String sql = " UPDATE PagoRolEmpleado pre SET pre.documentoReferencia = :documentoReferencia WHERE pre = :pagoRolEmpleado";
/* 267:290 */     Query query = this.em.createQuery(sql);
/* 268:291 */     query.setParameter("documentoReferencia", pagoRolEmpleado.getDocumentoReferencia());
/* 269:292 */     query.setParameter("pagoRolEmpleado", pagoRolEmpleado);
/* 270:293 */     query.executeUpdate();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void aprobarPagoRolEmpleado(AprobacionPagoRol aprobacionPagoRol)
/* 274:    */   {
/* 275:303 */     StringBuilder sbSQL = new StringBuilder();
/* 276:304 */     sbSQL.append("UPDATE PagoRolEmpleado pre SET indicadorAprobado = :indicadorAprobado WHERE pre.idPagoRolEmpleado=:idPagoRolEmpleado and pre.indicadorCobrado=false");
/* 277:305 */     Query query = this.em.createQuery(sbSQL.toString());
/* 278:306 */     query.setParameter("indicadorAprobado", Boolean.valueOf(aprobacionPagoRol.isIndicadorAprobado()));
/* 279:307 */     query.setParameter("idPagoRolEmpleado", Integer.valueOf(aprobacionPagoRol.getIdPagoRolEmpleado()));
/* 280:308 */     query.executeUpdate();
/* 281:    */   }
/* 282:    */   
/* 283:    */   public Empleado obtenerEmpleado(int idPagoRolEmpleado)
/* 284:    */   {
/* 285:312 */     Query query = this.em.createQuery(" SELECT emp  FROM PagoRolEmpleado pre JOIN pre.empleado emp WHERE pre.idPagoRolEmpleado =:idPagoRolEmpleado");
/* 286:    */     
/* 287:314 */     query.setParameter("idPagoRolEmpleado", Integer.valueOf(idPagoRolEmpleado));
/* 288:315 */     Empleado valor = null;
/* 289:    */     try
/* 290:    */     {
/* 291:317 */       valor = (Empleado)query.getSingleResult();
/* 292:    */     }
/* 293:    */     catch (Exception e)
/* 294:    */     {
/* 295:319 */       return null;
/* 296:    */     }
/* 297:322 */     return valor;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public BigDecimal obtenerValoresAportablesMensualesDecimosTerceroCuarto(Date fechaDesde, Date fechaHasta, int idHistoricoEmpleado, Rubro rubro)
/* 301:    */   {
/* 302:326 */     StringBuilder sql = new StringBuilder();
/* 303:327 */     sql.append(" SELECT COALESCE(SUM(prer.valor),0)");
/* 304:328 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/* 305:329 */     sql.append(" JOIN prer.pagoRolEmpleado pre");
/* 306:330 */     sql.append(" JOIN pre.historicoEmpleado he ");
/* 307:331 */     sql.append(" JOIN pre.pagoRol pr");
/* 308:332 */     sql.append(" JOIN prer.rubro r");
/* 309:333 */     sql.append(" WHERE r =:rubro  ");
/* 310:334 */     sql.append(" AND he.idHistoricoEmpleado = :idHistoricoEmpleado ");
/* 311:335 */     sql.append(" AND pr.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 312:336 */     sql.append(" AND pr.indicadorFiniquito = false ");
/* 313:    */     
/* 314:338 */     Query query = this.em.createQuery(sql.toString());
/* 315:339 */     query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/* 316:340 */     query.setParameter("fechaDesde", fechaDesde);
/* 317:341 */     query.setParameter("fechaHasta", fechaHasta);
/* 318:342 */     query.setParameter("rubro", rubro);
/* 319:343 */     BigDecimal valor = (BigDecimal)query.getSingleResult();
/* 320:344 */     return valor;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<HistoricoEmpleado> getEmpleadosInactivosPagoRolEmpleadoPropina(PagoRol pagoRol, PagoRol pagoRolDiasTrabajados)
/* 324:    */   {
/* 325:349 */     StringBuilder sql = new StringBuilder();
/* 326:350 */     sql.append(" SELECT he FROM PagoRolEmpleado rpe1");
/* 327:351 */     sql.append(" JOIN rpe1.historicoEmpleado he");
/* 328:352 */     sql.append(" JOIN fetch he.empleado e1");
/* 329:353 */     sql.append(" JOIN rpe1.pagoRol pr1");
/* 330:354 */     sql.append(" JOIN e1.cargoEmpleado ce");
/* 331:355 */     sql.append(" WHERE pr1 = :pagoRolDiasTrabajados");
/* 332:356 */     sql.append(" AND ce.indicadorPropina = TRUE");
/* 333:357 */     sql.append(" AND NOT EXISTS (");
/* 334:358 */     sql.append(" \tSELECT 1 FROM PagoRolEmpleado pre2");
/* 335:359 */     sql.append(" \tJOIN pre2.empleado e2");
/* 336:360 */     sql.append(" \tJOIN pre2.pagoRol pr2");
/* 337:361 */     sql.append(" \tWHERE pr2 = :pagoRol");
/* 338:362 */     sql.append(" \tAND e1 = e2");
/* 339:363 */     sql.append(" )");
/* 340:    */     
/* 341:365 */     Query query = this.em.createQuery(sql.toString());
/* 342:366 */     query.setParameter("pagoRol", pagoRol);
/* 343:367 */     query.setParameter("pagoRolDiasTrabajados", pagoRolDiasTrabajados);
/* 344:    */     
/* 345:369 */     return query.getResultList();
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void actualizarValorAPagar(PagoRolEmpleado pre)
/* 349:    */   {
/* 350:373 */     StringBuilder sql = new StringBuilder();
/* 351:374 */     sql.append(" UPDATE PagoRolEmpleado pre SET pre.valorAPagar = ( ");
/* 352:375 */     sql.append(" \tSELECT SUM(CASE WHEN r.indicadorImpresionSobre = true THEN (prer.valor * r.operacion) ELSE 0.00 END) ");
/* 353:376 */     sql.append(" \tFROM PagoRolEmpleadoRubro prer ");
/* 354:377 */     sql.append(" \tINNER JOIN prer.pagoRolEmpleado pre ");
/* 355:378 */     sql.append(" \tINNER JOIN prer.rubro r ");
/* 356:379 */     sql.append(" \tWHERE pre.idPagoRolEmpleado = :idPagoRolEmpleado ");
/* 357:380 */     sql.append(" \tAND prer.indicadorNoProcesado = FALSE");
/* 358:381 */     sql.append(" ) WHERE pre.idPagoRolEmpleado = :idPagoRolEmpleado ");
/* 359:382 */     Query query = this.em.createQuery(sql.toString());
/* 360:383 */     query.setParameter("idPagoRolEmpleado", Integer.valueOf(pre.getIdPagoRolEmpleado()));
/* 361:384 */     query.executeUpdate();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<PagoRolEmpleado> getPagoRolEmpleado(PagoRol pagoRol)
/* 365:    */   {
/* 366:389 */     StringBuilder sql = new StringBuilder();
/* 367:    */     
/* 368:391 */     sql.append(" SELECT pre FROM PagoRolEmpleado pre");
/* 369:392 */     sql.append(" JOIN FETCH pre.historicoEmpleado ");
/* 370:393 */     sql.append(" WHERE pre.pagoRol = :pagoRol");
/* 371:    */     
/* 372:395 */     Query q = this.em.createQuery(sql.toString());
/* 373:396 */     q.setParameter("pagoRol", pagoRol);
/* 374:    */     
/* 375:398 */     return q.getResultList();
/* 376:    */   }
/* 377:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoRolEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */