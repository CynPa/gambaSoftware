/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import org.hibernate.annotations.ColumnDefault;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="filtro_producto")
/*  20:    */ public class FiltroProducto
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="filtro_producto", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="filtro_producto")
/*  28:    */   @Column(name="id_filtro_producto", unique=true, nullable=false)
/*  29:    */   private int idFiltroProducto;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   @NotNull
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="numero_registros", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   @ColumnDefault("30")
/*  39:    */   @Min(10L)
/*  40: 55 */   private int numeroRegistros = 30;
/*  41:    */   @Column(name="indicador_codigo", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private boolean indicadorCodigo;
/*  44:    */   @Column(name="indicador_codigo_alterno", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private boolean indicadorCodigoAlterno;
/*  47:    */   @Column(name="indicador_codigo_barras", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @ColumnDefault("'1'")
/*  50:    */   private boolean indicadorCodigoBarras;
/*  51:    */   @Column(name="indicador_nombre", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private boolean indicadorNombre;
/*  54:    */   @Column(name="indicador_nombre_comercial", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private boolean indicadorNombreComercial;
/*  57:    */   @Column(name="indicador_descripcion", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private boolean indicadorDescripcion;
/*  60:    */   @Column(name="indicador_categoria_producto", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   private boolean indicadorCategoriaProducto;
/*  63:    */   @Column(name="indicador_subcategoria_producto", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private boolean indicadorSubcategoriaProducto;
/*  66:    */   @Column(name="indicador_unidad", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private boolean indicadorUnidad;
/*  69:    */   @Column(name="indicador_unidad_almacenamiento", nullable=false)
/*  70:    */   @NotNull
/*  71:    */   private boolean indicadorUnidadAlmacenamiento;
/*  72:    */   @Column(name="indicador_unidad_venta", nullable=false)
/*  73:    */   @NotNull
/*  74:    */   private boolean indicadorUnidadVenta;
/*  75:    */   @Column(name="indicador_unidad_compra", nullable=true)
/*  76:    */   @NotNull
/*  77:    */   private boolean indicadorUnidadCompra;
/*  78:    */   @Column(name="indicador_presentacion_producto", nullable=true)
/*  79:    */   @ColumnDefault("'0'")
/*  80:    */   @NotNull
/*  81:    */   private boolean indicadorPresentacionProducto;
/*  82:    */   @Column(name="indicador_atributo1", nullable=false)
/*  83:    */   @NotNull
/*  84:    */   private boolean indicadorAtributo1;
/*  85:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  86:    */   @JoinColumn(name="id_atributo1", nullable=true)
/*  87:    */   private Atributo atributo1;
/*  88:    */   @Column(name="indicador_atributo2", nullable=false)
/*  89:    */   @NotNull
/*  90:    */   private boolean indicadorAtributo2;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_atributo2", nullable=true)
/*  93:    */   private Atributo atributo2;
/*  94:    */   @Column(name="indicador_atributo3", nullable=false)
/*  95:    */   @NotNull
/*  96:    */   private boolean indicadorAtributo3;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_atributo3", nullable=true)
/*  99:    */   private Atributo atributo3;
/* 100:    */   @Column(name="indicador_atributo4", nullable=false)
/* 101:    */   @NotNull
/* 102:    */   private boolean indicadorAtributo4;
/* 103:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 104:    */   @JoinColumn(name="id_atributo4", nullable=true)
/* 105:    */   private Atributo atributo4;
/* 106:    */   @Column(name="indicador_atributo5", nullable=false)
/* 107:    */   @NotNull
/* 108:    */   private boolean indicadorAtributo5;
/* 109:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 110:    */   @JoinColumn(name="id_atributo5", nullable=true)
/* 111:    */   private Atributo atributo5;
/* 112:    */   @Column(name="indicador_atributo6", nullable=false)
/* 113:    */   @NotNull
/* 114:    */   private boolean indicadorAtributo6;
/* 115:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 116:    */   @JoinColumn(name="id_atributo6", nullable=true)
/* 117:    */   private Atributo atributo6;
/* 118:    */   @Column(name="indicador_atributo7", nullable=false)
/* 119:    */   @NotNull
/* 120:    */   private boolean indicadorAtributo7;
/* 121:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 122:    */   @JoinColumn(name="id_atributo7", nullable=true)
/* 123:    */   private Atributo atributo7;
/* 124:    */   @Column(name="indicador_atributo8", nullable=false)
/* 125:    */   @NotNull
/* 126:    */   private boolean indicadorAtributo8;
/* 127:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 128:    */   @JoinColumn(name="id_atributo8", nullable=true)
/* 129:    */   private Atributo atributo8;
/* 130:    */   @Column(name="indicador_atributo9", nullable=false)
/* 131:    */   @NotNull
/* 132:    */   private boolean indicadorAtributo9;
/* 133:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 134:    */   @JoinColumn(name="id_atributo9", nullable=true)
/* 135:    */   private Atributo atributo9;
/* 136:    */   @Column(name="indicador_atributo10", nullable=false)
/* 137:    */   @NotNull
/* 138:    */   private boolean indicadorAtributo10;
/* 139:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 140:    */   @JoinColumn(name="id_atributo10", nullable=true)
/* 141:    */   private Atributo atributo10;
/* 142:    */   
/* 143:    */   public int getId()
/* 144:    */   {
/* 145:197 */     return this.idFiltroProducto;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getIdFiltroProducto()
/* 149:    */   {
/* 150:206 */     return this.idFiltroProducto;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdFiltroProducto(int idFiltroProducto)
/* 154:    */   {
/* 155:216 */     this.idFiltroProducto = idFiltroProducto;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getIdOrganizacion()
/* 159:    */   {
/* 160:225 */     return this.idOrganizacion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdOrganizacion(int idOrganizacion)
/* 164:    */   {
/* 165:235 */     this.idOrganizacion = idOrganizacion;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getIdSucursal()
/* 169:    */   {
/* 170:244 */     return this.idSucursal;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdSucursal(int idSucursal)
/* 174:    */   {
/* 175:254 */     this.idSucursal = idSucursal;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isIndicadorCodigo()
/* 179:    */   {
/* 180:263 */     return this.indicadorCodigo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIndicadorCodigo(boolean indicadorCodigo)
/* 184:    */   {
/* 185:273 */     this.indicadorCodigo = indicadorCodigo;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean isIndicadorCodigoAlterno()
/* 189:    */   {
/* 190:282 */     return this.indicadorCodigoAlterno;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setIndicadorCodigoAlterno(boolean indicadorCodigoAlterno)
/* 194:    */   {
/* 195:292 */     this.indicadorCodigoAlterno = indicadorCodigoAlterno;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean isIndicadorNombre()
/* 199:    */   {
/* 200:301 */     return this.indicadorNombre;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setIndicadorNombre(boolean indicadorNombre)
/* 204:    */   {
/* 205:311 */     this.indicadorNombre = indicadorNombre;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isIndicadorNombreComercial()
/* 209:    */   {
/* 210:320 */     return this.indicadorNombreComercial;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorNombreComercial(boolean indicadorNombreComercial)
/* 214:    */   {
/* 215:330 */     this.indicadorNombreComercial = indicadorNombreComercial;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isIndicadorDescripcion()
/* 219:    */   {
/* 220:339 */     return this.indicadorDescripcion;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIndicadorDescripcion(boolean indicadorDescripcion)
/* 224:    */   {
/* 225:349 */     this.indicadorDescripcion = indicadorDescripcion;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isIndicadorCategoriaProducto()
/* 229:    */   {
/* 230:358 */     return this.indicadorCategoriaProducto;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setIndicadorCategoriaProducto(boolean indicadorCategoriaProducto)
/* 234:    */   {
/* 235:368 */     this.indicadorCategoriaProducto = indicadorCategoriaProducto;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean isIndicadorSubcategoriaProducto()
/* 239:    */   {
/* 240:377 */     return this.indicadorSubcategoriaProducto;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setIndicadorSubcategoriaProducto(boolean indicadorSubcategoriaProducto)
/* 244:    */   {
/* 245:387 */     this.indicadorSubcategoriaProducto = indicadorSubcategoriaProducto;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean isIndicadorUnidad()
/* 249:    */   {
/* 250:396 */     return this.indicadorUnidad;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setIndicadorUnidad(boolean indicadorUnidad)
/* 254:    */   {
/* 255:406 */     this.indicadorUnidad = indicadorUnidad;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean isIndicadorUnidadAlmacenamiento()
/* 259:    */   {
/* 260:415 */     return this.indicadorUnidadAlmacenamiento;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setIndicadorUnidadAlmacenamiento(boolean indicadorUnidadAlmacenamiento)
/* 264:    */   {
/* 265:425 */     this.indicadorUnidadAlmacenamiento = indicadorUnidadAlmacenamiento;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean isIndicadorUnidadVenta()
/* 269:    */   {
/* 270:434 */     return this.indicadorUnidadVenta;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setIndicadorUnidadVenta(boolean indicadorUnidadVenta)
/* 274:    */   {
/* 275:444 */     this.indicadorUnidadVenta = indicadorUnidadVenta;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean isIndicadorUnidadCompra()
/* 279:    */   {
/* 280:453 */     return this.indicadorUnidadCompra;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setIndicadorUnidadCompra(boolean indicadorUnidadCompra)
/* 284:    */   {
/* 285:463 */     this.indicadorUnidadCompra = indicadorUnidadCompra;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public boolean isIndicadorAtributo1()
/* 289:    */   {
/* 290:472 */     return this.indicadorAtributo1;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setIndicadorAtributo1(boolean indicadorAtributo1)
/* 294:    */   {
/* 295:482 */     this.indicadorAtributo1 = indicadorAtributo1;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Atributo getAtributo1()
/* 299:    */   {
/* 300:491 */     return this.atributo1;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setAtributo1(Atributo atributo1)
/* 304:    */   {
/* 305:501 */     this.atributo1 = atributo1;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public boolean isIndicadorAtributo2()
/* 309:    */   {
/* 310:510 */     return this.indicadorAtributo2;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setIndicadorAtributo2(boolean indicadorAtributo2)
/* 314:    */   {
/* 315:520 */     this.indicadorAtributo2 = indicadorAtributo2;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Atributo getAtributo2()
/* 319:    */   {
/* 320:529 */     return this.atributo2;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setAtributo2(Atributo atributo2)
/* 324:    */   {
/* 325:539 */     this.atributo2 = atributo2;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public boolean isIndicadorAtributo3()
/* 329:    */   {
/* 330:548 */     return this.indicadorAtributo3;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setIndicadorAtributo3(boolean indicadorAtributo3)
/* 334:    */   {
/* 335:558 */     this.indicadorAtributo3 = indicadorAtributo3;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public Atributo getAtributo3()
/* 339:    */   {
/* 340:567 */     return this.atributo3;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setAtributo3(Atributo atributo3)
/* 344:    */   {
/* 345:577 */     this.atributo3 = atributo3;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public boolean isIndicadorAtributo4()
/* 349:    */   {
/* 350:586 */     return this.indicadorAtributo4;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setIndicadorAtributo4(boolean indicadorAtributo4)
/* 354:    */   {
/* 355:596 */     this.indicadorAtributo4 = indicadorAtributo4;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public Atributo getAtributo4()
/* 359:    */   {
/* 360:605 */     return this.atributo4;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setAtributo4(Atributo atributo4)
/* 364:    */   {
/* 365:615 */     this.atributo4 = atributo4;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public boolean isIndicadorAtributo5()
/* 369:    */   {
/* 370:624 */     return this.indicadorAtributo5;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setIndicadorAtributo5(boolean indicadorAtributo5)
/* 374:    */   {
/* 375:634 */     this.indicadorAtributo5 = indicadorAtributo5;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public Atributo getAtributo5()
/* 379:    */   {
/* 380:643 */     return this.atributo5;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setAtributo5(Atributo atributo5)
/* 384:    */   {
/* 385:653 */     this.atributo5 = atributo5;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public boolean isIndicadorAtributo6()
/* 389:    */   {
/* 390:662 */     return this.indicadorAtributo6;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setIndicadorAtributo6(boolean indicadorAtributo6)
/* 394:    */   {
/* 395:672 */     this.indicadorAtributo6 = indicadorAtributo6;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public Atributo getAtributo6()
/* 399:    */   {
/* 400:681 */     return this.atributo6;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setAtributo6(Atributo atributo6)
/* 404:    */   {
/* 405:691 */     this.atributo6 = atributo6;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public boolean isIndicadorAtributo7()
/* 409:    */   {
/* 410:700 */     return this.indicadorAtributo7;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setIndicadorAtributo7(boolean indicadorAtributo7)
/* 414:    */   {
/* 415:710 */     this.indicadorAtributo7 = indicadorAtributo7;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public Atributo getAtributo7()
/* 419:    */   {
/* 420:719 */     return this.atributo7;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setAtributo7(Atributo atributo7)
/* 424:    */   {
/* 425:729 */     this.atributo7 = atributo7;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean isIndicadorAtributo8()
/* 429:    */   {
/* 430:738 */     return this.indicadorAtributo8;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setIndicadorAtributo8(boolean indicadorAtributo8)
/* 434:    */   {
/* 435:748 */     this.indicadorAtributo8 = indicadorAtributo8;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public Atributo getAtributo8()
/* 439:    */   {
/* 440:757 */     return this.atributo8;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setAtributo8(Atributo atributo8)
/* 444:    */   {
/* 445:767 */     this.atributo8 = atributo8;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean isIndicadorAtributo9()
/* 449:    */   {
/* 450:776 */     return this.indicadorAtributo9;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setIndicadorAtributo9(boolean indicadorAtributo9)
/* 454:    */   {
/* 455:786 */     this.indicadorAtributo9 = indicadorAtributo9;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public Atributo getAtributo9()
/* 459:    */   {
/* 460:795 */     return this.atributo9;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void setAtributo9(Atributo atributo9)
/* 464:    */   {
/* 465:805 */     this.atributo9 = atributo9;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public boolean isIndicadorAtributo10()
/* 469:    */   {
/* 470:814 */     return this.indicadorAtributo10;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void setIndicadorAtributo10(boolean indicadorAtributo10)
/* 474:    */   {
/* 475:824 */     this.indicadorAtributo10 = indicadorAtributo10;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public Atributo getAtributo10()
/* 479:    */   {
/* 480:833 */     return this.atributo10;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void setAtributo10(Atributo atributo10)
/* 484:    */   {
/* 485:843 */     this.atributo10 = atributo10;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public int getNumeroRegistros()
/* 489:    */   {
/* 490:847 */     return this.numeroRegistros;
/* 491:    */   }
/* 492:    */   
/* 493:    */   public void setNumeroRegistros(int numeroRegistros)
/* 494:    */   {
/* 495:851 */     this.numeroRegistros = numeroRegistros;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public boolean isIndicadorPresentacionProducto()
/* 499:    */   {
/* 500:855 */     return this.indicadorPresentacionProducto;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setIndicadorPresentacionProducto(boolean indicadorPresentacionProducto)
/* 504:    */   {
/* 505:859 */     this.indicadorPresentacionProducto = indicadorPresentacionProducto;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public boolean isIndicadorCodigoBarras()
/* 509:    */   {
/* 510:863 */     return this.indicadorCodigoBarras;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public void setIndicadorCodigoBarras(boolean indicadorCodigoBarras)
/* 514:    */   {
/* 515:867 */     this.indicadorCodigoBarras = indicadorCodigoBarras;
/* 516:    */   }
/* 517:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FiltroProducto
 * JD-Core Version:    0.7.0.1
 */