/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="caja_chica")
/*  26:    */ public class CajaChica
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="caja_chica", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="caja_chica")
/*  33:    */   @Column(name="id_caja_chica")
/*  34:    */   private int idCajaChica;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_sucursal")
/*  39:    */   private Sucursal sucursal;
/*  40:    */   @Column(name="codigo", length=10, nullable=false)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=10)
/*  43:    */   private String codigo;
/*  44:    */   @Column(name="nombre", length=50, nullable=false)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=2, max=50)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="descripcion", length=200)
/*  49:    */   @Size(max=200)
/*  50:    */   private String descripcion;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  53:    */   @NotNull
/*  54:    */   private Date fechaDesde;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  57:    */   @NotNull
/*  58:    */   private Date fechaHasta;
/*  59:    */   @Column(name="estado", nullable=true)
/*  60:    */   private Estado estado;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  63:    */   private CuentaContable cuentaContable;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_cuenta_contable_liquidacion", nullable=true)
/*  66:    */   private CuentaContable cuentaContableLiquidacion;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_documento", nullable=false)
/*  69:    */   private Documento documento;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  72:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  73:    */   @OneToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  75:    */   private Asiento asiento;
/*  76:    */   @Temporal(TemporalType.DATE)
/*  77:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  78:    */   private Date fechaContabilizacion;
/*  79:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  80:    */   @Size(min=2, max=20)
/*  81:    */   private String documentoReferencia;
/*  82:    */   @Column(name="valor", precision=12, scale=2, nullable=true)
/*  83:    */   @Digits(integer=12, fraction=2)
/*  84:    */   @NotNull
/*  85:117 */   private BigDecimal valor = BigDecimal.ZERO;
/*  86:    */   @Column(name="saldo", precision=12, scale=2, nullable=false)
/*  87:    */   @Digits(integer=12, fraction=2)
/*  88:    */   @NotNull
/*  89:122 */   private BigDecimal saldo = BigDecimal.ZERO;
/*  90:    */   @Column(name="beneficiario", length=50, nullable=true)
/*  91:    */   @Size(min=2, max=50)
/*  92:    */   private String beneficiario;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_forma_pago", nullable=true)
/*  95:    */   private FormaPago formaPago;
/*  96:    */   @Transient
/*  97:    */   private Secuencia secuencia;
/*  98:    */   @Column(name="nota_contabilizacion", length=200, nullable=true)
/*  99:    */   @Size(max=200)
/* 100:    */   private String notaContabilizacion;
/* 101:    */   
/* 102:    */   public CajaChica() {}
/* 103:    */   
/* 104:    */   public CajaChica(int idCajaChica, String nombre)
/* 105:    */   {
/* 106:146 */     this.idCajaChica = idCajaChica;
/* 107:147 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdCajaChica()
/* 111:    */   {
/* 112:151 */     return this.idCajaChica;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdCajaChica(int idCajaChica)
/* 116:    */   {
/* 117:155 */     this.idCajaChica = idCajaChica;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Sucursal getSucursal()
/* 121:    */   {
/* 122:159 */     return this.sucursal;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setSucursal(Sucursal sucursal)
/* 126:    */   {
/* 127:163 */     this.sucursal = sucursal;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getCodigo()
/* 131:    */   {
/* 132:167 */     return this.codigo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setCodigo(String codigo)
/* 136:    */   {
/* 137:171 */     this.codigo = codigo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getNombre()
/* 141:    */   {
/* 142:175 */     return this.nombre;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setNombre(String nombre)
/* 146:    */   {
/* 147:179 */     this.nombre = nombre;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getDescripcion()
/* 151:    */   {
/* 152:183 */     return this.descripcion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDescripcion(String descripcion)
/* 156:    */   {
/* 157:187 */     this.descripcion = descripcion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaDesde()
/* 161:    */   {
/* 162:191 */     return this.fechaDesde;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaDesde(Date fechaDesde)
/* 166:    */   {
/* 167:195 */     this.fechaDesde = fechaDesde;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Date getFechaHasta()
/* 171:    */   {
/* 172:199 */     return this.fechaHasta;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFechaHasta(Date fechaHasta)
/* 176:    */   {
/* 177:203 */     this.fechaHasta = fechaHasta;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Estado getEstado()
/* 181:    */   {
/* 182:207 */     return this.estado;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setEstado(Estado estado)
/* 186:    */   {
/* 187:211 */     this.estado = estado;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public CuentaContable getCuentaContable()
/* 191:    */   {
/* 192:215 */     return this.cuentaContable;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 196:    */   {
/* 197:219 */     this.cuentaContable = cuentaContable;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Documento getDocumento()
/* 201:    */   {
/* 202:223 */     return this.documento;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setDocumento(Documento documento)
/* 206:    */   {
/* 207:227 */     this.documento = documento;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Asiento getAsiento()
/* 211:    */   {
/* 212:231 */     return this.asiento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setAsiento(Asiento asiento)
/* 216:    */   {
/* 217:235 */     this.asiento = asiento;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Date getFechaContabilizacion()
/* 221:    */   {
/* 222:239 */     return this.fechaContabilizacion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 226:    */   {
/* 227:243 */     this.fechaContabilizacion = fechaContabilizacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 231:    */   {
/* 232:247 */     return this.cuentaBancariaOrganizacion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 236:    */   {
/* 237:251 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getDocumentoReferencia()
/* 241:    */   {
/* 242:255 */     return this.documentoReferencia;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 246:    */   {
/* 247:259 */     this.documentoReferencia = documentoReferencia;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public BigDecimal getValor()
/* 251:    */   {
/* 252:263 */     return this.valor;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setValor(BigDecimal valor)
/* 256:    */   {
/* 257:267 */     this.valor = valor;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public BigDecimal getSaldo()
/* 261:    */   {
/* 262:271 */     return this.saldo;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setSaldo(BigDecimal saldo)
/* 266:    */   {
/* 267:275 */     this.saldo = saldo;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getBeneficiario()
/* 271:    */   {
/* 272:279 */     return this.beneficiario;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setBeneficiario(String beneficiario)
/* 276:    */   {
/* 277:283 */     this.beneficiario = beneficiario;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public int getId()
/* 281:    */   {
/* 282:288 */     return this.idCajaChica;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String toString()
/* 286:    */   {
/* 287:293 */     return this.nombre;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public FormaPago getFormaPago()
/* 291:    */   {
/* 292:302 */     return this.formaPago;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setFormaPago(FormaPago formaPago)
/* 296:    */   {
/* 297:312 */     this.formaPago = formaPago;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public int getIdOrganizacion()
/* 301:    */   {
/* 302:319 */     return this.idOrganizacion;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setIdOrganizacion(int idOrganizacion)
/* 306:    */   {
/* 307:327 */     this.idOrganizacion = idOrganizacion;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Secuencia getSecuencia()
/* 311:    */   {
/* 312:336 */     return this.secuencia;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setSecuencia(Secuencia secuencia)
/* 316:    */   {
/* 317:346 */     this.secuencia = secuencia;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getNotaContabilizacion()
/* 321:    */   {
/* 322:350 */     return this.notaContabilizacion;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setNotaContabilizacion(String notaContabilizacion)
/* 326:    */   {
/* 327:354 */     this.notaContabilizacion = notaContabilizacion;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public CuentaContable getCuentaContableLiquidacion()
/* 331:    */   {
/* 332:361 */     return this.cuentaContableLiquidacion;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setCuentaContableLiquidacion(CuentaContable cuentaContableLiquidacion)
/* 336:    */   {
/* 337:368 */     this.cuentaContableLiquidacion = cuentaContableLiquidacion;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CajaChica
 * JD-Core Version:    0.7.0.1
 */