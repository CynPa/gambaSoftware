/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum Mes
/*  4:   */ {
/*  5:22 */   ENERO("Enero", 1),  FEBRERO("Febrero", 2),  MARZO("Marzo", 3),  ABRIL("Abril", 4),  MAYO("Mayo", 5),  JUNIO("Junio", 6),  JULIO("Julio", 7),  AGOSTO("Agosto", 8),  SEPTIEMBRE("Septiembre", 9),  OCTUBRE("Octubre", 10),  NOVIEMBRE("Noviembre", 11),  DICIEMBRE("Diciembre", 12);
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   private int numero;
/*  9:   */   
/* 10:   */   private Mes(String nombre, int numero)
/* 11:   */   {
/* 12:42 */     this.nombre = nombre;
/* 13:43 */     this.numero = numero;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getNombre()
/* 17:   */   {
/* 18:52 */     return this.nombre;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setNombre(String nombre)
/* 22:   */   {
/* 23:62 */     this.nombre = nombre;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getNumero()
/* 27:   */   {
/* 28:66 */     return this.numero;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setNumero(int numero)
/* 32:   */   {
/* 33:70 */     this.numero = numero;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.Mes
 * JD-Core Version:    0.7.0.1
 */