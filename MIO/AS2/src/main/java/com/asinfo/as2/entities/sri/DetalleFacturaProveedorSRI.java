/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="detalle_factura_proveedorSRI", indexes={@javax.persistence.Index(columnList="id_factura_proveedorSRI")})
/*  26:    */ public class DetalleFacturaProveedorSRI
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="detalle_factura_proveedorSRI", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_proveedorSRI")
/*  33:    */   @Column(name="id_detalle_factura_proveedorSRI")
/*  34:    */   private int idDetalleFacturaProveedorSRI;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_concepto_retencionSRI", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private ConceptoRetencionSRI conceptoRetencionSRI;
/*  39:    */   @Column(name="porcentaje_retencion", nullable=false, precision=12, scale=2)
/*  40:    */   @Digits(integer=3, fraction=2)
/*  41:    */   @NotNull
/*  42:    */   @Min(0L)
/*  43: 63 */   private BigDecimal porcentajeRetencion = BigDecimal.ZERO;
/*  44:    */   @Column(name="base_imponible_retencion", nullable=true, precision=12, scale=2)
/*  45:    */   @Digits(integer=12, fraction=2)
/*  46: 69 */   private BigDecimal baseImponibleRetencion = BigDecimal.ZERO;
/*  47:    */   @Column(name="base_imponible_tarifa_cero", precision=12, scale=2)
/*  48:    */   @Digits(integer=12, fraction=2)
/*  49:    */   @Min(0L)
/*  50: 73 */   private BigDecimal baseImponibleTarifaCero = BigDecimal.ZERO;
/*  51:    */   @Column(name="base_imponible_diferente_cero", precision=12, scale=2)
/*  52:    */   @Digits(integer=12, fraction=2)
/*  53:    */   @Min(0L)
/*  54: 78 */   private BigDecimal baseImponibleDiferenteCero = BigDecimal.ZERO;
/*  55:    */   @Column(name="base_imponible_no_objeto_iva", precision=12, scale=2)
/*  56:    */   @Digits(integer=12, fraction=2)
/*  57:    */   @Min(0L)
/*  58: 83 */   private BigDecimal baseImponibleNoObjetoIva = BigDecimal.ZERO;
/*  59:    */   @Column(name="valor_retencion", nullable=true, precision=12, scale=2)
/*  60:    */   @Digits(integer=12, fraction=2)
/*  61: 88 */   private BigDecimal valorRetencion = BigDecimal.ZERO;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_factura_proveedorSRI", nullable=true)
/*  64:    */   private FacturaProveedorSRI facturaProveedorSRI;
/*  65:    */   @Transient
/*  66:    */   private TipoConceptoRetencion tipoConceptoRetencion;
/*  67:    */   @Transient
/*  68:    */   private String codigoConceptoRetencionSRI;
/*  69:    */   @Transient
/*  70:    */   private Date fechaEmisionRetencion;
/*  71:    */   @Transient
/*  72:    */   private String numeroFactura;
/*  73:    */   @Transient
/*  74:    */   private String identificacionProveedor;
/*  75:    */   @Transient
/*  76:    */   private String conceptoRetencion;
/*  77:    */   
/*  78:    */   public DetalleFacturaProveedorSRI() {}
/*  79:    */   
/*  80:    */   public List<String> getCamposAuditables()
/*  81:    */   {
/*  82:115 */     ArrayList<String> lista = new ArrayList();
/*  83:116 */     lista.add("conceptoRetencionSRI");
/*  84:117 */     lista.add("porcentajeRetencion");
/*  85:118 */     lista.add("baseImponibleRetencion");
/*  86:119 */     lista.add("valorRetencion");
/*  87:    */     
/*  88:121 */     return lista;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getId()
/*  92:    */   {
/*  93:131 */     return getIdDetalleFacturaProveedorSRI();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public DetalleFacturaProveedorSRI(BigDecimal baseImponibleRetencion, BigDecimal valorRetencion, String codigoConceptoRetencionSRI)
/*  97:    */   {
/*  98:136 */     this.baseImponibleRetencion = baseImponibleRetencion;
/*  99:137 */     this.valorRetencion = valorRetencion;
/* 100:138 */     this.codigoConceptoRetencionSRI = codigoConceptoRetencionSRI;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public DetalleFacturaProveedorSRI(Date fechaEmisionRetencion, String nuemeroFactura, String identificacionProveedor, String conceptoRetencion, BigDecimal baseImponibleRetencion, BigDecimal porcentajeRetencion, BigDecimal valorRetencion, String codigoConceptoRetencionSRI)
/* 104:    */   {
/* 105:156 */     this.fechaEmisionRetencion = fechaEmisionRetencion;
/* 106:157 */     this.numeroFactura = nuemeroFactura;
/* 107:158 */     this.identificacionProveedor = identificacionProveedor;
/* 108:159 */     this.conceptoRetencion = conceptoRetencion;
/* 109:160 */     this.baseImponibleRetencion = baseImponibleRetencion;
/* 110:161 */     this.porcentajeRetencion = porcentajeRetencion;
/* 111:162 */     this.valorRetencion = valorRetencion;
/* 112:163 */     this.codigoConceptoRetencionSRI = codigoConceptoRetencionSRI;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getIdDetalleFacturaProveedorSRI()
/* 116:    */   {
/* 117:172 */     return this.idDetalleFacturaProveedorSRI;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setIdDetalleFacturaProveedorSRI(int idDetalleFacturaProveedorSRI)
/* 121:    */   {
/* 122:182 */     this.idDetalleFacturaProveedorSRI = idDetalleFacturaProveedorSRI;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public ConceptoRetencionSRI getConceptoRetencionSRI()
/* 126:    */   {
/* 127:186 */     return this.conceptoRetencionSRI;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setConceptoRetencionSRI(ConceptoRetencionSRI conceptoRetencionSRI)
/* 131:    */   {
/* 132:191 */     this.conceptoRetencionSRI = conceptoRetencionSRI;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public BigDecimal getBaseImponibleRetencion()
/* 136:    */   {
/* 137:195 */     return this.baseImponibleRetencion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setBaseImponibleRetencion(BigDecimal baseImponibleRetencion)
/* 141:    */   {
/* 142:199 */     this.baseImponibleRetencion = baseImponibleRetencion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public BigDecimal getPorcentajeRetencion()
/* 146:    */   {
/* 147:203 */     return this.porcentajeRetencion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setPorcentajeRetencion(BigDecimal porcentajeRetencion)
/* 151:    */   {
/* 152:207 */     this.porcentajeRetencion = porcentajeRetencion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public BigDecimal getValorRetencion()
/* 156:    */   {
/* 157:211 */     return this.valorRetencion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setValorRetencion(BigDecimal valorRetencion)
/* 161:    */   {
/* 162:215 */     this.valorRetencion = valorRetencion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public FacturaProveedorSRI getFacturaProveedorSRI()
/* 166:    */   {
/* 167:219 */     return this.facturaProveedorSRI;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/* 171:    */   {
/* 172:223 */     this.facturaProveedorSRI = facturaProveedorSRI;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getCodigoConceptoRetencionSRI()
/* 176:    */   {
/* 177:232 */     return this.codigoConceptoRetencionSRI;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setCodigoConceptoRetencionSRI(String codigoConceptoRetencionSRI)
/* 181:    */   {
/* 182:242 */     this.codigoConceptoRetencionSRI = codigoConceptoRetencionSRI;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Date getFechaEmisionRetencion()
/* 186:    */   {
/* 187:246 */     return this.fechaEmisionRetencion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setFechaEmisionRetencion(Date fechaEmisionRetencion)
/* 191:    */   {
/* 192:250 */     this.fechaEmisionRetencion = fechaEmisionRetencion;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getNumeroFactura()
/* 196:    */   {
/* 197:254 */     return this.numeroFactura;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setNumeroFactura(String nuemeroFactura)
/* 201:    */   {
/* 202:258 */     this.numeroFactura = nuemeroFactura;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getIdentificacionProveedor()
/* 206:    */   {
/* 207:262 */     return this.identificacionProveedor;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setIdentificacionProveedor(String identificacionProveedor)
/* 211:    */   {
/* 212:266 */     this.identificacionProveedor = identificacionProveedor;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String toString()
/* 216:    */   {
/* 217:271 */     return this.facturaProveedorSRI.getNombreProveedor();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String getConceptoRetencion()
/* 221:    */   {
/* 222:275 */     return this.conceptoRetencion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setConceptoRetencion(String conceptoRetencion)
/* 226:    */   {
/* 227:279 */     this.conceptoRetencion = conceptoRetencion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public BigDecimal getBaseImponibleTarifaCero()
/* 231:    */   {
/* 232:287 */     return this.baseImponibleTarifaCero;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setBaseImponibleTarifaCero(BigDecimal baseImponibleTarifaCero)
/* 236:    */   {
/* 237:295 */     this.baseImponibleTarifaCero = baseImponibleTarifaCero;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public BigDecimal getBaseImponibleDiferenteCero()
/* 241:    */   {
/* 242:303 */     return this.baseImponibleDiferenteCero;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setBaseImponibleDiferenteCero(BigDecimal baseImponibleDiferenteCero)
/* 246:    */   {
/* 247:311 */     this.baseImponibleDiferenteCero = baseImponibleDiferenteCero;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public BigDecimal getBaseImponibleNoObjetoIva()
/* 251:    */   {
/* 252:319 */     return this.baseImponibleNoObjetoIva;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setBaseImponibleNoObjetoIva(BigDecimal baseImponibleNoObjetoIva)
/* 256:    */   {
/* 257:327 */     this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public TipoConceptoRetencion getTipoConceptoRetencion()
/* 261:    */   {
/* 262:331 */     if (this.conceptoRetencionSRI != null) {
/* 263:332 */       this.tipoConceptoRetencion = this.conceptoRetencionSRI.getTipoConceptoRetencion();
/* 264:    */     }
/* 265:334 */     return this.tipoConceptoRetencion;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setTipoConceptoRetencion(TipoConceptoRetencion tipoConceptoRetencion)
/* 269:    */   {
/* 270:338 */     this.tipoConceptoRetencion = tipoConceptoRetencion;
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI
 * JD-Core Version:    0.7.0.1
 */