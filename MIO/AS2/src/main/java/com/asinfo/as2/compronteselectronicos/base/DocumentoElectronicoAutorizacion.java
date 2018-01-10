/*   1:    */ package com.asinfo.as2.compronteselectronicos.base;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   4:    */ import com.asinfo.as2.utils.ArchivoUtil;
/*   5:    */ import ec.com.facturae.ws.comprobante.FacturaeException;
/*   6:    */ import ec.com.facturae.ws.comprobante.ServicioWebComprobante;
/*   7:    */ import ec.com.facturae.ws.comprobante.impl.ComprobanteServiceLocator;
/*   8:    */ import ec.gob.sri.comprobantes.ws.autorizacion.Autorizacion;
/*   9:    */ import ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionComprobantesOffline;
/*  10:    */ import ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionComprobantesOfflineServiceLocator;
/*  11:    */ import ec.gob.sri.comprobantes.ws.autorizacion.RespuestaComprobante;
/*  12:    */ import ec.gob.sri.comprobantes.ws.autorizacion.RespuestaComprobanteAutorizaciones;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.net.URL;
/*  16:    */ import java.text.SimpleDateFormat;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import javax.xml.namespace.QName;
/*  19:    */ import javax.xml.parsers.DocumentBuilder;
/*  20:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  21:    */ import javax.xml.parsers.ParserConfigurationException;
/*  22:    */ import javax.xml.transform.Transformer;
/*  23:    */ import javax.xml.transform.TransformerConfigurationException;
/*  24:    */ import javax.xml.transform.TransformerException;
/*  25:    */ import javax.xml.transform.TransformerFactory;
/*  26:    */ import javax.xml.transform.dom.DOMSource;
/*  27:    */ import javax.xml.transform.stream.StreamResult;
/*  28:    */ import org.w3c.dom.Document;
/*  29:    */ import org.w3c.dom.Element;
/*  30:    */ 
/*  31:    */ public class DocumentoElectronicoAutorizacion
/*  32:    */ {
/*  33:    */   private static AutorizacionComprobantesOfflineServiceLocator servicioAutorizacion;
/*  34: 49 */   private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38: 52 */     ProxyConfig.init();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static RespuestaComprobante autorizarComprobanteIndividual(DocumentoElectronico documento)
/*  42:    */     throws AS2Exception
/*  43:    */   {
/*  44: 57 */     StringBuilder mensajeError = new StringBuilder();
/*  45: 58 */     RespuestaComprobante respuesta = null;
/*  46:    */     try
/*  47:    */     {
/*  48: 62 */       String endPoint = DocumentoElectronico.getEndPoint(documento.getAmbiente(), "AutorizacionComprobantesOffline");
/*  49:    */       AutorizacionComprobantesOffline port;
/*  50: 65 */       for (int i = 0; i < 5; i++)
/*  51:    */       {
/*  52: 66 */         servicioAutorizacion = new AutorizacionComprobantesOfflineServiceLocator(endPoint, new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService"));
/*  53:    */         
/*  54:    */ 
/*  55: 69 */         port = servicioAutorizacion.getAutorizacionComprobantesOfflinePort(new URL(endPoint));
/*  56:    */         
/*  57: 71 */         respuesta = port.autorizacionComprobante(documento.getClaveAcceso());
/*  58: 73 */         if ((respuesta != null) && (respuesta.getAutorizaciones() != null) && (respuesta.getAutorizaciones().getAutorizacion() != null) && 
/*  59: 74 */           (respuesta.getAutorizaciones().getAutorizacion().length > 0)) {
/*  60:    */           break;
/*  61:    */         }
/*  62: 78 */         Thread.currentThread();
/*  63: 79 */         Thread.sleep(300L);
/*  64:    */       }
/*  65: 82 */       if ((respuesta != null) && (respuesta.getAutorizaciones() != null) && (respuesta.getAutorizaciones().getAutorizacion() != null) && 
/*  66: 83 */         (respuesta.getAutorizaciones().getAutorizacion().length > 0))
/*  67:    */       {
/*  68: 84 */         boolean autorizado = false;
/*  69: 85 */         for (Autorizacion autorizacion : respuesta.getAutorizaciones().getAutorizacion()) {
/*  70: 86 */           if (autorizacion.getEstado().equals(EstadoDocumentoElectronico.AUTORIZADO.toString()))
/*  71:    */           {
/*  72: 88 */             documento.setEstado(EstadoDocumentoElectronico.AUTORIZADO);
/*  73: 89 */             generarDocumentoAutorizado(documento, autorizacion);
/*  74: 90 */             autorizado = true;
/*  75: 91 */             break;
/*  76:    */           }
/*  77:    */         }
/*  78: 94 */         if (!autorizado) {
/*  79: 95 */           for (Autorizacion autorizacion : respuesta.getAutorizaciones().getAutorizacion()) {
/*  80: 96 */             if (autorizacion.getEstado().equals("NO AUTORIZADO"))
/*  81:    */             {
/*  82: 97 */               documento.setEstado(EstadoDocumentoElectronico.NO_AUTORIZADO);
/*  83: 98 */               mensajeError.append(DocumentoElectronico.obtieneMensajesAutorizacion(autorizacion));
/*  84: 99 */               ArchivoUtil.stringToFile(autorizacion.getComprobante(), documento.getPathArchivoNoAutorizado());
/*  85:    */             }
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:106 */         mensajeError.append("TRANSMITIDO SIN RESPUESTA|Ha ocurrido un error en el proceso de la Autorización, por lo que se traslado el archivo a la carpeta de: transmitidosSinRespuesta");
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (Exception ex)
/*  95:    */     {
/*  96:109 */       ex.printStackTrace();
/*  97:110 */       mensajeError
/*  98:111 */         .append("ERROR INESPERADO|Ha ocurrido un error en el proceso de la Autorización, por lo que se traslado el archivo a la carpeta de: transmitidosSinRespuesta" + ex
/*  99:112 */         .getMessage());
/* 100:    */     }
/* 101:115 */     if (mensajeError.toString().isEmpty()) {
/* 102:116 */       return respuesta;
/* 103:    */     }
/* 104:118 */     throw new AS2Exception(mensajeError.toString());
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static boolean ingresarComprobanteFacturaE(String claveAcceso, String xml, String emails)
/* 108:    */   {
/* 109:124 */     boolean resultado = false;
/* 110:    */     try
/* 111:    */     {
/* 112:126 */       String endPoint = DocumentoElectronico.getEndPointFacturaE();
/* 113:127 */       ComprobanteServiceLocator servicio = new ComprobanteServiceLocator(endPoint, new QName("http://impl.soap.asinfo.com.ec/", "ComprobanteService"));
/* 114:    */       
/* 115:129 */       ServicioWebComprobante port = servicio.getComprobantePort();
/* 116:130 */       resultado = port.insertarClaveAcceso(claveAcceso, xml, emails);
/* 117:    */     }
/* 118:    */     catch (AS2Exception e1)
/* 119:    */     {
/* 120:133 */       System.out.println("Error tipo: AS2Exception");
/* 121:134 */       resultado = false;
/* 122:    */     }
/* 123:    */     catch (FacturaeException e)
/* 124:    */     {
/* 125:136 */       System.out.println("No se pudo insertar la clave de acceso " + claveAcceso);
/* 126:137 */       System.out.println(e.getCodigoExcepcion() + " - " + e.getMensaje() + " - " + e.getFaultString());
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:139 */       System.out.println("No se pudo insertar la clave de acceso " + claveAcceso);
/* 131:140 */       resultado = false;
/* 132:    */     }
/* 133:142 */     return resultado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void generarDocumentoAutorizado(DocumentoElectronico documento, Autorizacion autorizacion)
/* 137:    */     throws AS2Exception
/* 138:    */   {
/* 139:147 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 140:    */     try
/* 141:    */     {
/* 142:152 */       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 143:153 */       Document doc = docBuilder.newDocument();
/* 144:    */       
/* 145:155 */       Element elementAutorizacion = doc.createElement("autorizacion");
/* 146:156 */       doc.appendChild(elementAutorizacion);
/* 147:    */       
/* 148:158 */       Element elementEstado = doc.createElement("estado");
/* 149:159 */       elementEstado.appendChild(doc.createTextNode(autorizacion.getEstado()));
/* 150:160 */       elementAutorizacion.appendChild(elementEstado);
/* 151:    */       
/* 152:162 */       Element numeroAutorizacion = doc.createElement("numeroAutorizacion");
/* 153:163 */       numeroAutorizacion.appendChild(doc.createTextNode(autorizacion.getNumeroAutorizacion()));
/* 154:164 */       elementAutorizacion.appendChild(numeroAutorizacion);
/* 155:    */       
/* 156:166 */       Element fechaAutorizacion = doc.createElement("fechaAutorizacion");
/* 157:167 */       fechaAutorizacion.appendChild(doc.createTextNode(formatoFecha.format(autorizacion.getFechaAutorizacion().getTime())));
/* 158:168 */       elementAutorizacion.appendChild(fechaAutorizacion);
/* 159:    */       
/* 160:170 */       Element ambiente = doc.createElement("ambiente");
/* 161:171 */       ambiente.appendChild(doc.createTextNode(autorizacion.getAmbiente()));
/* 162:172 */       elementAutorizacion.appendChild(ambiente);
/* 163:    */       
/* 164:174 */       Element comprobante = doc.createElement("comprobante");
/* 165:175 */       comprobante.appendChild(doc.createCDATASection(autorizacion.getComprobante()));
/* 166:176 */       elementAutorizacion.appendChild(comprobante);
/* 167:    */       
/* 168:    */ 
/* 169:179 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 170:180 */       Transformer transformer = transformerFactory.newTransformer();
/* 171:181 */       transformer.setOutputProperty("encoding", "UTF-8");
/* 172:182 */       transformer.setOutputProperty("indent", "yes");
/* 173:183 */       DOMSource domSource = new DOMSource(doc);
/* 174:184 */       StreamResult streamResult = new StreamResult(new File(documento.getPathArchivoAutorizado()));
/* 175:    */       
/* 176:186 */       transformer.transform(domSource, streamResult);
/* 177:    */     }
/* 178:    */     catch (ParserConfigurationException e)
/* 179:    */     {
/* 180:189 */       throw new AS2Exception(e.getMessage());
/* 181:    */     }
/* 182:    */     catch (TransformerConfigurationException e)
/* 183:    */     {
/* 184:191 */       throw new AS2Exception(e.getMessage());
/* 185:    */     }
/* 186:    */     catch (TransformerException e)
/* 187:    */     {
/* 188:193 */       throw new AS2Exception(e.getMessage());
/* 189:    */     }
/* 190:    */     DocumentBuilder docBuilder;
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.DocumentoElectronicoAutorizacion
 * JD-Core Version:    0.7.0.1
 */