/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*   6:    */ import com.asinfo.as2.utils.EjbUtil;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.persistence.Column;
/*  12:    */ import javax.persistence.Entity;
/*  13:    */ import javax.persistence.EnumType;
/*  14:    */ import javax.persistence.Enumerated;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.OneToMany;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.persistence.Transient;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ import org.hibernate.annotations.ColumnDefault;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="orden_salida_material", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "id_documento"})})
/*  33:    */ public class OrdenSalidaMaterial
/*  34:    */   extends EntidadBase
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 4678701765469707314L;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="orden_salida_material", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_salida_material")
/*  41:    */   @Column(name="id_orden_salida_material")
/*  42:    */   private int idOrdenSalidaMaterial;
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private int idOrganizacion;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Sucursal sucursal;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_documento", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Documento documento;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @Column(name="fecha", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Date fecha;
/*  58:    */   @Temporal(TemporalType.DATE)
/*  59:    */   @Column(name="fecha_salida_material", nullable=true)
/*  60:    */   private Date fechaSalidaMaterial;
/*  61:    */   @Column(name="numero", nullable=false, length=20)
/*  62:    */   @NotNull
/*  63:    */   @Size(max=20)
/*  64:    */   private String numero;
/*  65:    */   @Column(name="descripcion", nullable=true, length=200)
/*  66:    */   @Size(max=200)
/*  67:    */   private String descripcion;
/*  68:    */   @Column(name="estado", nullable=false)
/*  69:    */   @Enumerated(EnumType.ORDINAL)
/*  70:    */   private Estado estado;
/*  71:    */   @Column(name="tipo_ciclo_produccion_enum", nullable=false)
/*  72:    */   @Enumerated(EnumType.ORDINAL)
/*  73:    */   @NotNull
/*  74:    */   @ColumnDefault("0")
/*  75: 95 */   private TipoCicloProduccionEnum tipoCicloProduccionEnum = TipoCicloProduccionEnum.CICLO_CORTO;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_responsable_salida_mercaderia", nullable=true)
/*  78:    */   private PersonaResponsable responsableSalidaMercaderia;
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  80:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  81:    */   private Transportista transportista;
/*  82:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  83:    */   @JoinColumn(name="id_chofer", nullable=true)
/*  84:    */   private Chofer chofer;
/*  85:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  86:    */   @JoinColumn(name="id_vehiculo", nullable=true)
/*  87:    */   private Vehiculo vehiculo;
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  89:    */   @JoinColumn(name="id_ruta", nullable=true)
/*  90:    */   private Ruta ruta;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_bodega_origen", nullable=true)
/*  93:    */   private Bodega bodegaOrigen;
/*  94:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenSalidaMaterial")
/*  95:131 */   private List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterial = new ArrayList();
/*  96:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenSalidaMaterial")
/*  97:134 */   private List<OrdenFabricacionOrdenSalidaMaterial> listaOrdenFabricacionOrdenSalidaMaterial = new ArrayList();
/*  98:    */   @Column(name="filtro", nullable=true, length=250)
/*  99:    */   private String filtro;
/* 100:    */   @Column(name="indicador_transferencia", nullable=false)
/* 101:    */   @NotNull
/* 102:    */   private boolean indicadorTransferencia;
/* 103:    */   @Column(name="aprobado", nullable=false)
/* 104:    */   @NotNull
/* 105:    */   @ColumnDefault("'0'")
/* 106:    */   private boolean aprobado;
/* 107:    */   @Transient
/* 108:150 */   private List<OrdenFabricacion> listaOrdenFabricacion = new ArrayList();
/* 109:    */   @Transient
/* 110:    */   private boolean indicadorImprimirOFA;
/* 111:    */   
/* 112:    */   public int getId()
/* 113:    */   {
/* 114:169 */     return this.idOrdenSalidaMaterial;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getIdOrdenSalidaMaterial()
/* 118:    */   {
/* 119:178 */     return this.idOrdenSalidaMaterial;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdOrdenSalidaMaterial(int idOrdenSalidaMaterial)
/* 123:    */   {
/* 124:188 */     this.idOrdenSalidaMaterial = idOrdenSalidaMaterial;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getIdOrganizacion()
/* 128:    */   {
/* 129:197 */     return this.idOrganizacion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdOrganizacion(int idOrganizacion)
/* 133:    */   {
/* 134:207 */     this.idOrganizacion = idOrganizacion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Documento getDocumento()
/* 138:    */   {
/* 139:216 */     return this.documento;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDocumento(Documento documento)
/* 143:    */   {
/* 144:226 */     this.documento = documento;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Date getFecha()
/* 148:    */   {
/* 149:235 */     return this.fecha;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setFecha(Date fecha)
/* 153:    */   {
/* 154:245 */     this.fecha = fecha;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getNumero()
/* 158:    */   {
/* 159:254 */     return this.numero;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setNumero(String numero)
/* 163:    */   {
/* 164:264 */     this.numero = numero;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getDescripcion()
/* 168:    */   {
/* 169:273 */     return this.descripcion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDescripcion(String descripcion)
/* 173:    */   {
/* 174:283 */     this.descripcion = descripcion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Estado getEstado()
/* 178:    */   {
/* 179:292 */     return this.estado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setEstado(Estado estado)
/* 183:    */   {
/* 184:302 */     this.estado = estado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrdenSalidaMaterial()
/* 188:    */   {
/* 189:311 */     return this.listaDetalleOrdenSalidaMaterial;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setListaDetalleOrdenSalidaMaterial(List<DetalleOrdenSalidaMaterial> listaDetalleOrdenSalidaMaterial)
/* 193:    */   {
/* 194:321 */     this.listaDetalleOrdenSalidaMaterial = listaDetalleOrdenSalidaMaterial;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Sucursal getSucursal()
/* 198:    */   {
/* 199:325 */     return this.sucursal;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setSucursal(Sucursal sucursal)
/* 203:    */   {
/* 204:329 */     this.sucursal = sucursal;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getFiltro()
/* 208:    */   {
/* 209:333 */     return this.filtro;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setFiltro(String filtro)
/* 213:    */   {
/* 214:337 */     this.filtro = filtro;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public PersonaResponsable getResponsableSalidaMercaderia()
/* 218:    */   {
/* 219:341 */     return this.responsableSalidaMercaderia;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setResponsableSalidaMercaderia(PersonaResponsable responsableSalidaMercaderia)
/* 223:    */   {
/* 224:345 */     this.responsableSalidaMercaderia = responsableSalidaMercaderia;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isIndicadorTransferencia()
/* 228:    */   {
/* 229:349 */     return this.indicadorTransferencia;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setIndicadorTransferencia(boolean indicadorTransferencia)
/* 233:    */   {
/* 234:353 */     this.indicadorTransferencia = indicadorTransferencia;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public TipoCicloProduccionEnum getTipoCicloProduccionEnum()
/* 238:    */   {
/* 239:357 */     return this.tipoCicloProduccionEnum;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setTipoCicloProduccionEnum(TipoCicloProduccionEnum tipoCicloProduccionEnum)
/* 243:    */   {
/* 244:361 */     this.tipoCicloProduccionEnum = tipoCicloProduccionEnum;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<OrdenFabricacionOrdenSalidaMaterial> getListaOrdenFabricacionOrdenSalidaMaterial()
/* 248:    */   {
/* 249:365 */     return this.listaOrdenFabricacionOrdenSalidaMaterial;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaOrdenFabricacionOrdenSalidaMaterial(List<OrdenFabricacionOrdenSalidaMaterial> listaOrdenFabricacionOrdenSalidaMaterial)
/* 253:    */   {
/* 254:369 */     this.listaOrdenFabricacionOrdenSalidaMaterial = listaOrdenFabricacionOrdenSalidaMaterial;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Date getFechaSalidaMaterial()
/* 258:    */   {
/* 259:373 */     return this.fechaSalidaMaterial;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setFechaSalidaMaterial(Date fechaSalidaMaterial)
/* 263:    */   {
/* 264:377 */     this.fechaSalidaMaterial = fechaSalidaMaterial;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Transportista getTransportista()
/* 268:    */   {
/* 269:381 */     return this.transportista;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setTransportista(Transportista transportista)
/* 273:    */   {
/* 274:385 */     this.transportista = transportista;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Chofer getChofer()
/* 278:    */   {
/* 279:389 */     return this.chofer;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setChofer(Chofer chofer)
/* 283:    */   {
/* 284:393 */     this.chofer = chofer;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Vehiculo getVehiculo()
/* 288:    */   {
/* 289:397 */     return this.vehiculo;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setVehiculo(Vehiculo vehiculo)
/* 293:    */   {
/* 294:401 */     this.vehiculo = vehiculo;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Ruta getRuta()
/* 298:    */   {
/* 299:405 */     return this.ruta;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setRuta(Ruta ruta)
/* 303:    */   {
/* 304:409 */     this.ruta = ruta;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Bodega getBodegaOrigen()
/* 308:    */   {
/* 309:413 */     return this.bodegaOrigen;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setBodegaOrigen(Bodega bodegaOrigen)
/* 313:    */   {
/* 314:417 */     this.bodegaOrigen = bodegaOrigen;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public boolean isAprobado()
/* 318:    */   {
/* 319:421 */     return this.aprobado;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setAprobado(boolean aprobado)
/* 323:    */   {
/* 324:425 */     this.aprobado = aprobado;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public List<OrdenFabricacion> getListaOrdenFabricacion()
/* 328:    */   {
/* 329:429 */     return this.listaOrdenFabricacion;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setListaOrdenFabricacion(List<OrdenFabricacion> listaOrdenFabricacion)
/* 333:    */   {
/* 334:433 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public boolean isIndicadorImprimirOFA()
/* 338:    */   {
/* 339:437 */     return this.indicadorImprimirOFA;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setIndicadorImprimirOFA(boolean indicadorImprimirOFA)
/* 343:    */   {
/* 344:441 */     this.indicadorImprimirOFA = indicadorImprimirOFA;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public List<DetalleOrdenSalidaMaterial> getListaDetalleOrdenSalidaMaterialNoEliminados()
/* 348:    */   {
/* 349:449 */     List<DetalleOrdenSalidaMaterial> lista = new ArrayList();
/* 350:450 */     for (DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial : this.listaDetalleOrdenSalidaMaterial)
/* 351:    */     {
/* 352:451 */       detalleOrdenSalidaMaterial.setListaDetalleOrdenSalidaMaterialOrdenFabricacion(EjbUtil.getEntidadesNoEliminadas(detalleOrdenSalidaMaterial.getListaDetalleOrdenSalidaMaterialOrdenFabricacion()));
/* 353:452 */       if (!detalleOrdenSalidaMaterial.isEliminado()) {
/* 354:453 */         lista.add(detalleOrdenSalidaMaterial);
/* 355:    */       }
/* 356:    */     }
/* 357:456 */     return lista;
/* 358:    */   }
/* 359:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrdenSalidaMaterial
 * JD-Core Version:    0.7.0.1
 */