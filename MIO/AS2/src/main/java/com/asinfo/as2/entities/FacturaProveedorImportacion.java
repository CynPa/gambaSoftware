/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.MedioTransporteEnum;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.Digits;
/*  26:    */ import javax.validation.constraints.Min;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="factura_proveedor_importacion")
/*  32:    */ public class FacturaProveedorImportacion
/*  33:    */   extends EntidadBase
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="factura_proveedor_importacion", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="factura_proveedor_importacion")
/*  39:    */   @Column(name="id_factura_proveedor_importacion")
/*  40:    */   private int idFacturaProveedorImportacion;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  43:    */   private Sucursal sucursal;
/*  44:    */   @Column(name="id_organizacion", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private int idOrganizacion;
/*  47:    */   @Column(name="puerto_embarque", nullable=false)
/*  48:    */   @Size(max=50)
/*  49: 70 */   private String puertoEmbarque = "";
/*  50:    */   @Column(name="puerto_llegada", nullable=false)
/*  51:    */   @Size(max=50)
/*  52: 74 */   private String puertoLlegada = "";
/*  53:    */   @Column(name="fecha_embarque", nullable=true)
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   private Date fechaEmbarque;
/*  56:    */   @Column(name="fecha_llegada", nullable=true)
/*  57:    */   @Temporal(TemporalType.DATE)
/*  58:    */   private Date fechaLlegada;
/*  59:    */   @Column(name="medio_transporte", nullable=false)
/*  60:    */   @Enumerated(EnumType.ORDINAL)
/*  61:    */   @NotNull
/*  62:    */   private MedioTransporteEnum medioTransporteEnum;
/*  63:    */   @Column(name="informacion_transporte", nullable=false, length=50)
/*  64:    */   @Size(max=50)
/*  65: 91 */   private String informacionTransporte = "";
/*  66:    */   @Column(name="numero_DUI", nullable=false, length=20)
/*  67:    */   @Size(max=20)
/*  68:    */   @NotNull
/*  69:    */   private String numeroDUI;
/*  70:    */   @Column(name="fecha_pago_DUI", nullable=true)
/*  71:    */   @Temporal(TemporalType.DATE)
/*  72:100 */   private Date fechaPagoDUI = null;
/*  73:    */   @Column(name="aseguradora", nullable=true, length=20)
/*  74:    */   @Size(max=20)
/*  75:    */   private String aseguradora;
/*  76:    */   @Column(name="fecha_prorrateo", nullable=true)
/*  77:    */   @Temporal(TemporalType.DATE)
/*  78:    */   private Date fechaProrrateo;
/*  79:    */   @Column(name="fecha_cierre", nullable=true)
/*  80:    */   @Temporal(TemporalType.DATE)
/*  81:    */   private Date fechaCierre;
/*  82:    */   @Column(name="base_imponible_diferente_cero", precision=12, scale=2)
/*  83:    */   @Digits(integer=12, fraction=2)
/*  84:    */   @Min(0L)
/*  85:116 */   private BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  86:    */   @Column(name="impuesto", nullable=true, precision=12, scale=2)
/*  87:    */   @Digits(integer=12, fraction=2)
/*  88:    */   @Min(0L)
/*  89:121 */   private BigDecimal impuesto = BigDecimal.ZERO;
/*  90:    */   @Column(name="estado_proceso_importacion", nullable=true, length=50)
/*  91:    */   @Size(max=50)
/*  92:    */   private String estadoProcesoImportacion;
/*  93:    */   @Column(name="total_valor_gasto_real", nullable=true, precision=12, scale=2)
/*  94:    */   @Digits(integer=12, fraction=2)
/*  95:130 */   private BigDecimal totalValorGastoReal = BigDecimal.ZERO;
/*  96:    */   @OneToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*  98:    */   private FacturaProveedor facturaProveedor;
/*  99:    */   @OneToMany(mappedBy="facturaProveedorImportacion", fetch=FetchType.LAZY)
/* 100:143 */   private List<DetalleFacturaProveedorImportacionGasto> listaDetalleFacturaProveedorImportacionGasto = new ArrayList();
/* 101:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 102:    */   @JoinColumn(name="id_pais", nullable=false)
/* 103:    */   @NotNull
/* 104:    */   private Pais pais;
/* 105:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 106:    */   @JoinColumn(name="id_moneda", nullable=true)
/* 107:    */   private Moneda moneda;
/* 108:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 109:    */   @JoinColumn(name="id_cuenta_contable_importacion", nullable=false)
/* 110:    */   @NotNull
/* 111:    */   private CuentaContable cuentaContableImportacion;
/* 112:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 113:    */   @JoinColumn(name="id_cuenta_contable_importacion_diferencia_en_mas_o_en_menos", nullable=true)
/* 114:    */   private CuentaContable cuentaContableImportacionDiferenciaEnMasOEnMenos;
/* 115:    */   @OneToOne(fetch=FetchType.LAZY)
/* 116:    */   @JoinColumn(name="id_asiento", nullable=true)
/* 117:    */   private Asiento asiento;
/* 118:    */   @OneToMany(mappedBy="facturaProveedorImportacion", fetch=FetchType.LAZY)
/* 119:168 */   private List<DetalleProcesoImportacion> listaDetalleProcesoImportacion = new ArrayList();
/* 120:    */   @Transient
/* 121:175 */   private BigDecimal totalValorGastoPresupuestado = BigDecimal.ZERO;
/* 122:    */   
/* 123:    */   public int getIdFacturaProveedorImportacion()
/* 124:    */   {
/* 125:192 */     return this.idFacturaProveedorImportacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdFacturaProveedorImportacion(int idFacturaProveedorImportacion)
/* 129:    */   {
/* 130:202 */     this.idFacturaProveedorImportacion = idFacturaProveedorImportacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Sucursal getSucursal()
/* 134:    */   {
/* 135:211 */     return this.sucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setSucursal(Sucursal sucursal)
/* 139:    */   {
/* 140:221 */     this.sucursal = sucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getIdOrganizacion()
/* 144:    */   {
/* 145:230 */     return this.idOrganizacion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setIdOrganizacion(int idOrganizacion)
/* 149:    */   {
/* 150:240 */     this.idOrganizacion = idOrganizacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getPuertoEmbarque()
/* 154:    */   {
/* 155:249 */     return this.puertoEmbarque;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setPuertoEmbarque(String puertoEmbarque)
/* 159:    */   {
/* 160:259 */     this.puertoEmbarque = puertoEmbarque;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getPuertoLlegada()
/* 164:    */   {
/* 165:268 */     return this.puertoLlegada;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setPuertoLlegada(String puertoLlegada)
/* 169:    */   {
/* 170:278 */     this.puertoLlegada = puertoLlegada;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Date getFechaEmbarque()
/* 174:    */   {
/* 175:287 */     return this.fechaEmbarque;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setFechaEmbarque(Date fechaEmbarque)
/* 179:    */   {
/* 180:297 */     this.fechaEmbarque = fechaEmbarque;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Date getFechaLlegada()
/* 184:    */   {
/* 185:306 */     return this.fechaLlegada;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setFechaLlegada(Date fechaLlegada)
/* 189:    */   {
/* 190:316 */     this.fechaLlegada = fechaLlegada;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public MedioTransporteEnum getMedioTransporteEnum()
/* 194:    */   {
/* 195:325 */     return this.medioTransporteEnum;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setMedioTransporteEnum(MedioTransporteEnum medioTransporteEnum)
/* 199:    */   {
/* 200:335 */     this.medioTransporteEnum = medioTransporteEnum;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getNumeroDUI()
/* 204:    */   {
/* 205:344 */     return this.numeroDUI;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setNumeroDUI(String numeroDUI)
/* 209:    */   {
/* 210:354 */     this.numeroDUI = numeroDUI;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Date getFechaPagoDUI()
/* 214:    */   {
/* 215:363 */     return this.fechaPagoDUI;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setFechaPagoDUI(Date fechaPagoDUI)
/* 219:    */   {
/* 220:373 */     this.fechaPagoDUI = fechaPagoDUI;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getAseguradora()
/* 224:    */   {
/* 225:382 */     return this.aseguradora;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setAseguradora(String aseguradora)
/* 229:    */   {
/* 230:392 */     this.aseguradora = aseguradora;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Date getFechaProrrateo()
/* 234:    */   {
/* 235:401 */     return this.fechaProrrateo;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setFechaProrrateo(Date fechaProrrateo)
/* 239:    */   {
/* 240:411 */     this.fechaProrrateo = fechaProrrateo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Date getFechaCierre()
/* 244:    */   {
/* 245:420 */     return this.fechaCierre;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setFechaCierre(Date fechaCierre)
/* 249:    */   {
/* 250:430 */     this.fechaCierre = fechaCierre;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public FacturaProveedor getFacturaProveedor()
/* 254:    */   {
/* 255:439 */     return this.facturaProveedor;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 259:    */   {
/* 260:449 */     this.facturaProveedor = facturaProveedor;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Pais getPais()
/* 264:    */   {
/* 265:458 */     return this.pais;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setPais(Pais pais)
/* 269:    */   {
/* 270:468 */     this.pais = pais;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public Moneda getMoneda()
/* 274:    */   {
/* 275:477 */     return this.moneda;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setMoneda(Moneda moneda)
/* 279:    */   {
/* 280:487 */     this.moneda = moneda;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public CuentaContable getCuentaContableImportacion()
/* 284:    */   {
/* 285:496 */     return this.cuentaContableImportacion;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setCuentaContableImportacion(CuentaContable cuentaContableImportacion)
/* 289:    */   {
/* 290:506 */     this.cuentaContableImportacion = cuentaContableImportacion;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public List<DetalleFacturaProveedorImportacionGasto> getListaDetalleFacturaProveedorImportacionGasto()
/* 294:    */   {
/* 295:515 */     return this.listaDetalleFacturaProveedorImportacionGasto;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setListaDetalleFacturaProveedorImportacionGasto(List<DetalleFacturaProveedorImportacionGasto> listaDetalleFacturaProveedorImportacionGasto)
/* 299:    */   {
/* 300:526 */     this.listaDetalleFacturaProveedorImportacionGasto = listaDetalleFacturaProveedorImportacionGasto;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public BigDecimal getTotalValorGastoPresupuestado()
/* 304:    */   {
/* 305:536 */     this.totalValorGastoPresupuestado = BigDecimal.ZERO;
/* 306:537 */     for (DetalleFacturaProveedorImportacionGasto dfpgi : this.listaDetalleFacturaProveedorImportacionGasto) {
/* 307:538 */       this.totalValorGastoPresupuestado = this.totalValorGastoPresupuestado.add(dfpgi.getValorPresupuesto());
/* 308:    */     }
/* 309:541 */     return this.totalValorGastoPresupuestado;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setTotalValorGastoPresupuestado(BigDecimal totalValorGastoPresupuestado)
/* 313:    */   {
/* 314:551 */     this.totalValorGastoPresupuestado = totalValorGastoPresupuestado;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public BigDecimal getTotalValorGastoReal()
/* 318:    */   {
/* 319:560 */     return this.totalValorGastoReal;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setTotalValorGastoReal(BigDecimal totalValorGastoReal)
/* 323:    */   {
/* 324:570 */     this.totalValorGastoReal = totalValorGastoReal;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public int getId()
/* 328:    */   {
/* 329:580 */     return this.idFacturaProveedorImportacion;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public BigDecimal getBaseImponibleDiferenteCero()
/* 333:    */   {
/* 334:589 */     return this.baseImponibleDiferenteCero;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setBaseImponibleDiferenteCero(BigDecimal baseImponibleDiferenteCero)
/* 338:    */   {
/* 339:599 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public BigDecimal getImpuesto()
/* 343:    */   {
/* 344:608 */     return this.impuesto;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setImpuesto(BigDecimal impuesto)
/* 348:    */   {
/* 349:618 */     this.impuesto = impuesto;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public List<DetalleProcesoImportacion> getListaDetalleProcesoImportacion()
/* 353:    */   {
/* 354:627 */     return this.listaDetalleProcesoImportacion;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setListaDetalleProcesoImportacion(List<DetalleProcesoImportacion> listaDetalleProcesoImportacion)
/* 358:    */   {
/* 359:637 */     this.listaDetalleProcesoImportacion = listaDetalleProcesoImportacion;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public String getEstadoProcesoImportacion()
/* 363:    */   {
/* 364:646 */     return this.estadoProcesoImportacion;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setEstadoProcesoImportacion(String estadoProcesoImportacion)
/* 368:    */   {
/* 369:656 */     this.estadoProcesoImportacion = estadoProcesoImportacion;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String getInformacionTransporte()
/* 373:    */   {
/* 374:665 */     return this.informacionTransporte;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setInformacionTransporte(String informacionTransporte)
/* 378:    */   {
/* 379:675 */     this.informacionTransporte = informacionTransporte;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public CuentaContable getCuentaContableImportacionDiferenciaEnMasOEnMenos()
/* 383:    */   {
/* 384:679 */     return this.cuentaContableImportacionDiferenciaEnMasOEnMenos;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setCuentaContableImportacionDiferenciaEnMasOEnMenos(CuentaContable cuentaContableImportacionDiferenciaEnMasOEnMenos)
/* 388:    */   {
/* 389:683 */     this.cuentaContableImportacionDiferenciaEnMasOEnMenos = cuentaContableImportacionDiferenciaEnMasOEnMenos;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public Asiento getAsiento()
/* 393:    */   {
/* 394:687 */     return this.asiento;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setAsiento(Asiento asiento)
/* 398:    */   {
/* 399:691 */     this.asiento = asiento;
/* 400:    */   }
/* 401:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FacturaProveedorImportacion
 * JD-Core Version:    0.7.0.1
 */