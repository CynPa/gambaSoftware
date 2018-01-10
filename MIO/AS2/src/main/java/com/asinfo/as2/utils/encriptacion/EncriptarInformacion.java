/*  1:   */ package com.asinfo.as2.utils.encriptacion;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*  5:   */ 
/*  6:   */ public class EncriptarInformacion
/*  7:   */ {
/*  8:   */   public static String encrypt(String texto)
/*  9:   */   {
/* 10:24 */     StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
/* 11:   */     
/* 12:26 */     encryptor.setPassword("uniquekey");
/* 13:27 */     return encryptor.encrypt(texto);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static String decrypt(String cadena)
/* 17:   */   {
/* 18:31 */     StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
/* 19:   */     
/* 20:33 */     s.setPassword("uniquekey");
/* 21:34 */     String devuelve = "";
/* 22:   */     try
/* 23:   */     {
/* 24:36 */       devuelve = s.decrypt(cadena);
/* 25:   */     }
/* 26:   */     catch (Exception e)
/* 27:   */     {
/* 28:38 */       e.printStackTrace();
/* 29:39 */       System.out.println("Se ha producido un error al desencriptar el dato");
/* 30:   */     }
/* 31:41 */     return devuelve;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void main(String[] args)
/* 35:   */   {
/* 36:45 */     String mensaje = "1";
/* 37:46 */     String mensaje_encriptado = encrypt(mensaje);
/* 38:47 */     System.out.println("Mensaje encriptado es >>>" + mensaje_encriptado);
/* 39:48 */     String dato = "xQrDH0H8QItCfTF92cBycg==";
/* 40:49 */     System.out.println("Mensaje desencriptado >>>" + decrypt(dato));
/* 41:   */     
/* 42:51 */     String dado_21 = "rwy3fO3LJLGpNf6CJtja/w==";
/* 43:52 */     System.out.println("Mensaje desencriptado21>>>" + decrypt(dado_21));
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.encriptacion.EncriptarInformacion
 * JD-Core Version:    0.7.0.1
 */