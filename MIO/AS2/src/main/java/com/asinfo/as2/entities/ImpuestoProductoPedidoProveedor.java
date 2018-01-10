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
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="impuesto_producto_pedido_proveedor")
/*  20:    */ public class ImpuestoProductoPedidoProveedor
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 6726549908362350007L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="impuesto_producto_pedido_proveedor", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto_producto_pedido_proveedor")
/*  28:    */   @Column(name="id_impuesto_producto_pedido_proveedor")
/*  29:    */   private int idImpuestoProductoPedidoProveedor;
/*  30:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  31:    */   @JoinColumn(name="id_detalle_pedido_proveedor", nullable=true)
/*  32:    */   private DetallePedidoProveedor detallePedidoProveedor;
/*  33:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  34:    */   @JoinColumn(name="id_impuesto", nullable=true)
/*  35:    */   private Impuesto impuesto;
/*  36:    */   @Column(name="porcentaje_impuesto", nullable=true, precision=12, scale=2)
/*  37:    */   @Min(0L)
/*  38:    */   private BigDecimal porcentajeImpuesto;
/*  39:    */   @Transient
/*  40:    */   private BigDecimal baseImponible;
/*  41:    */   @Transient
/*  42:    */   private BigDecimal impuestoProducto;
/*  43:    */   @Transient
/*  44:    */   private boolean traAuxiliarEliminaImpuesto;
/*  45:    */   
/*  46:    */   public boolean isAuditable()
/*  47:    */   {
/*  48: 76 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 86 */     return this.idImpuestoProductoPedidoProveedor;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdImpuestoProductoPedidoProveedor()
/*  57:    */   {
/*  58:104 */     return this.idImpuestoProductoPedidoProveedor;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdImpuestoProductoPedidoProveedor(int idImpuestoProductoPedidoProveedor)
/*  62:    */   {
/*  63:116 */     this.idImpuestoProductoPedidoProveedor = idImpuestoProductoPedidoProveedor;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public DetallePedidoProveedor getDetallePedidoProveedor()
/*  67:    */   {
/*  68:125 */     if (this.detallePedidoProveedor == null) {
/*  69:126 */       this.detallePedidoProveedor = new DetallePedidoProveedor();
/*  70:    */     }
/*  71:128 */     return this.detallePedidoProveedor;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor)
/*  75:    */   {
/*  76:139 */     this.detallePedidoProveedor = detallePedidoProveedor;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Impuesto getImpuesto()
/*  80:    */   {
/*  81:148 */     return this.impuesto;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setImpuesto(Impuesto impuesto)
/*  85:    */   {
/*  86:158 */     this.impuesto = impuesto;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public BigDecimal getPorcentajeImpuesto()
/*  90:    */   {
/*  91:167 */     if (this.porcentajeImpuesto == null) {
/*  92:168 */       this.porcentajeImpuesto = BigDecimal.ZERO;
/*  93:    */     }
/*  94:170 */     return this.porcentajeImpuesto;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/*  98:    */   {
/*  99:180 */     this.porcentajeImpuesto = porcentajeImpuesto;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BigDecimal getBaseImponible()
/* 103:    */   {
/* 104:189 */     if (this.baseImponible == null) {
/* 105:190 */       this.baseImponible = BigDecimal.ZERO;
/* 106:    */     }
/* 107:192 */     return this.baseImponible;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setBaseImponible(BigDecimal baseImponible)
/* 111:    */   {
/* 112:202 */     this.baseImponible = baseImponible;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public BigDecimal getImpuestoProducto()
/* 116:    */   {
/* 117:211 */     BigDecimal porcentajeImpuesto = getPorcentajeImpuesto().divide(new BigDecimal(100));
/* 118:    */     
/* 119:    */ 
/* 120:214 */     this.impuestoProducto = getDetallePedidoProveedor().getBaseImponible().multiply(porcentajeImpuesto);
/* 121:215 */     return this.impuestoProducto;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setImpuestoProducto(BigDecimal impuestoProducto)
/* 125:    */   {
/* 126:225 */     this.impuestoProducto = impuestoProducto;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isTraAuxiliarEliminaImpuesto()
/* 130:    */   {
/* 131:234 */     return this.traAuxiliarEliminaImpuesto;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setTraAuxiliarEliminaImpuesto(boolean traAuxiliarEliminaImpuesto)
/* 135:    */   {
/* 136:244 */     this.traAuxiliarEliminaImpuesto = traAuxiliarEliminaImpuesto;
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor
 * JD-Core Version:    0.7.0.1
 */