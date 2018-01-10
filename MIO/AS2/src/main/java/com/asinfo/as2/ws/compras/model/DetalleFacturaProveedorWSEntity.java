/*  1:   */ package com.asinfo.as2.ws.compras.model;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ 
/*  6:   */ public class DetalleFacturaProveedorWSEntity
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private Long idProducto;
/* 11:   */   private String codigoProducto;
/* 12:   */   private BigDecimal cantidad;
/* 13:   */   private BigDecimal precioUnitario;
/* 14:   */   private BigDecimal descuentoUnitario;
/* 15:   */   private boolean indicadorImpuestos;
/* 16:   */   
/* 17:   */   public Long getIdProducto()
/* 18:   */   {
/* 19:44 */     return this.idProducto;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setIdProducto(Long idProducto)
/* 23:   */   {
/* 24:48 */     this.idProducto = idProducto;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public BigDecimal getCantidad()
/* 28:   */   {
/* 29:52 */     return this.cantidad;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setCantidad(BigDecimal cantidad)
/* 33:   */   {
/* 34:56 */     this.cantidad = cantidad;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public BigDecimal getPrecioUnitario()
/* 38:   */   {
/* 39:60 */     return this.precioUnitario;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setPrecioUnitario(BigDecimal precioUnitario)
/* 43:   */   {
/* 44:64 */     this.precioUnitario = precioUnitario;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public BigDecimal getDescuentoUnitario()
/* 48:   */   {
/* 49:68 */     return this.descuentoUnitario;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setDescuentoUnitario(BigDecimal descuentoUnitario)
/* 53:   */   {
/* 54:72 */     this.descuentoUnitario = descuentoUnitario;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean isIndicadorImpuestos()
/* 58:   */   {
/* 59:76 */     return this.indicadorImpuestos;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setIndicadorImpuestos(boolean indicadorImpuestos)
/* 63:   */   {
/* 64:80 */     this.indicadorImpuestos = indicadorImpuestos;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String getCodigoProducto()
/* 68:   */   {
/* 69:84 */     return this.codigoProducto;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setCodigoProducto(String codigoProducto)
/* 73:   */   {
/* 74:88 */     this.codigoProducto = codigoProducto;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.compras.model.DetalleFacturaProveedorWSEntity
 * JD-Core Version:    0.7.0.1
 */