/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.FetchType;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.JoinColumn;
/* 11:   */ import javax.persistence.ManyToOne;
/* 12:   */ import javax.persistence.Table;
/* 13:   */ import javax.persistence.TableGenerator;
/* 14:   */ import javax.validation.constraints.NotNull;
/* 15:   */ 
/* 16:   */ @Entity
/* 17:   */ @Table(name="cuenta_contable_dimension_contable")
/* 18:   */ public class CuentaContableDimensionContable
/* 19:   */   extends EntidadBase
/* 20:   */   implements Serializable
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 1L;
/* 23:   */   @Id
/* 24:   */   @TableGenerator(name="cuenta_contable_dimension_contable", initialValue=0, allocationSize=50)
/* 25:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_contable_dimension_contable")
/* 26:   */   @Column(name="id_cuenta_contable_dimension_contable", unique=true, nullable=false)
/* 27:   */   private int idCuentaContableDimensionContable;
/* 28:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 29:   */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/* 30:   */   @NotNull
/* 31:   */   private CuentaContable cuentaContable;
/* 32:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 33:   */   @JoinColumn(name="id_dimension_contable", nullable=false)
/* 34:   */   @NotNull
/* 35:   */   private DimensionContable dimensionContable;
/* 36:   */   
/* 37:   */   public int getIdCuentaContableDimensionContable()
/* 38:   */   {
/* 39:47 */     return this.idCuentaContableDimensionContable;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdCuentaContableDimensionContable(int idCuentaContableDimensionContable)
/* 43:   */   {
/* 44:51 */     this.idCuentaContableDimensionContable = idCuentaContableDimensionContable;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public CuentaContable getCuentaContable()
/* 48:   */   {
/* 49:55 */     return this.cuentaContable;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setCuentaContable(CuentaContable cuentaContable)
/* 53:   */   {
/* 54:59 */     this.cuentaContable = cuentaContable;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public DimensionContable getDimensionContable()
/* 58:   */   {
/* 59:63 */     return this.dimensionContable;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setDimensionContable(DimensionContable dimensionContable)
/* 63:   */   {
/* 64:67 */     this.dimensionContable = dimensionContable;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int getId()
/* 68:   */   {
/* 69:72 */     return this.idCuentaContableDimensionContable;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaContableDimensionContable
 * JD-Core Version:    0.7.0.1
 */