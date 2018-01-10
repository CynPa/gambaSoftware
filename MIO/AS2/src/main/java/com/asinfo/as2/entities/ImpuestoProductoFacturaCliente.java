/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="impuesto_producto_factura_cliente", indexes={@javax.persistence.Index(columnList="id_detalle_factura_cliente")})
/*  21:    */ public class ImpuestoProductoFacturaCliente
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -3925034456921285076L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="impuesto_producto_factura_cliente", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto_producto_factura_cliente")
/*  29:    */   @Column(name="id_impuesto_producto_factura_cliente")
/*  30:    */   private int idImpuestoProductoFacturaCliente;
/*  31:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  32:    */   @JoinColumn(name="id_detalle_factura_cliente", nullable=true)
/*  33:    */   private DetalleFacturaCliente detalleFacturaCliente;
/*  34:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  35:    */   @JoinColumn(name="id_impuesto", nullable=true)
/*  36:    */   private Impuesto impuesto;
/*  37:    */   @Column(name="porcentaje_impuesto", nullable=true, precision=12, scale=2)
/*  38:    */   @Min(0L)
/*  39:    */   private BigDecimal porcentajeImpuesto;
/*  40:    */   @Transient
/*  41:    */   private BigDecimal baseImponible;
/*  42:    */   @Transient
/*  43:    */   private BigDecimal impuestoProducto;
/*  44:    */   
/*  45:    */   public int getId()
/*  46:    */   {
/*  47: 75 */     return this.idImpuestoProductoFacturaCliente;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdImpuestoProductoFacturaCliente()
/*  51:    */   {
/*  52: 93 */     return this.idImpuestoProductoFacturaCliente;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdImpuestoProductoFacturaCliente(int idImpuestoProductoFacturaCliente)
/*  56:    */   {
/*  57:103 */     this.idImpuestoProductoFacturaCliente = idImpuestoProductoFacturaCliente;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public DetalleFacturaCliente getDetalleFacturaCliente()
/*  61:    */   {
/*  62:112 */     return this.detalleFacturaCliente;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setDetalleFacturaCliente(DetalleFacturaCliente detalleFacturaCliente)
/*  66:    */   {
/*  67:122 */     this.detalleFacturaCliente = detalleFacturaCliente;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Impuesto getImpuesto()
/*  71:    */   {
/*  72:131 */     return this.impuesto;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setImpuesto(Impuesto impuesto)
/*  76:    */   {
/*  77:141 */     this.impuesto = impuesto;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public BigDecimal getBaseImponible()
/*  81:    */   {
/*  82:150 */     return this.baseImponible;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setBaseImponible(BigDecimal baseImponible)
/*  86:    */   {
/*  87:160 */     this.baseImponible = baseImponible;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public BigDecimal getImpuestoProducto()
/*  91:    */   {
/*  92:169 */     BigDecimal porcentajeImpuesto = getPorcentajeImpuesto().divide(new BigDecimal(100));
/*  93:170 */     if (getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_PRECIO)) {
/*  94:171 */       this.impuestoProducto = getDetalleFacturaCliente().getBaseImponible().multiply(porcentajeImpuesto);
/*  95:    */     }
/*  96:173 */     if (getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/*  97:    */     {
/*  98:174 */       BigDecimal cantidadUnidades = getDetalleFacturaCliente().getCantidad();
/*  99:175 */       if (getDetalleFacturaCliente().getProducto().getPresentacionProducto() != null) {
/* 100:177 */         cantidadUnidades = cantidadUnidades.multiply(getDetalleFacturaCliente().getProducto().getPresentacionProducto().getCantidadUnidades());
/* 101:    */       }
/* 102:179 */       this.impuestoProducto = getPorcentajeImpuesto().multiply(cantidadUnidades);
/* 103:    */     }
/* 104:182 */     return this.impuestoProducto;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BigDecimal getCantidadUnidades()
/* 108:    */   {
/* 109:186 */     BigDecimal cantidadUnidades = getDetalleFacturaCliente().getCantidad();
/* 110:187 */     if (getDetalleFacturaCliente().getProducto().getPresentacionProducto() != null) {
/* 111:188 */       cantidadUnidades = cantidadUnidades.multiply(getDetalleFacturaCliente().getProducto().getPresentacionProducto().getCantidadUnidades());
/* 112:    */     }
/* 113:190 */     return cantidadUnidades;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setImpuestoProducto(BigDecimal impuestoProducto)
/* 117:    */   {
/* 118:200 */     this.impuestoProducto = impuestoProducto;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public BigDecimal getPorcentajeImpuesto()
/* 122:    */   {
/* 123:209 */     return this.porcentajeImpuesto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/* 127:    */   {
/* 128:219 */     this.porcentajeImpuesto = porcentajeImpuesto;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ImpuestoProductoFacturaCliente
 * JD-Core Version:    0.7.0.1
 */