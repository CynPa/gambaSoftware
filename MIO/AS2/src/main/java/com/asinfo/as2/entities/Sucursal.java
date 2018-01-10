/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ import org.hibernate.annotations.ColumnDefault;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="sucursal", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  27:    */ public class Sucursal
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="sucursal", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="sucursal")
/*  35:    */   @Column(name="id_sucursal", unique=true, nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="id_organizacion")
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="codigo", nullable=false, length=3)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=3, max=3)
/*  42:    */   private String codigo;
/*  43:    */   @Column(name="nombre", nullable=false, length=50)
/*  44:    */   @NotNull
/*  45:    */   @Size(max=50)
/*  46:    */   private String nombre;
/*  47:    */   @Column(name="email1", nullable=true, length=50)
/*  48:    */   @Size(max=50)
/*  49:    */   private String email1;
/*  50:    */   @Column(name="email2", nullable=true, length=50)
/*  51:    */   @Size(max=50)
/*  52:    */   private String email2;
/*  53:    */   @Column(name="telefono1", nullable=true, length=13)
/*  54:    */   @Size(max=13)
/*  55:    */   private String telefono1;
/*  56:    */   @Column(name="telefono2", nullable=true, length=13)
/*  57:    */   @Size(max=13)
/*  58:    */   private String telefono2;
/*  59:    */   @Column(name="indicador_matriz", nullable=false)
/*  60:    */   private boolean indicadorMatriz;
/*  61:    */   @Column(name="descripcion", length=200)
/*  62:    */   @Size(max=200)
/*  63:    */   private String descripcion;
/*  64:    */   @Column(name="activo", nullable=false)
/*  65:    */   private boolean activo;
/*  66:    */   @Column(name="predeterminado", nullable=false)
/*  67:    */   private boolean predeterminado;
/*  68:    */   @NotNull
/*  69:    */   @Column(name="compensacion_solidaria", nullable=false, precision=12, scale=2)
/*  70:    */   @Digits(integer=12, fraction=2)
/*  71:    */   @ColumnDefault("0")
/*  72: 86 */   private BigDecimal compensacionSolidaria = BigDecimal.ZERO;
/*  73:    */   @OneToOne(fetch=FetchType.EAGER)
/*  74:    */   @JoinColumn(name="id_ubicacion", nullable=false)
/*  75:    */   @NotNull
/*  76:    */   private Ubicacion ubicacion;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_ciudad", nullable=true)
/*  79:    */   private Ciudad ciudad;
/*  80:    */   @OneToMany(mappedBy="sucursal", fetch=FetchType.LAZY)
/*  81:101 */   private List<Contacto> listaContacto = new ArrayList();
/*  82:    */   @Transient
/*  83:    */   private String direccion;
/*  84:    */   
/*  85:    */   public Sucursal() {}
/*  86:    */   
/*  87:    */   public Sucursal(int idSucursal, String codigo, String nombre)
/*  88:    */   {
/*  89:118 */     this.idSucursal = idSucursal;
/*  90:119 */     this.codigo = codigo;
/*  91:120 */     this.nombre = nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getId()
/*  95:    */   {
/*  96:125 */     return this.idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdSucursal()
/* 100:    */   {
/* 101:134 */     return this.idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdSucursal(int idSucursal)
/* 105:    */   {
/* 106:144 */     this.idSucursal = idSucursal;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getCodigo()
/* 110:    */   {
/* 111:153 */     return this.codigo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setCodigo(String codigo)
/* 115:    */   {
/* 116:163 */     this.codigo = codigo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getNombre()
/* 120:    */   {
/* 121:172 */     return this.nombre;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setNombre(String nombre)
/* 125:    */   {
/* 126:182 */     this.nombre = nombre;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getEmail1()
/* 130:    */   {
/* 131:191 */     return this.email1;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setEmail1(String email1)
/* 135:    */   {
/* 136:201 */     this.email1 = email1;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getEmail2()
/* 140:    */   {
/* 141:210 */     return this.email2;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setEmail2(String email2)
/* 145:    */   {
/* 146:220 */     this.email2 = email2;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getTelefono1()
/* 150:    */   {
/* 151:229 */     return this.telefono1;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setTelefono1(String telefono1)
/* 155:    */   {
/* 156:239 */     this.telefono1 = telefono1;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getTelefono2()
/* 160:    */   {
/* 161:248 */     return this.telefono2;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setTelefono2(String telefono2)
/* 165:    */   {
/* 166:258 */     this.telefono2 = telefono2;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isIndicadorMatriz()
/* 170:    */   {
/* 171:267 */     return this.indicadorMatriz;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIndicadorMatriz(boolean indicadorMatriz)
/* 175:    */   {
/* 176:277 */     this.indicadorMatriz = indicadorMatriz;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean isActivo()
/* 180:    */   {
/* 181:286 */     return this.activo;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setActivo(boolean activo)
/* 185:    */   {
/* 186:296 */     this.activo = activo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isPredeterminado()
/* 190:    */   {
/* 191:305 */     return this.predeterminado;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setPredeterminado(boolean predeterminado)
/* 195:    */   {
/* 196:315 */     this.predeterminado = predeterminado;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String toString()
/* 200:    */   {
/* 201:325 */     return this.nombre;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int getIdOrganizacion()
/* 205:    */   {
/* 206:334 */     return this.idOrganizacion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIdOrganizacion(int idOrganizacion)
/* 210:    */   {
/* 211:344 */     this.idOrganizacion = idOrganizacion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Ubicacion getUbicacion()
/* 215:    */   {
/* 216:353 */     return this.ubicacion;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setUbicacion(Ubicacion ubicacion)
/* 220:    */   {
/* 221:363 */     this.ubicacion = ubicacion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getDireccion()
/* 225:    */   {
/* 226:373 */     getUbicacion().getDireccion1().concat("-").concat(getUbicacion().getDireccion2().concat("-")).concat(getUbicacion().getDireccion3().concat("-")).concat(getUbicacion().getDireccion4());
/* 227:374 */     return this.direccion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setDireccion(String direccion)
/* 231:    */   {
/* 232:384 */     this.direccion = direccion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Ciudad getCiudad()
/* 236:    */   {
/* 237:393 */     return this.ciudad;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setCiudad(Ciudad ciudad)
/* 241:    */   {
/* 242:403 */     this.ciudad = ciudad;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public String getDescripcion()
/* 246:    */   {
/* 247:407 */     return this.descripcion;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setDescripcion(String descripcion)
/* 251:    */   {
/* 252:411 */     this.descripcion = descripcion;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public BigDecimal getCompensacionSolidaria()
/* 256:    */   {
/* 257:415 */     return this.compensacionSolidaria;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setCompensacionSolidaria(BigDecimal compensacionSolidaria)
/* 261:    */   {
/* 262:419 */     this.compensacionSolidaria = compensacionSolidaria;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<Contacto> getListaContacto()
/* 266:    */   {
/* 267:423 */     return this.listaContacto;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setListaContacto(List<Contacto> listaContacto)
/* 271:    */   {
/* 272:427 */     this.listaContacto = listaContacto;
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Sucursal
 * JD-Core Version:    0.7.0.1
 */