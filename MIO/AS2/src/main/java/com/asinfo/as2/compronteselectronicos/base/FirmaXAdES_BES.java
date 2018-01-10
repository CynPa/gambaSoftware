/*  1:   */ package com.asinfo.as2.compronteselectronicos.base;
/*  2:   */ 
/*  3:   */ import es.mityc.firmaJava.libreria.xades.DataToSign;
/*  4:   */ import es.mityc.firmaJava.libreria.xades.EnumFormatoFirma;
/*  5:   */ import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
/*  6:   */ import es.mityc.javasign.xml.refs.InternObjectToSign;
/*  7:   */ import es.mityc.javasign.xml.refs.ObjectToSign;
/*  8:   */ import org.w3c.dom.Document;
/*  9:   */ 
/* 10:   */ public class FirmaXAdES_BES
/* 11:   */   extends AbstractFirmaElectronica
/* 12:   */ {
/* 13:   */   public FirmaXAdES_BES(DocumentoElectronico documento)
/* 14:   */   {
/* 15:30 */     this.PKCS12_RESOURCE = documento.getPkcs12_resource();
/* 16:31 */     this.PKCS12_PASSWORD = documento.getPkcs12_password();
/* 17:32 */     if (documento.getTipoEmision() == 1) {
/* 18:33 */       this.pathArchivoEmitido = documento.getPathArchivoEmitido();
/* 19:   */     } else {
/* 20:35 */       this.pathArchivoEmitido = documento.getPathArchivoPendienteContingencia();
/* 21:   */     }
/* 22:37 */     this.pathArchivoFirmado = documento.getPathArchivoFirmado();
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected DataToSign createDataToSign()
/* 26:   */   {
/* 27:43 */     DataToSign dataToSign = new DataToSign();
/* 28:44 */     dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
/* 29:45 */     dataToSign.setEsquema(XAdESSchemas.XAdES_132);
/* 30:46 */     dataToSign.setXMLEncoding("UTF-8");
/* 31:47 */     dataToSign.setEnveloped(true);
/* 32:   */     
/* 33:49 */     dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
/* 34:   */     
/* 35:51 */     Document docToSign = getDocument(getPathArchivoEmitido());
/* 36:52 */     dataToSign.setDocument(docToSign);
/* 37:   */     
/* 38:54 */     return dataToSign;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.FirmaXAdES_BES
 * JD-Core Version:    0.7.0.1
 */