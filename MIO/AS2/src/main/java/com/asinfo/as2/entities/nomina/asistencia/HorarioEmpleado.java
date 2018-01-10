/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ import org.hibernate.annotations.ColumnDefault;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="horario_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class HorarioEmpleado
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="horario_empleado", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="horario_empleado")
/*  29:    */   @Column(name="id_horario_empleado", unique=true, nullable=false)
/*  30:    */   private int idHorarioEmpleado;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=10)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=10)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="nombre", nullable=false, length=50)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", nullable=true, length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Column(name="indicador_rotativo", nullable=false)
/*  51:    */   private boolean indicadorRotativo;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_turno_1", nullable=true)
/*  54:    */   private Turno turno1;
/*  55:    */   @Column(name="indicador_dia_descanso1", nullable=true)
/*  56: 84 */   private Boolean indicadorDiaDescanso1 = Boolean.valueOf(false);
/*  57:    */   @Column(name="indicador_horario_complementario1", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   @ColumnDefault("'0'")
/*  60: 89 */   private Boolean indicadorHorarioComplementario1 = Boolean.valueOf(false);
/*  61:    */   @Column(name="indicador_dia_opcional1", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   @ColumnDefault("'0'")
/*  64: 94 */   private Boolean indicadorDiaOpcional1 = Boolean.valueOf(false);
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_turno_2", nullable=true)
/*  67:    */   private Turno turno2;
/*  68:    */   @Column(name="indicador_dia_descanso2", nullable=true)
/*  69:102 */   private Boolean indicadorDiaDescanso2 = Boolean.valueOf(false);
/*  70:    */   @Column(name="indicador_horario_complementario2", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   @ColumnDefault("'0'")
/*  73:107 */   private Boolean indicadorHorarioComplementario2 = Boolean.valueOf(false);
/*  74:    */   @Column(name="indicador_dia_opcional2", nullable=false)
/*  75:    */   @NotNull
/*  76:    */   @ColumnDefault("'0'")
/*  77:112 */   private Boolean indicadorDiaOpcional2 = Boolean.valueOf(false);
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_turno_3", nullable=true)
/*  80:    */   private Turno turno3;
/*  81:    */   @Column(name="indicador_dia_descanso3", nullable=true)
/*  82:120 */   private Boolean indicadorDiaDescanso3 = Boolean.valueOf(false);
/*  83:    */   @Column(name="indicador_horario_complementario3", nullable=false)
/*  84:    */   @NotNull
/*  85:    */   @ColumnDefault("'0'")
/*  86:125 */   private Boolean indicadorHorarioComplementario3 = Boolean.valueOf(false);
/*  87:    */   @Column(name="indicador_dia_opcional3", nullable=false)
/*  88:    */   @NotNull
/*  89:    */   @ColumnDefault("'0'")
/*  90:130 */   private Boolean indicadorDiaOpcional3 = Boolean.valueOf(false);
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_turno_4", nullable=true)
/*  93:    */   private Turno turno4;
/*  94:    */   @Column(name="indicador_dia_descanso4", nullable=true)
/*  95:138 */   private Boolean indicadorDiaDescanso4 = Boolean.valueOf(false);
/*  96:    */   @Column(name="indicador_horario_complementario4", nullable=false)
/*  97:    */   @NotNull
/*  98:    */   @ColumnDefault("'0'")
/*  99:143 */   private Boolean indicadorHorarioComplementario4 = Boolean.valueOf(false);
/* 100:    */   @Column(name="indicador_dia_opcional4", nullable=false)
/* 101:    */   @NotNull
/* 102:    */   @ColumnDefault("'0'")
/* 103:148 */   private Boolean indicadorDiaOpcional4 = Boolean.valueOf(false);
/* 104:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 105:    */   @JoinColumn(name="id_turno_5", nullable=true)
/* 106:    */   private Turno turno5;
/* 107:    */   @Column(name="indicador_dia_descanso5", nullable=true)
/* 108:156 */   private Boolean indicadorDiaDescanso5 = Boolean.valueOf(false);
/* 109:    */   @Column(name="indicador_horario_complementario5", nullable=false)
/* 110:    */   @NotNull
/* 111:    */   @ColumnDefault("'0'")
/* 112:161 */   private Boolean indicadorHorarioComplementario5 = Boolean.valueOf(false);
/* 113:    */   @Column(name="indicador_dia_opcional5", nullable=false)
/* 114:    */   @NotNull
/* 115:    */   @ColumnDefault("'0'")
/* 116:166 */   private Boolean indicadorDiaOpcional5 = Boolean.valueOf(false);
/* 117:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 118:    */   @JoinColumn(name="id_turno_6", nullable=true)
/* 119:    */   private Turno turno6;
/* 120:    */   @Column(name="indicador_dia_descanso6", nullable=true)
/* 121:174 */   private Boolean indicadorDiaDescanso6 = Boolean.valueOf(false);
/* 122:    */   @Column(name="indicador_horario_complementario6", nullable=false)
/* 123:    */   @NotNull
/* 124:    */   @ColumnDefault("'0'")
/* 125:179 */   private Boolean indicadorHorarioComplementario6 = Boolean.valueOf(false);
/* 126:    */   @Column(name="indicador_dia_opcional6", nullable=false)
/* 127:    */   @NotNull
/* 128:    */   @ColumnDefault("'0'")
/* 129:184 */   private Boolean indicadorDiaOpcional6 = Boolean.valueOf(false);
/* 130:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 131:    */   @JoinColumn(name="id_turno_0", nullable=true)
/* 132:    */   private Turno turno0;
/* 133:    */   @Column(name="indicador_dia_descanso0", nullable=true)
/* 134:192 */   private Boolean indicadorDiaDescanso0 = Boolean.valueOf(false);
/* 135:    */   @Column(name="indicador_horario_complementario0", nullable=false)
/* 136:    */   @NotNull
/* 137:    */   @ColumnDefault("'0'")
/* 138:197 */   private Boolean indicadorHorarioComplementario0 = Boolean.valueOf(false);
/* 139:    */   @Column(name="indicador_dia_opcional0", nullable=false)
/* 140:    */   @NotNull
/* 141:    */   @ColumnDefault("'0'")
/* 142:202 */   private Boolean indicadorDiaOpcional0 = Boolean.valueOf(false);
/* 143:    */   
/* 144:    */   public int getId()
/* 145:    */   {
/* 146:209 */     return this.idHorarioEmpleado;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getIdHorarioEmpleado()
/* 150:    */   {
/* 151:218 */     return this.idHorarioEmpleado;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setIdHorarioEmpleado(int idHorarioEmpleado)
/* 155:    */   {
/* 156:228 */     this.idHorarioEmpleado = idHorarioEmpleado;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getIdOrganizacion()
/* 160:    */   {
/* 161:237 */     return this.idOrganizacion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIdOrganizacion(int idOrganizacion)
/* 165:    */   {
/* 166:247 */     this.idOrganizacion = idOrganizacion;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public int getIdSucursal()
/* 170:    */   {
/* 171:256 */     return this.idSucursal;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIdSucursal(int idSucursal)
/* 175:    */   {
/* 176:266 */     this.idSucursal = idSucursal;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getCodigo()
/* 180:    */   {
/* 181:275 */     return this.codigo;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setCodigo(String codigo)
/* 185:    */   {
/* 186:285 */     this.codigo = codigo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getNombre()
/* 190:    */   {
/* 191:294 */     return this.nombre;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setNombre(String nombre)
/* 195:    */   {
/* 196:304 */     this.nombre = nombre;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getDescripcion()
/* 200:    */   {
/* 201:313 */     return this.descripcion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setDescripcion(String descripcion)
/* 205:    */   {
/* 206:323 */     this.descripcion = descripcion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isIndicadorRotativo()
/* 210:    */   {
/* 211:327 */     return this.indicadorRotativo;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setIndicadorRotativo(boolean indicadorRotativo)
/* 215:    */   {
/* 216:331 */     this.indicadorRotativo = indicadorRotativo;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Turno getTurno1()
/* 220:    */   {
/* 221:335 */     return this.turno1;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setTurno1(Turno turno1)
/* 225:    */   {
/* 226:339 */     this.turno1 = turno1;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Turno getTurno2()
/* 230:    */   {
/* 231:343 */     return this.turno2;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setTurno2(Turno turno2)
/* 235:    */   {
/* 236:347 */     this.turno2 = turno2;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Turno getTurno3()
/* 240:    */   {
/* 241:351 */     return this.turno3;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setTurno3(Turno turno3)
/* 245:    */   {
/* 246:355 */     this.turno3 = turno3;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Turno getTurno4()
/* 250:    */   {
/* 251:359 */     return this.turno4;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setTurno4(Turno turno4)
/* 255:    */   {
/* 256:363 */     this.turno4 = turno4;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Turno getTurno5()
/* 260:    */   {
/* 261:367 */     return this.turno5;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setTurno5(Turno turno5)
/* 265:    */   {
/* 266:371 */     this.turno5 = turno5;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public Turno getTurno6()
/* 270:    */   {
/* 271:375 */     return this.turno6;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setTurno6(Turno turno6)
/* 275:    */   {
/* 276:379 */     this.turno6 = turno6;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Turno getTurno0()
/* 280:    */   {
/* 281:383 */     return this.turno0;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setTurno0(Turno turno0)
/* 285:    */   {
/* 286:387 */     this.turno0 = turno0;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public boolean isActivo()
/* 290:    */   {
/* 291:391 */     return this.activo;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setActivo(boolean activo)
/* 295:    */   {
/* 296:395 */     this.activo = activo;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean isPredeterminado()
/* 300:    */   {
/* 301:399 */     return this.predeterminado;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setPredeterminado(boolean predeterminado)
/* 305:    */   {
/* 306:403 */     this.predeterminado = predeterminado;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String toString()
/* 310:    */   {
/* 311:408 */     return this.nombre;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public Boolean getIndicadorDiaDescanso1()
/* 315:    */   {
/* 316:412 */     if (this.indicadorDiaDescanso1 == null) {
/* 317:413 */       this.indicadorDiaDescanso1 = Boolean.valueOf(false);
/* 318:    */     }
/* 319:415 */     return this.indicadorDiaDescanso1;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setIndicadorDiaDescanso1(Boolean indicadorDiaDescanso1)
/* 323:    */   {
/* 324:419 */     this.indicadorDiaDescanso1 = indicadorDiaDescanso1;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Boolean getIndicadorDiaDescanso2()
/* 328:    */   {
/* 329:423 */     if (this.indicadorDiaDescanso2 == null) {
/* 330:424 */       this.indicadorDiaDescanso2 = Boolean.valueOf(false);
/* 331:    */     }
/* 332:426 */     return this.indicadorDiaDescanso2;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setIndicadorDiaDescanso2(Boolean indicadorDiaDescanso2)
/* 336:    */   {
/* 337:430 */     this.indicadorDiaDescanso2 = indicadorDiaDescanso2;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Boolean getIndicadorDiaDescanso3()
/* 341:    */   {
/* 342:434 */     if (this.indicadorDiaDescanso3 == null) {
/* 343:435 */       this.indicadorDiaDescanso3 = Boolean.valueOf(false);
/* 344:    */     }
/* 345:437 */     return this.indicadorDiaDescanso3;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setIndicadorDiaDescanso3(Boolean indicadorDiaDescanso3)
/* 349:    */   {
/* 350:441 */     this.indicadorDiaDescanso3 = indicadorDiaDescanso3;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public Boolean getIndicadorDiaDescanso4()
/* 354:    */   {
/* 355:445 */     if (this.indicadorDiaDescanso4 == null) {
/* 356:446 */       this.indicadorDiaDescanso4 = Boolean.valueOf(false);
/* 357:    */     }
/* 358:448 */     return this.indicadorDiaDescanso4;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setIndicadorDiaDescanso4(Boolean indicadorDiaDescanso4)
/* 362:    */   {
/* 363:452 */     this.indicadorDiaDescanso4 = indicadorDiaDescanso4;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public Boolean getIndicadorDiaDescanso5()
/* 367:    */   {
/* 368:456 */     if (this.indicadorDiaDescanso5 == null) {
/* 369:457 */       this.indicadorDiaDescanso5 = Boolean.valueOf(false);
/* 370:    */     }
/* 371:459 */     return this.indicadorDiaDescanso5;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setIndicadorDiaDescanso5(Boolean indicadorDiaDescanso5)
/* 375:    */   {
/* 376:463 */     this.indicadorDiaDescanso5 = indicadorDiaDescanso5;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public Boolean getIndicadorDiaDescanso6()
/* 380:    */   {
/* 381:467 */     if (this.indicadorDiaDescanso6 == null) {
/* 382:468 */       this.indicadorDiaDescanso6 = Boolean.valueOf(false);
/* 383:    */     }
/* 384:470 */     return this.indicadorDiaDescanso6;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setIndicadorDiaDescanso6(Boolean indicadorDiaDescanso6)
/* 388:    */   {
/* 389:474 */     this.indicadorDiaDescanso6 = indicadorDiaDescanso6;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public Boolean getIndicadorDiaDescanso0()
/* 393:    */   {
/* 394:478 */     if (this.indicadorDiaDescanso0 == null) {
/* 395:479 */       this.indicadorDiaDescanso0 = Boolean.valueOf(false);
/* 396:    */     }
/* 397:481 */     return this.indicadorDiaDescanso0;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setIndicadorDiaDescanso0(Boolean indicadorDiaDescanso0)
/* 401:    */   {
/* 402:485 */     this.indicadorDiaDescanso0 = indicadorDiaDescanso0;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public Boolean getIndicadorHorarioComplementario1()
/* 406:    */   {
/* 407:489 */     return this.indicadorHorarioComplementario1;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setIndicadorHorarioComplementario1(Boolean indicadorHorarioComplementario1)
/* 411:    */   {
/* 412:493 */     this.indicadorHorarioComplementario1 = indicadorHorarioComplementario1;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public Boolean getIndicadorHorarioComplementario2()
/* 416:    */   {
/* 417:497 */     return this.indicadorHorarioComplementario2;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setIndicadorHorarioComplementario2(Boolean indicadorHorarioComplementario2)
/* 421:    */   {
/* 422:501 */     this.indicadorHorarioComplementario2 = indicadorHorarioComplementario2;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public Boolean getIndicadorHorarioComplementario3()
/* 426:    */   {
/* 427:505 */     return this.indicadorHorarioComplementario3;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setIndicadorHorarioComplementario3(Boolean indicadorHorarioComplementario3)
/* 431:    */   {
/* 432:509 */     this.indicadorHorarioComplementario3 = indicadorHorarioComplementario3;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public Boolean getIndicadorHorarioComplementario4()
/* 436:    */   {
/* 437:513 */     return this.indicadorHorarioComplementario4;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setIndicadorHorarioComplementario4(Boolean indicadorHorarioComplementario4)
/* 441:    */   {
/* 442:517 */     this.indicadorHorarioComplementario4 = indicadorHorarioComplementario4;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public Boolean getIndicadorHorarioComplementario5()
/* 446:    */   {
/* 447:521 */     return this.indicadorHorarioComplementario5;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setIndicadorHorarioComplementario5(Boolean indicadorHorarioComplementario5)
/* 451:    */   {
/* 452:525 */     this.indicadorHorarioComplementario5 = indicadorHorarioComplementario5;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public Boolean getIndicadorHorarioComplementario6()
/* 456:    */   {
/* 457:529 */     return this.indicadorHorarioComplementario6;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setIndicadorHorarioComplementario6(Boolean indicadorHorarioComplementario6)
/* 461:    */   {
/* 462:533 */     this.indicadorHorarioComplementario6 = indicadorHorarioComplementario6;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Boolean getIndicadorHorarioComplementario0()
/* 466:    */   {
/* 467:537 */     return this.indicadorHorarioComplementario0;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setIndicadorHorarioComplementario0(Boolean indicadorHorarioComplementario0)
/* 471:    */   {
/* 472:541 */     this.indicadorHorarioComplementario0 = indicadorHorarioComplementario0;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public Boolean getIndicadorDiaOpcional1()
/* 476:    */   {
/* 477:545 */     return this.indicadorDiaOpcional1;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setIndicadorDiaOpcional1(Boolean indicadorDiaOpcional1)
/* 481:    */   {
/* 482:549 */     this.indicadorDiaOpcional1 = indicadorDiaOpcional1;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public Boolean getIndicadorDiaOpcional2()
/* 486:    */   {
/* 487:553 */     return this.indicadorDiaOpcional2;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public void setIndicadorDiaOpcional2(Boolean indicadorDiaOpcional2)
/* 491:    */   {
/* 492:557 */     this.indicadorDiaOpcional2 = indicadorDiaOpcional2;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public Boolean getIndicadorDiaOpcional3()
/* 496:    */   {
/* 497:561 */     return this.indicadorDiaOpcional3;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void setIndicadorDiaOpcional3(Boolean indicadorDiaOpcional3)
/* 501:    */   {
/* 502:565 */     this.indicadorDiaOpcional3 = indicadorDiaOpcional3;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public Boolean getIndicadorDiaOpcional4()
/* 506:    */   {
/* 507:569 */     return this.indicadorDiaOpcional4;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setIndicadorDiaOpcional4(Boolean indicadorDiaOpcional4)
/* 511:    */   {
/* 512:573 */     this.indicadorDiaOpcional4 = indicadorDiaOpcional4;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public Boolean getIndicadorDiaOpcional5()
/* 516:    */   {
/* 517:577 */     return this.indicadorDiaOpcional5;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public void setIndicadorDiaOpcional5(Boolean indicadorDiaOpcional5)
/* 521:    */   {
/* 522:581 */     this.indicadorDiaOpcional5 = indicadorDiaOpcional5;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public Boolean getIndicadorDiaOpcional6()
/* 526:    */   {
/* 527:585 */     return this.indicadorDiaOpcional6;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void setIndicadorDiaOpcional6(Boolean indicadorDiaOpcional6)
/* 531:    */   {
/* 532:589 */     this.indicadorDiaOpcional6 = indicadorDiaOpcional6;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public Boolean getIndicadorDiaOpcional0()
/* 536:    */   {
/* 537:593 */     return this.indicadorDiaOpcional0;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void setIndicadorDiaOpcional0(Boolean indicadorDiaOpcional0)
/* 541:    */   {
/* 542:597 */     this.indicadorDiaOpcional0 = indicadorDiaOpcional0;
/* 543:    */   }
/* 544:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado
 * JD-Core Version:    0.7.0.1
 */