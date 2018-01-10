/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ 
/*   5:    */ public class DepreciacionNIIFNEC
/*   6:    */ {
/*   7:    */   private int idCuentaContableDebe;
/*   8:    */   private int idCuentaContableHaber;
/*   9:    */   private BigDecimal valorNIIF;
/*  10:    */   private BigDecimal valorNEC;
/*  11:    */   private BigDecimal diferencia;
/*  12:    */   private BigDecimal porcentajeImpuestoRentaAnual;
/*  13:    */   private BigDecimal impuestoDiferido;
/*  14:    */   
/*  15:    */   public int getIdCuentaContableDebe()
/*  16:    */   {
/*  17: 37 */     return this.idCuentaContableDebe;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setIdCuentaContableDebe(int idCuentaContableDebe)
/*  21:    */   {
/*  22: 47 */     this.idCuentaContableDebe = idCuentaContableDebe;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getIdCuentaContableHaber()
/*  26:    */   {
/*  27: 56 */     return this.idCuentaContableHaber;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setIdCuentaContableHaber(int idCuentaContableHaber)
/*  31:    */   {
/*  32: 66 */     this.idCuentaContableHaber = idCuentaContableHaber;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public BigDecimal getValorNIIF()
/*  36:    */   {
/*  37: 75 */     return this.valorNIIF;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setValorNIIF(BigDecimal valorNIIF)
/*  41:    */   {
/*  42: 85 */     this.valorNIIF = valorNIIF;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public BigDecimal getValorNEC()
/*  46:    */   {
/*  47: 94 */     return this.valorNEC;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setValorNEC(BigDecimal valorNEC)
/*  51:    */   {
/*  52:104 */     this.valorNEC = valorNEC;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public BigDecimal getDiferencia()
/*  56:    */   {
/*  57:113 */     return this.diferencia;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setDiferencia(BigDecimal diferencia)
/*  61:    */   {
/*  62:123 */     this.diferencia = diferencia;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public BigDecimal getPorcentajeImpuestoRentaAnual()
/*  66:    */   {
/*  67:132 */     return this.porcentajeImpuestoRentaAnual;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setPorcentajeImpuestoRentaAnual(BigDecimal porcentajeImpuestoRentaAnual)
/*  71:    */   {
/*  72:142 */     this.porcentajeImpuestoRentaAnual = porcentajeImpuestoRentaAnual;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public BigDecimal getImpuestoDiferido()
/*  76:    */   {
/*  77:151 */     return this.impuestoDiferido;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setImpuestoDiferido(BigDecimal impuestoDiferido)
/*  81:    */   {
/*  82:161 */     this.impuestoDiferido = impuestoDiferido;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DepreciacionNIIFNEC
 * JD-Core Version:    0.7.0.1
 */