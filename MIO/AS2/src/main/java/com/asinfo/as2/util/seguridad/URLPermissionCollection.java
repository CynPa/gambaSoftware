/*  1:   */ package com.asinfo.as2.util.seguridad;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.security.Permission;
/*  5:   */ import java.security.PermissionCollection;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Collections;
/*  8:   */ import java.util.Enumeration;
/*  9:   */ import java.util.List;
/* 10:   */ 
/* 11:   */ final class URLPermissionCollection
/* 12:   */   extends PermissionCollection
/* 13:   */   implements Serializable
/* 14:   */ {
/* 15:   */   private static final long serialVersionUID = 1L;
/* 16:39 */   private List<Permission> perms = new ArrayList();
/* 17:   */   
/* 18:   */   public void add(Permission permission)
/* 19:   */   {
/* 20:42 */     if (!(permission instanceof URLPermission)) {
/* 21:43 */       throw new IllegalArgumentException("invalid permission: " + permission);
/* 22:   */     }
/* 23:46 */     if (isReadOnly()) {
/* 24:47 */       throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/* 25:   */     }
/* 26:50 */     synchronized (this)
/* 27:   */     {
/* 28:51 */       this.perms.add(permission);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean implies(Permission permission)
/* 33:   */   {
/* 34:56 */     if ((permission instanceof URLPermission)) {
/* 35:57 */       synchronized (this)
/* 36:   */       {
/* 37:58 */         return this.perms.contains(permission);
/* 38:   */       }
/* 39:   */     }
/* 40:61 */     return false;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Enumeration<Permission> elements()
/* 44:   */   {
/* 45:66 */     synchronized (this)
/* 46:   */     {
/* 47:67 */       return Collections.enumeration(this.perms);
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.URLPermissionCollection
 * JD-Core Version:    0.7.0.1
 */