/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_factura_proveedor_importacion")
/*  23:    */ public class DetalleFacturaProveedorImportacion
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable, Cloneable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="detalle_factura_proveedor_importacion", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_proveedor_importacion")
/*  31:    */   @Column(name="id_detalle_factura_proveedor_importacion")
/*  32:    */   private int idDetalleFacturaProveedorImportacion;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*  39:    */   private FacturaProveedor facturaProveedor;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_detalle_factura_proveedor", nullable=true)
/*  42:    */   private DetalleFacturaProveedor detalleFacturaProveedor;
/*  43:    */   @Column(name="valor", nullable=true, precision=12, scale=2)
/*  44:    */   @Digits(integer=18, fraction=6)
/*  45:    */   @Min(0L)
/*  46: 57 */   private BigDecimal valor = BigDecimal.ZERO;
/*  47:    */   @Column(name="indicador_distribucion_manual", nullable=true)
/*  48:    */   private boolean indicadorDistribucionManual;
/*  49:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleFacturaProveedorImportacion")
/*  50: 65 */   private List<DetalleFacturaProveedorImportacionProducto> listaDetalleFacturaProveedorImportacionProducto = new ArrayList();
/*  51:    */   
/*  52:    */   public int getId()
/*  53:    */   {
/*  54: 81 */     return this.idDetalleFacturaProveedorImportacion;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdDetalleFacturaProveedorImportacion()
/*  58:    */   {
/*  59: 90 */     return this.idDetalleFacturaProveedorImportacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdDetalleFacturaProveedorImportacion(int idDetalleFacturaProveedorImportacion)
/*  63:    */   {
/*  64:101 */     this.idDetalleFacturaProveedorImportacion = idDetalleFacturaProveedorImportacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdOrganizacion()
/*  68:    */   {
/*  69:110 */     return this.idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdOrganizacion(int idOrganizacion)
/*  73:    */   {
/*  74:120 */     this.idOrganizacion = idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdSucursal()
/*  78:    */   {
/*  79:129 */     return this.idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdSucursal(int idSucursal)
/*  83:    */   {
/*  84:139 */     this.idSucursal = idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public FacturaProveedor getFacturaProveedor()
/*  88:    */   {
/*  89:148 */     return this.facturaProveedor;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/*  93:    */   {
/*  94:158 */     this.facturaProveedor = facturaProveedor;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public DetalleFacturaProveedor getDetalleFacturaProveedor()
/*  98:    */   {
/*  99:167 */     return this.detalleFacturaProveedor;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDetalleFacturaProveedor(DetalleFacturaProveedor detalleFacturaProveedor)
/* 103:    */   {
/* 104:177 */     this.detalleFacturaProveedor = detalleFacturaProveedor;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BigDecimal getValor()
/* 108:    */   {
/* 109:186 */     return this.valor;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setValor(BigDecimal valor)
/* 113:    */   {
/* 114:196 */     this.valor = valor;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isIndicadorDistribucionManual()
/* 118:    */   {
/* 119:200 */     return this.indicadorDistribucionManual;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIndicadorDistribucionManual(boolean indicadorDistribucionManual)
/* 123:    */   {
/* 124:204 */     this.indicadorDistribucionManual = indicadorDistribucionManual;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<DetalleFacturaProveedorImportacionProducto> getListaDetalleFacturaProveedorImportacionProducto()
/* 128:    */   {
/* 129:208 */     return this.listaDetalleFacturaProveedorImportacionProducto;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaDetalleFacturaProveedorImportacionProducto(List<DetalleFacturaProveedorImportacionProducto> listaDetalleFacturaProveedorImportacionProducto)
/* 133:    */   {
/* 134:213 */     this.listaDetalleFacturaProveedorImportacionProducto = listaDetalleFacturaProveedorImportacionProducto;
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFacturaProveedorImportacion
 * JD-Core Version:    0.7.0.1
 */