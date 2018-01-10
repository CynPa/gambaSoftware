/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.dao.DetallePagoCuotaPrestamoDao;
/*   5:    */ import com.asinfo.as2.dao.DetallePrestamoDao;
/*   6:    */ import com.asinfo.as2.dao.PagoRolDao;
/*   7:    */ import com.asinfo.as2.dao.PrestamoDao;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.Asiento;
/*  10:    */ import com.asinfo.as2.entities.DetallePagoCuotaPrestamo;
/*  11:    */ import com.asinfo.as2.entities.DetallePrestamo;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*  15:    */ import com.asinfo.as2.entities.Prestamo;
/*  16:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  17:    */ import com.asinfo.as2.entities.TipoPrestamo;
/*  18:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  19:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  23:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  24:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  25:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoPrestamo;
/*  26:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo;
/*  27:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  30:    */ import java.io.PrintStream;
/*  31:    */ import java.math.BigDecimal;
/*  32:    */ import java.math.RoundingMode;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Calendar;
/*  35:    */ import java.util.Date;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.ejb.EJB;
/*  39:    */ import javax.ejb.Stateless;
/*  40:    */ import javax.ejb.TransactionAttribute;
/*  41:    */ import javax.ejb.TransactionAttributeType;
/*  42:    */ import javax.ejb.TransactionManagement;
/*  43:    */ import javax.ejb.TransactionManagementType;
/*  44:    */ 
/*  45:    */ @Stateless
/*  46:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  47:    */ public class ServicioPrestamoImpl
/*  48:    */   extends AbstractServicioAS2Financiero
/*  49:    */   implements ServicioPrestamo
/*  50:    */ {
/*  51:    */   private static final long serialVersionUID = -585311322264652076L;
/*  52:    */   @EJB
/*  53:    */   private PrestamoDao prestamoDao;
/*  54:    */   @EJB
/*  55:    */   private DetallePrestamoDao detallePrestamoDao;
/*  56:    */   @EJB
/*  57:    */   private PagoRolDao pagoRolDao;
/*  58:    */   @EJB
/*  59:    */   private DetallePagoCuotaPrestamoDao detallePagoCuotaPrestamoDao;
/*  60:    */   @EJB
/*  61:    */   private ServicioAsiento servicioAsiento;
/*  62:    */   @EJB
/*  63:    */   private ServicioPeriodo servicioPeriodo;
/*  64:    */   @EJB
/*  65:    */   private ServicioSecuencia servicioSecuencia;
/*  66:    */   @EJB
/*  67:    */   private ServicioTipoPrestamo servicioTipoPrestamo;
/*  68:    */   
/*  69:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  70:    */   public void guardar(Prestamo prestamo)
/*  71:    */     throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception
/*  72:    */   {
/*  73: 92 */     guardar(prestamo, true, false);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void guardar(Prestamo prestamo, boolean validar, boolean modificado)
/*  77:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/*  78:    */   {
/*  79: 98 */     verificarMonto(prestamo);
/*  80:100 */     if (!validar) {
/*  81:101 */       validarFechas(prestamo);
/*  82:    */     }
/*  83:104 */     if (validar) {
/*  84:105 */       validar(prestamo);
/*  85:    */     }
/*  86:109 */     cargarSecuencia(prestamo);
/*  87:112 */     if (prestamo.getPrestamoPadre() != null)
/*  88:    */     {
/*  89:113 */       prestamo.getPrestamoPadre().setEstado(Estado.CERRADO);
/*  90:114 */       this.prestamoDao.guardar(prestamo.getPrestamoPadre());
/*  91:    */     }
/*  92:117 */     for (DetallePrestamo detallePrestamo : prestamo.getListaDetallePrestamo())
/*  93:    */     {
/*  94:118 */       if (detallePrestamo.getInteresCuota().compareTo(BigDecimal.ZERO) == 0)
/*  95:    */       {
/*  96:119 */         detallePrestamo.setCapitalCuota(detallePrestamo.getCuota());
/*  97:120 */         detallePrestamo.setSaldoCapitalCuota(detallePrestamo.getCuota());
/*  98:    */       }
/*  99:122 */       if (modificado) {
/* 100:123 */         for (DetallePagoCuotaPrestamo detallePagoCuotaPrestamo : detallePrestamo.getListaDetallePagoCuotaPrestamo())
/* 101:    */         {
/* 102:124 */           this.pagoRolDao.guardar(detallePagoCuotaPrestamo.getPagoRolEmpleado().getPagoRol());
/* 103:125 */           this.detallePagoCuotaPrestamoDao.guardar(detallePagoCuotaPrestamo);
/* 104:    */         }
/* 105:    */       }
/* 106:128 */       this.detallePrestamoDao.guardar(detallePrestamo);
/* 107:    */     }
/* 108:131 */     this.prestamoDao.guardar(prestamo);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void validar(Prestamo prestamo)
/* 112:    */     throws ExcepcionAS2Financiero, AS2Exception
/* 113:    */   {
/* 114:143 */     this.servicioPeriodo.buscarPorFecha(prestamo.getFechaSolicitud(), prestamo.getIdOrganizacion(), DocumentoBase.PRESTAMO_NOMINA);
/* 115:145 */     if (prestamo.getEstado() == Estado.ANULADO) {
/* 116:146 */       throw new ExcepcionAS2Financiero("msg_accion_no_permitida");
/* 117:    */     }
/* 118:148 */     prestamo.setSoloLectura(prestamo.getEstado().equals(Estado.APROBADO));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void validarCuotaCobrada(Prestamo prestamo)
/* 122:    */     throws AS2Exception
/* 123:    */   {
/* 124:153 */     List<DetallePagoCuotaPrestamo> lresult = this.prestamoDao.obtenerCuotasPagadasPrestamo(prestamo);
/* 125:154 */     if ((null != lresult) && (lresult.size() > 0)) {
/* 126:155 */       throw new AS2Exception("com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPrestamoImpl.PRESTAMO_CUOTAS_PAGADAS", new String[] { "" });
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void verificarMonto(Prestamo prestamo)
/* 131:    */     throws AS2Exception
/* 132:    */   {
/* 133:160 */     BigDecimal monto = prestamo.getMonto();
/* 134:161 */     BigDecimal totalCuotas = BigDecimal.ZERO;
/* 135:163 */     for (DetallePrestamo dp : prestamo.getListaDetallePrestamo()) {
/* 136:164 */       if (!dp.isEliminado()) {
/* 137:165 */         totalCuotas = totalCuotas.add(dp.getCuota());
/* 138:    */       }
/* 139:    */     }
/* 140:170 */     if (monto.compareTo(totalCuotas) != 0) {
/* 141:171 */       throw new AS2Exception("com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPrestamoImpl.MONTO_CUOTAS_DESIGUALES", new String[] { "" });
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void eliminar(Prestamo prestamo)
/* 146:    */   {
/* 147:182 */     this.prestamoDao.eliminar(prestamo);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void eliminarDetallesPrestamo(Prestamo prestamo)
/* 151:    */   {
/* 152:187 */     this.prestamoDao.eliminarDetallesPrestamo(prestamo);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Prestamo buscarPorId(int idPrestamo)
/* 156:    */   {
/* 157:197 */     return (Prestamo)this.prestamoDao.buscarPorId(Integer.valueOf(idPrestamo));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<Prestamo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 161:    */   {
/* 162:207 */     return this.prestamoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<Prestamo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 166:    */   {
/* 167:217 */     return this.prestamoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int contarPorCriterio(Map<String, String> filters)
/* 171:    */   {
/* 172:227 */     return this.prestamoDao.contarPorCriterio(filters);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Prestamo cargarDetalle(int idPrestamo)
/* 176:    */   {
/* 177:237 */     return this.prestamoDao.cargarDetalle(idPrestamo);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<DetallePrestamo> generarTablaAmortizacion(Prestamo prestamo)
/* 181:    */   {
/* 182:248 */     BigDecimal monto = prestamo.getMonto();
/* 183:249 */     BigDecimal interesPorcentaje = prestamo.getInteres();
/* 184:250 */     int plazo = prestamo.getPlazo();
/* 185:251 */     Date fecha = prestamo.getFechaInicioDescuento() == null ? prestamo.getFechaSolicitud() : prestamo.getFechaInicioDescuento();
/* 186:    */     
/* 187:253 */     List<DetallePrestamo> lista = new ArrayList();
/* 188:    */     
/* 189:255 */     Calendar fechaCuota = Calendar.getInstance();
/* 190:256 */     fechaCuota.setTime(fecha);
/* 191:    */     
/* 192:258 */     BigDecimal pago = getPago(monto, plazo, interesPorcentaje);
/* 193:259 */     BigDecimal saldo = monto;
/* 194:260 */     BigDecimal interes = BigDecimal.ZERO;
/* 195:261 */     BigDecimal principal = BigDecimal.ZERO;
/* 196:262 */     BigDecimal totalInteres = BigDecimal.ZERO;
/* 197:263 */     BigDecimal cuota = BigDecimal.ZERO;
/* 198:264 */     BigDecimal capital = BigDecimal.ZERO;
/* 199:266 */     for (int i = 1; i <= plazo; i++)
/* 200:    */     {
/* 201:268 */       DetallePrestamo detallePrestamo = new DetallePrestamo();
/* 202:    */       
/* 203:270 */       interes = saldo.multiply(interesPorcentaje.divide(new BigDecimal(1200), 24, RoundingMode.HALF_UP));
/* 204:271 */       totalInteres = totalInteres.add(interes);
/* 205:272 */       principal = pago.subtract(interes);
/* 206:273 */       saldo = saldo.subtract(principal);
/* 207:274 */       capital = saldo.add(principal);
/* 208:275 */       cuota = principal.add(interes);
/* 209:    */       
/* 210:277 */       detallePrestamo.setNumeroCuota(i);
/* 211:278 */       detallePrestamo.setCuota(cuota.setScale(2, RoundingMode.HALF_UP));
/* 212:279 */       detallePrestamo.setCapitalCuota(principal.setScale(2, RoundingMode.HALF_UP));
/* 213:280 */       detallePrestamo.setInteresCuota(interes.setScale(2, RoundingMode.HALF_UP));
/* 214:281 */       detallePrestamo.setSaldoCapitalCuota(detallePrestamo.getCapitalCuota());
/* 215:282 */       detallePrestamo.setSaldoInteresCuota(detallePrestamo.getInteresCuota());
/* 216:283 */       detallePrestamo.setPorcentajeInteresCuota(interesPorcentaje);
/* 217:285 */       if (i > 1) {
/* 218:286 */         fechaCuota.add(2, 1);
/* 219:    */       }
/* 220:289 */       fechaCuota.setTime(FuncionesUtiles.getFechaFinMes(fechaCuota.getTime()));
/* 221:    */       
/* 222:291 */       detallePrestamo.setFechaCuota(fechaCuota.getTime());
/* 223:    */       
/* 224:    */ 
/* 225:    */ 
/* 226:295 */       detallePrestamo.setSaldoTotal(saldo.setScale(2, RoundingMode.HALF_UP));
/* 227:296 */       detallePrestamo.setCapitalTotal(capital.setScale(2, RoundingMode.HALF_UP));
/* 228:    */       
/* 229:298 */       lista.add(detallePrestamo);
/* 230:    */     }
/* 231:301 */     return lista;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private BigDecimal getPago(BigDecimal monto, int plazo, BigDecimal interesPorcentaje)
/* 235:    */   {
/* 236:309 */     BigDecimal acc = BigDecimal.ZERO;
/* 237:310 */     BigDecimal base = BigDecimal.ONE.add(interesPorcentaje.divide(new BigDecimal(1200), 24, RoundingMode.HALF_UP));
/* 238:311 */     for (int i = 1; i <= plazo; i++) {
/* 239:312 */       acc = acc.add(new BigDecimal(Math.pow(base.doubleValue(), -i)));
/* 240:    */     }
/* 241:314 */     return monto.divide(acc, 24, RoundingMode.HALF_UP);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void contabilizar(Prestamo prestamo)
/* 245:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 246:    */   {
/* 247:321 */     prestamo = this.prestamoDao.cargarDetalle(prestamo.getIdPrestamo());
/* 248:322 */     Date fechaContabilizacion = prestamo.getFechaContabilizacion();
/* 249:    */     Asiento asiento;
/* 250:    */     Asiento asiento;
/* 251:324 */     if (prestamo.getAsiento() != null)
/* 252:    */     {
/* 253:325 */       asiento = this.servicioAsiento.cargarDetalle(prestamo.getAsiento().getId());
/* 254:    */     }
/* 255:    */     else
/* 256:    */     {
/* 257:328 */       asiento = new Asiento();
/* 258:329 */       asiento.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 259:330 */       asiento.setSucursal(AppUtil.getSucursal());
/* 260:331 */       TipoAsiento tipoAsiento = prestamo.getTipoPrestamo().getDocumento().getTipoAsiento();
/* 261:332 */       asiento.setTipoAsiento(tipoAsiento);
/* 262:    */     }
/* 263:337 */     if (prestamo.getSecuencia() != null) {
/* 264:339 */       this.servicioSecuencia.actualizarSecuencia(prestamo.getSecuencia(), prestamo.getDocumentoReferencia());
/* 265:    */     }
/* 266:343 */     String concepto = "";
/* 267:344 */     concepto = prestamo.getTipoPrestamo().getDocumento().getNombre().trim();
/* 268:345 */     asiento.setConcepto(concepto);
/* 269:346 */     asiento.setFecha(fechaContabilizacion);
/* 270:347 */     asiento.setTipoAsiento(prestamo.getTipoPrestamo().getDocumento().getTipoAsiento());
/* 271:348 */     List<DetalleInterfazContable> listaDA = new ArrayList();
/* 272:349 */     listaDA.addAll(this.prestamoDao.getPrestamoIC(prestamo.getIdPrestamo()));
/* 273:    */     
/* 274:351 */     super.generarAsiento(asiento, listaDA, prestamo.getTipoPrestamo().getDocumento());
/* 275:    */     
/* 276:353 */     System.out.println("nombre del tipo asiento " + asiento.getTipoAsiento());
/* 277:354 */     this.servicioAsiento.guardar(asiento);
/* 278:    */     
/* 279:356 */     prestamo.setEstado(Estado.CONTABILIZADO);
/* 280:    */     
/* 281:358 */     prestamo.setAsiento(asiento);
/* 282:359 */     this.prestamoDao.guardar(prestamo);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void tablaAmortizacion(Prestamo prestamo)
/* 286:    */   {
/* 287:370 */     int numeroCuota = 0;
/* 288:    */     
/* 289:372 */     int numeroCuotas = prestamo.getPlazo();
/* 290:373 */     BigDecimal total = prestamo.getMonto();
/* 291:374 */     Date fecha = prestamo.getFechaInicioDescuento() == null ? prestamo.getFechaSolicitud() : prestamo.getFechaInicioDescuento();
/* 292:    */     
/* 293:376 */     BigDecimal valorCuota = total.divide(new BigDecimal(numeroCuotas), 2, RoundingMode.HALF_UP);
/* 294:    */     
/* 295:    */ 
/* 296:379 */     BigDecimal saldo = total;
/* 297:380 */     BigDecimal saldoInteres = BigDecimal.ZERO;
/* 298:381 */     BigDecimal saldoCapital = BigDecimal.ZERO;
/* 299:382 */     BigDecimal interesAnual = BigDecimal.ZERO;
/* 300:383 */     BigDecimal interesMensual = BigDecimal.ZERO;
/* 301:    */     
/* 302:385 */     BigDecimal totalInteres = BigDecimal.ZERO;
/* 303:386 */     BigDecimal totalCapital = BigDecimal.ZERO;
/* 304:387 */     BigDecimal valorAjuste = BigDecimal.ZERO;
/* 305:389 */     if (prestamo.getInteres().compareTo(new BigDecimal(0)) == 1)
/* 306:    */     {
/* 307:390 */       interesAnual = prestamo.getInteres().divide(new BigDecimal(100), 6, RoundingMode.HALF_UP);
/* 308:391 */       interesMensual = interesAnual.divide(new BigDecimal(numeroCuotas), 6, RoundingMode.HALF_UP);
/* 309:392 */       BigDecimal numeradorCuota = interesMensual.multiply(interesMensual.add(new BigDecimal(1)).pow(numeroCuotas));
/* 310:393 */       BigDecimal denominadorCuota = interesMensual.add(new BigDecimal(1)).pow(numeroCuotas).subtract(new BigDecimal(1));
/* 311:394 */       BigDecimal division = numeradorCuota.divide(denominadorCuota, 6, RoundingMode.HALF_UP);
/* 312:395 */       valorCuota = total.multiply(division).setScale(2, RoundingMode.HALF_UP);
/* 313:    */     }
/* 314:399 */     int numeroDetalles = 0;
/* 315:401 */     if (!prestamo.getListaDetallePrestamo().isEmpty()) {
/* 316:402 */       numeroDetalles = prestamo.getListaDetallePrestamo().size();
/* 317:    */     }
/* 318:405 */     int lineas = Math.max(numeroDetalles, numeroCuotas);
/* 319:406 */     for (int i = 0; i < lineas; i++) {
/* 320:408 */       if ((!prestamo.getListaDetallePrestamo().isEmpty()) && (numeroDetalles > numeroCuotas) && (i >= numeroCuotas))
/* 321:    */       {
/* 322:409 */         ((DetallePrestamo)prestamo.getListaDetallePrestamo().get(i)).setEliminado(true);
/* 323:    */       }
/* 324:    */       else
/* 325:    */       {
/* 326:    */         DetallePrestamo detallePrestamo;
/* 327:413 */         if ((!prestamo.getListaDetallePrestamo().isEmpty()) && (i < prestamo.getListaDetallePrestamo().size()))
/* 328:    */         {
/* 329:414 */           DetallePrestamo detallePrestamo = (DetallePrestamo)prestamo.getListaDetallePrestamo().get(i);
/* 330:    */           
/* 331:416 */           detallePrestamo.setEliminado(false);
/* 332:    */         }
/* 333:    */         else
/* 334:    */         {
/* 335:419 */           detallePrestamo = new DetallePrestamo();
/* 336:    */         }
/* 337:421 */         numeroCuota = i + 1;
/* 338:422 */         Date fechaVencimiento = FuncionesUtiles.sumarFechaMeses(fecha, i);
/* 339:423 */         detallePrestamo.setFechaCuota(fechaVencimiento);
/* 340:424 */         detallePrestamo.setNumeroCuota(numeroCuota);
/* 341:425 */         detallePrestamo.setPrestamo(prestamo);
/* 342:426 */         detallePrestamo.setPorcentajeInteresCuota(prestamo.getInteres());
/* 343:428 */         if (prestamo.getInteres().compareTo(new BigDecimal(0)) == 1)
/* 344:    */         {
/* 345:429 */           saldoInteres = saldo.multiply(interesMensual).setScale(2, RoundingMode.HALF_UP);
/* 346:430 */           saldoCapital = valorCuota.subtract(saldoInteres);
/* 347:    */           
/* 348:432 */           totalCapital = totalCapital.add(saldoCapital);
/* 349:433 */           totalInteres = totalInteres.add(saldoInteres);
/* 350:435 */           if (numeroCuota == numeroCuotas)
/* 351:    */           {
/* 352:436 */             if (totalCapital.compareTo(total) == 1)
/* 353:    */             {
/* 354:437 */               valorAjuste = totalCapital.subtract(total);
/* 355:438 */               saldoCapital = saldoCapital.subtract(valorAjuste);
/* 356:439 */               saldoInteres = valorCuota.subtract(saldoCapital);
/* 357:    */             }
/* 358:441 */             if (total.compareTo(totalCapital) == 1)
/* 359:    */             {
/* 360:442 */               valorAjuste = total.subtract(totalCapital);
/* 361:443 */               saldoCapital = saldoCapital.add(valorAjuste);
/* 362:444 */               saldoInteres = valorCuota.subtract(saldoCapital);
/* 363:    */             }
/* 364:    */           }
/* 365:447 */           saldo = saldo.subtract(saldoCapital);
/* 366:448 */           detallePrestamo.setCuota(valorCuota);
/* 367:449 */           detallePrestamo.setCapitalCuota(saldoCapital);
/* 368:450 */           detallePrestamo.setSaldoCapitalCuota(saldoCapital);
/* 369:451 */           detallePrestamo.setInteresCuota(saldoInteres);
/* 370:452 */           detallePrestamo.setSaldoInteresCuota(saldoInteres);
/* 371:    */         }
/* 372:    */         else
/* 373:    */         {
/* 374:455 */           detallePrestamo.setInteresCuota(BigDecimal.ZERO);
/* 375:456 */           detallePrestamo.setSaldoInteresCuota(BigDecimal.ZERO);
/* 376:457 */           if (numeroCuota == numeroCuotas) {
/* 377:458 */             valorCuota = total;
/* 378:    */           }
/* 379:460 */           detallePrestamo.setCuota(valorCuota);
/* 380:461 */           detallePrestamo.setCapitalCuota(valorCuota);
/* 381:462 */           detallePrestamo.setSaldoCapitalCuota(valorCuota);
/* 382:463 */           total = total.subtract(valorCuota);
/* 383:    */         }
/* 384:465 */         if (i >= prestamo.getListaDetallePrestamo().size()) {
/* 385:466 */           prestamo.getListaDetallePrestamo().add(detallePrestamo);
/* 386:    */         }
/* 387:    */       }
/* 388:    */     }
/* 389:472 */     BigDecimal totalCapitalCuota = BigDecimal.ZERO;
/* 390:473 */     BigDecimal totalCuota = BigDecimal.ZERO;
/* 391:474 */     BigDecimal totalInteresCuota = BigDecimal.ZERO;
/* 392:476 */     for (DetallePrestamo detallePrestamo : prestamo.getListaDetallePrestamo()) {
/* 393:477 */       if (!detallePrestamo.isEliminado())
/* 394:    */       {
/* 395:478 */         totalCapitalCuota = totalCapitalCuota.add(detallePrestamo.getCapitalCuota());
/* 396:479 */         totalCuota = totalCuota.add(detallePrestamo.getCuota());
/* 397:480 */         totalInteresCuota = totalInteresCuota.add(detallePrestamo.getInteresCuota());
/* 398:    */       }
/* 399:    */     }
/* 400:484 */     prestamo.setTotalCapitalCuota(prestamo.getMonto().setScale(2, RoundingMode.HALF_UP));
/* 401:485 */     prestamo.setTotalCuota(totalInteresCuota.add(prestamo.getMonto()).setScale(2, RoundingMode.HALF_UP));
/* 402:486 */     prestamo.setTotalInteresCuota(totalInteresCuota);
/* 403:    */   }
/* 404:    */   
/* 405:    */   public List reporteTablaAmortizacion(Prestamo prestamo)
/* 406:    */   {
/* 407:497 */     return this.prestamoDao.reporteTablaAmortizacion(prestamo);
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void anularAsiento(Prestamo prestamo)
/* 411:    */     throws ExcepcionAS2Financiero, AS2Exception
/* 412:    */   {
/* 413:502 */     validarCuotaCobrada(prestamo);
/* 414:    */     
/* 415:504 */     Asiento asiento = this.servicioAsiento.cargarDetalle(prestamo.getAsiento().getId());
/* 416:505 */     asiento.setIndicadorAutomatico(false);
/* 417:506 */     this.servicioAsiento.anular(asiento);
/* 418:507 */     prestamo.setAsiento(null);
/* 419:508 */     prestamo.setEstado(Estado.APROBADO);
/* 420:509 */     this.prestamoDao.guardar(prestamo);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void anularPrestamo(Prestamo prestamo)
/* 424:    */     throws ExcepcionAS2, AS2Exception
/* 425:    */   {
/* 426:513 */     if (prestamo.getEstado().equals(Estado.CONTABILIZADO)) {
/* 427:514 */       throw new ExcepcionAS2Financiero("msg_accion_no_permitida");
/* 428:    */     }
/* 429:516 */     validarCuotaCobrada(prestamo);
/* 430:517 */     prestamo.setEstado(Estado.ANULADO);
/* 431:518 */     guardar(prestamo, false, false);
/* 432:    */   }
/* 433:    */   
/* 434:    */   private void cargarSecuencia(Prestamo prestamo)
/* 435:    */     throws ExcepcionAS2
/* 436:    */   {
/* 437:529 */     prestamo.setTipoPrestamo(this.servicioTipoPrestamo.cargarDetalle(prestamo.getTipoPrestamo().getId()));
/* 438:530 */     if (prestamo.getNumero().equals(""))
/* 439:    */     {
/* 440:531 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(prestamo.getTipoPrestamo().getDocumento().getId(), prestamo
/* 441:532 */         .getFechaSolicitud());
/* 442:533 */       prestamo.setNumero(numero);
/* 443:    */     }
/* 444:    */     else
/* 445:    */     {
/* 446:535 */       this.servicioSecuencia.actualizarSecuencia(prestamo.getTipoPrestamo().getDocumento().getSecuencia(), prestamo.getNumero());
/* 447:    */     }
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void validarFechas(Prestamo prestamo)
/* 451:    */     throws ExcepcionAS2Financiero, AS2Exception
/* 452:    */   {
/* 453:541 */     if ((prestamo.getEstado().equals(Estado.APROBADO)) && (prestamo.getFechaAprobacion().compareTo(prestamo.getFechaSolicitud()) == -1))
/* 454:    */     {
/* 455:543 */       String mensaje = FuncionesUtiles.dateToString(prestamo.getFechaSolicitud()) + " " + FuncionesUtiles.dateToString(prestamo.getFechaAprobacion());
/* 456:544 */       throw new ExcepcionAS2Financiero("msg_inf_fecha_solicitud_menor_aprobacion_prestamo", mensaje);
/* 457:    */     }
/* 458:    */   }
/* 459:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.impl.ServicioPrestamoImpl
 * JD-Core Version:    0.7.0.1
 */