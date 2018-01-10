/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum TipoListaPreciosEnum
/*  4:   */ {
/*  5:15 */   LISTA_PRECIOS("ListaPrecios"),  RECARGOS("Recargos");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private TipoListaPreciosEnum(String nombre)
/* 10:   */   {
/* 11:20 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:29 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.TipoListaPreciosEnum
 * JD-Core Version:    0.7.0.1
 */