/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoAnexoSRI
/*  4:   */ {
/*  5:21 */   ATS("ATS", "Anexo Transaccional Simplificado"),  REOC("REOC", "Registro de Retenciones Otros Conceptos");
/*  6:   */   
/*  7:   */   private String nombreAbreviado;
/*  8:   */   private String nombreAnexo;
/*  9:   */   
/* 10:   */   private TipoAnexoSRI(String nombreAbreviado, String nombreAnexo)
/* 11:   */   {
/* 12:28 */     this.nombreAbreviado = nombreAbreviado;
/* 13:29 */     this.nombreAnexo = nombreAnexo;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static String obtenerNombreAnexoPorNombreAbreviado(String nombreAbreviado)
/* 17:   */   {
/* 18:33 */     String respuesta = "";
/* 19:34 */     for (TipoAnexoSRI tipoAnexoSRI : values()) {
/* 20:35 */       if (tipoAnexoSRI.getNombreAbreviado().equals(nombreAbreviado))
/* 21:   */       {
/* 22:36 */         respuesta = tipoAnexoSRI.getNombreAnexo();
/* 23:37 */         break;
/* 24:   */       }
/* 25:   */     }
/* 26:40 */     return respuesta;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getNombreAbreviado()
/* 30:   */   {
/* 31:48 */     return this.nombreAbreviado;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setNombreAbreviado(String nombreAbreviado)
/* 35:   */   {
/* 36:56 */     this.nombreAbreviado = nombreAbreviado;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getNombreAnexo()
/* 40:   */   {
/* 41:64 */     return this.nombreAnexo;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setNombreAnexo(String nombreAnexo)
/* 45:   */   {
/* 46:72 */     this.nombreAnexo = nombreAnexo;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoAnexoSRI
 * JD-Core Version:    0.7.0.1
 */