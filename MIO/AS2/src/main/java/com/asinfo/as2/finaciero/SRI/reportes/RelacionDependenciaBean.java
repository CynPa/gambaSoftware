/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.RelacionDependencia;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   8:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*   9:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  14:    */ import java.io.File;
/*  15:    */ import java.io.FileNotFoundException;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.math.RoundingMode;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.List;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.event.ActionEvent;
/*  25:    */ import javax.faces.model.SelectItem;
/*  26:    */ import javax.xml.parsers.DocumentBuilder;
/*  27:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  28:    */ import javax.xml.parsers.ParserConfigurationException;
/*  29:    */ import javax.xml.transform.Result;
/*  30:    */ import javax.xml.transform.Transformer;
/*  31:    */ import javax.xml.transform.TransformerException;
/*  32:    */ import javax.xml.transform.TransformerFactory;
/*  33:    */ import javax.xml.transform.dom.DOMSource;
/*  34:    */ import javax.xml.transform.stream.StreamResult;
/*  35:    */ import org.apache.log4j.Logger;
/*  36:    */ import org.primefaces.model.StreamedContent;
/*  37:    */ import org.w3c.dom.Document;
/*  38:    */ import org.w3c.dom.Element;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class RelacionDependenciaBean
/*  43:    */   extends PageController
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = -938781041300438945L;
/*  46:    */   @EJB
/*  47:    */   private ServicioPagoRol servicioPagoRol;
/*  48:    */   @EJB
/*  49:    */   private ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  50:    */   private StreamedContent file;
/*  51: 74 */   private int anio = Calendar.getInstance().get(1);
/*  52:    */   private List<RelacionDependencia> listaRelacionDependencia;
/*  53:    */   private static final String CARPETA_ANEXOS = "anexos";
/*  54:    */   private static final String TIPO_CONTENIDO_ARCHIVO = "application/xml";
/*  55:    */   private boolean processed;
/*  56: 81 */   private int mes = Calendar.getInstance().get(2);
/*  57:    */   private List<SelectItem> listaMes;
/*  58:    */   private boolean acumulado;
/*  59: 85 */   private BigDecimal totalSueldoSalario = BigDecimal.ZERO;
/*  60: 86 */   private BigDecimal totalSobreSueldo = BigDecimal.ZERO;
/*  61: 87 */   private BigDecimal totalSueldoSalarioOtro = BigDecimal.ZERO;
/*  62: 88 */   private BigDecimal totalDecimoTercero = BigDecimal.ZERO;
/*  63: 89 */   private BigDecimal totalDecimoCuarto = BigDecimal.ZERO;
/*  64: 90 */   private BigDecimal totalUtilidades = BigDecimal.ZERO;
/*  65: 91 */   private BigDecimal totalIess = BigDecimal.ZERO;
/*  66: 92 */   private BigDecimal totalIessOtro = BigDecimal.ZERO;
/*  67: 93 */   private BigDecimal totalBaseImponible = BigDecimal.ZERO;
/*  68: 94 */   private BigDecimal totalValorRetener = BigDecimal.ZERO;
/*  69: 95 */   private BigDecimal totalValorRetenido = BigDecimal.ZERO;
/*  70:    */   
/*  71:    */   public StreamedContent generarRDEP()
/*  72:    */     throws ParserConfigurationException, TransformerException, FileNotFoundException, ExcepcionAS2Nomina
/*  73:    */   {
/*  74: 98 */     StreamedContent file = null;
/*  75:    */     
/*  76:100 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/*  77:101 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/*  78:    */     
/*  79:    */ 
/*  80:104 */     Document doc = docBuilder.newDocument();
/*  81:    */     
/*  82:106 */     Element rdep = doc.createElement("rdep");
/*  83:107 */     doc.appendChild(rdep);
/*  84:    */     
/*  85:    */ 
/*  86:110 */     cabecera(doc, rdep);
/*  87:    */     
/*  88:    */ 
/*  89:113 */     RDEP(doc, rdep);
/*  90:    */     
/*  91:    */ 
/*  92:116 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*  93:117 */     Transformer transformer = transformerFactory.newTransformer();
/*  94:118 */     transformer.setOutputProperty("indent", "yes");
/*  95:    */     
/*  96:120 */     DOMSource source = new DOMSource(doc);
/*  97:    */     
/*  98:122 */     String directorioAnexo = getDirectorioAnexos();
/*  99:123 */     String nombreArchivo = "RDEP-" + this.anio + ".xml";
/* 100:124 */     String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 101:125 */     Result result = new StreamResult(new File(rutaArchivo));
/* 102:    */     
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:130 */     transformer.transform(source, result);
/* 107:    */     
/* 108:    */ 
/* 109:133 */     file = FuncionesUtiles.descargarArchivo(rutaArchivo, "application/xml", nombreArchivo);
/* 110:    */     
/* 111:135 */     LOG.info("Archivo RDEP generado correctamente " + file);
/* 112:    */     
/* 113:137 */     return file;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void btnProcesarActionListener(ActionEvent event)
/* 117:    */   {
/* 118:149 */     List<Integer> lista = new ArrayList();
/* 119:    */     int i;
/* 120:150 */     if (getMes() != 0) {
/* 121:151 */       if (this.acumulado) {
/* 122:152 */         for (i = 1; i <= getMes(); i++) {
/* 123:153 */           lista.add(Integer.valueOf(i));
/* 124:    */         }
/* 125:    */       } else {
/* 126:156 */         lista.add(Integer.valueOf(getMes()));
/* 127:    */       }
/* 128:    */     }
/* 129:    */     try
/* 130:    */     {
/* 131:161 */       this.listaRelacionDependencia = new ArrayList();
/* 132:162 */       for (RelacionDependencia rd : this.servicioPagoRol.obtenerDatosRDEP(getAnio(), AppUtil.getOrganizacion().getIdOrganizacion(), lista)) {
/* 133:163 */         if (((rd.getAportePersonalIess() != null) && (rd.getAportePersonalIess().compareTo(BigDecimal.ZERO) != 0)) || 
/* 134:164 */           ((rd.getAportePersonalIessOtroEmpleador() != null) && 
/* 135:165 */           (rd.getAportePersonalIessOtroEmpleador().compareTo(BigDecimal.ZERO) != 0)) || 
/* 136:166 */           ((rd.getBaseImponible() != null) && (rd.getBaseImponible().compareTo(BigDecimal.ZERO) != 0)) || 
/* 137:167 */           ((rd.getDecimoCuarto() != null) && (rd.getDecimoCuarto().compareTo(BigDecimal.ZERO) != 0)) || 
/* 138:168 */           ((rd.getDecimoTercero() != null) && (rd.getDecimoTercero().compareTo(BigDecimal.ZERO) != 0)) || 
/* 139:169 */           ((rd.getDeducibleAlimentacion() != null) && (rd.getDeducibleAlimentacion().compareTo(BigDecimal.ZERO) != 0)) || 
/* 140:170 */           ((rd.getDeducibleEducacion() != null) && (rd.getDeducibleEducacion().compareTo(BigDecimal.ZERO) != 0)) || 
/* 141:171 */           ((rd.getDeducibleSalud() != null) && (rd.getDeducibleSalud().compareTo(BigDecimal.ZERO) != 0)) || 
/* 142:172 */           ((rd.getDeducibleVestimenta() != null) && (rd.getDeducibleVestimenta().compareTo(BigDecimal.ZERO) != 0)) || 
/* 143:173 */           ((rd.getDeducibleVivienda() != null) && (rd.getDeducibleVivienda().compareTo(BigDecimal.ZERO) != 0)) || 
/* 144:174 */           ((rd.getFondoReserva() != null) && (rd.getFondoReserva().compareTo(BigDecimal.ZERO) != 0)) || 
/* 145:175 */           ((rd.getImpuestoRentaCausado() != null) && (rd.getImpuestoRentaCausado().compareTo(BigDecimal.ZERO) != 0)) || 
/* 146:176 */           ((rd.getSobreSueldo() != null) && (rd.getSobreSueldo().compareTo(BigDecimal.ZERO) != 0)) || 
/* 147:177 */           ((rd.getSueldoSalario() != null) && (rd.getSueldoSalario().compareTo(BigDecimal.ZERO) != 0)) || 
/* 148:178 */           ((rd.getSueldoSalarioOtroEmpleador() != null) && (rd.getSueldoSalarioOtroEmpleador().compareTo(BigDecimal.ZERO) != 0)) || 
/* 149:179 */           ((rd.getUtilidades() != null) && (rd.getUtilidades().compareTo(BigDecimal.ZERO) != 0)) || 
/* 150:180 */           ((rd.getValorRetenido() != null) && (rd.getValorRetenido().compareTo(BigDecimal.ZERO) != 0)) || (
/* 151:181 */           (rd.getValorRetenidoOtroEmpleado() != null) && (rd.getValorRetenidoOtroEmpleado().compareTo(BigDecimal.ZERO) != 0))) {
/* 152:182 */           this.listaRelacionDependencia.add(rd);
/* 153:    */         }
/* 154:    */       }
/* 155:185 */       if (this.listaRelacionDependencia.size() > 0) {
/* 156:186 */         for (RelacionDependencia detalle : this.listaRelacionDependencia)
/* 157:    */         {
/* 158:190 */           BigDecimal totalIngresos = detalle.getSueldoSalario().add(detalle.getSobreSueldo()).add(detalle.getUtilidades()).add(detalle.getSueldoSalarioOtroEmpleador());
/* 159:    */           
/* 160:    */ 
/* 161:    */ 
/* 162:194 */           BigDecimal totalGastos = detalle.getDeducibleAlimentacion().add(detalle.getDeducibleEducacion()).add(detalle.getDeducibleSalud()).add(detalle.getDeducibleVestimenta()).add(detalle.getDeducibleVivienda());
/* 163:    */           
/* 164:    */ 
/* 165:197 */           BigDecimal totalIESS = detalle.getAportePersonalIess().add(detalle.getAportePersonalIessOtroEmpleador());
/* 166:    */           
/* 167:    */ 
/* 168:200 */           BigDecimal baseImponible = totalIngresos.subtract(totalIESS).subtract(totalGastos);
/* 169:201 */           BigDecimal impuestoRentaCausado = this.servicioPagoRolEmpleado.impuestoALaRentaCausado(this.anio, 
/* 170:202 */             AppUtil.getOrganizacion().getIdOrganizacion(), totalIngresos, totalIESS, totalGastos);
/* 171:203 */           detalle.setBaseImponible(baseImponible.doubleValue() < 0.0D ? BigDecimal.ZERO.setScale(2) : baseImponible);
/* 172:204 */           detalle.setImpuestoRentaCausado(impuestoRentaCausado);
/* 173:    */           
/* 174:206 */           calcularTotales(detalle);
/* 175:    */         }
/* 176:    */       }
/* 177:211 */       this.processed = true;
/* 178:    */     }
/* 179:    */     catch (ExcepcionAS2Nomina e)
/* 180:    */     {
/* 181:213 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:216 */       e.printStackTrace();
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   private void calcularTotales(RelacionDependencia detalle)
/* 190:    */   {
/* 191:224 */     this.totalSueldoSalario = this.totalSueldoSalario.add(detalle.getSueldoSalario());
/* 192:225 */     this.totalSobreSueldo = this.totalSobreSueldo.add(detalle.getSobreSueldo());
/* 193:226 */     this.totalSueldoSalarioOtro = this.totalSueldoSalarioOtro.add(detalle.getSueldoSalarioOtroEmpleador());
/* 194:227 */     this.totalDecimoTercero = this.totalDecimoTercero.add(detalle.getDecimoTercero());
/* 195:228 */     this.totalDecimoCuarto = this.totalDecimoCuarto.add(detalle.getDecimoCuarto());
/* 196:229 */     this.totalUtilidades = this.totalUtilidades.add(detalle.getUtilidades());
/* 197:230 */     this.totalIess = this.totalIess.add(detalle.getAportePersonalIess());
/* 198:231 */     this.totalIessOtro = this.totalIessOtro.add(detalle.getAportePersonalIessOtroEmpleador());
/* 199:232 */     this.totalBaseImponible = this.totalBaseImponible.add(detalle.getBaseImponible());
/* 200:233 */     this.totalValorRetener = this.totalValorRetener.add(detalle.getImpuestoRentaCausado());
/* 201:234 */     this.totalValorRetenido = this.totalValorRetenido.add(detalle.getValorRetenido());
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getDirectorioAnexos()
/* 205:    */   {
/* 206:244 */     String directorioAnexo = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getIdOrganizacion()) + File.separator + "anexos";
/* 207:245 */     File directorio = new File(directorioAnexo);
/* 208:246 */     if (!directorio.exists()) {
/* 209:247 */       directorio.mkdirs();
/* 210:    */     }
/* 211:250 */     return directorioAnexo;
/* 212:    */   }
/* 213:    */   
/* 214:    */   private void cabecera(Document doc, Element rdep)
/* 215:    */   {
/* 216:262 */     Element numeroRuc = doc.createElement("numRuc");
/* 217:263 */     numeroRuc.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getIdentificacion()));
/* 218:264 */     rdep.appendChild(numeroRuc);
/* 219:    */     
/* 220:    */ 
/* 221:267 */     Element anioElement = doc.createElement("anio");
/* 222:268 */     anioElement.appendChild(doc.createTextNode(String.valueOf(this.anio)));
/* 223:269 */     rdep.appendChild(anioElement);
/* 224:    */   }
/* 225:    */   
/* 226:    */   private void RDEP(Document doc, Element rdep)
/* 227:    */     throws ExcepcionAS2Nomina
/* 228:    */   {
/* 229:    */     Element retRelDep;
/* 230:281 */     if (this.listaRelacionDependencia.size() > 0)
/* 231:    */     {
/* 232:282 */       retRelDep = doc.createElement("retRelDep");
/* 233:283 */       rdep.appendChild(retRelDep);
/* 234:285 */       for (RelacionDependencia relacionDependencia : this.listaRelacionDependencia)
/* 235:    */       {
/* 236:287 */         Element datRetRelDep = doc.createElement("datRetRelDep");
/* 237:288 */         retRelDep.appendChild(datRetRelDep);
/* 238:    */         
/* 239:290 */         Element empleado = doc.createElement("empleado");
/* 240:291 */         datRetRelDep.appendChild(empleado);
/* 241:    */         
/* 242:    */ 
/* 243:294 */         Element benGalpg = doc.createElement("benGalpg");
/* 244:295 */         benGalpg.appendChild(doc.createTextNode("NO"));
/* 245:296 */         empleado.appendChild(benGalpg);
/* 246:    */         
/* 247:    */ 
/* 248:299 */         Element tipIdRet = doc.createElement("tipIdRet");
/* 249:300 */         tipIdRet.appendChild(doc.createTextNode(relacionDependencia.getTipoIdentificacion()));
/* 250:301 */         empleado.appendChild(tipIdRet);
/* 251:    */         
/* 252:    */ 
/* 253:304 */         Element idRet = doc.createElement("idRet");
/* 254:305 */         idRet.appendChild(doc.createTextNode(relacionDependencia.getCedula()));
/* 255:306 */         empleado.appendChild(idRet);
/* 256:    */         
/* 257:    */ 
/* 258:309 */         Element apellidoTrab = doc.createElement("apellidoTrab");
/* 259:310 */         apellidoTrab.appendChild(doc.createTextNode(
/* 260:311 */           FuncionesUtiles.removerCaracteresEspeciales(relacionDependencia.getApellido().toUpperCase().trim().replaceAll("  ", " "))));
/* 261:312 */         empleado.appendChild(apellidoTrab);
/* 262:    */         
/* 263:    */ 
/* 264:315 */         Element nombreTrab = doc.createElement("nombreTrab");
/* 265:316 */         nombreTrab.appendChild(doc.createTextNode(
/* 266:317 */           FuncionesUtiles.removerCaracteresEspeciales(relacionDependencia.getNombre().toUpperCase().trim().replaceAll("  ", " "))));
/* 267:318 */         empleado.appendChild(nombreTrab);
/* 268:    */         
/* 269:    */ 
/* 270:321 */         Element estab = doc.createElement("estab");
/* 271:322 */         estab.appendChild(doc.createTextNode(relacionDependencia.getEstablecimiento()));
/* 272:323 */         empleado.appendChild(estab);
/* 273:    */         
/* 274:    */ 
/* 275:326 */         Element residenciaTrab = doc.createElement("residenciaTrab");
/* 276:327 */         residenciaTrab.appendChild(doc.createTextNode(relacionDependencia.getResidenciaTrabajador()));
/* 277:328 */         empleado.appendChild(residenciaTrab);
/* 278:    */         
/* 279:    */ 
/* 280:331 */         Element paisResidencia = doc.createElement("paisResidencia");
/* 281:332 */         paisResidencia.appendChild(doc.createTextNode(relacionDependencia.getPaisResidencia()));
/* 282:333 */         empleado.appendChild(paisResidencia);
/* 283:    */         
/* 284:    */ 
/* 285:336 */         Element aplicaConvenio = doc.createElement("aplicaConvenio");
/* 286:337 */         aplicaConvenio.appendChild(doc.createTextNode(relacionDependencia.getAplicaConvenio()));
/* 287:338 */         empleado.appendChild(aplicaConvenio);
/* 288:    */         
/* 289:    */ 
/* 290:341 */         Element tipoTrabajDiscap = doc.createElement("tipoTrabajDiscap");
/* 291:342 */         tipoTrabajDiscap.appendChild(doc.createTextNode(relacionDependencia.getTipoTrabajadorDiscapacidad()));
/* 292:343 */         empleado.appendChild(tipoTrabajDiscap);
/* 293:    */         
/* 294:    */ 
/* 295:346 */         Double porcentajeDiscapacidad = Double.valueOf(Double.parseDouble(relacionDependencia.getPorcentajeDiscapacidad()));
/* 296:347 */         Element porcentajeDiscap = doc.createElement("porcentajeDiscap");
/* 297:348 */         porcentajeDiscap.appendChild(doc.createTextNode(String.valueOf(porcentajeDiscapacidad.intValue())));
/* 298:349 */         empleado.appendChild(porcentajeDiscap);
/* 299:    */         
/* 300:    */ 
/* 301:352 */         Element tipIdDiscap = doc.createElement("tipIdDiscap");
/* 302:353 */         tipIdDiscap.appendChild(doc.createTextNode(relacionDependencia.getTipoIdentificacionDiscapacidad()));
/* 303:354 */         empleado.appendChild(tipIdDiscap);
/* 304:    */         
/* 305:    */ 
/* 306:357 */         Element idDiscap = doc.createElement("idDiscap");
/* 307:358 */         idDiscap.appendChild(doc.createTextNode(relacionDependencia.getIdentificacionDiscapacidad()));
/* 308:359 */         empleado.appendChild(idDiscap);
/* 309:    */         
/* 310:    */ 
/* 311:362 */         Element suelSal = doc.createElement("suelSal");
/* 312:363 */         suelSal.appendChild(doc.createTextNode("" + relacionDependencia.getSueldoSalario()));
/* 313:364 */         datRetRelDep.appendChild(suelSal);
/* 314:    */         
/* 315:    */ 
/* 316:367 */         Element sobSuelComRemu = doc.createElement("sobSuelComRemu");
/* 317:368 */         sobSuelComRemu.appendChild(doc.createTextNode("" + relacionDependencia.getSobreSueldo()));
/* 318:369 */         datRetRelDep.appendChild(sobSuelComRemu);
/* 319:    */         
/* 320:    */ 
/* 321:372 */         Element partUtil = doc.createElement("partUtil");
/* 322:373 */         partUtil.appendChild(doc.createTextNode("" + relacionDependencia.getUtilidades()));
/* 323:374 */         datRetRelDep.appendChild(partUtil);
/* 324:    */         
/* 325:    */ 
/* 326:377 */         Element intGrabGen = doc.createElement("intGrabGen");
/* 327:378 */         intGrabGen.appendChild(doc.createTextNode("" + relacionDependencia.getSueldoSalarioOtroEmpleador()));
/* 328:379 */         datRetRelDep.appendChild(intGrabGen);
/* 329:    */         
/* 330:    */ 
/* 331:382 */         Element impRentEmpl = doc.createElement("impRentEmpl");
/* 332:383 */         impRentEmpl.appendChild(doc.createTextNode("0"));
/* 333:384 */         datRetRelDep.appendChild(impRentEmpl);
/* 334:    */         
/* 335:    */ 
/* 336:387 */         Element decimTer = doc.createElement("decimTer");
/* 337:388 */         decimTer.appendChild(doc.createTextNode("" + relacionDependencia.getDecimoTercero()));
/* 338:389 */         datRetRelDep.appendChild(decimTer);
/* 339:    */         
/* 340:    */ 
/* 341:392 */         Element decimCuar = doc.createElement("decimCuar");
/* 342:393 */         decimCuar.appendChild(doc.createTextNode("" + relacionDependencia.getDecimoCuarto()));
/* 343:394 */         datRetRelDep.appendChild(decimCuar);
/* 344:    */         
/* 345:    */ 
/* 346:397 */         Element fondoReserva = doc.createElement("fondoReserva");
/* 347:398 */         fondoReserva.appendChild(doc.createTextNode("" + relacionDependencia.getFondoReserva()));
/* 348:399 */         datRetRelDep.appendChild(fondoReserva);
/* 349:    */         
/* 350:    */ 
/* 351:402 */         Element salarioDigno = doc.createElement("salarioDigno");
/* 352:403 */         salarioDigno.appendChild(doc.createTextNode("0.00"));
/* 353:404 */         datRetRelDep.appendChild(salarioDigno);
/* 354:    */         
/* 355:    */ 
/* 356:407 */         Element otrosIngRenGrav = doc.createElement("otrosIngRenGrav");
/* 357:408 */         otrosIngRenGrav.appendChild(doc.createTextNode("0.00"));
/* 358:409 */         datRetRelDep.appendChild(otrosIngRenGrav);
/* 359:    */         
/* 360:    */ 
/* 361:412 */         BigDecimal ingresoConEsteEmpleador = relacionDependencia.getSueldoSalario().add(relacionDependencia.getSobreSueldo()).add(relacionDependencia.getUtilidades());
/* 362:    */         
/* 363:414 */         Element ingGravConEsteEmpl = doc.createElement("ingGravConEsteEmpl");
/* 364:415 */         ingGravConEsteEmpl.appendChild(doc.createTextNode("" + ingresoConEsteEmpleador));
/* 365:416 */         datRetRelDep.appendChild(ingGravConEsteEmpl);
/* 366:    */         
/* 367:    */ 
/* 368:419 */         Element sisSalNet = doc.createElement("sisSalNet");
/* 369:420 */         sisSalNet.appendChild(doc.createTextNode("1"));
/* 370:421 */         datRetRelDep.appendChild(sisSalNet);
/* 371:    */         
/* 372:    */ 
/* 373:424 */         Element apoPerIess = doc.createElement("apoPerIess");
/* 374:425 */         apoPerIess.appendChild(doc.createTextNode("" + relacionDependencia.getAportePersonalIess()));
/* 375:426 */         datRetRelDep.appendChild(apoPerIess);
/* 376:    */         
/* 377:    */ 
/* 378:429 */         Element aporPerIessConOtrosEmpls = doc.createElement("aporPerIessConOtrosEmpls");
/* 379:430 */         aporPerIessConOtrosEmpls.appendChild(doc.createTextNode("" + relacionDependencia.getAportePersonalIessOtroEmpleador()));
/* 380:431 */         datRetRelDep.appendChild(aporPerIessConOtrosEmpls);
/* 381:    */         
/* 382:    */ 
/* 383:434 */         Element deducVivienda = doc.createElement("deducVivienda");
/* 384:435 */         deducVivienda.appendChild(doc.createTextNode("" + relacionDependencia.getDeducibleVivienda()));
/* 385:436 */         datRetRelDep.appendChild(deducVivienda);
/* 386:    */         
/* 387:    */ 
/* 388:439 */         Element deducSalud = doc.createElement("deducSalud");
/* 389:440 */         deducSalud.appendChild(doc.createTextNode("" + relacionDependencia.getDeducibleSalud()));
/* 390:441 */         datRetRelDep.appendChild(deducSalud);
/* 391:    */         
/* 392:    */ 
/* 393:444 */         Element deducEduca = doc.createElement("deducEduca");
/* 394:445 */         deducEduca.appendChild(doc.createTextNode("" + relacionDependencia.getDeducibleEducacion()));
/* 395:446 */         datRetRelDep.appendChild(deducEduca);
/* 396:    */         
/* 397:    */ 
/* 398:449 */         Element deducAliement = doc.createElement("deducAliement");
/* 399:450 */         deducAliement.appendChild(doc.createTextNode("" + relacionDependencia.getDeducibleAlimentacion()));
/* 400:451 */         datRetRelDep.appendChild(deducAliement);
/* 401:    */         
/* 402:    */ 
/* 403:454 */         Element deducVestim = doc.createElement("deducVestim");
/* 404:455 */         deducVestim.appendChild(doc.createTextNode("" + relacionDependencia.getDeducibleVestimenta()));
/* 405:456 */         datRetRelDep.appendChild(deducVestim);
/* 406:    */         
/* 407:    */ 
/* 408:459 */         Element exoDiscap = doc.createElement("exoDiscap");
/* 409:460 */         exoDiscap.appendChild(doc.createTextNode("0.00"));
/* 410:461 */         datRetRelDep.appendChild(exoDiscap);
/* 411:    */         
/* 412:    */ 
/* 413:464 */         Element exoTerEd = doc.createElement("exoTerEd");
/* 414:465 */         exoTerEd.appendChild(doc.createTextNode("0"));
/* 415:466 */         datRetRelDep.appendChild(exoTerEd);
/* 416:    */         
/* 417:    */ 
/* 418:469 */         Element basImp = doc.createElement("basImp");
/* 419:470 */         basImp.appendChild(doc.createTextNode("" + relacionDependencia.getBaseImponible()));
/* 420:471 */         datRetRelDep.appendChild(basImp);
/* 421:    */         
/* 422:    */ 
/* 423:474 */         Element impRentCaus = doc.createElement("impRentCaus");
/* 424:475 */         impRentCaus.appendChild(doc.createTextNode("" + relacionDependencia.getImpuestoRentaCausado()));
/* 425:476 */         datRetRelDep.appendChild(impRentCaus);
/* 426:    */         
/* 427:    */ 
/* 428:479 */         Element valRetAsuOtrosEmpls = doc.createElement("valRetAsuOtrosEmpls");
/* 429:480 */         valRetAsuOtrosEmpls.appendChild(doc.createTextNode("" + relacionDependencia.getValorRetenidoOtroEmpleado()));
/* 430:481 */         datRetRelDep.appendChild(valRetAsuOtrosEmpls);
/* 431:    */         
/* 432:    */ 
/* 433:484 */         Element valImpAsuEsteEmpl = doc.createElement("valImpAsuEsteEmpl");
/* 434:485 */         valImpAsuEsteEmpl.appendChild(doc.createTextNode("0"));
/* 435:486 */         datRetRelDep.appendChild(valImpAsuEsteEmpl);
/* 436:    */         
/* 437:    */ 
/* 438:489 */         Element valRet = doc.createElement("valRet");
/* 439:490 */         valRet.appendChild(doc.createTextNode("" + relacionDependencia.getValorRetenido()));
/* 440:491 */         datRetRelDep.appendChild(valRet);
/* 441:    */         
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:496 */         BigDecimal valorRemunContrEstEmpl = relacionDependencia.getSueldoAgravadaContribucionEmpleador().add(relacionDependencia.getSobreSueldoAgravadaContribucionEmpleador());
/* 446:497 */         BigDecimal valorExonRemunContr = BigDecimal.ZERO;
/* 447:498 */         BigDecimal valorTotRemunContr = valorRemunContrEstEmpl.add(relacionDependencia.getSueldoAgravadaContribucionOtroEmpleador()).subtract(valorExonRemunContr);
/* 448:499 */         long valorTotNumMesTrabContr = relacionDependencia.getMesesTrabajadosVigenciaContribucionOtroEmpleador() + relacionDependencia.getMesesTrabajadosVigenciaContribucionEmpleador();
/* 449:500 */         BigDecimal valorRemunMenPromContr = valorTotNumMesTrabContr == 0L ? BigDecimal.ZERO : valorTotRemunContr.divide(new BigDecimal(valorTotNumMesTrabContr), 2, RoundingMode.HALF_UP);
/* 450:503 */         if ((relacionDependencia.getMesesTrabajadosContribucionEmpleador() > 0L) && (valorRemunMenPromContr.compareTo(new BigDecimal(1000)) >= 0))
/* 451:    */         {
/* 452:505 */           Element contribucion = doc.createElement("contribucion");
/* 453:506 */           datRetRelDep.appendChild(contribucion);
/* 454:    */           
/* 455:508 */           Element remunContrEstEmpl = doc.createElement("remunContrEstEmpl");
/* 456:509 */           remunContrEstEmpl.appendChild(doc.createTextNode("" + valorRemunContrEstEmpl));
/* 457:510 */           contribucion.appendChild(remunContrEstEmpl);
/* 458:    */           
/* 459:512 */           Element remunContrOtrEmpl = doc.createElement("remunContrOtrEmpl");
/* 460:513 */           remunContrOtrEmpl.appendChild(doc.createTextNode("" + relacionDependencia.getSueldoAgravadaContribucionOtroEmpleador()));
/* 461:514 */           contribucion.appendChild(remunContrOtrEmpl);
/* 462:    */           
/* 463:516 */           Element exonRemunContr = doc.createElement("exonRemunContr");
/* 464:517 */           exonRemunContr.appendChild(doc.createTextNode("" + valorExonRemunContr));
/* 465:518 */           contribucion.appendChild(exonRemunContr);
/* 466:    */           
/* 467:520 */           Element totRemunContr = doc.createElement("totRemunContr");
/* 468:521 */           totRemunContr.appendChild(doc.createTextNode("" + valorTotRemunContr));
/* 469:522 */           contribucion.appendChild(totRemunContr);
/* 470:    */           
/* 471:524 */           Element numMesTrabContrEstEmpl = doc.createElement("numMesTrabContrEstEmpl");
/* 472:525 */           numMesTrabContrEstEmpl.appendChild(doc.createTextNode("" + relacionDependencia.getMesesTrabajadosVigenciaContribucionEmpleador()));
/* 473:526 */           contribucion.appendChild(numMesTrabContrEstEmpl);
/* 474:    */           
/* 475:528 */           Element numMesTrabContrOtrEmpl = doc.createElement("numMesTrabContrOtrEmpl");
/* 476:529 */           numMesTrabContrOtrEmpl
/* 477:530 */             .appendChild(doc.createTextNode("" + relacionDependencia.getMesesTrabajadosVigenciaContribucionOtroEmpleador()));
/* 478:531 */           contribucion.appendChild(numMesTrabContrOtrEmpl);
/* 479:    */           
/* 480:533 */           Element totNumMesTrabContr = doc.createElement("totNumMesTrabContr");
/* 481:534 */           totNumMesTrabContr.appendChild(doc.createTextNode("" + valorTotNumMesTrabContr));
/* 482:535 */           contribucion.appendChild(totNumMesTrabContr);
/* 483:    */           
/* 484:537 */           Element remunMenPromContr = doc.createElement("remunMenPromContr");
/* 485:538 */           remunMenPromContr.appendChild(doc.createTextNode("" + valorRemunMenPromContr));
/* 486:539 */           contribucion.appendChild(remunMenPromContr);
/* 487:    */           
/* 488:541 */           Element numMesContrGenEstEmpl = doc.createElement("numMesContrGenEstEmpl");
/* 489:542 */           numMesContrGenEstEmpl.appendChild(doc.createTextNode("" + relacionDependencia.getMesesTrabajadosContribucionEmpleador()));
/* 490:543 */           contribucion.appendChild(numMesContrGenEstEmpl);
/* 491:    */           
/* 492:545 */           Element numMesContrGenOtrEmpl = doc.createElement("numMesContrGenOtrEmpl");
/* 493:546 */           numMesContrGenOtrEmpl.appendChild(doc.createTextNode("" + relacionDependencia.getMesesTrabajadosContribucionOtroEmpleador()));
/* 494:547 */           contribucion.appendChild(numMesContrGenOtrEmpl);
/* 495:    */           
/* 496:549 */           Element totNumMesContrGen = doc.createElement("totNumMesContrGen");
/* 497:    */           
/* 498:551 */           long valorTotNumMesContrGen = relacionDependencia.getMesesTrabajadosContribucionEmpleador() + relacionDependencia.getMesesTrabajadosContribucionOtroEmpleador();
/* 499:552 */           totNumMesContrGen.appendChild(doc.createTextNode("" + valorTotNumMesContrGen));
/* 500:553 */           contribucion.appendChild(totNumMesContrGen);
/* 501:    */           
/* 502:555 */           Element totContrGen = doc.createElement("totContrGen");
/* 503:556 */           BigDecimal valorTotContrGen = valorRemunMenPromContr.multiply(new BigDecimal(valorTotNumMesContrGen)).multiply(new BigDecimal(0.0333D)).setScale(2, RoundingMode.HALF_UP);
/* 504:557 */           totContrGen.appendChild(doc.createTextNode("" + valorTotContrGen));
/* 505:558 */           contribucion.appendChild(totContrGen);
/* 506:    */           
/* 507:560 */           Element credTribDonContrOtrEmpl = doc.createElement("credTribDonContrOtrEmpl");
/* 508:561 */           BigDecimal valorCredTribDonContrOtrEmpl = BigDecimal.ZERO;
/* 509:562 */           credTribDonContrOtrEmpl.appendChild(doc.createTextNode("" + valorCredTribDonContrOtrEmpl));
/* 510:563 */           contribucion.appendChild(credTribDonContrOtrEmpl);
/* 511:    */           
/* 512:565 */           Element credTribDonContrEstEmpl = doc.createElement("credTribDonContrEstEmpl");
/* 513:566 */           BigDecimal valorCredTribDonContrEstEmpl = BigDecimal.ZERO;
/* 514:567 */           credTribDonContrEstEmpl.appendChild(doc.createTextNode("" + valorCredTribDonContrEstEmpl));
/* 515:568 */           contribucion.appendChild(credTribDonContrEstEmpl);
/* 516:    */           
/* 517:570 */           Element credTribDonContrNOEstEmpl = doc.createElement("credTribDonContrNOEstEmpl");
/* 518:571 */           credTribDonContrNOEstEmpl.appendChild(doc.createTextNode("0.00"));
/* 519:572 */           contribucion.appendChild(credTribDonContrNOEstEmpl);
/* 520:    */           
/* 521:574 */           Element totCredTribDonContr = doc.createElement("totCredTribDonContr");
/* 522:575 */           BigDecimal valorTotCredTribDonContr = valorCredTribDonContrOtrEmpl.add(valorCredTribDonContrEstEmpl);
/* 523:576 */           totCredTribDonContr.appendChild(doc.createTextNode("" + valorTotCredTribDonContr));
/* 524:577 */           contribucion.appendChild(totCredTribDonContr);
/* 525:    */           
/* 526:579 */           Element contrPag = doc.createElement("contrPag");
/* 527:    */           
/* 528:581 */           BigDecimal valorContrPag = valorTotContrGen.subtract(valorTotCredTribDonContr).compareTo(BigDecimal.ZERO) >= 0 ? valorTotContrGen.subtract(valorTotCredTribDonContr) : BigDecimal.ZERO;
/* 529:582 */           contrPag.appendChild(doc.createTextNode("" + valorContrPag));
/* 530:583 */           contribucion.appendChild(contrPag);
/* 531:    */           
/* 532:585 */           Element contrAsuOtrEmpl = doc.createElement("contrAsuOtrEmpl");
/* 533:586 */           contrAsuOtrEmpl.appendChild(doc.createTextNode("0.00"));
/* 534:587 */           contribucion.appendChild(contrAsuOtrEmpl);
/* 535:    */           
/* 536:589 */           Element contrRetOtrEmpl = doc.createElement("contrRetOtrEmpl");
/* 537:590 */           contrRetOtrEmpl.appendChild(doc.createTextNode("0.00"));
/* 538:591 */           contribucion.appendChild(contrRetOtrEmpl);
/* 539:    */           
/* 540:593 */           Element contrAsuEstEmpl = doc.createElement("contrAsuEstEmpl");
/* 541:594 */           contrAsuEstEmpl.appendChild(doc.createTextNode("0.00"));
/* 542:595 */           contribucion.appendChild(contrAsuEstEmpl);
/* 543:    */           
/* 544:597 */           Element contrRetEstEmpl = doc.createElement("contrRetEstEmpl");
/* 545:598 */           contrRetEstEmpl.appendChild(doc.createTextNode("" + relacionDependencia.getContribucionRetenida()));
/* 546:599 */           contribucion.appendChild(contrRetEstEmpl);
/* 547:    */         }
/* 548:    */       }
/* 549:    */     }
/* 550:    */   }
/* 551:    */   
/* 552:    */   public int getAnio()
/* 553:    */   {
/* 554:653 */     return this.anio;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void setAnio(int anio)
/* 558:    */   {
/* 559:663 */     this.anio = anio;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public List<RelacionDependencia> getListaRelacionDependencia()
/* 563:    */   {
/* 564:672 */     return this.listaRelacionDependencia;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setListaRelacionDependencia(List<RelacionDependencia> listaRelacionDependencia)
/* 568:    */   {
/* 569:682 */     this.listaRelacionDependencia = listaRelacionDependencia;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public StreamedContent getFile()
/* 573:    */   {
/* 574:    */     try
/* 575:    */     {
/* 576:692 */       this.file = generarRDEP();
/* 577:    */     }
/* 578:    */     catch (ParserConfigurationException pce)
/* 579:    */     {
/* 580:694 */       pce.printStackTrace();
/* 581:695 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 582:696 */       LOG.error("Error al generar el AT " + pce);
/* 583:    */     }
/* 584:    */     catch (TransformerException tfe)
/* 585:    */     {
/* 586:698 */       tfe.printStackTrace();
/* 587:699 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 588:700 */       LOG.error("Error al generar el AT " + tfe);
/* 589:    */     }
/* 590:    */     catch (FileNotFoundException e)
/* 591:    */     {
/* 592:702 */       e.printStackTrace();
/* 593:703 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado") + " " + e.getMessage());
/* 594:704 */       LOG.error("Archivo no encontrado " + e);
/* 595:    */     }
/* 596:    */     catch (ExcepcionAS2Nomina e)
/* 597:    */     {
/* 598:706 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 599:707 */       LOG.error("Archivo no encontrado " + e);
/* 600:    */     }
/* 601:    */     catch (Exception e)
/* 602:    */     {
/* 603:709 */       e.printStackTrace();
/* 604:710 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 605:711 */       LOG.error("Error al generar el AT " + e);
/* 606:    */     }
/* 607:713 */     return this.file;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public void setFile(StreamedContent file)
/* 611:    */   {
/* 612:723 */     this.file = file;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public boolean isProcessed()
/* 616:    */   {
/* 617:727 */     return this.processed;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setProcessed(boolean processed)
/* 621:    */   {
/* 622:731 */     this.processed = processed;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public int getMes()
/* 626:    */   {
/* 627:738 */     return this.mes;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void setMes(int mes)
/* 631:    */   {
/* 632:746 */     this.mes = mes;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public List<SelectItem> getListaMes()
/* 636:    */   {
/* 637:755 */     if (this.listaMes == null)
/* 638:    */     {
/* 639:756 */       this.listaMes = new ArrayList();
/* 640:757 */       for (Mes t : Mes.values())
/* 641:    */       {
/* 642:758 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 643:759 */         this.listaMes.add(item);
/* 644:    */       }
/* 645:    */     }
/* 646:762 */     return this.listaMes;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setListaMes(List<SelectItem> listaMes)
/* 650:    */   {
/* 651:772 */     this.listaMes = listaMes;
/* 652:    */   }
/* 653:    */   
/* 654:    */   public boolean isAcumulado()
/* 655:    */   {
/* 656:779 */     return this.acumulado;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void setAcumulado(boolean acumulado)
/* 660:    */   {
/* 661:787 */     this.acumulado = acumulado;
/* 662:    */   }
/* 663:    */   
/* 664:    */   public BigDecimal getTotalSueldoSalario()
/* 665:    */   {
/* 666:794 */     return this.totalSueldoSalario;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void setTotalSueldoSalario(BigDecimal totalSueldoSalario)
/* 670:    */   {
/* 671:802 */     this.totalSueldoSalario = totalSueldoSalario;
/* 672:    */   }
/* 673:    */   
/* 674:    */   public BigDecimal getTotalSobreSueldo()
/* 675:    */   {
/* 676:809 */     return this.totalSobreSueldo;
/* 677:    */   }
/* 678:    */   
/* 679:    */   public void setTotalSobreSueldo(BigDecimal totalSobreSueldo)
/* 680:    */   {
/* 681:817 */     this.totalSobreSueldo = totalSobreSueldo;
/* 682:    */   }
/* 683:    */   
/* 684:    */   public BigDecimal getTotalSueldoSalarioOtro()
/* 685:    */   {
/* 686:824 */     return this.totalSueldoSalarioOtro;
/* 687:    */   }
/* 688:    */   
/* 689:    */   public void setTotalSueldoSalarioOtro(BigDecimal totalSueldoSalarioOtro)
/* 690:    */   {
/* 691:832 */     this.totalSueldoSalarioOtro = totalSueldoSalarioOtro;
/* 692:    */   }
/* 693:    */   
/* 694:    */   public BigDecimal getTotalDecimoTercero()
/* 695:    */   {
/* 696:839 */     return this.totalDecimoTercero;
/* 697:    */   }
/* 698:    */   
/* 699:    */   public void setTotalDecimoTercero(BigDecimal totalDecimoTercero)
/* 700:    */   {
/* 701:847 */     this.totalDecimoTercero = totalDecimoTercero;
/* 702:    */   }
/* 703:    */   
/* 704:    */   public BigDecimal getTotalDecimoCuarto()
/* 705:    */   {
/* 706:854 */     return this.totalDecimoCuarto;
/* 707:    */   }
/* 708:    */   
/* 709:    */   public void setTotalDecimoCuarto(BigDecimal totalDecimoCuarto)
/* 710:    */   {
/* 711:862 */     this.totalDecimoCuarto = totalDecimoCuarto;
/* 712:    */   }
/* 713:    */   
/* 714:    */   public BigDecimal getTotalUtilidades()
/* 715:    */   {
/* 716:869 */     return this.totalUtilidades;
/* 717:    */   }
/* 718:    */   
/* 719:    */   public void setTotalUtilidades(BigDecimal totalUtilidades)
/* 720:    */   {
/* 721:877 */     this.totalUtilidades = totalUtilidades;
/* 722:    */   }
/* 723:    */   
/* 724:    */   public BigDecimal getTotalIess()
/* 725:    */   {
/* 726:884 */     return this.totalIess;
/* 727:    */   }
/* 728:    */   
/* 729:    */   public void setTotalIess(BigDecimal totalIess)
/* 730:    */   {
/* 731:892 */     this.totalIess = totalIess;
/* 732:    */   }
/* 733:    */   
/* 734:    */   public BigDecimal getTotalIessOtro()
/* 735:    */   {
/* 736:899 */     return this.totalIessOtro;
/* 737:    */   }
/* 738:    */   
/* 739:    */   public void setTotalIessOtro(BigDecimal totalIessOtro)
/* 740:    */   {
/* 741:907 */     this.totalIessOtro = totalIessOtro;
/* 742:    */   }
/* 743:    */   
/* 744:    */   public BigDecimal getTotalBaseImponible()
/* 745:    */   {
/* 746:914 */     return this.totalBaseImponible;
/* 747:    */   }
/* 748:    */   
/* 749:    */   public void setTotalBaseImponible(BigDecimal totalBaseImponible)
/* 750:    */   {
/* 751:922 */     this.totalBaseImponible = totalBaseImponible;
/* 752:    */   }
/* 753:    */   
/* 754:    */   public BigDecimal getTotalValorRetener()
/* 755:    */   {
/* 756:929 */     return this.totalValorRetener;
/* 757:    */   }
/* 758:    */   
/* 759:    */   public void setTotalValorRetener(BigDecimal totalValorRetener)
/* 760:    */   {
/* 761:937 */     this.totalValorRetener = totalValorRetener;
/* 762:    */   }
/* 763:    */   
/* 764:    */   public BigDecimal getTotalValorRetenido()
/* 765:    */   {
/* 766:944 */     return this.totalValorRetenido;
/* 767:    */   }
/* 768:    */   
/* 769:    */   public void setTotalValorRetenido(BigDecimal totalValorRetenido)
/* 770:    */   {
/* 771:952 */     this.totalValorRetenido = totalValorRetenido;
/* 772:    */   }
/* 773:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.RelacionDependenciaBean
 * JD-Core Version:    0.7.0.1
 */