/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
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
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_recepcion_proveedor")
/*  27:    */ public class DetalleRecepcionProveedor
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_recepcion_proveedor", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_recepcion_proveedor")
/*  35:    */   @Column(name="id_detalle_recepcion_proveedor", unique=true, nullable=false)
/*  36:    */   private int idDetalleRecepcionProveedor;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_recepcion_proveedor", nullable=true)
/*  39:    */   private RecepcionProveedor recepcionProveedor;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_producto", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Producto producto;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Bodega bodega;
/*  48:    */   @Column(name="id_organizacion", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private int idOrganizacion;
/*  51:    */   @Column(name="id_sucursal", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private int idSucursal;
/*  54:    */   @NotNull
/*  55:    */   @Column(name="cantidad", nullable=true, precision=12, scale=4)
/*  56:    */   @Digits(integer=12, fraction=4)
/*  57: 77 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  58:    */   @Column(name="cantidad_unidad_informativa", nullable=true, precision=12, scale=2)
/*  59:    */   @Digits(integer=12, fraction=2)
/*  60:    */   @Min(0L)
/*  61:    */   private BigDecimal cantidadUnidadInformativa;
/*  62:    */   @Column(name="descripcion", nullable=true, length=200)
/*  63:    */   @Size(max=200)
/*  64:    */   private String descripcion;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_detalle_pedido_proveedor", nullable=true)
/*  67:    */   private DetallePedidoProveedor detallePedidoProveedor;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_unidad_compra", nullable=false)
/*  70:    */   private Unidad unidadCompra;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_detalle_factura_proveedor", nullable=true)
/*  73:    */   private DetalleFacturaProveedor detalleFacturaProveedor;
/*  74:    */   @OneToOne(mappedBy="detalleRecepcionProveedor", fetch=FetchType.LAZY)
/*  75:    */   private InventarioProducto inventarioProducto;
/*  76:    */   @Column(name="cantidad_devuelta", nullable=true, precision=12, scale=2)
/*  77:    */   @Digits(integer=12, fraction=2)
/*  78:    */   @Min(0L)
/*  79:107 */   private BigDecimal cantidadDevuelta = BigDecimal.ZERO;
/*  80:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleRecepcionProveedor")
/*  81:112 */   private List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_lote")
/*  84:    */   private Lote lote;
/*  85:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  86:    */   @JoinColumn(name="id_transformacion_automatica", nullable=true)
/*  87:    */   private MovimientoInventario transformacionAutomatica;
/*  88:    */   @Transient
/*  89:123 */   private BigDecimal cantidadPorDevolver = BigDecimal.ZERO;
/*  90:    */   @Transient
/*  91:    */   private BigDecimal saldo;
/*  92:    */   
/*  93:    */   public int getId()
/*  94:    */   {
/*  95:137 */     return this.idDetalleRecepcionProveedor;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List<String> getCamposAuditables()
/*  99:    */   {
/* 100:147 */     ArrayList<String> lista = new ArrayList();
/* 101:148 */     lista.add("producto");
/* 102:149 */     lista.add("bodega");
/* 103:150 */     lista.add("cantidad");
/* 104:    */     
/* 105:152 */     return lista;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdDetalleRecepcionProveedor()
/* 109:    */   {
/* 110:156 */     return this.idDetalleRecepcionProveedor;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdDetalleRecepcionProveedor(int idDetalleRecepcionProveedor)
/* 114:    */   {
/* 115:160 */     this.idDetalleRecepcionProveedor = idDetalleRecepcionProveedor;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public RecepcionProveedor getRecepcionProveedor()
/* 119:    */   {
/* 120:164 */     return this.recepcionProveedor;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setRecepcionProveedor(RecepcionProveedor recepcionProveedor)
/* 124:    */   {
/* 125:168 */     this.recepcionProveedor = recepcionProveedor;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Producto getProducto()
/* 129:    */   {
/* 130:172 */     return this.producto;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setProducto(Producto producto)
/* 134:    */   {
/* 135:176 */     this.producto = producto;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Bodega getBodega()
/* 139:    */   {
/* 140:180 */     return this.bodega;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setBodega(Bodega bodega)
/* 144:    */   {
/* 145:184 */     this.bodega = bodega;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getIdOrganizacion()
/* 149:    */   {
/* 150:188 */     return this.idOrganizacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdOrganizacion(int idOrganizacion)
/* 154:    */   {
/* 155:192 */     this.idOrganizacion = idOrganizacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getIdSucursal()
/* 159:    */   {
/* 160:196 */     return this.idSucursal;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdSucursal(int idSucursal)
/* 164:    */   {
/* 165:200 */     this.idSucursal = idSucursal;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public BigDecimal getCantidad()
/* 169:    */   {
/* 170:204 */     return this.cantidad;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCantidad(BigDecimal cantidad)
/* 174:    */   {
/* 175:208 */     this.cantidad = cantidad;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getDescripcion()
/* 179:    */   {
/* 180:212 */     return this.descripcion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setDescripcion(String descripcion)
/* 184:    */   {
/* 185:216 */     this.descripcion = descripcion;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public DetallePedidoProveedor getDetallePedidoProveedor()
/* 189:    */   {
/* 190:220 */     return this.detallePedidoProveedor;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor)
/* 194:    */   {
/* 195:224 */     this.detallePedidoProveedor = detallePedidoProveedor;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public InventarioProducto getInventarioProducto()
/* 199:    */   {
/* 200:233 */     return this.inventarioProducto;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 204:    */   {
/* 205:243 */     this.inventarioProducto = inventarioProducto;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public DetalleFacturaProveedor getDetalleFacturaProveedor()
/* 209:    */   {
/* 210:250 */     return this.detalleFacturaProveedor;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDetalleFacturaProveedor(DetalleFacturaProveedor detalleFacturaProveedor)
/* 214:    */   {
/* 215:258 */     this.detalleFacturaProveedor = detalleFacturaProveedor;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Unidad getUnidadCompra()
/* 219:    */   {
/* 220:267 */     return this.unidadCompra;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setUnidadCompra(Unidad unidadCompra)
/* 224:    */   {
/* 225:277 */     this.unidadCompra = unidadCompra;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setEliminado(boolean eliminado)
/* 229:    */   {
/* 230:281 */     super.setEliminado(eliminado);
/* 231:283 */     if (this.inventarioProducto != null) {
/* 232:284 */       this.inventarioProducto.setEliminado(eliminado);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public BigDecimal getCantidadDevuelta()
/* 237:    */   {
/* 238:294 */     return this.cantidadDevuelta;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setCantidadDevuelta(BigDecimal cantidadDevuelta)
/* 242:    */   {
/* 243:304 */     this.cantidadDevuelta = cantidadDevuelta;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public BigDecimal getCantidadPorDevolver()
/* 247:    */   {
/* 248:313 */     this.cantidadPorDevolver = this.cantidad.subtract(this.cantidadDevuelta);
/* 249:314 */     return this.cantidadPorDevolver;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setCantidadPorDevolver(BigDecimal cantidadPorDevolver)
/* 253:    */   {
/* 254:324 */     this.cantidadPorDevolver = cantidadPorDevolver;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public BigDecimal getSaldo()
/* 258:    */   {
/* 259:333 */     return this.saldo;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setSaldo(BigDecimal saldo)
/* 263:    */   {
/* 264:343 */     this.saldo = saldo;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 268:    */   {
/* 269:347 */     return this.listaLecturaBalanza;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setListaLecturaBalanza(List<LecturaBalanza> listaLecturaBalanza)
/* 273:    */   {
/* 274:351 */     this.listaLecturaBalanza = listaLecturaBalanza;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Lote getLote()
/* 278:    */   {
/* 279:355 */     return this.lote;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setLote(Lote lote)
/* 283:    */   {
/* 284:359 */     this.lote = lote;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public BigDecimal getCantidadUnidadInformativa()
/* 288:    */   {
/* 289:363 */     return this.cantidadUnidadInformativa;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setCantidadUnidadInformativa(BigDecimal cantidadUnidadInformativa)
/* 293:    */   {
/* 294:367 */     this.cantidadUnidadInformativa = cantidadUnidadInformativa;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public MovimientoInventario getTransformacionAutomatica()
/* 298:    */   {
/* 299:371 */     return this.transformacionAutomatica;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setTransformacionAutomatica(MovimientoInventario transformacionAutomatica)
/* 303:    */   {
/* 304:375 */     this.transformacionAutomatica = transformacionAutomatica;
/* 305:    */   }
/* 306:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleRecepcionProveedor
 * JD-Core Version:    0.7.0.1
 */