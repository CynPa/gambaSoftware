/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="detalle_factura_proveedor_importacion_gasto")
/*  26:    */ public class DetalleFacturaProveedorImportacionGasto
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="detalle_factura_proveedor_importacion_gasto", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_proveedor_importacion_gasto")
/*  33:    */   @Column(name="id_detalle_factura_proveedor_importacion_gasto")
/*  34:    */   private int idDetalleFacturaProveedorImportacionGasto;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="tipo_prorrateo", nullable=false)
/*  40:    */   @Enumerated(EnumType.ORDINAL)
/*  41:    */   private TipoProrrateoEnum tipoProrrateoEnum;
/*  42:    */   @Column(name="descripcion", length=200, nullable=true)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Temporal(TemporalType.DATE)
/*  46:    */   @Column(name="fecha_gasto", nullable=false)
/*  47:    */   private Date fechaGasto;
/*  48:    */   @Column(name="valor_real", nullable=false, precision=12, scale=2)
/*  49:    */   @Min(0L)
/*  50: 75 */   private BigDecimal valorReal = BigDecimal.ZERO;
/*  51:    */   @Column(name="valor_presupuesto", nullable=false, precision=12, scale=2)
/*  52:    */   @Min(0L)
/*  53: 79 */   private BigDecimal valorPresupuesto = BigDecimal.ZERO;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_factura_proveedor_importacion")
/*  56:    */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_moneda", nullable=true)
/*  59:    */   private Moneda moneda;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_gasto_importacion")
/*  62:    */   private GastoImportacion gastoImportacion;
/*  63:    */   @Transient
/*  64:101 */   private BigDecimal traValorManual = BigDecimal.ZERO;
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:118 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdDetalleFacturaProveedorImportacionGasto()
/*  72:    */   {
/*  73:127 */     return this.idDetalleFacturaProveedorImportacionGasto;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdDetalleFacturaProveedorImportacionGasto(int idDetalleFacturaProveedorImportacionGasto)
/*  77:    */   {
/*  78:138 */     this.idDetalleFacturaProveedorImportacionGasto = idDetalleFacturaProveedorImportacionGasto;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdOrganizacion(int idOrganizacion)
/*  82:    */   {
/*  83:148 */     this.idOrganizacion = idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdSucursal()
/*  87:    */   {
/*  88:157 */     return this.idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdSucursal(int idSucursal)
/*  92:    */   {
/*  93:167 */     this.idSucursal = idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public TipoProrrateoEnum getTipoProrrateoEnum()
/*  97:    */   {
/*  98:176 */     return this.tipoProrrateoEnum;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setTipoProrrateoEnum(TipoProrrateoEnum tipoProrrateoEnum)
/* 102:    */   {
/* 103:186 */     this.tipoProrrateoEnum = tipoProrrateoEnum;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDescripcion()
/* 107:    */   {
/* 108:195 */     return this.descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDescripcion(String descripcion)
/* 112:    */   {
/* 113:205 */     this.descripcion = descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public BigDecimal getValorReal()
/* 117:    */   {
/* 118:214 */     return this.valorReal;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setValorReal(BigDecimal valorReal)
/* 122:    */   {
/* 123:224 */     this.valorReal = valorReal;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public BigDecimal getValorPresupuesto()
/* 127:    */   {
/* 128:233 */     return this.valorPresupuesto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setValorPresupuesto(BigDecimal valorPresupuesto)
/* 132:    */   {
/* 133:243 */     this.valorPresupuesto = valorPresupuesto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 137:    */   {
/* 138:252 */     return this.facturaProveedorImportacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 142:    */   {
/* 143:262 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Moneda getMoneda()
/* 147:    */   {
/* 148:271 */     return this.moneda;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setMoneda(Moneda moneda)
/* 152:    */   {
/* 153:281 */     this.moneda = moneda;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public GastoImportacion getGastoImportacion()
/* 157:    */   {
/* 158:290 */     return this.gastoImportacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setGastoImportacion(GastoImportacion gastoImportacion)
/* 162:    */   {
/* 163:300 */     this.gastoImportacion = gastoImportacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Date getFechaGasto()
/* 167:    */   {
/* 168:309 */     return this.fechaGasto;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setFechaGasto(Date fechaGasto)
/* 172:    */   {
/* 173:319 */     this.fechaGasto = fechaGasto;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal getTraValorManual()
/* 177:    */   {
/* 178:323 */     return this.traValorManual;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setTraValorManual(BigDecimal traValorManual)
/* 182:    */   {
/* 183:327 */     this.traValorManual = traValorManual;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int getId()
/* 187:    */   {
/* 188:337 */     return this.idDetalleFacturaProveedorImportacionGasto;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto
 * JD-Core Version:    0.7.0.1
 */