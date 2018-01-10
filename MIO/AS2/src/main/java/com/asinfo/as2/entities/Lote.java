/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.Date;
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
/*  14:    */ import javax.persistence.Temporal;
/*  15:    */ import javax.persistence.TemporalType;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="lote", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empresa", "codigo", "id_producto"})})
/*  22:    */ public class Lote
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -1189799110709152351L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="lote", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="lote")
/*  29:    */   @Column(name="id_lote")
/*  30:    */   private int idLote;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Temporal(TemporalType.DATE)
/*  36:    */   @Column(name="fecha_fabricacion", nullable=true)
/*  37:    */   private Date fechaFabricacion;
/*  38:    */   @Temporal(TemporalType.DATE)
/*  39:    */   @Column(name="fecha_caducidad", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Date fechaCaducidad;
/*  42:    */   @Column(name="codigo", nullable=false, length=50)
/*  43:    */   @NotNull
/*  44:    */   @Size(min=1, max=50)
/*  45:    */   private String codigo;
/*  46:    */   @Column(name="activo", nullable=true)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="indicador_movimiento_interno", nullable=false)
/*  49:    */   private boolean indicadorMovimientoInterno;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_producto", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Producto producto;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  56:    */   private Empresa empresa;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_atributo_1", nullable=true)
/*  59:    */   private Atributo atributo1;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_valor_atributo_1", nullable=true)
/*  62:    */   private ValorAtributo valorAtributo1;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_atributo_2", nullable=true)
/*  65:    */   private Atributo atributo2;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_valor_atributo_2", nullable=true)
/*  68:    */   private ValorAtributo valorAtributo2;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_atributo_3", nullable=true)
/*  71:    */   private Atributo atributo3;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_valor_atributo_3", nullable=true)
/*  74:    */   private ValorAtributo valorAtributo3;
/*  75:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  76:    */   @JoinColumn(name="id_atributo_4", nullable=true)
/*  77:    */   private Atributo atributo4;
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_valor_atributo_4", nullable=true)
/*  80:    */   private ValorAtributo valorAtributo4;
/*  81:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  82:    */   @JoinColumn(name="id_atributo_5", nullable=true)
/*  83:    */   private Atributo atributo5;
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_valor_atributo_5", nullable=true)
/*  86:    */   private ValorAtributo valorAtributo5;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_atributo_6", nullable=true)
/*  89:    */   private Atributo atributo6;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_valor_atributo_6", nullable=true)
/*  92:    */   private ValorAtributo valorAtributo6;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_atributo_7", nullable=true)
/*  95:    */   private Atributo atributo7;
/*  96:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_valor_atributo_7", nullable=true)
/*  98:    */   private ValorAtributo valorAtributo7;
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_atributo_8", nullable=true)
/* 101:    */   private Atributo atributo8;
/* 102:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 103:    */   @JoinColumn(name="id_valor_atributo_8", nullable=true)
/* 104:    */   private ValorAtributo valorAtributo8;
/* 105:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 106:    */   @JoinColumn(name="id_atributo_9", nullable=true)
/* 107:    */   private Atributo atributo9;
/* 108:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 109:    */   @JoinColumn(name="id_valor_atributo_9", nullable=true)
/* 110:    */   private ValorAtributo valorAtributo9;
/* 111:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 112:    */   @JoinColumn(name="id_atributo_10", nullable=true)
/* 113:    */   private Atributo atributo10;
/* 114:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 115:    */   @JoinColumn(name="id_valor_atributo_10", nullable=true)
/* 116:    */   private ValorAtributo valorAtributo10;
/* 117:    */   @Transient
/* 118:    */   private boolean disableAtributoOrdenFabricacion;
/* 119:    */   @Transient
/* 120:    */   private boolean disableMovimientoInterno;
/* 121:    */   
/* 122:    */   public Lote() {}
/* 123:    */   
/* 124:    */   public Lote(int idLote)
/* 125:    */   {
/* 126:195 */     this.idLote = idLote;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Lote(int idLote, String codigo)
/* 130:    */   {
/* 131:205 */     this.idLote = idLote;
/* 132:206 */     this.codigo = codigo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getId()
/* 136:    */   {
/* 137:220 */     return this.idLote;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getIdLote()
/* 141:    */   {
/* 142:229 */     return this.idLote;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setIdLote(int idLote)
/* 146:    */   {
/* 147:239 */     this.idLote = idLote;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getIdOrganizacion()
/* 151:    */   {
/* 152:248 */     return this.idOrganizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setIdOrganizacion(int idOrganizacion)
/* 156:    */   {
/* 157:258 */     this.idOrganizacion = idOrganizacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getIdSucursal()
/* 161:    */   {
/* 162:267 */     return this.idSucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setIdSucursal(int idSucursal)
/* 166:    */   {
/* 167:277 */     this.idSucursal = idSucursal;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Date getFechaFabricacion()
/* 171:    */   {
/* 172:286 */     return this.fechaFabricacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFechaFabricacion(Date fechaFabricacion)
/* 176:    */   {
/* 177:296 */     this.fechaFabricacion = fechaFabricacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Date getFechaCaducidad()
/* 181:    */   {
/* 182:305 */     return this.fechaCaducidad;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setFechaCaducidad(Date fechaCaducidad)
/* 186:    */   {
/* 187:315 */     this.fechaCaducidad = fechaCaducidad;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getCodigo()
/* 191:    */   {
/* 192:324 */     return this.codigo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setCodigo(String codigo)
/* 196:    */   {
/* 197:334 */     this.codigo = codigo;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isIndicadorMovimientoInterno()
/* 201:    */   {
/* 202:343 */     return this.indicadorMovimientoInterno;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setIndicadorMovimientoInterno(boolean indicadorMovimientoInterno)
/* 206:    */   {
/* 207:353 */     this.indicadorMovimientoInterno = indicadorMovimientoInterno;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Producto getProducto()
/* 211:    */   {
/* 212:362 */     return this.producto;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setProducto(Producto producto)
/* 216:    */   {
/* 217:372 */     this.producto = producto;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Empresa getEmpresa()
/* 221:    */   {
/* 222:381 */     return this.empresa;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setEmpresa(Empresa empresa)
/* 226:    */   {
/* 227:391 */     this.empresa = empresa;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isActivo()
/* 231:    */   {
/* 232:395 */     return this.activo;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setActivo(boolean activo)
/* 236:    */   {
/* 237:399 */     this.activo = activo;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Atributo getAtributo1()
/* 241:    */   {
/* 242:403 */     return this.atributo1;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setAtributo1(Atributo atributo1)
/* 246:    */   {
/* 247:407 */     this.atributo1 = atributo1;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public ValorAtributo getValorAtributo1()
/* 251:    */   {
/* 252:411 */     return this.valorAtributo1;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setValorAtributo1(ValorAtributo valorAtributo1)
/* 256:    */   {
/* 257:415 */     this.valorAtributo1 = valorAtributo1;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Atributo getAtributo2()
/* 261:    */   {
/* 262:419 */     return this.atributo2;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setAtributo2(Atributo atributo2)
/* 266:    */   {
/* 267:423 */     this.atributo2 = atributo2;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public ValorAtributo getValorAtributo2()
/* 271:    */   {
/* 272:427 */     return this.valorAtributo2;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setValorAtributo2(ValorAtributo valorAtributo2)
/* 276:    */   {
/* 277:431 */     this.valorAtributo2 = valorAtributo2;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Atributo getAtributo3()
/* 281:    */   {
/* 282:435 */     return this.atributo3;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setAtributo3(Atributo atributo3)
/* 286:    */   {
/* 287:439 */     this.atributo3 = atributo3;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public ValorAtributo getValorAtributo3()
/* 291:    */   {
/* 292:443 */     return this.valorAtributo3;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setValorAtributo3(ValorAtributo valorAtributo3)
/* 296:    */   {
/* 297:447 */     this.valorAtributo3 = valorAtributo3;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Atributo getAtributo4()
/* 301:    */   {
/* 302:451 */     return this.atributo4;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setAtributo4(Atributo atributo4)
/* 306:    */   {
/* 307:455 */     this.atributo4 = atributo4;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public ValorAtributo getValorAtributo4()
/* 311:    */   {
/* 312:459 */     return this.valorAtributo4;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setValorAtributo4(ValorAtributo valorAtributo4)
/* 316:    */   {
/* 317:463 */     this.valorAtributo4 = valorAtributo4;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Atributo getAtributo5()
/* 321:    */   {
/* 322:467 */     return this.atributo5;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setAtributo5(Atributo atributo5)
/* 326:    */   {
/* 327:471 */     this.atributo5 = atributo5;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public ValorAtributo getValorAtributo5()
/* 331:    */   {
/* 332:475 */     return this.valorAtributo5;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setValorAtributo5(ValorAtributo valorAtributo5)
/* 336:    */   {
/* 337:479 */     this.valorAtributo5 = valorAtributo5;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Atributo getAtributo6()
/* 341:    */   {
/* 342:483 */     return this.atributo6;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setAtributo6(Atributo atributo6)
/* 346:    */   {
/* 347:487 */     this.atributo6 = atributo6;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public ValorAtributo getValorAtributo6()
/* 351:    */   {
/* 352:491 */     return this.valorAtributo6;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setValorAtributo6(ValorAtributo valorAtributo6)
/* 356:    */   {
/* 357:495 */     this.valorAtributo6 = valorAtributo6;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public Atributo getAtributo7()
/* 361:    */   {
/* 362:499 */     return this.atributo7;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setAtributo7(Atributo atributo7)
/* 366:    */   {
/* 367:503 */     this.atributo7 = atributo7;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public ValorAtributo getValorAtributo7()
/* 371:    */   {
/* 372:507 */     return this.valorAtributo7;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setValorAtributo7(ValorAtributo valorAtributo7)
/* 376:    */   {
/* 377:511 */     this.valorAtributo7 = valorAtributo7;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public Atributo getAtributo8()
/* 381:    */   {
/* 382:515 */     return this.atributo8;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setAtributo8(Atributo atributo8)
/* 386:    */   {
/* 387:519 */     this.atributo8 = atributo8;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public ValorAtributo getValorAtributo8()
/* 391:    */   {
/* 392:523 */     return this.valorAtributo8;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setValorAtributo8(ValorAtributo valorAtributo8)
/* 396:    */   {
/* 397:527 */     this.valorAtributo8 = valorAtributo8;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public Atributo getAtributo9()
/* 401:    */   {
/* 402:531 */     return this.atributo9;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setAtributo9(Atributo atributo9)
/* 406:    */   {
/* 407:535 */     this.atributo9 = atributo9;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public ValorAtributo getValorAtributo9()
/* 411:    */   {
/* 412:539 */     return this.valorAtributo9;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setValorAtributo9(ValorAtributo valorAtributo9)
/* 416:    */   {
/* 417:543 */     this.valorAtributo9 = valorAtributo9;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public Atributo getAtributo10()
/* 421:    */   {
/* 422:547 */     return this.atributo10;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setAtributo10(Atributo atributo10)
/* 426:    */   {
/* 427:551 */     this.atributo10 = atributo10;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public ValorAtributo getValorAtributo10()
/* 431:    */   {
/* 432:555 */     return this.valorAtributo10;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setValorAtributo10(ValorAtributo valorAtributo10)
/* 436:    */   {
/* 437:559 */     this.valorAtributo10 = valorAtributo10;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public boolean isDisableAtributoOrdenFabricacion()
/* 441:    */   {
/* 442:563 */     return this.disableAtributoOrdenFabricacion;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setDisableAtributoOrdenFabricacion(boolean disableAtributoOrdenFabricacion)
/* 446:    */   {
/* 447:567 */     this.disableAtributoOrdenFabricacion = disableAtributoOrdenFabricacion;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public boolean isDisableMovimientoInterno()
/* 451:    */   {
/* 452:571 */     return this.disableMovimientoInterno;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setDisableMovimientoInterno(boolean disableMovimientoInterno)
/* 456:    */   {
/* 457:575 */     this.disableMovimientoInterno = disableMovimientoInterno;
/* 458:    */   }
/* 459:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Lote
 * JD-Core Version:    0.7.0.1
 */