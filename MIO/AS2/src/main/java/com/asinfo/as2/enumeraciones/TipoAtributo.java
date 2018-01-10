/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoAtributo
/*  4:   */ {
/*  5:20 */   TEXTO("Texto"),  NUMERO("Numero"),  LISTA("Lista");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoAtributo(String nombre)
/* 10:   */   {
/* 11:27 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:35 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoAtributo
 * JD-Core Version:    0.7.0.1
 */