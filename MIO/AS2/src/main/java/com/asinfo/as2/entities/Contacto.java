/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="contacto")
/*  19:    */ public class Contacto
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="contacto", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="contacto")
/*  26:    */   @Column(name="id_contacto", unique=true, nullable=false)
/*  27:    */   private int idContacto;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  32:    */   private Empresa empresa;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  35:    */   private Sucursal sucursal;
/*  36:    */   @NotNull
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_tipo_contacto", nullable=false)
/*  39:    */   private TipoContacto tipoContacto;
/*  40:    */   @Column(name="cargo", length=50, nullable=true)
/*  41:    */   @Size(min=1, max=50)
/*  42:    */   private String cargo;
/*  43:    */   @Column(name="nombre", nullable=false, length=100)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=1, max=100)
/*  46:    */   private String nombre;
/*  47:    */   @Column(name="telefono1", length=13, nullable=true)
/*  48:    */   @Size(max=13)
/*  49:    */   private String telefono1;
/*  50:    */   @Column(name="extension1", length=13, nullable=true)
/*  51:    */   @Size(max=13)
/*  52:    */   private String extension1;
/*  53:    */   @Column(name="telefono2", length=13, nullable=true)
/*  54:    */   @Size(max=13)
/*  55:    */   private String telefono2;
/*  56:    */   @Column(name="extension2", length=13, nullable=true)
/*  57:    */   @Size(max=13)
/*  58:    */   private String extension2;
/*  59:    */   @Emails
/*  60:    */   @Column(name="email1", nullable=true, length=100)
/*  61:    */   @Size(max=100)
/*  62:    */   private String email1;
/*  63:    */   @Emails
/*  64:    */   @Column(name="email2", nullable=true, length=100)
/*  65:    */   @Size(max=100)
/*  66:    */   private String email2;
/*  67:    */   @Column(name="mensajeria_instantanea1", nullable=true, length=50)
/*  68:    */   @Size(max=50)
/*  69:    */   private String mensajeriaInstantanea1;
/*  70:    */   @Column(name="mensajeria_instantanea2", nullable=true, length=50)
/*  71:    */   @Size(max=50)
/*  72:    */   private String mensajeriaInstantanea2;
/*  73:    */   @Column(name="descripcion", length=200, nullable=true)
/*  74:    */   @Size(max=200)
/*  75:    */   private String descripcion;
/*  76:    */   @Column(name="activo", nullable=false)
/*  77:    */   private boolean activo;
/*  78:    */   @Column(name="telefono_respuesta", length=13, nullable=true)
/*  79:    */   @Size(max=13)
/*  80:    */   private String telefonoRespuesta;
/*  81:    */   @Emails
/*  82:    */   @Column(name="email_respuesta", nullable=true, length=100)
/*  83:    */   @Size(max=100)
/*  84:    */   private String emailRespuesta;
/*  85:    */   
/*  86:    */   public int getId()
/*  87:    */   {
/*  88:126 */     return this.idContacto;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdContacto()
/*  92:    */   {
/*  93:130 */     return this.idContacto;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdContacto(int idContacto)
/*  97:    */   {
/*  98:134 */     this.idContacto = idContacto;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdOrganizacion()
/* 102:    */   {
/* 103:138 */     return this.idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdOrganizacion(int idOrganizacion)
/* 107:    */   {
/* 108:142 */     this.idOrganizacion = idOrganizacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Empresa getEmpresa()
/* 112:    */   {
/* 113:146 */     return this.empresa;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setEmpresa(Empresa empresa)
/* 117:    */   {
/* 118:150 */     this.empresa = empresa;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public TipoContacto getTipoContacto()
/* 122:    */   {
/* 123:154 */     return this.tipoContacto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setTipoContacto(TipoContacto tipoContacto)
/* 127:    */   {
/* 128:158 */     this.tipoContacto = tipoContacto;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getCargo()
/* 132:    */   {
/* 133:162 */     return this.cargo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setCargo(String cargo)
/* 137:    */   {
/* 138:166 */     this.cargo = cargo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getNombre()
/* 142:    */   {
/* 143:171 */     return this.nombre;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setNombre(String nombre)
/* 147:    */   {
/* 148:175 */     this.nombre = nombre;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getTelefono1()
/* 152:    */   {
/* 153:179 */     return this.telefono1;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setTelefono1(String telefono1)
/* 157:    */   {
/* 158:183 */     this.telefono1 = telefono1;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getExtension1()
/* 162:    */   {
/* 163:187 */     return this.extension1;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setExtension1(String extension1)
/* 167:    */   {
/* 168:191 */     this.extension1 = extension1;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getTelefono2()
/* 172:    */   {
/* 173:195 */     return this.telefono2;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setTelefono2(String telefono2)
/* 177:    */   {
/* 178:199 */     this.telefono2 = telefono2;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getExtension2()
/* 182:    */   {
/* 183:203 */     return this.extension2;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setExtension2(String extension2)
/* 187:    */   {
/* 188:207 */     this.extension2 = extension2;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getEmail1()
/* 192:    */   {
/* 193:211 */     return this.email1;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setEmail1(String email1)
/* 197:    */   {
/* 198:215 */     this.email1 = email1;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getEmail2()
/* 202:    */   {
/* 203:219 */     return this.email2;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setEmail2(String email2)
/* 207:    */   {
/* 208:223 */     this.email2 = email2;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getMensajeriaInstantanea1()
/* 212:    */   {
/* 213:227 */     return this.mensajeriaInstantanea1;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setMensajeriaInstantanea1(String mensajeriaInstantanea1)
/* 217:    */   {
/* 218:231 */     this.mensajeriaInstantanea1 = mensajeriaInstantanea1;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getMensajeriaInstantanea2()
/* 222:    */   {
/* 223:235 */     return this.mensajeriaInstantanea2;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setMensajeriaInstantanea2(String mensajeriaInstantanea2)
/* 227:    */   {
/* 228:239 */     this.mensajeriaInstantanea2 = mensajeriaInstantanea2;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getDescripcion()
/* 232:    */   {
/* 233:243 */     return this.descripcion;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setDescripcion(String descripcion)
/* 237:    */   {
/* 238:247 */     this.descripcion = descripcion;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isActivo()
/* 242:    */   {
/* 243:251 */     return this.activo;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setActivo(boolean activo)
/* 247:    */   {
/* 248:255 */     this.activo = activo;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public Sucursal getSucursal()
/* 252:    */   {
/* 253:259 */     return this.sucursal;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setSucursal(Sucursal sucursal)
/* 257:    */   {
/* 258:263 */     this.sucursal = sucursal;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getTelefonoRespuesta()
/* 262:    */   {
/* 263:267 */     return this.telefonoRespuesta;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setTelefonoRespuesta(String telefonoRespuesta)
/* 267:    */   {
/* 268:271 */     this.telefonoRespuesta = telefonoRespuesta;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getEmailRespuesta()
/* 272:    */   {
/* 273:275 */     return this.emailRespuesta;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setEmailRespuesta(String emailRespuesta)
/* 277:    */   {
/* 278:279 */     this.emailRespuesta = emailRespuesta;
/* 279:    */   }
/* 280:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Contacto
 * JD-Core Version:    0.7.0.1
 */