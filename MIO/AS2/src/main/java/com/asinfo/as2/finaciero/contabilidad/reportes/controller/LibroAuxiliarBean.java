/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.CentroCosto;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   9:    */ import com.asinfo.as2.entities.DimensionContable;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  14:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  15:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  16:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteLibroAuxiliar;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.util.AppUtil;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import java.math.BigDecimal;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import java.util.TreeMap;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ManagedProperty;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import javax.faces.model.SelectItem;
/*  36:    */ import org.apache.log4j.Logger;
/*  37:    */ import org.primefaces.component.datatable.DataTable;
/*  38:    */ import org.primefaces.event.SelectEvent;
/*  39:    */ 
/*  40:    */ @ManagedBean
/*  41:    */ @ViewScoped
/*  42:    */ public class LibroAuxiliarBean
/*  43:    */   extends PageController
/*  44:    */ {
/*  45:    */   private static final long serialVersionUID = 1L;
/*  46:    */   @EJB
/*  47:    */   private ServicioCuentaContable servicioCuentaContable;
/*  48:    */   @EJB
/*  49:    */   private ServicioReporteLibroAuxiliar servicioReporteLibroAuxiliar;
/*  50:    */   @EJB
/*  51:    */   private ServicioSucursal servicioSucursal;
/*  52:    */   @EJB
/*  53:    */   private ServicioCentroCosto servicioCentroCosto;
/*  54:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  55:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  56:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  57:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  58:    */   private int mesDesde;
/*  59:    */   private int anioDesde;
/*  60:    */   private int mesHasta;
/*  61:    */   private int anioHasta;
/*  62:    */   private Date fechaDesde;
/*  63:    */   private Date fechaHasta;
/*  64:    */   private Sucursal sucursal;
/*  65:    */   private boolean indicadorNIIF;
/*  66:    */   private List<Sucursal> listaSucursal;
/*  67: 93 */   private Map<Integer, CuentaContable> mapaCuentaContable = new TreeMap();
/*  68:    */   private CentroCosto centroCosto;
/*  69:    */   private List<SelectItem> listaMes;
/*  70:    */   private CuentaContable cuentaContable;
/*  71:    */   private CuentaContable cuentaContableLibroAuxiliar;
/*  72:    */   private int idCuentaContable;
/*  73:    */   private int idSucursal;
/*  74:    */   private int idCentroCosto;
/*  75:101 */   private List<CuentaContable> listaLibroAuxiliar = new ArrayList();
/*  76:102 */   private String valorDimension = "";
/*  77:    */   DimensionContable dimension;
/*  78:    */   private boolean previsualizar;
/*  79:    */   private DataTable dtCuentaContable;
/*  80:    */   
/*  81:    */   @PostConstruct
/*  82:    */   public void init()
/*  83:    */   {
/*  84:111 */     this.listaDimensionContableBean.setNumeroDimension("");
/*  85:112 */     this.mesDesde = FuncionesUtiles.obtenerMesActual();
/*  86:113 */     this.anioDesde = FuncionesUtiles.obtenerAnioActual();
/*  87:114 */     this.mesHasta = FuncionesUtiles.obtenerMesActual();
/*  88:115 */     this.anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void procesarDesdeBalances()
/*  92:    */   {
/*  93:120 */     if ((this.cuentaContableLibroAuxiliar != null) && (this.cuentaContableLibroAuxiliar.isIndicadorMovimiento())) {
/*  94:121 */       procesar();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void procesar()
/*  99:    */   {
/* 100:126 */     if (getListCuentaContableLibroAuxiliar().size() > 0)
/* 101:    */     {
/* 102:127 */       this.listaLibroAuxiliar = new ArrayList();
/* 103:128 */       for (CuentaContable cc : getListCuentaContableLibroAuxiliar())
/* 104:    */       {
/* 105:130 */         String etiquetaSaldoInicial = getLanguageController().getMensaje("msg_saldo_inicial");
/* 106:131 */         this.fechaDesde = FuncionesUtiles.getFecha(1, this.mesDesde, this.anioDesde);
/* 107:132 */         this.fechaHasta = FuncionesUtiles.getFechaFinMes(this.anioHasta, this.mesHasta);
/* 108:133 */         boolean indicadorListaCuenta = false;
/* 109:135 */         if (cc.isIndicadorMovimiento())
/* 110:    */         {
/* 111:136 */           this.listaLibroAuxiliar.add(cc);
/* 112:    */         }
/* 113:    */         else
/* 114:    */         {
/* 115:138 */           Map<String, String> filters = new HashMap();
/* 116:139 */           filters.put("codigo", cc.getCodigo() + "%");
/* 117:140 */           filters.put("indicadorMovimiento", "true");
/* 118:141 */           filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 119:142 */           this.listaLibroAuxiliar = this.servicioCuentaContable.obtenerListaCombo("codigo", true, filters);
/* 120:143 */           indicadorListaCuenta = true;
/* 121:    */         }
/* 122:    */         try
/* 123:    */         {
/* 124:147 */           List<Object[]> saldos = this.servicioCuentaContable.obtenerValores(this.fechaDesde, this.fechaHasta, AppUtil.getOrganizacion()
/* 125:148 */             .getIdOrganizacion(), this.listaLibroAuxiliar, ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_INICIAL, this.indicadorNIIF, this.idSucursal, 
/* 126:149 */             getListaDimensionContableBean().getNumeroDimension(), getValorDimension());
/* 127:    */           
/* 128:151 */           mapaSaldos = new HashMap();
/* 129:152 */           for (Object[] saldo : saldos) {
/* 130:153 */             mapaSaldos.put(new Integer(saldo[0].toString()), new BigDecimal(saldo[1].toString()));
/* 131:    */           }
/* 132:155 */           Object lista = this.servicioReporteLibroAuxiliar.getReporteLibroAuxiliar(this.listaLibroAuxiliar, this.fechaDesde, this.fechaHasta, this.indicadorNIIF, 
/* 133:156 */             getSucursal().getIdSucursal(), getListaDimensionContableBean().getNumeroDimension(), getValorDimension(), 
/* 134:157 */             AppUtil.getOrganizacion().getIdOrganizacion());
/* 135:158 */           mapaDetalleAsiento = new HashMap();
/* 136:159 */           for (DetalleAsiento detalleAsiento : (List)lista)
/* 137:    */           {
/* 138:160 */             List<DetalleAsiento> listaTmp = (List)mapaDetalleAsiento.get(Integer.valueOf(detalleAsiento.getCuentaContable().getIdCuentaContable()));
/* 139:161 */             if (listaTmp == null)
/* 140:    */             {
/* 141:162 */               listaTmp = new ArrayList();
/* 142:163 */               mapaDetalleAsiento.put(Integer.valueOf(detalleAsiento.getCuentaContable().getIdCuentaContable()), listaTmp);
/* 143:    */             }
/* 144:165 */             listaTmp.add(detalleAsiento);
/* 145:    */           }
/* 146:167 */           for (CuentaContable cuentaContableList : this.listaLibroAuxiliar)
/* 147:    */           {
/* 148:169 */             BigDecimal saldoCuentaContable = (BigDecimal)mapaSaldos.get(Integer.valueOf(cuentaContableList.getIdCuentaContable()));
/* 149:170 */             saldoCuentaContable = saldoCuentaContable == null ? BigDecimal.ZERO : saldoCuentaContable;
/* 150:171 */             cuentaContableList.setTraSaldoInicial(saldoCuentaContable == null ? BigDecimal.ZERO : saldoCuentaContable);
/* 151:172 */             List<DetalleAsiento> listaTmp = (List)mapaDetalleAsiento.get(Integer.valueOf(cuentaContableList.getIdCuentaContable()));
/* 152:173 */             cuentaContableList.setListaDetalleAsiento(listaTmp == null ? new ArrayList() : listaTmp);
/* 153:    */             
/* 154:    */ 
/* 155:176 */             DetalleAsiento d = new DetalleAsiento(saldoCuentaContable.compareTo(BigDecimal.ZERO) > 0 ? saldoCuentaContable : BigDecimal.ZERO, saldoCuentaContable.compareTo(BigDecimal.ZERO) < 0 ? saldoCuentaContable.negate() : BigDecimal.ZERO, etiquetaSaldoInicial, etiquetaSaldoInicial, "", this.fechaDesde, "");
/* 156:    */             
/* 157:    */ 
/* 158:179 */             cuentaContableList.getListaDetalleAsiento().add(0, d);
/* 159:    */             
/* 160:    */ 
/* 161:182 */             detalleSaldoInicial = null;
/* 162:183 */             for (DetalleAsiento detalleAsiento : cuentaContableList.getListaDetalleAsiento())
/* 163:    */             {
/* 164:184 */               detalleAsiento.setSaldo(detalleAsiento.getDebe().subtract(detalleAsiento.getHaber()));
/* 165:186 */               if (detalleSaldoInicial != null) {
/* 166:187 */                 detalleAsiento.setSaldo(detalleAsiento.getSaldo().add(detalleSaldoInicial.getSaldo()));
/* 167:    */               }
/* 168:190 */               detalleSaldoInicial = detalleAsiento;
/* 169:    */             }
/* 170:    */           }
/* 171:    */         }
/* 172:    */         catch (ExcepcionAS2Financiero e)
/* 173:    */         {
/* 174:    */           Map<Integer, BigDecimal> mapaSaldos;
/* 175:    */           Map<Integer, List<DetalleAsiento>> mapaDetalleAsiento;
/* 176:    */           DetalleAsiento detalleSaldoInicial;
/* 177:197 */           addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 178:198 */           LOG.error("ERROR AL PROCESAR", e);
/* 179:    */         }
/* 180:    */       }
/* 181:201 */       calcular();
/* 182:    */     }
/* 183:    */     else
/* 184:    */     {
/* 185:203 */       this.listaLibroAuxiliar.clear();
/* 186:204 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void cargarDimensionContableListener(SelectEvent event)
/* 191:    */   {
/* 192:211 */     this.dimension = ((DimensionContable)event.getObject());
/* 193:    */     try
/* 194:    */     {
/* 195:213 */       this.valorDimension = this.dimension.getCodigo();
/* 196:    */     }
/* 197:    */     catch (Exception e)
/* 198:    */     {
/* 199:215 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 200:216 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<SelectItem> getListaMes()
/* 205:    */   {
/* 206:221 */     if (this.listaMes == null)
/* 207:    */     {
/* 208:222 */       this.listaMes = new ArrayList();
/* 209:223 */       for (Mes t : Mes.values())
/* 210:    */       {
/* 211:224 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 212:225 */         this.listaMes.add(item);
/* 213:    */       }
/* 214:    */     }
/* 215:228 */     return this.listaMes;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getMesDesde()
/* 219:    */   {
/* 220:232 */     return this.mesDesde;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setMesDesde(int mesDesde)
/* 224:    */   {
/* 225:236 */     this.mesDesde = mesDesde;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getAnioDesde()
/* 229:    */   {
/* 230:240 */     return this.anioDesde;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setAnioDesde(int anioDesde)
/* 234:    */   {
/* 235:244 */     this.anioDesde = anioDesde;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public int getMesHasta()
/* 239:    */   {
/* 240:248 */     return this.mesHasta;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setMesHasta(int mesHasta)
/* 244:    */   {
/* 245:252 */     this.mesHasta = mesHasta;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public int getAnioHasta()
/* 249:    */   {
/* 250:256 */     return this.anioHasta;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setAnioHasta(int anioHasta)
/* 254:    */   {
/* 255:260 */     this.anioHasta = anioHasta;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public DataTable getDtCuentaContable()
/* 259:    */   {
/* 260:264 */     return this.dtCuentaContable;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 264:    */   {
/* 265:268 */     this.dtCuentaContable = dtCuentaContable;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public CuentaContable getCuentaContable()
/* 269:    */   {
/* 270:272 */     return this.cuentaContable;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 274:    */   {
/* 275:276 */     this.cuentaContable = cuentaContable;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void cargarCuentaContable(SelectEvent event)
/* 279:    */   {
/* 280:281 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 281:282 */     this.cuentaContableLibroAuxiliar = this.cuentaContable;
/* 282:283 */     this.mapaCuentaContable.put(Integer.valueOf(this.cuentaContableLibroAuxiliar.getIdCuentaContable()), this.cuentaContableLibroAuxiliar);
/* 283:    */   }
/* 284:    */   
/* 285:    */   private void calcular()
/* 286:    */   {
/* 287:287 */     for (CuentaContable c : this.listaLibroAuxiliar)
/* 288:    */     {
/* 289:288 */       c.setTraSaldoFinal(BigDecimal.ZERO);
/* 290:289 */       c.setTraDebe(BigDecimal.ZERO);
/* 291:290 */       c.setTraHaber(BigDecimal.ZERO);
/* 292:291 */       for (DetalleAsiento d : c.getListaDetalleAsiento())
/* 293:    */       {
/* 294:292 */         c.setTraDebe(c.getTraDebe().add(d.getDebe()));
/* 295:293 */         c.setTraHaber(c.getTraHaber().add(d.getHaber()));
/* 296:    */       }
/* 297:295 */       c.setTraSaldoFinal(c.getTraSaldoFinal().add(c.getTraDebe().subtract(c.getTraHaber())));
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void eliminarCuenta(CuentaContable cuentaContable)
/* 302:    */   {
/* 303:300 */     this.mapaCuentaContable.remove(Integer.valueOf(cuentaContable.getIdCuentaContable()));
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Date getFechaDesde()
/* 307:    */   {
/* 308:309 */     return this.fechaDesde;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setFechaDesde(Date fechaDesde)
/* 312:    */   {
/* 313:319 */     this.fechaDesde = fechaDesde;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Date getFechaHasta()
/* 317:    */   {
/* 318:328 */     return this.fechaHasta;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setFechaHasta(Date fechaHasta)
/* 322:    */   {
/* 323:338 */     this.fechaHasta = fechaHasta;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public int getIdCuentaContable()
/* 327:    */   {
/* 328:347 */     return this.idCuentaContable;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setIdCuentaContable(int idCuentaContable)
/* 332:    */   {
/* 333:357 */     this.idCuentaContable = idCuentaContable;
/* 334:    */     
/* 335:359 */     this.cuentaContableLibroAuxiliar = this.servicioCuentaContable.buscarPorId(Integer.valueOf(idCuentaContable));
/* 336:360 */     if (this.cuentaContableLibroAuxiliar != null) {
/* 337:361 */       this.mapaCuentaContable.put(Integer.valueOf(this.cuentaContableLibroAuxiliar.getIdCuentaContable()), this.cuentaContableLibroAuxiliar);
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   public Sucursal getSucursal()
/* 342:    */   {
/* 343:370 */     if (this.sucursal == null)
/* 344:    */     {
/* 345:371 */       this.sucursal = new Sucursal();
/* 346:372 */       this.sucursal.setIdSucursal(-1);
/* 347:    */     }
/* 348:374 */     return this.sucursal;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void setSucursal(Sucursal sucursal)
/* 352:    */   {
/* 353:384 */     this.sucursal = sucursal;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public List<Sucursal> getListaSucursal()
/* 357:    */   {
/* 358:393 */     if (this.listaSucursal == null) {
/* 359:394 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("codigo", true, null);
/* 360:    */     }
/* 361:396 */     return this.listaSucursal;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public int getIdSucursal()
/* 365:    */   {
/* 366:405 */     return this.idSucursal;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public CentroCosto getCentroCosto()
/* 370:    */   {
/* 371:412 */     return this.centroCosto;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void setCentroCosto(CentroCosto centroCosto)
/* 375:    */   {
/* 376:420 */     this.centroCosto = centroCosto;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean isIndicadorNIIF()
/* 380:    */   {
/* 381:429 */     return this.indicadorNIIF;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setIndicadorNIIF(boolean indicadorNIIF)
/* 385:    */   {
/* 386:439 */     this.indicadorNIIF = indicadorNIIF;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setIdSucursal(int idSucursal)
/* 390:    */   {
/* 391:450 */     if (idSucursal == -1)
/* 392:    */     {
/* 393:451 */       this.sucursal = new Sucursal();
/* 394:452 */       this.sucursal.setIdSucursal(idSucursal);
/* 395:    */     }
/* 396:    */     else
/* 397:    */     {
/* 398:454 */       this.sucursal = this.servicioSucursal.buscarPorId(Integer.valueOf(idSucursal));
/* 399:    */     }
/* 400:456 */     this.idSucursal = idSucursal;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public CuentaContable getCuentaContableLibroAuxiliar()
/* 404:    */   {
/* 405:465 */     return this.cuentaContableLibroAuxiliar;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void setCuentaContableLibroAuxiliar(CuentaContable cuentaContableLibroAuxiliar)
/* 409:    */   {
/* 410:475 */     this.cuentaContableLibroAuxiliar = cuentaContableLibroAuxiliar;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public List<CuentaContable> getListaLibroAuxiliar()
/* 414:    */   {
/* 415:484 */     return this.listaLibroAuxiliar;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void setListaLibroAuxiliar(List<CuentaContable> listaLibroAuxiliar)
/* 419:    */   {
/* 420:494 */     this.listaLibroAuxiliar = listaLibroAuxiliar;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setIdCentroCosto(int idCentroCosto)
/* 424:    */   {
/* 425:505 */     if (idCentroCosto == -1)
/* 426:    */     {
/* 427:506 */       this.centroCosto = new CentroCosto();
/* 428:507 */       this.centroCosto.setIdCentroCosto(idCentroCosto);
/* 429:    */     }
/* 430:    */     else
/* 431:    */     {
/* 432:509 */       this.centroCosto = this.servicioCentroCosto.buscarPorId(idCentroCosto);
/* 433:    */     }
/* 434:511 */     this.idCentroCosto = idCentroCosto;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public int getIdCentroCosto()
/* 438:    */   {
/* 439:520 */     return this.idCentroCosto;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 443:    */   {
/* 444:529 */     return this.listaDimensionContableBean;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 448:    */   {
/* 449:539 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public String getValorDimension()
/* 453:    */   {
/* 454:548 */     return this.valorDimension;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void setValorDimension(String valorDimension)
/* 458:    */   {
/* 459:558 */     this.valorDimension = valorDimension;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 463:    */   {
/* 464:562 */     return this.listaCuentaContableBean;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 468:    */   {
/* 469:566 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public DimensionContable getDimension()
/* 473:    */   {
/* 474:570 */     return this.dimension;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void setDimension(DimensionContable dimension)
/* 478:    */   {
/* 479:574 */     this.dimension = dimension;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public List<CuentaContable> getListCuentaContableLibroAuxiliar()
/* 483:    */   {
/* 484:578 */     return new ArrayList(this.mapaCuentaContable.values());
/* 485:    */   }
/* 486:    */   
/* 487:    */   public boolean isPrevisualizar()
/* 488:    */   {
/* 489:582 */     return this.previsualizar;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setPrevisualizar(boolean previsualizar)
/* 493:    */   {
/* 494:586 */     this.previsualizar = previsualizar;
/* 495:    */   }
/* 496:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.LibroAuxiliarBean
 * JD-Core Version:    0.7.0.1
 */