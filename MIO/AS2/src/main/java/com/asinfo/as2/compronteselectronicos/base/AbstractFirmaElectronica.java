/*   1:    */ package com.asinfo.as2.compronteselectronicos.base;
/*   2:    */ 
/*   3:    */ import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
/*   4:    */ import es.mityc.firmaJava.libreria.xades.DataToSign;
/*   5:    */ import es.mityc.firmaJava.libreria.xades.FirmaXML;
/*   6:    */ import es.mityc.javasign.pkstore.CertStoreException;
/*   7:    */ import es.mityc.javasign.pkstore.IPKStoreManager;
/*   8:    */ import es.mityc.javasign.pkstore.keystore.KSStore;
/*   9:    */ import java.io.File;
/*  10:    */ import java.io.FileInputStream;
/*  11:    */ import java.io.FileNotFoundException;
/*  12:    */ import java.io.FileOutputStream;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.io.StringWriter;
/*  16:    */ import java.security.KeyStore;
/*  17:    */ import java.security.KeyStoreException;
/*  18:    */ import java.security.NoSuchAlgorithmException;
/*  19:    */ import java.security.PrivateKey;
/*  20:    */ import java.security.Provider;
/*  21:    */ import java.security.cert.CertificateException;
/*  22:    */ import java.security.cert.X509Certificate;
/*  23:    */ import java.util.List;
/*  24:    */ import javax.xml.parsers.DocumentBuilder;
/*  25:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  26:    */ import javax.xml.parsers.ParserConfigurationException;
/*  27:    */ import javax.xml.transform.Transformer;
/*  28:    */ import javax.xml.transform.TransformerException;
/*  29:    */ import javax.xml.transform.TransformerFactory;
/*  30:    */ import javax.xml.transform.dom.DOMSource;
/*  31:    */ import javax.xml.transform.stream.StreamResult;
/*  32:    */ import org.w3c.dom.Document;
/*  33:    */ import org.xml.sax.SAXException;
/*  34:    */ 
/*  35:    */ public abstract class AbstractFirmaElectronica
/*  36:    */ {
/*  37:    */   protected String PKCS12_RESOURCE;
/*  38:    */   protected String PKCS12_PASSWORD;
/*  39:    */   protected String pathArchivoEmitido;
/*  40:    */   protected String pathArchivoFirmado;
/*  41:    */   
/*  42:    */   protected abstract DataToSign createDataToSign();
/*  43:    */   
/*  44:    */   public void execute()
/*  45:    */   {
/*  46: 81 */     IPKStoreManager storeManager = getPKStoreManager();
/*  47: 82 */     if (storeManager == null)
/*  48:    */     {
/*  49: 83 */       System.err.println("El gestor de claves no se ha obtenido correctamente.");
/*  50: 84 */       return;
/*  51:    */     }
/*  52: 89 */     X509Certificate certificate = getFirstCertificate(storeManager);
/*  53: 90 */     if (certificate == null)
/*  54:    */     {
/*  55: 91 */       System.err.println("No existe ningún certificado para firmar.");
/*  56: 92 */       return;
/*  57:    */     }
/*  58:    */     try
/*  59:    */     {
/*  60: 98 */       privateKey = storeManager.getPrivateKey(certificate);
/*  61:    */     }
/*  62:    */     catch (CertStoreException e)
/*  63:    */     {
/*  64:    */       PrivateKey privateKey;
/*  65:100 */       System.err.println("Error al acceder al almacén."); return;
/*  66:    */     }
/*  67:    */     PrivateKey privateKey;
/*  68:105 */     Provider provider = storeManager.getProvider(certificate);
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:110 */     DataToSign dataToSign = createDataToSign();
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:115 */     FirmaXML firma = new FirmaXML();
/*  79:    */     
/*  80:    */ 
/*  81:118 */     Document docSigned = null;
/*  82:    */     try
/*  83:    */     {
/*  84:120 */       Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
/*  85:121 */       docSigned = (Document)res[0];
/*  86:    */     }
/*  87:    */     catch (Exception ex)
/*  88:    */     {
/*  89:123 */       System.err.println("Error realizando la firma");
/*  90:124 */       ex.printStackTrace();
/*  91:125 */       return;
/*  92:    */     }
/*  93:129 */     saveDocumentToFile(docSigned, getPathArchivoFirmado());
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void saveDocumentToFile(Document document, String pathfile)
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:144 */       FileOutputStream fos = new FileOutputStream(pathfile);
/* 101:145 */       UtilidadTratarNodo.saveDocumentToOutputStream(document, fos, true);
/* 102:    */     }
/* 103:    */     catch (FileNotFoundException e)
/* 104:    */     {
/* 105:147 */       System.err.println("Error al salvar el documento");
/* 106:148 */       e.printStackTrace();
/* 107:149 */       System.exit(-1);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void saveDocumentToFileUnsafeMode(Document document, String pathfile)
/* 112:    */   {
/* 113:166 */     TransformerFactory tfactory = TransformerFactory.newInstance();
/* 114:    */     try
/* 115:    */     {
/* 116:169 */       Transformer serializer = tfactory.newTransformer();
/* 117:    */       
/* 118:171 */       serializer.transform(new DOMSource(document), new StreamResult(new File(pathfile)));
/* 119:    */     }
/* 120:    */     catch (TransformerException e)
/* 121:    */     {
/* 122:173 */       System.err.println("Error al salvar el documento");
/* 123:174 */       e.printStackTrace();
/* 124:175 */       System.exit(-1);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected Document getDocument(String resource)
/* 129:    */   {
/* 130:189 */     Document doc = null;
/* 131:190 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 132:191 */     dbf.setNamespaceAware(true);
/* 133:    */     try
/* 134:    */     {
/* 135:193 */       doc = dbf.newDocumentBuilder().parse(new FileInputStream(resource));
/* 136:    */     }
/* 137:    */     catch (ParserConfigurationException ex)
/* 138:    */     {
/* 139:195 */       System.err.println("Error al parsear el documento");
/* 140:196 */       ex.printStackTrace();
/* 141:197 */       System.exit(-1);
/* 142:    */     }
/* 143:    */     catch (SAXException ex)
/* 144:    */     {
/* 145:199 */       System.err.println("Error al parsear el documento");
/* 146:200 */       ex.printStackTrace();
/* 147:201 */       System.exit(-1);
/* 148:    */     }
/* 149:    */     catch (IOException ex)
/* 150:    */     {
/* 151:203 */       System.err.println("Error al parsear el documento");
/* 152:204 */       ex.printStackTrace();
/* 153:205 */       System.exit(-1);
/* 154:    */     }
/* 155:    */     catch (IllegalArgumentException ex)
/* 156:    */     {
/* 157:207 */       System.err.println("Error al parsear el documento");
/* 158:208 */       ex.printStackTrace();
/* 159:209 */       System.exit(-1);
/* 160:    */     }
/* 161:211 */     return doc;
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected String getDocumentAsString(String resource)
/* 165:    */   {
/* 166:225 */     Document doc = getDocument(resource);
/* 167:226 */     TransformerFactory tfactory = TransformerFactory.newInstance();
/* 168:    */     
/* 169:228 */     StringWriter stringWriter = new StringWriter();
/* 170:    */     try
/* 171:    */     {
/* 172:230 */       Transformer serializer = tfactory.newTransformer();
/* 173:231 */       serializer.transform(new DOMSource(doc), new StreamResult(stringWriter));
/* 174:    */     }
/* 175:    */     catch (TransformerException e)
/* 176:    */     {
/* 177:233 */       System.err.println("Error al imprimir el documento");
/* 178:234 */       e.printStackTrace();
/* 179:235 */       System.exit(-1);
/* 180:    */     }
/* 181:238 */     return stringWriter.toString();
/* 182:    */   }
/* 183:    */   
/* 184:    */   private IPKStoreManager getPKStoreManager()
/* 185:    */   {
/* 186:249 */     IPKStoreManager storeManager = null;
/* 187:    */     try
/* 188:    */     {
/* 189:251 */       KeyStore ks = KeyStore.getInstance("PKCS12");
/* 190:252 */       ks.load(new FileInputStream(this.PKCS12_RESOURCE), this.PKCS12_PASSWORD.toCharArray());
/* 191:253 */       storeManager = new KSStore(ks, new PassStoreKS(this.PKCS12_PASSWORD));
/* 192:    */     }
/* 193:    */     catch (KeyStoreException ex)
/* 194:    */     {
/* 195:255 */       System.err.println("No se puede generar KeyStore PKCS12");
/* 196:256 */       ex.printStackTrace();
/* 197:    */     }
/* 198:    */     catch (NoSuchAlgorithmException ex)
/* 199:    */     {
/* 200:259 */       System.err.println("No se puede generar KeyStore PKCS12");
/* 201:260 */       ex.printStackTrace();
/* 202:    */     }
/* 203:    */     catch (CertificateException ex)
/* 204:    */     {
/* 205:263 */       System.err.println("No se puede generar KeyStore PKCS12");
/* 206:264 */       ex.printStackTrace();
/* 207:    */     }
/* 208:    */     catch (IOException ex)
/* 209:    */     {
/* 210:267 */       System.err.println("No se puede generar KeyStore PKCS12");
/* 211:268 */       ex.printStackTrace();
/* 212:    */     }
/* 213:271 */     return storeManager;
/* 214:    */   }
/* 215:    */   
/* 216:    */   private X509Certificate getFirstCertificate(IPKStoreManager storeManager)
/* 217:    */   {
/* 218:284 */     List<X509Certificate> certs = null;
/* 219:    */     try
/* 220:    */     {
/* 221:286 */       certs = storeManager.getSignCertificates();
/* 222:    */     }
/* 223:    */     catch (CertStoreException ex)
/* 224:    */     {
/* 225:288 */       System.err.println("Fallo obteniendo listado de certificados");
/* 226:289 */       System.exit(-1);
/* 227:    */     }
/* 228:291 */     if ((certs == null) || (certs.size() == 0))
/* 229:    */     {
/* 230:292 */       System.err.println("Lista de certificados vacía");
/* 231:293 */       System.exit(-1);
/* 232:    */     }
/* 233:296 */     X509Certificate certificate = (X509Certificate)certs.get(0);
/* 234:297 */     return certificate;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getPathArchivoEmitido()
/* 238:    */   {
/* 239:301 */     return this.pathArchivoEmitido;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setPathArchivoEmitido(String pathArchivoEmitido)
/* 243:    */   {
/* 244:305 */     this.pathArchivoEmitido = pathArchivoEmitido;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getPathArchivoFirmado()
/* 248:    */   {
/* 249:309 */     return this.pathArchivoFirmado;
/* 250:    */   }
/* 251:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.AbstractFirmaElectronica
 * JD-Core Version:    0.7.0.1
 */