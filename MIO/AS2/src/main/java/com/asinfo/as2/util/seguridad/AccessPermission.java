/*  1:   */ package com.asinfo.as2.util.seguridad;
/*  2:   */ 
/*  3:   */ import java.security.Permission;
/*  4:   */ 
/*  5:   */ public class AccessPermission
/*  6:   */   extends Permission
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   
/* 10:   */   public AccessPermission(String name)
/* 11:   */   {
/* 12:22 */     super(name);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean implies(Permission permission)
/* 16:   */   {
/* 17:26 */     return false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean equals(Object obj)
/* 21:   */   {
/* 22:30 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int hashCode()
/* 26:   */   {
/* 27:34 */     return 0;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getActions()
/* 31:   */   {
/* 32:38 */     return null;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.AccessPermission
 * JD-Core Version:    0.7.0.1
 */