/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   7:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*   8:    */ import com.asinfo.as2.entities.Dispositivo;
/*   9:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  14:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  15:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  18:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*  19:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioDesechoMaterial;
/*  20:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  21:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  22:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.JsfUtil;
/*  25:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  26:    */ import java.math.BigDecimal;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.List;
/*  30:    */ import java.util.Map;
/*  31:    */ import javax.annotation.PostConstruct;
/*  32:    */ import javax.ejb.EJB;
/*  33:    */ import javax.faces.bean.ManagedBean;
/*  34:    */ import javax.faces.bean.ViewScoped;
/*  35:    */ import org.primefaces.component.datatable.DataTable;
/*  36:    */ import org.primefaces.model.LazyDataModel;
/*  37:    */ import org.primefaces.model.SortOrder;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class RegistroDesechoBean
/*  42:    */   extends PageControllerAS2
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  45:    */   @EJB
/*  46:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  47:    */   @EJB
/*  48:    */   private ServicioProducto servicioProducto;
/*  49:    */   @EJB
/*  50:    */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  51:    */   @EJB
/*  52:    */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  53:    */   @EJB
/*  54:    */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  55:    */   @EJB
/*  56:    */   private ServicioDesechoMaterial servicioDesechoMaterial;
/*  57:    */   private OrdenFabricacion ordenFabricacion;
/*  58:    */   private LazyDataModel<OrdenFabricacion> listaOrdenFabricacion;
/*  59:    */   private DataTable dtOrdenFabricacion;
/*  60:    */   private List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalleOrdenSalidaMaterialOrdenFabricacion;
/*  61:    */   private DataTable dtDetalleOrdenFabricacion;
/*  62:    */   private DetalleOrdenSalidaMaterialOrdenFabricacion detalleSeleccionado;
/*  63:    */   private List<Dispositivo> listaDispositivo;
/*  64:    */   private LecturaBalanza lecturaBalanza;
/*  65:    */   private List<UnidadManejo> listaUnidadManejo;
/*  66:    */   private List<UnidadManejo> listaPallet;
/*  67:    */   private Boolean mostrarBalanza;
/*  68:    */   protected DataTable dtLecturaBalanza;
/*  69:    */   protected List<LecturaBalanza> listaLecturaBalanza;
/*  70:    */   
/*  71:    */   @PostConstruct
/*  72:    */   public void init()
/*  73:    */   {
/*  74:102 */     this.listaOrdenFabricacion = new LazyDataModel()
/*  75:    */     {
/*  76:    */       private static final long serialVersionUID = 1L;
/*  77:    */       
/*  78:    */       public List<OrdenFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  79:    */       {
/*  80:108 */         List<OrdenFabricacion> lista = new ArrayList();
/*  81:109 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  82:111 */         if ((filters == null) || (filters.isEmpty())) {
/*  83:112 */           filters.put("estado", EstadoProduccionEnum.LANZADA.toString());
/*  84:    */         }
/*  85:114 */         RegistroDesechoBean.this.agregarFiltroOrganizacion(filters);
/*  86:    */         
/*  87:116 */         lista = RegistroDesechoBean.this.servicioOrdenFabricacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, false);
/*  88:    */         
/*  89:118 */         RegistroDesechoBean.this.listaOrdenFabricacion.setRowCount(RegistroDesechoBean.this.servicioOrdenFabricacion.contarPorCriterio(filters));
/*  90:    */         
/*  91:120 */         return lista;
/*  92:    */       }
/*  93:    */     };
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String editar()
/*  97:    */   {
/*  98:126 */     if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getId() != 0))
/*  99:    */     {
/* 100:127 */       this.listaDetalleOrdenSalidaMaterialOrdenFabricacion = this.servicioOrdenFabricacion.obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(this.ordenFabricacion, true);
/* 101:    */       
/* 102:129 */       this.listaLecturaBalanza = null;
/* 103:130 */       setEditado(true);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:132 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 108:    */     }
/* 109:134 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String crear()
/* 113:    */   {
/* 114:139 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 115:140 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String guardar()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:145 */       this.servicioDesechoMaterial.guardarRegistroDesecho(getListaDetalleOrdenSalidaMaterialOrdenFabricacion());
/* 123:146 */       setEditado(false);
/* 124:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 125:    */     }
/* 126:    */     catch (AS2Exception e)
/* 127:    */     {
/* 128:149 */       e.printStackTrace();
/* 129:150 */       JsfUtil.addErrorMessage(e, "");
/* 130:    */     }
/* 131:152 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String eliminar()
/* 135:    */   {
/* 136:156 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 137:157 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String cargarDatos()
/* 141:    */   {
/* 142:161 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String limpiar()
/* 146:    */   {
/* 147:165 */     this.listaLecturaBalanza = null;
/* 148:166 */     return "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public OrdenFabricacion getOrdenFabricacion()
/* 152:    */   {
/* 153:170 */     return this.ordenFabricacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 157:    */   {
/* 158:174 */     this.ordenFabricacion = ordenFabricacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public LazyDataModel<OrdenFabricacion> getListaOrdenFabricacion()
/* 162:    */   {
/* 163:178 */     return this.listaOrdenFabricacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaOrdenFabricacion(LazyDataModel<OrdenFabricacion> listaOrdenFabricacion)
/* 167:    */   {
/* 168:182 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public DataTable getDtOrdenFabricacion()
/* 172:    */   {
/* 173:186 */     return this.dtOrdenFabricacion;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 177:    */   {
/* 178:190 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Bodega getBodega()
/* 182:    */   {
/* 183:194 */     Bodega bodega = null;
/* 184:195 */     if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getProducto() != null))
/* 185:    */     {
/* 186:196 */       if (this.ordenFabricacion.getBodega() != null) {
/* 187:197 */         return this.ordenFabricacion.getBodega();
/* 188:    */       }
/* 189:199 */       bodega = this.servicioProducto.obtenerBodegaTrabajoProducto(this.ordenFabricacion.getProducto(), Integer.valueOf(AppUtil.getSucursal().getId()));
/* 190:    */     }
/* 191:202 */     return bodega;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<DetalleOrdenSalidaMaterialOrdenFabricacion> getListaDetalleOrdenSalidaMaterialOrdenFabricacion()
/* 195:    */   {
/* 196:206 */     return this.listaDetalleOrdenSalidaMaterialOrdenFabricacion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaDetalleOrdenSalidaMaterialOrdenFabricacion(List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalleOrdenSalidaMaterialOrdenFabricacion)
/* 200:    */   {
/* 201:211 */     this.listaDetalleOrdenSalidaMaterialOrdenFabricacion = listaDetalleOrdenSalidaMaterialOrdenFabricacion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public DataTable getDtDetalleOrdenFabricacion()
/* 205:    */   {
/* 206:215 */     return this.dtDetalleOrdenFabricacion;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setDtDetalleOrdenFabricacion(DataTable dtDetalleOrdenFabricacion)
/* 210:    */   {
/* 211:219 */     this.dtDetalleOrdenFabricacion = dtDetalleOrdenFabricacion;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public DetalleOrdenSalidaMaterialOrdenFabricacion getDetalleSeleccionado()
/* 215:    */   {
/* 216:223 */     return this.detalleSeleccionado;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDetalleSeleccionado(DetalleOrdenSalidaMaterialOrdenFabricacion detalleSeleccionado)
/* 220:    */   {
/* 221:227 */     this.detalleSeleccionado = detalleSeleccionado;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean isMostrarBalanza()
/* 225:    */   {
/* 226:231 */     if (this.mostrarBalanza == null) {
/* 227:232 */       this.mostrarBalanza = ParametrosSistema.getProduccionUsaBalanza(AppUtil.getOrganizacion().getId());
/* 228:    */     }
/* 229:234 */     return this.mostrarBalanza.booleanValue();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public LecturaBalanza getLecturaBalanza()
/* 233:    */   {
/* 234:238 */     if (this.lecturaBalanza == null)
/* 235:    */     {
/* 236:239 */       this.lecturaBalanza = new LecturaBalanza();
/* 237:240 */       this.lecturaBalanza.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 238:241 */       this.lecturaBalanza.setIdSucursal(AppUtil.getSucursal().getId());
/* 239:242 */       this.lecturaBalanza.setDispositivo(AppUtil.getUsuarioEnSesion().getDispositivo());
/* 240:    */     }
/* 241:244 */     return this.lecturaBalanza;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setLecturaBalanza(LecturaBalanza lecturaBalanza)
/* 245:    */   {
/* 246:248 */     this.lecturaBalanza = lecturaBalanza;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<UnidadManejo> getListaUnidadManejo()
/* 250:    */   {
/* 251:255 */     if ((this.listaUnidadManejo == null) || ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)))
/* 252:    */     {
/* 253:256 */       this.listaUnidadManejo = new ArrayList();
/* 254:257 */       if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 255:258 */         this.listaUnidadManejo = this.servicioProducto.obtenerListaUnidadManejoPorProducto(this.lecturaBalanza.getProducto());
/* 256:    */       }
/* 257:    */     }
/* 258:261 */     return this.listaUnidadManejo;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 262:    */   {
/* 263:269 */     this.listaUnidadManejo = listaUnidadManejo;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<UnidadManejo> getListaPallet()
/* 267:    */   {
/* 268:273 */     if (this.listaPallet == null)
/* 269:    */     {
/* 270:274 */       Map<String, String> filters = new HashMap();
/* 271:275 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 272:276 */       filters.put("activo", "true");
/* 273:277 */       filters.put("categoriaUnidadManejo.indicadorPallet", "true");
/* 274:278 */       this.listaPallet = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, filters);
/* 275:    */     }
/* 276:280 */     return this.listaPallet;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setListaPallet(List<UnidadManejo> listaPallet)
/* 280:    */   {
/* 281:284 */     this.listaPallet = listaPallet;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public List<Dispositivo> getListaDispositivo()
/* 285:    */   {
/* 286:288 */     if (this.listaDispositivo == null)
/* 287:    */     {
/* 288:289 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 289:290 */       filtros.put("activo", "true");
/* 290:291 */       this.listaDispositivo = this.servicioDispositivo.obtenerListaCombo(Dispositivo.class, "nombre", true, filtros);
/* 291:    */     }
/* 292:293 */     return this.listaDispositivo;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 296:    */   {
/* 297:297 */     if (this.listaLecturaBalanza == null)
/* 298:    */     {
/* 299:298 */       this.listaLecturaBalanza = new ArrayList();
/* 300:299 */       for (DetalleOrdenSalidaMaterialOrdenFabricacion detalle : getListaDetalleOrdenSalidaMaterialOrdenFabricacion()) {
/* 301:300 */         if (!detalle.isEliminado()) {
/* 302:301 */           for (LecturaBalanza lectura : detalle.getListaLecturaBalanza()) {
/* 303:302 */             if (!lectura.isEliminado()) {
/* 304:303 */               this.listaLecturaBalanza.add(lectura);
/* 305:    */             }
/* 306:    */           }
/* 307:    */         }
/* 308:    */       }
/* 309:    */     }
/* 310:309 */     return this.listaLecturaBalanza;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public DataTable getDtLecturaBalanza()
/* 314:    */   {
/* 315:313 */     return this.dtLecturaBalanza;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void setDtLecturaBalanza(DataTable dtLecturaBalanza)
/* 319:    */   {
/* 320:317 */     this.dtLecturaBalanza = dtLecturaBalanza;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void eliminarLecturaBalanza(LecturaBalanza lectura)
/* 324:    */   {
/* 325:    */     try
/* 326:    */     {
/* 327:322 */       LecturaBalanza lecturaEliminar = (LecturaBalanza)this.dtLecturaBalanza.getRowData();
/* 328:323 */       this.servicioDesechoMaterial.eliminarPesoDesecho(lecturaEliminar);
/* 329:324 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 330:    */     }
/* 331:    */     catch (AS2Exception e)
/* 332:    */     {
/* 333:326 */       JsfUtil.addErrorMessage(e, "");
/* 334:    */     }
/* 335:    */     catch (Exception e)
/* 336:    */     {
/* 337:328 */       e.printStackTrace();
/* 338:329 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 339:    */     }
/* 340:    */     finally
/* 341:    */     {
/* 342:331 */       this.listaDetalleOrdenSalidaMaterialOrdenFabricacion = this.servicioOrdenFabricacion.obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(this.ordenFabricacion, true);
/* 343:    */       
/* 344:333 */       this.listaLecturaBalanza = null;
/* 345:335 */       if (this.dtLecturaBalanza != null) {
/* 346:336 */         this.dtLecturaBalanza.reset();
/* 347:    */       }
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void capturarPesoListener()
/* 352:    */   {
/* 353:342 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getProducto() != null)) {
/* 354:    */       try
/* 355:    */       {
/* 356:344 */         this.servicioMarcacionDispositivo.calcularPesoNeto(AppUtil.getOrganizacion().getId(), this.lecturaBalanza, true);
/* 357:    */       }
/* 358:    */       catch (AS2Exception ex)
/* 359:    */       {
/* 360:346 */         JsfUtil.addErrorMessage(ex, "");
/* 361:    */       }
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void agregarPesoListener()
/* 366:    */   {
/* 367:352 */     if ((this.lecturaBalanza.getProducto() != null) && (this.lecturaBalanza.getPesoNeto().compareTo(BigDecimal.ZERO) > 0)) {
/* 368:    */       try
/* 369:    */       {
/* 370:354 */         this.servicioDesechoMaterial.agregarPesoDesecho(this.lecturaBalanza);
/* 371:355 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 372:    */       }
/* 373:    */       catch (AS2Exception e)
/* 374:    */       {
/* 375:    */         Dispositivo dispositivo;
/* 376:357 */         JsfUtil.addErrorMessage(e, "");
/* 377:    */       }
/* 378:    */       catch (Exception e)
/* 379:    */       {
/* 380:    */         Dispositivo dispositivo;
/* 381:359 */         e.printStackTrace();
/* 382:360 */         addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 383:    */       }
/* 384:    */       finally
/* 385:    */       {
/* 386:    */         Dispositivo dispositivo;
/* 387:362 */         this.listaDetalleOrdenSalidaMaterialOrdenFabricacion = this.servicioOrdenFabricacion.obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(this.ordenFabricacion, true);
/* 388:    */         
/* 389:    */ 
/* 390:365 */         Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 391:366 */         this.lecturaBalanza = null;
/* 392:367 */         getLecturaBalanza().setDispositivo(dispositivo);
/* 393:    */         
/* 394:369 */         this.listaLecturaBalanza = null;
/* 395:370 */         this.detalleSeleccionado = null;
/* 396:371 */         if (this.dtLecturaBalanza != null) {
/* 397:372 */           this.dtLecturaBalanza.reset();
/* 398:    */         }
/* 399:    */       }
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void cargarProductoSeleccionadoPesa()
/* 404:    */   {
/* 405:379 */     if ((isMostrarBalanza()) && (this.lecturaBalanza != null))
/* 406:    */     {
/* 407:380 */       Dispositivo dispositivo = this.lecturaBalanza.getDispositivo();
/* 408:381 */       this.lecturaBalanza = null;
/* 409:382 */       getLecturaBalanza().setDispositivo(dispositivo);
/* 410:384 */       if (this.detalleSeleccionado.getDetalleOrdenSalidaMaterial().getProducto().isIndicadorPesoBalanza())
/* 411:    */       {
/* 412:385 */         this.lecturaBalanza.setProducto(this.detalleSeleccionado.getDetalleOrdenSalidaMaterial().getProducto());
/* 413:386 */         this.lecturaBalanza.setDetalleOrdenSalidaMaterialOrdenFabricacion(this.detalleSeleccionado);
/* 414:    */       }
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   public void calcularCantidad()
/* 419:    */   {
/* 420:392 */     if ((this.lecturaBalanza != null) && (this.lecturaBalanza.getUnidadManejo() != null)) {
/* 421:393 */       this.servicioMarcacionDispositivo.calcularCantidad(this.lecturaBalanza);
/* 422:    */     }
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void actualizarCantidadDesecho() {}
/* 426:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.RegistroDesechoBean
 * JD-Core Version:    0.7.0.1
 */