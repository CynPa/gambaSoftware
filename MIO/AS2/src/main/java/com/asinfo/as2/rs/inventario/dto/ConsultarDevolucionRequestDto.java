/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ConsultarDevolucionRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String codigoMovil;
/* 11:   */   private Integer idDevolucionCliente;
/* 12:   */   
/* 13:   */   public String getCodigoMovil()
/* 14:   */   {
/* 15:16 */     return this.codigoMovil;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setCodigoMovil(String codigoMovil)
/* 19:   */   {
/* 20:20 */     this.codigoMovil = codigoMovil;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Integer getIdDevolucionCliente()
/* 24:   */   {
/* 25:24 */     return this.idDevolucionCliente;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdDevolucionCliente(Integer idDevolucionCliente)
/* 29:   */   {
/* 30:28 */     this.idDevolucionCliente = idDevolucionCliente;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ConsultarDevolucionRequestDto
 * JD-Core Version:    0.7.0.1
 */