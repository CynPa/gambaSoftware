/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.FormaPago;
/*   8:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*   9:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.Iterator;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import java.util.Set;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.Query;
/*  26:    */ import javax.persistence.TypedQuery;
/*  27:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  28:    */ import javax.persistence.criteria.CriteriaQuery;
/*  29:    */ import javax.persistence.criteria.Expression;
/*  30:    */ import javax.persistence.criteria.Fetch;
/*  31:    */ import javax.persistence.criteria.Join;
/*  32:    */ import javax.persistence.criteria.JoinType;
/*  33:    */ import javax.persistence.criteria.Order;
/*  34:    */ import javax.persistence.criteria.Path;
/*  35:    */ import javax.persistence.criteria.Predicate;
/*  36:    */ import javax.persistence.criteria.Root;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ public class MovimientoBancarioDao
/*  40:    */   extends AbstractDaoAS2<MovimientoBancario>
/*  41:    */ {
/*  42:    */   @EJB
/*  43:    */   private ServicioGenerico<MovimientoBancarioEstadoCheque> servicioMBEC;
/*  44:    */   @EJB
/*  45:    */   private ServicioGenerico<FormaPago> servicioFormaPago;
/*  46:    */   
/*  47:    */   public MovimientoBancarioDao()
/*  48:    */   {
/*  49: 44 */     super(MovimientoBancario.class);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public MovimientoBancario recuperaMovimientoBancario(int idMovimientoBancario)
/*  53:    */   {
/*  54: 48 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  55:    */     
/*  56:    */ 
/*  57: 51 */     CriteriaQuery<MovimientoBancario> cqCabecera = criteriaBuilder.createQuery(MovimientoBancario.class);
/*  58: 52 */     Root<MovimientoBancario> fromCabecera = cqCabecera.from(MovimientoBancario.class);
/*  59: 53 */     fromCabecera.fetch("documento", JoinType.LEFT);
/*  60: 54 */     fromCabecera.fetch("conceptoContable", JoinType.LEFT);
/*  61: 55 */     Fetch<Object, Object> detalleAsientoC = fromCabecera.fetch("detalleAsiento", JoinType.LEFT);
/*  62: 56 */     detalleAsientoC.fetch("asiento", JoinType.INNER);
/*  63: 57 */     fromCabecera.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  64: 58 */     Path<Integer> pathId = fromCabecera.get("idMovimientoBancario");
/*  65:    */     
/*  66: 60 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idMovimientoBancario)));
/*  67: 61 */     CriteriaQuery<MovimientoBancario> selectMovimientoBancario = cqCabecera.select(fromCabecera);
/*  68:    */     
/*  69: 63 */     MovimientoBancario movimientoBancario = (MovimientoBancario)this.em.createQuery(selectMovimientoBancario).getSingleResult();
/*  70:    */     
/*  71: 65 */     return movimientoBancario;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int contarPorCriterio(Map<String, String> filters)
/*  75:    */   {
/*  76: 77 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  77: 78 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  78:    */     
/*  79: 80 */     Root<MovimientoBancario> from = criteriaQuery.from(MovimientoBancario.class);
/*  80: 81 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  81:    */     
/*  82: 83 */     List<Expression> predicates = getPredicates(filters, criteriaBuilder, from);
/*  83:    */     
/*  84: 85 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  85:    */     
/*  86: 87 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<MovimientoBancario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  90:    */   {
/*  91: 99 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  92:100 */     CriteriaQuery<MovimientoBancario> criteriaQuery = criteriaBuilder.createQuery(MovimientoBancario.class);
/*  93:101 */     Root<MovimientoBancario> from = criteriaQuery.from(MovimientoBancario.class);
/*  94:    */     
/*  95:103 */     from.fetch("documento", JoinType.LEFT);
/*  96:104 */     from.fetch("conceptoContable", JoinType.LEFT);
/*  97:105 */     from.fetch("formaPago", JoinType.LEFT);
/*  98:106 */     from.fetch("estadoCheque", JoinType.LEFT);
/*  99:107 */     from.fetch("detalleAsiento", JoinType.LEFT).fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/* 100:108 */     from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT).fetch("cuentaBancaria", JoinType.LEFT).fetch("banco", JoinType.LEFT);
/* 101:    */     
/* 102:110 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 103:111 */     List<Expression> predicates = getPredicates(filters, criteriaBuilder, from);
/* 104:112 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 105:113 */     CriteriaQuery<MovimientoBancario> select = criteriaQuery.select(from);
/* 106:    */     
/* 107:115 */     TypedQuery<MovimientoBancario> typedQuery = this.em.createQuery(select);
/* 108:116 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 109:    */     
/* 110:118 */     return typedQuery.getResultList();
/* 111:    */   }
/* 112:    */   
/* 113:    */   private List<Expression> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<MovimientoBancario> from)
/* 114:    */   {
/* 115:131 */     List<Expression> predicates = new ArrayList();
/* 116:133 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/* 117:    */     {
/* 118:134 */       String filterProperty = (String)it.next();
/* 119:136 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/* 120:    */       {
/* 121:137 */         String filterValue = (String)filters.get(filterProperty);
/* 122:139 */         if (filterProperty.equals("idOrganizacion"))
/* 123:    */         {
/* 124:140 */           Path<Integer> path = from.get("idOrganizacion");
/* 125:141 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/* 126:    */         }
/* 127:142 */         else if (filterProperty.equals("idCuentaBancariaOrganizacion"))
/* 128:    */         {
/* 129:143 */           Path<Integer> path = from.join("cuentaBancariaOrganizacion").get(filterProperty);
/* 130:144 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/* 131:    */         }
/* 132:145 */         else if (filterProperty.equals("documentoReferencia"))
/* 133:    */         {
/* 134:146 */           Path<String> path = from.get(filterProperty);
/* 135:147 */           predicates.add(criteriaBuilder.like(path, "%" + filterValue.trim() + "%"));
/* 136:    */         }
/* 137:148 */         else if (filterProperty.equals("documento.nombre"))
/* 138:    */         {
/* 139:149 */           Path<String> path = from.join("documento", JoinType.LEFT).get("nombre");
/* 140:150 */           predicates.add(criteriaBuilder.like(path, filterValue.trim() + "%"));
/* 141:    */         }
/* 142:151 */         else if (filterProperty.equals("cuentaBancariaOrganizacion.cuentaBancaria.banco.nombre"))
/* 143:    */         {
/* 144:153 */           Path<String> path = from.join("cuentaBancariaOrganizacion", JoinType.LEFT).join("cuentaBancaria", JoinType.LEFT).join("banco", JoinType.LEFT).get("nombre");
/* 145:154 */           predicates.add(criteriaBuilder.like(path, filterValue.trim() + "%"));
/* 146:    */         }
/* 147:155 */         else if (filterProperty.equals("cuentaBancariaOrganizacion.cuentaBancaria.numero"))
/* 148:    */         {
/* 149:156 */           Path<String> path = from.join("cuentaBancariaOrganizacion", JoinType.LEFT).join("cuentaBancaria", JoinType.LEFT).get("numero");
/* 150:157 */           predicates.add(criteriaBuilder.like(path, filterValue.trim() + "%"));
/* 151:    */         }
/* 152:158 */         else if (filterProperty.equals("detalleAsiento.descripcion"))
/* 153:    */         {
/* 154:159 */           Path<String> path = from.join("detalleAsiento", JoinType.LEFT).get("descripcion");
/* 155:160 */           predicates.add(criteriaBuilder.like(path, "%" + filterValue.trim() + "%"));
/* 156:    */         }
/* 157:161 */         else if (filterProperty.equals("formaPago.nombre"))
/* 158:    */         {
/* 159:162 */           Path<String> path = from.join("formaPago", JoinType.LEFT).get("nombre");
/* 160:163 */           predicates.add(criteriaBuilder.like(path, filterValue.trim() + "%"));
/* 161:    */         }
/* 162:164 */         else if (filterProperty.equals("beneficiario"))
/* 163:    */         {
/* 164:165 */           Path<String> path = from.get(filterProperty);
/* 165:166 */           predicates.add(criteriaBuilder.like(path, "%" + filterValue.trim() + "%"));
/* 166:    */         }
/* 167:167 */         else if (filterProperty.equals("detalleAsiento.asiento.numero"))
/* 168:    */         {
/* 169:168 */           Path<String> path = from.join("detalleAsiento", JoinType.LEFT).join("asiento", JoinType.LEFT).get("numero");
/* 170:169 */           predicates.add(criteriaBuilder.like(path, "%" + filterValue.trim() + "%"));
/* 171:    */         }
/* 172:170 */         else if (filterProperty.equals("formaPago.idFormaPago"))
/* 173:    */         {
/* 174:171 */           Path<Integer> path = from.join("formaPago", JoinType.LEFT).get("idFormaPago");
/* 175:172 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/* 176:    */         }
/* 177:173 */         else if (filterProperty.equals("idConceptoContable"))
/* 178:    */         {
/* 179:174 */           Path<Integer> path = from.join("conceptoContable", JoinType.LEFT).get("idConceptoContable");
/* 180:175 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/* 181:    */         }
/* 182:176 */         else if (filterProperty.contains("fechaDesde"))
/* 183:    */         {
/* 184:177 */           Date fechaDesde = FuncionesUtiles.stringToDate(filterValue);
/* 185:178 */           Expression<Boolean> exFechaDesde = criteriaBuilder.greaterThanOrEqualTo(from.get("fecha").as(Date.class), fechaDesde);
/* 186:179 */           predicates.add(exFechaDesde);
/* 187:    */         }
/* 188:180 */         else if (filterProperty.contains("fechaHasta"))
/* 189:    */         {
/* 190:181 */           Date fechaHasta = FuncionesUtiles.stringToDate(filterValue);
/* 191:182 */           Expression<Boolean> exFechaHasta = criteriaBuilder.lessThanOrEqualTo(from.get("fecha").as(Date.class), fechaHasta);
/* 192:183 */           predicates.add(exFechaHasta);
/* 193:    */         }
/* 194:184 */         else if (filterProperty.contains("fechaPosfechadoDesde"))
/* 195:    */         {
/* 196:185 */           Date fechaDesde = FuncionesUtiles.stringToDate(filterValue);
/* 197:186 */           Expression<Boolean> exFechaDesde = criteriaBuilder.greaterThanOrEqualTo(from.get("fechaPosfechado").as(Date.class), fechaDesde);
/* 198:187 */           predicates.add(exFechaDesde);
/* 199:    */         }
/* 200:188 */         else if (filterProperty.contains("fechaPosfechadoHasta"))
/* 201:    */         {
/* 202:189 */           Date fechaHasta = FuncionesUtiles.stringToDate(filterValue);
/* 203:190 */           Expression<Boolean> exFechaHasta = criteriaBuilder.lessThanOrEqualTo(from.get("fechaPosfechado").as(Date.class), fechaHasta);
/* 204:191 */           predicates.add(exFechaHasta);
/* 205:    */         }
/* 206:192 */         else if (filterProperty.equals("formaPago.indicadorCheque"))
/* 207:    */         {
/* 208:193 */           Path<Boolean> path = from.join("formaPago", JoinType.LEFT).get("indicadorCheque");
/* 209:194 */           predicates.add(criteriaBuilder.equal(path, Boolean.valueOf(Boolean.valueOf(filterValue).booleanValue())));
/* 210:    */         }
/* 211:195 */         else if (filterProperty.contains("estadoCheque.idEstadoCheque"))
/* 212:    */         {
/* 213:196 */           Path<Integer> path = from.join("estadoCheque", JoinType.LEFT).get("idEstadoCheque");
/* 214:197 */           predicates.add(criteriaBuilder.notEqual(path, filterValue));
/* 215:    */         }
/* 216:198 */         else if (filterProperty.equals("estadoCheque.nombre"))
/* 217:    */         {
/* 218:199 */           Path<String> path = from.join("estadoCheque", JoinType.LEFT).get("nombre");
/* 219:200 */           predicates.add(criteriaBuilder.like(path, "%" + filterValue.trim() + "%"));
/* 220:    */         }
/* 221:201 */         else if (filterProperty.equals("valor"))
/* 222:    */         {
/* 223:202 */           Path<BigDecimal> path = from.get(filterProperty);
/* 224:203 */           if (filterValue.startsWith("<")) {
/* 225:204 */             predicates.add(criteriaBuilder.lessThan(path, new BigDecimal(filterValue.substring(1))));
/* 226:205 */           } else if (filterValue.startsWith(">")) {
/* 227:206 */             predicates.add(criteriaBuilder.greaterThan(path, new BigDecimal(filterValue.substring(1))));
/* 228:    */           } else {
/* 229:208 */             predicates.add(criteriaBuilder.equal(path, new BigDecimal(filterValue)));
/* 230:    */           }
/* 231:    */         }
/* 232:210 */         else if (filterProperty.contains("cuentaBancariaOrganizacion.tipoCuentaBancariaOrganizacion"))
/* 233:    */         {
/* 234:211 */           Path<TipoCuentaBancariaOrganizacion> path = from.join("cuentaBancariaOrganizacion", JoinType.LEFT).get("tipoCuentaBancariaOrganizacion");
/* 235:    */           
/* 236:213 */           predicates.add(criteriaBuilder.equal(path, TipoCuentaBancariaOrganizacion.valueOf(filterValue)));
/* 237:    */         }
/* 238:214 */         else if (filterProperty.contains("estado"))
/* 239:    */         {
/* 240:215 */           String operador = "";
/* 241:216 */           if (filterValue.startsWith("!="))
/* 242:    */           {
/* 243:217 */             filterValue = filterValue.replace("!=", "");
/* 244:218 */             operador = "!=";
/* 245:    */           }
/* 246:220 */           Path<Estado> path = from.get("estado");
/* 247:221 */           Estado estado = Estado.valueOf(filterValue);
/* 248:222 */           if (operador.equals("!=")) {
/* 249:223 */             predicates.add(criteriaBuilder.notEqual(path, estado));
/* 250:    */           } else {
/* 251:225 */             predicates.add(criteriaBuilder.equal(path, estado));
/* 252:    */           }
/* 253:    */         }
/* 254:    */       }
/* 255:    */     }
/* 256:230 */     return predicates;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<DetalleInterfazContable> getMovimientoBanacarioCBIC(int idMovimientoBancario)
/* 260:    */   {
/* 261:244 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable, mb.descripcion, CASE WHEN mb.conceptoContable IS NULL THEN do.nombre ELSE mbcc.nombre END,  concat(mb.documentoReferencia,'-',mb.beneficiario), mb.beneficiario, mb.valor)  FROM MovimientoBancario mb  INNER JOIN mb.cuentaBancariaOrganizacion cbo  INNER JOIN cbo.cuentaContableBanco cc  INNER JOIN cbo.cuentaBancaria cb  INNER JOIN cb.banco b  INNER JOIN mb.documento do  LEFT JOIN mb.conceptoContable mbcc WHERE mb.idMovimientoBancario=:idMovimientoBancario");
/* 262:    */     
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:254 */     query.setParameter("idMovimientoBancario", Integer.valueOf(idMovimientoBancario));
/* 272:    */     
/* 273:256 */     return query.getResultList();
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<DetalleInterfazContable> getMovimientoBanacarioGBIC(int idMovimientoBancario)
/* 277:    */   {
/* 278:269 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(ccgb.idCuentaContable, mb.descripcion, do.nombre, concat(mb.documentoReferencia,'-',mb.beneficiario), -(mb.valor))  FROM MovimientoBancario mb  INNER JOIN mb.cuentaBancariaOrganizacion cbo  INNER JOIN cbo.cuentaContableGastosBancarios ccgb  INNER JOIN cbo.cuentaBancaria cb  INNER JOIN cb.banco b  INNER JOIN mb.documento do  WHERE mb.idMovimientoBancario=:idMovimientoBancario");
/* 279:    */     
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:277 */     query.setParameter("idMovimientoBancario", Integer.valueOf(idMovimientoBancario));
/* 287:    */     
/* 288:279 */     return query.getResultList();
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<DetalleInterfazContable> getMovimientoBanacarioCCIC(int idMovimientoBancario)
/* 292:    */   {
/* 293:292 */     Query query = this.em.createQuery("SELECT new DetalleInterfazContable(CASE WHEN mb.valor < 0 THEN cc.cuentaContableDebe.idCuentaContable ELSE cc.cuentaContableHaber.idCuentaContable END, mb.descripcion, cc.nombre,concat(mb.documentoReferencia,'-',mb.beneficiario), ABS(mb.valor))  FROM MovimientoBancario mb  INNER JOIN mb.documento do  INNER JOIN mb.conceptoContable cc  INNER JOIN mb.documento do  WHERE mb.idMovimientoBancario=:idMovimientoBancario");
/* 294:    */     
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:298 */     query.setParameter("idMovimientoBancario", Integer.valueOf(idMovimientoBancario));
/* 300:    */     
/* 301:300 */     return query.getResultList();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public List<MovimientoBancario> getListaMovimientoBancarioPorAsiento(int idAsiento)
/* 305:    */   {
/* 306:305 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 307:306 */     CriteriaQuery<MovimientoBancario> criteriaQuery = criteriaBuilder.createQuery(MovimientoBancario.class);
/* 308:307 */     Root<MovimientoBancario> from = criteriaQuery.from(MovimientoBancario.class);
/* 309:308 */     from.fetch("formaPago", JoinType.LEFT);
/* 310:    */     
/* 311:310 */     Fetch<Object, Object> cbo = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/* 312:311 */     Fetch<Object, Object> cb = cbo.fetch("cuentaBancaria", JoinType.LEFT);
/* 313:312 */     cb.fetch("banco", JoinType.LEFT);
/* 314:    */     
/* 315:    */ 
/* 316:315 */     Predicate asiento = criteriaBuilder.equal(from.join("detalleAsiento").join("asiento").get("idAsiento"), Integer.valueOf(idAsiento));
/* 317:316 */     Predicate valor = criteriaBuilder.lessThan(from.get("valor"), BigDecimal.ZERO);
/* 318:    */     
/* 319:318 */     criteriaQuery.where(criteriaBuilder.and(asiento, valor));
/* 320:    */     
/* 321:320 */     criteriaQuery.orderBy(new Order[] { criteriaBuilder.asc(from.get("valor")) });
/* 322:    */     
/* 323:322 */     CriteriaQuery<MovimientoBancario> select = criteriaQuery.select(from);
/* 324:    */     
/* 325:324 */     TypedQuery<MovimientoBancario> typedQuery = this.em.createQuery(select);
/* 326:    */     
/* 327:326 */     List<MovimientoBancario> lista = typedQuery.getResultList();
/* 328:328 */     for (MovimientoBancario movimientoBancario : lista)
/* 329:    */     {
/* 330:332 */       CriteriaQuery<MovimientoBancarioEstadoCheque> cqMovimientoBancarioEstadoCheque = criteriaBuilder.createQuery(MovimientoBancarioEstadoCheque.class);
/* 331:    */       
/* 332:334 */       Root<MovimientoBancarioEstadoCheque> fromMovimientoBancarioEstadoCheque = cqMovimientoBancarioEstadoCheque.from(MovimientoBancarioEstadoCheque.class);
/* 333:    */       
/* 334:336 */       Path<Integer> pathIdMovimientoBancario = fromMovimientoBancarioEstadoCheque.join("movimientoBancario").get("idMovimientoBancario");
/* 335:337 */       cqMovimientoBancarioEstadoCheque.where(criteriaBuilder.equal(pathIdMovimientoBancario, Integer.valueOf(movimientoBancario.getIdMovimientoBancario())));
/* 336:    */       
/* 337:    */ 
/* 338:340 */       CriteriaQuery<MovimientoBancarioEstadoCheque> selectMovimientoBancarioEstadoCheque = cqMovimientoBancarioEstadoCheque.select(fromMovimientoBancarioEstadoCheque);
/* 339:    */       
/* 340:    */ 
/* 341:343 */       List<MovimientoBancarioEstadoCheque> listaMovimientoBancarioEstadoCheque = this.em.createQuery(selectMovimientoBancarioEstadoCheque).getResultList();
/* 342:344 */       detach(movimientoBancario);
/* 343:345 */       movimientoBancario.setListaMovimientoBancarioEstadoCheque(listaMovimientoBancarioEstadoCheque);
/* 344:    */     }
/* 345:348 */     return lista;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void verificarExitenciaDocumentoReferencia(MovimientoBancario movimientoBancario)
/* 349:    */     throws ExcepcionAS2Financiero
/* 350:    */   {
/* 351:355 */     StringBuilder sql = new StringBuilder();
/* 352:356 */     sql.append(" SELECT mb FROM MovimientoBancario mb ");
/* 353:357 */     sql.append(" JOIN FETCH mb.cuentaBancariaOrganizacion cbo ");
/* 354:358 */     sql.append(" JOIN FETCH cbo.cuentaBancaria cb ");
/* 355:359 */     sql.append(" JOIN FETCH mb.formaPago fp ");
/* 356:360 */     sql.append(" WHERE mb.documentoReferencia =:documentoReferencia ");
/* 357:361 */     sql.append(" AND cbo.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion ");
/* 358:362 */     sql.append(" AND fp.idFormaPago=:idFormaPago ");
/* 359:363 */     sql.append(" AND mb.idOrganizacion=:idOrganizacion ");
/* 360:364 */     sql.append(" AND mb.idMovimientoBancario!=:idMovimientoBancario ");
/* 361:365 */     sql.append(" AND mb.valor < 0 ");
/* 362:    */     
/* 363:    */ 
/* 364:368 */     Query query = this.em.createQuery(sql.toString());
/* 365:369 */     query.setParameter("documentoReferencia", movimientoBancario.getDocumentoReferencia());
/* 366:370 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(movimientoBancario.getCuentaBancariaOrganizacion().getId()));
/* 367:371 */     query.setParameter("idFormaPago", Integer.valueOf(movimientoBancario.getFormaPago().getId()));
/* 368:372 */     query.setParameter("idOrganizacion", Integer.valueOf(movimientoBancario.getIdOrganizacion()));
/* 369:373 */     query.setParameter("idMovimientoBancario", Integer.valueOf(movimientoBancario.getIdMovimientoBancario()));
/* 370:    */     
/* 371:375 */     List<MovimientoBancario> lista = query.getResultList();
/* 372:377 */     if (!lista.isEmpty())
/* 373:    */     {
/* 374:378 */       MovimientoBancario mb = (MovimientoBancario)lista.get(0);
/* 375:379 */       if ((mb.getDocumentoReferencia() != null) && (mb.getCuentaBancariaOrganizacion().getCuentaBancaria() != null) && 
/* 376:380 */         (mb.getFormaPago().getNombre() != null)) {
/* 377:382 */         throw new ExcepcionAS2Financiero("msg_error_existe_documento_referencia", " Doc. " + mb.getDocumentoReferencia() + " #CB. " + mb.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero() + " Fp. " + mb.getFormaPago().getNombre());
/* 378:    */       }
/* 379:    */     }
/* 380:    */   }
/* 381:    */   
/* 382:    */   public List<DetalleInterfazContableProceso> getInterfazMovimientoBancarioDimensiones(List<Integer> listaMovimientoBancario)
/* 383:    */   {
/* 384:403 */     StringBuilder sql = new StringBuilder();
/* 385:    */     
/* 386:405 */     String descripcion = "";
/* 387:406 */     String grupoDescripcion = "";
/* 388:407 */     if (listaMovimientoBancario.size() == 1)
/* 389:    */     {
/* 390:408 */       descripcion = "concat(mb.documentoReferencia,'-',mb.beneficiario)";
/* 391:409 */       grupoDescripcion = "," + descripcion;
/* 392:    */     }
/* 393:    */     else
/* 394:    */     {
/* 395:411 */       descripcion = "''";
/* 396:    */     }
/* 397:414 */     sql.append(" SELECT new DetalleInterfazContableProcesoConceptoContable(d.idDocumento, mb.idSucursal,");
/* 398:415 */     sql.append(" mbcc.idConceptoContable, " + descripcion + ", ABS(SUM(mb.valor)) )");
/* 399:416 */     sql.append(" FROM MovimientoBancario mb ");
/* 400:417 */     sql.append(" INNER JOIN mb.documento d ");
/* 401:418 */     sql.append(" LEFT JOIN mb.conceptoContable mbcc ");
/* 402:419 */     sql.append(" WHERE mb.idMovimientoBancario in (:listaMovimientoBancario) ");
/* 403:420 */     sql.append(" GROUP BY d.idDocumento, mb.idSucursal, mbcc.idConceptoContable" + grupoDescripcion);
/* 404:421 */     sql.append(" HAVING SUM(mb.valor) <> 0");
/* 405:    */     
/* 406:423 */     Query query = this.em.createQuery(sql.toString());
/* 407:424 */     query.setParameter("listaMovimientoBancario", listaMovimientoBancario);
/* 408:425 */     return query.getResultList();
/* 409:    */   }
/* 410:    */   
/* 411:    */   public List<MovimientoBancarioEstadoCheque> getListaEstadosCheque(MovimientoBancario mb)
/* 412:    */   {
/* 413:435 */     StringBuilder sql = new StringBuilder();
/* 414:436 */     sql.append(" SELECT mbec FROM MovimientoBancarioEstadoCheque mbec ");
/* 415:437 */     sql.append(" INNER JOIN FETCH mbec.estadoCheque ec ");
/* 416:438 */     sql.append(" WHERE mbec.movimientoBancario = :movimientoBancario ");
/* 417:439 */     Query query = this.em.createQuery(sql.toString());
/* 418:440 */     query.setParameter("movimientoBancario", mb);
/* 419:    */     
/* 420:442 */     return query.getResultList();
/* 421:    */   }
/* 422:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MovimientoBancarioDao
 * JD-Core Version:    0.7.0.1
 */