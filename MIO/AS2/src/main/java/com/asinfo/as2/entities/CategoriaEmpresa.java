/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.DecimalMin;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ import org.hibernate.annotations.ColumnDefault;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="categoria_empresa")
/*  30:    */ public class CategoriaEmpresa
/*  31:    */   extends EntidadBase
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="tipo_bodega", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_bodega")
/*  38:    */   @Column(name="id_categoria_empresa", unique=true, nullable=false)
/*  39:    */   private int idCategoriaEmpresa;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Column(name="id_sucursal", nullable=false)
/*  43:    */   private int idSucursal;
/*  44:    */   @Column(name="codigo", nullable=false, length=10)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=1, max=10)
/*  47:    */   private String codigo;
/*  48:    */   @Column(name="nombre", nullable=false, length=50)
/*  49:    */   @NotNull
/*  50:    */   @Size(min=1, max=50)
/*  51:    */   private String nombre;
/*  52:    */   @Column(name="credito_maximo", precision=12, scale=2)
/*  53: 62 */   private BigDecimal creditoMaximo = BigDecimal.ZERO;
/*  54:    */   @Column(name="descripcion", nullable=true, length=200)
/*  55:    */   @Size(max=200)
/*  56:    */   private String descripcion;
/*  57:    */   @Column(name="activo", nullable=false)
/*  58:    */   private boolean activo;
/*  59:    */   @Column(name="predeterminado", nullable=false)
/*  60:    */   private boolean predeterminado;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_cuenta_contable_cliente", nullable=true)
/*  63:    */   private CuentaContable cuentaContableCliente;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_cuenta_contable_proveedor", nullable=true)
/*  66:    */   private CuentaContable cuentaContableProveedor;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_cuenta_contable_anticipo_cliente", nullable=true)
/*  69:    */   private CuentaContable cuentaContableAnticipoCliente;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_cuenta_contable_anticipo_cliente_nota_credito", nullable=true)
/*  72:    */   private CuentaContable cuentaContableAnticipoClienteNotaCredito;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_cuenta_contable_anticipo_proveedor", nullable=true)
/*  75:    */   private CuentaContable cuentaContableAnticipoProveedor;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_cuenta_contable_anticipo_proveedor_nota_credito", nullable=true)
/*  78:    */   private CuentaContable cuentaContableAnticipoProveedorNotaCredito;
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_cuenta_contable_suedo_por_pagar", nullable=true)
/*  81:    */   private CuentaContable cuentaContableSueldoPorPagar;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_cuenta_contable_iva_presuntivo", nullable=true)
/*  84:    */   private CuentaContable cuentaContableIvaPresuntivo;
/*  85:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  86:    */   @JoinColumn(name="id_cuenta_contable_2x1000", nullable=true)
/*  87:    */   private CuentaContable cuentaContable2X1000;
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  89:    */   @JoinColumn(name="id_cuenta_contable_3x1000", nullable=true)
/*  90:    */   private CuentaContable cuentaContable3X1000;
/*  91:    */   @Column(name="porcentaje_maximo_morosidad", nullable=true, precision=12, scale=2)
/*  92:    */   @Digits(integer=12, fraction=2)
/*  93:    */   @DecimalMin("0.00")
/*  94:    */   private BigDecimal porcentajeMaximoMorosidad;
/*  95:    */   @Column(name="numero_maximo_documentos_sin_garantia", nullable=true)
/*  96:    */   @Min(0L)
/*  97:    */   private Integer numeroMaximoDocumentosSinGarantia;
/*  98:    */   @Column(name="verificar_documentos", nullable=false)
/*  99:    */   @NotNull
/* 100:    */   @ColumnDefault("'0'")
/* 101:    */   private boolean verificarDocumentos;
/* 102:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="categoriaEmpresa")
/* 103:129 */   private List<DocumentoDigitalizadoCategoriaEmpresa> listaDocumentoDigitalizadoCategoriaEmpresa = new ArrayList();
/* 104:    */   @Transient
/* 105:132 */   private Map<Integer, Proveedor> mapaProveedor = new HashMap();
/* 106:    */   @Transient
/* 107:135 */   private List<Proveedor> listaProveedorFiltrado = new ArrayList();
/* 108:    */   @Transient
/* 109:138 */   private BigDecimal valorPendientePago = BigDecimal.ZERO;
/* 110:    */   @Transient
/* 111:141 */   private BigDecimal valorSolicitadoPago = BigDecimal.ZERO;
/* 112:    */   @Transient
/* 113:144 */   private BigDecimal valorAprobadoPago = BigDecimal.ZERO;
/* 114:    */   
/* 115:    */   public CategoriaEmpresa() {}
/* 116:    */   
/* 117:    */   public CategoriaEmpresa(int idCategoriaEmpresa, String codigo, String nombre)
/* 118:    */   {
/* 119:157 */     this.idCategoriaEmpresa = idCategoriaEmpresa;
/* 120:158 */     this.codigo = codigo;
/* 121:159 */     this.nombre = nombre;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getId()
/* 125:    */   {
/* 126:164 */     return this.idCategoriaEmpresa;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getIdCategoriaEmpresa()
/* 130:    */   {
/* 131:173 */     return this.idCategoriaEmpresa;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIdCategoriaEmpresa(int idCategoriaEmpresa)
/* 135:    */   {
/* 136:183 */     this.idCategoriaEmpresa = idCategoriaEmpresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getIdOrganizacion()
/* 140:    */   {
/* 141:192 */     return this.idOrganizacion;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setIdOrganizacion(int idOrganizacion)
/* 145:    */   {
/* 146:202 */     this.idOrganizacion = idOrganizacion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getIdSucursal()
/* 150:    */   {
/* 151:211 */     return this.idSucursal;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setIdSucursal(int idSucursal)
/* 155:    */   {
/* 156:221 */     this.idSucursal = idSucursal;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getCodigo()
/* 160:    */   {
/* 161:230 */     return this.codigo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setCodigo(String codigo)
/* 165:    */   {
/* 166:240 */     this.codigo = codigo;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getNombre()
/* 170:    */   {
/* 171:249 */     return this.nombre;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setNombre(String nombre)
/* 175:    */   {
/* 176:259 */     this.nombre = nombre;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public BigDecimal getCreditoMaximo()
/* 180:    */   {
/* 181:266 */     return this.creditoMaximo == null ? BigDecimal.ZERO : this.creditoMaximo;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setCreditoMaximo(BigDecimal creditoMaximo)
/* 185:    */   {
/* 186:274 */     this.creditoMaximo = creditoMaximo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getDescripcion()
/* 190:    */   {
/* 191:283 */     return this.descripcion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setDescripcion(String descripcion)
/* 195:    */   {
/* 196:293 */     this.descripcion = descripcion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isActivo()
/* 200:    */   {
/* 201:302 */     return this.activo;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setActivo(boolean activo)
/* 205:    */   {
/* 206:312 */     this.activo = activo;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isPredeterminado()
/* 210:    */   {
/* 211:321 */     return this.predeterminado;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setPredeterminado(boolean predeterminado)
/* 215:    */   {
/* 216:331 */     this.predeterminado = predeterminado;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public CuentaContable getCuentaContableCliente()
/* 220:    */   {
/* 221:340 */     return this.cuentaContableCliente;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setCuentaContableCliente(CuentaContable cuentaContableCliente)
/* 225:    */   {
/* 226:350 */     this.cuentaContableCliente = cuentaContableCliente;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public CuentaContable getCuentaContableProveedor()
/* 230:    */   {
/* 231:359 */     return this.cuentaContableProveedor;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setCuentaContableProveedor(CuentaContable cuentaContableProveedor)
/* 235:    */   {
/* 236:369 */     this.cuentaContableProveedor = cuentaContableProveedor;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public CuentaContable getCuentaContableAnticipoCliente()
/* 240:    */   {
/* 241:378 */     return this.cuentaContableAnticipoCliente;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setCuentaContableAnticipoCliente(CuentaContable cuentaContableAnticipoCliente)
/* 245:    */   {
/* 246:388 */     this.cuentaContableAnticipoCliente = cuentaContableAnticipoCliente;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public CuentaContable getCuentaContableAnticipoProveedor()
/* 250:    */   {
/* 251:397 */     return this.cuentaContableAnticipoProveedor;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setCuentaContableAnticipoProveedor(CuentaContable cuentaContableAnticipoProveedor)
/* 255:    */   {
/* 256:407 */     this.cuentaContableAnticipoProveedor = cuentaContableAnticipoProveedor;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public CuentaContable getCuentaContableSueldoPorPagar()
/* 260:    */   {
/* 261:416 */     return this.cuentaContableSueldoPorPagar;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setCuentaContableSueldoPorPagar(CuentaContable cuentaContableSueldoPorPagar)
/* 265:    */   {
/* 266:426 */     this.cuentaContableSueldoPorPagar = cuentaContableSueldoPorPagar;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public CuentaContable getCuentaContableAnticipoClienteNotaCredito()
/* 270:    */   {
/* 271:435 */     return this.cuentaContableAnticipoClienteNotaCredito;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setCuentaContableAnticipoClienteNotaCredito(CuentaContable cuentaContableAnticipoClienteNotaCredito)
/* 275:    */   {
/* 276:445 */     this.cuentaContableAnticipoClienteNotaCredito = cuentaContableAnticipoClienteNotaCredito;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public CuentaContable getCuentaContableAnticipoProveedorNotaCredito()
/* 280:    */   {
/* 281:454 */     return this.cuentaContableAnticipoProveedorNotaCredito;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setCuentaContableAnticipoProveedorNotaCredito(CuentaContable cuentaContableAnticipoProveedorNotaCredito)
/* 285:    */   {
/* 286:464 */     this.cuentaContableAnticipoProveedorNotaCredito = cuentaContableAnticipoProveedorNotaCredito;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public CuentaContable getCuentaContableIvaPresuntivo()
/* 290:    */   {
/* 291:473 */     return this.cuentaContableIvaPresuntivo;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setCuentaContableIvaPresuntivo(CuentaContable cuentaContableIvaPresuntivo)
/* 295:    */   {
/* 296:483 */     this.cuentaContableIvaPresuntivo = cuentaContableIvaPresuntivo;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public CuentaContable getCuentaContable2X1000()
/* 300:    */   {
/* 301:492 */     return this.cuentaContable2X1000;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setCuentaContable2X1000(CuentaContable cuentaContable2X1000)
/* 305:    */   {
/* 306:502 */     this.cuentaContable2X1000 = cuentaContable2X1000;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public CuentaContable getCuentaContable3X1000()
/* 310:    */   {
/* 311:511 */     return this.cuentaContable3X1000;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setCuentaContable3X1000(CuentaContable cuentaContable3X1000)
/* 315:    */   {
/* 316:521 */     this.cuentaContable3X1000 = cuentaContable3X1000;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Map<Integer, Proveedor> getMapaProveedor()
/* 320:    */   {
/* 321:525 */     return this.mapaProveedor;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setMapaProveedor(Map<Integer, Proveedor> mapaProveedor)
/* 325:    */   {
/* 326:529 */     this.mapaProveedor = mapaProveedor;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public List<Proveedor> getListaProveedor()
/* 330:    */   {
/* 331:533 */     List<Proveedor> listaProveedor = new ArrayList();
/* 332:534 */     if (this.mapaProveedor != null) {
/* 333:535 */       listaProveedor.addAll(this.mapaProveedor.values());
/* 334:    */     }
/* 335:537 */     return listaProveedor;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public BigDecimal getValorPendientePago()
/* 339:    */   {
/* 340:541 */     if (this.valorPendientePago == null) {
/* 341:542 */       this.valorPendientePago = BigDecimal.ZERO;
/* 342:    */     }
/* 343:544 */     return this.valorPendientePago;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setValorPendientePago(BigDecimal valorPendientePago)
/* 347:    */   {
/* 348:548 */     this.valorPendientePago = valorPendientePago;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public BigDecimal getValorSolicitadoPago()
/* 352:    */   {
/* 353:552 */     if (this.valorSolicitadoPago == null) {
/* 354:553 */       this.valorSolicitadoPago = BigDecimal.ZERO;
/* 355:    */     }
/* 356:555 */     return this.valorSolicitadoPago;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setValorSolicitadoPago(BigDecimal valorSolicitadoPago)
/* 360:    */   {
/* 361:559 */     this.valorSolicitadoPago = valorSolicitadoPago;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public BigDecimal getValorAprobadoPago()
/* 365:    */   {
/* 366:563 */     if (this.valorAprobadoPago == null) {
/* 367:564 */       this.valorAprobadoPago = BigDecimal.ZERO;
/* 368:    */     }
/* 369:566 */     return this.valorAprobadoPago;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setValorAprobadoPago(BigDecimal valorAprobadoPago)
/* 373:    */   {
/* 374:570 */     this.valorAprobadoPago = valorAprobadoPago;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public BigDecimal getDiferenciaPendiente()
/* 378:    */   {
/* 379:574 */     return getValorPendientePago().subtract(getValorSolicitadoPago());
/* 380:    */   }
/* 381:    */   
/* 382:    */   public BigDecimal getDiferenciaAprobado()
/* 383:    */   {
/* 384:578 */     return getValorSolicitadoPago().subtract(getValorAprobadoPago());
/* 385:    */   }
/* 386:    */   
/* 387:    */   public List<Proveedor> getListaProveedorFiltrado()
/* 388:    */   {
/* 389:582 */     return this.listaProveedorFiltrado;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setListaProveedorFiltrado(List<Proveedor> listaProveedorFiltrado)
/* 393:    */   {
/* 394:586 */     this.listaProveedorFiltrado = listaProveedorFiltrado;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public List<DocumentoDigitalizadoCategoriaEmpresa> getListaDocumentoDigitalizadoCategoriaEmpresa()
/* 398:    */   {
/* 399:590 */     return this.listaDocumentoDigitalizadoCategoriaEmpresa;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setListaDocumentoDigitalizadoCategoriaEmpresa(List<DocumentoDigitalizadoCategoriaEmpresa> listaDocumentoDigitalizadoCategoriaEmpresa)
/* 403:    */   {
/* 404:594 */     this.listaDocumentoDigitalizadoCategoriaEmpresa = listaDocumentoDigitalizadoCategoriaEmpresa;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public BigDecimal getPorcentajeMaximoMorosidad()
/* 408:    */   {
/* 409:598 */     return this.porcentajeMaximoMorosidad;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setPorcentajeMaximoMorosidad(BigDecimal porcentajeMaximoMorosidad)
/* 413:    */   {
/* 414:602 */     this.porcentajeMaximoMorosidad = porcentajeMaximoMorosidad;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public Integer getNumeroMaximoDocumentosSinGarantia()
/* 418:    */   {
/* 419:606 */     return this.numeroMaximoDocumentosSinGarantia;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setNumeroMaximoDocumentosSinGarantia(Integer numeroMaximoDocumentosSinGarantia)
/* 423:    */   {
/* 424:610 */     this.numeroMaximoDocumentosSinGarantia = numeroMaximoDocumentosSinGarantia;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public boolean isVerificarDocumentos()
/* 428:    */   {
/* 429:614 */     return this.verificarDocumentos;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setVerificarDocumentos(boolean verificarDocumentos)
/* 433:    */   {
/* 434:618 */     this.verificarDocumentos = verificarDocumentos;
/* 435:    */   }
/* 436:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaEmpresa
 * JD-Core Version:    0.7.0.1
 */