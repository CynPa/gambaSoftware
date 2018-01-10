/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.DecimalMin;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="detalle_orden_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_pedido_cliente", "id_detalle_orden_fabricacion"})})
/*  24:    */ public class DetalleOrdenFabricacion
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="detalle_orden_fabricacion", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_fabricacion")
/*  31:    */   @Column(name="id_detalle_orden_fabricacion")
/*  32:    */   private int idDetalleOrdenFabricacion;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @NotNull
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  38:    */   private Sucursal sucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_orden_fabricacion", nullable=true)
/*  41:    */   private OrdenFabricacion ordenFabricacion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_detalle_pedido_cliente", nullable=true)
/*  44:    */   private DetallePedidoCliente detallePedido;
/*  45:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  46:    */   @Digits(integer=10, fraction=2)
/*  47:    */   @Min(0L)
/*  48: 56 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  49:    */   @Column(name="cantidad_producida", nullable=true, precision=12, scale=2)
/*  50:    */   @Digits(integer=10, fraction=2)
/*  51:    */   @DecimalMin("0.00")
/*  52: 61 */   private BigDecimal cantidadProducida = BigDecimal.ZERO;
/*  53:    */   
/*  54:    */   public int getIdDetalleOrdenFabricacion()
/*  55:    */   {
/*  56: 67 */     return this.idDetalleOrdenFabricacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdDetalleOrdenFabricacion(int idDetalleOrdenFabricacion)
/*  60:    */   {
/*  61: 71 */     this.idDetalleOrdenFabricacion = idDetalleOrdenFabricacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66: 75 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71: 79 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Sucursal getSucursal()
/*  75:    */   {
/*  76: 83 */     return this.sucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setSucursal(Sucursal sucursal)
/*  80:    */   {
/*  81: 87 */     this.sucursal = sucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public OrdenFabricacion getOrdenFabricacion()
/*  85:    */   {
/*  86: 91 */     return this.ordenFabricacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/*  90:    */   {
/*  91: 95 */     this.ordenFabricacion = ordenFabricacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public BigDecimal getCantidad()
/*  95:    */   {
/*  96: 99 */     return this.cantidad;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setCantidad(BigDecimal cantidad)
/* 100:    */   {
/* 101:103 */     this.cantidad = cantidad;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public DetallePedidoCliente getDetallePedido()
/* 105:    */   {
/* 106:107 */     return this.detallePedido;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDetallePedido(DetallePedidoCliente detallePedido)
/* 110:    */   {
/* 111:111 */     this.detallePedido = detallePedido;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getId()
/* 115:    */   {
/* 116:116 */     return this.idDetalleOrdenFabricacion;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.DetalleOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */