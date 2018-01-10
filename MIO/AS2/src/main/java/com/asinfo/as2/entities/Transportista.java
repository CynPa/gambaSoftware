/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
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
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="transportista", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  20:    */ public class Transportista
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="transportista", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="transportista")
/*  27:    */   @Column(name="id_transportista")
/*  28:    */   private int idTransportista;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   @Column(name="codigo", nullable=false, length=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  40:    */   @JoinColumn(name="id_tipo_identificacion", nullable=false)
/*  41:    */   private TipoIdentificacion tipoIdentificacion;
/*  42:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  43:    */   @JoinColumn(name="id_usuario", nullable=true)
/*  44:    */   private EntidadUsuario usuario;
/*  45:    */   @Column(name="identificacion", nullable=false, length=20)
/*  46:    */   @NotNull
/*  47:    */   @Size(max=20)
/*  48:    */   private String identificacion;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  51:    */   private Empresa empresa;
/*  52:    */   @Column(name="email", nullable=true, length=500)
/*  53:    */   @Size(max=500)
/*  54:    */   private String email;
/*  55:    */   @Column(name="telefono", nullable=true, length=13)
/*  56:    */   @Size(max=13)
/*  57:    */   private String telefono;
/*  58:    */   @Column(name="direccion", nullable=false)
/*  59:    */   @Size(max=200)
/*  60:    */   private String direccion;
/*  61:    */   @Column(name="descripcion", nullable=false)
/*  62:    */   @Size(max=200)
/*  63:    */   private String descripcion;
/*  64:    */   @Column(name="activo", nullable=false)
/*  65:    */   private boolean activo;
/*  66:    */   @Column(name="indicador_paga_flete", nullable=false)
/*  67:    */   private boolean indicadorPagaFlete;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_cliente", nullable=true)
/*  70:    */   private Empresa cliente;
/*  71:    */   @Transient
/*  72:    */   private String traTipoTidentificacion;
/*  73:    */   @Transient
/*  74:    */   private boolean rendered;
/*  75:    */   
/*  76:    */   public int getId()
/*  77:    */   {
/*  78: 97 */     return this.idTransportista;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Transportista() {}
/*  82:    */   
/*  83:    */   public Transportista(int idTransportista, String codigo, String nombre)
/*  84:    */   {
/*  85:105 */     this.idTransportista = idTransportista;
/*  86:106 */     this.codigo = codigo;
/*  87:107 */     this.nombre = nombre;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdTransportista()
/*  91:    */   {
/*  92:111 */     return this.idTransportista;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdTransportista(int idTransportista)
/*  96:    */   {
/*  97:115 */     this.idTransportista = idTransportista;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdOrganizacion()
/* 101:    */   {
/* 102:119 */     return this.idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdOrganizacion(int idOrganizacion)
/* 106:    */   {
/* 107:123 */     this.idOrganizacion = idOrganizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdSucursal()
/* 111:    */   {
/* 112:127 */     return this.idSucursal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdSucursal(int idSucursal)
/* 116:    */   {
/* 117:131 */     this.idSucursal = idSucursal;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getCodigo()
/* 121:    */   {
/* 122:135 */     return this.codigo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setCodigo(String codigo)
/* 126:    */   {
/* 127:139 */     this.codigo = codigo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getNombre()
/* 131:    */   {
/* 132:143 */     return this.nombre;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setNombre(String nombre)
/* 136:    */   {
/* 137:147 */     this.nombre = nombre;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getIdentificacion()
/* 141:    */   {
/* 142:151 */     return this.identificacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setIdentificacion(String identificacion)
/* 146:    */   {
/* 147:155 */     this.identificacion = identificacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getEmail()
/* 151:    */   {
/* 152:159 */     return this.email;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setEmail(String email)
/* 156:    */   {
/* 157:163 */     this.email = email;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getTelefono()
/* 161:    */   {
/* 162:167 */     return this.telefono;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTelefono(String telefono)
/* 166:    */   {
/* 167:171 */     this.telefono = telefono;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getDireccion()
/* 171:    */   {
/* 172:175 */     return this.direccion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setDireccion(String direccion)
/* 176:    */   {
/* 177:179 */     this.direccion = direccion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getDescripcion()
/* 181:    */   {
/* 182:183 */     return this.descripcion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setDescripcion(String descripcion)
/* 186:    */   {
/* 187:187 */     this.descripcion = descripcion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isActivo()
/* 191:    */   {
/* 192:191 */     return this.activo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setActivo(boolean activo)
/* 196:    */   {
/* 197:195 */     this.activo = activo;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public TipoIdentificacion getTipoIdentificacion()
/* 201:    */   {
/* 202:199 */     return this.tipoIdentificacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/* 206:    */   {
/* 207:203 */     this.tipoIdentificacion = tipoIdentificacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getTraTipoTidentificacion()
/* 211:    */   {
/* 212:207 */     return this.traTipoTidentificacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setTraTipoTidentificacion(String traTipoTidentificacion)
/* 216:    */   {
/* 217:211 */     this.traTipoTidentificacion = traTipoTidentificacion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String toString()
/* 221:    */   {
/* 222:216 */     return this.nombre;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public EntidadUsuario getUsuario()
/* 226:    */   {
/* 227:220 */     return this.usuario;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setUsuario(EntidadUsuario usuario)
/* 231:    */   {
/* 232:224 */     this.usuario = usuario;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Empresa getEmpresa()
/* 236:    */   {
/* 237:228 */     return this.empresa;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setEmpresa(Empresa empresa)
/* 241:    */   {
/* 242:232 */     this.empresa = empresa;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isIndicadorPagaFlete()
/* 246:    */   {
/* 247:236 */     return this.indicadorPagaFlete;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setIndicadorPagaFlete(boolean indicadorPagaFlete)
/* 251:    */   {
/* 252:240 */     this.indicadorPagaFlete = indicadorPagaFlete;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public Empresa getCliente()
/* 256:    */   {
/* 257:244 */     return this.cliente;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setCliente(Empresa cliente)
/* 261:    */   {
/* 262:248 */     this.cliente = cliente;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public boolean isRendered()
/* 266:    */   {
/* 267:252 */     return this.rendered;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setRendered(boolean rendered)
/* 271:    */   {
/* 272:256 */     this.rendered = rendered;
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Transportista
 * JD-Core Version:    0.7.0.1
 */