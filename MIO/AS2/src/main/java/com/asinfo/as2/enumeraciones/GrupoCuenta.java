/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum GrupoCuenta
/*  4:   */ {
/*  5:20 */   ACTIVO("Activo", 1),  PASIVO("Pasivo", -1),  CAPITAL("Capital", 1),  INGRESOS("Ingresos", -1),  COSTOS("Costos", 1),  GASTOS("Gastos", 1);
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private int operacion;
/*  9:   */   
/* 10:   */   private GrupoCuenta(String nombre, int operacion)
/* 11:   */   {
/* 12:31 */     this.nombre = nombre;
/* 13:32 */     this.operacion = operacion;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getNombre()
/* 17:   */   {
/* 18:41 */     return this.nombre;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getOperacion()
/* 22:   */   {
/* 23:45 */     return this.operacion;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.GrupoCuenta
 * JD-Core Version:    0.7.0.1
 */