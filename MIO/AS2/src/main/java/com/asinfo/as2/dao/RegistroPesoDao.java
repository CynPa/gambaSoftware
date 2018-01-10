/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleRegistroPeso;
/*   4:    */ import com.asinfo.as2.entities.DetalleRegistroPesoLote;
/*   5:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Lote;
/*   8:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  12:    */ import com.asinfo.as2.entities.Transportista;
/*  13:    */ import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
/*  14:    */ import com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso;
/*  15:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  16:    */ import com.asinfo.as2.enumeraciones.EstadoRegistroPeso;
/*  17:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ import javax.persistence.EntityManager;
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
/*  38:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  39:    */ 
/*  40:    */ @Stateless
/*  41:    */ public class RegistroPesoDao
/*  42:    */   extends AbstractDaoAS2<RegistroPeso>
/*  43:    */ {
/*  44:    */   public RegistroPesoDao()
/*  45:    */   {
/*  46: 60 */     super(RegistroPeso.class);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<RegistroPeso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  50:    */   {
/*  51: 65 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  52: 66 */     CriteriaQuery<RegistroPeso> criteriaQuery = criteriaBuilder.createQuery(RegistroPeso.class);
/*  53: 67 */     Root<RegistroPeso> from = criteriaQuery.from(RegistroPeso.class);
/*  54:    */     
/*  55: 69 */     from.fetch("documento", JoinType.LEFT);
/*  56:    */     
/*  57: 71 */     Fetch<Object, Object> detallePedidoProveedor = from.fetch("detallePedidoProveedor", JoinType.LEFT);
/*  58: 72 */     detallePedidoProveedor.fetch("unidadCompra", JoinType.LEFT);
/*  59: 73 */     detallePedidoProveedor.fetch("pedidoProveedor", JoinType.LEFT);
/*  60:    */     
/*  61: 75 */     from.fetch("detalleTransferenciaBodega", JoinType.LEFT).fetch("movimientoInventario", JoinType.LEFT);
/*  62: 76 */     from.fetch("transferenciaBodega", JoinType.LEFT);
/*  63: 77 */     from.fetch("despachoCliente", JoinType.LEFT);
/*  64: 78 */     from.fetch("devolucionCliente", JoinType.LEFT);
/*  65: 79 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/*  66: 80 */     empresa.fetch("cliente", JoinType.LEFT);
/*  67: 81 */     empresa.fetch("proveedor", JoinType.LEFT);
/*  68:    */     
/*  69:    */ 
/*  70: 84 */     from.fetch("bodega", JoinType.LEFT);
/*  71: 85 */     from.fetch("listaRecepcionProveedor", JoinType.LEFT);
/*  72: 86 */     from.fetch("producto", JoinType.LEFT);
/*  73: 87 */     from.fetch("lote", JoinType.LEFT);
/*  74: 88 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  75: 89 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  76:    */     
/*  77: 91 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  78:    */     
/*  79: 93 */     CriteriaQuery<RegistroPeso> select = criteriaQuery.select(from);
/*  80:    */     
/*  81: 95 */     TypedQuery<RegistroPeso> typedQuery = this.em.createQuery(select);
/*  82: 96 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  83:    */     
/*  84: 98 */     return typedQuery.getResultList();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public RegistroPeso cargarDetalle(int idRegistroPeso)
/*  88:    */   {
/*  89:104 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  90:    */     
/*  91:    */ 
/*  92:107 */     CriteriaQuery<RegistroPeso> cqCabecera = criteriaBuilder.createQuery(RegistroPeso.class);
/*  93:108 */     Root<RegistroPeso> fromCabecera = cqCabecera.from(RegistroPeso.class);
/*  94:    */     
/*  95:110 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  96:111 */     documento.fetch("secuencia", JoinType.LEFT);
/*  97:112 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  98:113 */     Fetch<Object, Object> detallePedidoProveedor = fromCabecera.fetch("detallePedidoProveedor", JoinType.LEFT);
/*  99:114 */     detallePedidoProveedor.fetch("unidadCompra", JoinType.LEFT);
/* 100:115 */     detallePedidoProveedor.fetch("pedidoProveedor", JoinType.LEFT);
/* 101:116 */     detallePedidoProveedor.fetch("producto", JoinType.LEFT);
/* 102:    */     
/* 103:118 */     fromCabecera.fetch("despachoCliente", JoinType.LEFT);
/* 104:119 */     fromCabecera.fetch("motivoCastigoCalidad", JoinType.LEFT);
/* 105:120 */     fromCabecera.fetch("bodegaLiberar", JoinType.LEFT);
/* 106:    */     
/* 107:122 */     Fetch<Object, Object> detalleTransferenciaBodega = fromCabecera.fetch("detalleTransferenciaBodega", JoinType.LEFT);
/* 108:123 */     detalleTransferenciaBodega.fetch("movimientoInventario", JoinType.LEFT);
/* 109:124 */     Fetch<Object, Object> inventarioproducto = detalleTransferenciaBodega.fetch("inventarioProducto", JoinType.LEFT);
/* 110:125 */     inventarioproducto.fetch("lote", JoinType.LEFT);
/* 111:126 */     fromCabecera.fetch("transferenciaBodega", JoinType.LEFT);
/* 112:127 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/* 113:128 */     empresa.fetch("cliente", JoinType.LEFT);
/* 114:129 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 115:130 */     fromCabecera.fetch("transportista", JoinType.INNER);
/* 116:131 */     fromCabecera.fetch("chofer", JoinType.INNER);
/* 117:132 */     fromCabecera.fetch("vehiculo", JoinType.INNER).fetch("tipoVehiculo", JoinType.INNER);
/* 118:133 */     fromCabecera.fetch("ruta", JoinType.LEFT);
/* 119:134 */     fromCabecera.fetch("bodega", JoinType.LEFT);
/* 120:135 */     fromCabecera.fetch("dispositivo", JoinType.LEFT);
/* 121:136 */     fromCabecera.fetch("listaRecepcionProveedor", JoinType.LEFT);
/* 122:137 */     fromCabecera.fetch("producto", JoinType.LEFT);
/* 123:138 */     fromCabecera.fetch("lote", JoinType.LEFT);
/* 124:    */     
/* 125:140 */     Path<Integer> pathId = fromCabecera.get("idRegistroPeso");
/* 126:141 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idRegistroPeso)));
/* 127:142 */     CriteriaQuery<RegistroPeso> selectRegistroPeso = cqCabecera.select(fromCabecera);
/* 128:    */     
/* 129:144 */     RegistroPeso registroPeso = (RegistroPeso)this.em.createQuery(selectRegistroPeso).getSingleResult();
/* 130:    */     
/* 131:    */ 
/* 132:147 */     CriteriaQuery<DetalleRegistroPesoLote> cqDetalle = criteriaBuilder.createQuery(DetalleRegistroPesoLote.class);
/* 133:148 */     Root<DetalleRegistroPesoLote> fromDetalle = cqDetalle.from(DetalleRegistroPesoLote.class);
/* 134:    */     
/* 135:150 */     fromDetalle.fetch("lote", JoinType.INNER);
/* 136:151 */     fromDetalle.fetch("producto", JoinType.INNER);
/* 137:    */     
/* 138:153 */     Path<Integer> pathIdDetalle = fromDetalle.join("registroPeso").get("idRegistroPeso");
/* 139:154 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idRegistroPeso)));
/* 140:155 */     CriteriaQuery<DetalleRegistroPesoLote> selectDetalle = cqDetalle.select(fromDetalle);
/* 141:    */     
/* 142:157 */     List<DetalleRegistroPesoLote> listaDetalleRegistroPeso = this.em.createQuery(selectDetalle).getResultList();
/* 143:    */     
/* 144:159 */     registroPeso.setListaDetalleRegistroPesoLote(listaDetalleRegistroPeso);
/* 145:    */     
/* 146:    */ 
/* 147:162 */     CriteriaQuery<DetalleRegistroPeso> cqDetallePC = criteriaBuilder.createQuery(DetalleRegistroPeso.class);
/* 148:163 */     Root<DetalleRegistroPeso> fromDetallePC = cqDetallePC.from(DetalleRegistroPeso.class);
/* 149:    */     
/* 150:165 */     fromDetallePC.fetch("registroPeso", JoinType.INNER);
/* 151:166 */     Fetch<Object, Object> detallePedidoCliente = fromDetallePC.fetch("detallePedidoCliente", JoinType.LEFT);
/* 152:167 */     detallePedidoCliente.fetch("pedidoCliente", JoinType.LEFT);
/* 153:168 */     detallePedidoCliente.fetch("producto", JoinType.LEFT);
/* 154:    */     
/* 155:170 */     Fetch<Object, Object> detalleOrdenSalidaMaterial = fromDetallePC.fetch("detalleOrdenSalidaMaterial", JoinType.LEFT);
/* 156:171 */     detalleOrdenSalidaMaterial.fetch("bodega", JoinType.LEFT);
/* 157:172 */     detalleOrdenSalidaMaterial.fetch("ordenSalidaMaterial", JoinType.LEFT);
/* 158:173 */     Fetch<Object, Object> producto = detalleOrdenSalidaMaterial.fetch("producto", JoinType.LEFT);
/* 159:174 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/* 160:175 */     producto.fetch("unidadCompra", JoinType.LEFT);
/* 161:    */     
/* 162:177 */     Fetch<Object, Object> detalleFacturaCliente = fromDetallePC.fetch("detalleFacturaCliente", JoinType.LEFT);
/* 163:178 */     detalleFacturaCliente.fetch("facturaCliente", JoinType.LEFT).fetch("sucursal", JoinType.LEFT);
/* 164:179 */     detalleFacturaCliente.fetch("producto", JoinType.LEFT);
/* 165:180 */     detalleFacturaCliente.fetch("detalleDespachoCliente", JoinType.LEFT);
/* 166:181 */     detalleFacturaCliente.fetch("detalleDespachoClienteNoFacturado", JoinType.LEFT);
/* 167:    */     
/* 168:183 */     Path<Integer> pathIdDetallePC = fromDetallePC.join("registroPeso").get("idRegistroPeso");
/* 169:184 */     cqDetallePC.where(criteriaBuilder.equal(pathIdDetallePC, Integer.valueOf(idRegistroPeso)));
/* 170:185 */     CriteriaQuery<DetalleRegistroPeso> selectDetallePC = cqDetallePC.select(fromDetallePC);
/* 171:    */     
/* 172:187 */     List<DetalleRegistroPeso> listaRegistroPesoDetallePedidoCliente = this.em.createQuery(selectDetallePC).getResultList();
/* 173:    */     
/* 174:189 */     registroPeso.setListaDetalleRegistroPeso(listaRegistroPesoDetallePedidoCliente);
/* 175:    */     
/* 176:191 */     return registroPeso;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<Object[]> getReporte(int idRegistroPeso)
/* 180:    */   {
/* 181:196 */     StringBuilder sql = new StringBuilder();
/* 182:197 */     sql.append(" SELECT rp.fecha, rp.numero, emp.nombreFiscal, emp.identificacion, bg.nombre, rp.fechaCreacion, rp.fechaEntrada, rp.fechaSalida, ");
/* 183:198 */     sql.append(" ch.nombre, ch.licencia, vh.placa, transp.nombre, prod.codigo, CASE WHEN prod IS NULL THEN rp.cargaOtros ELSE prod.nombre END,");
/* 184:199 */     sql.append(" lot.codigo, rp.pesoEntrada, rp.pesoSalida, rp.pesoDestareTotal, rp.pesoNeto, rp.cantidad, ");
/* 185:200 */     sql.append(" pp.numero, pp.usuarioCambioEstado, pp.fechaCambioEstado, dpp.cantidad, ");
/* 186:    */     
/* 187:202 */     sql.append(" mi.numero, mi.fecha, mi.usuarioCreacion, bo.nombre, dtb.cantidad, ");
/* 188:    */     
/* 189:204 */     sql.append(" p.codigo, p.nombre, l.codigo");
/* 190:205 */     sql.append(" FROM RegistroPeso rp ");
/* 191:206 */     sql.append(" LEFT JOIN rp.bodega bg ");
/* 192:207 */     sql.append(" LEFT JOIN rp.detallePedidoProveedor dpp ");
/* 193:208 */     sql.append(" LEFT JOIN dpp.pedidoProveedor pp ");
/* 194:209 */     sql.append(" LEFT JOIN rp.empresa emp ");
/* 195:210 */     sql.append(" LEFT JOIN rp.transportista transp ");
/* 196:211 */     sql.append(" LEFT JOIN rp.chofer ch ");
/* 197:212 */     sql.append(" LEFT JOIN rp.vehiculo vh ");
/* 198:213 */     sql.append(" LEFT JOIN rp.producto prod ");
/* 199:214 */     sql.append(" LEFT JOIN rp.lote lot ");
/* 200:215 */     sql.append(" LEFT JOIN rp.detalleTransferenciaBodega dtb ");
/* 201:216 */     sql.append(" LEFT JOIN dtb.movimientoInventario mi ");
/* 202:217 */     sql.append(" LEFT JOIN mi.bodegaOrigen bo ");
/* 203:218 */     sql.append(" LEFT JOIN rp.despachoCliente dc");
/* 204:219 */     sql.append(" LEFT JOIN rp.listaDetalleRegistroPeso detalle");
/* 205:220 */     sql.append(" LEFT JOIN detalle.detallePedidoCliente dpc");
/* 206:221 */     sql.append(" LEFT JOIN dpc.producto p");
/* 207:222 */     sql.append(" LEFT JOIN rp.listaDetalleRegistroPesoLote drp");
/* 208:223 */     sql.append(" LEFT JOIN drp.lote l");
/* 209:224 */     sql.append(" WHERE rp.idRegistroPeso = :idRegistroPeso ");
/* 210:225 */     Query query = this.em.createQuery(sql.toString());
/* 211:226 */     query.setParameter("idRegistroPeso", Integer.valueOf(idRegistroPeso));
/* 212:227 */     return query.getResultList();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<Object[]> getReporteCalidadMateriaPrima(Date fechaDesde, Date fechaHasta, EstadoControlCalidad estado, PedidoProveedor pedidoProveedor)
/* 216:    */   {
/* 217:232 */     StringBuilder sql = new StringBuilder();
/* 218:233 */     sql.append(" SELECT rp.fecha, rp.numero, emp.nombreFiscal, emp.identificacion, ");
/* 219:234 */     sql.append(" ch.nombre, ch.licencia, vh.placa, transp.nombre, prod.codigo, prod.nombre, lot.codigo, ");
/* 220:235 */     sql.append(" rp.pesoNeto, rp.cantidad, pp.numero, dpp.cantidad, ");
/* 221:236 */     sql.append(" vc.nombre, vcrp.valorNir, ((dpp.precio * dpp.cantidad) - dpp.descuento), ");
/* 222:237 */     sql.append(" rp.estadoControlCalidad, cv.nombre, de ");
/* 223:238 */     sql.append(" FROM RegistroPeso rp ");
/* 224:239 */     sql.append(" LEFT JOIN rp.bodega bg ");
/* 225:240 */     sql.append(" LEFT JOIN rp.detallePedidoProveedor dpp ");
/* 226:241 */     sql.append(" LEFT JOIN dpp.pedidoProveedor pp ");
/* 227:242 */     sql.append(" LEFT JOIN pp.direccionEmpresa de ");
/* 228:243 */     sql.append(" LEFT JOIN FETCH de.ubicacion ub ");
/* 229:244 */     sql.append(" LEFT JOIN rp.empresa emp ");
/* 230:245 */     sql.append(" LEFT JOIN rp.transportista transp ");
/* 231:246 */     sql.append(" LEFT JOIN rp.chofer ch ");
/* 232:247 */     sql.append(" LEFT JOIN rp.vehiculo vh ");
/* 233:248 */     sql.append(" LEFT JOIN rp.producto prod ");
/* 234:249 */     sql.append(" LEFT JOIN rp.lote lot ");
/* 235:250 */     sql.append(" INNER JOIN rp.listaVariableCalidadRegistroPeso vcrp ");
/* 236:251 */     sql.append(" LEFT JOIN vcrp.variableCalidadProducto vcp ");
/* 237:252 */     sql.append(" LEFT JOIN vcp.variableCalidad vc ");
/* 238:253 */     sql.append(" LEFT JOIN vc.categoriaVariableCalidad cv ");
/* 239:    */     
/* 240:255 */     sql.append(" WHERE rp.fecha between :fechaDesde and :fechaHasta ");
/* 241:256 */     sql.append(" AND rp.tipoRegistroPeso = :tipoRegistroPesoMateriaPrima ");
/* 242:257 */     if (estado != null) {
/* 243:258 */       sql.append(" AND rp.estadoControlCalidad = :estadoControlCalidad ");
/* 244:    */     } else {
/* 245:260 */       sql.append(" AND rp.estadoControlCalidad IS NOT NULL ");
/* 246:    */     }
/* 247:262 */     if (pedidoProveedor != null) {
/* 248:263 */       sql.append(" AND pp.idPedidoProveedor = :idPedidoProveedor ");
/* 249:    */     }
/* 250:265 */     sql.append(" ORDER BY rp.fecha, rp.numero, prod.codigo, prod.nombre, vc.nombre ");
/* 251:    */     
/* 252:267 */     Query query = this.em.createQuery(sql.toString());
/* 253:268 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 254:269 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 255:270 */     query.setParameter("tipoRegistroPesoMateriaPrima", TipoRegistroPeso.INGRESO_MATERIA_PRIMA);
/* 256:271 */     if (estado != null) {
/* 257:272 */       query.setParameter("estadoControlCalidad", estado);
/* 258:    */     }
/* 259:274 */     if (pedidoProveedor != null) {
/* 260:275 */       query.setParameter("idPedidoProveedor", Integer.valueOf(pedidoProveedor.getId()));
/* 261:    */     }
/* 262:278 */     List<Object[]> lista = query.getResultList();
/* 263:279 */     Map<String, Object[]> mapaRegistroPeso = new HashMap();
/* 264:280 */     for (Object[] objects : lista)
/* 265:    */     {
/* 266:281 */       numeroRegistroPeso = (String)objects[1];
/* 267:282 */       Object[] lineaRegistroPeso = (Object[])mapaRegistroPeso.get(numeroRegistroPeso);
/* 268:283 */       if (lineaRegistroPeso == null)
/* 269:    */       {
/* 270:284 */         lineaRegistroPeso = new Object[12];
/* 271:285 */         lineaRegistroPeso[0] = objects[0];
/* 272:286 */         lineaRegistroPeso[1] = objects[1];
/* 273:287 */         lineaRegistroPeso[2] = objects[2];
/* 274:288 */         lineaRegistroPeso[3] = objects[3];
/* 275:289 */         lineaRegistroPeso[4] = objects[4];
/* 276:290 */         lineaRegistroPeso[5] = objects[5];
/* 277:291 */         lineaRegistroPeso[6] = objects[6];
/* 278:292 */         lineaRegistroPeso[7] = objects[7];
/* 279:293 */         lineaRegistroPeso[8] = objects[13];
/* 280:294 */         lineaRegistroPeso[9] = ((EstadoControlCalidad)objects[18]).getNombre();
/* 281:295 */         lineaRegistroPeso[10] = ((DireccionEmpresa)objects[20]).getDireccionCompleta();
/* 282:296 */         lineaRegistroPeso[11] = new ArrayList();
/* 283:    */       }
/* 284:298 */       List<Object[]> detalles = (ArrayList)lineaRegistroPeso[11];
/* 285:299 */       Object[] detalle = new Object[10];
/* 286:300 */       detalle[0] = objects[8];
/* 287:301 */       detalle[1] = objects[9];
/* 288:302 */       detalle[2] = objects[10];
/* 289:303 */       detalle[3] = objects[11];
/* 290:304 */       detalle[4] = objects[12];
/* 291:305 */       detalle[5] = objects[14];
/* 292:306 */       detalle[6] = objects[15];
/* 293:307 */       detalle[7] = objects[16];
/* 294:308 */       detalle[8] = objects[17];
/* 295:309 */       detalle[9] = objects[19];
/* 296:310 */       detalles.add(detalle);
/* 297:    */       
/* 298:312 */       mapaRegistroPeso.put(numeroRegistroPeso, lineaRegistroPeso);
/* 299:    */     }
/* 300:    */     String numeroRegistroPeso;
/* 301:315 */     String[] fields = { "f_codigoProducto", "f_nombreProducto", "f_loteProducto", "f_pesoNeto", "f_cantidadProducto", "f_cantidadPedido", "f_variable", "f_valorNir", "f_precioPedido", "f_categoriaVariable" };
/* 302:    */     
/* 303:317 */     List<Object[]> resultado = new ArrayList();
/* 304:318 */     for (Object[] objects : mapaRegistroPeso.values())
/* 305:    */     {
/* 306:319 */       List<Object[]> detalles = (ArrayList)objects[11];
/* 307:320 */       JRDataSource ds = new QueryResultDataSource(detalles, fields);
/* 308:321 */       objects[11] = ds;
/* 309:    */       
/* 310:323 */       resultado.add(objects);
/* 311:    */     }
/* 312:326 */     return resultado;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<RegistroPeso> obtenerListaRegistroPesoPendientesPorLiquidar(Empresa empresaTransportista)
/* 316:    */   {
/* 317:331 */     StringBuilder sql = new StringBuilder();
/* 318:332 */     sql.append(" SELECT rp ");
/* 319:333 */     sql.append(" FROM RegistroPeso rp ");
/* 320:334 */     sql.append(" INNER JOIN FETCH rp.transportista transp ");
/* 321:335 */     sql.append(" INNER JOIN FETCH transp.empresa emp ");
/* 322:336 */     sql.append(" INNER JOIN FETCH rp.chofer ch ");
/* 323:337 */     sql.append(" INNER JOIN FETCH rp.vehiculo vh ");
/* 324:338 */     sql.append(" INNER JOIN FETCH vh.tipoVehiculo tvh ");
/* 325:339 */     sql.append(" INNER JOIN FETCH rp.ruta rut ");
/* 326:    */     
/* 327:341 */     sql.append(" WHERE emp.idEmpresa = :idEmpresaTransportista ");
/* 328:342 */     sql.append(" AND rp.estado = :estadoSegundoPeso ");
/* 329:343 */     sql.append(" AND transp.indicadorPagaFlete = true ");
/* 330:344 */     sql.append(" AND rp.indicadorLiquidadoFlete = false ");
/* 331:    */     
/* 332:346 */     Query query = this.em.createQuery(sql.toString());
/* 333:347 */     query.setParameter("idEmpresaTransportista", Integer.valueOf(empresaTransportista.getId()));
/* 334:348 */     query.setParameter("estadoSegundoPeso", EstadoRegistroPeso.SEGUNDO_PESO);
/* 335:349 */     return query.getResultList();
/* 336:    */   }
/* 337:    */   
/* 338:    */   public List<Object[]> getReporteFleteTransportistas(int idOrganizacion, Date fechaDesde, Date fechaHasta, Transportista transportista, boolean indicadorLiquidados, boolean indicadorPorLiquidar)
/* 339:    */   {
/* 340:355 */     StringBuilder sql = new StringBuilder();
/* 341:356 */     sql.append(" SELECT rp.fecha, transp.identificacion, transp.nombre, CONCAT(vh.placa, ' | ', vh.marca, ' | ', vh.modelo ),");
/* 342:357 */     sql.append(" rp.numero, fp.numero, CONCAT(fpSRI.establecimiento,'-',fpSRI.puntoEmision,'-', fpSRI.numero), rp.indicadorLiquidadoFlete, rp.pesoNeto ");
/* 343:358 */     sql.append(" FROM RegistroPeso rp ");
/* 344:359 */     sql.append(" INNER JOIN rp.transportista transp ");
/* 345:    */     
/* 346:361 */     sql.append(" INNER JOIN transp.empresa empT ");
/* 347:362 */     sql.append(" INNER JOIN rp.chofer ch ");
/* 348:363 */     sql.append(" INNER JOIN rp.vehiculo vh ");
/* 349:364 */     sql.append(" LEFT JOIN rp.detalleFacturaProveedor dfp ");
/* 350:365 */     sql.append(" LEFT JOIN dfp.facturaProveedor fp ");
/* 351:366 */     sql.append(" LEFT JOIN fp.facturaProveedorSRI fpSRI ");
/* 352:    */     
/* 353:368 */     sql.append(" WHERE rp.idOrganizacion = :idOrganizacion ");
/* 354:369 */     sql.append(" AND rp.estado = :estadoSegundoPeso ");
/* 355:370 */     sql.append(" AND rp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 356:371 */     if (transportista != null) {
/* 357:372 */       sql.append(" AND transp.idTransportista = :idTransportista ");
/* 358:    */     }
/* 359:374 */     if (!indicadorLiquidados) {
/* 360:375 */       sql.append(" AND rp.indicadorLiquidadoFlete = false ");
/* 361:    */     }
/* 362:377 */     if (!indicadorPorLiquidar) {
/* 363:378 */       sql.append(" AND rp.indicadorLiquidadoFlete = true ");
/* 364:    */     }
/* 365:381 */     Query query = this.em.createQuery(sql.toString());
/* 366:382 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 367:383 */     query.setParameter("estadoSegundoPeso", EstadoRegistroPeso.SEGUNDO_PESO);
/* 368:384 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 369:385 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 370:386 */     if (transportista != null) {
/* 371:387 */       query.setParameter("idTransportista", Integer.valueOf(transportista.getId()));
/* 372:    */     }
/* 373:389 */     return query.getResultList();
/* 374:    */   }
/* 375:    */   
/* 376:    */   public List<VariableCalidadRegistroPeso> getListaVariableCalidadRegistroPeso(RegistroPeso registroPeso)
/* 377:    */   {
/* 378:394 */     StringBuilder sql = new StringBuilder();
/* 379:395 */     sql.append(" SELECT vcrp ");
/* 380:396 */     sql.append(" FROM VariableCalidadRegistroPeso vcrp ");
/* 381:397 */     sql.append(" INNER JOIN FETCH vcrp.variableCalidadProducto vcp ");
/* 382:398 */     sql.append(" INNER JOIN FETCH vcp.variableCalidad vc ");
/* 383:399 */     sql.append(" INNER JOIN FETCH vc.categoriaVariableCalidad cvc ");
/* 384:400 */     sql.append(" WHERE vcrp.registroPeso = :registroPeso ");
/* 385:401 */     Query query = this.em.createQuery(sql.toString());
/* 386:402 */     query.setParameter("registroPeso", registroPeso);
/* 387:    */     
/* 388:404 */     return query.getResultList();
/* 389:    */   }
/* 390:    */   
/* 391:    */   public List<VariableCalidadProducto> getListaVariableCalidadProducto(Producto producto)
/* 392:    */   {
/* 393:409 */     StringBuilder sql = new StringBuilder();
/* 394:410 */     sql.append(" SELECT vcp ");
/* 395:411 */     sql.append(" FROM VariableCalidadProducto vcp ");
/* 396:412 */     sql.append(" INNER JOIN FETCH vcp.variableCalidad vc ");
/* 397:413 */     sql.append(" INNER JOIN FETCH vc.categoriaVariableCalidad cvc ");
/* 398:414 */     sql.append(" WHERE vcp.producto = :producto ");
/* 399:415 */     Query query = this.em.createQuery(sql.toString());
/* 400:416 */     query.setParameter("producto", producto);
/* 401:    */     
/* 402:418 */     return query.getResultList();
/* 403:    */   }
/* 404:    */   
/* 405:    */   public List<Object[]> getReporteRegistroPesoRecepcionMateriaPrima(Date fechaDesde, Date fechaHasta, Empresa proveedor, Producto producto, Lote lote)
/* 406:    */   {
/* 407:424 */     StringBuilder sql = new StringBuilder();
/* 408:425 */     sql.append(" SELECT rp.fecha, prod.codigo, prod.nombre, rp.numero, rp.numeroGuiaRemision, ");
/* 409:426 */     sql.append("ch.nombre, rp.pesoReferencia, rp.pesoNeto,em.nombreFiscal,rp.pesoReferencia-rp.pesoNeto,l.codigo");
/* 410:427 */     sql.append(" FROM RegistroPeso rp ");
/* 411:428 */     sql.append(" LEFT JOIN rp.producto bg ");
/* 412:429 */     sql.append(" LEFT JOIN rp.chofer ch ");
/* 413:430 */     sql.append(" LEFT JOIN rp.producto prod ");
/* 414:431 */     sql.append(" LEFT JOIN rp.empresa em ");
/* 415:432 */     sql.append(" LEFT JOIN rp.lote l");
/* 416:433 */     sql.append(" WHERE rp.fecha between :fechaDesde and :fechaHasta ");
/* 417:434 */     sql.append(" AND rp.tipoRegistroPeso = :tipoRegistroPesoMateriaPrima ");
/* 418:435 */     if (proveedor != null) {
/* 419:436 */       sql.append(" AND em.idEmpresa= :idEmpresa");
/* 420:    */     }
/* 421:438 */     if (producto != null) {
/* 422:439 */       sql.append(" AND prod.idProducto= :idProducto");
/* 423:    */     }
/* 424:441 */     if (lote != null) {
/* 425:442 */       sql.append(" AND l.idLote = :idLote");
/* 426:    */     }
/* 427:444 */     sql.append(" ORDER BY rp.fecha, rp.numero, prod.codigo, prod.nombre,l.codigo");
/* 428:    */     
/* 429:446 */     Query query = this.em.createQuery(sql.toString());
/* 430:447 */     if (proveedor != null) {
/* 431:448 */       query.setParameter("idEmpresa", Integer.valueOf(proveedor.getIdEmpresa()));
/* 432:    */     }
/* 433:450 */     if (producto != null) {
/* 434:451 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 435:    */     }
/* 436:453 */     if (lote != null) {
/* 437:454 */       query.setParameter("idLote", Integer.valueOf(lote.getIdLote()));
/* 438:    */     }
/* 439:457 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 440:458 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 441:459 */     query.setParameter("tipoRegistroPesoMateriaPrima", TipoRegistroPeso.INGRESO_MATERIA_PRIMA);
/* 442:    */     
/* 443:461 */     return query.getResultList();
/* 444:    */   }
/* 445:    */   
/* 446:    */   public List<Object[]> getReporteRegistroPesoTransferencia(Date fechaDesde, Date fechaHasta, Producto producto, MovimientoInventario transferencia, Lote lote, TipoRegistroPeso tipoRegistroPeso)
/* 447:    */   {
/* 448:469 */     StringBuilder sql = new StringBuilder();
/* 449:470 */     sql.append(" SELECT rp.fecha, prod.codigo, prod.nombre, rp.numero, rp.numeroGuiaRemision, ");
/* 450:471 */     sql.append(" ch.nombre, rp.pesoReferencia, rp.pesoNeto, rp.pesoEntrada, rp.pesoSalida, rp.pesoDestareTotal, l.codigo, trans.numero, rp.tipoRegistroPeso ");
/* 451:472 */     sql.append(" FROM RegistroPeso rp ");
/* 452:473 */     sql.append(" LEFT JOIN rp.producto bg ");
/* 453:474 */     sql.append(" LEFT JOIN rp.chofer ch ");
/* 454:475 */     sql.append(" LEFT JOIN rp.producto prod ");
/* 455:476 */     sql.append(" LEFT JOIN rp.transferenciaBodega trans ");
/* 456:477 */     sql.append(" LEFT JOIN rp.lote l");
/* 457:478 */     sql.append(" LEFT JOIN rp.detalleTransferenciaBodega dtb");
/* 458:    */     
/* 459:480 */     sql.append(" WHERE rp.fecha between :fechaDesde and :fechaHasta ");
/* 460:481 */     sql.append(" AND rp.tipoRegistroPeso = :tipoRegistroPesoMateriaPrima ");
/* 461:482 */     if (producto != null) {
/* 462:483 */       sql.append(" AND prod.idProducto= :idProducto");
/* 463:    */     }
/* 464:485 */     if (lote != null) {
/* 465:486 */       sql.append(" AND l.idLote = :idLote");
/* 466:    */     }
/* 467:488 */     if (transferencia != null) {
/* 468:489 */       sql.append(" AND trans.idMovimientoInventario=:idMovimientoInventario");
/* 469:    */     }
/* 470:491 */     sql.append(" ORDER BY rp.fecha, rp.numero, prod.codigo, prod.nombre");
/* 471:    */     
/* 472:493 */     Query query = this.em.createQuery(sql.toString());
/* 473:494 */     if (transferencia != null) {
/* 474:495 */       query.setParameter("idMovimientoInventario", Integer.valueOf(transferencia.getIdMovimientoInventario()));
/* 475:    */     }
/* 476:497 */     if (producto != null) {
/* 477:498 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 478:    */     }
/* 479:500 */     if (lote != null) {
/* 480:501 */       query.setParameter("idLote", Integer.valueOf(lote.getIdLote()));
/* 481:    */     }
/* 482:504 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 483:505 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 484:506 */     query.setParameter("tipoRegistroPesoMateriaPrima", tipoRegistroPeso);
/* 485:    */     
/* 486:508 */     return query.getResultList();
/* 487:    */   }
/* 488:    */   
/* 489:    */   public List<Object[]> getReporteProductoCuarentena(Date fechaHasta, Empresa proveedor, Producto producto, PedidoProveedor pedidoProveedor)
/* 490:    */   {
/* 491:514 */     StringBuilder sql = new StringBuilder();
/* 492:515 */     sql.append(" SELECT rp.fecha, rp.numero , pp.numero, em.identificacion, em.nombreFiscal, prod.codigo, prod.nombre,  ");
/* 493:516 */     sql.append(" dpp.cantidad, rp.pesoNeto");
/* 494:517 */     sql.append(" FROM RegistroPeso rp ");
/* 495:518 */     sql.append(" LEFT JOIN  rp.producto prod ");
/* 496:519 */     sql.append(" LEFT JOIN  rp.detallePedidoProveedor dpp ");
/* 497:520 */     sql.append(" LEFT JOIN  dpp.pedidoProveedor pp ");
/* 498:521 */     sql.append(" LEFT JOIN  pp.empresa em ");
/* 499:522 */     sql.append(" LEFT JOIN  rp.listaRecepcionProveedor lrp ");
/* 500:523 */     sql.append(" WHERE rp.fecha <= :fechaHasta ");
/* 501:524 */     sql.append(" AND rp.tipoRegistroPeso = :tipoRegistroPesoMateriaPrima ");
/* 502:525 */     sql.append(" AND prod.indicadorControlCalidad = true ");
/* 503:526 */     sql.append(" AND lrp.idRecepcionProveedor IS NULL");
/* 504:527 */     sql.append(" AND rp.estado = :estadoSegundoPeso ");
/* 505:529 */     if (proveedor != null) {
/* 506:530 */       sql.append(" AND em.idEmpresa= :idEmpresa");
/* 507:    */     }
/* 508:532 */     if (producto != null) {
/* 509:533 */       sql.append(" AND prod.idProducto= :idProducto");
/* 510:    */     }
/* 511:535 */     if (pedidoProveedor != null) {
/* 512:536 */       sql.append(" AND pp.idPedidoProveedor= :idPedidoProveedor");
/* 513:    */     }
/* 514:538 */     sql.append(" ORDER BY rp.fecha, rp.numero, prod.codigo, prod.nombre");
/* 515:    */     
/* 516:540 */     Query query = this.em.createQuery(sql.toString());
/* 517:541 */     if (proveedor != null) {
/* 518:542 */       query.setParameter("idEmpresa", Integer.valueOf(proveedor.getIdEmpresa()));
/* 519:    */     }
/* 520:544 */     if (producto != null) {
/* 521:545 */       query.setParameter("idProducto", Integer.valueOf(producto.getIdProducto()));
/* 522:    */     }
/* 523:547 */     if (pedidoProveedor != null) {
/* 524:548 */       query.setParameter("idPedidoProveedor", Integer.valueOf(pedidoProveedor.getIdPedidoProveedor()));
/* 525:    */     }
/* 526:551 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 527:552 */     query.setParameter("tipoRegistroPesoMateriaPrima", TipoRegistroPeso.INGRESO_MATERIA_PRIMA);
/* 528:553 */     query.setParameter("estadoSegundoPeso", EstadoRegistroPeso.SEGUNDO_PESO);
/* 529:    */     
/* 530:555 */     return query.getResultList();
/* 531:    */   }
/* 532:    */   
/* 533:    */   public List<RegistroPeso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 534:    */   {
/* 535:567 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 536:568 */     CriteriaQuery<RegistroPeso> cq = cb.createQuery(RegistroPeso.class);
/* 537:569 */     Root<RegistroPeso> from = cq.from(RegistroPeso.class);
/* 538:    */     
/* 539:571 */     from.fetch("transferenciaBodega", JoinType.LEFT);
/* 540:    */     
/* 541:    */ 
/* 542:574 */     agregarFiltros(filtros, cb, cq, from);
/* 543:    */     
/* 544:    */ 
/* 545:577 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 546:    */     
/* 547:    */ 
/* 548:580 */     TypedQuery<RegistroPeso> typedQuery = this.em.createQuery(cq.select(from));
/* 549:581 */     agregarPaginacion(0, 20, typedQuery);
/* 550:    */     
/* 551:583 */     return this.em.createQuery(cq).getResultList();
/* 552:    */   }
/* 553:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RegistroPesoDao
 * JD-Core Version:    0.7.0.1
 */