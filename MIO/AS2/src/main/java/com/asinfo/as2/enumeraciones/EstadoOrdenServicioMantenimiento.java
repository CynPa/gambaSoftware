/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum EstadoOrdenServicioMantenimiento
/*  4:   */ {
/*  5:20 */   ANULADO("Anulado"),  ELABORADA("Elaborada"),  FINALIZADA("Finalizada"),  CERRADA("Cerrada"),  CONTABILIZADO("Contabilizada");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private EstadoOrdenServicioMantenimiento(String nombre)
/* 10:   */   {
/* 11:29 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:38 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.EstadoOrdenServicioMantenimiento
 * JD-Core Version:    0.7.0.1
 */