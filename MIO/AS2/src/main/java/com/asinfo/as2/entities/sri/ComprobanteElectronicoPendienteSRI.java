/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="comprobante_electronico_pendiente_sri", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "clave_acceso"})}, indexes={@javax.persistence.Index(columnList="documento_base")})
/*  23:    */ public class ComprobanteElectronicoPendienteSRI
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="comprobante_electronico_pendiente_sri", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="comprobante_electronico_pendiente_sri")
/*  30:    */   @Column(name="id_comprobante_electronico_pendiente_sri")
/*  31:    */   private int idComprobanteElectronicoPendienteSRI;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="documento_base", nullable=false)
/*  37:    */   @Enumerated(EnumType.ORDINAL)
/*  38:    */   @NotNull
/*  39:    */   private DocumentoBase documentoBase;
/*  40:    */   @Column(name="tipo_documento", nullable=false)
/*  41:    */   @Enumerated(EnumType.ORDINAL)
/*  42:    */   @NotNull
/*  43:    */   private TipoDocumentoElectronicoEnum tipoDocumento;
/*  44:    */   @Column(name="clave_acceso", length=49, nullable=false)
/*  45:    */   @Size(max=49)
/*  46:    */   @NotNull
/*  47:    */   private String claveAcceso;
/*  48:    */   @Column(name="ambiente", nullable=false)
/*  49:    */   private int ambiente;
/*  50:    */   @Column(name="tipo_emision", nullable=false)
/*  51:    */   private int tipoEmision;
/*  52:    */   @Column(name="numero", length=20, nullable=false)
/*  53:    */   @Size(max=20)
/*  54:    */   @NotNull
/*  55:    */   private String numero;
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   @Column(name="fecha_emision", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private Date fechaEmision;
/*  60:    */   @Column(name="id_factura_cliente", nullable=true)
/*  61:    */   private Integer idFacturaCliente;
/*  62:    */   @Column(name="id_factura_proveedor_sri", nullable=true)
/*  63:    */   private Integer idFacturaProveedorSRI;
/*  64:    */   @Column(name="id_guia_remision", nullable=true)
/*  65:    */   private Integer idGuiaRemision;
/*  66:    */   @Column(name="emails", length=5000, nullable=true)
/*  67:    */   @Size(max=5000)
/*  68:    */   private String emails;
/*  69:    */   @Column(name="fecha_ultimo_intento", nullable=true)
/*  70:    */   private Date fechaUltimoIntento;
/*  71:    */   @Column(name="cantidad_intentos", nullable=false)
/*  72:106 */   private int cantidadIntentos = 0;
/*  73:    */   @Column(name="indicador_no_enviado", nullable=false)
/*  74:109 */   private boolean indicadorNoEnviado = true;
/*  75:    */   @Column(name="indicador_comprobar_autorizacion", nullable=false)
/*  76:112 */   private boolean indicadorComprobarAutorizacion = false;
/*  77:    */   @Column(name="indicador_rechazado", nullable=false)
/*  78:115 */   private boolean indicadorRechazado = false;
/*  79:    */   @Column(name="mensaje_sri", length=5000, nullable=true)
/*  80:    */   @Size(max=5000)
/*  81:    */   private String mensajeSRI;
/*  82:    */   
/*  83:    */   public int getId()
/*  84:    */   {
/*  85:127 */     return this.idComprobanteElectronicoPendienteSRI;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdComprobanteElectronicoPendienteSRI()
/*  89:    */   {
/*  90:131 */     return this.idComprobanteElectronicoPendienteSRI;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdComprobanteElectronicoPendienteSRI(int idComprobanteElectronicoPendienteSRI)
/*  94:    */   {
/*  95:135 */     this.idComprobanteElectronicoPendienteSRI = idComprobanteElectronicoPendienteSRI;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdOrganizacion()
/*  99:    */   {
/* 100:139 */     return this.idOrganizacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdOrganizacion(int idOrganizacion)
/* 104:    */   {
/* 105:143 */     this.idOrganizacion = idOrganizacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdSucursal()
/* 109:    */   {
/* 110:147 */     return this.idSucursal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdSucursal(int idSucursal)
/* 114:    */   {
/* 115:151 */     this.idSucursal = idSucursal;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public DocumentoBase getDocumentoBase()
/* 119:    */   {
/* 120:155 */     return this.documentoBase;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 124:    */   {
/* 125:159 */     this.documentoBase = documentoBase;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getClaveAcceso()
/* 129:    */   {
/* 130:163 */     return this.claveAcceso;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setClaveAcceso(String claveAcceso)
/* 134:    */   {
/* 135:167 */     this.claveAcceso = claveAcceso;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Date getFechaEmision()
/* 139:    */   {
/* 140:171 */     return this.fechaEmision;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setFechaEmision(Date fechaEmision)
/* 144:    */   {
/* 145:175 */     this.fechaEmision = fechaEmision;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Integer getIdFacturaCliente()
/* 149:    */   {
/* 150:179 */     return this.idFacturaCliente;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 154:    */   {
/* 155:183 */     this.idFacturaCliente = idFacturaCliente;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Integer getIdFacturaProveedorSRI()
/* 159:    */   {
/* 160:187 */     return this.idFacturaProveedorSRI;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdFacturaProveedorSRI(Integer idFacturaProveedorSRI)
/* 164:    */   {
/* 165:191 */     this.idFacturaProveedorSRI = idFacturaProveedorSRI;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Integer getIdGuiaRemision()
/* 169:    */   {
/* 170:195 */     return this.idGuiaRemision;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdGuiaRemision(Integer idGuiaRemision)
/* 174:    */   {
/* 175:199 */     this.idGuiaRemision = idGuiaRemision;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Date getFechaUltimoIntento()
/* 179:    */   {
/* 180:203 */     return this.fechaUltimoIntento;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setFechaUltimoIntento(Date fechaUltimoIntento)
/* 184:    */   {
/* 185:207 */     this.fechaUltimoIntento = fechaUltimoIntento;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getCantidadIntentos()
/* 189:    */   {
/* 190:211 */     return this.cantidadIntentos;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setCantidadIntentos(int cantidadIntentos)
/* 194:    */   {
/* 195:215 */     this.cantidadIntentos = cantidadIntentos;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean isIndicadorNoEnviado()
/* 199:    */   {
/* 200:219 */     return this.indicadorNoEnviado;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setIndicadorNoEnviado(boolean indicadorNoEnviado)
/* 204:    */   {
/* 205:223 */     this.indicadorNoEnviado = indicadorNoEnviado;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isIndicadorComprobarAutorizacion()
/* 209:    */   {
/* 210:227 */     return this.indicadorComprobarAutorizacion;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorComprobarAutorizacion(boolean indicadorComprobarAutorizacion)
/* 214:    */   {
/* 215:231 */     this.indicadorComprobarAutorizacion = indicadorComprobarAutorizacion;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isIndicadorRechazado()
/* 219:    */   {
/* 220:235 */     return this.indicadorRechazado;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIndicadorRechazado(boolean indicadorRechazado)
/* 224:    */   {
/* 225:239 */     this.indicadorRechazado = indicadorRechazado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getMensajeSRI()
/* 229:    */   {
/* 230:243 */     return this.mensajeSRI;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setMensajeSRI(String mensajeSRI)
/* 234:    */   {
/* 235:247 */     this.mensajeSRI = mensajeSRI;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getEmails()
/* 239:    */   {
/* 240:251 */     return this.emails;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setEmails(String emails)
/* 244:    */   {
/* 245:255 */     this.emails = emails;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getNumero()
/* 249:    */   {
/* 250:259 */     return this.numero;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setNumero(String numero)
/* 254:    */   {
/* 255:263 */     this.numero = numero;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public int getAmbiente()
/* 259:    */   {
/* 260:267 */     return this.ambiente;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setAmbiente(int ambiente)
/* 264:    */   {
/* 265:271 */     this.ambiente = ambiente;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public TipoDocumentoElectronicoEnum getTipoDocumento()
/* 269:    */   {
/* 270:275 */     return this.tipoDocumento;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setTipoDocumento(TipoDocumentoElectronicoEnum tipoDocumento)
/* 274:    */   {
/* 275:279 */     this.tipoDocumento = tipoDocumento;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public int getTipoEmision()
/* 279:    */   {
/* 280:283 */     return this.tipoEmision;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setTipoEmision(int tipoEmision)
/* 284:    */   {
/* 285:287 */     this.tipoEmision = tipoEmision;
/* 286:    */   }
/* 287:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI
 * JD-Core Version:    0.7.0.1
 */