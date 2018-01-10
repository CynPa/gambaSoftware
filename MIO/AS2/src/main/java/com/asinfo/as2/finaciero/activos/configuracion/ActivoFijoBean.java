/*   1:    */ package com.asinfo.as2.finaciero.activos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   7:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   8:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   9:    */ import com.asinfo.as2.entities.Departamento;
/*  10:    */ import com.asinfo.as2.entities.Depreciacion;
/*  11:    */ import com.asinfo.as2.entities.DetalleDepreciacion;
/*  12:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Producto;
/*  15:    */ import com.asinfo.as2.entities.Sucursal;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  19:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*  20:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo;
/*  21:    */ import com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacion;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.util.RutaArchivo;
/*  25:    */ import java.io.BufferedInputStream;
/*  26:    */ import java.io.File;
/*  27:    */ import java.io.FileOutputStream;
/*  28:    */ import java.io.InputStream;
/*  29:    */ import java.math.BigDecimal;
/*  30:    */ import java.util.ArrayList;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.List;
/*  33:    */ import java.util.Map;
/*  34:    */ import javax.annotation.PostConstruct;
/*  35:    */ import javax.ejb.EJB;
/*  36:    */ import javax.faces.bean.ManagedBean;
/*  37:    */ import javax.faces.bean.ViewScoped;
/*  38:    */ import org.apache.log4j.Logger;
/*  39:    */ import org.primefaces.component.datatable.DataTable;
/*  40:    */ import org.primefaces.event.FileUploadEvent;
/*  41:    */ import org.primefaces.model.LazyDataModel;
/*  42:    */ import org.primefaces.model.SortOrder;
/*  43:    */ import org.primefaces.model.UploadedFile;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class ActivoFijoBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 1L;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioActivoFijo servicioActivoFijo;
/*  53:    */   @EJB
/*  54:    */   private transient ServicioCategoriaActivo servicioCategoriaActivo;
/*  55:    */   @EJB
/*  56:    */   private transient ServicioMotivoBajaActivo servicioMotivoBajaActivo;
/*  57:    */   @EJB
/*  58:    */   private transient ServicioDepreciacion servicioDepreciacion;
/*  59:    */   @EJB
/*  60:    */   private transient ServicioDepartamento servicioDepartamento;
/*  61:    */   @EJB
/*  62:    */   private transient ServicioSucursal servicioSucursal;
/*  63:    */   private ActivoFijo activoFijo;
/*  64:    */   private Producto producto;
/*  65:    */   private ActivoFijo activoPrincipal;
/*  66:    */   private BigDecimal totalDepreciacion;
/*  67:    */   private LazyDataModel<ActivoFijo> listaActivoFijo;
/*  68:    */   private List<CategoriaActivo> listaCategoriaActivo;
/*  69:    */   private List<MotivoBajaActivo> listaMotivoBajaActivo;
/*  70:    */   private List<DetalleDepreciacion> listaDetalleDepreciacion;
/*  71:    */   private Depreciacion depreciacionFiscal;
/*  72:    */   private Depreciacion depreciacionNIIF;
/*  73:    */   private List<ActivoFijo> listaActivoFijoPrincipal;
/*  74:    */   private List<Sucursal> listaSucursal;
/*  75:    */   private List<Departamento> listaDepartamento;
/*  76:    */   private DataTable dtActivoFijo;
/*  77:    */   private DataTable dtDepreciacionFiscal;
/*  78:    */   private DataTable dtDepreciacionNIIF;
/*  79:    */   private DataTable dtDetalleDepreciacion;
/*  80:    */   private DataTable dtActivoFijoRelacionado;
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85:122 */     this.listaActivoFijo = new LazyDataModel()
/*  86:    */     {
/*  87:    */       private static final long serialVersionUID = 1L;
/*  88:    */       
/*  89:    */       public List<ActivoFijo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  90:    */       {
/*  91:135 */         List<ActivoFijo> lista = new ArrayList();
/*  92:136 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  93:138 */         if (filters == null) {
/*  94:139 */           filters = new HashMap();
/*  95:    */         }
/*  96:141 */         filters.put("activo", "true");
/*  97:142 */         lista = ActivoFijoBean.this.servicioActivoFijo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  98:    */         
/*  99:144 */         ActivoFijoBean.this.listaActivoFijo.setRowCount(ActivoFijoBean.this.servicioActivoFijo.contarPorCriterio(filters));
/* 100:    */         
/* 101:146 */         return lista;
/* 102:    */       }
/* 103:    */     };
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void crearActivoFijo()
/* 107:    */   {
/* 108:160 */     this.activoFijo = new ActivoFijo();
/* 109:161 */     this.activoFijo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 110:162 */     this.activoFijo.setSucursal(AppUtil.getSucursal());
/* 111:163 */     this.activoFijo.setActivo(true);
/* 112:164 */     this.activoFijo.setIndicadorDepreciar(true);
/* 113:165 */     this.depreciacionFiscal = crearDepreciacion(this.depreciacionFiscal);
/* 114:166 */     this.depreciacionNIIF = crearDepreciacion(this.depreciacionNIIF);
/* 115:167 */     this.depreciacionFiscal.setIndicadorDepreciacionFiscal(true);
/* 116:168 */     this.depreciacionNIIF.setIndicadorDepreciacionFiscal(false);
/* 117:    */   }
/* 118:    */   
/* 119:    */   private Depreciacion crearDepreciacion(Depreciacion depreciacion)
/* 120:    */   {
/* 121:175 */     depreciacion = new Depreciacion();
/* 122:176 */     depreciacion.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 123:177 */     depreciacion.setIdSucursal(AppUtil.getSucursal().getId());
/* 124:178 */     depreciacion.setActivoFijo(this.activoFijo);
/* 125:179 */     depreciacion.setActivo(true);
/* 126:180 */     depreciacion.setEstado(Estado.ELABORADO);
/* 127:181 */     depreciacion.setTraEsEditable(true);
/* 128:182 */     this.activoFijo.getListaDepreciacion().add(depreciacion);
/* 129:183 */     return depreciacion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String editar()
/* 133:    */   {
/* 134:193 */     this.listaActivoFijoPrincipal = null;
/* 135:194 */     if (getActivoFijo().getIdActivoFijo() > 0)
/* 136:    */     {
/* 137:195 */       this.activoFijo = this.servicioActivoFijo.cargarDetalle(getActivoFijo().getId());
/* 138:    */       
/* 139:197 */       this.servicioActivoFijo.esEditable(this.activoFijo);
/* 140:199 */       if (this.activoFijo.getListaDepreciacion().size() > 0) {
/* 141:200 */         this.activoFijo.setTraFechaInicioDepreciacion(((Depreciacion)this.activoFijo.getListaDepreciacion().get(0)).getFechaInicioDepreciacion());
/* 142:    */       }
/* 143:202 */       actualizarCategoriaActivo();
/* 144:203 */       setEditado(true);
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:205 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 149:    */     }
/* 150:208 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String guardar()
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:218 */       boolean guardar = true;
/* 158:219 */       if (this.activoFijo.isIndicadorDepreciar())
/* 159:    */       {
/* 160:220 */         generarDepreciacion();
/* 161:221 */         if (this.activoFijo.getListaDepreciacion().size() > 0) {
/* 162:222 */           for (Depreciacion d : this.activoFijo.getListaDepreciacion()) {
/* 163:223 */             if (d.getValorADepreciar().compareTo(BigDecimal.ZERO) < 0) {
/* 164:224 */               guardar = false;
/* 165:    */             }
/* 166:    */           }
/* 167:    */         }
/* 168:    */       }
/* 169:    */       else
/* 170:    */       {
/* 171:229 */         for (Depreciacion dp : this.activoFijo.getListaDepreciacion()) {
/* 172:230 */           dp.setEliminado(true);
/* 173:    */         }
/* 174:    */       }
/* 175:233 */       if (guardar)
/* 176:    */       {
/* 177:234 */         this.servicioActivoFijo.guardar(this.activoFijo);
/* 178:235 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 179:236 */         setEditado(false);
/* 180:237 */         limpiar();
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:239 */         addInfoMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 185:    */       }
/* 186:    */     }
/* 187:    */     catch (ExcepcionAS2Financiero e)
/* 188:    */     {
/* 189:243 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 190:244 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 191:    */     }
/* 192:    */     catch (ExcepcionAS2 e)
/* 193:    */     {
/* 194:246 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 195:247 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 196:    */     }
/* 197:    */     catch (Exception e)
/* 198:    */     {
/* 199:249 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 200:250 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 201:    */     }
/* 202:253 */     return "";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String eliminar()
/* 206:    */   {
/* 207:    */     try
/* 208:    */     {
/* 209:263 */       addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 210:    */     }
/* 211:    */     catch (Exception e)
/* 212:    */     {
/* 213:265 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 214:266 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 215:    */     }
/* 216:268 */     return "";
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String cargarDatos()
/* 220:    */   {
/* 221:277 */     return "";
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<ActivoFijo> autocompletarAFT(String consulta)
/* 225:    */   {
/* 226:296 */     HashMap<String, String> filters = new HashMap();
/* 227:297 */     filters.put("nombre", consulta.trim());
/* 228:    */     
/* 229:    */ 
/* 230:300 */     List<ActivoFijo> lista = this.servicioActivoFijo.obtenerListaCombo("", true, filters);
/* 231:    */     
/* 232:302 */     return lista;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String limpiar()
/* 236:    */   {
/* 237:315 */     crearActivoFijo();
/* 238:316 */     this.listaActivoFijoPrincipal = null;
/* 239:317 */     return "";
/* 240:    */   }
/* 241:    */   
/* 242:    */   public List<Depreciacion> getListaDepreciacionFiscal()
/* 243:    */   {
/* 244:326 */     List<Depreciacion> lista = new ArrayList();
/* 245:327 */     for (Depreciacion depreciacion : getActivoFijo().getListaDepreciacion()) {
/* 246:328 */       if ((depreciacion != null) && (!depreciacion.isEliminado()) && (depreciacion.isIndicadorDepreciacionFiscal())) {
/* 247:329 */         lista.add(depreciacion);
/* 248:    */       }
/* 249:    */     }
/* 250:332 */     return lista;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<Depreciacion> getListaDepreciacionNIIF()
/* 254:    */   {
/* 255:341 */     List<Depreciacion> lista = new ArrayList();
/* 256:342 */     for (Depreciacion depreciacion : getActivoFijo().getListaDepreciacion()) {
/* 257:343 */       if ((depreciacion != null) && (!depreciacion.isEliminado()) && (!depreciacion.isIndicadorDepreciacionFiscal())) {
/* 258:344 */         lista.add(depreciacion);
/* 259:    */       }
/* 260:    */     }
/* 261:347 */     return lista;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public List<ActivoFijo> getListaActivoFijoRelacionado()
/* 265:    */   {
/* 266:356 */     List<ActivoFijo> lista = new ArrayList();
/* 267:357 */     for (ActivoFijo afr : getActivoFijo().getListaActivoFijoRelacionado()) {
/* 268:358 */       if (!afr.isEliminado()) {
/* 269:359 */         lista.add(afr);
/* 270:    */       }
/* 271:    */     }
/* 272:362 */     return lista;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void generarDepreciacion()
/* 276:    */   {
/* 277:366 */     this.servicioDepreciacion.generarDepreciacion(this.activoFijo);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void dateSelect()
/* 281:    */   {
/* 282:372 */     generarDepreciacion();
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String generarListaDetalleDepreciacionFiscal()
/* 286:    */   {
/* 287:383 */     this.listaDetalleDepreciacion = new ArrayList();
/* 288:384 */     this.totalDepreciacion = BigDecimal.ZERO;
/* 289:385 */     Depreciacion depreciacion = (Depreciacion)this.dtDepreciacionFiscal.getRowData();
/* 290:386 */     for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/* 291:387 */       if (!detalleDepreciacion.isEliminado())
/* 292:    */       {
/* 293:388 */         this.listaDetalleDepreciacion.add(detalleDepreciacion);
/* 294:389 */         this.totalDepreciacion = this.totalDepreciacion.add(detalleDepreciacion.getValor());
/* 295:    */       }
/* 296:    */     }
/* 297:392 */     return "";
/* 298:    */   }
/* 299:    */   
/* 300:    */   public String generarListaDetalleDepreciacionNIIF()
/* 301:    */   {
/* 302:402 */     this.listaDetalleDepreciacion = new ArrayList();
/* 303:403 */     this.totalDepreciacion = BigDecimal.ZERO;
/* 304:404 */     Depreciacion depreciacion = (Depreciacion)this.dtDepreciacionNIIF.getRowData();
/* 305:405 */     for (DetalleDepreciacion detalleDepreciacion : depreciacion.getListaDetalleDepreciacion()) {
/* 306:406 */       if (!detalleDepreciacion.isEliminado())
/* 307:    */       {
/* 308:407 */         this.listaDetalleDepreciacion.add(detalleDepreciacion);
/* 309:408 */         this.totalDepreciacion = this.totalDepreciacion.add(detalleDepreciacion.getValor());
/* 310:    */       }
/* 311:    */     }
/* 312:411 */     return "";
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String cargarProducto()
/* 316:    */   {
/* 317:420 */     this.activoFijo.setProducto(this.producto);
/* 318:421 */     return "";
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String limpiarProducto()
/* 322:    */   {
/* 323:430 */     this.activoFijo.setProducto(null);
/* 324:431 */     return "";
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void processUpload(FileUploadEvent event)
/* 328:    */   {
/* 329:    */     try
/* 330:    */     {
/* 331:445 */       String fileName = "act_" + AppUtil.getOrganizacion().getId() + "_" + getActivoFijo().getCodigo() + recuperaExtencion(event.getFile().getFileName());
/* 332:    */       
/* 333:447 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "activos");
/* 334:    */       
/* 335:449 */       File directorio = new File(uploadDir);
/* 336:    */       
/* 337:451 */       File file = new File(uploadDir + fileName);
/* 338:453 */       if (!directorio.exists()) {
/* 339:454 */         directorio.mkdirs();
/* 340:    */       }
/* 341:457 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 342:    */       
/* 343:459 */       this.activoFijo.setImagen(fileName);
/* 344:    */       
/* 345:461 */       FileOutputStream output = new FileOutputStream(file);
/* 346:463 */       while (input.available() != 0) {
/* 347:464 */         output.write(input.read());
/* 348:    */       }
/* 349:467 */       input.close();
/* 350:468 */       output.close();
/* 351:469 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_imagen"));
/* 352:    */     }
/* 353:    */     catch (Exception e)
/* 354:    */     {
/* 355:472 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/* 356:473 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   public String recuperaExtencion(String nombreArchivo)
/* 361:    */   {
/* 362:486 */     int mid = nombreArchivo.lastIndexOf(".");
/* 363:487 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void actualizarCategoriaActivo()
/* 367:    */   {
/* 368:498 */     CategoriaActivo categoriaActivo = this.servicioCategoriaActivo.cargarDetalle(this.activoFijo.getCategoriaActivo().getId());
/* 369:499 */     this.activoFijo.setCategoriaActivo(categoriaActivo);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public ActivoFijo getActivoFijo()
/* 373:    */   {
/* 374:512 */     if (this.activoFijo == null) {
/* 375:513 */       crearActivoFijo();
/* 376:    */     }
/* 377:515 */     return this.activoFijo;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 381:    */   {
/* 382:525 */     this.activoFijo = activoFijo;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public LazyDataModel<ActivoFijo> getListaActivoFijo()
/* 386:    */   {
/* 387:534 */     return this.listaActivoFijo;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void setListaActivoFijo(LazyDataModel<ActivoFijo> listaActivoFijo)
/* 391:    */   {
/* 392:544 */     this.listaActivoFijo = listaActivoFijo;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public DataTable getDtActivoFijo()
/* 396:    */   {
/* 397:553 */     return this.dtActivoFijo;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setDtActivoFijo(DataTable dtActivoFijo)
/* 401:    */   {
/* 402:563 */     this.dtActivoFijo = dtActivoFijo;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public List<CategoriaActivo> getListaCategoriaActivo()
/* 406:    */   {
/* 407:572 */     if (this.listaCategoriaActivo == null) {
/* 408:573 */       this.listaCategoriaActivo = this.servicioCategoriaActivo.obtenerListaCombo("nombre", true, null);
/* 409:    */     }
/* 410:575 */     return this.listaCategoriaActivo;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public void setListaCategoriaActivo(List<CategoriaActivo> listaCategoriaActivo)
/* 414:    */   {
/* 415:585 */     this.listaCategoriaActivo = listaCategoriaActivo;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List<MotivoBajaActivo> getListaMotivoBajaActivo()
/* 419:    */   {
/* 420:594 */     if (this.listaMotivoBajaActivo == null) {
/* 421:595 */       this.listaMotivoBajaActivo = this.servicioMotivoBajaActivo.obtenerListaCombo("nombre", true, null);
/* 422:    */     }
/* 423:597 */     return this.listaMotivoBajaActivo;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setListaMotivoBajaActivo(List<MotivoBajaActivo> listaMotivoBajaActivo)
/* 427:    */   {
/* 428:607 */     this.listaMotivoBajaActivo = listaMotivoBajaActivo;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public Producto getProducto()
/* 432:    */   {
/* 433:616 */     if (this.producto == null) {
/* 434:617 */       this.producto = new Producto();
/* 435:    */     }
/* 436:619 */     return this.producto;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void setProducto(Producto producto)
/* 440:    */   {
/* 441:629 */     this.producto = producto;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public Depreciacion getDepreciacionFiscal()
/* 445:    */   {
/* 446:638 */     return this.depreciacionFiscal;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public Depreciacion getDepreciacionNIIF()
/* 450:    */   {
/* 451:647 */     return this.depreciacionNIIF;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setDepreciacionNIIF(Depreciacion depreciacionNIIF)
/* 455:    */   {
/* 456:657 */     this.depreciacionNIIF = depreciacionNIIF;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void setDepreciacionFiscal(Depreciacion depreciacionFiscal)
/* 460:    */   {
/* 461:667 */     this.depreciacionFiscal = depreciacionFiscal;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public BigDecimal getTotalDepreciacion()
/* 465:    */   {
/* 466:676 */     return this.totalDepreciacion;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setTotalDepreciacion(BigDecimal totalDepreciacion)
/* 470:    */   {
/* 471:686 */     this.totalDepreciacion = totalDepreciacion;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public List<ActivoFijo> getListaActivoFijoPrincipal()
/* 475:    */   {
/* 476:697 */     if (this.listaActivoFijoPrincipal == null)
/* 477:    */     {
/* 478:698 */       this.listaActivoFijoPrincipal = new ArrayList();
/* 479:699 */       List<ActivoFijo> lista = this.servicioActivoFijo.obtenerListaCombo("nombre", true, null);
/* 480:700 */       for (ActivoFijo activoFijoAux : lista) {
/* 481:701 */         if (activoFijoAux.getId() != this.activoFijo.getId()) {
/* 482:702 */           this.listaActivoFijoPrincipal.add(activoFijoAux);
/* 483:    */         }
/* 484:    */       }
/* 485:    */     }
/* 486:706 */     return this.listaActivoFijoPrincipal;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public void setListaActivoFijoPrincipal(List<ActivoFijo> listaActivoFijoPrincipal)
/* 490:    */   {
/* 491:716 */     this.listaActivoFijoPrincipal = listaActivoFijoPrincipal;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public List<DetalleDepreciacion> getListaDetalleDepreciacion()
/* 495:    */   {
/* 496:725 */     if (this.listaDetalleDepreciacion == null) {
/* 497:726 */       this.listaDetalleDepreciacion = new ArrayList();
/* 498:    */     }
/* 499:728 */     return this.listaDetalleDepreciacion;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void setListaDetalleDepreciacion(List<DetalleDepreciacion> listaDetalleDepreciacion)
/* 503:    */   {
/* 504:738 */     this.listaDetalleDepreciacion = listaDetalleDepreciacion;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public DataTable getDtDepreciacionFiscal()
/* 508:    */   {
/* 509:747 */     return this.dtDepreciacionFiscal;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void setDtDepreciacionFiscal(DataTable dtDepreciacionFiscal)
/* 513:    */   {
/* 514:757 */     this.dtDepreciacionFiscal = dtDepreciacionFiscal;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public DataTable getDtDepreciacionNIIF()
/* 518:    */   {
/* 519:766 */     return this.dtDepreciacionNIIF;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void setDtDepreciacionNIIF(DataTable dtDepreciacionNIIF)
/* 523:    */   {
/* 524:776 */     this.dtDepreciacionNIIF = dtDepreciacionNIIF;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public DataTable getDtDetalleDepreciacion()
/* 528:    */   {
/* 529:785 */     return this.dtDetalleDepreciacion;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public void setDtDetalleDepreciacion(DataTable dtDetalleDepreciacion)
/* 533:    */   {
/* 534:795 */     this.dtDetalleDepreciacion = dtDetalleDepreciacion;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public DataTable getDtActivoFijoRelacionado()
/* 538:    */   {
/* 539:804 */     return this.dtActivoFijoRelacionado;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public void setDtActivoFijoRelacionado(DataTable dtActivoFijoRelacionado)
/* 543:    */   {
/* 544:814 */     this.dtActivoFijoRelacionado = dtActivoFijoRelacionado;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public ActivoFijo getActivoPrincipal()
/* 548:    */   {
/* 549:823 */     return this.activoPrincipal;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setActivoPrincipal(ActivoFijo activoPrincipal)
/* 553:    */   {
/* 554:833 */     this.activoPrincipal = activoPrincipal;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public List<Sucursal> getListaSucursal()
/* 558:    */   {
/* 559:837 */     if (this.listaSucursal == null) {
/* 560:838 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 561:    */     }
/* 562:840 */     return this.listaSucursal;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public List<Departamento> getListaDepartamento()
/* 566:    */   {
/* 567:844 */     if (this.listaDepartamento == null) {
/* 568:845 */       this.listaDepartamento = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 569:    */     }
/* 570:847 */     return this.listaDepartamento;
/* 571:    */   }
/* 572:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.configuracion.ActivoFijoBean
 * JD-Core Version:    0.7.0.1
 */