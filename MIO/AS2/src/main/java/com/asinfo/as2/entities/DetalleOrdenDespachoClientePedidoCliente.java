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
/*  16:    */ import javax.validation.constraints.DecimalMin;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_orden_despacho_cliente_pedido_cliente", indexes={@javax.persistence.Index(columnList="id_detalle_orden_despacho_cliente")})
/*  22:    */ public class DetalleOrdenDespachoClientePedidoCliente
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 6041597607233226319L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_orden_despacho_cliente_pedido_cliente", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_despacho_cliente_pedido_cliente")
/*  30:    */   @Column(name="id_detalle_orden_despacho_cliente_pedido_cliente", unique=true, nullable=false)
/*  31:    */   private int idDetalleOrdenDespachoClientePedidoCliente;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private int idSucursal;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_detalle_pedido_cliente", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private DetallePedidoCliente detallePedidoCliente;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_detalle_orden_despacho_cliente", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private DetalleOrdenDespachoCliente detalleOrdenDespachoCliente;
/*  46:    */   @Column(name="cantidad", nullable=true, precision=12, scale=4)
/*  47:    */   @Digits(integer=10, fraction=4)
/*  48:    */   @DecimalMin("0.00")
/*  49: 70 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  50:    */   @Column(name="peso_materia_prima", nullable=true, precision=38, scale=20)
/*  51:    */   @Digits(integer=18, fraction=20)
/*  52:    */   @DecimalMin("0.00")
/*  53: 75 */   private BigDecimal pesoMateriaPrima = BigDecimal.ZERO;
/*  54:    */   @Transient
/*  55: 80 */   private BigDecimal proporcion = BigDecimal.ZERO;
/*  56:    */   
/*  57:    */   public int getId()
/*  58:    */   {
/*  59: 85 */     return this.idDetalleOrdenDespachoClientePedidoCliente;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdOrganizacion()
/*  63:    */   {
/*  64: 89 */     return this.idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdOrganizacion(int idOrganizacion)
/*  68:    */   {
/*  69: 93 */     this.idOrganizacion = idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74: 97 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79:101 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdDetalleOrdenDespachoClientePedidoCliente()
/*  83:    */   {
/*  84:105 */     return this.idDetalleOrdenDespachoClientePedidoCliente;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdDetalleOrdenDespachoClientePedidoCliente(int idDetalleOrdenDespachoClientePedidoCliente)
/*  88:    */   {
/*  89:109 */     this.idDetalleOrdenDespachoClientePedidoCliente = idDetalleOrdenDespachoClientePedidoCliente;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public DetallePedidoCliente getDetallePedidoCliente()
/*  93:    */   {
/*  94:113 */     return this.detallePedidoCliente;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setDetallePedidoCliente(DetallePedidoCliente detallePedidoCliente)
/*  98:    */   {
/*  99:117 */     this.detallePedidoCliente = detallePedidoCliente;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public DetalleOrdenDespachoCliente getDetalleOrdenDespachoCliente()
/* 103:    */   {
/* 104:121 */     return this.detalleOrdenDespachoCliente;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDetalleOrdenDespachoCliente(DetalleOrdenDespachoCliente detalleOrdenDespachoCliente)
/* 108:    */   {
/* 109:125 */     this.detalleOrdenDespachoCliente = detalleOrdenDespachoCliente;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public BigDecimal getCantidad()
/* 113:    */   {
/* 114:129 */     return this.cantidad;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setCantidad(BigDecimal cantidad)
/* 118:    */   {
/* 119:133 */     this.cantidad = cantidad;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public BigDecimal getPesoMateriaPrima()
/* 123:    */   {
/* 124:137 */     return this.pesoMateriaPrima;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPesoMateriaPrima(BigDecimal pesoMateriaPrima)
/* 128:    */   {
/* 129:141 */     this.pesoMateriaPrima = pesoMateriaPrima;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public BigDecimal getProporcion()
/* 133:    */   {
/* 134:145 */     return this.proporcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setProporcion(BigDecimal proporcion)
/* 138:    */   {
/* 139:149 */     this.proporcion = proporcion;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleOrdenDespachoClientePedidoCliente
 * JD-Core Version:    0.7.0.1
 */