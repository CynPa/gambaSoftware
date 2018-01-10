/*  1:   */ package com.asinfo.as2.compronteselectronicos.base;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*  4:   */ import java.io.File;
/*  5:   */ 
/*  6:   */ public class ProxyConfig
/*  7:   */ {
/*  8:16 */   public static final String KEY_STORE_SRI = ServicioConfiguracion.DIRECTORIO_SRI + File.separator + "certificados" + File.separator + "jssecacerts";
/*  9:   */   
/* 10:   */   public static void init()
/* 11:   */   {
/* 12:20 */     System.setProperty("java.net.useSystemProxies", "true");
/* 13:21 */     System.setProperty("javax.net.ssl.trustStore", KEY_STORE_SRI);
/* 14:22 */     System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.base.ProxyConfig
 * JD-Core Version:    0.7.0.1
 */