/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class FiltroRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private String campo;
/*  9:   */   private String valor;
/* 10:   */   
/* 11:   */   public String getCampo()
/* 12:   */   {
/* 13:16 */     return this.campo;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setCampo(String campo)
/* 17:   */   {
/* 18:24 */     this.campo = campo;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getValor()
/* 22:   */   {
/* 23:31 */     return this.valor;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setValor(String valor)
/* 27:   */   {
/* 28:39 */     this.valor = valor;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.FiltroRequestDto
 * JD-Core Version:    0.7.0.1
 */