/*    1:     */ package com.asinfo.as2.dao;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.Atributo;
/*    4:     */ import com.asinfo.as2.entities.CondicionPago;
/*    5:     */ import com.asinfo.as2.entities.Contacto;
/*    6:     */ import com.asinfo.as2.entities.CuentaBancariaEmpresa;
/*    7:     */ import com.asinfo.as2.entities.Departamento;
/*    8:     */ import com.asinfo.as2.entities.DireccionEmpresa;
/*    9:     */ import com.asinfo.as2.entities.DotacionEmpleado;
/*   10:     */ import com.asinfo.as2.entities.Empleado;
/*   11:     */ import com.asinfo.as2.entities.Empresa;
/*   12:     */ import com.asinfo.as2.entities.EmpresaAtributo;
/*   13:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*   14:     */ import com.asinfo.as2.entities.FormacionAcademica;
/*   15:     */ import com.asinfo.as2.entities.LlamadoAtencion;
/*   16:     */ import com.asinfo.as2.entities.Organizacion;
/*   17:     */ import com.asinfo.as2.entities.RecargoListaPreciosCliente;
/*   18:     */ import com.asinfo.as2.entities.Subempresa;
/*   19:     */ import com.asinfo.as2.entities.Sucursal;
/*   20:     */ import com.asinfo.as2.entities.TipoContacto;
/*   21:     */ import com.asinfo.as2.entities.Transportista;
/*   22:     */ import com.asinfo.as2.entities.ValorAtributo;
/*   23:     */ import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
/*   24:     */ import com.asinfo.as2.enumeraciones.Genero;
/*   25:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   26:     */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*   27:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   28:     */ import com.asinfo.as2.util.AppUtil;
/*   29:     */ import com.asinfo.as2.utils.ParametrosSistema;
/*   30:     */ import java.util.Date;
/*   31:     */ import java.util.HashMap;
/*   32:     */ import java.util.Iterator;
/*   33:     */ import java.util.List;
/*   34:     */ import java.util.Map;
/*   35:     */ import java.util.Set;
/*   36:     */ import javax.ejb.Stateless;
/*   37:     */ import javax.persistence.EntityManager;
/*   38:     */ import javax.persistence.NoResultException;
/*   39:     */ import javax.persistence.Query;
/*   40:     */ import javax.persistence.TypedQuery;
/*   41:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   42:     */ import javax.persistence.criteria.CriteriaQuery;
/*   43:     */ import javax.persistence.criteria.Expression;
/*   44:     */ import javax.persistence.criteria.Fetch;
/*   45:     */ import javax.persistence.criteria.Join;
/*   46:     */ import javax.persistence.criteria.JoinType;
/*   47:     */ import javax.persistence.criteria.Order;
/*   48:     */ import javax.persistence.criteria.Path;
/*   49:     */ import javax.persistence.criteria.Predicate;
/*   50:     */ import javax.persistence.criteria.Root;
/*   51:     */ 
/*   52:     */ @Stateless
/*   53:     */ public class EmpresaDao
/*   54:     */   extends AbstractDaoAS2<Empresa>
/*   55:     */ {
/*   56:     */   private static final String EMPLEADO = "empleado";
/*   57:     */   private static final String ID_EMPRESA = "idEmpresa";
/*   58:     */   private static final String ID_EMPLEADO = "idEmpleado";
/*   59:     */   
/*   60:     */   public EmpresaDao()
/*   61:     */   {
/*   62:  79 */     super(Empresa.class);
/*   63:     */   }
/*   64:     */   
/*   65:     */   public List<Empresa> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*   66:     */   {
/*   67:  91 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   68:  92 */     CriteriaQuery<Empresa> criteriaQuery = criteriaBuilder.createQuery(Empresa.class);
/*   69:  93 */     Root<Empresa> from = criteriaQuery.from(Empresa.class);
/*   70:     */     
/*   71:  95 */     from.fetch("tipoIdentificacion", JoinType.LEFT);
/*   72:  96 */     from.fetch("categoriaEmpresa", JoinType.LEFT);
/*   73:  97 */     from.fetch("empleado", JoinType.LEFT);
/*   74:  98 */     Fetch<Object, Object> cliente = from.fetch("cliente", JoinType.LEFT);
/*   75:  99 */     cliente.fetch("agenteComercial", JoinType.LEFT);
/*   76: 100 */     cliente.fetch("tipoOrdenDespacho", JoinType.LEFT);
/*   77: 101 */     from.fetch("proveedor", JoinType.LEFT);
/*   78: 103 */     for (Iterator<String> iterator = filters.keySet().iterator(); iterator.hasNext();)
/*   79:     */     {
/*   80: 104 */       String filterProperty = (String)iterator.next();
/*   81: 105 */       if ((filterProperty != null) && (!filterProperty.isEmpty()) && 
/*   82: 106 */         ("indicadorEmpleado".equals(filterProperty)))
/*   83:     */       {
/*   84: 107 */         Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*   85: 108 */         empleado.fetch("cargoEmpleado", JoinType.LEFT);
/*   86: 109 */         empleado.fetch("departamento", JoinType.LEFT);
/*   87: 110 */         empleado.fetch("titulo", JoinType.LEFT);
/*   88: 111 */         empleado.fetch("tipoDiscapacidad", JoinType.LEFT);
/*   89: 112 */         empleado.fetch("tipoContrato", JoinType.LEFT);
/*   90: 113 */         empleado.fetch("pais", JoinType.LEFT);
/*   91: 114 */         empleado.fetch("horarioEmpleado", JoinType.LEFT);
/*   92:     */       }
/*   93:     */     }
/*   94: 119 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*   95: 120 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*   96: 121 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*   97:     */     
/*   98: 123 */     CriteriaQuery<Empresa> select = criteriaQuery.select(from);
/*   99: 124 */     TypedQuery<Empresa> typedQuery = this.em.createQuery(select);
/*  100: 125 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  101:     */     
/*  102: 127 */     return typedQuery.getResultList();
/*  103:     */   }
/*  104:     */   
/*  105:     */   public List<Empresa> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  106:     */   {
/*  107: 138 */     boolean notSetMaxResults = false;
/*  108: 139 */     if (filters == null) {
/*  109: 140 */       filters = new HashMap();
/*  110:     */     }
/*  111: 142 */     if (filters.get("notSetMaxResults") != null)
/*  112:     */     {
/*  113: 143 */       notSetMaxResults = true;
/*  114: 144 */       filters.remove("notSetMaxResults");
/*  115:     */     }
/*  116: 147 */     filters = agregarFiltrosOrganizacion(filters);
/*  117:     */     
/*  118: 149 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  119: 150 */     CriteriaQuery<Empresa> criteriaQuery = criteriaBuilder.createQuery(Empresa.class);
/*  120: 151 */     Root<Empresa> from = criteriaQuery.from(Empresa.class);
/*  121: 152 */     from.fetch("categoriaEmpresa", JoinType.LEFT);
/*  122: 153 */     from.fetch("tipoIdentificacion", JoinType.LEFT);
/*  123:     */     
/*  124: 155 */     Fetch<Object, Object> cliente = from.fetch("cliente", JoinType.LEFT);
/*  125: 156 */     cliente.fetch("agenteComercial", JoinType.LEFT);
/*  126: 157 */     cliente.fetch("listaPrecios", JoinType.LEFT);
/*  127: 158 */     cliente.fetch("listaDescuentos", JoinType.LEFT);
/*  128: 159 */     cliente.fetch("zona", JoinType.LEFT);
/*  129: 160 */     cliente.fetch("condicionPago", JoinType.LEFT);
/*  130: 161 */     cliente.fetch("recaudador", JoinType.LEFT);
/*  131: 162 */     cliente.fetch("transportista", JoinType.LEFT);
/*  132: 163 */     cliente.fetch("tipoOrdenDespacho", JoinType.LEFT);
/*  133:     */     
/*  134: 165 */     Fetch<Object, Object> proveedor = from.fetch("proveedor", JoinType.LEFT);
/*  135: 166 */     proveedor.fetch("condicionPago", JoinType.LEFT);
/*  136: 167 */     proveedor.fetch("listaPrecios", JoinType.LEFT);
/*  137:     */     
/*  138: 169 */     Fetch<Object, Object> empleado = from.fetch("empleado", JoinType.LEFT);
/*  139: 170 */     empleado.fetch("estadoCivil", JoinType.LEFT);
/*  140:     */     
/*  141:     */ 
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145:     */ 
/*  146: 177 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  147: 178 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  148: 180 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  149:     */     {
/*  150: 181 */       String filterProperty = (String)it.next();
/*  151: 183 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  152:     */       {
/*  153: 184 */         String filterValue = (String)filters.get(filterProperty);
/*  154: 186 */         if (filterProperty.startsWith("idOrganizacion"))
/*  155:     */         {
/*  156: 187 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  157:     */         }
/*  158: 188 */         else if (filterProperty.startsWith("activo"))
/*  159:     */         {
/*  160: 189 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  161:     */         }
/*  162: 190 */         else if (filterProperty.startsWith("indicador"))
/*  163:     */         {
/*  164: 191 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  165:     */         }
/*  166: 192 */         else if (filterProperty.equals("idEmpresa"))
/*  167:     */         {
/*  168: 193 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  169:     */         }
/*  170: 194 */         else if (filterProperty.contains("indicadorClienteBloqueado"))
/*  171:     */         {
/*  172: 195 */           conjunction.getExpressions().add(criteriaBuilder
/*  173: 196 */             .equal(from.get("cliente").get("indicadorClienteBloqueado"), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  174:     */         }
/*  175: 197 */         else if (filterProperty.contains("categoriaEmpresa.idCategoriaEmpresa"))
/*  176:     */         {
/*  177: 198 */           conjunction.getExpressions().add(criteriaBuilder
/*  178: 199 */             .equal(from.get("categoriaEmpresa").get("idCategoriaEmpresa"), Integer.valueOf(Integer.parseInt(filterValue))));
/*  179:     */         }
/*  180: 200 */         else if (filterProperty.contains("cliente.agenteComercial.idUsuario"))
/*  181:     */         {
/*  182: 201 */           conjunction.getExpressions().add(criteriaBuilder
/*  183: 202 */             .equal(from.get("cliente").get("agenteComercial").get("idUsuario"), Integer.valueOf(Integer.parseInt(filterValue))));
/*  184:     */         }
/*  185: 203 */         else if (filterProperty.contains("tipoCliente"))
/*  186:     */         {
/*  187: 204 */           String operador = "";
/*  188: 205 */           if (filterValue.startsWith("!="))
/*  189:     */           {
/*  190: 206 */             filterValue = filterValue.replace("!=", "");
/*  191: 207 */             operador = "!=";
/*  192:     */           }
/*  193: 209 */           Path<TipoEmpresaEnum> path = from.get("cliente").get("tipoCliente");
/*  194: 210 */           TipoEmpresaEnum tipoAgrupacion = TipoEmpresaEnum.valueOf(filterValue);
/*  195: 211 */           if (operador.equals("!=")) {
/*  196: 212 */             conjunction.getExpressions().add(criteriaBuilder.notEqual(path, tipoAgrupacion));
/*  197:     */           } else {
/*  198: 214 */             conjunction.getExpressions().add(criteriaBuilder.equal(path, tipoAgrupacion));
/*  199:     */           }
/*  200:     */         }
/*  201: 216 */         else if (filterProperty.contains("tipoProveedor"))
/*  202:     */         {
/*  203: 217 */           String operador = "";
/*  204: 218 */           if (filterValue.startsWith("!="))
/*  205:     */           {
/*  206: 219 */             filterValue = filterValue.replace("!=", "");
/*  207: 220 */             operador = "!=";
/*  208:     */           }
/*  209: 222 */           Path<TipoEmpresaEnum> path = from.get("proveedor").get("tipoProveedor");
/*  210: 223 */           TipoEmpresaEnum tipoAgrupacion = TipoEmpresaEnum.valueOf(filterValue);
/*  211: 224 */           if (operador.equals("!=")) {
/*  212: 225 */             conjunction.getExpressions().add(criteriaBuilder.notEqual(path, tipoAgrupacion));
/*  213:     */           } else {
/*  214: 227 */             conjunction.getExpressions().add(criteriaBuilder.equal(path, tipoAgrupacion));
/*  215:     */           }
/*  216:     */         }
/*  217: 229 */         else if (filterProperty.equals("idTransportista"))
/*  218:     */         {
/*  219: 230 */           String operadorOtrosDespachos = "";
/*  220: 231 */           if (filterValue.startsWith("!="))
/*  221:     */           {
/*  222: 232 */             filterValue = filterValue.replace("!=", "");
/*  223: 233 */             operadorOtrosDespachos = "!=";
/*  224:     */           }
/*  225: 235 */           Path<Transportista> tra = from.get("cliente").get("transportista");
/*  226: 236 */           if (operadorOtrosDespachos.equals("!=")) {
/*  227: 237 */             conjunction.getExpressions().add(criteriaBuilder.notEqual(tra.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  228:     */           } else {
/*  229: 239 */             conjunction.getExpressions().add(criteriaBuilder.equal(tra.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  230:     */           }
/*  231:     */         }
/*  232: 241 */         else if (filterProperty.contains("indicadorAgenteComercial"))
/*  233:     */         {
/*  234: 242 */           conjunction.getExpressions().add(criteriaBuilder
/*  235: 243 */             .equal(from.join("empleado").get("indicadorAgenteComercial"), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  236:     */         }
/*  237:     */         else
/*  238:     */         {
/*  239: 245 */           disjunction.getExpressions().add(criteriaBuilder
/*  240: 246 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue.toLowerCase() + "%"));
/*  241:     */         }
/*  242:     */       }
/*  243:     */     }
/*  244: 252 */     if (disjunction.getExpressions().size() > 0) {
/*  245: 254 */       conjunction.getExpressions().add(disjunction);
/*  246:     */     }
/*  247: 257 */     if (conjunction.getExpressions().size() > 0) {
/*  248: 258 */       criteriaQuery.where(conjunction);
/*  249:     */     }
/*  250: 261 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  251: 262 */     CriteriaQuery<Empresa> select = criteriaQuery.select(from);
/*  252: 263 */     TypedQuery<Empresa> typedQuery = this.em.createQuery(select);
/*  253: 266 */     if (!notSetMaxResults) {
/*  254: 267 */       typedQuery.setMaxResults(20);
/*  255:     */     }
/*  256: 270 */     return typedQuery.getResultList();
/*  257:     */   }
/*  258:     */   
/*  259:     */   public Empresa cargarDetalleAtributos(Empresa empresa)
/*  260:     */   {
/*  261: 274 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  262:     */     
/*  263: 276 */     CriteriaQuery<EmpresaAtributo> cqEmpresaAtributo = criteriaBuilder.createQuery(EmpresaAtributo.class);
/*  264: 277 */     Root<EmpresaAtributo> fromEmpresaAtributo = cqEmpresaAtributo.from(EmpresaAtributo.class);
/*  265:     */     
/*  266: 279 */     fromEmpresaAtributo.fetch("atributo", JoinType.LEFT);
/*  267: 280 */     fromEmpresaAtributo.fetch("conjuntoAtributo", JoinType.LEFT);
/*  268:     */     
/*  269: 282 */     Path<Integer> pathIdEmpresaAtributo = fromEmpresaAtributo.join("empresa").get("idEmpresa");
/*  270: 283 */     cqEmpresaAtributo.where(criteriaBuilder.equal(pathIdEmpresaAtributo, Integer.valueOf(empresa.getIdEmpresa())));
/*  271: 284 */     CriteriaQuery<EmpresaAtributo> selectEmpresaAtributo = cqEmpresaAtributo.select(fromEmpresaAtributo);
/*  272:     */     
/*  273: 286 */     List<EmpresaAtributo> listaEmpresaAtributo = this.em.createQuery(selectEmpresaAtributo).getResultList();
/*  274: 287 */     empresa.setListaEmpresaAtributo(listaEmpresaAtributo);
/*  275: 289 */     for (EmpresaAtributo empresaAtributo : listaEmpresaAtributo)
/*  276:     */     {
/*  277: 291 */       CriteriaQuery<ValorAtributo> cqValorAtributo = criteriaBuilder.createQuery(ValorAtributo.class);
/*  278: 292 */       Root<ValorAtributo> fromValorAtributo = cqValorAtributo.from(ValorAtributo.class);
/*  279:     */       
/*  280: 294 */       Path<Integer> pathIdAtributoValorAtributo = fromValorAtributo.join("atributo").get("idAtributo");
/*  281: 295 */       cqValorAtributo.where(criteriaBuilder.equal(pathIdAtributoValorAtributo, Integer.valueOf(empresaAtributo.getAtributo().getId())));
/*  282: 296 */       CriteriaQuery<ValorAtributo> selectValorAtributo = cqValorAtributo.select(fromValorAtributo);
/*  283:     */       
/*  284: 298 */       List<ValorAtributo> listaValorAtributo = this.em.createQuery(selectValorAtributo).getResultList();
/*  285: 299 */       empresaAtributo.getAtributo().setListaValorAtributo(listaValorAtributo);
/*  286:     */     }
/*  287: 302 */     return empresa;
/*  288:     */   }
/*  289:     */   
/*  290:     */   public Empresa cargarDetalle(Empresa empresa)
/*  291:     */   {
/*  292: 306 */     return cargarDetalle(empresa, true);
/*  293:     */   }
/*  294:     */   
/*  295:     */   public Empresa cargarDetalle(Empresa empresa, boolean cargarDetalle)
/*  296:     */   {
/*  297: 317 */     Empresa auxEmpresa = empresa;
/*  298:     */     
/*  299: 319 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  300:     */     
/*  301:     */ 
/*  302: 322 */     CriteriaQuery<Empresa> cqCabecera = criteriaBuilder.createQuery(Empresa.class);
/*  303: 323 */     Root<Empresa> fromCabecera = cqCabecera.from(Empresa.class);
/*  304: 324 */     fromCabecera.fetch("tipoIdentificacion", JoinType.LEFT);
/*  305: 325 */     fromCabecera.fetch("categoriaEmpresa", JoinType.LEFT);
/*  306: 326 */     fromCabecera.fetch("origenIngresos", JoinType.LEFT);
/*  307: 327 */     fromCabecera.fetch("estadoCivil", JoinType.LEFT);
/*  308: 328 */     fromCabecera.fetch("conjuntoAtributoCliente", JoinType.LEFT);
/*  309: 329 */     fromCabecera.fetch("conjuntoAtributoProveedor", JoinType.LEFT);
/*  310: 330 */     if (auxEmpresa.getCliente() != null)
/*  311:     */     {
/*  312: 331 */       Fetch<Object, Object> cliente = fromCabecera.fetch("cliente", JoinType.LEFT);
/*  313: 332 */       cliente.fetch("condicionPago", JoinType.LEFT);
/*  314: 333 */       cliente.fetch("agenteComercial", JoinType.LEFT);
/*  315: 334 */       cliente.fetch("formaPago", JoinType.LEFT);
/*  316: 335 */       cliente.fetch("listaPrecios", JoinType.LEFT);
/*  317: 336 */       cliente.fetch("listaDescuentos", JoinType.LEFT);
/*  318: 337 */       cliente.fetch("zona", JoinType.LEFT);
/*  319: 338 */       cliente.fetch("recaudador", JoinType.LEFT);
/*  320: 339 */       cliente.fetch("transportista", JoinType.LEFT);
/*  321: 340 */       cliente.fetch("tipoOrdenDespacho", JoinType.LEFT);
/*  322: 341 */       cliente.fetch("sector", JoinType.LEFT);
/*  323:     */     }
/*  324: 344 */     if (auxEmpresa.getProveedor() != null)
/*  325:     */     {
/*  326: 346 */       Fetch<Object, Object> proveedor = fromCabecera.fetch("proveedor", JoinType.LEFT);
/*  327: 347 */       proveedor.fetch("condicionPago", JoinType.LEFT);
/*  328: 348 */       proveedor.fetch("formaPago", JoinType.LEFT);
/*  329: 349 */       proveedor.fetch("categoriaRetencion", JoinType.LEFT);
/*  330: 350 */       proveedor.fetch("listaPrecios", JoinType.LEFT);
/*  331:     */     }
/*  332: 353 */     if (auxEmpresa.getEmpleado() != null)
/*  333:     */     {
/*  334: 355 */       Fetch<Object, Object> empleado = fromCabecera.fetch("empleado", JoinType.LEFT);
/*  335: 356 */       empleado.fetch("cargoEmpleado", JoinType.LEFT);
/*  336: 357 */       empleado.fetch("titulo", JoinType.LEFT);
/*  337: 358 */       empleado.fetch("departamento", JoinType.LEFT);
/*  338: 359 */       empleado.fetch("estadoCivil", JoinType.LEFT);
/*  339: 360 */       empleado.fetch("tipoDiscapacidad", JoinType.LEFT);
/*  340: 361 */       empleado.fetch("tipoContrato", JoinType.LEFT);
/*  341: 362 */       empleado.fetch("pais", JoinType.LEFT);
/*  342: 363 */       empleado.fetch("sucursal", JoinType.LEFT);
/*  343: 364 */       empleado.fetch("centroCosto", JoinType.LEFT);
/*  344: 365 */       empleado.fetch("horarioEmpleado", JoinType.LEFT);
/*  345: 366 */       empleado.fetch("categoriaRubro", JoinType.LEFT);
/*  346:     */     }
/*  347: 368 */     Path<Integer> pathId = fromCabecera.get("idEmpresa");
/*  348: 369 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(auxEmpresa.getId())));
/*  349: 370 */     CriteriaQuery<Empresa> selectEmpresa = cqCabecera.select(fromCabecera);
/*  350: 371 */     auxEmpresa = (Empresa)this.em.createQuery(selectEmpresa).getSingleResult();
/*  351: 372 */     this.em.detach(auxEmpresa);
/*  352:     */     
/*  353:     */ 
/*  354: 375 */     CriteriaQuery<DireccionEmpresa> cqDirecionEmpresa = criteriaBuilder.createQuery(DireccionEmpresa.class);
/*  355: 376 */     Root<DireccionEmpresa> fromDireccionEmpresa = cqDirecionEmpresa.from(DireccionEmpresa.class);
/*  356: 377 */     fromDireccionEmpresa.fetch("ubicacion", JoinType.LEFT);
/*  357: 378 */     fromDireccionEmpresa.fetch("parroquia", JoinType.LEFT);
/*  358:     */     
/*  359: 380 */     Fetch<Object, Object> provincia = fromDireccionEmpresa.fetch("ciudad", JoinType.LEFT);
/*  360: 381 */     Fetch<Object, Object> pais = provincia.fetch("provincia", JoinType.LEFT);
/*  361: 382 */     pais.fetch("pais", JoinType.LEFT);
/*  362:     */     
/*  363: 384 */     Path<Integer> pathidDireccionEmpresa = fromDireccionEmpresa.join("empresa").get("idEmpresa");
/*  364: 385 */     cqDirecionEmpresa.where(criteriaBuilder.equal(pathidDireccionEmpresa, Integer.valueOf(auxEmpresa.getId())));
/*  365: 386 */     CriteriaQuery<DireccionEmpresa> selectDireccionEmpresa = cqDirecionEmpresa.select(fromDireccionEmpresa);
/*  366: 387 */     List<DireccionEmpresa> listaDireccionEmpresas = this.em.createQuery(selectDireccionEmpresa).getResultList();
/*  367: 388 */     auxEmpresa.setDirecciones(listaDireccionEmpresas);
/*  368:     */     
/*  369:     */ 
/*  370: 391 */     CriteriaQuery<AutorizacionProveedorSRI> cqAutorizacionProveedorSRI = criteriaBuilder.createQuery(AutorizacionProveedorSRI.class);
/*  371: 392 */     Root<AutorizacionProveedorSRI> fromAutorizacionProveedorSRI = cqAutorizacionProveedorSRI.from(AutorizacionProveedorSRI.class);
/*  372: 393 */     Path<Integer> pathIdAutorizacionProveedorSRI = fromAutorizacionProveedorSRI.join("empresa").get("idEmpresa");
/*  373: 394 */     cqAutorizacionProveedorSRI.where(criteriaBuilder.equal(pathIdAutorizacionProveedorSRI, Integer.valueOf(auxEmpresa.getId())));
/*  374:     */     
/*  375: 396 */     CriteriaQuery<AutorizacionProveedorSRI> selectAutorizacionProveedorSRI = cqAutorizacionProveedorSRI.select(fromAutorizacionProveedorSRI);
/*  376: 397 */     List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI = this.em.createQuery(selectAutorizacionProveedorSRI).getResultList();
/*  377: 398 */     auxEmpresa.setListaAutorizacionProveedorSRI(listaAutorizacionProveedorSRI);
/*  378:     */     
/*  379:     */ 
/*  380: 401 */     CriteriaQuery<CuentaBancariaEmpresa> cqCuentaBancariaEmpresa = criteriaBuilder.createQuery(CuentaBancariaEmpresa.class);
/*  381: 402 */     Root<CuentaBancariaEmpresa> fromCuentaBancariaEmpresa = cqCuentaBancariaEmpresa.from(CuentaBancariaEmpresa.class);
/*  382: 403 */     Fetch<Object, Object> fromCuentaBancaria = fromCuentaBancariaEmpresa.fetch("cuentaBancaria", JoinType.LEFT);
/*  383: 404 */     fromCuentaBancaria.fetch("pais", JoinType.LEFT);
/*  384: 405 */     fromCuentaBancaria.fetch("banco", JoinType.LEFT);
/*  385: 406 */     fromCuentaBancaria.fetch("tipoCuentaBancaria", JoinType.LEFT);
/*  386:     */     
/*  387: 408 */     Path<Integer> pathIdCuentaBancariaEmpresa = fromCuentaBancariaEmpresa.join("empresa").get("idEmpresa");
/*  388: 409 */     cqCuentaBancariaEmpresa.where(criteriaBuilder.equal(pathIdCuentaBancariaEmpresa, Integer.valueOf(auxEmpresa.getId())));
/*  389: 410 */     CriteriaQuery<CuentaBancariaEmpresa> selectCuentaBancariaEmpresa = cqCuentaBancariaEmpresa.select(fromCuentaBancariaEmpresa);
/*  390: 411 */     List<CuentaBancariaEmpresa> listaCuentaBancariaEmpresa = this.em.createQuery(selectCuentaBancariaEmpresa).getResultList();
/*  391: 412 */     auxEmpresa.setListaCuentaBancariaEmpresa(listaCuentaBancariaEmpresa);
/*  392: 416 */     if (auxEmpresa.getEmpleado() != null)
/*  393:     */     {
/*  394: 418 */       CriteriaQuery<FormacionAcademica> cqFormacionAcademica = criteriaBuilder.createQuery(FormacionAcademica.class);
/*  395: 419 */       Root<FormacionAcademica> fromFormacionAcademica = cqFormacionAcademica.from(FormacionAcademica.class);
/*  396:     */       
/*  397: 421 */       Path<Integer> pathIdFormacionAcademica = fromFormacionAcademica.join("empleado").get("idEmpleado");
/*  398: 422 */       cqFormacionAcademica.where(criteriaBuilder.equal(pathIdFormacionAcademica, Integer.valueOf(auxEmpresa.getEmpleado().getId())));
/*  399: 423 */       CriteriaQuery<FormacionAcademica> selectFormacionAcademica = cqFormacionAcademica.select(fromFormacionAcademica);
/*  400: 424 */       List<FormacionAcademica> listaFormacionAcademica = this.em.createQuery(selectFormacionAcademica).getResultList();
/*  401: 425 */       auxEmpresa.getEmpleado().setListaFormacionAcademica(listaFormacionAcademica);
/*  402:     */     }
/*  403: 430 */     if (auxEmpresa.getEmpleado() != null)
/*  404:     */     {
/*  405: 432 */       CriteriaQuery<DotacionEmpleado> cqDotacion = criteriaBuilder.createQuery(DotacionEmpleado.class);
/*  406: 433 */       Root<DotacionEmpleado> fromDotacion = cqDotacion.from(DotacionEmpleado.class);
/*  407: 434 */       fromDotacion.fetch("producto", JoinType.INNER);
/*  408: 435 */       Path<Integer> pathIdDotacion = fromDotacion.join("empleado").get("idEmpleado");
/*  409: 436 */       cqDotacion.where(criteriaBuilder.equal(pathIdDotacion, Integer.valueOf(auxEmpresa.getEmpleado().getId())));
/*  410: 437 */       CriteriaQuery<DotacionEmpleado> selectDotacion = cqDotacion.select(fromDotacion);
/*  411: 438 */       List<DotacionEmpleado> listaDotacion = this.em.createQuery(selectDotacion).getResultList();
/*  412:     */       
/*  413: 440 */       auxEmpresa.getEmpleado().setListaDotacion(listaDotacion);
/*  414:     */     }
/*  415: 444 */     CriteriaQuery<RecargoListaPreciosCliente> cqRecargo = criteriaBuilder.createQuery(RecargoListaPreciosCliente.class);
/*  416: 445 */     Root<RecargoListaPreciosCliente> fromRecargo = cqRecargo.from(RecargoListaPreciosCliente.class);
/*  417:     */     
/*  418: 447 */     fromRecargo.fetch("listaPrecios", JoinType.LEFT);
/*  419: 448 */     fromRecargo.fetch("cuentaContable", JoinType.LEFT);
/*  420:     */     
/*  421: 450 */     Path<Integer> pathIdRecargoEmpresa = fromRecargo.join("empresa").get("idEmpresa");
/*  422: 451 */     cqRecargo.where(criteriaBuilder.equal(pathIdRecargoEmpresa, Integer.valueOf(auxEmpresa.getId())));
/*  423: 452 */     CriteriaQuery<RecargoListaPreciosCliente> selectRecargo = cqRecargo.select(fromRecargo);
/*  424: 453 */     List<RecargoListaPreciosCliente> listaReargoEmpresa = this.em.createQuery(selectRecargo).getResultList();
/*  425: 454 */     auxEmpresa.setListaRecargoListaPreciosCliente(listaReargoEmpresa);
/*  426:     */     
/*  427:     */ 
/*  428: 457 */     CriteriaQuery<Subempresa> cqSubempresa = criteriaBuilder.createQuery(Subempresa.class);
/*  429: 458 */     Root<Subempresa> fromSubempresa = cqSubempresa.from(Subempresa.class);
/*  430:     */     
/*  431: 460 */     fromSubempresa.fetch("empresa", JoinType.LEFT);
/*  432:     */     
/*  433: 462 */     Path<Integer> pathIdEmpresaPadre = fromSubempresa.join("empresaPadre").get("idEmpresa");
/*  434: 463 */     cqSubempresa.where(criteriaBuilder.equal(pathIdEmpresaPadre, Integer.valueOf(auxEmpresa.getId())));
/*  435: 464 */     CriteriaQuery<Subempresa> selectSubempresa = cqSubempresa.select(fromSubempresa);
/*  436: 465 */     List<Subempresa> listaSubempresa = this.em.createQuery(selectSubempresa).getResultList();
/*  437: 466 */     auxEmpresa.setListaSubempresa(listaSubempresa);
/*  438:     */     
/*  439:     */ 
/*  440:     */ 
/*  441:     */ 
/*  442:     */ 
/*  443:     */ 
/*  444:     */ 
/*  445:     */ 
/*  446:     */ 
/*  447:     */ 
/*  448:     */ 
/*  449:     */ 
/*  450: 479 */     CriteriaQuery<Contacto> cqContacto = criteriaBuilder.createQuery(Contacto.class);
/*  451: 480 */     Root<Contacto> fromContacto = cqContacto.from(Contacto.class);
/*  452: 481 */     fromContacto.fetch("tipoContacto");
/*  453: 482 */     fromContacto.fetch("empresa", JoinType.LEFT);
/*  454:     */     
/*  455: 484 */     Path<Integer> pathIdEmpresaContacto = fromContacto.join("empresa").get("idEmpresa");
/*  456: 485 */     cqContacto.where(criteriaBuilder.equal(pathIdEmpresaContacto, Integer.valueOf(auxEmpresa.getId())));
/*  457: 486 */     CriteriaQuery<Contacto> selectContacto = cqContacto.select(fromContacto);
/*  458: 487 */     List<Contacto> listaContacto = this.em.createQuery(selectContacto).getResultList();
/*  459: 488 */     cqContacto.orderBy(new Order[] { criteriaBuilder.asc(fromContacto.join("tipoContacto").get("nombre")) });
/*  460: 489 */     auxEmpresa.setListaContacto(listaContacto);
/*  461:     */     
/*  462:     */ 
/*  463: 492 */     CriteriaQuery<FormaPagoSRI> cqFormaPagoSRI = criteriaBuilder.createQuery(FormaPagoSRI.class);
/*  464: 493 */     Root<FormaPagoSRI> fromFormaPagoSRI = cqFormaPagoSRI.from(FormaPagoSRI.class);
/*  465: 494 */     Path<Integer> pathIdEmpresa = fromFormaPagoSRI.join("empresa").get("idEmpresa");
/*  466: 495 */     cqFormaPagoSRI.where(criteriaBuilder.equal(pathIdEmpresa, Integer.valueOf(auxEmpresa.getId())));
/*  467: 496 */     CriteriaQuery<FormaPagoSRI> selectFormaPagoSRI = cqFormaPagoSRI.select(fromFormaPagoSRI);
/*  468: 497 */     List<FormaPagoSRI> listaFormaPagoSRI = this.em.createQuery(selectFormaPagoSRI).getResultList();
/*  469: 498 */     auxEmpresa.setListaFormaPagoSRI(listaFormaPagoSRI);
/*  470:     */     
/*  471: 500 */     return auxEmpresa;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI(int idEmpresa, Date fechaFacturaProveedor)
/*  475:     */   {
/*  476: 505 */     String sql = " SELECT apSRI FROM AutorizacionProveedorSRI apSRI INNER JOIN apSRI.empresa em  WHERE em.idEmpresa = :idEmpresa  AND :fechaFacturaProveedor BETWEEN apSRI.fechaDesde AND apSRI.fechaHasta";
/*  477:     */     
/*  478: 507 */     Query query = this.em.createQuery(" SELECT apSRI FROM AutorizacionProveedorSRI apSRI INNER JOIN apSRI.empresa em  WHERE em.idEmpresa = :idEmpresa  AND :fechaFacturaProveedor BETWEEN apSRI.fechaDesde AND apSRI.fechaHasta");
/*  479: 508 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  480: 509 */     query.setParameter("fechaFacturaProveedor", fechaFacturaProveedor);
/*  481: 510 */     return query.getResultList();
/*  482:     */   }
/*  483:     */   
/*  484:     */   @Deprecated
/*  485:     */   public Empresa buscarEmpresaPorIdentificacion(String identificacion)
/*  486:     */     throws ExcepcionAS2
/*  487:     */   {
/*  488:     */     try
/*  489:     */     {
/*  490: 519 */       StringBuilder sql = new StringBuilder();
/*  491: 520 */       sql.append(" SELECT em FROM Empresa em ");
/*  492: 521 */       sql.append(" LEFT JOIN FETCH em.tipoIdentificacion ti ");
/*  493: 522 */       sql.append(" LEFT JOIN FETCH em.empleado e ");
/*  494: 523 */       sql.append(" WHERE em.identificacion = :identificacion ");
/*  495: 524 */       sql.append(" AND em.idOrganizacion = :idOrganizacion");
/*  496:     */       
/*  497: 526 */       Query query = this.em.createQuery(sql.toString());
/*  498: 527 */       query.setParameter("identificacion", identificacion);
/*  499: 528 */       query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/*  500:     */       
/*  501: 530 */       return (Empresa)query.getSingleResult();
/*  502:     */     }
/*  503:     */     catch (NoResultException e)
/*  504:     */     {
/*  505: 533 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + identificacion);
/*  506:     */     }
/*  507:     */   }
/*  508:     */   
/*  509:     */   @Deprecated
/*  510:     */   public Empresa bucarEmpresaPorIdentificacion(Map<String, String> filters)
/*  511:     */     throws ExcepcionAS2
/*  512:     */   {
/*  513: 541 */     String identificacion = (String)filters.get("identificacion");
/*  514:     */     try
/*  515:     */     {
/*  516: 544 */       CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  517: 545 */       CriteriaQuery<Empresa> cq = cb.createQuery(Empresa.class);
/*  518: 546 */       Root<Empresa> from = cq.from(Empresa.class);
/*  519: 547 */       from.fetch("tipoIdentificacion", JoinType.LEFT);
/*  520:     */       
/*  521: 549 */       filters.remove("identificacion");
/*  522: 550 */       List<Expression<?>> expresiones = obtenerExpresiones(filters, cb, from);
/*  523: 551 */       if (identificacion != null) {
/*  524: 552 */         expresiones.add(cb.equal(from.get("identificacion"), identificacion));
/*  525:     */       }
/*  526: 556 */       cq.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  527:     */       
/*  528: 558 */       CriteriaQuery<Empresa> selectEmpresa = cq.select(from);
/*  529: 559 */       return (Empresa)this.em.createQuery(selectEmpresa).getSingleResult();
/*  530:     */     }
/*  531:     */     catch (NoResultException e)
/*  532:     */     {
/*  533: 561 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + identificacion);
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   public Empresa buscarEmpresaPorIdentificacion(int idOrganizacion, String identificacion)
/*  538:     */     throws ExcepcionAS2
/*  539:     */   {
/*  540:     */     try
/*  541:     */     {
/*  542: 569 */       StringBuilder sql = new StringBuilder();
/*  543: 570 */       sql.append(" SELECT em FROM Empresa em ");
/*  544: 571 */       sql.append(" INNER JOIN FETCH em.categoriaEmpresa cem ");
/*  545: 572 */       sql.append(" LEFT JOIN FETCH em.tipoIdentificacion ti ");
/*  546: 573 */       sql.append(" LEFT JOIN FETCH em.direcciones dir ");
/*  547: 574 */       sql.append(" LEFT JOIN FETCH dir.ubicacion ubi ");
/*  548: 575 */       sql.append(" LEFT JOIN FETCH dir.ciudad cit ");
/*  549: 576 */       sql.append(" LEFT JOIN FETCH cit.provincia pro ");
/*  550: 577 */       sql.append(" LEFT JOIN FETCH pro.pais pa ");
/*  551: 578 */       sql.append(" LEFT JOIN FETCH em.cliente cli ");
/*  552: 579 */       sql.append(" LEFT JOIN FETCH em.proveedor pro ");
/*  553: 580 */       sql.append(" LEFT JOIN FETCH em.empleado empl ");
/*  554: 581 */       sql.append(" LEFT JOIN FETCH cli.formaPago for ");
/*  555: 582 */       sql.append(" LEFT JOIN FETCH cli.condicionPago con ");
/*  556: 583 */       sql.append(" WHERE em.identificacion = :identificacion ");
/*  557: 584 */       sql.append(" AND em.idOrganizacion = :idOrganizacion");
/*  558:     */       
/*  559: 586 */       Query query = this.em.createQuery(sql.toString());
/*  560: 587 */       query.setParameter("identificacion", identificacion);
/*  561: 588 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  562:     */       
/*  563: 590 */       return (Empresa)query.getSingleResult();
/*  564:     */     }
/*  565:     */     catch (NoResultException e)
/*  566:     */     {
/*  567: 593 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + identificacion);
/*  568:     */     }
/*  569:     */   }
/*  570:     */   
/*  571:     */   public Empresa empresaExiste(int idOrganizacion, String codigo)
/*  572:     */   {
/*  573:     */     try
/*  574:     */     {
/*  575: 606 */       StringBuilder sql = new StringBuilder();
/*  576: 607 */       sql.append(" SELECT em FROM Empresa em ");
/*  577: 608 */       sql.append(" WHERE em.codigo = :codigo ");
/*  578: 609 */       sql.append(" AND em.idOrganizacion = :idOrganizacion");
/*  579: 610 */       Query query = this.em.createQuery(sql.toString());
/*  580: 611 */       query.setParameter("codigo", codigo);
/*  581: 612 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  582:     */       
/*  583: 614 */       return (Empresa)query.getSingleResult();
/*  584:     */     }
/*  585:     */     catch (NoResultException e) {}
/*  586: 616 */     return null;
/*  587:     */   }
/*  588:     */   
/*  589:     */   public Empresa obtenerDatosEmpresa(int idEmpresa, boolean indicadorCliente, boolean indicadorProveedor)
/*  590:     */   {
/*  591: 622 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  592:     */     
/*  593:     */ 
/*  594: 625 */     CriteriaQuery<Empresa> cqCabecera = criteriaBuilder.createQuery(Empresa.class);
/*  595: 626 */     Root<Empresa> fromCabecera = cqCabecera.from(Empresa.class);
/*  596: 627 */     fromCabecera.fetch("tipoIdentificacion", JoinType.LEFT);
/*  597: 628 */     fromCabecera.fetch("categoriaEmpresa", JoinType.LEFT);
/*  598: 629 */     fromCabecera.fetch("origenIngresos", JoinType.LEFT);
/*  599:     */     
/*  600:     */ 
/*  601: 632 */     Fetch<Object, Object> cliente = fromCabecera.fetch("cliente", JoinType.LEFT);
/*  602: 633 */     Fetch<Object, Object> proveedor = fromCabecera.fetch("proveedor", JoinType.LEFT);
/*  603: 635 */     if (indicadorCliente)
/*  604:     */     {
/*  605: 636 */       cliente.fetch("condicionPago", JoinType.LEFT);
/*  606: 637 */       cliente.fetch("recaudador", JoinType.LEFT);
/*  607: 638 */       cliente.fetch("listaPrecios", JoinType.LEFT);
/*  608:     */     }
/*  609: 641 */     if (indicadorProveedor)
/*  610:     */     {
/*  611: 642 */       proveedor.fetch("condicionPago", JoinType.LEFT);
/*  612: 643 */       proveedor.fetch("listaPrecios", JoinType.LEFT);
/*  613:     */     }
/*  614: 646 */     Path<Integer> pathId = fromCabecera.get("idEmpresa");
/*  615: 647 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idEmpresa)));
/*  616: 648 */     CriteriaQuery<Empresa> selectEmpresa = cqCabecera.select(fromCabecera);
/*  617: 649 */     Empresa empresa = (Empresa)this.em.createQuery(selectEmpresa).getSingleResult();
/*  618: 650 */     this.em.detach(empresa);
/*  619:     */     
/*  620:     */ 
/*  621: 653 */     CriteriaQuery<DireccionEmpresa> cqDirecionEmpresa = criteriaBuilder.createQuery(DireccionEmpresa.class);
/*  622: 654 */     Root<DireccionEmpresa> fromDireccionEmpresa = cqDirecionEmpresa.from(DireccionEmpresa.class);
/*  623: 655 */     fromDireccionEmpresa.fetch("ubicacion", JoinType.LEFT);
/*  624: 656 */     fromDireccionEmpresa.fetch("ciudad", JoinType.LEFT).fetch("provincia", JoinType.LEFT).fetch("pais", JoinType.LEFT);
/*  625:     */     
/*  626: 658 */     Path<Integer> pathidDireccionEmpresa = fromDireccionEmpresa.join("empresa").get("idEmpresa");
/*  627: 659 */     cqDirecionEmpresa.where(criteriaBuilder.equal(pathidDireccionEmpresa, Integer.valueOf(idEmpresa)));
/*  628: 660 */     CriteriaQuery<DireccionEmpresa> selectDireccionEmpresa = cqDirecionEmpresa.select(fromDireccionEmpresa);
/*  629: 661 */     List<DireccionEmpresa> listaDireccionEmpresas = this.em.createQuery(selectDireccionEmpresa).getResultList();
/*  630: 662 */     empresa.setDirecciones(listaDireccionEmpresas);
/*  631:     */     
/*  632: 664 */     return empresa;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public Empresa obtenerDatosProveedor(int idEmpresa)
/*  636:     */   {
/*  637: 677 */     return obtenerDatosEmpresa(idEmpresa, false, true);
/*  638:     */   }
/*  639:     */   
/*  640:     */   public Empresa obtenerDatosCliente(int idEmpresa)
/*  641:     */   {
/*  642: 690 */     return obtenerDatosEmpresa(idEmpresa, true, false);
/*  643:     */   }
/*  644:     */   
/*  645:     */   public List<Empresa> obtenerEmpresas(int idOrganizacion)
/*  646:     */   {
/*  647: 695 */     String sql = "SELECT em FROM Empresa em WHERE em.idOrganizacion=:idOrganizacion";
/*  648: 696 */     return this.em.createQuery(sql).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion)).getResultList();
/*  649:     */   }
/*  650:     */   
/*  651:     */   public List<RecargoListaPreciosCliente> obtenerRecargos(Empresa empresa)
/*  652:     */   {
/*  653: 706 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  654: 707 */     CriteriaQuery<RecargoListaPreciosCliente> cqRecargo = criteriaBuilder.createQuery(RecargoListaPreciosCliente.class);
/*  655: 708 */     Root<RecargoListaPreciosCliente> fromRecargo = cqRecargo.from(RecargoListaPreciosCliente.class);
/*  656: 709 */     fromRecargo.fetch("cuentaContable", JoinType.LEFT);
/*  657:     */     
/*  658: 711 */     Path<Integer> pathIdEmpresa = fromRecargo.join("empresa").get("idEmpresa");
/*  659: 712 */     cqRecargo.where(criteriaBuilder.equal(pathIdEmpresa, Integer.valueOf(empresa.getId())));
/*  660: 713 */     CriteriaQuery<RecargoListaPreciosCliente> selectRecargo = cqRecargo.select(fromRecargo);
/*  661: 714 */     return this.em.createQuery(selectRecargo).getResultList();
/*  662:     */   }
/*  663:     */   
/*  664:     */   public List<FormaPagoSRI> getListaFormaPagoSRIEmpresa(String identificacion, int idOrganizacion)
/*  665:     */   {
/*  666: 719 */     StringBuilder sql = new StringBuilder();
/*  667: 720 */     sql.append(" SELECT fp");
/*  668: 721 */     sql.append(" FROM FormaPagoSRI fp");
/*  669: 722 */     sql.append(" JOIN FETCH fp.empresa e");
/*  670: 723 */     sql.append(" WHERE e.identificacion=:identificacion");
/*  671: 724 */     sql.append(" AND fp.idOrganizacion=:idOrganizacion");
/*  672: 725 */     Query query = this.em.createQuery(sql.toString());
/*  673: 726 */     query.setParameter("identificacion", identificacion);
/*  674: 727 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  675: 728 */     return query.getResultList();
/*  676:     */   }
/*  677:     */   
/*  678:     */   public List getFichaEmpleado(int idEmpleado)
/*  679:     */   {
/*  680: 733 */     StringBuilder sql = new StringBuilder();
/*  681: 734 */     sql.append(" SELECT epl.nombres,epl.apellidos,epr.codigo,epr.identificacion,suc.nombre,epl.imagen,eciv.nombre,tit.nombre,tcont.nombre,cargo.nombre,de.nombre,epr.email1,epr.paginaWeb,epl.lugarNacimiento,epl.fechaNacimiento,cat.nombre, he.nombre, he.indicadorRotativo");
/*  682: 735 */     for (int i = 0; i <= 6; i++)
/*  683:     */     {
/*  684: 736 */       sql.append(" ,t" + i + ".horaEntrada");
/*  685: 737 */       sql.append(" ,t" + i + ".horaSalida");
/*  686:     */     }
/*  687: 740 */     sql.append(" ,epl.imagenHuella1, epl.imagenHuella2, epl.imagenHuella3 , epl.imagenHuella4, epl.imagenHuella5, epl.imagenHuella6, epl.imagenHuella7, epl.imagenHuella8, epl.imagenHuella9, epl.imagenHuella10 ");
/*  688: 741 */     sql.append(" FROM Empleado epl");
/*  689: 742 */     sql.append(" LEFT JOIN epl.empresa epr");
/*  690: 743 */     sql.append(" LEFT JOIN epl.sucursal suc");
/*  691: 744 */     sql.append(" LEFT JOIN epl.estadoCivil eciv");
/*  692: 745 */     sql.append(" LEFT JOIN epl.titulo tit");
/*  693: 746 */     sql.append(" LEFT JOIN epl.tipoContrato tcont");
/*  694: 747 */     sql.append(" LEFT JOIN epl.cargoEmpleado cargo");
/*  695: 748 */     sql.append(" LEFT JOIN epl.departamento de");
/*  696: 749 */     sql.append(" LEFT JOIN epr.categoriaEmpresa cat");
/*  697: 750 */     sql.append(" LEFT JOIN epl.horarioEmpleado he");
/*  698: 751 */     for (int i = 0; i <= 6; i++) {
/*  699: 752 */       sql.append(" LEFT JOIN he.turno" + i + " t" + i);
/*  700:     */     }
/*  701: 754 */     sql.append(" WHERE epl.idEmpleado=:idEmpleado");
/*  702: 755 */     Query query = this.em.createQuery(sql.toString());
/*  703: 756 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  704: 757 */     return query.getResultList();
/*  705:     */   }
/*  706:     */   
/*  707:     */   public List<FormacionAcademica> getFichaEmpleadoFormacionAcademica(int idEmpleado)
/*  708:     */   {
/*  709: 763 */     StringBuilder sbSQL = new StringBuilder();
/*  710: 764 */     sbSQL.append(" SELECT fa ");
/*  711: 765 */     sbSQL.append(" FROM FormacionAcademica fa ");
/*  712: 766 */     sbSQL.append(" LEFT JOIN fa.empleado epl ");
/*  713: 767 */     sbSQL.append(" LEFT JOIN fa.institucionEducativa ie ");
/*  714: 768 */     sbSQL.append(" LEFT JOIN fa.nivelInstruccion ni ");
/*  715: 769 */     sbSQL.append(" WHERE epl.idEmpleado=:idEmpleado");
/*  716: 770 */     Query query = this.em.createQuery(sbSQL.toString());
/*  717: 771 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/*  718:     */     
/*  719: 773 */     return query.getResultList();
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void actualizarActivoSalidaEmpleado(Empresa empresa, int idOrganizacion, boolean estado)
/*  723:     */   {
/*  724: 778 */     StringBuilder sql = new StringBuilder();
/*  725: 779 */     sql.append(" UPDATE Empresa e  ");
/*  726: 780 */     sql.append(" SET e.activo  =:estado ");
/*  727: 781 */     sql.append(" WHERE e =:empresa  ");
/*  728: 782 */     sql.append(" AND e.idOrganizacion =:idOrganizacion ");
/*  729:     */     
/*  730: 784 */     Query query = this.em.createQuery(sql.toString());
/*  731: 785 */     query.setParameter("empresa", empresa);
/*  732: 786 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  733: 787 */     query.setParameter("estado", Boolean.valueOf(estado));
/*  734:     */     
/*  735: 789 */     query.executeUpdate();
/*  736:     */   }
/*  737:     */   
/*  738:     */   public List<Object[]> listaEmpledos(Date fechaDesde, Date fechaHasta, Departamento departamento, Sucursal sucursal, Genero genero, int idOrganizacion, String estado, String tipoReporte, int mes)
/*  739:     */   {
/*  740: 795 */     StringBuilder sql = new StringBuilder();
/*  741: 796 */     sql.append(" SELECT e.nombres, e.apellidos, ec.nombre, e.genero, e.fechaNacimiento, he.fechaIngreso, he.fechaSalida, em.identificacion, e.tipoSangre, e.codigoSectorial,  ");
/*  742: 797 */     sql.append(" tc.nombre, t.nombre , ");
/*  743: 798 */     sql.append(" d.nombre, e.activo, em.email1, em.email2, e.indicadorPagoCash, p.gentilicio, e.numeroCargasActivas,   ");
/*  744: 799 */     sql.append(" dir.telefono1, CONCAT( u.direccion1, ' ', u.direccion2, ' ', u.direccion3 ), u.direccion4, u.direccion5, c.nombre,  ");
/*  745: 800 */     sql.append(" cb.numero, b.nombre, tcb.nombre, ");
/*  746: 801 */     sql.append(" (SELECT MAX(re.valor) FROM RubroEmpleado re RIGHT JOIN re.empleado e1 LEFT JOIN re.rubro r WHERE e1 = e AND r.idRubro =:rubro),  ");
/*  747: 802 */     sql.append(" dir.telefono2, ce.nombre, s.nombre, ti.nombre, pv.nombre, cc.nombre, '' ");
/*  748: 803 */     sql.append(" FROM HistoricoEmpleado he ");
/*  749: 804 */     sql.append(" INNER JOIN he.empleado e ");
/*  750: 805 */     sql.append(" INNER JOIN e.empresa em ");
/*  751: 806 */     sql.append(" INNER JOIN e.pais p ");
/*  752: 807 */     sql.append(" LEFT JOIN e.centroCosto cc ");
/*  753: 808 */     sql.append(" LEFT JOIN e.estadoCivil ec ");
/*  754: 809 */     sql.append(" LEFT JOIN e.tipoContrato tc ");
/*  755: 810 */     sql.append(" LEFT JOIN e.departamento d ");
/*  756: 811 */     sql.append(" LEFT JOIN e.titulo t ");
/*  757: 812 */     sql.append(" LEFT JOIN em.direcciones dir ");
/*  758: 813 */     sql.append(" LEFT JOIN dir.ubicacion u ");
/*  759: 814 */     sql.append(" LEFT JOIN dir.ciudad c ");
/*  760: 815 */     sql.append(" LEFT JOIN em.listaCuentaBancariaEmpresa lcbe ");
/*  761: 816 */     sql.append(" LEFT JOIN lcbe.cuentaBancaria cb ");
/*  762: 817 */     sql.append(" LEFT JOIN cb.banco b ");
/*  763: 818 */     sql.append(" LEFT JOIN cb.tipoCuentaBancaria tcb ");
/*  764: 819 */     sql.append(" LEFT JOIN e.sucursal s ");
/*  765: 820 */     sql.append(" LEFT JOIN e.cargoEmpleado ce ");
/*  766: 821 */     sql.append(" LEFT JOIN em.tipoIdentificacion ti ");
/*  767: 822 */     sql.append(" LEFT JOIN c.provincia pv ");
/*  768: 823 */     sql.append(" WHERE e.idOrganizacion =:idOrganizacion ");
/*  769: 824 */     if (departamento != null) {
/*  770: 825 */       sql.append(" AND d =:departamento ");
/*  771:     */     }
/*  772: 827 */     if (sucursal != null) {
/*  773: 828 */       sql.append(" AND s =:sucursal ");
/*  774:     */     }
/*  775: 830 */     if (genero != null) {
/*  776: 831 */       sql.append(" AND e.genero =:genero ");
/*  777:     */     }
/*  778: 833 */     if (tipoReporte.equals("ENTRADAS"))
/*  779:     */     {
/*  780: 834 */       sql.append(" AND he.fechaIngreso BETWEEN :fechaDesde AND :fechaHasta ");
/*  781: 835 */       if (!estado.equals("TODOS")) {
/*  782: 836 */         sql.append(" AND e.activo = :estado ");
/*  783:     */       }
/*  784:     */     }
/*  785: 839 */     if (tipoReporte.equals("SALIDAS")) {
/*  786: 840 */       sql.append(" AND he.fechaSalida BETWEEN :fechaDesde AND :fechaHasta ");
/*  787:     */     }
/*  788: 842 */     if (tipoReporte.equals("CUMPLEANOS"))
/*  789:     */     {
/*  790: 843 */       sql.append(" AND MONTH(e.fechaNacimiento) = :mes ");
/*  791: 844 */       sql.append(" AND he.fechaSalida is null ");
/*  792:     */     }
/*  793: 846 */     sql.append(" ORDER BY e.apellidos ");
/*  794:     */     
/*  795: 848 */     Query query = this.em.createQuery(sql.toString());
/*  796: 849 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  797: 850 */     if (!tipoReporte.equals("CUMPLEANOS"))
/*  798:     */     {
/*  799: 851 */       query.setParameter("fechaDesde", fechaDesde);
/*  800: 852 */       query.setParameter("fechaHasta", fechaHasta);
/*  801:     */     }
/*  802: 854 */     if (departamento != null) {
/*  803: 855 */       query.setParameter("departamento", departamento);
/*  804:     */     }
/*  805: 857 */     if (sucursal != null) {
/*  806: 858 */       query.setParameter("sucursal", sucursal);
/*  807:     */     }
/*  808: 860 */     if (genero != null) {
/*  809: 861 */       query.setParameter("genero", genero);
/*  810:     */     }
/*  811: 863 */     if ((tipoReporte.equals("ENTRADAS")) && (!estado.equals("TODOS"))) {
/*  812: 864 */       query.setParameter("estado", Boolean.valueOf(estado.equals("ACTIVO")));
/*  813:     */     }
/*  814: 866 */     if (tipoReporte.equals("CUMPLEANOS")) {
/*  815: 867 */       query.setParameter("mes", Integer.valueOf(mes));
/*  816:     */     }
/*  817: 869 */     query.setParameter("rubro", ParametrosSistema.getRubroSalarioUnificado(idOrganizacion));
/*  818:     */     
/*  819: 871 */     return query.getResultList();
/*  820:     */   }
/*  821:     */   
/*  822:     */   public List<Object[]> reporteEmpresas(int idOrganizacion, boolean indicadorCliente, boolean indicadorProveedor, int idCategoria, TipoEmpresa tipoEmpresa, Empresa empresa)
/*  823:     */   {
/*  824: 879 */     StringBuilder sql = new StringBuilder();
/*  825: 880 */     sql.append(" SELECT em.idEmpresa, em.codigo,em.tipoIdentificacion.nombre,em.identificacion,em.nombreComercial,em.nombreFiscal,em.categoriaEmpresa.nombre,em.tipoEmpresa,em.descripcion,pais.nombre,prov.nombre,ciu.nombre,CONCAT(ub.direccion1,ub.direccion2,ub.direccion3,ub.direccion4,ub.direccion5)");
/*  826: 881 */     if (indicadorCliente) {
/*  827: 882 */       sql.append(",cli.tipoCliente,zo.nombre,ac.nombre1 ,re.nombre,fpa.nombre,cpa.nombre,cli.numeroCuotas,cli.metodoFacturacion,lpre.nombre,lde.nombre,cli.creditoMaximo,cli.creditoUtilizado,cli.excentoImpuestos,cli.contacto,tra.nombre, em.email1, dir.telefono1, dir.telefono2 ");
/*  828:     */     }
/*  829: 884 */     if (indicadorProveedor) {
/*  830: 885 */       sql.append(",fp.nombre,cp.nombre,cr.nombre,pro.numeroCuotas,lp.nombre,pro.indicadorPagoCash,pro.beneficiario,pro.contacto,pro.indicadorParteRelacionada, em.email1, dir.telefono1, dir.telefono2,'','','','' ");
/*  831:     */     }
/*  832: 888 */     sql.append(" FROM Empresa em ");
/*  833:     */     
/*  834: 890 */     sql.append(" LEFT JOIN em.direcciones dir ");
/*  835: 891 */     sql.append(" LEFT JOIN dir.ubicacion ub ");
/*  836: 892 */     sql.append(" LEFT JOIN dir.ciudad ciu ");
/*  837: 893 */     sql.append(" LEFT JOIN ciu.provincia prov ");
/*  838: 894 */     sql.append(" LEFT JOIN prov.pais pais ");
/*  839: 896 */     if (indicadorCliente)
/*  840:     */     {
/*  841: 897 */       sql.append(" INNER JOIN em.cliente cli");
/*  842: 898 */       sql.append(" LEFT JOIN cli.zona zo");
/*  843: 899 */       sql.append(" LEFT JOIN cli.agenteComercial ac");
/*  844: 900 */       sql.append(" LEFT JOIN cli.recaudador re");
/*  845: 901 */       sql.append(" LEFT JOIN cli.formaPago fpa");
/*  846: 902 */       sql.append(" LEFT JOIN cli.condicionPago cpa");
/*  847: 903 */       sql.append(" LEFT JOIN cli.listaPrecios lpre");
/*  848: 904 */       sql.append(" LEFT JOIN cli.listaDescuentos lde");
/*  849: 905 */       sql.append(" LEFT JOIN cli.transportista tra");
/*  850:     */     }
/*  851: 909 */     if (indicadorProveedor)
/*  852:     */     {
/*  853: 910 */       sql.append(" INNER JOIN em.proveedor pro");
/*  854: 911 */       sql.append(" LEFT JOIN pro.formaPago fp");
/*  855: 912 */       sql.append(" LEFT JOIN pro.condicionPago cp");
/*  856: 913 */       sql.append(" LEFT JOIN pro.categoriaRetencion cr");
/*  857: 914 */       sql.append(" LEFT JOIN pro.listaPrecios lp");
/*  858:     */     }
/*  859: 916 */     sql.append(" WHERE em.idOrganizacion = :idOrganizacion");
/*  860: 917 */     if (indicadorCliente) {
/*  861: 918 */       sql.append(" AND em.indicadorCliente = true");
/*  862:     */     }
/*  863: 920 */     if (indicadorProveedor) {
/*  864: 921 */       sql.append(" AND em.indicadorProveedor = true");
/*  865:     */     }
/*  866: 923 */     if (idCategoria != 0) {
/*  867: 924 */       sql.append(" AND em.categoriaEmpresa.idCategoriaEmpresa = :idCategoria");
/*  868:     */     }
/*  869: 926 */     if (tipoEmpresa != null) {
/*  870: 927 */       sql.append(" AND em.tipoEmpresa = :tipoEmpresa");
/*  871:     */     }
/*  872: 929 */     if (empresa != null) {
/*  873: 930 */       sql.append(" AND em = :empresa");
/*  874:     */     }
/*  875: 933 */     Query query = this.em.createQuery(sql.toString());
/*  876: 934 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  877: 935 */     if (idCategoria != 0) {
/*  878: 936 */       query.setParameter("idCategoria", Integer.valueOf(idCategoria));
/*  879:     */     }
/*  880: 938 */     if (tipoEmpresa != null) {
/*  881: 939 */       query.setParameter("tipoEmpresa", tipoEmpresa);
/*  882:     */     }
/*  883: 941 */     if (empresa != null) {
/*  884: 942 */       query.setParameter("empresa", empresa);
/*  885:     */     }
/*  886: 945 */     return query.getResultList();
/*  887:     */   }
/*  888:     */   
/*  889:     */   public Empresa buscarPorId(Object idEmpresa)
/*  890:     */   {
/*  891:     */     try
/*  892:     */     {
/*  893: 956 */       StringBuilder sql = new StringBuilder();
/*  894: 957 */       sql.append(" SELECT em FROM Empresa em ");
/*  895: 958 */       sql.append(" LEFT JOIN FETCH em.tipoIdentificacion ti ");
/*  896: 959 */       sql.append(" INNER JOIN FETCH em.categoriaEmpresa cemp ");
/*  897: 960 */       sql.append(" LEFT JOIN FETCH em.cliente c ");
/*  898: 961 */       sql.append(" LEFT JOIN FETCH c.listaDescuentos ld ");
/*  899: 962 */       sql.append(" WHERE em.idEmpresa = :idEmpresa ");
/*  900:     */       
/*  901: 964 */       Query query = this.em.createQuery(sql.toString());
/*  902: 965 */       query.setParameter("idEmpresa", idEmpresa);
/*  903:     */       
/*  904: 967 */       return (Empresa)query.getSingleResult();
/*  905:     */     }
/*  906:     */     catch (NoResultException e) {}
/*  907: 970 */     return null;
/*  908:     */   }
/*  909:     */   
/*  910:     */   public List<TipoContacto> getTipoContactosPorTipoNotificacion(Empresa empresa, String tipoNotificacion)
/*  911:     */   {
/*  912: 976 */     StringBuilder sql = new StringBuilder();
/*  913: 977 */     sql.append("SELECT tc");
/*  914: 978 */     sql.append(" FROM Empresa e ");
/*  915: 979 */     sql.append(" INNER JOIN e.listaContacto con ");
/*  916: 980 */     sql.append(" INNER JOIN con.tipoContacto tc ");
/*  917: 981 */     sql.append(" WHERE e.idEmpresa = :idEmpresa");
/*  918: 982 */     sql.append(" AND tc.indicadorNotificar" + tipoNotificacion + " = true");
/*  919:     */     
/*  920: 984 */     Query query = this.em.createQuery(sql.toString()).setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/*  921: 985 */     return query.getResultList();
/*  922:     */   }
/*  923:     */   
/*  924:     */   public List<CuentaBancariaEmpresa> obtenerListaCuentaBancariaEmpresa(int idOrganizacion, int idEmpresa)
/*  925:     */   {
/*  926: 990 */     StringBuilder sql = new StringBuilder();
/*  927: 991 */     sql.append("SELECT cbe");
/*  928: 992 */     sql.append(" FROM CuentaBancariaEmpresa cbe");
/*  929: 993 */     sql.append(" LEFT JOIN cbe.empresa e");
/*  930: 994 */     sql.append(" WHERE e.idOrganizacion = :idOrganizacion");
/*  931: 995 */     sql.append(" AND e.idEmpresa = :idEmpresa");
/*  932:     */     
/*  933: 997 */     Query query = this.em.createQuery(sql.toString());
/*  934: 998 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  935: 999 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  936:1000 */     return query.getResultList();
/*  937:     */   }
/*  938:     */   
/*  939:     */   public boolean verificarCodigoEmpresa(Empresa empresa)
/*  940:     */   {
/*  941:1005 */     String sql = "SELECT count(e) FROM Empresa e WHERE e.codigo = :codigo and e.idOrganizacion = :idOrganziacion and e.idEmpresa <> :idEmpresa and e.idEmpresa <> 0";
/*  942:     */     
/*  943:     */ 
/*  944:     */ 
/*  945:1009 */     Query query = this.em.createQuery(sql).setParameter("codigo", empresa.getCodigo()).setParameter("idOrganziacion", Integer.valueOf(empresa.getIdOrganizacion())).setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/*  946:     */     
/*  947:1011 */     Long cuenta = (Long)query.getSingleResult();
/*  948:     */     
/*  949:1013 */     return cuenta.longValue() > 0L;
/*  950:     */   }
/*  951:     */   
/*  952:     */   public CondicionPago obtenerFormaPago(int idEmpresa)
/*  953:     */   {
/*  954:1017 */     StringBuilder sql = new StringBuilder();
/*  955:1018 */     sql.append("SELECT cp");
/*  956:1019 */     sql.append(" FROM Cliente c");
/*  957:1020 */     sql.append(" INNER JOIN c.empresa e");
/*  958:1021 */     sql.append(" INNER JOIN c.condicionPago cp");
/*  959:1022 */     sql.append(" WHERE e.idEmpresa = :idEmpresa");
/*  960:     */     
/*  961:1024 */     Query query = this.em.createQuery(sql.toString());
/*  962:1025 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  963:1026 */     query.setMaxResults(1);
/*  964:1027 */     return (CondicionPago)query.getSingleResult();
/*  965:     */   }
/*  966:     */   
/*  967:     */   public List<Object[]> listaCuentaBancariaEmpresa(int idOrganizacion)
/*  968:     */   {
/*  969:1039 */     String sql = " SELECT e.idEmpresa, cbe.predeterminado, b.codigo, b.nombre, cb.numero, tcb.nombre FROM CuentaBancariaEmpresa cbe  INNER JOIN cbe.empresa e INNER JOIN cbe.cuentaBancaria cb INNER JOIN cb.tipoCuentaBancaria tcb INNER JOIN cb.banco b WHERE e.idOrganizacion = :idOrganizacion AND cb.predeterminado = TRUE ORDER BY cbe.fechaModificacion asc";
/*  970:     */     
/*  971:     */ 
/*  972:     */ 
/*  973:     */ 
/*  974:     */ 
/*  975:     */ 
/*  976:     */ 
/*  977:     */ 
/*  978:1048 */     Query query = this.em.createQuery(sql);
/*  979:1049 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  980:1050 */     return query.getResultList();
/*  981:     */   }
/*  982:     */   
/*  983:     */   public List<LlamadoAtencion> cargarLlamadosDeAtencion(int idEmpleado)
/*  984:     */   {
/*  985:1055 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  986:1056 */     CriteriaQuery<LlamadoAtencion> cqLlamadoAtencion = criteriaBuilder.createQuery(LlamadoAtencion.class);
/*  987:1057 */     Root<LlamadoAtencion> fromLlamadoAtencion = cqLlamadoAtencion.from(LlamadoAtencion.class);
/*  988:1058 */     fromLlamadoAtencion.fetch("motivoLlamadoAtencion", JoinType.INNER);
/*  989:1059 */     Path<Integer> pathIdLlamadoAtencion = fromLlamadoAtencion.join("empleado").get("idEmpleado");
/*  990:1060 */     cqLlamadoAtencion.where(criteriaBuilder.equal(pathIdLlamadoAtencion, Integer.valueOf(idEmpleado)));
/*  991:1061 */     CriteriaQuery<LlamadoAtencion> selectLlamadoAtencion = cqLlamadoAtencion.select(fromLlamadoAtencion);
/*  992:1062 */     List<LlamadoAtencion> listaLlamadoAtencion = this.em.createQuery(selectLlamadoAtencion).getResultList();
/*  993:     */     
/*  994:1064 */     return listaLlamadoAtencion;
/*  995:     */   }
/*  996:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.EmpresaDao
 * JD-Core Version:    0.7.0.1
 */