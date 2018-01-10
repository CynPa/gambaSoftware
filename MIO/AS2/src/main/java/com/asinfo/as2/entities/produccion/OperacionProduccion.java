/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.io.Serializable;
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
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="operacion_produccion")
/*  24:    */ public class OperacionProduccion
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="operacion_produccion", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="operacion_produccion")
/*  32:    */   @Column(name="id_operacion_produccion")
/*  33:    */   private int idOperacionProduccion;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="descripcion", length=200, nullable=false)
/*  39:    */   @Size(max=200)
/*  40: 61 */   private String descripcion = "";
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   private boolean activo;
/*  43:    */   @Column(name="orden", nullable=false)
/*  44:    */   private int orden;
/*  45:    */   @NotNull
/*  46:    */   @Temporal(TemporalType.DATE)
/*  47:    */   @Column(name="fecha_desde", nullable=false)
/*  48:    */   private Date fechaDesde;
/*  49:    */   @Temporal(TemporalType.DATE)
/*  50:    */   @Column(name="fecha_hasta", nullable=true)
/*  51:    */   private Date fechaHasta;
/*  52:    */   @Column(name="indicador_punto_recuento", nullable=false)
/*  53:    */   private boolean indicadorPuntoRecuento;
/*  54:    */   @Column(name="numero_maquinas", nullable=false)
/*  55:    */   private int numeroMaquinas;
/*  56:    */   @Column(name="numero_persona", nullable=false)
/*  57:    */   private int numeroPersonas;
/*  58:    */   @Column(name="indicador_fijo", nullable=true)
/*  59: 90 */   private Boolean indicadorFijo = Boolean.valueOf(true);
/*  60:    */   @Column(name="indicador_automatico", nullable=true)
/*  61: 93 */   private Boolean indicadorAutomatico = Boolean.valueOf(false);
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_ruta_fabricacion", nullable=true)
/*  64:    */   private RutaFabricacion rutaFabricacion;
/*  65:    */   @NotNull
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_tarea_produccion", nullable=false)
/*  68:    */   private TareaProduccion tareaProduccion;
/*  69:    */   @NotNull
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_centro_trabajo", nullable=false)
/*  72:    */   private CentroTrabajo centroTrabajo;
/*  73:    */   @NotNull
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id_maquina", nullable=false)
/*  76:    */   private Maquina maquina;
/*  77:    */   
/*  78:    */   public int getIdOperacionProduccion()
/*  79:    */   {
/*  80:130 */     return this.idOperacionProduccion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOperacionProduccion(int idOperacionProduccion)
/*  84:    */   {
/*  85:140 */     this.idOperacionProduccion = idOperacionProduccion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdOrganizacion()
/*  89:    */   {
/*  90:149 */     return this.idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdOrganizacion(int idOrganizacion)
/*  94:    */   {
/*  95:159 */     this.idOrganizacion = idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdSucursal()
/*  99:    */   {
/* 100:168 */     return this.idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdSucursal(int idSucursal)
/* 104:    */   {
/* 105:178 */     this.idSucursal = idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getOrden()
/* 109:    */   {
/* 110:187 */     return this.orden;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setOrden(int orden)
/* 114:    */   {
/* 115:197 */     this.orden = orden;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Date getFechaDesde()
/* 119:    */   {
/* 120:206 */     return this.fechaDesde;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setFechaDesde(Date fechaDesde)
/* 124:    */   {
/* 125:216 */     this.fechaDesde = fechaDesde;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Date getFechaHasta()
/* 129:    */   {
/* 130:225 */     return this.fechaHasta;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setFechaHasta(Date fechaHasta)
/* 134:    */   {
/* 135:235 */     this.fechaHasta = fechaHasta;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isIndicadorPuntoRecuento()
/* 139:    */   {
/* 140:244 */     return this.indicadorPuntoRecuento;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIndicadorPuntoRecuento(boolean indicadorPuntoRecuento)
/* 144:    */   {
/* 145:254 */     this.indicadorPuntoRecuento = indicadorPuntoRecuento;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public RutaFabricacion getRutaFabricacion()
/* 149:    */   {
/* 150:263 */     return this.rutaFabricacion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 154:    */   {
/* 155:273 */     this.rutaFabricacion = rutaFabricacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public TareaProduccion getTareaProduccion()
/* 159:    */   {
/* 160:282 */     return this.tareaProduccion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setTareaProduccion(TareaProduccion tareaProduccion)
/* 164:    */   {
/* 165:292 */     this.tareaProduccion = tareaProduccion;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public CentroTrabajo getCentroTrabajo()
/* 169:    */   {
/* 170:301 */     return this.centroTrabajo;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCentroTrabajo(CentroTrabajo centroTrabajo)
/* 174:    */   {
/* 175:311 */     this.centroTrabajo = centroTrabajo;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Maquina getMaquina()
/* 179:    */   {
/* 180:320 */     return this.maquina;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setMaquina(Maquina maquina)
/* 184:    */   {
/* 185:330 */     this.maquina = maquina;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getDescripcion()
/* 189:    */   {
/* 190:339 */     return this.descripcion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDescripcion(String descripcion)
/* 194:    */   {
/* 195:349 */     this.descripcion = descripcion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean isActivo()
/* 199:    */   {
/* 200:358 */     return this.activo;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setActivo(boolean activo)
/* 204:    */   {
/* 205:368 */     this.activo = activo;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getNumeroMaquinas()
/* 209:    */   {
/* 210:377 */     return this.numeroMaquinas;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setNumeroMaquinas(int numeroMaquinas)
/* 214:    */   {
/* 215:387 */     this.numeroMaquinas = numeroMaquinas;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getNumeroPersonas()
/* 219:    */   {
/* 220:396 */     return this.numeroPersonas;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setNumeroPersonas(int numeroPersonas)
/* 224:    */   {
/* 225:406 */     this.numeroPersonas = numeroPersonas;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getId()
/* 229:    */   {
/* 230:416 */     return this.idOperacionProduccion;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isIndicadorFijo()
/* 234:    */   {
/* 235:420 */     if (this.indicadorFijo == null) {
/* 236:421 */       this.indicadorFijo = Boolean.valueOf(true);
/* 237:    */     }
/* 238:423 */     return this.indicadorFijo.booleanValue();
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setIndicadorFijo(boolean indicadorFijo)
/* 242:    */   {
/* 243:427 */     this.indicadorFijo = Boolean.valueOf(indicadorFijo);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean isIndicadorAutomatico()
/* 247:    */   {
/* 248:431 */     if (this.indicadorAutomatico == null) {
/* 249:432 */       this.indicadorAutomatico = Boolean.valueOf(false);
/* 250:    */     }
/* 251:434 */     return this.indicadorAutomatico.booleanValue();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 255:    */   {
/* 256:438 */     this.indicadorAutomatico = Boolean.valueOf(indicadorAutomatico);
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.OperacionProduccion
 * JD-Core Version:    0.7.0.1
 */