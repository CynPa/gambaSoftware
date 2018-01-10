/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ReporteEnvioMailsEnum
/*  4:   */ {
/*  5:19 */   INDICADOR_VENCIMIENTO_FACTURA_CLIENTE("indicadorNotificarVencimientoFacturaCliente");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private ReporteEnvioMailsEnum(String nombre)
/* 10:   */   {
/* 11:27 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:36 */     return this.nombre;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setNombre(String nombre)
/* 20:   */   {
/* 21:46 */     this.nombre = nombre;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ReporteEnvioMailsEnum
 * JD-Core Version:    0.7.0.1
 */