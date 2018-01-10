/*  1:   */ package com.asinfo.as2.ws.ventas.model;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ 
/*  6:   */ public class DetalleCobroWSEntity
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private String establecimientoFactura;
/* 11:   */   private String puntoVentaFactura;
/* 12:   */   private String numeroFactura;
/* 13:31 */   private BigDecimal valorCobro = BigDecimal.ZERO.setScale(2);
/* 14:   */   
/* 15:   */   public String getEstablecimientoFactura()
/* 16:   */   {
/* 17:38 */     return this.establecimientoFactura;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setEstablecimientoFactura(String establecimientoFactura)
/* 21:   */   {
/* 22:42 */     this.establecimientoFactura = establecimientoFactura;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getPuntoVentaFactura()
/* 26:   */   {
/* 27:46 */     return this.puntoVentaFactura;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setPuntoVentaFactura(String puntoVentaFactura)
/* 31:   */   {
/* 32:50 */     this.puntoVentaFactura = puntoVentaFactura;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getNumeroFactura()
/* 36:   */   {
/* 37:54 */     return this.numeroFactura;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setNumeroFactura(String numeroFactura)
/* 41:   */   {
/* 42:58 */     this.numeroFactura = numeroFactura;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public BigDecimal getValorCobro()
/* 46:   */   {
/* 47:62 */     return this.valorCobro;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setValorCobro(BigDecimal valorCobro)
/* 51:   */   {
/* 52:66 */     this.valorCobro = valorCobro;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.DetalleCobroWSEntity
 * JD-Core Version:    0.7.0.1
 */