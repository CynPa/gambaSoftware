/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioComponenteCosto;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.produccion.CostosDeFabricacion;
/*  11:    */ import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
/*  12:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  15:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  19:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo;
/*  20:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto;
/*  21:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioCostosDeFabricacion;
/*  22:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  25:    */ import com.asinfo.as2.utils.JsfUtil;
/*  26:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  27:    */ import java.math.BigDecimal;
/*  28:    */ import java.math.RoundingMode;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Date;
/*  31:    */ import java.util.HashMap;
/*  32:    */ import java.util.Iterator;
/*  33:    */ import java.util.List;
/*  34:    */ import java.util.Map;
/*  35:    */ import javax.annotation.PostConstruct;
/*  36:    */ import javax.ejb.EJB;
/*  37:    */ import javax.faces.bean.ManagedBean;
/*  38:    */ import javax.faces.bean.ViewScoped;
/*  39:    */ import javax.faces.model.SelectItem;
/*  40:    */ import org.apache.log4j.Logger;
/*  41:    */ import org.primefaces.component.datatable.DataTable;
/*  42:    */ import org.primefaces.model.LazyDataModel;
/*  43:    */ import org.primefaces.model.SortOrder;
/*  44:    */ 
/*  45:    */ @ManagedBean
/*  46:    */ @ViewScoped
/*  47:    */ public class CostosDeFabricacionBean
/*  48:    */   extends PageControllerAS2
/*  49:    */ {
/*  50:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  51:    */   @EJB
/*  52:    */   private ServicioCostosDeFabricacion servicioCostosDeFabricacion;
/*  53:    */   @EJB
/*  54:    */   private ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  55:    */   @EJB
/*  56:    */   private ServicioComponenteCosto servicioComponenteCosto;
/*  57:    */   @EJB
/*  58:    */   private ServicioInventarioProducto servicioInventarioProducto;
/*  59:    */   @EJB
/*  60:    */   private ServicioBodega servicioBodega;
/*  61:    */   @EJB
/*  62:    */   private ServicioCosteo servicioCosteo;
/*  63:    */   private CostosDeFabricacion costosDeFabricacion;
/*  64:    */   private LazyDataModel<CostosDeFabricacion> listaCostosDeFabricacion;
/*  65:    */   private DataTable dtCostoDeFabricacion;
/*  66:    */   private DataTable dtOrdenFabricacion;
/*  67:    */   private DataTable dtProducto;
/*  68:    */   private List<Producto> listaProducto;
/*  69:    */   private List<Producto> listaProductoFiltrado;
/*  70:    */   private List<DetalleCostoFabricacion> listaDetalleCostoFabricacionFiltrado;
/*  71:    */   private Date fechaInicio;
/*  72:    */   private Date fechaFin;
/*  73:    */   private List<SelectItem> listaSelectItemMes;
/*  74:    */   private String nombreMes;
/*  75:109 */   BigDecimal costoMateriales = BigDecimal.ZERO;
/*  76:110 */   BigDecimal costoManoObra = BigDecimal.ZERO;
/*  77:111 */   BigDecimal costoDepreciaciones = BigDecimal.ZERO;
/*  78:112 */   BigDecimal costoIndirectos = BigDecimal.ZERO;
/*  79:113 */   BigDecimal costoTotalAsignado = BigDecimal.ZERO;
/*  80:115 */   BigDecimal costoMaterialesCalculo = BigDecimal.ZERO;
/*  81:116 */   BigDecimal costoManoObraCalculo = BigDecimal.ZERO;
/*  82:117 */   BigDecimal costoDepreciacionesCalculo = BigDecimal.ZERO;
/*  83:118 */   BigDecimal costoIndirectosCalculo = BigDecimal.ZERO;
/*  84:120 */   Map<Integer, Producto> mapaProducto = new HashMap();
/*  85:    */   
/*  86:    */   @PostConstruct
/*  87:    */   public void init()
/*  88:    */   {
/*  89:128 */     this.listaCostosDeFabricacion = new LazyDataModel()
/*  90:    */     {
/*  91:    */       private static final long serialVersionUID = 1L;
/*  92:    */       
/*  93:    */       public List<CostosDeFabricacion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  94:    */       {
/*  95:135 */         List<CostosDeFabricacion> lista = new ArrayList();
/*  96:136 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  97:    */         
/*  98:138 */         lista = CostosDeFabricacionBean.this.servicioCostosDeFabricacion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  99:    */         
/* 100:140 */         CostosDeFabricacionBean.this.listaCostosDeFabricacion.setRowCount(CostosDeFabricacionBean.this.servicioCostosDeFabricacion.contarPorCriterio(filters));
/* 101:    */         
/* 102:142 */         return lista;
/* 103:    */       }
/* 104:    */     };
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void crearEntidad()
/* 108:    */   {
/* 109:156 */     this.costosDeFabricacion = new CostosDeFabricacion();
/* 110:157 */     this.costosDeFabricacion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 111:158 */     this.costosDeFabricacion.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getId()));
/* 112:159 */     this.costosDeFabricacion.setEstado(Estado.ELABORADO);
/* 113:160 */     Date hoy = new Date();
/* 114:161 */     this.fechaInicio = FuncionesUtiles.getFechaInicioMes(hoy);
/* 115:162 */     this.fechaFin = FuncionesUtiles.getFechaFinMes(hoy);
/* 116:    */     
/* 117:164 */     this.costosDeFabricacion.setAnio(Integer.valueOf(FuncionesUtiles.getAnio(hoy)));
/* 118:165 */     this.costosDeFabricacion.setMes(Integer.valueOf(FuncionesUtiles.getMes(hoy)));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String editar()
/* 122:    */   {
/* 123:174 */     if ((getCostosDeFabricacion() != null) && (getCostosDeFabricacion().getId() != 0))
/* 124:    */     {
/* 125:175 */       this.costosDeFabricacion = this.servicioCostosDeFabricacion.cargarDetalle(Integer.valueOf(this.costosDeFabricacion.getId()));
/* 126:176 */       this.mapaProducto = new HashMap();
/* 127:177 */       for (DetalleCostoFabricacion detalleCostoFabricacion : getListaDetalleCostoFabricacion())
/* 128:    */       {
/* 129:178 */         Producto producto = detalleCostoFabricacion.getOrdenFabricacion().getProducto();
/* 130:179 */         if (this.mapaProducto.containsKey(Integer.valueOf(producto.getId()))) {
/* 131:180 */           producto = (Producto)this.mapaProducto.get(Integer.valueOf(producto.getId()));
/* 132:    */         }
/* 133:182 */         producto.getListaDetalleCostoFabricacion().add(detalleCostoFabricacion);
/* 134:183 */         this.mapaProducto.put(Integer.valueOf(producto.getId()), producto);
/* 135:    */       }
/* 136:185 */       this.listaProducto = null;
/* 137:    */       
/* 138:187 */       this.costoMateriales = this.costosDeFabricacion.getCostoMateriales();
/* 139:188 */       this.costoManoObra = this.costosDeFabricacion.getCostoManoDeObra();
/* 140:189 */       this.costoDepreciaciones = this.costosDeFabricacion.getCostoDepreciaciones();
/* 141:190 */       this.costoIndirectos = this.costosDeFabricacion.getCostoIndirectos();
/* 142:    */       
/* 143:192 */       totalizar(false);
/* 144:193 */       setEditado(true);
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:195 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 149:    */     }
/* 150:197 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String guardar()
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:207 */       this.servicioCostosDeFabricacion.guardar(this.costosDeFabricacion);
/* 158:    */       
/* 159:    */ 
/* 160:210 */       List<Bodega> bodegasRecosteo = new ArrayList();
/* 161:211 */       if (isCosteoPorBodega())
/* 162:    */       {
/* 163:212 */         filtros = agregarFiltroOrganizacion(null);
/* 164:213 */         bodegasRecosteo = this.servicioBodega.obtenerListaCombo("nombre", true, filtros);
/* 165:    */       }
/* 166:    */       else
/* 167:    */       {
/* 168:215 */         bodegasRecosteo = new ArrayList();
/* 169:216 */         bodegasRecosteo.add(null);
/* 170:    */       }
/* 171:219 */       for (Map<String, String> filtros = bodegasRecosteo.iterator(); filtros.hasNext();)
/* 172:    */       {
/* 173:219 */         bodegaTmp = (Bodega)filtros.next();
/* 174:220 */         List<Producto> productosRecosteo = new ArrayList();
/* 175:221 */         productosRecosteo.addAll(this.mapaProducto.values());
/* 176:222 */         i = 0;
/* 177:223 */         versionCosteo = 0;
/* 178:224 */         for (Producto productoTmp : productosRecosteo)
/* 179:    */         {
/* 180:225 */           if (i == 0) {
/* 181:226 */             versionCosteo = productoTmp.getVersionCosteo() + 1;
/* 182:    */           }
/* 183:228 */           this.servicioCosteo.generarCostosEstandar(AppUtil.getOrganizacion().getIdOrganizacion(), productoTmp, this.fechaInicio, this.fechaFin);
/* 184:229 */           this.servicioCosteo.generarCostos(AppUtil.getOrganizacion().getIdOrganizacion(), productoTmp, this.fechaInicio, this.fechaFin, bodegaTmp, Integer.valueOf(versionCosteo), null);
/* 185:230 */           i++;
/* 186:    */         }
/* 187:    */       }
/* 188:    */       Bodega bodegaTmp;
/* 189:    */       int i;
/* 190:    */       int versionCosteo;
/* 191:234 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 192:    */       
/* 193:236 */       limpiar();
/* 194:237 */       setEditado(false);
/* 195:    */     }
/* 196:    */     catch (AS2Exception e)
/* 197:    */     {
/* 198:240 */       JsfUtil.addErrorMessage(e, "");
/* 199:    */     }
/* 200:    */     catch (Exception e)
/* 201:    */     {
/* 202:242 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 203:243 */       LOG.error("ERROR AL GUARDAR DATOS COSTOS DE FABRICACION", e);
/* 204:    */     }
/* 205:245 */     return "";
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String eliminar()
/* 209:    */   {
/* 210:    */     try
/* 211:    */     {
/* 212:255 */       this.servicioCostosDeFabricacion.eliminar(this.costosDeFabricacion);
/* 213:256 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 214:    */     }
/* 215:    */     catch (Exception e)
/* 216:    */     {
/* 217:258 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 218:259 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 219:    */     }
/* 220:261 */     return "";
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String cargarDatos()
/* 224:    */   {
/* 225:270 */     return "";
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String limpiar()
/* 229:    */   {
/* 230:279 */     crearEntidad();
/* 231:280 */     this.listaProducto = null;
/* 232:281 */     this.listaProductoFiltrado = null;
/* 233:282 */     this.mapaProducto = new HashMap();
/* 234:283 */     this.costoMaterialesCalculo = BigDecimal.ZERO;
/* 235:284 */     this.costoManoObraCalculo = BigDecimal.ZERO;
/* 236:285 */     this.costoDepreciacionesCalculo = BigDecimal.ZERO;
/* 237:286 */     this.costoIndirectosCalculo = BigDecimal.ZERO;
/* 238:287 */     this.costoTotalAsignado = BigDecimal.ZERO;
/* 239:288 */     return "";
/* 240:    */   }
/* 241:    */   
/* 242:    */   public CostosDeFabricacion getCostosDeFabricacion()
/* 243:    */   {
/* 244:292 */     return this.costosDeFabricacion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setCostosDeFabricacion(CostosDeFabricacion costosDeFabricacion)
/* 248:    */   {
/* 249:296 */     this.costosDeFabricacion = costosDeFabricacion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public LazyDataModel<CostosDeFabricacion> getListaCostosDeFabricacion()
/* 253:    */   {
/* 254:300 */     return this.listaCostosDeFabricacion;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setListaCostosDeFabricacion(LazyDataModel<CostosDeFabricacion> listaCostosDeFabricacion)
/* 258:    */   {
/* 259:304 */     this.listaCostosDeFabricacion = listaCostosDeFabricacion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List<SelectItem> getListaSelectItemMes()
/* 263:    */   {
/* 264:308 */     if (this.listaSelectItemMes == null)
/* 265:    */     {
/* 266:309 */       this.listaSelectItemMes = new ArrayList();
/* 267:310 */       for (Mes mes : Mes.values())
/* 268:    */       {
/* 269:311 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 270:312 */         this.listaSelectItemMes.add(selectItem);
/* 271:    */       }
/* 272:    */     }
/* 273:315 */     return this.listaSelectItemMes;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public DataTable getDtCostoDeFabricacion()
/* 277:    */   {
/* 278:319 */     return this.dtCostoDeFabricacion;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setDtCostoDeFabricacion(DataTable dtCostoDeFabricacion)
/* 282:    */   {
/* 283:323 */     this.dtCostoDeFabricacion = dtCostoDeFabricacion;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public DataTable getDtOrdenFabricacion()
/* 287:    */   {
/* 288:327 */     return this.dtOrdenFabricacion;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDtOrdenFabricacion(DataTable dtOrdenFabricacion)
/* 292:    */   {
/* 293:331 */     this.dtOrdenFabricacion = dtOrdenFabricacion;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void procesar()
/* 297:    */   {
/* 298:    */     try
/* 299:    */     {
/* 300:336 */       this.fechaInicio = FuncionesUtiles.getFecha(1, this.costosDeFabricacion.getMes().intValue(), this.costosDeFabricacion.getAnio().intValue());
/* 301:337 */       this.fechaFin = FuncionesUtiles.getFechaFinMes(this.costosDeFabricacion.getAnio().intValue(), this.costosDeFabricacion.getMes().intValue());
/* 302:338 */       this.costosDeFabricacion.setFechaDesde(this.fechaInicio);
/* 303:339 */       this.costosDeFabricacion.setFechaHasta(this.fechaFin);
/* 304:340 */       this.costosDeFabricacion.setFecha(this.fechaInicio);
/* 305:    */       
/* 306:    */ 
/* 307:343 */       this.costosDeFabricacion = this.servicioCostosDeFabricacion.generarCostosDeFabricacion(this.costosDeFabricacion, isCosteoSubOrdenes());
/* 308:344 */       this.mapaProducto = new HashMap();
/* 309:345 */       for (DetalleCostoFabricacion detalleCostoFabricacion : getListaDetalleCostoFabricacion())
/* 310:    */       {
/* 311:346 */         Producto producto = detalleCostoFabricacion.getOrdenFabricacion().getProducto();
/* 312:347 */         if (this.mapaProducto.containsKey(Integer.valueOf(producto.getId()))) {
/* 313:348 */           producto = (Producto)this.mapaProducto.get(Integer.valueOf(producto.getId()));
/* 314:    */         }
/* 315:350 */         producto.getListaDetalleCostoFabricacion().add(detalleCostoFabricacion);
/* 316:351 */         this.mapaProducto.put(Integer.valueOf(producto.getId()), producto);
/* 317:    */       }
/* 318:354 */       this.costoMateriales = this.costosDeFabricacion.getCostoMateriales();
/* 319:355 */       this.costoManoObra = this.costosDeFabricacion.getCostoManoDeObra();
/* 320:356 */       this.costoDepreciaciones = this.costosDeFabricacion.getCostoDepreciaciones();
/* 321:357 */       this.costoIndirectos = this.costosDeFabricacion.getCostoIndirectos();
/* 322:    */       
/* 323:359 */       this.listaProducto = null;
/* 324:360 */       this.listaProductoFiltrado = null;
/* 325:361 */       totalizar(true);
/* 326:362 */       this.costosDeFabricacion.setEstado(Estado.PROCESADO);
/* 327:    */     }
/* 328:    */     catch (AS2Exception e)
/* 329:    */     {
/* 330:364 */       JsfUtil.addErrorMessage(e, "");
/* 331:365 */       e.printStackTrace();
/* 332:    */     }
/* 333:    */     catch (ExcepcionAS2 e)
/* 334:    */     {
/* 335:367 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 336:368 */       e.printStackTrace();
/* 337:    */     }
/* 338:    */     catch (Exception e)
/* 339:    */     {
/* 340:370 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 341:371 */       e.printStackTrace();
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void totalizar(boolean indicadorAjustarDiferencias)
/* 346:    */   {
/* 347:376 */     this.costoMaterialesCalculo = BigDecimal.ZERO;
/* 348:377 */     this.costoManoObraCalculo = BigDecimal.ZERO;
/* 349:378 */     this.costoDepreciacionesCalculo = BigDecimal.ZERO;
/* 350:379 */     this.costoIndirectosCalculo = BigDecimal.ZERO;
/* 351:380 */     this.costoTotalAsignado = BigDecimal.ZERO;
/* 352:    */     
/* 353:382 */     int i = 0;
/* 354:384 */     for (Producto producto : getListaProducto())
/* 355:    */     {
/* 356:385 */       BigDecimal costoMaterialesProducto = BigDecimal.ZERO;
/* 357:386 */       BigDecimal costoManoObraProducto = BigDecimal.ZERO;
/* 358:387 */       BigDecimal costoDepreciacionesProducto = BigDecimal.ZERO;
/* 359:388 */       BigDecimal costoIndirectosProducto = BigDecimal.ZERO;
/* 360:389 */       BigDecimal cantidadFabricadaProducto = BigDecimal.ZERO;
/* 361:390 */       BigDecimal costoTotalAsignadoProducto = BigDecimal.ZERO;
/* 362:391 */       for (DetalleCostoFabricacion detalleCostoFabricacion : producto.getListaDetalleCostoFabricacion())
/* 363:    */       {
/* 364:392 */         i++;
/* 365:394 */         if ((i == getListaDetalleCostoFabricacion().size()) && (indicadorAjustarDiferencias))
/* 366:    */         {
/* 367:395 */           BigDecimal diferenciaMateriales = this.costoMateriales.subtract(this.costoMaterialesCalculo);
/* 368:396 */           BigDecimal diferenciaManoObra = this.costoManoObra.subtract(this.costoManoObraCalculo);
/* 369:397 */           BigDecimal diferenciaDepreciacion = this.costoDepreciaciones.subtract(this.costoDepreciacionesCalculo);
/* 370:398 */           BigDecimal diferenciaIndirectos = this.costoIndirectos.subtract(this.costoIndirectosCalculo);
/* 371:    */           
/* 372:400 */           detalleCostoFabricacion.setCostoMaterialesMes(diferenciaMateriales.setScale(4, RoundingMode.HALF_UP));
/* 373:401 */           detalleCostoFabricacion.setCostoManoObraMes(diferenciaManoObra.setScale(4, RoundingMode.HALF_UP));
/* 374:402 */           detalleCostoFabricacion.setCostoDepreciacionMes(diferenciaDepreciacion.setScale(4, RoundingMode.HALF_UP));
/* 375:403 */           detalleCostoFabricacion.setCostoIndirectosMes(diferenciaIndirectos.setScale(4, RoundingMode.HALF_UP));
/* 376:    */           
/* 377:405 */           this.servicioCostosDeFabricacion.actualizarCantidadAsignadaMesCostofabricacion(detalleCostoFabricacion);
/* 378:    */         }
/* 379:407 */         this.costoMaterialesCalculo = this.costoMaterialesCalculo.add(detalleCostoFabricacion.getCostoMaterialesMes());
/* 380:408 */         this.costoManoObraCalculo = this.costoManoObraCalculo.add(detalleCostoFabricacion.getCostoManoObraMes());
/* 381:409 */         this.costoDepreciacionesCalculo = this.costoDepreciacionesCalculo.add(detalleCostoFabricacion.getCostoDepreciacionMes());
/* 382:410 */         this.costoIndirectosCalculo = this.costoIndirectosCalculo.add(detalleCostoFabricacion.getCostoIndirectosMes());
/* 383:411 */         this.costoTotalAsignado = this.costoTotalAsignado.add(detalleCostoFabricacion.getCostoTotalAsignadoMes());
/* 384:    */         
/* 385:413 */         costoMaterialesProducto = costoMaterialesProducto.add(detalleCostoFabricacion.getCostoMaterialesMes());
/* 386:414 */         costoManoObraProducto = costoManoObraProducto.add(detalleCostoFabricacion.getCostoManoObraMes());
/* 387:415 */         costoDepreciacionesProducto = costoDepreciacionesProducto.add(detalleCostoFabricacion.getCostoDepreciacionMes());
/* 388:416 */         costoIndirectosProducto = costoIndirectosProducto.add(detalleCostoFabricacion.getCostoIndirectosMes());
/* 389:417 */         cantidadFabricadaProducto = cantidadFabricadaProducto.add(detalleCostoFabricacion.getCantidadFabricadaMes());
/* 390:418 */         costoTotalAsignadoProducto = costoTotalAsignadoProducto.add(detalleCostoFabricacion.getCostoTotalAsignadoMes());
/* 391:    */       }
/* 392:420 */       producto.setCostoMateriales(costoMaterialesProducto);
/* 393:421 */       producto.setCostoManoObra(costoManoObraProducto);
/* 394:422 */       producto.setCostoDepreciaciones(costoDepreciacionesProducto);
/* 395:423 */       producto.setCostoIndirecto(costoIndirectosProducto);
/* 396:424 */       producto.setCantidadFabricada(cantidadFabricadaProducto);
/* 397:425 */       producto.setCostoTotalAsignado(costoTotalAsignadoProducto);
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   public List<DetalleCostoFabricacion> getListaDetalleCostoFabricacion()
/* 402:    */   {
/* 403:430 */     List<DetalleCostoFabricacion> listaDetalleCostoFabricacion = new ArrayList();
/* 404:431 */     if (this.costosDeFabricacion != null) {
/* 405:432 */       for (DetalleCostoFabricacion detalleCostoFabricacion : this.costosDeFabricacion.getListaDetalleCostoFabricacion()) {
/* 406:433 */         if (!detalleCostoFabricacion.isEliminado()) {
/* 407:434 */           listaDetalleCostoFabricacion.add(detalleCostoFabricacion);
/* 408:    */         }
/* 409:    */       }
/* 410:    */     }
/* 411:438 */     return listaDetalleCostoFabricacion;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public List<Producto> getListaProducto()
/* 415:    */   {
/* 416:442 */     if (this.listaProducto == null)
/* 417:    */     {
/* 418:443 */       this.listaProducto = new ArrayList();
/* 419:444 */       this.listaProducto.addAll(this.mapaProducto.values());
/* 420:    */     }
/* 421:446 */     return this.listaProducto;
/* 422:    */   }
/* 423:    */   
/* 424:    */   public BigDecimal getTotal()
/* 425:    */   {
/* 426:450 */     return this.costoMaterialesCalculo.add(this.costoManoObraCalculo).add(this.costoDepreciacionesCalculo).add(this.costoIndirectosCalculo);
/* 427:    */   }
/* 428:    */   
/* 429:    */   public BigDecimal getCostoMaterialesCalculo()
/* 430:    */   {
/* 431:454 */     return this.costoMaterialesCalculo;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public BigDecimal getCostoManoObraCalculo()
/* 435:    */   {
/* 436:458 */     return this.costoManoObraCalculo;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public BigDecimal getCostoDepreciacionesCalculo()
/* 440:    */   {
/* 441:462 */     return this.costoDepreciacionesCalculo;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public BigDecimal getCostoIndirectosCalculo()
/* 445:    */   {
/* 446:466 */     return this.costoIndirectosCalculo;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public DataTable getDtProducto()
/* 450:    */   {
/* 451:470 */     return this.dtProducto;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setDtProducto(DataTable dtProducto)
/* 455:    */   {
/* 456:474 */     this.dtProducto = dtProducto;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public List<Producto> getListaProductoFiltrado()
/* 460:    */   {
/* 461:478 */     return this.listaProductoFiltrado;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void setListaProductoFiltrado(List<Producto> listaProductoFiltrado)
/* 465:    */   {
/* 466:482 */     this.listaProductoFiltrado = listaProductoFiltrado;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void contabilizar(CostosDeFabricacion costo)
/* 470:    */   {
/* 471:    */     try
/* 472:    */     {
/* 473:487 */       this.servicioCostosDeFabricacion.contabilizar(this.costosDeFabricacion);
/* 474:488 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/* 475:    */     }
/* 476:    */     catch (ExcepcionAS2Financiero e)
/* 477:    */     {
/* 478:491 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 479:492 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 480:493 */       e.printStackTrace();
/* 481:    */     }
/* 482:    */     catch (ExcepcionAS2 e)
/* 483:    */     {
/* 484:495 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 485:496 */       LOG.info("ERROR AL GENERAR-INTERFAZ VENTAS:", e);
/* 486:497 */       e.printStackTrace();
/* 487:    */     }
/* 488:    */   }
/* 489:    */   
/* 490:    */   public BigDecimal getCostoTotalAsignado()
/* 491:    */   {
/* 492:502 */     return this.costoTotalAsignado;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public String getNombreMes()
/* 496:    */   {
/* 497:506 */     if (getCostosDeFabricacion() != null) {
/* 498:507 */       this.nombreMes = FuncionesUtiles.nombreMes(getCostosDeFabricacion().getMes().intValue());
/* 499:    */     } else {
/* 500:509 */       this.nombreMes = FuncionesUtiles.nombreMes(FuncionesUtiles.getMes(new Date()));
/* 501:    */     }
/* 502:511 */     return this.nombreMes;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void setNombreMes(String nombreMes)
/* 506:    */   {
/* 507:515 */     this.nombreMes = nombreMes;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public List<DetalleCostoFabricacion> getListaDetalleCostoFabricacionFiltrado()
/* 511:    */   {
/* 512:519 */     return this.listaDetalleCostoFabricacionFiltrado;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public void setListaDetalleCostoFabricacionFiltrado(List<DetalleCostoFabricacion> listaDetalleCostoFabricacionFiltrado)
/* 516:    */   {
/* 517:523 */     this.listaDetalleCostoFabricacionFiltrado = listaDetalleCostoFabricacionFiltrado;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public boolean isCosteoSubOrdenes()
/* 521:    */   {
/* 522:527 */     return ParametrosSistema.isCosteoSubOrdenes(AppUtil.getOrganizacion().getId()).booleanValue();
/* 523:    */   }
/* 524:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.CostosDeFabricacionBean
 * JD-Core Version:    0.7.0.1
 */