/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
/*   7:    */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.Fetch;
/*  22:    */ import javax.persistence.criteria.Join;
/*  23:    */ import javax.persistence.criteria.JoinType;
/*  24:    */ import javax.persistence.criteria.Path;
/*  25:    */ import javax.persistence.criteria.Predicate;
/*  26:    */ import javax.persistence.criteria.Root;
/*  27:    */ 
/*  28:    */ @Stateless
/*  29:    */ public class PlanProduccionDao
/*  30:    */   extends AbstractDaoAS2<PlanProduccion>
/*  31:    */ {
/*  32:    */   public PlanProduccionDao()
/*  33:    */   {
/*  34: 51 */     super(PlanProduccion.class);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public PlanProduccion cargarDetalle(int id)
/*  38:    */   {
/*  39: 56 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  40:    */     
/*  41:    */ 
/*  42: 59 */     CriteriaQuery<PlanProduccion> cqCabecera = criteriaBuilder.createQuery(PlanProduccion.class);
/*  43: 60 */     Root<PlanProduccion> fromCabecera = cqCabecera.from(PlanProduccion.class);
/*  44:    */     
/*  45: 62 */     fromCabecera.fetch("documento", JoinType.INNER);
/*  46: 63 */     fromCabecera.fetch("bodegaTrabajo", JoinType.LEFT);
/*  47: 64 */     fromCabecera.fetch("planMaestroProduccion", JoinType.LEFT);
/*  48:    */     
/*  49: 66 */     Path<Integer> pathId = fromCabecera.get("idPlanProduccion");
/*  50: 67 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(id)));
/*  51: 68 */     CriteriaQuery<PlanProduccion> cqPlanProduccion = cqCabecera.select(fromCabecera);
/*  52:    */     
/*  53: 70 */     PlanProduccion planProduccion = (PlanProduccion)this.em.createQuery(cqPlanProduccion).getSingleResult();
/*  54:    */     
/*  55:    */ 
/*  56: 73 */     CriteriaQuery<DetallePlanProduccion> cqDetalle = criteriaBuilder.createQuery(DetallePlanProduccion.class);
/*  57: 74 */     Root<DetallePlanProduccion> fromDetalle = cqDetalle.from(DetallePlanProduccion.class);
/*  58:    */     
/*  59: 76 */     fromDetalle.fetch("planProduccion", JoinType.LEFT);
/*  60: 77 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  61: 78 */     fromDetalle.fetch("unidadStock", JoinType.LEFT);
/*  62: 79 */     fromDetalle.fetch("lote", JoinType.LEFT);
/*  63: 80 */     fromDetalle.fetch("rutaFabricacion", JoinType.LEFT);
/*  64: 81 */     Fetch<Object, Object> subCategoria = producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  65: 82 */     subCategoria.fetch("categoriaProducto", JoinType.LEFT);
/*  66:    */     
/*  67: 84 */     Path<Integer> pathIdPlanProduccion = fromDetalle.join("planProduccion").get("idPlanProduccion");
/*  68: 85 */     cqDetalle.where(criteriaBuilder.equal(pathIdPlanProduccion, Integer.valueOf(id)));
/*  69: 86 */     CriteriaQuery<DetallePlanProduccion> selectDetalle = cqDetalle.select(fromDetalle);
/*  70:    */     
/*  71: 88 */     List<DetallePlanProduccion> listaDetallePlanProduccion = this.em.createQuery(selectDetalle).getResultList();
/*  72: 89 */     planProduccion.setListaDetallePlanProduccion(listaDetallePlanProduccion);
/*  73:    */     
/*  74: 91 */     return planProduccion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<PlanProduccion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  78:    */   {
/*  79: 96 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  80: 97 */     CriteriaQuery<PlanProduccion> criteriaQuery = criteriaBuilder.createQuery(PlanProduccion.class);
/*  81: 98 */     Root<PlanProduccion> from = criteriaQuery.from(PlanProduccion.class);
/*  82:    */     
/*  83:100 */     from.fetch("bodegaTrabajo", JoinType.LEFT);
/*  84:101 */     from.fetch("documento", JoinType.INNER);
/*  85:    */     
/*  86:103 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  87:104 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  88:    */     
/*  89:106 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  90:    */     
/*  91:108 */     CriteriaQuery<PlanProduccion> select = criteriaQuery.select(from);
/*  92:    */     
/*  93:110 */     TypedQuery<PlanProduccion> typedQuery = this.em.createQuery(select);
/*  94:111 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  95:    */     
/*  96:113 */     return typedQuery.getResultList();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<Producto> obtenerProductosPlanificacion(int idOrganizacion, List<Bodega> listaBodegaTrabajo, int idSucursal)
/* 100:    */   {
/* 101:118 */     StringBuilder sql = new StringBuilder();
/* 102:    */     
/* 103:120 */     sql.append("SELECT p ");
/* 104:121 */     sql.append(" FROM Producto p ");
/* 105:122 */     sql.append(" LEFT JOIN FETCH p.unidad u ");
/* 106:123 */     sql.append(" LEFT JOIN FETCH p.unidadAlmacenamiento ua ");
/* 107:124 */     sql.append(" LEFT JOIN FETCH p.unidadVenta uv ");
/* 108:125 */     sql.append(" LEFT JOIN FETCH p.subcategoriaProducto scp ");
/* 109:126 */     sql.append(" LEFT JOIN FETCH scp.categoriaProducto cp ");
/* 110:127 */     sql.append(" LEFT JOIN FETCH p.listaBodegaTrabajoSucursal lbts");
/* 111:128 */     sql.append(" LEFT JOIN FETCH lbts.bodegaTrabajo bt");
/* 112:129 */     sql.append(" LEFT JOIN FETCH lbts.sucursal s");
/* 113:130 */     sql.append(" WHERE p.idOrganizacion = :idOrganizacion ");
/* 114:131 */     sql.append(" AND p.indicadorProduccion IS TRUE ");
/* 115:132 */     sql.append(" AND p.indicadorPlanificarProduccion IS TRUE ");
/* 116:133 */     sql.append(" AND p.activo IS TRUE ");
/* 117:134 */     if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0))
/* 118:    */     {
/* 119:135 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 120:136 */       sql.append(" AND bt IN (:listaBodegaTrabajo) ");
/* 121:    */     }
/* 122:138 */     Query query = this.em.createQuery(sql.toString());
/* 123:139 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 124:140 */     if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0))
/* 125:    */     {
/* 126:141 */       query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 127:142 */       query.setParameter("listaBodegaTrabajo", listaBodegaTrabajo);
/* 128:    */     }
/* 129:144 */     return query.getResultList();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<Object[]> obtenerProductosPlanificadosPorFecha(PlanProduccion planProduccion)
/* 133:    */   {
/* 134:149 */     String valor = " SUM(0";
/* 135:150 */     if (planProduccion.isIndicadorFecha1()) {
/* 136:151 */       valor = valor + " + (dpp.batchLunes * p.cantidadProduccion) ";
/* 137:    */     }
/* 138:153 */     if (planProduccion.isIndicadorFecha2()) {
/* 139:154 */       valor = valor + " + (dpp.batchMartes * p.cantidadProduccion) ";
/* 140:    */     }
/* 141:156 */     if (planProduccion.isIndicadorFecha3()) {
/* 142:157 */       valor = valor + " + (dpp.batchMiercoles * p.cantidadProduccion) ";
/* 143:    */     }
/* 144:159 */     if (planProduccion.isIndicadorFecha4()) {
/* 145:160 */       valor = valor + " + (dpp.batchJueves * p.cantidadProduccion) ";
/* 146:    */     }
/* 147:162 */     if (planProduccion.isIndicadorFecha5()) {
/* 148:163 */       valor = valor + " + (dpp.batchViernes * p.cantidadProduccion) ";
/* 149:    */     }
/* 150:165 */     if (planProduccion.isIndicadorFecha6()) {
/* 151:166 */       valor = valor + " + (dpp.batchSabado * p.cantidadProduccion) ";
/* 152:    */     }
/* 153:168 */     if (planProduccion.isIndicadorFecha7()) {
/* 154:169 */       valor = valor + " + (dpp.batchDomingo * p.cantidadProduccion) ";
/* 155:    */     }
/* 156:171 */     valor = valor + " )";
/* 157:    */     
/* 158:173 */     StringBuilder sql = new StringBuilder();
/* 159:174 */     sql.append("SELECT p.idProducto, " + valor);
/* 160:175 */     sql.append(" FROM DetallePlanProduccion dpp ");
/* 161:176 */     sql.append(" INNER JOIN dpp.planProduccion pp ");
/* 162:177 */     sql.append(" INNER JOIN dpp.producto p ");
/* 163:178 */     sql.append(" WHERE pp.idPlanProduccion = :idPlanProduccion ");
/* 164:179 */     sql.append(" GROUP BY p ");
/* 165:180 */     sql.append(" HAVING " + valor + " <> 0 ");
/* 166:    */     
/* 167:182 */     Query query = this.em.createQuery(sql.toString());
/* 168:183 */     query.setParameter("idPlanProduccion", Integer.valueOf(planProduccion.getId()));
/* 169:184 */     return query.getResultList();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public HashMap<Integer, BigDecimal> obtenerTotalPlanificadoPorProductoPorFecha(Producto producto, Date fecha, String diaSemana, boolean indicadorVentas)
/* 173:    */   {
/* 174:191 */     HashMap<Integer, BigDecimal> planificacionProducto = new HashMap();
/* 175:    */     
/* 176:193 */     StringBuilder sql = new StringBuilder();
/* 177:194 */     if (indicadorVentas) {
/* 178:195 */       sql.append(" SELECT p.idProducto,  COALESCE(SUM(dpp.venta" + diaSemana + "),0) ");
/* 179:    */     } else {
/* 180:197 */       sql.append(" SELECT p.idProducto,  COALESCE(SUM(dpp.batch" + diaSemana + " * p.cantidadProduccion),0) ");
/* 181:    */     }
/* 182:199 */     sql.append(" FROM DetallePlanProduccion dpp ");
/* 183:200 */     sql.append(" INNER JOIN dpp.planProduccion pp ");
/* 184:201 */     sql.append(" INNER JOIN dpp.producto p ");
/* 185:202 */     sql.append(" WHERE pp.estado != :estadoAnulado ");
/* 186:203 */     sql.append(" AND pp.fechaInicio <= :fecha ");
/* 187:204 */     sql.append(" AND pp.fechaFin >= :fecha ");
/* 188:206 */     if (producto != null) {
/* 189:207 */       sql.append(" AND p.idProducto = :idProducto ");
/* 190:    */     }
/* 191:210 */     sql.append(" GROUP BY p.idProducto");
/* 192:211 */     Query query = this.em.createQuery(sql.toString());
/* 193:212 */     if (producto != null) {
/* 194:213 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 195:    */     }
/* 196:215 */     query.setParameter("fecha", fecha);
/* 197:216 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 198:    */     
/* 199:218 */     List<Object[]> listaPlinificacion = query.getResultList();
/* 200:220 */     for (Object[] objects : listaPlinificacion) {
/* 201:221 */       planificacionProducto.put((Integer)objects[0], (BigDecimal)objects[1]);
/* 202:    */     }
/* 203:223 */     return planificacionProducto;
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.PlanProduccionDao
 * JD-Core Version:    0.7.0.1
 */