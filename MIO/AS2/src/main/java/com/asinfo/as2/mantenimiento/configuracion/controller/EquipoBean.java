/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   7:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*  11:    */ import com.asinfo.as2.entities.mantenimiento.ActividadImagenOrdenTrabajoMantenimiento;
/*  12:    */ import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
/*  13:    */ import com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo;
/*  14:    */ import com.asinfo.as2.entities.mantenimiento.DocumentoEquipo;
/*  15:    */ import com.asinfo.as2.entities.mantenimiento.Equipo;
/*  16:    */ import com.asinfo.as2.entities.mantenimiento.ImagenEquipo;
/*  17:    */ import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
/*  18:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
/*  19:    */ import com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo;
/*  20:    */ import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
/*  21:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  22:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  23:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  24:    */ import com.asinfo.as2.enumeraciones.FrecuenciaFechaEnum;
/*  25:    */ import com.asinfo.as2.enumeraciones.PrioridadEnum;
/*  26:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  27:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  28:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  29:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo;
/*  30:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo;
/*  31:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioLecturaMantenimiento;
/*  32:    */ import com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento;
/*  33:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  34:    */ import com.asinfo.as2.util.AppUtil;
/*  35:    */ import com.asinfo.as2.util.RutaArchivo;
/*  36:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  37:    */ import com.asinfo.as2.utils.JsfUtil;
/*  38:    */ import java.util.ArrayList;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import javax.faces.model.SelectItem;
/*  47:    */ import org.apache.log4j.Logger;
/*  48:    */ import org.primefaces.component.datatable.DataTable;
/*  49:    */ import org.primefaces.event.FileUploadEvent;
/*  50:    */ import org.primefaces.model.LazyDataModel;
/*  51:    */ import org.primefaces.model.SortOrder;
/*  52:    */ 
/*  53:    */ @ManagedBean
/*  54:    */ @ViewScoped
/*  55:    */ public class EquipoBean
/*  56:    */   extends PageControllerAS2
/*  57:    */ {
/*  58:    */   private static final long serialVersionUID = 1L;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioEquipo servicioEquipo;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  63:    */   @EJB
/*  64:    */   private transient ServicioUbicacionActivo servicioUbicacionActivo;
/*  65:    */   @EJB
/*  66:    */   private transient ServicioGenerico<SubcategoriaEquipo> servicioSubcategoriaEquipo;
/*  67:    */   @EJB
/*  68:    */   private ServicioGenerico<ComponenteEquipo> servicioComponenteEquipo;
/*  69:    */   @EJB
/*  70:    */   private ServicioGenerico<DetalleComponenteEquipo> servicioDetalleComponenteEquipo;
/*  71:    */   @EJB
/*  72:    */   private ServicioGenerico<DocumentoEquipo> servicioDocumentoEquipo;
/*  73:    */   @EJB
/*  74:    */   private transient ServicioActivoFijo servicioActivoFijo;
/*  75:    */   @EJB
/*  76:    */   private transient ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  77:    */   @EJB
/*  78:    */   private transient ServicioPlanMantenimiento servicioPlanMantenimiento;
/*  79:    */   @EJB
/*  80:    */   private transient ServicioLecturaMantenimiento servicioLecturaMantenimiento;
/*  81:    */   private Equipo equipo;
/*  82:    */   private LazyDataModel<Equipo> listaEquipo;
/*  83:    */   private boolean visible;
/*  84:    */   private DataTable dtEquipo;
/*  85:    */   private DataTable dtDocumentoEquipo;
/*  86:    */   private DataTable dtImagenEquipo;
/*  87:    */   private DataTable dtDetallePlanMantenimiento;
/*  88:    */   private DataTable dtDetallePlanMantenimientoNoAsignado;
/*  89:    */   private List<SelectItem> listaPeriodoVidaUtil;
/*  90:    */   private List<SelectItem> listaPrioridad;
/*  91:    */   private DocumentoEquipo documentoEquipo;
/*  92:    */   private ImagenEquipo imagenEquipo;
/*  93:    */   private DataTable dtDocumentoInfoEquipo;
/*  94:    */   private DataTable dtImagenInfoEquipo;
/*  95:    */   private DataTable dtComponenteEquipo;
/*  96:    */   private DataTable dtDetalleComponenteEquipo;
/*  97:    */   private DataTable dtDetalleComponenteEquipoInfo;
/*  98:    */   private DataTable dtDetallePlanMantenimientoInfo;
/*  99:    */   private DataTable dtDetalleActividad;
/* 100:    */   private ActividadImagenOrdenTrabajoMantenimiento imagenOTM;
/* 101:    */   private DataTable dtDetalleActividadInfo;
/* 102:    */   private List<ComponenteEquipo> listaComponente;
/* 103:    */   private List<PlanMantenimiento> listaPlanMantenimientoNoAsignado;
/* 104:    */   
/* 105:    */   public List<ComponenteEquipo> getListaComponente()
/* 106:    */   {
/* 107:121 */     if (this.listaComponente == null)
/* 108:    */     {
/* 109:122 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 110:123 */       this.listaComponente = this.servicioComponenteEquipo.obtenerListaCombo(ComponenteEquipo.class, "nombre", true, filters);
/* 111:    */     }
/* 112:125 */     return this.listaComponente;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setListaComponente(List<ComponenteEquipo> listaComponente)
/* 116:    */   {
/* 117:129 */     this.listaComponente = listaComponente;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public DataTable getDtDetalleComponenteEquipo()
/* 121:    */   {
/* 122:133 */     return this.dtDetalleComponenteEquipo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDtDetalleComponenteEquipo(DataTable dtDetalleComponenteEquipo)
/* 126:    */   {
/* 127:137 */     this.dtDetalleComponenteEquipo = dtDetalleComponenteEquipo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public DataTable getDtComponenteEquipo()
/* 131:    */   {
/* 132:141 */     return this.dtComponenteEquipo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDtComponenteEquipo(DataTable dtComponenteEquipo)
/* 136:    */   {
/* 137:145 */     this.dtComponenteEquipo = dtComponenteEquipo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   @PostConstruct
/* 141:    */   public void init()
/* 142:    */   {
/* 143:150 */     this.listaEquipo = new LazyDataModel()
/* 144:    */     {
/* 145:    */       private static final long serialVersionUID = 1L;
/* 146:    */       
/* 147:    */       public List<Equipo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 148:    */       {
/* 149:157 */         List<Equipo> lista = new ArrayList();
/* 150:158 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 151:159 */         filters = EquipoBean.this.agregarFiltroOrganizacion(filters);
/* 152:160 */         lista = EquipoBean.this.servicioEquipo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/* 153:161 */         EquipoBean.this.listaEquipo.setRowCount(EquipoBean.this.servicioEquipo.contarPorCriterio(filters));
/* 154:162 */         return lista;
/* 155:    */       }
/* 156:    */     };
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String editar()
/* 160:    */   {
/* 161:175 */     if (getEquipo().getId() > 0)
/* 162:    */     {
/* 163:176 */       this.equipo = this.servicioEquipo.cargarDetalle(this.equipo);
/* 164:177 */       this.listaPlanMantenimientoNoAsignado = null;
/* 165:178 */       setEditado(true);
/* 166:    */     }
/* 167:    */     else
/* 168:    */     {
/* 169:180 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 170:    */     }
/* 171:182 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String guardar()
/* 175:    */   {
/* 176:    */     try
/* 177:    */     {
/* 178:193 */       this.servicioEquipo.guardar(this.equipo);
/* 179:194 */       limpiar();
/* 180:195 */       setEditado(false);
/* 181:196 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 182:    */     }
/* 183:    */     catch (AS2Exception e)
/* 184:    */     {
/* 185:198 */       JsfUtil.addErrorMessage(e, "");
/* 186:    */     }
/* 187:    */     catch (Exception e)
/* 188:    */     {
/* 189:200 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 190:201 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 191:    */     }
/* 192:203 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String limpiar()
/* 196:    */   {
/* 197:213 */     crearEquipo();
/* 198:214 */     this.equipo.getListaImagenEquipo().clear();
/* 199:215 */     this.listaPlanMantenimientoNoAsignado = null;
/* 200:216 */     return "";
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String eliminar()
/* 204:    */   {
/* 205:    */     try
/* 206:    */     {
/* 207:228 */       this.servicioEquipo.eliminar(this.equipo);
/* 208:229 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 209:    */     }
/* 210:    */     catch (Exception e)
/* 211:    */     {
/* 212:231 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 213:232 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 214:    */     }
/* 215:234 */     return "";
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String cargarDatos()
/* 219:    */   {
/* 220:244 */     return "";
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void crearEquipo()
/* 224:    */   {
/* 225:251 */     this.equipo = new Equipo();
/* 226:252 */     this.equipo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 227:253 */     this.equipo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 228:254 */     this.equipo.setActivo(true);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Equipo getEquipo()
/* 232:    */   {
/* 233:263 */     if (this.equipo == null) {
/* 234:264 */       crearEquipo();
/* 235:    */     }
/* 236:266 */     return this.equipo;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setEquipo(Equipo Equipo)
/* 240:    */   {
/* 241:276 */     this.equipo = Equipo;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public LazyDataModel<Equipo> getListaEquipo()
/* 245:    */   {
/* 246:285 */     return this.listaEquipo;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setListaEquipo(LazyDataModel<Equipo> listaEquipo)
/* 250:    */   {
/* 251:295 */     this.listaEquipo = listaEquipo;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public DataTable getDtEquipo()
/* 255:    */   {
/* 256:304 */     return this.dtEquipo;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setDtEquipo(DataTable dtEquipo)
/* 260:    */   {
/* 261:314 */     this.dtEquipo = dtEquipo;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<FacturaProveedor> autocompletarFactura(String consulta)
/* 265:    */   {
/* 266:318 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 267:319 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/* 268:320 */     filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 269:321 */     if ((!consulta.isEmpty()) && (consulta != null)) {
/* 270:322 */       if (consulta.matches("[0-9]*"))
/* 271:    */       {
/* 272:323 */         filters.put("OR~facturaProveedorSRI.numero", consulta);
/* 273:324 */         filters.put("OR~empresa.nombreFiscal", consulta);
/* 274:    */       }
/* 275:325 */       else if (consulta.indexOf("-") > -1)
/* 276:    */       {
/* 277:326 */         String delimeter = "-";
/* 278:    */         
/* 279:328 */         String[] temp = consulta.split(delimeter);
/* 280:329 */         if (temp.length == 3)
/* 281:    */         {
/* 282:330 */           String sucursal = temp[0];
/* 283:331 */           String puntoVenta = temp[1];
/* 284:332 */           String secuencial = temp[2];
/* 285:333 */           filters.put("facturaProveedorSRI.establecimiento", "=" + sucursal);
/* 286:334 */           filters.put("facturaProveedorSRI.puntoEmision", "=" + puntoVenta);
/* 287:335 */           filters.put("facturaProveedorSRI.numero", "=" + secuencial);
/* 288:    */         }
/* 289:    */       }
/* 290:    */       else
/* 291:    */       {
/* 292:338 */         filters.put("OR~referencia3", consulta);
/* 293:339 */         filters.put("OR~empresa.nombreFiscal", consulta);
/* 294:    */       }
/* 295:    */     }
/* 296:343 */     return this.servicioFacturaProveedor.obtenerListaCombo("referencia3", true, filters);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public List<ActivoFijo> autoCompletarActivoFijo(String consulta)
/* 300:    */   {
/* 301:348 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 302:349 */     List<ActivoFijo> lista = new ArrayList();
/* 303:350 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 304:351 */       filters.put("nombre", "%" + consulta + "%");
/* 305:    */     }
/* 306:353 */     lista = this.servicioActivoFijo.obtenerListaComboParaMantenimiento("nombre", true, filters);
/* 307:354 */     return lista;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void listenerCalendario()
/* 311:    */   {
/* 312:358 */     String numeroFactura = "";
/* 313:359 */     if (this.equipo.getFacturaProveedor() != null)
/* 314:    */     {
/* 315:360 */       this.equipo.setFechaCompra(this.equipo.getFacturaProveedor().getFecha());
/* 316:    */       
/* 317:    */ 
/* 318:363 */       numeroFactura = this.equipo.getFacturaProveedor().getFacturaProveedorSRI().getEstablecimiento() + "-" + this.equipo.getFacturaProveedor().getFacturaProveedorSRI().getPuntoEmision() + "-" + this.equipo.getFacturaProveedor().getFacturaProveedorSRI().getNumero();
/* 319:    */     }
/* 320:    */     else
/* 321:    */     {
/* 322:365 */       numeroFactura = null;
/* 323:    */     }
/* 324:367 */     this.equipo.setNumeroFactura(numeroFactura);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public List<UbicacionActivo> autocompletarUbicacion(String consulta)
/* 328:    */   {
/* 329:371 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 330:372 */     List<UbicacionActivo> lista = new ArrayList();
/* 331:373 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 332:374 */       filters.put("nombre", "%" + consulta + "%");
/* 333:    */     }
/* 334:376 */     lista = this.servicioUbicacionActivo.obtenerListaCombo("nombre", true, filters);
/* 335:    */     
/* 336:378 */     return lista;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public List<Equipo> autocompletarEquipoPadre(String consulta)
/* 340:    */   {
/* 341:382 */     List<Equipo> lista = new ArrayList();
/* 342:383 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 343:384 */     if (this.equipo.getIdEquipo() != 0) {
/* 344:385 */       filters.put("idEquipo", "!=" + String.valueOf(this.equipo.getIdEquipo()));
/* 345:    */     }
/* 346:387 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 347:388 */       filters.put("nombre", "%" + consulta + "%");
/* 348:    */     }
/* 349:390 */     lista = this.servicioEquipo.obtenerListaCombo("nombre", true, filters);
/* 350:    */     
/* 351:392 */     return lista;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public List<SubcategoriaEquipo> autocompletarSubcategoriaEquipo(String consulta)
/* 355:    */   {
/* 356:396 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 357:397 */     List<SubcategoriaEquipo> lista = new ArrayList();
/* 358:398 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 359:399 */       filters.put("nombre", "%" + consulta + "%");
/* 360:    */     }
/* 361:401 */     lista = this.servicioSubcategoriaEquipo.obtenerListaCombo(SubcategoriaEquipo.class, "nombre", true, filters);
/* 362:    */     
/* 363:403 */     return lista;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public List<SelectItem> getListaPrioridad()
/* 367:    */   {
/* 368:410 */     if (this.listaPrioridad == null)
/* 369:    */     {
/* 370:411 */       this.listaPrioridad = new ArrayList();
/* 371:412 */       this.listaPrioridad.add(new SelectItem("", ""));
/* 372:413 */       for (PrioridadEnum t : PrioridadEnum.values()) {
/* 373:414 */         this.listaPrioridad.add(new SelectItem(t, t.getNombre()));
/* 374:    */       }
/* 375:    */     }
/* 376:417 */     return this.listaPrioridad;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public DataTable getDtDocumentoEquipo()
/* 380:    */   {
/* 381:424 */     return this.dtDocumentoEquipo;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setDtDocumentoEquipo(DataTable dtDocumentoEquipo)
/* 385:    */   {
/* 386:432 */     this.dtDocumentoEquipo = dtDocumentoEquipo;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public DataTable getDtImagenEquipo()
/* 390:    */   {
/* 391:439 */     return this.dtImagenEquipo;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void setDtImagenEquipo(DataTable dtImagenEquipo)
/* 395:    */   {
/* 396:447 */     this.dtImagenEquipo = dtImagenEquipo;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public DocumentoEquipo getDocumentoEquipo()
/* 400:    */   {
/* 401:454 */     return this.documentoEquipo;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void setDocumentoEquipo(DocumentoEquipo documentoEquipo)
/* 405:    */   {
/* 406:462 */     this.documentoEquipo = documentoEquipo;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public ImagenEquipo getImagenEquipo()
/* 410:    */   {
/* 411:469 */     return this.imagenEquipo;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void setImagenEquipo(ImagenEquipo imagenEquipo)
/* 415:    */   {
/* 416:477 */     this.imagenEquipo = imagenEquipo;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void processUpload(FileUploadEvent event)
/* 420:    */   {
/* 421:    */     try
/* 422:    */     {
/* 423:482 */       if (this.documentoEquipo != null)
/* 424:    */       {
/* 425:483 */         String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "equipo");
/* 426:484 */         String nombre = AppUtil.getOrganizacion().getId() + "_" + this.documentoEquipo.getNombre();
/* 427:485 */         String fileName = FuncionesUtiles.uploadArchivo(event, String.valueOf(FuncionesUtiles.obtenerNumeroRandomico(999999999)), nombre, uploadDir);
/* 428:    */         
/* 429:487 */         this.documentoEquipo.setArchivo(fileName);
/* 430:488 */         this.documentoEquipo.setEliminado(false);
/* 431:489 */         this.documentoEquipo = null;
/* 432:    */       }
/* 433:491 */       if (this.imagenEquipo != null)
/* 434:    */       {
/* 435:492 */         String uploadDir = RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "equipo");
/* 436:493 */         String nombre = AppUtil.getOrganizacion().getId() + "_" + this.imagenEquipo.getNombre();
/* 437:494 */         String fileName = FuncionesUtiles.uploadArchivo(event, String.valueOf(FuncionesUtiles.obtenerNumeroRandomico(999999999)), nombre, uploadDir);
/* 438:    */         
/* 439:496 */         this.imagenEquipo.setArchivo(fileName);
/* 440:497 */         this.imagenEquipo.setEliminado(false);
/* 441:498 */         this.imagenEquipo = null;
/* 442:    */       }
/* 443:500 */       addInfoMessage(getLanguageController().getMensaje("msg_info_upload_archivo"));
/* 444:    */     }
/* 445:    */     catch (Exception e)
/* 446:    */     {
/* 447:502 */       e.printStackTrace();
/* 448:503 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_fichero"));
/* 449:504 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   public String getDirectorioDescarga()
/* 454:    */   {
/* 455:510 */     if ((this.imagenEquipo != null) || (this.documentoEquipo != null)) {
/* 456:511 */       return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "documentos", "equipo");
/* 457:    */     }
/* 458:512 */     if (this.imagenOTM != null) {
/* 459:513 */       return RutaArchivo.getDirectorioUpload(AppUtil.getOrganizacion().getId(), "imagenes", "orden_trabajo");
/* 460:    */     }
/* 461:515 */     return "";
/* 462:    */   }
/* 463:    */   
/* 464:    */   public String getNombreArchivo()
/* 465:    */   {
/* 466:    */     try
/* 467:    */     {
/* 468:    */       String str;
/* 469:522 */       if (this.documentoEquipo != null) {
/* 470:523 */         return this.documentoEquipo.getArchivo();
/* 471:    */       }
/* 472:524 */       if (this.imagenEquipo != null) {
/* 473:525 */         return this.imagenEquipo.getArchivo();
/* 474:    */       }
/* 475:526 */       if (this.imagenOTM != null) {
/* 476:527 */         return this.imagenOTM.getArchivo();
/* 477:    */       }
/* 478:    */     }
/* 479:    */     catch (Exception localException1) {}finally
/* 480:    */     {
/* 481:531 */       this.documentoEquipo = null;
/* 482:532 */       this.imagenEquipo = null;
/* 483:    */     }
/* 484:535 */     return null;
/* 485:    */   }
/* 486:    */   
/* 487:    */   /* Error */
/* 488:    */   public String eliminarArchivo()
/* 489:    */   {
/* 490:    */     // Byte code:
/* 491:    */     //   0: aload_0
/* 492:    */     //   1: getfield 111	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:documentoEquipo	Lcom/asinfo/as2/entities/mantenimiento/DocumentoEquipo;
/* 493:    */     //   4: ifnull +14 -> 18
/* 494:    */     //   7: aload_0
/* 495:    */     //   8: getfield 111	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:documentoEquipo	Lcom/asinfo/as2/entities/mantenimiento/DocumentoEquipo;
/* 496:    */     //   11: iconst_1
/* 497:    */     //   12: invokevirtual 124	com/asinfo/as2/entities/mantenimiento/DocumentoEquipo:setEliminado	(Z)V
/* 498:    */     //   15: goto +18 -> 33
/* 499:    */     //   18: aload_0
/* 500:    */     //   19: getfield 112	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:imagenEquipo	Lcom/asinfo/as2/entities/mantenimiento/ImagenEquipo;
/* 501:    */     //   22: ifnull +11 -> 33
/* 502:    */     //   25: aload_0
/* 503:    */     //   26: getfield 112	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:imagenEquipo	Lcom/asinfo/as2/entities/mantenimiento/ImagenEquipo;
/* 504:    */     //   29: iconst_1
/* 505:    */     //   30: invokevirtual 127	com/asinfo/as2/entities/mantenimiento/ImagenEquipo:setEliminado	(Z)V
/* 506:    */     //   33: aload_0
/* 507:    */     //   34: aconst_null
/* 508:    */     //   35: putfield 111	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:documentoEquipo	Lcom/asinfo/as2/entities/mantenimiento/DocumentoEquipo;
/* 509:    */     //   38: aload_0
/* 510:    */     //   39: aconst_null
/* 511:    */     //   40: putfield 112	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:imagenEquipo	Lcom/asinfo/as2/entities/mantenimiento/ImagenEquipo;
/* 512:    */     //   43: goto +30 -> 73
/* 513:    */     //   46: astore_1
/* 514:    */     //   47: aload_0
/* 515:    */     //   48: aconst_null
/* 516:    */     //   49: putfield 111	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:documentoEquipo	Lcom/asinfo/as2/entities/mantenimiento/DocumentoEquipo;
/* 517:    */     //   52: aload_0
/* 518:    */     //   53: aconst_null
/* 519:    */     //   54: putfield 112	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:imagenEquipo	Lcom/asinfo/as2/entities/mantenimiento/ImagenEquipo;
/* 520:    */     //   57: goto +16 -> 73
/* 521:    */     //   60: astore_2
/* 522:    */     //   61: aload_0
/* 523:    */     //   62: aconst_null
/* 524:    */     //   63: putfield 111	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:documentoEquipo	Lcom/asinfo/as2/entities/mantenimiento/DocumentoEquipo;
/* 525:    */     //   66: aload_0
/* 526:    */     //   67: aconst_null
/* 527:    */     //   68: putfield 112	com/asinfo/as2/mantenimiento/configuracion/controller/EquipoBean:imagenEquipo	Lcom/asinfo/as2/entities/mantenimiento/ImagenEquipo;
/* 528:    */     //   71: aload_2
/* 529:    */     //   72: athrow
/* 530:    */     //   73: aconst_null
/* 531:    */     //   74: areturn
/* 532:    */     // Line number table:
/* 533:    */     //   Java source line #540	-> byte code offset #0
/* 534:    */     //   Java source line #541	-> byte code offset #7
/* 535:    */     //   Java source line #542	-> byte code offset #18
/* 536:    */     //   Java source line #543	-> byte code offset #25
/* 537:    */     //   Java source line #547	-> byte code offset #33
/* 538:    */     //   Java source line #548	-> byte code offset #38
/* 539:    */     //   Java source line #549	-> byte code offset #43
/* 540:    */     //   Java source line #545	-> byte code offset #46
/* 541:    */     //   Java source line #547	-> byte code offset #47
/* 542:    */     //   Java source line #548	-> byte code offset #52
/* 543:    */     //   Java source line #549	-> byte code offset #57
/* 544:    */     //   Java source line #547	-> byte code offset #60
/* 545:    */     //   Java source line #548	-> byte code offset #66
/* 546:    */     //   Java source line #550	-> byte code offset #73
/* 547:    */     // Local variable table:
/* 548:    */     //   start	length	slot	name	signature
/* 549:    */     //   0	75	0	this	EquipoBean
/* 550:    */     //   46	1	1	localException	Exception
/* 551:    */     //   60	12	2	localObject	Object
/* 552:    */     // Exception table:
/* 553:    */     //   from	to	target	type
/* 554:    */     //   0	33	46	java/lang/Exception
/* 555:    */     //   0	33	60	finally
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void agregarDocumento()
/* 559:    */   {
/* 560:554 */     DocumentoEquipo de = new DocumentoEquipo();
/* 561:555 */     de.setIdOrganizacion(this.equipo.getIdOrganizacion());
/* 562:556 */     de.setIdSucursal(this.equipo.getIdSucursal());
/* 563:557 */     de.setEquipo(this.equipo);
/* 564:558 */     this.equipo.getListaDocumentoEquipo().add(de);
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void agregarImagen()
/* 568:    */   {
/* 569:562 */     ImagenEquipo ie = new ImagenEquipo();
/* 570:563 */     ie.setIdOrganizacion(this.equipo.getIdOrganizacion());
/* 571:564 */     ie.setIdSucursal(this.equipo.getIdSucursal());
/* 572:565 */     ie.setEquipo(this.equipo);
/* 573:566 */     this.equipo.getListaImagenEquipo().add(ie);
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void eliminarEquipoPadre()
/* 577:    */   {
/* 578:571 */     this.equipo.setEquipoPadre(null);
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void cargarDetalle()
/* 582:    */   {
/* 583:575 */     this.equipo = ((Equipo)this.dtEquipo.getRowData());
/* 584:576 */     this.equipo = this.servicioEquipo.cargarDetalle(this.equipo);
/* 585:577 */     getListaLecturaMantenimiento();
/* 586:    */   }
/* 587:    */   
/* 588:    */   public DataTable getDtDocumentoInfoEquipo()
/* 589:    */   {
/* 590:582 */     return this.dtDocumentoInfoEquipo;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public void setDtDocumentoInfoEquipo(DataTable dtDocumentoInfoEquipo)
/* 594:    */   {
/* 595:586 */     this.dtDocumentoInfoEquipo = dtDocumentoInfoEquipo;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public DataTable getDtImagenInfoEquipo()
/* 599:    */   {
/* 600:590 */     return this.dtImagenInfoEquipo;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public void setDtImagenInfoEquipo(DataTable dtImagenInfoEquipo)
/* 604:    */   {
/* 605:594 */     this.dtImagenInfoEquipo = dtImagenInfoEquipo;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public List<DetalleComponenteEquipo> getDetalleComponenteEquipo()
/* 609:    */   {
/* 610:599 */     List<DetalleComponenteEquipo> detalle = new ArrayList();
/* 611:600 */     for (DetalleComponenteEquipo dm : getEquipo().getListaComponenteEquipo()) {
/* 612:601 */       if (!dm.isEliminado()) {
/* 613:602 */         detalle.add(dm);
/* 614:    */       }
/* 615:    */     }
/* 616:605 */     return detalle;
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void agregarDetalleComponenteEquipo()
/* 620:    */   {
/* 621:609 */     DetalleComponenteEquipo detalle = new DetalleComponenteEquipo();
/* 622:610 */     detalle.setIdOrganizacion(this.equipo.getIdOrganizacion());
/* 623:611 */     detalle.setIdSucursal(this.equipo.getIdSucursal());
/* 624:612 */     detalle.setEquipo(this.equipo);
/* 625:613 */     this.equipo.getListaComponenteEquipo().add(detalle);
/* 626:    */   }
/* 627:    */   
/* 628:    */   public List<ComponenteEquipo> autocompletarComponente(String consulta)
/* 629:    */   {
/* 630:618 */     HashMap<ComponenteEquipo, DetalleComponenteEquipo> hmDce = new HashMap();
/* 631:619 */     List<ComponenteEquipo> lista = new ArrayList();
/* 632:620 */     for (DetalleComponenteEquipo dce : this.equipo.getListaComponenteEquipo()) {
/* 633:621 */       if (!dce.isEliminado()) {
/* 634:622 */         hmDce.put(dce.getComponenteEquipo(), dce);
/* 635:    */       }
/* 636:    */     }
/* 637:625 */     for (ComponenteEquipo ce : getListaComponente()) {
/* 638:626 */       if (!hmDce.containsKey(ce)) {
/* 639:627 */         lista.add(ce);
/* 640:    */       }
/* 641:    */     }
/* 642:630 */     return lista;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public void eliminarDetalle()
/* 646:    */   {
/* 647:634 */     DetalleComponenteEquipo dce = (DetalleComponenteEquipo)this.dtDetalleComponenteEquipo.getRowData();
/* 648:635 */     dce.setEliminado(true);
/* 649:    */   }
/* 650:    */   
/* 651:    */   public List<DetalleComponenteEquipo> getListaDetalleComponenteEquipo()
/* 652:    */   {
/* 653:639 */     List<DetalleComponenteEquipo> listaDetalleComponenteEquipo = new ArrayList();
/* 654:640 */     for (DetalleComponenteEquipo lDCE : this.equipo.getListaComponenteEquipo()) {
/* 655:641 */       if (!lDCE.isEliminado()) {
/* 656:644 */         listaDetalleComponenteEquipo.add(lDCE);
/* 657:    */       }
/* 658:    */     }
/* 659:646 */     return listaDetalleComponenteEquipo;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public void eliminarDocumento()
/* 663:    */   {
/* 664:650 */     DocumentoEquipo de = (DocumentoEquipo)this.dtDocumentoEquipo.getRowData();
/* 665:    */     
/* 666:652 */     de.setEliminado(true);
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void eliminarImagen()
/* 670:    */   {
/* 671:656 */     ImagenEquipo ie = (ImagenEquipo)this.dtImagenEquipo.getRowData();
/* 672:657 */     ie.setEliminado(true);
/* 673:    */   }
/* 674:    */   
/* 675:    */   public List<DocumentoEquipo> getListaDocumentoEquipo()
/* 676:    */   {
/* 677:661 */     List<DocumentoEquipo> listaDocumentoEquipo = new ArrayList();
/* 678:662 */     for (DocumentoEquipo de : this.equipo.getListaDocumentoEquipo()) {
/* 679:663 */       if (!de.isEliminado()) {
/* 680:664 */         listaDocumentoEquipo.add(de);
/* 681:    */       }
/* 682:    */     }
/* 683:667 */     return listaDocumentoEquipo;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public List<ImagenEquipo> getListaImagenEquipo()
/* 687:    */   {
/* 688:671 */     List<ImagenEquipo> listaImagenEquipo = new ArrayList();
/* 689:672 */     for (ImagenEquipo ie : this.equipo.getListaImagenEquipo()) {
/* 690:673 */       if (!ie.isEliminado()) {
/* 691:674 */         listaImagenEquipo.add(ie);
/* 692:    */       }
/* 693:    */     }
/* 694:677 */     return listaImagenEquipo;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public DataTable getDtDetalleComponenteEquipoInfo()
/* 698:    */   {
/* 699:681 */     return this.dtDetalleComponenteEquipoInfo;
/* 700:    */   }
/* 701:    */   
/* 702:    */   public void setDtDetalleComponenteEquipoInfo(DataTable dtDetalleComponenteEquipoInfo)
/* 703:    */   {
/* 704:685 */     this.dtDetalleComponenteEquipoInfo = dtDetalleComponenteEquipoInfo;
/* 705:    */   }
/* 706:    */   
/* 707:    */   public boolean isVisible()
/* 708:    */   {
/* 709:689 */     return this.visible;
/* 710:    */   }
/* 711:    */   
/* 712:    */   public void setVisible(boolean visible)
/* 713:    */   {
/* 714:693 */     this.visible = visible;
/* 715:    */   }
/* 716:    */   
/* 717:    */   public void verDialogoDetalle()
/* 718:    */   {
/* 719:697 */     setVisible(true);
/* 720:698 */     cargarDetalle();
/* 721:    */   }
/* 722:    */   
/* 723:    */   public void quitarDialogoDetalle()
/* 724:    */   {
/* 725:702 */     setVisible(false);
/* 726:    */   }
/* 727:    */   
/* 728:    */   public void listenerEquipo()
/* 729:    */     throws AS2Exception
/* 730:    */   {
/* 731:706 */     if (this.equipo.getActivoFijo() != null)
/* 732:    */     {
/* 733:707 */       this.equipo.setCodigo(this.equipo.getActivoFijo().getCodigo());
/* 734:708 */       this.equipo.setNombre(this.equipo.getActivoFijo().getNombre());
/* 735:709 */       this.equipo.setNumeroSerie(this.equipo.getActivoFijo().getNumeroSerie());
/* 736:710 */       this.equipo.setNumeroParte(this.equipo.getActivoFijo().getNumeroParte());
/* 737:711 */       this.equipo.setCodigoBarras(this.equipo.getActivoFijo().getCodigoBarras());
/* 738:712 */       this.equipo.setCentroCosto(this.equipo.getActivoFijo().getCentroCosto());
/* 739:713 */       this.equipo.setFechaCompra(this.equipo.getActivoFijo().getFechaFacturaProveedor());
/* 740:714 */       String numeroFactura = this.equipo.getActivoFijo().getNumeroFacturaProveedor();
/* 741:715 */       List<FacturaProveedor> listaFacturaProvedor = new ArrayList();
/* 742:716 */       if (numeroFactura != null)
/* 743:    */       {
/* 744:717 */         String delimeter = "-";
/* 745:    */         
/* 746:719 */         String[] temp = numeroFactura.split(delimeter);
/* 747:720 */         if (temp.length == 3)
/* 748:    */         {
/* 749:721 */           String sucursal = temp[0];
/* 750:722 */           String puntoVenta = temp[1];
/* 751:723 */           String secuencial = temp[2];
/* 752:724 */           Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 753:725 */           filters.put("facturaProveedorSRI.establecimiento", "=" + sucursal);
/* 754:726 */           filters.put("facturaProveedorSRI.puntoEmision", "=" + puntoVenta);
/* 755:727 */           filters.put("facturaProveedorSRI.numero", "=" + secuencial);
/* 756:728 */           listaFacturaProvedor = this.servicioFacturaProveedor.obtenerListaCombo("fecha", true, filters);
/* 757:    */         }
/* 758:    */       }
/* 759:731 */       if (listaFacturaProvedor.size() > 0)
/* 760:    */       {
/* 761:732 */         this.equipo.setFacturaProveedor((FacturaProveedor)listaFacturaProvedor.get(0));
/* 762:733 */         this.equipo.setNumeroFactura(numeroFactura);
/* 763:    */       }
/* 764:    */     }
/* 765:    */   }
/* 766:    */   
/* 767:    */   public DataTable getDtDetallePlanMantenimiento()
/* 768:    */   {
/* 769:739 */     return this.dtDetallePlanMantenimiento;
/* 770:    */   }
/* 771:    */   
/* 772:    */   public void setDtDetallePlanMantenimiento(DataTable dtDetallePlanMantenimiento)
/* 773:    */   {
/* 774:743 */     this.dtDetallePlanMantenimiento = dtDetallePlanMantenimiento;
/* 775:    */   }
/* 776:    */   
/* 777:    */   public List<PlanMantenimientoEquipo> getListaDetallePlanMantenimiento()
/* 778:    */   {
/* 779:747 */     List<PlanMantenimientoEquipo> lista = new ArrayList();
/* 780:748 */     if (this.equipo != null) {
/* 781:749 */       for (PlanMantenimientoEquipo planMantenimientoEquipo : this.equipo.getListaPlanMantenimientoEquipo()) {
/* 782:750 */         if (!planMantenimientoEquipo.isEliminado()) {
/* 783:751 */           lista.add(planMantenimientoEquipo);
/* 784:    */         }
/* 785:    */       }
/* 786:    */     }
/* 787:755 */     return lista;
/* 788:    */   }
/* 789:    */   
/* 790:    */   public void eliminarDetallePlanMantenimiento()
/* 791:    */   {
/* 792:759 */     PlanMantenimientoEquipo detalle = (PlanMantenimientoEquipo)this.dtDetallePlanMantenimiento.getRowData();
/* 793:760 */     detalle.setEliminado(true);
/* 794:761 */     this.listaPlanMantenimientoNoAsignado = null;
/* 795:    */   }
/* 796:    */   
/* 797:    */   public DataTable getDtDetallePlanMantenimientoNoAsignado()
/* 798:    */   {
/* 799:765 */     return this.dtDetallePlanMantenimientoNoAsignado;
/* 800:    */   }
/* 801:    */   
/* 802:    */   public void setDtDetallePlanMantenimientoNoAsignado(DataTable dtDetallePlanMantenimientoNoAsignado)
/* 803:    */   {
/* 804:769 */     this.dtDetallePlanMantenimientoNoAsignado = dtDetallePlanMantenimientoNoAsignado;
/* 805:    */   }
/* 806:    */   
/* 807:    */   public List<PlanMantenimiento> getListaPlanMantenimientoNoAsignado()
/* 808:    */   {
/* 809:773 */     if (this.listaPlanMantenimientoNoAsignado == null)
/* 810:    */     {
/* 811:774 */       this.listaPlanMantenimientoNoAsignado = new ArrayList();
/* 812:775 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 813:776 */       List<PlanMantenimiento> lista = this.servicioPlanMantenimiento.obtenerListaPorPagina(0, 1000, "nombre", true, filtros);
/* 814:777 */       for (PlanMantenimiento planMantenimiento : lista)
/* 815:    */       {
/* 816:778 */         boolean encontre = false;
/* 817:779 */         for (PlanMantenimientoEquipo detalle : getListaDetallePlanMantenimiento()) {
/* 818:780 */           if (detalle.getPlanMantenimiento().getId() == planMantenimiento.getId())
/* 819:    */           {
/* 820:781 */             encontre = true;
/* 821:782 */             break;
/* 822:    */           }
/* 823:    */         }
/* 824:785 */         if (!encontre) {
/* 825:786 */           this.listaPlanMantenimientoNoAsignado.add(planMantenimiento);
/* 826:    */         }
/* 827:    */       }
/* 828:    */     }
/* 829:790 */     return this.listaPlanMantenimientoNoAsignado;
/* 830:    */   }
/* 831:    */   
/* 832:    */   public void setListaPlanMantenimientoNoAsignado(List<PlanMantenimiento> listaPlanMantenimientoNoAsignado)
/* 833:    */   {
/* 834:794 */     this.listaPlanMantenimientoNoAsignado = listaPlanMantenimientoNoAsignado;
/* 835:    */   }
/* 836:    */   
/* 837:    */   public void agregarDetallePlanMantenimiento()
/* 838:    */   {
/* 839:798 */     PlanMantenimiento plan = (PlanMantenimiento)this.dtDetallePlanMantenimientoNoAsignado.getRowData();
/* 840:799 */     PlanMantenimientoEquipo detalle = new PlanMantenimientoEquipo();
/* 841:800 */     detalle.setIdOrganizacion(this.equipo.getIdOrganizacion());
/* 842:801 */     detalle.setIdSucursal(AppUtil.getSucursal().getId());
/* 843:802 */     detalle.setEquipo(this.equipo);
/* 844:803 */     detalle.setPlanMantenimiento(plan);
/* 845:804 */     this.equipo.getListaPlanMantenimientoEquipo().add(detalle);
/* 846:805 */     this.listaPlanMantenimientoNoAsignado = null;
/* 847:806 */     if (this.dtDetallePlanMantenimientoNoAsignado != null)
/* 848:    */     {
/* 849:807 */       this.dtDetallePlanMantenimientoNoAsignado.setFilteredValue(null);
/* 850:808 */       this.dtDetallePlanMantenimientoNoAsignado.reset();
/* 851:    */     }
/* 852:    */   }
/* 853:    */   
/* 854:    */   public List<SelectItem> getListaPeriodoVidaUtil()
/* 855:    */   {
/* 856:813 */     if (this.listaPeriodoVidaUtil == null)
/* 857:    */     {
/* 858:814 */       this.listaPeriodoVidaUtil = new ArrayList();
/* 859:815 */       for (FrecuenciaFechaEnum f : FrecuenciaFechaEnum.values()) {
/* 860:816 */         this.listaPeriodoVidaUtil.add(new SelectItem(f, f.getNombre()));
/* 861:    */       }
/* 862:    */     }
/* 863:819 */     return this.listaPeriodoVidaUtil;
/* 864:    */   }
/* 865:    */   
/* 866:    */   public void setListaPerioVidaUtil(List<SelectItem> listaPeriodoVidaUtil)
/* 867:    */   {
/* 868:823 */     this.listaPeriodoVidaUtil = listaPeriodoVidaUtil;
/* 869:    */   }
/* 870:    */   
/* 871:    */   public DataTable getDtDetallePlanMantenimientoInfo()
/* 872:    */   {
/* 873:827 */     return this.dtDetallePlanMantenimientoInfo;
/* 874:    */   }
/* 875:    */   
/* 876:    */   public void setDtDetallePlanMantenimientoInfo(DataTable dtDetallePlanMantenimientoInfo)
/* 877:    */   {
/* 878:831 */     this.dtDetallePlanMantenimientoInfo = dtDetallePlanMantenimientoInfo;
/* 879:    */   }
/* 880:    */   
/* 881:    */   public List<LecturaMantenimiento> getListaLecturaMantenimiento()
/* 882:    */   {
/* 883:835 */     return this.servicioLecturaMantenimiento.obtenerLecturasEquipo(this.equipo);
/* 884:    */   }
/* 885:    */   
/* 886:    */   public DataTable getDtDetalleActividad()
/* 887:    */   {
/* 888:839 */     return this.dtDetalleActividad;
/* 889:    */   }
/* 890:    */   
/* 891:    */   public void setDtDetalleActividad(DataTable dtDetalleActividad)
/* 892:    */   {
/* 893:843 */     this.dtDetalleActividad = dtDetalleActividad;
/* 894:    */   }
/* 895:    */   
/* 896:    */   public ActividadImagenOrdenTrabajoMantenimiento getImagenOTM()
/* 897:    */   {
/* 898:847 */     return this.imagenOTM;
/* 899:    */   }
/* 900:    */   
/* 901:    */   public void setImagenOTM(ActividadImagenOrdenTrabajoMantenimiento imagenOTM)
/* 902:    */   {
/* 903:851 */     this.imagenOTM = imagenOTM;
/* 904:    */   }
/* 905:    */   
/* 906:    */   public DataTable getDtDetalleActividadInfo()
/* 907:    */   {
/* 908:855 */     return this.dtDetalleActividadInfo;
/* 909:    */   }
/* 910:    */   
/* 911:    */   public void setDtDetalleActividadInfo(DataTable dtDetalleActividadInfo)
/* 912:    */   {
/* 913:859 */     this.dtDetalleActividadInfo = dtDetalleActividadInfo;
/* 914:    */   }
/* 915:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.EquipoBean
 * JD-Core Version:    0.7.0.1
 */