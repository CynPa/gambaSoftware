/*   1:    */ package com.asinfo.as2.nomina.procesos.compilador;
/*   2:    */ 
/*   3:    */ public class Evaluador
/*   4:    */ {
/*   5:    */   private String cFuncion;
/*   6:    */   private NodoSimple Analizado;
/*   7:    */   private NodoSimple IniAnalizado;
/*   8:    */   private NodoSimple objNodo;
/*   9:    */   private NodoSimple objPasea;
/*  10:    */   private NodoSimple NodoMultip;
/*  11:    */   private NodoSimple NodoPotencia;
/*  12:    */   private int iMultipDivi;
/*  13:    */   private int iPotencia;
/*  14:    */   public int ERRORMATEMATICO;
/*  15:    */   
/*  16:    */   private void vNuevoBloque(double fValor, char cOperador, char cVariable, NodoSimple objACUM)
/*  17:    */   {
/*  18:    */     NodoSimple objNuevoBloque;
/*  19:    */     NodoSimple objNuevoBloque;
/*  20: 60 */     if (objACUM == null)
/*  21:    */     {
/*  22:    */       NodoSimple objNuevoBloque;
/*  23: 61 */       if (cVariable == '@') {
/*  24: 62 */         objNuevoBloque = new NodoSimple(fValor, cOperador);
/*  25:    */       } else {
/*  26: 64 */         objNuevoBloque = new NodoSimple(cVariable, cOperador);
/*  27:    */       }
/*  28:    */     }
/*  29:    */     else
/*  30:    */     {
/*  31: 66 */       objNuevoBloque = new NodoSimple(objACUM, cOperador);
/*  32:    */     }
/*  33: 69 */     if (((cOperador == '+') || (cOperador == '-') || (cOperador == 'N')) && (this.iMultipDivi == 0))
/*  34:    */     {
/*  35: 70 */       this.objPasea.Abajo = objNuevoBloque;
/*  36: 71 */       this.objPasea = this.objPasea.Abajo;
/*  37:    */     }
/*  38: 75 */     else if (this.iMultipDivi == 0)
/*  39:    */     {
/*  40: 77 */       this.iMultipDivi = 1;
/*  41:    */       
/*  42:    */ 
/*  43: 80 */       this.NodoMultip = new NodoSimple(0.0D, 'A');
/*  44:    */       
/*  45:    */ 
/*  46: 83 */       this.objPasea.Abajo = this.NodoMultip;
/*  47:    */       
/*  48:    */ 
/*  49: 86 */       this.objPasea = this.objPasea.Abajo;
/*  50: 89 */       if (cOperador == '^')
/*  51:    */       {
/*  52: 90 */         this.iPotencia = 1;
/*  53:    */         
/*  54:    */ 
/*  55: 93 */         NodoSimple NodoMultiplica = new NodoSimple(1.0D, '*');
/*  56:    */         
/*  57:    */ 
/*  58: 96 */         this.NodoMultip.Derecha = NodoMultiplica;
/*  59:    */         
/*  60:    */ 
/*  61: 99 */         this.NodoPotencia = new NodoSimple(0.0D, 'B');
/*  62:    */         
/*  63:    */ 
/*  64:102 */         NodoMultiplica.Derecha = this.NodoPotencia;
/*  65:    */         
/*  66:    */ 
/*  67:105 */         this.NodoMultip = this.NodoPotencia;
/*  68:    */         
/*  69:    */ 
/*  70:108 */         this.NodoPotencia.Potencia = objNuevoBloque;
/*  71:    */         
/*  72:    */ 
/*  73:111 */         this.NodoPotencia = this.NodoPotencia.Potencia;
/*  74:    */       }
/*  75:    */       else
/*  76:    */       {
/*  77:114 */         this.NodoMultip.Derecha = objNuevoBloque;
/*  78:115 */         this.NodoMultip = this.NodoMultip.Derecha;
/*  79:    */       }
/*  80:    */     }
/*  81:120 */     else if (this.iPotencia == 1)
/*  82:    */     {
/*  83:122 */       this.NodoPotencia.Potencia = objNuevoBloque;
/*  84:123 */       this.NodoPotencia = this.NodoPotencia.Potencia;
/*  85:127 */       if (cOperador != '^')
/*  86:    */       {
/*  87:128 */         this.iPotencia = 0;
/*  88:129 */         this.NodoPotencia.cOperador = 'N';
/*  89:130 */         if ((cOperador == '*') || (cOperador == '/'))
/*  90:    */         {
/*  91:131 */           this.NodoMultip.cOperador = cOperador;
/*  92:    */         }
/*  93:    */         else
/*  94:    */         {
/*  95:134 */           this.NodoMultip.cOperador = '*';
/*  96:135 */           objNuevoBloque = new NodoSimple(1.0D, 'N');
/*  97:136 */           this.NodoMultip.Derecha = objNuevoBloque;
/*  98:137 */           this.objPasea.cOperador = cOperador;
/*  99:138 */           this.iMultipDivi = 0;
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:    */     else
/* 104:    */     {
/* 105:142 */       if (cOperador == '^')
/* 106:    */       {
/* 107:143 */         this.iPotencia = 1;
/* 108:    */         
/* 109:    */ 
/* 110:146 */         this.NodoPotencia = new NodoSimple(0.0D, 'B');
/* 111:    */         
/* 112:    */ 
/* 113:149 */         this.NodoMultip.Derecha = this.NodoPotencia;
/* 114:    */         
/* 115:    */ 
/* 116:152 */         this.NodoMultip = this.NodoPotencia;
/* 117:    */         
/* 118:    */ 
/* 119:155 */         this.NodoPotencia.Potencia = objNuevoBloque;
/* 120:    */         
/* 121:    */ 
/* 122:158 */         this.NodoPotencia = this.NodoPotencia.Potencia;
/* 123:    */       }
/* 124:    */       else
/* 125:    */       {
/* 126:161 */         this.NodoMultip.Derecha = objNuevoBloque;
/* 127:162 */         this.NodoMultip = this.NodoMultip.Derecha;
/* 128:    */       }
/* 129:167 */       if ((cOperador == '+') || (cOperador == '-'))
/* 130:    */       {
/* 131:168 */         this.iMultipDivi = 0;
/* 132:169 */         objNuevoBloque.cOperador = 'N';
/* 133:170 */         this.objPasea.cOperador = cOperador;
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   private double fEvaluaSumaResta()
/* 139:    */   {
/* 140:180 */     double fTotal = this.objNodo.fNumero;
/* 141:181 */     char cOperador = this.objNodo.cOperador;
/* 142:182 */     while (cOperador != 'N')
/* 143:    */     {
/* 144:183 */       this.objNodo = this.objNodo.Abajo;
/* 145:186 */       if (this.objNodo.Derecha != null) {
/* 146:187 */         this.objNodo.fNumero = fEvaluaMultiplicaDivide(this.objNodo);
/* 147:    */       }
/* 148:190 */       if (cOperador == '+')
/* 149:    */       {
/* 150:191 */         if (this.objNodo.objACUM != null) {
/* 151:192 */           fTotal += this.objNodo.objACUM.fNumero;
/* 152:193 */         } else if (this.objNodo.cVariable == 0) {
/* 153:194 */           fTotal += this.objNodo.fNumero;
/* 154:    */         } else {
/* 155:196 */           fTotal += this.fVariables[this.objNodo.cVariable];
/* 156:    */         }
/* 157:    */       }
/* 158:197 */       else if (this.objNodo.objACUM != null) {
/* 159:198 */         fTotal -= this.objNodo.objACUM.fNumero;
/* 160:199 */       } else if (this.objNodo.cVariable == 0) {
/* 161:200 */         fTotal -= this.objNodo.fNumero;
/* 162:    */       } else {
/* 163:202 */         fTotal -= this.fVariables[this.objNodo.cVariable];
/* 164:    */       }
/* 165:203 */       cOperador = this.objNodo.cOperador;
/* 166:    */     }
/* 167:205 */     return fTotal;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private double fEvaluaMultiplicaDivide(NodoSimple objMultiDivi)
/* 171:    */   {
/* 172:212 */     objMultiDivi = objMultiDivi.Derecha;
/* 173:    */     double fTotal;
/* 174:    */     double fTotal;
/* 175:216 */     if (objMultiDivi.objACUM != null)
/* 176:    */     {
/* 177:217 */       fTotal = objMultiDivi.objACUM.fNumero;
/* 178:    */     }
/* 179:    */     else
/* 180:    */     {
/* 181:    */       double fTotal;
/* 182:218 */       if (objMultiDivi.cVariable == 0) {
/* 183:219 */         fTotal = objMultiDivi.fNumero;
/* 184:    */       } else {
/* 185:221 */         fTotal = this.fVariables[objMultiDivi.cVariable];
/* 186:    */       }
/* 187:    */     }
/* 188:223 */     char cOperador = objMultiDivi.cOperador;
/* 189:224 */     while (cOperador != 'N')
/* 190:    */     {
/* 191:225 */       objMultiDivi = objMultiDivi.Derecha;
/* 192:228 */       if (objMultiDivi.Potencia != null) {
/* 193:229 */         objMultiDivi.fNumero = fEvaluaPotencia(objMultiDivi);
/* 194:    */       }
/* 195:231 */       if (cOperador == '*')
/* 196:    */       {
/* 197:232 */         if (objMultiDivi.objACUM != null) {
/* 198:233 */           fTotal *= objMultiDivi.objACUM.fNumero;
/* 199:234 */         } else if (objMultiDivi.cVariable == 0) {
/* 200:235 */           fTotal *= objMultiDivi.fNumero;
/* 201:    */         } else {
/* 202:237 */           fTotal *= this.fVariables[objMultiDivi.cVariable];
/* 203:    */         }
/* 204:    */       }
/* 205:238 */       else if (objMultiDivi.objACUM != null)
/* 206:    */       {
/* 207:239 */         if (objMultiDivi.objACUM.fNumero != 0.0D) {
/* 208:240 */           fTotal /= objMultiDivi.objACUM.fNumero;
/* 209:    */         } else {
/* 210:242 */           this.ERRORMATEMATICO = this.DIVISIONENTRECERO;
/* 211:    */         }
/* 212:    */       }
/* 213:243 */       else if (objMultiDivi.cVariable == 0)
/* 214:    */       {
/* 215:244 */         if (objMultiDivi.fNumero != 0.0D) {
/* 216:245 */           fTotal /= objMultiDivi.fNumero;
/* 217:    */         } else {
/* 218:247 */           this.ERRORMATEMATICO = this.DIVISIONENTRECERO;
/* 219:    */         }
/* 220:    */       }
/* 221:248 */       else if (this.fVariables[objMultiDivi.cVariable] != 0.0D) {
/* 222:249 */         fTotal /= this.fVariables[objMultiDivi.cVariable];
/* 223:    */       } else {
/* 224:251 */         this.ERRORMATEMATICO = this.DIVISIONENTRECERO;
/* 225:    */       }
/* 226:253 */       cOperador = objMultiDivi.cOperador;
/* 227:    */     }
/* 228:255 */     return fTotal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private double fEvaluaPotencia(NodoSimple objPotencia)
/* 232:    */   {
/* 233:261 */     objPotencia = objPotencia.Potencia;
/* 234:    */     double fTotal;
/* 235:    */     double fTotal;
/* 236:265 */     if (objPotencia.objACUM != null)
/* 237:    */     {
/* 238:266 */       fTotal = objPotencia.objACUM.fNumero;
/* 239:    */     }
/* 240:    */     else
/* 241:    */     {
/* 242:    */       double fTotal;
/* 243:267 */       if (objPotencia.cVariable == 0) {
/* 244:268 */         fTotal = objPotencia.fNumero;
/* 245:    */       } else {
/* 246:270 */         fTotal = this.fVariables[objPotencia.cVariable];
/* 247:    */       }
/* 248:    */     }
/* 249:272 */     char cOperador = objPotencia.cOperador;
/* 250:274 */     while (cOperador != 'N')
/* 251:    */     {
/* 252:275 */       objPotencia = objPotencia.Potencia;
/* 253:276 */       if (objPotencia.objACUM != null) {
/* 254:277 */         fTotal = Math.pow(fTotal, objPotencia.objACUM.fNumero);
/* 255:278 */       } else if (objPotencia.cVariable == 0) {
/* 256:279 */         fTotal = Math.pow(fTotal, objPotencia.fNumero);
/* 257:    */       } else {
/* 258:281 */         fTotal = Math.pow(fTotal, this.fVariables[objPotencia.cVariable]);
/* 259:    */       }
/* 260:282 */       cOperador = objPotencia.cOperador;
/* 261:    */     }
/* 262:285 */     return fTotal;
/* 263:    */   }
/* 264:    */   
/* 265:294 */   public int NOERRORES = 0;
/* 266:295 */   public int DIVISIONENTRECERO = 1;
/* 267:296 */   public int ERRORARCOSENO = 2;
/* 268:297 */   public int ERRORARCOCOSENO = 3;
/* 269:300 */   public double[] fVariables = new double[256];
/* 270:    */   
/* 271:    */   public Evaluador()
/* 272:    */   {
/* 273:305 */     this.cFuncion = "sinsencostanabsasnacsatnlogceiexpsqrrcb";
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String MensajeSintaxis(String sExpresion)
/* 277:    */   {
/* 278:334 */     String Respuesta = "";
/* 279:335 */     switch (iAnalizaSintaxis(sExpresion))
/* 280:    */     {
/* 281:    */     case 0: 
/* 282:337 */       Respuesta = "0: Expresión Correcta";
/* 283:338 */       break;
/* 284:    */     case 1: 
/* 285:340 */       Respuesta = "1: Dos o más signos estén seguidos.  Ejemplo: 2++4, 5-*3";
/* 286:341 */       break;
/* 287:    */     case 2: 
/* 288:343 */       Respuesta = "2: Un signo seguido de un paréntesis que cierra.  Ejemplo: 2-(4+)-7";
/* 289:344 */       break;
/* 290:    */     case 3: 
/* 291:346 */       Respuesta = "3: Un paréntesis que abre seguido de un signo. Ejemplo: 2-(*3)";
/* 292:347 */       break;
/* 293:    */     case 4: 
/* 294:349 */       Respuesta = "4: Que empiece con signo +, *, / . Ejemplo: /12-5*2 ,  *17-4";
/* 295:350 */       break;
/* 296:    */     case 5: 
/* 297:352 */       Respuesta = "5: Que termine con signo . Ejemplo:  12-67*  2/3-";
/* 298:353 */       break;
/* 299:    */     case 6: 
/* 300:355 */       Respuesta = "6: Que los paréntesis estén desbalanceados. Ejemplo:  3-(2*4))";
/* 301:356 */       break;
/* 302:    */     case 7: 
/* 303:358 */       Respuesta = "7: Que haya paréntesis vacío. Ejemplo:  2-()*3";
/* 304:359 */       break;
/* 305:    */     case 8: 
/* 306:361 */       Respuesta = "8: Así estén balanceados los paréntesis no corresponde el que abre con el que cierra. Ejemplo: 2+3)-2*(4";
/* 307:362 */       break;
/* 308:    */     case 9: 
/* 309:364 */       Respuesta = "9: Un paréntesis que cierra seguido de un número. Ejemplo: (12-4)7-1";
/* 310:365 */       break;
/* 311:    */     case 10: 
/* 312:367 */       Respuesta = "10: Un número seguido de un paréntesis que abre. Ejemplo: 7-2(5-6)";
/* 313:368 */       break;
/* 314:    */     case 11: 
/* 315:370 */       Respuesta = "11: Doble punto en un número de tipo real. Ejemplo: 3-2..4+1   7-6.46.1+2";
/* 316:371 */       break;
/* 317:    */     case 12: 
/* 318:373 */       Respuesta = "12: Función inexistente:  Ejemplo: xyz(45)";
/* 319:374 */       break;
/* 320:    */     case 13: 
/* 321:376 */       Respuesta = "13: Función no seguida de paréntesis. Ejemplo: 2-sen5";
/* 322:377 */       break;
/* 323:    */     case 14: 
/* 324:379 */       Respuesta = "14: Cadena está vacía";
/* 325:380 */       break;
/* 326:    */     case 15: 
/* 327:382 */       Respuesta = "15: Antes de la función no hay signo o parentesis que abre. Ejemplo: 12cos(3)";
/* 328:383 */       break;
/* 329:    */     case 16: 
/* 330:385 */       Respuesta = "16: Las variables solo son de una sola letra, no debe haber dos letras seguidas. Ejemplo: 3-xy+2*ab";
/* 331:386 */       break;
/* 332:    */     case 17: 
/* 333:388 */       Respuesta = "17: Una variable seguida de un paréntesis que abre. Ejemplo: 7-x(5-6)";
/* 334:389 */       break;
/* 335:    */     case 18: 
/* 336:391 */       Respuesta = "18: Un paréntesis que cierra seguido de una variable. Ejemplo: (12-4)y-1";
/* 337:392 */       break;
/* 338:    */     case 19: 
/* 339:394 */       Respuesta = "19: Una variable seguida de un punto. Ejemplo: 4-z.1+3";
/* 340:395 */       break;
/* 341:    */     case 20: 
/* 342:397 */       Respuesta = "20: Un punto seguido de una variable. Ejemplo: 7-2.p+1";
/* 343:398 */       break;
/* 344:    */     case 21: 
/* 345:400 */       Respuesta = "21: Un número antes o después de una variable. Ejemplo: 3x+1  x21+4  . Nota: Algebraicamente es aceptable 3x+1 pero entonces vuelve mas complejo un evaluador porque debe saber que 3x+1 es en realidad 3*x+1";
/* 346:401 */       break;
/* 347:    */     case 22: 
/* 348:403 */       Respuesta = "22: Hayan caracteres extraños.  4+@-1";
/* 349:    */     }
/* 350:406 */     return Respuesta;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public int iAnalizaSintaxis(String sExpresion)
/* 354:    */   {
/* 355:413 */     String sNueva = "";
/* 356:414 */     for (int iLetra = 0; iLetra < sExpresion.length(); iLetra++) {
/* 357:415 */       if (sExpresion.charAt(iLetra) != ' ') {
/* 358:416 */         sNueva = sNueva + sExpresion.charAt(iLetra);
/* 359:    */       }
/* 360:    */     }
/* 361:417 */     sNueva = sNueva.toLowerCase();
/* 362:420 */     if (sNueva.length() == 0) {
/* 363:421 */       return 14;
/* 364:    */     }
/* 365:424 */     int TamanoExpresion = sNueva.length();
/* 366:    */     
/* 367:426 */     char[] Arreglo = new char[TamanoExpresion];
/* 368:427 */     for (int iCont = 0; iCont < TamanoExpresion; iCont++) {
/* 369:428 */       Arreglo[iCont] = sNueva.charAt(iCont);
/* 370:    */     }
/* 371:431 */     if ((Arreglo[0] == '^') || (Arreglo[0] == '*') || (Arreglo[0] == '+') || (Arreglo[0] == '/')) {
/* 372:432 */       return 4;
/* 373:    */     }
/* 374:436 */     for (iCont = 1; iCont < TamanoExpresion - 2; iCont++) {
/* 375:437 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 376:438 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z') && 
/* 377:439 */         (Arreglo[(iCont + 2)] >= 'a') && (Arreglo[(iCont + 2)] <= 'z') && 
/* 378:440 */         (Arreglo[(iCont - 1)] != '(') && (Arreglo[(iCont - 1)] != '+') && (Arreglo[(iCont - 1)] != '-') && (Arreglo[(iCont - 1)] != '*') && (Arreglo[(iCont - 1)] != '/') && (Arreglo[(iCont - 1)] != '^')) {
/* 379:442 */         return 15;
/* 380:    */       }
/* 381:    */     }
/* 382:445 */     for (iCont = 0; iCont < TamanoExpresion - 3; iCont++) {
/* 383:446 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 384:447 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z') && 
/* 385:448 */         (Arreglo[(iCont + 2)] >= 'a') && (Arreglo[(iCont + 2)] <= 'z') && 
/* 386:449 */         (Arreglo[(iCont + 3)] != '(')) {
/* 387:450 */         return 13;
/* 388:    */       }
/* 389:    */     }
/* 390:453 */     int TamanoFuncion = this.cFuncion.length();
/* 391:454 */     for (iCont = 0; iCont < TamanoExpresion - 2; iCont++)
/* 392:    */     {
/* 393:455 */       boolean bNoExiste = true;
/* 394:456 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 395:457 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z') && 
/* 396:458 */         (Arreglo[(iCont + 2)] >= 'a') && (Arreglo[(iCont + 2)] <= 'z'))
/* 397:    */       {
/* 398:459 */         for (int iPosible = 0; iPosible < TamanoFuncion; iPosible += 3) {
/* 399:460 */           if ((this.cFuncion.charAt(iPosible) == Arreglo[iCont]) && (this.cFuncion.charAt(iPosible + 1) == Arreglo[(iCont + 1)]) && 
/* 400:461 */             (this.cFuncion.charAt(iPosible + 2) == Arreglo[(iCont + 2)])) {
/* 401:462 */             bNoExiste = false;
/* 402:    */           }
/* 403:    */         }
/* 404:464 */         if (bNoExiste) {
/* 405:465 */           return 12;
/* 406:    */         }
/* 407:    */       }
/* 408:    */     }
/* 409:469 */     for (iCont = 0; iCont < TamanoExpresion - 1; iCont++)
/* 410:    */     {
/* 411:471 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 412:472 */         (Arreglo[(iCont + 1)] == '.')) {
/* 413:473 */         return 19;
/* 414:    */       }
/* 415:476 */       if ((Arreglo[iCont] == '.') && 
/* 416:477 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z')) {
/* 417:478 */         return 20;
/* 418:    */       }
/* 419:482 */       if ((Arreglo[iCont] == ')') && 
/* 420:483 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z')) {
/* 421:484 */         return 18;
/* 422:    */       }
/* 423:487 */       if (((Arreglo[iCont] == '+') || (Arreglo[iCont] == '-') || (Arreglo[iCont] == '*') || (Arreglo[iCont] == '/') || (Arreglo[iCont] == '^')) && (
/* 424:488 */         (Arreglo[(iCont + 1)] == '+') || (Arreglo[(iCont + 1)] == '-') || (Arreglo[(iCont + 1)] == '*') || (Arreglo[(iCont + 1)] == '/') || (Arreglo[(iCont + 1)] == '^'))) {
/* 425:490 */         return 1;
/* 426:    */       }
/* 427:494 */       if (((Arreglo[iCont] == '+') || (Arreglo[iCont] == '-') || (Arreglo[iCont] == '*') || (Arreglo[iCont] == '/') || (Arreglo[iCont] == '^')) && 
/* 428:495 */         (Arreglo[(iCont + 1)] == ')')) {
/* 429:496 */         return 2;
/* 430:    */       }
/* 431:499 */       if ((Arreglo[iCont] == '(') && (
/* 432:500 */         (Arreglo[(iCont + 1)] == '+') || (Arreglo[(iCont + 1)] == '*') || (Arreglo[(iCont + 1)] == '/') || (Arreglo[(iCont + 1)] == '^'))) {
/* 433:501 */         return 3;
/* 434:    */       }
/* 435:504 */       if ((Arreglo[iCont] == '(') && (Arreglo[(iCont + 1)] == ')')) {
/* 436:505 */         return 7;
/* 437:    */       }
/* 438:509 */       if ((Arreglo[iCont] == ')') && (Arreglo[(iCont + 1)] >= '0') && (Arreglo[(iCont + 1)] <= '9')) {
/* 439:510 */         return 9;
/* 440:    */       }
/* 441:514 */       if ((Arreglo[iCont] >= '0') && (Arreglo[iCont] <= '9') && (Arreglo[(iCont + 1)] == '(')) {
/* 442:515 */         return 10;
/* 443:    */       }
/* 444:    */     }
/* 445:519 */     int iParentesis = 0;
/* 446:520 */     for (iCont = 0; iCont < TamanoExpresion; iCont++)
/* 447:    */     {
/* 448:521 */       if (Arreglo[iCont] == '(') {
/* 449:522 */         iParentesis++;
/* 450:    */       }
/* 451:523 */       if (Arreglo[iCont] == ')') {
/* 452:524 */         iParentesis--;
/* 453:    */       }
/* 454:    */     }
/* 455:526 */     if (iParentesis != 0) {
/* 456:527 */       return 6;
/* 457:    */     }
/* 458:533 */     int iPunto = 0;
/* 459:534 */     for (iCont = 0; iCont < TamanoExpresion; iCont++)
/* 460:    */     {
/* 461:535 */       if (Arreglo[iCont] == '(') {
/* 462:536 */         iParentesis++;
/* 463:    */       }
/* 464:537 */       if (Arreglo[iCont] == ')') {
/* 465:538 */         iParentesis--;
/* 466:    */       }
/* 467:539 */       if (iParentesis < 0) {
/* 468:540 */         return 8;
/* 469:    */       }
/* 470:542 */       if ((Arreglo[iCont] == '+') || (Arreglo[iCont] == '-') || (Arreglo[iCont] == '*') || (Arreglo[iCont] == '/') || (Arreglo[iCont] == '^')) {
/* 471:543 */         iPunto = 0;
/* 472:    */       }
/* 473:545 */       if (Arreglo[iCont] == '.') {
/* 474:546 */         iPunto++;
/* 475:    */       }
/* 476:547 */       if (iPunto > 1) {
/* 477:548 */         return 11;
/* 478:    */       }
/* 479:    */     }
/* 480:552 */     if ((Arreglo[(TamanoExpresion - 1)] == '^') || (Arreglo[(TamanoExpresion - 1)] == '-') || (Arreglo[(TamanoExpresion - 1)] == '*') || (Arreglo[(TamanoExpresion - 1)] == '+') || (Arreglo[(TamanoExpresion - 1)] == '/')) {
/* 481:554 */       return 5;
/* 482:    */     }
/* 483:559 */     for (iCont = TamanoExpresion - 1; iCont >= 3; iCont--) {
/* 484:560 */       if (Arreglo[iCont] == '(')
/* 485:    */       {
/* 486:561 */         char cLetraA = Arreglo[(iCont - 3)];
/* 487:562 */         char cLetraB = Arreglo[(iCont - 2)];
/* 488:563 */         char cLetraC = Arreglo[(iCont - 1)];
/* 489:566 */         for (int iLetra = 0; iLetra < this.cFuncion.length() - 3; iLetra += 3) {
/* 490:567 */           if ((cLetraA == this.cFuncion.charAt(iLetra)) && (cLetraB == this.cFuncion.charAt(iLetra + 1)) && (cLetraC == this.cFuncion.charAt(iLetra + 2)))
/* 491:    */           {
/* 492:568 */             Arreglo[(iCont - 3)] = '0';
/* 493:569 */             Arreglo[(iCont - 2)] = '0';
/* 494:570 */             Arreglo[(iCont - 1)] = '+';
/* 495:    */           }
/* 496:    */         }
/* 497:    */       }
/* 498:    */     }
/* 499:576 */     for (iCont = 0; iCont < TamanoExpresion - 1; iCont++)
/* 500:    */     {
/* 501:579 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 502:580 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z')) {
/* 503:581 */         return 16;
/* 504:    */       }
/* 505:585 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 506:586 */         (Arreglo[(iCont + 1)] == '(')) {
/* 507:587 */         return 17;
/* 508:    */       }
/* 509:593 */       if ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z') && 
/* 510:594 */         (Arreglo[(iCont + 1)] >= '0') && (Arreglo[(iCont + 1)] <= '9')) {
/* 511:595 */         return 21;
/* 512:    */       }
/* 513:597 */       if ((Arreglo[iCont] >= '0') && (Arreglo[iCont] <= '9') && 
/* 514:598 */         (Arreglo[(iCont + 1)] >= 'a') && (Arreglo[(iCont + 1)] <= 'z')) {
/* 515:599 */         return 21;
/* 516:    */       }
/* 517:    */     }
/* 518:603 */     for (iCont = 0; iCont < TamanoExpresion; iCont++) {
/* 519:604 */       if (((Arreglo[iCont] >= '0') && (Arreglo[iCont] <= '9')) || ((Arreglo[iCont] >= 'a') && (Arreglo[iCont] <= 'z')) || (Arreglo[iCont] == '+') || (Arreglo[iCont] == '-') || (Arreglo[iCont] == '*') || (Arreglo[iCont] == '/') || (Arreglo[iCont] == '^') || (Arreglo[iCont] == '(') || (Arreglo[iCont] == ')')) {
/* 520:607 */         Arreglo[iCont] = '.';
/* 521:    */       }
/* 522:    */     }
/* 523:611 */     for (iCont = 0; iCont < TamanoExpresion; iCont++) {
/* 524:612 */       if (Arreglo[iCont] != '.') {
/* 525:613 */         return 22;
/* 526:    */       }
/* 527:    */     }
/* 528:616 */     return 0;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void vAnaliza(String sExpresion)
/* 532:    */   {
/* 533:623 */     double fValor = 0.0D;
/* 534:624 */     double iDecimal = 0.0D;
/* 535:625 */     double iFraccion = 0.0D;
/* 536:626 */     double dDivide = 1.0D;
/* 537:627 */     boolean bPunto = false;
/* 538:    */     
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:    */ 
/* 543:633 */     char cVariable = '@';
/* 544:    */     
/* 545:635 */     int iACUMULADO = 1;
/* 546:636 */     NodoSimple objACUMULADOR = null;
/* 547:    */     
/* 548:    */ 
/* 549:    */ 
/* 550:640 */     NodoDoble objListaDoble = new NodoDoble('(');
/* 551:    */     
/* 552:    */ 
/* 553:    */ 
/* 554:644 */     String sNueva = sExpresion.toLowerCase();
/* 555:645 */     for (int iLetra = 0; iLetra < sNueva.length(); iLetra++) {
/* 556:646 */       if (sNueva.charAt(iLetra) != ' ') {
/* 557:648 */         objListaDoble = new NodoDoble(sNueva.charAt(iLetra), objListaDoble);
/* 558:    */       }
/* 559:    */     }
/* 560:650 */     objListaDoble = new NodoDoble(')', objListaDoble);
/* 561:    */     
/* 562:    */ 
/* 563:    */ 
/* 564:    */ 
/* 565:    */ 
/* 566:    */ 
/* 567:    */ 
/* 568:658 */     this.Analizado = null;
/* 569:659 */     this.IniAnalizado = null;
/* 570:663 */     while (objListaDoble != null) {
/* 571:666 */       if (objListaDoble.cLetra == '(')
/* 572:    */       {
/* 573:668 */         NodoDoble objIni = objListaDoble;
/* 574:672 */         while (objListaDoble.cLetra != ')') {
/* 575:673 */           objListaDoble = objListaDoble.FlechaD;
/* 576:    */         }
/* 577:676 */         NodoDoble objFin = objListaDoble;
/* 578:683 */         if (this.Analizado == null)
/* 579:    */         {
/* 580:684 */           this.Analizado = new NodoSimple();
/* 581:685 */           this.IniAnalizado = this.Analizado;
/* 582:    */         }
/* 583:    */         else
/* 584:    */         {
/* 585:687 */           this.Analizado = new NodoSimple(this.Analizado);
/* 586:    */         }
/* 587:690 */         this.objNodo = new NodoSimple(0.0D, '+');
/* 588:691 */         this.objPasea = this.objNodo;
/* 589:692 */         this.iMultipDivi = 0;
/* 590:693 */         this.iPotencia = 0;
/* 591:    */         
/* 592:    */ 
/* 593:696 */         boolean bNegativoInicio = false;
/* 594:697 */         if (objIni.FlechaD.cLetra == '-') {
/* 595:698 */           bNegativoInicio = true;
/* 596:    */         }
/* 597:702 */         NodoDoble objExtrae = objIni;
/* 598:703 */         while (objExtrae.FlechaD != objFin)
/* 599:    */         {
/* 600:706 */           objExtrae = objExtrae.FlechaD;
/* 601:709 */           if (objExtrae.iACUM >= 0)
/* 602:    */           {
/* 603:711 */             int iACUM = 1;
/* 604:712 */             objACUMULADOR = this.IniAnalizado;
/* 605:713 */             while (iACUM < objExtrae.iACUM)
/* 606:    */             {
/* 607:714 */               objACUMULADOR = objACUMULADOR.Abajo;
/* 608:715 */               iACUM++;
/* 609:    */             }
/* 610:    */           }
/* 611:720 */           else if (((objExtrae.cLetra >= '0') && (objExtrae.cLetra <= '9')) || (objExtrae.cLetra == '.'))
/* 612:    */           {
/* 613:721 */             if (objExtrae.cLetra == '.')
/* 614:    */             {
/* 615:722 */               bPunto = true;
/* 616:    */             }
/* 617:723 */             else if (!bPunto)
/* 618:    */             {
/* 619:724 */               iDecimal = iDecimal * 10.0D + objExtrae.cLetra - 48.0D;
/* 620:    */             }
/* 621:    */             else
/* 622:    */             {
/* 623:726 */               iFraccion = iFraccion * 10.0D + objExtrae.cLetra - 48.0D;
/* 624:727 */               dDivide *= 10.0D;
/* 625:    */             }
/* 626:730 */             cVariable = '@';
/* 627:731 */             objACUMULADOR = null;
/* 628:    */           }
/* 629:734 */           else if ((objExtrae.cLetra >= 'a') && (objExtrae.cLetra <= 'z'))
/* 630:    */           {
/* 631:735 */             objACUMULADOR = null;
/* 632:736 */             cVariable = objExtrae.cLetra;
/* 633:    */           }
/* 634:    */           else
/* 635:    */           {
/* 636:743 */             fValor = iDecimal + iFraccion / dDivide;
/* 637:744 */             iDecimal = iFraccion = 0.0D;
/* 638:745 */             dDivide = 1.0D;
/* 639:746 */             bPunto = false;
/* 640:747 */             if (bNegativoInicio)
/* 641:    */             {
/* 642:748 */               vNuevoBloque(0.0D, '-', '0', null);
/* 643:749 */               bNegativoInicio = false;
/* 644:    */             }
/* 645:    */             else
/* 646:    */             {
/* 647:751 */               vNuevoBloque(fValor, objExtrae.cLetra, cVariable, objACUMULADOR);
/* 648:    */             }
/* 649:752 */             objACUMULADOR = null;
/* 650:    */           }
/* 651:    */         }
/* 652:757 */         fValor = iDecimal + iFraccion / dDivide;
/* 653:758 */         iDecimal = iFraccion = 0.0D;
/* 654:759 */         dDivide = 1.0D;
/* 655:760 */         bPunto = false;
/* 656:761 */         vNuevoBloque(fValor, '+', cVariable, objACUMULADOR);
/* 657:762 */         vNuevoBloque(0.0D, 'N', '0', null);
/* 658:    */         
/* 659:    */ 
/* 660:765 */         this.Analizado.Derecha = this.objNodo;
/* 661:    */         
/* 662:    */ 
/* 663:768 */         objIni.iACUM = (iACUMULADO++);
/* 664:    */         
/* 665:    */ 
/* 666:    */ 
/* 667:772 */         objIni.FlechaD = objFin.FlechaD;
/* 668:773 */         if (objIni.FlechaI == null) {
/* 669:    */           break;
/* 670:    */         }
/* 671:778 */         if ((objIni.FlechaI.cLetra >= 'a') && (objIni.FlechaI.cLetra <= 'z'))
/* 672:    */         {
/* 673:780 */           objIni = objIni.FlechaI.FlechaI.FlechaI;
/* 674:    */           
/* 675:782 */           int iFuncion = 1;
/* 676:783 */           for (int Letra = 0; Letra < this.cFuncion.length(); Letra += 3)
/* 677:    */           {
/* 678:784 */             if ((objIni.cLetra == this.cFuncion.charAt(Letra)) && (objIni.FlechaD.cLetra == this.cFuncion.charAt(Letra + 1)) && 
/* 679:785 */               (objIni.FlechaD.FlechaD.cLetra == this.cFuncion.charAt(Letra + 2)))
/* 680:    */             {
/* 681:786 */               this.objNodo = new NodoSimple();
/* 682:787 */               this.objNodo.iFuncion = iFuncion;
/* 683:788 */               this.Analizado = new NodoSimple(this.Analizado);
/* 684:789 */               this.Analizado.Derecha = this.objNodo;
/* 685:790 */               break;
/* 686:    */             }
/* 687:792 */             iFuncion++;
/* 688:    */           }
/* 689:796 */           NodoDoble objTemp = objIni.FlechaD.FlechaD.FlechaD.FlechaD;
/* 690:797 */           objIni.FlechaD = objTemp;
/* 691:798 */           objIni.iACUM = (iACUMULADO++);
/* 692:    */         }
/* 693:802 */         objListaDoble = objIni.FlechaI;
/* 694:    */       }
/* 695:    */       else
/* 696:    */       {
/* 697:804 */         objListaDoble = objListaDoble.FlechaI;
/* 698:    */       }
/* 699:    */     }
/* 700:    */   }
/* 701:    */   
/* 702:    */   public double fEvaluar()
/* 703:    */   {
/* 704:812 */     this.ERRORMATEMATICO = this.NOERRORES;
/* 705:813 */     double fValor = 0.0D;
/* 706:    */     
/* 707:815 */     NodoSimple objNavegar = this.IniAnalizado;
/* 708:    */     for (;;)
/* 709:    */     {
/* 710:817 */       if (objNavegar.Derecha != null)
/* 711:    */       {
/* 712:818 */         this.objNodo = objNavegar.Derecha;
/* 713:819 */         if (this.objNodo.iFuncion == 0)
/* 714:    */         {
/* 715:820 */           fValor = fEvaluaSumaResta();
/* 716:821 */           objNavegar.fNumero = fValor;
/* 717:822 */           if (this.ERRORMATEMATICO != 0) {
/* 718:823 */             return 0.0D;
/* 719:    */           }
/* 720:    */         }
/* 721:    */         else
/* 722:    */         {
/* 723:826 */           switch (this.objNodo.iFuncion)
/* 724:    */           {
/* 725:    */           case 1: 
/* 726:    */           case 2: 
/* 727:829 */             objNavegar.fNumero = Math.sin(fValor);
/* 728:830 */             break;
/* 729:    */           case 3: 
/* 730:832 */             objNavegar.fNumero = Math.cos(fValor);
/* 731:833 */             break;
/* 732:    */           case 4: 
/* 733:835 */             objNavegar.fNumero = Math.tan(fValor);
/* 734:836 */             break;
/* 735:    */           case 5: 
/* 736:838 */             if (fValor < 0.0D) {
/* 737:839 */               objNavegar.fNumero = (-fValor);
/* 738:    */             } else {
/* 739:841 */               objNavegar.fNumero = fValor;
/* 740:    */             }
/* 741:842 */             break;
/* 742:    */           case 6: 
/* 743:844 */             if ((fValor >= -1.0D) && (fValor <= 1.0D))
/* 744:    */             {
/* 745:845 */               objNavegar.fNumero = Math.asin(fValor);
/* 746:    */             }
/* 747:    */             else
/* 748:    */             {
/* 749:847 */               objNavegar.fNumero = 0.0D;
/* 750:848 */               this.ERRORMATEMATICO = this.ERRORARCOSENO;
/* 751:849 */               return 0.0D;
/* 752:    */             }
/* 753:    */             break;
/* 754:    */           case 7: 
/* 755:853 */             if ((fValor >= -1.0D) && (fValor <= 1.0D))
/* 756:    */             {
/* 757:854 */               objNavegar.fNumero = Math.acos(fValor);
/* 758:    */             }
/* 759:    */             else
/* 760:    */             {
/* 761:856 */               objNavegar.fNumero = 0.0D;
/* 762:857 */               this.ERRORMATEMATICO = this.ERRORARCOCOSENO;
/* 763:858 */               return 0.0D;
/* 764:    */             }
/* 765:    */             break;
/* 766:    */           case 8: 
/* 767:862 */             objNavegar.fNumero = Math.atan(fValor);
/* 768:863 */             break;
/* 769:    */           case 9: 
/* 770:865 */             objNavegar.fNumero = Math.log(fValor);
/* 771:866 */             break;
/* 772:    */           case 10: 
/* 773:868 */             objNavegar.fNumero = Math.ceil(fValor);
/* 774:869 */             break;
/* 775:    */           case 11: 
/* 776:871 */             objNavegar.fNumero = Math.exp(fValor);
/* 777:872 */             break;
/* 778:    */           case 12: 
/* 779:874 */             objNavegar.fNumero = Math.sqrt(fValor);
/* 780:875 */             break;
/* 781:    */           case 13: 
/* 782:877 */             objNavegar.fNumero = Math.pow(fValor, 0.3333333333333333D);
/* 783:    */           }
/* 784:    */         }
/* 785:    */       }
/* 786:884 */       if (objNavegar.Abajo == null) {
/* 787:    */         break;
/* 788:    */       }
/* 789:885 */       objNavegar = objNavegar.Abajo;
/* 790:    */     }
/* 791:889 */     return objNavegar.fNumero;
/* 792:    */   }
/* 793:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.compilador.Evaluador
 * JD-Core Version:    0.7.0.1
 */