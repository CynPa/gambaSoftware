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
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ import org.hibernate.annotations.ColumnDefault;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="saldo_producto_lote", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_bodega", "id_lote", "fecha"})}, indexes={@javax.persistence.Index(columnList="fecha"), @javax.persistence.Index(columnList="id_producto"), @javax.persistence.Index(columnList="id_bodega"), @javax.persistence.Index(columnList="id_lote")})
/*  24:    */ public class SaldoProductoLote
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="saldo_producto_lote", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="saldo_producto_lote")
/*  31:    */   @Column(name="id_saldo_producto_lote", unique=true, nullable=false)
/*  32:    */   private int idSaldoProductoLote;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_producto", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private Producto producto;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private Bodega bodega;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_lote", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private Lote lote;
/*  45:    */   @Temporal(TemporalType.DATE)
/*  46:    */   @Column(name="fecha", nullable=false)
/*  47:    */   private Date fecha;
/*  48:    */   @Column(name="saldo", nullable=false, precision=12, scale=4)
/*  49: 74 */   private BigDecimal saldo = BigDecimal.ZERO;
/*  50:    */   @Column(name="descripcion", length=50)
/*  51:    */   @Size(max=50)
/*  52:    */   private String descripcion;
/*  53:    */   @Column(name="inventario_comprometido", nullable=false, precision=12, scale=4)
/*  54:    */   @NotNull
/*  55:    */   @ColumnDefault("0")
/*  56: 82 */   private BigDecimal inventarioComprometido = BigDecimal.ZERO;
/*  57:    */   @Column(name="inventario_comprometido_produccion", nullable=false, precision=12, scale=4)
/*  58:    */   @NotNull
/*  59:    */   @ColumnDefault("0")
/*  60: 88 */   private BigDecimal inventarioComprometidoProduccion = BigDecimal.ZERO;
/*  61:    */   @Transient
/*  62: 93 */   private BigDecimal saldoUnidadAlmacenamiento = BigDecimal.ZERO;
/*  63:    */   
/*  64:    */   public SaldoProductoLote() {}
/*  65:    */   
/*  66:    */   public SaldoProductoLote(Producto producto, Bodega bodega, Lote lote, Date fecha, BigDecimal saldo)
/*  67:    */   {
/*  68:100 */     this.producto = producto;
/*  69:101 */     this.bodega = bodega;
/*  70:102 */     this.lote = lote;
/*  71:103 */     this.fecha = fecha;
/*  72:104 */     this.saldo = saldo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isAuditable()
/*  76:    */   {
/*  77:114 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSaldoProductoLote()
/*  81:    */   {
/*  82:121 */     return this.idSaldoProductoLote;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSaldoProductoLote(int idSaldoProductoLote)
/*  86:    */   {
/*  87:129 */     this.idSaldoProductoLote = idSaldoProductoLote;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Producto getProducto()
/*  91:    */   {
/*  92:138 */     return this.producto;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setProducto(Producto producto)
/*  96:    */   {
/*  97:148 */     this.producto = producto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Bodega getBodega()
/* 101:    */   {
/* 102:157 */     return this.bodega;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setBodega(Bodega bodega)
/* 106:    */   {
/* 107:167 */     this.bodega = bodega;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Date getFecha()
/* 111:    */   {
/* 112:176 */     return this.fecha;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setFecha(Date fecha)
/* 116:    */   {
/* 117:186 */     this.fecha = fecha;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public BigDecimal getSaldo()
/* 121:    */   {
/* 122:195 */     return this.saldo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setSaldo(BigDecimal saldo)
/* 126:    */   {
/* 127:205 */     this.saldo = saldo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public BigDecimal getSaldoUnidadAlmacenamiento()
/* 131:    */   {
/* 132:212 */     return this.saldoUnidadAlmacenamiento;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setSaldoUnidadAlmacenamiento(BigDecimal saldoUnidadAlmacenamiento)
/* 136:    */   {
/* 137:220 */     this.saldoUnidadAlmacenamiento = saldoUnidadAlmacenamiento;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Lote getLote()
/* 141:    */   {
/* 142:227 */     return this.lote;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setLote(Lote lote)
/* 146:    */   {
/* 147:235 */     this.lote = lote;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getDescripcion()
/* 151:    */   {
/* 152:242 */     return this.descripcion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDescripcion(String descripcion)
/* 156:    */   {
/* 157:250 */     this.descripcion = descripcion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getId()
/* 161:    */   {
/* 162:255 */     return this.idSaldoProductoLote;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public BigDecimal getInventarioComprometido()
/* 166:    */   {
/* 167:259 */     return this.inventarioComprometido;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setInventarioComprometido(BigDecimal inventarioComprometido)
/* 171:    */   {
/* 172:263 */     this.inventarioComprometido = inventarioComprometido;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public BigDecimal getInventarioComprometidoProduccion()
/* 176:    */   {
/* 177:267 */     return this.inventarioComprometidoProduccion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setInventarioComprometidoProduccion(BigDecimal inventarioComprometidoProduccion)
/* 181:    */   {
/* 182:271 */     this.inventarioComprometidoProduccion = inventarioComprometidoProduccion;
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SaldoProductoLote
 * JD-Core Version:    0.7.0.1
 */