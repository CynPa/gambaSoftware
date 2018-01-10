/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class EstadoPedidoResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idPedido;
/* 11:   */   private String numero;
/* 12:   */   private String estado;
/* 13:   */   
/* 14:   */   public Integer getIdPedido()
/* 15:   */   {
/* 16:18 */     return this.idPedido;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setIdPedido(Integer idPedido)
/* 20:   */   {
/* 21:22 */     this.idPedido = idPedido;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getNumero()
/* 25:   */   {
/* 26:26 */     return this.numero;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setNumero(String numero)
/* 30:   */   {
/* 31:30 */     this.numero = numero;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getEstado()
/* 35:   */   {
/* 36:34 */     return this.estado;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setEstado(String estado)
/* 40:   */   {
/* 41:38 */     this.estado = estado;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.EstadoPedidoResponseDto
 * JD-Core Version:    0.7.0.1
 */