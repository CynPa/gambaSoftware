/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.cobros;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Pago;
/*   8:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   9:    */ import com.asinfo.as2.entities.Recaudador;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Zona;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ import javax.persistence.EntityManager;
/*  20:    */ import javax.persistence.Query;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ReporteCobroClienteDao
/*  25:    */   extends AbstractDaoAS2<Pago>
/*  26:    */ {
/*  27:    */   public ReporteCobroClienteDao()
/*  28:    */   {
/*  29: 28 */     super(Pago.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List getReportePagoCliente(Date fechaDesde, Date fechaHasta, int idCliente, Recaudador recaudador, int idOrganizacion, boolean indicadorPorVendedor, int idDocumento, boolean posfechados, Sucursal sucursal, PuntoDeVenta puntoDeVenta, EntidadUsuario usuario, Zona zona)
/*  33:    */     throws ExcepcionAS2
/*  34:    */   {
/*  35: 36 */     StringBuilder sql = new StringBuilder();
/*  36: 37 */     sql.append(" SELECT em.identificacion, em.nombreFiscal, c.numero, ");
/*  37: 38 */     sql.append(" c.fecha,  fc.numero, c.estado,c.descripcion ,a.numero, ta.nombre, ");
/*  38: 39 */     sql.append(" dc.valor,r.nombre,c.usuarioCreacion,cc.fechaCancelacion,cc.fechaVencimiento, dcf.codigo, fc.fecha, CONCAT( ac.nombre2 ,'  ', ac.nombre1), fc.referencia2, ");
/*  39:    */     
/*  40: 41 */     sql.append(" see.nombreFiscal ");
/*  41: 42 */     sql.append(" FROM DetalleCobro dc ");
/*  42: 43 */     sql.append(" INNER JOIN dc.cobro c ");
/*  43: 44 */     sql.append(" INNER JOIN c.documento d  ");
/*  44: 45 */     sql.append(" INNER JOIN dc.cuentaPorCobrar cc ");
/*  45: 46 */     sql.append(" INNER JOIN cc.facturaCliente fc ");
/*  46: 47 */     sql.append(" INNER JOIN fc.documento dcf ");
/*  47: 48 */     sql.append(" INNER JOIN fc.empresa em ");
/*  48: 49 */     sql.append(" INNER JOIN em.cliente cli ");
/*  49: 50 */     sql.append(" LEFT OUTER JOIN c.recaudador r ");
/*  50: 51 */     sql.append(" LEFT OUTER JOIN c.asiento a ");
/*  51: 52 */     sql.append(" LEFT OUTER JOIN a.tipoAsiento ta ");
/*  52: 53 */     sql.append(" LEFT OUTER JOIN fc.agenteComercial ac ");
/*  53: 54 */     sql.append(" LEFT OUTER JOIN fc.subempresa se ");
/*  54: 55 */     sql.append(" LEFT OUTER JOIN se.empresa see ");
/*  55: 56 */     sql.append(" WHERE (em.idEmpresa = :idCliente OR :idCliente=0) ");
/*  56: 57 */     sql.append(" AND (r = :recaudador OR :recaudador IS NULL) ");
/*  57: 58 */     sql.append(" AND em.idEmpresa=cli.empresa.idEmpresa ");
/*  58: 59 */     sql.append(" AND em.idOrganizacion=cli.empresa.idOrganizacion ");
/*  59: 60 */     sql.append(" AND em.idSucursal=cli.empresa.idSucursal ");
/*  60: 61 */     sql.append(" AND d.documentoBase = :documentoBase ");
/*  61: 62 */     sql.append(" AND c.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  62: 63 */     sql.append(" AND c.estado=:estado");
/*  63: 64 */     sql.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0) ");
/*  64: 65 */     sql.append(" AND c.idOrganizacion= :idOrganizacion");
/*  65: 66 */     if (null != sucursal) {
/*  66: 67 */       sql.append(" AND c.sucursal.idSucursal = :idSucursal");
/*  67:    */     }
/*  68: 69 */     if (null != zona) {
/*  69: 70 */       sql.append(" AND fc.zona = :zona ");
/*  70:    */     }
/*  71: 72 */     if (puntoDeVenta != null) {
/*  72: 73 */       sql.append(" AND fc.numero LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/*  73:    */     }
/*  74: 75 */     if (usuario != null) {
/*  75: 76 */       sql.append(" AND fc.agenteComercial = :agenteComercial ");
/*  76:    */     }
/*  77: 78 */     if (indicadorPorVendedor) {
/*  78: 79 */       sql.append(" ORDER BY r.nombre, c.fecha ");
/*  79:    */     } else {
/*  80: 81 */       sql.append(" ORDER BY em.nombreFiscal, c.numero ");
/*  81:    */     }
/*  82: 84 */     Query query = this.em.createQuery(sql.toString());
/*  83: 85 */     query.setParameter("fechaDesde", fechaDesde);
/*  84: 86 */     query.setParameter("fechaHasta", fechaHasta);
/*  85: 87 */     query.setParameter("documentoBase", DocumentoBase.COBRO_CLIENTE);
/*  86: 88 */     if (posfechados) {
/*  87: 89 */       query.setParameter("estado", Estado.ELABORADO);
/*  88:    */     } else {
/*  89: 91 */       query.setParameter("estado", Estado.CONTABILIZADO);
/*  90:    */     }
/*  91: 93 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  92: 94 */     query.setParameter("recaudador", recaudador);
/*  93: 95 */     query.setParameter("idDocumento", Integer.valueOf(idDocumento));
/*  94: 96 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  95: 97 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  96: 98 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getId()));
/*  97:    */     }
/*  98:100 */     if (null != zona) {
/*  99:101 */       query.setParameter("zona", zona);
/* 100:    */     }
/* 101:103 */     if (puntoDeVenta != null) {
/* 102:104 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/* 103:    */     }
/* 104:106 */     if (usuario != null) {
/* 105:107 */       query.setParameter("agenteComercial", usuario);
/* 106:    */     }
/* 107:110 */     return query.getResultList();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List getReporteCobro(int idCobro)
/* 111:    */   {
/* 112:115 */     String sql = " SELECT e.nombreFiscal, e.identificacion,  c.numero, c.fecha, c.valor, c.descripcion, fc.numero, fc.fecha,  cc.fechaVencimiento, cc.numeroCuota, cc.saldo, dc.valor, c.descripcion2  FROM DetalleCobro dc  INNER JOIN dc.cobro c  INNER JOIN c.empresa e  INNER JOIN dc.cuentaPorCobrar cc  INNER JOIN cc.facturaCliente fc  WHERE c.idCobro = :idCobro ";
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:120 */     Query query = this.em.createQuery(sql).setParameter("idCobro", Integer.valueOf(idCobro));
/* 118:121 */     return query.getResultList();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<Object[]> getRegistroVoucher(int idOrganizacion, int idCobro)
/* 122:    */   {
/* 123:126 */     StringBuilder sql = new StringBuilder();
/* 124:127 */     sql.append(" SELECT  c.fecha, d.nombre, c.descripcion, pv.codigoAlterno, tc.nombre, ptc.codigo, dfc.numeroTarjeta, dfc.baseImponibleDiferenteCero,");
/* 125:    */     
/* 126:129 */     sql.append(" dfc.baseImponibleTarifaCero, dfc.montoIva, dfc.interes, dfc.valor, dfc.documentoReferencia, dfc.lote, dfc.fechaVoucher, dfc.descripcion");
/* 127:    */     
/* 128:131 */     sql.append(" FROM  DetalleFormaCobro dfc ");
/* 129:132 */     sql.append(" LEFT JOIN dfc.cobro c ");
/* 130:133 */     sql.append(" INNER JOIN c.documento d ");
/* 131:134 */     sql.append(" LEFT JOIN dfc.tarjetaCredito tc ");
/* 132:135 */     sql.append(" LEFT JOIN dfc.puntoVenta pv ");
/* 133:136 */     sql.append(" LEFT JOIN dfc.planTarjetaCredito ptc");
/* 134:137 */     sql.append(" WHERE c.idCobro = :idCobro ");
/* 135:138 */     sql.append(" AND c.idOrganizacion= :idOrganizacion");
/* 136:    */     
/* 137:140 */     Query query = this.em.createQuery(sql.toString());
/* 138:141 */     query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 139:142 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 140:143 */     return query.getResultList();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Long countTickets(int idOrganizacion, Date fecha, String operacion, String estacion)
/* 144:    */   {
/* 145:147 */     StringBuilder sql = new StringBuilder();
/* 146:    */     
/* 147:149 */     sql.append(" SELECT  COUNT(tk.idTicket)");
/* 148:150 */     sql.append(" FROM  Ticket tk");
/* 149:151 */     sql.append(" LEFT JOIN tk.puntoDeVenta pv ");
/* 150:152 */     sql.append(" WHERE tk.idOrganizacion= :idOrganizacion");
/* 151:153 */     sql.append(" AND pv.codigoAlterno=:puntoVenta");
/* 152:154 */     sql.append(" AND tk.periodo=:fecha");
/* 153:155 */     sql.append(" AND tk.operacion=:operacion");
/* 154:    */     
/* 155:157 */     Query query = this.em.createQuery(sql.toString());
/* 156:158 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 157:159 */     query.setParameter("puntoVenta", estacion);
/* 158:160 */     query.setParameter("fecha", fecha);
/* 159:161 */     query.setParameter("operacion", operacion);
/* 160:162 */     return (Long)query.getSingleResult();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<Object[]> getPagoVoucher(int idOrganizacion, int idCobro)
/* 164:    */   {
/* 165:167 */     StringBuilder sql = new StringBuilder();
/* 166:    */     
/* 167:169 */     sql.append(" SELECT  pv.codigoAlterno, tc.nombre, ptc.codigo, dfc.baseImponibleDiferenteCero, dfc.baseImponibleTarifaCero, dfc.montoIva, dfc.valor, dfc.documentoReferencia,");
/* 168:    */     
/* 169:171 */     sql.append(" dfc.lote, dfc.fechaVoucher, dfc.porcentajeComision, dfc.valorComision, dfc.impuestoComision, dfc.valorPagadoCalculado, dfc.valorPagado");
/* 170:    */     
/* 171:173 */     sql.append(" FROM  DetalleFormaCobro dfc ");
/* 172:174 */     sql.append(" LEFT JOIN dfc.cobroTarjeta c ");
/* 173:175 */     sql.append(" INNER JOIN c.documento d ");
/* 174:176 */     sql.append(" LEFT JOIN dfc.tarjetaCredito tc ");
/* 175:177 */     sql.append(" LEFT JOIN dfc.puntoVenta pv ");
/* 176:178 */     sql.append(" LEFT JOIN dfc.planTarjetaCredito ptc");
/* 177:179 */     sql.append(" WHERE c.idCobro = :idCobro ");
/* 178:180 */     sql.append(" AND c.idOrganizacion= :idOrganizacion");
/* 179:    */     
/* 180:182 */     Query query = this.em.createQuery(sql.toString());
/* 181:183 */     query.setParameter("idCobro", Integer.valueOf(idCobro));
/* 182:184 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 183:185 */     return query.getResultList();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Object[]> getConsultaVoucher(int idOrganizacion, PuntoDeVenta puntoDeVenta, Date fechaDesde, Date fechaHasta, String fechaReporte)
/* 187:    */   {
/* 188:190 */     StringBuilder sql = new StringBuilder();
/* 189:    */     
/* 190:192 */     sql.append("SELECT dfc.secuencial, tc.nombre, ttc.nombre, dfc.numeroTarjeta, dfc.baseImponibleDiferenteCero, dfc.baseImponibleTarifaCero,");
/* 191:193 */     sql.append(" dfc.montoIva, dfc.interes, dfc.valor, dfc.documentoReferencia, dfc.lote, dfc.fechaVoucher, pv.nombre, pv.codigoAlterno, 'fechaReporte', 'agencia',");
/* 192:    */     
/* 193:195 */     sql.append(" ptc.codigo, dfc.descripcion, ct.numero, ct.fecha, bdfc.nombre, b.nombre, dfc.valorPagado, dfc.porcentajeComision,  dfc.valorComision,");
/* 194:    */     
/* 195:197 */     sql.append(" dfc.valorPagadoCalculado, c.numero, c.fecha, ct.valor, ct.fecha, dfc.secuencial, dfc.impuestoComision");
/* 196:198 */     sql.append(" FROM  DetalleFormaCobro dfc");
/* 197:199 */     sql.append(" LEFT JOIN dfc.cobro c");
/* 198:200 */     sql.append(" LEFT JOIN dfc.cobroTarjeta ct");
/* 199:201 */     sql.append(" LEFT JOIN dfc.banco bdfc");
/* 200:202 */     sql.append(" INNER JOIN c.documento d");
/* 201:203 */     sql.append(" LEFT JOIN dfc.tarjetaCredito tc");
/* 202:204 */     sql.append(" LEFT JOIN tc.tipoTarjetaCredito ttc");
/* 203:205 */     sql.append(" LEFT JOIN tc.banco b");
/* 204:206 */     sql.append(" LEFT JOIN dfc.puntoVenta pv");
/* 205:207 */     sql.append(" LEFT JOIN dfc.planTarjetaCredito ptc");
/* 206:208 */     sql.append(" WHERE c.idOrganizacion= :idOrganizacion");
/* 207:210 */     if (puntoDeVenta != null) {
/* 208:211 */       sql.append(" AND pv = :puntoDVenta");
/* 209:    */     }
/* 210:213 */     if ("Fecha de registro".equals(fechaReporte))
/* 211:    */     {
/* 212:214 */       if ((fechaDesde != null) && (fechaHasta == null)) {
/* 213:215 */         sql.append(" AND dfc.fechaVoucher= :fechaDesde");
/* 214:    */       }
/* 215:217 */       if ((fechaDesde != null) && (fechaHasta != null)) {
/* 216:218 */         sql.append(" AND dfc.fechaVoucher BETWEEN :fechaDesde AND :fechaHasta");
/* 217:    */       }
/* 218:221 */       sql.append(" ORDER BY dfc.fechaVoucher");
/* 219:    */     }
/* 220:    */     else
/* 221:    */     {
/* 222:223 */       if ((fechaDesde != null) && (fechaHasta == null)) {
/* 223:224 */         sql.append(" AND ct.fecha= :fechaDesde");
/* 224:    */       }
/* 225:226 */       if ((fechaDesde != null) && (fechaHasta != null)) {
/* 226:227 */         sql.append(" AND ct.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 227:    */       }
/* 228:229 */       sql.append(" ORDER BY ct.fecha");
/* 229:    */     }
/* 230:232 */     Query query = this.em.createQuery(sql.toString());
/* 231:234 */     if (puntoDeVenta != null) {
/* 232:235 */       query.setParameter("puntoDVenta", puntoDeVenta);
/* 233:    */     }
/* 234:237 */     if ((fechaDesde != null) && (fechaHasta == null)) {
/* 235:238 */       query.setParameter("fechaDesde", fechaDesde);
/* 236:    */     }
/* 237:240 */     if ((fechaDesde != null) && (fechaHasta != null))
/* 238:    */     {
/* 239:241 */       query.setParameter("fechaDesde", fechaDesde);
/* 240:242 */       query.setParameter("fechaHasta", fechaHasta);
/* 241:    */     }
/* 242:244 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 243:245 */     return query.getResultList();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<Object[]> getlistaReporteTicket(int idOrganizacion, Date fechaDesde, Date fechaHasta, String numeroTicket, String operacion, String agencia, String tipoReporte)
/* 247:    */   {
/* 248:251 */     StringBuilder sql = new StringBuilder();
/* 249:    */     
/* 250:253 */     sql.append(" SELECT ");
/* 251:254 */     StringBuilder sqlCampos = new StringBuilder();
/* 252:255 */     sqlCampos.append("t.moneda, pv.codigoAlterno,");
/* 253:256 */     if ((tipoReporte.equals("Ventas locales")) || (tipoReporte.equals("BSP Actualizado"))) {
/* 254:257 */       sqlCampos.append(" t.codigoAgente,");
/* 255:    */     } else {
/* 256:259 */       sqlCampos.append(" t.aerolinea,");
/* 257:    */     }
/* 258:261 */     sqlCampos.append(" t.periodo, t.fecha, ");
/* 259:262 */     if ((tipoReporte.equals("Ventas locales")) || (tipoReporte.equals("BSP Actualizado"))) {
/* 260:263 */       sqlCampos.append(" t.record, ");
/* 261:    */     } else {
/* 262:265 */       sqlCampos.append(" t.codigoDisenioTicket,");
/* 263:    */     }
/* 264:267 */     sqlCampos.append(" t.numero, t.originalConjuncion, t.operacion,   ");
/* 265:268 */     sqlCampos.append(" t.tipoDeDocumento, t.tipoTransaccion, t.pasajero, t.identificacionTributaria, t.ruta, t.fechaViaje, t.isCredito, t.formaPago, t.valorFormaPago, ");
/* 266:    */     
/* 267:270 */     sqlCampos.append("  t.valorTotalPreliminar, t.observaciones, t.tarifaPreliminar, t.tarifa, t.tarifaDiferenteCero, t.tarifaCero, t.yq, t.YqDiferenteCero, t.YqCero, ");
/* 268:    */     
/* 269:272 */     sqlCampos.append("  t.anticipo, t.descuento, t.penalty, t.codigoDeServicio, dt.taxMiscFeeType1, ");
/* 270:273 */     sqlCampos.append(" CASE WHEN dt.indicadorNacional = true  THEN '1IMPUESTO NACIONAL' ELSE '2IMPUESTO EXTRANJERO' END, ");
/* 271:274 */     sqlCampos.append(" b.indicadorRespaldo, b.tipo, b.fecha, ");
/* 272:275 */     if ((tipoReporte.equals("Ventas locales")) || (tipoReporte.equals("BSP Actualizado"))) {
/* 273:276 */       sqlCampos.append(" t.numeroDocumentoRelacionado, ");
/* 274:    */     } else {
/* 275:278 */       sqlCampos.append(" dt.reltTktDocNum, ");
/* 276:    */     }
/* 277:280 */     sqlCampos.append(" t.tipoTarjetaCredito, t.valorTotal,");
/* 278:281 */     sqlCampos.append(" t.comision, t.ivaComision, t.retencionFte, t.yr, t.neto, t.porComision, t.numeroPeriodo, t.periodoBSP  ");
/* 279:    */     
/* 280:283 */     sql.append(sqlCampos + ", sum(dt.taxMiscFeeAmt1)");
/* 281:    */     
/* 282:285 */     sql.append(" FROM Ticket t ");
/* 283:286 */     sql.append(" INNER JOIN t.bsp b ");
/* 284:287 */     sql.append(" LEFT JOIN t.puntoDeVenta pv ");
/* 285:288 */     sql.append(" LEFT JOIN t.listaDetalleTicket dt ");
/* 286:289 */     sql.append(" WHERE t.idOrganizacion= :idOrganizacion");
/* 287:291 */     if ((tipoReporte != null) && (!tipoReporte.isEmpty()) && (!tipoReporte.equals("Todos"))) {
/* 288:292 */       sql.append(" and b.tipo = :tipoReporte ");
/* 289:    */     }
/* 290:294 */     if (!numeroTicket.equals("")) {
/* 291:295 */       sql.append(" and t.numero = :numero");
/* 292:    */     }
/* 293:297 */     if (!operacion.equals("")) {
/* 294:298 */       sql.append(" and t.operacion = :operacion");
/* 295:    */     }
/* 296:300 */     if ((!agencia.equals("")) && (!tipoReporte.equals("Ventas locales"))) {
/* 297:301 */       sql.append(" and t.aerolinea = :aerolinea");
/* 298:    */     }
/* 299:303 */     if ((!agencia.equals("")) && ((tipoReporte.equals("Ventas locales")) || (tipoReporte.equals("BSP Actualizado")))) {
/* 300:304 */       sql.append(" and pv.codigoAlterno = :puntoVenta");
/* 301:    */     }
/* 302:306 */     if ((fechaDesde != null) && (fechaHasta == null)) {
/* 303:307 */       sql.append(" and t.periodo= :fechaDesde");
/* 304:    */     }
/* 305:309 */     if ((fechaDesde != null) && (fechaHasta != null)) {
/* 306:310 */       sql.append(" and t.periodo between :fechaDesde and :fechaHasta");
/* 307:    */     }
/* 308:314 */     sql.append(" GROUP BY " + sqlCampos);
/* 309:315 */     sql.append(" order by dt.taxMiscFeeType1 ");
/* 310:    */     
/* 311:317 */     Query query = this.em.createQuery(sql.toString());
/* 312:319 */     if ((tipoReporte != null) && (!tipoReporte.isEmpty()) && (!tipoReporte.equals("Todos"))) {
/* 313:320 */       query.setParameter("tipoReporte", tipoReporte);
/* 314:    */     }
/* 315:323 */     if (!numeroTicket.equals("")) {
/* 316:324 */       query.setParameter("numero", numeroTicket);
/* 317:    */     }
/* 318:326 */     if (!operacion.equals("")) {
/* 319:327 */       query.setParameter("operacion", operacion);
/* 320:    */     }
/* 321:329 */     if ((!agencia.equals("")) && (!tipoReporte.equals("Ventas locales"))) {
/* 322:330 */       query.setParameter("aerolinea", agencia);
/* 323:    */     }
/* 324:332 */     if ((!agencia.equals("")) && (tipoReporte.equals("Ventas locales"))) {
/* 325:333 */       query.setParameter("puntoVenta", agencia);
/* 326:    */     }
/* 327:335 */     if ((fechaDesde != null) && (fechaHasta == null)) {
/* 328:336 */       query.setParameter("fechaDesde", fechaDesde);
/* 329:    */     }
/* 330:338 */     if ((fechaDesde != null) && (fechaHasta != null))
/* 331:    */     {
/* 332:339 */       query.setParameter("fechaDesde", fechaDesde);
/* 333:340 */       query.setParameter("fechaHasta", fechaHasta);
/* 334:    */     }
/* 335:342 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 336:343 */     return query.getResultList();
/* 337:    */   }
/* 338:    */   
/* 339:    */   public List getReporteCobroClientePorFormaPago(Date fechaDesde, Date fechaHasta, int idCliente, Recaudador recaudador, int idOrganizacion, boolean indicadorResumen, int idDocumento, boolean posfechados, Sucursal sucursal, Zona zona, PuntoDeVenta puntoDeVenta, EntidadUsuario usuario)
/* 340:    */   {
/* 341:350 */     StringBuilder sql = new StringBuilder();
/* 342:351 */     if (indicadorResumen)
/* 343:    */     {
/* 344:352 */       sql.append(" SELECT  em.nombreFiscal, ");
/* 345:353 */       sql.append(" fp.nombre, SUM(dcfc.valor)");
/* 346:    */     }
/* 347:    */     else
/* 348:    */     {
/* 349:355 */       sql.append(" SELECT  em.nombreFiscal, ");
/* 350:356 */       sql.append(" fp.nombre, SUM(dcfc.valor),c.numero, c.fecha, c.descripcion, fc.numero ");
/* 351:    */     }
/* 352:358 */     sql.append(" FROM  DetalleCobroFormaCobro dcfc ");
/* 353:359 */     sql.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 354:360 */     sql.append(" INNER JOIN dc.cuentaPorCobrar cc ");
/* 355:361 */     sql.append(" INNER JOIN cc.facturaCliente fc ");
/* 356:362 */     sql.append(" INNER JOIN dcfc.detalleFormaCobro dfc ");
/* 357:363 */     sql.append(" INNER JOIN dfc.formaPago fp");
/* 358:364 */     sql.append(" INNER JOIN dfc.cobro c ");
/* 359:365 */     sql.append(" INNER JOIN c.documento d  ");
/* 360:366 */     sql.append(" INNER JOIN c.empresa em ");
/* 361:367 */     sql.append(" INNER JOIN em.cliente cli ");
/* 362:368 */     sql.append(" LEFT OUTER JOIN c.recaudador r ");
/* 363:369 */     sql.append(" LEFT OUTER JOIN c.asiento a ");
/* 364:370 */     sql.append(" LEFT OUTER JOIN a.tipoAsiento ta ");
/* 365:371 */     sql.append(" WHERE (em.idEmpresa = :idCliente OR :idCliente=0) ");
/* 366:372 */     sql.append(" AND (r = :recaudador OR :recaudador IS NULL) ");
/* 367:373 */     sql.append(" AND d.documentoBase = :documentoBase ");
/* 368:374 */     sql.append(" AND c.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 369:375 */     sql.append(" AND c.estado=:estado");
/* 370:376 */     sql.append(" AND (d.idDocumento = :idDocumento OR :idDocumento=0) ");
/* 371:377 */     sql.append(" AND c.idOrganizacion= :idOrganizacion");
/* 372:378 */     if (null != sucursal) {
/* 373:379 */       sql.append(" AND c.sucursal.idSucursal = :idSucursal");
/* 374:    */     }
/* 375:381 */     if (puntoDeVenta != null) {
/* 376:382 */       sql.append(" AND fc.numero LIKE CONCAT('%-',:codigoPuntoVenta,'-%') ");
/* 377:    */     }
/* 378:384 */     if (null != zona) {
/* 379:385 */       sql.append(" AND fc.zona = :zona");
/* 380:    */     }
/* 381:387 */     if (usuario != null) {
/* 382:388 */       sql.append(" AND fc.agenteComercial = :agenteComercial ");
/* 383:    */     }
/* 384:390 */     if (indicadorResumen)
/* 385:    */     {
/* 386:391 */       sql.append(" GROUP BY em.nombreFiscal,fp.nombre");
/* 387:392 */       sql.append(" ORDER BY em.nombreFiscal ");
/* 388:    */     }
/* 389:    */     else
/* 390:    */     {
/* 391:394 */       sql.append(" GROUP BY c.numero,c.fecha,em.nombreFiscal,fp.nombre, c.descripcion, fc.numero");
/* 392:395 */       sql.append(" ORDER BY em.nombreFiscal, c.numero ");
/* 393:    */     }
/* 394:398 */     Query query = this.em.createQuery(sql.toString());
/* 395:399 */     query.setParameter("fechaDesde", fechaDesde);
/* 396:400 */     query.setParameter("fechaHasta", fechaHasta);
/* 397:401 */     query.setParameter("documentoBase", DocumentoBase.COBRO_CLIENTE);
/* 398:402 */     if (posfechados) {
/* 399:403 */       query.setParameter("estado", Estado.ELABORADO);
/* 400:    */     } else {
/* 401:405 */       query.setParameter("estado", Estado.CONTABILIZADO);
/* 402:    */     }
/* 403:407 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 404:408 */     query.setParameter("recaudador", recaudador);
/* 405:409 */     query.setParameter("idDocumento", Integer.valueOf(idDocumento));
/* 406:410 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 407:411 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 408:412 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 409:    */     }
/* 410:414 */     if (puntoDeVenta != null) {
/* 411:415 */       query.setParameter("codigoPuntoVenta", puntoDeVenta.getCodigo());
/* 412:    */     }
/* 413:417 */     if (null != zona) {
/* 414:418 */       query.setParameter("zona", zona);
/* 415:    */     }
/* 416:420 */     if (usuario != null) {
/* 417:421 */       query.setParameter("agenteComercial", usuario);
/* 418:    */     }
/* 419:424 */     return query.getResultList();
/* 420:    */   }
/* 421:    */   
/* 422:    */   public List getReporteCobroProtestos(int idEmpresa, Date fechaDesde, Date fechaHasta, int idCuentaBancariaOrganizacion, int idFormaCobro, int idOrganizacion)
/* 423:    */   {
/* 424:431 */     StringBuilder sql = new StringBuilder();
/* 425:432 */     sql.append(" SELECT c.fecha, c.numero, em.identificacion, em.nombreFiscal, em.nombreComercial, cbc.numero, fpc.nombre, dfc.documentoReferencia, dfc.valor, ac.numero , ");
/* 426:    */     
/* 427:434 */     sql.append(" ap.fecha, cbp.numero, fpp.nombre, dfc.valorProtestado, ap.numero,bc.nombre,bp.nombre, c.descripcion ");
/* 428:435 */     sql.append(" FROM DetalleFormaCobro dfc ");
/* 429:436 */     sql.append(" LEFT JOIN dfc.cobro c ");
/* 430:437 */     sql.append(" LEFT JOIN c.empresa em ");
/* 431:438 */     sql.append(" LEFT JOIN c.asiento ac ");
/* 432:439 */     sql.append(" LEFT JOIN c.asientoProtesto ap ");
/* 433:440 */     sql.append(" LEFT JOIN dfc.cuentaBancariaOrganizacion cbo ");
/* 434:441 */     sql.append(" LEFT JOIN cbo.cuentaBancaria cbc ");
/* 435:442 */     sql.append(" LEFT JOIN cbc.banco bc ");
/* 436:443 */     sql.append(" LEFT JOIN dfc.cuentaBancariaOrganizacionProtesto cbop ");
/* 437:444 */     sql.append(" LEFT JOIN cbop.cuentaBancaria cbp ");
/* 438:445 */     sql.append(" LEFT JOIN cbp.banco bp ");
/* 439:446 */     sql.append(" LEFT JOIN dfc.formaPago fpc ");
/* 440:447 */     sql.append(" LEFT JOIN dfc.formaPagoProtesto fpp ");
/* 441:448 */     sql.append(" WHERE c.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 442:449 */     sql.append(" AND c.estado != :estadoAnulado ");
/* 443:450 */     sql.append(" AND (em.idEmpresa=:idEmpresa OR :idEmpresa=0) ");
/* 444:451 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion=0) ");
/* 445:452 */     sql.append(" AND (fpc.idFormaPago=:idFormaCobro OR :idFormaCobro=0) ");
/* 446:453 */     sql.append(" AND c.idOrganizacion=:idOrganizacion ");
/* 447:454 */     sql.append(" AND ap IS NOT NULL ");
/* 448:455 */     sql.append(" AND dfc.indicadorChequeProtestado IS TRUE ");
/* 449:456 */     sql.append(" ORDER BY c.fecha, c.numero  ");
/* 450:    */     
/* 451:458 */     Query query = this.em.createQuery(sql.toString());
/* 452:459 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 453:460 */     query.setParameter("fechaDesde", fechaDesde);
/* 454:461 */     query.setParameter("fechaHasta", fechaHasta);
/* 455:462 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(idCuentaBancariaOrganizacion));
/* 456:463 */     query.setParameter("idFormaCobro", Integer.valueOf(idFormaCobro));
/* 457:464 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 458:465 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 459:    */     
/* 460:467 */     return query.getResultList();
/* 461:    */   }
/* 462:    */   
/* 463:    */   public List getReporteDepositosCobro(int idEmpresa, Date fechaDesde, Date fechaHasta, int idCuentaBancariaOrganizacion, boolean pendienteDeposito, int idOrganizacion, int idFormaCobro)
/* 464:    */   {
/* 465:475 */     StringBuilder sql = new StringBuilder();
/* 466:476 */     sql.append(" SELECT c.fecha, c.numero, em.identificacion, em.nombreFiscal, em.nombreComercial , cbc.numero, fpc.nombre, dfc.documentoReferencia, dfc.valor, ac.numero, ");
/* 467:    */     
/* 468:478 */     sql.append(" icp.numero, icp.fechaContabilizacion, cbd.numero, fpd.nombre, icp.valor, ad.numero,bc.nombre,bd.nombre ");
/* 469:479 */     sql.append(" FROM DetalleCierreCaja dcc ");
/* 470:480 */     sql.append(" LEFT JOIN dcc.detalleFormaCobro dfc ");
/* 471:481 */     sql.append(" LEFT JOIN dfc.cobro c ");
/* 472:482 */     sql.append(" LEFT JOIN c.empresa em ");
/* 473:483 */     sql.append(" LEFT JOIN c.asiento ac ");
/* 474:484 */     sql.append(" LEFT JOIN dfc.cuentaBancariaOrganizacion cbo ");
/* 475:485 */     sql.append(" LEFT JOIN cbo.cuentaBancaria cbc ");
/* 476:486 */     sql.append(" LEFT JOIN cbc.banco bc ");
/* 477:487 */     sql.append(" LEFT JOIN dfc.formaPago fpc ");
/* 478:488 */     sql.append(" LEFT JOIN dcc.interfazContableProceso icp ");
/* 479:489 */     sql.append(" LEFT JOIN icp.cuentaBancariaOrganizacion cbod ");
/* 480:490 */     sql.append(" LEFT JOIN cbod.cuentaBancaria cbd ");
/* 481:491 */     sql.append(" LEFT JOIN cbd.banco bd ");
/* 482:492 */     sql.append(" LEFT JOIN icp.formaPago fpd ");
/* 483:493 */     sql.append(" LEFT JOIN icp.asiento ad ");
/* 484:494 */     sql.append(" WHERE c.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 485:495 */     sql.append(" AND c.estado != :estadoAnulado ");
/* 486:496 */     sql.append(" AND (em.idEmpresa=:idEmpresa OR :idEmpresa=0) ");
/* 487:497 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion=0) ");
/* 488:498 */     sql.append(" AND (fpc.idFormaPago=:idFormaCobro OR :idFormaCobro=0) ");
/* 489:499 */     sql.append(" AND c.idOrganizacion=:idOrganizacion ");
/* 490:500 */     if (pendienteDeposito) {
/* 491:501 */       sql.append(" AND icp IS NULL ");
/* 492:    */     }
/* 493:503 */     sql.append(" ORDER BY c.fecha, c.numero  ");
/* 494:    */     
/* 495:505 */     Query query = this.em.createQuery(sql.toString());
/* 496:506 */     query.setParameter("idEmpresa", Integer.valueOf(idEmpresa));
/* 497:507 */     query.setParameter("fechaDesde", fechaDesde);
/* 498:508 */     query.setParameter("fechaHasta", fechaHasta);
/* 499:509 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(idCuentaBancariaOrganizacion));
/* 500:510 */     query.setParameter("idFormaCobro", Integer.valueOf(idFormaCobro));
/* 501:511 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 502:512 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 503:    */     
/* 504:514 */     return query.getResultList();
/* 505:    */   }
/* 506:    */   
/* 507:    */   public List getReporteChequesProtestados(Date fechaDesde, Date fechaHasta, Empresa empresa, CuentaBancariaOrganizacion cuentaBancariaOrganizacion, boolean indicadorSaldoDiferenciaCero)
/* 508:    */   {
/* 509:522 */     StringBuilder sql = new StringBuilder();
/* 510:523 */     sql.append(" SELECT coalesce(a.nombre2,'NA') +' '+coalesce(a.nombre1,''), e.nombreFiscal, e.nombreComercial, e.identificacion, ");
/* 511:524 */     sql.append(" c.numero, df.fechaProtesto, df.documentoReferencia, c.descripcion, cxc.valor, cxc.saldo, cxc.valorBloqueado, b.nombre, cxc.indicadorAnulada ");
/* 512:    */     
/* 513:526 */     sql.append(" FROM CuentaPorCobrar cxc ");
/* 514:527 */     sql.append(" INNER JOIN cxc.detalleFormaCobroProtesto df ");
/* 515:528 */     sql.append(" lEFT JOIN df.banco b ");
/* 516:529 */     sql.append(" INNER JOIN df.cobro c ");
/* 517:530 */     sql.append(" INNER JOIN cxc.facturaCliente f ");
/* 518:531 */     sql.append(" LEFT JOIN f.agenteComercial a ");
/* 519:532 */     sql.append(" INNER JOIN f.empresa e ");
/* 520:533 */     sql.append(" INNER JOIN df.cuentaBancariaOrganizacionProtesto cbo ");
/* 521:534 */     sql.append(" WHERE (e.idEmpresa = :idEmpresa OR :idEmpresa = 0 )");
/* 522:535 */     sql.append(" AND (cbo.idCuentaBancariaOrganizacion = :idCuentaBancariaOrganizacion OR :idCuentaBancariaOrganizacion = 0)");
/* 523:536 */     sql.append(" AND (df.fechaProtesto between :fechaDesde AND :fechaHasta)");
/* 524:538 */     if (indicadorSaldoDiferenciaCero) {
/* 525:539 */       sql.append(" AND cxc.saldo <> 0");
/* 526:    */     }
/* 527:542 */     sql.append(" ORDER BY a.nombre2 +' '+a.nombre1, e.nombreFiscal, df.fechaProtesto");
/* 528:    */     
/* 529:544 */     Query query = this.em.createQuery(sql.toString());
/* 530:    */     
/* 531:546 */     query.setParameter("idEmpresa", Integer.valueOf(empresa == null ? 0 : empresa.getIdEmpresa()));
/* 532:547 */     query.setParameter("idCuentaBancariaOrganizacion", 
/* 533:548 */       Integer.valueOf(cuentaBancariaOrganizacion == null ? 0 : cuentaBancariaOrganizacion.getIdCuentaBancariaOrganizacion()));
/* 534:549 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 535:550 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 536:551 */     return query.getResultList();
/* 537:    */   }
/* 538:    */   
/* 539:    */   public List<Object[]> facturasCanceladas(Date fechaDesde, Date fechaHasta, int idOrganizacion, Sucursal sucursal, Zona zona)
/* 540:    */   {
/* 541:557 */     StringBuilder sql = new StringBuilder();
/* 542:558 */     sql.append(" select  coalesce(concat(ac.nombre2,' ',ac.nombre1), ' '), fc1.numero, fc1.fecha, em.nombreComercial, ");
/* 543:559 */     sql.append(" fc1.total, fc1.idFacturaCliente, fc1.total*0 ,fc1.impuesto, fc1.total*0, fc1.referencia2, ");
/* 544:560 */     sql.append(" see.nombreFiscal ");
/* 545:561 */     sql.append(" from    FacturaCliente fc1 ");
/* 546:562 */     sql.append(" inner   join fc1.empresa em ");
/* 547:563 */     sql.append(" left    join fc1.agenteComercial ac ");
/* 548:564 */     sql.append(" left    join fc1.subempresa se ");
/* 549:565 */     sql.append(" left    join se.empresa see ");
/* 550:566 */     sql.append(" where   fc1.idOrganizacion =:idOrganizacion ");
/* 551:567 */     if (sucursal != null) {
/* 552:568 */       sql.append(" and     fc1.sucursal =:sucursal ");
/* 553:    */     }
/* 554:570 */     if (zona != null) {
/* 555:571 */       sql.append(" and     fc1.zona =:zona ");
/* 556:    */     }
/* 557:573 */     sql.append(" and fc1.estado != 0 ");
/* 558:574 */     sql.append(" and     exists (");
/* 559:575 */     sql.append("                   select 1 from VEstadoCuenta vec");
/* 560:576 */     sql.append("                   where vec.fechaDocumento <= :fechaHasta");
/* 561:577 */     sql.append("                   and vec.idFacturaCliente = fc1.idFacturaCliente ");
/* 562:578 */     sql.append("\t\t\t\t\t   and vec.idOrganizacion = :idOrganizacion ");
/* 563:579 */     sql.append("                   and exists (");
/* 564:580 */     sql.append("                                select 1 from VEstadoCuenta vec1 ");
/* 565:581 */     sql.append("                                where vec1.idFacturaCliente = vec.idFacturaCliente ");
/* 566:582 */     sql.append("                                and vec1.credito > 0 ");
/* 567:583 */     sql.append("                                and vec1.fechaDocumento between :fechaDesde and :fechaHasta ");
/* 568:584 */     sql.append("                                and vec1.documentoBase != :documentoBase ");
/* 569:585 */     sql.append("                                and vec1.idOrganizacion = :idOrganizacion ");
/* 570:586 */     sql.append("                               ) ");
/* 571:587 */     sql.append("                   group by vec.numeroFactura     ");
/* 572:588 */     sql.append("                   having (sum(vec.debito-vec.credito)) = 0  ");
/* 573:589 */     sql.append("                  )");
/* 574:590 */     sql.append(" group by ac.nombre2 ,ac.nombre1, fc1.numero, fc1.fecha, em.nombreComercial, fc1.total, fc1.idFacturaCliente, fc1.impuesto, fc1.referencia2, see.nombreFiscal ");
/* 575:    */     
/* 576:592 */     sql.append(" order by ac.nombre2 ,ac.nombre1 ");
/* 577:    */     
/* 578:594 */     Query query = this.em.createQuery(sql.toString());
/* 579:595 */     query.setParameter("fechaDesde", fechaDesde);
/* 580:596 */     query.setParameter("fechaHasta", fechaHasta);
/* 581:597 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 582:598 */     query.setParameter("documentoBase", DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 583:599 */     if (sucursal != null) {
/* 584:600 */       query.setParameter("sucursal", sucursal);
/* 585:    */     }
/* 586:602 */     if (zona != null) {
/* 587:603 */       query.setParameter("zona", zona);
/* 588:    */     }
/* 589:606 */     return query.getResultList();
/* 590:    */   }
/* 591:    */   
/* 592:    */   public List<Object[]> listaNotasDeCredito(List<Integer> idsFacturasClientePadre)
/* 593:    */   {
/* 594:612 */     StringBuilder sql = new StringBuilder();
/* 595:    */     
/* 596:614 */     sql.append(" SELECT fcp.idFacturaCliente, sum(nc.total), sum(nc.impuesto)  ");
/* 597:615 */     sql.append(" FROM   FacturaCliente nc ");
/* 598:616 */     sql.append(" INNER  JOIN nc.facturaClientePadre fcp ");
/* 599:617 */     sql.append(" WHERE  nc.estado != 0 ");
/* 600:618 */     sql.append(" AND    fcp.idFacturaCliente in (:idsFacturasClientePadre) ");
/* 601:619 */     sql.append(" GROUP BY fcp.idFacturaCliente   ");
/* 602:    */     
/* 603:621 */     Query query = this.em.createQuery(sql.toString());
/* 604:622 */     query.setParameter("idsFacturasClientePadre", idsFacturasClientePadre);
/* 605:    */     
/* 606:624 */     return query.getResultList();
/* 607:    */   }
/* 608:    */   
/* 609:    */   public List getReporteCarteraCobrada(CategoriaEmpresa categoriaEmpresa, EntidadUsuario vendedor, Recaudador recaudador, Empresa empresa, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 610:    */   {
/* 611:631 */     StringBuilder sql = new StringBuilder();
/* 612:632 */     sql.append(" SELECT new ReporteCarteraCobrada(em.idOrganizacion, em.idEmpresa, em.nombreFiscal, em.codigo, em.identificacion, ");
/* 613:633 */     sql.append(" ti.codigo, see.idEmpresa, se.empresaFinal, see.identificacion, see.nombreFiscal, fc.idFacturaCliente, fc.numero, ");
/* 614:634 */     sql.append(" fc.fecha, dcf.codigo, dcf.nombre, af.numero, cp.diasPlazo, fc.total, cc.idCuentaPorCobrar, fc.fechaVencimiento, cc.valor, c.idCobro, ");
/* 615:    */     
/* 616:636 */     sql.append(" c.fecha, c.numero, d.codigo, d.nombre, dc.valor, a.numero, ac.idUsuario ,CONCAT( ac.nombre2 ,'  ', ac.nombre1), r.idRecaudador, r.nombre) ");
/* 617:    */     
/* 618:638 */     sql.append(" FROM DetalleCobro dc ");
/* 619:639 */     sql.append(" INNER JOIN dc.cobro c ");
/* 620:640 */     sql.append(" LEFT  JOIN c.recaudador r ");
/* 621:641 */     sql.append(" LEFT  JOIN c.asiento a ");
/* 622:642 */     sql.append(" INNER JOIN c.documento d  ");
/* 623:643 */     sql.append(" LEFT  JOIN a.tipoAsiento ta ");
/* 624:644 */     sql.append(" INNER JOIN dc.cuentaPorCobrar cc ");
/* 625:645 */     sql.append(" INNER JOIN cc.facturaCliente fc ");
/* 626:646 */     sql.append(" INNER JOIN fc.documento dcf ");
/* 627:647 */     sql.append(" LEFT  JOIN fc.asiento af ");
/* 628:648 */     sql.append(" INNER JOIN fc.empresa em ");
/* 629:649 */     sql.append(" INNER JOIN em.categoriaEmpresa ce ");
/* 630:650 */     sql.append(" LEFT  JOIN fc.agenteComercial ac ");
/* 631:651 */     sql.append(" LEFT  JOIN fc.condicionPago cp ");
/* 632:652 */     sql.append(" LEFT  JOIN em.tipoIdentificacion ti ");
/* 633:653 */     sql.append(" LEFT  JOIN fc.subempresa se ");
/* 634:654 */     sql.append(" LEFT  JOIN se.empresa see ");
/* 635:655 */     sql.append(" WHERE c.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 636:656 */     sql.append(" AND dcf.documentoBase = :documentoBase ");
/* 637:657 */     sql.append(" AND c.estado != :estado");
/* 638:658 */     sql.append(" AND fc.estado != :estado");
/* 639:659 */     sql.append(" AND fc.idOrganizacion= :idOrganizacion");
/* 640:660 */     sql.append(" AND (r = :recaudador OR :recaudador IS NULL) ");
/* 641:661 */     sql.append(" AND (ac = :vendedor OR :vendedor IS NULL) ");
/* 642:662 */     sql.append(" AND (ce = :categoriaEmpresa OR :categoriaEmpresa IS NULL) ");
/* 643:663 */     sql.append(" AND (em = :empresa OR :empresa IS NULL) ");
/* 644:    */     
/* 645:665 */     Query query = this.em.createQuery(sql.toString());
/* 646:666 */     query.setParameter("fechaDesde", fechaDesde);
/* 647:667 */     query.setParameter("fechaHasta", fechaHasta);
/* 648:668 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/* 649:669 */     query.setParameter("estado", Estado.ANULADO);
/* 650:670 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 651:671 */     query.setParameter("vendedor", vendedor);
/* 652:672 */     query.setParameter("recaudador", recaudador);
/* 653:673 */     query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 654:674 */     query.setParameter("empresa", empresa);
/* 655:    */     
/* 656:676 */     return query.getResultList();
/* 657:    */   }
/* 658:    */   
/* 659:    */   public List getReporteCalificacionCliente(CategoriaEmpresa categoriaEmpresa, EntidadUsuario vendedor, Recaudador recaudador, Empresa empresa, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 660:    */   {
/* 661:683 */     StringBuilder sql = new StringBuilder();
/* 662:684 */     sql.append(" SELECT new ReporteCarteraCobrada(em.idOrganizacion, em.idEmpresa, em.nombreFiscal, em.codigo, em.identificacion, ");
/* 663:685 */     sql.append(" ti.codigo, ac.idUsuario ,CONCAT( ac.nombre2 ,'  ', ac.nombre1), AVG(cc.diasPlazo), AVG(cc.diasRotacion), (AVG(cc.diasRotacion) - AVG(cc.diasPlazo))) ");
/* 664:    */     
/* 665:687 */     sql.append(" FROM CuentaPorCobrar cc ");
/* 666:688 */     sql.append(" INNER JOIN cc.facturaCliente fc ");
/* 667:689 */     sql.append(" INNER JOIN fc.documento dcf ");
/* 668:690 */     sql.append(" INNER JOIN fc.empresa em ");
/* 669:691 */     sql.append(" INNER JOIN em.categoriaEmpresa ce ");
/* 670:692 */     sql.append(" LEFT  JOIN fc.agenteComercial ac ");
/* 671:693 */     sql.append(" LEFT  JOIN em.tipoIdentificacion ti ");
/* 672:694 */     sql.append(" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 673:695 */     sql.append(" AND dcf.documentoBase = :documentoBase ");
/* 674:696 */     sql.append(" AND fc.estado != :estado");
/* 675:697 */     sql.append(" AND fc.idOrganizacion= :idOrganizacion");
/* 676:698 */     sql.append(" AND cc.fechaCancelacion IS NOT NULL ");
/* 677:699 */     sql.append(" AND (ac = :vendedor OR :vendedor IS NULL) ");
/* 678:700 */     sql.append(" AND (ce = :categoriaEmpresa OR :categoriaEmpresa IS NULL) ");
/* 679:701 */     sql.append(" AND (em = :empresa OR :empresa IS NULL) ");
/* 680:702 */     sql.append(" GROUP BY em.idOrganizacion, em.idEmpresa, em.nombreFiscal, em.codigo, em.identificacion, ti.codigo, ac.idUsuario ,CONCAT( ac.nombre2 ,'  ', ac.nombre1)");
/* 681:    */     
/* 682:    */ 
/* 683:705 */     Query query = this.em.createQuery(sql.toString());
/* 684:706 */     query.setParameter("fechaDesde", fechaDesde);
/* 685:707 */     query.setParameter("fechaHasta", fechaHasta);
/* 686:708 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/* 687:709 */     query.setParameter("estado", Estado.ANULADO);
/* 688:710 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 689:711 */     query.setParameter("vendedor", vendedor);
/* 690:712 */     query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 691:713 */     query.setParameter("empresa", empresa);
/* 692:    */     
/* 693:715 */     return query.getResultList();
/* 694:    */   }
/* 695:    */   
/* 696:    */   public List getSaldoClientes(CategoriaEmpresa categoriaEmpresa, EntidadUsuario vendedor, Recaudador recaudador, Empresa empresa, int idOrganizacion)
/* 697:    */   {
/* 698:722 */     StringBuilder sql = new StringBuilder();
/* 699:723 */     sql.append(" SELECT em.idEmpresa, SUM(cc.saldo) ");
/* 700:724 */     sql.append(" FROM CuentaPorCobrar cc ");
/* 701:725 */     sql.append(" INNER JOIN cc.facturaCliente fc ");
/* 702:726 */     sql.append(" INNER JOIN fc.documento dcf ");
/* 703:727 */     sql.append(" INNER JOIN fc.empresa em ");
/* 704:728 */     sql.append(" INNER JOIN em.categoriaEmpresa ce ");
/* 705:729 */     sql.append(" LEFT  JOIN fc.agenteComercial ac ");
/* 706:730 */     sql.append(" LEFT  JOIN em.tipoIdentificacion ti ");
/* 707:731 */     sql.append(" WHERE dcf.documentoBase = :documentoBase ");
/* 708:732 */     sql.append(" AND fc.estado != :estado");
/* 709:733 */     sql.append(" AND fc.idOrganizacion= :idOrganizacion");
/* 710:734 */     sql.append(" AND (ac = :vendedor OR :vendedor IS NULL) ");
/* 711:735 */     sql.append(" AND (ce = :categoriaEmpresa OR :categoriaEmpresa IS NULL) ");
/* 712:736 */     sql.append(" AND (em = :empresa OR :empresa IS NULL) ");
/* 713:737 */     sql.append(" GROUP BY em.idEmpresa ");
/* 714:    */     
/* 715:739 */     Query query = this.em.createQuery(sql.toString());
/* 716:740 */     query.setParameter("documentoBase", DocumentoBase.FACTURA_CLIENTE);
/* 717:741 */     query.setParameter("estado", Estado.ANULADO);
/* 718:742 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 719:743 */     query.setParameter("vendedor", vendedor);
/* 720:744 */     query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 721:745 */     query.setParameter("empresa", empresa);
/* 722:    */     
/* 723:747 */     return query.getResultList();
/* 724:    */   }
/* 725:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.cobros.ReporteCobroClienteDao
 * JD-Core Version:    0.7.0.1
 */