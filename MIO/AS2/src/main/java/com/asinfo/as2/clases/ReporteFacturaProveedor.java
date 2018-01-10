/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="tmp_reporte_factura_proveedor")
/*  23:    */ public class ReporteFacturaProveedor
/*  24:    */ {
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="tmp_reporte_factura_proveedor", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_reporte_factura_proveedor")
/*  28:    */   @Column(name="id_tmp_reporte_factura_proveedor")
/*  29:    */   private Integer idReporteFacturaProveedor;
/*  30:    */   @Transient
/*  31:    */   private Integer idAsientoFacturaProveedor;
/*  32:    */   @Transient
/*  33:    */   private Integer idAsientoRetencion;
/*  34:    */   @Transient
/*  35:    */   private Integer idAsientoRecepcion;
/*  36:    */   @Transient
/*  37:    */   private String codigoEmpresa;
/*  38:    */   @Transient
/*  39:    */   private String nombreEmpresa;
/*  40:    */   @Transient
/*  41:    */   private String identificacionEmpresa;
/*  42:    */   @Transient
/*  43:    */   private String direccion;
/*  44:    */   @Transient
/*  45:    */   private String ciudad;
/*  46:    */   @Transient
/*  47:    */   private String telefono;
/*  48:    */   @Transient
/*  49:    */   private String numeroFactura;
/*  50:    */   @Transient
/*  51:    */   private String numeroRetencion;
/*  52:    */   @Transient
/*  53:    */   private String numeroPedido;
/*  54:    */   @Transient
/*  55:    */   private String numeroRecepcion;
/*  56:    */   @Transient
/*  57:    */   private String numeroAsientoFactura;
/*  58:    */   @Transient
/*  59:    */   private String numeroAsientoRetencion;
/*  60:    */   @Transient
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   private Date fechaFactura;
/*  63:    */   @Transient
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   private Date fechaEmision;
/*  66:    */   @Transient
/*  67:    */   @Temporal(TemporalType.DATE)
/*  68:    */   private Date fechaVencimiento;
/*  69:    */   @Transient
/*  70:    */   @Temporal(TemporalType.DATE)
/*  71:    */   private Date fechaRecepcion;
/*  72:    */   @Transient
/*  73:    */   private Integer numeroCuotas;
/*  74:    */   @Transient
/*  75:    */   private String codigoTipoComprobante;
/*  76:    */   @Transient
/*  77:    */   private String nombreTipoComprobante;
/*  78:    */   @Transient
/*  79:    */   private String codigoCreditoTributario;
/*  80:    */   @Transient
/*  81:    */   private String nombreCreditoTributario;
/*  82:    */   @Transient
/*  83:    */   private String tipoCompra;
/*  84:    */   @Transient
/*  85:    */   private String usuario;
/*  86:    */   @Transient
/*  87:    */   private String descripcion;
/*  88:    */   @Transient
/*  89:    */   private String devolucionIva;
/*  90:    */   @Transient
/*  91:    */   private Integer diasPlazoFactura;
/*  92:    */   @Transient
/*  93:    */   private BigDecimal totalFactura;
/*  94:    */   @Transient
/*  95:    */   private Estado estado;
/*  96:    */   @Transient
/*  97:    */   private Boolean indicadorCreditoTributario;
/*  98:    */   @Transient
/*  99:    */   private BigDecimal valorOrdenPago;
/* 100:    */   @Transient
/* 101:    */   private BigDecimal valorAprobadoOrdenPago;
/* 102:    */   @Transient
/* 103:    */   private BigDecimal valorPagadoOrdenPago;
/* 104:    */   @Transient
/* 105:    */   private BigDecimal saldo;
/* 106:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedor")
/* 107:145 */   private List<ReporteAsiento> listaReporteAsientoFacturaProveedor = new ArrayList();
/* 108:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedorRetencion")
/* 109:148 */   private List<ReporteAsiento> listaReporteAsientoRetencion = new ArrayList();
/* 110:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedorNotaCredito")
/* 111:151 */   private List<ReporteAsiento> listaReporteAsientoNotaCredito = new ArrayList();
/* 112:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedorPago")
/* 113:154 */   private List<ReporteAsiento> listaReporteAsientoPago = new ArrayList();
/* 114:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedorAnticipo")
/* 115:157 */   private List<ReporteAsiento> listaReporteAsientoAnticipo = new ArrayList();
/* 116:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedorLiquidacionAnticipo")
/* 117:160 */   private List<ReporteAsiento> listaReporteAsientoLiquidacionAnticipo = new ArrayList();
/* 118:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteFacturaProveedorRecepcion")
/* 119:163 */   private List<ReporteAsiento> listaReporteAsientoRecepcion = new ArrayList();
/* 120:    */   
/* 121:    */   public ReporteFacturaProveedor(Integer idAsientoFacturaProveedor, Integer idAsientoRetencion, String codigoEmpresa, String nombreEmpresa, String identificacionEmpresa, String direccion, String ciudad, String telefono, String numeroFactura, String numeroRetencion, String numeroPedido, String numeroRecepcion, String numeroAsientoFactura, String numeroAsientoRetencion, Date fechaFactura, Date fechaEmision, Date fechaVencimiento, Date fechaRecepcion, Integer numeroCuotas, String codigoTipoComprobante, String nombreTipoComprobante, String codigoCreditoTributario, String nombreCreditoTributario, String tipoCompra, String usuario, String descripcion, String devolucionIva, Integer diasPlazoFactura, BigDecimal totalFactura, Estado estado, Boolean indicadorCreditoTributario, Integer idAsientoRecepcion)
/* 122:    */   {
/* 123:174 */     this.idAsientoFacturaProveedor = idAsientoFacturaProveedor;
/* 124:175 */     this.idAsientoRetencion = idAsientoRetencion;
/* 125:176 */     this.codigoEmpresa = codigoEmpresa;
/* 126:177 */     this.nombreEmpresa = nombreEmpresa;
/* 127:178 */     this.identificacionEmpresa = identificacionEmpresa;
/* 128:179 */     this.direccion = direccion;
/* 129:180 */     this.ciudad = ciudad;
/* 130:181 */     this.telefono = telefono;
/* 131:182 */     this.numeroFactura = numeroFactura;
/* 132:183 */     this.numeroRetencion = numeroRetencion;
/* 133:184 */     this.numeroPedido = numeroPedido;
/* 134:185 */     this.numeroRecepcion = numeroRecepcion;
/* 135:186 */     this.numeroAsientoFactura = numeroAsientoFactura;
/* 136:187 */     this.numeroAsientoRetencion = numeroAsientoRetencion;
/* 137:188 */     this.fechaFactura = fechaFactura;
/* 138:189 */     this.fechaEmision = fechaEmision;
/* 139:190 */     this.fechaVencimiento = fechaVencimiento;
/* 140:191 */     this.fechaRecepcion = fechaRecepcion;
/* 141:192 */     this.numeroCuotas = numeroCuotas;
/* 142:193 */     this.codigoTipoComprobante = codigoTipoComprobante;
/* 143:194 */     this.nombreTipoComprobante = nombreTipoComprobante;
/* 144:195 */     this.codigoCreditoTributario = codigoCreditoTributario;
/* 145:196 */     this.nombreCreditoTributario = nombreCreditoTributario;
/* 146:197 */     this.tipoCompra = tipoCompra;
/* 147:198 */     this.usuario = usuario;
/* 148:199 */     this.descripcion = descripcion;
/* 149:200 */     this.devolucionIva = devolucionIva;
/* 150:201 */     this.diasPlazoFactura = diasPlazoFactura;
/* 151:202 */     this.totalFactura = totalFactura;
/* 152:203 */     this.estado = estado;
/* 153:204 */     this.indicadorCreditoTributario = indicadorCreditoTributario;
/* 154:205 */     this.idAsientoRecepcion = idAsientoRecepcion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Integer getIdReporteFacturaProveedor()
/* 158:    */   {
/* 159:209 */     return this.idReporteFacturaProveedor;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdReporteFacturaProveedor(Integer idReporteFacturaProveedor)
/* 163:    */   {
/* 164:213 */     this.idReporteFacturaProveedor = idReporteFacturaProveedor;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Integer getIdAsientoFacturaProveedor()
/* 168:    */   {
/* 169:217 */     return this.idAsientoFacturaProveedor;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setIdAsientoFacturaProveedor(Integer idAsientoFacturaProveedor)
/* 173:    */   {
/* 174:221 */     this.idAsientoFacturaProveedor = idAsientoFacturaProveedor;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Integer getIdAsientoRetencion()
/* 178:    */   {
/* 179:225 */     return this.idAsientoRetencion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIdAsientoRetencion(Integer idAsientoRetencion)
/* 183:    */   {
/* 184:229 */     this.idAsientoRetencion = idAsientoRetencion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getCodigoEmpresa()
/* 188:    */   {
/* 189:233 */     return this.codigoEmpresa;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setCodigoEmpresa(String codigoEmpresa)
/* 193:    */   {
/* 194:237 */     this.codigoEmpresa = codigoEmpresa;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getNombreEmpresa()
/* 198:    */   {
/* 199:241 */     return this.nombreEmpresa;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setNombreEmpresa(String nombreEmpresa)
/* 203:    */   {
/* 204:245 */     this.nombreEmpresa = nombreEmpresa;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getIdentificacionEmpresa()
/* 208:    */   {
/* 209:249 */     return this.identificacionEmpresa;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIdentificacionEmpresa(String identificacionEmpresa)
/* 213:    */   {
/* 214:253 */     this.identificacionEmpresa = identificacionEmpresa;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getDireccion()
/* 218:    */   {
/* 219:257 */     return this.direccion;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDireccion(String direccion)
/* 223:    */   {
/* 224:261 */     this.direccion = direccion;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCiudad()
/* 228:    */   {
/* 229:265 */     return this.ciudad;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setCiudad(String ciudad)
/* 233:    */   {
/* 234:269 */     this.ciudad = ciudad;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getTelefono()
/* 238:    */   {
/* 239:273 */     return this.telefono;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setTelefono(String telefono)
/* 243:    */   {
/* 244:277 */     this.telefono = telefono;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getNumeroFactura()
/* 248:    */   {
/* 249:281 */     return this.numeroFactura;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setNumeroFactura(String numeroFactura)
/* 253:    */   {
/* 254:285 */     this.numeroFactura = numeroFactura;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getNumeroRetencion()
/* 258:    */   {
/* 259:289 */     return this.numeroRetencion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setNumeroRetencion(String numeroRetencion)
/* 263:    */   {
/* 264:293 */     this.numeroRetencion = numeroRetencion;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getNumeroRecepcion()
/* 268:    */   {
/* 269:297 */     return this.numeroRecepcion;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setNumeroRecepcion(String numeroRecepcion)
/* 273:    */   {
/* 274:301 */     this.numeroRecepcion = numeroRecepcion;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getNumeroAsientoFactura()
/* 278:    */   {
/* 279:305 */     return this.numeroAsientoFactura;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setNumeroAsientoFactura(String numeroAsientoFactura)
/* 283:    */   {
/* 284:309 */     this.numeroAsientoFactura = numeroAsientoFactura;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String getNumeroAsientoRetencion()
/* 288:    */   {
/* 289:313 */     return this.numeroAsientoRetencion;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setNumeroAsientoRetencion(String numeroAsientoRetencion)
/* 293:    */   {
/* 294:317 */     this.numeroAsientoRetencion = numeroAsientoRetencion;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Date getFechaFactura()
/* 298:    */   {
/* 299:321 */     return this.fechaFactura;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setFechaFactura(Date fechaFactura)
/* 303:    */   {
/* 304:325 */     this.fechaFactura = fechaFactura;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Date getFechaEmision()
/* 308:    */   {
/* 309:329 */     return this.fechaEmision;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setFechaEmision(Date fechaEmision)
/* 313:    */   {
/* 314:333 */     this.fechaEmision = fechaEmision;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public Date getFechaVencimiento()
/* 318:    */   {
/* 319:337 */     return this.fechaVencimiento;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Date getFechaRecepcion()
/* 323:    */   {
/* 324:341 */     return this.fechaRecepcion;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setFechaRecepcion(Date fechaRecepcion)
/* 328:    */   {
/* 329:345 */     this.fechaRecepcion = fechaRecepcion;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 333:    */   {
/* 334:349 */     this.fechaVencimiento = fechaVencimiento;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Integer getNumeroCuotas()
/* 338:    */   {
/* 339:353 */     return this.numeroCuotas;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setNumeroCuotas(Integer numeroCuotas)
/* 343:    */   {
/* 344:357 */     this.numeroCuotas = numeroCuotas;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public String getCodigoTipoComprobante()
/* 348:    */   {
/* 349:361 */     return this.codigoTipoComprobante;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setCodigoTipoComprobante(String codigoTipoComprobante)
/* 353:    */   {
/* 354:365 */     this.codigoTipoComprobante = codigoTipoComprobante;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public String getNombreTipoComprobante()
/* 358:    */   {
/* 359:369 */     return this.nombreTipoComprobante;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setNombreTipoComprobante(String nombreTipoComprobante)
/* 363:    */   {
/* 364:373 */     this.nombreTipoComprobante = nombreTipoComprobante;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public String getCodigoCreditoTributario()
/* 368:    */   {
/* 369:377 */     return this.codigoCreditoTributario;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setCodigoCreditoTributario(String codigoCreditoTributario)
/* 373:    */   {
/* 374:381 */     this.codigoCreditoTributario = codigoCreditoTributario;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public String getNombreCreditoTributario()
/* 378:    */   {
/* 379:385 */     return this.nombreCreditoTributario;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setNombreCreditoTributario(String nombreCreditoTributario)
/* 383:    */   {
/* 384:389 */     this.nombreCreditoTributario = nombreCreditoTributario;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public String getTipoCompra()
/* 388:    */   {
/* 389:393 */     return this.tipoCompra;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setTipoCompra(String tipoCompra)
/* 393:    */   {
/* 394:397 */     this.tipoCompra = tipoCompra;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public String getUsuario()
/* 398:    */   {
/* 399:401 */     return this.usuario;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setUsuario(String usuario)
/* 403:    */   {
/* 404:405 */     this.usuario = usuario;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public String getNumeroPedido()
/* 408:    */   {
/* 409:409 */     return this.numeroPedido;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setNumeroPedido(String numeroPedido)
/* 413:    */   {
/* 414:413 */     this.numeroPedido = numeroPedido;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public String getDescripcion()
/* 418:    */   {
/* 419:417 */     return this.descripcion;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setDescripcion(String descripcion)
/* 423:    */   {
/* 424:421 */     this.descripcion = descripcion;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String getDevolucionIva()
/* 428:    */   {
/* 429:425 */     return this.devolucionIva;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setDevolucionIva(String devolucionIva)
/* 433:    */   {
/* 434:429 */     this.devolucionIva = devolucionIva;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public Integer getDiasPlazoFactura()
/* 438:    */   {
/* 439:433 */     return this.diasPlazoFactura;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setDiasPlazoFactura(Integer diasPlazoFactura)
/* 443:    */   {
/* 444:437 */     this.diasPlazoFactura = diasPlazoFactura;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public BigDecimal getTotalFactura()
/* 448:    */   {
/* 449:441 */     return this.totalFactura;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setTotalFactura(BigDecimal totalFactura)
/* 453:    */   {
/* 454:445 */     this.totalFactura = totalFactura;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public List<ReporteAsiento> getListaReporteAsientoFacturaProveedor()
/* 458:    */   {
/* 459:449 */     return this.listaReporteAsientoFacturaProveedor;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setListaReporteAsientoFacturaProveedor(List<ReporteAsiento> listaReporteAsientoFacturaProveedor)
/* 463:    */   {
/* 464:453 */     this.listaReporteAsientoFacturaProveedor = listaReporteAsientoFacturaProveedor;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public List<ReporteAsiento> getListaReporteAsientoRetencion()
/* 468:    */   {
/* 469:457 */     return this.listaReporteAsientoRetencion;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setListaReporteAsientoRetencion(List<ReporteAsiento> listaReporteAsientoRetencion)
/* 473:    */   {
/* 474:461 */     this.listaReporteAsientoRetencion = listaReporteAsientoRetencion;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public List<ReporteAsiento> getListaReporteAsientoNotaCredito()
/* 478:    */   {
/* 479:465 */     return this.listaReporteAsientoNotaCredito;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setListaReporteAsientoNotaCredito(List<ReporteAsiento> listaReporteAsientoNotaCredito)
/* 483:    */   {
/* 484:469 */     this.listaReporteAsientoNotaCredito = listaReporteAsientoNotaCredito;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public List<ReporteAsiento> getListaReporteAsientoPago()
/* 488:    */   {
/* 489:473 */     return this.listaReporteAsientoPago;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setListaReporteAsientoPago(List<ReporteAsiento> listaReporteAsientoPago)
/* 493:    */   {
/* 494:477 */     this.listaReporteAsientoPago = listaReporteAsientoPago;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public Estado getEstado()
/* 498:    */   {
/* 499:481 */     return this.estado;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setEstado(Estado estado)
/* 503:    */   {
/* 504:485 */     this.estado = estado;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public Boolean getIndicadorCreditoTributario()
/* 508:    */   {
/* 509:489 */     return this.indicadorCreditoTributario;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setIndicadorCreditoTributario(Boolean indicadorCreditoTributario)
/* 513:    */   {
/* 514:493 */     this.indicadorCreditoTributario = indicadorCreditoTributario;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public List<ReporteAsiento> getListaReporteAsientoAnticipo()
/* 518:    */   {
/* 519:497 */     return this.listaReporteAsientoAnticipo;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setListaReporteAsientoAnticipo(List<ReporteAsiento> listaReporteAsientoAnticipo)
/* 523:    */   {
/* 524:501 */     this.listaReporteAsientoAnticipo = listaReporteAsientoAnticipo;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public List<ReporteAsiento> getListaReporteAsientoLiquidacionAnticipo()
/* 528:    */   {
/* 529:505 */     return this.listaReporteAsientoLiquidacionAnticipo;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void setListaReporteAsientoLiquidacionAnticipo(List<ReporteAsiento> listaReporteAsientoLiquidacionAnticipo)
/* 533:    */   {
/* 534:509 */     this.listaReporteAsientoLiquidacionAnticipo = listaReporteAsientoLiquidacionAnticipo;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public BigDecimal getValorOrdenPago()
/* 538:    */   {
/* 539:513 */     return this.valorOrdenPago;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void setValorOrdenPago(BigDecimal valorOrdenPago)
/* 543:    */   {
/* 544:517 */     this.valorOrdenPago = valorOrdenPago;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public BigDecimal getValorAprobadoOrdenPago()
/* 548:    */   {
/* 549:521 */     return this.valorAprobadoOrdenPago;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setValorAprobadoOrdenPago(BigDecimal valorAprobadoOrdenPago)
/* 553:    */   {
/* 554:525 */     this.valorAprobadoOrdenPago = valorAprobadoOrdenPago;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public BigDecimal getValorPagadoOrdenPago()
/* 558:    */   {
/* 559:529 */     return this.valorPagadoOrdenPago;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setValorPagadoOrdenPago(BigDecimal valorPagadoOrdenPago)
/* 563:    */   {
/* 564:533 */     this.valorPagadoOrdenPago = valorPagadoOrdenPago;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public BigDecimal getSaldo()
/* 568:    */   {
/* 569:537 */     return this.saldo;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void setSaldo(BigDecimal saldo)
/* 573:    */   {
/* 574:541 */     this.saldo = saldo;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public List<ReporteAsiento> getListaReporteAsientoRecepcion()
/* 578:    */   {
/* 579:545 */     return this.listaReporteAsientoRecepcion;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void setListaReporteAsientoRecepcion(List<ReporteAsiento> listaReporteAsientoRecepcion)
/* 583:    */   {
/* 584:549 */     this.listaReporteAsientoRecepcion = listaReporteAsientoRecepcion;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public Integer getIdAsientoRecepcion()
/* 588:    */   {
/* 589:553 */     return this.idAsientoRecepcion;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void setIdAsientoRecepcion(Integer idAsientoRecepcion)
/* 593:    */   {
/* 594:557 */     this.idAsientoRecepcion = idAsientoRecepcion;
/* 595:    */   }
/* 596:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteFacturaProveedor
 * JD-Core Version:    0.7.0.1
 */