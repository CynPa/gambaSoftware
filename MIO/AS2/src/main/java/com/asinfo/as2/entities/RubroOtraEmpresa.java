/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
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
/*  18:    */ import javax.validation.constraints.DecimalMin;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="rubro_otra_empresa", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"anio", "id_empleado", "mes"})})
/*  25:    */ public class RubroOtraEmpresa
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 3973177329916577975L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="rubro_otra_empresa", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rubro_otra_empresa")
/*  33:    */   @Column(name="id_rubro_otra_empresa", unique=true, nullable=false)
/*  34:    */   private int idRubroOtraEmpresa;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="anio", nullable=false)
/*  40:    */   private int anio;
/*  41:    */   @Column(name="mes", nullable=false)
/*  42:    */   @Enumerated(EnumType.ORDINAL)
/*  43:    */   private Mes mes;
/*  44:    */   @Column(name="valor_ingreso", nullable=false, precision=12, scale=2)
/*  45:    */   @Digits(integer=12, fraction=2)
/*  46:    */   @DecimalMin("0.01")
/*  47:    */   @NotNull
/*  48: 64 */   private BigDecimal valorIngreso = BigDecimal.ZERO;
/*  49:    */   @Column(name="valor_retenido", nullable=true, precision=12, scale=2)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52: 70 */   private BigDecimal valorRetenido = BigDecimal.ZERO;
/*  53:    */   @Column(name="aporte_personal_iess_otro_empleador", nullable=true, precision=12, scale=2)
/*  54:    */   @Digits(integer=12, fraction=2)
/*  55:    */   @DecimalMin("0.01")
/*  56:    */   @NotNull
/*  57: 75 */   private BigDecimal aportePersonalIessOtroEmpleador = BigDecimal.ZERO;
/*  58:    */   @Column(name="sueldo_agravada_contribucion")
/*  59:    */   @Digits(integer=12, fraction=2)
/*  60:    */   @Min(0L)
/*  61: 83 */   private BigDecimal sueldoAgravadaContribucion = BigDecimal.ZERO;
/*  62:    */   @Column(name="meses_trabajados_vigencia_contribucion")
/*  63:    */   @Min(0L)
/*  64: 88 */   private long mesesTrabajadosVigenciaContribucion = 0L;
/*  65:    */   @Column(name="meses_trabajados_contribucion")
/*  66:    */   @Min(0L)
/*  67: 92 */   private long mesesTrabajadosContribucion = 0L;
/*  68:    */   private transient String cedula;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_empleado", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private Empleado empleado;
/*  73:    */   
/*  74:    */   public RubroOtraEmpresa() {}
/*  75:    */   
/*  76:    */   public RubroOtraEmpresa(int idRubroOtraEmpresa, int idOrganizacion, int idSucursal, int anio, Mes mes, BigDecimal valorIngreso, BigDecimal aportePersonalIessOtroEmpleador, BigDecimal valorRetenido, String cedula)
/*  77:    */   {
/*  78:117 */     this.idRubroOtraEmpresa = idRubroOtraEmpresa;
/*  79:118 */     this.idOrganizacion = idOrganizacion;
/*  80:119 */     this.idSucursal = idSucursal;
/*  81:120 */     this.anio = anio;
/*  82:121 */     this.mes = mes;
/*  83:122 */     this.valorIngreso = valorIngreso;
/*  84:123 */     this.aportePersonalIessOtroEmpleador = aportePersonalIessOtroEmpleador;
/*  85:124 */     this.valorRetenido = valorRetenido;
/*  86:125 */     this.cedula = cedula;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getId()
/*  90:    */   {
/*  91:135 */     return this.idRubroOtraEmpresa;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdRubroOtraEmpresa()
/*  95:    */   {
/*  96:144 */     return this.idRubroOtraEmpresa;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdRubroOtraEmpresa(int idRubroOtraEmpresa)
/* 100:    */   {
/* 101:154 */     this.idRubroOtraEmpresa = idRubroOtraEmpresa;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdOrganizacion()
/* 105:    */   {
/* 106:163 */     return this.idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdOrganizacion(int idOrganizacion)
/* 110:    */   {
/* 111:173 */     this.idOrganizacion = idOrganizacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getIdSucursal()
/* 115:    */   {
/* 116:182 */     return this.idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdSucursal(int idSucursal)
/* 120:    */   {
/* 121:192 */     this.idSucursal = idSucursal;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getAnio()
/* 125:    */   {
/* 126:201 */     return this.anio;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setAnio(int anio)
/* 130:    */   {
/* 131:211 */     this.anio = anio;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Empleado getEmpleado()
/* 135:    */   {
/* 136:220 */     return this.empleado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEmpleado(Empleado empleado)
/* 140:    */   {
/* 141:230 */     this.empleado = empleado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getValorIngreso()
/* 145:    */   {
/* 146:239 */     return this.valorIngreso;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setValorIngreso(BigDecimal valorIngreso)
/* 150:    */   {
/* 151:249 */     this.valorIngreso = valorIngreso;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getValorRetenido()
/* 155:    */   {
/* 156:258 */     return this.valorRetenido;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setValorRetenido(BigDecimal valorRetenido)
/* 160:    */   {
/* 161:268 */     this.valorRetenido = valorRetenido;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Mes getMes()
/* 165:    */   {
/* 166:277 */     return this.mes;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setMes(Mes mes)
/* 170:    */   {
/* 171:287 */     this.mes = mes;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getCedula()
/* 175:    */   {
/* 176:294 */     return this.cedula;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setCedula(String cedula)
/* 180:    */   {
/* 181:302 */     this.cedula = cedula;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public BigDecimal getAportePersonalIessOtroEmpleador()
/* 185:    */   {
/* 186:309 */     return this.aportePersonalIessOtroEmpleador;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setAportePersonalIessOtroEmpleador(BigDecimal aportePersonalIessOtroEmpleador)
/* 190:    */   {
/* 191:317 */     this.aportePersonalIessOtroEmpleador = aportePersonalIessOtroEmpleador;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public BigDecimal getSueldoAgravadaContribucion()
/* 195:    */   {
/* 196:323 */     return this.sueldoAgravadaContribucion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setSueldoAgravadaContribucion(BigDecimal sueldoAgravadaContribucion)
/* 200:    */   {
/* 201:327 */     this.sueldoAgravadaContribucion = sueldoAgravadaContribucion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public long getMesesTrabajadosVigenciaContribucion()
/* 205:    */   {
/* 206:331 */     return this.mesesTrabajadosVigenciaContribucion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setMesesTrabajadosVigenciaContribucion(long mesesTrabajadosVigenciaContribucion)
/* 210:    */   {
/* 211:335 */     this.mesesTrabajadosVigenciaContribucion = mesesTrabajadosVigenciaContribucion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public long getMesesTrabajadosContribucion()
/* 215:    */   {
/* 216:339 */     return this.mesesTrabajadosContribucion;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setMesesTrabajadosContribucion(long mesesTrabajadosContribucion)
/* 220:    */   {
/* 221:343 */     this.mesesTrabajadosContribucion = mesesTrabajadosContribucion;
/* 222:    */   }
/* 223:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RubroOtraEmpresa
 * JD-Core Version:    0.7.0.1
 */