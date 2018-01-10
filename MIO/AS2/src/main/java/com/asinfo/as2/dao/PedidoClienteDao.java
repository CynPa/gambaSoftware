/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Contacto;
/*   4:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.ImpuestoProductoPedidoCliente;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  11:    */ import com.asinfo.as2.entities.Ruta;
/*  12:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*  13:    */ import com.asinfo.as2.entities.TipoPresentacionProducto;
/*  14:    */ import com.asinfo.as2.entities.Transportista;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import javax.persistence.EntityManager;
/*  25:    */ import javax.persistence.NoResultException;
/*  26:    */ import javax.persistence.Query;
/*  27:    */ import javax.persistence.TemporalType;
/*  28:    */ import javax.persistence.TypedQuery;
/*  29:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  30:    */ import javax.persistence.criteria.CriteriaQuery;
/*  31:    */ import javax.persistence.criteria.Expression;
/*  32:    */ import javax.persistence.criteria.Fetch;
/*  33:    */ import javax.persistence.criteria.Join;
/*  34:    */ import javax.persistence.criteria.JoinType;
/*  35:    */ import javax.persistence.criteria.Path;
/*  36:    */ import javax.persistence.criteria.Predicate;
/*  37:    */ import javax.persistence.criteria.Root;
/*  38:    */ 
/*  39:    */ @Stateless
/*  40:    */ public class PedidoClienteDao
/*  41:    */   extends AbstractDaoAS2<PedidoCliente>
/*  42:    */ {
/*  43:    */   public PedidoClienteDao()
/*  44:    */   {
/*  45: 57 */     super(PedidoCliente.class);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public PedidoCliente cargarDetallePedidoADespachar(int idPedidoCliente)
/*  49:    */   {
/*  50: 68 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  51:    */     
/*  52:    */ 
/*  53: 71 */     CriteriaQuery<PedidoCliente> cqCabecera = criteriaBuilder.createQuery(PedidoCliente.class);
/*  54: 72 */     Root<PedidoCliente> fromCabecera = cqCabecera.from(PedidoCliente.class);
/*  55:    */     
/*  56: 74 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  57: 75 */     documento.fetch("secuencia", JoinType.LEFT);
/*  58: 76 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/*  59: 77 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  60: 78 */     fromCabecera.fetch("empresa", JoinType.LEFT);
/*  61: 79 */     fromCabecera.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  62: 80 */     fromCabecera.fetch("agenteComercial", JoinType.LEFT);
/*  63: 81 */     fromCabecera.fetch("canal", JoinType.LEFT);
/*  64: 82 */     fromCabecera.fetch("zona", JoinType.LEFT);
/*  65: 83 */     fromCabecera.fetch("motivoPedidoCliente", JoinType.LEFT);
/*  66:    */     
/*  67: 85 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/*  68: 86 */     direccion.fetch("ubicacion", JoinType.LEFT);
/*  69:    */     
/*  70: 88 */     Path<Integer> pathId = fromCabecera.get("idPedidoCliente");
/*  71: 89 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPedidoCliente)));
/*  72: 90 */     CriteriaQuery<PedidoCliente> selectPedido = cqCabecera.select(fromCabecera);
/*  73:    */     
/*  74: 92 */     PedidoCliente pedidoCliente = (PedidoCliente)this.em.createQuery(selectPedido).getSingleResult();
/*  75:    */     
/*  76:    */ 
/*  77: 95 */     CriteriaQuery<DetallePedidoCliente> cqDetalle = criteriaBuilder.createQuery(DetallePedidoCliente.class);
/*  78: 96 */     Root<DetallePedidoCliente> fromDetalle = cqDetalle.from(DetallePedidoCliente.class);
/*  79:    */     
/*  80: 98 */     fromDetalle.fetch("unidadVenta", JoinType.LEFT);
/*  81:    */     
/*  82:100 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  83:101 */     producto.fetch("unidad", JoinType.LEFT);
/*  84:    */     
/*  85:103 */     Path<Integer> pathIdDetalle = fromDetalle.join("pedidoCliente").get("idPedidoCliente");
/*  86:104 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idPedidoCliente)));
/*  87:105 */     CriteriaQuery<DetallePedidoCliente> selectDetalleDespacho = cqDetalle.select(fromDetalle);
/*  88:    */     
/*  89:107 */     List<DetallePedidoCliente> listaDetallePedidoCliente = this.em.createQuery(selectDetalleDespacho).getResultList();
/*  90:108 */     pedidoCliente.setListaDetallePedidoCliente(listaDetallePedidoCliente);
/*  91:111 */     for (DetallePedidoCliente detallePedidoCliente : listaDetallePedidoCliente)
/*  92:    */     {
/*  93:112 */       Integer idDetallePedidoCliente = Integer.valueOf(detallePedidoCliente.getId());
/*  94:    */       
/*  95:114 */       CriteriaQuery<ImpuestoProductoPedidoCliente> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoPedidoCliente.class);
/*  96:115 */       Root<ImpuestoProductoPedidoCliente> fromImpuesto = cqImpuesto.from(ImpuestoProductoPedidoCliente.class);
/*  97:    */       
/*  98:117 */       fromImpuesto.fetch("impuesto", JoinType.LEFT);
/*  99:    */       
/* 100:119 */       Path<Integer> pathIdImpuesto = fromImpuesto.join("detallePedidoCliente").get("idDetallePedidoCliente");
/* 101:120 */       cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, idDetallePedidoCliente));
/* 102:    */       
/* 103:122 */       CriteriaQuery<ImpuestoProductoPedidoCliente> selectImpuesto = cqImpuesto.select(fromImpuesto);
/* 104:    */       
/* 105:124 */       List<ImpuestoProductoPedidoCliente> listaImpuestoProductoPedidoCliente = this.em.createQuery(selectImpuesto).getResultList();
/* 106:    */       
/* 107:126 */       detallePedidoCliente.setListaImpuestoProductoPedidoCliente(listaImpuestoProductoPedidoCliente);
/* 108:    */     }
/* 109:129 */     return pedidoCliente;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public PedidoCliente cargarDetalle(int idPedidoCliente)
/* 113:    */   {
/* 114:133 */     return cargarDetalle(idPedidoCliente, true);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public PedidoCliente cargarDetalle(int idPedidoCliente, boolean cargarDetalle)
/* 118:    */   {
/* 119:144 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 120:    */     
/* 121:    */ 
/* 122:147 */     CriteriaQuery<PedidoCliente> cqCabecera = criteriaBuilder.createQuery(PedidoCliente.class);
/* 123:148 */     Root<PedidoCliente> fromCabecera = cqCabecera.from(PedidoCliente.class);
/* 124:    */     
/* 125:150 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/* 126:151 */     documento.fetch("secuencia", JoinType.LEFT);
/* 127:152 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/* 128:153 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/* 129:154 */     fromCabecera.fetch("bodega", JoinType.LEFT);
/* 130:155 */     fromCabecera.fetch("transportista", JoinType.LEFT);
/* 131:156 */     fromCabecera.fetch("chofer", JoinType.LEFT);
/* 132:157 */     fromCabecera.fetch("ruta", JoinType.LEFT);
/* 133:158 */     Fetch<Object, Object> vehiculo = fromCabecera.fetch("vehiculo", JoinType.LEFT);
/* 134:159 */     vehiculo.fetch("tipoVehiculo", JoinType.LEFT);
/* 135:160 */     fromCabecera.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT).fetch("cliente", JoinType.LEFT).fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 136:    */     
/* 137:162 */     fromCabecera.fetch("contacto", JoinType.LEFT);
/* 138:    */     
/* 139:164 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/* 140:165 */     empresa.fetch("categoriaEmpresa", JoinType.LEFT);
/* 141:166 */     Fetch<Object, Object> cliente = empresa.fetch("cliente", JoinType.LEFT);
/* 142:167 */     cliente.fetch("listaPrecios", JoinType.LEFT);
/* 143:168 */     cliente.fetch("listaDescuentos", JoinType.LEFT);
/* 144:169 */     cliente.fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 145:    */     
/* 146:171 */     fromCabecera.fetch("agenteComercial", JoinType.LEFT);
/* 147:172 */     fromCabecera.fetch("canal", JoinType.LEFT);
/* 148:173 */     fromCabecera.fetch("zona", JoinType.LEFT);
/* 149:174 */     fromCabecera.fetch("motivoPedidoCliente", JoinType.LEFT);
/* 150:175 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/* 151:    */     
/* 152:177 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/* 153:178 */     direccion.fetch("ubicacion", JoinType.LEFT);
/* 154:179 */     direccion.fetch("ciudad", JoinType.LEFT);
/* 155:    */     
/* 156:181 */     Path<Integer> pathId = fromCabecera.get("idPedidoCliente");
/* 157:182 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPedidoCliente)));
/* 158:183 */     CriteriaQuery<PedidoCliente> selectPedido = cqCabecera.select(fromCabecera);
/* 159:    */     
/* 160:185 */     PedidoCliente pedidoCliente = (PedidoCliente)this.em.createQuery(selectPedido).getSingleResult();
/* 161:    */     
/* 162:    */ 
/* 163:188 */     CriteriaQuery<DetallePedidoCliente> cqDetalle = criteriaBuilder.createQuery(DetallePedidoCliente.class);
/* 164:189 */     Root<DetallePedidoCliente> fromDetalle = cqDetalle.from(DetallePedidoCliente.class);
/* 165:190 */     fromDetalle.fetch("unidadVenta", JoinType.LEFT);
/* 166:191 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/* 167:192 */     producto.fetch("unidad", JoinType.LEFT);
/* 168:193 */     producto.fetch("presentacionProducto", JoinType.LEFT);
/* 169:194 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 170:    */     
/* 171:196 */     Path<Integer> pathIdDetalle = fromDetalle.join("pedidoCliente").get("idPedidoCliente");
/* 172:197 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idPedidoCliente)));
/* 173:198 */     CriteriaQuery<DetallePedidoCliente> selectDetalleDespacho = cqDetalle.select(fromDetalle);
/* 174:    */     
/* 175:200 */     List<DetallePedidoCliente> listaDetallePedidoCliente = this.em.createQuery(selectDetalleDespacho).getResultList();
/* 176:201 */     pedidoCliente.setListaDetallePedidoCliente(listaDetallePedidoCliente);
/* 177:204 */     for (DetallePedidoCliente detallePedidoCliente : listaDetallePedidoCliente)
/* 178:    */     {
/* 179:205 */       Integer idDetallePedidoCliente = Integer.valueOf(detallePedidoCliente.getId());
/* 180:    */       
/* 181:207 */       CriteriaQuery<ImpuestoProductoPedidoCliente> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoPedidoCliente.class);
/* 182:208 */       Root<ImpuestoProductoPedidoCliente> fromImpuesto = cqImpuesto.from(ImpuestoProductoPedidoCliente.class);
/* 183:    */       
/* 184:210 */       fromImpuesto.fetch("impuesto", JoinType.LEFT);
/* 185:    */       
/* 186:212 */       Path<Integer> pathIdImpuesto = fromImpuesto.join("detallePedidoCliente").get("idDetallePedidoCliente");
/* 187:213 */       cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, idDetallePedidoCliente));
/* 188:    */       
/* 189:215 */       CriteriaQuery<ImpuestoProductoPedidoCliente> selectImpuesto = cqImpuesto.select(fromImpuesto);
/* 190:    */       
/* 191:217 */       List<ImpuestoProductoPedidoCliente> listaImpuestoProductoPedidoCliente = this.em.createQuery(selectImpuesto).getResultList();
/* 192:    */       
/* 193:219 */       detallePedidoCliente.setListaImpuestoProductoPedidoCliente(listaImpuestoProductoPedidoCliente);
/* 194:    */     }
/* 195:222 */     return pedidoCliente;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<PedidoCliente> listaPedidosPorDespachar(int idEmpresa)
/* 199:    */   {
/* 200:233 */     Query query = this.em.createQuery("SELECT new PedidoCliente(p.idPedidoCliente,p.numero,p.fecha)  FROM PedidoCliente p   WHERE p.estado=:estadoProcesado AND p.empresa.idEmpresa=:idEmpresa AND EXISTS (SELECT 1 FROM DetallePedidoCliente dpc WHERE dpc.pedidoCliente.idPedidoCliente = p.idPedidoCliente AND dpc.cantidadPorDespachar>0) ORDER BY p.numero DESC");
/* 201:    */     
/* 202:    */ 
/* 203:    */ 
/* 204:237 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 205:238 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 206:239 */     return query.getResultList();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<PedidoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 210:    */   {
/* 211:249 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 212:250 */     CriteriaQuery<PedidoCliente> criteriaQuery = criteriaBuilder.createQuery(PedidoCliente.class);
/* 213:251 */     Root<PedidoCliente> from = criteriaQuery.from(PedidoCliente.class);
/* 214:    */     
/* 215:253 */     from.fetch("motivoPedidoCliente", JoinType.LEFT);
/* 216:254 */     from.fetch("documento", JoinType.LEFT);
/* 217:255 */     from.fetch("sucursal", JoinType.LEFT);
/* 218:256 */     from.fetch("proyecto", JoinType.LEFT);
/* 219:257 */     from.fetch("transportista", JoinType.LEFT);
/* 220:    */     
/* 221:    */ 
/* 222:260 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/* 223:261 */     empresa.fetch("cliente");
/* 224:262 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 225:263 */     empresa.fetch("empleado", JoinType.LEFT);
/* 226:    */     
/* 227:    */ 
/* 228:266 */     Fetch<Object, Object> subempresa = from.fetch("subempresa", JoinType.LEFT);
/* 229:267 */     Fetch<Object, Object> empresaSubempresa = subempresa.fetch("empresa", JoinType.LEFT);
/* 230:268 */     empresaSubempresa.fetch("cliente", JoinType.LEFT);
/* 231:269 */     empresaSubempresa.fetch("proveedor", JoinType.LEFT);
/* 232:270 */     empresaSubempresa.fetch("empleado", JoinType.LEFT);
/* 233:    */     
/* 234:272 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 235:273 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 236:    */     
/* 237:275 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 238:    */     
/* 239:277 */     CriteriaQuery<PedidoCliente> select = criteriaQuery.select(from);
/* 240:    */     
/* 241:279 */     TypedQuery<PedidoCliente> typedQuery = this.em.createQuery(select);
/* 242:280 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 243:    */     
/* 244:282 */     return typedQuery.getResultList();
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<PedidoCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 248:    */   {
/* 249:292 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 250:293 */     CriteriaQuery<PedidoCliente> criteriaQuery = criteriaBuilder.createQuery(PedidoCliente.class);
/* 251:294 */     Root<PedidoCliente> from = criteriaQuery.from(PedidoCliente.class);
/* 252:295 */     from.fetch("zona", JoinType.LEFT);
/* 253:296 */     from.fetch("canal", JoinType.LEFT);
/* 254:297 */     from.fetch("condicionPago", JoinType.LEFT);
/* 255:298 */     Fetch<Object, Object> direccionEmpresa = from.fetch("direccionEmpresa", JoinType.LEFT);
/* 256:299 */     direccionEmpresa.fetch("ubicacion", JoinType.LEFT);
/* 257:300 */     direccionEmpresa.fetch("ciudad", JoinType.LEFT);
/* 258:301 */     from.fetch("agenteComercial", JoinType.LEFT);
/* 259:302 */     from.fetch("motivoPedidoCliente", JoinType.LEFT);
/* 260:303 */     from.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/* 261:304 */     from.fetch("proyecto", JoinType.LEFT);
/* 262:305 */     from.fetch("transportista", JoinType.LEFT);
/* 263:    */     
/* 264:307 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 265:308 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 266:    */     
/* 267:310 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 268:    */     
/* 269:312 */     CriteriaQuery<PedidoCliente> select = criteriaQuery.select(from);
/* 270:313 */     TypedQuery<PedidoCliente> typedQuery = this.em.createQuery(select);
/* 271:    */     
/* 272:315 */     return typedQuery.getResultList();
/* 273:    */   }
/* 274:    */   
/* 275:    */   public List<PedidoCliente> listaPedidosPorFacturar(Estado estadoAprobado, int idEmpresa)
/* 276:    */   {
/* 277:327 */     Query query = this.em.createQuery("SELECT new PedidoCliente(p.idPedidoCliente,p.numero,p.fecha)  FROM PedidoCliente p   WHERE p.estado>=:estadoAprobado  AND p.estado!=:estado  AND p.empresa.idEmpresa=:idEmpresa AND EXISTS (SELECT 1 FROM DetallePedidoCliente dpc WHERE dpc.pedidoCliente.idPedidoCliente = p.idPedidoCliente AND dpc.cantidadPorFacturar>0) ORDER BY p.numero DESC");
/* 278:    */     
/* 279:    */ 
/* 280:    */ 
/* 281:331 */     query.setParameter("estadoAprobado", estadoAprobado);
/* 282:332 */     query.setParameter("estado", Estado.ANULADO);
/* 283:333 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 284:334 */     return query.getResultList();
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<DetallePedidoCliente> obtenerListaDetallePedidoPorDespachar(int idPedidoCliente)
/* 288:    */   {
/* 289:346 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 290:347 */     CriteriaQuery<DetallePedidoCliente> criteriaQuery = criteriaBuilder.createQuery(DetallePedidoCliente.class);
/* 291:348 */     Root<DetallePedidoCliente> from = criteriaQuery.from(DetallePedidoCliente.class);
/* 292:    */     
/* 293:350 */     from.fetch("unidadVenta", JoinType.LEFT);
/* 294:    */     
/* 295:352 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/* 296:353 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 297:354 */     producto.fetch("bodegaVenta", JoinType.LEFT);
/* 298:355 */     from.fetch("pedidoCliente", JoinType.LEFT).fetch("bodega", JoinType.LEFT);
/* 299:    */     
/* 300:357 */     CriteriaQuery<DetallePedidoCliente> select = criteriaQuery.select(from);
/* 301:    */     
/* 302:359 */     List<Expression> predicates = new ArrayList();
/* 303:360 */     predicates.add(criteriaBuilder.equal(from.join("pedidoCliente").get("idPedidoCliente"), Integer.valueOf(idPedidoCliente)));
/* 304:361 */     predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorDespachar").as(BigDecimal.class), BigDecimal.ZERO));
/* 305:    */     
/* 306:363 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 307:    */     
/* 308:365 */     return this.em.createQuery(select).getResultList();
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<DetallePedidoCliente> obtenerListaDetallePedidoPorFacturar(int idPedidoCliente)
/* 312:    */   {
/* 313:377 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 314:378 */     CriteriaQuery<DetallePedidoCliente> criteriaQuery = criteriaBuilder.createQuery(DetallePedidoCliente.class);
/* 315:379 */     Root<DetallePedidoCliente> from = criteriaQuery.from(DetallePedidoCliente.class);
/* 316:    */     
/* 317:381 */     from.fetch("unidadVenta", JoinType.LEFT);
/* 318:    */     
/* 319:383 */     Fetch<Object, Object> producto = from.fetch("producto", JoinType.LEFT);
/* 320:384 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 321:385 */     producto.fetch("unidad", JoinType.LEFT);
/* 322:386 */     producto.fetch("unidadVenta", JoinType.LEFT);
/* 323:387 */     producto.fetch("unidadCompra", JoinType.LEFT);
/* 324:388 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/* 325:389 */     from.fetch("pedidoCliente", JoinType.LEFT);
/* 326:    */     
/* 327:391 */     CriteriaQuery<DetallePedidoCliente> select = criteriaQuery.select(from);
/* 328:    */     
/* 329:393 */     List<Expression> predicates = new ArrayList();
/* 330:394 */     predicates.add(criteriaBuilder.equal(from.join("pedidoCliente").get("idPedidoCliente"), Integer.valueOf(idPedidoCliente)));
/* 331:395 */     predicates.add(criteriaBuilder.greaterThan(from.get("cantidadPorFacturar").as(BigDecimal.class), BigDecimal.ZERO));
/* 332:    */     
/* 333:397 */     criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/* 334:    */     
/* 335:399 */     return this.em.createQuery(select).getResultList();
/* 336:    */   }
/* 337:    */   
/* 338:    */   public boolean esPedidoPorDespachar(int idPedidoCliente)
/* 339:    */   {
/* 340:410 */     Query query = this.em.createQuery("SELECT COUNT(*) FROM DetallePedidoCliente dpc INNER JOIN dpc.pedidoCliente pc  WHERE pc.idPedidoCliente=:idPedidoCliente AND (dpc.cantidadPorFacturar>0 AND dpc.cantidadPorDespachar>0)");
/* 341:    */     
/* 342:412 */     query.setParameter("idPedidoCliente", Integer.valueOf(idPedidoCliente));
/* 343:    */     
/* 344:414 */     return ((Long)query.getSingleResult()).longValue() > 0L;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public List getReportePedidoCliente(int idPedidoCliente)
/* 348:    */     throws ExcepcionAS2
/* 349:    */   {
/* 350:425 */     StringBuilder sql = new StringBuilder();
/* 351:426 */     sql.append(" SELECT e.nombreFiscal, CONCAT(u.direccion1,' ', COALESCE(u.direccion2,''),' ',COALESCE(u.direccion3,''),' ',COALESCE(u.direccion4,'')) , e.identificacion, p.fecha,");
/* 352:    */     
/* 353:428 */     sql.append(" d.cantidad, pr.codigo, pr.nombreComercial, d.precio, p.total, p.descuento,");
/* 354:429 */     sql.append(" p.impuesto, c.nombre, p.numeroCuotas, p.descripcion,p.numero,z.codigo,z.nombre,e.nombreComercial,CONCAT(ag.nombre2,' ',ag.nombre1),");
/* 355:    */     
/* 356:431 */     sql.append(" pr.imagen, e.email1, ag.email, cli.contacto, de.telefono1, pr.idProducto, coalesce(t.nombre,''), coalesce(ag.telefono, ''), p.usuarioCreacion, ");
/* 357:    */     
/* 358:433 */     sql.append(" d.descripcion, pro.nombre, p.estado, cont.nombre, cont.telefono1, cont.email1, d.cantidadEmbalajeDespacho, d.cantidadUnidadDespacho, p.indicadorFijo, pr.nombre, ");
/* 359:    */     
/* 360:435 */     sql.append(" p.fechaDespacho, p.referencia8, se.empresaFinal, p.montoIce, ag.email, e.identificacion, p.descuentoImpuesto, uv.codigo ");
/* 361:436 */     sql.append(" FROM DetallePedidoCliente d");
/* 362:437 */     sql.append(" LEFT JOIN d.unidadVenta uv ");
/* 363:438 */     sql.append(" LEFT JOIN d.pedidoCliente p ");
/* 364:439 */     sql.append(" LEFT JOIN p.transportista t ");
/* 365:440 */     sql.append(" LEFT JOIN p.empresa e ");
/* 366:441 */     sql.append(" LEFT JOIN p.subempresa se");
/* 367:442 */     sql.append(" LEFT JOIN e.cliente cli ");
/* 368:443 */     sql.append(" LEFT JOIN p.direccionEmpresa de ");
/* 369:444 */     sql.append(" LEFT JOIN de.ubicacion u");
/* 370:445 */     sql.append(" LEFT JOIN d.producto pr ");
/* 371:446 */     sql.append(" LEFT JOIN p.condicionPago c ");
/* 372:447 */     sql.append(" LEFT JOIN p.zona z ");
/* 373:448 */     sql.append(" LEFT JOIN p.agenteComercial ag ");
/* 374:449 */     sql.append(" LEFT JOIN p.proyecto pro ");
/* 375:450 */     sql.append(" LEFT JOIN p.contacto cont ");
/* 376:451 */     sql.append(" WHERE p.idPedidoCliente = :idPedidoCliente");
/* 377:452 */     sql.append(" ORDER BY d.idDetallePedidoCliente");
/* 378:    */     
/* 379:454 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPedidoCliente", Integer.valueOf(idPedidoCliente));
/* 380:455 */     return query.getResultList();
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void actualizarEstado(PedidoCliente pedidoCliente, Estado estado, String descripcion, Boolean indicadorAutorizacionManual, String usuarioAutoriza, Date fechaAutorizacion)
/* 384:    */   {
/* 385:466 */     System.out.println(fechaAutorizacion);
/* 386:467 */     StringBuilder sql = new StringBuilder();
/* 387:468 */     sql.append(" UPDATE PedidoCliente p SET p.estado=:estado");
/* 388:469 */     if (descripcion != null) {
/* 389:470 */       sql.append(" , descripcion = :descripcion");
/* 390:    */     }
/* 391:472 */     if (indicadorAutorizacionManual != null) {
/* 392:473 */       sql.append(" , indicadorAutorizacionManual = :indicadorAutorizacionManual");
/* 393:    */     }
/* 394:475 */     if (usuarioAutoriza != null)
/* 395:    */     {
/* 396:476 */       sql.append(" , usuarioAutoriza = :usuarioAutoriza ");
/* 397:477 */       sql.append(" , usuariosAutorizacion = CONCAT(usuariosAutorizacion , ', ', :usuarioAutoriza)");
/* 398:    */     }
/* 399:479 */     if (fechaAutorizacion != null) {
/* 400:480 */       sql.append(" , fechaAutorizacion = :fechaAutorizacion");
/* 401:    */     }
/* 402:482 */     sql.append(" WHERE p.idPedidoCliente=:idPedidoCliente");
/* 403:483 */     Query query = this.em.createQuery(sql.toString());
/* 404:484 */     query.setParameter("idPedidoCliente", Integer.valueOf(pedidoCliente.getIdPedidoCliente()));
/* 405:485 */     query.setParameter("estado", estado);
/* 406:486 */     if (descripcion != null) {
/* 407:487 */       query.setParameter("descripcion", descripcion);
/* 408:    */     }
/* 409:489 */     if (indicadorAutorizacionManual != null) {
/* 410:490 */       query.setParameter("indicadorAutorizacionManual", indicadorAutorizacionManual);
/* 411:    */     }
/* 412:492 */     if (usuarioAutoriza != null) {
/* 413:493 */       query.setParameter("usuarioAutoriza", usuarioAutoriza);
/* 414:    */     }
/* 415:495 */     if (fechaAutorizacion != null) {
/* 416:496 */       query.setParameter("fechaAutorizacion", fechaAutorizacion);
/* 417:    */     }
/* 418:498 */     query.executeUpdate();
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void actualizarCantidadPorDespachar(List<Integer> listaIdDetalleDespachoCliente)
/* 422:    */   {
/* 423:507 */     String sql = "UPDATE DetallePedidoCliente dpp SET dpp.cantidadPorDespachar = dpp.cantidad -  \t\tCOALESCE( (SELECT SUM(drp1.cantidad) FROM DetalleDespachoCliente drp1  \t\tWHERE drp1.detallePedidoCliente.idDetallePedidoCliente=dpp.idDetallePedidoCliente\t\t\tAND drp1.despachoCliente.estado!=:estadoAnulado),0 ) WHERE dpp.idDetallePedidoCliente in (:listaIdDetalleDespachoCliente)";
/* 424:    */     
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:513 */     Query query = this.em.createQuery(sql);
/* 430:514 */     query.setParameter("listaIdDetalleDespachoCliente", listaIdDetalleDespachoCliente);
/* 431:515 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 432:516 */     query.executeUpdate();
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void actualizarCantidadPorFacturar(DetallePedidoCliente dpc, BigDecimal cantidad)
/* 436:    */   {
/* 437:526 */     String sql = "UPDATE DetallePedidoCliente dpc SET dpc.cantidadPorFacturar = dpc.cantidadPorFacturar - :cantidad WHERE dpc.idDetallePedidoCliente = :idDetallePedidoCliente";
/* 438:    */     
/* 439:    */ 
/* 440:529 */     Query query = this.em.createQuery(sql);
/* 441:530 */     query.setParameter("idDetallePedidoCliente", Integer.valueOf(dpc.getId()));
/* 442:531 */     query.setParameter("cantidad", cantidad);
/* 443:532 */     query.executeUpdate();
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void actualizarCantidadPorDespachar(DetallePedidoCliente dpc, BigDecimal cantidad)
/* 447:    */   {
/* 448:542 */     String sql = "UPDATE DetallePedidoCliente dpc SET dpc.cantidadPorDespachar = dpc.cantidadPorDespachar - :cantidad WHERE dpc.idDetallePedidoCliente = :idDetallePedidoCliente";
/* 449:    */     
/* 450:    */ 
/* 451:545 */     Query query = this.em.createQuery(sql);
/* 452:546 */     query.setParameter("idDetallePedidoCliente", Integer.valueOf(dpc.getId()));
/* 453:547 */     query.setParameter("cantidad", cantidad);
/* 454:548 */     query.executeUpdate();
/* 455:550 */     if (cantidad.compareTo(BigDecimal.ZERO) > 0)
/* 456:    */     {
/* 457:551 */       String sql2 = "UPDATE PedidoCliente pc SET pc.indicadorTieneDespacho = true  WHERE pc.idPedidoCliente = :idPedidoCliente and (pc.indicadorTieneDespacho IS NULL OR pc.indicadorTieneDespacho = false) ";
/* 458:    */       
/* 459:    */ 
/* 460:554 */       Query query2 = this.em.createQuery(sql2);
/* 461:555 */       query2.setParameter("idPedidoCliente", Integer.valueOf(dpc.getPedidoCliente().getId()));
/* 462:556 */       query2.executeUpdate();
/* 463:    */     }
/* 464:    */   }
/* 465:    */   
/* 466:    */   public List<PedidoCliente> listaPedidosPorDespachar(Organizacion organizacion)
/* 467:    */   {
/* 468:562 */     Query query = this.em.createQuery("SELECT new PedidoCliente(p.idPedidoCliente,p.numero,p.fecha, p.empresa.idEmpresa, p.empresa.identificacion, p.empresa.nombreFiscal)  FROM PedidoCliente p   WHERE p.estado=:estadoProcesado AND p.idOrganizacion=:idOrganizacion AND EXISTS (SELECT 1 FROM DetallePedidoCliente dpc WHERE dpc.pedidoCliente.idPedidoCliente = p.idPedidoCliente AND dpc.cantidadPorDespachar>0) ORDER BY p.numero DESC");
/* 469:    */     
/* 470:    */ 
/* 471:    */ 
/* 472:    */ 
/* 473:567 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 474:568 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getId()));
/* 475:569 */     return query.getResultList();
/* 476:    */   }
/* 477:    */   
/* 478:    */   public List<Contacto> getContactosPedidoCliente(PedidoCliente pedidoCliente)
/* 479:    */   {
/* 480:574 */     StringBuilder sql = new StringBuilder();
/* 481:575 */     sql.append("SELECT con");
/* 482:576 */     sql.append(" FROM PedidoCliente pc ");
/* 483:577 */     sql.append(" INNER JOIN pc.empresa e ");
/* 484:578 */     sql.append(" INNER JOIN e.listaContacto con ");
/* 485:579 */     sql.append(" INNER JOIN FETCH con.tipoContacto tc ");
/* 486:580 */     sql.append(" WHERE pc.idPedidoCliente = :idPedidoCliente");
/* 487:581 */     sql.append(" AND tc.indicadorNotificarPedidoCliente = true");
/* 488:    */     
/* 489:583 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPedidoCliente", Integer.valueOf(pedidoCliente.getId()));
/* 490:584 */     return query.getResultList();
/* 491:    */   }
/* 492:    */   
/* 493:    */   public List<DetallePedidoCliente> buscarDetallePedidoClientePorFechaDespacho(int idOrganizacion, TipoOrdenDespacho tipoOrdenDespacho, Date fechaDespacho, TipoPresentacionProducto tipoPresentacionProducto)
/* 494:    */   {
/* 495:590 */     if (tipoOrdenDespacho != null)
/* 496:    */     {
/* 497:591 */       StringBuilder sql = new StringBuilder();
/* 498:592 */       sql.append("SELECT DISTINCT(dpc) ");
/* 499:593 */       sql.append(" FROM DetallePedidoCliente dpc ");
/* 500:594 */       sql.append(" INNER JOIN FETCH dpc.pedidoCliente pc ");
/* 501:595 */       sql.append(" INNER JOIN FETCH pc.empresa e ");
/* 502:596 */       sql.append(" INNER JOIN FETCH e.cliente cl ");
/* 503:597 */       sql.append(" INNER JOIN FETCH cl.tipoOrdenDespacho tod ");
/* 504:598 */       sql.append(" INNER JOIN FETCH dpc.unidadVenta uv ");
/* 505:599 */       sql.append(" INNER JOIN FETCH dpc.producto pro ");
/* 506:600 */       sql.append(" INNER JOIN FETCH pc.bodega bdgpc ");
/* 507:601 */       sql.append(" INNER JOIN FETCH pc.sucursal su ");
/* 508:602 */       sql.append(" INNER JOIN FETCH pc.direccionEmpresa dir ");
/* 509:603 */       sql.append(" LEFT JOIN FETCH dir.ubicacion ubi ");
/* 510:604 */       sql.append(" LEFT JOIN FETCH pc.transportista tra ");
/* 511:605 */       sql.append(" LEFT JOIN FETCH pc.subempresa sube ");
/* 512:606 */       sql.append(" LEFT JOIN FETCH sube.empresa subee ");
/* 513:607 */       sql.append(" LEFT JOIN FETCH subee.cliente clSub ");
/* 514:608 */       sql.append(" LEFT JOIN FETCH clSub.tipoOrdenDespacho todSub ");
/* 515:609 */       sql.append(" LEFT JOIN FETCH pro.presentacionProducto pp ");
/* 516:610 */       sql.append(" LEFT JOIN FETCH pp.tipoPresentacionProducto tpp ");
/* 517:    */       
/* 518:612 */       sql.append(" LEFT JOIN FETCH e.proveedor ");
/* 519:613 */       sql.append(" LEFT JOIN FETCH e.empleado ");
/* 520:614 */       sql.append(" LEFT JOIN FETCH subee.proveedor ");
/* 521:615 */       sql.append(" LEFT JOIN FETCH subee.empleado ");
/* 522:    */       
/* 523:617 */       sql.append(" WHERE pc.idOrganizacion = :idOrganizacion ");
/* 524:618 */       sql.append(" AND pc.fechaDespacho = :fechaDespacho ");
/* 525:619 */       sql.append(" AND pc.estado = :estadoProcesado ");
/* 526:620 */       sql.append(" AND dpc.cantidadPorDespachar > 0 ");
/* 527:621 */       sql.append(" AND ((sube IS NULL AND tod.idTipoOrdenDespacho = :idTipoOrdenDespacho )  OR (todSub.idTipoOrdenDespacho = :idTipoOrdenDespacho)) ");
/* 528:623 */       if (tipoPresentacionProducto != null) {
/* 529:624 */         sql.append(" AND tpp.idTipoPresentacionProducto = :idTipoPresentacionProducto ");
/* 530:    */       }
/* 531:627 */       Query query = this.em.createQuery(sql.toString());
/* 532:628 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 533:629 */       query.setParameter("fechaDespacho", fechaDespacho, TemporalType.DATE);
/* 534:630 */       query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 535:631 */       query.setParameter("idTipoOrdenDespacho", Integer.valueOf(tipoOrdenDespacho.getId()));
/* 536:632 */       if (tipoPresentacionProducto != null) {
/* 537:633 */         query.setParameter("idTipoPresentacionProducto", Integer.valueOf(tipoPresentacionProducto.getId()));
/* 538:    */       }
/* 539:635 */       return query.getResultList();
/* 540:    */     }
/* 541:637 */     return new ArrayList();
/* 542:    */   }
/* 543:    */   
/* 544:    */   public BigDecimal obtenerTotalPedidoPorProductoPorFechaDespacho(Producto producto, Date fechaInicio, Date fechaFin)
/* 545:    */   {
/* 546:642 */     StringBuilder sql = new StringBuilder();
/* 547:643 */     sql.append(" SELECT SUM(dpc.cantidad) ");
/* 548:644 */     sql.append(" FROM DetallePedidoCliente dpc ");
/* 549:645 */     sql.append(" INNER JOIN dpc.pedidoCliente pc ");
/* 550:646 */     sql.append(" INNER JOIN dpc.producto p ");
/* 551:647 */     sql.append(" WHERE p.idProducto = :idProducto ");
/* 552:648 */     sql.append(" AND pc.estado = :estadoProcesado ");
/* 553:649 */     sql.append(" AND pc.fechaDespacho >= :fechaInicio ");
/* 554:650 */     sql.append(" AND pc.fechaDespacho <= :fechaFin ");
/* 555:    */     
/* 556:652 */     Query query = this.em.createQuery(sql.toString());
/* 557:653 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/* 558:654 */     query.setParameter("fechaInicio", fechaInicio);
/* 559:655 */     query.setParameter("fechaFin", fechaFin);
/* 560:656 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 561:    */     
/* 562:658 */     BigDecimal valor = BigDecimal.ZERO;
/* 563:    */     try
/* 564:    */     {
/* 565:660 */       valor = (BigDecimal)query.getSingleResult();
/* 566:    */     }
/* 567:    */     catch (NoResultException e)
/* 568:    */     {
/* 569:662 */       valor = BigDecimal.ZERO;
/* 570:    */     }
/* 571:664 */     if (valor == null) {
/* 572:665 */       valor = BigDecimal.ZERO;
/* 573:    */     }
/* 574:667 */     return valor;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public String obtenerMayorNumeroPedidoDividido(String numero)
/* 578:    */   {
/* 579:671 */     StringBuilder sql = new StringBuilder();
/* 580:672 */     sql.append(" SELECT MAX(pc.numero) ");
/* 581:673 */     sql.append(" FROM PedidoCliente pc ");
/* 582:674 */     sql.append(" WHERE pc.numero like :numero ");
/* 583:    */     
/* 584:676 */     Query query = this.em.createQuery(sql.toString());
/* 585:677 */     query.setParameter("numero", numero + "%");
/* 586:    */     
/* 587:679 */     String mayorNumero = (String)query.getSingleResult();
/* 588:680 */     return mayorNumero;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public List<Object[]> getReporteLogisticaTransportista(Date fechaDesde, Date fechaHasta, int idOrganizacion, Transportista transportista, Ruta ruta, Empresa empresa)
/* 592:    */   {
/* 593:686 */     StringBuilder sql = new StringBuilder();
/* 594:687 */     sql.append(" SELECT pc.numero, pc.fecha, pc.fechaDespacho, emp.identificacion, emp.nombreFiscal, tra.nombre, ");
/* 595:688 */     sql.append(" ch.nombre, vh.placa, rut.ruta, pc.total - pc.descuento + pc.impuesto ");
/* 596:689 */     sql.append(" FROM PedidoCliente pc ");
/* 597:690 */     sql.append(" INNER JOIN pc.empresa emp ");
/* 598:691 */     sql.append(" INNER JOIN pc.transportista tra ");
/* 599:692 */     sql.append(" INNER JOIN pc.ruta rut ");
/* 600:693 */     sql.append(" INNER JOIN pc.chofer ch ");
/* 601:694 */     sql.append(" INNER JOIN pc.vehiculo vh ");
/* 602:695 */     sql.append(" WHERE pc.fecha between :fechaDesde AND :fechaHasta ");
/* 603:696 */     sql.append(" AND pc.idOrganizacion = :idOrganizacion ");
/* 604:697 */     sql.append(" AND pc.estado != :estadoAnulado ");
/* 605:698 */     if (transportista != null) {
/* 606:699 */       sql.append(" AND tra.idTransportista = :idTransportista ");
/* 607:    */     }
/* 608:701 */     if (ruta != null) {
/* 609:702 */       sql.append(" AND rut.idRuta = :idRuta ");
/* 610:    */     }
/* 611:704 */     if (empresa != null) {
/* 612:705 */       sql.append(" AND emp.idEmpresa = :idEmpresa ");
/* 613:    */     }
/* 614:707 */     sql.append(" ORDER BY emp.nombreFiscal, pc.fecha ");
/* 615:    */     
/* 616:709 */     Query query = this.em.createQuery(sql.toString());
/* 617:710 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 618:711 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 619:712 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 620:713 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 621:714 */     if (transportista != null) {
/* 622:715 */       query.setParameter("idTransportista", Integer.valueOf(transportista.getId()));
/* 623:    */     }
/* 624:717 */     if (ruta != null) {
/* 625:718 */       query.setParameter("idRuta", Integer.valueOf(ruta.getId()));
/* 626:    */     }
/* 627:720 */     if (empresa != null) {
/* 628:721 */       query.setParameter("idEmpresa", Integer.valueOf(empresa.getId()));
/* 629:    */     }
/* 630:724 */     return query.getResultList();
/* 631:    */   }
/* 632:    */   
/* 633:    */   public PedidoCliente buscarPorId(Object id)
/* 634:    */   {
/* 635:730 */     PedidoCliente pedidoCliente = (PedidoCliente)super.buscarPorId(id);
/* 636:731 */     pedidoCliente.getDocumento();
/* 637:732 */     return pedidoCliente;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public List<Object[]> getReporteProductoPedidoVsFactura(Date fechaDesde, Date fechaHasta, int idCategoriaEmpresa, int idEmpresa, int idZona, int idCanal, int idAgenteComercial, int idSucursal, int idOrganizacion, List<Integer> listaIdDetallePedidoCliente)
/* 641:    */   {
/* 642:745 */     StringBuilder sqlp = new StringBuilder();
/* 643:746 */     sqlp.append(" SELECT dpc.idDetallePedidoCliente, pc.fecha, pc.numero, e.identificacion,e.nombreFiscal, CONCAT(a.nombre2,' ',a.nombre1) ,c.nombre, z.nombre, s.nombre , p.codigo, p.nombre, scp.codigo, scp.nombre, dpc.cantidad, dpc.precio, dpc.descuento, (dpc.cantidad * dpc.precio), (dpc.cantidad*0), (dpc.cantidad*0), (dpc.cantidad*0), (dpc.cantidad*0), pc.fecha, '', u.codigo ");
/* 644:    */     
/* 645:748 */     sqlp.append(" FROM DetallePedidoCliente dpc");
/* 646:749 */     sqlp.append(" INNER JOIN dpc.pedidoCliente pc");
/* 647:750 */     sqlp.append(" INNER JOIN pc.empresa e");
/* 648:751 */     sqlp.append(" INNER JOIN e.categoriaEmpresa ce");
/* 649:752 */     sqlp.append(" LEFT  JOIN pc.agenteComercial a");
/* 650:753 */     sqlp.append(" LEFT  JOIN pc.canal c");
/* 651:754 */     sqlp.append(" LEFT  JOIN pc.zona  z");
/* 652:755 */     sqlp.append(" INNER JOIN pc.sucursal s");
/* 653:756 */     sqlp.append(" INNER JOIN dpc.producto p");
/* 654:757 */     sqlp.append(" INNER JOIN p.unidadVenta u ");
/* 655:758 */     sqlp.append(" LEFT JOIN p.subcategoriaProducto scp");
/* 656:760 */     if ((listaIdDetallePedidoCliente != null) && (!listaIdDetallePedidoCliente.isEmpty()))
/* 657:    */     {
/* 658:761 */       sqlp.append(" WHERE dpc.idDetallePedidoCliente IN (:listaIdDetallePedidoCliente) ");
/* 659:    */     }
/* 660:    */     else
/* 661:    */     {
/* 662:764 */       sqlp.append(" WHERE pc.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 663:765 */       sqlp.append(" AND pc.idOrganizacion = :idOrganizacion");
/* 664:766 */       sqlp.append(" AND pc.estado != :estadoElaborado ");
/* 665:767 */       sqlp.append(" AND pc.estado != :estadoAnulado ");
/* 666:769 */       if (idCategoriaEmpresa != 0) {
/* 667:770 */         sqlp.append("  AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 668:    */       }
/* 669:772 */       if (idEmpresa != 0) {
/* 670:773 */         sqlp.append("  AND e.idEmpresa = :idEmpresa ");
/* 671:    */       }
/* 672:775 */       if (idZona != 0) {
/* 673:776 */         sqlp.append("  AND z.idZona = :idZona ");
/* 674:    */       }
/* 675:778 */       if (idCanal != 0) {
/* 676:779 */         sqlp.append("  AND c.idCanal = :idCanal ");
/* 677:    */       }
/* 678:781 */       if (idAgenteComercial != 0) {
/* 679:782 */         sqlp.append("  AND a.idUsuario = :idAgenteComercial ");
/* 680:    */       }
/* 681:784 */       if (idSucursal != 0) {
/* 682:785 */         sqlp.append(" AND s.idSucursal = :idSucursal ");
/* 683:    */       }
/* 684:787 */       sqlp.append("  ORDER BY pc.numero, e.nombreFiscal, p.nombre ");
/* 685:    */     }
/* 686:790 */     Query query = this.em.createQuery(sqlp.toString());
/* 687:792 */     if ((listaIdDetallePedidoCliente != null) && (!listaIdDetallePedidoCliente.isEmpty()))
/* 688:    */     {
/* 689:793 */       query.setParameter("listaIdDetallePedidoCliente", listaIdDetallePedidoCliente);
/* 690:    */     }
/* 691:    */     else
/* 692:    */     {
/* 693:796 */       query.setParameter("fechaDesde", fechaDesde);
/* 694:797 */       query.setParameter("fechaHasta", fechaHasta);
/* 695:798 */       query.setParameter("estadoElaborado", Estado.ELABORADO);
/* 696:799 */       query.setParameter("estadoAnulado", Estado.ANULADO);
/* 697:800 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 698:802 */       if (idCategoriaEmpresa != 0) {
/* 699:803 */         query.setParameter("idCategoriaEmpresa", Integer.valueOf(idCategoriaEmpresa));
/* 700:    */       }
/* 701:805 */       if (idEmpresa != 0) {
/* 702:806 */         query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 703:    */       }
/* 704:808 */       if (idZona != 0) {
/* 705:809 */         query.setParameter("idZona", Integer.valueOf(idZona));
/* 706:    */       }
/* 707:811 */       if (idCanal != 0) {
/* 708:812 */         query.setParameter("idCanal", Integer.valueOf(idCanal));
/* 709:    */       }
/* 710:814 */       if (idAgenteComercial != 0) {
/* 711:815 */         query.setParameter("idAgenteComercial", Integer.valueOf(idAgenteComercial));
/* 712:    */       }
/* 713:817 */       if (idSucursal != 0) {
/* 714:818 */         query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 715:    */       }
/* 716:    */     }
/* 717:822 */     return query.getResultList();
/* 718:    */   }
/* 719:    */   
/* 720:    */   public List<PedidoCliente> getPedidosClienteNoExistentesRegistroPeso(Estado estado, Empresa empresa, RegistroPeso registroPeso)
/* 721:    */   {
/* 722:832 */     StringBuilder sql = new StringBuilder();
/* 723:833 */     sql.append(" SELECT pc FROM PedidoCliente pc ");
/* 724:834 */     sql.append(" WHERE NOT EXISTS ( ");
/* 725:835 */     sql.append(" \tSELECT drp FROM DetalleRegistroPeso drp ");
/* 726:836 */     sql.append("    INNER JOIN drp.detallePedidoCliente dpc ");
/* 727:837 */     sql.append(" \tWHERE dpc.pedidoCliente = pc ");
/* 728:838 */     sql.append(" \tAND   drp.registroPeso.idRegistroPeso  <> :idRegistroPeso ");
/* 729:839 */     sql.append(" ) ");
/* 730:840 */     sql.append(" AND  pc.estado = :estado ");
/* 731:841 */     sql.append(" AND  pc.empresa = :empresa ");
/* 732:    */     
/* 733:843 */     Query query = this.em.createQuery(sql.toString());
/* 734:844 */     query.setParameter("idRegistroPeso", Integer.valueOf(registroPeso == null ? 0 : registroPeso.getIdRegistroPeso()));
/* 735:845 */     query.setParameter("empresa", empresa);
/* 736:846 */     query.setParameter("estado", estado);
/* 737:    */     
/* 738:848 */     return query.getResultList();
/* 739:    */   }
/* 740:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PedidoClienteDao
 * JD-Core Version:    0.7.0.1
 */