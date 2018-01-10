/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import javax.naming.Context;
/*  6:   */ import javax.naming.InitialContext;
/*  7:   */ import javax.naming.NamingException;
/*  8:   */ 
/*  9:   */ public class ServiceLocator
/* 10:   */ {
/* 11:   */   private static ServiceLocator serviceLocatorInstance;
/* 12:   */   private Map<String, Object> cacheEJB;
/* 13:   */   private Context context;
/* 14:   */   public static String APP_NAME;
/* 15:   */   
/* 16:   */   static
/* 17:   */   {
/* 18:   */     try
/* 19:   */     {
/* 20:34 */       InitialContext ic = new InitialContext();
/* 21:35 */       APP_NAME = (String)ic.lookup("java:app/AppName");
/* 22:   */     }
/* 23:   */     catch (NamingException e)
/* 24:   */     {
/* 25:38 */       e.printStackTrace();
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   private ServiceLocator()
/* 30:   */     throws NamingException
/* 31:   */   {
/* 32:49 */     this.context = new InitialContext();
/* 33:50 */     this.cacheEJB = new HashMap();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static ServiceLocator getInstance()
/* 37:   */     throws NamingException
/* 38:   */   {
/* 39:61 */     if (serviceLocatorInstance == null) {
/* 40:62 */       serviceLocatorInstance = new ServiceLocator();
/* 41:   */     }
/* 42:64 */     return serviceLocatorInstance;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Object getEJB(String nombreJNDI)
/* 46:   */     throws NamingException
/* 47:   */   {
/* 48:77 */     nombreJNDI = "java:app/" + APP_NAME + "/" + nombreJNDI.replace("/local", "");
/* 49:   */     
/* 50:79 */     Object ejbEncontrado = null;
/* 51:80 */     if (this.cacheEJB.containsKey(nombreJNDI)) {
/* 52:81 */       ejbEncontrado = this.cacheEJB.get(nombreJNDI);
/* 53:   */     }
/* 54:83 */     if (ejbEncontrado == null)
/* 55:   */     {
/* 56:84 */       ejbEncontrado = this.context.lookup(nombreJNDI);
/* 57:85 */       this.cacheEJB.put(nombreJNDI, ejbEncontrado);
/* 58:   */     }
/* 59:87 */     return ejbEncontrado;
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.ServiceLocator
 * JD-Core Version:    0.7.0.1
 */