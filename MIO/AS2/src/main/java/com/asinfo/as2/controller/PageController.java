/*   1:    */ package com.asinfo.as2.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioEstadoProceso;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioMotivoAnulacion;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  10:    */ import com.asinfo.as2.entities.seguridad.LogAuditoria;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.Parentezco;
/*  14:    */ import com.asinfo.as2.enumeraciones.ProcesoAuditoriaEnum;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoEmpresaEnum;
/*  16:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  17:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*  18:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  21:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  24:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  25:    */ import com.asinfo.as2.utils.ServiceLocator;
/*  26:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  27:    */ import com.lowagie.text.BadElementException;
/*  28:    */ import com.lowagie.text.Document;
/*  29:    */ import com.lowagie.text.DocumentException;
/*  30:    */ import com.lowagie.text.Image;
/*  31:    */ import com.lowagie.text.PageSize;
/*  32:    */ import com.lowagie.text.Phrase;
/*  33:    */ import java.io.File;
/*  34:    */ import java.io.FileNotFoundException;
/*  35:    */ import java.io.IOException;
/*  36:    */ import java.io.InputStream;
/*  37:    */ import java.io.PrintStream;
/*  38:    */ import java.io.Serializable;
/*  39:    */ import java.util.ArrayList;
/*  40:    */ import java.util.Arrays;
/*  41:    */ import java.util.Date;
/*  42:    */ import java.util.HashMap;
/*  43:    */ import java.util.List;
/*  44:    */ import java.util.Locale;
/*  45:    */ import java.util.Map;
/*  46:    */ import java.util.StringTokenizer;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.faces.application.FacesMessage;
/*  49:    */ import javax.faces.application.FacesMessage.Severity;
/*  50:    */ import javax.faces.bean.ManagedProperty;
/*  51:    */ import javax.faces.component.UIViewRoot;
/*  52:    */ import javax.faces.context.ExternalContext;
/*  53:    */ import javax.faces.context.FacesContext;
/*  54:    */ import javax.faces.model.SelectItem;
/*  55:    */ import javax.servlet.ServletContext;
/*  56:    */ import javax.servlet.ServletOutputStream;
/*  57:    */ import javax.servlet.http.HttpServletRequest;
/*  58:    */ import javax.servlet.http.HttpServletResponse;
/*  59:    */ import org.apache.log4j.Logger;
/*  60:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  61:    */ import org.apache.poi.hssf.usermodel.HSSFCellStyle;
/*  62:    */ import org.apache.poi.hssf.usermodel.HSSFRow;
/*  63:    */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*  64:    */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*  65:    */ 
/*  66:    */ public abstract class PageController
/*  67:    */   implements Serializable
/*  68:    */ {
/*  69:    */   private static final long serialVersionUID = 1556543226020361596L;
/*  70: 86 */   protected static final Logger LOG = Logger.getLogger("com.asinfo.as2");
/*  71:    */   @EJB
/*  72:    */   private ServicioEstadoProceso servicioEstadoProceso;
/*  73:    */   @EJB
/*  74:    */   private ServicioMotivoAnulacion servicioMotivoAnulacion;
/*  75:    */   @EJB
/*  76:    */   private ServicioGenerico<LogAuditoria> servicioLogAuditoria;
/*  77:    */   @ManagedProperty("#{languageController}")
/*  78:    */   private LanguageController languageController;
/*  79:    */   private String tituloDocumento;
/*  80:101 */   private boolean exportarPaginaActual = true;
/*  81:    */   private SelectItem[] listaEstadoItem;
/*  82:105 */   private boolean estadoItemTodos = true;
/*  83:106 */   private boolean estadoItemAnulado = true;
/*  84:    */   private SelectItem[] listaParentezcoItem;
/*  85:    */   private SelectItem[] listaActivoItem;
/*  86:    */   private SelectItem[] listaDocumentoBaseItem;
/*  87:    */   private DocumentoBase documentoBase;
/*  88:    */   private List<SelectItem> listaTipoServicioCuentaBancaria;
/*  89:    */   private List<SelectItem> listaOperaciones;
/*  90:119 */   private boolean secuenciaEditable = true;
/*  91:    */   private SelectItem[] listaTipoClienteItem;
/*  92:    */   
/*  93:    */   public static String getURLHostApp()
/*  94:    */   {
/*  95:131 */     Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
/*  96:132 */     String URLActual = ((HttpServletRequest)request).getRequestURL().toString();
/*  97:133 */     String URLHostApp = URLActual.substring(0, URLActual.indexOf("/" + ServiceLocator.APP_NAME + "/") + ServiceLocator.APP_NAME.length() + 2);
/*  98:134 */     return URLHostApp;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected Locale getLocale()
/* 102:    */   {
/* 103:143 */     Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
/* 104:144 */     return locale;
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void addMessage(FacesMessage.Severity severityError, String id, String detail)
/* 108:    */   {
/* 109:156 */     if (detail != null) {
/* 110:157 */       for (String mensaje : detail.split("\n"))
/* 111:    */       {
/* 112:158 */         FacesMessage errorMessage = new FacesMessage();
/* 113:159 */         errorMessage.setSummary("");
/* 114:160 */         errorMessage.setDetail(" " + mensaje);
/* 115:161 */         errorMessage.setSeverity(severityError);
/* 116:162 */         FacesContext.getCurrentInstance().addMessage(id, errorMessage);
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected void addErrorMessage(String detail)
/* 122:    */   {
/* 123:174 */     addMessage(FacesMessage.SEVERITY_ERROR, null, detail);
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected void addErrorMessage(String id, String detail)
/* 127:    */   {
/* 128:185 */     addMessage(FacesMessage.SEVERITY_ERROR, id, detail);
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void addInfoMessage(String detail)
/* 132:    */   {
/* 133:195 */     addMessage(FacesMessage.SEVERITY_INFO, null, detail);
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected void addWarnMessage(String detail)
/* 137:    */   {
/* 138:204 */     addMessage(FacesMessage.SEVERITY_WARN, null, detail);
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected void addInfoMessage(String id, String detail)
/* 142:    */   {
/* 143:215 */     addMessage(FacesMessage.SEVERITY_INFO, id, detail);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getMessagesStyleClass()
/* 147:    */   {
/* 148:219 */     if (FacesContext.getCurrentInstance().getMaximumSeverity() != null)
/* 149:    */     {
/* 150:220 */       int maximumSeverityOrdinal = FacesContext.getCurrentInstance().getMaximumSeverity().getOrdinal();
/* 151:221 */       if (maximumSeverityOrdinal == FacesMessage.SEVERITY_ERROR.getOrdinal()) {
/* 152:222 */         return "errormessage";
/* 153:    */       }
/* 154:223 */       if (maximumSeverityOrdinal == FacesMessage.SEVERITY_INFO.getOrdinal()) {
/* 155:224 */         return "infomessage";
/* 156:    */       }
/* 157:225 */       if (maximumSeverityOrdinal == FacesMessage.SEVERITY_WARN.getOrdinal()) {
/* 158:226 */         return "warningmessage";
/* 159:    */       }
/* 160:    */     }
/* 161:229 */     return "messages-hidden";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void preProcessPDF(Object document)
/* 165:    */     throws IOException, DocumentException
/* 166:    */   {
/* 167:242 */     Document pdf = (Document)document;
/* 168:243 */     pdf.open();
/* 169:244 */     pdf.setPageSize(PageSize.A4);
/* 170:    */     
/* 171:246 */     pdf.add(Image.getInstance(getImage("logo.png")));
/* 172:247 */     Phrase phrase = new Phrase(getTituloDocumento());
/* 173:    */     
/* 174:249 */     pdf.add(phrase);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void postProcessXLS(Object document)
/* 178:    */   {
/* 179:259 */     HSSFWorkbook wb = (HSSFWorkbook)document;
/* 180:260 */     HSSFSheet sheet = wb.getSheetAt(0);
/* 181:261 */     HSSFRow header = sheet.getRow(0);
/* 182:    */     
/* 183:263 */     HSSFCellStyle cellStyle = wb.createCellStyle();
/* 184:264 */     cellStyle.setFillForegroundColor((short)54);
/* 185:265 */     cellStyle.setFillPattern((short)1);
/* 186:267 */     for (int i = 0; i < header.getPhysicalNumberOfCells(); i++)
/* 187:    */     {
/* 188:268 */       HSSFCell cell = header.getCell(i);
/* 189:269 */       sheet.autoSizeColumn(i);
/* 190:270 */       cell.setCellStyle(cellStyle);
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   private Image getImage(String imageName)
/* 195:    */     throws IOException, BadElementException
/* 196:    */   {
/* 197:283 */     Image image = Image.getInstance(getAbsolutePath(imageName));
/* 198:284 */     image.scalePercent(90.0F);
/* 199:285 */     return image;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String getIP()
/* 203:    */   {
/* 204:295 */     HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
/* 205:    */     
/* 206:297 */     return request.getRemoteAddr();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Map<String, String> agregarFiltroOrganizacion(Map<String, String> filters)
/* 210:    */   {
/* 211:305 */     if (filters == null) {
/* 212:306 */       filters = new HashMap();
/* 213:    */     }
/* 214:308 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 215:    */     
/* 216:310 */     return filters;
/* 217:    */   }
/* 218:    */   
/* 219:    */   private String getAbsolutePath(String imageName)
/* 220:    */   {
/* 221:320 */     ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
/* 222:321 */     StringBuilder logo = new StringBuilder().append(servletContext.getRealPath(""));
/* 223:322 */     logo.append(File.separator).append("resources");
/* 224:323 */     logo.append(File.separator).append("images");
/* 225:324 */     logo.append(File.separator).append(imageName);
/* 226:325 */     System.out.println("Path es: " + logo.toString());
/* 227:    */     
/* 228:327 */     return logo.toString();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public SelectItem[] getListaEstadoItem()
/* 232:    */   {
/* 233:336 */     if (this.listaEstadoItem == null)
/* 234:    */     {
/* 235:338 */       List<SelectItem> lista = new ArrayList();
/* 236:339 */       if (this.estadoItemTodos) {
/* 237:340 */         lista.add(new SelectItem("", ""));
/* 238:    */       }
/* 239:    */       List<Estado> listaEstados;
/* 240:    */       Estado[] arrayEstados;
/* 241:    */       Estado[] arrayEstados;
/* 242:344 */       if (this.documentoBase != null)
/* 243:    */       {
/* 244:345 */         listaEstados = this.servicioEstadoProceso.buscarPorDocumentoBase(this.documentoBase);
/* 245:346 */         arrayEstados = (Estado[])listaEstados.toArray(new Estado[listaEstados.size()]);
/* 246:    */       }
/* 247:    */       else
/* 248:    */       {
/* 249:349 */         arrayEstados = Estado.values();
/* 250:    */       }
/* 251:352 */       for (Estado t : arrayEstados) {
/* 252:353 */         if ((this.estadoItemAnulado) || (t != Estado.ANULADO))
/* 253:    */         {
/* 254:357 */           SelectItem item = new SelectItem(t, t.getNombre());
/* 255:358 */           lista.add(item);
/* 256:    */         }
/* 257:    */       }
/* 258:360 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 259:    */       
/* 260:362 */       Arrays.sort(this.listaEstadoItem, new SelectItemComparator());
/* 261:    */     }
/* 262:365 */     return this.listaEstadoItem;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public SelectItem[] getListaParentezcoItem()
/* 266:    */   {
/* 267:375 */     if (this.listaParentezcoItem == null)
/* 268:    */     {
/* 269:377 */       List<SelectItem> lista = new ArrayList();
/* 270:378 */       for (Parentezco t : Parentezco.values())
/* 271:    */       {
/* 272:379 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 273:380 */         lista.add(item);
/* 274:    */       }
/* 275:382 */       this.listaParentezcoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 276:    */     }
/* 277:384 */     Arrays.sort(this.listaParentezcoItem, new SelectItemComparator());
/* 278:385 */     return this.listaParentezcoItem;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public SelectItem[] getListaDocumentoBaseItem()
/* 282:    */   {
/* 283:394 */     if (this.listaDocumentoBaseItem == null)
/* 284:    */     {
/* 285:396 */       List<SelectItem> lista = new ArrayList();
/* 286:397 */       lista.add(new SelectItem("", ""));
/* 287:399 */       for (DocumentoBase t : DocumentoBase.values())
/* 288:    */       {
/* 289:400 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 290:401 */         lista.add(item);
/* 291:    */       }
/* 292:403 */       this.listaDocumentoBaseItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 293:    */     }
/* 294:406 */     Arrays.sort(this.listaDocumentoBaseItem, new SelectItemComparator());
/* 295:    */     
/* 296:408 */     return this.listaDocumentoBaseItem;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public int getNumeroFilas()
/* 300:    */   {
/* 301:417 */     return ParametrosSistema.getNumeroRegistrosVentana(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public String getNumeroFilasPorPagina()
/* 305:    */   {
/* 306:427 */     return "10,20,30,40,50,100,200,300,400,500,1000,2000,3000,4000,5000";
/* 307:    */   }
/* 308:    */   
/* 309:    */   public int getNumeroFilasReporte()
/* 310:    */   {
/* 311:436 */     return ParametrosSistema.getNumeroRegistrosVentanaReportes(AppUtil.getOrganizacion().getIdOrganizacion()).intValue();
/* 312:    */   }
/* 313:    */   
/* 314:    */   public int getNumeroColumnasPanelNuevo()
/* 315:    */   {
/* 316:446 */     return 4;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String getFormatoFecha()
/* 320:    */   {
/* 321:450 */     return ParametrosSistema.getFormatoFecha(AppUtil.getOrganizacion().getIdOrganizacion());
/* 322:    */   }
/* 323:    */   
/* 324:    */   public String getFormatoDinero()
/* 325:    */   {
/* 326:454 */     return ParametrosSistema.getFormatoDinero(AppUtil.getOrganizacion().getIdOrganizacion());
/* 327:    */   }
/* 328:    */   
/* 329:    */   public String getFormatoDineroCompras()
/* 330:    */   {
/* 331:458 */     String format1 = "$,###,";
/* 332:459 */     String format2 = "0.";
/* 333:460 */     Integer numeroDecimales = ParametrosSistema.getNumeroDecimalesPrecioCompra(AppUtil.getOrganizacion().getIdOrganizacion());
/* 334:461 */     for (int i = 0; i < numeroDecimales.intValue(); i++)
/* 335:    */     {
/* 336:462 */       format1 = format1 + "#";
/* 337:463 */       format2 = format2 + "0";
/* 338:    */     }
/* 339:465 */     return format1 + format2;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public String getTituloDocumento()
/* 343:    */   {
/* 344:474 */     return this.tituloDocumento;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setTituloDocumento(String tituloDocumento)
/* 348:    */   {
/* 349:484 */     this.tituloDocumento = tituloDocumento;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public boolean isExportarPaginaActual()
/* 353:    */   {
/* 354:493 */     return this.exportarPaginaActual;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setExportarPaginaActual(boolean exportarPaginaActual)
/* 358:    */   {
/* 359:503 */     this.exportarPaginaActual = exportarPaginaActual;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public LanguageController getLanguageController()
/* 363:    */   {
/* 364:512 */     return this.languageController;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setLanguageController(LanguageController languageController)
/* 368:    */   {
/* 369:522 */     this.languageController = languageController;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public SelectItem[] getListaActivoItem()
/* 373:    */   {
/* 374:532 */     if (this.listaActivoItem == null)
/* 375:    */     {
/* 376:534 */       List<SelectItem> lista = new ArrayList();
/* 377:    */       
/* 378:536 */       lista.add(new SelectItem("", ""));
/* 379:537 */       lista.add(new SelectItem("true", "true"));
/* 380:538 */       lista.add(new SelectItem("false", "false"));
/* 381:    */       
/* 382:540 */       this.listaActivoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 383:    */     }
/* 384:543 */     return this.listaActivoItem;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setListaActivoItem(SelectItem[] listaActivoItem)
/* 388:    */   {
/* 389:553 */     this.listaActivoItem = listaActivoItem;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public DocumentoBase getDocumentoBase()
/* 393:    */   {
/* 394:560 */     return this.documentoBase;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 398:    */   {
/* 399:568 */     this.documentoBase = documentoBase;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public String getNombreDimension1()
/* 403:    */   {
/* 404:572 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension1();
/* 405:    */   }
/* 406:    */   
/* 407:    */   public String getNombreDimension2()
/* 408:    */   {
/* 409:576 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension2();
/* 410:    */   }
/* 411:    */   
/* 412:    */   public String getNombreDimension3()
/* 413:    */   {
/* 414:580 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension3();
/* 415:    */   }
/* 416:    */   
/* 417:    */   public String getNombreDimension4()
/* 418:    */   {
/* 419:584 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension4();
/* 420:    */   }
/* 421:    */   
/* 422:    */   public String getNombreDimension5()
/* 423:    */   {
/* 424:588 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension5();
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String getMascaraDimension1()
/* 428:    */   {
/* 429:592 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getMascaraDimension1();
/* 430:    */   }
/* 431:    */   
/* 432:    */   public String getMascaraDimension2()
/* 433:    */   {
/* 434:596 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getMascaraDimension2();
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String getMascaraDimension3()
/* 438:    */   {
/* 439:600 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getMascaraDimension3();
/* 440:    */   }
/* 441:    */   
/* 442:    */   public String getMascaraDimension4()
/* 443:    */   {
/* 444:604 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getMascaraDimension4();
/* 445:    */   }
/* 446:    */   
/* 447:    */   public String getMascaraDimension5()
/* 448:    */   {
/* 449:608 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getMascaraDimension5();
/* 450:    */   }
/* 451:    */   
/* 452:    */   public List<SelectItem> getListaDimension()
/* 453:    */   {
/* 454:612 */     List<SelectItem> lista = new ArrayList();
/* 455:613 */     SelectItem si = null;
/* 456:614 */     if (!AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension1().equals(""))
/* 457:    */     {
/* 458:615 */       si = new SelectItem();
/* 459:616 */       si.setLabel(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension1());
/* 460:617 */       si.setValue("1");
/* 461:618 */       lista.add(si);
/* 462:    */     }
/* 463:620 */     if (!AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension2().equals(""))
/* 464:    */     {
/* 465:621 */       si = new SelectItem();
/* 466:622 */       si.setLabel(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension2());
/* 467:623 */       si.setValue("2");
/* 468:624 */       lista.add(si);
/* 469:    */     }
/* 470:626 */     if (!AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension3().equals(""))
/* 471:    */     {
/* 472:627 */       si = new SelectItem();
/* 473:628 */       si.setLabel(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension3());
/* 474:629 */       si.setValue("3");
/* 475:630 */       lista.add(si);
/* 476:    */     }
/* 477:632 */     if (!AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension4().equals(""))
/* 478:    */     {
/* 479:633 */       si = new SelectItem();
/* 480:634 */       si.setLabel(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension4());
/* 481:635 */       si.setValue("4");
/* 482:636 */       lista.add(si);
/* 483:    */     }
/* 484:638 */     if (!AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension5().equals(""))
/* 485:    */     {
/* 486:639 */       si = new SelectItem();
/* 487:640 */       si.setLabel(AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNombreDimension5());
/* 488:641 */       si.setValue("5");
/* 489:642 */       lista.add(si);
/* 490:    */     }
/* 491:644 */     return lista;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public List<SelectItem> getNivelDimension(String numeroDimension)
/* 495:    */   {
/* 496:648 */     String mascara = getMascara(numeroDimension);
/* 497:649 */     return getListaNivelDimensionContable(mascara);
/* 498:    */   }
/* 499:    */   
/* 500:    */   public String getMascara(String numeroDimension)
/* 501:    */   {
/* 502:659 */     String mascara = "";
/* 503:660 */     if (numeroDimension.trim().equals("1")) {
/* 504:661 */       mascara = getMascaraDimension1();
/* 505:662 */     } else if (numeroDimension.trim().equals("2")) {
/* 506:663 */       mascara = getMascaraDimension2();
/* 507:664 */     } else if (numeroDimension.trim().equals("3")) {
/* 508:665 */       mascara = getMascaraDimension3();
/* 509:666 */     } else if (numeroDimension.trim().equals("4")) {
/* 510:667 */       mascara = getMascaraDimension4();
/* 511:668 */     } else if (numeroDimension.trim().equals("5")) {
/* 512:669 */       mascara = getMascaraDimension5();
/* 513:    */     }
/* 514:671 */     return mascara;
/* 515:    */   }
/* 516:    */   
/* 517:    */   private List<SelectItem> getListaNivelDimensionContable(String mascaraDimension)
/* 518:    */   {
/* 519:675 */     List<SelectItem> listaNivelDimensionContable = new ArrayList();
/* 520:676 */     StringTokenizer tokens = new StringTokenizer(mascaraDimension, ".");
/* 521:677 */     String mascara = "";
/* 522:678 */     int nivel = 0;
/* 523:679 */     while (tokens.hasMoreTokens())
/* 524:    */     {
/* 525:680 */       nivel++;
/* 526:681 */       mascara = mascara + tokens.nextToken() + ".";
/* 527:682 */       SelectItem sl = new SelectItem(mascara, String.valueOf(nivel));
/* 528:683 */       listaNivelDimensionContable.add(sl);
/* 529:    */     }
/* 530:685 */     return listaNivelDimensionContable;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public List<DocumentoBase> getListaDocumentoBaseConfigurable()
/* 534:    */   {
/* 535:689 */     List<DocumentoBase> lista = new ArrayList();
/* 536:690 */     lista.add(DocumentoBase.RECEPCION_BODEGA);
/* 537:691 */     lista.add(DocumentoBase.FACTURA_PROVEEDOR);
/* 538:692 */     return lista;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public boolean isEstadoItemTodos()
/* 542:    */   {
/* 543:696 */     return this.estadoItemTodos;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setEstadoItemTodos(boolean estadoItemTodos)
/* 547:    */   {
/* 548:700 */     this.estadoItemTodos = estadoItemTodos;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public boolean isEstadoItemAnulado()
/* 552:    */   {
/* 553:704 */     return this.estadoItemAnulado;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setEstadoItemAnulado(boolean estadoItemAnulado)
/* 557:    */   {
/* 558:708 */     this.estadoItemAnulado = estadoItemAnulado;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public TipoOrganizacion getTipoOrganizacion()
/* 562:    */   {
/* 563:712 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().getTipoOrganizacion();
/* 564:    */   }
/* 565:    */   
/* 566:    */   public boolean isCosteoPorBodega()
/* 567:    */   {
/* 568:716 */     return ParametrosSistema.isCosteoPorBodega(AppUtil.getOrganizacion().getId()).booleanValue();
/* 569:    */   }
/* 570:    */   
/* 571:    */   public boolean isPermitirFechaInventario()
/* 572:    */   {
/* 573:720 */     return ParametrosSistema.isPermitirFechaInventario(AppUtil.getOrganizacion().getId()).booleanValue();
/* 574:    */   }
/* 575:    */   
/* 576:    */   public boolean isIndicadorAutoimpresor()
/* 577:    */   {
/* 578:724 */     return AppUtil.getOrganizacion().getOrganizacionConfiguracion().isIndicadorAutoimpresor();
/* 579:    */   }
/* 580:    */   
/* 581:    */   public List<SelectItem> getListaTipoServicioCuentaBancaria()
/* 582:    */   {
/* 583:728 */     if (this.listaTipoServicioCuentaBancaria == null)
/* 584:    */     {
/* 585:729 */       this.listaTipoServicioCuentaBancaria = new ArrayList();
/* 586:730 */       SelectItem a = new SelectItem(null, "Seleccione");
/* 587:731 */       this.listaTipoServicioCuentaBancaria.add(a);
/* 588:732 */       for (TipoServicioCuentaBancariaEnum tipo : TipoServicioCuentaBancariaEnum.values())
/* 589:    */       {
/* 590:733 */         SelectItem item = new SelectItem(tipo, tipo.getNombre());
/* 591:734 */         this.listaTipoServicioCuentaBancaria.add(item);
/* 592:    */       }
/* 593:    */     }
/* 594:737 */     return this.listaTipoServicioCuentaBancaria;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public List<SelectItem> getListaOperaciones()
/* 598:    */   {
/* 599:746 */     if (this.listaOperaciones == null)
/* 600:    */     {
/* 601:747 */       this.listaOperaciones = new ArrayList();
/* 602:748 */       this.listaOperaciones.add(new SelectItem("-1", "Egreso"));
/* 603:749 */       this.listaOperaciones.add(new SelectItem("1", "Ingreso"));
/* 604:    */     }
/* 605:751 */     return this.listaOperaciones;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public void setListaOperaciones(List<SelectItem> listaOperaciones)
/* 609:    */   {
/* 610:761 */     this.listaOperaciones = listaOperaciones;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public boolean isSecuenciaEditable()
/* 614:    */   {
/* 615:768 */     return this.secuenciaEditable;
/* 616:    */   }
/* 617:    */   
/* 618:    */   public void setSecuenciaEditable(boolean secuenciaEditable)
/* 619:    */   {
/* 620:776 */     this.secuenciaEditable = secuenciaEditable;
/* 621:    */   }
/* 622:    */   
/* 623:    */   public void descargarPlantilla()
/* 624:    */   {
/* 625:    */     try
/* 626:    */     {
/* 627:784 */       System.out.println("entrooo" + getRutaPlantilla() + " " + getNombrePlantilla());
/* 628:785 */       FacesContext ctx = FacesContext.getCurrentInstance();
/* 629:    */       
/* 630:787 */       InputStream fis = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(getRutaPlantilla());
/* 631:788 */       byte[] buffer = new byte[1000];
/* 632:789 */       int read = 0;
/* 633:791 */       if (!ctx.getResponseComplete())
/* 634:    */       {
/* 635:792 */         String contentType = "application/vnd.ms-excel";
/* 636:    */         
/* 637:794 */         HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
/* 638:795 */         response.setContentType(contentType);
/* 639:796 */         response.setHeader("Content-Disposition", "attachment;filename=\"" + getNombrePlantilla() + "\"");
/* 640:    */         
/* 641:798 */         ServletOutputStream out = response.getOutputStream();
/* 642:799 */         while ((read = fis.read(buffer)) != -1) {
/* 643:800 */           out.write(buffer, 0, read);
/* 644:    */         }
/* 645:802 */         out.flush();
/* 646:803 */         out.close();
/* 647:804 */         ctx.responseComplete();
/* 648:    */       }
/* 649:    */     }
/* 650:    */     catch (FileNotFoundException e)
/* 651:    */     {
/* 652:807 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 653:808 */       LOG.error("ERROR AL DESCARGAR PLANTILLA", e);
/* 654:809 */       e.printStackTrace();
/* 655:    */     }
/* 656:    */     catch (IOException e)
/* 657:    */     {
/* 658:811 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 659:812 */       LOG.error("ERROR AL DESCARGAR PLANTILLA", e);
/* 660:813 */       e.printStackTrace();
/* 661:    */     }
/* 662:    */   }
/* 663:    */   
/* 664:    */   public void processDownload()
/* 665:    */   {
/* 666:    */     try
/* 667:    */     {
/* 668:823 */       String nombreArchivo = getNombreArchivo();
/* 669:824 */       if (nombreArchivo == null) {
/* 670:825 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 671:    */       } else {
/* 672:827 */         FuncionesUtiles.downloadArchivo(getDirectorioDescarga(), nombreArchivo);
/* 673:    */       }
/* 674:    */     }
/* 675:    */     catch (Exception e)
/* 676:    */     {
/* 677:831 */       e.printStackTrace();
/* 678:832 */       addErrorMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 679:    */     }
/* 680:    */   }
/* 681:    */   
/* 682:    */   public String getDirectorioDescarga()
/* 683:    */   {
/* 684:840 */     return "";
/* 685:    */   }
/* 686:    */   
/* 687:    */   public String getNombreArchivo()
/* 688:    */   {
/* 689:847 */     return null;
/* 690:    */   }
/* 691:    */   
/* 692:    */   public String getRutaPlantilla()
/* 693:    */   {
/* 694:854 */     return "";
/* 695:    */   }
/* 696:    */   
/* 697:    */   public String getNombrePlantilla()
/* 698:    */   {
/* 699:861 */     return "";
/* 700:    */   }
/* 701:    */   
/* 702:    */   public void crearLogAuditoria(ProcesoAuditoriaEnum procesoAuditoria, String mensaje)
/* 703:    */   {
/* 704:865 */     LogAuditoria logAuditoria = new LogAuditoria();
/* 705:866 */     logAuditoria.setIdEntidad(Long.valueOf(0L));
/* 706:867 */     logAuditoria.setClaseEntidad("");
/* 707:868 */     logAuditoria.setFechaCreacion(new Date());
/* 708:869 */     logAuditoria.setHostRemoto(AppUtil.getHostRemoto());
/* 709:870 */     logAuditoria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 710:871 */     logAuditoria.setNombreUsuario(AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 711:872 */     logAuditoria.setIdUsuario(new Long(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/* 712:873 */     logAuditoria.setProcesoAuditoria(procesoAuditoria);
/* 713:874 */     logAuditoria.setMensaje(mensaje);
/* 714:    */     try
/* 715:    */     {
/* 716:876 */       this.servicioLogAuditoria.guardar(logAuditoria);
/* 717:    */     }
/* 718:    */     catch (AS2Exception e)
/* 719:    */     {
/* 720:879 */       e.printStackTrace();
/* 721:    */     }
/* 722:    */   }
/* 723:    */   
/* 724:    */   public Map<String, String> agregarFiltroPorTipoVisualizacionUsuario(Map<String, String> filters, Usuario usuarioEnSesion, boolean indicadorEntidadRelacionSucursal)
/* 725:    */   {
/* 726:889 */     filters = agregarFiltroOrganizacion(filters);
/* 727:892 */     if (TipoVisualizacionEnum.MIS_SUCURSALES.equals(usuarioEnSesion.getTipoVisualizacion())) {
/* 728:893 */       agregarFiltroSucursalUsuario(filters, usuarioEnSesion, indicadorEntidadRelacionSucursal);
/* 729:    */     }
/* 730:896 */     if (TipoVisualizacionEnum.MIS_REGISTROS.equals(usuarioEnSesion.getTipoVisualizacion())) {
/* 731:897 */       filters.put("usuarioCreacion", "=" + usuarioEnSesion.getNombreUsuario());
/* 732:    */     }
/* 733:900 */     return filters;
/* 734:    */   }
/* 735:    */   
/* 736:    */   public Map<String, String> agregarFiltroSucursalUsuario(Map<String, String> filters, Usuario usuarioEnSesion, boolean indicadorEntidadRelacionSucursal)
/* 737:    */   {
/* 738:905 */     if (filters == null) {
/* 739:906 */       filters = new HashMap();
/* 740:    */     }
/* 741:909 */     int secuencial = 0;
/* 742:910 */     for (UsuarioSucursal usuarioSucursal : usuarioEnSesion.getListaUsuarioSucursal())
/* 743:    */     {
/* 744:911 */       secuencial++;
/* 745:912 */       String relacionSucursal = "idSucursal";
/* 746:914 */       if (indicadorEntidadRelacionSucursal) {
/* 747:915 */         relacionSucursal = "sucursal." + relacionSucursal;
/* 748:    */       }
/* 749:917 */       filters.put("OR~SUC01~" + secuencial + "~" + relacionSucursal, "" + usuarioSucursal.getSucursal().getId());
/* 750:    */     }
/* 751:920 */     return filters;
/* 752:    */   }
/* 753:    */   
/* 754:    */   public Map<String, String> agregarFiltroUsuarioAutorizacion(Map<String, String> filters, String nombreCampoUsuarioAutorizacion, List<EntidadUsuario> listaSubordinados)
/* 755:    */   {
/* 756:925 */     if (filters == null) {
/* 757:926 */       filters = new HashMap();
/* 758:    */     }
/* 759:929 */     int secuencial = 0;
/* 760:930 */     for (EntidadUsuario subordinado : listaSubordinados)
/* 761:    */     {
/* 762:931 */       secuencial++;
/* 763:932 */       filters.put("OR~USAUT01~" + secuencial + "~" + nombreCampoUsuarioAutorizacion, "=" + subordinado.getNombreUsuario());
/* 764:    */     }
/* 765:935 */     return filters;
/* 766:    */   }
/* 767:    */   
/* 768:    */   public SelectItem[] getListaTipoClienteItem()
/* 769:    */   {
/* 770:941 */     if (this.listaTipoClienteItem == null)
/* 771:    */     {
/* 772:942 */       List<SelectItem> lista = new ArrayList();
/* 773:943 */       lista.add(new SelectItem("", ""));
/* 774:944 */       for (TipoEmpresaEnum t : TipoEmpresaEnum.values())
/* 775:    */       {
/* 776:945 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 777:946 */         lista.add(item);
/* 778:    */       }
/* 779:948 */       this.listaTipoClienteItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 780:    */     }
/* 781:950 */     Arrays.sort(this.listaTipoClienteItem, new SelectItemComparator());
/* 782:951 */     return this.listaTipoClienteItem;
/* 783:    */   }
/* 784:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.controller.PageController
 * JD-Core Version:    0.7.0.1
 */