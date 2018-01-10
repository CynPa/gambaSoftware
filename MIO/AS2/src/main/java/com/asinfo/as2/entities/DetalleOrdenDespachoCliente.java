/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.math.RoundingMode;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.DecimalMin;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="detalle_orden_despacho_cliente")
/*  26:    */ public class DetalleOrdenDespachoCliente
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 6041597607233226319L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="detalle_orden_despacho_cliente", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_despacho_cliente")
/*  34:    */   @Column(name="id_detalle_orden_despacho_cliente", unique=true, nullable=false)
/*  35:    */   private int idDetalleOrdenDespachoCliente;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private int idSucursal;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_orden_despacho_cliente", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private OrdenDespachoCliente ordenDespachoCliente;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_producto", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Producto producto;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  52:    */   private Bodega bodega;
/*  53:    */   @Column(name="cantidad", nullable=true, precision=12, scale=2)
/*  54:    */   @Digits(integer=10, fraction=2)
/*  55:    */   @DecimalMin("0.00")
/*  56: 77 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  57:    */   @Column(name="cantidad_pedido", nullable=true, precision=12, scale=4)
/*  58:    */   @Digits(integer=10, fraction=4)
/*  59:    */   @DecimalMin("0.00")
/*  60: 82 */   private BigDecimal cantidadPedido = BigDecimal.ZERO;
/*  61:    */   @Column(name="peso_materia_prima", nullable=true, precision=12, scale=2)
/*  62:    */   @Digits(integer=10, fraction=2)
/*  63:    */   @DecimalMin("0.00")
/*  64: 87 */   private BigDecimal pesoMateriaPrima = BigDecimal.ZERO;
/*  65:    */   @Column(name="indicador_manejo_peso", nullable=true)
/*  66:    */   private boolean indicadorManejoPeso;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_lote")
/*  69:    */   private Lote lote;
/*  70:    */   @Column(name="cantidad_embalaje_despacho", nullable=true)
/*  71:101 */   private BigDecimal cantidadEmbalajeDespacho = BigDecimal.ONE;
/*  72:    */   @Column(name="cantidad_unidad_despacho", nullable=true)
/*  73:    */   private BigDecimal cantidadUnidadDespacho;
/*  74:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenDespachoCliente")
/*  75:108 */   private List<DetalleOrdenDespachoClientePedidoCliente> listaDetallePedidoCliente = new ArrayList();
/*  76:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenSalidaMaterial")
/*  77:111 */   private List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/*  78:    */   @Column(name="indicador_automatico", nullable=false)
/*  79:    */   @NotNull
/*  80:    */   private boolean indicadorAutomatico;
/*  81:    */   @Transient
/*  82:    */   private BigDecimal cantidadUnidadDespachoPedido;
/*  83:    */   @Transient
/*  84:    */   private int cantidadPedidos;
/*  85:    */   @Transient
/*  86:    */   private BigDecimal saldo;
/*  87:    */   @Transient
/*  88:    */   private BigDecimal porcientoDespachado;
/*  89:    */   @Transient
/*  90:    */   private BigDecimal porcientoPorDespachar;
/*  91:    */   
/*  92:    */   public int getId()
/*  93:    */   {
/*  94:136 */     return this.idDetalleOrdenDespachoCliente;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdDetalleOrdenDespachoCliente()
/*  98:    */   {
/*  99:140 */     return this.idDetalleOrdenDespachoCliente;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIdOrganizacion()
/* 103:    */   {
/* 104:144 */     return this.idOrganizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdOrganizacion(int idOrganizacion)
/* 108:    */   {
/* 109:148 */     this.idOrganizacion = idOrganizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getIdSucursal()
/* 113:    */   {
/* 114:152 */     return this.idSucursal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setIdSucursal(int idSucursal)
/* 118:    */   {
/* 119:156 */     this.idSucursal = idSucursal;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public OrdenDespachoCliente getOrdenDespachoCliente()
/* 123:    */   {
/* 124:160 */     return this.ordenDespachoCliente;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setOrdenDespachoCliente(OrdenDespachoCliente ordenDespachoCliente)
/* 128:    */   {
/* 129:164 */     this.ordenDespachoCliente = ordenDespachoCliente;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Producto getProducto()
/* 133:    */   {
/* 134:168 */     return this.producto;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setProducto(Producto producto)
/* 138:    */   {
/* 139:172 */     this.producto = producto;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Bodega getBodega()
/* 143:    */   {
/* 144:176 */     return this.bodega;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setBodega(Bodega bodega)
/* 148:    */   {
/* 149:180 */     this.bodega = bodega;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public BigDecimal getCantidad()
/* 153:    */   {
/* 154:184 */     return this.cantidad;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setCantidad(BigDecimal cantidad)
/* 158:    */   {
/* 159:188 */     if (cantidad.compareTo(this.cantidad) != 0)
/* 160:    */     {
/* 161:189 */       setEditado(true);
/* 162:190 */       this.cantidad = cantidad;
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getCantidadPedido()
/* 167:    */   {
/* 168:195 */     return this.cantidadPedido;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setCantidadPedido(BigDecimal cantidadPedido)
/* 172:    */   {
/* 173:199 */     this.cantidadPedido = cantidadPedido;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isIndicadorManejoPeso()
/* 177:    */   {
/* 178:203 */     return this.indicadorManejoPeso;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setIndicadorManejoPeso(boolean indicadorManejoPeso)
/* 182:    */   {
/* 183:207 */     this.indicadorManejoPeso = indicadorManejoPeso;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setIdDetalleOrdenDespachoCliente(int idDetalleOrdenDespachoCliente)
/* 187:    */   {
/* 188:211 */     this.idDetalleOrdenDespachoCliente = idDetalleOrdenDespachoCliente;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public BigDecimal getCantidadEmbalajeDespacho()
/* 192:    */   {
/* 193:215 */     if (this.cantidadEmbalajeDespacho == null) {
/* 194:216 */       this.cantidadEmbalajeDespacho = BigDecimal.ONE;
/* 195:    */     }
/* 196:218 */     return this.cantidadEmbalajeDespacho;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setCantidadEmbalajeDespacho(BigDecimal cantidadEmbalajeDespacho)
/* 200:    */   {
/* 201:222 */     this.cantidadEmbalajeDespacho = cantidadEmbalajeDespacho;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public BigDecimal getCantidadUnidadDespacho()
/* 205:    */   {
/* 206:226 */     if (this.cantidadUnidadDespacho == null) {
/* 207:227 */       this.cantidadUnidadDespacho = this.cantidad;
/* 208:    */     }
/* 209:229 */     return this.cantidadUnidadDespacho;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setCantidadUnidadDespacho(BigDecimal cantidadUnidadDespacho)
/* 213:    */   {
/* 214:233 */     this.cantidadUnidadDespacho = cantidadUnidadDespacho;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public BigDecimal getCantidadUnidadDespachoPedido()
/* 218:    */   {
/* 219:237 */     if ((getCantidadEmbalajeDespacho() != null) && (getCantidadPedido() != null) && (getCantidadEmbalajeDespacho().compareTo(BigDecimal.ZERO) != 0)) {
/* 220:238 */       this.cantidadUnidadDespachoPedido = getCantidadPedido().divide(getCantidadEmbalajeDespacho(), 2, RoundingMode.HALF_UP);
/* 221:    */     } else {
/* 222:240 */       this.cantidadUnidadDespachoPedido = getCantidadPedido();
/* 223:    */     }
/* 224:242 */     return this.cantidadUnidadDespachoPedido;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setCantidadUnidadDespachoPedido(BigDecimal cantidadUnidadDespachoPedido)
/* 228:    */   {
/* 229:246 */     this.cantidadUnidadDespachoPedido = cantidadUnidadDespachoPedido;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 233:    */   {
/* 234:250 */     return this.listaLecturaBalanza;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setListaLecturaBalanza(List<LecturaBalanza> listaLecturaBalanza)
/* 238:    */   {
/* 239:254 */     this.listaLecturaBalanza = listaLecturaBalanza;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean isIndicadorAutomatico()
/* 243:    */   {
/* 244:258 */     return this.indicadorAutomatico;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 248:    */   {
/* 249:262 */     this.indicadorAutomatico = indicadorAutomatico;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public BigDecimal getSaldo()
/* 253:    */   {
/* 254:267 */     this.saldo = getCantidadUnidadDespachoPedido().subtract(getCantidadUnidadDespacho());
/* 255:268 */     return this.saldo;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public BigDecimal getPorcientoDespachado()
/* 259:    */   {
/* 260:272 */     if ((getCantidadPedido() != null) && (getCantidadPedido().compareTo(BigDecimal.ZERO) != 0)) {
/* 261:273 */       this.porcientoDespachado = getCantidad().multiply(new BigDecimal(100)).divide(getCantidadPedido(), 2, RoundingMode.HALF_UP);
/* 262:    */     }
/* 263:275 */     return this.porcientoDespachado;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public BigDecimal getPorcientoPorDespachar()
/* 267:    */   {
/* 268:279 */     if ((getCantidadPedido() != null) && (getCantidadPedido().compareTo(BigDecimal.ZERO) != 0)) {
/* 269:280 */       this.porcientoPorDespachar = getSaldo().multiply(new BigDecimal(100)).divide(getCantidadUnidadDespachoPedido(), 2, RoundingMode.HALF_UP);
/* 270:    */     }
/* 271:282 */     return this.porcientoPorDespachar;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int getCantidadPedidos()
/* 275:    */   {
/* 276:286 */     this.cantidadPedidos = getListaDetallePedidoCliente().size();
/* 277:287 */     return this.cantidadPedidos;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setCantidadPedidos(int cantidadPedidos)
/* 281:    */   {
/* 282:291 */     this.cantidadPedidos = cantidadPedidos;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public BigDecimal getPesoMateriaPrima()
/* 286:    */   {
/* 287:295 */     return this.pesoMateriaPrima;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setPesoMateriaPrima(BigDecimal pesoMateriaPrima)
/* 291:    */   {
/* 292:299 */     this.pesoMateriaPrima = pesoMateriaPrima;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Lote getLote()
/* 296:    */   {
/* 297:303 */     return this.lote;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setLote(Lote lote)
/* 301:    */   {
/* 302:307 */     this.lote = lote;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public List<DetalleOrdenDespachoClientePedidoCliente> getListaDetallePedidoCliente()
/* 306:    */   {
/* 307:311 */     return this.listaDetallePedidoCliente;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setListaDetallePedidoCliente(List<DetalleOrdenDespachoClientePedidoCliente> listaDetallePedidoCliente)
/* 311:    */   {
/* 312:315 */     this.listaDetallePedidoCliente = listaDetallePedidoCliente;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setEliminado(boolean eliminado)
/* 316:    */   {
/* 317:319 */     super.setEliminado(eliminado);
/* 318:320 */     setEditado(true);
/* 319:    */   }
/* 320:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleOrdenDespachoCliente
 * JD-Core Version:    0.7.0.1
 */