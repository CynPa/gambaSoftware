/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   4:    */ import com.asinfo.as2.entities.Asiento;
/*   5:    */ import com.asinfo.as2.entities.Depreciacion;
/*   6:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.HistoricoDepreciacion;
/*   9:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  10:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.Set;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ import javax.persistence.EntityManager;
/*  20:    */ import javax.persistence.NoResultException;
/*  21:    */ import javax.persistence.Query;
/*  22:    */ import javax.persistence.TypedQuery;
/*  23:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  24:    */ import javax.persistence.criteria.CriteriaQuery;
/*  25:    */ import javax.persistence.criteria.Expression;
/*  26:    */ import javax.persistence.criteria.Join;
/*  27:    */ import javax.persistence.criteria.JoinType;
/*  28:    */ import javax.persistence.criteria.Path;
/*  29:    */ import javax.persistence.criteria.Predicate;
/*  30:    */ import javax.persistence.criteria.Root;
/*  31:    */ 
/*  32:    */ @Stateless
/*  33:    */ public class DepreciacionDao
/*  34:    */   extends AbstractDaoAS2<Depreciacion>
/*  35:    */ {
/*  36:    */   public DepreciacionDao()
/*  37:    */   {
/*  38: 50 */     super(Depreciacion.class);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<Depreciacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  42:    */   {
/*  43: 61 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  44: 62 */     CriteriaQuery<Depreciacion> criteriaQuery = criteriaBuilder.createQuery(Depreciacion.class);
/*  45: 63 */     Root<Depreciacion> from = criteriaQuery.from(Depreciacion.class);
/*  46: 64 */     from.fetch("activoFijo", JoinType.LEFT);
/*  47: 65 */     from.fetch("documentoRevalorizacion", JoinType.LEFT);
/*  48:    */     
/*  49: 67 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  50: 68 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  51: 69 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  52:    */     
/*  53: 71 */     CriteriaQuery<Depreciacion> select = criteriaQuery.select(from);
/*  54: 72 */     TypedQuery<Depreciacion> typedQuery = this.em.createQuery(select);
/*  55: 73 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  56:    */     
/*  57: 75 */     return typedQuery.getResultList();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<Depreciacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  61:    */   {
/*  62: 86 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int contarPorCriterio(Map<String, String> filters)
/*  66:    */   {
/*  67: 98 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  68: 99 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  69:    */     
/*  70:101 */     Root<Depreciacion> from = criteriaQuery.from(Depreciacion.class);
/*  71:102 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  72:    */     
/*  73:104 */     List<Predicate> predicates = getPredicates(filters, criteriaBuilder, from);
/*  74:105 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  75:    */     
/*  76:107 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  77:    */   }
/*  78:    */   
/*  79:    */   private List<Predicate> getPredicates(Map<String, String> filters, CriteriaBuilder criteriaBuilder, Root<Depreciacion> from)
/*  80:    */   {
/*  81:123 */     List<Predicate> predicates = new ArrayList();
/*  82:    */     
/*  83:125 */     filters = agregarFiltrosOrganizacion(filters);
/*  84:126 */     filters.put("indicadorDepreciacionFiscal", "");
/*  85:128 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  86:    */     {
/*  87:129 */       String filterProperty = (String)it.next();
/*  88:131 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  89:    */       {
/*  90:132 */         String filterValue = (String)filters.get(filterProperty);
/*  91:134 */         if (filterProperty.equals("idOrganizacion"))
/*  92:    */         {
/*  93:135 */           Expression<Integer> path = from.get(filterProperty);
/*  94:136 */           predicates.add(criteriaBuilder.equal(path, filterValue));
/*  95:    */         }
/*  96:137 */         else if (filterProperty.equals("activoFijo.nombre"))
/*  97:    */         {
/*  98:138 */           Path<String> path = from.join("activoFijo").get("nombre");
/*  99:139 */           predicates.add(criteriaBuilder.like(path, filterValue + "%"));
/* 100:    */         }
/* 101:140 */         else if (filterProperty.equals("fechaInicioDepreciacion"))
/* 102:    */         {
/* 103:141 */           Path<String> path = from.get(filterProperty);
/* 104:142 */           predicates.add(criteriaBuilder.like(path, filterValue + "%"));
/* 105:    */         }
/* 106:143 */         else if (filterProperty.equals("indicadorDepreciacionFiscal"))
/* 107:    */         {
/* 108:144 */           Path<Boolean> path = from.get(filterProperty);
/* 109:145 */           predicates.add(criteriaBuilder.equal(path, Boolean.valueOf(false)));
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:149 */     return predicates;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Depreciacion cargarDetalle(int idDepreciacion)
/* 117:    */   {
/* 118:159 */     Depreciacion depreciacion = (Depreciacion)buscarPorId(Integer.valueOf(idDepreciacion));
/* 119:160 */     depreciacion.getActivoFijo().getId();
/* 120:161 */     depreciacion.getListaDetalleDepreciacion().size();
/* 121:162 */     if (depreciacion.getDepreciacionPadre() != null)
/* 122:    */     {
/* 123:163 */       depreciacion.getDepreciacionPadre().getId();
/* 124:164 */       depreciacion.getDepreciacionPadre().getListaDetalleDepreciacion().size();
/* 125:    */     }
/* 126:166 */     if (depreciacion.getDocumentoRevalorizacion() != null)
/* 127:    */     {
/* 128:167 */       depreciacion.getDocumentoRevalorizacion().getId();
/* 129:168 */       depreciacion.getDocumentoRevalorizacion().getTipoAsiento().getId();
/* 130:    */     }
/* 131:170 */     if (depreciacion.getAsientoRevalorizacion() != null) {
/* 132:171 */       depreciacion.getAsientoRevalorizacion().getId();
/* 133:    */     }
/* 134:173 */     return depreciacion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Depreciacion obtenerDepreciacionActivo(int idActivoFijo, boolean indicadorDepreciacionFiscal)
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:186 */       StringBuilder sql = new StringBuilder();
/* 142:187 */       sql.append(" SELECT d FROM Depreciacion d ");
/* 143:188 */       sql.append(" INNER JOIN d.activoFijo af ");
/* 144:189 */       sql.append(" WHERE d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal ");
/* 145:190 */       sql.append(" AND   d.activo = true");
/* 146:191 */       sql.append(" AND   af.idActivoFijo = :idActivoFijo ");
/* 147:    */       
/* 148:193 */       Query query = this.em.createQuery(sql.toString());
/* 149:194 */       query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 150:195 */       query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(indicadorDepreciacionFiscal));
/* 151:196 */       query.setMaxResults(1);
/* 152:197 */       return (Depreciacion)query.getSingleResult();
/* 153:    */     }
/* 154:    */     catch (NoResultException e) {}
/* 155:200 */     return null;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<DetalleDepreciacion> buscarDetalleDepreciacionDepreciados(int idDepreciacion)
/* 159:    */   {
/* 160:213 */     String sql = " SELECT dd FROM DetalleDepreciacion dd  INNER JOIN dd.depreciacion d  WHERE dd.historicoDepreciacion IS NOT NULL  AND d.idDepreciacion = :idDepreciacion ";
/* 161:    */     
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:218 */     Query query = this.em.createQuery(sql);
/* 166:219 */     query.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 167:    */     
/* 168:221 */     return query.getResultList();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Depreciacion obtenerDepreciacionAnterior(int idActivoFijo)
/* 172:    */   {
/* 173:233 */     Depreciacion depreciacion = null;
/* 174:234 */     String sql1 = " SELECT COUNT(d.idDepreciacion) FROM Depreciacion d  INNER JOIN d.activoFijo af  WHERE af.idActivoFijo =:idActivoFijo  AND d.activo =:activo  AND d.indicadorDepreciacionFiscal =:indicadorDepreciacionFiscal ";
/* 175:    */     
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:240 */     Query query1 = this.em.createQuery(sql1);
/* 181:241 */     query1.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 182:242 */     query1.setParameter("activo", Boolean.valueOf(true));
/* 183:243 */     query1.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(false));
/* 184:244 */     Long numeroRegistros = (Long)query1.getSingleResult();
/* 185:246 */     if (numeroRegistros.longValue() > 0L)
/* 186:    */     {
/* 187:248 */       String sql = " SELECT d FROM Depreciacion d  INNER JOIN d.activoFijo af  WHERE af.idActivoFijo =:idActivoFijo  AND d.activo =:activo  AND d.indicadorDepreciacionFiscal =:indicadorDepreciacionFiscal ";
/* 188:    */       
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:254 */       Query query = this.em.createQuery(sql);
/* 194:255 */       query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 195:256 */       query.setParameter("activo", Boolean.valueOf(true));
/* 196:257 */       query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(false));
/* 197:    */       
/* 198:259 */       depreciacion = (Depreciacion)query.getSingleResult();
/* 199:260 */       depreciacion.getListaDetalleDepreciacion().size();
/* 200:    */     }
/* 201:263 */     return depreciacion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public DetalleDepreciacion obtenerUltimoDetalleDepreciacionDepreciado(int idDepreciacion)
/* 205:    */   {
/* 206:275 */     DetalleDepreciacion detalleDepreciacion = null;
/* 207:    */     
/* 208:277 */     String sqlAnio = " (SELECT max(sadd.anio) FROM DetalleDepreciacion sadd  INNER JOIN sadd.depreciacion sad  WHERE sad.idDepreciacion =:idDepreciacion  AND sad.activo =:activo  AND sadd.historicoDepreciacion IS NOT NULL  )";
/* 209:    */     
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:284 */     String sqlMes = "  (SELECT max(smdd.mes) FROM DetalleDepreciacion smdd  INNER JOIN smdd.depreciacion smd  WHERE smd.idDepreciacion =:idDepreciacion  AND smd.activo =:activo  AND smdd.historicoDepreciacion IS NOT NULL  AND smdd.anio = " + sqlAnio + "" + ")";
/* 216:    */     
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:292 */     String sqlNumeroRegistros = " SELECT COUNT(dd.idDetalleDepreciacion) FROM DetalleDepreciacion dd  INNER JOIN dd.depreciacion d  WHERE d.idDepreciacion =:idDepreciacion  AND d.activo =:activo  AND dd.historicoDepreciacion IS NOT NULL  AND dd.anio = " + sqlAnio + "" + " AND dd.mes = " + sqlMes + "";
/* 224:    */     
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:300 */     Query queryNumeroRegistros = this.em.createQuery(sqlNumeroRegistros);
/* 232:301 */     queryNumeroRegistros.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 233:302 */     queryNumeroRegistros.setParameter("activo", Boolean.valueOf(true));
/* 234:303 */     Long numeroRegsitros = (Long)queryNumeroRegistros.getSingleResult();
/* 235:305 */     if (numeroRegsitros.longValue() > 0L)
/* 236:    */     {
/* 237:306 */       String sql = " SELECT dd FROM DetalleDepreciacion dd  INNER JOIN dd.depreciacion d  WHERE d.idDepreciacion =:idDepreciacion  AND d.activo =:activo  AND dd.historicoDepreciacion IS NOT NULL  AND dd.anio = " + sqlAnio + "" + " AND dd.mes = " + sqlMes + "";
/* 238:    */       
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:313 */       Query query = this.em.createQuery(sql);
/* 245:314 */       query.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 246:315 */       query.setParameter("activo", Boolean.valueOf(true));
/* 247:316 */       detalleDepreciacion = (DetalleDepreciacion)query.getSingleResult();
/* 248:317 */       detalleDepreciacion.getHistoricoDepreciacion().getId();
/* 249:    */     }
/* 250:321 */     return detalleDepreciacion;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void actualizaDetalleDepreciacionNoDepreciados(int idDepreciacion, int anio, int mes)
/* 254:    */   {
/* 255:334 */     String sql1 = " UPDATE DetalleDepreciacion dd SET dd.activo = false  WHERE dd.historicoDepreciacion IS NULL  AND dd.depreciacion in  (SELECT d FROM Depreciacion d  WHERE d.idDepreciacion = :idDepreciacion )  AND :mes BETWEEN :mes and :ultimoMes  AND dd.anio = :anio ";
/* 256:    */     
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:342 */     Query query1 = this.em.createQuery(sql1);
/* 264:343 */     query1.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 265:344 */     query1.setParameter("mes", Integer.valueOf(mes));
/* 266:345 */     query1.setParameter("anio", Integer.valueOf(anio));
/* 267:346 */     query1.setParameter("ultimoMes", Integer.valueOf(Mes.DICIEMBRE.ordinal()));
/* 268:347 */     query1.executeUpdate();
/* 269:    */     
/* 270:349 */     String sql2 = " UPDATE DetalleDepreciacion dd SET dd.activo = false  WHERE dd.historicoDepreciacion IS NULL  AND dd.depreciacion in  (SELECT d FROM Depreciacion d  WHERE d.idDepreciacion = :idDepreciacion)  AND dd.anio > :anio ";
/* 271:    */     
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:356 */     Query query2 = this.em.createQuery(sql2);
/* 278:357 */     query2.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 279:358 */     query2.setParameter("anio", Integer.valueOf(anio));
/* 280:359 */     query2.executeUpdate();
/* 281:    */     
/* 282:361 */     String sql3 = " UPDATE DetalleDepreciacion dd SET dd.activo = true  WHERE dd.historicoDepreciacion IS NULL  AND dd.depreciacion in  (SELECT d FROM Depreciacion d  WHERE d.idDepreciacion = :idDepreciacion)  AND dd.mes < :mes AND dd.mes > :mesInicial  AND dd.anio = :anio ";
/* 283:    */     
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:369 */     Query query3 = this.em.createQuery(sql3);
/* 291:370 */     query3.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 292:371 */     query3.setParameter("mes", Integer.valueOf(mes));
/* 293:372 */     query3.setParameter("anio", Integer.valueOf(anio));
/* 294:373 */     query3.setParameter("mesInicial", Integer.valueOf(Mes.ENERO.ordinal()));
/* 295:374 */     query3.executeUpdate();
/* 296:    */     
/* 297:376 */     String sql4 = " UPDATE DetalleDepreciacion dd SET dd.activo = true  WHERE dd.historicoDepreciacion IS NULL  AND dd.depreciacion in  (SELECT d FROM Depreciacion d  WHERE d.idDepreciacion = :idDepreciacion)  AND dd.anio < :anio ";
/* 298:    */     
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:383 */     Query query4 = this.em.createQuery(sql4);
/* 305:384 */     query4.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 306:385 */     query4.setParameter("anio", Integer.valueOf(anio));
/* 307:386 */     query4.executeUpdate();
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void eliminaDetalleDepreciacio(int idDepreciacion)
/* 311:    */   {
/* 312:397 */     String sql = " DELETE FROM DetalleDepreciacion dd  WHERE dd.depreciacion in  (SELECT d FROM Depreciacion d  WHERE d.idDepreciacion = :idDepreciacion)";
/* 313:    */     
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:402 */     Query query = this.em.createQuery(sql);
/* 318:403 */     query.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 319:404 */     query.executeUpdate();
/* 320:    */   }
/* 321:    */   
/* 322:    */   public BigDecimal getValorActivoCorteFecha(int idDepreciacion, Date fecha)
/* 323:    */   {
/* 324:415 */     String sql = " SELECT d.valorActivo  -\t( \t\tSELECT SUM(dd.valor) FROM DetalleDepreciacion dd\t\t\tWHERE dd.depreciacion = d AND dd.fecha <= :fecha\t\t)  FROM Depreciacion d WHERE d.idDepreciacion = :idDepreciacion";
/* 325:    */     
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:422 */     Query query = this.em.createQuery(sql);
/* 332:423 */     query.setParameter("idDepreciacion", Integer.valueOf(idDepreciacion));
/* 333:424 */     query.setParameter("fecha", fecha);
/* 334:425 */     return (BigDecimal)query.getSingleResult();
/* 335:    */   }
/* 336:    */   
/* 337:    */   public List<DetalleDepreciacion> buscarPorActivo(int idActivoFijo, boolean indicadorDepreciacionFiscal)
/* 338:    */   {
/* 339:430 */     String sql = " SELECT dd FROM DetalleDepreciacion dd join dd.depreciacion d WHERE d.activoFijo.idActivoFijo = :idActivoFijo AND d.activo = true  AND d.indicadorDepreciacionFiscal = :indicadorDepreciacionFiscal";
/* 340:    */     
/* 341:    */ 
/* 342:433 */     Query query = this.em.createQuery(sql);
/* 343:434 */     query.setParameter("idActivoFijo", Integer.valueOf(idActivoFijo));
/* 344:435 */     query.setParameter("indicadorDepreciacionFiscal", Boolean.valueOf(indicadorDepreciacionFiscal));
/* 345:436 */     return query.getResultList();
/* 346:    */   }
/* 347:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DepreciacionDao
 * JD-Core Version:    0.7.0.1
 */