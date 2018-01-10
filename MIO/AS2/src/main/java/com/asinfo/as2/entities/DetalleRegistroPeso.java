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
/*  14:    */ import javax.validation.constraints.DecimalMin;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import org.hibernate.annotations.ColumnDefault;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_registro_peso")
/*  21:    */ public class DetalleRegistroPeso
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_registro_peso", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_registro_peso")
/*  28:    */   @Column(name="id_detalle_registro_peso")
/*  29:    */   private int idDetalleRegistroPeso;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  35:    */   @Digits(integer=12, fraction=2)
/*  36:    */   @DecimalMin("0.00")
/*  37:    */   @ColumnDefault("0")
/*  38: 48 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_registro_peso", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private RegistroPeso registroPeso;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_detalle_pedido_cliente", nullable=true)
/*  45:    */   private DetallePedidoCliente detallePedidoCliente;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_detalle_factura_cliente", nullable=true)
/*  48:    */   private DetalleFacturaCliente detalleFacturaCliente;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_detalle_orden_salida_material", nullable=true)
/*  51:    */   private DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 74 */     return this.idDetalleRegistroPeso;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdDetalleRegistroPeso()
/*  59:    */   {
/*  60: 78 */     return this.idDetalleRegistroPeso;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdDetalleRegistroPeso(int idDetalleRegistroPeso)
/*  64:    */   {
/*  65: 82 */     this.idDetalleRegistroPeso = idDetalleRegistroPeso;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 86 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75: 90 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80: 94 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85: 98 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public RegistroPeso getRegistroPeso()
/*  89:    */   {
/*  90:102 */     return this.registroPeso;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/*  94:    */   {
/*  95:106 */     this.registroPeso = registroPeso;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public DetallePedidoCliente getDetallePedidoCliente()
/*  99:    */   {
/* 100:110 */     return this.detallePedidoCliente;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setDetallePedidoCliente(DetallePedidoCliente detallePedidoCliente)
/* 104:    */   {
/* 105:114 */     this.detallePedidoCliente = detallePedidoCliente;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public DetalleOrdenSalidaMaterial getDetalleOrdenSalidaMaterial()
/* 109:    */   {
/* 110:118 */     return this.detalleOrdenSalidaMaterial;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDetalleOrdenSalidaMaterial(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial)
/* 114:    */   {
/* 115:122 */     this.detalleOrdenSalidaMaterial = detalleOrdenSalidaMaterial;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public DetalleFacturaCliente getDetalleFacturaCliente()
/* 119:    */   {
/* 120:126 */     return this.detalleFacturaCliente;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDetalleFacturaCliente(DetalleFacturaCliente detalleFacturaCliente)
/* 124:    */   {
/* 125:130 */     this.detalleFacturaCliente = detalleFacturaCliente;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public BigDecimal getCantidad()
/* 129:    */   {
/* 130:134 */     return this.cantidad;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setCantidad(BigDecimal cantidad)
/* 134:    */   {
/* 135:138 */     this.cantidad = cantidad;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleRegistroPeso
 * JD-Core Version:    0.7.0.1
 */