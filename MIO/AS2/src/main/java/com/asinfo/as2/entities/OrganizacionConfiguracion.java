/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   4:    */ import com.asinfo.as2.utils.validacion.email.Emails;
/*   5:    */ import java.io.Serializable;
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
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ import org.hibernate.annotations.ColumnDefault;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="organizacion_configuracion")
/*  25:    */ public class OrganizacionConfiguracion
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -2438506313187908844L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="organizacion_configuracion", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="organizacion_configuracion")
/*  33:    */   @Column(name="id_organizacion_configuracion", unique=true, nullable=false)
/*  34:    */   private int idOrganizacionConfiguracion;
/*  35:    */   @OneToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_organizacion", nullable=true)
/*  37:    */   private Organizacion organizacion;
/*  38:    */   @Column(name="creacion_organizacion", nullable=true)
/*  39:    */   private String creacionOrganizacion;
/*  40:    */   @Enumerated(EnumType.STRING)
/*  41:    */   @Column(name="tipo_organizacion", nullable=true, length=50)
/*  42:    */   private TipoOrganizacion tipoOrganizacion;
/*  43:    */   @Column(name="inicio_serie", nullable=true)
/*  44:    */   private Integer inicioSerie;
/*  45:    */   @Column(name="nombre_dimension1", nullable=false)
/*  46:    */   @Size(max=50)
/*  47: 73 */   private String nombreDimension1 = "";
/*  48:    */   @Column(name="nombre_dimension2", nullable=false)
/*  49:    */   @Size(max=50)
/*  50: 77 */   private String nombreDimension2 = "";
/*  51:    */   @Column(name="nombre_dimension3", nullable=false)
/*  52:    */   @Size(max=50)
/*  53: 81 */   private String nombreDimension3 = "";
/*  54:    */   @Column(name="nombre_dimension4", nullable=false)
/*  55:    */   @Size(max=50)
/*  56: 85 */   private String nombreDimension4 = "";
/*  57:    */   @Column(name="nombre_dimension5", nullable=false)
/*  58:    */   @Size(max=50)
/*  59: 89 */   private String nombreDimension5 = "";
/*  60:    */   @Column(name="mascara_dimension1", nullable=false)
/*  61:    */   @Size(max=50)
/*  62: 93 */   private String mascaraDimension1 = "";
/*  63:    */   @Column(name="mascara_dimension2", nullable=false)
/*  64:    */   @Size(max=50)
/*  65: 97 */   private String mascaraDimension2 = "";
/*  66:    */   @Column(name="mascara_dimension3", nullable=false)
/*  67:    */   @Size(max=50)
/*  68:101 */   private String mascaraDimension3 = "";
/*  69:    */   @Column(name="mascara_dimension4", nullable=false)
/*  70:    */   @Size(max=50)
/*  71:105 */   private String mascaraDimension4 = "";
/*  72:    */   @Column(name="mascara_dimension5", nullable=false)
/*  73:    */   @Size(max=50)
/*  74:109 */   private String mascaraDimension5 = "";
/*  75:    */   @Column(name="indicador_uso_dimension1", nullable=false)
/*  76:    */   private boolean indicadorUsoDimension1;
/*  77:    */   @Column(name="indicador_uso_dimension2", nullable=false)
/*  78:    */   private boolean indicadorUsoDimension2;
/*  79:    */   @Column(name="indicador_uso_dimension3", nullable=false)
/*  80:    */   private boolean indicadorUsoDimension3;
/*  81:    */   @Column(name="indicador_uso_dimension4", nullable=false)
/*  82:    */   private boolean indicadorUsoDimension4;
/*  83:    */   @Column(name="indicador_uso_dimension5", nullable=false)
/*  84:    */   private boolean indicadorUsoDimension5;
/*  85:    */   @Column(name="indicador_autoimpresor", nullable=true)
/*  86:    */   private boolean indicadorAutoimpresor;
/*  87:    */   @Column(name="clase_contribuyente", nullable=true, length=100)
/*  88:    */   @Size(max=100)
/*  89:131 */   private String claseContribuyente = "";
/*  90:    */   @Column(name="indicador_obligado_contabilidad", nullable=true)
/*  91:    */   private boolean indicadorObligadoContabilidad;
/*  92:    */   @Column(name="numero_resolucion_contribuyente", nullable=true, length=50)
/*  93:    */   @Size(max=50)
/*  94:    */   private String numeroResolucionContribuyente;
/*  95:    */   @Column(name="numero_copias_documento_tributario", nullable=false)
/*  96:    */   private int numeroCopiasDocumentoTributario;
/*  97:    */   @Column(name="smtp_servidor")
/*  98:    */   private String smtpServidor;
/*  99:    */   @Column(name="smtp_puerto")
/* 100:    */   private String smtpPuerto;
/* 101:    */   @Column(name="smtp_requiere_autenticacion")
/* 102:    */   private Boolean smtpRequiereAutenticacion;
/* 103:    */   @Column(name="smtp_enviar_de")
/* 104:    */   private String smtpEnviarDe;
/* 105:    */   @Column(name="smtp_usuario")
/* 106:    */   private String smtpUsuario;
/* 107:    */   @Column(name="smtp_clave")
/* 108:    */   private String smtpClave;
/* 109:    */   @Column(name="autenticacion_SSL")
/* 110:    */   private Boolean autenticacionSSL;
/* 111:    */   @Column(name="pkcs12_password")
/* 112:    */   private String pkcs12Password;
/* 113:    */   @Column(name="mensaje_documentos_electronicos", nullable=true)
/* 114:    */   @Size(max=1000)
/* 115:170 */   private String mensajeDocumentosElectronicos = "";
/* 116:    */   @Column(name="indicador_manejo_peso", nullable=true)
/* 117:    */   private boolean indicadorManejoPeso;
/* 118:    */   @Emails
/* 119:    */   @Column(name="smtp_enviar_copia_a")
/* 120:    */   private String smtpEnviarCopiaA;
/* 121:    */   @Column(name="indicador_uso_presupuesto_dimension1", nullable=false)
/* 122:182 */   private boolean indicadorUsoPresupuestoDimension1 = false;
/* 123:    */   @Column(name="indicador_uso_presupuesto_dimension2", nullable=false)
/* 124:185 */   private boolean indicadorUsoPresupuestoDimension2 = false;
/* 125:    */   @Column(name="indicador_uso_presupuesto_dimension3", nullable=false)
/* 126:188 */   private boolean indicadorUsoPresupuestoDimension3 = false;
/* 127:    */   @Column(name="indicador_uso_presupuesto_dimension4", nullable=false)
/* 128:191 */   private boolean indicadorUsoPresupuestoDimension4 = false;
/* 129:    */   @Column(name="indicador_uso_presupuesto_dimension5", nullable=false)
/* 130:194 */   private boolean indicadorUsoPresupuestoDimension5 = false;
/* 131:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 132:    */   @JoinColumn(name="id_atributo_1", nullable=true)
/* 133:    */   private Atributo atributo1;
/* 134:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 135:    */   @JoinColumn(name="id_atributo_2", nullable=true)
/* 136:    */   private Atributo atributo2;
/* 137:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 138:    */   @JoinColumn(name="id_atributo_3", nullable=true)
/* 139:    */   private Atributo atributo3;
/* 140:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 141:    */   @JoinColumn(name="id_atributo_4", nullable=true)
/* 142:    */   private Atributo atributo4;
/* 143:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 144:    */   @JoinColumn(name="id_atributo_5", nullable=true)
/* 145:    */   private Atributo atributo5;
/* 146:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 147:    */   @JoinColumn(name="id_atributo_6", nullable=true)
/* 148:    */   private Atributo atributo6;
/* 149:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 150:    */   @JoinColumn(name="id_atributo_7", nullable=true)
/* 151:    */   private Atributo atributo7;
/* 152:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 153:    */   @JoinColumn(name="id_atributo_8", nullable=true)
/* 154:    */   private Atributo atributo8;
/* 155:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 156:    */   @JoinColumn(name="id_atributo_9", nullable=true)
/* 157:    */   private Atributo atributo9;
/* 158:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 159:    */   @JoinColumn(name="id_atributo_10", nullable=true)
/* 160:    */   private Atributo atributo10;
/* 161:    */   @NotNull
/* 162:    */   @Column(name="numero_atributos", nullable=false)
/* 163:    */   @ColumnDefault("0")
/* 164:241 */   private int numeroAtributos = 0;
/* 165:    */   
/* 166:    */   public int getId()
/* 167:    */   {
/* 168:257 */     return this.idOrganizacionConfiguracion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getIdOrganizacionConfiguracion()
/* 172:    */   {
/* 173:266 */     return this.idOrganizacionConfiguracion;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setIdOrganizacionConfiguracion(int idOrganizacionConfiguracion)
/* 177:    */   {
/* 178:276 */     this.idOrganizacionConfiguracion = idOrganizacionConfiguracion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Organizacion getOrganizacion()
/* 182:    */   {
/* 183:285 */     return this.organizacion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setOrganizacion(Organizacion organizacion)
/* 187:    */   {
/* 188:295 */     this.organizacion = organizacion;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getCreacionOrganizacion()
/* 192:    */   {
/* 193:304 */     return this.creacionOrganizacion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setCreacionOrganizacion(String creacionOrganizacion)
/* 197:    */   {
/* 198:314 */     this.creacionOrganizacion = creacionOrganizacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getNombreDimension1()
/* 202:    */   {
/* 203:323 */     return this.nombreDimension1;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setNombreDimension1(String nombreDimension1)
/* 207:    */   {
/* 208:333 */     this.nombreDimension1 = nombreDimension1;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getNombreDimension2()
/* 212:    */   {
/* 213:342 */     return this.nombreDimension2;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setNombreDimension2(String nombreDimension2)
/* 217:    */   {
/* 218:352 */     this.nombreDimension2 = nombreDimension2;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getNombreDimension3()
/* 222:    */   {
/* 223:361 */     return this.nombreDimension3;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setNombreDimension3(String nombreDimension3)
/* 227:    */   {
/* 228:371 */     this.nombreDimension3 = nombreDimension3;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getNombreDimension4()
/* 232:    */   {
/* 233:380 */     return this.nombreDimension4;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setNombreDimension4(String nombreDimension4)
/* 237:    */   {
/* 238:390 */     this.nombreDimension4 = nombreDimension4;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getNombreDimension5()
/* 242:    */   {
/* 243:399 */     return this.nombreDimension5;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setNombreDimension5(String nombreDimension5)
/* 247:    */   {
/* 248:409 */     this.nombreDimension5 = nombreDimension5;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getMascaraDimension1()
/* 252:    */   {
/* 253:418 */     return this.mascaraDimension1;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setMascaraDimension1(String mascaraDimension1)
/* 257:    */   {
/* 258:428 */     this.mascaraDimension1 = mascaraDimension1;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getMascaraDimension2()
/* 262:    */   {
/* 263:437 */     return this.mascaraDimension2;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setMascaraDimension2(String mascaraDimension2)
/* 267:    */   {
/* 268:447 */     this.mascaraDimension2 = mascaraDimension2;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getMascaraDimension3()
/* 272:    */   {
/* 273:456 */     return this.mascaraDimension3;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setMascaraDimension3(String mascaraDimension3)
/* 277:    */   {
/* 278:466 */     this.mascaraDimension3 = mascaraDimension3;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String getMascaraDimension4()
/* 282:    */   {
/* 283:475 */     return this.mascaraDimension4;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setMascaraDimension4(String mascaraDimension4)
/* 287:    */   {
/* 288:485 */     this.mascaraDimension4 = mascaraDimension4;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public String getMascaraDimension5()
/* 292:    */   {
/* 293:494 */     return this.mascaraDimension5;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setMascaraDimension5(String mascaraDimension5)
/* 297:    */   {
/* 298:504 */     this.mascaraDimension5 = mascaraDimension5;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public boolean isIndicadorUsoDimension1()
/* 302:    */   {
/* 303:513 */     return this.indicadorUsoDimension1;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setIndicadorUsoDimension1(boolean indicadorUsoDimension1)
/* 307:    */   {
/* 308:523 */     this.indicadorUsoDimension1 = indicadorUsoDimension1;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public boolean isIndicadorUsoDimension2()
/* 312:    */   {
/* 313:532 */     return this.indicadorUsoDimension2;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setIndicadorUsoDimension2(boolean indicadorUsoDimension2)
/* 317:    */   {
/* 318:542 */     this.indicadorUsoDimension2 = indicadorUsoDimension2;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean isIndicadorUsoDimension3()
/* 322:    */   {
/* 323:551 */     return this.indicadorUsoDimension3;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setIndicadorUsoDimension3(boolean indicadorUsoDimension3)
/* 327:    */   {
/* 328:561 */     this.indicadorUsoDimension3 = indicadorUsoDimension3;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public boolean isIndicadorUsoDimension4()
/* 332:    */   {
/* 333:570 */     return this.indicadorUsoDimension4;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setIndicadorUsoDimension4(boolean indicadorUsoDimension4)
/* 337:    */   {
/* 338:580 */     this.indicadorUsoDimension4 = indicadorUsoDimension4;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public boolean isIndicadorUsoDimension5()
/* 342:    */   {
/* 343:589 */     return this.indicadorUsoDimension5;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setIndicadorUsoDimension5(boolean indicadorUsoDimension5)
/* 347:    */   {
/* 348:599 */     this.indicadorUsoDimension5 = indicadorUsoDimension5;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public TipoOrganizacion getTipoOrganizacion()
/* 352:    */   {
/* 353:608 */     return this.tipoOrganizacion;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion)
/* 357:    */   {
/* 358:618 */     this.tipoOrganizacion = tipoOrganizacion;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public Integer getInicioSerie()
/* 362:    */   {
/* 363:627 */     return this.inicioSerie;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setInicioSerie(Integer inicioSerie)
/* 367:    */   {
/* 368:637 */     this.inicioSerie = inicioSerie;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public boolean isIndicadorAutoimpresor()
/* 372:    */   {
/* 373:641 */     return this.indicadorAutoimpresor;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setIndicadorAutoimpresor(boolean indicadorAutoimpresor)
/* 377:    */   {
/* 378:645 */     this.indicadorAutoimpresor = indicadorAutoimpresor;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String getClaseContribuyente()
/* 382:    */   {
/* 383:649 */     return this.claseContribuyente;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setClaseContribuyente(String claseContribuyente)
/* 387:    */   {
/* 388:653 */     this.claseContribuyente = claseContribuyente;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public String getNumeroResolucionContribuyente()
/* 392:    */   {
/* 393:657 */     return this.numeroResolucionContribuyente;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setNumeroResolucionContribuyente(String numeroResolucionContribuyente)
/* 397:    */   {
/* 398:661 */     this.numeroResolucionContribuyente = numeroResolucionContribuyente;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public int getNumeroCopiasDocumentoTributario()
/* 402:    */   {
/* 403:665 */     return this.numeroCopiasDocumentoTributario;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setNumeroCopiasDocumentoTributario(int numeroCopiasDocumentoTributario)
/* 407:    */   {
/* 408:669 */     this.numeroCopiasDocumentoTributario = numeroCopiasDocumentoTributario;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public boolean isIndicadorObligadoContabilidad()
/* 412:    */   {
/* 413:673 */     return this.indicadorObligadoContabilidad;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setIndicadorObligadoContabilidad(boolean indicadorObligadoContabilidad)
/* 417:    */   {
/* 418:677 */     this.indicadorObligadoContabilidad = indicadorObligadoContabilidad;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String getSmtpServidor()
/* 422:    */   {
/* 423:681 */     return this.smtpServidor;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setSmtpServidor(String smtpServidor)
/* 427:    */   {
/* 428:685 */     this.smtpServidor = smtpServidor;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public String getSmtpPuerto()
/* 432:    */   {
/* 433:689 */     return this.smtpPuerto;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setSmtpPuerto(String smtpPuerto)
/* 437:    */   {
/* 438:693 */     this.smtpPuerto = smtpPuerto;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public Boolean getSmtpRequiereAutenticacion()
/* 442:    */   {
/* 443:697 */     return Boolean.valueOf(this.smtpRequiereAutenticacion == null ? false : this.smtpRequiereAutenticacion.booleanValue());
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setSmtpRequiereAutenticacion(Boolean smtpRequiereAutenticacion)
/* 447:    */   {
/* 448:701 */     this.smtpRequiereAutenticacion = smtpRequiereAutenticacion;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public String getSmtpUsuario()
/* 452:    */   {
/* 453:705 */     return this.smtpUsuario;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setSmtpUsuario(String smtpUsuario)
/* 457:    */   {
/* 458:709 */     this.smtpUsuario = smtpUsuario;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public String getSmtpClave()
/* 462:    */   {
/* 463:713 */     return this.smtpClave;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setSmtpClave(String smtpClave)
/* 467:    */   {
/* 468:717 */     this.smtpClave = smtpClave;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public String getSmtpEnviarDe()
/* 472:    */   {
/* 473:721 */     return this.smtpEnviarDe;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setSmtpEnviarDe(String smtpEnviarDe)
/* 477:    */   {
/* 478:725 */     this.smtpEnviarDe = smtpEnviarDe;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public boolean isIndicadorManejoPeso()
/* 482:    */   {
/* 483:729 */     return this.indicadorManejoPeso;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setIndicadorManejoPeso(boolean indicadorManejoPeso)
/* 487:    */   {
/* 488:733 */     this.indicadorManejoPeso = indicadorManejoPeso;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public Boolean getAutenticacionSSL()
/* 492:    */   {
/* 493:740 */     return Boolean.valueOf(this.autenticacionSSL == null ? false : this.autenticacionSSL.booleanValue());
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setAutenticacionSSL(Boolean autenticacionSSL)
/* 497:    */   {
/* 498:748 */     this.autenticacionSSL = autenticacionSSL;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public String getPkcs12Password()
/* 502:    */   {
/* 503:752 */     return this.pkcs12Password;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setPkcs12Password(String pkcs12Password)
/* 507:    */   {
/* 508:756 */     this.pkcs12Password = pkcs12Password;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public String getMensajeDocumentosElectronicos()
/* 512:    */   {
/* 513:763 */     return this.mensajeDocumentosElectronicos;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setMensajeDocumentosElectronicos(String mensajeDocumentosElectronicos)
/* 517:    */   {
/* 518:771 */     this.mensajeDocumentosElectronicos = mensajeDocumentosElectronicos;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public String getSmtpEnviarCopiaA()
/* 522:    */   {
/* 523:775 */     return this.smtpEnviarCopiaA;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setSmtpEnviarCopiaA(String smtpEnviarCopiaA)
/* 527:    */   {
/* 528:779 */     this.smtpEnviarCopiaA = smtpEnviarCopiaA;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public boolean isIndicadorUsoPresupuestoDimension1()
/* 532:    */   {
/* 533:783 */     return this.indicadorUsoPresupuestoDimension1;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setIndicadorUsoPresupuestoDimension1(boolean indicadorUsoPresupuestoDimension1)
/* 537:    */   {
/* 538:787 */     this.indicadorUsoPresupuestoDimension1 = indicadorUsoPresupuestoDimension1;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public boolean isIndicadorUsoPresupuestoDimension2()
/* 542:    */   {
/* 543:791 */     return this.indicadorUsoPresupuestoDimension2;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setIndicadorUsoPresupuestoDimension2(boolean indicadorUsoPresupuestoDimension2)
/* 547:    */   {
/* 548:795 */     this.indicadorUsoPresupuestoDimension2 = indicadorUsoPresupuestoDimension2;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public boolean isIndicadorUsoPresupuestoDimension3()
/* 552:    */   {
/* 553:799 */     return this.indicadorUsoPresupuestoDimension3;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setIndicadorUsoPresupuestoDimension3(boolean indicadorUsoPresupuestoDimension3)
/* 557:    */   {
/* 558:803 */     this.indicadorUsoPresupuestoDimension3 = indicadorUsoPresupuestoDimension3;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public boolean isIndicadorUsoPresupuestoDimension4()
/* 562:    */   {
/* 563:807 */     return this.indicadorUsoPresupuestoDimension4;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void setIndicadorUsoPresupuestoDimension4(boolean indicadorUsoPresupuestoDimension4)
/* 567:    */   {
/* 568:811 */     this.indicadorUsoPresupuestoDimension4 = indicadorUsoPresupuestoDimension4;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public boolean isIndicadorUsoPresupuestoDimension5()
/* 572:    */   {
/* 573:815 */     return this.indicadorUsoPresupuestoDimension5;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void setIndicadorUsoPresupuestoDimension5(boolean indicadorUsoPresupuestoDimension5)
/* 577:    */   {
/* 578:819 */     this.indicadorUsoPresupuestoDimension5 = indicadorUsoPresupuestoDimension5;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public Atributo getAtributo1()
/* 582:    */   {
/* 583:823 */     return this.atributo1;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void setAtributo1(Atributo atributo1)
/* 587:    */   {
/* 588:827 */     this.atributo1 = atributo1;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public Atributo getAtributo2()
/* 592:    */   {
/* 593:831 */     return this.atributo2;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setAtributo2(Atributo atributo2)
/* 597:    */   {
/* 598:835 */     this.atributo2 = atributo2;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public Atributo getAtributo3()
/* 602:    */   {
/* 603:839 */     return this.atributo3;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setAtributo3(Atributo atributo3)
/* 607:    */   {
/* 608:843 */     this.atributo3 = atributo3;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public Atributo getAtributo4()
/* 612:    */   {
/* 613:847 */     return this.atributo4;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setAtributo4(Atributo atributo4)
/* 617:    */   {
/* 618:851 */     this.atributo4 = atributo4;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public Atributo getAtributo5()
/* 622:    */   {
/* 623:855 */     return this.atributo5;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setAtributo5(Atributo atributo5)
/* 627:    */   {
/* 628:859 */     this.atributo5 = atributo5;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public Atributo getAtributo6()
/* 632:    */   {
/* 633:863 */     return this.atributo6;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void setAtributo6(Atributo atributo6)
/* 637:    */   {
/* 638:867 */     this.atributo6 = atributo6;
/* 639:    */   }
/* 640:    */   
/* 641:    */   public Atributo getAtributo7()
/* 642:    */   {
/* 643:871 */     return this.atributo7;
/* 644:    */   }
/* 645:    */   
/* 646:    */   public void setAtributo7(Atributo atributo7)
/* 647:    */   {
/* 648:875 */     this.atributo7 = atributo7;
/* 649:    */   }
/* 650:    */   
/* 651:    */   public Atributo getAtributo8()
/* 652:    */   {
/* 653:879 */     return this.atributo8;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public void setAtributo8(Atributo atributo8)
/* 657:    */   {
/* 658:883 */     this.atributo8 = atributo8;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public Atributo getAtributo9()
/* 662:    */   {
/* 663:887 */     return this.atributo9;
/* 664:    */   }
/* 665:    */   
/* 666:    */   public void setAtributo9(Atributo atributo9)
/* 667:    */   {
/* 668:891 */     this.atributo9 = atributo9;
/* 669:    */   }
/* 670:    */   
/* 671:    */   public int getNumeroAtributos()
/* 672:    */   {
/* 673:895 */     return this.numeroAtributos;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public void setNumeroAtributos(int numeroAtributos)
/* 677:    */   {
/* 678:899 */     this.numeroAtributos = numeroAtributos;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public Atributo getAtributo10()
/* 682:    */   {
/* 683:903 */     return this.atributo10;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public void setAtributo10(Atributo atributo10)
/* 687:    */   {
/* 688:907 */     this.atributo10 = atributo10;
/* 689:    */   }
/* 690:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrganizacionConfiguracion
 * JD-Core Version:    0.7.0.1
 */