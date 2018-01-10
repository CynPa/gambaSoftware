/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*   4:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.persistence.Column;
/*  12:    */ import javax.persistence.Entity;
/*  13:    */ import javax.persistence.EnumType;
/*  14:    */ import javax.persistence.Enumerated;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.OneToMany;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="cuenta_contable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  30:    */ public class CuentaContable
/*  31:    */   extends EntidadBase
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="cuenta_contable", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_contable")
/*  38:    */   @Column(name="id_cuenta_contable")
/*  39:    */   private int idCuentaContable;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_nivel_cuenta", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private NivelCuenta nivelCuenta;
/*  44:    */   @Enumerated(EnumType.ORDINAL)
/*  45:    */   @Column(name="grupo_cuenta", nullable=false)
/*  46:    */   private GrupoCuenta grupoCuenta;
/*  47:    */   @Enumerated(EnumType.ORDINAL)
/*  48:    */   @Column(name="tipo_cuenta_contable", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private TipoCuentaContable tipoCuentaContable;
/*  51:    */   @Enumerated(EnumType.ORDINAL)
/*  52:    */   @Column(name="tipo_acceso_cuenta_contable", nullable=false)
/*  53:    */   private TipoAccesoContable tipoAccesoCuentaContable;
/*  54:    */   @Column(name="id_organizacion", nullable=false)
/*  55:    */   private int idOrganizacion;
/*  56:    */   @Column(name="id_sucursal", nullable=false)
/*  57:    */   private int idSucursal;
/*  58:    */   @Column(name="codigo", nullable=false, length=30)
/*  59:    */   @NotNull
/*  60:    */   @Size(min=1, max=30)
/*  61:    */   private String codigo;
/*  62:    */   @Column(name="codigo_alterno", nullable=true, length=50)
/*  63:    */   @Size(max=50)
/*  64:    */   private String codigoAlterno;
/*  65:    */   @Column(name="nombre", nullable=false, length=100)
/*  66:    */   @NotNull
/*  67:    */   @Size(min=2, max=100)
/*  68:    */   private String nombre;
/*  69:    */   @Column(name="indicador_movimiento", nullable=false)
/*  70:    */   private boolean indicadorMovimiento;
/*  71:    */   @Column(name="indicador_validar_distribucion", nullable=false)
/*  72:    */   private boolean indicadorValidarDistribucion;
/*  73:    */   @Column(name="descripcion", length=200, nullable=true)
/*  74:    */   @Size(max=200)
/*  75:    */   private String descripcion;
/*  76:    */   @Column(name="predeterminado", nullable=false)
/*  77:    */   private boolean predeterminado;
/*  78:    */   @Column(name="activo", nullable=false)
/*  79:    */   private boolean activo;
/*  80:    */   @Column(name="indicador_validar_dimension1", nullable=false)
/*  81:    */   private boolean indicadorValidarDimension1;
/*  82:    */   @Column(name="indicador_validar_dimension2", nullable=false)
/*  83:    */   private boolean indicadorValidarDimension2;
/*  84:    */   @Column(name="indicador_validar_dimension3", nullable=false)
/*  85:    */   private boolean indicadorValidarDimension3;
/*  86:    */   @Column(name="indicador_validar_dimension4", nullable=false)
/*  87:    */   private boolean indicadorValidarDimension4;
/*  88:    */   @Column(name="indicador_validar_dimension5", nullable=false)
/*  89:    */   private boolean indicadorValidarDimension5;
/*  90:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  91:    */   @JoinColumn(name="id_cuenta_padre", nullable=true)
/*  92:    */   private CuentaContable cuentaPadre;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_partida_presupuestaria", nullable=true)
/*  95:    */   private PartidaPresupuestaria partidaPresupuestaria;
/*  96:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_dimension_contable", nullable=true)
/*  98:    */   private DimensionContable dimensionContable;
/*  99:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaContable")
/* 100:141 */   private List<CuentaContableDimensionContable> listaCuentaContableDimensionContable = new ArrayList();
/* 101:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cuentaContable")
/* 102:144 */   private List<Prestamo> listaPrestamo = new ArrayList();
/* 103:    */   @Transient
/* 104:150 */   private List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 105:    */   @Transient
/* 106:    */   private String mascara;
/* 107:    */   @Transient
/* 108:    */   private String traNombreParaMostrar;
/* 109:    */   @Transient
/* 110:159 */   private BigDecimal traSaldoInicial = BigDecimal.ZERO;
/* 111:    */   @Transient
/* 112:162 */   private BigDecimal traDebe = BigDecimal.ZERO;
/* 113:    */   @Transient
/* 114:165 */   private BigDecimal traHaber = BigDecimal.ZERO;
/* 115:    */   @Transient
/* 116:168 */   private BigDecimal traSaldoFinal = BigDecimal.ZERO;
/* 117:    */   @Transient
/* 118:    */   private String codigoCuentaTransient;
/* 119:    */   
/* 120:    */   public CuentaContable(int idCuentaContable, String codigo, String nombre, boolean indicadorMovimiento)
/* 121:    */   {
/* 122:178 */     this.idCuentaContable = idCuentaContable;
/* 123:179 */     this.codigo = codigo;
/* 124:180 */     this.nombre = nombre;
/* 125:181 */     this.indicadorMovimiento = indicadorMovimiento;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public CuentaContable(int idCuentaContable)
/* 129:    */   {
/* 130:185 */     this.idCuentaContable = idCuentaContable;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public CuentaContable() {}
/* 134:    */   
/* 135:    */   public int getId()
/* 136:    */   {
/* 137:193 */     return this.idCuentaContable;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<String> getCamposAuditables()
/* 141:    */   {
/* 142:203 */     ArrayList<String> lista = new ArrayList();
/* 143:204 */     lista.add("nivelCuenta");
/* 144:205 */     lista.add("grupoCuenta");
/* 145:206 */     lista.add("tipoCuentaContable");
/* 146:207 */     lista.add("codigo");
/* 147:208 */     lista.add("nombre");
/* 148:209 */     lista.add("indicadorMovimiento");
/* 149:210 */     lista.add("indicadorValidarDistribucion");
/* 150:211 */     lista.add("predeterminado");
/* 151:212 */     lista.add("activo");
/* 152:    */     
/* 153:214 */     return lista;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getIdCuentaContable()
/* 157:    */   {
/* 158:223 */     return this.idCuentaContable;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setIdCuentaContable(int idCuentaContable)
/* 162:    */   {
/* 163:233 */     this.idCuentaContable = idCuentaContable;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public NivelCuenta getNivelCuenta()
/* 167:    */   {
/* 168:242 */     if (this.nivelCuenta == null) {
/* 169:243 */       this.nivelCuenta = new NivelCuenta();
/* 170:    */     }
/* 171:245 */     return this.nivelCuenta;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setNivelCuenta(NivelCuenta nivelCuenta)
/* 175:    */   {
/* 176:255 */     this.nivelCuenta = nivelCuenta;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public TipoCuentaContable getTipoCuentaContable()
/* 180:    */   {
/* 181:264 */     return this.tipoCuentaContable;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setTipoCuentaContable(TipoCuentaContable tipoCuentaContable)
/* 185:    */   {
/* 186:274 */     this.tipoCuentaContable = tipoCuentaContable;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public int getIdOrganizacion()
/* 190:    */   {
/* 191:283 */     return this.idOrganizacion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setIdOrganizacion(int idOrganizacion)
/* 195:    */   {
/* 196:293 */     this.idOrganizacion = idOrganizacion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getIdSucursal()
/* 200:    */   {
/* 201:302 */     return this.idSucursal;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setIdSucursal(int idSucursal)
/* 205:    */   {
/* 206:312 */     this.idSucursal = idSucursal;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public CuentaContable getCuentaPadre()
/* 210:    */   {
/* 211:316 */     return this.cuentaPadre;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setCuentaPadre(CuentaContable cuentaPadre)
/* 215:    */   {
/* 216:320 */     this.cuentaPadre = cuentaPadre;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String getCodigo()
/* 220:    */   {
/* 221:335 */     return this.codigo;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setCodigo(String codigo)
/* 225:    */   {
/* 226:345 */     this.codigo = codigo;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String getNombre()
/* 230:    */   {
/* 231:354 */     return this.nombre;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setNombre(String nombre)
/* 235:    */   {
/* 236:364 */     this.nombre = nombre;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean isIndicadorMovimiento()
/* 240:    */   {
/* 241:373 */     return this.indicadorMovimiento;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setIndicadorMovimiento(boolean indicadorMovimiento)
/* 245:    */   {
/* 246:383 */     this.indicadorMovimiento = indicadorMovimiento;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getDescripcion()
/* 250:    */   {
/* 251:392 */     return this.descripcion;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setDescripcion(String descripcion)
/* 255:    */   {
/* 256:402 */     this.descripcion = descripcion;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean isPredeterminado()
/* 260:    */   {
/* 261:411 */     return this.predeterminado;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setPredeterminado(boolean predeterminado)
/* 265:    */   {
/* 266:421 */     this.predeterminado = predeterminado;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean isActivo()
/* 270:    */   {
/* 271:430 */     return this.activo;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setActivo(boolean activo)
/* 275:    */   {
/* 276:440 */     this.activo = activo;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String getMascara()
/* 280:    */   {
/* 281:444 */     return this.mascara;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setMascara(String mascara)
/* 285:    */   {
/* 286:448 */     this.mascara = mascara;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public String getTraNombreParaMostrar()
/* 290:    */   {
/* 291:452 */     this.traNombreParaMostrar = (getCodigo() + "\t|\t" + getNombre());
/* 292:453 */     return this.traNombreParaMostrar;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setTraNombreParaMostrar(String traNombreParaMostrar)
/* 296:    */   {
/* 297:457 */     this.traNombreParaMostrar = traNombreParaMostrar;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 301:    */   {
/* 302:461 */     return this.listaDetalleAsiento;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 306:    */   {
/* 307:465 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean isIndicadorValidarDistribucion()
/* 311:    */   {
/* 312:469 */     return this.indicadorValidarDistribucion;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setIndicadorValidarDistribucion(boolean indicadorValidarDistribucion)
/* 316:    */   {
/* 317:473 */     this.indicadorValidarDistribucion = indicadorValidarDistribucion;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public GrupoCuenta getGrupoCuenta()
/* 321:    */   {
/* 322:477 */     return this.grupoCuenta;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setGrupoCuenta(GrupoCuenta grupoCuenta)
/* 326:    */   {
/* 327:481 */     this.grupoCuenta = grupoCuenta;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public TipoAccesoContable getTipoAccesoCuentaContable()
/* 331:    */   {
/* 332:490 */     return this.tipoAccesoCuentaContable;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setTipoAccesoCuentaContable(TipoAccesoContable tipoAccesoCuentaContable)
/* 336:    */   {
/* 337:500 */     this.tipoAccesoCuentaContable = tipoAccesoCuentaContable;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public PartidaPresupuestaria getPartidaPresupuestaria()
/* 341:    */   {
/* 342:504 */     return this.partidaPresupuestaria;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria)
/* 346:    */   {
/* 347:508 */     this.partidaPresupuestaria = partidaPresupuestaria;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public String toString()
/* 351:    */   {
/* 352:513 */     return this.nombre;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public BigDecimal getTraSaldoInicial()
/* 356:    */   {
/* 357:522 */     return this.traSaldoInicial;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setTraSaldoInicial(BigDecimal traSaldoInicial)
/* 361:    */   {
/* 362:532 */     this.traSaldoInicial = traSaldoInicial;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public BigDecimal getTraDebe()
/* 366:    */   {
/* 367:541 */     return this.traDebe;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setTraDebe(BigDecimal traDebe)
/* 371:    */   {
/* 372:551 */     this.traDebe = traDebe;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public BigDecimal getTraHaber()
/* 376:    */   {
/* 377:560 */     return this.traHaber;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setTraHaber(BigDecimal traHaber)
/* 381:    */   {
/* 382:570 */     this.traHaber = traHaber;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public BigDecimal getTraSaldoFinal()
/* 386:    */   {
/* 387:579 */     return this.traSaldoFinal;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setTraSaldoFinal(BigDecimal traSaldoFinal)
/* 391:    */   {
/* 392:589 */     this.traSaldoFinal = traSaldoFinal;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public boolean isIndicadorValidarDimension1()
/* 396:    */   {
/* 397:598 */     return this.indicadorValidarDimension1;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setIndicadorValidarDimension1(boolean indicadorValidarDimension1)
/* 401:    */   {
/* 402:608 */     this.indicadorValidarDimension1 = indicadorValidarDimension1;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public boolean isIndicadorValidarDimension2()
/* 406:    */   {
/* 407:617 */     return this.indicadorValidarDimension2;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setIndicadorValidarDimension2(boolean indicadorValidarDimension2)
/* 411:    */   {
/* 412:627 */     this.indicadorValidarDimension2 = indicadorValidarDimension2;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public boolean isIndicadorValidarDimension3()
/* 416:    */   {
/* 417:636 */     return this.indicadorValidarDimension3;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setIndicadorValidarDimension3(boolean indicadorValidarDimension3)
/* 421:    */   {
/* 422:646 */     this.indicadorValidarDimension3 = indicadorValidarDimension3;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public boolean isIndicadorValidarDimension4()
/* 426:    */   {
/* 427:655 */     return this.indicadorValidarDimension4;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setIndicadorValidarDimension4(boolean indicadorValidarDimension4)
/* 431:    */   {
/* 432:665 */     this.indicadorValidarDimension4 = indicadorValidarDimension4;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public boolean isIndicadorValidarDimension5()
/* 436:    */   {
/* 437:674 */     return this.indicadorValidarDimension5;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setIndicadorValidarDimension5(boolean indicadorValidarDimension5)
/* 441:    */   {
/* 442:684 */     this.indicadorValidarDimension5 = indicadorValidarDimension5;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public String getCodigoAlterno()
/* 446:    */   {
/* 447:688 */     return this.codigoAlterno;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setCodigoAlterno(String codigoAlterno)
/* 451:    */   {
/* 452:692 */     this.codigoAlterno = codigoAlterno;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public DimensionContable getDimensionContable()
/* 456:    */   {
/* 457:696 */     return this.dimensionContable;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 461:    */   {
/* 462:700 */     this.dimensionContable = dimensionContable;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public List<CuentaContableDimensionContable> getListaCuentaContableDimensionContable()
/* 466:    */   {
/* 467:704 */     return this.listaCuentaContableDimensionContable;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setListaCuentaContableDimensionContable(List<CuentaContableDimensionContable> listaCuentaContableDimensionContable)
/* 471:    */   {
/* 472:708 */     this.listaCuentaContableDimensionContable = listaCuentaContableDimensionContable;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public String getCodigoCuentaTransient()
/* 476:    */   {
/* 477:712 */     return this.codigoCuentaTransient;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setCodigoCuentaTransient(String codigoCuentaTransient)
/* 481:    */   {
/* 482:716 */     this.codigoCuentaTransient = codigoCuentaTransient;
/* 483:    */   }
/* 484:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaContable
 * JD-Core Version:    0.7.0.1
 */