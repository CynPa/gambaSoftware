/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ConsultarPedidoRequestDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private String codigoMovil;
/* 11:   */   private Integer idPedidoCliente;
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
/* 23:   */   public Integer getIdPedidoCliente()
/* 24:   */   {
/* 25:24 */     return this.idPedidoCliente;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdPedidoCliente(Integer idPedidoCliente)
/* 29:   */   {
/* 30:28 */     this.idPedidoCliente = idPedidoCliente;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ConsultarPedidoRequestDto
 * JD-Core Version:    0.7.0.1
 */