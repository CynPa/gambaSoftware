/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ProcesoPeriodoEnum
/*  4:   */ {
/*  5:15 */   COBROS("Cobros", "indicadorCierreCobros"),  PAGOS("Pagos", "indicadorCierrePagos"),  INVENTARIO("Inventario", "indicadorCierreInventario"),  CONTABILIDAD("Contabilidad", "indicadorCierreContabilidad"),  PRESUPUESTO("Presupuesto", "indicadorCierrePresupuesto"),  PRODUCCION("Produccion", "indicadorCierreProduccion"),  ACTIVOS_FIJOS("Activos Fijos", "indicadorCierreActivosFijos"),  COMPRAS("Compras", "indicadorCierreCompras"),  VENTAS("Ventas", "indicadorCierreVentas"),  NOMINA("Nomina", "indicadorCierreNomina");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private String nombreIndicador;
/*  9:   */   
/* 10:   */   private ProcesoPeriodoEnum(String nombre, String nombreIndicador)
/* 11:   */   {
/* 12:30 */     this.nombre = nombre;
/* 13:31 */     this.nombreIndicador = nombreIndicador;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getNombre()
/* 17:   */   {
/* 18:40 */     return this.nombre;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getNombreIndicador()
/* 22:   */   {
/* 23:44 */     return this.nombreIndicador;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.ProcesoPeriodoEnum
 * JD-Core Version:    0.7.0.1
 */