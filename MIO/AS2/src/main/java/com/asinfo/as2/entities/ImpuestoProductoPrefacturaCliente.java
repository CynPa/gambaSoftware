/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="impuesto_producto_prefactura_cliente")
/*  21:    */ public class ImpuestoProductoPrefacturaCliente
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 8641839061796532072L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="impuesto_producto_prefactura_cliente", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto_producto_prefactura_cliente")
/*  29:    */   @Column(name="id_impuesto_producto_prefactura_cliente")
/*  30:    */   private int idImpuestoProductoPrefacturaCliente;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_detalle_ajuste_prefactura_cliente", nullable=true)
/*  33:    */   private DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_impuesto", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private Impuesto impuesto;
/*  38:    */   @Column(name="porcentaje_impuesto", nullable=false, precision=12, scale=2)
/*  39:    */   @Min(0L)
/*  40:    */   @NotNull
/*  41:    */   private BigDecimal porcentajeImpuesto;
/*  42:    */   @Transient
/*  43:    */   private BigDecimal impuestoProducto;
/*  44:    */   
/*  45:    */   public boolean isAuditable()
/*  46:    */   {
/*  47: 71 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 81 */     return this.idImpuestoProductoPrefacturaCliente;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdImpuestoProductoPrefacturaCliente()
/*  56:    */   {
/*  57: 90 */     return this.idImpuestoProductoPrefacturaCliente;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdImpuestoProductoPrefacturaCliente(int idImpuestoProductoPrefacturaCliente)
/*  61:    */   {
/*  62:100 */     this.idImpuestoProductoPrefacturaCliente = idImpuestoProductoPrefacturaCliente;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public DetalleAjustePrefacturaCliente getDetalleAjustePrefacturaCliente()
/*  66:    */   {
/*  67:109 */     return this.detalleAjustePrefacturaCliente;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setDetalleAjustePrefacturaCliente(DetalleAjustePrefacturaCliente detalleAjustePrefacturaCliente)
/*  71:    */   {
/*  72:119 */     this.detalleAjustePrefacturaCliente = detalleAjustePrefacturaCliente;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Impuesto getImpuesto()
/*  76:    */   {
/*  77:128 */     return this.impuesto;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setImpuesto(Impuesto impuesto)
/*  81:    */   {
/*  82:138 */     this.impuesto = impuesto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public BigDecimal getPorcentajeImpuesto()
/*  86:    */   {
/*  87:147 */     if (this.porcentajeImpuesto == null) {
/*  88:148 */       this.porcentajeImpuesto = BigDecimal.ZERO;
/*  89:    */     }
/*  90:150 */     return this.porcentajeImpuesto;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/*  94:    */   {
/*  95:160 */     this.porcentajeImpuesto = porcentajeImpuesto;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public BigDecimal getImpuestoProducto()
/*  99:    */   {
/* 100:169 */     BigDecimal porcentajeImpuesto = getPorcentajeImpuesto().divide(new BigDecimal(100));
/* 101:170 */     this.impuestoProducto = getDetalleAjustePrefacturaCliente().getBaseImponible().multiply(porcentajeImpuesto);
/* 102:171 */     return this.impuestoProducto;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setImpuestoProducto(BigDecimal impuestoProducto)
/* 106:    */   {
/* 107:181 */     this.impuestoProducto = impuestoProducto;
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ImpuestoProductoPrefacturaCliente
 * JD-Core Version:    0.7.0.1
 */