/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Contacto;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   7:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  11:    */ import com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor;
/*  12:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*  17:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*  21:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  22:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  23:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  24:    */ import java.io.PrintStream;
/*  25:    */ import java.math.BigDecimal;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.persistence.EntityManager;
/*  32:    */ import javax.persistence.NoResultException;
/*  33:    */ import javax.persistence.Query;
/*  34:    */ import javax.persistence.TemporalType;
/*  35:    */ import javax.persistence.TypedQuery;
/*  36:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  37:    */ import javax.persistence.criteria.CriteriaQuery;
/*  38:    */ import javax.persistence.criteria.Expression;
/*  39:    */ import javax.persistence.criteria.Fetch;
/*  40:    */ import javax.persistence.criteria.Join;
/*  41:    */ import javax.persistence.criteria.JoinType;
/*  42:    */ import javax.persistence.criteria.Path;
/*  43:    */ import javax.persistence.criteria.Predicate;
/*  44:    */ import javax.persistence.criteria.Root;
/*  45:    */ 
/*  46:    */ @Stateless
/*  47:    */ public class PedidoProveedorDao
/*  48:    */   extends AbstractDaoAS2<PedidoProveedor>
/*  49:    */ {
/*  50:    */   public PedidoProveedorDao()
/*  51:    */   {
/*  52: 51 */     super(PedidoProveedor.class);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public PedidoProveedor cargarDetalle(int idPedidoProveedor)
/*  56:    */   {
/*  57: 56 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  58:    */     
/*  59:    */ 
/*  60: 59 */     CriteriaQuery<PedidoProveedor> cqCabecera = criteriaBuilder.createQuery(PedidoProveedor.class);
/*  61: 60 */     Root<PedidoProveedor> fromCabecera = cqCabecera.from(PedidoProveedor.class);
/*  62:    */     
/*  63: 62 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  64: 63 */     documento.fetch("secuencia", JoinType.LEFT);
/*  65: 64 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/*  66: 65 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  67:    */     
/*  68: 67 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/*  69: 68 */     Fetch<Object, Object> proveedor = empresa.fetch("proveedor", JoinType.LEFT);
/*  70: 69 */     proveedor.fetch("listaPrecios", JoinType.LEFT);
/*  71: 70 */     fromCabecera.fetch("bodega", JoinType.LEFT);
/*  72: 71 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*  73: 72 */     fromCabecera.fetch("personaResponsable", JoinType.LEFT);
/*  74:    */     
/*  75: 74 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/*  76: 75 */     direccion.fetch("ubicacion", JoinType.LEFT);
/*  77:    */     
/*  78: 77 */     Path<Integer> pathId = fromCabecera.get("idPedidoProveedor");
/*  79: 78 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPedidoProveedor)));
/*  80: 79 */     CriteriaQuery<PedidoProveedor> selectPedido = cqCabecera.select(fromCabecera);
/*  81:    */     
/*  82: 81 */     PedidoProveedor pedidoProveedor = (PedidoProveedor)this.em.createQuery(selectPedido).getSingleResult();
/*  83:    */     
/*  84:    */ 
/*  85: 84 */     CriteriaQuery<DetallePedidoProveedor> cqDetalle = criteriaBuilder.createQuery(DetallePedidoProveedor.class);
/*  86: 85 */     Root<DetallePedidoProveedor> fromDetalle = cqDetalle.from(DetallePedidoProveedor.class);
/*  87: 86 */     fromDetalle.fetch("unidadCompra", JoinType.LEFT);
/*  88: 87 */     fromDetalle.fetch("listaDetalleRecepcionProveedor", JoinType.LEFT);
/*  89:    */     
/*  90: 89 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  91: 90 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/*  92: 91 */     producto.fetch("subcategoriaProducto", JoinType.LEFT).fetch("categoriaProducto", JoinType.LEFT);
/*  93:    */     
/*  94: 93 */     fromDetalle.fetch("dimensionContable1", JoinType.LEFT);
/*  95: 94 */     fromDetalle.fetch("dimensionContable2", JoinType.LEFT);
/*  96: 95 */     fromDetalle.fetch("dimensionContable3", JoinType.LEFT);
/*  97: 96 */     fromDetalle.fetch("dimensionContable4", JoinType.LEFT);
/*  98: 97 */     fromDetalle.fetch("dimensionContable5", JoinType.LEFT);
/*  99:    */     
/* 100: 99 */     Path<Integer> pathIdDetalle = fromDetalle.join("pedidoProveedor").get("idPedidoProveedor");
/* 101:100 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idPedidoProveedor)));
/* 102:101 */     CriteriaQuery<DetallePedidoProveedor> selectDetalleDespacho = cqDetalle.select(fromDetalle);
/* 103:    */     
/* 104:103 */     List<DetallePedidoProveedor> listaDetallePedidoProveedor = this.em.createQuery(selectDetalleDespacho).getResultList();
/* 105:104 */     pedidoProveedor.setListaDetallePedidoProveedor(listaDetallePedidoProveedor);
/* 106:107 */     for (DetallePedidoProveedor detallePedidoProveedor : listaDetallePedidoProveedor)
/* 107:    */     {
/* 108:108 */       Integer idDetallePedidoProveedor = Integer.valueOf(detallePedidoProveedor.getId());
/* 109:    */       
/* 110:110 */       CriteriaQuery<ImpuestoProductoPedidoProveedor> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoPedidoProveedor.class);
/* 111:111 */       Root<ImpuestoProductoPedidoProveedor> fromImpuesto = cqImpuesto.from(ImpuestoProductoPedidoProveedor.class);
/* 112:    */       
/* 113:113 */       fromImpuesto.fetch("impuesto", JoinType.LEFT);
/* 114:    */       
/* 115:115 */       Path<Integer> pathIdImpuesto = fromImpuesto.join("detallePedidoProveedor").get("idDetallePedidoProveedor");
/* 116:116 */       cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, idDetallePedidoProveedor));
/* 117:    */       
/* 118:118 */       CriteriaQuery<ImpuestoProductoPedidoProveedor> selectImpuesto = cqImpuesto.select(fromImpuesto);
/* 119:    */       
/* 120:120 */       List<ImpuestoProductoPedidoProveedor> listaImpuestoProductoPedidoProveedor = this.em.createQuery(selectImpuesto).getResultList();
/* 121:    */       
/* 122:122 */       detallePedidoProveedor.setListaImpuestoProductoPedidoProveedor(listaImpuestoProductoPedidoProveedor);
/* 123:    */     }
/* 124:125 */     return pedidoProveedor;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<DetallePedidoProveedor> getListaDetallePedidoPorProcesar(int idPedidoProveedor, DocumentoBase documentoBase)
/* 128:    */   {
/* 129:140 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 130:141 */     CriteriaQuery<DetallePedidoProveedor> criteriaQuery = criteriaBuilder.createQuery(DetallePedidoProveedor.class);
/* 131:142 */     Root<DetallePedidoProveedor> from = criteriaQuery.from(DetallePedidoProveedor.class);
/* 132:    */     
/* 133:144 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/* 134:145 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 135:146 */     from.fetch("pedidoProveedor", JoinType.LEFT);
/* 136:    */     
/* 137:148 */     CriteriaQuery<DetallePedidoProveedor> select = criteriaQuery.select(from);
/* 138:    */     
/* 139:150 */     List<Expression> predicates = new ArrayList();
/* 140:151 */     predicates.add(criteriaBuilder.equal(from.join("pedidoProveedor").get("idPedidoProveedor"), Integer.valueOf(idPedidoProveedor)));
/* 141:153 */     if (documentoBase == DocumentoBase.RECEPCION_BODEGA) {
/* 142:154 */       predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorRecibir").as(BigDecimal.class), BigDecimal.ZERO));
/* 143:156 */     } else if (documentoBase == DocumentoBase.FACTURA_PROVEEDOR) {
/* 144:157 */       predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorFacturar").as(BigDecimal.class), BigDecimal.ZERO));
/* 145:    */     }
/* 146:161 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 147:    */     
/* 148:163 */     return this.em.createQuery(select).getResultList();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<PedidoProveedor> listaPedidosPorRecibir(int idEmpresa)
/* 152:    */   {
/* 153:176 */     StringBuilder sql = new StringBuilder();
/* 154:177 */     sql.append(" SELECT p ");
/* 155:178 */     sql.append(" FROM PedidoProveedor p  ");
/* 156:179 */     sql.append(" LEFT JOIN FETCH p.proyecto pr ");
/* 157:180 */     sql.append(" LEFT JOIN FETCH p.personaResponsable prr ");
/* 158:181 */     sql.append(" WHERE p.empresa.idEmpresa=:idEmpresa");
/* 159:182 */     sql.append(" AND p.estado=:estadoAprobado");
/* 160:183 */     sql.append(" AND p.indicadorSolicitudCambioPrecio = false ");
/* 161:    */     
/* 162:185 */     sql.append(" AND EXISTS (SELECT 1 FROM DetallePedidoProveedor dpc WHERE dpc.pedidoProveedor.idPedidoProveedor = p.idPedidoProveedor AND dpc.cantidadPorRecibir>0)");
/* 163:186 */     sql.append(" ORDER BY p.numero DESC");
/* 164:    */     
/* 165:188 */     Query query = this.em.createQuery(sql.toString());
/* 166:    */     
/* 167:190 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/* 168:191 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 169:192 */     return query.getResultList();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<PedidoProveedor> listaPedidosPorFacturar(int idEmpresa)
/* 173:    */   {
/* 174:204 */     Query query = this.em.createQuery("SELECT new PedidoProveedor(p.idPedidoProveedor,p.numero,p.fecha)  FROM PedidoProveedor p   AND p.estado!=:estadoCerrado  AND p.estado!=:estadoAnulado  AND p.empresa.idEmpresa=:idEmpresa AND EXISTS (SELECT 1 FROM DetallePedidoProveedor dpc WHERE dpc.pedidoProveedor.idPedidoProveedor = p.idPedidoProveedor AND dpc.cantidadPorFacturar>0) ORDER BY p.numero DESC");
/* 175:    */     
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:211 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 182:212 */     query.setParameter("estadoCerrado", Estado.CERRADO);
/* 183:213 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 184:214 */     return query.getResultList();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<PedidoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 188:    */   {
/* 189:219 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 190:220 */     CriteriaQuery<PedidoProveedor> criteriaQuery = criteriaBuilder.createQuery(PedidoProveedor.class);
/* 191:221 */     Root<PedidoProveedor> from = criteriaQuery.from(PedidoProveedor.class);
/* 192:222 */     from.fetch("documento", JoinType.LEFT);
/* 193:223 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/* 194:224 */     from.fetch("bodega", JoinType.LEFT);
/* 195:225 */     from.fetch("proyecto", JoinType.LEFT);
/* 196:    */     
/* 197:227 */     from.fetch("sucursal", JoinType.LEFT);
/* 198:228 */     from.fetch("solicitudCompra", JoinType.LEFT);
/* 199:    */     
/* 200:    */ 
/* 201:231 */     empresa.fetch("cliente", JoinType.LEFT);
/* 202:232 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 203:233 */     empresa.fetch("empleado", JoinType.LEFT);
/* 204:    */     
/* 205:235 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 206:236 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 207:    */     
/* 208:238 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 209:    */     
/* 210:240 */     CriteriaQuery<PedidoProveedor> select = criteriaQuery.select(from);
/* 211:    */     
/* 212:242 */     TypedQuery<PedidoProveedor> typedQuery = this.em.createQuery(select);
/* 213:243 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 214:    */     
/* 215:245 */     return typedQuery.getResultList();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List getReportePedidoProveedor(int idPedidoProveedor)
/* 219:    */     throws ExcepcionAS2
/* 220:    */   {
/* 221:258 */     String sql = "SELECT e.nombreFiscal, u.direccion1, e.identificacion, p.fecha, d.cantidad, pr.codigo, pr.nombreComercial, d.precio, p.total, p.descuento, p.impuesto, c.nombre, p.numeroCuotas, p.descripcion  FROM DetallePedidoProveedor d INNER JOIN d.pedidoProveedor p  INNER JOIN p.empresa e  INNER JOIN p.direccionEmpresa de  INNER JOIN de.ubicacion u INNER JOIN d.producto pr  INNER JOIN p.condicionPago c  WHERE p.idPedidoProveedor = :idPedidoProveedor";
/* 222:    */     
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:264 */     Query query = this.em.createQuery(sql).setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 228:    */     
/* 229:266 */     return query.getResultList();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void actualizarEstado(Integer idPedidoProveedor, Estado estado, String descripcionCambioEstado, String usuarioCambioEstado, Date fecha, boolean indicadorCambioPrecio)
/* 233:    */   {
/* 234:272 */     actualizarEstado(idPedidoProveedor, estado, descripcionCambioEstado, usuarioCambioEstado, fecha, indicadorCambioPrecio, false);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void actualizarEstado(Integer idPedidoProveedor, Estado estado, String descripcionCambioEstado, String usuarioCambioEstado, Date fecha, boolean indicadorCambioPrecio, boolean actualizarUsuarioAprobacion)
/* 238:    */   {
/* 239:283 */     System.out.println(estado + " " + descripcionCambioEstado + " " + usuarioCambioEstado + " " + indicadorCambioPrecio);
/* 240:    */     StringBuilder sql;
/* 241:286 */     if (!indicadorCambioPrecio)
/* 242:    */     {
/* 243:287 */       List<RecepcionProveedor> rp = recepcionAtadaAlPedido(idPedidoProveedor.intValue());
/* 244:288 */       List<DetalleFacturaProveedor> Listadfp = facturaAtadaAlPedido(idPedidoProveedor.intValue());
/* 245:289 */       if (rp.size() > 0)
/* 246:    */       {
/* 247:290 */         StringBuilder sql = new StringBuilder();
/* 248:291 */         sql.append(" UPDATE RecepcionProveedor rp SET rp.pedidoProveedor = NULL ");
/* 249:292 */         sql.append(" WHERE rp.idRecepcionProveedor =:idRecepcionProveedor");
/* 250:293 */         Query query = this.em.createQuery(sql.toString());
/* 251:294 */         query.setParameter("idRecepcionProveedor", Integer.valueOf(((RecepcionProveedor)rp.get(0)).getIdRecepcionProveedor()));
/* 252:295 */         query.executeUpdate();
/* 253:    */       }
/* 254:298 */       if (rp.size() > 0)
/* 255:    */       {
/* 256:299 */         sql = new StringBuilder();
/* 257:300 */         sql.append(" UPDATE DetalleRecepcionProveedor drp SET drp.detallePedidoProveedor = NULL ");
/* 258:301 */         sql.append(" WHERE drp.recepcionProveedor =:recepcionProveedor");
/* 259:302 */         Query query = this.em.createQuery(sql.toString());
/* 260:303 */         query.setParameter("recepcionProveedor", rp.get(0));
/* 261:304 */         query.executeUpdate();
/* 262:    */       }
/* 263:307 */       if (Listadfp.size() > 0) {
/* 264:308 */         for (DetalleFacturaProveedor dfp : Listadfp)
/* 265:    */         {
/* 266:309 */           StringBuilder sql = new StringBuilder();
/* 267:310 */           sql.append(" UPDATE DetalleFacturaProveedor dfp SET dfp.detallePedidoProveedor = NULL ");
/* 268:311 */           sql.append(" WHERE dfp.idDetalleFacturaProveedor =:idDetalleFacturaProveedor");
/* 269:312 */           Query query = this.em.createQuery(sql.toString());
/* 270:313 */           query.setParameter("idDetalleFacturaProveedor", Integer.valueOf(dfp.getIdDetalleFacturaProveedor()));
/* 271:314 */           query.executeUpdate();
/* 272:    */         }
/* 273:    */       }
/* 274:    */     }
/* 275:320 */     StringBuilder sql = new StringBuilder();
/* 276:321 */     sql.append(" UPDATE PedidoProveedor p SET p.estado=:estado, ");
/* 277:322 */     sql.append(" p.descripcionCambioEstado = :descripcionCambioEstado, ");
/* 278:323 */     if (!indicadorCambioPrecio) {
/* 279:324 */       sql.append(" p.usuarioCambioEstado = :usuarioCambioEstado, ");
/* 280:    */     } else {
/* 281:326 */       sql.append(" p.usuarioAprobacionCambioPrecio = :usuarioCambioEstado, ");
/* 282:    */     }
/* 283:328 */     sql.append(" p.fechaCambioEstado = :fecha ");
/* 284:329 */     if ((!indicadorCambioPrecio) && ((Estado.APROBADO.equals(estado)) || (Estado.APROBADO_PARCIAL.equals(estado))))
/* 285:    */     {
/* 286:330 */       if (actualizarUsuarioAprobacion) {
/* 287:331 */         sql.append(" , p.usuariosAutorizacion = CONCAT(p.usuariosAutorizacion , ', ', :usuarioCambioEstado )");
/* 288:    */       }
/* 289:    */     }
/* 290:333 */     else if ((indicadorCambioPrecio) && (Estado.APROBADO.equals(estado))) {
/* 291:334 */       sql.append(" , p.indicadorSolicitudCambioPrecio = false ");
/* 292:    */     }
/* 293:339 */     sql.append(" , p.fechaModificacion = :fechaModificacion ");
/* 294:340 */     sql.append(" , p.usuarioModificacion = :usuarioCambioEstado ");
/* 295:341 */     sql.append(" WHERE p.idPedidoProveedor=:idPedidoProveedor");
/* 296:342 */     Query query = this.em.createQuery(sql.toString());
/* 297:343 */     query.setParameter("idPedidoProveedor", idPedidoProveedor);
/* 298:344 */     query.setParameter("estado", estado);
/* 299:345 */     query.setParameter("descripcionCambioEstado", descripcionCambioEstado);
/* 300:346 */     query.setParameter("usuarioCambioEstado", usuarioCambioEstado);
/* 301:347 */     query.setParameter("fecha", fecha);
/* 302:348 */     query.setParameter("fechaModificacion", new Date());
/* 303:349 */     query.executeUpdate();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List<DetallePedidoProveedor> obtenerListaDetallePedidoPorRecibir(int idPedidoProveedor, boolean aprobacionParcial)
/* 307:    */   {
/* 308:360 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 309:361 */     CriteriaQuery<DetallePedidoProveedor> criteriaQuery = criteriaBuilder.createQuery(DetallePedidoProveedor.class);
/* 310:362 */     Root<DetallePedidoProveedor> from = criteriaQuery.from(DetallePedidoProveedor.class);
/* 311:    */     
/* 312:364 */     from.fetch("unidadCompra", JoinType.LEFT);
/* 313:365 */     from.fetch("pedidoProveedor", JoinType.LEFT);
/* 314:    */     
/* 315:367 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/* 316:368 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 317:369 */     producto.fetch("bodegaCompra", JoinType.LEFT);
/* 318:    */     
/* 319:371 */     CriteriaQuery<DetallePedidoProveedor> select = criteriaQuery.select(from);
/* 320:    */     
/* 321:373 */     List<Expression> predicates = new ArrayList();
/* 322:374 */     predicates.add(criteriaBuilder.equal(from.join("pedidoProveedor").get("idPedidoProveedor"), Integer.valueOf(idPedidoProveedor)));
/* 323:375 */     predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorRecibir").as(BigDecimal.class), BigDecimal.ZERO));
/* 324:377 */     if (aprobacionParcial) {
/* 325:378 */       predicates.add(criteriaBuilder.equal(from.get("indicadorAprobado").as(Boolean.class), Boolean.valueOf(true)));
/* 326:    */     }
/* 327:381 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 328:    */     
/* 329:383 */     return this.em.createQuery(select).getResultList();
/* 330:    */   }
/* 331:    */   
/* 332:    */   public boolean esPedidoPorRecibir(int idPedidoProveedor)
/* 333:    */   {
/* 334:393 */     Query query = this.em.createQuery("SELECT COUNT(*) FROM DetallePedidoProveedor dpc INNER JOIN dpc.pedidoProveedor pc  WHERE pc.idPedidoProveedor=:idPedidoProveedor AND (dpc.cantidadPorRecibir>0 OR dpc.cantidadPorFacturar>0)");
/* 335:    */     
/* 336:395 */     query.setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 337:    */     
/* 338:397 */     return ((Long)query.getSingleResult()).longValue() > 0L;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public List<DetallePedidoProveedor> obtenerListaDetallePedidoPorFacturar(int idPedidoProveedor)
/* 342:    */   {
/* 343:408 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 344:409 */     CriteriaQuery<DetallePedidoProveedor> criteriaQuery = criteriaBuilder.createQuery(DetallePedidoProveedor.class);
/* 345:410 */     Root<DetallePedidoProveedor> from = criteriaQuery.from(DetallePedidoProveedor.class);
/* 346:    */     
/* 347:412 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/* 348:413 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 349:414 */     producto.fetch("partidaArancelaria", JoinType.LEFT);
/* 350:415 */     producto.fetch("unidadCompra", JoinType.LEFT);
/* 351:416 */     from.fetch("pedidoProveedor", JoinType.LEFT).fetch("direccionEmpresa", JoinType.LEFT);
/* 352:417 */     from.fetch("unidadCompra", JoinType.LEFT);
/* 353:    */     
/* 354:419 */     CriteriaQuery<DetallePedidoProveedor> select = criteriaQuery.select(from);
/* 355:    */     
/* 356:421 */     List<Expression> predicates = new ArrayList();
/* 357:422 */     predicates.add(criteriaBuilder.equal(from.join("pedidoProveedor").get("idPedidoProveedor"), Integer.valueOf(idPedidoProveedor)));
/* 358:423 */     predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorFacturar").as(BigDecimal.class), BigDecimal.ZERO));
/* 359:    */     
/* 360:425 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 361:    */     
/* 362:427 */     return this.em.createQuery(select).getResultList();
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void actualizarCantidadPorRecibir(DetallePedidoProveedor dpp, BigDecimal cantidad)
/* 366:    */   {
/* 367:437 */     String sql = "UPDATE DetallePedidoProveedor dpc SET dpc.cantidadPorRecibir = dpc.cantidadPorRecibir - :cantidad WHERE dpc.idDetallePedidoProveedor = :idDetallePedidoProveedor";
/* 368:    */     
/* 369:    */ 
/* 370:440 */     Query query = this.em.createQuery(sql);
/* 371:441 */     query.setParameter("idDetallePedidoProveedor", Integer.valueOf(dpp.getId()));
/* 372:442 */     query.setParameter("cantidad", cantidad);
/* 373:443 */     query.executeUpdate();
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void actualizarCantidadPorFacturar(DetallePedidoProveedor dpp, BigDecimal cantidad)
/* 377:    */   {
/* 378:453 */     String sql = "UPDATE DetallePedidoProveedor dpc SET dpc.cantidadPorFacturar = dpc.cantidadPorFacturar - :cantidad WHERE dpc.idDetallePedidoProveedor = :idDetallePedidoProveedor";
/* 379:    */     
/* 380:    */ 
/* 381:456 */     Query query = this.em.createQuery(sql);
/* 382:457 */     query.setParameter("idDetallePedidoProveedor", Integer.valueOf(dpp.getId()));
/* 383:458 */     query.setParameter("cantidad", cantidad);
/* 384:459 */     query.executeUpdate();
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<PedidoProveedor> cargarPedidosPorAprobar(int idOrganizacion, Sucursal sucursal, Documento documento, Empresa proveedor, Date fechaDesde, Date fechaHasta, boolean cargarPedidoAprobados, Usuario usuarioSesion, List<EntidadUsuario> listaSuperiores, List<EntidadUsuario> listaSubordinados, boolean indicadorMostrarTodoAprobacion, CategoriaEmpresa categoriaEmpresa)
/* 388:    */     throws AS2Exception
/* 389:    */   {
/* 390:476 */     StringBuilder sql = new StringBuilder();
/* 391:477 */     sql.append(" SELECT p FROM PedidoProveedor p ");
/* 392:478 */     sql.append(" JOIN FETCH p.empresa e ");
/* 393:479 */     sql.append(" JOIN FETCH e.categoriaEmpresa ce ");
/* 394:480 */     sql.append(" JOIN FETCH p.documento d  ");
/* 395:481 */     sql.append(" JOIN FETCH p.sucursal s ");
/* 396:482 */     sql.append(" LEFT JOIN FETCH p.personaResponsable pr ");
/* 397:483 */     sql.append(" LEFT JOIN FETCH p.condicionPago cp ");
/* 398:485 */     if (cargarPedidoAprobados)
/* 399:    */     {
/* 400:487 */       sql.append(" WHERE (p.estado = :estadoAprobado OR p.estado = :estadoEnEspera) AND p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 401:488 */       sql.append(" AND p.idPedidoProveedor NOT IN (SELECT pp.idPedidoProveedor  FROM DetalleRecepcionProveedor drp JOIN drp.recepcionProveedor rp JOIN drp.detallePedidoProveedor dpp JOIN dpp.pedidoProveedor pp WHERE rp.estado !=:estadoAnulado) " + (proveedor == null ? "" : " AND p.empresa.idEmpresa =:idEmpresa "));
/* 402:    */       
/* 403:490 */       sql.append(" AND p.idPedidoProveedor NOT IN (SELECT pp.idPedidoProveedor  FROM DetalleFacturaProveedor dfp   JOIN dfp.facturaProveedor fp   JOIN dfp.detallePedidoProveedor dpp JOIN dpp.pedidoProveedor pp WHERE fp.estado !=:estadoAnulado) " + (proveedor == null ? "" : " AND p.empresa.idEmpresa =:idEmpresa "));
/* 404:    */     }
/* 405:493 */     else if (!usuarioSesion.isIndicadorAprobador())
/* 406:    */     {
/* 407:495 */       sql.append(" WHERE (p.estado = :estadoElaborado OR p.estado = :estadoEnEspera OR p.indicadorSolicitudCambioPrecio = true) AND p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 408:    */     }
/* 409:    */     else
/* 410:    */     {
/* 411:497 */       if ((listaSuperiores.isEmpty()) && (listaSubordinados.isEmpty())) {
/* 412:498 */         throw new AS2Exception("ERROR_NO_DEFINIDA_JERARQUIA_USUARIO_APROBADOR", new String[] { usuarioSesion.getNombreUsuario() });
/* 413:    */       }
/* 414:502 */       if ((!listaSuperiores.isEmpty()) && (listaSubordinados.isEmpty()))
/* 415:    */       {
/* 416:504 */         sql.append(" WHERE (p.estado = :estadoElaborado OR (p.indicadorSolicitudCambioPrecio = true AND p.estado != :estadoAprobadoParcial)) AND p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 417:506 */         if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioSesion.getTipoVisualizacion())) {
/* 418:508 */           sql.append(" AND s.idSucursal IN :listaIdSucursal ");
/* 419:    */         }
/* 420:    */       }
/* 421:    */       else
/* 422:    */       {
/* 423:512 */         sql.append(" WHERE p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 424:514 */         if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioSesion.getTipoVisualizacion())) {
/* 425:516 */           sql.append(" AND s.idSucursal IN :listaIdSucursal ");
/* 426:    */         }
/* 427:519 */         sql.append(" AND ((p.indicadorSolicitudCambioPrecio = false AND p.usuarioCambioEstado != :usuarioSesion) ");
/* 428:520 */         sql.append(" OR (p.indicadorSolicitudCambioPrecio = true AND p.usuarioAprobacionCambioPrecio != :usuarioSesion) ) ");
/* 429:523 */         if (!indicadorMostrarTodoAprobacion)
/* 430:    */         {
/* 431:525 */           sql.append(" AND ((p.estado = :estadoAprobadoParcial ");
/* 432:526 */           sql.append(" AND ((p.indicadorSolicitudCambioPrecio = false AND p.usuarioCambioEstado IN (:listaUsuarioSubordinados)) ");
/* 433:527 */           sql.append(" OR (p.indicadorSolicitudCambioPrecio = true AND p.usuarioAprobacionCambioPrecio IN (:listaUsuarioSubordinados)))) OR (p.estado = :estadoElaborado AND p.usuarioCreacion IN (:listaUsuarioSubordinados)))");
/* 434:    */         }
/* 435:    */         else
/* 436:    */         {
/* 437:531 */           sql.append(" AND ( p.estado = :estadoElaborado OR p.estado = :estadoAprobadoParcial OR p.indicadorSolicitudCambioPrecio = true )");
/* 438:    */         }
/* 439:    */       }
/* 440:    */     }
/* 441:536 */     if (sucursal != null) {
/* 442:537 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 443:    */     }
/* 444:539 */     if (proveedor != null) {
/* 445:540 */       sql.append(" AND e.idEmpresa = :idEmpresa ");
/* 446:    */     }
/* 447:542 */     if (documento != null) {
/* 448:543 */       sql.append(" AND d = :documento ");
/* 449:    */     }
/* 450:545 */     if ((categoriaEmpresa != null) && (proveedor == null)) {
/* 451:546 */       sql.append(" AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 452:    */     }
/* 453:548 */     sql.append(" AND p.idOrganizacion = :idOrganizacion ");
/* 454:    */     
/* 455:550 */     Query query = this.em.createQuery(sql.toString());
/* 456:551 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 457:552 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 458:553 */     if ((cargarPedidoAprobados) || (!usuarioSesion.isIndicadorAprobador())) {
/* 459:554 */       query.setParameter("estadoEnEspera", Estado.EN_ESPERA);
/* 460:    */     }
/* 461:556 */     if (cargarPedidoAprobados)
/* 462:    */     {
/* 463:557 */       query.setParameter("estadoAprobado", Estado.APROBADO);
/* 464:558 */       query.setParameter("estadoAnulado", Estado.ANULADO);
/* 465:    */     }
/* 466:561 */     if (sql.toString().contains(":estadoElaborado")) {
/* 467:562 */       query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 468:    */     }
/* 469:565 */     if (sucursal != null) {
/* 470:566 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 471:    */     }
/* 472:568 */     if (proveedor != null) {
/* 473:569 */       query.setParameter("idEmpresa", Integer.valueOf(proveedor.getIdEmpresa()));
/* 474:    */     }
/* 475:571 */     if (documento != null) {
/* 476:572 */       query.setParameter("documento", documento);
/* 477:    */     }
/* 478:575 */     if ((categoriaEmpresa != null) && (proveedor == null)) {
/* 479:576 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 480:    */     }
/* 481:579 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 482:582 */     if ((!cargarPedidoAprobados) && (usuarioSesion.isIndicadorAprobador()))
/* 483:    */     {
/* 484:583 */       if (!TipoVisualizacionEnum.TODA_LA_ORGANIZACION.equals(usuarioSesion.getTipoVisualizacion()))
/* 485:    */       {
/* 486:584 */         List<Integer> listaIdSucursal = new ArrayList();
/* 487:585 */         for (UsuarioSucursal usuarioSucursal : usuarioSesion.getListaUsuarioSucursal()) {
/* 488:586 */           listaIdSucursal.add(Integer.valueOf(usuarioSucursal.getSucursal().getId()));
/* 489:    */         }
/* 490:588 */         query.setParameter("listaIdSucursal", listaIdSucursal);
/* 491:    */       }
/* 492:590 */       query.setParameter("estadoAprobadoParcial", Estado.APROBADO_PARCIAL);
/* 493:591 */       if ((listaSuperiores.isEmpty()) || (!listaSubordinados.isEmpty()))
/* 494:    */       {
/* 495:592 */         query.setParameter("usuarioSesion", usuarioSesion.getNombreUsuario());
/* 496:593 */         if (!indicadorMostrarTodoAprobacion)
/* 497:    */         {
/* 498:594 */           List<String> listaUsuarioSubordinados = new ArrayList();
/* 499:595 */           for (EntidadUsuario entidadUsuario : listaSubordinados) {
/* 500:596 */             listaUsuarioSubordinados.add(entidadUsuario.getNombreUsuario());
/* 501:    */           }
/* 502:598 */           query.setParameter("listaUsuarioSubordinados", listaUsuarioSubordinados);
/* 503:    */         }
/* 504:    */       }
/* 505:    */     }
/* 506:603 */     return query.getResultList();
/* 507:    */   }
/* 508:    */   
/* 509:    */   public List<Object[]> getResumenAnualAprobadasYPorAprobar(int idOrganizanizacion, int anio, int mes, Documento documento)
/* 510:    */   {
/* 511:618 */     StringBuilder sbSQL = new StringBuilder();
/* 512:619 */     sbSQL.append(" SELECT CASE WHEN dc1.nombre IS NOT NULL THEN '01' WHEN dc2.nombre IS NOT NULL THEN '02' WHEN dc3.nombre IS NOT NULL THEN '03' WHEN dc4.nombre IS NOT NULL THEN '04' WHEN dc5.nombre IS NOT NULL THEN '05' ELSE '' END,");
/* 513:620 */     sbSQL.append(" ROUND(SUM(CASE WHEN (pp.estado=:estadoAprobado OR pp.estado=:estadoCerrado) THEN (dp.cantidad*(dp.precio-dp.descuento)) ELSE 0.00 END),2), ");
/* 514:621 */     sbSQL.append(" ROUND(SUM(CASE WHEN (pp.estado=:estadoAprobado OR pp.estado=:estadoCerrado) AND YEAR(COALESCE(pp.fechaCambioEstado,pp.fecha))=:anio AND MONTH(COALESCE(pp.fechaCambioEstado,pp.fecha))=:mes THEN (dp.cantidad*(dp.precio-dp.descuento)) ELSE 0.00 END),2), ");
/* 515:622 */     sbSQL.append(" ROUND(SUM(CASE WHEN (pp.estado=:estadoElaborado OR pp.estado=:estadoEnEspera) THEN (dp.cantidad*(dp.precio-dp.descuento)) ELSE 0.00 END),2) ");
/* 516:623 */     sbSQL.append(" FROM DetallePedidoProveedor dp");
/* 517:624 */     sbSQL.append(" JOIN dp.pedidoProveedor pp");
/* 518:625 */     sbSQL.append(" LEFT JOIN pp.documento d");
/* 519:626 */     sbSQL.append(" LEFT JOIN dp.dimensionContable1 dc1");
/* 520:627 */     sbSQL.append(" LEFT JOIN dp.dimensionContable2 dc2");
/* 521:628 */     sbSQL.append(" LEFT JOIN dp.dimensionContable3 dc3");
/* 522:629 */     sbSQL.append(" LEFT JOIN dp.dimensionContable4 dc4");
/* 523:630 */     sbSQL.append(" LEFT JOIN dp.dimensionContable5 dc5");
/* 524:631 */     sbSQL.append(" WHERE pp.estado != :estadoAnulado");
/* 525:632 */     sbSQL.append(" AND d.documentoBase = :documentoBase ");
/* 526:633 */     sbSQL.append(" AND YEAR(pp.fecha) =:anio ");
/* 527:634 */     if (documento != null) {
/* 528:635 */       sbSQL.append(" AND d =:documento ");
/* 529:    */     }
/* 530:637 */     sbSQL.append(" AND pp.idOrganizacion = :idOrganizacion ");
/* 531:638 */     sbSQL.append(" GROUP BY CASE WHEN dc1.nombre IS NOT NULL THEN '01' WHEN dc2.nombre IS NOT NULL THEN '02' WHEN dc3.nombre IS NOT NULL THEN '03' WHEN dc4.nombre IS NOT NULL THEN '04' WHEN dc5.nombre IS NOT NULL THEN '05' ELSE '' END");
/* 532:    */     
/* 533:640 */     Query query = this.em.createQuery(sbSQL.toString());
/* 534:641 */     query.setParameter("anio", Integer.valueOf(anio));
/* 535:642 */     query.setParameter("mes", Integer.valueOf(mes));
/* 536:643 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 537:644 */     query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 538:645 */     query.setParameter("estadoEnEspera", Estado.EN_ESPERA);
/* 539:646 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/* 540:647 */     query.setParameter("documentoBase", DocumentoBase.PEDIDO_PROVEEDOR);
/* 541:648 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizanizacion));
/* 542:649 */     if (documento != null) {
/* 543:650 */       query.setParameter("documento", documento);
/* 544:    */     }
/* 545:655 */     query.setParameter("estadoCerrado", Estado.CERRADO);
/* 546:    */     
/* 547:657 */     return query.getResultList();
/* 548:    */   }
/* 549:    */   
/* 550:    */   public List<FacturaProveedor> pedidoEnFacturaProveedor(int idPedidoProveedor)
/* 551:    */     throws ExcepcionAS2Compras
/* 552:    */   {
/* 553:662 */     StringBuilder sql = new StringBuilder();
/* 554:663 */     sql.append(" SELECT fp ");
/* 555:664 */     sql.append(" FROM DetalleFacturaProveedor dfp ");
/* 556:665 */     sql.append(" LEFT JOIN dfp.facturaProveedor fp ");
/* 557:666 */     sql.append(" LEFT JOIN dfp.detallePedidoProveedor dpp ");
/* 558:667 */     sql.append(" LEFT JOIN dpp.pedidoProveedor pp ");
/* 559:668 */     sql.append(" WHERE pp.idPedidoProveedor = :idPedidoProveedor ");
/* 560:669 */     Query query = this.em.createQuery(sql.toString());
/* 561:670 */     query.setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 562:671 */     List<FacturaProveedor> listaFacturaProveedor = query.getResultList();
/* 563:672 */     if (listaFacturaProveedor.size() > 0) {
/* 564:673 */       throw new ExcepcionAS2Compras("msg_error_anulacion_pedido_proveedor", " F:/ " + ((FacturaProveedor)listaFacturaProveedor.get(0)).getNumero());
/* 565:    */     }
/* 566:675 */     return listaFacturaProveedor;
/* 567:    */   }
/* 568:    */   
/* 569:    */   public List<DetalleFacturaProveedor> facturaAtadaAlPedido(int idPedidoProveedor)
/* 570:    */   {
/* 571:680 */     StringBuilder sql = new StringBuilder();
/* 572:681 */     sql.append(" SELECT dfp ");
/* 573:682 */     sql.append(" FROM DetalleFacturaProveedor dfp ");
/* 574:683 */     sql.append(" LEFT JOIN dfp.facturaProveedor fp ");
/* 575:684 */     sql.append(" LEFT JOIN dfp.detallePedidoProveedor dpp ");
/* 576:685 */     sql.append(" LEFT JOIN dpp.pedidoProveedor pp ");
/* 577:686 */     sql.append(" WHERE pp.idPedidoProveedor = :idPedidoProveedor ");
/* 578:687 */     sql.append(" AND fp.estado =:estado ");
/* 579:688 */     Query query = this.em.createQuery(sql.toString());
/* 580:689 */     query.setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 581:690 */     query.setParameter("estado", Estado.ANULADO);
/* 582:    */     
/* 583:692 */     return query.getResultList();
/* 584:    */   }
/* 585:    */   
/* 586:    */   public List<RecepcionProveedor> pedidoEnRecepcionProveedor(int idPedidoProveedor)
/* 587:    */     throws ExcepcionAS2Compras
/* 588:    */   {
/* 589:696 */     StringBuilder sql = new StringBuilder();
/* 590:697 */     sql.append(" SELECT rp ");
/* 591:698 */     sql.append(" FROM RecepcionProveedor rp ");
/* 592:699 */     sql.append(" INNER JOIN rp.pedidoProveedor pp ");
/* 593:700 */     sql.append(" WHERE pp.idPedidoProveedor = :idPedidoProveedor ");
/* 594:701 */     Query query = this.em.createQuery(sql.toString());
/* 595:702 */     query.setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 596:703 */     List<RecepcionProveedor> listaRecepcionProveedor = query.getResultList();
/* 597:704 */     if (listaRecepcionProveedor.size() > 0) {
/* 598:705 */       throw new ExcepcionAS2Compras("msg_error_anulacion_pedido_proveedor", " R:/ " + ((RecepcionProveedor)listaRecepcionProveedor.get(0)).getNumero());
/* 599:    */     }
/* 600:707 */     return listaRecepcionProveedor;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public List<RecepcionProveedor> recepcionAtadaAlPedido(int idPedidoProveedor)
/* 604:    */   {
/* 605:712 */     StringBuilder sql = new StringBuilder();
/* 606:713 */     sql.append(" SELECT rp ");
/* 607:714 */     sql.append(" FROM RecepcionProveedor rp ");
/* 608:715 */     sql.append(" INNER JOIN rp.pedidoProveedor pp ");
/* 609:716 */     sql.append(" WHERE pp.idPedidoProveedor = :idPedidoProveedor ");
/* 610:717 */     sql.append(" AND rp.estado =:estado ");
/* 611:718 */     Query query = this.em.createQuery(sql.toString());
/* 612:719 */     query.setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 613:720 */     query.setParameter("estado", Estado.ANULADO);
/* 614:721 */     return query.getResultList();
/* 615:    */   }
/* 616:    */   
/* 617:    */   public List<PedidoProveedor> cargarPedidosCambioElaborado(int idOrganizacion, Sucursal sucursal, Documento documento, Empresa proveedor, Date fechaDesde, Date fechaHasta, boolean cargarPedidoProcesados)
/* 618:    */   {
/* 619:728 */     StringBuilder sql = new StringBuilder();
/* 620:729 */     sql.append(" SELECT p FROM PedidoProveedor p ");
/* 621:730 */     sql.append(" JOIN FETCH p.empresa e ");
/* 622:731 */     sql.append(" JOIN p.documento d  ");
/* 623:732 */     sql.append(" JOIN p.sucursal s ");
/* 624:733 */     sql.append(" LEFT JOIN FETCH p.condicionPago cp ");
/* 625:735 */     if (cargarPedidoProcesados)
/* 626:    */     {
/* 627:736 */       sql.append(" WHERE (p.estado = :estadoAprobado OR p.estado = :estadoEnEspera ) AND p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 628:737 */       sql.append(" AND ( p.idPedidoProveedor NOT IN (SELECT pp.idPedidoProveedor FROM DetalleFacturaProveedor dfp ");
/* 629:738 */       sql.append("  INNER JOIN dfp.facturaProveedor fp ");
/* 630:739 */       sql.append("  INNER JOIN dfp.detallePedidoProveedor dpp ");
/* 631:740 */       sql.append("  INNER JOIN dpp.pedidoProveedor pp ");
/* 632:741 */       sql.append("  WHERE fp.estado !=:estadoAnulado  ");
/* 633:742 */       sql.append("  GROUP BY pp) OR p.idPedidoProveedor NOT IN (SELECT pp.idPedidoProveedor FROM RecepcionProveedor rp");
/* 634:743 */       sql.append("  INNER JOIN rp.pedidoProveedor pp");
/* 635:744 */       sql.append("  WHERE rp.estado !=:estadoAnulado  ");
/* 636:745 */       sql.append("  GROUP BY pp )) ");
/* 637:    */     }
/* 638:    */     else
/* 639:    */     {
/* 640:747 */       sql.append(" WHERE (p.estado = :estadoElaborado OR p.estado = :estadoEnEspera ) AND p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 641:    */     }
/* 642:750 */     if (sucursal != null) {
/* 643:751 */       sql.append(" AND s.idSucursal = :idSucursal ");
/* 644:    */     }
/* 645:753 */     if (proveedor != null) {
/* 646:754 */       sql.append(" AND e.idEmpresa = :idEmpresa ");
/* 647:    */     }
/* 648:756 */     if (documento != null) {
/* 649:757 */       sql.append(" AND d = :documento ");
/* 650:    */     }
/* 651:760 */     Query query = this.em.createQuery(sql.toString());
/* 652:761 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 653:762 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 654:763 */     query.setParameter("estadoEnEspera", Estado.EN_ESPERA);
/* 655:764 */     if (cargarPedidoProcesados)
/* 656:    */     {
/* 657:765 */       query.setParameter("estadoAprobado", Estado.APROBADO);
/* 658:766 */       query.setParameter("estadoAnulado", Estado.ANULADO);
/* 659:    */     }
/* 660:    */     else
/* 661:    */     {
/* 662:768 */       query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 663:    */     }
/* 664:772 */     if (sucursal != null) {
/* 665:773 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 666:    */     }
/* 667:775 */     if (proveedor != null) {
/* 668:776 */       query.setParameter("idEmpresa", Integer.valueOf(proveedor.getIdEmpresa()));
/* 669:    */     }
/* 670:778 */     if (documento != null) {
/* 671:779 */       query.setParameter("documento", documento);
/* 672:    */     }
/* 673:782 */     return query.getResultList();
/* 674:    */   }
/* 675:    */   
/* 676:    */   public List<PedidoProveedor> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 677:    */   {
/* 678:792 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 679:793 */     CriteriaQuery<PedidoProveedor> criteriaQuery = criteriaBuilder.createQuery(PedidoProveedor.class);
/* 680:794 */     Root<PedidoProveedor> from = criteriaQuery.from(PedidoProveedor.class);
/* 681:795 */     from.fetch("proyecto", JoinType.LEFT);
/* 682:    */     
/* 683:797 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 684:798 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 685:    */     
/* 686:800 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 687:    */     
/* 688:802 */     CriteriaQuery<PedidoProveedor> select = criteriaQuery.select(from);
/* 689:803 */     TypedQuery<PedidoProveedor> typedQuery = this.em.createQuery(select);
/* 690:    */     
/* 691:805 */     return typedQuery.getResultList();
/* 692:    */   }
/* 693:    */   
/* 694:    */   public List<Contacto> getContactosPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 695:    */   {
/* 696:810 */     StringBuilder sql = new StringBuilder();
/* 697:811 */     sql.append("SELECT con");
/* 698:812 */     sql.append(" FROM PedidoProveedor pp ");
/* 699:813 */     sql.append(" INNER JOIN pp.empresa e ");
/* 700:814 */     sql.append(" INNER JOIN e.listaContacto con ");
/* 701:815 */     sql.append(" INNER JOIN FETCH con.tipoContacto tc ");
/* 702:816 */     sql.append(" WHERE pp.idPedidoProveedor = :idPedidoProveedor");
/* 703:817 */     sql.append(" AND tc.indicadorNotificarPedidoProveedor = true");
/* 704:    */     
/* 705:819 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPedidoProveedor", Integer.valueOf(pedidoProveedor.getId()));
/* 706:820 */     return query.getResultList();
/* 707:    */   }
/* 708:    */   
/* 709:    */   public PedidoProveedor buscarPorNumero(Integer idOrganizacion, String numero, String nombreDocumento)
/* 710:    */     throws AS2Exception
/* 711:    */   {
/* 712:832 */     StringBuilder sql = new StringBuilder();
/* 713:833 */     sql.append(" SELECT pp");
/* 714:834 */     sql.append(" FROM PedidoProveedor pp ");
/* 715:835 */     sql.append(" JOIN pp.documento do ");
/* 716:836 */     sql.append(" WHERE pp.idOrganizacion = :idOrganizacion");
/* 717:837 */     sql.append(" AND pp.numero = :numero");
/* 718:838 */     sql.append(" AND do.nombre = :nombreDocumento");
/* 719:    */     
/* 720:840 */     Query query = this.em.createQuery(sql.toString());
/* 721:841 */     query.setParameter("idOrganizacion", idOrganizacion);
/* 722:842 */     query.setParameter("numero", numero);
/* 723:843 */     query.setParameter("nombreDocumento", nombreDocumento);
/* 724:    */     try
/* 725:    */     {
/* 726:846 */       return (PedidoProveedor)query.getSingleResult();
/* 727:    */     }
/* 728:    */     catch (NoResultException e)
/* 729:    */     {
/* 730:848 */       throw new AS2Exception("msg_info_registro_no_encontrado", new String[] { "numero=" + numero + " nombreDocumento=" + nombreDocumento });
/* 731:    */     }
/* 732:    */   }
/* 733:    */   
/* 734:    */   public void aprobarPorProducto(PedidoProveedor pedidoProveedor, Producto producto)
/* 735:    */   {
/* 736:858 */     StringBuilder sql = new StringBuilder();
/* 737:859 */     sql.append(" UPDATE DetallePedidoProveedor dp set dp.indicadorAprobado = TRUE ");
/* 738:860 */     sql.append(" WHERE dp.pedidoProveedor = :pedidoProveedor");
/* 739:861 */     if (producto != null) {
/* 740:862 */       sql.append(" AND dp.producto = :producto");
/* 741:    */     }
/* 742:865 */     Query query = this.em.createQuery(sql.toString());
/* 743:866 */     query.setParameter("pedidoProveedor", pedidoProveedor);
/* 744:867 */     if (producto != null) {
/* 745:868 */       query.setParameter("producto", producto);
/* 746:    */     }
/* 747:871 */     query.executeUpdate();
/* 748:    */   }
/* 749:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PedidoProveedorDao
 * JD-Core Version:    0.7.0.1
 */