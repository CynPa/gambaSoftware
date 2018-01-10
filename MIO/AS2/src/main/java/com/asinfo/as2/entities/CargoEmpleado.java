/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="cargo_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class CargoEmpleado
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -7718262962617064415L;
/*  19:    */   @Column(name="id_organizacion", nullable=false)
/*  20:    */   private int idOrganizacion;
/*  21:    */   @Column(name="id_sucursal", nullable=false)
/*  22:    */   private int idSucursal;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="cargo_empleado", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cargo_empleado")
/*  26:    */   @Column(name="id_cargo_empleado")
/*  27:    */   private int idCargoEmpleado;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @Size(min=2, max=10)
/*  30:    */   @NotNull
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @Size(min=2, max=50)
/*  34:    */   @NotNull
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", nullable=true, length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   @Column(name="indicador_utilidades", nullable=false)
/*  44:    */   private boolean indicadorUtilidades;
/*  45:    */   @Column(name="indicador_decimos", nullable=false)
/*  46:    */   private boolean indicadorDecimos;
/*  47:    */   @Column(name="indicador_vacaciones", nullable=false)
/*  48:    */   private boolean indicadorVacaciones;
/*  49:    */   @Column(name="indicador_propina", nullable=false)
/*  50:    */   private boolean indicadorPropina;
/*  51:    */   @Column(name="indicador_registra_asistencia", nullable=false)
/*  52:    */   @NotNull
/*  53: 88 */   private Boolean indicadorRegistraAsistencia = Boolean.valueOf(false);
/*  54:    */   @Column(name="indicador_gana_horas_extras", nullable=false)
/*  55:    */   @NotNull
/*  56: 92 */   private Boolean indicadorGanaHorasExtras = Boolean.valueOf(false);
/*  57:    */   
/*  58:    */   public CargoEmpleado() {}
/*  59:    */   
/*  60:    */   public CargoEmpleado(int idCargoEmpleado, String codigo, String nombre)
/*  61:    */   {
/*  62:113 */     this.idCargoEmpleado = idCargoEmpleado;
/*  63:114 */     this.codigo = codigo;
/*  64:115 */     this.nombre = nombre;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdOrganizacion()
/*  68:    */   {
/*  69:128 */     return this.idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdOrganizacion(int idOrganizacion)
/*  73:    */   {
/*  74:138 */     this.idOrganizacion = idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdSucursal()
/*  78:    */   {
/*  79:147 */     return this.idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdSucursal(int idSucursal)
/*  83:    */   {
/*  84:157 */     this.idSucursal = idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdCargoEmpleado()
/*  88:    */   {
/*  89:166 */     return this.idCargoEmpleado;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdCargoEmpleado(int idCargoEmpleado)
/*  93:    */   {
/*  94:176 */     this.idCargoEmpleado = idCargoEmpleado;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getCodigo()
/*  98:    */   {
/*  99:185 */     return this.codigo;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setCodigo(String codigo)
/* 103:    */   {
/* 104:195 */     this.codigo = codigo;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getNombre()
/* 108:    */   {
/* 109:204 */     return this.nombre;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setNombre(String nombre)
/* 113:    */   {
/* 114:214 */     this.nombre = nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getDescripcion()
/* 118:    */   {
/* 119:223 */     return this.descripcion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setDescripcion(String descripcion)
/* 123:    */   {
/* 124:233 */     this.descripcion = descripcion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isActivo()
/* 128:    */   {
/* 129:242 */     return this.activo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setActivo(boolean activo)
/* 133:    */   {
/* 134:252 */     this.activo = activo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isPredeterminado()
/* 138:    */   {
/* 139:261 */     return this.predeterminado;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setPredeterminado(boolean predeterminado)
/* 143:    */   {
/* 144:271 */     this.predeterminado = predeterminado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isIndicadorUtilidades()
/* 148:    */   {
/* 149:280 */     return this.indicadorUtilidades;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorUtilidades(boolean indicadorUtilidades)
/* 153:    */   {
/* 154:290 */     this.indicadorUtilidades = indicadorUtilidades;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorDecimos()
/* 158:    */   {
/* 159:299 */     return this.indicadorDecimos;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorDecimos(boolean indicadorDecimos)
/* 163:    */   {
/* 164:309 */     this.indicadorDecimos = indicadorDecimos;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean isIndicadorVacaciones()
/* 168:    */   {
/* 169:318 */     return this.indicadorVacaciones;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setIndicadorVacaciones(boolean indicadorVacaciones)
/* 173:    */   {
/* 174:328 */     this.indicadorVacaciones = indicadorVacaciones;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean isIndicadorPropina()
/* 178:    */   {
/* 179:334 */     return this.indicadorPropina;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIndicadorPropina(boolean indicadorPropina)
/* 183:    */   {
/* 184:338 */     this.indicadorPropina = indicadorPropina;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getId()
/* 188:    */   {
/* 189:348 */     return this.idCargoEmpleado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isIndicadorRegistraAsistencia()
/* 193:    */   {
/* 194:352 */     if (this.indicadorRegistraAsistencia == null) {
/* 195:353 */       this.indicadorRegistraAsistencia = Boolean.valueOf(false);
/* 196:    */     }
/* 197:355 */     return this.indicadorRegistraAsistencia.booleanValue();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setIndicadorRegistraAsistencia(boolean indicadorRegistraAsistencia)
/* 201:    */   {
/* 202:359 */     this.indicadorRegistraAsistencia = Boolean.valueOf(indicadorRegistraAsistencia);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean isIndicadorGanaHorasExtras()
/* 206:    */   {
/* 207:363 */     if (this.indicadorGanaHorasExtras == null) {
/* 208:364 */       this.indicadorGanaHorasExtras = Boolean.valueOf(false);
/* 209:    */     }
/* 210:366 */     return this.indicadorGanaHorasExtras.booleanValue();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorGanaHorasExtras(boolean indicadorGanaHorasExtras)
/* 214:    */   {
/* 215:370 */     this.indicadorGanaHorasExtras = Boolean.valueOf(indicadorGanaHorasExtras);
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CargoEmpleado
 * JD-Core Version:    0.7.0.1
 */