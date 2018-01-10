/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
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
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="tmp_reporte_asiento")
/*  21:    */ public class ReporteAsiento
/*  22:    */ {
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="tmp_reporte_asiento", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_reporte_asiento")
/*  26:    */   @Column(name="id_tmp_reporte_asiento")
/*  27:    */   private Integer idReporteAsiento;
/*  28:    */   @Transient
/*  29:    */   private String numeroAsiento;
/*  30:    */   @Transient
/*  31:    */   private String tipoAsiento;
/*  32:    */   @Transient
/*  33:    */   private String concepto;
/*  34:    */   @Transient
/*  35:    */   @Temporal(TemporalType.DATE)
/*  36:    */   private Date fecha;
/*  37:    */   @Transient
/*  38:    */   private String codigoCuentaContable;
/*  39:    */   @Transient
/*  40:    */   private String codigoAlternoCuentaContable;
/*  41:    */   @Transient
/*  42:    */   private String nombreCuentaContable;
/*  43:    */   @Transient
/*  44: 52 */   private BigDecimal debe = BigDecimal.ZERO
/*  45: 53 */     .setScale(0);
/*  46:    */   @Transient
/*  47: 55 */   private BigDecimal haber = BigDecimal.ZERO
/*  48: 56 */     .setScale(0);
/*  49:    */   @Transient
/*  50:    */   private String descripcion;
/*  51:    */   @Transient
/*  52:    */   private String codigoDimensionContable1;
/*  53:    */   @Transient
/*  54:    */   private String nombreDimensionContable1;
/*  55:    */   @Transient
/*  56:    */   private String codigoDimensionContable2;
/*  57:    */   @Transient
/*  58:    */   private String nombreDimensionContable2;
/*  59:    */   @Transient
/*  60:    */   private String codigoDimensionContable3;
/*  61:    */   @Transient
/*  62:    */   private String nombreDimensionContable3;
/*  63:    */   @Transient
/*  64:    */   private String codigoDimensionContable4;
/*  65:    */   @Transient
/*  66:    */   private String nombreDimensionContable4;
/*  67:    */   @Transient
/*  68:    */   private String codigoDimensionContable5;
/*  69:    */   @Transient
/*  70:    */   private String nombreDimensionContable5;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_reporte_factura_proveedor", nullable=true)
/*  73:    */   private ReporteFacturaProveedor reporteFacturaProveedor;
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id_reporte_factura_proveedor_retencion", nullable=true)
/*  76:    */   private ReporteFacturaProveedor reporteFacturaProveedorRetencion;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_reporte_factura_proveedor_nota_credito", nullable=true)
/*  79:    */   private ReporteFacturaProveedor reporteFacturaProveedorNotaCredito;
/*  80:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  81:    */   @JoinColumn(name="id_reporte_factura_proveedor_pago", nullable=true)
/*  82:    */   private ReporteFacturaProveedor reporteFacturaProveedorPago;
/*  83:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  84:    */   @JoinColumn(name="id_reporte_factura_proveedor_anticipo", nullable=true)
/*  85:    */   private ReporteFacturaProveedor reporteFacturaProveedorAnticipo;
/*  86:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  87:    */   @JoinColumn(name="id_reporte_factura_proveedor_liquidacion_anticipo", nullable=true)
/*  88:    */   private ReporteFacturaProveedor reporteFacturaProveedorLiquidacionAnticipo;
/*  89:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  90:    */   @JoinColumn(name="id_reporte_factura_proveedor_liquidacion_recepcion", nullable=true)
/*  91:    */   private ReporteFacturaProveedor reporteFacturaProveedorRecepcion;
/*  92:    */   
/*  93:    */   public ReporteAsiento(String numeroAsiento, String tipoAsiento, String concepto, Date fecha, String codigoCuentaContable, String codigoAlternoCuentaContable, String nombreCuentaContable, BigDecimal debe, BigDecimal haber, String descripcion, String codigoDimensionContable1, String nombreDimensionContable1, String codigoDimensionContable2, String nombreDimensionContable2, String codigoDimensionContable3, String nombreDimensionContable3, String codigoDimensionContable4, String nombreDimensionContable4, String codigoDimensionContable5, String nombreDimensionContable5)
/*  94:    */   {
/*  95:125 */     this.numeroAsiento = numeroAsiento;
/*  96:126 */     this.tipoAsiento = tipoAsiento;
/*  97:127 */     this.concepto = concepto;
/*  98:128 */     this.fecha = fecha;
/*  99:129 */     this.codigoCuentaContable = codigoCuentaContable;
/* 100:130 */     this.codigoAlternoCuentaContable = codigoAlternoCuentaContable;
/* 101:131 */     this.nombreCuentaContable = nombreCuentaContable;
/* 102:132 */     this.debe = debe;
/* 103:133 */     this.haber = haber;
/* 104:134 */     this.descripcion = descripcion;
/* 105:135 */     this.codigoDimensionContable1 = codigoDimensionContable1;
/* 106:136 */     this.nombreDimensionContable1 = nombreDimensionContable1;
/* 107:137 */     this.codigoDimensionContable2 = codigoDimensionContable2;
/* 108:138 */     this.nombreDimensionContable2 = nombreDimensionContable2;
/* 109:139 */     this.codigoDimensionContable3 = codigoDimensionContable3;
/* 110:140 */     this.nombreDimensionContable3 = nombreDimensionContable3;
/* 111:141 */     this.codigoDimensionContable4 = codigoDimensionContable4;
/* 112:142 */     this.nombreDimensionContable4 = nombreDimensionContable4;
/* 113:143 */     this.codigoDimensionContable5 = codigoDimensionContable5;
/* 114:144 */     this.nombreDimensionContable5 = nombreDimensionContable5;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Integer getIdReporteAsiento()
/* 118:    */   {
/* 119:148 */     return this.idReporteAsiento;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdReporteAsiento(Integer idReporteAsiento)
/* 123:    */   {
/* 124:152 */     this.idReporteAsiento = idReporteAsiento;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getNumeroAsiento()
/* 128:    */   {
/* 129:156 */     return this.numeroAsiento;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setNumeroAsiento(String numeroAsiento)
/* 133:    */   {
/* 134:160 */     this.numeroAsiento = numeroAsiento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getTipoAsiento()
/* 138:    */   {
/* 139:164 */     return this.tipoAsiento;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setTipoAsiento(String tipoAsiento)
/* 143:    */   {
/* 144:168 */     this.tipoAsiento = tipoAsiento;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getConcepto()
/* 148:    */   {
/* 149:172 */     return this.concepto;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setConcepto(String concepto)
/* 153:    */   {
/* 154:176 */     this.concepto = concepto;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Date getFecha()
/* 158:    */   {
/* 159:180 */     return this.fecha;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFecha(Date fecha)
/* 163:    */   {
/* 164:184 */     this.fecha = fecha;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getCodigoCuentaContable()
/* 168:    */   {
/* 169:188 */     return this.codigoCuentaContable;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setCodigoCuentaContable(String codigoCuentaContable)
/* 173:    */   {
/* 174:192 */     this.codigoCuentaContable = codigoCuentaContable;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getCodigoAlternoCuentaContable()
/* 178:    */   {
/* 179:196 */     return this.codigoAlternoCuentaContable;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setCodigoAlternoCuentaContable(String codigoAlternoCuentaContable)
/* 183:    */   {
/* 184:200 */     this.codigoAlternoCuentaContable = codigoAlternoCuentaContable;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getNombreCuentaContable()
/* 188:    */   {
/* 189:204 */     return this.nombreCuentaContable;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setNombreCuentaContable(String nombreCuentaContable)
/* 193:    */   {
/* 194:208 */     this.nombreCuentaContable = nombreCuentaContable;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public BigDecimal getDebe()
/* 198:    */   {
/* 199:212 */     return this.debe;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setDebe(BigDecimal debe)
/* 203:    */   {
/* 204:216 */     this.debe = debe;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public BigDecimal getHaber()
/* 208:    */   {
/* 209:220 */     return this.haber;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setHaber(BigDecimal haber)
/* 213:    */   {
/* 214:224 */     this.haber = haber;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getDescripcion()
/* 218:    */   {
/* 219:228 */     return this.descripcion;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDescripcion(String descripcion)
/* 223:    */   {
/* 224:232 */     this.descripcion = descripcion;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCodigoDimensionContable1()
/* 228:    */   {
/* 229:236 */     return this.codigoDimensionContable1;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setCodigoDimensionContable1(String codigoDimensionContable1)
/* 233:    */   {
/* 234:240 */     this.codigoDimensionContable1 = codigoDimensionContable1;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getNombreDimensionContable1()
/* 238:    */   {
/* 239:244 */     return this.nombreDimensionContable1;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setNombreDimensionContable1(String nombreDimensionContable1)
/* 243:    */   {
/* 244:248 */     this.nombreDimensionContable1 = nombreDimensionContable1;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getCodigoDimensionContable2()
/* 248:    */   {
/* 249:252 */     return this.codigoDimensionContable2;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setCodigoDimensionContable2(String codigoDimensionContable2)
/* 253:    */   {
/* 254:256 */     this.codigoDimensionContable2 = codigoDimensionContable2;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getNombreDimensionContable2()
/* 258:    */   {
/* 259:260 */     return this.nombreDimensionContable2;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setNombreDimensionContable2(String nombreDimensionContable2)
/* 263:    */   {
/* 264:264 */     this.nombreDimensionContable2 = nombreDimensionContable2;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getCodigoDimensionContable3()
/* 268:    */   {
/* 269:268 */     return this.codigoDimensionContable3;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setCodigoDimensionContable3(String codigoDimensionContable3)
/* 273:    */   {
/* 274:272 */     this.codigoDimensionContable3 = codigoDimensionContable3;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getNombreDimensionContable3()
/* 278:    */   {
/* 279:276 */     return this.nombreDimensionContable3;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setNombreDimensionContable3(String nombreDimensionContable3)
/* 283:    */   {
/* 284:280 */     this.nombreDimensionContable3 = nombreDimensionContable3;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String getCodigoDimensionContable4()
/* 288:    */   {
/* 289:284 */     return this.codigoDimensionContable4;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setCodigoDimensionContable4(String codigoDimensionContable4)
/* 293:    */   {
/* 294:288 */     this.codigoDimensionContable4 = codigoDimensionContable4;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public String getNombreDimensionContable4()
/* 298:    */   {
/* 299:292 */     return this.nombreDimensionContable4;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setNombreDimensionContable4(String nombreDimensionContable4)
/* 303:    */   {
/* 304:296 */     this.nombreDimensionContable4 = nombreDimensionContable4;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public String getCodigoDimensionContable5()
/* 308:    */   {
/* 309:300 */     return this.codigoDimensionContable5;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setCodigoDimensionContable5(String codigoDimensionContable5)
/* 313:    */   {
/* 314:304 */     this.codigoDimensionContable5 = codigoDimensionContable5;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public String getNombreDimensionContable5()
/* 318:    */   {
/* 319:308 */     return this.nombreDimensionContable5;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setNombreDimensionContable5(String nombreDimensionContable5)
/* 323:    */   {
/* 324:312 */     this.nombreDimensionContable5 = nombreDimensionContable5;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public ReporteFacturaProveedor getReporteFacturaProveedor()
/* 328:    */   {
/* 329:316 */     return this.reporteFacturaProveedor;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setReporteFacturaProveedor(ReporteFacturaProveedor reporteFacturaProveedor)
/* 333:    */   {
/* 334:320 */     this.reporteFacturaProveedor = reporteFacturaProveedor;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public ReporteFacturaProveedor getReporteFacturaProveedorRetencion()
/* 338:    */   {
/* 339:324 */     return this.reporteFacturaProveedorRetencion;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setReporteFacturaProveedorRetencion(ReporteFacturaProveedor reporteFacturaProveedorRetencion)
/* 343:    */   {
/* 344:328 */     this.reporteFacturaProveedorRetencion = reporteFacturaProveedorRetencion;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public ReporteFacturaProveedor getReporteFacturaProveedorNotaCredito()
/* 348:    */   {
/* 349:332 */     return this.reporteFacturaProveedorNotaCredito;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setReporteFacturaProveedorNotaCredito(ReporteFacturaProveedor reporteFacturaProveedorNotaCredito)
/* 353:    */   {
/* 354:336 */     this.reporteFacturaProveedorNotaCredito = reporteFacturaProveedorNotaCredito;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public ReporteFacturaProveedor getReporteFacturaProveedorPago()
/* 358:    */   {
/* 359:340 */     return this.reporteFacturaProveedorPago;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setReporteFacturaProveedorPago(ReporteFacturaProveedor reporteFacturaProveedorPago)
/* 363:    */   {
/* 364:344 */     this.reporteFacturaProveedorPago = reporteFacturaProveedorPago;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public ReporteFacturaProveedor getReporteFacturaProveedorAnticipo()
/* 368:    */   {
/* 369:348 */     return this.reporteFacturaProveedorAnticipo;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setReporteFacturaProveedorAnticipo(ReporteFacturaProveedor reporteFacturaProveedorAnticipo)
/* 373:    */   {
/* 374:352 */     this.reporteFacturaProveedorAnticipo = reporteFacturaProveedorAnticipo;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public ReporteFacturaProveedor getReporteFacturaProveedorLiquidacionAnticipo()
/* 378:    */   {
/* 379:356 */     return this.reporteFacturaProveedorLiquidacionAnticipo;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setReporteFacturaProveedorLiquidacionAnticipo(ReporteFacturaProveedor reporteFacturaProveedorLiquidacionAnticipo)
/* 383:    */   {
/* 384:360 */     this.reporteFacturaProveedorLiquidacionAnticipo = reporteFacturaProveedorLiquidacionAnticipo;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public ReporteFacturaProveedor getReporteFacturaProveedorRecepcion()
/* 388:    */   {
/* 389:364 */     return this.reporteFacturaProveedorRecepcion;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setReporteFacturaProveedorRecepcion(ReporteFacturaProveedor reporteFacturaProveedorRecepcion)
/* 393:    */   {
/* 394:368 */     this.reporteFacturaProveedorRecepcion = reporteFacturaProveedorRecepcion;
/* 395:    */   }
/* 396:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteAsiento
 * JD-Core Version:    0.7.0.1
 */