/*  1:   */ package com.asinfo.as2.util.exception;
/*  2:   */ 
/*  3:   */ public class SecurityViolationException
/*  4:   */   extends ServiceException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1L;
/*  7:   */   
/*  8:   */   public SecurityViolationException() {}
/*  9:   */   
/* 10:   */   public SecurityViolationException(String message)
/* 11:   */   {
/* 12:19 */     super(message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public SecurityViolationException(String message, Throwable cause)
/* 16:   */   {
/* 17:23 */     super(message, cause);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public SecurityViolationException(Throwable cause)
/* 21:   */   {
/* 22:27 */     super(cause);
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.exception.SecurityViolationException
 * JD-Core Version:    0.7.0.1
 */