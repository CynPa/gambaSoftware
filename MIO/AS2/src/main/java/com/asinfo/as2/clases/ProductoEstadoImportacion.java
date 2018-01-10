/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ 
/*   6:    */ public class ProductoEstadoImportacion
/*   7:    */ {
/*   8:    */   private String procesoImportacion;
/*   9:    */   private String estadoImportacion;
/*  10:    */   private BigDecimal cantidad;
/*  11:    */   private Date fecha;
/*  12:    */   private String transporte;
/*  13:    */   
/*  14:    */   public String getProcesoImportacion()
/*  15:    */   {
/*  16: 44 */     return this.procesoImportacion;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void setProcesoImportacion(String procesoImportacion)
/*  20:    */   {
/*  21: 54 */     this.procesoImportacion = procesoImportacion;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getEstadoImportacion()
/*  25:    */   {
/*  26: 63 */     return this.estadoImportacion;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setEstadoImportacion(String estadoImportacion)
/*  30:    */   {
/*  31: 73 */     this.estadoImportacion = estadoImportacion;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public BigDecimal getCantidad()
/*  35:    */   {
/*  36: 82 */     return this.cantidad;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setCantidad(BigDecimal cantidad)
/*  40:    */   {
/*  41: 92 */     this.cantidad = cantidad;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Date getFecha()
/*  45:    */   {
/*  46:101 */     return this.fecha;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setFecha(Date fecha)
/*  50:    */   {
/*  51:111 */     this.fecha = fecha;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getTransporte()
/*  55:    */   {
/*  56:120 */     return this.transporte;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setTransporte(String transporte)
/*  60:    */   {
/*  61:130 */     this.transporte = transporte;
/*  62:    */   }
/*  63:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ProductoEstadoImportacion
 * JD-Core Version:    0.7.0.1
 */