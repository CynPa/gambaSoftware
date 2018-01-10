/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Genero;
/*   4:    */ import com.asinfo.as2.enumeraciones.Parentezco;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.Date;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.Digits;
/*  24:    */ import javax.validation.constraints.Max;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="carga_empleado")
/*  31:    */ public class CargaEmpleado
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 3823508771974994511L;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="carga_empleado", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="carga_empleado")
/*  42:    */   @Column(name="id_carga_empleado")
/*  43:    */   private int idCargaEmpleado;
/*  44:    */   @Column(name="nombre", nullable=false, length=50)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=2, max=50)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="identificacion", nullable=true, length=20)
/*  49:    */   @Size(max=20)
/*  50:    */   private String identificacion;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha_nacimiento", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private Date fechaNacimiento;
/*  55:    */   @Column(name="genero", nullable=false)
/*  56:    */   @Enumerated(EnumType.ORDINAL)
/*  57:    */   private Genero genero;
/*  58:    */   @Column(name="parentezco", nullable=false)
/*  59:    */   @Enumerated(EnumType.ORDINAL)
/*  60:    */   private Parentezco parentezco;
/*  61:    */   @Column(name="porcentaje_discapacidad", nullable=true, precision=12, scale=2)
/*  62:    */   @Digits(integer=12, fraction=2)
/*  63:    */   @Min(0L)
/*  64:    */   @Max(100L)
/*  65: 93 */   private BigDecimal porcentajeDiscapacidad = BigDecimal.ZERO;
/*  66:    */   @Column(name="descripcion", nullable=true)
/*  67:    */   @Size(max=200)
/*  68:    */   private String descripcion;
/*  69:    */   @Column(name="indicador_discapacidad", nullable=false)
/*  70:    */   private boolean indicadorDiscapacidad;
/*  71:    */   @Column(name="activo", nullable=false)
/*  72:106 */   private boolean activo = true;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  75:    */   private Empleado empleado;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_tipo_discapacidad", nullable=true)
/*  78:    */   private TipoDiscapacidad tipoDiscapacidad;
/*  79:    */   @Transient
/*  80:    */   private Integer edad;
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:147 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:157 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94:166 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:176 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIdCargaEmpleado()
/* 103:    */   {
/* 104:185 */     return this.idCargaEmpleado;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdCargaEmpleado(int idCargaEmpleado)
/* 108:    */   {
/* 109:195 */     this.idCargaEmpleado = idCargaEmpleado;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombre()
/* 113:    */   {
/* 114:204 */     return this.nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombre(String nombre)
/* 118:    */   {
/* 119:214 */     this.nombre = nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getIdentificacion()
/* 123:    */   {
/* 124:223 */     return this.identificacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setIdentificacion(String identificacion)
/* 128:    */   {
/* 129:233 */     this.identificacion = identificacion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Date getFechaNacimiento()
/* 133:    */   {
/* 134:242 */     return this.fechaNacimiento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFechaNacimiento(Date fechaNacimiento)
/* 138:    */   {
/* 139:252 */     this.fechaNacimiento = fechaNacimiento;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Genero getGenero()
/* 143:    */   {
/* 144:261 */     return this.genero;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setGenero(Genero genero)
/* 148:    */   {
/* 149:271 */     this.genero = genero;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Parentezco getParentezco()
/* 153:    */   {
/* 154:280 */     return this.parentezco;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setParentezco(Parentezco parentezco)
/* 158:    */   {
/* 159:290 */     this.parentezco = parentezco;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getDescripcion()
/* 163:    */   {
/* 164:299 */     return this.descripcion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setDescripcion(String descripcion)
/* 168:    */   {
/* 169:309 */     this.descripcion = descripcion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isActivo()
/* 173:    */   {
/* 174:318 */     return this.activo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setActivo(boolean activo)
/* 178:    */   {
/* 179:328 */     this.activo = activo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Empleado getEmpleado()
/* 183:    */   {
/* 184:337 */     return this.empleado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setEmpleado(Empleado empleado)
/* 188:    */   {
/* 189:347 */     this.empleado = empleado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Integer getEdad()
/* 193:    */   {
/* 194:356 */     if (getFechaNacimiento() == null) {
/* 195:357 */       this.edad = new Integer(0);
/* 196:    */     } else {
/* 197:359 */       this.edad = Integer.valueOf(FuncionesUtiles.obtenerEdad(getFechaNacimiento()));
/* 198:    */     }
/* 199:361 */     return this.edad;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setEdad(Integer edad)
/* 203:    */   {
/* 204:371 */     this.edad = edad;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getId()
/* 208:    */   {
/* 209:381 */     return this.idCargaEmpleado;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean isIndicadorDiscapacidad()
/* 213:    */   {
/* 214:390 */     return this.indicadorDiscapacidad;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setIndicadorDiscapacidad(boolean indicadorDiscapacidad)
/* 218:    */   {
/* 219:400 */     setActivo(indicadorDiscapacidad ? indicadorDiscapacidad : this.activo);
/* 220:401 */     this.indicadorDiscapacidad = indicadorDiscapacidad;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public TipoDiscapacidad getTipoDiscapacidad()
/* 224:    */   {
/* 225:410 */     return this.tipoDiscapacidad;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad)
/* 229:    */   {
/* 230:420 */     this.tipoDiscapacidad = tipoDiscapacidad;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public BigDecimal getPorcentajeDiscapacidad()
/* 234:    */   {
/* 235:429 */     return this.porcentajeDiscapacidad;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setPorcentajeDiscapacidad(BigDecimal porcentajeDiscapacidad)
/* 239:    */   {
/* 240:439 */     this.porcentajeDiscapacidad = porcentajeDiscapacidad;
/* 241:    */   }
/* 242:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CargaEmpleado
 * JD-Core Version:    0.7.0.1
 */