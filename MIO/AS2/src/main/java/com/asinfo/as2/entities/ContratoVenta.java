/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
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
/*  28:    */ import javax.validation.constraints.Pattern;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="contrato_venta", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero"})})
/*  33:    */ public class ContratoVenta
/*  34:    */   extends EntidadBase
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="contrato_venta", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="contrato_venta")
/*  40:    */   @Column(name="id_contrato_venta", unique=true, nullable=false)
/*  41:    */   private int idContratoVenta;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  44:    */   private Sucursal sucursal;
/*  45:    */   @Column(name="id_organizacion", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idOrganizacion;
/*  48:    */   @Column(name="numero", nullable=true, length=10)
/*  49:    */   @NotNull
/*  50:    */   @Size(max=10)
/*  51:    */   private String numero;
/*  52:    */   @Column(name="numero_cuotas", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   @Min(0L)
/*  55:    */   private int numeroCuotas;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  58:    */   private Empresa empresa;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_documento", nullable=true)
/*  61:    */   private Documento documento;
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   @Column(name="fecha", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private Date fecha;
/*  66:    */   @Temporal(TemporalType.DATE)
/*  67:    */   @Column(name="fecha_vencimiento", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private Date fechaVencimiento;
/*  70:    */   @Column(name="descripcion", nullable=true, length=400)
/*  71:    */   @Size(max=400)
/*  72:    */   private String descripcion;
/*  73:    */   @Column(name="estado", nullable=false)
/*  74:    */   @Enumerated(EnumType.STRING)
/*  75:    */   private Estado estado;
/*  76:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="contratoVenta")
/*  77:101 */   private List<DetalleContratoVenta> listaDetalleContratoVenta = new ArrayList();
/*  78:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="contratoVenta")
/*  79:104 */   private List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta = new ArrayList();
/*  80:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="contratoVenta")
/*  81:107 */   private List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVenta = new ArrayList();
/*  82:    */   @Column(name="indicador_saldo_inicial", nullable=true)
/*  83:    */   private Boolean indicadorSaldoInicial;
/*  84:    */   @Column(name="indicador_cerrado", nullable=false)
/*  85:    */   @NotNull
/*  86:    */   private boolean indicadorCerrado;
/*  87:    */   @Column(name="indicador_mostrar_periodo", nullable=false)
/*  88:    */   @NotNull
/*  89:117 */   private boolean indicadorMostrarPeriodo = true;
/*  90:    */   @Column(name="indicador_maneja_cuotas", nullable=true)
/*  91:    */   private Boolean indicadorManejaCuotas;
/*  92:    */   @Column(name="cuotas_anteriores", nullable=true)
/*  93:    */   @Min(0L)
/*  94:    */   private Integer cuotasAnteriores;
/*  95:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  96:    */   @JoinColumn(name="id_condicion_pago", nullable=true)
/*  97:    */   private CondicionPago condicionPago;
/*  98:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  99:    */   @JoinColumn(name="id_zona", nullable=true)
/* 100:    */   private Zona zona;
/* 101:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 102:    */   @JoinColumn(name="id_canal", nullable=true)
/* 103:    */   private Canal canal;
/* 104:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 105:    */   @JoinColumn(name="id_direccion_empresa", nullable=true)
/* 106:    */   private DireccionEmpresa direccionEmpresa;
/* 107:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 108:    */   @JoinColumn(name="id_subempresa", nullable=true)
/* 109:    */   private Subempresa subempresa;
/* 110:    */   @Pattern(regexp="^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,3};{0,1}))*$", message="Campo email invalido")
/* 111:    */   @Size(max=500)
/* 112:    */   @Column(name="email", nullable=true, length=500)
/* 113:    */   private String email;
/* 114:    */   @OneToOne(fetch=FetchType.LAZY)
/* 115:    */   @JoinColumn(name="id_agente_comercial", nullable=true)
/* 116:    */   private EntidadUsuario agenteComercial;
/* 117:    */   @Transient
/* 118:157 */   private boolean cuotasFacturadas = false;
/* 119:    */   
/* 120:    */   public BigDecimal getTotalContratoVenta()
/* 121:    */   {
/* 122:165 */     BigDecimal total = BigDecimal.ZERO;
/* 123:166 */     for (DetalleContratoVenta dcv : getListaDetalleContratoVenta()) {
/* 124:167 */       if (!dcv.isEliminado()) {
/* 125:168 */         total = total.add(dcv.getPrecioLinea());
/* 126:    */       }
/* 127:    */     }
/* 128:172 */     return total;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getId()
/* 132:    */   {
/* 133:177 */     return this.idContratoVenta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getIdContratoVenta()
/* 137:    */   {
/* 138:181 */     return this.idContratoVenta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIdContratoVenta(int idContratoVenta)
/* 142:    */   {
/* 143:185 */     this.idContratoVenta = idContratoVenta;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Sucursal getSucursal()
/* 147:    */   {
/* 148:189 */     return this.sucursal;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setSucursal(Sucursal sucursal)
/* 152:    */   {
/* 153:193 */     this.sucursal = sucursal;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isIndicadorSaldoInicial()
/* 157:    */   {
/* 158:197 */     if (this.indicadorSaldoInicial == null) {
/* 159:198 */       this.indicadorSaldoInicial = Boolean.valueOf(false);
/* 160:    */     }
/* 161:200 */     return this.indicadorSaldoInicial.booleanValue();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/* 165:    */   {
/* 166:204 */     this.indicadorSaldoInicial = Boolean.valueOf(indicadorSaldoInicial);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public int getIdOrganizacion()
/* 170:    */   {
/* 171:208 */     return this.idOrganizacion;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIdOrganizacion(int idOrganizacion)
/* 175:    */   {
/* 176:212 */     this.idOrganizacion = idOrganizacion;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getNumero()
/* 180:    */   {
/* 181:216 */     return this.numero;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setNumero(String numero)
/* 185:    */   {
/* 186:220 */     this.numero = numero;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Empresa getEmpresa()
/* 190:    */   {
/* 191:224 */     return this.empresa;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setEmpresa(Empresa empresa)
/* 195:    */   {
/* 196:228 */     this.empresa = empresa;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Date getFecha()
/* 200:    */   {
/* 201:232 */     return this.fecha;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setFecha(Date fecha)
/* 205:    */   {
/* 206:236 */     this.fecha = fecha;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Date getFechaVencimiento()
/* 210:    */   {
/* 211:240 */     return this.fechaVencimiento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 215:    */   {
/* 216:244 */     this.fechaVencimiento = fechaVencimiento;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String getDescripcion()
/* 220:    */   {
/* 221:248 */     return this.descripcion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setDescripcion(String descripcion)
/* 225:    */   {
/* 226:252 */     this.descripcion = descripcion;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Estado getEstado()
/* 230:    */   {
/* 231:256 */     return this.estado;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setEstado(Estado estado)
/* 235:    */   {
/* 236:260 */     this.estado = estado;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<DetalleContratoVenta> getListaDetalleContratoVenta()
/* 240:    */   {
/* 241:264 */     return this.listaDetalleContratoVenta;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setListaDetalleContratoVenta(List<DetalleContratoVenta> listaDetalleContratoVenta)
/* 245:    */   {
/* 246:269 */     this.listaDetalleContratoVenta = listaDetalleContratoVenta;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<DetalleValoresContratoVenta> getListaDetalleValoresContratoVenta()
/* 250:    */   {
/* 251:273 */     return this.listaDetalleValoresContratoVenta;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaDetalleValoresContratoVenta(List<DetalleValoresContratoVenta> listaDetalleValoresContratoVenta)
/* 255:    */   {
/* 256:278 */     this.listaDetalleValoresContratoVenta = listaDetalleValoresContratoVenta;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public List<DetallesFacturaContratoVenta> getListaDetallesFacturaContratoVenta()
/* 260:    */   {
/* 261:282 */     return this.listaDetallesFacturaContratoVenta;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setListaDetallesFacturaContratoVenta(List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVenta)
/* 265:    */   {
/* 266:287 */     this.listaDetallesFacturaContratoVenta = listaDetallesFacturaContratoVenta;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int getNumeroCuotas()
/* 270:    */   {
/* 271:291 */     return this.numeroCuotas;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setNumeroCuotas(int numeroCuotas)
/* 275:    */   {
/* 276:295 */     this.numeroCuotas = numeroCuotas;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Documento getDocumento()
/* 280:    */   {
/* 281:299 */     return this.documento;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setDocumento(Documento documento)
/* 285:    */   {
/* 286:303 */     this.documento = documento;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Boolean getIndicadorManejaCuotas()
/* 290:    */   {
/* 291:307 */     if (this.indicadorManejaCuotas == null) {
/* 292:308 */       this.indicadorManejaCuotas = Boolean.valueOf(false);
/* 293:    */     }
/* 294:310 */     return this.indicadorManejaCuotas;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setIndicadorManejaCuotas(Boolean indicadorManejaCuotas)
/* 298:    */   {
/* 299:314 */     this.indicadorManejaCuotas = indicadorManejaCuotas;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public int getCuotasAnteriores()
/* 303:    */   {
/* 304:318 */     if (this.cuotasAnteriores == null) {
/* 305:319 */       this.cuotasAnteriores = Integer.valueOf(0);
/* 306:    */     }
/* 307:321 */     return this.cuotasAnteriores.intValue();
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setCuotasAnteriores(int cuotasAnteriores)
/* 311:    */   {
/* 312:325 */     this.cuotasAnteriores = Integer.valueOf(cuotasAnteriores);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public CondicionPago getCondicionPago()
/* 316:    */   {
/* 317:329 */     return this.condicionPago;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 321:    */   {
/* 322:333 */     this.condicionPago = condicionPago;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public Zona getZona()
/* 326:    */   {
/* 327:337 */     return this.zona;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setZona(Zona zona)
/* 331:    */   {
/* 332:341 */     this.zona = zona;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public Canal getCanal()
/* 336:    */   {
/* 337:345 */     return this.canal;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setCanal(Canal canal)
/* 341:    */   {
/* 342:349 */     this.canal = canal;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public DireccionEmpresa getDireccionEmpresa()
/* 346:    */   {
/* 347:353 */     return this.direccionEmpresa;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 351:    */   {
/* 352:357 */     this.direccionEmpresa = direccionEmpresa;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public Subempresa getSubempresa()
/* 356:    */   {
/* 357:361 */     return this.subempresa;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setSubempresa(Subempresa subempresa)
/* 361:    */   {
/* 362:365 */     this.subempresa = subempresa;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public String getEmail()
/* 366:    */   {
/* 367:369 */     return this.email;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setEmail(String email)
/* 371:    */   {
/* 372:373 */     this.email = email;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public EntidadUsuario getAgenteComercial()
/* 376:    */   {
/* 377:377 */     return this.agenteComercial;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 381:    */   {
/* 382:381 */     this.agenteComercial = agenteComercial;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public boolean isIndicadorCerrado()
/* 386:    */   {
/* 387:385 */     return this.indicadorCerrado;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setIndicadorCerrado(boolean indicadorCerrado)
/* 391:    */   {
/* 392:389 */     this.indicadorCerrado = indicadorCerrado;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public boolean isIndicadorMostrarPeriodo()
/* 396:    */   {
/* 397:393 */     return this.indicadorMostrarPeriodo;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setIndicadorMostrarPeriodo(boolean indicadorMostrarPeriodo)
/* 401:    */   {
/* 402:397 */     this.indicadorMostrarPeriodo = indicadorMostrarPeriodo;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public boolean isCuotasFacturadas()
/* 406:    */   {
/* 407:401 */     return this.cuotasFacturadas;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void setCuotasFacturadas(boolean cuotasFacturadas)
/* 411:    */   {
/* 412:405 */     this.cuotasFacturadas = cuotasFacturadas;
/* 413:    */   }
/* 414:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ContratoVenta
 * JD-Core Version:    0.7.0.1
 */