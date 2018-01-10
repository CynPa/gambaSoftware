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
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="factura_proveedor_gasto_importacion")
/*  25:    */ public class FacturaProveedorGastoImportacion
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="factura_proveedor_gasto_importacion", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_proveedor_gasto_importacion")
/*  32:    */   @Column(name="id_factura_proveedor_gasto_importacion")
/*  33:    */   private int idFacturaProveedorGastoImportacion;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="tipo_prorrateo", nullable=false)
/*  39:    */   @Enumerated(EnumType.ORDINAL)
/*  40:    */   private TipoProrrateoEnum tipoProrrateoEnum;
/*  41:    */   @Column(name="descripcion", length=200, nullable=true)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   @Column(name="fecha_gasto", nullable=false)
/*  46:    */   private Date fechaGasto;
/*  47:    */   @Column(name="valor_real", nullable=false, precision=12, scale=2)
/*  48:    */   @Min(0L)
/*  49:    */   private BigDecimal valorReal;
/*  50:    */   @Column(name="valor_presupuesto", nullable=false, precision=12, scale=2)
/*  51:    */   @Min(0L)
/*  52:    */   private BigDecimal valorPresupuesto;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_factura_proveedor_importacion")
/*  55:    */   private FacturaProveedorImportacion facturaProveedorImportacion;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_moneda")
/*  58:    */   private Moneda moneda;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_gasto_importacion")
/*  61:    */   private GastoImportacion gastoImportacion;
/*  62:    */   
/*  63:    */   public int getIdFacturaProveedorGastoImportacion()
/*  64:    */   {
/*  65:115 */     return this.idFacturaProveedorGastoImportacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdFacturaProveedorGastoImportacion(int idFacturaProveedorGastoImportacion)
/*  69:    */   {
/*  70:126 */     this.idFacturaProveedorGastoImportacion = idFacturaProveedorGastoImportacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:135 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:145 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:154 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:164 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public TipoProrrateoEnum getTipoProrrateoEnum()
/*  94:    */   {
/*  95:173 */     return this.tipoProrrateoEnum;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setTipoProrrateoEnum(TipoProrrateoEnum tipoProrrateoEnum)
/*  99:    */   {
/* 100:183 */     this.tipoProrrateoEnum = tipoProrrateoEnum;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDescripcion()
/* 104:    */   {
/* 105:192 */     return this.descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDescripcion(String descripcion)
/* 109:    */   {
/* 110:202 */     this.descripcion = descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BigDecimal getValorReal()
/* 114:    */   {
/* 115:211 */     return this.valorReal;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setValorReal(BigDecimal valorReal)
/* 119:    */   {
/* 120:221 */     this.valorReal = valorReal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public BigDecimal getValorPresupuesto()
/* 124:    */   {
/* 125:230 */     return this.valorPresupuesto;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setValorPresupuesto(BigDecimal valorPresupuesto)
/* 129:    */   {
/* 130:240 */     this.valorPresupuesto = valorPresupuesto;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public FacturaProveedorImportacion getFacturaProveedorImportacion()
/* 134:    */   {
/* 135:250 */     return this.facturaProveedorImportacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFacturaProveedorImportacion(FacturaProveedorImportacion facturaProveedorImportacion)
/* 139:    */   {
/* 140:258 */     this.facturaProveedorImportacion = facturaProveedorImportacion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Moneda getMoneda()
/* 144:    */   {
/* 145:267 */     return this.moneda;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setMoneda(Moneda moneda)
/* 149:    */   {
/* 150:277 */     this.moneda = moneda;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public GastoImportacion getGastoImportacion()
/* 154:    */   {
/* 155:286 */     return this.gastoImportacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setGastoImportacion(GastoImportacion gastoImportacion)
/* 159:    */   {
/* 160:296 */     this.gastoImportacion = gastoImportacion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Date getFechaGasto()
/* 164:    */   {
/* 165:305 */     return this.fechaGasto;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setFechaGasto(Date fechaGasto)
/* 169:    */   {
/* 170:315 */     this.fechaGasto = fechaGasto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getId()
/* 174:    */   {
/* 175:325 */     return this.idFacturaProveedorGastoImportacion;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FacturaProveedorGastoImportacion
 * JD-Core Version:    0.7.0.1
 */