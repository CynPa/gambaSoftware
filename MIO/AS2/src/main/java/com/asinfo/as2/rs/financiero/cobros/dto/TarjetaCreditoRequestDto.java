/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class TarjetaCreditoRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idTarjetaCredito;
/*  9:   */   private Integer hashCode;
/* 10:12 */   private Boolean revisado = Boolean.valueOf(false);
/* 11:   */   
/* 12:   */   public Integer getHashCode()
/* 13:   */   {
/* 14:15 */     return this.hashCode;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setHashCode(Integer hashCode)
/* 18:   */   {
/* 19:19 */     this.hashCode = hashCode;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Boolean getRevisado()
/* 23:   */   {
/* 24:23 */     return this.revisado;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setRevisado(Boolean revisado)
/* 28:   */   {
/* 29:27 */     this.revisado = revisado;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Integer getIdTarjetaCredito()
/* 33:   */   {
/* 34:31 */     return this.idTarjetaCredito;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setIdTarjetaCredito(Integer idTarjetaCredito)
/* 38:   */   {
/* 39:35 */     this.idTarjetaCredito = idTarjetaCredito;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.TarjetaCreditoRequestDto
 * JD-Core Version:    0.7.0.1
 */