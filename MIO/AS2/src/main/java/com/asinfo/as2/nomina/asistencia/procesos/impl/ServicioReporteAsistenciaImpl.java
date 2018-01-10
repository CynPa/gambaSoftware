/*   1:    */ package com.asinfo.as2.nomina.asistencia.procesos.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ReporteAsistenciaDao;
/*   4:    */ import com.asinfo.as2.entities.Departamento;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.nomina.asistencia.reportes.ServicioReporteAsistencia;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Calendar;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.HashSet;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.List<[Ljava.lang.Object;>;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Set;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioReporteAsistenciaImpl
/*  24:    */   implements ServicioReporteAsistencia
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private transient ReporteAsistenciaDao reporteAsistenciaDao;
/*  28:    */   
/*  29:    */   public List<Object[]> getReporteAsistencia(Empleado empleado, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion, int tipo, boolean incluirDiasNoTrabajados)
/*  30:    */   {
/*  31: 49 */     List<Object[]> listDB = this.reporteAsistenciaDao.getReporteAsistencia(empleado, departamento, fechaDesde, fechaHasta, idOrganizacion, tipo);
/*  32: 50 */     List<Object[]> listResult = new ArrayList();
/*  33: 51 */     Map<Integer, Object[]> hashAsistencias = new HashMap();
/*  34: 52 */     Map<String, Map<Date, Object[]>> hashAsistenciasPorEmpleado = new HashMap();
/*  35:    */     
/*  36: 54 */     Set<Integer> listaIdAsistenciasPadres = new HashSet();
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 68 */     Date horaCero = FuncionesUtiles.stringToDate("00:00", "HH:mm");
/*  51: 69 */     Date horaFinDia = FuncionesUtiles.stringToDate("23:59", "HH:mm");
/*  52: 83 */     for (Object[] objects : listDB)
/*  53:    */     {
/*  54: 84 */       hashAsistencias.put((Integer)objects[1], objects);
/*  55: 85 */       if (incluirDiasNoTrabajados) {
/*  56: 86 */         if (hashAsistenciasPorEmpleado.containsKey(objects[21] + "~" + objects[22] + "~" + objects[23] + "~" + objects[27]))
/*  57:    */         {
/*  58: 87 */           if (objects[0] == null) {
/*  59: 88 */             ((Map)hashAsistenciasPorEmpleado.get(objects[21] + "~" + objects[22] + "~" + objects[23] + "~" + objects[27])).put((Date)objects[2], objects);
/*  60:    */           }
/*  61:    */         }
/*  62:    */         else
/*  63:    */         {
/*  64: 92 */           Map<Date, Object[]> hashAsistenciasPorFecha = new HashMap();
/*  65: 93 */           hashAsistenciasPorFecha.put((Date)objects[2], objects);
/*  66: 94 */           hashAsistenciasPorEmpleado.put(objects[21] + "~" + objects[22] + "~" + objects[23] + "~" + objects[27], hashAsistenciasPorFecha);
/*  67:    */         }
/*  68:    */       }
/*  69: 97 */       if (objects[0] != null) {
/*  70: 98 */         listaIdAsistenciasPadres.add((Integer)objects[0]);
/*  71:    */       }
/*  72:100 */       BigDecimal horasTrabajadas = null;
/*  73:101 */       BigDecimal recesoPlanificado = null;
/*  74:102 */       BigDecimal recesoUtilizado = null;
/*  75:103 */       BigDecimal receso = null;
/*  76:105 */       if ((objects[4] != null) && (objects[10] != null))
/*  77:    */       {
/*  78:106 */         Date horaCalculo = (Date)objects[10];
/*  79:107 */         if (horaCalculo.equals(horaCero)) {
/*  80:108 */           horaCalculo = horaFinDia;
/*  81:    */         }
/*  82:110 */         if (((Date)objects[4]).after(horaCalculo))
/*  83:    */         {
/*  84:113 */           horasTrabajadas = FuncionesUtiles.diferenciasDeHoras((Date)objects[4], horaFinDia);
/*  85:114 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras(horaCero, horaCalculo));
/*  86:    */         }
/*  87:    */         else
/*  88:    */         {
/*  89:116 */           horasTrabajadas = FuncionesUtiles.diferenciasDeHoras((Date)objects[4], horaCalculo);
/*  90:    */         }
/*  91:    */       }
/*  92:120 */       if ((objects[31] != null) && (objects[32] != null))
/*  93:    */       {
/*  94:121 */         Date horaCalculo = (Date)objects[32];
/*  95:122 */         if (horasTrabajadas == null) {
/*  96:123 */           horasTrabajadas = BigDecimal.ZERO;
/*  97:    */         }
/*  98:124 */         if (horaCalculo.equals(horaCero)) {
/*  99:125 */           horaCalculo = horaFinDia;
/* 100:    */         }
/* 101:127 */         if (((Date)objects[31]).after(horaCalculo))
/* 102:    */         {
/* 103:130 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras((Date)objects[31], horaFinDia));
/* 104:131 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras(horaCero, horaCalculo));
/* 105:    */         }
/* 106:    */         else
/* 107:    */         {
/* 108:133 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras((Date)objects[31], horaCalculo));
/* 109:    */         }
/* 110:    */       }
/* 111:137 */       if ((objects[33] != null) && (objects[34] != null))
/* 112:    */       {
/* 113:138 */         Date horaCalculo = (Date)objects[34];
/* 114:139 */         if (horasTrabajadas == null) {
/* 115:140 */           horasTrabajadas = BigDecimal.ZERO;
/* 116:    */         }
/* 117:141 */         if (horaCalculo.equals(horaCero)) {
/* 118:142 */           horaCalculo = horaFinDia;
/* 119:    */         }
/* 120:144 */         if (((Date)objects[33]).after(horaCalculo))
/* 121:    */         {
/* 122:147 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras((Date)objects[33], horaFinDia));
/* 123:148 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras(horaCero, horaCalculo));
/* 124:    */         }
/* 125:    */         else
/* 126:    */         {
/* 127:150 */           horasTrabajadas = horasTrabajadas.add(FuncionesUtiles.diferenciasDeHoras((Date)objects[33], horaCalculo));
/* 128:    */         }
/* 129:    */       }
/* 130:154 */       if ((objects[5] != null) && (objects[7] != null))
/* 131:    */       {
/* 132:155 */         Date horaCalculo = (Date)objects[7];
/* 133:156 */         if (horaCalculo.equals(horaCero)) {
/* 134:157 */           horaCalculo = horaFinDia;
/* 135:    */         }
/* 136:159 */         recesoPlanificado = FuncionesUtiles.diferenciasDeHoras((Date)objects[5], (Date)objects[7]);
/* 137:    */       }
/* 138:162 */       if ((objects[6] != null) && (objects[8] != null))
/* 139:    */       {
/* 140:163 */         Date horaCalculo = (Date)objects[8];
/* 141:164 */         if (horaCalculo.equals(horaCero)) {
/* 142:165 */           horaCalculo = horaFinDia;
/* 143:    */         }
/* 144:167 */         recesoUtilizado = FuncionesUtiles.diferenciasDeHoras((Date)objects[6], (Date)objects[8]);
/* 145:    */       }
/* 146:170 */       if ((recesoUtilizado != null) && (recesoPlanificado != null))
/* 147:    */       {
/* 148:171 */         if (recesoUtilizado.compareTo(recesoPlanificado) == 1) {
/* 149:172 */           receso = recesoUtilizado;
/* 150:    */         } else {
/* 151:174 */           receso = recesoPlanificado;
/* 152:    */         }
/* 153:    */       }
/* 154:176 */       else if (recesoUtilizado != null) {
/* 155:177 */         receso = recesoUtilizado;
/* 156:178 */       } else if (recesoPlanificado != null) {
/* 157:179 */         receso = recesoPlanificado;
/* 158:    */       }
/* 159:181 */       if ((horasTrabajadas != null) && (receso != null))
/* 160:    */       {
/* 161:182 */         horasTrabajadas = horasTrabajadas.subtract(receso);
/* 162:183 */         if (horasTrabajadas.compareTo(BigDecimal.ZERO) < 0) {
/* 163:184 */           horasTrabajadas = BigDecimal.ZERO;
/* 164:    */         }
/* 165:    */       }
/* 166:187 */       objects[28] = horasTrabajadas;
/* 167:188 */       objects[29] = receso;
/* 168:    */     }
/* 169:191 */     for (Object[] objects : listDB) {
/* 170:192 */       if (objects[0] != null)
/* 171:    */       {
/* 172:193 */         obj = (Object[])hashAsistencias.get((Integer)objects[0]);
/* 173:194 */         if (obj != null)
/* 174:    */         {
/* 175:199 */           obj[9] = objects[9];
/* 176:200 */           obj[10] = objects[10];
/* 177:    */           
/* 178:202 */           BigDecimal horasFalta = (BigDecimal)objects[11];
/* 179:203 */           BigDecimal horasPermiso = (BigDecimal)objects[12];
/* 180:204 */           Integer horasSubsidio = (Integer)objects[13];
/* 181:205 */           BigDecimal horasExtras25 = (BigDecimal)objects[14];
/* 182:206 */           BigDecimal horasExtras50 = (BigDecimal)objects[15];
/* 183:207 */           BigDecimal horasExtras100 = (BigDecimal)objects[16];
/* 184:208 */           BigDecimal horasExtras100Feriado = (BigDecimal)objects[17];
/* 185:209 */           BigDecimal horasExtras100FeriadoAprobadas = (BigDecimal)objects[30];
/* 186:210 */           BigDecimal horasExtras25Aprobadas = (BigDecimal)objects[18];
/* 187:211 */           BigDecimal horasExtras50Aprobadas = (BigDecimal)objects[19];
/* 188:212 */           BigDecimal horasExtras100Aprobadas = (BigDecimal)objects[20];
/* 189:213 */           obj[11] = horasFalta.add((BigDecimal)obj[11]);
/* 190:214 */           obj[12] = horasPermiso.add((BigDecimal)obj[12]);
/* 191:215 */           obj[13] = Integer.valueOf(horasSubsidio.intValue() + ((Integer)obj[13]).intValue());
/* 192:216 */           obj[14] = horasExtras25.add((BigDecimal)obj[14]);
/* 193:217 */           obj[15] = horasExtras50.add((BigDecimal)obj[15]);
/* 194:218 */           obj[16] = horasExtras100.add((BigDecimal)obj[16]);
/* 195:219 */           obj[17] = horasExtras100Feriado.add((BigDecimal)obj[17]);
/* 196:220 */           obj[18] = horasExtras25Aprobadas.add((BigDecimal)obj[18]);
/* 197:221 */           obj[19] = horasExtras50Aprobadas.add((BigDecimal)obj[19]);
/* 198:222 */           obj[20] = horasExtras100Aprobadas.add((BigDecimal)obj[20]);
/* 199:223 */           obj[30] = horasExtras100FeriadoAprobadas.add((BigDecimal)obj[30]);
/* 200:224 */           if ((obj[28] != null) && (objects[28] != null)) {
/* 201:225 */             obj[28] = ((BigDecimal)objects[28]).add((BigDecimal)obj[28]);
/* 202:    */           }
/* 203:226 */           if ((obj[29] != null) && (objects[29] != null)) {
/* 204:227 */             obj[29] = ((BigDecimal)objects[29]).add((BigDecimal)obj[29]);
/* 205:228 */           } else if (objects[29] != null) {
/* 206:229 */             obj[29] = objects[29];
/* 207:    */           }
/* 208:231 */           if (objects[31] != null) {
/* 209:232 */             obj[31] = objects[31];
/* 210:    */           }
/* 211:233 */           if (objects[32] != null) {
/* 212:234 */             obj[32] = objects[32];
/* 213:    */           }
/* 214:235 */           if (objects[33] != null) {
/* 215:236 */             obj[33] = objects[33];
/* 216:    */           }
/* 217:237 */           if (objects[34] != null) {
/* 218:238 */             obj[34] = objects[34];
/* 219:    */           }
/* 220:242 */           if (objects[5] != null) {
/* 221:243 */             obj[5] = objects[5];
/* 222:    */           }
/* 223:244 */           if (objects[6] != null) {
/* 224:245 */             obj[6] = objects[6];
/* 225:    */           }
/* 226:246 */           if (objects[7] != null) {
/* 227:247 */             obj[7] = objects[7];
/* 228:    */           }
/* 229:248 */           if (objects[8] != null) {
/* 230:249 */             obj[8] = objects[8];
/* 231:    */           }
/* 232:250 */           listResult.add(obj);
/* 233:    */         }
/* 234:    */       }
/* 235:254 */       else if (!listaIdAsistenciasPadres.contains((Integer)objects[1]))
/* 236:    */       {
/* 237:255 */         listResult.add(objects);
/* 238:    */       }
/* 239:    */     }
/* 240:    */     Object[] obj;
/* 241:258 */     if (incluirDiasNoTrabajados)
/* 242:    */     {
/* 243:259 */       Object listaConDiasNotrabajados = new ArrayList();
/* 244:261 */       for (String st : hashAsistenciasPorEmpleado.keySet())
/* 245:    */       {
/* 246:262 */         Date fechaPivot = fechaDesde;
/* 247:263 */         String[] stEmpleado = st.split("~");
/* 248:264 */         while (fechaPivot.before(fechaHasta))
/* 249:    */         {
/* 250:265 */           Object[] obj = (Object[])((Map)hashAsistenciasPorEmpleado.get(st)).get(fechaPivot);
/* 251:266 */           if (obj != null)
/* 252:    */           {
/* 253:267 */             ((List)listaConDiasNotrabajados).add(obj);
/* 254:    */           }
/* 255:    */           else
/* 256:    */           {
/* 257:269 */             obj = new Object[35];
/* 258:270 */             obj[2] = fechaPivot;
/* 259:271 */             obj[21] = stEmpleado[0];
/* 260:272 */             obj[22] = stEmpleado[1];
/* 261:273 */             obj[23] = stEmpleado[2];
/* 262:274 */             obj[27] = stEmpleado[3];
/* 263:275 */             ((List)listaConDiasNotrabajados).add(obj);
/* 264:    */           }
/* 265:277 */           fechaPivot = FuncionesUtiles.sumarFechaDiasMeses(fechaPivot, 1);
/* 266:    */         }
/* 267:    */       }
/* 268:281 */       listResult = (List<Object[]>)listaConDiasNotrabajados;
/* 269:    */     }
/* 270:284 */     return listResult;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public List<Object[]> getReporteHorarioPersonalizado(Departamento departamento, Date fechaDesde, Date fechaHasta)
/* 274:    */   {
/* 275:290 */     Calendar desdeC = Calendar.getInstance();
/* 276:291 */     desdeC.setTime(fechaDesde);
/* 277:292 */     Calendar hastaC = Calendar.getInstance();
/* 278:293 */     hastaC.setTime(fechaHasta);
/* 279:    */     
/* 280:295 */     List<Object[]> listaMeses = new ArrayList();
/* 281:296 */     int diaInicio = desdeC.get(5);
/* 282:    */     Mes mesHasta;
/* 283:298 */     while (desdeC.before(hastaC))
/* 284:    */     {
/* 285:299 */       desdeC.set(5, 1);
/* 286:300 */       Mes mes = Mes.values()[desdeC.get(2)];
/* 287:301 */       mesHasta = Mes.values()[hastaC.get(2)];
/* 288:302 */       int anio = desdeC.get(1);
/* 289:303 */       int anioHasta = hastaC.get(1);
/* 290:    */       
/* 291:305 */       int diaFin = FuncionesUtiles.getDiaFinMes(desdeC);
/* 292:306 */       if ((mes.equals(mesHasta)) && (anio == anioHasta)) {
/* 293:307 */         diaFin = hastaC.get(5);
/* 294:    */       }
/* 295:310 */       Object[] mensual = { mes, Integer.valueOf(anio), Integer.valueOf(diaInicio), Integer.valueOf(diaFin) };
/* 296:    */       
/* 297:312 */       listaMeses.add(mensual);
/* 298:    */       
/* 299:314 */       desdeC.add(2, 1);
/* 300:315 */       diaInicio = desdeC.get(5);
/* 301:    */     }
/* 302:318 */     List<Object[]> resultado = new ArrayList();
/* 303:319 */     for (Object[] objects : listaMeses) {
/* 304:320 */       resultado.addAll(this.reporteAsistenciaDao.getReporteHorarioPersonalizado(departamento, (Mes)objects[0], ((Integer)objects[1]).intValue(), ((Integer)objects[2]).intValue(), ((Integer)objects[3]).intValue()));
/* 305:    */     }
/* 306:322 */     return resultado;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public List<Object[]> getReporteAsistenciaResumido(Empleado empleado, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 310:    */   {
/* 311:327 */     List<Object[]> listResult = this.reporteAsistenciaDao.getReporteAsistenciaResumido(empleado, departamento, fechaDesde, fechaHasta, idOrganizacion);
/* 312:328 */     Map<String, Object[]> hashTotales = new HashMap();
/* 313:    */     
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:333 */     Date fechaMaxima = FuncionesUtiles.sumarFechaAnios(fechaHasta, 50);
/* 318:335 */     for (Object[] obj : listResult)
/* 319:    */     {
/* 320:336 */       Object[] object = (Object[])hashTotales.get(obj[1] + "~" + obj[4]);
/* 321:337 */       if (object != null)
/* 322:    */       {
/* 323:338 */         object[3] = ((BigDecimal)object[3]).add((BigDecimal)obj[3]);
/* 324:339 */         hashTotales.put(obj[1] + "~" + obj[4], object);
/* 325:    */       }
/* 326:    */       else
/* 327:    */       {
/* 328:341 */         object = new Object[5];
/* 329:342 */         object[0] = obj[0];
/* 330:343 */         object[1] = obj[1];
/* 331:344 */         object[2] = fechaMaxima;
/* 332:345 */         object[3] = obj[3];
/* 333:346 */         object[4] = obj[4];
/* 334:347 */         hashTotales.put(obj[1] + "~" + obj[4], object);
/* 335:    */       }
/* 336:    */     }
/* 337:350 */     listResult.addAll(hashTotales.values());
/* 338:351 */     return listResult;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public List<Object[]> getReporteAsistenciaSemanal(Empleado empleado, Departamento departamento, Date fechaDesde, Date fechaHasta, int idOrganizacion)
/* 342:    */   {
/* 343:357 */     List<Object[]> listResult = new ArrayList();
/* 344:358 */     Date fechaAuxInicio = fechaDesde;
/* 345:359 */     Date fechaAuxFin = FuncionesUtiles.getUltimoDiaSemana(fechaAuxInicio);
/* 346:360 */     Map<String, Object[]> hashEmpleados = new HashMap();
/* 347:    */     
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:369 */     int counter = 10;
/* 356:370 */     while (fechaAuxInicio.getTime() <= fechaHasta.getTime())
/* 357:    */     {
/* 358:372 */       String fecha = counter + "Semana del " + FuncionesUtiles.convertidorFechaALetras(fechaAuxInicio, false) + " al " + FuncionesUtiles.convertidorFechaALetras(fechaAuxFin, false);
/* 359:373 */       List<Object[]> list = this.reporteAsistenciaDao.getReporteAsistenciaResumido(empleado, departamento, fechaAuxInicio, fechaAuxFin, idOrganizacion);
/* 360:374 */       for (Object[] objects : list)
/* 361:    */       {
/* 362:375 */         Object[] o = (Object[])hashEmpleados.get(objects[1] + "~" + fecha + "~" + objects[4]);
/* 363:376 */         if (o != null)
/* 364:    */         {
/* 365:377 */           o[3] = ((BigDecimal)o[3]).add((BigDecimal)objects[3]);
/* 366:378 */           hashEmpleados.put(o[1] + "~" + fecha + "~" + o[4], o);
/* 367:    */         }
/* 368:    */         else
/* 369:    */         {
/* 370:380 */           o = new Object[6];
/* 371:381 */           o[0] = objects[0];
/* 372:382 */           o[1] = objects[1];
/* 373:383 */           o[2] = objects[2];
/* 374:384 */           o[3] = objects[3];
/* 375:385 */           o[4] = objects[4];
/* 376:386 */           o[5] = fecha;
/* 377:387 */           hashEmpleados.put(o[1] + "~" + fecha + "~" + o[4], o);
/* 378:    */         }
/* 379:    */       }
/* 380:390 */       fechaAuxInicio = FuncionesUtiles.sumarFechaDiasMeses(fechaAuxFin, 1);
/* 381:391 */       fechaAuxFin = FuncionesUtiles.getUltimoDiaSemana(fechaAuxInicio);
/* 382:392 */       counter++;
/* 383:    */     }
/* 384:394 */     listResult.addAll(hashEmpleados.values());
/* 385:395 */     Object hashTotales = new HashMap();
/* 386:401 */     for (Object[] obj : listResult)
/* 387:    */     {
/* 388:402 */       Object[] object = (Object[])((Map)hashTotales).get(obj[1] + "~" + obj[4]);
/* 389:403 */       if (object != null)
/* 390:    */       {
/* 391:404 */         object[3] = ((BigDecimal)object[3]).add((BigDecimal)obj[3]);
/* 392:405 */         ((Map)hashTotales).put(obj[1] + "~" + obj[4], object);
/* 393:    */       }
/* 394:    */       else
/* 395:    */       {
/* 396:407 */         object = new Object[6];
/* 397:408 */         object[0] = obj[0];
/* 398:409 */         object[1] = obj[1];
/* 399:410 */         object[3] = obj[3];
/* 400:411 */         object[4] = obj[4];
/* 401:412 */         object[5] = "99Total";
/* 402:413 */         ((Map)hashTotales).put(obj[1] + "~" + obj[4], object);
/* 403:    */       }
/* 404:    */     }
/* 405:416 */     listResult.addAll(((Map)hashTotales).values());
/* 406:417 */     return listResult;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public List<Object[]> getReporteControlAsistenciSobretiempos(Departamento departamento, Date fechaDesde, Empleado responsable)
/* 410:    */   {
/* 411:422 */     Calendar desdeC = Calendar.getInstance();
/* 412:423 */     desdeC.setFirstDayOfWeek(2);
/* 413:424 */     desdeC.setTime(fechaDesde);
/* 414:425 */     List<Object[]> resultado = new ArrayList();
/* 415:426 */     if (desdeC.get(7) == 2) {
/* 416:427 */       resultado.addAll(this.reporteAsistenciaDao.getReporteControlAsistenciSobretiempos(departamento, fechaDesde, responsable));
/* 417:    */     }
/* 418:429 */     return resultado;
/* 419:    */   }
/* 420:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.impl.ServicioReporteAsistenciaImpl
 * JD-Core Version:    0.7.0.1
 */