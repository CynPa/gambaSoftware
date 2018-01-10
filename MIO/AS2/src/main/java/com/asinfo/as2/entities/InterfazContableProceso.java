/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.aerolineas.CargaArchivo;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
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
/*  28:    */ import javax.validation.constraints.Min;
/*  29:    */ import javax.validation.constraints.NotNull;
/*  30:    */ import javax.validation.constraints.Size;
/*  31:    */ 
/*  32:    */ @Entity
/*  33:    */ @Table(name="interfaz_contable_proceso")
/*  34:    */ public class InterfazContableProceso
/*  35:    */   extends EntidadBase
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @Id
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="interfaz_contable_proceso")
/*  40:    */   @TableGenerator(name="interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  41:    */   @Column(name="id_interfaz_contable_proceso")
/*  42:    */   private int idInterfazContableProceso;
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   private int idOrganizacion;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  47:    */   private Sucursal sucursal;
/*  48:    */   @Column(name="numero", nullable=false, length=20, insertable=true, updatable=false)
/*  49:    */   @NotNull
/*  50:    */   @Size(max=20)
/*  51: 53 */   private String numero = "";
/*  52:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  53:    */   @Digits(integer=12, fraction=2)
/*  54: 58 */   private BigDecimal valor = BigDecimal.ZERO;
/*  55:    */   @Column(name="documento_base", nullable=false)
/*  56:    */   @Enumerated(EnumType.ORDINAL)
/*  57:    */   private DocumentoBase documentoBase;
/*  58:    */   @OneToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  60:    */   private Asiento asiento;
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha_desde", nullable=false)
/*  63:    */   private Date fechaDesde;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha_hasta", nullable=false)
/*  66:    */   private Date fechaHasta;
/*  67:    */   @Temporal(TemporalType.DATE)
/*  68:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  69:    */   private Date fechaContabilizacion;
/*  70:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  71: 82 */   private List<FacturaCliente> listaFacturaCliente = new ArrayList();
/*  72:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  73: 85 */   private List<DetalleCierreCaja> listaDetalleCierreCaja = new ArrayList();
/*  74:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  75: 88 */   private List<MovimientoInventario> listaMovimientoInventario = new ArrayList();
/*  76:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  77: 91 */   private List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta = new ArrayList();
/*  78:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  79: 94 */   private List<DespachoCliente> listaDespachoCliente = new ArrayList();
/*  80:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  81: 97 */   private List<ExtractoBancario> listaExtractoBancario = new ArrayList();
/*  82:    */   @OneToMany(mappedBy="interfazContableProceso", fetch=FetchType.LAZY)
/*  83:100 */   private List<CargaArchivo> listaCargaArchivo = new ArrayList();
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  86:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_documento", nullable=true)
/*  89:    */   private Documento documento;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_forma_pago", nullable=true)
/*  92:    */   private FormaPago formaPago;
/*  93:    */   @Column(name="estado", nullable=false)
/*  94:    */   @Enumerated(EnumType.ORDINAL)
/*  95:    */   private Estado estado;
/*  96:    */   @Column(name="observacion", nullable=true)
/*  97:    */   private String observacion;
/*  98:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  99:    */   @Size(max=20)
/* 100:122 */   private String documentoReferencia = "";
/* 101:    */   @Column(name="indicador_agrupa_movimientos", nullable=true)
/* 102:    */   private Boolean indicadorAgrupaMovimientos;
/* 103:    */   @Transient
/* 104:129 */   private boolean indicadorAgrupadoPorCuenta = true;
/* 105:    */   @Transient
/* 106:    */   private Documento filtroDocumento;
/* 107:    */   @Transient
/* 108:    */   private DocumentoBase filtroDocumentoBase;
/* 109:    */   @Transient
/* 110:    */   private Secuencia secuencia;
/* 111:    */   @Transient
/* 112:141 */   private List<List<Integer>> listaProcesosContabilizados = new ArrayList();
/* 113:    */   @Transient
/* 114:144 */   private List<Integer> listaCuentaContable = new ArrayList();
/* 115:    */   @Transient
/* 116:147 */   private boolean contabilizacionAutomatica = false;
/* 117:    */   @Transient
/* 118:    */   @Min(0L)
/* 119:    */   private BigDecimal descuentoImpuesto;
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:159 */     return this.idInterfazContableProceso;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getIdInterfazContableProceso()
/* 127:    */   {
/* 128:163 */     return this.idInterfazContableProceso;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdInterfazContableProceso(int idInterfazContableProceso)
/* 132:    */   {
/* 133:167 */     this.idInterfazContableProceso = idInterfazContableProceso;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getObservacion()
/* 137:    */   {
/* 138:171 */     return this.observacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setObservacion(String observacion)
/* 142:    */   {
/* 143:175 */     this.observacion = observacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Asiento getAsiento()
/* 147:    */   {
/* 148:179 */     return this.asiento;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setAsiento(Asiento asiento)
/* 152:    */   {
/* 153:183 */     this.asiento = asiento;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Estado getEstado()
/* 157:    */   {
/* 158:187 */     return this.estado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setEstado(Estado estado)
/* 162:    */   {
/* 163:191 */     this.estado = estado;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public DocumentoBase getDocumentoBase()
/* 167:    */   {
/* 168:200 */     return this.documentoBase;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 172:    */   {
/* 173:210 */     this.documentoBase = documentoBase;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Date getFechaDesde()
/* 177:    */   {
/* 178:219 */     return this.fechaDesde;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setFechaDesde(Date fechaDesde)
/* 182:    */   {
/* 183:229 */     this.fechaDesde = fechaDesde;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Date getFechaHasta()
/* 187:    */   {
/* 188:238 */     return this.fechaHasta;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setFechaHasta(Date fechaHasta)
/* 192:    */   {
/* 193:248 */     this.fechaHasta = fechaHasta;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Date getFechaContabilizacion()
/* 197:    */   {
/* 198:257 */     return this.fechaContabilizacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 202:    */   {
/* 203:267 */     this.fechaContabilizacion = fechaContabilizacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean isIndicadorAgrupadoPorCuenta()
/* 207:    */   {
/* 208:276 */     return this.indicadorAgrupadoPorCuenta;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setIndicadorAgrupadoPorCuenta(boolean indicadorAgrupadoPorCuenta)
/* 212:    */   {
/* 213:286 */     this.indicadorAgrupadoPorCuenta = indicadorAgrupadoPorCuenta;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getDocumentoReferencia()
/* 217:    */   {
/* 218:295 */     return this.documentoReferencia;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 222:    */   {
/* 223:305 */     this.documentoReferencia = documentoReferencia;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<DetalleCierreCaja> getListaDetalleCierreCaja()
/* 227:    */   {
/* 228:314 */     return this.listaDetalleCierreCaja;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaDetalleCierreCaja(List<DetalleCierreCaja> listaDetalleCierreCaja)
/* 232:    */   {
/* 233:324 */     this.listaDetalleCierreCaja = listaDetalleCierreCaja;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public int getIdOrganizacion()
/* 237:    */   {
/* 238:333 */     return this.idOrganizacion;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setIdOrganizacion(int idOrganizacion)
/* 242:    */   {
/* 243:343 */     this.idOrganizacion = idOrganizacion;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<MovimientoInventario> getListaMovimientoInventario()
/* 247:    */   {
/* 248:352 */     return this.listaMovimientoInventario;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaMovimientoInventario(List<MovimientoInventario> listaMovimientoInventario)
/* 252:    */   {
/* 253:362 */     this.listaMovimientoInventario = listaMovimientoInventario;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 257:    */   {
/* 258:371 */     return this.cuentaBancariaOrganizacion;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 262:    */   {
/* 263:381 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Documento getDocumento()
/* 267:    */   {
/* 268:390 */     return this.documento;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setDocumento(Documento documento)
/* 272:    */   {
/* 273:400 */     this.documento = documento;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public FormaPago getFormaPago()
/* 277:    */   {
/* 278:409 */     return this.formaPago;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setFormaPago(FormaPago formaPago)
/* 282:    */   {
/* 283:419 */     this.formaPago = formaPago;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public List<DespachoCliente> getListaDespachoCliente()
/* 287:    */   {
/* 288:428 */     return this.listaDespachoCliente;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setListaDespachoCliente(List<DespachoCliente> listaDespachoCliente)
/* 292:    */   {
/* 293:438 */     this.listaDespachoCliente = listaDespachoCliente;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public List<FacturaCliente> getListaFacturaCliente()
/* 297:    */   {
/* 298:447 */     return this.listaFacturaCliente;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setListaFacturaCliente(List<FacturaCliente> listaFacturaCliente)
/* 302:    */   {
/* 303:457 */     this.listaFacturaCliente = listaFacturaCliente;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Documento getFiltroDocumento()
/* 307:    */   {
/* 308:466 */     return this.filtroDocumento;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setFiltroDocumento(Documento filtroDocumento)
/* 312:    */   {
/* 313:476 */     this.filtroDocumento = filtroDocumento;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public DocumentoBase getFiltroDocumentoBase()
/* 317:    */   {
/* 318:485 */     return this.filtroDocumentoBase;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setFiltroDocumentoBase(DocumentoBase filtroDocumentoBase)
/* 322:    */   {
/* 323:495 */     this.filtroDocumentoBase = filtroDocumentoBase;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Secuencia getSecuencia()
/* 327:    */   {
/* 328:504 */     return this.secuencia;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setSecuencia(Secuencia secuencia)
/* 332:    */   {
/* 333:514 */     this.secuencia = secuencia;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public String getNumero()
/* 337:    */   {
/* 338:523 */     return this.numero;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void setNumero(String numero)
/* 342:    */   {
/* 343:533 */     this.numero = numero;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public BigDecimal getValor()
/* 347:    */   {
/* 348:542 */     return this.valor;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setValor(BigDecimal valor)
/* 352:    */   {
/* 353:552 */     this.valor = valor;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<List<Integer>> getListaProcesosContabilizados()
/* 357:    */   {
/* 358:556 */     return this.listaProcesosContabilizados;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setListaProcesosContabilizados(List<List<Integer>> listaProcesosContabilizados)
/* 362:    */   {
/* 363:560 */     this.listaProcesosContabilizados = listaProcesosContabilizados;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<Integer> getListaCuentaContable()
/* 367:    */   {
/* 368:564 */     return this.listaCuentaContable;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void setListaCuentaContable(List<Integer> listaCuentaContable)
/* 372:    */   {
/* 373:568 */     this.listaCuentaContable = listaCuentaContable;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public Sucursal getSucursal()
/* 377:    */   {
/* 378:572 */     return this.sucursal;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setSucursal(Sucursal sucursal)
/* 382:    */   {
/* 383:576 */     this.sucursal = sucursal;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public boolean isIndicadorAgrupaMovimientos()
/* 387:    */   {
/* 388:580 */     if (this.indicadorAgrupaMovimientos == null) {
/* 389:581 */       this.indicadorAgrupaMovimientos = Boolean.valueOf(false);
/* 390:    */     }
/* 391:583 */     return this.indicadorAgrupaMovimientos.booleanValue();
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setIndicadorAgrupaMovimientos(boolean indicadorAgrupaMovimientos)
/* 395:    */   {
/* 396:587 */     this.indicadorAgrupaMovimientos = Boolean.valueOf(indicadorAgrupaMovimientos);
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List<ExtractoBancario> getListaExtractoBancario()
/* 400:    */   {
/* 401:591 */     return this.listaExtractoBancario;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setListaExtractoBancario(List<ExtractoBancario> listaExtractoBancario)
/* 405:    */   {
/* 406:595 */     this.listaExtractoBancario = listaExtractoBancario;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public List<CargaArchivo> getListaCargaArchivo()
/* 410:    */   {
/* 411:599 */     return this.listaCargaArchivo;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setListaCargaArchivo(List<CargaArchivo> listaCargaArchivo)
/* 415:    */   {
/* 416:603 */     this.listaCargaArchivo = listaCargaArchivo;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public boolean isContabilizacionAutomatica()
/* 420:    */   {
/* 421:607 */     return this.contabilizacionAutomatica;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setContabilizacionAutomatica(boolean contabilizacionAutomatica)
/* 425:    */   {
/* 426:611 */     this.contabilizacionAutomatica = contabilizacionAutomatica;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public BigDecimal getDescuentoImpuesto()
/* 430:    */   {
/* 431:615 */     return this.descuentoImpuesto;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/* 435:    */   {
/* 436:619 */     this.descuentoImpuesto = descuentoImpuesto;
/* 437:    */   }
/* 438:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.InterfazContableProceso
 * JD-Core Version:    0.7.0.1
 */