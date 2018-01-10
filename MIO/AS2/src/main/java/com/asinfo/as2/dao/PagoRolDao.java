/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*    4:     */ import com.asinfo.as2.clases.RelacionDependencia;
/*    5:     */ import com.asinfo.as2.entities.DetalleFiniquitoEmpleado;
/*    6:     */ import com.asinfo.as2.entities.DetalleHistoricoEmpleado;
/*    7:     */ import com.asinfo.as2.entities.Empleado;
/*    8:     */ import com.asinfo.as2.entities.HistoricoEmpleado;
/*    9:     */ import com.asinfo.as2.entities.PagoCash;
/*   10:     */ import com.asinfo.as2.entities.PagoRol;
/*   11:     */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   12:     */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   13:     */ import com.asinfo.as2.entities.Quincena;
/*   14:     */ import com.asinfo.as2.entities.Rubro;
/*   15:     */ import com.asinfo.as2.entities.RubroEmpleado;
/*   16:     */ import com.asinfo.as2.entities.Sucursal;
/*   17:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   18:     */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*   19:     */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*   20:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   21:     */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*   22:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   23:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   24:     */ import java.math.BigDecimal;
/*   25:     */ import java.util.ArrayList;
/*   26:     */ import java.util.Calendar;
/*   27:     */ import java.util.Date;
/*   28:     */ import java.util.List;
/*   29:     */ import java.util.Map;
/*   30:     */ import javax.ejb.EJB;
/*   31:     */ import javax.ejb.Stateless;
/*   32:     */ import javax.persistence.EntityManager;
/*   33:     */ import javax.persistence.NoResultException;
/*   34:     */ import javax.persistence.Query;
/*   35:     */ import javax.persistence.TemporalType;
/*   36:     */ import javax.persistence.TypedQuery;
/*   37:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   38:     */ import javax.persistence.criteria.CriteriaQuery;
/*   39:     */ import javax.persistence.criteria.Expression;
/*   40:     */ import javax.persistence.criteria.Fetch;
/*   41:     */ import javax.persistence.criteria.Join;
/*   42:     */ import javax.persistence.criteria.JoinType;
/*   43:     */ import javax.persistence.criteria.Order;
/*   44:     */ import javax.persistence.criteria.Path;
/*   45:     */ import javax.persistence.criteria.Predicate;
/*   46:     */ import javax.persistence.criteria.Root;
/*   47:     */ 
/*   48:     */ @Stateless
/*   49:     */ public class PagoRolDao
/*   50:     */   extends AbstractDaoAS2<PagoRol>
/*   51:     */ {
/*   52:     */   @EJB
/*   53:     */   private ServicioPagoRol servicioPagoRol;
/*   54:     */   @EJB
/*   55:     */   private RubroDao rubroDao;
/*   56:     */   
/*   57:     */   public PagoRolDao()
/*   58:     */   {
/*   59:  71 */     super(PagoRol.class);
/*   60:     */   }
/*   61:     */   
/*   62:     */   public List<PagoRol> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*   63:     */   {
/*   64:  88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   65:  89 */     CriteriaQuery<PagoRol> criteriaQuery = criteriaBuilder.createQuery(PagoRol.class);
/*   66:  90 */     Root<PagoRol> from = criteriaQuery.from(PagoRol.class);
/*   67:     */     
/*   68:  92 */     from.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*   69:  93 */     from.fetch("documento", JoinType.LEFT);
/*   70:  94 */     from.fetch("quincena", JoinType.LEFT);
/*   71:     */     
/*   72:  96 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*   73:  97 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*   74:  98 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*   75:     */     
/*   76: 100 */     CriteriaQuery<PagoRol> select = criteriaQuery.select(from);
/*   77: 101 */     TypedQuery<PagoRol> typedQuery = this.em.createQuery(select);
/*   78: 102 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*   79: 103 */     return typedQuery.getResultList();
/*   80:     */   }
/*   81:     */   
/*   82:     */   public List<PagoRol> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*   83:     */   {
/*   84: 114 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   85: 115 */     CriteriaQuery<PagoRol> criteriaQuery = criteriaBuilder.createQuery(PagoRol.class);
/*   86: 116 */     Root<PagoRol> from = criteriaQuery.from(PagoRol.class);
/*   87: 117 */     from.fetch("quincena", JoinType.LEFT);
/*   88:     */     
/*   89: 119 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*   90:     */     
/*   91: 121 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*   92: 122 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*   93:     */     
/*   94: 124 */     CriteriaQuery<PagoRol> select = criteriaQuery.select(from);
/*   95: 125 */     TypedQuery<PagoRol> typedQuery = this.em.createQuery(select);
/*   96:     */     
/*   97: 127 */     return typedQuery.getResultList();
/*   98:     */   }
/*   99:     */   
/*  100:     */   public PagoRol cargarDetalle(int idPagoRol)
/*  101:     */   {
/*  102: 138 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  103:     */     
/*  104:     */ 
/*  105: 141 */     CriteriaQuery<PagoRol> cqCabecera = criteriaBuilder.createQuery(PagoRol.class);
/*  106: 142 */     Root<PagoRol> fromCabecera = cqCabecera.from(PagoRol.class);
/*  107:     */     
/*  108: 144 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  109: 145 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  110:     */     
/*  111: 147 */     fromCabecera.fetch("asiento", JoinType.LEFT);
/*  112: 148 */     fromCabecera.fetch("quincena", JoinType.LEFT);
/*  113:     */     
/*  114: 150 */     Path<Integer> pathId = fromCabecera.get("idPagoRol");
/*  115: 151 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPagoRol)));
/*  116: 152 */     CriteriaQuery<PagoRol> selectPagoRol = cqCabecera.select(fromCabecera);
/*  117:     */     
/*  118: 154 */     PagoRol pagoRol = (PagoRol)this.em.createQuery(selectPagoRol).getSingleResult();
/*  119:     */     
/*  120:     */ 
/*  121: 157 */     CriteriaQuery<PagoRolEmpleado> cqDetalle = criteriaBuilder.createQuery(PagoRolEmpleado.class);
/*  122: 158 */     Root<PagoRolEmpleado> fromDetalle = cqDetalle.from(PagoRolEmpleado.class);
/*  123:     */     
/*  124: 160 */     fromDetalle.fetch("empleado", JoinType.LEFT);
/*  125:     */     
/*  126: 162 */     Path<Integer> pathIdDetalle = fromDetalle.join("pagoRol").get("idPagoRol");
/*  127: 163 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idPagoRol)));
/*  128: 164 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idPagoRolEmpleado")) });
/*  129:     */     
/*  130: 166 */     CriteriaQuery<PagoRolEmpleado> selectListPagoRolEmpleado = cqDetalle.select(fromDetalle);
/*  131:     */     
/*  132: 168 */     List<PagoRolEmpleado> listaPagoRolEmpleado = this.em.createQuery(selectListPagoRolEmpleado).getResultList();
/*  133: 169 */     pagoRol.setListaPagoRolEmpleado(listaPagoRolEmpleado);
/*  134:     */     
/*  135: 171 */     return pagoRol;
/*  136:     */   }
/*  137:     */   
/*  138:     */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorEmpleado(int idPagoRolEmpleado)
/*  139:     */   {
/*  140: 177 */     Query query = this.em.createQuery(" SELECT prer FROM PagoRolEmpleadoRubro prer JOIN FETCH prer.rubro r WHERE prer.pagoRolEmpleado.idPagoRolEmpleado= :idPagoRolEmpleado");
/*  141:     */     
/*  142: 179 */     query.setParameter("idPagoRolEmpleado", Integer.valueOf(idPagoRolEmpleado));
/*  143:     */     
/*  144: 181 */     return query.getResultList();
/*  145:     */   }
/*  146:     */   
/*  147:     */   public List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorRubro(int idPagoRol, int idRubro)
/*  148:     */   {
/*  149: 187 */     Query query = this.em.createQuery(" SELECT prer FROM PagoRolEmpleadoRubro prer join FETCH prer.rubro r WHERE r.idRubro = :idRubro and prer.pagoRolEmpleado.pagoRol.idPagoRol = :idPagoRol");
/*  150:     */     
/*  151: 189 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol)).setParameter("idRubro", Integer.valueOf(idRubro));
/*  152:     */     
/*  153: 191 */     return query.getResultList();
/*  154:     */   }
/*  155:     */   
/*  156:     */   public List<HistoricoEmpleado> actualizarPagoRolEmpleado(int idPagoRol, int idOrganizacion)
/*  157:     */   {
/*  158: 197 */     PagoRol pr = (PagoRol)buscarPorId(Integer.valueOf(idPagoRol));
/*  159: 198 */     detach(pr);
/*  160: 199 */     Date fechaInicioMesRol = FuncionesUtiles.getFechaInicioMes(pr.getFecha());
/*  161: 200 */     Date fechaRol = pr.getFecha();
/*  162:     */     
/*  163:     */ 
/*  164:     */ 
/*  165:     */ 
/*  166: 205 */     StringBuilder sql = new StringBuilder();
/*  167: 206 */     sql.append(" SELECT prer FROM PagoRolEmpleadoRubro prer ");
/*  168: 207 */     sql.append(" JOIN prer.pagoRolEmpleado pre ");
/*  169: 208 */     sql.append(" JOIN prer.rubro ru1 ");
/*  170: 209 */     sql.append(" JOIN pre.empleado e1 ");
/*  171: 210 */     sql.append(" JOIN pre.pagoRol pr ");
/*  172: 211 */     sql.append(" WHERE pr.idPagoRol= :idPagoRol");
/*  173: 212 */     sql.append(" AND prer.indicadorAutomatico = FALSE ");
/*  174: 213 */     sql.append(" AND NOT EXISTS (SELECT 1 FROM RubroEmpleado re JOIN re.empleado e2 JOIN re.rubro ru2 WHERE ru1 = ru2 AND e1 = e2)");
/*  175: 214 */     Query query = this.em.createQuery(sql.toString());
/*  176: 215 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  177: 216 */     List<PagoRolEmpleadoRubro> lista = query.getResultList();
/*  178:     */     
/*  179: 218 */     List<List<PagoRolEmpleadoRubro>> listaFinal = new ArrayList();
/*  180: 219 */     int contador = 0;
/*  181: 220 */     List<PagoRolEmpleadoRubro> listaAux = new ArrayList();
/*  182: 221 */     listaFinal.add(listaAux);
/*  183: 222 */     if (!lista.isEmpty()) {
/*  184: 223 */       if (lista.size() > 2000)
/*  185:     */       {
/*  186: 224 */         for (PagoRolEmpleadoRubro prer : lista)
/*  187:     */         {
/*  188: 225 */           if (contador == 2000)
/*  189:     */           {
/*  190: 226 */             listaAux = new ArrayList();
/*  191: 227 */             listaFinal.add(listaAux);
/*  192: 228 */             contador = 0;
/*  193:     */           }
/*  194: 230 */           contador++;
/*  195: 231 */           listaAux.add(prer);
/*  196:     */         }
/*  197: 233 */         for (List<PagoRolEmpleadoRubro> lista2 : listaFinal)
/*  198:     */         {
/*  199: 234 */           sql = new StringBuilder();
/*  200: 235 */           sql.append(" DELETE PagoRolEmpleadoRubro prer WHERE prer IN (:lista) ");
/*  201: 236 */           query = this.em.createQuery(sql.toString());
/*  202: 237 */           query.setParameter("lista", lista2);
/*  203: 238 */           query.executeUpdate();
/*  204:     */         }
/*  205:     */       }
/*  206:     */       else
/*  207:     */       {
/*  208: 242 */         sql = new StringBuilder();
/*  209: 243 */         sql.append(" DELETE PagoRolEmpleadoRubro prer WHERE prer IN (:lista) ");
/*  210: 244 */         query = this.em.createQuery(sql.toString());
/*  211: 245 */         query.setParameter("lista", lista);
/*  212: 246 */         query.executeUpdate();
/*  213:     */       }
/*  214:     */     }
/*  215: 254 */     sql = new StringBuilder();
/*  216: 255 */     sql.append(" SELECT pre FROM PagoRolEmpleado pre ");
/*  217: 256 */     sql.append(" JOIN pre.historicoEmpleado he ");
/*  218: 257 */     sql.append(" JOIN pre.pagoRol pr ");
/*  219: 258 */     sql.append(" WHERE he.fechaIngreso > pr.fecha ");
/*  220: 259 */     sql.append(" AND pr.idPagoRol = :idPagoRol");
/*  221:     */     
/*  222: 261 */     query = this.em.createQuery(sql.toString());
/*  223: 262 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  224:     */     
/*  225: 264 */     Object listaPagoRolEmpleado = query.getResultList();
/*  226:     */     
/*  227:     */ 
/*  228:     */ 
/*  229:     */ 
/*  230:     */ 
/*  231: 270 */     sql = new StringBuilder();
/*  232: 271 */     sql.append(" SELECT pre FROM PagoRolEmpleado pre ");
/*  233: 272 */     sql.append(" JOIN pre.historicoEmpleado he ");
/*  234: 273 */     sql.append(" JOIN pre.pagoRol pr ");
/*  235: 274 */     sql.append(" WHERE he.fechaSalida IS NOT NULL AND he.fechaSalida <= pr.fecha AND he.indicadorFiniquito = true ");
/*  236: 275 */     sql.append(" AND pr.idPagoRol = :idPagoRol");
/*  237:     */     
/*  238: 277 */     query = this.em.createQuery(sql.toString());
/*  239: 278 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  240:     */     
/*  241: 280 */     ((List)listaPagoRolEmpleado).addAll(query.getResultList());
/*  242:     */     
/*  243:     */ 
/*  244:     */ 
/*  245:     */ 
/*  246: 285 */     sql = new StringBuilder();
/*  247: 286 */     sql.append(" SELECT pre FROM PagoRolEmpleado pre ");
/*  248: 287 */     sql.append(" JOIN pre.historicoEmpleado he ");
/*  249: 288 */     sql.append(" JOIN pre.pagoRol pr ");
/*  250: 289 */     sql.append(" WHERE he.fechaSalida IS NOT NULL AND he.fechaSalida < :fechaInicioRol ");
/*  251: 290 */     sql.append(" AND pr.idPagoRol = :idPagoRol");
/*  252:     */     
/*  253: 292 */     query = this.em.createQuery(sql.toString());
/*  254:     */     
/*  255: 294 */     query.setParameter("fechaInicioRol", fechaInicioMesRol);
/*  256: 295 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  257: 296 */     ((List)listaPagoRolEmpleado).addAll(query.getResultList());
/*  258: 298 */     if (!((List)listaPagoRolEmpleado).isEmpty())
/*  259:     */     {
/*  260: 303 */       StringBuilder sql1 = new StringBuilder();
/*  261: 304 */       sql1.append(" SELECT pre FROM PagoRolEmpleado pre ");
/*  262: 305 */       sql1.append(" WHERE pre IN (:listaPagoRolEmpleado) AND pre NOT IN ");
/*  263: 306 */       sql1.append(" (SELECT pre1 FROM PagoRolEmpleadoRubro prer INNER JOIN prer.pagoRolEmpleado pre1 ");
/*  264: 307 */       sql1.append(" WHERE prer.indicadorAutomatico = true ");
/*  265: 308 */       sql1.append(" AND pre1 IN (:listaPagoRolEmpleado)) ");
/*  266:     */       
/*  267: 310 */       Query query1 = this.em.createQuery(sql1.toString());
/*  268: 311 */       query1.setParameter("listaPagoRolEmpleado", listaPagoRolEmpleado);
/*  269:     */       
/*  270: 313 */       List<PagoRolEmpleado> listaPagoRolEmpleadoEliminar = query1.getResultList();
/*  271:     */       
/*  272:     */ 
/*  273: 316 */       sql = new StringBuilder();
/*  274: 317 */       sql.append(" DELETE FROM PagoRolEmpleadoRubro prer ");
/*  275: 318 */       sql.append(" WHERE prer.indicadorAutomatico = FALSE ");
/*  276: 319 */       sql.append(" and prer.pagoRolEmpleado IN (:listaPagoRolEmpleado) ");
/*  277:     */       
/*  278: 321 */       query = this.em.createQuery(sql.toString());
/*  279: 322 */       query.setParameter("listaPagoRolEmpleado", listaPagoRolEmpleado);
/*  280:     */       
/*  281: 324 */       query.executeUpdate();
/*  282: 327 */       if (!listaPagoRolEmpleadoEliminar.isEmpty())
/*  283:     */       {
/*  284: 328 */         sql = new StringBuilder();
/*  285: 329 */         sql.append(" UPDATE DetallePrestamo dp SET saldoCapitalCuota = capitalCuota, capitalCuota = 0 ");
/*  286: 330 */         sql.append(" WHERE dp in (select dpcp.detallePrestamo from DetallePagoCuotaPrestamo dpcp ");
/*  287: 331 */         sql.append(" WHERE dpcp.pagoRolEmpleado IN (:listaPagoRolEmpleado)) ");
/*  288:     */         
/*  289: 333 */         query = this.em.createQuery(sql.toString());
/*  290: 334 */         query.setParameter("listaPagoRolEmpleado", listaPagoRolEmpleadoEliminar);
/*  291: 335 */         query.executeUpdate();
/*  292:     */         
/*  293:     */ 
/*  294: 338 */         sql = new StringBuilder();
/*  295: 339 */         sql.append(" DELETE FROM DetallePagoCuotaPrestamo dpcp ");
/*  296: 340 */         sql.append(" WHERE dpcp.pagoRolEmpleado IN (:listaPagoRolEmpleado) ");
/*  297:     */         
/*  298: 342 */         query = this.em.createQuery(sql.toString());
/*  299: 343 */         query.setParameter("listaPagoRolEmpleado", listaPagoRolEmpleadoEliminar);
/*  300: 344 */         query.executeUpdate();
/*  301:     */         
/*  302:     */ 
/*  303: 347 */         sql = new StringBuilder();
/*  304: 348 */         sql.append(" DELETE FROM PagoRolEmpleado pre ");
/*  305: 349 */         sql.append(" WHERE pre IN (:listaPagoRolEmpleado) ");
/*  306:     */         
/*  307: 351 */         query = this.em.createQuery(sql.toString());
/*  308: 352 */         query.setParameter("listaPagoRolEmpleado", listaPagoRolEmpleadoEliminar);
/*  309:     */         
/*  310: 354 */         query.executeUpdate();
/*  311:     */       }
/*  312:     */     }
/*  313: 361 */     StringBuilder sql3 = new StringBuilder();
/*  314: 362 */     sql3.append("SELECT he FROM HistoricoEmpleado he ");
/*  315: 363 */     sql3.append(" JOIN FETCH he.empleado e ");
/*  316: 364 */     sql3.append(" LEFT JOIN FETCH e.departamento d ");
/*  317: 365 */     sql3.append(" LEFT JOIN FETCH e.centroCosto cc ");
/*  318: 366 */     sql3.append(" WHERE he.idOrganizacion = :idOrganizacion ");
/*  319: 367 */     sql3.append(" AND he NOT IN ");
/*  320: 368 */     sql3.append("\t( ");
/*  321: 369 */     sql3.append("\t\tSELECT pre.historicoEmpleado FROM PagoRolEmpleado pre JOIN pre.historicoEmpleado he WHERE pre.pagoRol.idPagoRol = :idPagoRol ");
/*  322: 370 */     sql3.append(" \t)  ");
/*  323: 371 */     sql3.append(" AND :fechaRol >= he.fechaIngreso AND ");
/*  324: 372 */     sql3.append("\t( ");
/*  325: 373 */     sql3.append("\t\the.fechaSalida IS NULL OR he.fechaSalida > :fechaRol OR (he.fechaSalida = :fechaRol AND he.indicadorFiniquito = false)  OR (he.fechaSalida >= :fechaInicioRol AND he.indicadorFiniquito = false) ");
/*  326: 374 */     sql3.append("\t)");
/*  327:     */     
/*  328: 376 */     query = this.em.createQuery(sql3.toString());
/*  329: 377 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  330: 378 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  331: 379 */     query.setParameter("fechaInicioRol", fechaInicioMesRol);
/*  332: 380 */     query.setParameter("fechaRol", fechaRol);
/*  333: 381 */     return query.getResultList();
/*  334:     */   }
/*  335:     */   
/*  336:     */   public List<RubroEmpleado> actualizarPagoRolEmpleadoRubro(PagoRol pagoRol)
/*  337:     */   {
/*  338: 388 */     StringBuilder sql = new StringBuilder();
/*  339: 389 */     sql.append(" DELETE FROM PagoRolEmpleadoRubro prer ");
/*  340: 390 */     sql.append(" WHERE prer.indicadorAutomatico = FALSE ");
/*  341: 391 */     sql.append(" AND EXISTS ");
/*  342: 392 */     sql.append(" ( ");
/*  343: 393 */     sql.append("  \tSELECT prer1 ");
/*  344: 394 */     sql.append(" \tFROM PagoRolEmpleadoRubro prer1  ");
/*  345: 395 */     sql.append(" \tJOIN prer1.pagoRolEmpleado pre1 ");
/*  346: 396 */     sql.append(" \tJOIN pre1.pagoRol pr ");
/*  347: 397 */     sql.append(" \tWHERE pr.idPagoRol = :idPagoRol ");
/*  348: 398 */     sql.append(" \tAND prer.idPagoRolEmpleadoRubro = prer1.idPagoRolEmpleadoRubro ");
/*  349: 399 */     sql.append(" )\t");
/*  350: 400 */     sql.append(" AND NOT EXISTS ");
/*  351: 401 */     sql.append(" ( ");
/*  352: 402 */     sql.append(" \tSELECT re FROM RubroEmpleado re");
/*  353: 403 */     sql.append(" \tJOIN re.rubro r");
/*  354: 404 */     sql.append(" \tJOIN re.empleado e");
/*  355: 405 */     sql.append(" \tWHERE e.idEmpleado = prer.pagoRolEmpleado.empleado.idEmpleado");
/*  356: 406 */     sql.append(" \tAND r = prer.rubro");
/*  357: 407 */     sql.append(" \tAND re.rubro.quincena.idQuincena=:idQuincena AND (r.mesPago = 0 OR r.mesPago = prer.pagoRolEmpleado.pagoRol.mes )");
/*  358: 408 */     sql.append(" )");
/*  359:     */     
/*  360: 410 */     Query query = this.em.createQuery(sql.toString());
/*  361:     */     
/*  362: 412 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getId()));
/*  363: 413 */     query.setParameter("idQuincena", Integer.valueOf(pagoRol.getQuincena().getId()));
/*  364: 414 */     query.executeUpdate();
/*  365:     */     
/*  366: 416 */     sql = new StringBuilder();
/*  367: 417 */     sql.append(" SELECT re ");
/*  368: 418 */     sql.append(" FROM RubroEmpleado re ");
/*  369: 419 */     sql.append(" JOIN FETCH re.rubro r ");
/*  370: 420 */     sql.append(" JOIN FETCH re.empleado ee ");
/*  371: 421 */     sql.append(" LEFT JOIN r.quincena q ");
/*  372: 422 */     sql.append(" WHERE q.idQuincena=:idQuincena ");
/*  373: 423 */     sql.append(" AND EXISTS ");
/*  374: 424 */     sql.append(" \t( ");
/*  375: 425 */     sql.append(" \t\tselect e from PagoRolEmpleado pre ");
/*  376: 426 */     sql.append(" \t\tJOIN pre.empleado e ");
/*  377: 427 */     sql.append(" \t\tJOIN pre.pagoRol pr ");
/*  378: 428 */     sql.append(" \t\tWHERE pr.idPagoRol = :idPagoRol AND e.idEmpleado = ee.idEmpleado");
/*  379: 429 */     sql.append(" \t)");
/*  380: 430 */     query = this.em.createQuery(sql.toString());
/*  381: 431 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getId()));
/*  382: 432 */     query.setParameter("idQuincena", Integer.valueOf(pagoRol.getQuincena().getId()));
/*  383: 433 */     return query.getResultList();
/*  384:     */   }
/*  385:     */   
/*  386:     */   public List getDatosArchivoVariacionesIESS(PagoRol pagoRol, Sucursal sucursal)
/*  387:     */   {
/*  388: 439 */     int idRubroSueldo = ParametrosSistema.getRubroSalarioUnificado(pagoRol.getIdOrganizacion()).intValue();
/*  389: 440 */     StringBuilder sql = new StringBuilder();
/*  390: 441 */     sql.append("SELECT em.identificacion, SUM(prer.valor) ");
/*  391: 442 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  392: 443 */     sql.append(" JOIN prer.pagoRolEmpleado pre ");
/*  393: 444 */     sql.append(" JOIN pre.pagoRol pr ");
/*  394: 445 */     sql.append(" JOIN pre.empleado e ");
/*  395: 446 */     sql.append(" JOIN prer.rubro r ");
/*  396: 447 */     sql.append(" JOIN e.empresa em ");
/*  397: 448 */     sql.append(" WHERE pr.idPagoRol = :idPagoRol AND prer.indicadorCalculoIESS = true AND r.idRubro != :idRubroSueldo and e.sucursal = :sucursal ");
/*  398: 449 */     sql.append(" GROUP BY em.identificacion ");
/*  399: 450 */     sql.append(" HAVING SUM(prer.valor) > 0 ");
/*  400:     */     
/*  401: 452 */     Query query = this.em.createQuery(sql.toString());
/*  402: 453 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/*  403: 454 */     query.setParameter("idRubroSueldo", Integer.valueOf(idRubroSueldo));
/*  404: 455 */     query.setParameter("sucursal", sucursal);
/*  405:     */     
/*  406: 457 */     return query.getResultList();
/*  407:     */   }
/*  408:     */   
/*  409:     */   public List<Object[]> getArchivoDecimocuarto(int idOrganizacion, Date fechaHasta, Date fechaDesde)
/*  410:     */   {
/*  411: 463 */     StringBuilder sql = new StringBuilder();
/*  412: 464 */     sql.append("SELECT  em.identificacion, ");
/*  413: 465 */     sql.append(" e.nombres, ");
/*  414: 466 */     sql.append(" e.apellidos, ");
/*  415: 467 */     sql.append(" CASE WHEN e.genero = 1 THEN 'F' ELSE 'M' END, ");
/*  416: 468 */     sql.append(" coalesce(e.codigoSectorial,' '),");
/*  417: 469 */     sql.append(" 0, ");
/*  418: 470 */     sql.append(" 'P', ");
/*  419: 471 */     sql.append(" CASE WHEN dhe.horasSemana < 40 THEN 'X' ELSE '' END, ");
/*  420: 472 */     sql.append(" CASE WHEN dhe.horasSemana < 40 THEN coalesce(dhe.horasSemana, 0) ELSE 0 END, ");
/*  421: 473 */     sql.append(" CASE WHEN e.porcentajeDiscapacidad > 0 THEN 'X' ELSE '' END, ");
/*  422: 474 */     sql.append(" '', ");
/*  423: 475 */     sql.append(" '', ");
/*  424: 476 */     sql.append(" 'X', ");
/*  425: 477 */     sql.append(" dhe.idDetalleHistoricoEmpleado, ");
/*  426: 478 */     sql.append(" tc.contratoEventual, ");
/*  427: 479 */     sql.append(" dhe.fechaInicio, ");
/*  428: 480 */     sql.append(" dhe.fechaFin, ");
/*  429: 481 */     sql.append(" he.idHistoricoEmpleado, ");
/*  430: 482 */     sql.append(" e.idEmpleado ");
/*  431: 483 */     sql.append(" FROM DetalleHistoricoEmpleado dhe ");
/*  432: 484 */     sql.append(" JOIN dhe.historicoEmpleado he ");
/*  433: 485 */     sql.append(" LEFT JOIN dhe.tipoContrato tc ");
/*  434: 486 */     sql.append(" LEFT JOIN he.empleado e ");
/*  435: 487 */     sql.append(" LEFT JOIN e.empresa em ");
/*  436: 488 */     sql.append(" WHERE he.idOrganizacion =:idOrganizacion ");
/*  437: 489 */     sql.append(" AND he.fechaIngreso <:fechaHasta AND (he.fechaSalida is null or he.fechaSalida > :fechaHasta) ");
/*  438: 490 */     sql.append(" AND (dhe.fechaFin is null or dhe.fechaFin > :fechaDesde) AND dhe.fechaInicio <= :fechaHasta)) ");
/*  439:     */     
/*  440: 492 */     Query query = this.em.createQuery(sql.toString());
/*  441: 493 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  442: 494 */     query.setParameter("fechaHasta", fechaHasta);
/*  443: 495 */     query.setParameter("fechaDesde", fechaDesde);
/*  444:     */     
/*  445: 497 */     return query.getResultList();
/*  446:     */   }
/*  447:     */   
/*  448:     */   public long getDiasFaltaContratoEventual(int idHistoricoEmpleado, Date fechaDesde, Date fechaHasta)
/*  449:     */   {
/*  450: 503 */     StringBuilder sql = new StringBuilder();
/*  451: 504 */     sql.append(" SELECT COALESCE(SUM(pre.diasFalta),0) ");
/*  452: 505 */     sql.append(" FROM PagoRolEmpleado pre ");
/*  453: 506 */     sql.append(" JOIN pre.historicoEmpleado he ");
/*  454: 507 */     sql.append(" JOIN pre.pagoRol pr ");
/*  455: 508 */     sql.append(" WHERE he.idHistoricoEmpleado=:idHistoricoEmpleado ");
/*  456: 509 */     sql.append(" AND pr.fecha between :fechaDesde AND :fechaHasta ");
/*  457:     */     
/*  458: 511 */     Query query = this.em.createQuery(sql.toString());
/*  459: 512 */     query.setParameter("idHistoricoEmpleado", Integer.valueOf(idHistoricoEmpleado));
/*  460: 513 */     query.setParameter("fechaDesde", fechaDesde);
/*  461: 514 */     query.setParameter("fechaHasta", fechaHasta);
/*  462:     */     
/*  463: 516 */     return ((Long)query.getSingleResult()).longValue();
/*  464:     */   }
/*  465:     */   
/*  466:     */   public List<Object[]> getTotalGanado(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/*  467:     */   {
/*  468: 551 */     StringBuilder sql = new StringBuilder();
/*  469: 552 */     sql.append(" SELECT e.idEmpleado, SUM (prer.valor)");
/*  470: 553 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  471: 554 */     sql.append(" JOIN prer.rubro r ");
/*  472: 555 */     sql.append(" JOIN prer.pagoRolEmpleado pre ");
/*  473: 556 */     sql.append(" JOIN pre.pagoRol pr ");
/*  474: 557 */     sql.append(" JOIN pre.empleado e ");
/*  475: 558 */     sql.append(" WHERE ((r.operacion =1 AND r.indicadorCalculoIESS = true) AND (pr.fecha between :fechaDesde AND :fechaHasta) AND e.idOrganizacion = :idOrganizacion)");
/*  476: 559 */     sql.append(" GROUP BY e.idEmpleado");
/*  477:     */     
/*  478: 561 */     Query query = this.em.createQuery(sql.toString());
/*  479: 562 */     query.setParameter("fechaDesde", fechaDesde);
/*  480: 563 */     query.setParameter("fechaHasta", fechaHasta);
/*  481: 564 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  482:     */     
/*  483: 566 */     return query.getResultList();
/*  484:     */   }
/*  485:     */   
/*  486:     */   public List<Object[]> getArchivoDecimotercero(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  487:     */   {
/*  488: 574 */     Date fechaHasta2 = FuncionesUtiles.sumarFechaMeses(fechaHasta, -1);
/*  489: 575 */     fechaHasta2 = FuncionesUtiles.getFechaFinMes(fechaHasta2);
/*  490:     */     
/*  491: 577 */     StringBuilder sql = new StringBuilder();
/*  492: 578 */     sql.append(" SELECT em.identificacion, e.nombres, e.apellidos, CASE WHEN e.genero = 1 THEN 'F' ELSE 'M' END, ");
/*  493: 579 */     sql.append(" COALESCE(e.codigoSectorial,''), SUM(prer.valor), SUM(prer.tiempo), 'P', '','', ");
/*  494: 580 */     sql.append(" CASE WHEN e.porcentajeDiscapacidad > 0 THEN 'x' ELSE '' END, '', ");
/*  495: 581 */     sql.append(" CASE WHEN MONTH(MAX(CASE WHEN pr.fecha <= :fechaHasta2 THEN pr.fecha ELSE  '01/01/1900' END )) = MONTH(CAST(:fechaHasta2 AS date)) THEN 'x' ELSE '' END, ");
/*  496: 582 */     sql.append(" CASE WHEN MONTH(MAX(CASE WHEN pr.fecha <= :fechaHasta2 THEN pr.fecha ELSE  '01/01/1900' END )) = MONTH(CAST(:fechaHasta2 AS date)) THEN MIN(pr.fecha) ELSE '01/01/1900' END, ");
/*  497: 583 */     sql.append(" CASE WHEN MONTH(MAX(CASE WHEN pr.fecha <= :fechaHasta2 THEN pr.fecha ELSE  '01/01/1900' END )) < MONTH(CAST(:fechaHasta2 AS date)) THEN MAX(pr.fecha) ELSE '01/01/1900' END, ");
/*  498: 584 */     sql.append(" MAX(he.idHistoricoEmpleado), e.idEmpleado ");
/*  499: 585 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  500: 586 */     sql.append(" LEFT JOIN prer.rubro r ");
/*  501: 587 */     sql.append(" LEFT JOIN prer.pagoRolEmpleado pre ");
/*  502: 588 */     sql.append(" LEFT JOIN pre.historicoEmpleado he ");
/*  503: 589 */     sql.append(" INNER JOIN he.listaDetalleHistoricoEmpleado dhe ");
/*  504: 590 */     sql.append(" LEFT JOIN pre.pagoRol pr ");
/*  505: 591 */     sql.append(" LEFT JOIN pre.empleado e ");
/*  506: 592 */     sql.append(" LEFT JOIN e.empresa em ");
/*  507: 593 */     sql.append(" WHERE pr.idOrganizacion = :idOrganizacion ");
/*  508: 594 */     sql.append(" AND r.tipo = :tipo ");
/*  509: 595 */     sql.append(" AND he.fechaSalida is NULL ");
/*  510: 596 */     sql.append(" AND (dhe.fechaFin IS NULL OR dhe.fechaFin > :fechaHasta) ");
/*  511: 597 */     sql.append(" AND pr.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  512: 598 */     sql.append(" AND e.activo = true ");
/*  513: 599 */     sql.append(" GROUP BY em.identificacion, e.nombres, e.apellidos, e.codigoSectorial, e.genero, e.porcentajeDiscapacidad, e.idEmpleado ");
/*  514: 600 */     Query query = this.em.createQuery(sql.toString());
/*  515: 601 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  516: 602 */     query.setParameter("tipo", TipoRubroEnum.DECIMO_TERCERO);
/*  517: 603 */     query.setParameter("fechaHasta2", fechaHasta2);
/*  518: 604 */     query.setParameter("fechaDesde", fechaDesde);
/*  519: 605 */     query.setParameter("fechaHasta", fechaHasta);
/*  520: 606 */     return query.getResultList();
/*  521:     */   }
/*  522:     */   
/*  523:     */   public List<DetalleHistoricoEmpleado> getContratosEmpleado(int idEmpleado)
/*  524:     */   {
/*  525: 611 */     StringBuilder sb = new StringBuilder();
/*  526: 612 */     sb.append(" SELECT dhe FROM DetalleHistoricoEmpleado dhe ");
/*  527: 613 */     sb.append(" JOIN dhe.historicoEmpleado he ");
/*  528: 614 */     sb.append(" WHERE he.empleado.idEmpleado = :idEmpleado ");
/*  529: 615 */     sb.append(" ORDER BY dhe.fechaInicio DESC ");
/*  530: 616 */     Query query = this.em.createQuery(sb.toString());
/*  531: 617 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  532:     */     
/*  533: 619 */     return query.getResultList();
/*  534:     */   }
/*  535:     */   
/*  536:     */   public BigDecimal getValorRetenido(int idEmpleado, int idPagoRol)
/*  537:     */   {
/*  538: 623 */     StringBuilder sb = new StringBuilder();
/*  539: 624 */     sb.append(" SELECT COALESCE(SUM(prer.valor),0.00) FROM PagoRolEmpleadoRubro prer ");
/*  540: 625 */     sb.append(" JOIN prer.pagoRolEmpleado pre ");
/*  541: 626 */     sb.append(" JOIN pre.pagoRol pr ");
/*  542: 627 */     sb.append(" JOIN prer.rubro r ");
/*  543: 628 */     sb.append(" WHERE pr.idPagoRol = :idPagoRol ");
/*  544: 629 */     sb.append(" AND pre.empleado.idEmpleado = :idEmpleado ");
/*  545: 630 */     sb.append(" AND r.tipo = :tipo ");
/*  546: 631 */     Query query = this.em.createQuery(sb.toString());
/*  547: 632 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  548: 633 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  549: 634 */     query.setParameter("tipo", TipoRubroEnum.RETENCION_JUDICIAL);
/*  550:     */     
/*  551: 636 */     return (BigDecimal)query.getSingleResult();
/*  552:     */   }
/*  553:     */   
/*  554:     */   public List<Object[]> getValoresRetenidos(int idPagoRol)
/*  555:     */   {
/*  556: 640 */     StringBuilder sb = new StringBuilder();
/*  557: 641 */     sb.append(" SELECT he.idHistoricoEmpleado, COALESCE(SUM(prer.valor),0.00) ");
/*  558: 642 */     sb.append(" FROM PagoRolEmpleadoRubro prer ");
/*  559: 643 */     sb.append(" JOIN prer.pagoRolEmpleado pre ");
/*  560: 644 */     sb.append(" JOIN pre.historicoEmpleado he ");
/*  561: 645 */     sb.append(" JOIN pre.pagoRol pr ");
/*  562: 646 */     sb.append(" JOIN prer.rubro r ");
/*  563: 647 */     sb.append(" WHERE pr.idPagoRol = :idPagoRol ");
/*  564: 648 */     sb.append(" AND r.tipo = :tipo ");
/*  565: 649 */     sb.append(" GROUP BY he.idHistoricoEmpleado ");
/*  566: 650 */     Query query = this.em.createQuery(sb.toString());
/*  567: 651 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  568: 652 */     query.setParameter("tipo", TipoRubroEnum.RETENCION_JUDICIAL);
/*  569:     */     
/*  570: 654 */     return query.getResultList();
/*  571:     */   }
/*  572:     */   
/*  573:     */   public List<Object[]> getArchivoUtilidades(int idPagoRol, int idOrganizacion)
/*  574:     */   {
/*  575: 660 */     StringBuilder sql = new StringBuilder();
/*  576: 661 */     sql.append(" SELECT coalesce(em.identificacion,ud.identificacion), coalesce(e.nombres,ud.nombres), coalesce(e.apellidos,ud.apellidos), ");
/*  577:     */     
/*  578: 663 */     sql.append(" CASE WHEN e.genero =1 THEN 'F' ELSE 'M' END, ");
/*  579: 664 */     sql.append(" coalesce(ud.codigoSectorial,e.codigoSectorial,' '),ud.cargasFamiliares, ud.diasRealesTrabajados, ");
/*  580: 665 */     sql.append(" CASE WHEN e.indicadorPagoCash = true THEN 'A' ELSE 'P' END , ' ', ' ', CASE WHEN e.porcentajeDiscapacidad > 0.00 THEN 'x' ELSE ' ' END ,");
/*  581:     */     
/*  582: 667 */     sql.append(" coalesce( ud.rucEmpresaComplementaria, '' ), 0.00, 0.00, 0.00,  0.00, 0.00, 0.00, 0.00, 0.00,coalesce(ud.retencionJudicial,0.00 ) , 0.00,");
/*  583:     */     
/*  584: 669 */     sql.append(" ' ',   'P', coalesce(e.idEmpleado,0) ");
/*  585: 670 */     sql.append(" FROM DetalleUtilidad ud ");
/*  586: 671 */     sql.append(" LEFT JOIN ud.empleado e");
/*  587: 672 */     sql.append(" LEFT JOIN e.empresa emp");
/*  588: 673 */     sql.append(" LEFT JOIN ud.utilidad u ");
/*  589: 674 */     sql.append(" LEFT JOIN u.pagoRol pr ");
/*  590: 675 */     sql.append(" LEFT JOIN e.empresa em ");
/*  591: 676 */     sql.append(" WHERE pr.idPagoRol = :idPagoRol   AND pr.idOrganizacion =:idOrganizacion");
/*  592: 677 */     sql.append(" GROUP BY coalesce(em.identificacion,ud.identificacion), coalesce(e.nombres,ud.nombres), coalesce(e.apellidos,ud.apellidos),coalesce( ud.rucEmpresaComplementaria, '' ), e.genero,ud.codigoSectorial ,e.codigoSectorial, ud.cargasFamiliares, ud.diasRealesTrabajados, e.indicadorPagoCash, e.idEmpleado, e.porcentajeDiscapacidad, ud.retencionJudicial ");
/*  593:     */     
/*  594:     */ 
/*  595:     */ 
/*  596: 681 */     Query query = this.em.createQuery(sql.toString());
/*  597: 682 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  598: 683 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  599:     */     
/*  600: 685 */     return query.getResultList();
/*  601:     */   }
/*  602:     */   
/*  603:     */   public List<Object[]> getValorPagoRubros(int idOrganizacion, int anio)
/*  604:     */   {
/*  605: 691 */     StringBuilder sql = new StringBuilder();
/*  606: 692 */     sql.append("  SELECT e.idEmpleado, ");
/*  607: 693 */     sql.append(" coalesce(SUM( CASE WHEN r.formula = 'g' THEN prer.valor ELSE 0 END ),0), ");
/*  608: 694 */     sql.append(" coalesce(SUM( CASE WHEN r.formula = 'f' THEN prer.valor ELSE 0 END),0), ");
/*  609: 695 */     sql.append(" coalesce(SUM( CASE WHEN r.tipo = :tipoRubroFondoReserva THEN prer.valor ELSE 0 END),0), ");
/*  610: 696 */     sql.append(" coalesce(SUM( CASE WHEN (r.indicadorCalculoIESS = true  AND r.operacion = 1  ) THEN prer.valor ELSE 0 END),0) ");
/*  611: 697 */     sql.append("  FROM PagoRolEmpleadoRubro prer ");
/*  612: 698 */     sql.append("  LEFT JOIN prer.pagoRolEmpleado pre ");
/*  613: 699 */     sql.append("  LEFT JOIN pre.empleado e ");
/*  614: 700 */     sql.append("  LEFT JOIN pre.pagoRol pr ");
/*  615: 701 */     sql.append("  LEFT JOIN prer.rubro r ");
/*  616: 702 */     sql.append("  WHERE pr.anio =:anio AND pr.idOrganizacion = :idOrganizacion");
/*  617: 703 */     sql.append("  GROUP BY e.idEmpleado");
/*  618:     */     
/*  619: 705 */     Query query = this.em.createQuery(sql.toString());
/*  620: 706 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  621:     */     
/*  622:     */ 
/*  623: 709 */     query.setParameter("tipoRubroFondoReserva", TipoRubroEnum.FONDOS_RESERVA);
/*  624: 710 */     query.setParameter("anio", Integer.valueOf(anio));
/*  625:     */     
/*  626: 712 */     return query.getResultList();
/*  627:     */   }
/*  628:     */   
/*  629:     */   public int getRubroID(String formula)
/*  630:     */   {
/*  631:     */     try
/*  632:     */     {
/*  633: 719 */       StringBuilder sql = new StringBuilder();
/*  634: 720 */       sql.append("SELECT r.idRubro ");
/*  635: 721 */       sql.append(" FROM Rubro r ");
/*  636: 722 */       sql.append(" WHERE r.formula = :formula AND r.indicadorProvision = 1");
/*  637:     */       
/*  638: 724 */       Query query = this.em.createQuery(sql.toString());
/*  639: 725 */       query.setParameter("formula", formula);
/*  640:     */       
/*  641: 727 */       return ((Integer)query.getSingleResult()).intValue();
/*  642:     */     }
/*  643:     */     catch (Exception e) {}
/*  644: 730 */     return -1;
/*  645:     */   }
/*  646:     */   
/*  647:     */   public List<Rubro> getRubroDecimoTercero(int idOrganizacion)
/*  648:     */   {
/*  649: 737 */     StringBuilder sql = new StringBuilder();
/*  650: 738 */     sql.append(" SELECT r ");
/*  651: 739 */     sql.append(" FROM Rubro r ");
/*  652: 740 */     sql.append(" WHERE r.formula = :formula AND r.idOrganizacion = :idOrganizacion ");
/*  653: 741 */     sql.append(" AND r.rubroPadre IS NULL ");
/*  654: 742 */     Query query = this.em.createQuery(sql.toString());
/*  655: 743 */     query.setParameter("formula", "b");
/*  656: 744 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  657: 745 */     List<Rubro> listaRubros = query.getResultList();
/*  658: 746 */     for (Rubro rubro : listaRubros) {
/*  659: 747 */       rubro.getQuincena().getIdQuincena();
/*  660:     */     }
/*  661: 750 */     return listaRubros;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public List<Rubro> getRubrosDecimoTerceroMensual(int idOrganizacion, Rubro rubroDT)
/*  665:     */   {
/*  666: 755 */     StringBuilder sql = new StringBuilder();
/*  667: 756 */     sql.append(" SELECT r ");
/*  668: 757 */     sql.append(" FROM Rubro r ");
/*  669: 758 */     sql.append(" WHERE r.tipo = :tipo AND r.idOrganizacion = :idOrganizacion ");
/*  670: 759 */     sql.append(" AND r.idRubro <> :idRubroDT ");
/*  671: 760 */     Query query = this.em.createQuery(sql.toString());
/*  672: 761 */     query.setParameter("tipo", TipoRubroEnum.DECIMO_TERCERO);
/*  673: 762 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  674: 763 */     query.setParameter("idRubroDT", Integer.valueOf(rubroDT.getId()));
/*  675:     */     
/*  676: 765 */     return query.getResultList();
/*  677:     */   }
/*  678:     */   
/*  679:     */   public List<RelacionDependencia> obtenerDatosRDEP(int anio, int idOrganizacion, List<Integer> lista)
/*  680:     */   {
/*  681: 770 */     int idRubroSueldo = ParametrosSistema.getRubroSalarioUnificado(idOrganizacion).intValue();
/*  682: 771 */     int idRubroUtilidad = ParametrosSistema.getRubroUtilidad(idOrganizacion).intValue();
/*  683: 772 */     int idRubroIess = ParametrosSistema.getRubroAportePersonalIESS(idOrganizacion).intValue();
/*  684: 773 */     int idRubroImpuestoRenta = ParametrosSistema.getRubroImpuestoALARenta(idOrganizacion).intValue();
/*  685:     */     
/*  686: 775 */     StringBuilder sql = new StringBuilder();
/*  687: 776 */     sql.append("SELECT new RelacionDependencia");
/*  688: 777 */     sql.append("(");
/*  689: 778 */     sql.append("\tv.cedula, v.apellidos, v.nombres, v.callePrincipal, v.numeroCasa, v.ciudad, v.provincia, v.telefono, ");
/*  690: 779 */     sql.append("\tv.establecimiento, v.residenciaTrabajador, v.paisResidencia, v.aplicaConvenio,");
/*  691: 780 */     sql.append("    CASE WHEN v.porcentajeDiscapacidad >= 40 THEN v.tipoTrabajadorDiscapacidad ELSE '01' END , ");
/*  692: 781 */     sql.append("\tCASE WHEN v.porcentajeDiscapacidad >= 40 THEN v.porcentajeDiscapacidad ELSE '0' END  , v.tipoIdentificacionDiscapacidad, v.identificacionDiscapacidad, ");
/*  693: 782 */     sql.append("\tSUM(CASE WHEN r.idRubro = :idRubroSueldo THEN prer.valor ELSE 0 END), ");
/*  694: 783 */     sql.append("\tSUM(CASE WHEN r.idRubro != :idRubroSueldo AND prer.indicadorCalculoImpuestoRenta = TRUE AND r.operacion = 1 THEN prer.valor ELSE 0 END), ");
/*  695: 784 */     sql.append("\tSUM(CASE WHEN r.tipo =:tipoDecimoTercero THEN prer.valor ELSE 0 END), ");
/*  696: 785 */     sql.append("\tSUM(CASE WHEN r.tipo =:tipoDecimoCuarto THEN prer.valor ELSE 0 END), ");
/*  697: 786 */     sql.append("\tSUM(CASE WHEN r.tipo =:tipoFondosReserva THEN prer.valor ELSE 0 END), ");
/*  698: 787 */     sql.append("\tSUM(prer.valor*0), ");
/*  699: 788 */     sql.append("\tSUM(CASE WHEN r.idRubro = :idRubroIess THEN prer.valor ELSE 0 END), ");
/*  700: 789 */     sql.append("\tSUM(prer.valor*0), SUM(prer.valor*0), SUM(prer.valor*0), SUM(prer.valor*0), SUM(prer.valor*0), ");
/*  701: 790 */     sql.append("\tSUM(CASE WHEN r.idRubro = :idRubroImpuestoRenta AND prer.valor > 0 THEN 1 ELSE 0 END), ");
/*  702: 791 */     sql.append("\tSUM(CASE WHEN r.idRubro = :idRubroSueldo AND prer.valor > 0 THEN 1 ELSE 0 END), ");
/*  703: 792 */     sql.append("\tSUM(CASE WHEN r.idRubro = :idRubroImpuestoRenta THEN prer.valor ELSE 0 END),");
/*  704: 793 */     sql.append("    v.tipoIdentificacion )");
/*  705: 794 */     sql.append("    FROM PagoRolEmpleadoRubro prer ");
/*  706: 795 */     sql.append("   \tJOIN prer.rubro r ");
/*  707: 796 */     sql.append("    JOIN prer.pagoRolEmpleado pre ");
/*  708: 797 */     sql.append("    JOIN pre.pagoRol pr, ");
/*  709: 798 */     sql.append("    VInformacionEmpleado v ");
/*  710: 799 */     sql.append(" \tWHERE pr.anio = :anio  ");
/*  711: 800 */     if (lista.size() > 0) {
/*  712: 801 */       sql.append("    AND pr.mes in (:lista) ");
/*  713:     */     }
/*  714: 803 */     sql.append(" \tAND r.idRubro != :idRubroUtilidad ");
/*  715:     */     
/*  716:     */ 
/*  717:     */ 
/*  718: 807 */     sql.append(" AND v.empleado.idEmpleado = pre.empleado.idEmpleado AND pr.idOrganizacion = :idOrganizacion ");
/*  719: 808 */     sql.append(" GROUP BY pre.empleado.idEmpleado, v.cedula, v.apellidos, v.nombres,v.callePrincipal, v.numeroCasa, v.ciudad, v.provincia, v.telefono, ");
/*  720: 809 */     sql.append(" v.establecimiento, v.residenciaTrabajador, v.paisResidencia, v.aplicaConvenio, v.tipoTrabajadorDiscapacidad, ");
/*  721: 810 */     sql.append(" v.porcentajeDiscapacidad, v.tipoIdentificacionDiscapacidad, v.identificacionDiscapacidad, v.tipoIdentificacion ");
/*  722:     */     
/*  723: 812 */     Query query = this.em.createQuery(sql.toString());
/*  724: 813 */     query.setParameter("anio", Integer.valueOf(anio));
/*  725: 814 */     query.setParameter("idRubroSueldo", Integer.valueOf(idRubroSueldo));
/*  726: 815 */     query.setParameter("tipoDecimoCuarto", TipoRubroEnum.DECIMO_CUARTO);
/*  727: 816 */     query.setParameter("tipoFondosReserva", TipoRubroEnum.FONDOS_RESERVA);
/*  728: 817 */     query.setParameter("tipoDecimoTercero", TipoRubroEnum.DECIMO_TERCERO);
/*  729: 818 */     query.setParameter("idRubroUtilidad", Integer.valueOf(idRubroUtilidad));
/*  730: 819 */     query.setParameter("idRubroIess", Integer.valueOf(idRubroIess));
/*  731: 820 */     query.setParameter("idRubroImpuestoRenta", Integer.valueOf(idRubroImpuestoRenta));
/*  732: 821 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  733: 822 */     if (lista.size() > 0) {
/*  734: 823 */       query.setParameter("lista", lista);
/*  735:     */     }
/*  736: 826 */     return query.getResultList();
/*  737:     */   }
/*  738:     */   
/*  739:     */   public List<Object[]> obtenerDatosRDEPGastosDeducibles(int anio, int idOrganizacion)
/*  740:     */   {
/*  741: 832 */     StringBuilder sql = new StringBuilder();
/*  742: 833 */     sql.append(" SELECT em.identificacion, gdsri.valorAlimentacion, gdsri.valorEducacion, gdsri.valorSalud, gdsri.valorVestimenta, gdsri.valorVivienda ");
/*  743: 834 */     sql.append(" FROM GastoDeducibleSRI gdsri ");
/*  744: 835 */     sql.append(" INNER JOIN gdsri.empleado e  ");
/*  745: 836 */     sql.append(" INNER JOIN e.empresa em  ");
/*  746: 837 */     sql.append(" WHERE gdsri.anio = :anio  ");
/*  747: 838 */     sql.append(" AND  gdsri.idOrganizacion =:idOrganizacion ");
/*  748:     */     
/*  749: 840 */     Query query = this.em.createQuery(sql.toString());
/*  750: 841 */     query.setParameter("anio", Integer.valueOf(anio));
/*  751: 842 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  752:     */     
/*  753: 844 */     return query.getResultList();
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void actualizarPagoCash(int idPagoRol, PagoCash pagoCash, boolean indicadorAnular, boolean indicadorCobrado)
/*  757:     */   {
/*  758: 853 */     StringBuilder sql = new StringBuilder();
/*  759: 854 */     sql.append(" UPDATE PagoRolEmpleado p SET p.pagoCash.idPagoCash = :idPagoCashUpdate, indicadorCobrado = :indicadorCobrado");
/*  760: 855 */     sql.append(" WHERE p.pagoRol.idPagoRol=:idPagoRol ");
/*  761: 856 */     sql.append(" AND EXISTS (");
/*  762: 857 */     sql.append(" \t\t\tSELECT 1 FROM DetallePagoCash dpc ");
/*  763: 858 */     sql.append(" \t\t\tJOIN dpc.pagoCash pc ");
/*  764: 859 */     sql.append(" \t\t\tJOIN dpc.empleado em ");
/*  765: 860 */     sql.append(" \t\t\tWHERE pc.idPagoCash = :idPagoCash");
/*  766: 861 */     sql.append(" \t\t\tAND p.empleado.idEmpleado=dpc.empleado.idEmpleado");
/*  767: 862 */     sql.append(" )");
/*  768:     */     
/*  769: 864 */     Query query = this.em.createQuery(sql.toString());
/*  770: 865 */     query.setParameter("idPagoCash", Integer.valueOf(pagoCash.getIdPagoCash()));
/*  771: 866 */     query.setParameter("idPagoCashUpdate", indicadorAnular ? null : Integer.valueOf(pagoCash.getIdPagoCash()));
/*  772: 867 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  773: 868 */     query.setParameter("indicadorCobrado", Boolean.valueOf(indicadorCobrado));
/*  774: 869 */     query.executeUpdate();
/*  775:     */   }
/*  776:     */   
/*  777:     */   public boolean validarPagoRolAnterior(Date fechaRol, int idOrganizacion)
/*  778:     */   {
/*  779: 873 */     StringBuilder sql = new StringBuilder();
/*  780: 874 */     sql.append("SELECT MAX(p.fecha) FROM PagoRol p ");
/*  781: 875 */     sql.append("WHERE p.fecha < :fechaRol ");
/*  782: 876 */     sql.append("AND p.estado = :estado ");
/*  783: 877 */     sql.append("AND p.idOrganizacion=:idOrganizacion ");
/*  784: 878 */     sql.append("AND p.indicadorSaldoInicial = false AND indicadorFiniquito = false");
/*  785: 879 */     Query query = this.em.createQuery(sql.toString());
/*  786: 880 */     query.setParameter("estado", Estado.ELABORADO);
/*  787: 881 */     query.setParameter("fechaRol", fechaRol);
/*  788: 882 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  789:     */     
/*  790: 884 */     Date fecha = (Date)query.getSingleResult();
/*  791: 885 */     if (fecha == null) {
/*  792: 886 */       return true;
/*  793:     */     }
/*  794: 888 */     return false;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public List<Object[]> obtenerListaEmpleadosConIngresoSalida(int idPagoRol)
/*  798:     */   {
/*  799: 896 */     String sql = " SELECT pre.idPagoRolEmpleado, h.fechaIngreso, h.fechaSalida  FROM PagoRolEmpleado pre JOIN pre.pagoRol pr JOIN pre.empleado e,  HistoricoEmpleado h JOIN h.empleado em  WHERE pr.idPagoRol = :idPagoRol AND em = e AND  ( \t( \t\tYEAR(h.fechaIngreso) = pr.anio and MONTH(h.fechaIngreso) = pr.mes\t\t)  \tOR \t( \t\tYEAR(h.fechaSalida) = pr.anio and MONTH(h.fechaSalida) = pr.mes\t\t)  )";
/*  800:     */     
/*  801:     */ 
/*  802:     */ 
/*  803:     */ 
/*  804: 901 */     Query query = this.em.createQuery(sql);
/*  805: 902 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/*  806: 903 */     return query.getResultList();
/*  807:     */   }
/*  808:     */   
/*  809:     */   public List<Object[]> obtenerPagoRolEmpleadoActivos(int iPagoRol, Date fechaInicio, Date fechaRol)
/*  810:     */   {
/*  811: 909 */     StringBuilder sql = new StringBuilder();
/*  812: 910 */     sql.append(" SELECT pre.idPagoRolEmpleado, h.fechaIngreso, h.fechaSalida ");
/*  813: 911 */     sql.append(" FROM  PagoRolEmpleado pre ");
/*  814: 912 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/*  815: 913 */     sql.append(" INNER JOIN pre.empleado e");
/*  816: 914 */     sql.append(" INNER JOIN pre.historicoEmpleado h ");
/*  817: 915 */     sql.append(" INNER JOIN h.empleado em ");
/*  818: 916 */     sql.append(" WHERE em = e AND pr.idPagoRol = :idPagoRol ");
/*  819: 917 */     sql.append(" AND  h.fechaIngreso <= :fechaRol ");
/*  820: 918 */     sql.append(" AND (h.fechaSalida = null OR h.fechaSalida >= :fechaInicio)");
/*  821: 919 */     sql.append(" ORDER BY CASE WHEN h.fechaSalida = null THEN :fechaRol ELSE h.fechaSalida END DESC");
/*  822:     */     
/*  823: 921 */     Query query = this.em.createQuery(sql.toString());
/*  824: 922 */     query.setParameter("idPagoRol", Integer.valueOf(iPagoRol));
/*  825: 923 */     query.setParameter("fechaRol", fechaRol);
/*  826: 924 */     query.setParameter("fechaInicio", fechaInicio);
/*  827:     */     
/*  828: 926 */     return query.getResultList();
/*  829:     */   }
/*  830:     */   
/*  831:     */   public List<PagoRol> obtenerPagoRol(Date fechaDesde, Date fechaHasta, int idSucursal)
/*  832:     */   {
/*  833: 933 */     StringBuilder sql = new StringBuilder();
/*  834:     */     
/*  835: 935 */     sql.append("SELECT pr FROM PagoRol pr ");
/*  836: 936 */     sql.append("WHERE pr.fecha BETWEEN :fechaDesde and :fechaHasta ");
/*  837:     */     
/*  838: 938 */     sql.append("ORDER BY pr.fecha ASC ");
/*  839:     */     
/*  840: 940 */     Query query = this.em.createQuery(sql.toString());
/*  841: 941 */     query.setParameter("fechaDesde", fechaDesde);
/*  842: 942 */     query.setParameter("fechaHasta", fechaHasta);
/*  843:     */     
/*  844:     */ 
/*  845: 945 */     return query.getResultList();
/*  846:     */   }
/*  847:     */   
/*  848:     */   public PagoRol obtenerPagoRolPorDiaMes(int mes, int anio)
/*  849:     */   {
/*  850: 956 */     StringBuffer sql = new StringBuffer();
/*  851:     */     
/*  852: 958 */     sql.append("SELECT pr FORM PagoRol pr ");
/*  853: 959 */     sql.append(" WHERE MONTH(pr.fecha) = :mes ");
/*  854: 960 */     sql.append(" AND YEAR(pr.fecha) = :anio ");
/*  855:     */     
/*  856: 962 */     Query query = this.em.createQuery(sql.toString());
/*  857: 963 */     query.setParameter("mes", Integer.valueOf(mes));
/*  858: 964 */     query.setParameter("anio", Integer.valueOf(anio));
/*  859:     */     
/*  860: 966 */     return (PagoRol)query.getSingleResult();
/*  861:     */   }
/*  862:     */   
/*  863:     */   public PagoRol obtenerPagoRol(int mes, int anio, int idQuincena, int idOrganizacion)
/*  864:     */   {
/*  865: 972 */     PagoRol pagoRol = null;
/*  866:     */     
/*  867: 974 */     StringBuilder sql = new StringBuilder();
/*  868: 975 */     sql.append(" SELECT pr FROM PagoRol pr ");
/*  869: 976 */     sql.append(" WHERE MONTH(pr.fecha) = :mes");
/*  870: 977 */     sql.append(" AND YEAR(pr.fecha) = :anio ");
/*  871: 978 */     sql.append(" AND pr.quincena.idQuincena = :idQuincena ");
/*  872: 979 */     sql.append(" AND pr.idOrganizacion=:idOrganizacion ");
/*  873:     */     
/*  874: 981 */     Query query = this.em.createQuery(sql.toString());
/*  875: 982 */     query.setParameter("mes", Integer.valueOf(mes));
/*  876: 983 */     query.setParameter("anio", Integer.valueOf(anio));
/*  877: 984 */     query.setParameter("idQuincena", Integer.valueOf(idQuincena));
/*  878: 985 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  879:     */     
/*  880: 987 */     List<PagoRol> lista = query.getResultList();
/*  881: 989 */     if (!lista.isEmpty()) {
/*  882: 990 */       pagoRol = (PagoRol)lista.get(0);
/*  883:     */     }
/*  884: 993 */     return pagoRol;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void actualizarEstadoAprobacion(PagoRol pagoRol)
/*  888:     */   {
/*  889: 998 */     StringBuilder sbSQL = new StringBuilder();
/*  890: 999 */     sbSQL.append(" SELECT SUM(1), SUM(CASE WHEN pre.indicadorAprobado=true THEN 1 ELSE 0 END)");
/*  891:1000 */     sbSQL.append(" FROM PagoRolEmpleado pre");
/*  892:1001 */     sbSQL.append(" JOIN pre.pagoRol pr");
/*  893:1002 */     sbSQL.append(" WHERE pr = :pagoRol");
/*  894:1003 */     sbSQL.append(" AND EXISTS (");
/*  895:1004 */     sbSQL.append(" \t\t\tSELECT 1 FROM PagoRolEmpleadoRubro prer ");
/*  896:1005 */     sbSQL.append(" \t\t\tJOIN prer.pagoRolEmpleado pre2");
/*  897:1006 */     sbSQL.append(" \t\t\tJOIN pre2.pagoRol pr2");
/*  898:1007 */     sbSQL.append(" \t\t\tWHERE prer.valor>0");
/*  899:1008 */     sbSQL.append(" \t\t\tAND pr2 = :pagoRol");
/*  900:1009 */     sbSQL.append(" \t\t\tAND pre.idPagoRolEmpleado=pre2.idPagoRolEmpleado");
/*  901:1010 */     sbSQL.append(" ) ");
/*  902:1011 */     sbSQL.append(" AND (pr.estado != :estadoAnulado OR pr.estado != :estadoContabilizado)");
/*  903:     */     
/*  904:1013 */     Query query = this.em.createQuery(sbSQL.toString());
/*  905:1014 */     query.setParameter("pagoRol", pagoRol);
/*  906:1015 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  907:1016 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/*  908:     */     try
/*  909:     */     {
/*  910:1019 */       Object[] dato = (Object[])query.getSingleResult();
/*  911:1020 */       Long totalRegistros = (Long)dato[0];
/*  912:1021 */       Long totalRegistrosAprobados = (Long)dato[1];
/*  913:1023 */       if (totalRegistrosAprobados.longValue() == 0L) {
/*  914:1024 */         actualizarEstado(pagoRol, Estado.ELABORADO);
/*  915:1025 */       } else if (totalRegistrosAprobados.longValue() < totalRegistros.longValue()) {
/*  916:1026 */         actualizarEstado(pagoRol, Estado.APROBADO_PARCIAL);
/*  917:1027 */       } else if ((totalRegistros.longValue() > 0L) && (totalRegistrosAprobados.equals(totalRegistros))) {
/*  918:1028 */         actualizarEstado(pagoRol, Estado.APROBADO);
/*  919:     */       }
/*  920:     */     }
/*  921:     */     catch (NoResultException localNoResultException) {}
/*  922:     */   }
/*  923:     */   
/*  924:     */   public void actualizarEstado(PagoRol pagoRol, Estado estado)
/*  925:     */   {
/*  926:1038 */     StringBuilder sbSQL = new StringBuilder();
/*  927:1039 */     sbSQL.append("UPDATE PagoRol pr SET estado=:estado WHERE pr= :pagoRol");
/*  928:1040 */     Query query = this.em.createQuery(sbSQL.toString());
/*  929:1041 */     query.setParameter("pagoRol", pagoRol);
/*  930:1042 */     query.setParameter("estado", estado);
/*  931:1043 */     query.executeUpdate();
/*  932:     */   }
/*  933:     */   
/*  934:     */   public List<Rubro> getRubroTipo(TipoRubroEnum tipo, int idOrganizacion)
/*  935:     */   {
/*  936:1048 */     StringBuilder sql = new StringBuilder();
/*  937:1049 */     sql.append("SELECT r ");
/*  938:1050 */     sql.append(" FROM Rubro r ");
/*  939:1051 */     sql.append(" WHERE r.tipo =:tipo AND r.idOrganizacion =:idOrganizacion");
/*  940:     */     
/*  941:1053 */     Query query = this.em.createQuery(sql.toString());
/*  942:1054 */     query.setParameter("tipo", tipo);
/*  943:1055 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  944:1056 */     List<Rubro> listaRubros = query.getResultList();
/*  945:1057 */     for (Rubro rubro : listaRubros) {
/*  946:1058 */       rubro.getQuincena().getIdQuincena();
/*  947:     */     }
/*  948:1061 */     return listaRubros;
/*  949:     */   }
/*  950:     */   
/*  951:     */   public List<DetalleInterfazContableProceso> getInterfazPagoRolDimensiones(PagoRol pagoRol, ProcesoContabilizacionEnum procesoContabilizacionEnum)
/*  952:     */     throws ExcepcionAS2Financiero
/*  953:     */   {
/*  954:1081 */     String valores = "";
/*  955:1082 */     String condicion = "";
/*  956:1083 */     String grupoRubro = " r.idRubro, r.nombre, ";
/*  957:1084 */     String agrupamiento = ", r.nombre";
/*  958:1085 */     boolean ordenar = true;
/*  959:1086 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacionEnum.ordinal()])
/*  960:     */     {
/*  961:     */     case 1: 
/*  962:1089 */       valores = " sum(r.operacion*prer.valor)";
/*  963:1090 */       condicion = " and r.indicadorProvision = false";
/*  964:1091 */       grupoRubro = "";
/*  965:1092 */       agrupamiento = ", ''";
/*  966:1093 */       ordenar = false;
/*  967:1094 */       break;
/*  968:     */     case 2: 
/*  969:1097 */       valores = "sum(prer.valor)";
/*  970:1098 */       condicion = " AND r.operacion = 1";
/*  971:1099 */       break;
/*  972:     */     case 3: 
/*  973:1102 */       valores = "sum(prer.valor)";
/*  974:1103 */       condicion = "  AND r.operacion = -1";
/*  975:1104 */       break;
/*  976:     */     default: 
/*  977:1108 */       valores = "sum(prer.valor)";
/*  978:1109 */       condicion = " and r.indicadorProvision = true";
/*  979:     */     }
/*  980:1113 */     StringBuilder sql = new StringBuilder();
/*  981:     */     
/*  982:1115 */     sql.append("SELECT new DetalleInterfazContableProcesoPagoRol(d.idDocumento, d.nombre, s.idSucursal, '', ce.idCategoriaEmpresa, ce.nombre, e.idEmpleado, CONCAT(e.apellidos,' ',e.nombres), ");
/*  983:1116 */     sql.append(grupoRubro + " dep.idDepartamento, dep.nombre" + agrupamiento + agrupamiento + "," + valores + " )");
/*  984:1117 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/*  985:1118 */     sql.append(" INNER JOIN prer.rubro r");
/*  986:1119 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre");
/*  987:1120 */     sql.append(" INNER JOIN pre.departamento dep ");
/*  988:1121 */     sql.append(" INNER JOIN pre.empleado e");
/*  989:1122 */     sql.append(" INNER JOIN e.empresa em ");
/*  990:1123 */     sql.append(" INNER JOIN em.categoriaEmpresa ce ");
/*  991:1124 */     sql.append(" INNER JOIN e.sucursal s");
/*  992:1125 */     sql.append(" INNER JOIN pre.pagoRol pr");
/*  993:1126 */     sql.append(" INNER JOIN pr.documento d ");
/*  994:1127 */     sql.append(" WHERE pr.idPagoRol = :idPagoRol" + condicion);
/*  995:1128 */     sql.append(" AND prer.indicadorNoProcesado = FALSE ");
/*  996:1129 */     sql.append(" GROUP BY d.idDocumento, d.nombre, s.idSucursal, ce.idCategoriaEmpresa, ce.nombre, e.idEmpleado, CONCAT(e.apellidos,' ',e.nombres), " + grupoRubro);
/*  997:     */     
/*  998:1131 */     sql.append(" dep.idDepartamento, dep.nombre");
/*  999:1132 */     sql.append(" HAVING " + valores + " <> 0");
/* 1000:1133 */     if (ordenar) {
/* 1001:1134 */       sql.append(" ORDER BY r.nombre");
/* 1002:     */     }
/* 1003:1137 */     Query query = this.em.createQuery(sql.toString());
/* 1004:1138 */     query.setParameter("idPagoRol", Integer.valueOf(pagoRol.getIdPagoRol()));
/* 1005:     */     
/* 1006:1140 */     List<DetalleInterfazContableProceso> datos = query.getResultList();
/* 1007:     */     
/* 1008:1142 */     return datos;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public PagoRol obtenerPagoRolPorQuincenaMesAnio(int idOrganizacion, Quincena quincena, int mes, int anio)
/* 1012:     */   {
/* 1013:1150 */     StringBuilder sql = new StringBuilder();
/* 1014:1151 */     sql.append(" SELECT pr FROM PagoRol pr ");
/* 1015:1152 */     sql.append(" JOIN pr.quincena q ");
/* 1016:1153 */     sql.append(" WHERE q = :quincena");
/* 1017:1154 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion");
/* 1018:1155 */     sql.append(" AND pr.anio = :anio");
/* 1019:1156 */     sql.append(" AND pr.mes = :mes");
/* 1020:     */     
/* 1021:1158 */     Query query = this.em.createQuery(sql.toString());
/* 1022:1159 */     query.setParameter("quincena", quincena);
/* 1023:1160 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1024:1161 */     query.setParameter("anio", Integer.valueOf(anio));
/* 1025:1162 */     query.setParameter("mes", Integer.valueOf(mes));
/* 1026:     */     
/* 1027:1164 */     PagoRol pagoRol = null;
/* 1028:     */     try
/* 1029:     */     {
/* 1030:1166 */       pagoRol = (PagoRol)query.getSingleResult();
/* 1031:     */     }
/* 1032:     */     catch (NoResultException e)
/* 1033:     */     {
/* 1034:1168 */       pagoRol = null;
/* 1035:     */     }
/* 1036:1171 */     return pagoRol;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public PagoRol obtenerPagoRolPorFecha(int idOrganizacion, Date fecha)
/* 1040:     */   {
/* 1041:1175 */     StringBuilder sql = new StringBuilder();
/* 1042:1176 */     sql.append(" SELECT pr FROM PagoRol pr ");
/* 1043:1177 */     sql.append(" WHERE pr.idOrganizacion = :idOrganizacion");
/* 1044:1178 */     sql.append(" AND pr.fecha = :fecha");
/* 1045:     */     
/* 1046:1180 */     Query query = this.em.createQuery(sql.toString());
/* 1047:1181 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1048:1182 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 1049:     */     
/* 1050:1184 */     PagoRol pagoRol = null;
/* 1051:     */     try
/* 1052:     */     {
/* 1053:1186 */       pagoRol = (PagoRol)query.getSingleResult();
/* 1054:     */     }
/* 1055:     */     catch (NoResultException e)
/* 1056:     */     {
/* 1057:1188 */       pagoRol = null;
/* 1058:     */     }
/* 1059:1191 */     return pagoRol;
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   public List<DetalleFiniquitoEmpleado> obtenerValoresPagadosPorRubrosYFechas(int idOrganizacion, Empleado empleado, List<Rubro> listaRubros, Date fechaDesde, Date fechaHasta)
/* 1063:     */   {
/* 1064:1197 */     StringBuilder sql = new StringBuilder();
/* 1065:1198 */     sql.append(" SELECT new DetalleFiniquitoEmpleado(pr.idOrganizacion, ");
/* 1066:1199 */     sql.append(" pr.idSucursal, pr.fecha, prer.valor, prer.indicadorImpresionSobre) ");
/* 1067:1200 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/* 1068:1201 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/* 1069:1202 */     sql.append(" INNER JOIN prer.rubro r ");
/* 1070:1203 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/* 1071:1204 */     sql.append(" INNER JOIN pre.empleado e ");
/* 1072:1205 */     sql.append(" WHERE pr.fecha between :fechaDesde AND :fechaHasta ");
/* 1073:1206 */     sql.append(" AND r in (:listaRubros) ");
/* 1074:1207 */     sql.append(" AND e = :empleado ");
/* 1075:1208 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion ");
/* 1076:     */     
/* 1077:1210 */     Query query = this.em.createQuery(sql.toString());
/* 1078:     */     
/* 1079:1212 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1080:1213 */     query.setParameter("empleado", empleado);
/* 1081:1214 */     query.setParameter("listaRubros", listaRubros);
/* 1082:1215 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 1083:1216 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 1084:     */     
/* 1085:1218 */     return query.getResultList();
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   public List<Object[]> listaJornadaParcialPorcentaje(int idPagoRol)
/* 1089:     */   {
/* 1090:1225 */     StringBuilder sql = new StringBuilder();
/* 1091:1226 */     sql.append(" SELECT emp.identificacion, 'x' , dhe.horasSemana ");
/* 1092:1227 */     sql.append(" FROM DetalleHistoricoEmpleado dhe ");
/* 1093:1228 */     sql.append(" INNER JOIN dhe.historicoEmpleado he ");
/* 1094:1229 */     sql.append(" INNER JOIN he.empleado e2 ");
/* 1095:1230 */     sql.append(" INNER JOIN e2.empresa emp ");
/* 1096:1231 */     sql.append(" WHERE dhe.fechaFin is null ");
/* 1097:1232 */     sql.append(" AND EXISTS (");
/* 1098:1233 */     sql.append("              SELECT 1 FROM DetalleUtilidad du ");
/* 1099:1234 */     sql.append("              INNER JOIN du.utilidad u ");
/* 1100:1235 */     sql.append("              INNER JOIN du.empleado empl  ");
/* 1101:1236 */     sql.append("              INNER JOIN empl.empresa empr ");
/* 1102:1237 */     sql.append("\t\t      INNER JOIN u.pagoRol pr ");
/* 1103:1238 */     sql.append("              WHERE pr.idPagoRol = :idPagoRol ");
/* 1104:1239 */     sql.append("              AND empr.identificacion = emp.identificacion ");
/* 1105:1240 */     sql.append("             ) ");
/* 1106:1241 */     sql.append(" AND dhe.porcentajeCapacidadSemanal < 100 ");
/* 1107:     */     
/* 1108:1243 */     Query query = this.em.createQuery(sql.toString());
/* 1109:1244 */     query.setParameter("idPagoRol", Integer.valueOf(idPagoRol));
/* 1110:1245 */     return query.getResultList();
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public List<Object[]> beneficiosAdicionalesPorAnio(int anio, int idOrganizacion)
/* 1114:     */   {
/* 1115:1251 */     int idRubroSueldo = ParametrosSistema.getRubroSalarioUnificado(idOrganizacion).intValue();
/* 1116:1252 */     int idRubroUtilidad = ParametrosSistema.getRubroUtilidad(idOrganizacion).intValue();
/* 1117:1253 */     int idRubroIess = ParametrosSistema.getRubroAportePersonalIESS(idOrganizacion).intValue();
/* 1118:1254 */     int idRubroImpuestoRenta = ParametrosSistema.getRubroImpuestoALARenta(idOrganizacion).intValue();
/* 1119:     */     
/* 1120:1256 */     StringBuilder sql = new StringBuilder();
/* 1121:1257 */     sql.append(" SELECT empr.identificacion, SUM(prer.valor)    ");
/* 1122:1258 */     sql.append(" FROM PagoRolEmpleadoRubro prer ");
/* 1123:1259 */     sql.append(" INNER JOIN prer.rubro r ");
/* 1124:1260 */     sql.append(" INNER JOIN prer.pagoRolEmpleado pre ");
/* 1125:1261 */     sql.append(" INNER JOIN pre.empleado empl ");
/* 1126:1262 */     sql.append(" INNER JOIN empl.empresa empr ");
/* 1127:1263 */     sql.append(" INNER JOIN pre.pagoRol pr ");
/* 1128:1264 */     sql.append(" WHERE pr.anio = :anio  ");
/* 1129:1265 */     sql.append(" AND r.idRubro != :idRubroSueldo ");
/* 1130:1266 */     sql.append(" AND r.idRubro != :idRubroUtilidad ");
/* 1131:1267 */     sql.append(" AND r.idRubro != :idRubroIess ");
/* 1132:1268 */     sql.append(" AND r.idRubro != :idRubroImpuestoRenta ");
/* 1133:1269 */     sql.append(" AND r.indicadorCalculoIESS = false ");
/* 1134:1270 */     sql.append(" AND r.indicadorProvision = false ");
/* 1135:1271 */     sql.append(" AND r.operacion = 1 ");
/* 1136:1272 */     sql.append(" AND pr.idOrganizacion = :idOrganizacion ");
/* 1137:1273 */     sql.append(" AND r.tipo = :tipo ");
/* 1138:1274 */     sql.append(" GROUP BY empr.identificacion ");
/* 1139:     */     
/* 1140:1276 */     Query query = this.em.createQuery(sql.toString());
/* 1141:1277 */     query.setParameter("anio", Integer.valueOf(anio));
/* 1142:1278 */     query.setParameter("tipo", TipoRubroEnum.BENEFICIOS_ADICIONALES_EN_EFECTIVO);
/* 1143:1279 */     query.setParameter("idRubroSueldo", Integer.valueOf(idRubroSueldo));
/* 1144:1280 */     query.setParameter("idRubroUtilidad", Integer.valueOf(idRubroUtilidad));
/* 1145:1281 */     query.setParameter("idRubroIess", Integer.valueOf(idRubroIess));
/* 1146:1282 */     query.setParameter("idRubroImpuestoRenta", Integer.valueOf(idRubroImpuestoRenta));
/* 1147:1283 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1148:     */     
/* 1149:1285 */     return query.getResultList();
/* 1150:     */   }
/* 1151:     */   
/* 1152:     */   public List<RelacionDependencia> obtenerDatosContribucionSolidariaRDEP(int anio, int idOrganizacion)
/* 1153:     */   {
/* 1154:1292 */     int idRubroSueldo = ParametrosSistema.getRubroSalarioUnificado(idOrganizacion).intValue();
/* 1155:1293 */     int idRubroUtilidad = ParametrosSistema.getRubroUtilidad(idOrganizacion).intValue();
/* 1156:     */     
/* 1157:1295 */     Date fechaDesde = null;
/* 1158:1296 */     Date fechaHasta = null;
/* 1159:1297 */     int idRubroContribucionSolidaria = 0;
/* 1160:1298 */     Rubro rubroContribucionSolidaria = this.rubroDao.obtenerRubroPorTipoRubro(TipoRubroEnum.CONTRIBUCION_SOLIDARIA, idOrganizacion);
/* 1161:1299 */     if (rubroContribucionSolidaria != null)
/* 1162:     */     {
/* 1163:1301 */       int mesDesde = rubroContribucionSolidaria.getMesCalculoDesde() == 0 ? 1 : rubroContribucionSolidaria.getMesCalculoDesde();
/* 1164:1302 */       int mesHasta = rubroContribucionSolidaria.getMesCalculoHasta() == 0 ? 12 : rubroContribucionSolidaria.getMesCalculoHasta();
/* 1165:1303 */       idRubroContribucionSolidaria = rubroContribucionSolidaria.getId();
/* 1166:     */       
/* 1167:1305 */       int anioHasta = anio;
/* 1168:1306 */       if (mesHasta < mesDesde) {
/* 1169:1307 */         anioHasta++;
/* 1170:     */       }
/* 1171:1310 */       Calendar calendar = Calendar.getInstance();
/* 1172:1311 */       calendar.set(anio, mesDesde - 1, 1);
/* 1173:1312 */       fechaDesde = FuncionesUtiles.setAtributoFecha(calendar.getTime());
/* 1174:1313 */       fechaHasta = FuncionesUtiles.getFechaFinMes(anioHasta, mesHasta);
/* 1175:     */     }
/* 1176:1316 */     StringBuilder sql = new StringBuilder();
/* 1177:1317 */     sql.append("SELECT new RelacionDependencia");
/* 1178:1318 */     sql.append("(");
/* 1179:1319 */     sql.append("\tv.cedula, ");
/* 1180:1320 */     sql.append("\tSUM(CASE WHEN 0 != :idRubroContribucionSolidaria AND r.idRubro = :idRubroSueldo THEN prer.valor ELSE 0 END), ");
/* 1181:1321 */     sql.append("\tSUM(CASE WHEN 0 != :idRubroContribucionSolidaria AND r.idRubro != :idRubroSueldo AND prer.indicadorCalculoImpuestoRenta = TRUE AND r.operacion = 1 THEN prer.valor ELSE 0 END), ");
/* 1182:1322 */     sql.append("\tSUM(CASE WHEN 0 != :idRubroContribucionSolidaria AND r.idRubro = :idRubroSueldo THEN 1 ELSE 0 END), ");
/* 1183:1323 */     sql.append("\tSUM(CASE WHEN 0 != :idRubroContribucionSolidaria AND r.idRubro = :idRubroContribucionSolidaria AND prer.valor > 0 THEN 1 ELSE 0 END), ");
/* 1184:1324 */     sql.append("\tSUM(CASE WHEN 0 != :idRubroContribucionSolidaria AND r.idRubro = :idRubroContribucionSolidaria AND prer.valor > 0 THEN prer.valor ELSE 0 END)) ");
/* 1185:1325 */     sql.append("    FROM PagoRolEmpleadoRubro prer ");
/* 1186:1326 */     sql.append("   \tJOIN prer.rubro r ");
/* 1187:1327 */     sql.append("    JOIN prer.pagoRolEmpleado pre ");
/* 1188:1328 */     sql.append("    JOIN pre.pagoRol pr, ");
/* 1189:1329 */     sql.append("    VInformacionEmpleado v ");
/* 1190:1330 */     sql.append(" \tWHERE pr.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 1191:1331 */     sql.append(" \tAND r.idRubro != :idRubroUtilidad ");
/* 1192:1332 */     sql.append(" AND v.empleado.idEmpleado = pre.empleado.idEmpleado AND pr.idOrganizacion = :idOrganizacion ");
/* 1193:1333 */     sql.append(" GROUP BY v.cedula ");
/* 1194:     */     
/* 1195:1335 */     Query query = this.em.createQuery(sql.toString());
/* 1196:1336 */     query.setParameter("idRubroSueldo", Integer.valueOf(idRubroSueldo));
/* 1197:1337 */     query.setParameter("idRubroUtilidad", Integer.valueOf(idRubroUtilidad));
/* 1198:1338 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1199:1339 */     query.setParameter("fechaDesde", fechaDesde);
/* 1200:1340 */     query.setParameter("fechaHasta", fechaHasta);
/* 1201:1341 */     query.setParameter("idRubroContribucionSolidaria", Integer.valueOf(idRubroContribucionSolidaria));
/* 1202:     */     
/* 1203:1343 */     return query.getResultList();
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public List<PagoRolEmpleadoRubro> obtenerRubrosNoProcesados(int idOrganizacion, Date fechaRol, Date fechaRolPrevio)
/* 1207:     */   {
/* 1208:1355 */     StringBuilder sql = new StringBuilder();
/* 1209:1356 */     sql.append(" SELECT prer FROM PagoRolEmpleadoRubro prer");
/* 1210:1357 */     sql.append(" JOIN FETCH prer.pagoRolEmpleado pre ");
/* 1211:1358 */     sql.append(" JOIN FETCH prer.rubro ru");
/* 1212:1359 */     sql.append(" JOIN FETCH pre.historicoEmpleado he ");
/* 1213:1360 */     sql.append(" JOIN pre.pagoRol pr");
/* 1214:1361 */     sql.append(" WHERE pr.idOrganizacion=:idOrganizacion");
/* 1215:1362 */     sql.append(" AND pr.fecha=:fechaRolPrevio");
/* 1216:1363 */     sql.append(" AND prer.indicadorNoProcesado IS TRUE");
/* 1217:1364 */     sql.append(" AND NOT EXISTS(");
/* 1218:1365 */     sql.append(" \tSELECT 1 FROM PagoRolEmpleadoRubro prer2");
/* 1219:1366 */     sql.append(" \tJOIN prer2.pagoRolEmpleadoRubroPadre prer2Padre");
/* 1220:1367 */     sql.append("    JOIN prer2.pagoRolEmpleado pre2 ");
/* 1221:1368 */     sql.append(" \tJOIN pre2.pagoRol pr2");
/* 1222:1369 */     sql.append(" \tWHERE pr2.idOrganizacion=:idOrganizacion");
/* 1223:1370 */     sql.append(" \tAND pr2.fecha=:fechaRol");
/* 1224:1371 */     sql.append(" \tAND prer2Padre.idPagoRolEmpleadoRubro = prer.idPagoRolEmpleadoRubro");
/* 1225:1372 */     sql.append(" )");
/* 1226:     */     
/* 1227:1374 */     Query q = this.em.createQuery(sql.toString());
/* 1228:1375 */     q.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1229:1376 */     q.setParameter("fechaRol", fechaRol, TemporalType.DATE);
/* 1230:1377 */     q.setParameter("fechaRolPrevio", fechaRolPrevio, TemporalType.DATE);
/* 1231:     */     
/* 1232:1379 */     List<PagoRolEmpleadoRubro> datos = q.getResultList();
/* 1233:     */     
/* 1234:1381 */     return datos;
/* 1235:     */   }
/* 1236:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PagoRolDao
 * JD-Core Version:    0.7.0.1
 */