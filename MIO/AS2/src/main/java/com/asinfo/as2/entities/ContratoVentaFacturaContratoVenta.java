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
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="contrato_venta_factura_contrato_venta")
/*  20:    */ public class ContratoVentaFacturaContratoVenta
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="contrato_venta_factura_contrato_venta", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="contrato_venta_factura_contrato_venta")
/*  28:    */   @Column(name="id_contrato_venta_factura_contrato_venta", unique=true, nullable=false)
/*  29:    */   private int idContratoVentaFacturaContratoVenta;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_detalle_contrato_venta", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private DetalleContratoVenta detalleContratoVenta;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_detalles_factura_contrato_venta", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private DetallesFacturaContratoVenta detallesFacturaContratoVenta;
/*  42:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  43:    */   @Digits(integer=12, fraction=2)
/*  44: 54 */   private BigDecimal valor = BigDecimal.ZERO;
/*  45: 59 */   private transient BigDecimal valorTotal = BigDecimal.ZERO;
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 66 */     return this.idContratoVentaFacturaContratoVenta;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdContratoVentaFacturaContratoVenta()
/*  53:    */   {
/*  54: 70 */     return this.idContratoVentaFacturaContratoVenta;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdContratoVentaFacturaContratoVenta(int idContratoVentaFacturaContratoVenta)
/*  58:    */   {
/*  59: 74 */     this.idContratoVentaFacturaContratoVenta = idContratoVentaFacturaContratoVenta;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdOrganizacion()
/*  63:    */   {
/*  64: 78 */     return this.idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdOrganizacion(int idOrganizacion)
/*  68:    */   {
/*  69: 82 */     this.idOrganizacion = idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74: 86 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79: 90 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public DetalleContratoVenta getDetalleContratoVenta()
/*  83:    */   {
/*  84: 94 */     return this.detalleContratoVenta;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setDetalleContratoVenta(DetalleContratoVenta detalleContratoVenta)
/*  88:    */   {
/*  89: 98 */     this.detalleContratoVenta = detalleContratoVenta;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public DetallesFacturaContratoVenta getDetallesFacturaContratoVenta()
/*  93:    */   {
/*  94:102 */     return this.detallesFacturaContratoVenta;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setDetallesFacturaContratoVenta(DetallesFacturaContratoVenta detallesFacturaContratoVenta)
/*  98:    */   {
/*  99:106 */     this.detallesFacturaContratoVenta = detallesFacturaContratoVenta;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BigDecimal getValor()
/* 103:    */   {
/* 104:110 */     return this.valor;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setValor(BigDecimal valor)
/* 108:    */   {
/* 109:114 */     this.valor = valor;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public BigDecimal getValorTotal()
/* 113:    */   {
/* 114:118 */     return this.valorTotal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setValorTotal(BigDecimal valorTotal)
/* 118:    */   {
/* 119:122 */     this.valorTotal = valorTotal;
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta
 * JD-Core Version:    0.7.0.1
 */