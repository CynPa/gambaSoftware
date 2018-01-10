/*  1:   */ package com.asinfo.as2.util.exception;
/*  2:   */ 
/*  3:   */ public class ServiceException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1L;
/*  7:   */   
/*  8:   */   public ServiceException() {}
/*  9:   */   
/* 10:   */   public ServiceException(String message)
/* 11:   */   {
/* 12:19 */     super(message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ServiceException(String message, Throwable cause)
/* 16:   */   {
/* 17:23 */     super(message, cause);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ServiceException(Throwable cause)
/* 21:   */   {
/* 22:27 */     super(cause);
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.exception.ServiceException
 * JD-Core Version:    0.7.0.1
 */