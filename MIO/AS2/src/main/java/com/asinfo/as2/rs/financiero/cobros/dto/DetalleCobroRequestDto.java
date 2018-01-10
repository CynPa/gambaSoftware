/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ 
/*  6:   */ public class DetalleCobroRequestDto
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private Integer idCuentaPorCobrar;
/* 11:   */   private Integer idDetalleCobro;
/* 12:17 */   private BigDecimal valorCobro = BigDecimal.ZERO;
/* 13:   */   
/* 14:   */   public Integer getIdCuentaPorCobrar()
/* 15:   */   {
/* 16:20 */     return this.idCuentaPorCobrar;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setIdCuentaPorCobrar(Integer idCuentaPorCobrar)
/* 20:   */   {
/* 21:24 */     this.idCuentaPorCobrar = idCuentaPorCobrar;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public BigDecimal getValorCobro()
/* 25:   */   {
/* 26:28 */     return this.valorCobro;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setValorCobro(BigDecimal valorCobro)
/* 30:   */   {
/* 31:32 */     this.valorCobro = valorCobro;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Integer getIdDetalleCobro()
/* 35:   */   {
/* 36:36 */     return this.idDetalleCobro;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setIdDetalleCobro(Integer idDetalleCobro)
/* 40:   */   {
/* 41:40 */     this.idDetalleCobro = idDetalleCobro;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.DetalleCobroRequestDto
 * JD-Core Version:    0.7.0.1
 */