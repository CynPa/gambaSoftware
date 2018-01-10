/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   8:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.RecepcionProveedor;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.ejb.Stateless;
/*  22:    */ import javax.persistence.EntityManager;
/*  23:    */ import javax.persistence.NoResultException;
/*  24:    */ import javax.persistence.Query;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.persistence.TypedQuery;
/*  27:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  28:    */ import javax.persistence.criteria.CriteriaQuery;
/*  29:    */ import javax.persistence.criteria.Expression;
/*  30:    */ import javax.persistence.criteria.Fetch;
/*  31:    */ import javax.persistence.criteria.Join;
/*  32:    */ import javax.persistence.criteria.JoinType;
/*  33:    */ import javax.persistence.criteria.Path;
/*  34:    */ import javax.persistence.criteria.Predicate;
/*  35:    */ import javax.persistence.criteria.Root;
/*  36:    */ 
/*  37:    */ @Stateless
/*  38:    */ public class RecepcionProveedorDao
/*  39:    */   extends AbstractDaoAS2<RecepcionProveedor>
/*  40:    */ {
/*  41:    */   @EJB
/*  42:    */   private SerieInventarioProductoDao serieInventarioProductoDao;
/*  43:    */   
/*  44:    */   public RecepcionProveedorDao()
/*  45:    */   {
/*  46: 62 */     super(RecepcionProveedor.class);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public RecepcionProveedor cargarDetalle(RecepcionProveedor rp)
/*  50:    */   {
/*  51: 71 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  52:    */     
/*  53:    */ 
/*  54: 74 */     CriteriaQuery<RecepcionProveedor> cqCabecera = criteriaBuilder.createQuery(RecepcionProveedor.class);
/*  55: 75 */     Root<RecepcionProveedor> fromCabecera = cqCabecera.from(RecepcionProveedor.class);
/*  56:    */     
/*  57: 77 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  58: 78 */     documento.fetch("secuencia", JoinType.LEFT);
/*  59: 79 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  60:    */     
/*  61: 81 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  62: 82 */     fromCabecera.fetch("empresa", JoinType.LEFT);
/*  63: 83 */     fromCabecera.fetch("asiento", JoinType.LEFT);
/*  64: 84 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*  65: 85 */     fromCabecera.fetch("registroPeso", JoinType.LEFT);
/*  66:    */     
/*  67: 87 */     Path<Integer> pathId = fromCabecera.get("idRecepcionProveedor");
/*  68: 88 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(rp.getIdRecepcionProveedor())));
/*  69: 89 */     CriteriaQuery<RecepcionProveedor> selectRecepcion = cqCabecera.select(fromCabecera);
/*  70:    */     
/*  71: 91 */     RecepcionProveedor recepcionProveedor = (RecepcionProveedor)this.em.createQuery(selectRecepcion).getSingleResult();
/*  72:    */     
/*  73:    */ 
/*  74: 94 */     CriteriaQuery<DetalleRecepcionProveedor> cqDetalle = criteriaBuilder.createQuery(DetalleRecepcionProveedor.class);
/*  75: 95 */     Root<DetalleRecepcionProveedor> fromDetalle = cqDetalle.from(DetalleRecepcionProveedor.class);
/*  76:    */     
/*  77: 97 */     fromDetalle.fetch("unidadCompra", JoinType.LEFT);
/*  78: 98 */     fromDetalle.fetch("lote", JoinType.LEFT);
/*  79: 99 */     fromDetalle.fetch("transformacionAutomatica", JoinType.LEFT);
/*  80:    */     
/*  81:101 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  82:102 */     producto.fetch("unidad", JoinType.LEFT);
/*  83:103 */     fromDetalle.fetch("bodega", JoinType.LEFT);
/*  84:104 */     Fetch<Object, Object> detallePedidoProveedor = fromDetalle.fetch("detallePedidoProveedor", JoinType.LEFT);
/*  85:105 */     detallePedidoProveedor.fetch("pedidoProveedor", JoinType.LEFT);
/*  86:106 */     Join<Object, Object> inventarioProducto = (Join)fromDetalle.fetch("inventarioProducto", JoinType.LEFT);
/*  87:107 */     inventarioProducto.fetch("lote", JoinType.LEFT);
/*  88:    */     
/*  89:109 */     Fetch<Object, Object> detalleFacturaProveedor = fromDetalle.fetch("detalleFacturaProveedor", JoinType.LEFT);
/*  90:110 */     Fetch<Object, Object> facturaProveedor = detalleFacturaProveedor.fetch("facturaProveedor", JoinType.LEFT);
/*  91:111 */     facturaProveedor.fetch("documento", JoinType.LEFT);
/*  92:    */     
/*  93:113 */     Path<Integer> pathIdDetalle = fromDetalle.join("recepcionProveedor").get("idRecepcionProveedor");
/*  94:    */     
/*  95:115 */     List<Expression<?>> listaExpresiones = new ArrayList();
/*  96:116 */     listaExpresiones.add(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(rp.getIdRecepcionProveedor())));
/*  97:117 */     if (!recepcionProveedor.getEstado().equals(Estado.ELABORADO)) {
/*  98:119 */       listaExpresiones.add(criteriaBuilder.equal(inventarioProducto.get("fecha"), rp.getFecha()));
/*  99:    */     }
/* 100:121 */     cqDetalle.where((Predicate[])listaExpresiones.toArray(new Predicate[listaExpresiones.size()]));
/* 101:122 */     CriteriaQuery<DetalleRecepcionProveedor> selectDetalleRecepcion = cqDetalle.select(fromDetalle);
/* 102:    */     
/* 103:124 */     List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = this.em.createQuery(selectDetalleRecepcion).getResultList();
/* 104:125 */     recepcionProveedor.setListaDetalleRecepcionProveedor(listaDetalleRecepcionProveedor);
/* 105:127 */     for (DetalleRecepcionProveedor detalle : listaDetalleRecepcionProveedor)
/* 106:    */     {
/* 107:128 */       if (detalle.getProducto().getIndicadorSerie().booleanValue())
/* 108:    */       {
/* 109:130 */         this.em.detach(detalle);
/* 110:131 */         this.serieInventarioProductoDao.cargarDetalle(detalle.getInventarioProducto());
/* 111:    */       }
/* 112:134 */       CriteriaQuery<LecturaBalanza> cqLecturaBalanza = criteriaBuilder.createQuery(LecturaBalanza.class);
/* 113:135 */       Root<LecturaBalanza> fromLecturaBalanza = cqLecturaBalanza.from(LecturaBalanza.class);
/* 114:136 */       fromLecturaBalanza.fetch("producto", JoinType.INNER);
/* 115:137 */       fromLecturaBalanza.fetch("unidadManejo", JoinType.LEFT);
/* 116:138 */       fromLecturaBalanza.fetch("pallet", JoinType.LEFT);
/* 117:139 */       fromLecturaBalanza.fetch("detalleRecepcionProveedor", JoinType.INNER);
/* 118:    */       
/* 119:141 */       cqLecturaBalanza.where(criteriaBuilder.equal(fromLecturaBalanza.join("detalleRecepcionProveedor"), detalle));
/* 120:142 */       CriteriaQuery<LecturaBalanza> selectLecturaBalanza = cqLecturaBalanza.select(fromLecturaBalanza);
/* 121:    */       
/* 122:144 */       List<LecturaBalanza> listaLecturaBalanza = this.em.createQuery(selectLecturaBalanza).getResultList();
/* 123:145 */       detalle.setListaLecturaBalanza(listaLecturaBalanza);
/* 124:    */     }
/* 125:151 */     Object cqFactura = criteriaBuilder.createQuery(FacturaProveedor.class);
/* 126:152 */     Root<FacturaProveedor> fromFactura = ((CriteriaQuery)cqFactura).from(FacturaProveedor.class);
/* 127:    */     
/* 128:154 */     Path<Integer> pathIdRecepcion = fromFactura.join("recepcionProveedor").get("idRecepcionProveedor");
/* 129:155 */     ((CriteriaQuery)cqFactura).where(criteriaBuilder.equal(pathIdRecepcion, Integer.valueOf(rp.getId())));
/* 130:156 */     CriteriaQuery<FacturaProveedor> selectFacturaProveedor = ((CriteriaQuery)cqFactura).select(fromFactura);
/* 131:    */     
/* 132:158 */     List<FacturaProveedor> listaFacturaProveedor = this.em.createQuery(selectFacturaProveedor).setMaxResults(1).getResultList();
/* 133:159 */     if (listaFacturaProveedor.size() > 0) {
/* 134:160 */       recepcionProveedor.setFacturaProveedor((FacturaProveedor)listaFacturaProveedor.get(0));
/* 135:    */     }
/* 136:165 */     return recepcionProveedor;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public RecepcionProveedor cargarDetalleAFacturar(int idRecepcionProveedor)
/* 140:    */   {
/* 141:174 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 142:    */     
/* 143:    */ 
/* 144:177 */     CriteriaQuery<RecepcionProveedor> cqCabecera = criteriaBuilder.createQuery(RecepcionProveedor.class);
/* 145:178 */     Root<RecepcionProveedor> fromCabecera = cqCabecera.from(RecepcionProveedor.class);
/* 146:179 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/* 147:180 */     fromCabecera.fetch("registroPeso", JoinType.LEFT);
/* 148:    */     
/* 149:182 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/* 150:183 */     empresa.fetch("tipoIdentificacion", JoinType.LEFT);
/* 151:184 */     Fetch<Object, Object> proveedor = empresa.fetch("proveedor", JoinType.LEFT);
/* 152:185 */     proveedor.fetch("condicionPago", JoinType.LEFT);
/* 153:186 */     proveedor.fetch("listaPrecios", JoinType.LEFT);
/* 154:    */     
/* 155:188 */     Fetch<Object, Object> pedido = fromCabecera.fetch("pedidoProveedor", JoinType.LEFT);
/* 156:189 */     Fetch<Object, Object> direccion = pedido.fetch("direccionEmpresa", JoinType.LEFT);
/* 157:190 */     direccion.fetch("ubicacion", JoinType.LEFT);
/* 158:    */     
/* 159:192 */     pedido.fetch("condicionPago", JoinType.LEFT);
/* 160:193 */     pedido.fetch("personaResponsable", JoinType.LEFT);
/* 161:    */     
/* 162:195 */     Path<Integer> pathId = fromCabecera.get("idRecepcionProveedor");
/* 163:196 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idRecepcionProveedor)));
/* 164:197 */     CriteriaQuery<RecepcionProveedor> selectRecepcion = cqCabecera.select(fromCabecera);
/* 165:    */     
/* 166:199 */     RecepcionProveedor recepcionProveedor = (RecepcionProveedor)this.em.createQuery(selectRecepcion).getSingleResult();
/* 167:    */     
/* 168:    */ 
/* 169:202 */     CriteriaQuery<DetalleRecepcionProveedor> cqDetalle = criteriaBuilder.createQuery(DetalleRecepcionProveedor.class);
/* 170:203 */     Root<DetalleRecepcionProveedor> fromDetalle = cqDetalle.from(DetalleRecepcionProveedor.class);
/* 171:    */     
/* 172:205 */     fromDetalle.fetch("unidadCompra", JoinType.LEFT);
/* 173:    */     
/* 174:207 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/* 175:208 */     Fetch<Object, Object> detalleFacturaProveedor = fromDetalle.fetch("detalleFacturaProveedor", JoinType.LEFT);
/* 176:209 */     detalleFacturaProveedor.fetch("facturaProveedor", JoinType.LEFT);
/* 177:210 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/* 178:211 */     fromDetalle.fetch("detallePedidoProveedor", JoinType.LEFT);
/* 179:212 */     fromDetalle.fetch("inventarioProducto", JoinType.LEFT);
/* 180:    */     
/* 181:214 */     Path<Integer> pathIdDetalle = fromDetalle.join("recepcionProveedor").get("idRecepcionProveedor");
/* 182:215 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idRecepcionProveedor)));
/* 183:216 */     CriteriaQuery<DetalleRecepcionProveedor> selectDetalleRecepcion = cqDetalle.select(fromDetalle);
/* 184:    */     
/* 185:218 */     List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = this.em.createQuery(selectDetalleRecepcion).getResultList();
/* 186:219 */     recepcionProveedor.setListaDetalleRecepcionProveedor(listaDetalleRecepcionProveedor);
/* 187:    */     
/* 188:221 */     return recepcionProveedor;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<DetalleRecepcionProveedor> cargarDetalleRecepcion(int idRecepcionProveedor)
/* 192:    */   {
/* 193:233 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 194:234 */     CriteriaQuery<DetalleRecepcionProveedor> cq = criteriaBuilder.createQuery(DetalleRecepcionProveedor.class);
/* 195:235 */     Root<DetalleRecepcionProveedor> from = cq.from(DetalleRecepcionProveedor.class);
/* 196:    */     
/* 197:237 */     from.fetch("detallePedidoProveedor", JoinType.LEFT);
/* 198:238 */     from.fetch("detalleFacturaProveedor", JoinType.LEFT);
/* 199:    */     
/* 200:240 */     Path<Integer> pathId = from.join("recepcionProveedor").get("idRecepcionProveedor");
/* 201:241 */     cq.where(criteriaBuilder.equal(pathId, Integer.valueOf(idRecepcionProveedor)));
/* 202:242 */     CriteriaQuery<DetalleRecepcionProveedor> select = cq.select(from);
/* 203:    */     
/* 204:244 */     return this.em.createQuery(select).getResultList();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public List<RecepcionProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 208:    */   {
/* 209:255 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 210:256 */     CriteriaQuery<RecepcionProveedor> criteriaQuery = criteriaBuilder.createQuery(RecepcionProveedor.class);
/* 211:257 */     Root<RecepcionProveedor> from = criteriaQuery.from(RecepcionProveedor.class);
/* 212:    */     
/* 213:259 */     Fetch<Object, Object> empresa = from.fetch("empresa", JoinType.LEFT);
/* 214:260 */     from.fetch("pedidoProveedor", JoinType.LEFT);
/* 215:261 */     from.fetch("proyecto", JoinType.LEFT);
/* 216:262 */     from.fetch("registroPeso", JoinType.LEFT);
/* 217:263 */     from.fetch("documento", JoinType.LEFT);
/* 218:264 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/* 219:265 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/* 220:    */     
/* 221:    */ 
/* 222:268 */     empresa.fetch("cliente", JoinType.LEFT);
/* 223:269 */     empresa.fetch("proveedor", JoinType.LEFT);
/* 224:270 */     empresa.fetch("empleado", JoinType.LEFT);
/* 225:    */     
/* 226:272 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 227:273 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 228:    */     
/* 229:275 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 230:    */     
/* 231:277 */     CriteriaQuery<RecepcionProveedor> select = criteriaQuery.select(from);
/* 232:    */     
/* 233:279 */     TypedQuery<RecepcionProveedor> typedQuery = this.em.createQuery(select);
/* 234:280 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 235:    */     
/* 236:282 */     return typedQuery.getResultList();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<DetalleInterfazContable> getRedepcionProveedorCCMXRIC(Integer idRecepcionProveedor)
/* 240:    */     throws ExcepcionAS2Financiero
/* 241:    */   {
/* 242:    */     try
/* 243:    */     {
/* 244:296 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(sc.cuentaContableMercaderiaPorRecibir.idCuentaContable,\tem.nombreFiscal,CONCAT(do.nombre, ' #', rp.numero),'',ROUND(-SUM(ip.costo),2) )\tFROM DetalleRecepcionProveedor drp\tINNER JOIN drp.recepcionProveedor rp\tINNER JOIN rp.documento do\tINNER JOIN rp.empresa em\tINNER JOIN drp.producto pr \tINNER JOIN drp.inventarioProducto ip\tINNER JOIN pr.subcategoriaProducto sc WHERE pr.tipoProducto=:tipoProducto AND rp.idRecepcionProveedor=:idRecepcionProveedor\tGROUP BY sc.cuentaContableMercaderiaPorRecibir.idCuentaContable, em.nombreFiscal,CONCAT(do.nombre, ' #', rp.numero)");
/* 245:    */       
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:303 */       query.setParameter("idRecepcionProveedor", idRecepcionProveedor);
/* 252:304 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 253:305 */       return query.getResultList();
/* 254:    */     }
/* 255:    */     catch (IllegalArgumentException e)
/* 256:    */     {
/* 257:308 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableMercaderiaPorRecibir");
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<DetalleInterfazContable> getRedepcionProveedorCCIIC(Integer idRecepcionProveedor)
/* 262:    */     throws ExcepcionAS2Financiero
/* 263:    */   {
/* 264:    */     try
/* 265:    */     {
/* 266:322 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(sc.cuentaContableInventario.idCuentaContable,\tem.nombreFiscal,CONCAT(do.nombre, ' #', rp.numero),'',ROUND(SUM(ip.costo),2) )\tFROM DetalleRecepcionProveedor drp\tINNER JOIN drp.recepcionProveedor rp\tINNER JOIN rp.documento do\tINNER JOIN rp.empresa em\tINNER JOIN drp.producto pr \tINNER JOIN drp.inventarioProducto ip\tINNER JOIN pr.subcategoriaProducto sc WHERE pr.tipoProducto=:tipoProducto AND rp.idRecepcionProveedor=:idRecepcionProveedor\tGROUP BY sc.cuentaContableInventario.idCuentaContable, em.nombreFiscal,CONCAT(do.nombre, ' #', rp.numero)");
/* 267:    */       
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:329 */       query.setParameter("idRecepcionProveedor", idRecepcionProveedor);
/* 274:330 */       query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 275:331 */       return query.getResultList();
/* 276:    */     }
/* 277:    */     catch (IllegalArgumentException e)
/* 278:    */     {
/* 279:334 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableInventario");
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void actualizarEstado(Integer idRecepcionProveedor, Estado estado)
/* 284:    */   {
/* 285:340 */     Query query = this.em.createQuery("UPDATE RecepcionProveedor rp SET rp.estado=:estado WHERE rp.idRecepcionProveedor=:idRecepcionProveedor");
/* 286:341 */     query.setParameter("idRecepcionProveedor", idRecepcionProveedor);
/* 287:342 */     query.setParameter("estado", estado);
/* 288:343 */     query.executeUpdate();
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<DetalleInterfazContable> getRedepcionProveedorCCFPIIC(int idCuentaContableImportacion, RecepcionProveedor rp)
/* 292:    */   {
/* 293:354 */     StringBuilder sql = new StringBuilder();
/* 294:355 */     sql.append(" SELECT new DetalleInterfazContable(" + idCuentaContableImportacion + ", em.nombreFiscal, CONCAT(do.nombre, ' #', rp.numero), '', ROUND(-SUM(ip.costo),2) )");
/* 295:    */     
/* 296:357 */     sql.append(" FROM DetalleRecepcionProveedor drp");
/* 297:358 */     sql.append(" INNER JOIN drp.recepcionProveedor rp");
/* 298:359 */     sql.append(" INNER JOIN rp.documento do");
/* 299:360 */     sql.append(" INNER JOIN rp.empresa em");
/* 300:361 */     sql.append(" INNER JOIN drp.producto pr ");
/* 301:362 */     sql.append(" INNER JOIN drp.inventarioProducto ip");
/* 302:363 */     sql.append(" INNER JOIN pr.subcategoriaProducto sc");
/* 303:364 */     sql.append(" WHERE pr.tipoProducto=:tipoProducto");
/* 304:365 */     sql.append(" AND rp.idRecepcionProveedor=:idRecepcionProveedor");
/* 305:366 */     sql.append(" AND ip.fecha = :fecha");
/* 306:367 */     sql.append(" GROUP BY em.nombreFiscal, CONCAT(do.nombre, ' #', rp.numero)");
/* 307:    */     
/* 308:369 */     Query query = this.em.createQuery(sql.toString());
/* 309:370 */     query.setParameter("idRecepcionProveedor", Integer.valueOf(rp.getIdRecepcionProveedor()));
/* 310:371 */     query.setParameter("fecha", rp.getFecha(), TemporalType.DATE);
/* 311:372 */     query.setParameter("tipoProducto", TipoProducto.ARTICULO);
/* 312:373 */     return query.getResultList();
/* 313:    */   }
/* 314:    */   
/* 315:    */   public RecepcionProveedor buscarPorFacturaProveedor(int idFacturaProveedor)
/* 316:    */   {
/* 317:384 */     Query query = this.em.createQuery("SELECT r FROM FacturaProveedor f INNER JOIN f.recepcionProveedor r WHERE f.idFacturaProveedor = :idFacturaProveedor");
/* 318:385 */     query.setParameter("idFacturaProveedor", Integer.valueOf(idFacturaProveedor));
/* 319:386 */     query.setMaxResults(1);
/* 320:    */     try
/* 321:    */     {
/* 322:389 */       return (RecepcionProveedor)query.getSingleResult();
/* 323:    */     }
/* 324:    */     catch (NoResultException e) {}
/* 325:391 */     return null;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public RecepcionProveedor buscarRecepcionPorNumero(String numero)
/* 329:    */   {
/* 330:403 */     StringBuilder sql = new StringBuilder();
/* 331:404 */     sql.append("SELECT d FROM DetalleRecepcionProveedor d ");
/* 332:405 */     sql.append(" LEFT OUTER JOIN d.inventarioProducto i ");
/* 333:406 */     sql.append(" LEFT OUTER JOIN i.lote lo ");
/* 334:407 */     sql.append(" WHERE d.recepcionProveedor.numero = :numero ");
/* 335:408 */     sql.append(" AND lo.indicadorMovimientoInterno = true ORDER BY lo.codigo");
/* 336:    */     
/* 337:410 */     Query query = this.em.createQuery(sql.toString());
/* 338:411 */     query.setParameter("numero", numero);
/* 339:    */     try
/* 340:    */     {
/* 341:414 */       List<DetalleRecepcionProveedor> lista = query.getResultList();
/* 342:415 */       if (lista.size() == 0) {
/* 343:416 */         return null;
/* 344:    */       }
/* 345:418 */       RecepcionProveedor recepcionProveedor = null;
/* 346:419 */       Iterator localIterator = lista.iterator();
/* 347:419 */       if (localIterator.hasNext())
/* 348:    */       {
/* 349:419 */         DetalleRecepcionProveedor detalle = (DetalleRecepcionProveedor)localIterator.next();
/* 350:420 */         recepcionProveedor = detalle.getRecepcionProveedor();
/* 351:    */       }
/* 352:424 */       return (RecepcionProveedor)cargarDetalle(recepcionProveedor.getIdRecepcionProveedor());
/* 353:    */     }
/* 354:    */     catch (NoResultException e) {}
/* 355:426 */     return null;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public List<Object[]> getDatosImpresionEtiquetaLote(int idOrganizacion, Documento documento, String numero, String loteDesde, String loteHasta, boolean indicadoriImprimirPorUnidad, int numeroAtributos)
/* 359:    */   {
/* 360:433 */     String provedor = documento.getDocumentoBase() == DocumentoBase.RECEPCION_BODEGA ? " e.nombreFiscal" : "''";
/* 361:    */     
/* 362:435 */     StringBuilder sql = new StringBuilder();
/* 363:436 */     sql.append(" SELECT r.numero, r.fecha, lo.codigo, p.nombreComercial, d.cantidad,");
/* 364:437 */     sql.append(" u.nombre, p.descripcion, " + provedor + ", p.nombre, u.codigo,p.codigo, u.numeroDecimales, (d.cantidad * 0), r.fechaCreacion,");
/* 365:439 */     if (documento.getDocumentoBase() == DocumentoBase.INGRESO_PRODUCCION) {
/* 366:440 */       for (int i = 1; i <= numeroAtributos; i++)
/* 367:    */       {
/* 368:441 */         sql.append(" at" + i + ".codigo, ");
/* 369:442 */         sql.append(" at" + i + ".nombre, ");
/* 370:443 */         sql.append(" vat" + i + ".codigo, ");
/* 371:444 */         sql.append(" vat" + i + ".nombre,");
/* 372:    */       }
/* 373:    */     }
/* 374:447 */     sql = new StringBuilder(sql.toString().substring(0, sql.toString().length() - 1));
/* 375:449 */     if (documento.getDocumentoBase() == DocumentoBase.RECEPCION_BODEGA)
/* 376:    */     {
/* 377:450 */       sql.append(" FROM DetalleRecepcionProveedor d ");
/* 378:    */     }
/* 379:452 */     else if ((documento.getDocumentoBase() == DocumentoBase.AJUSTE_INVENTARIO) || (documento.getDocumentoBase() == DocumentoBase.TRANSFERENCIA_BODEGA) || (documento.getDocumentoBase() == DocumentoBase.INGRESO_PRODUCCION))
/* 380:    */     {
/* 381:453 */       sql.append(" FROM DetalleMovimientoInventario d ");
/* 382:454 */       sql.append(" JOIN d.movimientoInventario r ");
/* 383:    */     }
/* 384:456 */     else if (documento.getDocumentoBase() == DocumentoBase.DEVOLUCION_CLIENTE)
/* 385:    */     {
/* 386:457 */       sql.append(" FROM DetalleFacturaCliente d ");
/* 387:458 */       sql.append(" JOIN d.facturaCliente r ");
/* 388:    */     }
/* 389:461 */     sql.append(" JOIN d.producto p ");
/* 390:462 */     sql.append(" JOIN p.unidad u");
/* 391:464 */     if (provedor.length() > 5)
/* 392:    */     {
/* 393:465 */       sql.append(" JOIN d.recepcionProveedor r ");
/* 394:466 */       sql.append(" JOIN r.empresa e ");
/* 395:    */     }
/* 396:468 */     sql.append(" LEFT JOIN d.inventarioProducto i ");
/* 397:469 */     if (indicadoriImprimirPorUnidad) {
/* 398:470 */       sql.append(" LEFT  JOIN i.lote lo ");
/* 399:    */     } else {
/* 400:472 */       sql.append(" INNER JOIN i.lote lo ");
/* 401:    */     }
/* 402:474 */     if (documento.getDocumentoBase() == DocumentoBase.INGRESO_PRODUCCION) {
/* 403:475 */       for (int i = 1; i <= numeroAtributos; i++)
/* 404:    */       {
/* 405:476 */         sql.append(" LEFT JOIN lo.atributo" + i + " at" + i);
/* 406:477 */         sql.append(" LEFT JOIN lo.valorAtributo" + i + " vat" + i);
/* 407:    */       }
/* 408:    */     }
/* 409:480 */     sql.append(" WHERE r.idOrganizacion = :idOrganizacion ");
/* 410:481 */     sql.append(" AND r.documento = :documento ");
/* 411:482 */     sql.append(" AND r.numero = :numero ");
/* 412:484 */     if ((loteDesde != null) && (!loteDesde.isEmpty())) {
/* 413:485 */       sql.append(" AND lo.codigo >= :loteDesde");
/* 414:    */     }
/* 415:488 */     if ((loteHasta != null) && (!loteHasta.isEmpty())) {
/* 416:489 */       sql.append(" AND lo.codigo <= :loteHasta");
/* 417:    */     }
/* 418:492 */     sql.append(" ORDER BY lo.codigo");
/* 419:    */     
/* 420:494 */     Query query = this.em.createQuery(sql.toString());
/* 421:495 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 422:496 */     query.setParameter("documento", documento);
/* 423:497 */     query.setParameter("numero", numero);
/* 424:499 */     if ((loteDesde != null) && (!loteDesde.isEmpty())) {
/* 425:500 */       query.setParameter("loteDesde", loteDesde);
/* 426:    */     }
/* 427:503 */     if ((loteHasta != null) && (!loteHasta.isEmpty())) {
/* 428:504 */       query.setParameter("loteHasta", loteHasta);
/* 429:    */     }
/* 430:507 */     return query.getResultList();
/* 431:    */   }
/* 432:    */   
/* 433:    */   public List<DetalleInterfazContableProceso> getInterfazRecepcionProveedorDimensiones(RecepcionProveedor rp, TipoProducto tipoProducto)
/* 434:    */   {
/* 435:523 */     return getInterfazRecepcionProveedorDimensiones(rp, tipoProducto, null);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public List<DetalleInterfazContableProceso> getInterfazRecepcionProveedorDimensiones(RecepcionProveedor rp, TipoProducto tipoProducto, FacturaProveedor notaCreditoProveedor)
/* 439:    */   {
/* 440:530 */     String detalle = rp != null ? "DetalleRecepcionProveedor" : "DetalleFacturaProveedor";
/* 441:531 */     String cabecera = rp != null ? "recepcionProveedor" : "facturaProveedor";
/* 442:532 */     StringBuilder sql = new StringBuilder();
/* 443:533 */     sql.append("SELECT new DetalleInterfazContableProcesoRecepcionProveedor(d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa,");
/* 444:    */     
/* 445:535 */     sql.append(" ce.nombre, e.idEmpresa, e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto,");
/* 446:536 */     sql.append(" p.nombre, b.idBodega, b.nombre, CONCAT(d.nombre,' #',cab.numero,' ',cab.descripcion),");
/* 447:537 */     if (TipoProducto.ARTICULO.equals(tipoProducto))
/* 448:    */     {
/* 449:538 */       if (rp == null) {
/* 450:539 */         sql.append("-");
/* 451:    */       }
/* 452:541 */       sql.append("SUM(ip.costo))");
/* 453:    */     }
/* 454:542 */     else if (rp != null)
/* 455:    */     {
/* 456:543 */       sql.append(" CASE WHEN dfp IS NULL THEN SUM(dpp.cantidad*dpp.precio) ELSE SUM(dfp.cantidad*dfp.precio) END, dc1.idDimensionContable, dc2.idDimensionContable, dc3.idDimensionContable, dc4.idDimensionContable, dc5.idDimensionContable)");
/* 457:    */     }
/* 458:545 */     sql.append(" FROM " + detalle + " det ");
/* 459:546 */     sql.append(" INNER JOIN det." + cabecera + " cab ");
/* 460:547 */     sql.append(" INNER JOIN cab.documento d ");
/* 461:548 */     sql.append(" INNER JOIN cab.sucursal s ");
/* 462:549 */     sql.append(" INNER JOIN cab.empresa e ");
/* 463:550 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 464:551 */     sql.append(" INNER JOIN det.producto p ");
/* 465:552 */     sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 466:553 */     sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 467:554 */     sql.append(" LEFT JOIN det.inventarioProducto ip ");
/* 468:555 */     sql.append(" LEFT JOIN ip.bodega b ");
/* 469:556 */     if (rp != null)
/* 470:    */     {
/* 471:557 */       sql.append(" LEFT JOIN det.detalleFacturaProveedor dfp");
/* 472:558 */       sql.append(" LEFT JOIN det.detallePedidoProveedor dpp");
/* 473:559 */       sql.append(" LEFT JOIN dpp.dimensionContable1 dc1");
/* 474:560 */       sql.append(" LEFT JOIN dpp.dimensionContable2 dc2");
/* 475:561 */       sql.append(" LEFT JOIN dpp.dimensionContable3 dc3");
/* 476:562 */       sql.append(" LEFT JOIN dpp.dimensionContable4 dc4");
/* 477:563 */       sql.append(" LEFT JOIN dpp.dimensionContable5 dc5");
/* 478:    */     }
/* 479:565 */     if (rp != null) {
/* 480:566 */       sql.append(" WHERE cab.idRecepcionProveedor = :idCabecera");
/* 481:    */     } else {
/* 482:568 */       sql.append(" WHERE cab.idFacturaProveedor = :idCabecera");
/* 483:    */     }
/* 484:570 */     sql.append("  AND p.tipoProducto = :pTipoArticulo");
/* 485:571 */     sql.append(" AND cab.fecha = :fecha");
/* 486:572 */     sql.append(" GROUP BY d.idDocumento, d.nombre, s.idSucursal, s.nombre, ce.idCategoriaEmpresa, ce.nombre, e.idEmpresa,");
/* 487:573 */     sql.append(" e.nombreFiscal, cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto, p.nombre, ");
/* 488:574 */     sql.append(" b.idBodega, b.nombre, CONCAT(d.nombre,' #',cab.numero,' ',cab.descripcion)");
/* 489:575 */     if (rp != null) {
/* 490:576 */       sql.append(", dfp.idDetalleFacturaProveedor, dc1.idDimensionContable, dc2.idDimensionContable, dc3.idDimensionContable, dc4.idDimensionContable, dc5.idDimensionContable");
/* 491:    */     }
/* 492:578 */     if (TipoProducto.ARTICULO.equals(tipoProducto)) {
/* 493:579 */       sql.append(" HAVING SUM(ip.costo) <> 0");
/* 494:580 */     } else if (rp != null) {
/* 495:581 */       sql.append(" HAVING (CASE WHEN dfp IS NULL THEN SUM(dpp.cantidad*dpp.precio) ELSE SUM(dfp.cantidad*dfp.precio) END) <>0");
/* 496:    */     }
/* 497:584 */     Query query = this.em.createQuery(sql.toString());
/* 498:585 */     query.setParameter("idCabecera", Integer.valueOf(rp != null ? rp.getIdRecepcionProveedor() : notaCreditoProveedor.getIdFacturaProveedor()));
/* 499:586 */     query.setParameter("fecha", rp != null ? rp.getFecha() : notaCreditoProveedor.getFecha(), TemporalType.DATE);
/* 500:587 */     query.setParameter("pTipoArticulo", tipoProducto);
/* 501:    */     
/* 502:589 */     return query.getResultList();
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void actualizarFechaAnulacionRecepcionProveedor(RecepcionProveedor recepcionProveedor, Date fechaAnulacion)
/* 506:    */   {
/* 507:604 */     StringBuilder sql = new StringBuilder();
/* 508:605 */     sql.append("UPDATE RecepcionProveedor rp ");
/* 509:606 */     sql.append(" SET rp.fechaAnulacion = :fechaAnulacion ");
/* 510:607 */     sql.append(" WHERE rp = :recepcionProveedor ");
/* 511:    */     
/* 512:609 */     Query query = this.em.createQuery(sql.toString());
/* 513:610 */     query.setParameter("fechaAnulacion", fechaAnulacion);
/* 514:611 */     query.setParameter("recepcionProveedor", recepcionProveedor);
/* 515:    */     
/* 516:613 */     query.executeUpdate();
/* 517:    */   }
/* 518:    */   
/* 519:    */   public List<RecepcionProveedor> buscarRecepcionesNoFacturadasPorProveedor(Integer idEmpresa)
/* 520:    */   {
/* 521:617 */     return autocompletarRecepcionesNoFacturadasPorProveedor(idEmpresa, null);
/* 522:    */   }
/* 523:    */   
/* 524:    */   public List<RecepcionProveedor> autocompletarRecepcionesNoFacturadasPorProveedor(Integer idEmpresa, String numero)
/* 525:    */   {
/* 526:622 */     StringBuilder sql = new StringBuilder();
/* 527:623 */     sql.append("SELECT rp");
/* 528:624 */     sql.append(" FROM RecepcionProveedor rp");
/* 529:625 */     sql.append(" LEFT JOIN FETCH rp.pedidoProveedor p");
/* 530:626 */     sql.append(" INNER JOIN rp.empresa e");
/* 531:627 */     sql.append(" WHERE e.idEmpresa = :idEmpresa");
/* 532:628 */     sql.append(" AND rp.estado != :estadoAnulado");
/* 533:629 */     sql.append(" AND NOT EXISTS(");
/* 534:630 */     sql.append("\t\tSELECT 1 FROM FacturaProveedor f");
/* 535:631 */     sql.append("\t\tINNER JOIN f.documento d");
/* 536:632 */     sql.append("\t\tWHERE f.recepcionProveedor = rp");
/* 537:633 */     sql.append("\t\tAND f.estado!=:estadoAnulado");
/* 538:634 */     sql.append("\t\tAND d.documentoBase=:documentoFacturaProveedor");
/* 539:635 */     sql.append(")");
/* 540:636 */     if ((numero != null) && (!numero.isEmpty())) {
/* 541:637 */       sql.append(" AND rp.numero LIKE :numero");
/* 542:    */     }
/* 543:639 */     sql.append(" ORDER BY rp.fecha DESC");
/* 544:640 */     Query query = this.em.createQuery(sql.toString());
/* 545:641 */     query.setParameter("idEmpresa", idEmpresa);
/* 546:642 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 547:643 */     query.setParameter("documentoFacturaProveedor", DocumentoBase.FACTURA_PROVEEDOR);
/* 548:644 */     if ((numero != null) && (!numero.isEmpty())) {
/* 549:645 */       query.setParameter("numero", "%" + numero + "%");
/* 550:    */     }
/* 551:647 */     return query.getResultList();
/* 552:    */   }
/* 553:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RecepcionProveedorDao
 * JD-Core Version:    0.7.0.1
 */