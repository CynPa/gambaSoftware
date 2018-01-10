/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
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
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.DecimalMin;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_registro_peso_lote")
/*  21:    */ public class DetalleRegistroPesoLote
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_registro_peso_lote", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_registro_peso_lote")
/*  28:    */   @Column(name="id_detalle_registro_peso_lote")
/*  29:    */   private int idDetalleRegistroPesoLote;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_registro_peso", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private RegistroPeso registroPeso;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_producto", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Producto producto;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_lote", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private Lote lote;
/*  46:    */   @Column(name="peso", nullable=true, precision=12, scale=2)
/*  47:    */   @Digits(integer=12, fraction=2)
/*  48:    */   @DecimalMin("0.00")
/*  49: 62 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  50:    */   @Transient
/*  51: 67 */   private BigDecimal saldoInventario = BigDecimal.ZERO;
/*  52:    */   @Transient
/*  53: 70 */   private BigDecimal saldo = BigDecimal.ZERO;
/*  54:    */   @Transient
/*  55: 73 */   private BigDecimal saldoComprometido = BigDecimal.ZERO;
/*  56:    */   @Transient
/*  57:    */   private boolean eliminadoManual;
/*  58:    */   
/*  59:    */   public int getId()
/*  60:    */   {
/*  61: 81 */     return this.idDetalleRegistroPesoLote;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdDetalleRegistroPesoLote()
/*  65:    */   {
/*  66: 85 */     return this.idDetalleRegistroPesoLote;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdDetalleRegistroPesoLote(int idDetalleRegistroPesoLote)
/*  70:    */   {
/*  71: 89 */     this.idDetalleRegistroPesoLote = idDetalleRegistroPesoLote;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdOrganizacion()
/*  75:    */   {
/*  76: 93 */     return this.idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdOrganizacion(int idOrganizacion)
/*  80:    */   {
/*  81: 97 */     this.idOrganizacion = idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdSucursal()
/*  85:    */   {
/*  86:101 */     return this.idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdSucursal(int idSucursal)
/*  90:    */   {
/*  91:105 */     this.idSucursal = idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public RegistroPeso getRegistroPeso()
/*  95:    */   {
/*  96:109 */     return this.registroPeso;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 100:    */   {
/* 101:113 */     this.registroPeso = registroPeso;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Lote getLote()
/* 105:    */   {
/* 106:117 */     return this.lote;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setLote(Lote lote)
/* 110:    */   {
/* 111:121 */     this.lote = lote;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public BigDecimal getCantidad()
/* 115:    */   {
/* 116:125 */     return this.cantidad;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setCantidad(BigDecimal cantidad)
/* 120:    */   {
/* 121:129 */     this.cantidad = cantidad;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BigDecimal getSaldo()
/* 125:    */   {
/* 126:133 */     return this.saldo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setSaldo(BigDecimal saldo)
/* 130:    */   {
/* 131:137 */     this.saldo = saldo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Producto getProducto()
/* 135:    */   {
/* 136:141 */     return this.producto;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setProducto(Producto producto)
/* 140:    */   {
/* 141:145 */     this.producto = producto;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean isEliminadoManual()
/* 145:    */   {
/* 146:149 */     return this.eliminadoManual;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setEliminadoManual(boolean eliminadoManual)
/* 150:    */   {
/* 151:153 */     this.eliminadoManual = eliminadoManual;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getSaldoComprometido()
/* 155:    */   {
/* 156:157 */     return this.saldoComprometido;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setSaldoComprometido(BigDecimal saldoComprometido)
/* 160:    */   {
/* 161:161 */     this.saldoComprometido = saldoComprometido;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public BigDecimal getSaldoInventario()
/* 165:    */   {
/* 166:165 */     return this.saldoInventario;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setSaldoInventario(BigDecimal saldoInventario)
/* 170:    */   {
/* 171:169 */     this.saldoInventario = saldoInventario;
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleRegistroPesoLote
 * JD-Core Version:    0.7.0.1
 */