/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.BodegaTrabajoProductoSucursal;
/*   8:    */ import com.asinfo.as2.entities.Lote;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.Unidad;
/*  13:    */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*  14:    */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterialMezcla;
/*  15:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  16:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  17:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  18:    */ import com.asinfo.as2.enumeraciones.TipoMaterialEnum;
/*  19:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  24:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  25:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  26:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  27:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  28:    */ import com.asinfo.as2.util.AppUtil;
/*  29:    */ import com.asinfo.as2.utils.JsfUtil;
/*  30:    */ import com.asinfo.as2.utils.NodoArbol;
/*  31:    */ import java.math.BigDecimal;
/*  32:    */ import java.math.RoundingMode;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import java.util.Iterator;
/*  36:    */ import java.util.List;
/*  37:    */ import java.util.Map;
/*  38:    */ import javax.annotation.PostConstruct;
/*  39:    */ import javax.ejb.EJB;
/*  40:    */ import javax.faces.bean.ManagedBean;
/*  41:    */ import javax.faces.bean.ManagedProperty;
/*  42:    */ import javax.faces.bean.ViewScoped;
/*  43:    */ import javax.faces.model.SelectItem;
/*  44:    */ import org.apache.log4j.Logger;
/*  45:    */ import org.hibernate.LazyInitializationException;
/*  46:    */ import org.primefaces.component.datatable.DataTable;
/*  47:    */ import org.primefaces.context.RequestContext;
/*  48:    */ import org.primefaces.model.LazyDataModel;
/*  49:    */ import org.primefaces.model.SortOrder;
/*  50:    */ 
/*  51:    */ @ManagedBean
/*  52:    */ @ViewScoped
/*  53:    */ public class FormulacionBean
/*  54:    */   extends PageControllerAS2
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  57:    */   @EJB
/*  58:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  59:    */   @EJB
/*  60:    */   private ServicioRutaFabricacion servicioRutaFabricacion;
/*  61:    */   @EJB
/*  62:    */   private ServicioBodega servicioBodega;
/*  63:    */   @EJB
/*  64:    */   private ServicioDocumento servicioDocumento;
/*  65:    */   @EJB
/*  66:    */   private ServicioProducto servicioProducto;
/*  67:    */   private SelectItem[] listaEstadoItem;
/*  68:    */   private OrdenFabricacion ordenFabricacion;
/*  69:    */   private LazyDataModel<OrdenFabricacion> listaOrdenFabricacion;
/*  70:    */   private Bodega bodega;
/*  71:    */   private BigDecimal cantidadBatchFormulacion;
/*  72:    */   private List<DetalleOrdenFabricacionMaterial> listaDetalleOrdenFabricacionMaterial;
/*  73:    */   private List<DetalleOrdenFabricacionMaterial> listaDetalleMaterialMicroFiltrados;
/*  74:    */   private List<DetalleOrdenFabricacionMaterial> listaDetalleMaterialMacroFiltrados;
/*  75:    */   @ManagedProperty("#{listaProductoBean}")
/*  76:    */   private ListaProductoBean listaProductoBean;
/*  77:    */   private List<OrdenFabricacion> listaOrdenFabricacionFormuladas;
/*  78:    */   private DataTable dtDetalleMacro;
/*  79:    */   private DataTable dtDetalleMicro;
/*  80:107 */   private BigDecimal totalMacro = BigDecimal.ZERO;
/*  81:108 */   private BigDecimal totalMicro = BigDecimal.ZERO;
/*  82:    */   private boolean activaSuspender;
/*  83:113 */   private Date fechaCierreOrden = new Date();
/*  84:    */   
/*  85:    */   @PostConstruct
/*  86:    */   public void init()
/*  87:    */   {
/*  88:121 */     getListaProductoBean().resetearIndicadores();
/*  89:    */     
/*  90:123 */     this.listaOrdenFabricacion = new LazyDataModel()
/*  91:    */     {
/*  92:    */       private static final long serialVersionUID = 1L;
/*  93:    */       
/*  94:    */       public List<OrdenFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  95:    */       {
/*  96:129 */         List<OrdenFabricacion> lista = new ArrayList();
/*  97:130 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  98:132 */         if ((filters == null) || (filters.isEmpty())) {
/*  99:133 */           filters.put("estado", EstadoProduccionEnum.LANZADA.toString());
/* 100:    */         }
/* 101:135 */         FormulacionBean.this.agregarFiltroOrganizacion(filters);
/* 102:136 */         filters.put("indicadorSuborden", "true");
/* 103:    */         
/* 104:138 */         lista = FormulacionBean.this.servicioOrdenFabricacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, false);
/* 105:    */         
/* 106:140 */         FormulacionBean.this.listaOrdenFabricacion.setRowCount(FormulacionBean.this.servicioOrdenFabricacion.contarPorCriterio(filters));
/* 107:    */         
/* 108:142 */         return lista;
/* 109:    */       }
/* 110:    */     };
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String editar()
/* 114:    */   {
/* 115:153 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 116:154 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String crear()
/* 120:    */   {
/* 121:159 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 122:160 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String formular()
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:167 */       if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getId() != 0))
/* 130:    */       {
/* 131:169 */         this.listaOrdenFabricacionFormuladas = null;
/* 132:172 */         if ((this.ordenFabricacion.getOrdenFabricacionPadre() != null) && (!this.ordenFabricacion.getOrdenFabricacionPadre().getEstado().equals(EstadoProduccionEnum.LANZADA)) && 
/* 133:173 */           (!this.ordenFabricacion.getOrdenFabricacionPadre().getEstado().equals(EstadoProduccionEnum.SUSPENDIDA)))
/* 134:    */         {
/* 135:174 */           JsfUtil.addErrorMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " " + this.ordenFabricacion.getOrdenFabricacionPadre().getNumero() + " " + this.ordenFabricacion.getOrdenFabricacionPadre().getEstado() + "");
/* 136:175 */           return "";
/* 137:    */         }
/* 138:179 */         this.bodega = this.ordenFabricacion.getBodega();
/* 139:180 */         this.bodega = this.servicioProducto.obtenerBodegaTrabajoProducto(this.ordenFabricacion.getProducto(), Integer.valueOf(AppUtil.getSucursal().getId()));
/* 140:183 */         if (this.ordenFabricacion.getEstado().equals(EstadoProduccionEnum.LANZADA))
/* 141:    */         {
/* 142:184 */           this.cantidadBatchFormulacion = this.ordenFabricacion.getCantidadBatch();
/* 143:    */         }
/* 144:185 */         else if (this.ordenFabricacion.getEstado().equals(EstadoProduccionEnum.SUSPENDIDA))
/* 145:    */         {
/* 146:186 */           this.cantidadBatchFormulacion = this.ordenFabricacion.getCantidadBatch().subtract(this.ordenFabricacion.getCantidadBatchFabricados());
/* 147:187 */           if (this.cantidadBatchFormulacion.compareTo(BigDecimal.ZERO) <= 0) {
/* 148:188 */             this.cantidadBatchFormulacion = BigDecimal.ZERO;
/* 149:    */           }
/* 150:    */         }
/* 151:    */         else
/* 152:    */         {
/* 153:191 */           this.cantidadBatchFormulacion = this.ordenFabricacion.getCantidadFormulacion();
/* 154:    */         }
/* 155:195 */         cargarListaDetalleOrdenFabricacionMaterial();
/* 156:196 */         setEditado(true);
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:198 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 161:    */       }
/* 162:    */     }
/* 163:    */     catch (AS2Exception e)
/* 164:    */     {
/* 165:202 */       JsfUtil.addErrorMessage(e, "");
/* 166:    */     }
/* 167:205 */     return "";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void cargarListaDetalleOrdenFabricacionMaterial()
/* 171:    */     throws AS2Exception
/* 172:    */   {
/* 173:210 */     this.listaDetalleOrdenFabricacionMaterial = null;
/* 174:212 */     if (this.ordenFabricacion.getEstado().equals(EstadoProduccionEnum.LANZADA))
/* 175:    */     {
/* 176:215 */       this.listaDetalleOrdenFabricacionMaterial = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(this.ordenFabricacion, null);
/* 177:226 */       if (((this.listaDetalleOrdenFabricacionMaterial == null) || (this.listaDetalleOrdenFabricacionMaterial.isEmpty())) && 
/* 178:227 */         (!getListaOrdenFabricacionFormuladas().isEmpty()))
/* 179:    */       {
/* 180:228 */         this.ordenFabricacion.setOrdenFabricacionFormulacion((OrdenFabricacion)getListaOrdenFabricacionFormuladas().get(0));
/* 181:229 */         actualizarFormulaOrdenFabricacion();
/* 182:    */       }
/* 183:233 */       if ((this.listaDetalleOrdenFabricacionMaterial == null) || (this.listaDetalleOrdenFabricacionMaterial.isEmpty())) {
/* 184:234 */         cargarRecetaOriginal();
/* 185:    */       }
/* 186:    */     }
/* 187:236 */     else if (this.ordenFabricacion.getEstado().equals(EstadoProduccionEnum.SUSPENDIDA))
/* 188:    */     {
/* 189:237 */       this.listaDetalleOrdenFabricacionMaterial = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(this.ordenFabricacion, Boolean.valueOf(true));
/* 190:    */     }
/* 191:    */     else
/* 192:    */     {
/* 193:239 */       this.listaDetalleOrdenFabricacionMaterial = this.servicioProducto.obtenerMaterialesDetalleOrdenFabricacionMaterial(this.ordenFabricacion, Boolean.valueOf(true));
/* 194:    */     }
/* 195:241 */     calcularSaldos();
/* 196:242 */     reiniciarTablasDetalles();
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void calcularSaldos()
/* 200:    */     throws AS2Exception
/* 201:    */   {
/* 202:246 */     if (this.listaDetalleOrdenFabricacionMaterial != null) {
/* 203:248 */       for (DetalleOrdenFabricacionMaterial detalle : this.listaDetalleOrdenFabricacionMaterial) {
/* 204:249 */         if ((detalle.isActivo()) && (detalle.isIndicadorHoja()))
/* 205:    */         {
/* 206:252 */           Bodega bodegaTrabajo = this.servicioProducto.obtenerBodegaTrabajoProducto(detalle.getMaterial(), 
/* 207:253 */             Integer.valueOf(this.ordenFabricacion.getSucursal().getId()));
/* 208:254 */           if (bodegaTrabajo == null) {
/* 209:256 */             throw new AS2Exception("com.asinfo.as2.produccion.procesos.servicio.impl.ServicioOrdenFabricacionImpl.ERROR_NO_EXISTE_BODEGA_TRABAJO_SUCURSAL", new String[] {detalle.getMaterial().getCodigo() + "-" + detalle.getMaterial().getNombre() });
/* 210:    */           }
/* 211:259 */           detalle.setBodegaTrabajoMaterial(bodegaTrabajo);
/* 212:260 */           BigDecimal saldoBodega = BigDecimal.ZERO;
/* 213:262 */           if (detalle.getLote() == null) {
/* 214:263 */             saldoBodega = this.servicioProducto.getSaldo(detalle.getMaterial().getId(), bodegaTrabajo.getId(), new Date());
/* 215:    */           } else {
/* 216:265 */             saldoBodega = this.servicioProducto.getSaldoLote(detalle.getMaterial().getId(), bodegaTrabajo.getId(), detalle.getLote().getId(), new Date());
/* 217:    */           }
/* 218:278 */           BigDecimal saldoDisponible = saldoBodega;
/* 219:    */           
/* 220:280 */           detalle.setCantidadDisponible(saldoDisponible);
/* 221:    */         }
/* 222:    */       }
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private void cargarRecetaOriginal()
/* 227:    */   {
/* 228:287 */     this.listaDetalleOrdenFabricacionMaterial = new ArrayList();
/* 229:    */     
/* 230:289 */     DetalleOrdenFabricacionMaterial detallePrincipal = null;
/* 231:    */     try
/* 232:    */     {
/* 233:291 */       detallePrincipal = this.servicioProducto.crearDetalle(this.ordenFabricacion, this.listaDetalleOrdenFabricacionMaterial, null, this.ordenFabricacion
/* 234:292 */         .getProducto(), getCantidadFormulacion(), null);
/* 235:    */     }
/* 236:    */     catch (AS2Exception e)
/* 237:    */     {
/* 238:294 */       JsfUtil.addErrorMessage(e, "");
/* 239:    */     }
/* 240:297 */     this.ordenFabricacion.getProducto().setCantidadProducir(getCantidadFormulacion());
/* 241:298 */     NodoArbol<Producto> arbol = this.servicioProducto.obtenerArbolComponentes(this.ordenFabricacion.getProducto());
/* 242:299 */     for (NodoArbol<Producto> nodo : arbol.getHojas()) {
/* 243:    */       try
/* 244:    */       {
/* 245:301 */         this.servicioProducto.crearDetalle(this.ordenFabricacion, this.listaDetalleOrdenFabricacionMaterial, detallePrincipal, (Producto)nodo.getValor(), 
/* 246:302 */           ((Producto)nodo.getValor()).getCantidadProducir().setScale(2, RoundingMode.HALF_UP), null);
/* 247:    */       }
/* 248:    */       catch (AS2Exception e)
/* 249:    */       {
/* 250:304 */         JsfUtil.addErrorMessage(e, "");
/* 251:    */       }
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void actualizarFormulaOrdenFabricacion()
/* 256:    */   {
/* 257:310 */     if (this.ordenFabricacion.getOrdenFabricacionFormulacion() != null) {
/* 258:    */       try
/* 259:    */       {
/* 260:312 */         this.listaDetalleOrdenFabricacionMaterial = this.servicioProducto.copiarFormula(this.ordenFabricacion.getOrdenFabricacionFormulacion(), this.ordenFabricacion, 
/* 261:313 */           getCantidadFormulacion());
/* 262:314 */         reiniciarTablasDetalles();
/* 263:    */       }
/* 264:    */       catch (AS2Exception e)
/* 265:    */       {
/* 266:316 */         JsfUtil.addErrorMessage(e, "");
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String guardar()
/* 272:    */   {
/* 273:    */     try
/* 274:    */     {
/* 275:328 */       if (this.ordenFabricacion.getProducto().getCantidadProduccion().compareTo(getTotal()) != 0)
/* 276:    */       {
/* 277:329 */         String mensaje = " " + getCantidadFormulacion() + " != " + getTotal();
/* 278:330 */         addErrorMessage(getLanguageController().getMensaje("msg_error_total_formula") + mensaje);
/* 279:331 */         return null;
/* 280:    */       }
/* 281:333 */       this.ordenFabricacion.setFechaFormulacion(new Date());
/* 282:334 */       this.ordenFabricacion.setCantidadFormulacion(getCantidadFormulacion());
/* 283:335 */       this.servicioOrdenFabricacion.actualizarFormula(this.listaDetalleOrdenFabricacionMaterial, this.ordenFabricacion, getCantidadFormulacion());
/* 284:336 */       setEditado(false);
/* 285:337 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 286:    */     }
/* 287:    */     catch (AS2Exception e)
/* 288:    */     {
/* 289:339 */       JsfUtil.addErrorMessage(e, "");
/* 290:    */     }
/* 291:341 */     return "";
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<RutaFabricacion> autocompletarRutaFabricacion(String consulta)
/* 295:    */   {
/* 296:345 */     if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getProducto() != null)) {
/* 297:346 */       return this.servicioRutaFabricacion.autocompletarRutaFabricacion(this.ordenFabricacion.getProducto(), consulta);
/* 298:    */     }
/* 299:348 */     return new ArrayList();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public String eliminar()
/* 303:    */   {
/* 304:    */     try
/* 305:    */     {
/* 306:360 */       if ((getOrdenFabricacion() != null) && (getOrdenFabricacion().getIdOrdenFabricacion() != 0))
/* 307:    */       {
/* 308:362 */         if ((getOrdenFabricacion().getEstado().equals(EstadoProduccionEnum.ANULADO)) || 
/* 309:363 */           (getOrdenFabricacion().getEstado().equals(EstadoProduccionEnum.FINALIZADA)) || 
/* 310:364 */           (getOrdenFabricacion().getEstado().equals(EstadoProduccionEnum.ENVIADA)))
/* 311:    */         {
/* 312:365 */           JsfUtil.addErrorMessage(
/* 313:366 */             getLanguageController().getMensaje("msg_accion_no_permitida") + " " + this.ordenFabricacion.getNumero() + " " + this.ordenFabricacion.getEstado() + "");
/* 314:367 */           return "";
/* 315:    */         }
/* 316:370 */         if (this.ordenFabricacion.getCantidadFabricada().compareTo(BigDecimal.ZERO) > 0)
/* 317:    */         {
/* 318:371 */           JsfUtil.addErrorMessage(
/* 319:372 */             getLanguageController().getMensaje("msg_accion_no_permitida") + " Tiene Cantidad Producida" + this.ordenFabricacion.getNumero());
/* 320:373 */           return "";
/* 321:    */         }
/* 322:376 */         this.servicioOrdenFabricacion.anularPorFormulacion(getOrdenFabricacion());
/* 323:377 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 324:    */       }
/* 325:    */       else
/* 326:    */       {
/* 327:379 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 328:    */       }
/* 329:    */     }
/* 330:    */     catch (AS2Exception e)
/* 331:    */     {
/* 332:382 */       JsfUtil.addErrorMessage(e, "");
/* 333:383 */       e.printStackTrace();
/* 334:    */     }
/* 335:    */     catch (Exception e)
/* 336:    */     {
/* 337:385 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 338:386 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 339:    */     }
/* 340:389 */     return "";
/* 341:    */   }
/* 342:    */   
/* 343:    */   public String cargarDatos()
/* 344:    */   {
/* 345:398 */     return "";
/* 346:    */   }
/* 347:    */   
/* 348:    */   public String limpiar()
/* 349:    */   {
/* 350:407 */     return "";
/* 351:    */   }
/* 352:    */   
/* 353:    */   public String enviarWinCC()
/* 354:    */   {
/* 355:    */     try
/* 356:    */     {
/* 357:412 */       this.servicioOrdenFabricacion.enviarWinCC(this.ordenFabricacion);
/* 358:413 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 359:    */     }
/* 360:    */     catch (AS2Exception e)
/* 361:    */     {
/* 362:415 */       addErrorMessage(e.getMessage());
/* 363:416 */       e.printStackTrace();
/* 364:    */     }
/* 365:    */     catch (Exception e)
/* 366:    */     {
/* 367:418 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 368:419 */       e.printStackTrace();
/* 369:    */     }
/* 370:421 */     return "";
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void generarOperaciones() {}
/* 374:    */   
/* 375:    */   public void finalizarOrdenFabricacion()
/* 376:    */   {
/* 377:    */     try
/* 378:    */     {
/* 379:430 */       if ((!EstadoProduccionEnum.LANZADA.equals(this.ordenFabricacion.getEstado())) && 
/* 380:431 */         (!EstadoProduccionEnum.SUSPENDIDA.equals(this.ordenFabricacion.getEstado())))
/* 381:    */       {
/* 382:432 */         JsfUtil.addErrorMessage(
/* 383:433 */           getLanguageController().getMensaje("msg_accion_no_permitida") + " " + this.ordenFabricacion.getNumero() + " " + this.ordenFabricacion.getEstado() + "");
/* 384:434 */         return;
/* 385:    */       }
/* 386:436 */       this.servicioOrdenFabricacion.finalizarOrdenPorFormulacion(this.ordenFabricacion, getFechaCierreOrden());
/* 387:437 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 388:    */     }
/* 389:    */     catch (ExcepcionAS2Financiero e)
/* 390:    */     {
/* 391:440 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 392:441 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 393:    */     }
/* 394:    */     catch (ExcepcionAS2 e)
/* 395:    */     {
/* 396:443 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 397:444 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 398:    */     }
/* 399:    */     catch (AS2Exception e)
/* 400:    */     {
/* 401:446 */       JsfUtil.addErrorMessage(e, "");
/* 402:447 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/* 403:    */     }
/* 404:    */   }
/* 405:    */   
/* 406:    */   public LazyDataModel<OrdenFabricacion> getListaOrdenFabricacion()
/* 407:    */   {
/* 408:457 */     return this.listaOrdenFabricacion;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void setListaOrdenFabricacion(LazyDataModel<OrdenFabricacion> listaOrdenFabricacion)
/* 412:    */   {
/* 413:467 */     this.listaOrdenFabricacion = listaOrdenFabricacion;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public ListaProductoBean getListaProductoBean()
/* 417:    */   {
/* 418:471 */     return this.listaProductoBean;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/* 422:    */   {
/* 423:475 */     this.listaProductoBean = listaProductoBean;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public SelectItem[] getListaEstadoItem()
/* 427:    */   {
/* 428:480 */     if (this.listaEstadoItem == null)
/* 429:    */     {
/* 430:481 */       List<SelectItem> lista = new ArrayList();
/* 431:482 */       lista.add(new SelectItem("", ""));
/* 432:484 */       for (EstadoProduccionEnum t : EstadoProduccionEnum.values()) {
/* 433:485 */         lista.add(new SelectItem(t, t.getNombre()));
/* 434:    */       }
/* 435:487 */       this.listaEstadoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 436:    */     }
/* 437:490 */     return this.listaEstadoItem;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setListaEstadoItem(SelectItem[] listaEstadoItem)
/* 441:    */   {
/* 442:494 */     this.listaEstadoItem = listaEstadoItem;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public OrdenFabricacion getOrdenFabricacion()
/* 446:    */   {
/* 447:    */     try
/* 448:    */     {
/* 449:499 */       if (this.ordenFabricacion != null) {
/* 450:500 */         this.ordenFabricacion.getListaOperacionProduccion().size();
/* 451:    */       }
/* 452:502 */       return this.ordenFabricacion;
/* 453:    */     }
/* 454:    */     catch (LazyInitializationException e)
/* 455:    */     {
/* 456:504 */       this.ordenFabricacion.setListaOperacionProduccion(new ArrayList());
/* 457:    */     }
/* 458:505 */     return this.ordenFabricacion;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 462:    */   {
/* 463:510 */     this.ordenFabricacion = ordenFabricacion;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public Bodega getBodega()
/* 467:    */   {
/* 468:514 */     return this.bodega;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public void setBodega(Bodega bodega)
/* 472:    */   {
/* 473:518 */     this.bodega = bodega;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public BigDecimal getCantidadBatchFormulacion()
/* 477:    */   {
/* 478:522 */     return this.cantidadBatchFormulacion;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void setCantidadBatchFormulacion(BigDecimal cantidadBatchFormulacion)
/* 482:    */   {
/* 483:526 */     this.cantidadBatchFormulacion = cantidadBatchFormulacion;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public BigDecimal getCantidadFormulacion()
/* 487:    */   {
/* 488:530 */     BigDecimal cantidadFormulacion = BigDecimal.ZERO;
/* 489:531 */     if ((this.ordenFabricacion != null) && (this.cantidadBatchFormulacion != null)) {
/* 490:532 */       cantidadFormulacion = this.cantidadBatchFormulacion.multiply(this.ordenFabricacion.getProducto().getCantidadProduccion()).setScale(2, RoundingMode.HALF_UP);
/* 491:    */     }
/* 492:535 */     return cantidadFormulacion;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public List<OrdenFabricacion> getListaOrdenFabricacionFormuladas()
/* 496:    */   {
/* 497:539 */     if ((this.listaOrdenFabricacionFormuladas == null) && (this.ordenFabricacion != null) && (this.ordenFabricacion.getProducto() != null)) {
/* 498:540 */       this.listaOrdenFabricacionFormuladas = this.servicioProducto.buscarOrdenesFabricacionFormuladasPorProducto(this.ordenFabricacion.getProducto());
/* 499:    */     }
/* 500:542 */     return this.listaOrdenFabricacionFormuladas;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void setListaOrdenFabricacionFormuladas(List<OrdenFabricacion> listaOrdenFabricacionFormuladas)
/* 504:    */   {
/* 505:546 */     this.listaOrdenFabricacionFormuladas = listaOrdenFabricacionFormuladas;
/* 506:    */   }
/* 507:    */   
/* 508:    */   public List<DetalleOrdenFabricacionMaterial> getListaMacros()
/* 509:    */   {
/* 510:550 */     List<DetalleOrdenFabricacionMaterial> listaMacros = new ArrayList();
/* 511:551 */     for (DetalleOrdenFabricacionMaterial detalle : this.listaDetalleOrdenFabricacionMaterial) {
/* 512:552 */       if ((!detalle.isEliminado()) && (detalle.isIndicadorHoja()) && (detalle.getMaterial().getTipoMaterialEnum() != null) && (detalle.getMaterial().getTipoMaterialEnum().equals(TipoMaterialEnum.MACRO))) {
/* 513:553 */         listaMacros.add(detalle);
/* 514:    */       }
/* 515:    */     }
/* 516:557 */     return listaMacros;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public List<DetalleOrdenFabricacionMaterial> getListaMicros()
/* 520:    */   {
/* 521:561 */     List<DetalleOrdenFabricacionMaterial> listaMacros = new ArrayList();
/* 522:562 */     for (DetalleOrdenFabricacionMaterial detalle : this.listaDetalleOrdenFabricacionMaterial) {
/* 523:563 */       if ((!detalle.isEliminado()) && (detalle.isIndicadorHoja()) && (detalle.getMaterial().getTipoMaterialEnum() != null) && (detalle.getMaterial().getTipoMaterialEnum().equals(TipoMaterialEnum.MICRO))) {
/* 524:564 */         listaMacros.add(detalle);
/* 525:    */       }
/* 526:    */     }
/* 527:567 */     return listaMacros;
/* 528:    */   }
/* 529:    */   
/* 530:    */   private DetalleOrdenFabricacionMaterial obtenerDetalleActivoPrincipal()
/* 531:    */   {
/* 532:571 */     DetalleOrdenFabricacionMaterial detallePrincipal = null;
/* 533:572 */     if (this.listaDetalleOrdenFabricacionMaterial != null) {
/* 534:573 */       for (DetalleOrdenFabricacionMaterial detalle : this.listaDetalleOrdenFabricacionMaterial) {
/* 535:574 */         if ((!detalle.isEliminado()) && (detalle.getDetalleOrdenFabricacionMaterialPadre() == null))
/* 536:    */         {
/* 537:575 */           detallePrincipal = detalle;
/* 538:576 */           break;
/* 539:    */         }
/* 540:    */       }
/* 541:    */     }
/* 542:580 */     return detallePrincipal;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/* 546:    */   {
/* 547:    */     try
/* 548:    */     {
/* 549:585 */       Producto producto = saldoLote.getProducto();
/* 550:586 */       BigDecimal traCantidad = getListaProductoBean().getProducto().getTraCantidad();
/* 551:587 */       if (traCantidad == null) {
/* 552:588 */         traCantidad = BigDecimal.ZERO;
/* 553:    */       }
/* 554:590 */       producto = this.servicioProducto.cargaDetalle(producto.getId());
/* 555:591 */       validarProducto(producto);
/* 556:592 */       DetalleOrdenFabricacionMaterial detallePrincipal = obtenerDetalleActivoPrincipal();
/* 557:    */       try
/* 558:    */       {
/* 559:594 */         this.servicioProducto.crearDetalle(this.ordenFabricacion, this.listaDetalleOrdenFabricacionMaterial, detallePrincipal, producto, traCantidad, saldoLote);
/* 560:    */       }
/* 561:    */       catch (AS2Exception e)
/* 562:    */       {
/* 563:597 */         JsfUtil.addErrorMessage(e, "");
/* 564:    */       }
/* 565:599 */       reiniciarTablasDetalles();
/* 566:    */     }
/* 567:    */     catch (AS2Exception e)
/* 568:    */     {
/* 569:601 */       JsfUtil.addErrorMessage(e, e.getMessage());
/* 570:    */     }
/* 571:    */   }
/* 572:    */   
/* 573:    */   public void cargarProducto(Producto producto)
/* 574:    */   {
/* 575:    */     try
/* 576:    */     {
/* 577:607 */       BigDecimal traCantidad = producto.getTraCantidad();
/* 578:608 */       producto = this.servicioProducto.cargaDetalle(producto.getId());
/* 579:609 */       validarProducto(producto);
/* 580:610 */       DetalleOrdenFabricacionMaterial detallePrincipal = obtenerDetalleActivoPrincipal();
/* 581:    */       try
/* 582:    */       {
/* 583:612 */         this.servicioProducto.crearDetalle(this.ordenFabricacion, this.listaDetalleOrdenFabricacionMaterial, detallePrincipal, producto, traCantidad, null);
/* 584:    */       }
/* 585:    */       catch (AS2Exception e)
/* 586:    */       {
/* 587:614 */         JsfUtil.addErrorMessage(e, "");
/* 588:    */       }
/* 589:616 */       totalizar();
/* 590:617 */       reiniciarTablasDetalles();
/* 591:    */     }
/* 592:    */     catch (AS2Exception e)
/* 593:    */     {
/* 594:619 */       JsfUtil.addErrorMessage(e, e.getMessage());
/* 595:    */     }
/* 596:    */   }
/* 597:    */   
/* 598:    */   private void validarProducto(Producto producto)
/* 599:    */     throws AS2Exception
/* 600:    */   {
/* 601:631 */     if (producto.getTipoMaterialEnum() == null) {
/* 602:632 */       throw new AS2Exception(getLanguageController().getMensaje("msg_error_no_tiene_tipo_material"));
/* 603:    */     }
/* 604:634 */     for (BodegaTrabajoProductoSucursal bodegaTrabajoProductoSucursal : producto.getListaBodegaTrabajoSucursal()) {
/* 605:635 */       if ((AppUtil.getBodega() == null) || (
/* 606:636 */         (AppUtil.getBodega() != null) && (AppUtil.getBodega().getId() != bodegaTrabajoProductoSucursal.getBodegaTrabajo().getId()))) {
/* 607:637 */         throw new AS2Exception(getLanguageController().getMensaje("msg_info_parametrizar_bodega_trabajo"));
/* 608:    */       }
/* 609:    */     }
/* 610:    */   }
/* 611:    */   
/* 612:    */   private void reiniciarTablasDetalles()
/* 613:    */   {
/* 614:643 */     this.dtDetalleMacro.reset();
/* 615:644 */     this.dtDetalleMicro.reset();
/* 616:645 */     this.listaDetalleMaterialMicroFiltrados = getListaMicros();
/* 617:646 */     this.listaDetalleMaterialMacroFiltrados = getListaMacros();
/* 618:647 */     totalizar();
/* 619:    */   }
/* 620:    */   
/* 621:    */   public DataTable getDtDetalleMacro()
/* 622:    */   {
/* 623:651 */     return this.dtDetalleMacro;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setDtDetalleMacro(DataTable dtDetalleMacro)
/* 627:    */   {
/* 628:655 */     this.dtDetalleMacro = dtDetalleMacro;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public DataTable getDtDetalleMicro()
/* 632:    */   {
/* 633:659 */     return this.dtDetalleMicro;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void setDtDetalleMicro(DataTable dtDetalleMicro)
/* 637:    */   {
/* 638:663 */     this.dtDetalleMicro = dtDetalleMicro;
/* 639:    */   }
/* 640:    */   
/* 641:    */   public void eliminarDetalle(DetalleOrdenFabricacionMaterial detalle)
/* 642:    */   {
/* 643:667 */     detalle.setEliminado(true);
/* 644:668 */     reiniciarTablasDetalles();
/* 645:    */   }
/* 646:    */   
/* 647:    */   public List<DetalleOrdenFabricacionMaterial> getListaDetalleMaterialMicroFiltrados()
/* 648:    */   {
/* 649:672 */     return this.listaDetalleMaterialMicroFiltrados;
/* 650:    */   }
/* 651:    */   
/* 652:    */   public void setListaDetalleMaterialMicroFiltrados(List<DetalleOrdenFabricacionMaterial> listaDetalleMaterialMicroFiltrados)
/* 653:    */   {
/* 654:676 */     this.listaDetalleMaterialMicroFiltrados = listaDetalleMaterialMicroFiltrados;
/* 655:    */   }
/* 656:    */   
/* 657:    */   public List<DetalleOrdenFabricacionMaterial> getListaDetalleMaterialMacroFiltrados()
/* 658:    */   {
/* 659:680 */     return this.listaDetalleMaterialMacroFiltrados;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public void setListaDetalleMaterialMacroFiltrados(List<DetalleOrdenFabricacionMaterial> listaDetalleMaterialMacroFiltrados)
/* 663:    */   {
/* 664:684 */     this.listaDetalleMaterialMacroFiltrados = listaDetalleMaterialMacroFiltrados;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public BigDecimal getTotalMacro()
/* 668:    */   {
/* 669:688 */     return this.totalMacro;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public void setTotalMacro(BigDecimal totalMacro)
/* 673:    */   {
/* 674:692 */     this.totalMacro = totalMacro;
/* 675:    */   }
/* 676:    */   
/* 677:    */   public BigDecimal getTotalMicro()
/* 678:    */   {
/* 679:696 */     return this.totalMicro;
/* 680:    */   }
/* 681:    */   
/* 682:    */   public void setTotalMicro(BigDecimal totalMicro)
/* 683:    */   {
/* 684:700 */     this.totalMicro = totalMicro;
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void totalizar()
/* 688:    */   {
/* 689:704 */     this.totalMicro = BigDecimal.ZERO;
/* 690:705 */     this.totalMacro = BigDecimal.ZERO;
/* 691:706 */     for (Iterator localIterator1 = getListaMicros().iterator(); localIterator1.hasNext();)
/* 692:    */     {
/* 693:706 */       detalle = (DetalleOrdenFabricacionMaterial)localIterator1.next();
/* 694:707 */       if (!detalle.getMaterial().getUnidad().getTipoUnidadMedida().equals(TipoUnidadMedida.LONGITUD))
/* 695:    */       {
/* 696:708 */         this.totalMicro = this.totalMicro.add(detalle.getCantidadPorCadaBatch());
/* 697:709 */         for (DetalleOrdenFabricacionMaterialMezcla detalleOrdenFabricacionMaterialMezcla : detalle.getListaDetalleOrdenFabricacionMaterialMezcla()) {
/* 698:710 */           if (!detalleOrdenFabricacionMaterialMezcla.isEliminado())
/* 699:    */           {
/* 700:711 */             detalleOrdenFabricacionMaterialMezcla.setCantidad(detalle.getCantidad().multiply(detalleOrdenFabricacionMaterialMezcla.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 701:712 */             BigDecimal cantidadPorCadaBatch = detalle.getCantidadPorCadaBatch();
/* 702:713 */             detalleOrdenFabricacionMaterialMezcla.setCantidadPorCadaBatch(cantidadPorCadaBatch.multiply(detalleOrdenFabricacionMaterialMezcla.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 703:    */           }
/* 704:    */         }
/* 705:    */       }
/* 706:    */     }
/* 707:    */     DetalleOrdenFabricacionMaterial detalle;
/* 708:718 */     for (localIterator1 = getListaMacros().iterator(); localIterator1.hasNext();)
/* 709:    */     {
/* 710:718 */       detalle = (DetalleOrdenFabricacionMaterial)localIterator1.next();
/* 711:719 */       if (!detalle.getMaterial().getUnidad().getTipoUnidadMedida().equals(TipoUnidadMedida.LONGITUD))
/* 712:    */       {
/* 713:720 */         this.totalMacro = this.totalMacro.add(detalle.getCantidadPorCadaBatch());
/* 714:721 */         for (DetalleOrdenFabricacionMaterialMezcla detalleOrdenFabricacionMaterialMezcla : detalle.getListaDetalleOrdenFabricacionMaterialMezcla()) {
/* 715:722 */           if (!detalleOrdenFabricacionMaterialMezcla.isEliminado())
/* 716:    */           {
/* 717:723 */             detalleOrdenFabricacionMaterialMezcla.setCantidad(detalle.getCantidad().multiply(detalleOrdenFabricacionMaterialMezcla.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 718:724 */             BigDecimal cantidadPorCadaBatch = detalle.getCantidadPorCadaBatch();
/* 719:725 */             detalleOrdenFabricacionMaterialMezcla.setCantidadPorCadaBatch(cantidadPorCadaBatch.multiply(detalleOrdenFabricacionMaterialMezcla.getPorcentaje()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
/* 720:    */           }
/* 721:    */         }
/* 722:    */       }
/* 723:    */     }
/* 724:    */     DetalleOrdenFabricacionMaterial detalle;
/* 725:    */   }
/* 726:    */   
/* 727:    */   public BigDecimal getTotal()
/* 728:    */   {
/* 729:733 */     return getTotalMicro().add(getTotalMacro());
/* 730:    */   }
/* 731:    */   
/* 732:    */   public void prepararSuspencion()
/* 733:    */   {
/* 734:    */     try
/* 735:    */     {
/* 736:740 */       if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getId() != 0))
/* 737:    */       {
/* 738:742 */         OrdenFabricacion ordenBase = this.servicioOrdenFabricacion.cargarDetalle(this.ordenFabricacion.getId());
/* 739:744 */         if ((ordenBase.getOrdenFabricacionPadre() != null) && (!ordenBase.getOrdenFabricacionPadre().getEstado().equals(EstadoProduccionEnum.ENVIADA)))
/* 740:    */         {
/* 741:745 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " " + this.ordenFabricacion
/* 742:746 */             .getOrdenFabricacionPadre().getNumero() + ": " + ordenBase.getOrdenFabricacionPadre().getEstado());
/* 743:747 */           return;
/* 744:    */         }
/* 745:749 */         if (!ordenBase.getEstado().equals(EstadoProduccionEnum.ENVIADA))
/* 746:    */         {
/* 747:750 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida") + " " + this.ordenFabricacion.getNumero() + ": " + ordenBase.getEstado());
/* 748:751 */           return;
/* 749:    */         }
/* 750:754 */         this.ordenFabricacion.setCantidadBatch(ordenBase.getCantidadBatch());
/* 751:755 */         this.ordenFabricacion.setCantidadBatchFabricados(ordenBase.getCantidadBatchFabricados());
/* 752:756 */         setActivaSuspender(true);
/* 753:758 */         if ((ordenBase.getCantidadBatchFabricados().compareTo(BigDecimal.ZERO) == 0) || 
/* 754:759 */           (ordenBase.getCantidadFabricada().compareTo(BigDecimal.ZERO) == 0))
/* 755:    */         {
/* 756:760 */           this.servicioOrdenFabricacion.suspenderOrdenFabricacion(ordenBase, false);
/* 757:761 */           addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 758:762 */           RequestContext.getCurrentInstance().update("panelContenedor");
/* 759:    */         }
/* 760:    */         else
/* 761:    */         {
/* 762:764 */           RequestContext context = RequestContext.getCurrentInstance();
/* 763:765 */           context.execute("PF('dlgSuspender').show();");
/* 764:    */         }
/* 765:    */       }
/* 766:    */       else
/* 767:    */       {
/* 768:769 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 769:    */       }
/* 770:    */     }
/* 771:    */     catch (Exception e)
/* 772:    */     {
/* 773:773 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 774:774 */       e.printStackTrace();
/* 775:    */     }
/* 776:    */   }
/* 777:    */   
/* 778:    */   public void suspender(boolean crearNuevaOrdenFabricacion)
/* 779:    */   {
/* 780:    */     try
/* 781:    */     {
/* 782:781 */       this.servicioOrdenFabricacion.suspenderOrdenFabricacion(this.ordenFabricacion, crearNuevaOrdenFabricacion);
/* 783:782 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 784:783 */       setActivaSuspender(false);
/* 785:    */     }
/* 786:    */     catch (AS2Exception e)
/* 787:    */     {
/* 788:785 */       JsfUtil.addErrorMessage(e, "");
/* 789:786 */       e.printStackTrace();
/* 790:    */     }
/* 791:    */     catch (ExcepcionAS2Financiero e)
/* 792:    */     {
/* 793:788 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 794:789 */       e.printStackTrace();
/* 795:    */     }
/* 796:    */     catch (ExcepcionAS2 e)
/* 797:    */     {
/* 798:791 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 799:792 */       e.printStackTrace();
/* 800:    */     }
/* 801:    */     catch (Exception e)
/* 802:    */     {
/* 803:794 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 804:795 */       e.printStackTrace();
/* 805:    */     }
/* 806:    */   }
/* 807:    */   
/* 808:    */   public BigDecimal getBatchsDe()
/* 809:    */   {
/* 810:800 */     if ((this.ordenFabricacion != null) && (this.ordenFabricacion.getOrdenFabricacionPrincipal() != null) && 
/* 811:801 */       (this.ordenFabricacion.getOrdenFabricacionPrincipal().getProducto().getPeso().compareTo(BigDecimal.ZERO) != 0) && 
/* 812:802 */       (getCantidadBatchFormulacion().compareTo(BigDecimal.ZERO) != 0)) {
/* 813:804 */       return getCantidadFormulacion().divide(this.ordenFabricacion.getOrdenFabricacionPrincipal().getProducto().getPeso(), 2, RoundingMode.HALF_UP).divide(getCantidadBatchFormulacion(), 2, RoundingMode.HALF_UP);
/* 814:    */     }
/* 815:806 */     return BigDecimal.ZERO;
/* 816:    */   }
/* 817:    */   
/* 818:    */   public boolean isActivaSuspender()
/* 819:    */   {
/* 820:810 */     return this.activaSuspender;
/* 821:    */   }
/* 822:    */   
/* 823:    */   public void setActivaSuspender(boolean activaSuspender)
/* 824:    */   {
/* 825:814 */     this.activaSuspender = activaSuspender;
/* 826:    */   }
/* 827:    */   
/* 828:    */   public Date getFechaCierreOrden()
/* 829:    */   {
/* 830:818 */     return this.fechaCierreOrden;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public void setFechaCierreOrden(Date fechaCierreOrden)
/* 834:    */   {
/* 835:822 */     this.fechaCierreOrden = fechaCierreOrden;
/* 836:    */   }
/* 837:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.FormulacionBean
 * JD-Core Version:    0.7.0.1
 */