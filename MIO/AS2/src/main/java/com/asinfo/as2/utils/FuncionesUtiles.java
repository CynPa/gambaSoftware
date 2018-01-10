/*    1:     */ package com.asinfo.as2.utils;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.clases.Celda;
/*    4:     */ import com.asinfo.as2.entities.Organizacion;
/*    5:     */ import com.asinfo.as2.enumeraciones.FormatoCelda;
/*    6:     */ import com.asinfo.as2.excepciones.AS2Exception;
/*    7:     */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*    8:     */ import com.asinfo.as2.util.AppUtil;
/*    9:     */ import com.asinfo.as2.utils.encriptacion.AESEncriptacion;
/*   10:     */ import com.asinfo.as2.utils.encriptacion.AESKey;
/*   11:     */ import java.io.BufferedInputStream;
/*   12:     */ import java.io.BufferedReader;
/*   13:     */ import java.io.BufferedWriter;
/*   14:     */ import java.io.File;
/*   15:     */ import java.io.FileInputStream;
/*   16:     */ import java.io.FileNotFoundException;
/*   17:     */ import java.io.FileOutputStream;
/*   18:     */ import java.io.FileReader;
/*   19:     */ import java.io.FileWriter;
/*   20:     */ import java.io.IOException;
/*   21:     */ import java.io.InputStream;
/*   22:     */ import java.io.InputStreamReader;
/*   23:     */ import java.io.OutputStreamWriter;
/*   24:     */ import java.lang.reflect.Method;
/*   25:     */ import java.math.BigDecimal;
/*   26:     */ import java.math.BigInteger;
/*   27:     */ import java.math.RoundingMode;
/*   28:     */ import java.text.Normalizer;
/*   29:     */ import java.text.Normalizer.Form;
/*   30:     */ import java.text.ParseException;
/*   31:     */ import java.text.SimpleDateFormat;
/*   32:     */ import java.util.ArrayList;
/*   33:     */ import java.util.Calendar;
/*   34:     */ import java.util.Collection;
/*   35:     */ import java.util.Collections;
/*   36:     */ import java.util.Comparator;
/*   37:     */ import java.util.Date;
/*   38:     */ import java.util.GregorianCalendar;
/*   39:     */ import java.util.Iterator;
/*   40:     */ import java.util.List;
/*   41:     */ import java.util.Locale;
/*   42:     */ import java.util.Properties;
/*   43:     */ import java.util.Random;
/*   44:     */ import java.util.StringTokenizer;
/*   45:     */ import java.util.Vector;
/*   46:     */ import java.util.regex.Matcher;
/*   47:     */ import java.util.regex.Pattern;
/*   48:     */ import javax.activation.MimetypesFileTypeMap;
/*   49:     */ import javax.faces.context.ExternalContext;
/*   50:     */ import javax.faces.context.FacesContext;
/*   51:     */ import javax.servlet.ServletOutputStream;
/*   52:     */ import javax.servlet.http.HttpServletResponse;
/*   53:     */ import net.sf.jasperreports.engine.JRDefaultScriptlet;
/*   54:     */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*   55:     */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*   56:     */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*   57:     */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*   58:     */ import org.apache.poi.ss.usermodel.Cell;
/*   59:     */ import org.apache.poi.ss.usermodel.CellStyle;
/*   60:     */ import org.apache.poi.ss.usermodel.CreationHelper;
/*   61:     */ import org.apache.poi.ss.usermodel.DataFormat;
/*   62:     */ import org.apache.poi.ss.usermodel.Font;
/*   63:     */ import org.apache.poi.ss.usermodel.IndexedColors;
/*   64:     */ import org.apache.poi.ss.usermodel.Row;
/*   65:     */ import org.apache.poi.ss.usermodel.Sheet;
/*   66:     */ import org.apache.poi.ss.usermodel.Workbook;
/*   67:     */ import org.apache.poi.xssf.usermodel.XSSFSheet;
/*   68:     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*   69:     */ import org.primefaces.event.FileUploadEvent;
/*   70:     */ import org.primefaces.model.DefaultStreamedContent;
/*   71:     */ import org.primefaces.model.UploadedFile;
/*   72:     */ 
/*   73:     */ public class FuncionesUtiles
/*   74:     */   extends JRDefaultScriptlet
/*   75:     */ {
/*   76:  90 */   private static final BigDecimal CIEN = new BigDecimal(100);
/*   77:     */   
/*   78:     */   public static String Mes(int mes)
/*   79:     */   {
/*   80: 100 */     String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciemrbre" };
/*   81:     */     
/*   82: 102 */     String retornaMes = " ";
/*   83: 103 */     retornaMes = meses[mes];
/*   84: 104 */     return retornaMes;
/*   85:     */   }
/*   86:     */   
/*   87:     */   public static double Redondear(double numero)
/*   88:     */   {
/*   89: 114 */     int cifras = (int)Math.pow(10.0D, 2.0D);
/*   90: 115 */     return Math.rint(numero * cifras) / cifras;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public static Date sumarFechaDiasMeses(Date fecha, int dias)
/*   94:     */   {
/*   95: 127 */     Calendar fechaCalendar = Calendar.getInstance();
/*   96: 128 */     fechaCalendar.setTime(fecha);
/*   97: 129 */     fechaCalendar.add(5, dias);
/*   98: 130 */     return fechaCalendar.getTime();
/*   99:     */   }
/*  100:     */   
/*  101:     */   public static Date sumarFechaMeses(Date fecha, int mes)
/*  102:     */   {
/*  103: 142 */     Calendar fechaCalendar = Calendar.getInstance();
/*  104: 143 */     fechaCalendar.setTime(fecha);
/*  105: 144 */     fechaCalendar.add(2, mes);
/*  106: 145 */     return fechaCalendar.getTime();
/*  107:     */   }
/*  108:     */   
/*  109:     */   public static Date sumarFechaAnios(Date fecha, int anio)
/*  110:     */   {
/*  111: 157 */     Calendar fechaCalendar = Calendar.getInstance();
/*  112: 158 */     fechaCalendar.setTime(fecha);
/*  113: 159 */     fechaCalendar.add(1, anio);
/*  114: 160 */     return fechaCalendar.getTime();
/*  115:     */   }
/*  116:     */   
/*  117:     */   public static Date sumarFechaDiasMeses(Date fecha, int meses, int dias)
/*  118:     */   {
/*  119: 173 */     Calendar fechaCalendar = Calendar.getInstance();
/*  120: 174 */     fechaCalendar.setTime(fecha);
/*  121: 175 */     fechaCalendar.add(5, dias);
/*  122: 176 */     fechaCalendar.add(2, meses);
/*  123: 177 */     return fechaCalendar.getTime();
/*  124:     */   }
/*  125:     */   
/*  126:     */   public static Date sumarFechaDiasMeses(Date fecha, int meses, int dias, int anios)
/*  127:     */   {
/*  128: 191 */     Calendar fechaCalendar = Calendar.getInstance();
/*  129: 192 */     fechaCalendar.setTime(fecha);
/*  130: 193 */     fechaCalendar.add(5, dias);
/*  131: 194 */     fechaCalendar.add(2, meses);
/*  132: 195 */     fechaCalendar.add(1, anios);
/*  133: 196 */     return fechaCalendar.getTime();
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static boolean compararFechas(Date fecha1, Date fecha2)
/*  137:     */   {
/*  138: 207 */     return fecha1.before(fecha2);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public static boolean compararFechaAnteriorOIgual(Date fecha1, Date fecha2)
/*  142:     */   {
/*  143: 218 */     return (fecha1.before(fecha2)) || (fecha1.equals(fecha2));
/*  144:     */   }
/*  145:     */   
/*  146:     */   public static BigDecimal redondearBigDecimal(BigDecimal numero)
/*  147:     */   {
/*  148: 228 */     return redondearBigDecimal(numero, 2);
/*  149:     */   }
/*  150:     */   
/*  151:     */   public static BigDecimal redondearBigDecimal(BigDecimal numero, int numeroDecimales)
/*  152:     */   {
/*  153: 239 */     BigDecimal resp = null;
/*  154: 240 */     resp = numero.setScale(numeroDecimales, RoundingMode.HALF_UP);
/*  155: 241 */     return resp;
/*  156:     */   }
/*  157:     */   
/*  158:     */   public static Date obtenerFechaFinal()
/*  159:     */   {
/*  160: 250 */     Calendar calendar = Calendar.getInstance();
/*  161: 251 */     calendar.set(9999, 11, 31, 0, 0, 0);
/*  162: 252 */     calendar.set(14, 0);
/*  163: 253 */     Date date = calendar.getTime();
/*  164: 254 */     return date;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public static Date obtenerFechaInicial()
/*  168:     */   {
/*  169: 263 */     Calendar calendar = Calendar.getInstance();
/*  170: 264 */     calendar.set(1900, 0, 1);
/*  171: 265 */     Date date = calendar.getTime();
/*  172: 266 */     return date;
/*  173:     */   }
/*  174:     */   
/*  175:     */   @Deprecated
/*  176:     */   public static Date obtenerFechaCualquiera(int day, int month, int year)
/*  177:     */   {
/*  178: 280 */     Calendar calendar = Calendar.getInstance();
/*  179: 281 */     calendar.set(year, month - 1, day);
/*  180: 282 */     Date date = calendar.getTime();
/*  181: 283 */     return date;
/*  182:     */   }
/*  183:     */   
/*  184:     */   public static Date getFecha(int day, int month, int year)
/*  185:     */   {
/*  186: 296 */     Calendar calendar = Calendar.getInstance();
/*  187: 297 */     calendar.set(year, month - 1, day, 0, 0, 0);
/*  188: 298 */     calendar.set(14, 0);
/*  189: 299 */     Date date = calendar.getTime();
/*  190: 300 */     return date;
/*  191:     */   }
/*  192:     */   
/*  193:     */   @Deprecated
/*  194:     */   public static Date obtenerUltimoDiaMes(int year, int month)
/*  195:     */   {
/*  196: 313 */     Calendar calendar = Calendar.getInstance();
/*  197: 314 */     calendar.set(1, year);
/*  198: 315 */     calendar.set(2, month - 1);
/*  199: 316 */     calendar.set(5, 1);
/*  200:     */     
/*  201: 318 */     calendar.add(2, 1);
/*  202: 319 */     calendar.add(5, -1);
/*  203:     */     
/*  204: 321 */     return calendar.getTime();
/*  205:     */   }
/*  206:     */   
/*  207:     */   public static Date getFechaFinMes(int anio, int mes)
/*  208:     */   {
/*  209: 332 */     Calendar calendar = Calendar.getInstance();
/*  210: 333 */     calendar.set(anio, mes - 1, 1, 0, 0, 0);
/*  211: 334 */     calendar.set(14, 0);
/*  212: 335 */     return getFechaFinMes(calendar.getTime());
/*  213:     */   }
/*  214:     */   
/*  215:     */   public static Date getFechaFinMes(Date fecha)
/*  216:     */   {
/*  217: 345 */     Calendar fechaFinMes = Calendar.getInstance();
/*  218: 346 */     fechaFinMes.setTime(fecha);
/*  219:     */     
/*  220: 348 */     int anio = fechaFinMes.get(1);
/*  221: 349 */     int mes = fechaFinMes.get(2);
/*  222:     */     
/*  223:     */ 
/*  224:     */ 
/*  225:     */ 
/*  226: 354 */     fechaFinMes.set(anio, mes, 1, 0, 0, 0);
/*  227: 355 */     fechaFinMes.set(14, 0);
/*  228:     */     
/*  229:     */ 
/*  230:     */ 
/*  231:     */ 
/*  232: 360 */     fechaFinMes.add(2, 1);
/*  233:     */     
/*  234:     */ 
/*  235:     */ 
/*  236: 364 */     fechaFinMes.add(5, -1);
/*  237:     */     
/*  238: 366 */     return fechaFinMes.getTime();
/*  239:     */   }
/*  240:     */   
/*  241:     */   public static Date getFechaProximoDiaSemana(Date fecha, int diaSemana)
/*  242:     */   {
/*  243: 371 */     Calendar fechaProximoDiaSemana = Calendar.getInstance();
/*  244: 372 */     fechaProximoDiaSemana.setTime(fecha);
/*  245: 375 */     while (fechaProximoDiaSemana.get(7) != diaSemana) {
/*  246: 378 */       fechaProximoDiaSemana.add(5, 1);
/*  247:     */     }
/*  248: 382 */     return fechaProximoDiaSemana.getTime();
/*  249:     */   }
/*  250:     */   
/*  251:     */   public static int getDiaFinMes(Calendar fecha)
/*  252:     */   {
/*  253: 386 */     Calendar fechaFinMes = (Calendar)fecha.clone();
/*  254: 387 */     int anio = fechaFinMes.get(1);
/*  255: 388 */     int mes = fechaFinMes.get(2);
/*  256:     */     
/*  257:     */ 
/*  258:     */ 
/*  259:     */ 
/*  260: 393 */     fechaFinMes.set(anio, mes, 1, 0, 0, 0);
/*  261: 394 */     fechaFinMes.set(14, 0);
/*  262:     */     
/*  263:     */ 
/*  264:     */ 
/*  265:     */ 
/*  266: 399 */     fechaFinMes.add(2, 1);
/*  267:     */     
/*  268:     */ 
/*  269:     */ 
/*  270: 403 */     fechaFinMes.add(5, -1);
/*  271:     */     
/*  272: 405 */     return fechaFinMes.get(5);
/*  273:     */   }
/*  274:     */   
/*  275:     */   public static BigDecimal getProporcianalDiasAnterioresVSMes(Date fecha)
/*  276:     */   {
/*  277: 409 */     Calendar fechaCalendar = Calendar.getInstance();
/*  278: 410 */     fechaCalendar.setTime(fecha);
/*  279: 411 */     int diasAnteriores = fechaCalendar.get(5);
/*  280: 412 */     int mes = fechaCalendar.get(2);
/*  281: 413 */     int diasMes = 30;
/*  282: 414 */     if (mes == 1)
/*  283:     */     {
/*  284: 415 */       Date ultiDiaMes = getFechaFinMes(fecha);
/*  285: 416 */       Calendar ultiDiaMesCalendar = Calendar.getInstance();
/*  286:     */       
/*  287: 418 */       ultiDiaMesCalendar.setTime(ultiDiaMes);
/*  288: 419 */       diasMes = ultiDiaMesCalendar.get(5);
/*  289:     */     }
/*  290: 422 */     BigDecimal proporcional = new BigDecimal(diasAnteriores).divide(new BigDecimal(diasMes), 2, RoundingMode.HALF_UP);
/*  291: 423 */     if (proporcional.compareTo(BigDecimal.ONE) == 1) {
/*  292: 424 */       proporcional = BigDecimal.ONE;
/*  293:     */     }
/*  294: 426 */     return proporcional;
/*  295:     */   }
/*  296:     */   
/*  297:     */   public static BigDecimal getProporcianalDiasPosterioresVSMes(Date fecha)
/*  298:     */   {
/*  299: 430 */     Calendar fechaCalendar = Calendar.getInstance();
/*  300: 431 */     fechaCalendar.setTime(fecha);
/*  301: 432 */     int diasAnteriores = fechaCalendar.get(5);
/*  302: 433 */     int mes = fechaCalendar.get(2);
/*  303: 434 */     int diasMes = 30;
/*  304: 435 */     if (mes == 1)
/*  305:     */     {
/*  306: 436 */       Date ultiDiaMes = getFechaFinMes(fecha);
/*  307: 437 */       Calendar ultiDiaMesCalendar = Calendar.getInstance();
/*  308:     */       
/*  309: 439 */       ultiDiaMesCalendar.setTime(ultiDiaMes);
/*  310: 440 */       diasMes = ultiDiaMesCalendar.get(5);
/*  311:     */     }
/*  312: 442 */     int diasPosteriores = diasMes - diasAnteriores + 1;
/*  313:     */     
/*  314: 444 */     BigDecimal proporcional = new BigDecimal(diasPosteriores).divide(new BigDecimal(diasMes), 2, RoundingMode.HALF_UP);
/*  315: 445 */     return proporcional;
/*  316:     */   }
/*  317:     */   
/*  318:     */   public static Date getFechaInicioMes(Date fecha)
/*  319:     */   {
/*  320: 449 */     Calendar fechaInicioMes = Calendar.getInstance();
/*  321: 450 */     fechaInicioMes.setTime(fecha);
/*  322:     */     
/*  323: 452 */     int anio = fechaInicioMes.get(1);
/*  324: 453 */     int mes = fechaInicioMes.get(2);
/*  325:     */     
/*  326:     */ 
/*  327:     */ 
/*  328:     */ 
/*  329: 458 */     fechaInicioMes.set(anio, mes, 1, 0, 0, 0);
/*  330: 459 */     fechaInicioMes.set(14, 0);
/*  331:     */     
/*  332: 461 */     return fechaInicioMes.getTime();
/*  333:     */   }
/*  334:     */   
/*  335:     */   public static void ordenaLista(List lista, String propiedad)
/*  336:     */   {
/*  337: 472 */     ordenaLista(lista, propiedad, true);
/*  338:     */   }
/*  339:     */   
/*  340:     */   public static void ordenaLista(List lista, String propiedad, final boolean order)
/*  341:     */   {
/*  342: 484 */     Collections.sort(lista, new Comparator()
/*  343:     */     {
/*  344:     */       public int compare(Object obj1, Object obj2)
/*  345:     */       {
/*  346: 488 */         Class clase = obj1.getClass();
/*  347: 489 */         String getter = "get" + Character.toUpperCase(this.val$propiedad.charAt(0)) + this.val$propiedad.substring(1);
/*  348:     */         try
/*  349:     */         {
/*  350: 491 */           Method getPropiedad = clase.getMethod(getter, new Class[0]);
/*  351:     */           
/*  352: 493 */           Object propiedad1 = getPropiedad.invoke(obj1, new Object[0]);
/*  353: 494 */           Object propiedad2 = getPropiedad.invoke(obj2, new Object[0]);
/*  354: 496 */           if (((propiedad1 instanceof Comparable)) && ((propiedad2 instanceof Comparable)))
/*  355:     */           {
/*  356: 497 */             Comparable prop1 = (Comparable)propiedad1;
/*  357: 498 */             Comparable prop2 = (Comparable)propiedad2;
/*  358: 499 */             if (order) {
/*  359: 500 */               return prop1.compareTo(prop2);
/*  360:     */             }
/*  361: 502 */             return prop2.compareTo(prop1);
/*  362:     */           }
/*  363: 506 */           if (propiedad1.equals(propiedad2)) {
/*  364: 507 */             return 0;
/*  365:     */           }
/*  366: 509 */           return 1;
/*  367:     */         }
/*  368:     */         catch (Exception localException) {}
/*  369: 516 */         return 0;
/*  370:     */       }
/*  371:     */     });
/*  372:     */   }
/*  373:     */   
/*  374:     */   public static String nombreMes(int numeroMes)
/*  375:     */   {
/*  376: 529 */     String nombreMes = null;
/*  377: 530 */     switch (numeroMes)
/*  378:     */     {
/*  379:     */     case 0: 
/*  380: 532 */       nombreMes = "Enero";
/*  381: 533 */       break;
/*  382:     */     case 1: 
/*  383: 535 */       nombreMes = "Febrero";
/*  384: 536 */       break;
/*  385:     */     case 2: 
/*  386: 538 */       nombreMes = "Marzo";
/*  387: 539 */       break;
/*  388:     */     case 3: 
/*  389: 541 */       nombreMes = "Abril";
/*  390: 542 */       break;
/*  391:     */     case 4: 
/*  392: 544 */       nombreMes = "Mayo";
/*  393: 545 */       break;
/*  394:     */     case 5: 
/*  395: 547 */       nombreMes = "Junio";
/*  396: 548 */       break;
/*  397:     */     case 6: 
/*  398: 550 */       nombreMes = "Julio";
/*  399: 551 */       break;
/*  400:     */     case 7: 
/*  401: 553 */       nombreMes = "Agosto";
/*  402: 554 */       break;
/*  403:     */     case 8: 
/*  404: 556 */       nombreMes = "Septiembre";
/*  405: 557 */       break;
/*  406:     */     case 9: 
/*  407: 559 */       nombreMes = "Octubre";
/*  408: 560 */       break;
/*  409:     */     case 10: 
/*  410: 562 */       nombreMes = "Noviembre";
/*  411: 563 */       break;
/*  412:     */     case 11: 
/*  413: 565 */       nombreMes = "Diciembre";
/*  414: 566 */       break;
/*  415:     */     }
/*  416: 570 */     return nombreMes;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public static int diferenciasDeFechas(Date fechaInicial, Date fechaFinal)
/*  420:     */   {
/*  421: 581 */     long fechaInicialMs = fechaInicial.getTime();
/*  422: 582 */     long fechaFinalMs = fechaFinal.getTime();
/*  423: 583 */     long diferencia = fechaFinalMs - fechaInicialMs;
/*  424: 584 */     double dias = Math.floor(diferencia / 86400000L);
/*  425: 585 */     return (int)dias + 1;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public static int diferenciasDeFechas30Dias(Date fecha1, Date fecha2)
/*  429:     */   {
/*  430: 595 */     Calendar cFecha1 = Calendar.getInstance();
/*  431: 596 */     cFecha1.setTime(fecha1);
/*  432: 597 */     Calendar cFecha2 = Calendar.getInstance();
/*  433: 598 */     cFecha2.setTime(fecha2);
/*  434:     */     
/*  435: 600 */     int anios = cFecha2.get(1) - cFecha1.get(1);
/*  436:     */     
/*  437: 602 */     int mesFin = cFecha2.get(2);
/*  438: 603 */     int mesIni = cFecha1.get(2);
/*  439:     */     
/*  440: 605 */     int meses = mesFin - mesIni;
/*  441:     */     
/*  442: 607 */     int dia1 = 0;int dia2 = 0;
/*  443: 608 */     boolean fecha2FinMes = cFecha2.get(5) == cFecha2.getActualMaximum(5);
/*  444:     */     
/*  445: 610 */     dia1 = cFecha1.get(5) - 1;
/*  446: 612 */     if ((anios == 0) && (mesIni == mesFin) && (fecha2FinMes)) {
/*  447: 613 */       dia2 = 30;
/*  448:     */     } else {
/*  449: 615 */       dia2 = cFecha2.get(5) > 30 ? 30 : cFecha2.get(5);
/*  450:     */     }
/*  451: 618 */     int dias = 0;
/*  452: 619 */     if ((anios == 0) && (mesIni == mesFin)) {
/*  453: 620 */       dias = dia2 - dia1;
/*  454:     */     } else {
/*  455: 622 */       dias = -dia1 + dia2;
/*  456:     */     }
/*  457: 625 */     return anios * 360 + meses * 30 + dias;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public static int getMinutosEntreFechas(Date fechaInicial, Date fechaFinal)
/*  461:     */   {
/*  462: 661 */     long fechaInicialMs = fechaInicial.getTime();
/*  463: 662 */     long fechaFinalMs = fechaFinal.getTime();
/*  464: 663 */     long diferencia = fechaFinalMs - fechaInicialMs;
/*  465: 664 */     double minutos = diferencia / 1000L / 60L;
/*  466: 665 */     return (int)minutos;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public static String dateToString(Date fecha)
/*  470:     */   {
/*  471: 677 */     SimpleDateFormat formatoFecha = new SimpleDateFormat(ParametrosSistema.getFormatoFecha());
/*  472:     */     
/*  473: 679 */     return formatoFecha.format(fecha);
/*  474:     */   }
/*  475:     */   
/*  476:     */   public static Date stringToDate(String fecha)
/*  477:     */   {
/*  478: 689 */     String formatoFecha = ParametrosSistema.getFormatoFecha();
/*  479: 690 */     return stringToDate(fecha, formatoFecha);
/*  480:     */   }
/*  481:     */   
/*  482:     */   public static Date stringToDate(String fecha, String strFormatoFecha)
/*  483:     */   {
/*  484: 700 */     Date fecha_salida = null;
/*  485: 701 */     SimpleDateFormat formatoFecha = new SimpleDateFormat(strFormatoFecha);
/*  486:     */     try
/*  487:     */     {
/*  488: 704 */       fecha_salida = formatoFecha.parse(fecha);
/*  489:     */     }
/*  490:     */     catch (ParseException e)
/*  491:     */     {
/*  492: 706 */       e.printStackTrace();
/*  493:     */     }
/*  494: 709 */     return fecha_salida;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public static String obtenerSubcadena(String cadena, int valorInicial, int valorFinal)
/*  498:     */   {
/*  499: 721 */     return cadena.substring(valorInicial, valorFinal);
/*  500:     */   }
/*  501:     */   
/*  502:     */   public static String convertidorFechaALetras(Date fecha)
/*  503:     */   {
/*  504: 725 */     return convertidorFechaALetras(fecha, true);
/*  505:     */   }
/*  506:     */   
/*  507:     */   public static String convertidorFechaALetras(Date fecha, boolean withYear)
/*  508:     */   {
/*  509: 736 */     String pattern = withYear ? "dd 'de' MMMM 'de' yyyy" : "dd 'de' MMMM";
/*  510: 737 */     SimpleDateFormat formateador = new SimpleDateFormat(pattern, new Locale("es", "ES"));
/*  511: 738 */     String fechaLetras = formateador.format(fecha);
/*  512: 739 */     return fechaLetras;
/*  513:     */   }
/*  514:     */   
/*  515:     */   public static String convertidorFechaALetrasHoras(Date fecha)
/*  516:     */   {
/*  517: 750 */     SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
/*  518: 751 */     String fechaLetras = formateador.format(fecha);
/*  519: 752 */     return fechaLetras.concat(" " + fecha.getHours() + ":" + fecha.getMinutes() + ":" + fecha.getSeconds());
/*  520:     */   }
/*  521:     */   
/*  522: 759 */   private final String[] UNIDADES = { "", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve " };
/*  523: 760 */   private final String[] DECENAS = { "diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ", "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ", "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa " };
/*  524: 762 */   private final String[] CENTENAS = { "", "ciento ", "doscientos ", "trescientos ", "cuatrocientos ", "quinientos ", "seiscientos ", "setecientos ", "ochocientos ", "novecientos " };
/*  525:     */   
/*  526:     */   public String convertidorNumeroALetras(BigDecimal numero, boolean mayusculas)
/*  527:     */   {
/*  528: 783 */     String strNnumero = "" + numero.doubleValue();
/*  529:     */     
/*  530: 785 */     String literal = "";
/*  531:     */     
/*  532:     */ 
/*  533: 788 */     strNnumero = strNnumero.replace(".", ",");
/*  534: 790 */     if (strNnumero.indexOf(",") == -1) {
/*  535: 791 */       strNnumero = strNnumero + ",00";
/*  536:     */     }
/*  537: 794 */     if (Pattern.matches("\\d{1,9},\\d{1,2}", strNnumero))
/*  538:     */     {
/*  539: 796 */       String[] Num = strNnumero.split(",");
/*  540:     */       String parte_decimal;
/*  541:     */       String parte_decimal;
/*  542: 798 */       if (Num[1].length() == 1) {
/*  543: 799 */         parte_decimal = " con " + Num[1] + "0" + "/100";
/*  544:     */       } else {
/*  545: 801 */         parte_decimal = " con " + Num[1] + "/100";
/*  546:     */       }
/*  547: 804 */       if (Integer.parseInt(Num[0]) == 0) {
/*  548: 805 */         literal = "cero ";
/*  549: 806 */       } else if (Integer.parseInt(Num[0]) > 999999) {
/*  550: 808 */         literal = getMillones(Num[0]);
/*  551: 809 */       } else if (Integer.parseInt(Num[0]) > 999) {
/*  552: 811 */         literal = getMiles(Num[0]);
/*  553: 812 */       } else if (Integer.parseInt(Num[0]) > 99) {
/*  554: 814 */         literal = getCentenas(Num[0]);
/*  555: 815 */       } else if (Integer.parseInt(Num[0]) > 9) {
/*  556: 817 */         literal = getDecenas(Num[0]);
/*  557:     */       } else {
/*  558: 820 */         literal = getUnidades(Num[0]);
/*  559:     */       }
/*  560: 823 */       if (mayusculas) {
/*  561: 824 */         return (literal + parte_decimal).toUpperCase();
/*  562:     */       }
/*  563: 826 */       return literal + parte_decimal;
/*  564:     */     }
/*  565: 830 */     return literal = null;
/*  566:     */   }
/*  567:     */   
/*  568:     */   private String getUnidades(String numero)
/*  569:     */   {
/*  570: 837 */     String num = numero.substring(numero.length() - 1);
/*  571: 838 */     return this.UNIDADES[Integer.parseInt(num)];
/*  572:     */   }
/*  573:     */   
/*  574:     */   private String getDecenas(String num)
/*  575:     */   {
/*  576: 843 */     int n = Integer.parseInt(num);
/*  577: 844 */     if (n < 10) {
/*  578: 846 */       return getUnidades(num);
/*  579:     */     }
/*  580: 847 */     if (n > 19)
/*  581:     */     {
/*  582: 849 */       String u = getUnidades(num);
/*  583: 850 */       if (u.equals("")) {
/*  584: 852 */         return this.DECENAS[(Integer.parseInt(num.substring(0, 1)) + 8)];
/*  585:     */       }
/*  586: 854 */       return this.DECENAS[(Integer.parseInt(num.substring(0, 1)) + 8)] + "y " + u;
/*  587:     */     }
/*  588: 858 */     return this.DECENAS[(n - 10)];
/*  589:     */   }
/*  590:     */   
/*  591:     */   private String getCentenas(String num)
/*  592:     */   {
/*  593: 864 */     if (Integer.parseInt(num) > 99)
/*  594:     */     {
/*  595: 866 */       if (Integer.parseInt(num) == 100) {
/*  596: 868 */         return " cien ";
/*  597:     */       }
/*  598: 870 */       return this.CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
/*  599:     */     }
/*  600: 875 */     return getDecenas(Integer.parseInt(num) + "");
/*  601:     */   }
/*  602:     */   
/*  603:     */   private String getMiles(String numero)
/*  604:     */   {
/*  605: 882 */     String c = numero.substring(numero.length() - 3);
/*  606:     */     
/*  607: 884 */     String m = numero.substring(0, numero.length() - 3);
/*  608: 885 */     String n = "";
/*  609: 887 */     if (Integer.parseInt(m) > 0)
/*  610:     */     {
/*  611: 888 */       n = getCentenas(m);
/*  612: 889 */       return n + "mil " + getCentenas(c);
/*  613:     */     }
/*  614: 891 */     return "" + getCentenas(c);
/*  615:     */   }
/*  616:     */   
/*  617:     */   private String getMillones(String numero)
/*  618:     */   {
/*  619: 899 */     String miles = numero.substring(numero.length() - 6);
/*  620:     */     
/*  621: 901 */     String millon = numero.substring(0, numero.length() - 6);
/*  622: 902 */     String n = "";
/*  623: 903 */     if (millon.length() > 1) {
/*  624: 904 */       n = getCentenas(millon) + "millones ";
/*  625:     */     } else {
/*  626: 906 */       n = getUnidades(millon) + "millon ";
/*  627:     */     }
/*  628: 908 */     return n + getMiles(miles);
/*  629:     */   }
/*  630:     */   
/*  631:     */   public static int obtenerAnioActual()
/*  632:     */   {
/*  633: 921 */     Calendar fechaCalendar = Calendar.getInstance();
/*  634: 922 */     return fechaCalendar.get(1);
/*  635:     */   }
/*  636:     */   
/*  637:     */   public static int obtenerMesActual()
/*  638:     */   {
/*  639: 931 */     Calendar fechaCalendar = Calendar.getInstance();
/*  640: 932 */     return fechaCalendar.get(2);
/*  641:     */   }
/*  642:     */   
/*  643:     */   @Deprecated
/*  644:     */   public static int obtenerAnioFecha(Date fecha)
/*  645:     */   {
/*  646: 943 */     Calendar fechaCalendar = Calendar.getInstance();
/*  647: 944 */     fechaCalendar.setTime(fecha);
/*  648: 945 */     return fechaCalendar.get(1);
/*  649:     */   }
/*  650:     */   
/*  651:     */   public static int getAnio(Date fecha)
/*  652:     */   {
/*  653: 955 */     Calendar fechaCalendar = Calendar.getInstance();
/*  654: 956 */     fechaCalendar.setTime(fecha);
/*  655: 957 */     return fechaCalendar.get(1);
/*  656:     */   }
/*  657:     */   
/*  658:     */   @Deprecated
/*  659:     */   public static int obtenerMesFecha(Date fecha)
/*  660:     */   {
/*  661: 968 */     Calendar fechaCalendar = Calendar.getInstance();
/*  662: 969 */     fechaCalendar.setTime(fecha);
/*  663: 970 */     return fechaCalendar.get(2);
/*  664:     */   }
/*  665:     */   
/*  666:     */   public static int getMes(Date fecha)
/*  667:     */   {
/*  668: 974 */     Calendar fechaCalendar = Calendar.getInstance();
/*  669: 975 */     fechaCalendar.setTime(fecha);
/*  670: 976 */     return fechaCalendar.get(2) + 1;
/*  671:     */   }
/*  672:     */   
/*  673:     */   public static int getDiaFecha(Date fecha)
/*  674:     */   {
/*  675: 980 */     Calendar fechaCalendar = Calendar.getInstance();
/*  676: 981 */     fechaCalendar.setTime(fecha);
/*  677: 982 */     return fechaCalendar.get(5);
/*  678:     */   }
/*  679:     */   
/*  680:     */   public static int obtenerDiaFecha(Date fecha)
/*  681:     */   {
/*  682: 992 */     Calendar fechaCalendar = Calendar.getInstance();
/*  683: 993 */     fechaCalendar.setTime(fecha);
/*  684: 994 */     return fechaCalendar.get(5);
/*  685:     */   }
/*  686:     */   
/*  687:     */   public static Date obtenerMaximaFechaLista(List<Date> fechas)
/*  688:     */   {
/*  689: 998 */     Date fechaMaxima = (Date)fechas.get(0);
/*  690: 999 */     for (int i = 1; i < fechas.size(); i++) {
/*  691:1000 */       fechaMaxima = obtenerFechaMaxima(fechaMaxima, (Date)fechas.get(i));
/*  692:     */     }
/*  693:1002 */     return fechaMaxima;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public static Date obtenerFechaMaxima(Date d1, Date d2)
/*  697:     */   {
/*  698:1006 */     if ((d1 == null) && (d2 == null)) {
/*  699:1007 */       return null;
/*  700:     */     }
/*  701:1008 */     if (d1 == null) {
/*  702:1009 */       return d2;
/*  703:     */     }
/*  704:1010 */     if (d2 == null) {
/*  705:1011 */       return d1;
/*  706:     */     }
/*  707:1012 */     return d1.after(d2) ? d1 : d2;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public static Date obtenerMinimaFechaLista(List<Date> fechas)
/*  711:     */   {
/*  712:1016 */     Date fechaMinima = (Date)fechas.get(0);
/*  713:1017 */     for (int i = 1; i < fechas.size(); i++) {
/*  714:1018 */       fechaMinima = obtenerFechaMinima(fechaMinima, (Date)fechas.get(i));
/*  715:     */     }
/*  716:1020 */     return fechaMinima;
/*  717:     */   }
/*  718:     */   
/*  719:     */   public static Date obtenerFechaMinima(Date d1, Date d2)
/*  720:     */   {
/*  721:1024 */     if ((d1 == null) && (d2 == null)) {
/*  722:1025 */       return null;
/*  723:     */     }
/*  724:1026 */     if (d1 == null) {
/*  725:1027 */       return d2;
/*  726:     */     }
/*  727:1028 */     if (d2 == null) {
/*  728:1029 */       return d1;
/*  729:     */     }
/*  730:1030 */     return d1.before(d2) ? d1 : d2;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public static int obtenerEdad(Date fechaNac)
/*  734:     */   {
/*  735:1041 */     Calendar fechaActual = Calendar.getInstance();
/*  736:1042 */     fechaActual.setTime(new Date());
/*  737:     */     
/*  738:1044 */     Calendar fechaNacimiento = Calendar.getInstance();
/*  739:1045 */     fechaNacimiento.setTime(fechaNac);
/*  740:     */     
/*  741:1047 */     int dif_anios = fechaActual.get(1) - fechaNacimiento.get(1);
/*  742:1048 */     int dif_meses = fechaActual.get(2) - fechaNacimiento.get(2);
/*  743:1049 */     int dif_dias = fechaActual.get(5) - fechaNacimiento.get(5);
/*  744:1052 */     if ((dif_meses < 0) || ((dif_meses == 0) && (dif_dias < 0))) {
/*  745:1053 */       dif_anios--;
/*  746:     */     }
/*  747:1055 */     return dif_anios;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public static long DiasEntreFechas(Date fecha1, Date fecha2)
/*  751:     */   {
/*  752:1061 */     Calendar cal1 = Calendar.getInstance();
/*  753:1062 */     cal1.setTime(fecha1);
/*  754:1063 */     cal1.set(10, 0);
/*  755:1064 */     cal1.set(12, 0);
/*  756:1065 */     cal1.set(13, 0);
/*  757:1066 */     cal1.set(14, 0);
/*  758:1067 */     Calendar cal2 = Calendar.getInstance();
/*  759:1068 */     cal2.setTime(fecha2);
/*  760:1069 */     cal2.set(10, 0);
/*  761:1070 */     cal2.set(12, 0);
/*  762:1071 */     cal2.set(13, 0);
/*  763:1072 */     cal2.set(14, 0);
/*  764:     */     
/*  765:     */ 
/*  766:1075 */     long milis1 = cal1.getTimeInMillis();
/*  767:1076 */     long milis2 = cal2.getTimeInMillis();
/*  768:     */     
/*  769:     */ 
/*  770:1079 */     long diff = milis2 - milis1;
/*  771:1080 */     diff /= 86400000L;
/*  772:     */     
/*  773:1082 */     return diff + 1L;
/*  774:     */   }
/*  775:     */   
/*  776:     */   public static Date setAtributoFecha(int anio, int mes, int dia)
/*  777:     */   {
/*  778:1097 */     Calendar calendar = Calendar.getInstance();
/*  779:1098 */     calendar.set(anio, mes, dia, 0, 0, 0);
/*  780:1099 */     calendar.set(14, 0);
/*  781:1100 */     return calendar.getTime();
/*  782:     */   }
/*  783:     */   
/*  784:     */   public static Date setAtributoFecha(Date fecha)
/*  785:     */   {
/*  786:1110 */     Calendar calendar = Calendar.getInstance();
/*  787:1111 */     calendar.setTime(fecha);
/*  788:1112 */     int anio = calendar.get(1);
/*  789:1113 */     int mes = calendar.get(2);
/*  790:1114 */     int dia = calendar.get(5);
/*  791:1115 */     return setAtributoFecha(anio, mes, dia);
/*  792:     */   }
/*  793:     */   
/*  794:     */   public static String recuperaExtencion(String nombreArchivo)
/*  795:     */   {
/*  796:1126 */     int mid = nombreArchivo.lastIndexOf(".");
/*  797:1127 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/*  798:     */   }
/*  799:     */   
/*  800:     */   public static Celda[][] leerExcel(String fileName, InputStream imInputStream, int filaInicial)
/*  801:     */     throws IOException
/*  802:     */   {
/*  803:1131 */     return leerExcel(fileName, imInputStream, filaInicial, 0);
/*  804:     */   }
/*  805:     */   
/*  806:     */   @Deprecated
/*  807:     */   public static Celda[][] leerExcel(String fileName, InputStream imInputStream, int filaInicial, int hoja)
/*  808:     */     throws IOException
/*  809:     */   {
/*  810:1137 */     Celda[][] resultado = (Celda[][])null;
/*  811:     */     
/*  812:1139 */     String uploadDir = ParametrosSistema.getAS2_HOME() + File.separator + "documentos" + File.separator + "";
/*  813:     */     
/*  814:1141 */     File directorio = new File(uploadDir);
/*  815:     */     
/*  816:1143 */     File file = new File(uploadDir + fileName);
/*  817:1145 */     if (!directorio.exists()) {
/*  818:1146 */       directorio.mkdirs();
/*  819:     */     }
/*  820:1149 */     FileOutputStream output = new FileOutputStream(file);
/*  821:1151 */     while (imInputStream.available() != 0) {
/*  822:1152 */       output.write(imInputStream.read());
/*  823:     */     }
/*  824:1155 */     imInputStream.close();
/*  825:1156 */     output.close();
/*  826:     */     
/*  827:1158 */     HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(uploadDir + fileName));
/*  828:1159 */     HSSFSheet sheet = wb.getSheetAt(hoja);
/*  829:1160 */     int columnas = 0;
/*  830:1161 */     int filas = sheet.getPhysicalNumberOfRows();
/*  831:1162 */     for (int f = filaInicial - 1; f < filas; f++)
/*  832:     */     {
/*  833:1163 */       HSSFRow fila = sheet.getRow(f);
/*  834:1164 */       if (fila != null)
/*  835:     */       {
/*  836:1168 */         if (resultado == null)
/*  837:     */         {
/*  838:1169 */           columnas = fila.getPhysicalNumberOfCells();
/*  839:1170 */           resultado = new Celda[filas - (filaInicial - 1)][columnas];
/*  840:     */         }
/*  841:1173 */         for (int c = 0; c < columnas; c++)
/*  842:     */         {
/*  843:1174 */           HSSFCell celda = fila.getCell(c);
/*  844:1175 */           switch (celda.getCellType())
/*  845:     */           {
/*  846:     */           case 2: 
/*  847:1178 */             resultado[(f - (filaInicial - 1))][c] = new Celda("" + celda.getCellFormula());
/*  848:1179 */             break;
/*  849:     */           case 0: 
/*  850:1182 */             resultado[(f - (filaInicial - 1))][c] = new Celda("" + celda.getNumericCellValue());
/*  851:1183 */             break;
/*  852:     */           case 4: 
/*  853:1186 */             resultado[(f - (filaInicial - 1))][c] = new Celda("" + celda.getBooleanCellValue());
/*  854:1187 */             break;
/*  855:     */           case 1: 
/*  856:1190 */             resultado[(f - (filaInicial - 1))][c] = new Celda("" + celda.getStringCellValue());
/*  857:     */           }
/*  858:     */         }
/*  859:     */       }
/*  860:     */     }
/*  861:1198 */     return resultado;
/*  862:     */   }
/*  863:     */   
/*  864:     */   @Deprecated
/*  865:     */   public static HSSFCell[][] leerExcel2(String fileName, InputStream imInputStream, int filaInicial, int hoja)
/*  866:     */     throws IOException
/*  867:     */   {
/*  868:1213 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  869:1214 */     return leerExcel2(idOrganizacion, fileName, imInputStream, filaInicial, hoja);
/*  870:     */   }
/*  871:     */   
/*  872:     */   public static HSSFCell[][] leerExcel2(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial, int hoja)
/*  873:     */     throws IOException
/*  874:     */   {
/*  875:1232 */     HSSFCell[][] resultado = (HSSFCell[][])null;
/*  876:     */     
/*  877:1234 */     String uploadDir = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + "documentos" + File.separator + "";
/*  878:     */     
/*  879:1236 */     File directorio = new File(uploadDir);
/*  880:     */     
/*  881:1238 */     File file = new File(uploadDir + fileName);
/*  882:1240 */     if (!directorio.exists()) {
/*  883:1241 */       directorio.mkdirs();
/*  884:     */     }
/*  885:1244 */     FileOutputStream output = new FileOutputStream(file);
/*  886:1245 */     while (imInputStream.available() != 0) {
/*  887:1246 */       output.write(imInputStream.read());
/*  888:     */     }
/*  889:1248 */     imInputStream.close();
/*  890:1249 */     output.close();
/*  891:     */     
/*  892:1251 */     HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(uploadDir + fileName));
/*  893:1252 */     HSSFSheet sheet = wb.getSheetAt(hoja);
/*  894:     */     
/*  895:     */ 
/*  896:1255 */     int filas = sheet.getPhysicalNumberOfRows();
/*  897:     */     
/*  898:     */ 
/*  899:1258 */     int columnas = sheet.getRow(filaInicial - 2).getPhysicalNumberOfCells();
/*  900:     */     
/*  901:     */ 
/*  902:1261 */     resultado = new HSSFCell[filas - (filaInicial - 1)][columnas];
/*  903:1263 */     for (int f = filaInicial - 1; f < filas; f++)
/*  904:     */     {
/*  905:1264 */       HSSFRow fila = sheet.getRow(f);
/*  906:1265 */       if (fila != null) {
/*  907:1269 */         for (int c = 0; c < columnas; c++) {
/*  908:1270 */           resultado[(f - (filaInicial - 1))][c] = fila.getCell(c);
/*  909:     */         }
/*  910:     */       }
/*  911:     */     }
/*  912:1273 */     return resultado;
/*  913:     */   }
/*  914:     */   
/*  915:     */   public static HSSFCell[][] leerExcel2ColumnasIrregulares(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial, int hoja, int columnas)
/*  916:     */     throws IOException
/*  917:     */   {
/*  918:1291 */     HSSFCell[][] resultado = (HSSFCell[][])null;
/*  919:     */     
/*  920:1293 */     String uploadDir = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + "documentos" + File.separator + "";
/*  921:     */     
/*  922:1295 */     File directorio = new File(uploadDir);
/*  923:     */     
/*  924:1297 */     File file = new File(uploadDir + fileName);
/*  925:1299 */     if (!directorio.exists()) {
/*  926:1300 */       directorio.mkdirs();
/*  927:     */     }
/*  928:1303 */     FileOutputStream output = new FileOutputStream(file);
/*  929:1304 */     while (imInputStream.available() != 0) {
/*  930:1305 */       output.write(imInputStream.read());
/*  931:     */     }
/*  932:1307 */     imInputStream.close();
/*  933:1308 */     output.close();
/*  934:     */     
/*  935:1310 */     HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(uploadDir + fileName));
/*  936:1311 */     HSSFSheet sheet = wb.getSheetAt(hoja);
/*  937:     */     
/*  938:     */ 
/*  939:1314 */     int filas = sheet.getPhysicalNumberOfRows();
/*  940:     */     
/*  941:     */ 
/*  942:1317 */     resultado = new HSSFCell[filas - (filaInicial - 1)][columnas];
/*  943:1319 */     for (int f = filaInicial - 1; f < filas; f++)
/*  944:     */     {
/*  945:1320 */       HSSFRow fila = sheet.getRow(f);
/*  946:1321 */       if (fila != null) {
/*  947:1325 */         for (int c = 0; c < columnas; c++) {
/*  948:1326 */           resultado[(f - (filaInicial - 1))][c] = fila.getCell(c);
/*  949:     */         }
/*  950:     */       }
/*  951:     */     }
/*  952:1329 */     return resultado;
/*  953:     */   }
/*  954:     */   
/*  955:     */   @Deprecated
/*  956:     */   public static HSSFCell[][] leerExcelFinal(int idOrganizacion, String fileName, InputStream imInputStream, int filaInicial, int hoja)
/*  957:     */     throws IOException
/*  958:     */   {
/*  959:1347 */     HSSFCell[][] resultado = (HSSFCell[][])null;
/*  960:     */     
/*  961:1349 */     String uploadDir = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + "documentos" + File.separator + "";
/*  962:     */     
/*  963:1351 */     File directorio = new File(uploadDir);
/*  964:     */     
/*  965:1353 */     File file = new File(uploadDir + fileName);
/*  966:1355 */     if (!directorio.exists()) {
/*  967:1356 */       directorio.mkdirs();
/*  968:     */     }
/*  969:1359 */     FileOutputStream output = new FileOutputStream(file);
/*  970:1360 */     while (imInputStream.available() != 0) {
/*  971:1361 */       output.write(imInputStream.read());
/*  972:     */     }
/*  973:1363 */     imInputStream.close();
/*  974:1364 */     output.close();
/*  975:     */     
/*  976:1366 */     HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(uploadDir + fileName));
/*  977:1367 */     HSSFSheet sheet = wb.getSheetAt(hoja);
/*  978:     */     
/*  979:     */ 
/*  980:1370 */     int filas = sheet.getPhysicalNumberOfRows();
/*  981:     */     
/*  982:1372 */     int columnas = sheet.getRow(filaInicial > 0 ? filaInicial - 1 : 0).getPhysicalNumberOfCells();
/*  983:     */     
/*  984:     */ 
/*  985:1375 */     resultado = new HSSFCell[filas - filaInicial][columnas];
/*  986:1377 */     for (int f = filaInicial; f < filas; f++)
/*  987:     */     {
/*  988:1379 */       HSSFRow fila = sheet.getRow(f);
/*  989:1380 */       if (fila != null) {
/*  990:1384 */         for (int c = 0; c < columnas; c++) {
/*  991:1385 */           resultado[(f - filaInicial)][c] = fila.getCell(c);
/*  992:     */         }
/*  993:     */       }
/*  994:     */     }
/*  995:1389 */     return resultado;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public static HSSFCell[][] leerExcelFinal(InputStream imInputStream, int filaInicial, int hoja, Integer columnaInicial, Integer filasNull)
/*  999:     */     throws IOException
/* 1000:     */   {
/* 1001:1403 */     HSSFWorkbook wb = new HSSFWorkbook(imInputStream);
/* 1002:1404 */     HSSFSheet sheet = wb.getSheetAt(hoja);
/* 1003:     */     
/* 1004:     */ 
/* 1005:1407 */     int filas = sheet.getPhysicalNumberOfRows() + (filasNull == null ? 0 : filasNull.intValue());
/* 1006:     */     
/* 1007:1409 */     int columnas = sheet.getRow(filaInicial > 0 ? filaInicial - 1 : 0).getPhysicalNumberOfCells() + (columnaInicial == null ? 0 : columnaInicial.intValue());
/* 1008:     */     
/* 1009:     */ 
/* 1010:     */ 
/* 1011:1413 */     HSSFCell[][] resultado = new HSSFCell[filas - filaInicial][columnas];
/* 1012:1415 */     for (int f = filaInicial; f < filas; f++)
/* 1013:     */     {
/* 1014:1417 */       HSSFRow fila = sheet.getRow(f);
/* 1015:1418 */       if (fila != null) {
/* 1016:1421 */         for (int c = 0; c < columnas; c++) {
/* 1017:1422 */           resultado[(f - filaInicial)][c] = fila.getCell(c);
/* 1018:     */         }
/* 1019:     */       }
/* 1020:     */     }
/* 1021:1426 */     return resultado;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public static HSSFCell[][] leerExcelSinEncabezado(InputStream imInputStream, int filaInicial, int hoja)
/* 1025:     */     throws IOException
/* 1026:     */   {
/* 1027:1431 */     HSSFWorkbook wb = new HSSFWorkbook(imInputStream);
/* 1028:1432 */     HSSFSheet sheet = wb.getSheetAt(hoja);
/* 1029:     */     
/* 1030:1434 */     int filas = sheet.getLastRowNum() + 1;
/* 1031:     */     
/* 1032:1436 */     int columnas = sheet.getRow(filaInicial - 1).getPhysicalNumberOfCells();
/* 1033:     */     
/* 1034:     */ 
/* 1035:1439 */     HSSFCell[][] resultado = new HSSFCell[filas - filaInicial + 1][columnas];
/* 1036:1440 */     for (int f = filaInicial - 1; f < filas; f++)
/* 1037:     */     {
/* 1038:1441 */       HSSFRow fila = sheet.getRow(f);
/* 1039:1442 */       if (fila != null) {
/* 1040:1445 */         for (int c = 0; c < columnas; c++) {
/* 1041:1446 */           resultado[(f - filaInicial + 1)][c] = fila.getCell(c);
/* 1042:     */         }
/* 1043:     */       }
/* 1044:     */     }
/* 1045:1450 */     return resultado;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public static HSSFCell[][] leerExcelFinal(InputStream imInputStream, int filaInicial, int hoja)
/* 1049:     */     throws IOException
/* 1050:     */   {
/* 1051:1463 */     return leerExcelFinal(imInputStream, filaInicial, hoja, null, null);
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public static String completarALaIzquierda(char caracterDeRepeticion, int numeroDeCaracteres, String cadenaOriginal)
/* 1055:     */   {
/* 1056:1467 */     return replicar(caracterDeRepeticion, numeroDeCaracteres - cadenaOriginal.length()) + cadenaOriginal;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public static String completarALaDerecha(char caracterDeRepeticion, int numeroDeCaracteres, String cadenaOriginal)
/* 1060:     */   {
/* 1061:1471 */     return cadenaOriginal + replicar(caracterDeRepeticion, numeroDeCaracteres - cadenaOriginal.length());
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public static String replicar(char caracterDeRepeticion, int numeroDeCaracteres)
/* 1065:     */   {
/* 1066:1475 */     String r = "";
/* 1067:1476 */     for (int j = 0; j < numeroDeCaracteres; j++) {
/* 1068:1477 */       r = r + caracterDeRepeticion;
/* 1069:     */     }
/* 1070:1479 */     return r;
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public static String completarNumeroConCerosIzquierda(int numero, int numeroCeros)
/* 1074:     */   {
/* 1075:1483 */     String format = String.format("%%0%dd", new Object[] { Integer.valueOf(numeroCeros) });
/* 1076:1484 */     String result = String.format(format, new Object[] { Integer.valueOf(numero) });
/* 1077:     */     
/* 1078:1486 */     return new String(result);
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public static void crearExcel(String fileName, List<Object[]> listaDatos, int numeroColumnas, boolean celdaConTipoDato)
/* 1082:     */   {
/* 1083:1501 */     List<Object[]> excelData = listaDatos;
/* 1084:     */     
/* 1085:1503 */     HSSFWorkbook myWorkBook = new HSSFWorkbook();
/* 1086:1504 */     HSSFSheet mySheet = myWorkBook.createSheet();
/* 1087:1505 */     HSSFRow myRow = null;
/* 1088:1506 */     HSSFCell myCell = null;
/* 1089:     */     
/* 1090:1508 */     int rowNum = 0;
/* 1091:1509 */     for (Object[] object : excelData)
/* 1092:     */     {
/* 1093:1510 */       myRow = mySheet.createRow(rowNum);
/* 1094:1511 */       for (int cellNum = 0; cellNum < numeroColumnas; cellNum++)
/* 1095:     */       {
/* 1096:1512 */         myCell = myRow.createCell(cellNum);
/* 1097:1513 */         if (celdaConTipoDato)
/* 1098:     */         {
/* 1099:1514 */           if ((object[cellNum] instanceof Integer)) {
/* 1100:1515 */             myCell.setCellValue(((Integer)object[cellNum]).intValue());
/* 1101:1516 */           } else if ((object[cellNum] instanceof Double)) {
/* 1102:1517 */             myCell.setCellValue(((Double)object[cellNum]).doubleValue());
/* 1103:1518 */           } else if ((object[cellNum] instanceof BigDecimal)) {
/* 1104:1519 */             myCell.setCellValue(((BigDecimal)object[cellNum]).doubleValue());
/* 1105:1520 */           } else if ((object[cellNum] instanceof Date)) {
/* 1106:1521 */             myCell.setCellValue(dateToString((Date)object[cellNum]));
/* 1107:     */           } else {
/* 1108:1523 */             myCell.setCellValue(object[cellNum].toString());
/* 1109:     */           }
/* 1110:     */         }
/* 1111:     */         else {
/* 1112:1526 */           myCell.setCellValue(object[cellNum].toString());
/* 1113:     */         }
/* 1114:     */       }
/* 1115:1529 */       rowNum++;
/* 1116:     */     }
/* 1117:     */     try
/* 1118:     */     {
/* 1119:1533 */       FileOutputStream out = new FileOutputStream(fileName);
/* 1120:1534 */       myWorkBook.write(out);
/* 1121:1535 */       out.close();
/* 1122:     */     }
/* 1123:     */     catch (Exception e)
/* 1124:     */     {
/* 1125:1537 */       e.printStackTrace();
/* 1126:     */     }
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   @Deprecated
/* 1130:     */   public static void crearArchivoTxt(String directorioArchivo, String fileName, List<Object[]> listaDatos, int numeroColumnas)
/* 1131:     */     throws ExcepcionAS2
/* 1132:     */   {
/* 1133:1555 */     List<Object[]> textoData = listaDatos;
/* 1134:     */     
/* 1135:1557 */     File directorioTxt = new File(directorioArchivo);
/* 1136:1558 */     if (!directorioTxt.exists()) {
/* 1137:1559 */       directorioTxt.mkdirs();
/* 1138:     */     }
/* 1139:     */     try
/* 1140:     */     {
/* 1141:1563 */       BufferedWriter bw = new BufferedWriter(new FileWriter(directorioArchivo + fileName));
/* 1142:1565 */       for (Object[] object : textoData)
/* 1143:     */       {
/* 1144:1566 */         for (int i = 0; i < numeroColumnas; i++) {
/* 1145:1567 */           bw.write(object[i].toString() + "\t");
/* 1146:     */         }
/* 1147:1569 */         bw.write("\n");
/* 1148:     */       }
/* 1149:1571 */       bw.close();
/* 1150:     */     }
/* 1151:     */     catch (IOException e)
/* 1152:     */     {
/* 1153:1574 */       throw new ExcepcionAS2(e);
/* 1154:     */     }
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   public static void crearArchivoTxt(String directorioArchivo, String fileName, List<Object[]> listaDatos, int numeroColumnas, String separador)
/* 1158:     */     throws ExcepcionAS2
/* 1159:     */   {
/* 1160:1593 */     crearArchivoTxt(directorioArchivo, fileName, listaDatos, numeroColumnas, separador, null);
/* 1161:     */   }
/* 1162:     */   
/* 1163:     */   public static String uploadArchivo(FileUploadEvent event, String codigo, String nombre, String uploadDir)
/* 1164:     */   {
/* 1165:1611 */     String fileName = nombre + "_" + codigo + extension(event.getFile().getFileName());
/* 1166:     */     try
/* 1167:     */     {
/* 1168:1615 */       File directorio = new File(uploadDir);
/* 1169:1616 */       File file = new File(uploadDir + fileName);
/* 1170:1617 */       if (!directorio.exists()) {
/* 1171:1618 */         directorio.mkdirs();
/* 1172:     */       }
/* 1173:1620 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 1174:1621 */       FileOutputStream output = new FileOutputStream(file);
/* 1175:1622 */       while (input.available() != 0) {
/* 1176:1623 */         output.write(input.read());
/* 1177:     */       }
/* 1178:1625 */       input.close();
/* 1179:1626 */       output.close();
/* 1180:     */     }
/* 1181:     */     catch (Exception localException) {}
/* 1182:1631 */     return fileName;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public static String extension(String nombreArchivo)
/* 1186:     */   {
/* 1187:1642 */     int mid = nombreArchivo.lastIndexOf(".");
/* 1188:1643 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public static void downloadArchivo(String downloadDirectorio, String fileName)
/* 1192:     */   {
/* 1193:1648 */     File file = new File(downloadDirectorio + fileName);
/* 1194:1649 */     FacesContext faces = FacesContext.getCurrentInstance();
/* 1195:1650 */     HttpServletResponse response = (HttpServletResponse)faces.getExternalContext().getResponse();
/* 1196:     */     
/* 1197:1652 */     String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
/* 1198:     */     
/* 1199:1654 */     response.setContentType(mimeType);
/* 1200:1655 */     response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
/* 1201:     */     try
/* 1202:     */     {
/* 1203:1659 */       FileInputStream fileIn = new FileInputStream(file);
/* 1204:1660 */       ServletOutputStream out = response.getOutputStream();
/* 1205:     */       
/* 1206:1662 */       byte[] outputByte = new byte[4096];
/* 1207:1664 */       while (fileIn.read(outputByte, 0, 4096) != -1) {
/* 1208:1665 */         out.write(outputByte, 0, 4096);
/* 1209:     */       }
/* 1210:1667 */       fileIn.close();
/* 1211:1668 */       out.flush();
/* 1212:1669 */       out.close();
/* 1213:     */     }
/* 1214:     */     catch (FileNotFoundException e)
/* 1215:     */     {
/* 1216:1673 */       e.printStackTrace();
/* 1217:     */     }
/* 1218:     */     catch (IOException e)
/* 1219:     */     {
/* 1220:1676 */       e.printStackTrace();
/* 1221:     */     }
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public static void eliminarArchivo(String downloadDirectorio, String fileName)
/* 1225:     */   {
/* 1226:     */     try
/* 1227:     */     {
/* 1228:1684 */       File file = new File(downloadDirectorio + fileName);
/* 1229:     */       
/* 1230:1686 */       file.delete();
/* 1231:     */     }
/* 1232:     */     catch (Exception e)
/* 1233:     */     {
/* 1234:1690 */       e.printStackTrace();
/* 1235:     */     }
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public static DefaultStreamedContent descargarArchivo(String rutaArchivo, String tipoContenido, String nombreArchivo)
/* 1239:     */     throws FileNotFoundException
/* 1240:     */   {
/* 1241:1709 */     FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
/* 1242:1710 */     return new DefaultStreamedContent(fileInputStream, tipoContenido, nombreArchivo);
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public static BigDecimal porcentaje(BigDecimal base, BigDecimal pct, int decimales)
/* 1246:     */   {
/* 1247:1723 */     return redondearBigDecimal(base.multiply(pct).divide(CIEN), decimales);
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public static BigDecimal porcentaje(BigDecimal base, double pct, int decimales)
/* 1251:     */   {
/* 1252:1727 */     return porcentaje(base, new BigDecimal(pct), decimales);
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public static BigDecimal porcentaje(BigDecimal base, BigDecimal pct)
/* 1256:     */   {
/* 1257:1732 */     return porcentaje(base, pct, 2);
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public static BigDecimal porcentaje(BigDecimal base, double pct)
/* 1261:     */   {
/* 1262:1737 */     return porcentaje(base, pct, 2);
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public static int calcularEdad(Date fechaNacimiento, Date fechaCalculo)
/* 1266:     */   {
/* 1267:1749 */     Calendar birth = new GregorianCalendar();
/* 1268:1750 */     Calendar today = new GregorianCalendar();
/* 1269:1751 */     int age = 0;
/* 1270:1752 */     int factor = 0;
/* 1271:1753 */     Date birthDate = fechaNacimiento;
/* 1272:1754 */     Date currentDate = fechaCalculo;
/* 1273:1755 */     birth.setTime(birthDate);
/* 1274:1756 */     today.setTime(currentDate);
/* 1275:     */     
/* 1276:1758 */     int diaHoy = today.get(5);
/* 1277:1759 */     int diaCumple = birth.get(5);
/* 1278:1760 */     int mesHoy = today.get(2) + 1;
/* 1279:1761 */     int mesCumple = birth.get(2) + 1;
/* 1280:1763 */     if (mesHoy <= mesCumple) {
/* 1281:1764 */       if (mesHoy == mesCumple)
/* 1282:     */       {
/* 1283:1765 */         if (diaHoy < diaCumple) {
/* 1284:1766 */           factor = -1;
/* 1285:     */         }
/* 1286:     */       }
/* 1287:     */       else {
/* 1288:1769 */         factor = -1;
/* 1289:     */       }
/* 1290:     */     }
/* 1291:1772 */     age = today.get(1) - birth.get(1) + factor;
/* 1292:1773 */     return age;
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public static void createCell(Row row, int i, String value, CellStyle style, Integer cellType)
/* 1296:     */   {
/* 1297:1789 */     Cell cell = row.createCell(i);
/* 1298:1792 */     if (style != null) {
/* 1299:1793 */       cell.setCellStyle(style);
/* 1300:     */     }
/* 1301:1795 */     if ((cellType != null) && (cellType.intValue() == 0))
/* 1302:     */     {
/* 1303:1796 */       cell.setCellValue(Double.parseDouble(value));
/* 1304:1797 */       cell.setCellType(0);
/* 1305:     */     }
/* 1306:     */     else
/* 1307:     */     {
/* 1308:1799 */       cell.setCellValue(value);
/* 1309:     */     }
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public static void crearExcel(Vector v, String namesheet, String directorioArchivo, String filename)
/* 1313:     */     throws Exception
/* 1314:     */   {
/* 1315:     */     try
/* 1316:     */     {
/* 1317:1817 */       File directorio = new File(directorioArchivo);
/* 1318:1818 */       if (!directorio.exists()) {
/* 1319:1819 */         directorio.mkdirs();
/* 1320:     */       }
/* 1321:1822 */       Workbook wb = new HSSFWorkbook();
/* 1322:1823 */       CellStyle cellStyleNumber = wb.createCellStyle();
/* 1323:1824 */       DataFormat hssfDataFormat = wb.createDataFormat();
/* 1324:1825 */       cellStyleNumber.setDataFormat(hssfDataFormat.getFormat("#,##0.00"));
/* 1325:     */       
/* 1326:     */ 
/* 1327:1828 */       CreationHelper createHelper = wb.getCreationHelper();
/* 1328:1829 */       Sheet sheet = wb.createSheet(namesheet);
/* 1329:1830 */       int filas = v.size();
/* 1330:1831 */       for (int i = 0; i < filas; i++)
/* 1331:     */       {
/* 1332:1832 */         String fila = (String)v.elementAt(i);
/* 1333:1833 */         StringTokenizer st = new StringTokenizer(fila, ",");
/* 1334:1834 */         Row row = sheet.createRow((short)i);
/* 1335:1835 */         int j = 0;
/* 1336:1836 */         while (st.hasMoreTokens())
/* 1337:     */         {
/* 1338:1837 */           String token = st.nextToken();
/* 1339:1840 */           if (i == 0)
/* 1340:     */           {
/* 1341:1841 */             CellStyle style = wb.createCellStyle();
/* 1342:1842 */             style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
/* 1343:1843 */             style.setFillPattern((short)1);
/* 1344:1844 */             Font font = wb.createFont();
/* 1345:     */             
/* 1346:1846 */             font.setFontName("Courier New");
/* 1347:     */             
/* 1348:     */ 
/* 1349:1849 */             font.setBoldweight((short)700);
/* 1350:1850 */             font.setColor(IndexedColors.WHITE.getIndex());
/* 1351:1851 */             style.setFont(font);
/* 1352:1852 */             createCell(row, j, token.trim(), style, null);
/* 1353:     */           }
/* 1354:1853 */           else if (token.startsWith("n::"))
/* 1355:     */           {
/* 1356:1854 */             token = token.replace("n::", "");
/* 1357:1855 */             createCell(row, j, token, cellStyleNumber, Integer.valueOf(0));
/* 1358:     */           }
/* 1359:     */           else
/* 1360:     */           {
/* 1361:1857 */             createCell(row, j, token, null, null);
/* 1362:     */           }
/* 1363:1860 */           j += 1;
/* 1364:     */         }
/* 1365:     */       }
/* 1366:1868 */       for (int i = 0; i < filas; i++) {
/* 1367:1869 */         sheet.autoSizeColumn((short)i);
/* 1368:     */       }
/* 1369:1873 */       FileOutputStream fileOut = new FileOutputStream(directorioArchivo + filename);
/* 1370:1874 */       wb.write(fileOut);
/* 1371:1875 */       fileOut.close();
/* 1372:     */     }
/* 1373:     */     catch (Exception e)
/* 1374:     */     {
/* 1375:1878 */       e.printStackTrace();
/* 1376:     */     }
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public static BigDecimal diferenciasDeHoras(Date horaInicial, Date horaFinal)
/* 1380:     */   {
/* 1381:1890 */     double horaInicialMs = horaInicial.getTime();
/* 1382:1891 */     double horaFinalMs = horaFinal.getTime();
/* 1383:1892 */     double diferencia = horaFinalMs - horaInicialMs;
/* 1384:1893 */     double horas = Redondear(diferencia / 3600000.0D);
/* 1385:1894 */     return BigDecimal.valueOf(horas);
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public static Date SumarHoras(Date hora, double numeroHoras)
/* 1389:     */   {
/* 1390:1907 */     int horas = (int)numeroHoras;
/* 1391:1908 */     int minutos = (int)((numeroHoras - horas) * 60.0D);
/* 1392:1909 */     Calendar fechaCalendar = Calendar.getInstance();
/* 1393:1910 */     fechaCalendar.setTime(hora);
/* 1394:1911 */     fechaCalendar.add(10, horas);
/* 1395:1912 */     fechaCalendar.add(12, minutos);
/* 1396:1913 */     return fechaCalendar.getTime();
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public static BigDecimal inverso(BigDecimal valor, int numeroDecimales)
/* 1400:     */   {
/* 1401:1924 */     return BigDecimal.valueOf(1L).divide(valor, numeroDecimales, RoundingMode.HALF_UP);
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   public static boolean validaSoloNumero(String cadena)
/* 1405:     */   {
/* 1406:1934 */     boolean resp = true;
/* 1407:1935 */     for (int i = 0; i < cadena.length(); i++) {
/* 1408:1936 */       if (!Character.isDigit(cadena.charAt(i))) {
/* 1409:1937 */         resp = false;
/* 1410:     */       }
/* 1411:     */     }
/* 1412:1940 */     return resp;
/* 1413:     */   }
/* 1414:     */   
/* 1415:     */   public static Date stringAFecha(String fecha)
/* 1416:     */     throws ExcepcionAS2
/* 1417:     */   {
/* 1418:1952 */     SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
/* 1419:1953 */     String strFecha = fecha;
/* 1420:1954 */     Date fechaDate = null;
/* 1421:     */     try
/* 1422:     */     {
/* 1423:1956 */       fechaDate = formato.parse(strFecha);
/* 1424:     */     }
/* 1425:     */     catch (ParseException e)
/* 1426:     */     {
/* 1427:1958 */       throw new ExcepcionAS2("msg_error_formato_incorrecto");
/* 1428:     */     }
/* 1429:1960 */     return fechaDate;
/* 1430:     */   }
/* 1431:     */   
/* 1432:     */   public static List<String> leerArchivoTexto(InputStream imInputStream)
/* 1433:     */     throws IOException
/* 1434:     */   {
/* 1435:1969 */     BufferedReader entrada = null;
/* 1436:1970 */     List<String> lista = new ArrayList();
/* 1437:     */     try
/* 1438:     */     {
/* 1439:1973 */       entrada = new BufferedReader(new InputStreamReader(imInputStream));
/* 1440:1975 */       while (entrada.ready())
/* 1441:     */       {
/* 1442:1976 */         String linea = entrada.readLine();
/* 1443:1977 */         if ((linea != null) && (!linea.trim().isEmpty())) {
/* 1444:1978 */           lista.add(linea);
/* 1445:     */         }
/* 1446:     */       }
/* 1447:     */     }
/* 1448:     */     catch (IOException e)
/* 1449:     */     {
/* 1450:1982 */       e.printStackTrace();
/* 1451:     */     }
/* 1452:     */     finally
/* 1453:     */     {
/* 1454:1984 */       entrada.close();
/* 1455:     */     }
/* 1456:1986 */     return lista;
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public static String desEncriptar(String texto, AESKey aesKey)
/* 1460:     */   {
/* 1461:1990 */     AESEncriptacion encripta = new AESEncriptacion(aesKey);
/* 1462:1991 */     String desencriptado = encripta.desencriptar(texto);
/* 1463:1992 */     return desencriptado;
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public static String encriptar(String texto, AESKey aesKey)
/* 1467:     */   {
/* 1468:1996 */     AESEncriptacion encripta = new AESEncriptacion(aesKey);
/* 1469:1997 */     String encriptado = encripta.encripta(texto);
/* 1470:1998 */     return encriptado;
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   public static String leerArchivoConfiguracion(String clave)
/* 1474:     */     throws ExcepcionAS2
/* 1475:     */   {
/* 1476:2008 */     Properties prop = new Properties();
/* 1477:2009 */     InputStream is = null;
/* 1478:     */     try
/* 1479:     */     {
/* 1480:2011 */       String as2Home = System.getenv("AS2_HOME");
/* 1481:2012 */       File config = new File(as2Home + File.separator + "config" + File.separator + "configuracion.properties");
/* 1482:2013 */       is = new FileInputStream(config);
/* 1483:2014 */       prop.load(is);
/* 1484:     */     }
/* 1485:     */     catch (IOException e)
/* 1486:     */     {
/* 1487:2016 */       throw new ExcepcionAS2("msg_error_archivo_configuracion_no_encontrado", e);
/* 1488:     */     }
/* 1489:2018 */     return prop.getProperty(clave);
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   public static Object validarCelda(HSSFCell[] fila, FormatoCelda formatoCelda, int numeroFila, int numeroColumna, boolean requerido, Integer tamanioMenor, Integer tamanioMayor)
/* 1493:     */     throws ExcepcionAS2
/* 1494:     */   {
/* 1495:2035 */     if ((requerido) && (fila[numeroColumna] == null)) {
/* 1496:2036 */       throw new ExcepcionAS2("msg_error_dato_obligatorio", " Fila: " + numeroFila + " Columna: " + numeroColumna);
/* 1497:     */     }
/* 1498:2039 */     if ((!requerido) && (fila[numeroColumna] == null))
/* 1499:     */     {
/* 1500:2040 */       if (formatoCelda.equals(FormatoCelda.NUMERO)) {
/* 1501:2041 */         return BigDecimal.ZERO;
/* 1502:     */       }
/* 1503:2043 */       return null;
/* 1504:     */     }
/* 1505:2047 */     Object dato = null;
/* 1506:2048 */     String datoString = null;
/* 1507:2049 */     FormatoCelda formato = null;
/* 1508:     */     try
/* 1509:     */     {
/* 1510:2052 */       if (formatoCelda.equals(FormatoCelda.TEXTO))
/* 1511:     */       {
/* 1512:2054 */         formato = FormatoCelda.TEXTO;
/* 1513:2055 */         datoString = fila[numeroColumna].getStringCellValue();
/* 1514:2056 */         datoString = datoString.trim();
/* 1515:2057 */         if ((!requerido) && (datoString.isEmpty())) {
/* 1516:2058 */           return null;
/* 1517:     */         }
/* 1518:2060 */         if ((requerido) && (datoString.isEmpty())) {
/* 1519:2062 */           throw new ExcepcionAS2("msg_error_dato_obligatorio", " Fila: " + numeroFila + " Columna: " + numeroColumna + " Dato: " + fila[numeroColumna].toString());
/* 1520:     */         }
/* 1521:2064 */         if (datoString.length() > tamanioMayor.intValue()) {
/* 1522:2065 */           datoString = datoString.substring(0, tamanioMayor.intValue());
/* 1523:     */         }
/* 1524:2067 */         if (datoString.length() < tamanioMenor.intValue()) {
/* 1525:2068 */           datoString = completarALaDerecha(' ', tamanioMenor.intValue() - datoString.length(), datoString);
/* 1526:     */         }
/* 1527:2070 */         dato = datoString;
/* 1528:     */       }
/* 1529:2072 */       else if (formatoCelda.equals(FormatoCelda.FECHA))
/* 1530:     */       {
/* 1531:2073 */         formato = FormatoCelda.FECHA;
/* 1532:2074 */         dato = fila[numeroColumna].getDateCellValue();
/* 1533:     */       }
/* 1534:2075 */       else if (formatoCelda.equals(FormatoCelda.NUMERO))
/* 1535:     */       {
/* 1536:2076 */         formato = FormatoCelda.NUMERO;
/* 1537:2077 */         dato = redondearBigDecimal(new BigDecimal(fila[numeroColumna].getNumericCellValue()), 2);
/* 1538:     */       }
/* 1539:2078 */       else if (formatoCelda.equals(FormatoCelda.COSTO))
/* 1540:     */       {
/* 1541:2079 */         formato = FormatoCelda.COSTO;
/* 1542:2080 */         dato = redondearBigDecimal(new BigDecimal(fila[numeroColumna].getNumericCellValue()), 4);
/* 1543:     */       }
/* 1544:     */       else
/* 1545:     */       {
/* 1546:2083 */         throw new ExcepcionAS2("msg_error_formato_incorrecto_001", " " + formato.getNombre() + " Fila: " + numeroFila + " Columna: " + numeroColumna + " Dato: " + fila[numeroColumna].toString());
/* 1547:     */       }
/* 1548:     */     }
/* 1549:     */     catch (IllegalStateException e)
/* 1550:     */     {
/* 1551:2087 */       throw new ExcepcionAS2("msg_error_formato_incorrecto_001", " " + formato.getNombre() + " Fila: " + numeroFila + " Columna: " + numeroColumna + " Dato: " + fila[numeroColumna].toString());
/* 1552:     */     }
/* 1553:2089 */     return dato;
/* 1554:     */   }
/* 1555:     */   
/* 1556:     */   public static String removerCaracteresEspeciales(String input)
/* 1557:     */   {
/* 1558:2100 */     String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
/* 1559:     */     
/* 1560:2102 */     Pattern pattern = Pattern.compile("\\P{ASCII}+");
/* 1561:2103 */     return pattern.matcher(normalized).replaceAll("");
/* 1562:     */   }
/* 1563:     */   
/* 1564:     */   public static String obtenerAlfanumericos(String s)
/* 1565:     */   {
/* 1566:2107 */     Pattern pattern = Pattern.compile("[^a-z A-Z 0-9]");
/* 1567:2108 */     Matcher matcher = pattern.matcher(s);
/* 1568:     */     
/* 1569:2110 */     return matcher.replaceAll("").replaceAll(" ", "");
/* 1570:     */   }
/* 1571:     */   
/* 1572:     */   public static int obtenerNumeroRandomico(int n)
/* 1573:     */   {
/* 1574:2114 */     Random rand = new Random();
/* 1575:     */     
/* 1576:2116 */     return rand.nextInt(n) + 1;
/* 1577:     */   }
/* 1578:     */   
/* 1579:     */   public static String getTipoIdentificacionClienteSRI(String codigoTipoIdentificacion)
/* 1580:     */   {
/* 1581:2120 */     String tipoIdentificacionSRI = codigoTipoIdentificacion;
/* 1582:2121 */     if ("R".equals(codigoTipoIdentificacion)) {
/* 1583:2122 */       tipoIdentificacionSRI = "04";
/* 1584:2123 */     } else if ("C".equals(codigoTipoIdentificacion)) {
/* 1585:2124 */       tipoIdentificacionSRI = "05";
/* 1586:2125 */     } else if ("P".equals(codigoTipoIdentificacion)) {
/* 1587:2126 */       tipoIdentificacionSRI = "06";
/* 1588:     */     }
/* 1589:2129 */     return tipoIdentificacionSRI;
/* 1590:     */   }
/* 1591:     */   
/* 1592:     */   public static int cantidadMeses(Date fechaInicial, Date fechaFinal)
/* 1593:     */   {
/* 1594:2140 */     Date inicio = getFechaInicioMes(fechaInicial);
/* 1595:2141 */     Date fin = getFechaFinMes(fechaFinal);
/* 1596:2142 */     int cantidad = 0;
/* 1597:2144 */     while (inicio.before(fin))
/* 1598:     */     {
/* 1599:2145 */       cantidad += 1;
/* 1600:2146 */       inicio = sumarFechaMeses(inicio, 1);
/* 1601:     */     }
/* 1602:2148 */     return cantidad;
/* 1603:     */   }
/* 1604:     */   
/* 1605:     */   public static BigDecimal cantidadMesesDecimales(Date fechaInicial, Date fechaFinal)
/* 1606:     */   {
/* 1607:2159 */     int cantidadMeses = cantidadMeses(fechaInicial, fechaFinal);
/* 1608:2160 */     BigDecimal proporcionalPrimero = getProporcianalDiasPosterioresVSMes(fechaInicial);
/* 1609:2161 */     BigDecimal proporcionalUltimo = getProporcianalDiasAnterioresVSMes(fechaFinal);
/* 1610:2162 */     BigDecimal totalMeses = new BigDecimal(cantidadMeses - 2).add(proporcionalPrimero).add(proporcionalUltimo);
/* 1611:2163 */     return totalMeses;
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public static List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin)
/* 1615:     */   {
/* 1616:2174 */     Calendar c1 = Calendar.getInstance();
/* 1617:2175 */     c1.setTime(fechaInicio);
/* 1618:2176 */     Calendar c2 = Calendar.getInstance();
/* 1619:2177 */     c2.setTime(fechaFin);
/* 1620:2178 */     List<Date> listaFechas = new ArrayList();
/* 1621:2179 */     while (!c1.after(c2))
/* 1622:     */     {
/* 1623:2180 */       listaFechas.add(c1.getTime());
/* 1624:2181 */       c1.add(5, 1);
/* 1625:     */     }
/* 1626:2183 */     return listaFechas;
/* 1627:     */   }
/* 1628:     */   
/* 1629:     */   @Deprecated
/* 1630:     */   public static Date getHoraCero(Date fecha)
/* 1631:     */   {
/* 1632:2189 */     Calendar calFecha = Calendar.getInstance();
/* 1633:2190 */     calFecha.setTime(fecha);
/* 1634:2191 */     calFecha.set(11, 0);
/* 1635:2192 */     calFecha.set(12, 0);
/* 1636:2193 */     calFecha.set(13, 0);
/* 1637:2194 */     calFecha.set(14, 0);
/* 1638:     */     
/* 1639:2196 */     return calFecha.getTime();
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public static Date copiarFechaManteniendoHoras(Date fechaOrigen, Date fechaDestino)
/* 1643:     */   {
/* 1644:2200 */     if (fechaDestino == null) {
/* 1645:2201 */       return null;
/* 1646:     */     }
/* 1647:2203 */     Calendar calFechaOrigen = Calendar.getInstance();
/* 1648:2204 */     calFechaOrigen.setTime(fechaOrigen);
/* 1649:2205 */     int anio = calFechaOrigen.get(1);
/* 1650:2206 */     int mes = calFechaOrigen.get(2);
/* 1651:2207 */     int dia = calFechaOrigen.get(5);
/* 1652:     */     
/* 1653:2209 */     Calendar calFechaDestino = Calendar.getInstance();
/* 1654:2210 */     calFechaDestino.setTime(fechaDestino);
/* 1655:2211 */     calFechaDestino.set(1, anio);
/* 1656:2212 */     calFechaDestino.set(2, mes);
/* 1657:2213 */     calFechaDestino.set(5, dia);
/* 1658:     */     
/* 1659:2215 */     return calFechaDestino.getTime();
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   public static String LeeFicheroHTMLToEmail(String direcion)
/* 1663:     */   {
/* 1664:2219 */     File archivo = null;
/* 1665:2220 */     FileReader fr = null;
/* 1666:2221 */     BufferedReader br = null;
/* 1667:2222 */     resultado = "";
/* 1668:     */     try
/* 1669:     */     {
/* 1670:2227 */       archivo = new File(direcion);
/* 1671:2228 */       fr = new FileReader(archivo);
/* 1672:2229 */       br = new BufferedReader(fr);
/* 1673:     */       String linea;
/* 1674:2233 */       while ((linea = br.readLine()) != null)
/* 1675:     */       {
/* 1676:2234 */         if (linea.startsWith("<img src=\""))
/* 1677:     */         {
/* 1678:2235 */           String[] cadenas = linea.split(" style=");
/* 1679:2236 */           String parte1 = cadenas[0];
/* 1680:2237 */           String nombreFichero = parte1.split("\"")[1];
/* 1681:2238 */           String fichero = nombreFichero.split("/")[1];
/* 1682:2239 */           linea = "<img src='cid:" + fichero + "' style=" + cadenas[1];
/* 1683:     */         }
/* 1684:2242 */         resultado = resultado + linea;
/* 1685:     */       }
/* 1686:2258 */       return resultado;
/* 1687:     */     }
/* 1688:     */     catch (Exception e)
/* 1689:     */     {
/* 1690:2245 */       e.printStackTrace();
/* 1691:     */     }
/* 1692:     */     finally
/* 1693:     */     {
/* 1694:     */       try
/* 1695:     */       {
/* 1696:2251 */         if (null != fr) {
/* 1697:2252 */           fr.close();
/* 1698:     */         }
/* 1699:     */       }
/* 1700:     */       catch (Exception e2)
/* 1701:     */       {
/* 1702:2255 */         e2.printStackTrace();
/* 1703:     */       }
/* 1704:     */     }
/* 1705:     */   }
/* 1706:     */   
/* 1707:     */   public static String formarDescripcion(Collection<String> lista, String des, int longitudMaxima)
/* 1708:     */   {
/* 1709:2263 */     StringBuilder datos = new StringBuilder();
/* 1710:2264 */     StringBuilder descripcion = new StringBuilder(des == null ? "" : des);
/* 1711:     */     
/* 1712:2266 */     int k = 0;
/* 1713:2267 */     for (String dato : lista) {
/* 1714:2268 */       datos.append((k++ > 0 ? "," : "") + dato);
/* 1715:     */     }
/* 1716:2271 */     int i = descripcion.lastIndexOf("]");
/* 1717:2272 */     if (i >= 0) {
/* 1718:2273 */       descripcion.replace(0, i + 2, "");
/* 1719:     */     }
/* 1720:2276 */     if (datos.length() > 0) {
/* 1721:2277 */       descripcion.insert(0, "[" + datos.toString() + "] ");
/* 1722:     */     }
/* 1723:2280 */     return descripcion.length() > longitudMaxima ? descripcion.substring(0, longitudMaxima).toString() : descripcion.toString();
/* 1724:     */   }
/* 1725:     */   
/* 1726:     */   public static BigDecimal redondearMultiploMasCercano(BigDecimal valor, int unidadBase)
/* 1727:     */   {
/* 1728:2284 */     BigDecimal multiplo = BigDecimal.ZERO;
/* 1729:2285 */     if (unidadBase == 0) {
/* 1730:2286 */       return valor;
/* 1731:     */     }
/* 1732:2288 */     Long divisor = Long.valueOf(valor.divide(new BigDecimal(unidadBase), 0, RoundingMode.DOWN).longValue());
/* 1733:2289 */     multiplo = new BigDecimal(divisor.longValue() * unidadBase);
/* 1734:     */     
/* 1735:2291 */     return multiplo;
/* 1736:     */   }
/* 1737:     */   
/* 1738:     */   public static Date setFechaMilisegundos(Date hora, int horas, int minutos, int segundos, int milisegundos)
/* 1739:     */   {
/* 1740:2295 */     Calendar fechaCalendar = Calendar.getInstance();
/* 1741:2296 */     fechaCalendar.setTime(hora);
/* 1742:2297 */     fechaCalendar.add(10, horas);
/* 1743:2298 */     fechaCalendar.add(12, minutos);
/* 1744:2299 */     fechaCalendar.add(13, segundos);
/* 1745:2300 */     fechaCalendar.add(14, milisegundos);
/* 1746:2301 */     return fechaCalendar.getTime();
/* 1747:     */   }
/* 1748:     */   
/* 1749:     */   public static String getCodigoImpuesto(double porcentaje)
/* 1750:     */   {
/* 1751:2305 */     if ((porcentaje >= 6.0D) && (porcentaje <= 12.99D)) {
/* 1752:2307 */       return "2";
/* 1753:     */     }
/* 1754:2308 */     if (porcentaje >= 13.1D) {
/* 1755:2309 */       return "3";
/* 1756:     */     }
/* 1757:2310 */     if (porcentaje == 0.02D) {
/* 1758:2311 */       return "5001";
/* 1759:     */     }
/* 1760:2312 */     if (porcentaje == 0.0D) {
/* 1761:2313 */       return "0";
/* 1762:     */     }
/* 1763:2315 */     return "";
/* 1764:     */   }
/* 1765:     */   
/* 1766:     */   public static String arrayToString(String[] lista)
/* 1767:     */   {
/* 1768:2320 */     return arrayToString(lista, null);
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   public static String arrayToString(String[] lista, String delimitador)
/* 1772:     */   {
/* 1773:2325 */     if (delimitador == null) {
/* 1774:2326 */       delimitador = "~";
/* 1775:     */     }
/* 1776:2329 */     StringBuffer sb = new StringBuffer();
/* 1777:2330 */     for (String dato : lista) {
/* 1778:2331 */       sb.append(dato).append(delimitador);
/* 1779:     */     }
/* 1780:2334 */     if (sb.length() > 0)
/* 1781:     */     {
/* 1782:2335 */       sb.setLength(sb.length() - 1);
/* 1783:2336 */       sb.insert(0, "[");
/* 1784:2337 */       sb.append("]");
/* 1785:     */     }
/* 1786:2340 */     return sb.toString();
/* 1787:     */   }
/* 1788:     */   
/* 1789:     */   public static String[] stringToArray(String lista)
/* 1790:     */   {
/* 1791:2344 */     return stringToArray(lista, null);
/* 1792:     */   }
/* 1793:     */   
/* 1794:     */   public static String[] stringToArray(String lista, String delimitador)
/* 1795:     */   {
/* 1796:2349 */     if (lista.length() == 0) {
/* 1797:2350 */       return new String[0];
/* 1798:     */     }
/* 1799:2353 */     if (delimitador == null) {
/* 1800:2354 */       delimitador = "~";
/* 1801:     */     }
/* 1802:2357 */     lista = lista.replace("[", "").replace("]", "");
/* 1803:     */     
/* 1804:2359 */     return lista.split(delimitador);
/* 1805:     */   }
/* 1806:     */   
/* 1807:     */   public static Iterator leerExcelXlsx(InputStream inputStream, int hoja)
/* 1808:     */     throws IOException
/* 1809:     */   {
/* 1810:2365 */     XSSFWorkbook wb = new XSSFWorkbook(inputStream);
/* 1811:2366 */     XSSFSheet sheet = wb.getSheetAt(hoja);
/* 1812:     */     
/* 1813:2368 */     return sheet.rowIterator();
/* 1814:     */   }
/* 1815:     */   
/* 1816:     */   public static String convertirDecimalToString(BigDecimal num)
/* 1817:     */   {
/* 1818:2378 */     String ret = null;
/* 1819:     */     try
/* 1820:     */     {
/* 1821:2380 */       ret = num.toBigIntegerExact().toString();
/* 1822:     */     }
/* 1823:     */     catch (ArithmeticException e)
/* 1824:     */     {
/* 1825:2382 */       num = num.setScale(2, 0);
/* 1826:2383 */       ret = num.toPlainString();
/* 1827:     */     }
/* 1828:2385 */     return ret;
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   public static String quitarCDATA(String xml)
/* 1832:     */   {
/* 1833:2389 */     xml = xml.trim();
/* 1834:2390 */     if (xml.startsWith("<![CDATA["))
/* 1835:     */     {
/* 1836:2391 */       xml = xml.substring(9);
/* 1837:2392 */       int i = xml.indexOf("]]>");
/* 1838:2393 */       if (i == -1) {
/* 1839:2394 */         throw new IllegalStateException("argument starts with <![CDATA[ but cannot find pairing ]]>");
/* 1840:     */       }
/* 1841:2396 */       xml = xml.substring(0, i);
/* 1842:     */     }
/* 1843:2398 */     return xml;
/* 1844:     */   }
/* 1845:     */   
/* 1846:     */   public static Date getUltimoDiaSemana(Date fecha)
/* 1847:     */   {
/* 1848:2404 */     Calendar cal1 = Calendar.getInstance();
/* 1849:2405 */     cal1.setTime(fecha);
/* 1850:2408 */     if (cal1.get(7) == 1) {
/* 1851:2409 */       return cal1.getTime();
/* 1852:     */     }
/* 1853:2411 */     int diferencia = 8 - cal1.get(7);
/* 1854:2412 */     cal1.add(5, diferencia);
/* 1855:2413 */     return cal1.getTime();
/* 1856:     */   }
/* 1857:     */   
/* 1858:     */   public static Date fechaMasCercana(List<Date> dates, Date currentDate)
/* 1859:     */   {
/* 1860:2425 */     long diferenciaMinima = -1L;long currentTime = currentDate.getTime();
/* 1861:2426 */     Date fechaCercana = null;
/* 1862:2427 */     for (Date date : dates)
/* 1863:     */     {
/* 1864:2428 */       long diferencia = Math.abs(currentTime - date.getTime());
/* 1865:2429 */       if ((diferenciaMinima == -1L) || (diferencia < diferenciaMinima))
/* 1866:     */       {
/* 1867:2430 */         diferenciaMinima = diferencia;
/* 1868:2431 */         fechaCercana = date;
/* 1869:     */       }
/* 1870:     */     }
/* 1871:2434 */     return fechaCercana;
/* 1872:     */   }
/* 1873:     */   
/* 1874:     */   public static void validarPeriodosExcluyentes(Date[][] fechas)
/* 1875:     */     throws AS2Exception
/* 1876:     */   {
/* 1877:2444 */     if (fechas != null) {
/* 1878:2445 */       for (int i = 0; i < fechas.length; i++)
/* 1879:     */       {
/* 1880:2446 */         Date fechaDesde = fechas[i][0];
/* 1881:2447 */         Date fechaHasta = fechas[i][1];
/* 1882:2448 */         for (int j = 0; j < fechas.length; j++) {
/* 1883:2449 */           if (i != j)
/* 1884:     */           {
/* 1885:2452 */             if ((fechaDesde.compareTo(fechas[j][0]) >= 0) && (fechaDesde.compareTo(fechas[j][1]) <= 0)) {
/* 1886:2453 */               throw new AS2Exception("com.asinfo.as2.ERROR_PERIODO_FECHAS_DUPLICADO", new String[] { "" });
/* 1887:     */             }
/* 1888:2455 */             if ((fechaHasta.compareTo(fechas[j][0]) >= 0) && (fechaHasta.compareTo(fechas[j][1]) <= 0)) {
/* 1889:2456 */               throw new AS2Exception("com.asinfo.as2.ERROR_PERIODO_FECHAS_DUPLICADO", new String[] { "" });
/* 1890:     */             }
/* 1891:     */           }
/* 1892:     */         }
/* 1893:     */       }
/* 1894:     */     }
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   public static void crearArchivoTxt(String directorioArchivo, String fileName, List<Object[]> listaDatos, int numeroColumnas, String separador, String codificacion)
/* 1898:     */     throws ExcepcionAS2
/* 1899:     */   {
/* 1900:2480 */     List<Object[]> textoData = listaDatos;
/* 1901:     */     
/* 1902:2482 */     File directorioTxt = new File(directorioArchivo);
/* 1903:2483 */     if (!directorioTxt.exists()) {
/* 1904:2484 */       directorioTxt.mkdirs();
/* 1905:     */     }
/* 1906:     */     try
/* 1907:     */     {
/* 1908:2489 */       BufferedWriter bw = null;
/* 1909:2490 */       if ((codificacion != null) && (!codificacion.isEmpty())) {
/* 1910:2491 */         bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(directorioArchivo + fileName), codificacion));
/* 1911:     */       } else {
/* 1912:2493 */         bw = new BufferedWriter(new FileWriter(directorioArchivo + fileName));
/* 1913:     */       }
/* 1914:2496 */       for (Object[] object : textoData)
/* 1915:     */       {
/* 1916:2497 */         int tamano = object.length - 1;
/* 1917:2498 */         for (int i = 0; i < numeroColumnas; i++) {
/* 1918:2499 */           bw.write(object[i].toString() + (i != tamano ? separador : ""));
/* 1919:     */         }
/* 1920:2501 */         bw.newLine();
/* 1921:     */       }
/* 1922:2503 */       bw.close();
/* 1923:     */     }
/* 1924:     */     catch (IOException e)
/* 1925:     */     {
/* 1926:2506 */       throw new ExcepcionAS2(e);
/* 1927:     */     }
/* 1928:     */   }
/* 1929:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.FuncionesUtiles
 * JD-Core Version:    0.7.0.1
 */