/*   1:    */ package com.asinfo.as2.dao.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.TipoActividad;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashSet;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import java.util.Set;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ import javax.persistence.EntityManager;
/*  26:    */ import javax.persistence.Query;
/*  27:    */ import javax.persistence.TemporalType;
/*  28:    */ import javax.persistence.TypedQuery;
/*  29:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  30:    */ import javax.persistence.criteria.CriteriaQuery;
/*  31:    */ import javax.persistence.criteria.Expression;
/*  32:    */ import javax.persistence.criteria.Fetch;
/*  33:    */ import javax.persistence.criteria.JoinType;
/*  34:    */ import javax.persistence.criteria.Predicate;
/*  35:    */ import javax.persistence.criteria.Root;
/*  36:    */ 
/*  37:    */ @Stateless
/*  38:    */ public class CalendarioMantenimientoDao
/*  39:    */   extends AbstractDaoAS2<CalendarioMantenimientoEntidad>
/*  40:    */ {
/*  41:    */   public CalendarioMantenimientoDao()
/*  42:    */   {
/*  43: 42 */     super(CalendarioMantenimientoEntidad.class);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<CalendarioMantenimientoEntidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  47:    */   {
/*  48: 47 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  49: 48 */     CriteriaQuery<CalendarioMantenimientoEntidad> criteriaQuery = cb.createQuery(CalendarioMantenimientoEntidad.class);
/*  50: 49 */     Root<CalendarioMantenimientoEntidad> from = criteriaQuery.from(CalendarioMantenimientoEntidad.class);
/*  51: 50 */     from.fetch("equipo", JoinType.INNER);
/*  52: 51 */     Fetch<Object, Object> lectura = from.fetch("lecturaMantenimiento", JoinType.LEFT);
/*  53: 52 */     lectura.fetch("frecuencia", JoinType.LEFT);
/*  54: 53 */     Fetch<Object, Object> detalleOT = from.fetch("detalleOrdenTrabajoMantenimiento", JoinType.LEFT);
/*  55: 54 */     detalleOT.fetch("ordenTrabajoMantenimiento", JoinType.LEFT);
/*  56: 55 */     Fetch<Object, Object> detallePlan = from.fetch("detallePlanMantenimiento", JoinType.INNER);
/*  57: 56 */     detallePlan.fetch("componente", JoinType.INNER);
/*  58: 57 */     detallePlan.fetch("actividad", JoinType.INNER);
/*  59: 58 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, cb, from);
/*  60: 59 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  61:    */     
/*  62: 61 */     agregarOrdenamiento(sortField, sortOrder, cb, criteriaQuery, from);
/*  63:    */     
/*  64: 63 */     CriteriaQuery<CalendarioMantenimientoEntidad> select = criteriaQuery.select(from);
/*  65:    */     
/*  66: 65 */     TypedQuery<CalendarioMantenimientoEntidad> typedQuery = this.em.createQuery(select);
/*  67: 66 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  68:    */     
/*  69: 68 */     return typedQuery.getResultList();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void desvincularDetalleOrdenTrabajoMantenimiento(DetalleOrdenTrabajoMantenimiento detalle)
/*  73:    */   {
/*  74: 72 */     StringBuilder sql = new StringBuilder();
/*  75: 73 */     sql.append(" UPDATE CalendarioMantenimientoEntidad cme ");
/*  76: 74 */     sql.append(" SET cme.detalleOrdenTrabajoMantenimiento = null ");
/*  77: 75 */     sql.append(" WHERE cme.detalleOrdenTrabajoMantenimiento.idDetalleOrdenTrabajoMantenimiento = :idDetalleOrdenTrabajoMantenimiento");
/*  78:    */     
/*  79: 77 */     Query query = this.em.createQuery(sql.toString());
/*  80: 78 */     query.setParameter("idDetalleOrdenTrabajoMantenimiento", Integer.valueOf(detalle.getId()));
/*  81: 79 */     query.executeUpdate();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<Object[]> getReporteCalendarioMantenimiento(int idOrganizacion, Date fechaDesde, Date fechaHasta, CategoriaEquipo categoriaEquipo, SubcategoriaEquipo subcategoriaEquipo, Equipo equipo, ComponenteEquipo componenteEquipo, TipoActividad tipoActividad, ActividadMantenimiento actividad, UbicacionActivo ubicacion, boolean indicadorSoloConParo, boolean indicadorReporteParos)
/*  85:    */   {
/*  86: 86 */     StringBuilder sql = new StringBuilder();
/*  87: 87 */     if (!indicadorReporteParos) {
/*  88: 89 */       sql.append(" SELECT eq.nombre, ce.nombre, act.nombre, cm.fecha, cm.fechaModificada, otm.fechaMantenimiento ");
/*  89:    */     } else {
/*  90: 92 */       sql.append(" SELECT eq.nombre, cm.fechaModificada, SUM(dpm.horasParo) ");
/*  91:    */     }
/*  92: 94 */     sql.append(" FROM CalendarioMantenimientoEntidad cm ");
/*  93: 95 */     sql.append(" INNER JOIN cm.equipo eq ");
/*  94: 96 */     sql.append(" INNER JOIN eq.ubicacion ub ");
/*  95: 97 */     sql.append(" INNER JOIN eq.subcategoriaEquipo sce ");
/*  96: 98 */     sql.append(" INNER JOIN sce.categoriaEquipo cate ");
/*  97: 99 */     sql.append(" INNER JOIN cm.detallePlanMantenimiento dpm ");
/*  98:100 */     sql.append(" INNER JOIN dpm.componente ce ");
/*  99:101 */     sql.append(" INNER JOIN dpm.actividad act ");
/* 100:102 */     sql.append(" INNER JOIN act.tipoActividad tact ");
/* 101:103 */     sql.append(" LEFT JOIN cm.detalleOrdenTrabajoMantenimiento dotm ");
/* 102:104 */     sql.append(" LEFT JOIN dotm.ordenTrabajoMantenimiento otm ");
/* 103:105 */     sql.append(" WHERE cm.idOrganizacion = :idOrganizacion ");
/* 104:106 */     sql.append(" AND ( cm.fechaModificada BETWEEN :fechaDesde AND :fechaHasta ");
/* 105:107 */     if (!indicadorReporteParos) {
/* 106:108 */       sql.append(" OR cm.fecha BETWEEN :fechaDesde AND :fechaHasta) ");
/* 107:    */     } else {
/* 108:110 */       sql.append(" ) ");
/* 109:    */     }
/* 110:112 */     if (categoriaEquipo != null) {
/* 111:113 */       sql.append(" AND cate.idCategoriaEquipo = :idCategoriaEquipo ");
/* 112:    */     }
/* 113:115 */     if (subcategoriaEquipo != null) {
/* 114:116 */       sql.append(" AND sce.idSubcategoriaEquipo = :idSubcategoriaEquipo ");
/* 115:    */     }
/* 116:118 */     if (equipo != null) {
/* 117:119 */       sql.append(" AND eq.idEquipo = :idEquipo ");
/* 118:    */     }
/* 119:121 */     if (componenteEquipo != null) {
/* 120:122 */       sql.append(" AND ce.idComponenteEquipo = :idComponenteEquipo ");
/* 121:    */     }
/* 122:124 */     if (tipoActividad != null) {
/* 123:125 */       sql.append(" AND tact.idTipoActividad = :idTipoActividad ");
/* 124:    */     }
/* 125:127 */     if (actividad != null) {
/* 126:128 */       sql.append(" AND act.idActividadMantenimiento = :idActividadMantenimiento ");
/* 127:    */     }
/* 128:130 */     if (ubicacion != null) {
/* 129:131 */       sql.append(" AND ub.idUbicacionActivo = :idUbicacionActivo ");
/* 130:    */     }
/* 131:133 */     if ((indicadorReporteParos) || (indicadorSoloConParo)) {
/* 132:134 */       sql.append(" AND dpm.requiereParo IS TRUE ");
/* 133:    */     }
/* 134:137 */     if (indicadorReporteParos) {
/* 135:138 */       sql.append(" GROUP BY eq.nombre, cm.fechaModificada ");
/* 136:    */     }
/* 137:141 */     sql.append(" ORDER BY cm.fechaModificada ");
/* 138:    */     
/* 139:143 */     Query query = this.em.createQuery(sql.toString());
/* 140:144 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 141:145 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 142:146 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 143:147 */     if (categoriaEquipo != null) {
/* 144:148 */       query.setParameter("idCategoriaEquipo", Integer.valueOf(categoriaEquipo.getId()));
/* 145:    */     }
/* 146:150 */     if (subcategoriaEquipo != null) {
/* 147:151 */       query.setParameter("idSubcategoriaEquipo", Integer.valueOf(subcategoriaEquipo.getId()));
/* 148:    */     }
/* 149:153 */     if (equipo != null) {
/* 150:154 */       query.setParameter("idEquipo", Integer.valueOf(equipo.getId()));
/* 151:    */     }
/* 152:156 */     if (componenteEquipo != null) {
/* 153:157 */       query.setParameter("idComponenteEquipo", Integer.valueOf(componenteEquipo.getId()));
/* 154:    */     }
/* 155:159 */     if (tipoActividad != null) {
/* 156:160 */       query.setParameter("idTipoActividad", Integer.valueOf(tipoActividad.getId()));
/* 157:    */     }
/* 158:162 */     if (actividad != null) {
/* 159:163 */       query.setParameter("idActividadMantenimiento", Integer.valueOf(actividad.getId()));
/* 160:    */     }
/* 161:165 */     if (ubicacion != null) {
/* 162:166 */       query.setParameter("idUbicacionActivo", Integer.valueOf(ubicacion.getId()));
/* 163:    */     }
/* 164:169 */     List<Object[]> lista = query.getResultList();
/* 165:170 */     List<Object[]> resultado = new ArrayList();
/* 166:171 */     Set<Integer> diasAsignados = new HashSet();
/* 167:172 */     for (Object[] objects : lista)
/* 168:    */     {
/* 169:173 */       Date fechaInicialMantenimiento = null;
/* 170:174 */       Date fechaMantenimiento = null;
/* 171:175 */       Date fechaOT = null;
/* 172:    */       
/* 173:177 */       String equipoFila = null;
/* 174:178 */       String componenteFila = null;
/* 175:179 */       String actividadFila = null;
/* 176:    */       
/* 177:181 */       equipoFila = (String)objects[0];
/* 178:183 */       if (!indicadorReporteParos)
/* 179:    */       {
/* 180:184 */         componenteFila = (String)objects[1];
/* 181:185 */         actividadFila = (String)objects[2];
/* 182:186 */         fechaInicialMantenimiento = (Date)objects[3];
/* 183:187 */         fechaMantenimiento = (Date)objects[4];
/* 184:188 */         fechaOT = (Date)objects[5];
/* 185:190 */         if ((fechaOT != null) && (!fechaOT.after(fechaHasta)) && (!fechaOT.before(fechaDesde))) {
/* 186:191 */           crearFilaReporteCalendarioMantenimiento(equipoFila, componenteFila, actividadFila, fechaOT, "X", resultado, diasAsignados);
/* 187:192 */         } else if ((fechaMantenimiento != null) && (!fechaMantenimiento.after(fechaHasta)) && (!fechaMantenimiento.before(fechaDesde))) {
/* 188:193 */           crearFilaReporteCalendarioMantenimiento(equipoFila, componenteFila, actividadFila, fechaMantenimiento, "*", resultado, diasAsignados);
/* 189:    */         }
/* 190:197 */         if ((fechaInicialMantenimiento != null) && (!fechaInicialMantenimiento.equals(fechaMantenimiento)) && 
/* 191:198 */           (!fechaInicialMantenimiento.after(fechaHasta)) && (!fechaInicialMantenimiento.before(fechaDesde))) {
/* 192:199 */           crearFilaReporteCalendarioMantenimiento(equipoFila, componenteFila, actividadFila, fechaInicialMantenimiento, "O", resultado, diasAsignados);
/* 193:    */         }
/* 194:    */       }
/* 195:205 */       if (indicadorReporteParos)
/* 196:    */       {
/* 197:206 */         fechaMantenimiento = (Date)objects[1];
/* 198:207 */         BigDecimal valor = (BigDecimal)objects[2];
/* 199:208 */         crearFilaReporteCalendarioMantenimiento(equipoFila, componenteFila, actividadFila, fechaMantenimiento, valor, resultado, diasAsignados);
/* 200:    */       }
/* 201:    */     }
/* 202:213 */     while (!fechaDesde.after(fechaHasta))
/* 203:    */     {
/* 204:214 */       Calendar calendario = Calendar.getInstance();
/* 205:215 */       calendario.setTime(fechaDesde);
/* 206:216 */       int diaMes = calendario.get(5);
/* 207:218 */       if (!diasAsignados.contains(Integer.valueOf(diaMes)))
/* 208:    */       {
/* 209:219 */         String equipoFila = null;
/* 210:220 */         String componenteFila = null;
/* 211:221 */         String actividadFila = null;
/* 212:222 */         if (resultado.size() > 0)
/* 213:    */         {
/* 214:223 */           Object[] arreglo = (Object[])resultado.get(0);
/* 215:224 */           equipoFila = (String)arreglo[0];
/* 216:225 */           if (!indicadorReporteParos)
/* 217:    */           {
/* 218:226 */             componenteFila = (String)arreglo[1];
/* 219:227 */             actividadFila = (String)arreglo[2];
/* 220:    */           }
/* 221:    */         }
/* 222:230 */         Object valor = indicadorReporteParos ? BigDecimal.ZERO : "";
/* 223:231 */         crearFilaReporteCalendarioMantenimiento(equipoFila, componenteFila, actividadFila, fechaDesde, valor, resultado, diasAsignados);
/* 224:    */       }
/* 225:234 */       fechaDesde = FuncionesUtiles.sumarFechaDiasMeses(fechaDesde, 1);
/* 226:    */     }
/* 227:237 */     return resultado;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void crearFilaReporteCalendarioMantenimiento(String equipo, String componente, String actividad, Date fecha, Object valorLeyenda, List<Object[]> listaResultado, Set<Integer> diasAsignados)
/* 231:    */   {
/* 232:242 */     Object[] fila = new Object[5];
/* 233:243 */     fila[0] = equipo;
/* 234:244 */     fila[1] = componente;
/* 235:245 */     fila[2] = actividad;
/* 236:    */     
/* 237:247 */     Calendar calendario = Calendar.getInstance();
/* 238:248 */     calendario.setTime(fecha);
/* 239:    */     
/* 240:250 */     int diaMes = calendario.get(5);
/* 241:251 */     diasAsignados.add(Integer.valueOf(diaMes));
/* 242:    */     
/* 243:253 */     fila[3] = Integer.valueOf(diaMes);
/* 244:254 */     fila[4] = valorLeyenda;
/* 245:255 */     listaResultado.add(fila);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<CalendarioMantenimientoEntidad> obtenerCalendarioMantenimiento(int idOrganizacion)
/* 249:    */   {
/* 250:265 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 251:    */     
/* 252:267 */     StringBuilder sql = new StringBuilder();
/* 253:268 */     sql.append(" SELECT cm ");
/* 254:269 */     sql.append(" FROM CalendarioMantenimientoEntidad cm ");
/* 255:270 */     sql.append(" INNER JOIN FETCH cm.equipo eq ");
/* 256:271 */     sql.append(" INNER JOIN FETCH cm.detallePlanMantenimiento dpm ");
/* 257:272 */     sql.append(" INNER JOIN FETCH dpm.componente c ");
/* 258:273 */     sql.append(" INNER JOIN FETCH dpm.actividad a ");
/* 259:274 */     sql.append(" WHERE cm.idOrganizacion = :idOrganizacion ");
/* 260:    */     
/* 261:276 */     Query query = this.em.createQuery(sql.toString());
/* 262:277 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 263:    */     
/* 264:279 */     List<CalendarioMantenimientoEntidad> lista = query.getResultList();
/* 265:281 */     for (CalendarioMantenimientoEntidad calendarioMantenimiento : lista)
/* 266:    */     {
/* 267:283 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> cqRF = cb.createQuery(DetallePlanMantenimientoFrecuencia.class);
/* 268:284 */       Root<DetallePlanMantenimientoFrecuencia> fromRF = cqRF.from(DetallePlanMantenimientoFrecuencia.class);
/* 269:285 */       fromRF.fetch("frecuencia", JoinType.LEFT);
/* 270:286 */       Predicate conjunction = cb.conjunction();
/* 271:287 */       conjunction.getExpressions()
/* 272:288 */         .add(cb.equal(fromRF.join("detallePlanMantenimiento"), calendarioMantenimiento.getDetallePlanMantenimiento()));
/* 273:289 */       cqRF.where(conjunction);
/* 274:290 */       CriteriaQuery<DetallePlanMantenimientoFrecuencia> selectRF = cqRF.select(fromRF);
/* 275:291 */       List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia = this.em.createQuery(selectRF).getResultList();
/* 276:292 */       calendarioMantenimiento.getDetallePlanMantenimiento().setListaDetallePlanMantenimientoFrecuencia(listaDetallePlanMantenimientoFrecuencia);
/* 277:    */     }
/* 278:294 */     return lista;
/* 279:    */   }
/* 280:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.CalendarioMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */