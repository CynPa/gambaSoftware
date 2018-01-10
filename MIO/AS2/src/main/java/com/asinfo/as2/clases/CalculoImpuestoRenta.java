/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.Id;
/*   7:    */ import javax.persistence.Table;
/*   8:    */ 
/*   9:    */ @Entity
/*  10:    */ @Table(name="tmp_calculo_impuesto_renta")
/*  11:    */ public class CalculoImpuestoRenta
/*  12:    */ {
/*  13:    */   @Id
/*  14:    */   @Column(name="calculo_impuesto_renta")
/*  15:    */   private Integer idCalculoImpuestoRenta;
/*  16:    */   @Column(name="ingresos")
/*  17:    */   private BigDecimal ingresos;
/*  18:    */   @Column(name="iess")
/*  19:    */   private BigDecimal iess;
/*  20:    */   @Column(name="impuesto_renta")
/*  21:    */   private BigDecimal impuestoRenta;
/*  22:    */   
/*  23:    */   public CalculoImpuestoRenta(BigDecimal ingresos, BigDecimal iess, BigDecimal impuestoRenta)
/*  24:    */   {
/*  25: 51 */     this.ingresos = ingresos;
/*  26: 52 */     this.iess = iess;
/*  27: 53 */     this.impuestoRenta = impuestoRenta;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Integer getIdCalculoImpuestoRenta()
/*  31:    */   {
/*  32: 62 */     return this.idCalculoImpuestoRenta;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setIdCalculoImpuestoRenta(Integer idCalculoImpuestoRenta)
/*  36:    */   {
/*  37: 72 */     this.idCalculoImpuestoRenta = idCalculoImpuestoRenta;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public BigDecimal getIngresos()
/*  41:    */   {
/*  42: 81 */     return this.ingresos;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIngresos(BigDecimal ingresos)
/*  46:    */   {
/*  47: 91 */     this.ingresos = ingresos;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public BigDecimal getIess()
/*  51:    */   {
/*  52:100 */     return this.iess;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIess(BigDecimal iess)
/*  56:    */   {
/*  57:110 */     this.iess = iess;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public BigDecimal getImpuestoRenta()
/*  61:    */   {
/*  62:119 */     return this.impuestoRenta;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setImpuestoRenta(BigDecimal impuestoRenta)
/*  66:    */   {
/*  67:129 */     this.impuestoRenta = impuestoRenta;
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.CalculoImpuestoRenta
 * JD-Core Version:    0.7.0.1
 */