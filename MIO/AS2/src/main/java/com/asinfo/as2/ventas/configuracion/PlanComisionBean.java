/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   8:    */ import com.asinfo.as2.entities.DetalleVersionPlanComision;
/*   9:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias;
/*  10:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionSupervisor;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.PlanComision;
/*  14:    */ import com.asinfo.as2.entities.Producto;
/*  15:    */ import com.asinfo.as2.entities.RangoDiasComision;
/*  16:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  17:    */ import com.asinfo.as2.entities.Sucursal;
/*  18:    */ import com.asinfo.as2.entities.VersionPlanComision;
/*  19:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  20:    */ import com.asinfo.as2.enumeraciones.FormaPagoComisionEnum;
/*  21:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  22:    */ import com.asinfo.as2.enumeraciones.TipoComisionEnum;
/*  23:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  24:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  25:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  26:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  27:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  28:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  29:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  30:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  31:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  32:    */ import com.asinfo.as2.util.AppUtil;
/*  33:    */ import com.asinfo.as2.util.RutaArchivo;
/*  34:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  35:    */ import com.asinfo.as2.utils.JsfUtil;
/*  36:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  37:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision;
/*  38:    */ import java.io.BufferedInputStream;
/*  39:    */ import java.io.InputStream;
/*  40:    */ import java.util.ArrayList;
/*  41:    */ import java.util.Calendar;
/*  42:    */ import java.util.Date;
/*  43:    */ import java.util.List;
/*  44:    */ import java.util.Map;
/*  45:    */ import java.util.Vector;
/*  46:    */ import javax.annotation.PostConstruct;
/*  47:    */ import javax.ejb.EJB;
/*  48:    */ import javax.faces.bean.ManagedBean;
/*  49:    */ import javax.faces.bean.ManagedProperty;
/*  50:    */ import javax.faces.bean.ViewScoped;
/*  51:    */ import org.apache.log4j.Logger;
/*  52:    */ import org.primefaces.component.datatable.DataTable;
/*  53:    */ import org.primefaces.context.RequestContext;
/*  54:    */ import org.primefaces.event.FileUploadEvent;
/*  55:    */ import org.primefaces.model.LazyDataModel;
/*  56:    */ import org.primefaces.model.SortOrder;
/*  57:    */ import org.primefaces.model.StreamedContent;
/*  58:    */ import org.primefaces.model.UploadedFile;
/*  59:    */ 
/*  60:    */ @ManagedBean
/*  61:    */ @ViewScoped
/*  62:    */ public class PlanComisionBean
/*  63:    */   extends PageControllerAS2
/*  64:    */ {
/*  65:    */   private static final long serialVersionUID = 1L;
/*  66:    */   @EJB
/*  67:    */   private ServicioPlanComision servicioPlanComision;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioUsuario servicioUsuario;
/*  70:    */   @EJB
/*  71:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  72:    */   @EJB
/*  73:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  74:    */   @EJB
/*  75:    */   private ServicioProducto servicioProducto;
/*  76:    */   @EJB
/*  77:    */   private ServicioGenerico<RangoDiasComision> servicioRangoDiasComision;
/*  78:    */   @EJB
/*  79:    */   private ServicioEmpresa servicioEmpresa;
/*  80:    */   @EJB
/*  81:    */   private ServicioMigracion servicioMigracion;
/*  82:    */   private PlanComision planComision;
/*  83:    */   private LazyDataModel<PlanComision> listaPlanComision;
/*  84:    */   private VersionPlanComision versionPlanComision;
/*  85:    */   private List<EntidadUsuario> listaAgenteComercial;
/*  86:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  87:    */   private List<RangoDiasComision> listaRangoDiasComision;
/*  88:    */   private List<DetalleVersionPlanComision> listaDetalleVersionPlanComisionFiltrados;
/*  89:107 */   private boolean indicadorCrearVersion = false;
/*  90:108 */   private boolean indicadorVentanaVersion = false;
/*  91:    */   private DataTable dtPlanComision;
/*  92:    */   private DataTable dtDetalleVersionPlanComision;
/*  93:    */   private DataTable dtDetalleVersionPlanComisionSupervisor;
/*  94:    */   @ManagedProperty("#{listaProductoBean}")
/*  95:    */   private ListaProductoBean listaProductoBean;
/*  96:    */   
/*  97:    */   @PostConstruct
/*  98:    */   public void init()
/*  99:    */   {
/* 100:123 */     getListaProductoBean().setIndicadorVenta(true);
/* 101:124 */     getListaProductoBean().setActivo(true);
/* 102:    */     
/* 103:126 */     this.listaPlanComision = new LazyDataModel()
/* 104:    */     {
/* 105:    */       private static final long serialVersionUID = -1956844755215090557L;
/* 106:    */       
/* 107:    */       public List<PlanComision> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 108:    */       {
/* 109:133 */         List<PlanComision> lista = new ArrayList();
/* 110:134 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 111:    */         
/* 112:136 */         lista = PlanComisionBean.this.servicioPlanComision.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 113:    */         
/* 114:138 */         PlanComisionBean.this.listaPlanComision.setRowCount(PlanComisionBean.this.servicioPlanComision.contarPorCriterio(filters));
/* 115:139 */         return lista;
/* 116:    */       }
/* 117:    */     };
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String editar()
/* 121:    */   {
/* 122:151 */     if ((getPlanComision() != null) && (getPlanComision().getId() != 0))
/* 123:    */     {
/* 124:152 */       this.planComision = this.servicioPlanComision.cargarDetalle(this.planComision.getId());
/* 125:154 */       for (VersionPlanComision version : this.planComision.getListaVersionPlanComision())
/* 126:    */       {
/* 127:155 */         Date diaInicial = FuncionesUtiles.getFecha(1, version.getMesInicial().getNumero(), version.getAnioInicial());
/* 128:156 */         Date diaFinal = FuncionesUtiles.getFechaFinMes(version.getAnioFinal(), version.getMesFinal().getNumero());
/* 129:157 */         Date hoy = new Date();
/* 130:158 */         if ((version.isActivo()) && (hoy.before(diaFinal)) && (hoy.after(diaInicial)))
/* 131:    */         {
/* 132:159 */           this.versionPlanComision = version;
/* 133:160 */           break;
/* 134:    */         }
/* 135:    */       }
/* 136:163 */       actualizarVersion();
/* 137:    */       
/* 138:165 */       actualizarRangoDiasCompleto();
/* 139:166 */       setEditado(true);
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:168 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 144:    */     }
/* 145:170 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String guardar()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:181 */       this.servicioPlanComision.guardar(this.planComision);
/* 153:182 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 154:183 */       limpiar();
/* 155:    */     }
/* 156:    */     catch (AS2Exception e)
/* 157:    */     {
/* 158:185 */       JsfUtil.addErrorMessage(e, "");
/* 159:    */     }
/* 160:    */     catch (Exception e)
/* 161:    */     {
/* 162:187 */       e.printStackTrace();
/* 163:188 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 164:    */     }
/* 165:190 */     return "";
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String eliminar()
/* 169:    */   {
/* 170:200 */     if ((getPlanComision() != null) && (getPlanComision().getId() != 0)) {
/* 171:    */       try
/* 172:    */       {
/* 173:202 */         this.servicioPlanComision.eliminar(this.planComision);
/* 174:203 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 175:204 */         limpiar();
/* 176:    */       }
/* 177:    */       catch (Exception e)
/* 178:    */       {
/* 179:206 */         e.printStackTrace();
/* 180:207 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 181:208 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 182:    */       }
/* 183:    */     } else {
/* 184:211 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 185:    */     }
/* 186:213 */     return "";
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String limpiar()
/* 190:    */   {
/* 191:223 */     setEditado(false);
/* 192:    */     
/* 193:225 */     this.planComision = new PlanComision();
/* 194:226 */     this.planComision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 195:227 */     this.planComision.setIdSucursal(AppUtil.getSucursal().getId());
/* 196:228 */     this.planComision.setActivo(true);
/* 197:    */     
/* 198:230 */     this.versionPlanComision = null;
/* 199:    */     
/* 200:232 */     actualizarVersion();
/* 201:    */     
/* 202:234 */     return "";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String cargarDatos()
/* 206:    */   {
/* 207:239 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   private void actualizarRangoDiasCompleto()
/* 211:    */   {
/* 212:243 */     for (VersionPlanComision versionPlanComision : this.planComision.getListaVersionPlanComision()) {
/* 213:244 */       actualizarRangoDiasPorVersion(versionPlanComision);
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void actualizarRangoDiasPorVersion(VersionPlanComision versionPlanComision)
/* 218:    */   {
/* 219:249 */     for (DetalleVersionPlanComision detalle : versionPlanComision.getListaDetalleVersionPlanComision()) {
/* 220:250 */       actualizarRangoDiasPorDetalleVersion(detalle);
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void actualizarRangoDiasPorDetalleVersion(DetalleVersionPlanComision detalleVersionPlanComision)
/* 225:    */   {
/* 226:255 */     this.servicioPlanComision.actualizarRangoDiasPorDetalleVersion(detalleVersionPlanComision, getListaRangoDiasComision());
/* 227:    */   }
/* 228:    */   
/* 229:    */   public DetalleVersionPlanComisionRangoDias obtenerValorDetalleVersionPlanComision(DetalleVersionPlanComision detalleVersionPlanComision, RangoDiasComision rangoDias)
/* 230:    */   {
/* 231:260 */     return this.servicioPlanComision.obtenerValorDetalleVersionPlanComision(detalleVersionPlanComision, rangoDias);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void agregarVersion()
/* 235:    */   {
/* 236:264 */     Calendar hoy = Calendar.getInstance();
/* 237:265 */     this.versionPlanComision = new VersionPlanComision();
/* 238:266 */     this.versionPlanComision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 239:267 */     this.versionPlanComision.setIdSucursal(AppUtil.getSucursal().getId());
/* 240:268 */     this.versionPlanComision.setActivo(true);
/* 241:269 */     this.versionPlanComision.setAnioInicial(hoy.get(1));
/* 242:270 */     this.versionPlanComision.setAnioFinal(hoy.get(1));
/* 243:271 */     this.versionPlanComision.setMesFinal(Mes.DICIEMBRE);
/* 244:272 */     this.versionPlanComision.setMesInicial(Mes.ENERO);
/* 245:273 */     this.versionPlanComision.setPlanComision(this.planComision);
/* 246:274 */     this.indicadorCrearVersion = true;
/* 247:275 */     this.indicadorVentanaVersion = true;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void guardarVersionPlanComision()
/* 251:    */   {
/* 252:279 */     if (this.indicadorCrearVersion) {
/* 253:280 */       this.planComision.getListaVersionPlanComision().add(this.versionPlanComision);
/* 254:    */     }
/* 255:282 */     RequestContext context = RequestContext.getCurrentInstance();
/* 256:283 */     context.execute("PF('dglVersion').hide();");
/* 257:284 */     this.indicadorCrearVersion = false;
/* 258:285 */     this.indicadorVentanaVersion = false;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void cancelarVersionPlanComision()
/* 262:    */   {
/* 263:289 */     this.versionPlanComision = null;
/* 264:290 */     RequestContext context = RequestContext.getCurrentInstance();
/* 265:291 */     context.execute("PF('dglVersion').hide();");
/* 266:292 */     this.indicadorCrearVersion = false;
/* 267:293 */     this.indicadorVentanaVersion = false;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public DetalleVersionPlanComision agregarDetalleVersion()
/* 271:    */   {
/* 272:297 */     DetalleVersionPlanComision detalle = null;
/* 273:298 */     if (this.versionPlanComision != null)
/* 274:    */     {
/* 275:299 */       detalle = new DetalleVersionPlanComision();
/* 276:300 */       detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 277:301 */       detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 278:302 */       detalle.setVersionPlanComision(this.versionPlanComision);
/* 279:303 */       this.versionPlanComision.getListaDetalleVersionPlanComision().add(detalle);
/* 280:304 */       actualizarRangoDiasPorDetalleVersion(detalle);
/* 281:    */     }
/* 282:306 */     actualizarVersion();
/* 283:307 */     return detalle;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void eliminarDetalleVersion()
/* 287:    */   {
/* 288:311 */     DetalleVersionPlanComision detalle = (DetalleVersionPlanComision)this.dtDetalleVersionPlanComision.getRowData();
/* 289:312 */     detalle.setEliminado(true);
/* 290:313 */     actualizarVersion();
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void agregarDetalleVersionSupervisor()
/* 294:    */   {
/* 295:317 */     if (this.versionPlanComision != null)
/* 296:    */     {
/* 297:318 */       DetalleVersionPlanComisionSupervisor detalle = new DetalleVersionPlanComisionSupervisor();
/* 298:319 */       detalle.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 299:320 */       detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 300:321 */       detalle.setVersionPlanComision(this.versionPlanComision);
/* 301:322 */       this.versionPlanComision.getListaDetalleVersionPlanComisionSupervisor().add(detalle);
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void actualizarAgenteComercial(DetalleVersionPlanComisionSupervisor detalle)
/* 306:    */   {
/* 307:327 */     detalle.setEmpresa(null);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void eliminarDetalleVersionSupervisor()
/* 311:    */   {
/* 312:331 */     DetalleVersionPlanComisionSupervisor detalle = (DetalleVersionPlanComisionSupervisor)this.dtDetalleVersionPlanComisionSupervisor.getRowData();
/* 313:332 */     detalle.setEliminado(true);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void cargarProducto(Producto producto)
/* 317:    */   {
/* 318:336 */     DetalleVersionPlanComision detalle = agregarDetalleVersion();
/* 319:337 */     detalle.setProducto(producto);
/* 320:338 */     actualizarVersion();
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List<DetalleVersionPlanComision> getListaDetalleVersionPlanComision()
/* 324:    */   {
/* 325:342 */     List<DetalleVersionPlanComision> lista = new ArrayList();
/* 326:343 */     if (this.versionPlanComision != null) {
/* 327:344 */       for (DetalleVersionPlanComision detalleVersionPlanComision : this.versionPlanComision.getListaDetalleVersionPlanComision()) {
/* 328:345 */         if (!detalleVersionPlanComision.isEliminado()) {
/* 329:346 */           lista.add(detalleVersionPlanComision);
/* 330:    */         }
/* 331:    */       }
/* 332:    */     }
/* 333:350 */     return lista;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void actualizarVersion()
/* 337:    */   {
/* 338:354 */     this.listaDetalleVersionPlanComisionFiltrados = getListaDetalleVersionPlanComision();
/* 339:355 */     if (this.dtDetalleVersionPlanComision != null) {
/* 340:356 */       this.dtDetalleVersionPlanComision.reset();
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   public List<DetalleVersionPlanComision> getListaDetalleVersionPlanComisionFiltrados()
/* 345:    */   {
/* 346:361 */     return this.listaDetalleVersionPlanComisionFiltrados;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setListaDetalleVersionPlanComisionFiltrados(List<DetalleVersionPlanComision> listaDetalleVersionPlanComisionFiltrados)
/* 350:    */   {
/* 351:365 */     this.listaDetalleVersionPlanComisionFiltrados = listaDetalleVersionPlanComisionFiltrados;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 355:    */   {
/* 356:369 */     DetalleVersionPlanComisionSupervisor detalle = (DetalleVersionPlanComisionSupervisor)this.dtDetalleVersionPlanComisionSupervisor.getRowData();
/* 357:370 */     if (detalle.getAgenteComercial() != null)
/* 358:    */     {
/* 359:371 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 360:372 */       filtros.put("activo", "true");
/* 361:373 */       filtros.put("cliente.agenteComercial.idUsuario", detalle.getAgenteComercial().getId() + "");
/* 362:374 */       return this.servicioEmpresa.autocompletarClientes(consulta, filtros);
/* 363:    */     }
/* 364:376 */     return new ArrayList();
/* 365:    */   }
/* 366:    */   
/* 367:    */   public List<DetalleVersionPlanComisionSupervisor> getListaDetalleVersionPlanComisionSupervisor()
/* 368:    */   {
/* 369:380 */     List<DetalleVersionPlanComisionSupervisor> lista = new ArrayList();
/* 370:381 */     if (this.versionPlanComision != null) {
/* 371:382 */       for (DetalleVersionPlanComisionSupervisor detalleVersionPlanComisionSupervisor : this.versionPlanComision
/* 372:383 */         .getListaDetalleVersionPlanComisionSupervisor()) {
/* 373:384 */         if (!detalleVersionPlanComisionSupervisor.isEliminado()) {
/* 374:385 */           lista.add(detalleVersionPlanComisionSupervisor);
/* 375:    */         }
/* 376:    */       }
/* 377:    */     }
/* 378:389 */     return lista;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public List<Mes> getListaMes()
/* 382:    */   {
/* 383:393 */     List<Mes> lista = new ArrayList();
/* 384:394 */     for (Mes mes : Mes.values()) {
/* 385:395 */       lista.add(mes);
/* 386:    */     }
/* 387:397 */     return lista;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<TipoComisionEnum> getListaTipoComisionEnum()
/* 391:    */   {
/* 392:401 */     List<TipoComisionEnum> lista = new ArrayList();
/* 393:402 */     for (TipoComisionEnum tipo : TipoComisionEnum.values()) {
/* 394:403 */       lista.add(tipo);
/* 395:    */     }
/* 396:405 */     return lista;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public List<FormaPagoComisionEnum> getListaFormaPagoComisionEnum()
/* 400:    */   {
/* 401:409 */     List<FormaPagoComisionEnum> lista = new ArrayList();
/* 402:410 */     for (FormaPagoComisionEnum formaPago : FormaPagoComisionEnum.values()) {
/* 403:411 */       lista.add(formaPago);
/* 404:    */     }
/* 405:413 */     return lista;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public List<EntidadUsuario> getListaAgenteComercial()
/* 409:    */   {
/* 410:417 */     if (this.listaAgenteComercial == null) {
/* 411:418 */       this.listaAgenteComercial = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId(), Boolean.valueOf(true));
/* 412:    */     }
/* 413:420 */     return this.listaAgenteComercial;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void actualizarCategoriaProducto(DetalleVersionPlanComision detalle)
/* 417:    */   {
/* 418:424 */     detalle.setSubcategoriaProducto(null);
/* 419:    */   }
/* 420:    */   
/* 421:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 422:    */   {
/* 423:428 */     if (this.listaCategoriaProducto == null)
/* 424:    */     {
/* 425:429 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 426:430 */       filtros.put("activo", "true");
/* 427:431 */       this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/* 428:    */     }
/* 429:433 */     return this.listaCategoriaProducto;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public List<SubcategoriaProducto> obtenerListaSubcategoriaProducto(CategoriaProducto categoriaProducto)
/* 433:    */   {
/* 434:437 */     List<SubcategoriaProducto> listaSubcategoriaProducto = new ArrayList();
/* 435:438 */     if (categoriaProducto != null)
/* 436:    */     {
/* 437:439 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 438:440 */       filtros.put("activo", "true");
/* 439:441 */       filtros.put("categoriaProducto.idCategoriaProducto", "" + categoriaProducto.getId());
/* 440:442 */       listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filtros);
/* 441:    */     }
/* 442:444 */     return listaSubcategoriaProducto;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public List<Producto> autocompletarProducto(String consulta)
/* 446:    */   {
/* 447:448 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 448:449 */     filtros.put("indicadorVenta", "true");
/* 449:450 */     filtros.put("activo", "true");
/* 450:451 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/* 451:    */   }
/* 452:    */   
/* 453:    */   public List<RangoDiasComision> getListaRangoDiasComision()
/* 454:    */   {
/* 455:455 */     if (this.listaRangoDiasComision == null)
/* 456:    */     {
/* 457:456 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 458:457 */       filtros.put("activo", "true");
/* 459:458 */       this.listaRangoDiasComision = this.servicioRangoDiasComision.obtenerListaCombo(RangoDiasComision.class, "diaInicial", true, filtros);
/* 460:    */     }
/* 461:460 */     return this.listaRangoDiasComision;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public StreamedContent descargarExcelDetalles()
/* 465:    */   {
/* 466:464 */     StreamedContent file = null;
/* 467:465 */     if (this.versionPlanComision != null) {
/* 468:    */       try
/* 469:    */       {
/* 470:468 */         Vector<Object> v = new Vector();
/* 471:    */         
/* 472:    */ 
/* 473:471 */         String cabecera = "";
/* 474:472 */         switch (getClasificacionPagoComisiones().intValue())
/* 475:    */         {
/* 476:    */         case 1: 
/* 477:474 */           cabecera = "Codigo Categoria Producto, Nombre Categoria Producto";
/* 478:475 */           break;
/* 479:    */         case 2: 
/* 480:477 */           cabecera = "Codigo SubCategoria Producto, Nombre SubCategoria Producto";
/* 481:478 */           break;
/* 482:    */         case 3: 
/* 483:480 */           cabecera = "Codigo Producto, Nombre Producto";
/* 484:481 */           break;
/* 485:    */         }
/* 486:485 */         cabecera = cabecera + ", Forma Pago, Nota ";
/* 487:486 */         for (RangoDiasComision rangoDias : getListaRangoDiasComision()) {
/* 488:487 */           cabecera = cabecera + ", " + rangoDias.getNombre();
/* 489:    */         }
/* 490:489 */         v.addElement(cabecera);
/* 491:492 */         for (DetalleVersionPlanComision detalle : this.versionPlanComision.getListaDetalleVersionPlanComision())
/* 492:    */         {
/* 493:493 */           String fila = "";
/* 494:494 */           switch (getClasificacionPagoComisiones().intValue())
/* 495:    */           {
/* 496:    */           case 1: 
/* 497:496 */             fila = detalle.getCategoriaProducto().getCodigo() + ", " + detalle.getCategoriaProducto().getNombre();
/* 498:497 */             break;
/* 499:    */           case 2: 
/* 500:499 */             fila = detalle.getSubcategoriaProducto().getCodigo() + ", " + detalle.getSubcategoriaProducto().getNombre();
/* 501:500 */             break;
/* 502:    */           case 3: 
/* 503:502 */             fila = detalle.getProducto().getCodigo() + ", " + detalle.getProducto().getNombre();
/* 504:503 */             break;
/* 505:    */           }
/* 506:507 */           fila = fila + ", " + detalle.getFormaPagoComisionEnum().toString();
/* 507:508 */           fila = fila + ", " + detalle.getDescripcion();
/* 508:509 */           for (RangoDiasComision rangoDias : getListaRangoDiasComision()) {
/* 509:510 */             fila = fila + ", " + obtenerValorDetalleVersionPlanComision(detalle, rangoDias).getValor();
/* 510:    */           }
/* 511:512 */           v.addElement(fila);
/* 512:    */         }
/* 513:515 */         String rutaArchivo = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "plantillas", "planComision");
/* 514:516 */         String nombreArchivo = "ListaDetalleVersionPlanComision.xls";
/* 515:    */         
/* 516:518 */         FuncionesUtiles.crearExcel(v, this.versionPlanComision.getNombre(), rutaArchivo, nombreArchivo);
/* 517:519 */         file = FuncionesUtiles.descargarArchivo(rutaArchivo + nombreArchivo, "application/xls", nombreArchivo);
/* 518:    */       }
/* 519:    */       catch (Exception e)
/* 520:    */       {
/* 521:522 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 522:    */       }
/* 523:    */     } else {
/* 524:525 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_version"));
/* 525:    */     }
/* 526:528 */     return file;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void migrarListaDetalleComision(FileUploadEvent event)
/* 530:    */   {
/* 531:532 */     if (this.versionPlanComision != null) {
/* 532:    */       try
/* 533:    */       {
/* 534:534 */         String fileName = "migracion_lista_version_plan_comision" + event.getFile().getFileName();
/* 535:535 */         InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 536:536 */         this.servicioMigracion.migrarListaDetalleVersionPlanComision(this.planComision, this.versionPlanComision, fileName, input, 0, 
/* 537:537 */           getClasificacionPagoComisiones().intValue());
/* 538:    */         
/* 539:539 */         addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 540:    */       }
/* 541:    */       catch (AS2Exception e)
/* 542:    */       {
/* 543:541 */         JsfUtil.addErrorMessage(e, "");
/* 544:    */       }
/* 545:    */       catch (ExcepcionAS2 e)
/* 546:    */       {
/* 547:543 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 548:544 */         LOG.error(e);
/* 549:    */       }
/* 550:    */       catch (Exception e)
/* 551:    */       {
/* 552:546 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 553:547 */         LOG.error(e);
/* 554:    */       }
/* 555:    */     } else {
/* 556:550 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_version"));
/* 557:    */     }
/* 558:    */   }
/* 559:    */   
/* 560:    */   public String copiar()
/* 561:    */     throws ExcepcionAS2Financiero
/* 562:    */   {
/* 563:555 */     if ((this.planComision != null) && (this.planComision.getId() != 0))
/* 564:    */     {
/* 565:556 */       PlanComision planComisionCopia = this.servicioPlanComision.cargarDetalle(this.planComision.getId());
/* 566:557 */       this.planComision = this.servicioPlanComision.copiarPlanComision(planComisionCopia);
/* 567:558 */       setEditado(true);
/* 568:    */     }
/* 569:    */     else
/* 570:    */     {
/* 571:560 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 572:    */     }
/* 573:562 */     return "";
/* 574:    */   }
/* 575:    */   
/* 576:    */   public PlanComision getPlanComision()
/* 577:    */   {
/* 578:566 */     return this.planComision;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void setPlanComision(PlanComision planComision)
/* 582:    */   {
/* 583:570 */     this.planComision = planComision;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public LazyDataModel<PlanComision> getListaPlanComision()
/* 587:    */   {
/* 588:574 */     return this.listaPlanComision;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public void setListaPlanComision(LazyDataModel<PlanComision> listaPlanComision)
/* 592:    */   {
/* 593:578 */     this.listaPlanComision = listaPlanComision;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public DataTable getDtPlanComision()
/* 597:    */   {
/* 598:582 */     return this.dtPlanComision;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public void setDtPlanComision(DataTable dtPlanComision)
/* 602:    */   {
/* 603:586 */     this.dtPlanComision = dtPlanComision;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public VersionPlanComision getVersionPlanComision()
/* 607:    */   {
/* 608:590 */     return this.versionPlanComision;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void setVersionPlanComision(VersionPlanComision versionPlanComision)
/* 612:    */   {
/* 613:594 */     this.versionPlanComision = versionPlanComision;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public DataTable getDtDetalleVersionPlanComision()
/* 617:    */   {
/* 618:598 */     return this.dtDetalleVersionPlanComision;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void setDtDetalleVersionPlanComision(DataTable dtDetalleVersionPlanComision)
/* 622:    */   {
/* 623:602 */     this.dtDetalleVersionPlanComision = dtDetalleVersionPlanComision;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public ListaProductoBean getListaProductoBean()
/* 627:    */   {
/* 628:606 */     return this.listaProductoBean;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 632:    */   {
/* 633:610 */     this.listaProductoBean = listaProductoBean;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public Integer getClasificacionPagoComisiones()
/* 637:    */   {
/* 638:614 */     return ParametrosSistema.getClasificadorPagoComisiones(AppUtil.getOrganizacion().getId());
/* 639:    */   }
/* 640:    */   
/* 641:    */   public boolean isIndicadorVentanaVersion()
/* 642:    */   {
/* 643:618 */     return this.indicadorVentanaVersion;
/* 644:    */   }
/* 645:    */   
/* 646:    */   public void setIndicadorVentanaVersion(boolean indicadorVentanaVersion)
/* 647:    */   {
/* 648:622 */     this.indicadorVentanaVersion = indicadorVentanaVersion;
/* 649:    */   }
/* 650:    */   
/* 651:    */   public DataTable getDtDetalleVersionPlanComisionSupervisor()
/* 652:    */   {
/* 653:626 */     return this.dtDetalleVersionPlanComisionSupervisor;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public void setDtDetalleVersionPlanComisionSupervisor(DataTable dtDetalleVersionPlanComisionSupervisor)
/* 657:    */   {
/* 658:630 */     this.dtDetalleVersionPlanComisionSupervisor = dtDetalleVersionPlanComisionSupervisor;
/* 659:    */   }
/* 660:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.PlanComisionBean
 * JD-Core Version:    0.7.0.1
 */