/*  1:   */ package com.asinfo.as2.util.seguridad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.seguridad.modelo.Rol;
/*  4:   */ import java.net.URL;
/*  5:   */ import java.security.CodeSource;
/*  6:   */ import java.security.PermissionCollection;
/*  7:   */ import java.security.Policy;
/*  8:   */ import java.security.Principal;
/*  9:   */ import java.security.ProtectionDomain;
/* 10:   */ 
/* 11:   */ public class DynamicPolicy
/* 12:   */   extends Policy
/* 13:   */ {
/* 14:   */   private Policy deferredPolicy;
/* 15:   */   
/* 16:   */   public DynamicPolicy()
/* 17:   */   {
/* 18:27 */     this.deferredPolicy = Policy.getPolicy();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public PermissionCollection getPermissions(ProtectionDomain domain)
/* 22:   */   {
/* 23:32 */     PermissionCollection pc = this.deferredPolicy.getPermissions(domain);
/* 24:33 */     String location = domain.getCodeSource().getLocation().toString();
/* 25:35 */     if ((location.indexOf("as2") > 0) && 
/* 26:36 */       (!pc.isReadOnly())) {
/* 27:37 */       for (Principal p : domain.getPrincipals()) {
/* 28:38 */         Rol localRol = (Rol)p;
/* 29:   */       }
/* 30:   */     }
/* 31:46 */     return pc;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void refresh()
/* 35:   */   {
/* 36:50 */     this.deferredPolicy.refresh();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void afterPropertiesSet()
/* 40:   */     throws Exception
/* 41:   */   {
/* 42:54 */     Policy.setPolicy(this);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.DynamicPolicy
 * JD-Core Version:    0.7.0.1
 */