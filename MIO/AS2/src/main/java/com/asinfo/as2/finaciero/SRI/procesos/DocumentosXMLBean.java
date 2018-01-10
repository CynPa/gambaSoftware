/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.entities.DespachoCliente;
/*   8:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  12:    */ import com.asinfo.as2.entities.GuiaRemision;
/*  13:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  16:    */ import com.asinfo.as2.entities.Producto;
/*  17:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  18:    */ import com.asinfo.as2.entities.Sucursal;
/*  19:    */ import com.asinfo.as2.entities.Ubicacion;
/*  20:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  21:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  25:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  26:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  27:    */ import java.io.File;
/*  28:    */ import java.math.BigDecimal;
/*  29:    */ import java.math.RoundingMode;
/*  30:    */ import java.text.SimpleDateFormat;
/*  31:    */ import java.util.ArrayList;
/*  32:    */ import java.util.Arrays;
/*  33:    */ import java.util.Date;
/*  34:    */ import java.util.HashMap;
/*  35:    */ import java.util.List;
/*  36:    */ import java.util.Map;
/*  37:    */ import javax.ejb.EJB;
/*  38:    */ import javax.faces.bean.ManagedBean;
/*  39:    */ import javax.faces.bean.ViewScoped;
/*  40:    */ import javax.faces.model.SelectItem;
/*  41:    */ import javax.xml.parsers.DocumentBuilder;
/*  42:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  43:    */ import javax.xml.transform.Result;
/*  44:    */ import javax.xml.transform.Transformer;
/*  45:    */ import javax.xml.transform.TransformerFactory;
/*  46:    */ import javax.xml.transform.dom.DOMSource;
/*  47:    */ import javax.xml.transform.stream.StreamResult;
/*  48:    */ import org.w3c.dom.Document;
/*  49:    */ import org.w3c.dom.Element;
/*  50:    */ 
/*  51:    */ @ManagedBean
/*  52:    */ @ViewScoped
/*  53:    */ public class DocumentosXMLBean
/*  54:    */   extends PageControllerAS2
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 6502107917491200540L;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioSucursal servicioSucursal;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  63:    */   private Date fechaDesde;
/*  64:    */   private Date fechaHasta;
/*  65:    */   private List<Sucursal> listaSucursal;
/*  66:    */   private Sucursal sucursal;
/*  67:    */   private PuntoDeVenta puntoDeVenta;
/*  68:    */   private DocumentoBase documentoBase;
/*  69:    */   private SelectItem[] listaDocumentoBaseItem;
/*  70:    */   
/*  71:    */   public String generarArchivo()
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75: 80 */       formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/*  76: 81 */       List<FacturaCliente> listaFacturas = this.servicioFacturaCliente.obtenerFacturasMes(this.fechaDesde, this.fechaHasta, this.sucursal, this.puntoDeVenta, 
/*  77: 82 */         AppUtil.getOrganizacion().getIdOrganizacion(), this.documentoBase, true);
/*  78:    */       
/*  79: 84 */       idClienteConsumidorFinal = ParametrosSistema.getClienteGenerico(AppUtil.getOrganizacion().getIdOrganizacion());
/*  80: 85 */       indicadorConsumidorFinal = false;
/*  81: 86 */       for (FacturaCliente facturaCliente : listaFacturas)
/*  82:    */       {
/*  83: 88 */         indicadorConsumidorFinal = false;
/*  84: 89 */         if ((idClienteConsumidorFinal != null) && (idClienteConsumidorFinal.intValue() == facturaCliente.getEmpresa().getIdEmpresa())) {
/*  85: 90 */           indicadorConsumidorFinal = true;
/*  86:    */         }
/*  87: 93 */         DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/*  88: 94 */         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/*  89:    */         
/*  90: 96 */         Document doc = docBuilder.newDocument();
/*  91:    */         Element factura;
/*  92:    */         Element factura;
/*  93: 99 */         if (this.documentoBase == DocumentoBase.FACTURA_CLIENTE) {
/*  94:100 */           factura = doc.createElement("factura");
/*  95:    */         } else {
/*  96:102 */           factura = doc.createElement("notaCredito");
/*  97:    */         }
/*  98:104 */         factura.setAttribute("version", "2_00");
/*  99:105 */         doc.appendChild(factura);
/* 100:106 */         Element informacionTributaria = doc.createElement("infoTributaria");
/* 101:107 */         factura.appendChild(informacionTributaria);
/* 102:    */         
/* 103:109 */         Element razonSocial = doc.createElement("razonSocial");
/* 104:110 */         razonSocial.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getRazonSocial().replace(".", "")));
/* 105:111 */         informacionTributaria.appendChild(razonSocial);
/* 106:    */         
/* 107:113 */         Element ruc = doc.createElement("ruc");
/* 108:114 */         ruc.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getIdentificacion().replace(".", "")));
/* 109:115 */         informacionTributaria.appendChild(ruc);
/* 110:    */         
/* 111:117 */         Element numAut = doc.createElement("numAut");
/* 112:118 */         numAut.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getAutorizacion()));
/* 113:119 */         informacionTributaria.appendChild(numAut);
/* 114:    */         
/* 115:121 */         Element codDoc = doc.createElement("codDoc");
/* 116:122 */         codDoc.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getFacturaCliente().getDocumento()
/* 117:123 */           .getTipoComprobanteSRI().getCodigoDocumentoElectronico()));
/* 118:124 */         informacionTributaria.appendChild(codDoc);
/* 119:    */         
/* 120:126 */         Element estab = doc.createElement("estab");
/* 121:127 */         estab.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getEstablecimiento()));
/* 122:128 */         informacionTributaria.appendChild(estab);
/* 123:    */         
/* 124:130 */         Element ptoEmi = doc.createElement("ptoEmi");
/* 125:131 */         ptoEmi.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getPuntoEmision()));
/* 126:132 */         informacionTributaria.appendChild(ptoEmi);
/* 127:    */         
/* 128:134 */         Element secuencial = doc.createElement("secuencial");
/* 129:135 */         secuencial.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getNumero()));
/* 130:136 */         informacionTributaria.appendChild(secuencial);
/* 131:    */         
/* 132:138 */         Element fechaAutorizacion = doc.createElement("fechaAutorizacion");
/* 133:139 */         fechaAutorizacion.appendChild(doc.createTextNode(formatoFecha.format(facturaCliente.getFacturaClienteSRI().getFechaAutorizacion())));
/* 134:140 */         informacionTributaria.appendChild(fechaAutorizacion);
/* 135:    */         
/* 136:142 */         Element caducidad = doc.createElement("caducidad");
/* 137:143 */         caducidad.appendChild(doc.createTextNode(formatoFecha.format(facturaCliente.getFacturaClienteSRI().getFechaCaducidad())));
/* 138:144 */         informacionTributaria.appendChild(caducidad);
/* 139:    */         
/* 140:146 */         Element fechaEmision = doc.createElement("fechaEmision");
/* 141:147 */         fechaEmision.appendChild(doc.createTextNode(formatoFecha.format(facturaCliente.getFacturaClienteSRI().getFechaEmision())));
/* 142:148 */         informacionTributaria.appendChild(fechaEmision);
/* 143:    */         
/* 144:150 */         Element dirMatriz = doc.createElement("dirMatriz");
/* 145:151 */         dirMatriz.appendChild(doc.createTextNode(AppUtil.getDireccionMatriz()));
/* 146:152 */         informacionTributaria.appendChild(dirMatriz);
/* 147:    */         
/* 148:154 */         Element razonSocialComprador = doc.createElement("razonSocialComprador");
/* 149:155 */         razonSocialComprador.appendChild(doc.createTextNode(facturaCliente.getEmpresa().getNombreFiscal()));
/* 150:156 */         informacionTributaria.appendChild(razonSocialComprador);
/* 151:158 */         if (!indicadorConsumidorFinal)
/* 152:    */         {
/* 153:160 */           Element rucCedulaComprador = doc.createElement("rucCedulaComprador");
/* 154:161 */           rucCedulaComprador.appendChild(doc.createTextNode(facturaCliente.getEmpresa().getIdentificacion()));
/* 155:162 */           informacionTributaria.appendChild(rucCedulaComprador);
/* 156:    */         }
/* 157:164 */         if ((facturaCliente.getDespachoCliente() != null) && (facturaCliente.getDespachoCliente().getGuiaRemision() != null))
/* 158:    */         {
/* 159:166 */           Element guiaRemision = doc.createElement("guiaRemision");
/* 160:167 */           guiaRemision.appendChild(doc.createTextNode(facturaCliente.getDespachoCliente().getGuiaRemision().getNumero()));
/* 161:168 */           informacionTributaria.appendChild(guiaRemision);
/* 162:    */         }
/* 163:171 */         Element contribuyenteEspecial = doc.createElement("contribuyenteEspecial");
/* 164:172 */         contribuyenteEspecial.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/* 165:173 */           .getNumeroResolucionContribuyente()));
/* 166:174 */         informacionTributaria.appendChild(contribuyenteEspecial);
/* 167:175 */         if (AppUtil.getOrganizacion().getOrganizacionConfiguracion().isIndicadorObligadoContabilidad())
/* 168:    */         {
/* 169:177 */           Element obligado = doc.createElement("obligado");
/* 170:178 */           obligado.appendChild(doc.createTextNode("Obligado a Llevar Contabilidad"));
/* 171:179 */           informacionTributaria.appendChild(obligado);
/* 172:    */         }
/* 173:182 */         if (!this.documentoBase.equals(DocumentoBase.FACTURA_CLIENTE))
/* 174:    */         {
/* 175:183 */           Element codDocModificado = doc.createElement("codDocModificado");
/* 176:184 */           codDocModificado.appendChild(doc.createTextNode(facturaCliente.getFacturaClientePadre().getDocumento().getTipoComprobanteSRI()
/* 177:185 */             .getCodigoDocumentoElectronico()));
/* 178:186 */           informacionTributaria.appendChild(codDocModificado);
/* 179:187 */           Element numDocModificado = doc.createElement("numDocModificado");
/* 180:188 */           numDocModificado.appendChild(doc.createTextNode(facturaCliente.getFacturaClientePadre().getNumero()));
/* 181:189 */           informacionTributaria.appendChild(numDocModificado);
/* 182:190 */           Element numAutDocSustento = doc.createElement("numAutDocSustento");
/* 183:191 */           numAutDocSustento.appendChild(doc
/* 184:192 */             .createTextNode(facturaCliente.getFacturaClientePadre().getFacturaClienteSRI().getAutorizacion()));
/* 185:193 */           informacionTributaria.appendChild(numAutDocSustento);
/* 186:    */           
/* 187:195 */           Element fechaEmisionDocSustento = doc.createElement("fechaEmisionDocSustento");
/* 188:196 */           fechaEmisionDocSustento.appendChild(doc.createTextNode(formatoFecha.format(facturaCliente.getFacturaClientePadre()
/* 189:197 */             .getFacturaClienteSRI().getFechaEmision())));
/* 190:198 */           informacionTributaria.appendChild(fechaEmisionDocSustento);
/* 191:    */           
/* 192:200 */           Element valorModificacion = doc.createElement("valorModificacion");
/* 193:201 */           valorModificacion.appendChild(doc.createTextNode(facturaCliente.getFacturaClientePadre().getTotal()
/* 194:202 */             .subtract(facturaCliente.getFacturaClientePadre().getDescuento())
/* 195:203 */             .add(facturaCliente.getFacturaClientePadre().getImpuesto()).setScale(2, RoundingMode.HALF_UP).toString()));
/* 196:204 */           informacionTributaria.appendChild(valorModificacion);
/* 197:    */         }
/* 198:    */         Element baseNoObjetoIVA;
/* 199:207 */         if (!indicadorConsumidorFinal)
/* 200:    */         {
/* 201:209 */           Element totalSinImpuestos = doc.createElement("totalSinImpuestos");
/* 202:210 */           totalSinImpuestos.appendChild(doc.createTextNode(facturaCliente.getTotal().subtract(facturaCliente.getDescuento())
/* 203:211 */             .setScale(2, RoundingMode.HALF_UP).toString()));
/* 204:212 */           informacionTributaria.appendChild(totalSinImpuestos);
/* 205:    */           
/* 206:    */ 
/* 207:215 */           Element ICE = doc.createElement("ICE");
/* 208:216 */           ICE.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getMontoIce().setScale(2, RoundingMode.HALF_UP)
/* 209:217 */             .toString()));
/* 210:218 */           informacionTributaria.appendChild(ICE);
/* 211:    */           
/* 212:220 */           baseNoObjetoIVA = doc.createElement("baseNoObjetoIVA");
/* 213:221 */           baseNoObjetoIVA.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getBaseImponibleNoObjetoIva()
/* 214:222 */             .setScale(2, RoundingMode.HALF_UP).toString()));
/* 215:223 */           informacionTributaria.appendChild(baseNoObjetoIVA);
/* 216:    */           
/* 217:225 */           Element baseIVA0 = doc.createElement("baseIVA0");
/* 218:226 */           baseIVA0.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getBaseImponibleTarifaCero()
/* 219:227 */             .setScale(2, RoundingMode.HALF_UP).toString()));
/* 220:228 */           informacionTributaria.appendChild(baseIVA0);
/* 221:    */           
/* 222:230 */           Element baseIVA12 = doc.createElement("baseIVA12");
/* 223:231 */           baseIVA12.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getBaseImponibleDiferenteCero()
/* 224:232 */             .setScale(2, RoundingMode.HALF_UP).toString()));
/* 225:233 */           informacionTributaria.appendChild(baseIVA12);
/* 226:    */           
/* 227:    */ 
/* 228:236 */           Element IVA12 = doc.createElement("IVA12");
/* 229:237 */           IVA12.appendChild(doc.createTextNode(facturaCliente.getFacturaClienteSRI().getMontoIva().setScale(2, RoundingMode.HALF_UP)
/* 230:238 */             .toString()));
/* 231:239 */           informacionTributaria.appendChild(IVA12);
/* 232:    */         }
/* 233:242 */         if (this.documentoBase.equals(DocumentoBase.FACTURA_CLIENTE))
/* 234:    */         {
/* 235:244 */           Element totalConImpuestos = doc.createElement("totalConImpuestos");
/* 236:245 */           totalConImpuestos.appendChild(doc.createTextNode(facturaCliente.getTotal().subtract(facturaCliente.getDescuento())
/* 237:246 */             .add(facturaCliente.getImpuesto()).setScale(2, RoundingMode.HALF_UP).toString()));
/* 238:247 */           informacionTributaria.appendChild(totalConImpuestos);
/* 239:    */         }
/* 240:    */         else
/* 241:    */         {
/* 242:250 */           Element totalConImpuestos = doc.createElement("valorTotal");
/* 243:251 */           totalConImpuestos.appendChild(doc.createTextNode(facturaCliente.getTotal().subtract(facturaCliente.getDescuento())
/* 244:252 */             .add(facturaCliente.getImpuesto()).setScale(2, RoundingMode.HALF_UP).toString()));
/* 245:253 */           informacionTributaria.appendChild(totalConImpuestos);
/* 246:    */         }
/* 247:256 */         Element moneda = doc.createElement("moneda");
/* 248:257 */         moneda.appendChild(doc.createTextNode("Dolares"));
/* 249:258 */         informacionTributaria.appendChild(moneda);
/* 250:    */         Element detalles;
/* 251:259 */         if (this.documentoBase.equals(DocumentoBase.FACTURA_CLIENTE))
/* 252:    */         {
/* 253:261 */           detalles = doc.createElement("detalles");
/* 254:262 */           factura.appendChild(detalles);
/* 255:264 */           for (DetalleFacturaCliente detalleFacturaCliente : facturaCliente.getListaDetalleFacturaCliente())
/* 256:    */           {
/* 257:266 */             Element detalle = doc.createElement("detalle");
/* 258:267 */             detalles.appendChild(detalle);
/* 259:    */             
/* 260:269 */             Element concepto = doc.createElement("concepto");
/* 261:270 */             concepto.appendChild(doc.createTextNode(detalleFacturaCliente.getProducto().getNombre()));
/* 262:271 */             detalle.appendChild(concepto);
/* 263:272 */             Element precioUnitario = doc.createElement("precioUnitario");
/* 264:273 */             precioUnitario
/* 265:274 */               .appendChild(doc.createTextNode(detalleFacturaCliente.getPrecio().setScale(2, RoundingMode.HALF_UP).toString()));
/* 266:275 */             detalle.appendChild(precioUnitario);
/* 267:276 */             Element descuentosD = doc.createElement("descuentos");
/* 268:277 */             descuentosD
/* 269:278 */               .appendChild(doc.createTextNode(detalleFacturaCliente.getDescuento().setScale(2, RoundingMode.HALF_UP).toString()));
/* 270:279 */             detalle.appendChild(descuentosD);
/* 271:280 */             Element precioTotal = doc.createElement("precioTotal");
/* 272:281 */             precioTotal.appendChild(doc.createTextNode(detalleFacturaCliente.getPrecioLinea().setScale(2, RoundingMode.HALF_UP)
/* 273:282 */               .toString()));
/* 274:283 */             detalle.appendChild(precioTotal);
/* 275:    */           }
/* 276:    */         }
/* 277:285 */         else if (this.documentoBase.equals(DocumentoBase.NOTA_CREDITO_CLIENTE))
/* 278:    */         {
/* 279:286 */           Element motivos = doc.createElement("motivos");
/* 280:287 */           factura.appendChild(motivos);
/* 281:    */           
/* 282:289 */           Element motivo = doc.createElement("motivo");
/* 283:290 */           motivo.appendChild(doc.createTextNode(facturaCliente.getMotivoNotaCreditoCliente().getNombre()));
/* 284:291 */           motivos.appendChild(motivo);
/* 285:    */         }
/* 286:    */         else
/* 287:    */         {
/* 288:293 */           Element motivos = doc.createElement("motivos");
/* 289:294 */           factura.appendChild(motivos);
/* 290:    */           
/* 291:296 */           Element motivo = doc.createElement("motivo");
/* 292:297 */           motivo.appendChild(doc.createTextNode("Nota de debito"));
/* 293:298 */           motivos.appendChild(motivo);
/* 294:    */         }
/* 295:301 */         Element infoAdicional = doc.createElement("infoAdicional");
/* 296:302 */         factura.appendChild(infoAdicional);
/* 297:    */         
/* 298:304 */         Element campoAdicional = doc.createElement("campoAdicional");
/* 299:305 */         campoAdicional.setAttribute("nombre", "Direccion Establecimiento");
/* 300:306 */         campoAdicional.appendChild(doc.createTextNode(facturaCliente.getSucursal().getUbicacion().getDireccionCompleta()));
/* 301:307 */         infoAdicional.appendChild(campoAdicional);
/* 302:    */         
/* 303:    */ 
/* 304:310 */         TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 305:311 */         Transformer transformer = transformerFactory.newTransformer();
/* 306:312 */         DOMSource source = new DOMSource(doc);
/* 307:    */         
/* 308:314 */         String directorioAnexo = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getIdOrganizacion()) + File.separator + facturaCliente.getDocumento().getDocumentoBase().getNombre().replace(" ", "_");
/* 309:315 */         String nombreArchivo = facturaCliente.getNumero() + ".xml";
/* 310:316 */         String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 311:317 */         Result result = new StreamResult(new File(rutaArchivo));
/* 312:    */         
/* 313:    */ 
/* 314:    */ 
/* 315:321 */         transformer.transform(source, result);
/* 316:    */         
/* 317:323 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado"));
/* 318:    */       }
/* 319:    */     }
/* 320:    */     catch (Exception e)
/* 321:    */     {
/* 322:    */       SimpleDateFormat formatoFecha;
/* 323:    */       Integer idClienteConsumidorFinal;
/* 324:    */       boolean indicadorConsumidorFinal;
/* 325:327 */       e.printStackTrace();
/* 326:    */     }
/* 327:329 */     return "";
/* 328:    */   }
/* 329:    */   
/* 330:    */   public Date getFechaDesde()
/* 331:    */   {
/* 332:333 */     return this.fechaDesde;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setFechaDesde(Date fechaDesde)
/* 336:    */   {
/* 337:337 */     this.fechaDesde = fechaDesde;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Date getFechaHasta()
/* 341:    */   {
/* 342:341 */     return this.fechaHasta;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setFechaHasta(Date fechaHasta)
/* 346:    */   {
/* 347:345 */     this.fechaHasta = fechaHasta;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public PuntoDeVenta getPuntoDeVenta()
/* 351:    */   {
/* 352:349 */     return this.puntoDeVenta;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 356:    */   {
/* 357:353 */     this.puntoDeVenta = puntoDeVenta;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public Sucursal getSucursal()
/* 361:    */   {
/* 362:357 */     return this.sucursal;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setSucursal(Sucursal sucursal)
/* 366:    */   {
/* 367:361 */     this.sucursal = sucursal;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<Sucursal> getListaSucursal()
/* 371:    */   {
/* 372:370 */     if (this.listaSucursal == null) {
/* 373:371 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 374:    */     }
/* 375:373 */     return this.listaSucursal;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public List<PuntoDeVenta> getListaPuntoDeVenta()
/* 379:    */   {
/* 380:377 */     Map<String, String> filtros = null;
/* 381:378 */     if (this.sucursal != null)
/* 382:    */     {
/* 383:379 */       filtros = new HashMap();
/* 384:380 */       filtros.put("sucursal.idSucursal", String.valueOf(this.sucursal.getIdSucursal()));
/* 385:    */     }
/* 386:382 */     return this.servicioPuntoDeVenta.obtenerListaCombo("codigo", true, filtros);
/* 387:    */   }
/* 388:    */   
/* 389:    */   public DocumentoBase getDocumentoBase()
/* 390:    */   {
/* 391:391 */     return this.documentoBase;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 395:    */   {
/* 396:401 */     this.documentoBase = documentoBase;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public SelectItem[] getListaDocumentoBaseItem()
/* 400:    */   {
/* 401:410 */     if (this.listaDocumentoBaseItem == null)
/* 402:    */     {
/* 403:412 */       List<SelectItem> lista = new ArrayList();
/* 404:413 */       lista.add(new SelectItem("", ""));
/* 405:    */       
/* 406:415 */       SelectItem item = new SelectItem(DocumentoBase.FACTURA_CLIENTE, DocumentoBase.FACTURA_CLIENTE.getNombre());
/* 407:416 */       lista.add(item);
/* 408:417 */       item = new SelectItem(DocumentoBase.NOTA_CREDITO_CLIENTE, DocumentoBase.NOTA_CREDITO_CLIENTE.getNombre());
/* 409:418 */       lista.add(item);
/* 410:419 */       item = new SelectItem(DocumentoBase.NOTA_DEBITO_CLIENTE, DocumentoBase.NOTA_DEBITO_CLIENTE.getNombre());
/* 411:420 */       lista.add(item);
/* 412:421 */       this.listaDocumentoBaseItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 413:    */     }
/* 414:424 */     Arrays.sort(this.listaDocumentoBaseItem, new SelectItemComparator());
/* 415:    */     
/* 416:426 */     return this.listaDocumentoBaseItem;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String editar()
/* 420:    */   {
/* 421:432 */     return null;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public String guardar()
/* 425:    */   {
/* 426:438 */     return null;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public String eliminar()
/* 430:    */   {
/* 431:444 */     return null;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public String limpiar()
/* 435:    */   {
/* 436:450 */     return null;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public String cargarDatos()
/* 440:    */   {
/* 441:456 */     return null;
/* 442:    */   }
/* 443:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.DocumentosXMLBean
 * JD-Core Version:    0.7.0.1
 */