/*   1:    */ package com.asinfo.as2.dao.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.mantenimiento.ActividadImagenOrdenTrabajoMantenimiento;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.ActividadMaterialOrdenTrabajoMantenimiento;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.ActividadResponsableOrdenTrabajoMantenimiento;
/*   8:    */ import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
/*   9:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  10:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.HerramientaOrdenTrabajoMantenimiento;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.MaterialOrdenTrabajoMantenimiento;
/*  16:    */ import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.ResponsableOrdenTrabajoMantenimiento;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import java.io.PrintStream;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.Lock;
/*  26:    */ import javax.ejb.LockType;
/*  27:    */ import javax.ejb.Stateless;
/*  28:    */ import javax.persistence.EntityManager;
/*  29:    */ import javax.persistence.Query;
/*  30:    */ import javax.persistence.TemporalType;
/*  31:    */ import javax.persistence.TypedQuery;
/*  32:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  33:    */ import javax.persistence.criteria.CriteriaQuery;
/*  34:    */ import javax.persistence.criteria.Expression;
/*  35:    */ import javax.persistence.criteria.Fetch;
/*  36:    */ import javax.persistence.criteria.JoinType;
/*  37:    */ import javax.persistence.criteria.Predicate;
/*  38:    */ import javax.persistence.criteria.Root;
/*  39:    */ 
/*  40:    */ @Stateless
/*  41:    */ public class OrdenTrabajoMantenimientoDao
/*  42:    */   extends AbstractDaoAS2<OrdenTrabajoMantenimiento>
/*  43:    */ {
/*  44:    */   public OrdenTrabajoMantenimientoDao()
/*  45:    */   {
/*  46: 51 */     super(OrdenTrabajoMantenimiento.class);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public OrdenTrabajoMantenimiento cargarDetalle(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/*  50:    */   {
/*  51: 55 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  52: 56 */     CriteriaQuery<OrdenTrabajoMantenimiento> criteriaQuery = cb.createQuery(OrdenTrabajoMantenimiento.class);
/*  53: 57 */     Root<OrdenTrabajoMantenimiento> from = criteriaQuery.from(OrdenTrabajoMantenimiento.class);
/*  54: 58 */     Fetch<Object, Object> documento = from.fetch("documento", JoinType.INNER);
/*  55: 59 */     documento.fetch("secuencia", JoinType.LEFT);
/*  56: 60 */     criteriaQuery.where(cb.equal(from.get("idOrdenTrabajoMantenimiento"), Integer.valueOf(ordenTrabajoMantenimiento.getId())));
/*  57: 61 */     CriteriaQuery<OrdenTrabajoMantenimiento> select = criteriaQuery.select(from);
/*  58: 62 */     OrdenTrabajoMantenimiento ordenTrabajo = (OrdenTrabajoMantenimiento)this.em.createQuery(select).getSingleResult();
/*  59:    */     
/*  60: 64 */     CriteriaQuery<DetalleOrdenTrabajoMantenimiento> cqDetalle = cb.createQuery(DetalleOrdenTrabajoMantenimiento.class);
/*  61: 65 */     Root<DetalleOrdenTrabajoMantenimiento> fromDetalle = cqDetalle.from(DetalleOrdenTrabajoMantenimiento.class);
/*  62: 66 */     fromDetalle.fetch("equipo", JoinType.INNER);
/*  63: 67 */     fromDetalle.fetch("componenteEquipo", JoinType.INNER);
/*  64: 68 */     fromDetalle.fetch("actividadMantenimiento", JoinType.INNER);
/*  65: 69 */     fromDetalle.fetch("planMantenimiento", JoinType.LEFT);
/*  66: 70 */     cqDetalle.where(cb.equal(fromDetalle.join("ordenTrabajoMantenimiento"), ordenTrabajo));
/*  67: 71 */     CriteriaQuery<DetalleOrdenTrabajoMantenimiento> selectDetalle = cqDetalle.select(fromDetalle);
/*  68: 72 */     List<DetalleOrdenTrabajoMantenimiento> listaDetalle = this.em.createQuery(selectDetalle).getResultList();
/*  69: 73 */     for (DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento : listaDetalle)
/*  70:    */     {
/*  71: 75 */       CriteriaQuery<DetalleComponenteEquipo> cqDCE = cb.createQuery(DetalleComponenteEquipo.class);
/*  72: 76 */       Root<DetalleComponenteEquipo> fromCE = cqDCE.from(DetalleComponenteEquipo.class);
/*  73: 77 */       fromCE.fetch("componenteEquipo", JoinType.LEFT);
/*  74: 78 */       cqDCE.where(cb.equal(fromCE.join("equipo"), detalleOrdenTrabajoMantenimiento.getEquipo()));
/*  75: 79 */       CriteriaQuery<DetalleComponenteEquipo> selectDCE = cqDCE.select(fromCE);
/*  76: 80 */       List<DetalleComponenteEquipo> listaDCE = this.em.createQuery(selectDCE).getResultList();
/*  77: 81 */       detalleOrdenTrabajoMantenimiento.getEquipo().setListaComponenteEquipo(listaDCE);
/*  78:    */       
/*  79:    */ 
/*  80: 84 */       CriteriaQuery<ActividadResponsableOrdenTrabajoMantenimiento> cqARM = cb.createQuery(ActividadResponsableOrdenTrabajoMantenimiento.class);
/*  81: 85 */       Root<ActividadResponsableOrdenTrabajoMantenimiento> fromARM = cqARM.from(ActividadResponsableOrdenTrabajoMantenimiento.class);
/*  82: 86 */       fromARM.fetch("responsableOrdenTrabajoMantenimiento", JoinType.INNER).fetch("personaResponsable", JoinType.INNER);
/*  83: 87 */       cqARM.where(cb.equal(fromARM.join("detalleOrdenTrabajoMantenimiento"), detalleOrdenTrabajoMantenimiento));
/*  84: 88 */       CriteriaQuery<ActividadResponsableOrdenTrabajoMantenimiento> selectARM = cqARM.select(fromARM);
/*  85: 89 */       List<ActividadResponsableOrdenTrabajoMantenimiento> listaARM = this.em.createQuery(selectARM).getResultList();
/*  86: 90 */       detalleOrdenTrabajoMantenimiento.setListaResponsableOrdenTrabajoMantenimiento(listaARM);
/*  87:    */       
/*  88:    */ 
/*  89: 93 */       CriteriaQuery<ActividadMaterialOrdenTrabajoMantenimiento> cqAMM = cb.createQuery(ActividadMaterialOrdenTrabajoMantenimiento.class);
/*  90: 94 */       Root<ActividadMaterialOrdenTrabajoMantenimiento> fromAMM = cqAMM.from(ActividadMaterialOrdenTrabajoMantenimiento.class);
/*  91: 95 */       fromAMM.fetch("materialOrdenTrabajoMantenimiento", JoinType.INNER).fetch("material", JoinType.INNER);
/*  92: 96 */       cqAMM.where(cb.equal(fromAMM.join("detalleOrdenTrabajoMantenimiento"), detalleOrdenTrabajoMantenimiento));
/*  93: 97 */       CriteriaQuery<ActividadMaterialOrdenTrabajoMantenimiento> selectAMM = cqAMM.select(fromAMM);
/*  94: 98 */       List<ActividadMaterialOrdenTrabajoMantenimiento> listaAMM = this.em.createQuery(selectAMM).getResultList();
/*  95: 99 */       detalleOrdenTrabajoMantenimiento.setListaMaterialOrdenTrabajoMantenimiento(listaAMM);
/*  96:    */       
/*  97:    */ 
/*  98:102 */       CriteriaQuery<ActividadImagenOrdenTrabajoMantenimiento> cqAIM = cb.createQuery(ActividadImagenOrdenTrabajoMantenimiento.class);
/*  99:103 */       Root<ActividadImagenOrdenTrabajoMantenimiento> fromAIM = cqAIM.from(ActividadImagenOrdenTrabajoMantenimiento.class);
/* 100:104 */       cqAIM.where(cb.equal(fromAIM.join("detalleOrdenTrabajoMantenimiento"), detalleOrdenTrabajoMantenimiento));
/* 101:105 */       CriteriaQuery<ActividadImagenOrdenTrabajoMantenimiento> selectAIM = cqAIM.select(fromAIM);
/* 102:106 */       List<ActividadImagenOrdenTrabajoMantenimiento> listaAIM = this.em.createQuery(selectAIM).getResultList();
/* 103:107 */       detalleOrdenTrabajoMantenimiento.setListaImagenOrdenTrabajoMantenimiento(listaAIM);
/* 104:    */       
/* 105:    */ 
/* 106:110 */       CriteriaQuery<LecturaMantenimiento> cqLM = cb.createQuery(LecturaMantenimiento.class);
/* 107:111 */       Root<LecturaMantenimiento> fromLM = cqLM.from(LecturaMantenimiento.class);
/* 108:112 */       fromLM.fetch("frecuencia", JoinType.LEFT);
/* 109:113 */       cqLM.where(cb.equal(fromLM.join("detalleOrdenTrabajoMantenimiento"), detalleOrdenTrabajoMantenimiento));
/* 110:114 */       CriteriaQuery<LecturaMantenimiento> selectLM = cqLM.select(fromLM);
/* 111:115 */       List<LecturaMantenimiento> listaLM = this.em.createQuery(selectLM).getResultList();
/* 112:116 */       detalleOrdenTrabajoMantenimiento.setListaLecturaMantenimiento(listaLM);
/* 113:    */     }
/* 114:119 */     Object cqResponsable = cb.createQuery(ResponsableOrdenTrabajoMantenimiento.class);
/* 115:120 */     Root<ResponsableOrdenTrabajoMantenimiento> fromResponsable = ((CriteriaQuery)cqResponsable).from(ResponsableOrdenTrabajoMantenimiento.class);
/* 116:121 */     fromResponsable.fetch("personaResponsable", JoinType.INNER);
/* 117:122 */     ((CriteriaQuery)cqResponsable).where(cb.equal(fromResponsable.join("ordenTrabajoMantenimiento"), ordenTrabajo));
/* 118:123 */     CriteriaQuery<ResponsableOrdenTrabajoMantenimiento> selectResponsable = ((CriteriaQuery)cqResponsable).select(fromResponsable);
/* 119:124 */     List<ResponsableOrdenTrabajoMantenimiento> listaResponsable = this.em.createQuery(selectResponsable).getResultList();
/* 120:    */     
/* 121:    */ 
/* 122:127 */     CriteriaQuery<MaterialOrdenTrabajoMantenimiento> cqMaterial = cb.createQuery(MaterialOrdenTrabajoMantenimiento.class);
/* 123:128 */     Root<MaterialOrdenTrabajoMantenimiento> fromMaterial = cqMaterial.from(MaterialOrdenTrabajoMantenimiento.class);
/* 124:129 */     fromMaterial.fetch("material", JoinType.INNER);
/* 125:130 */     cqMaterial.where(cb.equal(fromMaterial.join("ordenTrabajoMantenimiento"), ordenTrabajo));
/* 126:131 */     CriteriaQuery<MaterialOrdenTrabajoMantenimiento> selectMaterial = cqMaterial.select(fromMaterial);
/* 127:132 */     List<MaterialOrdenTrabajoMantenimiento> listaMaterial = this.em.createQuery(selectMaterial).getResultList();
/* 128:    */     
/* 129:    */ 
/* 130:135 */     CriteriaQuery<HerramientaOrdenTrabajoMantenimiento> cqHerramienta = cb.createQuery(HerramientaOrdenTrabajoMantenimiento.class);
/* 131:136 */     Root<HerramientaOrdenTrabajoMantenimiento> fromHerramienta = cqHerramienta.from(HerramientaOrdenTrabajoMantenimiento.class);
/* 132:137 */     fromHerramienta.fetch("herramienta", JoinType.INNER);
/* 133:138 */     cqHerramienta.where(cb.equal(fromHerramienta.join("ordenTrabajoMantenimiento"), ordenTrabajo));
/* 134:139 */     CriteriaQuery<HerramientaOrdenTrabajoMantenimiento> selectHerramienta = cqHerramienta.select(fromHerramienta);
/* 135:140 */     List<HerramientaOrdenTrabajoMantenimiento> listaHerramienta = this.em.createQuery(selectHerramienta).getResultList();
/* 136:    */     
/* 137:142 */     ordenTrabajo.setListaDetalleOrdenTrabajoMantenimiento(listaDetalle);
/* 138:143 */     ordenTrabajo.setListaResponsableOrdenTrabajoMantenimiento(listaResponsable);
/* 139:144 */     ordenTrabajo.setListaHerramientaOrdenTrabajoMantenimiento(listaHerramienta);
/* 140:145 */     ordenTrabajo.setListaMaterialOrdenTrabajoMantenimiento(listaMaterial);
/* 141:    */     
/* 142:147 */     return ordenTrabajo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<OrdenTrabajoMantenimiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 146:    */   {
/* 147:152 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 148:153 */     CriteriaQuery<OrdenTrabajoMantenimiento> criteriaQuery = criteriaBuilder.createQuery(OrdenTrabajoMantenimiento.class);
/* 149:154 */     Root<OrdenTrabajoMantenimiento> from = criteriaQuery.from(OrdenTrabajoMantenimiento.class);
/* 150:155 */     from.fetch("documento", JoinType.INNER);
/* 151:    */     
/* 152:157 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 153:158 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 154:    */     
/* 155:160 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 156:    */     
/* 157:162 */     CriteriaQuery<OrdenTrabajoMantenimiento> select = criteriaQuery.select(from);
/* 158:    */     
/* 159:164 */     TypedQuery<OrdenTrabajoMantenimiento> typedQuery = this.em.createQuery(select);
/* 160:165 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 161:    */     
/* 162:167 */     return typedQuery.getResultList();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void actualizarEstado(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento, Estado estado)
/* 166:    */   {
/* 167:171 */     StringBuilder sql = new StringBuilder();
/* 168:172 */     sql.append(" UPDATE OrdenTrabajoMantenimiento ot SET ot.estado = :estado ");
/* 169:173 */     sql.append(" WHERE ot.idOrdenTrabajoMantenimiento = :idOrdenTrabajoMantenimiento ");
/* 170:    */     
/* 171:175 */     Query query = this.em.createQuery(sql.toString());
/* 172:176 */     query.setParameter("idOrdenTrabajoMantenimiento", Integer.valueOf(ordenTrabajoMantenimiento.getId()));
/* 173:177 */     query.setParameter("estado", estado);
/* 174:178 */     query.executeUpdate();
/* 175:    */   }
/* 176:    */   
/* 177:    */   @Lock(LockType.WRITE)
/* 178:    */   public void actualizarCantidadConsumidaMaterial(MaterialOrdenTrabajoMantenimiento materialOrdenTrabajo, BigDecimal cantidadDespachada, BigDecimal cantidadDevuelta)
/* 179:    */   {
/* 180:184 */     StringBuilder sql = new StringBuilder();
/* 181:185 */     sql.append(" UPDATE MaterialOrdenTrabajoMantenimiento mot SET mot.cantidadDespachada = mot.cantidadDespachada + :cantidadDespachada, ");
/* 182:186 */     sql.append(" mot.cantidadDevuelta = mot.cantidadDevuelta + :cantidadDevuelta ");
/* 183:187 */     sql.append(" WHERE mot.idMaterialOrdenTrabajoMantenimiento = :idMaterialOrdenTrabajoMantenimiento ");
/* 184:    */     
/* 185:189 */     Query query = this.em.createQuery(sql.toString());
/* 186:190 */     query.setParameter("idMaterialOrdenTrabajoMantenimiento", Integer.valueOf(materialOrdenTrabajo.getId()));
/* 187:191 */     query.setParameter("cantidadDespachada", cantidadDespachada);
/* 188:192 */     query.setParameter("cantidadDevuelta", cantidadDevuelta);
/* 189:193 */     query.executeUpdate();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<OrdenTrabajoMantenimiento> obtenerListaOrdenTrabajo(Equipo equipo, ComponenteEquipo componenteEquipo, ActividadMantenimiento actividad, Date fechaDesde, Date fechaHasta)
/* 193:    */   {
/* 194:199 */     StringBuilder sql = new StringBuilder();
/* 195:200 */     sql.append(" SELECT DISTINCT(otm) ");
/* 196:201 */     sql.append(" FROM DetalleOrdenTrabajoMantenimiento dotm ");
/* 197:202 */     sql.append(" INNER JOIN dotm.ordenTrabajoMantenimiento otm ");
/* 198:203 */     sql.append(" INNER JOIN dotm.equipo eq ");
/* 199:204 */     sql.append(" INNER JOIN dotm.componenteEquipo ceq ");
/* 200:205 */     sql.append(" INNER JOIN dotm.actividadMantenimiento act ");
/* 201:206 */     sql.append(" WHERE eq.idEquipo = :idEquipo ");
/* 202:207 */     sql.append(" AND ceq.idComponenteEquipo = :idComponenteEquipo ");
/* 203:208 */     sql.append(" AND act.idActividadMantenimiento = :idActividadMantenimiento ");
/* 204:209 */     sql.append(" AND otm.fechaMantenimiento BETWEEN :fechaDesde AND :fechaHasta ");
/* 205:    */     
/* 206:211 */     Query query = this.em.createQuery(sql.toString());
/* 207:212 */     query.setParameter("idEquipo", Integer.valueOf(equipo.getId()));
/* 208:213 */     query.setParameter("idComponenteEquipo", Integer.valueOf(componenteEquipo.getId()));
/* 209:214 */     query.setParameter("idActividadMantenimiento", Integer.valueOf(actividad.getId()));
/* 210:215 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 211:216 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 212:    */     
/* 213:218 */     return query.getResultList();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<Object[]> getReporteCostoHistoricoMantenimiento(Date fechaDesde, Date fechaHasta, CategoriaEquipo categoriaEquipo, SubcategoriaEquipo subcategoriaEquipo, Equipo equipo, ComponenteEquipo componenteEquipo, ActividadMantenimiento actividadMantenimiento, OrdenTrabajoMantenimiento otm, String tipoReporte, int agrupar)
/* 217:    */   {
/* 218:225 */     StringBuilder sql = new StringBuilder();
/* 219:227 */     if ((tipoReporte != null) && (tipoReporte.equalsIgnoreCase("costo")))
/* 220:    */     {
/* 221:228 */       System.out.println("----------------------->1");
/* 222:229 */       sql.append(" SELECT DISTINCT otm.fechaMantenimiento,otm.numero,eq.nombre,ceq.nombre,act.nombre,");
/* 223:230 */       sql.append(" dotm.horasParo,dotm.tiempoReal,");
/* 224:231 */       sql.append(" CASE WHEN pr.indicadorExterno = TRUE THEN arotm.valorFactura");
/* 225:232 */       sql.append(" WHEN pr.indicadorExterno = FALSE THEN (esp.costoHoraHombre*arotm.horasTrabajo) END,");
/* 226:233 */       sql.append(" CASE WHEN otm.indicadorAgil = TRUE THEN 'SI'");
/* 227:234 */       sql.append(" ELSE 'NO' END,");
/* 228:235 */       sql.append(" SUM(ip.costo*d.operacion*-1)");
/* 229:236 */       sql.append(" FROM DetalleOrdenTrabajoMantenimiento dotm,DetalleMovimientoInventario dmi ");
/* 230:237 */       sql.append(" INNER JOIN dotm.ordenTrabajoMantenimiento otm ");
/* 231:238 */       sql.append(" INNER JOIN dotm.equipo eq ");
/* 232:239 */       sql.append(" INNER JOIN eq.subcategoriaEquipo sce ");
/* 233:240 */       sql.append(" INNER JOIN sce.categoriaEquipo ce ");
/* 234:241 */       sql.append(" INNER JOIN dotm.componenteEquipo ceq ");
/* 235:242 */       sql.append(" INNER JOIN dotm.actividadMantenimiento act ");
/* 236:243 */       sql.append(" INNER JOIN dotm.listaResponsableOrdenTrabajoMantenimiento arotm ");
/* 237:244 */       sql.append(" INNER JOIN arotm.responsableOrdenTrabajoMantenimiento rotm ");
/* 238:245 */       sql.append(" INNER JOIN rotm.personaResponsable pr ");
/* 239:246 */       sql.append(" INNER JOIN pr.especialidad esp ");
/* 240:247 */       sql.append(" INNER JOIN dotm.listaMaterialOrdenTrabajoMantenimiento amotm ");
/* 241:248 */       sql.append(" INNER JOIN amotm.materialOrdenTrabajoMantenimiento motm ");
/* 242:    */     }
/* 243:251 */     if ((tipoReporte != null) && (tipoReporte.equalsIgnoreCase("materiales")))
/* 244:    */     {
/* 245:252 */       System.out.println("----------------------->2");
/* 246:253 */       sql.append(" SELECT DISTINCT otm.fechaMantenimiento,otm.numero,eq.nombre,m.nombre,ceq.nombre,act.nombre,");
/* 247:254 */       sql.append(" u.nombre,");
/* 248:255 */       sql.append(" motm.cantidadRequerida,");
/* 249:256 */       sql.append(" motm.cantidadDespachada,");
/* 250:257 */       sql.append(" motm.cantidadDevuelta,");
/* 251:258 */       sql.append(" amotm.cantidad,");
/* 252:259 */       sql.append(" (motm.cantidadDespachada-motm.cantidadDevuelta),");
/* 253:260 */       sql.append(" (SELECT amm.cantidad FROM ActividadMantenimientoMaterial amm");
/* 254:261 */       sql.append(" WHERE amm.actividadMantenimiento=act");
/* 255:262 */       sql.append(" AND amm.material=m)");
/* 256:263 */       sql.append(" FROM DetalleOrdenTrabajoMantenimiento dotm ");
/* 257:264 */       sql.append(" INNER JOIN dotm.ordenTrabajoMantenimiento otm ");
/* 258:265 */       sql.append(" INNER JOIN dotm.equipo eq ");
/* 259:266 */       sql.append(" INNER JOIN eq.subcategoriaEquipo sce ");
/* 260:267 */       sql.append(" INNER JOIN sce.categoriaEquipo ce ");
/* 261:268 */       sql.append(" INNER JOIN dotm.componenteEquipo ceq ");
/* 262:269 */       sql.append(" INNER JOIN dotm.actividadMantenimiento act ");
/* 263:270 */       sql.append(" LEFT JOIN dotm.listaMaterialOrdenTrabajoMantenimiento amotm ");
/* 264:271 */       sql.append(" INNER JOIN amotm.materialOrdenTrabajoMantenimiento motm ");
/* 265:272 */       sql.append(" INNER JOIN motm.material m ");
/* 266:273 */       sql.append(" INNER JOIN m.unidad u ");
/* 267:    */     }
/* 268:275 */     if ((tipoReporte != null) && (tipoReporte.equalsIgnoreCase("horas")))
/* 269:    */     {
/* 270:276 */       System.out.println("----------------------->3");
/* 271:277 */       sql.append(" SELECT DISTINCT otm.fechaMantenimiento,otm.numero,eq.nombre,ceq.nombre,act.nombre,");
/* 272:278 */       sql.append(" CONCAT(pr.nombres,' ',pr.apellidos),");
/* 273:279 */       sql.append(" arotm.horasTrabajo,");
/* 274:280 */       sql.append(" CASE WHEN pr.indicadorExterno = TRUE THEN 'SI'");
/* 275:281 */       sql.append(" ELSE 'NO' END");
/* 276:282 */       sql.append(" FROM DetalleOrdenTrabajoMantenimiento dotm ");
/* 277:283 */       sql.append(" INNER JOIN dotm.ordenTrabajoMantenimiento otm ");
/* 278:284 */       sql.append(" INNER JOIN dotm.equipo eq ");
/* 279:285 */       sql.append(" INNER JOIN eq.subcategoriaEquipo sce ");
/* 280:286 */       sql.append(" INNER JOIN sce.categoriaEquipo ce ");
/* 281:287 */       sql.append(" INNER JOIN dotm.componenteEquipo ceq ");
/* 282:288 */       sql.append(" INNER JOIN dotm.actividadMantenimiento act ");
/* 283:289 */       sql.append(" INNER JOIN dotm.listaResponsableOrdenTrabajoMantenimiento arotm ");
/* 284:290 */       sql.append(" INNER JOIN arotm.responsableOrdenTrabajoMantenimiento rotm ");
/* 285:291 */       sql.append(" INNER JOIN rotm.personaResponsable pr ");
/* 286:292 */       sql.append(" INNER JOIN pr.especialidad esp ");
/* 287:    */     }
/* 288:295 */     if ((tipoReporte != null) && (tipoReporte.equalsIgnoreCase("costo")))
/* 289:    */     {
/* 290:296 */       System.out.println("----------------------->4");
/* 291:297 */       sql.append(" INNER JOIN dmi.materialOrdenTrabajoMantenimiento dmimotm");
/* 292:298 */       sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 293:299 */       sql.append(" INNER JOIN mi.documento d ");
/* 294:300 */       sql.append(" INNER JOIN mi.ordenTrabajoMantenimiento miotm ");
/* 295:301 */       sql.append(" INNER JOIN dmi.inventarioProducto ip ");
/* 296:    */     }
/* 297:304 */     sql.append(" WHERE otm.fechaMantenimiento between :fechaDesde and :fechaHasta ");
/* 298:305 */     sql.append(" AND dotm.indicadorCerrada=TRUE ");
/* 299:306 */     sql.append(" AND otm.estado =:estado ");
/* 300:308 */     if ((tipoReporte != null) && (tipoReporte.equalsIgnoreCase("costo")))
/* 301:    */     {
/* 302:309 */       System.out.println("----------------------->5");
/* 303:310 */       sql.append(" and miotm=otm");
/* 304:311 */       sql.append(" and motm=dmimotm");
/* 305:    */     }
/* 306:313 */     if (equipo != null)
/* 307:    */     {
/* 308:314 */       System.out.println("----------------------->6");
/* 309:315 */       sql.append(" AND eq.idEquipo = :idEquipo ");
/* 310:    */     }
/* 311:317 */     if (categoriaEquipo != null)
/* 312:    */     {
/* 313:318 */       System.out.println("----------------------->7");
/* 314:319 */       sql.append(" AND ce.idCategoriaEquipo = :idCategoriaEquipo ");
/* 315:    */     }
/* 316:321 */     if (subcategoriaEquipo != null)
/* 317:    */     {
/* 318:322 */       System.out.println("----------------------->8");
/* 319:323 */       sql.append(" AND sce.idSubcategoriaEquipo = :idSubcategoriaEquipo ");
/* 320:    */     }
/* 321:325 */     if (componenteEquipo != null)
/* 322:    */     {
/* 323:326 */       System.out.println("----------------------->9");
/* 324:327 */       sql.append(" AND ceq.idComponenteEquipo = :idComponenteEquipo ");
/* 325:    */     }
/* 326:329 */     if (actividadMantenimiento != null)
/* 327:    */     {
/* 328:330 */       System.out.println("----------------------->10");
/* 329:331 */       sql.append(" AND act.idActividadMantenimiento = :idActividadMantenimiento ");
/* 330:    */     }
/* 331:333 */     if (otm != null)
/* 332:    */     {
/* 333:334 */       System.out.println("----------------------->11");
/* 334:335 */       sql.append(" AND otm.idOrdenTrabajoMantenimiento =:idOrdenTrabajoMantenimiento ");
/* 335:    */     }
/* 336:338 */     if (tipoReporte.equalsIgnoreCase("costo"))
/* 337:    */     {
/* 338:339 */       System.out.println("----------------------->12");
/* 339:340 */       sql.append(" GROUP BY otm.fechaMantenimiento,otm.numero,eq.nombre,ceq.nombre,act.nombre,");
/* 340:341 */       sql.append(" dotm.horasParo,dotm.tiempoReal,");
/* 341:342 */       sql.append(" CASE WHEN pr.indicadorExterno = TRUE THEN arotm.valorFactura");
/* 342:343 */       sql.append(" WHEN pr.indicadorExterno = FALSE THEN (esp.costoHoraHombre*arotm.horasTrabajo) END,");
/* 343:344 */       sql.append(" CASE WHEN otm.indicadorAgil = TRUE THEN 'SI'");
/* 344:345 */       sql.append(" ELSE 'NO' END");
/* 345:346 */       if (agrupar == 0) {
/* 346:347 */         sql.append(" ORDER BY eq.nombre");
/* 347:    */       }
/* 348:348 */       if (agrupar == 1) {
/* 349:349 */         sql.append(" ORDER BY otm.numero");
/* 350:    */       }
/* 351:350 */       if (agrupar == 2) {
/* 352:351 */         sql.append(" ORDER BY m.nombre");
/* 353:    */       }
/* 354:    */     }
/* 355:354 */     if (tipoReporte.equalsIgnoreCase("materiales"))
/* 356:    */     {
/* 357:355 */       if (agrupar == 0) {
/* 358:356 */         sql.append(" ORDER BY eq.nombre,otm.numero");
/* 359:    */       }
/* 360:357 */       if (agrupar == 1) {
/* 361:358 */         sql.append(" ORDER BY otm.numero,m.nombre,eq.nombre");
/* 362:    */       }
/* 363:359 */       if (agrupar == 2) {
/* 364:360 */         sql.append(" ORDER BY m.nombre");
/* 365:    */       }
/* 366:    */     }
/* 367:363 */     if (tipoReporte.equalsIgnoreCase("horas"))
/* 368:    */     {
/* 369:364 */       if (agrupar == 0) {
/* 370:365 */         sql.append(" ORDER BY eq.nombre");
/* 371:    */       }
/* 372:366 */       if (agrupar == 1) {
/* 373:367 */         sql.append(" ORDER BY otm.numero");
/* 374:    */       }
/* 375:368 */       if (agrupar == 2) {
/* 376:369 */         sql.append(" ORDER BY CONCAT(pr.nombres,' ',pr.apellidos)");
/* 377:    */       }
/* 378:    */     }
/* 379:372 */     System.out.println(sql.toString());
/* 380:    */     
/* 381:374 */     Query query = this.em.createQuery(sql.toString());
/* 382:376 */     if (equipo != null) {
/* 383:377 */       query.setParameter("idEquipo", Integer.valueOf(equipo.getIdEquipo()));
/* 384:    */     }
/* 385:379 */     if (categoriaEquipo != null) {
/* 386:380 */       query.setParameter("idCategoriaEquipo", Integer.valueOf(categoriaEquipo.getIdCategoriaEquipo()));
/* 387:    */     }
/* 388:382 */     if (subcategoriaEquipo != null) {
/* 389:383 */       query.setParameter("idSubcategoriaEquipo", Integer.valueOf(subcategoriaEquipo.getIdSubcategoriaEquipo()));
/* 390:    */     }
/* 391:385 */     if (componenteEquipo != null) {
/* 392:386 */       query.setParameter("idComponenteEquipo", Integer.valueOf(componenteEquipo.getIdComponenteEquipo()));
/* 393:    */     }
/* 394:388 */     if (actividadMantenimiento != null) {
/* 395:389 */       query.setParameter("idActividadMantenimiento", Integer.valueOf(actividadMantenimiento.getIdActividadMantenimiento()));
/* 396:    */     }
/* 397:391 */     if (otm != null) {
/* 398:392 */       query.setParameter("idOrdenTrabajoMantenimiento", Integer.valueOf(otm.getIdOrdenTrabajoMantenimiento()));
/* 399:    */     }
/* 400:394 */     query.setParameter("estado", Estado.CERRADO);
/* 401:395 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 402:396 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 403:397 */     return query.getResultList();
/* 404:    */   }
/* 405:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.OrdenTrabajoMantenimientoDao
 * JD-Core Version:    0.7.0.1
 */