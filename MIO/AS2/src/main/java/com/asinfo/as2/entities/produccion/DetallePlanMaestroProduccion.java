/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
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
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Max;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="detalle_plan_maestro_produccion")
/*  25:    */ public class DetallePlanMaestroProduccion
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="detalle_plan_maestro_produccion", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_plan_maestro_produccion")
/*  33:    */   @Column(name="id_detalle_plan_maestro_produccion")
/*  34:    */   private int idDetallePlanMaestroProduccion;
/*  35:    */   @Column(name="id_organizacion")
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal")
/*  38:    */   private int idSucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_plan_maestro_produccion", nullable=false)
/*  41:    */   private PlanMaestroProduccion planMaestroProduccion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_producto", nullable=false)
/*  44:    */   private Producto producto;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_ruta_fabricacion", nullable=true)
/*  47:    */   private RutaFabricacion rutaFabricacion;
/*  48:    */   @Column(name="proporcion_lunes", nullable=false, precision=12, scale=2)
/*  49:    */   @NotNull
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52:    */   @Max(100L)
/*  53: 74 */   private BigDecimal proporcionLunes = new BigDecimal(20);
/*  54:    */   @Column(name="proporcion_martes", nullable=false, precision=12, scale=2)
/*  55:    */   @NotNull
/*  56:    */   @Digits(integer=12, fraction=2)
/*  57:    */   @Min(0L)
/*  58:    */   @Max(100L)
/*  59: 81 */   private BigDecimal proporcionMartes = new BigDecimal(20);
/*  60:    */   @Column(name="proporcion_miercoles", nullable=false, precision=12, scale=2)
/*  61:    */   @NotNull
/*  62:    */   @Digits(integer=12, fraction=2)
/*  63:    */   @Min(0L)
/*  64:    */   @Max(100L)
/*  65: 88 */   private BigDecimal proporcionMiercoles = new BigDecimal(20);
/*  66:    */   @Column(name="proporcion_jueves", nullable=false, precision=12, scale=2)
/*  67:    */   @NotNull
/*  68:    */   @Digits(integer=12, fraction=2)
/*  69:    */   @Min(0L)
/*  70:    */   @Max(100L)
/*  71: 95 */   private BigDecimal proporcionJueves = new BigDecimal(20);
/*  72:    */   @Column(name="proporcion_viernes", nullable=false, precision=12, scale=2)
/*  73:    */   @NotNull
/*  74:    */   @Digits(integer=12, fraction=2)
/*  75:    */   @Min(0L)
/*  76:    */   @Max(100L)
/*  77:102 */   private BigDecimal proporcionViernes = new BigDecimal(20);
/*  78:    */   @Column(name="proporcion_sabado", nullable=false, precision=12, scale=2)
/*  79:    */   @NotNull
/*  80:    */   @Digits(integer=12, fraction=2)
/*  81:    */   @Min(0L)
/*  82:    */   @Max(100L)
/*  83:109 */   private BigDecimal proporcionSabado = BigDecimal.ZERO;
/*  84:    */   @Column(name="proporcion_domingo", nullable=false, precision=12, scale=2)
/*  85:    */   @NotNull
/*  86:    */   @Digits(integer=12, fraction=2)
/*  87:    */   @Min(0L)
/*  88:    */   @Max(100L)
/*  89:116 */   private BigDecimal proporcionDomingo = BigDecimal.ZERO;
/*  90:    */   @Column(name="cantidad_dias_stock", nullable=false, precision=12, scale=0)
/*  91:    */   @NotNull
/*  92:    */   @Digits(integer=12, fraction=0)
/*  93:    */   @Min(0L)
/*  94:123 */   private BigDecimal cantidadDiasStock = BigDecimal.ONE;
/*  95:    */   @Column(name="meses", nullable=false, length=50)
/*  96:    */   @NotNull
/*  97:129 */   private String meses = "0,1,2,3,4,5,6,7,8,9,10,11";
/*  98:    */   @Transient
/*  99:133 */   private String[] mesesSeleccionados = null;
/* 100:    */   
/* 101:    */   public int getId()
/* 102:    */   {
/* 103:144 */     return this.idDetallePlanMaestroProduccion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdDetallePlanMaestroProduccion()
/* 107:    */   {
/* 108:148 */     return this.idDetallePlanMaestroProduccion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdDetallePlanMaestroProduccion(int idDetallePlanMaestroProduccion)
/* 112:    */   {
/* 113:152 */     this.idDetallePlanMaestroProduccion = idDetallePlanMaestroProduccion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getIdOrganizacion()
/* 117:    */   {
/* 118:156 */     return this.idOrganizacion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdOrganizacion(int idOrganizacion)
/* 122:    */   {
/* 123:160 */     this.idOrganizacion = idOrganizacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getIdSucursal()
/* 127:    */   {
/* 128:164 */     return this.idSucursal;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdSucursal(int idSucursal)
/* 132:    */   {
/* 133:168 */     this.idSucursal = idSucursal;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public PlanMaestroProduccion getPlanMaestroProduccion()
/* 137:    */   {
/* 138:172 */     return this.planMaestroProduccion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setPlanMaestroProduccion(PlanMaestroProduccion planMaestroProduccion)
/* 142:    */   {
/* 143:176 */     this.planMaestroProduccion = planMaestroProduccion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Producto getProducto()
/* 147:    */   {
/* 148:180 */     return this.producto;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setProducto(Producto producto)
/* 152:    */   {
/* 153:184 */     this.producto = producto;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public RutaFabricacion getRutaFabricacion()
/* 157:    */   {
/* 158:188 */     return this.rutaFabricacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 162:    */   {
/* 163:192 */     this.rutaFabricacion = rutaFabricacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getProporcionLunes()
/* 167:    */   {
/* 168:196 */     return this.proporcionLunes;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setProporcionLunes(BigDecimal proporcionLunes)
/* 172:    */   {
/* 173:200 */     this.proporcionLunes = proporcionLunes;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal getProporcionMartes()
/* 177:    */   {
/* 178:204 */     return this.proporcionMartes;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setProporcionMartes(BigDecimal proporcionMartes)
/* 182:    */   {
/* 183:208 */     this.proporcionMartes = proporcionMartes;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BigDecimal getProporcionMiercoles()
/* 187:    */   {
/* 188:212 */     return this.proporcionMiercoles;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setProporcionMiercoles(BigDecimal proporcionMiercoles)
/* 192:    */   {
/* 193:216 */     this.proporcionMiercoles = proporcionMiercoles;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigDecimal getProporcionJueves()
/* 197:    */   {
/* 198:220 */     return this.proporcionJueves;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setProporcionJueves(BigDecimal proporcionJueves)
/* 202:    */   {
/* 203:224 */     this.proporcionJueves = proporcionJueves;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public BigDecimal getProporcionViernes()
/* 207:    */   {
/* 208:228 */     return this.proporcionViernes;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setProporcionViernes(BigDecimal proporcionViernes)
/* 212:    */   {
/* 213:232 */     this.proporcionViernes = proporcionViernes;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public BigDecimal getProporcionSabado()
/* 217:    */   {
/* 218:236 */     return this.proporcionSabado;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setProporcionSabado(BigDecimal proporcionSabado)
/* 222:    */   {
/* 223:240 */     this.proporcionSabado = proporcionSabado;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public BigDecimal getProporcionDomingo()
/* 227:    */   {
/* 228:244 */     return this.proporcionDomingo;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setProporcionDomingo(BigDecimal proporcionDomingo)
/* 232:    */   {
/* 233:248 */     this.proporcionDomingo = proporcionDomingo;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public BigDecimal getCantidadDiasStock()
/* 237:    */   {
/* 238:252 */     return this.cantidadDiasStock;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setCantidadDiasStock(BigDecimal cantidadDiasStock)
/* 242:    */   {
/* 243:256 */     this.cantidadDiasStock = cantidadDiasStock;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public String getMeses()
/* 247:    */   {
/* 248:260 */     return this.meses;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setMeses(String meses)
/* 252:    */   {
/* 253:264 */     this.meses = meses;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String[] getMesesSeleccionados()
/* 257:    */   {
/* 258:269 */     if (this.mesesSeleccionados == null) {
/* 259:270 */       if (this.meses.length() > 0) {
/* 260:271 */         this.mesesSeleccionados = this.meses.split(",");
/* 261:    */       } else {
/* 262:273 */         this.mesesSeleccionados = new String[0];
/* 263:    */       }
/* 264:    */     }
/* 265:277 */     return this.mesesSeleccionados;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setMesesSeleccionados(String[] mesesSeleccionados)
/* 269:    */   {
/* 270:282 */     this.mesesSeleccionados = mesesSeleccionados;
/* 271:    */     
/* 272:284 */     this.meses = "";
/* 273:285 */     for (String mes : mesesSeleccionados) {
/* 274:286 */       this.meses = (this.meses + "," + mes);
/* 275:    */     }
/* 276:288 */     if (mesesSeleccionados.length > 0) {
/* 277:289 */       this.meses = this.meses.substring(1);
/* 278:    */     }
/* 279:    */   }
/* 280:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.DetallePlanMaestroProduccion
 * JD-Core Version:    0.7.0.1
 */