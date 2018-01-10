/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.math.RoundingMode;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.DecimalMin;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_costo_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_orden_fabricacion", "id_costos_de_fabricacion"})})
/*  23:    */ public class DetalleCostoFabricacion
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 43902058619899582L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_costo_fabricacion", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_costo_fabricacion")
/*  30:    */   @Column(name="id_detalle_costo_fabricacion")
/*  31:    */   private int idDetalleCostoFabricacion;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_orden_fabricacion", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private OrdenFabricacion ordenFabricacion;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_costos_de_fabricacion", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private CostosDeFabricacion costosDeFabricacion;
/*  44:    */   @Column(name="cantidad_fabricada_mes", precision=12, scale=4, nullable=false)
/*  45:    */   @Digits(integer=12, fraction=4)
/*  46:    */   @DecimalMin("0.0000")
/*  47:    */   @NotNull
/*  48: 74 */   private BigDecimal cantidadFabricadaMes = BigDecimal.ZERO;
/*  49:    */   @Column(name="costo_materiales_inicial", precision=12, scale=4, nullable=false)
/*  50:    */   @Digits(integer=12, fraction=4)
/*  51:    */   @DecimalMin("0.0000")
/*  52:    */   @NotNull
/*  53: 81 */   private BigDecimal costoMaterialesInicial = BigDecimal.ZERO;
/*  54:    */   @Column(name="costo_materiales_mes", precision=12, scale=4, nullable=false)
/*  55:    */   @Digits(integer=12, fraction=4)
/*  56:    */   @DecimalMin("0.0000")
/*  57:    */   @NotNull
/*  58: 87 */   private BigDecimal costoMaterialesMes = BigDecimal.ZERO;
/*  59:    */   @Column(name="costo_materiales_asignado_mes", precision=12, scale=4, nullable=false)
/*  60:    */   @Digits(integer=12, fraction=4)
/*  61:    */   @DecimalMin("0.0000")
/*  62:    */   @NotNull
/*  63: 93 */   private BigDecimal costoMaterialesAsignadoMes = BigDecimal.ZERO;
/*  64:    */   @Column(name="costo_materiales_pendiente", precision=12, scale=4, nullable=false)
/*  65:    */   @Digits(integer=12, fraction=4)
/*  66:    */   @DecimalMin("0.0000")
/*  67:    */   @NotNull
/*  68: 99 */   private BigDecimal costoMaterialesPendiente = BigDecimal.ZERO;
/*  69:    */   @Column(name="costo_mano_obra_inicial", precision=12, scale=4, nullable=false)
/*  70:    */   @Digits(integer=12, fraction=4)
/*  71:    */   @DecimalMin("0.0000")
/*  72:    */   @NotNull
/*  73:106 */   private BigDecimal costoManoObraInicial = BigDecimal.ZERO;
/*  74:    */   @Column(name="costo_mano_obra_mes", precision=12, scale=4, nullable=false)
/*  75:    */   @Digits(integer=12, fraction=4)
/*  76:    */   @DecimalMin("0.0000")
/*  77:    */   @NotNull
/*  78:112 */   private BigDecimal costoManoObraMes = BigDecimal.ZERO;
/*  79:    */   @Column(name="costo_mano_obra_asignado_mes", precision=12, scale=4, nullable=false)
/*  80:    */   @Digits(integer=12, fraction=4)
/*  81:    */   @DecimalMin("0.0000")
/*  82:    */   @NotNull
/*  83:118 */   private BigDecimal costoManoObraAsignadoMes = BigDecimal.ZERO;
/*  84:    */   @Column(name="costo_mano_obra_pendiente", precision=12, scale=4, nullable=false)
/*  85:    */   @Digits(integer=12, fraction=4)
/*  86:    */   @DecimalMin("0.0000")
/*  87:    */   @NotNull
/*  88:124 */   private BigDecimal costoManoObraPendiente = BigDecimal.ZERO;
/*  89:    */   @Column(name="costo_depreciacion_inicial", precision=12, scale=4, nullable=false)
/*  90:    */   @Digits(integer=12, fraction=4)
/*  91:    */   @DecimalMin("0.0000")
/*  92:    */   @NotNull
/*  93:131 */   private BigDecimal costoDepreciacionInicial = BigDecimal.ZERO;
/*  94:    */   @Column(name="costo_depreciacion_mes", precision=12, scale=4, nullable=false)
/*  95:    */   @Digits(integer=12, fraction=4)
/*  96:    */   @DecimalMin("0.0000")
/*  97:    */   @NotNull
/*  98:137 */   private BigDecimal costoDepreciacionMes = BigDecimal.ZERO;
/*  99:    */   @Column(name="costo_depreciacion_asignado_mes", precision=12, scale=4, nullable=false)
/* 100:    */   @Digits(integer=12, fraction=4)
/* 101:    */   @DecimalMin("0.0000")
/* 102:    */   @NotNull
/* 103:143 */   private BigDecimal costoDepreciacionAsignadoMes = BigDecimal.ZERO;
/* 104:    */   @Column(name="costo_depreciacion_pendiente", precision=12, scale=4, nullable=false)
/* 105:    */   @Digits(integer=12, fraction=4)
/* 106:    */   @DecimalMin("0.0000")
/* 107:    */   @NotNull
/* 108:149 */   private BigDecimal costoDepreciacionPendiente = BigDecimal.ZERO;
/* 109:    */   @Column(name="costo_indirectos_inicial", precision=12, scale=4, nullable=false)
/* 110:    */   @Digits(integer=12, fraction=4)
/* 111:    */   @DecimalMin("0.0000")
/* 112:    */   @NotNull
/* 113:156 */   private BigDecimal costoIndirectosInicial = BigDecimal.ZERO;
/* 114:    */   @Column(name="costo_indirectos_mes", precision=12, scale=4, nullable=false)
/* 115:    */   @Digits(integer=12, fraction=4)
/* 116:    */   @DecimalMin("0.0000")
/* 117:    */   @NotNull
/* 118:162 */   private BigDecimal costoIndirectosMes = BigDecimal.ZERO;
/* 119:    */   @Column(name="costo_indirectos_asignado_mes", precision=12, scale=4, nullable=false)
/* 120:    */   @Digits(integer=12, fraction=4)
/* 121:    */   @DecimalMin("0.0000")
/* 122:    */   @NotNull
/* 123:168 */   private BigDecimal costoIndirectosAsignadoMes = BigDecimal.ZERO;
/* 124:    */   @Column(name="costo_indirectos_pendiente", precision=12, scale=4, nullable=false)
/* 125:    */   @Digits(integer=12, fraction=4)
/* 126:    */   @DecimalMin("0.0000")
/* 127:    */   @NotNull
/* 128:174 */   private BigDecimal costoIndirectosPendiente = BigDecimal.ZERO;
/* 129:    */   @Transient
/* 130:183 */   private BigDecimal cantidadFabricadaAntes = BigDecimal.ZERO;
/* 131:    */   @Transient
/* 132:    */   private boolean indicadorCierraOFEsteMes;
/* 133:    */   @Transient
/* 134:    */   private boolean indicadorCicloLargo;
/* 135:    */   
/* 136:    */   public int getId()
/* 137:    */   {
/* 138:202 */     return this.idDetalleCostoFabricacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getIdDetalleCostoFabricacion()
/* 142:    */   {
/* 143:206 */     return this.idDetalleCostoFabricacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setIdDetalleCostoFabricacion(int idDetalleCostoFabricacion)
/* 147:    */   {
/* 148:210 */     this.idDetalleCostoFabricacion = idDetalleCostoFabricacion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getIdOrganizacion()
/* 152:    */   {
/* 153:214 */     return this.idOrganizacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setIdOrganizacion(int idOrganizacion)
/* 157:    */   {
/* 158:218 */     this.idOrganizacion = idOrganizacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getIdSucursal()
/* 162:    */   {
/* 163:222 */     return this.idSucursal;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setIdSucursal(int idSucursal)
/* 167:    */   {
/* 168:226 */     this.idSucursal = idSucursal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public OrdenFabricacion getOrdenFabricacion()
/* 172:    */   {
/* 173:230 */     return this.ordenFabricacion;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 177:    */   {
/* 178:234 */     this.ordenFabricacion = ordenFabricacion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public CostosDeFabricacion getCostosDeFabricacion()
/* 182:    */   {
/* 183:238 */     return this.costosDeFabricacion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setCostosDeFabricacion(CostosDeFabricacion costosDeFabricacion)
/* 187:    */   {
/* 188:242 */     this.costosDeFabricacion = costosDeFabricacion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public BigDecimal getCantidadFabricadaMes()
/* 192:    */   {
/* 193:246 */     return this.cantidadFabricadaMes;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setCantidadFabricadaMes(BigDecimal cantidadFabricadaMes)
/* 197:    */   {
/* 198:250 */     this.cantidadFabricadaMes = cantidadFabricadaMes;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public BigDecimal getCostoMaterialesInicial()
/* 202:    */   {
/* 203:254 */     return this.costoMaterialesInicial;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setCostoMaterialesInicial(BigDecimal costoMaterialesInicial)
/* 207:    */   {
/* 208:258 */     this.costoMaterialesInicial = costoMaterialesInicial;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public BigDecimal getCostoMaterialesMes()
/* 212:    */   {
/* 213:262 */     return this.costoMaterialesMes;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setCostoMaterialesMes(BigDecimal costoMaterialesMes)
/* 217:    */   {
/* 218:266 */     this.costoMaterialesMes = costoMaterialesMes;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public BigDecimal getCostoMaterialesAsignadoMes()
/* 222:    */   {
/* 223:270 */     return this.costoMaterialesAsignadoMes;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setCostoMaterialesAsignadoMes(BigDecimal costoMaterialesAsignadoMes)
/* 227:    */   {
/* 228:274 */     this.costoMaterialesAsignadoMes = costoMaterialesAsignadoMes;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public BigDecimal getCostoMaterialesPendiente()
/* 232:    */   {
/* 233:278 */     return this.costoMaterialesPendiente;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setCostoMaterialesPendiente(BigDecimal costoMaterialesPendiente)
/* 237:    */   {
/* 238:282 */     this.costoMaterialesPendiente = costoMaterialesPendiente;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public BigDecimal getCostoManoObraInicial()
/* 242:    */   {
/* 243:286 */     return this.costoManoObraInicial;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setCostoManoObraInicial(BigDecimal costoManoObraInicial)
/* 247:    */   {
/* 248:290 */     this.costoManoObraInicial = costoManoObraInicial;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public BigDecimal getCostoManoObraMes()
/* 252:    */   {
/* 253:294 */     return this.costoManoObraMes;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setCostoManoObraMes(BigDecimal costoManoObraMes)
/* 257:    */   {
/* 258:298 */     this.costoManoObraMes = costoManoObraMes;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public BigDecimal getCostoManoObraAsignadoMes()
/* 262:    */   {
/* 263:302 */     return this.costoManoObraAsignadoMes;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setCostoManoObraAsignadoMes(BigDecimal costoManoObraAsignadoMes)
/* 267:    */   {
/* 268:306 */     this.costoManoObraAsignadoMes = costoManoObraAsignadoMes;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public BigDecimal getCostoManoObraPendiente()
/* 272:    */   {
/* 273:310 */     return this.costoManoObraPendiente;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setCostoManoObraPendiente(BigDecimal costoManoObraPendiente)
/* 277:    */   {
/* 278:314 */     this.costoManoObraPendiente = costoManoObraPendiente;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public BigDecimal getCostoDepreciacionInicial()
/* 282:    */   {
/* 283:318 */     return this.costoDepreciacionInicial;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setCostoDepreciacionInicial(BigDecimal costoDepreciacionInicial)
/* 287:    */   {
/* 288:322 */     this.costoDepreciacionInicial = costoDepreciacionInicial;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public BigDecimal getCostoDepreciacionMes()
/* 292:    */   {
/* 293:326 */     return this.costoDepreciacionMes;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setCostoDepreciacionMes(BigDecimal costoDepreciacionMes)
/* 297:    */   {
/* 298:330 */     this.costoDepreciacionMes = costoDepreciacionMes;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public BigDecimal getCostoDepreciacionAsignadoMes()
/* 302:    */   {
/* 303:334 */     return this.costoDepreciacionAsignadoMes;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setCostoDepreciacionAsignadoMes(BigDecimal costoDepreciacionAsignadoMes)
/* 307:    */   {
/* 308:338 */     this.costoDepreciacionAsignadoMes = costoDepreciacionAsignadoMes;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public BigDecimal getCostoDepreciacionPendiente()
/* 312:    */   {
/* 313:342 */     return this.costoDepreciacionPendiente;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setCostoDepreciacionPendiente(BigDecimal costoDepreciacionPendiente)
/* 317:    */   {
/* 318:346 */     this.costoDepreciacionPendiente = costoDepreciacionPendiente;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public BigDecimal getCostoIndirectosInicial()
/* 322:    */   {
/* 323:350 */     return this.costoIndirectosInicial;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setCostoIndirectosInicial(BigDecimal costoIndirectosInicial)
/* 327:    */   {
/* 328:354 */     this.costoIndirectosInicial = costoIndirectosInicial;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public BigDecimal getCostoIndirectosMes()
/* 332:    */   {
/* 333:358 */     return this.costoIndirectosMes;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setCostoIndirectosMes(BigDecimal costoIndirectosMes)
/* 337:    */   {
/* 338:362 */     this.costoIndirectosMes = costoIndirectosMes;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public BigDecimal getCostoIndirectosAsignadoMes()
/* 342:    */   {
/* 343:366 */     return this.costoIndirectosAsignadoMes;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setCostoIndirectosAsignadoMes(BigDecimal costoIndirectosAsignadoMes)
/* 347:    */   {
/* 348:370 */     this.costoIndirectosAsignadoMes = costoIndirectosAsignadoMes;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public BigDecimal getCostoIndirectosPendiente()
/* 352:    */   {
/* 353:374 */     return this.costoIndirectosPendiente;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setCostoIndirectosPendiente(BigDecimal costoIndirectosPendiente)
/* 357:    */   {
/* 358:378 */     this.costoIndirectosPendiente = costoIndirectosPendiente;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public boolean isIndicadorCierraOFEsteMes()
/* 362:    */   {
/* 363:382 */     return this.indicadorCierraOFEsteMes;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setIndicadorCierraOFEsteMes(boolean indicadorCierraOFEsteMes)
/* 367:    */   {
/* 368:386 */     this.indicadorCierraOFEsteMes = indicadorCierraOFEsteMes;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public boolean isIndicadorCicloLargo()
/* 372:    */   {
/* 373:390 */     return this.indicadorCicloLargo;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setIndicadorCicloLargo(boolean indicadorCicloLargo)
/* 377:    */   {
/* 378:394 */     this.indicadorCicloLargo = indicadorCicloLargo;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public BigDecimal getCantidadFabricadaAntes()
/* 382:    */   {
/* 383:398 */     return this.cantidadFabricadaAntes;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setCantidadFabricadaAntes(BigDecimal cantidadFabricadaAntes)
/* 387:    */   {
/* 388:402 */     this.cantidadFabricadaAntes = cantidadFabricadaAntes;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public BigDecimal getCostoUnitarioMaterialesAsignado()
/* 392:    */   {
/* 393:406 */     if (getCantidadFabricadaMes().compareTo(BigDecimal.ZERO) == 0) {
/* 394:407 */       return BigDecimal.ZERO;
/* 395:    */     }
/* 396:409 */     return getCostoMaterialesAsignadoMes().divide(getCantidadFabricadaMes(), 4, RoundingMode.HALF_UP);
/* 397:    */   }
/* 398:    */   
/* 399:    */   public BigDecimal getCostoUnitarioManoObraAsignado()
/* 400:    */   {
/* 401:413 */     if (getCantidadFabricadaMes().compareTo(BigDecimal.ZERO) == 0) {
/* 402:414 */       return BigDecimal.ZERO;
/* 403:    */     }
/* 404:416 */     return getCostoManoObraAsignadoMes().divide(getCantidadFabricadaMes(), 4, RoundingMode.HALF_UP);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public BigDecimal getCostoUnitarioDepreciacionAsignado()
/* 408:    */   {
/* 409:420 */     if (getCantidadFabricadaMes().compareTo(BigDecimal.ZERO) == 0) {
/* 410:421 */       return BigDecimal.ZERO;
/* 411:    */     }
/* 412:423 */     return getCostoDepreciacionAsignadoMes().divide(getCantidadFabricadaMes(), 4, RoundingMode.HALF_UP);
/* 413:    */   }
/* 414:    */   
/* 415:    */   public BigDecimal getCostoUnitarioIndirectosAsignado()
/* 416:    */   {
/* 417:427 */     if (getCantidadFabricadaMes().compareTo(BigDecimal.ZERO) == 0) {
/* 418:428 */       return BigDecimal.ZERO;
/* 419:    */     }
/* 420:430 */     return getCostoIndirectosAsignadoMes().divide(getCantidadFabricadaMes(), 4, RoundingMode.HALF_UP);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public BigDecimal getCostoTotalMes()
/* 424:    */   {
/* 425:434 */     return getCostoMaterialesMes().add(getCostoManoObraMes()).add(getCostoDepreciacionMes()).add(getCostoIndirectosMes());
/* 426:    */   }
/* 427:    */   
/* 428:    */   public BigDecimal getCostoTotalAsignadoMes()
/* 429:    */   {
/* 430:439 */     return getCostoMaterialesAsignadoMes().add(getCostoManoObraAsignadoMes()).add(getCostoDepreciacionAsignadoMes()).add(getCostoIndirectosAsignadoMes());
/* 431:    */   }
/* 432:    */   
/* 433:    */   public BigDecimal getCostoUnitarioAsignado()
/* 434:    */   {
/* 435:443 */     if (getCantidadFabricadaMes().compareTo(BigDecimal.ZERO) == 0) {
/* 436:444 */       return BigDecimal.ZERO;
/* 437:    */     }
/* 438:446 */     return getCostoTotalAsignadoMes().divide(getCantidadFabricadaMes(), 4, RoundingMode.HALF_UP);
/* 439:    */   }
/* 440:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.DetalleCostoFabricacion
 * JD-Core Version:    0.7.0.1
 */