/*   1:    */ package com.asinfo.as2.entities.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
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
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="configuracion_cass")
/*  20:    */ public class ConfiguracionCass
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="configuracion_cass", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="configuracion_cass")
/*  27:    */   @Column(name="id_configuracion_cass", unique=true, nullable=false)
/*  28:    */   private int idConfiguracionCass;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", nullable=false, length=10)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", nullable=false, length=50)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=2, max=50)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", nullable=true, length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_ventas_cass", nullable=true)
/*  50:    */   private CuentaContable ventasCass;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_manejo_carga_tarifa_cero", nullable=true)
/*  53:    */   private CuentaContable manejoCargaTarifaCero;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_cuenta_por_cobrar_cass", nullable=true)
/*  56:    */   private CuentaContable cuentaPorCobrarCass;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_discount", nullable=true)
/*  59:    */   private CuentaContable discount;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_comm_agcy", nullable=true)
/*  62:    */   private CuentaContable commissionAgency;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_guia_collect_x", nullable=true)
/*  65:    */   private CuentaContable guiaCollectX;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_credito_tributario_commision", nullable=true)
/*  68:    */   private CuentaContable creditoTributarioCommision;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_iva_retenido", nullable=true)
/*  71:    */   private CuentaContable ivaRetenido;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_retencion_flete_agencia_viaje_carga", nullable=true)
/*  74:    */   private CuentaContable retencionFleteAgenciaViajeCarga;
/*  75:    */   
/*  76:    */   public int getId()
/*  77:    */   {
/*  78:102 */     return this.idConfiguracionCass;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdConfiguracionCass()
/*  82:    */   {
/*  83:107 */     return this.idConfiguracionCass;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdConfiguracionCass(int idConfiguracionCass)
/*  87:    */   {
/*  88:112 */     this.idConfiguracionCass = idConfiguracionCass;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdOrganizacion()
/*  92:    */   {
/*  93:117 */     return this.idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdOrganizacion(int idOrganizacion)
/*  97:    */   {
/*  98:122 */     this.idOrganizacion = idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdSucursal()
/* 102:    */   {
/* 103:127 */     return this.idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdSucursal(int idSucursal)
/* 107:    */   {
/* 108:132 */     this.idSucursal = idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getCodigo()
/* 112:    */   {
/* 113:137 */     return this.codigo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setCodigo(String codigo)
/* 117:    */   {
/* 118:142 */     this.codigo = codigo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getNombre()
/* 122:    */   {
/* 123:147 */     return this.nombre;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setNombre(String nombre)
/* 127:    */   {
/* 128:152 */     this.nombre = nombre;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getDescripcion()
/* 132:    */   {
/* 133:157 */     return this.descripcion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setDescripcion(String descripcion)
/* 137:    */   {
/* 138:162 */     this.descripcion = descripcion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isActivo()
/* 142:    */   {
/* 143:167 */     return this.activo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setActivo(boolean activo)
/* 147:    */   {
/* 148:172 */     this.activo = activo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isPredeterminado()
/* 152:    */   {
/* 153:177 */     return this.predeterminado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setPredeterminado(boolean predeterminado)
/* 157:    */   {
/* 158:182 */     this.predeterminado = predeterminado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public CuentaContable getVentasCass()
/* 162:    */   {
/* 163:187 */     return this.ventasCass;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setVentasCass(CuentaContable ventasCass)
/* 167:    */   {
/* 168:192 */     this.ventasCass = ventasCass;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public CuentaContable getManejoCargaTarifaCero()
/* 172:    */   {
/* 173:197 */     return this.manejoCargaTarifaCero;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setManejoCargaTarifaCero(CuentaContable manejoCargaTarifaCero)
/* 177:    */   {
/* 178:202 */     this.manejoCargaTarifaCero = manejoCargaTarifaCero;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public CuentaContable getCuentaPorCobrarCass()
/* 182:    */   {
/* 183:207 */     return this.cuentaPorCobrarCass;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setCuentaPorCobrarCass(CuentaContable cuentaPorCobrarCass)
/* 187:    */   {
/* 188:212 */     this.cuentaPorCobrarCass = cuentaPorCobrarCass;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public CuentaContable getDiscount()
/* 192:    */   {
/* 193:217 */     return this.discount;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setDiscount(CuentaContable discount)
/* 197:    */   {
/* 198:222 */     this.discount = discount;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public CuentaContable getCommissionAgency()
/* 202:    */   {
/* 203:227 */     return this.commissionAgency;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setCommissionAgency(CuentaContable commissionAgency)
/* 207:    */   {
/* 208:232 */     this.commissionAgency = commissionAgency;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public CuentaContable getGuiaCollectX()
/* 212:    */   {
/* 213:237 */     return this.guiaCollectX;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setGuiaCollectX(CuentaContable guiaCollectX)
/* 217:    */   {
/* 218:242 */     this.guiaCollectX = guiaCollectX;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public CuentaContable getCreditoTributarioCommision()
/* 222:    */   {
/* 223:247 */     return this.creditoTributarioCommision;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setCreditoTributarioCommision(CuentaContable creditoTributarioCommision)
/* 227:    */   {
/* 228:252 */     this.creditoTributarioCommision = creditoTributarioCommision;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public CuentaContable getIvaRetenido()
/* 232:    */   {
/* 233:257 */     return this.ivaRetenido;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIvaRetenido(CuentaContable ivaRetenido)
/* 237:    */   {
/* 238:262 */     this.ivaRetenido = ivaRetenido;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public CuentaContable getRetencionFleteAgenciaViajeCarga()
/* 242:    */   {
/* 243:267 */     return this.retencionFleteAgenciaViajeCarga;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setRetencionFleteAgenciaViajeCarga(CuentaContable retencionFleteAgenciaViajeCarga)
/* 247:    */   {
/* 248:272 */     this.retencionFleteAgenciaViajeCarga = retencionFleteAgenciaViajeCarga;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String toString()
/* 252:    */   {
/* 253:280 */     return this.nombre;
/* 254:    */   }
/* 255:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.aerolineas.ConfiguracionCass
 * JD-Core Version:    0.7.0.1
 */