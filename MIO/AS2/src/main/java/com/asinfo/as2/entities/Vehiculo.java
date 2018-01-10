/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.Max;
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="vehiculo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  23:    */ public class Vehiculo
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="vehiculo", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="vehiculo")
/*  30:    */   @Column(name="id_vehiculo")
/*  31:    */   private int idVehiculo;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Size(min=1, max=10)
/*  37:    */   @Column(name="codigo", nullable=false, length=10)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="placa", nullable=false, length=10)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=7, max=10)
/*  42:    */   private String placa;
/*  43:    */   @Column(name="marca", nullable=false, length=50)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=2, max=50)
/*  46:    */   private String marca;
/*  47:    */   @Column(name="modelo", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @Size(min=2, max=50)
/*  50:    */   private String modelo;
/*  51:    */   @Column(name="anio", nullable=false)
/*  52:    */   @Min(1900L)
/*  53:    */   @Max(9999L)
/*  54:    */   @NotNull
/*  55:    */   private int anio;
/*  56:    */   @Column(name="capacidad_volumen", nullable=false)
/*  57:    */   @Digits(integer=12, fraction=2)
/*  58:    */   @Min(0L)
/*  59: 75 */   private BigDecimal capacidadVolumuen = BigDecimal.ZERO;
/*  60:    */   @Column(name="capacidad_cantidad", nullable=false)
/*  61:    */   @Digits(integer=12, fraction=2)
/*  62:    */   @Min(0L)
/*  63: 80 */   private BigDecimal capacidadCantidad = BigDecimal.ZERO;
/*  64:    */   @Column(name="peso_vehiculo_vacio", nullable=false)
/*  65:    */   @Digits(integer=12, fraction=2)
/*  66:    */   @Min(0L)
/*  67: 86 */   private BigDecimal pesoVehiculoVacio = BigDecimal.ZERO;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_tipo_vehiculo", nullable=false)
/*  70:    */   @NotNull
/*  71:    */   private TipoVehiculo tipoVehiculo;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_transportista", nullable=false)
/*  74:    */   @NotNull
/*  75:    */   private Transportista transportista;
/*  76:    */   @Column(name="descripcion", nullable=false)
/*  77:    */   @Size(max=200)
/*  78:    */   private String descripcion;
/*  79:    */   @Column(name="activo", nullable=false)
/*  80:    */   private boolean activo;
/*  81:    */   @Column(name="porcentaje_tolerancia", nullable=true, precision=12, scale=2)
/*  82:    */   private BigDecimal porcentajeTolerancia;
/*  83:    */   @Transient
/*  84:    */   private String traTipoVehiculo;
/*  85:    */   @Transient
/*  86:    */   private String traTransportista;
/*  87:    */   @Transient
/*  88:    */   private boolean rendered;
/*  89:    */   
/*  90:    */   public int getId()
/*  91:    */   {
/*  92:122 */     return this.idVehiculo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Vehiculo() {}
/*  96:    */   
/*  97:    */   public Vehiculo(int idVehiculo, String codigo, String placa)
/*  98:    */   {
/*  99:130 */     this.idVehiculo = idVehiculo;
/* 100:131 */     this.codigo = codigo;
/* 101:132 */     this.placa = placa;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Vehiculo(int idVehiculo, String codigo, String marca, String placa, int anio)
/* 105:    */   {
/* 106:136 */     this.idVehiculo = idVehiculo;
/* 107:137 */     this.codigo = codigo;
/* 108:138 */     this.placa = placa;
/* 109:139 */     this.marca = marca;
/* 110:140 */     this.anio = anio;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getNombreCompletoVehiculo()
/* 114:    */   {
/* 115:144 */     return this.codigo + " " + this.placa + " " + this.marca + " " + this.anio;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getIdVehiculo()
/* 119:    */   {
/* 120:148 */     return this.idVehiculo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIdVehiculo(int idVehiculo)
/* 124:    */   {
/* 125:152 */     this.idVehiculo = idVehiculo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getIdOrganizacion()
/* 129:    */   {
/* 130:156 */     return this.idOrganizacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIdOrganizacion(int idOrganizacion)
/* 134:    */   {
/* 135:160 */     this.idOrganizacion = idOrganizacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getIdSucursal()
/* 139:    */   {
/* 140:164 */     return this.idSucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdSucursal(int idSucursal)
/* 144:    */   {
/* 145:168 */     this.idSucursal = idSucursal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getCodigo()
/* 149:    */   {
/* 150:172 */     return this.codigo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setCodigo(String codigo)
/* 154:    */   {
/* 155:176 */     this.codigo = codigo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getPlaca()
/* 159:    */   {
/* 160:180 */     return this.placa;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setPlaca(String placa)
/* 164:    */   {
/* 165:184 */     this.placa = placa;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getMarca()
/* 169:    */   {
/* 170:188 */     return this.marca;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setMarca(String marca)
/* 174:    */   {
/* 175:192 */     this.marca = marca;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getModelo()
/* 179:    */   {
/* 180:196 */     return this.modelo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setModelo(String modelo)
/* 184:    */   {
/* 185:200 */     this.modelo = modelo;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getAnio()
/* 189:    */   {
/* 190:204 */     return this.anio;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setAnio(int anio)
/* 194:    */   {
/* 195:208 */     this.anio = anio;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public BigDecimal getCapacidadVolumuen()
/* 199:    */   {
/* 200:212 */     return this.capacidadVolumuen;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setCapacidadVolumuen(BigDecimal capacidadVolumuen)
/* 204:    */   {
/* 205:216 */     this.capacidadVolumuen = capacidadVolumuen;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public BigDecimal getCapacidadCantidad()
/* 209:    */   {
/* 210:220 */     return this.capacidadCantidad;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setCapacidadCantidad(BigDecimal capacidadCantidad)
/* 214:    */   {
/* 215:224 */     this.capacidadCantidad = capacidadCantidad;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public TipoVehiculo getTipoVehiculo()
/* 219:    */   {
/* 220:228 */     return this.tipoVehiculo;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setTipoVehiculo(TipoVehiculo tipoVehiculo)
/* 224:    */   {
/* 225:232 */     this.tipoVehiculo = tipoVehiculo;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getDescripcion()
/* 229:    */   {
/* 230:236 */     return this.descripcion;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDescripcion(String descripcion)
/* 234:    */   {
/* 235:240 */     this.descripcion = descripcion;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean isActivo()
/* 239:    */   {
/* 240:244 */     return this.activo;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setActivo(boolean activo)
/* 244:    */   {
/* 245:248 */     this.activo = activo;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getTraTipoVehiculo()
/* 249:    */   {
/* 250:252 */     return this.traTipoVehiculo;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setTraTipoVehiculo(String traTipoVehiculo)
/* 254:    */   {
/* 255:256 */     this.traTipoVehiculo = traTipoVehiculo;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Transportista getTransportista()
/* 259:    */   {
/* 260:260 */     return this.transportista;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setTransportista(Transportista transportista)
/* 264:    */   {
/* 265:264 */     this.transportista = transportista;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String getTraTransportista()
/* 269:    */   {
/* 270:268 */     return this.traTransportista;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setTraTransportista(String traTransportista)
/* 274:    */   {
/* 275:272 */     this.traTransportista = traTransportista;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String toString()
/* 279:    */   {
/* 280:277 */     return this.placa;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public BigDecimal getPesoVehiculoVacio()
/* 284:    */   {
/* 285:281 */     return this.pesoVehiculoVacio;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setPesoVehiculoVacio(BigDecimal pesoVehiculoVacio)
/* 289:    */   {
/* 290:285 */     this.pesoVehiculoVacio = pesoVehiculoVacio;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public BigDecimal getPorcentajeTolerancia()
/* 294:    */   {
/* 295:289 */     return this.porcentajeTolerancia;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setPorcentajeTolerancia(BigDecimal porcentajeTolerancia)
/* 299:    */   {
/* 300:293 */     this.porcentajeTolerancia = porcentajeTolerancia;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean isRendered()
/* 304:    */   {
/* 305:297 */     return this.rendered;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setRendered(boolean rendered)
/* 309:    */   {
/* 310:301 */     this.rendered = rendered;
/* 311:    */   }
/* 312:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Vehiculo
 * JD-Core Version:    0.7.0.1
 */