/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class FacturaClienteWSEntityString
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 1L;
/*   9:    */   private String establecimiento;
/*  10:    */   private String puntoDeVenta;
/*  11:    */   private String numero;
/*  12:    */   private String fechaFactura;
/*  13:    */   private String fechaVencimiento;
/*  14:    */   private String identificacionFactura;
/*  15:    */   private String direccionFactura;
/*  16:    */   private String totalImpuesto;
/*  17:    */   private String totalImporte;
/*  18:    */   
/*  19:    */   public String getEstablecimiento()
/*  20:    */   {
/*  21: 46 */     return this.establecimiento;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setEstablecimiento(String establecimiento)
/*  25:    */   {
/*  26: 50 */     this.establecimiento = establecimiento;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getPuntoDeVenta()
/*  30:    */   {
/*  31: 54 */     return this.puntoDeVenta;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setPuntoDeVenta(String puntoDeVenta)
/*  35:    */   {
/*  36: 58 */     this.puntoDeVenta = puntoDeVenta;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getNumero()
/*  40:    */   {
/*  41: 62 */     return this.numero;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setNumero(String numero)
/*  45:    */   {
/*  46: 66 */     this.numero = numero;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getFechaFactura()
/*  50:    */   {
/*  51: 70 */     return this.fechaFactura;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setFechaFactura(String fechaFactura)
/*  55:    */   {
/*  56: 74 */     this.fechaFactura = fechaFactura;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getFechaVencimiento()
/*  60:    */   {
/*  61: 78 */     return this.fechaVencimiento;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setFechaVencimiento(String fechaVencimiento)
/*  65:    */   {
/*  66: 82 */     this.fechaVencimiento = fechaVencimiento;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getDireccionFactura()
/*  70:    */   {
/*  71: 86 */     return this.direccionFactura;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDireccionFactura(String direccionFactura)
/*  75:    */   {
/*  76: 90 */     this.direccionFactura = direccionFactura;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getTotalImpuesto()
/*  80:    */   {
/*  81: 94 */     return this.totalImpuesto;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setTotalImpuesto(String totalImpuesto)
/*  85:    */   {
/*  86: 98 */     this.totalImpuesto = totalImpuesto;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getTotalImporte()
/*  90:    */   {
/*  91:102 */     return this.totalImporte;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setTotalImporte(String totalImporte)
/*  95:    */   {
/*  96:106 */     this.totalImporte = totalImporte;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getIdentificacionFactura()
/* 100:    */   {
/* 101:110 */     return this.identificacionFactura;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdentificacionFactura(String identificacionFactura)
/* 105:    */   {
/* 106:114 */     this.identificacionFactura = identificacionFactura;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.FacturaClienteWSEntityString
 * JD-Core Version:    0.7.0.1
 */