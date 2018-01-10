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
/*  19:    */ @Table(name="impuesto_producto_factura_proveedor", indexes={@javax.persistence.Index(columnList="id_detalle_factura_proveedor")})
/*  20:    */ public class ImpuestoProductoFacturaProveedor
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -290466553428542113L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="impuesto_producto_factura_proveedor", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto_producto_factura_proveedor")
/*  28:    */   @Column(name="id_impuesto_producto_factura_proveedor")
/*  29:    */   private int idImpuestoProductoFacturaProveedor;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_detalle_factura_proveedor", nullable=true)
/*  32:    */   private DetalleFacturaProveedor detalleFacturaProveedor;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_impuesto", nullable=true)
/*  35:    */   private Impuesto impuesto;
/*  36:    */   @Column(name="porcentaje_impuesto", nullable=true, precision=12, scale=2)
/*  37:    */   @Min(0L)
/*  38:    */   private BigDecimal porcentajeImpuesto;
/*  39:    */   @Column(name="porcentaje_descuento_impuesto", nullable=true, precision=12, scale=2)
/*  40:    */   @Min(0L)
/*  41: 60 */   private BigDecimal porcentajeDescuentoImpuesto = BigDecimal.ZERO;
/*  42:    */   @Transient
/*  43:    */   private BigDecimal impuestoProducto;
/*  44:    */   @Transient
/*  45:    */   private BigDecimal descuentoImpuestoProducto;
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 77 */     return this.idImpuestoProductoFacturaProveedor;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setId(int idImpuestoProductoFacturaProveedor)
/*  53:    */   {
/*  54: 82 */     this.idImpuestoProductoFacturaProveedor = idImpuestoProductoFacturaProveedor;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdImpuestoProductoFacturaProveedor()
/*  58:    */   {
/*  59:100 */     return this.idImpuestoProductoFacturaProveedor;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdImpuestoProductoFacturaProveedor(int idImpuestoProductoFacturaProveedor)
/*  63:    */   {
/*  64:110 */     this.idImpuestoProductoFacturaProveedor = idImpuestoProductoFacturaProveedor;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public DetalleFacturaProveedor getDetalleFacturaProveedor()
/*  68:    */   {
/*  69:119 */     return this.detalleFacturaProveedor;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setDetalleFacturaProveedor(DetalleFacturaProveedor detalleFacturaProveedor)
/*  73:    */   {
/*  74:129 */     this.detalleFacturaProveedor = detalleFacturaProveedor;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Impuesto getImpuesto()
/*  78:    */   {
/*  79:138 */     return this.impuesto;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setImpuesto(Impuesto impuesto)
/*  83:    */   {
/*  84:148 */     this.impuesto = impuesto;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public BigDecimal getPorcentajeImpuesto()
/*  88:    */   {
/*  89:157 */     return this.porcentajeImpuesto;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/*  93:    */   {
/*  94:167 */     if (this.porcentajeDescuentoImpuesto.compareTo(porcentajeImpuesto) > 0) {
/*  95:168 */       this.porcentajeDescuentoImpuesto = porcentajeImpuesto;
/*  96:    */     }
/*  97:170 */     this.porcentajeImpuesto = porcentajeImpuesto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public BigDecimal getImpuestoProducto()
/* 101:    */   {
/* 102:180 */     BigDecimal porcentajeImpuesto = getPorcentajeImpuesto().divide(new BigDecimal(100));
/* 103:181 */     this.impuestoProducto = getDetalleFacturaProveedor().getBaseImponible().multiply(porcentajeImpuesto);
/* 104:    */     
/* 105:183 */     return this.impuestoProducto;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public BigDecimal getDescuentoImpuestoProducto()
/* 109:    */   {
/* 110:193 */     BigDecimal porcentajeDescuentoImpuesto = getPorcentajeDescuentoImpuesto().divide(new BigDecimal(100));
/* 111:194 */     this.descuentoImpuestoProducto = getDetalleFacturaProveedor().getBaseImponible().multiply(porcentajeDescuentoImpuesto);
/* 112:    */     
/* 113:196 */     return this.descuentoImpuestoProducto;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setImpuestoProducto(BigDecimal impuestoProducto)
/* 117:    */   {
/* 118:206 */     this.impuestoProducto = impuestoProducto;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public BigDecimal getPorcentajeDescuentoImpuesto()
/* 122:    */   {
/* 123:210 */     return this.porcentajeDescuentoImpuesto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPorcentajeDescuentoImpuesto(BigDecimal porcentajeDescuentoImpuesto)
/* 127:    */   {
/* 128:214 */     if (porcentajeDescuentoImpuesto.compareTo(this.porcentajeImpuesto) > 0) {
/* 129:215 */       porcentajeDescuentoImpuesto = this.porcentajeImpuesto;
/* 130:    */     }
/* 131:217 */     this.porcentajeDescuentoImpuesto = porcentajeDescuentoImpuesto;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ImpuestoProductoFacturaProveedor
 * JD-Core Version:    0.7.0.1
 */