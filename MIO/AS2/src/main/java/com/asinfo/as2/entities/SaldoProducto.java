/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
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
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="saldo_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_bodega", "fecha"})}, indexes={@javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(columnList="id_producto"), @javax.persistence.Index(columnList="id_bodega")})
/*  22:    */ public class SaldoProducto
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="saldo_producto", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="saldo_producto")
/*  29:    */   @Column(name="id_saldo_producto", unique=true, nullable=false)
/*  30:    */   private int idSaldoProducto;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_producto", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private Producto producto;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private Bodega bodega;
/*  41:    */   @Temporal(TemporalType.DATE)
/*  42:    */   @Column(name="fecha", nullable=false)
/*  43:    */   private Date fecha;
/*  44:    */   @Column(name="saldo", nullable=false, precision=12, scale=4)
/*  45:    */   private BigDecimal saldo;
/*  46:    */   @Column(name="inventario_comprometido", nullable=false, precision=12, scale=4)
/*  47:    */   @NotNull
/*  48: 73 */   private BigDecimal inventarioComprometido = BigDecimal.ZERO;
/*  49:    */   @Column(name="inventario_comprometido_produccion", nullable=true, precision=12, scale=4)
/*  50: 78 */   private BigDecimal inventarioComprometidoProduccion = BigDecimal.ZERO;
/*  51:    */   @Transient
/*  52: 81 */   private BigDecimal saldoUnidadAlmacenamiento = BigDecimal.ZERO;
/*  53:    */   @Transient
/*  54:    */   private int idProducto;
/*  55:    */   @Transient
/*  56:    */   private int idBodega;
/*  57:    */   
/*  58:    */   public SaldoProducto(int idBodega, int idProducto, Date fecha)
/*  59:    */   {
/*  60: 92 */     this.idProducto = idProducto;
/*  61: 93 */     this.idBodega = idBodega;
/*  62: 94 */     this.fecha = fecha;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public SaldoProducto() {}
/*  66:    */   
/*  67:    */   public SaldoProducto(Producto producto, Bodega bodega, Date fecha, BigDecimal saldo)
/*  68:    */   {
/*  69:101 */     this.producto = producto;
/*  70:102 */     this.bodega = bodega;
/*  71:103 */     this.fecha = fecha;
/*  72:104 */     this.saldo = saldo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public SaldoProducto(Producto producto, Bodega bodega, Date fecha)
/*  76:    */   {
/*  77:108 */     this(producto, bodega, fecha, BigDecimal.ZERO);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isAuditable()
/*  81:    */   {
/*  82:118 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSaldoProducto()
/*  86:    */   {
/*  87:127 */     return this.idSaldoProducto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSaldoProducto(int idSaldoProducto)
/*  91:    */   {
/*  92:137 */     this.idSaldoProducto = idSaldoProducto;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Producto getProducto()
/*  96:    */   {
/*  97:146 */     return this.producto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setProducto(Producto producto)
/* 101:    */   {
/* 102:156 */     this.producto = producto;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Bodega getBodega()
/* 106:    */   {
/* 107:165 */     return this.bodega;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setBodega(Bodega bodega)
/* 111:    */   {
/* 112:175 */     this.bodega = bodega;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getFecha()
/* 116:    */   {
/* 117:184 */     return this.fecha;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFecha(Date fecha)
/* 121:    */   {
/* 122:194 */     this.fecha = fecha;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BigDecimal getSaldo()
/* 126:    */   {
/* 127:203 */     if (this.saldo == null) {
/* 128:204 */       this.saldo = BigDecimal.ZERO;
/* 129:    */     }
/* 130:206 */     return this.saldo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setSaldo(BigDecimal saldo)
/* 134:    */   {
/* 135:216 */     this.saldo = saldo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public BigDecimal getSaldoUnidadAlmacenamiento()
/* 139:    */   {
/* 140:223 */     return this.saldoUnidadAlmacenamiento;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setSaldoUnidadAlmacenamiento(BigDecimal saldoUnidadAlmacenamiento)
/* 144:    */   {
/* 145:231 */     this.saldoUnidadAlmacenamiento = saldoUnidadAlmacenamiento;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getIdOrganizacion()
/* 149:    */   {
/* 150:238 */     return this.idOrganizacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdOrganizacion(int idOrganizacion)
/* 154:    */   {
/* 155:246 */     this.idOrganizacion = idOrganizacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getIdProducto()
/* 159:    */   {
/* 160:255 */     return this.idProducto;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdProducto(int idProducto)
/* 164:    */   {
/* 165:265 */     this.idProducto = idProducto;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getIdBodega()
/* 169:    */   {
/* 170:274 */     return this.idBodega;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdBodega(int idBodega)
/* 174:    */   {
/* 175:284 */     this.idBodega = idBodega;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public BigDecimal getInventarioComprometido()
/* 179:    */   {
/* 180:293 */     return this.inventarioComprometido;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setInventarioComprometido(BigDecimal inventarioComprometido)
/* 184:    */   {
/* 185:303 */     this.inventarioComprometido = inventarioComprometido;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public BigDecimal getInventarioComprometidoProduccion()
/* 189:    */   {
/* 190:307 */     return this.inventarioComprometidoProduccion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setInventarioComprometidoProduccion(BigDecimal inventarioComprometidoProduccion)
/* 194:    */   {
/* 195:311 */     this.inventarioComprometidoProduccion = inventarioComprometidoProduccion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getId()
/* 199:    */   {
/* 200:316 */     return this.idSaldoProducto;
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SaldoProducto
 * JD-Core Version:    0.7.0.1
 */