/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.OneToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.DecimalMin;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ import org.hibernate.annotations.Fetch;
/*  23:    */ import org.hibernate.annotations.FetchMode;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_despacho_cliente", indexes={@javax.persistence.Index(columnList="id_detalle_despacho_cliente"), @javax.persistence.Index(columnList="id_producto")})
/*  27:    */ public class DetalleDespachoCliente
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 6041597607233226319L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_despacho_cliente", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_despacho_cliente")
/*  35:    */   @Column(name="id_detalle_despacho_cliente", unique=true, nullable=false)
/*  36:    */   private int idDetalleDespachoCliente;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_despacho_cliente", nullable=true)
/*  39:    */   private DespachoCliente despachoCliente;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_producto", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Producto producto;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  46:    */   private Bodega bodega;
/*  47:    */   @Column(name="id_organizacion", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private int idOrganizacion;
/*  50:    */   @Column(name="id_sucursal", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private int idSucursal;
/*  53:    */   @Column(name="cantidad", nullable=true, precision=12, scale=4)
/*  54:    */   @Digits(integer=12, fraction=4)
/*  55:    */   @DecimalMin("0.0001")
/*  56: 79 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  57:    */   @Column(name="indicador_manejo_peso", nullable=true)
/*  58:    */   private boolean indicadorManejoPeso;
/*  59:    */   @Column(name="peso", nullable=true, precision=12, scale=2)
/*  60:    */   @Digits(integer=12, fraction=2)
/*  61:    */   @Min(0L)
/*  62: 87 */   private BigDecimal peso = BigDecimal.ZERO;
/*  63:    */   @Column(name="descripcion", nullable=true, length=5000)
/*  64:    */   @Size(max=5000)
/*  65:    */   private String descripcion;
/*  66:    */   @OneToOne(mappedBy="detalleDespachoCliente", fetch=FetchType.LAZY)
/*  67:    */   private DetalleFacturaCliente detalleFacturaCliente;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_detalle_pedido_cliente", nullable=true)
/*  70:    */   private DetallePedidoCliente detallePedidoCliente;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_unidad_venta", nullable=false)
/*  73:    */   private Unidad unidadVenta;
/*  74:    */   @OneToOne(mappedBy="detalleDespachoCliente", fetch=FetchType.LAZY)
/*  75:    */   @Fetch(FetchMode.JOIN)
/*  76:    */   private InventarioProducto inventarioProducto;
/*  77:    */   @OneToOne(mappedBy="detalleDespachoClienteAjuste", fetch=FetchType.LAZY)
/*  78:    */   private InventarioProducto inventarioProductoAjuste;
/*  79:    */   @Column(name="cantidad_devuelta", nullable=true, precision=12, scale=4)
/*  80:    */   @Digits(integer=10, fraction=4)
/*  81:    */   @Min(0L)
/*  82:116 */   private BigDecimal cantidadDevuelta = BigDecimal.ZERO;
/*  83:    */   @Column(name="cantidad_embalaje", nullable=true, precision=12, scale=2)
/*  84:    */   @Digits(integer=10, fraction=2)
/*  85:    */   @Min(0L)
/*  86:121 */   private BigDecimal cantidadEmbalaje = BigDecimal.ONE;
/*  87:    */   @Column(name="peso_materia_prima", nullable=true, precision=38, scale=20)
/*  88:    */   @Digits(integer=18, fraction=20)
/*  89:    */   @DecimalMin("0.00")
/*  90:126 */   private BigDecimal pesoMateriaPrima = BigDecimal.ZERO;
/*  91:    */   @Transient
/*  92:131 */   private BigDecimal cantidadPorDevolver = BigDecimal.ZERO;
/*  93:    */   @Transient
/*  94:    */   private BigDecimal saldo;
/*  95:    */   @Transient
/*  96:    */   private boolean indicadorProyecto;
/*  97:    */   @Column(name="codigo_lote", nullable=true, length=100)
/*  98:    */   @Size(max=100)
/*  99:    */   private String codigoLote;
/* 100:    */   
/* 101:    */   public DetallePedidoCliente getDetallePedidoCliente()
/* 102:    */   {
/* 103:147 */     return this.detallePedidoCliente;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDetallePedidoCliente(DetallePedidoCliente detallePedidoCliente)
/* 107:    */   {
/* 108:151 */     this.detallePedidoCliente = detallePedidoCliente;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int getId()
/* 112:    */   {
/* 113:156 */     return this.idDetalleDespachoCliente;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getIdDetalleDespachoCliente()
/* 117:    */   {
/* 118:160 */     return this.idDetalleDespachoCliente;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdDetalleDespachoCliente(int idDetalleDespachoCliente)
/* 122:    */   {
/* 123:164 */     this.idDetalleDespachoCliente = idDetalleDespachoCliente;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public DespachoCliente getDespachoCliente()
/* 127:    */   {
/* 128:168 */     return this.despachoCliente;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDespachoCliente(DespachoCliente despachoCliente)
/* 132:    */   {
/* 133:172 */     this.despachoCliente = despachoCliente;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Producto getProducto()
/* 137:    */   {
/* 138:176 */     return this.producto;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setProducto(Producto producto)
/* 142:    */   {
/* 143:180 */     this.producto = producto;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Bodega getBodega()
/* 147:    */   {
/* 148:184 */     return this.bodega;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setBodega(Bodega bodega)
/* 152:    */   {
/* 153:188 */     this.bodega = bodega;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getIdOrganizacion()
/* 157:    */   {
/* 158:192 */     return this.idOrganizacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setIdOrganizacion(int idOrganizacion)
/* 162:    */   {
/* 163:196 */     this.idOrganizacion = idOrganizacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getIdSucursal()
/* 167:    */   {
/* 168:200 */     return this.idSucursal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setIdSucursal(int idSucursal)
/* 172:    */   {
/* 173:204 */     this.idSucursal = idSucursal;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal getCantidad()
/* 177:    */   {
/* 178:208 */     return this.cantidad;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setCantidad(BigDecimal cantidad)
/* 182:    */   {
/* 183:212 */     this.cantidad = cantidad;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getDescripcion()
/* 187:    */   {
/* 188:216 */     return this.descripcion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDescripcion(String descripcion)
/* 192:    */   {
/* 193:220 */     this.descripcion = descripcion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public DetalleFacturaCliente getDetalleFacturaCliente()
/* 197:    */   {
/* 198:229 */     return this.detalleFacturaCliente;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDetalleFacturaCliente(DetalleFacturaCliente detalleFacturaCliente)
/* 202:    */   {
/* 203:239 */     this.detalleFacturaCliente = detalleFacturaCliente;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public InventarioProducto getInventarioProducto()
/* 207:    */   {
/* 208:246 */     return this.inventarioProducto;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 212:    */   {
/* 213:254 */     this.inventarioProducto = inventarioProducto;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Unidad getUnidadVenta()
/* 217:    */   {
/* 218:263 */     return this.unidadVenta;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setUnidadVenta(Unidad unidadVenta)
/* 222:    */   {
/* 223:273 */     this.unidadVenta = unidadVenta;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setEliminado(boolean eliminado)
/* 227:    */   {
/* 228:278 */     super.setEliminado(eliminado);
/* 229:279 */     if (this.inventarioProducto != null) {
/* 230:280 */       this.inventarioProducto.setEliminado(eliminado);
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public BigDecimal getCantidadDevuelta()
/* 235:    */   {
/* 236:290 */     return this.cantidadDevuelta;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setCantidadDevuelta(BigDecimal cantidadDevuelta)
/* 240:    */   {
/* 241:300 */     this.cantidadDevuelta = cantidadDevuelta;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public BigDecimal getCantidadPorDevolver()
/* 245:    */   {
/* 246:309 */     this.cantidadPorDevolver = this.cantidad.subtract(this.cantidadDevuelta);
/* 247:310 */     return this.cantidadPorDevolver;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setCantidadPorDevolver(BigDecimal cantidadPorDevolver)
/* 251:    */   {
/* 252:320 */     this.cantidadPorDevolver = cantidadPorDevolver;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public BigDecimal getSaldo()
/* 256:    */   {
/* 257:329 */     return this.saldo;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setSaldo(BigDecimal saldo)
/* 261:    */   {
/* 262:339 */     this.saldo = saldo;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public InventarioProducto getInventarioProductoAjuste()
/* 266:    */   {
/* 267:348 */     return this.inventarioProductoAjuste;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setInventarioProductoAjuste(InventarioProducto inventarioProductoAjuste)
/* 271:    */   {
/* 272:358 */     this.inventarioProductoAjuste = inventarioProductoAjuste;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean isIndicadorManejoPeso()
/* 276:    */   {
/* 277:362 */     return this.indicadorManejoPeso;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setIndicadorManejoPeso(boolean indicadorManejoPeso)
/* 281:    */   {
/* 282:366 */     this.indicadorManejoPeso = indicadorManejoPeso;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public BigDecimal getPeso()
/* 286:    */   {
/* 287:370 */     return this.peso;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setPeso(BigDecimal peso)
/* 291:    */   {
/* 292:374 */     this.peso = peso;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isIndicadorProyecto()
/* 296:    */   {
/* 297:378 */     return this.indicadorProyecto;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setIndicadorProyecto(boolean indicadorProyecto)
/* 301:    */   {
/* 302:382 */     this.indicadorProyecto = indicadorProyecto;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public BigDecimal getCantidadEmbalaje()
/* 306:    */   {
/* 307:386 */     if ((this.cantidadEmbalaje == null) || (this.cantidadEmbalaje.compareTo(BigDecimal.ZERO) == 0)) {
/* 308:387 */       this.cantidadEmbalaje = BigDecimal.ONE;
/* 309:    */     }
/* 310:389 */     return this.cantidadEmbalaje;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setCantidadEmbalaje(BigDecimal cantidadEmbalaje)
/* 314:    */   {
/* 315:393 */     this.cantidadEmbalaje = cantidadEmbalaje;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public BigDecimal getPesoMateriaPrima()
/* 319:    */   {
/* 320:397 */     return this.pesoMateriaPrima;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setPesoMateriaPrima(BigDecimal pesoMateriaPrima)
/* 324:    */   {
/* 325:401 */     this.pesoMateriaPrima = pesoMateriaPrima;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getCodigoLote()
/* 329:    */   {
/* 330:405 */     return this.codigoLote;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setCodigoLote(String codigoLote)
/* 334:    */   {
/* 335:409 */     this.codigoLote = codigoLote;
/* 336:    */   }
/* 337:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleDespachoCliente
 * JD-Core Version:    0.7.0.1
 */