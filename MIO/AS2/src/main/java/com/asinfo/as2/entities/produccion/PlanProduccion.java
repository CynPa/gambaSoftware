/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.text.SimpleDateFormat;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Calendar;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.persistence.Column;
/*  14:    */ import javax.persistence.Entity;
/*  15:    */ import javax.persistence.EnumType;
/*  16:    */ import javax.persistence.Enumerated;
/*  17:    */ import javax.persistence.FetchType;
/*  18:    */ import javax.persistence.GeneratedValue;
/*  19:    */ import javax.persistence.GenerationType;
/*  20:    */ import javax.persistence.Id;
/*  21:    */ import javax.persistence.JoinColumn;
/*  22:    */ import javax.persistence.ManyToOne;
/*  23:    */ import javax.persistence.OneToMany;
/*  24:    */ import javax.persistence.Table;
/*  25:    */ import javax.persistence.TableGenerator;
/*  26:    */ import javax.persistence.Temporal;
/*  27:    */ import javax.persistence.TemporalType;
/*  28:    */ import javax.persistence.Transient;
/*  29:    */ import javax.validation.constraints.NotNull;
/*  30:    */ import javax.validation.constraints.Size;
/*  31:    */ import org.hibernate.annotations.ColumnDefault;
/*  32:    */ 
/*  33:    */ @Entity
/*  34:    */ @Table(name="plan_produccion")
/*  35:    */ public class PlanProduccion
/*  36:    */   extends EntidadBase
/*  37:    */   implements Serializable
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  40:    */   @Id
/*  41:    */   @TableGenerator(name="plan_produccion", initialValue=0, allocationSize=50)
/*  42:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_produccion")
/*  43:    */   @Column(name="id_plan_produccion")
/*  44:    */   private int idPlanProduccion;
/*  45:    */   @Column(name="id_organizacion")
/*  46:    */   private int idOrganizacion;
/*  47:    */   @Column(name="id_sucursal")
/*  48:    */   private int idSucursal;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_documento", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Documento documento;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_bodega_trabajo", nullable=true)
/*  55:    */   private Bodega bodegaTrabajo;
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   @Column(name="fecha_inicio", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private Date fechaInicio;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha_fin", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private Date fechaFin;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha_elaboracion", nullable=false)
/*  66:    */   @NotNull
/*  67: 92 */   private Date fechaElaboracion = new Date();
/*  68:    */   @Column(name="descripcion", length=200, nullable=true)
/*  69:    */   @Size(max=200)
/*  70:    */   private String descripcion;
/*  71:    */   @Column(name="indicador_agrupa_componentes", nullable=false)
/*  72:    */   private boolean indicadorAgrupaComponentes;
/*  73:    */   @Column(name="estado", nullable=false)
/*  74:    */   @Enumerated(EnumType.ORDINAL)
/*  75:104 */   private Estado estado = Estado.ELABORADO;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_plan_maestro_produccion", nullable=true)
/*  78:    */   private PlanMaestroProduccion planMaestroProduccion;
/*  79:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="planProduccion")
/*  80:112 */   private List<DetallePlanProduccion> listaDetallePlanProduccion = new ArrayList();
/*  81:    */   @Temporal(TemporalType.DATE)
/*  82:    */   @Column(name="ultima_fecha_generada_orden", nullable=true)
/*  83:    */   private Date ultimaFechaGeneradaOrden;
/*  84:    */   @Column(name="bodegas_trabajo", nullable=true, length=1000)
/*  85:    */   @ColumnDefault("''")
/*  86:    */   @NotNull
/*  87:    */   @Size(max=1000)
/*  88:119 */   private String bodegasTrabajo = "";
/*  89:    */   @Transient
/*  90:    */   private int semanaAnio;
/*  91:    */   @Transient
/*  92:    */   private Date fecha1;
/*  93:    */   @Transient
/*  94:    */   private Date fecha2;
/*  95:    */   @Transient
/*  96:    */   private Date fecha3;
/*  97:    */   @Transient
/*  98:    */   private Date fecha4;
/*  99:    */   @Transient
/* 100:    */   private Date fecha5;
/* 101:    */   @Transient
/* 102:    */   private Date fecha6;
/* 103:    */   @Transient
/* 104:    */   private Date fecha7;
/* 105:    */   @Transient
/* 106:    */   private boolean indicadorFecha1;
/* 107:    */   @Transient
/* 108:    */   private boolean indicadorFecha2;
/* 109:    */   @Transient
/* 110:    */   private boolean indicadorFecha3;
/* 111:    */   @Transient
/* 112:    */   private boolean indicadorFecha4;
/* 113:    */   @Transient
/* 114:    */   private boolean indicadorFecha5;
/* 115:    */   @Transient
/* 116:    */   private boolean indicadorFecha6;
/* 117:    */   @Transient
/* 118:    */   private boolean indicadorFecha7;
/* 119:    */   
/* 120:    */   public int getId()
/* 121:    */   {
/* 122:178 */     return this.idPlanProduccion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getIdPlanProduccion()
/* 126:    */   {
/* 127:182 */     return this.idPlanProduccion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setIdPlanProduccion(int idPlanProduccion)
/* 131:    */   {
/* 132:186 */     this.idPlanProduccion = idPlanProduccion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getIdOrganizacion()
/* 136:    */   {
/* 137:190 */     return this.idOrganizacion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setIdOrganizacion(int idOrganizacion)
/* 141:    */   {
/* 142:194 */     this.idOrganizacion = idOrganizacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdSucursal()
/* 146:    */   {
/* 147:198 */     return this.idSucursal;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdSucursal(int idSucursal)
/* 151:    */   {
/* 152:202 */     this.idSucursal = idSucursal;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Documento getDocumento()
/* 156:    */   {
/* 157:206 */     return this.documento;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDocumento(Documento documento)
/* 161:    */   {
/* 162:210 */     this.documento = documento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Bodega getBodegaTrabajo()
/* 166:    */   {
/* 167:214 */     return this.bodegaTrabajo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setBodegaTrabajo(Bodega bodegaTrabajo)
/* 171:    */   {
/* 172:218 */     this.bodegaTrabajo = bodegaTrabajo;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Date getFechaInicio()
/* 176:    */   {
/* 177:222 */     return this.fechaInicio;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setFechaInicio(Date fechaInicio)
/* 181:    */   {
/* 182:226 */     this.fechaInicio = fechaInicio;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Date getFechaFin()
/* 186:    */   {
/* 187:230 */     return this.fechaFin;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setFechaFin(Date fechaFin)
/* 191:    */   {
/* 192:234 */     this.fechaFin = fechaFin;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getDescripcion()
/* 196:    */   {
/* 197:238 */     return this.descripcion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setDescripcion(String descripcion)
/* 201:    */   {
/* 202:242 */     this.descripcion = descripcion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<DetallePlanProduccion> getListaDetallePlanProduccion()
/* 206:    */   {
/* 207:246 */     return this.listaDetallePlanProduccion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setListaDetallePlanProduccion(List<DetallePlanProduccion> listaDetallePlanProduccion)
/* 211:    */   {
/* 212:250 */     this.listaDetallePlanProduccion = listaDetallePlanProduccion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public PlanMaestroProduccion getPlanMaestroProduccion()
/* 216:    */   {
/* 217:254 */     return this.planMaestroProduccion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setPlanMaestroProduccion(PlanMaestroProduccion planMaestroProduccion)
/* 221:    */   {
/* 222:258 */     this.planMaestroProduccion = planMaestroProduccion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isIndicadorAgrupaComponentes()
/* 226:    */   {
/* 227:262 */     return this.indicadorAgrupaComponentes;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIndicadorAgrupaComponentes(boolean indicadorAgrupaComponentes)
/* 231:    */   {
/* 232:266 */     this.indicadorAgrupaComponentes = indicadorAgrupaComponentes;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Estado getEstado()
/* 236:    */   {
/* 237:270 */     return this.estado;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setEstado(Estado estado)
/* 241:    */   {
/* 242:274 */     this.estado = estado;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Date getFechaElaboracion()
/* 246:    */   {
/* 247:278 */     return this.fechaElaboracion;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setFechaElaboracion(Date fechaElaboracion)
/* 251:    */   {
/* 252:282 */     this.fechaElaboracion = fechaElaboracion;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public Date getUltimaFechaGeneradaOrden()
/* 256:    */   {
/* 257:286 */     return this.ultimaFechaGeneradaOrden;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setUltimaFechaGeneradaOrden(Date ultimaFechaGeneradaOrden)
/* 261:    */   {
/* 262:290 */     this.ultimaFechaGeneradaOrden = ultimaFechaGeneradaOrden;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String toString()
/* 266:    */   {
/* 267:295 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 268:296 */     if ((this.fechaInicio != null) && (this.fechaFin != null)) {
/* 269:297 */       return sdf.format(this.fechaInicio) + " - " + sdf.format(this.fechaFin);
/* 270:    */     }
/* 271:300 */     return getId() + "";
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int getSemanaAnio()
/* 275:    */   {
/* 276:304 */     if (getFechaInicio() != null)
/* 277:    */     {
/* 278:305 */       Calendar fecha = Calendar.getInstance();
/* 279:306 */       fecha.setTime(getFechaInicio());
/* 280:307 */       this.semanaAnio = fecha.get(3);
/* 281:    */     }
/* 282:309 */     return this.semanaAnio;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setSemanaAnio(int semanaAnio)
/* 286:    */   {
/* 287:313 */     this.semanaAnio = semanaAnio;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public Date getFecha1()
/* 291:    */   {
/* 292:317 */     return this.fecha1;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setFecha1(Date fecha1)
/* 296:    */   {
/* 297:321 */     this.fecha1 = fecha1;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Date getFecha2()
/* 301:    */   {
/* 302:325 */     return this.fecha2;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setFecha2(Date fecha2)
/* 306:    */   {
/* 307:329 */     this.fecha2 = fecha2;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public Date getFecha3()
/* 311:    */   {
/* 312:333 */     return this.fecha3;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setFecha3(Date fecha3)
/* 316:    */   {
/* 317:337 */     this.fecha3 = fecha3;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Date getFecha4()
/* 321:    */   {
/* 322:341 */     return this.fecha4;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setFecha4(Date fecha4)
/* 326:    */   {
/* 327:345 */     this.fecha4 = fecha4;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public Date getFecha5()
/* 331:    */   {
/* 332:349 */     return this.fecha5;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setFecha5(Date fecha5)
/* 336:    */   {
/* 337:353 */     this.fecha5 = fecha5;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Date getFecha6()
/* 341:    */   {
/* 342:357 */     return this.fecha6;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setFecha6(Date fecha6)
/* 346:    */   {
/* 347:361 */     this.fecha6 = fecha6;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public Date getFecha7()
/* 351:    */   {
/* 352:365 */     return this.fecha7;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setFecha7(Date fecha7)
/* 356:    */   {
/* 357:369 */     this.fecha7 = fecha7;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public boolean isIndicadorFecha1()
/* 361:    */   {
/* 362:373 */     return this.indicadorFecha1;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setIndicadorFecha1(boolean indicadorFecha1)
/* 366:    */   {
/* 367:377 */     this.indicadorFecha1 = indicadorFecha1;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public boolean isIndicadorFecha2()
/* 371:    */   {
/* 372:381 */     return this.indicadorFecha2;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setIndicadorFecha2(boolean indicadorFecha2)
/* 376:    */   {
/* 377:385 */     this.indicadorFecha2 = indicadorFecha2;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public boolean isIndicadorFecha3()
/* 381:    */   {
/* 382:389 */     return this.indicadorFecha3;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setIndicadorFecha3(boolean indicadorFecha3)
/* 386:    */   {
/* 387:393 */     this.indicadorFecha3 = indicadorFecha3;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public boolean isIndicadorFecha4()
/* 391:    */   {
/* 392:397 */     return this.indicadorFecha4;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setIndicadorFecha4(boolean indicadorFecha4)
/* 396:    */   {
/* 397:401 */     this.indicadorFecha4 = indicadorFecha4;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public boolean isIndicadorFecha5()
/* 401:    */   {
/* 402:405 */     return this.indicadorFecha5;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setIndicadorFecha5(boolean indicadorFecha5)
/* 406:    */   {
/* 407:409 */     this.indicadorFecha5 = indicadorFecha5;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public boolean isIndicadorFecha6()
/* 411:    */   {
/* 412:413 */     return this.indicadorFecha6;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void setIndicadorFecha6(boolean indicadorFecha6)
/* 416:    */   {
/* 417:417 */     this.indicadorFecha6 = indicadorFecha6;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public boolean isIndicadorFecha7()
/* 421:    */   {
/* 422:421 */     return this.indicadorFecha7;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setIndicadorFecha7(boolean indicadorFecha7)
/* 426:    */   {
/* 427:425 */     this.indicadorFecha7 = indicadorFecha7;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String getBodegasTrabajo()
/* 431:    */   {
/* 432:429 */     return this.bodegasTrabajo;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setBodegasTrabajo(String bodegasTrabajo)
/* 436:    */   {
/* 437:433 */     this.bodegasTrabajo = bodegasTrabajo;
/* 438:    */   }
/* 439:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.PlanProduccion
 * JD-Core Version:    0.7.0.1
 */