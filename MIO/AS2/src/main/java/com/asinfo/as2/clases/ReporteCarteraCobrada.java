/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.persistence.Temporal;
/*  13:    */ import javax.persistence.TemporalType;
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="tmp_reporte_cartera_cobrada")
/*  18:    */ public class ReporteCarteraCobrada
/*  19:    */ {
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="tmp_reporte_cartera_cobrada", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_reporte_cartera_cobrada")
/*  23:    */   @Column(name="id_tmp_reporte_cartera_cobrada")
/*  24:    */   private Integer idReporteCarteraCobrada;
/*  25:    */   @Transient
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Transient
/*  28:    */   private int idEmpresa;
/*  29:    */   @Transient
/*  30:    */   private String nombreEmpresa;
/*  31:    */   @Transient
/*  32:    */   private String codigoEmpresa;
/*  33:    */   @Transient
/*  34:    */   private String identificacionEmpresa;
/*  35:    */   @Transient
/*  36:    */   private String codigoTipoIdentificacionEmpresa;
/*  37:    */   @Transient
/*  38:    */   private Integer idSubempresa;
/*  39:    */   @Transient
/*  40:    */   private String empresaFinal;
/*  41:    */   @Transient
/*  42:    */   private String identificacionSubempresa;
/*  43:    */   @Transient
/*  44:    */   private String nombreSubempresa;
/*  45:    */   @Transient
/*  46:    */   private Integer idFacturaCliente;
/*  47:    */   @Transient
/*  48:    */   private String numeroFactura;
/*  49:    */   @Transient
/*  50:    */   @Temporal(TemporalType.DATE)
/*  51:    */   private Date fechaFactura;
/*  52:    */   @Transient
/*  53:    */   private String codigoDocumentoFactura;
/*  54:    */   @Transient
/*  55:    */   private String nombreDocumentoFactura;
/*  56:    */   @Transient
/*  57:    */   private String asientoFactura;
/*  58:    */   @Transient
/*  59:    */   private Integer diasPlazoFactura;
/*  60:    */   @Transient
/*  61:    */   private BigDecimal totalFactura;
/*  62:    */   @Transient
/*  63: 62 */   private BigDecimal total = BigDecimal.ZERO;
/*  64:    */   @Transient
/*  65:    */   private Integer idCuentaPorCobrar;
/*  66:    */   @Transient
/*  67:    */   @Temporal(TemporalType.DATE)
/*  68:    */   private Date fechaVencimiento;
/*  69:    */   @Transient
/*  70:    */   private BigDecimal valorCuentaPorCobrar;
/*  71:    */   @Transient
/*  72:    */   private Integer idCobro;
/*  73:    */   @Transient
/*  74:    */   @Temporal(TemporalType.DATE)
/*  75:    */   private Date fechaCobro;
/*  76:    */   @Transient
/*  77:    */   private String numeroCobro;
/*  78:    */   @Transient
/*  79:    */   private String codigoDocumentoCobro;
/*  80:    */   @Transient
/*  81:    */   private String nombreDocumentoCobro;
/*  82:    */   @Transient
/*  83:    */   private BigDecimal valorCobrado;
/*  84:    */   @Transient
/*  85:    */   private String asientoCobro;
/*  86:    */   @Transient
/*  87:    */   private Integer idAgenteComercial;
/*  88:    */   @Transient
/*  89:    */   private String nombreAgenteComercial;
/*  90:    */   @Transient
/*  91:    */   private Integer idRecaudador;
/*  92:    */   @Transient
/*  93:    */   private String nombreRecaudador;
/*  94:    */   @Transient
/*  95: 94 */   private BigDecimal cobrado0 = BigDecimal.ZERO;
/*  96:    */   @Transient
/*  97: 96 */   private BigDecimal cobrado30 = BigDecimal.ZERO;
/*  98:    */   @Transient
/*  99: 98 */   private BigDecimal cobrado60 = BigDecimal.ZERO;
/* 100:    */   @Transient
/* 101:100 */   private BigDecimal cobrado90 = BigDecimal.ZERO;
/* 102:    */   @Transient
/* 103:102 */   private BigDecimal cobrado120 = BigDecimal.ZERO;
/* 104:    */   @Transient
/* 105:104 */   private BigDecimal totalCobrado = BigDecimal.ZERO;
/* 106:    */   @Transient
/* 107:    */   private BigDecimal promedioDiasPlazo;
/* 108:    */   @Transient
/* 109:    */   private BigDecimal promedioDiasRotacion;
/* 110:    */   @Transient
/* 111:110 */   private BigDecimal diferenciaDias = BigDecimal.ZERO;
/* 112:    */   @Transient
/* 113:    */   private String calificacion;
/* 114:    */   @Transient
/* 115:    */   private String nombreCalificacion;
/* 116:    */   @Transient
/* 117:116 */   private BigDecimal saldo = BigDecimal.ZERO;
/* 118:    */   
/* 119:    */   public ReporteCarteraCobrada(Integer idOrganizacion, Integer idEmpresa, String nombreEmpresa, String codigoEmpresa, String identificacionEmpresa, String codigoTipoIdentificacionEmpresa, Integer idSubempresa, String empresaFinal, String identificacionSubempresa, String nombreSubempresa, Integer idFacturaCliente, String numeroFactura, Date fechaFactura, String codigoDocumentoFactura, String nombreDocumentoFactura, String asientoFactura, Integer diasPlazoFactura, BigDecimal totalFactura, Integer idCuentaPorCobrar, Date fechaVencimiento, BigDecimal valorCuentaPorCobrar, Integer idCobro, Date fechaCobro, String numeroCobro, String codigoDocumentoCobro, String nombreDocumentoCobro, BigDecimal valorCobrado, String asientoCobro, Integer idAgenteComercial, String nombreAgenteComercial, Integer idRecaudador, String nombreRecaudador)
/* 120:    */   {
/* 121:126 */     this.idOrganizacion = idOrganizacion.intValue();
/* 122:127 */     this.idEmpresa = idEmpresa.intValue();
/* 123:128 */     this.nombreEmpresa = nombreEmpresa;
/* 124:129 */     this.codigoEmpresa = codigoEmpresa;
/* 125:130 */     this.identificacionEmpresa = identificacionEmpresa;
/* 126:131 */     this.codigoTipoIdentificacionEmpresa = codigoTipoIdentificacionEmpresa;
/* 127:132 */     this.idSubempresa = idSubempresa;
/* 128:133 */     this.empresaFinal = empresaFinal;
/* 129:134 */     this.identificacionSubempresa = identificacionSubempresa;
/* 130:135 */     this.nombreSubempresa = nombreSubempresa;
/* 131:136 */     this.idFacturaCliente = idFacturaCliente;
/* 132:137 */     this.numeroFactura = numeroFactura;
/* 133:138 */     this.fechaFactura = fechaFactura;
/* 134:139 */     this.codigoDocumentoFactura = codigoDocumentoFactura;
/* 135:140 */     this.nombreDocumentoFactura = nombreDocumentoFactura;
/* 136:141 */     this.asientoFactura = asientoFactura;
/* 137:142 */     this.diasPlazoFactura = diasPlazoFactura;
/* 138:143 */     this.totalFactura = totalFactura;
/* 139:144 */     this.idCuentaPorCobrar = idCuentaPorCobrar;
/* 140:145 */     this.fechaVencimiento = fechaVencimiento;
/* 141:146 */     this.valorCuentaPorCobrar = valorCuentaPorCobrar;
/* 142:147 */     this.idCobro = idCobro;
/* 143:148 */     this.fechaCobro = fechaCobro;
/* 144:149 */     this.numeroCobro = numeroCobro;
/* 145:150 */     this.codigoDocumentoCobro = codigoDocumentoCobro;
/* 146:151 */     this.nombreDocumentoCobro = nombreDocumentoCobro;
/* 147:152 */     this.valorCobrado = valorCobrado;
/* 148:153 */     this.asientoCobro = asientoCobro;
/* 149:154 */     this.idAgenteComercial = idAgenteComercial;
/* 150:155 */     this.nombreAgenteComercial = nombreAgenteComercial;
/* 151:156 */     this.idRecaudador = idRecaudador;
/* 152:157 */     this.nombreRecaudador = nombreRecaudador;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public ReporteCarteraCobrada(Integer idOrganizacion, Integer idEmpresa, String nombreEmpresa, String codigoEmpresa, String identificacionEmpresa, String codigoTipoIdentificacionEmpresa, Integer idAgenteComercial, String nombreAgenteComercial, Double promedioDiasPlazo, Double promedioDiasRotacion, Double diferenciaDias)
/* 156:    */   {
/* 157:163 */     this.idOrganizacion = idOrganizacion.intValue();
/* 158:164 */     this.idEmpresa = idEmpresa.intValue();
/* 159:165 */     this.nombreEmpresa = nombreEmpresa;
/* 160:166 */     this.codigoEmpresa = codigoEmpresa;
/* 161:167 */     this.identificacionEmpresa = identificacionEmpresa;
/* 162:168 */     this.codigoTipoIdentificacionEmpresa = codigoTipoIdentificacionEmpresa;
/* 163:169 */     this.idAgenteComercial = idAgenteComercial;
/* 164:170 */     this.nombreAgenteComercial = nombreAgenteComercial;
/* 165:171 */     this.promedioDiasPlazo = new BigDecimal(promedioDiasPlazo.doubleValue());
/* 166:172 */     this.promedioDiasRotacion = new BigDecimal(promedioDiasRotacion.doubleValue());
/* 167:173 */     this.diferenciaDias = new BigDecimal(diferenciaDias.doubleValue());
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Integer getIdReporteCarteraCobrada()
/* 171:    */   {
/* 172:177 */     return this.idReporteCarteraCobrada;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setIdReporteCarteraCobrada(Integer idReporteCarteraCobrada)
/* 176:    */   {
/* 177:181 */     this.idReporteCarteraCobrada = idReporteCarteraCobrada;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getIdOrganizacion()
/* 181:    */   {
/* 182:185 */     return this.idOrganizacion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setIdOrganizacion(int idOrganizacion)
/* 186:    */   {
/* 187:189 */     this.idOrganizacion = idOrganizacion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getIdEmpresa()
/* 191:    */   {
/* 192:193 */     return this.idEmpresa;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setIdEmpresa(int idEmpresa)
/* 196:    */   {
/* 197:197 */     this.idEmpresa = idEmpresa;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getNombreEmpresa()
/* 201:    */   {
/* 202:201 */     return this.nombreEmpresa;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setNombreEmpresa(String nombreEmpresa)
/* 206:    */   {
/* 207:205 */     this.nombreEmpresa = nombreEmpresa;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getCodigoEmpresa()
/* 211:    */   {
/* 212:209 */     return this.codigoEmpresa;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setCodigoEmpresa(String codigoEmpresa)
/* 216:    */   {
/* 217:213 */     this.codigoEmpresa = codigoEmpresa;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String getIdentificacionEmpresa()
/* 221:    */   {
/* 222:217 */     return this.identificacionEmpresa;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setIdentificacionEmpresa(String identificacionEmpresa)
/* 226:    */   {
/* 227:221 */     this.identificacionEmpresa = identificacionEmpresa;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getCodigoTipoIdentificacionEmpresa()
/* 231:    */   {
/* 232:225 */     return this.codigoTipoIdentificacionEmpresa;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setCodigoTipoIdentificacionEmpresa(String codigoTipoIdentificacionEmpresa)
/* 236:    */   {
/* 237:229 */     this.codigoTipoIdentificacionEmpresa = codigoTipoIdentificacionEmpresa;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Integer getIdSubempresa()
/* 241:    */   {
/* 242:233 */     return this.idSubempresa;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setIdSubempresa(Integer idSubempresa)
/* 246:    */   {
/* 247:237 */     this.idSubempresa = idSubempresa;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getEmpresaFinal()
/* 251:    */   {
/* 252:241 */     return this.empresaFinal;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setEmpresaFinal(String empresaFinal)
/* 256:    */   {
/* 257:245 */     this.empresaFinal = empresaFinal;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getIdentificacionSubempresa()
/* 261:    */   {
/* 262:249 */     return this.identificacionSubempresa;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setIdentificacionSubempresa(String identificacionSubempresa)
/* 266:    */   {
/* 267:253 */     this.identificacionSubempresa = identificacionSubempresa;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getNombreSubempresa()
/* 271:    */   {
/* 272:257 */     return this.nombreSubempresa;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setNombreSubempresa(String nombreSubempresa)
/* 276:    */   {
/* 277:261 */     this.nombreSubempresa = nombreSubempresa;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Integer getIdFacturaCliente()
/* 281:    */   {
/* 282:265 */     return this.idFacturaCliente;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 286:    */   {
/* 287:269 */     this.idFacturaCliente = idFacturaCliente;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public String getNumeroFactura()
/* 291:    */   {
/* 292:273 */     return this.numeroFactura;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setNumeroFactura(String numeroFactura)
/* 296:    */   {
/* 297:277 */     this.numeroFactura = numeroFactura;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Date getFechaFactura()
/* 301:    */   {
/* 302:281 */     return this.fechaFactura;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setFechaFactura(Date fechaFactura)
/* 306:    */   {
/* 307:285 */     this.fechaFactura = fechaFactura;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String getCodigoDocumentoFactura()
/* 311:    */   {
/* 312:289 */     return this.codigoDocumentoFactura;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setCodigoDocumentoFactura(String codigoDocumentoFactura)
/* 316:    */   {
/* 317:293 */     this.codigoDocumentoFactura = codigoDocumentoFactura;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getNombreDocumentoFactura()
/* 321:    */   {
/* 322:297 */     return this.nombreDocumentoFactura;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setNombreDocumentoFactura(String nombreDocumentoFactura)
/* 326:    */   {
/* 327:301 */     this.nombreDocumentoFactura = nombreDocumentoFactura;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String getAsientoFactura()
/* 331:    */   {
/* 332:305 */     return this.asientoFactura;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setAsientoFactura(String asientoFactura)
/* 336:    */   {
/* 337:309 */     this.asientoFactura = asientoFactura;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Integer getDiasPlazoFactura()
/* 341:    */   {
/* 342:313 */     return this.diasPlazoFactura;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setDiasPlazoFactura(Integer diasPlazoFactura)
/* 346:    */   {
/* 347:317 */     this.diasPlazoFactura = diasPlazoFactura;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public BigDecimal getTotalFactura()
/* 351:    */   {
/* 352:321 */     return this.totalFactura;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setTotalFactura(BigDecimal totalFactura)
/* 356:    */   {
/* 357:325 */     this.totalFactura = totalFactura;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public BigDecimal getTotal()
/* 361:    */   {
/* 362:329 */     return this.total;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setTotal(BigDecimal total)
/* 366:    */   {
/* 367:333 */     this.total = total;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Integer getIdCuentaPorCobrar()
/* 371:    */   {
/* 372:337 */     return this.idCuentaPorCobrar;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setIdCuentaPorCobrar(Integer idCuentaPorCobrar)
/* 376:    */   {
/* 377:341 */     this.idCuentaPorCobrar = idCuentaPorCobrar;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public Date getFechaVencimiento()
/* 381:    */   {
/* 382:345 */     return this.fechaVencimiento;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 386:    */   {
/* 387:349 */     this.fechaVencimiento = fechaVencimiento;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public BigDecimal getValorCuentaPorCobrar()
/* 391:    */   {
/* 392:353 */     return this.valorCuentaPorCobrar;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setValorCuentaPorCobrar(BigDecimal valorCuentaPorCobrar)
/* 396:    */   {
/* 397:357 */     this.valorCuentaPorCobrar = valorCuentaPorCobrar;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public Integer getIdCobro()
/* 401:    */   {
/* 402:361 */     return this.idCobro;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setIdCobro(Integer idCobro)
/* 406:    */   {
/* 407:365 */     this.idCobro = idCobro;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public Date getFechaCobro()
/* 411:    */   {
/* 412:369 */     return this.fechaCobro;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setFechaCobro(Date fechaCobro)
/* 416:    */   {
/* 417:373 */     this.fechaCobro = fechaCobro;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public String getNumeroCobro()
/* 421:    */   {
/* 422:377 */     return this.numeroCobro;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setNumeroCobro(String numeroCobro)
/* 426:    */   {
/* 427:381 */     this.numeroCobro = numeroCobro;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String getCodigoDocumentoCobro()
/* 431:    */   {
/* 432:385 */     return this.codigoDocumentoCobro;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setCodigoDocumentoCobro(String codigoDocumentoCobro)
/* 436:    */   {
/* 437:389 */     this.codigoDocumentoCobro = codigoDocumentoCobro;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public String getNombreDocumentoCobro()
/* 441:    */   {
/* 442:393 */     return this.nombreDocumentoCobro;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setNombreDocumentoCobro(String nombreDocumentoCobro)
/* 446:    */   {
/* 447:397 */     this.nombreDocumentoCobro = nombreDocumentoCobro;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public BigDecimal getValorCobrado()
/* 451:    */   {
/* 452:401 */     return this.valorCobrado;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setValorCobrado(BigDecimal valorCobrado)
/* 456:    */   {
/* 457:405 */     this.valorCobrado = valorCobrado;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public String getAsientoCobro()
/* 461:    */   {
/* 462:409 */     return this.asientoCobro;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void setAsientoCobro(String asientoCobro)
/* 466:    */   {
/* 467:413 */     this.asientoCobro = asientoCobro;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public Integer getIdAgenteComercial()
/* 471:    */   {
/* 472:417 */     return this.idAgenteComercial;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void setIdAgenteComercial(Integer idAgenteComercial)
/* 476:    */   {
/* 477:421 */     this.idAgenteComercial = idAgenteComercial;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public String getNombreAgenteComercial()
/* 481:    */   {
/* 482:425 */     return this.nombreAgenteComercial;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public void setNombreAgenteComercial(String nombreAgenteComercial)
/* 486:    */   {
/* 487:429 */     this.nombreAgenteComercial = nombreAgenteComercial;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public Integer getIdRecaudador()
/* 491:    */   {
/* 492:433 */     return this.idRecaudador;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public void setIdRecaudador(Integer idRecaudador)
/* 496:    */   {
/* 497:437 */     this.idRecaudador = idRecaudador;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public String getNombreRecaudador()
/* 501:    */   {
/* 502:441 */     return this.nombreRecaudador;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setNombreRecaudador(String nombreRecaudador)
/* 506:    */   {
/* 507:445 */     this.nombreRecaudador = nombreRecaudador;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public BigDecimal getCobrado0()
/* 511:    */   {
/* 512:449 */     return this.cobrado0;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setCobrado0(BigDecimal cobrado0)
/* 516:    */   {
/* 517:453 */     this.cobrado0 = cobrado0;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public BigDecimal getCobrado30()
/* 521:    */   {
/* 522:457 */     return this.cobrado30;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void setCobrado30(BigDecimal cobrado30)
/* 526:    */   {
/* 527:461 */     this.cobrado30 = cobrado30;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public BigDecimal getCobrado60()
/* 531:    */   {
/* 532:465 */     return this.cobrado60;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public void setCobrado60(BigDecimal cobrado60)
/* 536:    */   {
/* 537:469 */     this.cobrado60 = cobrado60;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public BigDecimal getCobrado90()
/* 541:    */   {
/* 542:473 */     return this.cobrado90;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void setCobrado90(BigDecimal cobrado90)
/* 546:    */   {
/* 547:477 */     this.cobrado90 = cobrado90;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public BigDecimal getCobrado120()
/* 551:    */   {
/* 552:481 */     return this.cobrado120;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public void setCobrado120(BigDecimal cobrado120)
/* 556:    */   {
/* 557:485 */     this.cobrado120 = cobrado120;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public BigDecimal getTotalCobrado()
/* 561:    */   {
/* 562:489 */     return this.totalCobrado;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public void setTotalCobrado(BigDecimal totalCobrado)
/* 566:    */   {
/* 567:493 */     this.totalCobrado = totalCobrado;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public BigDecimal getPromedioDiasPlazo()
/* 571:    */   {
/* 572:497 */     return this.promedioDiasPlazo;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public void setPromedioDiasPlazo(BigDecimal promedioDiasPlazo)
/* 576:    */   {
/* 577:501 */     this.promedioDiasPlazo = promedioDiasPlazo;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public BigDecimal getPromedioDiasRotacion()
/* 581:    */   {
/* 582:505 */     return this.promedioDiasRotacion;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void setPromedioDiasRotacion(BigDecimal promedioDiasRotacion)
/* 586:    */   {
/* 587:509 */     this.promedioDiasRotacion = promedioDiasRotacion;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public String getCalificacion()
/* 591:    */   {
/* 592:513 */     return this.calificacion;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void setCalificacion(String calificacion)
/* 596:    */   {
/* 597:517 */     this.calificacion = calificacion;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public String getNombreCalificacion()
/* 601:    */   {
/* 602:521 */     return this.nombreCalificacion;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public void setNombreCalificacion(String nombreCalificacion)
/* 606:    */   {
/* 607:525 */     this.nombreCalificacion = nombreCalificacion;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public BigDecimal getDiferenciaDias()
/* 611:    */   {
/* 612:529 */     return this.diferenciaDias;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public void setDiferenciaDias(BigDecimal diferenciaDias)
/* 616:    */   {
/* 617:533 */     this.diferenciaDias = diferenciaDias;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public BigDecimal getSaldo()
/* 621:    */   {
/* 622:537 */     return this.saldo;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public void setSaldo(BigDecimal saldo)
/* 626:    */   {
/* 627:541 */     this.saldo = saldo;
/* 628:    */   }
/* 629:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteCarteraCobrada
 * JD-Core Version:    0.7.0.1
 */