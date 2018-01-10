/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Digits;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="detalle_pago_cuota_prestamo")
/*  20:    */ public class DetallePagoCuotaPrestamo
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 4364845703395497938L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="detalle_pago_cuota_prestamo", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pago_cuota_prestamo")
/*  27:    */   @Column(name="id_detalle_pago_cuota_prestamo")
/*  28:    */   private int idDetallePagoCuotaPrestamo;
/*  29:    */   @Column(name="numero_cuota", nullable=false)
/*  30:    */   @Min(0L)
/*  31:    */   private int numeroCuota;
/*  32:    */   @Column(name="capital_cuota", scale=12, precision=2, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Digits(integer=12, fraction=2)
/*  35:    */   @Min(0L)
/*  36:    */   private BigDecimal capitalCuota;
/*  37:    */   @Column(name="interes_cuota", scale=12, precision=2, nullable=false)
/*  38:    */   @NotNull
/*  39:    */   @Digits(integer=12, fraction=2)
/*  40:    */   @Min(0L)
/*  41:    */   private BigDecimal interesCuota;
/*  42:    */   @Column(name="interes_mora_cuota", scale=12, precision=2, nullable=true)
/*  43:    */   @Digits(integer=12, fraction=2)
/*  44:    */   @Min(0L)
/*  45:    */   private BigDecimal interesMoraCuota;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_pago_rol_empleado")
/*  48:    */   private PagoRolEmpleado pagoRolEmpleado;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_detalle_prestamo")
/*  51:    */   private DetallePrestamo detallePrestamo;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55:107 */     return this.idDetallePagoCuotaPrestamo;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdDetallePagoCuotaPrestamo()
/*  59:    */   {
/*  60:116 */     return this.idDetallePagoCuotaPrestamo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdDetallePagoCuotaPrestamo(int idDetallePagoCuotaPrestamo)
/*  64:    */   {
/*  65:126 */     this.idDetallePagoCuotaPrestamo = idDetallePagoCuotaPrestamo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getNumeroCuota()
/*  69:    */   {
/*  70:135 */     return this.numeroCuota;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setNumeroCuota(int numeroCuota)
/*  74:    */   {
/*  75:145 */     this.numeroCuota = numeroCuota;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public BigDecimal getCapitalCuota()
/*  79:    */   {
/*  80:154 */     return this.capitalCuota;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setCapitalCuota(BigDecimal capitalCuota)
/*  84:    */   {
/*  85:164 */     this.capitalCuota = capitalCuota;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public BigDecimal getInteresCuota()
/*  89:    */   {
/*  90:173 */     return this.interesCuota;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setInteresCuota(BigDecimal interesCuota)
/*  94:    */   {
/*  95:183 */     this.interesCuota = interesCuota;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public BigDecimal getInteresMoraCuota()
/*  99:    */   {
/* 100:192 */     return this.interesMoraCuota;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setInteresMoraCuota(BigDecimal interesMoraCuota)
/* 104:    */   {
/* 105:202 */     this.interesMoraCuota = interesMoraCuota;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public DetallePrestamo getDetallePrestamo()
/* 109:    */   {
/* 110:211 */     return this.detallePrestamo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDetallePrestamo(DetallePrestamo detallePrestamo)
/* 114:    */   {
/* 115:221 */     this.detallePrestamo = detallePrestamo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public PagoRolEmpleado getPagoRolEmpleado()
/* 119:    */   {
/* 120:230 */     return this.pagoRolEmpleado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPagoRolEmpleado(PagoRolEmpleado pagoRolEmpleado)
/* 124:    */   {
/* 125:240 */     this.pagoRolEmpleado = pagoRolEmpleado;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePagoCuotaPrestamo
 * JD-Core Version:    0.7.0.1
 */