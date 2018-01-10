/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioComponenteCosto;
/*   6:    */ import com.asinfo.as2.entities.ComponenteCosto;
/*   7:    */ import com.asinfo.as2.entities.CuentaContable;
/*   8:    */ import com.asinfo.as2.entities.DetalleComponenteCosto;
/*   9:    */ import com.asinfo.as2.entities.DetalleComponenteCostoDistribucion;
/*  10:    */ import com.asinfo.as2.entities.DimensionContable;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  14:    */ import com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum;
/*  15:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  18:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaDimensionContableBean;
/*  19:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  20:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  21:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  22:    */ import com.asinfo.as2.util.AppUtil;
/*  23:    */ import com.asinfo.as2.utils.JsfUtil;
/*  24:    */ import java.util.ArrayList;
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
/*  41:    */ public class ComponenteCostoBean
/*  42:    */   extends PageControllerAS2
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  45:    */   @EJB
/*  46:    */   private ServicioComponenteCosto servicioComponenteCosto;
/*  47:    */   @EJB
/*  48:    */   private ServicioCuentaContable servicioCuentaContable;
/*  49:    */   @EJB
/*  50:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  51:    */   private ComponenteCosto componenteCosto;
/*  52:    */   private LazyDataModel<ComponenteCosto> listaComponenteCosto;
/*  53:    */   private List<TipoComponenteCostoEnum> listaTipoComponenteCosto;
/*  54:    */   private List<ValoresCalculo> listaValoresCalculo;
/*  55:    */   private List<DetalleComponenteCosto> listaDetalleComponenteCosto;
/*  56:    */   private List<RutaFabricacion> listaRutaFabricacion;
/*  57:    */   private DataTable dtComponenteCosto;
/*  58:    */   private DataTable dtDetalleComponenteCosto;
/*  59:    */   private DataTable dtDetalleComponenteCostoDistribucion;
/*  60:    */   private DetalleComponenteCosto detalleComponenteCosto;
/*  61:    */   private DetalleComponenteCostoDistribucion detalleComponenteCostoDistribucion;
/*  62:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  63:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  64:    */   @ManagedProperty("#{listaDimensionContableBean}")
/*  65:    */   private ListaDimensionContableBean listaDimensionContableBean;
/*  66:    */   
/*  67:    */   @PostConstruct
/*  68:    */   public void init()
/*  69:    */   {
/*  70:111 */     this.listaComponenteCosto = new LazyDataModel()
/*  71:    */     {
/*  72:    */       private static final long serialVersionUID = 1L;
/*  73:    */       
/*  74:    */       public List<ComponenteCosto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  75:    */       {
/*  76:118 */         List<ComponenteCosto> lista = new ArrayList();
/*  77:119 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  78:    */         
/*  79:121 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  80:122 */         lista = ComponenteCostoBean.this.servicioComponenteCosto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  81:    */         
/*  82:124 */         ComponenteCostoBean.this.listaComponenteCosto.setRowCount(ComponenteCostoBean.this.servicioComponenteCosto.contarPorCriterio(filters));
/*  83:    */         
/*  84:126 */         return lista;
/*  85:    */       }
/*  86:    */     };
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void crearEntidad()
/*  90:    */   {
/*  91:144 */     this.componenteCosto = new ComponenteCosto();
/*  92:145 */     this.componenteCosto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  93:146 */     this.componenteCosto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  94:147 */     this.componenteCosto.setActivo(true);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String editar()
/*  98:    */   {
/*  99:156 */     if ((this.componenteCosto != null) && (this.componenteCosto.getId() != 0))
/* 100:    */     {
/* 101:157 */       this.componenteCosto = this.servicioComponenteCosto.cargarDetalle(this.componenteCosto.getId());
/* 102:158 */       for (DetalleComponenteCosto detalle : this.componenteCosto.getListaDetalleComponenteCosto()) {
/* 103:159 */         if (detalle.getCuentaContableCierre() == null)
/* 104:    */         {
/* 105:160 */           detalle.setCuentaContableCierre(new CuentaContable());
/* 106:161 */           detalle.getCuentaContableCierre().setCodigo("  ");
/* 107:    */         }
/* 108:    */       }
/* 109:164 */       setEditado(true);
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:166 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 114:    */     }
/* 115:168 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String guardar()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:179 */       for (DetalleComponenteCostoDistribucion detalle : this.componenteCosto.getListaDetalleComponenteCostoDistribucion())
/* 123:    */       {
/* 124:180 */         if (!isIndicadorValidarDimension1()) {
/* 125:181 */           detalle.setDimensionContable1(null);
/* 126:    */         }
/* 127:183 */         if (!isIndicadorValidarDimension2()) {
/* 128:184 */           detalle.setDimensionContable2(null);
/* 129:    */         }
/* 130:186 */         if (!isIndicadorValidarDimension3()) {
/* 131:187 */           detalle.setDimensionContable3(null);
/* 132:    */         }
/* 133:189 */         if (!isIndicadorValidarDimension4()) {
/* 134:190 */           detalle.setDimensionContable4(null);
/* 135:    */         }
/* 136:192 */         if (!isIndicadorValidarDimension5()) {
/* 137:193 */           detalle.setDimensionContable5(null);
/* 138:    */         }
/* 139:    */       }
/* 140:196 */       this.servicioComponenteCosto.guardar(this.componenteCosto);
/* 141:197 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 142:198 */       limpiar();
/* 143:199 */       setEditado(false);
/* 144:    */     }
/* 145:    */     catch (AS2Exception e)
/* 146:    */     {
/* 147:201 */       JsfUtil.addErrorMessage(e, "");
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:203 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 152:204 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 153:    */     }
/* 154:206 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String eliminar()
/* 158:    */   {
/* 159:215 */     if ((this.componenteCosto != null) && (this.componenteCosto.getId() != 0)) {
/* 160:    */       try
/* 161:    */       {
/* 162:217 */         this.servicioComponenteCosto.eliminar(this.componenteCosto);
/* 163:218 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 164:    */       }
/* 165:    */       catch (Exception e)
/* 166:    */       {
/* 167:220 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 168:221 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 169:    */       }
/* 170:    */     } else {
/* 171:224 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 172:    */     }
/* 173:226 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String cargarDatos()
/* 177:    */   {
/* 178:235 */     return "";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String limpiar()
/* 182:    */   {
/* 183:244 */     crearEntidad();
/* 184:245 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   public ComponenteCosto getComponenteCosto()
/* 188:    */   {
/* 189:258 */     return this.componenteCosto;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setComponenteCosto(ComponenteCosto componenteCosto)
/* 193:    */   {
/* 194:268 */     this.componenteCosto = componenteCosto;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public LazyDataModel<ComponenteCosto> getListaComponenteCosto()
/* 198:    */   {
/* 199:277 */     return this.listaComponenteCosto;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setListaComponenteCosto(LazyDataModel<ComponenteCosto> listaComponenteCosto)
/* 203:    */   {
/* 204:287 */     this.listaComponenteCosto = listaComponenteCosto;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public DataTable getDtComponenteCosto()
/* 208:    */   {
/* 209:296 */     return this.dtComponenteCosto;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setDtComponenteCosto(DataTable dtComponenteCosto)
/* 213:    */   {
/* 214:306 */     this.dtComponenteCosto = dtComponenteCosto;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public List<TipoComponenteCostoEnum> getListaTipoComponenteCosto()
/* 218:    */   {
/* 219:316 */     if (this.listaTipoComponenteCosto == null)
/* 220:    */     {
/* 221:317 */       this.listaTipoComponenteCosto = new ArrayList();
/* 222:318 */       for (TipoComponenteCostoEnum tipoComponenteCostoEnum : TipoComponenteCostoEnum.values()) {
/* 223:319 */         this.listaTipoComponenteCosto.add(tipoComponenteCostoEnum);
/* 224:    */       }
/* 225:    */     }
/* 226:322 */     return this.listaTipoComponenteCosto;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void actualizarTipoComponente()
/* 230:    */   {
/* 231:326 */     if (this.componenteCosto.getTipoComponenteCostoEnum().equals(TipoComponenteCostoEnum.MANO_OBRA))
/* 232:    */     {
/* 233:327 */       this.componenteCosto.setIndicadorProrratearHorasHombre(Boolean.valueOf(false));
/* 234:328 */       this.componenteCosto.setIndicadorProrratearHorasHombreXValor(Boolean.valueOf(true));
/* 235:329 */       this.componenteCosto.setIndicadorProrratearHorasMaquina(Boolean.valueOf(false));
/* 236:330 */       this.componenteCosto.setIndicadorProrratearHorasMaquinaXValor(Boolean.valueOf(false));
/* 237:    */     }
/* 238:332 */     if (this.componenteCosto.getTipoComponenteCostoEnum().equals(TipoComponenteCostoEnum.DEPRECIACION))
/* 239:    */     {
/* 240:333 */       this.componenteCosto.setIndicadorProrratearHorasHombre(Boolean.valueOf(false));
/* 241:334 */       this.componenteCosto.setIndicadorProrratearHorasHombreXValor(Boolean.valueOf(false));
/* 242:335 */       this.componenteCosto.setIndicadorProrratearHorasMaquina(Boolean.valueOf(false));
/* 243:336 */       this.componenteCosto.setIndicadorProrratearHorasMaquinaXValor(Boolean.valueOf(true));
/* 244:    */     }
/* 245:338 */     if (this.componenteCosto.getTipoComponenteCostoEnum().equals(TipoComponenteCostoEnum.INDIRECTO))
/* 246:    */     {
/* 247:339 */       this.componenteCosto.setIndicadorProrratearHorasHombre(Boolean.valueOf(true));
/* 248:340 */       this.componenteCosto.setIndicadorProrratearHorasHombreXValor(Boolean.valueOf(false));
/* 249:341 */       this.componenteCosto.setIndicadorProrratearHorasMaquina(Boolean.valueOf(true));
/* 250:342 */       this.componenteCosto.setIndicadorProrratearHorasMaquinaXValor(Boolean.valueOf(false));
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void actualizarIndicadorHorasHombre()
/* 255:    */   {
/* 256:347 */     this.componenteCosto.setIndicadorCoeficienteProduccion(Boolean.valueOf(false));
/* 257:348 */     if (this.componenteCosto.getIndicadorProrratearHorasHombre().booleanValue()) {
/* 258:349 */       this.componenteCosto.setIndicadorProrratearHorasHombreXValor(Boolean.valueOf(false));
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void actualizarIndicadorHorasHombreXValor()
/* 263:    */   {
/* 264:354 */     this.componenteCosto.setIndicadorCoeficienteProduccion(Boolean.valueOf(false));
/* 265:355 */     if (this.componenteCosto.getIndicadorProrratearHorasHombreXValor().booleanValue()) {
/* 266:356 */       this.componenteCosto.setIndicadorProrratearHorasHombre(Boolean.valueOf(false));
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void actualizarIndicadorHorasMaquina()
/* 271:    */   {
/* 272:361 */     this.componenteCosto.setIndicadorCoeficienteProduccion(Boolean.valueOf(false));
/* 273:362 */     if (this.componenteCosto.getIndicadorProrratearHorasMaquina().booleanValue()) {
/* 274:363 */       this.componenteCosto.setIndicadorProrratearHorasMaquinaXValor(Boolean.valueOf(false));
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void actualizarIndicadorHorasMaquinaXValor()
/* 279:    */   {
/* 280:368 */     this.componenteCosto.setIndicadorCoeficienteProduccion(Boolean.valueOf(false));
/* 281:369 */     if (this.componenteCosto.getIndicadorProrratearHorasMaquinaXValor().booleanValue()) {
/* 282:370 */       this.componenteCosto.setIndicadorProrratearHorasMaquina(Boolean.valueOf(false));
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void actualizarIndicadorCoeficienteProduccion()
/* 287:    */   {
/* 288:375 */     if (this.componenteCosto.getIndicadorCoeficienteProduccion().booleanValue())
/* 289:    */     {
/* 290:376 */       this.componenteCosto.setIndicadorProrratearHorasMaquina(Boolean.valueOf(false));
/* 291:377 */       this.componenteCosto.setIndicadorProrratearHorasMaquinaXValor(Boolean.valueOf(false));
/* 292:378 */       this.componenteCosto.setIndicadorProrratearHorasHombre(Boolean.valueOf(false));
/* 293:379 */       this.componenteCosto.setIndicadorProrratearHorasHombreXValor(Boolean.valueOf(false));
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   public DetalleComponenteCosto agregarDetalle()
/* 298:    */   {
/* 299:384 */     DetalleComponenteCosto detalle = new DetalleComponenteCosto();
/* 300:385 */     detalle.setIdOrganizacion(this.componenteCosto.getIdOrganizacion());
/* 301:386 */     detalle.setIdSucursal(this.componenteCosto.getIdSucursal());
/* 302:387 */     detalle.setCuentaContable(new CuentaContable());
/* 303:388 */     detalle.setCuentaContableCierre(new CuentaContable());
/* 304:389 */     detalle.getCuentaContableCierre().setCodigo("  ");
/* 305:390 */     detalle.setComponenteCosto(this.componenteCosto);
/* 306:391 */     this.componenteCosto.getListaDetalleComponenteCosto().add(detalle);
/* 307:392 */     return detalle;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void eliminarDetalle()
/* 311:    */   {
/* 312:396 */     this.detalleComponenteCosto = ((DetalleComponenteCosto)this.dtDetalleComponenteCosto.getRowData());
/* 313:397 */     this.detalleComponenteCosto.setEliminado(true);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void seleccionarCuentaContable(SelectEvent event)
/* 317:    */   {
/* 318:401 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 319:    */     
/* 320:403 */     this.detalleComponenteCosto.setCuentaContable(cuentaContable);
/* 321:    */     
/* 322:405 */     Map<String, String> filtros = new HashMap();
/* 323:406 */     filtros.put("idOrganizacion", "" + AppUtil.getOrganizacion().getId());
/* 324:407 */     filtros.put("codigo", cuentaContable.getCodigo() + "%");
/* 325:408 */     filtros.put("indicadorMovimiento", "true");
/* 326:409 */     filtros.put("idCuentaContable", "!=" + cuentaContable.getId());
/* 327:410 */     List<CuentaContable> listaSubcuentas = this.servicioCuentaContable.obtenerListaCombo("codigo", true, filtros);
/* 328:411 */     for (CuentaContable subcuenta : listaSubcuentas)
/* 329:    */     {
/* 330:412 */       DetalleComponenteCosto detalle = agregarDetalle();
/* 331:413 */       detalle.setCuentaContable(subcuenta);
/* 332:414 */       detalle.setValorCalculo(this.detalleComponenteCosto.getValorCalculo());
/* 333:415 */       detalle.setCuentaContableCierre(this.detalleComponenteCosto.getCuentaContableCierre());
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void seleccionarCuentaContableCierre(SelectEvent event)
/* 338:    */   {
/* 339:420 */     CuentaContable cuentaContable = (CuentaContable)event.getObject();
/* 340:    */     
/* 341:422 */     this.detalleComponenteCosto.setCuentaContableCierre(cuentaContable);
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void buscarCuentaContable()
/* 345:    */   {
/* 346:    */     try
/* 347:    */     {
/* 348:428 */       this.detalleComponenteCosto = ((DetalleComponenteCosto)this.dtDetalleComponenteCosto.getRowData());
/* 349:429 */       String codigoCuentaContable = this.detalleComponenteCosto.getCuentaContable().getCodigo();
/* 350:430 */       if (codigoCuentaContable != "")
/* 351:    */       {
/* 352:431 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion()
/* 353:432 */           .getIdOrganizacion());
/* 354:433 */         this.detalleComponenteCosto.setCuentaContable(cuentaContable);
/* 355:    */       }
/* 356:    */     }
/* 357:    */     catch (ExcepcionAS2Financiero e)
/* 358:    */     {
/* 359:436 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + this.detalleComponenteCosto.getCuentaContable().getCodigo();
/* 360:437 */       addInfoMessage(strMensaje);
/* 361:    */     }
/* 362:    */     catch (Exception e)
/* 363:    */     {
/* 364:440 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void buscarCuentaContableCierre()
/* 369:    */   {
/* 370:    */     try
/* 371:    */     {
/* 372:447 */       this.detalleComponenteCosto = ((DetalleComponenteCosto)this.dtDetalleComponenteCosto.getRowData());
/* 373:448 */       String codigoCuentaContable = this.detalleComponenteCosto.getCuentaContableCierre().getCodigo();
/* 374:449 */       if (codigoCuentaContable != "")
/* 375:    */       {
/* 376:450 */         CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion()
/* 377:451 */           .getIdOrganizacion());
/* 378:452 */         this.detalleComponenteCosto.setCuentaContableCierre(cuentaContable);
/* 379:    */       }
/* 380:    */     }
/* 381:    */     catch (ExcepcionAS2Financiero e)
/* 382:    */     {
/* 383:456 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion()) + this.detalleComponenteCosto.getCuentaContableCierre().getCodigo();
/* 384:457 */       addInfoMessage(strMensaje);
/* 385:    */     }
/* 386:    */     catch (Exception e)
/* 387:    */     {
/* 388:460 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 389:    */     }
/* 390:    */   }
/* 391:    */   
/* 392:    */   public DataTable getDtDetalleComponenteCosto()
/* 393:    */   {
/* 394:465 */     return this.dtDetalleComponenteCosto;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void setDtDetalleComponenteCosto(DataTable dtDetalleComponenteCosto)
/* 398:    */   {
/* 399:469 */     this.dtDetalleComponenteCosto = dtDetalleComponenteCosto;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public List<DetalleComponenteCosto> getListaDetalleComponenteCosto()
/* 403:    */   {
/* 404:473 */     this.listaDetalleComponenteCosto = new ArrayList();
/* 405:474 */     for (DetalleComponenteCosto detalle : this.componenteCosto.getListaDetalleComponenteCosto()) {
/* 406:475 */       if (!detalle.isEliminado()) {
/* 407:476 */         this.listaDetalleComponenteCosto.add(detalle);
/* 408:    */       }
/* 409:    */     }
/* 410:479 */     return this.listaDetalleComponenteCosto;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setListaDetalleComponenteCosto(List<DetalleComponenteCosto> listaDetalleComponenteCosto)
/* 414:    */   {
/* 415:483 */     this.listaDetalleComponenteCosto = listaDetalleComponenteCosto;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 419:    */   {
/* 420:487 */     return this.listaCuentaContableBean;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 424:    */   {
/* 425:491 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public DetalleComponenteCosto getDetalleComponenteCosto()
/* 429:    */   {
/* 430:495 */     return this.detalleComponenteCosto;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void setDetalleComponenteCosto(DetalleComponenteCosto detalleComponenteCosto)
/* 434:    */   {
/* 435:499 */     this.detalleComponenteCosto = detalleComponenteCosto;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public List<ValoresCalculo> getListaValoresCalculo()
/* 439:    */   {
/* 440:503 */     if (this.listaValoresCalculo == null)
/* 441:    */     {
/* 442:504 */       this.listaValoresCalculo = new ArrayList();
/* 443:505 */       for (ValoresCalculo valor : ValoresCalculo.values()) {
/* 444:506 */         this.listaValoresCalculo.add(valor);
/* 445:    */       }
/* 446:    */     }
/* 447:509 */     return this.listaValoresCalculo;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public DataTable getDtDetalleComponenteCostoDistribucion()
/* 451:    */   {
/* 452:513 */     return this.dtDetalleComponenteCostoDistribucion;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setDtDetalleComponenteCostoDistribucion(DataTable dtDetalleComponenteCostoDistribucion)
/* 456:    */   {
/* 457:517 */     this.dtDetalleComponenteCostoDistribucion = dtDetalleComponenteCostoDistribucion;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public List<DetalleComponenteCostoDistribucion> getListaDetalleComponenteCostoDistribucion()
/* 461:    */   {
/* 462:521 */     List<DetalleComponenteCostoDistribucion> lista = new ArrayList();
/* 463:522 */     for (DetalleComponenteCostoDistribucion detalleComponenteCostoDistribucion : this.componenteCosto.getListaDetalleComponenteCostoDistribucion()) {
/* 464:523 */       if (!detalleComponenteCostoDistribucion.isEliminado()) {
/* 465:524 */         lista.add(detalleComponenteCostoDistribucion);
/* 466:    */       }
/* 467:    */     }
/* 468:527 */     return lista;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void eliminarDetalleDistribucion()
/* 472:    */   {
/* 473:532 */     DetalleComponenteCostoDistribucion detalleComponenteCostoDistribucion = (DetalleComponenteCostoDistribucion)this.dtDetalleComponenteCostoDistribucion.getRowData();
/* 474:533 */     detalleComponenteCostoDistribucion.setEliminado(true);
/* 475:    */   }
/* 476:    */   
/* 477:    */   public void agregarDetalleDistribucion()
/* 478:    */   {
/* 479:537 */     DetalleComponenteCostoDistribucion detalleComponenteCostoDistribucion = new DetalleComponenteCostoDistribucion();
/* 480:538 */     detalleComponenteCostoDistribucion.setIdOrganizacion(this.componenteCosto.getIdOrganizacion());
/* 481:539 */     detalleComponenteCostoDistribucion.setIdSucursal(this.componenteCosto.getIdSucursal());
/* 482:540 */     detalleComponenteCostoDistribucion.setComponenteCosto(this.componenteCosto);
/* 483:541 */     this.componenteCosto.getListaDetalleComponenteCostoDistribucion().add(detalleComponenteCostoDistribucion);
/* 484:    */   }
/* 485:    */   
/* 486:    */   public List<RutaFabricacion> getListaRutaFabricacion()
/* 487:    */   {
/* 488:545 */     if (this.listaRutaFabricacion == null)
/* 489:    */     {
/* 490:546 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 491:547 */       filtros.put("activo", "true");
/* 492:548 */       this.listaRutaFabricacion = this.servicioRutaFabricacion.obtenerListaCombo("nombre", true, filtros);
/* 493:    */     }
/* 494:550 */     return this.listaRutaFabricacion;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public boolean isIndicadorValidarDimension1()
/* 498:    */   {
/* 499:554 */     for (DetalleComponenteCosto detalle : getListaDetalleComponenteCosto()) {
/* 500:555 */       if (detalle.getCuentaContable() != null) {
/* 501:556 */         return detalle.getCuentaContable().isIndicadorValidarDimension1();
/* 502:    */       }
/* 503:    */     }
/* 504:559 */     return false;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public boolean isIndicadorValidarDimension2()
/* 508:    */   {
/* 509:563 */     for (DetalleComponenteCosto detalle : getListaDetalleComponenteCosto()) {
/* 510:564 */       if (detalle.getCuentaContable() != null) {
/* 511:565 */         return detalle.getCuentaContable().isIndicadorValidarDimension2();
/* 512:    */       }
/* 513:    */     }
/* 514:568 */     return false;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public boolean isIndicadorValidarDimension3()
/* 518:    */   {
/* 519:572 */     for (DetalleComponenteCosto detalle : getListaDetalleComponenteCosto()) {
/* 520:573 */       if (detalle.getCuentaContable() != null) {
/* 521:574 */         return detalle.getCuentaContable().isIndicadorValidarDimension3();
/* 522:    */       }
/* 523:    */     }
/* 524:577 */     return false;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public boolean isIndicadorValidarDimension4()
/* 528:    */   {
/* 529:581 */     for (DetalleComponenteCosto detalle : getListaDetalleComponenteCosto()) {
/* 530:582 */       if (detalle.getCuentaContable() != null) {
/* 531:583 */         return detalle.getCuentaContable().isIndicadorValidarDimension4();
/* 532:    */       }
/* 533:    */     }
/* 534:586 */     return false;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public boolean isIndicadorValidarDimension5()
/* 538:    */   {
/* 539:590 */     for (DetalleComponenteCosto detalle : getListaDetalleComponenteCosto()) {
/* 540:591 */       if (detalle.getCuentaContable() != null) {
/* 541:592 */         return detalle.getCuentaContable().isIndicadorValidarDimension5();
/* 542:    */       }
/* 543:    */     }
/* 544:595 */     return false;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public ListaDimensionContableBean getListaDimensionContableBean()
/* 548:    */   {
/* 549:599 */     return this.listaDimensionContableBean;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setListaDimensionContableBean(ListaDimensionContableBean listaDimensionContableBean)
/* 553:    */   {
/* 554:603 */     this.listaDimensionContableBean = listaDimensionContableBean;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public DetalleComponenteCostoDistribucion getDetalleComponenteCostoDistribucion()
/* 558:    */   {
/* 559:607 */     return this.detalleComponenteCostoDistribucion;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setDetalleComponenteCostoDistribucion(DetalleComponenteCostoDistribucion detalleComponenteCostoDistribucion)
/* 563:    */   {
/* 564:611 */     this.detalleComponenteCostoDistribucion = detalleComponenteCostoDistribucion;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void seleccionarDimensionContableListener(SelectEvent event)
/* 568:    */   {
/* 569:615 */     DimensionContable dimension = (DimensionContable)event.getObject();
/* 570:616 */     String numeroDimension = getListaDimensionContableBean().getNumeroDimension();
/* 571:617 */     if (numeroDimension.equals("1")) {
/* 572:618 */       this.detalleComponenteCostoDistribucion.setDimensionContable1(dimension);
/* 573:619 */     } else if (numeroDimension.equals("2")) {
/* 574:620 */       this.detalleComponenteCostoDistribucion.setDimensionContable2(dimension);
/* 575:621 */     } else if (numeroDimension.equals("3")) {
/* 576:622 */       this.detalleComponenteCostoDistribucion.setDimensionContable3(dimension);
/* 577:623 */     } else if (numeroDimension.equals("4")) {
/* 578:624 */       this.detalleComponenteCostoDistribucion.setDimensionContable4(dimension);
/* 579:625 */     } else if (numeroDimension.equals("5")) {
/* 580:626 */       this.detalleComponenteCostoDistribucion.setDimensionContable5(dimension);
/* 581:    */     }
/* 582:    */   }
/* 583:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ComponenteCostoBean
 * JD-Core Version:    0.7.0.1
 */