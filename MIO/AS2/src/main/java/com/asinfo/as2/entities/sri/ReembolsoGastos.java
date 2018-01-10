/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   5:    */ import java.math.BigDecimal;
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
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Pattern;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ import org.hibernate.annotations.ColumnDefault;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="reembolso_gastos")
/*  30:    */ public class ReembolsoGastos
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="reembolso_gastos", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="reembolso_gastos")
/*  37:    */   @Column(name="id_reembolso_gastos")
/*  38:    */   private int idReembolsoGastos;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   private int idSucursal;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_factura_proveedorSRI", nullable=true)
/*  45:    */   private FacturaProveedorSRI facturaProveedorSRI;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_tipo_identificacion", nullable=true)
/*  48:    */   private TipoIdentificacion tipoIdentificacion;
/*  49:    */   @Column(name="identificacion_proveedor", length=20, nullable=true)
/*  50:    */   @Size(min=1, max=20)
/*  51:    */   private String identificacionProveedor;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_tipo_comprobante", nullable=true)
/*  54:    */   private TipoComprobanteSRI tipoComprobanteSRI;
/*  55:    */   @Column(name="establecimiento", length=3, nullable=false)
/*  56:    */   @NotNull
/*  57:    */   @Size(min=3, max=3)
/*  58:    */   @Pattern(regexp="(\\d+)", message="registrar solo numeros")
/*  59:    */   private String establecimiento;
/*  60:    */   @Column(name="punto_emision", length=3, nullable=false)
/*  61:    */   @NotNull
/*  62:    */   @Size(min=3, max=3)
/*  63:    */   @Pattern(regexp="(\\d+)", message="registrar solo numeros")
/*  64:    */   private String puntoEmision;
/*  65:    */   @Column(name="numero", length=20, nullable=false)
/*  66:    */   @NotNull
/*  67:    */   @Size(min=1, max=20)
/*  68:    */   @Pattern(regexp="(\\d+)", message="registrar solo numeros")
/*  69:    */   private String numero;
/*  70:    */   @Column(name="autorizacion", length=50, nullable=true)
/*  71:    */   @Size(min=10, max=50)
/*  72:    */   @Pattern(regexp="(\\d+)", message="registrar solo numeros")
/*  73:    */   private String autorizacion;
/*  74:    */   @Temporal(TemporalType.DATE)
/*  75:    */   @Column(name="fecha_emision", nullable=true)
/*  76:    */   private Date fechaEmision;
/*  77:    */   @Column(name="base_imponible_tarifa_cero", precision=12, scale=2, nullable=false)
/*  78:    */   @Digits(integer=12, fraction=2)
/*  79:    */   @Min(0L)
/*  80:108 */   private BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/*  81:    */   @Column(name="base_imponible_diferente_cero", precision=12, scale=2, nullable=false)
/*  82:    */   @Digits(integer=12, fraction=2)
/*  83:    */   @Min(0L)
/*  84:113 */   private BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  85:    */   @Column(name="base_imponible_no_objeto_iva", precision=12, scale=2, nullable=false)
/*  86:    */   @Digits(integer=12, fraction=2)
/*  87:    */   @Min(0L)
/*  88:118 */   private BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  89:    */   @Column(name="base_exenta_iva", precision=12, scale=2, nullable=false)
/*  90:    */   @Digits(integer=12, fraction=2)
/*  91:    */   @Min(0L)
/*  92:123 */   private BigDecimal baseExentaIva = BigDecimal.ZERO;
/*  93:    */   @Column(name="monto_ice", precision=12, scale=2, nullable=false)
/*  94:    */   @Digits(integer=12, fraction=2)
/*  95:    */   @Min(0L)
/*  96:128 */   private BigDecimal montoIce = BigDecimal.ZERO;
/*  97:    */   @Column(name="monto_iva", precision=12, scale=2, nullable=false)
/*  98:    */   @Digits(integer=12, fraction=2)
/*  99:    */   @Min(0L)
/* 100:133 */   private BigDecimal montoIva = BigDecimal.ZERO;
/* 101:    */   @Column(name="descuento_impuesto", nullable=false, precision=12, scale=2)
/* 102:    */   @NotNull
/* 103:    */   @Digits(integer=12, fraction=2)
/* 104:    */   @Min(0L)
/* 105:    */   @ColumnDefault("0")
/* 106:138 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/* 107:    */   @Transient
/* 108:    */   private List<TipoComprobanteSRI> listaTipoComprobanteSRI;
/* 109:    */   
/* 110:    */   public int getId()
/* 111:    */   {
/* 112:153 */     return this.idReembolsoGastos;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getIdReembolsoGastos()
/* 116:    */   {
/* 117:157 */     return this.idReembolsoGastos;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setIdReembolsoGastos(int idReembolsoGastos)
/* 121:    */   {
/* 122:161 */     this.idReembolsoGastos = idReembolsoGastos;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getIdOrganizacion()
/* 126:    */   {
/* 127:165 */     return this.idOrganizacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setIdOrganizacion(int idOrganizacion)
/* 131:    */   {
/* 132:169 */     this.idOrganizacion = idOrganizacion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getIdSucursal()
/* 136:    */   {
/* 137:173 */     return this.idSucursal;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setIdSucursal(int idSucursal)
/* 141:    */   {
/* 142:177 */     this.idSucursal = idSucursal;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public FacturaProveedorSRI getFacturaProveedorSRI()
/* 146:    */   {
/* 147:181 */     return this.facturaProveedorSRI;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/* 151:    */   {
/* 152:185 */     this.facturaProveedorSRI = facturaProveedorSRI;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public TipoIdentificacion getTipoIdentificacion()
/* 156:    */   {
/* 157:189 */     return this.tipoIdentificacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/* 161:    */   {
/* 162:193 */     this.tipoIdentificacion = tipoIdentificacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getIdentificacionProveedor()
/* 166:    */   {
/* 167:197 */     return this.identificacionProveedor;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setIdentificacionProveedor(String identificacionProveedor)
/* 171:    */   {
/* 172:201 */     this.identificacionProveedor = identificacionProveedor;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public TipoComprobanteSRI getTipoComprobanteSRI()
/* 176:    */   {
/* 177:205 */     return this.tipoComprobanteSRI;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/* 181:    */   {
/* 182:209 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getEstablecimiento()
/* 186:    */   {
/* 187:213 */     return this.establecimiento;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setEstablecimiento(String establecimiento)
/* 191:    */   {
/* 192:217 */     this.establecimiento = establecimiento;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getPuntoEmision()
/* 196:    */   {
/* 197:221 */     return this.puntoEmision;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setPuntoEmision(String puntoEmision)
/* 201:    */   {
/* 202:225 */     this.puntoEmision = puntoEmision;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getNumero()
/* 206:    */   {
/* 207:229 */     return this.numero;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setNumero(String numero)
/* 211:    */   {
/* 212:233 */     this.numero = numero;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getAutorizacion()
/* 216:    */   {
/* 217:237 */     return this.autorizacion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setAutorizacion(String autorizacion)
/* 221:    */   {
/* 222:241 */     this.autorizacion = autorizacion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Date getFechaEmision()
/* 226:    */   {
/* 227:245 */     return this.fechaEmision;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setFechaEmision(Date fechaEmision)
/* 231:    */   {
/* 232:249 */     this.fechaEmision = fechaEmision;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public BigDecimal getBaseImponibleTarifaCero()
/* 236:    */   {
/* 237:253 */     return this.baseImponibleTarifaCero;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setBaseImponibleTarifaCero(BigDecimal baseImponibleTarifaCero)
/* 241:    */   {
/* 242:257 */     this.baseImponibleTarifaCero = baseImponibleTarifaCero;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public BigDecimal getBaseImponibleDiferenteCero()
/* 246:    */   {
/* 247:261 */     return this.baseImponibleDiferenteCero;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setBaseImponibleDiferenteCero(BigDecimal baseImponibleDiferenteCero)
/* 251:    */   {
/* 252:265 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public BigDecimal getBaseImponibleNoObjetoIva()
/* 256:    */   {
/* 257:269 */     return this.baseImponibleNoObjetoIva;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setBaseImponibleNoObjetoIva(BigDecimal baseImponibleNoObjetoIva)
/* 261:    */   {
/* 262:273 */     this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public BigDecimal getBaseExentaIva()
/* 266:    */   {
/* 267:277 */     return this.baseExentaIva;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setBaseExentaIva(BigDecimal baseExentaIva)
/* 271:    */   {
/* 272:281 */     this.baseExentaIva = baseExentaIva;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public BigDecimal getMontoIce()
/* 276:    */   {
/* 277:285 */     return this.montoIce;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setMontoIce(BigDecimal montoIce)
/* 281:    */   {
/* 282:289 */     this.montoIce = montoIce;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public BigDecimal getMontoIva()
/* 286:    */   {
/* 287:293 */     return this.montoIva;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setMontoIva(BigDecimal montoIva)
/* 291:    */   {
/* 292:297 */     this.montoIva = montoIva;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List<TipoComprobanteSRI> getListaTipoComprobanteSRI()
/* 296:    */   {
/* 297:304 */     return this.listaTipoComprobanteSRI;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setListaTipoComprobanteSRI(List<TipoComprobanteSRI> listaTipoComprobanteSRI)
/* 301:    */   {
/* 302:312 */     this.listaTipoComprobanteSRI = listaTipoComprobanteSRI;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public BigDecimal getDescuentoImpuesto()
/* 306:    */   {
/* 307:316 */     return this.descuentoImpuesto;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/* 311:    */   {
/* 312:320 */     this.descuentoImpuesto = descuentoImpuesto;
/* 313:    */   }
/* 314:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.ReembolsoGastos
 * JD-Core Version:    0.7.0.1
 */