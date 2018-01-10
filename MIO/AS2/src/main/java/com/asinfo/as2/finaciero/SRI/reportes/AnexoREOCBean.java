/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   6:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   7:    */ import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
/*   8:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   9:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  10:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.FileNotFoundException;
/*  15:    */ import java.io.Serializable;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.math.RoundingMode;
/*  18:    */ import java.text.SimpleDateFormat;
/*  19:    */ import java.util.List;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.xml.parsers.DocumentBuilder;
/*  23:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  24:    */ import javax.xml.parsers.ParserConfigurationException;
/*  25:    */ import javax.xml.transform.Result;
/*  26:    */ import javax.xml.transform.Transformer;
/*  27:    */ import javax.xml.transform.TransformerException;
/*  28:    */ import javax.xml.transform.TransformerFactory;
/*  29:    */ import javax.xml.transform.dom.DOMSource;
/*  30:    */ import javax.xml.transform.stream.StreamResult;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.model.StreamedContent;
/*  33:    */ import org.w3c.dom.Document;
/*  34:    */ import org.w3c.dom.Element;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class AnexoREOCBean
/*  39:    */   extends AnexoBaseBean
/*  40:    */   implements Serializable
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = 2612397541855104267L;
/*  43:    */   
/*  44:    */   protected String cabeceraREOC(String strMes, int strAnio, Document document, Element reoc, int idOrganizacion)
/*  45:    */   {
/*  46: 66 */     Element numeroRuc = document.createElement("numeroRuc");
/*  47: 67 */     numeroRuc.appendChild(document.createTextNode(AppUtil.getOrganizacion().getIdentificacion()));
/*  48: 68 */     reoc.appendChild(numeroRuc);
/*  49:    */     
/*  50:    */ 
/*  51: 71 */     Element anioElement = document.createElement("anio");
/*  52: 72 */     anioElement.appendChild(document.createTextNode(String.valueOf(strAnio)));
/*  53: 73 */     reoc.appendChild(anioElement);
/*  54:    */     
/*  55:    */ 
/*  56: 76 */     Element mes = document.createElement("mes");
/*  57: 77 */     mes.appendChild(document.createTextNode(strMes));
/*  58: 78 */     reoc.appendChild(mes);
/*  59:    */     
/*  60: 80 */     return strMes;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void comprasREOC(Document document, Element reoc, int anio, int mes, int idOrganizacion)
/*  64:    */   {
/*  65: 92 */     SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
/*  66:    */     
/*  67:    */ 
/*  68: 95 */     List<FacturaProveedorSRI> listaFacturaProveedorSRI = this.servicioFacturaProveedorSRI.obtenerFacturasMes(anio, mes, idOrganizacion);
/*  69:    */     Element compras;
/*  70: 98 */     if (!listaFacturaProveedorSRI.isEmpty())
/*  71:    */     {
/*  72:101 */       compras = document.createElement("compras");
/*  73:102 */       reoc.appendChild(compras);
/*  74:104 */       for (FacturaProveedorSRI facturaProveedorSRI : listaFacturaProveedorSRI)
/*  75:    */       {
/*  76:105 */         boolean existeRetencion = false;
/*  77:    */         
/*  78:107 */         Element detalleCompras = document.createElement("detalleCompras");
/*  79:108 */         compras.appendChild(detalleCompras);
/*  80:    */         
/*  81:    */ 
/*  82:111 */         Element tpIdProv = document.createElement("tpIdProv");
/*  83:112 */         String tipoIdentificacionSRI = "03";
/*  84:113 */         if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("R")) {
/*  85:114 */           tipoIdentificacionSRI = "01";
/*  86:115 */         } else if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("C")) {
/*  87:116 */           tipoIdentificacionSRI = "02";
/*  88:117 */         } else if (facturaProveedorSRI.getTipoIdentificacion().getCodigo().equals("P")) {
/*  89:118 */           tipoIdentificacionSRI = "03";
/*  90:    */         }
/*  91:121 */         tpIdProv.appendChild(document.createTextNode(tipoIdentificacionSRI));
/*  92:122 */         detalleCompras.appendChild(tpIdProv);
/*  93:    */         
/*  94:    */ 
/*  95:125 */         Element idProv = document.createElement("idProv");
/*  96:126 */         idProv.appendChild(document.createTextNode(facturaProveedorSRI.getIdentificacionProveedor()));
/*  97:127 */         detalleCompras.appendChild(idProv);
/*  98:    */         
/*  99:    */ 
/* 100:130 */         Element tipoComprobante = document.createElement("tipoComp");
/* 101:131 */         tipoComprobante.appendChild(document.createTextNode(facturaProveedorSRI.getTipoComprobanteSRI().getCodigo()));
/* 102:132 */         detalleCompras.appendChild(tipoComprobante);
/* 103:    */         
/* 104:    */ 
/* 105:135 */         Element autorizacion = document.createElement("aut");
/* 106:136 */         autorizacion.appendChild(document.createTextNode(facturaProveedorSRI.getAutorizacion()));
/* 107:137 */         detalleCompras.appendChild(autorizacion);
/* 108:    */         
/* 109:    */ 
/* 110:140 */         Element establecimiento = document.createElement("estab");
/* 111:141 */         establecimiento.appendChild(document.createTextNode(facturaProveedorSRI.getEstablecimiento()));
/* 112:142 */         detalleCompras.appendChild(establecimiento);
/* 113:    */         
/* 114:    */ 
/* 115:145 */         Element puntoEmision = document.createElement("ptoEmi");
/* 116:146 */         puntoEmision.appendChild(document.createTextNode(facturaProveedorSRI.getPuntoEmision()));
/* 117:147 */         detalleCompras.appendChild(puntoEmision);
/* 118:    */         
/* 119:    */ 
/* 120:150 */         Element secuencial = document.createElement("sec");
/* 121:151 */         secuencial.appendChild(document.createTextNode(facturaProveedorSRI.getNumero()));
/* 122:152 */         detalleCompras.appendChild(secuencial);
/* 123:    */         
/* 124:    */ 
/* 125:155 */         Element fechaEmision = document.createElement("fechaEmiCom");
/* 126:156 */         fechaEmision.appendChild(document.createTextNode(formatoFecha.format(facturaProveedorSRI.getFechaEmision())));
/* 127:157 */         detalleCompras.appendChild(fechaEmision);
/* 128:    */         
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:162 */         Element air = document.createElement("air");
/* 133:163 */         detalleCompras.appendChild(air);
/* 134:    */         
/* 135:    */ 
/* 136:166 */         facturaProveedorSRI = this.servicioFacturaProveedorSRI.cargarDetalle(facturaProveedorSRI.getId());
/* 137:168 */         for (DetalleFacturaProveedorSRI detalleFacturaProveedorSRI : facturaProveedorSRI.getListaDetalleFacturaProveedorSRI())
/* 138:    */         {
/* 139:170 */           Element detalleAir = document.createElement("detalleAir");
/* 140:171 */           air.appendChild(detalleAir);
/* 141:    */           
/* 142:    */ 
/* 143:174 */           Element codRetAir = document.createElement("codRetAir");
/* 144:175 */           codRetAir.appendChild(document.createTextNode(detalleFacturaProveedorSRI.getConceptoRetencionSRI().getCodigo()));
/* 145:176 */           detalleAir.appendChild(codRetAir);
/* 146:    */           
/* 147:    */ 
/* 148:179 */           Element porcentajeAir = document.createElement("porcentaje");
/* 149:180 */           porcentajeAir.appendChild(document.createTextNode(detalleFacturaProveedorSRI.getPorcentajeRetencion()
/* 150:181 */             .setScale(1, RoundingMode.HALF_UP).toString()));
/* 151:182 */           detalleAir.appendChild(porcentajeAir);
/* 152:    */           
/* 153:    */ 
/* 154:185 */           Element baseImponible = document.createElement("base0");
/* 155:186 */           baseImponible.appendChild(document.createTextNode(detalleFacturaProveedorSRI.getBaseImponibleTarifaCero().toString()));
/* 156:187 */           detalleAir.appendChild(baseImponible);
/* 157:    */           
/* 158:    */ 
/* 159:190 */           Element baseImpGrav = document.createElement("baseGrav");
/* 160:191 */           baseImpGrav.appendChild(document.createTextNode(detalleFacturaProveedorSRI.getBaseImponibleDiferenteCero().toString()));
/* 161:192 */           detalleAir.appendChild(baseImpGrav);
/* 162:    */           
/* 163:    */ 
/* 164:195 */           Element baseNoGraIva = document.createElement("baseNoGrav");
/* 165:196 */           baseNoGraIva.appendChild(document.createTextNode(detalleFacturaProveedorSRI.getBaseImponibleNoObjetoIva().toString()));
/* 166:197 */           detalleAir.appendChild(baseNoGraIva);
/* 167:    */           
/* 168:    */ 
/* 169:200 */           Element valRetAir = document.createElement("valRetAir");
/* 170:201 */           valRetAir.appendChild(document.createTextNode(detalleFacturaProveedorSRI.getValorRetencion().toString()));
/* 171:202 */           detalleAir.appendChild(valRetAir);
/* 172:203 */           if (detalleFacturaProveedorSRI.getValorRetencion().compareTo(BigDecimal.ZERO) > 0) {
/* 173:204 */             existeRetencion = true;
/* 174:    */           }
/* 175:    */         }
/* 176:211 */         if (existeRetencion)
/* 177:    */         {
/* 178:213 */           Element autRetencion = document.createElement("autRet");
/* 179:214 */           String autorizacionRetencion = facturaProveedorSRI.getAutorizacionRetencion();
/* 180:215 */           if ((autorizacionRetencion == null) || (autorizacionRetencion.isEmpty())) {
/* 181:216 */             autorizacionRetencion = "000";
/* 182:    */           }
/* 183:218 */           autRetencion.appendChild(document.createTextNode(autorizacionRetencion));
/* 184:219 */           detalleCompras.appendChild(autRetencion);
/* 185:    */           
/* 186:    */ 
/* 187:222 */           Element estabRetencion = document.createElement("estabRet");
/* 188:223 */           estabRetencion.appendChild(document.createTextNode(facturaProveedorSRI.getEstablecimientoRetencion()));
/* 189:224 */           detalleCompras.appendChild(estabRetencion);
/* 190:    */           
/* 191:    */ 
/* 192:227 */           Element ptoEmiRetencion = document.createElement("ptoEmiRet");
/* 193:228 */           ptoEmiRetencion.appendChild(document.createTextNode(facturaProveedorSRI.getPuntoEmisionRetencion()));
/* 194:229 */           detalleCompras.appendChild(ptoEmiRetencion);
/* 195:    */           
/* 196:    */ 
/* 197:232 */           Element secRetencion = document.createElement("secRet");
/* 198:233 */           secRetencion.appendChild(document.createTextNode(facturaProveedorSRI.getNumeroRetencion()));
/* 199:234 */           detalleCompras.appendChild(secRetencion);
/* 200:    */           
/* 201:    */ 
/* 202:237 */           Element fechaEmiRet = document.createElement("fechaEmiRet");
/* 203:    */           
/* 204:239 */           String fechaEmisionRetencion = facturaProveedorSRI.getFechaEmisionRetencion() == null ? "00/00/0000" : formatoFecha.format(facturaProveedorSRI.getFechaEmisionRetencion());
/* 205:240 */           fechaEmiRet.appendChild(document.createTextNode(fechaEmisionRetencion));
/* 206:241 */           detalleCompras.appendChild(fechaEmiRet);
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public StreamedContent generarAnexo(int anio, int mes, int idOrganizacion)
/* 213:    */   {
/* 214:280 */     LOG.info("Ingreso en generar REOC");
/* 215:281 */     StreamedContent file = null;
/* 216:    */     try
/* 217:    */     {
/* 218:283 */       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 219:284 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 220:    */       
/* 221:    */ 
/* 222:287 */       String strMes = (mes < 10 ? "0" : "") + mes;
/* 223:    */       
/* 224:    */ 
/* 225:290 */       Document document = docBuilder.newDocument();
/* 226:    */       
/* 227:292 */       Element reoc = document.createElement("reoc");
/* 228:293 */       document.appendChild(reoc);
/* 229:    */       
/* 230:    */ 
/* 231:296 */       cabeceraREOC(strMes, anio, document, reoc, idOrganizacion);
/* 232:    */       
/* 233:    */ 
/* 234:299 */       comprasREOC(document, reoc, anio, mes, idOrganizacion);
/* 235:    */       
/* 236:    */ 
/* 237:302 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 238:303 */       Transformer transformer = transformerFactory.newTransformer();
/* 239:    */       
/* 240:305 */       DOMSource domSource = new DOMSource(document);
/* 241:    */       
/* 242:307 */       String directorioAnexo = getDirectorioAnexos();
/* 243:308 */       String nombreArchivo = "REOC" + strMes + anio + ".xml";
/* 244:309 */       String rutaArchivo = directorioAnexo + File.separator + nombreArchivo;
/* 245:310 */       Result result = new StreamResult(new File(rutaArchivo));
/* 246:    */       
/* 247:312 */       transformer.transform(domSource, result);
/* 248:    */       
/* 249:    */ 
/* 250:315 */       file = FuncionesUtiles.descargarArchivo(rutaArchivo, "application/xml", nombreArchivo);
/* 251:    */       
/* 252:317 */       LOG.info("Archivo REOC generado correctamente");
/* 253:318 */       addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_generado"));
/* 254:    */     }
/* 255:    */     catch (ParserConfigurationException pce)
/* 256:    */     {
/* 257:321 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 258:322 */       LOG.error("Error al generar el REOC " + pce);
/* 259:    */     }
/* 260:    */     catch (TransformerException tfe)
/* 261:    */     {
/* 262:324 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 263:325 */       LOG.error("Error al generar el REOC " + tfe);
/* 264:    */     }
/* 265:    */     catch (FileNotFoundException e)
/* 266:    */     {
/* 267:327 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 268:328 */       LOG.error("Archivo no encontrado REOC " + e);
/* 269:    */     }
/* 270:    */     catch (Exception e)
/* 271:    */     {
/* 272:330 */       addErrorMessage(getLanguageController().getMensaje("msg_error_archivo_generado"));
/* 273:331 */       LOG.error("Error al generar el REOC " + e);
/* 274:    */     }
/* 275:333 */     return file;
/* 276:    */   }
/* 277:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.AnexoREOCBean
 * JD-Core Version:    0.7.0.1
 */