/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.rs.inventario.dto.ProductoResponseDto;
/*  4:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import java.math.BigDecimal;
/*  7:   */ 
/*  8:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  9:   */ public class EstadisticaVentaClienteResponseDto
/* 10:   */   implements Serializable
/* 11:   */ {
/* 12:   */   private Integer cantidad;
/* 13:   */   private BigDecimal valor;
/* 14:   */   private BigDecimal creditoMaximo;
/* 15:   */   private BigDecimal creditoDisponible;
/* 16:   */   private String fechaUltimaFactura;
/* 17:   */   private BigDecimal valorUltimaFactura;
/* 18:   */   private ProductoResponseDto producto;
/* 19:   */   
/* 20:   */   public Integer getCantidad()
/* 21:   */   {
/* 22:28 */     return this.cantidad;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setCantidad(Integer cantidad)
/* 26:   */   {
/* 27:32 */     this.cantidad = cantidad;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public BigDecimal getValor()
/* 31:   */   {
/* 32:36 */     return this.valor;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setValor(BigDecimal valor)
/* 36:   */   {
/* 37:40 */     this.valor = valor;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public BigDecimal getCreditoDisponible()
/* 41:   */   {
/* 42:44 */     return this.creditoDisponible;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setCreditoDisponible(BigDecimal creditoDisponible)
/* 46:   */   {
/* 47:48 */     this.creditoDisponible = creditoDisponible;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public ProductoResponseDto getProducto()
/* 51:   */   {
/* 52:52 */     return this.producto;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setProducto(ProductoResponseDto producto)
/* 56:   */   {
/* 57:56 */     this.producto = producto;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String getFechaUltimaFactura()
/* 61:   */   {
/* 62:60 */     return this.fechaUltimaFactura;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setFechaUltimaFactura(String fechaUltimaFactura)
/* 66:   */   {
/* 67:64 */     this.fechaUltimaFactura = fechaUltimaFactura;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public BigDecimal getValorUltimaFactura()
/* 71:   */   {
/* 72:68 */     return this.valorUltimaFactura;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setValorUltimaFactura(BigDecimal valorUltimaFactura)
/* 76:   */   {
/* 77:72 */     this.valorUltimaFactura = valorUltimaFactura;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public BigDecimal getCreditoMaximo()
/* 81:   */   {
/* 82:76 */     return this.creditoMaximo;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setCreditoMaximo(BigDecimal creditoMaximo)
/* 86:   */   {
/* 87:80 */     this.creditoMaximo = creditoMaximo;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.EstadisticaVentaClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */