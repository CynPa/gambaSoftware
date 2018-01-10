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
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_toma_fisica")
/*  21:    */ public class DetalleTomaFisica
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -4732740943676528738L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_toma_fisica", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_toma_fisica")
/*  29:    */   @Column(name="id_detalle_toma_fisica", unique=true, nullable=false)
/*  30:    */   private int idDetalleTomaFisica;
/*  31:    */   @Column(name="id_organizacion", nullable=true)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=true)
/*  34:    */   private int idSucursal;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_toma_fisica", nullable=true)
/*  37:    */   private TomaFisica tomaFisica;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_producto", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Producto producto;
/*  42:    */   @Column(name="cantidad_sistema", nullable=true, precision=12, scale=6)
/*  43:    */   @Digits(integer=12, fraction=6)
/*  44:    */   private BigDecimal cantidadSistema;
/*  45:    */   @Column(name="cantidad_toma_fisica", nullable=true, precision=12, scale=6)
/*  46:    */   @Digits(integer=12, fraction=6)
/*  47:    */   private BigDecimal cantidadTomaFisica;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_lote")
/*  50:    */   private Lote lote;
/*  51:    */   @Transient
/*  52: 78 */   private BigDecimal traMovimientoIngreso = BigDecimal.ZERO;
/*  53:    */   @Transient
/*  54: 81 */   private BigDecimal traMovimientoEgreso = BigDecimal.ZERO;
/*  55:    */   
/*  56:    */   public int getId()
/*  57:    */   {
/*  58: 98 */     return this.idDetalleTomaFisica;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdDetalleTomaFisica()
/*  62:    */   {
/*  63:109 */     return this.idDetalleTomaFisica;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdDetalleTomaFisica(int idDetalleTomaFisica)
/*  67:    */   {
/*  68:119 */     this.idDetalleTomaFisica = idDetalleTomaFisica;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdOrganizacion()
/*  72:    */   {
/*  73:128 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdOrganizacion(int idOrganizacion)
/*  77:    */   {
/*  78:138 */     this.idOrganizacion = idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83:147 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88:157 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public TomaFisica getTomaFisica()
/*  92:    */   {
/*  93:166 */     return this.tomaFisica;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setTomaFisica(TomaFisica tomaFisica)
/*  97:    */   {
/*  98:176 */     this.tomaFisica = tomaFisica;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Producto getProducto()
/* 102:    */   {
/* 103:185 */     return this.producto;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setProducto(Producto producto)
/* 107:    */   {
/* 108:195 */     this.producto = producto;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public BigDecimal getCantidadSistema()
/* 112:    */   {
/* 113:204 */     if (this.cantidadSistema == null) {
/* 114:205 */       this.cantidadSistema = BigDecimal.ZERO;
/* 115:    */     }
/* 116:208 */     return this.cantidadSistema;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setCantidadSistema(BigDecimal cantidadSistema)
/* 120:    */   {
/* 121:218 */     this.cantidadSistema = cantidadSistema;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BigDecimal getCantidadTomaFisica()
/* 125:    */   {
/* 126:227 */     if (this.cantidadTomaFisica == null) {
/* 127:228 */       this.cantidadTomaFisica = BigDecimal.ZERO;
/* 128:    */     }
/* 129:230 */     return this.cantidadTomaFisica;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setCantidadTomaFisica(BigDecimal cantidadTomaFisica)
/* 133:    */   {
/* 134:240 */     this.cantidadTomaFisica = cantidadTomaFisica;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public BigDecimal getTraMovimientoIngreso()
/* 138:    */   {
/* 139:249 */     if (this.traMovimientoIngreso == null) {
/* 140:250 */       this.traMovimientoIngreso = BigDecimal.ZERO;
/* 141:    */     }
/* 142:252 */     return this.traMovimientoIngreso;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setTraMovimientoIngreso(BigDecimal traMovimientoIngreso)
/* 146:    */   {
/* 147:262 */     this.traMovimientoIngreso = traMovimientoIngreso;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getTraMovimientoEgreso()
/* 151:    */   {
/* 152:271 */     if (this.traMovimientoEgreso == null) {
/* 153:272 */       this.traMovimientoEgreso = BigDecimal.ZERO;
/* 154:    */     }
/* 155:274 */     return this.traMovimientoEgreso;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setTraMovimientoEgreso(BigDecimal traMovimientoEgreso)
/* 159:    */   {
/* 160:284 */     this.traMovimientoEgreso = traMovimientoEgreso;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Lote getLote()
/* 164:    */   {
/* 165:288 */     return this.lote;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setLote(Lote lote)
/* 169:    */   {
/* 170:292 */     this.lote = lote;
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleTomaFisica
 * JD-Core Version:    0.7.0.1
 */