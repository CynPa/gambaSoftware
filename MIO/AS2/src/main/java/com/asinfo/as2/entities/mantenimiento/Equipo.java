/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   4:    */ import com.asinfo.as2.entities.DimensionContable;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   7:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*   8:    */ import com.asinfo.as2.enumeraciones.PrioridadEnum;
/*   9:    */ import java.io.Serializable;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.persistence.Column;
/*  14:    */ import javax.persistence.Entity;
/*  15:    */ import javax.persistence.EnumType;
/*  16:    */ import javax.persistence.Enumerated;
/*  17:    */ import javax.persistence.FetchType;
/*  18:    */ import javax.persistence.GeneratedValue;
/*  19:    */ import javax.persistence.GenerationType;
/*  20:    */ import javax.persistence.Id;
/*  21:    */ import javax.persistence.JoinColumn;
/*  22:    */ import javax.persistence.ManyToOne;
/*  23:    */ import javax.persistence.OneToMany;
/*  24:    */ import javax.persistence.Table;
/*  25:    */ import javax.persistence.TableGenerator;
/*  26:    */ import javax.persistence.Temporal;
/*  27:    */ import javax.persistence.TemporalType;
/*  28:    */ import javax.persistence.Transient;
/*  29:    */ import javax.validation.constraints.NotNull;
/*  30:    */ import javax.validation.constraints.Size;
/*  31:    */ 
/*  32:    */ @Entity
/*  33:    */ @Table(name="equipo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  34:    */ public class Equipo
/*  35:    */   extends EntidadBase
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="equipo", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="equipo")
/*  42:    */   @Column(name="id_equipo")
/*  43:    */   private int idEquipo;
/*  44:    */   @Column(name="id_organizacion")
/*  45:    */   private int idOrganizacion;
/*  46:    */   @Column(name="id_sucursal")
/*  47:    */   private int idSucursal;
/*  48:    */   @Column(name="codigo", nullable=false, length=20)
/*  49:    */   @NotNull
/*  50:    */   @Size(min=1, max=20)
/*  51:    */   private String codigo;
/*  52:    */   @Column(name="nombre", nullable=false, length=100)
/*  53:    */   @NotNull
/*  54:    */   @Size(min=2, max=100)
/*  55:    */   private String nombre;
/*  56:    */   @Column(name="descripcion", length=200, nullable=true)
/*  57:    */   @Size(max=200)
/*  58:    */   private String descripcion;
/*  59:    */   @Column(name="numero_serie", length=20, nullable=true)
/*  60:    */   @Size(max=20)
/*  61:    */   private String numeroSerie;
/*  62:    */   @Column(name="numero_parte", length=20, nullable=true)
/*  63:    */   @Size(max=20)
/*  64:    */   private String numeroParte;
/*  65:    */   @Column(name="codigo_barras", length=20, nullable=true)
/*  66:    */   @Size(max=20)
/*  67:    */   private String codigoBarras;
/*  68:    */   @Column(name="modelo", length=50, nullable=true)
/*  69:    */   @Size(max=50)
/*  70:    */   private String modelo;
/*  71:    */   @Column(name="prioridad", nullable=false)
/*  72:    */   @Enumerated(EnumType.ORDINAL)
/*  73:    */   @NotNull
/*  74:    */   private PrioridadEnum prioridad;
/*  75:    */   @Column(name="numero_factura", nullable=true, length=20)
/*  76:    */   @Size(max=20)
/*  77:    */   private String numeroFactura;
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  79:    */   @JoinColumn(name="id_subcategoria_equipo", nullable=false)
/*  80:    */   @NotNull
/*  81:    */   private SubcategoriaEquipo subcategoriaEquipo;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_ubicacion", nullable=false)
/*  84:    */   @NotNull
/*  85:    */   private UbicacionActivo ubicacion;
/*  86:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  87:    */   @JoinColumn(name="id_centro_costo", nullable=true)
/*  88:    */   private DimensionContable centroCosto;
/*  89:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  90:    */   @JoinColumn(name="id_equipo_padre", nullable=true)
/*  91:    */   private Equipo equipoPadre;
/*  92:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  93:    */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*  94:    */   private FacturaProveedor facturaProveedor;
/*  95:    */   @Column(name="predeterminado", nullable=false)
/*  96:    */   private boolean predeterminado;
/*  97:    */   @Column(name="activo", nullable=false)
/*  98:    */   private boolean activo;
/*  99:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="equipo")
/* 100:130 */   private List<DocumentoEquipo> listaDocumentoEquipo = new ArrayList();
/* 101:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="equipo")
/* 102:133 */   private List<ImagenEquipo> listaImagenEquipo = new ArrayList();
/* 103:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="equipo")
/* 104:136 */   private List<DetalleComponenteEquipo> listaComponenteEquipo = new ArrayList();
/* 105:    */   @Temporal(TemporalType.DATE)
/* 106:    */   @Column(name="fecha_compra", nullable=false)
/* 107:    */   @NotNull
/* 108:    */   private Date fechaCompra;
/* 109:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 110:    */   @JoinColumn(name="id_activo_fijo", nullable=true)
/* 111:    */   private ActivoFijo activoFijo;
/* 112:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="equipo")
/* 113:148 */   private List<PlanMantenimientoEquipo> listaPlanMantenimientoEquipo = new ArrayList();
/* 114:    */   @Transient
/* 115:151 */   private int cantidadPlanMantenimiento = 0;
/* 116:    */   @Temporal(TemporalType.DATE)
/* 117:    */   @Column(name="fecha_ultimo_matenimiento", nullable=true)
/* 118:    */   private Date fechaUltimoMantenimiento;
/* 119:    */   
/* 120:    */   public int getId()
/* 121:    */   {
/* 122:168 */     return this.idEquipo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getIdEquipo()
/* 126:    */   {
/* 127:175 */     return this.idEquipo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<DetalleComponenteEquipo> getListaComponenteEquipo()
/* 131:    */   {
/* 132:179 */     return this.listaComponenteEquipo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaComponenteEquipo(List<DetalleComponenteEquipo> listaComponenteEquipo)
/* 136:    */   {
/* 137:183 */     this.listaComponenteEquipo = listaComponenteEquipo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setIdEquipo(int idEquipo)
/* 141:    */   {
/* 142:191 */     this.idEquipo = idEquipo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdOrganizacion()
/* 146:    */   {
/* 147:198 */     return this.idOrganizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdOrganizacion(int idOrganizacion)
/* 151:    */   {
/* 152:206 */     this.idOrganizacion = idOrganizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getIdSucursal()
/* 156:    */   {
/* 157:213 */     return this.idSucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIdSucursal(int idSucursal)
/* 161:    */   {
/* 162:221 */     this.idSucursal = idSucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getCodigo()
/* 166:    */   {
/* 167:228 */     return this.codigo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setCodigo(String codigo)
/* 171:    */   {
/* 172:236 */     this.codigo = codigo;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getNombre()
/* 176:    */   {
/* 177:243 */     return this.nombre;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setNombre(String nombre)
/* 181:    */   {
/* 182:251 */     this.nombre = nombre;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getDescripcion()
/* 186:    */   {
/* 187:258 */     return this.descripcion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setDescripcion(String descripcion)
/* 191:    */   {
/* 192:266 */     this.descripcion = descripcion;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getNumeroSerie()
/* 196:    */   {
/* 197:273 */     return this.numeroSerie;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setNumeroSerie(String numeroSerie)
/* 201:    */   {
/* 202:281 */     this.numeroSerie = numeroSerie;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getNumeroParte()
/* 206:    */   {
/* 207:288 */     return this.numeroParte;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setNumeroParte(String numeroParte)
/* 211:    */   {
/* 212:296 */     this.numeroParte = numeroParte;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getCodigoBarras()
/* 216:    */   {
/* 217:303 */     return this.codigoBarras;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setCodigoBarras(String codigoBarras)
/* 221:    */   {
/* 222:311 */     this.codigoBarras = codigoBarras;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public String getModelo()
/* 226:    */   {
/* 227:318 */     return this.modelo;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setModelo(String modelo)
/* 231:    */   {
/* 232:326 */     this.modelo = modelo;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public PrioridadEnum getPrioridad()
/* 236:    */   {
/* 237:333 */     return this.prioridad;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public SubcategoriaEquipo getSubcategoriaEquipo()
/* 241:    */   {
/* 242:340 */     return this.subcategoriaEquipo;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setSubcategoriaEquipo(SubcategoriaEquipo subcategoriaEquipo)
/* 246:    */   {
/* 247:348 */     this.subcategoriaEquipo = subcategoriaEquipo;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setPrioridad(PrioridadEnum prioridad)
/* 251:    */   {
/* 252:356 */     this.prioridad = prioridad;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public UbicacionActivo getUbicacion()
/* 256:    */   {
/* 257:363 */     return this.ubicacion;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setUbicacion(UbicacionActivo ubicacion)
/* 261:    */   {
/* 262:371 */     this.ubicacion = ubicacion;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public DimensionContable getCentroCosto()
/* 266:    */   {
/* 267:378 */     return this.centroCosto;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setCentroCosto(DimensionContable centroCosto)
/* 271:    */   {
/* 272:386 */     this.centroCosto = centroCosto;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public Equipo getEquipoPadre()
/* 276:    */   {
/* 277:393 */     return this.equipoPadre;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setEquipoPadre(Equipo equipoPadre)
/* 281:    */   {
/* 282:401 */     this.equipoPadre = equipoPadre;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public FacturaProveedor getFacturaProveedor()
/* 286:    */   {
/* 287:408 */     return this.facturaProveedor;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 291:    */   {
/* 292:416 */     this.facturaProveedor = facturaProveedor;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isPredeterminado()
/* 296:    */   {
/* 297:423 */     return this.predeterminado;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setPredeterminado(boolean predeterminado)
/* 301:    */   {
/* 302:431 */     this.predeterminado = predeterminado;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public boolean isActivo()
/* 306:    */   {
/* 307:438 */     return this.activo;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setActivo(boolean activo)
/* 311:    */   {
/* 312:446 */     this.activo = activo;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<DocumentoEquipo> getListaDocumentoEquipo()
/* 316:    */   {
/* 317:453 */     return this.listaDocumentoEquipo;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setListaDocumentoEquipo(List<DocumentoEquipo> listaDocumentoEquipo)
/* 321:    */   {
/* 322:461 */     this.listaDocumentoEquipo = listaDocumentoEquipo;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public List<ImagenEquipo> getListaImagenEquipo()
/* 326:    */   {
/* 327:468 */     return this.listaImagenEquipo;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setListaImagenEquipo(List<ImagenEquipo> listaImagenEquipo)
/* 331:    */   {
/* 332:476 */     this.listaImagenEquipo = listaImagenEquipo;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public Date getFechaCompra()
/* 336:    */   {
/* 337:480 */     return this.fechaCompra;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setFechaCompra(Date fechaCompra)
/* 341:    */   {
/* 342:484 */     this.fechaCompra = fechaCompra;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public ActivoFijo getActivoFijo()
/* 346:    */   {
/* 347:488 */     return this.activoFijo;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 351:    */   {
/* 352:492 */     this.activoFijo = activoFijo;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getNumeroFactura()
/* 356:    */   {
/* 357:496 */     return this.numeroFactura;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setNumeroFactura(String numero)
/* 361:    */   {
/* 362:500 */     this.numeroFactura = numero;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public List<PlanMantenimientoEquipo> getListaPlanMantenimientoEquipo()
/* 366:    */   {
/* 367:504 */     return this.listaPlanMantenimientoEquipo;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setListaPlanMantenimientoEquipo(List<PlanMantenimientoEquipo> listaPlanMantenimientoEquipo)
/* 371:    */   {
/* 372:508 */     this.listaPlanMantenimientoEquipo = listaPlanMantenimientoEquipo;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public int getCantidadPlanMantenimiento()
/* 376:    */   {
/* 377:512 */     return this.cantidadPlanMantenimiento;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setCantidadPlanMantenimiento(int cantidadPlanMantenimiento)
/* 381:    */   {
/* 382:516 */     this.cantidadPlanMantenimiento = cantidadPlanMantenimiento;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Date getFechaUltimoMantenimiento()
/* 386:    */   {
/* 387:520 */     return this.fechaUltimoMantenimiento;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setFechaUltimoMantenimiento(Date fechaUltimoMantenimiento)
/* 391:    */   {
/* 392:524 */     this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
/* 393:    */   }
/* 394:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.Equipo
 * JD-Core Version:    0.7.0.1
 */