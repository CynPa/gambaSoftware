/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.DecimalMin;
/*  19:    */ import javax.validation.constraints.Max;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="detalle_configuracion_extracto_bancario")
/*  26:    */ public class DetalleConfiguracionExtractoBancario
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="detalle_configuracion_extracto_bancario", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_configuracion_extracto_bancario")
/*  34:    */   @Column(name="id_detalle_configuracion_extracto_bancario")
/*  35:    */   private int idDetalleConfiguracionExtractoBancario;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   private int idSucursal;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_configuracion_extracto_bancario")
/*  42:    */   private ConfiguracionExtractoBancario configuracionExtractoBancario;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  45:    */   private CuentaContable cuentaContable;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_cuenta_contable2", nullable=true)
/*  48:    */   private CuentaContable cuentaContable2;
/*  49:    */   @NotNull
/*  50:    */   @Column(name="criterio_de_busqueda", length=200)
/*  51:    */   @Size(min=2, max=200)
/*  52:    */   private String criterioDeBusqueda;
/*  53:    */   @Column(name="criterio_de_busqueda2", length=200)
/*  54:    */   @Size(max=200)
/*  55: 77 */   private String criterioDeBusqueda2 = "";
/*  56:    */   @Column(name="operacion", nullable=false)
/*  57: 82 */   private int operacion = 1;
/*  58:    */   @Column(name="indicador_validar_operacion", nullable=false)
/*  59: 86 */   private boolean indicadorValidarOperacion = false;
/*  60:    */   @NotNull
/*  61:    */   @DecimalMin("0.00")
/*  62:    */   @Column(name="monto", nullable=false)
/*  63: 90 */   private BigDecimal monto = BigDecimal.ZERO;
/*  64:    */   @NotNull
/*  65:    */   @Enumerated(EnumType.STRING)
/*  66:    */   @Column(name="operacion_monto", nullable=false, length=50)
/*  67:    */   private OperacionEnum operacionMonto;
/*  68:    */   @NotNull
/*  69:    */   @Min(0L)
/*  70:    */   @Max(31L)
/*  71:    */   @Column(name="dia", nullable=false)
/*  72:100 */   private int dia = 0;
/*  73:    */   @NotNull
/*  74:    */   @Enumerated(EnumType.STRING)
/*  75:    */   @Column(name="operacion_dia", nullable=false, length=50)
/*  76:    */   private OperacionEnum operacionDia;
/*  77:    */   @Column(name="descripcion", length=200)
/*  78:    */   @Size(max=200)
/*  79:    */   private String descripcion;
/*  80:    */   @Column(name="descripcion2_cuenta_banco", length=200)
/*  81:    */   @Size(max=200)
/*  82:    */   private String descripcion2CuentaBanco;
/*  83:    */   @Column(name="descripcion2_cuenta_contrapartida", length=200)
/*  84:    */   @Size(max=200)
/*  85:    */   private String descripcion2CuentaContrapartida;
/*  86:    */   @Column(name="indicador_modifica_cuenta_contable")
/*  87:    */   private boolean indicadorModificaCuentaContable;
/*  88:    */   @Column(name="indicador_contabilizar")
/*  89:128 */   private boolean indicadorContabilizar = true;
/*  90:    */   @Column(name="orden", nullable=false)
/*  91:131 */   private int orden = 1;
/*  92:    */   @Min(1L)
/*  93:    */   @Column(name="columna_criterio_de_busqueda", length=200)
/*  94:    */   private int columnaCriterioDeBusqueda;
/*  95:    */   @Column(name="columna_criterio_de_busqueda2", length=200)
/*  96:    */   private Integer columnaCriterioDeBusqueda2;
/*  97:    */   @Min(1L)
/*  98:    */   @Max(100L)
/*  99:    */   @NotNull
/* 100:    */   @Column(name="porcentaje")
/* 101:147 */   private Integer porcentaje = Integer.valueOf(100);
/* 102:    */   
/* 103:    */   public int getId()
/* 104:    */   {
/* 105:154 */     return this.idDetalleConfiguracionExtractoBancario;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdDetalleConfiguracionExtractoBancario()
/* 109:    */   {
/* 110:158 */     return this.idDetalleConfiguracionExtractoBancario;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdDetalleConfiguracionExtractoBancario(int idDetalleConfiguracionExtractoBancario)
/* 114:    */   {
/* 115:162 */     this.idDetalleConfiguracionExtractoBancario = idDetalleConfiguracionExtractoBancario;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getIdOrganizacion()
/* 119:    */   {
/* 120:166 */     return this.idOrganizacion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIdOrganizacion(int idOrganizacion)
/* 124:    */   {
/* 125:170 */     this.idOrganizacion = idOrganizacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getIdSucursal()
/* 129:    */   {
/* 130:174 */     return this.idSucursal;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIdSucursal(int idSucursal)
/* 134:    */   {
/* 135:178 */     this.idSucursal = idSucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public ConfiguracionExtractoBancario getConfiguracionExtractoBancario()
/* 139:    */   {
/* 140:182 */     return this.configuracionExtractoBancario;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setConfiguracionExtractoBancario(ConfiguracionExtractoBancario configuracionExtractoBancario)
/* 144:    */   {
/* 145:186 */     this.configuracionExtractoBancario = configuracionExtractoBancario;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getDescripcion()
/* 149:    */   {
/* 150:190 */     return this.descripcion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setDescripcion(String descripcion)
/* 154:    */   {
/* 155:194 */     this.descripcion = descripcion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public CuentaContable getCuentaContable()
/* 159:    */   {
/* 160:198 */     return this.cuentaContable;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 164:    */   {
/* 165:202 */     this.cuentaContable = cuentaContable;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public BigDecimal getMonto()
/* 169:    */   {
/* 170:206 */     return this.monto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setMonto(BigDecimal monto)
/* 174:    */   {
/* 175:210 */     this.monto = monto;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public OperacionEnum getOperacionMonto()
/* 179:    */   {
/* 180:214 */     return this.operacionMonto;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setOperacionMonto(OperacionEnum operacionMonto)
/* 184:    */   {
/* 185:218 */     this.operacionMonto = operacionMonto;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getDia()
/* 189:    */   {
/* 190:222 */     return this.dia;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDia(int dia)
/* 194:    */   {
/* 195:226 */     this.dia = dia;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public OperacionEnum getOperacionDia()
/* 199:    */   {
/* 200:230 */     return this.operacionDia;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setOperacionDia(OperacionEnum operacionDia)
/* 204:    */   {
/* 205:234 */     this.operacionDia = operacionDia;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isIndicadorModificaCuentaContable()
/* 209:    */   {
/* 210:238 */     return this.indicadorModificaCuentaContable;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIndicadorModificaCuentaContable(boolean indicadorModificaCuentaContable)
/* 214:    */   {
/* 215:242 */     this.indicadorModificaCuentaContable = indicadorModificaCuentaContable;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isIndicadorContabilizar()
/* 219:    */   {
/* 220:246 */     return this.indicadorContabilizar;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIndicadorContabilizar(boolean indicadorContabilizar)
/* 224:    */   {
/* 225:250 */     this.indicadorContabilizar = indicadorContabilizar;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getCriterioDeBusqueda()
/* 229:    */   {
/* 230:254 */     return this.criterioDeBusqueda;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setCriterioDeBusqueda(String criterioDeBusqueda)
/* 234:    */   {
/* 235:258 */     this.criterioDeBusqueda = criterioDeBusqueda;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getCriterioDeBusqueda2()
/* 239:    */   {
/* 240:262 */     return this.criterioDeBusqueda2;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setCriterioDeBusqueda2(String criterioDeBusqueda2)
/* 244:    */   {
/* 245:266 */     this.criterioDeBusqueda2 = criterioDeBusqueda2;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getDescripcion2CuentaBanco()
/* 249:    */   {
/* 250:270 */     return this.descripcion2CuentaBanco;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setDescripcion2CuentaBanco(String descripcion2CuentaBanco)
/* 254:    */   {
/* 255:274 */     this.descripcion2CuentaBanco = descripcion2CuentaBanco;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getDescripcion2CuentaContrapartida()
/* 259:    */   {
/* 260:278 */     return this.descripcion2CuentaContrapartida;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDescripcion2CuentaContrapartida(String descripcion2CuentaContrapartida)
/* 264:    */   {
/* 265:282 */     this.descripcion2CuentaContrapartida = descripcion2CuentaContrapartida;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public int getOperacion()
/* 269:    */   {
/* 270:286 */     return this.operacion;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setOperacion(int operacion)
/* 274:    */   {
/* 275:290 */     this.operacion = operacion;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public int getOrden()
/* 279:    */   {
/* 280:294 */     return this.orden;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setOrden(int orden)
/* 284:    */   {
/* 285:298 */     this.orden = orden;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public int getColumnaCriterioDeBusqueda()
/* 289:    */   {
/* 290:302 */     return this.columnaCriterioDeBusqueda;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setColumnaCriterioDeBusqueda(int columnaCriterioDeBusqueda)
/* 294:    */   {
/* 295:306 */     this.columnaCriterioDeBusqueda = columnaCriterioDeBusqueda;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Integer getColumnaCriterioDeBusqueda2()
/* 299:    */   {
/* 300:310 */     return this.columnaCriterioDeBusqueda2;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setColumnaCriterioDeBusqueda2(Integer columnaCriterioDeBusqueda2)
/* 304:    */   {
/* 305:314 */     this.columnaCriterioDeBusqueda2 = columnaCriterioDeBusqueda2;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public CuentaContable getCuentaContable2()
/* 309:    */   {
/* 310:318 */     return this.cuentaContable2;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setCuentaContable2(CuentaContable cuentaContable2)
/* 314:    */   {
/* 315:322 */     this.cuentaContable2 = cuentaContable2;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Integer getPorcentaje()
/* 319:    */   {
/* 320:326 */     return this.porcentaje;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setPorcentaje(Integer porcentaje)
/* 324:    */   {
/* 325:330 */     this.porcentaje = porcentaje;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public boolean isIndicadorValidarOperacion()
/* 329:    */   {
/* 330:334 */     return this.indicadorValidarOperacion;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setIndicadorValidarOperacion(boolean indicadorValidarOperacion)
/* 334:    */   {
/* 335:338 */     this.indicadorValidarOperacion = indicadorValidarOperacion;
/* 336:    */   }
/* 337:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleConfiguracionExtractoBancario
 * JD-Core Version:    0.7.0.1
 */