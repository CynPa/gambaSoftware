/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class DetalleDevolucionRequestDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer producto;
/*  12:    */   private BigDecimal cantidad;
/*  13:    */   private BigDecimal precio;
/*  14:    */   private Integer idBodega;
/*  15:    */   private Integer idDispositivoSincronizacion;
/*  16:    */   private Integer idAs2;
/*  17:    */   private Integer idDetalleFacturaClientePadre;
/*  18:    */   
/*  19:    */   public Integer getProducto()
/*  20:    */   {
/*  21: 30 */     return this.producto;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setProducto(Integer producto)
/*  25:    */   {
/*  26: 38 */     this.producto = producto;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BigDecimal getCantidad()
/*  30:    */   {
/*  31: 45 */     return this.cantidad;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setCantidad(BigDecimal cantidad)
/*  35:    */   {
/*  36: 53 */     this.cantidad = cantidad;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public BigDecimal getPrecio()
/*  40:    */   {
/*  41: 60 */     return this.precio;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setPrecio(BigDecimal precio)
/*  45:    */   {
/*  46: 68 */     this.precio = precio;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Integer getIdDispositivoSincronizacion()
/*  50:    */   {
/*  51: 72 */     return this.idDispositivoSincronizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/*  55:    */   {
/*  56: 76 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Integer getIdAs2()
/*  60:    */   {
/*  61: 80 */     return this.idAs2;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdAs2(Integer idAs2)
/*  65:    */   {
/*  66: 84 */     this.idAs2 = idAs2;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Integer getIdBodega()
/*  70:    */   {
/*  71: 88 */     return this.idBodega;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdBodega(Integer idBodega)
/*  75:    */   {
/*  76: 92 */     this.idBodega = idBodega;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Integer getIdDetalleFacturaClientePadre()
/*  80:    */   {
/*  81: 96 */     return this.idDetalleFacturaClientePadre;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdDetalleFacturaClientePadre(Integer idDetalleFacturaClientePadre)
/*  85:    */   {
/*  86:100 */     this.idDetalleFacturaClientePadre = idDetalleFacturaClientePadre;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.DetalleDevolucionRequestDto
 * JD-Core Version:    0.7.0.1
 */