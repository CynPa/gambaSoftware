/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ import org.hibernate.annotations.ColumnDefault;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="responsable_salida_mercaderia", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class PersonaResponsable
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="responsable_salida_mercaderia", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="responsable_salida_mercaderia")
/*  26:    */   @Column(name="id_responsable_salida_mercaderia")
/*  27:    */   private int idPersonaResponsable;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", length=10, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_tipo_identificacion", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private TipoIdentificacion tipoIdentificacion;
/*  40:    */   @Column(name="identificacion", nullable=false, length=20)
/*  41:    */   @Size(min=10, max=20)
/*  42:    */   private String identificacion;
/*  43:    */   @Column(name="nombres", nullable=false)
/*  44:    */   @Size(min=3, max=50)
/*  45:    */   private String nombres;
/*  46:    */   @Column(name="apellidos", nullable=false)
/*  47:    */   @Size(min=3, max=50)
/*  48:    */   private String apellidos;
/*  49:    */   @Column(name="descripcion", length=200)
/*  50:    */   @Size(max=200)
/*  51:    */   private String descripcion;
/*  52:    */   @Column(name="indicador_externo", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   @ColumnDefault("'0'")
/*  55:    */   private boolean indicadorExterno;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   @Column(name="indicador_salida_mercaderia", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   @ColumnDefault("'1'")
/*  63:    */   private boolean indicadorSalidaMercaderia;
/*  64:    */   @Column(name="indicador_mantenimiento", nullable=false)
/*  65:    */   @NotNull
/*  66:    */   @ColumnDefault("'0'")
/*  67:    */   private boolean indicadorMantenimiento;
/*  68:    */   @Column(name="indicador_orden_fabricacion", nullable=false)
/*  69:    */   @NotNull
/*  70:    */   @ColumnDefault("'0'")
/*  71:    */   private boolean indicadorOrdenFabricacion;
/*  72:    */   @Column(name="indicador_compra", nullable=false)
/*  73:    */   @NotNull
/*  74:    */   @ColumnDefault("'0'")
/*  75:    */   private boolean indicadorCompra;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_especialidad", nullable=true)
/*  78:    */   private Especialidad especialidad;
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  81:    */   private Empresa proveedor;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  84:    */   private Empleado empleado;
/*  85:    */   
/*  86:    */   public PersonaResponsable() {}
/*  87:    */   
/*  88:    */   public PersonaResponsable(int idOrganizacion, int idSucursal, String codigo, TipoIdentificacion tipoIdentificacion, String identificacion, String nombres, String apellidos, String descripcion, boolean activo, boolean predeterminado)
/*  89:    */   {
/*  90:160 */     this.idOrganizacion = idOrganizacion;
/*  91:161 */     this.idSucursal = idSucursal;
/*  92:162 */     this.codigo = codigo;
/*  93:163 */     this.tipoIdentificacion = tipoIdentificacion;
/*  94:164 */     this.identificacion = identificacion;
/*  95:165 */     this.nombres = nombres;
/*  96:166 */     this.apellidos = apellidos;
/*  97:167 */     this.descripcion = descripcion;
/*  98:168 */     this.activo = activo;
/*  99:169 */     this.predeterminado = predeterminado;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getId()
/* 103:    */   {
/* 104:183 */     return this.idPersonaResponsable;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIdPersonaResponsable()
/* 108:    */   {
/* 109:187 */     return this.idPersonaResponsable;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setIdPersonaResponsable(int idPersonaResponsable)
/* 113:    */   {
/* 114:191 */     this.idPersonaResponsable = idPersonaResponsable;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getIdOrganizacion()
/* 118:    */   {
/* 119:198 */     return this.idOrganizacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdOrganizacion(int idOrganizacion)
/* 123:    */   {
/* 124:206 */     this.idOrganizacion = idOrganizacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getIdSucursal()
/* 128:    */   {
/* 129:213 */     return this.idSucursal;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdSucursal(int idSucursal)
/* 133:    */   {
/* 134:221 */     this.idSucursal = idSucursal;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getCodigo()
/* 138:    */   {
/* 139:228 */     return this.codigo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setCodigo(String codigo)
/* 143:    */   {
/* 144:236 */     this.codigo = codigo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getIdentificacion()
/* 148:    */   {
/* 149:243 */     return this.identificacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIdentificacion(String identificacion)
/* 153:    */   {
/* 154:251 */     this.identificacion = identificacion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getNombres()
/* 158:    */   {
/* 159:258 */     return this.nombres;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setNombres(String nombres)
/* 163:    */   {
/* 164:266 */     this.nombres = nombres;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getApellidos()
/* 168:    */   {
/* 169:273 */     return this.apellidos;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setApellidos(String apellidos)
/* 173:    */   {
/* 174:281 */     this.apellidos = apellidos;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getDescripcion()
/* 178:    */   {
/* 179:288 */     return this.descripcion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDescripcion(String descripcion)
/* 183:    */   {
/* 184:296 */     this.descripcion = descripcion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isActivo()
/* 188:    */   {
/* 189:303 */     return this.activo;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setActivo(boolean activo)
/* 193:    */   {
/* 194:311 */     this.activo = activo;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isPredeterminado()
/* 198:    */   {
/* 199:318 */     return this.predeterminado;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setPredeterminado(boolean predeterminado)
/* 203:    */   {
/* 204:326 */     this.predeterminado = predeterminado;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public TipoIdentificacion getTipoIdentificacion()
/* 208:    */   {
/* 209:333 */     return this.tipoIdentificacion;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/* 213:    */   {
/* 214:341 */     this.tipoIdentificacion = tipoIdentificacion;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Especialidad getEspecialidad()
/* 218:    */   {
/* 219:345 */     return this.especialidad;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setEspecialidad(Especialidad especialidad)
/* 223:    */   {
/* 224:349 */     this.especialidad = especialidad;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isIndicadorExterno()
/* 228:    */   {
/* 229:353 */     return this.indicadorExterno;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setIndicadorExterno(boolean indicadorExterno)
/* 233:    */   {
/* 234:357 */     this.indicadorExterno = indicadorExterno;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Empresa getProveedor()
/* 238:    */   {
/* 239:361 */     return this.proveedor;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setProveedor(Empresa proveedor)
/* 243:    */   {
/* 244:365 */     this.proveedor = proveedor;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Empleado getEmpleado()
/* 248:    */   {
/* 249:369 */     return this.empleado;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setEmpleado(Empleado empleado)
/* 253:    */   {
/* 254:373 */     this.empleado = empleado;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean isIndicadorSalidaMercaderia()
/* 258:    */   {
/* 259:377 */     return this.indicadorSalidaMercaderia;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setIndicadorSalidaMercaderia(boolean indicadorSalidaMercaderia)
/* 263:    */   {
/* 264:381 */     this.indicadorSalidaMercaderia = indicadorSalidaMercaderia;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isIndicadorMantenimiento()
/* 268:    */   {
/* 269:385 */     return this.indicadorMantenimiento;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setIndicadorMantenimiento(boolean indicadorMantenimiento)
/* 273:    */   {
/* 274:389 */     this.indicadorMantenimiento = indicadorMantenimiento;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean isIndicadorOrdenFabricacion()
/* 278:    */   {
/* 279:393 */     return this.indicadorOrdenFabricacion;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setIndicadorOrdenFabricacion(boolean indicadorOrdenFabricacion)
/* 283:    */   {
/* 284:397 */     this.indicadorOrdenFabricacion = indicadorOrdenFabricacion;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public boolean isIndicadorCompra()
/* 288:    */   {
/* 289:401 */     return this.indicadorCompra;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setIndicadorCompra(boolean indicadorCompra)
/* 293:    */   {
/* 294:405 */     this.indicadorCompra = indicadorCompra;
/* 295:    */   }
/* 296:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PersonaResponsable
 * JD-Core Version:    0.7.0.1
 */