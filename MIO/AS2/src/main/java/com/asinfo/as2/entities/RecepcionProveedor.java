/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
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
/*  20:    */ import javax.persistence.OneToOne;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.Min;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="recepcion_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})})
/*  32:    */ public class RecepcionProveedor
/*  33:    */   extends EntidadBase
/*  34:    */   implements Serializable
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="recepcion_proveedor", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="recepcion_proveedor")
/*  40:    */   @Column(name="id_recepcion_proveedor", unique=true, nullable=false)
/*  41:    */   private int idRecepcionProveedor;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_documento", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private Documento documento;
/*  46:    */   @Column(name="id_organizacion", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private int idOrganizacion;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Sucursal sucursal;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Empresa empresa;
/*  57:    */   @Temporal(TemporalType.DATE)
/*  58:    */   @Column(name="fecha", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private Date fecha;
/*  61:    */   @Column(name="numero", nullable=false, length=20)
/*  62:    */   @NotNull
/*  63:    */   @Size(max=20)
/*  64:    */   private String numero;
/*  65:    */   @Column(name="descripcion", length=500, nullable=true)
/*  66:    */   @Size(max=500)
/*  67:    */   private String descripcion;
/*  68:    */   @Column(name="estado", nullable=false)
/*  69:    */   @Enumerated(EnumType.ORDINAL)
/*  70:    */   private Estado estado;
/*  71:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="recepcionProveedor")
/*  72:100 */   private List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = new ArrayList();
/*  73:    */   @OneToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  75:    */   private Asiento asiento;
/*  76:    */   @OneToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_pedido_proveedor", nullable=true)
/*  78:    */   private PedidoProveedor pedidoProveedor;
/*  79:    */   @Temporal(TemporalType.DATE)
/*  80:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  81:    */   private Date fechaContabilizacion;
/*  82:    */   @Column(name="numero_factura", length=20, nullable=false)
/*  83:    */   @Size(max=20)
/*  84:115 */   private String numeroFactura = "";
/*  85:    */   @Column(name="pdf", nullable=true)
/*  86:    */   @Size(max=50)
/*  87:    */   private String pdf;
/*  88:    */   @Temporal(TemporalType.DATE)
/*  89:    */   @Column(name="fecha_anulacion", nullable=true)
/*  90:    */   private Date fechaAnulacion;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_proyecto", nullable=true)
/*  93:    */   private DimensionContable proyecto;
/*  94:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  95:    */   @JoinColumn(name="id_registro_peso", nullable=true)
/*  96:    */   private RegistroPeso registroPeso;
/*  97:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  98:    */   @JoinColumn(name="id_persona_responsable", nullable=true)
/*  99:    */   private PersonaResponsable personaResponsable;
/* 100:    */   @Transient
/* 101:    */   @Min(0L)
/* 102:    */   private BigDecimal total;
/* 103:    */   @Transient
/* 104:    */   private FacturaProveedor facturaProveedor;
/* 105:    */   @Transient
/* 106:    */   private String numeroImportacion;
/* 107:    */   @Transient
/* 108:    */   private boolean activador;
/* 109:    */   @Transient
/* 110:    */   @Min(0L)
/* 111:    */   private BigDecimal totalCosto;
/* 112:    */   @Transient
/* 113:    */   private Date fechaContabilizacionImportacion;
/* 114:    */   
/* 115:    */   public RecepcionProveedor() {}
/* 116:    */   
/* 117:    */   public RecepcionProveedor(int idRecepcionProveedor, String numero, Date fecha)
/* 118:    */   {
/* 119:164 */     this.idRecepcionProveedor = idRecepcionProveedor;
/* 120:165 */     this.numero = numero;
/* 121:166 */     this.fecha = fecha;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getId()
/* 125:    */   {
/* 126:176 */     return this.idRecepcionProveedor;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getIdRecepcionProveedor()
/* 130:    */   {
/* 131:182 */     return this.idRecepcionProveedor;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setIdRecepcionProveedor(int idRecepcionProveedor)
/* 135:    */   {
/* 136:186 */     this.idRecepcionProveedor = idRecepcionProveedor;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Documento getDocumento()
/* 140:    */   {
/* 141:190 */     return this.documento;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setDocumento(Documento documento)
/* 145:    */   {
/* 146:194 */     this.documento = documento;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getIdOrganizacion()
/* 150:    */   {
/* 151:198 */     return this.idOrganizacion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setIdOrganizacion(int idOrganizacion)
/* 155:    */   {
/* 156:202 */     this.idOrganizacion = idOrganizacion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Sucursal getSucursal()
/* 160:    */   {
/* 161:207 */     return this.sucursal;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setSucursal(Sucursal sucursal)
/* 165:    */   {
/* 166:211 */     this.sucursal = sucursal;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Empresa getEmpresa()
/* 170:    */   {
/* 171:215 */     return this.empresa;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setEmpresa(Empresa empresa)
/* 175:    */   {
/* 176:219 */     this.empresa = empresa;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Date getFecha()
/* 180:    */   {
/* 181:223 */     return this.fecha;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setFecha(Date fecha)
/* 185:    */   {
/* 186:227 */     this.fecha = fecha;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getNumero()
/* 190:    */   {
/* 191:231 */     return this.numero;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setNumero(String numero)
/* 195:    */   {
/* 196:235 */     this.numero = numero;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getDescripcion()
/* 200:    */   {
/* 201:239 */     return this.descripcion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setDescripcion(String descripcion)
/* 205:    */   {
/* 206:243 */     this.descripcion = descripcion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Estado getEstado()
/* 210:    */   {
/* 211:247 */     return this.estado;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setEstado(Estado estado)
/* 215:    */   {
/* 216:251 */     this.estado = estado;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<DetalleRecepcionProveedor> getListaDetalleRecepcionProveedor()
/* 220:    */   {
/* 221:255 */     return this.listaDetalleRecepcionProveedor;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setListaDetalleRecepcionProveedor(List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor)
/* 225:    */   {
/* 226:259 */     this.listaDetalleRecepcionProveedor = listaDetalleRecepcionProveedor;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Asiento getAsiento()
/* 230:    */   {
/* 231:263 */     return this.asiento;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setAsiento(Asiento asiento)
/* 235:    */   {
/* 236:267 */     this.asiento = asiento;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public Date getFechaContabilizacion()
/* 240:    */   {
/* 241:271 */     return this.fechaContabilizacion;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 245:    */   {
/* 246:275 */     this.fechaContabilizacion = fechaContabilizacion;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public BigDecimal getTotal()
/* 250:    */   {
/* 251:279 */     this.total = BigDecimal.ZERO;
/* 252:280 */     for (DetalleRecepcionProveedor detalleDespachoCliente : getListaDetalleRecepcionProveedor()) {
/* 253:281 */       if (!detalleDespachoCliente.isEliminado()) {
/* 254:282 */         this.total = this.total.add(detalleDespachoCliente.getCantidad());
/* 255:    */       }
/* 256:    */     }
/* 257:285 */     return this.total;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setTotal(BigDecimal total)
/* 261:    */   {
/* 262:289 */     this.total = total;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public PedidoProveedor getPedidoProveedor()
/* 266:    */   {
/* 267:296 */     return this.pedidoProveedor;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 271:    */   {
/* 272:304 */     this.pedidoProveedor = pedidoProveedor;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public FacturaProveedor getFacturaProveedor()
/* 276:    */   {
/* 277:311 */     return this.facturaProveedor;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 281:    */   {
/* 282:319 */     this.facturaProveedor = facturaProveedor;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getNumeroFactura()
/* 286:    */   {
/* 287:328 */     return this.numeroFactura;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setNumeroFactura(String numeroFactura)
/* 291:    */   {
/* 292:338 */     this.numeroFactura = numeroFactura;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Date getFechaAnulacion()
/* 296:    */   {
/* 297:342 */     return this.fechaAnulacion;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setFechaAnulacion(Date fechaAnulacion)
/* 301:    */   {
/* 302:346 */     this.fechaAnulacion = fechaAnulacion;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public String getNumeroImportacion()
/* 306:    */   {
/* 307:355 */     return this.numeroImportacion;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setNumeroImportacion(String numeroImportacion)
/* 311:    */   {
/* 312:365 */     this.numeroImportacion = numeroImportacion;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public DimensionContable getProyecto()
/* 316:    */   {
/* 317:372 */     return this.proyecto;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setProyecto(DimensionContable proyecto)
/* 321:    */   {
/* 322:380 */     this.proyecto = proyecto;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public boolean isActivador()
/* 326:    */   {
/* 327:387 */     return this.activador;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setActivador(boolean activador)
/* 331:    */   {
/* 332:395 */     this.activador = activador;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public BigDecimal getTotalCosto()
/* 336:    */   {
/* 337:399 */     return this.totalCosto;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setTotalCosto(BigDecimal totalCosto)
/* 341:    */   {
/* 342:403 */     this.totalCosto = totalCosto;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public RegistroPeso getRegistroPeso()
/* 346:    */   {
/* 347:407 */     return this.registroPeso;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 351:    */   {
/* 352:411 */     this.registroPeso = registroPeso;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public Date getFechaContabilizacionImportacion()
/* 356:    */   {
/* 357:415 */     return this.fechaContabilizacionImportacion;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setFechaContabilizacionImportacion(Date fechaContabilizacionImportacion)
/* 361:    */   {
/* 362:419 */     this.fechaContabilizacionImportacion = fechaContabilizacionImportacion;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public String getPdf()
/* 366:    */   {
/* 367:423 */     return this.pdf;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setPdf(String pdf)
/* 371:    */   {
/* 372:427 */     this.pdf = pdf;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public PersonaResponsable getPersonaResponsable()
/* 376:    */   {
/* 377:431 */     return this.personaResponsable;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 381:    */   {
/* 382:435 */     this.personaResponsable = personaResponsable;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RecepcionProveedor
 * JD-Core Version:    0.7.0.1
 */