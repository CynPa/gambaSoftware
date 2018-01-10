package com.gmb.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class UtilEncriptaAs2 {
	
	
	private final String ALGORITMO = "AES";
	  private final int LONGITUD = 128;
	  private final String CODIFICACION = "UTF-8";
	  private AESKey aesKey;
	  
	   public UtilEncriptaAs2(AESKey aesKey)
	  {
	     this.aesKey = aesKey;
	   }
   
	   
	      public AESKey generaKey()
	      {
	       KeyGenerator kgen = null;
	       try
	        {
	         kgen = KeyGenerator.getInstance("AES");
	        }
	        catch (NoSuchAlgorithmException ex)
	      {
	         System.out.println(ex);
	       }
	        kgen.init(128);
	        SecretKey skey = kgen.generateKey();
	       this.aesKey = new AESKey();
	        this.aesKey.setEncoded(HexToString(skey.getEncoded()));
	        return this.aesKey;
	     }
  public String encripta(String cadena)
	   {
	     String encriptado = null;
	     try
	     {
	      byte[] raw = this.aesKey.getEncoded().getBytes();
	     SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES");
	      cipher.init(1, skeySpec);
	      byte[] encrypted = cipher.doFinal(cadena.getBytes("UTF-8"));
	     encriptado = HexToString(encrypted);
	    }
	    catch (Exception ex)
	   {
     System.out.println(ex);
	    }
	     return encriptado;
  }
  
	  private String HexToString(byte[] arregloEncriptado)
	  {
	    String textoEncriptado = "";
	     for (int i = 0; i < arregloEncriptado.length; i++)
	   {
	      int aux = arregloEncriptado[i] & 0xFF;
	      if (aux < 16) {
       textoEncriptado = textoEncriptado.concat("0");
	       }
	       textoEncriptado = textoEncriptado.concat(Integer.toHexString(aux));
	     }
	     return textoEncriptado;
	   }
	  
  private byte[] StringToHex(String encriptado)
	  {
	     byte[] enBytes = new byte[encriptado.length() / 2];
	   for (int i = 0; i < enBytes.length; i++)
	   {
	     int index = i * 2;
     String aux = encriptado.substring(index, index + 2);
	      int v = Integer.parseInt(aux, 16);
	      enBytes[i] = ((byte)v);
    }
	    return enBytes;
	  }
  
  
public static void main(String[] args) {
	AESKey valor= new AESKey();
	valor.setEncoded("UTF-8");
	UtilEncriptaAs2 encrip=new UtilEncriptaAs2(valor);
	String er=encrip.desencriptar("b2c53b5a290c71ba99202b12535e723b");
	System.out.println(er);
}
  
   public   String desencriptar(String encriptado)
	  {
	    String originalString = null;
	     try
	   {
      byte[] raw = this.aesKey.getEncoded().getBytes();
	      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	       Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(2, skeySpec);
	      byte[] original = cipher.doFinal(StringToHex(encriptado));
	      originalString = new String(original);
	     }
	     catch (Exception ex)
	     {
	       System.out.println(ex);
	    }
	    return originalString;
	 }

}
