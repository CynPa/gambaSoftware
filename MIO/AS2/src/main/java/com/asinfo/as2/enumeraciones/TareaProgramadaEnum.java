/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TareaProgramadaEnum
/*  4:   */ {
/*  5:20 */   PROCESAR_ASISTENCIA("Procesar Asistencia"),  CONTABILIZAR_AMORTIZACION("Contabilizar Amortizacion"),  SINCRONIZARCOMPROBANTESELECTRONICOS_VENTAS("Sincronizar Ventas"),  SINCRONIZARCOMPROBANTESELECTRONICOS_RETENCIONPROVEEDOR("Sincronizar Retencion Proveedor"),  SINCRONIZARCOMPROBANTESELECTRONICOS_GUIAREMISION("Sincronizar Guia Remision"),  SINCRONIZARCOMPROBANTESELECTRONICOS_HACIAFACTURAE("Sincronizar hacia FacturaE"),  SINCRONIZAR_MOVIMIENTOS_PRODUCCIOM("Sincronizar movimientos produccion hacia AS2");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TareaProgramadaEnum(String nombre)
/* 10:   */   {
/* 11:31 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:40 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TareaProgramadaEnum
 * JD-Core Version:    0.7.0.1
 */