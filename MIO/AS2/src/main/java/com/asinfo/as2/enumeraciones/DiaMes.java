/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum DiaMes
/*  4:   */ {
/*  5:21 */   CERO("Fin de Mes"),  UNO("1"),  DOS("2"),  TRES("3"),  CUATRO("4"),  CINCO("5"),  SEIS("6"),  SIETE("7"),  OCHO("8"),  NUEVE("9"),  DIEZ("10"),  ONCE("11"),  DOCE("12"),  TRECE("13"),  CARTORCE("14"),  QUINCE("15"),  DIECISEIS("16"),  DIECISIETE("17"),  DIECIOCHO("18"),  DIECINUEVE("19"),  VEINTE("20"),  VEINTEIUNO("21"),  VEINTEIDOS("22"),  VEINTEITRES("23"),  VEINTEICUATRO("24"),  VEINTEICINCO("25"),  VEINTEISEIS("26"),  VEINTEISIETE("27"),  VEINTEIOCHO("28"),  VEINTEINUEVE("29"),  TREINTA("30"),  TREINTAIUNO("31");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private DiaMes(String nombre)
/* 10:   */   {
/* 11:57 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:66 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.DiaMes
 * JD-Core Version:    0.7.0.1
 */