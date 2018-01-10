/*  1:   */ package com.asinfo.as2.util;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.seguridad.modelo.Rol;
/*  4:   */ import com.asinfo.as2.util.seguridad.AuthorizationPermission;
/*  5:   */ import java.security.AccessController;
/*  6:   */ import java.security.Permission;
/*  7:   */ import java.security.Principal;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import javax.security.auth.Subject;
/* 10:   */ 
/* 11:   */ public class SecurityUtil
/* 12:   */ {
/* 13:   */   public static boolean hasPermissions(Permission... permissions)
/* 14:   */   {
/* 15:18 */     for (Permission permission : permissions) {
/* 16:   */       try
/* 17:   */       {
/* 18:20 */         AccessController.checkPermission(permission);
/* 19:   */       }
/* 20:   */       catch (Exception e)
/* 21:   */       {
/* 22:22 */         return false;
/* 23:   */       }
/* 24:   */     }
/* 25:25 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static boolean hasPermission(Permission permission)
/* 29:   */   {
/* 30:   */     try
/* 31:   */     {
/* 32:30 */       AccessController.checkPermission(permission);
/* 33:31 */       return true;
/* 34:   */     }
/* 35:   */     catch (Exception e) {}
/* 36:33 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static boolean hasAtLeastOnePermission(Permission... permissions)
/* 40:   */   {
/* 41:38 */     for (Permission permission : permissions) {
/* 42:39 */       if (hasPermission(permission)) {
/* 43:40 */         return true;
/* 44:   */       }
/* 45:   */     }
/* 46:43 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static Subject getSubject()
/* 50:   */   {
/* 51:47 */     return Subject.getSubject(AccessController.getContext());
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static boolean hasPermissionToAccessViewId(Subject subject, String viewId, String accion)
/* 55:   */   {
/* 56:61 */     boolean result = false;
/* 57:63 */     for (Principal p : subject.getPrincipals())
/* 58:   */     {
/* 59:64 */       Rol rol = (Rol)p;
/* 60:   */       
/* 61:66 */       AuthorizationPermission permiso = (AuthorizationPermission)rol.getPermissions().get(viewId);
/* 62:68 */       if (permiso != null)
/* 63:   */       {
/* 64:69 */         String acciones = permiso.getAcciones().toUpperCase();
/* 65:70 */         AuthorizationPermission au = permiso;
/* 66:71 */         if (viewId.equals(au.getViewId())) {
/* 67:73 */           if (acciones.contains("ALL")) {
/* 68:74 */             result = true;
/* 69:75 */           } else if (acciones.contains("NONE")) {
/* 70:76 */             result = false;
/* 71:77 */           } else if (acciones.contains(accion)) {
/* 72:78 */             result = true;
/* 73:   */           }
/* 74:   */         }
/* 75:   */       }
/* 76:   */     }
/* 77:84 */     return result;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.SecurityUtil
 * JD-Core Version:    0.7.0.1
 */