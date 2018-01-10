/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoRubro;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoRubroEnum;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ import org.hibernate.annotations.ColumnDefault;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="rubro", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  27:    */ public class Rubro
/*  28:    */   extends EntidadBase
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 6655411391656292410L;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="rubro", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rubro")
/*  38:    */   @Column(name="id_rubro")
/*  39:    */   private int idRubro;
/*  40:    */   @Column(name="codigo", nullable=false, length=10)
/*  41:    */   @Size(max=10)
/*  42:    */   private String codigo;
/*  43:    */   @Column(name="nombre", nullable=false, length=50)
/*  44:    */   @Size(max=50)
/*  45:    */   private String nombre;
/*  46:    */   @Column(name="operacion", nullable=false)
/*  47:    */   private int operacion;
/*  48:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  49:    */   @NotNull
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52: 80 */   private BigDecimal valor = BigDecimal.ZERO;
/*  53:    */   @Column(name="formula", nullable=true, length=200)
/*  54:    */   @Size(max=200)
/*  55:    */   private String formula;
/*  56:    */   @Column(name="orden_calculo", nullable=false)
/*  57:    */   private int ordenCalculo;
/*  58:    */   @Column(name="orden_impresion", nullable=false)
/*  59:    */   private int ordenImpresion;
/*  60:    */   @Column(name="indicador_tiempo", nullable=false)
/*  61:    */   private boolean indicadorTiempo;
/*  62:    */   @Column(name="indicador_provision", nullable=false)
/*  63:    */   private boolean indicadorProvision;
/*  64:    */   @Column(name="indicador_impresion_sobre", nullable=false)
/*  65:    */   private boolean indicadorImpresionSobre;
/*  66:    */   @Column(name="indicador_calculo_IESS", nullable=false)
/*  67:    */   private boolean indicadorCalculoIESS;
/*  68:    */   @Column(name="indicador_calculo_impuesto_renta", nullable=false)
/*  69:    */   private boolean indicadorCalculoImpuestoRenta;
/*  70:    */   @Column(name="indicador_proyectar", nullable=false)
/*  71:    */   private boolean indicadorProyectar;
/*  72:    */   @Column(name="indicador_dias_trabajados", nullable=false)
/*  73:    */   private boolean indicadorDiasTrabajados;
/*  74:    */   @Column(name="descripcion", nullable=true, length=200)
/*  75:    */   @Size(max=200)
/*  76:    */   private String descripcion;
/*  77:    */   @Column(name="activo", nullable=false)
/*  78:    */   private boolean activo;
/*  79:    */   @Column(name="predeterminado", nullable=false)
/*  80:    */   private boolean predeterminado;
/*  81:    */   @Column(name="tipo_rubro", nullable=false)
/*  82:    */   @Enumerated(EnumType.ORDINAL)
/*  83:    */   private TipoRubro tipoRubro;
/*  84:    */   @Column(name="indicador_pago_finiquito", nullable=false)
/*  85:    */   private boolean indicadorPagoFiniquito;
/*  86:    */   @Column(name="valor_maximo", nullable=true, precision=12, scale=2)
/*  87:    */   @Digits(integer=12, fraction=2)
/*  88:    */   @Min(0L)
/*  89:    */   private BigDecimal valorMaximo;
/*  90:    */   @Column(name="mes_calculo_desde", nullable=true)
/*  91:    */   private int mesCalculoDesde;
/*  92:    */   @Column(name="mes_calculo_hasta", nullable=true)
/*  93:    */   private int mesCalculoHasta;
/*  94:    */   @Column(name="mes_pago", nullable=true)
/*  95:    */   private int mesPago;
/*  96:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  97:    */   @JoinColumn(name="id_quincena", nullable=true)
/*  98:    */   private Quincena quincena;
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/* 101:    */   private CuentaContable cuentaContableProvision;
/* 102:    */   @OneToOne(fetch=FetchType.LAZY)
/* 103:    */   @JoinColumn(name="id_rubro_padre", nullable=true)
/* 104:    */   private Rubro rubroPadre;
/* 105:    */   @Enumerated(EnumType.STRING)
/* 106:    */   @Column(name="tipo", length=50, nullable=true)
/* 107:    */   private TipoRubroEnum tipo;
/* 108:    */   @Column(name="indicador_decimo_cuarto_acumulado", nullable=false)
/* 109:    */   @ColumnDefault("'0'")
/* 110:    */   private boolean indicadorDecimoCuartoAcumulado;
/* 111:    */   
/* 112:    */   public Rubro() {}
/* 113:    */   
/* 114:    */   public Rubro(int idRubro, String codigo, String nombre)
/* 115:    */   {
/* 116:192 */     this.idRubro = idRubro;
/* 117:193 */     this.codigo = codigo;
/* 118:194 */     this.nombre = nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getIdOrganizacion()
/* 122:    */   {
/* 123:207 */     return this.idOrganizacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIdOrganizacion(int idOrganizacion)
/* 127:    */   {
/* 128:217 */     this.idOrganizacion = idOrganizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getIdSucursal()
/* 132:    */   {
/* 133:226 */     return this.idSucursal;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setIdSucursal(int idSucursal)
/* 137:    */   {
/* 138:236 */     this.idSucursal = idSucursal;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getCodigo()
/* 142:    */   {
/* 143:245 */     return this.codigo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setCodigo(String codigo)
/* 147:    */   {
/* 148:255 */     this.codigo = codigo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getNombre()
/* 152:    */   {
/* 153:264 */     return this.nombre;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setNombre(String nombre)
/* 157:    */   {
/* 158:274 */     this.nombre = nombre;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getOperacion()
/* 162:    */   {
/* 163:283 */     return this.operacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setOperacion(int operacion)
/* 167:    */   {
/* 168:293 */     this.operacion = operacion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public BigDecimal getValor()
/* 172:    */   {
/* 173:302 */     return this.valor;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setValor(BigDecimal valor)
/* 177:    */   {
/* 178:312 */     this.valor = valor;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getFormula()
/* 182:    */   {
/* 183:321 */     return this.formula;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setFormula(String formula)
/* 187:    */   {
/* 188:331 */     this.formula = formula;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public TipoRubro getTipoRubro()
/* 192:    */   {
/* 193:340 */     return this.tipoRubro;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setTipoRubro(TipoRubro tipoRubro)
/* 197:    */   {
/* 198:350 */     this.tipoRubro = tipoRubro;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getOrdenCalculo()
/* 202:    */   {
/* 203:359 */     return this.ordenCalculo;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setOrdenCalculo(int ordenCalculo)
/* 207:    */   {
/* 208:369 */     this.ordenCalculo = ordenCalculo;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean isIndicadorTiempo()
/* 212:    */   {
/* 213:378 */     return this.indicadorTiempo;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setIndicadorTiempo(boolean indicadorTiempo)
/* 217:    */   {
/* 218:388 */     this.indicadorTiempo = indicadorTiempo;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean isIndicadorProvision()
/* 222:    */   {
/* 223:397 */     return this.indicadorProvision;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIndicadorProvision(boolean indicadorProvision)
/* 227:    */   {
/* 228:407 */     this.indicadorProvision = indicadorProvision;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isIndicadorImpresionSobre()
/* 232:    */   {
/* 233:416 */     return this.indicadorImpresionSobre;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIndicadorImpresionSobre(boolean indicadorImpresionSobre)
/* 237:    */   {
/* 238:426 */     this.indicadorImpresionSobre = indicadorImpresionSobre;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isIndicadorCalculoIESS()
/* 242:    */   {
/* 243:435 */     return this.indicadorCalculoIESS;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setIndicadorCalculoIESS(boolean indicadorCalculoIESS)
/* 247:    */   {
/* 248:445 */     this.indicadorCalculoIESS = indicadorCalculoIESS;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getDescripcion()
/* 252:    */   {
/* 253:454 */     return this.descripcion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setDescripcion(String descripcion)
/* 257:    */   {
/* 258:464 */     this.descripcion = descripcion;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean isActivo()
/* 262:    */   {
/* 263:473 */     return this.activo;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setActivo(boolean activo)
/* 267:    */   {
/* 268:483 */     this.activo = activo;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean isPredeterminado()
/* 272:    */   {
/* 273:492 */     return this.predeterminado;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setPredeterminado(boolean predeterminado)
/* 277:    */   {
/* 278:502 */     this.predeterminado = predeterminado;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public int getIdRubro()
/* 282:    */   {
/* 283:511 */     return this.idRubro;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setIdRubro(int idRubro)
/* 287:    */   {
/* 288:521 */     this.idRubro = idRubro;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Quincena getQuincena()
/* 292:    */   {
/* 293:530 */     return this.quincena;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setQuincena(Quincena quincena)
/* 297:    */   {
/* 298:540 */     this.quincena = quincena;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public boolean isIndicadorCalculoImpuestoRenta()
/* 302:    */   {
/* 303:549 */     return this.indicadorCalculoImpuestoRenta;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setIndicadorCalculoImpuestoRenta(boolean indicadorCalculoImpuestoRenta)
/* 307:    */   {
/* 308:559 */     this.indicadorCalculoImpuestoRenta = indicadorCalculoImpuestoRenta;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public CuentaContable getCuentaContableProvision()
/* 312:    */   {
/* 313:568 */     return this.cuentaContableProvision;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setCuentaContableProvision(CuentaContable cuentaContableProvision)
/* 317:    */   {
/* 318:578 */     this.cuentaContableProvision = cuentaContableProvision;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public Rubro getRubroPadre()
/* 322:    */   {
/* 323:587 */     return this.rubroPadre;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setRubroPadre(Rubro rubroPadre)
/* 327:    */   {
/* 328:597 */     this.rubroPadre = rubroPadre;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public boolean isIndicadorDiasTrabajados()
/* 332:    */   {
/* 333:606 */     return this.indicadorDiasTrabajados;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setIndicadorDiasTrabajados(boolean indicadorDiasTrabajados)
/* 337:    */   {
/* 338:616 */     this.indicadorDiasTrabajados = indicadorDiasTrabajados;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public int getOrdenImpresion()
/* 342:    */   {
/* 343:625 */     return this.ordenImpresion;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setOrdenImpresion(int ordenImpresion)
/* 347:    */   {
/* 348:635 */     this.ordenImpresion = ordenImpresion;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean isIndicadorPagoFiniquito()
/* 352:    */   {
/* 353:644 */     return this.indicadorPagoFiniquito;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setIndicadorPagoFiniquito(boolean indicadorPagoFiniquito)
/* 357:    */   {
/* 358:654 */     this.indicadorPagoFiniquito = indicadorPagoFiniquito;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public boolean isIndicadorProyectar()
/* 362:    */   {
/* 363:658 */     return this.indicadorProyectar;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setIndicadorProyectar(boolean indicadorProyectar)
/* 367:    */   {
/* 368:662 */     this.indicadorProyectar = indicadorProyectar;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public int getId()
/* 372:    */   {
/* 373:672 */     return this.idRubro;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public BigDecimal getValorMaximo()
/* 377:    */   {
/* 378:676 */     return this.valorMaximo;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void setValorMaximo(BigDecimal valorMaximo)
/* 382:    */   {
/* 383:680 */     this.valorMaximo = valorMaximo;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public TipoRubroEnum getTipo()
/* 387:    */   {
/* 388:687 */     return this.tipo;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setTipo(TipoRubroEnum tipo)
/* 392:    */   {
/* 393:695 */     this.tipo = tipo;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public int getMesCalculoDesde()
/* 397:    */   {
/* 398:704 */     return this.mesCalculoDesde;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void setMesCalculoDesde(int mesCalculoDesde)
/* 402:    */   {
/* 403:714 */     this.mesCalculoDesde = mesCalculoDesde;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public int getMesCalculoHasta()
/* 407:    */   {
/* 408:723 */     return this.mesCalculoHasta;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void setMesCalculoHasta(int mesCalculoHasta)
/* 412:    */   {
/* 413:733 */     this.mesCalculoHasta = mesCalculoHasta;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public int getMesPago()
/* 417:    */   {
/* 418:737 */     return this.mesPago;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void setMesPago(int mesPago)
/* 422:    */   {
/* 423:741 */     this.mesPago = mesPago;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public boolean isIndicadorDecimoCuartoAcumulado()
/* 427:    */   {
/* 428:745 */     return this.indicadorDecimoCuartoAcumulado;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setIndicadorDecimoCuartoAcumulado(boolean indicadorDecimoCuartoAcumulado)
/* 432:    */   {
/* 433:749 */     this.indicadorDecimoCuartoAcumulado = indicadorDecimoCuartoAcumulado;
/* 434:    */   }
/* 435:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Rubro
 * JD-Core Version:    0.7.0.1
 */