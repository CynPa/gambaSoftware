/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.OneToOne;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.Max;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="costos_de_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "anio", "mes"})})
/*  33:    */ public class CostosDeFabricacion
/*  34:    */   extends EntidadBase
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="costos_de_fabricacion", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="costos_de_fabricacion")
/*  40:    */   @Column(name="id_costos_de_fabricacion")
/*  41:    */   private int idCostosDeFabricacion;
/*  42:    */   @NotNull
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   private int idOrganizacion;
/*  45:    */   @NotNull
/*  46:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  47:    */   private Integer idSucursal;
/*  48:    */   @NotNull
/*  49:    */   @Column(name="anio", nullable=false)
/*  50:    */   @Min(1000L)
/*  51:    */   @Max(5000L)
/*  52:    */   private Integer anio;
/*  53:    */   @NotNull
/*  54:    */   @Column(name="mes", nullable=false)
/*  55:    */   private Integer mes;
/*  56:    */   @NotNull
/*  57:    */   @Column(name="costo_materiales")
/*  58: 86 */   private BigDecimal costoMateriales = BigDecimal.ZERO;
/*  59:    */   @NotNull
/*  60:    */   @Column(name="costo_mano_de_obra")
/*  61: 90 */   private BigDecimal costoManoDeObra = BigDecimal.ZERO;
/*  62:    */   @NotNull
/*  63:    */   @Column(name="costo_depreciaciones")
/*  64: 94 */   private BigDecimal costoDepreciaciones = BigDecimal.ZERO;
/*  65:    */   @NotNull
/*  66:    */   @Column(name="costo_indirectos")
/*  67: 98 */   private BigDecimal costoIndirectos = BigDecimal.ZERO;
/*  68:    */   @Column(name="descripcion", length=200)
/*  69:    */   @Size(max=200)
/*  70:    */   private String descripcion;
/*  71:    */   @Transient
/*  72:106 */   private List<OrdenFabricacion> listaOrdenFabricacion = new ArrayList();
/*  73:    */   @OneToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  75:    */   private Asiento asiento;
/*  76:    */   @Enumerated(EnumType.ORDINAL)
/*  77:    */   @Column(name="estado", nullable=true)
/*  78:113 */   private Estado estado = Estado.ELABORADO;
/*  79:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="costosDeFabricacion")
/*  80:117 */   private List<DetalleCostoFabricacion> listaDetalleCostoFabricacion = new ArrayList();
/*  81:    */   @Temporal(TemporalType.DATE)
/*  82:    */   @Column(name="fecha", nullable=true)
/*  83:    */   private Date fecha;
/*  84:    */   @Transient
/*  85:    */   private Date fechaDesde;
/*  86:    */   @Transient
/*  87:    */   private Date fechaHasta;
/*  88:    */   
/*  89:    */   public int getId()
/*  90:    */   {
/*  91:144 */     return this.idCostosDeFabricacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdCostosDeFabricacion()
/*  95:    */   {
/*  96:148 */     return this.idCostosDeFabricacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdCostosDeFabricacion(int idCostosDeFabricacion)
/* 100:    */   {
/* 101:152 */     this.idCostosDeFabricacion = idCostosDeFabricacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdOrganizacion()
/* 105:    */   {
/* 106:156 */     return this.idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdOrganizacion(int idOrganizacion)
/* 110:    */   {
/* 111:160 */     this.idOrganizacion = idOrganizacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Integer getIdSucursal()
/* 115:    */   {
/* 116:164 */     return this.idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdSucursal(Integer idSucursal)
/* 120:    */   {
/* 121:168 */     this.idSucursal = idSucursal;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Integer getAnio()
/* 125:    */   {
/* 126:172 */     return this.anio;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setAnio(Integer anio)
/* 130:    */   {
/* 131:176 */     this.anio = anio;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Integer getMes()
/* 135:    */   {
/* 136:180 */     return this.mes;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setMes(Integer mes)
/* 140:    */   {
/* 141:184 */     this.mes = mes;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getCostoMateriales()
/* 145:    */   {
/* 146:188 */     return this.costoMateriales;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setCostoMateriales(BigDecimal costoMateriales)
/* 150:    */   {
/* 151:192 */     this.costoMateriales = (costoMateriales == null ? BigDecimal.ZERO : costoMateriales);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getCostoManoDeObra()
/* 155:    */   {
/* 156:196 */     return this.costoManoDeObra;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setCostoManoDeObra(BigDecimal costoManoDeObra)
/* 160:    */   {
/* 161:200 */     this.costoManoDeObra = costoManoDeObra;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public BigDecimal getCostoDepreciaciones()
/* 165:    */   {
/* 166:204 */     return this.costoDepreciaciones;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setCostoDepreciaciones(BigDecimal costoDepreciaciones)
/* 170:    */   {
/* 171:208 */     this.costoDepreciaciones = costoDepreciaciones;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public BigDecimal getCostoIndirectos()
/* 175:    */   {
/* 176:212 */     return this.costoIndirectos;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setCostoIndirectos(BigDecimal costoIndirectos)
/* 180:    */   {
/* 181:216 */     this.costoIndirectos = costoIndirectos;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getDescripcion()
/* 185:    */   {
/* 186:220 */     return this.descripcion;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setDescripcion(String descripcion)
/* 190:    */   {
/* 191:224 */     this.descripcion = descripcion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<OrdenFabricacion> getListaOrdenFabricacion()
/* 195:    */   {
/* 196:228 */     return this.listaOrdenFabricacion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaOrdenFabricacion(List<OrdenFabricacion> listaOrdenFabricacion)
/* 200:    */   {
/* 201:232 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Asiento getAsiento()
/* 205:    */   {
/* 206:236 */     return this.asiento;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setAsiento(Asiento asiento)
/* 210:    */   {
/* 211:240 */     this.asiento = asiento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Estado getEstado()
/* 215:    */   {
/* 216:244 */     if (this.estado == null) {
/* 217:245 */       this.estado = Estado.ELABORADO;
/* 218:    */     }
/* 219:247 */     return this.estado;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setEstado(Estado estado)
/* 223:    */   {
/* 224:251 */     this.estado = estado;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public List<DetalleCostoFabricacion> getListaDetalleCostoFabricacion()
/* 228:    */   {
/* 229:255 */     return this.listaDetalleCostoFabricacion;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setListaDetalleCostoFabricacion(List<DetalleCostoFabricacion> listaDetalleCostoFabricacion)
/* 233:    */   {
/* 234:259 */     this.listaDetalleCostoFabricacion = listaDetalleCostoFabricacion;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Date getFechaDesde()
/* 238:    */   {
/* 239:263 */     return this.fechaDesde;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setFechaDesde(Date fechaDesde)
/* 243:    */   {
/* 244:267 */     this.fechaDesde = fechaDesde;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Date getFechaHasta()
/* 248:    */   {
/* 249:271 */     return this.fechaHasta;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setFechaHasta(Date fechaHasta)
/* 253:    */   {
/* 254:275 */     this.fechaHasta = fechaHasta;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Date getFecha()
/* 258:    */   {
/* 259:279 */     return this.fecha;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setFecha(Date fecha)
/* 263:    */   {
/* 264:283 */     this.fecha = fecha;
/* 265:    */   }
/* 266:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.CostosDeFabricacion
 * JD-Core Version:    0.7.0.1
 */