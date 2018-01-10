/*  1:   */ package com.asinfo.as2.utils.encriptacion;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.File;
/*  5:   */ import java.io.FileNotFoundException;
/*  6:   */ import java.io.FileReader;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.math.BigInteger;
/*  9:   */ 
/* 10:   */ public class AESKeyBancoInternacional
/* 11:   */ {
/* 12:12 */   private String strKa = null;
/* 13:13 */   private static final String as2Home = System.getenv("AS2_HOME");
/* 14:   */   
/* 15:   */   public void init()
/* 16:   */     throws ExcepcionAS2Encriptacion
/* 17:   */   {
/* 18:16 */     String strArchivoKey = as2Home + File.separator + "baninter.key";
/* 19:17 */     leeClaves(strArchivoKey);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void leeClaves(String strArchivo)
/* 23:   */     throws ExcepcionAS2Encriptacion
/* 24:   */   {
/* 25:21 */     FileReader fr = null;
/* 26:   */     try
/* 27:   */     {
/* 28:23 */       File archivo = new File(strArchivo);
/* 29:24 */       fr = new FileReader(archivo);
/* 30:25 */       BufferedReader br = new BufferedReader(fr);
/* 31:   */       
/* 32:27 */       String strXb = br.readLine();
/* 33:28 */       String q = br.readLine();
/* 34:29 */       String strYa = br.readLine();
/* 35:30 */       String strYb = br.readLine();
/* 36:31 */       String strXa = br.readLine();
/* 37:   */       
/* 38:33 */       BigInteger xa = new BigInteger(strXa, 16);
/* 39:34 */       BigInteger yb = new BigInteger(strYb, 16);
/* 40:35 */       BigInteger big = new BigInteger(q, 16);
/* 41:   */       
/* 42:37 */       BigInteger ka = yb.modPow(xa, big);
/* 43:38 */       setStrKa(toHexString(ka.toByteArray())); return;
/* 44:   */     }
/* 45:   */     catch (FileNotFoundException e)
/* 46:   */     {
/* 47:41 */       new ExcepcionAS2Encriptacion("No existe el archivo baninter.key de " + strArchivo);
/* 48:   */     }
/* 49:   */     catch (IOException e)
/* 50:   */     {
/* 51:44 */       new ExcepcionAS2Encriptacion("No puede leer el archivo baninter.key de " + strArchivo);
/* 52:   */     }
/* 53:   */     finally
/* 54:   */     {
/* 55:   */       try
/* 56:   */       {
/* 57:48 */         if (fr != null) {
/* 58:49 */           fr.close();
/* 59:   */         }
/* 60:   */       }
/* 61:   */       catch (Exception e2)
/* 62:   */       {
/* 63:51 */         e2.printStackTrace();
/* 64:   */       }
/* 65:   */     }
/* 66:   */   }
/* 67:   */   
/* 68:   */   private static String toHexString(byte[] block)
/* 69:   */   {
/* 70:57 */     StringBuffer buf = new StringBuffer();
/* 71:   */     
/* 72:59 */     int len = block.length;
/* 73:61 */     for (int i = 0; i < len; i++) {
/* 74:62 */       byte2hex(block[i], buf);
/* 75:   */     }
/* 76:64 */     return buf.toString();
/* 77:   */   }
/* 78:   */   
/* 79:   */   private static void byte2hex(byte b, StringBuffer buf)
/* 80:   */   {
/* 81:68 */     char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/* 82:69 */     int high = (b & 0xF0) >> 4;
/* 83:70 */     int low = b & 0xF;
/* 84:71 */     buf.append(hexChars[high]);
/* 85:72 */     buf.append(hexChars[low]);
/* 86:   */   }
/* 87:   */   
/* 88:   */   public String getStrKa()
/* 89:   */   {
/* 90:79 */     return this.strKa;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public void setStrKa(String strKa)
/* 94:   */   {
/* 95:87 */     this.strKa = strKa;
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.encriptacion.AESKeyBancoInternacional
 * JD-Core Version:    0.7.0.1
 */