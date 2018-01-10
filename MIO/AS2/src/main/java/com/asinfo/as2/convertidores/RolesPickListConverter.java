/*   1:    */ package com.asinfo.as2.convertidores;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.faces.component.UIComponent;
/*   6:    */ import javax.faces.context.FacesContext;
/*   7:    */ import javax.faces.convert.Converter;
/*   8:    */ import javax.faces.convert.ConverterException;
/*   9:    */ import javax.faces.convert.FacesConverter;
/*  10:    */ import org.primefaces.component.picklist.PickList;
/*  11:    */ import org.primefaces.model.DualListModel;
/*  12:    */ 
/*  13:    */ @FacesConverter("rolesPickListConverter")
/*  14:    */ public class RolesPickListConverter
/*  15:    */   implements Converter
/*  16:    */ {
/*  17:    */   public Object getAsObject(FacesContext context, UIComponent component, String value)
/*  18:    */   {
/*  19: 45 */     return getObjectFromUIPickListComponent(component, value);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getAsString(FacesContext context, UIComponent component, Object object)
/*  23:    */   {
/*  24:    */     String string;
/*  25: 59 */     if (object == null) {
/*  26: 60 */       string = "";
/*  27:    */     } else {
/*  28:    */       try
/*  29:    */       {
/*  30: 63 */         string = String.valueOf(((EntidadRol)object).getNombre());
/*  31:    */       }
/*  32:    */       catch (ClassCastException cce)
/*  33:    */       {
/*  34:    */         String string;
/*  35: 65 */         throw new ConverterException();
/*  36:    */       }
/*  37:    */     }
/*  38:    */     String string;
/*  39: 68 */     return string;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private EntidadRol getObjectFromUIPickListComponent(UIComponent component, String value)
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46: 77 */       DualListModel<EntidadRol> dualList = (DualListModel)((PickList)component).getValue();
/*  47: 78 */       EntidadRol entidadRol = getObjectFromList(dualList.getSource(), 
/*  48: 79 */         Integer.valueOf(value));
/*  49: 80 */       if (entidadRol == null) {}
/*  50: 81 */       return getObjectFromList(dualList.getTarget(), 
/*  51: 82 */         Integer.valueOf(value));
/*  52:    */     }
/*  53:    */     catch (ClassCastException cce)
/*  54:    */     {
/*  55: 87 */       throw new ConverterException();
/*  56:    */     }
/*  57:    */     catch (NumberFormatException nfe)
/*  58:    */     {
/*  59: 89 */       throw new ConverterException();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   private EntidadRol getObjectFromList(List<?> list, Integer identifier)
/*  64:    */   {
/*  65: 95 */     for (Object object : list)
/*  66:    */     {
/*  67: 96 */       EntidadRol entidadRol = (EntidadRol)object;
/*  68: 97 */       if (entidadRol.getNombre().equals(identifier)) {
/*  69: 98 */         return entidadRol;
/*  70:    */       }
/*  71:    */     }
/*  72:101 */     return null;
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.convertidores.RolesPickListConverter
 * JD-Core Version:    0.7.0.1
 */