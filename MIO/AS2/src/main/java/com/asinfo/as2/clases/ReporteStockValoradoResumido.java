/*    1:     */ package com.asinfo.as2.clases;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.EntidadBase;
/*    4:     */ import java.math.BigDecimal;
/*    5:     */ import javax.persistence.Column;
/*    6:     */ import javax.persistence.Entity;
/*    7:     */ import javax.persistence.GeneratedValue;
/*    8:     */ import javax.persistence.GenerationType;
/*    9:     */ import javax.persistence.Id;
/*   10:     */ import javax.persistence.Table;
/*   11:     */ import javax.persistence.TableGenerator;
/*   12:     */ import javax.persistence.Transient;
/*   13:     */ import javax.validation.constraints.Digits;
/*   14:     */ import javax.validation.constraints.Min;
/*   15:     */ import javax.validation.constraints.NotNull;
/*   16:     */ import javax.validation.constraints.Size;
/*   17:     */ 
/*   18:     */ @Entity
/*   19:     */ @Table(name="tmp_reporte_stock_valorado_resumido")
/*   20:     */ public class ReporteStockValoradoResumido
/*   21:     */   extends EntidadBase
/*   22:     */ {
/*   23:     */   private static final long serialVersionUID = 1L;
/*   24:     */   @Id
/*   25:     */   @TableGenerator(name="detalle_stock_valorado", initialValue=0, allocationSize=50)
/*   26:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_stock_valorado")
/*   27:     */   @Column(name="id_tmp_reporte_stock_valorado_resumido")
/*   28:     */   private int idReporteStockValoradoResumido;
/*   29:     */   @Column(name="id_producto", nullable=false, length=20)
/*   30:     */   private int idProducto;
/*   31:     */   @Column(name="codigo_producto", nullable=false, length=20)
/*   32:     */   @NotNull
/*   33:     */   @Size(max=20)
/*   34:     */   private String codigoProducto;
/*   35:     */   @Column(name="nombre_producto", nullable=false, length=100)
/*   36:     */   @NotNull
/*   37:     */   @Size(max=100)
/*   38:     */   private String nombreProducto;
/*   39:     */   @Column(name="codigo_subcategoria_producto", nullable=false, length=20)
/*   40:     */   @NotNull
/*   41:     */   @Size(max=20)
/*   42:     */   private String codigoSubcategoriaProducto;
/*   43:     */   @Column(name="nombre_subcategoria_producto", nullable=false, length=100)
/*   44:     */   @NotNull
/*   45:     */   @Size(max=100)
/*   46:     */   private String nombreSubcategoriaProducto;
/*   47:     */   @Column(name="codigo_categoria_producto", nullable=false, length=20)
/*   48:     */   @NotNull
/*   49:     */   @Size(max=20)
/*   50:     */   private String codigoCategoriaProducto;
/*   51:     */   @Column(name="nombre_cubcategoria_producto", nullable=false, length=100)
/*   52:     */   @NotNull
/*   53:     */   @Size(max=100)
/*   54:     */   private String nombreCategoriaProducto;
/*   55:     */   @Column(name="id_bodega", nullable=false, length=20)
/*   56:     */   private int idBodega;
/*   57:     */   @Column(name="nombre_bodega", nullable=false, length=100)
/*   58:     */   @NotNull
/*   59:     */   @Size(max=100)
/*   60:     */   private String nombreBodega;
/*   61:     */   @Column(name="recepcion", precision=12, scale=2)
/*   62:     */   @Digits(integer=12, fraction=2)
/*   63:     */   @Min(0L)
/*   64:  92 */   private BigDecimal recepcion = BigDecimal.ZERO;
/*   65:     */   @Column(name="ajuste_ingreso", precision=12, scale=2)
/*   66:     */   @Digits(integer=12, fraction=2)
/*   67:     */   @Min(0L)
/*   68:  97 */   private BigDecimal ajusteIngreso = BigDecimal.ZERO;
/*   69:     */   @Column(name="transferencia_ingreso", precision=12, scale=2)
/*   70:     */   @Digits(integer=12, fraction=2)
/*   71:     */   @Min(0L)
/*   72: 102 */   private BigDecimal transferenciaIngreso = BigDecimal.ZERO;
/*   73:     */   @Column(name="devolucion_cliente", precision=12, scale=2)
/*   74:     */   @Digits(integer=12, fraction=2)
/*   75:     */   @Min(0L)
/*   76: 107 */   private BigDecimal devolucionCliente = BigDecimal.ZERO;
/*   77:     */   @Column(name="ajuste_egreso", precision=12, scale=2)
/*   78:     */   @Digits(integer=12, fraction=2)
/*   79:     */   @Min(0L)
/*   80: 112 */   private BigDecimal ajusteEgreso = BigDecimal.ZERO;
/*   81:     */   @Column(name="transferencia_egreso", precision=12, scale=2)
/*   82:     */   @Digits(integer=12, fraction=2)
/*   83:     */   @Min(0L)
/*   84: 117 */   private BigDecimal transferenciaEgreso = BigDecimal.ZERO;
/*   85:     */   @Column(name="consumo", precision=12, scale=2)
/*   86:     */   @Digits(integer=12, fraction=2)
/*   87:     */   @Min(0L)
/*   88: 122 */   private BigDecimal consumo = BigDecimal.ZERO;
/*   89:     */   @Column(name="despacho", precision=12, scale=2)
/*   90:     */   @Digits(integer=12, fraction=2)
/*   91:     */   @Min(0L)
/*   92: 127 */   private BigDecimal despacho = BigDecimal.ZERO;
/*   93:     */   @Column(name="devolucion_proveedor", precision=12, scale=2)
/*   94:     */   @Digits(integer=12, fraction=2)
/*   95:     */   @Min(0L)
/*   96: 132 */   private BigDecimal devolucionProvedor = BigDecimal.ZERO;
/*   97:     */   @Column(name="saldo_inicial", precision=12, scale=2)
/*   98:     */   @Digits(integer=12, fraction=2)
/*   99:     */   @Min(0L)
/*  100: 137 */   private BigDecimal saldoInicial = BigDecimal.ZERO;
/*  101:     */   @Column(name="saldo_final", precision=12, scale=2)
/*  102:     */   @Digits(integer=12, fraction=2)
/*  103:     */   @Min(0L)
/*  104: 142 */   private BigDecimal saldoFinal = BigDecimal.ZERO;
/*  105:     */   @Transient
/*  106:     */   private int idUnidad;
/*  107:     */   @Transient
/*  108:     */   private String codigoUnidad;
/*  109:     */   @Transient
/*  110:     */   private String nombreUnidad;
/*  111:     */   @Transient
/*  112:     */   private Integer idUnidadVenta;
/*  113:     */   @Transient
/*  114:     */   private String codigoUnidadVenta;
/*  115:     */   @Transient
/*  116:     */   private String nombreUnidadVenta;
/*  117:     */   @Transient
/*  118:     */   private Integer idUnidadAlmacenamiento;
/*  119:     */   @Transient
/*  120:     */   private String codigoUnidadAlmacenamiento;
/*  121:     */   @Transient
/*  122:     */   private String nombreUnidadAlmacenamiento;
/*  123:     */   @Transient
/*  124: 174 */   private BigDecimal costoPromedioAjusteInventario = BigDecimal.ZERO;
/*  125:     */   @Transient
/*  126: 177 */   private BigDecimal costoPromedioRecepcion = BigDecimal.ZERO;
/*  127:     */   @Transient
/*  128: 180 */   private BigDecimal costoPromedioTransferenciaIngreso = BigDecimal.ZERO;
/*  129:     */   @Transient
/*  130: 183 */   private BigDecimal costoPromedioDevolucion = BigDecimal.ZERO;
/*  131:     */   @Transient
/*  132: 186 */   private BigDecimal costoPromedioAjusteEgreso = BigDecimal.ZERO;
/*  133:     */   @Transient
/*  134: 189 */   private BigDecimal costoPromedioTransferenciaEgreso = BigDecimal.ZERO;
/*  135:     */   @Transient
/*  136: 192 */   private BigDecimal costoPromedioConsumo = BigDecimal.ZERO;
/*  137:     */   @Transient
/*  138: 195 */   private BigDecimal costoPromedioDespachos = BigDecimal.ZERO;
/*  139:     */   @Transient
/*  140: 198 */   private BigDecimal costoPromedioDevolucionProveedor = BigDecimal.ZERO;
/*  141:     */   @Transient
/*  142: 201 */   private BigDecimal precioDespacho = BigDecimal.ZERO;
/*  143:     */   @Transient
/*  144: 204 */   private BigDecimal saldoInicialEnPlata = BigDecimal.ZERO;
/*  145:     */   @Transient
/*  146: 207 */   private BigDecimal saldoDevolucionCliente = BigDecimal.ZERO;
/*  147:     */   @Transient
/*  148: 210 */   private BigDecimal devolucionClienteXAnulacion = BigDecimal.ZERO;
/*  149:     */   @Transient
/*  150: 213 */   private BigDecimal devolucionProveedorXAnulacion = BigDecimal.ZERO;
/*  151:     */   @Transient
/*  152: 216 */   private BigDecimal transformacionProducto = BigDecimal.ZERO;
/*  153:     */   @Transient
/*  154: 219 */   private BigDecimal transformacionProductoXAnulacion = BigDecimal.ZERO;
/*  155:     */   @Transient
/*  156: 222 */   private BigDecimal salidaMaterial = BigDecimal.ZERO;
/*  157:     */   @Transient
/*  158: 225 */   private BigDecimal ingresoProduccion = BigDecimal.ZERO;
/*  159:     */   @Transient
/*  160:     */   private String codigoAlterno;
/*  161:     */   @Transient
/*  162: 231 */   private BigDecimal costoRecepcion = BigDecimal.ZERO;
/*  163:     */   @Transient
/*  164: 234 */   private BigDecimal costoAjusteIngreso = BigDecimal.ZERO;
/*  165:     */   @Transient
/*  166: 237 */   private BigDecimal costoTransferenciaIngreso = BigDecimal.ZERO;
/*  167:     */   @Transient
/*  168: 240 */   private BigDecimal costoDevolucionCliente = BigDecimal.ZERO;
/*  169:     */   @Transient
/*  170: 243 */   private BigDecimal costoAjusteEgreso = BigDecimal.ZERO;
/*  171:     */   @Transient
/*  172: 246 */   private BigDecimal costoTransferenciaEgreso = BigDecimal.ZERO;
/*  173:     */   @Transient
/*  174: 249 */   private BigDecimal costoConsumo = BigDecimal.ZERO;
/*  175:     */   @Transient
/*  176: 252 */   private BigDecimal costoDespacho = BigDecimal.ZERO;
/*  177:     */   @Transient
/*  178: 255 */   private BigDecimal costoDevolucionProvedor = BigDecimal.ZERO;
/*  179:     */   @Transient
/*  180: 258 */   private BigDecimal costoDevolucionClienteXAnulacion = BigDecimal.ZERO;
/*  181:     */   @Transient
/*  182: 261 */   private BigDecimal costoDevolucionProveedorXAnulacion = BigDecimal.ZERO;
/*  183:     */   @Transient
/*  184: 264 */   private BigDecimal costoTransformacionProducto = BigDecimal.ZERO;
/*  185:     */   @Transient
/*  186: 267 */   private BigDecimal costoTransformacionProductoXAnulacion = BigDecimal.ZERO;
/*  187:     */   @Transient
/*  188: 270 */   private BigDecimal costoSalidaMaterial = BigDecimal.ZERO;
/*  189:     */   @Transient
/*  190: 273 */   private BigDecimal costoInicial = BigDecimal.ZERO;
/*  191:     */   @Transient
/*  192: 276 */   private BigDecimal costoIngresoProduccion = BigDecimal.ZERO;
/*  193:     */   @Transient
/*  194: 279 */   private BigDecimal costoUnitario = BigDecimal.ZERO;
/*  195:     */   
/*  196:     */   public ReporteStockValoradoResumido() {}
/*  197:     */   
/*  198:     */   public ReporteStockValoradoResumido(int idProducto, String codigoProducto, String nombreProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, int idBodega, String nombreBodega, BigDecimal recepcion, BigDecimal ajusteIngreso, BigDecimal transferenciaIngreso, BigDecimal devolucionCliente, BigDecimal ajusteEgreso, BigDecimal transferenciaEgreso, BigDecimal consumo, BigDecimal despacho, BigDecimal devolucionProvedor, int idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, BigDecimal costoPromedioAjusteIngreso, BigDecimal costoPromedioRecepcion, BigDecimal costoPromedioTransferenciaIngreso, BigDecimal costoPromedioDevolucion, BigDecimal costoPromedioAjusteEgreso, BigDecimal costoPromedioTransferenciaEgreso, BigDecimal costoPromedioConsumo, BigDecimal costoPromedioDespachos, BigDecimal costoPromedioDevolucionProveedor, BigDecimal precioDespacho, BigDecimal saldoDevolucionCliente)
/*  199:     */   {
/*  200: 336 */     this.idProducto = idProducto;
/*  201: 337 */     this.codigoProducto = codigoProducto;
/*  202: 338 */     this.nombreProducto = nombreProducto;
/*  203: 339 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/*  204: 340 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  205: 341 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/*  206: 342 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  207: 343 */     this.idBodega = idBodega;
/*  208: 344 */     this.nombreBodega = nombreBodega;
/*  209: 345 */     this.recepcion = recepcion;
/*  210: 346 */     this.ajusteIngreso = ajusteIngreso;
/*  211: 347 */     this.transferenciaIngreso = transferenciaIngreso;
/*  212: 348 */     this.devolucionCliente = devolucionCliente;
/*  213: 349 */     this.ajusteEgreso = ajusteEgreso;
/*  214: 350 */     this.transferenciaEgreso = transferenciaEgreso;
/*  215: 351 */     this.consumo = consumo;
/*  216: 352 */     this.despacho = despacho;
/*  217: 353 */     this.devolucionProvedor = devolucionProvedor;
/*  218:     */     
/*  219: 355 */     this.idUnidad = idUnidad;
/*  220: 356 */     this.codigoUnidad = codigoUnidad;
/*  221: 357 */     this.nombreUnidad = nombreUnidad;
/*  222:     */     
/*  223: 359 */     this.idUnidadVenta = idUnidadVenta;
/*  224: 360 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  225: 361 */     this.nombreUnidadVenta = nombreUnidadVenta;
/*  226:     */     
/*  227: 363 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/*  228: 364 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  229: 365 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/*  230:     */     
/*  231: 367 */     this.costoPromedioAjusteInventario = costoPromedioAjusteIngreso;
/*  232: 368 */     this.costoPromedioRecepcion = costoPromedioRecepcion;
/*  233: 369 */     this.costoPromedioTransferenciaIngreso = costoPromedioTransferenciaIngreso;
/*  234: 370 */     this.costoPromedioDevolucion = costoPromedioDevolucion;
/*  235: 371 */     this.costoPromedioAjusteEgreso = costoPromedioAjusteEgreso;
/*  236: 372 */     this.costoPromedioTransferenciaEgreso = costoPromedioTransferenciaEgreso;
/*  237: 373 */     this.costoPromedioConsumo = costoPromedioConsumo;
/*  238: 374 */     this.costoPromedioDespachos = costoPromedioDespachos;
/*  239: 375 */     this.costoPromedioDevolucionProveedor = costoPromedioDevolucionProveedor;
/*  240: 376 */     this.precioDespacho = precioDespacho;
/*  241:     */     
/*  242: 378 */     this.saldoDevolucionCliente = saldoDevolucionCliente;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public ReporteStockValoradoResumido(int idProducto, String codigoProducto, String nombreProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, int idBodega, String nombreBodega, BigDecimal recepcion, BigDecimal ajusteIngreso, BigDecimal transferenciaIngreso, BigDecimal devolucionCliente, BigDecimal ajusteEgreso, BigDecimal transferenciaEgreso, BigDecimal consumo, BigDecimal despacho, BigDecimal devolucionProvedor, int idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, BigDecimal devolucionClienteXAnulacion, BigDecimal devolucionProveedorXAnulacion, BigDecimal transformacionProducto, BigDecimal transformacionProductoXAnulacion, BigDecimal salidaMaterial, String codigoAlterno, BigDecimal costoRecepcion, BigDecimal costoAjusteIngreso, BigDecimal costoTransferenciaIngreso, BigDecimal costoDevolucionCliente, BigDecimal costoAjusteEgreso, BigDecimal costoTransferenciaEgreso, BigDecimal costoConsumo, BigDecimal costoDespacho, BigDecimal costoDevolucionProvedor, BigDecimal costoDevolucionClienteXAnulacion, BigDecimal costoDevolucionProveedorXAnulacion, BigDecimal costoTransformacionProducto, BigDecimal costoTransformacionProductoXAnulacion, BigDecimal costoSalidaMaterial, BigDecimal ingresoProduccion, BigDecimal costoIngresoProduccion)
/*  246:     */   {
/*  247: 403 */     this.idProducto = idProducto;
/*  248: 404 */     this.codigoProducto = codigoProducto;
/*  249: 405 */     this.nombreProducto = nombreProducto;
/*  250: 406 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/*  251: 407 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  252: 408 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/*  253: 409 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  254: 410 */     this.idBodega = idBodega;
/*  255: 411 */     this.nombreBodega = nombreBodega;
/*  256: 412 */     this.recepcion = recepcion;
/*  257: 413 */     this.ajusteIngreso = ajusteIngreso;
/*  258: 414 */     this.transferenciaIngreso = transferenciaIngreso;
/*  259: 415 */     this.devolucionCliente = devolucionCliente;
/*  260: 416 */     this.ajusteEgreso = ajusteEgreso;
/*  261: 417 */     this.transferenciaEgreso = transferenciaEgreso;
/*  262: 418 */     this.consumo = consumo;
/*  263: 419 */     this.despacho = despacho;
/*  264: 420 */     this.devolucionProvedor = devolucionProvedor;
/*  265:     */     
/*  266: 422 */     this.idUnidad = idUnidad;
/*  267: 423 */     this.codigoUnidad = codigoUnidad;
/*  268: 424 */     this.nombreUnidad = nombreUnidad;
/*  269:     */     
/*  270: 426 */     this.idUnidadVenta = idUnidadVenta;
/*  271: 427 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  272: 428 */     this.nombreUnidadVenta = nombreUnidadVenta;
/*  273:     */     
/*  274: 430 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/*  275: 431 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  276: 432 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/*  277:     */     
/*  278: 434 */     this.devolucionClienteXAnulacion = devolucionClienteXAnulacion;
/*  279: 435 */     this.devolucionProveedorXAnulacion = devolucionProveedorXAnulacion;
/*  280:     */     
/*  281: 437 */     this.transformacionProducto = transformacionProducto;
/*  282: 438 */     this.transformacionProductoXAnulacion = transformacionProductoXAnulacion;
/*  283: 439 */     this.salidaMaterial = salidaMaterial;
/*  284:     */     
/*  285: 441 */     this.codigoAlterno = codigoAlterno;
/*  286:     */     
/*  287: 443 */     this.costoRecepcion = costoRecepcion;
/*  288: 444 */     this.costoAjusteIngreso = costoAjusteIngreso;
/*  289: 445 */     this.costoTransferenciaIngreso = costoTransferenciaIngreso;
/*  290: 446 */     this.costoDevolucionCliente = costoDevolucionCliente;
/*  291: 447 */     this.costoAjusteEgreso = costoAjusteEgreso;
/*  292: 448 */     this.costoTransferenciaEgreso = costoTransferenciaEgreso;
/*  293: 449 */     this.costoConsumo = costoConsumo;
/*  294: 450 */     this.costoDespacho = costoDespacho;
/*  295: 451 */     this.costoDevolucionProvedor = costoDevolucionProvedor;
/*  296: 452 */     this.costoDevolucionClienteXAnulacion = costoDevolucionClienteXAnulacion;
/*  297: 453 */     this.costoDevolucionProveedorXAnulacion = costoDevolucionProveedorXAnulacion;
/*  298: 454 */     this.costoTransformacionProducto = costoTransformacionProducto;
/*  299: 455 */     this.costoTransformacionProductoXAnulacion = costoTransformacionProductoXAnulacion;
/*  300: 456 */     this.costoSalidaMaterial = costoSalidaMaterial;
/*  301: 457 */     this.costoIngresoProduccion = costoIngresoProduccion;
/*  302: 458 */     this.ingresoProduccion = ingresoProduccion;
/*  303:     */   }
/*  304:     */   
/*  305:     */   public ReporteStockValoradoResumido(int idProducto, String codigoProducto, String nombreProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, int idBodega, String nombreBodega, int idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento)
/*  306:     */   {
/*  307: 467 */     this.idProducto = idProducto;
/*  308: 468 */     this.codigoProducto = codigoProducto;
/*  309: 469 */     this.nombreProducto = nombreProducto;
/*  310: 470 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/*  311: 471 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  312: 472 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/*  313: 473 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  314: 474 */     this.idBodega = idBodega;
/*  315: 475 */     this.nombreBodega = nombreBodega;
/*  316:     */     
/*  317: 477 */     this.idUnidad = idUnidad;
/*  318: 478 */     this.codigoUnidad = codigoUnidad;
/*  319: 479 */     this.nombreUnidad = nombreUnidad;
/*  320:     */     
/*  321: 481 */     this.idUnidadVenta = idUnidadVenta;
/*  322: 482 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  323: 483 */     this.nombreUnidadVenta = nombreUnidadVenta;
/*  324:     */     
/*  325: 485 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/*  326: 486 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  327: 487 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public ReporteStockValoradoResumido(ReporteSaldoProducto rsp)
/*  331:     */   {
/*  332: 496 */     this(rsp.getIdProducto().intValue(), rsp.getCodigoProducto(), rsp.getNombreProducto(), rsp.getCodigoSubcategoriaProducto(), rsp
/*  333: 497 */       .getNombreSubcategoriaProducto(), rsp.getCodigoCategoriaProducto(), rsp.getNombreCategoriaProducto(), rsp.getIdBodega().intValue(), rsp
/*  334: 498 */       .getNombreBodega(), rsp.getIdUnidad().intValue(), rsp.getCodigoUnidad(), rsp.getNombreUnidad(), rsp.getIdUnidadVenta(), rsp
/*  335: 499 */       .getCodigoUnidadVenta(), rsp.getNombreUnidadVenta(), rsp.getIdUnidadAlmacenamiento(), rsp.getCodigoUnidadAlmacenamiento(), rsp
/*  336: 500 */       .getNombreUnidadAlmacenamiento());
/*  337:     */   }
/*  338:     */   
/*  339:     */   public int getIdReporteStockValoradoResumido()
/*  340:     */   {
/*  341: 504 */     return this.idReporteStockValoradoResumido;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void setIdReporteStockValoradoResumido(int idReporteStockValoradoResumido)
/*  345:     */   {
/*  346: 508 */     this.idReporteStockValoradoResumido = idReporteStockValoradoResumido;
/*  347:     */   }
/*  348:     */   
/*  349:     */   public int getIdProducto()
/*  350:     */   {
/*  351: 512 */     return this.idProducto;
/*  352:     */   }
/*  353:     */   
/*  354:     */   public void setIdProducto(int idProducto)
/*  355:     */   {
/*  356: 516 */     this.idProducto = idProducto;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public String getCodigoProducto()
/*  360:     */   {
/*  361: 520 */     return this.codigoProducto;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void setCodigoProducto(String codigoProducto)
/*  365:     */   {
/*  366: 524 */     this.codigoProducto = codigoProducto;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public String getNombreProducto()
/*  370:     */   {
/*  371: 528 */     return this.nombreProducto;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void setNombreProducto(String nombreProducto)
/*  375:     */   {
/*  376: 532 */     this.nombreProducto = nombreProducto;
/*  377:     */   }
/*  378:     */   
/*  379:     */   public String getCodigoSubcategoriaProducto()
/*  380:     */   {
/*  381: 536 */     return this.codigoSubcategoriaProducto;
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void setCodigoSubcategoriaProducto(String codigoSubcategoriaProducto)
/*  385:     */   {
/*  386: 540 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public String getNombreSubcategoriaProducto()
/*  390:     */   {
/*  391: 544 */     return this.nombreSubcategoriaProducto;
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void setNombreSubcategoriaProducto(String nombreSubcategoriaProducto)
/*  395:     */   {
/*  396: 548 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/*  397:     */   }
/*  398:     */   
/*  399:     */   public String getCodigoCategoriaProducto()
/*  400:     */   {
/*  401: 552 */     return this.codigoCategoriaProducto;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void setCodigoCategoriaProducto(String codigoCategoriaProducto)
/*  405:     */   {
/*  406: 556 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/*  407:     */   }
/*  408:     */   
/*  409:     */   public String getNombreCategoriaProducto()
/*  410:     */   {
/*  411: 560 */     return this.nombreCategoriaProducto;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public void setNombreCategoriaProducto(String nombreCategoriaProducto)
/*  415:     */   {
/*  416: 564 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public int getIdBodega()
/*  420:     */   {
/*  421: 568 */     return this.idBodega;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public void setIdBodega(int idBodega)
/*  425:     */   {
/*  426: 572 */     this.idBodega = idBodega;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public String getNombreBodega()
/*  430:     */   {
/*  431: 576 */     return this.nombreBodega;
/*  432:     */   }
/*  433:     */   
/*  434:     */   public void setNombreBodega(String nombreBodega)
/*  435:     */   {
/*  436: 580 */     this.nombreBodega = nombreBodega;
/*  437:     */   }
/*  438:     */   
/*  439:     */   public BigDecimal getRecepcion()
/*  440:     */   {
/*  441: 584 */     return this.recepcion;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void setRecepcion(BigDecimal recepcion)
/*  445:     */   {
/*  446: 588 */     this.recepcion = recepcion;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public BigDecimal getAjusteIngreso()
/*  450:     */   {
/*  451: 592 */     return this.ajusteIngreso;
/*  452:     */   }
/*  453:     */   
/*  454:     */   public void setAjusteIngreso(BigDecimal ajusteIngreso)
/*  455:     */   {
/*  456: 596 */     this.ajusteIngreso = ajusteIngreso;
/*  457:     */   }
/*  458:     */   
/*  459:     */   public BigDecimal getTransferenciaIngreso()
/*  460:     */   {
/*  461: 600 */     return this.transferenciaIngreso;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public void setTransferenciaIngreso(BigDecimal transferenciaIngreso)
/*  465:     */   {
/*  466: 604 */     this.transferenciaIngreso = transferenciaIngreso;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public BigDecimal getDevolucionCliente()
/*  470:     */   {
/*  471: 608 */     return this.devolucionCliente;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void setDevolucionCliente(BigDecimal devolucionCliente)
/*  475:     */   {
/*  476: 612 */     this.devolucionCliente = devolucionCliente;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public BigDecimal getAjusteEgreso()
/*  480:     */   {
/*  481: 616 */     return this.ajusteEgreso;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public void setAjusteEgreso(BigDecimal ajusteEgreso)
/*  485:     */   {
/*  486: 620 */     this.ajusteEgreso = ajusteEgreso;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public BigDecimal getTransferenciaEgreso()
/*  490:     */   {
/*  491: 624 */     return this.transferenciaEgreso;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void setTransferenciaEgreso(BigDecimal transferenciaEgreso)
/*  495:     */   {
/*  496: 628 */     this.transferenciaEgreso = transferenciaEgreso;
/*  497:     */   }
/*  498:     */   
/*  499:     */   public BigDecimal getConsumo()
/*  500:     */   {
/*  501: 632 */     return this.consumo;
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void setConsumo(BigDecimal consumo)
/*  505:     */   {
/*  506: 636 */     this.consumo = consumo;
/*  507:     */   }
/*  508:     */   
/*  509:     */   public BigDecimal getDespacho()
/*  510:     */   {
/*  511: 640 */     return this.despacho;
/*  512:     */   }
/*  513:     */   
/*  514:     */   public void setDespacho(BigDecimal despacho)
/*  515:     */   {
/*  516: 644 */     this.despacho = despacho;
/*  517:     */   }
/*  518:     */   
/*  519:     */   public BigDecimal getDevolucionProvedor()
/*  520:     */   {
/*  521: 648 */     return this.devolucionProvedor;
/*  522:     */   }
/*  523:     */   
/*  524:     */   public void setDevolucionProvedor(BigDecimal devolucionProvedor)
/*  525:     */   {
/*  526: 652 */     this.devolucionProvedor = devolucionProvedor;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public BigDecimal getSaldoInicial()
/*  530:     */   {
/*  531: 656 */     return this.saldoInicial;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void setSaldoInicial(BigDecimal saldoInicial)
/*  535:     */   {
/*  536: 660 */     this.saldoInicial = saldoInicial;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public BigDecimal getSaldoFinal()
/*  540:     */   {
/*  541: 664 */     return this.saldoFinal;
/*  542:     */   }
/*  543:     */   
/*  544:     */   public void setSaldoFinal(BigDecimal saldoFinal)
/*  545:     */   {
/*  546: 668 */     this.saldoFinal = saldoFinal;
/*  547:     */   }
/*  548:     */   
/*  549:     */   public int getIdUnidad()
/*  550:     */   {
/*  551: 672 */     return this.idUnidad;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void setIdUnidad(int idUnidad)
/*  555:     */   {
/*  556: 676 */     this.idUnidad = idUnidad;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public String getCodigoUnidad()
/*  560:     */   {
/*  561: 680 */     return this.codigoUnidad;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void setCodigoUnidad(String codigoUnidad)
/*  565:     */   {
/*  566: 684 */     this.codigoUnidad = codigoUnidad;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public String getNombreUnidad()
/*  570:     */   {
/*  571: 688 */     return this.nombreUnidad;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public void setNombreUnidad(String nombreUnidad)
/*  575:     */   {
/*  576: 692 */     this.nombreUnidad = nombreUnidad;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public Integer getIdUnidadVenta()
/*  580:     */   {
/*  581: 696 */     return this.idUnidadVenta;
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void setIdUnidadVenta(Integer idUnidadVenta)
/*  585:     */   {
/*  586: 700 */     this.idUnidadVenta = idUnidadVenta;
/*  587:     */   }
/*  588:     */   
/*  589:     */   public String getCodigoUnidadVenta()
/*  590:     */   {
/*  591: 704 */     return this.codigoUnidadVenta;
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void setCodigoUnidadVenta(String codigoUnidadVenta)
/*  595:     */   {
/*  596: 708 */     this.codigoUnidadVenta = codigoUnidadVenta;
/*  597:     */   }
/*  598:     */   
/*  599:     */   public String getNombreUnidadVenta()
/*  600:     */   {
/*  601: 712 */     return this.nombreUnidadVenta;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void setNombreUnidadVenta(String nombreUnidadVenta)
/*  605:     */   {
/*  606: 716 */     this.nombreUnidadVenta = nombreUnidadVenta;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public Integer getIdUnidadAlmacenamiento()
/*  610:     */   {
/*  611: 720 */     return this.idUnidadAlmacenamiento;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void setIdUnidadAlmacenamiento(Integer idUnidadAlmacenamiento)
/*  615:     */   {
/*  616: 724 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/*  617:     */   }
/*  618:     */   
/*  619:     */   public String getCodigoUnidadAlmacenamiento()
/*  620:     */   {
/*  621: 728 */     return this.codigoUnidadAlmacenamiento;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void setCodigoUnidadAlmacenamiento(String codigoUnidadAlmacenamiento)
/*  625:     */   {
/*  626: 732 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/*  627:     */   }
/*  628:     */   
/*  629:     */   public String getNombreUnidadAlmacenamiento()
/*  630:     */   {
/*  631: 736 */     return this.nombreUnidadAlmacenamiento;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void setNombreUnidadAlmacenamiento(String nombreUnidadAlmacenamiento)
/*  635:     */   {
/*  636: 740 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public BigDecimal getCostoPromedioAjusteInventario()
/*  640:     */   {
/*  641: 744 */     return this.costoPromedioAjusteInventario;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void setCostoPromedioAjusteInventario(BigDecimal costoPromedioAjusteInventario)
/*  645:     */   {
/*  646: 748 */     this.costoPromedioAjusteInventario = costoPromedioAjusteInventario;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public BigDecimal getCostoPromedioRecepcion()
/*  650:     */   {
/*  651: 752 */     return this.costoPromedioRecepcion;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public void setCostoPromedioRecepcion(BigDecimal costoPromedioRecepcion)
/*  655:     */   {
/*  656: 756 */     this.costoPromedioRecepcion = costoPromedioRecepcion;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public BigDecimal getCostoPromedioTransferenciaIngreso()
/*  660:     */   {
/*  661: 760 */     return this.costoPromedioTransferenciaIngreso;
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void setCostoPromedioTransferenciaIngreso(BigDecimal costoPromedioTransferenciaIngreso)
/*  665:     */   {
/*  666: 764 */     this.costoPromedioTransferenciaIngreso = costoPromedioTransferenciaIngreso;
/*  667:     */   }
/*  668:     */   
/*  669:     */   public BigDecimal getCostoPromedioDevolucion()
/*  670:     */   {
/*  671: 768 */     return this.costoPromedioDevolucion;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void setCostoPromedioDevolucion(BigDecimal costoPromedioDevolucion)
/*  675:     */   {
/*  676: 772 */     this.costoPromedioDevolucion = costoPromedioDevolucion;
/*  677:     */   }
/*  678:     */   
/*  679:     */   public BigDecimal getCostoPromedioAjusteEgreso()
/*  680:     */   {
/*  681: 776 */     return this.costoPromedioAjusteEgreso;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public void setCostoPromedioAjusteEgreso(BigDecimal costoPromedioAjusteEgreso)
/*  685:     */   {
/*  686: 780 */     this.costoPromedioAjusteEgreso = costoPromedioAjusteEgreso;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public BigDecimal getCostoPromedioTransferenciaEgreso()
/*  690:     */   {
/*  691: 784 */     return this.costoPromedioTransferenciaEgreso;
/*  692:     */   }
/*  693:     */   
/*  694:     */   public void setCostoPromedioTransferenciaEgreso(BigDecimal costoPromedioTransferenciaEgreso)
/*  695:     */   {
/*  696: 788 */     this.costoPromedioTransferenciaEgreso = costoPromedioTransferenciaEgreso;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public BigDecimal getCostoPromedioConsumo()
/*  700:     */   {
/*  701: 792 */     return this.costoPromedioConsumo;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void setCostoPromedioConsumo(BigDecimal costoPromedioConsumo)
/*  705:     */   {
/*  706: 796 */     this.costoPromedioConsumo = costoPromedioConsumo;
/*  707:     */   }
/*  708:     */   
/*  709:     */   public BigDecimal getCostoPromedioDespachos()
/*  710:     */   {
/*  711: 800 */     return this.costoPromedioDespachos;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void setCostoPromedioDespachos(BigDecimal costoPromedioDespachos)
/*  715:     */   {
/*  716: 804 */     this.costoPromedioDespachos = costoPromedioDespachos;
/*  717:     */   }
/*  718:     */   
/*  719:     */   public BigDecimal getCostoPromedioDevolucionProveedor()
/*  720:     */   {
/*  721: 808 */     return this.costoPromedioDevolucionProveedor;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void setCostoPromedioDevolucionProveedor(BigDecimal costoPromedioDevolucionProveedor)
/*  725:     */   {
/*  726: 812 */     this.costoPromedioDevolucionProveedor = costoPromedioDevolucionProveedor;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public BigDecimal getPrecioDespacho()
/*  730:     */   {
/*  731: 816 */     return this.precioDespacho;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public void setPrecioDespacho(BigDecimal precioDespacho)
/*  735:     */   {
/*  736: 820 */     this.precioDespacho = precioDespacho;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public BigDecimal getSaldoInicialEnPlata()
/*  740:     */   {
/*  741: 824 */     return this.saldoInicialEnPlata;
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void setSaldoInicialEnPlata(BigDecimal saldoInicialEnPlata)
/*  745:     */   {
/*  746: 828 */     this.saldoInicialEnPlata = saldoInicialEnPlata;
/*  747:     */   }
/*  748:     */   
/*  749:     */   public int getId()
/*  750:     */   {
/*  751: 838 */     return 0;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public BigDecimal getSaldoDevolucionCliente()
/*  755:     */   {
/*  756: 842 */     return this.saldoDevolucionCliente;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public void setSaldoDevolucionCliente(BigDecimal saldoDevolucionCliente)
/*  760:     */   {
/*  761: 846 */     this.saldoDevolucionCliente = saldoDevolucionCliente;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public BigDecimal getDevolucionClienteXAnulacion()
/*  765:     */   {
/*  766: 850 */     return this.devolucionClienteXAnulacion;
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void setDevolucionClienteXAnulacion(BigDecimal devolucionClienteXAnulacion)
/*  770:     */   {
/*  771: 854 */     this.devolucionClienteXAnulacion = devolucionClienteXAnulacion;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public BigDecimal getDevolucionProveedorXAnulacion()
/*  775:     */   {
/*  776: 858 */     return this.devolucionProveedorXAnulacion;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public void setDevolucionProveedorXAnulacion(BigDecimal devolucionProveedorXAnulacion)
/*  780:     */   {
/*  781: 862 */     this.devolucionProveedorXAnulacion = devolucionProveedorXAnulacion;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public BigDecimal getTransformacionProducto()
/*  785:     */   {
/*  786: 866 */     return this.transformacionProducto;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public void setTransformacionProducto(BigDecimal transformacionProducto)
/*  790:     */   {
/*  791: 870 */     this.transformacionProducto = transformacionProducto;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public BigDecimal getTransformacionProductoXAnulacion()
/*  795:     */   {
/*  796: 874 */     return this.transformacionProductoXAnulacion;
/*  797:     */   }
/*  798:     */   
/*  799:     */   public void setTransformacionProductoXAnulacion(BigDecimal transformacionProductoXAnulacion)
/*  800:     */   {
/*  801: 879 */     this.transformacionProductoXAnulacion = transformacionProductoXAnulacion;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public BigDecimal getSalidaMaterial()
/*  805:     */   {
/*  806: 883 */     return this.salidaMaterial;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public void setSalidaMaterial(BigDecimal salidaMaterial)
/*  810:     */   {
/*  811: 887 */     this.salidaMaterial = salidaMaterial;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public BigDecimal getIngresoProduccion()
/*  815:     */   {
/*  816: 891 */     return this.ingresoProduccion;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public void setIngresoProduccion(BigDecimal ingresoProduccion)
/*  820:     */   {
/*  821: 895 */     this.ingresoProduccion = ingresoProduccion;
/*  822:     */   }
/*  823:     */   
/*  824:     */   public String getCodigoAlterno()
/*  825:     */   {
/*  826: 899 */     return this.codigoAlterno;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public void setCodigoAlterno(String codigoAlterno)
/*  830:     */   {
/*  831: 903 */     this.codigoAlterno = codigoAlterno;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public BigDecimal getCostoRecepcion()
/*  835:     */   {
/*  836: 907 */     return this.costoRecepcion;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public void setCostoRecepcion(BigDecimal costoRecepcion)
/*  840:     */   {
/*  841: 911 */     this.costoRecepcion = costoRecepcion;
/*  842:     */   }
/*  843:     */   
/*  844:     */   public BigDecimal getCostoAjusteIngreso()
/*  845:     */   {
/*  846: 915 */     return this.costoAjusteIngreso;
/*  847:     */   }
/*  848:     */   
/*  849:     */   public void setCostoAjusteIngreso(BigDecimal costoAjusteIngreso)
/*  850:     */   {
/*  851: 919 */     this.costoAjusteIngreso = costoAjusteIngreso;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public BigDecimal getCostoTransferenciaIngreso()
/*  855:     */   {
/*  856: 923 */     return this.costoTransferenciaIngreso;
/*  857:     */   }
/*  858:     */   
/*  859:     */   public void setCostoTransferenciaIngreso(BigDecimal costoTransferenciaIngreso)
/*  860:     */   {
/*  861: 927 */     this.costoTransferenciaIngreso = costoTransferenciaIngreso;
/*  862:     */   }
/*  863:     */   
/*  864:     */   public BigDecimal getCostoDevolucionCliente()
/*  865:     */   {
/*  866: 931 */     return this.costoDevolucionCliente;
/*  867:     */   }
/*  868:     */   
/*  869:     */   public void setCostoDevolucionCliente(BigDecimal costoDevolucionCliente)
/*  870:     */   {
/*  871: 935 */     this.costoDevolucionCliente = costoDevolucionCliente;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public BigDecimal getCostoAjusteEgreso()
/*  875:     */   {
/*  876: 939 */     return this.costoAjusteEgreso;
/*  877:     */   }
/*  878:     */   
/*  879:     */   public void setCostoAjusteEgreso(BigDecimal costoAjusteEgreso)
/*  880:     */   {
/*  881: 943 */     this.costoAjusteEgreso = costoAjusteEgreso;
/*  882:     */   }
/*  883:     */   
/*  884:     */   public BigDecimal getCostoTransferenciaEgreso()
/*  885:     */   {
/*  886: 947 */     return this.costoTransferenciaEgreso;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void setCostoTransferenciaEgreso(BigDecimal costoTransferenciaEgreso)
/*  890:     */   {
/*  891: 951 */     this.costoTransferenciaEgreso = costoTransferenciaEgreso;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public BigDecimal getCostoConsumo()
/*  895:     */   {
/*  896: 955 */     return this.costoConsumo;
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void setCostoConsumo(BigDecimal costoConsumo)
/*  900:     */   {
/*  901: 959 */     this.costoConsumo = costoConsumo;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public BigDecimal getCostoDespacho()
/*  905:     */   {
/*  906: 963 */     return this.costoDespacho;
/*  907:     */   }
/*  908:     */   
/*  909:     */   public void setCostoDespacho(BigDecimal costoDespacho)
/*  910:     */   {
/*  911: 967 */     this.costoDespacho = costoDespacho;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public BigDecimal getCostoDevolucionProvedor()
/*  915:     */   {
/*  916: 971 */     return this.costoDevolucionProvedor;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public void setCostoDevolucionProvedor(BigDecimal costoDevolucionProvedor)
/*  920:     */   {
/*  921: 975 */     this.costoDevolucionProvedor = costoDevolucionProvedor;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public BigDecimal getCostoDevolucionClienteXAnulacion()
/*  925:     */   {
/*  926: 979 */     return this.costoDevolucionClienteXAnulacion;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public void setCostoDevolucionClienteXAnulacion(BigDecimal costoDevolucionClienteXAnulacion)
/*  930:     */   {
/*  931: 983 */     this.costoDevolucionClienteXAnulacion = costoDevolucionClienteXAnulacion;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public BigDecimal getCostoDevolucionProveedorXAnulacion()
/*  935:     */   {
/*  936: 987 */     return this.costoDevolucionProveedorXAnulacion;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public void setCostoDevolucionProveedorXAnulacion(BigDecimal costoDevolucionProveedorXAnulacion)
/*  940:     */   {
/*  941: 991 */     this.costoDevolucionProveedorXAnulacion = costoDevolucionProveedorXAnulacion;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public BigDecimal getCostoTransformacionProducto()
/*  945:     */   {
/*  946: 995 */     return this.costoTransformacionProducto;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void setCostoTransformacionProducto(BigDecimal costoTransformacionProducto)
/*  950:     */   {
/*  951: 999 */     this.costoTransformacionProducto = costoTransformacionProducto;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public BigDecimal getCostoTransformacionProductoXAnulacion()
/*  955:     */   {
/*  956:1003 */     return this.costoTransformacionProductoXAnulacion;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public void setCostoTransformacionProductoXAnulacion(BigDecimal costoTransformacionProductoXAnulacion)
/*  960:     */   {
/*  961:1007 */     this.costoTransformacionProductoXAnulacion = costoTransformacionProductoXAnulacion;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public BigDecimal getCostoSalidaMaterial()
/*  965:     */   {
/*  966:1011 */     return this.costoSalidaMaterial;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void setCostoSalidaMaterial(BigDecimal costoSalidaMaterial)
/*  970:     */   {
/*  971:1015 */     this.costoSalidaMaterial = costoSalidaMaterial;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public BigDecimal getCostoInicial()
/*  975:     */   {
/*  976:1019 */     return this.costoInicial;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public void setCostoInicial(BigDecimal costoInicial)
/*  980:     */   {
/*  981:1023 */     this.costoInicial = costoInicial;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public BigDecimal getCostoIngresoProduccion()
/*  985:     */   {
/*  986:1027 */     return this.costoIngresoProduccion;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public void setCostoIngresoProduccion(BigDecimal costoIngresoProduccion)
/*  990:     */   {
/*  991:1031 */     this.costoIngresoProduccion = costoIngresoProduccion;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public BigDecimal getCostoUnitario()
/*  995:     */   {
/*  996:1035 */     return this.costoUnitario;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public void setCostoUnitario(BigDecimal costoUnitario)
/* 1000:     */   {
/* 1001:1039 */     this.costoUnitario = costoUnitario;
/* 1002:     */   }
/* 1003:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteStockValoradoResumido
 * JD-Core Version:    0.7.0.1
 */