/*    1:     */ package com.asinfo.as2.dao.produccion;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*    4:     */ import com.asinfo.as2.entities.CategoriaProducto;
/*    5:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*    6:     */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*    7:     */ import com.asinfo.as2.entities.LecturaBalanza;
/*    8:     */ import com.asinfo.as2.entities.OrdenFabricacionMaquina;
/*    9:     */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*   10:     */ import com.asinfo.as2.entities.Producto;
/*   11:     */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   12:     */ import com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion;
/*   13:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacion;
/*   14:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*   15:     */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterialMezcla;
/*   16:     */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
/*   17:     */ import com.asinfo.as2.entities.produccion.OperacionOrdenFabricacion;
/*   18:     */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   19:     */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*   20:     */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   21:     */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   22:     */ import com.asinfo.as2.enumeraciones.Estado;
/*   23:     */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*   24:     */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*   25:     */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   26:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*   27:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   28:     */ import java.io.PrintStream;
/*   29:     */ import java.math.BigDecimal;
/*   30:     */ import java.math.RoundingMode;
/*   31:     */ import java.util.ArrayList;
/*   32:     */ import java.util.Date;
/*   33:     */ import java.util.HashMap;
/*   34:     */ import java.util.List;
/*   35:     */ import java.util.Map;
/*   36:     */ import javax.ejb.Lock;
/*   37:     */ import javax.ejb.LockType;
/*   38:     */ import javax.ejb.Stateless;
/*   39:     */ import javax.persistence.EntityManager;
/*   40:     */ import javax.persistence.NoResultException;
/*   41:     */ import javax.persistence.ParameterMode;
/*   42:     */ import javax.persistence.Query;
/*   43:     */ import javax.persistence.StoredProcedureQuery;
/*   44:     */ import javax.persistence.TemporalType;
/*   45:     */ import javax.persistence.TypedQuery;
/*   46:     */ import javax.persistence.criteria.CriteriaBuilder;
/*   47:     */ import javax.persistence.criteria.CriteriaQuery;
/*   48:     */ import javax.persistence.criteria.Expression;
/*   49:     */ import javax.persistence.criteria.Fetch;
/*   50:     */ import javax.persistence.criteria.Join;
/*   51:     */ import javax.persistence.criteria.JoinType;
/*   52:     */ import javax.persistence.criteria.Order;
/*   53:     */ import javax.persistence.criteria.Path;
/*   54:     */ import javax.persistence.criteria.Predicate;
/*   55:     */ import javax.persistence.criteria.Root;
/*   56:     */ 
/*   57:     */ @Stateless
/*   58:     */ public class OrdenFabricacionDao
/*   59:     */   extends AbstractDaoAS2<OrdenFabricacion>
/*   60:     */ {
/*   61:     */   public OrdenFabricacionDao()
/*   62:     */   {
/*   63:  79 */     super(OrdenFabricacion.class);
/*   64:     */   }
/*   65:     */   
/*   66:     */   public OrdenFabricacion cargarDetalle(int idOrdenFabricacion)
/*   67:     */   {
/*   68:  88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*   69:     */     
/*   70:     */ 
/*   71:  91 */     CriteriaQuery<OrdenFabricacion> cqCabecera = criteriaBuilder.createQuery(OrdenFabricacion.class);
/*   72:  92 */     Root<OrdenFabricacion> fromCabecera = cqCabecera.from(OrdenFabricacion.class);
/*   73:  93 */     fromCabecera.fetch("producto", JoinType.LEFT);
/*   74:  94 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*   75:  95 */     fromCabecera.fetch("rutaFabricacion", JoinType.LEFT);
/*   76:  96 */     fromCabecera.fetch("bodega", JoinType.LEFT);
/*   77:  97 */     fromCabecera.fetch("documento").fetch("secuencia", JoinType.LEFT);
/*   78:  98 */     fromCabecera.fetch("ordenFabricacionFormulacion", JoinType.LEFT);
/*   79:  99 */     fromCabecera.fetch("ordenSalidaMaterialPrincipal", JoinType.LEFT);
/*   80: 100 */     fromCabecera.fetch("personaResponsable", JoinType.LEFT);
/*   81: 101 */     fromCabecera.fetch("atributoOrdenFabricacion", JoinType.LEFT);
/*   82: 102 */     fromCabecera.fetch("valorAtributoOrdenFabricacion", JoinType.LEFT);
/*   83: 103 */     fromCabecera.fetch("ordenFabricacionPadre", JoinType.LEFT);
/*   84: 104 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idOrdenFabricacion"), Integer.valueOf(idOrdenFabricacion)));
/*   85: 105 */     CriteriaQuery<OrdenFabricacion> select = cqCabecera.select(fromCabecera);
/*   86:     */     
/*   87: 107 */     OrdenFabricacion ordenFabricacion = (OrdenFabricacion)this.em.createQuery(select).getSingleResult();
/*   88: 108 */     this.em.detach(ordenFabricacion);
/*   89:     */     
/*   90:     */ 
/*   91: 111 */     CriteriaQuery<DetalleOrdenFabricacion> cqDetalle = criteriaBuilder.createQuery(DetalleOrdenFabricacion.class);
/*   92: 112 */     Root<DetalleOrdenFabricacion> fromOrden = cqDetalle.from(DetalleOrdenFabricacion.class);
/*   93: 113 */     fromOrden.fetch("detallePedido", JoinType.LEFT).fetch("pedidoCliente", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*   94: 114 */     cqDetalle.where(criteriaBuilder.equal(fromOrden.get("ordenFabricacion"), ordenFabricacion));
/*   95: 115 */     CriteriaQuery<DetalleOrdenFabricacion> selectDetalle = cqDetalle.select(fromOrden);
/*   96: 116 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromOrden.get("idDetalleOrdenFabricacion")) });
/*   97:     */     
/*   98: 118 */     List<DetalleOrdenFabricacion> listaDetalleOrden = this.em.createQuery(selectDetalle).getResultList();
/*   99: 119 */     for (DetalleOrdenFabricacion dof : listaDetalleOrden)
/*  100:     */     {
/*  101: 120 */       this.em.detach(dof);
/*  102: 121 */       dof.setOrdenFabricacion(ordenFabricacion);
/*  103:     */     }
/*  104: 123 */     ordenFabricacion.setDetalleOrdenFabricacion(listaDetalleOrden);
/*  105:     */     
/*  106:     */ 
/*  107: 126 */     Object cqOperacion = criteriaBuilder.createQuery(OperacionOrdenFabricacion.class);
/*  108: 127 */     Root<OperacionOrdenFabricacion> fromOperacion = ((CriteriaQuery)cqOperacion).from(OperacionOrdenFabricacion.class);
/*  109:     */     
/*  110: 129 */     Fetch<Object, Object> operacionProduccion = fromOperacion.fetch("operacionProduccion", JoinType.INNER);
/*  111: 130 */     operacionProduccion.fetch("rutaFabricacion", JoinType.LEFT);
/*  112: 131 */     operacionProduccion.fetch("tareaProduccion", JoinType.LEFT);
/*  113: 132 */     operacionProduccion.fetch("centroTrabajo", JoinType.LEFT);
/*  114: 133 */     operacionProduccion.fetch("maquina", JoinType.LEFT);
/*  115:     */     
/*  116: 135 */     ((CriteriaQuery)cqOperacion).where(criteriaBuilder.equal(fromOperacion.get("ordenFabricacion"), ordenFabricacion));
/*  117: 136 */     CriteriaQuery<OperacionOrdenFabricacion> selectOperacion = ((CriteriaQuery)cqOperacion).select(fromOperacion);
/*  118:     */     
/*  119: 138 */     List<OperacionOrdenFabricacion> listaOperacion = this.em.createQuery(selectOperacion).getResultList();
/*  120: 139 */     for (OperacionOrdenFabricacion dof : listaOperacion)
/*  121:     */     {
/*  122: 140 */       this.em.detach(dof);
/*  123: 141 */       dof.setOrdenFabricacion(ordenFabricacion);
/*  124:     */     }
/*  125: 143 */     ordenFabricacion.setListaOperacionProduccion(listaOperacion);
/*  126:     */     
/*  127:     */ 
/*  128: 146 */     Object cqOFOSM = criteriaBuilder.createQuery(OrdenFabricacionOrdenSalidaMaterial.class);
/*  129: 147 */     Root<OrdenFabricacionOrdenSalidaMaterial> fromOFOSM = ((CriteriaQuery)cqOFOSM).from(OrdenFabricacionOrdenSalidaMaterial.class);
/*  130:     */     
/*  131: 149 */     fromOFOSM.fetch("ordenSalidaMaterial", JoinType.INNER);
/*  132: 150 */     fromOFOSM.fetch("ordenFabricacion", JoinType.INNER);
/*  133: 151 */     ((CriteriaQuery)cqOFOSM).where(criteriaBuilder.equal(fromOFOSM.get("ordenFabricacion"), ordenFabricacion));
/*  134: 152 */     CriteriaQuery<OrdenFabricacionOrdenSalidaMaterial> selectOFOSM = ((CriteriaQuery)cqOFOSM).select(fromOFOSM);
/*  135:     */     
/*  136: 154 */     List<OrdenFabricacionOrdenSalidaMaterial> listaOFOSM = this.em.createQuery(selectOFOSM).getResultList();
/*  137: 155 */     ordenFabricacion.setListaOrdenFabricacionOrdenSalidaMaterial(listaOFOSM);
/*  138:     */     
/*  139:     */ 
/*  140: 158 */     CriteriaQuery<OrdenFabricacionMaquina> cqOrdenFabricacionMaquina = criteriaBuilder.createQuery(OrdenFabricacionMaquina.class);
/*  141: 159 */     Root<OrdenFabricacionMaquina> fromOrdenFabricacionMaquina = cqOrdenFabricacionMaquina.from(OrdenFabricacionMaquina.class);
/*  142: 160 */     fromOrdenFabricacionMaquina.fetch("maquina", JoinType.LEFT);
/*  143: 161 */     cqOrdenFabricacionMaquina.where(criteriaBuilder.equal(fromOrdenFabricacionMaquina.get("ordenFabricacion"), ordenFabricacion));
/*  144: 162 */     CriteriaQuery<OrdenFabricacionMaquina> selectOrdenFabricacionMaquina = cqOrdenFabricacionMaquina.select(fromOrdenFabricacionMaquina);
/*  145:     */     
/*  146: 164 */     List<OrdenFabricacionMaquina> listaOrdenFabricacionMaquina = this.em.createQuery(selectOrdenFabricacionMaquina).getResultList();
/*  147: 165 */     ordenFabricacion.setListaOrdenFabricacionMaquina(listaOrdenFabricacionMaquina);
/*  148: 168 */     if (!ordenFabricacion.isIndicadorDirecto())
/*  149:     */     {
/*  150: 170 */       CriteriaQuery<OrdenFabricacion> cqSuborden = criteriaBuilder.createQuery(OrdenFabricacion.class);
/*  151: 171 */       Root<OrdenFabricacion> fromSuborden = cqSuborden.from(OrdenFabricacion.class);
/*  152: 172 */       fromSuborden.fetch("producto", JoinType.LEFT);
/*  153: 173 */       fromSuborden.fetch("rutaFabricacion", JoinType.LEFT);
/*  154: 174 */       fromSuborden.fetch("bodega", JoinType.LEFT);
/*  155: 175 */       fromSuborden.fetch("documento").fetch("secuencia", JoinType.LEFT);
/*  156: 176 */       fromSuborden.fetch("ordenFabricacionFormulacion", JoinType.LEFT);
/*  157: 177 */       fromSuborden.fetch("ordenSalidaMaterialPrincipal", JoinType.LEFT);
/*  158: 178 */       fromSuborden.fetch("personaResponsable", JoinType.LEFT);
/*  159: 179 */       fromSuborden.fetch("atributoOrdenFabricacion", JoinType.LEFT);
/*  160: 180 */       fromSuborden.fetch("valorAtributoOrdenFabricacion", JoinType.LEFT);
/*  161:     */       
/*  162: 182 */       cqSuborden.where(criteriaBuilder.equal(fromSuborden.get("ordenFabricacionPadre"), ordenFabricacion));
/*  163: 183 */       CriteriaQuery<OrdenFabricacion> selectSuborden = cqSuborden.select(fromSuborden);
/*  164:     */       
/*  165: 185 */       List<OrdenFabricacion> listaSuborden = this.em.createQuery(selectSuborden).getResultList();
/*  166: 186 */       for (OrdenFabricacion suborden : listaSuborden)
/*  167:     */       {
/*  168: 187 */         this.em.detach(suborden);
/*  169: 188 */         suborden.setOrdenFabricacionPadre(ordenFabricacion);
/*  170:     */       }
/*  171: 191 */       ordenFabricacion.setListaSubordenes(listaSuborden);
/*  172:     */     }
/*  173: 194 */     return ordenFabricacion;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public List<OrdenFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, boolean cargarSalidaMaterial)
/*  177:     */   {
/*  178: 200 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  179: 201 */     CriteriaQuery<OrdenFabricacion> criteriaQuery = criteriaBuilder.createQuery(OrdenFabricacion.class);
/*  180: 202 */     Root<OrdenFabricacion> from = criteriaQuery.from(OrdenFabricacion.class);
/*  181:     */     
/*  182: 204 */     from.fetch("bodega", JoinType.LEFT);
/*  183: 205 */     from.fetch("rutaFabricacion", JoinType.LEFT);
/*  184: 206 */     from.fetch("producto", JoinType.LEFT).fetch("presentacionProducto", JoinType.LEFT);
/*  185: 207 */     from.fetch("sucursal", JoinType.LEFT);
/*  186: 208 */     from.fetch("ordenFabricacionFormulacion", JoinType.LEFT);
/*  187: 209 */     from.fetch("ordenSalidaMaterialPrincipal", JoinType.LEFT);
/*  188: 210 */     Fetch<Object, Object> ofPrincipal = from.fetch("ordenFabricacionPrincipal", JoinType.LEFT);
/*  189: 211 */     ofPrincipal.fetch("producto", JoinType.LEFT);
/*  190: 212 */     Fetch<Object, Object> ofPadre = from.fetch("ordenFabricacionPadre", JoinType.LEFT);
/*  191: 213 */     ofPadre.fetch("producto", JoinType.LEFT);
/*  192:     */     
/*  193: 215 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  194:     */     
/*  195: 217 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  196: 218 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  197:     */     
/*  198: 220 */     CriteriaQuery<OrdenFabricacion> select = criteriaQuery.select(from);
/*  199:     */     
/*  200: 222 */     TypedQuery<OrdenFabricacion> typedQuery = this.em.createQuery(select);
/*  201: 223 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  202: 224 */     List<OrdenFabricacion> listaOrdenfabricacion = typedQuery.getResultList();
/*  203:     */     Map<Integer, List<OrdenFabricacionOrdenSalidaMaterial>> hmOrdenFabricacionOrdenSalidaMaterial;
/*  204: 226 */     if ((listaOrdenfabricacion.size() > 0) && (cargarSalidaMaterial))
/*  205:     */     {
/*  206: 227 */       CriteriaQuery<OrdenFabricacionOrdenSalidaMaterial> cqOFOSM = criteriaBuilder.createQuery(OrdenFabricacionOrdenSalidaMaterial.class);
/*  207: 228 */       Root<OrdenFabricacionOrdenSalidaMaterial> fromOFOSM = cqOFOSM.from(OrdenFabricacionOrdenSalidaMaterial.class);
/*  208:     */       
/*  209: 230 */       fromOFOSM.fetch("ordenSalidaMaterial", JoinType.INNER);
/*  210: 231 */       fromOFOSM.fetch("ordenFabricacion", JoinType.INNER);
/*  211:     */       
/*  212: 233 */       cqOFOSM.where(fromOFOSM.join("ordenFabricacion").in(listaOrdenfabricacion));
/*  213:     */       
/*  214: 235 */       CriteriaQuery<OrdenFabricacionOrdenSalidaMaterial> selectOFOSM = cqOFOSM.select(fromOFOSM);
/*  215: 236 */       TypedQuery<OrdenFabricacionOrdenSalidaMaterial> typedQuery2 = this.em.createQuery(selectOFOSM);
/*  216: 237 */       List<OrdenFabricacionOrdenSalidaMaterial> listaOFOSM = typedQuery2.getResultList();
/*  217:     */       
/*  218: 239 */       hmOrdenFabricacionOrdenSalidaMaterial = new HashMap();
/*  219: 240 */       for (OrdenFabricacionOrdenSalidaMaterial ofosm : listaOFOSM)
/*  220:     */       {
/*  221: 242 */         List<OrdenFabricacionOrdenSalidaMaterial> ordenFabricacionOrdenSalidaMaterial = (List)hmOrdenFabricacionOrdenSalidaMaterial.get(Integer.valueOf(ofosm.getOrdenFabricacion().getId()));
/*  222: 243 */         if (ordenFabricacionOrdenSalidaMaterial == null)
/*  223:     */         {
/*  224: 244 */           ordenFabricacionOrdenSalidaMaterial = new ArrayList();
/*  225: 245 */           hmOrdenFabricacionOrdenSalidaMaterial.put(Integer.valueOf(ofosm.getOrdenFabricacion().getId()), ordenFabricacionOrdenSalidaMaterial);
/*  226:     */         }
/*  227: 247 */         ordenFabricacionOrdenSalidaMaterial.add(ofosm);
/*  228:     */       }
/*  229: 250 */       for (OrdenFabricacion ordenFabricacion : listaOrdenfabricacion)
/*  230:     */       {
/*  231: 252 */         List<OrdenFabricacionOrdenSalidaMaterial> ordenFabricacionOrdenSalidaMaterial = (List)hmOrdenFabricacionOrdenSalidaMaterial.get(Integer.valueOf(ordenFabricacion.getId()));
/*  232: 253 */         if (ordenFabricacionOrdenSalidaMaterial != null) {
/*  233: 254 */           ordenFabricacion.setListaOrdenFabricacionOrdenSalidaMaterial(ordenFabricacionOrdenSalidaMaterial);
/*  234:     */         } else {
/*  235: 256 */           ordenFabricacion.setListaOrdenFabricacionOrdenSalidaMaterial(new ArrayList());
/*  236:     */         }
/*  237:     */       }
/*  238:     */     }
/*  239: 260 */     return listaOrdenfabricacion;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public List<OrdenFabricacion> autocompletarOrdenesAbiertas(int idOrganizacion, String cadena, CategoriaProducto categoriaProducto)
/*  243:     */   {
/*  244: 272 */     return autocompletarOrdenesAbiertas(idOrganizacion, cadena, categoriaProducto, null, null);
/*  245:     */   }
/*  246:     */   
/*  247:     */   public List<OrdenFabricacion> autocompletarOrdenesAbiertas(int idOrganizacion, String cadena, CategoriaProducto categoriaProducto, List<Integer> listIdsSucursalesAsignadasUsuarioEnSesion, Boolean indicadorBusquedaPorOrdenFabricacion)
/*  248:     */   {
/*  249: 279 */     cadena = cadena == null ? "" : cadena.trim();
/*  250:     */     
/*  251: 281 */     StringBuilder sql = new StringBuilder();
/*  252: 282 */     sql.append(" SELECT ofa FROM OrdenFabricacion ofa");
/*  253: 283 */     sql.append(" JOIN FETCH ofa.producto p ");
/*  254: 284 */     sql.append(" LEFT JOIN FETCH ofa.sucursal s");
/*  255: 285 */     sql.append(" LEFT JOIN FETCH ofa.atributoOrdenFabricacion aofa ");
/*  256: 286 */     sql.append(" LEFT JOIN FETCH ofa.valorAtributoOrdenFabricacion vaofa ");
/*  257: 287 */     sql.append(" LEFT JOIN FETCH ofa.ordenFabricacionPadre ofap ");
/*  258: 288 */     sql.append(" LEFT JOIN FETCH ofap.valorAtributoOrdenFabricacion va ");
/*  259: 289 */     sql.append(" LEFT JOIN FETCH p.subcategoriaProducto scp ");
/*  260: 290 */     sql.append(" LEFT JOIN FETCH scp.categoriaProducto cp ");
/*  261: 291 */     sql.append(" LEFT JOIN FETCH p.unidad u ");
/*  262: 292 */     sql.append(" LEFT JOIN FETCH p.unidadCompra uc ");
/*  263: 293 */     sql.append(" LEFT JOIN FETCH p.unidadVenta uv ");
/*  264: 294 */     sql.append(" LEFT JOIN FETCH p.unidadAlmacenamiento ua ");
/*  265: 295 */     sql.append(" LEFT JOIN FETCH p.unidadInformativa ui ");
/*  266: 296 */     sql.append(" WHERE ofa.idOrganizacion=:idOrganizacion");
/*  267: 297 */     sql.append(" AND ofa.estado != :estadoAnulado");
/*  268: 298 */     sql.append(" AND ofa.fechaLanzamiento IS NOT NULL");
/*  269: 299 */     sql.append(" AND ofa.fechaCierre IS NULL");
/*  270: 301 */     if (cadena.length() > 0)
/*  271:     */     {
/*  272: 302 */       sql.append(" AND ( ofa.numero LIKE :cadena ");
/*  273: 303 */       sql.append(" OR p.nombre LIKE :cadena");
/*  274: 304 */       sql.append(" OR p.codigo LIKE :cadena");
/*  275: 305 */       sql.append(" OR ofa.descripcion LIKE :cadena)");
/*  276:     */     }
/*  277: 307 */     if ((categoriaProducto != null) && (categoriaProducto.getId() != 0)) {
/*  278: 308 */       sql.append(" AND cp.idCategoriaProducto =:idCategoriaProducto");
/*  279:     */     }
/*  280: 311 */     if ((listIdsSucursalesAsignadasUsuarioEnSesion != null) && (listIdsSucursalesAsignadasUsuarioEnSesion.size() > 0)) {
/*  281: 312 */       sql.append(" AND s.idSucursal IN (:listIdsSucursales)");
/*  282:     */     }
/*  283: 315 */     if (indicadorBusquedaPorOrdenFabricacion != null) {
/*  284: 316 */       if (indicadorBusquedaPorOrdenFabricacion.booleanValue()) {
/*  285: 317 */         sql.append(" AND ofa.indicadorSuborden = false ");
/*  286:     */       } else {
/*  287: 319 */         sql.append(" AND ofa.indicadorSuborden = true ");
/*  288:     */       }
/*  289:     */     }
/*  290: 323 */     sql.append(" ORDER BY ofa.numero");
/*  291:     */     
/*  292: 325 */     Query query = this.em.createQuery(sql.toString());
/*  293: 326 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  294: 327 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/*  295: 328 */     if ((categoriaProducto != null) && (categoriaProducto.getId() != 0)) {
/*  296: 329 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/*  297:     */     }
/*  298: 332 */     if (cadena.length() > 0) {
/*  299: 333 */       query.setParameter("cadena", "%" + cadena + "%");
/*  300:     */     }
/*  301: 336 */     if ((listIdsSucursalesAsignadasUsuarioEnSesion != null) && (listIdsSucursalesAsignadasUsuarioEnSesion.size() > 0)) {
/*  302: 337 */       query.setParameter("listIdsSucursales", listIdsSucursalesAsignadasUsuarioEnSesion);
/*  303:     */     }
/*  304: 340 */     return query.getResultList();
/*  305:     */   }
/*  306:     */   
/*  307:     */   public List<OrdenFabricacion> getListaOrdenesLanzadas(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  308:     */   {
/*  309: 355 */     StringBuilder sql = new StringBuilder();
/*  310: 356 */     sql.append(" SELECT ofa FROM OrdenFabricacion ofa");
/*  311:     */     
/*  312: 358 */     sql.append(" WHERE ofa.idOrganizacion = :idOrganizacion");
/*  313: 359 */     sql.append(" AND ofa.fechaLanzamiento BETWEEN :fechaDesde AND :fechaHasta");
/*  314: 360 */     sql.append(" AND ofa.estado <> :estadoAnulado");
/*  315:     */     
/*  316: 362 */     Query query = this.em.createQuery(sql.toString());
/*  317: 363 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  318: 364 */     query.setParameter("fechaDesde", fechaDesde);
/*  319: 365 */     query.setParameter("fechaHasta", fechaHasta);
/*  320: 366 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/*  321:     */     
/*  322: 368 */     return query.getResultList();
/*  323:     */   }
/*  324:     */   
/*  325:     */   public boolean existeOrdenPorPlanYFecha(PlanProduccion planProduccion, Date fecha, Producto producto)
/*  326:     */   {
/*  327: 372 */     StringBuilder sql = new StringBuilder();
/*  328: 373 */     sql.append(" SELECT COUNT(ofa) ");
/*  329: 374 */     sql.append(" FROM OrdenFabricacion ofa ");
/*  330: 375 */     sql.append(" INNER JOIN ofa.producto p ");
/*  331: 376 */     sql.append(" INNER JOIN ofa.planProduccion pp ");
/*  332: 377 */     sql.append(" WHERE p.idProducto =:idProducto ");
/*  333: 378 */     sql.append(" AND pp.idPlanProduccion =:idPlanProduccion ");
/*  334: 379 */     sql.append(" AND ofa.fecha =:fecha ");
/*  335: 380 */     sql.append(" AND ofa.estado != :estadoAnulado ");
/*  336: 381 */     sql.append(" AND ofa.estado != :estadoFinalizado ");
/*  337:     */     
/*  338: 383 */     Query query = this.em.createQuery(sql.toString());
/*  339: 384 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  340: 385 */     query.setParameter("idPlanProduccion", Integer.valueOf(planProduccion.getId()));
/*  341: 386 */     query.setParameter("fecha", fecha);
/*  342: 387 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/*  343: 388 */     query.setParameter("estadoFinalizado", EstadoProduccionEnum.FINALIZADA);
/*  344: 389 */     long valor = 0L;
/*  345:     */     try
/*  346:     */     {
/*  347: 391 */       valor = ((Long)query.getSingleResult()).longValue();
/*  348:     */     }
/*  349:     */     catch (NoResultException e)
/*  350:     */     {
/*  351: 393 */       return false;
/*  352:     */     }
/*  353: 396 */     return valor > 0L;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public BigDecimal actualizarCantidadFabricada(OrdenFabricacion ordenFabricacion, BigDecimal cantidad)
/*  357:     */   {
/*  358: 400 */     BigDecimal cantidadBatchs = cantidad.divide(ordenFabricacion.getProducto().getCantidadProduccion(), 2, RoundingMode.HALF_UP);
/*  359: 401 */     StringBuilder sql = new StringBuilder();
/*  360: 402 */     sql.append(" UPDATE OrdenFabricacion ofa ");
/*  361: 403 */     sql.append(" SET ofa.cantidadFabricada = ofa.cantidadFabricada + :cantidad, ofa.cantidadBatchFabricados = ofa.cantidadBatchFabricados + :cantidadBatch ");
/*  362:     */     
/*  363: 405 */     sql.append(" WHERE ofa = :ordenFabricacion");
/*  364:     */     
/*  365: 407 */     Query query = this.em.createQuery(sql.toString());
/*  366: 408 */     query.setParameter("ordenFabricacion", ordenFabricacion);
/*  367: 409 */     query.setParameter("cantidad", cantidad);
/*  368: 410 */     query.setParameter("cantidadBatch", cantidadBatchs);
/*  369: 411 */     query.executeUpdate();
/*  370:     */     
/*  371: 413 */     sql = new StringBuilder();
/*  372: 414 */     sql.append(" SELECT ofa.cantidadFabricada FROM OrdenFabricacion ofa ");
/*  373: 415 */     sql.append(" WHERE ofa = :ordenFabricacion");
/*  374:     */     
/*  375: 417 */     query = this.em.createQuery(sql.toString());
/*  376: 418 */     query.setParameter("ordenFabricacion", ordenFabricacion);
/*  377:     */     
/*  378: 420 */     return (BigDecimal)query.getSingleResult();
/*  379:     */   }
/*  380:     */   
/*  381:     */   public List<OrdenFabricacion> buscarOrdenesPorRangoFechaCosto(int idOrganizacion, Date fechaDesde, Date fechaHasta, boolean costeoSubOrdenes)
/*  382:     */   {
/*  383: 425 */     StringBuilder sql = new StringBuilder();
/*  384: 426 */     sql.append(" SELECT ofa FROM OrdenFabricacion ofa ");
/*  385: 427 */     sql.append(" INNER JOIN FETCH ofa.producto pro ");
/*  386: 428 */     sql.append(" INNER JOIN FETCH ofa.rutaFabricacion rf ");
/*  387: 429 */     sql.append(" WHERE ofa.idOrganizacion = :idOrganizacion ");
/*  388: 430 */     sql.append(" AND (( ofa.tipoCicloProduccionEnum = :tipoCicloCorto ");
/*  389: 431 */     sql.append(" \tAND (ofa.fechaLanzamiento >= :fechaDesde AND ofa.fechaLanzamiento <= :fechaHasta) ");
/*  390:     */     
/*  391:     */ 
/*  392: 434 */     sql.append("\tAND ofa.estado = :estadoFinalizado ");
/*  393: 435 */     sql.append(" \tAND ofa.cantidadFabricada > 0 ) ");
/*  394: 436 */     sql.append(" OR (ofa.tipoCicloProduccionEnum = :tipoCicloLargo ");
/*  395: 437 */     sql.append(" \tAND ofa.fechaLanzamiento <= :fechaHasta ");
/*  396: 438 */     sql.append(" \tAND (ofa.fechaCierre IS NULL OR (ofa.fechaCierre >= :fechaDesde and ofa.fechaCierre <= :fechaHasta)) ");
/*  397: 439 */     sql.append(" \tAND ofa.estado != :estadoAnulado ");
/*  398: 440 */     sql.append(" )) ");
/*  399: 441 */     if (!costeoSubOrdenes) {
/*  400: 442 */       sql.append(" AND ofa.indicadorSuborden = false ");
/*  401:     */     }
/*  402: 445 */     Query query = this.em.createQuery(sql.toString());
/*  403: 446 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  404: 447 */     query.setParameter("fechaDesde", FuncionesUtiles.setFechaMilisegundos(fechaDesde, 0, 0, 0, 0));
/*  405: 448 */     query.setParameter("fechaHasta", FuncionesUtiles.setFechaMilisegundos(fechaHasta, 23, 59, 59, 99));
/*  406: 449 */     query.setParameter("estadoFinalizado", EstadoProduccionEnum.FINALIZADA);
/*  407: 450 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/*  408: 451 */     query.setParameter("tipoCicloCorto", TipoCicloProduccionEnum.CICLO_CORTO);
/*  409: 452 */     query.setParameter("tipoCicloLargo", TipoCicloProduccionEnum.CICLO_LARGO);
/*  410:     */     
/*  411: 454 */     return query.getResultList();
/*  412:     */   }
/*  413:     */   
/*  414:     */   public BigDecimal getCostosMateriales(OrdenFabricacion ordenFabricacion)
/*  415:     */   {
/*  416: 458 */     StringBuilder sql = new StringBuilder();
/*  417: 459 */     sql.append(" SELECT SUM(ip.costoMateriales) ");
/*  418: 460 */     sql.append(" FROM InventarioProducto ip ");
/*  419: 461 */     sql.append(" INNER JOIN ip.detalleMovimientoInventario dmi ");
/*  420: 462 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  421: 463 */     sql.append(" INNER JOIN mi.ordenFabricacion ofa ");
/*  422: 464 */     sql.append(" INNER JOIN ip.documento doc ");
/*  423: 465 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  424: 466 */     sql.append(" AND doc.documentoBase = :documentoIngresoFabricacion ");
/*  425: 467 */     sql.append(" AND mi.estado != :estadoAnulado ");
/*  426:     */     
/*  427: 469 */     Query query = this.em.createQuery(sql.toString());
/*  428: 470 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  429: 471 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  430: 472 */     query.setParameter("documentoIngresoFabricacion", DocumentoBase.INGRESO_PRODUCCION);
/*  431:     */     
/*  432: 474 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/*  433: 475 */     if (resultado == null) {
/*  434: 476 */       resultado = BigDecimal.ZERO;
/*  435:     */     }
/*  436: 478 */     return resultado;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public BigDecimal obtenerValorOrdenFabricacionProrratear(OrdenFabricacion ordenFabricacion, boolean indicadorProrratearHorasHombre, boolean indicadorProrratearHorasHombreXValor, boolean indicadorProrratearHorasMaquina, boolean indicadorProrratearHorasMaquinaXValor)
/*  440:     */   {
/*  441: 483 */     StringBuilder sql = new StringBuilder();
/*  442: 484 */     sql.append(" SELECT SUM(oof.horasHombre), SUM(oof.horasHombre * to.costo), SUM(oof.horasMaquina), SUM(oof.horasMaquina * to.costo) ");
/*  443: 485 */     sql.append(" FROM OperacionOrdenFabricacion oof ");
/*  444: 486 */     sql.append(" INNER JOIN oof.ordenFabricacion ofa ");
/*  445: 487 */     sql.append(" INNER JOIN oof.operacionProduccion op ");
/*  446: 488 */     sql.append(" INNER JOIN op.tareaProduccion tp ");
/*  447: 489 */     sql.append(" INNER JOIN tp.tarifaOperacion to ");
/*  448: 490 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion");
/*  449:     */     
/*  450: 492 */     Query query = this.em.createQuery(sql.toString());
/*  451: 493 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  452:     */     
/*  453: 495 */     Object[] result = (Object[])query.getSingleResult();
/*  454: 496 */     BigDecimal horasHombre = (BigDecimal)result[0];
/*  455: 497 */     BigDecimal horasHombreXCosto = (BigDecimal)result[1];
/*  456: 498 */     BigDecimal horasMaquina = (BigDecimal)result[2];
/*  457: 499 */     BigDecimal horasMaquinaXCosto = (BigDecimal)result[3];
/*  458:     */     
/*  459: 501 */     BigDecimal valor = BigDecimal.ZERO;
/*  460: 503 */     if (indicadorProrratearHorasHombre)
/*  461:     */     {
/*  462: 504 */       System.out.println("horasHombre----------------->" + horasHombre);
/*  463: 505 */       valor = valor.add(horasHombre);
/*  464:     */     }
/*  465: 507 */     if (indicadorProrratearHorasHombreXValor)
/*  466:     */     {
/*  467: 508 */       System.out.println("horasHombreXCosto----------------->" + horasHombreXCosto);
/*  468: 509 */       valor = valor.add(horasHombreXCosto);
/*  469:     */     }
/*  470: 511 */     if (indicadorProrratearHorasMaquina)
/*  471:     */     {
/*  472: 512 */       System.out.println("horasMaquina----------------->" + horasMaquina);
/*  473: 513 */       valor = valor.add(horasMaquina);
/*  474:     */     }
/*  475: 515 */     if (indicadorProrratearHorasMaquinaXValor)
/*  476:     */     {
/*  477: 516 */       System.out.println("horasMaquinaXCosto----------------->" + horasMaquinaXCosto);
/*  478: 517 */       valor = valor.add(horasMaquinaXCosto);
/*  479:     */     }
/*  480: 520 */     System.out.println("peso----------------->" + valor);
/*  481: 521 */     return valor;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public int reabrirOrden(OrdenFabricacion ordenFabricacion)
/*  485:     */   {
/*  486: 525 */     StringBuilder sql = new StringBuilder();
/*  487: 526 */     sql.append(" UPDATE OrdenFabricacion ofa ");
/*  488: 527 */     sql.append(" SET ofa.estado = :estadoLanzada, ");
/*  489: 528 */     sql.append(" ofa.fechaCierre = null ");
/*  490: 529 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  491: 530 */     sql.append(" AND ofa.estado = :estadoFinalizada ");
/*  492:     */     
/*  493: 532 */     Query query = this.em.createQuery(sql.toString());
/*  494: 533 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  495: 534 */     query.setParameter("estadoLanzada", EstadoProduccionEnum.LANZADA);
/*  496: 535 */     query.setParameter("estadoFinalizada", EstadoProduccionEnum.FINALIZADA);
/*  497:     */     
/*  498: 537 */     return query.executeUpdate();
/*  499:     */   }
/*  500:     */   
/*  501:     */   public List<DetalleOrdenFabricacionMaterial> obtenerDetalleOrdenFabricacionMaterial(OrdenFabricacion ordenFabricacion, DetalleOrdenFabricacionMaterial detallePadre)
/*  502:     */   {
/*  503: 543 */     StringBuilder sql = new StringBuilder();
/*  504: 544 */     sql.append(" SELECT dofm ");
/*  505: 545 */     sql.append(" FROM DetalleOrdenFabricacionMaterial dofm ");
/*  506: 546 */     sql.append(" INNER JOIN FETCH dofm.ordenFabricacion ofa ");
/*  507: 547 */     sql.append(" LEFT JOIN dofm.detalleOrdenFabricacionMaterialPadre dofmp ");
/*  508: 548 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  509: 549 */     sql.append(" AND dofmp = :detallePadre ");
/*  510: 550 */     sql.append(" AND dofm.activo IS TRUE ");
/*  511:     */     
/*  512: 552 */     Query query = this.em.createQuery(sql.toString());
/*  513: 553 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  514: 554 */     query.setParameter("detallePadre", detallePadre);
/*  515:     */     
/*  516: 556 */     return query.getResultList();
/*  517:     */   }
/*  518:     */   
/*  519:     */   public List<DetalleOrdenFabricacionMaterial> obtenerMaterialesDetalleOrdenFabricacionMaterial(OrdenFabricacion ordenFabricacion, Boolean indicadorHojas)
/*  520:     */   {
/*  521: 562 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  522:     */     
/*  523:     */ 
/*  524: 565 */     CriteriaQuery<DetalleOrdenFabricacionMaterial> cqCabecera = criteriaBuilder.createQuery(DetalleOrdenFabricacionMaterial.class);
/*  525: 566 */     Root<DetalleOrdenFabricacionMaterial> fromCabecera = cqCabecera.from(DetalleOrdenFabricacionMaterial.class);
/*  526: 567 */     fromCabecera.fetch("ordenFabricacion", JoinType.LEFT).fetch("producto", JoinType.LEFT);
/*  527: 568 */     fromCabecera.fetch("material", JoinType.LEFT);
/*  528: 569 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  529: 570 */     fromCabecera.fetch("lote", JoinType.LEFT);
/*  530:     */     
/*  531:     */ 
/*  532: 573 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  533: 574 */     conjunction.getExpressions().add(criteriaBuilder.equal(fromCabecera.get("ordenFabricacion"), ordenFabricacion));
/*  534: 575 */     conjunction.getExpressions().add(criteriaBuilder.equal(fromCabecera.get("ordenFabricacion").get("producto").get("activo"), Boolean.valueOf(true)));
/*  535: 576 */     if (indicadorHojas != null) {
/*  536: 577 */       conjunction.getExpressions().add(criteriaBuilder.equal(fromCabecera.get("indicadorHoja"), indicadorHojas));
/*  537:     */     }
/*  538: 579 */     cqCabecera.where((Predicate[])conjunction.getExpressions().toArray(new Predicate[conjunction.getExpressions().size()]));
/*  539: 580 */     cqCabecera.orderBy(new Order[] { criteriaBuilder.asc(fromCabecera.get("ordenFabricacion").get("producto").get("nombre")) });
/*  540:     */     
/*  541: 582 */     CriteriaQuery<DetalleOrdenFabricacionMaterial> select = cqCabecera.select(fromCabecera);
/*  542: 583 */     List<DetalleOrdenFabricacionMaterial> lista = this.em.createQuery(select).getResultList();
/*  543: 586 */     for (DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial : lista)
/*  544:     */     {
/*  545: 587 */       CriteriaQuery<DetalleOrdenFabricacionMaterialMezcla> cqDetalle = criteriaBuilder.createQuery(DetalleOrdenFabricacionMaterialMezcla.class);
/*  546: 588 */       Root<DetalleOrdenFabricacionMaterialMezcla> fromDetalle = cqDetalle.from(DetalleOrdenFabricacionMaterialMezcla.class);
/*  547: 589 */       fromDetalle.fetch("producto", JoinType.LEFT);
/*  548: 590 */       cqDetalle.where(criteriaBuilder.equal(fromDetalle.get("detalleOrdenFabricacionMaterial"), detalleOrdenFabricacionMaterial));
/*  549: 591 */       cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("producto").get("nombre")) });
/*  550: 592 */       CriteriaQuery<DetalleOrdenFabricacionMaterialMezcla> selectDetalle = cqDetalle.select(fromDetalle);
/*  551: 593 */       List<DetalleOrdenFabricacionMaterialMezcla> listaDetalle = this.em.createQuery(selectDetalle).getResultList();
/*  552: 594 */       detalleOrdenFabricacionMaterial.setListaDetalleOrdenFabricacionMaterialMezcla(listaDetalle);
/*  553:     */     }
/*  554: 597 */     return lista;
/*  555:     */   }
/*  556:     */   
/*  557:     */   public List<OrdenFabricacion> buscarOrdenesFabricacionFormuladasPorProducto(Producto producto)
/*  558:     */   {
/*  559: 603 */     StringBuilder sql = new StringBuilder();
/*  560: 604 */     sql.append(" SELECT distinct(ofa) ");
/*  561: 605 */     sql.append(" FROM DetalleOrdenFabricacionMaterial dofm ");
/*  562: 606 */     sql.append(" INNER JOIN dofm.ordenFabricacion ofa ");
/*  563: 607 */     sql.append(" INNER JOIN FETCH ofa.producto prod ");
/*  564: 608 */     sql.append(" WHERE prod.idProducto = :idProducto ");
/*  565: 609 */     sql.append(" AND dofm.indicadorHoja IS TRUE ");
/*  566: 610 */     sql.append(" AND dofm.activo IS TRUE ");
/*  567: 611 */     sql.append(" AND ofa.fechaLanzamiento IS NOT NULL ");
/*  568: 612 */     sql.append(" AND ofa.estado != :estadoAnulado ");
/*  569: 613 */     sql.append(" ORDER BY ofa.fechaLanzamiento DESC ");
/*  570:     */     
/*  571: 615 */     Query query = this.em.createQuery(sql.toString());
/*  572: 616 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  573: 617 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/*  574:     */     
/*  575: 619 */     return query.getResultList();
/*  576:     */   }
/*  577:     */   
/*  578:     */   public void actualizarCabeceraFormulacionOrdenFabricacion(int idOrdenFabricacion, String descripcionFormula, OrdenFabricacion ordenFabricacionFormulacion, boolean indicadorFormulada, BigDecimal cantidadFormulacion, Date fechaFormulacion)
/*  579:     */   {
/*  580: 624 */     StringBuilder sql = new StringBuilder();
/*  581: 625 */     sql.append(" UPDATE OrdenFabricacion ofa ");
/*  582: 626 */     sql.append(" SET ofa.descripcionFormula = :descripcionFormula, ");
/*  583: 627 */     sql.append(" ofa.ordenFabricacionFormulacion = :ordenFabricacionFormulacion, ");
/*  584: 628 */     sql.append(" ofa.indicadorFormulada = :indicadorFormulada, ");
/*  585: 629 */     sql.append(" ofa.cantidadFormulacion = :cantidadFormulacion, ");
/*  586: 630 */     sql.append(" ofa.fechaFormulacion = :fechaFormulacion ");
/*  587: 631 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  588:     */     
/*  589: 633 */     Query query = this.em.createQuery(sql.toString());
/*  590: 634 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(idOrdenFabricacion));
/*  591: 635 */     query.setParameter("descripcionFormula", descripcionFormula);
/*  592: 636 */     query.setParameter("ordenFabricacionFormulacion", ordenFabricacionFormulacion);
/*  593: 637 */     query.setParameter("indicadorFormulada", Boolean.valueOf(indicadorFormulada));
/*  594: 638 */     query.setParameter("cantidadFormulacion", cantidadFormulacion);
/*  595: 639 */     query.setParameter("fechaFormulacion", fechaFormulacion);
/*  596:     */     
/*  597: 641 */     query.executeUpdate();
/*  598:     */   }
/*  599:     */   
/*  600:     */   public List<OrdenFabricacion> obtenerListaSubordenFabricacion(int idOrdenFabricacion, boolean indicadorSoloInmediatos)
/*  601:     */   {
/*  602: 645 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  603:     */     
/*  604:     */ 
/*  605: 648 */     CriteriaQuery<OrdenFabricacion> cqCabecera = criteriaBuilder.createQuery(OrdenFabricacion.class);
/*  606: 649 */     Root<OrdenFabricacion> fromCabecera = cqCabecera.from(OrdenFabricacion.class);
/*  607: 650 */     fromCabecera.fetch("producto");
/*  608: 651 */     fromCabecera.fetch("rutaFabricacion");
/*  609: 652 */     fromCabecera.fetch("bodega", JoinType.LEFT);
/*  610: 653 */     fromCabecera.fetch("documento").fetch("secuencia", JoinType.LEFT);
/*  611: 654 */     fromCabecera.fetch("ordenFabricacionFormulacion", JoinType.LEFT);
/*  612: 655 */     fromCabecera.fetch("ordenSalidaMaterialPrincipal", JoinType.LEFT);
/*  613: 656 */     fromCabecera.fetch("ordenFabricacionPadre", JoinType.INNER);
/*  614: 657 */     fromCabecera.fetch("ordenFabricacionPrincipal", JoinType.INNER);
/*  615: 658 */     fromCabecera.fetch("atributoOrdenFabricacion", JoinType.LEFT);
/*  616: 659 */     fromCabecera.fetch("valorAtributoOrdenFabricacion", JoinType.LEFT);
/*  617: 661 */     if (indicadorSoloInmediatos) {
/*  618: 662 */       cqCabecera.where(criteriaBuilder.equal(fromCabecera.join("ordenFabricacionPadre").get("idOrdenFabricacion"), Integer.valueOf(idOrdenFabricacion)));
/*  619:     */     } else {
/*  620: 664 */       cqCabecera.where(criteriaBuilder.equal(fromCabecera.join("ordenFabricacionPrincipal").get("idOrdenFabricacion"), Integer.valueOf(idOrdenFabricacion)));
/*  621:     */     }
/*  622: 666 */     CriteriaQuery<OrdenFabricacion> select = cqCabecera.select(fromCabecera);
/*  623:     */     
/*  624: 668 */     List<OrdenFabricacion> listaSubordenFabricacion = this.em.createQuery(select).getResultList();
/*  625:     */     
/*  626: 670 */     return listaSubordenFabricacion;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public List<VariableCalidadOrdenFabricacion> getListaVariableCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/*  630:     */   {
/*  631: 676 */     StringBuilder sql = new StringBuilder();
/*  632: 677 */     sql.append(" SELECT vcof ");
/*  633: 678 */     sql.append(" FROM VariableCalidadOrdenFabricacion vcof ");
/*  634: 679 */     sql.append(" INNER JOIN FETCH vcof.variableCalidadProducto vcp ");
/*  635: 680 */     sql.append(" INNER JOIN FETCH vcp.variableCalidad vc ");
/*  636: 681 */     sql.append(" INNER JOIN FETCH vc.categoriaVariableCalidad cvc ");
/*  637: 682 */     sql.append(" INNER JOIN FETCH vcof.historicoCalidadOrdenFabricacion hcof ");
/*  638: 683 */     sql.append(" WHERE hcof.idHistoricoCalidadOrdenFabricacion = :idHistoricoCalidadOrdenFabricacion ");
/*  639: 684 */     Query query = this.em.createQuery(sql.toString());
/*  640: 685 */     query.setParameter("idHistoricoCalidadOrdenFabricacion", Integer.valueOf(historicoCalidadOrdenFabricacion.getId()));
/*  641:     */     
/*  642: 687 */     return query.getResultList();
/*  643:     */   }
/*  644:     */   
/*  645:     */   @Lock(LockType.WRITE)
/*  646:     */   public void actualizarCantidadControlCalidad(OrdenFabricacion ordenFabricacion, BigDecimal cantidadControlCalidad, String campo)
/*  647:     */     throws AS2Exception
/*  648:     */   {
/*  649: 693 */     if (cantidadControlCalidad.compareTo(BigDecimal.ZERO) < 0)
/*  650:     */     {
/*  651: 695 */       StringBuilder sql = new StringBuilder();
/*  652: 696 */       sql.append(" SELECT ofa." + campo);
/*  653: 697 */       sql.append(" FROM OrdenFabricacion ofa ");
/*  654: 698 */       sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  655: 699 */       Query query = this.em.createQuery(sql.toString());
/*  656: 700 */       query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  657:     */       
/*  658: 702 */       BigDecimal cantidadAnterior = (BigDecimal)query.getSingleResult();
/*  659: 703 */       if (cantidadAnterior == null) {
/*  660: 704 */         cantidadAnterior = BigDecimal.ZERO;
/*  661:     */       }
/*  662: 706 */       if (cantidadAnterior.add(cantidadControlCalidad).compareTo(BigDecimal.ZERO) < 0) {
/*  663: 708 */         throw new AS2Exception("ERROR_CANTIDAD_CALIDAD_ORDEN_FABRICACION", new String[] { campo, cantidadAnterior + " < " + cantidadControlCalidad.abs() });
/*  664:     */       }
/*  665:     */     }
/*  666: 711 */     StringBuilder sql = new StringBuilder();
/*  667: 712 */     sql.append(" UPDATE OrdenFabricacion ofa SET ofa." + campo);
/*  668: 713 */     sql.append(" = ofa." + campo + " + :cantidadControlCalidad ");
/*  669: 714 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  670: 715 */     Query query = this.em.createQuery(sql.toString());
/*  671: 716 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  672: 717 */     query.setParameter("cantidadControlCalidad", cantidadControlCalidad);
/*  673:     */     
/*  674: 719 */     query.executeUpdate();
/*  675:     */   }
/*  676:     */   
/*  677:     */   public List<Object[]> getReporteInspeccionCalidadPT(Date fechaDesde, Date fechaHasta, EstadoControlCalidad estado, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, OrdenFabricacion ordenFabricacion)
/*  678:     */   {
/*  679: 725 */     StringBuilder sql = new StringBuilder();
/*  680: 726 */     sql.append(" SELECT hcof.fechaControlCalidad, ofa.numero, prod.codigo, prod.nombre, hcof.cantidad, hcof.cantidad * prod.peso, vc.nombre, vcof.valorNir, cv.nombre ");
/*  681: 727 */     sql.append(" FROM VariableCalidadOrdenFabricacion vcof ");
/*  682: 728 */     sql.append(" INNER JOIN vcof.historicoCalidadOrdenFabricacion hcof ");
/*  683: 729 */     sql.append(" INNER JOIN hcof.ordenFabricacion ofa ");
/*  684: 730 */     sql.append(" INNER JOIN ofa.producto prod ");
/*  685: 731 */     sql.append(" INNER JOIN prod.subcategoriaProducto scp ");
/*  686: 732 */     sql.append(" INNER JOIN scp.categoriaProducto cp ");
/*  687: 733 */     sql.append(" INNER JOIN vcof.variableCalidadProducto vcp ");
/*  688: 734 */     sql.append(" INNER JOIN vcp.variableCalidad vc ");
/*  689: 735 */     sql.append(" INNER JOIN vc.categoriaVariableCalidad cv ");
/*  690:     */     
/*  691: 737 */     sql.append(" WHERE hcof.fechaControlCalidad between :fechaDesde AND :fechaHasta ");
/*  692: 738 */     sql.append(" AND ofa.indicadorSuborden = false ");
/*  693: 739 */     if (estado != null) {
/*  694: 740 */       sql.append(" AND hcof.estado = :estado ");
/*  695:     */     }
/*  696: 742 */     if (categoriaProducto != null) {
/*  697: 743 */       sql.append(" AND cp.idCategoriaProducto = :idCategoriaProducto ");
/*  698:     */     }
/*  699: 745 */     if (subcategoriaProducto != null) {
/*  700: 746 */       sql.append(" AND scp.idSubcategoriaProducto = :idSubcategoriaProducto ");
/*  701:     */     }
/*  702: 748 */     if (producto != null) {
/*  703: 749 */       sql.append(" AND prod.idProducto = :idProducto ");
/*  704:     */     }
/*  705: 751 */     if (ordenFabricacion != null) {
/*  706: 752 */       sql.append(" AND ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  707:     */     }
/*  708: 755 */     Query query = this.em.createQuery(sql.toString());
/*  709: 756 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  710: 757 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  711: 758 */     if (estado != null) {
/*  712: 759 */       query.setParameter("estado", estado);
/*  713:     */     }
/*  714: 761 */     if (categoriaProducto != null) {
/*  715: 762 */       query.setParameter("idCategoriaProducto", Integer.valueOf(categoriaProducto.getId()));
/*  716:     */     }
/*  717: 764 */     if (subcategoriaProducto != null) {
/*  718: 765 */       query.setParameter("idSubcategoriaProducto", Integer.valueOf(subcategoriaProducto.getId()));
/*  719:     */     }
/*  720: 767 */     if (producto != null) {
/*  721: 768 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  722:     */     }
/*  723: 770 */     if (ordenFabricacion != null) {
/*  724: 771 */       query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  725:     */     }
/*  726: 774 */     return query.getResultList();
/*  727:     */   }
/*  728:     */   
/*  729:     */   public List<Object[]> getReporteFormulacion(OrdenFabricacion ordenFabricacion)
/*  730:     */   {
/*  731: 779 */     StringBuilder sql = new StringBuilder();
/*  732: 780 */     sql.append(" SELECT ofa.numero, prod.codigo, prod.nombre, prodp.codigo, prodp.nombre, ofa.cantidadFormulacion, ofap.cantidad, prodp.peso, prod.cantidadProduccion ");
/*  733:     */     
/*  734: 782 */     sql.append(" , ofa.descripcionFormula, mat.codigo, mat.nombre, dofm.cantidad, uni.nombre, lot.codigo, mat.tipoMaterialEnum, ofa.descripcion, dofm.cantidadPorCadaBatch, ofa.fechaFormulacion, dofm.indicadorConsumoDirecto ");
/*  735:     */     
/*  736: 784 */     sql.append(" FROM DetalleOrdenFabricacionMaterial dofm ");
/*  737: 785 */     sql.append(" INNER JOIN dofm.ordenFabricacion ofa ");
/*  738: 786 */     sql.append(" INNER JOIN dofm.material mat ");
/*  739: 787 */     sql.append(" INNER JOIN dofm.unidad uni ");
/*  740: 788 */     sql.append(" LEFT JOIN dofm.lote lot ");
/*  741: 789 */     sql.append(" INNER JOIN ofa.producto prod ");
/*  742: 790 */     sql.append(" LEFT JOIN ofa.ordenFabricacionPrincipal ofap ");
/*  743: 791 */     sql.append(" LEFT JOIN ofap.producto prodp ");
/*  744:     */     
/*  745: 793 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  746: 794 */     sql.append(" AND dofm.activo = true ");
/*  747: 795 */     sql.append(" AND dofm.indicadorHoja = true ");
/*  748: 796 */     sql.append(" ORDER BY mat.tipoMaterialEnum ");
/*  749: 797 */     Query query = this.em.createQuery(sql.toString());
/*  750: 798 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  751:     */     
/*  752: 800 */     return query.getResultList();
/*  753:     */   }
/*  754:     */   
/*  755:     */   public BigDecimal obtenerCantidadFabricada(OrdenFabricacion ordenFabricacion, Date fechaDesde, Date fechaHasta)
/*  756:     */   {
/*  757: 804 */     StringBuilder sql = new StringBuilder();
/*  758: 805 */     sql.append(" SELECT SUM(dmi.cantidad) ");
/*  759: 806 */     sql.append(" FROM DetalleMovimientoInventario dmi ");
/*  760: 807 */     sql.append(" INNER JOIN dmi.movimientoInventario mi ");
/*  761: 808 */     sql.append(" INNER JOIN mi.documento doc ");
/*  762: 809 */     sql.append(" INNER JOIN mi.ordenFabricacion ofa ");
/*  763: 810 */     sql.append(" WHERE doc.documentoBase = :documentoBaseIngresoFabricacion ");
/*  764: 811 */     sql.append(" AND ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  765: 812 */     if (fechaDesde != null) {
/*  766: 813 */       sql.append(" AND mi.fecha >= :fechaDesde ");
/*  767:     */     }
/*  768: 815 */     if (fechaHasta != null) {
/*  769: 816 */       sql.append(" AND mi.fecha <= :fechaHasta ");
/*  770:     */     }
/*  771: 818 */     sql.append(" AND dmi.indicadorRecibido = true ");
/*  772: 819 */     sql.append(" AND mi.estado != :estadoAnulado ");
/*  773:     */     
/*  774: 821 */     Query query = this.em.createQuery(sql.toString());
/*  775: 822 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  776: 823 */     query.setParameter("documentoBaseIngresoFabricacion", DocumentoBase.INGRESO_PRODUCCION);
/*  777: 824 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/*  778: 825 */     if (fechaDesde != null) {
/*  779: 826 */       query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  780:     */     }
/*  781: 828 */     if (fechaHasta != null) {
/*  782: 829 */       query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  783:     */     }
/*  784: 832 */     BigDecimal cantidad = BigDecimal.ZERO;
/*  785:     */     try
/*  786:     */     {
/*  787: 834 */       cantidad = (BigDecimal)query.getSingleResult();
/*  788:     */     }
/*  789:     */     catch (NoResultException localNoResultException) {}
/*  790: 839 */     if (cantidad == null) {
/*  791: 840 */       cantidad = BigDecimal.ZERO;
/*  792:     */     }
/*  793: 843 */     return cantidad;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public List<DetalleOrdenSalidaMaterialOrdenFabricacion> obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(OrdenFabricacion ordenFabricacion, boolean indicadorNoCerradas)
/*  797:     */   {
/*  798: 849 */     StringBuilder sql = new StringBuilder();
/*  799: 850 */     sql.append(" SELECT dosmof ");
/*  800: 851 */     sql.append(" FROM DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/*  801: 852 */     sql.append(" INNER JOIN FETCH dosmof.ordenFabricacion ofa ");
/*  802: 853 */     sql.append(" INNER JOIN FETCH dosmof.detalleOrdenSalidaMaterial dosm ");
/*  803: 854 */     sql.append(" INNER JOIN FETCH dosm.ordenSalidaMaterial osm ");
/*  804: 855 */     sql.append(" INNER JOIN FETCH dosm.producto prod ");
/*  805: 856 */     sql.append(" INNER JOIN FETCH prod.unidad un ");
/*  806: 857 */     sql.append(" LEFT JOIN FETCH dosm.unidad unDet ");
/*  807: 858 */     sql.append(" LEFT JOIN FETCH dosm.lote lot ");
/*  808: 859 */     sql.append(" LEFT JOIN FETCH dosm.bodega bg ");
/*  809: 860 */     sql.append(" LEFT JOIN FETCH prod.unidadInformativa unin ");
/*  810: 861 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  811: 862 */     if (indicadorNoCerradas) {
/*  812: 863 */       sql.append(" AND osm.estado != :estadoCerrado ");
/*  813:     */     }
/*  814: 866 */     Query query = this.em.createQuery(sql.toString());
/*  815: 867 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  816: 868 */     if (indicadorNoCerradas) {
/*  817: 869 */       query.setParameter("estadoCerrado", Estado.CERRADO);
/*  818:     */     }
/*  819: 872 */     List<DetalleOrdenSalidaMaterialOrdenFabricacion> lista = query.getResultList();
/*  820: 873 */     for (DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion : lista)
/*  821:     */     {
/*  822: 874 */       List<LecturaBalanza> listaLecturaBalanza = buscarListaLecturaBalanzaDetalleOrdenSalidaMaterialOrdenFabricacion(detalleOrdenSalidaMaterialOrdenFabricacion);
/*  823:     */       
/*  824: 876 */       detalleOrdenSalidaMaterialOrdenFabricacion.setListaLecturaBalanza(listaLecturaBalanza);
/*  825:     */     }
/*  826: 879 */     return lista;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public List<LecturaBalanza> buscarListaLecturaBalanzaDetalleOrdenSalidaMaterialOrdenFabricacion(DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion)
/*  830:     */   {
/*  831: 885 */     StringBuilder sql2 = new StringBuilder();
/*  832: 886 */     sql2.append(" SELECT lb ");
/*  833: 887 */     sql2.append(" FROM LecturaBalanza lb ");
/*  834: 888 */     sql2.append(" INNER JOIN FETCH lb.detalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/*  835: 889 */     sql2.append(" INNER JOIN FETCH lb.producto ");
/*  836: 890 */     sql2.append(" LEFT JOIN FETCH lb.unidadManejo ");
/*  837: 891 */     sql2.append(" LEFT JOIN FETCH lb.pallet ");
/*  838: 892 */     sql2.append(" WHERE dosmof.idDetalleOrdenSalidaMaterialOrdenFabricacion = :idDetalleOrdenSalidaMaterialOrdenFabricacion ");
/*  839:     */     
/*  840: 894 */     Query query2 = this.em.createQuery(sql2.toString());
/*  841: 895 */     query2.setParameter("idDetalleOrdenSalidaMaterialOrdenFabricacion", Integer.valueOf(detalleOrdenSalidaMaterialOrdenFabricacion.getId()));
/*  842: 896 */     return query2.getResultList();
/*  843:     */   }
/*  844:     */   
/*  845:     */   @Lock(LockType.WRITE)
/*  846:     */   public void actualizarCantidadDesecho(DetalleOrdenSalidaMaterialOrdenFabricacion detalleOrdenSalidaMaterialOrdenFabricacion, BigDecimal cantidadDesecho, BigDecimal cantidadDesechoInformativa, boolean indicadorSobreescribir)
/*  847:     */     throws AS2Exception
/*  848:     */   {
/*  849: 904 */     StringBuilder sql1 = new StringBuilder();
/*  850: 905 */     sql1.append(" SELECT dosmof.cantidadDesecho, dosmof.cantidadDesechoUnidadInformativa ");
/*  851: 906 */     sql1.append(" FROM DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/*  852: 907 */     sql1.append(" WHERE dosmof.idDetalleOrdenSalidaMaterialOrdenFabricacion = :idDetalleOrdenSalidaMaterialOrdenFabricacion");
/*  853:     */     
/*  854: 909 */     Query query1 = this.em.createQuery(sql1.toString());
/*  855: 910 */     query1.setParameter("idDetalleOrdenSalidaMaterialOrdenFabricacion", Integer.valueOf(detalleOrdenSalidaMaterialOrdenFabricacion.getId()));
/*  856:     */     
/*  857: 912 */     Object[] cantidades = (Object[])query1.getSingleResult();
/*  858: 913 */     BigDecimal cantidadDesechoAnterior = (BigDecimal)cantidades[0];
/*  859: 914 */     BigDecimal cantidadDesechoInformativaAnterior = (BigDecimal)cantidades[1];
/*  860: 916 */     if (cantidadDesechoAnterior == null) {
/*  861: 917 */       cantidadDesechoAnterior = BigDecimal.ZERO;
/*  862:     */     }
/*  863: 919 */     if (cantidadDesechoInformativaAnterior == null) {
/*  864: 920 */       cantidadDesechoInformativaAnterior = BigDecimal.ZERO;
/*  865:     */     }
/*  866: 923 */     if (!indicadorSobreescribir)
/*  867:     */     {
/*  868: 924 */       cantidadDesecho = cantidadDesecho.add(cantidadDesechoAnterior);
/*  869: 925 */       if (cantidadDesechoInformativa != null) {
/*  870: 926 */         cantidadDesechoInformativa = cantidadDesechoInformativa.add(cantidadDesechoInformativaAnterior);
/*  871:     */       }
/*  872:     */     }
/*  873: 931 */     if ((cantidadDesecho.compareTo(BigDecimal.ZERO) < 0) || ((cantidadDesechoInformativa != null) && 
/*  874: 932 */       (cantidadDesechoInformativa.compareTo(BigDecimal.ZERO) < 0))) {
/*  875: 934 */       throw new AS2Exception("msg_error_cantidad_desecho_negativa", new String[] {detalleOrdenSalidaMaterialOrdenFabricacion.getDetalleOrdenSalidaMaterial().getProducto().getNombre() });
/*  876:     */     }
/*  877: 938 */     StringBuilder sql2 = new StringBuilder();
/*  878: 939 */     sql2.append(" UPDATE DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/*  879: 940 */     sql2.append(" SET dosmof.cantidadDesecho = :cantidadDesecho ");
/*  880: 941 */     if (cantidadDesechoInformativa != null) {
/*  881: 942 */       sql2.append(" ,dosmof.cantidadDesechoUnidadInformativa = :cantidadDesechoInformativa ");
/*  882:     */     }
/*  883: 944 */     sql2.append(" WHERE dosmof.idDetalleOrdenSalidaMaterialOrdenFabricacion = :idDetalleOrdenSalidaMaterialOrdenFabricacion");
/*  884:     */     
/*  885: 946 */     Query query2 = this.em.createQuery(sql2.toString());
/*  886: 947 */     query2.setParameter("idDetalleOrdenSalidaMaterialOrdenFabricacion", Integer.valueOf(detalleOrdenSalidaMaterialOrdenFabricacion.getId()));
/*  887: 948 */     query2.setParameter("cantidadDesecho", cantidadDesecho);
/*  888: 949 */     if (cantidadDesechoInformativa != null) {
/*  889: 950 */       query2.setParameter("cantidadDesechoInformativa", cantidadDesechoInformativa);
/*  890:     */     }
/*  891: 952 */     query2.executeUpdate();
/*  892:     */     
/*  893:     */ 
/*  894: 955 */     StringBuilder sql3 = new StringBuilder();
/*  895: 956 */     sql3.append(" UPDATE DetalleOrdenSalidaMaterial dosm ");
/*  896: 957 */     sql3.append(" SET dosm.cantidadDesecho = dosm.cantidadDesecho + :cantidadDesecho ");
/*  897: 958 */     sql3.append(" WHERE dosm.idDetalleOrdenSalidaMaterial = :idDetalleOrdenSalidaMaterial ");
/*  898: 959 */     Query query3 = this.em.createQuery(sql3.toString());
/*  899: 960 */     query3.setParameter("idDetalleOrdenSalidaMaterial", Integer.valueOf(detalleOrdenSalidaMaterialOrdenFabricacion.getDetalleOrdenSalidaMaterial().getId()));
/*  900: 961 */     query3.setParameter("cantidadDesecho", cantidadDesecho.subtract(cantidadDesechoAnterior));
/*  901: 962 */     query3.executeUpdate();
/*  902:     */   }
/*  903:     */   
/*  904:     */   public List<Object[]> getReporteRegistroDesecho(OrdenFabricacion ordenFabricacion)
/*  905:     */   {
/*  906: 967 */     StringBuilder sql = new StringBuilder();
/*  907: 968 */     sql.append(" SELECT ofa.numero, pro.codigo, pro.nombre, ofa.descripcion, ofa.cantidad, ofa.cantidadBatch, ofa.cantidadFabricada, ofa.cantidadBatchFabricados, pro.cantidadProduccion ");
/*  908:     */     
/*  909: 970 */     sql.append(" , osm.numero, mat.codigo, mat.nombre, lot.codigo, bg.nombre, un.nombre, dosmof.cantidad, dosmof.cantidadDesecho, unin.nombre, dosmof.cantidadDesechoUnidadInformativa ");
/*  910:     */     
/*  911: 972 */     sql.append(" FROM DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/*  912: 973 */     sql.append(" INNER JOIN dosmof.ordenFabricacion ofa ");
/*  913: 974 */     sql.append(" INNER JOIN ofa.producto pro ");
/*  914: 975 */     sql.append(" INNER JOIN dosmof.detalleOrdenSalidaMaterial dosm ");
/*  915: 976 */     sql.append(" INNER JOIN dosm.ordenSalidaMaterial osm ");
/*  916: 977 */     sql.append(" INNER JOIN dosm.producto mat ");
/*  917: 978 */     sql.append(" INNER JOIN mat.unidad un ");
/*  918: 979 */     sql.append(" LEFT JOIN dosm.unidad unDet ");
/*  919: 980 */     sql.append(" LEFT JOIN dosm.lote lot ");
/*  920: 981 */     sql.append(" LEFT JOIN dosm.bodega bg ");
/*  921: 982 */     sql.append(" LEFT JOIN mat.unidadInformativa unin ");
/*  922: 983 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  923:     */     
/*  924: 985 */     Query query = this.em.createQuery(sql.toString());
/*  925: 986 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  926:     */     
/*  927: 988 */     return query.getResultList();
/*  928:     */   }
/*  929:     */   
/*  930:     */   public List<OperacionOrdenFabricacion> getOperacionOrdenFabricacionPorAnioMes(int idOrganizacion, OrdenFabricacion ordenFabricacion, int anio, int mes, boolean costeoSubOrdenes)
/*  931:     */   {
/*  932: 995 */     StringBuilder sql = new StringBuilder();
/*  933: 996 */     sql.append(" SELECT oop ");
/*  934: 997 */     sql.append(" FROM  OperacionOrdenFabricacion oop ");
/*  935: 998 */     sql.append(" INNER JOIN FETCH oop.operacionProduccion op");
/*  936: 999 */     sql.append(" INNER JOIN FETCH oop.ordenFabricacion ofa ");
/*  937:1000 */     sql.append(" INNER JOIN FETCH ofa.rutaFabricacion rf ");
/*  938:1001 */     sql.append(" LEFT  JOIN FETCH op.tareaProduccion tp ");
/*  939:1002 */     sql.append(" LEFT  JOIN FETCH op.centroTrabajo ct  ");
/*  940:1003 */     sql.append(" LEFT  JOIN FETCH op.maquina m ");
/*  941:     */     
/*  942:1005 */     sql.append(" WHERE oop.mes = :mes ");
/*  943:1006 */     sql.append(" AND   oop.anio = :anio ");
/*  944:1007 */     sql.append(" AND   rf.idOrganizacion = :idOrganizacion ");
/*  945:1008 */     if (ordenFabricacion != null) {
/*  946:1009 */       sql.append(" AND   ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/*  947:     */     }
/*  948:1011 */     if (!costeoSubOrdenes) {
/*  949:1012 */       sql.append(" AND   ofa.indicadorSuborden = false ");
/*  950:     */     }
/*  951:1015 */     Query query = this.em.createQuery(sql.toString());
/*  952:1016 */     query.setParameter("anio", Integer.valueOf(anio));
/*  953:1017 */     query.setParameter("mes", Integer.valueOf(mes));
/*  954:1018 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  955:1019 */     if (ordenFabricacion != null) {
/*  956:1020 */       query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/*  957:     */     }
/*  958:1023 */     return query.getResultList();
/*  959:     */   }
/*  960:     */   
/*  961:     */   public List<OrdenFabricacion> getConsultaOrdenFabricacion(Date fechaHasta, TipoCicloProduccionEnum tipoCiclo, EstadoProduccionEnum estado, RutaFabricacion rutaFabricacion)
/*  962:     */   {
/*  963:1031 */     StringBuilder sql = new StringBuilder();
/*  964:1032 */     sql.append(" SELECT ofa FROM OrdenFabricacion ofa ");
/*  965:1033 */     sql.append(" JOIN FETCH ofa.producto p ");
/*  966:1034 */     sql.append(" JOIN FETCH ofa.rutaFabricacion rf ");
/*  967:1035 */     sql.append(" WHERE (ofa.fechaLanzamiento <= :fechaHasta or ofa.fechaLanzamiento is null)");
/*  968:1036 */     if (estado != null) {
/*  969:1037 */       sql.append(" AND   ofa.estado = :estado ");
/*  970:     */     }
/*  971:1039 */     if (tipoCiclo != null) {
/*  972:1040 */       sql.append(" AND   ofa.tipoCicloProduccionEnum = :tipoCiclo ");
/*  973:     */     }
/*  974:1042 */     if (rutaFabricacion != null) {
/*  975:1043 */       sql.append(" AND   rf.idRutaFabricacion = :idRutaFabricacion ");
/*  976:     */     }
/*  977:1046 */     Query query = this.em.createQuery(sql.toString());
/*  978:1047 */     query.setParameter("fechaHasta", FuncionesUtiles.setFechaMilisegundos(fechaHasta, 23, 59, 59, 99));
/*  979:1048 */     if (estado != null) {
/*  980:1049 */       query.setParameter("estado", estado);
/*  981:     */     }
/*  982:1051 */     if (tipoCiclo != null) {
/*  983:1052 */       query.setParameter("tipoCiclo", tipoCiclo);
/*  984:     */     }
/*  985:1054 */     if (rutaFabricacion != null) {
/*  986:1055 */       query.setParameter("idRutaFabricacion", Integer.valueOf(rutaFabricacion.getId()));
/*  987:     */     }
/*  988:1058 */     return query.getResultList();
/*  989:     */   }
/*  990:     */   
/*  991:     */   public List<OrdenFabricacion> obtenerOrdenFabricacionParaIngresoHorasMensual(int idOrganizacion, Date fechaDesde, Date fechaHasta, boolean costeoSubOrdenes)
/*  992:     */   {
/*  993:1063 */     StringBuilder sql = new StringBuilder();
/*  994:1064 */     sql.append(" SELECT ofa FROM OrdenFabricacion ofa ");
/*  995:1065 */     sql.append(" INNER JOIN FETCH ofa.producto pro ");
/*  996:1066 */     sql.append(" INNER JOIN FETCH ofa.rutaFabricacion rf ");
/*  997:1067 */     sql.append(" WHERE ofa.idOrganizacion = :idOrganizacion ");
/*  998:1068 */     sql.append(" AND (( ofa.tipoCicloProduccionEnum = :tipoCicloCorto ");
/*  999:1069 */     sql.append(" \tAND (ofa.fechaLanzamiento >= :fechaDesde AND ofa.fechaLanzamiento <= :fechaHasta) ");
/* 1000:     */     
/* 1001:1071 */     sql.append("\tAND ofa.estado != :estadoAnulado ");
/* 1002:1072 */     sql.append(" \tAND ofa.cantidadFabricada > 0 ) ");
/* 1003:1073 */     sql.append(" OR (ofa.tipoCicloProduccionEnum = :tipoCicloLargo ");
/* 1004:1074 */     sql.append(" \tAND ofa.fechaLanzamiento <= :fechaHasta ");
/* 1005:1075 */     sql.append(" \tAND (ofa.fechaCierre IS NULL OR (ofa.fechaCierre >= :fechaDesde)) ");
/* 1006:1076 */     sql.append(" \tAND ofa.estado != :estadoAnulado ");
/* 1007:1077 */     sql.append(" )) ");
/* 1008:1078 */     if (!costeoSubOrdenes) {
/* 1009:1079 */       sql.append(" AND ofa.indicadorSuborden = false ");
/* 1010:     */     }
/* 1011:1082 */     Query query = this.em.createQuery(sql.toString());
/* 1012:1083 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 1013:1084 */     query.setParameter("fechaDesde", FuncionesUtiles.setFechaMilisegundos(fechaDesde, 0, 0, 0, 0));
/* 1014:1085 */     query.setParameter("fechaHasta", FuncionesUtiles.setFechaMilisegundos(fechaHasta, 23, 59, 59, 99));
/* 1015:     */     
/* 1016:1087 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/* 1017:1088 */     query.setParameter("tipoCicloCorto", TipoCicloProduccionEnum.CICLO_CORTO);
/* 1018:1089 */     query.setParameter("tipoCicloLargo", TipoCicloProduccionEnum.CICLO_LARGO);
/* 1019:     */     
/* 1020:1091 */     return query.getResultList();
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public List<Integer> getVerificaOrdenFabricacionUnico(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial)
/* 1024:     */   {
/* 1025:1097 */     StringBuilder sql = new StringBuilder();
/* 1026:1098 */     sql.append(" SELECT distinct ofa.idOrdenFabricacion  ");
/* 1027:1099 */     sql.append(" FROM DetalleOrdenSalidaMaterialOrdenFabricacion dosmof ");
/* 1028:1100 */     sql.append(" INNER JOIN dosmof.ordenFabricacion ofa ");
/* 1029:1101 */     sql.append(" INNER JOIN dosmof.detalleOrdenSalidaMaterial dosm ");
/* 1030:1102 */     sql.append(" WHERE dosm = :detalleOrdenSalidaMaterial ");
/* 1031:     */     
/* 1032:1104 */     Query query = this.em.createQuery(sql.toString());
/* 1033:1105 */     query.setParameter("detalleOrdenSalidaMaterial", detalleOrdenSalidaMaterial);
/* 1034:     */     
/* 1035:1107 */     return query.getResultList();
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public List<Object[]> getReporteOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 1039:     */   {
/* 1040:1113 */     StringBuilder sql = new StringBuilder();
/* 1041:1114 */     sql.append(" SELECT ofp.numero, ofp.fecha, ofp.fechaLanzamiento, ofp.fechaCierre, ofp.tipoCicloProduccionEnum, ofp.descripcion, pp.codigo, pp.nombre, up.codigo, ");
/* 1042:     */     
/* 1043:1116 */     sql.append(" s.nombre, rfp.nombre, b.nombre, pr.nombres, pr.apellidos, at.nombre, va.nombre, ofp.cantidad, ofp.cantidadBatch, ofp.cantidadFabricada, ofp.cantidadBatchFabricados, pp.cantidadProduccion, ");
/* 1044:     */     
/* 1045:1118 */     sql.append(" ofh.numero, rfh.nombre, ph.codigo, ph.nombre, uh.codigo, ofh.cantidad, ofh.cantidadBatch, ofh.cantidadFabricada, ofh.cantidadBatchFabricados, ph.cantidadProduccion, ");
/* 1046:     */     
/* 1047:1120 */     sql.append(" osmp.numero, osmh.numero ");
/* 1048:1121 */     sql.append(" FROM OrdenFabricacion ofp ");
/* 1049:1122 */     sql.append(" LEFT JOIN ofp.producto pp ");
/* 1050:1123 */     sql.append(" LEFT JOIN ofp.sucursal s ");
/* 1051:1124 */     sql.append(" LEFT JOIN ofp.bodega   b ");
/* 1052:1125 */     sql.append(" LEFT JOIN ofp.personaResponsable pr ");
/* 1053:1126 */     sql.append(" LEFT JOIN ofp.rutaFabricacion rfp ");
/* 1054:1127 */     sql.append(" LEFT JOIN ofp.atributoOrdenFabricacion at ");
/* 1055:1128 */     sql.append(" LEFT JOIN ofp.valorAtributoOrdenFabricacion va ");
/* 1056:1129 */     sql.append(" LEFT JOIN ofp.ordenSalidaMaterialPrincipal osmp ");
/* 1057:1130 */     sql.append(" LEFT JOIN pp.unidad up ");
/* 1058:1131 */     sql.append(" LEFT JOIN ofp.listaSubordenes ofh ");
/* 1059:1132 */     sql.append(" LEFT JOIN ofh.producto ph ");
/* 1060:1133 */     sql.append(" LEFT JOIN ofh.ordenSalidaMaterialPrincipal osmh ");
/* 1061:1134 */     sql.append(" LEFT JOIN ph.unidad uh ");
/* 1062:1135 */     sql.append(" LEFT JOIN ofh.rutaFabricacion rfh ");
/* 1063:1136 */     sql.append(" WHERE ofp.idOrdenFabricacion = :idOrdenFabricacion ");
/* 1064:     */     
/* 1065:1138 */     Query query = this.em.createQuery(sql.toString());
/* 1066:1139 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/* 1067:     */     
/* 1068:1141 */     return query.getResultList();
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public void generarOrdenFabricacionWinCC(OrdenFabricacion ordenFabricacion)
/* 1072:     */   {
/* 1073:1146 */     StoredProcedureQuery query = this.em.createStoredProcedureQuery("sp_generar_orden_fabricacion_wincc");
/* 1074:1147 */     query.registerStoredProcedureParameter("@i_id_orden_fabricacion", Integer.class, ParameterMode.IN);
/* 1075:1148 */     query.setParameter("@i_id_orden_fabricacion", Integer.valueOf(ordenFabricacion.getId()));
/* 1076:1149 */     query.execute();
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public void suspenderOrdenFabricacionWinCC(OrdenFabricacion ordenFabricacion)
/* 1080:     */   {
/* 1081:1153 */     StoredProcedureQuery query = this.em.createStoredProcedureQuery("sp_suspender_orden_fabricacion_wincc");
/* 1082:1154 */     query.registerStoredProcedureParameter("@i_id_orden_fabricacion", Integer.class, ParameterMode.IN);
/* 1083:1155 */     query.setParameter("@i_id_orden_fabricacion", Integer.valueOf(ordenFabricacion.getId()));
/* 1084:1156 */     query.execute();
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public void sincronizarMovimientosProduccion()
/* 1088:     */   {
/* 1089:1160 */     StoredProcedureQuery query = this.em.createStoredProcedureQuery("sp_leer_movimientos_wincc");
/* 1090:1161 */     query.execute();
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public DetalleOrdenFabricacionMaterial getDetalleOrdenFabricacionMaterialConsumoDirecto(Producto producto, OrdenFabricacion ordenFabricacion)
/* 1094:     */   {
/* 1095:1168 */     StringBuilder sql = new StringBuilder();
/* 1096:1169 */     sql.append(" SELECT dofm FROM DetalleOrdenFabricacionMaterial dofm ");
/* 1097:1170 */     sql.append(" INNER JOIN dofm.ordenFabricacion ofa ");
/* 1098:1171 */     sql.append(" INNER JOIN dofm.material ma ");
/* 1099:1172 */     sql.append(" WHERE ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/* 1100:1173 */     sql.append(" AND   ma.idProducto = :idProducto ");
/* 1101:     */     
/* 1102:1175 */     Query query = this.em.createQuery(sql.toString());
/* 1103:1176 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getIdOrdenFabricacion()));
/* 1104:1177 */     query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 1105:     */     
/* 1106:1179 */     List<DetalleOrdenFabricacionMaterial> lista = query.getResultList();
/* 1107:1181 */     if (!lista.isEmpty()) {
/* 1108:1182 */       return (DetalleOrdenFabricacionMaterial)lista.get(0);
/* 1109:     */     }
/* 1110:1184 */     return null;
/* 1111:     */   }
/* 1112:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.OrdenFabricacionDao
 * JD-Core Version:    0.7.0.1
 */