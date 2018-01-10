/*  1:   */ package com.asinfo.as2.auditoria;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  5:   */ import com.asinfo.as2.util.AppUtil;
/*  6:   */ import java.util.Locale;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.faces.component.UIViewRoot;
/*  9:   */ import javax.faces.context.ExternalContext;
/* 10:   */ import javax.faces.context.FacesContext;
/* 11:   */ 
/* 12:   */ public class ViewHelperAS2
/* 13:   */ {
/* 14:   */   public static void cambiarIdioma(String idioma)
/* 15:   */   {
/* 16:37 */     FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(idioma));
/* 17:   */   }
/* 18:   */   
/* 19:   */   @Deprecated
/* 20:   */   public static EntidadBase agregaUsuarioEnSesion(EntidadBase entidadBase)
/* 21:   */   {
/* 22:53 */     Usuario usuarioEnSesion = getUsuarioEnSesion();
/* 23:54 */     entidadBase.setUsuarioModificacion(usuarioEnSesion.getNombreUsuario());
/* 24:   */     
/* 25:56 */     return entidadBase;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static Usuario getUsuarioEnSesion()
/* 29:   */   {
/* 30:65 */     Usuario usuarioEnSesion = null;
/* 31:   */     
/* 32:67 */     Object objectUsuarioEnSesion = AppUtil.getAtributo("com.asinfo.as2.usuario");
/* 33:69 */     if (objectUsuarioEnSesion != null) {
/* 34:70 */       usuarioEnSesion = (Usuario)objectUsuarioEnSesion;
/* 35:   */     } else {
/* 36:72 */       usuarioEnSesion = new Usuario("usuario_anonimo");
/* 37:   */     }
/* 38:74 */     return usuarioEnSesion;
/* 39:   */   }
/* 40:   */   
/* 41:   */   private static Map<String, Object> getSessionMap()
/* 42:   */   {
/* 43:85 */     Map<String, Object> sessionMap = getExternalContext().getSessionMap();
/* 44:86 */     return sessionMap;
/* 45:   */   }
/* 46:   */   
/* 47:   */   private static ExternalContext getExternalContext()
/* 48:   */   {
/* 49:96 */     ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
/* 50:97 */     return context;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.auditoria.ViewHelperAS2
 * JD-Core Version:    0.7.0.1
 */