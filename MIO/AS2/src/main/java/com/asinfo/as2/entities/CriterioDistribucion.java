/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="criterio_distribucion")
/*  21:    */ public class CriterioDistribucion
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -4654788611481712034L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="criterio_distribucion", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="criterio_distribucion")
/*  28:    */   @Column(name="id_criterio_distribucion")
/*  29:    */   private int idCriterioDistribucion;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Enumerated(EnumType.ORDINAL)
/*  33:    */   @Column(name="documento_base", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private DocumentoBase documentoBase;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_dimension_contable1")
/*  38:    */   private DimensionContable dimensionContable1;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_dimension_contable2")
/*  41:    */   private DimensionContable dimensionContable2;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_dimension_contable3")
/*  44:    */   private DimensionContable dimensionContable3;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_dimension_contable4")
/*  47:    */   private DimensionContable dimensionContable4;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_dimension_contable5")
/*  50:    */   private DimensionContable dimensionContable5;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_documento", nullable=true)
/*  53:    */   private Documento documento;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  56:    */   private Sucursal sucursal;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_categoria_empresa", nullable=true)
/*  59:    */   private CategoriaEmpresa categoriaEmpresa;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_cliente", nullable=true)
/*  62:    */   private Empresa cliente;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_proveedor", nullable=true)
/*  65:    */   private Empresa proveedor;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_categoria_producto", nullable=true)
/*  68:    */   private CategoriaProducto categoriaProducto;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_subcategoria_producto", nullable=true)
/*  71:    */   private SubcategoriaProducto subcategoriaProducto;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_producto", nullable=true)
/*  74:    */   private Producto producto;
/*  75:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  76:    */   @JoinColumn(name="id_bodega", nullable=true)
/*  77:    */   private Bodega bodega;
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_destino_costo", nullable=true)
/*  80:    */   private DestinoCosto destinoCosto;
/*  81:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  82:    */   @JoinColumn(name="id_canal", nullable=true)
/*  83:    */   private Canal canal;
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_subcliente", nullable=true)
/*  86:    */   private Subempresa subcliente;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_subproveedor", nullable=true)
/*  89:    */   private Subempresa subproveedor;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_zona", nullable=true)
/*  92:    */   private Zona zona;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_categoria_impuesto", nullable=true)
/*  95:    */   private CategoriaImpuesto categoriaImpuesto;
/*  96:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_impuesto", nullable=true)
/*  98:    */   private Impuesto impuesto;
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_motivo_nota_credito_cliente", nullable=true)
/* 101:    */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente;
/* 102:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 103:    */   @JoinColumn(name="id_motivo_nota_credito_proveedor", nullable=true)
/* 104:    */   private MotivoNotaCreditoProveedor motivoNotaCreditoProveedor;
/* 105:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 106:    */   @JoinColumn(name="id_motivo_ajuste_inventario", nullable=true)
/* 107:    */   private MotivoAjusteInventario motivoAjusteInventario;
/* 108:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 109:    */   @JoinColumn(name="id_motivo_baja_activo", nullable=true)
/* 110:    */   private MotivoBajaActivo motivoBajaActivo;
/* 111:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 112:    */   @JoinColumn(name="id_categoria_activo", nullable=true)
/* 113:    */   private CategoriaActivo categoriaActivo;
/* 114:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 115:    */   @JoinColumn(name="id_subcategoria_activo", nullable=true)
/* 116:    */   private SubcategoriaActivo subcategoriaActivo;
/* 117:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 118:    */   @JoinColumn(name="id_activo_fijo", nullable=true)
/* 119:    */   private ActivoFijo activoFijo;
/* 120:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 121:    */   @JoinColumn(name="id_empleado", nullable=true)
/* 122:    */   private Empleado empleado;
/* 123:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 124:    */   @JoinColumn(name="id_rubro", nullable=true)
/* 125:    */   private Rubro rubro;
/* 126:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 127:    */   @JoinColumn(name="id_departamento", nullable=true)
/* 128:    */   private Departamento departamento;
/* 129:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 130:    */   @JoinColumn(name="id_concepto_contable", nullable=true)
/* 131:    */   private ConceptoContable conceptoContable;
/* 132:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 133:    */   @JoinColumn(name="id_tipo_amortizacion", nullable=true)
/* 134:    */   private TipoAmortizacion tipoAmortizacion;
/* 135:    */   @Column(name="orden", nullable=false)
/* 136:    */   private int orden;
/* 137:    */   
/* 138:    */   public int getIdCriterioDistribucion()
/* 139:    */   {
/* 140:200 */     return this.idCriterioDistribucion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdCriterioDistribucion(int idCriterioDistribucion)
/* 144:    */   {
/* 145:210 */     this.idCriterioDistribucion = idCriterioDistribucion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getIdOrganizacion()
/* 149:    */   {
/* 150:219 */     return this.idOrganizacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdOrganizacion(int idOrganizacion)
/* 154:    */   {
/* 155:229 */     this.idOrganizacion = idOrganizacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public DocumentoBase getDocumentoBase()
/* 159:    */   {
/* 160:233 */     return this.documentoBase;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 164:    */   {
/* 165:237 */     this.documentoBase = documentoBase;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public DimensionContable getDimensionContable1()
/* 169:    */   {
/* 170:246 */     return this.dimensionContable1;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/* 174:    */   {
/* 175:256 */     this.dimensionContable1 = dimensionContable1;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public DimensionContable getDimensionContable2()
/* 179:    */   {
/* 180:265 */     return this.dimensionContable2;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 184:    */   {
/* 185:275 */     this.dimensionContable2 = dimensionContable2;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DimensionContable getDimensionContable3()
/* 189:    */   {
/* 190:284 */     return this.dimensionContable3;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 194:    */   {
/* 195:294 */     this.dimensionContable3 = dimensionContable3;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public DimensionContable getDimensionContable4()
/* 199:    */   {
/* 200:303 */     return this.dimensionContable4;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 204:    */   {
/* 205:313 */     this.dimensionContable4 = dimensionContable4;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public DimensionContable getDimensionContable5()
/* 209:    */   {
/* 210:322 */     return this.dimensionContable5;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 214:    */   {
/* 215:332 */     this.dimensionContable5 = dimensionContable5;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Documento getDocumento()
/* 219:    */   {
/* 220:341 */     return this.documento;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setDocumento(Documento documento)
/* 224:    */   {
/* 225:351 */     this.documento = documento;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Sucursal getSucursal()
/* 229:    */   {
/* 230:360 */     return this.sucursal;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setSucursal(Sucursal sucursal)
/* 234:    */   {
/* 235:370 */     this.sucursal = sucursal;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 239:    */   {
/* 240:379 */     return this.categoriaEmpresa;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 244:    */   {
/* 245:389 */     this.categoriaEmpresa = categoriaEmpresa;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Empresa getCliente()
/* 249:    */   {
/* 250:398 */     return this.cliente;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setCliente(Empresa cliente)
/* 254:    */   {
/* 255:408 */     this.cliente = cliente;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Empresa getProveedor()
/* 259:    */   {
/* 260:417 */     return this.proveedor;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setProveedor(Empresa proveedor)
/* 264:    */   {
/* 265:427 */     this.proveedor = proveedor;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public CategoriaProducto getCategoriaProducto()
/* 269:    */   {
/* 270:436 */     return this.categoriaProducto;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 274:    */   {
/* 275:446 */     this.categoriaProducto = categoriaProducto;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 279:    */   {
/* 280:455 */     return this.subcategoriaProducto;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 284:    */   {
/* 285:465 */     this.subcategoriaProducto = subcategoriaProducto;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Producto getProducto()
/* 289:    */   {
/* 290:474 */     return this.producto;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setProducto(Producto producto)
/* 294:    */   {
/* 295:484 */     this.producto = producto;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Bodega getBodega()
/* 299:    */   {
/* 300:493 */     return this.bodega;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setBodega(Bodega bodega)
/* 304:    */   {
/* 305:503 */     this.bodega = bodega;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Canal getCanal()
/* 309:    */   {
/* 310:512 */     return this.canal;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setCanal(Canal canal)
/* 314:    */   {
/* 315:522 */     this.canal = canal;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Subempresa getSubcliente()
/* 319:    */   {
/* 320:531 */     return this.subcliente;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setSubcliente(Subempresa subcliente)
/* 324:    */   {
/* 325:541 */     this.subcliente = subcliente;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public Subempresa getSubproveedor()
/* 329:    */   {
/* 330:550 */     return this.subproveedor;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setSubproveedor(Subempresa subproveedor)
/* 334:    */   {
/* 335:560 */     this.subproveedor = subproveedor;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public Zona getZona()
/* 339:    */   {
/* 340:569 */     return this.zona;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setZona(Zona zona)
/* 344:    */   {
/* 345:579 */     this.zona = zona;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/* 349:    */   {
/* 350:588 */     return this.motivoNotaCreditoCliente;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 354:    */   {
/* 355:598 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public MotivoNotaCreditoProveedor getMotivoNotaCreditoProveedor()
/* 359:    */   {
/* 360:607 */     return this.motivoNotaCreditoProveedor;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setMotivoNotaCreditoProveedor(MotivoNotaCreditoProveedor motivoNotaCreditoProveedor)
/* 364:    */   {
/* 365:617 */     this.motivoNotaCreditoProveedor = motivoNotaCreditoProveedor;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public MotivoAjusteInventario getMotivoAjusteInventario()
/* 369:    */   {
/* 370:626 */     return this.motivoAjusteInventario;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setMotivoAjusteInventario(MotivoAjusteInventario motivoAjusteInventario)
/* 374:    */   {
/* 375:636 */     this.motivoAjusteInventario = motivoAjusteInventario;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public MotivoBajaActivo getMotivoBajaActivo()
/* 379:    */   {
/* 380:645 */     return this.motivoBajaActivo;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setMotivoBajaActivo(MotivoBajaActivo motivoBajaActivo)
/* 384:    */   {
/* 385:655 */     this.motivoBajaActivo = motivoBajaActivo;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public CategoriaActivo getCategoriaActivo()
/* 389:    */   {
/* 390:664 */     return this.categoriaActivo;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setCategoriaActivo(CategoriaActivo categoriaActivo)
/* 394:    */   {
/* 395:674 */     this.categoriaActivo = categoriaActivo;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public SubcategoriaActivo getSubcategoriaActivo()
/* 399:    */   {
/* 400:683 */     return this.subcategoriaActivo;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setSubcategoriaActivo(SubcategoriaActivo subcategoriaActivo)
/* 404:    */   {
/* 405:693 */     this.subcategoriaActivo = subcategoriaActivo;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public ActivoFijo getActivoFijo()
/* 409:    */   {
/* 410:702 */     return this.activoFijo;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 414:    */   {
/* 415:712 */     this.activoFijo = activoFijo;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public Empleado getEmpleado()
/* 419:    */   {
/* 420:721 */     return this.empleado;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setEmpleado(Empleado empleado)
/* 424:    */   {
/* 425:731 */     this.empleado = empleado;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public Rubro getRubro()
/* 429:    */   {
/* 430:740 */     return this.rubro;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setRubro(Rubro rubro)
/* 434:    */   {
/* 435:750 */     this.rubro = rubro;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public Impuesto getImpuesto()
/* 439:    */   {
/* 440:759 */     return this.impuesto;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setImpuesto(Impuesto impuesto)
/* 444:    */   {
/* 445:769 */     this.impuesto = impuesto;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public ConceptoContable getConceptoContable()
/* 449:    */   {
/* 450:773 */     return this.conceptoContable;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setConceptoContable(ConceptoContable conceptoContable)
/* 454:    */   {
/* 455:777 */     this.conceptoContable = conceptoContable;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public int getOrden()
/* 459:    */   {
/* 460:781 */     return this.orden;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void setOrden(int orden)
/* 464:    */   {
/* 465:785 */     this.orden = orden;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public Departamento getDepartamento()
/* 469:    */   {
/* 470:789 */     return this.departamento;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void setDepartamento(Departamento departamento)
/* 474:    */   {
/* 475:793 */     this.departamento = departamento;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public DestinoCosto getDestinoCosto()
/* 479:    */   {
/* 480:797 */     return this.destinoCosto;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 484:    */   {
/* 485:801 */     this.destinoCosto = destinoCosto;
/* 486:    */   }
/* 487:    */   
/* 488:    */   public TipoAmortizacion getTipoAmortizacion()
/* 489:    */   {
/* 490:808 */     return this.tipoAmortizacion;
/* 491:    */   }
/* 492:    */   
/* 493:    */   public void setTipoAmortizacion(TipoAmortizacion tipoAmortizacion)
/* 494:    */   {
/* 495:816 */     this.tipoAmortizacion = tipoAmortizacion;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public CategoriaImpuesto getCategoriaImpuesto()
/* 499:    */   {
/* 500:820 */     return this.categoriaImpuesto;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setCategoriaImpuesto(CategoriaImpuesto categoriaImpuesto)
/* 504:    */   {
/* 505:824 */     this.categoriaImpuesto = categoriaImpuesto;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public int getId()
/* 509:    */   {
/* 510:834 */     return this.idCriterioDistribucion;
/* 511:    */   }
/* 512:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CriterioDistribucion
 * JD-Core Version:    0.7.0.1
 */