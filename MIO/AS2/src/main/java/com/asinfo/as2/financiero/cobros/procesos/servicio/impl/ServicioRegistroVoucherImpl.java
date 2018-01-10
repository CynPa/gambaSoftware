/*   1:    */ package com.asinfo.as2.financiero.cobros.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleFormaCobroDao;
/*   4:    */ import com.asinfo.as2.dao.MovimientoBancarioDao;
/*   5:    */ import com.asinfo.as2.dao.RegistroVoucherDao;
/*   6:    */ import com.asinfo.as2.dao.TarjetaCreditoDao;
/*   7:    */ import com.asinfo.as2.entities.Asiento;
/*   8:    */ import com.asinfo.as2.entities.Banco;
/*   9:    */ import com.asinfo.as2.entities.Cobro;
/*  10:    */ import com.asinfo.as2.entities.Comision;
/*  11:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  12:    */ import com.asinfo.as2.entities.CuentaContable;
/*  13:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*  14:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  15:    */ import com.asinfo.as2.entities.Documento;
/*  16:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  17:    */ import com.asinfo.as2.entities.MovimientoBancarioEstadoCheque;
/*  18:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*  19:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  20:    */ import com.asinfo.as2.entities.Sucursal;
/*  21:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*  22:    */ import com.asinfo.as2.entities.TipoAsiento;
/*  23:    */ import com.asinfo.as2.entities.TipoTarjetaCredito;
/*  24:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  25:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  26:    */ import com.asinfo.as2.enumeraciones.TipoCuentaContable;
/*  27:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  28:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  29:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCobro;
/*  30:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioRegistroVoucher;
/*  31:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  32:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  33:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  34:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  35:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import java.math.BigDecimal;
/*  38:    */ import java.math.RoundingMode;
/*  39:    */ import java.util.ArrayList;
/*  40:    */ import java.util.Collections;
/*  41:    */ import java.util.Comparator;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.HashMap;
/*  44:    */ import java.util.Iterator;
/*  45:    */ import java.util.List;
/*  46:    */ import java.util.Map;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.ejb.SessionContext;
/*  49:    */ import javax.ejb.Stateless;
/*  50:    */ 
/*  51:    */ @Stateless
/*  52:    */ public class ServicioRegistroVoucherImpl
/*  53:    */   extends AbstractServicioAS2Financiero
/*  54:    */   implements ServicioRegistroVoucher
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 1L;
/*  57:    */   @EJB
/*  58:    */   private RegistroVoucherDao registroVoucherDao;
/*  59:    */   @EJB
/*  60:    */   private DetalleFormaCobroDao detalleFormaCobroDao;
/*  61:    */   @EJB
/*  62:    */   private ServicioPeriodo servicioPeriodo;
/*  63:    */   @EJB
/*  64:    */   private ServicioCobro servicioCobro;
/*  65:    */   @EJB
/*  66:    */   private TarjetaCreditoDao tarjetaCreditoDao;
/*  67:    */   @EJB
/*  68:    */   private ServicioAsiento servicioAsiento;
/*  69:    */   @EJB
/*  70:    */   private MovimientoBancarioDao movimientoBancarioDao;
/*  71:    */   
/*  72:    */   public void guardarRegistroRegistro(Cobro cobro)
/*  73:    */     throws AS2Exception, ExcepcionAS2
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77: 90 */       validar(cobro);
/*  78:    */       
/*  79: 92 */       guardar(cobro);
/*  80:    */       
/*  81: 94 */       contabilizarRegistroVoucher(cobro);
/*  82:    */     }
/*  83:    */     catch (ExcepcionAS2Financiero e)
/*  84:    */     {
/*  85: 96 */       this.context.setRollbackOnly();
/*  86: 97 */       throw e;
/*  87:    */     }
/*  88:    */     catch (ExcepcionAS2 e)
/*  89:    */     {
/*  90: 99 */       this.context.setRollbackOnly();
/*  91:100 */       throw e;
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void validar(Cobro cobro)
/*  96:    */     throws AS2Exception, ExcepcionAS2Financiero
/*  97:    */   {
/*  98:106 */     Map<Date, Date> hmFehas = new HashMap();
/*  99:    */     
/* 100:108 */     Date fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 101:109 */     for (DetalleFormaCobro dfc : cobro.getListaDetalleFormaCobro())
/* 102:    */     {
/* 103:110 */       if ((!dfc.isEliminado()) && 
/* 104:111 */         (dfc.getFechaVoucher().compareTo(fechaDesde) <= 0)) {
/* 105:112 */         throw new AS2Exception("mensaje_error_fecha_erronea", new String[] { FuncionesUtiles.dateToString(dfc.getFechaVoucher()) });
/* 106:    */       }
/* 107:116 */       hmFehas.put(dfc.getFechaVoucher(), dfc.getFechaVoucher());
/* 108:    */     }
/* 109:119 */     for (Date fechaVoucher : hmFehas.values()) {
/* 110:120 */       this.servicioPeriodo.buscarPorFecha(fechaVoucher, cobro.getIdOrganizacion(), cobro.getDocumento().getDocumentoBase());
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void guardarCobroVoucher(Cobro cobro)
/* 115:    */     throws AS2Exception, ExcepcionAS2
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:129 */       this.servicioPeriodo.buscarPorFecha(cobro.getFecha(), cobro.getIdOrganizacion(), cobro.getDocumento().getDocumentoBase());
/* 120:    */       
/* 121:    */ 
/* 122:132 */       this.servicioCobro.cargarSecuencia(cobro);
/* 123:    */       
/* 124:134 */       this.registroVoucherDao.guardar(cobro);
/* 125:136 */       for (DetalleFormaCobro dfc : cobro.getListaDetalleFormaCobro())
/* 126:    */       {
/* 127:137 */         boolean indicadorCobrado = dfc.isIndicadorCobrado();
/* 128:139 */         if (dfc.isEliminado())
/* 129:    */         {
/* 130:140 */           dfc.setEliminado(false);
/* 131:141 */           dfc.setBanco(null);
/* 132:142 */           dfc.setCobroTarjeta(null);
/* 133:143 */           dfc.setIndicadorCobrado(false);
/* 134:    */         }
/* 135:146 */         dfc.setIndicadorCobrado(dfc.getValorPagado().compareTo(BigDecimal.ZERO) > 0);
/* 136:148 */         if ((indicadorCobrado) || (dfc.getValorPagado().compareTo(BigDecimal.ZERO) > 0)) {
/* 137:149 */           this.detalleFormaCobroDao.guardar(dfc);
/* 138:    */         }
/* 139:    */       }
/* 140:153 */       contabilizarCobroVoucher(cobro);
/* 141:    */     }
/* 142:    */     catch (ExcepcionAS2Financiero e)
/* 143:    */     {
/* 144:156 */       this.context.setRollbackOnly();
/* 145:157 */       throw e;
/* 146:    */     }
/* 147:    */     catch (ExcepcionAS2 e)
/* 148:    */     {
/* 149:159 */       this.context.setRollbackOnly();
/* 150:160 */       throw e;
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private Cobro guardar(Cobro cobro)
/* 155:    */     throws AS2Exception, ExcepcionAS2
/* 156:    */   {
/* 157:167 */     this.servicioPeriodo.buscarPorFecha(cobro.getFecha(), cobro.getIdOrganizacion(), cobro.getDocumento().getDocumentoBase());
/* 158:    */     
/* 159:    */ 
/* 160:170 */     this.servicioCobro.cargarSecuencia(cobro);
/* 161:173 */     for (DetalleFormaCobro dfc : cobro.getListaDetalleFormaCobro()) {
/* 162:174 */       this.detalleFormaCobroDao.guardar(dfc);
/* 163:    */     }
/* 164:177 */     this.registroVoucherDao.guardar(cobro);
/* 165:178 */     return cobro;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void esEditable(Cobro cobro)
/* 169:    */     throws ExcepcionAS2Financiero
/* 170:    */   {
/* 171:184 */     Cobro cobroBDD = this.servicioCobro.buscarPorId(Integer.valueOf(cobro.getId()));
/* 172:185 */     this.servicioPeriodo.buscarPorFecha(cobroBDD.getFecha(), cobroBDD.getIdOrganizacion(), cobro.getDocumento().getDocumentoBase());
/* 173:187 */     if (cobroBDD.getEstado().equals(Estado.ANULADO)) {
/* 174:188 */       throw new ExcepcionAS2Financiero("msg_error_editar");
/* 175:    */     }
/* 176:191 */     Map<Integer, Asiento> hmAsientos = new HashMap();
/* 177:192 */     if (cobroBDD.getDocumento().getDocumentoBase() == DocumentoBase.COBRO_VOUCHER)
/* 178:    */     {
/* 179:194 */       if (cobroBDD.getAsiento() != null) {
/* 180:195 */         hmAsientos.put(Integer.valueOf(cobroBDD.getAsiento().getId()), cobroBDD.getAsiento());
/* 181:    */       }
/* 182:    */     }
/* 183:    */     else {
/* 184:199 */       for (DetalleFormaCobro voucher : cobro.getListaDetalleFormaCobro()) {
/* 185:200 */         if (voucher.getAsiento() != null) {
/* 186:201 */           hmAsientos.put(Integer.valueOf(voucher.getAsiento().getId()), voucher.getAsiento());
/* 187:    */         }
/* 188:    */       }
/* 189:    */     }
/* 190:206 */     for (Asiento asiento : hmAsientos.values())
/* 191:    */     {
/* 192:208 */       asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 193:209 */       asiento.setIndicadorAutomatico(false);
/* 194:210 */       this.servicioAsiento.esEditable(asiento);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void conciliarVoucher(Banco banco, Cobro cobroTarjeta, DetalleFormaCobro dfc, BigDecimal porcentajeIVA, boolean conciliar)
/* 199:    */     throws AS2Exception
/* 200:    */   {
/* 201:219 */     dfc.setValorPagadoCalculado(BigDecimal.ZERO);
/* 202:220 */     dfc.setValorComision(BigDecimal.ZERO);
/* 203:221 */     dfc.setImpuestoComision(BigDecimal.ZERO);
/* 204:223 */     if (conciliar)
/* 205:    */     {
/* 206:224 */       BigDecimal impuestoComision = BigDecimal.ZERO;
/* 207:225 */       BigDecimal valorCalculoComision = dfc.getValor();
/* 208:227 */       if (!banco.isIndicadorIvaSobreComision()) {
/* 209:229 */         valorCalculoComision = dfc.getValor().subtract(dfc.getMontoIva());
/* 210:    */       }
/* 211:231 */       Comision comision = this.tarjetaCreditoDao.getComision(dfc.getTarjetaCredito(), dfc.getPlanTarjetaCredito(), dfc.getFechaVoucher());
/* 212:232 */       dfc.setPorcentajeComision(comision.getPorcentaje());
/* 213:233 */       BigDecimal valorComision = FuncionesUtiles.porcentaje(valorCalculoComision, comision.getPorcentaje(), 20);
/* 214:234 */       dfc.setValorComision(valorComision.setScale(2, RoundingMode.HALF_UP));
/* 215:235 */       if (comision.isIndicadorIvaSobreComision())
/* 216:    */       {
/* 217:236 */         impuestoComision = FuncionesUtiles.porcentaje(valorComision, porcentajeIVA);
/* 218:237 */         dfc.setImpuestoComision(impuestoComision);
/* 219:    */       }
/* 220:239 */       dfc.setValorPagadoCalculado(dfc.getValor().subtract(dfc.getValorComision()).subtract(impuestoComision).subtract(dfc.getRetencionFuente())
/* 221:240 */         .subtract(dfc.getRetencionIva()));
/* 222:    */       
/* 223:    */ 
/* 224:243 */       dfc.setValorPagado(dfc.getValorPagadoCalculado());
/* 225:    */       
/* 226:    */ 
/* 227:246 */       dfc.setCobroTarjeta(cobroTarjeta);
/* 228:247 */       dfc.setBanco(banco);
/* 229:    */     }
/* 230:    */     else
/* 231:    */     {
/* 232:249 */       dfc.setBanco(null);
/* 233:250 */       dfc.setCobroTarjeta(null);
/* 234:251 */       dfc.setValorPagado(BigDecimal.ZERO);
/* 235:252 */       dfc.setRetencionFuente(BigDecimal.ZERO);
/* 236:253 */       dfc.setRetencionIva(BigDecimal.ZERO);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   private void contabilizarCobroVoucher(Cobro cobro)
/* 241:    */     throws ExcepcionAS2
/* 242:    */   {
/* 243:259 */     Map<Integer, DetalleAsiento> hmIvaComision = new HashMap();
/* 244:260 */     Map<Integer, DetalleAsiento> hmRetencionFuente = new HashMap();
/* 245:261 */     Map<Integer, DetalleAsiento> hmRetencionIva = new HashMap();
/* 246:262 */     Banco bancoPagador = null;
/* 247:    */     
/* 248:264 */     Asiento asiento = cobro.getAsiento();
/* 249:265 */     if (cobro.getAsiento() != null)
/* 250:    */     {
/* 251:266 */       asiento = this.servicioAsiento.cargarDetalle(cobro.getAsiento().getId());
/* 252:267 */       for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento())
/* 253:    */       {
/* 254:268 */         detalleAsiento.setEliminado(true);
/* 255:269 */         if (detalleAsiento.getMovimientoBancario() != null)
/* 256:    */         {
/* 257:270 */           detalleAsiento.getMovimientoBancario().setEliminado(true);
/* 258:271 */           for (MovimientoBancarioEstadoCheque movimientoBancarioEstadoCheque : detalleAsiento.getMovimientoBancario()
/* 259:272 */             .getListaMovimientoBancarioEstadoCheque()) {
/* 260:273 */             movimientoBancarioEstadoCheque.setEliminado(true);
/* 261:    */           }
/* 262:    */         }
/* 263:    */       }
/* 264:    */     }
/* 265:    */     else
/* 266:    */     {
/* 267:278 */       asiento = new Asiento();
/* 268:279 */       asiento.setIdOrganizacion(cobro.getIdOrganizacion());
/* 269:280 */       asiento.setSucursal(cobro.getSucursal());
/* 270:281 */       TipoAsiento tipoAsiento = cobro.getDocumento().getTipoAsiento();
/* 271:282 */       asiento.setTipoAsiento(tipoAsiento);
/* 272:283 */       asiento.setEstado(Estado.ELABORADO);
/* 273:284 */       cobro.setAsiento(asiento);
/* 274:    */     }
/* 275:286 */     asiento.setIndicadorAutomatico(true);
/* 276:287 */     asiento.setDocumentoOrigen(cobro.getDocumento());
/* 277:    */     DetalleAsiento daIVACo;
/* 278:289 */     if (!cobro.getListaDetalleFormaCobro().isEmpty())
/* 279:    */     {
/* 280:291 */       CuentaBancariaOrganizacion cuentaBancariaOrganizacion = cobro.getCuentaBancariaOrganizacion();
/* 281:295 */       if (cuentaBancariaOrganizacion.getCuentaContableDiferencias() == null) {
/* 282:297 */         throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + cuentaBancariaOrganizacion.getNombreCompleto() + " (CUENTA CONTABLE DIFERENCIA)");
/* 283:    */       }
/* 284:300 */       if (cuentaBancariaOrganizacion.getCuentaContableGastosBancarios() == null) {
/* 285:302 */         throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + cuentaBancariaOrganizacion.getNombreCompleto() + " (CUENTA CONTABLE GASTOS BANCARIOS)");
/* 286:    */       }
/* 287:305 */       if (cuentaBancariaOrganizacion.getCuentaContableTransitoria() == null) {
/* 288:307 */         throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + cuentaBancariaOrganizacion.getNombreCompleto() + " (CUENTA CONTABLE TRANSITORIA)");
/* 289:    */       }
/* 290:311 */       String concepto = cobro.getDocumento().getNombre() + " " + cobro.getNumero();
/* 291:312 */       asiento.setFecha(cobro.getFecha());
/* 292:    */       
/* 293:314 */       asiento.setConcepto(concepto);
/* 294:    */       
/* 295:316 */       asiento.setConcepto(asiento.getConcepto() + (cobro.getDescripcion() == null ? "" : new StringBuilder().append(" ").append(cobro.getDescripcion()).toString()));
/* 296:    */       
/* 297:    */ 
/* 298:319 */       BigDecimal totalValorComision = BigDecimal.ZERO;
/* 299:320 */       BigDecimal totalIvaComision = BigDecimal.ZERO;
/* 300:321 */       List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 301:322 */       DetalleAsiento detalleAsientoBancoTransitoria = null;
/* 302:323 */       CuentaContable cuentaContableBancoTransitoria = cobro.getCuentaBancariaOrganizacion().getCuentaContableTransitoria();
/* 303:324 */       for (DetalleFormaCobro dfc : cobro.getListaDetalleFormaCobro()) {
/* 304:325 */         if ((!dfc.isEliminado()) && (dfc.isIndicadorCobrado()) && (dfc.getValorPagado().compareTo(BigDecimal.ZERO) > 0))
/* 305:    */         {
/* 306:327 */           if (bancoPagador == null) {
/* 307:328 */             bancoPagador = dfc.getBanco();
/* 308:    */           }
/* 309:331 */           totalValorComision = totalValorComision.add(dfc.getValorComision());
/* 310:332 */           TarjetaCredito tarjeta = dfc.getTarjetaCredito();
/* 311:    */           
/* 312:    */ 
/* 313:335 */           String conceptoDetalle = concepto + " " + dfc.getPuntoVenta().getCodigoAlterno() + " " + dfc.getDocumentoReferencia() + " " + tarjeta.getTipoTarjetaCredito().getCodigo();
/* 314:    */           
/* 315:    */ 
/* 316:    */ 
/* 317:339 */           DetalleAsiento daCXC = new DetalleAsiento(asiento, tarjeta.getCuentaBancariaOrganizacion().getCuentaContableBanco(), dfc.getValor().negate(), conceptoDetalle);
/* 318:340 */           listaDetalleAsiento.add(daCXC);
/* 319:343 */           if (dfc.getValorPagado().compareTo(dfc.getValorPagadoCalculado()) != 0)
/* 320:    */           {
/* 321:345 */             DetalleAsiento daIVACo = new DetalleAsiento(asiento, cuentaBancariaOrganizacion.getCuentaContableDiferencias(), dfc.getValorPagadoCalculado().subtract(dfc.getValorPagado()), conceptoDetalle + " - DIF COMM");
/* 322:346 */             listaDetalleAsiento.add(daIVACo);
/* 323:    */           }
/* 324:351 */           if (detalleAsientoBancoTransitoria == null)
/* 325:    */           {
/* 326:352 */             detalleAsientoBancoTransitoria = new DetalleAsiento(asiento, cuentaContableBancoTransitoria, BigDecimal.ZERO, concepto);
/* 327:353 */             asiento.getListaDetalleAsiento().add(detalleAsientoBancoTransitoria);
/* 328:354 */             if (cuentaContableBancoTransitoria.getTipoCuentaContable() == TipoCuentaContable.BANCO)
/* 329:    */             {
/* 330:355 */               MovimientoBancario movimientoBancario = new MovimientoBancario();
/* 331:356 */               movimientoBancario.setIdOrganizacion(cobro.getIdOrganizacion());
/* 332:357 */               movimientoBancario.setIdSucursal(cobro.getSucursal().getId());
/* 333:358 */               movimientoBancario.setFecha(cobro.getFecha());
/* 334:359 */               movimientoBancario.setEliminado(false);
/* 335:    */               
/* 336:361 */               movimientoBancario.setCuentaBancariaOrganizacion(cuentaBancariaOrganizacion);
/* 337:362 */               movimientoBancario.setDocumento(cobro.getDocumento());
/* 338:363 */               movimientoBancario.setFormaPago(cobro.getFormaPago());
/* 339:364 */               movimientoBancario.setEstado(Estado.CONTABILIZADO);
/* 340:365 */               movimientoBancario.setEstadoCheque(null);
/* 341:366 */               movimientoBancario.setDocumentoReferencia(cobro.getDocumentoReferencia());
/* 342:367 */               movimientoBancario.setDetalleAsiento(detalleAsientoBancoTransitoria);
/* 343:368 */               detalleAsientoBancoTransitoria.setMovimientoBancario(movimientoBancario);
/* 344:    */             }
/* 345:    */           }
/* 346:372 */           detalleAsientoBancoTransitoria.setDebe(detalleAsientoBancoTransitoria.getDebe().add(dfc.getValorPagado()));
/* 347:    */           
/* 348:    */ 
/* 349:375 */           CuentaContable cuentaIvaComision = dfc.getTarjetaCredito().getCuentaContableIvaComision();
/* 350:376 */           if (cuentaIvaComision != null)
/* 351:    */           {
/* 352:377 */             DetalleAsiento daIvaComision = (DetalleAsiento)hmIvaComision.get(Integer.valueOf(cuentaIvaComision.getId()));
/* 353:378 */             if (daIvaComision == null)
/* 354:    */             {
/* 355:379 */               daIvaComision = new DetalleAsiento(asiento, cuentaIvaComision, BigDecimal.ZERO, concepto);
/* 356:380 */               asiento.getListaDetalleAsiento().add(daIvaComision);
/* 357:381 */               hmIvaComision.put(Integer.valueOf(daIvaComision.getCuentaContable().getId()), daIvaComision);
/* 358:    */             }
/* 359:383 */             daIvaComision.setDebe(daIvaComision.getDebe().add(dfc.getImpuestoComision()));
/* 360:    */           }
/* 361:    */           else
/* 362:    */           {
/* 363:385 */             totalIvaComision = totalIvaComision.add(dfc.getImpuestoComision());
/* 364:    */           }
/* 365:389 */           if (dfc.getRetencionFuente().compareTo(BigDecimal.ZERO) > 0)
/* 366:    */           {
/* 367:390 */             CuentaContable cuentaRetencionFuente = dfc.getTarjetaCredito().getCuentaContableRetencionFuente();
/* 368:391 */             if (cuentaRetencionFuente == null) {
/* 369:393 */               throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + dfc.getTarjetaCredito().getNombre() + " (CUENTA CONTABLE RETENCION FUENTE)");
/* 370:    */             }
/* 371:395 */             DetalleAsiento daRetencionFuente = (DetalleAsiento)hmRetencionFuente.get(Integer.valueOf(cuentaRetencionFuente.getId()));
/* 372:396 */             if (daRetencionFuente == null)
/* 373:    */             {
/* 374:397 */               daRetencionFuente = new DetalleAsiento(asiento, cuentaRetencionFuente, BigDecimal.ZERO, concepto);
/* 375:398 */               asiento.getListaDetalleAsiento().add(daRetencionFuente);
/* 376:399 */               hmRetencionFuente.put(Integer.valueOf(daRetencionFuente.getCuentaContable().getId()), daRetencionFuente);
/* 377:    */             }
/* 378:401 */             daRetencionFuente.setDebe(daRetencionFuente.getDebe().add(dfc.getRetencionFuente()));
/* 379:    */           }
/* 380:405 */           if (dfc.getRetencionIva().compareTo(BigDecimal.ZERO) > 0)
/* 381:    */           {
/* 382:406 */             CuentaContable cuentaRetencionIva = dfc.getTarjetaCredito().getCuentaContableRetencionIva();
/* 383:407 */             if (cuentaRetencionIva == null) {
/* 384:409 */               throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + dfc.getTarjetaCredito().getNombre() + " (CUENTA CONTABLE RETENCION IVA)");
/* 385:    */             }
/* 386:411 */             DetalleAsiento daRetencionIva = (DetalleAsiento)hmRetencionIva.get(Integer.valueOf(cuentaRetencionIva.getId()));
/* 387:412 */             if (daRetencionIva == null)
/* 388:    */             {
/* 389:413 */               daRetencionIva = new DetalleAsiento(asiento, cuentaRetencionIva, BigDecimal.ZERO, concepto);
/* 390:414 */               asiento.getListaDetalleAsiento().add(daRetencionIva);
/* 391:415 */               hmRetencionIva.put(Integer.valueOf(daRetencionIva.getCuentaContable().getId()), daRetencionIva);
/* 392:    */             }
/* 393:417 */             daRetencionIva.setDebe(daRetencionIva.getDebe().add(dfc.getRetencionIva()));
/* 394:    */           }
/* 395:    */         }
/* 396:    */       }
/* 397:422 */       if ((detalleAsientoBancoTransitoria != null) && (detalleAsientoBancoTransitoria.getMovimientoBancario() != null)) {
/* 398:424 */         detalleAsientoBancoTransitoria.getMovimientoBancario().setValor(detalleAsientoBancoTransitoria.getDebe().subtract(detalleAsientoBancoTransitoria.getHaber()));
/* 399:    */       }
/* 400:427 */       DetalleAsiento daCo = new DetalleAsiento(asiento, cuentaBancariaOrganizacion.getCuentaContableGastosBancarios(), totalValorComision, concepto + "- COMM");
/* 401:    */       
/* 402:429 */       listaDetalleAsiento.add(daCo);
/* 403:432 */       if (totalIvaComision.compareTo(BigDecimal.ZERO) > 0)
/* 404:    */       {
/* 405:433 */         daIVACo = new DetalleAsiento(asiento, cuentaBancariaOrganizacion.getCuentaContableGastosBancarios(), totalIvaComision, concepto + " - IVA COMM");
/* 406:    */         
/* 407:435 */         listaDetalleAsiento.add(daIVACo);
/* 408:    */       }
/* 409:439 */       Collections.sort(listaDetalleAsiento, new Comparator()
/* 410:    */       {
/* 411:    */         public int compare(DetalleAsiento o1, DetalleAsiento o2)
/* 412:    */         {
/* 413:441 */           return o1.getCuentaContable().getCodigo().compareTo(o2.getCuentaContable().getCodigo());
/* 414:    */         }
/* 415:    */       });
/* 416:445 */       for (DetalleAsiento da : listaDetalleAsiento) {
/* 417:446 */         asiento.getListaDetalleAsiento().add(da);
/* 418:    */       }
/* 419:    */     }
/* 420:452 */     if (bancoPagador != null) {
/* 421:453 */       asiento.setConcepto(asiento.getConcepto() + " " + bancoPagador.getNombre());
/* 422:    */     }
/* 423:456 */     if (!asiento.getListaDetalleAsiento().isEmpty())
/* 424:    */     {
/* 425:457 */       cobro.setAsiento(null);
/* 426:458 */       this.servicioAsiento.guardar(asiento);
/* 427:    */       
/* 428:460 */       cobro.setAsiento(asiento);
/* 429:461 */       this.registroVoucherDao.guardar(cobro);
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   private void contabilizarRegistroVoucher(Cobro cobro)
/* 434:    */     throws ExcepcionAS2
/* 435:    */   {
/* 436:468 */     Map<String, List<DetalleFormaCobro>> hmVouchers = new HashMap();
/* 437:469 */     Map<Integer, Asiento> hmAsientosOld = new HashMap();
/* 438:470 */     Map<Integer, Asiento> hmAsientosNew = new HashMap();
/* 439:471 */     List<Asiento> listaAsientos = new ArrayList();
/* 440:    */     
/* 441:473 */     String clave = null;
/* 442:475 */     for (Iterator localIterator1 = cobro.getListaDetalleFormaCobro().iterator(); localIterator1.hasNext();)
/* 443:    */     {
/* 444:475 */       dfc = (DetalleFormaCobro)localIterator1.next();
/* 445:477 */       if (dfc.getAsiento() != null) {
/* 446:478 */         hmAsientosOld.put(Integer.valueOf(dfc.getAsiento().getId()), dfc.getAsiento());
/* 447:    */       }
/* 448:481 */       if (!dfc.isEliminado())
/* 449:    */       {
/* 450:483 */         clave = dfc.getPuntoVenta().getCodigo() + "~" + FuncionesUtiles.dateToString(dfc.getFechaVoucher());
/* 451:484 */         List<DetalleFormaCobro> listaVouchers = (List)hmVouchers.get(clave);
/* 452:485 */         if (listaVouchers == null)
/* 453:    */         {
/* 454:486 */           listaVouchers = new ArrayList();
/* 455:487 */           hmVouchers.put(clave, listaVouchers);
/* 456:    */         }
/* 457:490 */         listaVouchers.add(dfc);
/* 458:    */       }
/* 459:    */     }
/* 460:    */     DetalleFormaCobro dfc;
/* 461:493 */     listaAsientos.addAll(hmAsientosOld.values());
/* 462:    */     
/* 463:    */ 
/* 464:496 */     int i = 0;
/* 465:497 */     for (List<DetalleFormaCobro> lista : hmVouchers.values())
/* 466:    */     {
/* 467:499 */       Map<Integer, DetalleAsiento> hmCxCTarjeta = new HashMap();
/* 468:    */       
/* 469:501 */       asiento = null;
/* 470:502 */       if (i < listaAsientos.size())
/* 471:    */       {
/* 472:503 */         asiento = (Asiento)listaAsientos.get(i++);
/* 473:504 */         asiento = this.servicioAsiento.cargarDetalle(asiento.getId());
/* 474:505 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento()) {
/* 475:506 */           detalleAsiento.setEliminado(true);
/* 476:    */         }
/* 477:    */       }
/* 478:    */       else
/* 479:    */       {
/* 480:509 */         asiento = new Asiento();
/* 481:510 */         asiento.setIdOrganizacion(cobro.getIdOrganizacion());
/* 482:511 */         asiento.setSucursal(cobro.getSucursal());
/* 483:512 */         TipoAsiento tipoAsiento = cobro.getDocumento().getTipoAsiento();
/* 484:513 */         asiento.setTipoAsiento(tipoAsiento);
/* 485:514 */         asiento.setEstado(Estado.ELABORADO);
/* 486:    */       }
/* 487:516 */       asiento.setIndicadorAutomatico(true);
/* 488:517 */       asiento.setDocumentoOrigen(cobro.getDocumento());
/* 489:519 */       if (!lista.isEmpty())
/* 490:    */       {
/* 491:522 */         concepto = cobro.getDocumento().getNombre() + " " + cobro.getNumero();
/* 492:523 */         asiento.setConcepto((String)concepto + (cobro.getDescripcion() == null ? "" : new StringBuilder().append(" ").append(cobro.getDescripcion()).toString()));
/* 493:524 */         asiento.setFecha(((DetalleFormaCobro)lista.get(0)).getFechaVoucher());
/* 494:525 */         asiento.setDocumentoReferencia(((DetalleFormaCobro)lista.get(0)).getPuntoVenta().getCodigoAlterno());
/* 495:    */         
/* 496:    */ 
/* 497:528 */         List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/* 498:529 */         for (DetalleFormaCobro dfc : lista)
/* 499:    */         {
/* 500:531 */           if (dfc.getTarjetaCredito().getCuentaBancariaOrganizacion().getCuentaContableTransitoria() == null) {
/* 501:533 */             throw new ExcepcionAS2("msg_info_seleccionar_cuenta_contable", " " + dfc.getTarjetaCredito().getCuentaBancariaOrganizacion() + " (CUENTA CONTABLE TRANSITORIA)");
/* 502:    */           }
/* 503:536 */           TarjetaCredito tarjeta = dfc.getTarjetaCredito();
/* 504:    */           
/* 505:    */ 
/* 506:539 */           String conceptoDetalle = (String)concepto + " " + dfc.getPuntoVenta().getCodigoAlterno() + " " + dfc.getDocumentoReferencia() + " " + tarjeta.getTipoTarjetaCredito().getCodigo();
/* 507:    */           
/* 508:    */ 
/* 509:    */ 
/* 510:543 */           DetalleAsiento daCXC = new DetalleAsiento(asiento, tarjeta.getCuentaBancariaOrganizacion().getCuentaContableBanco(), dfc.getValor(), conceptoDetalle);
/* 511:544 */           listaDetalleAsiento.add(daCXC);
/* 512:    */           
/* 513:    */ 
/* 514:547 */           CuentaContable cuentaCXCTarjeta = dfc.getTarjetaCredito().getCuentaBancariaOrganizacion().getCuentaContableTransitoria();
/* 515:548 */           DetalleAsiento daPago = (DetalleAsiento)hmCxCTarjeta.get(Integer.valueOf(cuentaCXCTarjeta.getId()));
/* 516:549 */           if (daPago == null)
/* 517:    */           {
/* 518:551 */             daPago = new DetalleAsiento(asiento, cuentaCXCTarjeta, BigDecimal.ZERO, (String)concepto + " " + dfc.getPuntoVenta().getCodigoAlterno());
/* 519:552 */             listaDetalleAsiento.add(daPago);
/* 520:553 */             hmCxCTarjeta.put(Integer.valueOf(daPago.getCuentaContable().getId()), daPago);
/* 521:    */           }
/* 522:555 */           daPago.setHaber(daPago.getHaber().add(dfc.getValor()));
/* 523:    */         }
/* 524:559 */         Collections.sort(listaDetalleAsiento, new Comparator()
/* 525:    */         {
/* 526:    */           public int compare(DetalleAsiento o1, DetalleAsiento o2)
/* 527:    */           {
/* 528:561 */             return o1.getCuentaContable().getCodigo().compareTo(o2.getCuentaContable().getCodigo());
/* 529:    */           }
/* 530:    */         });
/* 531:565 */         for (DetalleAsiento da : listaDetalleAsiento) {
/* 532:566 */           asiento.getListaDetalleAsiento().add(da);
/* 533:    */         }
/* 534:    */       }
/* 535:570 */       if (!asiento.getListaDetalleAsiento().isEmpty())
/* 536:    */       {
/* 537:571 */         this.servicioAsiento.guardar(asiento);
/* 538:572 */         hmAsientosNew.put(Integer.valueOf(asiento.getId()), asiento);
/* 539:573 */         for (concepto = lista.iterator(); ((Iterator)concepto).hasNext();)
/* 540:    */         {
/* 541:573 */           DetalleFormaCobro dfc = (DetalleFormaCobro)((Iterator)concepto).next();
/* 542:574 */           dfc.setAsiento(asiento);
/* 543:575 */           this.detalleFormaCobroDao.guardar(dfc);
/* 544:    */         }
/* 545:    */       }
/* 546:    */     }
/* 547:    */     Asiento asiento;
/* 548:    */     Object concepto;
/* 549:580 */     for (Asiento asiento : hmAsientosNew.values()) {
/* 550:581 */       hmAsientosOld.remove(Integer.valueOf(asiento.getId()));
/* 551:    */     }
/* 552:584 */     for (Asiento asiento : hmAsientosOld.values())
/* 553:    */     {
/* 554:585 */       asiento.setIndicadorAutomatico(false);
/* 555:586 */       this.servicioAsiento.anular(asiento);
/* 556:    */     }
/* 557:    */   }
/* 558:    */   
/* 559:    */   public List<String> getLotesPendientesPorCobrar(int idOrganizacion)
/* 560:    */   {
/* 561:592 */     return this.registroVoucherDao.getLotesPendientesPorCobrar(idOrganizacion);
/* 562:    */   }
/* 563:    */   
/* 564:    */   public List<DetalleFormaCobro> getVouchersNoCobrados(int idOrganizacion, List<TarjetaCredito> listaTarjetaCredito, List<PlanTarjetaCredito> listaPlanTarjeta, List<String> listaLote)
/* 565:    */   {
/* 566:598 */     return this.registroVoucherDao.getVouchersNoCobrados(idOrganizacion, listaTarjetaCredito, listaPlanTarjeta, listaLote);
/* 567:    */   }
/* 568:    */   
/* 569:    */   public List<Cobro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 570:    */   {
/* 571:603 */     return this.registroVoucherDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 572:    */   }
/* 573:    */   
/* 574:    */   public int contarPorCriterio(Map<String, String> filters)
/* 575:    */   {
/* 576:608 */     return this.registroVoucherDao.contarPorCriterio(filters);
/* 577:    */   }
/* 578:    */   
/* 579:    */   public Cobro cargarDetalle(Cobro cobro, boolean cobroTarjeta)
/* 580:    */   {
/* 581:613 */     cobro = this.registroVoucherDao.cargarDetalle(cobro, cobroTarjeta);
/* 582:614 */     if (cobro.getCuentaBancariaOrganizacion() != null) {
/* 583:615 */       cobro.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(cobro.getCuentaBancariaOrganizacion().getId()));
/* 584:    */     }
/* 585:617 */     return cobro;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public List<Cobro> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 589:    */   {
/* 590:622 */     return this.registroVoucherDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 591:    */   }
/* 592:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.impl.ServicioRegistroVoucherImpl
 * JD-Core Version:    0.7.0.1
 */