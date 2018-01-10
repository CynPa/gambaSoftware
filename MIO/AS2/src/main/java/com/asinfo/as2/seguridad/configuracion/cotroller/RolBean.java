/*   1:    */ package com.asinfo.as2.seguridad.configuracion.cotroller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.ProcesoOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadAccion;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadPermiso;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  13:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  14:    */ import com.asinfo.as2.seguridad.ServicioAccion;
/*  15:    */ import com.asinfo.as2.seguridad.ServicioPermiso;
/*  16:    */ import com.asinfo.as2.seguridad.ServicioProceso;
/*  17:    */ import com.asinfo.as2.seguridad.ServicioRol;
/*  18:    */ import com.asinfo.as2.seguridad.ServicioSistema;
/*  19:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Collection;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import javax.faces.model.SelectItem;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.primefaces.component.datatable.DataTable;
/*  33:    */ import org.primefaces.event.SelectEvent;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class RolBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @EJB
/*  42:    */   private ServicioRol servicioRol;
/*  43:    */   @EJB
/*  44:    */   private ServicioProceso servicioProceso;
/*  45:    */   @EJB
/*  46:    */   private ServicioAccion servicioAccion;
/*  47:    */   @EJB
/*  48:    */   private ServicioSistema servicioSistema;
/*  49:    */   @EJB
/*  50:    */   private ServicioOrganizacion servicioOrganizacion;
/*  51:    */   @EJB
/*  52:    */   private ServicioPermiso servicioPermiso;
/*  53:    */   private EntidadRol entidadRol;
/*  54:    */   private EntidadPermiso entidadPermiso;
/*  55:    */   private List<EntidadRol> listaEntidadRol;
/*  56:    */   private List<SelectItem> entidadRolItems;
/*  57:    */   private DataTable dtEntidadRol;
/*  58:    */   private DataTable dtPermiso;
/*  59:    */   private DataTable dtAccion;
/*  60:    */   private DataTable dtOrganizacionACopiar;
/*  61:    */   private boolean indicadorCopiar;
/*  62: 81 */   private List<ProcesoOrganizacion> listaProcesosNoAsignados = new ArrayList();
/*  63:    */   private ProcesoOrganizacion[] listaProcesosSeleccionados;
/*  64: 84 */   private List<EntidadAccion> listaAccionesNoAsignadas = new ArrayList();
/*  65:    */   private EntidadAccion[] listaAccionesSeleccionadas;
/*  66:    */   private List<EntidadSistema> listaSistema;
/*  67:    */   private EntidadSistema sistemaSeleccionado;
/*  68:    */   private List<Organizacion> listaOrganizacion;
/*  69:    */   private Organizacion organizacionSeleccionada;
/*  70:    */   private List<EntidadPermiso> listaPermiso;
/*  71:    */   private List<EntidadPermiso> listaPermisoFiltrado;
/*  72:    */   private List<EntidadAccion> listaAccionTotal;
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77: 97 */     List<EntidadSistema> lista = getListaSistema();
/*  78: 98 */     if (!lista.isEmpty()) {
/*  79: 99 */       this.sistemaSeleccionado = ((EntidadSistema)lista.get(0));
/*  80:    */     } else {
/*  81:101 */       this.sistemaSeleccionado = new EntidadSistema(-1, "No Definido");
/*  82:    */     }
/*  83:103 */     List<Organizacion> listaO = getListaOrganizacion();
/*  84:104 */     if (!listaO.isEmpty()) {
/*  85:105 */       this.organizacionSeleccionada = ((Organizacion)listaO.get(0));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String editar()
/*  90:    */   {
/*  91:116 */     if (this.entidadRol.getId() != 0)
/*  92:    */     {
/*  93:117 */       this.entidadRol = this.servicioRol.cargarDetalle(getEntidadRol().getId());
/*  94:118 */       getListaPermiso();
/*  95:119 */       setEditado(true);
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 100:    */     }
/* 101:123 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String guardar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:135 */       this.servicioRol.guardar(this.entidadRol);
/* 109:    */       
/* 110:137 */       cargarDatos();
/* 111:138 */       this.indicadorCopiar = false;
/* 112:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 117:142 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 118:    */     }
/* 119:144 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String eliminar()
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:155 */       this.servicioRol.eliminar(this.entidadRol);
/* 127:156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 128:    */     }
/* 129:    */     catch (Exception e)
/* 130:    */     {
/* 131:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 132:159 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/* 133:    */     }
/* 134:161 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String limpiar()
/* 138:    */   {
/* 139:171 */     this.entidadRol = new EntidadRol();
/* 140:172 */     this.entidadRol.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 141:173 */     this.entidadRol.setIdSucursal(AppUtil.getSucursal().getId());
/* 142:174 */     this.listaPermiso = null;
/* 143:175 */     return "";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String cargarDatos()
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:186 */       setEditado(false);
/* 151:187 */       Map<String, String> filters = new HashMap();
/* 152:188 */       filters.put("idOrganizacion", "!=-1");
/* 153:189 */       this.listaEntidadRol = this.servicioRol.obtenerListaCombo("nombre", false, filters);
/* 154:190 */       limpiar();
/* 155:    */     }
/* 156:    */     catch (Exception e)
/* 157:    */     {
/* 158:192 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 159:193 */       LOG.error("ERROR AL CARGAR LOS DATOS", e);
/* 160:    */     }
/* 161:195 */     return null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String cargarPermisosNoAsignados()
/* 165:    */   {
/* 166:204 */     this.listaProcesosSeleccionados = null;
/* 167:205 */     HashMap<Integer, ProcesoOrganizacion> procesos = new HashMap();
/* 168:206 */     Map<String, String> filtros = new HashMap();
/* 169:207 */     filtros.put("entidadProceso.sistema.idSistema", String.valueOf(this.sistemaSeleccionado.getIdSistema()));
/* 170:    */     
/* 171:209 */     filtros.put("entidadProceso.activo", "true");
/* 172:210 */     for (ProcesoOrganizacion procesoOrganizacion : this.servicioProceso.obtenerListaProcesoOrganizacion(this.sistemaSeleccionado, this.organizacionSeleccionada)) {
/* 173:211 */       procesos.put(Integer.valueOf(procesoOrganizacion.getId()), procesoOrganizacion);
/* 174:    */     }
/* 175:214 */     for (EntidadPermiso entidadPermiso : getEntidadRol().getListaPermiso()) {
/* 176:215 */       if (!entidadPermiso.isEliminado()) {
/* 177:216 */         procesos.remove(Integer.valueOf(entidadPermiso.getProcesoOrganizacion().getId()));
/* 178:    */       }
/* 179:    */     }
/* 180:219 */     this.listaProcesosNoAsignados = new ArrayList(procesos.values());
/* 181:220 */     return "";
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String eliminarPermiso(EntidadPermiso entidadPermiso)
/* 185:    */   {
/* 186:229 */     entidadPermiso.setEliminado(true);
/* 187:231 */     for (EntidadAccion entidadAccion : entidadPermiso.getListaAccion()) {
/* 188:232 */       entidadAccion.setEliminado(true);
/* 189:    */     }
/* 190:235 */     this.listaPermiso.remove(entidadPermiso);
/* 191:236 */     this.listaPermisoFiltrado = null;
/* 192:237 */     this.dtPermiso.reset();
/* 193:    */     
/* 194:239 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String agregarPermiso()
/* 198:    */   {
/* 199:248 */     if (this.listaProcesosSeleccionados != null)
/* 200:    */     {
/* 201:249 */       EntidadPermiso entidadPermiso = null;
/* 202:250 */       for (ProcesoOrganizacion procesoOrganizacion : this.listaProcesosSeleccionados)
/* 203:    */       {
/* 204:251 */         entidadPermiso = new EntidadPermiso();
/* 205:252 */         entidadPermiso.setProcesoOrganizacion(procesoOrganizacion);
/* 206:253 */         entidadPermiso.setEntidadRol(getEntidadRol());
/* 207:254 */         entidadPermiso.setListaAccion(new ArrayList());
/* 208:    */         
/* 209:256 */         getEntidadRol().getListaPermiso().add(entidadPermiso);
/* 210:257 */         getListaPermiso().add(entidadPermiso);
/* 211:    */       }
/* 212:260 */       this.entidadPermiso = entidadPermiso;
/* 213:    */     }
/* 214:262 */     cargarPermisosNoAsignados();
/* 215:263 */     return "";
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String cargarAccionNoAsignada()
/* 219:    */   {
/* 220:272 */     if (this.listaAccionTotal == null)
/* 221:    */     {
/* 222:273 */       Map<String, String> filters = new HashMap();
/* 223:274 */       filters.put("idOrganizacion", "!=-1");
/* 224:275 */       this.listaAccionTotal = this.servicioAccion.obtenerListaCombo("", false, filters);
/* 225:    */     }
/* 226:278 */     HashMap<Integer, EntidadAccion> acciones = new HashMap();
/* 227:279 */     for (EntidadAccion entidadAccion : this.listaAccionTotal) {
/* 228:280 */       acciones.put(Integer.valueOf(entidadAccion.getId()), entidadAccion);
/* 229:    */     }
/* 230:283 */     this.listaAccionesSeleccionadas = null;
/* 231:284 */     setEntidadPermiso((EntidadPermiso)this.dtPermiso.getRowData());
/* 232:286 */     for (EntidadAccion entidadAccion : getEntidadPermiso().getListaAccion()) {
/* 233:287 */       if (!entidadAccion.isEliminado()) {
/* 234:288 */         acciones.remove(Integer.valueOf(entidadAccion.getId()));
/* 235:    */       }
/* 236:    */     }
/* 237:292 */     this.listaAccionesNoAsignadas = new ArrayList(acciones.values());
/* 238:293 */     return "";
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String agregarAccion()
/* 242:    */   {
/* 243:302 */     if (this.listaAccionesSeleccionadas != null) {
/* 244:303 */       for (EntidadAccion entidadAccion : this.listaAccionesSeleccionadas)
/* 245:    */       {
/* 246:304 */         getEntidadPermiso().getListaAccion().add(entidadAccion);
/* 247:305 */         getListaAccionesNoAsignadas().remove(entidadAccion);
/* 248:    */       }
/* 249:    */     }
/* 250:308 */     return "";
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void agregarAcciones()
/* 254:    */   {
/* 255:313 */     for (EntidadAccion ea : getEntidadPermiso().getListaAccion()) {
/* 256:314 */       if (ea.isAsignarTodos())
/* 257:    */       {
/* 258:315 */         for (EntidadPermiso ep : getListaPermiso())
/* 259:    */         {
/* 260:316 */           boolean encontro = false;
/* 261:317 */           for (EntidadAccion eap : ep.getListaAccion()) {
/* 262:318 */             if (eap.getId() == ea.getId())
/* 263:    */             {
/* 264:319 */               encontro = true;
/* 265:320 */               break;
/* 266:    */             }
/* 267:    */           }
/* 268:323 */           if (!encontro) {
/* 269:324 */             ep.getListaAccion().add(ea);
/* 270:    */           }
/* 271:    */         }
/* 272:327 */         ea.setAsignarTodos(false);
/* 273:    */       }
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String eliminarAccion()
/* 278:    */   {
/* 279:337 */     EntidadAccion entidadAccion = (EntidadAccion)this.dtAccion.getRowData();
/* 280:    */     
/* 281:    */ 
/* 282:340 */     getEntidadPermiso().getListaAccion().remove(entidadAccion);
/* 283:341 */     getListaAccionesNoAsignadas().add(entidadAccion);
/* 284:342 */     return "";
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String copiar()
/* 288:    */   {
/* 289:346 */     if (this.entidadRol != null)
/* 290:    */     {
/* 291:347 */       EntidadRol auxEntidadRol = this.servicioRol.cargarDetalle(this.entidadRol.getIdRol());
/* 292:348 */       this.entidadRol = this.servicioRol.copiarRol(auxEntidadRol);
/* 293:349 */       for (EntidadPermiso entidadPermiso : this.entidadRol.getListaPermiso())
/* 294:    */       {
/* 295:350 */         entidadPermiso.setProcesoOrganizacion(entidadPermiso.getProcesoOrganizacion());
/* 296:351 */         List<EntidadAccion> listaEntidadAccion = new ArrayList();
/* 297:352 */         for (EntidadAccion entidadAccion : entidadPermiso.getListaAccion())
/* 298:    */         {
/* 299:353 */           entidadAccion = this.servicioAccion.buscarPorId(Integer.valueOf(entidadAccion.getIdAccion()));
/* 300:354 */           listaEntidadAccion.add(entidadAccion);
/* 301:    */         }
/* 302:356 */         entidadPermiso.setListaAccion(listaEntidadAccion);
/* 303:    */       }
/* 304:358 */       setEditado(true);
/* 305:    */     }
/* 306:360 */     return "";
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String copiarPermisos()
/* 310:    */   {
/* 311:365 */     for (Organizacion organizacion : this.listaOrganizacion) {
/* 312:367 */       if (organizacion.isIndicadorCopiar()) {
/* 313:368 */         this.servicioPermiso.copiarPermisos(organizacion, this.organizacionSeleccionada, true);
/* 314:    */       }
/* 315:    */     }
/* 316:372 */     return "";
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void onRowSelect(SelectEvent event)
/* 320:    */   {
/* 321:381 */     this.entidadRol = ((EntidadRol)event.getObject());
/* 322:    */   }
/* 323:    */   
/* 324:    */   public EntidadRol getEntidadRol()
/* 325:    */   {
/* 326:385 */     if (this.entidadRol == null) {
/* 327:386 */       this.entidadRol = new EntidadRol();
/* 328:    */     }
/* 329:388 */     return this.entidadRol;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setEntidadRol(EntidadRol entidadRol)
/* 333:    */   {
/* 334:392 */     this.entidadRol = entidadRol;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public List<EntidadRol> getListaEntidadRol()
/* 338:    */   {
/* 339:396 */     if (this.listaEntidadRol == null) {
/* 340:397 */       cargarDatos();
/* 341:    */     }
/* 342:399 */     return this.listaEntidadRol;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setListaEntidadRol(List<EntidadRol> listaEntidadRol)
/* 346:    */   {
/* 347:403 */     this.listaEntidadRol = listaEntidadRol;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public List<SelectItem> getEntidadRolItems()
/* 351:    */   {
/* 352:407 */     return this.entidadRolItems;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setEntidadRolItems(List<SelectItem> entidadRolItems)
/* 356:    */   {
/* 357:411 */     this.entidadRolItems = entidadRolItems;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public DataTable getDtEntidadRol()
/* 361:    */   {
/* 362:415 */     return this.dtEntidadRol;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setDtEntidadRol(DataTable dtEntidadRol)
/* 366:    */   {
/* 367:419 */     this.dtEntidadRol = dtEntidadRol;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<ProcesoOrganizacion> getListaProcesosNoAsignados()
/* 371:    */   {
/* 372:428 */     return this.listaProcesosNoAsignados;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setListaProcesosNoAsignados(List<ProcesoOrganizacion> listaProcesosNoAsignados)
/* 376:    */   {
/* 377:438 */     this.listaProcesosNoAsignados = listaProcesosNoAsignados;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public ProcesoOrganizacion[] getListaProcesosSeleccionados()
/* 381:    */   {
/* 382:447 */     return this.listaProcesosSeleccionados;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setListaProcesosSeleccionados(ProcesoOrganizacion[] listaProcesosSeleccionados)
/* 386:    */   {
/* 387:457 */     this.listaProcesosSeleccionados = listaProcesosSeleccionados;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<EntidadPermiso> getListaPermiso()
/* 391:    */   {
/* 392:467 */     this.listaPermiso = new ArrayList();
/* 393:468 */     for (EntidadPermiso entidadPermiso : getEntidadRol().getListaPermiso()) {
/* 394:469 */       if ((!entidadPermiso.isEliminado()) && 
/* 395:470 */         (this.sistemaSeleccionado.equals(entidadPermiso.getProcesoOrganizacion().getEntidadProceso().getSistema())) && 
/* 396:471 */         (this.organizacionSeleccionada.getId() == entidadPermiso.getProcesoOrganizacion().getOrganizacion().getId())) {
/* 397:472 */         this.listaPermiso.add(entidadPermiso);
/* 398:    */       }
/* 399:    */     }
/* 400:477 */     return this.listaPermiso;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void setListaPermiso(List<EntidadPermiso> listaPermiso)
/* 404:    */   {
/* 405:481 */     this.listaPermiso = listaPermiso;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public List<EntidadAccion> getListaAccion()
/* 409:    */   {
/* 410:490 */     List<EntidadAccion> lista = new ArrayList();
/* 411:492 */     for (EntidadAccion entidadAccion : getEntidadPermiso().getListaAccion()) {
/* 412:493 */       if (!entidadAccion.isEliminado()) {
/* 413:494 */         lista.add(entidadAccion);
/* 414:    */       }
/* 415:    */     }
/* 416:498 */     return lista;
/* 417:    */   }
/* 418:    */   
/* 419:    */   public DataTable getDtPermiso()
/* 420:    */   {
/* 421:507 */     return this.dtPermiso;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setDtPermiso(DataTable dtPermiso)
/* 425:    */   {
/* 426:517 */     this.dtPermiso = dtPermiso;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public List<EntidadAccion> getListaAccionesNoAsignadas()
/* 430:    */   {
/* 431:526 */     if (this.listaAccionesNoAsignadas == null) {
/* 432:527 */       this.listaAccionesNoAsignadas = new ArrayList();
/* 433:    */     }
/* 434:529 */     return this.listaAccionesNoAsignadas;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void setListaAccionesNoAsignadas(List<EntidadAccion> listaAccionesNoAsignadas)
/* 438:    */   {
/* 439:539 */     this.listaAccionesNoAsignadas = listaAccionesNoAsignadas;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public EntidadAccion[] getListaAccionesSeleccionadas()
/* 443:    */   {
/* 444:548 */     return this.listaAccionesSeleccionadas;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void setListaAccionesSeleccionadas(EntidadAccion[] listaAccionesSeleccionadas)
/* 448:    */   {
/* 449:558 */     this.listaAccionesSeleccionadas = listaAccionesSeleccionadas;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public EntidadPermiso getEntidadPermiso()
/* 453:    */   {
/* 454:567 */     if (this.entidadPermiso == null) {
/* 455:568 */       this.entidadPermiso = new EntidadPermiso();
/* 456:    */     }
/* 457:570 */     return this.entidadPermiso;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public void setEntidadPermiso(EntidadPermiso entidadPermiso)
/* 461:    */   {
/* 462:580 */     this.entidadPermiso = entidadPermiso;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public DataTable getDtAccion()
/* 466:    */   {
/* 467:589 */     return this.dtAccion;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void setDtAccion(DataTable dtAccion)
/* 471:    */   {
/* 472:599 */     this.dtAccion = dtAccion;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public EntidadSistema getSistemaSeleccionado()
/* 476:    */   {
/* 477:608 */     return this.sistemaSeleccionado;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setSistemaSeleccionado(EntidadSistema sistemaSeleccionado)
/* 481:    */   {
/* 482:618 */     this.listaPermiso = null;
/* 483:619 */     this.sistemaSeleccionado = sistemaSeleccionado;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public List<EntidadSistema> getListaSistema()
/* 487:    */   {
/* 488:628 */     if (this.listaSistema == null) {
/* 489:629 */       this.listaSistema = this.servicioSistema.obtenerListaCombo("idSistema", true, null);
/* 490:    */     }
/* 491:631 */     return this.listaSistema;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setListaSistema(List<EntidadSistema> listaSistema)
/* 495:    */   {
/* 496:641 */     this.listaSistema = listaSistema;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public List<EntidadPermiso> getListaPermisoFiltrado()
/* 500:    */   {
/* 501:645 */     return this.listaPermisoFiltrado;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public void setListaPermisoFiltrado(List<EntidadPermiso> listaPermisoFiltrado)
/* 505:    */   {
/* 506:649 */     this.listaPermisoFiltrado = listaPermisoFiltrado;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public List<Organizacion> getListaOrganizacion()
/* 510:    */   {
/* 511:653 */     if (this.listaOrganizacion == null) {
/* 512:654 */       this.listaOrganizacion = this.servicioOrganizacion.obtenerOrganizacionPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 513:    */     }
/* 514:656 */     return this.listaOrganizacion;
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void setListaOrganizacion(List<Organizacion> listaOrganizacion)
/* 518:    */   {
/* 519:660 */     this.listaOrganizacion = listaOrganizacion;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public Organizacion getOrganizacionSeleccionada()
/* 523:    */   {
/* 524:664 */     return this.organizacionSeleccionada;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setOrganizacionSeleccionada(Organizacion organizacionSeleccionada)
/* 528:    */   {
/* 529:668 */     this.listaPermiso = null;
/* 530:669 */     this.organizacionSeleccionada = organizacionSeleccionada;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public DataTable getDtOrganizacionACopiar()
/* 534:    */   {
/* 535:673 */     return this.dtOrganizacionACopiar;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public void setDtOrganizacionACopiar(DataTable dtOrganizacionACopiar)
/* 539:    */   {
/* 540:677 */     this.dtOrganizacionACopiar = dtOrganizacionACopiar;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public boolean isIndicadorCopiar()
/* 544:    */   {
/* 545:681 */     return this.indicadorCopiar;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public void setIndicadorCopiar(boolean indicadorCopiar)
/* 549:    */   {
/* 550:685 */     this.indicadorCopiar = indicadorCopiar;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public void resetFiltros()
/* 554:    */   {
/* 555:690 */     this.listaPermisoFiltrado = null;
/* 556:691 */     this.dtPermiso.reset();
/* 557:    */   }
/* 558:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.configuracion.cotroller.RolBean
 * JD-Core Version:    0.7.0.1
 */