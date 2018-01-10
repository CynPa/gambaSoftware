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
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="detalle_pago", indexes={@javax.persistence.Index(columnList="id_pago"), @javax.persistence.Index(columnList="id_cuenta_por_pagar")})
/*  18:    */ public class DetallePago
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="detalle_pago", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pago")
/*  25:    */   @Column(name="id_detalle_pago")
/*  26:    */   private int idDetallePago;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_pago", nullable=true)
/*  33:    */   private Pago pago;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  35:    */   @JoinColumn(name="id_cuenta_por_pagar", nullable=false)
/*  36:    */   private CuentaPorPagar cuentaPorPagar;
/*  37:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  38:    */   @Digits(integer=12, fraction=2)
/*  39: 63 */   private BigDecimal valor = BigDecimal.ZERO;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  41:    */   @JoinColumn(name="id_detalle_orden_pago_proveedor", nullable=true)
/*  42:    */   private DetalleOrdenPagoProveedor detalleOrdenPagoProveedor;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 76 */     return this.idDetallePago;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdDetallePago()
/*  50:    */   {
/*  51: 85 */     return this.idDetallePago;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdDetallePago(int idDetallePago)
/*  55:    */   {
/*  56: 95 */     this.idDetallePago = idDetallePago;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdOrganizacion()
/*  60:    */   {
/*  61:104 */     return this.idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdOrganizacion(int idOrganizacion)
/*  65:    */   {
/*  66:114 */     this.idOrganizacion = idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdSucursal()
/*  70:    */   {
/*  71:123 */     return this.idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdSucursal(int idSucursal)
/*  75:    */   {
/*  76:133 */     this.idSucursal = idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Pago getPago()
/*  80:    */   {
/*  81:142 */     return this.pago;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setPago(Pago pago)
/*  85:    */   {
/*  86:152 */     this.pago = pago;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public CuentaPorPagar getCuentaPorPagar()
/*  90:    */   {
/*  91:161 */     return this.cuentaPorPagar;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setCuentaPorPagar(CuentaPorPagar cuentaPorPagar)
/*  95:    */   {
/*  96:171 */     this.cuentaPorPagar = cuentaPorPagar;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public BigDecimal getValor()
/* 100:    */   {
/* 101:180 */     return this.valor;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setValor(BigDecimal valor)
/* 105:    */   {
/* 106:190 */     this.valor = valor;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public DetalleOrdenPagoProveedor getDetalleOrdenPagoProveedor()
/* 110:    */   {
/* 111:194 */     return this.detalleOrdenPagoProveedor;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setDetalleOrdenPagoProveedor(DetalleOrdenPagoProveedor detalleOrdenPagoProveedor)
/* 115:    */   {
/* 116:198 */     this.detalleOrdenPagoProveedor = detalleOrdenPagoProveedor;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePago
 * JD-Core Version:    0.7.0.1
 */