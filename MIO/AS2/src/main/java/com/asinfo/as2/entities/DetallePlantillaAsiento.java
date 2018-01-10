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
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_plantilla_asiento")
/*  21:    */ public class DetallePlantillaAsiento
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_plantilla_asiento", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_plantilla_asiento")
/*  29:    */   @Column(name="id_detalle_plantilla_asiento", unique=true, nullable=false)
/*  30:    */   private int idDetallePlantillaAsiento;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  33:    */   private Asiento asiento;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_plantillaAsiento", nullable=true)
/*  40:    */   private PlantillaAsiento plantillaAsiento;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private CuentaContable cuentaContable;
/*  45:    */   @Column(name="debe", precision=12, scale=2)
/*  46:    */   @Digits(integer=12, fraction=2)
/*  47:    */   @Min(0L)
/*  48:    */   private BigDecimal debe;
/*  49:    */   @Column(name="haber", precision=12, scale=2)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52:    */   private BigDecimal haber;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_dimension_contable1")
/*  55:    */   private DimensionContable dimensionContable1;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_dimension_contable2")
/*  58:    */   private DimensionContable dimensionContable2;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_dimension_contable3")
/*  61:    */   private DimensionContable dimensionContable3;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_dimension_contable4")
/*  64:    */   private DimensionContable dimensionContable4;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_dimension_contable5")
/*  67:    */   private DimensionContable dimensionContable5;
/*  68:    */   
/*  69:    */   public int getId()
/*  70:    */   {
/*  71:106 */     return this.idDetallePlantillaAsiento;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdDetallePlantillaAsiento()
/*  75:    */   {
/*  76:110 */     return this.idDetallePlantillaAsiento;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdDetallePlantillaAsiento(int idDetallePlantillaAsiento)
/*  80:    */   {
/*  81:114 */     this.idDetallePlantillaAsiento = idDetallePlantillaAsiento;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Asiento getAsiento()
/*  85:    */   {
/*  86:118 */     return this.asiento;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setAsiento(Asiento asiento)
/*  90:    */   {
/*  91:122 */     this.asiento = asiento;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdOrganizacion()
/*  95:    */   {
/*  96:126 */     return this.idOrganizacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdOrganizacion(int idOrganizacion)
/* 100:    */   {
/* 101:130 */     this.idOrganizacion = idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdSucursal()
/* 105:    */   {
/* 106:134 */     return this.idSucursal;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdSucursal(int idSucursal)
/* 110:    */   {
/* 111:138 */     this.idSucursal = idSucursal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public PlantillaAsiento getPlantillaAsiento()
/* 115:    */   {
/* 116:142 */     return this.plantillaAsiento;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setPlantillaAsiento(PlantillaAsiento plantillaAsiento)
/* 120:    */   {
/* 121:146 */     this.plantillaAsiento = plantillaAsiento;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public CuentaContable getCuentaContable()
/* 125:    */   {
/* 126:150 */     return this.cuentaContable;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 130:    */   {
/* 131:154 */     this.cuentaContable = cuentaContable;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getDebe()
/* 135:    */   {
/* 136:158 */     return this.debe;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDebe(BigDecimal debe)
/* 140:    */   {
/* 141:162 */     this.debe = debe;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getHaber()
/* 145:    */   {
/* 146:166 */     return this.haber;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setHaber(BigDecimal haber)
/* 150:    */   {
/* 151:170 */     this.haber = haber;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public DimensionContable getDimensionContable1()
/* 155:    */   {
/* 156:174 */     return this.dimensionContable1;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/* 160:    */   {
/* 161:178 */     this.dimensionContable1 = dimensionContable1;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public DimensionContable getDimensionContable2()
/* 165:    */   {
/* 166:182 */     return this.dimensionContable2;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 170:    */   {
/* 171:186 */     this.dimensionContable2 = dimensionContable2;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public DimensionContable getDimensionContable3()
/* 175:    */   {
/* 176:190 */     return this.dimensionContable3;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 180:    */   {
/* 181:194 */     this.dimensionContable3 = dimensionContable3;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public DimensionContable getDimensionContable4()
/* 185:    */   {
/* 186:198 */     return this.dimensionContable4;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 190:    */   {
/* 191:202 */     this.dimensionContable4 = dimensionContable4;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public DimensionContable getDimensionContable5()
/* 195:    */   {
/* 196:206 */     return this.dimensionContable5;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 200:    */   {
/* 201:210 */     this.dimensionContable5 = dimensionContable5;
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePlantillaAsiento
 * JD-Core Version:    0.7.0.1
 */