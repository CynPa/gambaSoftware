/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.CatalogosACopiar;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   7:    */ import com.asinfo.as2.controller.LanguageController;
/*   8:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   9:    */ import com.asinfo.as2.entities.Atributo;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  12:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  13:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*  14:    */ import com.asinfo.as2.enumeraciones.CatalogosACopiarEnum;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  18:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.util.RutaArchivo;
/*  21:    */ import com.asinfo.as2.utils.EjbUtil;
/*  22:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  23:    */ import com.asinfo.as2.utils.encriptacion.EncriptarInformacion;
/*  24:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  25:    */ import java.io.BufferedInputStream;
/*  26:    */ import java.io.File;
/*  27:    */ import java.io.FileInputStream;
/*  28:    */ import java.io.FileOutputStream;
/*  29:    */ import java.io.InputStream;
/*  30:    */ import java.io.PrintStream;
/*  31:    */ import java.security.KeyStore;
/*  32:    */ import java.util.ArrayList;
/*  33:    */ import java.util.Collections;
/*  34:    */ import java.util.Comparator;
/*  35:    */ import java.util.HashMap;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.annotation.PostConstruct;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ViewScoped;
/*  42:    */ import javax.faces.model.SelectItem;
/*  43:    */ import org.apache.log4j.Logger;
/*  44:    */ import org.primefaces.component.datatable.DataTable;
/*  45:    */ import org.primefaces.event.FileUploadEvent;
/*  46:    */ import org.primefaces.event.SelectEvent;
/*  47:    */ import org.primefaces.model.LazyDataModel;
/*  48:    */ import org.primefaces.model.SortOrder;
/*  49:    */ import org.primefaces.model.UploadedFile;
/*  50:    */ 
/*  51:    */ @ManagedBean
/*  52:    */ @ViewScoped
/*  53:    */ public class OrganizacionBean
/*  54:    */   extends PageControllerAS2
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 7264958154926626725L;
/*  57:    */   @EJB
/*  58:    */   private ServicioOrganizacion servicioOrganizacion;
/*  59:    */   @EJB
/*  60:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  61:    */   @EJB
/*  62:    */   private ServicioAtributo servicioAtributo;
/*  63:    */   private Organizacion organizacion;
/*  64:    */   private LazyDataModel<Organizacion> listaOrganizacion;
/*  65:    */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  66:    */   private List<Atributo> listaAtributo;
/*  67:    */   private List<CatalogosACopiar> listaCatalogosACopiar;
/*  68:    */   private List<Organizacion> listaOrganizacionCopiar;
/*  69:    */   private List<SelectItem> listaTipoOrganizacion;
/*  70:    */   private Organizacion organizacionCopiar;
/*  71:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  72:    */   private String confirmarClave;
/*  73:    */   private String pkcs12_password;
/*  74:    */   private DataTable dtOrganizacion;
/*  75:    */   private DataTable dtCatalogosACopiar;
/*  76:    */   private String allowTypes;
/*  77:    */   private String sizeLimit;
/*  78:    */   private String tipoFileUpload;
/*  79:    */   
/*  80:    */   @PostConstruct
/*  81:    */   public void init()
/*  82:    */   {
/*  83:112 */     this.listaOrganizacion = new LazyDataModel()
/*  84:    */     {
/*  85:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  86:    */       
/*  87:    */       public List<Organizacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  88:    */       {
/*  89:119 */         List<Organizacion> lista = new ArrayList();
/*  90:120 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  91:    */         try
/*  92:    */         {
/*  93:124 */           if (!AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador()) {
/*  94:125 */             lista = OrganizacionBean.this.servicioOrganizacion.obtenerOrganizacionPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario(), startIndex, pageSize, sortField, ordenar);
/*  95:    */           } else {
/*  96:127 */             lista = OrganizacionBean.this.servicioOrganizacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  97:    */           }
/*  98:    */         }
/*  99:    */         catch (Exception e)
/* 100:    */         {
/* 101:131 */           e.printStackTrace();
/* 102:    */         }
/* 103:133 */         if (!AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador()) {
/* 104:134 */           OrganizacionBean.this.listaOrganizacion.setRowCount(AppUtil.getUsuarioEnSesion().getListaUsuarioOrganizacion().size());
/* 105:    */         } else {
/* 106:136 */           OrganizacionBean.this.listaOrganizacion.setRowCount(OrganizacionBean.this.servicioOrganizacion.contarPorCriterio(filters));
/* 107:    */         }
/* 108:138 */         return lista;
/* 109:    */       }
/* 110:    */     };
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String editar()
/* 114:    */   {
/* 115:150 */     if ((getOrganizacion() != null) && (getOrganizacion().getId() > 0))
/* 116:    */     {
/* 117:151 */       this.organizacion = this.servicioOrganizacion.cargarDetalle(getOrganizacion().getIdOrganizacion());
/* 118:152 */       this.organizacionConfiguracion = this.organizacion.getOrganizacionConfiguracion();
/* 119:154 */       if ((this.organizacionConfiguracion.getPkcs12Password() == null) || (this.organizacionConfiguracion.getPkcs12Password().isEmpty()))
/* 120:    */       {
/* 121:    */         try
/* 122:    */         {
/* 123:157 */           this.pkcs12_password = EjbUtil.obtenerValorArchivoProperties("clave", getPathFirma(), "clave_firma_electronica.properties");
/* 124:158 */           this.organizacionConfiguracion.setPkcs12Password(this.pkcs12_password);
/* 125:159 */           this.confirmarClave = this.pkcs12_password;
/* 126:    */         }
/* 127:    */         catch (Exception localException) {}
/* 128:    */       }
/* 129:    */       else
/* 130:    */       {
/* 131:165 */         this.pkcs12_password = this.organizacionConfiguracion.getPkcs12Password();
/* 132:166 */         this.confirmarClave = this.pkcs12_password;
/* 133:    */       }
/* 134:168 */       this.organizacionCopiar = null;
/* 135:169 */       setEditado(true);
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:171 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 140:    */     }
/* 141:174 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String guardar()
/* 145:    */   {
/* 146:184 */     if (((this.organizacionConfiguracion.getPkcs12Password() == null) && (this.confirmarClave == null)) || 
/* 147:185 */       (this.organizacionConfiguracion.getPkcs12Password().equals(this.confirmarClave))) {
/* 148:    */       try
/* 149:    */       {
/* 150:187 */         boolean error = false;
/* 151:188 */         if ((this.organizacionConfiguracion.getPkcs12Password() != null) && (!this.organizacionConfiguracion.getPkcs12Password().isEmpty()) && 
/* 152:189 */           (isExisteKeyP12()))
/* 153:    */         {
/* 154:190 */           KeyStore ks = KeyStore.getInstance("PKCS12");
/* 155:    */           try
/* 156:    */           {
/* 157:192 */             ks.load(new FileInputStream(getPathFirma() + "firma_electronica.p12"), this.organizacionConfiguracion.getPkcs12Password()
/* 158:193 */               .toCharArray());
/* 159:    */           }
/* 160:    */           catch (Exception e)
/* 161:    */           {
/* 162:195 */             error = true;
/* 163:196 */             addErrorMessage(getLanguageController().getMensaje("msgs_error_clave_p12"));
/* 164:    */           }
/* 165:    */         }
/* 166:199 */         if (!error)
/* 167:    */         {
/* 168:200 */           this.organizacion.setOrganizacionConfiguracion(this.organizacionConfiguracion);
/* 169:201 */           this.servicioOrganizacion.guardar(getOrganizacion(), this.listaCatalogosACopiar, this.organizacionCopiar);
/* 170:202 */           limpiar();
/* 171:203 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 172:204 */           setEditado(false);
/* 173:205 */           cargarDatos();
/* 174:    */         }
/* 175:    */       }
/* 176:    */       catch (ExcepcionAS2 e)
/* 177:    */       {
/* 178:208 */         e.printStackTrace();
/* 179:209 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 180:    */       }
/* 181:    */       catch (ExcepcionAS2Identification e)
/* 182:    */       {
/* 183:211 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 184:212 */         e.printStackTrace();
/* 185:    */       }
/* 186:    */       catch (Exception e)
/* 187:    */       {
/* 188:214 */         e.printStackTrace();
/* 189:215 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 190:216 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 191:    */       }
/* 192:    */     } else {
/* 193:220 */       addErrorMessage("Las claves no coinciden");
/* 194:    */     }
/* 195:222 */     return "";
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String eliminar()
/* 199:    */   {
/* 200:    */     try
/* 201:    */     {
/* 202:234 */       this.servicioOrganizacion.eliminar(getOrganizacion());
/* 203:    */       
/* 204:236 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 205:    */       
/* 206:238 */       cargarDatos();
/* 207:    */     }
/* 208:    */     catch (Exception e)
/* 209:    */     {
/* 210:241 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 211:242 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 212:    */     }
/* 213:245 */     return "";
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String limpiar()
/* 217:    */   {
/* 218:255 */     this.organizacion = null;
/* 219:256 */     this.organizacionConfiguracion = null;
/* 220:257 */     this.organizacionCopiar = null;
/* 221:258 */     this.confirmarClave = null;
/* 222:    */     
/* 223:260 */     return "";
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String cargarDatos()
/* 227:    */   {
/* 228:273 */     return "";
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String crear()
/* 232:    */   {
/* 233:283 */     limpiar();
/* 234:284 */     String numeroEmpresasEncriptado = "";
/* 235:285 */     Long numeroOrganizacionesMaximas = Long.valueOf(0L);
/* 236:286 */     Long numeroOrganizacionesExistentes = Long.valueOf(0L);
/* 237:    */     try
/* 238:    */     {
/* 239:288 */       numeroEmpresasEncriptado = FuncionesUtiles.leerArchivoConfiguracion("numeroOrganizacionesMaximas");
/* 240:289 */       numeroOrganizacionesMaximas = Long.valueOf(Long.parseLong(EncriptarInformacion.decrypt(numeroEmpresasEncriptado)));
/* 241:290 */       numeroOrganizacionesExistentes = this.servicioOrganizacion.contarNumeroOrganizaciones();
/* 242:291 */       if (numeroOrganizacionesExistentes.equals(numeroOrganizacionesMaximas))
/* 243:    */       {
/* 244:292 */         addErrorMessage(getLanguageController().getMensaje("msg_error_numero_organizaciones"));
/* 245:293 */         setEditado(false);
/* 246:    */       }
/* 247:    */       else
/* 248:    */       {
/* 249:295 */         this.organizacion = new Organizacion();
/* 250:296 */         this.organizacion.setTipoIdentificacion(new TipoIdentificacion());
/* 251:297 */         this.organizacion.setTipoIdentificacionContador(new TipoIdentificacion());
/* 252:298 */         this.organizacion.setTipoIdentificacionRepresentante(new TipoIdentificacion());
/* 253:299 */         this.organizacionCopiar = new Organizacion();
/* 254:300 */         this.organizacion.setOrganizacionConfiguracion(new OrganizacionConfiguracion());
/* 255:301 */         this.organizacionConfiguracion = this.organizacion.getOrganizacionConfiguracion();
/* 256:302 */         setEditado(true);
/* 257:    */       }
/* 258:    */     }
/* 259:    */     catch (ExcepcionAS2 e)
/* 260:    */     {
/* 261:305 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 262:306 */       setEditado(false);
/* 263:    */     }
/* 264:    */     catch (Exception e)
/* 265:    */     {
/* 266:308 */       addErrorMessage(getLanguageController().getMensaje("msg_error_parametro_numero_organizaciones"));
/* 267:309 */       setEditado(false);
/* 268:    */     }
/* 269:312 */     return "";
/* 270:    */   }
/* 271:    */   
/* 272:    */   public List<Atributo> getListaAtributo()
/* 273:    */   {
/* 274:316 */     if (this.listaAtributo == null)
/* 275:    */     {
/* 276:317 */       HashMap<String, String> filters = new HashMap();
/* 277:318 */       filters.put("indicadorInstancia", "true");
/* 278:319 */       this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/* 279:    */     }
/* 280:321 */     return this.listaAtributo;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setListaAtributo(List<Atributo> listaAtributo)
/* 284:    */   {
/* 285:325 */     this.listaAtributo = listaAtributo;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String recuperaExtencion(String nombreArchivo)
/* 289:    */   {
/* 290:336 */     int mid = nombreArchivo.lastIndexOf(".");
/* 291:337 */     return "." + nombreArchivo.substring(mid + 1, nombreArchivo.length());
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void onRowSelect(SelectEvent event)
/* 295:    */   {
/* 296:346 */     this.organizacion = ((Organizacion)event.getObject());
/* 297:    */   }
/* 298:    */   
/* 299:    */   public Organizacion getOrganizacion()
/* 300:    */   {
/* 301:355 */     return this.organizacion;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setOrganizacion(Organizacion organizacion)
/* 305:    */   {
/* 306:365 */     this.organizacion = organizacion;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public LazyDataModel<Organizacion> getListaOrganizacion()
/* 310:    */   {
/* 311:374 */     return this.listaOrganizacion;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setListaOrganizacion(LazyDataModel<Organizacion> listaOrganizacion)
/* 315:    */   {
/* 316:384 */     this.listaOrganizacion = listaOrganizacion;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public DataTable getDtOrganizacion()
/* 320:    */   {
/* 321:393 */     return this.dtOrganizacion;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setDtOrganizacion(DataTable dtOrganizacion)
/* 325:    */   {
/* 326:403 */     this.dtOrganizacion = dtOrganizacion;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 330:    */   {
/* 331:412 */     if (this.listaTipoIdentificacion == null) {
/* 332:413 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 333:    */     }
/* 334:415 */     return this.listaTipoIdentificacion;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 338:    */   {
/* 339:425 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public Organizacion getOrganizacionCopiar()
/* 343:    */   {
/* 344:434 */     return this.organizacionCopiar;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void setOrganizacionCopiar(Organizacion organizacionCopiar)
/* 348:    */   {
/* 349:444 */     this.organizacionCopiar = organizacionCopiar;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public List<Organizacion> getListaOrganizacionCopiar()
/* 353:    */   {
/* 354:453 */     if (this.listaOrganizacionCopiar == null) {
/* 355:455 */       if (AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador())
/* 356:    */       {
/* 357:456 */         this.listaOrganizacionCopiar = this.servicioOrganizacion.obtenerListaCombo(null, false, null);
/* 358:    */       }
/* 359:    */       else
/* 360:    */       {
/* 361:458 */         this.listaOrganizacionCopiar = new ArrayList();
/* 362:459 */         List<UsuarioOrganizacion> listaUsuarioOrganizacion = AppUtil.getUsuarioEnSesion().getListaUsuarioOrganizacion();
/* 363:460 */         for (UsuarioOrganizacion usuarioOrganizacion : listaUsuarioOrganizacion) {
/* 364:461 */           this.listaOrganizacionCopiar.add(usuarioOrganizacion.getOrganizacion());
/* 365:    */         }
/* 366:    */       }
/* 367:    */     }
/* 368:466 */     Collections.sort(this.listaOrganizacionCopiar, new Comparator()
/* 369:    */     {
/* 370:    */       public int compare(Organizacion o1, Organizacion o2)
/* 371:    */       {
/* 372:470 */         return o1.getRazonSocial().compareTo(o2.getRazonSocial());
/* 373:    */       }
/* 374:473 */     });
/* 375:474 */     return this.listaOrganizacionCopiar;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public void setListaOrganizacionCopiar(List<Organizacion> listaOrganizacionCopiar)
/* 379:    */   {
/* 380:484 */     this.listaOrganizacionCopiar = listaOrganizacionCopiar;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public List<CatalogosACopiar> getListaCatalogosACopiar()
/* 384:    */   {
/* 385:493 */     if (this.listaCatalogosACopiar == null)
/* 386:    */     {
/* 387:494 */       this.listaCatalogosACopiar = new ArrayList();
/* 388:495 */       for (CatalogosACopiarEnum catalogos : CatalogosACopiarEnum.values()) {
/* 389:496 */         this.listaCatalogosACopiar.add(new CatalogosACopiar(catalogos, catalogos.isIndicadorObligatorio()));
/* 390:    */       }
/* 391:    */     }
/* 392:499 */     Collections.sort(this.listaCatalogosACopiar, new Comparator()
/* 393:    */     {
/* 394:    */       public int compare(CatalogosACopiar o1, CatalogosACopiar o2)
/* 395:    */       {
/* 396:502 */         return o1.getCatalogosACopiar().getNombre().compareTo(o2.getCatalogosACopiar().getNombre());
/* 397:    */       }
/* 398:504 */     });
/* 399:505 */     return this.listaCatalogosACopiar;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setListaCatalogosACopiar(List<CatalogosACopiar> listaCatalogosACopiar)
/* 403:    */   {
/* 404:515 */     this.listaCatalogosACopiar = listaCatalogosACopiar;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public DataTable getDtCatalogosACopiar()
/* 408:    */   {
/* 409:524 */     return this.dtCatalogosACopiar;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setDtCatalogosACopiar(DataTable dtCatalogosACopiar)
/* 413:    */   {
/* 414:534 */     this.dtCatalogosACopiar = dtCatalogosACopiar;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public List<SelectItem> getListaTipoOrganizacion()
/* 418:    */   {
/* 419:543 */     if (this.listaTipoOrganizacion == null)
/* 420:    */     {
/* 421:544 */       this.listaTipoOrganizacion = new ArrayList();
/* 422:545 */       SelectItem item = null;
/* 423:546 */       for (TipoOrganizacion tipoOrganizacion : TipoOrganizacion.values())
/* 424:    */       {
/* 425:547 */         item = new SelectItem(tipoOrganizacion, tipoOrganizacion.getNombre());
/* 426:548 */         this.listaTipoOrganizacion.add(item);
/* 427:    */       }
/* 428:    */     }
/* 429:551 */     return this.listaTipoOrganizacion;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setListaTipoOrganizacion(List<SelectItem> listaTipoOrganizacion)
/* 433:    */   {
/* 434:561 */     this.listaTipoOrganizacion = listaTipoOrganizacion;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 438:    */   {
/* 439:570 */     if (this.organizacionConfiguracion == null)
/* 440:    */     {
/* 441:571 */       this.organizacionConfiguracion = new OrganizacionConfiguracion();
/* 442:572 */       this.organizacionConfiguracion.setTipoOrganizacion(TipoOrganizacion.TIPO_ORGANIZACION_GENERAL);
/* 443:573 */       this.organizacionConfiguracion.setOrganizacion(this.organizacion);
/* 444:    */     }
/* 445:575 */     return this.organizacionConfiguracion;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 449:    */   {
/* 450:585 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void processUploadFichero(FileUploadEvent eventFichero)
/* 454:    */   {
/* 455:589 */     if (this.tipoFileUpload.equals("logoOrganizacion")) {
/* 456:590 */       processUploadLogo(eventFichero);
/* 457:591 */     } else if (this.tipoFileUpload.equals("keyP12")) {
/* 458:592 */       processUploadKeyP12(eventFichero);
/* 459:593 */     } else if (!this.tipoFileUpload.equals("llamadoAtencion")) {
/* 460:596 */       System.out.println("oooo");
/* 461:    */     }
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void processUploadLogo(FileUploadEvent event)
/* 465:    */   {
/* 466:    */     try
/* 467:    */     {
/* 468:609 */       String fileName = "log_empresa" + getOrganizacion().getIdOrganizacion() + recuperaExtencion(event.getFile().getFileName());
/* 469:    */       
/* 470:611 */       String uploadDir = RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "logo");
/* 471:    */       
/* 472:613 */       File directorio = new File(uploadDir);
/* 473:    */       
/* 474:615 */       File file = new File(uploadDir + fileName);
/* 475:617 */       if (!directorio.exists()) {
/* 476:618 */         directorio.mkdirs();
/* 477:    */       }
/* 478:621 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 479:    */       
/* 480:623 */       this.organizacion.setImagen(fileName);
/* 481:    */       
/* 482:625 */       FileOutputStream output = new FileOutputStream(file);
/* 483:627 */       while (input.available() != 0) {
/* 484:628 */         output.write(input.read());
/* 485:    */       }
/* 486:630 */       input.close();
/* 487:631 */       output.close();
/* 488:    */       
/* 489:633 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_imagen"));
/* 490:    */     }
/* 491:    */     catch (Exception e)
/* 492:    */     {
/* 493:636 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_imagen"));
/* 494:637 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 495:    */     }
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void processUploadKeyP12(FileUploadEvent eventFichero)
/* 499:    */   {
/* 500:    */     try
/* 501:    */     {
/* 502:651 */       String fileName = "firma_electronica.p12";
/* 503:    */       
/* 504:653 */       String uploadDir = getPathFirma();
/* 505:    */       
/* 506:655 */       File directorio = new File(uploadDir);
/* 507:    */       
/* 508:657 */       File file = new File(uploadDir + fileName);
/* 509:659 */       if (!directorio.exists()) {
/* 510:660 */         directorio.mkdirs();
/* 511:    */       }
/* 512:663 */       InputStream input = new BufferedInputStream(eventFichero.getFile().getInputstream());
/* 513:    */       
/* 514:665 */       FileOutputStream output = new FileOutputStream(file);
/* 515:667 */       while (input.available() != 0) {
/* 516:668 */         output.write(input.read());
/* 517:    */       }
/* 518:670 */       input.close();
/* 519:671 */       output.close();
/* 520:    */       
/* 521:673 */       addInfoMessage(getLanguageController().getMensaje("msg_info_subir_fichero"));
/* 522:    */     }
/* 523:    */     catch (Exception e)
/* 524:    */     {
/* 525:676 */       addErrorMessage(getLanguageController().getMensaje("msg_error_subir_fichero"));
/* 526:677 */       LOG.error("ERROR AL SUBIR LOS DATOS", e);
/* 527:    */     }
/* 528:    */   }
/* 529:    */   
/* 530:    */   public String getTipoFileUpload()
/* 531:    */   {
/* 532:683 */     return this.tipoFileUpload;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public void setTipoFileUpload(String tipoFileUpload)
/* 536:    */   {
/* 537:687 */     this.tipoFileUpload = tipoFileUpload;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public String getSizeLimit()
/* 541:    */   {
/* 542:691 */     if (this.tipoFileUpload != null) {
/* 543:692 */       this.sizeLimit = (this.tipoFileUpload.equals("keyP12") ? "100000" : this.tipoFileUpload.equals("logoOrganizacion") ? "1000000" : "1000000");
/* 544:    */     } else {
/* 545:694 */       this.sizeLimit = "1000000";
/* 546:    */     }
/* 547:696 */     return this.sizeLimit;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void setSizeLimit(String sizeLimit)
/* 551:    */   {
/* 552:700 */     this.sizeLimit = sizeLimit;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public String getAllowTypes()
/* 556:    */   {
/* 557:704 */     if (this.tipoFileUpload != null) {
/* 558:706 */       this.allowTypes = (this.tipoFileUpload.equals("keyP12") ? "/(\\.|\\/)(p12)$/" : this.tipoFileUpload.equals("logoOrganizacion") ? "/(\\.|\\/)(gif|jpe?g|png)$/" : "/(\\.|\\/)(gif|jpe?g|png)$/");
/* 559:    */     } else {
/* 560:708 */       this.allowTypes = "/(\\.|\\/)(gif|jpe?g|png)$/";
/* 561:    */     }
/* 562:710 */     return this.allowTypes;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public void setAllowTypes(String allowTypes)
/* 566:    */   {
/* 567:714 */     this.allowTypes = allowTypes;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public String getPathFirma()
/* 571:    */   {
/* 572:718 */     String pathFirma = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri" + File.separator + "firmas" + File.separator;
/* 573:    */     
/* 574:720 */     return pathFirma;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public boolean isExisteKeyP12()
/* 578:    */   {
/* 579:    */     try
/* 580:    */     {
/* 581:725 */       File fichero = new File(getPathFirma() + "firma_electronica.p12");
/* 582:726 */       if (fichero.exists()) {
/* 583:727 */         return true;
/* 584:    */       }
/* 585:729 */       return false;
/* 586:    */     }
/* 587:    */     catch (Exception e) {}
/* 588:732 */     return false;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public String getConfirmarClave()
/* 592:    */   {
/* 593:737 */     return this.confirmarClave;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setConfirmarClave(String confirmarClave)
/* 597:    */   {
/* 598:741 */     this.confirmarClave = confirmarClave;
/* 599:    */   }
/* 600:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.OrganizacionBean
 * JD-Core Version:    0.7.0.1
 */