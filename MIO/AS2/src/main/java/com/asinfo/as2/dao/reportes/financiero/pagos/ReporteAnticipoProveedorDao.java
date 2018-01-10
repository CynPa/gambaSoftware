/*   1:    */ package com.asinfo.as2.dao.reportes.financiero.pagos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   5:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.enumeraciones.CategoriaEmpresaEnum;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
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
/*  24:    */ public class ReporteAnticipoProveedorDao
/*  25:    */   extends AbstractDaoAS2<AnticipoProveedor>
/*  26:    */ {
/*  27:    */   public ReporteAnticipoProveedorDao()
/*  28:    */   {
/*  29: 38 */     super(AnticipoProveedor.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List getReporteAnticipo(int idAnticipo)
/*  33:    */   {
/*  34: 43 */     String sql = " SELECT e.nombreFiscal, e.identificacion,  ap.fecha, ap.numero, ap.valor, ap.documentoReferencia , ap.beneficiario, ap.descripcion,  fp.nombre, cb.numero, b.nombre  FROM AnticipoProveedor ap  INNER JOIN ap.empresa e  INNER JOIN ap.formaPago fp  INNER JOIN ap.cuentaBancariaOrganizacion cbo  INNER JOIN cbo.cuentaBancaria cb  INNER JOIN cb.banco b  WHERE ap.idAnticipoProveedor = :idAnticipo ";
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40: 49 */     Query query = this.em.createQuery(sql).setParameter("idAnticipo", Integer.valueOf(idAnticipo));
/*  41: 50 */     return query.getResultList();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List getAnticipos(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/*  45:    */   {
/*  46: 58 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/*  47:    */     
/*  48: 60 */     StringBuilder sql = new StringBuilder();
/*  49: 61 */     sql.append(" SELECT ac.numero, ac.valor, ac.valor*0, e.nombreComercial, e.nombreFiscal,  ac.fecha, fc.numero, ac.saldo*0, e.identificacion, nc.numero, ac.fecha, ac.valor*0, ");
/*  50: 62 */     sql.append(" s.nombre, s.codigo, catem.idCategoriaEmpresa, catem.nombre, ac.descripcion");
/*  51: 63 */     sql.append(" FROM AnticipoProveedor ac, Sucursal s");
/*  52: 64 */     sql.append(" LEFT JOIN ac.notaCreditoProveedor nc ");
/*  53: 65 */     sql.append(" LEFT JOIN ac.empresa e ");
/*  54: 66 */     sql.append(" LEFT JOIN e.categoriaEmpresa catem ");
/*  55: 67 */     sql.append(" LEFT JOIN nc.facturaProveedorPadre fc ");
/*  56: 68 */     sql.append(" WHERE ac.sucursal.idSucursal = s.idSucursal AND ac.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  57: 69 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/*  58: 70 */     sql.append(" AND (e.idEmpresa =:idProveedor OR 0=:idProveedor) ");
/*  59: 71 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/*  60: 72 */     sql.append(" AND (ac.sucursal.idSucursal =:idSucursal OR 0=:idSucursal) ");
/*  61: 73 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  62: 74 */       sql.append(" AND catem = :categoriaEmpresa ");
/*  63:    */     }
/*  64: 76 */     if ((categoriaEmpresa != null) && (agrupadoCategoriaEmpresa)) {
/*  65: 77 */       sql.append(" ORDER BY catem.nombre ");
/*  66:    */     } else {
/*  67: 79 */       sql.append(" ORDER BY e.nombreComercial ");
/*  68:    */     }
/*  69: 82 */     Query query = this.em.createQuery(sql.toString());
/*  70: 83 */     query.setParameter("fechaDesde", fechaDesde);
/*  71: 84 */     query.setParameter("fechaHasta", fechaHasta);
/*  72: 85 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/*  73: 86 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/*  74: 87 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  75: 88 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/*  76: 89 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/*  77: 90 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/*  78:    */     }
/*  79: 93 */     return query.getResultList();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List getLiquidaciones(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/*  83:    */   {
/*  84:108 */     StringBuilder sql = new StringBuilder();
/*  85:109 */     sql.append(" SELECT ac.numero, ac.valor*0, dlac.valor, e.nombreComercial, e.nombreFiscal,  lac.fecha, fc.numero, ac.saldo*0, ");
/*  86:110 */     sql.append(" e.identificacion, nc.numero, ac.fecha, ac.valor*0, ");
/*  87:111 */     sql.append(" s.nombre, s.codigo, catem.idCategoriaEmpresa, catem.nombre, ac.descripcion");
/*  88:112 */     sql.append(" FROM DetalleLiquidacionAnticipoProveedor dlac ");
/*  89:113 */     sql.append(" LEFT JOIN  dlac.liquidacionAnticipoProveedor lac ");
/*  90:114 */     sql.append(" LEFT JOIN lac.anticipoProveedor ac, Sucursal s ");
/*  91:115 */     sql.append(" LEFT JOIN ac.notaCreditoProveedor nc ");
/*  92:116 */     sql.append(" LEFT JOIN ac.empresa e ");
/*  93:117 */     sql.append(" LEFT JOIN e.categoriaEmpresa catem ");
/*  94:118 */     sql.append(" LEFT JOIN dlac.cuentaPorPagar cxc ");
/*  95:119 */     sql.append(" LEFT JOIN cxc.facturaProveedor fc ");
/*  96:120 */     sql.append(" WHERE ac.sucursal.idSucursal = s.idSucursal AND lac.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  97:121 */     sql.append(" AND lac.estado <> :estadoLiquidacionAnticipo ");
/*  98:122 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/*  99:123 */     sql.append(" AND (e.idEmpresa =:idProveedor OR 0=:idProveedor) ");
/* 100:124 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 101:125 */     sql.append(" AND (ac.sucursal.idSucursal =:idSucursal OR 0=:idSucursal) ");
/* 102:126 */     if (categoriaEmpresa != null) {
/* 103:127 */       sql.append(" AND catem = :categoriaEmpresa ");
/* 104:    */     }
/* 105:129 */     sql.append(" ORDER BY catem.nombre ");
/* 106:    */     
/* 107:131 */     Query query = this.em.createQuery(sql.toString());
/* 108:132 */     query.setParameter("fechaDesde", fechaDesde);
/* 109:133 */     query.setParameter("fechaHasta", fechaHasta);
/* 110:134 */     query.setParameter("estadoLiquidacionAnticipo", Estado.ANULADO);
/* 111:135 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 112:136 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/* 113:137 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 114:138 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 115:139 */     if (categoriaEmpresa != null) {
/* 116:140 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 117:    */     }
/* 118:143 */     return query.getResultList();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List getDevoluciones(Date fechaDesde, Date fechaHasta, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/* 122:    */   {
/* 123:150 */     StringBuilder sql = new StringBuilder();
/* 124:151 */     sql.append(" SELECT ac.numero, ac.valor*0, ac.valor*0 , e.nombreComercial, e.nombreFiscal,  ac.fechaDevolucion, ");
/* 125:152 */     sql.append(" CONCAT('DEV# ',ac.numero), ac.saldo*0, e.identificacion, CONCAT('DEV# ',ac.numero), ac.fecha, ac.valorDevolucion, ");
/* 126:153 */     sql.append(" s.nombre, s.codigo, catem.idCategoriaEmpresa, catem.nombre, ac.descripcion");
/* 127:154 */     sql.append(" FROM AnticipoProveedor ac, Sucursal s ");
/* 128:155 */     sql.append(" LEFT JOIN ac.notaCreditoProveedor nc ");
/* 129:156 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 130:157 */     sql.append(" LEFT JOIN \te.categoriaEmpresa catem ");
/* 131:158 */     sql.append(" LEFT JOIN nc.facturaProveedorPadre fc ");
/* 132:159 */     sql.append(" WHERE ac.sucursal.idSucursal = s.idSucursal AND ac.fechaDevolucion BETWEEN :fechaDesde AND :fechaHasta ");
/* 133:160 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 134:161 */     sql.append(" AND (e.idEmpresa =:idProveedor OR 0=:idProveedor) ");
/* 135:162 */     sql.append(" AND ac.fechaDevolucion IS NOT NULL ");
/* 136:163 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 137:164 */     sql.append(" AND (ac.sucursal.idSucursal = :idSucursal OR 0=:idSucursal) ");
/* 138:165 */     if (categoriaEmpresa != null) {
/* 139:166 */       sql.append(" AND catem = :categoriaEmpresa ");
/* 140:    */     }
/* 141:168 */     sql.append(" ORDER BY catem.nombre ");
/* 142:    */     
/* 143:170 */     Query query = this.em.createQuery(sql.toString());
/* 144:171 */     query.setParameter("fechaDesde", fechaDesde);
/* 145:172 */     query.setParameter("fechaHasta", fechaHasta);
/* 146:173 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 147:174 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/* 148:175 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 149:176 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 150:177 */     if (categoriaEmpresa != null) {
/* 151:178 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 152:    */     }
/* 153:181 */     return query.getResultList();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List getAnticipoProveedor(Date fechaDesde, Date fechaHasta, int idProveedor, boolean indicadorSaldoDiferenciaCero, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/* 157:    */   {
/* 158:190 */     List<Object[]> anticiposSaldoIncial = getAnticiposSaldoIncial(fechaDesde, idProveedor, idOrganizacion, categoriaEmpresa, idSucursal);
/* 159:191 */     List<Object[]> liquidacionesSaldoInicial = getLiquidacionesSaldoIncial(fechaDesde, idProveedor, idOrganizacion, categoriaEmpresa, idSucursal);
/* 160:192 */     List<Object[]> devolucionesSaldoInicial = getDevolucionesSaldoIncial(fechaDesde, idProveedor, idOrganizacion, categoriaEmpresa, idSucursal);
/* 161:    */     
/* 162:194 */     Map<String, Object[]> hmAnticipos = new HashMap();
/* 163:196 */     for (Object[] o : anticiposSaldoIncial) {
/* 164:198 */       hmAnticipos.put(o[1].toString(), o);
/* 165:    */     }
/* 166:200 */     for (Object[] o : liquidacionesSaldoInicial) {
/* 167:201 */       if (hmAnticipos.get(o[1].toString()) != null)
/* 168:    */       {
/* 169:202 */         Object[] obj = (Object[])hmAnticipos.get(o[1].toString());
/* 170:203 */         obj[0] = ((BigDecimal)obj[0]).subtract((BigDecimal)o[0]);
/* 171:204 */         hmAnticipos.put(o[1].toString(), obj);
/* 172:    */       }
/* 173:    */       else
/* 174:    */       {
/* 175:206 */         BigDecimal n = BigDecimal.ZERO;
/* 176:207 */         o[0] = n.subtract((BigDecimal)o[0]);
/* 177:208 */         hmAnticipos.put(o[1].toString(), o);
/* 178:    */       }
/* 179:    */     }
/* 180:211 */     for (Object[] o : devolucionesSaldoInicial) {
/* 181:212 */       if (hmAnticipos.get(o[1].toString()) != null)
/* 182:    */       {
/* 183:213 */         Object[] obj = (Object[])hmAnticipos.get(o[1].toString());
/* 184:214 */         obj[0] = ((BigDecimal)obj[0]).subtract((BigDecimal)o[0]);
/* 185:215 */         hmAnticipos.put(o[1].toString(), obj);
/* 186:    */       }
/* 187:    */       else
/* 188:    */       {
/* 189:217 */         BigDecimal n = BigDecimal.ZERO;
/* 190:218 */         o[0] = n.subtract((BigDecimal)o[0]);
/* 191:219 */         hmAnticipos.put(o[1].toString(), o);
/* 192:    */       }
/* 193:    */     }
/* 194:223 */     Object anticipos = getAnticipos(fechaDesde, fechaHasta, idProveedor, idOrganizacion, categoriaEmpresa, idSucursal);
/* 195:224 */     List<Object[]> liquidaciones = getLiquidaciones(fechaDesde, fechaHasta, idProveedor, idOrganizacion, categoriaEmpresa, idSucursal);
/* 196:225 */     List<Object[]> devoluciones = getDevoluciones(fechaDesde, fechaHasta, idProveedor, idOrganizacion, categoriaEmpresa, idSucursal);
/* 197:    */     
/* 198:227 */     Map<String, Object[]> hmReporte = new HashMap();
/* 199:228 */     List<Object[]> list = new ArrayList();
/* 200:230 */     for (Object[] datos : hmAnticipos.values()) {
/* 201:231 */       if ((datos != null) && (((BigDecimal)datos[0]).compareTo(BigDecimal.ZERO) != 0))
/* 202:    */       {
/* 203:232 */         Object[] saldoInicial = new Object[17];
/* 204:    */         
/* 205:234 */         saldoInicial[0] = " Saldo Inicial";
/* 206:235 */         saldoInicial[1] = datos[0];
/* 207:236 */         saldoInicial[2] = BigDecimal.ZERO;
/* 208:237 */         saldoInicial[11] = BigDecimal.ZERO;
/* 209:238 */         saldoInicial[3] = datos[1];
/* 210:239 */         saldoInicial[4] = datos[2];
/* 211:240 */         saldoInicial[5] = FuncionesUtiles.getFecha(1, 1, 1950);
/* 212:241 */         saldoInicial[10] = FuncionesUtiles.getFecha(1, 1, 1950);
/* 213:242 */         saldoInicial[15] = "";
/* 214:    */         
/* 215:244 */         hmReporte.put("Saldo Inicial~" + datos[1], saldoInicial);
/* 216:    */       }
/* 217:    */     }
/* 218:248 */     for (Object[] datos : (List)anticipos) {
/* 219:249 */       hmReporte.put(datos[0].toString(), datos);
/* 220:    */     }
/* 221:257 */     for (Object[] datos : liquidaciones)
/* 222:    */     {
/* 223:258 */       Object[] detalleReporte = (Object[])hmReporte.get(datos[0].toString());
/* 224:259 */       if ((detalleReporte != null) && (((BigDecimal)detalleReporte[2]).compareTo(BigDecimal.ZERO) == 0))
/* 225:    */       {
/* 226:260 */         detalleReporte[2] = datos[2];
/* 227:261 */         detalleReporte[5] = datos[5];
/* 228:262 */         detalleReporte[6] = datos[6];
/* 229:263 */         detalleReporte[9] = datos[9];
/* 230:    */       }
/* 231:    */       else
/* 232:    */       {
/* 233:265 */         list.add(datos);
/* 234:    */       }
/* 235:    */     }
/* 236:274 */     for (Object[] datos : devoluciones)
/* 237:    */     {
/* 238:275 */       Object[] detalleReporte = (Object[])hmReporte.get(datos[0].toString());
/* 239:276 */       if ((detalleReporte != null) && (((BigDecimal)detalleReporte[2]).compareTo(BigDecimal.ZERO) == 0))
/* 240:    */       {
/* 241:277 */         detalleReporte[2] = datos[2];
/* 242:278 */         detalleReporte[5] = datos[5];
/* 243:279 */         detalleReporte[6] = datos[6];
/* 244:280 */         detalleReporte[11] = datos[11];
/* 245:    */       }
/* 246:    */       else
/* 247:    */       {
/* 248:282 */         list.add(datos);
/* 249:    */       }
/* 250:    */     }
/* 251:287 */     Object datosReporte = new ArrayList(hmReporte.values());
/* 252:288 */     ((List)datosReporte).addAll(list);
/* 253:    */     Map<String, BigDecimal> hmSaldos;
/* 254:    */     Map<String, List<Object[]>> hmListaAnticipos;
/* 255:291 */     if (indicadorSaldoDiferenciaCero)
/* 256:    */     {
/* 257:293 */       hmSaldos = new HashMap();
/* 258:294 */       hmListaAnticipos = new HashMap();
/* 259:296 */       for (Object[] datos : (List)datosReporte)
/* 260:    */       {
/* 261:298 */         BigDecimal valorAnticipo = (BigDecimal)datos[1];
/* 262:299 */         BigDecimal valorLiquidacion = (BigDecimal)datos[2];
/* 263:300 */         BigDecimal valorDevolucion = (BigDecimal)datos[11];
/* 264:    */         
/* 265:302 */         BigDecimal saldoAnticipo = (BigDecimal)hmSaldos.get((String)datos[0]);
/* 266:303 */         List<Object[]> detalleReporte = (List)hmListaAnticipos.get((String)datos[0]);
/* 267:304 */         if (saldoAnticipo == null)
/* 268:    */         {
/* 269:305 */           saldoAnticipo = valorAnticipo.subtract(valorLiquidacion).subtract(valorDevolucion);
/* 270:306 */           hmSaldos.put((String)datos[0], saldoAnticipo);
/* 271:    */           
/* 272:308 */           detalleReporte = new ArrayList();
/* 273:309 */           detalleReporte.add(datos);
/* 274:310 */           hmListaAnticipos.put((String)datos[0], detalleReporte);
/* 275:    */         }
/* 276:    */         else
/* 277:    */         {
/* 278:313 */           saldoAnticipo = saldoAnticipo.add(valorAnticipo).subtract(valorLiquidacion).subtract(valorDevolucion);
/* 279:314 */           hmSaldos.put((String)datos[0], saldoAnticipo);
/* 280:315 */           detalleReporte.add(datos);
/* 281:    */         }
/* 282:    */       }
/* 283:319 */       datosReporte = new ArrayList();
/* 284:320 */       for (String numeroAnticipo : hmSaldos.keySet()) {
/* 285:321 */         if (((BigDecimal)hmSaldos.get(numeroAnticipo)).compareTo(BigDecimal.ZERO) != 0) {
/* 286:322 */           ((List)datosReporte).addAll((Collection)hmListaAnticipos.get(numeroAnticipo));
/* 287:    */         }
/* 288:    */       }
/* 289:    */     }
/* 290:327 */     Collections.sort((List)datosReporte, new Comparator()
/* 291:    */     {
/* 292:    */       public int compare(Object[] o1, Object[] o2)
/* 293:    */       {
/* 294:330 */         return ((String)o1[4] + "_" + (String)o1[0] + "_" + new SimpleDateFormat("yyyyMMdd").format((Date)o1[5])).compareTo((String)o2[4] + "_" + (String)o2[0] + "_" + new SimpleDateFormat("yyyyMMdd")
/* 295:331 */           .format((Date)o2[5]));
/* 296:    */       }
/* 297:335 */     });
/* 298:336 */     return datosReporte;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<Object[]> getAnticiposSaldoIncial(Date fechaDesde, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/* 302:    */   {
/* 303:343 */     boolean agrupadoCategoriaEmpresa = (categoriaEmpresa != null) && (categoriaEmpresa.getCodigo().equals(CategoriaEmpresaEnum.TODOS_AGRUPADO.name()));
/* 304:    */     
/* 305:345 */     StringBuilder sql = new StringBuilder();
/* 306:346 */     sql.append(" SELECT SUM(ac.valor), e.nombreComercial, e.nombreFiscal");
/* 307:347 */     sql.append(" FROM AnticipoProveedor ac, Sucursal s");
/* 308:348 */     sql.append(" LEFT JOIN ac.notaCreditoProveedor nc ");
/* 309:349 */     sql.append(" LEFT JOIN ac.empresa e ");
/* 310:350 */     sql.append(" LEFT JOIN e.categoriaEmpresa catem ");
/* 311:351 */     sql.append(" LEFT JOIN nc.facturaProveedorPadre fc ");
/* 312:352 */     sql.append(" WHERE ac.sucursal.idSucursal = s.idSucursal AND ac.fecha < :fechaDesde");
/* 313:353 */     sql.append(" AND ac.estado  <> :estadoAnticipo ");
/* 314:354 */     sql.append(" AND (e.idEmpresa =:idProveedor OR 0=:idProveedor) ");
/* 315:355 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 316:356 */     sql.append(" AND (ac.sucursal.idSucursal =:idSucursal OR 0=:idSucursal) ");
/* 317:357 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/* 318:358 */       sql.append(" AND catem = :categoriaEmpresa ");
/* 319:    */     }
/* 320:360 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal");
/* 321:361 */     Query query = this.em.createQuery(sql.toString());
/* 322:362 */     query.setParameter("fechaDesde", fechaDesde);
/* 323:363 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 324:364 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/* 325:365 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 326:366 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 327:367 */     if ((categoriaEmpresa != null) && (!agrupadoCategoriaEmpresa)) {
/* 328:368 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 329:    */     }
/* 330:371 */     return query.getResultList();
/* 331:    */   }
/* 332:    */   
/* 333:    */   public List<Object[]> getLiquidacionesSaldoIncial(Date fechaDesde, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/* 334:    */   {
/* 335:375 */     StringBuilder sql = new StringBuilder();
/* 336:376 */     sql.append("SELECT SUM(dlac.valor), e.nombreComercial, e.nombreFiscal");
/* 337:377 */     sql.append(" FROM DetalleLiquidacionAnticipoProveedor dlac");
/* 338:378 */     sql.append(" LEFT JOIN  dlac.liquidacionAnticipoProveedor lac");
/* 339:379 */     sql.append(" LEFT JOIN lac.anticipoProveedor ac, Sucursal s");
/* 340:380 */     sql.append(" LEFT JOIN ac.notaCreditoProveedor nc");
/* 341:381 */     sql.append(" LEFT JOIN ac.empresa e");
/* 342:382 */     sql.append(" LEFT JOIN e.categoriaEmpresa catem");
/* 343:383 */     sql.append(" WHERE ac.sucursal.idSucursal = s.idSucursal AND lac.fecha < :fechaDesde");
/* 344:384 */     sql.append(" AND lac.estado <> :estadoLiquidacionAnticipo");
/* 345:385 */     sql.append(" AND ac.estado  <> :estadoAnticipo");
/* 346:386 */     sql.append(" AND (e.idEmpresa =:idProveedor OR 0=:idProveedor)");
/* 347:387 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion");
/* 348:388 */     sql.append(" AND (ac.sucursal.idSucursal =:idSucursal OR 0=:idSucursal)");
/* 349:389 */     if (categoriaEmpresa != null) {
/* 350:390 */       sql.append(" AND catem = :categoriaEmpresa");
/* 351:    */     }
/* 352:392 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal");
/* 353:393 */     Query query = this.em.createQuery(sql.toString());
/* 354:394 */     query.setParameter("fechaDesde", fechaDesde);
/* 355:395 */     query.setParameter("estadoLiquidacionAnticipo", Estado.ANULADO);
/* 356:396 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 357:397 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/* 358:398 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 359:399 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 360:400 */     if (categoriaEmpresa != null) {
/* 361:401 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 362:    */     }
/* 363:404 */     return query.getResultList();
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<Object[]> getDevolucionesSaldoIncial(Date fechaDesde, int idProveedor, int idOrganizacion, CategoriaEmpresa categoriaEmpresa, int idSucursal)
/* 367:    */   {
/* 368:410 */     StringBuilder sql = new StringBuilder();
/* 369:411 */     sql.append("SELECT SUM(ac.valorDevolucion), e.nombreComercial, e.nombreFiscal");
/* 370:412 */     sql.append(" FROM AnticipoProveedor ac, Sucursal s");
/* 371:413 */     sql.append(" LEFT JOIN ac.notaCreditoProveedor nc");
/* 372:414 */     sql.append(" LEFT JOIN ac.empresa e");
/* 373:415 */     sql.append(" LEFT JOIN \te.categoriaEmpresa catem");
/* 374:416 */     sql.append(" LEFT JOIN nc.facturaProveedorPadre fc");
/* 375:417 */     sql.append(" WHERE ac.sucursal.idSucursal = s.idSucursal AND ac.fechaDevolucion < :fechaDesde ");
/* 376:418 */     sql.append(" AND ac.estado  <> :estadoAnticipo");
/* 377:419 */     sql.append(" AND (e.idEmpresa =:idProveedor OR 0=:idProveedor)");
/* 378:420 */     sql.append(" AND ac.fechaDevolucion IS NOT NULL ");
/* 379:421 */     sql.append(" AND ac.idOrganizacion = :idOrganizacion ");
/* 380:422 */     sql.append(" AND (ac.sucursal.idSucursal = :idSucursal OR 0=:idSucursal)");
/* 381:423 */     if (categoriaEmpresa != null) {
/* 382:424 */       sql.append(" AND catem = :categoriaEmpresa");
/* 383:    */     }
/* 384:426 */     sql.append(" GROUP BY e.nombreComercial, e.nombreFiscal");
/* 385:427 */     Query query = this.em.createQuery(sql.toString());
/* 386:428 */     query.setParameter("fechaDesde", fechaDesde);
/* 387:429 */     query.setParameter("estadoAnticipo", Estado.ANULADO);
/* 388:430 */     query.setParameter("idProveedor", Integer.valueOf(idProveedor));
/* 389:431 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 390:432 */     query.setParameter("idSucursal", Integer.valueOf(idSucursal));
/* 391:433 */     if (categoriaEmpresa != null) {
/* 392:434 */       query.setParameter("categoriaEmpresa", categoriaEmpresa);
/* 393:    */     }
/* 394:437 */     return query.getResultList();
/* 395:    */   }
/* 396:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.financiero.pagos.ReporteAnticipoProveedorDao
 * JD-Core Version:    0.7.0.1
 */