/*  1:   */ package com.asinfo.as2.util.seguridad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.security.auth.login.AppConfigurationEntry;
/*  7:   */ import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
/*  8:   */ import javax.security.auth.login.Configuration;
/*  9:   */ 
/* 10:   */ public class LoginConfiguration
/* 11:   */   extends Configuration
/* 12:   */ {
/* 13:   */   private Usuario usuario;
/* 14:   */   
/* 15:   */   public LoginConfiguration(Usuario usuario)
/* 16:   */   {
/* 17:21 */     this.usuario = usuario;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public AppConfigurationEntry[] getAppConfigurationEntry(String name)
/* 21:   */   {
/* 22:26 */     Map<String, Object> values = new HashMap();
/* 23:27 */     values.put("usuario", this.usuario);
/* 24:28 */     values.put("debug", "true");
/* 25:29 */     AppConfigurationEntry ace = new AppConfigurationEntry(LoginModuleImpl.class.getName(), AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, values);
/* 26:30 */     return new AppConfigurationEntry[] { ace };
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.LoginConfiguration
 * JD-Core Version:    0.7.0.1
 */