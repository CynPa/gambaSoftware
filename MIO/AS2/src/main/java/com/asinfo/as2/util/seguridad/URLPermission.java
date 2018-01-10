/*  1:   */ package com.asinfo.as2.util.seguridad;
/*  2:   */ 
/*  3:   */ import java.security.BasicPermission;
/*  4:   */ import java.security.PermissionCollection;
/*  5:   */ 
/*  6:   */ public class URLPermission
/*  7:   */   extends BasicPermission
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   
/* 11:   */   public URLPermission(String name)
/* 12:   */   {
/* 13:22 */     super(name);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public PermissionCollection newPermissionCollection()
/* 17:   */   {
/* 18:30 */     return new URLPermissionCollection();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.URLPermission
 * JD-Core Version:    0.7.0.1
 */