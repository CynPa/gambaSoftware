/*  1:   */ package com.asinfo.as2.rs.ventas.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class DetalleCalculoImpuestoResponseDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private BigDecimal iceLinea;
/* 12:   */   private BigDecimal valorImpuestosTributarios;
/* 13:   */   private BigDecimal valorDescuentoImpuestoLinea;
/* 14:   */   private boolean succsess;
/* 15:   */   private String error;
/* 16:   */   
/* 17:   */   public BigDecimal getIceLinea()
/* 18:   */   {
/* 19:23 */     return this.iceLinea;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setIceLinea(BigDecimal iceLinea)
/* 23:   */   {
/* 24:27 */     this.iceLinea = iceLinea;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public BigDecimal getValorImpuestosTributarios()
/* 28:   */   {
/* 29:31 */     return this.valorImpuestosTributarios;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setValorImpuestosTributarios(BigDecimal valorImpuestosTributarios)
/* 33:   */   {
/* 34:35 */     this.valorImpuestosTributarios = valorImpuestosTributarios;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public BigDecimal getValorDescuentoImpuestoLinea()
/* 38:   */   {
/* 39:39 */     return this.valorDescuentoImpuestoLinea;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setValorDescuentoImpuestoLinea(BigDecimal valorDescuentoImpuestoLinea)
/* 43:   */   {
/* 44:43 */     this.valorDescuentoImpuestoLinea = valorDescuentoImpuestoLinea;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean isSuccsess()
/* 48:   */   {
/* 49:47 */     return this.succsess;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setSuccsess(boolean succsess)
/* 53:   */   {
/* 54:51 */     this.succsess = succsess;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String getError()
/* 58:   */   {
/* 59:55 */     return this.error;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setError(String error)
/* 63:   */   {
/* 64:59 */     this.error = error;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.dto.DetalleCalculoImpuestoResponseDto
 * JD-Core Version:    0.7.0.1
 */