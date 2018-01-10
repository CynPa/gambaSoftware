/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.entities.AjustePrefacturaCliente;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.DetalleAjustePrefacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   8:    */ import com.asinfo.as2.entities.Impuesto;
/*   9:    */ import com.asinfo.as2.entities.ImpuestoProductoPrefacturaCliente;
/*  10:    */ import com.asinfo.as2.entities.PrefacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.Iterator;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ import javax.persistence.EntityManager;
/*  24:    */ import javax.persistence.NoResultException;
/*  25:    */ import javax.persistence.Query;
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
/*  38:    */ public class PrefacturaClienteDao
/*  39:    */   extends AbstractDaoAS2<PrefacturaCliente>
/*  40:    */ {
/*  41:    */   @EJB
/*  42:    */   private DetalleAjustePrefacturaClienteDao detalleAjustePrefacturaClienteDao;
/*  43:    */   @EJB
/*  44:    */   private AjustePrefacturaClienteDao ajustePrefacturaClienteDao;
/*  45:    */   
/*  46:    */   public PrefacturaClienteDao()
/*  47:    */   {
/*  48: 59 */     super(PrefacturaCliente.class);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<PrefacturaCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  52:    */   {
/*  53: 65 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  54: 66 */     CriteriaQuery<PrefacturaCliente> criteriaQuery = criteriaBuilder.createQuery(PrefacturaCliente.class);
/*  55: 67 */     Root<PrefacturaCliente> from = criteriaQuery.from(PrefacturaCliente.class);
/*  56: 68 */     from.fetch("documento", JoinType.LEFT);
/*  57: 69 */     from.fetch("empresa", JoinType.LEFT);
/*  58: 70 */     from.fetch("facturaCliente", JoinType.LEFT);
/*  59:    */     
/*  60: 72 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  61: 73 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  62:    */     
/*  63: 75 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  64:    */     
/*  65: 77 */     CriteriaQuery<PrefacturaCliente> select = criteriaQuery.select(from);
/*  66:    */     
/*  67: 79 */     TypedQuery<PrefacturaCliente> typedQuery = this.em.createQuery(select);
/*  68: 80 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  69:    */     
/*  70: 82 */     return typedQuery.getResultList();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<PrefacturaCliente> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  74:    */   {
/*  75: 92 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  76: 93 */     CriteriaQuery<PrefacturaCliente> criteriaQuery = criteriaBuilder.createQuery(PrefacturaCliente.class);
/*  77: 94 */     Root<PrefacturaCliente> from = criteriaQuery.from(PrefacturaCliente.class);
/*  78: 95 */     from.fetch("empresa", JoinType.LEFT);
/*  79: 96 */     from.fetch("zona", JoinType.LEFT);
/*  80: 97 */     from.fetch("canal", JoinType.LEFT);
/*  81: 98 */     from.fetch("condicionPago", JoinType.LEFT);
/*  82: 99 */     from.fetch("direccionEmpresa", JoinType.LEFT);
/*  83:100 */     from.fetch("agenteComercial", JoinType.LEFT);
/*  84:101 */     from.fetch("subempresa", JoinType.LEFT);
/*  85:    */     
/*  86:103 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  87:104 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  88:    */     
/*  89:106 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  90:    */     
/*  91:108 */     CriteriaQuery<PrefacturaCliente> select = criteriaQuery.select(from);
/*  92:109 */     TypedQuery<PrefacturaCliente> typedQuery = this.em.createQuery(select);
/*  93:    */     
/*  94:111 */     return typedQuery.getResultList();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public PrefacturaCliente cargarDetalle(int idPrefacturaCliente)
/*  98:    */   {
/*  99:121 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 100:    */     
/* 101:    */ 
/* 102:124 */     CriteriaQuery<PrefacturaCliente> cqCabecera = criteriaBuilder.createQuery(PrefacturaCliente.class);
/* 103:125 */     Root<PrefacturaCliente> fromCabecera = cqCabecera.from(PrefacturaCliente.class);
/* 104:126 */     fromCabecera.fetch("sucursal", JoinType.INNER);
/* 105:127 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.INNER);
/* 106:128 */     Fetch<Object, Object> cliente = empresa.fetch("cliente", JoinType.LEFT);
/* 107:129 */     cliente.fetch("listaPrecios", JoinType.LEFT);
/* 108:130 */     cliente.fetch("listaDescuentos", JoinType.LEFT);
/* 109:131 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/* 110:132 */     fromCabecera.fetch("subempresa", JoinType.LEFT);
/* 111:133 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.INNER);
/* 112:134 */     documento.fetch("secuencia", JoinType.INNER);
/* 113:135 */     fromCabecera.fetch("zona", JoinType.LEFT);
/* 114:136 */     Fetch<Object, Object> direccionEmpresa = fromCabecera.fetch("direccionEmpresa", JoinType.INNER);
/* 115:137 */     direccionEmpresa.fetch("ubicacion", JoinType.LEFT);
/* 116:138 */     fromCabecera.fetch("agenteComercial", JoinType.LEFT);
/* 117:139 */     fromCabecera.fetch("bodega", JoinType.INNER);
/* 118:    */     
/* 119:141 */     Path<Integer> pathId = fromCabecera.get("idPrefacturaCliente");
/* 120:142 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPrefacturaCliente)));
/* 121:143 */     CriteriaQuery<PrefacturaCliente> selectPrefacturaCliente = cqCabecera.select(fromCabecera);
/* 122:    */     
/* 123:145 */     PrefacturaCliente prefacturaCliente = (PrefacturaCliente)this.em.createQuery(selectPrefacturaCliente).getSingleResult();
/* 124:    */     
/* 125:    */ 
/* 126:148 */     CriteriaQuery<AjustePrefacturaCliente> cqAjustePrefacturaCliente = criteriaBuilder.createQuery(AjustePrefacturaCliente.class);
/* 127:149 */     Root<AjustePrefacturaCliente> fromAjustePrefactura = cqAjustePrefacturaCliente.from(AjustePrefacturaCliente.class);
/* 128:150 */     fromAjustePrefactura.fetch("asiento", JoinType.LEFT).fetch("sucursal", JoinType.LEFT);
/* 129:151 */     Path<Integer> pathIdAjustePrefacturaCliente = fromAjustePrefactura.join("prefacturaCliente").get("idPrefacturaCliente");
/* 130:152 */     Path<Boolean> pathAjusteActivo = fromAjustePrefactura.get("activo");
/* 131:153 */     cqAjustePrefacturaCliente.where(new Predicate[] { criteriaBuilder.equal(pathIdAjustePrefacturaCliente, Integer.valueOf(idPrefacturaCliente)), criteriaBuilder
/* 132:154 */       .equal(pathAjusteActivo, Boolean.valueOf(true)) });
/* 133:    */     
/* 134:156 */     CriteriaQuery<AjustePrefacturaCliente> selectAjustePrefacturaCliente = cqAjustePrefacturaCliente.select(fromAjustePrefactura);
/* 135:157 */     List<AjustePrefacturaCliente> listaAjustePrefacturaCliente = this.em.createQuery(selectAjustePrefacturaCliente).getResultList();
/* 136:    */     
/* 137:159 */     this.em.detach(prefacturaCliente);
/* 138:160 */     prefacturaCliente.setListaAjustePrefacturaCliente(listaAjustePrefacturaCliente);
/* 139:163 */     for (Iterator localIterator1 = listaAjustePrefacturaCliente.iterator(); localIterator1.hasNext();)
/* 140:    */     {
/* 141:163 */       ajustePrefacturaCliente = (AjustePrefacturaCliente)localIterator1.next();
/* 142:164 */       for (DetalleAjustePrefacturaCliente detalleAjustePrefactura : ajustePrefacturaCliente.getListaDetalleAjustePrefacturaCliente())
/* 143:    */       {
/* 144:166 */         CriteriaQuery<DetalleAjustePrefacturaCliente> cqDetalleAjustePrefactura = criteriaBuilder.createQuery(DetalleAjustePrefacturaCliente.class);
/* 145:    */         
/* 146:168 */         Root<DetalleAjustePrefacturaCliente> fromDetalleAjustePrefactura = cqDetalleAjustePrefactura.from(DetalleAjustePrefacturaCliente.class);
/* 147:169 */         fromDetalleAjustePrefactura.fetch("producto", JoinType.LEFT);
/* 148:170 */         fromDetalleAjustePrefactura.fetch("unidadVenta", JoinType.LEFT);
/* 149:171 */         Fetch<Object, Object> detalleDespachoCliente = fromDetalleAjustePrefactura.fetch("detalleDespachoCliente", JoinType.LEFT);
/* 150:172 */         detalleDespachoCliente.fetch("despachoCliente", JoinType.LEFT);
/* 151:    */         
/* 152:174 */         Path<Integer> pathIdDetalleAjustePrefactura = fromDetalleAjustePrefactura.join("ajustePrefacturaCliente").get("idAjustePrefacturaCliente");
/* 153:    */         
/* 154:176 */         cqDetalleAjustePrefactura.where(criteriaBuilder.equal(pathIdDetalleAjustePrefactura, 
/* 155:177 */           Integer.valueOf(ajustePrefacturaCliente.getIdAjustePrefacturaCliente())));
/* 156:    */         
/* 157:179 */         CriteriaQuery<DetalleAjustePrefacturaCliente> selectDetalleAjustePrefactura = cqDetalleAjustePrefactura.select(fromDetalleAjustePrefactura);
/* 158:    */         
/* 159:181 */         List<DetalleAjustePrefacturaCliente> listaDetalleAjustePrefacturaCliente = this.em.createQuery(selectDetalleAjustePrefactura).getResultList();
/* 160:182 */         ajustePrefacturaCliente.setListaDetalleAjustePrefacturaCliente(listaDetalleAjustePrefacturaCliente);
/* 161:    */         
/* 162:    */ 
/* 163:185 */         CriteriaQuery<ImpuestoProductoPrefacturaCliente> cqImpuestoProductoPrefactura = criteriaBuilder.createQuery(ImpuestoProductoPrefacturaCliente.class);
/* 164:    */         
/* 165:187 */         Root<ImpuestoProductoPrefacturaCliente> fromImpuestoProductoPrefactura = cqImpuestoProductoPrefactura.from(ImpuestoProductoPrefacturaCliente.class);
/* 166:188 */         fromImpuestoProductoPrefactura.fetch("impuesto", JoinType.LEFT);
/* 167:189 */         Path<Integer> pathIdImpuestoProductoPrefactura = fromImpuestoProductoPrefactura.join("detalleAjustePrefacturaCliente").get("idDetalleAjustePrefacturaCliente");
/* 168:    */         
/* 169:191 */         cqImpuestoProductoPrefactura.where(criteriaBuilder.equal(pathIdImpuestoProductoPrefactura, 
/* 170:192 */           Integer.valueOf(detalleAjustePrefactura.getIdDetalleAjustePrefacturaCliente())));
/* 171:    */         
/* 172:194 */         CriteriaQuery<ImpuestoProductoPrefacturaCliente> selectImpuestoProductoPrefactura = cqImpuestoProductoPrefactura.select(fromImpuestoProductoPrefactura);
/* 173:    */         
/* 174:196 */         List<ImpuestoProductoPrefacturaCliente> listaImpuestoProductoPrefacturaCliente = this.em.createQuery(selectImpuestoProductoPrefactura).getResultList();
/* 175:197 */         detalleAjustePrefactura.setListaImpuestoProductoPrefacturaCliente(listaImpuestoProductoPrefacturaCliente);
/* 176:    */       }
/* 177:    */     }
/* 178:    */     AjustePrefacturaCliente ajustePrefacturaCliente;
/* 179:206 */     return prefacturaCliente;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List<AjustePrefacturaCliente> getListaAjustePrefacturaCliente(int idPrefacturaCliente)
/* 183:    */   {
/* 184:217 */     StringBuilder sql = new StringBuilder();
/* 185:218 */     sql.append(" SELECT a FROM AjustePrefacturaCliente a ");
/* 186:219 */     sql.append(" LEFT JOIN FETCH a.prefacturaCliente pre ");
/* 187:220 */     sql.append(" LEFT JOIN FETCH pre.documento doc ");
/* 188:221 */     sql.append(" LEFT JOIN FETCH a.asiento asi ");
/* 189:222 */     sql.append(" LEFT JOIN FETCH asi.tipoAsiento ta ");
/* 190:223 */     sql.append(" WHERE a.prefacturaCliente.idPrefacturaCliente = :idPrefacturaCliente ");
/* 191:224 */     sql.append(" ORDER BY a.fecha");
/* 192:    */     
/* 193:226 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPrefacturaCliente", Integer.valueOf(idPrefacturaCliente));
/* 194:    */     
/* 195:228 */     return query.getResultList();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void actualizarAjusteActivo(int idPrefacturaCliente, int idAjustePrefacturaCliente)
/* 199:    */   {
/* 200:238 */     StringBuilder sql = new StringBuilder();
/* 201:239 */     sql.append(" UPDATE AjustePrefacturaCliente a ");
/* 202:240 */     sql.append(" SET a.activo = false ");
/* 203:241 */     sql.append(" WHERE a.idAjustePrefacturaCliente <> :idAjustePrefacturaCliente ");
/* 204:242 */     sql.append(" AND a.prefacturaCliente.idPrefacturaCliente = :idPrefacturaCliente ");
/* 205:243 */     sql.append(" AND a.activo = true ");
/* 206:    */     
/* 207:245 */     Query query = this.em.createQuery(sql.toString());
/* 208:246 */     query.setParameter("idAjustePrefacturaCliente", Integer.valueOf(idAjustePrefacturaCliente));
/* 209:247 */     query.setParameter("idPrefacturaCliente", Integer.valueOf(idPrefacturaCliente));
/* 210:    */     
/* 211:249 */     query.executeUpdate();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<PrefacturaCliente> getListaPrefacturaCliente(FacturaCliente facturaCliente)
/* 215:    */   {
/* 216:260 */     StringBuilder sql = new StringBuilder();
/* 217:261 */     sql.append(" SELECT pre FROM PrefacturaCliente pre ");
/* 218:262 */     sql.append(" LEFT JOIN FETCH pre.empresa em ");
/* 219:263 */     sql.append(" WHERE pre.facturaCliente = :facturaCliente ");
/* 220:264 */     sql.append(" ORDER BY pre.numero");
/* 221:    */     
/* 222:266 */     Query query = this.em.createQuery(sql.toString()).setParameter("facturaCliente", facturaCliente);
/* 223:267 */     return query.getResultList();
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void liberarPrefacturaCliente(FacturaCliente facturaCliente)
/* 227:    */   {
/* 228:277 */     StringBuilder sql = new StringBuilder();
/* 229:278 */     sql.append(" UPDATE PrefacturaCliente pre SET facturaCliente = NULL, estado = :estado");
/* 230:279 */     sql.append(" WHERE pre.facturaCliente = :facturaCliente ");
/* 231:    */     
/* 232:281 */     Query query = this.em.createQuery(sql.toString());
/* 233:282 */     query.setParameter("facturaCliente", facturaCliente);
/* 234:283 */     query.setParameter("estado", Estado.ELABORADO);
/* 235:    */     
/* 236:285 */     query.executeUpdate();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public AjustePrefacturaCliente getAjusteActivo(int idPrefacturaCliente)
/* 240:    */   {
/* 241:296 */     StringBuilder sql = new StringBuilder();
/* 242:297 */     sql.append(" SELECT a FROM AjustePrefacturaCliente a ");
/* 243:298 */     sql.append(" JOIN a.prefacturaCliente pf ");
/* 244:299 */     sql.append(" LEFT JOIN FETCH a.asiento asi ");
/* 245:300 */     sql.append(" WHERE pf.idPrefacturaCliente = :idPrefacturaCliente ");
/* 246:301 */     sql.append(" AND a.activo = true ");
/* 247:    */     
/* 248:303 */     Query query = this.em.createQuery(sql.toString());
/* 249:304 */     query.setParameter("idPrefacturaCliente", Integer.valueOf(idPrefacturaCliente));
/* 250:    */     try
/* 251:    */     {
/* 252:307 */       return (AjustePrefacturaCliente)query.getSingleResult();
/* 253:    */     }
/* 254:    */     catch (NoResultException e) {}
/* 255:309 */     return null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public List<DetalleInterfazContableProceso> getInterfazVentasDimensiones(AjustePrefacturaCliente ajustePrefactura, ProcesoContabilizacionEnum procesoContabilizacion)
/* 259:    */     throws ExcepcionAS2Financiero
/* 260:    */   {
/* 261:325 */     String valores = "";
/* 262:326 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoContabilizacionEnum[procesoContabilizacion.ordinal()])
/* 263:    */     {
/* 264:    */     case 1: 
/* 265:328 */       valores = "sum(dpc.cantidad*((dpc.precio-dpc.descuento)))";
/* 266:329 */       break;
/* 267:    */     case 2: 
/* 268:332 */       valores = "sum(dpc.cantidad*dpc.precio)";
/* 269:333 */       break;
/* 270:    */     case 3: 
/* 271:336 */       valores = "sum(dpc.cantidad*dpc.descuento)";
/* 272:337 */       break;
/* 273:    */     default: 
/* 274:339 */       valores = "";
/* 275:    */     }
/* 276:343 */     StringBuilder sql = new StringBuilder();
/* 277:    */     try
/* 278:    */     {
/* 279:347 */       sql.append(" SELECT new DetalleInterfazContableProcesoPrefacturaCliente(d.idDocumento,d.nombre, s.idSucursal,s.nombre, ce.idCategoriaEmpresa,ce.nombre, e.idEmpresa,e.nombreFiscal,");
/* 280:348 */       sql.append(" cp.idCategoriaProducto, cp.nombre, sp.idSubcategoriaProducto, sp.nombre, p.idProducto,p.nombre, c.idCanal,c.nombre, se.idSubempresa,se.empresaFinal, z.idZona, z.nombre,");
/* 281:349 */       sql.append(" CONCAT(d.nombre,' #', pc.numero), " + valores + " )");
/* 282:350 */       sql.append(" FROM DetalleAjustePrefacturaCliente dpc ");
/* 283:351 */       sql.append(" INNER JOIN dpc.ajustePrefacturaCliente apc ");
/* 284:352 */       sql.append(" INNER JOIN apc.prefacturaCliente pc ");
/* 285:353 */       sql.append(" INNER JOIN dpc.producto p ");
/* 286:354 */       sql.append(" INNER JOIN p.subcategoriaProducto sp ");
/* 287:355 */       sql.append(" INNER JOIN sp.categoriaProducto cp ");
/* 288:356 */       sql.append(" INNER JOIN pc.documento d ");
/* 289:357 */       sql.append(" INNER JOIN pc.empresa e ");
/* 290:358 */       sql.append(" INNER JOIN pc.sucursal s ");
/* 291:359 */       sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 292:360 */       sql.append(" LEFT JOIN pc.canal c ");
/* 293:361 */       sql.append(" LEFT JOIN pc.zona z ");
/* 294:362 */       sql.append(" LEFT JOIN pc.subempresa se");
/* 295:363 */       sql.append(" WHERE apc.idAjustePrefacturaCliente=:idAjustePrefacturaCliente");
/* 296:364 */       sql.append(" GROUP BY d.idDocumento,d.nombre, s.idSucursal,s.nombre, ce.idCategoriaEmpresa,ce.nombre, e.idEmpresa,e.nombreFiscal,");
/* 297:365 */       sql.append(" cp.idCategoriaProducto,cp.nombre, sp.idSubcategoriaProducto,sp.nombre, p.idProducto,p.nombre, c.idCanal,c.nombre, se.idSubempresa,se.empresaFinal, z.idZona, z.nombre,");
/* 298:366 */       sql.append(" CONCAT(d.nombre,' #', pc.numero)");
/* 299:367 */       sql.append(" HAVING " + valores + " <> 0");
/* 300:    */       
/* 301:369 */       Query query = this.em.createQuery(sql.toString());
/* 302:370 */       query.setParameter("idAjustePrefacturaCliente", Integer.valueOf(ajustePrefactura.getIdAjustePrefacturaCliente()));
/* 303:371 */       return query.getResultList();
/* 304:    */     }
/* 305:    */     catch (IllegalArgumentException e)
/* 306:    */     {
/* 307:374 */       e.printStackTrace();
/* 308:375 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " " + procesoContabilizacion.toString());
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public AjustePrefacturaCliente obtenerAjustePrefacturaActivo(int idPrefacturaCliente)
/* 313:    */   {
/* 314:385 */     StringBuilder sql = new StringBuilder();
/* 315:386 */     sql.append(" SELECT dp from DetalleAjustePrefacturaCliente dp ");
/* 316:387 */     sql.append(" JOIN FETCH dp.producto pr ");
/* 317:388 */     sql.append(" JOIN FETCH pr.unidadVenta u ");
/* 318:389 */     sql.append(" JOIN FETCH pr.subcategoriaProducto sp ");
/* 319:390 */     sql.append(" JOIN FETCH sp.categoriaProducto cp ");
/* 320:391 */     sql.append(" LEFT JOIN FETCH pr.categoriaImpuesto ci ");
/* 321:392 */     sql.append(" JOIN FETCH dp.ajustePrefacturaCliente a ");
/* 322:393 */     sql.append(" JOIN FETCH a.prefacturaCliente p");
/* 323:394 */     sql.append(" JOIN FETCH p.empresa e");
/* 324:395 */     sql.append(" JOIN FETCH e.tipoIdentificacion tid");
/* 325:396 */     sql.append(" JOIN FETCH e.categoriaEmpresa ce");
/* 326:397 */     sql.append(" LEFT JOIN FETCH e.cliente cl");
/* 327:398 */     sql.append(" LEFT JOIN FETCH cl.listaPrecios lp");
/* 328:399 */     sql.append(" LEFT JOIN FETCH cl.listaDescuentos ld");
/* 329:400 */     sql.append(" JOIN FETCH p.condicionPago c");
/* 330:401 */     sql.append(" JOIN FETCH p.direccionEmpresa d");
/* 331:402 */     sql.append(" LEFT JOIN FETCH d.ubicacion ubi");
/* 332:403 */     sql.append(" WHERE p.idPrefacturaCliente = :idPrefacturaCliente AND a.activo = 1");
/* 333:    */     
/* 334:405 */     Query query = this.em.createQuery(sql.toString());
/* 335:406 */     query.setParameter("idPrefacturaCliente", Integer.valueOf(idPrefacturaCliente));
/* 336:    */     
/* 337:408 */     AjustePrefacturaCliente ajuste = null;
/* 338:409 */     for (DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente : new ArrayList(query.getResultList()))
/* 339:    */     {
/* 340:411 */       if (ajuste == null)
/* 341:    */       {
/* 342:412 */         ajuste = detalleAjustePrefacturaCliente.getAjustePrefacturaCliente();
/* 343:413 */         ajuste.setListaDetalleAjustePrefacturaCliente(new ArrayList());
/* 344:    */       }
/* 345:416 */       detalleAjustePrefacturaCliente.getProducto().getId();
/* 346:417 */       detalleAjustePrefacturaCliente.getListaImpuestoProductoPrefacturaCliente().size();
/* 347:419 */       for (ImpuestoProductoPrefacturaCliente impuestoProductoPrefacturaCliente : detalleAjustePrefacturaCliente
/* 348:420 */         .getListaImpuestoProductoPrefacturaCliente()) {
/* 349:421 */         impuestoProductoPrefacturaCliente.getImpuesto().getId();
/* 350:    */       }
/* 351:424 */       ajuste.getListaDetalleAjustePrefacturaCliente().add(detalleAjustePrefacturaCliente);
/* 352:    */     }
/* 353:427 */     return ajuste;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<Object[]> getReportePrefacturaCliente(int idPrefacturaCliente)
/* 357:    */     throws ExcepcionAS2
/* 358:    */   {
/* 359:434 */     StringBuilder sql = new StringBuilder();
/* 360:435 */     sql.append(" SELECT e.nombreFiscal, e.identificacion, e.codigo, e.nombreComercial, CONCAT(u.direccion1, ' ', u.direccion2, ' ', u.direccion3, ' ', u.direccion4), u.direccion5, ");
/* 361:436 */     sql.append(" pfc.fecha, pfc.descuento, pfc.total, pfc.impuesto, pfc.descripcion, pfc.numero, ");
/* 362:437 */     sql.append(" dapfc.cantidad, dapfc.precio, dapfc.descripcion, dapfc.descuento, pr.codigo, pr.nombreComercial, pr.codigoAlterno, pr.codigoBarras, pr.peso, ");
/* 363:438 */     sql.append(" ci.nombre, c.nombre, CONCAT(ag.nombre1, ' ', ag.nombre2), un.nombre, ");
/* 364:439 */     sql.append(" z.nombre, ca.nombre, de.telefono1, de.telefono2 , p.nombre ");
/* 365:440 */     sql.append(" FROM DetalleAjustePrefacturaCliente dapfc ");
/* 366:441 */     sql.append(" LEFT JOIN dapfc.ajustePrefacturaCliente apfc ");
/* 367:442 */     sql.append(" LEFT JOIN apfc.prefacturaCliente pfc ");
/* 368:443 */     sql.append(" LEFT JOIN pfc.empresa e ");
/* 369:444 */     sql.append(" LEFT JOIN pfc.direccionEmpresa de ");
/* 370:445 */     sql.append(" LEFT JOIN de.ubicacion u ");
/* 371:446 */     sql.append(" LEFT JOIN dapfc.producto pr ");
/* 372:447 */     sql.append(" LEFT JOIN de.ciudad ci ");
/* 373:448 */     sql.append(" LEFT JOIN ci.provincia p ");
/* 374:449 */     sql.append(" LEFT JOIN pfc.condicionPago c ");
/* 375:450 */     sql.append(" LEFT JOIN pfc.agenteComercial ag ");
/* 376:451 */     sql.append(" LEFT JOIN dapfc.unidadVenta un ");
/* 377:452 */     sql.append(" LEFT JOIN pfc.zona z ");
/* 378:453 */     sql.append(" LEFT JOIN pfc.canal ca ");
/* 379:454 */     sql.append(" WHERE pfc.idPrefacturaCliente = :idPrefacturaCliente ");
/* 380:455 */     sql.append(" ORDER BY pr.nombreComercial ");
/* 381:    */     
/* 382:457 */     Query query = this.em.createQuery(sql.toString());
/* 383:458 */     query.setParameter("idPrefacturaCliente", Integer.valueOf(idPrefacturaCliente));
/* 384:459 */     return query.getResultList();
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<Asiento> listaCodigosAsiento(int idPrefacturaCliente)
/* 388:    */   {
/* 389:466 */     StringBuilder sql = new StringBuilder();
/* 390:467 */     sql.append(" SELECT a  ");
/* 391:468 */     sql.append(" FROM AjustePrefacturaCliente apc ");
/* 392:469 */     sql.append(" INNER JOIN apc.prefacturaCliente pc ");
/* 393:470 */     sql.append(" INNER JOIN apc.asiento a ");
/* 394:471 */     sql.append(" WHERE pc.idPrefacturaCliente =:idPrefacturaCliente ");
/* 395:    */     
/* 396:473 */     Query query = this.em.createQuery(sql.toString());
/* 397:474 */     query.setParameter("idPrefacturaCliente", Integer.valueOf(idPrefacturaCliente));
/* 398:475 */     return query.getResultList();
/* 399:    */   }
/* 400:    */   
/* 401:    */   public List<PrefacturaCliente> getListaPrefacturaCliente(Date fechaDesde, Date fechaHasta)
/* 402:    */   {
/* 403:487 */     StringBuilder sql = new StringBuilder();
/* 404:488 */     sql.append(" SELECT pre FROM PrefacturaCliente pre ");
/* 405:489 */     sql.append(" WHERE pre.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 406:490 */     sql.append(" AND pre.estado != :estadoAnulado ");
/* 407:    */     
/* 408:492 */     Query query = this.em.createQuery(sql.toString());
/* 409:493 */     query.setParameter("fechaDesde", fechaDesde);
/* 410:494 */     query.setParameter("fechaHasta", fechaHasta);
/* 411:495 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 412:    */     
/* 413:497 */     return query.getResultList();
/* 414:    */   }
/* 415:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PrefacturaClienteDao
 * JD-Core Version:    0.7.0.1
 */