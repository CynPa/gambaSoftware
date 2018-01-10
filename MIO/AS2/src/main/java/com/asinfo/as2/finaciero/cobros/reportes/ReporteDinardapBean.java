/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Subempresa;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  11:    */ import com.asinfo.as2.enumeraciones.Genero;
/*  12:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  13:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  14:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.util.RutaArchivo;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  19:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  20:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*  21:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.math.RoundingMode;
/*  25:    */ import java.text.SimpleDateFormat;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.Collections;
/*  28:    */ import java.util.Comparator;
/*  29:    */ import java.util.Date;
/*  30:    */ import java.util.List;
/*  31:    */ import java.util.Map;
/*  32:    */ import javax.annotation.PostConstruct;
/*  33:    */ import javax.ejb.EJB;
/*  34:    */ import javax.faces.bean.ManagedBean;
/*  35:    */ import javax.faces.bean.ViewScoped;
/*  36:    */ import javax.faces.model.SelectItem;
/*  37:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  38:    */ import net.sf.jasperreports.engine.JRException;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.event.SelectEvent;
/*  41:    */ import org.primefaces.model.StreamedContent;
/*  42:    */ 
/*  43:    */ @ManagedBean
/*  44:    */ @ViewScoped
/*  45:    */ public class ReporteDinardapBean
/*  46:    */   extends AbstractBaseReportBean
/*  47:    */ {
/*  48:    */   private static final long serialVersionUID = 1L;
/*  49: 69 */   private final String COMPILE_FILE_NAME = "reporteDinardapDetallado";
/*  50:    */   @EJB
/*  51:    */   private ServicioEmpresa servicioEmpresa;
/*  52:    */   @EJB
/*  53:    */   private ServicioReporteVenta servicioReporteVenta;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioRecaudador servicioRecaudador;
/*  56:    */   @EJB
/*  57:    */   private ServicioUsuario servicioUsuario;
/*  58:    */   @EJB
/*  59:    */   private ServicioSucursal servicioSucursal;
/*  60:    */   private int mesHasta;
/*  61:    */   private int annio;
/*  62:    */   private Date fechaHasta;
/*  63:    */   private Date fechaDesde;
/*  64:    */   private String codigoDinarpad;
/*  65: 93 */   private int diaMinimo = 1;
/*  66: 94 */   private BigDecimal montoMinimo = new BigDecimal(50);
/*  67:    */   private StreamedContent file;
/*  68:    */   private static final String TIPO_CONTENIDO = "application/txt";
/*  69:    */   private boolean indicadorResumen;
/*  70:    */   private Empresa empresa;
/*  71:    */   private Subempresa subempresa;
/*  72:    */   private EntidadUsuario agenteComercial;
/*  73:    */   private Sucursal sucursal;
/*  74:    */   private List<SelectItem> listaMes;
/*  75:    */   private List<Empresa> listaClientes;
/*  76:    */   private List<Subempresa> listaSubempresa;
/*  77:    */   private List<EntidadUsuario> listaAgenteComercial;
/*  78:    */   
/*  79:    */   @PostConstruct
/*  80:    */   public void init()
/*  81:    */   {
/*  82:116 */     this.annio = FuncionesUtiles.obtenerAnioActual();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<SelectItem> getListaMes()
/*  86:    */   {
/*  87:120 */     if (this.listaMes == null)
/*  88:    */     {
/*  89:121 */       this.listaMes = new ArrayList();
/*  90:122 */       for (Mes t : Mes.values())
/*  91:    */       {
/*  92:123 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/*  93:124 */         this.listaMes.add(item);
/*  94:    */       }
/*  95:    */     }
/*  96:127 */     return this.listaMes;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setListaMes(List<SelectItem> listaMes)
/* 100:    */   {
/* 101:131 */     this.listaMes = listaMes;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getAnnio()
/* 105:    */   {
/* 106:135 */     return this.annio;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setAnnio(int annio)
/* 110:    */   {
/* 111:139 */     this.annio = annio;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getMesHasta()
/* 115:    */   {
/* 116:144 */     return this.mesHasta;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setMesHasta(int mesHasta)
/* 120:    */   {
/* 121:148 */     this.mesHasta = mesHasta;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getCodigoDinarpad()
/* 125:    */   {
/* 126:153 */     return this.codigoDinarpad;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setCodigoDinarpad(String codigoDinarpad)
/* 130:    */   {
/* 131:157 */     this.codigoDinarpad = codigoDinarpad;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public StreamedContent getFile()
/* 135:    */   {
/* 136:163 */     return this.file;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setFile(StreamedContent file)
/* 140:    */   {
/* 141:167 */     this.file = file;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected JRDataSource getJRDataSource()
/* 145:    */   {
/* 146:179 */     List listaDatosReporte = new ArrayList();
/* 147:180 */     JRDataSource ds = null;
/* 148:    */     try
/* 149:    */     {
/* 150:182 */       listaDatosReporte = this.servicioReporteVenta.getAnalisisDinarpad(getFechaHasta(), getFechaDesde(), getEmpresa().getIdEmpresa(), 0, AppUtil.getOrganizacion().getId(), getSubempresa().getId(), getAgenteComercial()
/* 151:183 */         .getIdUsuario(), getSucursal(), this.diaMinimo, this.montoMinimo);
/* 152:    */       
/* 153:185 */       String[] fields = { "codigoEmpresa", "nombreFiscal", "codigoTipoIdentificacion", "identificacion", "numeroFactura", "fechaFactura", "fechaVencimiento", "diasPlazoFactura", "agenteComercial", "empresaFinal", "f_total_factura", "f_saldo_factura", "fechaExigible", "fechaMinimaMora", "f_vencido120+", "f_vencido120", "f_vencido90", "f_vencido60", "f_vencido30", "f_por_vencer30", "f_por_vencer60", "f_por_vencer90", "f_por_vencer120", "f_por_vencer120+", "f_total", "codigoDocumento", "f_valorBloqueado", "f_valorCredito", "claseSujeto", "formaCancelacion", "fechaCancelacion", "cuotaCredito", "origenIngresos", "sexo", "estadoCivil", "provincia", "parroquia", "canton" };
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:190 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 159:    */     }
/* 160:    */     catch (ExcepcionAS2 e)
/* 161:    */     {
/* 162:192 */       LOG.info("Error " + e);
/* 163:193 */       e.printStackTrace();
/* 164:    */     }
/* 165:195 */     return ds;
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected Map<String, Object> getReportParameters()
/* 169:    */   {
/* 170:201 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 171:202 */     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 172:203 */     reportParameters.put("fechaDatos", sdf.format(getFechaHasta()));
/* 173:204 */     reportParameters.put("codigoDinarpad", getCodigoDinarpad());
/* 174:205 */     reportParameters.put("fechaHasta", getFechaHasta());
/* 175:    */     
/* 176:207 */     return reportParameters;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String execute()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:218 */       super.prepareReport();
/* 184:    */     }
/* 185:    */     catch (JRException e)
/* 186:    */     {
/* 187:220 */       LOG.info("Error JRException");
/* 188:221 */       e.printStackTrace();
/* 189:222 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 190:    */     }
/* 191:    */     catch (IOException e)
/* 192:    */     {
/* 193:224 */       LOG.info("Error IOException");
/* 194:225 */       e.printStackTrace();
/* 195:226 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 196:    */     }
/* 197:229 */     return null;
/* 198:    */   }
/* 199:    */   
/* 200:    */   protected String getCompileFileName()
/* 201:    */   {
/* 202:240 */     return "reporteDinardapDetallado";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void actualizarClienteListener(SelectEvent event)
/* 206:    */   {
/* 207:250 */     Empresa empresa = (Empresa)event.getObject();
/* 208:251 */     setEmpresa(empresa);
/* 209:252 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(empresa.getId(), false);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Empresa> getListaClientes()
/* 213:    */   {
/* 214:262 */     if (this.listaClientes == null) {
/* 215:263 */       this.listaClientes = this.servicioEmpresa.obtenerClientes();
/* 216:    */     }
/* 217:265 */     return this.listaClientes;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setListaClientes(List<Empresa> listaClientes)
/* 221:    */   {
/* 222:275 */     this.listaClientes = listaClientes;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Empresa getEmpresa()
/* 226:    */   {
/* 227:279 */     if (this.empresa == null) {
/* 228:280 */       this.empresa = new Empresa();
/* 229:    */     }
/* 230:282 */     return this.empresa;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setEmpresa(Empresa empresa)
/* 234:    */   {
/* 235:286 */     this.empresa = empresa;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 239:    */   {
/* 240:290 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean isIndicadorResumen()
/* 244:    */   {
/* 245:294 */     return this.indicadorResumen;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 249:    */   {
/* 250:298 */     this.indicadorResumen = indicadorResumen;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<Subempresa> getListaSubempresa()
/* 254:    */   {
/* 255:308 */     if (this.listaSubempresa == null) {
/* 256:309 */       this.listaSubempresa = new ArrayList();
/* 257:    */     }
/* 258:311 */     return this.listaSubempresa;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 262:    */   {
/* 263:321 */     this.listaSubempresa = listaSubempresa;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Subempresa getSubempresa()
/* 267:    */   {
/* 268:330 */     if (this.subempresa == null) {
/* 269:331 */       this.subempresa = new Subempresa();
/* 270:    */     }
/* 271:333 */     return this.subempresa;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setSubempresa(Subempresa subempresa)
/* 275:    */   {
/* 276:343 */     this.subempresa = subempresa;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<EntidadUsuario> getListaAgenteComercial()
/* 280:    */   {
/* 281:348 */     this.listaAgenteComercial = new ArrayList();
/* 282:349 */     this.listaAgenteComercial = this.servicioUsuario.getEntidadUsuario(AppUtil.getOrganizacion().getId(), true, AppUtil.getSucursal());
/* 283:    */     
/* 284:351 */     Collections.sort(this.listaAgenteComercial, new Comparator()
/* 285:    */     {
/* 286:    */       public int compare(EntidadUsuario o1, EntidadUsuario o2)
/* 287:    */       {
/* 288:355 */         return o1.getNombre2().compareTo(o2.getNombre2());
/* 289:    */       }
/* 290:359 */     });
/* 291:360 */     return this.listaAgenteComercial;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public EntidadUsuario getAgenteComercial()
/* 295:    */   {
/* 296:369 */     if (this.agenteComercial == null) {
/* 297:370 */       this.agenteComercial = new EntidadUsuario();
/* 298:    */     }
/* 299:372 */     return this.agenteComercial;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 303:    */   {
/* 304:382 */     this.agenteComercial = agenteComercial;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Date getFechaHasta()
/* 308:    */   {
/* 309:391 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(getAnnio(), getMesHasta());
/* 310:392 */     return this.fechaHasta;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setFechaHasta(Date fechaHasta)
/* 314:    */   {
/* 315:402 */     this.fechaHasta = fechaHasta;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Date getFechaDesde()
/* 319:    */   {
/* 320:411 */     Date finMes = FuncionesUtiles.getFechaFinMes(getAnnio(), getMesHasta());
/* 321:412 */     this.fechaDesde = FuncionesUtiles.getFechaInicioMes(finMes);
/* 322:413 */     return this.fechaDesde;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setFechaDesde(Date fechaDesde)
/* 326:    */   {
/* 327:423 */     this.fechaDesde = fechaDesde;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public Sucursal getSucursal()
/* 331:    */   {
/* 332:427 */     return this.sucursal;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setSucursal(Sucursal sucursal)
/* 336:    */   {
/* 337:431 */     this.sucursal = sucursal;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void generarArchivo()
/* 341:    */   {
/* 342:440 */     generarArchivoDinarpad();
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void generarArchivoDinarpad()
/* 346:    */   {
/* 347:445 */     List<Object[]> listaDatos = new ArrayList();
/* 348:446 */     int numeroColumnas = 0;
/* 349:    */     try
/* 350:    */     {
/* 351:449 */       listaDatos = this.servicioReporteVenta.getAnalisisDinarpad(getFechaHasta(), getFechaDesde(), getEmpresa().getIdEmpresa(), 0, AppUtil.getOrganizacion().getId(), getSubempresa().getId(), getAgenteComercial()
/* 352:450 */         .getIdUsuario(), getSucursal(), this.diaMinimo, this.montoMinimo);
/* 353:451 */       List<Object[]> lista = new ArrayList();
/* 354:    */       
/* 355:453 */       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/* 356:    */       
/* 357:455 */       String rutaArchivoTxt = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "Dinarpad");
/* 358:456 */       int dia = FuncionesUtiles.getDiaFecha(FuncionesUtiles.getFechaFinMes(getAnnio(), getMesHasta()));
/* 359:457 */       int mes = getMesHasta();
/* 360:458 */       int anno = getAnnio();
/* 361:459 */       String nombreArchivo = AppUtil.getOrganizacion().getIdentificacion() + dia + FuncionesUtiles.completarALaIzquierda('0', 2, String.valueOf(mes)) + anno + ".txt";
/* 362:461 */       for (Object[] obj : listaDatos)
/* 363:    */       {
/* 364:462 */         Object[] dato = new Object[1];
/* 365:463 */         Integer dias_moro = Integer.valueOf(0);
/* 366:464 */         Integer plazo_mora = Integer.valueOf(FuncionesUtiles.diferenciasDeFechas((Date)obj[5], (Date)obj[6]));
/* 367:465 */         Date d = (Date)obj[13];
/* 368:466 */         if (d != null) {
/* 369:467 */           dias_moro = Integer.valueOf(FuncionesUtiles.diferenciasDeFechas((Date)obj[13], getFechaHasta()));
/* 370:    */         }
/* 371:469 */         if (dias_moro.intValue() < 0) {
/* 372:470 */           dias_moro = Integer.valueOf(0);
/* 373:    */         }
/* 374:472 */         String Nat_Jur = "N";
/* 375:473 */         String sexo = null;
/* 376:474 */         String estado_civil = null;
/* 377:475 */         String fecha_excigible = null;
/* 378:476 */         String forma_cancelacion = " ";
/* 379:480 */         if (((Integer)obj[28]).intValue() == 1) {
/* 380:481 */           Nat_Jur = "J";
/* 381:    */         }
/* 382:484 */         if (obj[33] == null) {
/* 383:485 */           sexo = "";
/* 384:487 */         } else if (obj[33].equals(Genero.MASCULINO)) {
/* 385:488 */           sexo = "M";
/* 386:490 */         } else if (obj[33].equals(Genero.FEMENINO)) {
/* 387:491 */           sexo = "F";
/* 388:    */         }
/* 389:495 */         if (obj[34] == null) {
/* 390:496 */           estado_civil = "";
/* 391:    */         } else {
/* 392:498 */           estado_civil = ((String)obj[34]).substring(0, 1);
/* 393:    */         }
/* 394:502 */         if (obj[12] != null) {
/* 395:503 */           fecha_excigible = sdf.format((Date)obj[12]);
/* 396:    */         } else {
/* 397:506 */           fecha_excigible = sdf.format((Date)obj[6]);
/* 398:    */         }
/* 399:510 */         if (obj[29] == null) {
/* 400:511 */           forma_cancelacion = "";
/* 401:    */         } else {
/* 402:513 */           forma_cancelacion = ((String)obj[29]).substring(0, 1);
/* 403:    */         }
/* 404:517 */         BigDecimal monto_morosidad = BigDecimal.ZERO;
/* 405:518 */         if (FuncionesUtiles.diferenciasDeFechas((Date)obj[5], (Date)obj[6]) != 0) {
/* 406:520 */           monto_morosidad = ((BigDecimal)obj[14]).add((BigDecimal)obj[15]).add((BigDecimal)obj[16]).add((BigDecimal)obj[17]).add((BigDecimal)obj[18]).setScale(2, RoundingMode.HALF_UP);
/* 407:    */         } else {
/* 408:522 */           monto_morosidad.setScale(2, RoundingMode.HALF_UP);
/* 409:    */         }
/* 410:526 */         if ((obj[11] == null) || (((Integer)obj[28]).intValue() == 1)) {
/* 411:527 */           obj[11] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 412:    */         } else {
/* 413:529 */           obj[11] = "0.00";
/* 414:    */         }
/* 415:533 */         if ((obj[32] == null) || (((Integer)obj[28]).intValue() == 1)) {
/* 416:534 */           obj[32] = "";
/* 417:    */         }
/* 418:537 */         if (obj[36] == null) {
/* 419:538 */           obj[36] = "";
/* 420:    */         }
/* 421:542 */         if (obj[7] == null) {
/* 422:543 */           obj[7] = "1";
/* 423:    */         }
/* 424:546 */         if (obj[19] != null) {
/* 425:547 */           obj[19] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 426:    */         } else {
/* 427:549 */           obj[19] = "0.00";
/* 428:    */         }
/* 429:552 */         if (obj[20] != null) {
/* 430:553 */           obj[20] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 431:    */         } else {
/* 432:555 */           obj[20] = "0.00";
/* 433:    */         }
/* 434:559 */         if (obj[21] != null) {
/* 435:560 */           obj[21] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 436:    */         } else {
/* 437:562 */           obj[21] = "0.00";
/* 438:    */         }
/* 439:565 */         if (obj[22] != null) {
/* 440:566 */           obj[22] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 441:    */         } else {
/* 442:568 */           obj[22] = "0.00";
/* 443:    */         }
/* 444:571 */         if (obj[23] != null) {
/* 445:572 */           obj[23] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 446:    */         } else {
/* 447:574 */           obj[23] = "0.00";
/* 448:    */         }
/* 449:577 */         if (obj[18] != null) {
/* 450:578 */           obj[18] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 451:    */         } else {
/* 452:580 */           obj[18] = "0.00";
/* 453:    */         }
/* 454:583 */         if (obj[17] != null) {
/* 455:584 */           obj[17] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 456:    */         } else {
/* 457:586 */           obj[17] = "0.00";
/* 458:    */         }
/* 459:589 */         if (obj[16] != null) {
/* 460:590 */           obj[16] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 461:    */         } else {
/* 462:592 */           obj[16] = "0.00";
/* 463:    */         }
/* 464:595 */         if (obj[15] != null) {
/* 465:596 */           obj[15] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 466:    */         } else {
/* 467:598 */           obj[15] = "0.00";
/* 468:    */         }
/* 469:601 */         if (obj[14] != null) {
/* 470:602 */           obj[14] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 471:    */         } else {
/* 472:604 */           obj[14] = "0.00";
/* 473:    */         }
/* 474:607 */         if (obj[31] != null) {
/* 475:608 */           obj[31] = ((BigDecimal)obj[14]).setScale(2, RoundingMode.HALF_UP);
/* 476:    */         } else {
/* 477:610 */           obj[31] = "0.00";
/* 478:    */         }
/* 479:613 */         String fecha5 = obj[5] != null ? sdf.format((Date)obj[5]) : "";
/* 480:614 */         String fecha6 = obj[6] != null ? sdf.format((Date)obj[6]) : "";
/* 481:615 */         String fecha31 = obj[30] != null ? sdf.format((Date)obj[30]) : "";
/* 482:    */         
/* 483:617 */         dato[0] = 
/* 484:    */         
/* 485:    */ 
/* 486:620 */           (getCodigoDinarpad() + "|" + sdf.format(getFechaHasta()) + "|" + obj[2].toString().trim() + "|" + obj[3].toString().trim() + "|" + obj[1].toString().trim() + "|" + Nat_Jur.trim() + "|" + obj[35].toString().trim() + "|" + obj[37].toString().trim() + "|" + obj[36].toString().trim() + "|" + sexo.trim() + "|" + estado_civil + "|" + obj[32].toString().trim() + "|" + obj[4].toString().replaceAll("-", "").trim() + "|" + obj[10].toString().trim() + "|" + obj[11].toString().trim() + "|" + fecha5.trim() + "|" + fecha6.trim() + "|" + fecha_excigible.trim() + "|" + plazo_mora.toString().trim() + "|" + obj[7].toString().trim() + "|" + dias_moro.toString().trim() + "|" + monto_morosidad.toString().trim() + "|" + "0.00" + "|" + obj[19].toString().trim() + "|" + obj[20].toString().trim() + "|" + obj[21].toString().trim() + "|" + obj[22].toString().trim() + "|" + obj[23].toString().trim() + "|" + obj[15].toString().trim() + "|" + obj[16].toString().trim() + "|" + obj[17].toString().trim() + "|" + obj[18].toString().trim() + "|" + obj[14].toString().trim() + "|" + "0.00" + "|" + "0.00" + "|" + obj[31].toString().trim() + "|" + fecha31.trim() + "|" + forma_cancelacion.trim());
/* 487:    */         
/* 488:622 */         lista.add(dato);
/* 489:    */       }
/* 490:626 */       numeroColumnas = 1;
/* 491:    */       
/* 492:628 */       FuncionesUtiles.crearArchivoTxt(rutaArchivoTxt, nombreArchivo, lista, numeroColumnas, "");
/* 493:    */       
/* 494:630 */       this.file = FuncionesUtiles.descargarArchivo(rutaArchivoTxt + nombreArchivo, "application/txt", nombreArchivo);
/* 495:    */       
/* 496:    */ 
/* 497:633 */       LOG.info("Archivo reporte DINARPAD generado correctamente en la ruta" + rutaArchivoTxt + nombreArchivo);
/* 498:    */     }
/* 499:    */     catch (Exception e)
/* 500:    */     {
/* 501:636 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 502:637 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 503:    */     }
/* 504:    */   }
/* 505:    */   
/* 506:    */   public int getDiaMinimo()
/* 507:    */   {
/* 508:643 */     return this.diaMinimo;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setDiaMinimo(int diaMinimo)
/* 512:    */   {
/* 513:647 */     this.diaMinimo = diaMinimo;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public BigDecimal getMontoMinimo()
/* 517:    */   {
/* 518:651 */     return this.montoMinimo;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void setMontoMinimo(BigDecimal montoMinimo)
/* 522:    */   {
/* 523:655 */     this.montoMinimo = montoMinimo;
/* 524:    */   }
/* 525:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteDinardapBean
 * JD-Core Version:    0.7.0.1
 */