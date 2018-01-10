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
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="gasto_deducibleSRI")
/*  23:    */ public class GastoDeducibleSRI
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="gasto_deducibleSRI", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="gasto_deducibleSRI")
/*  31:    */   @Column(name="id_gasto_deducibleSRI")
/*  32:    */   private int idGastoDeducibleSRI;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="valor_vivienda", precision=12, scale=2)
/*  38:    */   @Digits(integer=12, fraction=2)
/*  39:    */   @Min(0L)
/*  40: 62 */   private BigDecimal valorVivienda = BigDecimal.ZERO;
/*  41:    */   @Column(name="valor_salud", precision=12, scale=2)
/*  42:    */   @Digits(integer=12, fraction=2)
/*  43:    */   @Min(0L)
/*  44: 67 */   private BigDecimal valorSalud = BigDecimal.ZERO;
/*  45:    */   @Column(name="valor_Educacion", precision=12, scale=2)
/*  46:    */   @Digits(integer=12, fraction=2)
/*  47:    */   @Min(0L)
/*  48: 72 */   private BigDecimal valorEducacion = BigDecimal.ZERO;
/*  49:    */   @Column(name="valor_alimentacion", precision=12, scale=2)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52: 77 */   private BigDecimal valorAlimentacion = BigDecimal.ZERO;
/*  53:    */   @Column(name="valor_vestimenta", precision=12, scale=2)
/*  54:    */   @Digits(integer=12, fraction=2)
/*  55:    */   @Min(0L)
/*  56: 82 */   private BigDecimal valorVestimenta = BigDecimal.ZERO;
/*  57:    */   @Column(name="anio", nullable=false)
/*  58:    */   @Min(0L)
/*  59:    */   private int anio;
/*  60:    */   @ColumnDefault("0")
/*  61:    */   @Column(name="total_gastos_deducibles", precision=12, scale=2)
/*  62:    */   @Digits(integer=12, fraction=2)
/*  63:    */   @Min(0L)
/*  64:    */   @NotNull
/*  65: 91 */   private BigDecimal totalGastosDeducibles = BigDecimal.ZERO;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_empleado", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private Empleado empleado;
/*  70:    */   @Transient
/*  71:    */   private String apellidos;
/*  72:    */   @Transient
/*  73:    */   private String nombres;
/*  74:    */   
/*  75:    */   public GastoDeducibleSRI() {}
/*  76:    */   
/*  77:    */   public GastoDeducibleSRI(int idGastoDeducibleSRI, BigDecimal valorAlimentacion, BigDecimal valorEducacion, BigDecimal valorSalud, BigDecimal valorVestimenta, BigDecimal valorVivienda, String apellidos, int idEmpleado, String nombres)
/*  78:    */   {
/*  79:129 */     this.idGastoDeducibleSRI = idGastoDeducibleSRI;
/*  80:130 */     this.valorAlimentacion = valorAlimentacion;
/*  81:131 */     this.valorEducacion = valorEducacion;
/*  82:132 */     this.valorSalud = valorSalud;
/*  83:133 */     this.valorVestimenta = valorVestimenta;
/*  84:134 */     this.valorVivienda = valorVivienda;
/*  85:135 */     Empleado empleado = new Empleado();
/*  86:136 */     empleado.setIdEmpleado(idEmpleado);
/*  87:137 */     this.empleado = empleado;
/*  88:138 */     this.apellidos = apellidos;
/*  89:139 */     this.nombres = nombres;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdGastoDeducibleSRI()
/*  93:    */   {
/*  94:152 */     return this.idGastoDeducibleSRI;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdGastoDeducibleSRI(int idGastoDeducibleSRI)
/*  98:    */   {
/*  99:162 */     this.idGastoDeducibleSRI = idGastoDeducibleSRI;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIdOrganizacion()
/* 103:    */   {
/* 104:171 */     return this.idOrganizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdOrganizacion(int idOrganizacion)
/* 108:    */   {
/* 109:181 */     this.idOrganizacion = idOrganizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getIdSucursal()
/* 113:    */   {
/* 114:190 */     return this.idSucursal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setIdSucursal(int idSucursal)
/* 118:    */   {
/* 119:200 */     this.idSucursal = idSucursal;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getAnio()
/* 123:    */   {
/* 124:209 */     return this.anio;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setAnio(int anio)
/* 128:    */   {
/* 129:219 */     this.anio = anio;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Empleado getEmpleado()
/* 133:    */   {
/* 134:228 */     return this.empleado;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setEmpleado(Empleado empleado)
/* 138:    */   {
/* 139:238 */     this.empleado = empleado;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public BigDecimal getValorVivienda()
/* 143:    */   {
/* 144:247 */     return this.valorVivienda;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setValorVivienda(BigDecimal valorVivienda)
/* 148:    */   {
/* 149:257 */     this.valorVivienda = valorVivienda;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public BigDecimal getValorSalud()
/* 153:    */   {
/* 154:266 */     return this.valorSalud;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setValorSalud(BigDecimal valorSalud)
/* 158:    */   {
/* 159:276 */     this.valorSalud = valorSalud;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public BigDecimal getValorEducacion()
/* 163:    */   {
/* 164:285 */     return this.valorEducacion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setValorEducacion(BigDecimal valorEducacion)
/* 168:    */   {
/* 169:295 */     this.valorEducacion = valorEducacion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public BigDecimal getValorAlimentacion()
/* 173:    */   {
/* 174:304 */     return this.valorAlimentacion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setValorAlimentacion(BigDecimal valorAlimentacion)
/* 178:    */   {
/* 179:314 */     this.valorAlimentacion = valorAlimentacion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public BigDecimal getValorVestimenta()
/* 183:    */   {
/* 184:323 */     return this.valorVestimenta;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setValorVestimenta(BigDecimal valorVestimenta)
/* 188:    */   {
/* 189:333 */     this.valorVestimenta = valorVestimenta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getApellidos()
/* 193:    */   {
/* 194:337 */     return this.apellidos;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setApellidos(String apellidos)
/* 198:    */   {
/* 199:341 */     this.apellidos = apellidos;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String getNombres()
/* 203:    */   {
/* 204:345 */     return this.nombres;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setNombres(String nombres)
/* 208:    */   {
/* 209:349 */     this.nombres = nombres;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int getId()
/* 213:    */   {
/* 214:354 */     return this.idGastoDeducibleSRI;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public BigDecimal getTotalGastosDeducibles()
/* 218:    */   {
/* 219:358 */     return this.totalGastosDeducibles;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setTotalGastosDeducibles(BigDecimal totalGastosDeducibles)
/* 223:    */   {
/* 224:362 */     this.totalGastosDeducibles = totalGastosDeducibles;
/* 225:    */   }
/* 226:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GastoDeducibleSRI
 * JD-Core Version:    0.7.0.1
 */