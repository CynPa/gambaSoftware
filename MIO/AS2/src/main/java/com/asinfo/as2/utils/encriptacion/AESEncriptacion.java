/*   1:    */ package com.asinfo.as2.utils.encriptacion;
/*   2:    */ 
/*   3:    */ import fin.internacional.util.AES;
/*   4:    */ import java.io.BufferedReader;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileNotFoundException;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.FileReader;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.io.UnsupportedEncodingException;
/*  13:    */ import java.security.InvalidKeyException;
/*  14:    */ import java.security.NoSuchAlgorithmException;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.crypto.BadPaddingException;
/*  18:    */ import javax.crypto.Cipher;
/*  19:    */ import javax.crypto.IllegalBlockSizeException;
/*  20:    */ import javax.crypto.KeyGenerator;
/*  21:    */ import javax.crypto.NoSuchPaddingException;
/*  22:    */ import javax.crypto.SecretKey;
/*  23:    */ import javax.crypto.spec.SecretKeySpec;
/*  24:    */ 
/*  25:    */ public class AESEncriptacion
/*  26:    */ {
/*  27: 44 */   private final String ALGORITMO = "AES";
/*  28: 45 */   private final int LONGITUD = 128;
/*  29: 46 */   private final String CODIFICACION = "UTF-8";
/*  30:    */   private AESKey aesKey;
/*  31:    */   
/*  32:    */   public AESEncriptacion(AESKey aesKey)
/*  33:    */   {
/*  34: 50 */     this.aesKey = aesKey;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AESKey generaKey()
/*  38:    */   {
/*  39: 54 */     KeyGenerator kgen = null;
/*  40:    */     try
/*  41:    */     {
/*  42: 56 */       kgen = KeyGenerator.getInstance("AES");
/*  43:    */     }
/*  44:    */     catch (NoSuchAlgorithmException ex)
/*  45:    */     {
/*  46: 58 */       error(ex);
/*  47:    */     }
/*  48: 60 */     kgen.init(128);
/*  49: 61 */     SecretKey skey = kgen.generateKey();
/*  50: 62 */     this.aesKey = new AESKey();
/*  51: 63 */     this.aesKey.setEncoded(HexToString(skey.getEncoded()));
/*  52: 64 */     return this.aesKey;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String encripta(String cadena)
/*  56:    */   {
/*  57: 68 */     String encriptado = null;
/*  58:    */     try
/*  59:    */     {
/*  60: 70 */       byte[] raw = this.aesKey.getEncoded().getBytes();
/*  61: 71 */       SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
/*  62: 72 */       Cipher cipher = Cipher.getInstance("AES");
/*  63: 73 */       cipher.init(1, skeySpec);
/*  64: 74 */       byte[] encrypted = cipher.doFinal(cadena.getBytes("UTF-8"));
/*  65: 75 */       encriptado = HexToString(encrypted);
/*  66:    */     }
/*  67:    */     catch (IllegalBlockSizeException ex)
/*  68:    */     {
/*  69: 78 */       error(ex);
/*  70:    */     }
/*  71:    */     catch (BadPaddingException ex)
/*  72:    */     {
/*  73: 80 */       error(ex);
/*  74:    */     }
/*  75:    */     catch (UnsupportedEncodingException ex)
/*  76:    */     {
/*  77: 82 */       error(ex);
/*  78:    */     }
/*  79:    */     catch (InvalidKeyException ex)
/*  80:    */     {
/*  81: 84 */       error(ex);
/*  82:    */     }
/*  83:    */     catch (NoSuchAlgorithmException ex)
/*  84:    */     {
/*  85: 86 */       error(ex);
/*  86:    */     }
/*  87:    */     catch (NoSuchPaddingException ex)
/*  88:    */     {
/*  89: 88 */       error(ex);
/*  90:    */     }
/*  91: 90 */     return encriptado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private String HexToString(byte[] arregloEncriptado)
/*  95:    */   {
/*  96: 94 */     String textoEncriptado = "";
/*  97: 95 */     for (int i = 0; i < arregloEncriptado.length; i++)
/*  98:    */     {
/*  99: 96 */       int aux = arregloEncriptado[i] & 0xFF;
/* 100: 97 */       if (aux < 16) {
/* 101: 98 */         textoEncriptado = textoEncriptado.concat("0");
/* 102:    */       }
/* 103:100 */       textoEncriptado = textoEncriptado.concat(Integer.toHexString(aux));
/* 104:    */     }
/* 105:102 */     return textoEncriptado;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private byte[] StringToHex(String encriptado)
/* 109:    */   {
/* 110:106 */     byte[] enBytes = new byte[encriptado.length() / 2];
/* 111:107 */     for (int i = 0; i < enBytes.length; i++)
/* 112:    */     {
/* 113:108 */       int index = i * 2;
/* 114:109 */       String aux = encriptado.substring(index, index + 2);
/* 115:110 */       int v = Integer.parseInt(aux, 16);
/* 116:111 */       enBytes[i] = ((byte)v);
/* 117:    */     }
/* 118:113 */     return enBytes;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String desencriptar(String encriptado)
/* 122:    */   {
/* 123:117 */     String originalString = null;
/* 124:    */     try
/* 125:    */     {
/* 126:119 */       byte[] raw = this.aesKey.getEncoded().getBytes();
/* 127:120 */       SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
/* 128:121 */       Cipher cipher = Cipher.getInstance("AES");
/* 129:122 */       cipher.init(2, skeySpec);
/* 130:123 */       byte[] original = cipher.doFinal(StringToHex(encriptado));
/* 131:124 */       originalString = new String(original);
/* 132:    */     }
/* 133:    */     catch (IllegalBlockSizeException ex)
/* 134:    */     {
/* 135:127 */       error(ex);
/* 136:    */     }
/* 137:    */     catch (BadPaddingException ex)
/* 138:    */     {
/* 139:129 */       error(ex);
/* 140:    */     }
/* 141:    */     catch (InvalidKeyException ex)
/* 142:    */     {
/* 143:131 */       error(ex);
/* 144:    */     }
/* 145:    */     catch (NoSuchAlgorithmException ex)
/* 146:    */     {
/* 147:133 */       error(ex);
/* 148:    */     }
/* 149:    */     catch (NoSuchPaddingException ex)
/* 150:    */     {
/* 151:135 */       error(ex);
/* 152:    */     }
/* 153:137 */     return originalString;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void error(Exception ex)
/* 157:    */   {
/* 158:141 */     System.err.print(ex.getMessage());
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static List<String[]> desencriptaBancoInternacional(String fileName, InputStream inputArchivoEncriptado)
/* 162:    */     throws ExcepcionAS2Encriptacion
/* 163:    */   {
/* 164:146 */     List<String[]> datos = new ArrayList();
/* 165:147 */     File archiEncriptado = new File(fileName);
/* 166:    */     try
/* 167:    */     {
/* 168:149 */       FileOutputStream output = new FileOutputStream(archiEncriptado);
/* 169:151 */       while (inputArchivoEncriptado.available() != 0) {
/* 170:152 */         output.write(inputArchivoEncriptado.read());
/* 171:    */       }
/* 172:154 */       output.flush();
/* 173:155 */       output.close();
/* 174:156 */       inputArchivoEncriptado.close();
/* 175:    */     }
/* 176:    */     catch (FileNotFoundException e)
/* 177:    */     {
/* 178:159 */       new ExcepcionAS2Encriptacion("No existe el archivo " + fileName);
/* 179:    */     }
/* 180:    */     catch (IOException e)
/* 181:    */     {
/* 182:161 */       new ExcepcionAS2Encriptacion(e.getMessage());
/* 183:    */     }
/* 184:164 */     AESKeyBancoInternacional key = new AESKeyBancoInternacional();
/* 185:165 */     key.init();
/* 186:    */     
/* 187:167 */     String nombreArchivoDesencriptado = fileName.substring(0, fileName.indexOf("_"));
/* 188:168 */     String extensionArchivoDesencriptado = fileName.substring(fileName.indexOf("."));
/* 189:169 */     File archivoDesencriptado = new File(nombreArchivoDesencriptado + extensionArchivoDesencriptado);
/* 190:    */     try
/* 191:    */     {
/* 192:172 */       AES.desencriptar(key.getStrKa().toCharArray(), archiEncriptado, archivoDesencriptado);
/* 193:    */       try
/* 194:    */       {
/* 195:175 */         BufferedReader entrada = new BufferedReader(new FileReader(archivoDesencriptado));
/* 196:177 */         while (entrada.ready())
/* 197:    */         {
/* 198:178 */           String[] factura = entrada.readLine().split("\\t");
/* 199:179 */           datos.add(factura);
/* 200:    */         }
/* 201:    */       }
/* 202:    */       catch (IOException e)
/* 203:    */       {
/* 204:182 */         new ExcepcionAS2Encriptacion(e.getMessage());
/* 205:    */       }
/* 206:    */     }
/* 207:    */     catch (Exception e1)
/* 208:    */     {
/* 209:186 */       new ExcepcionAS2Encriptacion(e1.getMessage());
/* 210:    */     }
/* 211:189 */     return datos;
/* 212:    */   }
/* 213:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.encriptacion.AESEncriptacion
 * JD-Core Version:    0.7.0.1
 */