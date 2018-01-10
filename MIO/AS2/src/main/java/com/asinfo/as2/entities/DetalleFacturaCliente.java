/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.OneToOne;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.DecimalMin;
/*  25:    */ import javax.validation.constraints.Digits;
/*  26:    */ import javax.validation.constraints.Max;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ import org.hibernate.annotations.ColumnDefault;
/*  31:    */ 
/*  32:    */ @Entity
/*  33:    */ @Table(name="detalle_factura_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_factura_cliente"})}, indexes={@javax.persistence.Index(columnList="id_factura_cliente"), @javax.persistence.Index(columnList="id_detalle_despacho_cliente"), @javax.persistence.Index(columnList="id_producto")})
/*  34:    */ public class DetalleFacturaCliente
/*  35:    */   extends EntidadBase
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="detalle_factura_cliente", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_cliente")
/*  42:    */   @Column(name="id_detalle_factura_cliente")
/*  43:    */   private int idDetalleFacturaCliente;
/*  44:    */   @Column(name="id_sucursal", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private int idSucursal;
/*  47:    */   @Column(name="id_organizacion", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private int idOrganizacion;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/*  52:    */   private FacturaCliente facturaCliente;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_producto", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Producto producto;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_detalle_pedido_cliente", nullable=true)
/*  59:    */   private DetallePedidoCliente detallePedidoCliente;
/*  60:    */   @NotNull
/*  61:    */   @Column(name="cantidad", nullable=false, precision=12, scale=4)
/*  62:    */   @Digits(integer=12, fraction=4)
/*  63: 84 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  64:    */   @Column(name="indicador_manejo_peso", nullable=true)
/*  65:    */   private boolean indicadorManejoPeso;
/*  66:    */   @Column(name="peso", nullable=true, precision=12, scale=2)
/*  67:    */   @Digits(integer=12, fraction=2)
/*  68:    */   @Min(0L)
/*  69: 93 */   private BigDecimal peso = BigDecimal.ZERO;
/*  70:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  71:    */   @NotNull
/*  72:    */   @Digits(integer=12, fraction=6)
/*  73:    */   @Min(0L)
/*  74: 98 */   private BigDecimal precio = BigDecimal.ZERO;
/*  75:    */   @Column(name="indicador_porcentaje_ice", nullable=false)
/*  76:    */   @NotNull
/*  77:    */   @ColumnDefault("'0'")
/*  78:    */   private boolean indicadorPorcentajeIce;
/*  79:    */   @Column(name="ice", nullable=false, precision=12, scale=2)
/*  80:    */   @NotNull
/*  81:    */   @ColumnDefault("0")
/*  82:109 */   private BigDecimal ice = BigDecimal.ZERO;
/*  83:    */   @Column(name="codigo_ice", length=10, nullable=true)
/*  84:    */   @Size(max=10)
/*  85:114 */   private String codigoIce = "";
/*  86:    */   @Column(name="ice_linea", nullable=false, precision=12, scale=2)
/*  87:    */   @NotNull
/*  88:    */   @Digits(integer=12, fraction=2)
/*  89:    */   @Min(0L)
/*  90:    */   @ColumnDefault("0")
/*  91:118 */   private BigDecimal iceLinea = BigDecimal.ZERO;
/*  92:    */   @Column(name="precio_linea", nullable=false)
/*  93:    */   @NotNull
/*  94:    */   @Digits(integer=10, fraction=8)
/*  95:    */   @Min(0L)
/*  96:125 */   private BigDecimal precioLinea = BigDecimal.ZERO;
/*  97:    */   @Column(name="indicador_impuesto", nullable=false)
/*  98:    */   @NotNull
/*  99:    */   private boolean indicadorImpuesto;
/* 100:    */   @Column(name="descripcion", nullable=true, length=5000)
/* 101:    */   @Size(max=5000)
/* 102:    */   private String descripcion;
/* 103:    */   @NotNull
/* 104:    */   @Column(name="descuento", nullable=false, precision=12, scale=6)
/* 105:    */   @Digits(integer=12, fraction=6)
/* 106:    */   @Min(0L)
/* 107:139 */   private BigDecimal descuento = BigDecimal.ZERO;
/* 108:    */   @Column(name="descuento_linea", nullable=true)
/* 109:    */   @Digits(integer=10, fraction=8)
/* 110:    */   @Min(0L)
/* 111:145 */   private BigDecimal descuentoLinea = BigDecimal.ZERO;
/* 112:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/* 113:    */   private Integer idDispositivoSincronizacion;
/* 114:    */   @NotNull
/* 115:    */   @Column(name="porcentaje_descuento", nullable=false, precision=5, scale=4)
/* 116:    */   @Digits(integer=3, fraction=4)
/* 117:    */   @Min(0L)
/* 118:    */   @Max(100L)
/* 119:153 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/* 120:    */   @OneToOne(fetch=FetchType.LAZY)
/* 121:    */   @JoinColumn(name="id_detalle_despacho_cliente", nullable=true)
/* 122:    */   private DetalleDespachoCliente detalleDespachoCliente;
/* 123:    */   @OneToOne(fetch=FetchType.LAZY)
/* 124:    */   @JoinColumn(name="id_detalle_despacho_cliente_no_facturado", nullable=true)
/* 125:    */   private DetalleDespachoCliente detalleDespachoClienteNoFacturado;
/* 126:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 127:    */   @JoinColumn(name="id_detalle_factura_cliente_padre", nullable=true)
/* 128:    */   private DetalleFacturaCliente detalleFacturaClientePadre;
/* 129:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 130:    */   @JoinColumn(name="id_unidad_venta", nullable=false)
/* 131:    */   private Unidad unidadVenta;
/* 132:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaCliente")
/* 133:176 */   private List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaCliente = new ArrayList();
/* 134:    */   @OneToOne(mappedBy="detalleDevolucionCliente", fetch=FetchType.LAZY)
/* 135:    */   private InventarioProducto inventarioProducto;
/* 136:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 137:    */   @JoinColumn(name="id_empresa", nullable=true)
/* 138:    */   private Bodega bodega;
/* 139:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 140:    */   @JoinColumn(name="id_contrato_venta_factura_contrato_venta", nullable=true)
/* 141:    */   private ContratoVentaFacturaContratoVenta contratoVentaFacturaContratoVenta;
/* 142:    */   @Transient
/* 143:190 */   private BigDecimal cantidadPorDevolver = BigDecimal.ZERO;
/* 144:    */   @Transient
/* 145:    */   private BigDecimal traCantidadDetalleMovimiento;
/* 146:    */   @Transient
/* 147:    */   private boolean traIndicadorDetalleRadicaion;
/* 148:    */   @Transient
/* 149:    */   private BigDecimal saldo;
/* 150:    */   @Transient
/* 151:203 */   private BigDecimal cantidadADevolver = BigDecimal.ZERO;
/* 152:    */   @Column(name="nandina", nullable=true, length=200)
/* 153:    */   @Size(max=200)
/* 154:    */   private String nandina;
/* 155:    */   @NotNull
/* 156:    */   @Column(name="descuento_linea_NC", nullable=false, precision=12, scale=6)
/* 157:    */   @Digits(integer=12, fraction=6)
/* 158:    */   @Min(0L)
/* 159:212 */   private BigDecimal descuentoLineaNC = BigDecimal.ZERO;
/* 160:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 161:    */   @JoinColumn(name="id_empresa_bono", nullable=true)
/* 162:    */   private Empresa empresaBono;
/* 163:    */   @Column(name="indicador_bono", nullable=false)
/* 164:    */   private boolean indicadorBono;
/* 165:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 166:    */   @JoinColumn(name="id_medico", nullable=true)
/* 167:    */   private PersonaResponsable medico;
/* 168:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 169:    */   @JoinColumn(name="id_tipo_bono", nullable=true)
/* 170:    */   private Especialidad tipoBono;
/* 171:    */   @Column(name="referencia", nullable=true, length=50)
/* 172:    */   private String referencia;
/* 173:    */   @Column(name="estado", nullable=false)
/* 174:    */   @Enumerated(EnumType.ORDINAL)
/* 175:239 */   private Estado estado = Estado.ELABORADO;
/* 176:    */   @DecimalMin("0.00")
/* 177:    */   @Column(name="subsidio", nullable=false, precision=12, scale=6)
/* 178:    */   @NotNull
/* 179:245 */   private BigDecimal subsidio = BigDecimal.ZERO;
/* 180:    */   
/* 181:    */   public DetalleFacturaCliente() {}
/* 182:    */   
/* 183:    */   public int getId()
/* 184:    */   {
/* 185:263 */     return this.idDetalleFacturaCliente;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DetalleFacturaCliente(int idDetalleFacturaCliente, Producto producto, BigDecimal cantidad, BigDecimal precio, BigDecimal cantidadPorDevolver, BigDecimal descuento, BigDecimal traCantidadDetalleMovimiento)
/* 189:    */   {
/* 190:277 */     this.idDetalleFacturaCliente = idDetalleFacturaCliente;
/* 191:278 */     this.producto = producto;
/* 192:279 */     this.cantidad = cantidad;
/* 193:280 */     this.precio = precio;
/* 194:281 */     this.cantidadPorDevolver = cantidadPorDevolver;
/* 195:282 */     this.descuento = descuento;
/* 196:283 */     this.traCantidadDetalleMovimiento = traCantidadDetalleMovimiento;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public DetalleFacturaCliente(Integer idOrganizacion, String usuarioCreacion, String usuarioModificacion, Date fechaCreacion, Date fechaModificacion, Integer idDetalleFacturaCliente, String referencia, Estado estado, Integer idFacturaCliente, String numero, Estado estadoFactura, String empresaNombreFiscal, String empresaNombreComercial, Integer idEmpresa, Date fechaFactura, BigDecimal precio, BigDecimal descuento, BigDecimal precioLinea)
/* 200:    */   {
/* 201:291 */     this.idOrganizacion = idOrganizacion.intValue();
/* 202:292 */     this.usuarioCreacion = usuarioCreacion;
/* 203:293 */     this.usuarioModificacion = usuarioModificacion;
/* 204:294 */     this.fechaCreacion = fechaCreacion;
/* 205:295 */     this.fechaModificacion = fechaModificacion;
/* 206:296 */     this.idDetalleFacturaCliente = idDetalleFacturaCliente.intValue();
/* 207:297 */     this.referencia = referencia;
/* 208:298 */     this.estado = estado;
/* 209:299 */     this.precio = precio;
/* 210:300 */     this.descuento = descuento;
/* 211:301 */     this.precioLinea = precioLinea;
/* 212:303 */     if (idEmpresa != null)
/* 213:    */     {
/* 214:304 */       Empresa empresa = new Empresa();
/* 215:305 */       empresa.setIdEmpresa(idEmpresa.intValue());
/* 216:306 */       empresa.setNombreFiscal(empresaNombreFiscal);
/* 217:307 */       empresa.setNombreComercial(empresaNombreComercial);
/* 218:308 */       this.empresaBono = empresa;
/* 219:    */     }
/* 220:311 */     if (idFacturaCliente != null)
/* 221:    */     {
/* 222:312 */       FacturaCliente facturaCliente = new FacturaCliente();
/* 223:313 */       facturaCliente.setIdFacturaCliente(idFacturaCliente.intValue());
/* 224:314 */       facturaCliente.setNumero(numero);
/* 225:315 */       facturaCliente.setEstado(estadoFactura);
/* 226:316 */       facturaCliente.setFecha(fechaFactura);
/* 227:317 */       this.facturaCliente = facturaCliente;
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getIdDetalleFacturaCliente()
/* 232:    */   {
/* 233:329 */     return this.idDetalleFacturaCliente;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIdDetalleFacturaCliente(int idDetalleFacturaCliente)
/* 237:    */   {
/* 238:339 */     this.idDetalleFacturaCliente = idDetalleFacturaCliente;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public int getIdSucursal()
/* 242:    */   {
/* 243:348 */     return this.idSucursal;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setIdSucursal(int idSucursal)
/* 247:    */   {
/* 248:358 */     this.idSucursal = idSucursal;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public int getIdOrganizacion()
/* 252:    */   {
/* 253:367 */     return this.idOrganizacion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setIdOrganizacion(int idOrganizacion)
/* 257:    */   {
/* 258:377 */     this.idOrganizacion = idOrganizacion;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public FacturaCliente getFacturaCliente()
/* 262:    */   {
/* 263:386 */     return this.facturaCliente;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 267:    */   {
/* 268:396 */     this.facturaCliente = facturaCliente;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Producto getProducto()
/* 272:    */   {
/* 273:405 */     return this.producto;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setProducto(Producto producto)
/* 277:    */   {
/* 278:415 */     this.producto = producto;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public DetallePedidoCliente getDetallePedidoCliente()
/* 282:    */   {
/* 283:424 */     return this.detallePedidoCliente;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setDetallePedidoCliente(DetallePedidoCliente detallePedidoCliente)
/* 287:    */   {
/* 288:434 */     this.detallePedidoCliente = detallePedidoCliente;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public BigDecimal getCantidad()
/* 292:    */   {
/* 293:443 */     return this.cantidad;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setCantidad(BigDecimal cantidad)
/* 297:    */   {
/* 298:453 */     this.cantidad = cantidad;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public BigDecimal getPrecio()
/* 302:    */   {
/* 303:464 */     return this.precio;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setPrecio(BigDecimal precio)
/* 307:    */   {
/* 308:474 */     this.precio = precio;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public boolean isIndicadorImpuesto()
/* 312:    */   {
/* 313:483 */     return this.indicadorImpuesto;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setIndicadorImpuesto(boolean indicadorImpuesto)
/* 317:    */   {
/* 318:493 */     this.indicadorImpuesto = indicadorImpuesto;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String getDescripcion()
/* 322:    */   {
/* 323:502 */     return this.descripcion;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setDescripcion(String descripcion)
/* 327:    */   {
/* 328:512 */     this.descripcion = descripcion;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public BigDecimal getDescuento()
/* 332:    */   {
/* 333:521 */     return this.descuento == null ? BigDecimal.ZERO : this.descuento;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setDescuento(BigDecimal descuento)
/* 337:    */   {
/* 338:531 */     this.descuento = descuento;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public List<ImpuestoProductoFacturaCliente> getListaImpuestoProductoFacturaCliente()
/* 342:    */   {
/* 343:540 */     return this.listaImpuestoProductoFacturaCliente;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setListaImpuestoProductoFacturaCliente(List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaCliente)
/* 347:    */   {
/* 348:550 */     this.listaImpuestoProductoFacturaCliente = listaImpuestoProductoFacturaCliente;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public DetalleDespachoCliente getDetalleDespachoCliente()
/* 352:    */   {
/* 353:559 */     return this.detalleDespachoCliente;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setDetalleDespachoCliente(DetalleDespachoCliente detalleDespachoCliente)
/* 357:    */   {
/* 358:569 */     this.detalleDespachoCliente = detalleDespachoCliente;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public BigDecimal getDescuentoLinea()
/* 362:    */   {
/* 363:573 */     return this.descuentoLinea;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setDescuentoLinea(BigDecimal descuentoLinea)
/* 367:    */   {
/* 368:577 */     this.descuentoLinea = descuentoLinea;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public BigDecimal getPrecioLinea()
/* 372:    */   {
/* 373:581 */     return this.precioLinea;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setPrecioLinea(BigDecimal precioLinea)
/* 377:    */   {
/* 378:585 */     this.precioLinea = precioLinea;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public BigDecimal getBaseImponible()
/* 382:    */   {
/* 383:594 */     return getPrecioLinea().subtract(getDescuentoLinea()).add(getIceLinea());
/* 384:    */   }
/* 385:    */   
/* 386:    */   public BigDecimal getValorImpuestosLinea()
/* 387:    */   {
/* 388:603 */     BigDecimal valorImpuestoLinea = BigDecimal.ZERO;
/* 389:605 */     for (ImpuestoProductoFacturaCliente ipfc : getListaImpuestoProductoFacturaCliente()) {
/* 390:606 */       if (!ipfc.isEliminado()) {
/* 391:607 */         valorImpuestoLinea = valorImpuestoLinea.add(ipfc.getImpuestoProducto());
/* 392:    */       }
/* 393:    */     }
/* 394:611 */     return valorImpuestoLinea;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public BigDecimal getValorImpuestosTributariosLinea()
/* 398:    */   {
/* 399:615 */     BigDecimal valorImpuestoLinea = BigDecimal.ZERO;
/* 400:617 */     for (ImpuestoProductoFacturaCliente ipfc : getListaImpuestoProductoFacturaCliente()) {
/* 401:618 */       if ((!ipfc.isEliminado()) && (ipfc.getImpuesto().isIndicadorImpuestoTributario())) {
/* 402:619 */         valorImpuestoLinea = valorImpuestoLinea.add(ipfc.getImpuestoProducto());
/* 403:    */       }
/* 404:    */     }
/* 405:623 */     return valorImpuestoLinea;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public BigDecimal getTraCantidadDetalleMovimiento()
/* 409:    */   {
/* 410:632 */     if (this.traCantidadDetalleMovimiento == null) {
/* 411:633 */       this.traCantidadDetalleMovimiento = BigDecimal.ZERO;
/* 412:    */     }
/* 413:635 */     return this.traCantidadDetalleMovimiento;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setTraCantidadDetalleMovimiento(BigDecimal traCantidadDetalleMovimiento)
/* 417:    */   {
/* 418:645 */     this.traCantidadDetalleMovimiento = traCantidadDetalleMovimiento;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public BigDecimal getPorcentajeDescuento()
/* 422:    */   {
/* 423:654 */     return this.porcentajeDescuento == null ? BigDecimal.ZERO : this.porcentajeDescuento;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/* 427:    */   {
/* 428:664 */     this.porcentajeDescuento = porcentajeDescuento;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public DetalleFacturaCliente getDetalleFacturaClientePadre()
/* 432:    */   {
/* 433:673 */     return this.detalleFacturaClientePadre;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setDetalleFacturaClientePadre(DetalleFacturaCliente detalleFacturaClientePadre)
/* 437:    */   {
/* 438:683 */     this.detalleFacturaClientePadre = detalleFacturaClientePadre;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public BigDecimal getCantidadPorDevolver()
/* 442:    */   {
/* 443:692 */     return this.cantidadPorDevolver;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setCantidadPorDevolver(BigDecimal cantidadPorDevolver)
/* 447:    */   {
/* 448:702 */     this.cantidadPorDevolver = cantidadPorDevolver;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public Unidad getUnidadVenta()
/* 452:    */   {
/* 453:711 */     return this.unidadVenta;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setUnidadVenta(Unidad unidadVenta)
/* 457:    */   {
/* 458:721 */     this.unidadVenta = unidadVenta;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public boolean isTraIndicadorDetalleRadicaion()
/* 462:    */   {
/* 463:730 */     return this.traIndicadorDetalleRadicaion;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setTraIndicadorDetalleRadicaion(boolean traIndicadorDetalleRadicaion)
/* 467:    */   {
/* 468:740 */     this.traIndicadorDetalleRadicaion = traIndicadorDetalleRadicaion;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public InventarioProducto getInventarioProducto()
/* 472:    */   {
/* 473:749 */     return this.inventarioProducto;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 477:    */   {
/* 478:759 */     this.inventarioProducto = inventarioProducto;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public Bodega getBodega()
/* 482:    */   {
/* 483:768 */     return this.bodega;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setBodega(Bodega bodega)
/* 487:    */   {
/* 488:778 */     this.bodega = bodega;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public BigDecimal getSaldo()
/* 492:    */   {
/* 493:787 */     return this.saldo;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setSaldo(BigDecimal saldo)
/* 497:    */   {
/* 498:797 */     this.saldo = saldo;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public BigDecimal getPeso()
/* 502:    */   {
/* 503:801 */     return this.peso;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setPeso(BigDecimal peso)
/* 507:    */   {
/* 508:805 */     this.peso = peso;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public boolean isIndicadorManejoPeso()
/* 512:    */   {
/* 513:809 */     return this.indicadorManejoPeso;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setIndicadorManejoPeso(boolean indicadorManejoPeso)
/* 517:    */   {
/* 518:813 */     this.indicadorManejoPeso = indicadorManejoPeso;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public BigDecimal getCantidadADevolver()
/* 522:    */   {
/* 523:817 */     return this.cantidadADevolver;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setCantidadADevolver(BigDecimal cantidadADevolver)
/* 527:    */   {
/* 528:821 */     this.cantidadADevolver = cantidadADevolver;
/* 529:822 */     setCantidad(this.cantidadADevolver);
/* 530:    */   }
/* 531:    */   
/* 532:    */   public String getNandina()
/* 533:    */   {
/* 534:829 */     return this.nandina;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void setNandina(String nandina)
/* 538:    */   {
/* 539:837 */     this.nandina = nandina;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public ContratoVentaFacturaContratoVenta getContratoVentaFacturaContratoVenta()
/* 543:    */   {
/* 544:841 */     return this.contratoVentaFacturaContratoVenta;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public void setContratoVentaFacturaContratoVenta(ContratoVentaFacturaContratoVenta contratoVentaFacturaContratoVenta)
/* 548:    */   {
/* 549:845 */     this.contratoVentaFacturaContratoVenta = contratoVentaFacturaContratoVenta;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public BigDecimal getDescuentoLineaNC()
/* 553:    */   {
/* 554:849 */     return this.descuentoLineaNC;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void setDescuentoLineaNC(BigDecimal descuentoLineaNC)
/* 558:    */   {
/* 559:853 */     this.descuentoLineaNC = descuentoLineaNC;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public Empresa getEmpresaBono()
/* 563:    */   {
/* 564:857 */     return this.empresaBono;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setEmpresaBono(Empresa empresaBono)
/* 568:    */   {
/* 569:861 */     this.empresaBono = empresaBono;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public boolean isIndicadorBono()
/* 573:    */   {
/* 574:865 */     return this.indicadorBono;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void setIndicadorBono(boolean indicadorBono)
/* 578:    */   {
/* 579:869 */     this.indicadorBono = indicadorBono;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public PersonaResponsable getMedico()
/* 583:    */   {
/* 584:873 */     return this.medico;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public void setMedico(PersonaResponsable medico)
/* 588:    */   {
/* 589:877 */     this.medico = medico;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public Especialidad getTipoBono()
/* 593:    */   {
/* 594:881 */     return this.tipoBono;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public void setTipoBono(Especialidad tipoBono)
/* 598:    */   {
/* 599:885 */     this.tipoBono = tipoBono;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public String getReferencia()
/* 603:    */   {
/* 604:889 */     return this.referencia;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public void setReferencia(String referencia)
/* 608:    */   {
/* 609:893 */     this.referencia = referencia;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public BigDecimal getSubsidio()
/* 613:    */   {
/* 614:897 */     return this.subsidio;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public void setSubsidio(BigDecimal subsidio)
/* 618:    */   {
/* 619:901 */     this.subsidio = subsidio;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public Estado getEstado()
/* 623:    */   {
/* 624:905 */     return this.estado;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public void setEstado(Estado estado)
/* 628:    */   {
/* 629:909 */     this.estado = estado;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public BigDecimal getIceLinea()
/* 633:    */   {
/* 634:913 */     return this.iceLinea;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public void setIceLinea(BigDecimal iceLinea)
/* 638:    */   {
/* 639:917 */     this.iceLinea = iceLinea;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public boolean isIndicadorPorcentajeIce()
/* 643:    */   {
/* 644:921 */     return this.indicadorPorcentajeIce;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public void setIndicadorPorcentajeIce(boolean indicadorPorcentajeIce)
/* 648:    */   {
/* 649:925 */     this.indicadorPorcentajeIce = indicadorPorcentajeIce;
/* 650:    */   }
/* 651:    */   
/* 652:    */   public BigDecimal getIce()
/* 653:    */   {
/* 654:929 */     return this.ice;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public void setIce(BigDecimal ice)
/* 658:    */   {
/* 659:933 */     this.ice = ice;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public String getCodigoIce()
/* 663:    */   {
/* 664:937 */     return this.codigoIce;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public void setCodigoIce(String codigoIce)
/* 668:    */   {
/* 669:941 */     this.codigoIce = codigoIce;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public DetalleDespachoCliente getDetalleDespachoClienteNoFacturado()
/* 673:    */   {
/* 674:945 */     return this.detalleDespachoClienteNoFacturado;
/* 675:    */   }
/* 676:    */   
/* 677:    */   public void setDetalleDespachoClienteNoFacturado(DetalleDespachoCliente detalleDespachoClienteNoFacturado)
/* 678:    */   {
/* 679:949 */     this.detalleDespachoClienteNoFacturado = detalleDespachoClienteNoFacturado;
/* 680:    */   }
/* 681:    */   
/* 682:    */   public Integer getIdDispositivoSincronizacion()
/* 683:    */   {
/* 684:953 */     return this.idDispositivoSincronizacion;
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 688:    */   {
/* 689:957 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 690:    */   }
/* 691:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFacturaCliente
 * JD-Core Version:    0.7.0.1
 */