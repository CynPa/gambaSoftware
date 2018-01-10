/*   1:    */ package com.asinfo.as2.dao.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.Frecuencia;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.JoinType;
/*  22:    */ import javax.persistence.criteria.Predicate;
/*  23:    */ import javax.persistence.criteria.Root;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class LecturaMantenimientoDao
/*  27:    */   extends AbstractDaoAS2<LecturaMantenimiento>
/*  28:    */ {
/*  29:    */   public LecturaMantenimientoDao()
/*  30:    */   {
/*  31: 29 */     super(LecturaMantenimiento.class);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<LecturaMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 34 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  37: 35 */     CriteriaQuery<LecturaMantenimiento> criteriaQuery = cb.createQuery(LecturaMantenimiento.class);
/*  38: 36 */     Root<LecturaMantenimiento> from = criteriaQuery.from(LecturaMantenimiento.class);
/*  39: 37 */     from.fetch("frecuencia", JoinType.LEFT);
/*  40: 38 */     from.fetch("componenteEquipo", JoinType.LEFT);
/*  41: 39 */     from.fetch("equipo", JoinType.LEFT);
/*  42: 40 */     from.fetch("actividadMantenimiento", JoinType.LEFT);
/*  43: 41 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, cb, from);
/*  44: 42 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  45:    */     
/*  46: 44 */     agregarOrdenamiento(sortField, sortOrder, cb, criteriaQuery, from);
/*  47:    */     
/*  48: 46 */     CriteriaQuery<LecturaMantenimiento> select = criteriaQuery.select(from);
/*  49:    */     
/*  50: 48 */     TypedQuery<LecturaMantenimiento> typedQuery = this.em.createQuery(select);
/*  51: 49 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  52:    */     
/*  53: 51 */     return typedQuery.getResultList();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public LecturaMantenimiento obtenerUltimaLectura(Equipo equipo, ComponenteEquipo componente, ActividadMantenimiento actividad, Frecuencia frecuencia, Date fechaCorte, boolean indicadorSoloAutomaticos, Integer idLectura)
/*  57:    */   {
/*  58: 57 */     StringBuilder sql = new StringBuilder();
/*  59: 58 */     sql.append(" SELECT lm ");
/*  60: 59 */     sql.append(" FROM LecturaMantenimiento lm ");
/*  61: 60 */     sql.append(" INNER JOIN FETCH lm.equipo eq ");
/*  62: 61 */     sql.append(" LEFT JOIN FETCH lm.componenteEquipo ce ");
/*  63: 62 */     sql.append(" LEFT JOIN FETCH lm.actividadMantenimiento am ");
/*  64: 63 */     sql.append(" LEFT JOIN FETCH lm.frecuencia frec ");
/*  65: 64 */     sql.append(" WHERE eq.idEquipo = :idEquipo ");
/*  66: 65 */     sql.append(" AND lm.fechaLectura <= :fechaCorte ");
/*  67: 66 */     sql.append(" AND (ce IS NULL OR ce.idComponenteEquipo = :idComponenteEquipo) ");
/*  68: 67 */     sql.append(" AND (am IS NULL OR am.idActividadMantenimiento = :idActividadMantenimiento) ");
/*  69: 68 */     if (frecuencia.getTipoFrecuenciaEnum().equals(TipoFrecuenciaEnum.FECHA))
/*  70:    */     {
/*  71: 69 */       sql.append(" AND lm.indicadorTiempo = TRUE ");
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75: 71 */       sql.append(" AND lm.indicadorTiempo = FALSE ");
/*  76: 72 */       sql.append(" AND frec.idFrecuencia = :idFrecuencia ");
/*  77:    */     }
/*  78: 74 */     if (idLectura != null) {
/*  79: 75 */       sql.append(" AND lm.idLecturaMantenimiento != :idLectura ");
/*  80:    */     }
/*  81: 77 */     if (indicadorSoloAutomaticos) {
/*  82: 78 */       sql.append(" AND lm.indicadorAutomatico IS TRUE ");
/*  83:    */     }
/*  84: 81 */     sql.append(" ORDER BY lm.fechaLectura DESC, lm.idLecturaMantenimiento DESC ");
/*  85:    */     
/*  86: 83 */     Query query = this.em.createQuery(sql.toString());
/*  87: 84 */     query.setParameter("idEquipo", Integer.valueOf(equipo.getIdEquipo()));
/*  88: 85 */     query.setParameter("fechaCorte", fechaCorte, TemporalType.DATE);
/*  89: 86 */     query.setParameter("idComponenteEquipo", componente != null ? Integer.valueOf(componente.getId()) : null);
/*  90: 87 */     query.setParameter("idActividadMantenimiento", actividad != null ? Integer.valueOf(actividad.getId()) : null);
/*  91: 88 */     if (frecuencia.getTipoFrecuenciaEnum().equals(TipoFrecuenciaEnum.LECTURA)) {
/*  92: 89 */       query.setParameter("idFrecuencia", Integer.valueOf(frecuencia.getId()));
/*  93:    */     }
/*  94: 91 */     if (idLectura != null) {
/*  95: 92 */       query.setParameter("idLectura", idLectura);
/*  96:    */     }
/*  97: 94 */     query.setMaxResults(1);
/*  98:    */     
/*  99: 96 */     LecturaMantenimiento lectura = null;
/* 100: 97 */     List<LecturaMantenimiento> listaLectura = query.getResultList();
/* 101: 98 */     if (listaLectura.size() > 0) {
/* 102: 99 */       lectura = (LecturaMantenimiento)listaLectura.get(0);
/* 103:    */     }
/* 104:101 */     return lectura;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<LecturaMantenimiento> obtenerLecturasEquipo(Equipo equipo)
/* 108:    */   {
/* 109:106 */     StringBuilder sql = new StringBuilder();
/* 110:107 */     sql.append("SELECT lm");
/* 111:108 */     sql.append(" FROM LecturaMantenimiento lm ");
/* 112:109 */     sql.append(" LEFT JOIN FETCH lm.detalleOrdenTrabajoMantenimiento dOTM ");
/* 113:110 */     sql.append(" LEFT JOIN FETCH lm.equipo e ");
/* 114:111 */     sql.append(" LEFT JOIN FETCH lm.actividadMantenimiento am ");
/* 115:112 */     sql.append(" LEFT JOIN FETCH dOTM.ordenTrabajoMantenimiento otm ");
/* 116:113 */     sql.append(" LEFT JOIN FETCH dOTM.listaImagenOrdenTrabajoMantenimiento iotm ");
/* 117:114 */     sql.append(" WHERE e.idEquipo = :idEquipo ");
/* 118:115 */     sql.append(" AND lm.actividadMantenimiento IS NOT NULL ");
/* 119:116 */     sql.append(" ORDER BY am.codigo");
/* 120:117 */     Query query = this.em.createQuery(sql.toString());
/* 121:118 */     query.setParameter("idEquipo", Integer.valueOf(equipo.getIdEquipo()));
/* 122:119 */     query.setMaxResults(100);
/* 123:120 */     List<LecturaMantenimiento> listaLectura = query.getResultList();
/* 124:121 */     return listaLectura;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LecturaMantenimiento obtenerUltimaLectura(Equipo equipo, ComponenteEquipo componente, ActividadMantenimiento actividad, Frecuencia frecuencia, boolean todas)
/* 128:    */   {
/* 129:127 */     StringBuilder sql = new StringBuilder();
/* 130:128 */     sql.append(" SELECT lm ");
/* 131:129 */     sql.append(" FROM LecturaMantenimiento lm ");
/* 132:130 */     sql.append(" INNER JOIN FETCH lm.equipo eq ");
/* 133:131 */     sql.append(" INNER JOIN FETCH lm.componenteEquipo ce ");
/* 134:132 */     sql.append(" LEFT  JOIN FETCH lm.actividadMantenimiento ac ");
/* 135:133 */     sql.append(" LEFT  JOIN FETCH lm.frecuencia f ");
/* 136:134 */     sql.append(" WHERE eq.idEquipo = :idEquipo ");
/* 137:135 */     sql.append(" AND ce.idComponenteEquipo = :idComponenteEquipo ");
/* 138:136 */     if (!todas) {
/* 139:137 */       if (actividad != null) {
/* 140:138 */         sql.append(" AND ac.idActividadMantenimiento = :idActividadMantenimiento ");
/* 141:    */       } else {
/* 142:140 */         sql.append(" AND f.idFrecuencia = :idFrecuencia ");
/* 143:    */       }
/* 144:    */     }
/* 145:143 */     sql.append(" ORDER BY lm.fechaLectura DESC ");
/* 146:    */     
/* 147:145 */     Query query = this.em.createQuery(sql.toString());
/* 148:146 */     query.setParameter("idEquipo", Integer.valueOf(equipo.getIdEquipo()));
/* 149:147 */     query.setParameter("idComponenteEquipo", Integer.valueOf(componente.getId()));
/* 150:148 */     if (!todas) {
/* 151:149 */       if (actividad != null) {
/* 152:150 */         query.setParameter("idActividadMantenimiento", Integer.valueOf(actividad.getIdActividadMantenimiento()));
/* 153:    */       } else {
/* 154:152 */         query.setParameter("idFrecuencia", Integer.valueOf(frecuencia.getIdFrecuencia()));
/* 155:    */       }
/* 156:    */     }
/* 157:155 */     query.setMaxResults(1);
/* 158:    */     
/* 159:157 */     LecturaMantenimiento lectura = null;
/* 160:158 */     List<LecturaMantenimiento> listaLectura = query.getResultList();
/* 161:159 */     if (listaLectura.size() > 0) {
/* 162:160 */       lectura = (LecturaMantenimiento)listaLectura.get(0);
/* 163:    */     }
/* 164:162 */     return lectura;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<LecturaMantenimiento> obtenerUltimasLecturas(TipoFrecuenciaEnum tipoFrecuenciaEnum, int idOrganizacion)
/* 168:    */   {
/* 169:178 */     StringBuilder sql = new StringBuilder();
/* 170:180 */     if (tipoFrecuenciaEnum == null) {
/* 171:181 */       sql.append(" SELECT new LecturaMantenimiento(lm.idLecturaMantenimiento, lm.fechaLectura, lm.fechaLecturaAnterior, lm.valor, lm.valorAnterior, lm.valorAcumulado, eq.idEquipo, ce.idComponenteEquipo) ");
/* 172:182 */     } else if (tipoFrecuenciaEnum.equals(TipoFrecuenciaEnum.LECTURA)) {
/* 173:183 */       sql.append(" SELECT new LecturaMantenimiento(lm.idLecturaMantenimiento, lm.fechaLectura, lm.fechaLecturaAnterior, lm.valor, lm.valorAnterior, lm.valorAcumulado, eq.idEquipo, ce.idComponenteEquipo, f.idFrecuencia) ");
/* 174:184 */     } else if (tipoFrecuenciaEnum.equals(TipoFrecuenciaEnum.FECHA)) {
/* 175:185 */       sql.append(" SELECT new LecturaMantenimiento(lm.idLecturaMantenimiento, lm.fechaLectura, lm.fechaLecturaAnterior, lm.valor, lm.valorAnterior, lm.valorAcumulado, eq.idEquipo, ce.idComponenteEquipo, ac.idActividadMantenimiento) ");
/* 176:    */     }
/* 177:188 */     sql.append(" FROM LecturaMantenimiento lm ");
/* 178:189 */     sql.append(" INNER JOIN lm.equipo eq ");
/* 179:190 */     sql.append(" INNER JOIN lm.componenteEquipo ce ");
/* 180:191 */     sql.append(" LEFT  JOIN lm.actividadMantenimiento ac ");
/* 181:192 */     sql.append(" LEFT  JOIN lm.frecuencia f ");
/* 182:193 */     sql.append(" WHERE lm.idOrganizacion = :idOrganizacion ");
/* 183:194 */     sql.append(" AND   lm.fechaLectura = ( SELECT MAX(lm2.fechaLectura) FROM LecturaMantenimiento lm2 ");
/* 184:195 */     sql.append(" \t\tINNER JOIN lm2.equipo eq2 ");
/* 185:196 */     sql.append(" \t\tINNER JOIN lm2.componenteEquipo ce2 ");
/* 186:197 */     sql.append(" \t\tWHERE eq2.idEquipo = eq.idEquipo ");
/* 187:198 */     sql.append(" \t\tAND ce2.idComponenteEquipo = ce.idComponenteEquipo ");
/* 188:200 */     if (tipoFrecuenciaEnum == null) {
/* 189:201 */       sql.append(" ) ");
/* 190:202 */     } else if (tipoFrecuenciaEnum.equals(TipoFrecuenciaEnum.LECTURA)) {
/* 191:203 */       sql.append(" AND lm2.frecuencia IS NOT NULL) ");
/* 192:204 */     } else if (tipoFrecuenciaEnum.equals(TipoFrecuenciaEnum.FECHA)) {
/* 193:205 */       sql.append(" AND lm2.actividadMantenimiento IS NOT NULL) ");
/* 194:    */     }
/* 195:208 */     Query query = this.em.createQuery(sql.toString());
/* 196:209 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 197:210 */     return query.getResultList();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Long countLecturas(Equipo equipo, ComponenteEquipo componente, ActividadMantenimiento actividad, Frecuencia frecuencia, boolean todas)
/* 201:    */   {
/* 202:216 */     StringBuilder sql = new StringBuilder();
/* 203:217 */     sql.append(" SELECT COUNT(lm) ");
/* 204:218 */     sql.append(" FROM LecturaMantenimiento lm ");
/* 205:219 */     sql.append(" INNER JOIN lm.equipo eq ");
/* 206:220 */     sql.append(" INNER JOIN lm.componenteEquipo ce ");
/* 207:221 */     sql.append(" LEFT  JOIN lm.actividadMantenimiento ac ");
/* 208:222 */     sql.append(" LEFT  JOIN lm.frecuencia f ");
/* 209:223 */     sql.append(" WHERE eq.idEquipo = :idEquipo ");
/* 210:224 */     sql.append(" AND ce.idComponenteEquipo = :idComponenteEquipo ");
/* 211:225 */     if (!todas) {
/* 212:226 */       if (actividad != null) {
/* 213:227 */         sql.append(" AND ac.idActividadMantenimiento = :idActividadMantenimiento ");
/* 214:    */       } else {
/* 215:229 */         sql.append(" AND f.idFrecuencia = :idFrecuencia ");
/* 216:    */       }
/* 217:    */     }
/* 218:233 */     Query query = this.em.createQuery(sql.toString());
/* 219:234 */     query.setParameter("idEquipo", Integer.valueOf(equipo.getIdEquipo()));
/* 220:235 */     query.setParameter("idComponenteEquipo", Integer.valueOf(componente.getId()));
/* 221:236 */     if (!todas) {
/* 222:237 */       if (actividad != null) {
/* 223:238 */         query.setParameter("idActividadMantenimiento", Integer.valueOf(actividad.getIdActividadMantenimiento()));
/* 224:    */       } else {
/* 225:240 */         query.setParameter("idFrecuencia", Integer.valueOf(frecuencia.getIdFrecuencia()));
/* 226:    */       }
/* 227:    */     }
/* 228:244 */     return (Long)query.getSingleResult();
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.LecturaMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */