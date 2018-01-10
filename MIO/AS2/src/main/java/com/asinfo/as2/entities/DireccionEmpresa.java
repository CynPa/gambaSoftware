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
/*  11:    */ import javax.persistence.OneToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="direccion_empresa", indexes={@javax.persistence.Index(columnList="id_empresa")})
/*  19:    */ public class DireccionEmpresa
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="direccion_empresa", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="direccion_empresa")
/*  26:    */   @Column(name="id_direccion_empresa", unique=true, nullable=false)
/*  27:    */   private int idDireccionEmpresa;
/*  28:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  29:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  30:    */   private Empresa empresa;
/*  31:    */   @OneToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_ubicacion", nullable=true)
/*  33:    */   private Ubicacion ubicacion;
/*  34:    */   @OneToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_ciudad", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private Ciudad ciudad;
/*  38:    */   @OneToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_parroquia", nullable=true)
/*  40:    */   private Parroquia parroquia;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   private int idSucursal;
/*  45:    */   @Column(name="telefono1", length=20, nullable=true)
/*  46:    */   @Size(max=20)
/*  47:    */   private String telefono1;
/*  48:    */   @Column(name="telefono2", length=20, nullable=true)
/*  49:    */   @Size(max=20)
/*  50:    */   private String telefono2;
/*  51:    */   @Column(name="indicador_direccion_envio", nullable=false)
/*  52:    */   private boolean indicadorDireccionEnvio;
/*  53:    */   @Column(name="indicador_direccion_factura", nullable=false)
/*  54:    */   private boolean indicadorDireccionFactura;
/*  55:    */   @Column(name="indicador_direccion_principal", nullable=false)
/*  56:    */   private boolean indicadorDireccionPrincipal;
/*  57:    */   @Column(name="indicador_paraiso_fiscal", nullable=true)
/*  58:    */   private boolean indicadorParaisoFiscal;
/*  59:    */   @Column(name="descripcion", length=200, nullable=true)
/*  60:    */   @Size(max=200)
/*  61:    */   private String descripcion;
/*  62:    */   @Column(name="activo", nullable=false)
/*  63:    */   private boolean activo;
/*  64:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  65:    */   private Integer idDispositivoSincronizacion;
/*  66:    */   
/*  67:    */   public String getDireccionCompleta()
/*  68:    */   {
/*  69: 97 */     return this.ubicacion == null ? "" : this.ubicacion.getDireccionCompleta();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74:102 */     return this.idDireccionEmpresa;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdDireccionEmpresa()
/*  78:    */   {
/*  79:111 */     return this.idDireccionEmpresa;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdDireccionEmpresa(int idDireccionEmpresa)
/*  83:    */   {
/*  84:121 */     this.idDireccionEmpresa = idDireccionEmpresa;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Empresa getEmpresa()
/*  88:    */   {
/*  89:130 */     return this.empresa;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setEmpresa(Empresa empresa)
/*  93:    */   {
/*  94:140 */     this.empresa = empresa;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Ubicacion getUbicacion()
/*  98:    */   {
/*  99:149 */     if (this.ubicacion == null) {
/* 100:150 */       this.ubicacion = new Ubicacion();
/* 101:    */     }
/* 102:152 */     return this.ubicacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setUbicacion(Ubicacion ubicacion)
/* 106:    */   {
/* 107:162 */     this.ubicacion = ubicacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdOrganizacion()
/* 111:    */   {
/* 112:171 */     return this.idOrganizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdOrganizacion(int idOrganizacion)
/* 116:    */   {
/* 117:181 */     this.idOrganizacion = idOrganizacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdSucursal()
/* 121:    */   {
/* 122:190 */     return this.idSucursal;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdSucursal(int idSucursal)
/* 126:    */   {
/* 127:200 */     this.idSucursal = idSucursal;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getTelefono1()
/* 131:    */   {
/* 132:209 */     return this.telefono1;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setTelefono1(String telefono1)
/* 136:    */   {
/* 137:219 */     this.telefono1 = (telefono1 == null ? "" : telefono1);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getTelefono2()
/* 141:    */   {
/* 142:228 */     return this.telefono2;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setTelefono2(String telefono2)
/* 146:    */   {
/* 147:238 */     this.telefono2 = (telefono2 == null ? "" : telefono2);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isIndicadorDireccionEnvio()
/* 151:    */   {
/* 152:247 */     return this.indicadorDireccionEnvio;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setIndicadorDireccionEnvio(boolean indicadorDireccionEnvio)
/* 156:    */   {
/* 157:257 */     this.indicadorDireccionEnvio = indicadorDireccionEnvio;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isIndicadorDireccionFactura()
/* 161:    */   {
/* 162:266 */     return this.indicadorDireccionFactura;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setIndicadorDireccionFactura(boolean indicadorDireccionFactura)
/* 166:    */   {
/* 167:276 */     this.indicadorDireccionFactura = indicadorDireccionFactura;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getDescripcion()
/* 171:    */   {
/* 172:285 */     return this.descripcion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setDescripcion(String descripcion)
/* 176:    */   {
/* 177:295 */     this.descripcion = descripcion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isActivo()
/* 181:    */   {
/* 182:304 */     return this.activo;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setActivo(boolean activo)
/* 186:    */   {
/* 187:314 */     this.activo = activo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isIndicadorDireccionPrincipal()
/* 191:    */   {
/* 192:329 */     return this.indicadorDireccionPrincipal;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setIndicadorDireccionPrincipal(boolean indicadorDireccionPrincipal)
/* 196:    */   {
/* 197:339 */     this.indicadorDireccionPrincipal = indicadorDireccionPrincipal;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Ciudad getCiudad()
/* 201:    */   {
/* 202:348 */     return this.ciudad;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setCiudad(Ciudad ciudad)
/* 206:    */   {
/* 207:358 */     this.ciudad = ciudad;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Parroquia getParroquia()
/* 211:    */   {
/* 212:362 */     return this.parroquia;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setParroquia(Parroquia parroquia)
/* 216:    */   {
/* 217:366 */     this.parroquia = parroquia;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isIndicadorParaisoFiscal()
/* 221:    */   {
/* 222:370 */     return this.indicadorParaisoFiscal;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setIndicadorParaisoFiscal(boolean indicadorParaisoFiscal)
/* 226:    */   {
/* 227:374 */     this.indicadorParaisoFiscal = indicadorParaisoFiscal;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Integer getIdDispositivoSincronizacion()
/* 231:    */   {
/* 232:378 */     return this.idDispositivoSincronizacion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 236:    */   {
/* 237:382 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DireccionEmpresa
 * JD-Core Version:    0.7.0.1
 */