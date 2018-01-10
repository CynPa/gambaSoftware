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
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="impuesto_producto_pedido_cliente")
/*  22:    */ public class ImpuestoProductoPedidoCliente
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -133637269446727260L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="impuesto_producto_pedido_cliente", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto_producto_pedido_cliente")
/*  30:    */   @Column(name="id_impuesto_producto_pedido_cliente")
/*  31:    */   private int idImpuestoProductoPedidoCliente;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_detalle_pedido_cliente", nullable=true)
/*  34:    */   private DetallePedidoCliente detallePedidoCliente;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_impuesto", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private Impuesto impuesto;
/*  39:    */   @Column(name="porcentaje_impuesto", nullable=false, precision=12, scale=2)
/*  40:    */   @Min(0L)
/*  41:    */   @NotNull
/*  42:    */   private BigDecimal porcentajeImpuesto;
/*  43:    */   @Transient
/*  44:    */   private BigDecimal baseImponible;
/*  45:    */   @Transient
/*  46:    */   private Producto producto;
/*  47:    */   @Transient
/*  48:    */   private BigDecimal cantidad;
/*  49:    */   @Transient
/*  50:    */   private BigDecimal impuestoProducto;
/*  51:    */   
/*  52:    */   public boolean isAuditable()
/*  53:    */   {
/*  54: 82 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getId()
/*  58:    */   {
/*  59: 92 */     return this.idImpuestoProductoPedidoCliente;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdImpuestoPrductoPedidoCliente()
/*  63:    */   {
/*  64:103 */     return this.idImpuestoProductoPedidoCliente;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdImpuestoPrductoPedidoCliente(int idImpuestoPrductoPedidoCliente)
/*  68:    */   {
/*  69:107 */     this.idImpuestoProductoPedidoCliente = idImpuestoPrductoPedidoCliente;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public DetallePedidoCliente getDetallePedidoCliente()
/*  73:    */   {
/*  74:111 */     return this.detallePedidoCliente;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setDetallePedidoCliente(DetallePedidoCliente detallePedidoCliente)
/*  78:    */   {
/*  79:115 */     this.detallePedidoCliente = detallePedidoCliente;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Impuesto getImpuesto()
/*  83:    */   {
/*  84:119 */     return this.impuesto;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setImpuesto(Impuesto impuesto)
/*  88:    */   {
/*  89:123 */     this.impuesto = impuesto;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public BigDecimal getPorcentajeImpuesto()
/*  93:    */   {
/*  94:127 */     if (this.porcentajeImpuesto == null) {
/*  95:128 */       this.porcentajeImpuesto = BigDecimal.ZERO;
/*  96:    */     }
/*  97:130 */     return this.porcentajeImpuesto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/* 101:    */   {
/* 102:134 */     this.porcentajeImpuesto = porcentajeImpuesto;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public BigDecimal getBaseImponible()
/* 106:    */   {
/* 107:138 */     if (this.baseImponible == null) {
/* 108:139 */       this.baseImponible = BigDecimal.ZERO;
/* 109:    */     }
/* 110:141 */     return this.baseImponible;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setBaseImponible(BigDecimal baseImponible)
/* 114:    */   {
/* 115:145 */     this.baseImponible = baseImponible;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Producto getProducto()
/* 119:    */   {
/* 120:149 */     return this.producto;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setProducto(Producto producto)
/* 124:    */   {
/* 125:153 */     this.producto = producto;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public BigDecimal getCantidad()
/* 129:    */   {
/* 130:157 */     if (this.cantidad == null) {
/* 131:158 */       this.cantidad = BigDecimal.ZERO;
/* 132:    */     }
/* 133:160 */     return this.cantidad;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setCantidad(BigDecimal cantidad)
/* 137:    */   {
/* 138:164 */     this.cantidad = cantidad;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public BigDecimal getImpuestoProducto()
/* 142:    */   {
/* 143:171 */     BigDecimal porcentajeImpuesto = getPorcentajeImpuesto().divide(new BigDecimal(100));
/* 144:172 */     if (getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_PRECIO))
/* 145:    */     {
/* 146:173 */       if (getDetallePedidoCliente() != null) {
/* 147:174 */         this.baseImponible = getDetallePedidoCliente().getBaseImponible();
/* 148:    */       }
/* 149:176 */       this.impuestoProducto = getBaseImponible().multiply(porcentajeImpuesto);
/* 150:    */     }
/* 151:178 */     if (getImpuesto().getTipoImpuesto().equals(TipoImpuestoEnum.AFECTA_CANTIDAD_UNIDADES))
/* 152:    */     {
/* 153:179 */       BigDecimal cantidadUnidades = getCantidad();
/* 154:180 */       if (getDetallePedidoCliente() != null)
/* 155:    */       {
/* 156:181 */         this.producto = getDetallePedidoCliente().getProducto();
/* 157:182 */         cantidadUnidades = getDetallePedidoCliente().getCantidad();
/* 158:    */       }
/* 159:184 */       if (this.producto.getPresentacionProducto() != null) {
/* 160:185 */         cantidadUnidades = cantidadUnidades.multiply(this.producto.getPresentacionProducto().getCantidadUnidades());
/* 161:    */       }
/* 162:187 */       this.impuestoProducto = getPorcentajeImpuesto().multiply(cantidadUnidades);
/* 163:    */     }
/* 164:189 */     return this.impuestoProducto;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getCantidadUnidades()
/* 168:    */   {
/* 169:193 */     BigDecimal cantidadUnidades = getDetallePedidoCliente().getCantidad();
/* 170:194 */     if (getDetallePedidoCliente().getProducto().getPresentacionProducto() != null) {
/* 171:195 */       cantidadUnidades = cantidadUnidades.multiply(getDetallePedidoCliente().getProducto().getPresentacionProducto().getCantidadUnidades());
/* 172:    */     }
/* 173:197 */     return cantidadUnidades;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setImpuestoProducto(BigDecimal impuestoProducto)
/* 177:    */   {
/* 178:201 */     this.impuestoProducto = impuestoProducto;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ImpuestoProductoPedidoCliente
 * JD-Core Version:    0.7.0.1
 */