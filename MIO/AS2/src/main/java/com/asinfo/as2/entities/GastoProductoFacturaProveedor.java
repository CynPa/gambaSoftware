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
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="gasto_producto_factura_proveedor", indexes={@javax.persistence.Index(columnList="id_detalle_factura_proveedor")})
/*  18:    */ public class GastoProductoFacturaProveedor
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="gasto_producto_factura_proveedor", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="gasto_producto_factura_proveedor")
/*  25:    */   @Column(name="id_gasto_producto_factura_proveedor")
/*  26:    */   private int idGastoProductoFacturaProveedor;
/*  27:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  28:    */   @JoinColumn(name="id_detalle_factura_proveedor", nullable=true)
/*  29:    */   private DetalleFacturaProveedor detalleFacturaProveedor;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_cuenta_contable_gasto", nullable=false)
/*  32:    */   private CuentaContable cuentaContableGasto;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_dimension_contable1")
/*  35:    */   private DimensionContable dimensionContable1;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_dimension_contable2")
/*  38:    */   private DimensionContable dimensionContable2;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_dimension_contable3")
/*  41:    */   private DimensionContable dimensionContable3;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_dimension_contable4")
/*  44:    */   private DimensionContable dimensionContable4;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_dimension_contable5")
/*  47:    */   private DimensionContable dimensionContable5;
/*  48:    */   @Column(name="valor", nullable=true, precision=12, scale=2)
/*  49:    */   @Min(0L)
/*  50: 77 */   private BigDecimal valor = BigDecimal.ZERO;
/*  51:    */   
/*  52:    */   public int getId()
/*  53:    */   {
/*  54: 91 */     return this.idGastoProductoFacturaProveedor;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setId(int idGastoProductoFacturaProveedor)
/*  58:    */   {
/*  59: 96 */     this.idGastoProductoFacturaProveedor = idGastoProductoFacturaProveedor;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdGastoProductoFacturaProveedor()
/*  63:    */   {
/*  64:105 */     return this.idGastoProductoFacturaProveedor;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdGastoProductoFacturaProveedor(int idGastoProductoFacturaProveedor)
/*  68:    */   {
/*  69:115 */     this.idGastoProductoFacturaProveedor = idGastoProductoFacturaProveedor;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public DetalleFacturaProveedor getDetalleFacturaProveedor()
/*  73:    */   {
/*  74:124 */     return this.detalleFacturaProveedor;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setDetalleFacturaProveedor(DetalleFacturaProveedor detalleFacturaProveedor)
/*  78:    */   {
/*  79:134 */     this.detalleFacturaProveedor = detalleFacturaProveedor;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public BigDecimal getValor()
/*  83:    */   {
/*  84:143 */     return this.valor;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setValor(BigDecimal valor)
/*  88:    */   {
/*  89:153 */     this.valor = valor;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public CuentaContable getCuentaContableGasto()
/*  93:    */   {
/*  94:157 */     return this.cuentaContableGasto;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setCuentaContableGasto(CuentaContable cuentaContableGasto)
/*  98:    */   {
/*  99:161 */     this.cuentaContableGasto = cuentaContableGasto;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public DimensionContable getDimensionContable1()
/* 103:    */   {
/* 104:165 */     return this.dimensionContable1;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/* 108:    */   {
/* 109:169 */     this.dimensionContable1 = dimensionContable1;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public DimensionContable getDimensionContable2()
/* 113:    */   {
/* 114:173 */     return this.dimensionContable2;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 118:    */   {
/* 119:177 */     this.dimensionContable2 = dimensionContable2;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public DimensionContable getDimensionContable3()
/* 123:    */   {
/* 124:181 */     return this.dimensionContable3;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 128:    */   {
/* 129:185 */     this.dimensionContable3 = dimensionContable3;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public DimensionContable getDimensionContable4()
/* 133:    */   {
/* 134:189 */     return this.dimensionContable4;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 138:    */   {
/* 139:193 */     this.dimensionContable4 = dimensionContable4;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public DimensionContable getDimensionContable5()
/* 143:    */   {
/* 144:197 */     return this.dimensionContable5;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 148:    */   {
/* 149:201 */     this.dimensionContable5 = dimensionContable5;
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GastoProductoFacturaProveedor
 * JD-Core Version:    0.7.0.1
 */