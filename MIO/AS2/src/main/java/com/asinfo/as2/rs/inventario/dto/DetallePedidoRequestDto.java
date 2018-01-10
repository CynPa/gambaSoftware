/*  1:   */ package com.asinfo.as2.rs.inventario.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.math.BigDecimal;
/*  6:   */ 
/*  7:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  8:   */ public class DetallePedidoRequestDto
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private Integer producto;
/* 12:   */   private BigDecimal cantidad;
/* 13:   */   private BigDecimal precio;
/* 14:   */   private BigDecimal descuento;
/* 15:   */   
/* 16:   */   public Integer getProducto()
/* 17:   */   {
/* 18:24 */     return this.producto;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setProducto(Integer producto)
/* 22:   */   {
/* 23:32 */     this.producto = producto;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BigDecimal getCantidad()
/* 27:   */   {
/* 28:39 */     return this.cantidad;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setCantidad(BigDecimal cantidad)
/* 32:   */   {
/* 33:47 */     this.cantidad = cantidad;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public BigDecimal getPrecio()
/* 37:   */   {
/* 38:54 */     return this.precio;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setPrecio(BigDecimal precio)
/* 42:   */   {
/* 43:62 */     this.precio = precio;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public BigDecimal getDescuento()
/* 47:   */   {
/* 48:69 */     return this.descuento;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setDescuento(BigDecimal descuento)
/* 52:   */   {
/* 53:77 */     this.descuento = descuento;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.DetallePedidoRequestDto
 * JD-Core Version:    0.7.0.1
 */