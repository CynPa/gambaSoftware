/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class MotivoNotaCreditoClienteRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idMotivoNotaCreditoCliente;
/*  9:   */   private Integer hashCode;
/* 10:12 */   private Boolean revisado = Boolean.valueOf(false);
/* 11:   */   
/* 12:   */   public Integer getIdMotivoNotaCreditoCliente()
/* 13:   */   {
/* 14:15 */     return this.idMotivoNotaCreditoCliente;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setIdMotivoNotaCreditoCliente(Integer idMotivoNotaCreditoCliente)
/* 18:   */   {
/* 19:19 */     this.idMotivoNotaCreditoCliente = idMotivoNotaCreditoCliente;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Integer getHashCode()
/* 23:   */   {
/* 24:23 */     return this.hashCode;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setHashCode(Integer hashCode)
/* 28:   */   {
/* 29:27 */     this.hashCode = hashCode;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Boolean getRevisado()
/* 33:   */   {
/* 34:31 */     return this.revisado;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setRevisado(Boolean revisado)
/* 38:   */   {
/* 39:35 */     this.revisado = revisado;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.MotivoNotaCreditoClienteRequestDto
 * JD-Core Version:    0.7.0.1
 */