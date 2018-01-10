/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.xml.bind.annotation.XmlElement;
/*   5:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlRootElement(name="infoFactura")
/*   9:    */ @XmlType(propOrder={"fechaEmision", "dirEstablecimiento", "contribuyenteEspecial", "obligadoContabilidad", "comercioExterior", "incoTermFactura", "lugarIncoTerm", "paisOrigen", "puertoEmbarque", "puertoDestino", "paisDestino", "paisAdquisicion", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "direccionComprador", "totalSinImpuestos", "totalSubsidio", "incoTermTotalSinImpuestos", "totalDescuento", "totalConImpuestosJaxb", "compensacionesJaxb", "propina", "fleteInternacional", "seguroInternacional", "gastosAduaneros", "gastosTransporteOtros", "importeTotal", "moneda", "pagos", "tipoIdentificacionSujetoRetenido", "razonSocialSujetoRetenido", "identificacionSujetoRetenido", "periodoFiscal"})
/*  10:    */ public class InfoFacturaJaxb
/*  11:    */ {
/*  12:    */   private String fechaEmision;
/*  13:    */   private String dirEstablecimiento;
/*  14:    */   private String contribuyenteEspecial;
/*  15:    */   private String obligadoContabilidad;
/*  16:    */   private String comercioExterior;
/*  17:    */   private String incoTermFactura;
/*  18:    */   private String lugarIncoTerm;
/*  19:    */   private String paisOrigen;
/*  20:    */   private String puertoEmbarque;
/*  21:    */   private String puertoDestino;
/*  22:    */   private String paisDestino;
/*  23:    */   private String paisAdquisicion;
/*  24:    */   private String tipoIdentificacionComprador;
/*  25:    */   private String razonSocialComprador;
/*  26:    */   private String identificacionComprador;
/*  27:    */   private String direccionComprador;
/*  28:    */   private BigDecimal totalSinImpuestos;
/*  29:    */   private BigDecimal totalSubsidio;
/*  30:    */   private String incoTermTotalSinImpuestos;
/*  31:    */   private BigDecimal totalDescuento;
/*  32:    */   private BigDecimal propina;
/*  33:    */   private BigDecimal fleteInternacional;
/*  34:    */   private BigDecimal seguroInternacional;
/*  35:    */   private BigDecimal gastosAduaneros;
/*  36:    */   private BigDecimal gastosTransporteOtros;
/*  37:    */   private BigDecimal importeTotal;
/*  38:    */   private String moneda;
/*  39:    */   private TotalConImpuestosJaxb totalConImpuestosJaxb;
/*  40:    */   private CompensacionesJaxb compensacionesJaxb;
/*  41:    */   private PagosJaxb pagos;
/*  42:    */   private String periodoFiscal;
/*  43:    */   private String tipoIdentificacionSujetoRetenido;
/*  44:    */   private String razonSocialSujetoRetenido;
/*  45:    */   private String identificacionSujetoRetenido;
/*  46:    */   
/*  47:    */   @XmlElement(name="totalConImpuestos")
/*  48:    */   public TotalConImpuestosJaxb getTotalConImpuestosJaxb()
/*  49:    */   {
/*  50: 75 */     return this.totalConImpuestosJaxb;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setTotalConImpuestosJaxb(TotalConImpuestosJaxb totalConImpuestosJaxb)
/*  54:    */   {
/*  55: 79 */     this.totalConImpuestosJaxb = totalConImpuestosJaxb;
/*  56:    */   }
/*  57:    */   
/*  58:    */   @XmlElement(name="compensaciones")
/*  59:    */   public CompensacionesJaxb getCompensacionesJaxb()
/*  60:    */   {
/*  61: 84 */     return this.compensacionesJaxb;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setCompensacionesJaxb(CompensacionesJaxb compensacionesJaxb)
/*  65:    */   {
/*  66: 88 */     this.compensacionesJaxb = compensacionesJaxb;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @XmlElement(name="fechaEmision")
/*  70:    */   public String getFechaEmision()
/*  71:    */   {
/*  72: 93 */     return this.fechaEmision;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setFechaEmision(String fechaEmision)
/*  76:    */   {
/*  77: 97 */     this.fechaEmision = fechaEmision;
/*  78:    */   }
/*  79:    */   
/*  80:    */   @XmlElement(name="dirEstablecimiento")
/*  81:    */   public String getDirEstablecimiento()
/*  82:    */   {
/*  83:102 */     return this.dirEstablecimiento;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setDirEstablecimiento(String dirEstablecimiento)
/*  87:    */   {
/*  88:106 */     this.dirEstablecimiento = dirEstablecimiento;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @XmlElement(name="contribuyenteEspecial")
/*  92:    */   public String getContribuyenteEspecial()
/*  93:    */   {
/*  94:111 */     return this.contribuyenteEspecial;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setContribuyenteEspecial(String contribuyenteEspecial)
/*  98:    */   {
/*  99:115 */     this.contribuyenteEspecial = contribuyenteEspecial;
/* 100:    */   }
/* 101:    */   
/* 102:    */   @XmlElement(name="obligadoContabilidad")
/* 103:    */   public String getObligadoContabilidad()
/* 104:    */   {
/* 105:120 */     return this.obligadoContabilidad;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setObligadoContabilidad(String obligadoContabilidad)
/* 109:    */   {
/* 110:124 */     this.obligadoContabilidad = obligadoContabilidad;
/* 111:    */   }
/* 112:    */   
/* 113:    */   @XmlElement(name="tipoIdentificacionComprador")
/* 114:    */   public String getTipoIdentificacionComprador()
/* 115:    */   {
/* 116:129 */     return this.tipoIdentificacionComprador;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setTipoIdentificacionComprador(String tipoIdentificacionComprador)
/* 120:    */   {
/* 121:133 */     this.tipoIdentificacionComprador = tipoIdentificacionComprador;
/* 122:    */   }
/* 123:    */   
/* 124:    */   @XmlElement(name="razonSocialComprador")
/* 125:    */   public String getRazonSocialComprador()
/* 126:    */   {
/* 127:138 */     return this.razonSocialComprador;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setRazonSocialComprador(String razonSocialComprador)
/* 131:    */   {
/* 132:142 */     this.razonSocialComprador = razonSocialComprador;
/* 133:    */   }
/* 134:    */   
/* 135:    */   @XmlElement(name="identificacionComprador")
/* 136:    */   public String getIdentificacionComprador()
/* 137:    */   {
/* 138:147 */     return this.identificacionComprador;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIdentificacionComprador(String identificacionComprador)
/* 142:    */   {
/* 143:151 */     this.identificacionComprador = identificacionComprador;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @XmlElement(name="totalSinImpuestos")
/* 147:    */   public BigDecimal getTotalSinImpuestos()
/* 148:    */   {
/* 149:156 */     return this.totalSinImpuestos;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setTotalSinImpuestos(BigDecimal totalSinImpuestos)
/* 153:    */   {
/* 154:160 */     this.totalSinImpuestos = totalSinImpuestos;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @XmlElement(name="totalDescuento")
/* 158:    */   public BigDecimal getTotalDescuento()
/* 159:    */   {
/* 160:165 */     return this.totalDescuento;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setTotalDescuento(BigDecimal totalDescuento)
/* 164:    */   {
/* 165:169 */     this.totalDescuento = totalDescuento;
/* 166:    */   }
/* 167:    */   
/* 168:    */   @XmlElement(name="propina")
/* 169:    */   public BigDecimal getPropina()
/* 170:    */   {
/* 171:174 */     return this.propina;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setPropina(BigDecimal propina)
/* 175:    */   {
/* 176:178 */     this.propina = propina;
/* 177:    */   }
/* 178:    */   
/* 179:    */   @XmlElement(name="importeTotal")
/* 180:    */   public BigDecimal getImporteTotal()
/* 181:    */   {
/* 182:183 */     return this.importeTotal;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setImporteTotal(BigDecimal importeTotal)
/* 186:    */   {
/* 187:187 */     this.importeTotal = importeTotal;
/* 188:    */   }
/* 189:    */   
/* 190:    */   @XmlElement(name="moneda")
/* 191:    */   public String getMoneda()
/* 192:    */   {
/* 193:192 */     return this.moneda;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setMoneda(String moneda)
/* 197:    */   {
/* 198:196 */     this.moneda = moneda;
/* 199:    */   }
/* 200:    */   
/* 201:    */   @XmlElement(name="periodoFiscal")
/* 202:    */   public String getPeriodoFiscal()
/* 203:    */   {
/* 204:201 */     return this.periodoFiscal;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setPeriodoFiscal(String periodoFiscal)
/* 208:    */   {
/* 209:205 */     this.periodoFiscal = periodoFiscal;
/* 210:    */   }
/* 211:    */   
/* 212:    */   @XmlElement(name="tipoIdentificacionSujetoRetenido")
/* 213:    */   public String getTipoIdentificacionSujetoRetenido()
/* 214:    */   {
/* 215:210 */     return this.tipoIdentificacionSujetoRetenido;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setTipoIdentificacionSujetoRetenido(String tipoIdentificacionSujetoRetenido)
/* 219:    */   {
/* 220:214 */     this.tipoIdentificacionSujetoRetenido = tipoIdentificacionSujetoRetenido;
/* 221:    */   }
/* 222:    */   
/* 223:    */   @XmlElement(name="razonSocialSujetoRetenido")
/* 224:    */   public String getRazonSocialSujetoRetenido()
/* 225:    */   {
/* 226:219 */     return this.razonSocialSujetoRetenido;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setRazonSocialSujetoRetenido(String razonSocialSujetoRetenido)
/* 230:    */   {
/* 231:223 */     this.razonSocialSujetoRetenido = razonSocialSujetoRetenido;
/* 232:    */   }
/* 233:    */   
/* 234:    */   @XmlElement(name="identificacionSujetoRetenido")
/* 235:    */   public String getIdentificacionSujetoRetenido()
/* 236:    */   {
/* 237:228 */     return this.identificacionSujetoRetenido;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setIdentificacionSujetoRetenido(String identificacionSujetoRetenido)
/* 241:    */   {
/* 242:232 */     this.identificacionSujetoRetenido = identificacionSujetoRetenido;
/* 243:    */   }
/* 244:    */   
/* 245:    */   @XmlElement(name="comercioExterior")
/* 246:    */   public String getComercioExterior()
/* 247:    */   {
/* 248:237 */     return this.comercioExterior;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setComercioExterior(String comercioExterior)
/* 252:    */   {
/* 253:241 */     this.comercioExterior = comercioExterior;
/* 254:    */   }
/* 255:    */   
/* 256:    */   @XmlElement(name="incoTermFactura")
/* 257:    */   public String getIncoTermFactura()
/* 258:    */   {
/* 259:246 */     return this.incoTermFactura;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setIncoTermFactura(String incoTermFactura)
/* 263:    */   {
/* 264:250 */     this.incoTermFactura = incoTermFactura;
/* 265:    */   }
/* 266:    */   
/* 267:    */   @XmlElement(name="lugarIncoTerm")
/* 268:    */   public String getLugarIncoTerm()
/* 269:    */   {
/* 270:255 */     return this.lugarIncoTerm;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setLugarIncoTerm(String lugarIncoTerm)
/* 274:    */   {
/* 275:259 */     this.lugarIncoTerm = lugarIncoTerm;
/* 276:    */   }
/* 277:    */   
/* 278:    */   @XmlElement(name="paisOrigen")
/* 279:    */   public String getPaisOrigen()
/* 280:    */   {
/* 281:264 */     return this.paisOrigen;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setPaisOrigen(String paisOrigen)
/* 285:    */   {
/* 286:268 */     this.paisOrigen = paisOrigen;
/* 287:    */   }
/* 288:    */   
/* 289:    */   @XmlElement(name="puertoEmbarque")
/* 290:    */   public String getPuertoEmbarque()
/* 291:    */   {
/* 292:273 */     return this.puertoEmbarque;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setPuertoEmbarque(String puertoEmbarque)
/* 296:    */   {
/* 297:277 */     this.puertoEmbarque = puertoEmbarque;
/* 298:    */   }
/* 299:    */   
/* 300:    */   @XmlElement(name="puertoDestino")
/* 301:    */   public String getPuertoDestino()
/* 302:    */   {
/* 303:282 */     return this.puertoDestino;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setPuertoDestino(String puertoDestino)
/* 307:    */   {
/* 308:286 */     this.puertoDestino = puertoDestino;
/* 309:    */   }
/* 310:    */   
/* 311:    */   @XmlElement(name="paisDestino")
/* 312:    */   public String getPaisDestino()
/* 313:    */   {
/* 314:291 */     return this.paisDestino;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setPaisDestino(String paisDestino)
/* 318:    */   {
/* 319:295 */     this.paisDestino = paisDestino;
/* 320:    */   }
/* 321:    */   
/* 322:    */   @XmlElement(name="paisAdquisicion")
/* 323:    */   public String getPaisAdquisicion()
/* 324:    */   {
/* 325:300 */     return this.paisAdquisicion;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setPaisAdquisicion(String paisAdquisicion)
/* 329:    */   {
/* 330:304 */     this.paisAdquisicion = paisAdquisicion;
/* 331:    */   }
/* 332:    */   
/* 333:    */   @XmlElement(name="direccionComprador")
/* 334:    */   public String getDireccionComprador()
/* 335:    */   {
/* 336:309 */     return this.direccionComprador;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setDireccionComprador(String direccionComprador)
/* 340:    */   {
/* 341:313 */     this.direccionComprador = direccionComprador;
/* 342:    */   }
/* 343:    */   
/* 344:    */   @XmlElement(name="totalSubsidio")
/* 345:    */   public BigDecimal getTotalSubsidio()
/* 346:    */   {
/* 347:318 */     return this.totalSubsidio;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setTotalSubsidio(BigDecimal totalSubsidio)
/* 351:    */   {
/* 352:322 */     this.totalSubsidio = totalSubsidio;
/* 353:    */   }
/* 354:    */   
/* 355:    */   @XmlElement(name="incoTermTotalSinImpuestos")
/* 356:    */   public String getIncoTermTotalSinImpuestos()
/* 357:    */   {
/* 358:327 */     return this.incoTermTotalSinImpuestos;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void setIncoTermTotalSinImpuestos(String incoTermTotalSinImpuestos)
/* 362:    */   {
/* 363:331 */     this.incoTermTotalSinImpuestos = incoTermTotalSinImpuestos;
/* 364:    */   }
/* 365:    */   
/* 366:    */   @XmlElement(name="fleteInternacional")
/* 367:    */   public BigDecimal getFleteInternacional()
/* 368:    */   {
/* 369:336 */     return this.fleteInternacional;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setFleteInternacional(BigDecimal fleteInternacional)
/* 373:    */   {
/* 374:340 */     this.fleteInternacional = fleteInternacional;
/* 375:    */   }
/* 376:    */   
/* 377:    */   @XmlElement(name="seguroInternacional")
/* 378:    */   public BigDecimal getSeguroInternacional()
/* 379:    */   {
/* 380:345 */     return this.seguroInternacional;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setSeguroInternacional(BigDecimal seguroInternacional)
/* 384:    */   {
/* 385:349 */     this.seguroInternacional = seguroInternacional;
/* 386:    */   }
/* 387:    */   
/* 388:    */   @XmlElement(name="gastosAduaneros")
/* 389:    */   public BigDecimal getGastosAduaneros()
/* 390:    */   {
/* 391:354 */     return this.gastosAduaneros;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setGastosAduaneros(BigDecimal gastosAduaneros)
/* 395:    */   {
/* 396:358 */     this.gastosAduaneros = gastosAduaneros;
/* 397:    */   }
/* 398:    */   
/* 399:    */   @XmlElement(name="gastosTransporteOtros")
/* 400:    */   public BigDecimal getGastosTransporteOtros()
/* 401:    */   {
/* 402:363 */     return this.gastosTransporteOtros;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setGastosTransporteOtros(BigDecimal gastosTransporteOtros)
/* 406:    */   {
/* 407:367 */     this.gastosTransporteOtros = gastosTransporteOtros;
/* 408:    */   }
/* 409:    */   
/* 410:    */   @XmlElement(name="pagos")
/* 411:    */   public PagosJaxb getPagos()
/* 412:    */   {
/* 413:372 */     return this.pagos;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setPagos(PagosJaxb pagos)
/* 417:    */   {
/* 418:376 */     this.pagos = pagos;
/* 419:    */   }
/* 420:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.InfoFacturaJaxb
 * JD-Core Version:    0.7.0.1
 */