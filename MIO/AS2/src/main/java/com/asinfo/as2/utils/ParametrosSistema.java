/*   1:    */ package com.asinfo.as2.utils;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Organizacion;
/*   4:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*   5:    */ import com.asinfo.as2.util.AppUtil;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.Map;
/*  12:    */ 
/*  13:    */ public class ParametrosSistema
/*  14:    */ {
/*  15: 33 */   private static Map<Integer, Map<Parametro, Object>> hmParametros = new HashMap();
/*  16:    */   
/*  17:    */   public static void borrarParametros()
/*  18:    */   {
/*  19: 36 */     hmParametros.clear();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static Map<Parametro, Object> getParametrosOrganizacion(Integer igOrganizacion)
/*  23:    */   {
/*  24: 41 */     Map<Parametro, Object> hmParametrosOrganizacion = (Map)hmParametros.get(igOrganizacion);
/*  25: 43 */     if (hmParametrosOrganizacion == null)
/*  26:    */     {
/*  27: 44 */       hmParametrosOrganizacion = new HashMap();
/*  28: 45 */       hmParametros.put(igOrganizacion, hmParametrosOrganizacion);
/*  29:    */     }
/*  30: 48 */     return hmParametrosOrganizacion;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static Object getParametro(Parametro parametro)
/*  34:    */   {
/*  35: 58 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  36: 59 */     return getParametro(idOrganizacion, parametro);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Object getParametro(int idOrganizacion, Parametro parametro)
/*  40:    */   {
/*  41: 71 */     Object parametroTmp = ((Map)hmParametros.get(Integer.valueOf(idOrganizacion))).get(parametro);
/*  42: 73 */     if ("String".equals(parametro.getTipoDato())) {
/*  43: 74 */       return parametroTmp;
/*  44:    */     }
/*  45: 76 */     if ("Boolean".equals(parametro.getTipoDato())) {
/*  46: 77 */       return Boolean.valueOf(Boolean.parseBoolean(parametroTmp.toString()));
/*  47:    */     }
/*  48: 79 */     if ("BigDecimal".equals(parametro.getTipoDato()))
/*  49:    */     {
/*  50: 81 */       double parametroTemp = Double.parseDouble(parametroTmp.toString());
/*  51: 82 */       return BigDecimal.valueOf(parametroTemp);
/*  52:    */     }
/*  53: 84 */     if ("Date".equals(parametro.getTipoDato())) {
/*  54: 85 */       return FuncionesUtiles.stringToDate(parametroTmp.toString(), getParametro(Parametro.FORMATO_FECHA).toString());
/*  55:    */     }
/*  56:    */     try
/*  57:    */     {
/*  58: 88 */       return Integer.valueOf(Integer.parseInt(parametroTmp.toString()));
/*  59:    */     }
/*  60:    */     catch (NumberFormatException e) {}
/*  61: 90 */     return Integer.valueOf(Integer.parseInt("0"));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Integer getCuentaUtilidadPeriodoActual(int idOrganizacion)
/*  65:    */   {
/*  66:108 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_UTILIDAD_PERIODO_ACTUAL);
/*  67:    */   }
/*  68:    */   
/*  69:    */   @Deprecated
/*  70:    */   public static Integer getCuentaUtilidadPeriodoActual()
/*  71:    */   {
/*  72:113 */     return (Integer)getParametro(Parametro.CUENTA_UTILIDAD_PERIODO_ACTUAL);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Integer getCuentaUtilidadPeriodoAnterior(int idOrganizacion)
/*  76:    */   {
/*  77:118 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_UTILIDAD_PERIODO_ANTERIOR);
/*  78:    */   }
/*  79:    */   
/*  80:    */   @Deprecated
/*  81:    */   public static Integer getCuentaUtilidadPeriodoAnterior()
/*  82:    */   {
/*  83:123 */     return (Integer)getParametro(Parametro.CUENTA_UTILIDAD_PERIODO_ANTERIOR);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static Integer getTipoAsientoInterfazVentas(int idOrganizacion)
/*  87:    */   {
/*  88:128 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_INTERFAZ_VENTAS);
/*  89:    */   }
/*  90:    */   
/*  91:    */   @Deprecated
/*  92:    */   public static Integer getTipoAsientoInterfazVentas()
/*  93:    */   {
/*  94:133 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_INTERFAZ_VENTAS);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static Integer getTipoAsientoProtesto(int idOrganizacion)
/*  98:    */   {
/*  99:138 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_PROTESTO);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static Integer getCuentaPerdidaPeriodoActual(int idOrganizacion)
/* 103:    */   {
/* 104:143 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_PERDIDA_PERIODO_ACTUAL);
/* 105:    */   }
/* 106:    */   
/* 107:    */   @Deprecated
/* 108:    */   public static Integer getCuentaPerdidaPeriodoActual()
/* 109:    */   {
/* 110:148 */     return (Integer)getParametro(Parametro.CUENTA_PERDIDA_PERIODO_ACTUAL);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static Integer getCuentaPerdidaPeriodoActerior(int idOrganizacion)
/* 114:    */   {
/* 115:153 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_PERDIDA_PERIODO_ANTERIOR);
/* 116:    */   }
/* 117:    */   
/* 118:    */   @Deprecated
/* 119:    */   public static Integer getCuentaPerdidaPeriodoActerior()
/* 120:    */   {
/* 121:158 */     return (Integer)getParametro(Parametro.CUENTA_PERDIDA_PERIODO_ANTERIOR);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static BigDecimal getPorcentajeIva(int idOrganizacion)
/* 125:    */   {
/* 126:163 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.PORCENTAJE_IVA);
/* 127:    */   }
/* 128:    */   
/* 129:    */   @Deprecated
/* 130:    */   public static BigDecimal getPorcentajeIva()
/* 131:    */   {
/* 132:168 */     return (BigDecimal)getParametro(Parametro.PORCENTAJE_IVA);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static Boolean isVerificarExistencia(int idOrganizacion)
/* 136:    */   {
/* 137:173 */     return (Boolean)getParametro(idOrganizacion, Parametro.VERIFICAR_EXISTENCIAS);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static Boolean isCosteoPorBodega(int idOrganizacion)
/* 141:    */   {
/* 142:178 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_COSTO_POR_BODEGA);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static Boolean isPermitirFechaInventario(int idOrganizacion)
/* 146:    */   {
/* 147:183 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_PERMITIR_FECHA_INVENTARIO);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static Boolean isRegistroReversoAnulacionInventario(int idOrganizacion)
/* 151:    */   {
/* 152:188 */     return (Boolean)getParametro(idOrganizacion, Parametro.GENERAR_REVERSO_ANULACION_MOVIMIENTO_INVENTARIO);
/* 153:    */   }
/* 154:    */   
/* 155:    */   @Deprecated
/* 156:    */   public static Boolean isVerificarExistencia()
/* 157:    */   {
/* 158:193 */     return (Boolean)getParametro(Parametro.VERIFICAR_EXISTENCIAS);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static Integer getDiasMaximosEdicionFactura(int idOrganizacion)
/* 162:    */   {
/* 163:198 */     return (Integer)getParametro(idOrganizacion, Parametro.DIAS_MAXIMOS_EDICION_FACTURA);
/* 164:    */   }
/* 165:    */   
/* 166:    */   @Deprecated
/* 167:    */   public static Integer getDiasMaximosEdicionFactura()
/* 168:    */   {
/* 169:203 */     return (Integer)getParametro(Parametro.DIAS_MAXIMOS_EDICION_FACTURA);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static BigDecimal getPorcentajeAportePersonal(int idOrganizacion)
/* 173:    */   {
/* 174:208 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.PORCENTAJE_APORTE_PERSONAL);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static BigDecimal getPorcentajeAportePatronal(int idOrganizacion)
/* 178:    */   {
/* 179:213 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.PORCENTAJE_APORTE_PATRONAL);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static BigDecimal getIntervaloIncrementoValorConversionUnidad(int idOrganizacion)
/* 183:    */   {
/* 184:218 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.INTERVALO_INCREMENTO_VALOR_CONVERSION_UNIDAD);
/* 185:    */   }
/* 186:    */   
/* 187:    */   @Deprecated
/* 188:    */   public static BigDecimal getIntervaloIncrementoValorConversionUnidad()
/* 189:    */   {
/* 190:223 */     return (BigDecimal)getParametro(Parametro.INTERVALO_INCREMENTO_VALOR_CONVERSION_UNIDAD);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static Date getFechaMaximaSaldosIniciales(int idOrganizacion)
/* 194:    */   {
/* 195:228 */     return (Date)getParametro(idOrganizacion, Parametro.FECHA_MAXIMA_SALDOS_INICIALES);
/* 196:    */   }
/* 197:    */   
/* 198:    */   @Deprecated
/* 199:    */   public static Date getFechaMaximaSaldosIniciales()
/* 200:    */   {
/* 201:233 */     return (Date)getParametro(Parametro.FECHA_MAXIMA_SALDOS_INICIALES);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static String getAS2_HOME(int idOrganizacion)
/* 205:    */   {
/* 206:238 */     return (String)getParametro(idOrganizacion, Parametro.AS2_HOME);
/* 207:    */   }
/* 208:    */   
/* 209:    */   @Deprecated
/* 210:    */   public static String getAS2_HOME()
/* 211:    */   {
/* 212:243 */     return (String)getParametro(Parametro.AS2_HOME);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static Boolean getGeneracionDeCostosAutomatica(int idOrganizacion)
/* 216:    */   {
/* 217:248 */     return (Boolean)getParametro(idOrganizacion, Parametro.GENERACION_DE_COSTOS_AUTOMATICA);
/* 218:    */   }
/* 219:    */   
/* 220:    */   @Deprecated
/* 221:    */   public static Boolean getGeneracionDeCostosAutomatica()
/* 222:    */   {
/* 223:253 */     return (Boolean)getParametro(Parametro.GENERACION_DE_COSTOS_AUTOMATICA);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static String getFormatoFecha(int idOrganizacion)
/* 227:    */   {
/* 228:258 */     return (String)getParametro(idOrganizacion, Parametro.FORMATO_FECHA);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public static String getFormatoDinero(int idOrganizacion)
/* 232:    */   {
/* 233:263 */     return (String)getParametro(idOrganizacion, Parametro.FORMATO_DINERO);
/* 234:    */   }
/* 235:    */   
/* 236:    */   @Deprecated
/* 237:    */   public static String getFormatoFecha()
/* 238:    */   {
/* 239:268 */     return (String)getParametro(Parametro.FORMATO_FECHA);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static Integer getRubroSalarioUnificado(int idOrganizacion)
/* 243:    */   {
/* 244:273 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_SALARIO_UNIFICADO);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public static Integer getRubroFormula1(int idOrganizacion)
/* 248:    */   {
/* 249:278 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_FORMULA1);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static Integer getRubroFormula2(int idOrganizacion)
/* 253:    */   {
/* 254:283 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_FORMULA2);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public static Integer getRubroFormula3(int idOrganizacion)
/* 258:    */   {
/* 259:288 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_FORMULA3);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static Integer getRubroFormula4(int idOrganizacion)
/* 263:    */   {
/* 264:293 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_FORMULA4);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public static Integer getRubroFormula5(int idOrganizacion)
/* 268:    */   {
/* 269:298 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_FORMULA5);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public static Integer getRubroImpuestoALARenta(int idOrganizacion)
/* 273:    */   {
/* 274:303 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_IMPUESTO_A_LA_RENTA);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public static Integer getRubroAportePatronalIESS(int idOrganizacion)
/* 278:    */   {
/* 279:308 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_APORTE_PATRONAL_IESS);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public static Integer getRubroAportePersonalIESS(int idOrganizacion)
/* 283:    */   {
/* 284:313 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_APORTE_PERSONAL_IESS);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public static BigDecimal getValorDecimoCuarto(int idOrganizacion)
/* 288:    */   {
/* 289:318 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.VALOR_DECIMO_CUARTO);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public static Integer getAnioDiaAdicionalVacacion(int idOrganizacion)
/* 293:    */   {
/* 294:323 */     return (Integer)getParametro(idOrganizacion, Parametro.ANIO_DIA_ADICIONAL_VACACION);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public static Integer getNumeroRegistrosVentana(int idOrganizacion)
/* 298:    */   {
/* 299:328 */     return (Integer)getParametro(idOrganizacion, Parametro.NUMERO_REGISTROS_VENTANA);
/* 300:    */   }
/* 301:    */   
/* 302:    */   @Deprecated
/* 303:    */   public static Integer getNumeroRegistrosVentana()
/* 304:    */   {
/* 305:333 */     return (Integer)getParametro(Parametro.NUMERO_REGISTROS_VENTANA);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public static Integer getRubroUtilidad(int idOrganizacion)
/* 309:    */   {
/* 310:338 */     return (Integer)getParametro(idOrganizacion, Parametro.RUBRO_UTILIDAD);
/* 311:    */   }
/* 312:    */   
/* 313:    */   public static Integer getNumeroRegistrosVentanaReportes(int idOrganizacion)
/* 314:    */   {
/* 315:343 */     return (Integer)getParametro(idOrganizacion, Parametro.NUMERO_REGISTROS_VENTANA_REPORTES);
/* 316:    */   }
/* 317:    */   
/* 318:    */   @Deprecated
/* 319:    */   public static Integer getNumeroRegistrosVentanaReportes()
/* 320:    */   {
/* 321:348 */     return (Integer)getParametro(Parametro.NUMERO_REGISTROS_VENTANA_REPORTES);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public static String getTipoAnexoSRI(int idOrganizacion)
/* 325:    */   {
/* 326:353 */     return (String)getParametro(idOrganizacion, Parametro.TIPO_ANEXO_SRI);
/* 327:    */   }
/* 328:    */   
/* 329:    */   @Deprecated
/* 330:    */   public static String getTipoAnexoSRI()
/* 331:    */   {
/* 332:358 */     return (String)getParametro(Parametro.TIPO_ANEXO_SRI);
/* 333:    */   }
/* 334:    */   
/* 335:    */   public static Integer getCuentaBonoCXP(int idOrganizacion)
/* 336:    */   {
/* 337:363 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_BONO_CXP);
/* 338:    */   }
/* 339:    */   
/* 340:    */   @Deprecated
/* 341:    */   public static Integer getCuentaBonoCXP()
/* 342:    */   {
/* 343:368 */     return (Integer)getParametro(Parametro.CUENTA_BONO_CXP);
/* 344:    */   }
/* 345:    */   
/* 346:    */   public static Integer getCuentaOrdenImpuestoRenta(int idOrganizacion)
/* 347:    */   {
/* 348:373 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_ORDEN_IMPUESTO_RENTA);
/* 349:    */   }
/* 350:    */   
/* 351:    */   @Deprecated
/* 352:    */   public static Integer getCuentaOrdenImpuestoRenta()
/* 353:    */   {
/* 354:378 */     return (Integer)getParametro(Parametro.CUENTA_ORDEN_IMPUESTO_RENTA);
/* 355:    */   }
/* 356:    */   
/* 357:    */   public static Integer getCuentaImpuestoDiferido(int idOrganizacion)
/* 358:    */   {
/* 359:383 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_IMPUESTO_DIFERIDO);
/* 360:    */   }
/* 361:    */   
/* 362:    */   @Deprecated
/* 363:    */   public static Integer getCuentaImpuestoDiferido()
/* 364:    */   {
/* 365:388 */     return (Integer)getParametro(Parametro.CUENTA_IMPUESTO_DIFERIDO);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public static Boolean getFacturaProveedorImpuestoProductoServicioGasto(int idOrganizacion)
/* 369:    */   {
/* 370:393 */     return (Boolean)getParametro(idOrganizacion, Parametro.FACTURA_PROVEEDOR_IMPUESTO_PRODUCTO_SERVICIO_GASTO);
/* 371:    */   }
/* 372:    */   
/* 373:    */   @Deprecated
/* 374:    */   public static Boolean getFacturaProveedorImpuestoProductoServicioGasto()
/* 375:    */   {
/* 376:398 */     return (Boolean)getParametro(Parametro.FACTURA_PROVEEDOR_IMPUESTO_PRODUCTO_SERVICIO_GASTO);
/* 377:    */   }
/* 378:    */   
/* 379:    */   public static Integer getTipoAsientoInterfazDepreciacion(int idOrganizacion)
/* 380:    */   {
/* 381:403 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_INTERFAZ_DEPRECIACION);
/* 382:    */   }
/* 383:    */   
/* 384:    */   @Deprecated
/* 385:    */   public static Integer getTipoAsientoInterfazDepreciacion()
/* 386:    */   {
/* 387:408 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_INTERFAZ_DEPRECIACION);
/* 388:    */   }
/* 389:    */   
/* 390:    */   public static Integer getTipoAsientoInterfazAjusteDepreciacion(int idOrganizacion)
/* 391:    */   {
/* 392:413 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_INTERFAZ_AJUSTE_DEPRECIACION);
/* 393:    */   }
/* 394:    */   
/* 395:    */   @Deprecated
/* 396:    */   public static Integer getTipoAsientoInterfazAjusteDepreciacion()
/* 397:    */   {
/* 398:418 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_INTERFAZ_AJUSTE_DEPRECIACION);
/* 399:    */   }
/* 400:    */   
/* 401:    */   public static Integer getTipoAsientoConciliacionBancariaAutomatica(int idOrganizacion)
/* 402:    */   {
/* 403:423 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_CONCILIACION_BANCARIA_AUTOMATICA);
/* 404:    */   }
/* 405:    */   
/* 406:    */   @Deprecated
/* 407:    */   public static Integer getTipoAsientoConciliacionBancariaAutomatica()
/* 408:    */   {
/* 409:428 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_CONCILIACION_BANCARIA_AUTOMATICA);
/* 410:    */   }
/* 411:    */   
/* 412:    */   public static Integer getNumeroDecimalesPrecioVenta(int idOrganizacion)
/* 413:    */   {
/* 414:433 */     return (Integer)getParametro(idOrganizacion, Parametro.NUMERO_DECIMALES_PRECIO_VENTA);
/* 415:    */   }
/* 416:    */   
/* 417:    */   @Deprecated
/* 418:    */   public static Integer getNumeroDecimalesPrecioVenta()
/* 419:    */   {
/* 420:438 */     return (Integer)getParametro(Parametro.NUMERO_DECIMALES_PRECIO_VENTA);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public static Integer getNumeroDecimalesPrecioCompra(int idOrganizacion)
/* 424:    */   {
/* 425:443 */     return (Integer)getParametro(idOrganizacion, Parametro.NUMERO_DECIMALES_PRECIO_COMPRA);
/* 426:    */   }
/* 427:    */   
/* 428:    */   @Deprecated
/* 429:    */   public static Integer getNumeroDecimalesPrecioCompra()
/* 430:    */   {
/* 431:448 */     return (Integer)getParametro(Parametro.NUMERO_DECIMALES_PRECIO_COMPRA);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public static Integer getClienteFacturaAfiliacion(int idOrganizacion)
/* 435:    */   {
/* 436:453 */     return (Integer)getParametro(idOrganizacion, Parametro.CLIENTE_FACTURA_BONO_AFILIACIONES);
/* 437:    */   }
/* 438:    */   
/* 439:    */   @Deprecated
/* 440:    */   public static Integer getClienteFacturaAfiliacion()
/* 441:    */   {
/* 442:458 */     return (Integer)getParametro(Parametro.CLIENTE_FACTURA_BONO_AFILIACIONES);
/* 443:    */   }
/* 444:    */   
/* 445:    */   public static Integer getNumeroMaximoLineasPago(int idOrganizacion)
/* 446:    */   {
/* 447:463 */     return (Integer)getParametro(idOrganizacion, Parametro.NUMERO_MAXIMO_LINEAS_PAGO);
/* 448:    */   }
/* 449:    */   
/* 450:    */   @Deprecated
/* 451:    */   public static Integer getNumeroMaximoLineasPago()
/* 452:    */   {
/* 453:468 */     return (Integer)getParametro(Parametro.NUMERO_MAXIMO_LINEAS_PAGO);
/* 454:    */   }
/* 455:    */   
/* 456:    */   public static Integer getTipoAsientoCierreContableCuenta(int idOrganizacion)
/* 457:    */   {
/* 458:473 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_CIERRE_CONTABLE_CUENTA);
/* 459:    */   }
/* 460:    */   
/* 461:    */   @Deprecated
/* 462:    */   public static Integer getTipoAsientoCierreContableCuenta()
/* 463:    */   {
/* 464:478 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_CIERRE_CONTABLE_CUENTA);
/* 465:    */   }
/* 466:    */   
/* 467:    */   public static Integer getTipoAsientoInterfazConsumoBodega(int idOrganizacion)
/* 468:    */   {
/* 469:483 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_CONSUMO_BODEGA);
/* 470:    */   }
/* 471:    */   
/* 472:    */   public static Integer getTipoAsientoInterfazContratoVenta(int idOrganizacion)
/* 473:    */   {
/* 474:488 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_CONTRATO_VENTA);
/* 475:    */   }
/* 476:    */   
/* 477:    */   public static Boolean getTransferenciaBodegaMostrarCliente(int idOrganizacion)
/* 478:    */   {
/* 479:493 */     return (Boolean)getParametro(idOrganizacion, Parametro.TRANSFERENCIA_BODEGA_MOSTRAR_CLIENTE);
/* 480:    */   }
/* 481:    */   
/* 482:    */   @Deprecated
/* 483:    */   public static Integer getTipoAsientoInterfazConsumoBodega()
/* 484:    */   {
/* 485:498 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_CONSUMO_BODEGA);
/* 486:    */   }
/* 487:    */   
/* 488:    */   public static Boolean getTarifasActualesOperacionEstimadas(int idOrganizacion)
/* 489:    */   {
/* 490:503 */     return (Boolean)getParametro(idOrganizacion, Parametro.TARIFAS_ACTUALES_OPERACION_ESTIMADAS);
/* 491:    */   }
/* 492:    */   
/* 493:    */   @Deprecated
/* 494:    */   public static Boolean getTarifasActualesOperacionEstimadas()
/* 495:    */   {
/* 496:508 */     return (Boolean)getParametro(Parametro.TARIFAS_ACTUALES_OPERACION_ESTIMADAS);
/* 497:    */   }
/* 498:    */   
/* 499:    */   public static Integer getClienteGenerico(int idOrganizacion)
/* 500:    */   {
/* 501:513 */     return (Integer)getParametro(idOrganizacion, Parametro.CLIENTE_GENERICO);
/* 502:    */   }
/* 503:    */   
/* 504:    */   @Deprecated
/* 505:    */   public static Integer getClienteGenerico()
/* 506:    */   {
/* 507:518 */     return (Integer)getParametro(Parametro.CLIENTE_GENERICO);
/* 508:    */   }
/* 509:    */   
/* 510:    */   public static Integer getTipoAsientoInterfazDespacho(int idOrganizacion)
/* 511:    */   {
/* 512:523 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_DESPACHO);
/* 513:    */   }
/* 514:    */   
/* 515:    */   @Deprecated
/* 516:    */   public static Integer getTipoAsientoInterfazDespacho()
/* 517:    */   {
/* 518:528 */     return (Integer)getParametro(Parametro.TIPO_ASIENTO_DESPACHO);
/* 519:    */   }
/* 520:    */   
/* 521:    */   public static Boolean getNotaCreditoReversaGasto(int idOrganizacion)
/* 522:    */   {
/* 523:533 */     return (Boolean)getParametro(idOrganizacion, Parametro.NOTA_CREDITO_REVERSA_GASTO);
/* 524:    */   }
/* 525:    */   
/* 526:    */   @Deprecated
/* 527:    */   public static Boolean getNotaCreditoReversaGasto()
/* 528:    */   {
/* 529:538 */     return (Boolean)getParametro(Parametro.NOTA_CREDITO_REVERSA_GASTO);
/* 530:    */   }
/* 531:    */   
/* 532:    */   public static Boolean getIndicadorAprobarPagos(int idOrganizacion)
/* 533:    */   {
/* 534:543 */     return (Boolean)getParametro(Parametro.INDICADOR_APROBAR_PAGOS);
/* 535:    */   }
/* 536:    */   
/* 537:    */   public static Boolean getIndicadorIngresoComprasPrecioTotal(int idOrganizacion)
/* 538:    */   {
/* 539:548 */     return (Boolean)getParametro(Parametro.INDICADOR_INGRESO_COMPRAS_PRECIO_TOTAL);
/* 540:    */   }
/* 541:    */   
/* 542:    */   public static BigDecimal getValorToleranciaCompraVenta(int idOrganizacion)
/* 543:    */   {
/* 544:553 */     return (BigDecimal)getParametro(Parametro.VALOR_TOLERANCIA_COMPRA_VENTA);
/* 545:    */   }
/* 546:    */   
/* 547:    */   public static Integer getTipoAsientoInterfazDevolucionCostoVentas(int idOrganizacion)
/* 548:    */   {
/* 549:558 */     return (Integer)getParametro(idOrganizacion, Parametro.TIPO_ASIENTO_INTERFAZ_DEVOLUCION_COSTO_VENTAS);
/* 550:    */   }
/* 551:    */   
/* 552:    */   public static Integer getConceptoRetencionComercializadora3X1000(int idOrganizacion)
/* 553:    */   {
/* 554:564 */     return (Integer)getParametro(idOrganizacion, Parametro.CONCEPTO_RETENCION_COMERCIALIZADORA_3X1000);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public static Boolean getRecargosEnFactura(int idOrganizacion)
/* 558:    */   {
/* 559:569 */     return (Boolean)getParametro(idOrganizacion, Parametro.RECARGOS_EN_FACTURA);
/* 560:    */   }
/* 561:    */   
/* 562:    */   public static Integer getcuentaRetencionAsumida(int idOrganizacion)
/* 563:    */   {
/* 564:574 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_RETENCION_ASUMIDA);
/* 565:    */   }
/* 566:    */   
/* 567:    */   public static Integer getCentroCostoGenerico(int idOrganizacion)
/* 568:    */   {
/* 569:579 */     return (Integer)getParametro(idOrganizacion, Parametro.CENTRO_COSTO_GENERICO);
/* 570:    */   }
/* 571:    */   
/* 572:    */   public static Integer getHorasSemanaLaboral(int idOrganizacion)
/* 573:    */   {
/* 574:584 */     return (Integer)getParametro(idOrganizacion, Parametro.HORAS_SEMANA_LABORAL);
/* 575:    */   }
/* 576:    */   
/* 577:    */   public static Boolean getIndicadorAprobarNomina(int idOrganizacion)
/* 578:    */   {
/* 579:589 */     return (Boolean)getParametro(Parametro.INDICADOR_APROBAR_NOMINA);
/* 580:    */   }
/* 581:    */   
/* 582:    */   public static Boolean getAmbienteFacturacionElectronica(int idOrganizacion)
/* 583:    */   {
/* 584:594 */     boolean parametroSistema = ((Boolean)getParametro(idOrganizacion, Parametro.AMBIENTE_FACTURACION_ELECTRONICA)).booleanValue();
/* 585:595 */     if (parametroSistema) {
/* 586:596 */       return Boolean.valueOf(getAmbienteFacturacioElectronicaProduccion());
/* 587:    */     }
/* 588:598 */     return Boolean.valueOf(false);
/* 589:    */   }
/* 590:    */   
/* 591:    */   public static boolean getAmbienteFacturacioElectronicaProduccion()
/* 592:    */   {
/* 593:    */     try
/* 594:    */     {
/* 595:604 */       Map map = System.getenv();
/* 596:605 */       String ruta = (String)map.get("AS2_HOME");
/* 597:606 */       ruta = ruta + File.separator + "config";
/* 598:607 */       String parametroFichero = EjbUtil.obtenerValorArchivoProperties("produccion", ruta, "produccion.properties");
/* 599:608 */       return parametroFichero.equals("SI");
/* 600:    */     }
/* 601:    */     catch (IOException e) {}
/* 602:610 */     return false;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public static Boolean getPublicarPdfFacturae(int idOrganizacion)
/* 606:    */   {
/* 607:616 */     return (Boolean)getParametro(idOrganizacion, Parametro.PUBLICAR_PDF_FACTURAE);
/* 608:    */   }
/* 609:    */   
/* 610:    */   public static Boolean getIndicadorAprobarAjusteInventario(int idOrganizacion)
/* 611:    */   {
/* 612:621 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_APRUEBA_AJUSTE_INVENTARIO);
/* 613:    */   }
/* 614:    */   
/* 615:    */   public static Integer getRetrasoFiltro(int idOrganizacion)
/* 616:    */   {
/* 617:626 */     return (Integer)getParametro(idOrganizacion, Parametro.RETRASO_FILTRO);
/* 618:    */   }
/* 619:    */   
/* 620:    */   public static Boolean getManejaProyectos(int idOrganizacion)
/* 621:    */   {
/* 622:631 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_MANEJA_PROYECTOS);
/* 623:    */   }
/* 624:    */   
/* 625:    */   public static BigDecimal getPorcentajeUtilidadTrabajor(int idOrganizacion)
/* 626:    */   {
/* 627:636 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.PORCENTAJE_UTILIDAD_TRABAJADOR);
/* 628:    */   }
/* 629:    */   
/* 630:    */   public static Boolean isCosteoInventarioFechaHora(int idOrganizacion)
/* 631:    */   {
/* 632:641 */     return (Boolean)getParametro(idOrganizacion, Parametro.COSTEO_INVENTARIO_FECHA_HORA);
/* 633:    */   }
/* 634:    */   
/* 635:    */   public static Integer getTiempoMinimoEntradaTardia(int idOrganizacion)
/* 636:    */   {
/* 637:647 */     return (Integer)getParametro(idOrganizacion, Parametro.TIEMPO_MINIMO_ENTRADA_TARDIA);
/* 638:    */   }
/* 639:    */   
/* 640:    */   public static Integer getTiempoMinimoSalidaTemprana(int idOrganizacion)
/* 641:    */   {
/* 642:652 */     return (Integer)getParametro(idOrganizacion, Parametro.TIEMPO_MINIMO_SALIDA_TEMPRANA);
/* 643:    */   }
/* 644:    */   
/* 645:    */   public static Integer getTiempoPagoHorasExtras(int idOrganizacion)
/* 646:    */   {
/* 647:657 */     return (Integer)getParametro(idOrganizacion, Parametro.TIEMPO_PAGO_HORAS_EXTRAS);
/* 648:    */   }
/* 649:    */   
/* 650:    */   public static Integer getTiempoMinimoEntreTimbradas(int idOrganizacion)
/* 651:    */   {
/* 652:662 */     return (Integer)getParametro(idOrganizacion, Parametro.TIEMPO_MINIMO_ENTRE_TIMBRADAS);
/* 653:    */   }
/* 654:    */   
/* 655:    */   public static Boolean isPagarTurnoPrimerDia(int idOrganizacion)
/* 656:    */   {
/* 657:667 */     return (Boolean)getParametro(idOrganizacion, Parametro.PAGAR_TURNO_PRIMER_DIA);
/* 658:    */   }
/* 659:    */   
/* 660:    */   public static Integer getDiaCortePagoHorasExtras(int idOrganizacion)
/* 661:    */   {
/* 662:672 */     return (Integer)getParametro(idOrganizacion, Parametro.DIA_CORTE_PAGO_HORAS_EXTRAS);
/* 663:    */   }
/* 664:    */   
/* 665:    */   public static Boolean getHorasAdelantoConsideroExtra(int idOrganizacion)
/* 666:    */   {
/* 667:677 */     return (Boolean)getParametro(idOrganizacion, Parametro.HORAS_ADELANTO_CONSIDERO_EXTRA);
/* 668:    */   }
/* 669:    */   
/* 670:    */   public static Boolean getPagarHorasExtrasCumplidoJornada(int idOrganizacion)
/* 671:    */   {
/* 672:682 */     return (Boolean)getParametro(idOrganizacion, Parametro.PAGAR_HORAS_EXTRAS_CUMPLIDO_JORNADA);
/* 673:    */   }
/* 674:    */   
/* 675:    */   public static Boolean getRecepcionUsaBalanza(int idOrganizacion)
/* 676:    */   {
/* 677:687 */     return (Boolean)getParametro(idOrganizacion, Parametro.RECEPCION_USA_BALANZA);
/* 678:    */   }
/* 679:    */   
/* 680:    */   public static Boolean getDespachoUsaBalanza(int idOrganizacion)
/* 681:    */   {
/* 682:692 */     return (Boolean)getParametro(idOrganizacion, Parametro.DESPACHO_USA_BALANZA);
/* 683:    */   }
/* 684:    */   
/* 685:    */   public static Boolean getProduccionUsaBalanza(int idOrganizacion)
/* 686:    */   {
/* 687:697 */     return (Boolean)getParametro(idOrganizacion, Parametro.PRODUCCION_USA_BALANZA);
/* 688:    */   }
/* 689:    */   
/* 690:    */   public static Boolean getTransferenciaUsaBalanza(int idOrganizacion)
/* 691:    */   {
/* 692:702 */     return (Boolean)getParametro(idOrganizacion, Parametro.TRANSFERENCIA_USA_BALANZA);
/* 693:    */   }
/* 694:    */   
/* 695:    */   public static Boolean getRecepcionTransferenciaUsaBalanza(int idOrganizacion)
/* 696:    */   {
/* 697:707 */     return (Boolean)getParametro(idOrganizacion, Parametro.RECEPCION_TRANSFERENCIA_USA_BALANZA);
/* 698:    */   }
/* 699:    */   
/* 700:    */   public static Boolean getAgregarDetalleDevolucionCliente(int idOrganizacion)
/* 701:    */   {
/* 702:712 */     return (Boolean)getParametro(idOrganizacion, Parametro.AGREGAR_DETALLE_DEVOLUCION_CLIENTE);
/* 703:    */   }
/* 704:    */   
/* 705:    */   public static Boolean getDespacharServicio(int idOrganizacion)
/* 706:    */   {
/* 707:717 */     return (Boolean)getParametro(idOrganizacion, Parametro.DESPACHAR_SERVICIO);
/* 708:    */   }
/* 709:    */   
/* 710:    */   public static Boolean getFiltrarSucursalInicioSesion(int idOrganizacion)
/* 711:    */   {
/* 712:722 */     return (Boolean)getParametro(idOrganizacion, Parametro.FILTRAR_SUCURSAL_INICIO_SESION);
/* 713:    */   }
/* 714:    */   
/* 715:    */   public static Boolean isReporteCierreCajaPorFactura(int idOrganizacion)
/* 716:    */   {
/* 717:727 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_REPORTE_CIERRE_CAJA_POR_FACTURA);
/* 718:    */   }
/* 719:    */   
/* 720:    */   public static Boolean isIndicadorListaPrecioPorZona(int idOrganizacion)
/* 721:    */   {
/* 722:732 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_LISTA_PRECIOS_POR_ZONA);
/* 723:    */   }
/* 724:    */   
/* 725:    */   public static Boolean isPagoCashProveedorShort(int idOrganizacion)
/* 726:    */   {
/* 727:737 */     return (Boolean)getParametro(idOrganizacion, Parametro.PAGO_CASH_PROVEEDOR_SHORT);
/* 728:    */   }
/* 729:    */   
/* 730:    */   public static Boolean isCuentaAlterna(int idOrganizacion)
/* 731:    */   {
/* 732:742 */     return (Boolean)getParametro(idOrganizacion, Parametro.CUENTA_ALTERNA);
/* 733:    */   }
/* 734:    */   
/* 735:    */   public static Boolean getGeneraAnticipoEnCobrosSuperioesAFacturas(int idOrganizacion)
/* 736:    */   {
/* 737:747 */     return (Boolean)getParametro(idOrganizacion, Parametro.COBROS_SUPERIORES_A_FACTURAS);
/* 738:    */   }
/* 739:    */   
/* 740:    */   public static Boolean getPublicarComprobantesFacturae(int idOrganizacion)
/* 741:    */   {
/* 742:752 */     return (Boolean)getParametro(idOrganizacion, Parametro.PUBLICAR_COMPROBANTES_FACTURAE);
/* 743:    */   }
/* 744:    */   
/* 745:    */   public static Boolean getIndicadorOrdenesPago(int idOrganizacion)
/* 746:    */   {
/* 747:757 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_ORDENES_DE_PAGO);
/* 748:    */   }
/* 749:    */   
/* 750:    */   public static Integer getIntervaloRefrescarPesoBalanza(int idOrganizacion)
/* 751:    */   {
/* 752:762 */     return (Integer)getParametro(idOrganizacion, Parametro.INTERVALO_REFRESCAR_PESO_BALANZA);
/* 753:    */   }
/* 754:    */   
/* 755:    */   public static Integer getIdProductoFlete(int idOrganizacion)
/* 756:    */   {
/* 757:767 */     return (Integer)getParametro(idOrganizacion, Parametro.PRODUCTO_FLETE);
/* 758:    */   }
/* 759:    */   
/* 760:    */   public static Boolean getIndicadorFacturarAlDespachar(int idOrganizacion)
/* 761:    */   {
/* 762:772 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_FACTURAR_AL_DESPACHAR);
/* 763:    */   }
/* 764:    */   
/* 765:    */   public static Boolean getXMLFacturacionElectronicaDescripcionProducto(int idOrganizacion)
/* 766:    */   {
/* 767:777 */     return (Boolean)getParametro(idOrganizacion, Parametro.XML_FACTURACION_ELECTRONICA_DESCRIPCION_PRODUCTO);
/* 768:    */   }
/* 769:    */   
/* 770:    */   public static Boolean getNotaCreditoFinancieraRequiereAprobacion(int idOrganizacion)
/* 771:    */   {
/* 772:782 */     return (Boolean)getParametro(idOrganizacion, Parametro.NOTA_CREDITO_REQUIERE_APROBACION);
/* 773:    */   }
/* 774:    */   
/* 775:    */   public static Boolean getOrdenFabricacionFormulacion(int idOrganizacion)
/* 776:    */   {
/* 777:787 */     return (Boolean)getParametro(idOrganizacion, Parametro.ORDEN_FABRICACION_FORMULACION);
/* 778:    */   }
/* 779:    */   
/* 780:    */   public static Boolean getEditarPrecioFacturaPedidoProveedor(int idOrganizacion)
/* 781:    */   {
/* 782:792 */     return (Boolean)getParametro(idOrganizacion, Parametro.EDITAR_PRECIO_FACTURA_PEDIDO_PROVEEDOR);
/* 783:    */   }
/* 784:    */   
/* 785:    */   public static Boolean getEditarAsientosAutomaticos(int idOrganizacion)
/* 786:    */   {
/* 787:797 */     return (Boolean)getParametro(idOrganizacion, Parametro.EDITAR_ASIENTOS_AUTOMATICOS);
/* 788:    */   }
/* 789:    */   
/* 790:    */   public static Boolean getMayorizarComprobantes(int idOrganizacion)
/* 791:    */   {
/* 792:801 */     return (Boolean)getParametro(idOrganizacion, Parametro.MAYORIZAR_COMPROBANTES);
/* 793:    */   }
/* 794:    */   
/* 795:    */   public static Integer getClasificadorPagoComisiones(int idOrganizacion)
/* 796:    */   {
/* 797:806 */     return (Integer)getParametro(idOrganizacion, Parametro.CLASIFICADOR_PAGO_COMISIONES);
/* 798:    */   }
/* 799:    */   
/* 800:    */   public static Boolean getRealizaNotasCreditoAFacturaNoAutorizada(int idOrganizacion)
/* 801:    */   {
/* 802:810 */     return (Boolean)getParametro(idOrganizacion, Parametro.REALIZAR_NOTAS_CREDITO_A_FACTURA_NO_AUTORIZADA);
/* 803:    */   }
/* 804:    */   
/* 805:    */   public static Boolean getManejaCiclosLargosProduccion(int idOrganizacion)
/* 806:    */   {
/* 807:815 */     return (Boolean)getParametro(idOrganizacion, Parametro.MANEJA_CICLOS_LARGOS_PRODUCCION);
/* 808:    */   }
/* 809:    */   
/* 810:    */   public static Boolean getRecibirServicio(int idOrganizacion)
/* 811:    */   {
/* 812:820 */     return (Boolean)getParametro(idOrganizacion, Parametro.RECIBIR_SERVICIO);
/* 813:    */   }
/* 814:    */   
/* 815:    */   public static Integer getCuentaImportacionTransito(int idOrganizacion)
/* 816:    */   {
/* 817:825 */     return (Integer)getParametro(idOrganizacion, Parametro.CUENTA_IMPORTACION_TRANSITO);
/* 818:    */   }
/* 819:    */   
/* 820:    */   public static Boolean getProcesaDiasFalta(int idOrganizacion)
/* 821:    */   {
/* 822:830 */     return (Boolean)getParametro(idOrganizacion, Parametro.PROCESA_DIAS_FALTA);
/* 823:    */   }
/* 824:    */   
/* 825:    */   public static Boolean isComprobantesElectronicosEnviarEmailGuardar(int idOrganizacion)
/* 826:    */   {
/* 827:835 */     return (Boolean)getParametro(idOrganizacion, Parametro.COMPROBANTES_ELECTRONICOS_ENVIAR_EMAIL_GUARDAR);
/* 828:    */   }
/* 829:    */   
/* 830:    */   public static Integer getCantidadComprobantesElectronicosSincronizacion(int idOrganizacion)
/* 831:    */   {
/* 832:840 */     return (Integer)getParametro(idOrganizacion, Parametro.CANTIDAD_COMPROBANTES_ELECTRONICOS_SINCRONIZACION);
/* 833:    */   }
/* 834:    */   
/* 835:    */   public static Boolean getContabilizacionVentas(int idOrganizacion)
/* 836:    */   {
/* 837:845 */     return (Boolean)getParametro(idOrganizacion, Parametro.CONTABILIZACION_AUTOMATICA_VENTAS);
/* 838:    */   }
/* 839:    */   
/* 840:    */   public static Boolean getContabilizacionConsumosBodega(int idOrganizacion)
/* 841:    */   {
/* 842:850 */     return (Boolean)getParametro(idOrganizacion, Parametro.CONTABILIZACION_AUTOMATICA_CONSUMOS_BODEGA);
/* 843:    */   }
/* 844:    */   
/* 845:    */   public static Boolean getContabilizacionDespachos(int idOrganizacion)
/* 846:    */   {
/* 847:855 */     return (Boolean)getParametro(idOrganizacion, Parametro.CONTABILIZACION_AUTOMATICA_DESPACHOS);
/* 848:    */   }
/* 849:    */   
/* 850:    */   public static Boolean isAnulaDespachoAlAnularFacturaClienteAgil(int idOrganizacion)
/* 851:    */   {
/* 852:859 */     return (Boolean)getParametro(idOrganizacion, Parametro.ANULAR_DESPACHO_AL_ANULAR_FACTURA_CLIENTE_AGIL);
/* 853:    */   }
/* 854:    */   
/* 855:    */   public static Boolean isContabilizaImportacionBasadaPresupuesto(int idOrganizacion)
/* 856:    */   {
/* 857:864 */     return (Boolean)getParametro(idOrganizacion, Parametro.CONTABILIZA_IMPORTACION_BASADA_PRESUPUESTO);
/* 858:    */   }
/* 859:    */   
/* 860:    */   public static Boolean isApruebarHorasExtra100Feriados(int idOrganizacion)
/* 861:    */   {
/* 862:869 */     return (Boolean)getParametro(idOrganizacion, Parametro.APROBAR_HORAS_EXTRA_100_FERIADOS);
/* 863:    */   }
/* 864:    */   
/* 865:    */   public static Boolean getCierreCajaPorDenominacionFormaCobro(int idOrganizacion)
/* 866:    */   {
/* 867:873 */     return (Boolean)getParametro(idOrganizacion, Parametro.CIERRE_CAJA_POR_DENOMINACION_FORMA_COBRO);
/* 868:    */   }
/* 869:    */   
/* 870:    */   public static Boolean getAprobarOrdenSalidaMaterial(int idOrganizacion)
/* 871:    */   {
/* 872:877 */     return (Boolean)getParametro(idOrganizacion, Parametro.APROBAR_ORDEN_SALIDA_MATERIAL);
/* 873:    */   }
/* 874:    */   
/* 875:    */   public static Integer getTiempoMarcacionValidaSalida(int idOrganizacion)
/* 876:    */   {
/* 877:881 */     return (Integer)getParametro(idOrganizacion, Parametro.TIEMPO_MARCACION_VALIDA_SALIDA);
/* 878:    */   }
/* 879:    */   
/* 880:    */   public static Integer getTiempoMarcacionValidaEntrada(int idOrganizacion)
/* 881:    */   {
/* 882:885 */     return (Integer)getParametro(idOrganizacion, Parametro.TIEMPO_MARCACION_VALIDA_ENTRADA);
/* 883:    */   }
/* 884:    */   
/* 885:    */   public static Boolean isRegistraReingresos(int idOrganizacion)
/* 886:    */   {
/* 887:889 */     return (Boolean)getParametro(idOrganizacion, Parametro.REGISTRA_REINGRESOS);
/* 888:    */   }
/* 889:    */   
/* 890:    */   public static Boolean getTotalizarPrecioYCantidad(int idOrganizacion)
/* 891:    */   {
/* 892:893 */     return (Boolean)getParametro(idOrganizacion, Parametro.TOTALIZAR_CANTIDAD_PRECIO);
/* 893:    */   }
/* 894:    */   
/* 895:    */   public static Integer getMesesSinConsumo(int idOrganizacion)
/* 896:    */   {
/* 897:897 */     return (Integer)getParametro(idOrganizacion, Parametro.MESES_SIN_CONSUMO);
/* 898:    */   }
/* 899:    */   
/* 900:    */   public static Boolean getDigitaCantidadAlRecibir(int idOrganizacion)
/* 901:    */   {
/* 902:901 */     return (Boolean)getParametro(idOrganizacion, Parametro.DIGITA_CANTIDAD_AL_RECIBIR);
/* 903:    */   }
/* 904:    */   
/* 905:    */   public static BigDecimal getCantidadFacturaClienteAgil(int idOrganizacion)
/* 906:    */   {
/* 907:905 */     return (BigDecimal)getParametro(idOrganizacion, Parametro.CANTIDAD_FACTURA_CLIENTE_AGIL);
/* 908:    */   }
/* 909:    */   
/* 910:    */   public static Boolean isManejaCodigoEmpresa(int idOrganizacion)
/* 911:    */   {
/* 912:909 */     return (Boolean)getParametro(idOrganizacion, Parametro.MANEJA_CODIGO_EMPRESA);
/* 913:    */   }
/* 914:    */   
/* 915:    */   public static Boolean isManejaDimesionPedidoProveedor(int idOrganizacion)
/* 916:    */   {
/* 917:913 */     return (Boolean)getParametro(idOrganizacion, Parametro.DIMENSION_PEDIDO_PROVEEDOR);
/* 918:    */   }
/* 919:    */   
/* 920:    */   public static Boolean isPermiteEliminacionIngresoProduccion(int idOrganizacion)
/* 921:    */   {
/* 922:917 */     return (Boolean)getParametro(idOrganizacion, Parametro.ELIMINAR_INGRESO_PRODUCCION);
/* 923:    */   }
/* 924:    */   
/* 925:    */   public static Boolean isOrdenCompraConPersonaResponsable(int idOrganizacion)
/* 926:    */   {
/* 927:921 */     return (Boolean)getParametro(idOrganizacion, Parametro.ORDEN_COMPRA_CON_PERSONA_RESPONSABLE);
/* 928:    */   }
/* 929:    */   
/* 930:    */   public static Boolean isCosteoSubOrdenes(int idOrganizacion)
/* 931:    */   {
/* 932:925 */     return (Boolean)getParametro(idOrganizacion, Parametro.COSTEO_SUBORDENES);
/* 933:    */   }
/* 934:    */   
/* 935:    */   public static Integer getEdadMinimaTerceraEdad(int idOrganizacion)
/* 936:    */   {
/* 937:929 */     return (Integer)getParametro(idOrganizacion, Parametro.EDAD_MINIMA_TERCERA_EDAD);
/* 938:    */   }
/* 939:    */   
/* 940:    */   public static Boolean getAprobacionParcialPedidoProveedor(int idOrganizacion)
/* 941:    */   {
/* 942:933 */     return (Boolean)getParametro(idOrganizacion, Parametro.APROBACION_PARCIAL_PEDIDO_PROVEEDOR);
/* 943:    */   }
/* 944:    */   
/* 945:    */   public static Integer getMeseAnalisisCalificacionCliente(int idOrganizacion)
/* 946:    */   {
/* 947:937 */     return (Integer)getParametro(idOrganizacion, Parametro.MESES_ANALISIS_CALIFICACION);
/* 948:    */   }
/* 949:    */   
/* 950:    */   public static Boolean isBloqueoProductoListaPrecios(int idOrganizacion)
/* 951:    */   {
/* 952:941 */     return (Boolean)getParametro(idOrganizacion, Parametro.VALIDAR_LISTA_DE_PRECIOS_EN_VENTAS);
/* 953:    */   }
/* 954:    */   
/* 955:    */   public static Boolean getAutorizacionPedidoPorCriterio(int idOrganizacion)
/* 956:    */   {
/* 957:945 */     return (Boolean)getParametro(idOrganizacion, Parametro.INDICADOR_AUTORIZACION_PEDIDO_POR_CRITERIOS);
/* 958:    */   }
/* 959:    */   
/* 960:    */   public static Boolean getVencimientoPorFechaRecepcion(int idOrganizacion)
/* 961:    */   {
/* 962:949 */     return (Boolean)getParametro(idOrganizacion, Parametro.VENCIMIENTO_POR_FECHA_RECEPCION);
/* 963:    */   }
/* 964:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.ParametrosSistema
 * JD-Core Version:    0.7.0.1
 */