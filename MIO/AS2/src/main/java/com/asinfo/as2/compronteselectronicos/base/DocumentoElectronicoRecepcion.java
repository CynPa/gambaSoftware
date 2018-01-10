/*  1:   */ package com.asinfo.as2.compronteselectronicos.base;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  4:   */ import com.asinfo.as2.utils.ArchivoUtil;
/*  5:   */ import ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOffline;
/*  6:   */ import ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOfflineServiceLocator;
/*  7:   */ import ec.gob.sri.comprobantes.ws.recepcion.RespuestaSolicitud;
/*  8:   */ import java.io.File;
/*  9:   */ import java.net.URL;
/* 10:   */ import javax.xml.namespace.QName;
/* 11:   */ import javax.xml.rpc.ServiceException;
/* 12:   */ import org.apache.log4j.Logger;
/* 13:   */ 
/* 14:   */ public class DocumentoElectronicoRecepcion
/* 15:   */ {
/* 16:   */   private static RecepcionComprobantesOfflineServiceLocator servicioRecepcion;
/* 17:37 */   protected static final Logger LOG = Logger.getLogger("com.asinfo.as2");
/* 18:   */   
/* 19:   */   static
/* 20:   */   {
/* 21:40 */     ProxyConfig.init();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static RespuestaSolicitud enviarComprobante(int ambiente, File xmlFile)
/* 25:   */     throws AS2Exception
/* 26:   */   {
/* 27:44 */     RespuestaSolicitud response = null;
/* 28:   */     try
/* 29:   */     {
/* 30:46 */       String endPoint = DocumentoElectronico.getEndPoint(ambiente, "RecepcionComprobantesOffline");
/* 31:47 */       servicioRecepcion = new RecepcionComprobantesOfflineServiceLocator(endPoint, new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService"));
/* 32:   */       
/* 33:49 */       RecepcionComprobantesOffline port = servicioRecepcion.getRecepcionComprobantesOfflinePort(new URL(endPoint));
/* 34:50 */       response = port.validarComprobante(ArchivoUtil.archivoToByte(xmlFile));
/* 35:   */     }
/* 36:   */     catch (Exception e)
/* 37:   */     {
/* 38:52 */       if ((e instanceof ServiceException)) {
/* 39:53 */         throw new AS2Exception("msg_error_comunicacion_ws_sri | " + e.getMessage());
/* 40:   */       }
/* 41:55 */       throw new AS2Exception(e.getMessage());
/* 42:   */     }
/* 43:59 */     return response;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.DocumentoElectronicoRecepcion
 * JD-Core Version:    0.7.0.1
 */