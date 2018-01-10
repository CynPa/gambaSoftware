/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.OneToMany;
/*  21:    */ import javax.persistence.OneToOne;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.validation.constraints.Digits;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="prefactura_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})})
/*  33:    */ public class PrefacturaCliente
/*  34:    */   extends EntidadBase
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 3935545106378163867L;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="prefactura_cliente", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="prefactura_cliente")
/*  41:    */   @Column(name="id_prefactura_cliente")
/*  42:    */   private int idPrefacturaCliente;
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private int idOrganizacion;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Sucursal sucursal;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Empresa empresa;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  56:    */   private Subempresa subempresa;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_documento", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private Documento documento;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_zona", nullable=true)
/*  63:    */   private Zona zona;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_direccion_empresa", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   private DireccionEmpresa direccionEmpresa;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_canal", nullable=true)
/*  70:    */   private Canal canal;
/*  71:    */   @Temporal(TemporalType.DATE)
/*  72:    */   @Column(name="fecha", nullable=false)
/*  73:    */   @NotNull
/*  74:    */   private Date fecha;
/*  75:    */   @Column(name="numero", nullable=false, length=20)
/*  76:    */   @NotNull
/*  77:    */   @Size(max=20)
/*  78:    */   private String numero;
/*  79:    */   @Column(name="mso", nullable=true, length=20)
/*  80:    */   @Size(max=20)
/*  81:    */   private String mso;
/*  82:    */   @Column(name="guia", nullable=true, length=20)
/*  83:    */   @Size(max=20)
/*  84:    */   private String guia;
/*  85:    */   @Column(name="descripcion", nullable=true, length=500)
/*  86:    */   @Size(max=500)
/*  87:    */   private String descripcion;
/*  88:    */   @Column(name="descripcion2", nullable=true, length=500)
/*  89:    */   @Size(max=500)
/*  90:    */   private String descripcion2;
/*  91:    */   @Column(name="valor_bono", nullable=false, precision=12, scale=2)
/*  92:    */   @Digits(integer=12, fraction=2)
/*  93:    */   @Min(0L)
/*  94:123 */   private BigDecimal valorBono = BigDecimal.ZERO;
/*  95:    */   @Column(name="estado", nullable=false)
/*  96:    */   @Enumerated(EnumType.ORDINAL)
/*  97:    */   private Estado estado;
/*  98:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  99:    */   @JoinColumn(name="id_condicion_pago", nullable=false)
/* 100:    */   @NotNull
/* 101:    */   private CondicionPago condicionPago;
/* 102:    */   @Column(name="numero_cuotas", nullable=true, precision=12, scale=2)
/* 103:    */   @NotNull
/* 104:    */   @Digits(integer=12, fraction=2)
/* 105:    */   @Min(1L)
/* 106:    */   private int numeroCuotas;
/* 107:    */   @OneToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_agente_comercial", nullable=true)
/* 109:    */   private EntidadUsuario agenteComercial;
/* 110:    */   @NotNull
/* 111:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 112:    */   @JoinColumn(name="id_bodega", nullable=false)
/* 113:    */   private Bodega bodega;
/* 114:    */   @Column(name="filtro_busqueda", nullable=true, length=200)
/* 115:    */   @Size(max=200)
/* 116:    */   private String filtroBusqueda;
/* 117:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 118:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/* 119:    */   private FacturaCliente facturaCliente;
/* 120:    */   @Column(name="total", nullable=true, precision=12, scale=2)
/* 121:    */   @Digits(integer=12, fraction=2)
/* 122:    */   @Min(0L)
/* 123:160 */   private BigDecimal total = BigDecimal.ZERO;
/* 124:    */   @Column(name="descuento", nullable=true, precision=12, scale=2)
/* 125:    */   @Digits(integer=12, fraction=2)
/* 126:    */   @Min(0L)
/* 127:165 */   private BigDecimal descuento = BigDecimal.ZERO;
/* 128:    */   @Column(name="impuesto", nullable=true, precision=12, scale=2)
/* 129:    */   @Digits(integer=12, fraction=2)
/* 130:    */   @Min(0L)
/* 131:170 */   private BigDecimal impuesto = BigDecimal.ZERO;
/* 132:    */   @Column(name="indicador_saldo_inicial")
/* 133:176 */   private Boolean indicadorSaldoInicial = Boolean.valueOf(false);
/* 134:    */   @Column(name="indicador_generado_despacho")
/* 135:179 */   private Boolean indicadorGeneradoDespacho = Boolean.valueOf(false);
/* 136:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="prefacturaCliente")
/* 137:181 */   private List<AjustePrefacturaCliente> listaAjustePrefacturaCliente = new ArrayList();
/* 138:    */   
/* 139:    */   public boolean isAuditable()
/* 140:    */   {
/* 141:191 */     return true;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<String> getCamposAuditables()
/* 145:    */   {
/* 146:201 */     List<String> lista = new ArrayList();
/* 147:202 */     lista.add("empresa");
/* 148:203 */     lista.add("documento");
/* 149:204 */     lista.add("direccionEmpresa");
/* 150:205 */     lista.add("fecha");
/* 151:206 */     lista.add("total");
/* 152:207 */     lista.add("subtotal");
/* 153:208 */     lista.add("impuestoTotal");
/* 154:209 */     lista.add("descuento");
/* 155:210 */     lista.add("descripcion");
/* 156:211 */     lista.add("estado");
/* 157:212 */     lista.add("condicionPago");
/* 158:213 */     lista.add("numeroCuotas");
/* 159:    */     
/* 160:215 */     return lista;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public PrefacturaCliente() {}
/* 164:    */   
/* 165:    */   public PrefacturaCliente(int idPrefacturaCliente, String numero, Date fecha)
/* 166:    */   {
/* 167:232 */     this.idPrefacturaCliente = idPrefacturaCliente;
/* 168:233 */     this.numero = numero;
/* 169:234 */     this.fecha = fecha;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public PrefacturaCliente(int idPrefacturaCliente, Date fecha, String numero, String nombreFiscal)
/* 173:    */   {
/* 174:244 */     this(idPrefacturaCliente, numero, fecha);
/* 175:245 */     Empresa empresa = new Empresa();
/* 176:246 */     empresa.setNombreFiscal(nombreFiscal);
/* 177:247 */     setEmpresa(empresa);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getId()
/* 181:    */   {
/* 182:257 */     return this.idPrefacturaCliente;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int getIdPrefacturaCliente()
/* 186:    */   {
/* 187:266 */     return this.idPrefacturaCliente;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIdPrefacturaCliente(int idPrefacturaCliente)
/* 191:    */   {
/* 192:276 */     this.idPrefacturaCliente = idPrefacturaCliente;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getIdOrganizacion()
/* 196:    */   {
/* 197:285 */     return this.idOrganizacion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setIdOrganizacion(int idOrganizacion)
/* 201:    */   {
/* 202:295 */     this.idOrganizacion = idOrganizacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Sucursal getSucursal()
/* 206:    */   {
/* 207:304 */     return this.sucursal;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setSucursal(Sucursal sucursal)
/* 211:    */   {
/* 212:314 */     this.sucursal = sucursal;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Empresa getEmpresa()
/* 216:    */   {
/* 217:323 */     return this.empresa;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setEmpresa(Empresa empresa)
/* 221:    */   {
/* 222:333 */     this.empresa = empresa;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Subempresa getSubempresa()
/* 226:    */   {
/* 227:342 */     return this.subempresa;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setSubempresa(Subempresa subempresa)
/* 231:    */   {
/* 232:352 */     this.subempresa = subempresa;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Documento getDocumento()
/* 236:    */   {
/* 237:361 */     return this.documento;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setDocumento(Documento documento)
/* 241:    */   {
/* 242:371 */     this.documento = documento;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Zona getZona()
/* 246:    */   {
/* 247:380 */     return this.zona;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setZona(Zona zona)
/* 251:    */   {
/* 252:390 */     this.zona = zona;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public DireccionEmpresa getDireccionEmpresa()
/* 256:    */   {
/* 257:399 */     return this.direccionEmpresa;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 261:    */   {
/* 262:409 */     this.direccionEmpresa = direccionEmpresa;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Canal getCanal()
/* 266:    */   {
/* 267:418 */     return this.canal;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setCanal(Canal canal)
/* 271:    */   {
/* 272:428 */     this.canal = canal;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public Date getFecha()
/* 276:    */   {
/* 277:437 */     return this.fecha;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setFecha(Date fecha)
/* 281:    */   {
/* 282:447 */     this.fecha = fecha;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getNumero()
/* 286:    */   {
/* 287:456 */     return this.numero;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setNumero(String numero)
/* 291:    */   {
/* 292:466 */     this.numero = numero;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public String getDescripcion()
/* 296:    */   {
/* 297:475 */     return this.descripcion;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setDescripcion(String descripcion)
/* 301:    */   {
/* 302:485 */     this.descripcion = descripcion;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public Estado getEstado()
/* 306:    */   {
/* 307:494 */     return this.estado;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setEstado(Estado estado)
/* 311:    */   {
/* 312:504 */     this.estado = estado;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public CondicionPago getCondicionPago()
/* 316:    */   {
/* 317:513 */     return this.condicionPago;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 321:    */   {
/* 322:523 */     this.condicionPago = condicionPago;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public int getNumeroCuotas()
/* 326:    */   {
/* 327:532 */     return this.numeroCuotas;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setNumeroCuotas(int numeroCuotas)
/* 331:    */   {
/* 332:542 */     this.numeroCuotas = numeroCuotas;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public EntidadUsuario getAgenteComercial()
/* 336:    */   {
/* 337:551 */     return this.agenteComercial;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 341:    */   {
/* 342:561 */     this.agenteComercial = agenteComercial;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public Bodega getBodega()
/* 346:    */   {
/* 347:570 */     return this.bodega;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setBodega(Bodega bodega)
/* 351:    */   {
/* 352:580 */     this.bodega = bodega;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getFiltroBusqueda()
/* 356:    */   {
/* 357:589 */     return this.filtroBusqueda;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setFiltroBusqueda(String filtroBusqueda)
/* 361:    */   {
/* 362:599 */     this.filtroBusqueda = filtroBusqueda;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public FacturaCliente getFacturaCliente()
/* 366:    */   {
/* 367:608 */     return this.facturaCliente;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 371:    */   {
/* 372:618 */     this.facturaCliente = facturaCliente;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public BigDecimal getTotal()
/* 376:    */   {
/* 377:627 */     return this.total;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setTotal(BigDecimal total)
/* 381:    */   {
/* 382:637 */     this.total = total;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public BigDecimal getDescuento()
/* 386:    */   {
/* 387:646 */     return this.descuento;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setDescuento(BigDecimal descuento)
/* 391:    */   {
/* 392:656 */     this.descuento = descuento;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public BigDecimal getImpuesto()
/* 396:    */   {
/* 397:665 */     return this.impuesto;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setImpuesto(BigDecimal impuesto)
/* 401:    */   {
/* 402:675 */     this.impuesto = impuesto;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public List<AjustePrefacturaCliente> getListaAjustePrefacturaCliente()
/* 406:    */   {
/* 407:684 */     return this.listaAjustePrefacturaCliente;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setListaAjustePrefacturaCliente(List<AjustePrefacturaCliente> listaAjustePrefacturaCliente)
/* 411:    */   {
/* 412:694 */     this.listaAjustePrefacturaCliente = listaAjustePrefacturaCliente;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public Boolean getIndicadorSaldoInicial()
/* 416:    */   {
/* 417:703 */     return this.indicadorSaldoInicial;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setIndicadorSaldoInicial(Boolean indicadorSaldoInicial)
/* 421:    */   {
/* 422:713 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public String getDescripcion2()
/* 426:    */   {
/* 427:717 */     return this.descripcion2;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setDescripcion2(String descripcion2)
/* 431:    */   {
/* 432:721 */     this.descripcion2 = descripcion2;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public BigDecimal getValorBono()
/* 436:    */   {
/* 437:725 */     return this.valorBono;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setValorBono(BigDecimal valorBono)
/* 441:    */   {
/* 442:729 */     this.valorBono = valorBono;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public String getMso()
/* 446:    */   {
/* 447:733 */     return this.mso;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setMso(String mso)
/* 451:    */   {
/* 452:737 */     this.mso = mso;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public String getGuia()
/* 456:    */   {
/* 457:741 */     return this.guia;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setGuia(String guia)
/* 461:    */   {
/* 462:745 */     this.guia = guia;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public Boolean getIndicadorGeneradoDespacho()
/* 466:    */   {
/* 467:749 */     return this.indicadorGeneradoDespacho;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setIndicadorGeneradoDespacho(Boolean indicadorGeneradoDespacho)
/* 471:    */   {
/* 472:753 */     this.indicadorGeneradoDespacho = indicadorGeneradoDespacho;
/* 473:    */   }
/* 474:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PrefacturaCliente
 * JD-Core Version:    0.7.0.1
 */