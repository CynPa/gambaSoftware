/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
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
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="criterio_contabilizacion")
/*  17:    */ public class CriterioContabilizacion
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -4654788611481712034L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="criterio_contabilizacion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="criterio_contabilizacion")
/*  24:    */   @Column(name="id_criterio_contabilizacion")
/*  25:    */   private int idCriterioContabilizacion;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  29:    */   @JoinColumn(name="id_documento_contabilzacion", nullable=true)
/*  30:    */   private DocumentoContabilizacion documentoContabilizacion;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  33:    */   private CuentaContable cuentaContable;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_documento", nullable=true)
/*  36:    */   private Documento documento;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  39:    */   private Sucursal sucursal;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_categoria_empresa", nullable=true)
/*  42:    */   private CategoriaEmpresa categoriaEmpresa;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_cliente", nullable=true)
/*  45:    */   private Empresa cliente;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_proveedor", nullable=true)
/*  48:    */   private Empresa proveedor;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_categoria_producto", nullable=true)
/*  51:    */   private CategoriaProducto categoriaProducto;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_subcategoria_producto", nullable=true)
/*  54:    */   private SubcategoriaProducto subcategoriaProducto;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_producto", nullable=true)
/*  57:    */   private Producto producto;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_bodega", nullable=true)
/*  60:    */   private Bodega bodega;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_destino_costo", nullable=true)
/*  63:    */   private DestinoCosto destinoCosto;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_canal", nullable=true)
/*  66:    */   private Canal canal;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_subcliente", nullable=true)
/*  69:    */   private Subempresa subcliente;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_subproveedor", nullable=true)
/*  72:    */   private Subempresa subproveedor;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_zona", nullable=true)
/*  75:    */   private Zona zona;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_categoria_impuesto", nullable=true)
/*  78:    */   private CategoriaImpuesto categoriaImpuesto;
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_impuesto", nullable=true)
/*  81:    */   private Impuesto impuesto;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_motivo_nota_credito_cliente", nullable=true)
/*  84:    */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente;
/*  85:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  86:    */   @JoinColumn(name="id_motivo_nota_credito_proveedor", nullable=true)
/*  87:    */   private MotivoNotaCreditoProveedor motivoNotaCreditoProveedor;
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  89:    */   @JoinColumn(name="id_motivo_ajuste_inventario", nullable=true)
/*  90:    */   private MotivoAjusteInventario motivoAjusteInventario;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_motivo_baja_activo", nullable=true)
/*  93:    */   private MotivoBajaActivo motivoBajaActivo;
/*  94:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  95:    */   @JoinColumn(name="id_categoria_activo", nullable=true)
/*  96:    */   private CategoriaActivo categoriaActivo;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_subcategoria_activo", nullable=true)
/*  99:    */   private SubcategoriaActivo subcategoriaActivo;
/* 100:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 101:    */   @JoinColumn(name="id_activo_fijo", nullable=true)
/* 102:    */   private ActivoFijo activoFijo;
/* 103:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 104:    */   @JoinColumn(name="id_empleado", nullable=true)
/* 105:    */   private Empleado empleado;
/* 106:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 107:    */   @JoinColumn(name="id_rubro", nullable=true)
/* 108:    */   private Rubro rubro;
/* 109:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 110:    */   @JoinColumn(name="id_departamento", nullable=true)
/* 111:    */   private Departamento departamento;
/* 112:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 113:    */   @JoinColumn(name="id_concepto_contable", nullable=true)
/* 114:    */   private ConceptoContable conceptoContable;
/* 115:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 116:    */   @JoinColumn(name="id_tipo_amortizacion", nullable=true)
/* 117:    */   private TipoAmortizacion tipoAmortizacion;
/* 118:    */   @Column(name="orden", nullable=false)
/* 119:    */   private int orden;
/* 120:    */   
/* 121:    */   public int getIdCriterioContabilizacion()
/* 122:    */   {
/* 123:179 */     return this.idCriterioContabilizacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIdCriterioContabilizacion(int idCriterioContabilizacion)
/* 127:    */   {
/* 128:189 */     this.idCriterioContabilizacion = idCriterioContabilizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getIdOrganizacion()
/* 132:    */   {
/* 133:198 */     return this.idOrganizacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setIdOrganizacion(int idOrganizacion)
/* 137:    */   {
/* 138:208 */     this.idOrganizacion = idOrganizacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public DocumentoContabilizacion getDocumentoContabilizacion()
/* 142:    */   {
/* 143:217 */     return this.documentoContabilizacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDocumentoContabilizacion(DocumentoContabilizacion documentoContabilizacion)
/* 147:    */   {
/* 148:227 */     this.documentoContabilizacion = documentoContabilizacion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public CuentaContable getCuentaContable()
/* 152:    */   {
/* 153:236 */     return this.cuentaContable;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 157:    */   {
/* 158:246 */     this.cuentaContable = cuentaContable;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Documento getDocumento()
/* 162:    */   {
/* 163:255 */     return this.documento;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setDocumento(Documento documento)
/* 167:    */   {
/* 168:265 */     this.documento = documento;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Sucursal getSucursal()
/* 172:    */   {
/* 173:274 */     return this.sucursal;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setSucursal(Sucursal sucursal)
/* 177:    */   {
/* 178:284 */     this.sucursal = sucursal;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 182:    */   {
/* 183:293 */     return this.categoriaEmpresa;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 187:    */   {
/* 188:303 */     this.categoriaEmpresa = categoriaEmpresa;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Empresa getCliente()
/* 192:    */   {
/* 193:312 */     return this.cliente;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setCliente(Empresa cliente)
/* 197:    */   {
/* 198:322 */     this.cliente = cliente;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Empresa getProveedor()
/* 202:    */   {
/* 203:331 */     return this.proveedor;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setProveedor(Empresa proveedor)
/* 207:    */   {
/* 208:341 */     this.proveedor = proveedor;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public CategoriaProducto getCategoriaProducto()
/* 212:    */   {
/* 213:350 */     return this.categoriaProducto;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 217:    */   {
/* 218:360 */     this.categoriaProducto = categoriaProducto;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 222:    */   {
/* 223:369 */     return this.subcategoriaProducto;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 227:    */   {
/* 228:379 */     this.subcategoriaProducto = subcategoriaProducto;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Producto getProducto()
/* 232:    */   {
/* 233:388 */     return this.producto;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setProducto(Producto producto)
/* 237:    */   {
/* 238:398 */     this.producto = producto;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Bodega getBodega()
/* 242:    */   {
/* 243:407 */     return this.bodega;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setBodega(Bodega bodega)
/* 247:    */   {
/* 248:417 */     this.bodega = bodega;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public Canal getCanal()
/* 252:    */   {
/* 253:426 */     return this.canal;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setCanal(Canal canal)
/* 257:    */   {
/* 258:436 */     this.canal = canal;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Subempresa getSubcliente()
/* 262:    */   {
/* 263:445 */     return this.subcliente;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setSubcliente(Subempresa subcliente)
/* 267:    */   {
/* 268:455 */     this.subcliente = subcliente;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Subempresa getSubproveedor()
/* 272:    */   {
/* 273:464 */     return this.subproveedor;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setSubproveedor(Subempresa subproveedor)
/* 277:    */   {
/* 278:474 */     this.subproveedor = subproveedor;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Zona getZona()
/* 282:    */   {
/* 283:483 */     return this.zona;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setZona(Zona zona)
/* 287:    */   {
/* 288:493 */     this.zona = zona;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/* 292:    */   {
/* 293:502 */     return this.motivoNotaCreditoCliente;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 297:    */   {
/* 298:512 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public MotivoNotaCreditoProveedor getMotivoNotaCreditoProveedor()
/* 302:    */   {
/* 303:521 */     return this.motivoNotaCreditoProveedor;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setMotivoNotaCreditoProveedor(MotivoNotaCreditoProveedor motivoNotaCreditoProveedor)
/* 307:    */   {
/* 308:531 */     this.motivoNotaCreditoProveedor = motivoNotaCreditoProveedor;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public MotivoAjusteInventario getMotivoAjusteInventario()
/* 312:    */   {
/* 313:540 */     return this.motivoAjusteInventario;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setMotivoAjusteInventario(MotivoAjusteInventario motivoAjusteInventario)
/* 317:    */   {
/* 318:550 */     this.motivoAjusteInventario = motivoAjusteInventario;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public MotivoBajaActivo getMotivoBajaActivo()
/* 322:    */   {
/* 323:559 */     return this.motivoBajaActivo;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setMotivoBajaActivo(MotivoBajaActivo motivoBajaActivo)
/* 327:    */   {
/* 328:569 */     this.motivoBajaActivo = motivoBajaActivo;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public CategoriaActivo getCategoriaActivo()
/* 332:    */   {
/* 333:578 */     return this.categoriaActivo;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setCategoriaActivo(CategoriaActivo categoriaActivo)
/* 337:    */   {
/* 338:588 */     this.categoriaActivo = categoriaActivo;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public SubcategoriaActivo getSubcategoriaActivo()
/* 342:    */   {
/* 343:597 */     return this.subcategoriaActivo;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setSubcategoriaActivo(SubcategoriaActivo subcategoriaActivo)
/* 347:    */   {
/* 348:607 */     this.subcategoriaActivo = subcategoriaActivo;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public ActivoFijo getActivoFijo()
/* 352:    */   {
/* 353:616 */     return this.activoFijo;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 357:    */   {
/* 358:626 */     this.activoFijo = activoFijo;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public Empleado getEmpleado()
/* 362:    */   {
/* 363:635 */     return this.empleado;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setEmpleado(Empleado empleado)
/* 367:    */   {
/* 368:645 */     this.empleado = empleado;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public Rubro getRubro()
/* 372:    */   {
/* 373:654 */     return this.rubro;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setRubro(Rubro rubro)
/* 377:    */   {
/* 378:664 */     this.rubro = rubro;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public Impuesto getImpuesto()
/* 382:    */   {
/* 383:673 */     return this.impuesto;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setImpuesto(Impuesto impuesto)
/* 387:    */   {
/* 388:683 */     this.impuesto = impuesto;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public ConceptoContable getConceptoContable()
/* 392:    */   {
/* 393:687 */     return this.conceptoContable;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setConceptoContable(ConceptoContable conceptoContable)
/* 397:    */   {
/* 398:691 */     this.conceptoContable = conceptoContable;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public Departamento getDepartamento()
/* 402:    */   {
/* 403:695 */     return this.departamento;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setDepartamento(Departamento departamento)
/* 407:    */   {
/* 408:699 */     this.departamento = departamento;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public DestinoCosto getDestinoCosto()
/* 412:    */   {
/* 413:703 */     return this.destinoCosto;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 417:    */   {
/* 418:707 */     this.destinoCosto = destinoCosto;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public TipoAmortizacion getTipoAmortizacion()
/* 422:    */   {
/* 423:714 */     return this.tipoAmortizacion;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setTipoAmortizacion(TipoAmortizacion tipoAmortizacion)
/* 427:    */   {
/* 428:722 */     this.tipoAmortizacion = tipoAmortizacion;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public int getOrden()
/* 432:    */   {
/* 433:726 */     return this.orden;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setOrden(int orden)
/* 437:    */   {
/* 438:730 */     this.orden = orden;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public CategoriaImpuesto getCategoriaImpuesto()
/* 442:    */   {
/* 443:734 */     return this.categoriaImpuesto;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setCategoriaImpuesto(CategoriaImpuesto categoriaImpuesto)
/* 447:    */   {
/* 448:738 */     this.categoriaImpuesto = categoriaImpuesto;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public int getId()
/* 452:    */   {
/* 453:748 */     return this.idCriterioContabilizacion;
/* 454:    */   }
/* 455:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CriterioContabilizacion
 * JD-Core Version:    0.7.0.1
 */