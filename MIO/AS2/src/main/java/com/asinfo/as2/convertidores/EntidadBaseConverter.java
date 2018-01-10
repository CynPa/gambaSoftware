/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.faces.component.UIComponent;
/*  7:   */ import javax.faces.context.ExternalContext;
/*  8:   */ import javax.faces.context.FacesContext;
/*  9:   */ import javax.faces.convert.Converter;
/* 10:   */ 
/* 11:   */ public class EntidadBaseConverter<T extends EntidadBase>
/* 12:   */   implements Converter
/* 13:   */ {
/* 14:30 */   private String clave = "com.asinfo.as2.entities.EntidadBase";
/* 15:31 */   private String empty = "";
/* 16:   */   
/* 17:   */   public EntidadBaseConverter(String clave)
/* 18:   */   {
/* 19:35 */     this.clave = clave;
/* 20:   */   }
/* 21:   */   
/* 22:   */   private Map<String, Object> getViewMap(FacesContext context)
/* 23:   */   {
/* 24:40 */     Map<String, Object> viewMap = context.getExternalContext().getSessionMap();
/* 25:   */     
/* 26:   */ 
/* 27:43 */     Map<String, Object> idMap = (Map)viewMap.get(this.clave);
/* 28:44 */     if (idMap == null)
/* 29:   */     {
/* 30:45 */       idMap = new HashMap();
/* 31:46 */       viewMap.put(this.clave, idMap);
/* 32:   */     }
/* 33:48 */     return idMap;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Object getAsObject(FacesContext context, UIComponent c, String value)
/* 37:   */   {
/* 38:59 */     if (value.isEmpty()) {
/* 39:60 */       return null;
/* 40:   */     }
/* 41:62 */     return getViewMap(context).get(value);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getAsString(FacesContext context, UIComponent c, Object value)
/* 45:   */   {
/* 46:74 */     if (value == null) {
/* 47:75 */       return this.empty;
/* 48:   */     }
/* 49:77 */     if ((value instanceof EntidadBase))
/* 50:   */     {
/* 51:78 */       String id = String.valueOf(((EntidadBase)value).getRowKey());
/* 52:79 */       getViewMap(context).put(id, value);
/* 53:80 */       return id;
/* 54:   */     }
/* 55:83 */     return this.empty;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.EntidadBaseConverter
 * JD-Core Version:    0.7.0.1
 */