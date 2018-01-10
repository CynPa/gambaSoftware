/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.math.RoundingMode;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="detalle_ajuste_prefactura_cliente")
/*  28:    */ public class DetalleAjustePrefacturaCliente
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -6431072583386991592L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="detalle_ajuste_prefactura_cliente", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_ajuste_prefactura_cliente")
/*  36:    */   @Column(name="id_detalle_ajuste_prefactura_cliente")
/*  37:    */   private int idDetalleAjustePrefacturaCliente;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private int idSucursal;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_ajuste_prefactura_cliente", nullable=true)
/*  46:    */   private AjustePrefacturaCliente ajustePrefacturaCliente;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_producto", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private Producto producto;
/*  51:    */   @Column(name="cantidad", nullable=false, precision=12, scale=4)
/*  52:    */   @Digits(integer=10, fraction=4)
/*  53:    */   @Min(0L)
/*  54: 72 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  55:    */   @Column(name="precio", nullable=false, precision=12, scale=6)
/*  56:    */   @NotNull
/*  57:    */   @Digits(integer=12, fraction=6)
/*  58:    */   @Min(0L)
/*  59: 77 */   private BigDecimal precio = BigDecimal.ZERO;
/*  60:    */   @Column(name="descuento", nullable=true, precision=12, scale=4)
/*  61:    */   @Digits(integer=12, fraction=4)
/*  62:    */   @Min(0L)
/*  63: 83 */   private BigDecimal descuento = BigDecimal.ZERO;
/*  64:    */   @Column(name="porcentaje_descuento", nullable=true, precision=5, scale=2)
/*  65:    */   @Digits(integer=3, fraction=2)
/*  66:    */   @Min(0L)
/*  67: 88 */   private BigDecimal porcentajeDescuento = BigDecimal.ZERO;
/*  68:    */   @Column(name="indicador_impuesto", nullable=false)
/*  69:    */   @NotNull
/*  70:    */   private boolean indicadorImpuesto;
/*  71:    */   @Column(name="descripcion", nullable=true, length=500)
/*  72:    */   @Size(max=500)
/*  73:    */   private String descripcion;
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id_unidad_venta", nullable=false)
/*  76:    */   private Unidad unidadVenta;
/*  77:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleAjustePrefacturaCliente")
/*  78:105 */   private List<ImpuestoProductoPrefacturaCliente> listaImpuestoProductoPrefacturaCliente = new ArrayList();
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_detalle_despacho_cliente", nullable=true)
/*  81:    */   private DetalleDespachoCliente detalleDespachoCliente;
/*  82:    */   
/*  83:    */   public BigDecimal getPrecioLinea()
/*  84:    */   {
/*  85:118 */     return FuncionesUtiles.redondearBigDecimal(getPrecio().multiply(getCantidad()));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public BigDecimal getDescuentoLinea()
/*  89:    */   {
/*  90:127 */     return getDescuento().multiply(this.cantidad).setScale(2, RoundingMode.HALF_UP);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public BigDecimal getValorImpuestosLinea()
/*  94:    */   {
/*  95:138 */     BigDecimal valorImpuestosLinea = BigDecimal.ZERO;
/*  96:140 */     for (ImpuestoProductoPrefacturaCliente ippp : getListaImpuestoProductoPrefacturaCliente()) {
/*  97:141 */       if (!ippp.isEliminado()) {
/*  98:142 */         valorImpuestosLinea = valorImpuestosLinea.add(ippp.getImpuestoProducto());
/*  99:    */       }
/* 100:    */     }
/* 101:146 */     return valorImpuestosLinea;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getId()
/* 105:    */   {
/* 106:156 */     return this.idDetalleAjustePrefacturaCliente;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getIdDetalleAjustePrefacturaCliente()
/* 110:    */   {
/* 111:165 */     return this.idDetalleAjustePrefacturaCliente;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdDetalleAjustePrefacturaCliente(int idDetalleAjustePrefacturaCliente)
/* 115:    */   {
/* 116:175 */     this.idDetalleAjustePrefacturaCliente = idDetalleAjustePrefacturaCliente;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getIdOrganizacion()
/* 120:    */   {
/* 121:184 */     return this.idOrganizacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setIdOrganizacion(int idOrganizacion)
/* 125:    */   {
/* 126:194 */     this.idOrganizacion = idOrganizacion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getIdSucursal()
/* 130:    */   {
/* 131:203 */     return this.idSucursal;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIdSucursal(int idSucursal)
/* 135:    */   {
/* 136:213 */     this.idSucursal = idSucursal;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public AjustePrefacturaCliente getAjustePrefacturaCliente()
/* 140:    */   {
/* 141:222 */     return this.ajustePrefacturaCliente;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setAjustePrefacturaCliente(AjustePrefacturaCliente ajustePrefacturaCliente)
/* 145:    */   {
/* 146:232 */     this.ajustePrefacturaCliente = ajustePrefacturaCliente;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Producto getProducto()
/* 150:    */   {
/* 151:241 */     return this.producto;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setProducto(Producto producto)
/* 155:    */   {
/* 156:251 */     this.producto = producto;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public BigDecimal getCantidad()
/* 160:    */   {
/* 161:260 */     return this.cantidad;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setCantidad(BigDecimal cantidad)
/* 165:    */   {
/* 166:270 */     this.cantidad = cantidad;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public BigDecimal getPrecio()
/* 170:    */   {
/* 171:279 */     return FuncionesUtiles.redondearBigDecimal(this.precio, ParametrosSistema.getNumeroDecimalesPrecioVenta(this.idOrganizacion).intValue());
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setPrecio(BigDecimal precio)
/* 175:    */   {
/* 176:289 */     this.precio = precio;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public BigDecimal getDescuento()
/* 180:    */   {
/* 181:298 */     return this.descuento;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setDescuento(BigDecimal descuento)
/* 185:    */   {
/* 186:308 */     this.descuento = descuento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public BigDecimal getPorcentajeDescuento()
/* 190:    */   {
/* 191:317 */     return this.porcentajeDescuento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setPorcentajeDescuento(BigDecimal porcentajeDescuento)
/* 195:    */   {
/* 196:327 */     this.porcentajeDescuento = porcentajeDescuento;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isIndicadorImpuesto()
/* 200:    */   {
/* 201:336 */     return this.indicadorImpuesto;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setIndicadorImpuesto(boolean indicadorImpuesto)
/* 205:    */   {
/* 206:346 */     this.indicadorImpuesto = indicadorImpuesto;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String getDescripcion()
/* 210:    */   {
/* 211:355 */     return this.descripcion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setDescripcion(String descripcion)
/* 215:    */   {
/* 216:365 */     this.descripcion = descripcion;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Unidad getUnidadVenta()
/* 220:    */   {
/* 221:374 */     return this.unidadVenta;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setUnidadVenta(Unidad unidadVenta)
/* 225:    */   {
/* 226:384 */     this.unidadVenta = unidadVenta;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public List<ImpuestoProductoPrefacturaCliente> getListaImpuestoProductoPrefacturaCliente()
/* 230:    */   {
/* 231:393 */     return this.listaImpuestoProductoPrefacturaCliente;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setListaImpuestoProductoPrefacturaCliente(List<ImpuestoProductoPrefacturaCliente> listaImpuestoProductoPrefacturaCliente)
/* 235:    */   {
/* 236:403 */     this.listaImpuestoProductoPrefacturaCliente = listaImpuestoProductoPrefacturaCliente;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public BigDecimal getBaseImponible()
/* 240:    */   {
/* 241:414 */     BigDecimal baseImponibleLinea = getPrecio().subtract(getDescuento()).multiply(getCantidad());
/* 242:    */     
/* 243:416 */     return baseImponibleLinea;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public DetalleDespachoCliente getDetalleDespachoCliente()
/* 247:    */   {
/* 248:425 */     return this.detalleDespachoCliente;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setDetalleDespachoCliente(DetalleDespachoCliente detalleDespachoCliente)
/* 252:    */   {
/* 253:435 */     this.detalleDespachoCliente = detalleDespachoCliente;
/* 254:    */   }
/* 255:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleAjustePrefacturaCliente
 * JD-Core Version:    0.7.0.1
 */