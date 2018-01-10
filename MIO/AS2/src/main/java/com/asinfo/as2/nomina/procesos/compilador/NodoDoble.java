/*  1:   */ package com.asinfo.as2.nomina.procesos.compilador;
/*  2:   */ 
/*  3:   */ public class NodoDoble
/*  4:   */ {
/*  5:   */   int iACUM;
/*  6:   */   char cLetra;
/*  7:   */   NodoDoble FlechaI;
/*  8:   */   NodoDoble FlechaD;
/*  9:   */   
/* 10:   */   NodoDoble(char cLetra)
/* 11:   */   {
/* 12:35 */     this.cLetra = cLetra;
/* 13:36 */     this.FlechaI = null;
/* 14:37 */     this.FlechaD = null;
/* 15:38 */     this.iACUM = -1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   NodoDoble(char cLetra, NodoDoble objNodo)
/* 19:   */   {
/* 20:42 */     this.cLetra = cLetra;
/* 21:43 */     this.FlechaI = objNodo;
/* 22:44 */     this.FlechaD = null;
/* 23:45 */     this.iACUM = -1;
/* 24:46 */     objNodo.FlechaD = this;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.compilador.NodoDoble
 * JD-Core Version:    0.7.0.1
 */