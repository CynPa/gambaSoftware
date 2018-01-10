/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   5:    */ import com.asinfo.as2.clases.ReporteAnticipoCliente;
/*   6:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   9:    */ import com.asinfo.as2.entities.Cobro;
/*  10:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*  11:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  15:    */ import com.asinfo.as2.entities.FormaPago;
/*  16:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  17:    */ import com.asinfo.as2.entities.Secuencia;
/*  18:    */ import com.asinfo.as2.entities.Sucursal;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import java.math.BigDecimal;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.Stateless;
/*  26:    */ import javax.persistence.EntityManager;
/*  27:    */ import javax.persistence.NoResultException;
/*  28:    */ import javax.persistence.Query;
/*  29:    */ import javax.persistence.TypedQuery;
/*  30:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  31:    */ import javax.persistence.criteria.CriteriaQuery;
/*  32:    */ import javax.persistence.criteria.Expression;
/*  33:    */ import javax.persistence.criteria.Fetch;
/*  34:    */ import javax.persistence.criteria.JoinType;
/*  35:    */ import javax.persistence.criteria.Predicate;
/*  36:    */ import javax.persistence.criteria.Root;
/*  37:    */ 
/*  38:    */ @Stateless
/*  39:    */ public class AnticipoClienteDao
/*  40:    */   extends AbstractDaoAS2<AnticipoCliente>
/*  41:    */ {
/*  42:    */   public AnticipoClienteDao()
/*  43:    */   {
/*  44: 53 */     super(AnticipoCliente.class);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<AnticipoCliente> obtenerAnticiposPendientes(int idEmpresa)
/*  48:    */   {
/*  49: 58 */     Query query = this.em.createQuery("SELECT a FROM AnticipoCliente a WHERE a.empresa.idEmpresa=:idEmpresa AND a.saldo > 0");
/*  50: 59 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/*  51: 60 */     return query.getResultList();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<AnticipoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 71 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  57: 72 */     CriteriaQuery<AnticipoCliente> criteriaQuery = criteriaBuilder.createQuery(AnticipoCliente.class);
/*  58: 73 */     Root<AnticipoCliente> from = criteriaQuery.from(AnticipoCliente.class);
/*  59:    */     
/*  60: 75 */     from.fetch("empresa", JoinType.LEFT);
/*  61: 76 */     from.fetch("formaPago", JoinType.LEFT);
/*  62: 77 */     from.fetch("documento", JoinType.LEFT);
/*  63: 78 */     from.fetch("asiento", JoinType.LEFT);
/*  64: 79 */     from.fetch("notaCreditoCliente", JoinType.LEFT);
/*  65: 80 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/*  66: 81 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  67: 82 */     Fetch<Object, Object> asientoDevolucion = from.fetch("asientoDevolucion", JoinType.LEFT);
/*  68: 83 */     asientoDevolucion.fetch("tipoAsiento", JoinType.LEFT);
/*  69: 84 */     from.fetch("sucursal", JoinType.LEFT);
/*  70:    */     
/*  71: 86 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  72:    */     
/*  73: 88 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  74: 89 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  75:    */     
/*  76: 91 */     CriteriaQuery<AnticipoCliente> select = criteriaQuery.select(from);
/*  77:    */     
/*  78: 93 */     TypedQuery<AnticipoCliente> typedQuery = this.em.createQuery(select);
/*  79: 94 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  80:    */     
/*  81: 96 */     return typedQuery.getResultList();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<DetalleInterfazContable> getCuentaAnticipoCliente(AnticipoCliente anticipoCliente)
/*  85:    */     throws ExcepcionAS2Financiero
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:111 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal,CONCAT(do.nombre,' #', ac.numero,' ', ac.descripcion), ac.documentoReferencia,-ac.valor)  FROM AnticipoCliente ac  INNER JOIN ac.documento do  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ce.cuentaContableAnticipoCliente cc  WHERE ac.idAnticipoCliente=:idAnticipoCliente");
/*  90:    */       
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:119 */       query.setParameter("idAnticipoCliente", Integer.valueOf(anticipoCliente.getIdAnticipoCliente()));
/*  98:    */       
/*  99:121 */       return query.getResultList();
/* 100:    */     }
/* 101:    */     catch (IllegalArgumentException e)
/* 102:    */     {
/* 103:123 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableAnticipoCliente");
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<DetalleInterfazContable> getDetalleFormaCobroAnticipoIC(AnticipoCliente anticipoCliente)
/* 108:    */     throws ExcepcionAS2Financiero
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:139 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre,' #', ac.numero,' ', ac.descripcion), ac.documentoReferencia,ac.formaPago.idFormaPago, ac.valor)  FROM AnticipoCliente ac INNER JOIN ac.documento do  INNER JOIN ac.cuentaBancariaOrganizacion cb  INNER JOIN cb.cuentaContableBanco cc  LEFT JOIN ac.empresa em  WHERE ac.idAnticipoCliente=:idAnticipoCliente");
/* 113:    */       
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:147 */       query.setParameter("idAnticipoCliente", Integer.valueOf(anticipoCliente.getIdAnticipoCliente()));
/* 121:    */       
/* 122:149 */       return query.getResultList();
/* 123:    */     }
/* 124:    */     catch (IllegalArgumentException e)
/* 125:    */     {
/* 126:152 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableBancos");
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<DetalleInterfazContable> getDetalleNotaCreditoIC(AnticipoCliente anticipoCliente)
/* 131:    */     throws ExcepcionAS2Financiero
/* 132:    */   {
/* 133:    */     try
/* 134:    */     {
/* 135:168 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre,' #', ac.numero,' ', ac.descripcion), ac.documentoReferencia, ac.valor)  FROM AnticipoCliente ac INNER JOIN ac.documento do  INNER JOIN ac.notaCreditoCliente.motivoNotaCreditoCliente mo  INNER JOIN ac.empresa em  LEFT JOIN mo.cuentaContable cc WHERE ac.idAnticipoCliente=:idAnticipoCliente");
/* 136:    */       
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:174 */       query.setParameter("idAnticipoCliente", Integer.valueOf(anticipoCliente.getIdAnticipoCliente()));
/* 142:    */       
/* 143:176 */       return query.getResultList();
/* 144:    */     }
/* 145:    */     catch (IllegalArgumentException e)
/* 146:    */     {
/* 147:179 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableBancos");
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public AnticipoCliente cargarDetalle(int idAnticipoCliente)
/* 152:    */   {
/* 153:185 */     AnticipoCliente anticipoCliente = (AnticipoCliente)buscarPorId(Integer.valueOf(idAnticipoCliente));
/* 154:186 */     anticipoCliente.getDocumento().getId();
/* 155:187 */     anticipoCliente.getDocumento().getSecuencia().getId();
/* 156:188 */     anticipoCliente.getDocumento().getTipoAsiento();
/* 157:189 */     anticipoCliente.getEmpresa().getId();
/* 158:191 */     if (anticipoCliente.getFormaPago() != null) {
/* 159:192 */       anticipoCliente.getFormaPago().getId();
/* 160:    */     }
/* 161:195 */     if (anticipoCliente.getCuentaBancariaOrganizacion() != null)
/* 162:    */     {
/* 163:196 */       anticipoCliente.getCuentaBancariaOrganizacion().getId();
/* 164:197 */       anticipoCliente.getCuentaBancariaOrganizacion().getCuentaBancaria().getId();
/* 165:198 */       anticipoCliente.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/* 166:200 */       for (FormaPagoCuentaBancariaOrganizacion formaPagoCuenta : anticipoCliente.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/* 167:201 */         formaPagoCuenta.getFormaPago().getId();
/* 168:    */       }
/* 169:    */     }
/* 170:205 */     if (anticipoCliente.getNotaCreditoCliente() != null)
/* 171:    */     {
/* 172:206 */       anticipoCliente.getNotaCreditoCliente().getId();
/* 173:207 */       anticipoCliente.getNotaCreditoCliente().getDocumento().getId();
/* 174:    */     }
/* 175:209 */     if (anticipoCliente.getSucursal() != null) {
/* 176:210 */       anticipoCliente.getSucursal().getId();
/* 177:    */     }
/* 178:213 */     if (anticipoCliente.getAsiento() != null) {
/* 179:214 */       anticipoCliente.getAsiento().getId();
/* 180:    */     }
/* 181:218 */     if (anticipoCliente.getCobro() != null) {
/* 182:219 */       anticipoCliente.getCobro().getId();
/* 183:    */     }
/* 184:223 */     if (anticipoCliente.getAsientoDevolucion() != null) {
/* 185:224 */       anticipoCliente.getAsientoDevolucion().getId();
/* 186:    */     }
/* 187:227 */     if (anticipoCliente.getDocumentoDevolucion() != null) {
/* 188:228 */       anticipoCliente.getDocumentoDevolucion().getId();
/* 189:    */     }
/* 190:231 */     if (anticipoCliente.getCuentaBancariaOrganizacionDevolucion() != null) {
/* 191:232 */       anticipoCliente.getCuentaBancariaOrganizacionDevolucion().getId();
/* 192:    */     }
/* 193:235 */     return anticipoCliente;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigDecimal obtenerSaldoAnticipo(int idCliente, Date fechaHasta)
/* 197:    */   {
/* 198:243 */     BigDecimal resultado = (BigDecimal)this.em.createQuery("SELECT SUM(saldo) FROM AnticipoCliente a  WHERE a.fecha <= :fechaHasta AND a.estado != :estadoAnulado AND a.empresa.idEmpresa= :idCliente").setParameter("fechaHasta", fechaHasta).setParameter("idCliente", Integer.valueOf(idCliente)).setParameter("estadoAnulado", Estado.ANULADO).getSingleResult();
/* 199:244 */     if (resultado == null) {
/* 200:245 */       resultado = BigDecimal.ZERO;
/* 201:    */     }
/* 202:247 */     return resultado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<ReporteAnticipoCliente> obtenerAnticiposClientes(CategoriaEmpresa categoriaEmpresa, Integer idCliente, Date fechaHasta, Integer idOrganizacion)
/* 206:    */   {
/* 207:253 */     StringBuilder sql = new StringBuilder();
/* 208:254 */     sql.append(" SELECT new ReporteAnticipoCliente (e.nombreFiscal, sum(ac.saldo) )");
/* 209:255 */     sql.append(" FROM AnticipoCliente ac ");
/* 210:256 */     sql.append(" INNER JOIN ac.empresa e ");
/* 211:257 */     sql.append(" LEFT JOIN e.categoriaEmpresa ce ");
/* 212:258 */     sql.append(" WHERE ac.estado != :estadoAnulado ");
/* 213:259 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 214:260 */     sql.append(" AND ac.fecha <= :fechaHasta ");
/* 215:261 */     if ((idCliente != null) && (idCliente.intValue() != 0)) {
/* 216:262 */       sql.append(" AND e.idEmpresa  = :idCliente ");
/* 217:    */     }
/* 218:264 */     if (categoriaEmpresa != null) {
/* 219:265 */       sql.append(" AND ce.idCategoriaEmpresa = :idCategoriaEmpresa ");
/* 220:    */     }
/* 221:267 */     sql.append(" group by e.nombreFiscal ");
/* 222:    */     
/* 223:269 */     Query query = this.em.createQuery(sql.toString());
/* 224:270 */     query.setParameter("idOrganizacion", idOrganizacion);
/* 225:271 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 226:272 */     query.setParameter("fechaHasta", fechaHasta);
/* 227:273 */     if ((idCliente != null) && (idCliente.intValue() != 0)) {
/* 228:274 */       query.setParameter("idCliente", idCliente);
/* 229:    */     }
/* 230:276 */     if (categoriaEmpresa != null) {
/* 231:277 */       query.setParameter("idCategoriaEmpresa", Integer.valueOf(categoriaEmpresa.getId()));
/* 232:    */     }
/* 233:279 */     return query.getResultList();
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void actualizarEstado(Integer idAnticipoCliente, Estado estado)
/* 237:    */   {
/* 238:290 */     Query query = this.em.createQuery("UPDATE AnticipoCliente a SET a.estado=:estado WHERE a.idAnticipoCliente=:idAnticipoCliente");
/* 239:291 */     query.setParameter("idAnticipoCliente", idAnticipoCliente);
/* 240:292 */     query.setParameter("estado", estado);
/* 241:293 */     query.executeUpdate();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void actualizarSaldo(int idAnticipoCliente, BigDecimal valor)
/* 245:    */   {
/* 246:304 */     Query query = this.em.createQuery("UPDATE AnticipoCliente a SET a.saldo=a.saldo + :valor WHERE a.idAnticipoCliente=:idAnticipoCliente");
/* 247:305 */     query.setParameter("idAnticipoCliente", Integer.valueOf(idAnticipoCliente));
/* 248:306 */     query.setParameter("valor", valor);
/* 249:307 */     query.executeUpdate();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public List<DetalleInterfazContable> getDetalleFormaCobroDevolucionAnticipoIC(int idAnticipoCliente)
/* 253:    */     throws ExcepcionAS2Financiero
/* 254:    */   {
/* 255:    */     try
/* 256:    */     {
/* 257:323 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable(cc.idCuentaContable,em.nombreFiscal, CONCAT(do.nombre,' #', ac.numero), ac.documentoReferenciaDevolucion, ac.beneficiarioDevolucion ,ac.formaPagoDevolucion.idFormaPago, -ac.valorDevolucion)  FROM AnticipoCliente ac INNER JOIN ac.documentoDevolucion do  INNER JOIN ac.cuentaBancariaOrganizacionDevolucion cb  LEFT JOIN cb.cuentaContableBanco cc  INNER JOIN ac.empresa em  WHERE ac.idAnticipoCliente=:idAnticipoCliente");
/* 258:    */       
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:330 */       query.setParameter("idAnticipoCliente", Integer.valueOf(idAnticipoCliente));
/* 265:    */       
/* 266:332 */       return query.getResultList();
/* 267:    */     }
/* 268:    */     catch (IllegalArgumentException e)
/* 269:    */     {
/* 270:334 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableBanco");
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<DetalleInterfazContable> getCuentaDevolucionAnticipoCliente(int idAnticipoCliente)
/* 275:    */     throws ExcepcionAS2Financiero
/* 276:    */   {
/* 277:    */     try
/* 278:    */     {
/* 279:349 */       Query query = this.em.createQuery("SELECT new DetalleInterfazContable ( \t(CASE WHEN nc IS NULL THEN ccac.idCuentaContable ELSE ccacnc.idCuentaContable END), \tCONCAT(do.nombre,' #', ac.numero), ac.documentoReferenciaDevolucion , '',  ac.valorDevolucion )  FROM AnticipoCliente ac  INNER JOIN ac.documentoDevolucion do  INNER JOIN ac.empresa em  INNER JOIN em.categoriaEmpresa ce  LEFT JOIN ac.notaCreditoCliente nc  LEFT JOIN ce.cuentaContableAnticipoCliente ccac  LEFT JOIN ce.cuentaContableAnticipoClienteNotaCredito ccacnc  WHERE ac.idAnticipoCliente=:idAnticipoCliente");
/* 280:    */       
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:357 */       query.setParameter("idAnticipoCliente", Integer.valueOf(idAnticipoCliente));
/* 288:358 */       return query.getResultList();
/* 289:    */     }
/* 290:    */     catch (IllegalArgumentException e)
/* 291:    */     {
/* 292:361 */       e.printStackTrace();
/* 293:362 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", " cuentaContableAnticipoCliente - cuentaContableAnticipoClienteNotaCredito");
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<DetalleInterfazContableProceso> getInterfazAnticipoClienteDimensiones(List<Integer> listaAnticipoCliente)
/* 298:    */   {
/* 299:379 */     StringBuilder sql = new StringBuilder();
/* 300:    */     
/* 301:381 */     String descripcion = "";
/* 302:382 */     String grupoDescripcion = "";
/* 303:383 */     if (listaAnticipoCliente.size() == 1)
/* 304:    */     {
/* 305:384 */       descripcion = "CONCAT(d.nombre,' #', ac.numero,' ', ac.descripcion, ' - ' ,e.nombreFiscal)";
/* 306:385 */       grupoDescripcion = "," + descripcion;
/* 307:    */     }
/* 308:    */     else
/* 309:    */     {
/* 310:387 */       descripcion = "''";
/* 311:    */     }
/* 312:390 */     sql.append("SELECT new DetalleInterfazContableProcesoAnticipoCliente(CASE WHEN nc IS NULL THEN d.idDocumento ELSE dc.idDocumento END, ac.sucursal.idSucursal, ce.idCategoriaEmpresa, e.idEmpresa,");
/* 313:391 */     sql.append(" " + descripcion + ", SUM(ac.valor) )");
/* 314:392 */     sql.append(" FROM AnticipoCliente ac ");
/* 315:393 */     sql.append(" INNER JOIN ac.documento d ");
/* 316:394 */     sql.append(" INNER JOIN ac.empresa e ");
/* 317:395 */     sql.append(" INNER JOIN e.categoriaEmpresa ce ");
/* 318:396 */     sql.append(" LEFT JOIN ac.notaCreditoCliente nc ");
/* 319:397 */     sql.append(" LEFT JOIN nc.documento dc ");
/* 320:398 */     sql.append(" WHERE ac.idAnticipoCliente in (:listaAnticipoCliente) ");
/* 321:399 */     sql.append(" GROUP BY CASE WHEN nc IS NULL THEN d.idDocumento ELSE dc.idDocumento END, ac.sucursal.idSucursal, ce.idCategoriaEmpresa, e.idEmpresa" + grupoDescripcion);
/* 322:    */     
/* 323:401 */     sql.append(" HAVING SUM(ac.valor) <> 0");
/* 324:    */     
/* 325:403 */     Query query = this.em.createQuery(sql.toString());
/* 326:404 */     query.setParameter("listaAnticipoCliente", listaAnticipoCliente);
/* 327:405 */     return query.getResultList();
/* 328:    */   }
/* 329:    */   
/* 330:    */   public BigDecimal getSaldoAnticipo(AnticipoCliente anticipoCliente)
/* 331:    */   {
/* 332:415 */     Query query = this.em.createQuery("SELECT ac.saldo FROM AnticipoCliente ac WHERE ac = :anticipoCliente ");
/* 333:416 */     query.setParameter("anticipoCliente", anticipoCliente);
/* 334:    */     
/* 335:418 */     return (BigDecimal)query.getSingleResult();
/* 336:    */   }
/* 337:    */   
/* 338:    */   public Long existeCierreCaja(AnticipoCliente anticipoCliente)
/* 339:    */   {
/* 340:424 */     Query query = this.em.createQuery("SELECT COUNT(1) FROM DetalleCierreCaja dcc where dcc.anticipoCliente = :anticipoCliente ");
/* 341:425 */     query.setParameter("anticipoCliente", anticipoCliente);
/* 342:    */     
/* 343:427 */     return (Long)query.getSingleResult();
/* 344:    */   }
/* 345:    */   
/* 346:    */   public AnticipoCliente buscarPorCobro(Cobro cobro)
/* 347:    */   {
/* 348:433 */     StringBuilder sql = new StringBuilder();
/* 349:434 */     sql.append(" SELECT ac ");
/* 350:435 */     sql.append(" FROM AnticipoCliente ac ");
/* 351:436 */     sql.append(" LEFT JOIN ac.cobro c ");
/* 352:437 */     sql.append(" WHERE c = :cobro ");
/* 353:    */     
/* 354:439 */     Query query = this.em.createQuery(sql.toString());
/* 355:440 */     query.setParameter("cobro", cobro);
/* 356:    */     try
/* 357:    */     {
/* 358:443 */       return (AnticipoCliente)query.getSingleResult();
/* 359:    */     }
/* 360:    */     catch (NoResultException e) {}
/* 361:445 */     return null;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public List<AnticipoCliente> listaAnticiposClientesMasivos(BigDecimal valor, int idOrganizacion, Empresa empresa)
/* 365:    */   {
/* 366:453 */     StringBuilder sql = new StringBuilder();
/* 367:454 */     sql.append(" SELECT ac ");
/* 368:455 */     sql.append(" FROM AnticipoCliente ac ");
/* 369:456 */     sql.append(" INNER JOIN ac.empresa e ");
/* 370:457 */     sql.append(" WHERE ac.estado != :anulados ");
/* 371:458 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 372:459 */     sql.append(" AND EXISTS ( SELECT 1 FROM CuentaPorCobrar cpc ");
/* 373:460 */     sql.append("\t\t\t   INNER JOIN cpc.facturaCliente fc ");
/* 374:461 */     sql.append("               INNER JOIN fc.empresa e1 ");
/* 375:462 */     sql.append("               WHERE cpc.saldo > 0                 AND e = e1          ) ");
/* 376:    */     
/* 377:464 */     sql.append(" AND ac.saldo > 0 ");
/* 378:465 */     sql.append(" AND ac.saldo <= :valor ");
/* 379:466 */     if (empresa != null) {
/* 380:467 */       sql.append(" AND ac.empresa  = :empresa ");
/* 381:    */     }
/* 382:469 */     sql.append(" order by ac.fecha ");
/* 383:    */     
/* 384:471 */     Query query = this.em.createQuery(sql.toString());
/* 385:472 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 386:473 */     query.setParameter("valor", valor);
/* 387:474 */     query.setParameter("anulados", Estado.ANULADO);
/* 388:475 */     if (empresa != null) {
/* 389:476 */       query.setParameter("empresa", empresa);
/* 390:    */     }
/* 391:480 */     return query.getResultList();
/* 392:    */   }
/* 393:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.AnticipoClienteDao
 * JD-Core Version:    0.7.0.1
 */