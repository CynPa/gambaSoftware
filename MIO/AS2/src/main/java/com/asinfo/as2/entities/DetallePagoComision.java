/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.FormaPagoComisionEnum;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="detalle_pago_comision")
/*  24:    */ public class DetallePagoComision
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="detalle_pago_comision", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pago_comision")
/*  31:    */   @Column(name="id_detalle_pago_comision")
/*  32:    */   private int idDetallePagoComision;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="base_comision", nullable=false, precision=12, scale=4)
/*  38:    */   @NotNull
/*  39:    */   @Digits(integer=12, fraction=4)
/*  40:    */   @Min(0L)
/*  41: 61 */   private BigDecimal baseComision = BigDecimal.ZERO;
/*  42:    */   @Column(name="forma_pago_comision_enum", nullable=false)
/*  43:    */   @Enumerated(EnumType.ORDINAL)
/*  44:    */   @NotNull
/*  45:    */   private FormaPagoComisionEnum formaPagoComisionEnum;
/*  46:    */   @Column(name="porcentaje_comision", nullable=false, precision=12, scale=2)
/*  47:    */   @NotNull
/*  48:    */   @Digits(integer=12, fraction=2)
/*  49:    */   @Min(0L)
/*  50: 72 */   private BigDecimal porcentajeComision = BigDecimal.ZERO;
/*  51:    */   @Column(name="valor_comision", nullable=false, precision=12, scale=2)
/*  52:    */   @NotNull
/*  53:    */   @Digits(integer=12, fraction=2)
/*  54:    */   @Min(0L)
/*  55: 78 */   private BigDecimal valorComision = BigDecimal.ZERO;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_pago_comision", nullable=true)
/*  58:    */   private PagoComision pagoComision;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_usuario", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   private EntidadUsuario agenteComercial;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_categoria_producto", nullable=true)
/*  65:    */   private CategoriaProducto categoriaProducto;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_subcategoria_producto", nullable=true)
/*  68:    */   private SubcategoriaProducto subcategoriaProducto;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_producto", nullable=true)
/*  71:    */   private Producto producto;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_rango_dias_comision", nullable=false)
/*  74:    */   @NotNull
/*  75:    */   private RangoDiasComision rangoDiasComision;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_factura_cliente", nullable=false)
/*  78:    */   @NotNull
/*  79:    */   private FacturaCliente facturaCliente;
/*  80:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  81:    */   @JoinColumn(name="id_detalle_cobro", nullable=false)
/*  82:    */   @NotNull
/*  83:    */   private DetalleCobro detalleCobro;
/*  84:    */   
/*  85:    */   public int getId()
/*  86:    */   {
/*  87:140 */     return this.idDetallePagoComision;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdDetallePagoComision()
/*  91:    */   {
/*  92:144 */     return this.idDetallePagoComision;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdDetallePagoComision(int idDetallePagoComision)
/*  96:    */   {
/*  97:148 */     this.idDetallePagoComision = idDetallePagoComision;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdOrganizacion()
/* 101:    */   {
/* 102:152 */     return this.idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdOrganizacion(int idOrganizacion)
/* 106:    */   {
/* 107:156 */     this.idOrganizacion = idOrganizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdSucursal()
/* 111:    */   {
/* 112:160 */     return this.idSucursal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdSucursal(int idSucursal)
/* 116:    */   {
/* 117:164 */     this.idSucursal = idSucursal;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public BigDecimal getBaseComision()
/* 121:    */   {
/* 122:168 */     return this.baseComision;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setBaseComision(BigDecimal baseComision)
/* 126:    */   {
/* 127:172 */     this.baseComision = baseComision;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public FormaPagoComisionEnum getFormaPagoComisionEnum()
/* 131:    */   {
/* 132:176 */     return this.formaPagoComisionEnum;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setFormaPagoComisionEnum(FormaPagoComisionEnum formaPagoComisionEnum)
/* 136:    */   {
/* 137:180 */     this.formaPagoComisionEnum = formaPagoComisionEnum;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BigDecimal getPorcentajeComision()
/* 141:    */   {
/* 142:184 */     return this.porcentajeComision;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPorcentajeComision(BigDecimal porcentajeComision)
/* 146:    */   {
/* 147:188 */     this.porcentajeComision = porcentajeComision;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getValorComision()
/* 151:    */   {
/* 152:192 */     return this.valorComision;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setValorComision(BigDecimal valorComision)
/* 156:    */   {
/* 157:196 */     this.valorComision = valorComision;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public PagoComision getPagoComision()
/* 161:    */   {
/* 162:200 */     return this.pagoComision;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setPagoComision(PagoComision pagoComision)
/* 166:    */   {
/* 167:204 */     this.pagoComision = pagoComision;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public EntidadUsuario getAgenteComercial()
/* 171:    */   {
/* 172:208 */     return this.agenteComercial;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 176:    */   {
/* 177:212 */     this.agenteComercial = agenteComercial;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public CategoriaProducto getCategoriaProducto()
/* 181:    */   {
/* 182:216 */     return this.categoriaProducto;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 186:    */   {
/* 187:220 */     this.categoriaProducto = categoriaProducto;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 191:    */   {
/* 192:224 */     return this.subcategoriaProducto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 196:    */   {
/* 197:228 */     this.subcategoriaProducto = subcategoriaProducto;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Producto getProducto()
/* 201:    */   {
/* 202:232 */     return this.producto;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setProducto(Producto producto)
/* 206:    */   {
/* 207:236 */     this.producto = producto;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public RangoDiasComision getRangoDiasComision()
/* 211:    */   {
/* 212:240 */     return this.rangoDiasComision;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setRangoDiasComision(RangoDiasComision rangoDiasComision)
/* 216:    */   {
/* 217:244 */     this.rangoDiasComision = rangoDiasComision;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public FacturaCliente getFacturaCliente()
/* 221:    */   {
/* 222:248 */     return this.facturaCliente;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 226:    */   {
/* 227:252 */     this.facturaCliente = facturaCliente;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public DetalleCobro getDetalleCobro()
/* 231:    */   {
/* 232:256 */     return this.detalleCobro;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDetalleCobro(DetalleCobro detalleCobro)
/* 236:    */   {
/* 237:260 */     this.detalleCobro = detalleCobro;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePagoComision
 * JD-Core Version:    0.7.0.1
 */