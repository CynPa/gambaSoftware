/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="activo_fijo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  29:    */ public class ActivoFijo
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="activo_fijo", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="activo_fijo")
/*  36:    */   @Column(name="id_activo_fijo")
/*  37:    */   private int idActivoFijo;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="codigo", nullable=false)
/*  41:    */   @Size(min=1, max=10)
/*  42:    */   @NotNull
/*  43:    */   private String codigo;
/*  44:    */   @Column(name="nombre", nullable=false, length=50)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=2, max=50)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="codigo_barras", nullable=true)
/*  49:    */   @Size(max=20)
/*  50:    */   private String codigoBarras;
/*  51:    */   @Column(name="numero_parte", nullable=false)
/*  52:    */   @Size(max=20)
/*  53: 77 */   private String numeroParte = "";
/*  54:    */   @Column(name="numero_serie", nullable=false)
/*  55:    */   @Size(max=20)
/*  56: 81 */   private String numeroSerie = "";
/*  57:    */   @Column(name="fecha_factura_proveedor", nullable=false)
/*  58:    */   @Temporal(TemporalType.DATE)
/*  59:    */   @NotNull
/*  60:    */   private Date fechaFacturaProveedor;
/*  61:    */   @Column(name="numero_factura_proveedor", nullable=false)
/*  62:    */   @Size(min=1, max=20)
/*  63:    */   @NotNull
/*  64: 90 */   private String numeroFacturaProveedor = "";
/*  65:    */   @Column(name="valor_activo", nullable=false, precision=12, scale=2)
/*  66:    */   @Digits(integer=12, fraction=2)
/*  67:    */   @Min(0L)
/*  68: 95 */   private BigDecimal valorActivo = BigDecimal.ZERO;
/*  69:    */   @Column(name="valor_depreciado", nullable=false, precision=12, scale=2)
/*  70:    */   @Digits(integer=12, fraction=2)
/*  71:    */   @Min(0L)
/*  72:100 */   private BigDecimal valorDepreciado = BigDecimal.ZERO;
/*  73:    */   @Column(name="valor_compra_relacionada", nullable=false, precision=12, scale=2)
/*  74:    */   @Digits(integer=12, fraction=2)
/*  75:    */   @Min(0L)
/*  76:105 */   private BigDecimal valorCompraRelacionada = BigDecimal.ZERO;
/*  77:    */   @Column(name="valor_adicional", nullable=false, precision=12, scale=2)
/*  78:    */   @Digits(integer=12, fraction=2)
/*  79:110 */   private BigDecimal valorAdicional = BigDecimal.ZERO;
/*  80:    */   @Column(name="fecha_baja", nullable=true)
/*  81:    */   @Temporal(TemporalType.DATE)
/*  82:    */   private Date fechaBaja;
/*  83:    */   @Column(name="indicador_depreciar", nullable=false)
/*  84:    */   private boolean indicadorDepreciar;
/*  85:    */   @Column(name="imagen", nullable=true)
/*  86:    */   @Size(max=50)
/*  87:    */   private String imagen;
/*  88:    */   @Column(name="descripcion", nullable=true)
/*  89:    */   @Size(max=200)
/*  90:125 */   private String descripcion = "";
/*  91:    */   @Column(name="activo", nullable=false)
/*  92:    */   private boolean activo;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_categoria_activo", nullable=false)
/*  95:    */   private CategoriaActivo categoriaActivo;
/*  96:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_subcategoria_activo", nullable=true)
/*  98:    */   private SubcategoriaActivo subcategoriaActivo;
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_sucursal", nullable=false)
/* 101:    */   private Sucursal sucursal;
/* 102:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 103:    */   @JoinColumn(name="id_departamento", nullable=false)
/* 104:    */   private Departamento departamento;
/* 105:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 106:    */   @JoinColumn(name="id_producto", nullable=true)
/* 107:    */   private Producto producto;
/* 108:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 109:    */   @JoinColumn(name="id_motivo_baja_activo", nullable=true)
/* 110:    */   private MotivoBajaActivo motivoBajaActivo;
/* 111:    */   @OneToOne(fetch=FetchType.LAZY)
/* 112:    */   @JoinColumn(name="id_asiento_baja_activo_niff", nullable=true)
/* 113:    */   private Asiento asientoBajaActivoNIIF;
/* 114:    */   @OneToOne(fetch=FetchType.LAZY)
/* 115:    */   @JoinColumn(name="id_asiento_baja_activo_fiscal", nullable=true)
/* 116:    */   private Asiento asientoBajaActivoFiscal;
/* 117:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="activoFijo")
/* 118:167 */   private List<CustodioActivoFijo> listaCustodioActivoFijo = new ArrayList();
/* 119:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="activoFijo")
/* 120:170 */   private List<Depreciacion> listaDepreciacion = new ArrayList();
/* 121:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 122:    */   @JoinColumn(name="id_activo_fijo_padre", nullable=true)
/* 123:    */   private ActivoFijo activoFijoPrincipal;
/* 124:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 125:    */   @JoinColumn(name="id_custodio_activo_fijo", nullable=true)
/* 126:    */   private CustodioActivoFijo custodioActivoFijo;
/* 127:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="activoFijoPrincipal")
/* 128:181 */   private List<ActivoFijo> listaActivoFijoRelacionado = new ArrayList();
/* 129:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 130:    */   @JoinColumn(name="id_centro_costo", nullable=true)
/* 131:    */   private DimensionContable centroCosto;
/* 132:    */   @Transient
/* 133:    */   private boolean traEsEditable;
/* 134:    */   @Transient
/* 135:    */   private Date traFechaInicioDepreciacion;
/* 136:    */   
/* 137:    */   public ActivoFijo() {}
/* 138:    */   
/* 139:    */   public ActivoFijo(int idActivoFijo, String codigo, String nombre)
/* 140:    */   {
/* 141:217 */     this.idActivoFijo = idActivoFijo;
/* 142:218 */     this.codigo = codigo;
/* 143:219 */     this.nombre = nombre;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getId()
/* 147:    */   {
/* 148:233 */     return this.idActivoFijo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getIdActivoFijo()
/* 152:    */   {
/* 153:242 */     return this.idActivoFijo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setIdActivoFijo(int idActivoFijo)
/* 157:    */   {
/* 158:252 */     this.idActivoFijo = idActivoFijo;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getIdOrganizacion()
/* 162:    */   {
/* 163:261 */     return this.idOrganizacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setIdOrganizacion(int idOrganizacion)
/* 167:    */   {
/* 168:271 */     this.idOrganizacion = idOrganizacion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getCodigo()
/* 172:    */   {
/* 173:280 */     return this.codigo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setCodigo(String codigo)
/* 177:    */   {
/* 178:290 */     this.codigo = codigo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getNombre()
/* 182:    */   {
/* 183:299 */     return this.nombre;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setNombre(String nombre)
/* 187:    */   {
/* 188:309 */     this.nombre = nombre;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getCodigoBarras()
/* 192:    */   {
/* 193:318 */     return this.codigoBarras;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setCodigoBarras(String codigoBarras)
/* 197:    */   {
/* 198:328 */     this.codigoBarras = codigoBarras;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getNumeroParte()
/* 202:    */   {
/* 203:337 */     return this.numeroParte;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setNumeroParte(String numeroParte)
/* 207:    */   {
/* 208:347 */     this.numeroParte = numeroParte;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getNumeroSerie()
/* 212:    */   {
/* 213:356 */     return this.numeroSerie;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setNumeroSerie(String numeroSerie)
/* 217:    */   {
/* 218:366 */     this.numeroSerie = numeroSerie;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Date getFechaFacturaProveedor()
/* 222:    */   {
/* 223:375 */     return this.fechaFacturaProveedor;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setFechaFacturaProveedor(Date fechaFacturaProveedor)
/* 227:    */   {
/* 228:385 */     this.fechaFacturaProveedor = fechaFacturaProveedor;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getNumeroFacturaProveedor()
/* 232:    */   {
/* 233:394 */     return this.numeroFacturaProveedor;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setNumeroFacturaProveedor(String numeroFacturaProveedor)
/* 237:    */   {
/* 238:404 */     this.numeroFacturaProveedor = numeroFacturaProveedor;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getImagen()
/* 242:    */   {
/* 243:413 */     return this.imagen;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setImagen(String imagen)
/* 247:    */   {
/* 248:423 */     this.imagen = imagen;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getDescripcion()
/* 252:    */   {
/* 253:432 */     return this.descripcion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setDescripcion(String descripcion)
/* 257:    */   {
/* 258:442 */     this.descripcion = descripcion;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public CategoriaActivo getCategoriaActivo()
/* 262:    */   {
/* 263:451 */     return this.categoriaActivo;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setCategoriaActivo(CategoriaActivo categoriaActivo)
/* 267:    */   {
/* 268:461 */     this.categoriaActivo = categoriaActivo;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Producto getProducto()
/* 272:    */   {
/* 273:470 */     return this.producto;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setProducto(Producto producto)
/* 277:    */   {
/* 278:480 */     this.producto = producto;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public MotivoBajaActivo getMotivoBajaActivo()
/* 282:    */   {
/* 283:489 */     return this.motivoBajaActivo;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setMotivoBajaActivo(MotivoBajaActivo motivoBajaActivo)
/* 287:    */   {
/* 288:499 */     this.motivoBajaActivo = motivoBajaActivo;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public BigDecimal getValorActivo()
/* 292:    */   {
/* 293:508 */     return this.valorActivo;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setValorActivo(BigDecimal valorActivo)
/* 297:    */   {
/* 298:518 */     this.valorActivo = valorActivo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public BigDecimal getValorDepreciado()
/* 302:    */   {
/* 303:527 */     return this.valorDepreciado;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setValorDepreciado(BigDecimal valorDepreciado)
/* 307:    */   {
/* 308:537 */     this.valorDepreciado = valorDepreciado;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public Date getFechaBaja()
/* 312:    */   {
/* 313:546 */     return this.fechaBaja;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setFechaBaja(Date fechaBaja)
/* 317:    */   {
/* 318:556 */     this.fechaBaja = fechaBaja;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean isIndicadorDepreciar()
/* 322:    */   {
/* 323:565 */     return this.indicadorDepreciar;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setIndicadorDepreciar(boolean indicadorDepreciar)
/* 327:    */   {
/* 328:575 */     this.indicadorDepreciar = indicadorDepreciar;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public boolean isActivo()
/* 332:    */   {
/* 333:584 */     return this.activo;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setActivo(boolean activo)
/* 337:    */   {
/* 338:594 */     this.activo = activo;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public List<CustodioActivoFijo> getListaCustodioActivoFijo()
/* 342:    */   {
/* 343:603 */     return this.listaCustodioActivoFijo;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setListaCustodioActivoFijo(List<CustodioActivoFijo> listaCustodioActivoFijo)
/* 347:    */   {
/* 348:613 */     this.listaCustodioActivoFijo = listaCustodioActivoFijo;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public List<Depreciacion> getListaDepreciacion()
/* 352:    */   {
/* 353:622 */     return this.listaDepreciacion;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setListaDepreciacion(List<Depreciacion> listaDepreciacion)
/* 357:    */   {
/* 358:632 */     this.listaDepreciacion = listaDepreciacion;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public boolean isTraEsEditable()
/* 362:    */   {
/* 363:641 */     return this.traEsEditable;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setTraEsEditable(boolean traEsEditable)
/* 367:    */   {
/* 368:651 */     this.traEsEditable = traEsEditable;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public Asiento getAsientoBajaActivoNIIF()
/* 372:    */   {
/* 373:660 */     return this.asientoBajaActivoNIIF;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setAsientoBajaActivoNIIF(Asiento asientoBajaActivoNIIF)
/* 377:    */   {
/* 378:670 */     this.asientoBajaActivoNIIF = asientoBajaActivoNIIF;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public Asiento getAsientoBajaActivoFiscal()
/* 382:    */   {
/* 383:679 */     return this.asientoBajaActivoFiscal;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setAsientoBajaActivoFiscal(Asiento asientoBajaActivoFiscal)
/* 387:    */   {
/* 388:689 */     this.asientoBajaActivoFiscal = asientoBajaActivoFiscal;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public BigDecimal getValorCompraRelacionada()
/* 392:    */   {
/* 393:698 */     return this.valorCompraRelacionada;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setValorCompraRelacionada(BigDecimal valorCompraRelacionada)
/* 397:    */   {
/* 398:708 */     this.valorCompraRelacionada = valorCompraRelacionada;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public BigDecimal getValorAdicional()
/* 402:    */   {
/* 403:717 */     return this.valorAdicional;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setValorAdicional(BigDecimal valorAdicional)
/* 407:    */   {
/* 408:727 */     this.valorAdicional = valorAdicional;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public ActivoFijo getActivoFijoPrincipal()
/* 412:    */   {
/* 413:736 */     return this.activoFijoPrincipal;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setActivoFijoPrincipal(ActivoFijo activoFijoPrincipal)
/* 417:    */   {
/* 418:746 */     this.activoFijoPrincipal = activoFijoPrincipal;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public List<ActivoFijo> getListaActivoFijoRelacionado()
/* 422:    */   {
/* 423:755 */     return this.listaActivoFijoRelacionado;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setListaActivoFijoRelacionado(List<ActivoFijo> listaActivoFijoRelacionado)
/* 427:    */   {
/* 428:765 */     this.listaActivoFijoRelacionado = listaActivoFijoRelacionado;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public CustodioActivoFijo getCustodioActivoFijo()
/* 432:    */   {
/* 433:769 */     return this.custodioActivoFijo;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setCustodioActivoFijo(CustodioActivoFijo custodioActivoFijo)
/* 437:    */   {
/* 438:773 */     this.custodioActivoFijo = custodioActivoFijo;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public SubcategoriaActivo getSubcategoriaActivo()
/* 442:    */   {
/* 443:782 */     return this.subcategoriaActivo;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setSubcategoriaActivo(SubcategoriaActivo subcategoriaActivo)
/* 447:    */   {
/* 448:792 */     this.subcategoriaActivo = subcategoriaActivo;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public Date getTraFechaInicioDepreciacion()
/* 452:    */   {
/* 453:801 */     return this.traFechaInicioDepreciacion;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setTraFechaInicioDepreciacion(Date traFechaInicioDepreciacion)
/* 457:    */   {
/* 458:811 */     this.traFechaInicioDepreciacion = traFechaInicioDepreciacion;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public Sucursal getSucursal()
/* 462:    */   {
/* 463:815 */     return this.sucursal;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setSucursal(Sucursal sucursal)
/* 467:    */   {
/* 468:819 */     this.sucursal = sucursal;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public Departamento getDepartamento()
/* 472:    */   {
/* 473:823 */     return this.departamento;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setDepartamento(Departamento departamento)
/* 477:    */   {
/* 478:827 */     this.departamento = departamento;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public DimensionContable getCentroCosto()
/* 482:    */   {
/* 483:831 */     return this.centroCosto;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setCentroCosto(DimensionContable centroCosto)
/* 487:    */   {
/* 488:835 */     this.centroCosto = centroCosto;
/* 489:    */   }
/* 490:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ActivoFijo
 * JD-Core Version:    0.7.0.1
 */