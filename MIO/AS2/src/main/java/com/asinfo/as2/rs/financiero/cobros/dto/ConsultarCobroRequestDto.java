/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ConsultarCobroRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private String codigoMovil;
/*  9:   */   private Integer idCobro;
/* 10:   */   
/* 11:   */   public String getCodigoMovil()
/* 12:   */   {
/* 13:13 */     return this.codigoMovil;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setCodigoMovil(String codigoMovil)
/* 17:   */   {
/* 18:17 */     this.codigoMovil = codigoMovil;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Integer getIdCobro()
/* 22:   */   {
/* 23:21 */     return this.idCobro;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setIdCobro(Integer idCobro)
/* 27:   */   {
/* 28:25 */     this.idCobro = idCobro;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.ConsultarCobroRequestDto
 * JD-Core Version:    0.7.0.1
 */