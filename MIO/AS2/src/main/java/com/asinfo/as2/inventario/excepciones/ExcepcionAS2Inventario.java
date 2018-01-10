/*  1:   */ package com.asinfo.as2.inventario.excepciones;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  4:   */ 
/*  5:   */ public class ExcepcionAS2Inventario
/*  6:   */   extends ExcepcionAS2
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 2411725930407692565L;
/*  9:   */   
/* 10:   */   public ExcepcionAS2Inventario(String codigo, String mensaje, Exception e)
/* 11:   */   {
/* 12:26 */     super(codigo, mensaje, e);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ExcepcionAS2Inventario(String codigo, String mensaje)
/* 16:   */   {
/* 17:30 */     super(codigo, mensaje);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ExcepcionAS2Inventario(String codigo)
/* 21:   */   {
/* 22:34 */     super(codigo);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ExcepcionAS2Inventario(String codigo, Exception e)
/* 26:   */   {
/* 27:38 */     super(codigo, e);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ExcepcionAS2Inventario(Exception e)
/* 31:   */   {
/* 32:42 */     super(e);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario
 * JD-Core Version:    0.7.0.1
 */