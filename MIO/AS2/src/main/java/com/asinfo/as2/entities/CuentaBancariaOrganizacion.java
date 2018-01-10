/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
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
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="cuenta_bancaria_organizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_cuenta_contable_banco"})})
/*  29:    */ public class CuentaBancariaOrganizacion
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="cuenta_bancaria_organizacion", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_bancaria_organizacion")
/*  36:    */   @Column(name="id_cuenta_bancaria_organizacion", unique=true, nullable=false)
/*  37:    */   private int idCuentaBancariaOrganizacion;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @OneToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_cuenta_bancaria", nullable=true)
/*  44:    */   private CuentaBancaria cuentaBancaria;
/*  45:    */   @Enumerated(EnumType.ORDINAL)
/*  46:    */   @Column(name="tipo_cuenta", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private TipoCuentaBancariaOrganizacion tipoCuentaBancariaOrganizacion;
/*  49:    */   @OneToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_cuenta_contable_banco_pago_cash", nullable=true)
/*  51:    */   private CuentaContable cuentaContableBancoPagoCash;
/*  52:    */   @OneToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_cuenta_contable_banco", nullable=false)
/*  54:    */   private CuentaContable cuentaContableBanco;
/*  55:    */   @OneToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_cuenta_contable_gastos_bancarios", nullable=false)
/*  57:    */   private CuentaContable cuentaContableGastosBancarios;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_cuenta_contable_gastos_protesto", nullable=true)
/*  60:    */   private CuentaContable cuentaContableGastosProtesto;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_cuenta_contable_diferencias", nullable=true)
/*  63:    */   private CuentaContable cuentaContableDiferencias;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_cuenta_contable_credito_transitorio", nullable=true)
/*  66:    */   private CuentaContable cuentaContableTransitoria;
/*  67:    */   @Column(name="reporte", length=50, nullable=false)
/*  68:    */   @Size(min=2, max=50)
/*  69:    */   private String reporte;
/*  70:    */   @Column(name="descripcion", length=200)
/*  71:    */   @Size(max=200)
/*  72:    */   private String descripcion;
/*  73:    */   @Column(name="activo", nullable=false)
/*  74:    */   private boolean activo;
/*  75:    */   @Column(name="predeterminado", nullable=false)
/*  76:    */   private boolean predeterminado;
/*  77:    */   @Column(name="valor_protesto", nullable=false, precision=12, scale=2)
/*  78:    */   @Digits(integer=12, fraction=2)
/*  79:    */   @Min(0L)
/*  80:111 */   private BigDecimal valorProtesto = BigDecimal.ZERO
/*  81:    */   
/*  82:    */ 
/*  83:114 */     .setScale(2);
/*  84:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaBancariaOrganizacion")
/*  85:116 */   private List<MovimientoBancario> listaMovimientoBancario = new ArrayList();
/*  86:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaBancariaOrganizacion")
/*  87:119 */   private List<ConciliacionBancaria> listaConciliacionBancaria = new ArrayList();
/*  88:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaBancariaOrganizacion")
/*  89:122 */   private List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago = new ArrayList();
/*  90:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaBancariaOrganizacion")
/*  91:125 */   private List<CuentaContableCruceCuentaBancariaOrganizacion> listaCuentaContableCruceCuentaBancariaOrganizacion = new ArrayList();
/*  92:    */   @Column(name="indicador_cruce", nullable=false)
/*  93:    */   private boolean indicadorCruce;
/*  94:    */   @Column(name="indicador_interbancaria_pago_rol", nullable=false)
/*  95:134 */   private boolean indicadorTransferenciaInterbancariaPagoRol = true;
/*  96:    */   @Column(name="indicador_interbancaria_pago_proveedor", nullable=false)
/*  97:140 */   private boolean indicadorTransferenciaInterbancariaPagoProveedor = true;
/*  98:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaBancariaOrganizacion")
/*  99:144 */   private List<BancoAcreedor> listaBancoAcreedor = new ArrayList();
/* 100:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 101:    */   @JoinColumn(name="id_empresa", nullable=true)
/* 102:    */   private Empresa empresa;
/* 103:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 104:    */   @JoinColumn(name="id_producto", nullable=true)
/* 105:    */   private Producto producto;
/* 106:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 107:    */   @JoinColumn(name="id_cuenta_contable_retencion", nullable=true)
/* 108:    */   private CuentaContable cuentaContableRetencion;
/* 109:    */   @Column(name="localidad", length=50, nullable=true)
/* 110:    */   @Size(min=1, max=50)
/* 111:    */   private String localidad;
/* 112:    */   @Transient
/* 113:    */   private ConfiguracionConciliacionBancaria configuracionConciliacionBancaria;
/* 114:    */   
/* 115:    */   public CuentaBancariaOrganizacion() {}
/* 116:    */   
/* 117:    */   public CuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/* 118:    */   {
/* 119:179 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getId()
/* 123:    */   {
/* 124:184 */     return this.idCuentaBancariaOrganizacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<String> getCamposAuditables()
/* 128:    */   {
/* 129:188 */     ArrayList<String> lista = new ArrayList();
/* 130:189 */     lista.add("tipoCuentaBancariaOrganizacion");
/* 131:190 */     lista.add("cuentaContableBanco");
/* 132:191 */     lista.add("cuentaContableGastosBancarios");
/* 133:192 */     lista.add("secuencia");
/* 134:193 */     lista.add("activo");
/* 135:    */     
/* 136:195 */     return lista;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getNombreCompleto()
/* 140:    */   {
/* 141:205 */     if (getCuentaBancaria() != null) {
/* 142:206 */       return getCuentaBancaria().getBanco().getNombre() + " #" + getCuentaBancaria().getNumero();
/* 143:    */     }
/* 144:208 */     return "";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getIdSucursal()
/* 148:    */   {
/* 149:217 */     return this.idSucursal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIdSucursal(int idSucursal)
/* 153:    */   {
/* 154:227 */     this.idSucursal = idSucursal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public CuentaBancaria getCuentaBancaria()
/* 158:    */   {
/* 159:236 */     return this.cuentaBancaria;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setCuentaBancaria(CuentaBancaria cuentaBancaria)
/* 163:    */   {
/* 164:246 */     this.cuentaBancaria = cuentaBancaria;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getDescripcion()
/* 168:    */   {
/* 169:255 */     return this.descripcion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDescripcion(String descripcion)
/* 173:    */   {
/* 174:265 */     this.descripcion = descripcion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean isActivo()
/* 178:    */   {
/* 179:274 */     return this.activo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setActivo(boolean activo)
/* 183:    */   {
/* 184:284 */     this.activo = activo;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isPredeterminado()
/* 188:    */   {
/* 189:293 */     return this.predeterminado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setPredeterminado(boolean predeterminado)
/* 193:    */   {
/* 194:303 */     this.predeterminado = predeterminado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public TipoCuentaBancariaOrganizacion getTipoCuentaBancariaOrganizacion()
/* 198:    */   {
/* 199:312 */     return this.tipoCuentaBancariaOrganizacion;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTipoCuentaBancariaOrganizacion(TipoCuentaBancariaOrganizacion tipoCuentaBancariaOrganizacion)
/* 203:    */   {
/* 204:322 */     this.tipoCuentaBancariaOrganizacion = tipoCuentaBancariaOrganizacion;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getIdCuentaBancariaOrganizacion()
/* 208:    */   {
/* 209:331 */     return this.idCuentaBancariaOrganizacion;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIdCuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/* 213:    */   {
/* 214:341 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String toString()
/* 218:    */   {
/* 219:346 */     if ((this.cuentaBancaria != null) && (this.cuentaBancaria.getNumero() != null)) {
/* 220:347 */       return this.cuentaBancaria.getBanco().getNombre() + " " + this.cuentaBancaria.getNumero();
/* 221:    */     }
/* 222:349 */     return getNombreCompleto();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public CuentaContable getCuentaContableGastosBancarios()
/* 226:    */   {
/* 227:359 */     return this.cuentaContableGastosBancarios;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCuentaContableGastosBancarios(CuentaContable cuentaContableGastosBancarios)
/* 231:    */   {
/* 232:369 */     this.cuentaContableGastosBancarios = cuentaContableGastosBancarios;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<MovimientoBancario> getListaMovimientoBancario()
/* 236:    */   {
/* 237:373 */     return this.listaMovimientoBancario;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setListaMovimientoBancario(List<MovimientoBancario> listaMovimientoBancario)
/* 241:    */   {
/* 242:377 */     this.listaMovimientoBancario = listaMovimientoBancario;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public CuentaContable getCuentaContableBanco()
/* 246:    */   {
/* 247:381 */     return this.cuentaContableBanco;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setCuentaContableBanco(CuentaContable cuentaContableBanco)
/* 251:    */   {
/* 252:385 */     this.cuentaContableBanco = cuentaContableBanco;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public List<ConciliacionBancaria> getListaConciliacionBancaria()
/* 256:    */   {
/* 257:389 */     return this.listaConciliacionBancaria;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setListaConciliacionBancaria(List<ConciliacionBancaria> listaConciliacionBancaria)
/* 261:    */   {
/* 262:393 */     this.listaConciliacionBancaria = listaConciliacionBancaria;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<FormaPagoCuentaBancariaOrganizacion> getListaFormaPago()
/* 266:    */   {
/* 267:402 */     return this.listaFormaPago;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setListaFormaPago(List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago)
/* 271:    */   {
/* 272:412 */     this.listaFormaPago = listaFormaPago;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public int getIdOrganizacion()
/* 276:    */   {
/* 277:421 */     return this.idOrganizacion;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setIdOrganizacion(int idOrganizacion)
/* 281:    */   {
/* 282:431 */     this.idOrganizacion = idOrganizacion;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getReporte()
/* 286:    */   {
/* 287:435 */     return this.reporte;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setReporte(String reporte)
/* 291:    */   {
/* 292:439 */     this.reporte = reporte;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public BigDecimal getValorProtesto()
/* 296:    */   {
/* 297:448 */     return this.valorProtesto;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setValorProtesto(BigDecimal valorProtesto)
/* 301:    */   {
/* 302:458 */     this.valorProtesto = valorProtesto;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public CuentaContable getCuentaContableGastosProtesto()
/* 306:    */   {
/* 307:467 */     return this.cuentaContableGastosProtesto;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setCuentaContableGastosProtesto(CuentaContable cuentaContableGastosProtesto)
/* 311:    */   {
/* 312:477 */     this.cuentaContableGastosProtesto = cuentaContableGastosProtesto;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public Empresa getEmpresa()
/* 316:    */   {
/* 317:486 */     return this.empresa;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setEmpresa(Empresa empresa)
/* 321:    */   {
/* 322:496 */     this.empresa = empresa;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public Producto getProducto()
/* 326:    */   {
/* 327:505 */     return this.producto;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setProducto(Producto producto)
/* 331:    */   {
/* 332:515 */     this.producto = producto;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public CuentaContable getCuentaContableRetencion()
/* 336:    */   {
/* 337:524 */     return this.cuentaContableRetencion;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setCuentaContableRetencion(CuentaContable cuentaContableRetencion)
/* 341:    */   {
/* 342:534 */     this.cuentaContableRetencion = cuentaContableRetencion;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public boolean isIndicadorCruce()
/* 346:    */   {
/* 347:538 */     return this.indicadorCruce;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setIndicadorCruce(boolean indicadorCruce)
/* 351:    */   {
/* 352:542 */     this.indicadorCruce = indicadorCruce;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public List<CuentaContableCruceCuentaBancariaOrganizacion> getListaCuentaContableCruceCuentaBancariaOrganizacion()
/* 356:    */   {
/* 357:546 */     return this.listaCuentaContableCruceCuentaBancariaOrganizacion;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setListaCuentaContableCruceCuentaBancariaOrganizacion(List<CuentaContableCruceCuentaBancariaOrganizacion> listaCuentaContableCruceCuentaBancariaOrganizacion)
/* 361:    */   {
/* 362:551 */     this.listaCuentaContableCruceCuentaBancariaOrganizacion = listaCuentaContableCruceCuentaBancariaOrganizacion;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public CuentaContable getCuentaContableBancoPagoCash()
/* 366:    */   {
/* 367:555 */     return this.cuentaContableBancoPagoCash;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setCuentaContableBancoPagoCash(CuentaContable cuentaContableBancoPagoCash)
/* 371:    */   {
/* 372:559 */     this.cuentaContableBancoPagoCash = cuentaContableBancoPagoCash;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public CuentaContable getCuentaContableDiferencias()
/* 376:    */   {
/* 377:563 */     return this.cuentaContableDiferencias;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setCuentaContableDiferencias(CuentaContable cuentaContableDiferencias)
/* 381:    */   {
/* 382:567 */     this.cuentaContableDiferencias = cuentaContableDiferencias;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public String getLocalidad()
/* 386:    */   {
/* 387:574 */     return this.localidad;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setLocalidad(String localidad)
/* 391:    */   {
/* 392:582 */     this.localidad = localidad;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public List<BancoAcreedor> getListaBancoAcreedor()
/* 396:    */   {
/* 397:586 */     return this.listaBancoAcreedor;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setListaBancoAcreedor(List<BancoAcreedor> listaBancoAcreedor)
/* 401:    */   {
/* 402:590 */     this.listaBancoAcreedor = listaBancoAcreedor;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public CuentaContable getCuentaContableTransitoria()
/* 406:    */   {
/* 407:594 */     return this.cuentaContableTransitoria;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setCuentaContableTransitoria(CuentaContable cuentaContableTransitoria)
/* 411:    */   {
/* 412:598 */     this.cuentaContableTransitoria = cuentaContableTransitoria;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public boolean isIndicadorTransferenciaInterbancariaPagoRol()
/* 416:    */   {
/* 417:605 */     return this.indicadorTransferenciaInterbancariaPagoRol;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setIndicadorTransferenciaInterbancariaPagoRol(boolean indicadorTransferenciaInterbancariaPagoRol)
/* 421:    */   {
/* 422:613 */     this.indicadorTransferenciaInterbancariaPagoRol = indicadorTransferenciaInterbancariaPagoRol;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public boolean isIndicadorTransferenciaInterbancariaPagoProveedor()
/* 426:    */   {
/* 427:620 */     return this.indicadorTransferenciaInterbancariaPagoProveedor;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setIndicadorTransferenciaInterbancariaPagoProveedor(boolean indicadorTransferenciaInterbancariaPagoProveedor)
/* 431:    */   {
/* 432:628 */     this.indicadorTransferenciaInterbancariaPagoProveedor = indicadorTransferenciaInterbancariaPagoProveedor;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public ConfiguracionConciliacionBancaria getConfiguracionConciliacionBancaria()
/* 436:    */   {
/* 437:635 */     return this.configuracionConciliacionBancaria;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setConfiguracionConciliacionBancaria(ConfiguracionConciliacionBancaria configuracionConciliacionBancaria)
/* 441:    */   {
/* 442:643 */     this.configuracionConciliacionBancaria = configuracionConciliacionBancaria;
/* 443:    */   }
/* 444:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaBancariaOrganizacion
 * JD-Core Version:    0.7.0.1
 */