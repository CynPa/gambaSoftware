/*   1:    */ package com.asinfo.as2.dao.amortizacion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   7:    */ import com.asinfo.as2.entities.amortizacion.Amortizacion;
/*   8:    */ import com.asinfo.as2.entities.amortizacion.DetalleAmortizacion;
/*   9:    */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ import javax.persistence.EntityManager;
/*  19:    */ import javax.persistence.Query;
/*  20:    */ import javax.persistence.TypedQuery;
/*  21:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  22:    */ import javax.persistence.criteria.CriteriaQuery;
/*  23:    */ import javax.persistence.criteria.Expression;
/*  24:    */ import javax.persistence.criteria.Fetch;
/*  25:    */ import javax.persistence.criteria.JoinType;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ 
/*  29:    */ @Stateless
/*  30:    */ public class AmortizacionDao
/*  31:    */   extends AbstractDaoAS2<Amortizacion>
/*  32:    */ {
/*  33:    */   public AmortizacionDao()
/*  34:    */   {
/*  35: 40 */     super(Amortizacion.class);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Amortizacion cargarDetalle(Amortizacion amortizacion)
/*  39:    */   {
/*  40: 44 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  41: 45 */     CriteriaQuery<Amortizacion> criteriaQuery = cb.createQuery(Amortizacion.class);
/*  42: 46 */     Root<Amortizacion> from = criteriaQuery.from(Amortizacion.class);
/*  43: 47 */     from.fetch("tipoAmortizacion", JoinType.INNER);
/*  44: 48 */     from.fetch("documento", JoinType.INNER).fetch("secuencia", JoinType.LEFT);
/*  45: 49 */     from.fetch("facturaProveedor", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  46: 50 */     from.fetch("sucursal", JoinType.INNER);
/*  47: 51 */     criteriaQuery.where(cb.equal(from.get("idAmortizacion"), Integer.valueOf(amortizacion.getIdAmortizacion())));
/*  48: 52 */     CriteriaQuery<Amortizacion> select = criteriaQuery.select(from);
/*  49: 53 */     Amortizacion tmpAmortizacion = (Amortizacion)this.em.createQuery(select).getSingleResult();
/*  50:    */     
/*  51: 55 */     CriteriaQuery<DetalleAmortizacion> cqDetalle = cb.createQuery(DetalleAmortizacion.class);
/*  52: 56 */     Root<DetalleAmortizacion> fromDetalle = cqDetalle.from(DetalleAmortizacion.class);
/*  53: 57 */     cqDetalle.where(cb.equal(fromDetalle.join("amortizacion"), tmpAmortizacion));
/*  54: 58 */     CriteriaQuery<DetalleAmortizacion> selectDetalle = cqDetalle.select(fromDetalle);
/*  55: 59 */     List<DetalleAmortizacion> listaDetalleAmortizacion = this.em.createQuery(selectDetalle).getResultList();
/*  56: 60 */     this.em.detach(tmpAmortizacion);
/*  57: 61 */     tmpAmortizacion.setListaDetalleAmortizacion(listaDetalleAmortizacion);
/*  58:    */     
/*  59: 63 */     return tmpAmortizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Amortizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64: 67 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  65: 68 */     CriteriaQuery<Amortizacion> criteriaQuery = criteriaBuilder.createQuery(Amortizacion.class);
/*  66: 69 */     Root<Amortizacion> from = criteriaQuery.from(Amortizacion.class);
/*  67: 70 */     from.fetch("tipoAmortizacion", JoinType.INNER);
/*  68: 71 */     from.fetch("facturaProveedor", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  69:    */     
/*  70: 73 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  71: 74 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  72:    */     
/*  73: 76 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  74:    */     
/*  75: 78 */     CriteriaQuery<Amortizacion> select = criteriaQuery.select(from);
/*  76:    */     
/*  77: 80 */     TypedQuery<Amortizacion> typedQuery = this.em.createQuery(select);
/*  78: 81 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  79:    */     
/*  80: 83 */     return typedQuery.getResultList();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean cuotasContabilizadas(Amortizacion amortizacion)
/*  84:    */   {
/*  85: 87 */     StringBuilder sql = new StringBuilder();
/*  86: 88 */     sql.append(" SELECT COUNT(1) FROM DetalleAmortizacion da ");
/*  87: 89 */     sql.append(" WHERE da.amortizacion = :amortizacion ");
/*  88: 90 */     sql.append(" AND da.estado = :estado ");
/*  89: 91 */     Query query = this.em.createQuery(sql.toString());
/*  90: 92 */     query.setParameter("amortizacion", amortizacion);
/*  91: 93 */     query.setParameter("estado", Estado.CONTABILIZADO);
/*  92: 94 */     long result = ((Long)query.getSingleResult()).longValue();
/*  93:    */     
/*  94: 96 */     return result > 0L;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<DetalleInterfazContableProceso> getInterfazContableAmortizacion(Date fechaDesde, Date fechaHasta, int idOrganizacion, ProcesoContabilizacionEnum procesoContabilizacion)
/*  98:    */   {
/*  99:102 */     String mes = FuncionesUtiles.nombreMes(FuncionesUtiles.getMes(fechaDesde) - 1);
/* 100:103 */     StringBuilder sql = new StringBuilder();
/* 101:104 */     sql.append(" SELECT new DetalleInterfazContableProcesoAmortizaciones(s.idSucursal, s.nombre, ta.idTipoAmortizacion, ta.nombre, ");
/* 102:105 */     if (procesoContabilizacion.equals(ProcesoContabilizacionEnum.AMORTIZACION)) {
/* 103:106 */       sql.append(" 'Amortizacion mes: " + mes + "', SUM(da.valor)) ");
/* 104:    */     } else {
/* 105:108 */       sql.append(" CONCAT('Amortizacion mes: " + mes + ". Numero: ', a.numero), da.valor)");
/* 106:    */     }
/* 107:110 */     sql.append(" FROM DetalleAmortizacion da ");
/* 108:111 */     sql.append(" INNER JOIN da.amortizacion a ");
/* 109:112 */     sql.append(" INNER JOIN a.sucursal s ");
/* 110:113 */     sql.append(" INNER JOIN a.tipoAmortizacion ta ");
/* 111:114 */     sql.append(" WHERE a.idOrganizacion = :idOrganizacion ");
/* 112:115 */     sql.append(" AND da.fechaVencimiento BETWEEN :fechaDesde AND :fechaHasta ");
/* 113:116 */     sql.append(" AND da.estado = :estado ");
/* 114:117 */     if (procesoContabilizacion.equals(ProcesoContabilizacionEnum.AMORTIZACION)) {
/* 115:118 */       sql.append(" GROUP BY s.idSucursal, s.nombre, ta.idTipoAmortizacion, ta.nombre, YEAR(da.fechaVencimiento), MONTH(da.fechaVencimiento) ");
/* 116:    */     }
/* 117:120 */     Query query = this.em.createQuery(sql.toString());
/* 118:121 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 119:122 */     query.setParameter("fechaDesde", fechaDesde);
/* 120:123 */     query.setParameter("fechaHasta", fechaHasta);
/* 121:124 */     query.setParameter("estado", Estado.ELABORADO);
/* 122:    */     
/* 123:126 */     return query.getResultList();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<Integer> obtenerAmortizacionesContabilizadas(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 127:    */   {
/* 128:131 */     StringBuilder sql = new StringBuilder();
/* 129:132 */     sql.append(" SELECT da.amortizacion.idAmortizacion ");
/* 130:133 */     sql.append(" FROM DetalleAmortizacion da ");
/* 131:134 */     sql.append(" WHERE da.idOrganizacion = :idOrganizacion ");
/* 132:135 */     sql.append(" AND da.fechaVencimiento BETWEEN :fechaDesde AND :fechaHasta ");
/* 133:136 */     sql.append(" AND da.estado != :estado ");
/* 134:137 */     sql.append(" GROUP BY da.amortizacion.idAmortizacion ");
/* 135:138 */     Query query = this.em.createQuery(sql.toString());
/* 136:139 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 137:140 */     query.setParameter("fechaDesde", fechaDesde);
/* 138:141 */     query.setParameter("fechaHasta", fechaHasta);
/* 139:142 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 140:    */     
/* 141:144 */     return query.getResultList();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int actualizarDetalleAmortizacion(int idAmortizacion, Asiento asiento, Date fechaDesde, Date fechaHasta, InterfazContableProceso interfazContableProceso, Estado estado)
/* 145:    */   {
/* 146:149 */     StringBuilder sql = new StringBuilder();
/* 147:150 */     sql.append(" UPDATE DetalleAmortizacion da set da.estado = :estado, da.asiento = :asiento, da.interfazContableProceso = :interfazContableProceso ");
/* 148:151 */     sql.append(" WHERE da.amortizacion.idAmortizacion = :idAmortizacion ");
/* 149:152 */     sql.append(" AND da.fechaVencimiento BETWEEN :fechaDesde AND :fechaHasta ");
/* 150:153 */     sql.append(" AND da.estado = :estadoElaborado ");
/* 151:154 */     Query query = this.em.createQuery(sql.toString());
/* 152:155 */     query.setParameter("idAmortizacion", Integer.valueOf(idAmortizacion));
/* 153:156 */     query.setParameter("fechaDesde", fechaDesde);
/* 154:157 */     query.setParameter("fechaHasta", fechaHasta);
/* 155:158 */     query.setParameter("estado", estado);
/* 156:159 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 157:160 */     query.setParameter("asiento", asiento);
/* 158:161 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 159:    */     
/* 160:163 */     return query.executeUpdate();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Object[] obtenerTotalesContabilizados(int idAmortizacion)
/* 164:    */   {
/* 165:167 */     StringBuilder sql = new StringBuilder();
/* 166:168 */     sql.append(" SELECT COALESCE(SUM(da.valor),0), COUNT(1) ");
/* 167:169 */     sql.append(" FROM DetalleAmortizacion da ");
/* 168:170 */     sql.append(" WHERE da.amortizacion.idAmortizacion = :idAmortizacion ");
/* 169:171 */     sql.append(" AND da.estado = :estado ");
/* 170:172 */     Query query = this.em.createQuery(sql.toString());
/* 171:173 */     query.setParameter("idAmortizacion", Integer.valueOf(idAmortizacion));
/* 172:174 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 173:    */     
/* 174:176 */     return (Object[])query.getSingleResult();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int actualizarCabeceraAmortizacion(int idAmortizacion, BigDecimal valorAmortizadoTotal, int mesesAmortizados)
/* 178:    */   {
/* 179:180 */     StringBuilder sql = new StringBuilder();
/* 180:181 */     sql.append(" UPDATE Amortizacion a set a.valorAmortizadoTotal = :valorAmortizadoTotal, ");
/* 181:182 */     sql.append(" a.mesesAmortizados = :mesesAmortizados ");
/* 182:183 */     sql.append(" WHERE a.idAmortizacion = :idAmortizacion ");
/* 183:184 */     Query query = this.em.createQuery(sql.toString());
/* 184:185 */     query.setParameter("idAmortizacion", Integer.valueOf(idAmortizacion));
/* 185:186 */     query.setParameter("valorAmortizadoTotal", valorAmortizadoTotal);
/* 186:187 */     query.setParameter("mesesAmortizados", Integer.valueOf(mesesAmortizados));
/* 187:    */     
/* 188:189 */     return query.executeUpdate();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<Object[]> obtenerMesesContabilizar(int idOrganizacion, Date fechaActual)
/* 192:    */   {
/* 193:194 */     StringBuilder sql = new StringBuilder();
/* 194:195 */     sql.append(" SELECT YEAR(da.fechaVencimiento), MONTH(da.fechaVencimiento), ta.idTipoAsiento ");
/* 195:196 */     sql.append(" FROM DetalleAmortizacion da ");
/* 196:197 */     sql.append(" INNER JOIN da.amortizacion a ");
/* 197:198 */     sql.append(" INNER JOIN a.documento d ");
/* 198:199 */     sql.append(" INNER JOIN d.tipoAsiento ta ");
/* 199:200 */     sql.append(" WHERE da.fechaVencimiento <= :fechaActual AND da.estado = :estado ");
/* 200:201 */     sql.append(" AND da.idOrganizacion = :idOrganizacion ");
/* 201:202 */     sql.append(" GROUP BY YEAR(da.fechaVencimiento), MONTH(da.fechaVencimiento), ta.idTipoAsiento ");
/* 202:203 */     sql.append(" ORDER BY YEAR(da.fechaVencimiento), MONTH(da.fechaVencimiento) DESC ");
/* 203:204 */     Query query = this.em.createQuery(sql.toString());
/* 204:205 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 205:206 */     query.setParameter("fechaActual", fechaActual);
/* 206:207 */     query.setParameter("estado", Estado.ELABORADO);
/* 207:    */     
/* 208:209 */     return query.getResultList();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<Object[]> getReporteAmortizacion(Amortizacion amortizacion)
/* 212:    */   {
/* 213:214 */     StringBuilder sql = new StringBuilder();
/* 214:215 */     sql.append(" SELECT a.numero, a.fechaInicioAmortizacion, a.mesesAmortizados, a.mesesPorAmortizarReal, ");
/* 215:216 */     sql.append(" a.valor, a.valorAmortizado, a.valorAmortizadoTotal, fp.numero, CONCAT(fps.establecimiento, '-', fps.puntoEmision, '-', fps.numero), a.fechaCompra, ");
/* 216:217 */     sql.append(" a.descripcion, ta.nombre, da.fechaVencimiento, da.valor, da.estado, asi.numero ");
/* 217:218 */     sql.append(" FROM DetalleAmortizacion da ");
/* 218:219 */     sql.append(" INNER JOIN da.amortizacion a ");
/* 219:220 */     sql.append(" INNER JOIN a.tipoAmortizacion ta ");
/* 220:221 */     sql.append(" LEFT JOIN a.facturaProveedor fp ");
/* 221:222 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 222:223 */     sql.append(" LEFT JOIN da.asiento asi ");
/* 223:224 */     sql.append(" WHERE a = :amortizacion ");
/* 224:225 */     Query query = this.em.createQuery(sql.toString());
/* 225:226 */     query.setParameter("amortizacion", amortizacion);
/* 226:    */     
/* 227:228 */     return query.getResultList();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<Object[]> getAmortizaciones(Date fechaHasta, TipoAmortizacion tipoAmortizacion, int idOrganizacion)
/* 231:    */   {
/* 232:233 */     StringBuilder sql = new StringBuilder();
/* 233:234 */     sql.append(" SELECT a.idAmortizacion, ta.nombre, a.numero, a.fechaInicioAmortizacion, ");
/* 234:235 */     sql.append(" '', a.mesesPorAmortizarReal, a.valor, a.valorAmortizado, '', ");
/* 235:236 */     sql.append(" '', e.nombreFiscal, fp.numero, CONCAT(fps.establecimiento, '-', fps.puntoEmision, '-', fps.numero), a.fechaCompra, a.descripcion ");
/* 236:237 */     sql.append(" FROM Amortizacion a ");
/* 237:238 */     sql.append(" INNER JOIN a.tipoAmortizacion ta ");
/* 238:239 */     sql.append(" LEFT JOIN a.facturaProveedor fp ");
/* 239:240 */     sql.append(" LEFT JOIN fp.empresa e ");
/* 240:241 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fps ");
/* 241:242 */     sql.append(" WHERE a.idOrganizacion = :idOrganizacion ");
/* 242:243 */     sql.append(" AND a.fechaInicioAmortizacion <= :fechaHasta ");
/* 243:244 */     if (null != tipoAmortizacion) {
/* 244:245 */       sql.append(" AND a.tipoAmortizacion = :tipoAmortizacion ");
/* 245:    */     }
/* 246:247 */     sql.append(" ORDER BY ta.nombre ");
/* 247:248 */     Query query = this.em.createQuery(sql.toString());
/* 248:249 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 249:250 */     query.setParameter("fechaHasta", fechaHasta);
/* 250:251 */     if (null != tipoAmortizacion) {
/* 251:252 */       query.setParameter("tipoAmortizacion", tipoAmortizacion);
/* 252:    */     }
/* 253:255 */     return query.getResultList();
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<DetalleAmortizacion> getDetalleAmortizacion(int idAmortizacion, Date fechaDesde, Date fechaHasta, Estado estado, int idOrganizacion)
/* 257:    */   {
/* 258:260 */     StringBuilder sql = new StringBuilder();
/* 259:261 */     sql.append(" SELECT da ");
/* 260:262 */     sql.append(" FROM DetalleAmortizacion da ");
/* 261:263 */     sql.append(" INNER JOIN FETCH da.amortizacion a ");
/* 262:264 */     sql.append(" WHERE da.idOrganizacion = :idOrganizacion ");
/* 263:265 */     if (idAmortizacion != 0) {
/* 264:266 */       sql.append(" AND da.amortizacion.idAmortizacion = :idAmortizacion ");
/* 265:    */     }
/* 266:268 */     if (fechaDesde != null) {
/* 267:269 */       sql.append(" AND da.fechaVencimiento >= :fechaDesde ");
/* 268:    */     }
/* 269:271 */     if (fechaHasta != null) {
/* 270:272 */       sql.append(" AND da.fechaVencimiento <= :fechaHasta ");
/* 271:    */     }
/* 272:274 */     if (estado != null) {
/* 273:275 */       sql.append(" AND da.estado = :estado ");
/* 274:    */     }
/* 275:277 */     sql.append(" ORDER BY da.fechaVencimiento  ");
/* 276:    */     
/* 277:279 */     Query query = this.em.createQuery(sql.toString());
/* 278:280 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 279:281 */     if (idAmortizacion != 0) {
/* 280:282 */       query.setParameter("idAmortizacion", Integer.valueOf(idAmortizacion));
/* 281:    */     }
/* 282:284 */     if (fechaDesde != null) {
/* 283:285 */       query.setParameter("fechaDesde", fechaDesde);
/* 284:    */     }
/* 285:287 */     if (fechaHasta != null) {
/* 286:288 */       query.setParameter("fechaHasta", fechaHasta);
/* 287:    */     }
/* 288:290 */     if (estado != null) {
/* 289:291 */       query.setParameter("estado", estado);
/* 290:    */     }
/* 291:294 */     return query.getResultList();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public Object[] getDetalleAmortizadoFecha(int idAmortizacion, Date fechaHasta, Estado estado)
/* 295:    */   {
/* 296:298 */     StringBuilder sql = new StringBuilder();
/* 297:299 */     sql.append(" SELECT COUNT(1), COALESCE(SUM(COALESCE(da.valor,0)),0), ");
/* 298:300 */     sql.append(" COALESCE(SUM(CASE WHEN MONTH(da.fechaVencimiento) = MONTH(:fechaHasta) THEN COALESCE(da.valor,0) ELSE 0 END),0) ");
/* 299:301 */     sql.append(" FROM DetalleAmortizacion da ");
/* 300:302 */     sql.append(" WHERE da.amortizacion.idAmortizacion = :idAmortizacion ");
/* 301:303 */     if (fechaHasta != null) {
/* 302:304 */       sql.append(" AND da.fechaVencimiento <= :fechaHasta ");
/* 303:    */     }
/* 304:306 */     if (estado != null) {
/* 305:307 */       sql.append(" AND da.estado = :estado ");
/* 306:    */     }
/* 307:309 */     Query query = this.em.createQuery(sql.toString());
/* 308:310 */     query.setParameter("idAmortizacion", Integer.valueOf(idAmortizacion));
/* 309:311 */     if (fechaHasta != null) {
/* 310:312 */       query.setParameter("fechaHasta", fechaHasta);
/* 311:    */     }
/* 312:314 */     if (estado != null) {
/* 313:315 */       query.setParameter("estado", estado);
/* 314:    */     }
/* 315:318 */     return (Object[])query.getSingleResult();
/* 316:    */   }
/* 317:    */   
/* 318:    */   public List<Integer> obtenerAmortizacionesInterfazContable(InterfazContableProceso interfazContableProceso)
/* 319:    */   {
/* 320:323 */     StringBuilder sql = new StringBuilder();
/* 321:324 */     sql.append(" SELECT da.amortizacion.idAmortizacion ");
/* 322:325 */     sql.append(" FROM DetalleAmortizacion da ");
/* 323:326 */     sql.append(" WHERE da.interfazContableProceso = :interfazContableProceso ");
/* 324:327 */     sql.append(" GROUP BY da.amortizacion.idAmortizacion ");
/* 325:328 */     Query query = this.em.createQuery(sql.toString());
/* 326:329 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 327:    */     
/* 328:331 */     return query.getResultList();
/* 329:    */   }
/* 330:    */   
/* 331:    */   public int anularAmortizacionesInterfazContable(InterfazContableProceso interfazContableProceso)
/* 332:    */   {
/* 333:335 */     StringBuilder sql = new StringBuilder();
/* 334:336 */     sql.append(" UPDATE DetalleAmortizacion da ");
/* 335:337 */     sql.append(" SET da.estado = :estado, da.asiento = NULL, da.interfazContableProceso = NULL ");
/* 336:338 */     sql.append(" WHERE da.interfazContableProceso = :interfazContableProceso ");
/* 337:339 */     Query query = this.em.createQuery(sql.toString());
/* 338:340 */     query.setParameter("estado", Estado.ELABORADO);
/* 339:341 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 340:    */     
/* 341:343 */     return query.executeUpdate();
/* 342:    */   }
/* 343:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.amortizacion.AmortizacionDao
 * JD-Core Version:    0.7.0.1
 */