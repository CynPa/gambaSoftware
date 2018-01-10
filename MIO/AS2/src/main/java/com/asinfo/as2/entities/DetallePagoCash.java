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
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ import org.hibernate.annotations.ColumnDefault;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_pago_cash")
/*  22:    */ public class DetallePagoCash
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 7337657570519425441L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_pago_cash", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_pago_cash")
/*  29:    */   @Column(name="id_detalle_pago_cash")
/*  30:    */   private int idDetallePagoCash;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_pago_cash", nullable=true)
/*  37:    */   private PagoCash pagoCash;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_proveedor", nullable=true)
/*  40:    */   private Proveedor proveedor;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  43:    */   private Empleado empleado;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_cuenta_por_pagar", nullable=true)
/*  46:    */   private CuentaPorPagar cuentaPorPagar;
/*  47:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  48:    */   @Digits(integer=12, fraction=2)
/*  49:    */   @Min(0L)
/*  50: 74 */   private BigDecimal valor = BigDecimal.ZERO;
/*  51:    */   @Column(name="indicador_aprobado", nullable=false)
/*  52:    */   private boolean indicadorAprobado;
/*  53:    */   @Column(name="indicador_procesado", nullable=false)
/*  54:    */   private boolean indicadorProcesado;
/*  55:    */   @Column(name="descripcion", nullable=true, length=200)
/*  56:    */   @Size(max=200)
/*  57:    */   private String descripcion;
/*  58:    */   @Column(name="cuenta_bancaria_empleado", nullable=true, length=20)
/*  59:    */   private String cuentaBancariaEmpleado;
/*  60:    */   @Column(name="codigo_banco_cuenta_bancaria_empleado", nullable=true, length=20)
/*  61:    */   private String codigoBancoCuentaBancariaEmpleado;
/*  62:    */   @Column(name="direccion_empleado", nullable=true, length=50)
/*  63:    */   private String direccionEmpleado;
/*  64:    */   @Column(name="telefono_empleado", nullable=true, length=13)
/*  65:    */   private String telefonoEmpleado;
/*  66:    */   @Column(name="ciudad_empleado", nullable=true, length=50)
/*  67:    */   private String ciudadEmpleado;
/*  68:    */   @Column(name="cuenta_bancaria_empresa", nullable=true, length=20)
/*  69:    */   private String cuentaBancariaEmpresa;
/*  70:    */   @Column(name="referencia_pago_empleado", nullable=true, length=200)
/*  71:    */   private String referenciaPagoEmpleado;
/*  72:    */   @Column(name="tipo_cuenta_bancaria", nullable=true, length=10)
/*  73:    */   private String tipoCuentaBancaria;
/*  74:    */   @Column(name="identificacion_empleado", nullable=true, length=20)
/*  75:    */   private String identificacionEmpleado;
/*  76:    */   @Column(name="tipo_servicio_cuenta_bancaria", nullable=true, length=50)
/*  77:    */   private String tipoServicioCuentaBancaria;
/*  78:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  79:    */   @JoinColumn(name="id_detalle_orden_pago_proveedor", nullable=true)
/*  80:    */   private DetalleOrdenPagoProveedor detalleOrdenPagoProveedor;
/*  81:    */   @Column(name="nombre_banco", nullable=true, length=50)
/*  82:    */   private String nombreBanco;
/*  83:    */   @Column(name="codigo_banco", nullable=true)
/*  84:    */   @Size(max=50)
/*  85:    */   @ColumnDefault("''")
/*  86:    */   private String codigoBanco;
/*  87:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  88:    */   @JoinColumn(name="id_persona_responsable", nullable=true)
/*  89:    */   private PersonaResponsable personaResponsable;
/*  90:    */   @Transient
/*  91:    */   private boolean indicadorDeshabilitarIngreso;
/*  92:    */   @Transient
/*  93:    */   private int diasVencidos;
/*  94:    */   
/*  95:    */   public DetallePagoCash() {}
/*  96:    */   
/*  97:    */   public DetallePagoCash(BigDecimal valor)
/*  98:    */   {
/*  99:146 */     this.valor = valor;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getId()
/* 103:    */   {
/* 104:156 */     return this.idDetallePagoCash;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIdDetallePagoCash()
/* 108:    */   {
/* 109:165 */     return this.idDetallePagoCash;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setIdDetallePagoCash(int idDetallePagoCash)
/* 113:    */   {
/* 114:175 */     this.idDetallePagoCash = idDetallePagoCash;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getIdOrganizacion()
/* 118:    */   {
/* 119:184 */     return this.idOrganizacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdOrganizacion(int idOrganizacion)
/* 123:    */   {
/* 124:194 */     this.idOrganizacion = idOrganizacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getIdSucursal()
/* 128:    */   {
/* 129:203 */     return this.idSucursal;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdSucursal(int idSucursal)
/* 133:    */   {
/* 134:213 */     this.idSucursal = idSucursal;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public PagoCash getPagoCash()
/* 138:    */   {
/* 139:222 */     return this.pagoCash;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setPagoCash(PagoCash pagoCash)
/* 143:    */   {
/* 144:232 */     this.pagoCash = pagoCash;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Proveedor getProveedor()
/* 148:    */   {
/* 149:241 */     return this.proveedor;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setProveedor(Proveedor proveedor)
/* 153:    */   {
/* 154:251 */     this.proveedor = proveedor;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public CuentaPorPagar getCuentaPorPagar()
/* 158:    */   {
/* 159:260 */     return this.cuentaPorPagar;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setCuentaPorPagar(CuentaPorPagar cuentaPorPagar)
/* 163:    */   {
/* 164:270 */     this.cuentaPorPagar = cuentaPorPagar;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getValor()
/* 168:    */   {
/* 169:279 */     return this.valor;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setValor(BigDecimal valor)
/* 173:    */   {
/* 174:289 */     this.valor = valor;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean isIndicadorAprobado()
/* 178:    */   {
/* 179:298 */     return this.indicadorAprobado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIndicadorAprobado(boolean indicadorAprobado)
/* 183:    */   {
/* 184:308 */     this.indicadorAprobado = indicadorAprobado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isIndicadorProcesado()
/* 188:    */   {
/* 189:317 */     return this.indicadorProcesado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setIndicadorProcesado(boolean indicadorProcesado)
/* 193:    */   {
/* 194:327 */     this.indicadorProcesado = indicadorProcesado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isIndicadorDeshabilitarIngreso()
/* 198:    */   {
/* 199:336 */     return this.indicadorDeshabilitarIngreso;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setIndicadorDeshabilitarIngreso(boolean indicadorDeshabilitarIngreso)
/* 203:    */   {
/* 204:346 */     this.indicadorDeshabilitarIngreso = indicadorDeshabilitarIngreso;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getDescripcion()
/* 208:    */   {
/* 209:355 */     return this.descripcion;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setDescripcion(String descripcion)
/* 213:    */   {
/* 214:365 */     this.descripcion = descripcion;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Empleado getEmpleado()
/* 218:    */   {
/* 219:374 */     return this.empleado;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setEmpleado(Empleado empleado)
/* 223:    */   {
/* 224:384 */     this.empleado = empleado;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCuentaBancariaEmpleado()
/* 228:    */   {
/* 229:393 */     return this.cuentaBancariaEmpleado;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setCuentaBancariaEmpleado(String cuentaBancariaEmpleado)
/* 233:    */   {
/* 234:403 */     this.cuentaBancariaEmpleado = cuentaBancariaEmpleado;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getDireccionEmpleado()
/* 238:    */   {
/* 239:412 */     return this.direccionEmpleado;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setDireccionEmpleado(String direccionEmpleado)
/* 243:    */   {
/* 244:422 */     this.direccionEmpleado = direccionEmpleado;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getTelefonoEmpleado()
/* 248:    */   {
/* 249:431 */     return this.telefonoEmpleado;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setTelefonoEmpleado(String telefonoEmpleado)
/* 253:    */   {
/* 254:441 */     this.telefonoEmpleado = telefonoEmpleado;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getCiudadEmpleado()
/* 258:    */   {
/* 259:450 */     return this.ciudadEmpleado;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setCiudadEmpleado(String ciudadEmpleado)
/* 263:    */   {
/* 264:460 */     this.ciudadEmpleado = ciudadEmpleado;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getCuentaBancariaEmpresa()
/* 268:    */   {
/* 269:469 */     return this.cuentaBancariaEmpresa;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setCuentaBancariaEmpresa(String cuentaBancariaEmpresa)
/* 273:    */   {
/* 274:479 */     this.cuentaBancariaEmpresa = cuentaBancariaEmpresa;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getReferenciaPagoEmpleado()
/* 278:    */   {
/* 279:488 */     return this.referenciaPagoEmpleado;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setReferenciaPagoEmpleado(String referenciaPagoEmpleado)
/* 283:    */   {
/* 284:498 */     this.referenciaPagoEmpleado = referenciaPagoEmpleado;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String getTipoCuentaBancaria()
/* 288:    */   {
/* 289:507 */     return this.tipoCuentaBancaria;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setTipoCuentaBancaria(String tipoCuentaBancaria)
/* 293:    */   {
/* 294:517 */     this.tipoCuentaBancaria = tipoCuentaBancaria;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public String getIdentificacionEmpleado()
/* 298:    */   {
/* 299:526 */     return this.identificacionEmpleado;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setIdentificacionEmpleado(String identificacionEmpleado)
/* 303:    */   {
/* 304:536 */     this.identificacionEmpleado = identificacionEmpleado;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public String toString()
/* 308:    */   {
/* 309:546 */     return "DetallePagoCash [idDetallePagoCash=" + this.idDetallePagoCash + ", idOrganizacion=" + this.idOrganizacion + ", idSucursal=" + this.idSucursal + ", pagoCash=" + this.pagoCash + ", proveedor=" + this.proveedor + ", empleado=" + this.empleado + ", cuentaPorPagar=" + this.cuentaPorPagar + ", valor=" + this.valor + ", indicadorAprobado=" + this.indicadorAprobado + ", indicadorProcesado=" + this.indicadorProcesado + ", descripcion=" + this.descripcion + ", cuentaBancariaEmpleado=" + this.cuentaBancariaEmpleado + ", direccionEmpleado=" + this.direccionEmpleado + ", telefonoEmpleado=" + this.telefonoEmpleado + ", ciudadEmpleado=" + this.ciudadEmpleado + ", cuentaBancariaEmpresa=" + this.cuentaBancariaEmpresa + ", referenciaPagoEmpleado=" + this.referenciaPagoEmpleado + ", indicadorDeshabilitarIngreso=" + this.indicadorDeshabilitarIngreso + "]";
/* 310:    */   }
/* 311:    */   
/* 312:    */   public String getCodigoBancoCuentaBancariaEmpleado()
/* 313:    */   {
/* 314:555 */     return this.codigoBancoCuentaBancariaEmpleado;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setCodigoBancoCuentaBancariaEmpleado(String codigoBancoCuentaBancariaEmpleado)
/* 318:    */   {
/* 319:559 */     this.codigoBancoCuentaBancariaEmpleado = codigoBancoCuentaBancariaEmpleado;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public String getTipoServicioCuentaBancaria()
/* 323:    */   {
/* 324:566 */     return this.tipoServicioCuentaBancaria;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setTipoServicioCuentaBancaria(String tipoServicioCuentaBancaria)
/* 328:    */   {
/* 329:574 */     this.tipoServicioCuentaBancaria = tipoServicioCuentaBancaria;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public int getDiasVencidos()
/* 333:    */   {
/* 334:578 */     return this.diasVencidos;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setDiasVencidos(int diasVencidos)
/* 338:    */   {
/* 339:582 */     this.diasVencidos = diasVencidos;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public DetalleOrdenPagoProveedor getDetalleOrdenPagoProveedor()
/* 343:    */   {
/* 344:586 */     return this.detalleOrdenPagoProveedor;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setDetalleOrdenPagoProveedor(DetalleOrdenPagoProveedor detalleOrdenPagoProveedor)
/* 348:    */   {
/* 349:590 */     this.detalleOrdenPagoProveedor = detalleOrdenPagoProveedor;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public String getNombreBanco()
/* 353:    */   {
/* 354:594 */     return this.nombreBanco;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void setNombreBanco(String nombreBanco)
/* 358:    */   {
/* 359:598 */     this.nombreBanco = nombreBanco;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public String getCodigoBanco()
/* 363:    */   {
/* 364:602 */     return this.codigoBanco;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void setCodigoBanco(String codigoBanco)
/* 368:    */   {
/* 369:606 */     this.codigoBanco = codigoBanco;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public PersonaResponsable getPersonaResponsable()
/* 373:    */   {
/* 374:610 */     return this.personaResponsable;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 378:    */   {
/* 379:614 */     this.personaResponsable = personaResponsable;
/* 380:    */   }
/* 381:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePagoCash
 * JD-Core Version:    0.7.0.1
 */