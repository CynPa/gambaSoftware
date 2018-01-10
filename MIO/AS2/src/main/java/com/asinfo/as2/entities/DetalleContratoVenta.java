/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_contrato_venta")
/*  27:    */ public class DetalleContratoVenta
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_contrato_venta", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_contrato_venta")
/*  35:    */   @Column(name="id_detalle_contrato_venta")
/*  36:    */   private int idDetalleContratoVenta;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private int idSucursal;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private int idOrganizacion;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_contrato_venta", nullable=true)
/*  45:    */   private ContratoVenta contratoVenta;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_producto", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Producto producto;
/*  50:    */   @Column(name="cantidad", nullable=true, precision=12, scale=2)
/*  51:    */   @Digits(integer=12, fraction=2)
/*  52: 63 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  53:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  54:    */   @NotNull
/*  55:    */   @Digits(integer=12, fraction=6)
/*  56:    */   @Min(0L)
/*  57: 67 */   private BigDecimal precio = BigDecimal.ZERO;
/*  58:    */   @Column(name="precio_linea", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   @Digits(integer=10, fraction=8)
/*  61:    */   @Min(0L)
/*  62: 73 */   private BigDecimal precioLinea = BigDecimal.ZERO;
/*  63:    */   @Temporal(TemporalType.DATE)
/*  64:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  65:    */   @NotNull
/*  66:    */   private Date fechaDesde;
/*  67:    */   @Temporal(TemporalType.DATE)
/*  68:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  69:    */   @NotNull
/*  70:    */   private Date fechaHasta;
/*  71:    */   @OneToMany(mappedBy="detalleContratoVenta", fetch=FetchType.LAZY)
/*  72: 89 */   private List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta = new ArrayList();
/*  73:    */   
/*  74:    */   public int getId()
/*  75:    */   {
/*  76: 97 */     return this.idDetalleContratoVenta;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdDetalleContratoVenta()
/*  80:    */   {
/*  81:101 */     return this.idDetalleContratoVenta;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdDetalleContratoVenta(int idDetalleContratoVenta)
/*  85:    */   {
/*  86:105 */     this.idDetalleContratoVenta = idDetalleContratoVenta;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:109 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:113 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdOrganizacion()
/* 100:    */   {
/* 101:117 */     return this.idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdOrganizacion(int idOrganizacion)
/* 105:    */   {
/* 106:121 */     this.idOrganizacion = idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public ContratoVenta getContratoVenta()
/* 110:    */   {
/* 111:125 */     return this.contratoVenta;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setContratoVenta(ContratoVenta contratoVenta)
/* 115:    */   {
/* 116:129 */     this.contratoVenta = contratoVenta;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public BigDecimal getCantidad()
/* 120:    */   {
/* 121:133 */     return this.cantidad;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setCantidad(BigDecimal cantidad)
/* 125:    */   {
/* 126:137 */     this.cantidad = cantidad;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public BigDecimal getPrecio()
/* 130:    */   {
/* 131:141 */     return this.precio;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setPrecio(BigDecimal precio)
/* 135:    */   {
/* 136:145 */     this.precio = precio;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public BigDecimal getPrecioLinea()
/* 140:    */   {
/* 141:149 */     return this.precioLinea;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPrecioLinea(BigDecimal precioLinea)
/* 145:    */   {
/* 146:153 */     this.precioLinea = precioLinea;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Date getFechaDesde()
/* 150:    */   {
/* 151:157 */     return this.fechaDesde;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFechaDesde(Date fechaDesde)
/* 155:    */   {
/* 156:161 */     this.fechaDesde = fechaDesde;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Date getFechaHasta()
/* 160:    */   {
/* 161:165 */     return this.fechaHasta;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setFechaHasta(Date fechaHasta)
/* 165:    */   {
/* 166:169 */     this.fechaHasta = fechaHasta;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Producto getProducto()
/* 170:    */   {
/* 171:173 */     return this.producto;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setProducto(Producto producto)
/* 175:    */   {
/* 176:177 */     this.producto = producto;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean equals(Object obj)
/* 180:    */   {
/* 181:182 */     if (this.producto != null) {
/* 182:183 */       return (obj != null) && (super.equals(obj)) && (((DetalleContratoVenta)obj).getId() == getId()) && (this.producto.equals(((DetalleContratoVenta)obj).getProducto()));
/* 183:    */     }
/* 184:185 */     return (super.equals(obj)) && (((DetalleContratoVenta)obj).getId() == getId());
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<ContratoVentaFacturaContratoVenta> getListaContratoVentaFacturaContratoVenta()
/* 188:    */   {
/* 189:189 */     return this.listaContratoVentaFacturaContratoVenta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setListaContratoVentaFacturaContratoVenta(List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta)
/* 193:    */   {
/* 194:193 */     this.listaContratoVentaFacturaContratoVenta = listaContratoVentaFacturaContratoVenta;
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleContratoVenta
 * JD-Core Version:    0.7.0.1
 */