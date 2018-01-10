/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.Lote;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.Unidad;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.math.RoundingMode;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import javax.persistence.Column;
/*  14:    */ import javax.persistence.Entity;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.OneToMany;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.DecimalMin;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import org.hibernate.annotations.ColumnDefault;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="detalle_orden_fabricacion_material")
/*  31:    */ public class DetalleOrdenFabricacionMaterial
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="detalle_orden_fabricacion_material", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_fabricacion_material")
/*  38:    */   @Column(name="id_detalle_orden_fabricacion_material")
/*  39:    */   private int idDetalleOrdenFabricacionMaterial;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @NotNull
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  45:    */   private Sucursal sucursal;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_orden_fabricacion", nullable=true)
/*  48:    */   private OrdenFabricacion ordenFabricacion;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_material", nullable=true)
/*  51:    */   private Producto material;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_lote", nullable=true)
/*  54:    */   private Lote lote;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_detalle_material_orden_fabricacion_padre", nullable=true)
/*  57:    */   private DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterialPadre;
/*  58:    */   @DecimalMin("0.0000")
/*  59:    */   @Column(name="cantidad", nullable=false, precision=14, scale=4)
/*  60:    */   @NotNull
/*  61: 68 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  62:    */   @Column(name="nivel", nullable=true)
/*  63:    */   private int nivel;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_unidad", nullable=true)
/*  66:    */   private Unidad unidad;
/*  67:    */   @Column(name="indicador_hoja", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private boolean indicadorHoja;
/*  70:    */   @Column(name="activo", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   @ColumnDefault("'1'")
/*  73: 84 */   private boolean activo = true;
/*  74:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenFabricacionMaterial")
/*  75: 89 */   private List<DetalleOrdenFabricacionMaterialMezcla> listaDetalleOrdenFabricacionMaterialMezcla = new ArrayList();
/*  76:    */   @Transient
/*  77:    */   private BigDecimal cantidadUtilizadaReal;
/*  78:    */   @Transient
/*  79:    */   private BigDecimal valorProducido;
/*  80:    */   @Transient
/*  81:    */   private Bodega bodegaTrabajoMaterial;
/*  82:    */   @Transient
/*  83:    */   private BigDecimal cantidadDisponible;
/*  84:    */   @DecimalMin("0.0000")
/*  85:    */   @Column(name="cantidad_batch", nullable=false, precision=12, scale=4)
/*  86:    */   @NotNull
/*  87:    */   @ColumnDefault("0")
/*  88:104 */   private BigDecimal cantidadPorCadaBatch = BigDecimal.ZERO;
/*  89:    */   @Column(name="indicador_consumo_directo", nullable=false)
/*  90:    */   @NotNull
/*  91:    */   @ColumnDefault("'0'")
/*  92:    */   private boolean indicadorConsumoDirecto;
/*  93:    */   
/*  94:    */   public int getIdDetalleOrdenFabricacionMaterial()
/*  95:    */   {
/*  96:116 */     return this.idDetalleOrdenFabricacionMaterial;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdDetalleOrdenFabricacionMaterial(int idDetalleOrdenFabricacionMaterial)
/* 100:    */   {
/* 101:120 */     this.idDetalleOrdenFabricacionMaterial = idDetalleOrdenFabricacionMaterial;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdOrganizacion()
/* 105:    */   {
/* 106:124 */     return this.idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdOrganizacion(int idOrganizacion)
/* 110:    */   {
/* 111:128 */     this.idOrganizacion = idOrganizacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Sucursal getSucursal()
/* 115:    */   {
/* 116:132 */     return this.sucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setSucursal(Sucursal sucursal)
/* 120:    */   {
/* 121:136 */     this.sucursal = sucursal;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public OrdenFabricacion getOrdenFabricacion()
/* 125:    */   {
/* 126:140 */     return this.ordenFabricacion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 130:    */   {
/* 131:144 */     this.ordenFabricacion = ordenFabricacion;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Producto getMaterial()
/* 135:    */   {
/* 136:148 */     return this.material;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setMaterial(Producto material)
/* 140:    */   {
/* 141:152 */     this.material = material;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public DetalleOrdenFabricacionMaterial getDetalleOrdenFabricacionMaterialPadre()
/* 145:    */   {
/* 146:156 */     return this.detalleOrdenFabricacionMaterialPadre;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDetalleOrdenFabricacionMaterialPadre(DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterialPadre)
/* 150:    */   {
/* 151:160 */     this.detalleOrdenFabricacionMaterialPadre = detalleOrdenFabricacionMaterialPadre;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getId()
/* 155:    */   {
/* 156:165 */     return this.idDetalleOrdenFabricacionMaterial;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getNivel()
/* 160:    */   {
/* 161:169 */     return this.nivel;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setNivel(int nivel)
/* 165:    */   {
/* 166:173 */     this.nivel = nivel;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public BigDecimal getCantidad()
/* 170:    */   {
/* 171:177 */     return this.cantidad;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setCantidad(BigDecimal cantidad)
/* 175:    */   {
/* 176:181 */     this.cantidad = cantidad;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Unidad getUnidad()
/* 180:    */   {
/* 181:185 */     return this.unidad;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setUnidad(Unidad unidad)
/* 185:    */   {
/* 186:189 */     this.unidad = unidad;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isIndicadorHoja()
/* 190:    */   {
/* 191:193 */     return this.indicadorHoja;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setIndicadorHoja(boolean indicadorHoja)
/* 195:    */   {
/* 196:197 */     this.indicadorHoja = indicadorHoja;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public BigDecimal getCantidadUtilizadaReal()
/* 200:    */   {
/* 201:201 */     return this.cantidadUtilizadaReal;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setCantidadUtilizadaReal(BigDecimal cantidadUtilizadaReal)
/* 205:    */   {
/* 206:205 */     this.cantidadUtilizadaReal = cantidadUtilizadaReal;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public BigDecimal getValorProducido()
/* 210:    */   {
/* 211:209 */     return this.valorProducido;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setValorProducido(BigDecimal valorProducido)
/* 215:    */   {
/* 216:213 */     this.valorProducido = valorProducido;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean isActivo()
/* 220:    */   {
/* 221:217 */     return this.activo;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setActivo(boolean activo)
/* 225:    */   {
/* 226:221 */     this.activo = activo;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public Bodega getBodegaTrabajoMaterial()
/* 230:    */   {
/* 231:225 */     return this.bodegaTrabajoMaterial;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setBodegaTrabajoMaterial(Bodega bodegaTrabajoMaterial)
/* 235:    */   {
/* 236:229 */     this.bodegaTrabajoMaterial = bodegaTrabajoMaterial;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public BigDecimal getCantidadDisponible()
/* 240:    */   {
/* 241:233 */     return this.cantidadDisponible;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setCantidadDisponible(BigDecimal cantidadDisponible)
/* 245:    */   {
/* 246:237 */     this.cantidadDisponible = cantidadDisponible;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Lote getLote()
/* 250:    */   {
/* 251:241 */     return this.lote;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setLote(Lote lote)
/* 255:    */   {
/* 256:245 */     this.lote = lote;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public BigDecimal getCantidadPorCadaBatch()
/* 260:    */   {
/* 261:249 */     if (this.ordenFabricacion.getCantidadBatch().subtract(this.ordenFabricacion.getCantidadBatchFabricados()).compareTo(BigDecimal.ZERO) != 0) {
/* 262:250 */       this.cantidadPorCadaBatch = this.cantidad.divide(this.ordenFabricacion.getCantidadBatch().subtract(this.ordenFabricacion.getCantidadBatchFabricados()), 2, RoundingMode.HALF_UP);
/* 263:    */     } else {
/* 264:253 */       this.cantidadPorCadaBatch = BigDecimal.ZERO;
/* 265:    */     }
/* 266:256 */     return this.cantidadPorCadaBatch;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setCantidadPorCadaBatch(BigDecimal cantidadPorCadaBatch)
/* 270:    */   {
/* 271:260 */     this.cantidadPorCadaBatch = cantidadPorCadaBatch;
/* 272:    */     
/* 273:262 */     this.cantidad = cantidadPorCadaBatch.multiply(this.ordenFabricacion.getCantidadBatch().subtract(this.ordenFabricacion.getCantidadBatchFabricados())).setScale(2, RoundingMode.HALF_UP);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<DetalleOrdenFabricacionMaterialMezcla> getListaDetalleOrdenFabricacionMaterialMezcla()
/* 277:    */   {
/* 278:266 */     return this.listaDetalleOrdenFabricacionMaterialMezcla;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setListaDetalleOrdenFabricacionMaterialMezcla(List<DetalleOrdenFabricacionMaterialMezcla> listaDetalleOrdenFabricacionMaterialMezcla)
/* 282:    */   {
/* 283:271 */     this.listaDetalleOrdenFabricacionMaterialMezcla = listaDetalleOrdenFabricacionMaterialMezcla;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public boolean isIndicadorConsumoDirecto()
/* 287:    */   {
/* 288:275 */     return this.indicadorConsumoDirecto;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setIndicadorConsumoDirecto(boolean indicadorConsumoDirecto)
/* 292:    */   {
/* 293:279 */     this.indicadorConsumoDirecto = indicadorConsumoDirecto;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setEliminado(boolean eliminado)
/* 297:    */   {
/* 298:284 */     for (DetalleOrdenFabricacionMaterialMezcla detalleOrdenFabricacionMaterialMezcla : this.listaDetalleOrdenFabricacionMaterialMezcla) {
/* 299:285 */       detalleOrdenFabricacionMaterialMezcla.setEliminado(eliminado);
/* 300:    */     }
/* 301:287 */     super.setEliminado(eliminado);
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial
 * JD-Core Version:    0.7.0.1
 */