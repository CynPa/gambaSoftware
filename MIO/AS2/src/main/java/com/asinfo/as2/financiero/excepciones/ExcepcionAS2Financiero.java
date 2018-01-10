/*  1:   */ package com.asinfo.as2.financiero.excepciones;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  4:   */ 
/*  5:   */ public class ExcepcionAS2Financiero
/*  6:   */   extends ExcepcionAS2
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -4746416066754841739L;
/*  9:   */   
/* 10:   */   public ExcepcionAS2Financiero(String codigo, String mensaje, Exception e)
/* 11:   */   {
/* 12:26 */     super(codigo, mensaje, e);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ExcepcionAS2Financiero(String codigo, String mensaje)
/* 16:   */   {
/* 17:30 */     super(codigo, mensaje);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ExcepcionAS2Financiero(String codigo)
/* 21:   */   {
/* 22:34 */     super(codigo);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ExcepcionAS2Financiero(String codigo, Exception e)
/* 26:   */   {
/* 27:38 */     super(codigo, e);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero
 * JD-Core Version:    0.7.0.1
 */