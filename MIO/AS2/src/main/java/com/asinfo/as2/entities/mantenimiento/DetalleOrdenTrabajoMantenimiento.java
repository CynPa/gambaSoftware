/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
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
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.DecimalMin;
/*  24:    */ import javax.validation.constraints.Digits;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ import org.hibernate.annotations.ColumnDefault;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="detalle_orden_trabajo_mantenimiento")
/*  31:    */ public class DetalleOrdenTrabajoMantenimiento
/*  32:    */   extends EntidadBase
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="detalle_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_trabajo_mantenimiento")
/*  39:    */   @Column(name="id_detalle_orden_trabajo_mantenimiento")
/*  40:    */   private int idDetalleOrdenTrabajoMantenimiento;
/*  41:    */   @Column(name="id_organizacion")
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal")
/*  44:    */   private int idSucursal;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_orden_trabajo_mantenimiento", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_equipo", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Equipo equipo;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_componente_equipo", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private ComponenteEquipo componenteEquipo;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_actividad_mantenimiento", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private ActividadMantenimiento actividadMantenimiento;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_plan_mantenimiento", nullable=true)
/*  63:    */   private PlanMantenimiento planMantenimiento;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha_planificada_orgininal", nullable=true)
/*  66:    */   private Date fechaPlanificadaOriginal;
/*  67:    */   @Temporal(TemporalType.DATE)
/*  68:    */   @Column(name="fecha_cierre", nullable=true)
/*  69:    */   private Date fechaCierre;
/*  70:    */   @Column(name="indicador_desplazar_fecha", nullable=false)
/*  71:    */   private boolean indicadorDesplazarFecha;
/*  72:    */   @Column(name="descripcion", nullable=true, length=500)
/*  73:    */   @Size(max=500)
/*  74:    */   private String descripcion;
/*  75:    */   @Column(name="indicador_cerrada", nullable=false)
/*  76:    */   @NotNull
/*  77:    */   @ColumnDefault("'0'")
/*  78:    */   private boolean indicadorCerrada;
/*  79:    */   @Column(name="tiempo_real", nullable=true, precision=12, scale=2)
/*  80:    */   @Digits(integer=10, fraction=2)
/*  81:    */   @DecimalMin("0.00")
/*  82:    */   private BigDecimal tiempoReal;
/*  83:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenTrabajoMantenimiento")
/*  84:110 */   private List<ActividadResponsableOrdenTrabajoMantenimiento> listaResponsableOrdenTrabajoMantenimiento = new ArrayList();
/*  85:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenTrabajoMantenimiento")
/*  86:113 */   private List<ActividadMaterialOrdenTrabajoMantenimiento> listaMaterialOrdenTrabajoMantenimiento = new ArrayList();
/*  87:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenTrabajoMantenimiento")
/*  88:116 */   private List<ActividadImagenOrdenTrabajoMantenimiento> listaImagenOrdenTrabajoMantenimiento = new ArrayList();
/*  89:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenTrabajoMantenimiento")
/*  90:119 */   private List<LecturaMantenimiento> listaLecturaMantenimiento = new ArrayList();
/*  91:    */   @Column(name="horas_paro", nullable=false)
/*  92:    */   @Digits(integer=12, fraction=2)
/*  93:    */   @NotNull
/*  94:    */   @ColumnDefault("0")
/*  95:122 */   private BigDecimal horasParo = BigDecimal.ZERO;
/*  96:    */   @Transient
/*  97:    */   private CalendarioMantenimientoEntidad calendarioMantenimientoEntidad;
/*  98:    */   
/*  99:    */   public int getId()
/* 100:    */   {
/* 101:141 */     return this.idDetalleOrdenTrabajoMantenimiento;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdDetalleOrdenTrabajoMantenimiento()
/* 105:    */   {
/* 106:145 */     return this.idDetalleOrdenTrabajoMantenimiento;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdDetalleOrdenTrabajoMantenimiento(int idDetalleOrdenTrabajoMantenimiento)
/* 110:    */   {
/* 111:149 */     this.idDetalleOrdenTrabajoMantenimiento = idDetalleOrdenTrabajoMantenimiento;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getIdOrganizacion()
/* 115:    */   {
/* 116:153 */     return this.idOrganizacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdOrganizacion(int idOrganizacion)
/* 120:    */   {
/* 121:157 */     this.idOrganizacion = idOrganizacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getIdSucursal()
/* 125:    */   {
/* 126:161 */     return this.idSucursal;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setIdSucursal(int idSucursal)
/* 130:    */   {
/* 131:165 */     this.idSucursal = idSucursal;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 135:    */   {
/* 136:169 */     return this.ordenTrabajoMantenimiento;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 140:    */   {
/* 141:173 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Equipo getEquipo()
/* 145:    */   {
/* 146:177 */     return this.equipo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setEquipo(Equipo equipo)
/* 150:    */   {
/* 151:181 */     this.equipo = equipo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public ComponenteEquipo getComponenteEquipo()
/* 155:    */   {
/* 156:185 */     return this.componenteEquipo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 160:    */   {
/* 161:189 */     this.componenteEquipo = componenteEquipo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public ActividadMantenimiento getActividadMantenimiento()
/* 165:    */   {
/* 166:193 */     return this.actividadMantenimiento;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setActividadMantenimiento(ActividadMantenimiento actividadMantenimiento)
/* 170:    */   {
/* 171:197 */     this.actividadMantenimiento = actividadMantenimiento;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public PlanMantenimiento getPlanMantenimiento()
/* 175:    */   {
/* 176:201 */     return this.planMantenimiento;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setPlanMantenimiento(PlanMantenimiento planMantenimiento)
/* 180:    */   {
/* 181:205 */     this.planMantenimiento = planMantenimiento;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Date getFechaPlanificadaOriginal()
/* 185:    */   {
/* 186:209 */     return this.fechaPlanificadaOriginal;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setFechaPlanificadaOriginal(Date fechaPlanificadaOriginal)
/* 190:    */   {
/* 191:213 */     this.fechaPlanificadaOriginal = fechaPlanificadaOriginal;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isIndicadorDesplazarFecha()
/* 195:    */   {
/* 196:217 */     return this.indicadorDesplazarFecha;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setIndicadorDesplazarFecha(boolean indicadorDesplazarFecha)
/* 200:    */   {
/* 201:221 */     this.indicadorDesplazarFecha = indicadorDesplazarFecha;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CalendarioMantenimientoEntidad getCalendarioMantenimientoEntidad()
/* 205:    */   {
/* 206:225 */     return this.calendarioMantenimientoEntidad;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setCalendarioMantenimientoEntidad(CalendarioMantenimientoEntidad calendarioMantenimientoEntidad)
/* 210:    */   {
/* 211:229 */     this.calendarioMantenimientoEntidad = calendarioMantenimientoEntidad;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getDescripcion()
/* 215:    */   {
/* 216:233 */     return this.descripcion;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDescripcion(String descripcion)
/* 220:    */   {
/* 221:237 */     this.descripcion = descripcion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean isIndicadorCerrada()
/* 225:    */   {
/* 226:241 */     return this.indicadorCerrada;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setIndicadorCerrada(boolean indicadorCerrada)
/* 230:    */   {
/* 231:245 */     this.indicadorCerrada = indicadorCerrada;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Date getFechaCierre()
/* 235:    */   {
/* 236:249 */     return this.fechaCierre;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setFechaCierre(Date fechaCierre)
/* 240:    */   {
/* 241:253 */     this.fechaCierre = fechaCierre;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public BigDecimal getTiempoReal()
/* 245:    */   {
/* 246:257 */     return this.tiempoReal;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setTiempoReal(BigDecimal tiempoReal)
/* 250:    */   {
/* 251:261 */     this.tiempoReal = tiempoReal;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public List<ActividadResponsableOrdenTrabajoMantenimiento> getListaResponsableOrdenTrabajoMantenimiento()
/* 255:    */   {
/* 256:265 */     return this.listaResponsableOrdenTrabajoMantenimiento;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setListaResponsableOrdenTrabajoMantenimiento(List<ActividadResponsableOrdenTrabajoMantenimiento> listaResponsableOrdenTrabajoMantenimiento)
/* 260:    */   {
/* 261:270 */     this.listaResponsableOrdenTrabajoMantenimiento = listaResponsableOrdenTrabajoMantenimiento;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<ActividadMaterialOrdenTrabajoMantenimiento> getListaMaterialOrdenTrabajoMantenimiento()
/* 265:    */   {
/* 266:274 */     return this.listaMaterialOrdenTrabajoMantenimiento;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setListaMaterialOrdenTrabajoMantenimiento(List<ActividadMaterialOrdenTrabajoMantenimiento> listaMaterialOrdenTrabajoMantenimiento)
/* 270:    */   {
/* 271:278 */     this.listaMaterialOrdenTrabajoMantenimiento = listaMaterialOrdenTrabajoMantenimiento;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<ActividadImagenOrdenTrabajoMantenimiento> getListaImagenOrdenTrabajoMantenimiento()
/* 275:    */   {
/* 276:282 */     return this.listaImagenOrdenTrabajoMantenimiento;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setListaImagenOrdenTrabajoMantenimiento(List<ActividadImagenOrdenTrabajoMantenimiento> listaImagenOrdenTrabajoMantenimiento)
/* 280:    */   {
/* 281:286 */     this.listaImagenOrdenTrabajoMantenimiento = listaImagenOrdenTrabajoMantenimiento;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<LecturaMantenimiento> getListaLecturaMantenimiento()
/* 285:    */   {
/* 286:290 */     return this.listaLecturaMantenimiento;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaLecturaMantenimiento(List<LecturaMantenimiento> listaLecturaMantenimiento)
/* 290:    */   {
/* 291:294 */     this.listaLecturaMantenimiento = listaLecturaMantenimiento;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public BigDecimal getHorasParo()
/* 295:    */   {
/* 296:298 */     return this.horasParo;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setHorasParo(BigDecimal horasParo)
/* 300:    */   {
/* 301:302 */     this.horasParo = horasParo;
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */