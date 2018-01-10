/*  1:   */ package com.asinfo.as2.excepciones;
/*  2:   */ 
/*  3:   */ import javax.ejb.ApplicationException;
/*  4:   */ 
/*  5:   */ @ApplicationException(rollback=true)
/*  6:   */ public class AS2ExceptionMantenimiento
/*  7:   */   extends AS2Exception
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   
/* 11:   */   public AS2ExceptionMantenimiento() {}
/* 12:   */   
/* 13:   */   public AS2ExceptionMantenimiento(String mensaje)
/* 14:   */   {
/* 15:27 */     super(mensaje);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public AS2ExceptionMantenimiento(String codigoExcepcion, String... messageArguments)
/* 19:   */   {
/* 20:31 */     super(codigoExcepcion, messageArguments);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.excepciones.AS2ExceptionMantenimiento
 * JD-Core Version:    0.7.0.1
 */