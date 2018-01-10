/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ContratoVenta;
/*   4:    */ import com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta;
/*   5:    */ import com.asinfo.as2.entities.DetalleContratoVenta;
/*   6:    */ import com.asinfo.as2.entities.DetalleValoresContratoVenta;
/*   7:    */ import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ import javax.persistence.EntityManager;
/*  22:    */ import javax.persistence.Query;
/*  23:    */ import javax.persistence.TypedQuery;
/*  24:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  25:    */ import javax.persistence.criteria.CriteriaQuery;
/*  26:    */ import javax.persistence.criteria.Expression;
/*  27:    */ import javax.persistence.criteria.Fetch;
/*  28:    */ import javax.persistence.criteria.Join;
/*  29:    */ import javax.persistence.criteria.JoinType;
/*  30:    */ import javax.persistence.criteria.Order;
/*  31:    */ import javax.persistence.criteria.Path;
/*  32:    */ import javax.persistence.criteria.Predicate;
/*  33:    */ import javax.persistence.criteria.Root;
/*  34:    */ 
/*  35:    */ @Stateless
/*  36:    */ public class ContratoVentaDao
/*  37:    */   extends AbstractDaoAS2<ContratoVenta>
/*  38:    */ {
/*  39:    */   @EJB
/*  40:    */   private FacturaClienteDao facturaClienteDao;
/*  41:    */   
/*  42:    */   public ContratoVentaDao()
/*  43:    */   {
/*  44: 52 */     super(ContratoVenta.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<ContratoVenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 62 */     List<ContratoVenta> listaContratoVenta = new ArrayList();
/*  50:    */     
/*  51: 64 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  52: 65 */     CriteriaQuery<ContratoVenta> criteriaQuery = criteriaBuilder.createQuery(ContratoVenta.class);
/*  53: 66 */     Root<ContratoVenta> from = criteriaQuery.from(ContratoVenta.class);
/*  54:    */     
/*  55: 68 */     from.fetch("empresa", JoinType.LEFT);
/*  56: 69 */     from.fetch("sucursal", JoinType.LEFT);
/*  57:    */     
/*  58:    */ 
/*  59: 72 */     Fetch<Object, Object> subempresa = from.fetch("subempresa", JoinType.LEFT);
/*  60: 73 */     Fetch<Object, Object> empresaSubempresa = subempresa.fetch("empresa", JoinType.LEFT);
/*  61: 74 */     empresaSubempresa.fetch("cliente", JoinType.LEFT);
/*  62: 75 */     empresaSubempresa.fetch("proveedor", JoinType.LEFT);
/*  63: 76 */     empresaSubempresa.fetch("empleado", JoinType.LEFT);
/*  64:    */     
/*  65: 78 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  66: 79 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  67:    */     
/*  68: 81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  69:    */     
/*  70: 83 */     CriteriaQuery<ContratoVenta> select = criteriaQuery.select(from);
/*  71:    */     
/*  72: 85 */     TypedQuery<ContratoVenta> typedQuery = this.em.createQuery(select);
/*  73: 86 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  74:    */     
/*  75: 88 */     listaContratoVenta = typedQuery.getResultList();
/*  76: 91 */     for (ContratoVenta cv : listaContratoVenta)
/*  77:    */     {
/*  78: 93 */       CriteriaQuery<DetalleContratoVenta> cqDetalle = criteriaBuilder.createQuery(DetalleContratoVenta.class);
/*  79: 94 */       Root<DetalleContratoVenta> fromDetalle = cqDetalle.from(DetalleContratoVenta.class);
/*  80: 95 */       fromDetalle.fetch("producto", JoinType.LEFT);
/*  81:    */       
/*  82: 97 */       cqDetalle.where(criteriaBuilder.equal(fromDetalle.join("contratoVenta"), cv));
/*  83: 98 */       CriteriaQuery<DetalleContratoVenta> selectContratoVenta = cqDetalle.select(fromDetalle);
/*  84:    */       
/*  85:100 */       List<DetalleContratoVenta> listaDetalleContratoVenta = this.em.createQuery(selectContratoVenta).getResultList();
/*  86:101 */       cv.setListaDetalleContratoVenta(listaDetalleContratoVenta);
/*  87:    */     }
/*  88:104 */     return listaContratoVenta;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public ContratoVenta cargarDetalle(ContratoVenta contratoVenta)
/*  92:    */   {
/*  93:113 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  94:    */     
/*  95:    */ 
/*  96:116 */     CriteriaQuery<ContratoVenta> cqCabecera = criteriaBuilder.createQuery(ContratoVenta.class);
/*  97:117 */     Root<ContratoVenta> fromCabecera = cqCabecera.from(ContratoVenta.class);
/*  98:    */     
/*  99:119 */     fromCabecera.fetch("documento", JoinType.LEFT).fetch("secuencia", JoinType.LEFT);
/* 100:120 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/* 101:121 */     fromCabecera.fetch("agenteComercial", JoinType.LEFT);
/* 102:122 */     fromCabecera.fetch("subempresa", JoinType.LEFT);
/* 103:123 */     fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/* 104:124 */     fromCabecera.fetch("canal", JoinType.LEFT);
/* 105:125 */     fromCabecera.fetch("zona", JoinType.LEFT);
/* 106:126 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/* 107:    */     
/* 108:128 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/* 109:129 */     empresa.fetch("tipoIdentificacion", JoinType.LEFT);
/* 110:    */     
/* 111:131 */     Path<Integer> pathId = fromCabecera.get("idContratoVenta");
/* 112:132 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(contratoVenta.getIdContratoVenta())));
/* 113:133 */     CriteriaQuery<ContratoVenta> selectPedido = cqCabecera.select(fromCabecera);
/* 114:    */     
/* 115:135 */     ContratoVenta auxContratoVenta = (ContratoVenta)this.em.createQuery(selectPedido).getSingleResult();
/* 116:    */     
/* 117:137 */     this.em.detach(auxContratoVenta);
/* 118:    */     
/* 119:    */ 
/* 120:140 */     CriteriaQuery<DetalleContratoVenta> cqDetalle = criteriaBuilder.createQuery(DetalleContratoVenta.class);
/* 121:141 */     Root<DetalleContratoVenta> fromDetalle = cqDetalle.from(DetalleContratoVenta.class);
/* 122:142 */     fromDetalle.fetch("producto", JoinType.LEFT);
/* 123:    */     
/* 124:144 */     cqDetalle.where(criteriaBuilder.equal(fromDetalle.join("contratoVenta"), auxContratoVenta));
/* 125:145 */     CriteriaQuery<DetalleContratoVenta> selectContratoVenta = cqDetalle.select(fromDetalle);
/* 126:    */     
/* 127:147 */     List<DetalleContratoVenta> listaDetalleContratoVenta = this.em.createQuery(selectContratoVenta).getResultList();
/* 128:148 */     auxContratoVenta.setListaDetalleContratoVenta(listaDetalleContratoVenta);
/* 129:    */     
/* 130:    */ 
/* 131:151 */     CriteriaQuery<DetallesFacturaContratoVenta> cqDetalleFacturaContratoVenta = criteriaBuilder.createQuery(DetallesFacturaContratoVenta.class);
/* 132:152 */     Root<DetallesFacturaContratoVenta> fromDetalleFacturaContratoVenta = cqDetalleFacturaContratoVenta.from(DetallesFacturaContratoVenta.class);
/* 133:    */     
/* 134:154 */     cqDetalleFacturaContratoVenta.where(criteriaBuilder.equal(fromDetalleFacturaContratoVenta.join("contratoVenta"), auxContratoVenta));
/* 135:155 */     CriteriaQuery<DetallesFacturaContratoVenta> selectFacturaContratoVenta = cqDetalleFacturaContratoVenta.select(fromDetalleFacturaContratoVenta);
/* 136:    */     
/* 137:157 */     selectFacturaContratoVenta.orderBy(new Order[] { criteriaBuilder.asc(fromDetalleFacturaContratoVenta.get("fecha")) });
/* 138:    */     
/* 139:159 */     List<DetallesFacturaContratoVenta> listaDetalleFacturaContratoVenta = this.em.createQuery(selectFacturaContratoVenta).getResultList();
/* 140:160 */     auxContratoVenta.setListaDetallesFacturaContratoVenta(listaDetalleFacturaContratoVenta);
/* 141:163 */     for (DetallesFacturaContratoVenta dfcv : listaDetalleFacturaContratoVenta)
/* 142:    */     {
/* 143:165 */       if (dfcv.isIndicadorFacturado()) {
/* 144:166 */         auxContratoVenta.setCuotasFacturadas(true);
/* 145:    */       }
/* 146:169 */       dfcv.setContratoVenta(auxContratoVenta);
/* 147:170 */       Integer idDetalleFacturaContratoVenta = Integer.valueOf(dfcv.getId());
/* 148:    */       
/* 149:172 */       CriteriaQuery<ContratoVentaFacturaContratoVenta> cqContratoVentaFacturaContratoVenta = criteriaBuilder.createQuery(ContratoVentaFacturaContratoVenta.class);
/* 150:173 */       Root<ContratoVentaFacturaContratoVenta> fromContratoVentaFacturaContratoVenta = cqContratoVentaFacturaContratoVenta.from(ContratoVentaFacturaContratoVenta.class);
/* 151:    */       
/* 152:175 */       Path<Integer> pathIdContratoVentaFacturaContratoVenta = fromContratoVentaFacturaContratoVenta.join("detallesFacturaContratoVenta").get("idDetallesFacturaContratoVenta");
/* 153:176 */       cqContratoVentaFacturaContratoVenta.where(criteriaBuilder.equal(pathIdContratoVentaFacturaContratoVenta, idDetalleFacturaContratoVenta));
/* 154:    */       
/* 155:178 */       CriteriaQuery<ContratoVentaFacturaContratoVenta> selectContratoVentaFacturaContratoVenta = cqContratoVentaFacturaContratoVenta.select(fromContratoVentaFacturaContratoVenta);
/* 156:    */       
/* 157:180 */       List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta = this.em.createQuery(selectContratoVentaFacturaContratoVenta).getResultList();
/* 158:    */       
/* 159:182 */       dfcv.setListaContratoVentaFacturaContratoVenta(listaContratoVentaFacturaContratoVenta);
/* 160:    */     }
/* 161:186 */     Object cqDetalleValoresContratoVenta = criteriaBuilder.createQuery(DetalleValoresContratoVenta.class);
/* 162:187 */     Root<DetalleValoresContratoVenta> fromDetalleValoresContratoVenta = ((CriteriaQuery)cqDetalleValoresContratoVenta).from(DetalleValoresContratoVenta.class);
/* 163:    */     
/* 164:189 */     ((CriteriaQuery)cqDetalleValoresContratoVenta).where(criteriaBuilder.equal(fromDetalleValoresContratoVenta.join("contratoVenta"), auxContratoVenta));
/* 165:190 */     CriteriaQuery<DetalleValoresContratoVenta> selectDetalleValoresContratoVenta = ((CriteriaQuery)cqDetalleValoresContratoVenta).select(fromDetalleValoresContratoVenta);
/* 166:    */     
/* 167:192 */     selectDetalleValoresContratoVenta.orderBy(new Order[] { criteriaBuilder.asc(fromDetalleValoresContratoVenta.get("fecha")) });
/* 168:    */     
/* 169:194 */     List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta = this.em.createQuery(selectDetalleValoresContratoVenta).getResultList();
/* 170:195 */     auxContratoVenta.setListaDetalleValoresContratoVenta(listaDetalleValoresContratoVenta);
/* 171:    */     
/* 172:197 */     return auxContratoVenta;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List getContratoVenta(ContratoVenta contratoVenta, Organizacion organizacion, Sucursal sucursal)
/* 176:    */     throws ExcepcionAS2
/* 177:    */   {
/* 178:206 */     List<Object[]> lista = new ArrayList();
/* 179:    */     
/* 180:208 */     StringBuilder sql = new StringBuilder();
/* 181:    */     
/* 182:210 */     sql.append(" SELECT cv.numero, e.nombreFiscal, cv.fecha, cv.fechaVencimiento, cv.descripcion, subemp.empresaFinal ");
/* 183:211 */     sql.append(" FROM ContratoVenta cv ");
/* 184:212 */     sql.append(" LEFT JOIN cv.empresa e ");
/* 185:213 */     sql.append(" LEFT join e.cliente cl ");
/* 186:214 */     sql.append(" LEFT JOIN cv.subempresa subemp ");
/* 187:    */     
/* 188:216 */     sql.append(" WHERE cv.idOrganizacion = :idOrganizacion ");
/* 189:224 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 190:225 */       sql.append(" AND cv.idContratoVenta = :idContratoVenta ");
/* 191:    */     }
/* 192:228 */     sql.append(" GROUP BY cv.numero, e.nombreFiscal, cv.fecha, cv.fechaVencimiento, cv.descripcion, subemp.empresaFinal ");
/* 193:    */     
/* 194:230 */     Query query = this.em.createQuery(sql.toString());
/* 195:231 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 196:239 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 197:240 */       query.setParameter("idContratoVenta", Integer.valueOf(contratoVenta.getIdContratoVenta()));
/* 198:    */     }
/* 199:243 */     List<Object[]> cabecera = query.getResultList();
/* 200:    */     
/* 201:    */ 
/* 202:246 */     StringBuilder sql2 = new StringBuilder();
/* 203:    */     
/* 204:248 */     sql2.append(" SELECT  dcv ");
/* 205:249 */     sql2.append(" FROM DetalleContratoVenta dcv ");
/* 206:250 */     sql2.append(" INNER JOIN FETCH dcv.producto ");
/* 207:    */     
/* 208:252 */     sql2.append(" WHERE dcv.idOrganizacion = :idOrganizacion ");
/* 209:260 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 210:261 */       sql2.append(" AND dcv.contratoVenta = :contratoVenta ");
/* 211:    */     }
/* 212:263 */     sql2.append(" ORDER BY dcv.fechaDesde  ");
/* 213:    */     
/* 214:265 */     Query query2 = this.em.createQuery(sql2.toString());
/* 215:266 */     query2.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 216:274 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 217:275 */       query2.setParameter("contratoVenta", contratoVenta);
/* 218:    */     }
/* 219:278 */     List<DetalleContratoVenta> listaDCV = query2.getResultList();
/* 220:    */     
/* 221:    */ 
/* 222:    */ 
/* 223:282 */     StringBuilder sql1 = new StringBuilder();
/* 224:    */     
/* 225:284 */     sql1.append(" SELECT ldfcv ");
/* 226:285 */     sql1.append(" FROM DetallesFacturaContratoVenta ldfcv ");
/* 227:    */     
/* 228:287 */     sql1.append(" WHERE ldfcv.idOrganizacion = :idOrganizacion ");
/* 229:295 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 230:296 */       sql1.append(" AND ldfcv.contratoVenta = :contratoVenta ");
/* 231:    */     }
/* 232:298 */     sql1.append(" ORDER BY ldfcv.fecha  ");
/* 233:299 */     Query query1 = this.em.createQuery(sql1.toString());
/* 234:300 */     query1.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 235:308 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 236:309 */       query1.setParameter("contratoVenta", contratoVenta);
/* 237:    */     }
/* 238:312 */     List<DetallesFacturaContratoVenta> listaDFCV = query1.getResultList();
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:316 */     StringBuilder sql3 = new StringBuilder();
/* 243:    */     
/* 244:318 */     sql3.append(" SELECT ldvcv ");
/* 245:319 */     sql3.append(" FROM DetalleValoresContratoVenta ldvcv ");
/* 246:    */     
/* 247:321 */     sql3.append(" WHERE ldvcv.idOrganizacion = :idOrganizacion ");
/* 248:322 */     sql3.append(" AND (ldvcv.valor != 0 OR ldvcv.valorDevengar != 0  ) ");
/* 249:330 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 250:331 */       sql3.append(" AND ldvcv.contratoVenta = :contratoVenta ");
/* 251:    */     }
/* 252:333 */     sql3.append(" ORDER BY ldvcv.fecha  ");
/* 253:    */     
/* 254:335 */     Query query3 = this.em.createQuery(sql3.toString());
/* 255:336 */     query3.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 256:344 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 257:345 */       query3.setParameter("contratoVenta", contratoVenta);
/* 258:    */     }
/* 259:348 */     List<DetalleValoresContratoVenta> listaDVCV = query3.getResultList();
/* 260:352 */     for (Object[] obj : cabecera)
/* 261:    */     {
/* 262:353 */       Object[] object = new Object[9];
/* 263:354 */       object[0] = obj[0];
/* 264:355 */       object[1] = obj[1];
/* 265:356 */       object[2] = obj[2];
/* 266:357 */       object[3] = obj[3];
/* 267:358 */       object[4] = obj[4];
/* 268:359 */       object[5] = listaDCV;
/* 269:360 */       object[6] = listaDFCV;
/* 270:361 */       object[7] = listaDVCV;
/* 271:362 */       object[8] = obj[5];
/* 272:    */       
/* 273:364 */       lista.add(object);
/* 274:    */     }
/* 275:368 */     return lista;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List<Object[]> getValoresDevengados(Date fecha, Date fechaHasta, Empresa empresa, Organizacion organizacion, Sucursal sucursal, boolean fechaFactura)
/* 279:    */   {
/* 280:376 */     List<Object[]> lista = new ArrayList();
/* 281:377 */     StringBuilder sql = new StringBuilder();
/* 282:    */     
/* 283:379 */     sql.append(" SELECT cv.idContratoVenta, cv.numero ,CASE WHEN fc IS NULL then '' else fc.numero end, CASE WHEN ncc IS NULL then '' else ncc.numero end ,ldvcv.fecha, ldvcv.valorDevengar, e.nombreFiscal, CASE WHEN ncc IS NULL then fc.fecha else ncc.fecha end ");
/* 284:380 */     sql.append(" FROM ContratoVenta cv ");
/* 285:381 */     sql.append(" LEFT JOIN cv.listaDetalleValoresContratoVenta ldvcv ");
/* 286:382 */     sql.append(" LEFT JOIN cv.empresa e ");
/* 287:383 */     sql.append(" LEFT JOIN ldvcv.facturaCliente fc ");
/* 288:384 */     sql.append(" LEFT JOIN ldvcv.notaCreditoCliente ncc ");
/* 289:385 */     sql.append(" LEFT JOIN ldvcv.comprobante c ");
/* 290:    */     
/* 291:387 */     sql.append(" WHERE cv.idOrganizacion = :idOrganizacion ");
/* 292:388 */     if (fechaFactura) {
/* 293:389 */       sql.append(" AND c.fecha >= :fecha and c.fecha <= :fechaHasta ");
/* 294:    */     } else {
/* 295:391 */       sql.append(" AND ldvcv.fecha >= :fecha and ldvcv.fecha <= :fechaHasta ");
/* 296:    */     }
/* 297:393 */     sql.append(" AND ldvcv.indicadorDevengado = true ");
/* 298:395 */     if ((null != empresa) && (empresa.getIdEmpresa() != 0)) {
/* 299:396 */       sql.append(" AND cv.empresa = :empresa ");
/* 300:    */     }
/* 301:399 */     Query query = this.em.createQuery(sql.toString());
/* 302:    */     
/* 303:401 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 304:403 */     if ((null != empresa) && (empresa.getIdEmpresa() != 0)) {
/* 305:404 */       query.setParameter("empresa", empresa);
/* 306:    */     }
/* 307:407 */     query.setParameter("fecha", fecha);
/* 308:408 */     query.setParameter("fechaHasta", fechaHasta);
/* 309:    */     
/* 310:410 */     List<Object[]> listaAux = query.getResultList();
/* 311:412 */     for (Object[] objeto : listaAux)
/* 312:    */     {
/* 313:413 */       Object[] obj2 = new Object[8];
/* 314:414 */       obj2[0] = objeto[1];
/* 315:415 */       obj2[1] = objeto[2];
/* 316:416 */       obj2[2] = objeto[3];
/* 317:417 */       obj2[3] = objeto[4];
/* 318:418 */       obj2[4] = objeto[5];
/* 319:419 */       obj2[5] = objeto[6];
/* 320:420 */       obj2[6] = BigDecimal.ZERO;
/* 321:421 */       obj2[7] = objeto[7];
/* 322:    */       
/* 323:    */ 
/* 324:424 */       StringBuilder sql1 = new StringBuilder();
/* 325:425 */       sql1.append("select SUM(dvcv.valorDevengar) from DetalleValoresContratoVenta dvcv");
/* 326:426 */       sql1.append(" LEFT JOIN dvcv.contratoVenta cv ");
/* 327:427 */       sql1.append(" INNER JOIN dvcv.facturaCliente fc ");
/* 328:428 */       sql1.append(" WHERE fc.numero = :numerofactura ");
/* 329:429 */       sql1.append(" and dvcv.fecha < :fecha ");
/* 330:430 */       sql1.append(" and dvcv.indicadorDevengado = true");
/* 331:    */       
/* 332:432 */       Query query1 = this.em.createQuery(sql1.toString());
/* 333:433 */       query1.setParameter("numerofactura", objeto[2]);
/* 334:434 */       query1.setParameter("fecha", fecha);
/* 335:    */       
/* 336:436 */       List lista1 = query1.getResultList();
/* 337:438 */       if ((lista1 != null) && (lista1.size() > 0)) {
/* 338:439 */         obj2[6] = ((BigDecimal)lista1.get(0));
/* 339:    */       }
/* 340:441 */       lista.add(obj2);
/* 341:    */     }
/* 342:444 */     return lista;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public List contratoVentaFacturadoVSNoFacturado(ContratoVenta contratoVenta, Organizacion organizacion, Sucursal sucursal)
/* 346:    */     throws ExcepcionAS2
/* 347:    */   {
/* 348:451 */     StringBuilder sql = new StringBuilder();
/* 349:    */     
/* 350:453 */     sql.append(" SELECT cv.numero, e.nombreFiscal, cv.fecha, cv.fechaVencimiento, cv.descripcion, ");
/* 351:454 */     sql.append(" ldfcv.valor, ldfcv.fecha, ldfcv.numeroCuota, ldfcv.indicadorFacturado ");
/* 352:455 */     sql.append(" FROM ContratoVenta cv ");
/* 353:456 */     sql.append(" INNER JOIN cv.listaDetallesFacturaContratoVenta ldfcv ");
/* 354:457 */     sql.append(" LEFT JOIN cv.empresa e ");
/* 355:458 */     sql.append(" LEFT JOIN e.cliente cl ");
/* 356:    */     
/* 357:460 */     sql.append(" WHERE cv.idOrganizacion = :idOrganizacion ");
/* 358:468 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 359:469 */       sql.append(" AND cv.idContratoVenta = :idContratoVenta ");
/* 360:    */     }
/* 361:472 */     sql.append(" GROUP BY cv.numero, e.nombreFiscal, cv.fecha, cv.fechaVencimiento, cv.descripcion, ldfcv.valor, ldfcv.fecha, ldfcv.numeroCuota, ldfcv.indicadorFacturado ");
/* 362:473 */     sql.append(" ORDER BY ldfcv.numeroCuota ");
/* 363:    */     
/* 364:475 */     Query query = this.em.createQuery(sql.toString());
/* 365:476 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 366:484 */     if ((null != contratoVenta) && (contratoVenta.getIdContratoVenta() != 0)) {
/* 367:485 */       query.setParameter("idContratoVenta", Integer.valueOf(contratoVenta.getIdContratoVenta()));
/* 368:    */     }
/* 369:488 */     List<Object[]> cabecera = query.getResultList();
/* 370:    */     
/* 371:    */ 
/* 372:491 */     return cabecera;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public List getContratoVentaDevengado(InterfazContableProceso interfazContableProceso, Organizacion organizacion, Sucursal sucursal)
/* 376:    */     throws ExcepcionAS2
/* 377:    */   {
/* 378:500 */     StringBuilder sql = new StringBuilder();
/* 379:    */     
/* 380:502 */     sql.append(" SELECT e.nombreFiscal, cv.numero, cv.fecha, cv.fechaVencimiento, dvcv.fecha, dvcv.valorDevengar, sum(dcv.precioLinea) ");
/* 381:503 */     sql.append(" FROM DetalleValoresContratoVenta dvcv ");
/* 382:504 */     sql.append(" INNER JOIN dvcv.contratoVenta cv ");
/* 383:505 */     sql.append(" LEFT JOIN cv.empresa e ");
/* 384:506 */     sql.append(" LEFT JOIN cv.listaDetalleContratoVenta dcv ");
/* 385:507 */     sql.append(" LEFT JOIN dvcv.interfazContableProceso icp ");
/* 386:    */     
/* 387:509 */     sql.append(" WHERE cv.idOrganizacion = :idOrganizacion ");
/* 388:511 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 389:512 */       sql.append(" AND cv.sucursal = :sucursal ");
/* 390:    */     }
/* 391:515 */     if ((null != interfazContableProceso) && (interfazContableProceso.getIdInterfazContableProceso() != 0)) {
/* 392:516 */       sql.append(" AND dvcv.interfazContableProceso = :interfazContableProceso ");
/* 393:    */     }
/* 394:519 */     sql.append(" GROUP BY e.nombreFiscal, cv.numero, cv.fecha, cv.fechaVencimiento, dvcv.fecha, dvcv.valorDevengar ");
/* 395:    */     
/* 396:521 */     Query query = this.em.createQuery(sql.toString());
/* 397:522 */     query.setParameter("idOrganizacion", Integer.valueOf(organizacion.getIdOrganizacion()));
/* 398:524 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 399:525 */       query.setParameter("sucursal", sucursal);
/* 400:    */     }
/* 401:528 */     if ((null != interfazContableProceso) && (interfazContableProceso.getIdInterfazContableProceso() != 0)) {
/* 402:529 */       query.setParameter("interfazContableProceso", interfazContableProceso);
/* 403:    */     }
/* 404:532 */     List<Object[]> lista = query.getResultList();
/* 405:    */     
/* 406:534 */     return lista;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public HashMap<String, String> getContratosEnFactura(int idOrganizacion, int idFacturaCliente)
/* 410:    */   {
/* 411:540 */     HashMap<String, String> hmContratos = new HashMap();
/* 412:541 */     StringBuilder sql = new StringBuilder();
/* 413:    */     
/* 414:543 */     sql.append(" SELECT cv.idContratoVenta, cv.numero, fc.numero");
/* 415:544 */     sql.append(" FROM ContratoVenta cv ");
/* 416:545 */     sql.append(" LEFT JOIN cv.listaDetalleValoresContratoVenta ldvcv ");
/* 417:546 */     sql.append(" LEFT JOIN ldvcv.facturaCliente fc ");
/* 418:547 */     sql.append(" WHERE cv.idOrganizacion = :idOrganizacion ");
/* 419:548 */     sql.append(" AND fc.idFacturaCliente = :idFacturaCliente ");
/* 420:    */     
/* 421:550 */     Query query = this.em.createQuery(sql.toString());
/* 422:    */     
/* 423:552 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 424:553 */     query.setParameter("idFacturaCliente", Integer.valueOf(idFacturaCliente));
/* 425:554 */     List<Object[]> lista = query.getResultList();
/* 426:555 */     for (Object[] objects : lista) {
/* 427:556 */       if (!hmContratos.containsKey((String)objects[1] + "~" + (String)objects[2])) {
/* 428:557 */         hmContratos.put((String)objects[1] + "~" + (String)objects[2], (String)objects[1]);
/* 429:    */       }
/* 430:    */     }
/* 431:560 */     return hmContratos;
/* 432:    */   }
/* 433:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ContratoVentaDao
 * JD-Core Version:    0.7.0.1
 */