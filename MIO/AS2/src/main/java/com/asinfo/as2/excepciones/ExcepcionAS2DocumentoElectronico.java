/*  1:   */ package com.asinfo.as2.excepciones;
/*  2:   */ 
/*  3:   */ public class ExcepcionAS2DocumentoElectronico
/*  4:   */   extends ExcepcionAS2
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1L;
/*  7:   */   
/*  8:   */   public ExcepcionAS2DocumentoElectronico(Exception e)
/*  9:   */   {
/* 10:30 */     super(e);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ExcepcionAS2DocumentoElectronico(String codigo)
/* 14:   */   {
/* 15:34 */     super(codigo);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ExcepcionAS2DocumentoElectronico(String codigo, String mensaje)
/* 19:   */   {
/* 20:38 */     super(codigo, mensaje);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico
 * JD-Core Version:    0.7.0.1
 */