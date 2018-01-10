/*  1:   */ package com.asinfo.as2.inventario.procesos.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  5:   */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  6:   */ import com.asinfo.as2.utils.JsfUtil;
/*  7:   */ import javax.annotation.PostConstruct;
/*  8:   */ import javax.faces.bean.ManagedBean;
/*  9:   */ import javax.faces.bean.ViewScoped;
/* 10:   */ 
/* 11:   */ @ManagedBean
/* 12:   */ @ViewScoped
/* 13:   */ public class DevolucionOrdenSalidaMaterialBean
/* 14:   */   extends OrdenSalidaMaterialBean
/* 15:   */ {
/* 16:   */   private static final long serialVersionUID = 1L;
/* 17:   */   
/* 18:   */   @PostConstruct
/* 19:   */   public void init()
/* 20:   */   {
/* 21:41 */     this.ingresoDevolucion = true;
/* 22:42 */     super.init();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String guardar()
/* 26:   */   {
/* 27:48 */     return guardar(true, true);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String crear()
/* 31:   */   {
/* 32:53 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 33:54 */     return "";
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void confirmarDevolucion()
/* 37:   */   {
/* 38:   */     try
/* 39:   */     {
/* 40:59 */       this.servicioOrdenSalidaMaterial.confirmarDevolucion(this.ordenSalidaMaterial);
/* 41:   */     }
/* 42:   */     catch (AS2Exception e)
/* 43:   */     {
/* 44:61 */       JsfUtil.addErrorMessage(e, "");
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.DevolucionOrdenSalidaMaterialBean
 * JD-Core Version:    0.7.0.1
 */