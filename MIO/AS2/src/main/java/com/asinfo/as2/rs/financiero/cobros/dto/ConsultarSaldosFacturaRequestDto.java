/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ConsultarSaldosFacturaRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idEmpresaFinal;
/*  9:   */   private String fechaHasta;
/* 10:   */   
/* 11:   */   public Integer getIdEmpresaFinal()
/* 12:   */   {
/* 13:13 */     return this.idEmpresaFinal;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setIdEmpresaFinal(Integer idEmpresaFinal)
/* 17:   */   {
/* 18:17 */     this.idEmpresaFinal = idEmpresaFinal;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getFechaHasta()
/* 22:   */   {
/* 23:21 */     return this.fechaHasta;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setFechaHasta(String fechaHasta)
/* 27:   */   {
/* 28:25 */     this.fechaHasta = fechaHasta;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.ConsultarSaldosFacturaRequestDto
 * JD-Core Version:    0.7.0.1
 */