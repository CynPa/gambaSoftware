/*  1:   */ package com.asinfo.as2.excepciones;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.io.StringWriter;
/*  5:   */ 
/*  6:   */ public class ExcepcionAS2
/*  7:   */   extends Exception
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 2202054387022953845L;
/* 10:26 */   protected String codigoExcepcion = "0";
/* 11:   */   
/* 12:   */   public ExcepcionAS2(String codigo, String mensaje, Exception e)
/* 13:   */   {
/* 14:29 */     super(mensaje, e);
/* 15:30 */     this.codigoExcepcion = codigo;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ExcepcionAS2(String codigo, Exception e)
/* 19:   */   {
/* 20:34 */     super(e);
/* 21:35 */     this.codigoExcepcion = codigo;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ExcepcionAS2(Exception e)
/* 25:   */   {
/* 26:39 */     super(e);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ExcepcionAS2(String codigo, String mensaje)
/* 30:   */   {
/* 31:43 */     super(mensaje);
/* 32:44 */     this.codigoExcepcion = codigo;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public ExcepcionAS2(String codigo)
/* 36:   */   {
/* 37:48 */     super("");
/* 38:49 */     this.codigoExcepcion = codigo;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getCodigoExcepcion()
/* 42:   */   {
/* 43:53 */     return this.codigoExcepcion;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getErrorMessage(Exception e)
/* 47:   */   {
/* 48:64 */     String errorMessage = "";
/* 49:65 */     StringWriter sw = new StringWriter();
/* 50:66 */     e.printStackTrace(new PrintWriter(sw));
/* 51:67 */     errorMessage = sw.toString();
/* 52:68 */     return errorMessage;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getMessage()
/* 56:   */   {
/* 57:76 */     return super.getMessage() == null ? "" : super.getMessage();
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.excepciones.ExcepcionAS2
 * JD-Core Version:    0.7.0.1
 */