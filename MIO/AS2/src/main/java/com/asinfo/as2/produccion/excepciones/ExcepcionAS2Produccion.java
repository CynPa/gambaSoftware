/*  1:   */ package com.asinfo.as2.produccion.excepciones;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  4:   */ 
/*  5:   */ public class ExcepcionAS2Produccion
/*  6:   */   extends ExcepcionAS2
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 5217457863537973534L;
/*  9:   */   
/* 10:   */   public ExcepcionAS2Produccion(String codigo, String mensaje, Exception e)
/* 11:   */   {
/* 12:26 */     super(codigo, mensaje, e);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ExcepcionAS2Produccion(String codigo, String mensaje)
/* 16:   */   {
/* 17:30 */     super(codigo, mensaje);
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.excepciones.ExcepcionAS2Produccion
 * JD-Core Version:    0.7.0.1
 */