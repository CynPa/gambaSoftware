/*   1:    */ package com.asinfo.as2.dao.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  14:    */ import java.util.ArrayList;
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
/*  26:    */ import javax.persistence.criteria.Path;
/*  27:    */ import javax.persistence.criteria.Predicate;
/*  28:    */ import javax.persistence.criteria.Root;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class PlanMantenimientoDao
/*  32:    */   extends AbstractDaoAS2<PlanMantenimiento>
/*  33:    */ {
/*  34:    */   public PlanMantenimientoDao()
/*  35:    */   {
/*  36: 41 */     super(PlanMantenimiento.class);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PlanMantenimiento cargarDetalle(PlanMantenimiento planMantenimiento)
/*  40:    */   {
/*  41: 45 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  42: 46 */     CriteriaQuery<PlanMantenimiento> criteriaQuery = cb.createQuery(PlanMantenimiento.class);
/*  43: 47 */     Root<PlanMantenimiento> from = criteriaQuery.from(PlanMantenimiento.class);
/*  44: 48 */     from.fetch("categoriaEquipo", JoinType.LEFT);
/*  45: 49 */     from.fetch("subcategoriaEquipo", JoinType.LEFT);
/*  46: 50 */     criteriaQuery.where(cb.equal(from.get("idPlanMantenimiento"), Integer.valueOf(planMantenimiento.getId())));
/*  47: 51 */     CriteriaQuery<PlanMantenimiento> select = criteriaQuery.select(from);
/*  48: 52 */     PlanMantenimiento tmpPlanMantenimiento = (PlanMantenimiento)this.em.createQuery(select).getSingleResult();
/*  49:    */     
/*  50: 54 */     CriteriaQuery<DetallePlanMantenimiento> cqDetalle = cb.createQuery(DetallePlanMantenimiento.class);
/*  51: 55 */     Root<DetallePlanMantenimiento> fromDE = cqDetalle.from(DetallePlanMantenimiento.class);
/*  52: 56 */     fromDE.fetch("componente", JoinType.LEFT);
/*  53: 57 */     fromDE.fetch("actividad", JoinType.LEFT);
/*  54: 58 */     cqDetalle.where(cb.equal(fromDE.join("planMantenimiento"), tmpPlanMantenimiento));
/*  55: 59 */     CriteriaQuery<DetallePlanMantenimiento> selectDR = cqDetalle.select(fromDE);
/*  56: 60 */     List<DetallePlanMantenimiento> listaDetallePlanMantenimiento = this.em.createQuery(selectDR).getResultList();
/*  57: 62 */     for (DetallePlanMantenimiento dr : listaDetallePlanMantenimiento)
/*  58:    */     {
/*  59: 63 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> cqRF = cb.createQuery(DetallePlanMantenimientoFrecuencia.class);
/*  60: 64 */       Root<DetallePlanMantenimientoFrecuencia> fromRF = cqRF.from(DetallePlanMantenimientoFrecuencia.class);
/*  61: 65 */       fromRF.fetch("frecuencia", JoinType.LEFT);
/*  62: 66 */       Predicate conjunction = cb.conjunction();
/*  63: 67 */       conjunction.getExpressions().add(cb.equal(fromRF.join("detallePlanMantenimiento"), dr));
/*  64: 68 */       cqRF.where(conjunction);
/*  65: 69 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> selectRF = cqRF.select(fromRF);
/*  66: 70 */       List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia = this.em.createQuery(selectRF).getResultList();
/*  67: 71 */       dr.setListaDetallePlanMantenimientoFrecuencia(listaDetallePlanMantenimientoFrecuencia);
/*  68:    */     }
/*  69: 74 */     Object cqEquipo = cb.createQuery(PlanMantenimientoEquipo.class);
/*  70: 75 */     Root<PlanMantenimientoEquipo> fromEquipo = ((CriteriaQuery)cqEquipo).from(PlanMantenimientoEquipo.class);
/*  71: 76 */     Fetch<Object, Object> equipo = fromEquipo.fetch("equipo", JoinType.INNER);
/*  72: 77 */     equipo.fetch("subcategoriaEquipo", JoinType.INNER);
/*  73: 78 */     equipo.fetch("ubicacion", JoinType.INNER);
/*  74: 79 */     ((CriteriaQuery)cqEquipo).where(cb.equal(fromEquipo.join("planMantenimiento"), tmpPlanMantenimiento));
/*  75: 80 */     CriteriaQuery<PlanMantenimientoEquipo> selectEquipo = ((CriteriaQuery)cqEquipo).select(fromEquipo);
/*  76: 81 */     List<PlanMantenimientoEquipo> listaPlanMantenimientoEquipo = this.em.createQuery(selectEquipo).getResultList();
/*  77:    */     
/*  78: 83 */     this.em.detach(tmpPlanMantenimiento);
/*  79: 84 */     tmpPlanMantenimiento.setListaDetallePlanMantenimiento(listaDetallePlanMantenimiento);
/*  80: 85 */     tmpPlanMantenimiento.setListaPlanMantenimientoEquipo(listaPlanMantenimientoEquipo);
/*  81:    */     
/*  82: 87 */     return tmpPlanMantenimiento;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<DetallePlanMantenimientoFrecuencia> cargarDetallaPlan(ComponenteEquipo componenteEquipo, Equipo equipo)
/*  86:    */   {
/*  87: 91 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  88: 92 */     CriteriaQuery<ComponenteEquipo> criteriaQuery = cb.createQuery(ComponenteEquipo.class);
/*  89: 93 */     Root<ComponenteEquipo> fromCE = criteriaQuery.from(ComponenteEquipo.class);
/*  90: 94 */     criteriaQuery.where(cb.equal(fromCE.get("idComponenteEquipo"), Integer.valueOf(componenteEquipo.getIdComponenteEquipo())));
/*  91: 95 */     CriteriaQuery<ComponenteEquipo> select = criteriaQuery.select(fromCE);
/*  92: 96 */     ComponenteEquipo tmpComponenteEquipo = (ComponenteEquipo)this.em.createQuery(select).getSingleResult();
/*  93:    */     
/*  94: 98 */     CriteriaQuery<PlanMantenimientoEquipo> cqPlanMantenimiento = cb.createQuery(PlanMantenimientoEquipo.class);
/*  95: 99 */     Root<PlanMantenimientoEquipo> fromPlanMantenimiento = cqPlanMantenimiento.from(PlanMantenimientoEquipo.class);
/*  96:100 */     fromPlanMantenimiento.fetch("equipo", JoinType.LEFT);
/*  97:101 */     cqPlanMantenimiento.where(cb.equal(fromPlanMantenimiento.join("equipo"), equipo));
/*  98:102 */     CriteriaQuery<PlanMantenimientoEquipo> selectPlanMantenimiento = cqPlanMantenimiento.select(fromPlanMantenimiento);
/*  99:103 */     List<PlanMantenimientoEquipo> listaPlanMantenimiento = this.em.createQuery(selectPlanMantenimiento).getResultList();
/* 100:104 */     List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia = new ArrayList();
/* 101:105 */     for (PlanMantenimientoEquipo planMantenimiento : listaPlanMantenimiento)
/* 102:    */     {
/* 103:107 */       CriteriaQuery<DetallePlanMantenimiento> cqDetallePM = cb.createQuery(DetallePlanMantenimiento.class);
/* 104:108 */       Root<DetallePlanMantenimiento> fromDetallePM = cqDetallePM.from(DetallePlanMantenimiento.class);
/* 105:109 */       Predicate conjunctionDPM = cb.conjunction();
/* 106:110 */       conjunctionDPM.getExpressions().add(cb.equal(fromDetallePM.get("componente"), tmpComponenteEquipo));
/* 107:111 */       conjunctionDPM.getExpressions().add(cb.equal(fromDetallePM.get("planMantenimiento"), planMantenimiento.getPlanMantenimiento()));
/* 108:112 */       cqDetallePM.where(conjunctionDPM);
/* 109:113 */       CriteriaQuery<DetallePlanMantenimiento> selectDetallePM = cqDetallePM.select(fromDetallePM);
/* 110:114 */       List<DetallePlanMantenimiento> listaDetallePlanMantenimiento = this.em.createQuery(selectDetallePM).getResultList();
/* 111:116 */       for (DetallePlanMantenimiento dPM : listaDetallePlanMantenimiento)
/* 112:    */       {
/* 113:117 */         CriteriaQuery<DetallePlanMantenimientoFrecuencia> cqDetallePMF = cb.createQuery(DetallePlanMantenimientoFrecuencia.class);
/* 114:118 */         Root<DetallePlanMantenimientoFrecuencia> fromDetallPMF = cqDetallePMF.from(DetallePlanMantenimientoFrecuencia.class);
/* 115:119 */         fromDetallPMF.fetch("frecuencia", JoinType.LEFT);
/* 116:120 */         Predicate conjunction = cb.conjunction();
/* 117:121 */         conjunction.getExpressions().add(cb.equal(fromDetallPMF.join("detallePlanMantenimiento"), dPM));
/* 118:122 */         conjunction.getExpressions().add(cb.equal(fromDetallPMF.get("frecuencia").get("tipoFrecuenciaEnum"), Integer.valueOf(1)));
/* 119:123 */         cqDetallePMF.where(conjunction);
/* 120:124 */         CriteriaQuery<DetallePlanMantenimientoFrecuencia> selectRF = cqDetallePMF.select(fromDetallPMF);
/* 121:125 */         listaDetallePlanMantenimientoFrecuencia = this.em.createQuery(selectRF).getResultList();
/* 122:    */       }
/* 123:    */     }
/* 124:128 */     this.em.detach(componenteEquipo);
/* 125:129 */     return listaDetallePlanMantenimientoFrecuencia;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public List<PlanMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 129:    */   {
/* 130:135 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 131:136 */     CriteriaQuery<PlanMantenimiento> criteriaQuery = criteriaBuilder.createQuery(PlanMantenimiento.class);
/* 132:137 */     Root<PlanMantenimiento> from = criteriaQuery.from(PlanMantenimiento.class);
/* 133:138 */     from.fetch("subcategoriaEquipo", JoinType.LEFT);
/* 134:139 */     from.fetch("categoriaEquipo", JoinType.LEFT);
/* 135:    */     
/* 136:141 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 137:142 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 138:    */     
/* 139:144 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 140:    */     
/* 141:146 */     CriteriaQuery<PlanMantenimiento> select = criteriaQuery.select(from);
/* 142:    */     
/* 143:148 */     TypedQuery<PlanMantenimiento> typedQuery = this.em.createQuery(select);
/* 144:149 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 145:    */     
/* 146:151 */     return typedQuery.getResultList();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<CalendarioMantenimientoEntidad> obtenerDetallesPlanMantenimiento(int idOrganizacion)
/* 150:    */   {
/* 151:156 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 152:    */     
/* 153:158 */     StringBuilder sql = new StringBuilder();
/* 154:159 */     sql.append(" SELECT dpm, eq ");
/* 155:160 */     sql.append(" FROM DetallePlanMantenimiento dpm, PlanMantenimientoEquipo pme ");
/* 156:161 */     sql.append(" INNER JOIN FETCH dpm.planMantenimiento pm ");
/* 157:162 */     sql.append(" INNER JOIN FETCH dpm.componente ce ");
/* 158:163 */     sql.append(" INNER JOIN FETCH dpm.actividad am ");
/* 159:164 */     sql.append(" INNER JOIN FETCH am.tipoActividad tam ");
/* 160:165 */     sql.append(" INNER JOIN pme.planMantenimiento pm2 ");
/* 161:166 */     sql.append(" INNER JOIN pme.equipo eq ");
/* 162:167 */     sql.append(" INNER JOIN FETCH eq.subcategoriaEquipo sce ");
/* 163:168 */     sql.append(" INNER JOIN FETCH sce.categoriaEquipo ceq ");
/* 164:169 */     sql.append(" INNER JOIN FETCH eq.ubicacion ubeq ");
/* 165:170 */     sql.append(" WHERE pm.idOrganizacion = :idOrganizacion");
/* 166:171 */     sql.append(" AND pm2.idPlanMantenimiento = pm.idPlanMantenimiento ");
/* 167:    */     
/* 168:173 */     Query query = this.em.createQuery(sql.toString());
/* 169:174 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 170:175 */     List<Object[]> lista = query.getResultList();
/* 171:176 */     List<CalendarioMantenimientoEntidad> listaCalendario = new ArrayList();
/* 172:177 */     for (Object[] objects : lista)
/* 173:    */     {
/* 174:178 */       DetallePlanMantenimiento detalle = (DetallePlanMantenimiento)objects[0];
/* 175:179 */       Equipo equipoDetalle = (Equipo)objects[1];
/* 176:    */       
/* 177:181 */       CalendarioMantenimientoEntidad calendario = new CalendarioMantenimientoEntidad();
/* 178:182 */       calendario.setIdOrganizacion(idOrganizacion);
/* 179:183 */       calendario.setDetallePlanMantenimiento(detalle);
/* 180:184 */       calendario.setEquipo(equipoDetalle);
/* 181:185 */       listaCalendario.add(calendario);
/* 182:    */       
/* 183:    */ 
/* 184:188 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> cqRF = cb.createQuery(DetallePlanMantenimientoFrecuencia.class);
/* 185:189 */       Root<DetallePlanMantenimientoFrecuencia> fromRF = cqRF.from(DetallePlanMantenimientoFrecuencia.class);
/* 186:190 */       fromRF.fetch("frecuencia", JoinType.LEFT);
/* 187:191 */       Predicate conjunction = cb.conjunction();
/* 188:192 */       conjunction.getExpressions().add(cb.equal(fromRF.join("detallePlanMantenimiento"), detalle));
/* 189:193 */       cqRF.where(conjunction);
/* 190:194 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> selectRF = cqRF.select(fromRF);
/* 191:195 */       List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia = this.em.createQuery(selectRF).getResultList();
/* 192:196 */       detalle.setListaDetallePlanMantenimientoFrecuencia(listaDetallePlanMantenimientoFrecuencia);
/* 193:    */     }
/* 194:199 */     return listaCalendario;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Equipo cargarEquipoAsignadoPlanMantenimiento(Equipo equipo)
/* 198:    */   {
/* 199:203 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 200:204 */     CriteriaQuery<Equipo> criteriaQuery = cb.createQuery(Equipo.class);
/* 201:205 */     Root<Equipo> fromEquipo = criteriaQuery.from(Equipo.class);
/* 202:206 */     criteriaQuery.where(cb.equal(fromEquipo.get("idEquipo"), Integer.valueOf(equipo.getIdEquipo())));
/* 203:207 */     CriteriaQuery<Equipo> select = criteriaQuery.select(fromEquipo);
/* 204:208 */     Equipo tmpEquipo = (Equipo)this.em.createQuery(select).getSingleResult();
/* 205:    */     
/* 206:210 */     CriteriaQuery<PlanMantenimientoEquipo> cqPlanMantenimiento = cb.createQuery(PlanMantenimientoEquipo.class);
/* 207:211 */     Root<PlanMantenimientoEquipo> fromPlanMantenimiento = cqPlanMantenimiento.from(PlanMantenimientoEquipo.class);
/* 208:212 */     fromPlanMantenimiento.fetch("equipo", JoinType.LEFT);
/* 209:213 */     cqPlanMantenimiento.where(cb.equal(fromPlanMantenimiento.join("equipo"), tmpEquipo));
/* 210:214 */     CriteriaQuery<PlanMantenimientoEquipo> selectDetallePM = cqPlanMantenimiento.select(fromPlanMantenimiento).distinct(true);
/* 211:215 */     this.em.createQuery(selectDetallePM).setMaxResults(1);
/* 212:216 */     List<PlanMantenimientoEquipo> planMantenimientoEquipo = this.em.createQuery(selectDetallePM).getResultList();
/* 213:217 */     PlanMantenimientoEquipo tmpPlanMantenimientoEquipo = new PlanMantenimientoEquipo();
/* 214:218 */     List<DetalleComponenteEquipo> listaDetalleComponenteEquipo = new ArrayList();
/* 215:219 */     if (!planMantenimientoEquipo.isEmpty())
/* 216:    */     {
/* 217:220 */       tmpPlanMantenimientoEquipo = (PlanMantenimientoEquipo)planMantenimientoEquipo.get(0);
/* 218:221 */       CriteriaQuery<DetalleComponenteEquipo> cqDetalleComponenteEquipo = cb.createQuery(DetalleComponenteEquipo.class);
/* 219:222 */       Root<DetalleComponenteEquipo> fromDetalleComponenteEquipo = cqDetalleComponenteEquipo.from(DetalleComponenteEquipo.class);
/* 220:223 */       fromDetalleComponenteEquipo.fetch("componenteEquipo", JoinType.LEFT);
/* 221:224 */       cqDetalleComponenteEquipo.where(cb.equal(fromDetalleComponenteEquipo.join("equipo"), tmpPlanMantenimientoEquipo.getEquipo()));
/* 222:225 */       CriteriaQuery<DetalleComponenteEquipo> selectDCE = cqDetalleComponenteEquipo.select(fromDetalleComponenteEquipo);
/* 223:226 */       listaDetalleComponenteEquipo = this.em.createQuery(selectDCE).getResultList();
/* 224:    */     }
/* 225:228 */     this.em.detach(tmpEquipo);
/* 226:229 */     tmpEquipo.setListaComponenteEquipo(listaDetalleComponenteEquipo);
/* 227:230 */     return tmpEquipo;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<DetallePlanMantenimientoFrecuencia> obtenerDetallePlanMantenimientoFrecuencia(Equipo equipo, ComponenteEquipo componenteEquipo, ActividadMantenimiento actividadMantenimiento, TipoFrecuenciaEnum tipoFrecuencia)
/* 231:    */   {
/* 232:237 */     StringBuilder sql = new StringBuilder();
/* 233:238 */     sql.append(" SELECT dpmf ");
/* 234:239 */     sql.append(" FROM DetallePlanMantenimientoFrecuencia dpmf, PlanMantenimientoEquipo pme ");
/* 235:240 */     sql.append(" INNER JOIN FETCH dpmf.frecuencia frec ");
/* 236:241 */     sql.append(" INNER JOIN FETCH dpmf.detallePlanMantenimiento dpm ");
/* 237:242 */     sql.append(" INNER JOIN FETCH dpm.planMantenimiento pm ");
/* 238:243 */     sql.append(" INNER JOIN FETCH dpm.componente ce ");
/* 239:244 */     sql.append(" INNER JOIN FETCH dpm.actividad am ");
/* 240:245 */     sql.append(" INNER JOIN FETCH am.tipoActividad tam ");
/* 241:246 */     sql.append(" INNER JOIN pme.planMantenimiento pm2 ");
/* 242:247 */     sql.append(" INNER JOIN pme.equipo eq ");
/* 243:248 */     sql.append(" WHERE eq.idEquipo = :idEquipo ");
/* 244:249 */     sql.append(" AND pm2.idPlanMantenimiento = pm.idPlanMantenimiento ");
/* 245:250 */     if (componenteEquipo != null) {
/* 246:251 */       sql.append(" AND ce.idComponenteEquipo = :idComponenteEquipo ");
/* 247:    */     }
/* 248:253 */     if (actividadMantenimiento != null) {
/* 249:254 */       sql.append(" AND am.idActividadMantenimiento = :idActividadMantenimiento ");
/* 250:    */     }
/* 251:256 */     if (tipoFrecuencia != null) {
/* 252:257 */       sql.append(" AND frec.tipoFrecuenciaEnum = :tipoFrecuencia ");
/* 253:    */     }
/* 254:260 */     Query query = this.em.createQuery(sql.toString());
/* 255:261 */     query.setParameter("idEquipo", Integer.valueOf(equipo.getId()));
/* 256:262 */     if (componenteEquipo != null) {
/* 257:263 */       query.setParameter("idComponenteEquipo", Integer.valueOf(componenteEquipo.getId()));
/* 258:    */     }
/* 259:265 */     if (actividadMantenimiento != null) {
/* 260:266 */       query.setParameter("idActividadMantenimiento", Integer.valueOf(actividadMantenimiento.getId()));
/* 261:    */     }
/* 262:268 */     if (tipoFrecuencia != null) {
/* 263:269 */       query.setParameter("tipoFrecuencia", tipoFrecuencia);
/* 264:    */     }
/* 265:272 */     return query.getResultList();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List<DetallePlanMantenimiento> obtenerDetallePlanMantenimiento(int idOrganizacion)
/* 269:    */   {
/* 270:278 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 271:    */     
/* 272:280 */     StringBuilder sql = new StringBuilder();
/* 273:281 */     sql.append(" SELECT dpm ");
/* 274:282 */     sql.append(" FROM DetallePlanMantenimiento dpm ");
/* 275:283 */     sql.append(" INNER JOIN FETCH dpm.planMantenimiento pm ");
/* 276:284 */     sql.append(" INNER JOIN FETCH dpm.componente ceq ");
/* 277:285 */     sql.append(" INNER JOIN FETCH dpm.actividad ac ");
/* 278:286 */     sql.append(" WHERE pm.idOrganizacion = :idOrganizacion ");
/* 279:287 */     sql.append(" AND NOT EXISTS (SELECT cm.idCalendarioMantenimiento FROM CalendarioMantenimientoEntidad cm WHERE cm.detallePlanMantenimiento = dpm) ");
/* 280:    */     
/* 281:289 */     Query query = this.em.createQuery(sql.toString());
/* 282:290 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 283:    */     
/* 284:292 */     List<DetallePlanMantenimiento> lista = query.getResultList();
/* 285:294 */     for (DetallePlanMantenimiento detalle : lista)
/* 286:    */     {
/* 287:296 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> cqRF = cb.createQuery(DetallePlanMantenimientoFrecuencia.class);
/* 288:297 */       Root<DetallePlanMantenimientoFrecuencia> fromRF = cqRF.from(DetallePlanMantenimientoFrecuencia.class);
/* 289:298 */       fromRF.fetch("frecuencia", JoinType.LEFT);
/* 290:299 */       Predicate conjunction = cb.conjunction();
/* 291:300 */       conjunction.getExpressions().add(cb.equal(fromRF.join("detallePlanMantenimiento"), detalle));
/* 292:301 */       cqRF.where(conjunction);
/* 293:302 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> selectRF = cqRF.select(fromRF);
/* 294:303 */       List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia = this.em.createQuery(selectRF).getResultList();
/* 295:304 */       detalle.setListaDetallePlanMantenimientoFrecuencia(listaDetallePlanMantenimientoFrecuencia);
/* 296:    */     }
/* 297:306 */     return lista;
/* 298:    */   }
/* 299:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.PlanMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */