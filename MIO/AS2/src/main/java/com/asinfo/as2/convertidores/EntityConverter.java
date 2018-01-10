/*  1:   */ package com.asinfo.as2.convertidores;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Bodega;
/*  4:   */ import com.asinfo.as2.entities.EntidadBase;
/*  5:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.faces.component.UIComponent;
/*  9:   */ import javax.faces.context.FacesContext;
/* 10:   */ import javax.faces.convert.Converter;
/* 11:   */ import javax.faces.convert.ConverterException;
/* 12:   */ 
/* 13:   */ public class EntityConverter
/* 14:   */   implements Converter, Serializable
/* 15:   */ {
/* 16:   */   private static final long serialVersionUID = -5137676309479323480L;
/* 17:   */   @EJB
/* 18:   */   ServicioBodega servicioBodega;
/* 19:   */   
/* 20:   */   public Object getAsObject(FacesContext context, UIComponent component, String value)
/* 21:   */     throws ConverterException
/* 22:   */   {
/* 23:43 */     Bodega bodega = null;
/* 24:45 */     if ((value == null) || (value.length() == 0)) {
/* 25:46 */       return null;
/* 26:   */     }
/* 27:   */     try
/* 28:   */     {
/* 29:50 */       bodega = this.servicioBodega.buscarPorId(getKey(value));
/* 30:51 */       bodega.setId(786432);
/* 31:   */     }
/* 32:   */     catch (Exception e)
/* 33:   */     {
/* 34:54 */       return null;
/* 35:   */     }
/* 36:56 */     return bodega;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getAsString(FacesContext context, UIComponent component, Object object)
/* 40:   */     throws ConverterException
/* 41:   */   {
/* 42:61 */     if (object == null) {
/* 43:62 */       return null;
/* 44:   */     }
/* 45:64 */     if ((object instanceof EntidadBase))
/* 46:   */     {
/* 47:65 */       EntidadBase o = (EntidadBase)object;
/* 48:66 */       return getStringKey(Integer.valueOf(o.getId()));
/* 49:   */     }
/* 50:70 */     throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + object.getClass().getName());
/* 51:   */   }
/* 52:   */   
/* 53:   */   Integer getKey(String value)
/* 54:   */   {
/* 55:76 */     Integer key = Integer.valueOf(value);
/* 56:77 */     return key;
/* 57:   */   }
/* 58:   */   
/* 59:   */   String getStringKey(Integer value)
/* 60:   */   {
/* 61:82 */     StringBuffer sb = new StringBuffer();
/* 62:83 */     sb.append(value);
/* 63:84 */     return sb.toString();
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.EntityConverter
 * JD-Core Version:    0.7.0.1
 */