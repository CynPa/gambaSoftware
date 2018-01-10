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
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Transient;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ import org.hibernate.annotations.ColumnDefault;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_solicitud_compra")
/*  27:    */ public class DetalleSolicitudCompra
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 571335322178384473L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_solicitud_compra", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_solicitud_compra")
/*  35:    */   @Column(name="id_detalle_solicitud_compra")
/*  36:    */   private int idDetalleSolicitudCompra;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private int idSucursal;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_solicitud_compra", nullable=true)
/*  45:    */   private SolicitudCompra solicitudCompra;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_producto", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Producto producto;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  52:    */   private Empleado empleado;
/*  53:    */   @NotNull
/*  54:    */   @Column(name="cantidad", nullable=false, precision=12, scale=4)
/*  55:    */   @Digits(integer=12, fraction=4)
/*  56: 65 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  57:    */   @NotNull
/*  58:    */   @Column(name="cantidad_aprobada", nullable=false, precision=12, scale=4)
/*  59:    */   @Digits(integer=12, fraction=4)
/*  60: 70 */   private BigDecimal cantidadAprobada = BigDecimal.ZERO;
/*  61:    */   @Column(name="cantidad_original", nullable=false, precision=12, scale=4)
/*  62:    */   @NotNull
/*  63:    */   @Digits(integer=12, fraction=4)
/*  64:    */   @Min(0L)
/*  65:    */   @ColumnDefault("0")
/*  66: 75 */   private BigDecimal cantidadOriginal = BigDecimal.ZERO;
/*  67:    */   @Column(name="cantidad_en_pedido", nullable=false, precision=12, scale=2)
/*  68:    */   @NotNull
/*  69:    */   @Digits(integer=12, fraction=2)
/*  70:    */   @Min(0L)
/*  71:    */   @ColumnDefault("0")
/*  72: 82 */   private BigDecimal cantidadEnPedido = BigDecimal.ZERO;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_unidad_compra", nullable=false)
/*  75:    */   private Unidad unidadCompra;
/*  76:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleSolicitudCompraPadre")
/*  77: 94 */   private List<DetalleSolicitudCompra> listaDetalleSolicitudCompra = new ArrayList();
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_detalle_solicitud_compra_padre", nullable=true)
/*  80:    */   private DetalleSolicitudCompra detalleSolicitudCompraPadre;
/*  81:    */   @Column(name="descripcion", nullable=true, length=500)
/*  82:    */   @Size(max=500)
/*  83:    */   private String descripcion;
/*  84:    */   @Column(name="indicador_agrupado", nullable=false)
/*  85:    */   private boolean indicadorAgrupado;
/*  86:    */   @Column(name="indicador_en_pedido", nullable=false)
/*  87:    */   private boolean indicadorEnPedido;
/*  88:    */   @Transient
/*  89:111 */   private BigDecimal saldoProductoActual = BigDecimal.ZERO;
/*  90:    */   @Transient
/*  91:114 */   private boolean eliminadoDesdeConsolidacion = false;
/*  92:    */   
/*  93:    */   public int getId()
/*  94:    */   {
/*  95:125 */     return this.idDetalleSolicitudCompra;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdOrganizacion()
/*  99:    */   {
/* 100:129 */     return this.idOrganizacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdOrganizacion(int idOrganizacion)
/* 104:    */   {
/* 105:133 */     this.idOrganizacion = idOrganizacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdSucursal()
/* 109:    */   {
/* 110:137 */     return this.idSucursal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdSucursal(int idSucursal)
/* 114:    */   {
/* 115:141 */     this.idSucursal = idSucursal;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Producto getProducto()
/* 119:    */   {
/* 120:145 */     return this.producto;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setProducto(Producto producto)
/* 124:    */   {
/* 125:149 */     this.producto = producto;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public BigDecimal getCantidad()
/* 129:    */   {
/* 130:153 */     return this.cantidad;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setCantidad(BigDecimal cantidad)
/* 134:    */   {
/* 135:157 */     this.cantidad = cantidad;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getIdDetalleSolicitudCompra()
/* 139:    */   {
/* 140:161 */     return this.idDetalleSolicitudCompra;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdDetalleSolicitudCompra(int idDetalleSolicitudCompra)
/* 144:    */   {
/* 145:165 */     this.idDetalleSolicitudCompra = idDetalleSolicitudCompra;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public SolicitudCompra getSolicitudCompra()
/* 149:    */   {
/* 150:169 */     return this.solicitudCompra;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setSolicitudCompra(SolicitudCompra solicitudCompra)
/* 154:    */   {
/* 155:173 */     this.solicitudCompra = solicitudCompra;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Unidad getUnidadCompra()
/* 159:    */   {
/* 160:177 */     return this.unidadCompra;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setUnidadCompra(Unidad unidadCompra)
/* 164:    */   {
/* 165:181 */     this.unidadCompra = unidadCompra;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudCompra()
/* 169:    */   {
/* 170:185 */     return this.listaDetalleSolicitudCompra;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaDetalleSolicitudCompra(List<DetalleSolicitudCompra> listaDetalleSolicitudCompra)
/* 174:    */   {
/* 175:189 */     this.listaDetalleSolicitudCompra = listaDetalleSolicitudCompra;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public DetalleSolicitudCompra getDetalleSolicitudCompraPadre()
/* 179:    */   {
/* 180:193 */     return this.detalleSolicitudCompraPadre;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setDetalleSolicitudCompraPadre(DetalleSolicitudCompra detalleSolicitudCompraPadre)
/* 184:    */   {
/* 185:197 */     this.detalleSolicitudCompraPadre = detalleSolicitudCompraPadre;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getDescripcion()
/* 189:    */   {
/* 190:201 */     return this.descripcion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDescripcion(String descripcion)
/* 194:    */   {
/* 195:205 */     this.descripcion = descripcion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Empleado getEmpleado()
/* 199:    */   {
/* 200:209 */     return this.empleado;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setEmpleado(Empleado empleado)
/* 204:    */   {
/* 205:213 */     this.empleado = empleado;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public BigDecimal getCantidadAprobada()
/* 209:    */   {
/* 210:217 */     return this.cantidadAprobada;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setCantidadAprobada(BigDecimal cantidadAprobada)
/* 214:    */   {
/* 215:221 */     this.cantidadAprobada = cantidadAprobada;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isIndicadorAgrupado()
/* 219:    */   {
/* 220:225 */     return this.indicadorAgrupado;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIndicadorAgrupado(boolean indicadorAgrupado)
/* 224:    */   {
/* 225:229 */     this.indicadorAgrupado = indicadorAgrupado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isIndicadorEnPedido()
/* 229:    */   {
/* 230:233 */     return this.indicadorEnPedido;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setIndicadorEnPedido(boolean indicadorEnPedido)
/* 234:    */   {
/* 235:237 */     this.indicadorEnPedido = indicadorEnPedido;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public BigDecimal getSaldoProductoActual()
/* 239:    */   {
/* 240:241 */     return this.saldoProductoActual;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setSaldoProductoActual(BigDecimal saldoProductoActual)
/* 244:    */   {
/* 245:245 */     this.saldoProductoActual = saldoProductoActual;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public BigDecimal getCantidadOriginal()
/* 249:    */   {
/* 250:249 */     return this.cantidadOriginal;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setCantidadOriginal(BigDecimal cantidadOriginal)
/* 254:    */   {
/* 255:253 */     this.cantidadOriginal = cantidadOriginal;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean isEliminadoDesdeConsolidacion()
/* 259:    */   {
/* 260:257 */     return this.eliminadoDesdeConsolidacion;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setEliminadoDesdeConsolidacion(boolean eliminadoDesdeConsolidacion)
/* 264:    */   {
/* 265:261 */     this.eliminadoDesdeConsolidacion = eliminadoDesdeConsolidacion;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public BigDecimal getCantidadEnPedido()
/* 269:    */   {
/* 270:265 */     return this.cantidadEnPedido;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setCantidadEnPedido(BigDecimal cantidadEnPedido)
/* 274:    */   {
/* 275:269 */     this.cantidadEnPedido = cantidadEnPedido;
/* 276:    */   }
/* 277:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleSolicitudCompra
 * JD-Core Version:    0.7.0.1
 */