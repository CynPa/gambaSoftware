/*  1:   */ package com.asinfo.as2.web;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.Accion;
/*  4:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  5:   */ import com.asinfo.as2.util.AppUtil;
/*  6:   */ import com.asinfo.as2.util.SecurityUtil;
/*  7:   */ import javax.faces.application.FacesMessage;
/*  8:   */ import javax.faces.component.UIComponent;
/*  9:   */ import javax.faces.component.UIViewRoot;
/* 10:   */ import javax.faces.context.FacesContext;
/* 11:   */ import javax.faces.event.AbortProcessingException;
/* 12:   */ import javax.faces.event.ActionEvent;
/* 13:   */ import javax.faces.event.ActionListener;
/* 14:   */ import javax.security.auth.Subject;
/* 15:   */ 
/* 16:   */ public class JAASActionListener
/* 17:   */   implements ActionListener
/* 18:   */ {
/* 19:31 */   private ActionListener parent = null;
/* 20:   */   
/* 21:   */   public JAASActionListener(ActionListener parent)
/* 22:   */   {
/* 23:34 */     this.parent = parent;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processAction(ActionEvent event)
/* 27:   */     throws AbortProcessingException
/* 28:   */   {
/* 29:39 */     FacesContext context = FacesContext.getCurrentInstance();
/* 30:   */     
/* 31:41 */     String accion = event.getComponent().getClientId();
/* 32:42 */     String[] accionArray = accion.split(":");
/* 33:43 */     if (accionArray.length > 1) {
/* 34:44 */       accion = accionArray[(accionArray.length - 1)];
/* 35:   */     }
/* 36:48 */     boolean onLoginPage = -1 != context.getViewRoot().getViewId().lastIndexOf("login");
/* 37:50 */     if ((onLoginPage) || ((!accion.equals(Accion.CREATE.toString())) && 
/* 38:51 */       (!accion.equals(Accion.UPDATE.toString())) && 
/* 39:52 */       (!accion.equals(Accion.PRINT.toString())) && 
/* 40:53 */       (!accion.equals(Accion.DELETE.toString()))))
/* 41:   */     {
/* 42:55 */       this.parent.processAction(event);
/* 43:   */     }
/* 44:   */     else
/* 45:   */     {
/* 46:58 */       boolean tienePermiso = false;
/* 47:59 */       String viewId = context.getViewRoot().getViewId();
/* 48:   */       
/* 49:61 */       Subject subject = (Subject)AppUtil.getAtributo("javax.security.auth.subject");
/* 50:63 */       if ((subject != null) && (
/* 51:64 */         (AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador()) || (SecurityUtil.hasPermissionToAccessViewId(subject, viewId, accion))))
/* 52:   */       {
/* 53:65 */         tienePermiso = true;
/* 54:66 */         this.parent.processAction(event);
/* 55:   */       }
/* 56:70 */       if (!tienePermiso)
/* 57:   */       {
/* 58:72 */         FacesMessage infoMessage = new FacesMessage();
/* 59:73 */         infoMessage.setSummary(null);
/* 60:74 */         infoMessage.setDetail(" Con su ROL actual no tiene permisos para ejecutar esta accion");
/* 61:75 */         infoMessage.setSeverity(FacesMessage.SEVERITY_INFO);
/* 62:76 */         FacesContext.getCurrentInstance().addMessage(null, infoMessage);
/* 63:   */       }
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.JAASActionListener
 * JD-Core Version:    0.7.0.1
 */