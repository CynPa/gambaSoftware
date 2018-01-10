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
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import org.hibernate.annotations.ColumnDefault;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_pre_devolucion_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_pre_devolucion_cliente"})})
/*  22:    */ public class DetallePreDevolucionCliente
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_pre_devolucion_cliente", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pre_devolucion_cliente")
/*  30:    */   @Column(name="id_detalle_pre_devolucion_cliente")
/*  31:    */   private int idDetallePreDevolucionCliente;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idOrganizacion;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_pre_devolucion_cliente", nullable=false)
/*  37:    */   private PreDevolucionCliente preDevolucionCliente;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_producto", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Producto producto;
/*  42:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  43:    */   @Digits(integer=12, fraction=2)
/*  44:    */   @NotNull
/*  45: 54 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  46:    */   @Column(name="cantidad_recibida", nullable=false, precision=12, scale=2)
/*  47:    */   @Digits(integer=12, fraction=2)
/*  48:    */   @NotNull
/*  49: 59 */   private BigDecimal cantidadRecibida = BigDecimal.ZERO;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_unidad", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Unidad unidad;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_bodega", nullable=true)
/*  56:    */   private Bodega bodega;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_lote", nullable=true)
/*  59:    */   private Lote lote;
/*  60:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  61:    */   @Digits(integer=12, fraction=6)
/*  62:    */   @Min(0L)
/*  63:    */   @NotNull
/*  64: 77 */   private BigDecimal precio = BigDecimal.ZERO;
/*  65:    */   @Column(name="precio_linea", nullable=false, precision=12, scale=6)
/*  66:    */   @Digits(integer=12, fraction=6)
/*  67:    */   @Min(0L)
/*  68:    */   @NotNull
/*  69: 83 */   private BigDecimal precioLinea = BigDecimal.ZERO;
/*  70:    */   @Column(name="indicador_procesar", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   @ColumnDefault("'0'")
/*  73:    */   private boolean indicadorProcesar;
/*  74:    */   @Column(name="cantidad_procesar", nullable=false, precision=12, scale=2)
/*  75:    */   @Digits(integer=12, fraction=2)
/*  76:    */   @NotNull
/*  77:    */   @ColumnDefault("0")
/*  78: 94 */   private BigDecimal cantidadProcesar = BigDecimal.ZERO;
/*  79:    */   
/*  80:    */   public int getId()
/*  81:    */   {
/*  82:113 */     return this.idDetallePreDevolucionCliente;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdDetallePreDevolucionCliente()
/*  86:    */   {
/*  87:120 */     return this.idDetallePreDevolucionCliente;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdOrganizacion()
/*  91:    */   {
/*  92:127 */     return this.idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdOrganizacion(int idOrganizacion)
/*  96:    */   {
/*  97:135 */     this.idOrganizacion = idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdDetallePreDevolucionCliente(int idDetallePreDevolucionCliente)
/* 101:    */   {
/* 102:143 */     this.idDetallePreDevolucionCliente = idDetallePreDevolucionCliente;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public PreDevolucionCliente getPreDevolucionCliente()
/* 106:    */   {
/* 107:150 */     return this.preDevolucionCliente;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setPreDevolucionCliente(PreDevolucionCliente preDevolucionCliente)
/* 111:    */   {
/* 112:158 */     this.preDevolucionCliente = preDevolucionCliente;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Producto getProducto()
/* 116:    */   {
/* 117:165 */     return this.producto;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setProducto(Producto producto)
/* 121:    */   {
/* 122:173 */     this.producto = producto;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BigDecimal getCantidad()
/* 126:    */   {
/* 127:180 */     return this.cantidad;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setCantidad(BigDecimal cantidad)
/* 131:    */   {
/* 132:188 */     this.cantidad = cantidad;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public BigDecimal getCantidadRecibida()
/* 136:    */   {
/* 137:195 */     return this.cantidadRecibida;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setCantidadRecibida(BigDecimal cantidadRecibida)
/* 141:    */   {
/* 142:203 */     this.cantidadRecibida = cantidadRecibida;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Unidad getUnidad()
/* 146:    */   {
/* 147:210 */     return this.unidad;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setUnidad(Unidad unidad)
/* 151:    */   {
/* 152:218 */     this.unidad = unidad;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Bodega getBodega()
/* 156:    */   {
/* 157:225 */     return this.bodega;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setBodega(Bodega bodega)
/* 161:    */   {
/* 162:233 */     this.bodega = bodega;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Lote getLote()
/* 166:    */   {
/* 167:240 */     return this.lote;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setLote(Lote lote)
/* 171:    */   {
/* 172:248 */     this.lote = lote;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public BigDecimal getPrecio()
/* 176:    */   {
/* 177:255 */     return this.precio;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setPrecio(BigDecimal precio)
/* 181:    */   {
/* 182:263 */     this.precio = precio;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public BigDecimal getPrecioLinea()
/* 186:    */   {
/* 187:270 */     return this.precioLinea;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setPrecioLinea(BigDecimal precioLinea)
/* 191:    */   {
/* 192:278 */     this.precioLinea = precioLinea;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean isIndicadorProcesar()
/* 196:    */   {
/* 197:282 */     return this.indicadorProcesar;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setIndicadorProcesar(boolean indicadorProcesar)
/* 201:    */   {
/* 202:286 */     this.indicadorProcesar = indicadorProcesar;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public BigDecimal getCantidadProcesar()
/* 206:    */   {
/* 207:290 */     return this.cantidadProcesar;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setCantidadProcesar(BigDecimal cantidadProcesar)
/* 211:    */   {
/* 212:294 */     this.cantidadProcesar = cantidadProcesar;
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePreDevolucionCliente
 * JD-Core Version:    0.7.0.1
 */