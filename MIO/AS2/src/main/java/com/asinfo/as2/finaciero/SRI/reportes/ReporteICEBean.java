/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.FileNotFoundException;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import javax.faces.model.SelectItem;
/*  26:    */ import javax.persistence.Temporal;
/*  27:    */ import javax.persistence.TemporalType;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.xml.parsers.DocumentBuilder;
/*  30:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  31:    */ import javax.xml.parsers.ParserConfigurationException;
/*  32:    */ import javax.xml.transform.Result;
/*  33:    */ import javax.xml.transform.Transformer;
/*  34:    */ import javax.xml.transform.TransformerException;
/*  35:    */ import javax.xml.transform.TransformerFactory;
/*  36:    */ import javax.xml.transform.dom.DOMSource;
/*  37:    */ import javax.xml.transform.stream.StreamResult;
/*  38:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  39:    */ import net.sf.jasperreports.engine.JRException;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.model.StreamedContent;
/*  42:    */ import org.w3c.dom.Document;
/*  43:    */ import org.w3c.dom.Element;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class ReporteICEBean
/*  48:    */   extends AbstractBaseReportBean
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 1L;
/*  51:    */   protected static final String CARPETA_ANEXOS = "anexos";
/*  52:    */   protected static final String TIPO_CONTENIDO_ARCHIVO = "application/xml";
/*  53:    */   @EJB
/*  54:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @NotNull
/*  57: 78 */   private Date fechaDesde = new Date();
/*  58:    */   @Temporal(TemporalType.DATE)
/*  59:    */   @NotNull
/*  60: 82 */   private Date fechaHasta = new Date();
/*  61:    */   private List<SelectItem> listaMes;
/*  62: 88 */   private int anio = Calendar.getInstance().get(1);
/*  63: 89 */   private int mes = Calendar.getInstance().get(2);
/*  64:    */   
/*  65:    */   protected JRDataSource getJRDataSource()
/*  66:    */   {
/*  67:103 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  68:104 */     JRDataSource ds = null;
/*  69:    */     try
/*  70:    */     {
/*  71:106 */       this.fechaDesde = FuncionesUtiles.getFecha(1, this.mes, this.anio);
/*  72:107 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(this.fechaDesde);
/*  73:    */       
/*  74:109 */       listaDatosReporte = this.servicioFacturaClienteSRI.getReporteICE(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/*  75:    */       
/*  76:111 */       String[] fields = { "f_tipoCliente", "f_identificacion", "f_nombreProducto", "f_codigoProducto", "f_codigoIce", "f_clasificacion", "f_marca", "f_presentacion", "f_capacidad", "f_unidad", "f_alcohol", "f_ventas", "f_gramosAzucar", "f_indicadorExportacion", "f_devolucion", "f_nombreFiscal" };
/*  77:    */       
/*  78:    */ 
/*  79:114 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:117 */       e.printStackTrace();
/*  84:    */     }
/*  85:119 */     return ds;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected String getCompileFileName()
/*  89:    */   {
/*  90:129 */     return "anexoICE";
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected Map<String, Object> getReportParameters()
/*  94:    */   {
/*  95:139 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  96:140 */     reportParameters.put("ReportTitle", "Anexo ICE");
/*  97:141 */     reportParameters.put("mes", Mes.values()[(this.mes - 1)].toString());
/*  98:142 */     reportParameters.put("anio", Integer.valueOf(this.anio));
/*  99:143 */     return reportParameters;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String execute()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:153 */       super.prepareReport();
/* 107:    */     }
/* 108:    */     catch (JRException e)
/* 109:    */     {
/* 110:155 */       LOG.info("Error JRException");
/* 111:156 */       e.printStackTrace();
/* 112:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */     }
/* 114:    */     catch (IOException e)
/* 115:    */     {
/* 116:159 */       LOG.info("Error IOException");
/* 117:160 */       e.printStackTrace();
/* 118:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 119:    */     }
/* 120:163 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void validaDatos()
/* 124:    */   {
/* 125:168 */     if (this.fechaDesde == null) {
/* 126:169 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 127:    */     }
/* 128:171 */     if (this.fechaHasta == null) {
/* 129:172 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Date getFechaDesde()
/* 134:    */   {
/* 135:182 */     return this.fechaDesde;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFechaDesde(Date fechaDesde)
/* 139:    */   {
/* 140:192 */     this.fechaDesde = fechaDesde;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Date getFechaHasta()
/* 144:    */   {
/* 145:201 */     return this.fechaHasta;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setFechaHasta(Date fechaHasta)
/* 149:    */   {
/* 150:211 */     this.fechaHasta = fechaHasta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<SelectItem> getListaMes()
/* 154:    */   {
/* 155:215 */     if (this.listaMes == null)
/* 156:    */     {
/* 157:216 */       this.listaMes = new ArrayList();
/* 158:217 */       for (Mes t : Mes.values())
/* 159:    */       {
/* 160:218 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 161:219 */         this.listaMes.add(item);
/* 162:    */       }
/* 163:    */     }
/* 164:222 */     return this.listaMes;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaMes(List<SelectItem> listaMes)
/* 168:    */   {
/* 169:232 */     this.listaMes = listaMes;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getAnio()
/* 173:    */   {
/* 174:241 */     return this.anio;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setAnio(int anio)
/* 178:    */   {
/* 179:251 */     this.anio = anio;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int getMes()
/* 183:    */   {
/* 184:260 */     return this.mes;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setMes(int mes)
/* 188:    */   {
/* 189:270 */     this.mes = mes;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public StreamedContent getAnexo()
/* 193:    */   {
/* 194:274 */     StreamedContent file = null;
/* 195:    */     try
/* 196:    */     {
/* 197:276 */       this.fechaDesde = FuncionesUtiles.getFecha(1, this.mes, this.anio);
/* 198:277 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(this.fechaDesde);
/* 199:    */       
/* 200:279 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 201:280 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 202:    */       
/* 203:    */ 
/* 204:283 */       String strMes = (this.mes < 10 ? "0" : "") + this.mes;
/* 205:    */       
/* 206:    */ 
/* 207:286 */       Document doc = docBuilder.newDocument();
/* 208:    */       
/* 209:288 */       Element ice = doc.createElement("ice");
/* 210:289 */       doc.appendChild(ice);
/* 211:    */       
/* 212:    */ 
/* 213:292 */       Element tipoIDInformante = doc.createElement("TipoIDInformante");
/* 214:293 */       tipoIDInformante.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getTipoIdentificacion().getCodigo()));
/* 215:294 */       ice.appendChild(tipoIDInformante);
/* 216:    */       
/* 217:    */ 
/* 218:297 */       Element idInformante = doc.createElement("IdInformante");
/* 219:298 */       idInformante.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getIdentificacion()));
/* 220:299 */       ice.appendChild(idInformante);
/* 221:    */       
/* 222:    */ 
/* 223:302 */       Element razonSocial = doc.createElement("razonSocial");
/* 224:303 */       razonSocial.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getRazonSocial().replace(".", "")));
/* 225:304 */       ice.appendChild(razonSocial);
/* 226:    */       
/* 227:    */ 
/* 228:307 */       Element anioElement = doc.createElement("Anio");
/* 229:308 */       anioElement.appendChild(doc.createTextNode(String.valueOf(this.anio)));
/* 230:309 */       ice.appendChild(anioElement);
/* 231:    */       
/* 232:    */ 
/* 233:312 */       Element mes = doc.createElement("Mes");
/* 234:313 */       mes.appendChild(doc.createTextNode(strMes));
/* 235:314 */       ice.appendChild(mes);
/* 236:    */       
/* 237:    */ 
/* 238:317 */       Element importaciones = doc.createElement("actImport");
/* 239:318 */       if (this.servicioFacturaClienteSRI.getImportaciones(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta).longValue() > 0L)
/* 240:    */       {
/* 241:319 */         importaciones.appendChild(doc.createTextNode("02"));
/* 242:320 */         ice.appendChild(importaciones);
/* 243:    */       }
/* 244:    */       else
/* 245:    */       {
/* 246:322 */         importaciones.appendChild(doc.createTextNode("01"));
/* 247:323 */         ice.appendChild(importaciones);
/* 248:    */       }
/* 249:327 */       Element codigoOperativo = doc.createElement("codigoOperativo");
/* 250:328 */       codigoOperativo.appendChild(doc.createTextNode("ICE"));
/* 251:329 */       ice.appendChild(codigoOperativo);
/* 252:    */       
/* 253:    */ 
/* 254:332 */       List<Object[]> listaDatosReporte = this.servicioFacturaClienteSRI.getReporteICE(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/* 255:    */       
/* 256:334 */       Element ventas = doc.createElement("ventas");
/* 257:335 */       ice.appendChild(ventas);
/* 258:337 */       for (Object[] objects : listaDatosReporte)
/* 259:    */       {
/* 260:338 */         Element vta = doc.createElement("vta");
/* 261:339 */         ventas.appendChild(vta);
/* 262:    */         
/* 263:341 */         String codigoProdICE = "";
/* 264:342 */         codigoProdICE = codigoProdICE + (String)objects[4] + "-";
/* 265:343 */         if (objects[5] == null) {
/* 266:344 */           codigoProdICE = codigoProdICE + "00-";
/* 267:    */         } else {
/* 268:346 */           codigoProdICE = codigoProdICE + (String)objects[5] + "-";
/* 269:    */         }
/* 270:348 */         if (objects[6] == null) {
/* 271:349 */           codigoProdICE = codigoProdICE + "000000-";
/* 272:    */         } else {
/* 273:351 */           codigoProdICE = codigoProdICE + (String)objects[6] + "-";
/* 274:    */         }
/* 275:353 */         if (objects[7] == null) {
/* 276:354 */           codigoProdICE = codigoProdICE + "000-";
/* 277:    */         } else {
/* 278:356 */           codigoProdICE = codigoProdICE + (String)objects[7] + "-";
/* 279:    */         }
/* 280:358 */         if (objects[8] == null) {
/* 281:359 */           codigoProdICE = codigoProdICE + "000000-";
/* 282:    */         } else {
/* 283:361 */           codigoProdICE = codigoProdICE + (String)objects[8] + "-";
/* 284:    */         }
/* 285:363 */         if (objects[9] == null) {
/* 286:364 */           codigoProdICE = codigoProdICE + "00-";
/* 287:    */         } else {
/* 288:366 */           codigoProdICE = codigoProdICE + (String)objects[9] + "-";
/* 289:    */         }
/* 290:368 */         codigoProdICE = codigoProdICE + "593-";
/* 291:369 */         codigoProdICE = codigoProdICE + (String)objects[10];
/* 292:    */         
/* 293:    */ 
/* 294:372 */         Element codProdICE = doc.createElement("codProdICE");
/* 295:373 */         codProdICE.appendChild(doc.createTextNode(codigoProdICE));
/* 296:374 */         vta.appendChild(codProdICE);
/* 297:    */         
/* 298:    */ 
/* 299:377 */         Element gramoAzucar = doc.createElement("gramoAzucar");
/* 300:378 */         gramoAzucar.appendChild(doc.createTextNode(((BigDecimal)objects[12]).toString()));
/* 301:379 */         vta.appendChild(gramoAzucar);
/* 302:    */         
/* 303:    */ 
/* 304:382 */         Element tipoIdCliente = doc.createElement("tipoIdCliente");
/* 305:383 */         tipoIdCliente.appendChild(doc.createTextNode((String)objects[0]));
/* 306:384 */         vta.appendChild(tipoIdCliente);
/* 307:    */         
/* 308:    */ 
/* 309:387 */         Element idCliente = doc.createElement("idCliente");
/* 310:388 */         idCliente.appendChild(doc.createTextNode((String)objects[1]));
/* 311:389 */         vta.appendChild(idCliente);
/* 312:    */         
/* 313:    */ 
/* 314:392 */         Element tipoVentaICE = doc.createElement("tipoVentaICE");
/* 315:393 */         if (((Boolean)objects[13]).booleanValue()) {
/* 316:394 */           tipoVentaICE.appendChild(doc.createTextNode("2"));
/* 317:    */         } else {
/* 318:396 */           tipoVentaICE.appendChild(doc.createTextNode("1"));
/* 319:    */         }
/* 320:398 */         vta.appendChild(tipoVentaICE);
/* 321:    */         
/* 322:    */ 
/* 323:401 */         Element ventaICE = doc.createElement("ventaICE");
/* 324:402 */         ventaICE.appendChild(doc.createTextNode(((Integer)objects[11]).toString()));
/* 325:403 */         vta.appendChild(ventaICE);
/* 326:    */         
/* 327:    */ 
/* 328:406 */         Element devICE = doc.createElement("devICE");
/* 329:407 */         devICE.appendChild(doc.createTextNode(((Integer)objects[14]).toString()));
/* 330:408 */         vta.appendChild(devICE);
/* 331:    */         
/* 332:    */ 
/* 333:411 */         Element cantProdBajaICE = doc.createElement("cantProdBajaICE");
/* 334:412 */         cantProdBajaICE.appendChild(doc.createTextNode("0"));
/* 335:413 */         vta.appendChild(cantProdBajaICE);
/* 336:    */       }
/* 337:418 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 338:419 */       Transformer transformer = transformerFactory.newTransformer();
/* 339:    */       
/* 340:421 */       DOMSource source = new DOMSource(doc);
/* 341:    */       
/* 342:423 */       String directorioAnexo = getDirectorioAnexos();
/* 343:424 */       String nombreArchivo = "ICE-" + strMes + "-" + this.anio + "-" + AppUtil.getOrganizacion().getIdentificacion() + ".xml";
/* 344:425 */       String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 345:426 */       Result result = new StreamResult(new File(rutaArchivo));
/* 346:    */       
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:431 */       transformer.transform(source, result);
/* 351:    */       
/* 352:    */ 
/* 353:434 */       file = FuncionesUtiles.descargarArchivo(rutaArchivo, "application/xml", nombreArchivo);
/* 354:    */     }
/* 355:    */     catch (ParserConfigurationException pce)
/* 356:    */     {
/* 357:436 */       pce.printStackTrace();
/* 358:437 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 359:438 */       LOG.error("Error al generar el AT " + pce);
/* 360:    */     }
/* 361:    */     catch (TransformerException tfe)
/* 362:    */     {
/* 363:440 */       tfe.printStackTrace();
/* 364:441 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 365:442 */       LOG.error("Error al generar el AT " + tfe);
/* 366:    */     }
/* 367:    */     catch (FileNotFoundException e)
/* 368:    */     {
/* 369:444 */       e.printStackTrace();
/* 370:445 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado") + " " + e.getMessage());
/* 371:446 */       LOG.error("Archivo no encontrado " + e);
/* 372:    */     }
/* 373:    */     catch (Exception e)
/* 374:    */     {
/* 375:448 */       e.printStackTrace();
/* 376:449 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 377:450 */       LOG.error("Error al generar el AT " + e);
/* 378:    */     }
/* 379:454 */     return file;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public String getDirectorioAnexos()
/* 383:    */   {
/* 384:458 */     String directorioAnexo = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getIdOrganizacion()) + File.separator + "anexos";
/* 385:459 */     File directorio = new File(directorioAnexo);
/* 386:460 */     if (!directorio.exists()) {
/* 387:461 */       directorio.mkdirs();
/* 388:    */     }
/* 389:464 */     return directorioAnexo;
/* 390:    */   }
/* 391:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.ReporteICEBean
 * JD-Core Version:    0.7.0.1
 */