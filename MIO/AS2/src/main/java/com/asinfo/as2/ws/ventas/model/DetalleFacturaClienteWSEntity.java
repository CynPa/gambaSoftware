/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ 
/*   6:    */ public class DetalleFacturaClienteWSEntity
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1L;
/*  10:    */   private String codigoProducto;
/*  11: 27 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  12: 29 */   private BigDecimal precio = BigDecimal.ZERO;
/*  13: 31 */   private BigDecimal descuento = BigDecimal.ZERO;
/*  14: 33 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/*  15:    */   private String descripcion;
/*  16:    */   private boolean indicadorImpuesto;
/*  17:    */   private String nandina;
/*  18:    */   
/*  19:    */   public String getCodigoProducto()
/*  20:    */   {
/*  21: 51 */     return this.codigoProducto;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setCodigoProducto(String codigoProducto)
/*  25:    */   {
/*  26: 61 */     this.codigoProducto = codigoProducto;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BigDecimal getPrecio()
/*  30:    */   {
/*  31: 70 */     return this.precio;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setPrecio(BigDecimal precio)
/*  35:    */   {
/*  36: 80 */     this.precio = precio;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public BigDecimal getDescuento()
/*  40:    */   {
/*  41: 89 */     return this.descuento;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setDescuento(BigDecimal descuento)
/*  45:    */   {
/*  46: 99 */     this.descuento = descuento;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public BigDecimal getPorcentajeDescuento()
/*  50:    */   {
/*  51:108 */     return this.porcentajeDescuento;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/*  55:    */   {
/*  56:118 */     this.porcentajeDescuento = porcentajeDescuento;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public BigDecimal getCantidad()
/*  60:    */   {
/*  61:127 */     return this.cantidad;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setCantidad(BigDecimal cantidad)
/*  65:    */   {
/*  66:137 */     this.cantidad = cantidad;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getDescripcion()
/*  70:    */   {
/*  71:141 */     return this.descripcion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDescripcion(String descripcion)
/*  75:    */   {
/*  76:145 */     this.descripcion = descripcion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isIndicadorImpuesto()
/*  80:    */   {
/*  81:149 */     return this.indicadorImpuesto;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIndicadorImpuesto(boolean indicadorImpuesto)
/*  85:    */   {
/*  86:153 */     this.indicadorImpuesto = indicadorImpuesto;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getNandina()
/*  90:    */   {
/*  91:160 */     return this.nandina;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setNandina(String nandina)
/*  95:    */   {
/*  96:167 */     this.nandina = nandina;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.DetalleFacturaClienteWSEntity
 * JD-Core Version:    0.7.0.1
 */