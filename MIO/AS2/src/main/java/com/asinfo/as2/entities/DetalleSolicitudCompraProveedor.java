/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
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
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import org.hibernate.annotations.ColumnDefault;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_solicitud_compra_proveedor")
/*  22:    */ public class DetalleSolicitudCompraProveedor
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_solicitud_compra_proveedor", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_solicitud_compra_proveedor")
/*  29:    */   @Column(name="id_detalle_solicitud_compra_proveedor")
/*  30:    */   private int idDetalleSolicitudCompraProveedor;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private int idSucursal;
/*  37:    */   @NotNull
/*  38:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  39:    */   @Digits(integer=12, fraction=2)
/*  40: 48 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  41:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  42:    */   @NotNull
/*  43:    */   @Digits(integer=12, fraction=6)
/*  44:    */   @Min(0L)
/*  45: 53 */   private BigDecimal precio = BigDecimal.ZERO;
/*  46:    */   @Column(name="indicador_en_pedido", nullable=false)
/*  47:    */   private boolean indicadorEnPedido;
/*  48:    */   @Column(name="total", nullable=false, precision=12, scale=6)
/*  49:    */   @NotNull
/*  50:    */   @Digits(integer=12, fraction=6)
/*  51:    */   @Min(0L)
/*  52:    */   @ColumnDefault("0")
/*  53: 62 */   private BigDecimal total = BigDecimal.ZERO;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_solicitud_compra", nullable=true)
/*  56:    */   private SolicitudCompra solicitudCompra;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  59:    */   private Empresa empresa;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_producto", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private Producto producto;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_unidad_compra", nullable=false)
/*  66:    */   private Unidad unidadCompra;
/*  67:    */   
/*  68:    */   public int getId()
/*  69:    */   {
/*  70: 92 */     return this.idDetalleSolicitudCompraProveedor;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdDetalleSolicitudCompraProveedor()
/*  74:    */   {
/*  75: 96 */     return this.idDetalleSolicitudCompraProveedor;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdDetalleSolicitudCompraProveedor(int idDetalleSolicitudCompraProveedor)
/*  79:    */   {
/*  80:100 */     this.idDetalleSolicitudCompraProveedor = idDetalleSolicitudCompraProveedor;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85:104 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90:108 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdSucursal()
/*  94:    */   {
/*  95:112 */     return this.idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdSucursal(int idSucursal)
/*  99:    */   {
/* 100:116 */     this.idSucursal = idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public SolicitudCompra getSolicitudCompra()
/* 104:    */   {
/* 105:120 */     return this.solicitudCompra;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setSolicitudCompra(SolicitudCompra solicitudCompra)
/* 109:    */   {
/* 110:124 */     this.solicitudCompra = solicitudCompra;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Empresa getEmpresa()
/* 114:    */   {
/* 115:128 */     return this.empresa;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setEmpresa(Empresa empresa)
/* 119:    */   {
/* 120:132 */     this.empresa = empresa;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public BigDecimal getCantidad()
/* 124:    */   {
/* 125:136 */     return this.cantidad;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setCantidad(BigDecimal cantidad)
/* 129:    */   {
/* 130:140 */     if (!isIndicadorEnPedido())
/* 131:    */     {
/* 132:141 */       this.cantidad = cantidad;
/* 133:142 */       this.total = FuncionesUtiles.redondearBigDecimal(this.cantidad.multiply(this.precio));
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public BigDecimal getPrecio()
/* 138:    */   {
/* 139:147 */     return this.precio;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setPrecio(BigDecimal precio)
/* 143:    */   {
/* 144:151 */     this.precio = precio;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Producto getProducto()
/* 148:    */   {
/* 149:155 */     return this.producto;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setProducto(Producto producto)
/* 153:    */   {
/* 154:159 */     this.producto = producto;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorEnPedido()
/* 158:    */   {
/* 159:163 */     return this.indicadorEnPedido;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorEnPedido(boolean indicadorEnPedido)
/* 163:    */   {
/* 164:167 */     this.indicadorEnPedido = indicadorEnPedido;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Unidad getUnidadCompra()
/* 168:    */   {
/* 169:171 */     return this.unidadCompra;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setUnidadCompra(Unidad unidadCompra)
/* 173:    */   {
/* 174:175 */     this.unidadCompra = unidadCompra;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public BigDecimal getTotal()
/* 178:    */   {
/* 179:179 */     return this.total;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setTotal(BigDecimal total)
/* 183:    */   {
/* 184:183 */     this.total = total;
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleSolicitudCompraProveedor
 * JD-Core Version:    0.7.0.1
 */