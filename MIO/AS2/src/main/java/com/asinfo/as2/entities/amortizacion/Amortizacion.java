/*   1:    */ package com.asinfo.as2.entities.amortizacion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.persistence.Column;
/*  13:    */ import javax.persistence.Entity;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.OneToMany;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.validation.constraints.DecimalMin;
/*  26:    */ import javax.validation.constraints.Digits;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="amortizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero"})})
/*  33:    */ public class Amortizacion
/*  34:    */   extends EntidadBase
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="amortizacion", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="amortizacion")
/*  41:    */   @Column(name="id_amortizacion")
/*  42:    */   private int idAmortizacion;
/*  43:    */   @Column(name="id_organizacion")
/*  44:    */   private int idOrganizacion;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private Sucursal sucursal;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_documento", nullable=true)
/*  51:    */   @NotNull
/*  52:    */   private Documento documento;
/*  53:    */   @Column(name="numero", nullable=false, length=20)
/*  54:    */   @NotNull
/*  55:    */   @Size(max=20)
/*  56:    */   private String numero;
/*  57:    */   @Temporal(TemporalType.DATE)
/*  58:    */   @Column(name="fecha_registro", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private Date fechaRegistro;
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha_inicio_amortizacion", nullable=false)
/*  63:    */   @NotNull
/*  64:    */   private Date fechaInicioAmortizacion;
/*  65:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  66:    */   @NotNull
/*  67:    */   @Digits(integer=12, fraction=2)
/*  68:    */   @DecimalMin("0.01")
/*  69:    */   private BigDecimal valor;
/*  70:    */   @Column(name="valor_amortizado", nullable=false, precision=12, scale=2)
/*  71:    */   @NotNull
/*  72:    */   @Digits(integer=12, fraction=2)
/*  73:    */   @DecimalMin("0.00")
/*  74:    */   private BigDecimal valorAmortizado;
/*  75:    */   @Column(name="meses_por_amortizar", nullable=false)
/*  76:    */   @NotNull
/*  77:    */   @Min(1L)
/*  78:    */   private int mesesPorAmortizar;
/*  79:    */   @Column(name="descripcion", length=200, nullable=true)
/*  80:    */   @Size(max=200)
/*  81:    */   private String descripcion;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_tipo_amortizacion", nullable=false)
/*  84:    */   @NotNull
/*  85:    */   private TipoAmortizacion tipoAmortizacion;
/*  86:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  87:    */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*  88:    */   private FacturaProveedor facturaProveedor;
/*  89:    */   @Temporal(TemporalType.DATE)
/*  90:    */   @Column(name="fecha_compra", nullable=true)
/*  91:    */   private Date fechaCompra;
/*  92:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="amortizacion")
/*  93:118 */   private List<DetalleAmortizacion> listaDetalleAmortizacion = new ArrayList();
/*  94:    */   @Column(name="valor_amortizado_total", nullable=false, precision=12, scale=2)
/*  95:    */   @Digits(integer=12, fraction=2)
/*  96:    */   @Min(0L)
/*  97:    */   @NotNull
/*  98:    */   private BigDecimal valorAmortizadoTotal;
/*  99:    */   @Column(name="meses_amortizados", nullable=false)
/* 100:    */   @NotNull
/* 101:    */   private int mesesAmortizados;
/* 102:    */   @Column(name="meses_por_amortizar_real")
/* 103:    */   private int mesesPorAmortizarReal;
/* 104:    */   
/* 105:    */   public int getId()
/* 106:    */   {
/* 107:144 */     return this.idAmortizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdAmortizacion()
/* 111:    */   {
/* 112:151 */     return this.idAmortizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdAmortizacion(int idAmortizacion)
/* 116:    */   {
/* 117:159 */     this.idAmortizacion = idAmortizacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdOrganizacion()
/* 121:    */   {
/* 122:166 */     return this.idOrganizacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdOrganizacion(int idOrganizacion)
/* 126:    */   {
/* 127:174 */     this.idOrganizacion = idOrganizacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Sucursal getSucursal()
/* 131:    */   {
/* 132:181 */     return this.sucursal;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setSucursal(Sucursal sucursal)
/* 136:    */   {
/* 137:189 */     this.sucursal = sucursal;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Documento getDocumento()
/* 141:    */   {
/* 142:196 */     return this.documento;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDocumento(Documento documento)
/* 146:    */   {
/* 147:204 */     this.documento = documento;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getNumero()
/* 151:    */   {
/* 152:211 */     return this.numero;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setNumero(String numero)
/* 156:    */   {
/* 157:219 */     this.numero = numero;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaRegistro()
/* 161:    */   {
/* 162:226 */     return this.fechaRegistro;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaRegistro(Date fechaRegistro)
/* 166:    */   {
/* 167:234 */     this.fechaRegistro = fechaRegistro;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Date getFechaInicioAmortizacion()
/* 171:    */   {
/* 172:241 */     return this.fechaInicioAmortizacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFechaInicioAmortizacion(Date fechaInicioAmortizacion)
/* 176:    */   {
/* 177:249 */     this.fechaInicioAmortizacion = fechaInicioAmortizacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public BigDecimal getValor()
/* 181:    */   {
/* 182:256 */     return this.valor;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setValor(BigDecimal valor)
/* 186:    */   {
/* 187:264 */     this.valor = valor;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public BigDecimal getValorAmortizado()
/* 191:    */   {
/* 192:271 */     return this.valorAmortizado;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setValorAmortizado(BigDecimal valorAmortizado)
/* 196:    */   {
/* 197:279 */     this.valorAmortizado = valorAmortizado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public int getMesesPorAmortizar()
/* 201:    */   {
/* 202:286 */     return this.mesesPorAmortizar;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setMesesPorAmortizar(int mesesPorAmortizar)
/* 206:    */   {
/* 207:294 */     this.mesesPorAmortizar = mesesPorAmortizar;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getDescripcion()
/* 211:    */   {
/* 212:301 */     return this.descripcion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDescripcion(String descripcion)
/* 216:    */   {
/* 217:309 */     this.descripcion = descripcion;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public TipoAmortizacion getTipoAmortizacion()
/* 221:    */   {
/* 222:316 */     return this.tipoAmortizacion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setTipoAmortizacion(TipoAmortizacion tipoAmortizacion)
/* 226:    */   {
/* 227:324 */     this.tipoAmortizacion = tipoAmortizacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public FacturaProveedor getFacturaProveedor()
/* 231:    */   {
/* 232:331 */     return this.facturaProveedor;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 236:    */   {
/* 237:339 */     this.facturaProveedor = facturaProveedor;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Date getFechaCompra()
/* 241:    */   {
/* 242:346 */     return this.fechaCompra;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setFechaCompra(Date fechaCompra)
/* 246:    */   {
/* 247:354 */     this.fechaCompra = fechaCompra;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public List<DetalleAmortizacion> getListaDetalleAmortizacion()
/* 251:    */   {
/* 252:361 */     return this.listaDetalleAmortizacion;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setListaDetalleAmortizacion(List<DetalleAmortizacion> listaDetalleAmortizacion)
/* 256:    */   {
/* 257:369 */     this.listaDetalleAmortizacion = listaDetalleAmortizacion;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public BigDecimal getValorAmortizadoTotal()
/* 261:    */   {
/* 262:376 */     return this.valorAmortizadoTotal;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setValorAmortizadoTotal(BigDecimal valorAmortizadoTotal)
/* 266:    */   {
/* 267:384 */     this.valorAmortizadoTotal = valorAmortizadoTotal;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public int getMesesAmortizados()
/* 271:    */   {
/* 272:391 */     return this.mesesAmortizados;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setMesesAmortizados(int mesesAmortizados)
/* 276:    */   {
/* 277:399 */     this.mesesAmortizados = mesesAmortizados;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public int getMesesPorAmortizarReal()
/* 281:    */   {
/* 282:406 */     return this.mesesPorAmortizarReal;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setMesesPorAmortizarReal(int mesesPorAmortizarReal)
/* 286:    */   {
/* 287:414 */     this.mesesPorAmortizarReal = mesesPorAmortizarReal;
/* 288:    */   }
/* 289:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.amortizacion.Amortizacion
 * JD-Core Version:    0.7.0.1
 */