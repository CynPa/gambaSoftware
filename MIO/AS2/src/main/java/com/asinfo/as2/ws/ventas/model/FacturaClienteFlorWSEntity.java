/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Calendar;
/*   6:    */ 
/*   7:    */ public class FacturaClienteFlorWSEntity
/*   8:    */   extends FacturaClienteBaseWSEntity
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1L;
/*  12:    */   private Long idOrganizacion;
/*  13:    */   private Long idDocumento;
/*  14:    */   private Long idEmpresa;
/*  15:    */   private Calendar fechaVuelo;
/*  16:    */   private Long idSubempresa;
/*  17:    */   private Long idDireccionEmpresa;
/*  18:    */   private Long idFacturaCliente;
/*  19:    */   private Long idFormaPago;
/*  20:    */   private Long idCondicionPago;
/*  21:    */   private Long idSucursal;
/*  22:    */   private boolean indicadorSaldoInicial;
/*  23:    */   private boolean indicadorDocumentoExterior;
/*  24:    */   private boolean indicadorDocumentoTributario;
/*  25:    */   private String refrendo;
/*  26:    */   private String distritoAduanero;
/*  27:    */   private String regimen;
/*  28:    */   private String correlativo;
/*  29:    */   private String numeroDocumentoTransporte;
/*  30:    */   private Long idAgenteComercial;
/*  31:    */   private String numeroPacking;
/*  32:    */   private String invoice;
/*  33:    */   private String guiaMadre;
/*  34: 71 */   private BigDecimal totalCajas = BigDecimal.ZERO.setScale(2);
/*  35: 73 */   private BigDecimal totalPiezas = BigDecimal.ZERO.setScale(2);
/*  36: 75 */   private BigDecimal totalTallos = BigDecimal.ZERO.setScale(2);
/*  37:    */   private String email;
/*  38: 79 */   private Boolean generarDocumentoElectronico = Boolean.valueOf(false);
/*  39:    */   private String referencia1;
/*  40:    */   private String dae;
/*  41:    */   private String guiaHija;
/*  42:    */   private String agenciaCarga;
/*  43:    */   private String empresa;
/*  44:    */   private String subempresa;
/*  45:    */   private Long idZona;
/*  46: 92 */   private Boolean noGenerarDocumentoElectronico = Boolean.valueOf(false);
/*  47:    */   private boolean indicador_automatico;
/*  48:    */   
/*  49:    */   public Long getIdCondicionPago()
/*  50:    */   {
/*  51:105 */     return this.idCondicionPago;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdCondicionPago(Long idCondicionPago)
/*  55:    */   {
/*  56:114 */     this.idCondicionPago = idCondicionPago;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Long getIdDocumento()
/*  60:    */   {
/*  61:123 */     return this.idDocumento;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdDocumento(Long idDocumento)
/*  65:    */   {
/*  66:132 */     this.idDocumento = idDocumento;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Long getIdEmpresa()
/*  70:    */   {
/*  71:141 */     return this.idEmpresa;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdEmpresa(Long idEmpresa)
/*  75:    */   {
/*  76:150 */     this.idEmpresa = idEmpresa;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Long getIdFacturaCliente()
/*  80:    */   {
/*  81:159 */     return this.idFacturaCliente;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdFacturaCliente(Long idFacturaCliente)
/*  85:    */   {
/*  86:168 */     this.idFacturaCliente = idFacturaCliente;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Long getIdFormaPago()
/*  90:    */   {
/*  91:177 */     return this.idFormaPago;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdFormaPago(Long idFormaPago)
/*  95:    */   {
/*  96:186 */     this.idFormaPago = idFormaPago;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Long getIdOrganizacion()
/* 100:    */   {
/* 101:195 */     return this.idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdOrganizacion(Long idOrganizacion)
/* 105:    */   {
/* 106:204 */     this.idOrganizacion = idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Long getIdSucursal()
/* 110:    */   {
/* 111:213 */     return this.idSucursal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdSucursal(Long idSucursal)
/* 115:    */   {
/* 116:222 */     this.idSucursal = idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isIndicadorSaldoInicial()
/* 120:    */   {
/* 121:231 */     return this.indicadorSaldoInicial;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/* 125:    */   {
/* 126:240 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Long getIdSubempresa()
/* 130:    */   {
/* 131:249 */     return this.idSubempresa;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIdSubempresa(Long idSubempresa)
/* 135:    */   {
/* 136:259 */     this.idSubempresa = idSubempresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isIndicadorDocumentoExterior()
/* 140:    */   {
/* 141:268 */     return this.indicadorDocumentoExterior;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setIndicadorDocumentoExterior(boolean indicadorDocumentoExterior)
/* 145:    */   {
/* 146:278 */     this.indicadorDocumentoExterior = indicadorDocumentoExterior;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Long getIdDireccionEmpresa()
/* 150:    */   {
/* 151:285 */     return this.idDireccionEmpresa;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setIdDireccionEmpresa(Long idDireccionEmpresa)
/* 155:    */   {
/* 156:293 */     this.idDireccionEmpresa = idDireccionEmpresa;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getRefrendo()
/* 160:    */   {
/* 161:302 */     return this.refrendo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setRefrendo(String refrendo)
/* 165:    */   {
/* 166:312 */     this.refrendo = refrendo;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getDistritoAduanero()
/* 170:    */   {
/* 171:321 */     return this.distritoAduanero;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setDistritoAduanero(String distritoAduanero)
/* 175:    */   {
/* 176:331 */     this.distritoAduanero = distritoAduanero;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getRegimen()
/* 180:    */   {
/* 181:340 */     return this.regimen;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setRegimen(String regimen)
/* 185:    */   {
/* 186:350 */     this.regimen = regimen;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getCorrelativo()
/* 190:    */   {
/* 191:359 */     return this.correlativo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setCorrelativo(String correlativo)
/* 195:    */   {
/* 196:369 */     this.correlativo = correlativo;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getNumeroDocumentoTransporte()
/* 200:    */   {
/* 201:378 */     return this.numeroDocumentoTransporte;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setNumeroDocumentoTransporte(String numeroDocumentoTransporte)
/* 205:    */   {
/* 206:388 */     this.numeroDocumentoTransporte = numeroDocumentoTransporte;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Calendar getFechaVuelo()
/* 210:    */   {
/* 211:397 */     return this.fechaVuelo;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setFechaVuelo(Calendar fechaVuelo)
/* 215:    */   {
/* 216:407 */     this.fechaVuelo = fechaVuelo;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Long getIdAgenteComercial()
/* 220:    */   {
/* 221:416 */     return this.idAgenteComercial;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setIdAgenteComercial(Long idAgenteComercial)
/* 225:    */   {
/* 226:426 */     this.idAgenteComercial = idAgenteComercial;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public boolean isIndicadorDocumentoTributario()
/* 230:    */   {
/* 231:435 */     return this.indicadorDocumentoTributario;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setIndicadorDocumentoTributario(boolean indicadorDocumentoTributario)
/* 235:    */   {
/* 236:445 */     this.indicadorDocumentoTributario = indicadorDocumentoTributario;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String getNumeroPacking()
/* 240:    */   {
/* 241:449 */     return this.numeroPacking;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setNumeroPacking(String numeroPacking)
/* 245:    */   {
/* 246:453 */     this.numeroPacking = numeroPacking;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getGuiaMadre()
/* 250:    */   {
/* 251:457 */     return this.guiaMadre;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setGuiaMadre(String guiaMadre)
/* 255:    */   {
/* 256:461 */     this.guiaMadre = guiaMadre;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public BigDecimal getTotalPiezas()
/* 260:    */   {
/* 261:465 */     return this.totalPiezas;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setTotalPiezas(BigDecimal totalPiezas)
/* 265:    */   {
/* 266:469 */     this.totalPiezas = totalPiezas;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public BigDecimal getTotalTallos()
/* 270:    */   {
/* 271:473 */     return this.totalTallos;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setTotalTallos(BigDecimal totalTallos)
/* 275:    */   {
/* 276:477 */     this.totalTallos = totalTallos;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String getEmail()
/* 280:    */   {
/* 281:484 */     return this.email;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setEmail(String email)
/* 285:    */   {
/* 286:492 */     this.email = email;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Boolean getGenerarDocumentoElectronico()
/* 290:    */   {
/* 291:499 */     return this.generarDocumentoElectronico;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setGenerarDocumentoElectronico(Boolean generarDocumentoElectronico)
/* 295:    */   {
/* 296:507 */     this.generarDocumentoElectronico = generarDocumentoElectronico;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public String getDae()
/* 300:    */   {
/* 301:514 */     return this.dae;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setDae(String dae)
/* 305:    */   {
/* 306:522 */     this.dae = dae;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String getGuiaHija()
/* 310:    */   {
/* 311:529 */     return this.guiaHija;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setGuiaHija(String guiaHija)
/* 315:    */   {
/* 316:537 */     this.guiaHija = guiaHija;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String getAgenciaCarga()
/* 320:    */   {
/* 321:544 */     return this.agenciaCarga;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setAgenciaCarga(String agenciaCarga)
/* 325:    */   {
/* 326:552 */     this.agenciaCarga = agenciaCarga;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public BigDecimal getTotalCajas()
/* 330:    */   {
/* 331:556 */     return this.totalCajas;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setTotalCajas(BigDecimal totalCajas)
/* 335:    */   {
/* 336:560 */     this.totalCajas = totalCajas;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String getEmpresa()
/* 340:    */   {
/* 341:564 */     return this.empresa;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setEmpresa(String empresa)
/* 345:    */   {
/* 346:568 */     this.empresa = empresa;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String getSubempresa()
/* 350:    */   {
/* 351:572 */     return this.subempresa;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setSubempresa(String subempresa)
/* 355:    */   {
/* 356:576 */     this.subempresa = subempresa;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public String getInvoice()
/* 360:    */   {
/* 361:580 */     return this.invoice;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setInvoice(String invoice)
/* 365:    */   {
/* 366:584 */     this.invoice = invoice;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String getReferencia1()
/* 370:    */   {
/* 371:588 */     return this.referencia1;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setReferencia1(String referencia1)
/* 375:    */   {
/* 376:592 */     this.referencia1 = referencia1;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean isIndicador_automatico()
/* 380:    */   {
/* 381:596 */     return this.indicador_automatico;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setIndicador_automatico(boolean indicador_automatico)
/* 385:    */   {
/* 386:600 */     this.indicador_automatico = indicador_automatico;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public Long getIdZona()
/* 390:    */   {
/* 391:607 */     return this.idZona;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setIdZona(Long idZona)
/* 395:    */   {
/* 396:614 */     this.idZona = idZona;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Boolean getNoGenerarDocumentoElectronico()
/* 400:    */   {
/* 401:618 */     return this.noGenerarDocumentoElectronico;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setNoGenerarDocumentoElectronico(Boolean noGenerarDocumentoElectronico)
/* 405:    */   {
/* 406:622 */     this.noGenerarDocumentoElectronico = noGenerarDocumentoElectronico;
/* 407:    */   }
/* 408:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.FacturaClienteFlorWSEntity
 * JD-Core Version:    0.7.0.1
 */