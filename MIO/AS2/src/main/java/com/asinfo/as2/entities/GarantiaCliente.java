/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.Digits;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="garantia_cliente")
/*  30:    */ public class GarantiaCliente
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="garantia_cliente", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="garantia_cliente")
/*  37:    */   @Column(name="id_garantia_cliente")
/*  38:    */   private int idGarantiaCliente;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   private int idSucursal;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private Empresa empresa;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_banco", nullable=true)
/*  49:    */   private Banco banco;
/*  50:    */   @Column(name="numero", length=20)
/*  51:    */   @Size(min=1, max=20)
/*  52:    */   private String numero;
/*  53:    */   @Column(name="numero_cuenta", length=20)
/*  54:    */   @Size(max=20)
/*  55:    */   private String numeroCuenta;
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   @Column(name="fecha_ingreso", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private Date fechaIngreso;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha_cobro", nullable=true)
/*  62:    */   private Date fechaCobro;
/*  63:    */   @Column(name="girador", nullable=true)
/*  64:    */   @Size(max=50)
/*  65:    */   private String girador;
/*  66:    */   @Column(name="recibido_por", nullable=true)
/*  67:    */   @Size(min=2, max=50)
/*  68:    */   private String recibidoPor;
/*  69:    */   @Enumerated(EnumType.ORDINAL)
/*  70:    */   @Column(name="estado_garantia_cliente", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private EstadoGarantiaCliente estadoGarantiaCliente;
/*  73:    */   @Enumerated(EnumType.ORDINAL)
/*  74:    */   @Column(name="tipo_garantia_cliente", nullable=false)
/*  75:    */   @NotNull
/*  76:    */   private TipoGarantiaCliente tipoGarantiaCliente;
/*  77:    */   @Column(name="valor", precision=12, scale=2)
/*  78:    */   @Digits(integer=12, fraction=2)
/*  79:    */   @Min(0L)
/*  80:    */   private BigDecimal valor;
/*  81:    */   @Column(name="observacion", length=200, nullable=true)
/*  82:    */   @Size(max=200)
/*  83:    */   private String observacion;
/*  84:    */   @OneToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_detalle_forma_cobro", nullable=true)
/*  86:    */   private DetalleFormaCobro detalleFormaCobro;
/*  87:    */   @OneToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/*  89:    */   private FacturaCliente facturaCliente;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  92:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  93:    */   @Column(name="valor_protestado", precision=12, scale=2, nullable=true)
/*  94:    */   @Digits(integer=12, fraction=2)
/*  95:    */   @Min(0L)
/*  96:    */   private BigDecimal valorProtestado;
/*  97:    */   @Column(name="dias_credito_otorgado")
/*  98:    */   @Min(0L)
/*  99:    */   private int diasCreditoOtorgado;
/* 100:    */   @Transient
/* 101:    */   private int traIndicadorPersonaProtestada;
/* 102:    */   @Transient
/* 103:    */   private int traIdOpcionPersonaProtestada;
/* 104:    */   
/* 105:    */   public int getId()
/* 106:    */   {
/* 107:150 */     return getIdGarantiaCliente();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdGarantiaCliente()
/* 111:    */   {
/* 112:162 */     return this.idGarantiaCliente;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdGarantiaCliente(int idGarantiaCliente)
/* 116:    */   {
/* 117:172 */     this.idGarantiaCliente = idGarantiaCliente;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getNumero()
/* 121:    */   {
/* 122:181 */     return this.numero;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setNumero(String numero)
/* 126:    */   {
/* 127:191 */     this.numero = numero;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public EstadoGarantiaCliente getEstadoGarantiaCliente()
/* 131:    */   {
/* 132:200 */     return this.estadoGarantiaCliente;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setEstadoGarantiaCliente(EstadoGarantiaCliente estadoGarantiaCliente)
/* 136:    */   {
/* 137:210 */     this.estadoGarantiaCliente = estadoGarantiaCliente;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public TipoGarantiaCliente getTipoGarantiaCliente()
/* 141:    */   {
/* 142:219 */     return this.tipoGarantiaCliente;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setTipoGarantiaCliente(TipoGarantiaCliente tipoGarantiaCliente)
/* 146:    */   {
/* 147:229 */     this.tipoGarantiaCliente = tipoGarantiaCliente;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getIdOrganizacion()
/* 151:    */   {
/* 152:233 */     return this.idOrganizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setIdOrganizacion(int idOrganizacion)
/* 156:    */   {
/* 157:237 */     this.idOrganizacion = idOrganizacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getIdSucursal()
/* 161:    */   {
/* 162:241 */     return this.idSucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setIdSucursal(int idSucursal)
/* 166:    */   {
/* 167:245 */     this.idSucursal = idSucursal;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Empresa getEmpresa()
/* 171:    */   {
/* 172:249 */     return this.empresa;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setEmpresa(Empresa empresa)
/* 176:    */   {
/* 177:253 */     this.empresa = empresa;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Banco getBanco()
/* 181:    */   {
/* 182:257 */     return this.banco;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setBanco(Banco banco)
/* 186:    */   {
/* 187:261 */     this.banco = banco;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getNumeroCuenta()
/* 191:    */   {
/* 192:265 */     return this.numeroCuenta;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setNumeroCuenta(String numeroCuenta)
/* 196:    */   {
/* 197:269 */     this.numeroCuenta = numeroCuenta;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public BigDecimal getValor()
/* 201:    */   {
/* 202:273 */     return this.valor;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setValor(BigDecimal valor)
/* 206:    */   {
/* 207:277 */     this.valor = valor;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getObservacion()
/* 211:    */   {
/* 212:281 */     return this.observacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setObservacion(String observacion)
/* 216:    */   {
/* 217:285 */     this.observacion = observacion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Date getFechaIngreso()
/* 221:    */   {
/* 222:289 */     return this.fechaIngreso;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setFechaIngreso(Date fechaIngreso)
/* 226:    */   {
/* 227:293 */     this.fechaIngreso = fechaIngreso;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Date getFechaCobro()
/* 231:    */   {
/* 232:297 */     return this.fechaCobro;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setFechaCobro(Date fechaCobro)
/* 236:    */   {
/* 237:301 */     this.fechaCobro = fechaCobro;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getGirador()
/* 241:    */   {
/* 242:306 */     return this.girador;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setGirador(String girador)
/* 246:    */   {
/* 247:310 */     this.girador = girador;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getRecibidoPor()
/* 251:    */   {
/* 252:314 */     return this.recibidoPor;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setRecibidoPor(String recibidoPor)
/* 256:    */   {
/* 257:318 */     this.recibidoPor = recibidoPor;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public DetalleFormaCobro getDetalleFormaCobro()
/* 261:    */   {
/* 262:322 */     return this.detalleFormaCobro;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setDetalleFormaCobro(DetalleFormaCobro detalleFormaCobro)
/* 266:    */   {
/* 267:326 */     this.detalleFormaCobro = detalleFormaCobro;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public int getTraIndicadorPersonaProtestada()
/* 271:    */   {
/* 272:330 */     return this.traIndicadorPersonaProtestada;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setTraIndicadorPersonaProtestada(int traIndicadorPersonaProtestada)
/* 276:    */   {
/* 277:334 */     this.traIndicadorPersonaProtestada = traIndicadorPersonaProtestada;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public int getTraIdOpcionPersonaProtestada()
/* 281:    */   {
/* 282:338 */     return this.traIdOpcionPersonaProtestada;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setTraIdOpcionPersonaProtestada(int traIdOpcionPersonaProtestada)
/* 286:    */   {
/* 287:342 */     this.traIdOpcionPersonaProtestada = traIdOpcionPersonaProtestada;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 291:    */   {
/* 292:346 */     return this.cuentaBancariaOrganizacion;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 296:    */   {
/* 297:350 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public FacturaCliente getFacturaCliente()
/* 301:    */   {
/* 302:354 */     return this.facturaCliente;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 306:    */   {
/* 307:358 */     this.facturaCliente = facturaCliente;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public BigDecimal getValorProtestado()
/* 311:    */   {
/* 312:362 */     return this.valorProtestado;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setValorProtestado(BigDecimal valorProtestado)
/* 316:    */   {
/* 317:366 */     this.valorProtestado = valorProtestado;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public int getDiasCreditoOtorgado()
/* 321:    */   {
/* 322:370 */     return this.diasCreditoOtorgado;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setDiasCreditoOtorgado(int diasCreditoOtorgado)
/* 326:    */   {
/* 327:374 */     this.diasCreditoOtorgado = diasCreditoOtorgado;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String toString()
/* 331:    */   {
/* 332:379 */     return this.numero;
/* 333:    */   }
/* 334:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GarantiaCliente
 * JD-Core Version:    0.7.0.1
 */