/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class EstadoCobroResponseDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idCobro;
/*  9:   */   private String numero;
/* 10:   */   private String estado;
/* 11:   */   
/* 12:   */   public Integer getIdCobro()
/* 13:   */   {
/* 14:15 */     return this.idCobro;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setIdCobro(Integer idCobro)
/* 18:   */   {
/* 19:19 */     this.idCobro = idCobro;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getNumero()
/* 23:   */   {
/* 24:23 */     return this.numero;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setNumero(String numero)
/* 28:   */   {
/* 29:27 */     this.numero = numero;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getEstado()
/* 33:   */   {
/* 34:31 */     return this.estado;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setEstado(String estado)
/* 38:   */   {
/* 39:35 */     this.estado = estado;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.EstadoCobroResponseDto
 * JD-Core Version:    0.7.0.1
 */