/*  1:   */ package com.asinfo.as2.ws.ventas.model;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ 
/*  6:   */ public class DetalleFormaCobroWSEntity
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private String numeroCuentaBancariaPago;
/* 11:   */   private String formaPago;
/* 12:   */   private String documentoReferencia;
/* 13:   */   private String bancoOrigen;
/* 14:   */   private BigDecimal valor;
/* 15:   */   
/* 16:   */   public String getNumeroCuentaBancariaPago()
/* 17:   */   {
/* 18:40 */     return this.numeroCuentaBancariaPago;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setNumeroCuentaBancariaPago(String numeroCuentaBancariaPago)
/* 22:   */   {
/* 23:44 */     this.numeroCuentaBancariaPago = numeroCuentaBancariaPago;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getFormaPago()
/* 27:   */   {
/* 28:48 */     return this.formaPago;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setFormaPago(String formaPago)
/* 32:   */   {
/* 33:52 */     this.formaPago = formaPago;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getDocumentoReferencia()
/* 37:   */   {
/* 38:56 */     return this.documentoReferencia;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setDocumentoReferencia(String documentoReferencia)
/* 42:   */   {
/* 43:60 */     this.documentoReferencia = documentoReferencia;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getBancoOrigen()
/* 47:   */   {
/* 48:64 */     return this.bancoOrigen;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setBancoOrigen(String bancoOrigen)
/* 52:   */   {
/* 53:68 */     this.bancoOrigen = bancoOrigen;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public BigDecimal getValor()
/* 57:   */   {
/* 58:72 */     return this.valor;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void setValor(BigDecimal valor)
/* 62:   */   {
/* 63:76 */     this.valor = valor;
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.DetalleFormaCobroWSEntity
 * JD-Core Version:    0.7.0.1
 */