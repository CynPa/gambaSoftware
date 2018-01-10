/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.OneToMany;
/*  21:    */ import javax.persistence.OneToOne;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.persistence.Transient;
/*  27:    */ import javax.validation.constraints.Digits;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ import org.hibernate.annotations.ColumnDefault;
/*  31:    */ 
/*  32:    */ @Entity
/*  33:    */ @Table(name="movimiento_bancario", indexes={@javax.persistence.Index(columnList="id_detalle_asiento")})
/*  34:    */ public class MovimientoBancario
/*  35:    */   extends EntidadBase
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="movimiento_bancario", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_bancario")
/*  42:    */   @Column(name="id_movimiento_bancario", unique=true, nullable=false)
/*  43:    */   private int idMovimientoBancario;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_conciliacion_bancaria", nullable=true)
/*  46:    */   private ConciliacionBancaria conciliacionBancaria;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  51:    */   @OneToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_detalle_asiento", nullable=true)
/*  53:    */   private DetalleAsiento detalleAsiento;
/*  54:    */   @Column(name="id_organizacion", nullable=false)
/*  55:    */   private int idOrganizacion;
/*  56:    */   @Column(name="id_sucursal", nullable=false)
/*  57:    */   private int idSucursal;
/*  58:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  59:    */   @Size(max=20)
/*  60:    */   private String documentoReferencia;
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha", nullable=false)
/*  63:    */   @NotNull
/*  64:    */   private Date fecha;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_documento", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private Documento documento;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_concepto_contable", nullable=true)
/*  71:    */   private ConceptoContable conceptoContable;
/*  72:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  73:    */   @Digits(integer=12, fraction=2)
/*  74: 95 */   private BigDecimal valor = BigDecimal.ZERO;
/*  75:    */   @Column(name="descripcion", length=1000)
/*  76:    */   @Size(max=1000)
/*  77:    */   private String descripcion;
/*  78:    */   @Column(name="beneficiario", nullable=false)
/*  79:    */   @NotNull
/*  80:    */   @Size(max=100)
/*  81:    */   @ColumnDefault("''")
/*  82:103 */   private String beneficiario = "";
/*  83:    */   @Enumerated(EnumType.ORDINAL)
/*  84:    */   @Column(name="estado", nullable=false)
/*  85:    */   @NotNull
/*  86:    */   private Estado estado;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="estado_cheque")
/*  89:    */   private EstadoCheque estadoCheque;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  92:    */   private FormaPago formaPago;
/*  93:    */   @Temporal(TemporalType.DATE)
/*  94:    */   @Column(name="fecha_posfechado", nullable=true)
/*  95:    */   private Date fechaPosfechado;
/*  96:    */   @Column(name="nota_posfechado", nullable=true, length=200)
/*  97:    */   @Size(max=200)
/*  98:    */   private String notaPosfechado;
/*  99:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="movimientoBancario")
/* 100:130 */   private List<MovimientoBancarioEstadoCheque> listaMovimientoBancarioEstadoCheque = new ArrayList();
/* 101:    */   @Transient
/* 102:    */   private MovimientoBancarioEstadoCheque movEstadoChequeInicial;
/* 103:    */   @Transient
/* 104:    */   private String traNumeroAsiento;
/* 105:    */   @Transient
/* 106:    */   private String traTipoAsiento;
/* 107:    */   @Transient
/* 108:    */   private boolean conciliado;
/* 109:    */   @Transient
/* 110:    */   private Documento traDocumento;
/* 111:    */   @Transient
/* 112:    */   private String traConceptoContable;
/* 113:    */   @Transient
/* 114:    */   private boolean edicion;
/* 115:    */   @Transient
/* 116:    */   private Secuencia secuencia;
/* 117:    */   
/* 118:    */   public MovimientoBancario() {}
/* 119:    */   
/* 120:    */   public MovimientoBancario(String documentoReferencia, BigDecimal valor)
/* 121:    */   {
/* 122:170 */     this.documentoReferencia = documentoReferencia;
/* 123:171 */     this.valor = valor;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public MovimientoBancario(int idMovimientoBancario, String documentoReferencia, String descripcion, Estado estado, String traNumeroAsiento, String traTipoAsiento, Date fecha, Documento traDocumento, boolean conciliado, BigDecimal valor)
/* 127:    */   {
/* 128:190 */     this.idMovimientoBancario = idMovimientoBancario;
/* 129:191 */     this.documentoReferencia = documentoReferencia;
/* 130:192 */     this.descripcion = descripcion;
/* 131:193 */     this.estado = estado;
/* 132:194 */     this.traNumeroAsiento = traNumeroAsiento;
/* 133:195 */     this.traTipoAsiento = traTipoAsiento;
/* 134:196 */     this.fecha = fecha;
/* 135:197 */     this.traDocumento = traDocumento;
/* 136:198 */     this.conciliado = conciliado;
/* 137:199 */     this.valor = valor;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getIdMovimientoBancario()
/* 141:    */   {
/* 142:203 */     return this.idMovimientoBancario;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setIdMovimientoBancario(int idMovimientoBancario)
/* 146:    */   {
/* 147:207 */     this.idMovimientoBancario = idMovimientoBancario;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public ConciliacionBancaria getConciliacionBancaria()
/* 151:    */   {
/* 152:211 */     return this.conciliacionBancaria;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setConciliacionBancaria(ConciliacionBancaria conciliacionBancaria)
/* 156:    */   {
/* 157:215 */     this.conciliacionBancaria = conciliacionBancaria;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 161:    */   {
/* 162:219 */     if (this.cuentaBancariaOrganizacion == null) {
/* 163:220 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 164:    */     }
/* 165:222 */     return this.cuentaBancariaOrganizacion;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 169:    */   {
/* 170:226 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public DetalleAsiento getDetalleAsiento()
/* 174:    */   {
/* 175:230 */     return this.detalleAsiento;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setDetalleAsiento(DetalleAsiento detalleAsiento)
/* 179:    */   {
/* 180:234 */     this.detalleAsiento = detalleAsiento;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int getIdOrganizacion()
/* 184:    */   {
/* 185:238 */     return this.idOrganizacion;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setIdOrganizacion(int idOrganizacion)
/* 189:    */   {
/* 190:242 */     this.idOrganizacion = idOrganizacion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int getIdSucursal()
/* 194:    */   {
/* 195:246 */     return this.idSucursal;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setIdSucursal(int idSucursal)
/* 199:    */   {
/* 200:250 */     this.idSucursal = idSucursal;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getDescripcion()
/* 204:    */   {
/* 205:254 */     return this.descripcion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setDescripcion(String descripcion)
/* 209:    */   {
/* 210:258 */     this.descripcion = descripcion;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public int getId()
/* 214:    */   {
/* 215:268 */     return getIdMovimientoBancario();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isConciliado()
/* 219:    */   {
/* 220:277 */     return this.conciliado;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setConciliado(boolean conciliado)
/* 224:    */   {
/* 225:287 */     this.conciliado = conciliado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getDocumentoReferencia()
/* 229:    */   {
/* 230:291 */     return this.documentoReferencia;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 234:    */   {
/* 235:295 */     this.documentoReferencia = documentoReferencia;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getTraNumeroAsiento()
/* 239:    */   {
/* 240:299 */     return this.traNumeroAsiento;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setTraNumeroAsiento(String traNumeroAsiento)
/* 244:    */   {
/* 245:303 */     this.traNumeroAsiento = traNumeroAsiento;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getTraTipoAsiento()
/* 249:    */   {
/* 250:307 */     return this.traTipoAsiento;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setTraTipoAsiento(String traTipoAsiento)
/* 254:    */   {
/* 255:311 */     this.traTipoAsiento = traTipoAsiento;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public BigDecimal getValor()
/* 259:    */   {
/* 260:315 */     return this.valor;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setValor(BigDecimal valor)
/* 264:    */   {
/* 265:319 */     this.valor = valor;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Date getFecha()
/* 269:    */   {
/* 270:323 */     return this.fecha;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setFecha(Date fecha)
/* 274:    */   {
/* 275:327 */     this.fecha = fecha;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public ConceptoContable getConceptoContable()
/* 279:    */   {
/* 280:331 */     return this.conceptoContable;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setConceptoContable(ConceptoContable conceptoContable)
/* 284:    */   {
/* 285:335 */     this.conceptoContable = conceptoContable;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Documento getDocumento()
/* 289:    */   {
/* 290:339 */     return this.documento;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setDocumento(Documento documento)
/* 294:    */   {
/* 295:343 */     this.documento = documento;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Estado getEstado()
/* 299:    */   {
/* 300:347 */     return this.estado;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setEstado(Estado estado)
/* 304:    */   {
/* 305:351 */     this.estado = estado;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public boolean isEdicion()
/* 309:    */   {
/* 310:355 */     return this.edicion;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setEdicion(boolean edicion)
/* 314:    */   {
/* 315:359 */     this.edicion = edicion;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public BigDecimal getValorCobrado()
/* 319:    */   {
/* 320:363 */     return this.valor.compareTo(BigDecimal.ZERO) > 0 ? this.valor : BigDecimal.ZERO;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public BigDecimal getValorPagado()
/* 324:    */   {
/* 325:367 */     return this.valor.compareTo(BigDecimal.ZERO) < 0 ? this.valor.negate() : BigDecimal.ZERO;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setValorCobrado(BigDecimal valorCobrado)
/* 329:    */   {
/* 330:371 */     if (valorCobrado.compareTo(BigDecimal.ZERO) >= 0) {
/* 331:372 */       this.valor = valorCobrado;
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setValorPagado(BigDecimal valorPagado)
/* 336:    */   {
/* 337:377 */     if (valorPagado.compareTo(BigDecimal.ZERO) >= 0) {
/* 338:378 */       this.valor = valorPagado.negate();
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   public Documento getTraDocumento()
/* 343:    */   {
/* 344:388 */     return this.traDocumento;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setTraDocumento(Documento traDocumento)
/* 348:    */   {
/* 349:398 */     this.traDocumento = traDocumento;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public String getTraConceptoContable()
/* 353:    */   {
/* 354:407 */     return this.traConceptoContable;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setTraConceptoContable(String traConceptoContable)
/* 358:    */   {
/* 359:417 */     this.traConceptoContable = traConceptoContable;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public String getBeneficiario()
/* 363:    */   {
/* 364:421 */     if (this.beneficiario == null) {
/* 365:422 */       this.beneficiario = "";
/* 366:    */     }
/* 367:424 */     return this.beneficiario;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setBeneficiario(String beneficiario)
/* 371:    */   {
/* 372:428 */     this.beneficiario = beneficiario;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public boolean getEsEditable()
/* 376:    */   {
/* 377:432 */     boolean resultado = true;
/* 378:433 */     if ((getEstado() != Estado.APROBADO) && (getEstado() != Estado.ANULADO) && (
/* 379:434 */       (getDocumento().getDocumentoBase() == DocumentoBase.CONCEPTOS_CONTABLES) || (getDocumento().getDocumentoBase() == DocumentoBase.GASTOS_BANCARIOS))) {
/* 380:435 */       resultado = false;
/* 381:    */     }
/* 382:437 */     return resultado;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public FormaPago getFormaPago()
/* 386:    */   {
/* 387:446 */     return this.formaPago;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setFormaPago(FormaPago formaPago)
/* 391:    */   {
/* 392:456 */     this.formaPago = formaPago;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public Secuencia getSecuencia()
/* 396:    */   {
/* 397:460 */     return this.secuencia;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setSecuencia(Secuencia secuencia)
/* 401:    */   {
/* 402:464 */     this.secuencia = secuencia;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public Date getFechaPosfechado()
/* 406:    */   {
/* 407:471 */     return this.fechaPosfechado;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setFechaPosfechado(Date fechaPosfechado)
/* 411:    */   {
/* 412:479 */     this.fechaPosfechado = fechaPosfechado;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String getNotaPosfechado()
/* 416:    */   {
/* 417:486 */     return this.notaPosfechado;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setNotaPosfechado(String notaPosfechado)
/* 421:    */   {
/* 422:494 */     this.notaPosfechado = notaPosfechado;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public List<MovimientoBancarioEstadoCheque> getListaMovimientoBancarioEstadoCheque()
/* 426:    */   {
/* 427:501 */     return this.listaMovimientoBancarioEstadoCheque;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setListaMovimientoBancarioEstadoCheque(List<MovimientoBancarioEstadoCheque> listaMovimientoBancarioEstadoCheque)
/* 431:    */   {
/* 432:509 */     this.listaMovimientoBancarioEstadoCheque = listaMovimientoBancarioEstadoCheque;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public EstadoCheque getEstadoCheque()
/* 436:    */   {
/* 437:516 */     return this.estadoCheque;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setEstadoCheque(EstadoCheque estadoCheque)
/* 441:    */   {
/* 442:523 */     this.estadoCheque = estadoCheque;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public MovimientoBancarioEstadoCheque getMovEstadoChequeInicial()
/* 446:    */   {
/* 447:527 */     return this.movEstadoChequeInicial;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setMovEstadoChequeInicial(MovimientoBancarioEstadoCheque movEstadoChequeInicial)
/* 451:    */   {
/* 452:531 */     this.movEstadoChequeInicial = movEstadoChequeInicial;
/* 453:    */   }
/* 454:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MovimientoBancario
 * JD-Core Version:    0.7.0.1
 */