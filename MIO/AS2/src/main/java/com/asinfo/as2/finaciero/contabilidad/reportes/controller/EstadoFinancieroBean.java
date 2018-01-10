/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.CentroCosto;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.EstadoFinanciero;
/*   9:    */ import com.asinfo.as2.entities.NivelCuenta;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*  14:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  17:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta;
/*  18:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioEstadoFinanciero;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ManagedProperty;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import javax.faces.model.SelectItem;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ import org.primefaces.component.datatable.DataTable;
/*  34:    */ import org.primefaces.event.SelectEvent;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class EstadoFinancieroBean
/*  39:    */   extends PageControllerAS2
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = -6739515644323228240L;
/*  42:    */   private EstadoFinanciero estadoFinanciero;
/*  43:    */   @EJB
/*  44:    */   private ServicioEstadoFinanciero servicioEstadoFinanciero;
/*  45:    */   @EJB
/*  46:    */   private ServicioCuentaContable servicioCuentaContable;
/*  47:    */   @EJB
/*  48:    */   private ServicioCentroCosto servicioCentroCosto;
/*  49:    */   @EJB
/*  50:    */   private ServicioNivelCuenta servicioNivelCuenta;
/*  51:    */   @EJB
/*  52:    */   private ServicioSucursal servicioSucursal;
/*  53: 76 */   private int mesDesde = FuncionesUtiles.obtenerMesActual();
/*  54: 77 */   private int anioDesde = FuncionesUtiles.obtenerAnioActual();
/*  55: 78 */   private int mesHasta = FuncionesUtiles.obtenerMesActual();
/*  56: 79 */   private int anioHasta = FuncionesUtiles.obtenerAnioActual();
/*  57:    */   private Sucursal sucursal;
/*  58:    */   private int nivel;
/*  59:    */   private int nivelPorDefecto;
/*  60:    */   private boolean indicadorBalanceGeneral;
/*  61: 85 */   private boolean comparativo = false;
/*  62: 86 */   private boolean acumulado = false;
/*  63:    */   private List<SelectItem> listaMes;
/*  64:    */   private List<NivelCuenta> listaNivelCuenta;
/*  65:    */   private List<SelectItem> listaTipoEstadoFinanciero;
/*  66:    */   private List<SelectItem> listaSucursal;
/*  67:    */   private List<NivelCuenta> listaNivelCuentaCombo;
/*  68:    */   private DataTable dtEstadoFinanciero;
/*  69:    */   private DataTable dtDetalleEstadoFinanciero;
/*  70: 97 */   private String valorDimension = "";
/*  71: 98 */   private String nombreDimension = "";
/*  72:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  73:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  74:    */   
/*  75:    */   public EstadoFinanciero getEstadoFinanciero()
/*  76:    */   {
/*  77:105 */     if (this.estadoFinanciero == null)
/*  78:    */     {
/*  79:106 */       this.estadoFinanciero = new EstadoFinanciero();
/*  80:107 */       this.estadoFinanciero.setTipoEstadoFinanciero(TipoEstadoFinanciero.BALANCE_GENERAL);
/*  81:    */     }
/*  82:109 */     return this.estadoFinanciero;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setEstadoFinanciero(EstadoFinanciero estadoFinanciero)
/*  86:    */   {
/*  87:113 */     this.estadoFinanciero = estadoFinanciero;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @PostConstruct
/*  91:    */   public void init()
/*  92:    */   {
/*  93:118 */     this.listaDimensionContableBean.setNumeroDimension("");
/*  94:119 */     getNivelPorDefecto();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String guardar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:124 */       this.estadoFinanciero.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 102:125 */       this.servicioEstadoFinanciero.guardar(this.estadoFinanciero);
/* 103:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 108:129 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 109:    */     }
/* 110:131 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String limpiar()
/* 114:    */   {
/* 115:135 */     this.estadoFinanciero = new EstadoFinanciero();
/* 116:136 */     this.estadoFinanciero.setTipoEstadoFinanciero(TipoEstadoFinanciero.BALANCE_GENERAL);
/* 117:137 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String procesar()
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:142 */       if (this.estadoFinanciero.getTipoEstadoFinanciero().equals(TipoEstadoFinanciero.BALANCE_GENERAL)) {
/* 125:143 */         setIndicadorBalanceGeneral(true);
/* 126:    */       } else {
/* 127:145 */         setIndicadorBalanceGeneral(false);
/* 128:    */       }
/* 129:147 */       if (!this.comparativo)
/* 130:    */       {
/* 131:148 */         if (this.estadoFinanciero.getTipoEstadoFinanciero().equals(TipoEstadoFinanciero.BALANCE_GENERAL))
/* 132:    */         {
/* 133:149 */           this.estadoFinanciero.setFechaDesde(FuncionesUtiles.obtenerFechaInicial());
/* 134:150 */           this.mesDesde = FuncionesUtiles.getMes(this.estadoFinanciero.getFechaDesde());
/* 135:151 */           this.anioDesde = FuncionesUtiles.getAnio(this.estadoFinanciero.getFechaDesde());
/* 136:    */         }
/* 137:    */         else
/* 138:    */         {
/* 139:153 */           this.estadoFinanciero.setFechaDesde(FuncionesUtiles.getFecha(1, this.mesDesde, this.anioDesde));
/* 140:    */         }
/* 141:155 */         this.estadoFinanciero.setFechaHasta(FuncionesUtiles.getFechaFinMes(this.anioHasta, this.mesHasta));
/* 142:156 */         this.estadoFinanciero.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 143:157 */         this.estadoFinanciero = this.servicioEstadoFinanciero.cargarDatos(this.estadoFinanciero, getListaDimensionContableBean().getNumeroDimension(), 
/* 144:158 */           getValorDimension(), this.sucursal.getIdSucursal(), this.nivel, this.acumulado);
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:160 */         this.estadoFinanciero = this.servicioEstadoFinanciero.cargarDatos(this.anioDesde, this.mesDesde, this.anioHasta, this.mesHasta, this.estadoFinanciero
/* 149:161 */           .getTipoEstadoFinanciero(), getListaDimensionContableBean().getNumeroDimension(), getValorDimension(), this.estadoFinanciero
/* 150:162 */           .isIndicadorNIIF(), this.sucursal.getIdSucursal(), this.nivel, this.acumulado, AppUtil.getOrganizacion().getId());
/* 151:    */       }
/* 152:    */     }
/* 153:    */     catch (ExcepcionAS2Financiero e)
/* 154:    */     {
/* 155:165 */       limpiar();
/* 156:166 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " - " + e.getMessage());
/* 157:167 */       LOG.error("ERROR AL PROCESAR", e);
/* 158:    */     }
/* 159:169 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void cargarDimensionContableListener(SelectEvent event)
/* 163:    */   {
/* 164:173 */     DimensionContable dimension = (DimensionContable)event.getObject();
/* 165:    */     try
/* 166:    */     {
/* 167:175 */       this.valorDimension = dimension.getCodigo();
/* 168:176 */       this.nombreDimension = dimension.getNombre();
/* 169:    */     }
/* 170:    */     catch (Exception e)
/* 171:    */     {
/* 172:178 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 173:179 */       LOG.error("ERROR AL CARGAR DIMENSION CONTABLE", e);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List<SelectItem> getListaMes()
/* 178:    */   {
/* 179:184 */     if (this.listaMes == null)
/* 180:    */     {
/* 181:185 */       this.listaMes = new ArrayList();
/* 182:186 */       for (Mes t : Mes.values())
/* 183:    */       {
/* 184:187 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 185:188 */         this.listaMes.add(item);
/* 186:    */       }
/* 187:    */     }
/* 188:191 */     return this.listaMes;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setListaMes(List<SelectItem> listaMes)
/* 192:    */   {
/* 193:195 */     this.listaMes = listaMes;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<CentroCosto> autocompletarCentroCosto(String consulta)
/* 197:    */   {
/* 198:206 */     consulta = consulta.toUpperCase();
/* 199:    */     
/* 200:208 */     String sortField = "codigo";
/* 201:209 */     HashMap<String, String> filters = new HashMap();
/* 202:210 */     filters.put("activo", "true");
/* 203:    */     
/* 204:212 */     List<CentroCosto> lista = new ArrayList();
/* 205:214 */     for (CentroCosto centroCosto : this.servicioCentroCosto.obtenerListaCombo(sortField, true, filters)) {
/* 206:216 */       if ((centroCosto.getCodigo().toUpperCase().contains(consulta)) || (centroCosto.getNombre().toUpperCase().contains(consulta))) {
/* 207:217 */         lista.add(centroCosto);
/* 208:    */       }
/* 209:    */     }
/* 210:221 */     return lista;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String editar()
/* 214:    */   {
/* 215:231 */     return null;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String eliminar()
/* 219:    */   {
/* 220:241 */     return null;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String cargarDatos()
/* 224:    */   {
/* 225:251 */     return null;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public ServicioEstadoFinanciero getServicioEstadoFinanciero()
/* 229:    */   {
/* 230:260 */     return this.servicioEstadoFinanciero;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setServicioEstadoFinanciero(ServicioEstadoFinanciero servicioEstadoFinanciero)
/* 234:    */   {
/* 235:270 */     this.servicioEstadoFinanciero = servicioEstadoFinanciero;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public ServicioCuentaContable getServicioCuentaContable()
/* 239:    */   {
/* 240:279 */     return this.servicioCuentaContable;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setServicioCuentaContable(ServicioCuentaContable servicioCuentaContable)
/* 244:    */   {
/* 245:289 */     this.servicioCuentaContable = servicioCuentaContable;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public int getMesDesde()
/* 249:    */   {
/* 250:298 */     return this.mesDesde;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setMesDesde(int mesDesde)
/* 254:    */   {
/* 255:308 */     this.mesDesde = mesDesde;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public int getAnioDesde()
/* 259:    */   {
/* 260:317 */     return this.anioDesde;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setAnioDesde(int anioDesde)
/* 264:    */   {
/* 265:327 */     this.anioDesde = anioDesde;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public int getMesHasta()
/* 269:    */   {
/* 270:336 */     return this.mesHasta;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setMesHasta(int mesHasta)
/* 274:    */   {
/* 275:346 */     this.mesHasta = mesHasta;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public int getAnioHasta()
/* 279:    */   {
/* 280:355 */     return this.anioHasta;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setAnioHasta(int anioHasta)
/* 284:    */   {
/* 285:365 */     this.anioHasta = anioHasta;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public boolean isComparativo()
/* 289:    */   {
/* 290:374 */     return this.comparativo;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setComparativo(boolean comparativo)
/* 294:    */   {
/* 295:384 */     this.comparativo = comparativo;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public DataTable getDtEstadoFinanciero()
/* 299:    */   {
/* 300:393 */     return this.dtEstadoFinanciero;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setDtEstadoFinanciero(DataTable dtEstadoFinanciero)
/* 304:    */   {
/* 305:403 */     this.dtEstadoFinanciero = dtEstadoFinanciero;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public DataTable getDtDetalleEstadoFinanciero()
/* 309:    */   {
/* 310:412 */     return this.dtDetalleEstadoFinanciero;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setDtDetalleEstadoFinanciero(DataTable dtDetalleEstadoFinanciero)
/* 314:    */   {
/* 315:422 */     this.dtDetalleEstadoFinanciero = dtDetalleEstadoFinanciero;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public List<NivelCuenta> getListaNivelCuenta()
/* 319:    */   {
/* 320:431 */     if (this.listaNivelCuenta == null)
/* 321:    */     {
/* 322:432 */       this.listaNivelCuenta = new ArrayList();
/* 323:433 */       for (NivelCuenta nc : getListaNivelCuentaCombo()) {
/* 324:435 */         this.listaNivelCuenta.add(nc);
/* 325:    */       }
/* 326:    */     }
/* 327:439 */     return this.listaNivelCuenta;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setListaNivelCuenta(List<NivelCuenta> listaNivelCuenta)
/* 331:    */   {
/* 332:449 */     this.listaNivelCuenta = listaNivelCuenta;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public List<SelectItem> getListaTipoEstadoFinanciero()
/* 336:    */   {
/* 337:459 */     if (this.listaTipoEstadoFinanciero == null)
/* 338:    */     {
/* 339:460 */       this.listaTipoEstadoFinanciero = new ArrayList();
/* 340:461 */       for (TipoEstadoFinanciero tipoEstadoFinanciero : TipoEstadoFinanciero.values()) {
/* 341:462 */         if (tipoEstadoFinanciero != TipoEstadoFinanciero.BALANCE_COMPROBACION)
/* 342:    */         {
/* 343:463 */           SelectItem se = new SelectItem();
/* 344:464 */           se.setValue(tipoEstadoFinanciero);
/* 345:465 */           se.setLabel(tipoEstadoFinanciero.getNombre());
/* 346:466 */           this.listaTipoEstadoFinanciero.add(se);
/* 347:    */         }
/* 348:    */       }
/* 349:    */     }
/* 350:470 */     return this.listaTipoEstadoFinanciero;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public Sucursal getSucursal()
/* 354:    */   {
/* 355:479 */     if (this.sucursal == null)
/* 356:    */     {
/* 357:480 */       this.sucursal = new Sucursal();
/* 358:481 */       this.sucursal.setIdSucursal(-1);
/* 359:    */     }
/* 360:483 */     return this.sucursal;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setSucursal(Sucursal sucursal)
/* 364:    */   {
/* 365:493 */     this.sucursal = sucursal;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public List<SelectItem> getListaSucursal()
/* 369:    */   {
/* 370:502 */     if (this.listaSucursal == null)
/* 371:    */     {
/* 372:503 */       this.listaSucursal = new ArrayList();
/* 373:504 */       String sortField = "codigo";
/* 374:505 */       Map<String, String> filters = new HashMap();
/* 375:506 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 376:508 */       for (Sucursal sucursal : this.servicioSucursal.obtenerListaCombo(sortField, true, filters))
/* 377:    */       {
/* 378:509 */         SelectItem se = new SelectItem();
/* 379:510 */         se.setValue(Integer.valueOf(sucursal.getIdSucursal()));
/* 380:511 */         se.setLabel(sucursal.getNombre());
/* 381:512 */         this.listaSucursal.add(se);
/* 382:    */       }
/* 383:    */     }
/* 384:515 */     return this.listaSucursal;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setListaSucursal(List<SelectItem> listaSucursal)
/* 388:    */   {
/* 389:525 */     this.listaSucursal = listaSucursal;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public boolean isIndicadorBalanceGeneral()
/* 393:    */   {
/* 394:534 */     return this.indicadorBalanceGeneral;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setIndicadorBalanceGeneral(boolean indicadorBalanceGeneral)
/* 398:    */   {
/* 399:544 */     this.indicadorBalanceGeneral = indicadorBalanceGeneral;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public List<NivelCuenta> getListaNivelCuentaCombo()
/* 403:    */   {
/* 404:553 */     if (this.listaNivelCuentaCombo == null)
/* 405:    */     {
/* 406:554 */       this.listaNivelCuentaCombo = new ArrayList();
/* 407:555 */       this.listaNivelCuentaCombo = this.servicioNivelCuenta.obtenerTodosOrdenadoDescendente();
/* 408:    */     }
/* 409:557 */     return this.listaNivelCuentaCombo;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setListaNivelCuentaCombo(List<NivelCuenta> listaNivelCuentaCombo)
/* 413:    */   {
/* 414:567 */     this.listaNivelCuentaCombo = listaNivelCuentaCombo;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public int getNivel()
/* 418:    */   {
/* 419:576 */     return this.nivel;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setNivel(int nivel)
/* 423:    */   {
/* 424:586 */     this.nivel = nivel;
/* 425:587 */     this.listaNivelCuenta = null;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public int getNivelPorDefecto()
/* 429:    */   {
/* 430:596 */     this.nivelPorDefecto = 0;
/* 431:597 */     if (getListaNivelCuentaCombo().isEmpty()) {
/* 432:598 */       addErrorMessage(getLanguageController().getMensaje("msg_error_nivel_cuenta_contable_no_existe"));
/* 433:    */     } else {
/* 434:600 */       for (NivelCuenta nivelCuenta : getListaNivelCuentaCombo()) {
/* 435:601 */         if (nivelCuenta.getCodigo() > this.nivelPorDefecto) {
/* 436:602 */           this.nivelPorDefecto = nivelCuenta.getCodigo();
/* 437:    */         }
/* 438:    */       }
/* 439:    */     }
/* 440:606 */     return this.nivelPorDefecto;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setNivelPorDefecto(int nivelPorDefecto)
/* 444:    */   {
/* 445:616 */     this.nivelPorDefecto = nivelPorDefecto;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean isAcumulado()
/* 449:    */   {
/* 450:625 */     return this.acumulado;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void setAcumulado(boolean acumulado)
/* 454:    */   {
/* 455:635 */     this.acumulado = acumulado;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 459:    */   {
/* 460:639 */     return this.listaDimensionContableBean;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 464:    */   {
/* 465:643 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public String getValorDimension()
/* 469:    */   {
/* 470:647 */     return this.valorDimension;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public void setValorDimension(String valorDimension)
/* 474:    */   {
/* 475:651 */     this.valorDimension = valorDimension;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public String getNombreDimension()
/* 479:    */   {
/* 480:660 */     return this.nombreDimension;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public void setNombreDimension(String nombreDimension)
/* 484:    */   {
/* 485:670 */     this.nombreDimension = nombreDimension;
/* 486:    */   }
/* 487:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.EstadoFinancieroBean
 * JD-Core Version:    0.7.0.1
 */