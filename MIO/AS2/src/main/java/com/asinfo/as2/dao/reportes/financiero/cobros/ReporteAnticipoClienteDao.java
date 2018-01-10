/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.cobros;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.text.SimpleDateFormat;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Collection;
/*  13:    */ import java.util.Collections;
/*  14:    */ import java.util.Comparator;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.Stateless;
/*  20:    */ import javax.persistence.EntityManager;
/*  21:    */ import javax.persistence.Query;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ReporteAnticipoClienteDao
/*  25:    */   extends AbstractDaoAS2<AnticipoProveedor>
/*  26:    */ {
/*  27:    */   public ReporteAnticipoClienteDao()
/*  28:    */   {
/*  29: 37 */     super(AnticipoProveedor.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List getReporteAnticipo(int idAnticipo)
/*  33:    */   {
/*  34: 42 */     String sql = " SELECT e.nombreFiscal, e.nombreComercial, e.identificacion,  ac.fecha, ac.numero, ac.valor, ac.documentoReferencia , ac.descripcion,  coalesce(fp.nombre,''), coalesce(cb.numero,''), coalesce(b.nombre,'')  FROM AnticipoCliente ac  INNER JOIN ac.empresa e  LEFT JOIN ac.formaPago fp  LEFT JOIN ac.cuentaBancariaOrganizacion cbo  LEFT JOIN cbo.cuentaBancaria cb  LEFT JOIN cb.banco b  WHERE ac.idAnticipoCliente = :idAnticipo ";
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40: 48 */     Query query = this.em.createQuery(sql).setParameter("idAnticipo", Integer.valueOf(idAnticipo));
/*  41: 49 */     return query.getResultList();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List getAnticipos(Date fechaDesde, Date fechaHasta, int idCliente, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/*  45:    */     throws ExcepcionAS2
/*  46:    */   {
/*  47: 56 */     StringBuilder sql = new StringBuilder();
/*  48: 57 */     String select = "SELECT ac.numero, ac.valor, ac.valor*0, e.nombreComercial, e.nombreFiscal,  ac.fecha ";
/*  49: 58 */     if (indicadorIncluirNotasCreditos) {
/*  50: 59 */       select = select + ", fc.numero, ac.saldo*0, e.identificacion ";
/*  51:    */     } else {
/*  52: 61 */       select = select + ", '', ac.saldo*0, e.identificacion ";
/*  53:    */     }
/*  54: 63 */     if (indicadorIncluirNotasCreditos) {
/*  55: 64 */       select = select + ", nc.numero, ac.fecha, ac.valor*0 ";
/*  56:    */     } else {
/*  57: 66 */       select = select + ", '', ac.fecha, ac.valor*0 ";
/*  58:    */     }
/*  59: 68 */     sql.append(select);
/*  60: 69 */     sql.append(" FROM AnticipoCliente ac ");
/*  61: 70 */     if (indicadorIncluirNotasCreditos) {
/*  62: 71 */       sql.append(" LEFT JOIN ac.notaCreditoCliente nc ");
/*  63:    */     }
/*  64: 73 */     sql.append(" LEFT JOIN ac.empresa e ");
/*  65: 74 */     if (indicadorIncluirNotasCreditos) {
/*  66: 75 */       sql.append(" LEFT JOIN nc.facturaClientePadre fc ");
/*  67:    */     }
/*  68: 77 */     sql.append(" WHERE ac.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  69: 78 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/*  70: 79 */     sql.append(" AND (e.idEmpresa =:idCliente OR 0=:idCliente) ");
/*  71: 80 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/*  72: 81 */     if (!indicadorIncluirNotasCreditos) {
/*  73: 82 */       sql.append(" AND ac.notaCreditoCliente is null ");
/*  74:    */     }
/*  75: 86 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  76: 87 */       sql.append(" AND ac.sucursal.idSucursal = :idSucursal");
/*  77:    */     }
/*  78: 90 */     Query query = this.em.createQuery(sql.toString());
/*  79: 91 */     query.setParameter("fechaDesde", fechaDesde);
/*  80: 92 */     query.setParameter("fechaHasta", fechaHasta);
/*  81: 93 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/*  82: 94 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/*  83: 95 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  84: 97 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/*  85: 98 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/*  86:    */     }
/*  87:101 */     return query.getResultList();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List getLiquidaciones(Date fechaDesde, Date fechaHasta, int idCliente, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/*  91:    */     throws ExcepcionAS2
/*  92:    */   {
/*  93:116 */     StringBuilder sql = new StringBuilder();
/*  94:117 */     String select = "SELECT ac.numero, ac.valor*0, dlac.valor, e.nombreComercial, e.nombreFiscal,  lac.fecha, fc.numero, ac.saldo*0, e.identificacion ";
/*  95:118 */     if (indicadorIncluirNotasCreditos) {
/*  96:119 */       select = select + ", nc.numero, ac.fecha, ac.valor*0 ";
/*  97:    */     } else {
/*  98:121 */       select = select + ", '', ac.fecha, ac.valor*0 ";
/*  99:    */     }
/* 100:123 */     sql.append(select);
/* 101:124 */     sql.append(" FROM DetalleLiquidacionAnticipoCliente dlac ");
/* 102:125 */     sql.append(" LEFT JOIN  dlac.liquidacionAnticipoCliente lac ");
/* 103:126 */     sql.append(" LEFT JOIN lac.anticipoCliente ac ");
/* 104:127 */     if (indicadorIncluirNotasCreditos) {
/* 105:128 */       sql.append(" LEFT JOIN ac.notaCreditoCliente nc ");
/* 106:    */     }
/* 107:130 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 108:131 */     sql.append(" LEFT JOIN dlac.cuentaPorCobrar cxc ");
/* 109:132 */     sql.append(" LEFT JOIN cxc.facturaCliente fc ");
/* 110:133 */     sql.append(" WHERE lac.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/* 111:134 */     sql.append(" AND lac.estado <> :estadoLiquidacionAnticipo ");
/* 112:135 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 113:136 */     sql.append(" AND (e.idEmpresa =:idCliente OR 0=:idCliente) ");
/* 114:137 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 115:138 */     if (!indicadorIncluirNotasCreditos) {
/* 116:139 */       sql.append(" AND ac.notaCreditoCliente is null ");
/* 117:    */     }
/* 118:142 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 119:143 */       sql.append(" AND dlac.idSucursal = :idSucursal");
/* 120:    */     }
/* 121:145 */     sql.append(" ORDER BY lac.fecha ");
/* 122:    */     
/* 123:147 */     Query query = this.em.createQuery(sql.toString());
/* 124:148 */     query.setParameter("fechaDesde", fechaDesde);
/* 125:149 */     query.setParameter("fechaHasta", fechaHasta);
/* 126:150 */     query.setParameter("estadoLiquidacionAnticipo", Estado.ANULADO);
/* 127:151 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 128:152 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 129:153 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 130:155 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 131:156 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 132:    */     }
/* 133:159 */     return query.getResultList();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List getDevoluciones(Date fechaDesde, Date fechaHasta, int idCliente, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/* 137:    */     throws ExcepcionAS2
/* 138:    */   {
/* 139:167 */     StringBuilder sql = new StringBuilder();
/* 140:168 */     sql.append(" SELECT ac.numero, ac.valor*0, ac.valor*0 , e.nombreComercial, e.nombreFiscal,  ac.fechaDevolucion, CONCAT('DEV# ',ac.numero), ac.saldo*0, e.identificacion, CONCAT('DEV# ',ac.numero), ac.fecha, ac.valorDevolucion ");
/* 141:169 */     sql.append(" FROM AnticipoCliente ac ");
/* 142:170 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 143:171 */     sql.append(" WHERE ac.idOrganizacion = :idOrganizacion ");
/* 144:172 */     sql.append(" AND ac.fechaDevolucion BETWEEN :fechaDesde AND :fechaHasta ");
/* 145:173 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 146:174 */     if (idCliente != 0) {
/* 147:175 */       sql.append(" AND e.idEmpresa =:idCliente");
/* 148:    */     }
/* 149:177 */     sql.append(" AND ac.fechaDevolucion IS NOT NULL ");
/* 150:179 */     if (!indicadorIncluirNotasCreditos) {
/* 151:180 */       sql.append(" AND ac.notaCreditoCliente IS NULL ");
/* 152:    */     }
/* 153:183 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 154:184 */       sql.append(" AND ac.sucursal.idSucursal = :idSucursal");
/* 155:    */     }
/* 156:187 */     Query query = this.em.createQuery(sql.toString());
/* 157:188 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 158:189 */     query.setParameter("fechaDesde", fechaDesde);
/* 159:190 */     query.setParameter("fechaHasta", fechaHasta);
/* 160:191 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 161:192 */     if (idCliente != 0) {
/* 162:193 */       query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 163:    */     }
/* 164:197 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 165:198 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 166:    */     }
/* 167:201 */     return query.getResultList();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List getAnticipoCliente(Date fechaDesde, Date fechaHasta, int idCliente, boolean indicadorSaldoDiferenciaCero, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/* 171:    */     throws ExcepcionAS2
/* 172:    */   {
/* 173:210 */     List<Object[]> anticiposSaldoIncial = getAnticiposSaldoIncial(fechaDesde, idCliente, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 174:211 */     List<Object[]> liquidacionesSaldoInicial = getLiquidacionesSaldoIncial(fechaDesde, idCliente, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 175:    */     
/* 176:213 */     List<Object[]> devolucionesSaldoInicial = getDevolucionesSaldoIncial(fechaDesde, idCliente, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 177:    */     
/* 178:    */ 
/* 179:216 */     Map<String, Object[]> hmAnticipos = new HashMap();
/* 180:218 */     for (Object[] o : anticiposSaldoIncial) {
/* 181:219 */       hmAnticipos.put(o[1].toString(), o);
/* 182:    */     }
/* 183:222 */     for (Object[] o : liquidacionesSaldoInicial) {
/* 184:223 */       if (hmAnticipos.get(o[1].toString()) != null)
/* 185:    */       {
/* 186:224 */         Object[] obj = (Object[])hmAnticipos.get(o[1].toString());
/* 187:225 */         obj[0] = ((BigDecimal)obj[0]).subtract((BigDecimal)o[0]);
/* 188:226 */         hmAnticipos.put(o[1].toString(), obj);
/* 189:    */       }
/* 190:    */       else
/* 191:    */       {
/* 192:228 */         BigDecimal n = BigDecimal.ZERO;
/* 193:229 */         o[0] = n.subtract((BigDecimal)o[0]);
/* 194:230 */         hmAnticipos.put(o[1].toString(), o);
/* 195:    */       }
/* 196:    */     }
/* 197:233 */     for (Object[] o : devolucionesSaldoInicial) {
/* 198:234 */       if (hmAnticipos.get(o[1].toString()) != null)
/* 199:    */       {
/* 200:235 */         Object[] obj = (Object[])hmAnticipos.get(o[1].toString());
/* 201:236 */         obj[0] = ((BigDecimal)obj[0]).subtract((BigDecimal)o[0]);
/* 202:237 */         hmAnticipos.put(o[1].toString(), obj);
/* 203:    */       }
/* 204:    */       else
/* 205:    */       {
/* 206:239 */         BigDecimal n = BigDecimal.ZERO;
/* 207:240 */         o[0] = n.subtract((BigDecimal)o[0]);
/* 208:241 */         hmAnticipos.put(o[1].toString(), o);
/* 209:    */       }
/* 210:    */     }
/* 211:246 */     Object anticipos = getAnticipos(fechaDesde, fechaHasta, idCliente, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 212:247 */     List<Object[]> liquidaciones = getLiquidaciones(fechaDesde, fechaHasta, idCliente, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 213:248 */     List<Object[]> devoluciones = getDevoluciones(fechaDesde, fechaHasta, idCliente, idOrganizacion, sucursal, indicadorIncluirNotasCreditos);
/* 214:    */     
/* 215:250 */     Map<String, Object[]> hmReporte = new HashMap();
/* 216:251 */     List<Object[]> list = new ArrayList();
/* 217:253 */     for (Object[] datos : hmAnticipos.values()) {
/* 218:254 */       if ((datos != null) && (((BigDecimal)datos[0]).compareTo(BigDecimal.ZERO) != 0))
/* 219:    */       {
/* 220:255 */         Object[] saldoInicial = new Object[15];
/* 221:    */         
/* 222:257 */         saldoInicial[0] = " Saldo Inicial";
/* 223:258 */         saldoInicial[1] = datos[0];
/* 224:259 */         saldoInicial[2] = BigDecimal.ZERO;
/* 225:260 */         saldoInicial[11] = BigDecimal.ZERO;
/* 226:261 */         saldoInicial[3] = datos[1];
/* 227:262 */         saldoInicial[4] = datos[2];
/* 228:263 */         saldoInicial[5] = FuncionesUtiles.getFecha(1, 1, 1950);
/* 229:264 */         saldoInicial[10] = FuncionesUtiles.getFecha(1, 1, 1950);
/* 230:265 */         hmReporte.put("Saldo Inicial~" + datos[1], saldoInicial);
/* 231:    */       }
/* 232:    */     }
/* 233:269 */     for (Object[] datos : (List)anticipos) {
/* 234:270 */       hmReporte.put(datos[0].toString(), datos);
/* 235:    */     }
/* 236:274 */     for (Object[] datos : liquidaciones)
/* 237:    */     {
/* 238:275 */       Object[] detalleReporte = (Object[])hmReporte.get(datos[0].toString());
/* 239:276 */       if ((detalleReporte != null) && (((BigDecimal)detalleReporte[2]).compareTo(BigDecimal.ZERO) == 0))
/* 240:    */       {
/* 241:277 */         detalleReporte[2] = datos[2];
/* 242:278 */         detalleReporte[5] = datos[5];
/* 243:279 */         detalleReporte[6] = datos[6];
/* 244:280 */         detalleReporte[9] = datos[9];
/* 245:    */       }
/* 246:    */       else
/* 247:    */       {
/* 248:282 */         list.add(datos);
/* 249:    */       }
/* 250:    */     }
/* 251:287 */     for (Object[] datos : devoluciones)
/* 252:    */     {
/* 253:288 */       Object[] detalleReporte = (Object[])hmReporte.get(datos[0].toString());
/* 254:289 */       if ((detalleReporte != null) && (((BigDecimal)detalleReporte[2]).compareTo(BigDecimal.ZERO) == 0))
/* 255:    */       {
/* 256:290 */         detalleReporte[2] = datos[2];
/* 257:291 */         detalleReporte[5] = datos[5];
/* 258:292 */         detalleReporte[6] = datos[6];
/* 259:293 */         detalleReporte[11] = datos[11];
/* 260:    */       }
/* 261:    */       else
/* 262:    */       {
/* 263:295 */         list.add(datos);
/* 264:    */       }
/* 265:    */     }
/* 266:300 */     Object datosReporte = new ArrayList(hmReporte.values());
/* 267:301 */     ((List)datosReporte).addAll(list);
/* 268:    */     Map<String, BigDecimal> hmSaldos;
/* 269:    */     Map<String, List<Object[]>> hmListaAnticipos;
/* 270:304 */     if (indicadorSaldoDiferenciaCero)
/* 271:    */     {
/* 272:306 */       hmSaldos = new HashMap();
/* 273:307 */       hmListaAnticipos = new HashMap();
/* 274:309 */       for (Object[] datos : (List)datosReporte)
/* 275:    */       {
/* 276:311 */         BigDecimal valorAnticipo = (BigDecimal)datos[1];
/* 277:312 */         BigDecimal valorLiquidacion = (BigDecimal)datos[2];
/* 278:313 */         BigDecimal valorDevolucion = (BigDecimal)datos[11];
/* 279:    */         
/* 280:315 */         BigDecimal saldoAnticipo = (BigDecimal)hmSaldos.get((String)datos[0]);
/* 281:316 */         List<Object[]> detalleReporte = (List)hmListaAnticipos.get((String)datos[0]);
/* 282:318 */         if (saldoAnticipo == null)
/* 283:    */         {
/* 284:319 */           saldoAnticipo = valorAnticipo.subtract(valorLiquidacion).subtract(valorDevolucion);
/* 285:320 */           hmSaldos.put((String)datos[0], saldoAnticipo);
/* 286:    */           
/* 287:322 */           detalleReporte = new ArrayList();
/* 288:323 */           detalleReporte.add(datos);
/* 289:324 */           hmListaAnticipos.put((String)datos[0], detalleReporte);
/* 290:    */         }
/* 291:    */         else
/* 292:    */         {
/* 293:326 */           saldoAnticipo = saldoAnticipo.add(valorAnticipo).subtract(valorLiquidacion).subtract(valorDevolucion);
/* 294:327 */           hmSaldos.put((String)datos[0], saldoAnticipo);
/* 295:328 */           detalleReporte.add(datos);
/* 296:    */         }
/* 297:    */       }
/* 298:332 */       datosReporte = new ArrayList();
/* 299:333 */       for (String numeroAnticipo : hmSaldos.keySet()) {
/* 300:334 */         if (((BigDecimal)hmSaldos.get(numeroAnticipo)).compareTo(BigDecimal.ZERO) != 0) {
/* 301:335 */           ((List)datosReporte).addAll((Collection)hmListaAnticipos.get(numeroAnticipo));
/* 302:    */         }
/* 303:    */       }
/* 304:    */     }
/* 305:340 */     Collections.sort((List)datosReporte, new Comparator()
/* 306:    */     {
/* 307:    */       public int compare(Object[] o1, Object[] o2)
/* 308:    */       {
/* 309:343 */         return ((String)o1[4] + "_" + (String)o1[0] + "_" + new SimpleDateFormat("yyyyMMdd").format((Date)o1[5])).compareTo((String)o2[4] + "_" + (String)o2[0] + "_" + new SimpleDateFormat("yyyyMMdd")
/* 310:344 */           .format((Date)o2[5]));
/* 311:    */       }
/* 312:348 */     });
/* 313:349 */     return datosReporte;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<Object[]> getAnticiposSaldoIncial(Date fechaDesde, int idCliente, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/* 317:    */     throws ExcepcionAS2
/* 318:    */   {
/* 319:367 */     StringBuilder sql = new StringBuilder();
/* 320:368 */     sql.append("SELECT SUM(ac.valor), e.nombreComercial, e.nombreFiscal");
/* 321:369 */     sql.append(" FROM AnticipoCliente ac ");
/* 322:370 */     if (indicadorIncluirNotasCreditos) {
/* 323:371 */       sql.append(" LEFT JOIN ac.notaCreditoCliente nc ");
/* 324:    */     }
/* 325:373 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 326:374 */     if (indicadorIncluirNotasCreditos) {
/* 327:375 */       sql.append(" LEFT JOIN nc.facturaClientePadre fc ");
/* 328:    */     }
/* 329:377 */     sql.append(" WHERE ac.fecha < :fechaDesde");
/* 330:378 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 331:379 */     sql.append(" AND (e.idEmpresa =:idCliente OR 0=:idCliente) ");
/* 332:380 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 333:381 */     if (!indicadorIncluirNotasCreditos) {
/* 334:382 */       sql.append(" AND ac.notaCreditoCliente is null ");
/* 335:    */     }
/* 336:385 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 337:386 */       sql.append(" AND ac.sucursal.idSucursal = :idSucursal");
/* 338:    */     }
/* 339:388 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal");
/* 340:389 */     Query query = this.em.createQuery(sql.toString());
/* 341:390 */     query.setParameter("fechaDesde", fechaDesde);
/* 342:391 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 343:392 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 344:393 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 345:395 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 346:396 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 347:    */     }
/* 348:399 */     return query.getResultList();
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<Object[]> getLiquidacionesSaldoIncial(Date fechaDesde, int idCliente, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/* 352:    */     throws ExcepcionAS2
/* 353:    */   {
/* 354:418 */     StringBuilder sql = new StringBuilder();
/* 355:419 */     sql.append("SELECT SUM(dlac.valor), e.nombreComercial, e.nombreFiscal");
/* 356:420 */     sql.append(" FROM DetalleLiquidacionAnticipoCliente dlac ");
/* 357:421 */     sql.append(" LEFT JOIN  dlac.liquidacionAnticipoCliente lac ");
/* 358:422 */     sql.append(" LEFT JOIN lac.anticipoCliente ac ");
/* 359:423 */     if (indicadorIncluirNotasCreditos) {
/* 360:424 */       sql.append(" LEFT JOIN ac.notaCreditoCliente nc ");
/* 361:    */     }
/* 362:426 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 363:427 */     sql.append(" WHERE lac.fecha < :fechaDesde");
/* 364:428 */     sql.append(" AND lac.estado <> :estadoLiquidacionAnticipo ");
/* 365:429 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 366:430 */     sql.append(" AND (e.idEmpresa =:idCliente OR 0=:idCliente) ");
/* 367:431 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 368:432 */     if (!indicadorIncluirNotasCreditos) {
/* 369:433 */       sql.append(" AND ac.notaCreditoCliente is null ");
/* 370:    */     }
/* 371:436 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 372:437 */       sql.append(" AND dlac.idSucursal = :idSucursal");
/* 373:    */     }
/* 374:439 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal");
/* 375:440 */     Query query = this.em.createQuery(sql.toString());
/* 376:441 */     query.setParameter("fechaDesde", fechaDesde);
/* 377:442 */     query.setParameter("estadoLiquidacionAnticipo", Estado.ANULADO);
/* 378:443 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 379:444 */     query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 380:445 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 381:447 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 382:448 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 383:    */     }
/* 384:451 */     return query.getResultList();
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<Object[]> getDevolucionesSaldoIncial(Date fechaDesde, int idCliente, int idOrganizacion, Sucursal sucursal, boolean indicadorIncluirNotasCreditos)
/* 388:    */     throws ExcepcionAS2
/* 389:    */   {
/* 390:470 */     StringBuilder sql = new StringBuilder();
/* 391:471 */     sql.append("SELECT SUM(ac.valorDevolucion), e.nombreComercial, e.nombreFiscal");
/* 392:472 */     sql.append(" FROM AnticipoCliente ac ");
/* 393:473 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 394:474 */     sql.append(" WHERE ac.idOrganizacion = :idOrganizacion ");
/* 395:475 */     sql.append(" AND ac.fechaDevolucion < :fechaDesde ");
/* 396:476 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 397:477 */     if (idCliente != 0) {
/* 398:478 */       sql.append(" AND e.idEmpresa =:idCliente");
/* 399:    */     }
/* 400:480 */     sql.append(" AND ac.fechaDevolucion IS NOT NULL ");
/* 401:482 */     if (!indicadorIncluirNotasCreditos) {
/* 402:483 */       sql.append(" AND ac.notaCreditoCliente IS NULL ");
/* 403:    */     }
/* 404:486 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 405:487 */       sql.append(" AND ac.sucursal.idSucursal = :idSucursal");
/* 406:    */     }
/* 407:489 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal");
/* 408:490 */     Query query = this.em.createQuery(sql.toString());
/* 409:491 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 410:492 */     query.setParameter("fechaDesde", fechaDesde);
/* 411:493 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 412:494 */     if (idCliente != 0) {
/* 413:495 */       query.setParameter("idCliente", Integer.valueOf(idCliente));
/* 414:    */     }
/* 415:498 */     if ((null != sucursal) && (sucursal.getIdSucursal() != 0)) {
/* 416:499 */       query.setParameter("idSucursal", Integer.valueOf(sucursal.getIdSucursal()));
/* 417:    */     }
/* 418:502 */     return query.getResultList();
/* 419:    */   }
/* 420:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.cobros.ReporteAnticipoClienteDao
 * JD-Core Version:    0.7.0.1
 */