/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   4:    */ import com.asinfo.as2.entities.Contacto;
/*   5:    */ import com.asinfo.as2.entities.DetalleSolicitudCompra;
/*   6:    */ import com.asinfo.as2.entities.DetalleSolicitudCompraProveedor;
/*   7:    */ import com.asinfo.as2.entities.Empleado;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.SolicitudCompra;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ import javax.persistence.EntityManager;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TypedQuery;
/*  19:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  20:    */ import javax.persistence.criteria.CriteriaQuery;
/*  21:    */ import javax.persistence.criteria.Expression;
/*  22:    */ import javax.persistence.criteria.Fetch;
/*  23:    */ import javax.persistence.criteria.Join;
/*  24:    */ import javax.persistence.criteria.JoinType;
/*  25:    */ import javax.persistence.criteria.Path;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ 
/*  29:    */ @Stateless
/*  30:    */ public class SolicitudCompraDao
/*  31:    */   extends AbstractDaoAS2<SolicitudCompra>
/*  32:    */ {
/*  33:    */   public SolicitudCompraDao()
/*  34:    */   {
/*  35: 32 */     super(SolicitudCompra.class);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public SolicitudCompra cargarDetalle(int idSolicitudCompra)
/*  39:    */   {
/*  40: 37 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  41:    */     
/*  42: 39 */     CriteriaQuery<SolicitudCompra> cqCabecera = criteriaBuilder.createQuery(SolicitudCompra.class);
/*  43: 40 */     Root<SolicitudCompra> fromCabecera = cqCabecera.from(SolicitudCompra.class);
/*  44:    */     
/*  45: 42 */     fromCabecera.fetch("documento", JoinType.LEFT).fetch("secuencia", JoinType.LEFT);
/*  46: 43 */     fromCabecera.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  47: 44 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  48:    */     
/*  49: 46 */     Path<Integer> pathId = fromCabecera.get("idSolicitudCompra");
/*  50: 47 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idSolicitudCompra)));
/*  51: 48 */     CriteriaQuery<SolicitudCompra> selectSolicitudCompra = cqCabecera.select(fromCabecera);
/*  52:    */     
/*  53: 50 */     SolicitudCompra solicitudCompra = (SolicitudCompra)this.em.createQuery(selectSolicitudCompra).getSingleResult();
/*  54:    */     
/*  55:    */ 
/*  56: 53 */     CriteriaQuery<DetalleSolicitudCompra> cqDetalle = criteriaBuilder.createQuery(DetalleSolicitudCompra.class);
/*  57: 54 */     Root<DetalleSolicitudCompra> fromDetalle = cqDetalle.from(DetalleSolicitudCompra.class);
/*  58: 55 */     fromDetalle.fetch("unidadCompra", JoinType.LEFT);
/*  59: 56 */     fromDetalle.fetch("producto", JoinType.LEFT);
/*  60: 57 */     fromDetalle.fetch("solicitudCompra", JoinType.LEFT);
/*  61: 58 */     fromDetalle.fetch("detalleSolicitudCompraPadre", JoinType.LEFT).fetch("solicitudCompra", JoinType.LEFT);
/*  62: 59 */     fromDetalle.fetch("empleado", JoinType.LEFT);
/*  63:    */     
/*  64: 61 */     Path<Integer> pathIdDetalle = fromDetalle.join("solicitudCompra").get("idSolicitudCompra");
/*  65: 62 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idSolicitudCompra)));
/*  66: 63 */     CriteriaQuery<DetalleSolicitudCompra> selectDetalleSolicitudCompra = cqDetalle.select(fromDetalle);
/*  67:    */     
/*  68: 65 */     List<DetalleSolicitudCompra> listaDetalleSolicitudCompra = this.em.createQuery(selectDetalleSolicitudCompra).getResultList();
/*  69: 67 */     for (DetalleSolicitudCompra dsc : listaDetalleSolicitudCompra)
/*  70:    */     {
/*  71: 69 */       CriteriaQuery<DetalleSolicitudCompra> cqDetalle2 = criteriaBuilder.createQuery(DetalleSolicitudCompra.class);
/*  72: 70 */       Root<DetalleSolicitudCompra> fromDetalle2 = cqDetalle2.from(DetalleSolicitudCompra.class);
/*  73: 71 */       fromDetalle2.fetch("unidadCompra", JoinType.LEFT);
/*  74: 72 */       fromDetalle2.fetch("producto", JoinType.LEFT);
/*  75: 73 */       fromDetalle2.fetch("solicitudCompra", JoinType.LEFT);
/*  76: 74 */       fromDetalle2.fetch("detalleSolicitudCompraPadre", JoinType.LEFT);
/*  77: 75 */       fromDetalle2.fetch("empleado", JoinType.LEFT);
/*  78: 76 */       Path<Integer> pathIdDetalle2 = fromDetalle2.join("detalleSolicitudCompraPadre").get("idDetalleSolicitudCompra");
/*  79: 77 */       cqDetalle2.where(criteriaBuilder.equal(pathIdDetalle2, Integer.valueOf(dsc.getIdDetalleSolicitudCompra())));
/*  80: 78 */       CriteriaQuery<DetalleSolicitudCompra> selectDetalleSolicitudCompra2 = cqDetalle2.select(fromDetalle2);
/*  81: 79 */       List<DetalleSolicitudCompra> listaDetalleSolicitudCompra2 = this.em.createQuery(selectDetalleSolicitudCompra2).getResultList();
/*  82: 80 */       dsc.setListaDetalleSolicitudCompra(listaDetalleSolicitudCompra2);
/*  83:    */     }
/*  84: 83 */     solicitudCompra.setListaDetalleSolicitudCompra(listaDetalleSolicitudCompra);
/*  85:    */     
/*  86:    */ 
/*  87: 86 */     Object cqDetalleProveedor = criteriaBuilder.createQuery(DetalleSolicitudCompraProveedor.class);
/*  88: 87 */     Root<DetalleSolicitudCompraProveedor> fromDetalleProveedor = ((CriteriaQuery)cqDetalleProveedor).from(DetalleSolicitudCompraProveedor.class);
/*  89: 88 */     fromDetalleProveedor.fetch("producto", JoinType.LEFT);
/*  90: 89 */     fromDetalleProveedor.fetch("solicitudCompra", JoinType.LEFT);
/*  91: 90 */     fromDetalleProveedor.fetch("empresa", JoinType.LEFT);
/*  92:    */     
/*  93: 92 */     Path<Integer> pathIdDetalleProveedor = fromDetalleProveedor.join("solicitudCompra").get("idSolicitudCompra");
/*  94: 93 */     ((CriteriaQuery)cqDetalleProveedor).where(criteriaBuilder.equal(pathIdDetalleProveedor, Integer.valueOf(idSolicitudCompra)));
/*  95: 94 */     CriteriaQuery<DetalleSolicitudCompraProveedor> selectDetalleSolicitudCompraProveedor = ((CriteriaQuery)cqDetalleProveedor).select(fromDetalleProveedor);
/*  96:    */     
/*  97:    */ 
/*  98: 97 */     List<DetalleSolicitudCompraProveedor> listaDetalleSolicitudCompraProveedor = this.em.createQuery(selectDetalleSolicitudCompraProveedor).getResultList();
/*  99: 98 */     solicitudCompra.setListaDetalleSolicitudCompraProveedor(listaDetalleSolicitudCompraProveedor);
/* 100:    */     
/* 101:100 */     return solicitudCompra;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<SolicitudCompra> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 105:    */   {
/* 106:107 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 107:108 */     CriteriaQuery<SolicitudCompra> criteriaQuery = criteriaBuilder.createQuery(SolicitudCompra.class);
/* 108:109 */     Root<SolicitudCompra> from = criteriaQuery.from(SolicitudCompra.class);
/* 109:110 */     from.fetch("documento", JoinType.LEFT).fetch("secuencia", JoinType.LEFT);
/* 110:111 */     from.fetch("empleado", JoinType.LEFT);
/* 111:112 */     from.fetch("sucursal", JoinType.LEFT);
/* 112:    */     
/* 113:114 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 114:115 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 115:    */     
/* 116:117 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 117:    */     
/* 118:119 */     CriteriaQuery<SolicitudCompra> select = criteriaQuery.select(from);
/* 119:    */     
/* 120:121 */     TypedQuery<SolicitudCompra> typedQuery = this.em.createQuery(select);
/* 121:122 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 122:    */     
/* 123:124 */     return typedQuery.getResultList();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<Object[]> getReporteSolicitudCompra(int idSolicitudCompra)
/* 127:    */   {
/* 128:130 */     StringBuilder sql = new StringBuilder();
/* 129:131 */     sql.append("SELECT sc.fecha, sc.numero, sc.descripcion, CONCAT(e.apellidos, e.nombres), sc.estado, s.nombre,");
/* 130:132 */     sql.append(" pr.codigo, pr.nombre, u.nombre, dsc.cantidad, dsc.cantidadOriginal, dsc.descripcion, sp.numero");
/* 131:133 */     sql.append(" FROM DetalleSolicitudCompra dsc");
/* 132:134 */     sql.append(" INNER JOIN dsc.producto pr");
/* 133:135 */     sql.append(" INNER JOIN dsc.unidadCompra u");
/* 134:136 */     sql.append(" INNER JOIN dsc.solicitudCompra sc");
/* 135:137 */     sql.append(" INNER JOIN sc.sucursal s");
/* 136:138 */     sql.append(" LEFT JOIN sc.empleado e");
/* 137:139 */     sql.append(" LEFT JOIN dsc.detalleSolicitudCompraPadre dscp");
/* 138:140 */     sql.append(" LEFT JOIN dscp.solicitudCompra sp");
/* 139:141 */     sql.append(" WHERE sc.idSolicitudCompra = :idSolicitudCompra");
/* 140:    */     
/* 141:143 */     Query query = this.em.createQuery(sql.toString());
/* 142:144 */     query.setParameter("idSolicitudCompra", Integer.valueOf(idSolicitudCompra));
/* 143:    */     
/* 144:146 */     return query.getResultList();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List<Object[]> getReporteProductosConsolidados(int idSolicitudCompra)
/* 148:    */   {
/* 149:152 */     StringBuilder sql = new StringBuilder();
/* 150:    */     
/* 151:154 */     sql.append("SELECT e.nombreComercial, pr.codigo, pr.nombre, u.nombre,");
/* 152:155 */     sql.append(" dsc.cantidad, dsc.precio, dsc.total, dsc.indicadorEnPedido");
/* 153:156 */     sql.append(" FROM DetalleSolicitudCompraProveedor dsc");
/* 154:157 */     sql.append(" INNER JOIN dsc.producto pr");
/* 155:158 */     sql.append(" INNER JOIN dsc.unidadCompra u");
/* 156:159 */     sql.append(" INNER JOIN dsc.solicitudCompra sc");
/* 157:160 */     sql.append(" INNER JOIN dsc.empresa e");
/* 158:    */     
/* 159:162 */     sql.append(" WHERE sc.idSolicitudCompra = :idSolicitudCompra");
/* 160:    */     
/* 161:164 */     sql.append(" ORDER BY e.nombreComercial, dsc.indicadorEnPedido");
/* 162:    */     
/* 163:166 */     Query query = this.em.createQuery(sql.toString());
/* 164:    */     
/* 165:168 */     query.setParameter("idSolicitudCompra", Integer.valueOf(idSolicitudCompra));
/* 166:    */     
/* 167:170 */     return query.getResultList();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<DetalleSolicitudCompra> getConsolidarSolicitudCompra(int idOrganizacion, List<CategoriaProducto> listCategoriaProducto, List<Producto> listaProducto, List<Empleado> listaEmpleado, Date fechaDesde, Date fechaHasta, List<SolicitudCompra> listaSolicitudCompra)
/* 171:    */   {
/* 172:178 */     StringBuilder sql = new StringBuilder();
/* 173:179 */     sql.append("SELECT dsc ");
/* 174:180 */     sql.append(" FROM DetalleSolicitudCompra dsc ");
/* 175:181 */     sql.append(" INNER JOIN dsc.solicitudCompra sc ");
/* 176:182 */     sql.append(" INNER JOIN FETCH dsc.producto pr ");
/* 177:183 */     if (listCategoriaProducto.size() > 0)
/* 178:    */     {
/* 179:184 */       sql.append(" INNER JOIN pr.subcategoriaProducto scpr");
/* 180:185 */       sql.append(" INNER JOIN scpr.categoriaProducto cpr");
/* 181:    */     }
/* 182:187 */     sql.append(" INNER JOIN FETCH dsc.empleado em ");
/* 183:188 */     sql.append(" INNER JOIN sc.documento d ");
/* 184:189 */     sql.append(" WHERE sc.idOrganizacion = :idOrganizacion ");
/* 185:190 */     sql.append(" AND dsc.indicadorEnPedido = false ");
/* 186:191 */     sql.append(" AND dsc.detalleSolicitudCompraPadre IS NULL ");
/* 187:192 */     sql.append(" AND (sc.estado != :estadoCerrado AND sc.estado != :estadoElaborado )");
/* 188:193 */     sql.append(" AND sc.indicadorConsolidado = false");
/* 189:194 */     if (listaEmpleado.size() > 0) {
/* 190:195 */       sql.append(" AND em in (:listaEmpleado) ");
/* 191:    */     }
/* 192:197 */     if (listCategoriaProducto.size() > 0) {
/* 193:198 */       sql.append(" AND cpr in (:listCategoriaProducto)  ");
/* 194:    */     }
/* 195:200 */     if (listaProducto.size() > 0) {
/* 196:201 */       sql.append(" AND pr in (:listaProducto)  ");
/* 197:    */     }
/* 198:203 */     if (fechaDesde != null) {
/* 199:204 */       sql.append(" AND sc.fecha >= :fechaDesde");
/* 200:    */     }
/* 201:206 */     if (fechaHasta != null) {
/* 202:207 */       sql.append(" AND sc.fecha <= :fechaHasta");
/* 203:    */     }
/* 204:209 */     if (listaSolicitudCompra.size() > 0) {
/* 205:210 */       sql.append(" AND sc in(:listaSolicitudCompra)");
/* 206:    */     }
/* 207:212 */     sql.append(" ORDER BY pr.codigo ");
/* 208:    */     
/* 209:214 */     Query query = this.em.createQuery(sql.toString());
/* 210:215 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 211:216 */     query.setParameter("estadoCerrado", EstadoSolicitudCompraEnum.CERRADO);
/* 212:217 */     query.setParameter("estadoElaborado", EstadoSolicitudCompraEnum.ELABORADO);
/* 213:218 */     if (listaEmpleado.size() > 0) {
/* 214:219 */       query.setParameter("listaEmpleado", listaEmpleado);
/* 215:    */     }
/* 216:221 */     if (listaProducto.size() > 0) {
/* 217:222 */       query.setParameter("listaProducto", listaProducto);
/* 218:    */     }
/* 219:224 */     if (listCategoriaProducto.size() > 0) {
/* 220:225 */       query.setParameter("listCategoriaProducto", listCategoriaProducto);
/* 221:    */     }
/* 222:227 */     if (listaSolicitudCompra.size() > 0) {
/* 223:228 */       query.setParameter("listaSolicitudCompra", listaSolicitudCompra);
/* 224:    */     }
/* 225:230 */     if (fechaDesde != null) {
/* 226:231 */       query.setParameter("fechaDesde", fechaDesde);
/* 227:    */     }
/* 228:233 */     if (fechaHasta != null) {
/* 229:234 */       query.setParameter("fechaHasta", fechaHasta);
/* 230:    */     }
/* 231:237 */     return query.getResultList();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<Contacto> getContactosSolicitudCompra(Sucursal sucursal)
/* 235:    */   {
/* 236:243 */     StringBuilder sql = new StringBuilder();
/* 237:244 */     sql.append("SELECT con");
/* 238:245 */     sql.append(" FROM Contacto con ");
/* 239:246 */     sql.append(" INNER JOIN con.sucursal s");
/* 240:247 */     sql.append(" INNER JOIN FETCH con.tipoContacto tc ");
/* 241:248 */     sql.append(" WHERE s = :sucursal");
/* 242:249 */     sql.append(" AND tc.indicadorNotificarSolicitudCompra = true");
/* 243:    */     
/* 244:251 */     Query query = this.em.createQuery(sql.toString()).setParameter("sucursal", sucursal);
/* 245:252 */     return query.getResultList();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void cerrarConsolidacion(int idSolicitudCompra)
/* 249:    */   {
/* 250:256 */     StringBuilder sql = new StringBuilder();
/* 251:257 */     sql.append("UPDATE SolicitudCompra sc");
/* 252:258 */     sql.append(" SET sc.estado = :estadoCerrado");
/* 253:259 */     sql.append(" WHERE sc.idSolicitudCompra IN ");
/* 254:260 */     sql.append("\t(SELECT DISTINCT s.idSolicitudCompra from DetalleSolicitudCompra dsc");
/* 255:261 */     sql.append("\tINNER JOIN dsc.solicitudCompra sc2");
/* 256:262 */     sql.append("\tINNER JOIN dsc.listaDetalleSolicitudCompra ldsc");
/* 257:263 */     sql.append("\tINNER JOIN ldsc.solicitudCompra s");
/* 258:264 */     sql.append("\tWHERE sc2.idSolicitudCompra = :idSolicitudCompra)");
/* 259:    */     
/* 260:266 */     Query query = this.em.createQuery(sql.toString());
/* 261:267 */     query.setParameter("estadoCerrado", EstadoSolicitudCompraEnum.CERRADO);
/* 262:268 */     query.setParameter("idSolicitudCompra", Integer.valueOf(idSolicitudCompra));
/* 263:269 */     query.executeUpdate();
/* 264:    */     
/* 265:271 */     StringBuilder cerrrarCosolidacion = new StringBuilder();
/* 266:272 */     cerrrarCosolidacion.append("UPDATE SolicitudCompra sc");
/* 267:273 */     cerrrarCosolidacion.append(" SET sc.estado = :estadoCerrado");
/* 268:274 */     cerrrarCosolidacion.append(" WHERE sc.idSolicitudCompra = :idConsolidacion");
/* 269:    */     
/* 270:276 */     Query updateConsolidacion = this.em.createQuery(cerrrarCosolidacion.toString());
/* 271:277 */     updateConsolidacion.setParameter("estadoCerrado", EstadoSolicitudCompraEnum.CERRADO);
/* 272:278 */     updateConsolidacion.setParameter("idConsolidacion", Integer.valueOf(idSolicitudCompra));
/* 273:279 */     updateConsolidacion.executeUpdate();
/* 274:    */   }
/* 275:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.SolicitudCompraDao
 * JD-Core Version:    0.7.0.1
 */