/*  1:   */ package com.asinfo.as2.nomina.excepciones;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  4:   */ 
/*  5:   */ public class ExcepcionAS2Nomina
/*  6:   */   extends ExcepcionAS2
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -4746416066754841739L;
/*  9:   */   
/* 10:   */   public ExcepcionAS2Nomina(String codigo, String mensaje, Exception e)
/* 11:   */   {
/* 12:27 */     super(codigo, mensaje, e);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ExcepcionAS2Nomina(Exception e)
/* 16:   */   {
/* 17:31 */     super(e);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ExcepcionAS2Nomina(String codigo, String mensaje)
/* 21:   */   {
/* 22:35 */     super(codigo, mensaje);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ExcepcionAS2Nomina(String codigo)
/* 26:   */   {
/* 27:39 */     super(codigo);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ExcepcionAS2Nomina(String codigo, Exception e)
/* 31:   */   {
/* 32:43 */     super(codigo, e);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina
 * JD-Core Version:    0.7.0.1
 */