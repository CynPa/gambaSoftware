/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ClienteDao;
/*   4:    */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*   5:    */ import com.asinfo.as2.dao.DespachoClienteDao;
/*   6:    */ import com.asinfo.as2.dao.DetalleDespachoClienteDao;
/*   7:    */ import com.asinfo.as2.dao.DetalleFacturaClienteDao;
/*   8:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   9:    */ import com.asinfo.as2.dao.PedidoClienteDao;
/*  10:    */ import com.asinfo.as2.dao.ProductoDao;
/*  11:    */ import com.asinfo.as2.dao.reportes.ventas.AnalisisVencimientosClienteDao;
/*  12:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  13:    */ import com.asinfo.as2.entities.Cliente;
/*  14:    */ import com.asinfo.as2.entities.Cobro;
/*  15:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  16:    */ import com.asinfo.as2.entities.DespachoCliente;
/*  17:    */ import com.asinfo.as2.entities.DetalleCobro;
/*  18:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*  19:    */ import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
/*  20:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  21:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*  22:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*  23:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  24:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*  25:    */ import com.asinfo.as2.entities.Empresa;
/*  26:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  27:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  28:    */ import com.asinfo.as2.entities.PedidoCliente;
/*  29:    */ import com.asinfo.as2.entities.Producto;
/*  30:    */ import com.asinfo.as2.entities.TipoOrdenDespacho;
/*  31:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  32:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  33:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  34:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*  35:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  38:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  39:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*  40:    */ import java.io.PrintStream;
/*  41:    */ import java.math.BigDecimal;
/*  42:    */ import java.math.RoundingMode;
/*  43:    */ import java.util.ArrayList;
/*  44:    */ import java.util.Date;
/*  45:    */ import java.util.List;
/*  46:    */ import javax.ejb.EJB;
/*  47:    */ import javax.ejb.Lock;
/*  48:    */ import javax.ejb.LockType;
/*  49:    */ import javax.ejb.SessionContext;
/*  50:    */ import javax.ejb.Singleton;
/*  51:    */ 
/*  52:    */ @Singleton
/*  53:    */ public class ServicioVerificadorVentasImpl
/*  54:    */   extends AbstractServicioAS2
/*  55:    */   implements ServicioVerificadorVentas
/*  56:    */ {
/*  57:    */   private static final long serialVersionUID = 1L;
/*  58:    */   @EJB
/*  59:    */   private transient PedidoClienteDao pedidoClienteDao;
/*  60:    */   @EJB
/*  61:    */   private transient DetalleDespachoClienteDao detalleDespachoClienteDao;
/*  62:    */   @EJB
/*  63:    */   private transient DetalleFacturaClienteDao detalleFacturaClienteDao;
/*  64:    */   @EJB
/*  65:    */   private transient FacturaClienteDao facturaClienteDao;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioPedidoCliente servicioPedidoCliente;
/*  68:    */   @EJB
/*  69:    */   private transient DespachoClienteDao despachoClienteDao;
/*  70:    */   @EJB
/*  71:    */   private transient CuentaPorCobrarDao cuentaPorCobrarDao;
/*  72:    */   @EJB
/*  73:    */   private transient ClienteDao clienteDao;
/*  74:    */   @EJB
/*  75:    */   private transient ServicioCobro servicioCobro;
/*  76:    */   @EJB
/*  77:    */   private transient ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  78:    */   @EJB
/*  79:    */   private transient ProductoDao productoDao;
/*  80:    */   @EJB
/*  81:    */   private transient AnalisisVencimientosClienteDao analisisVencimientosClienteDao;
/*  82:    */   @EJB
/*  83:    */   private transient ServicioDetalleDocumentoDigitalizado servicioDetalleDocumentoDigitalizado;
/*  84:    */   
/*  85:    */   @Lock(LockType.WRITE)
/*  86:    */   public void actualizarCantidadPorFacturar(FacturaCliente facturaCliente, boolean indicadorReverso)
/*  87:    */     throws ExcepcionAS2Inventario
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:107 */       if ((facturaCliente.getId() > 0) || (!indicadorReverso))
/*  92:    */       {
/*  93:    */         List<DetalleFacturaCliente> listataDetalle;
/*  94:    */         List<DetalleFacturaCliente> listataDetalle;
/*  95:110 */         if (indicadorReverso) {
/*  96:111 */           listataDetalle = this.facturaClienteDao.cargarDetalleFactura(facturaCliente.getId());
/*  97:    */         } else {
/*  98:113 */           listataDetalle = facturaCliente.getListaDetalleFacturaCliente();
/*  99:    */         }
/* 100:116 */         List<PedidoCliente> hmDPC = new ArrayList();
/* 101:117 */         for (DetalleFacturaCliente dfc : listataDetalle) {
/* 102:118 */           if (!dfc.isEliminado()) {
/* 103:119 */             actualizarCantidadPorFacturar(dfc, indicadorReverso, hmDPC);
/* 104:    */           }
/* 105:    */         }
/* 106:124 */         for (PedidoCliente pedidoCliente : hmDPC) {
/* 107:126 */           this.servicioPedidoCliente.cierreAutomatico(pedidoCliente);
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:    */     catch (ExcepcionAS2Inventario e)
/* 112:    */     {
/* 113:132 */       this.context.setRollbackOnly();
/* 114:133 */       e.printStackTrace();
/* 115:134 */       throw e;
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   @Lock(LockType.WRITE)
/* 120:    */   public void actualizarCantidadPorDespachar(DespachoCliente despachoCliente, boolean indicadorReverso)
/* 121:    */     throws ExcepcionAS2Inventario
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:148 */       if ((despachoCliente.getId() > 0) || (!indicadorReverso))
/* 126:    */       {
/* 127:    */         List<DetalleDespachoCliente> listataDetalle;
/* 128:    */         List<DetalleDespachoCliente> listataDetalle;
/* 129:152 */         if (indicadorReverso) {
/* 130:153 */           listataDetalle = this.despachoClienteDao.cargarDetalleDespacho(despachoCliente.getId());
/* 131:    */         } else {
/* 132:155 */           listataDetalle = despachoCliente.getListaDetalleDespachoCliente();
/* 133:    */         }
/* 134:158 */         List<PedidoCliente> hmDPC = new ArrayList();
/* 135:159 */         for (DetalleDespachoCliente ddc : listataDetalle) {
/* 136:160 */           if (!ddc.isEliminado()) {
/* 137:161 */             actualizarCantidadPorDespachar(ddc, indicadorReverso, hmDPC);
/* 138:    */           }
/* 139:    */         }
/* 140:166 */         for (PedidoCliente pedidoCliente : hmDPC) {
/* 141:167 */           this.servicioPedidoCliente.cierreAutomatico(pedidoCliente);
/* 142:    */         }
/* 143:    */       }
/* 144:    */     }
/* 145:    */     catch (ExcepcionAS2Inventario e)
/* 146:    */     {
/* 147:171 */       this.context.setRollbackOnly();
/* 148:172 */       e.printStackTrace();
/* 149:173 */       throw e;
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void actualizarCantidadPorFacturar(DetalleFacturaCliente dfc, boolean indicadorReverso, List<PedidoCliente> hmDPC)
/* 154:    */   {
/* 155:180 */     dfc = (DetalleFacturaCliente)this.detalleFacturaClienteDao.buscarPorId(Integer.valueOf(dfc.getId()));
/* 156:181 */     BigDecimal cantidad = dfc.getCantidad();
/* 157:182 */     if (indicadorReverso) {
/* 158:183 */       cantidad = cantidad.negate();
/* 159:    */     }
/* 160:186 */     if (dfc.getDetallePedidoCliente() != null)
/* 161:    */     {
/* 162:187 */       if (!hmDPC.contains(Integer.valueOf(dfc.getDetallePedidoCliente().getPedidoCliente().getId()))) {
/* 163:188 */         hmDPC.add(dfc.getDetallePedidoCliente().getPedidoCliente());
/* 164:    */       }
/* 165:191 */       actualizarCantidadPorFacturar(dfc.getDetallePedidoCliente(), cantidad);
/* 166:    */     }
/* 167:193 */     else if ((dfc.getDetalleDespachoCliente() != null) && (dfc.getDetalleDespachoCliente().getDetallePedidoCliente() != null))
/* 168:    */     {
/* 169:195 */       if (!hmDPC.contains(Integer.valueOf(dfc.getDetalleDespachoCliente().getDetallePedidoCliente().getPedidoCliente().getId()))) {
/* 170:196 */         hmDPC.add(dfc.getDetalleDespachoCliente().getDetallePedidoCliente().getPedidoCliente());
/* 171:    */       }
/* 172:199 */       actualizarCantidadPorFacturar(dfc.getDetalleDespachoCliente().getDetallePedidoCliente(), cantidad);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   private void actualizarCantidadPorDespachar(DetalleDespachoCliente ddc, boolean indicadorReverso, List<PedidoCliente> hmDPC)
/* 177:    */   {
/* 178:205 */     BigDecimal cantidad = ddc.getCantidad();
/* 179:    */     
/* 180:207 */     BigDecimal porciento = BigDecimal.ZERO;
/* 181:208 */     if ((ddc.getDespachoCliente().getEmpresa().getCliente().getTipoOrdenDespacho() != null) && 
/* 182:209 */       (ddc.getDespachoCliente().getEmpresa().getCliente().getTipoOrdenDespacho().getIndicadorAplicaPorcientoAdicionalPedidos().booleanValue())) {
/* 183:210 */       porciento = new BigDecimal(ddc.getProducto().getPorCientoDespachoSuperaPedido());
/* 184:    */     }
/* 185:214 */     BigDecimal porcientoSuperado = porciento.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).add(BigDecimal.ONE);
/* 186:215 */     cantidad = cantidad.divide(porcientoSuperado, 2, RoundingMode.HALF_UP);
/* 187:217 */     if (indicadorReverso) {
/* 188:218 */       cantidad = cantidad.negate();
/* 189:    */     }
/* 190:221 */     if (ddc.getDetallePedidoCliente() != null)
/* 191:    */     {
/* 192:222 */       if (!hmDPC.contains(Integer.valueOf(ddc.getDetallePedidoCliente().getPedidoCliente().getId()))) {
/* 193:223 */         hmDPC.add(ddc.getDetallePedidoCliente().getPedidoCliente());
/* 194:    */       }
/* 195:225 */       actualizarCantidadPorDespachar(ddc.getDetallePedidoCliente(), cantidad);
/* 196:    */     }
/* 197:227 */     else if ((ddc.getDetalleFacturaCliente() != null) && (ddc.getDetalleFacturaCliente().getDetallePedidoCliente() != null))
/* 198:    */     {
/* 199:228 */       if (!hmDPC.contains(Integer.valueOf(ddc.getDetalleFacturaCliente().getDetallePedidoCliente().getPedidoCliente().getId()))) {
/* 200:229 */         hmDPC.add(ddc.getDetalleFacturaCliente().getDetallePedidoCliente().getPedidoCliente());
/* 201:    */       }
/* 202:231 */       actualizarCantidadPorDespachar(ddc.getDetalleFacturaCliente().getDetallePedidoCliente(), cantidad);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   @Lock(LockType.WRITE)
/* 207:    */   private void actualizarCantidadPorFacturar(DetallePedidoCliente dpc, BigDecimal cantidad)
/* 208:    */   {
/* 209:237 */     this.pedidoClienteDao.actualizarCantidadPorFacturar(dpc, cantidad);
/* 210:    */   }
/* 211:    */   
/* 212:    */   @Lock(LockType.WRITE)
/* 213:    */   private void actualizarCantidadPorDespachar(DetallePedidoCliente dpc, BigDecimal cantidad)
/* 214:    */   {
/* 215:242 */     this.pedidoClienteDao.actualizarCantidadPorDespachar(dpc, cantidad);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void actualizarCuentaPorCobrar(Cobro cobro, boolean indicadorReverso)
/* 219:    */     throws ExcepcionAS2Ventas
/* 220:    */   {
/* 221:253 */     if ((cobro.getId() > 0) || (!indicadorReverso))
/* 222:    */     {
/* 223:    */       List<DetalleCobro> lista;
/* 224:    */       List<DetalleCobro> lista;
/* 225:256 */       if ((indicadorReverso) && (this.servicioCobro.buscarPorId(Integer.valueOf(cobro.getId())) != null)) {
/* 226:257 */         lista = this.servicioCobro.cargarDetalle(cobro.getId()).getListaDetalleCobro();
/* 227:    */       } else {
/* 228:259 */         lista = cobro.getListaDetalleCobro();
/* 229:    */       }
/* 230:262 */       for (DetalleCobro detalleCobro : lista) {
/* 231:263 */         if (!detalleCobro.isEliminado()) {
/* 232:264 */           actualizarCuentaPorCobrar(detalleCobro.getCuentaPorCobrar(), detalleCobro.getValor(), indicadorReverso, cobro.getFecha(), cobro
/* 233:265 */             .getTolerancia());
/* 234:    */         }
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void actualizarCuentaPorCobrar(LiquidacionAnticipoCliente lac, boolean indicadorReverso)
/* 240:    */     throws ExcepcionAS2Ventas
/* 241:    */   {
/* 242:275 */     if ((lac.getId() > 0) || (!indicadorReverso))
/* 243:    */     {
/* 244:    */       List<DetalleLiquidacionAnticipoCliente> lista;
/* 245:    */       List<DetalleLiquidacionAnticipoCliente> lista;
/* 246:278 */       if ((indicadorReverso) && (this.servicioLiquidacionAnticipoCliente.buscarPorId(Integer.valueOf(lac.getId())) != null)) {
/* 247:279 */         lista = this.servicioLiquidacionAnticipoCliente.cargarDetalle(lac.getId()).getListaDetalleLiquidacionAnticipoCliente();
/* 248:    */       } else {
/* 249:281 */         lista = lac.getListaDetalleLiquidacionAnticipoCliente();
/* 250:    */       }
/* 251:284 */       for (DetalleLiquidacionAnticipoCliente dlac : lista) {
/* 252:285 */         if (!dlac.isEliminado()) {
/* 253:286 */           actualizarCuentaPorCobrar(dlac.getCuentaPorCobrar(), dlac.getValor(), indicadorReverso, lac.getFecha(), BigDecimal.ZERO);
/* 254:    */         }
/* 255:    */       }
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   @Lock(LockType.WRITE)
/* 260:    */   private void actualizarCuentaPorCobrar(CuentaPorCobrar cxc, BigDecimal valor, boolean indicadorReverso, Date fechaPago, BigDecimal toletancia)
/* 261:    */     throws ExcepcionAS2Ventas
/* 262:    */   {
/* 263:305 */     actualizarCreditoUtilizado(this.cuentaPorCobrarDao.obtenerCliente(cxc), valor.negate(), indicadorReverso);
/* 264:307 */     if (indicadorReverso) {
/* 265:308 */       valor = valor.negate();
/* 266:    */     }
/* 267:310 */     BigDecimal saldo = this.cuentaPorCobrarDao.getSaldo(cxc);
/* 268:312 */     if (saldo.subtract(valor).add(toletancia).compareTo(BigDecimal.ZERO) >= 0)
/* 269:    */     {
/* 270:315 */       cxc.setTraFechaPago(fechaPago);
/* 271:    */       
/* 272:    */ 
/* 273:318 */       this.cuentaPorCobrarDao.actualizarCuentaPorCobrar(cxc, valor);
/* 274:    */     }
/* 275:    */     else
/* 276:    */     {
/* 277:321 */       String mensaje = " " + cxc.getFacturaCliente().getNumero() + " " + valor.abs().toString() + " > " + saldo.toString();
/* 278:322 */       throw new ExcepcionAS2Ventas("msg_info_cuenta_por_cobrar_negativa", mensaje);
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   @Lock(LockType.WRITE)
/* 283:    */   public void actualizarCreditoUtilizado(Cliente cliente, BigDecimal valor, boolean indicadorReverso)
/* 284:    */   {
/* 285:337 */     if (indicadorReverso) {
/* 286:338 */       valor = valor.negate();
/* 287:    */     }
/* 288:341 */     this.clienteDao.actualizarCreditoUtilizado(cliente, valor);
/* 289:342 */     this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cliente);
/* 290:    */   }
/* 291:    */   
/* 292:    */   @Lock(LockType.WRITE)
/* 293:    */   public void verificarCupoCredito(Cliente cliente, BigDecimal valor)
/* 294:    */     throws ExcepcionAS2Ventas
/* 295:    */   {
/* 296:354 */     BigDecimal creditoDisponible = this.clienteDao.getCreditoDisponible(cliente);
/* 297:356 */     if (creditoDisponible.subtract(valor).compareTo(BigDecimal.ZERO) < 0)
/* 298:    */     {
/* 299:358 */       String mensaje = " Cliente " + cliente.getEmpresa().getIdentificacion() + " " + valor.abs().toString() + " > " + creditoDisponible.toString();
/* 300:359 */       throw new ExcepcionAS2Ventas("msg_info_credito_maximo_cliente", mensaje);
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void verificarCupoCredito(Cliente cliente, BigDecimal valor, boolean autoriza)
/* 305:    */     throws ExcepcionAS2Ventas
/* 306:    */   {
/* 307:371 */     if (!autoriza) {
/* 308:372 */       verificarCupoCredito(cliente, valor);
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void verificarBloqueoCliente(Cliente cliente, Date fecha)
/* 313:    */     throws ExcepcionAS2Ventas
/* 314:    */   {
/* 315:379 */     verificarPorcentajeMorosidad(cliente, fecha);
/* 316:380 */     verificarCantidadDocumentosSinGarantia(cliente);
/* 317:381 */     verificarDocumentacionCompleta(cliente, fecha);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String verificarBloqueoClientePedido(Cliente cliente, Date fecha)
/* 321:    */     throws ExcepcionAS2Ventas
/* 322:    */   {
/* 323:386 */     String mensajes = "";
/* 324:    */     try
/* 325:    */     {
/* 326:388 */       verificarPorcentajeMorosidad(cliente, fecha);
/* 327:    */     }
/* 328:    */     catch (Exception e)
/* 329:    */     {
/* 330:390 */       e.printStackTrace();
/* 331:391 */       mensajes = "Verificar Porcentaje Morosidad " + e.getMessage() + " ";
/* 332:392 */       System.out.println(mensajes);
/* 333:    */     }
/* 334:    */     try
/* 335:    */     {
/* 336:395 */       verificarCantidadDocumentosSinGarantia(cliente);
/* 337:    */     }
/* 338:    */     catch (Exception e)
/* 339:    */     {
/* 340:397 */       mensajes = mensajes + "Verificar Cantidad Documentos Sin Garantia" + e.getMessage() + " ";
/* 341:398 */       System.out.println(mensajes);
/* 342:    */     }
/* 343:    */     try
/* 344:    */     {
/* 345:401 */       verificarDocumentacionCompleta(cliente, fecha);
/* 346:    */     }
/* 347:    */     catch (Exception e)
/* 348:    */     {
/* 349:403 */       mensajes = mensajes + "Verificar Documentación Incompleta" + e.getMessage();
/* 350:404 */       System.out.println(mensajes);
/* 351:    */     }
/* 352:406 */     return mensajes;
/* 353:    */   }
/* 354:    */   
/* 355:    */   private void verificarPorcentajeMorosidad(Cliente cliente, Date fecha)
/* 356:    */     throws ExcepcionAS2Ventas
/* 357:    */   {
/* 358:410 */     BigDecimal porcentajeMaximoPermitido = cliente.getEmpresa().getCategoriaEmpresa().getPorcentajeMaximoMorosidad();
/* 359:411 */     if (porcentajeMaximoPermitido != null)
/* 360:    */     {
/* 361:413 */       BigDecimal porcentajeMorosidadActual = getPorcentajeMorosidad(cliente.getEmpresa().getId(), fecha);
/* 362:414 */       if (porcentajeMorosidadActual.compareTo(porcentajeMaximoPermitido) > 0)
/* 363:    */       {
/* 364:416 */         String mensaje = " Cliente " + cliente.getEmpresa().getIdentificacion() + ". (" + porcentajeMorosidadActual.toString() + " > " + porcentajeMaximoPermitido.toString() + ")";
/* 365:417 */         throw new ExcepcionAS2Ventas("msg_info_porcentaje_maximo_morosidad", mensaje);
/* 366:    */       }
/* 367:    */     }
/* 368:    */   }
/* 369:    */   
/* 370:    */   private void verificarCantidadDocumentosSinGarantia(Cliente cliente)
/* 371:    */     throws ExcepcionAS2Ventas
/* 372:    */   {
/* 373:423 */     Integer cantidadMaximaDocumentosSinGarantia = cliente.getEmpresa().getCategoriaEmpresa().getNumeroMaximoDocumentosSinGarantia();
/* 374:424 */     if (cantidadMaximaDocumentosSinGarantia != null)
/* 375:    */     {
/* 376:427 */       Integer documentosSinGarantiaActuales = this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cliente);
/* 377:428 */       if (documentosSinGarantiaActuales.compareTo(cantidadMaximaDocumentosSinGarantia) > 0)
/* 378:    */       {
/* 379:430 */         String mensaje = " Cliente " + cliente.getEmpresa().getIdentificacion() + ". (" + documentosSinGarantiaActuales.toString() + " > " + cantidadMaximaDocumentosSinGarantia.toString() + ")";
/* 380:431 */         throw new ExcepcionAS2Ventas("msg_info_cantidad_maxima_documentos_sin_garantia", mensaje);
/* 381:    */       }
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   private void verificarDocumentacionCompleta(Cliente cliente, Date fecha)
/* 386:    */     throws ExcepcionAS2Ventas
/* 387:    */   {
/* 388:437 */     if (cliente.getEmpresa().getCategoriaEmpresa().isVerificarDocumentos()) {
/* 389:438 */       getEmpresaDocumentacionCompleta(cliente, fecha);
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void getEmpresaDocumentacionCompleta(Cliente cliente, Date fecha)
/* 394:    */     throws ExcepcionAS2Ventas
/* 395:    */   {
/* 396:444 */     fecha = FuncionesUtiles.setAtributoFecha(fecha);
/* 397:    */     
/* 398:    */ 
/* 399:447 */     List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizado = this.servicioDetalleDocumentoDigitalizado.cargarListaDetalleDocumentoDigitalizadoEmpresa(cliente.getIdOrganizacion(), cliente.getEmpresa().getIdEmpresa(), cliente
/* 400:448 */       .getEmpresa().getCategoriaEmpresa().getId(), false, true, 0);
/* 401:449 */     String mensaje = "";
/* 402:450 */     for (DetalleDocumentoDigitalizado detalleDocumentoDigitalizado : listaDetalleDocumentoDigitalizado) {
/* 403:451 */       if (((detalleDocumentoDigitalizado.getDocumentoDigitalizadoCategoriaEmpresa().isRequerido()) && (detalleDocumentoDigitalizado.getFichero() == null)) || (
/* 404:452 */         (detalleDocumentoDigitalizado.getFechaHasta() != null) && (detalleDocumentoDigitalizado.getFechaHasta().before(fecha)))) {
/* 405:453 */         mensaje = mensaje + detalleDocumentoDigitalizado.getDocumentoDigitalizado().getNombre() + ", ";
/* 406:    */       }
/* 407:    */     }
/* 408:456 */     if (!mensaje.isEmpty()) {
/* 409:457 */       throw new ExcepcionAS2Ventas("msg_info_documentacion_incompleta", mensaje);
/* 410:    */     }
/* 411:    */   }
/* 412:    */   
/* 413:    */   @Lock(LockType.WRITE)
/* 414:    */   public void actualizarPrecioFechaUltimaVenta(FacturaCliente facturaCliente)
/* 415:    */   {
/* 416:469 */     for (DetalleFacturaCliente dfc : facturaCliente.getListaDetalleFacturaCliente()) {
/* 417:470 */       if (!dfc.isEliminado()) {
/* 418:471 */         this.productoDao.actualizarPrecioFechaUltimaVenta(facturaCliente.getFecha(), dfc.getPrecio(), dfc.getProducto().getIdProducto());
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   public BigDecimal getPorcentajeMorosidad(int idEmpresa, Date fecha)
/* 424:    */   {
/* 425:478 */     return this.analisisVencimientosClienteDao.getPorcentajeMorosidad(idEmpresa, fecha);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public Integer getNumeroFacturasPendientesSinGarantia(Cliente cliente)
/* 429:    */   {
/* 430:483 */     return this.clienteDao.actualizarNumeroFacturasPendientesSinGarantia(cliente);
/* 431:    */   }
/* 432:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioVerificadorVentasImpl
 * JD-Core Version:    0.7.0.1
 */