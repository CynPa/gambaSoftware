/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum Accion
/*  4:   */ {
/*  5:21 */   READ("Leer"),  CREATE("Crear"),  UPDATE("Editar"),  DELETE("Eliminar/Anular"),  PRINT("Imprimir"),  ALL("Todo"),  NONE("Ninguno");
/*  6:   */   
/*  7:   */   private String nombre;
/*  8:   */   
/*  9:   */   private Accion(String nombre)
/* 10:   */   {
/* 11:33 */     this.nombre = nombre;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getNombre()
/* 15:   */   {
/* 16:41 */     return this.nombre;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.Accion
 * JD-Core Version:    0.7.0.1
 */