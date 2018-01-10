/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum ParteFecha
/*  4:   */ {
/*  5:21 */   ANIO("Añio"),  MES("Mes"),  DIA("Dia"),  HORA("Hora"),  MINUTO("Minuto"),  SEGUNDO("Segundo");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private ParteFecha(String nombre)
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
 * Qualified Name:     com.asinfo.as2.enumeraciones.ParteFecha
 * JD-Core Version:    0.7.0.1
 */