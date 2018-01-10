/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="cobro", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(columnList="estado"), @javax.persistence.Index(name="ix_cobro_asiento", columnList="id_asiento"), @javax.persistence.Index(name="ix_cobro_organizacion", columnList="id_organizacion")})
/*  30:    */ public class Cobro
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="cobro", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cobro")
/*  37:    */   @Column(name="id_cobro")
/*  38:    */   private int idCobro;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  43:    */   private Sucursal sucursal;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Empresa empresa;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_documento", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private Documento documento;
/*  52:    */   @Enumerated(EnumType.ORDINAL)
/*  53:    */   @Column(name="estado", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private Estado estado;
/*  56:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  57:    */   private Date fechaContabilizacion;
/*  58:    */   @Column(name="numero", nullable=false, length=20)
/*  59:    */   @Size(max=20)
/*  60: 88 */   private String numero = "";
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha", nullable=false)
/*  63:    */   @NotNull
/*  64:    */   private Date fecha;
/*  65:    */   @Column(name="fecha_registro", nullable=true)
/*  66:    */   private Date fechaRegistro;
/*  67:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  68:    */   private BigDecimal valor;
/*  69:    */   @Column(name="descripcion", length=200, nullable=true)
/*  70:    */   @Size(max=200)
/*  71:103 */   private String descripcion = "";
/*  72:    */   @Column(name="descripcion2", length=500, nullable=true)
/*  73:    */   @Size(max=500)
/*  74:108 */   private String descripcion2 = "";
/*  75:    */   @OneToOne(fetch=FetchType.LAZY)
/*  76:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  77:    */   private Asiento asiento;
/*  78:    */   @OneToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_asiento_protesto", nullable=true)
/*  80:    */   private Asiento asientoProtesto;
/*  81:    */   @OneToMany(mappedBy="cobro", fetch=FetchType.LAZY)
/*  82:120 */   private List<DetalleCobro> listaDetalleCobro = new ArrayList();
/*  83:    */   @OneToMany(mappedBy="cobro", fetch=FetchType.LAZY)
/*  84:123 */   private List<DetalleFormaCobro> listaDetalleFormaCobro = new ArrayList();
/*  85:    */   @Column(name="indicador_tiene_cheques", nullable=false)
/*  86:    */   private boolean indicadorTieneCheques;
/*  87:    */   @Column(name="indicador_tiene_posfechados", nullable=false)
/*  88:    */   private boolean indicadorTienePosfechados;
/*  89:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  90:    */   @JoinColumn(name="id_recaudador", nullable=true)
/*  91:    */   private Recaudador recaudador;
/*  92:    */   @Column(name="indicador_prorrateado_detalles", nullable=true)
/*  93:    */   private Boolean indicadorProrrateadoDetalles;
/*  94:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  95:    */   private Integer idDispositivoSincronizacion;
/*  96:    */   @Column(name="codigo_movil", nullable=true, length=200)
/*  97:    */   @Size(max=200)
/*  98:142 */   private String codigoMovil = "";
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/* 101:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/* 102:    */   @Column(name="documento_referencia", nullable=true, length=20)
/* 103:    */   @Size(max=20)
/* 104:    */   private String documentoReferencia;
/* 105:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 106:    */   @JoinColumn(name="id_forma_pago", nullable=true)
/* 107:    */   private FormaPago formaPago;
/* 108:    */   @Column(name="archivo", nullable=true)
/* 109:    */   @Size(max=50)
/* 110:    */   private String archivo;
/* 111:    */   @Transient
/* 112:164 */   private BigDecimal tolerancia = BigDecimal.ZERO;
/* 113:    */   @Transient
/* 114:    */   private boolean indicadorCobroLiquidacion;
/* 115:    */   @Transient
/* 116:    */   private Date fechaCobroLiquidacion;
/* 117:    */   @Transient
/* 118:    */   private boolean indicadorEmisionRetencion;
/* 119:    */   @Transient
/* 120:    */   private BigDecimal totalLiquidado;
/* 121:    */   @Transient
/* 122:    */   private Caja caja;
/* 123:    */   @Transient
/* 124:    */   private AnticipoCliente anticipoCliente;
/* 125:    */   @Transient
/* 126:184 */   private boolean indicadorExcede = false;
/* 127:    */   @Transient
/* 128:    */   private String numeroFactura;
/* 129:    */   
/* 130:    */   public int getId()
/* 131:    */   {
/* 132:198 */     return this.idCobro;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getIdCobro()
/* 136:    */   {
/* 137:210 */     return this.idCobro;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setIdCobro(int idCobro)
/* 141:    */   {
/* 142:220 */     this.idCobro = idCobro;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdOrganizacion()
/* 146:    */   {
/* 147:229 */     return this.idOrganizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdOrganizacion(int idOrganizacion)
/* 151:    */   {
/* 152:239 */     this.idOrganizacion = idOrganizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Sucursal getSucursal()
/* 156:    */   {
/* 157:246 */     return this.sucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setSucursal(Sucursal sucursal)
/* 161:    */   {
/* 162:254 */     this.sucursal = sucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Documento getDocumento()
/* 166:    */   {
/* 167:263 */     return this.documento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setDocumento(Documento documento)
/* 171:    */   {
/* 172:273 */     this.documento = documento;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getNumero()
/* 176:    */   {
/* 177:282 */     return this.numero;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setNumero(String numero)
/* 181:    */   {
/* 182:292 */     this.numero = numero;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Date getFecha()
/* 186:    */   {
/* 187:301 */     return this.fecha;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setFecha(Date fecha)
/* 191:    */   {
/* 192:311 */     this.fecha = fecha;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public BigDecimal getValor()
/* 196:    */   {
/* 197:320 */     return this.valor;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setValor(BigDecimal valor)
/* 201:    */   {
/* 202:330 */     this.valor = valor;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Empresa getEmpresa()
/* 206:    */   {
/* 207:339 */     return this.empresa;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setEmpresa(Empresa empresa)
/* 211:    */   {
/* 212:349 */     this.empresa = empresa;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<DetalleCobro> getListaDetalleCobro()
/* 216:    */   {
/* 217:353 */     if (this.listaDetalleCobro == null) {
/* 218:354 */       this.listaDetalleCobro = new ArrayList();
/* 219:    */     }
/* 220:356 */     return this.listaDetalleCobro;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaDetalleCobro(List<DetalleCobro> listaDetalleCobro)
/* 224:    */   {
/* 225:360 */     this.listaDetalleCobro = listaDetalleCobro;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Date getFechaContabilizacion()
/* 229:    */   {
/* 230:364 */     return this.fechaContabilizacion;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 234:    */   {
/* 235:368 */     this.fechaContabilizacion = fechaContabilizacion;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getDescripcion()
/* 239:    */   {
/* 240:372 */     return this.descripcion;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setDescripcion(String descripcion)
/* 244:    */   {
/* 245:376 */     this.descripcion = descripcion;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public List<DetalleFormaCobro> getListaDetalleFormaCobro()
/* 249:    */   {
/* 250:385 */     return this.listaDetalleFormaCobro;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setListaDetalleFormaCobro(List<DetalleFormaCobro> listaDetalleFormaCobro)
/* 254:    */   {
/* 255:395 */     this.listaDetalleFormaCobro = listaDetalleFormaCobro;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Estado getEstado()
/* 259:    */   {
/* 260:404 */     return this.estado;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setEstado(Estado estado)
/* 264:    */   {
/* 265:414 */     this.estado = estado;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Asiento getAsiento()
/* 269:    */   {
/* 270:423 */     return this.asiento;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setAsiento(Asiento asiento)
/* 274:    */   {
/* 275:433 */     this.asiento = asiento;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String toString()
/* 279:    */   {
/* 280:443 */     return this.numero;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public boolean isIndicadorTienePosfechados()
/* 284:    */   {
/* 285:452 */     return this.indicadorTienePosfechados;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setIndicadorTienePosfechados(boolean indicadorTienePosfechados)
/* 289:    */   {
/* 290:462 */     this.indicadorTienePosfechados = indicadorTienePosfechados;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public boolean isIndicadorTieneCheques()
/* 294:    */   {
/* 295:471 */     return this.indicadorTieneCheques;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setIndicadorTieneCheques(boolean indicadorTieneCheques)
/* 299:    */   {
/* 300:481 */     this.indicadorTieneCheques = indicadorTieneCheques;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public Asiento getAsientoProtesto()
/* 304:    */   {
/* 305:490 */     return this.asientoProtesto;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setAsientoProtesto(Asiento asientoProtesto)
/* 309:    */   {
/* 310:500 */     this.asientoProtesto = asientoProtesto;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public BigDecimal getTolerancia()
/* 314:    */   {
/* 315:507 */     return this.tolerancia;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setTolerancia(BigDecimal tolerancia)
/* 319:    */   {
/* 320:515 */     this.tolerancia = tolerancia;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public boolean isIndicadorEmisionRetencion()
/* 324:    */   {
/* 325:524 */     return this.indicadorEmisionRetencion;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setIndicadorEmisionRetencion(boolean indicadorEmisionRetencion)
/* 329:    */   {
/* 330:534 */     this.indicadorEmisionRetencion = indicadorEmisionRetencion;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean getIndicadorCobroLiquidacion()
/* 334:    */   {
/* 335:538 */     return this.indicadorCobroLiquidacion;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setIndicadorCobroLiquidacion(boolean indicadorCobroLiquidacion)
/* 339:    */   {
/* 340:542 */     this.indicadorCobroLiquidacion = indicadorCobroLiquidacion;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Date getFechaCobroLiquidacion()
/* 344:    */   {
/* 345:546 */     return this.fechaCobroLiquidacion;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setFechaCobroLiquidacion(Date fechaCobroLiquidacion)
/* 349:    */   {
/* 350:550 */     this.fechaCobroLiquidacion = fechaCobroLiquidacion;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public Recaudador getRecaudador()
/* 354:    */   {
/* 355:559 */     return this.recaudador;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setRecaudador(Recaudador recaudador)
/* 359:    */   {
/* 360:569 */     this.recaudador = recaudador;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public Date getFechaRegistro()
/* 364:    */   {
/* 365:573 */     return this.fechaRegistro;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setFechaRegistro(Date fechaRegistro)
/* 369:    */   {
/* 370:577 */     this.fechaRegistro = fechaRegistro;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public Boolean getIndicadorProrrateadoDetalles()
/* 374:    */   {
/* 375:581 */     if (this.indicadorProrrateadoDetalles == null) {
/* 376:582 */       this.indicadorProrrateadoDetalles = Boolean.valueOf(false);
/* 377:    */     }
/* 378:584 */     return this.indicadorProrrateadoDetalles;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setIndicadorProrrateadoDetalles(Boolean indicadorProrrateadoDetalles)
/* 382:    */   {
/* 383:588 */     this.indicadorProrrateadoDetalles = indicadorProrrateadoDetalles;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 387:    */   {
/* 388:592 */     return this.cuentaBancariaOrganizacion;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 392:    */   {
/* 393:596 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public BigDecimal getTotalLiquidado()
/* 397:    */   {
/* 398:600 */     return this.totalLiquidado;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/* 402:    */   {
/* 403:604 */     this.totalLiquidado = totalLiquidado;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public Caja getCaja()
/* 407:    */   {
/* 408:608 */     return this.caja;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void setCaja(Caja caja)
/* 412:    */   {
/* 413:612 */     this.caja = caja;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public String getCodigoMovil()
/* 417:    */   {
/* 418:616 */     return this.codigoMovil;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void setCodigoMovil(String codigoMovil)
/* 422:    */   {
/* 423:620 */     this.codigoMovil = codigoMovil;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public AnticipoCliente getAnticipoCliente()
/* 427:    */   {
/* 428:624 */     return this.anticipoCliente;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/* 432:    */   {
/* 433:628 */     this.anticipoCliente = anticipoCliente;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public boolean isIndicadorExcede()
/* 437:    */   {
/* 438:632 */     return this.indicadorExcede;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setIndicadorExcede(boolean indicadorExcede)
/* 442:    */   {
/* 443:636 */     this.indicadorExcede = indicadorExcede;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public String getArchivo()
/* 447:    */   {
/* 448:640 */     return this.archivo;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void setArchivo(String archivo)
/* 452:    */   {
/* 453:644 */     this.archivo = archivo;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public String getNumeroFactura()
/* 457:    */   {
/* 458:648 */     return this.numeroFactura;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public void setNumeroFactura(String numeroFactura)
/* 462:    */   {
/* 463:652 */     this.numeroFactura = numeroFactura;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public String getDescripcion2()
/* 467:    */   {
/* 468:656 */     return this.descripcion2;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setDescripcion2(String descripcion2)
/* 472:    */   {
/* 473:660 */     this.descripcion2 = descripcion2;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public Integer getIdDispositivoSincronizacion()
/* 477:    */   {
/* 478:664 */     return this.idDispositivoSincronizacion;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 482:    */   {
/* 483:668 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public String getDocumentoReferencia()
/* 487:    */   {
/* 488:672 */     return this.documentoReferencia;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 492:    */   {
/* 493:676 */     this.documentoReferencia = documentoReferencia;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public FormaPago getFormaPago()
/* 497:    */   {
/* 498:680 */     return this.formaPago;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void setFormaPago(FormaPago formaPago)
/* 502:    */   {
/* 503:684 */     this.formaPago = formaPago;
/* 504:    */   }
/* 505:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Cobro
 * JD-Core Version:    0.7.0.1
 */