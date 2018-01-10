/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class PedidoSugeridoResponseDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private Integer idProducto;
/* 12:   */   private BigDecimal cantidad;
/* 13:   */   
/* 14:   */   public Integer getIdProducto()
/* 15:   */   {
/* 16:17 */     return this.idProducto;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setIdProducto(Integer idProducto)
/* 20:   */   {
/* 21:21 */     this.idProducto = idProducto;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public BigDecimal getCantidad()
/* 25:   */   {
/* 26:25 */     return this.cantidad;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setCantidad(BigDecimal cantidad)
/* 30:   */   {
/* 31:29 */     this.cantidad = cantidad;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.PedidoSugeridoResponseDto
 * JD-Core Version:    0.7.0.1
 */