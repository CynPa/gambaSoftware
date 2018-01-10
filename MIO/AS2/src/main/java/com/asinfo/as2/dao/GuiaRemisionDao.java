/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   5:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.DetalleGuiaRemision;
/*   8:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  12:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ import javax.persistence.EntityManager;
/*  21:    */ import javax.persistence.NoResultException;
/*  22:    */ import javax.persistence.Query;
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
/*  35:    */ public class GuiaRemisionDao
/*  36:    */   extends AbstractDaoAS2<GuiaRemision>
/*  37:    */ {
/*  38:    */   @EJB
/*  39:    */   private DetalleGuiaRemisionDao detalleGuiaRemisionDao;
/*  40:    */   @EJB
/*  41:    */   private FacturaClienteDao facturaClienteDao;
/*  42:    */   
/*  43:    */   public GuiaRemisionDao()
/*  44:    */   {
/*  45: 39 */     super(GuiaRemision.class);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public GuiaRemision buscarPorDespacho(int idDespachoCliente)
/*  49:    */   {
/*  50: 49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  51: 50 */     CriteriaQuery<GuiaRemision> cqDetalle = criteriaBuilder.createQuery(GuiaRemision.class);
/*  52: 51 */     Root<GuiaRemision> from = cqDetalle.from(GuiaRemision.class);
/*  53:    */     
/*  54: 53 */     Fetch<Object, Object> fromDocumento = from.fetch("documento", JoinType.LEFT);
/*  55: 54 */     fromDocumento.fetch("secuencia", JoinType.LEFT);
/*  56: 55 */     from.fetch("ciudadOrigen", JoinType.LEFT);
/*  57: 56 */     from.fetch("ciudadDestino", JoinType.LEFT);
/*  58: 57 */     Fetch<Object, Object> fromVehiculo = from.fetch("vehiculo", JoinType.LEFT);
/*  59: 58 */     fromVehiculo.fetch("transportista");
/*  60: 59 */     fromVehiculo.fetch("tipoVehiculo");
/*  61:    */     
/*  62: 61 */     Path<Integer> pathIdDetalle = from.join("despachoCliente").get("idDespachoCliente");
/*  63: 62 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idDespachoCliente)));
/*  64: 63 */     CriteriaQuery<GuiaRemision> select = cqDetalle.select(from);
/*  65:    */     try
/*  66:    */     {
/*  67: 66 */       return (GuiaRemision)this.em.createQuery(select).getSingleResult();
/*  68:    */     }
/*  69:    */     catch (NoResultException e) {}
/*  70: 68 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public GuiaRemision buscarPorTransferenciaBodega(int idTransferenciaBodega)
/*  74:    */   {
/*  75: 79 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  76: 80 */     CriteriaQuery<GuiaRemision> cqDetalle = criteriaBuilder.createQuery(GuiaRemision.class);
/*  77: 81 */     Root<GuiaRemision> from = cqDetalle.from(GuiaRemision.class);
/*  78:    */     
/*  79: 83 */     Fetch<Object, Object> fromDocumento = from.fetch("documento", JoinType.LEFT);
/*  80: 84 */     fromDocumento.fetch("secuencia", JoinType.LEFT);
/*  81: 85 */     from.fetch("ciudadOrigen", JoinType.LEFT);
/*  82: 86 */     from.fetch("ciudadDestino", JoinType.LEFT);
/*  83: 87 */     Fetch<Object, Object> fromVehiculo = from.fetch("vehiculo", JoinType.LEFT);
/*  84: 88 */     fromVehiculo.fetch("transportista");
/*  85: 89 */     fromVehiculo.fetch("tipoVehiculo");
/*  86:    */     
/*  87: 91 */     Path<Integer> pathIdDetalle = from.join("transferenciaBodega").get("idMovimientoInventario");
/*  88: 92 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idTransferenciaBodega)));
/*  89: 93 */     CriteriaQuery<GuiaRemision> select = cqDetalle.select(from);
/*  90:    */     try
/*  91:    */     {
/*  92: 96 */       return (GuiaRemision)this.em.createQuery(select).getSingleResult();
/*  93:    */     }
/*  94:    */     catch (NoResultException e) {}
/*  95: 98 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public GuiaRemision buscarPorHojaRutaTransportista(int idHojaRutaTransportista)
/*  99:    */   {
/* 100:103 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 101:104 */     CriteriaQuery<GuiaRemision> cqDetalle = criteriaBuilder.createQuery(GuiaRemision.class);
/* 102:105 */     Root<GuiaRemision> from = cqDetalle.from(GuiaRemision.class);
/* 103:    */     
/* 104:107 */     Fetch<Object, Object> fromDocumento = from.fetch("documento", JoinType.LEFT);
/* 105:108 */     fromDocumento.fetch("secuencia", JoinType.LEFT);
/* 106:109 */     from.fetch("ciudadOrigen", JoinType.LEFT);
/* 107:110 */     from.fetch("ciudadDestino", JoinType.LEFT);
/* 108:111 */     Fetch<Object, Object> fromVehiculo = from.fetch("vehiculo", JoinType.LEFT);
/* 109:112 */     fromVehiculo.fetch("transportista");
/* 110:113 */     fromVehiculo.fetch("tipoVehiculo");
/* 111:    */     
/* 112:115 */     Path<Integer> pathIdDetalle = from.join("hojaRutaTransportista").get("idHojaRuta");
/* 113:116 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idHojaRutaTransportista)));
/* 114:117 */     CriteriaQuery<GuiaRemision> select = cqDetalle.select(from);
/* 115:    */     try
/* 116:    */     {
/* 117:120 */       return (GuiaRemision)this.em.createQuery(select).getSingleResult();
/* 118:    */     }
/* 119:    */     catch (NoResultException e) {}
/* 120:122 */     return null;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public GuiaRemision cargarDetalle(int idGuiaRemision)
/* 124:    */   {
/* 125:128 */     StringBuilder sql = new StringBuilder();
/* 126:129 */     sql.append(" SELECT gr FROM GuiaRemision gr ");
/* 127:130 */     sql.append(" LEFT JOIN FETCH gr.documento do ");
/* 128:131 */     sql.append(" LEFT JOIN FETCH gr.ciudadOrigen co ");
/* 129:132 */     sql.append(" LEFT JOIN FETCH gr.ciudadDestino cd ");
/* 130:133 */     sql.append(" LEFT JOIN FETCH gr.vehiculo ve ");
/* 131:134 */     sql.append(" LEFT JOIN FETCH ve.transportista tr ");
/* 132:135 */     sql.append(" LEFT JOIN FETCH tr.tipoIdentificacion ti ");
/* 133:136 */     sql.append(" LEFT JOIN FETCH gr.despachoCliente dc ");
/* 134:137 */     sql.append(" LEFT JOIN FETCH dc.direccionEmpresa de ");
/* 135:138 */     sql.append(" LEFT JOIN FETCH dc.empresa emp2 ");
/* 136:139 */     sql.append(" LEFT JOIN FETCH dc.listaDetalleDespachoCliente lddc ");
/* 137:140 */     sql.append(" LEFT JOIN FETCH lddc.producto pr ");
/* 138:141 */     sql.append(" LEFT JOIN FETCH gr.transferenciaBodega tb ");
/* 139:142 */     sql.append(" LEFT JOIN FETCH tb.bodegaDestino bd ");
/* 140:143 */     sql.append(" LEFT JOIN FETCH bd.ubicacion u ");
/* 141:144 */     sql.append(" LEFT JOIN FETCH gr.empresa em ");
/* 142:145 */     sql.append(" LEFT JOIN FETCH gr.direccionEmpresa de ");
/* 143:146 */     sql.append(" LEFT JOIN FETCH gr.facturaCliente fc ");
/* 144:147 */     sql.append(" LEFT JOIN FETCH fc.facturaClienteSRI fcSRI ");
/* 145:    */     
/* 146:149 */     sql.append(" WHERE gr.idGuiaRemision = :idGuiaRemision ");
/* 147:150 */     Query query = this.em.createQuery(sql.toString());
/* 148:151 */     query.setParameter("idGuiaRemision", Integer.valueOf(idGuiaRemision));
/* 149:    */     try
/* 150:    */     {
/* 151:154 */       GuiaRemision guia = (GuiaRemision)query.getSingleResult();
/* 152:156 */       if ((guia.getFacturaCliente() == null) && 
/* 153:157 */         (guia.getDespachoCliente() != null) && (guia.getDespachoCliente().getFacturaCliente() != null))
/* 154:    */       {
/* 155:158 */         guia.getDespachoCliente().getFacturaCliente().getListaDetalleFacturaCliente().size();
/* 156:159 */         guia.setFacturaCliente(guia.getDespachoCliente().getFacturaCliente());
/* 157:    */       }
/* 158:163 */       if (guia.getFacturaCliente() != null) {
/* 159:164 */         guia.setFacturaCliente(this.facturaClienteDao.cargarDetalle(guia.getFacturaCliente().getId()));
/* 160:    */       }
/* 161:167 */       if ((guia.getFacturaCliente() != null) && (guia.getFacturaCliente().getListaDetalleFacturaCliente().size() > 0)) {
/* 162:168 */         for (DetalleFacturaCliente detalleFactura : guia.getFacturaCliente().getListaDetalleFacturaCliente()) {
/* 163:169 */           detalleFactura.getId();
/* 164:    */         }
/* 165:    */       }
/* 166:173 */       if ((guia.getEmpresa() == null) && 
/* 167:174 */         (guia.getDespachoCliente() != null) && (guia.getDespachoCliente().getEmpresa() != null)) {
/* 168:175 */         guia.setEmpresa(guia.getDespachoCliente().getEmpresa());
/* 169:    */       }
/* 170:178 */       if ((guia.getDireccionEmpresa() == null) && 
/* 171:179 */         (guia.getDespachoCliente() != null) && (guia.getDespachoCliente().getDireccionEmpresa() != null)) {
/* 172:180 */         guia.setDireccionEmpresa(guia.getDespachoCliente().getDireccionEmpresa());
/* 173:    */       }
/* 174:184 */       if (guia.getListaDetalleGuiaRemision().size() == 0)
/* 175:    */       {
/* 176:185 */         this.em.detach(guia);
/* 177:186 */         if ((guia.getDespachoCliente() != null) && (guia.getDespachoCliente().getListaDetalleDespachoCliente().size() > 0)) {
/* 178:187 */           for (DetalleDespachoCliente detalleDespacho : guia.getDespachoCliente().getListaDetalleDespachoCliente())
/* 179:    */           {
/* 180:188 */             detalleDespacho.getProducto().getId();
/* 181:189 */             DetalleGuiaRemision detalleGuia = new DetalleGuiaRemision();
/* 182:190 */             detalleGuia.setIdOrganizacion(guia.getIdOrganizacion());
/* 183:191 */             detalleGuia.setIdSucursal(guia.getIdSucursal());
/* 184:192 */             detalleGuia.setProducto(detalleDespacho.getProducto());
/* 185:193 */             detalleGuia.setCantidad(detalleDespacho.getCantidad());
/* 186:194 */             detalleGuia.setGuiaRemision(guia);
/* 187:195 */             detalleGuia.setEliminado(false);
/* 188:196 */             guia.getListaDetalleGuiaRemision().add(detalleGuia);
/* 189:    */           }
/* 190:    */         }
/* 191:199 */         if ((guia.getTransferenciaBodega() != null) && (guia.getTransferenciaBodega().getDetalleMovimientosInventario().size() > 0)) {
/* 192:200 */           for (DetalleMovimientoInventario detalleTransferencia : guia.getTransferenciaBodega().getDetalleMovimientosInventario())
/* 193:    */           {
/* 194:201 */             detalleTransferencia.getProducto().getId();
/* 195:202 */             DetalleGuiaRemision detalleGuia = new DetalleGuiaRemision();
/* 196:203 */             detalleGuia.setIdOrganizacion(guia.getIdOrganizacion());
/* 197:204 */             detalleGuia.setIdSucursal(guia.getIdSucursal());
/* 198:205 */             detalleGuia.setProducto(detalleTransferencia.getProducto());
/* 199:206 */             detalleGuia.setCantidad(detalleTransferencia.getCantidad());
/* 200:207 */             detalleGuia.setGuiaRemision(guia);
/* 201:208 */             detalleGuia.setEliminado(false);
/* 202:209 */             guia.getListaDetalleGuiaRemision().add(detalleGuia);
/* 203:    */           }
/* 204:    */         }
/* 205:    */       }
/* 206:214 */       for (DetalleGuiaRemision detalle : guia.getListaDetalleGuiaRemision()) {
/* 207:215 */         detalle.getProducto().getId();
/* 208:    */       }
/* 209:218 */       return guia;
/* 210:    */     }
/* 211:    */     catch (NoResultException e) {}
/* 212:220 */     return null;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<GuiaRemision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 216:    */   {
/* 217:231 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 218:232 */     CriteriaQuery<GuiaRemision> criteriaQuery = criteriaBuilder.createQuery(GuiaRemision.class);
/* 219:233 */     Root<GuiaRemision> from = criteriaQuery.from(GuiaRemision.class);
/* 220:    */     
/* 221:235 */     from.fetch("empresa", JoinType.LEFT);
/* 222:236 */     from.fetch("direccionEmpresa", JoinType.LEFT);
/* 223:237 */     from.fetch("transferenciaBodega", JoinType.LEFT);
/* 224:238 */     from.fetch("despachoCliente", JoinType.LEFT);
/* 225:    */     
/* 226:240 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 227:241 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 228:    */     
/* 229:243 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 230:    */     
/* 231:245 */     CriteriaQuery<GuiaRemision> select = criteriaQuery.select(from);
/* 232:    */     
/* 233:247 */     TypedQuery<GuiaRemision> typedQuery = this.em.createQuery(select);
/* 234:248 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 235:249 */     List<GuiaRemision> result = typedQuery.getResultList();
/* 236:251 */     for (GuiaRemision guia : result)
/* 237:    */     {
/* 238:252 */       if ((guia.getEmpresa() == null) && 
/* 239:253 */         (guia.getDespachoCliente() != null) && (guia.getDespachoCliente().getEmpresa() != null)) {
/* 240:254 */         guia.setEmpresa(guia.getDespachoCliente().getEmpresa());
/* 241:    */       }
/* 242:257 */       if ((guia.getDireccionEmpresa() == null) && 
/* 243:258 */         (guia.getDespachoCliente() != null) && (guia.getDespachoCliente().getDireccionEmpresa() != null)) {
/* 244:259 */         guia.setDireccionEmpresa(guia.getDespachoCliente().getDireccionEmpresa());
/* 245:    */       }
/* 246:    */     }
/* 247:264 */     return result;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void guardar(GuiaRemision entidad)
/* 251:    */   {
/* 252:270 */     super.guardar(entidad);
/* 253:271 */     if (entidad.getListaDetalleGuiaRemision().size() > 0) {
/* 254:272 */       for (DetalleGuiaRemision detalle : entidad.getListaDetalleGuiaRemision())
/* 255:    */       {
/* 256:273 */         if (detalle.getProducto() == null) {
/* 257:274 */           detalle.setEliminado(true);
/* 258:    */         }
/* 259:276 */         detalle.setGuiaRemision(entidad);
/* 260:277 */         this.detalleGuiaRemisionDao.guardar(detalle);
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public GuiaRemision buscarPorFactura(int idFacturaCliente)
/* 266:    */   {
/* 267:289 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 268:290 */     CriteriaQuery<GuiaRemision> cqDetalle = criteriaBuilder.createQuery(GuiaRemision.class);
/* 269:291 */     Root<GuiaRemision> from = cqDetalle.from(GuiaRemision.class);
/* 270:    */     
/* 271:293 */     from.fetch("facturaCliente", JoinType.LEFT);
/* 272:294 */     Path<Integer> pathIdfacturaCliente = from.join("facturaCliente").get("idFacturaCliente");
/* 273:295 */     cqDetalle.where(criteriaBuilder.equal(pathIdfacturaCliente, Integer.valueOf(idFacturaCliente)));
/* 274:296 */     CriteriaQuery<GuiaRemision> select = cqDetalle.select(from);
/* 275:297 */     TypedQuery<GuiaRemision> typedQuery = this.em.createQuery(select);
/* 276:    */     try
/* 277:    */     {
/* 278:299 */       return (GuiaRemision)typedQuery.getSingleResult();
/* 279:    */     }
/* 280:    */     catch (NoResultException e) {}
/* 281:301 */     return null;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void anular(GuiaRemision guiaRemision)
/* 285:    */   {
/* 286:306 */     String sql = "UPDATE GuiaRemision gr  SET gr.estado=:estado, gr.despachoCliente = NULL, gr.facturaCliente = NULL, gr.mensajeSRI = '' \tWHERE gr = :guiaRemision";
/* 287:    */     
/* 288:    */ 
/* 289:309 */     Query query = this.em.createQuery(sql);
/* 290:310 */     query.setParameter("estado", Estado.ANULADO);
/* 291:311 */     query.setParameter("guiaRemision", guiaRemision);
/* 292:312 */     query.executeUpdate();
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void liberarGuiaRemisionAutomatica(Integer idGuiaRemision)
/* 296:    */   {
/* 297:316 */     StringBuilder sql = new StringBuilder();
/* 298:317 */     sql.append(" UPDATE GuiaRemision gr ");
/* 299:318 */     sql.append(" SET gr.indicadorAutomatico = false  ");
/* 300:319 */     sql.append(" WHERE gr.idGuiaRemision = :idGuiaRemision ");
/* 301:    */     
/* 302:321 */     Query query = this.em.createQuery(sql.toString());
/* 303:322 */     query.setParameter("idGuiaRemision", idGuiaRemision);
/* 304:    */     
/* 305:324 */     query.executeUpdate();
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void actualizaGuiaRemision(int idGuiaRemision, Estado estadoGuiaRemision, EstadoDocumentoElectronico estadoSRI, Date fechaAutorizacion, String numeroAutorizacion, String mensajeSRI)
/* 309:    */   {
/* 310:330 */     String set = "SET ";
/* 311:331 */     StringBuilder sql1 = new StringBuilder();
/* 312:332 */     sql1.append("UPDATE GuiaRemision gr ");
/* 313:333 */     if (estadoGuiaRemision != null)
/* 314:    */     {
/* 315:334 */       sql1.append(set + " gr.estado = :estadoGuiaRemision ");
/* 316:335 */       set = ", ";
/* 317:    */     }
/* 318:337 */     if (fechaAutorizacion != null)
/* 319:    */     {
/* 320:338 */       sql1.append(set + " gr.fechaAutorizacion = :fechaAutorizacion ");
/* 321:339 */       set = ", ";
/* 322:    */     }
/* 323:341 */     if (numeroAutorizacion != null)
/* 324:    */     {
/* 325:342 */       sql1.append(set + " gr.autorizacion = :numeroAutorizacion ");
/* 326:343 */       set = ", ";
/* 327:    */     }
/* 328:345 */     if (estadoSRI != null)
/* 329:    */     {
/* 330:346 */       sql1.append(set + " gr.estadoDocumentoElectronico = :estadoSRI ");
/* 331:347 */       set = ", ";
/* 332:    */     }
/* 333:349 */     if (mensajeSRI != null) {
/* 334:350 */       sql1.append(set + " gr.mensajeSRI = :mensajeSRI ");
/* 335:    */     }
/* 336:352 */     sql1.append(" WHERE gr.idGuiaRemision = :idGuiaRemision ");
/* 337:    */     
/* 338:354 */     Query query1 = this.em.createQuery(sql1.toString());
/* 339:355 */     query1.setParameter("idGuiaRemision", Integer.valueOf(idGuiaRemision));
/* 340:356 */     if (estadoGuiaRemision != null) {
/* 341:357 */       query1.setParameter("estadoGuiaRemision", estadoGuiaRemision);
/* 342:    */     }
/* 343:359 */     if (fechaAutorizacion != null) {
/* 344:360 */       query1.setParameter("fechaAutorizacion", fechaAutorizacion);
/* 345:    */     }
/* 346:362 */     if (numeroAutorizacion != null) {
/* 347:363 */       query1.setParameter("numeroAutorizacion", numeroAutorizacion);
/* 348:    */     }
/* 349:365 */     if (estadoSRI != null) {
/* 350:366 */       query1.setParameter("estadoSRI", estadoSRI);
/* 351:    */     }
/* 352:368 */     if (mensajeSRI != null) {
/* 353:369 */       query1.setParameter("mensajeSRI", mensajeSRI);
/* 354:    */     }
/* 355:371 */     query1.executeUpdate();
/* 356:    */   }
/* 357:    */   
/* 358:    */   public Empresa obtenerEmpresaGuiaRemision(int idGuiaRemision)
/* 359:    */   {
/* 360:375 */     StringBuilder sql = new StringBuilder();
/* 361:376 */     sql.append(" SELECT emp ");
/* 362:377 */     sql.append(" FROM GuiaRemision gr ");
/* 363:378 */     sql.append(" INNER JOIN gr.empresa emp ");
/* 364:379 */     sql.append(" WHERE gr.idGuiaRemision = :idGuiaRemision ");
/* 365:    */     
/* 366:381 */     Query query = this.em.createQuery(sql.toString());
/* 367:382 */     query.setParameter("idGuiaRemision", Integer.valueOf(idGuiaRemision));
/* 368:383 */     return (Empresa)query.getSingleResult();
/* 369:    */   }
/* 370:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.GuiaRemisionDao
 * JD-Core Version:    0.7.0.1
 */