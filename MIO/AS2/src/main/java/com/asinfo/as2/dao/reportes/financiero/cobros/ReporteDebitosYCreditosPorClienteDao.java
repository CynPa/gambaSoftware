/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.cobros;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empresa;
/*   4:    */ import com.asinfo.as2.entities.Sucursal;
/*   5:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.PersistenceContext;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ReporteDebitosYCreditosPorClienteDao
/*  18:    */ {
/*  19:    */   @PersistenceContext(name="AS2PU")
/*  20:    */   protected EntityManager em;
/*  21:    */   
/*  22:    */   public List<Object[]> getSaldos(int idOrganizacion, Empresa empresa, Date fechaDesde, int tipoReporte, int idVendedor)
/*  23:    */   {
/*  24: 43 */     String numeroFactura = tipoReporte == 1 ? "" : ", v.numeroFactura, v.fechaFactura";
/*  25: 44 */     String fechaVecimiento = tipoReporte == 1 ? "" : ", MAX(v.fechaVencimiento) ";
/*  26:    */     
/*  27: 46 */     StringBuilder sql = new StringBuilder();
/*  28: 47 */     sql.append(" SELECT v.idEmpresa, v.nombreFiscal, SUM(v.debito - v.credito)");
/*  29: 48 */     sql.append(numeroFactura + fechaVecimiento + ", v.identificacion, v.nombreAgenteComercial");
/*  30: 49 */     sql.append(" FROM VEstadoCuenta v ");
/*  31: 50 */     sql.append(" WHERE v.fechaDocumento < :fechaDesde ");
/*  32: 52 */     if (empresa != null) {
/*  33: 53 */       sql.append(" AND (v.idEmpresa = :idCliente) ");
/*  34:    */     }
/*  35: 56 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/*  36: 57 */     if (idVendedor != 0) {
/*  37: 58 */       sql.append(" AND ( v.idAgenteComercial = :idVendedor  ) ");
/*  38:    */     }
/*  39: 60 */     sql.append(" GROUP BY v.idEmpresa, v.nombreFiscal" + numeroFactura + ", v.identificacion, v.nombreAgenteComercial ");
/*  40: 61 */     sql.append(" HAVING SUM(v.debito - v.credito)!=0");
/*  41: 62 */     sql.append(" ORDER BY v.nombreFiscal");
/*  42:    */     
/*  43: 64 */     Query query = this.em.createQuery(sql.toString());
/*  44: 65 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  45: 67 */     if (idVendedor != 0) {
/*  46: 68 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/*  47:    */     }
/*  48: 70 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  49: 71 */     if (empresa != null) {
/*  50: 72 */       query.setParameter("idCliente", Integer.valueOf(empresa.getId()));
/*  51:    */     }
/*  52: 75 */     return query.getResultList();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<Object[]> getProtestos(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, int idVendedor)
/*  56:    */   {
/*  57: 82 */     String numeroFactura = tipoReporte == 1 ? "" : ", v.numeroFactura, v.fechaFactura, v.chequeProtestado";
/*  58: 83 */     String fechaVecimiento = tipoReporte == 1 ? "" : ", MAX(v.fechaVencimiento) ";
/*  59:    */     
/*  60: 85 */     StringBuilder sql = new StringBuilder();
/*  61: 86 */     sql.append(" SELECT v.idEmpresa, v.nombreFiscal, SUM(v.debito)");
/*  62: 87 */     sql.append(numeroFactura + fechaVecimiento + ", v.identificacion ");
/*  63: 88 */     sql.append(" FROM VEstadoCuenta v ");
/*  64: 89 */     sql.append(" WHERE v.fechaDocumento BETWEEN :fechaDesde AND :fechaHasta");
/*  65: 90 */     sql.append(" AND v.debito != 0");
/*  66: 91 */     sql.append(" AND v.indicadorGeneradaProtesto = true");
/*  67: 93 */     if (idVendedor != 0) {
/*  68: 94 */       sql.append(" AND ( v.idAgenteComercial = :idVendedor  ) ");
/*  69:    */     }
/*  70: 96 */     if (empresa != null) {
/*  71: 97 */       sql.append(" AND (v.idEmpresa = :idCliente) ");
/*  72:    */     }
/*  73:100 */     if (sucursal != null) {
/*  74:101 */       sql.append(" AND (v.idSucursal = :idSucursal) ");
/*  75:    */     }
/*  76:104 */     sql.append(" AND v.idOrganizacion = :idOrganizacion ");
/*  77:105 */     sql.append(" GROUP BY v.idEmpresa, v.nombreFiscal" + numeroFactura + ", v.identificacion ");
/*  78:106 */     sql.append(" HAVING SUM(v.debito - v.credito)!=0");
/*  79:107 */     sql.append(" ORDER BY v.nombreFiscal");
/*  80:    */     
/*  81:109 */     Query query = this.em.createQuery(sql.toString());
/*  82:110 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  83:111 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/*  84:112 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/*  85:113 */     if (idVendedor != 0) {
/*  86:115 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/*  87:    */     }
/*  88:117 */     if (empresa != null) {
/*  89:118 */       query.setParameter("idCliente", Integer.valueOf(empresa.getId()));
/*  90:    */     }
/*  91:121 */     if (sucursal != null) {
/*  92:122 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  93:    */     }
/*  94:125 */     return query.getResultList();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<Object[]> getCobros(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, Estado estado, int tipoReporte, int idVendedor)
/*  98:    */   {
/*  99:133 */     String numeroFactura = tipoReporte == 1 ? "" : ", f.numero, f.fecha, f.fechaVencimiento";
/* 100:    */     
/* 101:135 */     StringBuilder sql = new StringBuilder();
/* 102:136 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(dcfc.valor)");
/* 103:137 */     sql.append(numeroFactura + ", em.identificacion");
/* 104:138 */     sql.append(" FROM DetalleCobroFormaCobro dcfc ");
/* 105:139 */     sql.append(" INNER JOIN dcfc.detalleFormaCobro dfc ");
/* 106:140 */     sql.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 107:    */     
/* 108:142 */     sql.append(" INNER JOIN dfc.formaPago fc");
/* 109:143 */     sql.append(" INNER JOIN dfc.cobro co ");
/* 110:144 */     sql.append(" INNER JOIN dc.cuentaPorCobrar  cc ");
/* 111:145 */     sql.append(" INNER JOIN cc.facturaCliente f ");
/* 112:146 */     sql.append(" LEFT JOIN f.agenteComercial ac ");
/* 113:147 */     sql.append(" INNER JOIN co.empresa em ");
/* 114:148 */     sql.append(" WHERE co.idOrganizacion = :idOrganizacion ");
/* 115:149 */     sql.append(" AND fc.indicadorRetencionIva=false");
/* 116:150 */     sql.append(" AND fc.indicadorRetencionFuente=false");
/* 117:151 */     if (idVendedor != 0) {
/* 118:152 */       sql.append(" AND ( ac.idUsuario = :idVendedor  ) ");
/* 119:    */     }
/* 120:154 */     if (empresa != null) {
/* 121:155 */       sql.append(" AND (em = :empresa) ");
/* 122:    */     }
/* 123:158 */     if (sucursal != null) {
/* 124:159 */       sql.append(" AND (f.sucursal = :sucursal) ");
/* 125:    */     }
/* 126:162 */     if (estado == Estado.CONTABILIZADO) {
/* 127:163 */       sql.append(" AND co.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 128:    */     } else {
/* 129:165 */       sql.append(" AND co.fecha <= :fechaHasta ");
/* 130:    */     }
/* 131:168 */     sql.append(" AND co.estado=:estado");
/* 132:169 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura + ", em.identificacion ");
/* 133:170 */     sql.append(" ORDER BY em.nombreFiscal");
/* 134:    */     
/* 135:172 */     Query query = this.em.createQuery(sql.toString());
/* 136:173 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 137:175 */     if (estado == Estado.CONTABILIZADO) {
/* 138:176 */       query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 139:    */     }
/* 140:179 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 141:180 */     query.setParameter("estado", estado);
/* 142:181 */     if (idVendedor != 0) {
/* 143:182 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/* 144:    */     }
/* 145:184 */     if (empresa != null) {
/* 146:185 */       query.setParameter("empresa", empresa);
/* 147:    */     }
/* 148:188 */     if (sucursal != null) {
/* 149:189 */       query.setParameter("sucursal", sucursal);
/* 150:    */     }
/* 151:192 */     return query.getResultList();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<Object[]> getRetenciones(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, int idVendedor)
/* 155:    */   {
/* 156:200 */     String numeroFactura = tipoReporte == 1 ? "" : ", f.numero, f.fecha, f.fechaVencimiento";
/* 157:    */     
/* 158:202 */     StringBuilder sql = new StringBuilder();
/* 159:203 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(dcfc.valor)");
/* 160:204 */     sql.append(numeroFactura + ", em.identificacion");
/* 161:205 */     sql.append(" FROM DetalleCobroFormaCobro dcfc ");
/* 162:206 */     sql.append(" INNER JOIN dcfc.detalleFormaCobro dfc ");
/* 163:207 */     sql.append(" INNER JOIN dcfc.detalleCobro dc ");
/* 164:    */     
/* 165:209 */     sql.append(" INNER JOIN dfc.formaPago fc");
/* 166:210 */     sql.append(" INNER JOIN dfc.cobro co");
/* 167:211 */     sql.append(" INNER JOIN dc.cuentaPorCobrar  cc ");
/* 168:212 */     sql.append(" INNER JOIN cc.facturaCliente f ");
/* 169:213 */     sql.append(" LEFT JOIN f.agenteComercial ac ");
/* 170:214 */     sql.append(" INNER JOIN co.empresa em ");
/* 171:215 */     sql.append(" WHERE co.idOrganizacion = :idOrganizacion ");
/* 172:216 */     sql.append(" AND (fc.indicadorRetencionIva=true OR fc.indicadorRetencionFuente=true) ");
/* 173:217 */     if (idVendedor != 0) {
/* 174:218 */       sql.append(" AND (ac.idUsuario = :idVendedor  ) ");
/* 175:    */     }
/* 176:220 */     if (empresa != null) {
/* 177:221 */       sql.append(" AND (em = :empresa) ");
/* 178:    */     }
/* 179:224 */     if (sucursal != null) {
/* 180:225 */       sql.append(" AND (f.sucursal = :sucursal) ");
/* 181:    */     }
/* 182:228 */     sql.append(" AND co.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 183:229 */     sql.append(" AND co.estado>=:estado");
/* 184:230 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura + " " + ", em.identificacion ");
/* 185:231 */     sql.append(" ORDER BY em.nombreFiscal");
/* 186:    */     
/* 187:233 */     Query query = this.em.createQuery(sql.toString());
/* 188:234 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 189:235 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 190:236 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 191:237 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 192:238 */     if (idVendedor != 0) {
/* 193:239 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/* 194:    */     }
/* 195:241 */     if (empresa != null) {
/* 196:242 */       query.setParameter("empresa", empresa);
/* 197:    */     }
/* 198:245 */     if (sucursal != null) {
/* 199:246 */       query.setParameter("sucursal", sucursal);
/* 200:    */     }
/* 201:249 */     return query.getResultList();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<Object[]> getLiquidaciones(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, int idVendedor)
/* 205:    */   {
/* 206:256 */     String numeroFactura = tipoReporte == 1 ? "" : ", fc.numero, fc.fecha, fc.fechaVencimiento ";
/* 207:    */     
/* 208:258 */     StringBuilder sql = new StringBuilder();
/* 209:259 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(dlac.valor)");
/* 210:260 */     sql.append(numeroFactura + ", em.identificacion");
/* 211:261 */     sql.append(" FROM DetalleLiquidacionAnticipoCliente dlac ");
/* 212:262 */     sql.append(" JOIN dlac.cuentaPorCobrar cxc ");
/* 213:263 */     sql.append(" JOIN cxc.facturaCliente fc ");
/* 214:264 */     sql.append(" LEFT JOIN fc.agenteComercial agc ");
/* 215:265 */     sql.append(" JOIN dlac.liquidacionAnticipoCliente lac ");
/* 216:266 */     sql.append(" JOIN lac.anticipoCliente ac ");
/* 217:267 */     sql.append(" JOIN ac.empresa em ");
/* 218:268 */     sql.append(" WHERE lac.idOrganizacion = :idOrganizacion ");
/* 219:270 */     if (empresa != null) {
/* 220:271 */       sql.append(" AND (em = :empresa) ");
/* 221:    */     }
/* 222:274 */     if (sucursal != null) {
/* 223:275 */       sql.append(" AND (fc.sucursal = :sucursal) ");
/* 224:    */     }
/* 225:278 */     sql.append(" AND lac.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 226:279 */     sql.append(" AND lac.estado>=:estado");
/* 227:280 */     if (idVendedor != 0) {
/* 228:281 */       sql.append(" AND ( agc.idUsuario = :idVendedor  ) ");
/* 229:    */     }
/* 230:283 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura + " " + ", em.identificacion ");
/* 231:284 */     sql.append(" ORDER BY em.nombreFiscal");
/* 232:    */     
/* 233:286 */     Query query = this.em.createQuery(sql.toString());
/* 234:287 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 235:288 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 236:289 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 237:290 */     query.setParameter("estado", Estado.CONTABILIZADO);
/* 238:292 */     if (idVendedor != 0) {
/* 239:293 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/* 240:    */     }
/* 241:295 */     if (empresa != null) {
/* 242:296 */       query.setParameter("empresa", empresa);
/* 243:    */     }
/* 244:299 */     if (sucursal != null) {
/* 245:300 */       query.setParameter("sucursal", sucursal);
/* 246:    */     }
/* 247:303 */     return query.getResultList();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<Object[]> getVentas(int idOrganizacion, Sucursal sucursal, Empresa empresa, DocumentoBase documentoBase, Date fechaDesde, Date fechaHasta, int tipoReporte, int idVendedor)
/* 251:    */   {
/* 252:310 */     String numeroFactura = tipoReporte == 1 ? "" : ", fc.numero, fc.fecha, fc.fechaVencimiento";
/* 253:    */     
/* 254:312 */     List<DocumentoBase> listaDocumentoBase = new ArrayList();
/* 255:313 */     listaDocumentoBase.add(documentoBase);
/* 256:315 */     if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE)
/* 257:    */     {
/* 258:316 */       listaDocumentoBase.add(DocumentoBase.DEVOLUCION_CLIENTE);
/* 259:317 */       numeroFactura = tipoReporte == 1 ? "" : ", fcp.numero, fcp.fecha, fcp.fechaVencimiento";
/* 260:    */     }
/* 261:321 */     StringBuilder sql = new StringBuilder();
/* 262:322 */     sql.append(" SELECT em.idEmpresa, em.nombreFiscal, SUM(fc.total-fc.descuento), SUM(fc.impuesto)");
/* 263:323 */     sql.append(numeroFactura + ", em.identificacion");
/* 264:324 */     if (tipoReporte == 2) {
/* 265:325 */       sql.append(", fc.referencia2, sub.nombreFiscal, CONCAT(ac.nombre1, ac.nombre2)");
/* 266:    */     }
/* 267:327 */     sql.append(" FROM FacturaCliente fc ");
/* 268:328 */     sql.append(" LEFT JOIN fc.agenteComercial ac ");
/* 269:329 */     sql.append(" LEFT JOIN fc.empresa em");
/* 270:330 */     sql.append(" LEFT JOIN fc.documento d");
/* 271:331 */     if (tipoReporte == 2)
/* 272:    */     {
/* 273:332 */       sql.append(" LEFT JOIN fc.subempresa se ");
/* 274:333 */       sql.append(" LEFT JOIN se.empresa sub ");
/* 275:    */     }
/* 276:335 */     if (documentoBase == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/* 277:336 */       sql.append(" LEFT JOIN fc.facturaClientePadre fcp ");
/* 278:    */     }
/* 279:338 */     sql.append(" WHERE fc.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 280:339 */     sql.append(" AND fc.estado != :estadoAnulado");
/* 281:340 */     sql.append(" AND d.documentoBase in (:listaDocumentoBase) ");
/* 282:342 */     if (idVendedor != 0) {
/* 283:343 */       sql.append(" AND ( ac.idUsuario = :idVendedor  ) ");
/* 284:    */     }
/* 285:345 */     if (empresa != null) {
/* 286:346 */       sql.append(" AND (em = :empresa) ");
/* 287:    */     }
/* 288:349 */     if (sucursal != null) {
/* 289:350 */       sql.append(" AND (fc.sucursal = :sucursal) ");
/* 290:    */     }
/* 291:353 */     sql.append(" AND fc.idOrganizacion = :idOrganizacion ");
/* 292:354 */     sql.append(" GROUP BY em.idEmpresa, em.nombreFiscal" + numeroFactura + ", em.identificacion ");
/* 293:355 */     if (tipoReporte == 2) {
/* 294:356 */       sql.append(", fc.referencia2, sub.nombreFiscal, CONCAT(ac.nombre1, ac.nombre2) ");
/* 295:    */     }
/* 296:358 */     sql.append(" ORDER BY em.nombreFiscal");
/* 297:    */     
/* 298:360 */     Query query = this.em.createQuery(sql.toString());
/* 299:361 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 300:362 */     query.setParameter("fechaDesde", fechaDesde, TemporalType.DATE);
/* 301:363 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 302:364 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 303:365 */     query.setParameter("listaDocumentoBase", listaDocumentoBase);
/* 304:367 */     if (idVendedor != 0) {
/* 305:368 */       query.setParameter("idVendedor", Integer.valueOf(idVendedor));
/* 306:    */     }
/* 307:370 */     if (empresa != null) {
/* 308:371 */       query.setParameter("empresa", empresa);
/* 309:    */     }
/* 310:374 */     if (sucursal != null) {
/* 311:375 */       query.setParameter("sucursal", sucursal);
/* 312:    */     }
/* 313:378 */     return query.getResultList();
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<Object[]> getSaldoAnticipoCliente(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, int idVendedor)
/* 317:    */   {
/* 318:386 */     StringBuilder sql = new StringBuilder();
/* 319:387 */     sql.append(" SELECT e.idEmpresa, e.nombreFiscal, ");
/* 320:388 */     sql.append(" SUM ( ac.valor) , ");
/* 321:389 */     sql.append(" SUM ( CASE WHEN ac.fechaDevolucion IS NULL OR ac.fechaDevolucion > :fechaHasta THEN (ac.valor * 0) ELSE ac.valorDevolucion END ) ");
/* 322:390 */     sql.append(" FROM AnticipoCliente ac ");
/* 323:391 */     sql.append(" INNER JOIN ac.empresa e ");
/* 324:392 */     sql.append(" WHERE ac.idOrganizacion = :idOrganizacion ");
/* 325:393 */     sql.append(" AND ac.fecha <= :fechaHasta ");
/* 326:394 */     sql.append(" AND ac.estado != :estadoAnulado ");
/* 327:395 */     if (empresa != null) {
/* 328:396 */       sql.append(" AND (e = :empresa) ");
/* 329:    */     }
/* 330:399 */     if (sucursal != null) {
/* 331:400 */       sql.append(" AND (ac.sucursal.idSucursal = :idSucursal) ");
/* 332:    */     }
/* 333:403 */     sql.append(" GROUP BY e.idEmpresa, e.nombreFiscal ");
/* 334:404 */     sql.append(" ORDER BY e.nombreFiscal");
/* 335:    */     
/* 336:406 */     Query query = this.em.createQuery(sql.toString());
/* 337:407 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 338:408 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 339:409 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 340:410 */     if (empresa != null) {
/* 341:411 */       query.setParameter("empresa", empresa);
/* 342:    */     }
/* 343:414 */     if (sucursal != null) {
/* 344:415 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 345:    */     }
/* 346:418 */     List<Object[]> lista = query.getResultList();
/* 347:    */     
/* 348:420 */     return lista;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<Object[]> getSaldoLiquidaciones(int idOrganizacion, Sucursal sucursal, Empresa empresa, Date fechaDesde, Date fechaHasta, int tipoReporte, int idVendedor)
/* 352:    */   {
/* 353:428 */     StringBuilder sql = new StringBuilder();
/* 354:429 */     sql.append(" SELECT e.idEmpresa, e.nombreFiscal, ");
/* 355:430 */     sql.append(" SUM ( lac.valor) ");
/* 356:431 */     sql.append(" FROM LiquidacionAnticipoCliente lac ");
/* 357:432 */     sql.append(" INNER JOIN lac.anticipoCliente ac ");
/* 358:433 */     sql.append(" INNER JOIN ac.empresa e ");
/* 359:434 */     sql.append(" WHERE ac.idOrganizacion = :idOrganizacion ");
/* 360:435 */     sql.append(" AND lac.fecha <= :fechaHasta ");
/* 361:436 */     sql.append(" AND lac.estado != :estadoAnulado ");
/* 362:437 */     if (empresa != null) {
/* 363:438 */       sql.append(" AND (e = :empresa) ");
/* 364:    */     }
/* 365:441 */     if (sucursal != null) {
/* 366:442 */       sql.append(" AND (ac.sucursal.idSucursal = :idSucursal) ");
/* 367:    */     }
/* 368:445 */     sql.append(" GROUP BY e.idEmpresa, e.nombreFiscal ");
/* 369:446 */     sql.append(" ORDER BY e.nombreFiscal");
/* 370:    */     
/* 371:448 */     Query query = this.em.createQuery(sql.toString());
/* 372:449 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 373:450 */     query.setParameter("fechaHasta", fechaHasta, TemporalType.DATE);
/* 374:451 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 375:452 */     if (empresa != null) {
/* 376:453 */       query.setParameter("empresa", empresa);
/* 377:    */     }
/* 378:456 */     if (sucursal != null) {
/* 379:457 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 380:    */     }
/* 381:460 */     List<Object[]> lista = query.getResultList();
/* 382:    */     
/* 383:462 */     return lista;
/* 384:    */   }
/* 385:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.cobros.ReporteDebitosYCreditosPorClienteDao
 * JD-Core Version:    0.7.0.1
 */