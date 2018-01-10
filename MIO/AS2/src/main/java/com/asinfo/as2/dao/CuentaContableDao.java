/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.entities.NivelCuenta;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*   9:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  12:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioEstadoFinancieroImpl;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.Stateless;
/*  22:    */ import javax.persistence.EntityManager;
/*  23:    */ import javax.persistence.LockModeType;
/*  24:    */ import javax.persistence.NoResultException;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TypedQuery;
/*  27:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  28:    */ import javax.persistence.criteria.CriteriaQuery;
/*  29:    */ import javax.persistence.criteria.Expression;
/*  30:    */ import javax.persistence.criteria.JoinType;
/*  31:    */ import javax.persistence.criteria.Predicate;
/*  32:    */ import javax.persistence.criteria.Root;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class CuentaContableDao
/*  36:    */   extends AbstractDaoAS2<CuentaContable>
/*  37:    */ {
/*  38:    */   public CuentaContableDao()
/*  39:    */   {
/*  40: 57 */     super(CuentaContable.class);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<CuentaContable> buscarPorTipo(TipoCuentaContable tipoCuentaContable, int idOrganizacion)
/*  44:    */   {
/*  45: 63 */     Query query = this.em.createQuery("SELECT NEW CuentaContable(c.idCuentaContable, c.codigo, c.nombre, c.indicadorValidarDistribucion)  FROM CuentaContable c  WHERE c.tipoCuentaContable=:tipoCuentaContable and c.idOrganizacion = :idOrganizacion  ORDER BY c.codigo");
/*  46:    */     
/*  47:    */ 
/*  48: 66 */     query.setParameter("tipoCuentaContable", tipoCuentaContable);
/*  49: 67 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  50:    */     
/*  51: 69 */     return query.getResultList();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public CuentaContable buscarPorCodigo(String codigoCuentaContable, int idOrganizacion)
/*  55:    */     throws ExcepcionAS2Financiero
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 83 */       StringBuffer sql = new StringBuffer();
/*  60: 84 */       sql.append("SELECT c FROM CuentaContable c ");
/*  61: 85 */       sql.append(" WHERE c.codigo=:codigoCuentaContable ");
/*  62: 86 */       sql.append(" AND c.idOrganizacion = :idOrganizacion");
/*  63: 87 */       Query query = this.em.createQuery(sql.toString());
/*  64: 88 */       query.setParameter("codigoCuentaContable", codigoCuentaContable);
/*  65: 89 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  66:    */       
/*  67: 91 */       CuentaContable cuentaContable = (CuentaContable)query.getSingleResult();
/*  68: 93 */       if (!cuentaContable.isIndicadorMovimiento()) {
/*  69: 94 */         throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0001", " " + codigoCuentaContable);
/*  70:    */       }
/*  71: 97 */       return cuentaContable;
/*  72:    */     }
/*  73:    */     catch (NoResultException e)
/*  74:    */     {
/*  75:100 */       throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0002", " " + codigoCuentaContable);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<CuentaContable> buscarPorGrupoNivel(GrupoCuenta grupoCuenta, int codigo, int idOrganizacion)
/*  80:    */   {
/*  81:107 */     Query query = this.em.createQuery("SELECT c FROM CuentaContable c WHERE c.nivelCuenta.codigo =:codigo and c.grupoCuenta =:grupoCuenta and c.idOrganizacion = :idOrganizacion ORDER BY c.codigo ");
/*  82:    */     
/*  83:109 */     query.setParameter("codigo", Integer.valueOf(codigo)).setParameter("grupoCuenta", grupoCuenta).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  84:110 */     return query.getResultList();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<Object[]> calcularSaldos(Date fechaDesde, Date fechaHasta, String dimension, String codigoDimension, boolean indicadorNIIF, List<GrupoCuenta> listaGrupos, int idSucursal)
/*  88:    */   {
/*  89:130 */     int idOrganizacion = AppUtil.getSucursal().getIdOrganizacion();
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:134 */     StringBuilder sql = new StringBuilder();
/*  94:135 */     sql.append(" SELECT c.codigo,SUM(d.debe),SUM(d.haber) ");
/*  95:136 */     sql.append(" FROM DetalleAsiento d ");
/*  96:137 */     sql.append(" INNER JOIN d.cuentaContable c ");
/*  97:138 */     if ((dimension != null) && (!dimension.isEmpty())) {
/*  98:139 */       sql.append(" LEFT JOIN d.dimensionContable" + dimension + " di ");
/*  99:    */     }
/* 100:141 */     sql.append(" INNER JOIN d.asiento a ");
/* 101:142 */     sql.append(" INNER JOIN a.sucursal s ");
/* 102:143 */     sql.append(" INNER JOIN a.tipoAsiento ta ");
/* 103:144 */     sql.append(" WHERE a.estado IN (:estado) ");
/* 104:145 */     sql.append(" AND a.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 105:146 */     if (!indicadorNIIF) {
/* 106:147 */       sql.append(" AND ta.indicadorNIIF = false ");
/* 107:    */     }
/* 108:149 */     sql.append(" AND c.grupoCuenta in (:listaGrupos) ");
/* 109:150 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/* 110:151 */     if (idSucursal != -1) {
/* 111:152 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 112:    */     }
/* 113:154 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 114:155 */       sql.append(" AND di.codigo LIKE :codigoDimension ");
/* 115:    */     }
/* 116:157 */     sql.append(" GROUP BY c.codigo ");
/* 117:    */     
/* 118:159 */     Query query = this.em.createQuery(sql.toString());
/* 119:160 */     query.setParameter("fechaDesde", fechaDesde);
/* 120:161 */     query.setParameter("fechaHasta", fechaHasta);
/* 121:162 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 122:163 */     query.setParameter("listaGrupos", listaGrupos);
/* 123:164 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 124:165 */     query.setLockMode(LockModeType.NONE);
/* 125:166 */     if (idSucursal != -1) {
/* 126:167 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 127:    */     }
/* 128:169 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 129:170 */       query.setParameter("codigoDimension", codigoDimension + "%");
/* 130:    */     }
/* 131:172 */     return query.getResultList();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal obteneResultadoEjercicio(Date fechaDesde, Date fechaHasta, String dimension, String codigoDimension, boolean indicadorNIIF, int idSucursal, int idOrganizacion)
/* 135:    */   {
/* 136:178 */     List<GrupoCuenta> listaGrupos = new ArrayList();
/* 137:179 */     listaGrupos.add(GrupoCuenta.INGRESOS);
/* 138:180 */     listaGrupos.add(GrupoCuenta.COSTOS);
/* 139:181 */     listaGrupos.add(GrupoCuenta.GASTOS);
/* 140:    */     
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:186 */     StringBuilder sql = new StringBuilder();
/* 145:187 */     sql.append(" SELECT SUM(d.debe-d.haber) ");
/* 146:188 */     sql.append(" FROM DetalleAsiento d ");
/* 147:189 */     sql.append(" INNER JOIN d.cuentaContable c ");
/* 148:190 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 149:191 */       sql.append(" LEFT JOIN d.dimensionContable" + dimension + " dim ");
/* 150:    */     }
/* 151:193 */     sql.append(" INNER JOIN d.asiento a ");
/* 152:194 */     sql.append(" INNER JOIN a.sucursal s ");
/* 153:195 */     sql.append(" INNER JOIN a.tipoAsiento ta ");
/* 154:196 */     sql.append(" WHERE a.estado IN(:estado) ");
/* 155:197 */     sql.append(" AND a.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 156:198 */     if (!indicadorNIIF) {
/* 157:199 */       sql.append(" AND ta.indicadorNIIF = false ");
/* 158:    */     }
/* 159:201 */     sql.append(" AND c.grupoCuenta in (:listaGrupos) ");
/* 160:202 */     sql.append(" AND a.idOrganizacion = :idOrganizacion ");
/* 161:203 */     if (idSucursal != -1) {
/* 162:204 */       sql.append("  AND s.idSucursal = :idSucursal  ");
/* 163:    */     }
/* 164:206 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 165:207 */       sql.append(" AND dim.codigo LIKE :codigoDimension ");
/* 166:    */     }
/* 167:210 */     Query query = this.em.createQuery(sql.toString());
/* 168:211 */     query.setParameter("fechaDesde", fechaDesde);
/* 169:212 */     query.setParameter("fechaHasta", fechaHasta);
/* 170:213 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 171:214 */     query.setParameter("listaGrupos", listaGrupos);
/* 172:215 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 173:216 */     query.setLockMode(LockModeType.NONE);
/* 174:217 */     if (idSucursal != -1) {
/* 175:218 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 176:    */     }
/* 177:220 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 178:221 */       query.setParameter("codigoDimension", codigoDimension + "%");
/* 179:    */     }
/* 180:224 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/* 181:225 */     return resultado;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<Object[]> obtenerValores(Date fechaDesde, Date fechaHasta, int idOrganizacion, List<CuentaContable> listaCuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, String dimension, String codigoDimension)
/* 185:    */   {
/* 186:231 */     String sumatoria = "";
/* 187:232 */     String condicionFecha = "";
/* 188:233 */     String debe = "da.debe";
/* 189:234 */     String haber = "da.haber";
/* 190:236 */     if (valoresCalculo == ValoresCalculo.DEBE_HABER) {
/* 191:237 */       sumatoria = debe + "-" + haber;
/* 192:238 */     } else if (valoresCalculo == ValoresCalculo.DEBE) {
/* 193:239 */       sumatoria = debe;
/* 194:240 */     } else if (valoresCalculo == ValoresCalculo.HABER) {
/* 195:241 */       sumatoria = haber;
/* 196:    */     }
/* 197:244 */     if (tipoCalculo == TipoCalculo.MOVIMIENTOS_MES) {
/* 198:245 */       condicionFecha = " AND at.fecha BETWEEN :fechaDesde AND :fechaHasta";
/* 199:246 */     } else if (tipoCalculo == TipoCalculo.SALDO_INICIAL) {
/* 200:247 */       condicionFecha = " AND at.fecha < :fechaDesde AND :fechaHasta = :fechaHasta";
/* 201:248 */     } else if (tipoCalculo == TipoCalculo.SALDO_FINAL) {
/* 202:249 */       condicionFecha = " AND :fechaDesde = :fechaDesde AND at.fecha <= :fechaHasta";
/* 203:    */     }
/* 204:252 */     StringBuilder sql = new StringBuilder();
/* 205:253 */     sql.append(" SELECT cc.idCuentaContable, SUM(" + sumatoria + ")");
/* 206:254 */     sql.append(" FROM DetalleAsiento da ");
/* 207:255 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 208:256 */       sql.append(" LEFT JOIN da.dimensionContable" + dimension + " dim ");
/* 209:    */     }
/* 210:258 */     sql.append(" INNER JOIN da.asiento at ");
/* 211:259 */     sql.append(" INNER JOIN da.cuentaContable cc ");
/* 212:260 */     sql.append(" INNER JOIN at.tipoAsiento ta  ");
/* 213:261 */     sql.append(" INNER JOIN at.sucursal s ");
/* 214:262 */     sql.append(" WHERE at.estado IN (:estado) ");
/* 215:263 */     sql.append(" AND (ta.indicadorNIIF = :indicadorNIIF OR :indicadorNIIF = true) ");
/* 216:264 */     sql.append(" AND at.idOrganizacion = :idOrganizacion ");
/* 217:265 */     sql.append(condicionFecha);
/* 218:266 */     sql.append(" AND cc in (:listaCuentaContable)");
/* 219:268 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 220:269 */       sql.append(" AND dim.codigo LIKE :codigoDimension ");
/* 221:    */     }
/* 222:272 */     if (idSucursal > 0) {
/* 223:273 */       sql.append(" AND s.idSucursal = :idSucursal");
/* 224:    */     }
/* 225:275 */     sql.append(" GROUP BY cc.idCuentaContable ");
/* 226:276 */     Query query = this.em.createQuery(sql.toString());
/* 227:277 */     query.setParameter("fechaDesde", fechaDesde);
/* 228:278 */     query.setParameter("fechaHasta", fechaHasta);
/* 229:279 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/* 230:280 */     query.setParameter("listaCuentaContable", listaCuentaContable);
/* 231:281 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 232:282 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 233:284 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 234:285 */       query.setParameter("codigoDimension", codigoDimension);
/* 235:    */     }
/* 236:288 */     if (idSucursal > 0) {
/* 237:289 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 238:    */     }
/* 239:292 */     return query.getResultList();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public BigDecimal obtenerSaldo(Date fechaDesde, Date fechaHasta, int idCuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, String dimension, String codigoDimension)
/* 243:    */   {
/* 244:301 */     int idOrganizacion = AppUtil.getSucursal().getIdOrganizacion();
/* 245:    */     
/* 246:303 */     String sumatoria = "";
/* 247:304 */     String condicionFecha = "";
/* 248:305 */     String debe = "da.debe";
/* 249:306 */     String haber = "da.haber";
/* 250:308 */     if (valoresCalculo == ValoresCalculo.DEBE_HABER) {
/* 251:309 */       sumatoria = debe + "-" + haber;
/* 252:310 */     } else if (valoresCalculo == ValoresCalculo.DEBE) {
/* 253:311 */       sumatoria = debe;
/* 254:312 */     } else if (valoresCalculo == ValoresCalculo.HABER) {
/* 255:313 */       sumatoria = haber;
/* 256:    */     }
/* 257:316 */     if (tipoCalculo == TipoCalculo.MOVIMIENTOS_MES) {
/* 258:317 */       condicionFecha = " AND at.fecha BETWEEN :fechaDesde AND :fechaHasta";
/* 259:318 */     } else if (tipoCalculo == TipoCalculo.SALDO_INICIAL) {
/* 260:319 */       condicionFecha = " AND at.fecha < :fechaDesde AND :fechaHasta = :fechaHasta";
/* 261:320 */     } else if (tipoCalculo == TipoCalculo.SALDO_FINAL) {
/* 262:321 */       condicionFecha = " AND :fechaDesde = :fechaDesde AND at.fecha <= :fechaHasta";
/* 263:    */     }
/* 264:324 */     StringBuilder sql = new StringBuilder();
/* 265:325 */     sql.append(" SELECT SUM(" + sumatoria + ")");
/* 266:326 */     sql.append(" FROM DetalleAsiento da ");
/* 267:327 */     sql.append(" INNER JOIN da.cuentaContable cc ");
/* 268:328 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 269:329 */       sql.append(" LEFT JOIN da.dimensionContable" + dimension + " dim ");
/* 270:    */     }
/* 271:331 */     sql.append(" INNER JOIN da.asiento at ");
/* 272:332 */     sql.append(" INNER JOIN at.tipoAsiento ta  ");
/* 273:333 */     sql.append(" INNER JOIN at.sucursal s ");
/* 274:334 */     sql.append(" WHERE at.estado IN (:estado) ");
/* 275:335 */     sql.append(" AND (ta.indicadorNIIF = :indicadorNIIF OR :indicadorNIIF = true) ");
/* 276:336 */     sql.append(condicionFecha);
/* 277:337 */     sql.append(" AND cc.idCuentaContable= :idCuentaContable ");
/* 278:340 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 279:341 */       sql.append(" AND dim.codigo LIKE :codigoDimension ");
/* 280:    */     }
/* 281:344 */     if (idSucursal > 0) {
/* 282:345 */       sql.append(" AND s.idSucursal = :idSucursal");
/* 283:    */     }
/* 284:348 */     Query query = this.em.createQuery(sql.toString());
/* 285:349 */     query.setParameter("fechaDesde", fechaDesde);
/* 286:350 */     query.setParameter("fechaHasta", fechaHasta);
/* 287:351 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/* 288:352 */     query.setParameter("idCuentaContable", Integer.valueOf(idCuentaContable));
/* 289:353 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 290:355 */     if ((dimension != null) && (!dimension.isEmpty())) {
/* 291:356 */       query.setParameter("codigoDimension", codigoDimension);
/* 292:    */     }
/* 293:359 */     if (idSucursal > 0) {
/* 294:360 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 295:    */     }
/* 296:363 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/* 297:    */     
/* 298:365 */     return resultado == null ? BigDecimal.ZERO : resultado;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<CuentaContable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 302:    */   {
/* 303:377 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 304:378 */     CriteriaQuery<CuentaContable> criteriaQuery = criteriaBuilder.createQuery(CuentaContable.class);
/* 305:379 */     Root<CuentaContable> from = criteriaQuery.from(CuentaContable.class);
/* 306:    */     
/* 307:381 */     from.fetch("nivelCuenta", JoinType.LEFT);
/* 308:382 */     from.fetch("partidaPresupuestaria", JoinType.LEFT);
/* 309:    */     
/* 310:384 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 311:385 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 312:386 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 313:    */     
/* 314:388 */     CriteriaQuery<CuentaContable> select = criteriaQuery.select(from);
/* 315:389 */     TypedQuery<CuentaContable> typedQuery = this.em.createQuery(select);
/* 316:390 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 317:    */     
/* 318:392 */     return typedQuery.getResultList();
/* 319:    */   }
/* 320:    */   
/* 321:    */   public List<CuentaContable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 322:    */   {
/* 323:402 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 324:403 */     CriteriaQuery<CuentaContable> criteriaQuery = criteriaBuilder.createQuery(CuentaContable.class);
/* 325:404 */     Root<CuentaContable> from = criteriaQuery.from(CuentaContable.class);
/* 326:405 */     from.fetch("nivelCuenta", JoinType.LEFT);
/* 327:    */     
/* 328:407 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 329:408 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 330:409 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 331:    */     
/* 332:411 */     CriteriaQuery<CuentaContable> select = criteriaQuery.select(from);
/* 333:412 */     TypedQuery<CuentaContable> typedQuery = this.em.createQuery(select);
/* 334:    */     
/* 335:414 */     return typedQuery.getResultList();
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void eliminar(CuentaContable entidad)
/* 339:    */   {
/* 340:424 */     this.em.merge(entidad);
/* 341:425 */     if (entidad.getCuentaPadre() != null) {
/* 342:426 */       entidad.setCuentaPadre(null);
/* 343:    */     }
/* 344:428 */     super.eliminar(entidad);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public CuentaContable cargarDetalle(int idCuentaContable)
/* 348:    */   {
/* 349:440 */     CuentaContable cuentaContable = (CuentaContable)buscarPorId(Integer.valueOf(idCuentaContable));
/* 350:441 */     cuentaContable.getNivelCuenta().getId();
/* 351:442 */     if (cuentaContable.getCuentaPadre() != null) {
/* 352:443 */       cuentaContable.getCuentaPadre().getId();
/* 353:    */     }
/* 354:445 */     if (cuentaContable.getPartidaPresupuestaria() != null) {
/* 355:446 */       cuentaContable.getPartidaPresupuestaria().getId();
/* 356:    */     }
/* 357:448 */     if (cuentaContable.getDimensionContable() != null) {
/* 358:449 */       cuentaContable.getDimensionContable().getId();
/* 359:    */     }
/* 360:451 */     return cuentaContable;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public List<DetalleAsiento> obtenerCierreCuentas(int idCuentaContable, List<Integer> listaCuentaContable, Date fechaDesde, Date fechaHasta)
/* 364:    */     throws ExcepcionAS2Financiero
/* 365:    */   {
/* 366:458 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(d.cuentaContable, \t'' ,CONCAT('Cierre de cuentas'),'',-ROUND(SUM(d.debe-d.haber),2) ) \tFROM DetalleAsiento d  WHERE d.asiento.periodo.ejercicio.idEjercicio = :idEjercidio AND d.cuentaContable.idCuentaContable in (:listadCuentaContable)  GROUP BY d.cuentaContable");
/* 367:    */     
/* 368:    */ 
/* 369:    */ 
/* 370:462 */     query.setParameter("idCuentaContable", Integer.valueOf(idCuentaContable));
/* 371:463 */     query.setParameter("listaCuentaContable", listaCuentaContable);
/* 372:464 */     query.setParameter("fechaDesde", fechaDesde);
/* 373:465 */     query.setParameter("fechaHasta", fechaHasta);
/* 374:    */     
/* 375:467 */     List<DetalleAsiento> detalles = query.getResultList();
/* 376:469 */     if (detalles.size() == 0) {
/* 377:470 */       throw new ExcepcionAS2Financiero("msg_no_hay_datos");
/* 378:    */     }
/* 379:472 */     return detalles;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public List<DetalleAsiento> obtenerCierreCuentas(List<Integer> listaCuentaContable, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 383:    */     throws ExcepcionAS2Financiero
/* 384:    */   {
/* 385:479 */     StringBuilder sql = new StringBuilder();
/* 386:480 */     sql.append(" SELECT new DetalleAsiento (c.idCuentaContable,c.codigo,c.nombre,c.indicadorValidarDistribucion , ");
/* 387:481 */     sql.append(" dc1.idDimensionContable, dc1.numero, dc1.codigo, dc1.nombre,  ");
/* 388:482 */     sql.append(" dc2.idDimensionContable, dc2.numero, dc2.codigo, dc2.nombre,  ");
/* 389:483 */     sql.append(" dc3.idDimensionContable, dc3.numero, dc3.codigo, dc3.nombre,  ");
/* 390:484 */     sql.append(" dc4.idDimensionContable, dc4.numero, dc4.codigo, dc4.nombre,  ");
/* 391:485 */     sql.append(" dc5.idDimensionContable, dc5.numero, dc5.codigo, dc5.nombre,  ");
/* 392:486 */     sql.append(" SUM(da.haber-da.debe), c.indicadorMovimiento) ");
/* 393:487 */     sql.append(" FROM  DetalleAsiento da");
/* 394:488 */     sql.append(" INNER JOIN da.asiento a");
/* 395:489 */     sql.append(" INNER JOIN da.cuentaContable c ");
/* 396:490 */     sql.append(" LEFT  JOIN da.dimensionContable1 dc1 ");
/* 397:491 */     sql.append(" LEFT  JOIN da.dimensionContable2 dc2 ");
/* 398:492 */     sql.append(" LEFT  JOIN da.dimensionContable3 dc3 ");
/* 399:493 */     sql.append(" LEFT  JOIN da.dimensionContable4 dc4 ");
/* 400:494 */     sql.append(" LEFT  JOIN da.dimensionContable5 dc5 ");
/* 401:495 */     sql.append(" WHERE a.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 402:496 */     sql.append(" AND   c.idCuentaContable in (:listaCuentaContable) AND a.estado in ( :estado) ");
/* 403:497 */     sql.append(" GROUP BY c.idCuentaContable,c.codigo,c.nombre,c.indicadorValidarDistribucion, ");
/* 404:498 */     sql.append(" dc1.idDimensionContable, dc1.numero, dc1.codigo, dc1.nombre,  ");
/* 405:499 */     sql.append(" dc2.idDimensionContable, dc2.numero, dc2.codigo, dc2.nombre,  ");
/* 406:500 */     sql.append(" dc3.idDimensionContable, dc3.numero, dc3.codigo, dc3.nombre,  ");
/* 407:501 */     sql.append(" dc4.idDimensionContable, dc4.numero, dc4.codigo, dc4.nombre,  ");
/* 408:502 */     sql.append(" dc5.idDimensionContable, dc5.numero, dc5.codigo, dc5.nombre,  ");
/* 409:503 */     sql.append(" c.indicadorMovimiento");
/* 410:504 */     sql.append(" HAVING SUM(da.haber-da.debe) != 0");
/* 411:505 */     sql.append(" ORDER BY c.codigo");
/* 412:    */     
/* 413:507 */     Query query = this.em.createQuery(sql.toString());
/* 414:508 */     query.setParameter("listaCuentaContable", listaCuentaContable);
/* 415:509 */     query.setParameter("fechaDesde", fechaDesde);
/* 416:510 */     query.setParameter("fechaHasta", fechaHasta);
/* 417:511 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 418:512 */     List<DetalleAsiento> detalles = query.getResultList();
/* 419:514 */     if (detalles.size() == 0) {
/* 420:515 */       throw new ExcepcionAS2Financiero("msg_no_hay_datos");
/* 421:    */     }
/* 422:517 */     return detalles;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public List<CuentaContable> buscarCuentasMovimientoPorCodigo(String codigoCuentaContable, int idOrganizacion)
/* 426:    */     throws ExcepcionAS2Financiero
/* 427:    */   {
/* 428:    */     try
/* 429:    */     {
/* 430:523 */       StringBuffer sql = new StringBuffer();
/* 431:524 */       sql.append("SELECT c FROM CuentaContable c ");
/* 432:525 */       sql.append(" WHERE c.codigo LIKE :codigoCuentaContable ");
/* 433:526 */       sql.append(" AND c.idOrganizacion = :idOrganizacion");
/* 434:527 */       sql.append(" AND c.indicadorMovimiento IS TRUE");
/* 435:528 */       Query query = this.em.createQuery(sql.toString());
/* 436:529 */       query.setParameter("codigoCuentaContable", codigoCuentaContable + "%");
/* 437:530 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 438:    */       
/* 439:532 */       return query.getResultList();
/* 440:    */     }
/* 441:    */     catch (NoResultException e)
/* 442:    */     {
/* 443:537 */       throw new ExcepcionAS2Financiero("msg_info_cuenta_contable_0002", " " + codigoCuentaContable);
/* 444:    */     }
/* 445:    */   }
/* 446:    */   
/* 447:    */   public List<Object[]> obtenerCierreCostosPorComponenteCosto(int idOrganizacion, int anio, int mes)
/* 448:    */   {
/* 449:542 */     StringBuilder sql = new StringBuilder();
/* 450:543 */     sql.append(" SELECT coalesce(cccc.idCuentaContable, ccc.idCuentaContable), ");
/* 451:544 */     sql.append(" coalesce(cccc.codigo, ccc.codigo), coalesce(cccc.nombre, ccc.nombre), coalesce(cccc.indicadorMovimiento, ccc.indicadorMovimiento), ");
/* 452:545 */     sql.append(" d1.idDimensionContable, coalesce(d1.codigo,''), coalesce(d1.nombre, ''), ");
/* 453:546 */     sql.append(" d2.idDimensionContable, coalesce(d2.codigo,''), coalesce(d2.nombre, ''), ");
/* 454:547 */     sql.append(" d3.idDimensionContable, coalesce(d3.codigo,''), coalesce(d3.nombre, ''), ");
/* 455:548 */     sql.append(" d4.idDimensionContable, coalesce(d4.codigo,''), coalesce(d4.nombre, ''), ");
/* 456:549 */     sql.append(" d5.idDimensionContable, coalesce(d5.codigo,''), coalesce(d5.nombre, ''), ");
/* 457:550 */     sql.append(" -sum(CASE WHEN dcc.valorCalculo = :valorCalculoDebe then da.debe WHEN dcc.valorCalculo = :valorCalculoHaber then da.haber ELSE (da.debe-da.haber) END) ");
/* 458:551 */     sql.append(" FROM DetalleAsiento da ");
/* 459:552 */     sql.append(" JOIN da.asiento a ");
/* 460:553 */     sql.append(" LEFT JOIN da.dimensionContable1 d1 ");
/* 461:554 */     sql.append(" LEFT JOIN da.dimensionContable2 d2 ");
/* 462:555 */     sql.append(" LEFT JOIN da.dimensionContable3 d3 ");
/* 463:556 */     sql.append(" LEFT JOIN da.dimensionContable4 d4 ");
/* 464:557 */     sql.append(" LEFT JOIN da.dimensionContable5 d5 ");
/* 465:558 */     sql.append(" JOIN da.cuentaContable cc, ");
/* 466:559 */     sql.append(" DetalleComponenteCosto dcc ");
/* 467:560 */     sql.append(" JOIN dcc.cuentaContable ccc ");
/* 468:561 */     sql.append(" LEFT JOIN dcc.cuentaContableCierre cccc ");
/* 469:562 */     sql.append(" WHERE ccc = cc AND a.idOrganizacion = :idOrganizacion ");
/* 470:563 */     sql.append(" AND year(a.fecha) = :anio AND MONTH(a.fecha) = :mes ");
/* 471:564 */     sql.append(" GROUP BY coalesce(cccc.idCuentaContable, ccc.idCuentaContable), ");
/* 472:565 */     sql.append(" coalesce(cccc.codigo, ccc.codigo), coalesce(cccc.nombre, ccc.nombre), coalesce(cccc.indicadorMovimiento, ccc.indicadorMovimiento), ");
/* 473:566 */     sql.append(" d1.idDimensionContable, coalesce(d1.codigo,''), coalesce(d1.nombre, ''), ");
/* 474:567 */     sql.append(" d2.idDimensionContable, coalesce(d2.codigo,''), coalesce(d2.nombre, ''), ");
/* 475:568 */     sql.append(" d3.idDimensionContable, coalesce(d3.codigo,''), coalesce(d3.nombre, ''), ");
/* 476:569 */     sql.append(" d4.idDimensionContable, coalesce(d4.codigo,''), coalesce(d4.nombre, ''), ");
/* 477:570 */     sql.append(" d5.idDimensionContable, coalesce(d5.codigo,''), coalesce(d5.nombre, '') ");
/* 478:571 */     Query query = this.em.createQuery(sql.toString());
/* 479:572 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 480:573 */     query.setParameter("anio", Integer.valueOf(anio));
/* 481:574 */     query.setParameter("mes", Integer.valueOf(mes));
/* 482:575 */     query.setParameter("valorCalculoDebe", ValoresCalculo.DEBE);
/* 483:576 */     query.setParameter("valorCalculoHaber", ValoresCalculo.HABER);
/* 484:577 */     return query.getResultList();
/* 485:    */   }
/* 486:    */   
/* 487:    */   public BigDecimal obtenerSaldoPorConbinacionDimensiones(Date fechaDesde, Date fechaHasta, CuentaContable cuentaContable, ValoresCalculo valoresCalculo, TipoCalculo tipoCalculo, boolean indicadorNIIF, int idSucursal, DimensionContable dimension1, DimensionContable dimension2, DimensionContable dimension3, DimensionContable dimension4, DimensionContable dimension5)
/* 488:    */   {
/* 489:583 */     int idOrganizacion = cuentaContable.getIdOrganizacion();
/* 490:    */     
/* 491:585 */     String sumatoria = "";
/* 492:586 */     String condicionFecha = "";
/* 493:587 */     String debe = "da.debe";
/* 494:588 */     String haber = "da.haber";
/* 495:590 */     if (valoresCalculo == ValoresCalculo.DEBE_HABER) {
/* 496:591 */       sumatoria = debe + "-" + haber;
/* 497:592 */     } else if (valoresCalculo == ValoresCalculo.DEBE) {
/* 498:593 */       sumatoria = debe;
/* 499:594 */     } else if (valoresCalculo == ValoresCalculo.HABER) {
/* 500:595 */       sumatoria = haber;
/* 501:    */     }
/* 502:598 */     if (tipoCalculo == TipoCalculo.MOVIMIENTOS_MES) {
/* 503:599 */       condicionFecha = " AND at.fecha BETWEEN :fechaDesde AND :fechaHasta";
/* 504:600 */     } else if (tipoCalculo == TipoCalculo.SALDO_INICIAL) {
/* 505:601 */       condicionFecha = " AND at.fecha < :fechaDesde AND :fechaHasta = :fechaHasta";
/* 506:602 */     } else if (tipoCalculo == TipoCalculo.SALDO_FINAL) {
/* 507:603 */       condicionFecha = " AND :fechaDesde = :fechaDesde AND at.fecha <= :fechaHasta";
/* 508:    */     }
/* 509:606 */     StringBuilder sql = new StringBuilder();
/* 510:607 */     sql.append(" SELECT SUM(" + sumatoria + ")");
/* 511:608 */     sql.append(" FROM DetalleAsiento da ");
/* 512:609 */     sql.append(" INNER JOIN da.cuentaContable cc ");
/* 513:610 */     if (dimension1 != null) {
/* 514:611 */       sql.append(" INNER JOIN da.dimensionContable1 dim1 ");
/* 515:    */     }
/* 516:613 */     if (dimension2 != null) {
/* 517:614 */       sql.append(" INNER JOIN da.dimensionContable2 dim2 ");
/* 518:    */     }
/* 519:616 */     if (dimension3 != null) {
/* 520:617 */       sql.append(" INNER JOIN da.dimensionContable3 dim3 ");
/* 521:    */     }
/* 522:619 */     if (dimension4 != null) {
/* 523:620 */       sql.append(" INNER JOIN da.dimensionContable4 dim4 ");
/* 524:    */     }
/* 525:622 */     if (dimension5 != null) {
/* 526:623 */       sql.append(" INNER JOIN da.dimensionContable5 dim5 ");
/* 527:    */     }
/* 528:625 */     sql.append(" INNER JOIN da.asiento at ");
/* 529:626 */     sql.append(" INNER JOIN at.tipoAsiento ta  ");
/* 530:627 */     sql.append(" INNER JOIN at.sucursal s ");
/* 531:628 */     sql.append(" WHERE at.estado IN (:estado) ");
/* 532:629 */     sql.append(" AND (ta.indicadorNIIF = :indicadorNIIF OR :indicadorNIIF = true) ");
/* 533:630 */     sql.append(condicionFecha);
/* 534:631 */     sql.append(" AND cc.idCuentaContable= :idCuentaContable ");
/* 535:632 */     if (dimension1 != null) {
/* 536:633 */       sql.append(" AND dim1.idDimensionContable = :idDimensionContable1 ");
/* 537:    */     }
/* 538:635 */     if (dimension2 != null) {
/* 539:636 */       sql.append(" AND dim2.idDimensionContable = :idDimensionContable2 ");
/* 540:    */     }
/* 541:638 */     if (dimension3 != null) {
/* 542:639 */       sql.append(" AND dim3.idDimensionContable = :idDimensionContable3 ");
/* 543:    */     }
/* 544:641 */     if (dimension4 != null) {
/* 545:642 */       sql.append(" AND dim4.idDimensionContable = :idDimensionContable4 ");
/* 546:    */     }
/* 547:644 */     if (dimension5 != null) {
/* 548:645 */       sql.append(" AND dim5.idDimensionContable = :idDimensionContable5 ");
/* 549:    */     }
/* 550:648 */     if (idSucursal != 0) {
/* 551:649 */       sql.append(" AND s.idSucursal = :idSucursal");
/* 552:    */     }
/* 553:652 */     Query query = this.em.createQuery(sql.toString());
/* 554:653 */     query.setParameter("fechaDesde", fechaDesde);
/* 555:654 */     query.setParameter("fechaHasta", fechaHasta);
/* 556:655 */     query.setParameter("indicadorNIIF", Boolean.valueOf(indicadorNIIF));
/* 557:656 */     query.setParameter("idCuentaContable", Integer.valueOf(cuentaContable.getId()));
/* 558:657 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 559:659 */     if (dimension1 != null) {
/* 560:660 */       query.setParameter("idDimensionContable1", Integer.valueOf(dimension1.getId()));
/* 561:    */     }
/* 562:662 */     if (dimension2 != null) {
/* 563:663 */       query.setParameter("idDimensionContable2", Integer.valueOf(dimension2.getId()));
/* 564:    */     }
/* 565:665 */     if (dimension3 != null) {
/* 566:666 */       query.setParameter("idDimensionContable3", Integer.valueOf(dimension3.getId()));
/* 567:    */     }
/* 568:668 */     if (dimension4 != null) {
/* 569:669 */       query.setParameter("idDimensionContable4", Integer.valueOf(dimension4.getId()));
/* 570:    */     }
/* 571:671 */     if (dimension5 != null) {
/* 572:672 */       query.setParameter("idDimensionContable5", Integer.valueOf(dimension5.getId()));
/* 573:    */     }
/* 574:675 */     if (idSucursal != 0) {
/* 575:676 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 576:    */     }
/* 577:679 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/* 578:    */     
/* 579:681 */     return resultado == null ? BigDecimal.ZERO : resultado;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public CuentaContable existeCuentaContable(String codigoCuentaContable, int idOrganizacion)
/* 583:    */     throws ExcepcionAS2Financiero
/* 584:    */   {
/* 585:692 */     CuentaContable cc = null;
/* 586:    */     try
/* 587:    */     {
/* 588:695 */       StringBuffer sql = new StringBuffer();
/* 589:696 */       sql.append("SELECT c FROM CuentaContable c ");
/* 590:697 */       sql.append(" WHERE c.codigo=:codigoCuentaContable ");
/* 591:698 */       sql.append(" AND c.idOrganizacion = :idOrganizacion");
/* 592:699 */       Query query = this.em.createQuery(sql.toString());
/* 593:700 */       query.setParameter("codigoCuentaContable", codigoCuentaContable);
/* 594:701 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 595:    */       
/* 596:703 */       return (CuentaContable)query.getSingleResult();
/* 597:    */     }
/* 598:    */     catch (NoResultException e) {}
/* 599:708 */     return cc;
/* 600:    */   }
/* 601:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CuentaContableDao
 * JD-Core Version:    0.7.0.1
 */