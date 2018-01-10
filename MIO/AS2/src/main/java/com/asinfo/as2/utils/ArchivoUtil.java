/*   1:    */ package com.asinfo.as2.utils;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   4:    */ import java.io.BufferedReader;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.FileReader;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.io.OutputStream;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.nio.file.CopyOption;
/*  14:    */ import java.nio.file.Files;
/*  15:    */ import java.nio.file.Path;
/*  16:    */ import java.nio.file.Paths;
/*  17:    */ import java.nio.file.StandardCopyOption;
/*  18:    */ import java.util.List;
/*  19:    */ import javax.xml.parsers.DocumentBuilder;
/*  20:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  21:    */ import javax.xml.parsers.ParserConfigurationException;
/*  22:    */ import org.jdom2.Document;
/*  23:    */ import org.jdom2.Element;
/*  24:    */ import org.jdom2.JDOMException;
/*  25:    */ import org.jdom2.input.SAXBuilder;
/*  26:    */ 
/*  27:    */ public class ArchivoUtil
/*  28:    */ {
/*  29:    */   public static byte[] archivoToByte(File file)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 48 */     buffer = new byte[(int)file.length()];
/*  33: 49 */     InputStream ios = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 51 */       ios = new FileInputStream(file);
/*  37: 52 */       if (ios.read(buffer) == -1) {
/*  38: 53 */         throw new IOException("EOF reached while trying to read the whole file");
/*  39:    */       }
/*  40: 64 */       return buffer;
/*  41:    */     }
/*  42:    */     finally
/*  43:    */     {
/*  44:    */       try
/*  45:    */       {
/*  46: 56 */         if (ios != null) {
/*  47: 57 */           ios.close();
/*  48:    */         }
/*  49:    */       }
/*  50:    */       catch (IOException e)
/*  51:    */       {
/*  52: 59 */         throw e;
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static boolean byteToFile(byte[] arrayBytes, String rutaArchivo)
/*  58:    */   {
/*  59: 68 */     boolean correcto = false;
/*  60:    */     try
/*  61:    */     {
/*  62: 70 */       OutputStream out = new FileOutputStream(rutaArchivo);
/*  63: 71 */       out.write(arrayBytes);
/*  64: 72 */       out.flush();
/*  65: 73 */       out.close();
/*  66: 74 */       correcto = true;
/*  67:    */     }
/*  68:    */     catch (Exception e)
/*  69:    */     {
/*  70: 76 */       e.printStackTrace();
/*  71:    */     }
/*  72: 78 */     return correcto;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Document obtenerDocumentoXML(String rutaArchivo)
/*  76:    */     throws ExcepcionAS2
/*  77:    */   {
/*  78: 83 */     SAXBuilder builder = new SAXBuilder();
/*  79: 84 */     File archivo = new File(rutaArchivo);
/*  80: 86 */     if (!archivo.exists())
/*  81:    */     {
/*  82: 87 */       System.out.println("Archivo no encontrado >>> " + archivo.getAbsolutePath());
/*  83: 88 */       throw new ExcepcionAS2("Archivo no encontrado");
/*  84:    */     }
/*  85: 90 */     Document document = null;
/*  86:    */     try
/*  87:    */     {
/*  88: 93 */       return builder.build(archivo);
/*  89:    */     }
/*  90:    */     catch (IOException io)
/*  91:    */     {
/*  92: 97 */       System.out.println("Error al leer el documento >>>" + io);
/*  93: 98 */       io.printStackTrace();
/*  94: 99 */       throw new ExcepcionAS2(io);
/*  95:    */     }
/*  96:    */     catch (JDOMException jdomex)
/*  97:    */     {
/*  98:101 */       System.out.println("Error al leer el documento >>>" + jdomex);
/*  99:102 */       jdomex.printStackTrace();
/* 100:103 */       throw new ExcepcionAS2(jdomex);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void stringToFile2(String contenido, String archivo)
/* 105:    */     throws ExcepcionAS2, ParserConfigurationException
/* 106:    */   {
/* 107:111 */     Document doc = obtenerDocumentoXML(archivo);
/* 108:112 */     Element nodoPrincipal = doc.getRootElement();
/* 109:113 */     List<Element> listaAutorizacionXML = nodoPrincipal.getChildren("autorizacion");
/* 110:    */     
/* 111:115 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 112:116 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 113:    */     
/* 114:118 */     Document docNew = new Document();
/* 115:120 */     for (Element autorizacion : listaAutorizacionXML) {
/* 116:121 */       docNew.addContent(autorizacion);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void stringToFile(String contenido, String archivo)
/* 121:    */   {
/* 122:137 */     File file = new File(archivo);
/* 123:138 */     FileOutputStream fop = null;
/* 124:    */     try
/* 125:    */     {
/* 126:141 */       fop = new FileOutputStream(file);
/* 127:143 */       if (!file.exists()) {
/* 128:144 */         file.createNewFile();
/* 129:    */       }
/* 130:148 */       byte[] contentInBytes = contenido.getBytes();
/* 131:    */       
/* 132:150 */       fop.write(contentInBytes);
/* 133:151 */       fop.flush(); return;
/* 134:    */     }
/* 135:    */     catch (IOException e)
/* 136:    */     {
/* 137:154 */       e.printStackTrace();
/* 138:    */     }
/* 139:    */     finally
/* 140:    */     {
/* 141:    */       try
/* 142:    */       {
/* 143:157 */         if (fop != null) {
/* 144:158 */           fop.close();
/* 145:    */         }
/* 146:    */       }
/* 147:    */       catch (IOException e)
/* 148:    */       {
/* 149:161 */         e.printStackTrace();
/* 150:    */       }
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static String leerArchivoSQL(String aSQLScriptFilePath)
/* 155:    */   {
/* 156:167 */     File archivo = null;
/* 157:168 */     FileReader fr = null;
/* 158:169 */     BufferedReader br = null;
/* 159:170 */     resultado = "";
/* 160:    */     try
/* 161:    */     {
/* 162:173 */       archivo = new File(aSQLScriptFilePath);
/* 163:174 */       fr = new FileReader(archivo);
/* 164:175 */       br = new BufferedReader(fr);
/* 165:    */       String linea;
/* 166:177 */       while ((linea = br.readLine()) != null) {
/* 167:178 */         resultado = resultado + linea;
/* 168:    */       }
/* 169:191 */       return resultado;
/* 170:    */     }
/* 171:    */     catch (Exception e)
/* 172:    */     {
/* 173:181 */       e.printStackTrace();
/* 174:    */     }
/* 175:    */     finally
/* 176:    */     {
/* 177:    */       try
/* 178:    */       {
/* 179:184 */         if (null != fr) {
/* 180:185 */           fr.close();
/* 181:    */         }
/* 182:    */       }
/* 183:    */       catch (Exception e2)
/* 184:    */       {
/* 185:188 */         e2.printStackTrace();
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static void crearDirectorio(File directorio)
/* 191:    */   {
/* 192:201 */     if (!directorio.isDirectory()) {
/* 193:202 */       directorio.mkdir();
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static void copiarDirectorio(File pathOrigen, File pathDestino)
/* 198:    */     throws IOException
/* 199:    */   {
/* 200:218 */     if (pathOrigen.isDirectory())
/* 201:    */     {
/* 202:221 */       if (!pathDestino.exists())
/* 203:    */       {
/* 204:222 */         pathDestino.mkdir();
/* 205:223 */         System.out.println("Directorio copiado desde " + pathOrigen + "  a " + pathDestino);
/* 206:    */       }
/* 207:228 */       String[] archivos = pathOrigen.list();
/* 208:230 */       for (String archivo : archivos)
/* 209:    */       {
/* 210:232 */         File archivoOrigen = new File(pathOrigen, archivo);
/* 211:233 */         File archivoDestino = new File(pathDestino, archivo);
/* 212:    */         
/* 213:235 */         copiarDirectorio(archivoOrigen, archivoDestino);
/* 214:    */       }
/* 215:    */     }
/* 216:    */     else
/* 217:    */     {
/* 218:241 */       InputStream in = new FileInputStream(pathOrigen);??? = null;
/* 219:    */       try
/* 220:    */       {
/* 221:241 */         Object out = new FileOutputStream(pathDestino);Throwable localThrowable8 = null;
/* 222:    */         try
/* 223:    */         {
/* 224:243 */           byte[] buffer = new byte[1024];
/* 225:    */           int logitud;
/* 226:247 */           while ((logitud = in.read(buffer)) > 0) {
/* 227:248 */             ((OutputStream)out).write(buffer, 0, logitud);
/* 228:    */           }
/* 229:    */         }
/* 230:    */         catch (Throwable localThrowable1)
/* 231:    */         {
/* 232:241 */           localThrowable8 = localThrowable1;throw localThrowable1;
/* 233:    */         }
/* 234:    */         finally
/* 235:    */         {
/* 236:251 */           if (out != null) {
/* 237:251 */             if (localThrowable8 != null) {
/* 238:    */               try
/* 239:    */               {
/* 240:251 */                 ((OutputStream)out).close();
/* 241:    */               }
/* 242:    */               catch (Throwable localThrowable2)
/* 243:    */               {
/* 244:251 */                 localThrowable8.addSuppressed(localThrowable2);
/* 245:    */               }
/* 246:    */             } else {
/* 247:251 */               ((OutputStream)out).close();
/* 248:    */             }
/* 249:    */           }
/* 250:    */         }
/* 251:    */       }
/* 252:    */       catch (Throwable localThrowable7)
/* 253:    */       {
/* 254:241 */         ??? = localThrowable7;throw localThrowable7;
/* 255:    */       }
/* 256:    */       finally
/* 257:    */       {
/* 258:251 */         if (in != null) {
/* 259:251 */           if (??? != null) {
/* 260:    */             try
/* 261:    */             {
/* 262:251 */               in.close();
/* 263:    */             }
/* 264:    */             catch (Throwable localThrowable5)
/* 265:    */             {
/* 266:251 */               ((Throwable)???).addSuppressed(localThrowable5);
/* 267:    */             }
/* 268:    */           } else {
/* 269:251 */             in.close();
/* 270:    */           }
/* 271:    */         }
/* 272:    */       }
/* 273:253 */       System.out.println("Archivo copiado desde " + pathOrigen + " a " + pathDestino);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public static boolean esArchivoNuevo(String archivoNuevo, String archivoAntiguo)
/* 278:    */   {
/* 279:266 */     long d1 = new File(archivoNuevo).lastModified();
/* 280:267 */     long d2 = new File(archivoAntiguo).lastModified();
/* 281:    */     
/* 282:269 */     return d1 > d2;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public static void sobreescribirArchivo(String archivoOrigen, String archivoDestino)
/* 286:    */     throws IOException
/* 287:    */   {
/* 288:281 */     Path origen = Paths.get(archivoOrigen, new String[0]);
/* 289:282 */     Path destino = Paths.get(archivoDestino, new String[0]);
/* 290:    */     
/* 291:284 */     Files.copy(origen, destino, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.ArchivoUtil
 * JD-Core Version:    0.7.0.1
 */