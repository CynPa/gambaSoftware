/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.entities.Asiento;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   9:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  10:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  11:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  12:    */ import com.asinfo.as2.entities.Empresa;
/*  13:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  14:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  15:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  16:    */ import com.asinfo.as2.entities.Lote;
/*  17:    */ import com.asinfo.as2.entities.Producto;
/*  18:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  21:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  22:    */ import java.math.BigDecimal;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.ejb.Stateless;
/*  30:    */ import javax.persistence.EntityManager;
/*  31:    */ import javax.persistence.NoResultException;
/*  32:    */ import javax.persistence.Query;
/*  33:    */ import javax.persistence.TypedQuery;
/*  34:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  35:    */ import javax.persistence.criteria.CriteriaQuery;
/*  36:    */ import javax.persistence.criteria.Expression;
/*  37:    */ import javax.persistence.criteria.Fetch;
/*  38:    */ import javax.persistence.criteria.Join;
/*  39:    */ import javax.persistence.criteria.JoinType;
/*  40:    */ import javax.persistence.criteria.Path;
/*  41:    */ import javax.persistence.criteria.Predicate;
/*  42:    */ import javax.persistence.criteria.Root;
/*  43:    */ 
/*  44:    */ @Stateless
/*  45:    */ public class DespachoClienteDao
/*  46:    */   extends AbstractDaoAS2<DespachoCliente>
/*  47:    */ {
/*  48:    */   @EJB
/*  49:    */   private SerieInventarioProductoDao serieInventarioProductoDao;
/*  50:    */   
/*  51:    */   public DespachoClienteDao()
/*  52:    */   {
/*  53: 62 */     super(DespachoCliente.class);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public DespachoCliente cargarDetalle(int idDespachoCliente)
/*  57:    */   {
/*  58: 67 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  59:    */     
/*  60:    */ 
/*  61: 70 */     CriteriaQuery<DespachoCliente> cqCabecera = criteriaBuilder.createQuery(DespachoCliente.class);
/*  62: 71 */     Root<DespachoCliente> fromCabecera = cqCabecera.from(DespachoCliente.class);
/*  63:    */     
/*  64: 73 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  65: 74 */     fromCabecera.fetch("tipoOrdenDespacho", JoinType.LEFT);
/*  66: 75 */     documento.fetch("secuencia", JoinType.LEFT);
/*  67: 76 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  68:    */     
/*  69: 78 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/*  70: 79 */     direccion.fetch("ciudad", JoinType.INNER);
/*  71: 80 */     direccion.fetch("ubicacion", JoinType.LEFT);
/*  72:    */     
/*  73: 82 */     fromCabecera.fetch("pedidoCliente", JoinType.LEFT);
/*  74: 83 */     fromCabecera.fetch("prefacturaCliente", JoinType.LEFT);
/*  75: 84 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  76: 85 */     fromCabecera.fetch("empresa", JoinType.LEFT);
/*  77: 86 */     fromCabecera.fetch("subempresa", JoinType.LEFT);
/*  78: 87 */     fromCabecera.fetch("responsableSalidaMercaderia", JoinType.LEFT);
/*  79: 88 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*  80:    */     
/*  81: 90 */     Path<Integer> pathId = fromCabecera.get("idDespachoCliente");
/*  82: 91 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idDespachoCliente)));
/*  83: 92 */     CriteriaQuery<DespachoCliente> selectDespacho = cqCabecera.select(fromCabecera);
/*  84:    */     
/*  85: 94 */     DespachoCliente despachoCliente = (DespachoCliente)this.em.createQuery(selectDespacho).getSingleResult();
/*  86:    */     
/*  87:    */ 
/*  88: 97 */     CriteriaQuery<DetalleDespachoCliente> cqDetalle = criteriaBuilder.createQuery(DetalleDespachoCliente.class);
/*  89: 98 */     Root<DetalleDespachoCliente> fromDetalle = cqDetalle.from(DetalleDespachoCliente.class);
/*  90:    */     
/*  91:100 */     fromDetalle.fetch("unidadVenta", JoinType.LEFT);
/*  92:    */     
/*  93:102 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  94:103 */     Fetch<Object, Object> cabecera = fromDetalle.fetch("despachoCliente", JoinType.INNER);
/*  95:104 */     cabecera.fetch("tipoOrdenDespacho", JoinType.LEFT);
/*  96:105 */     producto.fetch("unidad", JoinType.LEFT);
/*  97:106 */     fromDetalle.fetch("bodega", JoinType.LEFT);
/*  98:    */     
/*  99:108 */     Fetch<Object, Object> detallePedidoCliente = fromDetalle.fetch("detallePedidoCliente", JoinType.LEFT);
/* 100:109 */     detallePedidoCliente.fetch("pedidoCliente", JoinType.LEFT);
/* 101:    */     
/* 102:111 */     Path<Integer> pathIdDetalle = fromDetalle.join("despachoCliente").get("idDespachoCliente");
/* 103:112 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idDespachoCliente)));
/* 104:113 */     CriteriaQuery<DetalleDespachoCliente> selectDetalleDespacho = cqDetalle.select(fromDetalle);
/* 105:    */     
/* 106:115 */     List<DetalleDespachoCliente> listaDetalleDespachoCliente = this.em.createQuery(selectDetalleDespacho).getResultList();
/* 107:116 */     despachoCliente.setListaDetalleDespachoCliente(listaDetalleDespachoCliente);
/* 108:118 */     for (DetalleDespachoCliente detalleDespachoCliente : listaDetalleDespachoCliente)
/* 109:    */     {
/* 110:119 */       if (detalleDespachoCliente.getInventarioProducto() != null)
/* 111:    */       {
/* 112:120 */         detalleDespachoCliente.getInventarioProducto().getId();
/* 113:121 */         if (detalleDespachoCliente.getInventarioProducto().getLote() != null) {
/* 114:122 */           detalleDespachoCliente.getInventarioProducto().getLote().getId();
/* 115:    */         }
/* 116:125 */         if (detalleDespachoCliente.getProducto().getIndicadorSerie().booleanValue())
/* 117:    */         {
/* 118:127 */           this.em.detach(detalleDespachoCliente.getInventarioProducto());
/* 119:128 */           this.serieInventarioProductoDao.cargarDetalle(detalleDespachoCliente.getInventarioProducto());
/* 120:    */         }
/* 121:    */       }
/* 122:131 */       if (detalleDespachoCliente.getProducto().getCategoriaImpuesto() != null) {
/* 123:132 */         detalleDespachoCliente.getProducto().getCategoriaImpuesto().getId();
/* 124:    */       }
/* 125:134 */       if (detalleDespachoCliente.getDetalleFacturaCliente() != null)
/* 126:    */       {
/* 127:135 */         despachoCliente.setFacturaCliente(detalleDespachoCliente.getDetalleFacturaCliente().getFacturaCliente());
/* 128:136 */         despachoCliente.getFacturaCliente().getId();
/* 129:137 */         if (despachoCliente.getFacturaCliente().getFacturaClienteSRI() != null) {
/* 130:138 */           despachoCliente.getFacturaCliente().getFacturaClienteSRI().getId();
/* 131:    */         }
/* 132:    */       }
/* 133:    */     }
/* 134:143 */     return despachoCliente;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<DetalleDespachoCliente> cargarDetalleDespacho(int idDespachoCliente)
/* 138:    */   {
/* 139:156 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 140:157 */     CriteriaQuery<DetalleDespachoCliente> cqDetalle = criteriaBuilder.createQuery(DetalleDespachoCliente.class);
/* 141:158 */     Root<DetalleDespachoCliente> from = cqDetalle.from(DetalleDespachoCliente.class);
/* 142:    */     
/* 143:160 */     from.fetch("detallePedidoCliente", JoinType.LEFT);
/* 144:161 */     from.fetch("detalleFacturaCliente", JoinType.LEFT);
/* 145:    */     
/* 146:163 */     Path<Integer> pathIdDetalle = from.join("despachoCliente").get("idDespachoCliente");
/* 147:164 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idDespachoCliente)));
/* 148:165 */     CriteriaQuery<DetalleDespachoCliente> select = cqDetalle.select(from);
/* 149:    */     
/* 150:167 */     return this.em.createQuery(select).getResultList();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<DespachoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 154:    */   {
/* 155:178 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 156:179 */     CriteriaQuery<DespachoCliente> criteriaQuery = criteriaBuilder.createQuery(DespachoCliente.class);
/* 157:180 */     Root<DespachoCliente> from = criteriaQuery.from(DespachoCliente.class);
/* 158:    */     
/* 159:182 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/* 160:183 */     from.fetch("direccionEmpresa", JoinType.LEFT);
/* 161:184 */     from.fetch("pedidoCliente", JoinType.LEFT);
/* 162:185 */     from.fetch("prefacturaCliente", JoinType.LEFT);
/* 163:186 */     from.fetch("guiaRemision", JoinType.LEFT);
/* 164:187 */     from.fetch("proyecto", JoinType.LEFT);
/* 165:    */     
/* 166:    */ 
/* 167:190 */     empresa.fetch("cliente", JoinType.LEFT);
/* 168:191 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 169:192 */     empresa.fetch("empleado", JoinType.LEFT);
/* 170:    */     
/* 171:194 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/* 172:195 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/* 173:    */     
/* 174:197 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 175:198 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 176:    */     
/* 177:200 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 178:    */     
/* 179:202 */     CriteriaQuery<DespachoCliente> select = criteriaQuery.select(from);
/* 180:    */     
/* 181:204 */     TypedQuery<DespachoCliente> typedQuery = this.em.createQuery(select);
/* 182:205 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 183:    */     
/* 184:207 */     return typedQuery.getResultList();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<DespachoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 188:    */   {
/* 189:226 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 190:227 */     CriteriaQuery<DespachoCliente> criteriaQuery = criteriaBuilder.createQuery(DespachoCliente.class);
/* 191:228 */     Root<DespachoCliente> from = criteriaQuery.from(DespachoCliente.class);
/* 192:229 */     from.fetch("empresa", JoinType.LEFT);
/* 193:230 */     from.fetch("pedidoCliente", JoinType.LEFT);
/* 194:231 */     from.fetch("guiaRemision", JoinType.LEFT);
/* 195:    */     
/* 196:233 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 197:    */     
/* 198:235 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 199:    */     
/* 200:237 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 201:    */     
/* 202:239 */     CriteriaQuery<DespachoCliente> select = criteriaQuery.select(from);
/* 203:240 */     TypedQuery<DespachoCliente> typedQuery = this.em.createQuery(select);
/* 204:    */     
/* 205:    */ 
/* 206:243 */     return typedQuery.getResultList();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<DespachoCliente> listaDespachosPorFacturar(Estado estadoAprobado, int idEmpresa)
/* 210:    */   {
/* 211:248 */     Query query = this.em.createQuery("SELECT new DespachoCliente(f.idDespachoCliente,f.numero,f.fecha)  FROM DespachoCliente f   WHERE f.estado>=:estadoAprobado  AND f.estado!=:estado  AND f.empresa.idEmpresa=:idEmpresa AND EXISTS (SELECT 1 FROM DetalleDespachoCliente dfc WHERE dfc.despachoCliente.idDespachoCliente = f.idDespachoCliente AND dfc.cantidadPorFacturar>0) ORDER BY f.numero DESC");
/* 212:    */     
/* 213:    */ 
/* 214:    */ 
/* 215:252 */     query.setParameter("estadoAprobado", estadoAprobado);
/* 216:253 */     query.setParameter("estado", Estado.ANULADO);
/* 217:254 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 218:255 */     return query.getResultList();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<DetalleDespachoCliente> getListaDetalleDespachosPorFacturar(int idDespachoCliente)
/* 222:    */   {
/* 223:268 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 224:269 */     CriteriaQuery<DetalleDespachoCliente> criteriaQuery = criteriaBuilder.createQuery(DetalleDespachoCliente.class);
/* 225:270 */     Root<DetalleDespachoCliente> from = criteriaQuery.from(DetalleDespachoCliente.class);
/* 226:    */     
/* 227:272 */     from.fetch("unidadVenta", JoinType.LEFT);
/* 228:273 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/* 229:274 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 230:    */     
/* 231:276 */     CriteriaQuery<DetalleDespachoCliente> select = criteriaQuery.select(from);
/* 232:    */     
/* 233:278 */     List<Expression> predicates = new ArrayList();
/* 234:279 */     predicates.add(criteriaBuilder.equal(from.join("despachoCliente").get("idDespachoCliente"), Integer.valueOf(idDespachoCliente)));
/* 235:280 */     predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorFacturar").as(BigDecimal.class), BigDecimal.ZERO));
/* 236:    */     
/* 237:282 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 238:    */     
/* 239:284 */     return this.em.createQuery(select).getResultList();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void actualizarEstado(Integer idDespachoCliente, Estado estado)
/* 243:    */   {
/* 244:288 */     Query query = this.em.createQuery("UPDATE DespachoCliente dc SET dc.estado=:estado WHERE dc.idDespachoCliente=:idDespachoCliente");
/* 245:289 */     query.setParameter("idDespachoCliente", idDespachoCliente);
/* 246:290 */     query.setParameter("estado", estado);
/* 247:291 */     query.executeUpdate();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public DespachoCliente cargarDetalleAFacturar(Integer idDespachoCliente)
/* 251:    */   {
/* 252:300 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 253:    */     
/* 254:    */ 
/* 255:303 */     CriteriaQuery<DespachoCliente> cqCabecera = criteriaBuilder.createQuery(DespachoCliente.class);
/* 256:304 */     Root<DespachoCliente> fromCabecera = cqCabecera.from(DespachoCliente.class);
/* 257:    */     
/* 258:306 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/* 259:307 */     fromCabecera.fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 260:308 */     fromCabecera.fetch("transportista", JoinType.LEFT);
/* 261:    */     
/* 262:310 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/* 263:311 */     direccion.fetch("ubicacion", JoinType.LEFT);
/* 264:    */     
/* 265:313 */     Fetch<Object, Object> pedido = fromCabecera.fetch("pedidoCliente", JoinType.LEFT);
/* 266:314 */     pedido.fetch("zona", JoinType.LEFT);
/* 267:315 */     pedido.fetch("canal", JoinType.LEFT);
/* 268:316 */     pedido.fetch("condicionPago", JoinType.LEFT);
/* 269:317 */     pedido.fetch("agenteComercial", JoinType.LEFT);
/* 270:    */     
/* 271:319 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/* 272:320 */     empresa.fetch("tipoIdentificacion", JoinType.LEFT);
/* 273:321 */     empresa.fetch("categoriaEmpresa", JoinType.LEFT);
/* 274:    */     
/* 275:323 */     Fetch<Object, Object> cliente = empresa.fetch("cliente", JoinType.LEFT);
/* 276:324 */     cliente.fetch("listaPrecios", JoinType.LEFT);
/* 277:325 */     cliente.fetch("listaDescuentos", JoinType.LEFT);
/* 278:326 */     cliente.fetch("condicionPago", JoinType.INNER);
/* 279:327 */     cliente.fetch("agenteComercial", JoinType.LEFT);
/* 280:    */     
/* 281:    */ 
/* 282:330 */     fromCabecera.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT).fetch("categoriaEmpresa", JoinType.LEFT);
/* 283:    */     
/* 284:332 */     Path<Integer> pathId = fromCabecera.get("idDespachoCliente");
/* 285:333 */     cqCabecera.where(criteriaBuilder.equal(pathId, idDespachoCliente));
/* 286:334 */     CriteriaQuery<DespachoCliente> despacho = cqCabecera.select(fromCabecera);
/* 287:    */     
/* 288:336 */     DespachoCliente despachoCliente = (DespachoCliente)this.em.createQuery(despacho).getSingleResult();
/* 289:    */     
/* 290:    */ 
/* 291:339 */     CriteriaQuery<DetalleDespachoCliente> cqDetalle = criteriaBuilder.createQuery(DetalleDespachoCliente.class);
/* 292:340 */     Root<DetalleDespachoCliente> fromDetalle = cqDetalle.from(DetalleDespachoCliente.class);
/* 293:    */     
/* 294:342 */     fromDetalle.fetch("unidadVenta", JoinType.LEFT);
/* 295:343 */     fromDetalle.fetch("bodega", JoinType.LEFT);
/* 296:    */     
/* 297:345 */     Fetch<Object, Object> detalleFacturaCliente = fromDetalle.fetch("detalleFacturaCliente", JoinType.LEFT);
/* 298:346 */     Fetch<Object, Object> inventarioProducto = fromDetalle.fetch("inventarioProducto", JoinType.LEFT);
/* 299:347 */     inventarioProducto.fetch("lote", JoinType.LEFT);
/* 300:348 */     detalleFacturaCliente.fetch("facturaCliente", JoinType.LEFT);
/* 301:349 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/* 302:350 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 303:351 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/* 304:352 */     producto.fetch("unidad", JoinType.LEFT);
/* 305:353 */     producto.fetch("unidadVenta", JoinType.LEFT);
/* 306:354 */     producto.fetch("unidadCompra", JoinType.LEFT);
/* 307:355 */     producto.fetch("presentacionProducto", JoinType.LEFT);
/* 308:    */     
/* 309:357 */     Fetch<Object, Object> detallePedidoCliente = fromDetalle.fetch("detallePedidoCliente", JoinType.LEFT);
/* 310:358 */     detallePedidoCliente.fetch("pedidoCliente", JoinType.LEFT);
/* 311:    */     
/* 312:360 */     Fetch<Object, Object> cabecera = fromDetalle.fetch("despachoCliente", JoinType.LEFT);
/* 313:361 */     cabecera.fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 314:    */     
/* 315:363 */     Path<Integer> pathIdDetalle = fromDetalle.join("despachoCliente").get("idDespachoCliente");
/* 316:364 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, idDespachoCliente));
/* 317:365 */     CriteriaQuery<DetalleDespachoCliente> selectDetalleDespacho = cqDetalle.select(fromDetalle);
/* 318:    */     
/* 319:367 */     List<DetalleDespachoCliente> listaDetalleDespachoCliente = this.em.createQuery(selectDetalleDespacho).getResultList();
/* 320:368 */     despachoCliente.setListaDetalleDespachoCliente(listaDetalleDespachoCliente);
/* 321:    */     
/* 322:370 */     return despachoCliente;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void actualizarCantidadPorFacturar(List<Integer> listaIdDetalleDespachoCliente)
/* 326:    */   {
/* 327:379 */     String sql = "UPDATE DetalleDespachoCliente drp SET drp.cantidadPorFacturar = drp.cantidad -  \t\tCOALESCE( (SELECT SUM(dfp1.cantidad) FROM DetalleFacturaCliente dfp1  \t\tWHERE dfp1.detalleDespachoCliente.idDetalleDespachoCliente=drp.idDetalleDespachoCliente\t\t\tAND dfp1.facturaCliente.estado!=:estadoAnulado),0 ) WHERE drp.idDetalleDespachoCliente in (:listaIdDetalleDespachoCliente)";
/* 328:    */     
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:385 */     Query query = this.em.createQuery(sql);
/* 334:386 */     query.setParameter("listaIdDetalleDespachoCliente", listaIdDetalleDespachoCliente);
/* 335:387 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 336:388 */     query.executeUpdate();
/* 337:    */   }
/* 338:    */   
/* 339:    */   public DespachoCliente buscarPorFacturaCliente(Integer idFacturaCliente)
/* 340:    */   {
/* 341:399 */     Query query = this.em.createQuery("SELECT dc FROM FacturaCliente fc INNER JOIN fc.despachoCliente dc  WHERE fc.idFacturaCliente = :idFacturaCliente AND dc.estado != :estadoAnulado");
/* 342:    */     
/* 343:401 */     query.setParameter("idFacturaCliente", idFacturaCliente);
/* 344:402 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 345:403 */     query.setMaxResults(1);
/* 346:    */     try
/* 347:    */     {
/* 348:406 */       return (DespachoCliente)query.getSingleResult();
/* 349:    */     }
/* 350:    */     catch (NoResultException e) {}
/* 351:408 */     return null;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<DespachoCliente> getListaDespachoCliente(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 355:    */   {
/* 356:422 */     Query query = this.em.createQuery("SELECT dc FROM DespachoCliente dc \tINNER JOIN dc.documento do \tWHERE dc.fecha BETWEEN :fechaDesde and :fechaHasta\tAND dc.estado<>:estadoAnulado  AND dc.estado<>:estadoContabilizado AND dc.idOrganizacion = :idOrganizacion");
/* 357:    */     
/* 358:    */ 
/* 359:    */ 
/* 360:426 */     query.setParameter("fechaDesde", fechaDesde);
/* 361:427 */     query.setParameter("fechaHasta", fechaHasta);
/* 362:428 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 363:429 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 364:430 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 365:    */     
/* 366:432 */     return query.getResultList();
/* 367:    */   }
/* 368:    */   
/* 369:    */   public List<DetalleInterfazContableProceso> getInterfazDespachosDimensiones(List<Integer> listaDespachoCliente, ProcesoContabilizacionEnum procesoContabilizacionEnum)
/* 370:    */     throws ExcepcionAS2Financiero
/* 371:    */   {
/* 372:448 */     String valores = "";
/* 373:449 */     valores = "ROUND(SUM(ip.costo),2)";
/* 374:450 */     String descripcion = "";
/* 375:451 */     String grupoDescripcion = "";
/* 376:452 */     if (listaDespachoCliente.size() == 1)
/* 377:    */     {
/* 378:453 */       descripcion = "CONCAT(dc.numero,coalesce(CONCAT('/',fc.numero),''))";
/* 379:454 */       grupoDescripcion = descripcion;
/* 380:    */     }
/* 381:    */     else
/* 382:    */     {
/* 383:456 */       descripcion = "CONCAT(dc.numero,coalesce(CONCAT('/',fc.numero),''))";
/* 384:457 */       grupoDescripcion = descripcion;
/* 385:    */     }
/* 386:460 */     StringBuilder sql = new StringBuilder();
/* 387:    */     
/* 388:    */ 
/* 389:463 */     sql.append("SELECT new DetalleInterfazContableProcesoDespachos(d.idDocumento, d.nombre, s.idSucursal, s.nombre,");
/* 390:464 */     sql.append(" ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, ");
/* 391:465 */     sql.append(" sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, b.idBodega, b.nombre, ");
/* 392:466 */     sql.append(" se.idSubempresa, se.codigo, " + descripcion + "," + valores + "," + grupoDescripcion + " )");
/* 393:467 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/* 394:468 */     sql.append(" LEFT OUTER JOIN ddc.detalleFacturaCliente dfc ");
/* 395:469 */     sql.append(" LEFT OUTER JOIN dfc.facturaCliente fc ");
/* 396:470 */     sql.append(" INNER JOIN ddc.despachoCliente dc");
/* 397:471 */     sql.append(" INNER JOIN ddc.inventarioProducto ip");
/* 398:472 */     sql.append(" INNER JOIN ip.bodega b");
/* 399:473 */     sql.append(" INNER JOIN ddc.producto p ");
/* 400:474 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 401:475 */     sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 402:476 */     sql.append(" INNER JOIN dc.documento d ");
/* 403:477 */     sql.append(" INNER JOIN dc.empresa e ");
/* 404:478 */     sql.append(" INNER JOIN dc.sucursal s ");
/* 405:479 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 406:480 */     sql.append(" LEFT JOIN dc.subempresa se");
/* 407:481 */     sql.append(" WHERE dc.idDespachoCliente in (:listaDespachoCliente) ");
/* 408:482 */     sql.append(" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre,");
/* 409:483 */     sql.append(" ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa, e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, ");
/* 410:484 */     sql.append(" sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, b.idBodega, b.nombre, ");
/* 411:485 */     sql.append(" se.idSubempresa, se.codigo");
/* 412:486 */     if (listaDespachoCliente.size() >= 1) {
/* 413:487 */       sql.append(" ," + grupoDescripcion);
/* 414:    */     }
/* 415:489 */     sql.append(" HAVING " + valores + " <> 0");
/* 416:    */     
/* 417:491 */     Query query = this.em.createQuery(sql.toString());
/* 418:492 */     query.setParameter("listaDespachoCliente", listaDespachoCliente);
/* 419:493 */     return query.getResultList();
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void actualizarEstadoDespachosInterfazContable(InterfazContableProceso interfazContableProceso)
/* 423:    */   {
/* 424:513 */     String sql = "UPDATE DespachoCliente dc  SET dc.estado=:estado, dc.interfazContableProceso = NULL\tWHERE dc.interfazContableProceso = :interfazContableProceso";
/* 425:    */     
/* 426:    */ 
/* 427:516 */     Query query = this.em.createQuery(sql);
/* 428:517 */     query.setParameter("estado", Estado.PROCESADO);
/* 429:518 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 430:519 */     query.executeUpdate();
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void actualizarEstadoContabilizadoDespachosInterfazContable(InterfazContableProceso interfazContableProceso, List<Integer> listaDespachoInterfazContable)
/* 434:    */   {
/* 435:532 */     String sql = "UPDATE DespachoCliente dc  SET dc.estado=:estado, dc.interfazContableProceso = :interfazContableProceso, fechaContabilizacion = :fechaContabilizacion\tWHERE dc.idDespachoCliente in (:listaDespachoInterfazContable)";
/* 436:    */     
/* 437:    */ 
/* 438:    */ 
/* 439:536 */     Query query = this.em.createQuery(sql);
/* 440:537 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 441:538 */     query.setParameter("interfazContableProceso", interfazContableProceso);
/* 442:539 */     query.setParameter("fechaContabilizacion", interfazContableProceso.getFechaHasta());
/* 443:540 */     query.setParameter("listaDespachoInterfazContable", listaDespachoInterfazContable);
/* 444:541 */     query.executeUpdate();
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void actualizarFechaAnulacionDespachoCliente(DespachoCliente despachoCliente, Date fechaAnulacion)
/* 448:    */   {
/* 449:551 */     StringBuilder sql = new StringBuilder();
/* 450:552 */     sql.append("UPDATE DespachoCliente dc ");
/* 451:553 */     sql.append(" SET dc.fechaAnulacion = :fechaAnulacion ");
/* 452:554 */     sql.append(" WHERE dc = :despachoCliente ");
/* 453:    */     
/* 454:556 */     Query query = this.em.createQuery(sql.toString());
/* 455:557 */     query.setParameter("fechaAnulacion", fechaAnulacion);
/* 456:558 */     query.setParameter("despachoCliente", despachoCliente);
/* 457:    */     
/* 458:560 */     query.executeUpdate();
/* 459:    */   }
/* 460:    */   
/* 461:    */   public List<DespachoCliente> buscarDespachosNoFacturadosPorCliente(Integer idEmpresa)
/* 462:    */   {
/* 463:565 */     return buscarDespachosNoFacturadosPorCliente(idEmpresa, null, 0);
/* 464:    */   }
/* 465:    */   
/* 466:    */   public List<DespachoCliente> buscarDespachosNoFacturadosPorCliente(Integer idEmpresa, String consulta, int idSubempresa)
/* 467:    */   {
/* 468:570 */     StringBuilder sqll = new StringBuilder();
/* 469:571 */     sqll.append(" SELECT dc FROM DespachoCliente dc ");
/* 470:572 */     sqll.append(" LEFT JOIN FETCH dc.ordenDespachoCliente odc ");
/* 471:573 */     sqll.append(" LEFT JOIN FETCH odc.tipoPresentacionProducto tp");
/* 472:574 */     if (idSubempresa != 0) {
/* 473:575 */       sqll.append(" LEFT JOIN FETCH dc.subempresa se");
/* 474:    */     }
/* 475:576 */     sqll.append(" WHERE dc.empresa.idEmpresa = :idEmpresa ");
/* 476:577 */     sqll.append(" AND dc.estado != :estadoAnulado ");
/* 477:578 */     sqll.append(" AND ( dc.indicadorGeneradoFactura = false ) ");
/* 478:579 */     if (consulta != null) {
/* 479:580 */       sqll.append(" AND dc.numero LIKE :consulta");
/* 480:    */     }
/* 481:581 */     if (idSubempresa != 0) {
/* 482:582 */       sqll.append(" AND se.idSubempresa =:idSubempresa");
/* 483:    */     }
/* 484:584 */     Query query = this.em.createQuery(sqll.toString());
/* 485:585 */     query.setParameter("idEmpresa", idEmpresa);
/* 486:586 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 487:587 */     if (consulta != null) {
/* 488:588 */       query.setParameter("consulta", "%" + consulta + "%");
/* 489:    */     }
/* 490:589 */     if (idSubempresa != 0) {
/* 491:590 */       query.setParameter("idSubempresa", Integer.valueOf(idSubempresa));
/* 492:    */     }
/* 493:592 */     return query.getResultList();
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void eliminarPedido(Integer idDespachoCliente)
/* 497:    */   {
/* 498:596 */     Query query = this.em.createQuery("UPDATE DespachoCliente dc SET dc.pedidoCliente=null WHERE dc.idDespachoCliente=:idDespachoCliente");
/* 499:597 */     query.setParameter("idDespachoCliente", idDespachoCliente);
/* 500:598 */     query.executeUpdate();
/* 501:    */   }
/* 502:    */   
/* 503:    */   public HashMap<Integer, BigDecimal> obtenerTotalDespachadoPorProductoPorFecha(Producto producto, Date fechaInicio, Date fechaFin, List<Bodega> listaBodegaTrabajo)
/* 504:    */   {
/* 505:603 */     HashMap<Integer, BigDecimal> mpDespahos = new HashMap();
/* 506:    */     
/* 507:    */ 
/* 508:606 */     StringBuilder sql = new StringBuilder();
/* 509:607 */     sql.append(" SELECT p.idProducto, COALESCE(SUM(ddc.cantidad),0) ");
/* 510:608 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/* 511:609 */     sql.append(" INNER JOIN ddc.bodega b ");
/* 512:610 */     sql.append(" INNER JOIN ddc.producto p ");
/* 513:611 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/* 514:612 */     sql.append(" WHERE (dc.estado = :estadoProcesado OR dc.estado = :estadoContabilizado)");
/* 515:613 */     sql.append(" AND dc.fecha >= :fechaInicio ");
/* 516:614 */     sql.append(" AND dc.fecha <= :fechaFin ");
/* 517:615 */     if (producto != null) {
/* 518:616 */       sql.append(" AND p.idProducto = :idProducto ");
/* 519:    */     }
/* 520:618 */     if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0)) {
/* 521:619 */       sql.append(" AND b IN (:listaBodegaTrabajo)");
/* 522:    */     }
/* 523:621 */     sql.append(" GROUP BY p.idProducto");
/* 524:    */     
/* 525:623 */     Query query = this.em.createQuery(sql.toString());
/* 526:625 */     if (producto != null) {
/* 527:626 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 528:    */     }
/* 529:628 */     query.setParameter("fechaInicio", fechaInicio);
/* 530:629 */     query.setParameter("fechaFin", fechaFin);
/* 531:630 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 532:631 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 533:633 */     if ((listaBodegaTrabajo != null) && (listaBodegaTrabajo.size() > 0)) {
/* 534:634 */       query.setParameter("listaBodegaTrabajo", listaBodegaTrabajo);
/* 535:    */     }
/* 536:637 */     List<Object[]> listaDespachos = query.getResultList();
/* 537:638 */     for (Object[] objects : listaDespachos) {
/* 538:639 */       mpDespahos.put((Integer)objects[0], (BigDecimal)objects[1]);
/* 539:    */     }
/* 540:641 */     return mpDespahos;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public List<DespachoCliente> obtenerDespachosPorFecha(Date fechaDespacho, int idOrganizacion, Empresa empresa)
/* 544:    */   {
/* 545:647 */     StringBuilder sql = new StringBuilder();
/* 546:648 */     sql.append(" SELECT dc ");
/* 547:649 */     sql.append(" FROM DespachoCliente dc ");
/* 548:650 */     sql.append(" INNER JOIN dc.empresa em ");
/* 549:651 */     sql.append(" LEFT JOIN FETCH dc.ordenDespachoCliente odc");
/* 550:652 */     sql.append(" WHERE dc.fecha = :fechaDespacho ");
/* 551:653 */     sql.append(" AND dc.idOrganizacion = :idOrganizacion ");
/* 552:654 */     sql.append(" AND em = :empresa ");
/* 553:655 */     sql.append(" AND dc.indicadorGeneradoFactura = false ");
/* 554:656 */     sql.append(" AND dc.estado != :anulados  ");
/* 555:    */     
/* 556:658 */     Query query = this.em.createQuery(sql.toString());
/* 557:659 */     query.setParameter("fechaDespacho", fechaDespacho);
/* 558:660 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 559:661 */     query.setParameter("empresa", empresa);
/* 560:662 */     query.setParameter("anulados", Estado.ANULADO);
/* 561:    */     
/* 562:664 */     return query.getResultList();
/* 563:    */   }
/* 564:    */   
/* 565:    */   public List<DetalleDespachoCliente> cargarDetalleDespachoClientePorDespacho(DespachoCliente despachoCliente)
/* 566:    */   {
/* 567:670 */     StringBuilder sql = new StringBuilder();
/* 568:671 */     sql.append(" SELECT ddc ");
/* 569:672 */     sql.append(" FROM DetalleDespachoCliente ddc ");
/* 570:673 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/* 571:674 */     sql.append(" INNER JOIN FETCH ddc.producto p ");
/* 572:675 */     sql.append(" INNER JOIN ddc.inventarioProducto ip");
/* 573:676 */     sql.append(" LEFT JOIN ip.lote l ");
/* 574:677 */     sql.append(" WHERE dc.idDespachoCliente = :idDespachoCliente");
/* 575:    */     
/* 576:679 */     Query query = this.em.createQuery(sql.toString());
/* 577:680 */     query.setParameter("idDespachoCliente", Integer.valueOf(despachoCliente.getId()));
/* 578:    */     
/* 579:682 */     return query.getResultList();
/* 580:    */   }
/* 581:    */   
/* 582:    */   public boolean existeDevolucion(DespachoCliente despachoCliente)
/* 583:    */   {
/* 584:687 */     StringBuilder sql = new StringBuilder();
/* 585:688 */     sql.append(" SELECT COUNT(*) FROM DetalleDespachoCliente ddc ");
/* 586:689 */     sql.append(" INNER JOIN ddc.despachoCliente dc ");
/* 587:690 */     sql.append(" WHERE  dc = :despachoCliente ");
/* 588:691 */     sql.append(" AND ddc.cantidadDevuelta > 0 ");
/* 589:    */     
/* 590:693 */     Query query = this.em.createQuery(sql.toString());
/* 591:694 */     query.setParameter("despachoCliente", despachoCliente);
/* 592:    */     
/* 593:696 */     Long cont = (Long)query.getSingleResult();
/* 594:698 */     if (cont.compareTo(Long.valueOf(0L)) > 0) {
/* 595:699 */       return true;
/* 596:    */     }
/* 597:701 */     return false;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public DespachoCliente buscarPorId(Object id)
/* 601:    */   {
/* 602:707 */     DespachoCliente despachoCliente = (DespachoCliente)super.buscarPorId(id);
/* 603:708 */     despachoCliente.getDocumento();
/* 604:709 */     return despachoCliente;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public List<DespachoCliente> obtenerDespachos(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 608:    */   {
/* 609:715 */     StringBuilder sql = new StringBuilder();
/* 610:716 */     sql.append(" SELECT dc ");
/* 611:717 */     sql.append(" FROM DespachoCliente dc ");
/* 612:718 */     sql.append(" LEFT JOIN FETCH dc.asiento a ");
/* 613:719 */     sql.append(" LEFT JOIN FETCH dc.documento do ");
/* 614:720 */     sql.append(" WHERE dc.fecha BETWEEN :fechaDesde and :fechaHasta");
/* 615:    */     
/* 616:722 */     sql.append(" AND dc.idOrganizacion = :idOrganizacion ");
/* 617:723 */     sql.append(" AND dc.estado != :anulados ");
/* 618:724 */     sql.append(" AND a IS NOT NULL ");
/* 619:    */     
/* 620:726 */     Query query = this.em.createQuery(sql.toString());
/* 621:727 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 622:728 */     query.setParameter("fechaDesde", fechaDesde);
/* 623:729 */     query.setParameter("fechaHasta", fechaHasta);
/* 624:730 */     query.setParameter("anulados", Estado.ANULADO);
/* 625:    */     
/* 626:732 */     List<DespachoCliente> lista = new ArrayList();
/* 627:733 */     lista = query.getResultList();
/* 628:735 */     for (DespachoCliente despachoCliente : lista) {
/* 629:736 */       if (despachoCliente.getAsiento() != null)
/* 630:    */       {
/* 631:737 */         despachoCliente.getAsiento().getListaDetalleAsiento().size();
/* 632:738 */         for (DetalleAsiento da : despachoCliente.getAsiento().getListaDetalleAsiento()) {
/* 633:739 */           da.getCuentaContable().getId();
/* 634:    */         }
/* 635:    */       }
/* 636:    */     }
/* 637:744 */     return lista;
/* 638:    */   }
/* 639:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DespachoClienteDao
 * JD-Core Version:    0.7.0.1
 */