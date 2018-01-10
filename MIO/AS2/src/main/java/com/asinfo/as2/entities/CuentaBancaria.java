/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.EnumType;
/*   7:    */ import javax.persistence.Enumerated;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="cuenta_bancaria")
/*  21:    */ public class CuentaBancaria
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="cuenta_bancaria", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_bancaria")
/*  28:    */   @Column(name="id_cuenta_bancaria", unique=true, nullable=false)
/*  29:    */   private int idCuentaBancaria;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  35:    */   @JoinColumn(name="id_pais", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private Pais pais;
/*  38:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  39:    */   @JoinColumn(name="id_banco", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Banco banco;
/*  42:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  43:    */   @JoinColumn(name="id_tipo_cuenta_bancaria", nullable=false)
/*  44:    */   private TipoCuentaBancaria tipoCuentaBancaria;
/*  45:    */   @Column(name="numero", nullable=false, length=20)
/*  46:    */   @NotNull
/*  47:    */   @Size(min=2, max=20)
/*  48:    */   private String numero;
/*  49:    */   @Column(name="contacto", nullable=false, length=50)
/*  50:    */   @NotNull
/*  51:    */   @Size(min=2, max=50)
/*  52:    */   private String contacto;
/*  53:    */   @Column(name="telefono_contacto", nullable=false, length=20)
/*  54:    */   @NotNull
/*  55:    */   @Size(min=6, max=20)
/*  56:    */   private String telefonoContacto;
/*  57:    */   @Column(name="descripcion", length=200)
/*  58:    */   @Size(max=200)
/*  59:    */   private String descripcion;
/*  60:    */   @Column(name="activo", nullable=false)
/*  61:    */   private boolean activo;
/*  62:    */   @Column(name="predeterminado", nullable=false)
/*  63:    */   private boolean predeterminado;
/*  64:    */   @Enumerated(EnumType.STRING)
/*  65:    */   @Column(name="tipo_servicio_cuenta_bancaria", length=50, nullable=true)
/*  66:    */   private TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria;
/*  67:    */   @Enumerated(EnumType.STRING)
/*  68:    */   @Column(name="tipo_servicio_cuenta_bancaria_proveedor", length=50, nullable=true)
/*  69:    */   private TipoServicioCuentaBancariaEnum tipoServicioCuentaBancariaProveedor;
/*  70:    */   @Column(name="motivo", length=20, nullable=true)
/*  71:    */   @Size(max=20)
/*  72:    */   private String motivo;
/*  73:    */   
/*  74:    */   public CuentaBancaria()
/*  75:    */   {
/*  76:109 */     this.contacto = "N/A";
/*  77:110 */     this.telefonoContacto = "9999999999";
/*  78:111 */     this.activo = true;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public CuentaBancaria(Pais pais, Banco banco, TipoCuentaBancaria tipoCuentaBancaria, String numero)
/*  82:    */   {
/*  83:128 */     this.pais = pais;
/*  84:129 */     this.banco = banco;
/*  85:130 */     this.tipoCuentaBancaria = tipoCuentaBancaria;
/*  86:131 */     this.numero = numero;
/*  87:132 */     this.contacto = "N/A";
/*  88:133 */     this.telefonoContacto = "9999999999";
/*  89:134 */     this.activo = true;
/*  90:135 */     this.predeterminado = true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getId()
/*  94:    */   {
/*  95:140 */     return this.idCuentaBancaria;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdCuentaBancaria()
/*  99:    */   {
/* 100:149 */     return this.idCuentaBancaria;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdCuentaBancaria(int idCuentaBancaria)
/* 104:    */   {
/* 105:159 */     this.idCuentaBancaria = idCuentaBancaria;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdOrganizacion()
/* 109:    */   {
/* 110:168 */     return this.idOrganizacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdOrganizacion(int idOrganizacion)
/* 114:    */   {
/* 115:178 */     this.idOrganizacion = idOrganizacion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getIdSucursal()
/* 119:    */   {
/* 120:187 */     return this.idSucursal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIdSucursal(int idSucursal)
/* 124:    */   {
/* 125:197 */     this.idSucursal = idSucursal;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Pais getPais()
/* 129:    */   {
/* 130:206 */     return this.pais;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPais(Pais pais)
/* 134:    */   {
/* 135:216 */     this.pais = pais;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Banco getBanco()
/* 139:    */   {
/* 140:225 */     return this.banco;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setBanco(Banco banco)
/* 144:    */   {
/* 145:235 */     this.banco = banco;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public TipoCuentaBancaria getTipoCuentaBancaria()
/* 149:    */   {
/* 150:244 */     return this.tipoCuentaBancaria;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria)
/* 154:    */   {
/* 155:254 */     this.tipoCuentaBancaria = tipoCuentaBancaria;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getNumero()
/* 159:    */   {
/* 160:263 */     return this.numero;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setNumero(String numero)
/* 164:    */   {
/* 165:273 */     this.numero = numero;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getContacto()
/* 169:    */   {
/* 170:282 */     return this.contacto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setContacto(String contacto)
/* 174:    */   {
/* 175:292 */     this.contacto = contacto;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getTelefonoContacto()
/* 179:    */   {
/* 180:301 */     return this.telefonoContacto;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setTelefonoContacto(String telefonoContacto)
/* 184:    */   {
/* 185:311 */     this.telefonoContacto = telefonoContacto;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getDescripcion()
/* 189:    */   {
/* 190:320 */     return this.descripcion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDescripcion(String descripcion)
/* 194:    */   {
/* 195:330 */     this.descripcion = descripcion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean isActivo()
/* 199:    */   {
/* 200:339 */     return this.activo;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setActivo(boolean activo)
/* 204:    */   {
/* 205:349 */     this.activo = activo;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isPredeterminado()
/* 209:    */   {
/* 210:358 */     return this.predeterminado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setPredeterminado(boolean predeterminado)
/* 214:    */   {
/* 215:368 */     this.predeterminado = predeterminado;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String toString()
/* 219:    */   {
/* 220:373 */     return this.numero;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public TipoServicioCuentaBancariaEnum getTipoServicioCuentaBancaria()
/* 224:    */   {
/* 225:380 */     return this.tipoServicioCuentaBancaria;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTipoServicioCuentaBancaria(TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria)
/* 229:    */   {
/* 230:388 */     this.tipoServicioCuentaBancaria = tipoServicioCuentaBancaria;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public TipoServicioCuentaBancariaEnum getTipoServicioCuentaBancariaProveedor()
/* 234:    */   {
/* 235:395 */     return this.tipoServicioCuentaBancariaProveedor;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setTipoServicioCuentaBancariaProveedor(TipoServicioCuentaBancariaEnum tipoServicioCuentaBancariaProveedor)
/* 239:    */   {
/* 240:402 */     this.tipoServicioCuentaBancariaProveedor = tipoServicioCuentaBancariaProveedor;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public String getMotivo()
/* 244:    */   {
/* 245:406 */     return this.motivo;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setMotivo(String motivo)
/* 249:    */   {
/* 250:410 */     this.motivo = motivo;
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaBancaria
 * JD-Core Version:    0.7.0.1
 */