/*  1:   */ package com.asinfo.as2.util;
/*  2:   */ 
/*  3:   */ import javax.faces.component.UIComponent;
/*  4:   */ import javax.faces.context.ExternalContext;
/*  5:   */ import javax.faces.context.FacesContext;
/*  6:   */ 
/*  7:   */ public class FileUploadRenderer
/*  8:   */   extends org.primefaces.component.fileupload.FileUploadRenderer
/*  9:   */ {
/* 10:   */   public void decode(FacesContext context, UIComponent component)
/* 11:   */   {
/* 12:10 */     if (context.getExternalContext().getRequestContentType().toLowerCase().startsWith("multipart/")) {
/* 13:11 */       super.decode(context, component);
/* 14:   */     }
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.FileUploadRenderer
 * JD-Core Version:    0.7.0.1
 */