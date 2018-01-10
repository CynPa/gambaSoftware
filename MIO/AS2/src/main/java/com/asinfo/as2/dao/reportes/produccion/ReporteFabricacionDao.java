/*   1:    */ package com.asinfo.as2.dao.reportes.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   7:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.ValorAtributo;
/*  10:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*  11:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  12:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.io.Serializable;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Collections;
/*  21:    */ import java.util.Comparator;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.ejb.Stateless;
/*  27:    */ import javax.persistence.EntityManager;
/*  28:    */ import javax.persistence.Query;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class ReporteFabricacionDao
/*  32:    */   extends AbstractDaoAS2<OrdenSalidaMaterial>
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 2072654409753052392L;
/*  36:    */   
/*  37:    */   public ReporteFabricacionDao()
/*  38:    */   {
/*  39: 39 */     super(OrdenSalidaMaterial.class);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List getReporteSalidaMaterialvsConsumo(Date fechaDesde, Date fechaHasta, OrdenFabricacion ordenFabricacion)
/*  43:    */   {
/*  44: 45 */     StringBuilder jpql = new StringBuilder();
/*  45: 46 */     jpql.append(" SELECT ofb.numero, pr.codigo, pr.nombreComercial, osm.numero, dosm.cantidad, dmi.cantidad");
/*  46: 47 */     jpql.append(" FROM DetalleMovimientoInventario dmi");
/*  47: 48 */     jpql.append(" JOIN dmi.detalleOrdenSalidaMaterial dosm");
/*  48: 49 */     jpql.append(" JOIN dosm.ordenFabricacion ofb");
/*  49: 50 */     jpql.append(" JOIN dosm.ordenSalidaMaterial osm");
/*  50: 51 */     jpql.append(" WHERE ofb.fechaLanzamiento BETWEEN :fechaDesde AND :fechaHasta");
/*  51: 53 */     if (null != ordenFabricacion) {
/*  52: 54 */       jpql.append(" AND ofb = :ordenFabricacion");
/*  53:    */     }
/*  54: 57 */     Query query = this.em.createQuery(jpql.toString());
/*  55:    */     
/*  56: 59 */     query.setParameter("fechaDesde", fechaDesde);
/*  57: 60 */     query.setParameter("fechaHasta", fechaHasta);
/*  58: 62 */     if (null != ordenFabricacion) {
/*  59: 63 */       query.setParameter("ordenFabricacion", ordenFabricacion);
/*  60:    */     }
/*  61: 66 */     return query.getResultList();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List getReporteCostoFabricacion(Date fechaDesde, Date fechaHasta, OrdenFabricacion ordenFabricacion)
/*  65:    */   {
/*  66: 71 */     StringBuilder jpql = new StringBuilder();
/*  67: 72 */     jpql.append("SELECT ofb.numero, pr.codigo, pr.nombreComercial, ipr.cantidad, ipr.costoMateriales, ipr.costoDepreciaciones, ipr.costoManoDeObra, ipr.costoIndirectos, ipr.costo");
/*  68: 73 */     jpql.append(" FROM InventarioProducto ipr");
/*  69: 74 */     jpql.append("  JOIN ipr.producto pr");
/*  70: 75 */     jpql.append("  JOIN ipr.detalleMovimientoInventario dmi");
/*  71: 76 */     jpql.append("  JOIN dmi.movimientoInventario mi");
/*  72: 77 */     jpql.append("  JOIN mi.ordenFabricacion ofb");
/*  73: 78 */     jpql.append(" WHERE ofb.fechaLanzamiento BETWEEN :fechaDesde AND :fechaHasta");
/*  74: 80 */     if (null != ordenFabricacion) {
/*  75: 81 */       jpql.append(" AND ofb = :ordenFabricacion");
/*  76:    */     }
/*  77: 84 */     Query query = this.em.createQuery(jpql.toString());
/*  78:    */     
/*  79: 86 */     query.setParameter("fechaDesde", fechaDesde);
/*  80: 87 */     query.setParameter("fechaHasta", fechaHasta);
/*  81: 89 */     if (null != ordenFabricacion) {
/*  82: 90 */       query.setParameter("ordenFabricacion", ordenFabricacion);
/*  83:    */     }
/*  84: 93 */     return query.getResultList();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List getReporteParteProduccion(Date fechaDesde, Date fechaHasta)
/*  88:    */   {
/*  89:100 */     HashMap<String, Object[]> hmProduccion = new HashMap();
/*  90:101 */     HashMap<String, Object[]> hmEmpaque = new HashMap();
/*  91:    */     
/*  92:103 */     Date fechaLanzamiento = null;
/*  93:104 */     Date fechaCierre = null;
/*  94:105 */     BigDecimal tiempoHoras = null;
/*  95:    */     
/*  96:107 */     Object[] arregloEmpaque = new Object[13];
/*  97:    */     
/*  98:109 */     StringBuilder jpql = new StringBuilder();
/*  99:    */     
/* 100:111 */     jpql.append(" SELECT ofb.idOrdenFabricacion, ofb.numero, ofb.producto.nombre, ofb.fechaLanzamiento, ofb.fechaCierre, ofb.cantidad ");
/* 101:112 */     jpql.append(" FROM OrdenFabricacion ofb ");
/* 102:113 */     jpql.append(" WHERE (ofb.fechaLanzamiento  >= :fechaDesde AND ofb.fechaLanzamiento <= :fechaHasta) ");
/* 103:114 */     jpql.append(" AND ofb.estado != :estadoAnulado ");
/* 104:115 */     jpql.append(" ORDER BY ofb.idOrdenFabricacion");
/* 105:    */     
/* 106:117 */     Query query = this.em.createQuery(jpql.toString());
/* 107:    */     
/* 108:119 */     query.setParameter("fechaDesde", fechaDesde);
/* 109:120 */     query.setParameter("fechaHasta", fechaHasta);
/* 110:121 */     query.setParameter("estadoAnulado", EstadoProduccionEnum.ANULADO);
/* 111:122 */     List<Object[]> resultPlanificado = query.getResultList();
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:126 */     BigDecimal cantidadProducida = BigDecimal.ZERO;
/* 116:127 */     Date fechaProduccion = null;
/* 117:    */     
/* 118:129 */     StringBuilder jpqll = new StringBuilder();
/* 119:130 */     jpqll.append(" SELECT ofb.idOrdenFabricacion, ofb.numero, SUM(dmi.cantidad), MAX(mi.fecha) ");
/* 120:131 */     jpqll.append(" FROM DetalleMovimientoInventario dmi ");
/* 121:132 */     jpqll.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 122:133 */     jpqll.append(" INNER JOIN mi.ordenFabricacion ofb ");
/* 123:134 */     jpqll.append(" WHERE (mi.documento.documentoBase = :documentoBaseINGPROD) ");
/* 124:135 */     jpqll.append(" GROUP BY ofb.idOrdenFabricacion, ofb.numero");
/* 125:136 */     jpqll.append(" ORDER BY ofb.idOrdenFabricacion");
/* 126:    */     
/* 127:138 */     Query queryProduccion = this.em.createQuery(jpqll.toString());
/* 128:    */     
/* 129:140 */     queryProduccion.setParameter("documentoBaseINGPROD", DocumentoBase.INGRESO_PRODUCCION);
/* 130:    */     
/* 131:142 */     List<Object[]> resultProduccion = queryProduccion.getResultList();
/* 132:144 */     for (Object[] objects2 : resultProduccion)
/* 133:    */     {
/* 134:145 */       Integer idOrdenFabricacion = (Integer)objects2[0];
/* 135:146 */       String numeroOrdenFabricacion = (String)objects2[1];
/* 136:147 */       hmProduccion.put(idOrdenFabricacion + "~" + numeroOrdenFabricacion, objects2);
/* 137:    */     }
/* 138:151 */     Object listaEmpaque = new ArrayList();
/* 139:    */     
/* 140:    */ 
/* 141:    */ 
/* 142:155 */     StringBuilder jpqlll = new StringBuilder();
/* 143:156 */     jpqlll.append(" SELECT dmi.producto.marcaProducto.nombre, dmi.producto.presentacionProducto.nombre, SUM(dmi.cantidad), mi.fecha, ofb.idOrdenFabricacion, ofb.numero ");
/* 144:    */     
/* 145:158 */     jpqlll.append(" FROM DetalleMovimientoInventario dmi ");
/* 146:159 */     jpqlll.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 147:160 */     jpqlll.append(" INNER JOIN mi.ordenFabricacion ofb ");
/* 148:161 */     jpqlll.append(" WHERE (mi.documento.documentoBase = :documentoBaseTRANSPRO) ");
/* 149:162 */     jpqlll.append(" GROUP BY dmi.producto.marcaProducto.nombre, dmi.producto.presentacionProducto.nombre, mi.fecha, ofb.idOrdenFabricacion, ofb.numero");
/* 150:163 */     jpqlll.append(" ORDER BY ofb.idOrdenFabricacion");
/* 151:    */     
/* 152:165 */     Query queryEmpacado = this.em.createQuery(jpqlll.toString());
/* 153:    */     
/* 154:167 */     queryEmpacado.setParameter("documentoBaseTRANSPRO", DocumentoBase.TRANSFORMACION_PRODUCTO);
/* 155:    */     
/* 156:169 */     List<Object[]> resultEmpacado = queryEmpacado.getResultList();
/* 157:171 */     for (Object[] objects3 : resultEmpacado)
/* 158:    */     {
/* 159:172 */       Integer idOrdenFabricacion = (Integer)objects3[4];
/* 160:173 */       String numeroOrdenFabricacion = (String)objects3[5];
/* 161:174 */       hmEmpaque.put(idOrdenFabricacion + "~" + numeroOrdenFabricacion, objects3);
/* 162:    */     }
/* 163:177 */     for (Object[] objects : resultPlanificado)
/* 164:    */     {
/* 165:179 */       fechaLanzamiento = (Date)objects[3];
/* 166:180 */       fechaCierre = (Date)objects[4];
/* 167:181 */       tiempoHoras = BigDecimal.ZERO;
/* 168:182 */       if ((fechaLanzamiento != null) && (fechaCierre != null)) {
/* 169:183 */         tiempoHoras = FuncionesUtiles.diferenciasDeHoras(fechaLanzamiento, fechaCierre);
/* 170:    */       }
/* 171:186 */       if (!hmProduccion.isEmpty())
/* 172:    */       {
/* 173:187 */         Object[] producido = (Object[])hmProduccion.get(objects[0] + "~" + objects[1]);
/* 174:188 */         if (producido != null)
/* 175:    */         {
/* 176:189 */           cantidadProducida = (BigDecimal)producido[2];
/* 177:190 */           fechaProduccion = (Date)producido[3];
/* 178:    */         }
/* 179:    */         else
/* 180:    */         {
/* 181:192 */           cantidadProducida = null;
/* 182:193 */           fechaProduccion = null;
/* 183:    */         }
/* 184:    */       }
/* 185:197 */       arregloEmpaque = new Object[13];
/* 186:198 */       arregloEmpaque[0] = objects[0];
/* 187:199 */       arregloEmpaque[1] = objects[1];
/* 188:200 */       arregloEmpaque[2] = objects[2];
/* 189:201 */       arregloEmpaque[3] = objects[3];
/* 190:202 */       arregloEmpaque[4] = objects[4];
/* 191:203 */       arregloEmpaque[5] = objects[5];
/* 192:205 */       if (!hmEmpaque.isEmpty())
/* 193:    */       {
/* 194:206 */         Object[] empaque = (Object[])hmEmpaque.get(objects[0] + "~" + objects[1]);
/* 195:207 */         if (empaque != null)
/* 196:    */         {
/* 197:208 */           arregloEmpaque[8] = ((String)empaque[0]);
/* 198:209 */           arregloEmpaque[9] = ((String)empaque[1]);
/* 199:210 */           arregloEmpaque[10] = ((BigDecimal)empaque[2]);
/* 200:211 */           arregloEmpaque[11] = ((Date)empaque[3]);
/* 201:    */         }
/* 202:    */       }
/* 203:214 */       arregloEmpaque[6] = (cantidadProducida == null ? BigDecimal.ZERO : cantidadProducida);
/* 204:215 */       arregloEmpaque[7] = (fechaProduccion == null ? null : fechaProduccion);
/* 205:216 */       arregloEmpaque[12] = tiempoHoras;
/* 206:    */       
/* 207:218 */       ((List)listaEmpaque).add(arregloEmpaque);
/* 208:    */     }
/* 209:220 */     return listaEmpaque;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Object[]> getReporteProduccion(Date fechaDesde, Date fechaHasta, Bodega bodega, CategoriaProducto categoriaProducto, SubcategoriaProducto subCategoriaProducto, Maquina maquina, PersonaResponsable personaResponsable, List<ValorAtributo> listValoresAtributos, boolean agrupado, int tipoReporte, int numeroAtributosOrganizacion)
/* 213:    */   {
/* 214:234 */     StringBuilder jpql = new StringBuilder();
/* 215:235 */     jpql = new StringBuilder();
/* 216:236 */     jpql.append("SELECT pro.nombre, uc.nombre,");
/* 217:237 */     if (tipoReporte == 0)
/* 218:    */     {
/* 219:238 */       jpql.append(" pre.nombre, SUM(dmi.cantidad), SUM(ip.costoMateriales),");
/* 220:239 */       jpql.append(" SUM(ip.costoIndirectos), SUM(ip.costoDepreciaciones), SUM(ip.costoManoDeObra) ");
/* 221:240 */       jpql.append(" ,pro.codigo ");
/* 222:    */     }
/* 223:242 */     else if (agrupado)
/* 224:    */     {
/* 225:243 */       if (tipoReporte == 1) {
/* 226:244 */         jpql.append(" SUM(dmi.cantidad), COALESCE(va1.nombre, ''), COUNT(pro.nombre)");
/* 227:245 */       } else if (tipoReporte == 2) {
/* 228:246 */         jpql.append(" SUM(dmi.cantidad), COALESCE(m.nombre, ''), COUNT(pro.nombre)");
/* 229:247 */       } else if (tipoReporte == 3) {
/* 230:248 */         jpql.append(" SUM(dmi.cantidad), CONCAT(pr.apellidos, ' ', pr.nombres), COUNT(pro.nombre)");
/* 231:    */       }
/* 232:    */     }
/* 233:    */     else
/* 234:    */     {
/* 235:251 */       if (tipoReporte == 1) {
/* 236:252 */         jpql.append(" CONCAT(a1.nombre, ': ', va1.nombre),");
/* 237:253 */       } else if (tipoReporte == 2) {
/* 238:254 */         jpql.append(" CONCAT('Maquina: ', m.nombre),");
/* 239:255 */       } else if (tipoReporte == 3) {
/* 240:256 */         jpql.append(" CONCAT('Responsable: ', pr.apellidos, ' ', pr.nombres),");
/* 241:    */       }
/* 242:258 */       jpql.append(" dmi.cantidad, mi.fecha, mi.numero, ofb.numero,");
/* 243:259 */       for (int i = 2; i <= numeroAtributosOrganizacion; i++) {
/* 244:260 */         jpql.append(" a" + i + ".nombre, va" + i + ".nombre,");
/* 245:    */       }
/* 246:262 */       if (tipoReporte == 1) {
/* 247:263 */         jpql.append(" '', '', 1");
/* 248:    */       } else {
/* 249:265 */         jpql.append(" a1.nombre, va1.nombre, 1");
/* 250:    */       }
/* 251:    */     }
/* 252:269 */     jpql.append(" FROM DetalleMovimientoInventario dmi ");
/* 253:270 */     jpql.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 254:271 */     jpql.append(" INNER JOIN dmi.unidadConversion uc");
/* 255:272 */     jpql.append(" INNER JOIN dmi.inventarioProducto ip ");
/* 256:273 */     jpql.append(" INNER JOIN dmi.producto pro ");
/* 257:274 */     jpql.append(" INNER JOIN pro.subcategoriaProducto scp ");
/* 258:275 */     jpql.append(" INNER JOIN pro.presentacionProducto pre");
/* 259:276 */     jpql.append(" INNER JOIN scp.categoriaProducto cp ");
/* 260:277 */     jpql.append(" INNER JOIN mi.ordenFabricacion ofb ");
/* 261:278 */     jpql.append(" INNER JOIN mi.documento d");
/* 262:279 */     if (tipoReporte != 0)
/* 263:    */     {
/* 264:280 */       jpql.append(" LEFT JOIN mi.responsableSalidaMercaderia pr");
/* 265:281 */       jpql.append(" LEFT JOIN dmi.maquina m");
/* 266:282 */       jpql.append(" LEFT JOIN dmi.lote l");
/* 267:283 */       for (int i = 1; i <= numeroAtributosOrganizacion; i++)
/* 268:    */       {
/* 269:284 */         jpql.append(" LEFT JOIN l.atributo" + i + " a" + i);
/* 270:285 */         jpql.append(" LEFT JOIN l.valorAtributo" + i + " va" + i);
/* 271:    */       }
/* 272:    */     }
/* 273:288 */     jpql.append(" WHERE ofb.fechaLanzamiento BETWEEN :fechaDesde AND :fechaHasta");
/* 274:289 */     jpql.append(" AND (d.documentoBase = :documentoBaseINGPROD OR d.documentoBase = :documentoBaseTRANSPRO) ");
/* 275:290 */     if (bodega != null) {
/* 276:291 */       jpql.append(" AND dmi.bodegaDestino = :bodega ");
/* 277:    */     }
/* 278:293 */     if (categoriaProducto != null) {
/* 279:294 */       jpql.append(" AND cp = :categoriaProducto ");
/* 280:    */     }
/* 281:296 */     if (subCategoriaProducto != null) {
/* 282:297 */       jpql.append(" AND scp = :subCategoriaProducto ");
/* 283:    */     }
/* 284:299 */     if (personaResponsable != null) {
/* 285:300 */       jpql.append(" AND pr = :personaResponsable");
/* 286:    */     }
/* 287:302 */     if (maquina != null) {
/* 288:303 */       jpql.append(" AND m = :maquina");
/* 289:    */     }
/* 290:305 */     if ((listValoresAtributos != null) && (listValoresAtributos.size() > 0))
/* 291:    */     {
/* 292:306 */       for (int i = 1; i <= numeroAtributosOrganizacion; i++) {
/* 293:307 */         jpql.append(" OR va" + i + " IN (:listValoresAtributos)");
/* 294:    */       }
/* 295:309 */       jpql.append(")");
/* 296:    */     }
/* 297:311 */     if (tipoReporte == 0)
/* 298:    */     {
/* 299:312 */       jpql.append(" GROUP BY pro.nombre,pro.codigo, pre.nombre, uc.nombre ");
/* 300:    */     }
/* 301:313 */     else if (agrupado)
/* 302:    */     {
/* 303:314 */       jpql.append(" GROUP BY pro.nombre, uc.nombre,");
/* 304:315 */       if (tipoReporte == 1) {
/* 305:316 */         jpql.append(" a1.nombre, va1.nombre");
/* 306:317 */       } else if (tipoReporte == 2) {
/* 307:318 */         jpql.append(" m.nombre");
/* 308:319 */       } else if (tipoReporte == 3) {
/* 309:320 */         jpql.append(" CONCAT(pr.apellidos, ' ', pr.nombres)");
/* 310:    */       }
/* 311:    */     }
/* 312:323 */     if (tipoReporte > 1)
/* 313:    */     {
/* 314:324 */       jpql.append(" ORDER BY");
/* 315:325 */       if (tipoReporte == 1) {
/* 316:326 */         jpql.append(" a1.nombre, va1.nombre");
/* 317:327 */       } else if (tipoReporte == 2) {
/* 318:328 */         jpql.append(" m.nombre");
/* 319:329 */       } else if (tipoReporte == 3) {
/* 320:330 */         jpql.append(" CONCAT(pr.apellidos, ' ', pr.nombres)");
/* 321:    */       }
/* 322:332 */       if (!agrupado)
/* 323:    */       {
/* 324:333 */         jpql.append(" ,mi.fecha, mi.numero, ofb.numero,pro.nombre,");
/* 325:334 */         if (tipoReporte != 1) {
/* 326:335 */           jpql.append(" a1.nombre, va1.nombre,");
/* 327:    */         }
/* 328:337 */         for (int i = 2; i <= numeroAtributosOrganizacion; i++)
/* 329:    */         {
/* 330:338 */           jpql.append(" a" + i + ".nombre,");
/* 331:339 */           jpql.append(" va" + i + ".nombre,");
/* 332:    */         }
/* 333:341 */         jpql = new StringBuilder(jpql.toString().substring(0, jpql.toString().length() - 1));
/* 334:    */       }
/* 335:    */     }
/* 336:    */     else
/* 337:    */     {
/* 338:344 */       jpql.append(" ORDER BY");
/* 339:345 */       if (tipoReporte == 1) {
/* 340:346 */         jpql.append(" a1.nombre, va1.nombre,");
/* 341:347 */       } else if (tipoReporte == 2) {
/* 342:348 */         jpql.append(" m.nombre,");
/* 343:349 */       } else if (tipoReporte == 3) {
/* 344:350 */         jpql.append(" CONCAT(pr.apellidos, ' ', nombres),");
/* 345:    */       }
/* 346:352 */       jpql.append(" pro.nombre");
/* 347:    */     }
/* 348:354 */     System.out.println("SQL-->" + jpql.toString());
/* 349:355 */     Query query = this.em.createQuery(jpql.toString());
/* 350:356 */     query.setParameter("fechaDesde", FuncionesUtiles.setAtributoFecha(fechaDesde));
/* 351:357 */     query.setParameter("fechaHasta", FuncionesUtiles.setFechaMilisegundos(fechaHasta, 23, 59, 59, 99));
/* 352:358 */     query.setParameter("documentoBaseINGPROD", DocumentoBase.INGRESO_PRODUCCION);
/* 353:359 */     query.setParameter("documentoBaseTRANSPRO", DocumentoBase.TRANSFORMACION_PRODUCTO);
/* 354:360 */     if (bodega != null) {
/* 355:361 */       query.setParameter("bodega", bodega);
/* 356:    */     }
/* 357:363 */     if (categoriaProducto != null) {
/* 358:364 */       query.setParameter("categoriaProducto", categoriaProducto);
/* 359:    */     }
/* 360:366 */     if (subCategoriaProducto != null) {
/* 361:367 */       query.setParameter("subCategoriaProducto", subCategoriaProducto);
/* 362:    */     }
/* 363:369 */     if (personaResponsable != null) {
/* 364:370 */       query.setParameter("personaResponsable", personaResponsable);
/* 365:    */     }
/* 366:372 */     if (maquina != null) {
/* 367:373 */       query.setParameter("maquina", maquina);
/* 368:    */     }
/* 369:375 */     if ((listValoresAtributos != null) && (listValoresAtributos.size() > 0)) {
/* 370:376 */       query.setParameter("listValoresAtributos", listValoresAtributos);
/* 371:    */     }
/* 372:378 */     List<Object[]> listResult = query.getResultList();
/* 373:379 */     if ((tipoReporte > 1) && (!agrupado)) {
/* 374:380 */       Collections.sort(listResult, new Comparator()
/* 375:    */       {
/* 376:    */         public int compare(Object[] o1, Object[] o2)
/* 377:    */         {
/* 378:383 */           return ((String)o1[2] + "_" + (String)o1[0]).compareTo((String)o2[2] + "_" + (String)o2[0]);
/* 379:    */         }
/* 380:    */       });
/* 381:    */     }
/* 382:388 */     return listResult;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List getReporteIRBPNR(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/* 386:    */   {
/* 387:393 */     Map<String, Object[]> mapa = new HashMap();
/* 388:394 */     StringBuilder jpql = new StringBuilder();
/* 389:395 */     jpql = new StringBuilder();
/* 390:396 */     jpql.append(" SELECT pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol,  SUM(dfc.cantidad * (coalesce(pp.cantidadUnidades,1))), 0, 0, 0, 0, ipfc.porcentajeImpuesto ");
/* 391:397 */     jpql.append(" FROM ImpuestoProductoFacturaCliente ipfc ");
/* 392:398 */     jpql.append(" INNER JOIN ipfc.impuesto imp ");
/* 393:399 */     jpql.append(" INNER JOIN ipfc.detalleFacturaCliente dfc ");
/* 394:400 */     jpql.append(" INNER JOIN dfc.facturaCliente fc ");
/* 395:401 */     jpql.append(" INNER JOIN fc.documento do ");
/* 396:402 */     jpql.append(" INNER JOIN dfc.producto pro ");
/* 397:403 */     jpql.append(" LEFT JOIN pro.presentacionProducto pp ");
/* 398:404 */     jpql.append(" LEFT JOIN pro.ibpMarca mar ");
/* 399:405 */     jpql.append(" LEFT JOIN pro.ibpCapacidad cap ");
/* 400:406 */     jpql.append(" LEFT JOIN pro.ibpUnidad unid ");
/* 401:407 */     jpql.append(" LEFT JOIN mar.ibpClasificacion clas ");
/* 402:408 */     jpql.append(" WHERE (fc.fecha >= :fechaDesde AND fc.fecha <= :fechaHasta) ");
/* 403:409 */     jpql.append(" AND imp.codigo = '5' ");
/* 404:410 */     jpql.append(" AND do.documentoBase = :documentoBaseFactura ");
/* 405:411 */     jpql.append(" AND (fc.estado = :estadoProcesado OR fc.estado = :estadoContabilizado)");
/* 406:412 */     jpql.append(" AND fc.idOrganizacion = :idOrganizacion ");
/* 407:413 */     jpql.append(" GROUP BY pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, ipfc.porcentajeImpuesto ");
/* 408:    */     
/* 409:415 */     Query query = this.em.createQuery(jpql.toString());
/* 410:416 */     query.setParameter("fechaDesde", fechaDesde);
/* 411:417 */     query.setParameter("fechaHasta", fechaHasta);
/* 412:418 */     query.setParameter("estadoProcesado", Estado.PROCESADO);
/* 413:419 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 414:420 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 415:421 */     query.setParameter("documentoBaseFactura", DocumentoBase.FACTURA_CLIENTE);
/* 416:    */     
/* 417:423 */     List<Object[]> result = query.getResultList();
/* 418:424 */     for (Object[] objects : result)
/* 419:    */     {
/* 420:425 */       objects[9] = Integer.valueOf(((BigDecimal)objects[9]).intValue());
/* 421:426 */       objects[10] = Integer.valueOf(0);
/* 422:427 */       mapa.put((String)objects[1], objects);
/* 423:    */     }
/* 424:431 */     StringBuilder jpql2 = new StringBuilder();
/* 425:432 */     jpql2 = new StringBuilder();
/* 426:433 */     jpql2.append(" SELECT pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, 0,  SUM(dfc.cantidad * (coalesce(pp.cantidadUnidades,1))), 0, 0, 0, ipfc.porcentajeImpuesto ");
/* 427:434 */     jpql2.append(" FROM ImpuestoProductoFacturaCliente ipfc ");
/* 428:435 */     jpql2.append(" INNER JOIN ipfc.impuesto imp ");
/* 429:436 */     jpql2.append(" INNER JOIN ipfc.detalleFacturaCliente dfc ");
/* 430:437 */     jpql2.append(" INNER JOIN dfc.facturaCliente fc ");
/* 431:438 */     jpql2.append(" INNER JOIN fc.documento do ");
/* 432:439 */     jpql2.append(" INNER JOIN dfc.producto pro ");
/* 433:440 */     jpql2.append(" LEFT JOIN pro.presentacionProducto pp ");
/* 434:441 */     jpql2.append(" LEFT JOIN pro.ibpMarca mar ");
/* 435:442 */     jpql2.append(" LEFT JOIN pro.ibpCapacidad cap ");
/* 436:443 */     jpql2.append(" LEFT JOIN pro.ibpUnidad unid ");
/* 437:444 */     jpql2.append(" LEFT JOIN mar.ibpClasificacion clas ");
/* 438:445 */     jpql2.append(" WHERE (fc.fecha >= :fechaDesde AND fc.fecha <= :fechaHasta) ");
/* 439:446 */     jpql2.append(" AND imp.codigo = '5' ");
/* 440:447 */     jpql2.append(" AND do.documentoBase = :documentoBaseDevolucion ");
/* 441:448 */     jpql2.append(" AND (fc.estado = :estadoProcesado OR fc.estado = :estadoContabilizado)");
/* 442:449 */     jpql2.append(" AND fc.idOrganizacion = :idOrganizacion ");
/* 443:450 */     jpql2.append(" GROUP BY pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, ipfc.porcentajeImpuesto ");
/* 444:    */     
/* 445:452 */     Query query2 = this.em.createQuery(jpql2.toString());
/* 446:453 */     query2.setParameter("fechaDesde", fechaDesde);
/* 447:454 */     query2.setParameter("fechaHasta", fechaHasta);
/* 448:455 */     query2.setParameter("estadoProcesado", Estado.PROCESADO);
/* 449:456 */     query2.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 450:457 */     query2.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 451:458 */     query2.setParameter("documentoBaseDevolucion", DocumentoBase.DEVOLUCION_CLIENTE);
/* 452:    */     
/* 453:460 */     List<Object[]> result2 = query2.getResultList();
/* 454:461 */     for (Object[] objects : result2) {
/* 455:462 */       if (mapa.containsKey((String)objects[1]))
/* 456:    */       {
/* 457:463 */         Object[] factura = (Object[])mapa.get((String)objects[1]);
/* 458:464 */         factura[10] = Integer.valueOf(((BigDecimal)objects[10]).intValue());
/* 459:465 */         mapa.put((String)objects[1], factura);
/* 460:    */       }
/* 461:    */       else
/* 462:    */       {
/* 463:467 */         objects[9] = Integer.valueOf(0);
/* 464:468 */         objects[10] = Integer.valueOf(((BigDecimal)objects[10]).intValue());
/* 465:469 */         mapa.put((String)objects[1], objects);
/* 466:    */       }
/* 467:    */     }
/* 468:473 */     StringBuilder jpql3 = new StringBuilder();
/* 469:474 */     jpql3.append(" SELECT pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, 0,  0, SUM(dmi.cantidad * (coalesce(pp.cantidadUnidades,1))), 0, 0, ri.porcentajeImpuesto");
/* 470:475 */     jpql3.append(" FROM DetalleMovimientoInventario dmi ");
/* 471:476 */     jpql3.append(" INNER JOIN dmi.movimientoInventario mi ");
/* 472:477 */     jpql3.append(" INNER JOIN mi.documento do ");
/* 473:478 */     jpql3.append(" INNER JOIN dmi.producto pro ");
/* 474:479 */     jpql3.append(" LEFT JOIN pro.presentacionProducto pp ");
/* 475:480 */     jpql3.append(" LEFT JOIN pro.ibpMarca mar ");
/* 476:481 */     jpql3.append(" LEFT JOIN pro.ibpCapacidad cap ");
/* 477:482 */     jpql3.append(" LEFT JOIN pro.ibpUnidad unid ");
/* 478:483 */     jpql3.append(" LEFT JOIN mar.ibpClasificacion clas ");
/* 479:484 */     jpql3.append(" LEFT JOIN pro.categoriaImpuesto ci ");
/* 480:485 */     jpql3.append(" INNER JOIN ci.listaImpuesto imp ");
/* 481:486 */     jpql3.append(" INNER JOIN imp.listaRangoImpuesto ri ");
/* 482:487 */     jpql3.append(" WHERE (mi.fecha >= :fechaDesde AND mi.fecha <= :fechaHasta) ");
/* 483:488 */     jpql3.append(" AND (ri.fechaDesde <= :fechaHasta AND ri.fechaHasta >= :fechaDesde) ");
/* 484:489 */     jpql3.append(" AND imp.codigo = '5' ");
/* 485:490 */     jpql3.append(" AND do.documentoBase = :documentoBaseIngresoProduccion ");
/* 486:491 */     jpql3.append(" AND dmi.indicadorRecibido = true ");
/* 487:492 */     jpql3.append(" AND mi.idOrganizacion = :idOrganizacion ");
/* 488:493 */     jpql3.append(" GROUP BY pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, ri.porcentajeImpuesto ");
/* 489:    */     
/* 490:495 */     Query query3 = this.em.createQuery(jpql3.toString());
/* 491:496 */     query3.setParameter("fechaDesde", fechaDesde);
/* 492:497 */     query3.setParameter("fechaHasta", fechaHasta);
/* 493:498 */     query3.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 494:499 */     query3.setParameter("documentoBaseIngresoProduccion", DocumentoBase.INGRESO_PRODUCCION);
/* 495:    */     
/* 496:501 */     List<Object[]> result3 = query3.getResultList();
/* 497:502 */     for (Object[] objects : result3) {
/* 498:503 */       if (mapa.containsKey((String)objects[1]))
/* 499:    */       {
/* 500:504 */         Object[] factura = (Object[])mapa.get((String)objects[1]);
/* 501:505 */         factura[11] = Integer.valueOf(((BigDecimal)objects[11]).intValue());
/* 502:506 */         mapa.put((String)objects[1], factura);
/* 503:    */       }
/* 504:    */       else
/* 505:    */       {
/* 506:508 */         objects[9] = Integer.valueOf(0);
/* 507:509 */         objects[10] = Integer.valueOf(0);
/* 508:510 */         objects[11] = Integer.valueOf(((BigDecimal)objects[11]).intValue());
/* 509:511 */         mapa.put((String)objects[1], objects);
/* 510:    */       }
/* 511:    */     }
/* 512:516 */     StringBuilder jpql4 = new StringBuilder();
/* 513:517 */     jpql4.append(" SELECT pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, 0,  0, 0, ");
/* 514:518 */     jpql4.append(" SUM(dosm.cantidadAdicional), 0, ri.porcentajeImpuesto");
/* 515:519 */     jpql4.append(" FROM DetalleOrdenSalidaMaterial dosm ");
/* 516:520 */     jpql4.append(" INNER JOIN dosm.ordenSalidaMaterial sm ");
/* 517:521 */     jpql4.append(" INNER JOIN dosm.producto pro ");
/* 518:522 */     jpql4.append(" LEFT JOIN pro.presentacionProducto pp ");
/* 519:523 */     jpql4.append(" LEFT JOIN pro.ibpCapacidad cap ");
/* 520:524 */     jpql4.append(" LEFT JOIN pro.ibpUnidad unid ");
/* 521:525 */     jpql4.append(" LEFT JOIN pro.ibpMarca mar ");
/* 522:526 */     jpql4.append(" LEFT JOIN mar.ibpClasificacion clas ");
/* 523:527 */     jpql4.append(" LEFT JOIN pro.categoriaImpuesto ci ");
/* 524:528 */     jpql4.append(" INNER JOIN ci.listaImpuesto imp ");
/* 525:529 */     jpql4.append(" INNER JOIN imp.listaRangoImpuesto ri ");
/* 526:530 */     jpql4.append(" WHERE (sm.fecha >= :fechaDesde AND sm.fecha <= :fechaHasta) ");
/* 527:531 */     jpql4.append(" AND (ri.fechaDesde <= :fechaHasta AND ri.fechaHasta >= :fechaDesde) ");
/* 528:532 */     jpql4.append(" AND imp.codigo = '5' ");
/* 529:533 */     jpql4.append(" AND sm.estado = :estadoCerrado ");
/* 530:534 */     jpql4.append(" AND sm.idOrganizacion = :idOrganizacion ");
/* 531:535 */     jpql4.append(" GROUP BY pro.nombre, pro.codigo, pro.ibpCodigoImpuesto, clas.codigo, mar.codigo, pro.ibpPresentacion, cap.codigo, unid.codigo, pro.ibpGradoAlcohol, ri.porcentajeImpuesto ");
/* 532:536 */     jpql4.append(" HAVING SUM(dosm.cantidadAdicional) != 0 ");
/* 533:    */     
/* 534:538 */     Query query4 = this.em.createQuery(jpql4.toString());
/* 535:539 */     query4.setParameter("fechaDesde", fechaDesde);
/* 536:540 */     query4.setParameter("fechaHasta", fechaHasta);
/* 537:541 */     query4.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 538:542 */     query4.setParameter("estadoCerrado", Estado.CERRADO);
/* 539:    */     
/* 540:544 */     List<Object[]> result4 = query4.getResultList();
/* 541:545 */     for (Object[] objects : result4) {
/* 542:546 */       if (mapa.containsKey((String)objects[1]))
/* 543:    */       {
/* 544:547 */         Object[] factura = (Object[])mapa.get((String)objects[1]);
/* 545:548 */         factura[12] = Integer.valueOf(((BigDecimal)objects[12]).intValue());
/* 546:549 */         mapa.put((String)objects[1], factura);
/* 547:    */       }
/* 548:    */       else
/* 549:    */       {
/* 550:551 */         objects[9] = Integer.valueOf(0);
/* 551:552 */         objects[10] = Integer.valueOf(0);
/* 552:553 */         objects[11] = Integer.valueOf(0);
/* 553:554 */         objects[12] = Integer.valueOf(((BigDecimal)objects[12]).intValue());
/* 554:555 */         mapa.put((String)objects[1], objects);
/* 555:    */       }
/* 556:    */     }
/* 557:560 */     Object lista = new ArrayList();
/* 558:561 */     ((List)lista).addAll(mapa.values());
/* 559:562 */     return lista;
/* 560:    */   }
/* 561:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.reportes.produccion.ReporteFabricacionDao
 * JD-Core Version:    0.7.0.1
 */