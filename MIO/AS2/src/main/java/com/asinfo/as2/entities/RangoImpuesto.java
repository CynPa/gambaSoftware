/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Max;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="rango_impuesto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_rango_impuesto"})})
/*  25:    */ public class RangoImpuesto
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -628183742385505074L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="rango_impuesto", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rango_impuesto")
/*  33:    */   @Column(name="id_rango_impuesto")
/*  34:    */   private int idRangoImpuesto;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="porcentaje_impuesto", nullable=false, precision=5, scale=2)
/*  42:    */   @NotNull
/*  43:    */   @Digits(integer=5, fraction=2)
/*  44:    */   @Min(0L)
/*  45:    */   @Max(100L)
/*  46:    */   private BigDecimal porcentajeImpuesto;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha_desde", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private Date fechaDesde;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha_hasta", nullable=true)
/*  53:    */   private Date fechaHasta;
/*  54:    */   @Column(name="indicador_compra", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private boolean indicadorCompra;
/*  57:    */   @Column(name="indicador_venta", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private boolean indicadorVenta;
/*  60:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  61:    */   @JoinColumn(name="id_impuesto", nullable=true)
/*  62:    */   private Impuesto impuesto;
/*  63:    */   
/*  64:    */   public RangoImpuesto() {}
/*  65:    */   
/*  66:    */   public RangoImpuesto(int idSucursal, int idOrganizacion, BigDecimal porcentajeImpuesto, Date fechaDesde, Date fechaHasta, boolean indicadorCompra, boolean indicadorVenta, Impuesto impuesto)
/*  67:    */   {
/*  68:106 */     this.idSucursal = idSucursal;
/*  69:107 */     this.idOrganizacion = idOrganizacion;
/*  70:108 */     this.porcentajeImpuesto = porcentajeImpuesto;
/*  71:109 */     this.fechaDesde = fechaDesde;
/*  72:110 */     this.fechaHasta = fechaHasta;
/*  73:111 */     this.indicadorCompra = indicadorCompra;
/*  74:112 */     this.indicadorVenta = indicadorVenta;
/*  75:113 */     this.impuesto = impuesto;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getId()
/*  79:    */   {
/*  80:123 */     return this.idRangoImpuesto;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdRangoImpuesto()
/*  84:    */   {
/*  85:134 */     return this.idRangoImpuesto;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdRangoImpuesto(int idRangoImpuesto)
/*  89:    */   {
/*  90:144 */     this.idRangoImpuesto = idRangoImpuesto;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdSucursal()
/*  94:    */   {
/*  95:153 */     return this.idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdSucursal(int idSucursal)
/*  99:    */   {
/* 100:163 */     this.idSucursal = idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdOrganizacion()
/* 104:    */   {
/* 105:172 */     return this.idOrganizacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdOrganizacion(int idOrganizacion)
/* 109:    */   {
/* 110:182 */     this.idOrganizacion = idOrganizacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BigDecimal getPorcentajeImpuesto()
/* 114:    */   {
/* 115:191 */     if (this.porcentajeImpuesto == null) {
/* 116:192 */       this.porcentajeImpuesto = BigDecimal.ZERO;
/* 117:    */     }
/* 118:194 */     return this.porcentajeImpuesto;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/* 122:    */   {
/* 123:204 */     this.porcentajeImpuesto = porcentajeImpuesto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Date getFechaDesde()
/* 127:    */   {
/* 128:213 */     return this.fechaDesde;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setFechaDesde(Date fechaDesde)
/* 132:    */   {
/* 133:223 */     this.fechaDesde = fechaDesde;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Date getFechaHasta()
/* 137:    */   {
/* 138:232 */     return this.fechaHasta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFechaHasta(Date fechaHasta)
/* 142:    */   {
/* 143:242 */     this.fechaHasta = fechaHasta;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Impuesto getImpuesto()
/* 147:    */   {
/* 148:251 */     return this.impuesto;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setImpuesto(Impuesto impuesto)
/* 152:    */   {
/* 153:261 */     this.impuesto = impuesto;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isIndicadorCompra()
/* 157:    */   {
/* 158:270 */     return this.indicadorCompra;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setIndicadorCompra(boolean indicadorCompra)
/* 162:    */   {
/* 163:280 */     this.indicadorCompra = indicadorCompra;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean isIndicadorVenta()
/* 167:    */   {
/* 168:289 */     return this.indicadorVenta;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setIndicadorVenta(boolean indicadorVenta)
/* 172:    */   {
/* 173:299 */     this.indicadorVenta = indicadorVenta;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RangoImpuesto
 * JD-Core Version:    0.7.0.1
 */