/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Banco;
/*   5:    */ import com.asinfo.as2.entities.BancoAcreedor;
/*   6:    */ import com.asinfo.as2.entities.Cobro;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   9:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*  12:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoCuentaBancariaOrganizacion;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioRegistroVoucher;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.JsfUtil;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.Iterator;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class PagoVoucherBean
/*  38:    */   extends VoucherBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   private Banco bancoAcreedor;
/*  42:    */   private List<Banco> listaBanco;
/*  43: 53 */   private BigDecimal totalValorComision = BigDecimal.ZERO;
/*  44: 54 */   private BigDecimal totalImpuestoComision = BigDecimal.ZERO;
/*  45: 55 */   private BigDecimal totalValorPagadoCalculado = BigDecimal.ZERO;
/*  46: 56 */   private BigDecimal totalValorPagado = BigDecimal.ZERO;
/*  47: 57 */   private BigDecimal totalRetencionFuente = BigDecimal.ZERO;
/*  48: 58 */   private BigDecimal totalRetencionIva = BigDecimal.ZERO;
/*  49:    */   private TarjetaCredito tarjetaCredito;
/*  50:    */   private List<TarjetaCredito> listaTarjetaCreditoSelected;
/*  51:    */   private List<PlanTarjetaCredito> listaPlanTarjetaSelected;
/*  52:    */   private List<String> listaLote;
/*  53:    */   private List<String> listaLoteSelected;
/*  54:    */   private List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago;
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init()
/*  58:    */   {
/*  59: 70 */     super.init();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String guardar()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 76 */       this.servicioRegistroVoucher.guardarCobroVoucher(this.cobro);
/*  67: 77 */       setEditado(false);
/*  68: 78 */       limpiar();
/*  69:    */       
/*  70: 80 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  71:    */     }
/*  72:    */     catch (ExcepcionAS2Financiero e)
/*  73:    */     {
/*  74: 82 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  75:    */     }
/*  76:    */     catch (ExcepcionAS2 e)
/*  77:    */     {
/*  78: 84 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  79:    */     }
/*  80:    */     catch (AS2Exception e)
/*  81:    */     {
/*  82: 86 */       JsfUtil.addErrorMessage(e, "");
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86: 88 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87: 89 */       LOG.error("COBRO VOUCHERS-ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89: 91 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void crearCobro()
/*  93:    */   {
/*  94: 96 */     super.crearCobro();
/*  95: 98 */     if (getCobro() != null) {
/*  96: 99 */       for (CuentaBancariaOrganizacion cbo : getListaCuentaBancariaOrganizacion()) {
/*  97:100 */         if (cbo.isPredeterminado())
/*  98:    */         {
/*  99:101 */           getCobro().setCuentaBancariaOrganizacion(cbo);
/* 100:102 */           cargarListaBancoAcreedor();
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public DocumentoBase getDocumento()
/* 107:    */   {
/* 108:111 */     return DocumentoBase.COBRO_VOUCHER;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public TipoCuentaBancariaOrganizacion getTipoCuentaBancariaOrganizacion()
/* 112:    */   {
/* 113:116 */     return TipoCuentaBancariaOrganizacion.BANCO;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void desConciliarTodo()
/* 117:    */   {
/* 118:120 */     for (DetalleFormaCobro fc : getListaDetalleVoucher()) {
/* 119:121 */       conciliar(fc, false);
/* 120:    */     }
/* 121:124 */     totalizarCobroTarjeta();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void conciliarTodo()
/* 125:    */   {
/* 126:128 */     for (DetalleFormaCobro fc : getListaDetalleVoucher()) {
/* 127:129 */       conciliar(fc, true);
/* 128:    */     }
/* 129:131 */     totalizarCobroTarjeta();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void conciliarDesConciliar(DetalleFormaCobro dfc, boolean conciliar)
/* 133:    */   {
/* 134:135 */     conciliar(dfc, conciliar);
/* 135:136 */     totalizarCobroTarjeta();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void conciliar(DetalleFormaCobro dfc, boolean conciliar)
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:141 */       this.servicioRegistroVoucher.conciliarVoucher(this.bancoAcreedor, this.cobro, dfc, 
/* 143:142 */         getPorcentajeIVA(AppUtil.getOrganizacion().getId(), dfc.getFechaVoucher()), conciliar);
/* 144:    */     }
/* 145:    */     catch (AS2Exception e)
/* 146:    */     {
/* 147:144 */       JsfUtil.addErrorMessage(e, "");
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public BigDecimal getPorcentajeIVA(int idOrganizacion, Date fecha)
/* 152:    */   {
/* 153:149 */     return this.servicioImpuesto.getPorcentajeIVA(idOrganizacion, fecha);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String editar()
/* 157:    */   {
/* 158:155 */     if (!super.editar().equals("-1"))
/* 159:    */     {
/* 160:157 */       totalizarCobroTarjeta();
/* 161:158 */       cargarListaBancoAcreedor();
/* 162:160 */       if (!getListaDetalleVoucher().isEmpty()) {
/* 163:161 */         this.bancoAcreedor = ((DetalleFormaCobro)getListaDetalleVoucher().get(0)).getBanco();
/* 164:    */       }
/* 165:    */     }
/* 166:165 */     return "";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String limpiar()
/* 170:    */   {
/* 171:170 */     super.limpiar();
/* 172:171 */     this.listaTarjetaCreditoSelected = null;
/* 173:172 */     this.listaPlanTarjetaSelected = null;
/* 174:173 */     this.tarjetaCredito = null;
/* 175:174 */     this.listaLoteSelected = null;
/* 176:175 */     this.bancoAcreedor = null;
/* 177:176 */     return "";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void totalizarCobroTarjeta()
/* 181:    */   {
/* 182:181 */     this.totalValorComision = BigDecimal.ZERO;
/* 183:182 */     this.totalImpuestoComision = BigDecimal.ZERO;
/* 184:183 */     this.totalValorPagadoCalculado = BigDecimal.ZERO;
/* 185:184 */     this.totalValorPagado = BigDecimal.ZERO;
/* 186:185 */     this.totalRetencionIva = BigDecimal.ZERO;
/* 187:186 */     this.totalRetencionFuente = BigDecimal.ZERO;
/* 188:187 */     getCobro().setValor(BigDecimal.ZERO);
/* 189:189 */     for (DetalleFormaCobro dfc : getListaDetalleVoucher())
/* 190:    */     {
/* 191:190 */       this.totalValorComision = this.totalValorComision.add(dfc.getValorComision());
/* 192:191 */       this.totalImpuestoComision = this.totalImpuestoComision.add(dfc.getImpuestoComision());
/* 193:192 */       this.totalValorPagadoCalculado = this.totalValorPagadoCalculado.add(dfc.getValorPagadoCalculado());
/* 194:193 */       this.totalValorPagado = this.totalValorPagado.add(dfc.getValorPagado());
/* 195:194 */       this.totalRetencionIva = this.totalRetencionIva.add(dfc.getRetencionIva());
/* 196:195 */       this.totalRetencionFuente = this.totalRetencionFuente.add(dfc.getRetencionFuente());
/* 197:    */     }
/* 198:198 */     if (getCobro() != null) {
/* 199:199 */       getCobro().setValor(this.totalValorPagado);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void cargarVouchersNoCobrados()
/* 204:    */   {
/* 205:208 */     setearTablas();
/* 206:    */     
/* 207:210 */     List<DetalleFormaCobro> listaVouchers = this.servicioRegistroVoucher.getVouchersNoCobrados(AppUtil.getOrganizacion().getId(), this.listaTarjetaCreditoSelected, this.listaPlanTarjetaSelected, this.listaLoteSelected);
/* 208:    */     
/* 209:    */ 
/* 210:213 */     Map<Integer, DetalleFormaCobro> hmVouchers = new HashMap();
/* 211:214 */     for (DetalleFormaCobro dfc : this.cobro.getListaDetalleFormaCobro())
/* 212:    */     {
/* 213:215 */       if (!dfc.isIndicadorCobrado()) {
/* 214:216 */         dfc.setEliminado(true);
/* 215:    */       }
/* 216:218 */       hmVouchers.put(Integer.valueOf(dfc.getId()), dfc);
/* 217:    */     }
/* 218:221 */     for (DetalleFormaCobro dfc : listaVouchers)
/* 219:    */     {
/* 220:222 */       DetalleFormaCobro dfcBusqueda = (DetalleFormaCobro)hmVouchers.get(Integer.valueOf(dfc.getId()));
/* 221:223 */       if (dfcBusqueda == null)
/* 222:    */       {
/* 223:224 */         getListaDetalleVoucher().add(dfc);
/* 224:225 */         getCobro().getListaDetalleFormaCobro().add(dfc);
/* 225:226 */         if (getListaDetalleVoucherFiler() != null) {
/* 226:227 */           getListaDetalleVoucherFiler().add(dfc);
/* 227:    */         }
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:230 */         dfcBusqueda.setEliminado(false);
/* 232:    */       }
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<TarjetaCredito> autocompletarTarjeta(String consulta)
/* 237:    */   {
/* 238:236 */     List<TarjetaCredito> lista = new ArrayList();
/* 239:237 */     consulta = consulta.toUpperCase();
/* 240:238 */     if (consulta.trim().isEmpty())
/* 241:    */     {
/* 242:239 */       lista.addAll(getListaTarjetaCredito());
/* 243:    */     }
/* 244:    */     else
/* 245:    */     {
/* 246:242 */       consulta = consulta.toUpperCase();
/* 247:243 */       for (TarjetaCredito tarjeta : getListaTarjetaCredito()) {
/* 248:244 */         if (tarjeta.getNombre().toUpperCase().contains(consulta)) {
/* 249:245 */           lista.add(tarjeta);
/* 250:    */         }
/* 251:    */       }
/* 252:    */     }
/* 253:250 */     return lista;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<PlanTarjetaCredito> autocompletarPlanTarjeta(String consulta)
/* 257:    */   {
/* 258:254 */     List<PlanTarjetaCredito> lista = new ArrayList();
/* 259:255 */     consulta = consulta.toUpperCase();
/* 260:256 */     if (consulta.trim().isEmpty())
/* 261:    */     {
/* 262:257 */       lista.addAll(getListaPlanTarjetaCredito());
/* 263:    */     }
/* 264:    */     else
/* 265:    */     {
/* 266:260 */       consulta = consulta.toUpperCase();
/* 267:261 */       for (PlanTarjetaCredito plan : getListaPlanTarjetaCredito()) {
/* 268:262 */         if (plan.getCodigo().toUpperCase().startsWith(consulta)) {
/* 269:263 */           lista.add(plan);
/* 270:    */         }
/* 271:    */       }
/* 272:    */     }
/* 273:268 */     return lista;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public List<String> autocompletarLoteTarjeta(String consulta)
/* 277:    */   {
/* 278:272 */     List<String> lista = new ArrayList();
/* 279:273 */     consulta = consulta.toUpperCase();
/* 280:274 */     if (consulta.trim().isEmpty())
/* 281:    */     {
/* 282:275 */       lista.addAll(getListaLote());
/* 283:    */     }
/* 284:    */     else
/* 285:    */     {
/* 286:278 */       consulta = consulta.toUpperCase();
/* 287:279 */       for (String lote : getListaLote()) {
/* 288:280 */         if (lote.toUpperCase().startsWith(consulta)) {
/* 289:281 */           lista.add(lote);
/* 290:    */         }
/* 291:    */       }
/* 292:    */     }
/* 293:286 */     return lista;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void cargarListaBancoAcreedor()
/* 297:    */   {
/* 298:    */     FormaPagoCuentaBancariaOrganizacion fp;
/* 299:290 */     if (this.cobro.getCuentaBancariaOrganizacion() != null)
/* 300:    */     {
/* 301:291 */       this.cobro.setCuentaBancariaOrganizacion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(this.cobro.getCuentaBancariaOrganizacion().getId()));
/* 302:292 */       this.listaFormaPago = new ArrayList();
/* 303:    */       Iterator localIterator;
/* 304:293 */       if (this.listaFormaPago != null) {
/* 305:294 */         for (localIterator = this.cobro.getCuentaBancariaOrganizacion().getListaFormaPago().iterator(); localIterator.hasNext();)
/* 306:    */         {
/* 307:294 */           fp = (FormaPagoCuentaBancariaOrganizacion)localIterator.next();
/* 308:295 */           this.listaFormaPago.add(fp);
/* 309:    */         }
/* 310:    */       }
/* 311:298 */       Object listaBA = this.cobro.getCuentaBancariaOrganizacion().getListaBancoAcreedor();
/* 312:299 */       this.listaBanco = new ArrayList();
/* 313:301 */       for (BancoAcreedor ba : (List)listaBA)
/* 314:    */       {
/* 315:302 */         this.listaBanco.add(ba.getBanco());
/* 316:303 */         if ((this.bancoAcreedor == null) && (ba.isPredeterminado())) {
/* 317:304 */           setBancoAcreedor(ba.getBanco());
/* 318:    */         }
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public BigDecimal getTotalValorComision()
/* 324:    */   {
/* 325:311 */     return this.totalValorComision;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setTotalValorComision(BigDecimal totalValorComision)
/* 329:    */   {
/* 330:315 */     this.totalValorComision = totalValorComision;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public BigDecimal getTotalImpuestoComision()
/* 334:    */   {
/* 335:319 */     return this.totalImpuestoComision;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void setTotalImpuestoComision(BigDecimal totalImpuestoComision)
/* 339:    */   {
/* 340:323 */     this.totalImpuestoComision = totalImpuestoComision;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public BigDecimal getTotalValorPagadoCalculado()
/* 344:    */   {
/* 345:327 */     return this.totalValorPagadoCalculado;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setTotalValorPagadoCalculado(BigDecimal totalValorPagadoCalculado)
/* 349:    */   {
/* 350:331 */     this.totalValorPagadoCalculado = totalValorPagadoCalculado;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public BigDecimal getTotalValorPagado()
/* 354:    */   {
/* 355:335 */     return this.totalValorPagado;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setTotalValorPagado(BigDecimal totalValorPagado)
/* 359:    */   {
/* 360:339 */     this.totalValorPagado = totalValorPagado;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public BigDecimal getTotalRetencionFuente()
/* 364:    */   {
/* 365:343 */     return this.totalRetencionFuente;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setTotalRetencionFuente(BigDecimal totalRetencionFuente)
/* 369:    */   {
/* 370:347 */     this.totalRetencionFuente = totalRetencionFuente;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public BigDecimal getTotalRetencionIva()
/* 374:    */   {
/* 375:351 */     return this.totalRetencionIva;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void setTotalRetencionIva(BigDecimal totalRetencionIva)
/* 379:    */   {
/* 380:355 */     this.totalRetencionIva = totalRetencionIva;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public TarjetaCredito getTarjetaCredito()
/* 384:    */   {
/* 385:359 */     return this.tarjetaCredito;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void setTarjetaCredito(TarjetaCredito tarjetaCredito)
/* 389:    */   {
/* 390:363 */     this.tarjetaCredito = tarjetaCredito;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public List<PlanTarjetaCredito> getListaPlanTarjetaSelected()
/* 394:    */   {
/* 395:367 */     return this.listaPlanTarjetaSelected;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void setListaPlanTarjetaSelected(List<PlanTarjetaCredito> listaPlanTarjetaSelected)
/* 399:    */   {
/* 400:371 */     this.listaPlanTarjetaSelected = listaPlanTarjetaSelected;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public Banco getBancoAcreedor()
/* 404:    */   {
/* 405:375 */     return this.bancoAcreedor;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setBancoAcreedor(Banco bancoAcreedor)
/* 409:    */   {
/* 410:379 */     this.bancoAcreedor = bancoAcreedor;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public List<Banco> getListaBanco()
/* 414:    */   {
/* 415:383 */     return this.listaBanco;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setListaBanco(List<Banco> listaBanco)
/* 419:    */   {
/* 420:387 */     this.listaBanco = listaBanco;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public List<String> getListaLote()
/* 424:    */   {
/* 425:391 */     if (this.listaLote == null) {
/* 426:392 */       this.listaLote = this.servicioRegistroVoucher.getLotesPendientesPorCobrar(AppUtil.getOrganizacion().getId());
/* 427:    */     }
/* 428:394 */     return this.listaLote;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setListaLote(List<String> listaLote)
/* 432:    */   {
/* 433:398 */     this.listaLote = listaLote;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public List<String> getListaLoteSelected()
/* 437:    */   {
/* 438:402 */     return this.listaLoteSelected;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setListaLoteSelected(List<String> listaLoteSelected)
/* 442:    */   {
/* 443:406 */     this.listaLoteSelected = listaLoteSelected;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public List<TarjetaCredito> getListaTarjetaCreditoSelected()
/* 447:    */   {
/* 448:410 */     return this.listaTarjetaCreditoSelected;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void setListaTarjetaCreditoSelected(List<TarjetaCredito> listaTarjetaCreditoSelected)
/* 452:    */   {
/* 453:414 */     this.listaTarjetaCreditoSelected = listaTarjetaCreditoSelected;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public List<FormaPagoCuentaBancariaOrganizacion> getListaFormaPago()
/* 457:    */   {
/* 458:418 */     if (this.listaFormaPago == null) {
/* 459:419 */       this.listaFormaPago = new ArrayList();
/* 460:    */     }
/* 461:422 */     return this.listaFormaPago;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setListaFormaPago(List<FormaPagoCuentaBancariaOrganizacion> listaFormaPago)
/* 465:    */   {
/* 466:426 */     this.listaFormaPago = listaFormaPago;
/* 467:    */   }
/* 468:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.PagoVoucherBean
 * JD-Core Version:    0.7.0.1
 */