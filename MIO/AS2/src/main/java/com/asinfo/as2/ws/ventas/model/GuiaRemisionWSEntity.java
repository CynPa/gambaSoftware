/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Calendar;
/*   6:    */ import java.util.Date;
/*   7:    */ 
/*   8:    */ public class GuiaRemisionWSEntity
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1L;
/*  12:    */   private Long idSucursal;
/*  13:    */   private Long idOrganizacion;
/*  14:    */   private Long idDocumento;
/*  15:    */   private Long idTransportista;
/*  16:    */   private Long idVehiculo;
/*  17:    */   private Long idCiudadOrigen;
/*  18:    */   private Long idCiudadDestino;
/*  19:    */   private String numero;
/*  20:    */   private Calendar fecha;
/*  21:    */   private Calendar fechaVigencia;
/*  22:    */   private String conductor;
/*  23:    */   private DetalleGuiaRemisionWSEntity[] listaDetalleGuiaRemision;
/*  24:    */   private Long idTipoIdentificacionTransportista;
/*  25:    */   private String licencia;
/*  26: 26 */   private BigDecimal tarifa = BigDecimal.ZERO;
/*  27:    */   private String descripcion;
/*  28:    */   private Long idDespachoCliente;
/*  29:    */   private Long idTransferenciaBodega;
/*  30:    */   private String email;
/*  31:    */   private String emailTransportista;
/*  32:    */   private String emailCliente;
/*  33:    */   private String placa;
/*  34:    */   private Long idEmpresa;
/*  35:    */   private Long idDireccionEmpresa;
/*  36:    */   private Long idFacturaCliente;
/*  37:    */   private Long ambiente;
/*  38:    */   private Long tipoEmision;
/*  39:    */   private boolean indicadorDocumentoElectronico;
/*  40:    */   private String claveAcceso;
/*  41:    */   private String codigoUnico;
/*  42:    */   private String direccionMatriz;
/*  43:    */   private String direccionSucursal;
/*  44:    */   private String autorizacion;
/*  45:    */   private Calendar fechaAutorizacion;
/*  46:    */   private String mensajeSRI;
/*  47:    */   private String establecimiento;
/*  48:    */   private String puntoVenta;
/*  49:    */   private String ruta;
/*  50:    */   private Date horaSalida;
/*  51:    */   private Date horaLlegada;
/*  52:    */   
/*  53:    */   public Long getIdSucursal()
/*  54:    */   {
/*  55: 57 */     return this.idSucursal;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdSucursal(Long idSucursal)
/*  59:    */   {
/*  60: 65 */     this.idSucursal = idSucursal;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Long getIdOrganizacion()
/*  64:    */   {
/*  65: 72 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(Long idOrganizacion)
/*  69:    */   {
/*  70: 80 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Long getIdDocumento()
/*  74:    */   {
/*  75: 87 */     return this.idDocumento;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdDocumento(Long idDocumento)
/*  79:    */   {
/*  80: 95 */     this.idDocumento = idDocumento;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Long getIdTransportista()
/*  84:    */   {
/*  85:102 */     return this.idTransportista;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdTransportista(Long idTransportista)
/*  89:    */   {
/*  90:110 */     this.idTransportista = idTransportista;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Long getIdVehiculo()
/*  94:    */   {
/*  95:117 */     return this.idVehiculo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdVehiculo(Long idVehiculo)
/*  99:    */   {
/* 100:125 */     this.idVehiculo = idVehiculo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Long getIdCiudadOrigen()
/* 104:    */   {
/* 105:132 */     return this.idCiudadOrigen;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdCiudadOrigen(Long idCiudadOrigen)
/* 109:    */   {
/* 110:140 */     this.idCiudadOrigen = idCiudadOrigen;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Long getIdCiudadDestino()
/* 114:    */   {
/* 115:147 */     return this.idCiudadDestino;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setIdCiudadDestino(Long idCiudadDestino)
/* 119:    */   {
/* 120:155 */     this.idCiudadDestino = idCiudadDestino;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getNumero()
/* 124:    */   {
/* 125:162 */     return this.numero;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setNumero(String numero)
/* 129:    */   {
/* 130:170 */     this.numero = numero;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Calendar getFecha()
/* 134:    */   {
/* 135:177 */     return this.fecha;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFecha(Calendar fecha)
/* 139:    */   {
/* 140:185 */     this.fecha = fecha;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Calendar getFechaVigencia()
/* 144:    */   {
/* 145:192 */     return this.fechaVigencia;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setFechaVigencia(Calendar fechaVigencia)
/* 149:    */   {
/* 150:200 */     this.fechaVigencia = fechaVigencia;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setFechaAutorizacion(Calendar fechaAutorizacion)
/* 154:    */   {
/* 155:208 */     this.fechaAutorizacion = fechaAutorizacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getConductor()
/* 159:    */   {
/* 160:215 */     return this.conductor;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setConductor(String conductor)
/* 164:    */   {
/* 165:223 */     this.conductor = conductor;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Long getIdTipoIdentificacionTransportista()
/* 169:    */   {
/* 170:230 */     return this.idTipoIdentificacionTransportista;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdTipoIdentificacionTransportista(Long idTipoIdentificacionTransportista)
/* 174:    */   {
/* 175:238 */     this.idTipoIdentificacionTransportista = idTipoIdentificacionTransportista;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getLicencia()
/* 179:    */   {
/* 180:245 */     return this.licencia;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setLicencia(String licencia)
/* 184:    */   {
/* 185:253 */     this.licencia = licencia;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public BigDecimal getTarifa()
/* 189:    */   {
/* 190:260 */     return this.tarifa;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setTarifa(BigDecimal tarifa)
/* 194:    */   {
/* 195:268 */     this.tarifa = tarifa;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getDescripcion()
/* 199:    */   {
/* 200:275 */     return this.descripcion;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDescripcion(String descripcion)
/* 204:    */   {
/* 205:283 */     this.descripcion = descripcion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Long getIdDespachoCliente()
/* 209:    */   {
/* 210:290 */     return this.idDespachoCliente;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIdDespachoCliente(Long idDespachoCliente)
/* 214:    */   {
/* 215:298 */     this.idDespachoCliente = idDespachoCliente;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Long getIdTransferenciaBodega()
/* 219:    */   {
/* 220:305 */     return this.idTransferenciaBodega;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setIdTransferenciaBodega(Long idTransferenciaBodega)
/* 224:    */   {
/* 225:313 */     this.idTransferenciaBodega = idTransferenciaBodega;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getEmail()
/* 229:    */   {
/* 230:320 */     return this.email;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setEmail(String email)
/* 234:    */   {
/* 235:328 */     this.email = email;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getEmailTransportista()
/* 239:    */   {
/* 240:335 */     return this.emailTransportista;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setEmailTransportista(String emailTransportista)
/* 244:    */   {
/* 245:343 */     this.emailTransportista = emailTransportista;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getEmailCliente()
/* 249:    */   {
/* 250:350 */     return this.emailCliente;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setEmailCliente(String emailCliente)
/* 254:    */   {
/* 255:358 */     this.emailCliente = emailCliente;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getPlaca()
/* 259:    */   {
/* 260:365 */     return this.placa;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setPlaca(String placa)
/* 264:    */   {
/* 265:373 */     this.placa = placa;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Long getIdEmpresa()
/* 269:    */   {
/* 270:380 */     return this.idEmpresa;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setIdEmpresa(Long idEmpresa)
/* 274:    */   {
/* 275:388 */     this.idEmpresa = idEmpresa;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Long getIdDireccionEmpresa()
/* 279:    */   {
/* 280:395 */     return this.idDireccionEmpresa;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setIdDireccionEmpresa(Long idDireccionEmpresa)
/* 284:    */   {
/* 285:403 */     this.idDireccionEmpresa = idDireccionEmpresa;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Long getIdFacturaCliente()
/* 289:    */   {
/* 290:410 */     return this.idFacturaCliente;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setIdFacturaCliente(Long idFacturaCliente)
/* 294:    */   {
/* 295:418 */     this.idFacturaCliente = idFacturaCliente;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Long getAmbiente()
/* 299:    */   {
/* 300:425 */     return this.ambiente;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setAmbiente(Long ambiente)
/* 304:    */   {
/* 305:433 */     this.ambiente = ambiente;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Long getTipoEmision()
/* 309:    */   {
/* 310:440 */     return this.tipoEmision;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setTipoEmision(Long tipoEmision)
/* 314:    */   {
/* 315:448 */     this.tipoEmision = tipoEmision;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public boolean isIndicadorDocumentoElectronico()
/* 319:    */   {
/* 320:455 */     return this.indicadorDocumentoElectronico;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setIndicadorDocumentoElectronico(boolean indicadorDocumentoElectronico)
/* 324:    */   {
/* 325:463 */     this.indicadorDocumentoElectronico = indicadorDocumentoElectronico;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String getClaveAcceso()
/* 329:    */   {
/* 330:470 */     return this.claveAcceso;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void setClaveAcceso(String claveAcceso)
/* 334:    */   {
/* 335:478 */     this.claveAcceso = claveAcceso;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String getCodigoUnico()
/* 339:    */   {
/* 340:485 */     return this.codigoUnico;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setCodigoUnico(String codigoUnico)
/* 344:    */   {
/* 345:493 */     this.codigoUnico = codigoUnico;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public String getDireccionMatriz()
/* 349:    */   {
/* 350:500 */     return this.direccionMatriz;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void setDireccionMatriz(String direccionMatriz)
/* 354:    */   {
/* 355:508 */     this.direccionMatriz = direccionMatriz;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public String getDireccionSucursal()
/* 359:    */   {
/* 360:515 */     return this.direccionSucursal;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setDireccionSucursal(String direccionSucursal)
/* 364:    */   {
/* 365:523 */     this.direccionSucursal = direccionSucursal;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public String getAutorizacion()
/* 369:    */   {
/* 370:530 */     return this.autorizacion;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setAutorizacion(String autorizacion)
/* 374:    */   {
/* 375:538 */     this.autorizacion = autorizacion;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public String getMensajeSRI()
/* 379:    */   {
/* 380:545 */     return this.mensajeSRI;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setMensajeSRI(String mensajeSRI)
/* 384:    */   {
/* 385:553 */     this.mensajeSRI = mensajeSRI;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public DetalleGuiaRemisionWSEntity[] getListaDetalleGuiaRemision()
/* 389:    */   {
/* 390:560 */     return this.listaDetalleGuiaRemision;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setListaDetalleGuiaRemision(DetalleGuiaRemisionWSEntity[] listaDetalleGuiaRemision)
/* 394:    */   {
/* 395:568 */     this.listaDetalleGuiaRemision = listaDetalleGuiaRemision;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public Calendar getFechaAutorizacion()
/* 399:    */   {
/* 400:575 */     return this.fechaAutorizacion;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public String getEstablecimiento()
/* 404:    */   {
/* 405:582 */     return this.establecimiento;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setEstablecimiento(String establecimiento)
/* 409:    */   {
/* 410:590 */     this.establecimiento = establecimiento;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public String getPuntoVenta()
/* 414:    */   {
/* 415:597 */     return this.puntoVenta;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setPuntoVenta(String puntoVenta)
/* 419:    */   {
/* 420:605 */     this.puntoVenta = puntoVenta;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public String getRuta()
/* 424:    */   {
/* 425:609 */     return this.ruta;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void setRuta(String ruta)
/* 429:    */   {
/* 430:613 */     this.ruta = ruta;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public Date getHoraSalida()
/* 434:    */   {
/* 435:617 */     return this.horaSalida;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void setHoraSalida(Date horaSalida)
/* 439:    */   {
/* 440:621 */     this.horaSalida = horaSalida;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public Date getHoraLlegada()
/* 444:    */   {
/* 445:625 */     return this.horaLlegada;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setHoraLlegada(Date horaLlegada)
/* 449:    */   {
/* 450:629 */     this.horaLlegada = horaLlegada;
/* 451:    */   }
/* 452:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.GuiaRemisionWSEntity
 * JD-Core Version:    0.7.0.1
 */