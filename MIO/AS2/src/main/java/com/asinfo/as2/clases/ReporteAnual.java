/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ 
/*   5:    */ public class ReporteAnual
/*   6:    */ {
/*   7:    */   private int id;
/*   8:    */   private String nombre;
/*   9:    */   private String descripcion;
/*  10:    */   private BigDecimal valorEnero;
/*  11:    */   private BigDecimal valorFebrero;
/*  12:    */   private BigDecimal valorMarzo;
/*  13:    */   private BigDecimal valorAbril;
/*  14:    */   private BigDecimal valorMayo;
/*  15:    */   private BigDecimal valorJunio;
/*  16:    */   private BigDecimal valorJulio;
/*  17:    */   private BigDecimal valorAgosto;
/*  18:    */   private BigDecimal valorSeptiembre;
/*  19:    */   private BigDecimal valorOctubre;
/*  20:    */   private BigDecimal valorNoviembre;
/*  21:    */   private BigDecimal valorDiciembre;
/*  22:    */   private String notaEnero;
/*  23:    */   private String notaFebrero;
/*  24:    */   private String notaMarzo;
/*  25:    */   private String notaAbril;
/*  26:    */   private String notaMayo;
/*  27:    */   private String notaJunio;
/*  28:    */   private String notaJulio;
/*  29:    */   private String notaAgosto;
/*  30:    */   private String notaSeptiembre;
/*  31:    */   private String notaOctubre;
/*  32:    */   private String notaNoviembre;
/*  33:    */   private String notaDiciembre;
/*  34:    */   
/*  35:    */   public void setValor(int mes, BigDecimal valor)
/*  36:    */   {
/*  37: 54 */     switch (mes)
/*  38:    */     {
/*  39:    */     case 1: 
/*  40: 56 */       setValorEnero(valor);
/*  41: 57 */       break;
/*  42:    */     case 2: 
/*  43: 59 */       setValorFebrero(valor);
/*  44: 60 */       break;
/*  45:    */     case 3: 
/*  46: 62 */       setValorMarzo(valor);
/*  47: 63 */       break;
/*  48:    */     case 4: 
/*  49: 65 */       setValorAbril(valor);
/*  50: 66 */       break;
/*  51:    */     case 5: 
/*  52: 68 */       setValorMayo(valor);
/*  53: 69 */       break;
/*  54:    */     case 6: 
/*  55: 71 */       setValorJunio(valor);
/*  56: 72 */       break;
/*  57:    */     case 7: 
/*  58: 74 */       setValorJulio(valor);
/*  59: 75 */       break;
/*  60:    */     case 8: 
/*  61: 77 */       setValorAgosto(valor);
/*  62: 78 */       break;
/*  63:    */     case 9: 
/*  64: 80 */       setValorSeptiembre(valor);
/*  65: 81 */       break;
/*  66:    */     case 10: 
/*  67: 83 */       setValorOctubre(valor);
/*  68: 84 */       break;
/*  69:    */     case 11: 
/*  70: 86 */       setValorNoviembre(valor);
/*  71: 87 */       break;
/*  72:    */     case 12: 
/*  73: 89 */       setValorDiciembre(valor);
/*  74: 90 */       break;
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setNota(int mes, String nota)
/*  79:    */   {
/*  80: 98 */     switch (mes)
/*  81:    */     {
/*  82:    */     case 1: 
/*  83:100 */       setNotaEnero(nota);
/*  84:101 */       break;
/*  85:    */     case 2: 
/*  86:103 */       setNotaFebrero(nota);
/*  87:104 */       break;
/*  88:    */     case 3: 
/*  89:106 */       setNotaMarzo(nota);
/*  90:107 */       break;
/*  91:    */     case 4: 
/*  92:109 */       setNotaAbril(nota);
/*  93:110 */       break;
/*  94:    */     case 5: 
/*  95:112 */       setNotaMayo(nota);
/*  96:113 */       break;
/*  97:    */     case 6: 
/*  98:115 */       setNotaJunio(nota);
/*  99:116 */       break;
/* 100:    */     case 7: 
/* 101:118 */       setNotaJulio(nota);
/* 102:119 */       break;
/* 103:    */     case 8: 
/* 104:121 */       setNotaAgosto(nota);
/* 105:122 */       break;
/* 106:    */     case 9: 
/* 107:124 */       setNotaSeptiembre(nota);
/* 108:125 */       break;
/* 109:    */     case 10: 
/* 110:127 */       setNotaOctubre(nota);
/* 111:128 */       break;
/* 112:    */     case 11: 
/* 113:130 */       setNotaNoviembre(nota);
/* 114:131 */       break;
/* 115:    */     case 12: 
/* 116:133 */       setNotaDiciembre(nota);
/* 117:134 */       break;
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:142 */     return this.id;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setId(int id)
/* 127:    */   {
/* 128:146 */     this.id = id;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getNombre()
/* 132:    */   {
/* 133:150 */     return this.nombre;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setNombre(String nombre)
/* 137:    */   {
/* 138:154 */     this.nombre = nombre;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getDescripcion()
/* 142:    */   {
/* 143:158 */     return this.descripcion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDescripcion(String descripcion)
/* 147:    */   {
/* 148:162 */     this.descripcion = descripcion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public BigDecimal getValorEnero()
/* 152:    */   {
/* 153:166 */     return this.valorEnero;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setValorEnero(BigDecimal valorEnero)
/* 157:    */   {
/* 158:170 */     this.valorEnero = valorEnero;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BigDecimal getValorFebrero()
/* 162:    */   {
/* 163:174 */     return this.valorFebrero;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setValorFebrero(BigDecimal valorFebrero)
/* 167:    */   {
/* 168:178 */     this.valorFebrero = valorFebrero;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public BigDecimal getValorMarzo()
/* 172:    */   {
/* 173:182 */     return this.valorMarzo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setValorMarzo(BigDecimal valorMarzo)
/* 177:    */   {
/* 178:186 */     this.valorMarzo = valorMarzo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public BigDecimal getValorAbril()
/* 182:    */   {
/* 183:190 */     return this.valorAbril;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setValorAbril(BigDecimal valorAbril)
/* 187:    */   {
/* 188:194 */     this.valorAbril = valorAbril;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public BigDecimal getValorMayo()
/* 192:    */   {
/* 193:198 */     return this.valorMayo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setValorMayo(BigDecimal valorMayo)
/* 197:    */   {
/* 198:202 */     this.valorMayo = valorMayo;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public BigDecimal getValorJunio()
/* 202:    */   {
/* 203:206 */     return this.valorJunio;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setValorJunio(BigDecimal valorJunio)
/* 207:    */   {
/* 208:210 */     this.valorJunio = valorJunio;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public BigDecimal getValorJulio()
/* 212:    */   {
/* 213:214 */     return this.valorJulio;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setValorJulio(BigDecimal valorJulio)
/* 217:    */   {
/* 218:218 */     this.valorJulio = valorJulio;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public BigDecimal getValorAgosto()
/* 222:    */   {
/* 223:222 */     return this.valorAgosto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setValorAgosto(BigDecimal valorAgosto)
/* 227:    */   {
/* 228:226 */     this.valorAgosto = valorAgosto;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public BigDecimal getValorSeptiembre()
/* 232:    */   {
/* 233:230 */     return this.valorSeptiembre;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setValorSeptiembre(BigDecimal valorSeptiembre)
/* 237:    */   {
/* 238:234 */     this.valorSeptiembre = valorSeptiembre;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public BigDecimal getValorOctubre()
/* 242:    */   {
/* 243:238 */     return this.valorOctubre;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setValorOctubre(BigDecimal valorOctubre)
/* 247:    */   {
/* 248:242 */     this.valorOctubre = valorOctubre;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public BigDecimal getValorNoviembre()
/* 252:    */   {
/* 253:246 */     return this.valorNoviembre;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setValorNoviembre(BigDecimal valorNoviembre)
/* 257:    */   {
/* 258:250 */     this.valorNoviembre = valorNoviembre;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public BigDecimal getValorDiciembre()
/* 262:    */   {
/* 263:254 */     return this.valorDiciembre;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setValorDiciembre(BigDecimal valorDiciembre)
/* 267:    */   {
/* 268:258 */     this.valorDiciembre = valorDiciembre;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getNotaEnero()
/* 272:    */   {
/* 273:262 */     return this.notaEnero;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setNotaEnero(String notaEnero)
/* 277:    */   {
/* 278:266 */     this.notaEnero = notaEnero;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String getNotaFebrero()
/* 282:    */   {
/* 283:270 */     return this.notaFebrero;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setNotaFebrero(String notaFebrero)
/* 287:    */   {
/* 288:274 */     this.notaFebrero = notaFebrero;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public String getNotaMarzo()
/* 292:    */   {
/* 293:278 */     return this.notaMarzo;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setNotaMarzo(String notaMarzo)
/* 297:    */   {
/* 298:282 */     this.notaMarzo = notaMarzo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public String getNotaAbril()
/* 302:    */   {
/* 303:286 */     return this.notaAbril;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setNotaAbril(String notaAbril)
/* 307:    */   {
/* 308:290 */     this.notaAbril = notaAbril;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public String getNotaMayo()
/* 312:    */   {
/* 313:294 */     return this.notaMayo;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setNotaMayo(String notaMayo)
/* 317:    */   {
/* 318:298 */     this.notaMayo = notaMayo;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String getNotaJunio()
/* 322:    */   {
/* 323:302 */     return this.notaJunio;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setNotaJunio(String notaJunio)
/* 327:    */   {
/* 328:306 */     this.notaJunio = notaJunio;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public String getNotaJulio()
/* 332:    */   {
/* 333:310 */     return this.notaJulio;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setNotaJulio(String notaJulio)
/* 337:    */   {
/* 338:314 */     this.notaJulio = notaJulio;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public String getNotaAgosto()
/* 342:    */   {
/* 343:318 */     return this.notaAgosto;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setNotaAgosto(String notaAgosto)
/* 347:    */   {
/* 348:322 */     this.notaAgosto = notaAgosto;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public String getNotaSeptiembre()
/* 352:    */   {
/* 353:326 */     return this.notaSeptiembre;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setNotaSeptiembre(String notaSeptiembre)
/* 357:    */   {
/* 358:330 */     this.notaSeptiembre = notaSeptiembre;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public String getNotaOctubre()
/* 362:    */   {
/* 363:334 */     return this.notaOctubre;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setNotaOctubre(String notaOctubre)
/* 367:    */   {
/* 368:338 */     this.notaOctubre = notaOctubre;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public String getNotaNoviembre()
/* 372:    */   {
/* 373:342 */     return this.notaNoviembre;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setNotaNoviembre(String notaNoviembre)
/* 377:    */   {
/* 378:346 */     this.notaNoviembre = notaNoviembre;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String getNotaDiciembre()
/* 382:    */   {
/* 383:350 */     return this.notaDiciembre;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setNotaDiciembre(String notaDiciembre)
/* 387:    */   {
/* 388:354 */     this.notaDiciembre = notaDiciembre;
/* 389:    */   }
/* 390:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteAnual
 * JD-Core Version:    0.7.0.1
 */