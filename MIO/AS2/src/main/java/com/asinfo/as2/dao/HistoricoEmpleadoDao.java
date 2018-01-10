/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.CargoEmpleado;
/*   5:    */ import com.asinfo.as2.entities.CategoriaRubro;
/*   6:    */ import com.asinfo.as2.entities.CausaSalidaEmpleado;
/*   7:    */ import com.asinfo.as2.entities.Ciudad;
/*   8:    */ import com.asinfo.as2.entities.Departamento;
/*   9:    */ import com.asinfo.as2.entities.DetalleFiniquitoEmpleado;
/*  10:    */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*  11:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empleado;
/*  14:    */ import com.asinfo.as2.entities.Empresa;
/*  15:    */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*  16:    */ import com.asinfo.as2.entities.PagoRol;
/*  17:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  18:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*  19:    */ import com.asinfo.as2.entities.Quincena;
/*  20:    */ import com.asinfo.as2.entities.Rubro;
/*  21:    */ import com.asinfo.as2.entities.Secuencia;
/*  22:    */ import com.asinfo.as2.entities.TipoContrato;
/*  23:    */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*  24:    */ import com.asinfo.as2.entities.Vacacion;
/*  25:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  26:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  27:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.Iterator;
/*  31:    */ import java.util.List;
/*  32:    */ import java.util.Map;
/*  33:    */ import java.util.Set;
/*  34:    */ import javax.ejb.Stateless;
/*  35:    */ import javax.persistence.EntityManager;
/*  36:    */ import javax.persistence.Query;
/*  37:    */ import javax.persistence.TemporalType;
/*  38:    */ import javax.persistence.TypedQuery;
/*  39:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  40:    */ import javax.persistence.criteria.CriteriaQuery;
/*  41:    */ import javax.persistence.criteria.Expression;
/*  42:    */ import javax.persistence.criteria.Fetch;
/*  43:    */ import javax.persistence.criteria.Join;
/*  44:    */ import javax.persistence.criteria.JoinType;
/*  45:    */ import javax.persistence.criteria.Path;
/*  46:    */ import javax.persistence.criteria.Predicate;
/*  47:    */ import javax.persistence.criteria.Root;
/*  48:    */ 
/*  49:    */ @Stateless
/*  50:    */ public class HistoricoEmpleadoDao
/*  51:    */   extends AbstractDaoAS2<HistoricoEmpleado>
/*  52:    */ {
/*  53:    */   public HistoricoEmpleadoDao()
/*  54:    */   {
/*  55: 55 */     super(HistoricoEmpleado.class);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<HistoricoEmpleado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60: 66 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  61: 67 */     CriteriaQuery<HistoricoEmpleado> criteriaQuery = criteriaBuilder.createQuery(HistoricoEmpleado.class);
/*  62: 68 */     Root<HistoricoEmpleado> from = criteriaQuery.from(HistoricoEmpleado.class);
/*  63: 69 */     from.fetch("pagoRolEmpleadoFiniquito", JoinType.LEFT).fetch("pagoRol", JoinType.LEFT).fetch("asiento", JoinType.LEFT)
/*  64: 70 */       .fetch("tipoAsiento", JoinType.LEFT);
/*  65: 71 */     from.fetch("pagoRolEmpleadoFiniquito", JoinType.LEFT).fetch("empleado", JoinType.LEFT).fetch("tipoDiscapacidad", JoinType.LEFT);
/*  66: 72 */     from.fetch("empleado", JoinType.LEFT).fetch("cargoEmpleado", JoinType.LEFT);
/*  67: 73 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.INNER);
/*  68:    */     
/*  69: 75 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  70:    */     
/*  71: 77 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  72: 78 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  73:    */     
/*  74: 80 */     CriteriaQuery<HistoricoEmpleado> select = criteriaQuery.select(from);
/*  75:    */     
/*  76: 82 */     TypedQuery<HistoricoEmpleado> typedQuery = this.em.createQuery(select);
/*  77: 83 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  78:    */     
/*  79: 85 */     return typedQuery.getResultList();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<HistoricoEmpleado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  83:    */   {
/*  84: 96 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  85: 97 */     CriteriaQuery<HistoricoEmpleado> criteriaQuery = criteriaBuilder.createQuery(HistoricoEmpleado.class);
/*  86: 98 */     Root<HistoricoEmpleado> from = criteriaQuery.from(HistoricoEmpleado.class);
/*  87:    */     
/*  88:100 */     from.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  89:101 */     from.fetch("causaSalidaEmpleado", JoinType.LEFT);
/*  90:    */     
/*  91:103 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  92:104 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  93:106 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  94:    */     {
/*  95:107 */       String filterProperty = (String)it.next();
/*  96:109 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  97:    */       {
/*  98:110 */         String filterValue = (String)filters.get(filterProperty);
/*  99:112 */         if (filterProperty.startsWith("idOrganizacion"))
/* 100:    */         {
/* 101:113 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/* 102:    */         }
/* 103:114 */         else if (filterProperty.startsWith("idHistoricoEmpleado"))
/* 104:    */         {
/* 105:115 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/* 106:    */         }
/* 107:116 */         else if (filterProperty.startsWith("estadoFiniquito"))
/* 108:    */         {
/* 109:117 */           Estado estadoFiniquito = Estado.valueOf(filterValue);
/* 110:118 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), estadoFiniquito));
/* 111:    */         }
/* 112:119 */         else if (filterProperty.startsWith("fechaSalida"))
/* 113:    */         {
/* 114:120 */           Expression<Boolean> path = from.get("fechaSalida");
/* 115:121 */           if (filterValue == "null") {
/* 116:122 */             conjunction.getExpressions().add(criteriaBuilder.isNull(path));
/* 117:123 */           } else if (filterValue == "notNull") {
/* 118:124 */             conjunction.getExpressions().add(criteriaBuilder.isNotNull(path));
/* 119:    */           } else {
/* 120:126 */             conjunction.getExpressions().add(criteriaBuilder.equal(path, filterValue));
/* 121:    */           }
/* 122:    */         }
/* 123:128 */         else if (filterProperty.startsWith("empleado"))
/* 124:    */         {
/* 125:129 */           filterProperty = filterProperty.substring(filterProperty.lastIndexOf(".") + 1, filterProperty.length());
/* 126:130 */           disjunction.getExpressions().add(criteriaBuilder
/* 127:131 */             .like(criteriaBuilder.lower(from.join("empleado").get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/* 128:    */         }
/* 129:    */         else
/* 130:    */         {
/* 131:134 */           disjunction.getExpressions().add(criteriaBuilder
/* 132:135 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:141 */     if (disjunction.getExpressions().size() > 0) {
/* 137:143 */       conjunction.getExpressions().add(disjunction);
/* 138:    */     }
/* 139:146 */     if (conjunction.getExpressions().size() > 0) {
/* 140:147 */       criteriaQuery.where(conjunction);
/* 141:    */     }
/* 142:150 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 143:151 */     CriteriaQuery<HistoricoEmpleado> select = criteriaQuery.select(from);
/* 144:152 */     TypedQuery<HistoricoEmpleado> typedQuery = this.em.createQuery(select);
/* 145:    */     
/* 146:154 */     typedQuery.setMaxResults(20);
/* 147:    */     
/* 148:156 */     return typedQuery.getResultList();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public HistoricoEmpleado cargarDetalle(int idHistoricoEmpleado, boolean direcciones)
/* 152:    */   {
/* 153:172 */     HistoricoEmpleado historicoEmpleado = (HistoricoEmpleado)buscarPorId(Integer.valueOf(idHistoricoEmpleado));
/* 154:173 */     historicoEmpleado.getId();
/* 155:174 */     historicoEmpleado.getListaVacacion().size();
/* 156:175 */     historicoEmpleado.getEmpleado().getId();
/* 157:176 */     historicoEmpleado.getEmpleado().getEmpresa().getId();
/* 158:177 */     if (direcciones) {
/* 159:178 */       historicoEmpleado.getEmpleado().getEmpresa().getDirecciones().size();
/* 160:    */     } else {
/* 161:180 */       historicoEmpleado.getEmpleado().getEmpresa().setDirecciones(new ArrayList());
/* 162:    */     }
/* 163:182 */     historicoEmpleado.getListaVacacion();
/* 164:183 */     for (DetalleFiniquitoEmpleado detalleFiniquitoEmpleado : historicoEmpleado.getListaDetalleFiniquitoEmpleado()) {
/* 165:184 */       detalleFiniquitoEmpleado.getId();
/* 166:    */     }
/* 167:186 */     if (historicoEmpleado.getEmpleado().getCargoEmpleado() != null)
/* 168:    */     {
/* 169:187 */       historicoEmpleado.getEmpleado().getCargoEmpleado().getIdCargoEmpleado();
/* 170:188 */       historicoEmpleado.getEmpleado().getCargoEmpleado();
/* 171:    */     }
/* 172:191 */     if (historicoEmpleado.getEmpleado().getTipoDiscapacidad() != null) {
/* 173:192 */       historicoEmpleado.getEmpleado().getTipoDiscapacidad().getId();
/* 174:    */     }
/* 175:195 */     if (historicoEmpleado.getEmpleado().getTipoContrato() != null) {
/* 176:196 */       historicoEmpleado.getEmpleado().getTipoContrato().getId();
/* 177:    */     }
/* 178:199 */     if (historicoEmpleado.getEmpleado().getDepartamento() != null) {
/* 179:200 */       historicoEmpleado.getEmpleado().getDepartamento().getNombre();
/* 180:    */     }
/* 181:203 */     if (historicoEmpleado.getEmpleado().getCategoriaRubro() != null) {
/* 182:204 */       historicoEmpleado.getEmpleado().getCategoriaRubro().getNombre();
/* 183:    */     }
/* 184:207 */     if (historicoEmpleado.getRubroDecimoTercero() != null) {
/* 185:208 */       historicoEmpleado.getRubroDecimoTercero().getId();
/* 186:    */     }
/* 187:211 */     if (historicoEmpleado.getRubroDecimoCuarto() != null) {
/* 188:212 */       historicoEmpleado.getRubroDecimoCuarto().getId();
/* 189:    */     }
/* 190:215 */     if (historicoEmpleado.getRubroFondoReserva() != null) {
/* 191:216 */       historicoEmpleado.getRubroFondoReserva().getId();
/* 192:    */     }
/* 193:219 */     if (historicoEmpleado.getCausaSalidaEmpleado() != null) {
/* 194:220 */       historicoEmpleado.getCausaSalidaEmpleado().getIdCausaSalidaEmpleado();
/* 195:    */     }
/* 196:222 */     for (DireccionEmpresa direccionEmpresa : historicoEmpleado.getEmpleado().getEmpresa().getDirecciones())
/* 197:    */     {
/* 198:223 */       if (direccionEmpresa.getCiudad() != null) {
/* 199:224 */         direccionEmpresa.getCiudad().getId();
/* 200:    */       }
/* 201:226 */       if (direccionEmpresa.getDireccionCompleta() != null) {
/* 202:227 */         ((DireccionEmpresa)historicoEmpleado.getEmpleado().getEmpresa().getDirecciones().get(0)).getDireccionCompleta();
/* 203:    */       }
/* 204:    */     }
/* 205:230 */     if (historicoEmpleado.getPagoRolEmpleadoFiniquito() != null)
/* 206:    */     {
/* 207:231 */       historicoEmpleado.getPagoRolEmpleadoFiniquito().getIdPagoRolEmpleado();
/* 208:232 */       historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getIdPagoRol();
/* 209:233 */       historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getQuincena().getIdQuincena();
/* 210:234 */       historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getDocumento().getIdDocumento();
/* 211:235 */       historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getDocumento().getSecuencia().getIdSecuencia();
/* 212:236 */       if (historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getAsiento() != null) {
/* 213:237 */         historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getAsiento().getIdAsiento();
/* 214:    */       }
/* 215:240 */       historicoEmpleado.getPagoRolEmpleadoFiniquito().getPagoRol().getListaPagoRolEmpleado().size();
/* 216:241 */       historicoEmpleado.getEmpleado().getIdEmpleado();
/* 217:242 */       for (PagoRolEmpleadoRubro pagoRolEmpleadoRubro : historicoEmpleado.getPagoRolEmpleadoFiniquito().getListaPagoRolEmpleadoRubro())
/* 218:    */       {
/* 219:243 */         pagoRolEmpleadoRubro.getRubro().getId();
/* 220:244 */         if (pagoRolEmpleadoRubro.getRubro().getRubroPadre() != null) {
/* 221:245 */           pagoRolEmpleadoRubro.getRubro().getRubroPadre().getId();
/* 222:    */         }
/* 223:    */       }
/* 224:249 */       for (Vacacion vacacion : historicoEmpleado.getListaVacacion()) {
/* 225:250 */         vacacion.getId();
/* 226:    */       }
/* 227:    */     }
/* 228:253 */     historicoEmpleado.getListaDetalleHistoricoEmpleado().size();
/* 229:254 */     for (DetalleHistoricoEmpleado dhe : historicoEmpleado.getListaDetalleHistoricoEmpleado())
/* 230:    */     {
/* 231:255 */       dhe.getId();
/* 232:256 */       dhe.getTipoContrato().getId();
/* 233:257 */       dhe.getTipoContrato().getSecuencia().getId();
/* 234:    */     }
/* 235:260 */     return historicoEmpleado;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public HistoricoEmpleado obtenerPeriodoActivo(int idEmpleado, Date fechaInicio, Date fechaRol)
/* 239:    */     throws ExcepcionAS2Nomina
/* 240:    */   {
/* 241:273 */     StringBuilder sql = new StringBuilder();
/* 242:274 */     sql.append(" SELECT h FROM HistoricoEmpleado h ");
/* 243:275 */     sql.append(" JOIN FETCH h.empleado e");
/* 244:276 */     sql.append(" WHERE e.idEmpleado = :idEmpleado  ");
/* 245:277 */     sql.append(" AND h.fechaIngreso <= :fechaRol");
/* 246:278 */     sql.append(" AND (h.fechaSalida IS NULL OR h.fechaSalida >= :fechaInicio)");
/* 247:279 */     sql.append(" ORDER BY CASE WHEN h.fechaSalida IS NULL THEN :fechaRol ELSE h.fechaSalida END DESC");
/* 248:    */     
/* 249:281 */     Query query = this.em.createQuery(sql.toString());
/* 250:282 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 251:283 */     query.setParameter("fechaRol", fechaRol);
/* 252:284 */     query.setParameter("fechaInicio", fechaInicio);
/* 253:    */     
/* 254:286 */     List<Object> lista = query.getResultList();
/* 255:288 */     if (lista.size() > 0) {
/* 256:289 */       return (HistoricoEmpleado)lista.get(0);
/* 257:    */     }
/* 258:291 */     throw new ExcepcionAS2Nomina("msg_error_error_periodo_activo");
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<HistoricoEmpleado> obtenerListaEmpleadosConIngresoSalida(int anio, int mes)
/* 262:    */   {
/* 263:306 */     String sql = "\tSELECT h FROM HistoricoEmpleado h WHERE  ( \tYEAR(h.fechaIngreso) = :anio and MONTH(h.fechaIngreso) = :mes\t)  OR ( \tYEAR(h.fechaSalida) = :anio and MONTH(h.fechaSalida) = :mes\t)";
/* 264:    */     
/* 265:    */ 
/* 266:309 */     Query query = this.em.createQuery(sql);
/* 267:310 */     query.setParameter("anio", Integer.valueOf(anio));
/* 268:311 */     query.setParameter("mes", Integer.valueOf(mes));
/* 269:312 */     return query.getResultList();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<HistoricoEmpleado> verificacion(int idOrganizacion, Empleado empleado)
/* 273:    */   {
/* 274:318 */     StringBuilder sql = new StringBuilder();
/* 275:319 */     sql.append(" SELECT he ");
/* 276:320 */     sql.append(" FROM HistoricoEmpleado he ");
/* 277:321 */     sql.append(" INNER JOIN he.empleado e ");
/* 278:322 */     sql.append(" WHERE he.idOrganizacion = :idOrganizacion ");
/* 279:323 */     sql.append(" AND he.fechaSalida IS NULL ");
/* 280:324 */     sql.append(" AND e = :empleado ");
/* 281:325 */     Query query = this.em.createQuery(sql.toString());
/* 282:326 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 283:327 */     query.setParameter("empleado", empleado);
/* 284:    */     
/* 285:329 */     return query.getResultList();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<HistoricoEmpleado> listaHitoricoEmpleadoFiniquito(int idOrganizacion)
/* 289:    */   {
/* 290:336 */     StringBuilder sql = new StringBuilder();
/* 291:337 */     sql.append(" SELECT he ");
/* 292:338 */     sql.append(" FROM HistoricoEmpleado he ");
/* 293:339 */     sql.append(" WHERE he.estadoFiniquito = :estadoFiniquito  ");
/* 294:340 */     sql.append(" AND he.fechaSalida IS NOT NULL ");
/* 295:341 */     sql.append(" AND he.idOrganizacion = :idOrganizacion ");
/* 296:    */     
/* 297:343 */     Query query = this.em.createQuery(sql.toString());
/* 298:344 */     query.setParameter("estadoFiniquito", Estado.SIN_ESTADO);
/* 299:345 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 300:    */     
/* 301:347 */     return query.getResultList();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public Date ultimaFechaSalida(int idOrganizacion, HistoricoEmpleado historicoEmpleado)
/* 305:    */   {
/* 306:353 */     StringBuilder sql = new StringBuilder();
/* 307:354 */     sql.append(" SELECT MAX(he.fechaSalida) ");
/* 308:355 */     sql.append(" FROM HistoricoEmpleado he ");
/* 309:356 */     sql.append(" INNER JOIN he.empleado e ");
/* 310:357 */     sql.append(" WHERE he.fechaSalida IS NOT NULL ");
/* 311:358 */     sql.append(" AND he.idOrganizacion = :idOrganizacion ");
/* 312:359 */     sql.append(" AND e =:empleado ");
/* 313:360 */     if (historicoEmpleado.getId() != 0) {
/* 314:361 */       sql.append(" AND he != :historicoEmpleado");
/* 315:    */     }
/* 316:364 */     Query query = this.em.createQuery(sql.toString());
/* 317:365 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 318:366 */     query.setParameter("empleado", historicoEmpleado.getEmpleado());
/* 319:367 */     if (historicoEmpleado.getId() != 0) {
/* 320:368 */       query.setParameter("historicoEmpleado", historicoEmpleado);
/* 321:    */     }
/* 322:371 */     return (Date)query.getSingleResult();
/* 323:    */   }
/* 324:    */   
/* 325:    */   public List<HistoricoEmpleado> historicosParaUtilidad(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 326:    */   {
/* 327:377 */     StringBuilder sql = new StringBuilder();
/* 328:378 */     List<HistoricoEmpleado> lista = null;
/* 329:    */     
/* 330:380 */     sql.append(" SELECT he FROM HistoricoEmpleado he");
/* 331:381 */     sql.append(" INNER JOIN he.empleado e");
/* 332:382 */     sql.append(" WHERE he.fechaIngreso <= :fechaHasta");
/* 333:383 */     sql.append(" AND ((he.fechaSalida IS NULL OR he.fechaSalida >= :fechaDesde) AND he.fechaIngreso <= :fechaHasta )");
/* 334:384 */     sql.append(" AND he.idOrganizacion = :idOrganizacion");
/* 335:385 */     sql.append(" AND e IN (SELECT re.empleado FROM RubroEmpleado re WHERE re.rubro.idRubro =:rubroUtilidad) ");
/* 336:386 */     sql.append(" ORDER BY he.fechaIngreso ASC");
/* 337:387 */     Query query = this.em.createQuery(sql.toString());
/* 338:388 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 339:389 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 340:390 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 341:391 */     query.setParameter("rubroUtilidad", ParametrosSistema.getRubroUtilidad(idOrganizacion));
/* 342:    */     
/* 343:393 */     lista = query.getResultList();
/* 344:    */     
/* 345:395 */     return lista;
/* 346:    */   }
/* 347:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.HistoricoEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */