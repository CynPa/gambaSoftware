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
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="detalle_factura_proveedor_importacion_producto")
/*  20:    */ public class DetalleFacturaProveedorImportacionProducto
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable, Cloneable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_factura_proveedor_importacion_producto", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_proveedor_importacion_producto")
/*  28:    */   @Column(name="id_detalle_factura_proveedor_importacion_producto")
/*  29:    */   private int idDetalleFacturaProveedorImportacionProducto;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_detalle_factura_proveedor_importacion", nullable=true)
/*  36:    */   private DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacion;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_detalle_factura_proveedor", nullable=true)
/*  39:    */   private DetalleFacturaProveedor detalleFacturaProveedor;
/*  40:    */   @Column(name="valor", nullable=true, precision=12, scale=2)
/*  41:    */   @Digits(integer=18, fraction=6)
/*  42:    */   @Min(0L)
/*  43: 54 */   private BigDecimal valor = BigDecimal.ZERO;
/*  44:    */   
/*  45:    */   public int getId()
/*  46:    */   {
/*  47: 72 */     return this.idDetalleFacturaProveedorImportacionProducto;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdDetalleFacturaProveedorImportacion()
/*  51:    */   {
/*  52: 81 */     return this.idDetalleFacturaProveedorImportacionProducto;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdDetalleFacturaProveedorImportacion(int idDetalleFacturaProveedorImportacion)
/*  56:    */   {
/*  57: 92 */     this.idDetalleFacturaProveedorImportacionProducto = idDetalleFacturaProveedorImportacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdOrganizacion()
/*  61:    */   {
/*  62:101 */     return this.idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdOrganizacion(int idOrganizacion)
/*  66:    */   {
/*  67:111 */     this.idOrganizacion = idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdSucursal()
/*  71:    */   {
/*  72:120 */     return this.idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdSucursal(int idSucursal)
/*  76:    */   {
/*  77:130 */     this.idSucursal = idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public DetalleFacturaProveedor getDetalleFacturaProveedor()
/*  81:    */   {
/*  82:139 */     return this.detalleFacturaProveedor;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setDetalleFacturaProveedor(DetalleFacturaProveedor detalleFacturaProveedor)
/*  86:    */   {
/*  87:149 */     this.detalleFacturaProveedor = detalleFacturaProveedor;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public BigDecimal getValor()
/*  91:    */   {
/*  92:158 */     return this.valor;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setValor(BigDecimal valor)
/*  96:    */   {
/*  97:168 */     this.valor = valor;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdDetalleFacturaProveedorImportacionProducto()
/* 101:    */   {
/* 102:172 */     return this.idDetalleFacturaProveedorImportacionProducto;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdDetalleFacturaProveedorImportacionProducto(int idDetalleFacturaProveedorImportacionProducto)
/* 106:    */   {
/* 107:176 */     this.idDetalleFacturaProveedorImportacionProducto = idDetalleFacturaProveedorImportacionProducto;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public DetalleFacturaProveedorImportacion getDetalleFacturaProveedorImportacion()
/* 111:    */   {
/* 112:180 */     return this.detalleFacturaProveedorImportacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDetalleFacturaProveedorImportacion(DetalleFacturaProveedorImportacion detalleFacturaProveedorImportacion)
/* 116:    */   {
/* 117:184 */     this.detalleFacturaProveedorImportacion = detalleFacturaProveedorImportacion;
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFacturaProveedorImportacionProducto
 * JD-Core Version:    0.7.0.1
 */