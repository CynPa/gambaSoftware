/*   1:    */ package com.asinfo.as2.util.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   4:    */ import com.asinfo.as2.seguridad.modelo.Rol;
/*   5:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   6:    */ import com.asinfo.as2.util.AppUtil;
/*   7:    */ import com.asinfo.as2.util.exception.ServiceException;
/*   8:    */ import com.asinfo.as2.utils.ServiceLocator;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import javax.naming.NamingException;
/*  13:    */ import javax.security.auth.Subject;
/*  14:    */ import javax.security.auth.callback.Callback;
/*  15:    */ import javax.security.auth.callback.CallbackHandler;
/*  16:    */ import javax.security.auth.callback.NameCallback;
/*  17:    */ import javax.security.auth.callback.PasswordCallback;
/*  18:    */ import javax.security.auth.callback.UnsupportedCallbackException;
/*  19:    */ import javax.security.auth.login.LoginException;
/*  20:    */ import javax.security.auth.spi.LoginModule;
/*  21:    */ 
/*  22:    */ public class LoginModuleImpl
/*  23:    */   implements LoginModule
/*  24:    */ {
/*  25: 30 */   private boolean debug = false;
/*  26:    */   private Subject subject;
/*  27:    */   private CallbackHandler callbackHandler;
/*  28:    */   private Usuario usuario;
/*  29:    */   
/*  30:    */   public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options)
/*  31:    */   {
/*  32: 38 */     this.subject = subject;
/*  33: 39 */     this.callbackHandler = callbackHandler;
/*  34: 40 */     this.subject = subject;
/*  35: 41 */     this.usuario = ((Usuario)options.get("usuario"));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean login()
/*  39:    */     throws LoginException
/*  40:    */   {
/*  41: 45 */     NameCallback nameCallback = new NameCallback("nombreUsuario");
/*  42: 46 */     PasswordCallback passwordCallback = new PasswordCallback("clave", false);
/*  43: 47 */     Callback[] callbacks = { nameCallback, passwordCallback };
/*  44:    */     try
/*  45:    */     {
/*  46: 50 */       this.callbackHandler.handle(callbacks);
/*  47:    */     }
/*  48:    */     catch (IOException e)
/*  49:    */     {
/*  50: 52 */       e.printStackTrace();
/*  51: 53 */       LoginException ex = new LoginException("IOException logging in.");
/*  52: 54 */       ex.initCause(e);
/*  53: 55 */       throw ex;
/*  54:    */     }
/*  55:    */     catch (UnsupportedCallbackException e)
/*  56:    */     {
/*  57: 57 */       String className = e.getCallback().getClass().getName();
/*  58: 58 */       LoginException ex = new LoginException(className + " is not a supported Callback.");
/*  59: 59 */       ex.initCause(e);
/*  60: 60 */       throw ex;
/*  61:    */     }
/*  62: 62 */     String nombreUsuario = nameCallback.getName();
/*  63: 63 */     String password = String.valueOf(passwordCallback.getPassword());
/*  64: 64 */     passwordCallback.clearPassword();
/*  65:    */     try
/*  66:    */     {
/*  67: 67 */       ServiceLocator serviceLocator = ServiceLocator.getInstance();
/*  68: 68 */       ServicioUsuario servicioUsuario = (ServicioUsuario)serviceLocator.getEJB("ServicioUsuarioImpl/local");
/*  69: 69 */       this.usuario = servicioUsuario.login(nombreUsuario, password);
/*  70:    */       
/*  71: 71 */       return true;
/*  72:    */     }
/*  73:    */     catch (ServiceException e)
/*  74:    */     {
/*  75: 74 */       throw new LoginException(e.getMessage());
/*  76:    */     }
/*  77:    */     catch (NamingException e)
/*  78:    */     {
/*  79: 76 */       throw new LoginException(e.getMessage());
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean commit()
/*  84:    */     throws LoginException
/*  85:    */   {
/*  86: 81 */     this.subject.getPublicCredentials().add(this.usuario);
/*  87: 83 */     for (Rol rol : this.usuario.getListaRol()) {
/*  88: 84 */       this.subject.getPrincipals().add(rol);
/*  89:    */     }
/*  90: 86 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean abort()
/*  94:    */     throws LoginException
/*  95:    */   {
/*  96: 90 */     this.subject.getPublicCredentials().clear();
/*  97: 91 */     this.subject.getPrincipals().clear();
/*  98: 92 */     this.subject.getPrincipals().add(AppUtil.getAnonymousRole());
/*  99: 93 */     this.usuario = null;
/* 100: 94 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean logout()
/* 104:    */     throws LoginException
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:101 */       ServiceLocator serviceLocator = ServiceLocator.getInstance();
/* 109:    */       
/* 110:103 */       ServicioUsuario servicioUsuario = (ServicioUsuario)serviceLocator.getEJB("ServicioUsuarioImpl/local");
/* 111:    */       
/* 112:105 */       servicioUsuario.logout(this.usuario);
/* 113:    */       
/* 114:107 */       boolean bool = true;return bool;
/* 115:    */     }
/* 116:    */     catch (ServiceException e)
/* 117:    */     {
/* 118:109 */       e = e;
/* 119:110 */       throw new LoginException("Can not execute logout process!!!");
/* 120:    */     }
/* 121:    */     catch (NamingException e)
/* 122:    */     {
/* 123:111 */       e = e;
/* 124:112 */       throw new LoginException(e.getMessage());
/* 125:    */     }
/* 126:    */     finally {}
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isDebug()
/* 130:    */   {
/* 131:124 */     return this.debug;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setDebug(boolean debug)
/* 135:    */   {
/* 136:132 */     this.debug = debug;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.LoginModuleImpl
 * JD-Core Version:    0.7.0.1
 */