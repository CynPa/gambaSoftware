/*   1:    */ package com.asinfo.as2.utils;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   4:    */ import java.util.EnumSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.ResourceBundle;
/*   8:    */ import javax.faces.application.FacesMessage;
/*   9:    */ import javax.faces.component.UIComponent;
/*  10:    */ import javax.faces.context.ExternalContext;
/*  11:    */ import javax.faces.context.FacesContext;
/*  12:    */ import javax.faces.context.Flash;
/*  13:    */ import javax.faces.convert.Converter;
/*  14:    */ import javax.faces.model.SelectItem;
/*  15:    */ 
/*  16:    */ public class JsfUtil
/*  17:    */ {
/*  18:    */   private static final String APLICACION = "AS2";
/*  19:    */   
/*  20:    */   public static Flash flashScope()
/*  21:    */   {
/*  22: 34 */     return FacesContext.getCurrentInstance().getExternalContext().getFlash();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne)
/*  26:    */   {
/*  27: 47 */     int size = selectOne ? entities.size() + 1 : entities.size();
/*  28: 48 */     SelectItem[] items = new SelectItem[size];
/*  29: 49 */     int i = 0;
/*  30: 50 */     if (selectOne)
/*  31:    */     {
/*  32: 51 */       items[0] = new SelectItem("", "---");
/*  33: 52 */       i++;
/*  34:    */     }
/*  35: 54 */     for (Object x : entities) {
/*  36: 55 */       items[(i++)] = new SelectItem(x, x.toString());
/*  37:    */     }
/*  38: 57 */     return items;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static <E extends Enum<E>> SelectItem[] getSelectItems(Class<E> elemType, boolean selectOne)
/*  42:    */   {
/*  43: 70 */     EnumSet<E> enumSet = EnumSet.allOf(elemType);
/*  44: 71 */     int size = selectOne ? enumSet.size() + 1 : enumSet.size();
/*  45: 72 */     SelectItem[] items = new SelectItem[size];
/*  46:    */     
/*  47: 74 */     int i = 0;
/*  48: 75 */     if (selectOne)
/*  49:    */     {
/*  50: 76 */       items[0] = new SelectItem("", "---");
/*  51: 77 */       i++;
/*  52:    */     }
/*  53: 80 */     for (Object x : enumSet) {
/*  54: 81 */       items[(i++)] = new SelectItem(x, x.toString());
/*  55:    */     }
/*  56: 83 */     return items;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static String getMensaje(String idMensaje)
/*  60:    */   {
/*  61: 93 */     return ResourceBundle.getBundle("Mensajes").getString(idMensaje);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void addErrorMessage(AS2Exception ex, String defaultMsg)
/*  65:    */   {
/*  66: 98 */     for (String msg : ex.getMensajes()) {
/*  67: 99 */       if ((msg != null) && (msg.length() > 0)) {
/*  68:100 */         addErrorMessage(msg);
/*  69:    */       } else {
/*  70:102 */         addErrorMessage(defaultMsg);
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void addErrorMessages(List<String> messages)
/*  76:    */   {
/*  77:108 */     for (String message : messages) {
/*  78:109 */       addErrorMessage(message);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static void addErrorMessage(String msg)
/*  83:    */   {
/*  84:114 */     FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "AS2", msg);
/*  85:115 */     FacesContext.getCurrentInstance().addMessage(null, facesMsg);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void addSuccessMessage(String msg)
/*  89:    */   {
/*  90:119 */     FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "AS2", msg);
/*  91:120 */     FacesContext.getCurrentInstance().addMessage(null, facesMsg);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void addInfoMessage(String msg)
/*  95:    */   {
/*  96:124 */     FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "AS2", msg);
/*  97:125 */     FacesContext.getCurrentInstance().addMessage(null, facesMsg);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String getRequestParameter(String key)
/* 101:    */   {
/* 102:129 */     return (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component)
/* 106:    */   {
/* 107:133 */     String theId = getRequestParameter(requestParameterName);
/* 108:134 */     return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.JsfUtil
 * JD-Core Version:    0.7.0.1
 */