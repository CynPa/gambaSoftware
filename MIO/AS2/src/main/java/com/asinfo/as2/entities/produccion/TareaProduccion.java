/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CentroTrabajo;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="tarea_produccion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class TareaProduccion
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="tarea_produccion", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tarea_produccion")
/*  29:    */   @Column(name="id_tarea_produccion")
/*  30:    */   private int idTareaProduccion;
/*  31:    */   @Column(name="id_organizacion")
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal")
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=20)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=1, max=20)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="nombre", nullable=false, length=100)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=2, max=100)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", length=200, nullable=true)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @Column(name="activo", nullable=false)
/*  49:    */   private boolean activo;
/*  50:    */   @Column(name="indicador_posconsumo", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private boolean indicadorPosconsumo;
/*  53:    */   @Column(name="numero_maquinas", nullable=false)
/*  54:    */   private int numeroMaquinas;
/*  55:    */   @Column(name="numero_persona", nullable=false)
/*  56:    */   private int numeroPersonas;
/*  57:    */   @Column(name="tiempo_entrega", nullable=false)
/*  58:    */   private int tiempoEntrega;
/*  59:    */   @NotNull
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_centro_trabajo", nullable=false)
/*  62:    */   private CentroTrabajo centroTrabajo;
/*  63:    */   @NotNull
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_maquina", nullable=false)
/*  66:    */   private Maquina maquina;
/*  67:    */   @NotNull
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_tarifa_operacion", nullable=true)
/*  70:    */   private TarifaOperacion tarifaOperacion;
/*  71:    */   @Column(name="indicador_fijo", nullable=true)
/*  72:108 */   private Boolean indicadorFijo = Boolean.valueOf(true);
/*  73:    */   @Column(name="indicador_automatico", nullable=true)
/*  74:111 */   private Boolean indicadorAutomatico = Boolean.valueOf(false);
/*  75:    */   
/*  76:    */   public TareaProduccion() {}
/*  77:    */   
/*  78:    */   public TareaProduccion(int idTareaProduccion, String codigo, String nombre)
/*  79:    */   {
/*  80:126 */     this.idTareaProduccion = idTareaProduccion;
/*  81:127 */     this.codigo = codigo;
/*  82:128 */     this.nombre = nombre;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdTareaProduccion()
/*  86:    */   {
/*  87:138 */     return this.idTareaProduccion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdTareaProduccion(int idTareaProduccion)
/*  91:    */   {
/*  92:148 */     this.idTareaProduccion = idTareaProduccion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdOrganizacion()
/*  96:    */   {
/*  97:157 */     return this.idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdOrganizacion(int idOrganizacion)
/* 101:    */   {
/* 102:167 */     this.idOrganizacion = idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getIdSucursal()
/* 106:    */   {
/* 107:176 */     return this.idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdSucursal(int idSucursal)
/* 111:    */   {
/* 112:186 */     this.idSucursal = idSucursal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getCodigo()
/* 116:    */   {
/* 117:195 */     return this.codigo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setCodigo(String codigo)
/* 121:    */   {
/* 122:205 */     this.codigo = codigo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getNombre()
/* 126:    */   {
/* 127:214 */     return this.nombre;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setNombre(String nombre)
/* 131:    */   {
/* 132:224 */     this.nombre = nombre;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getDescripcion()
/* 136:    */   {
/* 137:233 */     return this.descripcion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDescripcion(String descripcion)
/* 141:    */   {
/* 142:243 */     this.descripcion = descripcion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isPredeterminado()
/* 146:    */   {
/* 147:252 */     return this.predeterminado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setPredeterminado(boolean predeterminado)
/* 151:    */   {
/* 152:262 */     this.predeterminado = predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isActivo()
/* 156:    */   {
/* 157:271 */     return this.activo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setActivo(boolean activo)
/* 161:    */   {
/* 162:281 */     this.activo = activo;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public CentroTrabajo getCentroTrabajo()
/* 166:    */   {
/* 167:290 */     return this.centroTrabajo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setCentroTrabajo(CentroTrabajo centroTrabajo)
/* 171:    */   {
/* 172:300 */     this.centroTrabajo = centroTrabajo;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Maquina getMaquina()
/* 176:    */   {
/* 177:309 */     return this.maquina;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setMaquina(Maquina maquina)
/* 181:    */   {
/* 182:319 */     this.maquina = maquina;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isIndicadorPosconsumo()
/* 186:    */   {
/* 187:328 */     return this.indicadorPosconsumo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIndicadorPosconsumo(boolean indicadorPosconsumo)
/* 191:    */   {
/* 192:338 */     this.indicadorPosconsumo = indicadorPosconsumo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getNumeroMaquinas()
/* 196:    */   {
/* 197:347 */     return this.numeroMaquinas;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setNumeroMaquinas(int numeroMaquinas)
/* 201:    */   {
/* 202:357 */     this.numeroMaquinas = numeroMaquinas;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getNumeroPersonas()
/* 206:    */   {
/* 207:366 */     return this.numeroPersonas;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setNumeroPersonas(int numeroPersonas)
/* 211:    */   {
/* 212:376 */     this.numeroPersonas = numeroPersonas;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int getTiempoEntrega()
/* 216:    */   {
/* 217:385 */     return this.tiempoEntrega;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setTiempoEntrega(int tiempoEntrega)
/* 221:    */   {
/* 222:395 */     this.tiempoEntrega = tiempoEntrega;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public TarifaOperacion getTarifaOperacion()
/* 226:    */   {
/* 227:404 */     return this.tarifaOperacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setTarifaOperacion(TarifaOperacion tarifaOperacion)
/* 231:    */   {
/* 232:414 */     this.tarifaOperacion = tarifaOperacion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int getId()
/* 236:    */   {
/* 237:424 */     return this.idTareaProduccion;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isIndicadorFijo()
/* 241:    */   {
/* 242:428 */     if (this.indicadorFijo == null) {
/* 243:429 */       this.indicadorFijo = Boolean.valueOf(true);
/* 244:    */     }
/* 245:431 */     return this.indicadorFijo.booleanValue();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIndicadorFijo(boolean indicadorFijo)
/* 249:    */   {
/* 250:435 */     this.indicadorFijo = Boolean.valueOf(indicadorFijo);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public boolean isIndicadorAutomatico()
/* 254:    */   {
/* 255:439 */     if (this.indicadorAutomatico == null) {
/* 256:440 */       this.indicadorAutomatico = Boolean.valueOf(false);
/* 257:    */     }
/* 258:442 */     return this.indicadorAutomatico.booleanValue();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 262:    */   {
/* 263:446 */     this.indicadorAutomatico = Boolean.valueOf(indicadorAutomatico);
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.TareaProduccion
 * JD-Core Version:    0.7.0.1
 */