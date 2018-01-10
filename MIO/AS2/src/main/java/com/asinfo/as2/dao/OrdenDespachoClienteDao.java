/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   4:    */ import com.asinfo.as2.entities.DetalleOrdenDespachoCliente;
/*   5:    */ import com.asinfo.as2.entities.DetalleOrdenDespachoClientePedidoCliente;
/*   6:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*   7:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.OrdenDespachoCliente;
/*   9:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*  10:    */ import java.math.BigDecimal;
/*  11:    */ import java.math.RoundingMode;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.HashSet;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Set;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ import javax.persistence.EntityManager;
/*  21:    */ import javax.persistence.Query;
/*  22:    */ import javax.persistence.TypedQuery;
/*  23:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  24:    */ import javax.persistence.criteria.CriteriaQuery;
/*  25:    */ import javax.persistence.criteria.Expression;
/*  26:    */ import javax.persistence.criteria.Fetch;
/*  27:    */ import javax.persistence.criteria.JoinType;
/*  28:    */ import javax.persistence.criteria.Path;
/*  29:    */ import javax.persistence.criteria.Predicate;
/*  30:    */ import javax.persistence.criteria.Root;
/*  31:    */ 
/*  32:    */ @Stateless
/*  33:    */ public class OrdenDespachoClienteDao
/*  34:    */   extends AbstractDaoAS2<OrdenDespachoCliente>
/*  35:    */ {
/*  36:    */   public OrdenDespachoClienteDao()
/*  37:    */   {
/*  38: 50 */     super(OrdenDespachoCliente.class);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public OrdenDespachoCliente cargarDetalle(int idOrdenDespachoCliente)
/*  42:    */   {
/*  43: 55 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  44:    */     
/*  45:    */ 
/*  46: 58 */     CriteriaQuery<OrdenDespachoCliente> cqCabecera = criteriaBuilder.createQuery(OrdenDespachoCliente.class);
/*  47: 59 */     Root<OrdenDespachoCliente> fromCabecera = cqCabecera.from(OrdenDespachoCliente.class);
/*  48:    */     
/*  49: 61 */     fromCabecera.fetch("documento", JoinType.INNER).fetch("secuencia", JoinType.LEFT);
/*  50: 62 */     fromCabecera.fetch("tipoOrdenDespacho", JoinType.LEFT);
/*  51: 63 */     fromCabecera.fetch("sucursal", JoinType.LEFT).fetch("ubicacion", JoinType.LEFT);
/*  52: 64 */     fromCabecera.fetch("tipoPresentacionProducto", JoinType.LEFT);
/*  53:    */     
/*  54: 66 */     Path<Integer> pathId = fromCabecera.get("idOrdenDespachoCliente");
/*  55: 67 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idOrdenDespachoCliente)));
/*  56: 68 */     CriteriaQuery<OrdenDespachoCliente> selectFrom = cqCabecera.select(fromCabecera);
/*  57:    */     
/*  58: 70 */     OrdenDespachoCliente ordenDespachoCliente = (OrdenDespachoCliente)this.em.createQuery(selectFrom).getSingleResult();
/*  59:    */     
/*  60:    */ 
/*  61: 73 */     CriteriaQuery<MovimientoInventario> cqTransformacion = criteriaBuilder.createQuery(MovimientoInventario.class);
/*  62: 74 */     Root<MovimientoInventario> fromTransformacion = cqTransformacion.from(MovimientoInventario.class);
/*  63:    */     
/*  64: 76 */     fromTransformacion.fetch("documento", JoinType.INNER);
/*  65: 77 */     fromTransformacion.fetch("movimientoInventarioPadre", JoinType.INNER);
/*  66: 78 */     fromTransformacion.fetch("asiento", JoinType.LEFT);
/*  67:    */     
/*  68: 80 */     List<Expression<?>> expresionesDetalle = new ArrayList();
/*  69: 81 */     expresionesDetalle.add(criteriaBuilder.equal(fromTransformacion.join("ordenDespachoCliente"), ordenDespachoCliente));
/*  70: 82 */     expresionesDetalle.add(criteriaBuilder.isNotNull(fromTransformacion.get("movimientoInventarioPadre")));
/*  71:    */     
/*  72: 84 */     cqTransformacion.where((Predicate[])expresionesDetalle.toArray(new Predicate[expresionesDetalle.size()]));
/*  73: 85 */     CriteriaQuery<MovimientoInventario> selectTransformacion = cqTransformacion.select(fromTransformacion);
/*  74:    */     
/*  75: 87 */     List<MovimientoInventario> listaTransformacionProducto = this.em.createQuery(selectTransformacion).getResultList();
/*  76:    */     
/*  77: 89 */     ordenDespachoCliente.setListaTransformacionProducto(listaTransformacionProducto);
/*  78:    */     
/*  79:    */ 
/*  80: 92 */     CriteriaQuery<DetalleOrdenDespachoCliente> cqDetalle = criteriaBuilder.createQuery(DetalleOrdenDespachoCliente.class);
/*  81: 93 */     Root<DetalleOrdenDespachoCliente> fromDetalle = cqDetalle.from(DetalleOrdenDespachoCliente.class);
/*  82: 94 */     fromDetalle.fetch("bodega", JoinType.LEFT).fetch("ubicacion", JoinType.LEFT);
/*  83: 95 */     fromDetalle.fetch("lote", JoinType.LEFT);
/*  84: 96 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.INNER);
/*  85:    */     
/*  86: 98 */     Fetch<Object, Object> presentacionProducto = producto.fetch("presentacionProducto", JoinType.LEFT);
/*  87: 99 */     presentacionProducto.fetch("tipoPresentacionProducto", JoinType.LEFT);
/*  88:    */     
/*  89:101 */     cqDetalle.where(criteriaBuilder.equal(fromDetalle.join("ordenDespachoCliente"), ordenDespachoCliente));
/*  90:102 */     CriteriaQuery<DetalleOrdenDespachoCliente> selectDetalle = cqDetalle.select(fromDetalle);
/*  91:    */     
/*  92:104 */     List<DetalleOrdenDespachoCliente> listaDetalleOrdenDespachoCliente = this.em.createQuery(selectDetalle).getResultList();
/*  93:105 */     ordenDespachoCliente.setListaDetalleOrdenDespachoCliente(listaDetalleOrdenDespachoCliente);
/*  94:106 */     for (DetalleOrdenDespachoCliente detalleOrdenDespachoCliente : listaDetalleOrdenDespachoCliente)
/*  95:    */     {
/*  96:109 */       CriteriaQuery<DetalleOrdenDespachoClientePedidoCliente> cqDetallePedido = criteriaBuilder.createQuery(DetalleOrdenDespachoClientePedidoCliente.class);
/*  97:    */       
/*  98:111 */       Root<DetalleOrdenDespachoClientePedidoCliente> fromDetalleOrdenPedido = cqDetallePedido.from(DetalleOrdenDespachoClientePedidoCliente.class);
/*  99:112 */       Fetch<Object, Object> detallePedido = fromDetalleOrdenPedido.fetch("detallePedidoCliente", JoinType.LEFT);
/* 100:113 */       fromDetalleOrdenPedido.fetch("detalleOrdenDespachoCliente", JoinType.LEFT);
/* 101:114 */       Fetch<Object, Object> pedidoCliente = detallePedido.fetch("pedidoCliente", JoinType.LEFT);
/* 102:    */       
/* 103:116 */       Fetch<Object, Object> empresa = pedidoCliente.fetch("empresa", JoinType.LEFT);
/* 104:117 */       empresa.fetch("proveedor", JoinType.LEFT);
/* 105:118 */       empresa.fetch("empleado", JoinType.LEFT);
/* 106:119 */       empresa.fetch("cliente", JoinType.LEFT).fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 107:120 */       Fetch<Object, Object> subempresa = pedidoCliente.fetch("subempresa", JoinType.LEFT);
/* 108:121 */       Fetch<Object, Object> empresaS = subempresa.fetch("empresa", JoinType.LEFT);
/* 109:122 */       empresaS.fetch("proveedor", JoinType.LEFT);
/* 110:123 */       empresaS.fetch("empleado", JoinType.LEFT);
/* 111:124 */       empresaS.fetch("cliente", JoinType.LEFT).fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 112:125 */       pedidoCliente.fetch("transportista", JoinType.LEFT);
/* 113:126 */       Fetch<Object, Object> direccion = pedidoCliente.fetch("direccionEmpresa", JoinType.LEFT);
/* 114:127 */       direccion.fetch("ubicacion", JoinType.LEFT);
/* 115:128 */       detallePedido.fetch("unidadVenta", JoinType.LEFT);
/* 116:    */       
/* 117:130 */       cqDetallePedido.where(criteriaBuilder.equal(fromDetalleOrdenPedido.join("detalleOrdenDespachoCliente"), detalleOrdenDespachoCliente));
/* 118:131 */       CriteriaQuery<DetalleOrdenDespachoClientePedidoCliente> selectDetallePedido = cqDetallePedido.select(fromDetalleOrdenPedido);
/* 119:    */       
/* 120:133 */       List<DetalleOrdenDespachoClientePedidoCliente> listaDetallePedidoCliente = this.em.createQuery(selectDetallePedido).getResultList();
/* 121:134 */       detalleOrdenDespachoCliente.setListaDetallePedidoCliente(listaDetallePedidoCliente);
/* 122:    */       
/* 123:    */ 
/* 124:137 */       CriteriaQuery<LecturaBalanza> cqLecturaBalanza = criteriaBuilder.createQuery(LecturaBalanza.class);
/* 125:138 */       Root<LecturaBalanza> fromLecturaBalanza = cqLecturaBalanza.from(LecturaBalanza.class);
/* 126:139 */       fromLecturaBalanza.fetch("producto", JoinType.INNER);
/* 127:140 */       fromLecturaBalanza.fetch("unidadManejo", JoinType.LEFT);
/* 128:141 */       fromLecturaBalanza.fetch("pallet", JoinType.LEFT);
/* 129:142 */       fromLecturaBalanza.fetch("detalleOrdenDespachoCliente", JoinType.INNER);
/* 130:    */       
/* 131:144 */       cqLecturaBalanza.where(criteriaBuilder.equal(fromLecturaBalanza.join("detalleOrdenDespachoCliente"), detalleOrdenDespachoCliente));
/* 132:145 */       CriteriaQuery<LecturaBalanza> selectLecturaBalanza = cqLecturaBalanza.select(fromLecturaBalanza);
/* 133:    */       
/* 134:147 */       List<LecturaBalanza> listaLecturaBalanza = this.em.createQuery(selectLecturaBalanza).getResultList();
/* 135:148 */       detalleOrdenDespachoCliente.setListaLecturaBalanza(listaLecturaBalanza);
/* 136:    */     }
/* 137:150 */     return ordenDespachoCliente;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<OrdenDespachoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 141:    */   {
/* 142:156 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 143:157 */     CriteriaQuery<OrdenDespachoCliente> criteriaQuery = criteriaBuilder.createQuery(OrdenDespachoCliente.class);
/* 144:158 */     Root<OrdenDespachoCliente> from = criteriaQuery.from(OrdenDespachoCliente.class);
/* 145:    */     
/* 146:160 */     from.fetch("tipoOrdenDespacho", JoinType.LEFT);
/* 147:161 */     from.fetch("tipoPresentacionProducto", JoinType.LEFT);
/* 148:    */     
/* 149:163 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 150:164 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 151:    */     
/* 152:166 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 153:    */     
/* 154:168 */     CriteriaQuery<OrdenDespachoCliente> select = criteriaQuery.select(from);
/* 155:    */     
/* 156:170 */     TypedQuery<OrdenDespachoCliente> typedQuery = this.em.createQuery(select);
/* 157:171 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 158:    */     
/* 159:173 */     List<OrdenDespachoCliente> listaOrdenDespachoCliente = typedQuery.getResultList();
/* 160:174 */     for (OrdenDespachoCliente ordenDespachoCliente : listaOrdenDespachoCliente)
/* 161:    */     {
/* 162:176 */       CriteriaQuery<MovimientoInventario> cqDetalle = criteriaBuilder.createQuery(MovimientoInventario.class);
/* 163:177 */       Root<MovimientoInventario> fromDetalle = cqDetalle.from(MovimientoInventario.class);
/* 164:    */       
/* 165:179 */       List<Expression<?>> expresionesDetalle = new ArrayList();
/* 166:180 */       expresionesDetalle.add(criteriaBuilder.equal(fromDetalle.join("ordenDespachoCliente"), ordenDespachoCliente));
/* 167:181 */       expresionesDetalle.add(criteriaBuilder.isNotNull(fromDetalle.get("movimientoInventarioPadre")));
/* 168:    */       
/* 169:183 */       cqDetalle.where((Predicate[])expresionesDetalle.toArray(new Predicate[expresionesDetalle.size()]));
/* 170:184 */       CriteriaQuery<MovimientoInventario> selectDetalle = cqDetalle.select(fromDetalle);
/* 171:    */       
/* 172:186 */       List<MovimientoInventario> listaTransformacionProducto = this.em.createQuery(selectDetalle).getResultList();
/* 173:    */       
/* 174:188 */       ordenDespachoCliente.setListaTransformacionProducto(listaTransformacionProducto);
/* 175:    */       
/* 176:    */ 
/* 177:191 */       CriteriaQuery<DespachoCliente> cqDespacho = criteriaBuilder.createQuery(DespachoCliente.class);
/* 178:192 */       Root<DespachoCliente> fromDespacho = cqDespacho.from(DespachoCliente.class);
/* 179:193 */       fromDespacho.fetch("guiaRemision", JoinType.LEFT);
/* 180:194 */       List<Expression<?>> expresionesDespacho = new ArrayList();
/* 181:195 */       expresionesDespacho.add(criteriaBuilder.equal(fromDespacho.join("ordenDespachoCliente"), ordenDespachoCliente));
/* 182:    */       
/* 183:197 */       cqDespacho.where((Predicate[])expresionesDespacho.toArray(new Predicate[expresionesDespacho.size()]));
/* 184:198 */       CriteriaQuery<DespachoCliente> selectDespacho = cqDespacho.select(fromDespacho);
/* 185:    */       
/* 186:200 */       List<DespachoCliente> listaDespachoCliente = this.em.createQuery(selectDespacho).getResultList();
/* 187:    */       
/* 188:202 */       ordenDespachoCliente.setListaDespachoCliente(listaDespachoCliente);
/* 189:    */     }
/* 190:205 */     return listaOrdenDespachoCliente;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<Object[]> getReporteOrdenDespachoGavetas(OrdenDespachoCliente ordenDespachoCliente, boolean indicadorAcumulado)
/* 194:    */   {
/* 195:210 */     StringBuilder sql = new StringBuilder();
/* 196:211 */     sql.append(" SELECT dodc.idDetalleOrdenDespachoCliente, p.idProducto, p.codigo, p.nombre, dodc.cantidadEmbalajeDespacho, dodc.cantidadUnidadDespacho, gv.nombre, SUM(lb.numeroUnidadesManejo), CASE WHEN todc.indicadorAplicaPorcientoAdicionalPedidos = true THEN p.porCientoDespachoSuperaPedido ELSE 0 END, uv.numeroDecimales ");
/* 197:212 */     sql.append(" FROM LecturaBalanza lb ");
/* 198:213 */     sql.append(" LEFT JOIN lb.unidadManejo gv ");
/* 199:214 */     sql.append(" RIGHT JOIN lb.detalleOrdenDespachoCliente dodc ");
/* 200:215 */     sql.append(" INNER JOIN dodc.ordenDespachoCliente odc ");
/* 201:216 */     sql.append(" INNER JOIN odc.tipoOrdenDespacho todc ");
/* 202:217 */     sql.append(" INNER JOIN dodc.producto p ");
/* 203:218 */     sql.append(" INNER JOIN p.unidadVenta uv ");
/* 204:219 */     if (!indicadorAcumulado)
/* 205:    */     {
/* 206:220 */       sql.append(" WHERE odc.idOrdenDespachoCliente =:idOrdenDespachoCliente ");
/* 207:    */     }
/* 208:    */     else
/* 209:    */     {
/* 210:222 */       sql.append(" WHERE odc.fecha =:fecha ");
/* 211:223 */       sql.append(" AND todc.idTipoOrdenDespacho =:idTipoOrdenDespacho ");
/* 212:    */     }
/* 213:225 */     sql.append(" GROUP BY dodc.idDetalleOrdenDespachoCliente, p.idProducto, p.codigo, p.nombre, dodc.cantidadEmbalajeDespacho, dodc.cantidadUnidadDespacho, gv.nombre, p.porCientoDespachoSuperaPedido, todc.indicadorAplicaPorcientoAdicionalPedidos, uv.numeroDecimales ");
/* 214:226 */     if (!indicadorAcumulado) {
/* 215:227 */       sql.append(" HAVING SUM(dodc.cantidadUnidadDespacho) > 0 ");
/* 216:    */     }
/* 217:230 */     Query query = this.em.createQuery(sql.toString());
/* 218:231 */     if (!indicadorAcumulado)
/* 219:    */     {
/* 220:232 */       query.setParameter("idOrdenDespachoCliente", Integer.valueOf(ordenDespachoCliente.getId()));
/* 221:    */     }
/* 222:    */     else
/* 223:    */     {
/* 224:234 */       query.setParameter("idTipoOrdenDespacho", Integer.valueOf(ordenDespachoCliente.getTipoOrdenDespacho().getId()));
/* 225:235 */       query.setParameter("fecha", ordenDespachoCliente.getFecha());
/* 226:    */     }
/* 227:239 */     List<Object[]> lista = query.getResultList();
/* 228:240 */     Map<String, BigDecimal> mapaCantidadPedidoDetalle = new HashMap();
/* 229:241 */     Map<String, BigDecimal> mapaCantidadPedidoProducto = new HashMap();
/* 230:242 */     Map<String, BigDecimal> mapaProductoCantidadDespachada = new HashMap();
/* 231:243 */     Set<Integer> setIdDetalleOrdenDespachoCliente = new HashSet();
/* 232:244 */     for (Iterator localIterator1 = lista.iterator(); localIterator1.hasNext();)
/* 233:    */     {
/* 234:244 */       objects = (Object[])localIterator1.next();
/* 235:245 */       Integer idDetalleOrdenDespachoCliente = (Integer)objects[0];
/* 236:246 */       Integer idProducto = (Integer)objects[1];
/* 237:247 */       BigDecimal cantidadEmbalajeDespacho = (BigDecimal)objects[4];
/* 238:248 */       BigDecimal cantidadUnidadDespacho = (BigDecimal)objects[5];
/* 239:250 */       if (cantidadUnidadDespacho == null) {
/* 240:251 */         cantidadUnidadDespacho = BigDecimal.ZERO;
/* 241:    */       }
/* 242:254 */       if (!setIdDetalleOrdenDespachoCliente.contains(idDetalleOrdenDespachoCliente))
/* 243:    */       {
/* 244:256 */         if (mapaProductoCantidadDespachada.containsKey(idProducto + "-" + cantidadEmbalajeDespacho)) {
/* 245:257 */           cantidadUnidadDespacho = cantidadUnidadDespacho.add((BigDecimal)mapaProductoCantidadDespachada.get(idProducto + "-" + cantidadEmbalajeDespacho));
/* 246:    */         }
/* 247:260 */         mapaProductoCantidadDespachada.put(idProducto + "-" + cantidadEmbalajeDespacho, cantidadUnidadDespacho);
/* 248:    */         
/* 249:262 */         StringBuilder sql2 = new StringBuilder();
/* 250:263 */         sql2.append(" SELECT dpc.idDetallePedidoCliente, dpc.cantidad ");
/* 251:264 */         sql2.append(" FROM DetalleOrdenDespachoClientePedidoCliente dodcpc ");
/* 252:265 */         sql2.append(" INNER JOIN dodcpc.detallePedidoCliente dpc ");
/* 253:266 */         sql2.append(" INNER JOIN dodcpc.detalleOrdenDespachoCliente dodc ");
/* 254:267 */         sql2.append(" WHERE dodc.idDetalleOrdenDespachoCliente = :idDetalleOrdenDespachoCliente ");
/* 255:    */         
/* 256:269 */         Query query2 = this.em.createQuery(sql2.toString());
/* 257:270 */         query2.setParameter("idDetalleOrdenDespachoCliente", idDetalleOrdenDespachoCliente);
/* 258:271 */         List<Object[]> listaCantidadPedido = query2.getResultList();
/* 259:272 */         for (Object[] objects2 : listaCantidadPedido)
/* 260:    */         {
/* 261:273 */           Integer idDetallePedidoCliente = (Integer)objects2[0];
/* 262:274 */           BigDecimal cantidadPedido = (BigDecimal)objects2[1];
/* 263:275 */           String key = idProducto.toString() + "-" + idDetallePedidoCliente.toString();
/* 264:276 */           if (!mapaCantidadPedidoDetalle.containsKey(key))
/* 265:    */           {
/* 266:277 */             BigDecimal cantidadPedidoProducto = BigDecimal.ZERO;
/* 267:278 */             if (mapaCantidadPedidoProducto.containsKey(idProducto + "-" + cantidadEmbalajeDespacho)) {
/* 268:279 */               cantidadPedidoProducto = (BigDecimal)mapaCantidadPedidoProducto.get(idProducto + "-" + cantidadEmbalajeDespacho);
/* 269:    */             }
/* 270:281 */             cantidadPedidoProducto = cantidadPedidoProducto.add(cantidadPedido);
/* 271:282 */             mapaCantidadPedidoProducto.put(idProducto + "-" + cantidadEmbalajeDespacho, cantidadPedidoProducto);
/* 272:    */           }
/* 273:284 */           mapaCantidadPedidoDetalle.put(key, cantidadPedido);
/* 274:    */         }
/* 275:    */       }
/* 276:287 */       setIdDetalleOrdenDespachoCliente.add(idDetalleOrdenDespachoCliente);
/* 277:    */     }
/* 278:    */     Object[] objects;
/* 279:290 */     Object mapaProductoGaveta = new HashMap();
/* 280:291 */     for (Object[] objects : lista)
/* 281:    */     {
/* 282:292 */       Integer idDetalleOrdenDespachoCliente = (Integer)objects[0];
/* 283:293 */       Integer idProducto = (Integer)objects[1];
/* 284:294 */       String codigoProducto = (String)objects[2];
/* 285:295 */       String nombreProducto = (String)objects[3];
/* 286:296 */       BigDecimal cantidadEmbalajeDespacho = (BigDecimal)objects[4];
/* 287:297 */       if ((cantidadEmbalajeDespacho == null) || (cantidadEmbalajeDespacho.compareTo(BigDecimal.ZERO) == 0)) {
/* 288:298 */         cantidadEmbalajeDespacho = BigDecimal.ONE;
/* 289:    */       }
/* 290:300 */       BigDecimal cantidadUnidadDespacho = (BigDecimal)mapaProductoCantidadDespachada.get(idProducto + "-" + cantidadEmbalajeDespacho);
/* 291:301 */       if (cantidadUnidadDespacho == null) {
/* 292:302 */         cantidadUnidadDespacho = BigDecimal.ZERO;
/* 293:    */       }
/* 294:304 */       String nombreUnidadManejo = (String)objects[6];
/* 295:305 */       Long numeroUnidadesManejo = (Long)objects[7];
/* 296:306 */       if (numeroUnidadesManejo == null) {
/* 297:307 */         numeroUnidadesManejo = new Long(0L);
/* 298:    */       }
/* 299:309 */       Integer porcientoDespachoSuperaPedido = (Integer)objects[8];
/* 300:310 */       Integer numeroDecimales = (Integer)objects[9];
/* 301:    */       
/* 302:312 */       BigDecimal cantidadPedidoProducto = (BigDecimal)mapaCantidadPedidoProducto.get(idProducto + "-" + cantidadEmbalajeDespacho);
/* 303:313 */       if (cantidadPedidoProducto == null) {
/* 304:314 */         cantidadPedidoProducto = BigDecimal.ZERO;
/* 305:    */       }
/* 306:317 */       BigDecimal porcientoPermitido = new BigDecimal(porcientoDespachoSuperaPedido.intValue());
/* 307:318 */       BigDecimal cantidadAdicional = porcientoPermitido.multiply(cantidadPedidoProducto).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
/* 308:319 */       BigDecimal cantidadPedidoTotal = cantidadPedidoProducto.add(cantidadAdicional).setScale(numeroDecimales.intValue(), RoundingMode.HALF_UP);
/* 309:    */       
/* 310:321 */       String key = idProducto.toString() + "-" + nombreUnidadManejo + "-" + cantidadEmbalajeDespacho;
/* 311:322 */       Object[] objeto = new Object[10];
/* 312:323 */       if (((Map)mapaProductoGaveta).containsKey(key))
/* 313:    */       {
/* 314:324 */         objeto = (Object[])((Map)mapaProductoGaveta).get(key);
/* 315:325 */         objeto[4] = cantidadUnidadDespacho;
/* 316:326 */         numeroUnidadesManejo = Long.valueOf(numeroUnidadesManejo.longValue() + ((Long)objeto[6]).longValue());
/* 317:327 */         objeto[6] = numeroUnidadesManejo;
/* 318:    */       }
/* 319:    */       else
/* 320:    */       {
/* 321:329 */         objeto[0] = codigoProducto;
/* 322:330 */         objeto[1] = nombreProducto;
/* 323:331 */         objeto[2] = cantidadEmbalajeDespacho;
/* 324:332 */         objeto[3] = cantidadPedidoProducto;
/* 325:333 */         objeto[4] = cantidadUnidadDespacho;
/* 326:334 */         objeto[5] = nombreUnidadManejo;
/* 327:335 */         objeto[6] = numeroUnidadesManejo;
/* 328:336 */         objeto[7] = porcientoDespachoSuperaPedido;
/* 329:337 */         objeto[8] = cantidadPedidoTotal;
/* 330:338 */         objeto[9] = numeroDecimales;
/* 331:    */       }
/* 332:340 */       ((Map)mapaProductoGaveta).put(key, objeto);
/* 333:    */     }
/* 334:343 */     List<Object[]> resultado = new ArrayList();
/* 335:344 */     resultado.addAll(((Map)mapaProductoGaveta).values());
/* 336:    */     
/* 337:346 */     return resultado;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.OrdenDespachoClienteDao
 * JD-Core Version:    0.7.0.1
 */