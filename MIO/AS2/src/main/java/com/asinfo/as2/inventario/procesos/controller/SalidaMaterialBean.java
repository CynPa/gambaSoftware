/*  1:   */ package com.asinfo.as2.inventario.procesos.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  5:   */ import javax.faces.bean.ManagedBean;
/*  6:   */ import javax.faces.bean.ViewScoped;
/*  7:   */ 
/*  8:   */ @ManagedBean
/*  9:   */ @ViewScoped
/* 10:   */ public class SalidaMaterialBean
/* 11:   */   extends ConsumoBodegaBean
/* 12:   */ {
/* 13:   */   private static final long serialVersionUID = 1L;
/* 14:   */   
/* 15:   */   public DocumentoBase getDocumentoBase()
/* 16:   */   {
/* 17:29 */     return DocumentoBase.SALIDA_MATERIAL;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String editar()
/* 21:   */   {
/* 22:34 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 23:35 */     return "";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String crear()
/* 27:   */   {
/* 28:40 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 29:41 */     return "";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String eliminar()
/* 33:   */   {
/* 34:46 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 35:47 */     return "";
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.SalidaMaterialBean
 * JD-Core Version:    0.7.0.1
 */