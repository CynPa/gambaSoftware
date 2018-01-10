/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import javax.faces.component.UIComponent;
/*  5:   */ import javax.faces.context.FacesContext;
/*  6:   */ import javax.faces.convert.Converter;
/*  7:   */ import javax.faces.convert.FacesConverter;
/*  8:   */ 
/*  9:   */ @FacesConverter("stringLimitConverter")
/* 10:   */ public class StringLimitConverter
/* 11:   */   implements Converter
/* 12:   */ {
/* 13:   */   private static final String LIMIT_PARAMETER_NAME = "limit";
/* 14:   */   private static final int DEFAULT_LIMIT = 5;
/* 15:   */   
/* 16:   */   public Object getAsObject(FacesContext context, UIComponent component, String value)
/* 17:   */   {
/* 18:41 */     return limit(value, getLimitAttribute(component));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getAsString(FacesContext context, UIComponent component, Object value)
/* 22:   */   {
/* 23:54 */     if (!(value instanceof String)) {
/* 24:55 */       return null;
/* 25:   */     }
/* 26:57 */     return limit(value.toString(), getLimitAttribute(component));
/* 27:   */   }
/* 28:   */   
/* 29:   */   private int getLimitAttribute(UIComponent component)
/* 30:   */   {
/* 31:62 */     Object att = component.getAttributes().get("limit");
/* 32:63 */     if (att == null) {
/* 33:64 */       return 5;
/* 34:   */     }
/* 35:66 */     return Integer.parseInt((String)component.getAttributes().get("limit"));
/* 36:   */   }
/* 37:   */   
/* 38:   */   private String limit(String s, int limit)
/* 39:   */   {
/* 40:71 */     String limited = s;
/* 41:72 */     if (s.length() > limit) {
/* 42:73 */       limited = s.substring(0, limit) + "...";
/* 43:   */     }
/* 44:74 */     return limited;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.StringLimitConverter
 * JD-Core Version:    0.7.0.1
 */