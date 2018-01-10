/*   1:    */ package com.asinfo.as2.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.ServiceLocator;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.text.MessageFormat;
/*   7:    */ import java.util.Locale;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.MissingResourceException;
/*  10:    */ import java.util.Properties;
/*  11:    */ import java.util.ResourceBundle;
/*  12:    */ import java.util.concurrent.ConcurrentHashMap;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.SessionScoped;
/*  15:    */ import javax.faces.component.UIViewRoot;
/*  16:    */ import javax.faces.context.ExternalContext;
/*  17:    */ import javax.faces.context.FacesContext;
/*  18:    */ import javax.servlet.http.HttpServletRequest;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @SessionScoped
/*  23:    */ public class LanguageController
/*  24:    */ {
/*  25: 25 */   private static Logger LOG = Logger.getLogger(LanguageController.class);
/*  26:    */   protected Properties mensajes;
/*  27: 28 */   protected static boolean accesoWeb = true;
/*  28:    */   protected static String urlHost;
/*  29: 32 */   private static Map<String, Properties> hmMensajes = new ConcurrentHashMap();
/*  30:    */   
/*  31:    */   protected Locale getLocale()
/*  32:    */   {
/*  33: 41 */     Locale locale = null;
/*  34: 42 */     if (accesoWeb) {
/*  35: 43 */       locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
/*  36:    */     }
/*  37: 45 */     if (locale == null)
/*  38:    */     {
/*  39: 46 */       locale = new Locale("es");
/*  40: 47 */       System.out.println("no encontro el idioma, por tanto por defecto es espanol");
/*  41:    */     }
/*  42: 49 */     return locale;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static String getURLHostApp()
/*  46:    */   {
/*  47: 58 */     String URLActual = urlHost;
/*  48: 59 */     if (accesoWeb)
/*  49:    */     {
/*  50: 60 */       Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
/*  51: 61 */       URLActual = ((HttpServletRequest)request).getRequestURL().toString();
/*  52:    */     }
/*  53: 63 */     String URLHostApp = URLActual.substring(0, URLActual.indexOf("/" + ServiceLocator.APP_NAME + "/") + ServiceLocator.APP_NAME.length() + 2);
/*  54: 64 */     return URLHostApp;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getMensaje(String idMensaje)
/*  58:    */   {
/*  59: 68 */     String key = idMensaje;
/*  60: 69 */     String msg = getValue(key);
/*  61: 70 */     return MessageFormat.format(msg, new Object[] { " " });
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Properties getMensajes()
/*  65:    */   {
/*  66: 78 */     this.mensajes = ((Properties)hmMensajes.get(getLocale().toString().toLowerCase()));
/*  67: 80 */     if (this.mensajes == null)
/*  68:    */     {
/*  69: 81 */       this.mensajes = new Properties();
/*  70: 82 */       StringBuilder URLHostApp = new StringBuilder(getURLHostApp());
/*  71: 83 */       URLHostApp.append("resources/mensajes/mensajes_");
/*  72: 84 */       URLHostApp.append(getLocale().toString());
/*  73: 85 */       URLHostApp.append(".properties");
/*  74:    */       try
/*  75:    */       {
/*  76: 87 */         URL urlArchivoProperties = new URL(URLHostApp.toString());
/*  77: 88 */         this.mensajes.load(urlArchivoProperties.openStream());
/*  78:    */         
/*  79: 90 */         hmMensajes.put(getLocale().toString().toLowerCase(), this.mensajes);
/*  80:    */       }
/*  81:    */       catch (Exception e)
/*  82:    */       {
/*  83: 92 */         LOG.error("ERROR AL CARGAR ARCHIVO DE PROPIEDADES", e);
/*  84:    */       }
/*  85:    */     }
/*  86: 96 */     return this.mensajes;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setMensajes(Properties mensajes)
/*  90:    */   {
/*  91:104 */     this.mensajes = mensajes;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isAccesoWeb()
/*  95:    */   {
/*  96:108 */     return accesoWeb;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setAccesoWeb(boolean accesoWeb)
/* 100:    */   {
/* 101:112 */     accesoWeb = accesoWeb;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getUrlHost()
/* 105:    */   {
/* 106:116 */     return urlHost;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setUrlHost(String urlHost)
/* 110:    */   {
/* 111:120 */     urlHost = urlHost;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getValue(String key)
/* 115:    */   {
/* 116:125 */     String result = null;
/* 117:126 */     String patch = "controller.com.asinfo.as2.internacionalizacion.recursos.frontend.ExceptionsBundle";
/* 118:    */     try
/* 119:    */     {
/* 120:128 */       result = ResourceBundle.getBundle(patch, getLocale()).getString(key);
/* 121:    */     }
/* 122:    */     catch (MissingResourceException e)
/* 123:    */     {
/* 124:130 */       result = " ";
/* 125:    */     }
/* 126:132 */     return result;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.controller.LanguageController
 * JD-Core Version:    0.7.0.1
 */