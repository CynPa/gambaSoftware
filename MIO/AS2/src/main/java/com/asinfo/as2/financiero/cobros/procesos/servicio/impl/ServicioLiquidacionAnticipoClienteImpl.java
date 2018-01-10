/*   1:    */ package com.asinfo.as2.financiero.cobros.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContableProceso;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.dao.AnticipoClienteDao;
/*   6:    */ import com.asinfo.as2.dao.DetalleLiquidacionAnticipoClienteDao;
/*   7:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*   8:    */ import com.asinfo.as2.dao.LiquidacionAnticipoClienteDao;
/*   9:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  10:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*  11:    */ import com.asinfo.as2.entities.Asiento;
/*  12:    */ import com.asinfo.as2.entities.CriterioDistribucion;
/*  13:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  14:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  15:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*  16:    */ import com.asinfo.as2.entities.Documento;
/*  17:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  18:    */ import com.asinfo.as2.entities.Empresa;
/*  19:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  20:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  23:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  24:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  25:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  26:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  27:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  28:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  29:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  30:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion;
/*  31:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  34:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  35:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  36:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  37:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  38:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  39:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*  40:    */ import java.io.PrintStream;
/*  41:    */ import java.math.BigDecimal;
/*  42:    */ import java.math.RoundingMode;
/*  43:    */ import java.util.ArrayList;
/*  44:    */ import java.util.Collection;
/*  45:    */ import java.util.Date;
/*  46:    */ import java.util.Iterator;
/*  47:    */ import java.util.List;
/*  48:    */ import java.util.Map;
/*  49:    */ import javax.ejb.EJB;
/*  50:    */ import javax.ejb.SessionContext;
/*  51:    */ import javax.ejb.Stateless;
/*  52:    */ import javax.ejb.TransactionAttribute;
/*  53:    */ import javax.ejb.TransactionAttributeType;
/*  54:    */ import javax.ejb.TransactionManagement;
/*  55:    */ import javax.ejb.TransactionManagementType;
/*  56:    */ 
/*  57:    */ @Stateless
/*  58:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  59:    */ public class ServicioLiquidacionAnticipoClienteImpl
/*  60:    */   extends AbstractServicioAS2Financiero
/*  61:    */   implements ServicioLiquidacionAnticipoCliente
/*  62:    */ {
/*  63:    */   private static final long serialVersionUID = 3598143267574277827L;
/*  64:    */   @EJB
/*  65:    */   private LiquidacionAnticipoClienteDao liquidacionAnticipoClienteDao;
/*  66:    */   @EJB
/*  67:    */   private FacturaClienteDao facturaClienteDao;
/*  68:    */   @EJB
/*  69:    */   private ServicioVerificadorVentas servicioVerificadorVentas;
/*  70:    */   @EJB
/*  71:    */   private DetalleLiquidacionAnticipoClienteDao detalleLiquidacionAnticipoClienteDao;
/*  72:    */   @EJB
/*  73:    */   private AnticipoClienteDao anticipoClienteDao;
/*  74:    */   @EJB
/*  75:    */   private ServicioSecuencia servicioSecuencia;
/*  76:    */   @EJB
/*  77:    */   private ServicioPeriodo servicioPeriodo;
/*  78:    */   @EJB
/*  79:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  80:    */   @EJB
/*  81:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  82:    */   @EJB
/*  83:    */   private ServicioDocumentoContabilizacion servicioDocumentoContabilizacion;
/*  84:    */   @EJB
/*  85:    */   private ServicioCriterioDistribucion servicioCriterioDistribucion;
/*  86:    */   @EJB
/*  87:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/*  88:    */   @EJB
/*  89:    */   private ServicioSucursal servicioSucursal;
/*  90:    */   
/*  91:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  92:    */   public void guardar(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/*  93:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:116 */       validaFechaLiquidacionAnticipo(liquidacionAnticipoCliente.getAnticipoCliente().getFecha(), liquidacionAnticipoCliente.getFecha());
/*  98:117 */       validar(liquidacionAnticipoCliente);
/*  99:    */       
/* 100:    */ 
/* 101:120 */       this.servicioVerificadorVentas.actualizarCuentaPorCobrar(liquidacionAnticipoCliente, false);
/* 102:    */       
/* 103:    */ 
/* 104:123 */       cargarSecuencia(liquidacionAnticipoCliente);
/* 105:    */       
/* 106:125 */       BigDecimal valorLiquidacion = BigDecimal.ZERO;
/* 107:128 */       for (DetalleLiquidacionAnticipoCliente dLiquidacion : liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente()) {
/* 108:129 */         if (dLiquidacion.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 109:    */         {
/* 110:131 */           if (!dLiquidacion.isEliminado()) {
/* 111:132 */             valorLiquidacion = valorLiquidacion.add(dLiquidacion.getValor());
/* 112:    */           }
/* 113:135 */           this.detalleLiquidacionAnticipoClienteDao.guardar(dLiquidacion);
/* 114:    */         }
/* 115:    */       }
/* 116:140 */       BigDecimal saldoAnticipo = liquidacionAnticipoCliente.getAnticipoCliente().getSaldo();
/* 117:141 */       saldoAnticipo = saldoAnticipo.subtract(valorLiquidacion);
/* 118:142 */       liquidacionAnticipoCliente.getAnticipoCliente().setSaldo(saldoAnticipo);
/* 119:143 */       this.anticipoClienteDao.guardar(liquidacionAnticipoCliente.getAnticipoCliente());
/* 120:    */       
/* 121:    */ 
/* 122:146 */       this.liquidacionAnticipoClienteDao.guardar(liquidacionAnticipoCliente);
/* 123:    */       
/* 124:    */ 
/* 125:149 */       contabilizar(liquidacionAnticipoCliente);
/* 126:    */     }
/* 127:    */     catch (ExcepcionAS2Financiero e)
/* 128:    */     {
/* 129:152 */       this.context.setRollbackOnly();
/* 130:153 */       e.printStackTrace();
/* 131:154 */       throw e;
/* 132:    */     }
/* 133:    */     catch (ExcepcionAS2 e)
/* 134:    */     {
/* 135:156 */       this.context.setRollbackOnly();
/* 136:157 */       e.printStackTrace();
/* 137:158 */       throw e;
/* 138:    */     }
/* 139:    */     catch (Exception e)
/* 140:    */     {
/* 141:160 */       this.context.setRollbackOnly();
/* 142:161 */       e.printStackTrace();
/* 143:162 */       throw new ExcepcionAS2(e);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void eliminar(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 148:    */   {
/* 149:174 */     this.liquidacionAnticipoClienteDao.eliminar(liquidacionAnticipoCliente);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public LiquidacionAnticipoCliente buscarPorId(Integer id)
/* 153:    */   {
/* 154:185 */     return (LiquidacionAnticipoCliente)this.liquidacionAnticipoClienteDao.buscarPorId(id);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public LiquidacionAnticipoCliente cargarDetalle(int idLiquidacionAnticipoCliente)
/* 158:    */   {
/* 159:195 */     return this.liquidacionAnticipoClienteDao.cargarDetalle(idLiquidacionAnticipoCliente);
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void validar(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 163:    */     throws ExcepcionAS2Financiero, ExcepcionAS2
/* 164:    */   {
/* 165:200 */     this.servicioPeriodo.buscarPorFecha(liquidacionAnticipoCliente.getFecha(), liquidacionAnticipoCliente.getIdOrganizacion(), liquidacionAnticipoCliente.getDocumento().getDocumentoBase());
/* 166:    */     
/* 167:    */ 
/* 168:203 */     BigDecimal saldoAnticipo = this.servicioAnticipoCliente.getSaldoAnticipo(liquidacionAnticipoCliente.getAnticipoCliente());
/* 169:205 */     if (saldoAnticipo.compareTo(BigDecimal.ZERO) == 0) {
/* 170:206 */       throw new ExcepcionAS2Financiero("msg_info_cobro_0001");
/* 171:    */     }
/* 172:209 */     liquidacionAnticipoCliente.setValor(BigDecimal.ZERO);
/* 173:210 */     for (DetalleLiquidacionAnticipoCliente detalleCobro : liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente())
/* 174:    */     {
/* 175:211 */       saldoAnticipo = saldoAnticipo.subtract(detalleCobro.getValor());
/* 176:212 */       liquidacionAnticipoCliente.setValor(liquidacionAnticipoCliente.getValor().add(detalleCobro.getValor()));
/* 177:    */       
/* 178:214 */       System.out.println("<<<<<<<<<<<< (!) >>>>>>>>>>>>>>>");
/* 179:215 */       System.out.println("DC Valor: " + detalleCobro.getValor());
/* 180:216 */       System.out.println("SALDO" + detalleCobro.getCuentaPorCobrar().getSaldo());
/* 181:217 */       if (detalleCobro.getValor().compareTo(detalleCobro.getCuentaPorCobrar().getSaldo()) > 0) {
/* 182:218 */         throw new ExcepcionAS2Financiero("msg_info_cobro_0002");
/* 183:    */       }
/* 184:220 */       System.out.println("<<<<<<<<<<<< ! >>>>>>>>>>>>>>>");
/* 185:    */     }
/* 186:223 */     if (saldoAnticipo.compareTo(BigDecimal.ZERO) < 0) {
/* 187:224 */       throw new ExcepcionAS2Financiero("msg_info_cobro_0001");
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   private void cargarSecuencia(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 192:    */     throws ExcepcionAS2
/* 193:    */   {
/* 194:235 */     if (liquidacionAnticipoCliente.getNumero().equals(""))
/* 195:    */     {
/* 196:236 */       String numero = this.servicioSecuencia.obtenerSecuenciaDocumento(liquidacionAnticipoCliente.getDocumento().getIdDocumento(), liquidacionAnticipoCliente
/* 197:237 */         .getFecha());
/* 198:238 */       liquidacionAnticipoCliente.setNumero(numero);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void contabilizar(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 203:    */     throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception
/* 204:    */   {
/* 205:250 */     if (liquidacionAnticipoCliente.getAnticipoCliente().getIndicadorContabilizar().booleanValue())
/* 206:    */     {
/* 207:251 */       Date fechaContabilizacion = liquidacionAnticipoCliente.getFecha();
/* 208:    */       Asiento asiento;
/* 209:    */       Asiento asiento;
/* 210:    */       int idOrganizacion;
/* 211:254 */       if (liquidacionAnticipoCliente.getAsiento() != null)
/* 212:    */       {
/* 213:255 */         asiento = this.servicioAsiento.cargarDetalle(liquidacionAnticipoCliente.getAsiento().getId());
/* 214:    */       }
/* 215:    */       else
/* 216:    */       {
/* 217:257 */         asiento = new Asiento();
/* 218:    */         
/* 219:259 */         idOrganizacion = liquidacionAnticipoCliente.getIdOrganizacion();
/* 220:260 */         Sucursal sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(liquidacionAnticipoCliente.getIdSucursal()));
/* 221:    */         
/* 222:262 */         asiento.setIdOrganizacion(idOrganizacion);
/* 223:263 */         asiento.setSucursal(sucursal);
/* 224:    */         
/* 225:265 */         TipoAsiento tipoAsiento = liquidacionAnticipoCliente.getDocumento().getTipoAsiento();
/* 226:266 */         asiento.setTipoAsiento(tipoAsiento);
/* 227:    */       }
/* 228:268 */       for (DetalleAsiento dt : asiento.getListaDetalleAsiento()) {
/* 229:269 */         dt.setEliminado(true);
/* 230:    */       }
/* 231:271 */       AnticipoCliente anticipoCliente = liquidacionAnticipoCliente.getAnticipoCliente();
/* 232:    */       
/* 233:273 */       String concepto = "";
/* 234:274 */       concepto = liquidacionAnticipoCliente.getDocumento().getNombre().trim() + " #" + liquidacionAnticipoCliente.getNumero();
/* 235:    */       
/* 236:276 */       concepto = concepto + " " + anticipoCliente.getDocumento().getNombre() + " #" + anticipoCliente.getNumero() + " - " + anticipoCliente.getEmpresa().getNombreFiscal();
/* 237:277 */       asiento.setConcepto(concepto);
/* 238:278 */       asiento.setFecha(fechaContabilizacion);
/* 239:279 */       asiento.setEstado(Estado.ELABORADO);
/* 240:280 */       asiento.setIndicadorAutomatico(true);
/* 241:    */       
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:286 */       List<DetalleInterfazContableProceso> lista = new ArrayList();
/* 247:    */       
/* 248:288 */       List<DocumentoContabilizacion> listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(liquidacionAnticipoCliente
/* 249:289 */         .getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/* 250:    */       
/* 251:291 */       List<CriterioDistribucion> listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(liquidacionAnticipoCliente.getIdOrganizacion(), DocumentoBase.FACTURA_CLIENTE);
/* 252:    */       
/* 253:    */ 
/* 254:294 */       lista = new ArrayList();
/* 255:    */       
/* 256:296 */       DocumentoContabilizacion documentoContabilizacion = new DocumentoContabilizacion();
/* 257:297 */       for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/* 258:298 */         if (dc.getProcesoContabilizacion().equals(ProcesoContabilizacionEnum.CXC_CLIENTE)) {
/* 259:299 */           documentoContabilizacion = dc;
/* 260:    */         }
/* 261:    */       }
/* 262:302 */       BigDecimal valorFactura = BigDecimal.ZERO;
/* 263:303 */       BigDecimal pagoTotal = BigDecimal.ZERO;
/* 264:304 */       BigDecimal valorPago = BigDecimal.ZERO;
/* 265:305 */       List<Integer> list = new ArrayList();
/* 266:306 */       for (Iterator localIterator2 = liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().iterator(); localIterator2.hasNext();)
/* 267:    */       {
/* 268:306 */         dl = (DetalleLiquidacionAnticipoCliente)localIterator2.next();
/* 269:307 */         if (dl.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 270:    */         {
/* 271:309 */           list.add(Integer.valueOf(dl.getCuentaPorCobrar().getFacturaCliente().getIdFacturaCliente()));
/* 272:310 */           valorPago = valorPago.add(dl.getValor());
/* 273:    */         }
/* 274:    */       }
/* 275:313 */       Object listaTmp = new ArrayList();
/* 276:314 */       for (DetalleLiquidacionAnticipoCliente dl = liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().iterator(); dl.hasNext();)
/* 277:    */       {
/* 278:314 */         dl = (DetalleLiquidacionAnticipoCliente)dl.next();
/* 279:315 */         if (dl.getValor().compareTo(BigDecimal.ZERO) != 0)
/* 280:    */         {
/* 281:317 */           list = new ArrayList();
/* 282:318 */           list.add(Integer.valueOf(dl.getCuentaPorCobrar().getFacturaCliente().getIdFacturaCliente()));
/* 283:    */           
/* 284:320 */           listaTmp = this.facturaClienteDao.getInterfazVentasDimensiones(list, documentoContabilizacion.getProcesoContabilizacion());
/* 285:321 */           valorFactura = BigDecimal.ZERO;
/* 286:322 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : (List)listaTmp) {
/* 287:323 */             valorFactura = valorFactura.add(detalleInterfazContableProceso.getValor());
/* 288:    */           }
/* 289:325 */           for (DetalleInterfazContableProceso detalleInterfazContableProceso : (List)listaTmp) {
/* 290:326 */             detalleInterfazContableProceso.setValor(dl
/* 291:327 */               .getValor().multiply(detalleInterfazContableProceso.getValor()).divide(valorFactura, 24, RoundingMode.HALF_UP));
/* 292:    */           }
/* 293:329 */           lista.addAll((Collection)listaTmp);
/* 294:330 */           pagoTotal = pagoTotal.add(dl.getValor());
/* 295:    */         }
/* 296:    */       }
/* 297:    */       DetalleLiquidacionAnticipoCliente dl;
/* 298:334 */       List<DetalleAsiento> listaDetalleAsiento = this.servicioInterfazContableProceso.generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true);
/* 299:    */       
/* 300:336 */       asiento.getListaDetalleAsiento().addAll(listaDetalleAsiento);
/* 301:    */       
/* 302:338 */       listaDocumentoContabilizacion = this.servicioDocumentoContabilizacion.obtenerListaPorDocumentoBase(liquidacionAnticipoCliente
/* 303:339 */         .getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/* 304:340 */       for (DocumentoContabilizacion dc : listaDocumentoContabilizacion) {
/* 305:341 */         documentoContabilizacion = dc;
/* 306:    */       }
/* 307:343 */       listaCriterioDistribucion = this.servicioCriterioDistribucion.obtenerListaPorDocumentoBase(liquidacionAnticipoCliente.getIdOrganizacion(), DocumentoBase.ANTICIPO_CLIENTE);
/* 308:    */       
/* 309:345 */       lista = new ArrayList();
/* 310:346 */       list = new ArrayList();
/* 311:347 */       list.add(Integer.valueOf(anticipoCliente.getIdAnticipoCliente()));
/* 312:349 */       for (DetalleInterfazContableProceso detalleInterfazContableProceso : this.anticipoClienteDao.getInterfazAnticipoClienteDimensiones(list))
/* 313:    */       {
/* 314:350 */         detalleInterfazContableProceso.setValor(pagoTotal);
/* 315:351 */         lista.add(detalleInterfazContableProceso);
/* 316:    */       }
/* 317:354 */       asiento.getListaDetalleAsiento().addAll(this.servicioInterfazContableProceso
/* 318:355 */         .generarAsiento(asiento, lista, documentoContabilizacion, listaCriterioDistribucion, true));
/* 319:    */       
/* 320:    */ 
/* 321:358 */       this.servicioAsiento.guardar(asiento);
/* 322:    */       
/* 323:    */ 
/* 324:361 */       liquidacionAnticipoCliente.setEstado(Estado.CONTABILIZADO);
/* 325:    */       
/* 326:363 */       liquidacionAnticipoCliente.setFechaContabilizacion(fechaContabilizacion);
/* 327:    */       
/* 328:    */ 
/* 329:366 */       liquidacionAnticipoCliente.setAsiento(asiento);
/* 330:367 */       this.liquidacionAnticipoClienteDao.guardar(liquidacionAnticipoCliente);
/* 331:    */     }
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void esEditable(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 335:    */     throws ExcepcionAS2Financiero
/* 336:    */   {
/* 337:373 */     this.servicioPeriodo.buscarPorFecha(liquidacionAnticipoCliente.getFecha(), liquidacionAnticipoCliente.getIdOrganizacion(), liquidacionAnticipoCliente.getDocumento().getDocumentoBase());
/* 338:374 */     if ((liquidacionAnticipoCliente.getAsiento() != null) && (liquidacionAnticipoCliente.getAsiento().getEstado() == Estado.REVISADO)) {
/* 339:375 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 340:    */     }
/* 341:377 */     if (liquidacionAnticipoCliente.getEstado() == Estado.ANULADO) {
/* 342:378 */       throw new ExcepcionAS2Financiero("msg_error_anular");
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void actualizarEstado(int idLiquidacionAnticipoCliente, Estado estado)
/* 347:    */   {
/* 348:390 */     this.liquidacionAnticipoClienteDao.actualizarEstado(Integer.valueOf(idLiquidacionAnticipoCliente), estado);
/* 349:    */   }
/* 350:    */   
/* 351:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 352:    */   public void anular(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 353:    */     throws ExcepcionAS2, ExcepcionAS2Financiero
/* 354:    */   {
/* 355:    */     try
/* 356:    */     {
/* 357:400 */       esEditable(liquidacionAnticipoCliente);
/* 358:401 */       this.anticipoClienteDao.actualizarSaldo(liquidacionAnticipoCliente.getAnticipoCliente().getId(), liquidacionAnticipoCliente.getValor());
/* 359:402 */       liquidacionAnticipoCliente.getAnticipoCliente().setSaldo(liquidacionAnticipoCliente
/* 360:403 */         .getAnticipoCliente().getSaldo().add(liquidacionAnticipoCliente.getValor()));
/* 361:    */       
/* 362:405 */       this.servicioVerificadorVentas.actualizarCuentaPorCobrar(liquidacionAnticipoCliente, true);
/* 363:    */       
/* 364:407 */       actualizarEstado(liquidacionAnticipoCliente.getId(), Estado.ANULADO);
/* 365:408 */       if (liquidacionAnticipoCliente.getAsiento() != null)
/* 366:    */       {
/* 367:409 */         liquidacionAnticipoCliente.getAsiento().setIndicadorAutomatico(false);
/* 368:410 */         this.servicioAsiento.anular(liquidacionAnticipoCliente.getAsiento());
/* 369:    */       }
/* 370:    */     }
/* 371:    */     catch (ExcepcionAS2Financiero e)
/* 372:    */     {
/* 373:413 */       this.context.setRollbackOnly();
/* 374:414 */       throw e;
/* 375:    */     }
/* 376:    */     catch (Exception e)
/* 377:    */     {
/* 378:417 */       this.context.setRollbackOnly();
/* 379:418 */       throw new ExcepcionAS2(e);
/* 380:    */     }
/* 381:    */   }
/* 382:    */   
/* 383:    */   public List<LiquidacionAnticipoCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 384:    */   {
/* 385:431 */     return this.liquidacionAnticipoClienteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 386:    */   }
/* 387:    */   
/* 388:    */   public int contarPorCriterio(Map<String, String> filters)
/* 389:    */   {
/* 390:441 */     return this.liquidacionAnticipoClienteDao.contarPorCriterio(filters);
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void validaFechaLiquidacionAnticipo(Date fechaAnticipoCliente, Date fechaLiquidacion)
/* 394:    */     throws ExcepcionAS2Financiero
/* 395:    */   {
/* 396:453 */     Date fechaAnticipoClienteAux = FuncionesUtiles.setAtributoFecha(fechaAnticipoCliente);
/* 397:    */     
/* 398:    */ 
/* 399:456 */     Date fechaLiquidacionAux = FuncionesUtiles.setAtributoFecha(fechaLiquidacion);
/* 400:458 */     if (!FuncionesUtiles.compararFechaAnteriorOIgual(fechaAnticipoClienteAux, fechaLiquidacionAux)) {
/* 401:459 */       throw new ExcepcionAS2Financiero("msg_error_fecha_anticipo_liquidacion");
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   public LiquidacionAnticipoCliente cargarFacturasPendientes(LiquidacionAnticipoCliente liquidacionAnticipoCliente, int idFacturaCliente)
/* 406:    */   {
/* 407:466 */     liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().clear();
/* 408:    */     
/* 409:468 */     int idEmpresa = liquidacionAnticipoCliente.getAnticipoCliente().getEmpresa().getId();
/* 410:    */     
/* 411:470 */     List<CuentaPorCobrar> listaFacturasPendientes = this.servicioFacturaCliente.obtenerFacturasPendientes(idEmpresa, idFacturaCliente);
/* 412:    */     
/* 413:472 */     BigDecimal valorPago = liquidacionAnticipoCliente.getAnticipoCliente().getSaldo();
/* 414:473 */     for (CuentaPorCobrar cxc : listaFacturasPendientes)
/* 415:    */     {
/* 416:474 */       DetalleLiquidacionAnticipoCliente detalleLiquidacionAnticipoCliente = new DetalleLiquidacionAnticipoCliente();
/* 417:475 */       detalleLiquidacionAnticipoCliente.setLiquidacionAnticipoCliente(liquidacionAnticipoCliente);
/* 418:476 */       detalleLiquidacionAnticipoCliente.setCuentaPorCobrar(cxc);
/* 419:    */       
/* 420:478 */       BigDecimal saldoCuentaPorCobra = cxc.getSaldo().subtract(cxc.getValorBloqueado());
/* 421:479 */       BigDecimal valor = BigDecimal.ZERO;
/* 422:481 */       if (valorPago.compareTo(saldoCuentaPorCobra) > 0) {
/* 423:482 */         valor = saldoCuentaPorCobra;
/* 424:    */       } else {
/* 425:484 */         valor = valorPago;
/* 426:    */       }
/* 427:487 */       detalleLiquidacionAnticipoCliente.setValor(valor);
/* 428:488 */       valorPago = valorPago.subtract(valor);
/* 429:    */       
/* 430:490 */       liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().add(detalleLiquidacionAnticipoCliente);
/* 431:    */     }
/* 432:492 */     return liquidacionAnticipoCliente;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public List<LiquidacionAnticipoCliente> getLiquidacionAnticipoClientePorAnticipoCliente(Integer idAnticipoCliente)
/* 436:    */   {
/* 437:504 */     return this.liquidacionAnticipoClienteDao.getLiquidacionAnticipoClientePorAnticipoCliente(idAnticipoCliente);
/* 438:    */   }
/* 439:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioLiquidacionAnticipoClienteImpl
 * JD-Core Version:    0.7.0.1
 */