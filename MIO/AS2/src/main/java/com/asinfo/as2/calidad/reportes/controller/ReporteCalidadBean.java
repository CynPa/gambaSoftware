/*   1:    */ package com.asinfo.as2.calidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  10:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  11:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  12:    */ import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
/*  13:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  17:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  18:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  21:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  22:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  23:    */ import java.io.IOException;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Calendar;
/*  26:    */ import java.util.Date;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import javax.faces.model.SelectItem;
/*  35:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  36:    */ import net.sf.jasperreports.engine.JRException;
/*  37:    */ import org.apache.log4j.Logger;
/*  38:    */ 
/*  39:    */ @ManagedBean
/*  40:    */ @ViewScoped
/*  41:    */ public class ReporteCalidadBean
/*  42:    */   extends AbstractBaseReportBean
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 4992625350815288045L;
/*  45:    */   @EJB
/*  46:    */   private transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  47:    */   @EJB
/*  48:    */   private transient ServicioRegistroPeso servicioRegistroPeso;
/*  49:    */   @EJB
/*  50:    */   private transient ServicioOrdenFabricacion servicioOrdenFabricacion;
/*  51:    */   @EJB
/*  52:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  53:    */   @EJB
/*  54:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  55:    */   private Date fechaDesde;
/*  56:    */   private Date fechaHasta;
/*  57:    */   private EstadoControlCalidad estadoControlCalidad;
/*  58:    */   private PedidoProveedor pedidoProveedor;
/*  59:    */   private TipoReporte tipoReporte;
/*  60:    */   private OrdenFabricacion ordenFabricacion;
/*  61:    */   private SubcategoriaProducto subcategoriaProducto;
/*  62:    */   private CategoriaProducto categoriaProductoSeleccionado;
/*  63:    */   private Producto producto;
/*  64:    */   private List<EstadoControlCalidad> listaEstadoControlCalidadCombo;
/*  65:    */   private List<SelectItem> listaTipoReporte;
/*  66:    */   private List<CategoriaProducto> listaCategoriaProductos;
/*  67:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  68:    */   
/*  69:    */   static enum TipoReporte
/*  70:    */   {
/*  71: 74 */     REPORTE_MATERIA_PRIMA("Reporte Materia Prima"),  REPORTE_INSPECCION_PRODUCTO_TERMINADO("Reporte Inspeccion Producto Terminado"),  REPORTE_PRODUCTO_TERMINADO_NO_CONFORME("Reporte Producto Terminado No Conforme");
/*  72:    */     
/*  73:    */     private String nombre;
/*  74:    */     
/*  75:    */     private TipoReporte(String nombre)
/*  76:    */     {
/*  77: 80 */       this.nombre = nombre;
/*  78:    */     }
/*  79:    */     
/*  80:    */     public String getNombre()
/*  81:    */     {
/*  82: 84 */       return this.nombre;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public void setNombre(String nombre)
/*  86:    */     {
/*  87: 88 */       this.nombre = nombre;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected JRDataSource getJRDataSource()
/*  92:    */   {
/*  93:117 */     List listaDatosReporte = new ArrayList();
/*  94:118 */     JRDataSource ds = null;
/*  95:    */     try
/*  96:    */     {
/*  97:    */       String[] fields;
/*  98:    */       String[] fields;
/*  99:121 */       if (this.tipoReporte.equals(TipoReporte.REPORTE_MATERIA_PRIMA))
/* 100:    */       {
/* 101:123 */         listaDatosReporte = this.servicioRegistroPeso.getReporteCalidadMateriaPrima(this.fechaDesde, this.fechaHasta, this.estadoControlCalidad, this.pedidoProveedor);
/* 102:    */         
/* 103:125 */         fields = new String[] { "f_fechaRegistroPeso", "f_numeroRegistroPeso", "f_nombreFiscalProveedor", "f_identificacionProveedor", "f_nombreChofer", "f_licenciaChofer", "f_placaVehiculo", "f_nombreTransportista", "f_numeroPedido", "f_estadoCalidad", "f_direccionProveedor", "f_detalles" };
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:    */         String[] fields;
/* 108:128 */         if (this.tipoReporte.equals(TipoReporte.REPORTE_INSPECCION_PRODUCTO_TERMINADO))
/* 109:    */         {
/* 110:129 */           listaDatosReporte = this.servicioOrdenFabricacion.getReporteInspeccionCalidadPT(this.fechaDesde, this.fechaHasta, this.estadoControlCalidad, this.categoriaProductoSeleccionado, this.subcategoriaProducto, this.producto, this.ordenFabricacion);
/* 111:    */           
/* 112:131 */           fields = new String[] { "f_fechaControlCalidad", "f_numeroOrdenFabricacion", "f_codigoProducto", "f_nombreProducto", "f_cantidad", "f_peso", "f_nombreVariable", "f_valorNir", "f_categoriaVariable" };
/* 113:    */         }
/* 114:    */         else
/* 115:    */         {
/* 116:134 */           fields = new String[] { "" };
/* 117:    */         }
/* 118:    */       }
/* 119:136 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:138 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:141 */     return ds;
/* 126:    */   }
/* 127:    */   
/* 128:    */   @PostConstruct
/* 129:    */   public void init()
/* 130:    */   {
/* 131:146 */     Calendar calfechaDesde = Calendar.getInstance();
/* 132:147 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 133:148 */     this.fechaDesde = calfechaDesde.getTime();
/* 134:149 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 135:150 */     this.estadoControlCalidad = EstadoControlCalidad.LIBERADO;
/* 136:151 */     this.tipoReporte = TipoReporte.REPORTE_MATERIA_PRIMA;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected String getCompileFileName()
/* 140:    */   {
/* 141:161 */     if (this.tipoReporte.equals(TipoReporte.REPORTE_MATERIA_PRIMA)) {
/* 142:162 */       return "reporteCalidadMateriaPrima";
/* 143:    */     }
/* 144:163 */     if (this.tipoReporte.equals(TipoReporte.REPORTE_INSPECCION_PRODUCTO_TERMINADO)) {
/* 145:164 */       return "reporteInspeccionCalidadPT";
/* 146:    */     }
/* 147:166 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String execute()
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:178 */       super.prepareReport();
/* 155:    */     }
/* 156:    */     catch (JRException e)
/* 157:    */     {
/* 158:180 */       LOG.info("Error JRException");
/* 159:181 */       e.printStackTrace();
/* 160:182 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 161:    */     }
/* 162:    */     catch (IOException e)
/* 163:    */     {
/* 164:184 */       LOG.info("Error IOException");
/* 165:185 */       e.printStackTrace();
/* 166:186 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 167:    */     }
/* 168:189 */     return "";
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected Map<String, Object> getReportParameters()
/* 172:    */   {
/* 173:199 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 174:200 */     if (this.tipoReporte.equals(TipoReporte.REPORTE_MATERIA_PRIMA)) {
/* 175:201 */       reportParameters.put("ReportTitle", "Reporte de Materia Prima");
/* 176:202 */     } else if (this.tipoReporte.equals(TipoReporte.REPORTE_INSPECCION_PRODUCTO_TERMINADO)) {
/* 177:203 */       reportParameters.put("ReportTitle", "Reporte Inspección Calidad Producto Terminado");
/* 178:    */     } else {
/* 179:205 */       reportParameters.put("ReportTitle", "Reporte Producto Terminado No Conforme");
/* 180:    */     }
/* 181:208 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 182:209 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 183:210 */     reportParameters.put("p_estadoControlCalidad", this.estadoControlCalidad == null ? "TODOS" : this.estadoControlCalidad.getNombre());
/* 184:211 */     if (this.tipoReporte.equals(TipoReporte.REPORTE_MATERIA_PRIMA))
/* 185:    */     {
/* 186:212 */       reportParameters.put("p_pedidoProveedor", this.pedidoProveedor == null ? "TODOS" : this.pedidoProveedor.getNumero());
/* 187:    */     }
/* 188:213 */     else if (this.tipoReporte.equals(TipoReporte.REPORTE_INSPECCION_PRODUCTO_TERMINADO))
/* 189:    */     {
/* 190:214 */       reportParameters.put("p_categoriaProducto", this.categoriaProductoSeleccionado == null ? "TODOS" : this.categoriaProductoSeleccionado.getNombre());
/* 191:215 */       reportParameters.put("p_subcategoriaProducto", this.subcategoriaProducto == null ? "TODOS" : this.subcategoriaProducto.getNombre());
/* 192:216 */       reportParameters.put("p_producto", this.producto == null ? "TODOS" : this.producto.getNombre());
/* 193:217 */       reportParameters.put("p_ordenFabricacion", this.ordenFabricacion == null ? "TODOS" : this.ordenFabricacion.getNumero());
/* 194:    */     }
/* 195:219 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 196:220 */     return reportParameters;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Date getFechaDesde()
/* 200:    */   {
/* 201:224 */     return this.fechaDesde;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setFechaDesde(Date fechaDesde)
/* 205:    */   {
/* 206:228 */     this.fechaDesde = fechaDesde;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Date getFechaHasta()
/* 210:    */   {
/* 211:232 */     return this.fechaHasta;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setFechaHasta(Date fechaHasta)
/* 215:    */   {
/* 216:236 */     this.fechaHasta = fechaHasta;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public TipoReporte getTipoReporte()
/* 220:    */   {
/* 221:240 */     return this.tipoReporte;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 225:    */   {
/* 226:244 */     this.tipoReporte = tipoReporte;
/* 227:245 */     this.listaEstadoControlCalidadCombo = null;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<SelectItem> getListaTipoReporte()
/* 231:    */   {
/* 232:249 */     this.listaTipoReporte = new ArrayList();
/* 233:250 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 234:    */     {
/* 235:251 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.getNombre());
/* 236:252 */       this.listaTipoReporte.add(item);
/* 237:    */     }
/* 238:255 */     return this.listaTipoReporte;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public EstadoControlCalidad getEstadoControlCalidad()
/* 242:    */   {
/* 243:259 */     return this.estadoControlCalidad;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setEstadoControlCalidad(EstadoControlCalidad estadoControlCalidad)
/* 247:    */   {
/* 248:263 */     this.estadoControlCalidad = estadoControlCalidad;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public PedidoProveedor getPedidoProveedor()
/* 252:    */   {
/* 253:267 */     return this.pedidoProveedor;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setPedidoProveedor(PedidoProveedor pedidoProveedor)
/* 257:    */   {
/* 258:271 */     this.pedidoProveedor = pedidoProveedor;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<EstadoControlCalidad> getListaEstadoControlCalidadCombo()
/* 262:    */   {
/* 263:275 */     if (this.listaEstadoControlCalidadCombo == null)
/* 264:    */     {
/* 265:276 */       this.listaEstadoControlCalidadCombo = new ArrayList();
/* 266:277 */       if (TipoReporte.REPORTE_INSPECCION_PRODUCTO_TERMINADO.equals(this.tipoReporte))
/* 267:    */       {
/* 268:278 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.LIBERADO);
/* 269:279 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.RECHAZADO);
/* 270:280 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.REPROCESO);
/* 271:281 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.CUARENTENA);
/* 272:    */       }
/* 273:283 */       if (TipoReporte.REPORTE_MATERIA_PRIMA.equals(this.tipoReporte))
/* 274:    */       {
/* 275:284 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.EN_ESPERA);
/* 276:285 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.LIBERADO);
/* 277:286 */         this.listaEstadoControlCalidadCombo.add(EstadoControlCalidad.RECHAZADO);
/* 278:    */       }
/* 279:    */     }
/* 280:289 */     return this.listaEstadoControlCalidadCombo;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public List<PedidoProveedor> autocompletarPedidoProveedor(String consulta)
/* 284:    */     throws ExcepcionAS2
/* 285:    */   {
/* 286:293 */     List<PedidoProveedor> lista = new ArrayList();
/* 287:294 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 288:295 */     filtros.put("OR~numero", "%" + consulta + "%");
/* 289:296 */     filtros.put("OR~empresa.nombreComercial", "%" + consulta + "%");
/* 290:297 */     filtros.put("OR~empresa.nombreFiscal", "%" + consulta + "%");
/* 291:298 */     lista = this.servicioPedidoProveedor.obtenerListaPorPagina(0, 50, "fecha", false, filtros);
/* 292:299 */     return lista;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List<OrdenFabricacion> autocompletarOrdenFabricacion(String consulta)
/* 296:    */   {
/* 297:303 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 298:304 */     filtros.put("estado", "!=" + EstadoProduccionEnum.ANULADO);
/* 299:305 */     filtros.put("fechaLanzamiento", OperacionEnum.IS_NOT_NULL.toString());
/* 300:306 */     filtros.put("indicadorSuborden", "false");
/* 301:307 */     filtros.put("OR~numero", consulta);
/* 302:308 */     filtros.put("OR~producto.codigo", consulta);
/* 303:309 */     filtros.put("OR~producto.nombre", consulta);
/* 304:310 */     if (this.categoriaProductoSeleccionado != null) {
/* 305:311 */       filtros.put("producto.subcategoriaProducto.categoriaProducto.idCategoriaProducto", this.categoriaProductoSeleccionado.getId() + "");
/* 306:    */     }
/* 307:313 */     if (this.subcategoriaProducto != null) {
/* 308:314 */       filtros.put("producto.subcategoriaProducto.idSubcategoriaProducto", this.subcategoriaProducto.getId() + "");
/* 309:    */     }
/* 310:316 */     if (this.producto != null) {
/* 311:317 */       filtros.put("producto.idProducto", this.producto.getId() + "");
/* 312:    */     }
/* 313:319 */     return this.servicioOrdenFabricacion.obtenerListaPorPagina(0, 50, "numero", false, filtros, false);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public OrdenFabricacion getOrdenFabricacion()
/* 317:    */   {
/* 318:323 */     return this.ordenFabricacion;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 322:    */   {
/* 323:327 */     this.ordenFabricacion = ordenFabricacion;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public List<CategoriaProducto> getListaCategoriaProductos()
/* 327:    */   {
/* 328:331 */     HashMap<String, String> filters = new HashMap();
/* 329:332 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 330:333 */     if (this.listaCategoriaProductos == null) {
/* 331:334 */       this.listaCategoriaProductos = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 332:    */     }
/* 333:336 */     return this.listaCategoriaProductos;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void cargarListaSubcategoriaProducto()
/* 337:    */   {
/* 338:340 */     this.listaSubcategoriaProducto = new ArrayList();
/* 339:341 */     if (this.categoriaProductoSeleccionado != null)
/* 340:    */     {
/* 341:342 */       HashMap<String, String> filters = new HashMap();
/* 342:343 */       filters.put("categoriaProducto.idCategoriaProducto", "" + this.categoriaProductoSeleccionado.getId());
/* 343:344 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", false, filters);
/* 344:    */     }
/* 345:347 */     this.subcategoriaProducto = null;
/* 346:348 */     actualizarSubcategoriaProducto();
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void actualizarSubcategoriaProducto()
/* 350:    */   {
/* 351:352 */     this.producto = null;
/* 352:353 */     this.ordenFabricacion = null;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void cargarProducto(Producto producto)
/* 356:    */   {
/* 357:357 */     this.producto = producto;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 361:    */   {
/* 362:361 */     return this.subcategoriaProducto;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 366:    */   {
/* 367:365 */     this.subcategoriaProducto = subcategoriaProducto;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public CategoriaProducto getCategoriaProductoSeleccionado()
/* 371:    */   {
/* 372:369 */     return this.categoriaProductoSeleccionado;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void setCategoriaProductoSeleccionado(CategoriaProducto categoriaProductoSeleccionado)
/* 376:    */   {
/* 377:373 */     this.categoriaProductoSeleccionado = categoriaProductoSeleccionado;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public Producto getProducto()
/* 381:    */   {
/* 382:377 */     return this.producto;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setProducto(Producto producto)
/* 386:    */   {
/* 387:381 */     this.producto = producto;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 391:    */   {
/* 392:385 */     return this.listaSubcategoriaProducto;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 396:    */   {
/* 397:389 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 398:    */   }
/* 399:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.reportes.controller.ReporteCalidadBean
 * JD-Core Version:    0.7.0.1
 */