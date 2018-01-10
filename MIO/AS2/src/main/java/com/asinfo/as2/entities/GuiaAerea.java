/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.aerolineas.Cass;
/*   4:    */ import java.math.BigDecimal;
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
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="guia_aerea")
/*  20:    */ public class GuiaAerea
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="guiaAerea", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="guiaAerea")
/*  27:    */   @Column(name="id_guia_aerea", unique=true, nullable=false)
/*  28:    */   private int idGuiaAerea;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="record_id", nullable=true, length=50)
/*  34:    */   private String recordId;
/*  35:    */   @Column(name="vat_indicator", nullable=true, length=50)
/*  36:    */   private String vatIndicator;
/*  37:    */   @Column(name="agent_code", nullable=true, length=50)
/*  38:    */   private String agentCode;
/*  39:    */   @Column(name="airline_prefix", nullable=true)
/*  40:    */   private Integer airlinePrefix;
/*  41:    */   @Column(name="awb_serial_number", nullable=true)
/*  42:    */   private String awbSerialNumber;
/*  43:    */   @Column(name="filler", nullable=true, length=50)
/*  44:    */   private String filler;
/*  45:    */   @Column(name="origin", nullable=true, length=50)
/*  46:    */   private String origin;
/*  47:    */   @Column(name="branch_office_indicator", nullable=true, length=50)
/*  48:    */   private String branchOfficeIndicator;
/*  49:    */   @Column(name="destination", nullable=true, length=50)
/*  50:    */   private String destination;
/*  51:    */   @Column(name="date_awb_execution", nullable=true)
/*  52:    */   private Integer dateAwbExecution;
/*  53:    */   @Column(name="weight_indicator", nullable=true, length=50)
/*  54:    */   private String weightIndicator;
/*  55:    */   @Column(name="currency_code", nullable=true, length=50)
/*  56:    */   private String currencyCode;
/*  57:    */   @Column(name="commission", nullable=true)
/*  58: 77 */   private BigDecimal commission = BigDecimal.ZERO;
/*  59:    */   @Digits(integer=12, fraction=6)
/*  60:    */   @Column(name="discount")
/*  61: 80 */   private BigDecimal discount = BigDecimal.ZERO;
/*  62:    */   @Digits(integer=12, fraction=6)
/*  63:    */   @Column(name="rate_of_exchange")
/*  64:    */   private BigDecimal rateOfExchange;
/*  65:    */   @Column(name="reserved_space", nullable=true, length=50)
/*  66:    */   private String reservedSpace;
/*  67:    */   @Column(name="awb_as_invoice_indicator", nullable=true, length=50)
/*  68:    */   private String awbAsInvoiceIndicator;
/*  69:    */   @Column(name="awb_modular_number_check", nullable=true, length=50)
/*  70:    */   private String awbModularNumberCheck;
/*  71:    */   @Column(name="awb_use_indicator", nullable=true, length=50)
/*  72:    */   private String awbUseIndicator;
/*  73:    */   @Column(name="filler2", nullable=true, length=50)
/*  74:    */   private String filler2;
/*  75:    */   @Column(name="weight_gross", nullable=true)
/*  76:105 */   private Integer weightGross = Integer.valueOf(0);
/*  77:    */   @Digits(integer=12, fraction=6)
/*  78:    */   @Column(name="weight_charge_pp")
/*  79:    */   private BigDecimal weightChargePp;
/*  80:    */   @Column(name="valuation_charge_pp", nullable=true)
/*  81:    */   private Integer valuationChargePp;
/*  82:    */   @Digits(integer=12, fraction=6)
/*  83:    */   @Column(name="charges_due_carrier_pp")
/*  84:    */   private BigDecimal chargesDueCarrierPp;
/*  85:    */   @Column(name="charges_due_agent_pp", nullable=true)
/*  86:    */   private Integer chargesDueAgentPp;
/*  87:    */   @Digits(integer=12, fraction=6)
/*  88:    */   @Column(name="weight_charge_cc")
/*  89:    */   private BigDecimal weightChargeCc;
/*  90:    */   @Column(name="valuation_charge_cc", nullable=true)
/*  91:    */   private Integer valuationChargeCc;
/*  92:    */   @Digits(integer=12, fraction=6)
/*  93:    */   @Column(name="other_charges_due_carrier_cc")
/*  94:    */   private BigDecimal otherChargesDueCarrierCc;
/*  95:    */   @Digits(integer=12, fraction=6)
/*  96:    */   @Column(name="other_charges_due_agent_cc")
/*  97:    */   private BigDecimal otherChargesDueAgentCc;
/*  98:    */   @Column(name="commission_percentage", nullable=true)
/*  99:    */   private Integer commissionPercentage;
/* 100:    */   @Column(name="commission_indicator", nullable=true, length=50)
/* 101:    */   private String commissionIndicator;
/* 102:    */   @Column(name="date_awb_acceptance", nullable=true)
/* 103:    */   private Integer dateAwbAcceptance;
/* 104:    */   @Column(name="agents_reference_data", nullable=true, length=50)
/* 105:    */   private String agentsReferenceData;
/* 106:    */   @Column(name="tax_due_airline", nullable=true)
/* 107:    */   private Integer taxDueAirline;
/* 108:    */   @Column(name="tax_due_agent", nullable=true)
/* 109:    */   private Integer taxDueAgent;
/* 110:    */   @Column(name="tax_due_airline_indicator", nullable=true, length=50)
/* 111:    */   private String taxDueAirlineIndicator;
/* 112:    */   @Column(name="discount_indicato", nullable=true, length=50)
/* 113:    */   private String discountIndicato;
/* 114:    */   @Column(name="late_indicator", nullable=true)
/* 115:    */   private Integer lateIndicator;
/* 116:    */   @Column(name="filler3", nullable=true, length=50)
/* 117:    */   private String filler3;
/* 118:    */   @Column(name="filler4", nullable=true, length=50)
/* 119:    */   private String filler4;
/* 120:    */   @Column(name="day", nullable=true)
/* 121:    */   private Integer day;
/* 122:    */   @Column(name="sequence_number", nullable=true)
/* 123:    */   private Integer sequenceNumber;
/* 124:    */   @Column(name="reporting_indicator", nullable=true, length=50)
/* 125:    */   private String reportingIndicator;
/* 126:    */   @Column(name="nora_sales_period", nullable=true)
/* 127:    */   private Integer noraSalesPeriod;
/* 128:    */   @Column(name="awb_number_modular_check", nullable=true, length=50)
/* 129:    */   private String awbNumberModularCheck;
/* 130:    */   @Column(name="cca_dcm_number", nullable=true, length=50)
/* 131:    */   private String ccaDcmNumber;
/* 132:    */   @Column(name="pp_cc_indicator1", nullable=true, length=50)
/* 133:    */   private String ppCcIndicator1;
/* 134:    */   @Digits(integer=12, fraction=6)
/* 135:    */   @Column(name="weight_charge")
/* 136:    */   private BigDecimal weightCharge;
/* 137:    */   @Column(name="pp_cc_indicator2", nullable=true, length=50)
/* 138:    */   private String ppCcIndicator2;
/* 139:    */   @Column(name="valuation_charge", nullable=true)
/* 140:    */   private Integer valuationCharge;
/* 141:    */   @Column(name="pp_cc_indicator3", nullable=true, length=50)
/* 142:    */   private String ppCcIndicator3;
/* 143:    */   @Column(name="taxes", nullable=true)
/* 144:    */   private Integer taxes;
/* 145:    */   @Column(name="pp_cc_indicator4", nullable=true, length=50)
/* 146:    */   private String ppCcIndicator4;
/* 147:    */   @Digits(integer=12, fraction=6)
/* 148:    */   @Column(name="charges_due_agent")
/* 149:    */   private BigDecimal chargesDueAgent;
/* 150:    */   @Column(name="pp_cc_indicator5", nullable=true, length=50)
/* 151:    */   private String ppCcIndicator5;
/* 152:    */   @Digits(integer=12, fraction=6)
/* 153:    */   @Column(name="charges_due_carrier")
/* 154:    */   private BigDecimal chargesDueCarrier;
/* 155:    */   @Column(name="vat_on_awb_charges", nullable=true)
/* 156:    */   private Integer vatOnAwbCharges;
/* 157:    */   @Column(name="vat_on_commission", nullable=true)
/* 158:    */   private Integer vatOnCommission;
/* 159:    */   @Column(name="discount_indicator", nullable=true)
/* 160:    */   private Integer discountIndicator;
/* 161:    */   @Column(name="weight", nullable=true)
/* 162:    */   private Integer weight;
/* 163:    */   @Column(name="reason_for_adjustment", nullable=true, length=50)
/* 164:    */   private String reasonForAdjustment;
/* 165:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 166:    */   @JoinColumn(name="id_cass", nullable=true)
/* 167:    */   private Cass cass;
/* 168:    */   @Transient
/* 169:245 */   private BigDecimal totalWgtCharge = BigDecimal.ZERO;
/* 170:    */   @Transient
/* 171:247 */   private BigDecimal totalDueCarrier = BigDecimal.ZERO;
/* 172:    */   @Transient
/* 173:249 */   private BigDecimal totalWgtChargeCollect = BigDecimal.ZERO;
/* 174:    */   @Transient
/* 175:251 */   private BigDecimal totalOtherChargesDueAgentCc = BigDecimal.ZERO;
/* 176:    */   @Transient
/* 177:253 */   private BigDecimal totalDiscount = BigDecimal.ZERO;
/* 178:    */   @Transient
/* 179:255 */   private BigDecimal totalCommission = BigDecimal.ZERO;
/* 180:    */   @Transient
/* 181:257 */   private BigDecimal totalNetSales = BigDecimal.ZERO;
/* 182:    */   @Transient
/* 183:259 */   private BigDecimal totalTaxCom = BigDecimal.ZERO;
/* 184:    */   @Transient
/* 185:261 */   private BigDecimal totalIvaAgt3080 = BigDecimal.ZERO;
/* 186:    */   @Transient
/* 187:263 */   private BigDecimal totalIvaRet7020 = BigDecimal.ZERO;
/* 188:    */   @Transient
/* 189:265 */   private BigDecimal totalIsrl = BigDecimal.ZERO;
/* 190:    */   @Transient
/* 191:267 */   private BigDecimal totalAmount = BigDecimal.ZERO;
/* 192:    */   @Transient
/* 193:283 */   private BigDecimal totalIVARetenido70 = BigDecimal.ZERO;
/* 194:    */   @Transient
/* 195:285 */   private BigDecimal totalIVARetenido20 = BigDecimal.ZERO;
/* 196:    */   
/* 197:    */   public String getAwbAsInvoiceIndicator()
/* 198:    */   {
/* 199:289 */     return this.awbAsInvoiceIndicator;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setAwbAsInvoiceIndicator(String awbAsInvoiceIndicator)
/* 203:    */   {
/* 204:293 */     this.awbAsInvoiceIndicator = awbAsInvoiceIndicator;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getAwbModularNumberCheck()
/* 208:    */   {
/* 209:297 */     return this.awbModularNumberCheck;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setAwbModularNumberCheck(String awbModularNumberCheck)
/* 213:    */   {
/* 214:301 */     this.awbModularNumberCheck = awbModularNumberCheck;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getAwbUseIndicator()
/* 218:    */   {
/* 219:305 */     return this.awbUseIndicator;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setAwbUseIndicator(String awbUseIndicator)
/* 223:    */   {
/* 224:309 */     this.awbUseIndicator = awbUseIndicator;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getFiller2()
/* 228:    */   {
/* 229:313 */     return this.filler2;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setFiller2(String filler2)
/* 233:    */   {
/* 234:317 */     this.filler2 = filler2;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Integer getWeightGross()
/* 238:    */   {
/* 239:321 */     return this.weightGross;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setWeightGross(Integer weightGross)
/* 243:    */   {
/* 244:325 */     this.weightGross = weightGross;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public BigDecimal getWeightChargePp()
/* 248:    */   {
/* 249:329 */     return this.weightChargePp;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setWeightChargePp(BigDecimal weightChargePp)
/* 253:    */   {
/* 254:333 */     this.weightChargePp = weightChargePp;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Integer getValuationChargePp()
/* 258:    */   {
/* 259:337 */     return this.valuationChargePp;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setValuationChargePp(Integer valuationChargePp)
/* 263:    */   {
/* 264:341 */     this.valuationChargePp = valuationChargePp;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public BigDecimal getChargesDueCarrierPp()
/* 268:    */   {
/* 269:345 */     return this.chargesDueCarrierPp;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setChargesDueCarrierPp(BigDecimal chargesDueCarrierPp)
/* 273:    */   {
/* 274:349 */     this.chargesDueCarrierPp = chargesDueCarrierPp;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Integer getChargesDueAgentPp()
/* 278:    */   {
/* 279:353 */     return this.chargesDueAgentPp;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setChargesDueAgentPp(Integer chargesDueAgentPp)
/* 283:    */   {
/* 284:357 */     this.chargesDueAgentPp = chargesDueAgentPp;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public BigDecimal getWeightChargeCc()
/* 288:    */   {
/* 289:361 */     return this.weightChargeCc;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setWeightChargeCc(BigDecimal weightChargeCc)
/* 293:    */   {
/* 294:365 */     this.weightChargeCc = weightChargeCc;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Integer getValuationChargeCc()
/* 298:    */   {
/* 299:369 */     return this.valuationChargeCc;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setValuationChargeCc(Integer valuationChargeCc)
/* 303:    */   {
/* 304:373 */     this.valuationChargeCc = valuationChargeCc;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public BigDecimal getOtherChargesDueCarrierCc()
/* 308:    */   {
/* 309:377 */     return this.otherChargesDueCarrierCc;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setOtherChargesDueCarrierCc(BigDecimal otherChargesDueCarrierCc)
/* 313:    */   {
/* 314:381 */     this.otherChargesDueCarrierCc = otherChargesDueCarrierCc;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public BigDecimal getOtherChargesDueAgentCc()
/* 318:    */   {
/* 319:385 */     return this.otherChargesDueAgentCc;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setOtherChargesDueAgentCc(BigDecimal otherChargesDueAgentCc)
/* 323:    */   {
/* 324:389 */     this.otherChargesDueAgentCc = otherChargesDueAgentCc;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Integer getCommissionPercentage()
/* 328:    */   {
/* 329:393 */     return this.commissionPercentage;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setCommissionPercentage(Integer commissionPercentage)
/* 333:    */   {
/* 334:397 */     this.commissionPercentage = commissionPercentage;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public String getCommissionIndicator()
/* 338:    */   {
/* 339:401 */     return this.commissionIndicator;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setCommissionIndicator(String commissionIndicator)
/* 343:    */   {
/* 344:405 */     this.commissionIndicator = commissionIndicator;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public Integer getDateAwbAcceptance()
/* 348:    */   {
/* 349:409 */     return this.dateAwbAcceptance;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void setDateAwbAcceptance(Integer dateAwbAcceptance)
/* 353:    */   {
/* 354:413 */     this.dateAwbAcceptance = dateAwbAcceptance;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public String getAgentsReferenceData()
/* 358:    */   {
/* 359:417 */     return this.agentsReferenceData;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void setAgentsReferenceData(String agentsReferenceData)
/* 363:    */   {
/* 364:421 */     this.agentsReferenceData = agentsReferenceData;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public Integer getTaxDueAirline()
/* 368:    */   {
/* 369:425 */     return this.taxDueAirline;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setTaxDueAirline(Integer taxDueAirline)
/* 373:    */   {
/* 374:429 */     this.taxDueAirline = taxDueAirline;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public Integer getTaxDueAgent()
/* 378:    */   {
/* 379:433 */     return this.taxDueAgent;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void setTaxDueAgent(Integer taxDueAgent)
/* 383:    */   {
/* 384:437 */     this.taxDueAgent = taxDueAgent;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public String getTaxDueAirlineIndicator()
/* 388:    */   {
/* 389:441 */     return this.taxDueAirlineIndicator;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void setTaxDueAirlineIndicator(String taxDueAirlineIndicator)
/* 393:    */   {
/* 394:445 */     this.taxDueAirlineIndicator = taxDueAirlineIndicator;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public String getDiscountIndicato()
/* 398:    */   {
/* 399:449 */     return this.discountIndicato;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setDiscountIndicato(String discountIndicato)
/* 403:    */   {
/* 404:453 */     this.discountIndicato = discountIndicato;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public Integer getLateIndicator()
/* 408:    */   {
/* 409:457 */     return this.lateIndicator;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setLateIndicator(Integer lateIndicator)
/* 413:    */   {
/* 414:461 */     this.lateIndicator = lateIndicator;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public String getFiller3()
/* 418:    */   {
/* 419:465 */     return this.filler3;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setFiller3(String filler3)
/* 423:    */   {
/* 424:469 */     this.filler3 = filler3;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String getFiller4()
/* 428:    */   {
/* 429:473 */     return this.filler4;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setFiller4(String filler4)
/* 433:    */   {
/* 434:477 */     this.filler4 = filler4;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public Integer getDay()
/* 438:    */   {
/* 439:481 */     return this.day;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setDay(Integer day)
/* 443:    */   {
/* 444:485 */     this.day = day;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public Integer getSequenceNumber()
/* 448:    */   {
/* 449:489 */     return this.sequenceNumber;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setSequenceNumber(Integer sequenceNumber)
/* 453:    */   {
/* 454:493 */     this.sequenceNumber = sequenceNumber;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public String getReportingIndicator()
/* 458:    */   {
/* 459:497 */     return this.reportingIndicator;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setReportingIndicator(String reportingIndicator)
/* 463:    */   {
/* 464:501 */     this.reportingIndicator = reportingIndicator;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public Integer getNoraSalesPeriod()
/* 468:    */   {
/* 469:505 */     return this.noraSalesPeriod;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setNoraSalesPeriod(Integer noraSalesPeriod)
/* 473:    */   {
/* 474:509 */     this.noraSalesPeriod = noraSalesPeriod;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public String getAwbNumberModularCheck()
/* 478:    */   {
/* 479:513 */     return this.awbNumberModularCheck;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setAwbNumberModularCheck(String awbNumberModularCheck)
/* 483:    */   {
/* 484:517 */     this.awbNumberModularCheck = awbNumberModularCheck;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public String getCcaDcmNumber()
/* 488:    */   {
/* 489:521 */     return this.ccaDcmNumber;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setCcaDcmNumber(String ccaDcmNumber)
/* 493:    */   {
/* 494:525 */     this.ccaDcmNumber = ccaDcmNumber;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public String getPpCcIndicator1()
/* 498:    */   {
/* 499:529 */     return this.ppCcIndicator1;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setPpCcIndicator1(String ppCcIndicator1)
/* 503:    */   {
/* 504:533 */     this.ppCcIndicator1 = ppCcIndicator1;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public BigDecimal getWeightCharge()
/* 508:    */   {
/* 509:537 */     return this.weightCharge;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setWeightCharge(BigDecimal weightCharge)
/* 513:    */   {
/* 514:541 */     this.weightCharge = weightCharge;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public String getPpCcIndicator2()
/* 518:    */   {
/* 519:545 */     return this.ppCcIndicator2;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setPpCcIndicator2(String ppCcIndicator2)
/* 523:    */   {
/* 524:549 */     this.ppCcIndicator2 = ppCcIndicator2;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public Integer getValuationCharge()
/* 528:    */   {
/* 529:553 */     return this.valuationCharge;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void setValuationCharge(Integer valuationCharge)
/* 533:    */   {
/* 534:557 */     this.valuationCharge = valuationCharge;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public String getPpCcIndicator3()
/* 538:    */   {
/* 539:561 */     return this.ppCcIndicator3;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void setPpCcIndicator3(String ppCcIndicator3)
/* 543:    */   {
/* 544:565 */     this.ppCcIndicator3 = ppCcIndicator3;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public Integer getTaxes()
/* 548:    */   {
/* 549:569 */     return this.taxes;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setTaxes(Integer taxes)
/* 553:    */   {
/* 554:573 */     this.taxes = taxes;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public String getPpCcIndicator4()
/* 558:    */   {
/* 559:577 */     return this.ppCcIndicator4;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setPpCcIndicator4(String ppCcIndicator4)
/* 563:    */   {
/* 564:581 */     this.ppCcIndicator4 = ppCcIndicator4;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public BigDecimal getChargesDueAgent()
/* 568:    */   {
/* 569:585 */     return this.chargesDueAgent;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public void setChargesDueAgent(BigDecimal chargesDueAgent)
/* 573:    */   {
/* 574:589 */     this.chargesDueAgent = chargesDueAgent;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public String getPpCcIndicator5()
/* 578:    */   {
/* 579:593 */     return this.ppCcIndicator5;
/* 580:    */   }
/* 581:    */   
/* 582:    */   public void setPpCcIndicator5(String ppCcIndicator5)
/* 583:    */   {
/* 584:597 */     this.ppCcIndicator5 = ppCcIndicator5;
/* 585:    */   }
/* 586:    */   
/* 587:    */   public BigDecimal getChargesDueCarrier()
/* 588:    */   {
/* 589:601 */     return this.chargesDueCarrier;
/* 590:    */   }
/* 591:    */   
/* 592:    */   public void setChargesDueCarrier(BigDecimal chargesDueCarrier)
/* 593:    */   {
/* 594:605 */     this.chargesDueCarrier = chargesDueCarrier;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public Integer getVatOnAwbCharges()
/* 598:    */   {
/* 599:609 */     return this.vatOnAwbCharges;
/* 600:    */   }
/* 601:    */   
/* 602:    */   public void setVatOnAwbCharges(Integer vatOnAwbCharges)
/* 603:    */   {
/* 604:613 */     this.vatOnAwbCharges = vatOnAwbCharges;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public Integer getVatOnCommission()
/* 608:    */   {
/* 609:617 */     return this.vatOnCommission;
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void setVatOnCommission(Integer vatOnCommission)
/* 613:    */   {
/* 614:621 */     this.vatOnCommission = vatOnCommission;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public Integer getDiscountIndicator()
/* 618:    */   {
/* 619:625 */     return this.discountIndicator;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public void setDiscountIndicator(Integer discountIndicator)
/* 623:    */   {
/* 624:629 */     this.discountIndicator = discountIndicator;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public Integer getWeight()
/* 628:    */   {
/* 629:633 */     return this.weight;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public void setWeight(Integer weight)
/* 633:    */   {
/* 634:637 */     this.weight = weight;
/* 635:    */   }
/* 636:    */   
/* 637:    */   public String getReasonForAdjustment()
/* 638:    */   {
/* 639:641 */     return this.reasonForAdjustment;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public void setReasonForAdjustment(String reasonForAdjustment)
/* 643:    */   {
/* 644:645 */     this.reasonForAdjustment = reasonForAdjustment;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public BigDecimal getTotalAmount()
/* 648:    */   {
/* 649:649 */     return this.totalAmount;
/* 650:    */   }
/* 651:    */   
/* 652:    */   public void setTotalAmount(BigDecimal totalAmount)
/* 653:    */   {
/* 654:653 */     this.totalAmount = totalAmount;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public BigDecimal getTotalDiscount()
/* 658:    */   {
/* 659:657 */     return this.totalDiscount;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public void setTotalDiscount(BigDecimal totalDiscount)
/* 663:    */   {
/* 664:661 */     this.totalDiscount = totalDiscount;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public BigDecimal getTotalCommission()
/* 668:    */   {
/* 669:665 */     return this.totalCommission;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public void setTotalCommission(BigDecimal totalCommission)
/* 673:    */   {
/* 674:669 */     this.totalCommission = totalCommission;
/* 675:    */   }
/* 676:    */   
/* 677:    */   public BigDecimal getTotalIVARetenido70()
/* 678:    */   {
/* 679:673 */     return this.totalIVARetenido70;
/* 680:    */   }
/* 681:    */   
/* 682:    */   public void setTotalIVARetenido70(BigDecimal totalIVARetenido70)
/* 683:    */   {
/* 684:677 */     this.totalIVARetenido70 = totalIVARetenido70;
/* 685:    */   }
/* 686:    */   
/* 687:    */   public String getRecordId()
/* 688:    */   {
/* 689:681 */     return this.recordId;
/* 690:    */   }
/* 691:    */   
/* 692:    */   public void setRecordId(String recordId)
/* 693:    */   {
/* 694:685 */     this.recordId = recordId;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public String getVatIndicator()
/* 698:    */   {
/* 699:689 */     return this.vatIndicator;
/* 700:    */   }
/* 701:    */   
/* 702:    */   public void setVatIndicator(String vatIndicator)
/* 703:    */   {
/* 704:693 */     this.vatIndicator = vatIndicator;
/* 705:    */   }
/* 706:    */   
/* 707:    */   public String getAgentCode()
/* 708:    */   {
/* 709:699 */     return this.agentCode;
/* 710:    */   }
/* 711:    */   
/* 712:    */   public void setAgentCode(String agentCode)
/* 713:    */   {
/* 714:703 */     this.agentCode = agentCode;
/* 715:    */   }
/* 716:    */   
/* 717:    */   public Integer getAirlinePrefix()
/* 718:    */   {
/* 719:707 */     return this.airlinePrefix;
/* 720:    */   }
/* 721:    */   
/* 722:    */   public void setAirlinePrefix(Integer airlinePrefix)
/* 723:    */   {
/* 724:711 */     this.airlinePrefix = airlinePrefix;
/* 725:    */   }
/* 726:    */   
/* 727:    */   public String getAwbSerialNumber()
/* 728:    */   {
/* 729:717 */     return this.awbSerialNumber;
/* 730:    */   }
/* 731:    */   
/* 732:    */   public void setAwbSerialNumber(String awbSerialNumber)
/* 733:    */   {
/* 734:721 */     this.awbSerialNumber = awbSerialNumber;
/* 735:    */   }
/* 736:    */   
/* 737:    */   public String getFiller()
/* 738:    */   {
/* 739:725 */     return this.filler;
/* 740:    */   }
/* 741:    */   
/* 742:    */   public void setFiller(String filler)
/* 743:    */   {
/* 744:729 */     this.filler = filler;
/* 745:    */   }
/* 746:    */   
/* 747:    */   public String getOrigin()
/* 748:    */   {
/* 749:733 */     return this.origin;
/* 750:    */   }
/* 751:    */   
/* 752:    */   public void setOrigin(String origin)
/* 753:    */   {
/* 754:737 */     this.origin = origin;
/* 755:    */   }
/* 756:    */   
/* 757:    */   public String getBranchOfficeIndicator()
/* 758:    */   {
/* 759:741 */     return this.branchOfficeIndicator;
/* 760:    */   }
/* 761:    */   
/* 762:    */   public void setBranchOfficeIndicator(String branchOfficeIndicator)
/* 763:    */   {
/* 764:745 */     this.branchOfficeIndicator = branchOfficeIndicator;
/* 765:    */   }
/* 766:    */   
/* 767:    */   public String getDestination()
/* 768:    */   {
/* 769:749 */     return this.destination;
/* 770:    */   }
/* 771:    */   
/* 772:    */   public void setDestination(String destination)
/* 773:    */   {
/* 774:753 */     this.destination = destination;
/* 775:    */   }
/* 776:    */   
/* 777:    */   public Integer getDateAwbExecution()
/* 778:    */   {
/* 779:757 */     return this.dateAwbExecution;
/* 780:    */   }
/* 781:    */   
/* 782:    */   public void setDateAwbExecution(Integer dateAwbExecution)
/* 783:    */   {
/* 784:761 */     this.dateAwbExecution = dateAwbExecution;
/* 785:    */   }
/* 786:    */   
/* 787:    */   public String getWeightIndicator()
/* 788:    */   {
/* 789:765 */     return this.weightIndicator;
/* 790:    */   }
/* 791:    */   
/* 792:    */   public void setWeightIndicator(String weightIndicator)
/* 793:    */   {
/* 794:769 */     this.weightIndicator = weightIndicator;
/* 795:    */   }
/* 796:    */   
/* 797:    */   public String getCurrencyCode()
/* 798:    */   {
/* 799:773 */     return this.currencyCode;
/* 800:    */   }
/* 801:    */   
/* 802:    */   public void setCurrencyCode(String currencyCode)
/* 803:    */   {
/* 804:777 */     this.currencyCode = currencyCode;
/* 805:    */   }
/* 806:    */   
/* 807:    */   public BigDecimal getCommission()
/* 808:    */   {
/* 809:781 */     return this.commission;
/* 810:    */   }
/* 811:    */   
/* 812:    */   public void setCommission(BigDecimal commission)
/* 813:    */   {
/* 814:785 */     this.commission = commission;
/* 815:    */   }
/* 816:    */   
/* 817:    */   public BigDecimal getDiscount()
/* 818:    */   {
/* 819:789 */     return this.discount;
/* 820:    */   }
/* 821:    */   
/* 822:    */   public void setDiscount(BigDecimal discount)
/* 823:    */   {
/* 824:793 */     this.discount = discount;
/* 825:    */   }
/* 826:    */   
/* 827:    */   public BigDecimal getRateOfExchange()
/* 828:    */   {
/* 829:797 */     return this.rateOfExchange;
/* 830:    */   }
/* 831:    */   
/* 832:    */   public void setRateOfExchange(BigDecimal rateOfExchange)
/* 833:    */   {
/* 834:801 */     this.rateOfExchange = rateOfExchange;
/* 835:    */   }
/* 836:    */   
/* 837:    */   public String getReservedSpace()
/* 838:    */   {
/* 839:805 */     return this.reservedSpace;
/* 840:    */   }
/* 841:    */   
/* 842:    */   public void setReservedSpace(String reservedSpace)
/* 843:    */   {
/* 844:809 */     this.reservedSpace = reservedSpace;
/* 845:    */   }
/* 846:    */   
/* 847:    */   public int getId()
/* 848:    */   {
/* 849:814 */     return this.idGuiaAerea;
/* 850:    */   }
/* 851:    */   
/* 852:    */   public String toString()
/* 853:    */   {
/* 854:819 */     return this.recordId;
/* 855:    */   }
/* 856:    */   
/* 857:    */   public Cass getCass()
/* 858:    */   {
/* 859:823 */     return this.cass;
/* 860:    */   }
/* 861:    */   
/* 862:    */   public void setCass(Cass cass)
/* 863:    */   {
/* 864:827 */     this.cass = cass;
/* 865:    */   }
/* 866:    */   
/* 867:    */   public int getIdGuiaAerea()
/* 868:    */   {
/* 869:831 */     return this.idGuiaAerea;
/* 870:    */   }
/* 871:    */   
/* 872:    */   public void setIdGuiaAerea(int idGuiaAerea)
/* 873:    */   {
/* 874:835 */     this.idGuiaAerea = idGuiaAerea;
/* 875:    */   }
/* 876:    */   
/* 877:    */   public int getIdOrganizacion()
/* 878:    */   {
/* 879:839 */     return this.idOrganizacion;
/* 880:    */   }
/* 881:    */   
/* 882:    */   public void setIdOrganizacion(int idOrganizacion)
/* 883:    */   {
/* 884:843 */     this.idOrganizacion = idOrganizacion;
/* 885:    */   }
/* 886:    */   
/* 887:    */   public int getIdSucursal()
/* 888:    */   {
/* 889:847 */     return this.idSucursal;
/* 890:    */   }
/* 891:    */   
/* 892:    */   public void setIdSucursal(int idSucursal)
/* 893:    */   {
/* 894:851 */     this.idSucursal = idSucursal;
/* 895:    */   }
/* 896:    */   
/* 897:    */   public BigDecimal getTotalWgtCharge()
/* 898:    */   {
/* 899:855 */     return this.totalWgtCharge;
/* 900:    */   }
/* 901:    */   
/* 902:    */   public void setTotalWgtCharge(BigDecimal totalWgtCharge)
/* 903:    */   {
/* 904:859 */     this.totalWgtCharge = totalWgtCharge;
/* 905:    */   }
/* 906:    */   
/* 907:    */   public BigDecimal getTotalDueCarrier()
/* 908:    */   {
/* 909:863 */     return this.totalDueCarrier;
/* 910:    */   }
/* 911:    */   
/* 912:    */   public void setTotalDueCarrier(BigDecimal totalDueCarrier)
/* 913:    */   {
/* 914:867 */     this.totalDueCarrier = totalDueCarrier;
/* 915:    */   }
/* 916:    */   
/* 917:    */   public BigDecimal getTotalWgtChargeCollect()
/* 918:    */   {
/* 919:871 */     return this.totalWgtChargeCollect;
/* 920:    */   }
/* 921:    */   
/* 922:    */   public void setTotalWgtChargeCollect(BigDecimal totalWgtChargeCollect)
/* 923:    */   {
/* 924:875 */     this.totalWgtChargeCollect = totalWgtChargeCollect;
/* 925:    */   }
/* 926:    */   
/* 927:    */   public BigDecimal getTotalOtherChargesDueAgentCc()
/* 928:    */   {
/* 929:879 */     return this.totalOtherChargesDueAgentCc;
/* 930:    */   }
/* 931:    */   
/* 932:    */   public void setTotalOtherChargesDueAgentCc(BigDecimal totalOtherChargesDueAgentCc)
/* 933:    */   {
/* 934:883 */     this.totalOtherChargesDueAgentCc = totalOtherChargesDueAgentCc;
/* 935:    */   }
/* 936:    */   
/* 937:    */   public BigDecimal getTotalNetSales()
/* 938:    */   {
/* 939:887 */     return this.totalNetSales;
/* 940:    */   }
/* 941:    */   
/* 942:    */   public void setTotalNetSales(BigDecimal totalNetSales)
/* 943:    */   {
/* 944:891 */     this.totalNetSales = totalNetSales;
/* 945:    */   }
/* 946:    */   
/* 947:    */   public BigDecimal getTotalTaxCom()
/* 948:    */   {
/* 949:895 */     return this.totalTaxCom;
/* 950:    */   }
/* 951:    */   
/* 952:    */   public void setTotalTaxCom(BigDecimal totalTaxCom)
/* 953:    */   {
/* 954:899 */     this.totalTaxCom = totalTaxCom;
/* 955:    */   }
/* 956:    */   
/* 957:    */   public BigDecimal getTotalIvaAgt3080()
/* 958:    */   {
/* 959:903 */     return this.totalIvaAgt3080;
/* 960:    */   }
/* 961:    */   
/* 962:    */   public void setTotalIvaAgt3080(BigDecimal totalIvaAgt3080)
/* 963:    */   {
/* 964:907 */     this.totalIvaAgt3080 = totalIvaAgt3080;
/* 965:    */   }
/* 966:    */   
/* 967:    */   public BigDecimal getTotalIvaRet7020()
/* 968:    */   {
/* 969:911 */     return this.totalIvaRet7020;
/* 970:    */   }
/* 971:    */   
/* 972:    */   public void setTotalIvaRet7020(BigDecimal totalIvaRet7020)
/* 973:    */   {
/* 974:915 */     this.totalIvaRet7020 = totalIvaRet7020;
/* 975:    */   }
/* 976:    */   
/* 977:    */   public BigDecimal getTotalIsrl()
/* 978:    */   {
/* 979:919 */     return this.totalIsrl;
/* 980:    */   }
/* 981:    */   
/* 982:    */   public void setTotalIsrl(BigDecimal totalIsrl)
/* 983:    */   {
/* 984:923 */     this.totalIsrl = totalIsrl;
/* 985:    */   }
/* 986:    */   
/* 987:    */   public BigDecimal getTotalIVARetenido20()
/* 988:    */   {
/* 989:927 */     return this.totalIVARetenido20;
/* 990:    */   }
/* 991:    */   
/* 992:    */   public void setTotalIVARetenido20(BigDecimal totalIVARetenido20)
/* 993:    */   {
/* 994:931 */     this.totalIVARetenido20 = totalIVARetenido20;
/* 995:    */   }
/* 996:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GuiaAerea
 * JD-Core Version:    0.7.0.1
 */