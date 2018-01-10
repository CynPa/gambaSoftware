/*    1:     */ package com.asinfo.as2.finaciero.SRI.reportes;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.dao.sri.FacturaProveedorSRIDao;
/*    4:     */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*    5:     */ import com.asinfo.as2.entities.FormaPagoSRI;
/*    6:     */ import com.asinfo.as2.entities.Organizacion;
/*    7:     */ import com.asinfo.as2.entities.TipoIdentificacion;
/*    8:     */ import com.asinfo.as2.entities.sri.AnuladoSRI;
/*    9:     */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   10:     */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   11:     */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   12:     */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   13:     */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   14:     */ import com.asinfo.as2.entities.sri.ReembolsoGastos;
/*   15:     */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   16:     */ import com.asinfo.as2.enumeraciones.RefrendoEnum;
/*   17:     */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   18:     */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*   19:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI;
/*   20:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   21:     */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*   22:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   23:     */ import com.asinfo.as2.util.AppUtil;
/*   24:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   25:     */ import java.io.File;
/*   26:     */ import java.io.FileNotFoundException;
/*   27:     */ import java.io.PrintStream;
/*   28:     */ import java.io.Serializable;
/*   29:     */ import java.math.BigDecimal;
/*   30:     */ import java.math.RoundingMode;
/*   31:     */ import java.text.SimpleDateFormat;
/*   32:     */ import java.util.Formatter;
/*   33:     */ import java.util.HashMap;
/*   34:     */ import java.util.Iterator;
/*   35:     */ import java.util.List;
/*   36:     */ import java.util.Map;
/*   37:     */ import java.util.Map.Entry;
/*   38:     */ import java.util.Set;
/*   39:     */ import javax.ejb.EJB;
/*   40:     */ import javax.faces.bean.ManagedBean;
/*   41:     */ import javax.faces.bean.ViewScoped;
/*   42:     */ import javax.xml.parsers.DocumentBuilder;
/*   43:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*   44:     */ import javax.xml.parsers.ParserConfigurationException;
/*   45:     */ import javax.xml.transform.Result;
/*   46:     */ import javax.xml.transform.Transformer;
/*   47:     */ import javax.xml.transform.TransformerException;
/*   48:     */ import javax.xml.transform.TransformerFactory;
/*   49:     */ import javax.xml.transform.dom.DOMSource;
/*   50:     */ import javax.xml.transform.stream.StreamResult;
/*   51:     */ import org.primefaces.model.StreamedContent;
/*   52:     */ import org.w3c.dom.Document;
/*   53:     */ import org.w3c.dom.Element;
/*   54:     */ 
/*   55:     */ @ManagedBean
/*   56:     */ @ViewScoped
/*   57:     */ public class AnexoATSBean
/*   58:     */   extends AnexoBaseBean
/*   59:     */   implements Serializable
/*   60:     */ {
/*   61:     */   private static final long serialVersionUID = 6502107917491200540L;
/*   62:     */   @EJB
/*   63:     */   private transient ServicioEmpresa servicioEmpresa;
/*   64:     */   @EJB
/*   65:     */   private transient FacturaProveedorSRIDao facturaProveedorSRIDao;
/*   66:     */   
/*   67:     */   protected String cabeceraATS(String strMes, Document doc, Element iva, int anio, HashMap<String, BigDecimal[]> hmResumenFacturas)
/*   68:     */   {
/*   69:  85 */     Element tipoIDInformante = doc.createElement("TipoIDInformante");
/*   70:  86 */     tipoIDInformante.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getTipoIdentificacion().getCodigo()));
/*   71:  87 */     iva.appendChild(tipoIDInformante);
/*   72:     */     
/*   73:     */ 
/*   74:  90 */     Element idInformante = doc.createElement("IdInformante");
/*   75:  91 */     idInformante.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getIdentificacion()));
/*   76:  92 */     iva.appendChild(idInformante);
/*   77:     */     
/*   78:     */ 
/*   79:  95 */     Element razonSocial = doc.createElement("razonSocial");
/*   80:  96 */     razonSocial.appendChild(doc.createTextNode(AppUtil.getOrganizacion().getRazonSocial().replace(".", "").replace("&", "Y")));
/*   81:  97 */     iva.appendChild(razonSocial);
/*   82:     */     
/*   83:     */ 
/*   84: 100 */     Element anioElement = doc.createElement("Anio");
/*   85: 101 */     anioElement.appendChild(doc.createTextNode(String.valueOf(anio)));
/*   86: 102 */     iva.appendChild(anioElement);
/*   87:     */     
/*   88:     */ 
/*   89: 105 */     Element mes = doc.createElement("Mes");
/*   90: 106 */     mes.appendChild(doc.createTextNode(strMes));
/*   91: 107 */     iva.appendChild(mes);
/*   92: 108 */     BigDecimal total = BigDecimal.ZERO;
/*   93: 109 */     for (String clave : hmResumenFacturas.keySet()) {
/*   94: 110 */       total = total.add(((BigDecimal[])hmResumenFacturas.get(clave))[0]);
/*   95:     */     }
/*   96: 116 */     Formatter fmt = new Formatter();
/*   97: 117 */     Element numEstabRuc = doc.createElement("numEstabRuc");
/*   98: 118 */     if (hmResumenFacturas.size() > 0)
/*   99:     */     {
/*  100: 119 */       numEstabRuc.appendChild(doc.createTextNode(fmt.format("%03d", new Object[] { Integer.valueOf(hmResumenFacturas.size()) }).toString()));
/*  101: 120 */       iva.appendChild(numEstabRuc);
/*  102:     */     }
/*  103: 124 */     Element totalVentas = doc.createElement("totalVentas");
/*  104: 125 */     totalVentas.appendChild(doc.createTextNode(total.toString()));
/*  105: 126 */     iva.appendChild(totalVentas);
/*  106:     */     
/*  107:     */ 
/*  108: 129 */     Element codigoOperativo = doc.createElement("codigoOperativo");
/*  109: 130 */     codigoOperativo.appendChild(doc.createTextNode("IVA"));
/*  110: 131 */     iva.appendChild(codigoOperativo);
/*  111:     */     
/*  112: 133 */     return strMes;
/*  113:     */   }
/*  114:     */   
/*  115:     */   protected void comprasATS(Document doc, Element iva, int anio, int mes, int idOrganizacion)
/*  116:     */   {
/*  117: 144 */     SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/*  118:     */     
/*  119:     */ 
/*  120: 147 */     List<FacturaProveedorSRI> listaFacturaProveedorSRI = this.servicioFacturaProveedorSRI.obtenerFacturasMes(anio, mes, idOrganizacion);
/*  121: 148 */     HashMap<String, List<FormaPagoSRI>> hmFormaPagoSRI = new HashMap();
/*  122:     */     Element compras;
/*  123: 150 */     if (!listaFacturaProveedorSRI.isEmpty())
/*  124:     */     {
/*  125: 153 */       compras = doc.createElement("compras");
/*  126: 154 */       iva.appendChild(compras);
/*  127: 157 */       for (FacturaProveedorSRI facturaProveedorSRI : listaFacturaProveedorSRI)
/*  128:     */       {
/*  129: 160 */         boolean indicadorReembolso = false;
/*  130:     */         
/*  131:     */ 
/*  132: 163 */         BigDecimal valorRetenido = BigDecimal.ZERO;
/*  133:     */         
/*  134: 165 */         List<ReembolsoGastos> listaReembolsoGastos = this.servicioFacturaProveedorSRI.listaReembolsoGastos(facturaProveedorSRI);
/*  135: 166 */         if (listaReembolsoGastos.size() > 0)
/*  136:     */         {
/*  137: 167 */           indicadorReembolso = true;
/*  138:     */           
/*  139: 169 */           valorRetenido = BigDecimal.ONE;
/*  140:     */         }
/*  141: 172 */         Element detalleCompras = doc.createElement("detalleCompras");
/*  142: 173 */         compras.appendChild(detalleCompras);
/*  143:     */         
/*  144:     */ 
/*  145: 176 */         String codigoSustento = "";
/*  146: 177 */         Element codSustento = doc.createElement("codSustento");
/*  147: 178 */         if (facturaProveedorSRI.getCreditoTributarioSRI() == null)
/*  148:     */         {
/*  149: 179 */           if (indicadorReembolso) {
/*  150: 180 */             codigoSustento = "01";
/*  151:     */           } else {
/*  152: 182 */             codigoSustento = "00";
/*  153:     */           }
/*  154:     */         }
/*  155:     */         else {
/*  156: 185 */           codigoSustento = facturaProveedorSRI.getCreditoTributarioSRI().getCodigo();
/*  157:     */         }
/*  158: 188 */         codSustento.appendChild(doc.createTextNode(codigoSustento));
/*  159: 189 */         detalleCompras.appendChild(codSustento);
/*  160:     */         
/*  161:     */ 
/*  162: 192 */         Element tpIdProv = doc.createElement("tpIdProv");
/*  163: 193 */         String tipoIdentificacionSRI = "03";
/*  164: 195 */         if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("R")) {
/*  165: 196 */           tipoIdentificacionSRI = "01";
/*  166: 197 */         } else if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("C")) {
/*  167: 198 */           tipoIdentificacionSRI = "02";
/*  168: 199 */         } else if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("P")) {
/*  169: 200 */           tipoIdentificacionSRI = "03";
/*  170:     */         }
/*  171: 203 */         tpIdProv.appendChild(doc.createTextNode(tipoIdentificacionSRI));
/*  172: 204 */         detalleCompras.appendChild(tpIdProv);
/*  173:     */         
/*  174:     */ 
/*  175: 207 */         Element idProv = doc.createElement("idProv");
/*  176: 208 */         idProv.appendChild(doc.createTextNode(facturaProveedorSRI.getIdentificacionProveedor().replace(" ", "").replace("_", "")));
/*  177: 209 */         detalleCompras.appendChild(idProv);
/*  178:     */         
/*  179:     */ 
/*  180: 212 */         Element tipoComprobante = doc.createElement("tipoComprobante");
/*  181: 213 */         tipoComprobante.appendChild(doc.createTextNode(facturaProveedorSRI.getTipoComprobanteSRI().getCodigo().length() == 1 ? "0" + facturaProveedorSRI
/*  182: 214 */           .getTipoComprobanteSRI().getCodigo() : facturaProveedorSRI.getTipoComprobanteSRI().getCodigo()));
/*  183: 215 */         detalleCompras.appendChild(tipoComprobante);
/*  184: 218 */         if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("P"))
/*  185:     */         {
/*  186: 220 */           Element tipoProv = doc.createElement("tipoProv");
/*  187: 221 */           tipoProv.appendChild(doc.createTextNode(facturaProveedorSRI.getTipoEmpresa().equals(TipoEmpresa.NATURAL) ? "01" : "02"));
/*  188: 222 */           detalleCompras.appendChild(tipoProv);
/*  189:     */           
/*  190: 224 */           Element denoProv = doc.createElement("denoProv");
/*  191: 225 */           denoProv.appendChild(doc.createTextNode(facturaProveedorSRI.getNombreProveedor().replaceAll("[.|-]", "")));
/*  192: 226 */           detalleCompras.appendChild(denoProv);
/*  193:     */         }
/*  194: 231 */         Element parteRel = doc.createElement("parteRel");
/*  195: 232 */         parteRel.appendChild(doc.createTextNode(facturaProveedorSRI.isIndicadorParteRelacionada() ? "SI" : "NO"));
/*  196: 233 */         detalleCompras.appendChild(parteRel);
/*  197:     */         
/*  198:     */ 
/*  199: 236 */         Element fechaRegistro = doc.createElement("fechaRegistro");
/*  200: 237 */         fechaRegistro.appendChild(doc.createTextNode(formatoFecha.format(facturaProveedorSRI.getFechaRegistro())));
/*  201: 238 */         detalleCompras.appendChild(fechaRegistro);
/*  202:     */         
/*  203:     */ 
/*  204: 241 */         Element establecimiento = doc.createElement("establecimiento");
/*  205: 242 */         establecimiento.appendChild(doc.createTextNode(facturaProveedorSRI.getEstablecimiento()));
/*  206: 243 */         detalleCompras.appendChild(establecimiento);
/*  207:     */         
/*  208:     */ 
/*  209: 246 */         Element puntoEmision = doc.createElement("puntoEmision");
/*  210: 247 */         puntoEmision.appendChild(doc.createTextNode(facturaProveedorSRI.getPuntoEmision()));
/*  211: 248 */         detalleCompras.appendChild(puntoEmision);
/*  212:     */         
/*  213:     */ 
/*  214: 251 */         Element secuencial = doc.createElement("secuencial");
/*  215: 252 */         secuencial.appendChild(doc.createTextNode(facturaProveedorSRI.getNumero()));
/*  216: 253 */         detalleCompras.appendChild(secuencial);
/*  217:     */         
/*  218:     */ 
/*  219: 256 */         Element fechaEmision = doc.createElement("fechaEmision");
/*  220: 257 */         fechaEmision.appendChild(doc.createTextNode(formatoFecha.format(facturaProveedorSRI.getFechaEmision())));
/*  221: 258 */         detalleCompras.appendChild(fechaEmision);
/*  222:     */         
/*  223:     */ 
/*  224: 261 */         Element autorizacion = doc.createElement("autorizacion");
/*  225: 262 */         autorizacion.appendChild(doc.createTextNode(facturaProveedorSRI.getAutorizacion().trim()));
/*  226: 263 */         detalleCompras.appendChild(autorizacion);
/*  227:     */         
/*  228:     */ 
/*  229: 266 */         Element baseNoGraIva = doc.createElement("baseNoGraIva");
/*  230: 267 */         baseNoGraIva.appendChild(doc.createTextNode(facturaProveedorSRI.getBaseImponibleNoObjetoIva().toString()));
/*  231: 268 */         detalleCompras.appendChild(baseNoGraIva);
/*  232:     */         
/*  233:     */ 
/*  234: 271 */         Element baseImponible = doc.createElement("baseImponible");
/*  235: 272 */         baseImponible.appendChild(doc.createTextNode(facturaProveedorSRI.getBaseImponibleTarifaCero().toString()));
/*  236: 273 */         detalleCompras.appendChild(baseImponible);
/*  237:     */         
/*  238:     */ 
/*  239: 276 */         Element baseImpGrav = doc.createElement("baseImpGrav");
/*  240: 277 */         baseImpGrav.appendChild(doc.createTextNode(facturaProveedorSRI.getBaseImponibleDiferenteCero().toString()));
/*  241: 278 */         detalleCompras.appendChild(baseImpGrav);
/*  242:     */         
/*  243:     */ 
/*  244: 281 */         Element baseImpExe = doc.createElement("baseImpExe");
/*  245: 282 */         baseImpExe.appendChild(doc.createTextNode("0.00"));
/*  246: 283 */         detalleCompras.appendChild(baseImpExe);
/*  247:     */         
/*  248:     */ 
/*  249: 286 */         Element montoIce = doc.createElement("montoIce");
/*  250: 287 */         montoIce.appendChild(doc.createTextNode(facturaProveedorSRI.getMontoIce().toString()));
/*  251: 288 */         detalleCompras.appendChild(montoIce);
/*  252:     */         
/*  253:     */ 
/*  254: 291 */         Element montoIva = doc.createElement("montoIva");
/*  255: 292 */         montoIva.appendChild(doc.createTextNode(facturaProveedorSRI.getMontoIva().toString()));
/*  256: 293 */         detalleCompras.appendChild(montoIva);
/*  257:     */         
/*  258:     */ 
/*  259: 296 */         facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(facturaProveedorSRI.getId());
/*  260: 298 */         if ((anio > 2015) || ((anio == 2015) && (mes >= 6)))
/*  261:     */         {
/*  262: 300 */           Element valRetBien10 = doc.createElement("valRetBien10");
/*  263: 301 */           BigDecimal valorRetenidoBienes10 = this.facturaProveedorSRIDao.getValorRetenidoPorCodigoEnMemoria(facturaProveedorSRI, "9");
/*  264: 302 */           valRetBien10.appendChild(doc.createTextNode(valorRetenidoBienes10.toString()));
/*  265: 303 */           detalleCompras.appendChild(valRetBien10);
/*  266:     */           
/*  267:     */ 
/*  268: 306 */           Element valRetServ20 = doc.createElement("valRetServ20");
/*  269: 307 */           BigDecimal valorRetenidoServicios20 = this.facturaProveedorSRIDao.getValorRetenidoPorCodigoEnMemoria(facturaProveedorSRI, "10");
/*  270: 308 */           valRetServ20.appendChild(doc.createTextNode(valorRetenidoServicios20.toString()));
/*  271: 309 */           detalleCompras.appendChild(valRetServ20);
/*  272:     */         }
/*  273: 312 */         Element valorRetBienes = doc.createElement("valorRetBienes");
/*  274: 313 */         BigDecimal valorRetenidoBienes30 = this.facturaProveedorSRIDao.getValorRetenidoPorCodigoEnMemoria(facturaProveedorSRI, "1");
/*  275: 314 */         valorRetBienes.appendChild(doc.createTextNode(valorRetenidoBienes30.toString()));
/*  276: 315 */         detalleCompras.appendChild(valorRetBienes);
/*  277:     */         
/*  278:     */ 
/*  279: 318 */         Element valRetServ50 = doc.createElement("valRetServ50");
/*  280: 319 */         BigDecimal valorRetenidoServicios50 = BigDecimal.ZERO;
/*  281:     */         
/*  282: 321 */         valRetServ50.appendChild(doc.createTextNode(valorRetenidoServicios50.toString()));
/*  283: 322 */         detalleCompras.appendChild(valRetServ50);
/*  284:     */         
/*  285:     */ 
/*  286: 325 */         Element valorRetServicios = doc.createElement("valorRetServicios");
/*  287: 326 */         BigDecimal valorRetenidoServicios70 = this.facturaProveedorSRIDao.getValorRetenidoPorCodigoEnMemoria(facturaProveedorSRI, "2");
/*  288: 327 */         valorRetServicios.appendChild(doc.createTextNode(valorRetenidoServicios70.toString()));
/*  289: 328 */         detalleCompras.appendChild(valorRetServicios);
/*  290:     */         
/*  291:     */ 
/*  292: 331 */         Element valRetServ100 = doc.createElement("valRetServ100");
/*  293: 332 */         valRetServ100.appendChild(doc.createTextNode(this.facturaProveedorSRIDao.getValorRetenidoPorCodigoEnMemoria(facturaProveedorSRI, "3")
/*  294: 333 */           .toString()));
/*  295: 334 */         detalleCompras.appendChild(valRetServ100);
/*  296:     */         
/*  297:     */ 
/*  298: 337 */         Element totbasesImpReemb = doc.createElement("totbasesImpReemb");
/*  299: 338 */         BigDecimal totbasesImpReembValor = BigDecimal.ZERO;
/*  300: 339 */         if (indicadorReembolso) {
/*  301: 340 */           for (ReembolsoGastos rg : listaReembolsoGastos) {
/*  302: 341 */             totbasesImpReembValor = totbasesImpReembValor.add(rg.getBaseImponibleDiferenteCero().add(rg.getBaseImponibleTarifaCero())
/*  303: 342 */               .add(rg.getBaseImponibleNoObjetoIva()).add(rg.getBaseExentaIva()));
/*  304:     */           }
/*  305:     */         }
/*  306: 345 */         totbasesImpReemb.appendChild(doc.createTextNode(totbasesImpReembValor.toString()));
/*  307: 346 */         detalleCompras.appendChild(totbasesImpReemb);
/*  308:     */         
/*  309:     */ 
/*  310:     */ 
/*  311:     */ 
/*  312:     */ 
/*  313: 352 */         Element pagoExterior = doc.createElement("pagoExterior");
/*  314: 353 */         detalleCompras.appendChild(pagoExterior);
/*  315:     */         
/*  316:     */ 
/*  317: 356 */         Element pagoLocExt = doc.createElement("pagoLocExt");
/*  318: 357 */         pagoLocExt.appendChild(doc.createTextNode("01"));
/*  319: 358 */         pagoExterior.appendChild(pagoLocExt);
/*  320:     */         
/*  321:     */ 
/*  322: 361 */         Element paisEfecPago = doc.createElement("paisEfecPago");
/*  323: 362 */         paisEfecPago.appendChild(doc.createTextNode("NA"));
/*  324: 363 */         pagoExterior.appendChild(paisEfecPago);
/*  325:     */         
/*  326:     */ 
/*  327: 366 */         Element aplicConvDobTrib = doc.createElement("aplicConvDobTrib");
/*  328: 367 */         aplicConvDobTrib.appendChild(doc.createTextNode("NA"));
/*  329: 368 */         pagoExterior.appendChild(aplicConvDobTrib);
/*  330:     */         
/*  331:     */ 
/*  332: 371 */         Element pagExtSujRetNorLeg = doc.createElement("pagExtSujRetNorLeg");
/*  333: 372 */         pagExtSujRetNorLeg.appendChild(doc.createTextNode("NA"));
/*  334: 373 */         pagoExterior.appendChild(pagExtSujRetNorLeg);
/*  335:     */         
/*  336:     */ 
/*  337: 376 */         Element pagoRegFis = doc.createElement("pagoRegFis");
/*  338: 377 */         pagoRegFis.appendChild(doc.createTextNode("NA"));
/*  339: 378 */         pagoExterior.appendChild(pagoRegFis);
/*  340:     */         
/*  341:     */ 
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345:     */ 
/*  346: 385 */         BigDecimal total = facturaProveedorSRI.getBaseImponibleNoObjetoIva().add(facturaProveedorSRI.getBaseImponibleTarifaCero()).add(facturaProveedorSRI.getBaseImponibleDiferenteCero()).add(facturaProveedorSRI.getMontoIce()).add(facturaProveedorSRI.getMontoIva());
/*  347: 386 */         if ((total.compareTo(new BigDecimal(1000.0D)) >= 0) && 
/*  348: 387 */           (!facturaProveedorSRI.getTipoComprobanteSRI().getCodigo().equals("04"))) {
/*  349: 399 */           if (facturaProveedorSRI.getCodigoFormaPagoSRI() != null)
/*  350:     */           {
/*  351: 401 */             Element formasDePago = doc.createElement("formasDePago");
/*  352: 402 */             detalleCompras.appendChild(formasDePago);
/*  353:     */             
/*  354:     */ 
/*  355:     */ 
/*  356: 406 */             Element formaPago = doc.createElement("formaPago");
/*  357: 407 */             formaPago.appendChild(doc.createTextNode(facturaProveedorSRI.getCodigoFormaPagoSRI()));
/*  358: 408 */             formasDePago.appendChild(formaPago);
/*  359:     */           }
/*  360: 411 */           else if (facturaProveedorSRI.getTipoComprobanteSRI().getCodigo().equals("42"))
/*  361:     */           {
/*  362: 413 */             Element formasDePago = doc.createElement("formasDePago");
/*  363: 414 */             detalleCompras.appendChild(formasDePago);
/*  364:     */             
/*  365: 416 */             Element formaPago = doc.createElement("formaPago");
/*  366: 417 */             formaPago.appendChild(doc.createTextNode("06"));
/*  367: 418 */             formasDePago.appendChild(formaPago);
/*  368:     */           }
/*  369:     */         }
/*  370:     */         Element air;
/*  371: 427 */         if (!indicadorReembolso)
/*  372:     */         {
/*  373: 429 */           air = doc.createElement("air");
/*  374: 430 */           detalleCompras.appendChild(air);
/*  375:     */           
/*  376: 432 */           BigDecimal valoresFuente = facturaProveedorSRI.getValorRetenidoFuente();
/*  377:     */           
/*  378: 434 */           valorRetenido = valoresFuente.add(facturaProveedorSRI.getValorRetenidoIVA());
/*  379: 436 */           for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI()) {
/*  380: 437 */             if (detalleFacturaProveedorSRI.getTipoConceptoRetencion().equals(TipoConceptoRetencion.FUENTE))
/*  381:     */             {
/*  382: 439 */               Element detalleAir = doc.createElement("detalleAir");
/*  383: 440 */               air.appendChild(detalleAir);
/*  384:     */               
/*  385:     */ 
/*  386: 443 */               Element codRetAir = doc.createElement("codRetAir");
/*  387: 444 */               codRetAir.appendChild(doc.createTextNode(detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCodigo()));
/*  388: 445 */               detalleAir.appendChild(codRetAir);
/*  389:     */               
/*  390:     */ 
/*  391: 448 */               Element baseImpAir = doc.createElement("baseImpAir");
/*  392: 449 */               baseImpAir.appendChild(doc.createTextNode(detalleFacturaProveedorSRI.getBaseImponibleRetencion().toString()));
/*  393: 450 */               detalleAir.appendChild(baseImpAir);
/*  394:     */               
/*  395:     */ 
/*  396: 453 */               Element porcentajeAir = doc.createElement("porcentajeAir");
/*  397: 454 */               porcentajeAir.appendChild(doc.createTextNode(detalleFacturaProveedorSRI.getPorcentajeRetencion()
/*  398: 455 */                 .setScale(2, RoundingMode.HALF_UP).toString()));
/*  399: 456 */               detalleAir.appendChild(porcentajeAir);
/*  400:     */               
/*  401:     */ 
/*  402: 459 */               Element valRetAir = doc.createElement("valRetAir");
/*  403: 460 */               valRetAir.appendChild(doc.createTextNode(detalleFacturaProveedorSRI.getValorRetencion().toString()));
/*  404: 461 */               detalleAir.appendChild(valRetAir);
/*  405: 463 */               if (detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCodigo().equals("327"))
/*  406:     */               {
/*  407: 465 */                 Element fechaPagoDiv = doc.createElement("fechaPagoDiv");
/*  408: 466 */                 fechaPagoDiv.appendChild(doc.createTextNode(formatoFecha.format(facturaProveedorSRI.getFechaPagoDividendo())));
/*  409: 467 */                 detalleAir.appendChild(fechaPagoDiv);
/*  410:     */                 
/*  411:     */ 
/*  412: 470 */                 Element imRentaSoc = doc.createElement("imRentaSoc");
/*  413: 471 */                 imRentaSoc.appendChild(doc.createTextNode(facturaProveedorSRI.getIrAsociadoDividendo().toString()));
/*  414: 472 */                 detalleAir.appendChild(imRentaSoc);
/*  415:     */                 
/*  416:     */ 
/*  417: 475 */                 Element anioUtDiv = doc.createElement("anioUtDiv");
/*  418: 476 */                 anioUtDiv.appendChild(doc.createTextNode(facturaProveedorSRI.getAnioGeneracionUtilidades().toString()));
/*  419: 477 */                 detalleAir.appendChild(anioUtDiv);
/*  420:     */               }
/*  421:     */             }
/*  422:     */           }
/*  423:     */         }
/*  424:     */         String estabRetencionTexto;
/*  425: 485 */         if ((valorRetenido.compareTo(BigDecimal.ZERO) > 0) || (
/*  426: 486 */           (facturaProveedorSRI.getNumeroRetencion() != null) && (!facturaProveedorSRI.getNumeroRetencion().trim().equals("0"))))
/*  427:     */         {
/*  428: 488 */           Element estabRetencion1 = doc.createElement("estabRetencion1");
/*  429: 489 */           estabRetencionTexto = facturaProveedorSRI.getEstablecimientoRetencion();
/*  430: 491 */           if (indicadorReembolso) {
/*  431: 493 */             estabRetencion1.appendChild(doc.createTextNode(facturaProveedorSRI.getEstablecimiento().trim()));
/*  432: 496 */           } else if ((estabRetencionTexto != null) && (!estabRetencionTexto.trim().isEmpty())) {
/*  433: 497 */             estabRetencion1.appendChild(doc.createTextNode(facturaProveedorSRI.getEstablecimientoRetencion().trim()));
/*  434:     */           } else {
/*  435: 499 */             estabRetencion1.appendChild(doc.createTextNode("000"));
/*  436:     */           }
/*  437: 504 */           detalleCompras.appendChild(estabRetencion1);
/*  438:     */           
/*  439:     */ 
/*  440: 507 */           Element ptoEmiRetencion1 = doc.createElement("ptoEmiRetencion1");
/*  441: 508 */           String ptoEmiRetencion1Texto = facturaProveedorSRI.getPuntoEmisionRetencion();
/*  442: 509 */           if (indicadorReembolso) {
/*  443: 511 */             ptoEmiRetencion1.appendChild(doc.createTextNode(facturaProveedorSRI.getPuntoEmision().trim()));
/*  444: 513 */           } else if ((ptoEmiRetencion1Texto != null) && (!ptoEmiRetencion1Texto.trim().isEmpty())) {
/*  445: 514 */             ptoEmiRetencion1.appendChild(doc.createTextNode(facturaProveedorSRI.getPuntoEmisionRetencion().trim()));
/*  446:     */           } else {
/*  447: 516 */             ptoEmiRetencion1.appendChild(doc.createTextNode("000"));
/*  448:     */           }
/*  449: 520 */           detalleCompras.appendChild(ptoEmiRetencion1);
/*  450:     */           
/*  451:     */ 
/*  452: 523 */           Element secRetencion1 = doc.createElement("secRetencion1");
/*  453: 524 */           String secRetencion1Texto = facturaProveedorSRI.getNumeroRetencion();
/*  454: 526 */           if (indicadorReembolso) {
/*  455: 528 */             secRetencion1.appendChild(doc.createTextNode(Integer.toString(facturaProveedorSRI.getIdFacturaProveedorSRI())));
/*  456: 530 */           } else if ((secRetencion1Texto != null) && (!secRetencion1Texto.trim().isEmpty())) {
/*  457: 531 */             secRetencion1.appendChild(doc.createTextNode(facturaProveedorSRI.getNumeroRetencion().trim()));
/*  458:     */           } else {
/*  459: 533 */             ptoEmiRetencion1.appendChild(doc.createTextNode("0"));
/*  460:     */           }
/*  461: 538 */           detalleCompras.appendChild(secRetencion1);
/*  462:     */           
/*  463:     */ 
/*  464: 541 */           Element autRetencion1 = doc.createElement("autRetencion1");
/*  465: 542 */           String autorizacionRetencion = facturaProveedorSRI.getAutorizacionRetencion();
/*  466: 543 */           if ((autorizacionRetencion == null) || (autorizacionRetencion.trim().isEmpty())) {
/*  467: 544 */             autorizacionRetencion = "000";
/*  468: 545 */           } else if (indicadorReembolso) {
/*  469: 546 */             autorizacionRetencion = "0000000000";
/*  470:     */           } else {
/*  471: 548 */             autorizacionRetencion = autorizacionRetencion.trim();
/*  472:     */           }
/*  473: 550 */           autRetencion1.appendChild(doc.createTextNode(autorizacionRetencion));
/*  474: 551 */           detalleCompras.appendChild(autRetencion1);
/*  475:     */           
/*  476:     */ 
/*  477: 554 */           Element fechaEmiRet1 = doc.createElement("fechaEmiRet1");
/*  478:     */           
/*  479: 556 */           String fechaEmisionRetencion = facturaProveedorSRI.getFechaEmisionRetencion() == null ? formatoFecha.format(facturaProveedorSRI.getFechaEmision()) : formatoFecha.format(facturaProveedorSRI.getFechaEmisionRetencion());
/*  480: 557 */           fechaEmiRet1.appendChild(doc.createTextNode(fechaEmisionRetencion));
/*  481: 558 */           detalleCompras.appendChild(fechaEmiRet1);
/*  482:     */         }
/*  483:     */         Element reembolsos;
/*  484: 562 */         if (indicadorReembolso)
/*  485:     */         {
/*  486: 564 */           reembolsos = doc.createElement("reembolsos");
/*  487: 565 */           detalleCompras.appendChild(reembolsos);
/*  488: 566 */           for (ReembolsoGastos rg : listaReembolsoGastos)
/*  489:     */           {
/*  490: 567 */             Element reembolso = doc.createElement("reembolso");
/*  491: 568 */             reembolsos.appendChild(reembolso);
/*  492:     */             
/*  493: 570 */             Element tipoComprobanteReemb = doc.createElement("tipoComprobanteReemb");
/*  494: 571 */             tipoComprobanteReemb.appendChild(doc.createTextNode(rg.getTipoComprobanteSRI().getCodigo().length() == 1 ? "0" + rg
/*  495: 572 */               .getTipoComprobanteSRI().getCodigo() : rg.getTipoComprobanteSRI().getCodigo()));
/*  496: 573 */             reembolso.appendChild(tipoComprobanteReemb);
/*  497:     */             
/*  498: 575 */             Element tpIdProvReemb = doc.createElement("tpIdProvReemb");
/*  499: 576 */             String codigoTipoIdentificacion = rg.getTipoIdentificacion().getCodigo();
/*  500: 578 */             if (codigoTipoIdentificacion.equals("R")) {
/*  501: 579 */               codigoTipoIdentificacion = "01";
/*  502: 580 */             } else if (codigoTipoIdentificacion.equals("C")) {
/*  503: 581 */               codigoTipoIdentificacion = "02";
/*  504: 582 */             } else if (codigoTipoIdentificacion.equals("P")) {
/*  505: 583 */               codigoTipoIdentificacion = "03";
/*  506:     */             }
/*  507: 585 */             tpIdProvReemb.appendChild(doc.createTextNode(codigoTipoIdentificacion));
/*  508: 586 */             reembolso.appendChild(tpIdProvReemb);
/*  509:     */             
/*  510: 588 */             Element idProvReemb = doc.createElement("idProvReemb");
/*  511: 589 */             idProvReemb.appendChild(doc.createTextNode(rg.getIdentificacionProveedor()));
/*  512: 590 */             reembolso.appendChild(idProvReemb);
/*  513:     */             
/*  514: 592 */             Element establecimientoReemb = doc.createElement("establecimientoReemb");
/*  515: 593 */             establecimientoReemb.appendChild(doc.createTextNode(rg.getEstablecimiento()));
/*  516: 594 */             reembolso.appendChild(establecimientoReemb);
/*  517:     */             
/*  518: 596 */             Element puntoEmisionReemb = doc.createElement("puntoEmisionReemb");
/*  519: 597 */             puntoEmisionReemb.appendChild(doc.createTextNode(rg.getPuntoEmision()));
/*  520: 598 */             reembolso.appendChild(puntoEmisionReemb);
/*  521:     */             
/*  522: 600 */             Element secuencialReemb = doc.createElement("secuencialReemb");
/*  523: 601 */             secuencialReemb.appendChild(doc.createTextNode(rg.getNumero()));
/*  524: 602 */             reembolso.appendChild(secuencialReemb);
/*  525:     */             
/*  526: 604 */             Element fechaEmisionReemb = doc.createElement("fechaEmisionReemb");
/*  527: 605 */             fechaEmisionReemb.appendChild(doc.createTextNode(formatoFecha.format(rg.getFechaEmision())));
/*  528: 606 */             reembolso.appendChild(fechaEmisionReemb);
/*  529:     */             
/*  530: 608 */             Element autorizacionReemb = doc.createElement("autorizacionReemb");
/*  531: 609 */             autorizacionReemb.appendChild(doc.createTextNode(rg.getAutorizacion().trim()));
/*  532: 610 */             reembolso.appendChild(autorizacionReemb);
/*  533:     */             
/*  534: 612 */             Element baseImponibleReemb = doc.createElement("baseImponibleReemb");
/*  535: 613 */             baseImponibleReemb.appendChild(doc.createTextNode(rg.getBaseImponibleTarifaCero().toString()));
/*  536: 614 */             reembolso.appendChild(baseImponibleReemb);
/*  537:     */             
/*  538: 616 */             Element baseImpGravReemb = doc.createElement("baseImpGravReemb");
/*  539: 617 */             baseImpGravReemb.appendChild(doc.createTextNode(rg.getBaseImponibleDiferenteCero().toString()));
/*  540: 618 */             reembolso.appendChild(baseImpGravReemb);
/*  541:     */             
/*  542: 620 */             totbasesImpReembValor = totbasesImpReembValor.add(rg.getBaseImponibleDiferenteCero().add(rg.getBaseImponibleTarifaCero()));
/*  543:     */             
/*  544: 622 */             Element baseNoGraIvaReemb = doc.createElement("baseNoGraIvaReemb");
/*  545: 623 */             baseNoGraIvaReemb.appendChild(doc.createTextNode(rg.getBaseImponibleNoObjetoIva().toString()));
/*  546: 624 */             reembolso.appendChild(baseNoGraIvaReemb);
/*  547:     */             
/*  548: 626 */             Element baseImpExeReemb = doc.createElement("baseImpExeReemb");
/*  549: 627 */             baseImpExeReemb.appendChild(doc.createTextNode(rg.getBaseExentaIva().toString()));
/*  550: 628 */             reembolso.appendChild(baseImpExeReemb);
/*  551:     */             
/*  552: 630 */             Element montoIceRemb = doc.createElement("montoIceRemb");
/*  553: 631 */             montoIceRemb.appendChild(doc.createTextNode(rg.getMontoIce().toString()));
/*  554: 632 */             reembolso.appendChild(montoIceRemb);
/*  555:     */             
/*  556: 634 */             Element montoIvaRemb = doc.createElement("montoIvaRemb");
/*  557: 635 */             montoIvaRemb.appendChild(doc.createTextNode(rg.getMontoIva().toString()));
/*  558: 636 */             reembolso.appendChild(montoIvaRemb);
/*  559:     */           }
/*  560:     */         }
/*  561: 641 */         if (facturaProveedorSRI.getDocumentoModificado() != null)
/*  562:     */         {
/*  563: 643 */           Element docModificado = doc.createElement("docModificado");
/*  564:     */           
/*  565:     */ 
/*  566:     */ 
/*  567: 647 */           String documentoModificado = facturaProveedorSRI.getDocumentoModificado() == null ? "00" : "01";
/*  568: 648 */           docModificado.appendChild(doc.createTextNode(documentoModificado));
/*  569: 649 */           detalleCompras.appendChild(docModificado);
/*  570:     */           
/*  571:     */ 
/*  572: 652 */           Element estabModificado = doc.createElement("estabModificado");
/*  573:     */           
/*  574: 654 */           String establecimientoModificado = facturaProveedorSRI.getEstablecimientoModificado() == null ? "000" : facturaProveedorSRI.getEstablecimientoModificado();
/*  575: 655 */           estabModificado.appendChild(doc.createTextNode(establecimientoModificado));
/*  576: 656 */           detalleCompras.appendChild(estabModificado);
/*  577:     */           
/*  578:     */ 
/*  579: 659 */           Element ptoEmiModificado = doc.createElement("ptoEmiModificado");
/*  580:     */           
/*  581: 661 */           String puntoEmisionModificado = facturaProveedorSRI.getPuntoEmisionModificado() == null ? "000" : facturaProveedorSRI.getPuntoEmisionModificado();
/*  582: 662 */           ptoEmiModificado.appendChild(doc.createTextNode(puntoEmisionModificado));
/*  583: 663 */           detalleCompras.appendChild(ptoEmiModificado);
/*  584:     */           
/*  585:     */ 
/*  586: 666 */           Element secModificado = doc.createElement("secModificado");
/*  587:     */           
/*  588: 668 */           String numeroModificado = facturaProveedorSRI.getNumeroModificado() == null ? "0" : facturaProveedorSRI.getNumeroModificado();
/*  589: 669 */           secModificado.appendChild(doc.createTextNode(numeroModificado));
/*  590: 670 */           detalleCompras.appendChild(secModificado);
/*  591:     */           
/*  592:     */ 
/*  593: 673 */           Element autModificado = doc.createElement("autModificado");
/*  594:     */           
/*  595: 675 */           String autorizacionModificado = facturaProveedorSRI.getAutorizacionModificado().trim() == null ? "000" : facturaProveedorSRI.getAutorizacionModificado();
/*  596: 676 */           autModificado.appendChild(doc.createTextNode(autorizacionModificado));
/*  597: 677 */           detalleCompras.appendChild(autModificado);
/*  598:     */         }
/*  599:     */       }
/*  600:     */     }
/*  601:     */   }
/*  602:     */   
/*  603:     */   protected void ventasATS(Document doc, Element iva, int anio, int mes, int idOrganizacion, HashMap<String, FacturaClienteSRI> hmFacturas)
/*  604:     */   {
/*  605:     */     BigDecimal total;
/*  606:     */     Element ventas;
/*  607: 687 */     if (!hmFacturas.isEmpty())
/*  608:     */     {
/*  609: 689 */       total = BigDecimal.ZERO;
/*  610:     */       
/*  611:     */ 
/*  612: 692 */       ventas = doc.createElement("ventas");
/*  613: 693 */       iva.appendChild(ventas);
/*  614: 696 */       for (FacturaClienteSRI facturaClienteSRI : hmFacturas.values())
/*  615:     */       {
/*  616: 697 */         BigDecimal baseImponibleTarifaCero = facturaClienteSRI.getBaseImponibleTarifaCero();
/*  617: 698 */         BigDecimal baseImponibleDiferenteCero = facturaClienteSRI.getBaseImponibleDiferenteCero();
/*  618: 699 */         BigDecimal baseImponibleNoObjetoIva = facturaClienteSRI.getBaseImponibleNoObjetoIva();
/*  619: 700 */         total = baseImponibleTarifaCero.add(baseImponibleDiferenteCero).add(baseImponibleNoObjetoIva);
/*  620: 701 */         if ((facturaClienteSRI != null) && (facturaClienteSRI.getCodigoTipoComprobanteSRI() != null) && 
/*  621: 702 */           (facturaClienteSRI.getCodigoTipoComprobanteSRI().equals("04"))) {
/*  622: 703 */           total = total.negate();
/*  623:     */         }
/*  624: 706 */         Element detalleVentas = doc.createElement("detalleVentas");
/*  625: 707 */         ventas.appendChild(detalleVentas);
/*  626:     */         
/*  627:     */ 
/*  628: 710 */         Element tpIdCliente = doc.createElement("tpIdCliente");
/*  629: 711 */         tpIdCliente.appendChild(doc.createTextNode(FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaClienteSRI
/*  630: 712 */           .getCodigoTipoIdentificacion())));
/*  631: 713 */         detalleVentas.appendChild(tpIdCliente);
/*  632:     */         
/*  633:     */ 
/*  634: 716 */         Element idCliente = doc.createElement("idCliente");
/*  635: 717 */         idCliente.appendChild(doc.createTextNode(facturaClienteSRI.getIdentificacionCliente().trim().replace(" ", "").replace("_", "")));
/*  636: 718 */         detalleVentas.appendChild(idCliente);
/*  637: 720 */         if (!FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaClienteSRI.getCodigoTipoIdentificacion().trim()).equals("07"))
/*  638:     */         {
/*  639: 722 */           Element parteRelVtas = doc.createElement("parteRelVtas");
/*  640: 723 */           parteRelVtas.appendChild(doc.createTextNode("NO"));
/*  641: 724 */           detalleVentas.appendChild(parteRelVtas);
/*  642:     */         }
/*  643: 727 */         if (FuncionesUtiles.getTipoIdentificacionClienteSRI(facturaClienteSRI.getCodigoTipoIdentificacion().trim()).equals("06"))
/*  644:     */         {
/*  645: 729 */           Element tipoCliente = doc.createElement("tipoCliente");
/*  646: 730 */           tipoCliente.appendChild(doc.createTextNode("01"));
/*  647: 731 */           detalleVentas.appendChild(tipoCliente);
/*  648:     */           
/*  649:     */ 
/*  650: 734 */           Element denoCli = doc.createElement("denoCli");
/*  651: 735 */           denoCli.appendChild(doc.createTextNode(facturaClienteSRI.getIdentificacionCliente().trim().replace("_", "")));
/*  652: 736 */           detalleVentas.appendChild(denoCli);
/*  653:     */         }
/*  654: 740 */         Element tipoComprobante = doc.createElement("tipoComprobante");
/*  655: 741 */         tipoComprobante.appendChild(doc.createTextNode(facturaClienteSRI.getCodigoTipoComprobanteSRI().length() == 1 ? "0" + facturaClienteSRI
/*  656: 742 */           .getCodigoTipoComprobanteSRI() : facturaClienteSRI.getCodigoTipoComprobanteSRI()));
/*  657: 743 */         detalleVentas.appendChild(tipoComprobante);
/*  658:     */         
/*  659:     */ 
/*  660: 746 */         Element tipoEmision = doc.createElement("tipoEmision");
/*  661: 747 */         tipoEmision.appendChild(doc.createTextNode(facturaClienteSRI.isIndicadorDocumentoElectronico() ? "E" : "F"));
/*  662: 748 */         detalleVentas.appendChild(tipoEmision);
/*  663:     */         
/*  664:     */ 
/*  665: 751 */         Element numeroComprobantes = doc.createElement("numeroComprobantes");
/*  666: 752 */         numeroComprobantes.appendChild(doc.createTextNode(Long.toString(facturaClienteSRI.getNumeroComprobantes())));
/*  667: 753 */         detalleVentas.appendChild(numeroComprobantes);
/*  668:     */         
/*  669:     */ 
/*  670: 756 */         Element baseNoGraIva = doc.createElement("baseNoGraIva");
/*  671: 757 */         baseNoGraIva.appendChild(doc.createTextNode(facturaClienteSRI.getBaseImponibleNoObjetoIva().toString()));
/*  672: 758 */         detalleVentas.appendChild(baseNoGraIva);
/*  673:     */         
/*  674:     */ 
/*  675: 761 */         Element baseImponible = doc.createElement("baseImponible");
/*  676: 762 */         baseImponible.appendChild(doc.createTextNode(facturaClienteSRI.getBaseImponibleTarifaCero().toString()));
/*  677: 763 */         detalleVentas.appendChild(baseImponible);
/*  678:     */         
/*  679:     */ 
/*  680: 766 */         Element baseImpGrav = doc.createElement("baseImpGrav");
/*  681: 767 */         baseImpGrav.appendChild(doc.createTextNode(facturaClienteSRI.getBaseImponibleDiferenteCero().toString()));
/*  682: 768 */         detalleVentas.appendChild(baseImpGrav);
/*  683:     */         
/*  684:     */ 
/*  685: 771 */         Element montoIva = doc.createElement("montoIva");
/*  686: 772 */         montoIva.appendChild(doc.createTextNode(facturaClienteSRI.getMontoIva().toString()));
/*  687: 773 */         detalleVentas.appendChild(montoIva);
/*  688: 776 */         if (facturaClienteSRI.getDescuentoImpuesto().compareTo(BigDecimal.ZERO) > 0)
/*  689:     */         {
/*  690: 777 */           Element compensaciones = doc.createElement("compensaciones");
/*  691: 778 */           detalleVentas.appendChild(compensaciones);
/*  692:     */           
/*  693: 780 */           Element compensacion = doc.createElement("compensacion");
/*  694: 781 */           compensaciones.appendChild(compensacion);
/*  695:     */           
/*  696: 783 */           Element tipoCompe = doc.createElement("tipoCompe");
/*  697: 784 */           tipoCompe.appendChild(doc.createTextNode("01"));
/*  698: 785 */           compensacion.appendChild(tipoCompe);
/*  699:     */           
/*  700: 787 */           Element monto = doc.createElement("monto");
/*  701: 788 */           monto.appendChild(doc.createTextNode(facturaClienteSRI.getDescuentoImpuesto().toString()));
/*  702: 789 */           compensacion.appendChild(monto);
/*  703:     */         }
/*  704: 793 */         Element montoIce = doc.createElement("montoIce");
/*  705: 794 */         montoIce.appendChild(doc.createTextNode(facturaClienteSRI.getMontoIce().toString()));
/*  706: 795 */         detalleVentas.appendChild(montoIce);
/*  707:     */         
/*  708:     */ 
/*  709: 798 */         Element valorRetIva = doc.createElement("valorRetIva");
/*  710: 799 */         valorRetIva.appendChild(doc.createTextNode(facturaClienteSRI.getValorRetenidoIva().toString()));
/*  711: 800 */         detalleVentas.appendChild(valorRetIva);
/*  712:     */         
/*  713:     */ 
/*  714: 803 */         Element valorRetRenta = doc.createElement("valorRetRenta");
/*  715: 804 */         valorRetRenta.appendChild(doc.createTextNode(facturaClienteSRI.getValorRetenidoFuente().toString()));
/*  716: 805 */         detalleVentas.appendChild(valorRetRenta);
/*  717: 807 */         if (!facturaClienteSRI.getCodigoTipoComprobanteSRI().equals("04"))
/*  718:     */         {
/*  719: 810 */           formasDePago = doc.createElement("formasDePago");
/*  720: 811 */           detalleVentas.appendChild(formasDePago);
/*  721: 813 */           for (String codigo : facturaClienteSRI.getListaCodigoFormaPagoSri().values())
/*  722:     */           {
/*  723: 815 */             Element formaPago = doc.createElement("formaPago");
/*  724: 816 */             formaPago.appendChild(doc.createTextNode(codigo));
/*  725: 817 */             formasDePago.appendChild(formaPago);
/*  726:     */           }
/*  727:     */         }
/*  728:     */       }
/*  729:     */     }
/*  730:     */     Element formasDePago;
/*  731:     */   }
/*  732:     */   
/*  733:     */   protected void resumenVentasATS(Document doc, Element iva, int anio, int mes, int idOrganizacion, HashMap<String, BigDecimal[]> hmResumenFacturas)
/*  734:     */   {
/*  735: 828 */     if (!hmResumenFacturas.isEmpty())
/*  736:     */     {
/*  737: 831 */       Element ventasEstablecimiento = doc.createElement("ventasEstablecimiento");
/*  738: 832 */       iva.appendChild(ventasEstablecimiento);
/*  739:     */       
/*  740: 834 */       Iterator<Map.Entry<String, BigDecimal[]>> it = hmResumenFacturas.entrySet().iterator();
/*  741: 835 */       while (it.hasNext())
/*  742:     */       {
/*  743: 837 */         Map.Entry<String, BigDecimal[]> e = (Map.Entry)it.next();
/*  744:     */         
/*  745:     */ 
/*  746: 840 */         Element ventaEst = doc.createElement("ventaEst");
/*  747: 841 */         ventasEstablecimiento.appendChild(ventaEst);
/*  748:     */         
/*  749:     */ 
/*  750: 844 */         Element codEstab = doc.createElement("codEstab");
/*  751: 845 */         codEstab.appendChild(doc.createTextNode((String)e.getKey()));
/*  752: 846 */         ventaEst.appendChild(codEstab);
/*  753:     */         
/*  754:     */ 
/*  755: 849 */         Element ventasEstab = doc.createElement("ventasEstab");
/*  756: 850 */         ventasEstab.appendChild(doc.createTextNode(((BigDecimal[])e.getValue())[0].toString()));
/*  757: 851 */         ventaEst.appendChild(ventasEstab);
/*  758: 854 */         if (((BigDecimal[])e.getValue())[1].compareTo(BigDecimal.ZERO) != 0)
/*  759:     */         {
/*  760: 855 */           Element ivaComp = doc.createElement("ivaComp");
/*  761: 856 */           ivaComp.appendChild(doc.createTextNode(((BigDecimal[])e.getValue())[1].toString()));
/*  762: 857 */           ventaEst.appendChild(ivaComp);
/*  763:     */         }
/*  764:     */       }
/*  765:     */     }
/*  766:     */   }
/*  767:     */   
/*  768:     */   protected void anuladasATS(Document doc, Element iva, int anio, int mes, int idOrganizacion)
/*  769:     */   {
/*  770: 865 */     Element anulados = doc.createElement("anulados");
/*  771: 866 */     iva.appendChild(anulados);
/*  772: 869 */     for (AnuladoSRI anuladoSRI : this.servicioAnuladoSRI.obtenerAnuladosMes(anio, mes, idOrganizacion)) {
/*  773: 870 */       if ((!anuladoSRI.getNumeroDesde().equals(String.valueOf(0))) && (!anuladoSRI.getNumeroHasta().equals(String.valueOf(0))))
/*  774:     */       {
/*  775: 871 */         Element detalleAnulados = doc.createElement("detalleAnulados");
/*  776: 872 */         anulados.appendChild(detalleAnulados);
/*  777:     */         
/*  778:     */ 
/*  779: 875 */         Element tipoComprobante = doc.createElement("tipoComprobante");
/*  780: 876 */         tipoComprobante.appendChild(doc.createTextNode(anuladoSRI.getTipoComprobanteSRI().getCodigo()));
/*  781: 877 */         detalleAnulados.appendChild(tipoComprobante);
/*  782:     */         
/*  783:     */ 
/*  784: 880 */         Element establecimiento = doc.createElement("establecimiento");
/*  785: 881 */         establecimiento.appendChild(doc.createTextNode(anuladoSRI.getEstablecimiento()));
/*  786: 882 */         detalleAnulados.appendChild(establecimiento);
/*  787:     */         
/*  788:     */ 
/*  789: 885 */         Element puntoEmision = doc.createElement("puntoEmision");
/*  790: 886 */         puntoEmision.appendChild(doc.createTextNode(anuladoSRI.getPuntoEmision()));
/*  791: 887 */         detalleAnulados.appendChild(puntoEmision);
/*  792:     */         
/*  793:     */ 
/*  794: 890 */         Element secuencialInicio = doc.createElement("secuencialInicio");
/*  795: 891 */         secuencialInicio.appendChild(doc.createTextNode(anuladoSRI.getNumeroDesde()));
/*  796: 892 */         detalleAnulados.appendChild(secuencialInicio);
/*  797:     */         
/*  798:     */ 
/*  799: 895 */         Element secuencialFin = doc.createElement("secuencialFin");
/*  800: 896 */         secuencialFin.appendChild(doc.createTextNode(anuladoSRI.getNumeroHasta()));
/*  801: 897 */         detalleAnulados.appendChild(secuencialFin);
/*  802:     */         
/*  803:     */ 
/*  804: 900 */         Element autorizacion = doc.createElement("autorizacion");
/*  805: 901 */         autorizacion.appendChild(doc.createTextNode(anuladoSRI.getAutorizacion().trim()));
/*  806: 902 */         detalleAnulados.appendChild(autorizacion);
/*  807:     */       }
/*  808:     */     }
/*  809:     */   }
/*  810:     */   
/*  811:     */   protected void exportacionesATS(Document doc, Element iva, int anio, int mes, int idOrganizacion)
/*  812:     */     throws ExcepcionAS2Financiero
/*  813:     */   {
/*  814: 917 */     List<FacturaClienteSRI> listaFacturaExportacion = this.servicioFacturaClienteSRI.obtenerFacturasExportacionMes(anio, mes, idOrganizacion);
/*  815:     */     Map<String, BigDecimal> hmVAlorFobDae;
/*  816:     */     FacturaClienteSRI facSRI;
/*  817:     */     Element exportaciones;
/*  818: 918 */     if (!listaFacturaExportacion.isEmpty())
/*  819:     */     {
/*  820: 921 */       hmVAlorFobDae = new HashMap();
/*  821: 922 */       for (Iterator localIterator = listaFacturaExportacion.iterator(); localIterator.hasNext();)
/*  822:     */       {
/*  823: 922 */         facSRI = (FacturaClienteSRI)localIterator.next();
/*  824: 923 */         if ((facSRI.getRefrendo() != null) && (facSRI.getRefrendo().equals(RefrendoEnum.CON_REFRENDO)))
/*  825:     */         {
/*  826: 924 */           String numeroDAE = facSRI.getNumeroDAE();
/*  827: 925 */           BigDecimal valorFobDae = (BigDecimal)hmVAlorFobDae.get(numeroDAE);
/*  828:     */           
/*  829: 927 */           valorFobDae = (valorFobDae == null ? BigDecimal.ZERO : valorFobDae).add(facSRI.getValorFobComprobanteRefrendo() != null ? facSRI.getValorFobComprobanteRefrendo() : BigDecimal.ZERO);
/*  830: 928 */           hmVAlorFobDae.put(numeroDAE, valorFobDae);
/*  831:     */         }
/*  832:     */       }
/*  833: 933 */       exportaciones = doc.createElement("exportaciones");
/*  834: 934 */       iva.appendChild(exportaciones);
/*  835: 936 */       for (FacturaClienteSRI facturaClienteSRI : listaFacturaExportacion)
/*  836:     */       {
/*  837: 937 */         Element detalleExportaciones = doc.createElement("detalleExportaciones");
/*  838: 938 */         exportaciones.appendChild(detalleExportaciones);
/*  839:     */         
/*  840:     */ 
/*  841: 941 */         String tipoIdentificacionSRI = "21";
/*  842: 942 */         if (facturaClienteSRI.getTpIdClienteEx().equals("R")) {
/*  843: 943 */           tipoIdentificacionSRI = "22";
/*  844:     */         }
/*  845: 945 */         Element tpIdClienteEx = doc.createElement("tpIdClienteEx");
/*  846: 946 */         tpIdClienteEx.appendChild(doc.createTextNode(tipoIdentificacionSRI));
/*  847: 947 */         detalleExportaciones.appendChild(tpIdClienteEx);
/*  848:     */         
/*  849:     */ 
/*  850: 950 */         Element idClienteEx = doc.createElement("idClienteEx");
/*  851: 951 */         idClienteEx.appendChild(doc.createTextNode(String.valueOf(facturaClienteSRI.getIdCliente()).replace(" ", "").replace("_", "")));
/*  852: 952 */         detalleExportaciones.appendChild(idClienteEx);
/*  853:     */         
/*  854:     */ 
/*  855: 955 */         Element parteRelExp = doc.createElement("parteRelExp");
/*  856: 956 */         parteRelExp.appendChild(doc.createTextNode("NO"));
/*  857: 957 */         detalleExportaciones.appendChild(parteRelExp);
/*  858: 959 */         if (tipoIdentificacionSRI == "21")
/*  859:     */         {
/*  860: 961 */           Element tipoCli = doc.createElement("tipoCli");
/*  861: 962 */           tipoCli.appendChild(doc.createTextNode("01"));
/*  862: 963 */           detalleExportaciones.appendChild(tipoCli);
/*  863:     */           
/*  864:     */ 
/*  865: 966 */           Element denoExpCli = doc.createElement("denoExpCli");
/*  866: 967 */           denoExpCli.appendChild(doc.createTextNode(facturaClienteSRI.getIdCliente().trim().replace(" ", "").replace("_", "")));
/*  867: 968 */           detalleExportaciones.appendChild(denoExpCli);
/*  868:     */         }
/*  869: 972 */         Element tipoRegi = doc.createElement("tipoRegi");
/*  870: 973 */         tipoRegi.appendChild(doc.createTextNode("01"));
/*  871: 974 */         detalleExportaciones.appendChild(tipoRegi);
/*  872:     */         
/*  873:     */ 
/*  874: 977 */         Element paisEfecPagoGen = doc.createElement("paisEfecPagoGen");
/*  875: 978 */         paisEfecPagoGen.appendChild(doc.createTextNode(String.valueOf(facturaClienteSRI.getPaisEfecExp())));
/*  876: 979 */         detalleExportaciones.appendChild(paisEfecPagoGen);
/*  877:     */         
/*  878:     */ 
/*  879: 982 */         Element paisEfecExp = doc.createElement("paisEfecExp");
/*  880: 983 */         paisEfecExp.appendChild(doc.createTextNode(String.valueOf(facturaClienteSRI.getPaisEfecExp())));
/*  881: 984 */         detalleExportaciones.appendChild(paisEfecExp);
/*  882: 986 */         if (tipoIdentificacionSRI != "21")
/*  883:     */         {
/*  884: 988 */           Element pagoRegFis = doc.createElement("pagoRegFis");
/*  885: 989 */           pagoRegFis.appendChild(doc.createTextNode(String.valueOf(facturaClienteSRI.getExportParaisoFiscal())));
/*  886: 990 */           detalleExportaciones.appendChild(pagoRegFis);
/*  887:     */         }
/*  888: 993 */         Element exportacionDe = doc.createElement("exportacionDe");
/*  889: 994 */         exportacionDe.appendChild(doc.createTextNode("0" + String.valueOf(facturaClienteSRI.getTraRefrendo())));
/*  890: 995 */         detalleExportaciones.appendChild(exportacionDe);
/*  891: 996 */         if (!facturaClienteSRI.getTipoIngresoExterior().isEmpty())
/*  892:     */         {
/*  893: 998 */           Element tipIngExt = doc.createElement("tipIngExt");
/*  894: 999 */           tipIngExt.appendChild(doc.createTextNode(facturaClienteSRI.getTipoIngresoExterior()));
/*  895:1000 */           detalleExportaciones.appendChild(tipIngExt);
/*  896:     */           
/*  897:     */ 
/*  898:1003 */           Element ingExtGravOtroPais = doc.createElement("ingExtGravOtroPais");
/*  899:1004 */           ingExtGravOtroPais.appendChild(doc.createTextNode(facturaClienteSRI.isIngresoExteriorGraboImpuestos() ? "SI" : "NO"));
/*  900:1005 */           detalleExportaciones.appendChild(ingExtGravOtroPais);
/*  901:1008 */           if (facturaClienteSRI.isIngresoExteriorGraboImpuestos())
/*  902:     */           {
/*  903:1009 */             Element impuestoOtroPais = doc.createElement("impuestoOtroPais");
/*  904:1010 */             impuestoOtroPais.appendChild(doc.createTextNode(facturaClienteSRI.getValorImpuestoExportacion().toString()));
/*  905:1011 */             detalleExportaciones.appendChild(impuestoOtroPais);
/*  906:     */           }
/*  907:     */         }
/*  908:1015 */         Element tipoComprobante = doc.createElement("tipoComprobante");
/*  909:1016 */         if (facturaClienteSRI.getCodigoTipoComprobanteSRI() == null) {
/*  910:1017 */           facturaClienteSRI.setCodigoTipoComprobanteSRI("");
/*  911:     */         }
/*  912:1019 */         tipoComprobante.appendChild(doc.createTextNode(facturaClienteSRI.getCodigoTipoComprobanteSRI()));
/*  913:1020 */         detalleExportaciones.appendChild(tipoComprobante);
/*  914:     */         
/*  915:     */ 
/*  916:1023 */         Element distAduanero = doc.createElement("distAduanero");
/*  917:1024 */         if (facturaClienteSRI.getDistritoRefrendo() != null)
/*  918:     */         {
/*  919:1025 */           distAduanero.appendChild(doc.createTextNode(facturaClienteSRI.getDistritoRefrendo()));
/*  920:1026 */           detalleExportaciones.appendChild(distAduanero);
/*  921:     */         }
/*  922:1030 */         Element anioRefrendo = doc.createElement("anio");
/*  923:1031 */         if (facturaClienteSRI.getAnioRefrendo() != null)
/*  924:     */         {
/*  925:1032 */           anioRefrendo.appendChild(doc.createTextNode(String.valueOf(facturaClienteSRI.getAnioRefrendo())));
/*  926:1033 */           detalleExportaciones.appendChild(anioRefrendo);
/*  927:     */         }
/*  928:1037 */         Element regimen = doc.createElement("regimen");
/*  929:1038 */         if (facturaClienteSRI.getRegimenRefrendo() != null)
/*  930:     */         {
/*  931:1039 */           regimen.appendChild(doc.createTextNode(facturaClienteSRI.getRegimenRefrendo()));
/*  932:1040 */           detalleExportaciones.appendChild(regimen);
/*  933:     */         }
/*  934:1044 */         Element correlativo = doc.createElement("correlativo");
/*  935:1045 */         if ((facturaClienteSRI.getCorrelativoRefrendo() != null) && (!facturaClienteSRI.getCorrelativoRefrendo().isEmpty()))
/*  936:     */         {
/*  937:1046 */           correlativo.appendChild(doc.createTextNode(facturaClienteSRI.getCorrelativoRefrendo()));
/*  938:1047 */           detalleExportaciones.appendChild(correlativo);
/*  939:     */         }
/*  940:1050 */         if ((facturaClienteSRI.getRefrendo() != null) && (facturaClienteSRI.getRefrendo().equals(RefrendoEnum.CON_REFRENDO)) && 
/*  941:1051 */           (facturaClienteSRI.getDocumentoTransporteRefrendo() != null))
/*  942:     */         {
/*  943:1053 */           Element docTransp = doc.createElement("docTransp");
/*  944:1054 */           docTransp.appendChild(doc.createTextNode(facturaClienteSRI.getDocumentoTransporteRefrendo()));
/*  945:1055 */           detalleExportaciones.appendChild(docTransp);
/*  946:     */         }
/*  947:1059 */         Element fechaEmbarque = doc.createElement("fechaEmbarque");
/*  948:1060 */         if (facturaClienteSRI.getFechaTransaccion() == null) {
/*  949:1061 */           facturaClienteSRI.setFechaTransaccion(FuncionesUtiles.getFecha(1, 1, 1900));
/*  950:     */         }
/*  951:1063 */         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
/*  952:1064 */         fechaEmbarque.appendChild(doc.createTextNode(sdf.format(facturaClienteSRI.getFechaTransaccion())));
/*  953:1065 */         detalleExportaciones.appendChild(fechaEmbarque);
/*  954:     */         
/*  955:     */ 
/*  956:1068 */         Element valorFOB = doc.createElement("valorFOB");
/*  957:1069 */         if (facturaClienteSRI.getValorFobRefrendo() == null) {
/*  958:1070 */           facturaClienteSRI.setValorFobRefrendo(BigDecimal.ZERO);
/*  959:     */         }
/*  960:1072 */         BigDecimal valorFobDae = (BigDecimal)hmVAlorFobDae.get(facturaClienteSRI.getNumeroDAE());
/*  961:1073 */         valorFobDae = valorFobDae != null ? valorFobDae : facturaClienteSRI.getValorFobRefrendo();
/*  962:1074 */         valorFOB.appendChild(doc.createTextNode(valorFobDae.toString()));
/*  963:1075 */         detalleExportaciones.appendChild(valorFOB);
/*  964:     */         
/*  965:     */ 
/*  966:1078 */         Element valorFOBComprobante = doc.createElement("valorFOBComprobante");
/*  967:1079 */         if (facturaClienteSRI.getValorFobComprobanteRefrendo() == null) {
/*  968:1080 */           facturaClienteSRI.setValorFobComprobanteRefrendo(BigDecimal.ZERO);
/*  969:     */         }
/*  970:1082 */         valorFOBComprobante.appendChild(doc.createTextNode(facturaClienteSRI.getValorFobComprobanteRefrendo().toString()));
/*  971:1083 */         detalleExportaciones.appendChild(valorFOBComprobante);
/*  972:     */         
/*  973:     */ 
/*  974:1086 */         Element establecimiento = doc.createElement("establecimiento");
/*  975:1087 */         if (facturaClienteSRI.getEstablecimiento() == null) {
/*  976:1088 */           facturaClienteSRI.setEstablecimiento("");
/*  977:     */         }
/*  978:1090 */         establecimiento.appendChild(doc.createTextNode(facturaClienteSRI.getEstablecimiento()));
/*  979:1091 */         detalleExportaciones.appendChild(establecimiento);
/*  980:     */         
/*  981:     */ 
/*  982:1094 */         Element puntoEmision = doc.createElement("puntoEmision");
/*  983:1095 */         if (facturaClienteSRI.getPuntoEmision() == null) {
/*  984:1096 */           facturaClienteSRI.setPuntoEmision("");
/*  985:     */         }
/*  986:1098 */         puntoEmision.appendChild(doc.createTextNode(facturaClienteSRI.getPuntoEmision()));
/*  987:1099 */         detalleExportaciones.appendChild(puntoEmision);
/*  988:     */         
/*  989:     */ 
/*  990:1102 */         Element secuencial = doc.createElement("secuencial");
/*  991:1103 */         if (facturaClienteSRI.getNumero() == null) {
/*  992:1104 */           facturaClienteSRI.setNumero("");
/*  993:     */         }
/*  994:1106 */         secuencial.appendChild(doc.createTextNode(facturaClienteSRI.getNumero()));
/*  995:1107 */         detalleExportaciones.appendChild(secuencial);
/*  996:     */         
/*  997:     */ 
/*  998:1110 */         Element autorizacion = doc.createElement("autorizacion");
/*  999:1111 */         if (facturaClienteSRI.getAutorizacion() == null) {
/* 1000:1112 */           facturaClienteSRI.setAutorizacion("");
/* 1001:     */         }
/* 1002:1114 */         autorizacion.appendChild(doc.createTextNode(facturaClienteSRI.getAutorizacion().trim()));
/* 1003:1115 */         detalleExportaciones.appendChild(autorizacion);
/* 1004:     */         
/* 1005:     */ 
/* 1006:1118 */         Element fechaEmision = doc.createElement("fechaEmision");
/* 1007:1119 */         if (facturaClienteSRI.getFechaEmision() == null) {
/* 1008:1120 */           facturaClienteSRI.setFechaEmision(FuncionesUtiles.getFecha(1, 1, 1900));
/* 1009:     */         }
/* 1010:1122 */         fechaEmision.appendChild(doc.createTextNode(sdf.format(facturaClienteSRI.getFechaEmision())));
/* 1011:1123 */         detalleExportaciones.appendChild(fechaEmision);
/* 1012:     */       }
/* 1013:     */     }
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public StreamedContent generarAnexo(int anio, int mes, int idOrganizacion)
/* 1017:     */     throws ParserConfigurationException, TransformerException, FileNotFoundException, ExcepcionAS2Financiero
/* 1018:     */   {
/* 1019:1138 */     StreamedContent file = null;
/* 1020:1139 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 1021:1140 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 1022:     */     
/* 1023:     */ 
/* 1024:1143 */     String strMes = (mes < 10 ? "0" : "") + mes;
/* 1025:     */     
/* 1026:     */ 
/* 1027:1146 */     Document doc = docBuilder.newDocument();
/* 1028:     */     
/* 1029:1148 */     Element iva = doc.createElement("iva");
/* 1030:1149 */     doc.appendChild(iva);
/* 1031:     */     
/* 1032:     */ 
/* 1033:1152 */     List<FacturaClienteSRI> listaFacturas = this.servicioFacturaClienteSRI.obtenerFacturasMes(anio, mes, idOrganizacion);
/* 1034:     */     
/* 1035:1154 */     HashMap<String, BigDecimal[]> hmResumenFacturas = getResumenVentasATS(anio, mes, idOrganizacion, listaFacturas);
/* 1036:1155 */     cabeceraATS(strMes, doc, iva, anio, hmResumenFacturas);
/* 1037:     */     
/* 1038:     */ 
/* 1039:1158 */     comprasATS(doc, iva, anio, mes, idOrganizacion);
/* 1040:     */     
/* 1041:     */ 
/* 1042:1161 */     HashMap<String, FacturaClienteSRI> hmFacturas = getVentasATS(anio, mes, idOrganizacion, listaFacturas);
/* 1043:1162 */     ventasATS(doc, iva, anio, mes, idOrganizacion, hmFacturas);
/* 1044:     */     
/* 1045:     */ 
/* 1046:1165 */     resumenVentasATS(doc, iva, anio, mes, idOrganizacion, hmResumenFacturas);
/* 1047:     */     
/* 1048:     */ 
/* 1049:1168 */     exportacionesATS(doc, iva, anio, mes, idOrganizacion);
/* 1050:     */     
/* 1051:     */ 
/* 1052:     */ 
/* 1053:     */ 
/* 1054:1173 */     anuladasATS(doc, iva, anio, mes, idOrganizacion);
/* 1055:     */     
/* 1056:     */ 
/* 1057:1176 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 1058:1177 */     Transformer transformer = transformerFactory.newTransformer();
/* 1059:     */     
/* 1060:1179 */     DOMSource source = new DOMSource(doc);
/* 1061:     */     
/* 1062:1181 */     String directorioAnexo = getDirectorioAnexos();
/* 1063:1182 */     String nombreArchivo = "AT-" + strMes + anio + ".xml";
/* 1064:1183 */     String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 1065:1184 */     Result result = new StreamResult(new File(rutaArchivo));
/* 1066:     */     
/* 1067:     */ 
/* 1068:     */ 
/* 1069:     */ 
/* 1070:1189 */     transformer.transform(source, result);
/* 1071:     */     
/* 1072:     */ 
/* 1073:1192 */     file = FuncionesUtiles.descargarArchivo(rutaArchivo, "application/xml", nombreArchivo);
/* 1074:     */     
/* 1075:1194 */     return file;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   private HashMap<String, FacturaClienteSRI> getVentasATS(int anio, int mes, int idOrganizacion, List<FacturaClienteSRI> listaFacturasMes)
/* 1079:     */   {
/* 1080:1200 */     HashMap<String, FacturaClienteSRI> hmFacturas = new HashMap();
/* 1081:     */     
/* 1082:     */ 
/* 1083:1203 */     FacturaClienteSRI facturaClienteSRI = null;
/* 1084:1204 */     BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/* 1085:1205 */     BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/* 1086:1206 */     BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/* 1087:1207 */     BigDecimal baseMontoIva = BigDecimal.ZERO;
/* 1088:1208 */     BigDecimal baseMontoIce = BigDecimal.ZERO;
/* 1089:1209 */     BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/* 1090:1211 */     for (FacturaClienteSRI fcSRI : listaFacturasMes)
/* 1091:     */     {
/* 1092:1213 */       String clave = fcSRI.getCodigoTipoIdentificacion() + ":" + fcSRI.getCodigoTipoComprobanteSRI() + ":" + fcSRI.getIdentificacionCliente() + ":" + (fcSRI.isIndicadorDocumentoElectronico() ? "E" : "F");
/* 1093:1214 */       clave = clave.replace(" ", "").trim();
/* 1094:1215 */       facturaClienteSRI = (FacturaClienteSRI)hmFacturas.get(clave);
/* 1095:1216 */       if (facturaClienteSRI == null)
/* 1096:     */       {
/* 1097:1217 */         fcSRI.getListaCodigoFormaPagoSri().put(fcSRI.getCodigoFormaPagoSRI(), fcSRI.getCodigoFormaPagoSRI());
/* 1098:1218 */         hmFacturas.put(clave, fcSRI);
/* 1099:     */       }
/* 1100:     */       else
/* 1101:     */       {
/* 1102:1220 */         baseImponibleDiferenteCero = fcSRI.getBaseImponibleDiferenteCero();
/* 1103:1221 */         baseImponibleNoObjetoIva = fcSRI.getBaseImponibleNoObjetoIva();
/* 1104:1222 */         baseImponibleTarifaCero = fcSRI.getBaseImponibleTarifaCero();
/* 1105:1223 */         baseMontoIva = fcSRI.getMontoIva();
/* 1106:1224 */         baseMontoIce = fcSRI.getMontoIce();
/* 1107:1225 */         descuentoImpuesto = fcSRI.getDescuentoImpuesto();
/* 1108:     */         
/* 1109:1227 */         facturaClienteSRI.getListaCodigoFormaPagoSri().put(fcSRI.getCodigoFormaPagoSRI(), fcSRI.getCodigoFormaPagoSRI());
/* 1110:1228 */         facturaClienteSRI.setBaseImponibleDiferenteCero(baseImponibleDiferenteCero.add(facturaClienteSRI.getBaseImponibleDiferenteCero()));
/* 1111:1229 */         facturaClienteSRI.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIva.add(facturaClienteSRI.getBaseImponibleNoObjetoIva()));
/* 1112:1230 */         facturaClienteSRI.setBaseImponibleTarifaCero(baseImponibleTarifaCero.add(facturaClienteSRI.getBaseImponibleTarifaCero()));
/* 1113:1231 */         facturaClienteSRI.setMontoIva(baseMontoIva.add(facturaClienteSRI.getMontoIva()));
/* 1114:1232 */         facturaClienteSRI.setMontoIce(baseMontoIce.add(facturaClienteSRI.getMontoIce()));
/* 1115:1233 */         facturaClienteSRI.setDescuentoImpuesto(descuentoImpuesto.add(facturaClienteSRI.getDescuentoImpuesto()));
/* 1116:1234 */         facturaClienteSRI.setNumeroComprobantes(fcSRI.getNumeroComprobantes() + facturaClienteSRI.getNumeroComprobantes());
/* 1117:     */       }
/* 1118:     */     }
/* 1119:1239 */     for (FacturaClienteSRI fcSRI : this.servicioFacturaClienteSRI.obtenerValoresRetenidosMes(anio, mes, idOrganizacion))
/* 1120:     */     {
/* 1121:1240 */       facturaClienteSRI = null;
/* 1122:     */       
/* 1123:1242 */       String clave = fcSRI.getCodigoTipoIdentificacion() + ":" + fcSRI.getCodigoTipoComprobanteSRI() + ":" + fcSRI.getIdentificacionCliente() + ":" + (fcSRI.isIndicadorDocumentoElectronico() ? "E" : "F");
/* 1124:1243 */       clave = clave.replace(" ", "").trim();
/* 1125:1244 */       facturaClienteSRI = (FacturaClienteSRI)hmFacturas.get(clave);
/* 1126:1246 */       if (facturaClienteSRI == null)
/* 1127:     */       {
/* 1128:1247 */         fcSRI.getListaCodigoFormaPagoSri().put(fcSRI.getCodigoFormaPagoSRI(), fcSRI.getCodigoFormaPagoSRI());
/* 1129:1248 */         hmFacturas.put(clave, fcSRI);
/* 1130:     */       }
/* 1131:     */       else
/* 1132:     */       {
/* 1133:1250 */         facturaClienteSRI.getListaCodigoFormaPagoSri().put(fcSRI.getCodigoFormaPagoSRI(), fcSRI.getCodigoFormaPagoSRI());
/* 1134:1251 */         facturaClienteSRI.setValorRetenidoFuente(facturaClienteSRI.getValorRetenidoFuente().add(fcSRI.getValorRetenidoFuente()));
/* 1135:1252 */         facturaClienteSRI.setValorRetenidoIva(facturaClienteSRI.getValorRetenidoIva().add(fcSRI.getValorRetenidoIva()));
/* 1136:     */       }
/* 1137:     */     }
/* 1138:1256 */     return hmFacturas;
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   private HashMap<String, BigDecimal[]> getResumenVentasATS(int anio, int mes, int idOrganizacion, List<FacturaClienteSRI> lista)
/* 1142:     */   {
/* 1143:1261 */     HashMap<String, BigDecimal[]> hmResumenFacturas = new HashMap();
/* 1144:1262 */     BigDecimal total = BigDecimal.ZERO;
/* 1145:1264 */     for (FacturaClienteSRI facturaSRI : lista)
/* 1146:     */     {
/* 1147:1266 */       String clave = facturaSRI.getEstablecimiento();
/* 1148:1267 */       BigDecimal baseImponibleTarifaCero = facturaSRI.getBaseImponibleTarifaCero();
/* 1149:1268 */       BigDecimal baseImponibleDiferenteCero = facturaSRI.getBaseImponibleDiferenteCero();
/* 1150:1269 */       BigDecimal baseImponibleNoObjetoIva = facturaSRI.getBaseImponibleNoObjetoIva();
/* 1151:1270 */       BigDecimal compensacionSolidaria = facturaSRI.getDescuentoImpuesto() == null ? BigDecimal.ZERO : facturaSRI.getDescuentoImpuesto();
/* 1152:1271 */       if (!facturaSRI.isIndicadorDocumentoElectronico())
/* 1153:     */       {
/* 1154:1272 */         total = baseImponibleTarifaCero.add(baseImponibleDiferenteCero).add(baseImponibleNoObjetoIva);
/* 1155:     */       }
/* 1156:     */       else
/* 1157:     */       {
/* 1158:1274 */         total = BigDecimal.ZERO;
/* 1159:1275 */         compensacionSolidaria = BigDecimal.ZERO;
/* 1160:     */       }
/* 1161:1277 */       if (!facturaSRI.getCodigoTipoComprobanteSRI().equals("41"))
/* 1162:     */       {
/* 1163:1278 */         if ((facturaSRI != null) && (facturaSRI.getCodigoTipoComprobanteSRI() != null) && (facturaSRI.getCodigoTipoComprobanteSRI().equals("04")))
/* 1164:     */         {
/* 1165:1279 */           total = total.negate();
/* 1166:1280 */           compensacionSolidaria = compensacionSolidaria.negate();
/* 1167:     */         }
/* 1168:1282 */         if (hmResumenFacturas.containsKey(clave))
/* 1169:     */         {
/* 1170:1283 */           BigDecimal[] valores = (BigDecimal[])hmResumenFacturas.get(clave);System.out.println(valores[0] + "\t" + clave);
/* 1171:1284 */           valores[0] = total.add(valores[0]);
/* 1172:1285 */           valores[1] = compensacionSolidaria.add(valores[1]);
/* 1173:1286 */           hmResumenFacturas.put(clave, valores);
/* 1174:     */         }
/* 1175:     */         else
/* 1176:     */         {
/* 1177:1288 */           BigDecimal[] valores = new BigDecimal[2];
/* 1178:1289 */           valores[0] = total;
/* 1179:1290 */           valores[1] = compensacionSolidaria;
/* 1180:1291 */           hmResumenFacturas.put(clave, valores);
/* 1181:     */         }
/* 1182:     */       }
/* 1183:     */     }
/* 1184:1296 */     return hmResumenFacturas;
/* 1185:     */   }
/* 1186:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.AnexoATSBean
 * JD-Core Version:    0.7.0.1
 */