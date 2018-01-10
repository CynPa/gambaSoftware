/*  1:   */ package com.asinfo.as2.internacionalizacion.datamanager;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.auditoria.ViewHelperAS2;
/*  4:   */ import com.asinfo.as2.controller.PageController;
/*  5:   */ import java.util.Locale;
/*  6:   */ import javax.faces.bean.ManagedBean;
/*  7:   */ import javax.faces.bean.SessionScoped;
/*  8:   */ 
/*  9:   */ @ManagedBean
/* 10:   */ @SessionScoped
/* 11:   */ public class InternacionalizacionDataManager
/* 12:   */   extends PageController
/* 13:   */ {
/* 14:   */   private String idiomaSeleccionado;
/* 15:   */   
/* 16:   */   public String getIdiomaSeleccionado()
/* 17:   */   {
/* 18:32 */     if (this.idiomaSeleccionado == null) {
/* 19:33 */       this.idiomaSeleccionado = getLocale().toString();
/* 20:   */     }
/* 21:35 */     ViewHelperAS2.cambiarIdioma(this.idiomaSeleccionado);
/* 22:36 */     return this.idiomaSeleccionado;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setIdiomaSeleccionado(String idiomaSeleccionado)
/* 26:   */   {
/* 27:44 */     this.idiomaSeleccionado = idiomaSeleccionado;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.internacionalizacion.datamanager.InternacionalizacionDataManager
 * JD-Core Version:    0.7.0.1
 */