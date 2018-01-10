/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.DecimalMin;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import org.hibernate.annotations.ColumnDefault;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="lectura_mantenimiento")
/*  27:    */ public class LecturaMantenimiento
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="lectura_mantenimiento", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="lectura_mantenimiento")
/*  35:    */   @Column(name="id_lectura_mantenimiento")
/*  36:    */   private int idLecturaMantenimiento;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private int idSucursal;
/*  43:    */   @Temporal(TemporalType.DATE)
/*  44:    */   @Column(name="fecha_lectura", nullable=false)
/*  45:    */   @NotNull
/*  46: 62 */   private Date fechaLectura = new Date();
/*  47:    */   @Column(name="indicador_tiempo", nullable=true)
/*  48:    */   private boolean indicadorTiempo;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_equipo", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Equipo equipo;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_componente", nullable=true)
/*  55:    */   private ComponenteEquipo componenteEquipo;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_actividad", nullable=true)
/*  58:    */   private ActividadMantenimiento actividadMantenimiento;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_frecuencia", nullable=true)
/*  61:    */   private Frecuencia frecuencia;
/*  62:    */   @Column(name="valor", nullable=true, precision=12, scale=2)
/*  63:    */   @Digits(integer=10, fraction=2)
/*  64:    */   @DecimalMin("0.00")
/*  65:    */   private BigDecimal valor;
/*  66:    */   @Column(name="indicador_automatico", nullable=true)
/*  67:    */   @NotNull
/*  68:    */   @ColumnDefault("'0'")
/*  69:    */   private boolean indicadorAutomatico;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_detalle_orden_trabajo_mantenimiento", nullable=true)
/*  72:    */   private DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento;
/*  73:    */   @Temporal(TemporalType.DATE)
/*  74:    */   @Column(name="fecha_lectura_anterior", nullable=true)
/*  75:    */   private Date fechaLecturaAnterior;
/*  76:    */   @Column(name="valor_anterior", nullable=true, precision=12, scale=2)
/*  77:    */   @Digits(integer=10, fraction=2)
/*  78:    */   private BigDecimal valorAnterior;
/*  79:    */   @Column(name="valor_acumulado", nullable=true, precision=12, scale=2)
/*  80:    */   @Digits(integer=10, fraction=2)
/*  81:    */   private BigDecimal valorAcumulado;
/*  82:    */   @Transient
/*  83:    */   private Long numeroLecturas;
/*  84:    */   @Transient
/*  85:    */   private Integer idEquipo;
/*  86:    */   @Transient
/*  87:    */   private Integer idComponenteEquipo;
/*  88:    */   @Transient
/*  89:    */   private Integer idFrecuenciaOActividad;
/*  90:    */   
/*  91:    */   public LecturaMantenimiento() {}
/*  92:    */   
/*  93:    */   public LecturaMantenimiento(int idLecturaMantenimiento, Date fechaLectura, Date fechaLecturaAnterior, BigDecimal valor, BigDecimal valorAnterior, BigDecimal valorAcumulado, Integer idEquipo, Integer idComponenteEquipo)
/*  94:    */   {
/*  95:131 */     this.idLecturaMantenimiento = idLecturaMantenimiento;
/*  96:132 */     this.fechaLectura = fechaLectura;
/*  97:133 */     this.fechaLecturaAnterior = fechaLecturaAnterior;
/*  98:134 */     this.valor = valor;
/*  99:135 */     this.valorAnterior = valorAnterior;
/* 100:136 */     this.valorAcumulado = valorAcumulado;
/* 101:137 */     this.idEquipo = idEquipo;
/* 102:138 */     this.idComponenteEquipo = idComponenteEquipo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public LecturaMantenimiento(int idLecturaMantenimiento, Date fechaLectura, Date fechaLecturaAnterior, BigDecimal valor, BigDecimal valorAnterior, BigDecimal valorAcumulado, Integer idEquipo, Integer idComponenteEquipo, Integer idFrecuenciaOActividad)
/* 106:    */   {
/* 107:143 */     this.idLecturaMantenimiento = idLecturaMantenimiento;
/* 108:144 */     this.fechaLectura = fechaLectura;
/* 109:145 */     this.fechaLecturaAnterior = fechaLecturaAnterior;
/* 110:146 */     this.valor = valor;
/* 111:147 */     this.valorAnterior = valorAnterior;
/* 112:148 */     this.valorAcumulado = valorAcumulado;
/* 113:149 */     this.idEquipo = idEquipo;
/* 114:150 */     this.idComponenteEquipo = idComponenteEquipo;
/* 115:151 */     this.idFrecuenciaOActividad = idFrecuenciaOActividad;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getId()
/* 119:    */   {
/* 120:156 */     return this.idLecturaMantenimiento;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getIdLecturaMantenimiento()
/* 124:    */   {
/* 125:160 */     return this.idLecturaMantenimiento;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdLecturaMantenimiento(int idLecturaMantenimiento)
/* 129:    */   {
/* 130:164 */     this.idLecturaMantenimiento = idLecturaMantenimiento;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getIdOrganizacion()
/* 134:    */   {
/* 135:168 */     return this.idOrganizacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setIdOrganizacion(int idOrganizacion)
/* 139:    */   {
/* 140:172 */     this.idOrganizacion = idOrganizacion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getIdSucursal()
/* 144:    */   {
/* 145:176 */     return this.idSucursal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setIdSucursal(int idSucursal)
/* 149:    */   {
/* 150:180 */     this.idSucursal = idSucursal;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Date getFechaLectura()
/* 154:    */   {
/* 155:184 */     return this.fechaLectura;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setFechaLectura(Date fechaLectura)
/* 159:    */   {
/* 160:188 */     this.fechaLectura = fechaLectura;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean isIndicadorTiempo()
/* 164:    */   {
/* 165:192 */     return this.indicadorTiempo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setIndicadorTiempo(boolean indicadorTiempo)
/* 169:    */   {
/* 170:196 */     this.indicadorTiempo = indicadorTiempo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public ComponenteEquipo getComponenteEquipo()
/* 174:    */   {
/* 175:200 */     return this.componenteEquipo;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 179:    */   {
/* 180:204 */     this.componenteEquipo = componenteEquipo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public ActividadMantenimiento getActividadMantenimiento()
/* 184:    */   {
/* 185:208 */     return this.actividadMantenimiento;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setActividadMantenimiento(ActividadMantenimiento actividadMantenimiento)
/* 189:    */   {
/* 190:212 */     this.actividadMantenimiento = actividadMantenimiento;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public BigDecimal getValor()
/* 194:    */   {
/* 195:216 */     if (this.valor == null) {
/* 196:217 */       this.valor = BigDecimal.ZERO;
/* 197:    */     }
/* 198:219 */     return this.valor;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setValor(BigDecimal valor)
/* 202:    */   {
/* 203:223 */     this.valorAcumulado = valor.add(this.valorAnterior);
/* 204:224 */     this.valor = valor;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Equipo getEquipo()
/* 208:    */   {
/* 209:228 */     return this.equipo;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setEquipo(Equipo equipo)
/* 213:    */   {
/* 214:232 */     this.equipo = equipo;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Frecuencia getFrecuencia()
/* 218:    */   {
/* 219:236 */     return this.frecuencia;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setFrecuencia(Frecuencia frecuencia)
/* 223:    */   {
/* 224:240 */     this.frecuencia = frecuencia;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isIndicadorAutomatico()
/* 228:    */   {
/* 229:244 */     return this.indicadorAutomatico;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 233:    */   {
/* 234:248 */     this.indicadorAutomatico = indicadorAutomatico;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public DetalleOrdenTrabajoMantenimiento getDetalleOrdenTrabajoMantenimiento()
/* 238:    */   {
/* 239:252 */     return this.detalleOrdenTrabajoMantenimiento;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setDetalleOrdenTrabajoMantenimiento(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 243:    */   {
/* 244:256 */     this.detalleOrdenTrabajoMantenimiento = detalleOrdenTrabajoMantenimiento;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Date getFechaLecturaAnterior()
/* 248:    */   {
/* 249:260 */     return this.fechaLecturaAnterior;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setFechaLecturaAnterior(Date fechaLecturaAnterior)
/* 253:    */   {
/* 254:264 */     this.fechaLecturaAnterior = fechaLecturaAnterior;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public BigDecimal getValorAnterior()
/* 258:    */   {
/* 259:268 */     return this.valorAnterior;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setValorAnterior(BigDecimal valorAnterior)
/* 263:    */   {
/* 264:272 */     this.valorAnterior = valorAnterior;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public BigDecimal getValorAcumulado()
/* 268:    */   {
/* 269:276 */     if (this.valorAcumulado == null) {
/* 270:277 */       this.valorAcumulado = BigDecimal.ZERO;
/* 271:    */     }
/* 272:279 */     return this.valorAcumulado;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setValorAcumulado(BigDecimal valorAcumulado)
/* 276:    */   {
/* 277:283 */     this.valor = valorAcumulado.subtract(this.valorAnterior);
/* 278:284 */     this.valorAcumulado = valorAcumulado;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Long getNumeroLecturas()
/* 282:    */   {
/* 283:288 */     return this.numeroLecturas;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setNumeroLecturas(Long numeroLecturas)
/* 287:    */   {
/* 288:292 */     this.numeroLecturas = numeroLecturas;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Integer getIdEquipo()
/* 292:    */   {
/* 293:296 */     return this.idEquipo;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setIdEquipo(Integer idEquipo)
/* 297:    */   {
/* 298:300 */     this.idEquipo = idEquipo;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Integer getIdComponenteEquipo()
/* 302:    */   {
/* 303:304 */     return this.idComponenteEquipo;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setIdComponenteEquipo(Integer idComponenteEquipo)
/* 307:    */   {
/* 308:308 */     this.idComponenteEquipo = idComponenteEquipo;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public Integer getIdFrecuenciaOActividad()
/* 312:    */   {
/* 313:312 */     return this.idFrecuenciaOActividad;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setIdFrecuenciaOActividad(Integer idFrecuenciaOActividad)
/* 317:    */   {
/* 318:316 */     this.idFrecuenciaOActividad = idFrecuenciaOActividad;
/* 319:    */   }
/* 320:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento
 * JD-Core Version:    0.7.0.1
 */