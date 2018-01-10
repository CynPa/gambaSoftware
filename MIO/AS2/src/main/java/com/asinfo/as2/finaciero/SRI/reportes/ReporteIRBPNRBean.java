/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.FileNotFoundException;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import javax.persistence.Temporal;
/*  26:    */ import javax.persistence.TemporalType;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.xml.parsers.DocumentBuilder;
/*  29:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  30:    */ import javax.xml.parsers.ParserConfigurationException;
/*  31:    */ import javax.xml.transform.Result;
/*  32:    */ import javax.xml.transform.Transformer;
/*  33:    */ import javax.xml.transform.TransformerException;
/*  34:    */ import javax.xml.transform.TransformerFactory;
/*  35:    */ import javax.xml.transform.dom.DOMSource;
/*  36:    */ import javax.xml.transform.stream.StreamResult;
/*  37:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  38:    */ import net.sf.jasperreports.engine.JRException;
/*  39:    */ import org.apache.log4j.Logger;
/*  40:    */ import org.primefaces.model.StreamedContent;
/*  41:    */ import org.w3c.dom.Document;
/*  42:    */ import org.w3c.dom.Element;
/*  43:    */ 
/*  44:    */ @ManagedBean
/*  45:    */ @ViewScoped
/*  46:    */ public class ReporteIRBPNRBean
/*  47:    */   extends AbstractBaseReportBean
/*  48:    */ {
/*  49:    */   private static final long serialVersionUID = 1L;
/*  50:    */   protected static final String CARPETA_ANEXOS = "anexos";
/*  51:    */   protected static final String TIPO_CONTENIDO_ARCHIVO = "application/xml";
/*  52:    */   @EJB
/*  53:    */   private ServicioReporteFabricacion servicioReporteFabricacion;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @NotNull
/*  56: 79 */   private Date fechaDesde = new Date();
/*  57:    */   @Temporal(TemporalType.DATE)
/*  58:    */   @NotNull
/*  59: 83 */   private Date fechaHasta = new Date();
/*  60:    */   private List<SelectItem> listaMes;
/*  61: 89 */   private int anio = Calendar.getInstance().get(1);
/*  62: 90 */   private int mes = Calendar.getInstance().get(2);
/*  63:    */   
/*  64:    */   protected JRDataSource getJRDataSource()
/*  65:    */   {
/*  66:104 */     List listaDatosReporte = new ArrayList();
/*  67:105 */     JRDataSource ds = null;
/*  68:    */     try
/*  69:    */     {
/*  70:107 */       this.fechaDesde = FuncionesUtiles.getFecha(1, this.mes, this.anio);
/*  71:108 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(this.fechaDesde);
/*  72:    */       
/*  73:110 */       listaDatosReporte = this.servicioReporteFabricacion.getReporteIRBPNR(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/*  74:    */       
/*  75:112 */       String[] fields = { "f_nombreProducto", "f_codigoProducto", "f_codigoImpuesto", "f_clasificacion", "f_marca", "f_presentacion", "f_capacidad", "f_unidad", "f_alcohol", "f_ventas", "f_devoluciones", "f_embotellamiento", "f_bajaEmbotellamiento", "f_bajaVenta", "f_porcentajeImpuesto" };
/*  76:    */       
/*  77:    */ 
/*  78:115 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82:118 */       e.printStackTrace();
/*  83:    */     }
/*  84:120 */     return ds;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected String getCompileFileName()
/*  88:    */   {
/*  89:130 */     return "anexoIBP";
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected Map<String, Object> getReportParameters()
/*  93:    */   {
/*  94:140 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  95:141 */     reportParameters.put("ReportTitle", "Anexo IBP");
/*  96:142 */     reportParameters.put("mes", Mes.values()[(this.mes - 1)].toString());
/*  97:143 */     reportParameters.put("anio", Integer.valueOf(this.anio));
/*  98:144 */     return reportParameters;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String execute()
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:154 */       super.prepareReport();
/* 106:    */     }
/* 107:    */     catch (JRException e)
/* 108:    */     {
/* 109:156 */       LOG.info("Error JRException");
/* 110:157 */       e.printStackTrace();
/* 111:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 112:    */     }
/* 113:    */     catch (IOException e)
/* 114:    */     {
/* 115:160 */       LOG.info("Error IOException");
/* 116:161 */       e.printStackTrace();
/* 117:162 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:164 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void validaDatos()
/* 123:    */   {
/* 124:169 */     if (this.fechaDesde == null) {
/* 125:170 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 126:    */     }
/* 127:172 */     if (this.fechaHasta == null) {
/* 128:173 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Date getFechaDesde()
/* 133:    */   {
/* 134:183 */     return this.fechaDesde;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFechaDesde(Date fechaDesde)
/* 138:    */   {
/* 139:193 */     this.fechaDesde = fechaDesde;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Date getFechaHasta()
/* 143:    */   {
/* 144:202 */     return this.fechaHasta;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setFechaHasta(Date fechaHasta)
/* 148:    */   {
/* 149:212 */     this.fechaHasta = fechaHasta;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<SelectItem> getListaMes()
/* 153:    */   {
/* 154:216 */     if (this.listaMes == null)
/* 155:    */     {
/* 156:217 */       this.listaMes = new ArrayList();
/* 157:218 */       for (Mes t : Mes.values())
/* 158:    */       {
/* 159:219 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 160:220 */         this.listaMes.add(item);
/* 161:    */       }
/* 162:    */     }
/* 163:223 */     return this.listaMes;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaMes(List<SelectItem> listaMes)
/* 167:    */   {
/* 168:233 */     this.listaMes = listaMes;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getAnio()
/* 172:    */   {
/* 173:242 */     return this.anio;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setAnio(int anio)
/* 177:    */   {
/* 178:252 */     this.anio = anio;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getMes()
/* 182:    */   {
/* 183:261 */     return this.mes;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setMes(int mes)
/* 187:    */   {
/* 188:271 */     this.mes = mes;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public StreamedContent getAnexo()
/* 192:    */   {
/* 193:275 */     StreamedContent file = null;
/* 194:    */     try
/* 195:    */     {
/* 196:277 */       this.fechaDesde = FuncionesUtiles.getFecha(1, this.mes, this.anio);
/* 197:278 */       this.fechaHasta = FuncionesUtiles.getFechaFinMes(this.fechaDesde);
/* 198:279 */       int idOrganizacion = AppUtil.getOrganizacion().getId();
/* 199:    */       
/* 200:281 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 201:282 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 202:    */       
/* 203:    */ 
/* 204:285 */       String strMes = (this.mes < 10 ? "0" : "") + this.mes;
/* 205:    */       
/* 206:    */ 
/* 207:288 */       Document doc = docBuilder.newDocument();
/* 208:    */       
/* 209:290 */       Element ibp = doc.createElement("ibp");
/* 210:291 */       doc.appendChild(ibp);
/* 211:    */       
/* 212:    */ 
/* 213:294 */       Element tipoIDInformante = doc.createElement("TipoIDInformante");
/* 214:295 */       tipoIDInformante.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getTipoIdentificacion().getCodigo()));
/* 215:296 */       ibp.appendChild(tipoIDInformante);
/* 216:    */       
/* 217:    */ 
/* 218:299 */       Element idInformante = doc.createElement("IdInformante");
/* 219:300 */       idInformante.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getIdentificacion()));
/* 220:301 */       ibp.appendChild(idInformante);
/* 221:    */       
/* 222:    */ 
/* 223:304 */       Element anioElement = doc.createElement("Anio");
/* 224:305 */       anioElement.appendChild(doc.createTextNode(String.valueOf(this.anio)));
/* 225:306 */       ibp.appendChild(anioElement);
/* 226:    */       
/* 227:    */ 
/* 228:309 */       Element mes = doc.createElement("Mes");
/* 229:310 */       mes.appendChild(doc.createTextNode(strMes));
/* 230:311 */       ibp.appendChild(mes);
/* 231:    */       
/* 232:    */ 
/* 233:314 */       Element razonSocial = doc.createElement("razonSocial");
/* 234:315 */       razonSocial.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getRazonSocial().replace(".", "")));
/* 235:316 */       ibp.appendChild(razonSocial);
/* 236:    */       
/* 237:    */ 
/* 238:319 */       Element codigoOperativo = doc.createElement("codigoOperativo");
/* 239:320 */       codigoOperativo.appendChild(doc.createTextNode("IBP"));
/* 240:321 */       ibp.appendChild(codigoOperativo);
/* 241:    */       
/* 242:    */ 
/* 243:324 */       List<Object[]> listaDatosReporte = this.servicioReporteFabricacion.getReporteIRBPNR(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta);
/* 244:    */       
/* 245:326 */       Element embotellamiento = doc.createElement("embotellamiento");
/* 246:327 */       ibp.appendChild(embotellamiento);
/* 247:329 */       for (Object[] objects : listaDatosReporte)
/* 248:    */       {
/* 249:330 */         Element emb = doc.createElement("emb");
/* 250:331 */         embotellamiento.appendChild(emb);
/* 251:    */         
/* 252:333 */         String codigoProductoIBP = "";
/* 253:334 */         codigoProductoIBP = codigoProductoIBP + (String)objects[2] + "-";
/* 254:335 */         codigoProductoIBP = codigoProductoIBP + (String)objects[3] + "-";
/* 255:336 */         codigoProductoIBP = codigoProductoIBP + (String)objects[4] + "-";
/* 256:337 */         codigoProductoIBP = codigoProductoIBP + (String)objects[5] + "-";
/* 257:338 */         codigoProductoIBP = codigoProductoIBP + (String)objects[6] + "-";
/* 258:339 */         codigoProductoIBP = codigoProductoIBP + (String)objects[7] + "-";
/* 259:340 */         codigoProductoIBP = codigoProductoIBP + "593-";
/* 260:341 */         codigoProductoIBP = codigoProductoIBP + (String)objects[8];
/* 261:    */         
/* 262:    */ 
/* 263:344 */         Element codProdIBP = doc.createElement("codProdIBP");
/* 264:345 */         codProdIBP.appendChild(doc.createTextNode(codigoProductoIBP));
/* 265:346 */         emb.appendChild(codProdIBP);
/* 266:    */         
/* 267:    */ 
/* 268:    */ 
/* 269:350 */         Element embIBP = doc.createElement("embIBP");
/* 270:351 */         embIBP.appendChild(doc.createTextNode(((Integer)objects[11]).toString()));
/* 271:352 */         emb.appendChild(embIBP);
/* 272:    */         
/* 273:    */ 
/* 274:    */ 
/* 275:356 */         Element canProBajaEmb = doc.createElement("canProBajaEmb");
/* 276:357 */         canProBajaEmb.appendChild(doc.createTextNode(((Integer)objects[12]).toString()));
/* 277:358 */         emb.appendChild(canProBajaEmb);
/* 278:    */         
/* 279:    */ 
/* 280:361 */         Element ventaIBP = doc.createElement("ventaIBP");
/* 281:362 */         ventaIBP.appendChild(doc.createTextNode(((Integer)objects[9]).toString()));
/* 282:363 */         emb.appendChild(ventaIBP);
/* 283:    */         
/* 284:    */ 
/* 285:366 */         Element devIBP = doc.createElement("devIBP");
/* 286:367 */         devIBP.appendChild(doc.createTextNode(((Integer)objects[10]).toString()));
/* 287:368 */         emb.appendChild(devIBP);
/* 288:    */         
/* 289:    */ 
/* 290:    */ 
/* 291:372 */         Element canProBajaVenta = doc.createElement("canProBajaVenta");
/* 292:373 */         canProBajaVenta.appendChild(doc.createTextNode(((Integer)objects[13]).toString()));
/* 293:374 */         emb.appendChild(canProBajaVenta);
/* 294:    */       }
/* 295:378 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 296:379 */       Transformer transformer = transformerFactory.newTransformer();
/* 297:    */       
/* 298:381 */       DOMSource source = new DOMSource(doc);
/* 299:    */       
/* 300:383 */       String directorioAnexo = getDirectorioAnexos();
/* 301:384 */       String nombreArchivo = "IBP-" + strMes + "-" + this.anio + "-" + AppUtil.getOrganizacion().getIdentificacion() + ".xml";
/* 302:385 */       String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 303:386 */       Result result = new StreamResult(new File(rutaArchivo));
/* 304:    */       
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:391 */       transformer.transform(source, result);
/* 309:    */       
/* 310:    */ 
/* 311:394 */       file = FuncionesUtiles.descargarArchivo(rutaArchivo, "application/xml", nombreArchivo);
/* 312:    */     }
/* 313:    */     catch (ParserConfigurationException pce)
/* 314:    */     {
/* 315:396 */       pce.printStackTrace();
/* 316:397 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 317:398 */       LOG.error("Error al generar el AT " + pce);
/* 318:    */     }
/* 319:    */     catch (TransformerException tfe)
/* 320:    */     {
/* 321:400 */       tfe.printStackTrace();
/* 322:401 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 323:402 */       LOG.error("Error al generar el AT " + tfe);
/* 324:    */     }
/* 325:    */     catch (FileNotFoundException e)
/* 326:    */     {
/* 327:404 */       e.printStackTrace();
/* 328:405 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado") + " " + e.getMessage());
/* 329:406 */       LOG.error("Archivo no encontrado " + e);
/* 330:    */     }
/* 331:    */     catch (Exception e)
/* 332:    */     {
/* 333:408 */       e.printStackTrace();
/* 334:409 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 335:410 */       LOG.error("Error al generar el AT " + e);
/* 336:    */     }
/* 337:414 */     return file;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String getDirectorioAnexos()
/* 341:    */   {
/* 342:418 */     String directorioAnexo = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getIdOrganizacion()) + File.separator + "anexos";
/* 343:419 */     File directorio = new File(directorioAnexo);
/* 344:420 */     if (!directorio.exists()) {
/* 345:421 */       directorio.mkdirs();
/* 346:    */     }
/* 347:424 */     return directorioAnexo;
/* 348:    */   }
/* 349:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.ReporteIRBPNRBean
 * JD-Core Version:    0.7.0.1
 */