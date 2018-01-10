/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Departamento;
/*   4:    */ import com.asinfo.as2.entities.DetalleVacacion;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Vacacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ import javax.persistence.EntityManager;
/*  14:    */ import javax.persistence.NoResultException;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.Fetch;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Predicate;
/*  24:    */ import javax.persistence.criteria.Root;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class VacacionDao
/*  28:    */   extends AbstractDaoAS2<Vacacion>
/*  29:    */ {
/*  30:    */   public VacacionDao()
/*  31:    */   {
/*  32: 52 */     super(Vacacion.class);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<Vacacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 62 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  38: 63 */     CriteriaQuery<Vacacion> criteriaQuery = criteriaBuilder.createQuery(Vacacion.class);
/*  39: 64 */     Root<Vacacion> from = criteriaQuery.from(Vacacion.class);
/*  40: 65 */     Fetch<Object, Object> historicoEmpleado = from.fetch("historicoEmpleado", JoinType.LEFT);
/*  41: 66 */     Fetch<Object, Object> empleado = historicoEmpleado.fetch("empleado", JoinType.LEFT);
/*  42: 67 */     empleado.fetch("empresa", JoinType.LEFT);
/*  43:    */     
/*  44: 69 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  45: 70 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  46: 71 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  47:    */     
/*  48: 73 */     CriteriaQuery<Vacacion> select = criteriaQuery.select(from);
/*  49: 74 */     TypedQuery<Vacacion> typedQuery = this.em.createQuery(select);
/*  50: 75 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  51:    */     
/*  52: 77 */     return typedQuery.getResultList();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<Vacacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  56:    */   {
/*  57: 88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  58: 89 */     CriteriaQuery<Vacacion> criteriaQuery = criteriaBuilder.createQuery(Vacacion.class);
/*  59: 90 */     Root<Vacacion> from = criteriaQuery.from(Vacacion.class);
/*  60: 91 */     from.fetch("historicoEmpleado", JoinType.LEFT);
/*  61:    */     
/*  62: 93 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  63:    */     
/*  64: 95 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  65: 96 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  66:    */     
/*  67: 98 */     CriteriaQuery<Vacacion> select = criteriaQuery.select(from);
/*  68: 99 */     TypedQuery<Vacacion> typedQuery = this.em.createQuery(select);
/*  69:    */     
/*  70:101 */     return typedQuery.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Vacacion cargarDetalle(int idVacacion)
/*  74:    */   {
/*  75:111 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<Vacacion> getVacaciones(int idOrganizacion, Empleado empleado, boolean activo)
/*  79:    */   {
/*  80:122 */     List<Vacacion> listaVacacion = new ArrayList();
/*  81:    */     
/*  82:124 */     StringBuilder sql = new StringBuilder();
/*  83:125 */     sql.append(" SELECT NEW Vacacion(v.idVacacion, v.fechaInicioPeriodo, v.fechaFinPeriodo, v.historicoEmpleado, he.fechaIngreso) ");
/*  84:126 */     sql.append(" FROM Vacacion v ");
/*  85:127 */     sql.append(" INNER JOIN  v.historicoEmpleado he ");
/*  86:128 */     sql.append(" INNER JOIN he.empleado e ");
/*  87:129 */     sql.append(" INNER JOIN e.empresa em ");
/*  88:130 */     sql.append(" WHERE em.indicadorEmpleado=true ");
/*  89:131 */     sql.append(" AND v.idOrganizacion =:idOrganizacion ");
/*  90:132 */     sql.append(" AND he.fechaIngreso IS NOT NULL ");
/*  91:133 */     if (activo)
/*  92:    */     {
/*  93:134 */       sql.append(" AND he.fechaSalida IS NULL ");
/*  94:135 */       sql.append(" AND e.activo=true ");
/*  95:    */     }
/*  96:137 */     if (empleado != null) {
/*  97:138 */       sql.append(" AND e = :empleado ");
/*  98:    */     }
/*  99:141 */     Query query = this.em.createQuery(sql.toString());
/* 100:142 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 101:143 */     if (empleado != null) {
/* 102:144 */       query.setParameter("empleado", empleado);
/* 103:    */     }
/* 104:146 */     listaVacacion.addAll(query.getResultList());
/* 105:    */     
/* 106:    */ 
/* 107:149 */     sql = new StringBuilder();
/* 108:150 */     sql.append(" SELECT NEW Vacacion(0, he.fechaIngreso, he.fechaSalida, he, he.fechaIngreso) ");
/* 109:151 */     sql.append(" FROM  HistoricoEmpleado he ");
/* 110:152 */     sql.append(" INNER JOIN he.empleado e ");
/* 111:153 */     sql.append(" INNER JOIN e.empresa em ");
/* 112:154 */     sql.append(" WHERE em.indicadorEmpleado=true ");
/* 113:155 */     sql.append(" AND he.idOrganizacion =:idOrganizacion ");
/* 114:156 */     sql.append(" AND he.fechaIngreso IS NOT NULL ");
/* 115:157 */     if (activo)
/* 116:    */     {
/* 117:158 */       sql.append(" AND he.fechaSalida IS NULL ");
/* 118:159 */       sql.append(" AND e.activo=true ");
/* 119:    */     }
/* 120:161 */     if (empleado != null) {
/* 121:162 */       sql.append(" AND e = :empleado ");
/* 122:    */     }
/* 123:164 */     sql.append(" AND NOT EXISTS (SELECT 1 FROM Vacacion v WHERE he = v.historicoEmpleado ) ");
/* 124:    */     
/* 125:    */ 
/* 126:167 */     query = this.em.createQuery(sql.toString());
/* 127:168 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 128:169 */     if (empleado != null) {
/* 129:170 */       query.setParameter("empleado", empleado);
/* 130:    */     }
/* 131:172 */     listaVacacion.addAll(query.getResultList());
/* 132:    */     
/* 133:    */ 
/* 134:    */ 
/* 135:176 */     return listaVacacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Boolean existeVacacionPorPeriodo(int idHistoricoEmpleado, Date fechaInicioPeriodo, Date fechaFinPeriodo)
/* 139:    */   {
/* 140:185 */     return Boolean.valueOf(!obtenerVacacionPorPeriodo(idHistoricoEmpleado, fechaInicioPeriodo, fechaFinPeriodo).isEmpty());
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Vacacion> obtenerVacacionPorPeriodo(int idHistoricoEmpleado, Date fechaInicioPeriodo, Date fechaFinPeriodo)
/* 144:    */   {
/* 145:191 */     String sql = "SELECT v\tFROM Vacacion v WHERE v.historicoEmpleado.idHistoricoEmpleado=:idHistoricoEmpleado AND v.fechaInicioPeriodo=:fechaInicioPeriodo AND v.fechaFinPeriodo=:fechaFinPeriodo";
/* 146:    */     
/* 147:    */ 
/* 148:194 */     Query query = this.em.createQuery(sql);
/* 149:195 */     query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/* 150:196 */     query.setParameter("fechaInicioPeriodo", fechaInicioPeriodo, TemporalType.DATE);
/* 151:197 */     query.setParameter("fechaFinPeriodo", fechaFinPeriodo, TemporalType.DATE);
/* 152:    */     
/* 153:199 */     return query.getResultList();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Vacacion buscarVacacionPorEmpleadoId(int idEmpleado)
/* 157:    */   {
/* 158:203 */     return null;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<Vacacion> getListaVacacion(int idEmpleado, int idOrganizacion, DetalleVacacion detalleVacacion)
/* 162:    */   {
/* 163:215 */     StringBuilder sql = new StringBuilder();
/* 164:216 */     if (detalleVacacion != null)
/* 165:    */     {
/* 166:217 */       sql.append("SELECT DISTINCT(v)");
/* 167:218 */       sql.append(" FROM DetalleVacacion dv");
/* 168:219 */       sql.append(" INNER JOIN dv.vacacion v");
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:221 */       sql.append(" SELECT v FROM Vacacion v");
/* 173:    */     }
/* 174:223 */     sql.append(" INNER JOIN v.historicoEmpleado he ");
/* 175:224 */     sql.append(" INNER JOIN he.empleado e");
/* 176:225 */     sql.append(" INNER JOIN e.empresa em");
/* 177:226 */     sql.append(" WHERE e.activo=true AND he.fechaSalida IS NULL AND he.fechaIngreso IS NOT NULL");
/* 178:227 */     sql.append(" AND em.indicadorEmpleado=true");
/* 179:228 */     if (detalleVacacion != null) {
/* 180:229 */       sql.append(" AND dv = :detalleVacacion");
/* 181:    */     } else {
/* 182:231 */       sql.append(" AND ((v.dias+v.diasAdicionales)-v.diasTomados)>0");
/* 183:    */     }
/* 184:233 */     sql.append(" AND e.idEmpleado=:idEmpleado");
/* 185:234 */     sql.append(" AND v.idOrganizacion =:idOrganizacion ");
/* 186:235 */     sql.append(" ORDER BY v.fechaInicioPeriodo ASC");
/* 187:236 */     Query query = this.em.createQuery(sql.toString());
/* 188:237 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 189:238 */     if (detalleVacacion != null) {
/* 190:239 */       query.setParameter("detalleVacacion", detalleVacacion);
/* 191:    */     }
/* 192:241 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 193:    */     
/* 194:243 */     return query.getResultList();
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void actualizarDiasAdicionales(int idVacacion, int diasAdicionales)
/* 198:    */   {
/* 199:248 */     Query query = this.em.createQuery(" UPDATE Vacacion v SET v.diasAdicionales =:diasAdicionales WHERE v.idVacacion = :idVacacion ");
/* 200:249 */     query.setParameter("idVacacion", Integer.valueOf(idVacacion));
/* 201:250 */     query.setParameter("diasAdicionales", Integer.valueOf(diasAdicionales));
/* 202:251 */     query.executeUpdate();
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<Empleado> getEmpleadosConVacacionesPorFecha(int idOrganizacion, Date fecha, Departamento departamento, Empleado empleado)
/* 206:    */   {
/* 207:257 */     StringBuilder sql = new StringBuilder();
/* 208:    */     
/* 209:259 */     sql.append(" SELECT e FROM DetalleVacacion dv");
/* 210:260 */     sql.append(" INNER JOIN dv.vacacion v");
/* 211:261 */     sql.append(" INNER JOIN v.historicoEmpleado he ");
/* 212:262 */     sql.append(" INNER JOIN he.empleado e");
/* 213:263 */     sql.append(" INNER JOIN e.empresa emp");
/* 214:264 */     if (departamento != null) {
/* 215:265 */       sql.append(" JOIN e.departamento d");
/* 216:    */     }
/* 217:267 */     sql.append(" WHERE (e.idOrganizacion = :idOrganizacion) ");
/* 218:268 */     sql.append(" AND (e.activo IS TRUE)");
/* 219:269 */     sql.append(" AND dv.estado != :estadoAnulado");
/* 220:270 */     sql.append(" AND dv.estado != :estadoElaborado");
/* 221:271 */     sql.append(" AND (:fecha BETWEEN dv.fechaInicio AND dv.fechaFin)");
/* 222:272 */     sql.append(" AND dv.vacacionPagada = false");
/* 223:273 */     if (departamento != null) {
/* 224:274 */       sql.append(" AND d = :departamento");
/* 225:    */     }
/* 226:276 */     if (empleado != null) {
/* 227:277 */       sql.append(" AND e = :empleado");
/* 228:    */     }
/* 229:279 */     Query query = this.em.createQuery(sql.toString());
/* 230:280 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 231:281 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 232:282 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 233:283 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 234:284 */     if (departamento != null) {
/* 235:285 */       query.setParameter("departamento", departamento);
/* 236:    */     }
/* 237:287 */     if (empleado != null) {
/* 238:288 */       query.setParameter("empleado", empleado);
/* 239:    */     }
/* 240:290 */     return query.getResultList();
/* 241:    */   }
/* 242:    */   
/* 243:    */   public long getDiasPendientes(int idHistoricoEmpleado)
/* 244:    */   {
/* 245:    */     try
/* 246:    */     {
/* 247:295 */       StringBuilder sb = new StringBuilder();
/* 248:296 */       sb.append(" SELECT SUM(v.dias + v.diasAdicionales - v. diasTomados) FROM Vacacion v");
/* 249:297 */       sb.append(" WHERE v.historicoEmpleado.idHistoricoEmpleado = :idHistoricoEmpleado ");
/* 250:298 */       sb.append(" GROUP BY v.historicoEmpleado.idHistoricoEmpleado ");
/* 251:299 */       Query qry = this.em.createQuery(sb.toString());
/* 252:300 */       qry.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/* 253:    */       
/* 254:302 */       return ((Long)qry.getSingleResult()).longValue();
/* 255:    */     }
/* 256:    */     catch (NoResultException e) {}
/* 257:304 */     return 0L;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public long getTotalDiasPendientesXCerrar(DetalleVacacion detalleVacacion, Empleado empleado)
/* 261:    */   {
/* 262:315 */     StringBuilder sb = new StringBuilder();
/* 263:316 */     sb.append(" SELECT COALESCE(SUM(dv.diasTomados),0) FROM DetalleVacacion dv ");
/* 264:317 */     sb.append(" INNER JOIN  dv.vacacion v ");
/* 265:318 */     sb.append(" INNER JOIN  v.historicoEmpleado he ");
/* 266:319 */     sb.append(" INNER JOIN  he.empleado e ");
/* 267:    */     
/* 268:321 */     sb.append(" WHERE e.idEmpleado = :empleado and v.fechaInicioPeriodo = :fechaInicio AND v.fechaFinPeriodo = :fechaFin ");
/* 269:322 */     sb.append(" AND (dv.estado = :estadoAprobado or dv.estado = :estadoElaborado) AND dv.idDetalleVacacion <> :idDetalleVacacion ");
/* 270:323 */     Query qry = this.em.createQuery(sb.toString());
/* 271:324 */     qry.setParameter("fechaInicio", detalleVacacion.getVacacion().getFechaInicioPeriodo());
/* 272:325 */     qry.setParameter("fechaFin", detalleVacacion.getVacacion().getFechaFinPeriodo());
/* 273:326 */     qry.setParameter("empleado", Integer.valueOf(empleado.getId()));
/* 274:327 */     qry.setParameter("estadoAprobado", Estado.APROBADO);
/* 275:328 */     qry.setParameter("estadoElaborado", Estado.ELABORADO);
/* 276:329 */     qry.setParameter("idDetalleVacacion", Integer.valueOf(detalleVacacion.getId()));
/* 277:    */     
/* 278:331 */     return ((Long)qry.getSingleResult()).longValue();
/* 279:    */   }
/* 280:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.VacacionDao
 * JD-Core Version:    0.7.0.1
 */