/*  1:   */ package com.asinfo.as2.utils.encriptacion;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.security.MessageDigest;
/*  5:   */ import java.security.NoSuchAlgorithmException;
/*  6:   */ 
/*  7:   */ public class EncriptarTexto
/*  8:   */ {
/*  9:25 */   public static String MD2 = "MD2";
/* 10:26 */   public static String MD5 = "MD5";
/* 11:27 */   public static String SHA1 = "SHA-1";
/* 12:28 */   public static String SHA256 = "SHA-256";
/* 13:29 */   public static String SHA384 = "SHA-384";
/* 14:30 */   public static String SHA512 = "SHA-512";
/* 15:   */   
/* 16:   */   private static String toHexadecimal(byte[] digest)
/* 17:   */   {
/* 18:40 */     String hash = "";
/* 19:41 */     for (byte aux : digest)
/* 20:   */     {
/* 21:42 */       int b = aux & 0xFF;
/* 22:43 */       if (Integer.toHexString(b).length() == 1) {
/* 23:44 */         hash = hash + "0";
/* 24:   */       }
/* 25:45 */       hash = hash + Integer.toHexString(b);
/* 26:   */     }
/* 27:47 */     return hash;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static String getStringMessageDigest(String message, String algorithm)
/* 31:   */   {
/* 32:60 */     byte[] digest = null;
/* 33:61 */     byte[] buffer = message.getBytes();
/* 34:   */     try
/* 35:   */     {
/* 36:63 */       MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
/* 37:64 */       messageDigest.reset();
/* 38:65 */       messageDigest.update(buffer);
/* 39:66 */       digest = messageDigest.digest();
/* 40:   */     }
/* 41:   */     catch (NoSuchAlgorithmException ex)
/* 42:   */     {
/* 43:68 */       System.out.println("Error creando Digest");
/* 44:   */     }
/* 45:70 */     return toHexadecimal(digest);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static void main(String[] args)
/* 49:   */   {
/* 50:75 */     String mensaje = "2";
/* 51:   */     
/* 52:77 */     System.out.println("Mensaje = " + mensaje);
/* 53:78 */     System.out.println("MD2 = " + getStringMessageDigest(mensaje, MD2));
/* 54:79 */     System.out.println("MD5 = " + getStringMessageDigest(mensaje, MD5));
/* 55:80 */     System.out.println("SHA-1 = " + getStringMessageDigest(mensaje, SHA1));
/* 56:81 */     System.out.println("SHA-256 = " + getStringMessageDigest(mensaje, SHA256));
/* 57:82 */     System.out.println("SHA-384 = " + getStringMessageDigest(mensaje, SHA384));
/* 58:83 */     System.out.println("SHA-512 = " + getStringMessageDigest(mensaje, SHA512));
/* 59:   */     
/* 60:85 */     String mensaje_encriptado_md5 = "baac73e0d9e99bad6ea493b865aa42c3";
/* 61:86 */     if (mensaje_encriptado_md5.equals(getStringMessageDigest(mensaje, MD5))) {
/* 62:87 */       System.out.println("El dato es correcto");
/* 63:   */     } else {
/* 64:89 */       System.out.println("El dato es erroneo");
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.encriptacion.EncriptarTexto
 * JD-Core Version:    0.7.0.1
 */