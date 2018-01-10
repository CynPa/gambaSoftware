/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.Min;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="pago_cash")
/*  31:    */ public class PagoCash
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 70211214498552263L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="pago_cash", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago_cash")
/*  38:    */   @Column(name="id_pago_cash", unique=true, nullable=false)
/*  39:    */   private int idPagoCash;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Column(name="id_sucursal", nullable=false)
/*  43:    */   private int idSucursal;
/*  44:    */   @Column(name="numero", nullable=false, length=20)
/*  45:    */   @NotNull
/*  46:    */   @Size(max=20)
/*  47:    */   private String numero;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=false)
/*  50:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  53:    */   private FormaPago formaPago;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_pago_rol", nullable=true)
/*  56:    */   private PagoRol pagoRol;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  59:    */   private Asiento asiento;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private Date fechaPago;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha_vencimiento", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   private Date fechaVencimiento;
/*  68:    */   @Temporal(TemporalType.DATE)
/*  69:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  70:    */   private Date fechaContabilizacion;
/*  71:    */   @Column(name="valor_pago", precision=12, scale=2, nullable=false)
/*  72:    */   @Min(0L)
/*  73:100 */   private BigDecimal valorPago = BigDecimal.ZERO;
/*  74:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  75:    */   @Size(max=20)
/*  76:    */   private String documentoReferencia;
/*  77:    */   @Column(name="pdf", nullable=true)
/*  78:    */   @Size(max=50)
/*  79:    */   private String pdf;
/*  80:    */   @Enumerated(EnumType.ORDINAL)
/*  81:    */   @Column(name="estado", nullable=false)
/*  82:    */   @NotNull
/*  83:    */   private Estado estado;
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_documento", nullable=false)
/*  86:    */   @NotNull
/*  87:    */   private Documento documento;
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  89:    */   @JoinColumn(name="id_documento_pago")
/*  90:    */   private Documento documentoPago;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_documento_anticipo")
/*  93:    */   private Documento documentoAnticipo;
/*  94:    */   @Enumerated(EnumType.STRING)
/*  95:    */   @Column(name="tipo_servicio_cuenta_bancaria", length=50, nullable=true)
/*  96:    */   private TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria;
/*  97:    */   @OneToMany(mappedBy="pagoCash", fetch=FetchType.LAZY)
/*  98:134 */   private List<DetallePagoCash> listaDetallePagoCash = new ArrayList();
/*  99:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 100:    */   @JoinColumn(name="id_orden_pago_proveedor", nullable=true)
/* 101:    */   private OrdenPagoProveedor ordenPagoProveedor;
/* 102:    */   @Transient
/* 103:    */   private CategoriaEmpresa categoriaEmpresa;
/* 104:    */   
/* 105:    */   public int getId()
/* 106:    */   {
/* 107:155 */     return this.idPagoCash;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdPagoCash()
/* 111:    */   {
/* 112:164 */     return this.idPagoCash;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdPagoCash(int idPagoCash)
/* 116:    */   {
/* 117:174 */     this.idPagoCash = idPagoCash;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdOrganizacion()
/* 121:    */   {
/* 122:183 */     return this.idOrganizacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdOrganizacion(int idOrganizacion)
/* 126:    */   {
/* 127:193 */     this.idOrganizacion = idOrganizacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getIdSucursal()
/* 131:    */   {
/* 132:202 */     return this.idSucursal;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIdSucursal(int idSucursal)
/* 136:    */   {
/* 137:212 */     this.idSucursal = idSucursal;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 141:    */   {
/* 142:221 */     return this.cuentaBancariaOrganizacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 146:    */   {
/* 147:231 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public FormaPago getFormaPago()
/* 151:    */   {
/* 152:240 */     return this.formaPago;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setFormaPago(FormaPago formaPago)
/* 156:    */   {
/* 157:250 */     this.formaPago = formaPago;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaPago()
/* 161:    */   {
/* 162:259 */     return this.fechaPago;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaPago(Date fechaPago)
/* 166:    */   {
/* 167:269 */     this.fechaPago = fechaPago;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public BigDecimal getValorPago()
/* 171:    */   {
/* 172:278 */     return this.valorPago;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setValorPago(BigDecimal valorPago)
/* 176:    */   {
/* 177:288 */     this.valorPago = valorPago;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getNumero()
/* 181:    */   {
/* 182:297 */     return this.numero;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setNumero(String numero)
/* 186:    */   {
/* 187:307 */     this.numero = numero;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getDocumentoReferencia()
/* 191:    */   {
/* 192:316 */     return this.documentoReferencia;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 196:    */   {
/* 197:326 */     this.documentoReferencia = documentoReferencia;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Estado getEstado()
/* 201:    */   {
/* 202:335 */     return this.estado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setEstado(Estado estado)
/* 206:    */   {
/* 207:345 */     this.estado = estado;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Documento getDocumento()
/* 211:    */   {
/* 212:354 */     return this.documento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDocumento(Documento documento)
/* 216:    */   {
/* 217:364 */     this.documento = documento;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Documento getDocumentoPago()
/* 221:    */   {
/* 222:371 */     return this.documentoPago;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setDocumentoPago(Documento documentoPago)
/* 226:    */   {
/* 227:379 */     this.documentoPago = documentoPago;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Documento getDocumentoAnticipo()
/* 231:    */   {
/* 232:386 */     return this.documentoAnticipo;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDocumentoAnticipo(Documento documentoAnticipo)
/* 236:    */   {
/* 237:394 */     this.documentoAnticipo = documentoAnticipo;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Date getFechaVencimiento()
/* 241:    */   {
/* 242:403 */     return this.fechaVencimiento;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 246:    */   {
/* 247:413 */     this.fechaVencimiento = fechaVencimiento;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Date getFechaContabilizacion()
/* 251:    */   {
/* 252:422 */     return this.fechaContabilizacion;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 256:    */   {
/* 257:432 */     this.fechaContabilizacion = fechaContabilizacion;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List<DetallePagoCash> getListaDetallePagoCash()
/* 261:    */   {
/* 262:441 */     return this.listaDetallePagoCash;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setListaDetallePagoCash(List<DetallePagoCash> listaDetallePagoCash)
/* 266:    */   {
/* 267:451 */     this.listaDetallePagoCash = listaDetallePagoCash;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public PagoRol getPagoRol()
/* 271:    */   {
/* 272:460 */     return this.pagoRol;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setPagoRol(PagoRol pagoRol)
/* 276:    */   {
/* 277:470 */     this.pagoRol = pagoRol;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Asiento getAsiento()
/* 281:    */   {
/* 282:479 */     return this.asiento;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setAsiento(Asiento asiento)
/* 286:    */   {
/* 287:489 */     this.asiento = asiento;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public TipoServicioCuentaBancariaEnum getTipoServicioCuentaBancaria()
/* 291:    */   {
/* 292:496 */     return this.tipoServicioCuentaBancaria;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setTipoServicioCuentaBancaria(TipoServicioCuentaBancariaEnum tipoServicioCuentaBancaria)
/* 296:    */   {
/* 297:504 */     this.tipoServicioCuentaBancaria = tipoServicioCuentaBancaria;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 301:    */   {
/* 302:508 */     return this.categoriaEmpresa;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 306:    */   {
/* 307:512 */     this.categoriaEmpresa = categoriaEmpresa;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public OrdenPagoProveedor getOrdenPagoProveedor()
/* 311:    */   {
/* 312:516 */     return this.ordenPagoProveedor;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 316:    */   {
/* 317:520 */     this.ordenPagoProveedor = ordenPagoProveedor;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getPdf()
/* 321:    */   {
/* 322:524 */     return this.pdf;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setPdf(String pdf)
/* 326:    */   {
/* 327:528 */     this.pdf = pdf;
/* 328:    */   }
/* 329:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PagoCash
 * JD-Core Version:    0.7.0.1
 */