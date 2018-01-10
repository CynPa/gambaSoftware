/*   1:    */ package com.asinfo.as2.nomina.procesos.compilador;
/*   2:    */ 
/*   3:    */ public class NodoSimple
/*   4:    */ {
/*   5:    */   int iFuncion;
/*   6:    */   char cVariable;
/*   7:    */   double fNumero;
/*   8:    */   char cOperador;
/*   9:    */   NodoSimple Abajo;
/*  10:    */   NodoSimple Derecha;
/*  11:    */   NodoSimple Potencia;
/*  12:    */   NodoSimple Arriba;
/*  13:    */   NodoSimple objACUM;
/*  14:    */   
/*  15:    */   NodoSimple(double fNumero, char cOperador)
/*  16:    */   {
/*  17: 54 */     this.cVariable = '\000';
/*  18: 55 */     this.fNumero = fNumero;
/*  19: 56 */     this.cOperador = cOperador;
/*  20: 57 */     this.Abajo = null;
/*  21: 58 */     this.Derecha = null;
/*  22: 59 */     this.Potencia = null;
/*  23: 60 */     this.objACUM = null;
/*  24: 61 */     this.iFuncion = 0;
/*  25:    */   }
/*  26:    */   
/*  27:    */   NodoSimple(char cVariable, char cOperador)
/*  28:    */   {
/*  29: 66 */     this.cVariable = cVariable;
/*  30: 67 */     this.fNumero = 0.0D;
/*  31: 68 */     this.cOperador = cOperador;
/*  32: 69 */     this.Abajo = null;
/*  33: 70 */     this.Derecha = null;
/*  34: 71 */     this.Potencia = null;
/*  35: 72 */     this.objACUM = null;
/*  36: 73 */     this.iFuncion = 0;
/*  37:    */   }
/*  38:    */   
/*  39:    */   NodoSimple()
/*  40:    */   {
/*  41: 78 */     this.cVariable = '\000';
/*  42: 79 */     this.fNumero = 0.0D;
/*  43: 80 */     this.cOperador = '\000';
/*  44: 81 */     this.Abajo = null;
/*  45: 82 */     this.Derecha = null;
/*  46: 83 */     this.Potencia = null;
/*  47: 84 */     this.objACUM = null;
/*  48: 85 */     this.iFuncion = 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   NodoSimple(NodoSimple objACUM)
/*  52:    */   {
/*  53: 90 */     this.cVariable = '\000';
/*  54: 91 */     this.fNumero = 0.0D;
/*  55: 92 */     this.cOperador = '\000';
/*  56: 93 */     this.Abajo = null;
/*  57: 94 */     this.Derecha = null;
/*  58: 95 */     this.Potencia = null;
/*  59: 96 */     this.objACUM = null;
/*  60: 97 */     this.Arriba = objACUM;
/*  61: 98 */     objACUM.Abajo = this;
/*  62: 99 */     this.iFuncion = 0;
/*  63:    */   }
/*  64:    */   
/*  65:    */   NodoSimple(NodoSimple objACUM, char cOperador)
/*  66:    */   {
/*  67:104 */     this.cVariable = '\000';
/*  68:105 */     this.fNumero = 0.0D;
/*  69:106 */     this.cOperador = cOperador;
/*  70:107 */     this.Abajo = null;
/*  71:108 */     this.Derecha = null;
/*  72:109 */     this.Potencia = null;
/*  73:110 */     this.objACUM = objACUM;
/*  74:111 */     this.iFuncion = 0;
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.compilador.NodoSimple
 * JD-Core Version:    0.7.0.1
 */