/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageController;
/*   4:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   5:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   6:    */ import java.io.File;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Calendar;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import java.util.TreeMap;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import javax.faces.model.SelectItem;
/*  17:    */ import javax.xml.parsers.DocumentBuilder;
/*  18:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  19:    */ import javax.xml.parsers.ParserConfigurationException;
/*  20:    */ import javax.xml.transform.Result;
/*  21:    */ import javax.xml.transform.Transformer;
/*  22:    */ import javax.xml.transform.TransformerException;
/*  23:    */ import javax.xml.transform.TransformerFactory;
/*  24:    */ import javax.xml.transform.dom.DOMSource;
/*  25:    */ import javax.xml.transform.stream.StreamResult;
/*  26:    */ import org.w3c.dom.Document;
/*  27:    */ import org.w3c.dom.Element;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class Declaracion103Bean
/*  32:    */   extends PageController
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -2418549264640764020L;
/*  35: 56 */   private int anio = Calendar.getInstance().get(1);
/*  36: 57 */   private int mes = Calendar.getInstance().get(2);
/*  37:    */   private List<SelectItem> listaMes;
/*  38:    */   
/*  39:    */   public void generar103()
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 65 */       String strMes = (this.mes < 10 ? "0" : "") + this.mes;
/*  44:    */       
/*  45: 67 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/*  46: 68 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/*  47:    */       
/*  48:    */ 
/*  49: 71 */       Document doc = docBuilder.newDocument();
/*  50:    */       
/*  51: 73 */       Element formulario = doc.createElement("formulario");
/*  52: 74 */       doc.appendChild(formulario);
/*  53:    */       
/*  54:    */ 
/*  55: 77 */       formulario.setAttribute("version", "0.2");
/*  56:    */       
/*  57:    */ 
/*  58: 80 */       cabecera(doc, formulario);
/*  59:    */       
/*  60:    */ 
/*  61: 83 */       detalle(strMes, this.anio, doc, formulario);
/*  62:    */       
/*  63:    */ 
/*  64: 86 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*  65: 87 */       Transformer transformer = transformerFactory.newTransformer();
/*  66:    */       
/*  67: 89 */       DOMSource source = new DOMSource(doc);
/*  68:    */       
/*  69: 91 */       String nombreArchivo = "103ORI_MES" + this.anio + ".xml";
/*  70: 92 */       Result result = new StreamResult(new File("D:\\" + nombreArchivo));
/*  71:    */       
/*  72: 94 */       transformer.transform(source, result);
/*  73:    */     }
/*  74:    */     catch (ParserConfigurationException pce)
/*  75:    */     {
/*  76: 97 */       pce.printStackTrace();
/*  77:    */     }
/*  78:    */     catch (TransformerException tfe)
/*  79:    */     {
/*  80: 99 */       tfe.printStackTrace();
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private void cabecera(Document doc, Element formulario)
/*  85:    */   {
/*  86:112 */     Element cabecera = doc.createElement("cabecera");
/*  87:113 */     formulario.appendChild(cabecera);
/*  88:    */     
/*  89:    */ 
/*  90:116 */     Element codigo_version_formulario = doc.createElement("codigo_version_formulario");
/*  91:117 */     codigo_version_formulario.appendChild(doc.createTextNode("03201101"));
/*  92:118 */     cabecera.appendChild(codigo_version_formulario);
/*  93:    */     
/*  94:    */ 
/*  95:121 */     Element ruc = doc.createElement("ruc");
/*  96:122 */     ruc.appendChild(doc.createTextNode("1713263547001"));
/*  97:123 */     cabecera.appendChild(ruc);
/*  98:    */     
/*  99:    */ 
/* 100:126 */     Element codigo_moneda = doc.createElement("codigo_moneda");
/* 101:127 */     codigo_moneda.appendChild(doc.createTextNode("1"));
/* 102:128 */     cabecera.appendChild(codigo_moneda);
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void detalle(String strMes, int anio, Document doc, Element formulario)
/* 106:    */   {
/* 107:141 */     Element detalle = doc.createElement("detalle");
/* 108:142 */     formulario.appendChild(detalle);
/* 109:    */     
/* 110:    */ 
/* 111:145 */     TreeMap<String, Campo103> detalle103 = getDetalle103();
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:149 */     Element campo = doc.createElement("campo");
/* 116:150 */     campo.setAttribute("numero", "31");
/* 117:151 */     campo.appendChild(doc.createTextNode("O"));
/* 118:152 */     detalle.appendChild(campo);
/* 119:    */     
/* 120:154 */     campo = doc.createElement("campo");
/* 121:155 */     campo.setAttribute("numero", "101");
/* 122:156 */     campo.appendChild(doc.createTextNode(strMes));
/* 123:157 */     detalle.appendChild(campo);
/* 124:    */     
/* 125:159 */     campo = doc.createElement("campo");
/* 126:160 */     campo.setAttribute("numero", "102");
/* 127:161 */     campo.appendChild(doc.createTextNode(String.valueOf(anio)));
/* 128:162 */     detalle.appendChild(campo);
/* 129:    */     
/* 130:164 */     campo = doc.createElement("campo");
/* 131:165 */     campo.setAttribute("numero", "104");
/* 132:166 */     detalle.appendChild(campo);
/* 133:    */     
/* 134:168 */     campo = doc.createElement("campo");
/* 135:169 */     campo.setAttribute("numero", "198");
/* 136:170 */     campo.appendChild(doc.createTextNode("1713263547"));
/* 137:171 */     detalle.appendChild(campo);
/* 138:    */     
/* 139:173 */     campo = doc.createElement("campo");
/* 140:174 */     campo.setAttribute("numero", "199");
/* 141:175 */     detalle.appendChild(campo);
/* 142:    */     
/* 143:177 */     campo = doc.createElement("campo");
/* 144:178 */     campo.setAttribute("numero", "201");
/* 145:179 */     campo.appendChild(doc.createTextNode("1713263547001"));
/* 146:180 */     detalle.appendChild(campo);
/* 147:    */     
/* 148:182 */     campo = doc.createElement("campo");
/* 149:183 */     campo.setAttribute("numero", "202");
/* 150:184 */     campo.appendChild(doc.createTextNode("ASINFO"));
/* 151:185 */     detalle.appendChild(campo);
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:189 */     List<DetalleFacturaProveedorSRI> lista = new ArrayList();
/* 156:191 */     for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : lista)
/* 157:    */     {
/* 158:192 */       Campo103 nodoDeclaracion = (Campo103)detalle103.get(detalleFacturaProveedorSRI.getCodigoConceptoRetencionSRI());
/* 159:193 */       if (nodoDeclaracion != null)
/* 160:    */       {
/* 161:194 */         nodoDeclaracion.setValor(detalleFacturaProveedorSRI.getBaseImponibleRetencion());
/* 162:195 */         nodoDeclaracion.getCampoAsociado().setValor(detalleFacturaProveedorSRI.getValorRetencion());
/* 163:    */       }
/* 164:    */     }
/* 165:200 */     for (Object it = detalle103.keySet().iterator(); ((Iterator)it).hasNext();)
/* 166:    */     {
/* 167:201 */       String codigo = (String)((Iterator)it).next();
/* 168:    */       
/* 169:203 */       Campo103 campo103 = (Campo103)detalle103.get(codigo);
/* 170:204 */       BigDecimal valor = campo103.getValor();
/* 171:207 */       for (Campo103 sumandosCampo103 : campo103.getListaSumandos()) {
/* 172:208 */         valor = valor.add(sumandosCampo103.getValor());
/* 173:    */       }
/* 174:210 */       campo103.setValor(valor);
/* 175:    */     }
/* 176:215 */     ((Campo103)detalle103.get("907")).setValor(
/* 177:216 */       ((Campo103)detalle103.get("909")).getValor().add(((Campo103)detalle103.get("911")).getValor()).add(((Campo103)detalle103.get("913")).getValor())
/* 178:217 */       .add(((Campo103)detalle103.get("915")).getValor()));
/* 179:    */     
/* 180:219 */     ((Campo103)detalle103.get("905")).setValor(((Campo103)detalle103.get("999")).getValor().subtract(((Campo103)detalle103.get("907")).getValor()));
/* 181:222 */     for (Object it = detalle103.keySet().iterator(); ((Iterator)it).hasNext();)
/* 182:    */     {
/* 183:223 */       String codigo = (String)((Iterator)it).next();
/* 184:    */       
/* 185:225 */       Campo103 campo103 = (Campo103)detalle103.get(codigo);
/* 186:    */       
/* 187:227 */       Element elementBase = doc.createElement("campo");
/* 188:228 */       elementBase.setAttribute("numero", campo103.getCodigo());
/* 189:230 */       if (campo103.isCampoTexto())
/* 190:    */       {
/* 191:231 */         if (campo103.getStrValor() != null) {
/* 192:232 */           elementBase.appendChild(doc.createTextNode(campo103.getStrValor()));
/* 193:    */         }
/* 194:    */       }
/* 195:    */       else {
/* 196:235 */         elementBase.appendChild(doc.createTextNode(campo103.getValor().toString()));
/* 197:    */       }
/* 198:238 */       detalle.appendChild(elementBase);
/* 199:    */     }
/* 200:243 */     campo = doc.createElement("campo");
/* 201:244 */     campo.setAttribute("numero", "922");
/* 202:245 */     campo.appendChild(doc.createTextNode("10"));
/* 203:246 */     detalle.appendChild(campo);
/* 204:    */   }
/* 205:    */   
/* 206:    */   private TreeMap<String, Campo103> getDetalle103()
/* 207:    */   {
/* 208:257 */     TreeMap<String, Campo103> detalle103 = new TreeMap();
/* 209:    */     
/* 210:259 */     Campo103 nodo302 = new Campo103("302");
/* 211:260 */     Campo103 nodo352 = new Campo103("352", nodo302);
/* 212:261 */     detalle103.put(nodo302.getCodigo(), nodo302);
/* 213:262 */     detalle103.put(nodo352.getCodigo(), nodo352);
/* 214:    */     
/* 215:264 */     Campo103 nodo353 = new Campo103("353");
/* 216:265 */     Campo103 nodo303 = new Campo103("303", nodo353);
/* 217:266 */     detalle103.put(nodo303.getCodigo(), nodo303);
/* 218:267 */     detalle103.put(nodo353.getCodigo(), nodo353);
/* 219:    */     
/* 220:269 */     Campo103 nodo354 = new Campo103("354");
/* 221:270 */     Campo103 nodo304 = new Campo103("304", nodo354);
/* 222:271 */     detalle103.put(nodo304.getCodigo(), nodo304);
/* 223:272 */     detalle103.put(nodo354.getCodigo(), nodo354);
/* 224:    */     
/* 225:274 */     Campo103 nodo357 = new Campo103("357");
/* 226:275 */     Campo103 nodo307 = new Campo103("307", nodo357);
/* 227:276 */     detalle103.put(nodo307.getCodigo(), nodo307);
/* 228:277 */     detalle103.put(nodo357.getCodigo(), nodo357);
/* 229:    */     
/* 230:279 */     Campo103 nodo358 = new Campo103("358");
/* 231:280 */     Campo103 nodo308 = new Campo103("308", nodo358);
/* 232:281 */     detalle103.put(nodo358.getCodigo(), nodo308);
/* 233:    */     
/* 234:283 */     Campo103 nodo359 = new Campo103("359");
/* 235:284 */     Campo103 nodo309 = new Campo103("309", nodo359);
/* 236:285 */     detalle103.put(nodo309.getCodigo(), nodo309);
/* 237:286 */     detalle103.put(nodo359.getCodigo(), nodo359);
/* 238:    */     
/* 239:288 */     Campo103 nodo360 = new Campo103("360");
/* 240:289 */     Campo103 nodo310 = new Campo103("310", nodo360);
/* 241:290 */     detalle103.put(nodo310.getCodigo(), nodo310);
/* 242:291 */     detalle103.put(nodo360.getCodigo(), nodo360);
/* 243:    */     
/* 244:293 */     Campo103 nodo362 = new Campo103("362");
/* 245:294 */     Campo103 nodo312 = new Campo103("312", nodo362);
/* 246:295 */     detalle103.put(nodo312.getCodigo(), nodo312);
/* 247:296 */     detalle103.put(nodo362.getCodigo(), nodo362);
/* 248:    */     
/* 249:298 */     Campo103 nodo369 = new Campo103("369");
/* 250:299 */     Campo103 nodo319 = new Campo103("319", nodo369);
/* 251:300 */     detalle103.put(nodo319.getCodigo(), nodo319);
/* 252:301 */     detalle103.put(nodo369.getCodigo(), nodo369);
/* 253:    */     
/* 254:303 */     Campo103 nodo370 = new Campo103("370");
/* 255:304 */     Campo103 nodo320 = new Campo103("320", nodo370);
/* 256:305 */     detalle103.put(nodo320.getCodigo(), nodo320);
/* 257:306 */     detalle103.put(nodo370.getCodigo(), nodo370);
/* 258:    */     
/* 259:308 */     Campo103 nodo372 = new Campo103("372");
/* 260:309 */     Campo103 nodo322 = new Campo103("322", nodo372);
/* 261:310 */     detalle103.put(nodo322.getCodigo(), nodo322);
/* 262:311 */     detalle103.put(nodo372.getCodigo(), nodo372);
/* 263:    */     
/* 264:313 */     Campo103 nodo373 = new Campo103("373");
/* 265:314 */     Campo103 nodo323 = new Campo103("323", nodo373);
/* 266:315 */     detalle103.put(nodo323.getCodigo(), nodo323);
/* 267:316 */     detalle103.put(nodo373.getCodigo(), nodo373);
/* 268:    */     
/* 269:318 */     Campo103 nodo375 = new Campo103("375");
/* 270:319 */     Campo103 nodo325 = new Campo103("325", nodo375);
/* 271:320 */     detalle103.put(nodo325.getCodigo(), nodo325);
/* 272:321 */     detalle103.put(nodo375.getCodigo(), nodo375);
/* 273:    */     
/* 274:323 */     Campo103 nodo377 = new Campo103("377");
/* 275:324 */     Campo103 nodo327 = new Campo103("327", nodo373);
/* 276:325 */     detalle103.put(nodo327.getCodigo(), nodo327);
/* 277:326 */     detalle103.put(nodo377.getCodigo(), nodo377);
/* 278:    */     
/* 279:328 */     Campo103 nodo378 = new Campo103("378");
/* 280:329 */     Campo103 nodo328 = new Campo103("328", nodo378);
/* 281:330 */     detalle103.put(nodo328.getCodigo(), nodo328);
/* 282:331 */     detalle103.put(nodo378.getCodigo(), nodo378);
/* 283:    */     
/* 284:333 */     Campo103 nodo332 = new Campo103("332");
/* 285:334 */     detalle103.put(nodo332.getCodigo(), nodo332);
/* 286:    */     
/* 287:336 */     Campo103 nodo390 = new Campo103("390");
/* 288:337 */     Campo103 nodo340 = new Campo103("340", nodo390);
/* 289:338 */     detalle103.put(nodo340.getCodigo(), nodo340);
/* 290:339 */     detalle103.put(nodo390.getCodigo(), nodo390);
/* 291:    */     
/* 292:341 */     Campo103 nodo391 = new Campo103("391");
/* 293:342 */     Campo103 nodo341 = new Campo103("341", nodo391);
/* 294:343 */     detalle103.put(nodo341.getCodigo(), nodo341);
/* 295:344 */     detalle103.put(nodo391.getCodigo(), nodo391);
/* 296:    */     
/* 297:346 */     Campo103 nodo392 = new Campo103("392");
/* 298:347 */     Campo103 nodo342 = new Campo103("342", nodo392);
/* 299:348 */     detalle103.put(nodo342.getCodigo(), nodo342);
/* 300:349 */     detalle103.put(nodo392.getCodigo(), nodo392);
/* 301:    */     
/* 302:351 */     Campo103 nodo393 = new Campo103("393");
/* 303:352 */     Campo103 nodo343 = new Campo103("343", nodo393);
/* 304:353 */     detalle103.put(nodo343.getCodigo(), nodo343);
/* 305:354 */     detalle103.put(nodo393.getCodigo(), nodo393);
/* 306:    */     
/* 307:356 */     Campo103 nodo394 = new Campo103("394");
/* 308:357 */     Campo103 nodo344 = new Campo103("344", nodo394);
/* 309:358 */     detalle103.put(nodo344.getCodigo(), nodo344);
/* 310:359 */     detalle103.put(nodo394.getCodigo(), nodo394);
/* 311:    */     
/* 312:361 */     Campo103 nodo399 = new Campo103("399");
/* 313:362 */     Campo103 nodo349 = new Campo103("349", nodo399);
/* 314:363 */     detalle103.put(nodo349.getCodigo(), nodo349);
/* 315:364 */     detalle103.put(nodo399.getCodigo(), nodo399);
/* 316:    */     
/* 317:366 */     nodo349.getListaSumandos().add(nodo302);
/* 318:367 */     nodo349.getListaSumandos().add(nodo303);
/* 319:368 */     nodo349.getListaSumandos().add(nodo304);
/* 320:369 */     nodo349.getListaSumandos().add(nodo307);
/* 321:370 */     nodo349.getListaSumandos().add(nodo308);
/* 322:371 */     nodo349.getListaSumandos().add(nodo309);
/* 323:372 */     nodo349.getListaSumandos().add(nodo310);
/* 324:373 */     nodo349.getListaSumandos().add(nodo312);
/* 325:374 */     nodo349.getListaSumandos().add(nodo319);
/* 326:375 */     nodo349.getListaSumandos().add(nodo320);
/* 327:376 */     nodo349.getListaSumandos().add(nodo322);
/* 328:377 */     nodo349.getListaSumandos().add(nodo323);
/* 329:378 */     nodo349.getListaSumandos().add(nodo325);
/* 330:379 */     nodo349.getListaSumandos().add(nodo327);
/* 331:380 */     nodo349.getListaSumandos().add(nodo328);
/* 332:381 */     nodo349.getListaSumandos().add(nodo332);
/* 333:382 */     nodo349.getListaSumandos().add(nodo340);
/* 334:383 */     nodo349.getListaSumandos().add(nodo341);
/* 335:384 */     nodo349.getListaSumandos().add(nodo342);
/* 336:385 */     nodo349.getListaSumandos().add(nodo343);
/* 337:386 */     nodo349.getListaSumandos().add(nodo344);
/* 338:    */     
/* 339:388 */     nodo399.getListaSumandos().add(nodo352);
/* 340:389 */     nodo399.getListaSumandos().add(nodo353);
/* 341:390 */     nodo399.getListaSumandos().add(nodo354);
/* 342:391 */     nodo399.getListaSumandos().add(nodo357);
/* 343:392 */     nodo399.getListaSumandos().add(nodo358);
/* 344:393 */     nodo399.getListaSumandos().add(nodo359);
/* 345:394 */     nodo399.getListaSumandos().add(nodo360);
/* 346:395 */     nodo399.getListaSumandos().add(nodo362);
/* 347:396 */     nodo399.getListaSumandos().add(nodo369);
/* 348:397 */     nodo399.getListaSumandos().add(nodo370);
/* 349:398 */     nodo399.getListaSumandos().add(nodo372);
/* 350:399 */     nodo399.getListaSumandos().add(nodo373);
/* 351:400 */     nodo399.getListaSumandos().add(nodo375);
/* 352:401 */     nodo399.getListaSumandos().add(nodo377);
/* 353:402 */     nodo399.getListaSumandos().add(nodo378);
/* 354:403 */     nodo399.getListaSumandos().add(nodo390);
/* 355:404 */     nodo399.getListaSumandos().add(nodo391);
/* 356:405 */     nodo399.getListaSumandos().add(nodo392);
/* 357:406 */     nodo399.getListaSumandos().add(nodo393);
/* 358:407 */     nodo399.getListaSumandos().add(nodo394);
/* 359:    */     
/* 360:409 */     Campo103 nodo451 = new Campo103("451");
/* 361:410 */     Campo103 nodo401 = new Campo103("401", nodo451);
/* 362:411 */     detalle103.put(nodo401.getCodigo(), nodo401);
/* 363:412 */     detalle103.put(nodo451.getCodigo(), nodo451);
/* 364:    */     
/* 365:414 */     Campo103 nodo453 = new Campo103("453");
/* 366:415 */     Campo103 nodo403 = new Campo103("403", nodo453);
/* 367:416 */     detalle103.put(nodo403.getCodigo(), nodo403);
/* 368:417 */     detalle103.put(nodo453.getCodigo(), nodo453);
/* 369:    */     
/* 370:419 */     Campo103 nodo455 = new Campo103("455");
/* 371:420 */     Campo103 nodo405 = new Campo103("405", nodo455);
/* 372:421 */     detalle103.put(nodo405.getCodigo(), nodo405);
/* 373:422 */     detalle103.put(nodo455.getCodigo(), nodo455);
/* 374:    */     
/* 375:424 */     Campo103 nodo471 = new Campo103("471");
/* 376:425 */     Campo103 nodo421 = new Campo103("421", nodo471);
/* 377:426 */     detalle103.put(nodo421.getCodigo(), nodo421);
/* 378:427 */     detalle103.put(nodo471.getCodigo(), nodo471);
/* 379:    */     
/* 380:429 */     Campo103 nodo427 = new Campo103("427");
/* 381:430 */     detalle103.put(nodo427.getCodigo(), nodo427);
/* 382:    */     
/* 383:432 */     Campo103 nodo429 = new Campo103("429");
/* 384:433 */     detalle103.put(nodo429.getCodigo(), nodo429);
/* 385:434 */     nodo429.getListaSumandos().add(nodo401);
/* 386:435 */     nodo429.getListaSumandos().add(nodo403);
/* 387:436 */     nodo429.getListaSumandos().add(nodo405);
/* 388:437 */     nodo429.getListaSumandos().add(nodo421);
/* 389:438 */     nodo429.getListaSumandos().add(nodo427);
/* 390:    */     
/* 391:440 */     Campo103 nodo498 = new Campo103("498");
/* 392:441 */     detalle103.put(nodo498.getCodigo(), nodo498);
/* 393:442 */     nodo498.getListaSumandos().add(nodo451);
/* 394:443 */     nodo498.getListaSumandos().add(nodo453);
/* 395:444 */     nodo498.getListaSumandos().add(nodo455);
/* 396:445 */     nodo498.getListaSumandos().add(nodo471);
/* 397:    */     
/* 398:447 */     Campo103 nodo499 = new Campo103("499");
/* 399:448 */     detalle103.put(nodo499.getCodigo(), nodo499);
/* 400:449 */     nodo499.getListaSumandos().add(nodo399);
/* 401:450 */     nodo499.getListaSumandos().add(nodo498);
/* 402:    */     
/* 403:452 */     Campo103 nodo890 = new Campo103("890");
/* 404:453 */     detalle103.put(nodo890.getCodigo(), nodo890);
/* 405:    */     
/* 406:455 */     Campo103 nodo897 = new Campo103("897");
/* 407:456 */     detalle103.put(nodo897.getCodigo(), nodo897);
/* 408:    */     
/* 409:458 */     Campo103 nodo898 = new Campo103("898");
/* 410:459 */     detalle103.put(nodo898.getCodigo(), nodo898);
/* 411:    */     
/* 412:461 */     Campo103 nodo899 = new Campo103("899");
/* 413:462 */     detalle103.put(nodo899.getCodigo(), nodo899);
/* 414:    */     
/* 415:464 */     Campo103 nodo902 = new Campo103("902");
/* 416:465 */     detalle103.put(nodo902.getCodigo(), nodo902);
/* 417:466 */     nodo902.getListaSumandos().add(nodo499);
/* 418:467 */     nodo902.getListaSumandos().add(nodo897);
/* 419:    */     
/* 420:469 */     Campo103 nodo903 = new Campo103("903");
/* 421:470 */     detalle103.put(nodo903.getCodigo(), nodo903);
/* 422:    */     
/* 423:472 */     Campo103 nodo904 = new Campo103("904");
/* 424:473 */     detalle103.put(nodo904.getCodigo(), nodo904);
/* 425:    */     
/* 426:475 */     Campo103 nodo905 = new Campo103("905");
/* 427:476 */     detalle103.put(nodo905.getCodigo(), nodo905);
/* 428:    */     
/* 429:478 */     Campo103 nodo907 = new Campo103("907");
/* 430:479 */     detalle103.put(nodo907.getCodigo(), nodo907);
/* 431:    */     
/* 432:481 */     Campo103 nodo908 = new Campo103("908");
/* 433:482 */     nodo908.setCampoTexto(true);
/* 434:483 */     detalle103.put(nodo908.getCodigo(), nodo908);
/* 435:    */     
/* 436:485 */     Campo103 nodo909 = new Campo103("909");
/* 437:486 */     detalle103.put(nodo909.getCodigo(), nodo909);
/* 438:    */     
/* 439:488 */     Campo103 nodo910 = new Campo103("910");
/* 440:489 */     nodo910.setCampoTexto(true);
/* 441:490 */     detalle103.put(nodo910.getCodigo(), nodo910);
/* 442:    */     
/* 443:492 */     Campo103 nodo911 = new Campo103("911");
/* 444:493 */     detalle103.put(nodo911.getCodigo(), nodo911);
/* 445:    */     
/* 446:495 */     Campo103 nodo912 = new Campo103("912");
/* 447:496 */     nodo912.setCampoTexto(true);
/* 448:497 */     detalle103.put(nodo912.getCodigo(), nodo912);
/* 449:    */     
/* 450:499 */     Campo103 nodo913 = new Campo103("913");
/* 451:500 */     detalle103.put(nodo913.getCodigo(), nodo913);
/* 452:    */     
/* 453:502 */     Campo103 nodo914 = new Campo103("914");
/* 454:503 */     nodo914.setCampoTexto(true);
/* 455:504 */     detalle103.put(nodo914.getCodigo(), nodo914);
/* 456:    */     
/* 457:506 */     Campo103 nodo915 = new Campo103("915");
/* 458:507 */     detalle103.put(nodo915.getCodigo(), nodo915);
/* 459:    */     
/* 460:509 */     Campo103 nodo921 = new Campo103("921");
/* 461:510 */     detalle103.put(nodo921.getCodigo(), nodo921);
/* 462:    */     
/* 463:512 */     Campo103 nodo999 = new Campo103("999");
/* 464:513 */     detalle103.put(nodo999.getCodigo(), nodo999);
/* 465:514 */     nodo999.getListaSumandos().add(nodo902);
/* 466:515 */     nodo999.getListaSumandos().add(nodo903);
/* 467:516 */     nodo999.getListaSumandos().add(nodo904);
/* 468:    */     
/* 469:518 */     return detalle103;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public List<SelectItem> getListaMes()
/* 473:    */   {
/* 474:527 */     if (this.listaMes == null)
/* 475:    */     {
/* 476:528 */       this.listaMes = new ArrayList();
/* 477:529 */       for (Mes t : Mes.values())
/* 478:    */       {
/* 479:530 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 480:531 */         this.listaMes.add(item);
/* 481:    */       }
/* 482:    */     }
/* 483:535 */     return this.listaMes;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setListaMes(List<SelectItem> listaMes)
/* 487:    */   {
/* 488:545 */     this.listaMes = listaMes;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public int getMes()
/* 492:    */   {
/* 493:555 */     return this.mes;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setMes(int mes)
/* 497:    */   {
/* 498:565 */     this.mes = mes;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public int getAnio()
/* 502:    */   {
/* 503:574 */     return this.anio;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setAnio(int anio)
/* 507:    */   {
/* 508:584 */     this.anio = anio;
/* 509:    */   }
/* 510:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.Declaracion103Bean
 * JD-Core Version:    0.7.0.1
 */