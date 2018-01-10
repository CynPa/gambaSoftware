/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.produccion.CostosDeFabricacion;
/*   7:    */ import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
/*   8:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ import javax.persistence.EntityManager;
/*  20:    */ import javax.persistence.NoResultException;
/*  21:    */ import javax.persistence.Query;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.persistence.TypedQuery;
/*  24:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  25:    */ import javax.persistence.criteria.CriteriaQuery;
/*  26:    */ import javax.persistence.criteria.Expression;
/*  27:    */ import javax.persistence.criteria.Fetch;
/*  28:    */ import javax.persistence.criteria.Join;
/*  29:    */ import javax.persistence.criteria.JoinType;
/*  30:    */ import javax.persistence.criteria.Path;
/*  31:    */ import javax.persistence.criteria.Predicate;
/*  32:    */ import javax.persistence.criteria.Root;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class CostosDeFabricacionDao
/*  36:    */   extends AbstractDaoAS2<CostosDeFabricacion>
/*  37:    */ {
/*  38:    */   public CostosDeFabricacionDao()
/*  39:    */   {
/*  40: 58 */     super(CostosDeFabricacion.class);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<CostosDeFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 64 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  46: 65 */     CriteriaQuery<CostosDeFabricacion> criteriaQuery = criteriaBuilder.createQuery(CostosDeFabricacion.class);
/*  47: 66 */     Root<CostosDeFabricacion> from = criteriaQuery.from(CostosDeFabricacion.class);
/*  48:    */     
/*  49: 68 */     from.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  50:    */     
/*  51: 70 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  52: 71 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  53:    */     
/*  54: 73 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  55:    */     
/*  56: 75 */     CriteriaQuery<CostosDeFabricacion> select = criteriaQuery.select(from);
/*  57:    */     
/*  58: 77 */     TypedQuery<CostosDeFabricacion> typedQuery = this.em.createQuery(select);
/*  59: 78 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  60:    */     
/*  61: 80 */     return typedQuery.getResultList();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public CostosDeFabricacion cargarDetalle(int idCostosDeFabricacion)
/*  65:    */   {
/*  66: 85 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  67:    */     
/*  68:    */ 
/*  69: 88 */     CriteriaQuery<CostosDeFabricacion> cqCabecera = criteriaBuilder.createQuery(CostosDeFabricacion.class);
/*  70: 89 */     Root<CostosDeFabricacion> fromCabecera = cqCabecera.from(CostosDeFabricacion.class);
/*  71:    */     
/*  72: 91 */     fromCabecera.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  73:    */     
/*  74: 93 */     Path<Integer> pathId = fromCabecera.get("idCostosDeFabricacion");
/*  75: 94 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idCostosDeFabricacion)));
/*  76: 95 */     CriteriaQuery<CostosDeFabricacion> selectCosto = cqCabecera.select(fromCabecera);
/*  77:    */     
/*  78: 97 */     CostosDeFabricacion costosDeFabricacion = (CostosDeFabricacion)this.em.createQuery(selectCosto).getSingleResult();
/*  79:    */     
/*  80:    */ 
/*  81:100 */     CriteriaQuery<DetalleCostoFabricacion> cqDetalle = criteriaBuilder.createQuery(DetalleCostoFabricacion.class);
/*  82:101 */     Root<DetalleCostoFabricacion> fromDetalle = cqDetalle.from(DetalleCostoFabricacion.class);
/*  83:    */     
/*  84:103 */     Fetch<Object, Object> ordenFabricacion = fromDetalle.fetch("ordenFabricacion", JoinType.INNER);
/*  85:104 */     ordenFabricacion.fetch("rutaFabricacion", JoinType.INNER);
/*  86:105 */     Fetch<Object, Object> producto = ordenFabricacion.fetch("producto", JoinType.INNER);
/*  87:106 */     producto.fetch("unidad", JoinType.INNER);
/*  88:    */     
/*  89:108 */     Path<Integer> pathIdDetalle = fromDetalle.join("costosDeFabricacion").get("idCostosDeFabricacion");
/*  90:109 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idCostosDeFabricacion)));
/*  91:110 */     CriteriaQuery<DetalleCostoFabricacion> selectDetalle = cqDetalle.select(fromDetalle);
/*  92:    */     
/*  93:112 */     List<DetalleCostoFabricacion> listaDetalleCostoFabricacion = this.em.createQuery(selectDetalle).getResultList();
/*  94:113 */     costosDeFabricacion.setListaDetalleCostoFabricacion(listaDetalleCostoFabricacion);
/*  95:    */     
/*  96:115 */     return costosDeFabricacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public BigDecimal getCostosMateriales(Integer idOrganizacion, Date fechaDesde, Date fechaHasta, OrdenFabricacion ordenFabricacion)
/* 100:    */   {
/* 101:127 */     if (idOrganizacion == null) {
/* 102:128 */       idOrganizacion = Integer.valueOf(ordenFabricacion.getIdOrganizacion());
/* 103:    */     }
/* 104:131 */     StringBuilder sql = new StringBuilder();
/* 105:132 */     sql.append(" SELECT SUM(ip.costo) FROM InventarioProducto ip");
/* 106:133 */     sql.append(" JOIN ip.detalleMovimientoInventario dmi");
/* 107:134 */     sql.append(" JOIN dmi.detalleOrdenSalidaMaterial dosm");
/* 108:135 */     sql.append(" JOIN dmi.movimientoInventario mi");
/* 109:136 */     sql.append(" JOIN dosm.ordenFabricacion ofa");
/* 110:137 */     sql.append(" WHERE ofa.idOrganizacion = :idOrganizacion");
/* 111:139 */     if (ordenFabricacion == null) {
/* 112:140 */       sql.append(" AND ofa.fechaLanzamiento BETWEEN :fechaDesde AND :fechaHasta");
/* 113:    */     } else {
/* 114:142 */       sql.append(" AND ofa = :ordenFabricacion");
/* 115:    */     }
/* 116:145 */     sql.append(" AND ofa.estado != :estadoAnulado");
/* 117:146 */     sql.append(" AND mi.estado != :estadoMovimiento");
/* 118:147 */     Query query = this.em.createQuery(sql.toString());
/* 119:148 */     query.setParameter("idOrganizacion", idOrganizacion);
/* 120:149 */     if (ordenFabricacion == null)
/* 121:    */     {
/* 122:150 */       query.setParameter("fechaDesde", fechaDesde);
/* 123:151 */       query.setParameter("fechaHasta", fechaHasta);
/* 124:    */     }
/* 125:    */     else
/* 126:    */     {
/* 127:153 */       query.setParameter("ordenFabricacion", ordenFabricacion);
/* 128:    */     }
/* 129:155 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/* 130:156 */     query.setParameter("estadoMovimiento", Estado.ANULADO);
/* 131:    */     
/* 132:158 */     BigDecimal resultado = (BigDecimal)query.getSingleResult();
/* 133:159 */     return resultado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List<DetalleMovimientoInventario> getFabricacion(OrdenFabricacion ordenFabricacion)
/* 137:    */   {
/* 138:165 */     StringBuilder sql = new StringBuilder();
/* 139:166 */     sql.append(" SELECT dmi FROM DetalleMovimientoInventario dmi");
/* 140:167 */     sql.append(" JOIN FETCH dmi.producto pr");
/* 141:168 */     sql.append(" JOIN FETCH dmi.inventarioProducto ip");
/* 142:169 */     sql.append(" JOIN dmi.movimientoInventario mi");
/* 143:170 */     sql.append(" WHERE mi.ordenFabricacion = :ordenFabricacion");
/* 144:171 */     sql.append(" AND dmi.cantidad > 0");
/* 145:172 */     sql.append(" AND mi.estado <> :estadoAnulado");
/* 146:    */     
/* 147:174 */     Query query = this.em.createQuery(sql.toString());
/* 148:175 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 149:176 */     query.setParameter("ordenFabricacion", ordenFabricacion);
/* 150:    */     
/* 151:178 */     List<DetalleMovimientoInventario> datos = query.getResultList();
/* 152:    */     
/* 153:180 */     return datos;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public DetalleCostoFabricacion buscarDetalleCostoFabricacionMesAnterior(CostosDeFabricacion costosDeFabricacion, OrdenFabricacion ordenFabricacion)
/* 157:    */     throws AS2Exception
/* 158:    */   {
/* 159:185 */     StringBuilder sql = new StringBuilder();
/* 160:186 */     sql.append(" SELECT dcf ");
/* 161:187 */     sql.append(" FROM DetalleCostoFabricacion dcf ");
/* 162:188 */     sql.append(" INNER JOIN dcf.ordenFabricacion ofa ");
/* 163:189 */     sql.append(" INNER JOIN dcf.costosDeFabricacion cf ");
/* 164:190 */     sql.append(" WHERE cf.idOrganizacion = :idOrganizacion ");
/* 165:191 */     sql.append(" AND ofa.idOrdenFabricacion = :idOrdenFabricacion ");
/* 166:192 */     sql.append(" AND cf.anio = :anioAnterior ");
/* 167:193 */     sql.append(" AND cf.mes = :mesAnterior ");
/* 168:    */     
/* 169:195 */     Query query = this.em.createQuery(sql.toString());
/* 170:196 */     query.setParameter("idOrganizacion", Integer.valueOf(costosDeFabricacion.getIdOrganizacion()));
/* 171:197 */     query.setParameter("idOrdenFabricacion", Integer.valueOf(ordenFabricacion.getId()));
/* 172:    */     
/* 173:199 */     int anioAnterior = costosDeFabricacion.getAnio().intValue();
/* 174:200 */     int mesAnterior = costosDeFabricacion.getMes().intValue();
/* 175:201 */     if (mesAnterior == 1)
/* 176:    */     {
/* 177:202 */       mesAnterior = 12;
/* 178:203 */       anioAnterior--;
/* 179:    */     }
/* 180:    */     else
/* 181:    */     {
/* 182:205 */       mesAnterior -= 1;
/* 183:    */     }
/* 184:207 */     query.setParameter("anioAnterior", Integer.valueOf(anioAnterior));
/* 185:208 */     query.setParameter("mesAnterior", Integer.valueOf(mesAnterior));
/* 186:    */     
/* 187:210 */     DetalleCostoFabricacion detalle = null;
/* 188:    */     try
/* 189:    */     {
/* 190:212 */       detalle = (DetalleCostoFabricacion)query.getSingleResult();
/* 191:    */     }
/* 192:    */     catch (NoResultException e)
/* 193:    */     {
/* 194:215 */       throw new AS2Exception("msg_error_no_existe_detalle_mes_anterior_ciclo_largo", new String[] { ordenFabricacion.getNumero(), FuncionesUtiles.nombreMes(mesAnterior) + " " + anioAnterior });
/* 195:    */     }
/* 196:217 */     if (detalle == null) {
/* 197:219 */       throw new AS2Exception("msg_error_no_existe_detalle_mes_anterior_ciclo_largo", new String[] { ordenFabricacion.getNumero(), FuncionesUtiles.nombreMes(mesAnterior) + " " + anioAnterior });
/* 198:    */     }
/* 199:222 */     return detalle;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Object[]> getReporteCostosFabricacion(int idOrganizacion, Producto producto, int anioDesde, int mesDesde, int anioHasta, int mesHasta)
/* 203:    */   {
/* 204:228 */     mesDesde--;
/* 205:229 */     Calendar calendar = Calendar.getInstance();
/* 206:230 */     calendar.set(anioDesde, mesDesde, 1);
/* 207:231 */     Date fechaDesde = calendar.getTime();
/* 208:232 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(anioHasta, mesHasta);
/* 209:    */     
/* 210:234 */     StringBuilder sql = new StringBuilder();
/* 211:235 */     sql.append(" SELECT p.codigo, p.nombre, cf.anio, cf.mes, rf.codigo, rf.nombre, ");
/* 212:236 */     sql.append(" ofa.numero, ofa.fecha, ofa.fechaLanzamiento, ofa.fechaCierre, ofa.cantidad, ofa.cantidadFabricada, dcf.cantidadFabricadaMes, ");
/* 213:    */     
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:241 */     sql.append(" dcf.costoMaterialesInicial, dcf.costoManoObraInicial, dcf.costoIndirectosInicial, dcf.costoDepreciacionInicial, ");
/* 218:242 */     sql.append(" dcf.costoMaterialesMes, dcf.costoManoObraMes, dcf.costoIndirectosMes, dcf.costoDepreciacionMes, ");
/* 219:243 */     sql.append(" dcf.costoMaterialesAsignadoMes, dcf.costoManoObraAsignadoMes, dcf.costoIndirectosAsignadoMes, dcf.costoDepreciacionAsignadoMes, ");
/* 220:244 */     sql.append(" dcf.costoMaterialesPendiente, dcf.costoManoObraPendiente, dcf.costoIndirectosPendiente, dcf.costoDepreciacionPendiente ");
/* 221:245 */     sql.append(" FROM DetalleCostoFabricacion dcf ");
/* 222:246 */     sql.append(" INNER JOIN dcf.costosDeFabricacion cf ");
/* 223:247 */     sql.append(" INNER JOIN dcf.ordenFabricacion ofa ");
/* 224:248 */     sql.append(" INNER JOIN ofa.producto p ");
/* 225:249 */     sql.append(" INNER JOIN ofa.rutaFabricacion rf ");
/* 226:250 */     sql.append(" WHERE cf.idOrganizacion = :idOrganizacion ");
/* 227:251 */     sql.append(" AND cf.fecha BETWEEN :fechaDesde and :fechaHasta ");
/* 228:252 */     if (producto != null) {
/* 229:253 */       sql.append(" AND p.idProducto = :idProducto ");
/* 230:    */     }
/* 231:255 */     sql.append(" ORDER BY p.codigo, ofa.numero, cf.anio, cf.mes ");
/* 232:    */     
/* 233:257 */     Query query = this.em.createQuery(sql.toString());
/* 234:258 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 235:259 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 236:260 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 237:261 */     if (producto != null) {
/* 238:262 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 239:    */     }
/* 240:265 */     return query.getResultList();
/* 241:    */   }
/* 242:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.CostosDeFabricacionDao
 * JD-Core Version:    0.7.0.1
 */