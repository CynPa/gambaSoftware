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
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="anticipo_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})}, indexes={@javax.persistence.Index(columnList="id_factura_cliente")})
/*  31:    */ public class AnticipoCliente
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="anticipo_cliente", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="anticipo_cliente")
/*  38:    */   @Column(name="id_anticipo_cliente")
/*  39:    */   private int idAnticipoCliente;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  44:    */   private Sucursal sucursal;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private Empresa empresa;
/*  49:    */   @Enumerated(EnumType.ORDINAL)
/*  50:    */   @Column(name="estado", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Estado estado;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_documento", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Documento documento;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_forma_pago", nullable=true)
/*  59:    */   private FormaPago formaPago;
/*  60:    */   @OneToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  62:    */   private Asiento asiento;
/*  63:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  64:    */   private Date fechaContabilizacion;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  67:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  68:    */   @Column(name="numero", nullable=false, length=20)
/*  69:    */   private String numero;
/*  70:    */   @NotNull
/*  71:    */   @Temporal(TemporalType.DATE)
/*  72:    */   @Column(name="fecha", nullable=false)
/*  73:    */   private Date fecha;
/*  74:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  75:    */   @Min(0L)
/*  76:    */   private BigDecimal valor;
/*  77:    */   @Column(name="saldo", precision=12, scale=2, nullable=false)
/*  78:    */   private BigDecimal saldo;
/*  79:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  80:    */   @Size(max=20)
/*  81:114 */   private String documentoReferencia = "";
/*  82:    */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  83:    */   private boolean indicadorSaldoInicial;
/*  84:    */   @Column(name="descripcion", length=200, nullable=true)
/*  85:    */   @Size(max=200)
/*  86:    */   private String descripcion;
/*  87:    */   @OneToMany(mappedBy="anticipoCliente", fetch=FetchType.LAZY)
/*  88:125 */   private List<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente = new ArrayList();
/*  89:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  90:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion_devolucion", nullable=true)
/*  91:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacionDevolucion;
/*  92:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  93:    */   @JoinColumn(name="id_documento_devolucion", nullable=true)
/*  94:    */   private Documento documentoDevolucion;
/*  95:    */   @Column(name="fecha_devolucion", nullable=true)
/*  96:    */   private Date fechaDevolucion;
/*  97:    */   @Column(name="valor_devolucion", precision=12, scale=2, nullable=true)
/*  98:140 */   private BigDecimal valorDevolucion = BigDecimal.ZERO;
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_forma_pago_devolucion", nullable=true)
/* 101:    */   private FormaPago formaPagoDevolucion;
/* 102:    */   @OneToOne(fetch=FetchType.LAZY)
/* 103:    */   @JoinColumn(name="id_asiento_devolucion", nullable=true)
/* 104:    */   private Asiento asientoDevolucion;
/* 105:    */   @Column(name="documento_referencia_devolucion", nullable=true, length=20)
/* 106:    */   @Size(max=20)
/* 107:151 */   private String documentoReferenciaDevolucion = "";
/* 108:    */   @Column(name="beneficiario_devolucion", nullable=true)
/* 109:    */   @Size(max=50)
/* 110:155 */   private String beneficiarioDevolucion = "";
/* 111:    */   @Column(name="descripcion_devolucion", nullable=true, length=20)
/* 112:    */   @Size(max=20)
/* 113:    */   private String descripcionDevolucion;
/* 114:    */   @OneToOne(fetch=FetchType.LAZY)
/* 115:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/* 116:    */   private FacturaCliente notaCreditoCliente;
/* 117:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 118:    */   @JoinColumn(name="id_caja", nullable=true)
/* 119:    */   private Caja caja;
/* 120:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 121:    */   @JoinColumn(name="id_cobro", nullable=true)
/* 122:    */   private Cobro cobro;
/* 123:    */   @Column(name="indicador_contabilizar", nullable=true)
/* 124:    */   private Boolean indicadorContabilizar;
/* 125:    */   @Column(name="archivo", nullable=true)
/* 126:    */   @Size(max=50)
/* 127:    */   private String archivo;
/* 128:    */   @Transient
/* 129:    */   private CuentaContable cuentaContableDevolucionCruce;
/* 130:    */   @Transient
/* 131:    */   private Secuencia secuencia;
/* 132:    */   
/* 133:    */   public int getId()
/* 134:    */   {
/* 135:193 */     return this.idAnticipoCliente;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<String> getCamposAuditables()
/* 139:    */   {
/* 140:201 */     ArrayList<String> lista = new ArrayList();
/* 141:202 */     lista.add("empresa");
/* 142:203 */     lista.add("numero");
/* 143:204 */     lista.add("fecha");
/* 144:205 */     lista.add("valor");
/* 145:206 */     lista.add("saldo");
/* 146:207 */     lista.add("estado");
/* 147:208 */     lista.add("documento");
/* 148:209 */     lista.add("formaPago");
/* 149:210 */     lista.add("documentoReferencia");
/* 150:    */     
/* 151:212 */     return lista;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getIdAnticipoCliente()
/* 155:    */   {
/* 156:216 */     return this.idAnticipoCliente;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setIdAnticipoCliente(int idAnticipoCliente)
/* 160:    */   {
/* 161:220 */     this.idAnticipoCliente = idAnticipoCliente;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int getIdOrganizacion()
/* 165:    */   {
/* 166:229 */     return this.idOrganizacion;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setIdOrganizacion(int idOrganizacion)
/* 170:    */   {
/* 171:239 */     this.idOrganizacion = idOrganizacion;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Sucursal getSucursal()
/* 175:    */   {
/* 176:246 */     return this.sucursal;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setSucursal(Sucursal sucursal)
/* 180:    */   {
/* 181:254 */     this.sucursal = sucursal;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Documento getDocumento()
/* 185:    */   {
/* 186:263 */     return this.documento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setDocumento(Documento documento)
/* 190:    */   {
/* 191:273 */     this.documento = documento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public FormaPago getFormaPago()
/* 195:    */   {
/* 196:282 */     return this.formaPago;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setFormaPago(FormaPago formaPago)
/* 200:    */   {
/* 201:292 */     this.formaPago = formaPago;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 205:    */   {
/* 206:301 */     return this.cuentaBancariaOrganizacion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 210:    */   {
/* 211:311 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getNumero()
/* 215:    */   {
/* 216:320 */     return this.numero;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setNumero(String numero)
/* 220:    */   {
/* 221:330 */     this.numero = numero;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Date getFecha()
/* 225:    */   {
/* 226:339 */     return this.fecha;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setFecha(Date fecha)
/* 230:    */   {
/* 231:349 */     this.fecha = fecha;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public BigDecimal getValor()
/* 235:    */   {
/* 236:358 */     return this.valor;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setValor(BigDecimal valor)
/* 240:    */   {
/* 241:368 */     this.valor = valor;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Empresa getEmpresa()
/* 245:    */   {
/* 246:377 */     return this.empresa;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setEmpresa(Empresa empresa)
/* 250:    */   {
/* 251:387 */     this.empresa = empresa;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Date getFechaContabilizacion()
/* 255:    */   {
/* 256:391 */     return this.fechaContabilizacion;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 260:    */   {
/* 261:395 */     this.fechaContabilizacion = fechaContabilizacion;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public BigDecimal getSaldo()
/* 265:    */   {
/* 266:399 */     return this.saldo;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setSaldo(BigDecimal saldo)
/* 270:    */   {
/* 271:403 */     this.saldo = saldo;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<LiquidacionAnticipoCliente> getListaLiquidacionAnticipoCliente()
/* 275:    */   {
/* 276:407 */     return this.listaLiquidacionAnticipoCliente;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setListaLiquidacionAnticipoCliente(List<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente)
/* 280:    */   {
/* 281:411 */     this.listaLiquidacionAnticipoCliente = listaLiquidacionAnticipoCliente;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public Asiento getAsiento()
/* 285:    */   {
/* 286:415 */     return this.asiento;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setAsiento(Asiento asiento)
/* 290:    */   {
/* 291:419 */     this.asiento = asiento;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String getDescripcion()
/* 295:    */   {
/* 296:428 */     return this.descripcion;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setDescripcion(String descripcion)
/* 300:    */   {
/* 301:438 */     this.descripcion = descripcion;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public Estado getEstado()
/* 305:    */   {
/* 306:447 */     return this.estado;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setEstado(Estado estado)
/* 310:    */   {
/* 311:457 */     this.estado = estado;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public String toString()
/* 315:    */   {
/* 316:462 */     return this.numero;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String getDocumentoReferencia()
/* 320:    */   {
/* 321:466 */     return this.documentoReferencia;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 325:    */   {
/* 326:470 */     this.documentoReferencia = documentoReferencia;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public boolean isIndicadorSaldoInicial()
/* 330:    */   {
/* 331:474 */     return this.indicadorSaldoInicial;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/* 335:    */   {
/* 336:478 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacionDevolucion()
/* 340:    */   {
/* 341:487 */     return this.cuentaBancariaOrganizacionDevolucion;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setCuentaBancariaOrganizacionDevolucion(CuentaBancariaOrganizacion cuentaBancariaOrganizacionDevolucion)
/* 345:    */   {
/* 346:497 */     this.cuentaBancariaOrganizacionDevolucion = cuentaBancariaOrganizacionDevolucion;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public Documento getDocumentoDevolucion()
/* 350:    */   {
/* 351:506 */     return this.documentoDevolucion;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setDocumentoDevolucion(Documento documentoDevolucion)
/* 355:    */   {
/* 356:516 */     this.documentoDevolucion = documentoDevolucion;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public Date getFechaDevolucion()
/* 360:    */   {
/* 361:525 */     return this.fechaDevolucion;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setFechaDevolucion(Date fechaDevolucion)
/* 365:    */   {
/* 366:535 */     this.fechaDevolucion = fechaDevolucion;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public BigDecimal getValorDevolucion()
/* 370:    */   {
/* 371:544 */     return this.valorDevolucion;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setValorDevolucion(BigDecimal valorDevolucion)
/* 375:    */   {
/* 376:554 */     this.valorDevolucion = valorDevolucion;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public FormaPago getFormaPagoDevolucion()
/* 380:    */   {
/* 381:563 */     return this.formaPagoDevolucion;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setFormaPagoDevolucion(FormaPago formaPagoDevolucion)
/* 385:    */   {
/* 386:573 */     this.formaPagoDevolucion = formaPagoDevolucion;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public Asiento getAsientoDevolucion()
/* 390:    */   {
/* 391:582 */     return this.asientoDevolucion;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setAsientoDevolucion(Asiento asientoDevolucion)
/* 395:    */   {
/* 396:592 */     this.asientoDevolucion = asientoDevolucion;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public String getDocumentoReferenciaDevolucion()
/* 400:    */   {
/* 401:601 */     return this.documentoReferenciaDevolucion;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setDocumentoReferenciaDevolucion(String documentoReferenciaDevolucion)
/* 405:    */   {
/* 406:611 */     this.documentoReferenciaDevolucion = documentoReferenciaDevolucion;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public String getBeneficiarioDevolucion()
/* 410:    */   {
/* 411:620 */     return this.beneficiarioDevolucion;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setBeneficiarioDevolucion(String beneficiarioDevolucion)
/* 415:    */   {
/* 416:630 */     this.beneficiarioDevolucion = beneficiarioDevolucion;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String getDescripcionDevolucion()
/* 420:    */   {
/* 421:639 */     return this.descripcionDevolucion;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setDescripcionDevolucion(String descripcionDevolucion)
/* 425:    */   {
/* 426:649 */     this.descripcionDevolucion = descripcionDevolucion;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public FacturaCliente getNotaCreditoCliente()
/* 430:    */   {
/* 431:658 */     return this.notaCreditoCliente;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setNotaCreditoCliente(FacturaCliente notaCreditoCliente)
/* 435:    */   {
/* 436:668 */     this.notaCreditoCliente = notaCreditoCliente;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public Secuencia getSecuencia()
/* 440:    */   {
/* 441:677 */     return this.secuencia;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setSecuencia(Secuencia secuencia)
/* 445:    */   {
/* 446:687 */     this.secuencia = secuencia;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public Caja getCaja()
/* 450:    */   {
/* 451:696 */     return this.caja;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setCaja(Caja caja)
/* 455:    */   {
/* 456:706 */     this.caja = caja;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public CuentaContable getCuentaContableDevolucionCruce()
/* 460:    */   {
/* 461:715 */     return this.cuentaContableDevolucionCruce;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setCuentaContableDevolucionCruce(CuentaContable cuentaContableDevolucionCruce)
/* 465:    */   {
/* 466:725 */     this.cuentaContableDevolucionCruce = cuentaContableDevolucionCruce;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public Cobro getCobro()
/* 470:    */   {
/* 471:729 */     return this.cobro;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setCobro(Cobro cobro)
/* 475:    */   {
/* 476:733 */     this.cobro = cobro;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public Boolean getIndicadorContabilizar()
/* 480:    */   {
/* 481:737 */     return this.indicadorContabilizar;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setIndicadorContabilizar(Boolean indicadorContabilizar)
/* 485:    */   {
/* 486:741 */     this.indicadorContabilizar = indicadorContabilizar;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public String getArchivo()
/* 490:    */   {
/* 491:745 */     return this.archivo;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setArchivo(String archivo)
/* 495:    */   {
/* 496:749 */     this.archivo = archivo;
/* 497:    */   }
/* 498:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.AnticipoCliente
 * JD-Core Version:    0.7.0.1
 */