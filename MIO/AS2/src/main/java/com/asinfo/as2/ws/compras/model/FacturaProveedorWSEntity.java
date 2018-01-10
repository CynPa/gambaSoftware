/*   1:    */ package com.asinfo.as2.ws.compras.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Calendar;
/*   6:    */ 
/*   7:    */ public class FacturaProveedorWSEntity
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11:    */   private Long idOrganizacion;
/*  12:    */   private Long idSucursal;
/*  13:    */   private Long idFacturaProveedor;
/*  14:    */   private Long idDocumento;
/*  15:    */   private Long idEmpresa;
/*  16:    */   private String numero;
/*  17:    */   private String establecimiento;
/*  18:    */   private String puntoDeVenta;
/*  19:    */   private String numeroFactura;
/*  20:    */   private String autorizacion;
/*  21:    */   private Calendar fechaRegistro;
/*  22:    */   private Calendar fechaEmision;
/*  23:    */   private Calendar fechaVencimiento;
/*  24:    */   private Long idFormaPago;
/*  25:    */   private Long idCondicionPago;
/*  26:    */   private int numeroCuotas;
/*  27:    */   private Long idDireccionEmpresa;
/*  28: 63 */   private BigDecimal porcentajeImpuesto = BigDecimal.ZERO.setScale(2);
/*  29: 65 */   private BigDecimal totalImpuesto = BigDecimal.ZERO.setScale(2);
/*  30: 67 */   private BigDecimal totalImporte = BigDecimal.ZERO.setScale(2);
/*  31:    */   private DetalleFacturaProveedorWSEntity[] listaDetalleFacturaProveedor;
/*  32:    */   private boolean documentoTributario;
/*  33:    */   private boolean saldoInicial;
/*  34:    */   private boolean documentoExterior;
/*  35: 77 */   private String descripcion = "";
/*  36:    */   private String numeroPacking;
/*  37:    */   private String cliente;
/*  38:    */   private String subcliente;
/*  39:    */   private String guiaMadre;
/*  40: 87 */   private BigDecimal totalCajas = BigDecimal.ZERO.setScale(2);
/*  41: 89 */   private BigDecimal totalPiezas = BigDecimal.ZERO.setScale(2);
/*  42: 91 */   private BigDecimal totalTallos = BigDecimal.ZERO.setScale(2);
/*  43:    */   private String dae;
/*  44:    */   private String guiaHija;
/*  45:    */   private String agenciaCarga;
/*  46:    */   private String finca;
/*  47:    */   private String invoice;
/*  48:    */   
/*  49:    */   public Long getIdOrganizacion()
/*  50:    */   {
/*  51:107 */     return this.idOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdOrganizacion(Long idOrganizacion)
/*  55:    */   {
/*  56:111 */     this.idOrganizacion = idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Long getIdSucursal()
/*  60:    */   {
/*  61:115 */     return this.idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdSucursal(Long idSucursal)
/*  65:    */   {
/*  66:119 */     this.idSucursal = idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Long getIdFacturaProveedor()
/*  70:    */   {
/*  71:123 */     return this.idFacturaProveedor;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdFacturaProveedor(Long idFacturaProveedor)
/*  75:    */   {
/*  76:127 */     this.idFacturaProveedor = idFacturaProveedor;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Long getIdDocumento()
/*  80:    */   {
/*  81:131 */     return this.idDocumento;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdDocumento(Long idDocumento)
/*  85:    */   {
/*  86:135 */     this.idDocumento = idDocumento;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Long getIdEmpresa()
/*  90:    */   {
/*  91:139 */     return this.idEmpresa;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdEmpresa(Long idEmpresa)
/*  95:    */   {
/*  96:143 */     this.idEmpresa = idEmpresa;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getEstablecimiento()
/* 100:    */   {
/* 101:147 */     return this.establecimiento;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setEstablecimiento(String establecimiento)
/* 105:    */   {
/* 106:151 */     this.establecimiento = establecimiento;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getPuntoDeVenta()
/* 110:    */   {
/* 111:155 */     return this.puntoDeVenta;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setPuntoDeVenta(String puntoDeVenta)
/* 115:    */   {
/* 116:159 */     this.puntoDeVenta = puntoDeVenta;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getNumeroFactura()
/* 120:    */   {
/* 121:163 */     return this.numeroFactura;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setNumeroFactura(String numeroFactura)
/* 125:    */   {
/* 126:167 */     this.numeroFactura = numeroFactura;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Calendar getFechaRegistro()
/* 130:    */   {
/* 131:171 */     return this.fechaRegistro;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setFechaRegistro(Calendar fechaRegistro)
/* 135:    */   {
/* 136:175 */     this.fechaRegistro = fechaRegistro;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Calendar getFechaEmision()
/* 140:    */   {
/* 141:179 */     return this.fechaEmision;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setFechaEmision(Calendar fechaEmision)
/* 145:    */   {
/* 146:183 */     this.fechaEmision = fechaEmision;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Calendar getFechaVencimiento()
/* 150:    */   {
/* 151:187 */     return this.fechaVencimiento;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFechaVencimiento(Calendar fechaVencimiento)
/* 155:    */   {
/* 156:191 */     this.fechaVencimiento = fechaVencimiento;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Long getIdFormaPago()
/* 160:    */   {
/* 161:195 */     return this.idFormaPago;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIdFormaPago(Long idFormaPago)
/* 165:    */   {
/* 166:199 */     this.idFormaPago = idFormaPago;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Long getIdCondicionPago()
/* 170:    */   {
/* 171:203 */     return this.idCondicionPago;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIdCondicionPago(Long idCondicionPago)
/* 175:    */   {
/* 176:207 */     this.idCondicionPago = idCondicionPago;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getNumeroCuotas()
/* 180:    */   {
/* 181:211 */     return this.numeroCuotas;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setNumeroCuotas(int numeroCuotas)
/* 185:    */   {
/* 186:215 */     this.numeroCuotas = numeroCuotas;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Long getIdDireccionEmpresa()
/* 190:    */   {
/* 191:219 */     return this.idDireccionEmpresa;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setIdDireccionEmpresa(Long idDireccionEmpresa)
/* 195:    */   {
/* 196:223 */     this.idDireccionEmpresa = idDireccionEmpresa;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isSaldoInicial()
/* 200:    */   {
/* 201:227 */     return this.saldoInicial;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setSaldoInicial(boolean saldoInicial)
/* 205:    */   {
/* 206:231 */     this.saldoInicial = saldoInicial;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isDocumentoExterior()
/* 210:    */   {
/* 211:235 */     return this.documentoExterior;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setDocumentoExterior(boolean documentoExterior)
/* 215:    */   {
/* 216:239 */     this.documentoExterior = documentoExterior;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public BigDecimal getPorcentajeImpuesto()
/* 220:    */   {
/* 221:243 */     return this.porcentajeImpuesto;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/* 225:    */   {
/* 226:247 */     this.porcentajeImpuesto = porcentajeImpuesto;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public BigDecimal getTotalImpuesto()
/* 230:    */   {
/* 231:251 */     return this.totalImpuesto;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setTotalImpuesto(BigDecimal totalImpuesto)
/* 235:    */   {
/* 236:255 */     this.totalImpuesto = totalImpuesto;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public BigDecimal getTotalImporte()
/* 240:    */   {
/* 241:259 */     return this.totalImporte;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setTotalImporte(BigDecimal totalImporte)
/* 245:    */   {
/* 246:263 */     this.totalImporte = totalImporte;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public DetalleFacturaProveedorWSEntity[] getListaDetalleFacturaProveedor()
/* 250:    */   {
/* 251:267 */     return this.listaDetalleFacturaProveedor;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaDetalleFacturaProveedor(DetalleFacturaProveedorWSEntity[] listaDetalleFacturaProveedor)
/* 255:    */   {
/* 256:271 */     this.listaDetalleFacturaProveedor = listaDetalleFacturaProveedor;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String getDescripcion()
/* 260:    */   {
/* 261:275 */     return this.descripcion;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setDescripcion(String descripcion)
/* 265:    */   {
/* 266:279 */     this.descripcion = descripcion;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getNumero()
/* 270:    */   {
/* 271:283 */     return this.numero;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setNumero(String numero)
/* 275:    */   {
/* 276:287 */     this.numero = numero;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public boolean isDocumentoTributario()
/* 280:    */   {
/* 281:291 */     return this.documentoTributario;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setDocumentoTributario(boolean documentoTributario)
/* 285:    */   {
/* 286:295 */     this.documentoTributario = documentoTributario;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public String getAutorizacion()
/* 290:    */   {
/* 291:299 */     return this.autorizacion;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setAutorizacion(String autorizacion)
/* 295:    */   {
/* 296:303 */     this.autorizacion = autorizacion;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public String getNumeroPacking()
/* 300:    */   {
/* 301:307 */     return this.numeroPacking;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setNumeroPacking(String numeroPacking)
/* 305:    */   {
/* 306:311 */     this.numeroPacking = numeroPacking;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String getGuiaMadre()
/* 310:    */   {
/* 311:315 */     return this.guiaMadre;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setGuiaMadre(String guiaMadre)
/* 315:    */   {
/* 316:319 */     this.guiaMadre = guiaMadre;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public BigDecimal getTotalCajas()
/* 320:    */   {
/* 321:323 */     return this.totalCajas;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setTotalCajas(BigDecimal totalCajas)
/* 325:    */   {
/* 326:327 */     this.totalCajas = totalCajas;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public BigDecimal getTotalPiezas()
/* 330:    */   {
/* 331:331 */     return this.totalPiezas;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setTotalPiezas(BigDecimal totalPiezas)
/* 335:    */   {
/* 336:335 */     this.totalPiezas = totalPiezas;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public BigDecimal getTotalTallos()
/* 340:    */   {
/* 341:339 */     return this.totalTallos;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setTotalTallos(BigDecimal totalTallos)
/* 345:    */   {
/* 346:343 */     this.totalTallos = totalTallos;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String getDae()
/* 350:    */   {
/* 351:347 */     return this.dae;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setDae(String dae)
/* 355:    */   {
/* 356:351 */     this.dae = dae;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public String getGuiaHija()
/* 360:    */   {
/* 361:355 */     return this.guiaHija;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setGuiaHija(String guiaHija)
/* 365:    */   {
/* 366:359 */     this.guiaHija = guiaHija;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String getAgenciaCarga()
/* 370:    */   {
/* 371:363 */     return this.agenciaCarga;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setAgenciaCarga(String agenciaCarga)
/* 375:    */   {
/* 376:367 */     this.agenciaCarga = agenciaCarga;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public String getFinca()
/* 380:    */   {
/* 381:371 */     return this.finca;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setFinca(String finca)
/* 385:    */   {
/* 386:375 */     this.finca = finca;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public String getInvoice()
/* 390:    */   {
/* 391:379 */     return this.invoice;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setInvoice(String invoice)
/* 395:    */   {
/* 396:383 */     this.invoice = invoice;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public String getCliente()
/* 400:    */   {
/* 401:387 */     return this.cliente;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setCliente(String cliente)
/* 405:    */   {
/* 406:391 */     this.cliente = cliente;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public String getSubcliente()
/* 410:    */   {
/* 411:395 */     return this.subcliente;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setSubcliente(String subcliente)
/* 415:    */   {
/* 416:399 */     this.subcliente = subcliente;
/* 417:    */   }
/* 418:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.compras.model.FacturaProveedorWSEntity
 * JD-Core Version:    0.7.0.1
 */