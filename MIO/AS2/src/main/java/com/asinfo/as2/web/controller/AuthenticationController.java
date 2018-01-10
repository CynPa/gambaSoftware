/*   1:    */ package com.asinfo.as2.web.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   6:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   7:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.util.SecurityUtil;
/*  10:    */ import com.asinfo.as2.util.seguridad.LoginConfiguration;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import com.asinfo.as2.web.seguridad.HttpCallbackHandler;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.io.Serializable;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Set;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.application.Application;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.SessionScoped;
/*  22:    */ import javax.faces.context.ExternalContext;
/*  23:    */ import javax.faces.context.FacesContext;
/*  24:    */ import javax.faces.event.ExceptionQueuedEvent;
/*  25:    */ import javax.faces.event.ExceptionQueuedEventContext;
/*  26:    */ import javax.security.auth.Subject;
/*  27:    */ import javax.security.auth.login.Configuration;
/*  28:    */ import javax.security.auth.login.LoginContext;
/*  29:    */ import javax.security.auth.login.LoginException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @SessionScoped
/*  34:    */ public class AuthenticationController
/*  35:    */   extends PageController
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   private String nombreUsuario;
/*  40: 40 */   private String clave = "";
/*  41:    */   private String confirmaClave;
/*  42:    */   private String redirectView;
/*  43:    */   private Usuario usuario;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioUsuario servicioUsuario;
/*  46:    */   
/*  47:    */   public String guardarUsuario()
/*  48:    */   {
/*  49: 49 */     if (this.clave.equals(this.confirmaClave))
/*  50:    */     {
/*  51: 51 */       EntidadUsuario e = this.servicioUsuario.buscarPorId(Integer.valueOf(this.usuario.getIdUsuario()));
/*  52: 52 */       e.setIndicadorNuevo(false);
/*  53: 53 */       this.usuario.setIndicadorNuevo(false);
/*  54: 54 */       e.setClave(this.clave);
/*  55: 55 */       this.servicioUsuario.guardarNuevo(e);
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 57 */       addErrorMessage("Las claves no coinciden");
/*  60:    */     }
/*  61: 59 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String login()
/*  65:    */     throws LoginException
/*  66:    */   {
/*  67: 64 */     String loginSuccess = "login_failure";
/*  68:    */     try
/*  69:    */     {
/*  70: 67 */       LoginContext loginContext = createLoginContext();
/*  71:    */       
/*  72: 69 */       loginContext.login();
/*  73:    */       
/*  74: 71 */       Subject subject = loginContext.getSubject();
/*  75: 72 */       this.usuario = ((Usuario)subject.getPublicCredentials().iterator().next());
/*  76:    */       
/*  77: 74 */       AppUtil.setAtributo("javax.security.auth.subject", subject);
/*  78: 75 */       AppUtil.setAtributo("com.asinfo.as2.usuario", this.usuario);
/*  79: 76 */       AppUtil.setAtributo("host_remoro", getIP());
/*  80:    */       
/*  81: 78 */       redirectIfNecessary();
/*  82: 79 */       if (!this.usuario.isIndicadorNuevo()) {
/*  83: 80 */         loginSuccess = "login_success";
/*  84:    */       } else {
/*  85: 82 */         loginSuccess = "";
/*  86:    */       }
/*  87:    */     }
/*  88:    */     catch (LoginException e)
/*  89:    */     {
/*  90: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_login"));
/*  91: 87 */       LOG.info("Error al iniciar sesion " + e.getMessage());
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95: 89 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  96: 90 */       LOG.info("Error al iniciar sesion " + e.getMessage());
/*  97:    */     }
/*  98: 93 */     return loginSuccess;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String logout()
/* 102:    */     throws LoginException
/* 103:    */   {
/* 104: 98 */     invalidarSesion();
/* 105: 99 */     return "/login?faces-redirect=true";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void invalidarSesion()
/* 109:    */   {
/* 110:106 */     FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
/* 111:    */   }
/* 112:    */   
/* 113:    */   private LoginContext createLoginContext()
/* 114:    */     throws LoginException
/* 115:    */   {
/* 116:110 */     Subject subject = SecurityUtil.getSubject();
/* 117:    */     
/* 118:112 */     Configuration conf = new LoginConfiguration(this.usuario);
/* 119:    */     
/* 120:114 */     return new LoginContext("as2", subject, new HttpCallbackHandler(this.nombreUsuario, this.clave), conf);
/* 121:    */   }
/* 122:    */   
/* 123:    */   private void redirectIfNecessary()
/* 124:    */   {
/* 125:118 */     if (this.redirectView != null) {
/* 126:    */       try
/* 127:    */       {
/* 128:120 */         FacesContext.getCurrentInstance().getExternalContext().redirect(this.redirectView);
/* 129:121 */         this.redirectView = null;
/* 130:    */       }
/* 131:    */       catch (IOException e)
/* 132:    */       {
/* 133:123 */         e.printStackTrace();
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void setRedirect(String value) {}
/* 139:    */   
/* 140:    */   protected void publishException(Exception e)
/* 141:    */   {
/* 142:135 */     FacesContext ctx = FacesContext.getCurrentInstance();
/* 143:136 */     ExceptionQueuedEventContext eventContext = new ExceptionQueuedEventContext(ctx, e);
/* 144:137 */     ctx.getApplication().publishEvent(ctx, ExceptionQueuedEvent.class, eventContext);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getNombreUsuario()
/* 148:    */   {
/* 149:146 */     return this.nombreUsuario;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setNombreUsuario(String nombreUsuario)
/* 153:    */   {
/* 154:156 */     this.nombreUsuario = nombreUsuario;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getClave()
/* 158:    */   {
/* 159:165 */     return this.clave;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setClave(String clave)
/* 163:    */   {
/* 164:175 */     clave = AppUtil.encriptaEnMD5(clave);
/* 165:    */     
/* 166:177 */     this.clave = clave;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getRedirectView()
/* 170:    */   {
/* 171:186 */     return this.redirectView;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setRedirectView(String redirectView)
/* 175:    */   {
/* 176:196 */     this.redirectView = redirectView;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Usuario getUsuario()
/* 180:    */   {
/* 181:205 */     return this.usuario;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setUsuario(Usuario usuario)
/* 185:    */   {
/* 186:215 */     this.usuario = usuario;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public int getAnio()
/* 190:    */   {
/* 191:224 */     return FuncionesUtiles.getAnio(new Date());
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String getConfirmaClave()
/* 195:    */   {
/* 196:228 */     return this.confirmaClave;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setConfirmaClave(String confirmaClave)
/* 200:    */   {
/* 201:232 */     confirmaClave = AppUtil.encriptaEnMD5(confirmaClave);
/* 202:233 */     this.confirmaClave = confirmaClave;
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.controller.AuthenticationController
 * JD-Core Version:    0.7.0.1
 */