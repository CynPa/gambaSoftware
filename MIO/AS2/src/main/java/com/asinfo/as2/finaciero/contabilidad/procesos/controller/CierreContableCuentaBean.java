/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   8:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  14:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import java.math.BigDecimal;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.ManagedProperty;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.component.datatable.DataTable;
/*  35:    */ import org.primefaces.event.SelectEvent;
/*  36:    */ import org.primefaces.model.LazyDataModel;
/*  37:    */ import org.primefaces.model.SortOrder;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class CierreContableCuentaBean
/*  42:    */   extends PageControllerAS2
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = -1910500360676267354L;
/*  45:    */   @EJB
/*  46:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  47:    */   @EJB
/*  48:    */   private ServicioCuentaContable servicioCuentaContable;
/*  49:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  50: 70 */   private BigDecimal debe = BigDecimal.ZERO;
/*  51: 71 */   private BigDecimal haber = BigDecimal.ZERO;
/*  52:    */   private InterfazContableProceso interfazContableProceso;
/*  53:    */   private List<CuentaContable> listaCuentaContable;
/*  54:    */   private CuentaContable cuentaContable;
/*  55:    */   private CuentaContable cuentaContableCierre;
/*  56:    */   private DataTable dtInterfazContableProceso;
/*  57:    */   private DataTable dtDetalleAsiento;
/*  58:    */   private DataTable dtCuentaContable;
/*  59:    */   private enumCuentaContableSeleccionada cuentaContableSeleccionada;
/*  60:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  61:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  62:    */   
/*  63:    */   private static enum enumCuentaContableSeleccionada
/*  64:    */   {
/*  65: 83 */     CUENTA_CONTABLE_CIERRE,  CUENTA_CONTABLE_DETALLE;
/*  66:    */     
/*  67:    */     private enumCuentaContableSeleccionada() {}
/*  68:    */   }
/*  69:    */   
/*  70:    */   @PostConstruct
/*  71:    */   public void init()
/*  72:    */   {
/*  73: 96 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  74:    */     {
/*  75:    */       private static final long serialVersionUID = 763093382591716471L;
/*  76:    */       
/*  77:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  78:    */       {
/*  79:103 */         List<InterfazContableProceso> lista = new ArrayList();
/*  80:    */         
/*  81:105 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  82:106 */         filters.put("documentoBase", String.valueOf(DocumentoBase.CIERRE_CUENTAS));
/*  83:107 */         lista = CierreContableCuentaBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  84:    */         
/*  85:109 */         CierreContableCuentaBean.this.listaInterfazContableProceso.setRowCount(CierreContableCuentaBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  86:110 */         return lista;
/*  87:    */       }
/*  88:    */     };
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String editar()
/*  92:    */   {
/*  93:122 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  94:123 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String guardar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:135 */       if (getInterfazContableProceso().getAsiento().getListaDetalleAsiento().isEmpty())
/* 102:    */       {
/* 103:137 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/* 104:    */       }
/* 105:140 */       else if (getCuentaContableCierre() != null)
/* 106:    */       {
/* 107:142 */         getInterfazContableProceso().getAsiento().setEstado(Estado.REVISADO);
/* 108:143 */         this.servicioInterfazContableProceso.guardar(getInterfazContableProceso());
/* 109:144 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 110:145 */         limpiar();
/* 111:146 */         setEditado(false);
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:148 */         addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_cuenta_contable"));
/* 116:    */       }
/* 117:    */     }
/* 118:    */     catch (ExcepcionAS2Financiero e)
/* 119:    */     {
/* 120:154 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 121:    */     }
/* 122:    */     catch (ExcepcionAS2 e)
/* 123:    */     {
/* 124:157 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 125:    */     }
/* 126:160 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String eliminar()
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:173 */       this.servicioInterfazContableProceso.anular(getInterfazContableProceso());
/* 134:174 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 135:    */     }
/* 136:    */     catch (ExcepcionAS2Financiero e)
/* 137:    */     {
/* 138:177 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 139:    */     }
/* 140:    */     catch (ExcepcionAS2 e)
/* 141:    */     {
/* 142:179 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 143:180 */       e.printStackTrace();
/* 144:    */     }
/* 145:183 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String limpiar()
/* 149:    */   {
/* 150:194 */     this.interfazContableProceso = new InterfazContableProceso();
/* 151:195 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 152:196 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 153:197 */     this.interfazContableProceso.setFechaDesde(new Date());
/* 154:198 */     this.interfazContableProceso.setFechaHasta(new Date());
/* 155:199 */     this.interfazContableProceso.setEstado(Estado.CONTABILIZADO);
/* 156:200 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.CIERRE_CUENTAS);
/* 157:201 */     this.interfazContableProceso.setFechaContabilizacion(new Date());
/* 158:    */     
/* 159:203 */     Asiento asiento = new Asiento();
/* 160:204 */     asiento.setSucursal(AppUtil.getSucursal());
/* 161:205 */     asiento.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 162:206 */     this.interfazContableProceso.setAsiento(asiento);
/* 163:    */     
/* 164:208 */     this.cuentaContableCierre = null;
/* 165:209 */     this.debe = BigDecimal.ZERO;
/* 166:210 */     this.haber = BigDecimal.ZERO;
/* 167:    */     
/* 168:212 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String cargarDatos()
/* 172:    */   {
/* 173:222 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   private void calcular()
/* 177:    */   {
/* 178:230 */     this.debe = BigDecimal.ZERO;
/* 179:231 */     this.haber = BigDecimal.ZERO;
/* 180:    */     
/* 181:233 */     String descripcion = "Cierre de Periodo Desde: " + FuncionesUtiles.dateToString(this.interfazContableProceso.getFechaDesde()) + " Hasta:" + FuncionesUtiles.dateToString(this.interfazContableProceso.getFechaHasta());
/* 182:235 */     for (DetalleAsiento d : getInterfazContableProceso().getAsiento().getListaDetalleAsiento())
/* 183:    */     {
/* 184:236 */       d.setDescripcion(descripcion);
/* 185:237 */       d.setAsiento(getInterfazContableProceso().getAsiento());
/* 186:238 */       this.haber = this.haber.add(d.getHaber());
/* 187:239 */       this.debe = this.debe.add(d.getDebe());
/* 188:    */     }
/* 189:241 */     BigDecimal valor = this.haber.subtract(this.debe);
/* 190:    */     
/* 191:243 */     DetalleAsiento d = new DetalleAsiento();
/* 192:244 */     d.setCuentaContable(getCuentaContableCierre());
/* 193:245 */     d.setAsiento(getInterfazContableProceso().getAsiento());
/* 194:246 */     d.setDescripcion(descripcion);
/* 195:248 */     if (valor.compareTo(BigDecimal.ZERO) != 0)
/* 196:    */     {
/* 197:249 */       if (valor.compareTo(BigDecimal.ZERO) > 0)
/* 198:    */       {
/* 199:250 */         d.setDebe(valor);
/* 200:251 */         d.setHaber(BigDecimal.ZERO);
/* 201:    */       }
/* 202:    */       else
/* 203:    */       {
/* 204:253 */         d.setDebe(BigDecimal.ZERO);
/* 205:254 */         d.setHaber(valor.negate());
/* 206:    */       }
/* 207:    */     }
/* 208:    */     else
/* 209:    */     {
/* 210:257 */       d.setDebe(BigDecimal.ZERO);
/* 211:258 */       d.setHaber(BigDecimal.ZERO);
/* 212:    */     }
/* 213:261 */     this.haber = this.haber.add(d.getHaber());
/* 214:262 */     this.debe = this.debe.add(d.getDebe());
/* 215:    */     
/* 216:264 */     getInterfazContableProceso().getAsiento().getListaDetalleAsiento().add(0, d);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String cargarCuentaContable(SelectEvent event)
/* 220:    */   {
/* 221:270 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 222:272 */     switch (2.$SwitchMap$com$asinfo$as2$finaciero$contabilidad$procesos$controller$CierreContableCuentaBean$enumCuentaContableSeleccionada[this.cuentaContableSeleccionada.ordinal()])
/* 223:    */     {
/* 224:    */     case 1: 
/* 225:275 */       this.cuentaContableCierre = cuentaContable;
/* 226:276 */       break;
/* 227:    */     case 2: 
/* 228:279 */       DetalleAsiento detalleAsiento = new DetalleAsiento();
/* 229:280 */       detalleAsiento.setAsiento(this.interfazContableProceso.getAsiento());
/* 230:281 */       BigDecimal saldo = this.servicioCuentaContable.obtenerSaldo(this.interfazContableProceso.getFechaDesde(), this.interfazContableProceso.getFechaHasta(), cuentaContable
/* 231:282 */         .getIdCuentaContable(), ValoresCalculo.DEBE_HABER, TipoCalculo.MOVIMIENTOS_MES, false, AppUtil.getSucursal()
/* 232:283 */         .getIdSucursal());
/* 233:285 */       if (saldo.compareTo(BigDecimal.ZERO) != 0)
/* 234:    */       {
/* 235:286 */         detalleAsiento.setCuentaContable(cuentaContable);
/* 236:287 */         if (saldo.compareTo(BigDecimal.ZERO) >= 0)
/* 237:    */         {
/* 238:288 */           detalleAsiento.setHaber(saldo);
/* 239:289 */           detalleAsiento.setDebe(BigDecimal.ZERO);
/* 240:    */         }
/* 241:    */         else
/* 242:    */         {
/* 243:291 */           detalleAsiento.setDebe(saldo.negate());
/* 244:292 */           detalleAsiento.setHaber(BigDecimal.ZERO);
/* 245:    */         }
/* 246:294 */         getInterfazContableProceso().getAsiento().getListaDetalleAsiento().add(detalleAsiento);
/* 247:    */       }
/* 248:    */       break;
/* 249:    */     }
/* 250:303 */     return "";
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void cerrarPeriodoActual()
/* 254:    */   {
/* 255:    */     try
/* 256:    */     {
/* 257:309 */       getInterfazContableProceso().setListaCuentaContable(new ArrayList());
/* 258:    */       
/* 259:311 */       List<CuentaContable> lista = new ArrayList();
/* 260:312 */       Map<String, String> fitros = new HashMap();
/* 261:313 */       fitros.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 262:315 */       if ((this.listaCuentaContable == null) || (this.listaCuentaContable.isEmpty()))
/* 263:    */       {
/* 264:317 */         fitros.put("codigo", "4.%");
/* 265:318 */         fitros.put("indicadorMovimiento", "true");
/* 266:319 */         lista = this.servicioCuentaContable.obtenerListaCombo("codigo", true, fitros);
/* 267:    */         
/* 268:321 */         fitros.put("codigo", "5.%");
/* 269:322 */         lista.addAll(this.servicioCuentaContable.obtenerListaCombo("codigo", true, fitros));
/* 270:    */         
/* 271:324 */         fitros.put("codigo", "6.%");
/* 272:325 */         lista.addAll(this.servicioCuentaContable.obtenerListaCombo("codigo", true, fitros));
/* 273:    */         
/* 274:327 */         fitros.put("codigo", "7.%");
/* 275:328 */         lista.addAll(this.servicioCuentaContable.obtenerListaCombo("codigo", true, fitros));
/* 276:    */         
/* 277:330 */         fitros.put("codigo", "8.%");
/* 278:331 */         lista.addAll(this.servicioCuentaContable.obtenerListaCombo("codigo", true, fitros));
/* 279:    */       }
/* 280:    */       else
/* 281:    */       {
/* 282:334 */         for (CuentaContable cuentaContable : this.listaCuentaContable)
/* 283:    */         {
/* 284:335 */           fitros.put("codigo", cuentaContable.getCodigo() + "%");
/* 285:336 */           lista.addAll(this.servicioCuentaContable.obtenerListaCombo("codigo", true, fitros));
/* 286:    */         }
/* 287:    */       }
/* 288:340 */       for (CuentaContable cuentaContable : lista) {
/* 289:341 */         getInterfazContableProceso().getListaCuentaContable().add(Integer.valueOf(cuentaContable.getIdCuentaContable()));
/* 290:    */       }
/* 291:344 */       getInterfazContableProceso().setAsiento(this.servicioInterfazContableProceso.generarAsiento(getInterfazContableProceso()));
/* 292:    */       
/* 293:346 */       calcular();
/* 294:    */     }
/* 295:    */     catch (ExcepcionAS2Financiero e)
/* 296:    */     {
/* 297:349 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 298:350 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 299:351 */       e.printStackTrace();
/* 300:    */     }
/* 301:    */     catch (ExcepcionAS2 e)
/* 302:    */     {
/* 303:353 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 304:354 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 305:355 */       e.printStackTrace();
/* 306:    */     }
/* 307:    */     catch (Exception e)
/* 308:    */     {
/* 309:357 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 310:358 */       e.printStackTrace();
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 315:    */   {
/* 316:366 */     List<DetalleAsiento> lista = new ArrayList();
/* 317:367 */     for (DetalleAsiento da : getInterfazContableProceso().getAsiento().getListaDetalleAsiento()) {
/* 318:368 */       if (!da.isEliminado()) {
/* 319:369 */         lista.add(da);
/* 320:    */       }
/* 321:    */     }
/* 322:372 */     return lista;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void actualizarCuentaContableCierre()
/* 326:    */   {
/* 327:376 */     this.cuentaContableSeleccionada = enumCuentaContableSeleccionada.CUENTA_CONTABLE_CIERRE;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void actualizarCuentaContableDetalle()
/* 331:    */   {
/* 332:380 */     this.cuentaContableSeleccionada = enumCuentaContableSeleccionada.CUENTA_CONTABLE_DETALLE;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public DataTable getDtDetalleAsiento()
/* 336:    */   {
/* 337:384 */     return this.dtDetalleAsiento;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setDtDetalleAsiento(DataTable dtDetalleAsiento)
/* 341:    */   {
/* 342:388 */     this.dtDetalleAsiento = dtDetalleAsiento;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public BigDecimal getDebe()
/* 346:    */   {
/* 347:392 */     return this.debe;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setDebe(BigDecimal debe)
/* 351:    */   {
/* 352:396 */     this.debe = debe;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public BigDecimal getHaber()
/* 356:    */   {
/* 357:400 */     return this.haber;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setHaber(BigDecimal haber)
/* 361:    */   {
/* 362:404 */     this.haber = haber;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public InterfazContableProceso getInterfazContableProceso()
/* 366:    */   {
/* 367:408 */     if (this.interfazContableProceso == null) {
/* 368:409 */       this.interfazContableProceso = new InterfazContableProceso();
/* 369:    */     }
/* 370:411 */     return this.interfazContableProceso;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 374:    */   {
/* 375:415 */     this.interfazContableProceso = interfazContableProceso;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 379:    */   {
/* 380:419 */     return this.listaInterfazContableProceso;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 384:    */   {
/* 385:423 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public DataTable getDtInterfazContableProceso()
/* 389:    */   {
/* 390:427 */     return this.dtInterfazContableProceso;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 394:    */   {
/* 395:431 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public List<CuentaContable> getListaCuentaContable()
/* 399:    */   {
/* 400:440 */     return this.listaCuentaContable;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setListaCuentaContable(List<CuentaContable> listaCuentaContable)
/* 404:    */   {
/* 405:450 */     this.listaCuentaContable = listaCuentaContable;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public DataTable getDtCuentaContable()
/* 409:    */   {
/* 410:459 */     return this.dtCuentaContable;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 414:    */   {
/* 415:469 */     this.dtCuentaContable = dtCuentaContable;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public CuentaContable getCuentaContable()
/* 419:    */   {
/* 420:478 */     return this.cuentaContable;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 424:    */   {
/* 425:488 */     this.cuentaContable = cuentaContable;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public CuentaContable getCuentaContableCierre()
/* 429:    */   {
/* 430:497 */     return this.cuentaContableCierre;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setCuentaContableCierre(CuentaContable cuentaContableCierre)
/* 434:    */   {
/* 435:507 */     this.cuentaContableCierre = cuentaContableCierre;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 439:    */   {
/* 440:511 */     return this.listaCuentaContableBean;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 444:    */   {
/* 445:515 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public enumCuentaContableSeleccionada getCuentaContableSeleccionada()
/* 449:    */   {
/* 450:519 */     return this.cuentaContableSeleccionada;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setCuentaContableSeleccionada(enumCuentaContableSeleccionada cuentaContableSeleccionada)
/* 454:    */   {
/* 455:523 */     this.cuentaContableSeleccionada = cuentaContableSeleccionada;
/* 456:    */   }
/* 457:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.CierreContableCuentaBean
 * JD-Core Version:    0.7.0.1
 */